package projetoinfobus_versao2.dao;

import projetoinfobus_versao2.db.DB;
import projetoinfobus_versao2.model.GerenteRH;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GerenteRHDAO {

    // --- INSERIR GERENTE RH ---
    public void inserir(GerenteRH g) {
        String sqlUsuario = "INSERT INTO usuario (nome, cpf, telefone, email, senha, tipo) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlFuncionario = "INSERT INTO funcionario (idFuncionario, matricula, cargo, salario) VALUES (?, ?, ?, ?)";
        String sqlGerenteRH = "INSERT INTO gerenteRH (idGerenteRH) VALUES (?)";

        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);

            // Inserir usuario
            try (PreparedStatement ps = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, g.getNome());
                ps.setString(2, g.getCpf());
                ps.setString(3, g.getTelefone());
                ps.setString(4, g.getEmail());
                ps.setString(5, g.getSenha());
                ps.setString(6, "GerenteRH");
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        g.setId(rs.getInt(1));
                    }
                }
            }

            // Inserir funcionario
            try (PreparedStatement ps2 = conn.prepareStatement(sqlFuncionario)) {
                ps2.setInt(1, g.getId());
                ps2.setString(2, g.getMatricula());
                ps2.setString(3, g.getCargo());
                ps2.setDouble(4, g.getSalario());
                ps2.executeUpdate();
            }

            // Inserir gerenteRH
            try (PreparedStatement ps3 = conn.prepareStatement(sqlGerenteRH)) {
                ps3.setInt(1, g.getId());
                ps3.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- LISTAR TODOS OS GERENTES RH ---
    public List<GerenteRH> listarTodos() {
        List<GerenteRH> lista = new ArrayList<>();
        String sql = "SELECT u.idUsuario, u.nome, u.cpf, u.email, u.telefone, u.senha, " +
                     "f.matricula, f.cargo, f.salario, f.statusPagamento " +
                     "FROM usuario u " +
                     "JOIN funcionario f ON u.idUsuario = f.idFuncionario " +
                     "JOIN gerenteRH g ON f.idFuncionario = g.idGerenteRH";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                GerenteRH g = new GerenteRH(
                        rs.getInt("idUsuario"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        null, // login
                        rs.getString("senha"),
                        "GerenteRH",
                        rs.getString("matricula"),
                        rs.getString("cargo"),
                        rs.getDouble("salario"),
                        rs.getString("statusPagamento")
                );
                lista.add(g);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // --- ATUALIZAR GERENTE RH ---
    public void atualizar(GerenteRH g) {
        String sqlUsuario = "UPDATE usuario SET nome=?, cpf=?, telefone=?, email=?, senha=?, tipo=? WHERE idUsuario=?";
        String sqlFuncionario = "UPDATE funcionario SET matricula=?, cargo=?, salario=? WHERE idFuncionario=?";

        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);

            // Atualizar usuario
            try (PreparedStatement ps = conn.prepareStatement(sqlUsuario)) {
                ps.setString(1, g.getNome());
                ps.setString(2, g.getCpf());
                ps.setString(3, g.getTelefone());
                ps.setString(4, g.getEmail());
                ps.setString(5, g.getSenha());
                ps.setString(6, "GerenteRH");
                ps.setInt(7, g.getId());
                ps.executeUpdate();
            }

            // Atualizar funcionario
            try (PreparedStatement ps2 = conn.prepareStatement(sqlFuncionario)) {
                ps2.setString(1, g.getMatricula());
                ps2.setString(2, g.getCargo());
                ps2.setDouble(3, g.getSalario());
                ps2.setInt(4, g.getId());
                ps2.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- DELETAR GERENTE RH ---
    public void deletar(int id) {
        String sqlGerenteRH = "DELETE FROM gerenteRH WHERE idGerenteRH=?";
        String sqlFuncionario = "DELETE FROM funcionario WHERE idFuncionario=?";
        String sqlUsuario = "DELETE FROM usuario WHERE idUsuario=?";

        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sqlGerenteRH)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            try (PreparedStatement ps2 = conn.prepareStatement(sqlFuncionario)) {
                ps2.setInt(1, id);
                ps2.executeUpdate();
            }

            try (PreparedStatement ps3 = conn.prepareStatement(sqlUsuario)) {
                ps3.setInt(1, id);
                ps3.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- BUSCAR POR ID ---
    public GerenteRH buscarPorId(int id) {
        String sql = "SELECT u.idUsuario, u.nome, u.cpf, u.email, u.telefone, u.senha, " +
                     "f.matricula, f.cargo, f.salario, f.statusPagamento " +
                     "FROM usuario u " +
                     "JOIN funcionario f ON u.idUsuario = f.idFuncionario " +
                     "JOIN gerenteRH g ON f.idFuncionario = g.idGerenteRH " +
                     "WHERE u.idUsuario=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new GerenteRH(
                            rs.getInt("idUsuario"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("email"),
                            rs.getString("telefone"),
                            null, // login
                            rs.getString("senha"),
                            "GerenteRH",
                            rs.getString("matricula"),
                            rs.getString("cargo"),
                            rs.getDouble("salario"),
                            rs.getString("statusPagamento")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
