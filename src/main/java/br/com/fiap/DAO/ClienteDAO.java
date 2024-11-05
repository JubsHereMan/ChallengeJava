package br.com.fiap.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.beans.Cliente;
import br.com.fiap.conexoes.ConexaoFactory;

public class ClienteDAO {

    

	private Connection conexao;

	public ClienteDAO() {
	    this.conexao = ConexaoFactory.getConnection();
	    if (this.conexao == null) {
	        throw new IllegalStateException("Erro ao estabelecer a conexão com o banco de dados.");
	    }
	}


    public ClienteDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public int inserir(Cliente cliente) {
        String sql = "INSERT INTO TBL_CLIENTE (nome, cpf) VALUES (?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.executeUpdate();

            // Obtenção do ID do cliente após a inserção
            String queryId = "SELECT id_cliente FROM TBL_CLIENTE WHERE cpf = ?";
            try (PreparedStatement stmtId = conexao.prepareStatement(queryId)) {
                stmtId.setString(1, cliente.getCpf());
                ResultSet rs = stmtId.executeQuery();
                if (rs.next()) {
                    return rs.getInt("id_cliente");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir cliente: " + e.getMessage());
        }
        return -1;
    }



    public Cliente buscarPorId(int idCliente) {
        String sql = "SELECT * FROM tbl_cliente WHERE id_cliente = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getInt("id_endereco")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
        }
        return null;
    }

    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM tbl_cliente";
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getInt("id_endereco")
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }
        return clientes;
    }

    public void atualizar(Cliente cliente) {
        String sql = "UPDATE tbl_cliente SET nome = ?, cpf = ? WHERE id_cliente = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setInt(3, cliente.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    public void deletar(int idCliente) {
        String sql = "DELETE FROM tbl_cliente WHERE id_cliente = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao deletar cliente: " + e.getMessage());
        }
    }
    
    public void fecharConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
                System.out.println("FECHEI");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar a conexão: " + e.getMessage());
        }
    }
    
}
