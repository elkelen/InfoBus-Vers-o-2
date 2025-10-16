package projetoinfobus_versao2.dao;

import projetoinfobus_versao2.db.DB;
import projetoinfobus_versao2.model.Financeiro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FinanceiroDAO {

    // --- INSERIR FINANCEIRO ---
    public void inserir(Financeiro f) {
        String sqlUsuario = "INSERT INTO usuario (nome, cpf, telefone, email, senha, tipo) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlFuncionario = "INSERT INTO funcionario (idFuncionario, matricula, cargo, salario) VALUES (?, ?, ?, ?)";
        String sqlFinanceiro = "INSERT INTO financeiro (idFinanceiro) VALUES (?)";

        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);

            // 1) Inserir na tabela usuario
            try (PreparedStatement ps = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, f.getNome());
                ps.setString(2, f.getCpf());
                ps.setString(3, f.getTelefone());
                ps.setString(4, f.getEmail());
                ps.setString(5, f.getSenha());
                ps.setString(6, "Financeiro");

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        f.setId(rs.getInt(1));
                    }
                }
            }

            // 2) Inserir na tabela funcionario
            try (PreparedStatement ps2 = conn.prepareStatement(sqlFuncionario)) {
                ps2.setInt(1, f.getId());
                ps2.setString(2, f.getMatricula());
                ps2.setString(3, f.getCargo());
                ps2.setDouble(4, f.getSalario());
                ps2.executeUpdate();
            }

            // 3) Inserir na tabela financeiro
            try (PreparedStatement ps3 = conn.prepareStatement(sqlFinanceiro)) {
                ps3.setInt(1, f.getId());
                ps3.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- LISTAR TODOS OS FINANCEIROS ---
    public List<Financeiro> listarTodos() {
        List<Financeiro> lista = new ArrayList<>();
        String sql = "SELECT u.idUsuario, u.nome, u.cpf, u.email, u.telefone, u.senha, " +
                     "f.matricula, f.cargo, f.salario, f.statusPagamento " +
                     "FROM usuario u " +
                     "JOIN funcionario f ON u.idUsuario = f.idFuncionario " +
                     "JOIN financeiro fn ON f.idFuncionario = fn.idFinanceiro";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Financeiro f = new Financeiro(
                        rs.getInt("idUsuario"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        null, // login (não existe na tabela)
                        rs.getString("senha"),
                        "Financeiro",
                        rs.getString("matricula"),
                        rs.getString("cargo"),
                        rs.getDouble("salario"),
                        rs.getString("statusPagamento")
                );
                lista.add(f);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // --- ATUALIZAR FINANCEIRO ---
    public void atualizar(Financeiro f) {
        String sqlUsuario = "UPDATE usuario SET nome=?, cpf=?, telefone=?, email=?, senha=?, tipo=? WHERE idUsuario=?";
        String sqlFuncionario = "UPDATE funcionario SET matricula=?, cargo=?, salario=? WHERE idFuncionario=?";

        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);

            // Atualizar usuario
            try (PreparedStatement ps = conn.prepareStatement(sqlUsuario)) {
                ps.setString(1, f.getNome());
                ps.setString(2, f.getCpf());
                ps.setString(3, f.getTelefone());
                ps.setString(4, f.getEmail());
                ps.setString(5, f.getSenha());
                ps.setString(6, "Financeiro");
                ps.setInt(7, f.getId());
                ps.executeUpdate();
            }

            // Atualizar funcionario
            try (PreparedStatement ps2 = conn.prepareStatement(sqlFuncionario)) {
                ps2.setString(1, f.getMatricula());
                ps2.setString(2, f.getCargo());
                ps2.setDouble(3, f.getSalario());
                ps2.setInt(4, f.getId());
                ps2.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- DELETAR FINANCEIRO ---
    public void deletar(int id) {
        String sqlFinanceiro = "DELETE FROM financeiro WHERE idFinanceiro=?";
        String sqlFuncionario = "DELETE FROM funcionario WHERE idFuncionario=?";
        String sqlUsuario = "DELETE FROM usuario WHERE idUsuario=?";

        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sqlFinanceiro)) {
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

    // --- BUSCAR FINANCEIRO POR ID ---
    public Financeiro buscarPorId(int id) {
        String sql = "SELECT u.idUsuario, u.nome, u.cpf, u.email, u.telefone, u.senha, " +
                     "f.matricula, f.cargo, f.salario, f.statusPagamento " +
                     "FROM usuario u " +
                     "JOIN funcionario f ON u.idUsuario = f.idFuncionario " +
                     "JOIN financeiro fn ON f.idFuncionario = fn.idFinanceiro " +
                     "WHERE u.idUsuario=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Financeiro(
                            rs.getInt("idUsuario"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("email"),
                            rs.getString("telefone"),
                            null,
                            rs.getString("senha"),
                            "Financeiro",
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
