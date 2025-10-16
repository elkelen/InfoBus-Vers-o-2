package projetoinfobus_versao2.dao;

import projetoinfobus_versao2.db.DB;
import projetoinfobus_versao2.model.Ocorrencia;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OcorrenciaDAO {

    // --- INSERIR OCORRÊNCIA ---
    public void inserir(Ocorrencia o) {
        String sql = "INSERT INTO ocorrencia (dataHora, descricao, tipo, gravidade, idMotorista) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setTimestamp(1, Timestamp.valueOf(o.getDataHora()));
            ps.setString(2, o.getDescricao());
            ps.setString(3, o.getTipo());
            ps.setString(4, o.getGravidade());
            ps.setInt(5, o.getMotorista().getId());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    o.setIdOcorrencia(rs.getInt(1));
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // --- LISTAR TODAS AS OCORRÊNCIAS ---
    public List<Ocorrencia> listarTodos() {
        List<Ocorrencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM ocorrencia";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Ocorrencia o = new Ocorrencia(
                        rs.getInt("idOcorrencia"),
                        rs.getTimestamp("dataHora").toLocalDateTime(),
                        rs.getString("descricao"),
                        rs.getString("tipo"),
                        rs.getString("gravidade")
                );
                lista.add(o);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    // --- ATUALIZAR OCORRÊNCIA ---
    public void atualizar(Ocorrencia o) {
        String sql = "UPDATE ocorrencia SET dataHora=?, descricao=?, tipo=?, gravidade=? WHERE idOcorrencia=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(o.getDataHora()));
            ps.setString(2, o.getDescricao());
            ps.setString(3, o.getTipo());
            ps.setString(4, o.getGravidade());
            ps.setInt(5, o.getIdOcorrencia());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // --- DELETAR OCORRÊNCIA ---
    public void deletar(int id) {
        String sql = "DELETE FROM ocorrencia WHERE idOcorrencia=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // --- BUSCAR OCORRÊNCIA POR ID ---
    public Ocorrencia buscarPorId(int id) {
        String sql = "SELECT * FROM ocorrencia WHERE idOcorrencia=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Ocorrencia(
                            rs.getInt("idOcorrencia"),
                            rs.getTimestamp("dataHora").toLocalDateTime(),
                            rs.getString("descricao"),
                            rs.getString("tipo"),
                            rs.getString("gravidade")
                    );
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
    
    // --- LISTAR OCORRÊNCIAS POR MOTORISTA ---
    public List<Ocorrencia> listarPorMotoristaId(int idMotorista) {
        List<Ocorrencia> ocorrencias = new ArrayList<>();
        String sql = "SELECT * FROM ocorrencia WHERE idMotorista = ?";

        try (Connection conn = DB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idMotorista);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ocorrencia o = new Ocorrencia();
                    o.setIdMotorista(rs.getInt("idMotorista")); // usando setId da superclasse Funcionario
                    o.setTipo(rs.getString("tipo"));
                    o.setDescricao(rs.getString("descricao"));
                    o.setGravidade(rs.getString("gravidade"));
                    o.setDataHora(rs.getTimestamp("dataHora").toLocalDateTime());
                    ocorrencias.add(o);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return ocorrencias;
    }

}
