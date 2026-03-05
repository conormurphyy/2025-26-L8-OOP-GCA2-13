package org.example.recipe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbSmokeTest {

    public static void main(String[] args) throws Exception {

        String url = "jdbc:mysql://localhost:3306/recipehub?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String user = "admin";
        String pass = "root";

        String sql = "SELECT 1";

        try (Connection c = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            rs.next();
            int value = rs.getInt(1);

            System.out.println("DB connection OK, SELECT 1 -> " + value);
        }
    }
}