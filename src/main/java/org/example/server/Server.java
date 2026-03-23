package org.example.server;

import com.fasterxml.jackson.annotation.JsonTypeId;
import org.example.dao.IngredientDao;
import org.example.dao.RecipeDao;
import org.example.dao.UserDao;
import org.example.dao.jdbc.JDBCIngredientDao;
import org.example.dao.jdbc.JDBCRecipeDao;
import org.example.dao.jdbc.JdbcUserDao;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    private final int _port;
    private final UserDao _userDao;
    private final RecipeDao _recipeDao;
    private final IngredientDao _ingredientDao;
    private final ExecutorService _pool;
    public Server(int port, UserDao userDao, RecipeDao recipeDao, IngredientDao ingredientDao)

    {

        if(port <1_024 || port > 65_535)
        {
            throw new IllegalArgumentException("Port must be between 1024 and 65535");
        }
        if(userDao == null)
        {
            throw new IllegalArgumentException("userDao must not be null");
        }
        if(recipeDao == null)
        {
            throw new IllegalArgumentException("recipeDao must not be null");
        }
        if(ingredientDao == null)
        {
            throw new IllegalArgumentException("ingredientDao must not be null");
        }

        _port = port;
        _userDao = userDao;
        _recipeDao = recipeDao;
        _ingredientDao = ingredientDao;
        _pool = Executors.newCachedThreadPool();
    }

    public void start() throws IOException {
        System.out.println("Starting on port: " + _port);

        try(ServerSocket serverSocket = new ServerSocket(_port))
        {
            System.out.println("RecipeHub server started");

            while(!Thread.currentThread().isInterrupted())
            {
                Socket client = serverSocket.accept();
                System.out.println("Client connected from : "+ client.getInetAddress().getHostAddress());
                _pool.submit(new ClientHandler(client, _userDao, _recipeDao, _ingredientDao));
            }
        }
        finally{
        stop();
        }
    }
    private void stop()
    {
        System.out.println("Stopping RecipeHub server");
        _pool.shutdown();
        try{
            if(!_pool.awaitTermination(5, TimeUnit.SECONDS))
            {
                System.out.println("Server did not stop gracefully");gi
                _pool.shutdownNow();
            }
        }
        catch (InterruptedException e)
        {
            _pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("RecipeHub server stopped");
    }

    public static void main(String[] args) throws IOException, SQLException {
        String url = "jdbc:mysql://localhost:3306/recipehub";
        String user = "admin";
        String pass = "";

        UserDao userDao = new JdbcUserDao(url, user, pass);
        RecipeDao recipeDao = new JDBCRecipeDao(url, user, pass);
        IngredientDao ingredientDao = new JDBCIngredientDao(url, user, pass);

        new Server(8080, userDao, recipeDao, ingredientDao).start();
    }
}
