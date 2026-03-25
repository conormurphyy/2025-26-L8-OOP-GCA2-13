package org.example.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.client.Client;
import org.example.dao.IngredientDao;
import org.example.dao.RecipeDao;
import org.example.dao.UserDao;
import org.example.domain.Ingredient;
import org.example.domain.Recipe;
import org.example.domain.User;
import org.example.shared.ClientRequest;
import org.example.shared.ServerResponse;
import org.example.util.JsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static sun.misc.Signal.handle;

public class ClientHandler implements Runnable{

    @FunctionalInterface
    private interface RequestHandler {
        ServerResponse<?> handle(ClientRequest request) throws Exception;
    }
    private final Socket _socket;
    private final UserDao _userDao;
    private final RecipeDao _recipeDao;
    private final IngredientDao _ingredientDao;
    private final ObjectMapper _mapper;
    private final Map<String, RequestHandler> _handlers;
    private boolean _running;

    public ClientHandler(Socket socket, UserDao userDao, RecipeDao recipeDao, IngredientDao ingredientDao, ObjectMapper mapper, Map<String, RequestHandler> handlers) {
        if (socket == null) {
            throw new IllegalArgumentException("socket must not be null");
        }
        if (userDao == null) {
            throw new IllegalArgumentException("userDao must not be null");
        }
        if (recipeDao == null) {
            throw new IllegalArgumentException("recipeDao must not be null");
        }
        if (ingredientDao == null) {
            throw new IllegalArgumentException("ingredientDao must not be null");
        }

        _socket = socket;
        _userDao = userDao;
        _recipeDao = recipeDao;
        _ingredientDao = ingredientDao;
        _mapper = JsonUtil.getMapper();
        _handlers = new HashMap<>();
        _running = true;
        registerHandlers();
    }




    @Override
    public void run(){
String threadName = Thread.currentThread().getName();
        System.out.println("ClientHandler thread started: " + threadName + " for client: " + _socket.getInetAddress().getHostAddress());

        try(BufferedReader in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
            PrintWriter out = new PrintWriter(_socket.getOutputStream(), true))
        {
            String line;
            while(_running && (line = in.readLine())!=null)
            {
                String response = handle(line);
                out.println(response);
            }
        }
        catch (IOException e)
        {
            System.out.println("Client disconnected: " + e.getMessage());
        }
        finally {
            try{
                _socket.close();
            } catch(IOException ignored){

            }
            System.out.println(threadName + " thread stopped");
        }
    }
    private String handle(String rawJson)
    {
        try{
            ClientRequest request = _mapper.readValue(rawJson, ClientRequest.class);
            ServerResponse<?> response = dispatch(request);
            return _mapper.writeValueAsString(response);
        }
        catch(Exception e)
        {
            return toErrorJson("BAD Request" + e.getMessage());
        }
    }
    private ServerResponse <?> dispatch (ClientRequest request)
    {
        RequestHandler handler = _handlers.get(request.getType());
        if(handler == null) {
            return ServerResponse.error("BAD Request" + "No handler for request type: " + request.getType());
        }

            try{
                return handler.handle(request);
            }
            catch (IllegalArgumentException e)
            {
                return ServerResponse.error(e.getMessage());
            }
            catch (Exception e)
            {
                System.out.println("Error handling request: " + e.getMessage());
                return ServerResponse.error("Internal Server Error");
            }


    }

    private void registerHandlers()
    {
        //USERS
        _handlers.put("GET_ALL_USERS", this::handleGetAllUsers);
        _handlers.put("GET_USER_BY_ID", this::handleGetUserById);
        _handlers.put("CREATE_USER", this::handleCreateUser);
        _handlers.put("UPDATE_USER", this::handleUpdateUser);
        _handlers.put("DELETE_USER", this::handleDeleteUser);
        _handlers.put("FILTER_USERS", this::handleFilterUsers);




        //RECIPES
        _handlers.put("GET_ALL_RECIPES", this::handleGetAllRecipes);
        _handlers.put("GET_RECIPE_BY_ID", this::handleGetRecipeById);
        _handlers.put("CREATE_RECIPE", this::handleCreateRecipe);
        _handlers.put("UPDATE_RECIPE", this::handleUpdateRecipe);
        _handlers.put("DELETE_RECIPE", this::handleDeleteRecipe);

        //INGREDIENTS
        _handlers.put("GET_ALL_INGREDIENTS", this::handleGetAllIngredients);
        _handlers.put("GET_RECIPE_BY_INGREDIENT", this::handleGetIngredientById);
        _handlers.put("CREATE_INGREDIENT", this::handleCreateIngredient);
        _handlers.put("UPDATE_INGREDIENT", this::handleUpdateIngredient);
        _handlers.put("DELETE_INGREDIENT", this::handleDeleteIngredient);
        //Session
        _handlers.put("DISCONNECT", this::handleDisconnect);
    }
    //USERS
    private ServerResponse<?> handleGetAllUsers(ClientRequest request) throws Exception
    {
        List<User> users = _userDao.findAll();
        return ServerResponse.success("retrieved" + users.size() + " users", users);
    }
    private ServerResponse <?> handleGetUserById (ClientRequest request) throws Exception
    {
        int id = request.getInt("id");
        if(id <=0)
        {
            return ServerResponse.error("Invalid user id");
        }
        Optional<User> found = _userDao.findById(id);
        if(found.isEmpty())
        {
            return ServerResponse.error("User not found");
        }
        return ServerResponse.success("User found", found.get());
    }
    private ServerResponse <?> handleCreateUser (ClientRequest request) throws Exception
    {
        int id = request.getInt("id");
        if(id <1 || id > 1000000000)
        {
            return ServerResponse.error("Invalid id");
        }

        String username = request.getString("username");
        if(username == null || username.isEmpty())
        {
            return ServerResponse.error("Invalid user name");
        }
        String userType = request.getString("userType");
        if(userType == null|| userType.isEmpty())
        {
            return ServerResponse.error("Invalid user type");
        }
        double userRating = request.getDouble("userRating");
        if(userRating <0 || userRating > 5)
        {
            return ServerResponse.error("Invalid user rating");
        }

        User insertedUser = _userDao.insert(new User(id, username, userType, userRating));
        return ServerResponse.success("User created", insertedUser);
    }

    private ServerResponse<?> handleFilterUsers(ClientRequest request) throws Exception {
        String userType = request.getString("userType");
        Double minRating = request.getDouble("minRating");

        List<User> filtered = _userDao.filterUsers(userType, minRating);

        return ServerResponse.success("Found " + filtered.size() + " users", filtered);
    }

    private ServerResponse<?> handleUpdateUser (ClientRequest request) throws Exception {
        int id= request.getInt("id");
        if(id <=0 || id > 1000000000)
        {
            return ServerResponse.error("Invalid id");
        }
        String username= request.getString("username");
        if(username == null || username.isEmpty())
        {
            return ServerResponse.error("Invalid user name");
        }
        String userType= request.getString("userType");
        if(userType == null|| userType.isEmpty())
        {
            return ServerResponse.error("Invalid user type");
        }
        double userRating= request.getDouble("userRating");
        if(userRating <0 || userRating > 5)
        {
            return ServerResponse.error("Invalid user rating");
        }
        User updatedUser = _userDao.updateAll(new User(id, username, userType, userRating));
        return ServerResponse.success("User updated", updatedUser);

    }
    private ServerResponse <?> handleDeleteUser(ClientRequest request) throws Exception {
        int id= request.getInt("id");
        if(id <=0 || id > 1000000000)
        {
            return ServerResponse.error("Invalid id");
        }
        boolean deletedUser = _userDao.deleteById(id);
        return ServerResponse.success("User deleted", deletedUser);

    }

    //RECIPE
    private ServerResponse<?> handleGetAllRecipes(ClientRequest request) throws Exception {
        List<Recipe> recipes = _recipeDao.getAllRecipes();
        return ServerResponse.success("Retrieved " + recipes.size() + " recipes", recipes);
    }

    private ServerResponse<?> handleGetRecipeById(ClientRequest request) throws Exception {
        int id = request.getInt("id");
        if (id <= 0) {
            return ServerResponse.error("Invalid recipe id");
        }
        Optional<Recipe> found = _recipeDao.getRecipeById(id);
        if (found.isEmpty()) {
            return ServerResponse.error("Recipe not found");
        }
        return ServerResponse.success("Recipe found", found.get());
    }

    private ServerResponse<?> handleCreateRecipe(ClientRequest request) throws Exception {
        int recipeId = request.getInt("id");
        if (recipeId < 1 || recipeId > 1_000_000_000) {
            return ServerResponse.error("Invalid recipe id");
        }

        int userId = request.getInt("userId");
        String recipeName = request.getString("recipeName");
        int categoryId = request.getInt("categoryId");
        String description = request.getString("description");
        double totalCalories = request.getDouble("totalCalories");
        boolean isPublic = request.getBoolean("isPublic");

        Recipe newRecipe = new Recipe(recipeId, userId, recipeName, categoryId, description, totalCalories, isPublic);
        boolean added = _recipeDao.addRecipe(newRecipe);

        if (!added) {
            return ServerResponse.error("Failed to create recipe");
        }

        return ServerResponse.success("Recipe created", newRecipe);
    }

    private ServerResponse<?> handleUpdateRecipe(ClientRequest request) throws Exception {
        int recipeId = request.getInt("id");
        int userId = request.getInt("userId");
        String recipeName = request.getString("recipeName");
        int categoryId = request.getInt("categoryId");
        String description = request.getString("description");
        double totalCalories = request.getDouble("totalCalories");
        boolean isPublic = request.getBoolean("isPublic");

        Recipe updatedRecipe = new Recipe(recipeId, userId, recipeName, categoryId, description, totalCalories, isPublic);
        boolean updated = _recipeDao.updateRecipe(updatedRecipe);

        if (!updated) {
            return ServerResponse.error("Failed to update recipe");
        }

        return ServerResponse.success("Recipe updated", updatedRecipe);
    }

    private ServerResponse<?> handleDeleteRecipe(ClientRequest request) throws Exception {
        int recipeId = request.getInt("id");
        if (recipeId <= 0 || recipeId > 1_000_000_000) {
            return ServerResponse.error("Invalid recipe id");
        }

        boolean deleted = _recipeDao.deleteRecipe(recipeId);

        if (!deleted) {
            return ServerResponse.error("Failed to delete recipe");
        }

        return ServerResponse.success("Recipe deleted", deleted);
    }

    //Ingredient
    public ServerResponse<?> handleGetAllIngredients(ClientRequest request) throws Exception {
        List<Ingredient> ingredients = _ingredientDao.getAllIngredients();
        return ServerResponse.success("Retrieved " + ingredients.size() + " ingredients", ingredients);
    }

    public ServerResponse<?> handleGetIngredientById(ClientRequest request) throws Exception {
        int id = request.getInt("id");
        if (id <= 0) {
            return ServerResponse.error("Invalid ingredient id");
        }
        Optional<Ingredient> found = _ingredientDao.getIngredientById(id);
        if (found.isEmpty()) {
            return ServerResponse.error("Ingredient not found");
        }
        return ServerResponse.success("Ingredient found", found.get());
    }

    public ServerResponse<?> handleCreateIngredient(ClientRequest request) throws Exception {
        int ingredientId = request.getInt("id");
        if (ingredientId < 1 || ingredientId > 1_000_000_000) {
            return ServerResponse.error("Invalid ingredient id");
        }

        String name = request.getString("name");
        double calories = request.getDouble("calories");
        double protein = request.getDouble("protein");
        double carbs = request.getDouble("carbs");
        double fat = request.getDouble("fat");

        Ingredient newIngredient = new Ingredient(ingredientId, name, calories, protein, carbs, fat);
        boolean added = _ingredientDao.addIngredient(newIngredient);

        if (!added) {
            return ServerResponse.error("Failed to create ingredient");
        }

        return ServerResponse.success("Ingredient created", newIngredient);
    }

    public ServerResponse<?> handleUpdateIngredient(ClientRequest request) throws Exception {
        int ingredientId = request.getInt("id");
        String name = request.getString("name");
        double calories = request.getDouble("calories");
        double protein = request.getDouble("protein");
        double carbs = request.getDouble("carbs");
        double fat = request.getDouble("fat");

        Ingredient updatedIngredient = new Ingredient(ingredientId, name, calories, protein, carbs, fat);
        boolean updated = _ingredientDao.updateIngredient(updatedIngredient);

        if (!updated) {
            return ServerResponse.error("Failed to update ingredient");
        }

        return ServerResponse.success("Ingredient updated", updatedIngredient);
    }

    public ServerResponse<?> handleDeleteIngredient(ClientRequest request) throws Exception {
        int ingredientId = request.getInt("id");
        if (ingredientId <= 0 || ingredientId > 1_000_000_000) {
            return ServerResponse.error("Invalid ingredient id");
        }

        boolean deleted = _ingredientDao.deleteIngredient(ingredientId);

        if (!deleted) {
            return ServerResponse.error("Failed to delete ingredient");
        }

        return ServerResponse.success("Ingredient deleted", deleted);
    }


    //DISCONNECT
    private ServerResponse<?> handleDisconnect(ClientRequest request)
    {
        _running = false;
        System.out.println("Client disconnected");
        return ServerResponse.success("Client disconnected",null);
    }

    private String toErrorJson(String message) {
        return "{\"status\":\"ERROR\",\"message\":\"" + message + "\",\"data\":null}";
    }

}
