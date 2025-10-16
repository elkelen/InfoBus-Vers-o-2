package projetoinfobus_versao2.model;

public class Onibus {

    private int idOnibus;
    private String placa;
    private String modelo;
    private int capacidade;
    private String classe; // "Econômica" ou "Executiva"
    private String fabricante;
    private int idMotorista;
    private int idItinerario;

    // Construtor vazio
    public Onibus() {}

    // Construtor completo
    public Onibus(int idOnibus, String placa, String modelo, int capacidade, String classe, String fabricante, int idMotorista, int idItinerario) {
        this.idOnibus = idOnibus;
        this.placa = placa;
        this.modelo = modelo;
        this.capacidade = capacidade;
        this.classe = classe;
        this.fabricante = fabricante;
        this.idMotorista = idMotorista;
        this.idItinerario = idItinerario;
    }

    // Getters e Setters
    public int getIdOnibus() {
        return idOnibus;
    }

    public void setIdOnibus(int idOnibus) {
        this.idOnibus = idOnibus;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public int getIdMotorista() {
        return idMotorista;
    }

    public void setIdMotorista(int idMotorista) {
        this.idMotorista = idMotorista;
    }

    public int getIdItinerario() {
        return idItinerario;
    }

    public void setIdItinerario(int idItinerario) {
        this.idItinerario = idItinerario;
    }
}
