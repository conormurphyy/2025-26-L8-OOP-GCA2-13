package org.example.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.client.Client;
import org.example.dao.IngredientDao;
import org.example.dao.RecipeDao;
import org.example.dao.UserDao;
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
            return buildErrorJson("BAD Request" + e.getMessage());
        }
    }
    private ServerResponse <?> dispatch (ClientRequest request)
    {
        RequestHandler handler = _handlers.get(request.getRequestType());
        if(handler == null) {
            return ServerResponse.error("BAD Request" + "No handler for request type: " + request.getRequestType());
        }

            try{
                return handle(request)
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


        //RECIPES

        //INGREDIENTS

        //Session
        _handlers.put("Disconnect", this::handleDisconnect);
    }
    private ServerResponse<?> handleGetAllUsers(ClientRequest request) throws Exception
    {
        List<User> users = _userDao.findAll();
        return ServerResponse.ok("retrieved" + users.size() + " users", users);
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
        return ServerResponse.ok("User found", found.get());
    }

}
