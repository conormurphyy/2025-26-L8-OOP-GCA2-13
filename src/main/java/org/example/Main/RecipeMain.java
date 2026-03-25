package org.example.Main;

import org.example.dao.RecipeDao;
import org.example.dao.jdbc.JDBCRecipeDao;
import org.example.domain.Recipe;
import org.example.service.RecipeService;

public class RecipeMain {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/recipehub?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String user = "admin";
        String pass = "root";

        RecipeDao dao = new JDBCRecipeDao(url, user, pass);
        RecipeService service = new RecipeService(dao);


        Recipe newRecipe = new Recipe(
                11,
                2,
                "Protein Pancakes",
                4,
                "High protein pancakes with oats",
                550,
                true
        );

        boolean created = service.createRecipe(newRecipe);
        System.out.println("Recipe created: " + created);

        System.out.println("\nAll Recipes:");
        for (Recipe r : service.listAllRecipes()) {
            System.out.println(" - " + r);
        }

        System.out.println("\nSearch by name 'Chicken':");
        for (Recipe r : service.searchByName("Chicken")) {
            System.out.println(" - " + r);
        }

        System.out.println("\nPublic Recipes:");
        for (Recipe r : service.getPublicRecipes()) {
            System.out.println(" - " + r);
        }
    }
}
