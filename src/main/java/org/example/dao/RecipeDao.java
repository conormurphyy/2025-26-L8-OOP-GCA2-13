package org.example.dao;

import org.example.domain.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeDao {

    List<Recipe> getAllRecipes() throws Exception;
    Optional<Recipe> getRecipeById(int recipeID) throws Exception;
    Optional<Object> getRecipeByCategoryId(int categoryID) throws Exception;
    Optional<Object> getRecipeByUserId(int userID) throws Exception;

    boolean addRecipe(Recipe recipe) throws Exception;
    boolean updateRecipe(Recipe recipe) throws Exception;
    boolean deleteRecipe(int recipeID) throws Exception;

    List<Recipe> getRecipeByCalories(double min, double max) throws Exception;
    List<Recipe> getRecipeByName(String recipeName) throws Exception;

    List<Recipe> getPublicRecipes() throws Exception;



}
