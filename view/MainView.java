package projetoinfobus_versao2.view;

import projetoinfobus_versao2.controller.PassageiroController;
import projetoinfobus_versao2.controller.UsuarioController;
import projetoinfobus_versao2.model.Passageiro;
import projetoinfobus_versao2.model.Usuario;
import projetoinfobus_versao2.controller.GerenteRHController;
import projetoinfobus_versao2.model.GerenteRH;
import projetoinfobus_versao2.model.Atendente;

import java.util.Scanner;
import projetoinfobus_versao2.model.Financeiro;
import projetoinfobus_versao2.model.GerenteADM;
import projetoinfobus_versao2.model.Motorista;

public class MainView {

    private Scanner sc;
    private UsuarioController usuarioController;
    private PassageiroController passageiroController;
    private GerenteRHController gerenteRHController;
    
    public MainView() {
        sc = new Scanner(System.in);
        usuarioController = new UsuarioController();
        passageiroController = new PassageiroController();
        gerenteRHController = new GerenteRHController();
    }
    
    public void exibirMenuPrincipal() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("===== SISTEMA DE PASSAGENS =====");
            System.out.println("1 - Realizar login");
            System.out.println("2 - Novo passageiro? Cadastre-se");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer

            switch (opcao) {
                case 1:
                    login();
                    break;
                case 2:
                    cadastrarPassageiro();
                    break;
                case 0:
                    System.out.println("Saindo do sistema.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void login() {
        System.out.println("===== LOGIN =====");
        System.out.print("Login (com CPF): ");
        String login = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        Usuario usuario = usuarioController.login(login, senha);

        if (usuario == null) {
            System.out.println("Login ou senha inválidos. Tente novamente.");
            return;
        }

        System.out.println("Redirecionando...");
        redirecionarParaView(usuario);
    }

    private void cadastrarPassageiro() {
        System.out.println("===== CADASTRO DE PASSAGEIRO =====");
        Passageiro p = new Passageiro();

        System.out.print("Nome: ");
        p.setNome(sc.nextLine());

        System.out.print("CPF: ");
        p.setCpf(sc.nextLine());

        System.out.print("Telefone: ");
        p.setTelefone(sc.nextLine());

        System.out.print("Email: ");
        p.setEmail(sc.nextLine());

        System.out.print("Login: ");
        p.setLogin(sc.nextLine());

        System.out.print("Senha: ");
        p.setSenha(sc.nextLine());

        // Tipo fixo como "Passageiro"
        p.setTipo("Passageiro");

        passageiroController.cadastrarPassageiro(p);
    }

    private void redirecionarParaView(Usuario usuario) {
        String tipo = usuario.getTipo();

        if (tipo.equalsIgnoreCase("Passageiro")) {
            System.out.println("Bem-vindo, passageiro(a) " + usuario.getNome());

            Passageiro p = new Passageiro();
            p.setId(usuario.getId());
            p.setNome(usuario.getNome());
            p.setEmail(usuario.getEmail());
            p.setSenha(usuario.getSenha());
            p.setCpf(usuario.getCpf());
            p.setTelefone(usuario.getTelefone());
            p.setTipo(usuario.getTipo());
            p.setLogin(usuario.getLogin());

            new PassageiroView(p).exibirMenu();
            return;
        }

        // Se for funcionário, buscamos o cargo
        if (tipo.equalsIgnoreCase("Funcionário") || tipo.equalsIgnoreCase("Funcionario")) {
            projetoinfobus_versao2.dao.FuncionarioDAO funcionarioDAO = new projetoinfobus_versao2.dao.FuncionarioDAO();
            projetoinfobus_versao2.model.Funcionario funcionario = funcionarioDAO.buscarPorId(usuario.getId());

            if (funcionario == null) {
                System.out.println("Erro: Funcionário não encontrado no banco de dados.");
                return;
            }

            String cargo = funcionario.getCargo();

            if (cargo == null) {
                System.out.println("Cargo não definido para este funcionário. Contate o administrador.");
                return;
            }

            System.out.println("Bem-vindo, " + cargo + " " + usuario.getNome());

            switch (cargo) {
                case "Gerente RH":
                    GerenteRH g = new GerenteRH();
                    g.setId(funcionario.getId());
                    g.setNome(funcionario.getNome());
                    g.setCpf(funcionario.getCpf());
                    g.setTelefone(funcionario.getTelefone());
                    g.setEmail(funcionario.getEmail());
                    g.setLogin(funcionario.getLogin());
                    g.setSenha(funcionario.getSenha());
                    g.setTipo(funcionario.getTipo());
                    g.setCargo(funcionario.getCargo());
                    // adicione outros campos se houver

                    new GerenteRHView(g).exibirMenu();
                    break;

                case "Motorista":
                    Motorista m = new Motorista();
                    m.setId(funcionario.getId());
                    m.setNome(funcionario.getNome());
                    m.setCpf(funcionario.getCpf());
                    m.setTelefone(funcionario.getTelefone());
                    m.setEmail(funcionario.getEmail());
                    m.setLogin(funcionario.getLogin());
                    m.setSenha(funcionario.getSenha());
                    m.setTipo(funcionario.getTipo());
                    m.setCargo(funcionario.getCargo());
                    m.setCnh(funcionario instanceof Motorista ? ((Motorista) funcionario).getCnh() : null);

                    new MotoristaView(m).exibirMenu();
                    break;


                case "Atendente":
                        Atendente a = new Atendente();
                        a.setId(funcionario.getId());
                        a.setNome(funcionario.getNome());
                        a.setCpf(funcionario.getCpf());
                        a.setTelefone(funcionario.getTelefone());
                        a.setEmail(funcionario.getEmail());
                        a.setLogin(funcionario.getLogin());
                        a.setSenha(funcionario.getSenha());
                        a.setTipo(funcionario.getTipo());
                        a.setCargo(funcionario.getCargo());
                        
                        new AtendenteView(a).exibirMenu();
                        break;
                    
                case "Financeiro":
                    Financeiro f = new Financeiro();
                    f.setId(funcionario.getId());
                    f.setNome(funcionario.getNome());
                    f.setCpf(funcionario.getCpf());
                    f.setTelefone(funcionario.getTelefone());
                    f.setEmail(funcionario.getEmail());
                    f.setLogin(funcionario.getLogin());
                    f.setSenha(funcionario.getSenha());
                    f.setTipo(funcionario.getTipo());
                    f.setCargo(funcionario.getCargo());

                    new FinanceiroView(f).exibirMenu();
                    break;
                
                case "Gerente ADM":
                    GerenteADM adm = new GerenteADM();
                    adm.setId(funcionario.getId());
                    adm.setNome(funcionario.getNome());
                    adm.setCpf(funcionario.getCpf());
                    adm.setTelefone(funcionario.getTelefone());
                    adm.setEmail(funcionario.getEmail());
                    adm.setLogin(funcionario.getLogin());
                    adm.setSenha(funcionario.getSenha());
                    adm.setTipo(funcionario.getTipo());
                    adm.setCargo(funcionario.getCargo());

                    new GerenteADMView(adm).exibirMenu();
                    break;


                default:
                    System.out.println("Cargo não reconhecido: " + cargo);
            }
        } else {
            System.out.println("Tipo de usuário desconhecido. Contate o administrador.");
        }
    }


    // --- MAIN ---
    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");
        System.setOut(new java.io.PrintStream(System.out, true, java.nio.charset.StandardCharsets.UTF_8));
        
        MainView mainView = new MainView();
        mainView.exibirMenuPrincipal();
    }
}
