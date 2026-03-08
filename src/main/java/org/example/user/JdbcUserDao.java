package org.example.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Predicate;

public class JdbcUserDao implements UserDao {
    private String _url;
    private String _username;
    private String _password;

    public JdbcUserDao(String url, String username, String password) {

        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("url must not be empty");
        }

        _url = url.trim();
        _username = username;
        _password = password;

    }
        private Connection open() throws SQLException {
            return DriverManager.getConnection(_url, _username, _password);
        }

        @Override
        public int insert(String username) throws Exception {
        if(username ==null || username.isEmpty())
        {
            throw new IllegalArgumentException("username must not be empty");
        }
        String sql = "INSERT INTO users (username) VALUES (?)";


        try (Connection c = open();
        PreparedStatement ps =c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){
            ps.setString(1, username);
            int  rows =ps.executeUpdate();

            if(rows!=1)
            {
                throw new IllegalStateException("insert failed");
            }
            try(ResultSet keys = ps.getGeneratedKeys()){
                if(!keys.next())
                {
                    throw new IllegalStateException("insert failed");
                }
                return keys.getInt(1);
            }
        }
        }

        @Override
    public Optional<User> findById(int id) throws Exception {
        if(id<0)
        {
            return Optional.empty();
        }
        String sql = "SELECT * FROM users WHERE id = ?";
        try(Connection c =open();
        PreparedStatement ps = c.prepareStatement(sql))
        {
            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery())
            {
                if(!rs.next())
                {
                    return Optional.empty();
                }
                return Optional.of(mapRow(rs));
            }

        }
    }
    @Override
    public List<User> findAll() throws Exception {
        String sql = "SELECT * FROM users ORDER BY id";

        try(Connection c = open();
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()

        )
        {
            ArrayList<User> out = new ArrayList<>();
            while(rs.next())
            {
                out.add(mapRow(rs));
            }
            return out;
        }
    }
    @Override
    public boolean updateRating (int id,double newRating) throws Exception {
        if(id<0)
        {
            return false;
        }
        if(newRating<0 || newRating>5)
        {
            throw new IllegalArgumentException("newRating must be between 0 and 5");
        }

        String sql = "UPDATE users SET userRating = ? WHERE id = ?";

        try(Connection c =open();
        PreparedStatement ps = c.prepareStatement(sql)){
            ps.setDouble(1, newRating);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            return rows==1;
        }
    }
    @Override public boolean updateType (int id, String newType) throws Exception {
        if(id<0)
        {
            return false;
        }
        if(newType == null || newType.isEmpty())
        {
            throw new IllegalArgumentException("newType must not be empty");
        }
        String sql = "UPDATE users SET userType = ? WHERE id = ?";

        try(Connection c = open();
        PreparedStatement ps = c.prepareStatement(sql))
        {
            ps.setString(1, newType);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            return rows==1;
        }
    }
    @Override public boolean deleteById (int id) throws Exception {
        if(id<0)
        {
            return false;
        }
        String sql = "DELETE FROM users WHERE id = ?";

        try(Connection c = open();
        PreparedStatement ps = c.prepareStatement(sql))
        {
            ps.setInt(1, id);
            return ps.executeUpdate()==1;
        }
    }
    private static User mapRow(ResultSet rs) throws SQLException {
        int id =rs.getInt("id");
        String username = rs.getString("username");
        String userType = rs.getString("getUserType");
        double userRating = rs.getDouble("getUserRating");
        return new User(id, username, userType, userRating);
    }


}
