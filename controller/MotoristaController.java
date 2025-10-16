package projetoinfobus_versao2.controller;

import projetoinfobus_versao2.dao.EscalaDAO;
import projetoinfobus_versao2.dao.MotoristaDAO;
import projetoinfobus_versao2.dao.OcorrenciaDAO;
import projetoinfobus_versao2.dao.ViagemDAO;
import projetoinfobus_versao2.model.Escala;
import projetoinfobus_versao2.model.Motorista;
import projetoinfobus_versao2.model.Ocorrencia;
import projetoinfobus_versao2.model.Viagem;

import java.util.List;

public class MotoristaController {

    private MotoristaDAO motoristaDAO;
    private EscalaDAO escalaDAO;
    private OcorrenciaDAO ocorrenciaDAO;
    private ViagemDAO viagemDAO;

    public MotoristaController() {
        motoristaDAO = new MotoristaDAO();
        escalaDAO = new EscalaDAO();
        ocorrenciaDAO = new OcorrenciaDAO();
        viagemDAO = new ViagemDAO();
    }

    // --- MOTORISTA CRUD ---
    public void cadastrarMotorista(Motorista m) {
        motoristaDAO.inserir(m);
    }

    public List<Motorista> listarMotoristas() {
        return motoristaDAO.listarTodos();
    }

    public void atualizarMotorista(Motorista m) {
        motoristaDAO.atualizar(m);
    }

    public void removerMotorista(int id) {
        motoristaDAO.deletar(id);
    }

    public Motorista buscarMotoristaPorId(int id) {
        return motoristaDAO.buscarPorId(id);
    }

    // --- CASOS DE USO ESPECÍFICOS ---

    // 🔹 Consultar escala do motorista logado
    public List<Escala> consultarEscala(Motorista motorista) {
        if (motorista == null) return null;
        return escalaDAO.buscarPorMotoristaId(motorista.getId()); // novo método filtrando por motorista
    }

    // 🔹 Consultar itinerário das viagens (todas as viagens da escala do motorista)
    public List<Viagem> consultarViagens(Motorista motorista) {
        if (motorista == null) return null;
        return viagemDAO.listarPorMotoristaId(motorista.getId()); // novo método filtrando por motorista
    }

    // 🔹 Registrar ocorrência
    public void registrarOcorrencia(Ocorrencia o) {
        /*if (motorista != null) {
            o.setIdMotorista(motorista.getId());
        }*/
        ocorrenciaDAO.inserir(o);
    }

    // 🔹 Listar ocorrências do motorista logado
    public List<Ocorrencia> listarOcorrencias(Motorista motorista) {
        if (motorista == null) return null;
        return ocorrenciaDAO.listarPorMotoristaId(motorista.getId()); // novo método
    }
}
