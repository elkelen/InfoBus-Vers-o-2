package projetoinfobus_versao2.model;

import java.util.Date;

public class GerenteADM extends Funcionario {

    // Atributos específicos do Gerente Administrativo (se houver)
    // Por enquanto não há atributos extras além dos de Funcionario
    
    // Construtor vazio
    public GerenteADM() {
        super();
    }

    // Construtor completo (replicando a assinatura do Funcionario)
    public GerenteADM(int id, String nome, String cpf, String email, String telefone,
                     String login, String senha, String tipo, String matricula, String cargo, double salario, String statusPagamento) {
        super(id, nome, cpf, email, telefone, login, senha, "GerenteADM", matricula, cargo, salario, statusPagamento);
    }

    
}
