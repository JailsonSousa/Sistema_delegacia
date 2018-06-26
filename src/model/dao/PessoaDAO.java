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
import model.bean.Pessoa;


public class PessoaDAO {
	public List<Pessoa> getAllPessoas() {

		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Pessoa> listaPessoas = new ArrayList<>();

		try {
			statement = connection.prepareStatement("SELECT * FROM pessoas");
			resultSet = statement.executeQuery();
			while (resultSet.next())
				listaPessoas.add(new Pessoa(
						resultSet.getString("nome"), 
						resultSet.getString("cpf"),
						resultSet.getString("rua"),
						resultSet.getString("bairro"),
						resultSet.getString("telefone")
						));

		} catch (SQLException ex) {
			Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			ConnectionFactory.closeConnection(connection, statement, resultSet);
		}
		return listaPessoas;
	}
	
	public Pessoa getPessoa(String cpf) {

		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Pessoa pessoa = null;

		try {
			statement = connection.prepareStatement("SELECT * FROM pessoas where cpf = ?");
			statement.setString(1, cpf);
			resultSet = statement.executeQuery();

			while (resultSet.next())
				pessoa = new Pessoa(
						resultSet.getString("nome"), 
						resultSet.getString("cpf"),
						resultSet.getString("rua"),
						resultSet.getString("bairro"),
						resultSet.getString("telefone")
						);

		} catch (SQLException ex) {
			Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			ConnectionFactory.closeConnection(connection, statement, resultSet);
		}
		return pessoa;
	}
	
	public void create(Pessoa p){
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement("INSERT INTO pessoas (nome, cpf, rua, bairro, telefone)VALUES(?,?,?,?,?)");
			statement.setString(1, p.getNome());
			statement.setString(2, p.getCpf());
			statement.setString(3, p.getRua());
			statement.setString(4, p.getBairro());
			statement.setString(5, p.getTelefone());
			statement.executeUpdate();
			connection.close();
			statement.close();
			//JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
			System.out.println("Cadastrado com sucesso!");
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			ConnectionFactory.closeConnection(connection, statement);
		}
	}
	
	public void update(Pessoa p) {

        Connection connection = ConnectionFactory.getConnection();
        
        PreparedStatement statement = null;

        try {
        	statement = connection.prepareStatement("UPDATE pessoas SET nome = ? , cpf = ?, rua = ?, bairro = ?, telefone = ? WHERE cpf = ?");
        	statement.setString(1, p.getNome());
        	statement.setString(2, p.getCpf());
        	statement.setString(3, p.getRua());
        	statement.setString(4, p.getBairro());
        	statement.setString(5, p.getTelefone());
        	statement.setString(6, p.getCpf());
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
        	statement = connection.prepareStatement("DELETE FROM pessoas WHERE cpf = ?");
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
