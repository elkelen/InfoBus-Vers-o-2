package projetoinfobus_versao2.controller;

import projetoinfobus_versao2.dao.FinanceiroDAO;
import projetoinfobus_versao2.model.Financeiro;
import projetoinfobus_versao2.model.Despesa;
import projetoinfobus_versao2.model.Pagamento;

import java.util.List;

public class FinanceiroController {

    private FinanceiroDAO financeiroDAO;

    public FinanceiroController() {
        financeiroDAO = new FinanceiroDAO();
    }

    // --- FINANCEIROS ---
    public void cadastrarFinanceiro(Financeiro f) {
        financeiroDAO.inserir(f);
    }

    public List<Financeiro> listarFinanceiros() {
        return financeiroDAO.listarTodos();
    }

    public void atualizarFinanceiro(Financeiro f) {
        financeiroDAO.atualizar(f);
    }

    public void removerFinanceiro(int id) {
        financeiroDAO.deletar(id);
    }

    public Financeiro buscarFinanceiroPorId(int id) {
        return financeiroDAO.buscarPorId(id);
    }

    // --- DESPESAS ---
    public void cadastrarDespesa(Despesa d) {
        // chama DAO de Despesa (supondo que exista DespesaDAO)
        new projetoinfobus_versao2.dao.DespesaDAO().inserir(d);
    }

    public List<Despesa> listarDespesas() {
        return new projetoinfobus_versao2.dao.DespesaDAO().listarTodos();
    }

    public void atualizarDespesa(Despesa d) {
        new projetoinfobus_versao2.dao.DespesaDAO().atualizar(d);
    }

    public void removerDespesa(int id) {
        new projetoinfobus_versao2.dao.DespesaDAO().deletar(id);
    }

    // --- PAGAMENTOS ---
    public void cadastrarPagamento(Pagamento p) {
        new projetoinfobus_versao2.dao.PagamentoDAO().inserir(p);
    }

    public List<Pagamento> listarPagamentos() {
        return new projetoinfobus_versao2.dao.PagamentoDAO().listarTodos();
    }

    public void atualizarPagamento(Pagamento p) {
        new projetoinfobus_versao2.dao.PagamentoDAO().atualizar(p);
    }

    public void removerPagamento(int id) {
        new projetoinfobus_versao2.dao.PagamentoDAO().deletar(id);
    }

    // --- RELATÓRIO FINANCEIRO ---
    public void emitirRelatorioFinanceiro() {
        // Exemplo simples: imprime lista de despesas e pagamentos
        System.out.println("===== RELATÓRIO FINANCEIRO =====");
        List<Despesa> despesas = listarDespesas();
        System.out.println("\n--- DESPESAS ---");
        for (Despesa d : despesas) {
            System.out.println("ID: " + d.getIdDespesa() + " | Valor: " + d.getValor()
                    + " | Data: " + d.getDataDespesa() + " | Descrição: " + d.getDescricao());
        }
        List<Pagamento> pagamentos = listarPagamentos();
        System.out.println("\n--- PAGAMENTOS ---");
        for (Pagamento p : pagamentos) {
            System.out.println("ID: " + p.getIdPagamento() + " | Valor: " + p.getValor()
                    + " | Data: " + p.getDataPagamento() + " | Funcionário: " + p.getIdFuncionario());
        }
    }
}
