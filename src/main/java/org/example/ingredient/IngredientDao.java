package org.example.ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientDao {

    List<Ingredient> getAllIngredients() throws Exception;

    Optional<Ingredient> getIngredientById(int ingredientId) throws Exception;

    boolean addIngredient(Ingredient ingredient) throws Exception;
    boolean updateIngredient(Ingredient ingredient) throws Exception;
    boolean deleteIngredient(int ingredientId) throws Exception;

}