package projetoinfobus_versao2.dao;

import projetoinfobus_versao2.model.Atendente;
import projetoinfobus_versao2.db.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AtendenteDAO {

    // --- INSERIR ATENDENTE ---
    public void inserir(Atendente a) {
        String sqlUsuario = "INSERT INTO usuario (nome, cpf, telefone, email, senha, tipo) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlFuncionario = "INSERT INTO funcionario (idFuncionario, matricula, cargo, salario) VALUES (?, ?, ?, ?)";
        String sqlAtendente = "INSERT INTO atendente (idAtendente) VALUES (?)";

        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false); // iniciar transação

            // 1) Inserir na tabela usuario
            try (PreparedStatement ps = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, a.getNome());
                ps.setString(2, a.getCpf());
                ps.setString(3, a.getTelefone());
                ps.setString(4, a.getEmail());
                ps.setString(5, a.getSenha());
                ps.setString(6, "Atendente");

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        a.setId(rs.getInt(1));
                    }
                }
            }

            // 2) Inserir na tabela funcionario
            try (PreparedStatement ps2 = conn.prepareStatement(sqlFuncionario)) {
                ps2.setInt(1, a.getId());
                ps2.setString(2, a.getMatricula());
                ps2.setString(3, a.getCargo());
                ps2.setDouble(4, a.getSalario());
                ps2.executeUpdate();
            }

            // 3) Inserir na tabela atendente
            try (PreparedStatement ps3 = conn.prepareStatement(sqlAtendente)) {
                ps3.setInt(1, a.getId());
                ps3.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- LISTAR TODOS ATENDENTES ---
    public List<Atendente> listarTodos() {
        List<Atendente> lista = new ArrayList<>();
        String sql = "SELECT u.idUsuario, u.nome, u.cpf, u.email, u.telefone, u.senha, " +
                     "f.matricula, f.cargo, f.salario, f.statusPagamento " +
                     "FROM usuario u " +
                     "JOIN funcionario f ON u.idUsuario = f.idFuncionario " +
                     "JOIN atendente a ON f.idFuncionario = a.idAtendente";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Atendente a = new Atendente(
                        rs.getInt("idUsuario"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        null, // login (não existe na tabela)
                        rs.getString("senha"),
                        "Atendente",
                        rs.getString("matricula"),
                        rs.getString("cargo"),
                        rs.getDouble("salario"),
                        rs.getString("statusPagamento")
                );
                lista.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // --- ATUALIZAR ATENDENTE ---
    public void atualizar(Atendente a) {
        String sqlUsuario = "UPDATE usuario SET nome=?, cpf=?, telefone=?, email=?, senha=?, tipo=? WHERE idUsuario=?";
        String sqlFuncionario = "UPDATE funcionario SET matricula=?, cargo=?, salario=? WHERE idFuncionario=?";

        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);

            // Atualizar usuario
            try (PreparedStatement ps = conn.prepareStatement(sqlUsuario)) {
                ps.setString(1, a.getNome());
                ps.setString(2, a.getCpf());
                ps.setString(3, a.getTelefone());
                ps.setString(4, a.getEmail());
                ps.setString(5, a.getSenha());
                ps.setString(6, "Atendente");
                ps.setInt(7, a.getId());
                ps.executeUpdate();
            }

            // Atualizar funcionario
            try (PreparedStatement ps2 = conn.prepareStatement(sqlFuncionario)) {
                ps2.setString(1, a.getMatricula());
                ps2.setString(2, a.getCargo());
                ps2.setDouble(3, a.getSalario());
                ps2.setInt(4, a.getId());
                ps2.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- DELETAR ATENDENTE ---
    public void deletar(int id) {
        String sqlAtendente = "DELETE FROM atendente WHERE idAtendente=?";
        String sqlFuncionario = "DELETE FROM funcionario WHERE idFuncionario=?";
        String sqlUsuario = "DELETE FROM usuario WHERE idUsuario=?";

        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sqlAtendente)) {
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

    // --- BUSCAR ATENDENTE POR ID ---
    public Atendente buscarPorId(int id) {
        String sql = "SELECT u.idUsuario, u.nome, u.cpf, u.email, u.telefone, u.senha, " +
                     "f.matricula, f.cargo, f.salario, f.statusPagamento " +
                     "FROM usuario u " +
                     "JOIN funcionario f ON u.idUsuario = f.idFuncionario " +
                     "JOIN atendente a ON f.idFuncionario = a.idAtendente " +
                     "WHERE u.idUsuario=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Atendente(
                            rs.getInt("idUsuario"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("email"),
                            rs.getString("telefone"),
                            null,
                            rs.getString("senha"),
                            "Atendente",
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
