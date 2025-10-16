package projetoinfobus_versao2.model;

import java.time.LocalDate;
import java.math.BigDecimal;

public class Passagem {

    private int idPassagem;
    private int idPassageiro;
    private int idViagem;
    private LocalDate dataCompra;
    private BigDecimal valor;
    private String poltrona;
    private int plataforma;
    private String classe; // 'Econômica' ou 'Executiva'

    // Construtor vazio
    public Passagem() {}

    // Construtor antigo (compatibilidade)
    public Passagem(int idPassagem, int idPassageiro, LocalDate dataCompra, BigDecimal valor, String poltrona, int plataforma, String classe) {
        this.idPassagem = idPassagem;
        this.idPassageiro = idPassageiro;
        this.dataCompra = dataCompra;
        this.valor = valor;
        this.poltrona = poltrona;
        this.plataforma = plataforma;
        this.classe = classe;
    }

    // Novo construtor completo incluindo idViagem
    public Passagem(int idPassagem, int idPassageiro, int idViagem, LocalDate dataCompra, BigDecimal valor, String poltrona, int plataforma, String classe) {
        this.idPassagem = idPassagem;
        this.idPassageiro = idPassageiro;
        this.idViagem = idViagem;
        this.dataCompra = dataCompra;
        this.valor = valor;
        this.poltrona = poltrona;
        this.plataforma = plataforma;
        this.classe = classe;
    }

    // Getters e Setters
    public int getIdPassagem() { return idPassagem; }
    public void setIdPassagem(int idPassagem) { this.idPassagem = idPassagem; }

    public int getIdPassageiro() { return idPassageiro; }
    public void setIdPassageiro(int idPassageiro) { this.idPassageiro = idPassageiro; }

    public int getIdViagem() { return idViagem; }
    public void setIdViagem(int idViagem) { this.idViagem = idViagem; }

    public LocalDate getDataCompra() { return dataCompra; }
    public void setDataCompra(LocalDate dataCompra) { this.dataCompra = dataCompra; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public String getPoltrona() { return poltrona; }
    public void setPoltrona(String poltrona) { this.poltrona = poltrona; }

    public int getPlataforma() { return plataforma; }
    public void setPlataforma(int plataforma) { this.plataforma = plataforma; }

    public String getClasse() { return classe; }
    public void setClasse(String classe) { this.classe = classe; }
}
