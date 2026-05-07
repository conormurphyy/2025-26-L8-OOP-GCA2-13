package org.example.dao.jdbc;

import org.example.dao.jdbc.JDBCRecipeDao;
import org.example.domain.Recipe;
import org.example.domain.RecipeImageData;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JDBCRecipeDaoTest {

    private static final String URL =
            "jdbc:mysql://localhost:3306/recipehub";
    private static final String USER = "root";
    private static final String PASS = "";

    private JDBCRecipeDao dao;

    @BeforeEach
    void setUp() throws Exception {
        dao = new JDBCRecipeDao(URL, USER, PASS);

        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement("DELETE FROM recipe")) {
            ps.executeUpdate();
        }
    }

    private Recipe buildRecipe(int id, int userID, String name, int catID, String desc, double calories) {
        return new Recipe(
                id,
                userID,
                name,
                catID,
                desc,
                calories,
                false,
                null,
                null,
                null,
                0
        );
    }

    @Test
    void getAllRecipes_empty() throws Exception {
        List<Recipe> all = dao.getAllRecipes();

        assertTrue(all.isEmpty());
    }

    @Test
    void getRecipeById_invalidId() throws Exception {
        Optional<Recipe> result = dao.getRecipeById(0);

        assertTrue(result.isEmpty());
    }

    @Test
    void addRecipe_validRecipe() throws Exception {
        Recipe r = buildRecipe(1, 1,"Pasta", 500, "tst",1);

        boolean ok = dao.addRecipe(r);

        assertTrue(ok);
    }

    @Test
    void getAllRecipes_afterAdding() throws Exception {
        dao.addRecipe(buildRecipe(1, 1,"Pasta", 500, "test",1));
        dao.addRecipe(buildRecipe(2, 1,"Salad", 150, "tst",1));

        List<Recipe> all = dao.getAllRecipes();

        assertEquals(2, all.size());
    }

    @Test
    void deleteRecipe_validID() throws Exception {
        dao.addRecipe(buildRecipe(1, 1,"Test", 250, "test",1));
        int id = dao.getAllRecipes().get(0).getRecipeId();

        boolean deleted = dao.deleteRecipe(id);

        assertTrue(deleted);
    }

    @Test
    void addImageGetImageByID() throws Exception {
        dao.addRecipe(buildRecipe(4, 1,"TestImage", 200, "testIMage",1));
        int id = dao.getAllRecipes().get(0).getRecipeId();

        byte[] bytes = {1, 2, 3, 4};
        dao.saveImage(id, bytes, "test.png", "image/png", bytes.length);

        Optional<RecipeImageData> result = dao.getImageById(id);

        assertTrue(result.isPresent());
    }

    @Test
    void getRecipeImageMetadata_invalidID() throws Exception {
        Optional<RecipeImageData> result = dao.getRecipeImageMetadata(0);

        assertTrue(result.isEmpty());
    }
}