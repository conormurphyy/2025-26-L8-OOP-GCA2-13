<img src="RecipeHub.png">



# RecipeHub — GCA2 Project 📖

Entities:
---
### User 👨‍🦲

The "user" entity represents each user that is on our application. A user can either be a user with limited permissions or an admin with all permissions.

| Field        | Type   | Description                          | Example  |
|--------------|--------|--------------------------------------|----------|
| id           | int    | Unique ID for each user              | 3        |
| username     | String | User’s display name                  | "alice"  |
| userType     | String | Role of user (admin or user)         | "admin"  |
| userRating   | double | Rating (0–5 scale)                   | 4.5      |

---

### Recipe 📘

The "recipe" entity represents each recipe that is in our database, the recipe shows details about the recipe and the user responsible for creating it, it also declares wheither the recipe is public or private. Public meaning other users can see it, private meaning that only the user who made it can see it.

| Field          | Type    | Description                          | Example                        |
|----------------|---------|--------------------------------------|--------------------------------|
| recipeId       | int     | Unique ID for each recipe            | 10                             |
| userId         | int     | ID of user who created it            | 2                              |
| recipeName     | String  | Name of recipe                       | "Chicken Stir Fry"             |
| categoryId     | int     | Category ID                          | 1                              |
| description    | String  | Short description                    | "Chicken, broccoli, rice"      |
| totalCalories  | double  | Total calories                       | 650                            |
| isPublic       | boolean | Whether recipe is public             | true                           |

---

### Ingredient 🍚
The "ingredient" entity represents each ingredient in our database. This entity shows relevant information for each ingredient that goes into a recipe.
All nutrional values are based on 100g servings
| Field         | Type   | Description                        | Example            |
|---------------|--------|------------------------------------|--------------------|
| ingredientId  | int    | Unique ID for each ingredient      | 7                  |
| name          | String | Ingredient name                    | "Chicken Breast"   |
| calories      | double | Calories per serving               | 165                |
| protein       | double | Protein per serving                | 31                 |
| carbs         | double | Carbs per serving                  | 0                  |
| fat           | double | Fat per serving                    | 3.6                |

---

## 🌳 Structure Tree of Application (to date)
```
|   App.java
|
+---client
|       Client.java
|
+---dao
|   |   IngredientDao.java
|   |   RecipeDao.java
|   |   UserDao.java
|   |
|   \---jdbc
|           JDBCIngredientDao.java
|           JDBCRecipeDao.java
|           JdbcUserDao.java
|
+---domain
|       Ingredient.java
|       Recipe.java
|       User.java
|
+---Main
|       IngredientMain.java
|       RecipeMain.java
|       UserMain.java
|
+---recipe
|       DbSmokeTest.java
|
+---server
|       ClientHandler.java
|       Server.java
|
+---service
|       IngredientService.java
|       RecipeService.java
|       UserService.java
|
+---shared
|       ClientRequest.java
|       RequestType.java
|       ServerResponse.java
|
+---user
|       UserDbSmokeTest.java
|
\---util
        JsonUtil.java
```

## JSON Protocol (summary) 

### Request format ⬅️
```json
{ "requestType": "<TYPE>", "payload": { ... } }
```

### Response format ➡️
```json
{ "status": "SUCESS|ERROR", "message": "...", "data": ... }
```

---

## Request Types :clipboard:

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

