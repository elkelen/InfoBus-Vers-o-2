package projetoinfobus_versao2.controller;

import projetoinfobus_versao2.dao.ViagemDAO;
import projetoinfobus_versao2.dao.PassageiroDAO;
import projetoinfobus_versao2.dao.PassagemDAO;
import projetoinfobus_versao2.model.Viagem;
import projetoinfobus_versao2.model.Passageiro;
import projetoinfobus_versao2.model.Passagem;

import java.util.List;

public class PassageiroController {

    private PassageiroDAO passageiroDAO;
    private ViagemDAO viagemDAO;
    private PassagemDAO passagemDAO;

    public PassageiroController() {
        this.passageiroDAO = new PassageiroDAO();
        this.viagemDAO = new ViagemDAO();
        this.passagemDAO = new PassagemDAO();
    }

    // --- CADASTRAR PASSAGEIRO ---
    public void cadastrarPassageiro(Passageiro p) {
        passageiroDAO.inserir(p);
        System.out.println("Passageiro cadastrado com sucesso!");
    }

    // --- CONSULTAR VIAGENS ---
    public List<Viagem> consultarViagens() {
        return viagemDAO.listarTodos();
    }

    public Viagem consultarViagemPorId(int id) {
        return viagemDAO.buscarPorId(id);
    }

    // --- COMPRAR PASSAGEM ---
    public void comprarPassagem(Passagem p) {
        passagemDAO.inserir(p);
        System.out.println("Passagem comprada com sucesso!");
    }

    // --- CANCELAR PASSAGEM ---
    public void cancelarPassagem(int idPassagem) {
        passagemDAO.deletar(idPassagem);
        System.out.println("Passagem cancelada com sucesso!");
    }

    // --- CONSULTAR HISTÓRICO DE PASSAGENS ---
    public List<Passagem> consultarHistoricoPassagens(int idPassageiro) {
        // Agora utiliza o método direto do DAO
        return passagemDAO.listarPorPassageiro(idPassageiro);
    }

    public Passagem consultarPassagemPorId(int id) {
        return passagemDAO.buscarPorId(id);
    }

    // --- LISTAR TODOS OS PASSAGEIROS (opcional, caso admin queira) ---
    public List<Passageiro> listarPassageiros() {
        return passageiroDAO.listarTodos();
    }

    public Passageiro buscarPassageiroPorId(int id) {
        return passageiroDAO.buscarPorId(id);
    }
    
    public void atualizarPerfil(Passageiro passageiro) {
        PassageiroDAO passageiroDAO = new PassageiroDAO();
        passageiroDAO.atualizar(passageiro);
    }
    
    // no passageiro controller
    public List<Passagem> consultarPassagensPorViagem(int idViagem) {
        return passagemDAO.listarPorViagem(idViagem);
    }

    public List<Integer> listarPlataformasPorViagem(int idViagem) {
        return passagemDAO.listarPlataformasPorViagem(idViagem);
    }

}
