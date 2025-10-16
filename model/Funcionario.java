package projetoinfobus_versao2.model;

public class Funcionario extends Usuario {
    private String matricula;
    private String cargo;
    private double salario;
    private String statusPagamento;

    // Construtor vazio
    public Funcionario() {
        super();
    }

    // Construtor completo
    public Funcionario(int id, String nome, String cpf, String email, String telefone,
                       String login, String senha, String tipo,
                       String matricula, String cargo, double salario, String statusPagamento) {
        super(id, nome, cpf, email, telefone, login, senha, tipo); // chamando superclasse Usuario
        this.matricula = matricula;
        this.cargo = cargo;
        this.salario = salario;
        this.statusPagamento = statusPagamento;
    }

    // Getters e Setters
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
    
    public String getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

}
