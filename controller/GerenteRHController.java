package projetoinfobus_versao2.controller;

import projetoinfobus_versao2.dao.FuncionarioDAO;
import projetoinfobus_versao2.dao.GerenteRHDAO;
import projetoinfobus_versao2.model.Funcionario;
import projetoinfobus_versao2.model.GerenteRH;

import java.util.List;

public class GerenteRHController {

    private GerenteRHDAO gerenteRHDAO;
    private FuncionarioDAO funcionarioDAO;

    public GerenteRHController() {
        this.gerenteRHDAO = new GerenteRHDAO();
        this.funcionarioDAO = new FuncionarioDAO();
    }

    // --- CADASTRAR FUNCIONÁRIO ---
    public void cadastrarFuncionario(Funcionario f) {
        funcionarioDAO.inserir(f);
        System.out.println("Funcionário cadastrado com sucesso!");
    }

    // --- REMOVER FUNCIONÁRIO ---
    public void removerFuncionario(int idFuncionario) {
        funcionarioDAO.deletar(idFuncionario);
        System.out.println("Funcionário removido com sucesso!");
    }

    // --- LISTAR FUNCIONÁRIOS ---
    public List<Funcionario> listarFuncionarios() {
        return funcionarioDAO.listarTodos();
    }

    // --- ATUALIZAR DADOS DE FUNCIONÁRIO ---
    public void atualizarFuncionario(Funcionario f) {
        funcionarioDAO.atualizar(f);
        System.out.println("Dados do funcionário atualizados com sucesso!");
    }

    // --- BUSCAR FUNCIONÁRIO POR ID ---
    public Funcionario buscarFuncionarioPorId(int idFuncionario) {
        return funcionarioDAO.buscarPorId(idFuncionario);
    }

    // --- SOLICITAR PAGAMENTO DE FUNCIONÁRIO ---
    public void solicitarPagamentoFuncionario(int idFuncionario) {
        Funcionario f = funcionarioDAO.buscarPorId(idFuncionario);
        if (f != null) {
            // Atualiza o status de pagamento para "Solicitado"
            funcionarioDAO.atualizarStatusPagamento(idFuncionario, "Solicitado");
            System.out.println("Pagamento solicitado para o funcionário: " + f.getNome());
        } else {
            System.out.println("Funcionário não encontrado.");
        }
    }

    // --- EMITIR RELATÓRIO DE FUNCIONÁRIOS ---
    public void emitirRelatorioFuncionarios() {
        List<Funcionario> funcionarios = funcionarioDAO.listarTodos();
        System.out.println("----- RELATÓRIO DE FUNCIONÁRIOS -----");
        for (Funcionario f : funcionarios) {
            System.out.println("ID: " + f.getId() +
                               " | Nome: " + f.getNome() +
                               " | Cargo: " + f.getCargo() +
                               " | Matrícula: " + f.getMatricula() +
                               " | Salário: " + f.getSalario());
        }
        System.out.println("------------------------------------");
    }

    // --- MÉTODOS ESPECÍFICOS PARA GERENTE RH ---
    public void cadastrarGerenteRH(GerenteRH g) {
        gerenteRHDAO.inserir(g);
        System.out.println("GerenteRH cadastrado com sucesso!");
    }

    public void atualizarGerenteRH(GerenteRH g) {
        gerenteRHDAO.atualizar(g);
        System.out.println("Dados do GerenteRH atualizados com sucesso!");
    }

    public void removerGerenteRH(int idGerenteRH) {
        gerenteRHDAO.deletar(idGerenteRH);
        System.out.println("GerenteRH removido com sucesso!");
    }

    public List<GerenteRH> listarGerentesRH() {
        return gerenteRHDAO.listarTodos();
    }

    public GerenteRH buscarGerenteRHPorId(int idGerenteRH) {
        return gerenteRHDAO.buscarPorId(idGerenteRH);
    }
}
