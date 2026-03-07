package org.example.ingredient;

public class Ingredient {

    private int ingredientId;
    private String name;
    private double calories;
    private double protein;
    private double carbs;
    private double fat;

    public Ingredient(int ingredientId, String name, double calories) {

        if (ingredientId < 0)
            throw new IllegalArgumentException("Ingredient ID cannot be less than zero");

        if (name == null)
            throw new IllegalArgumentException("Ingredient name cannot be null");

        this.ingredientId = ingredientId;
        this.name = name;
        this.calories = calories;
    }

    public Ingredient(int ingredientId, String name, double calories, double protein, double carbs, double fat) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;


    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }


    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredientId=" + ingredientId +
                ", name='" + name + '\'' +
                ", calories=" + calories +
                ", protein=" + protein +
                ", carbs=" + carbs +
                ", fat=" + fat +
                '}';
    }
}
