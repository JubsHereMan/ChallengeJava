package br.com.fiap.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import br.com.fiap.DAO.ClienteDAO;
import br.com.fiap.DAO.EnderecoDAO;
import br.com.fiap.DAO.LoginDAO;
import br.com.fiap.beans.Cliente;
import br.com.fiap.beans.Login;
import br.com.fiap.model.Endereco;
import br.com.fiap.services.ViaCepService;

public class Crud {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClienteDAO clienteDAO = new ClienteDAO();
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        LoginDAO loginDAO = new LoginDAO();
        ViaCepService viaCepService = new ViaCepService();

        try {
            // Testando a conexão
            testarConexao();

            while (true) {
                System.out.println("\nEscolha uma opção:");
                System.out.println("1. Inserir novo cliente");
                System.out.println("2. Atualizar cliente");
                System.out.println("3. Deletar cliente");
                System.out.println("4. Listar todos os clientes");
                System.out.println("5. Buscar cliente por ID");
                System.out.println("0. Sair");

                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        inserirCliente(scanner, clienteDAO, enderecoDAO, loginDAO, viaCepService);
                        break;

                    case 2:
                        // Atualizar cliente
                        System.out.println("Digite o ID do cliente a ser atualizado:");
                        int idClienteAtualizar = scanner.nextInt();
                        scanner.nextLine();

                        Cliente clienteExistente = clienteDAO.buscarPorId(idClienteAtualizar);
                        if (clienteExistente == null) {
                            System.out.println("Cliente não encontrado.");
                            break;
                        }

                        System.out.println("Digite o novo nome:");
                        String novoNome = scanner.nextLine();
                        System.out.println("Digite o novo CPF:");
                        String novoCpf = scanner.nextLine();

                        clienteExistente.setNome(novoNome);
                        clienteExistente.setCpf(novoCpf);
                        clienteDAO.atualizar(clienteExistente);
                        System.out.println("Cliente atualizado com sucesso!");
                        break;

                    case 3:
                        System.out.println("Digite o ID do cliente a ser deletado:");
                        int idClienteDeletar = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Tentando deletar cliente com ID: " + idClienteDeletar);
                        clienteDAO.deletar(idClienteDeletar);
                        System.out.println("Cliente deletado com sucesso!");
                        break;

                    case 4:
                        // Listar todos os clientes
                        System.out.println("Lista de clientes:");
                        for (Cliente c : clienteDAO.listarTodos()) {
                            System.out.println(c);
                        }
                        break;

                    case 5:
                        // Buscar cliente por ID
                        System.out.println("Digite o ID do cliente:");
                        int idClienteBuscar = scanner.nextInt();
                        scanner.nextLine();

                        Cliente clienteBuscado = clienteDAO.buscarPorId(idClienteBuscar);
                        if (clienteBuscado != null) {
                            Endereco enderecoBuscado = enderecoDAO.buscarPorId(clienteBuscado.getEnderecoId());
                            Login loginBuscado = loginDAO.buscarPorClienteId(idClienteBuscar);
                            System.out.println("Cliente: " + clienteBuscado);
                            System.out.println("Endereço: " + enderecoBuscado);
                            System.out.println("Login: " + loginBuscado);
                        } else {
                            System.out.println("Cliente não encontrado.");
                        }
                        break;

                    case 0:
                        System.out.println("Saindo...");
                        return;

                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao realizar operação: " + e.getMessage());
        } finally {
            // Fechando conexões
            try {
                clienteDAO.fecharConexao();
                enderecoDAO.fecharConexao();
                loginDAO.fecharConexao();
                scanner.close();
            } catch (Exception e) {
                System.out.println("Erro ao fechar as conexões: " + e.getMessage());
            }
        }
    }

    private static void inserirCliente(Scanner scanner, ClienteDAO clienteDAO, EnderecoDAO enderecoDAO, LoginDAO loginDAO, ViaCepService viaCepService) throws Exception {
        System.out.println("Digite o CEP:");
        String cep = scanner.nextLine();

        Endereco endereco = viaCepService.buscarEnderecoPorCep(cep);
        if (endereco == null) {
            System.out.println("Endereço não encontrado. Verifique o CEP.");
            return;
        }

        System.out.println("Logradouro: " + endereco.getLogradouro());
        System.out.println("Bairro: " + endereco.getBairro());
        System.out.println("Cidade: " + endereco.getLocalidade());
        System.out.println("Estado: " + endereco.getUf());

        System.out.println("Digite o número:");
        String numero = scanner.nextLine();
        endereco.setNumero(numero);
        endereco.setCep(cep.replace("-", "").trim()); // Formatando o CEP

        System.out.println("Digite o nome do cliente:");
        String nomeCliente = scanner.nextLine();
        System.out.println("Digite o CPF do cliente:");
        String cpfCliente = scanner.nextLine();

        Cliente cliente = new Cliente(nomeCliente, cpfCliente, 0); // Inicializando com ID de endereço como 0
        int clienteId = clienteDAO.inserir(cliente);
        if (clienteId == -1) {
            System.out.println("Erro ao inserir cliente.");
            return;
        }
        System.out.println("Cliente inserido com sucesso. ID: " + clienteId);

        // Gerenciando a conexão ao inserir o endereço
        try (Connection connection = obterConexao()) { // Substitua obterConexao() pelo seu método de conexão
            int enderecoId = enderecoDAO.inserir(endereco, clienteId, connection);
            if (enderecoId == -1) {
                System.out.println("Erro ao inserir endereço.");
                return;
            }
            System.out.println("Endereço inserido com sucesso. ID: " + enderecoId);
        } catch (SQLException e) {
            System.out.println("Erro ao obter conexão: " + e.getMessage());
        }

        System.out.println("Digite o email:");
        String email = scanner.nextLine();
        System.out.println("Digite a senha:");
        String senha = scanner.nextLine();

        Login login = new Login(email, senha, clienteId);
        loginDAO.inserir(login, clienteId);
        System.out.println("Login cadastrado com sucesso!");
    }


    private static Connection obterConexao() {
        try {
            String url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521/ORCL"; // Altere para seu banco
            String usuario = "RM557774"; // Altere para seu usuário
            String senha = "080403"; // Altere para sua senha
            return DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            System.out.println("Erro ao obter conexão: " + e.getMessage());
            return null;
        }
    }

    public static void testarConexao() {
        try (Connection conn = obterConexao()) {
            if (conn != null) {
                System.out.println("Conexão estabelecida com sucesso!");
            } else {
                System.out.println("Falha ao estabelecer conexão.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao testar conexão: " + e.getMessage());
        }
    }
}
