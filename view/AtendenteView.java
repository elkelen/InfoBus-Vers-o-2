package projetoinfobus_versao2.view;

import projetoinfobus_versao2.controller.AtendenteController;
import projetoinfobus_versao2.dao.ViagemDAO;
import projetoinfobus_versao2.model.Atendente;
import projetoinfobus_versao2.model.Usuario;
import projetoinfobus_versao2.model.Viagem;

import java.util.List;
import java.util.Scanner;
import projetoinfobus_versao2.model.Passagem;

public class AtendenteView {

    private final AtendenteController controller;
    private final Usuario usuarioLogado;
    private final Scanner scanner;

    public AtendenteView(Atendente atendente) {
        this.usuarioLogado = atendente; // O atendente logado atua como o "usuário"
        this.controller = new AtendenteController();
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n==============================");
            System.out.println("       MENU DO ATENDENTE      ");
            System.out.println("==============================");
            System.out.println("Bem-vindo(a), " + usuarioLogado.getNome());
            System.out.println("1 - Reservar Passagem");
            System.out.println("2 - Listar Passagens Vendidas");
            System.out.println("3 - Cancelar Venda de Passagem");
            System.out.println("4 - Emitir Relatório de Passagens Vendidas");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número válido.");
                continue;
            }

            switch (opcao) {
                case 1 ->
                    reservarPassagem();
                case 2 ->
                    listarPassagensVendidas();
                case 3 ->
                    cancelarVendaPassagem();
                case 4 ->
                    emitirRelatorioPassagens();
                case 0 ->
                    System.out.println("Saindo do menu do atendente...");
                default ->
                    System.out.println("Opção inválida, tente novamente.");
            }
        }
    }

    // ================================
    // Métodos correspondentes ao menu
    // ================================
    private void reservarPassagem() {
        System.out.println("\n=== Reservar Passagem ===");

        // listar viagens
        projetoinfobus_versao2.dao.ViagemDAO viagemDAO = new projetoinfobus_versao2.dao.ViagemDAO();
        List<projetoinfobus_versao2.model.Viagem> viagens = viagemDAO.listarTodos();

        if (viagens == null || viagens.isEmpty()) {
            System.out.println("❌ Nenhuma viagem disponível no momento.");
            return;
        }

        System.out.println("\n🚌 Viagens disponíveis:");
        for (projetoinfobus_versao2.model.Viagem v : viagens) {
            System.out.println("--------------------------------------------------");
            System.out.println("ID da Viagem: " + v.getIdViagem());
            System.out.println("Origem: " + (v.getItinerario() != null ? v.getItinerario().getOrigem() : "?"));
            System.out.println("Destino: " + (v.getItinerario() != null ? v.getItinerario().getDestino() : "?"));
            System.out.println("Data de Saída: " + v.getDataSaida() + " às " + v.getHorarioSaida());
            System.out.println("Data de Chegada: " + v.getDataChegada() + " às " + v.getHorarioChegada());
        }
        System.out.println("--------------------------------------------------");

        try {
            System.out.print("\nInforme o ID da viagem que deseja reservar: ");
            int idViagem = Integer.parseInt(scanner.nextLine());

            // solicitar id do passageiro (essencial para FK)
            System.out.print("Informe o ID do passageiro (ex: 4): ");
            int idPassageiro = Integer.parseInt(scanner.nextLine());

            // solicitar detalhes da passagem
            System.out.print("Informe o número da poltrona desejada (ex: 12A): ");
            String poltrona = scanner.nextLine();

            System.out.print("Informe a plataforma de embarque (número): ");
            int plataforma = Integer.parseInt(scanner.nextLine());

            System.out.print("Informe a classe (Econômica/Executiva): ");
            String classe = scanner.nextLine();

            System.out.print("Informe o valor da passagem (ex: 120.00): ");
            String valorTxt = scanner.nextLine();
            java.math.BigDecimal valor = new java.math.BigDecimal(valorTxt);

            // monta objeto Passagem
            Passagem passagem = new Passagem();
            passagem.setIdPassageiro(idPassageiro);
            passagem.setIdViagem(idViagem);
            passagem.setDataCompra(java.time.LocalDate.now());
            passagem.setPoltrona(poltrona);
            passagem.setPlataforma(plataforma);
            passagem.setClasse(classe);
            passagem.setValor(valor);

            // chama o controller (novo método que recebe Passagem)
            controller.reservarPassagem(usuarioLogado, passagem);

        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida: digite números válidos onde solicitado.");
        } catch (Exception e) {
            System.out.println("Erro ao processar reserva: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void listarPassagensVendidas() {
        System.out.println("\n=== Listar Passagens Vendidas ===");
        controller.listarPassagensVendidas(usuarioLogado);
    }

    private void cancelarVendaPassagem() {
        System.out.println("\n=== Cancelar Venda de Passagem ===");
        
        listarPassagensVendidas();

        try {
            System.out.print("Informe o ID da venda a ser cancelada: ");
            int idVenda = Integer.parseInt(scanner.nextLine());
            controller.cancelarVendaPassagem(usuarioLogado, idVenda);
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Digite um número válido para o ID da venda.");
        }
    }

    private void emitirRelatorioPassagens() {
        System.out.println("\n=== Emitir Relatório de Passagens Vendidas ===");
        controller.emitirRelatorioPassagens(usuarioLogado);
    }
}
