package projetoinfobus_versao2.view;

import projetoinfobus_versao2.controller.MotoristaController;
import projetoinfobus_versao2.model.Escala;
import projetoinfobus_versao2.model.Motorista;
import projetoinfobus_versao2.model.Ocorrencia;
import projetoinfobus_versao2.model.Itinerario;
import projetoinfobus_versao2.model.Viagem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class MotoristaView {

    private final Motorista motorista;
    private final MotoristaController controller;
    private final Scanner sc;

    // Construtor
    public MotoristaView(Motorista motorista) {
        this.motorista = motorista;
        this.controller = new MotoristaController();
        this.sc = new Scanner(System.in);
    }

    // Exibe o menu principal do motorista
    public void exibirMenu() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n===== MENU DO MOTORISTA =====");
            System.out.println("1 - Consultar Minhas Escalas");
            System.out.println("2 - Registrar Ocorrência");
            System.out.println("3 - Listar Ocorrências Registradas");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1 ->
                    consultarEscalasDoMotorista();
                case 2 ->
                    registrarOcorrencia();
                case 3 ->
                    listarOcorrencias();
                case 0 ->
                    System.out.println("Saindo do menu do motorista...");
                default ->
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    // 1️⃣ Consultar escalas associadas ao motorista
    private void consultarEscalasDoMotorista() {
        List<Escala> escalas = controller.consultarEscala(motorista);

        if (escalas == null || escalas.isEmpty()) {
            System.out.println("Nenhuma escala associada a você foi encontrada.");
            return;
        }

        System.out.println("\n--- SUAS ESCALAS ---");
        for (Escala e : escalas) {
            Viagem v = e.getViagem();

            System.out.println("ID Escala: " + e.getIdEscala()
                    + " | Data: " + e.getDataEscala()
                    + " | Turno: " + e.getTurno()
                    + " | Status: " + e.getStatusEscala());

            if (v != null) {
                Itinerario it = v.getItinerario();
                System.out.println("→ Viagem #" + v.getIdViagem()
                        + " | Origem: " + (it != null ? it.getOrigem() : "N/D")
                        + " | Destino: " + (it != null ? it.getDestino() : "N/D")
                        + " | Saída: " + v.getHorarioSaida()
                        + " | Chegada: " + v.getHorarioChegada());
            }
            System.out.println("---------------------------------------------");
        }
    }

    // 2️⃣ Consultar itinerário de todas as viagens
    /*private void consultarViagens() {
        List<Viagem> viagens = controller.consultarViagens(motorista);

        if (viagens == null || viagens.isEmpty()) {
            System.out.println("Nenhuma viagem encontrada.");
            return;
        }

        System.out.println("\n--- ITINERÁRIO DE VIAGENS ---");
        for (Viagem v : viagens) {
            Itinerario it = v.getItinerario();
            System.out.println(
                    "ID Viagem: " + v.getIdViagem()
                    + " | Origem: " + (it != null ? it.getOrigem() : "N/D")
                    + " | Destino: " + (it != null ? it.getDestino() : "N/D")
                    + " | Distância: " + (it != null ? it.getDistanciaRota() + " km" : "N/D")
                    + " | Data Saída: " + v.getDataSaida()
                    + " | Data Chegada: " + v.getDataChegada()
                    + " | Horário Saída: " + v.getHorarioSaida()
                    + " | Horário Chegada: " + v.getHorarioChegada()
            );
        }
    }*/

    // 3️⃣ Registrar ocorrência
    private void registrarOcorrencia() {
        System.out.println("\n--- REGISTRAR OCORRÊNCIA ---");
        Ocorrencia o = new Ocorrencia();
        o.setDataHora(LocalDateTime.now());
        o.setMotorista(motorista);

        System.out.print("Descrição da ocorrência: ");
        o.setDescricao(sc.nextLine());

        System.out.print("Tipo (Ex: Mecânica, Atraso, Trânsito...): ");
        o.setTipo(sc.nextLine());

        System.out.print("Gravidade (Leve, Média, Grave): ");
        o.setGravidade(sc.nextLine());

        controller.registrarOcorrencia(o);
        System.out.println("Ocorrência registrada com sucesso!");
    }

    // 4️⃣ Listar ocorrências
    private void listarOcorrencias() {
        List<Ocorrencia> ocorrencias = controller.listarOcorrencias(motorista);

        if (ocorrencias == null || ocorrencias.isEmpty()) {
            System.out.println("Nenhuma ocorrência registrada.");
            return;
        }

        System.out.println("\n--- LISTA DE OCORRÊNCIAS ---");
        for (Ocorrencia o : ocorrencias) {
            System.out.println("ID: " + o.getIdOcorrencia()
                    + " | Tipo: " + o.getTipo()
                    + " | Gravidade: " + o.getGravidade()
                    + " | Data/Hora: " + o.getDataHora()
                    + "\nDescrição: " + o.getDescricao());
            System.out.println("---------------------------------");
        }
    }
}
