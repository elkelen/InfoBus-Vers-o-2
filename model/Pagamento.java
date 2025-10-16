package projetoinfobus_versao2.model;

import java.time.LocalDate;
import java.math.BigDecimal;

public class Pagamento {

    private int idPagamento;
    private BigDecimal valor;
    private LocalDate dataPagamento;
    private int idFuncionario; // FK para Funcionario

    // Construtor vazio
    public Pagamento() {}

    // Construtor completo
    public Pagamento(int idPagamento, BigDecimal valor, LocalDate dataPagamento, int idFuncionario) {
        this.idPagamento = idPagamento;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.idFuncionario = idFuncionario;
    }

    // Getters e Setters
    public int getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(int idPagamento) {
        this.idPagamento = idPagamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }
}
