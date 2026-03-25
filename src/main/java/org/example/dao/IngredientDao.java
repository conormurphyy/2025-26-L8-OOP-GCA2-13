package org.example.dao;

import org.example.domain.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientDao {

    List<Ingredient> getAllIngredients() throws Exception;

    Optional<Ingredient> getIngredientById(int ingredientId) throws Exception;

    boolean addIngredient(Ingredient ingredient) throws Exception;
    boolean updateIngredient(Ingredient ingredient) throws Exception;
    boolean deleteIngredient(int ingredientId) throws Exception;
    List<Ingredient> filterIngredients(String name, Double maxCalories, Double minProtein) throws Exception;

}

