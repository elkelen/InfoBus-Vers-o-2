package projetoinfobus_versao2.model;

import java.time.LocalDateTime;

public class Ocorrencia {

    private int idOcorrencia;
    private LocalDateTime dataHora;
    private String descricao;
    private String tipo;
    private String gravidade;
    private int idMotorista;
    private Motorista motorista;

    // Construtor vazio
    public Ocorrencia() {}

    // Construtor completo
    public Ocorrencia(int idOcorrencia, LocalDateTime dataHora, String descricao, String tipo, String gravidade) {
        this.idOcorrencia = idOcorrencia;
        this.dataHora = dataHora;
        this.descricao = descricao;
        this.tipo = tipo;
        this.gravidade = gravidade;
    }

    // Getters e Setters
    public int getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(int idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getGravidade() {
        return gravidade;
    }

    public void setGravidade(String gravidade) {
        this.gravidade = gravidade;
    }

    public int getIdMotorista() {
        return idMotorista;
    }

    public void setIdMotorista(int idMotorista) {
        this.idMotorista = idMotorista;
    }
    
    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    
}
