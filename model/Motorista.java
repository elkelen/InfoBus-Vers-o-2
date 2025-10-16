package projetoinfobus_versao2.model;

import java.util.Date;

public class Motorista extends Funcionario {

    private String cnh;

    // Construtor vazio
    public Motorista() {
        super();
    }

    // Construtor completo
    public Motorista(int id, String nome, String cpf, String email, String telefone,
                     String login, String senha, String tipo, String matricula, String cargo, double salario,
                     String cnh, String statusPagamento) {
        super(id, nome, cpf, email, telefone, login, senha, "Motorista", matricula, cargo, salario, statusPagamento);
        this.cnh = cnh;
    }

    // Getters e Setters
    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

}
