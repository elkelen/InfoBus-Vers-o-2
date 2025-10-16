package projetoinfobus_versao2.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Despesa {

    private int idDespesa;
    private BigDecimal valor;
    private LocalDate dataDespesa;
    private String descricao;

    // Construtor vazio
    public Despesa() {}

    // Construtor completo
    public Despesa(int idDespesa, BigDecimal valor, LocalDate dataDespesa, String descricao) {
        this.idDespesa = idDespesa;
        this.valor = valor;
        this.dataDespesa = dataDespesa;
        this.descricao = descricao;
    }

    // Getters e Setters
    public int getIdDespesa() {
        return idDespesa;
    }

    public void setIdDespesa(int idDespesa) {
        this.idDespesa = idDespesa;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataDespesa() {
        return dataDespesa;
    }

    public void setDataDespesa(LocalDate dataDespesa) {
        this.dataDespesa = dataDespesa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

