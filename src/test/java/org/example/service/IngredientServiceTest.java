package org.example.service;

import org.example.dao.IngredientDao;
import org.example.domain.Ingredient;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class IngredientServiceTest {

    // Stub DAO that just returns dummy values
    static class StubDao implements IngredientDao {
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
    }

    @Test
    void testIngredientServiceSimple() throws Exception {
        StubDao dao = new StubDao();
        IngredientService service = new IngredientService(dao);

        Ingredient ingredient = new Ingredient();

        // addIngredient
        assertTrue(service.addIngredient(ingredient));

        // updateIngredient
        assertTrue(service.updateIngredient(ingredient));

        // deleteIngredient
        assertTrue(service.deleteIngredient(1));

        // getAllIngredients
        assertEquals(1, service.getAllIngredients().size());

        // getIngredientById
        assertTrue(service.getIngredientById(1).isPresent());

        // findByFilter
        Predicate<Ingredient> alwaysTrue = i -> true;
        Predicate<Ingredient> alwaysFalse = i -> false;

        assertEquals(1, service.findByFilter(alwaysTrue).size());
        assertEquals(0, service.findByFilter(alwaysFalse).size());
    }

    @Test
    void testConstructor() {
        IngredientService service = new IngredientService(new StubDao());
        assertNotNull(service);
    }
}