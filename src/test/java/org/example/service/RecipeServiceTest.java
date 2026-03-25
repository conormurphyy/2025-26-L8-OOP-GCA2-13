package org.example.service;

import org.example.dao.RecipeDao;
import org.example.domain.Recipe;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RecipeServiceTest {

    static class TestDao implements RecipeDao {
        boolean addCalled = false;
        boolean updateCalled = false;
        boolean deleteCalled = false;

        @Override
        public boolean addRecipe(Recipe recipe) { addCalled = true; return true; }
        @Override
        public boolean updateRecipe(Recipe recipe) { updateCalled = true; return true; }
        @Override
        public boolean deleteRecipe(int recipeID) { deleteCalled = true; return true; }
        @Override
        public Optional<Recipe> getRecipeById(int recipeID) { return Optional.of(new Recipe()); }

        @Override
        public Optional<Object> getRecipeByCategoryId(int categoryID) throws Exception {
            return Optional.empty();
        }

        @Override
        public Optional<Object> getRecipeByUserId(int userID) throws Exception {
            return Optional.empty();
        }

        @Override
        public List<Recipe> getAllRecipes() { return List.of(new Recipe()); }
        @Override
        public List<Recipe> getPublicRecipes() { return List.of(new Recipe()); }
        @Override
        public List<Recipe> getRecipeByName(String name) { return List.of(new Recipe()); }
        @Override
        public List<Recipe> getRecipeByCalories(double min, double max) { return List.of(new Recipe()); }
    }

    @Test
    void testRecipeService() throws Exception {
        TestDao dao = new TestDao();
        RecipeService service = new RecipeService(dao);

        Recipe r = new Recipe();
        r.setRecipeName("Pizza");
        r.setTotalCalories(500);

        assertTrue(service.createRecipe(r));
        assertFalse(service.createRecipe(null));
        r.setRecipeName(" ");
        assertFalse(service.createRecipe(r));
        r.setRecipeName(null);
        assertFalse(service.createRecipe(r));
        r.setRecipeName("Soup");
        r.setTotalCalories(-1);
        assertFalse(service.createRecipe(r));

        assertTrue(service.updateRecipe(r));
        assertFalse(service.updateRecipe(null));

        assertTrue(service.deleteRecipe(1));

        assertTrue(service.getRecipe(1).isPresent());

        assertEquals(1, service.listAllRecipes().size());

        assertEquals(1, service.getPublicRecipes().size());

        assertEquals(1, service.searchByName("Cake").size());
        assertTrue(service.searchByName(" ").isEmpty());
        assertTrue(service.searchByName(null).isEmpty());

        assertEquals(1, service.searchByCalories(100, 200).size());
        assertTrue(service.searchByCalories(200, 100).isEmpty());
        assertTrue(service.searchByCalories(-1, 100).isEmpty());
        assertTrue(service.searchByCalories(100, -1).isEmpty());
    }

    @Test
    void testConstructorNull() {
        assertThrows(IllegalArgumentException.class, () -> new RecipeService(null));
    }
}
