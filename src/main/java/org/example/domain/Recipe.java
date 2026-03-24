package org.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe {

    //Fields
    private int fRecipeId;
    private int fUserId;
    private String fRecipeName;
    private int fCategoryId;
    private String fDescription;
    private double fTotalCalories;
    private boolean fIsPublic;

    //Constructors
    // empty recipe
    public Recipe() {
        fRecipeId = 0;
        fUserId = 0;
        fRecipeName = "";
        fCategoryId = 0;
        fDescription = "";
        fTotalCalories = 0.0;
        fIsPublic = false;
    }

    //Constructor
    public Recipe(int recipeId, int userId, String recipeName, int categoryId,
                  String description, double totalCalories, boolean isPublic) {
        if (recipeId < 0) {
            throw new IllegalArgumentException("recipeId must be >= 0");
        }
        if (userId < 0) {
            throw new IllegalArgumentException("userId must be >= 0");
        }
        if (recipeName == null || recipeName.isBlank()) {
            throw new IllegalArgumentException("recipeName is required");
        }
        if (categoryId < 0) {
            throw new IllegalArgumentException("categoryId must be >= 0");
        }
        if (description == null) {
            throw new IllegalArgumentException("description is required");
        }
        if (totalCalories < 0.0) {
            throw new IllegalArgumentException("totalCalories must be >= 0");
        }

        fRecipeId = recipeId;
        fUserId = userId;
        fRecipeName = recipeName;
        fCategoryId = categoryId;
        fDescription = description;
        fTotalCalories = totalCalories;
        fIsPublic = isPublic;
    }

    //Public API

    public int getRecipeId() {
        return fRecipeId;
    }

    public void setRecipeId(int recipeId) {
        fRecipeId = recipeId;
    }

    public int getUserId() {
        return fUserId;
    }

    public void setUserId(int userId) {
        fUserId = userId;
    }

    public String getRecipeName() {
        return fRecipeName;
    }

    public void setRecipeName(String recipeName) {
        fRecipeName = recipeName;
    }

    public int getCategoryId() {
        return fCategoryId;
    }

    public void setCategoryId(int categoryId) {
        fCategoryId = categoryId;
    }

    public String getDescription() {
        return fDescription;
    }

    public void setDescription(String description) {
        fDescription = description;
    }

    public double getTotalCalories() {
        return fTotalCalories;
    }

    public void setTotalCalories(double totalCalories) {
        fTotalCalories = totalCalories;
    }

    public boolean getIsPublic() {
        return fIsPublic;
    }

    public void setIsPublic(boolean isPublic) {
        fIsPublic = isPublic;
    }

    //Helper

    @Override
    public int hashCode() {
        return Objects.hash(fRecipeId, fUserId, fRecipeName, fCategoryId, fDescription, fTotalCalories, fIsPublic);
    }

    @Override
    public String toString() {
        return "Recipe{recipeId=" + fRecipeId
                + ", userId=" + fUserId
                + ", recipeName='" + fRecipeName + '\''
                + ", categoryId=" + fCategoryId
                + ", description='" + fDescription + '\''
                + ", totalCalories=" + fTotalCalories
                + ", isPublic=" + fIsPublic + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recipe)) {
            return false;
        }
        Recipe other = (Recipe) o;
        return fRecipeId == other.fRecipeId
                && fUserId == other.fUserId
                && fCategoryId == other.fCategoryId
                && Double.compare(fTotalCalories, other.fTotalCalories) == 0
                && fIsPublic == other.fIsPublic
                && Objects.equals(fRecipeName, other.fRecipeName)
                && Objects.equals(fDescription, other.fDescription);
    }
}