package org.example.ingredient;

import java.util.List;

public class IngredientMain {
    public static void main(String[] args) throws Exception {

        String url = "jdbc:mysql://localhost:3306/ingredient";
        String user = "root";
        String pass = "";

        IngredientDao dao = new JDBCIngredientDao(url, user, pass);
        IngredientService service = new IngredientService(dao);

        Ingredient rice = new Ingredient(0, "Rice", 130, 2.7, 28, 0.3);
        boolean created = service.addIngredient(rice);
        System.out.println("Ingredient created: " + created);

        System.out.println("All Ingredients:");
        List<Ingredient> ingredients = service.getAllIngredients();
        for (Ingredient i : ingredients) {
            System.out.println(" - " + i);
        }

        System.out.println("Ingredients with calories > 100:");
        List<Ingredient> highCalories = service.findByFilter(ing -> ing.getCalories() > 100);
        for (Ingredient i : highCalories) {
            System.out.println(" - " + i);
        }
    }
}