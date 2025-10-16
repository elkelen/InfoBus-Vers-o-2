package projetoinfobus_versao2.model;

import java.sql.Time;

public class Itinerario {

    private int idItinerario;
    private String origem;
    private String destino;
    private double distanciaRota;
    private Time tempoPercurso;

    // Construtor vazio
    public Itinerario() {}

    // Construtor completo
    public Itinerario(int idItinerario, String origem, String destino, double distanciaRota, Time tempoPercurso) {
        this.idItinerario = idItinerario;
        this.origem = origem;
        this.destino = destino;
        this.distanciaRota = distanciaRota;
        this.tempoPercurso = tempoPercurso;
    }

    // Getters e Setters
    public int getIdItinerario() {
        return idItinerario;
    }

    public void setIdItinerario(int idItinerario) {
        this.idItinerario = idItinerario;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getDistanciaRota() {
        return distanciaRota;
    }

    public void setDistanciaRota(double distanciaRota) {
        this.distanciaRota = distanciaRota;
    }

    public Time getTempoPercurso() {
        return tempoPercurso;
    }

    public void setTempoPercurso(Time tempoPercurso) {
        this.tempoPercurso = tempoPercurso;
    }
}
