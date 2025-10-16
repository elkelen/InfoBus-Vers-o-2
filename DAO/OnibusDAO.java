package projetoinfobus_versao2.dao;

import projetoinfobus_versao2.db.DB;
import projetoinfobus_versao2.model.Onibus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OnibusDAO {

    // --- INSERIR ÔNIBUS ---
    public void inserir(Onibus o) {
        String sql = "INSERT INTO onibus (placa, modelo, capacidade, classes, fabricante, idMotorista, idItinerario) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, o.getPlaca());
            ps.setString(2, o.getModelo());
            ps.setInt(3, o.getCapacidade());
            ps.setString(4, o.getClasse());
            ps.setString(5, o.getFabricante());
            ps.setInt(6, o.getIdMotorista());
            ps.setInt(7, o.getIdItinerario());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    o.setIdOnibus(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- LISTAR TODOS OS ÔNIBUS ---
    public List<Onibus> listarTodos() {
        List<Onibus> lista = new ArrayList<>();
        String sql = "SELECT * FROM onibus";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Onibus o = new Onibus(
                        rs.getInt("idOnibus"),
                        rs.getString("placa"),
                        rs.getString("modelo"),
                        rs.getInt("capacidade"),
                        rs.getString("classes"),
                        rs.getString("fabricante"),
                        rs.getInt("idMotorista"),
                        rs.getInt("idItinerario")
                );
                lista.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // --- ATUALIZAR ÔNIBUS ---
    public void atualizar(Onibus o) {
        String sql = "UPDATE onibus SET placa=?, modelo=?, capacidade=?, classes=?, fabricante=?, idMotorista=?, idItinerario=? WHERE idOnibus=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, o.getPlaca());
            ps.setString(2, o.getModelo());
            ps.setInt(3, o.getCapacidade());
            ps.setString(4, o.getClasse());
            ps.setString(5, o.getFabricante());
            ps.setInt(6, o.getIdMotorista());
            ps.setInt(7, o.getIdItinerario());
            ps.setInt(8, o.getIdOnibus());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- DELETAR ÔNIBUS ---
    public void deletar(int id) {
        String sql = "DELETE FROM onibus WHERE idOnibus=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- BUSCAR ÔNIBUS POR ID ---
    public Onibus buscarPorId(int id) {
        String sql = "SELECT * FROM onibus WHERE idOnibus=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Onibus(
                            rs.getInt("idOnibus"),
                            rs.getString("placa"),
                            rs.getString("modelo"),
                            rs.getInt("capacidade"),
                            rs.getString("classes"),
                            rs.getString("fabricante"),
                            rs.getInt("idMotorista"),
                            rs.getInt("idItinerario")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
