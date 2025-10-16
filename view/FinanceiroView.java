package projetoinfobus_versao2.view;

import projetoinfobus_versao2.controller.FinanceiroController;
import projetoinfobus_versao2.model.Despesa;
import projetoinfobus_versao2.model.Pagamento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import projetoinfobus_versao2.model.Financeiro;

public class FinanceiroView {

    private FinanceiroController financeiroController;
    private Scanner sc;

    public FinanceiroView(Financeiro financeiro) {
        financeiroController = new FinanceiroController();
        sc = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n===== MENU FINANCEIRO =====");
            System.out.println("1 - Gerenciar Despesas");
            System.out.println("2 - Gerenciar Pagamentos");
            System.out.println("3 - Emitir Relatório Financeiro");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer

            switch (opcao) {
                case 1:
                    exibirMenuDespesas();
                    break;
                case 2:
                    exibirMenuPagamentos();
                    break;
                case 3:
                    financeiroController.emitirRelatorioFinanceiro();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("⚠️ Opção inválida. Tente novamente.");
            }
        }
    }

    private void exibirMenuDespesas() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- GERENCIAR DESPESAS ---");
            System.out.println("1 - Cadastrar Despesa");
            System.out.println("2 - Listar Despesas");
            System.out.println("3 - Alterar Despesa");
            System.out.println("4 - Remover Despesa");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarDespesa();
                    break;
                case 2:
                    listarDespesas();
                    break;
                case 3:
                    alterarDespesa();
                    break;
                case 4:
                    removerDespesa();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("⚠️ Opção inválida.");
            }
        }
    }

    private void exibirMenuPagamentos() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- GERENCIAR PAGAMENTOS ---");
            System.out.println("1 - Cadastrar Pagamento");
            System.out.println("2 - Listar Pagamentos");
            System.out.println("3 - Alterar Pagamento");
            System.out.println("4 - Remover Pagamento");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarPagamento();
                    break;
                case 2:
                    listarPagamentos();
                    break;
                case 3:
                    alterarPagamento();
                    break;
                case 4:
                    removerPagamento();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("⚠️ Opção inválida.");
            }
        }
    }

    // --- DESPESAS ---
    private void cadastrarDespesa() {
        Despesa d = new Despesa();
        System.out.print("Descrição: ");
        d.setDescricao(sc.nextLine());
        System.out.print("Valor: ");
        d.setValor(sc.nextBigDecimal());
        sc.nextLine();
        System.out.print("Data (YYYY-MM-DD): ");
        d.setDataDespesa(LocalDate.parse(sc.nextLine()));

        financeiroController.cadastrarDespesa(d);
        System.out.println("Despesa cadastrada com sucesso!");
    }

    private void listarDespesas() {
        List<Despesa> despesas = financeiroController.listarDespesas();
        System.out.println("\n--- LISTA DE DESPESAS ---");
        for (Despesa d : despesas) {
            System.out.println("ID: " + d.getIdDespesa() + " | Valor: " + d.getValor() +
                    " | Data: " + d.getDataDespesa() + " | Descrição: " + d.getDescricao());
        }
    }

    private void alterarDespesa() {
        listarDespesas();
        
        System.out.print("\nID da despesa que deseja alterar: ");
        int id = sc.nextInt();
        sc.nextLine();

        Despesa d = financeiroController.listarDespesas()
                .stream()
                .filter(des -> des.getIdDespesa() == id)
                .findFirst()
                .orElse(null);

        if (d == null) {
            System.out.println("Despesa não encontrada.");
            return;
        }

        System.out.print("Nova descrição: ");
        d.setDescricao(sc.nextLine());
        System.out.print("Novo valor: ");
        d.setValor(sc.nextBigDecimal());
        sc.nextLine();
        System.out.print("Nova data (YYYY-MM-DD): ");
        d.setDataDespesa(LocalDate.parse(sc.nextLine()));

        financeiroController.atualizarDespesa(d);
        System.out.println("Despesa alterada com sucesso!");
    }

    private void removerDespesa() {
        listarDespesas();
        
        System.out.print("\nID da despesa que deseja remover: ");
        int id = sc.nextInt();
        sc.nextLine();

        financeiroController.removerDespesa(id);
        System.out.println("Despesa removida com sucesso!");
    }

    // --- PAGAMENTOS ---
    private void cadastrarPagamento() {
        Pagamento p = new Pagamento();
        System.out.print("ID do funcionário: ");
        p.setIdFuncionario(sc.nextInt());
        sc.nextLine();
        System.out.print("Valor do pagamento: ");
        p.setValor(sc.nextBigDecimal());
        sc.nextLine();
        System.out.print("Data do pagamento (YYYY-MM-DD): ");
        p.setDataPagamento(LocalDate.parse(sc.nextLine()));

        financeiroController.cadastrarPagamento(p);
        System.out.println("Pagamento cadastrado com sucesso!");
    }

    private void listarPagamentos() {
        List<Pagamento> pagamentos = financeiroController.listarPagamentos();
        System.out.println("\n--- LISTA DE PAGAMENTOS ---");
        for (Pagamento p : pagamentos) {
            System.out.println("ID: " + p.getIdPagamento() + " | Valor: " + p.getValor() +
                    " | Data: " + p.getDataPagamento() + " | Funcionário: " + p.getIdFuncionario());
        }
    }

    private void alterarPagamento() {
        listarPagamentos();
                
        System.out.print("ID do pagamento que deseja alterar: ");
        int id = sc.nextInt();
        sc.nextLine();

        Pagamento p = financeiroController.listarPagamentos()
                .stream()
                .filter(pg -> pg.getIdPagamento() == id)
                .findFirst()
                .orElse(null);

        if (p == null) {
            System.out.println("Pagamento não encontrado.");
            return;
        }

        System.out.print("Novo ID do funcionário: ");
        p.setIdFuncionario(sc.nextInt());
        sc.nextLine();
        System.out.print("Novo valor do pagamento: ");
        p.setValor(sc.nextBigDecimal());
        sc.nextLine();
        System.out.print("Nova data do pagamento (YYYY-MM-DD): ");
        p.setDataPagamento(LocalDate.parse(sc.nextLine()));

        financeiroController.atualizarPagamento(p);
        System.out.println("Pagamento alterado com sucesso!");
    }

    private void removerPagamento() {
        listarPagamentos();
        
        System.out.print("ID do pagamento que deseja remover: ");
        int id = sc.nextInt();
        sc.nextLine();

        financeiroController.removerPagamento(id);
        System.out.println("Pagamento removido com sucesso!");
    }
}
