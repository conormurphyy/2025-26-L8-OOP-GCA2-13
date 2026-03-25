package org.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngredientTest {

    @Test
    void testDefaultConstructorAndSettersGetters() {
        Ingredient ing = new Ingredient();

        assertEquals(0, ing.getIngredientId());
        assertEquals("", ing.getName());
        assertEquals(0.0, ing.getCalories());
        assertEquals(0.0, ing.getProtein());
        assertEquals(0.0, ing.getCarbs());
        assertEquals(0.0, ing.getFat());

        ing.setIngredientId(1);
        ing.setName("Chicken");
        ing.setCalories(200);
        ing.setProtein(30);
        ing.setCarbs(10);
        ing.setFat(5);

        assertEquals(1, ing.getIngredientId());
        assertEquals("Chicken", ing.getName());
        assertEquals(200, ing.getCalories());
        assertEquals(30, ing.getProtein());
        assertEquals(10, ing.getCarbs());
        assertEquals(5, ing.getFat());
    }

    @Test
    void testFullConstructorAndTrimName() {
        Ingredient ing = new Ingredient(2, "  Beef  ", 250, 35, 5, 10);

        assertEquals(2, ing.getIngredientId());
        assertEquals("Beef", ing.getName());
        assertEquals(250, ing.getCalories());
        assertEquals(35, ing.getProtein());
        assertEquals(5, ing.getCarbs());
        assertEquals(10, ing.getFat());
    }

    @Test
    void testConstructorExceptions() {
        // Negative ID
        assertThrows(IllegalArgumentException.class, () -> new Ingredient(-1, "Test", 0, 0, 0, 0));

        // Null name
        assertThrows(IllegalArgumentException.class, () -> new Ingredient(1, null, 0, 0, 0, 0));

        // Blank name
        assertThrows(IllegalArgumentException.class, () -> new Ingredient(1, "   ", 0, 0, 0, 0));

        // Negative macros
        assertThrows(IllegalArgumentException.class, () -> new Ingredient(1, "Test", -1, 0, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new Ingredient(1, "Test", 0, -1, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new Ingredient(1, "Test", 0, 0, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> new Ingredient(1, "Test", 0, 0, 0, -1));
    }

    @Test
    void testEqualsAndHashCode() {
        Ingredient ing1 = new Ingredient(1, "Egg", 70, 6, 1, 5);
        Ingredient ing2 = new Ingredient(1, "Egg", 70, 6, 1, 5);
        Ingredient ing3 = new Ingredient(2, "Milk", 50, 3, 5, 2);

        // equals
        assertEquals(ing1, ing2);
        assertNotEquals(ing1, ing3);
        assertNotEquals(ing1, null);
        assertNotEquals(ing1, "String");

        // hashCode
        assertEquals(ing1.hashCode(), ing2.hashCode());
    }

    @Test
    void testToString() {
        Ingredient ing = new Ingredient(1, "Egg", 70, 6, 1, 5);
        String s = ing.toString();
        assertTrue(s.contains("1"));
        assertTrue(s.contains("Egg"));
        assertTrue(s.contains("70"));
        assertTrue(s.contains("6"));
        assertTrue(s.contains("1"));
        assertTrue(s.contains("5"));
    }
}