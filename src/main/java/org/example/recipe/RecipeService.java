package org.example.recipe;

import java.util.List;
import java.util.Optional;

public class RecipeService {

    private RecipeDao _dao;

    public RecipeService(RecipeDao dao) {
        if (dao == null)
            throw new IllegalArgumentException("dao is null");

        _dao = dao;
    }

    public boolean createRecipe(Recipe recipe) throws Exception {
        if (recipe == null)
            return false;

        if (recipe.getRecipeName() == null || recipe.getRecipeName().isBlank())
            return false;

        if (recipe.getTotalCalories() < 0)
            return false;

        return _dao.addRecipe(recipe);
    }

    public boolean updateRecipe(Recipe recipe) throws Exception {
        if (recipe == null)
            return false;

        return _dao.updateRecipe(recipe);
    }

    public boolean deleteRecipe(int recipeID) throws Exception {
        return _dao.deleteRecipe(recipeID);
    }

    public Optional<Recipe> getRecipe(int recipeID) throws Exception {
        return _dao.getRecipeById(recipeID);
    }

    public List<Recipe> listAllRecipes() throws Exception {
        return _dao.getAllRecipes();
    }

    public List<Recipe> getPublicRecipes() throws Exception {
        return _dao.getPublicRecipes();
    }

    public List<Recipe> searchByName(String name) throws Exception {
        if (name == null || name.isBlank())
            return List.of();

        return _dao.getRecipeByName(name);
    }

    public List<Recipe> searchByCalories(double min, double max) throws Exception {
        if (min < 0 || max < 0 || min > max)
            return List.of();

        return _dao.getRecipeByCalories(min, max);
    }
}
