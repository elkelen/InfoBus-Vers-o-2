package projetoinfobus_versao2.controller;

import projetoinfobus_versao2.dao.PassagemDAO;
import projetoinfobus_versao2.dao.ViagemDAO;
import projetoinfobus_versao2.model.Atendente;
import projetoinfobus_versao2.model.Passagem;
import projetoinfobus_versao2.model.Usuario;
import projetoinfobus_versao2.model.Viagem;
import projetoinfobus_versao2.dao.PassageiroDAO;
import projetoinfobus_versao2.dao.PassagemDAO;
import projetoinfobus_versao2.model.Passageiro;
import java.sql.SQLException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class AtendenteController {

    private final PassagemDAO passagemDAO;
    private final ViagemDAO viagemDAO;

    public AtendenteController() {
        this.passagemDAO = new PassagemDAO();
        this.viagemDAO = new ViagemDAO();
    }

    // ======================================
    // 1. Reservar passagem
    // ======================================
    public void reservarPassagem(Usuario usuarioLogado, Passagem passagem) {
        // valida se é atendente
        if (!(usuarioLogado instanceof Atendente)
                && (usuarioLogado.getTipo() == null || !usuarioLogado.getTipo().equalsIgnoreCase("Atendente"))) {
            System.out.println("Apenas atendentes podem reservar passagens.");
            return;
        }

        // validar viagem
        Viagem viagem = viagemDAO.buscarPorId(passagem.getIdViagem());
        if (viagem == null) {
            System.out.println("❌ Viagem não encontrada (ID: " + passagem.getIdViagem() + ").");
            return;
        }

        // validar passageiro
        PassageiroDAO passageiroDAO = new PassageiroDAO();
        Passageiro passageiro = passageiroDAO.buscarPorId(passagem.getIdPassageiro());
        if (passageiro == null) {
            System.out.println("❌ Passageiro não encontrado (ID: " + passagem.getIdPassageiro() + ").");
            return;
        }

        // (opcional) checar se poltrona já ocupada na viagem
        List<Passagem> vendidas = passagemDAO.listarPorViagem(passagem.getIdViagem());
        for (Passagem p : vendidas) {
            if (p.getPoltrona().equalsIgnoreCase(passagem.getPoltrona())) {
                System.out.println("❌ Poltrona " + passagem.getPoltrona() + " já está ocupada nessa viagem.");
                return;
            }
        }

        // tentar inserir (seu PassagemDAO.inserir é void e trata SQLException internamente).
        try {
            passagemDAO.inserir(passagem);
            System.out.println("\n✅ Passagem reservada com sucesso!");
            System.out.println("Passageiro: " + passageiro.getNome() + " (ID " + passageiro.getId() + ")");
            System.out.println("Viagem: " + viagem.getItinerario().getOrigem() + " → " + viagem.getItinerario().getDestino()
                    + " | Saída: " + viagem.getDataSaida() + " " + viagem.getHorarioSaida());
        } catch (Exception e) {
            // caso seu DAO capture e não reprograme exceção, esse catch pode não ser acionado.
            // ainda assim, mantemos para segurança se você mudar o DAO no futuro.
            System.out.println("❌ Erro ao reservar a passagem: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ======================================
    // 2. Listar passagens vendidas
    // ======================================
    public List<Passagem> listarPassagensVendidas(Usuario usuarioLogado) {
        if (!(usuarioLogado instanceof Atendente) && 
            (usuarioLogado.getTipo() == null || 
             !usuarioLogado.getTipo().equalsIgnoreCase("Atendente"))) {
            System.out.println("Apenas atendentes podem visualizar passagens vendidas.");
            return null;
        }

        List<Passagem> lista = passagemDAO.listarTodos();
        if (lista == null || lista.isEmpty()) {
            System.out.println("Nenhuma passagem vendida encontrada.");
            return lista;
        }

        System.out.println("\n=== LISTA DE PASSAGENS VENDIDAS ===");
        for (Passagem p : lista) {
            System.out.printf("ID: %d | Viagem: %d | Valor: R$ %.2f | Poltrona: %s | Classe: %s%n",
                    p.getIdPassagem(), p.getIdViagem(), p.getValor(), p.getPoltrona(), p.getClasse());
        }

        return lista;
    }

    // ======================================
    // 3. Cancelar venda de passagem
    // ======================================
    public void cancelarVendaPassagem(Usuario usuarioLogado, int idVenda) {
        if (!(usuarioLogado instanceof Atendente) && 
            (usuarioLogado.getTipo() == null || 
             !usuarioLogado.getTipo().equalsIgnoreCase("Atendente"))) {
            System.out.println("Apenas atendentes podem cancelar passagens.");
            
        }

        passagemDAO.deletar(idVenda);
        System.out.println("✅ Venda de passagem cancelada com sucesso!");
        
    }

    // ======================================
    // 4. Emitir relatório de passagens vendidas
    // ======================================
    public void emitirRelatorioPassagens(Usuario usuarioLogado) {
        if (!(usuarioLogado instanceof Atendente) && 
            (usuarioLogado.getTipo() == null || 
             !usuarioLogado.getTipo().equalsIgnoreCase("Atendente"))) {
            System.out.println("Apenas atendentes podem emitir relatórios.");
            return;
        }

        List<Passagem> passagens = passagemDAO.listarTodos();
        if (passagens == null || passagens.isEmpty()) {
            System.out.println("Nenhuma passagem foi vendida até o momento.");
            return;
        }

        BigDecimal total = BigDecimal.ZERO;
        for (Passagem p : passagens) {
            total = total.add(p.getValor());
        }

        System.out.println("\n=== RELATÓRIO DE PASSAGENS ===");
        System.out.println("Total de passagens vendidas: " + passagens.size());
        System.out.println("Valor total arrecadado: R$ " + total);
        System.out.println("Média por passagem: R$ " + total.divide(new BigDecimal(passagens.size()), 2));
    }

    // ======================================
    // 5. Listar viagens disponíveis (para exibir antes da reserva)
    // ======================================
    public void listarViagensDisponiveis() {
        List<Viagem> viagens = viagemDAO.listarTodos();

        if (viagens.isEmpty()) {
            System.out.println("Nenhuma viagem cadastrada no sistema.");
            return;
        }

        System.out.println("\n=== VIAGENS DISPONÍVEIS ===");
        for (Viagem v : viagens) {
            System.out.printf("ID: %d | Origem: %s | Destino: %s | Saída: %s | Chegada: %s%n",
                    v.getIdViagem(),
                    v.getItinerario().getOrigem(),
                    v.getItinerario().getDestino(),
                    v.getHorarioSaida(),
                    v.getHorarioChegada());
        }
    }
}
