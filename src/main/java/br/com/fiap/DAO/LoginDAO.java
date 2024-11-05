package br.com.fiap.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.fiap.beans.Login;
import br.com.fiap.conexoes.ConexaoFactory;

public class LoginDAO {
    private Connection conexao;

    
    public LoginDAO() {
        
		this.conexao = ConexaoFactory.getConnection();
    }


    public void inserir(Login login, int clienteId) {
        String sql = "INSERT INTO TBL_LOGIN (email, senha, id_cliente) VALUES (?, ?, ?)"; // ajuste o nome da coluna se necessário
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, login.getEmail());
            stmt.setString(2, login.getSenha());
            stmt.setInt(3, clienteId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
            throw new RuntimeException("Erro ao inserir login: " + e.getMessage(), e);
        }
    }

  
    public Login buscarPorClienteId(int clienteId) {
        String sql = "SELECT * FROM TBL_LOGIN WHERE cliente_id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Login(rs.getString("email"), rs.getString("senha"), clienteId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar login: " + e.getMessage(), e);
        }
        return null; 
    }

   
    public void fecharConexao() {
        if (conexao != null) {
            try {
                conexao.close();
                System.out.println("FECHEI");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
