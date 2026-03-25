package org.example.service;

import org.example.dao.IngredientDao;
import org.example.domain.Ingredient;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class IngredientServiceTest {

    static class TestDao implements IngredientDao {
        @Override
        public List<Ingredient> getAllIngredients() { return List.of(new Ingredient()); }
        @Override
        public Optional<Ingredient> getIngredientById(int id) { return Optional.of(new Ingredient()); }
        @Override
        public boolean addIngredient(Ingredient ingredient) { return true; }
        @Override
        public boolean updateIngredient(Ingredient ingredient) { return true; }
        @Override
        public boolean deleteIngredient(int id) { return true; }

        @Override
        public List<Ingredient> filterIngredients(String name, Double maxCalories, Double minProtein) throws Exception {
            return List.of();
        }
    }

    @Test
    void testIngredientServiceSimple() throws Exception {
        TestDao dao = new TestDao();
        IngredientService service = new IngredientService(dao);

        Ingredient ingredient = new Ingredient();

        assertTrue(service.addIngredient(ingredient));

        assertTrue(service.updateIngredient(ingredient));

        assertTrue(service.deleteIngredient(1));

        assertEquals(1, service.getAllIngredients().size());

        assertTrue(service.getIngredientById(1).isPresent());

        Predicate<Ingredient> alwaysTrue = i -> true;
        Predicate<Ingredient> alwaysFalse = i -> false;

        assertEquals(1, service.findByFilter(alwaysTrue).size());
        assertEquals(0, service.findByFilter(alwaysFalse).size());
    }

    @Test
    void testConstructor() {
        IngredientService service = new IngredientService(new TestDao());
        assertNotNull(service);
    }
}

// This test was written using chatgpt