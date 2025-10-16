package projetoinfobus_versao2.dao;

import projetoinfobus_versao2.db.DB;
import projetoinfobus_versao2.model.Passagem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class PassagemDAO {

    // --- INSERIR PASSAGEM ---
    public void inserir(Passagem p) {
        String sql = "INSERT INTO passagem (idPassageiro, idViagem, dataCompra, valor, poltrona, plataforma, classe) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, p.getIdPassageiro());
            ps.setInt(2, p.getIdViagem()); // 🔹 ADICIONADO
            ps.setDate(3, Date.valueOf(p.getDataCompra()));
            ps.setBigDecimal(4, p.getValor());
            ps.setString(5, p.getPoltrona());
            ps.setInt(6, p.getPlataforma());
            ps.setString(7, p.getClasse());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setIdPassagem(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- LISTAR TODAS AS PASSAGENS ---
    public List<Passagem> listarTodos() {
        List<Passagem> lista = new ArrayList<>();
        String sql = "SELECT * FROM passagem";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Passagem p = new Passagem(
                        rs.getInt("idPassagem"),
                        rs.getInt("idPassageiro"),
                        rs.getInt("idViagem"), // 🔹 ADICIONADO
                        rs.getDate("dataCompra").toLocalDate(),
                        rs.getBigDecimal("valor"),
                        rs.getString("poltrona"),
                        rs.getInt("plataforma"),
                        rs.getString("classe")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // --- LISTAR PASSAGENS DE UM PASSAGEIRO ---
    public List<Passagem> listarPorPassageiro(int idPassageiro) {
        List<Passagem> lista = new ArrayList<>();
        String sql = "SELECT * FROM passagem WHERE idPassageiro=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPassageiro);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Passagem p = new Passagem(
                            rs.getInt("idPassagem"),
                            rs.getInt("idPassageiro"),
                            rs.getInt("idViagem"), // 🔹 ADICIONADO
                            rs.getDate("dataCompra").toLocalDate(),
                            rs.getBigDecimal("valor"),
                            rs.getString("poltrona"),
                            rs.getInt("plataforma"),
                            rs.getString("classe")
                    );
                    lista.add(p);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    // --- LISTAR PASSAGENS DE UMA VIAGEM ---
    public List<Passagem> listarPorViagem(int idViagem) {
        List<Passagem> lista = new ArrayList<>();
        String sql = "SELECT * FROM passagem WHERE idViagem=?";

        try (Connection conn = DB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idViagem);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Passagem p = new Passagem(
                            rs.getInt("idPassagem"),
                            rs.getInt("idPassageiro"),
                            rs.getInt("idViagem"),
                            rs.getDate("dataCompra").toLocalDate(),
                            rs.getBigDecimal("valor"),
                            rs.getString("poltrona"),
                            rs.getInt("plataforma"),
                            rs.getString("classe")
                    );
                    lista.add(p);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

// --- LISTAR PLATAFORMAS USADAS NUMA VIAGEM (DISTINCT) ---
    public List<Integer> listarPlataformasPorViagem(int idViagem) {
        List<Integer> lista = new ArrayList<>();
        String sql = "SELECT DISTINCT plataforma FROM passagem WHERE idViagem=?";

        try (Connection conn = DB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idViagem);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(rs.getInt("plataforma"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }


    // --- ATUALIZAR PASSAGEM ---
    public void atualizar(Passagem p) {
        String sql = "UPDATE passagem SET idPassageiro=?, idViagem=?, dataCompra=?, valor=?, poltrona=?, plataforma=?, classe=? WHERE idPassagem=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, p.getIdPassageiro());
            ps.setInt(2, p.getIdViagem()); // 🔹 ADICIONADO
            ps.setDate(3, Date.valueOf(p.getDataCompra()));
            ps.setBigDecimal(4, p.getValor());
            ps.setString(5, p.getPoltrona());
            ps.setInt(6, p.getPlataforma());
            ps.setString(7, p.getClasse());
            ps.setInt(8, p.getIdPassagem());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- DELETAR PASSAGEM ---
    public void deletar(int id) {
        String sql = "DELETE FROM passagem WHERE idPassagem=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- BUSCAR PASSAGEM POR ID ---
    public Passagem buscarPorId(int id) {
        String sql = "SELECT * FROM passagem WHERE idPassagem=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Passagem(
                            rs.getInt("idPassagem"),
                            rs.getInt("idPassageiro"),
                            rs.getInt("idViagem"), // 🔹 ADICIONADO
                            rs.getDate("dataCompra").toLocalDate(),
                            rs.getBigDecimal("valor"),
                            rs.getString("poltrona"),
                            rs.getInt("plataforma"),
                            rs.getString("classe")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
