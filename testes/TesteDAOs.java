package projetoinfobus_versao2.testes;

import projetoinfobus_versao2.dao.*;
import projetoinfobus_versao2.model.*;
import java.util.List;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;

public class TesteDAOs {
    public static void main(String[] args) {
        // --- Atendente ---
        System.out.println("=== ATENDENTES ===");
        AtendenteDAO atendenteDAO = new AtendenteDAO();
        List<Atendente> atendentes = atendenteDAO.listarTodos();
        if (atendentes.isEmpty()) System.out.println("Nenhum atendente encontrado.");
        else atendentes.forEach(a -> System.out.println(a.getId() + " | " + a.getNome() + " | " + a.getCargo() + " | R$ " + a.getSalario()));

        // --- Despesa ---
        System.out.println("\n=== DESPESAS ===");
        DespesaDAO despesaDAO = new DespesaDAO();
        List<Despesa> despesas = despesaDAO.listarTodos();
        if (despesas.isEmpty()) System.out.println("Nenhuma despesa encontrada.");
        else despesas.forEach(d -> System.out.println(d.getIdDespesa() + " | " + d.getDescricao() + " | R$ " + d.getValor() + " | " + d.getDataDespesa()));

        // --- Escala ---
        System.out.println("\n=== ESCALAS ===");
        EscalaDAO escalaDAO = new EscalaDAO();
        List<Escala> escalas = escalaDAO.listarTodos();
        if (escalas.isEmpty()) System.out.println("Nenhuma escala encontrada.");
        else escalas.forEach(e -> System.out.println(e.getIdEscala() + " | " + e.getDataEscala() + " | " + e.getTurno() + " | " + e.getStatusEscala()));

        // --- Funcionário ---
        System.out.println("\n=== FUNCIONÁRIOS ===");
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        List<Funcionario> funcionarios = funcionarioDAO.listarTodos();
        if (funcionarios.isEmpty()) System.out.println("Nenhum funcionário encontrado.");
        else funcionarios.forEach(f -> System.out.println(f.getId() + " | " + f.getNome() + " | " + f.getCargo() + " | R$ " + f.getSalario()));

        // --- GerenteADM ---
        System.out.println("\n=== GERENTES ADMINISTRATIVOS ===");
        GerenteADMDAO gerenteADMDAO = new GerenteADMDAO();
        List<GerenteADM> gerentesADM = gerenteADMDAO.listarTodos();
        if (gerentesADM.isEmpty()) System.out.println("Nenhum gerente administrativo encontrado.");
        else gerentesADM.forEach(g -> System.out.println(g.getId() + " | " + g.getNome() + " | " + g.getCargo() + " | R$ " + g.getSalario()));

        // --- GerenteRH ---
        System.out.println("\n=== GERENTES RH ===");
        GerenteRHDAO gerenteRHDAO = new GerenteRHDAO();
        List<GerenteRH> gerentesRH = gerenteRHDAO.listarTodos();
        if (gerentesRH.isEmpty()) System.out.println("Nenhum gerente RH encontrado.");
        else gerentesRH.forEach(g -> System.out.println(g.getId() + " | " + g.getNome() + " | " + g.getCargo() + " | R$ " + g.getSalario()));

        // --- Itinerário ---
        System.out.println("\n=== ITINERÁRIOS ===");
        ItinerarioDAO itinerarioDAO = new ItinerarioDAO();
        List<Itinerario> itinerarios = itinerarioDAO.listarTodos();
        if (itinerarios.isEmpty()) System.out.println("Nenhum itinerário encontrado.");
        else itinerarios.forEach(i -> System.out.println(i.getIdItinerario() + " | " + i.getOrigem() + " -> " + i.getDestino() + " | " + i.getDistanciaRota() + " km | Tempo: " + i.getTempoPercurso()));

        // --- Motorista ---
        System.out.println("\n=== MOTORISTAS ===");
        MotoristaDAO motoristaDAO = new MotoristaDAO();
        List<Motorista> motoristas = motoristaDAO.listarTodos();
        if (motoristas.isEmpty()) System.out.println("Nenhum motorista encontrado.");
        else motoristas.forEach(m -> System.out.println(m.getId() + " | " + m.getNome() + " | " + m.getCargo() + " | R$ " + m.getSalario()));

        // --- Ocorrência ---
        System.out.println("\n=== OCORRÊNCIAS ===");
        OcorrenciaDAO ocorrenciaDAO = new OcorrenciaDAO();
        List<Ocorrencia> ocorrencias = ocorrenciaDAO.listarTodos();
        if (ocorrencias.isEmpty()) System.out.println("Nenhuma ocorrência encontrada.");
        else ocorrencias.forEach(o -> System.out.println(o.getIdOcorrencia() + " | " + o.getDescricao() + " | " + o.getTipo() + " | Gravidade: " + o.getGravidade() + " | " + o.getDataHora()));

        // --- Ônibus ---
        System.out.println("\n=== ÔNIBUS ===");
        OnibusDAO onibusDAO = new OnibusDAO();
        List<Onibus> onibus = onibusDAO.listarTodos();
        if (onibus.isEmpty()) System.out.println("Nenhum ônibus encontrado.");
        else onibus.forEach(o -> System.out.println(o.getIdOnibus() + " | " + o.getPlaca() + " | " + o.getModelo() + " | Capacidade: " + o.getCapacidade() + " | Classe: " + o.getClasse()));

        // --- Pagamento ---
        System.out.println("\n=== PAGAMENTOS ===");
        PagamentoDAO pagamentoDAO = new PagamentoDAO();
        List<Pagamento> pagamentos = pagamentoDAO.listarTodos();
        if (pagamentos.isEmpty()) System.out.println("Nenhum pagamento encontrado.");
        else pagamentos.forEach(p -> System.out.println(p.getIdPagamento() + " | " + p.getValor() + " | " + p.getDataPagamento() + " | Funcionario ID: " + p.getIdFuncionario()));

        // --- Passageiro ---
        System.out.println("\n=== PASSAGEIROS ===");
        PassageiroDAO passageiroDAO = new PassageiroDAO();
        List<Passageiro> passageiros = passageiroDAO.listarTodos();
        if (passageiros.isEmpty()) System.out.println("Nenhum passageiro encontrado.");
        else passageiros.forEach(p -> System.out.println(p.getId() + " | " + p.getNome() + " | " + p.getCpf() + " | " + p.getEmail()));

        // --- Passagem ---
        System.out.println("\n=== PASSAGENS ===");
        PassagemDAO passagemDAO = new PassagemDAO();
        List<Passagem> passagens = passagemDAO.listarTodos();
        if (passagens.isEmpty()) System.out.println("Nenhuma passagem encontrada.");
        else passagens.forEach(p -> System.out.println(p.getIdPassagem() + " | " + p.getPoltrona() + " | R$ " + p.getValor() + " | " + p.getClasse() + " | Plataforma: " + p.getPlataforma() + " | Data: " + p.getDataCompra()));

        // --- Usuário ---
        System.out.println("\n=== USUÁRIOS ===");
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        if (usuarios.isEmpty()) System.out.println("Nenhum usuário encontrado.");
        else usuarios.forEach(u -> System.out.println(u.getId() + " | " + u.getNome() + " | " + u.getCpf() + " | " + u.getEmail()));

        // --- Viagem ---
        System.out.println("\n=== VIAGENS ===");
        ViagemDAO viagemDAO = new ViagemDAO();
        List<Viagem> viagens = viagemDAO.listarTodos();
        if (viagens.isEmpty()) System.out.println("Nenhuma viagem encontrada.");
        else viagens.forEach(v -> System.out.println(v.getIdViagem() + " | " + v.getDataSaida() + " -> " + v.getDataChegada() + " | Saída: " + v.getHorarioSaida() + " | Chegada: " + v.getHorarioChegada()));
    }
}
