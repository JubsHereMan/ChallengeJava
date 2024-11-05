package br.com.fiap.beans;

public class Cliente {
    private int id; // ID do cliente
    private String nome;
    private String cpf;
    private int enderecoId;

    // Construtor sem ID
    public Cliente(String nome, String cpf, int enderecoId) {
        this.nome = nome;
        this.cpf = cpf;
        this.enderecoId = enderecoId;
    }

    // Construtor com ID
    public Cliente(int id, String nome, String cpf, int enderecoId) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.enderecoId = enderecoId;
    }
    
    
    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }
    public Cliente() {
		super();
	}

	
    

    public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public int getEnderecoId() {
		return enderecoId;
	}

	public void setEnderecoId(int enderecoId) {
		this.enderecoId = enderecoId;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", enderecoId=" + enderecoId + "]";
	}

   
}
