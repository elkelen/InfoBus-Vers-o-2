package projetoinfobus_versao2.controller;

import projetoinfobus_versao2.model.Usuario;
import projetoinfobus_versao2.dao.UsuarioDAO;

import java.util.List;

public class UsuarioController {

    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    // --- CADASTRAR USUÁRIO ---
    // Método genérico — pode ser usado por qualquer View
    public boolean cadastrarUsuario(Usuario novoUsuario, Usuario usuarioLogado) {
        // Caso seja auto-cadastro (sem usuário logado)
        if (usuarioLogado == null) {
            if (!"Passageiro".equalsIgnoreCase(novoUsuario.getTipo())) {
                System.out.println("Auto-cadastro só é permitido para Passageiros.");
                return false;
            }
            usuarioDAO.inserir(novoUsuario);
            System.out.println("Usuário cadastrado com sucesso!");
            return true;
        }

        // Caso seja um cadastro interno (com login no sistema)
        if ("GerenteRH".equalsIgnoreCase(usuarioLogado.getTipo())) {
            usuarioDAO.inserir(novoUsuario);
            System.out.println("Novo usuário cadastrado com sucesso pelo GerenteRH!");
            return true;
        }

        // Bloqueia outros tipos de usuário
        System.out.println("Apenas GerenteRH pode cadastrar novos funcionários.");
        return false;
    }

    // Método opcional para auto-cadastro (atalho para View pública)
    public boolean autoCadastrarUsuario(Usuario novoUsuario) {
        return cadastrarUsuario(novoUsuario, null);
    }
    
    // --- ATUALIZAR USUÁRIO ---
    public boolean atualizarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getId() == 0) {
            System.out.println("Erro: usuário inválido.");
            return false;
        }

        usuarioDAO.atualizar(usuario);
        System.out.println("Usuário atualizado com sucesso!");
        return true;
    }

    // --- REMOVER USUÁRIO ---
    public boolean removerUsuario(int id, Usuario usuarioLogado) {
        // Validação opcional de permissão
        if (usuarioLogado == null || !usuarioLogado.getTipo().equalsIgnoreCase("GerenteRH")) {
            System.out.println("Acesso negado: apenas GerenteRH pode remover usuários.");
            return false;
        }

        usuarioDAO.deletar(id);
        System.out.println("Usuário removido com sucesso!");
        return true;
    }

    // --- BUSCAR USUÁRIO POR ID ---
    public Usuario buscarUsuarioPorId(int id, Usuario usuarioLogado) {
        // Apenas GerenteRH ou o próprio usuário pode consultar detalhes
        Usuario usuarioBuscado = usuarioDAO.buscarPorId(id);
        if (usuarioBuscado == null) {
            System.out.println("Usuário não encontrado.");
            return null;
        }

        if (usuarioLogado.getTipo().equalsIgnoreCase("GerenteRH") ||
            usuarioBuscado.getId() == usuarioLogado.getId()) {
            return usuarioBuscado;
        }

        System.out.println("Acesso negado: você não pode visualizar este usuário.");
        return null;
    }

    // --- LISTAR TODOS OS USUÁRIOS ---
    public List<Usuario> listarUsuarios(Usuario usuarioLogado) {
        if (usuarioLogado == null || !usuarioLogado.getTipo().equalsIgnoreCase("GerenteRH")) {
            System.out.println("Acesso negado: apenas GerenteRH pode listar usuários.");
            return null;
        }

        return usuarioDAO.listarTodos();
    }

    // --- LOGIN / AUTENTICAÇÃO ---
    public Usuario login(String login, String senha) {
        List<Usuario> usuarios = usuarioDAO.listarTodos();

        for (Usuario u : usuarios) {
            if (u.getLogin().equalsIgnoreCase(login) && u.getSenha().equals(senha)) {
                System.out.println("Login realizado com sucesso! Bem-vindo, " + u.getNome());
                return u;
            }
        }

        System.out.println("Login ou senha inválidos.");
        return null;
    }
}
