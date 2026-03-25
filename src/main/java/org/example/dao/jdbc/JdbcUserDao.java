package org.example.dao.jdbc;

import org.example.dao.UserDao;
import org.example.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
        public User insert(User user) throws Exception {
        if(user == null)
        {
            throw new IllegalArgumentException("user must not be null");
        }
        if(user.getUsername() == null || user.getUsername().isEmpty())
        {
            throw new IllegalArgumentException("username must not be empty");
        }
        if(user.getUserType() == null || user.getUserType().isEmpty())
        {
            throw new IllegalArgumentException("type must not be empty");
        }
        if(user.getUserRating() <0 || user.getUserRating() >5)
        {
            throw new IllegalArgumentException("rating must be between 0 and 5");
        }
        String sql = "INSERT INTO users (id, username, userType, userRating) VALUES (?, ?, ?, ?)";


        try (Connection c = open();
        PreparedStatement ps =c.prepareStatement(sql);
        ){
            ps.setInt(1, user.getId());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getUserType());
            ps.setDouble(4, user.getUserRating());
            int  rows =ps.executeUpdate();

            if(rows!=1)
            {
                throw new IllegalStateException("insert failed");
            }
            return user;
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

    @Override
    public User updateAll(User user) throws Exception {
        if (user == null) {
            throw new IllegalArgumentException("user must not be null");
        }
        if (user.getId() <= 0) {
            throw new IllegalArgumentException("invalid id");
        }
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("username must not be empty");
        }
        if (user.getUserType() == null || user.getUserType().isEmpty()) {
            throw new IllegalArgumentException("userType must not be empty");
        }
        if (user.getUserRating() < 0 || user.getUserRating() > 5) {
            throw new IllegalArgumentException("rating must be between 0 and 5");
        }

        String sql = "UPDATE users SET username = ?, userType = ?, userRating = ? WHERE id = ?";

        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getUserType());
            ps.setDouble(3, user.getUserRating());
            ps.setInt(4, user.getId());

            int rows = ps.executeUpdate();

            if (rows != 1) {
                throw new IllegalStateException("Update failed");
            }

            return user;
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

    @Override
    public List<User> filterUsers(String userType, Double minRating) throws Exception {
        List<User> allUsers = findAll();
        List<User> filtered = new ArrayList<>();

        for (User user : allUsers) {
            boolean match = true;
            if (userType != null && !user.getUserType().equals(userType)) {
                match = false;
            }
            if (minRating != null && user.getUserRating() < minRating) {
                match = false;
            }
            if (match) {
                filtered.add(user);
            }
        }
        return filtered;
    }

    private static User mapRow(ResultSet rs) throws SQLException {
        int id =rs.getInt("id");
        String username = rs.getString("username");
        String userType = rs.getString("userType");
        double userRating = rs.getDouble("userRating");
        return new User(id, username, userType, userRating);
    }


}
