package projetoinfobus_versao2.dao;

import projetoinfobus_versao2.db.DB;
import projetoinfobus_versao2.model.Viagem;
import projetoinfobus_versao2.model.Itinerario;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ViagemDAO {

    // --- INSERIR VIAGEM ---
    public void inserir(Viagem v) {
        String sql = "INSERT INTO viagem (dataSaida, dataChegada, horarioSaida, horarioChegada, idItinerario) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(v.getDataSaida()));
            ps.setDate(2, Date.valueOf(v.getDataChegada()));
            ps.setTime(3, Time.valueOf(v.getHorarioSaida()));   // conversão LocalTime -> Time
            ps.setTime(4, Time.valueOf(v.getHorarioChegada())); // conversão LocalTime -> Time
            ps.setInt(5, v.getItinerario().getIdItinerario());
            
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    v.setIdViagem(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- LISTAR TODAS AS VIAGENS (com dados do itinerário) ---
    public List<Viagem> listarTodos() {
        List<Viagem> lista = new ArrayList<>();

        String sql = """
            SELECT v.idViagem, v.dataSaida, v.dataChegada, v.horarioSaida, v.horarioChegada,
                   i.idItinerario, i.origem, i.destino, i.distanciaRota, i.tempoPercurso
            FROM viagem v
            INNER JOIN itinerario i ON v.idItinerario = i.idItinerario
        """;

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Criar e preencher o Itinerario vinculado
                Itinerario it = new Itinerario();
                it.setIdItinerario(rs.getInt("idItinerario"));
                it.setOrigem(rs.getString("origem"));
                it.setDestino(rs.getString("destino"));
                it.setDistanciaRota(rs.getDouble("distanciaRota"));
                it.setTempoPercurso(rs.getTime("tempoPercurso"));
                
                Viagem v = new Viagem(
                        rs.getInt("idViagem"),
                        rs.getDate("dataSaida").toLocalDate(),
                        rs.getDate("dataChegada").toLocalDate(),
                        rs.getTime("horarioSaida").toLocalTime(),
                        rs.getTime("horarioChegada").toLocalTime(),
                        it
                );

                

                // Associar o itinerário à viagem
                v.setItinerario(it);

                lista.add(v);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // --- ATUALIZAR VIAGEM ---
    public void atualizar(Viagem v) {
        String sql = "UPDATE viagem SET dataSaida=?, dataChegada=?, horarioSaida=?, horarioChegada=?, idItinerario=? WHERE idViagem=?";

        try (Connection conn = DB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(v.getDataSaida()));
            ps.setDate(2, Date.valueOf(v.getDataChegada()));
            ps.setTime(3, Time.valueOf(v.getHorarioSaida()));
            ps.setTime(4, Time.valueOf(v.getHorarioChegada()));
            ps.setInt(5, v.getItinerario().getIdItinerario());
            ps.setInt(6, v.getIdViagem());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- DELETAR VIAGEM ---
    public void deletar(int id) {
        String sql = "DELETE FROM viagem WHERE idViagem=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // --- LISTAR VIAGENS POR MOTORISTA ---
    public List<Viagem> listarPorMotoristaId(int idMotorista) {
        List<Viagem> viagens = new ArrayList<>();
        String sql = """
        SELECT v.* 
        FROM viagem v
        JOIN escala e ON e.idViagem = v.idViagem
        WHERE e.idFuncionario = ?
    """;

        try (Connection conn = DB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idMotorista);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idViagem = rs.getInt("idViagem");
                    LocalDate dataSaida = rs.getDate("dataSaida").toLocalDate();
                    LocalDate dataChegada = rs.getDate("dataChegada").toLocalDate();
                    LocalTime horarioSaida = rs.getTime("horarioSaida").toLocalTime();
                    LocalTime horarioChegada = rs.getTime("horarioChegada").toLocalTime();
                    int idItinerario = rs.getInt("idItinerario"); // assumindo que existe essa coluna na tabela viagem

                    Itinerario itinerario = null;
                    if (idItinerario > 0) {
                        itinerario = new ItinerarioDAO().buscarPorId(idItinerario);
                    }

                    Viagem v = new Viagem(idViagem, dataSaida, dataChegada, horarioSaida, horarioChegada, itinerario);
                    viagens.add(v);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return viagens;
    }


    // --- BUSCAR VIAGEM POR ID ---
    public Viagem buscarPorId(int id) {
    String sql = """
        SELECT v.idViagem, v.dataSaida, v.dataChegada, v.horarioSaida, v.horarioChegada,
               i.idItinerario, i.origem, i.destino, i.distanciaRota, i.tempoPercurso
        FROM viagem v
        INNER JOIN itinerario i ON v.idItinerario = i.idItinerario
        WHERE v.idViagem = ?
    """;

    try (Connection conn = DB.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, id);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                // Criar o Itinerário
                Itinerario it = new Itinerario();
                it.setIdItinerario(rs.getInt("idItinerario"));
                it.setOrigem(rs.getString("origem"));
                it.setDestino(rs.getString("destino"));
                it.setDistanciaRota(rs.getDouble("distanciaRota"));
                it.setTempoPercurso(rs.getTime("tempoPercurso"));

                // Criar a Viagem com o itinerário
                return new Viagem(
                        rs.getInt("idViagem"),
                        rs.getDate("dataSaida").toLocalDate(),
                        rs.getDate("dataChegada").toLocalDate(),
                        rs.getTime("horarioSaida").toLocalTime(),
                        rs.getTime("horarioChegada").toLocalTime(),
                        it
                );
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    

    return null;
    }
}

