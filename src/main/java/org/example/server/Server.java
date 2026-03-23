package org.example.server;

import com.fasterxml.jackson.annotation.JsonTypeId;
import org.example.dao.IngredientDao;
import org.example.dao.RecipeDao;
import org.example.dao.UserDao;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
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

        }
    }
    private void stop()
    {

    }
}
