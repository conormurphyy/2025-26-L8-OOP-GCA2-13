package org.example.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class UserDbSmokeTest {
    public static void main(String[] args) throws Exception {
      String url = "jdbc:mysql://localhost:3306/recipehub?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

      String sql = "SELECT * FROM users";
      String user = "recipehub_user";
      String password = "user";

      try(Connection connection = DriverManager.getConnection(url, user, password);
          PreparedStatement ps = connection.prepareStatement(sql);
          ResultSet rs = ps.executeQuery())

      {
          rs.next();
          String value = rs.getString("username");
          System.out.println(value);
      }



    }
}
