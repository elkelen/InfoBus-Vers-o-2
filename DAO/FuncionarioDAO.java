package projetoinfobus_versao2.dao;

import projetoinfobus_versao2.model.Funcionario;
import projetoinfobus_versao2.db.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    // --- INSERIR ---
    public void inserir(Funcionario f) {
        String sqlUsuario = "INSERT INTO usuario (nome, cpf, telefone, email, senha, tipo) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlFuncionario = "INSERT INTO funcionario (idFuncionario, matricula, cargo, salario) VALUES (?, ?, ?, ?)";

        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false); // iniciar transação

            // 1) Inserir na tabela usuario
            try (PreparedStatement ps = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, f.getNome());
                ps.setString(2, f.getCpf());
                ps.setString(3, f.getTelefone());
                ps.setString(4, f.getEmail());
                ps.setString(5, f.getSenha());
                ps.setString(6, f.getTipo());

                ps.executeUpdate();

                // pegar id gerado
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

            conn.commit(); // confirmar transação

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- LISTAR TODOS ---
    public List<Funcionario> listarTodos() {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT u.idUsuario, u.nome, u.cpf, u.email, u.telefone, u.tipo, u.senha, " +
                     "f.matricula, f.cargo, f.salario, f.statusPagamento " +
                     "FROM usuario u JOIN funcionario f ON u.idUsuario = f.idFuncionario";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Funcionario f = new Funcionario(
                        rs.getInt("idUsuario"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        null, // login (não existe na tabela)
                        rs.getString("senha"),
                        rs.getString("tipo"),
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

    // --- ATUALIZAR ---
    public void atualizar(Funcionario f) {
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
                ps.setString(6, f.getTipo());
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

    // --- DELETAR ---
    public void deletar(int id) {
        String sqlFuncionario = "DELETE FROM funcionario WHERE idFuncionario=?";
        String sqlUsuario = "DELETE FROM usuario WHERE idUsuario=?";

        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sqlFuncionario)) {
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

    // --- BUSCAR POR ID ---
    public Funcionario buscarPorId(int id) {
        String sql = "SELECT u.idUsuario, u.nome, u.cpf, u.email, u.telefone, u.tipo, u.senha, " +
             "f.matricula, f.cargo, f.salario, f.statusPagamento " +
             "FROM usuario u JOIN funcionario f ON u.idUsuario = f.idFuncionario WHERE u.idUsuario=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Funcionario(
                            rs.getInt("idUsuario"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("email"),
                            rs.getString("telefone"),
                            null,
                            rs.getString("senha"),
                            rs.getString("tipo"),
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
    
    // --- ATUALIZAR STATUS DE PAGAMENTO ---
    public void atualizarStatusPagamento(int idFuncionario, String novoStatus) {
        String sql = "UPDATE funcionario SET statusPagamento=? WHERE idFuncionario=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, novoStatus);
            ps.setInt(2, idFuncionario);

            int linhasAtualizadas = ps.executeUpdate();
            if (linhasAtualizadas > 0) {
                System.out.println("Status de pagamento atualizado com sucesso!");
            } else {
                System.out.println("Funcionário não encontrado para atualizar pagamento.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
