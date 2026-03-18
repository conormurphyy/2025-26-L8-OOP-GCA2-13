package org.example.recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeDao {

    List<Recipe> getAllRecipes() throws Exception;
    Optional<Recipe> getRecipeById(int recipeID) throws Exception;
    List<Recipe> getRecipeByCategoryId(int categoryID) throws Exception;
    List<Recipe> getRecipeByUserId(int userID) throws Exception;

    Recipe addRecipe(Recipe recipe) throws Exception;
    Recipe updateRecipe(Recipe recipe) throws Exception;
    boolean deleteRecipe(int recipeID) throws Exception;

    List<Recipe> getRecipeByCalories(double min, double max) throws Exception;
    List<Recipe> getRecipeByName(String recipeName) throws Exception;

    List<Recipe> getPublicRecipes() throws Exception;



}
