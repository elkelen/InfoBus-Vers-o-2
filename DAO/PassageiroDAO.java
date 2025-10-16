package projetoinfobus_versao2.dao;

import projetoinfobus_versao2.db.DB;
import projetoinfobus_versao2.model.Passageiro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassageiroDAO {

    // --- INSERIR PASSAGEIRO ---
    public void inserir(Passageiro p) {
        String sqlUsuario = "INSERT INTO usuario (nome, cpf, telefone, email, senha, tipo) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlPassageiro = "INSERT INTO passageiro (idPassageiro) VALUES (?)";

        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);

            // Inserir usuario
            try (PreparedStatement ps = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, p.getNome());
                ps.setString(2, p.getCpf());
                ps.setString(3, p.getTelefone());
                ps.setString(4, p.getEmail());
                ps.setString(5, p.getSenha());
                ps.setString(6, "Passageiro"); // tipo fixo
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        p.setId(rs.getInt(1));
                    }
                }
            }

            // Inserir passageiro
            try (PreparedStatement ps2 = conn.prepareStatement(sqlPassageiro)) {
                ps2.setInt(1, p.getId());
                ps2.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- LISTAR TODOS OS PASSAGEIROS ---
    public List<Passageiro> listarTodos() {
        List<Passageiro> lista = new ArrayList<>();
        String sql = "SELECT u.idUsuario, u.nome, u.cpf, u.email, u.telefone, u.senha " +
                     "FROM usuario u " +
                     "JOIN passageiro p ON u.idUsuario = p.idPassageiro";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Passageiro p = new Passageiro(
                        rs.getInt("idUsuario"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        null, // login (não usado)
                        rs.getString("senha")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // --- ATUALIZAR PASSAGEIRO ---
    public void atualizar(Passageiro p) {
        String sqlUsuario = "UPDATE usuario SET nome=?, cpf=?, telefone=?, email=?, senha=?, tipo=? WHERE idUsuario=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlUsuario)) {

            ps.setString(1, p.getNome());
            ps.setString(2, p.getCpf());
            ps.setString(3, p.getTelefone());
            ps.setString(4, p.getEmail());
            ps.setString(5, p.getSenha());
            ps.setString(6, "Passageiro"); // tipo fixo
            ps.setInt(7, p.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- DELETAR PASSAGEIRO ---
    public void deletar(int id) {
        String sqlPassageiro = "DELETE FROM passageiro WHERE idPassageiro=?";
        String sqlUsuario = "DELETE FROM usuario WHERE idUsuario=?";

        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sqlPassageiro)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            try (PreparedStatement ps2 = conn.prepareStatement(sqlUsuario)) {
                ps2.setInt(1, id);
                ps2.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- BUSCAR PASSAGEIRO POR ID ---
    public Passageiro buscarPorId(int id) {
        String sql = "SELECT u.idUsuario, u.nome, u.cpf, u.email, u.telefone, u.senha " +
                     "FROM usuario u " +
                     "JOIN passageiro p ON u.idUsuario = p.idPassageiro " +
                     "WHERE u.idUsuario=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Passageiro(
                            rs.getInt("idUsuario"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("email"),
                            rs.getString("telefone"),
                            null, // login
                            rs.getString("senha")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
