package org.example.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.IngredientDao;
import org.example.dao.RecipeDao;
import org.example.dao.UserDao;
import org.example.shared.ClientRequest;
import org.example.shared.ServerResponse;
import org.example.util.JsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

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

    public ClientHandler(Socket socket, UserDao userDao, RecipeDao recipeDao, IngredientDao ingredientDao, ObjectMapper mapper, Map<String, RequestHandler> handlers)
    {
        if(socket == null)
        {
            throw new IllegalArgumentException("socket must not be null");
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

        _socket=socket;
        _userDao=userDao;
        _recipeDao=recipeDao;
        _ingredientDao=ingredientDao;
        _mapper= JsonUtil.getMapper();
        _handlers=new HashMap<>();
        _running=true;
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

}
