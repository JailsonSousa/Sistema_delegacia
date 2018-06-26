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
import model.bean.Delito;

public class DelitoDAO {
	
	public List<Delito> getAllDelito() {

		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		List<Delito> listaDelitos = new ArrayList<>();

		try {
			statement = connection.prepareStatement("SELECT * FROM delitos");
			resultSet = statement.executeQuery();

			while (resultSet.next())
				listaDelitos.add(new Delito(resultSet.getString("nome"), resultSet.getString("descricao")));

		} catch (SQLException ex) {
			Logger.getLogger(DelitoDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			ConnectionFactory.closeConnection(connection, statement, resultSet);
		}
		return listaDelitos;
	}
	
	public Delito getDelito(String nome) {

		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Delito delito = null;

		try {
			statement = connection.prepareStatement("SELECT * FROM delitos where nome ="+ nome);
			resultSet = statement.executeQuery();

			while (resultSet.next())
				delito = new Delito(resultSet.getString("nome"), resultSet.getString("descricao"));

		} catch (SQLException ex) {
			Logger.getLogger(DelitoDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			ConnectionFactory.closeConnection(connection, statement, resultSet);
		}
		return delito;
	}
	
	public void create(Delito d){
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement("INSERT INTO delitos (nome,descrisao)VALUES(?,?)");
			statement.setString(1, d.getNome());
			statement.setString(2, d.getDescricao());
			statement.executeUpdate();
			JOptionPane.showMessageDialog(null, "Delito salvo com sucesso!");
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			ConnectionFactory.closeConnection(connection, statement);
		}
	}
	
	public void update(Delito d) {

        Connection connection = ConnectionFactory.getConnection();
        
        PreparedStatement statement = null;

        try {
        	statement = connection.prepareStatement("UPDATE delitos SET nome = ? , descrisao = ? WHERE nome = ?");
        	statement.setString(1, d.getNome());
        	statement.setString(1, d.getDescricao());
        	statement.setString(1, d.getNome());
        	statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }
	
	public void delete(String nome) {

        Connection connection = ConnectionFactory.getConnection();
        
        PreparedStatement statement = null;

        try {
        	statement = connection.prepareStatement("DELETE FROM delitos WHERE name = ?");
            statement.setString(1, nome);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir: " + ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
}
