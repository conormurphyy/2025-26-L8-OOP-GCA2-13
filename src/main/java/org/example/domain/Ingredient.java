package org.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ingredient {

    //Fields
    private int fIngredientId;
    private String fName;
    private double fCalories;
    private double fProtein;
    private double fCarbs;
    private double fFat;

    //Constructors
    public Ingredient() {
        fIngredientId = 0;
        fName = "";
        fCalories = 0.0;
        fProtein = 0.0;
        fCarbs = 0.0;
        fFat = 0.0;
    }

    public Ingredient(int ingredientId, String name, double calories, double protein, double carbs, double fat) {
        if (ingredientId < 0) {
            throw new IllegalArgumentException("ingredientId must be >= 0");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
        if (calories < 0.0 || protein < 0.0 || carbs < 0.0 || fat < 0.0) {
            throw new IllegalArgumentException("macros must be >= 0");
        }

        fIngredientId = ingredientId;
        fName = name.trim();
        fCalories = calories;
        fProtein = protein;
        fCarbs = carbs;
        fFat = fat;
    }

    //Public API
    public int getIngredientId() {
        return fIngredientId;
    }

    public void setIngredientId(int ingredientId) {
        fIngredientId = ingredientId;
    }

    public String getName() {
        return fName;
    }

    public void setName(String name) {
        fName = name;
    }

    public double getCalories() {
        return fCalories;
    }

    public void setCalories(double calories) {
        fCalories = calories;
    }

    public double getProtein() {
        return fProtein;
    }

    public void setProtein(double protein) {
        fProtein = protein;
    }

    public double getCarbs() {
        return fCarbs;
    }

    public void setCarbs(double carbs) {
        fCarbs = carbs;
    }

    public double getFat() {
        return fFat;
    }

    public void setFat(double fat) {
        fFat = fat;
    }

    //Helpers

    @Override
    public int hashCode() {
        return Objects.hash(fIngredientId, fName, fCalories, fProtein, fCarbs, fFat);
    }

    @Override
    public String toString() {
        return "Ingredient{id=" + fIngredientId
                + ", name='" + fName + '\''
                + ", calories=" + fCalories
                + ", protein=" + fProtein
                + ", carbs=" + fCarbs
                + ", fat=" + fFat + "}";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ingredient)) {
            return false;
        }
        Ingredient other = (Ingredient) o;
        return fIngredientId == other.fIngredientId
                && Double.compare(fCalories, other.fCalories) == 0
                && Double.compare(fProtein, other.fProtein) == 0
                && Double.compare(fCarbs, other.fCarbs) == 0
                && Double.compare(fFat, other.fFat) == 0
                && Objects.equals(fName, other.fName);
    }
}