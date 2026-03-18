package org.example.recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe {

    private int recipeID;
    private int userID;
    private String recipeName;
    private int categoryID;
    private String description;
    private double totalCalories;
    private boolean isPublic;

    public Recipe() {
        recipeID = 0;
        userID = 0;
        recipeName = "";
        categoryID = 0;
        description = "";
        totalCalories = 0;
        isPublic = false;
    }

    public Recipe(int recipeID, boolean isPublic, int userID, String recipeName,
                  int categoryID, double totalCalories, String description) {
        if (userID<0)
            throw new IllegalArgumentException("User ID cannot be less than zero");
        if (recipeID<0)
            throw new IllegalArgumentException("Recipe ID cannot be less than zero");
        if (categoryID<0)
            throw new IllegalArgumentException("Category ID cannot be less than zero");
        if (recipeName==null)
            throw new IllegalArgumentException("Recipe Name cannot be null");
        if (description==null)
            throw new IllegalArgumentException("Recipe Description cannot be null");
        if (totalCalories<0)
            throw new IllegalArgumentException("Total Calories cannot be less than zero");

        this.recipeID = recipeID;
        this.isPublic = isPublic;
        this.userID = userID;
        this.recipeName = recipeName;
        this.categoryID = categoryID;
        this.totalCalories = totalCalories;
        this.description = description;
    }

    // PUBLIC API
    @JsonProperty("id")
    public int getRecipeID() {
        return recipeID;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public double getTotalCalories() {
        return totalCalories;
    }

    public String getDescription() {
        return description;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public int getUserID() {
        return userID;
    }

    @JsonProperty("id")
    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public void setTotalCalories(double totalCalories) {
        this.totalCalories = totalCalories;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(recipeID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;
        Recipe other = (Recipe) o;
        return recipeID == other.recipeID;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeID=" + recipeID +
                ", userID=" + userID +
                ", recipeName='" + recipeName + '\'' +
                ", categoryID=" + categoryID +
                ", description='" + description + '\'' +
                ", totalCalories=" + totalCalories +
                ", isPublic=" + isPublic +
                '}';
    }
}


