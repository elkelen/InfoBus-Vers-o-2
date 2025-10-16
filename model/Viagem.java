package projetoinfobus_versao2.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Viagem {

    private int idViagem;
    private LocalDate dataSaida;
    private LocalDate dataChegada;
    private LocalTime horarioSaida;
    private LocalTime horarioChegada;
    private Itinerario itinerario; 

    // Construtor vazio
    public Viagem() {}

    // Construtor completo
    public Viagem(int idViagem, LocalDate dataSaida, LocalDate dataChegada,
            LocalTime horarioSaida, LocalTime horarioChegada, Itinerario itinerario) {
        this.idViagem = idViagem;
        this.dataSaida = dataSaida;
        this.dataChegada = dataChegada;
        this.horarioSaida = horarioSaida;
        this.horarioChegada = horarioChegada;
        this.itinerario = itinerario;
    }

    // Getters e Setters
    public int getIdViagem() {
        return idViagem;
    }

    public void setIdViagem(int idViagem) {
        this.idViagem = idViagem;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public LocalDate getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(LocalDate dataChegada) {
        this.dataChegada = dataChegada;
    }

    public LocalTime getHorarioSaida() {
        return horarioSaida;
    }

    public void setHorarioSaida(LocalTime horarioSaida) {
        this.horarioSaida = horarioSaida;
    }

    public LocalTime getHorarioChegada() {
        return horarioChegada;
    }

    public void setHorarioChegada(LocalTime horarioChegada) {
        this.horarioChegada = horarioChegada;
    }
    
    public Itinerario getItinerario() {
        return itinerario;
    }

    public void setItinerario(Itinerario itinerario) {
        this.itinerario = itinerario;
    }
}
