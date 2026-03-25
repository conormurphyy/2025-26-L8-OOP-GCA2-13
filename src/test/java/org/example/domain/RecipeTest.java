package org.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    @Test
    void testDefaultConstructorAndSettersGetters() {
        Recipe r = new Recipe();

        // Default values
        assertEquals(0, r.getRecipeId());
        assertEquals(0, r.getUserId());
        assertEquals("", r.getRecipeName());
        assertEquals(0, r.getCategoryId());
        assertEquals("", r.getDescription());
        assertEquals(0.0, r.getTotalCalories());
        assertFalse(r.getIsPublic());

        // Set values
        r.setRecipeId(1);
        r.setUserId(2);
        r.setRecipeName("Cake");
        r.setCategoryId(3);
        r.setDescription("Delicious");
        r.setTotalCalories(500);
        r.setIsPublic(true);

        assertEquals(1, r.getRecipeId());
        assertEquals(2, r.getUserId());
        assertEquals("Cake", r.getRecipeName());
        assertEquals(3, r.getCategoryId());
        assertEquals("Delicious", r.getDescription());
        assertEquals(500, r.getTotalCalories());
        assertTrue(r.getIsPublic());
    }

    @Test
    void testFullConstructor() {
        Recipe r = new Recipe(1, 2, "Cake", 3, "Tasty", 450, true);

        assertEquals(1, r.getRecipeId());
        assertEquals(2, r.getUserId());
        assertEquals("Cake", r.getRecipeName());
        assertEquals(3, r.getCategoryId());
        assertEquals("Tasty", r.getDescription());
        assertEquals(450, r.getTotalCalories());
        assertTrue(r.getIsPublic());
    }

    @Test
    void testConstructorExceptions() {
        // Negative recipeId
        assertThrows(IllegalArgumentException.class, () ->
                new Recipe(-1, 0, "Cake", 0, "desc", 0, false));

        // Negative userId
        assertThrows(IllegalArgumentException.class, () ->
                new Recipe(0, -1, "Cake", 0, "desc", 0, false));

        // Blank recipeName
        assertThrows(IllegalArgumentException.class, () ->
                new Recipe(0, 0, " ", 0, "desc", 0, false));

        // Null recipeName
        assertThrows(IllegalArgumentException.class, () ->
                new Recipe(0, 0, null, 0, "desc", 0, false));

        // Negative categoryId
        assertThrows(IllegalArgumentException.class, () ->
                new Recipe(0, 0, "Cake", -1, "desc", 0, false));

        // Null description
        assertThrows(IllegalArgumentException.class, () ->
                new Recipe(0, 0, "Cake", 0, null, 0, false));

        // Negative calories
        assertThrows(IllegalArgumentException.class, () ->
                new Recipe(0, 0, "Cake", 0, "desc", -1, false));
    }

    @Test
    void testEqualsAndHashCode() {
        Recipe r1 = new Recipe(1, 2, "Cake", 3, "Delicious", 400, true);
        Recipe r2 = new Recipe(1, 2, "Cake", 3, "Delicious", 400, true);
        Recipe r3 = new Recipe(2, 3, "Pie", 4, "Tasty", 300, false);

        assertEquals(r1, r2);
        assertNotEquals(r1, r3);
        assertNotEquals(r1, null);
        assertNotEquals(r1, "String");

        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testToString() {
        Recipe r = new Recipe(1, 2, "Cake", 3, "Delicious", 400, true);
        String s = r.toString();
        assertTrue(s.contains("1"));
        assertTrue(s.contains("2"));
        assertTrue(s.contains("Cake"));
        assertTrue(s.contains("3"));
        assertTrue(s.contains("Delicious"));
        assertTrue(s.contains("400"));
        assertTrue(s.contains("true"));
    }
}