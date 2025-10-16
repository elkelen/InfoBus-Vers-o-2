package projetoinfobus_versao2.model;

import java.util.Date;

public class Atendente extends Funcionario {

    // Construtor vazio
    public Atendente() {}

    // Construtor completo
    public Atendente(int id, String nome, String cpf, String email, String telefone,
                     String login, String senha, String tipo, String matricula, String cargo, double salario, String statusPagamento) {
        super(id, nome, cpf, email, telefone, login, senha, "Atendente", matricula, cargo, salario, statusPagamento);
    }

}
