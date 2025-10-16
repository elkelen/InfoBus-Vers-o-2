/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetoinfobus_versao2.testes;

import java.sql.Connection;
import java.sql.DriverManager;

public class TesteConexao {
    public static void main(String[] args) {
        try {
            Connection conexao = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/infobus2",
                "root",
                "root"
            );
            System.out.println("✅ Conexão bem-sucedida!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

