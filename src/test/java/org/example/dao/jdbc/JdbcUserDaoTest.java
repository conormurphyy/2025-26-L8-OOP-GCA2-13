package org.example.dao.jdbc;

import org.example.dao.jdbc.JdbcUserDao;
import org.example.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JdbcUserDaoTest {

    private static final String URL =
            "jdbc:mysql://localhost:3306/recipehub";
    private static final String USER = "root";
    private static final String PASS = "";

    private JdbcUserDao dao;

    @BeforeEach
    void setUp() throws Exception {
        dao = new JdbcUserDao(URL, USER, PASS);

        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement("DELETE FROM users")) {
            ps.executeUpdate();
        }
    }

    @Test
    void insertUser() throws Exception {
        User u = new User(1, "alice", "ADMIN", 4.5);
        User result = dao.insert(u);
        assertEquals(u, result);
    }

    @Test
    void findByIdInvalidUser() throws Exception {
        Optional<User> result = dao.findById(999);
        assertTrue(result.isEmpty());
    }

    @Test
    void findById_valid() throws Exception {
        dao.insert(new User(1, "bob", "USER", 3.0));
        Optional<User> result = dao.findById(1);
        assertTrue(result.isPresent());
        assertEquals("bob", result.get().getUsername());
    }

    @Test
    void findAll() throws Exception {
        dao.insert(new User(1, "a", "USER", 2.0));
        dao.insert(new User(2, "b", "ADMIN", 5.0));
        List<User> all = dao.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void updateRating_() throws Exception {
        dao.insert(new User(1, "c", "USER", 1.0));
        boolean ok = dao.updateRating(1, 4.0);
        assertTrue(ok);
        assertEquals(4.0, dao.findById(1).get().getUserRating());
    }

    @Test
    void updateType_() throws Exception {
        dao.insert(new User(1, "d", "USER", 2.0));
        boolean ok = dao.updateType(1, "ADMIN");
        assertTrue(ok);
        assertEquals("ADMIN", dao.findById(1).get().getUserType());
    }

    @Test
    void updateAll_() throws Exception {
        dao.insert(new User(1, "e", "USER", 2.0));
        User updated = new User(1, "eve", "ADMIN", 5.0);
        User result = dao.updateAll(updated);
        assertEquals(updated, result);
    }

    @Test
    void deleteById() throws Exception {
        dao.insert(new User(1, "f", "USER", 3.0));
        boolean deleted = dao.deleteById(1);
        assertTrue(deleted);
        assertTrue(dao.findById(1).isEmpty());
    }
}