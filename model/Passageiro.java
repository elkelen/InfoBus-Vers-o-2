package projetoinfobus_versao2.model;

import java.util.Date;

public class Passageiro extends Usuario {

    // Construtor vazio
    public Passageiro() {
        super();
    }

    // Construtor completo
    public Passageiro(int id, String nome, String cpf, String email, String telefone,
                      String login, String senha) {
        super(id, nome, cpf, email, telefone, login, senha, "Passageiro"); // tipo fixo como Passageiro
    }

    @Override
    public String toString() {
        return "Passageiro{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", telefone='" + getTelefone() + '\'' +
                '}';
    }
}
