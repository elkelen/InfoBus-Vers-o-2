package projetoinfobus_versao2.dao;

import projetoinfobus_versao2.model.Motorista;
import projetoinfobus_versao2.db.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MotoristaDAO {

    // --- INSERIR MOTORISTA ---
    public void inserir(Motorista m) {
        String sqlUsuario = "INSERT INTO usuario (nome, cpf, telefone, email, senha, tipo) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlFuncionario = "INSERT INTO funcionario (idFuncionario, matricula, cargo, salario) VALUES (?, ?, ?, ?)";
        String sqlMotorista = "INSERT INTO motorista (idMotorista, cnh) VALUES (?, ?)";

        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false); // iniciar transação

            // 1) Inserir na tabela usuario
            try (PreparedStatement ps = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, m.getNome());
                ps.setString(2, m.getCpf());
                ps.setString(3, m.getTelefone());
                ps.setString(4, m.getEmail());
                ps.setString(5, m.getSenha());
                ps.setString(6, "Motorista");

                ps.executeUpdate();

                // pegar id gerado
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        m.setId(rs.getInt(1));
                    }
                }
            }

            // 2) Inserir na tabela funcionario
            try (PreparedStatement ps2 = conn.prepareStatement(sqlFuncionario)) {
                ps2.setInt(1, m.getId());
                ps2.setString(2, m.getMatricula());
                ps2.setString(3, m.getCargo());
                ps2.setDouble(4, m.getSalario());

                ps2.executeUpdate();
            }

            // 3) Inserir na tabela motorista
            try (PreparedStatement ps3 = conn.prepareStatement(sqlMotorista)) {
                ps3.setInt(1, m.getId());
                ps3.setString(2, m.getCnh());
                ps3.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- LISTAR TODOS MOTORISTAS ---
    public List<Motorista> listarTodos() {
        List<Motorista> lista = new ArrayList<>();
        String sql = "SELECT u.idUsuario, u.nome, u.cpf, u.email, u.telefone, u.senha, " +
                     "f.matricula, f.cargo, f.salario, m.cnh, f.statusPagamento " +
                     "FROM usuario u " +
                     "JOIN funcionario f ON u.idUsuario = f.idFuncionario " +
                     "JOIN motorista m ON f.idFuncionario = m.idMotorista";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Motorista m = new Motorista(
                        rs.getInt("idUsuario"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        null, // login (não existe na tabela)
                        rs.getString("senha"),
                        "Motorista",
                        rs.getString("matricula"),
                        rs.getString("cargo"),
                        rs.getDouble("salario"),
                        rs.getString("cnh"),
                        rs.getString("statusPagamento")
                );
                lista.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // --- ATUALIZAR MOTORISTA ---
    public void atualizar(Motorista m) {
        String sqlUsuario = "UPDATE usuario SET nome=?, cpf=?, telefone=?, email=?, senha=?, tipo=? WHERE idUsuario=?";
        String sqlFuncionario = "UPDATE funcionario SET matricula=?, cargo=?, salario=? WHERE idFuncionario=?";
        String sqlMotorista = "UPDATE motorista SET cnh=? WHERE idMotorista=?";

        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);

            // Atualizar usuario
            try (PreparedStatement ps = conn.prepareStatement(sqlUsuario)) {
                ps.setString(1, m.getNome());
                ps.setString(2, m.getCpf());
                ps.setString(3, m.getTelefone());
                ps.setString(4, m.getEmail());
                ps.setString(5, m.getSenha());
                ps.setString(6, "Motorista");
                ps.setInt(7, m.getId());
                ps.executeUpdate();
            }

            // Atualizar funcionario
            try (PreparedStatement ps2 = conn.prepareStatement(sqlFuncionario)) {
                ps2.setString(1, m.getMatricula());
                ps2.setString(2, m.getCargo());
                ps2.setDouble(3, m.getSalario());
                ps2.setInt(4, m.getId());
                ps2.executeUpdate();
            }

            // Atualizar motorista
            try (PreparedStatement ps3 = conn.prepareStatement(sqlMotorista)) {
                ps3.setString(1, m.getCnh());
                ps3.setInt(2, m.getId());
                ps3.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- DELETAR MOTORISTA ---
    public void deletar(int id) {
        String sqlMotorista = "DELETE FROM motorista WHERE idMotorista=?";
        String sqlFuncionario = "DELETE FROM funcionario WHERE idFuncionario=?";
        String sqlUsuario = "DELETE FROM usuario WHERE idUsuario=?";

        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sqlMotorista)) {
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

    // --- BUSCAR MOTORISTA POR ID ---
    public Motorista buscarPorId(int id) {
        String sql = "SELECT u.idUsuario, u.nome, u.cpf, u.email, u.telefone, u.senha, " +
                     "f.matricula, f.cargo, f.salario, m.cnh, f.statusPagamento " +
                     "FROM usuario u " +
                     "JOIN funcionario f ON u.idUsuario = f.idFuncionario " +
                     "JOIN motorista m ON f.idFuncionario = m.idMotorista " +
                     "WHERE u.idUsuario=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Motorista(
                            rs.getInt("idUsuario"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("email"),
                            rs.getString("telefone"),
                            null,
                            rs.getString("senha"),
                            "Motorista",
                            rs.getString("matricula"),
                            rs.getString("cargo"),
                            rs.getDouble("salario"),
                            rs.getString("cnh"),
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
