package br.com.fiap.bo;

import java.sql.SQLException;
import java.util.List;

import br.com.fiap.DAO.ClienteDAO;
import br.com.fiap.beans.Cliente;

public class ClienteBO {

    private ClienteDAO clienteDAO;

    public ClienteBO() {
        clienteDAO = new ClienteDAO();
    }

    // Método para selecionar todos os clientes
    public List<Cliente> selecionarBo() throws ClassNotFoundException, SQLException {
        return clienteDAO.listarTodos();
    }

    // Método para inserir um cliente
    public int inserirBo(Cliente cliente) throws ClassNotFoundException, SQLException {
        return clienteDAO.inserir(cliente);
    }

    // Método para buscar um cliente por ID
    public Cliente buscarPorIdBo(int idCliente) throws ClassNotFoundException, SQLException {
        return clienteDAO.buscarPorId(idCliente);
    }

    // Método para atualizar um cliente
    public void atualizarBo(Cliente cliente) throws ClassNotFoundException, SQLException {
        clienteDAO.atualizar(cliente);
    }

    // Método para deletar um cliente
    public void deletarBo(int idCliente) throws ClassNotFoundException, SQLException {
        clienteDAO.deletar(idCliente);
    }
}
