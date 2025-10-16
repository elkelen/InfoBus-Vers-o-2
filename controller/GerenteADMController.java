package projetoinfobus_versao2.controller;

import projetoinfobus_versao2.dao.ItinerarioDAO;
import projetoinfobus_versao2.dao.ViagemDAO;
import projetoinfobus_versao2.dao.OnibusDAO;
import projetoinfobus_versao2.model.Itinerario;
import projetoinfobus_versao2.model.Viagem;
import projetoinfobus_versao2.model.Onibus;

import java.util.List;

public class GerenteADMController {

    private ItinerarioDAO itinerarioDAO;
    private ViagemDAO viagemDAO;
    private OnibusDAO onibusDAO;

    public GerenteADMController() {
        this.itinerarioDAO = new ItinerarioDAO();
        this.viagemDAO = new ViagemDAO();
        this.onibusDAO = new OnibusDAO();
    }

    // --- ITINERÁRIO ---
    public void cadastrarItinerario(Itinerario i) {
        itinerarioDAO.inserir(i);
    }

    public List<Itinerario> listarItinerarios() {
        return itinerarioDAO.listarTodos();
    }

    public void atualizarItinerario(Itinerario i) {
        itinerarioDAO.atualizar(i);
    }

    public void removerItinerario(int id) {
        itinerarioDAO.deletar(id);
    }

    // --- VIAGEM ---
    public void cadastrarViagem(Viagem v) {
        viagemDAO.inserir(v);
    }

    public List<Viagem> listarViagens() {
        return viagemDAO.listarTodos();
    }

    public void atualizarViagem(Viagem v) {
        viagemDAO.atualizar(v);
    }

    public void removerViagem(int id) {
        viagemDAO.deletar(id);
    }
    
    public Viagem buscarViagemPorId(int id) {
        return viagemDAO.buscarPorId(id);
    }


    // --- ÔNIBUS ---
    public void cadastrarOnibus(Onibus o) {
        onibusDAO.inserir(o);
    }

    public List<Onibus> listarOnibus() {
        return onibusDAO.listarTodos();
    }

    public void atualizarOnibus(Onibus o) {
        onibusDAO.atualizar(o);
    }

    public void removerOnibus(int id) {
        onibusDAO.deletar(id);
    }
}
