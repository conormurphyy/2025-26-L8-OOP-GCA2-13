package org.example.ingredient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCIngredientDao implements IngredientDao {

    private Connection connection;

    public JDBCIngredientDao(String url, String user, String pass) throws SQLException {
        this.connection = DriverManager.getConnection(url, user, pass);
    }

    @Override
    public List<Ingredient> getAllIngredients() throws Exception {
        List<Ingredient> ingredients = new ArrayList<>();
        String sql = "SELECT * FROM ingredient";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            ingredients.add(new Ingredient(
                    rs.getInt("ingredient_id"),
                    rs.getString("name"),
                    rs.getDouble("calories"),
                    rs.getDouble("protein"),
                    rs.getDouble("carbs"),
                    rs.getDouble("fat")
            ));
        }
        return ingredients;
    }

    @Override
    public Optional<Ingredient> getIngredientById(int ingredientId) throws Exception {
        String sql = "SELECT * FROM ingredient WHERE ingredient_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, ingredientId);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return Optional.of(new Ingredient(
                    rs.getInt("ingredient_id"),
                    rs.getString("name"),
                    rs.getDouble("calories"),
                    rs.getDouble("protein"),
                    rs.getDouble("carbs"),
                    rs.getDouble("fat")
            ));
        }
        return Optional.empty();
    }

    @Override
    public boolean addIngredient(Ingredient ingredient) throws Exception {
        String sql = "INSERT INTO ingredient (name, calories, protein, carbs, fat) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, ingredient.getName());
        ps.setDouble(2, ingredient.getCalories());
        ps.setDouble(3, ingredient.getProtein());
        ps.setDouble(4, ingredient.getCarbs());
        ps.setDouble(5, ingredient.getFat());
        return ps.executeUpdate() > 0;
    }

    @Override
    public boolean updateIngredient(Ingredient ingredient) throws Exception {
        String sql = "UPDATE ingredient SET name=?, calories=?, protein=?, carbs=?, fat=? WHERE ingredient_id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, ingredient.getName());
        ps.setDouble(2, ingredient.getCalories());
        ps.setDouble(3, ingredient.getProtein());
        ps.setDouble(4, ingredient.getCarbs());
        ps.setDouble(5, ingredient.getFat());
        ps.setInt(6, ingredient.getIngredientId());
        return ps.executeUpdate() > 0;
    }

    @Override
    public boolean deleteIngredient(int ingredientId) throws Exception {
        String sql = "DELETE FROM ingredient WHERE ingredient_id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, ingredientId);
        return ps.executeUpdate() > 0;
    }
}