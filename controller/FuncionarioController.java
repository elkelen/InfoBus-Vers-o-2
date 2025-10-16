package projetoinfobus_versao2.controller;

import projetoinfobus_versao2.dao.FuncionarioDAO;
import projetoinfobus_versao2.model.Funcionario;

public class FuncionarioController {

    private FuncionarioDAO funcionarioDAO;

    public FuncionarioController() {
        this.funcionarioDAO = new FuncionarioDAO();
    }

    // --- ATUALIZAR PRÓPRIOS DADOS ---
    public boolean atualizarFuncionario(Funcionario funcionario, int idFuncionarioLogado) {
        if (funcionario.getId() != idFuncionarioLogado) {
            System.out.println("Acesso negado: só é permitido atualizar seus próprios dados.");
            return false;
        }

        funcionarioDAO.atualizar(funcionario);
        System.out.println("Dados atualizados com sucesso!");
        return true;
    }

    // --- CONSULTAR PRÓPRIO PERFIL ---
    public Funcionario consultarPerfil(int idFuncionarioLogado) {
        return funcionarioDAO.buscarPorId(idFuncionarioLogado);
    }
}
