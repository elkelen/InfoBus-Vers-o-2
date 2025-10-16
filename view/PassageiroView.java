package projetoinfobus_versao2.view;

import projetoinfobus_versao2.controller.PassageiroController;
import projetoinfobus_versao2.model.Itinerario;
import projetoinfobus_versao2.model.Passagem;
import projetoinfobus_versao2.model.Passageiro;
import projetoinfobus_versao2.model.Viagem;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.math.BigDecimal;

public class PassageiroView {

    private PassageiroController passageiroController;
    private Passageiro passageiroLogado;
    private Scanner sc;

    public PassageiroView(Passageiro passageiroLogado) {
        this.passageiroController = new PassageiroController();
        this.passageiroLogado = passageiroLogado;
        this.sc = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n=== MENU DO PASSAGEIRO ===");
            System.out.println("1. Consultar Viagens Disponíveis");
            System.out.println("2. Consultar Histórico de Passagens");
            System.out.println("3. Cancelar Passagem");
            System.out.println("4. Atualizar Perfil");
            System.out.println("5. Sair para o menu principal");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer

            switch (opcao) {
                case 1:
                    consultarViagensDisponiveis();
                    break;
                case 2:
                    consultarHistoricoComRetorno();
                    break;
                case 3:
                    cancelarPassagem();
                    break;
                case 4:
                    atualizarPerfil();
                    break;
                case 5:
                    System.out.println("Retornando ao menu principal...");
                    new MainView().exibirMenuPrincipal();
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (true);
    }

    // --- CONSULTAR VIAGENS DISPONÍVEIS ---
    private void consultarViagensDisponiveis() {
        List<Viagem> viagens = passageiroController.consultarViagens();

        if (viagens.isEmpty()) {
            System.out.println("Nenhuma viagem disponível no momento!");
            return;
        }

        System.out.println("\n=== VIAGENS DISPONÍVEIS ===");
        for (Viagem v : viagens) {
            Itinerario it = v.getItinerario();

            System.out.println("ID Viagem: " + v.getIdViagem()
                    + " | Origem: " + (it != null ? it.getOrigem() : "N/D")
                    + " | Destino: " + (it != null ? it.getDestino() : "N/D")
                    + " | Data: " + v.getDataSaida()
                    + " | Horário: " + v.getHorarioSaida()
                    + " | Duração: " + (it != null ? it.getTempoPercurso() : "N/D"));
        }

        System.out.print("\nDeseja comprar passagem para uma viagem? (s/n): ");
        String resposta = sc.nextLine();
        if (resposta.equalsIgnoreCase("s")) {
            System.out.print("Informe o ID da viagem: ");
            int idViagem = sc.nextInt();
            sc.nextLine();

            BigDecimal precoEconomica = BigDecimal.valueOf(150.00);
            BigDecimal precoExecutiva = BigDecimal.valueOf(250.00);
            System.out.println("\nPreços (provisórios): Econômica = " + precoEconomica + " | Executiva = " + precoExecutiva);

            List<Passagem> vendidas = passageiroController.consultarPassagensPorViagem(idViagem);
            if (vendidas.isEmpty()) {
                System.out.println("Nenhuma poltrona vendida ainda para esta viagem.");
            } else {
                System.out.print("Poltronas já vendidas: ");
                for (Passagem p : vendidas) {
                    System.out.print(p.getPoltrona() + " ");
                }
                System.out.println();
            }

            List<Integer> plataformas = passageiroController.listarPlataformasPorViagem(idViagem);
            if (plataformas.isEmpty()) {
                System.out.println("Nenhuma plataforma registrada ainda para esta viagem.");
            } else {
                System.out.print("Plataformas registradas: ");
                for (Integer pl : plataformas) {
                    System.out.print(pl + " ");
                }
                System.out.println();
            }

            comprarPassagem(idViagem, precoEconomica, precoExecutiva);
        }
    }

    // --- COMPRAR PASSAGEM ---
    private void comprarPassagem(int idViagem, BigDecimal precoEconomica, BigDecimal precoExecutiva) {
        System.out.print("Informe o número da poltrona desejada: ");
        String poltrona = sc.nextLine();

        List<Passagem> vendidas = passageiroController.consultarPassagensPorViagem(idViagem);
        for (Passagem p : vendidas) {
            if (p.getPoltrona().equalsIgnoreCase(poltrona)) {
                System.out.println("Essa poltrona já está ocupada. Escolha outra.");
                return;
            }
        }

        System.out.print("Informe a plataforma de embarque: ");
        int plataforma = sc.nextInt();
        sc.nextLine();

        System.out.print("Informe a classe (Econômica/Executiva): ");
        String classe = sc.nextLine();

        Passagem passagem = new Passagem();
        passagem.setIdPassageiro(passageiroLogado.getId());
        passagem.setIdViagem(idViagem);
        passagem.setDataCompra(LocalDate.now());

        if (classe.equalsIgnoreCase("Executiva")) {
            passagem.setValor(precoExecutiva);
        } else {
            passagem.setValor(precoEconomica);
        }

        passagem.setPoltrona(poltrona);
        passagem.setPlataforma(plataforma);
        passagem.setClasse(classe);

        passageiroController.comprarPassagem(passagem);
        System.out.println("✅ Passagem comprada com sucesso!");
    }

    // --- HISTÓRICO DE PASSAGENS ---
    private boolean consultarHistoricoComRetorno() {
        List<Passagem> passagens = passageiroController.consultarHistoricoPassagens(passageiroLogado.getId());

        if (passagens.isEmpty()) {
            System.out.println("Nenhuma passagem encontrada em seu histórico!");
            return false;
        }

        System.out.println("\n=== HISTÓRICO DE PASSAGENS ===");
        for (Passagem p : passagens) {
            System.out.println("ID: " + p.getIdPassagem() +
                    " | Data da compra: " + p.getDataCompra() +
                    " | Valor: " + p.getValor() +
                    " | Poltrona: " + p.getPoltrona() +
                    " | Classe: " + p.getClasse());
        }
        return true;
    }

    // --- CANCELAR PASSAGEM ---
    private void cancelarPassagem() {
        System.out.println("\n=== CANCELAR PASSAGEM ===");
        System.out.println("Antes de cancelar, veja suas passagens compradas:");

        // ✅ Exibe o histórico e verifica se há passagens
        boolean temPassagens = consultarHistoricoComRetorno();

        if (!temPassagens) {
            System.out.println("Retornando ao menu do passageiro...");
            return; // volta automaticamente ao menu
        }

        System.out.print("\nInforme o ID da passagem que deseja cancelar: ");
        int idPassagem = sc.nextInt();
        sc.nextLine();

        passageiroController.cancelarPassagem(idPassagem);
        System.out.println("✅ Passagem cancelada com sucesso!");
    }

    // --- ATUALIZAR PERFIL ---
    private void atualizarPerfil() {
        System.out.println("\n=== ATUALIZAR PERFIL ===");
        System.out.print("Novo nome: ");
        String nome = sc.nextLine();
        System.out.print("Novo e-mail: ");
        String email = sc.nextLine();
        System.out.print("Nova senha: ");
        String senha = sc.nextLine();

        passageiroLogado.setNome(nome);
        passageiroLogado.setEmail(email);
        passageiroLogado.setSenha(senha);

        passageiroController.atualizarPerfil(passageiroLogado);
        System.out.println("✅ Perfil atualizado com sucesso!");
    }
}
