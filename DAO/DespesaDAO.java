package projetoinfobus_versao2.dao;

import projetoinfobus_versao2.db.DB;
import projetoinfobus_versao2.model.Despesa;

import java.sql.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DespesaDAO {

    // --- INSERIR DESPESA ---
    public void inserir(Despesa d) {
        String sql = "INSERT INTO despesa (valor, data_despesa, descricao) VALUES (?, ?, ?)";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setBigDecimal(1, d.getValor());
            ps.setDate(2, Date.valueOf(d.getDataDespesa()));
            ps.setString(3, d.getDescricao());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    d.setIdDespesa(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- LISTAR TODAS AS DESPESAS ---
    public List<Despesa> listarTodos() {
        List<Despesa> lista = new ArrayList<>();
        String sql = "SELECT * FROM despesa";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Despesa d = new Despesa(
                        rs.getInt("idDespesa"),
                        rs.getBigDecimal("valor"),
                        rs.getDate("data_despesa").toLocalDate(),
                        rs.getString("descricao")
                );
                lista.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // --- ATUALIZAR DESPESA ---
    public void atualizar(Despesa d) {
        String sql = "UPDATE despesa SET valor=?, data_despesa=?, descricao=? WHERE idDespesa=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBigDecimal(1, d.getValor());
            ps.setDate(2, Date.valueOf(d.getDataDespesa()));
            ps.setString(3, d.getDescricao());
            ps.setInt(4, d.getIdDespesa());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- DELETAR DESPESA ---
    public void deletar(int id) {
        String sql = "DELETE FROM despesa WHERE idDespesa=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- BUSCAR DESPESA POR ID ---
    public Despesa buscarPorId(int id) {
        String sql = "SELECT * FROM despesa WHERE idDespesa=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Despesa(
                            rs.getInt("idDespesa"),
                            rs.getBigDecimal("valor"),
                            rs.getDate("data_despesa").toLocalDate(),
                            rs.getString("descricao")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
