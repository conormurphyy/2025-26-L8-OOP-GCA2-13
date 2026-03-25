package org.example.dao.jdbc;

import org.example.dao.IngredientDao;
import org.example.domain.Ingredient;

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
                    rs.getString("ingredient_name"),
                    rs.getDouble("ingredient_calories"),
                    rs.getDouble("ingredient_protein"),
                    rs.getDouble("ingredient_carbs"),
                    rs.getDouble("ingredient_fats")
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
                    rs.getString("ingredient_name"),
                    rs.getDouble("ingredient_calories"),
                    rs.getDouble("ingredient_protein"),
                    rs.getDouble("ingredient_carbs"),
                    rs.getDouble("ingredient_fats")
            ));
        }
        return Optional.empty();
    }

    @Override
    public boolean addIngredient(Ingredient ingredient) throws Exception {
        String sql = "INSERT INTO ingredient (ingredient_id, ingredient_name, ingredient_calories, ingredient_protein, ingredient_carbs, ingredient_fats) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, ingredient.getIngredientId());
        ps.setString(2, ingredient.getName());
        ps.setDouble(3, ingredient.getCalories());
        ps.setDouble(4, ingredient.getProtein());
        ps.setDouble(5, ingredient.getCarbs());
        ps.setDouble(6, ingredient.getFat());
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