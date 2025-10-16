package projetoinfobus_versao2.model;

import java.util.Date;

public class GerenteRH extends Funcionario {

    // Construtor vazio
    public GerenteRH() {}

    // Construtor completo
    public GerenteRH(int id, String nome, String cpf, String email, String telefone,
                     String login, String senha, String tipo, String matricula, String cargo, double salario, String statusPagamento) {
        super(id, nome, cpf, email, telefone, login, senha, "GerenteRH", matricula, cargo, salario, statusPagamento);
    }
}
