package br.com.fiap.beans;

public class Login {
    private String email;
    private String senha;
    private int idCliente; // Campo para o relacionamento com o cliente

    // Construtor com parâmetros
    public Login(String email, String senha, int idCliente) {
        this.email = email;
        this.senha = senha;
        this.idCliente = idCliente;
    }

    // Construtor padrão
    public Login() {
        super();
    }

    // Getters e Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        return "Login [email=" + email + ", senha=" + senha + ", idCliente=" + idCliente + "]";
    }
}
