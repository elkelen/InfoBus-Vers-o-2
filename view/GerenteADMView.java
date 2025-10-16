package projetoinfobus_versao2.view;

import projetoinfobus_versao2.controller.GerenteADMController;
import projetoinfobus_versao2.model.GerenteADM;
import projetoinfobus_versao2.model.Itinerario;
import projetoinfobus_versao2.model.Onibus;
import projetoinfobus_versao2.model.Viagem;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class GerenteADMView {

    private GerenteADM gerente;
    private GerenteADMController controller;
    private Scanner sc;

    public GerenteADMView(GerenteADM gerente) {
        this.gerente = gerente;
        this.controller = new GerenteADMController();
        this.sc = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n===== MENU GERENTE ADMINISTRATIVO =====");
            System.out.println("1 - Gerenciar Itinerários");
            System.out.println("2 - Gerenciar Viagens");
            System.out.println("3 - Gerenciar Ônibus");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> menuItinerario();
                case 2 -> menuViagem();
                case 3 -> menuOnibus();
                case 0 -> System.out.println("Saindo do menu do gerente administrativo...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    // ========================== ITINERÁRIO ==========================
    private void menuItinerario() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- GERENCIAR ITINERÁRIOS ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Remover");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> cadastrarItinerario();
                case 2 -> listarItinerarios();
                case 3 -> atualizarItinerario();
                case 4 -> removerItinerario();
                case 0 -> {}
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrarItinerario() {
        Itinerario i = new Itinerario();
        System.out.println("=== CADASTRAR ITINERÁRIO ===");
        System.out.print("Origem: ");
        i.setOrigem(sc.nextLine());
        System.out.print("Destino: ");
        i.setDestino(sc.nextLine());
        System.out.print("Distância (km): ");
        i.setDistanciaRota(sc.nextDouble());
        sc.nextLine();
        System.out.print("Tempo de percurso (HH:MM:SS): ");
        i.setTempoPercurso(Time.valueOf(sc.nextLine()));

        controller.cadastrarItinerario(i);
        System.out.println("Itinerário cadastrado com sucesso!");
    }

    private void listarItinerarios() {
        System.out.println("=== LISTA DE ITINERÁRIOS ===");
        List<Itinerario> lista = controller.listarItinerarios();
        for (Itinerario i : lista) {
            System.out.printf("[%d] %s -> %s | %.1f km | Tempo: %s\n",
                    i.getIdItinerario(), i.getOrigem(), i.getDestino(), i.getDistanciaRota(), i.getTempoPercurso());
        }
    }

    private void atualizarItinerario() {
        listarItinerarios();
        System.out.print("Informe o ID do itinerário que deseja atualizar: ");
        int id = sc.nextInt();
        sc.nextLine();

        Itinerario i = new Itinerario();
        i.setIdItinerario(id);

        System.out.print("Nova origem: ");
        i.setOrigem(sc.nextLine());
        System.out.print("Novo destino: ");
        i.setDestino(sc.nextLine());
        System.out.print("Nova distância (km): ");
        i.setDistanciaRota(sc.nextDouble());
        sc.nextLine();
        System.out.print("Novo tempo de percurso (HH:MM:SS): ");
        i.setTempoPercurso(Time.valueOf(sc.nextLine()));

        controller.atualizarItinerario(i);
        System.out.println("Itinerário atualizado!");
    }

    private void removerItinerario() {
        listarItinerarios();
        System.out.print("Informe o ID do itinerário a remover: ");
        int id = sc.nextInt();
        sc.nextLine();

        controller.removerItinerario(id);
        System.out.println("Itinerário removido!");
    }

    // ========================== VIAGEM ==========================
    private void menuViagem() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- GERENCIAR VIAGENS ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Remover");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> cadastrarViagem();
                case 2 -> listarViagens();
                case 3 -> atualizarViagem();
                case 4 -> removerViagem();
                case 0 -> {}
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrarViagem() {
        Viagem v = new Viagem();
        System.out.println("=== CADASTRAR VIAGEM ===");

        // Lista os itinerários antes de escolher
        List<Itinerario> itinerarios = controller.listarItinerarios();
        if (itinerarios.isEmpty()) {
            System.out.println("⚠️ Nenhum itinerário cadastrado! Cadastre um itinerário antes de criar uma viagem.");
            return;
        }

        System.out.println("\n--- ITINERÁRIOS DISPONÍVEIS ---");
        for (Itinerario i : itinerarios) {
            System.out.printf("[%d] %s -> %s | Distância: %.1f km | Tempo: %s\n",
                    i.getIdItinerario(), i.getOrigem(), i.getDestino(), i.getDistanciaRota(), i.getTempoPercurso());
        }

        System.out.print("\nInforme o ID do itinerário para associar à viagem: ");
        int idItinerario = sc.nextInt();
        sc.nextLine();

        // Cria objeto itinerário e associa à viagem
        Itinerario itinerario = new Itinerario();
        itinerario.setIdItinerario(idItinerario);
        v.setItinerario(itinerario);

        // Informações da viagem
        System.out.print("Data de saída (AAAA-MM-DD): ");
        v.setDataSaida(LocalDate.parse(sc.nextLine()));
        System.out.print("Data de chegada (AAAA-MM-DD): ");
        v.setDataChegada(LocalDate.parse(sc.nextLine()));
        System.out.print("Horário de saída (HH:MM): ");
        v.setHorarioSaida(LocalTime.parse(sc.nextLine()));
        System.out.print("Horário de chegada (HH:MM): ");
        v.setHorarioChegada(LocalTime.parse(sc.nextLine()));

        controller.cadastrarViagem(v);
        System.out.println("✅ Viagem cadastrada com sucesso!");
    }


    private void listarViagens() {
        System.out.println("=== LISTA DE VIAGENS ===");
        List<Viagem> lista = controller.listarViagens();
        for (Viagem v : lista) {
            System.out.printf("[%d] %s %s -> %s %s | Itinerário: %s -> %s\n",
                    v.getIdViagem(),
                    v.getDataSaida(), v.getHorarioSaida(),
                    v.getDataChegada(), v.getHorarioChegada(),
                    (v.getItinerario() != null ? v.getItinerario().getOrigem() : "?"),
                    (v.getItinerario() != null ? v.getItinerario().getDestino() : "?"));
        }
    }

    private void atualizarViagem() {
        listarViagens();
        System.out.print("Informe o ID da viagem que deseja atualizar: ");
        int id = sc.nextInt();
        sc.nextLine();

        // Buscar a viagem existente
        Viagem v = controller.buscarViagemPorId(id);
        if (v == null) {
            System.out.println("Viagem não encontrada!");
            return;
        }

        // Lista os itinerários disponíveis
        List<Itinerario> itinerarios = controller.listarItinerarios();
        if (!itinerarios.isEmpty()) {
            System.out.println("\n--- ITINERÁRIOS DISPONÍVEIS ---");
            for (Itinerario i : itinerarios) {
                System.out.printf("[%d] %s -> %s | Distância: %.1f km | Tempo: %s\n",
                        i.getIdItinerario(), i.getOrigem(), i.getDestino(), i.getDistanciaRota(), i.getTempoPercurso());
            }

            System.out.print("\nInforme o ID do itinerário para associar à viagem (ou mesmo do atual): ");
            int idItinerario = sc.nextInt();
            sc.nextLine();

            Itinerario itinerario = new Itinerario();
            itinerario.setIdItinerario(idItinerario);
            v.setItinerario(itinerario);
        }

        // Atualizar dados da viagem
        System.out.print("Nova data de saída (AAAA-MM-DD): ");
        v.setDataSaida(LocalDate.parse(sc.nextLine()));
        System.out.print("Nova data de chegada (AAAA-MM-DD): ");
        v.setDataChegada(LocalDate.parse(sc.nextLine()));
        System.out.print("Novo horário de saída (HH:MM): ");
        v.setHorarioSaida(LocalTime.parse(sc.nextLine()));
        System.out.print("Novo horário de chegada (HH:MM): ");
        v.setHorarioChegada(LocalTime.parse(sc.nextLine()));

        controller.atualizarViagem(v);
        System.out.println("✅ Viagem atualizada com sucesso!");
    }


    private void removerViagem() {
        listarViagens();
        System.out.print("Informe o ID da viagem a remover: ");
        int id = sc.nextInt();
        sc.nextLine();

        controller.removerViagem(id);
        System.out.println("Viagem removida!");
    }

    // ========================== ÔNIBUS ==========================
    private void menuOnibus() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- GERENCIAR ÔNIBUS ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Remover");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> cadastrarOnibus();
                case 2 -> listarOnibus();
                case 3 -> atualizarOnibus();
                case 4 -> removerOnibus();
                case 0 -> {}
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrarOnibus() {
        Onibus o = new Onibus();
        System.out.println("=== CADASTRAR ÔNIBUS ===");
        System.out.print("Placa: ");
        o.setPlaca(sc.nextLine());
        System.out.print("Modelo: ");
        o.setModelo(sc.nextLine());
        System.out.print("Capacidade: ");
        o.setCapacidade(sc.nextInt());
        sc.nextLine();
        System.out.print("Classe (Econômica/Executiva): ");
        o.setClasse(sc.nextLine());
        System.out.print("Fabricante: ");
        o.setFabricante(sc.nextLine());
        System.out.print("ID do motorista: ");
        o.setIdMotorista(sc.nextInt());
        System.out.print("ID do itinerário: ");
        o.setIdItinerario(sc.nextInt());
        sc.nextLine();

        controller.cadastrarOnibus(o);
        System.out.println("Ônibus cadastrado com sucesso!");
    }

    private void listarOnibus() {
        System.out.println("=== LISTA DE ÔNIBUS ===");
        List<Onibus> lista = controller.listarOnibus();
        for (Onibus o : lista) {
            System.out.printf("[%d] Placa: %s | Modelo: %s | Capacidade: %d | Classe: %s | Fabricante: %s | Motorista ID: %d | Itinerário ID: %d\n",
                    o.getIdOnibus(), o.getPlaca(), o.getModelo(), o.getCapacidade(),
                    o.getClasse(), o.getFabricante(), o.getIdMotorista(), o.getIdItinerario());
        }
    }

    private void atualizarOnibus() {
        listarOnibus();
        System.out.print("Informe o ID do ônibus que deseja atualizar: ");
        int id = sc.nextInt();
        sc.nextLine();

        Onibus o = new Onibus();
        o.setIdOnibus(id);

        System.out.print("Nova placa: ");
        o.setPlaca(sc.nextLine());
        System.out.print("Novo modelo: ");
        o.setModelo(sc.nextLine());
        System.out.print("Nova capacidade: ");
        o.setCapacidade(sc.nextInt());
        sc.nextLine();
        System.out.print("Nova classe (Econômica/Executiva): ");
        o.setClasse(sc.nextLine());
        System.out.print("Novo fabricante: ");
        o.setFabricante(sc.nextLine());
        System.out.print("Novo ID do motorista: ");
        o.setIdMotorista(sc.nextInt());
        System.out.print("Novo ID do itinerário: ");
        o.setIdItinerario(sc.nextInt());
        sc.nextLine();

        controller.atualizarOnibus(o);
        System.out.println("Ônibus atualizado!");
    }

    private void removerOnibus() {
        listarOnibus();
        System.out.print("Informe o ID do ônibus a remover: ");
        int id = sc.nextInt();
        sc.nextLine();

        controller.removerOnibus(id);
        System.out.println("Ônibus removido!");
    }
}
