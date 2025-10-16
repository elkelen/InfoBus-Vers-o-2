package projetoinfobus_versao2.model;

import java.time.LocalDate;

public class Escala {

    private int idEscala;
    private Motorista motorista;   // referência para motorista
    private Viagem viagem;
    private LocalDate dataEscala;
    private String turno; // Matutino, Vespertino, Noturno
    private String statusEscala;

    // Construtor vazio
    public Escala() {}

    // Construtor completo
    public Escala(int idEscala, Motorista motorista, Viagem viagem, LocalDate dataEscala, String turno, String statusEscala) {
        this.idEscala = idEscala;
        this.motorista = motorista;
        this.viagem = viagem;
        this.dataEscala = dataEscala;
        this.turno = turno;
        this.statusEscala = statusEscala;
    }

    // Getters e Setters
    public int getIdEscala() {
        return idEscala;
    }

    public void setIdEscala(int idEscala) {
        this.idEscala = idEscala;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public Viagem getViagem() {
        return viagem;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }
    
    public LocalDate getDataEscala() {
        return dataEscala;
    }

    public void setDataEscala(LocalDate dataEscala) {
        this.dataEscala = dataEscala;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getStatusEscala() {
        return statusEscala;
    }

    public void setStatusEscala(String statusEscala) {
        this.statusEscala = statusEscala;
    }
}
