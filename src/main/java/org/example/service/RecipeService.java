package org.example.service;

import org.example.dao.RecipeDao;
import org.example.domain.Recipe;

import java.util.List;
import java.util.Optional;

public class RecipeService {

    private RecipeDao dao;

    public RecipeService(RecipeDao dao) {
        if (dao == null)
            throw new IllegalArgumentException("dao is null");

        this.dao = dao;
    }

    public boolean createRecipe(Recipe recipe) throws Exception {
        if (recipe == null)
            return false;

        if (recipe.getRecipeName() == null || recipe.getRecipeName().isBlank())
            return false;

        if (recipe.getTotalCalories() < 0)
            return false;

        return this.dao.addRecipe(recipe);
    }

    public boolean updateRecipe(Recipe recipe) throws Exception {
        if (recipe == null)
            return false;

        return this.dao.updateRecipe(recipe);
    }

    public boolean deleteRecipe(int recipeID) throws Exception {
        return this.dao.deleteRecipe(recipeID);
    }

    public Optional<Recipe> getRecipe(int recipeID) throws Exception {
        return this.dao.getRecipeById(recipeID);
    }

    public List<Recipe> listAllRecipes() throws Exception {
        return this.dao.getAllRecipes();
    }

    public List<Recipe> getPublicRecipes() throws Exception {
        return this.dao.getPublicRecipes();
    }

    public List<Recipe> searchByName(String name) throws Exception {
        if (name == null || name.isBlank())
            return List.of();

        return this.dao.getRecipeByName(name);
    }

    public List<Recipe> searchByCalories(double min, double max) throws Exception {
        if (min < 0 || max < 0 || min > max)
            return List.of();

        return this.dao.getRecipeByCalories(min, max);
    }
}
