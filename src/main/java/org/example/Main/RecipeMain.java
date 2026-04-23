package org.example.Main;

import org.example.dao.RecipeDao;
import org.example.dao.jdbc.JDBCRecipeDao;
import org.example.domain.Recipe;
import org.example.service.RecipeService;

import java.nio.file.Files;
import java.nio.file.Path;

public class RecipeMain {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/recipehub";
        String user = "root";
        String pass = "";

        RecipeDao dao = new JDBCRecipeDao(url, user, pass);
        RecipeService service = new RecipeService(dao);


        Recipe newRecipe = new Recipe(
                123163,
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

        Path path = Path.of("test.png");

        byte[] bytes = Files.readAllBytes(path);

        Recipe recipe = new Recipe(
                9999, 2, "Test Recipe", 1,
                "desc", 500, true,
                bytes,
                "test.png",
                "image/png",
                bytes.length
        );

        dao.addRecipe(recipe);
    }
}
