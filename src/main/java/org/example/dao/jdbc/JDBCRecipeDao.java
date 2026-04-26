package org.example.dao.jdbc;

import org.example.dao.RecipeDao;
import org.example.domain.Recipe;
import org.example.domain.RecipeImageData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCRecipeDao implements RecipeDao {

    private final String _url;
    private final String _user;
    private final String _pass;

    public JDBCRecipeDao(String url, String user, String pass) {
        if (url == null || url.isBlank())
            throw new IllegalArgumentException("url is required");

        _url = url.trim();
        _user = user;
        _pass = pass;
    }

    private Connection open() throws SQLException {
        return DriverManager.getConnection(_url, _user, _pass);
    }

    @Override
    public List<Recipe> getAllRecipes() throws Exception {
        String sql = "SELECT * FROM recipe ORDER BY recipe_id";
        List<Recipe> recipes = new ArrayList<>();

        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                recipes.add(new Recipe(
                        rs.getInt("recipe_id"),
                        rs.getInt("user_id"),
                        rs.getString("recipe_name"),
                        rs.getInt("category_id"),
                        rs.getString("description"),
                        rs.getDouble("total_calories"),
                        rs.getBoolean("is_public"),
                        rs.getBytes("recipe_image"),
                        rs.getString("image_file_name"),
                        rs.getString("image_content_type"),
                        rs.getInt("image_size")
                ));
            }
        }
        return recipes;
    }




    @Override
    public List<Recipe> filterRecipes(Boolean isPublic, Double minCalories) throws Exception {
        List<Recipe> allRecipes = getAllRecipes();
        List<Recipe> filtered = new ArrayList<>();

        for (Recipe recipe : allRecipes) {
            boolean match = true;
            if (isPublic != null && recipe.getIsPublic() != isPublic) {
                match = false;
            }
            if (minCalories != null && recipe.getTotalCalories() < minCalories) {
                match = false;
            }
            if (match) {
                filtered.add(recipe);
            }
        }
        return filtered;
    }


    @Override
    public Optional<Recipe> getRecipeById(int recipeID) throws Exception {
        if (recipeID <= 0) return Optional.empty();

        String sql = "SELECT * FROM recipe WHERE recipe_id = ?";
        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, recipeID);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();

                return Optional.of(new Recipe(
                        rs.getInt("recipe_id"),
                        rs.getInt("user_id"),
                        rs.getString("recipe_name"),
                        rs.getInt("category_id"),
                        rs.getString("description"),
                        rs.getDouble("total_calories"),
                        rs.getBoolean("is_public"),
                        rs.getBytes("recipe_image"),
                        rs.getString("image_file_name"),
                        rs.getString("image_content_type"),
                        rs.getInt("image_size")
                ));
            }
        }
    }

    @Override
    public Optional<Object> getRecipeByCategoryId(int categoryID) throws Exception {
        if (categoryID <= 0) return Optional.empty();

        String sql = "SELECT * FROM recipe WHERE category_id = ?";
        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, categoryID);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();

                return Optional.of(new Recipe(
                        rs.getInt("recipe_id"),
                        rs.getInt("user_id"),
                        rs.getString("recipe_name"),
                        rs.getInt("category_id"),
                        rs.getString("description"),
                        rs.getDouble("total_calories"),
                        rs.getBoolean("is_public"),
                        rs.getBytes("recipe_image"),
                        rs.getString("image_file_name"),
                        rs.getString("image_content_type"),
                        rs.getInt("image_size")
                ));
            }
        }
    }

    @Override
    public Optional<Object> getRecipeByUserId(int userID) throws Exception {
        if (userID <= 0) return Optional.empty();

        String sql = "SELECT * FROM recipe WHERE user_id = ?";
        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();

                return Optional.of(new Recipe(
                        rs.getInt("recipe_id"),
                        rs.getInt("user_id"),
                        rs.getString("recipe_name"),
                        rs.getInt("category_id"),
                        rs.getString("description"),
                        rs.getDouble("total_calories"),
                        rs.getBoolean("is_public"),
                        rs.getBytes("recipe_image"),
                        rs.getString("image_file_name"),
                        rs.getString("image_content_type"),
                        rs.getInt("image_size")

                ));
            }
        }
    }

    @Override
    public boolean addRecipe(Recipe recipe) throws Exception {
        String sql = "INSERT INTO recipe ( user_id, recipe_name, category_id, description, total_calories, is_public, recipe_image,image_file_name,image_content_type,image_size) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql)) {


            ps.setInt(2, recipe.getUserId());
            ps.setString(3, recipe.getRecipeName());
            ps.setInt(4, recipe.getCategoryId());
            ps.setString(5, recipe.getDescription());
            ps.setDouble(6, recipe.getTotalCalories());
            ps.setBoolean(7, recipe.getIsPublic());
            ps.setBytes(8, recipe.getRecipeImage());
            ps.setString(9, recipe.getImageFileName());
            ps.setString(10, recipe.getImageContentType());
            ps.setInt(11, recipe.getImageSize());

            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public boolean updateRecipe(Recipe recipe) throws Exception {
        String sql = "UPDATE recipe SET user_id=?, recipe_name=?, category_id=?, description=?, total_calories=?, is_public=? WHERE recipe_id=?";
        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, recipe.getUserId());
            ps.setString(2, recipe.getRecipeName());
            ps.setInt(3, recipe.getCategoryId());
            ps.setString(4, recipe.getDescription());
            ps.setDouble(5, recipe.getTotalCalories());
            ps.setBoolean(6, recipe.getIsPublic());
            ps.setInt(7, recipe.getRecipeId());
            ps.setBytes(8, recipe.getRecipeImage());
            ps.setString(9, recipe.getImageFileName());
            ps.setString(10, recipe.getImageContentType());
            ps.setInt(11, recipe.getImageSize());

            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public boolean deleteRecipe(int recipeID) throws Exception {
        if (recipeID <= 0) return false;

        String sql = "DELETE FROM recipe WHERE recipe_id=?";
        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, recipeID);
            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public List<Recipe> getRecipeByCalories(double min, double max) throws Exception {
        String sql = "SELECT * FROM recipe WHERE total_calories BETWEEN ? AND ? ORDER BY recipe_id";
        List<Recipe> recipes = new ArrayList<>();

        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDouble(1, min);
            ps.setDouble(2, max);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    recipes.add(new Recipe(
                            rs.getInt("recipe_id"),
                            rs.getInt("user_id"),
                            rs.getString("recipe_name"),
                            rs.getInt("category_id"),
                            rs.getString("description"),
                            rs.getDouble("total_calories"),
                            rs.getBoolean("is_public"),
                            rs.getBytes("recipe_image"),
                            rs.getString("image_file_name"),
                            rs.getString("image_content_type"),
                            rs.getInt("image_size")
                    ));
                }
            }
        }
        return recipes;
    }

    @Override
    public List<Recipe> getRecipeByName(String recipeName) throws Exception {
        String sql = "SELECT * FROM recipe WHERE recipe_name LIKE ? ORDER BY recipe_id";
        List<Recipe> recipes = new ArrayList<>();

        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, "%" + recipeName + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    recipes.add(new Recipe(
                            rs.getInt("recipe_id"),
                            rs.getInt("user_id"),
                            rs.getString("recipe_name"),
                            rs.getInt("category_id"),
                            rs.getString("description"),
                            rs.getDouble("total_calories"),
                            rs.getBoolean("is_public"),
                            rs.getBytes("recipe_image"),
                            rs.getString("image_file_name"),
                            rs.getString("image_content_type"),
                            rs.getInt("image_size")
                    ));
                }
            }
        }
        return recipes;
    }

    @Override
    public List<Recipe> getPublicRecipes() throws Exception {
        String sql = "SELECT * FROM recipe WHERE is_public = TRUE ORDER BY recipe_id";
        List<Recipe> recipes = new ArrayList<>();

        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                recipes.add(new Recipe(
                        rs.getInt("recipe_id"),
                        rs.getInt("user_id"),
                        rs.getString("recipe_name"),
                        rs.getInt("category_id"),
                        rs.getString("description"),
                        rs.getDouble("total_calories"),
                        rs.getBoolean("is_public"),
                        rs.getBytes("recipe_image"),
                        rs.getString("image_file_name"),
                        rs.getString("image_content_type"),
                        rs.getInt("image_size")
                ));
            }
        }
        
        return recipes;
    }

    @Override
    public void saveImage(int recipeId, byte[] imageData, String fileName, String contentType, int fileSize) throws Exception {
        String sql = """
        UPDATE recipe
        SET recipe_image = ?, image_file_name = ?, image_content_type = ?, image_size = ?
        WHERE recipe_id = ?
        """;

        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setBytes(1, imageData);
            ps.setString(2, fileName);
            ps.setString(3, contentType);
            ps.setInt(4, fileSize);
            ps.setInt(5, recipeId);
            ps.executeUpdate();
        }
    }

    @Override
    public Optional<RecipeImageData> getImageById(int recipeId) throws Exception {
        String sql = """
        SELECT recipe_image, image_file_name, image_content_type, image_size
        FROM recipe
        WHERE recipe_id = ?
        """;

        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, recipeId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }

                return Optional.of(new RecipeImageData(
                        rs.getBytes("recipe_image"),
                        rs.getString("image_file_name"),
                        rs.getString("image_content_type"),
                        rs.getInt("image_size")
                ));
            }
        }
    }

    @Override
    public Optional<RecipeImageData> getRecipeImageMetadata(int recipeId) throws Exception {
        if (recipeId <= 0) {
            return Optional.empty();
        }

        String sql = """
            SELECT image_file_name, image_content_type, image_size
            FROM recipe
            WHERE recipe_id = ?
            """;

        try (Connection c = open();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, recipeId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }

                return Optional.of(new RecipeImageData(
                        null,
                        rs.getString("image_file_name"),
                        rs.getString("image_content_type"),
                        rs.getInt("image_size")
                ));
            }
        }
    }
}