package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import connection.ConnectionFactory;
import model.bean.Acusado;


public class AcusadoDAO {
	
	public List<Acusado> getAllAcusados() {

		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Acusado> listaAcusado = new ArrayList<>();

		try {
			statement = connection.prepareStatement("SELECT * FROM acusados");
			resultSet = statement.executeQuery();
			while (resultSet.next())
				listaAcusado.add(new Acusado(
						resultSet.getString("nome"), 
						resultSet.getString("cpf"),
						resultSet.getString("rua"),
						resultSet.getString("bairro"),
						resultSet.getString("telefone")
						));

		} catch (SQLException ex) {
			Logger.getLogger(AcusadoDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			ConnectionFactory.closeConnection(connection, statement, resultSet);
		}
		return listaAcusado;
	}
	
	public Acusado getAcusado(String cpf) {

		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Acusado acusado = null;

		try {
			statement = connection.prepareStatement("SELECT * FROM acusados where cpf =?");
			statement.setString(1, cpf);
			resultSet = statement.executeQuery();

			while (resultSet.next())
				acusado = new Acusado(
						resultSet.getString("nome"), 
						resultSet.getString("cpf"),
						resultSet.getString("rua"),
						resultSet.getString("bairro"),
						resultSet.getString("telefone")
						);

		} catch (SQLException ex) {
			Logger.getLogger(DelitoDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			ConnectionFactory.closeConnection(connection, statement, resultSet);
		}
		return acusado;
	}
	
	public void create(Acusado a){
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement("INSERT INTO acusados (nome, cpf, rua, bairro, telefone)VALUES(?,?,?,?,?)");
			statement.setString(1, a.getNome());
			statement.setString(2, a.getCpf());
			statement.setString(3, a.getRua());
			statement.setString(4, a.getBairro());
			statement.setString(5, a.getTelefone());
			statement.executeUpdate();
			JOptionPane.showMessageDialog(null, "Acusado registrado com sucesso!");
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			ConnectionFactory.closeConnection(connection, statement);
		}
	}
	
	public void update(Acusado a) {

        Connection connection = ConnectionFactory.getConnection();
        
        PreparedStatement statement = null;

        try {
        	statement = connection.prepareStatement("UPDATE acusados SET nome = ? , cpf = ?, rua = ?, bairro = ?, telefone = ? WHERE cpf = ?");
        	statement.setString(1, a.getNome());
        	statement.setString(2, a.getCpf());
        	statement.setString(3, a.getRua());
        	statement.setString(4, a.getBairro());
        	statement.setString(5, a.getTelefone());
        	statement.setString(6, a.getCpf());
        	statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }
	
	public void delete(String cpf) {

        Connection connection = ConnectionFactory.getConnection();
        
        PreparedStatement statement = null;

        try {
        	statement = connection.prepareStatement("DELETE FROM acusados WHERE cpf = ?");
            statement.setString(1, cpf);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir: " + ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
}
