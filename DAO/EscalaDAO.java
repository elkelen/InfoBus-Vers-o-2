package projetoinfobus_versao2.dao;

import projetoinfobus_versao2.db.DB;
import projetoinfobus_versao2.model.Escala;
import projetoinfobus_versao2.model.Motorista;
import projetoinfobus_versao2.model.Viagem;
import projetoinfobus_versao2.model.Itinerario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EscalaDAO {

    // --- INSERIR ESCALA ---
    public void inserir(Escala e) {
        String sql = "INSERT INTO escala (data_escala, turno, statusEscala, idMotorista, idViagem) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(e.getDataEscala()));
            ps.setString(2, e.getTurno());
            ps.setString(3, e.getStatusEscala());
            ps.setInt(4, e.getMotorista().getId());
            ps.setInt(5, e.getViagem().getIdViagem());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    e.setIdEscala(rs.getInt(1));
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // --- LISTAR TODAS AS ESCALAS ---
    public List<Escala> listarTodos() {
        List<Escala> lista = new ArrayList<>();
        String sql = "SELECT e.idEscala, e.data_escala, e.turno, e.statusEscala, "
                + "m.idFuncionario AS idMotorista, u.nome AS nomeMotorista, u.cpf, u.email, u.telefone, "
                + "u.cpf AS login, u.senha, m.matricula, m.cargo, m.salario, m.statusPagamento, m.cnh, "
                + "v.idViagem, v.dataSaida, v.dataChegada, i.idItinerario, i.origem, i.destino "
                + "FROM escala e "
                + "JOIN funcionario m ON e.idMotorista = m.idFuncionario "
                + "JOIN usuario u ON m.idFuncionario = u.idUsuario "
                + "JOIN viagem v ON e.idViagem = v.idViagem "
                + "LEFT JOIN itinerario i ON v.idItinerario = i.idItinerario";



        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Motorista
                Motorista motorista = new Motorista();
                motorista.setId(rs.getInt("idMotorista"));
                motorista.setNome(rs.getString("nomeMotorista"));
                motorista.setCpf(rs.getString("cpf"));
                motorista.setEmail(rs.getString("email"));
                motorista.setTelefone(rs.getString("telefone"));
                motorista.setLogin(rs.getString("cpf"));
                motorista.setSenha(rs.getString("senha"));
                motorista.setMatricula(rs.getString("matricula"));
                motorista.setCargo(rs.getString("cargo"));
                motorista.setSalario(rs.getDouble("salario"));
                motorista.setStatusPagamento(rs.getString("statusPagamento"));
                motorista.setCnh(rs.getString("cnh"));
                motorista.setTipo("Motorista");

                // Itinerário (opcional)
                Itinerario itinerario = null;
                int idItinerario = rs.getInt("idItinerario");
                if (!rs.wasNull()) {
                    itinerario = new Itinerario();
                    itinerario.setIdItinerario(idItinerario);
                    itinerario.setOrigem(rs.getString("origem"));
                    itinerario.setDestino(rs.getString("destino"));
                }

                // Viagem
                Viagem viagem = new Viagem();
                viagem.setIdViagem(rs.getInt("idViagem"));
                viagem.setDataSaida(rs.getDate("dataSaida").toLocalDate());
                viagem.setDataChegada(rs.getDate("dataChegada").toLocalDate());
                viagem.setItinerario(itinerario);

                // Escala
                Escala e = new Escala();
                e.setIdEscala(rs.getInt("idEscala"));
                e.setDataEscala(rs.getDate("data_escala").toLocalDate());
                e.setTurno(rs.getString("turno"));
                e.setStatusEscala(rs.getString("statusEscala"));
                e.setMotorista(motorista);
                e.setViagem(viagem);

                lista.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    // --- ATUALIZAR ESCALA ---
    public void atualizar(Escala e) {
        String sql = "UPDATE escala SET data_escala=?, turno=?, statusEscala=?, idMotorista=?, idViagem=? WHERE idEscala=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(e.getDataEscala()));
            ps.setString(2, e.getTurno());
            ps.setString(3, e.getStatusEscala());
            ps.setInt(4, e.getMotorista().getId());
            ps.setInt(5, e.getViagem().getIdViagem());
            ps.setInt(6, e.getIdEscala());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // --- DELETAR ESCALA ---
    public void deletar(int id) {
        String sql = "DELETE FROM escala WHERE idEscala=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    // --- BUSCAR ESCALAS POR MOTORISTA ---
    public List<Escala> buscarPorMotoristaId(int idMotorista) {
        List<Escala> escalas = new ArrayList<>();
        String sql = "SELECT e.idEscala, e.data_escala, e.turno, e.statusEscala, "
                + "m.idFuncionario AS idMotorista, m.matricula, m.cargo, m.salario, m.statusPagamento, "
                + "u.nome AS nomeMotorista, u.cpf, u.email, u.telefone, u.senha, "
                + "mot.cnh, "
                + "v.idViagem, v.dataSaida, v.dataChegada, v.horarioSaida, v.horarioChegada, "
                + "i.idItinerario, i.origem, i.destino, i.distanciaRota, i.tempoPercurso "
                + "FROM escala e "
                + "JOIN funcionario m ON e.idMotorista = m.idFuncionario "
                + "JOIN usuario u ON m.idFuncionario = u.idUsuario "
                + "LEFT JOIN motorista mot ON m.idFuncionario = mot.idMotorista "
                + "JOIN viagem v ON e.idViagem = v.idViagem "
                + "LEFT JOIN itinerario i ON v.idItinerario = i.idItinerario "
                + "WHERE e.idMotorista = ?";



        try (Connection conn = DB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idMotorista);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Motorista
                    Motorista motorista = new Motorista();
                    motorista.setId(rs.getInt("idMotorista"));
                    motorista.setNome(rs.getString("nomeMotorista"));
                    motorista.setCpf(rs.getString("cpf"));
                    motorista.setEmail(rs.getString("email"));
                    motorista.setTelefone(rs.getString("telefone"));
                    motorista.setLogin(rs.getString("cpf"));
                    motorista.setSenha(rs.getString("senha"));
                    motorista.setMatricula(rs.getString("matricula"));
                    motorista.setCargo(rs.getString("cargo"));
                    motorista.setSalario(rs.getDouble("salario"));
                    motorista.setStatusPagamento(rs.getString("statusPagamento"));
                    motorista.setCnh(rs.getString("cnh"));
                    motorista.setTipo("Motorista");

                    // Itinerário
                    Itinerario itinerario = null;
                    int idItinerario = rs.getInt("idItinerario");
                    if (!rs.wasNull()) {
                        itinerario = new Itinerario();
                        itinerario.setIdItinerario(idItinerario);
                        itinerario.setOrigem(rs.getString("origem"));
                        itinerario.setDestino(rs.getString("destino"));
                        itinerario.setDistanciaRota(rs.getDouble("distanciaRota"));
                        itinerario.setTempoPercurso(rs.getTime("tempoPercurso"));
                    }

                    // Viagem
                    Viagem viagem = new Viagem();
                    viagem.setIdViagem(rs.getInt("idViagem"));
                    viagem.setDataSaida(rs.getDate("dataSaida").toLocalDate());
                    viagem.setDataChegada(rs.getDate("dataChegada").toLocalDate());
                    viagem.setHorarioSaida(rs.getTime("horarioSaida").toLocalTime());
                    viagem.setHorarioChegada(rs.getTime("horarioChegada").toLocalTime());
                    viagem.setItinerario(itinerario);

                    // Escala
                    Escala e = new Escala(
                            rs.getInt("idEscala"),
                            motorista,
                            viagem,
                            rs.getDate("data_escala").toLocalDate(),
                            rs.getString("turno"),
                            rs.getString("statusEscala")
                    );

                    escalas.add(e);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return escalas;
    }



    // --- BUSCAR ESCALA POR ID ---
    public Escala buscarPorId(int id) {
        String sql = "SELECT e.idEscala, e.data_escala, e.turno, e.statusEscala, "
                + "m.idFuncionario AS idMotorista, u.nome AS nomeMotorista, u.cpf, u.email, u.telefone, "
                + "u.cpf AS login, u.senha, m.matricula, m.cargo, m.salario, m.statusPagamento, m.cnh, "
                + "v.idViagem, v.dataSaida, v.dataChegada, i.idItinerario, i.origem, i.destino "
                + "FROM escala e "
                + "JOIN funcionario m ON e.idMotorista = m.idFuncionario "
                + "JOIN usuario u ON m.idFuncionario = u.idUsuario "
                + "JOIN viagem v ON e.idViagem = v.idViagem "
                + "LEFT JOIN itinerario i ON v.idItinerario = i.idItinerario "
                + "WHERE e.idEscala=?";


        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Motorista
                    Motorista motorista = new Motorista();
                    motorista.setId(rs.getInt("idMotorista"));
                    motorista.setNome(rs.getString("nomeMotorista"));
                    motorista.setCpf(rs.getString("cpf"));
                    motorista.setEmail(rs.getString("email"));
                    motorista.setTelefone(rs.getString("telefone"));
                    motorista.setLogin(rs.getString("cpf"));
                    motorista.setSenha(rs.getString("senha"));
                    motorista.setMatricula(rs.getString("matricula"));
                    motorista.setCargo(rs.getString("cargo"));
                    motorista.setSalario(rs.getDouble("salario"));
                    motorista.setStatusPagamento(rs.getString("statusPagamento"));
                    motorista.setCnh(rs.getString("cnh"));
                    motorista.setTipo("Motorista");

                    // Itinerário
                    Itinerario itinerario = null;
                    int idItinerario = rs.getInt("idItinerario");
                    if (!rs.wasNull()) {
                        itinerario = new Itinerario();
                        itinerario.setIdItinerario(idItinerario);
                        itinerario.setOrigem(rs.getString("origem"));
                        itinerario.setDestino(rs.getString("destino"));
                    }

                    // Viagem
                    Viagem viagem = new Viagem();
                    viagem.setIdViagem(rs.getInt("idViagem"));
                    viagem.setDataSaida(rs.getDate("dataSaida").toLocalDate());
                    viagem.setDataChegada(rs.getDate("dataChegada").toLocalDate());
                    viagem.setItinerario(itinerario);

                    // Escala
                    Escala e = new Escala();
                    e.setIdEscala(rs.getInt("idEscala"));
                    e.setDataEscala(rs.getDate("data_escala").toLocalDate());
                    e.setTurno(rs.getString("turno"));
                    e.setStatusEscala(rs.getString("statusEscala"));
                    e.setMotorista(motorista);
                    e.setViagem(viagem);

                    return e;
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
