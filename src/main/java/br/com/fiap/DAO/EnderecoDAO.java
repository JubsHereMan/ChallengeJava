package br.com.fiap.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.fiap.model.Endereco;

public class EnderecoDAO {

    private Connection conexao;

    
    public EnderecoDAO() {
        try {
            this.conexao = DriverManager.getConnection(
                "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL", "RM557774", "080403"
            );
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    public int inserir(Endereco endereco, int clienteId, Connection connection) {
        String sql = "INSERT INTO tbl_endereco (id_endereco, logradouro, bairro, localidade, uf, numero, cep, id_cliente) VALUES (seq_id_endereco.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, endereco.getLogradouro());
            stmt.setString(2, endereco.getBairro());
            stmt.setString(3, endereco.getLocalidade());
            stmt.setString(4, endereco.getUf());
            stmt.setString(5, endereco.getNumero());
            stmt.setString(6, endereco.getCep());
            stmt.setInt(7, clienteId); // ID do cliente

            stmt.executeUpdate();
            return 1; // Retorna um valor indicando sucesso
        } catch (SQLException e) {
            System.out.println("Erro ao inserir endereço: " + e.getMessage());
            return -1; // Indica erro
        }
    }

    public Endereco buscarPorId(int idEndereco) {
        String sql = "SELECT * FROM tbl_endereco WHERE id_endereco = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idEndereco);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Endereco(
                    rs.getString("logradouro"),
                    rs.getString("numero"),
                    rs.getString("cep"),
                    rs.getString("bairro"),
                    rs.getString("localidade"), 
                    rs.getString("uf") 
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar endereço: " + e.getMessage());
        }
        return null;
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
