package projetoinfobus_versao2.model;

public class Usuario {
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String login;
    private String senha;
    private String tipo; // ex: "Passageiro", "Motorista", "Financeiro", etc.

    // Construtor vazio (necessário para JDBC e frameworks)
    public Usuario() {}

    // Construtor completo compatível com super(...) em Funcionario e subclasses
    public Usuario(int id, String nome, String cpf, String email, String telefone,
                   String login, String senha, String tipo) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.login = login;
        this.senha = senha;
        this.tipo = tipo;
    }

    // --- Getters e Setters ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
