package org.example.service;

import org.example.domain.Ingredient;
import org.example.dao.IngredientDao;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class IngredientService {

    private IngredientDao ingredientDao;

    public IngredientService(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    public List<Ingredient> getAllIngredients() throws Exception {
        return ingredientDao.getAllIngredients();
    }

    public Optional<Ingredient> getIngredientById(int id) throws Exception {
        return ingredientDao.getIngredientById(id);
    }

    public boolean addIngredient(Ingredient ingredient) throws Exception {
        return ingredientDao.addIngredient(ingredient);
    }

    public boolean updateIngredient(Ingredient ingredient) throws Exception {
        return ingredientDao.updateIngredient(ingredient);
    }

    public boolean deleteIngredient(int id) throws Exception {
        return ingredientDao.deleteIngredient(id);
    }

    public List<Ingredient> findByFilter(Predicate<Ingredient> filter) throws Exception {

        return ingredientDao.getAllIngredients()
                .stream()
                .filter(filter)
                .collect(Collectors.toList());
    }
}
