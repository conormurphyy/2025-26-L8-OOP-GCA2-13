package org.example.dao;

import java.util.List;
import java.util.Optional;

import org.example.domain.Recipe;
import org.example.domain.RecipeImageData;

/**
 * @author Conor Murphy
 * @author Richie (filter recipes, save image, getImageById)
 */
public interface RecipeDao {

    /**
     *
     * @return
     * @throws Exception
     */
    List<Recipe> getAllRecipes() throws Exception;

    /**
     *
     * @param recipeID
     * @return
     * @throws Exception
     */
    Optional<Recipe> getRecipeById(int recipeID) throws Exception;

    /**
     *
     * @param categoryID
     * @return
     * @throws Exception
     */
    Optional<Object> getRecipeByCategoryId(int categoryID) throws Exception;

    /**
     *
     * @param userID
     * @return
     * @throws Exception
     */
    Optional<Object> getRecipeByUserId(int userID) throws Exception;

    /**
     *
     * @param recipe
     * @return
     * @throws Exception
     */
    boolean addRecipe(Recipe recipe) throws Exception;

    /**
     *
     * @param recipe
     * @return
     * @throws Exception
     */
    boolean updateRecipe(Recipe recipe) throws Exception;

    /**
     *
     * @param recipeID
     * @return
     * @throws Exception
     */
    boolean deleteRecipe(int recipeID) throws Exception;

    /**
     *
     * @param min
     * @param max
     * @return
     * @throws Exception
     */
    List<Recipe> getRecipeByCalories(double min, double max) throws Exception;

    /**
     *
     * @param recipeName
     * @return
     * @throws Exception
     */
    List<Recipe> getRecipeByName(String recipeName) throws Exception;

    /**
     *
     * @return
     * @throws Exception
     */
    List<Recipe> getPublicRecipes() throws Exception;


    List<Recipe> filterRecipes(Boolean isPublic, Double minCalories) throws Exception;


    void saveImage(int recipeId, byte[] imageData, String fileName, String contentType, int fileSize) throws Exception;
    Optional<RecipeImageData> getImageById(int recipeId) throws Exception;

    /**
     *
     * @param recipeId
     * @return
     * @throws Exception
     */
    Optional<RecipeImageData> getRecipeImageMetadataById(int recipeId) throws Exception;


    /**
     *
     * @param recipeId
     * @return
     * @throws Exception
     */
    Optional<RecipeImageData> getRecipeImageMetadata(int recipeId) throws Exception;
}
