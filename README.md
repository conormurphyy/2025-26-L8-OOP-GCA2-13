# RecipeHub — GCA2 Project

Entities:
### User

| Field        | Type   | Description                          | Example  |
|--------------|--------|--------------------------------------|----------|
| id           | int    | Unique ID for each user              | 3        |
| username     | String | User’s display name                  | "alice"  |
| userType     | String | Role of user (admin or user)         | "admin"  |
| userRating   | double | Rating (0–5 scale)                   | 4.5      |

### Recipe

| Field          | Type    | Description                          | Example                        |
|----------------|---------|--------------------------------------|--------------------------------|
| recipeId       | int     | Unique ID for each recipe            | 10                             |
| userId         | int     | ID of user who created it            | 2                              |
| recipeName     | String  | Name of recipe                       | "Chicken Stir Fry"             |
| categoryId     | int     | Category ID                          | 1                              |
| description    | String  | Short description                    | "Chicken, broccoli, rice"      |
| totalCalories  | double  | Total calories                       | 650                            |
| isPublic       | boolean | Whether recipe is public             | true                           |

### Ingredient
All nutrional values are based on 100g servings
| Field         | Type   | Description                        | Example            |
|---------------|--------|------------------------------------|--------------------|
| ingredientId  | int    | Unique ID for each ingredient      | 7                  |
| name          | String | Ingredient name                    | "Chicken Breast"   |
| calories      | double | Calories per serving               | 165                |
| protein       | double | Protein per serving                | 31                 |
| carbs         | double | Carbs per serving                  | 0                  |
| fat           | double | Fat per serving                    | 3.6                |


## JSON Protocol (summary)

### Request format
```json
{ "requestType": "<TYPE>", "payload": { ... } }
```

### Response format
```json
{ "status": "SUCESS|ERROR", "message": "...", "data": ... }
```

---

## Request Types

| requestType | Purpose |
| :- | :- |
| USER_GET_ALL | Get all users |
| USER_GET_BY_ID | Get user by id |
| USER_INSERT | Insert user |
| USER_UPDATE | Update user |
| USER_DELETE | Delete user |
| USER_FILTER | Filter users |
| RECIPE_GET_ALL | Get all recipes |
| RECIPE_GET_BY_ID | Get recipe by id |
| RECIPE_INSERT | Insert recipe |
| RECIPE_UPDATE | Update recipe |
| RECIPE_DELETE | Delete recipe |
| RECIPE_FILTER | Filter recipes |
| INGREDIENT_GET_ALL | Get all ingredients |
| INGREDIENT_GET_BY_ID | Get ingredient by id |
| INGREDIENT_INSERT | Insert ingredient |
| INGREDIENT_UPDATE | Update ingredient |
| INGREDIENT_DELETE | Delete ingredient |
| INGREDIENT_FILTER | Filter ingredients |
---

