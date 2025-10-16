package projetoinfobus_versao2.dao;

import projetoinfobus_versao2.db.DB;
import projetoinfobus_versao2.model.Pagamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAO {

    // --- INSERIR PAGAMENTO ---
    public void inserir(Pagamento p) {
        String sql = "INSERT INTO pagamento (valor, data_pagamento, idFuncionario) VALUES (?, ?, ?)";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setBigDecimal(1, p.getValor());
            ps.setDate(2, Date.valueOf(p.getDataPagamento()));
            ps.setInt(3, p.getIdFuncionario());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setIdPagamento(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- LISTAR TODOS OS PAGAMENTOS ---
    public List<Pagamento> listarTodos() {
        List<Pagamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM pagamento";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Pagamento p = new Pagamento(
                        rs.getInt("idPagamento"),
                        rs.getBigDecimal("valor"),
                        rs.getDate("data_pagamento").toLocalDate(),
                        rs.getInt("idFuncionario")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // --- ATUALIZAR PAGAMENTO ---
    public void atualizar(Pagamento p) {
        String sql = "UPDATE pagamento SET valor=?, data_pagamento=?, idFuncionario=? WHERE idPagamento=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBigDecimal(1, p.getValor());
            ps.setDate(2, Date.valueOf(p.getDataPagamento()));
            ps.setInt(3, p.getIdFuncionario());
            ps.setInt(4, p.getIdPagamento());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- DELETAR PAGAMENTO ---
    public void deletar(int id) {
        String sql = "DELETE FROM pagamento WHERE idPagamento=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- BUSCAR PAGAMENTO POR ID ---
    public Pagamento buscarPorId(int id) {
        String sql = "SELECT * FROM pagamento WHERE idPagamento=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pagamento(
                            rs.getInt("idPagamento"),
                            rs.getBigDecimal("valor"),
                            rs.getDate("data_pagamento").toLocalDate(),
                            rs.getInt("idFuncionario")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
