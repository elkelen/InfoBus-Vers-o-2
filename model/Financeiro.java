package projetoinfobus_versao2.model;

import java.util.Date;

public class Financeiro extends Funcionario {

    // Construtor vazio
    public Financeiro() {}

    // Construtor completo
    public Financeiro(int id, String nome, String cpf, String email, String telefone,
                     String login, String senha, String tipo, String matricula, String cargo, double salario, String statusPagamento) {
        super(id, nome, cpf, email, telefone, login, senha, "Financeiro", matricula, cargo, salario, statusPagamento);
    }

}
