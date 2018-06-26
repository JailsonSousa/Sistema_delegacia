package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.spi.DirStateFactory.Result;
import javax.swing.JOptionPane;

import connection.ConnectionFactory;
import model.bean.Acusado;
import model.bean.BoletimOcorrencia;
import model.bean.Delito;
import model.bean.Pessoa;
import model.bean.Vitima;

public class BoletimOcorrenciaDAO {
	
	
	public List<BoletimOcorrencia> getAllBoletimOcorrencia() throws SQLException {

		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		List<BoletimOcorrencia> listaBoletimOcorrencia = new ArrayList<>();
	
		try {
			
			statement = connection.prepareStatement("SELECT * FROM boletim_ocorrencia");
			resultSet = statement.executeQuery();

			while (resultSet.next())
				listaBoletimOcorrencia.add(new BoletimOcorrencia(new Delito(resultSet.getString("delito")),
						resultSet.getString("descricao"), resultSet.getString("rua"), resultSet.getString("bairro")));

		} catch (SQLException ex) {
			System.out.println(ex);
		} finally {
			connection.close();
			statement.close();
			resultSet.close();
		}
		return listaBoletimOcorrencia;
	}
	
	public BoletimOcorrencia getBoletimOcorrencia(int idBO) {

		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement statement = null;
		ResultSet resultSet = null;
		BoletimOcorrencia boletimOcorrencia = null;
		List<Pessoa> vitimas = new ArrayList<>();
		List<Pessoa> acusados = new ArrayList<>();

		try {

			// carrega os acusados e vitimas
			statement = connection.prepareStatement("SELECT pe.nome, pe.cpf, pe.rua, pe.bairro, pe.telefone, bo.envolvimento FROM boletim_ocorrencia_envolvidos as bo, pessoas pe WHERE pe.cpf = bo.cpf AND id_bo="+idBO);
			//statement.setInt(1, idBO);
			resultSet = statement.executeQuery();
			
			if(!resultSet.next())
				return null;
			while(resultSet.next()) {
				if (resultSet.getString("envolvimento").equalsIgnoreCase("vitima")) {
					vitimas.add(new Vitima(resultSet.getString("nome"), resultSet.getString("cpf"),
							resultSet.getString("rua"), resultSet.getString("bairro"), resultSet.getString("telefone")));
				} else {
					acusados.add(new Acusado(resultSet.getString("nome"), resultSet.getString("cpf"),
							resultSet.getString("rua"), resultSet.getString("bairro"), resultSet.getString("telefone")));
				}
			}

			// carrega boletim de ocorrencia
			statement = connection.prepareStatement("SELECT * FROM boletim_ocorrencia where id="+idBO);
			//statement.setInt(1, idBO);
			resultSet = statement.executeQuery();
			// carrega o tipo de delito com base no result set do boletim de ocorrencia
			
			//statement.setString(1, auxrs.getString("nome"));
			
			resultSet.next();
			
			String nomeDelito = resultSet.getString("delito");
			String descricao = resultSet.getString("descricao");
			String rua = resultSet.getString("rua");
			String bairro = resultSet.getString("bairro");
			
			boletimOcorrencia = new BoletimOcorrencia(
						new Delito(nomeDelito), vitimas, acusados,
						descricao, rua, bairro);
			
			resultSet.close();
			statement.close();
			connection.close();

		} catch (SQLException ex) {
			Logger.getLogger(BoletimOcorrenciaDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			//ConnectionFactory.closeConnection(connection, statement, resultSet);
		}
		return boletimOcorrencia;
	}
	
	public void create(BoletimOcorrencia bo) {
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = null;
		try {

			statement = connection.prepareStatement(
					"INSERT INTO boletim_ocorrencia(delito, descricao, rua, bairro)" + "VALUES(?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, bo.getDelito().getNome());
			statement.setString(2, bo.getDescricao());
			statement.setString(3, bo.getRua());
			statement.setString(4, bo.getBairro());
			statement.executeUpdate();
			statement.getGeneratedKeys().next();
			int idBO = statement.getGeneratedKeys().getInt("id");

			for (int i = 0; i < bo.getVitimas().size(); i++) {
				statement = connection.prepareStatement(
						"INSERT INTO boletim_ocorrencia_envolvidos( id_bo, cpf, envolvimento)" + "VALUES(?,?,?)");
				statement.setInt(1, idBO);
				statement.setString(2, bo.getVitimas().get(i).getCpf());
				statement.setString(3, "vitima");
				statement.executeUpdate();
			}

			for (int i = 0; i < bo.getAcusados().size(); i++) {
				statement = connection.prepareStatement(
						"INSERT INTO boletim_ocorrencia_envolvidos( id_bo, cpf, envolvimento)" + "VALUES(?,?,?)");
				statement.setInt(1, idBO);
				statement.setString(2, bo.getAcusados().get(i).getCpf());
				statement.setString(3, "acusados");
				statement.executeUpdate();
			}
			
			
			connection.close();
			statement.close();
			System.out.println("Boletim de Ocorrencia registrado com sucesso!");
			//JOptionPane.showMessageDialog(null, "Boletim de Ocorrencia registrado com sucesso!");
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			//ConnectionFactory.closeConnection(connection, statement);
		}
	}
	
	public void update(int id_bo, BoletimOcorrencia bo){

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;

        try {
      
        	// atualiza BO
        	statement = connection.prepareStatement("UPDATE boletim_ocorrencia SET delito = ? , descricao = ?, rua = ?, bairro = ? WHERE id ="+id_bo);
        	statement.setString(1, bo.getDelito().getNome());
        	statement.setString(2, bo.getDescricao());
        	statement.setString(3, bo.getRua());
        	statement.setString(4, bo.getBairro());
        	statement.executeUpdate();
        	
        	// atualiza envolvidos
        	// deleta todos os envolvidos no B.O e recria dnv
        	statement = connection.prepareStatement("DELETE FROM boletim_ocorrencia_envolvidos WHERE id_bo = ?");
            statement.setInt(1, id_bo);
            statement.executeUpdate();
            
			for (int i = 0; i < bo.getVitimas().size(); i++) {
				statement = connection.prepareStatement(
						"INSERT INTO boletim_ocorrencia_envolvidos( id_bo, cpf, envolvimento)" + "VALUES(?,?,?)");
				statement.setInt(1, id_bo);
				statement.setString(2, bo.getVitimas().get(i).getCpf());
				statement.setString(3, "vitima");
				statement.executeUpdate();
			}

			for (int i = 0; i < bo.getAcusados().size(); i++) {
				statement = connection.prepareStatement(
						"INSERT INTO boletim_ocorrencia_envolvidos( id_bo, cpf, envolvimento)" + "VALUES(?,?,?)");
				statement.setInt(1, id_bo);
				statement.setString(2, bo.getAcusados().get(i).getCpf());
				statement.setString(3, "acusados");
				statement.executeUpdate();
			}
        	       	
            //JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
			System.out.println("Atualiza��o feita com sucesso!");
			statement.close();
			connection.close();
            
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex);
        } finally {
            //ConnectionFactory.closeConnection(connection, statement);
        }

    }
	
	public void delete(int id_bo) {

        Connection connection = ConnectionFactory.getConnection();
        
        PreparedStatement statement = null;

        try {
        	statement = connection.prepareStatement("DELETE FROM boletim_ocorrencia_envolvidos WHERE id_bo = ?");
            statement.setInt(1, id_bo);
            statement.executeUpdate();
            
        	statement = connection.prepareStatement("DELETE FROM boletim_ocorrencia WHERE id = ?");
            statement.setInt(1, id_bo);
            statement.executeUpdate();
            
            //JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
            System.out.println("Excluido com sucesso!");
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Erro ao excluir: " + ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
}
