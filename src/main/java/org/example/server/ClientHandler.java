package org.example.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.IngredientDao;
import org.example.dao.RecipeDao;
import org.example.dao.UserDao;
import org.example.shared.ClientRequest;
import org.example.shared.ServerResponse;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

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
        _mapper=JsonUtil;
        _handlers=new HashMap<>();
        _running=true;

    }
}
