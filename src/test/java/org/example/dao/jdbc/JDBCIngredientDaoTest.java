package org.example.dao.jdbc;

import org.example.dao.jdbc.JDBCIngredientDao;
import org.example.domain.Ingredient;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JDBCIngredientDaoTest {

    private static final String URL = "jdbc:mysql://localhost:3306/gca2_test_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASS = "";

    private JDBCIngredientDao dao;

    @BeforeEach
    void setUp() throws Exception {
        dao = new JDBCIngredientDao(URL, USER, PASS);

        //Clear before test
        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement("DELETE FROM ingredient")) {
            ps.executeUpdate();
        }
    }

    @Test
    void getAllIngredients_emptyIngredient() throws Exception {
        List<Ingredient> all = dao.getAllIngredients();

        assertTrue(all.isEmpty());
    }

    @Test
    void addIngredient() throws Exception {
        Ingredient newIngredient = new Ingredient(1, "Chicken", 200, 30, 0, 5);

        boolean inserted = dao.addIngredient(newIngredient);

        assertTrue(inserted);
    }

    @Test
    void getIngredientById() throws Exception {
        Ingredient newIngredient = new Ingredient(1, "Rice", 180, 4, 40, 1);
        dao.addIngredient(newIngredient);

        Optional<Ingredient> found = dao.getIngredientById(1);

        assertTrue(found.isPresent());
        assertEquals("Rice", found.get().getName());
    }

    @Test
    void getIngredientById_doesntExist() throws Exception {
        Optional<Ingredient> found = dao.getIngredientById(9999);

        assertTrue(found.isEmpty());
    }

    @Test
    void getAllIngredients() throws Exception {
        dao.addIngredient(new Ingredient(1, "Chicken", 200, 30, 0, 5));
        dao.addIngredient(new Ingredient(2, "Rice", 180, 4, 40, 1));
        dao.addIngredient(new Ingredient(3, "Olive Oil", 120, 0, 0, 14));

        List<Ingredient> all = dao.getAllIngredients();

        assertEquals(3, all.size());
    }

    @Test
    void updateIngredient()throws Exception {
        dao.addIngredient(new Ingredient(1, "Milk", 100, 6, 12, 4));
        Ingredient updated = new Ingredient(1, "Skim Milk", 80, 8, 10, 0);

        boolean ok = dao.updateIngredient(updated);

        assertTrue(ok);
        assertEquals("Skim Milk", dao.getIngredientById(1).get().getName());
    }

    @Test
    void updateIngredientThatDoesntExist() throws Exception {
        Ingredient updated = new Ingredient(999, "Ghost", 0, 0, 0, 0);

        boolean ok = dao.updateIngredient(updated);

        assertFalse(ok);
    }

    @Test
    void deleteIngredient() throws Exception {
        dao.addIngredient(new Ingredient(1, "Eggs", 70, 6, 1, 5));

        boolean deleted = dao.deleteIngredient(1);

        assertTrue(deleted);
        assertTrue(dao.getIngredientById(1).isEmpty());
    }

    @Test
    void deleteIngredientDoesNotExist() throws Exception {
        boolean deleted = dao.deleteIngredient(9999);

        assertFalse(deleted);
    }

    @Test
    void filterIngredientsByName() throws Exception {
        dao.addIngredient(new Ingredient(1, "Chicken Breast", 200, 30, 0, 5));
        dao.addIngredient(new Ingredient(2, "Chicken Thigh", 240, 25, 0, 10));
        dao.addIngredient(new Ingredient(3, "Rice", 180, 4, 40, 1));

        List<Ingredient> result = dao.filterIngredients("chicken", null, null);

        assertEquals(2, result.size());
    }

    @Test
    void filterIngredients_setMaxCals() throws Exception {

        dao.addIngredient(new Ingredient(1, "Olive Oil", 120, 0, 0, 14));
        dao.addIngredient(new Ingredient(2, "Rice", 180, 4, 40, 1));

        List<Ingredient> result = dao.filterIngredients(null, 150.0, null);

        assertEquals(1, result.size());
        assertEquals("Olive Oil", result.get(0).getName());
    }

    @Test
    void filterIngredients_minProtein() throws Exception {
        dao.addIngredient(new Ingredient(1, "Chicken", 200, 30, 0, 5));
        dao.addIngredient(new Ingredient(2, "Rice", 180, 4, 40, 1));

        List<Ingredient> result = dao.filterIngredients(null, null, 10.0);

        assertEquals(1, result.size());
        assertEquals("Chicken", result.get(0).getName());
    }
}