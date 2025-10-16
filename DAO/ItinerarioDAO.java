package projetoinfobus_versao2.dao;

import projetoinfobus_versao2.db.DB;
import projetoinfobus_versao2.model.Itinerario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItinerarioDAO {

    // --- INSERIR ITINERÁRIO ---
    public void inserir(Itinerario i) {
        String sql = "INSERT INTO itinerario (origem, destino, distanciaRota, tempoPercurso) VALUES (?, ?, ?, ?)";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, i.getOrigem());
            ps.setString(2, i.getDestino());
            ps.setDouble(3, i.getDistanciaRota());
            ps.setTime(4, i.getTempoPercurso());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    i.setIdItinerario(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- LISTAR TODOS OS ITINERÁRIOS ---
    public List<Itinerario> listarTodos() {
        List<Itinerario> lista = new ArrayList<>();
        String sql = "SELECT * FROM itinerario";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Itinerario i = new Itinerario(
                        rs.getInt("idItinerario"),
                        rs.getString("origem"),
                        rs.getString("destino"),
                        rs.getDouble("distanciaRota"),
                        rs.getTime("tempoPercurso")
                );
                lista.add(i);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // --- ATUALIZAR ITINERÁRIO ---
    public void atualizar(Itinerario i) {
        String sql = "UPDATE itinerario SET origem=?, destino=?, distanciaRota=?, tempoPercurso=? WHERE idItinerario=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, i.getOrigem());
            ps.setString(2, i.getDestino());
            ps.setDouble(3, i.getDistanciaRota());
            ps.setTime(4, i.getTempoPercurso());
            ps.setInt(5, i.getIdItinerario());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- DELETAR ITINERÁRIO ---
    public void deletar(int id) {
        String sql = "DELETE FROM itinerario WHERE idItinerario=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- BUSCAR ITINERÁRIO POR ID ---
    public Itinerario buscarPorId(int id) {
        String sql = "SELECT * FROM itinerario WHERE idItinerario=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Itinerario(
                            rs.getInt("idItinerario"),
                            rs.getString("origem"),
                            rs.getString("destino"),
                            rs.getDouble("distanciaRota"),
                            rs.getTime("tempoPercurso")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
