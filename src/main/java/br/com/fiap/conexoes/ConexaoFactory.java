package br.com.fiap.conexoes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {
    public static Connection getConnection() {
        try {
            // Exemplo com JDBC: ajuste conforme seu driver e URL do banco
            return DriverManager.getConnection("jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL", "RM557774", "080403");
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Certifique-se de lidar com isso adequadamente
        }
    }
}
