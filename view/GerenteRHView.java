package projetoinfobus_versao2.view;

import projetoinfobus_versao2.controller.GerenteRHController;
import projetoinfobus_versao2.model.Funcionario;
import projetoinfobus_versao2.model.GerenteRH;

import java.util.List;
import java.util.Scanner;

public class GerenteRHView {
    private GerenteRH gerenteRH;
    private GerenteRHController controller;
    private Scanner scanner;

    public GerenteRHView(GerenteRH gerenteRH) {
        this.gerenteRH = gerenteRH;
        this.controller = new GerenteRHController();
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- MENU GERENTE RH ---");
            System.out.println("1. Cadastrar funcionário");
            System.out.println("2. Remover funcionário");
            System.out.println("3. Consultar funcionários");
            System.out.println("4. Atualizar dados do funcionário");
            System.out.println("5. Solicitar pagamento de funcionário");
            System.out.println("6. Emitir relatório de funcionários");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (opcao) {
                case 1:
                    cadastrarFuncionario();
                    break;
                case 2:
                    removerFuncionario();
                    break;
                case 3:
                    consultarFuncionarios();
                    break;
                case 4:
                    atualizarFuncionario();
                    break;
                case 5:
                    solicitarPagamento();
                    break;
                case 6:
                    emitirRelatorio();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrarFuncionario() {
        Funcionario f = new Funcionario();

        System.out.print("Nome: ");
        f.setNome(scanner.nextLine());

        System.out.print("CPF: ");
        f.setCpf(scanner.nextLine());

        System.out.print("Telefone: ");
        f.setTelefone(scanner.nextLine());

        System.out.print("Email: ");
        f.setEmail(scanner.nextLine());

        System.out.print("Senha: ");
        f.setSenha(scanner.nextLine());

        f.setTipo("Funcionário");

        System.out.print("Matrícula: ");
        f.setMatricula(scanner.nextLine());

        System.out.print("Cargo: ");
        f.setCargo(scanner.nextLine());

        System.out.print("Salário: ");
        f.setSalario(scanner.nextDouble());
        scanner.nextLine(); // limpar buffer

        f.setStatusPagamento("Pendente");

        controller.cadastrarFuncionario(f);
    }

    private void removerFuncionario() {
        System.out.println("\n--- Lista de Funcionários ---");
        List<Funcionario> funcionarios = controller.listarFuncionarios();

        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado!");
            return;
        }

        for (Funcionario f : funcionarios) {
            System.out.println("ID: " + f.getId()
                    + " | Nome: " + f.getNome()
                    + " | Cargo: " + f.getCargo()
                    + " | Matrícula: " + f.getMatricula()
                    + " | Salário: " + f.getSalario()
                    + " | Status Pagamento: " + f.getStatusPagamento());
        }

        System.out.print("\nInforme o ID do funcionário que deseja remover: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Funcionario funcionario = controller.buscarFuncionarioPorId(id);
        if (funcionario == null) {
            System.out.println("❌ Funcionário não encontrado. Operação cancelada.");
            return;
        }

        System.out.print("Tem certeza que deseja remover " + funcionario.getNome() + "? (s/n): ");
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("s")) {
            controller.removerFuncionario(id);
            System.out.println("✅ Funcionário removido com sucesso!");
        } else {
            System.out.println("Remoção cancelada.");
        }
    }

    private void consultarFuncionarios() {
        List<Funcionario> funcionarios = controller.listarFuncionarios();
        System.out.println("\n--- Lista de Funcionários ---");
        for (Funcionario f : funcionarios) {
            System.out.println("ID: " + f.getId() +
                               " | Nome: " + f.getNome() +
                               " | Cargo: " + f.getCargo() +
                               " | Matrícula: " + f.getMatricula() +
                               " | Salário: " + f.getSalario() +
                               " | Status Pagamento: " + f.getStatusPagamento());
        }
    }

    private void atualizarFuncionario() {
        System.out.println("\n--- Lista de Funcionários ---");
        List<Funcionario> funcionarios = controller.listarFuncionarios();

        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado!");
            return;
        }

        for (Funcionario f : funcionarios) {
            System.out.println("ID: " + f.getId()
                    + " | Nome: " + f.getNome()
                    + " | Cargo: " + f.getCargo()
                    + " | Matrícula: " + f.getMatricula()
                    + " | Salário: " + f.getSalario()
                    + " | Status Pagamento: " + f.getStatusPagamento());
        }

        System.out.print("\nInforme o ID do funcionário que deseja atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Funcionario f = controller.buscarFuncionarioPorId(id);
        if (f == null) {
            System.out.println("❌ Funcionário não encontrado. Operação cancelada.");
            return;
        }

        System.out.print("Novo nome (" + f.getNome() + "): ");
        String input = scanner.nextLine();
        if (!input.isEmpty()) {
            f.setNome(input);
        }

        System.out.print("Novo CPF (" + f.getCpf() + "): ");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            f.setCpf(input);
        }

        System.out.print("Novo telefone (" + f.getTelefone() + "): ");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            f.setTelefone(input);
        }

        System.out.print("Novo email (" + f.getEmail() + "): ");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            f.setEmail(input);
        }

        System.out.print("Nova senha (oculta): ");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            f.setSenha(input);
        }

        System.out.print("Nova matrícula (" + f.getMatricula() + "): ");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            f.setMatricula(input);
        }

        System.out.print("Novo cargo (" + f.getCargo() + "): ");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            f.setCargo(input);
        }

        System.out.print("Novo salário (" + f.getSalario() + "): ");
        String salarioInput = scanner.nextLine();
        if (!salarioInput.isEmpty()) {
            f.setSalario(Double.parseDouble(salarioInput));
        }

        controller.atualizarFuncionario(f);
        System.out.println("✅ Funcionário atualizado com sucesso!");
    }


    private void solicitarPagamento() {
        System.out.print("ID do funcionário para solicitar pagamento: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        controller.solicitarPagamentoFuncionario(id);
    }

    private void emitirRelatorio() {
        controller.emitirRelatorioFuncionarios();
    }

}
