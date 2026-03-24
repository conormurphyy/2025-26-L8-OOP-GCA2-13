package org.example.recipe;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.Recipe;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RecipeJsonTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSingleRecipeSerialization() throws Exception {
        Recipe recipe = new Recipe(1, true, 5, "Protein Pancakes", 2, 350, "High protein breakfast");

        String json = mapper.writeValueAsString(recipe);
        assertNotNull(json);

        Recipe deserialized = mapper.readValue(json, Recipe.class);
        assertEquals(recipe, deserialized);
    }

    @Test
    public void testRecipeListSerialization() throws Exception {
        List<Recipe> recipes = List.of(
                new Recipe(1, 1, 5, "Protein Pancakes", 2, 350, "High protein breakfast"),
                new Recipe(2, 1, 3, "Omelette", 2, 250, "Low-carb breakfast")
        );

        String jsonList = mapper.writeValueAsString(recipes);
        assertNotNull(jsonList);

        List<Recipe> deserializedList = mapper.readValue(jsonList, new TypeReference<List<Recipe>>() {});
        assertEquals(recipes, deserializedList);
    }
}
