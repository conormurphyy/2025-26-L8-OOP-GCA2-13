package org.example.service;

import org.example.dao.RecipeDao;
import org.example.domain.Recipe;

import java.util.List;
import java.util.Optional;

/**
 * @author Conor Murphy
 */
public class RecipeService {

    private RecipeDao dao;

    public RecipeService(RecipeDao dao) {
        if (dao == null)
            throw new IllegalArgumentException("dao is null");

        this.dao = dao;
    }

    /**
     * Exception and error handling create recipe before it is passed into dao
     *
     * @param recipe
     * @return
     * @throws Exception
     */
    public boolean createRecipe(Recipe recipe) throws Exception {
        if (recipe == null)
            return false;

        if (recipe.getRecipeName() == null || recipe.getRecipeName().isBlank())
            return false;

        if (recipe.getTotalCalories() < 0)
            return false;

        return this.dao.addRecipe(recipe);
    }

    /**
     * Exception handling and error checking update recipe before it passes into dao.
     * Handles passing recipe to update recipe.
     *
     * @param recipe
     * @return
     * @throws Exception
     */
    public boolean updateRecipe(Recipe recipe) throws Exception {
        if (recipe == null)
            return false;

        return this.dao.updateRecipe(recipe);
    }

    /**
     * Handles passing delete recipe into dao.
     *
     * @param recipeID
     * @return
     * @throws Exception
     */
    public boolean deleteRecipe(int recipeID) throws Exception {
        return this.dao.deleteRecipe(recipeID);
    }

    /**
     * Handles getRecipeByID before its passed into dao
     * @param recipeID
     * @return
     * @throws Exception
     */
    public Optional<Recipe> getRecipe(int recipeID) throws Exception {
        return this.dao.getRecipeById(recipeID);
    }

    /**
     * Handles listAllRecipes before its passed into dao
     * @return
     * @throws Exception
     */
    public List<Recipe> listAllRecipes() throws Exception {
        return this.dao.getAllRecipes();
    }

    /**
     * Handles getPublic Recipes before its passed into dao.
     *
     * @return
     * @throws Exception
     */
    public List<Recipe> getPublicRecipes() throws Exception {
        return this.dao.getPublicRecipes();
    }

    /**
     * Error handling and handles searchByName before its passed into recipes.
     * @param name
     * @return
     * @throws Exception
     */
    public List<Recipe> searchByName(String name) throws Exception {
        if (name == null || name.isBlank())
            return List.of();

        return this.dao.getRecipeByName(name);
    }

    /**
     * Error handling and handles searchByCalories before its passed into recipes.
     * @param min
     * @param max
     * @return
     * @throws Exception
     */
    public List<Recipe> searchByCalories(double min, double max) throws Exception {
        if (min < 0 || max < 0 || min > max)
            return List.of();

        return this.dao.getRecipeByCalories(min, max);
    }
}
