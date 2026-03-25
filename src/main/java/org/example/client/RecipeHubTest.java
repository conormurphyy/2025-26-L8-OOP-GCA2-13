package org.example.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.shared.ClientRequest;
import org.example.shared.RequestType;
import org.example.shared.ServerResponse;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeHubTest {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    private ObjectMapper mapper;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    private int createdId;

    @BeforeEach
    void setup() throws Exception
    {
        mapper = new ObjectMapper();
        socket = new Socket(HOST, PORT);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);

        createdId = -1;

    }
    @AfterEach
    void teardown() throws Exception
    {
        try{
            ClientRequest request = new ClientRequest();
            request.setType("DISCONNECT");
            request.setPayload(new HashMap<>());
            writer.println(mapper.writeValueAsString(request));

            reader.readLine();


        }
        catch (Exception e)
        {

        }

        if(socket != null && !socket.isClosed())
        {
            socket.close();
        }

    }

    @Test
    void testInsertSuccess() throws Exception {

        ClientRequest req = new ClientRequest();
        req.setType("CREATE_USER");

        Map<String, Object> payload = new HashMap<>();
        payload.put("id", 10106710);
        payload.put("username", "testuser");
        payload.put("userType", "user");
        payload.put("userRating", 5.0);
        req.setPayload(payload);

        writer.println(mapper.writeValueAsString(req));
        String rawJson = reader.readLine();
        ServerResponse res = mapper.readValue(rawJson, ServerResponse.class);

        assertEquals("SUCCESS", res.getStatus());
        assertEquals("User created", res.getMessage());
        assertNotNull(res.getData());
    }

    @Test
    void testGetAllSuccess() throws Exception {
        ClientRequest req = new ClientRequest();
        req.setType("GET_ALL_USERS");
        req.setPayload(null);

        writer.println(mapper.writeValueAsString(req));
        String rawJson = reader.readLine();

        ServerResponse res = mapper.readValue(rawJson, ServerResponse.class);

        assertEquals("SUCCESS", res.getStatus());
        assertNotNull(res.getData());

        List<?> users = (List<?>) res.getData();
        assertTrue(users.size() > 0);


    }
    @Test
    void testInsertRecipeSuccess() throws Exception {
        ClientRequest req = new ClientRequest();
        req.setType("CREATE_RECIPE");

        Map<String, Object> payload = new HashMap<>();
        payload.put("id", 200001);
        payload.put("userId", 10101010); // must exist if DB has FK
        payload.put("recipeName", "Pasta");
        payload.put("categoryId", 1);
        payload.put("description", "Simple pasta");
        payload.put("totalCalories", 550.0);
        payload.put("isPublic", true);
        req.setPayload(payload);
        writer.println(mapper.writeValueAsString(req));
        String rawJson = reader.readLine();
        ServerResponse res = mapper.readValue(rawJson, ServerResponse.class);
        assertEquals("SUCCESS", res.getStatus());
        assertEquals("Recipe created", res.getMessage());
        assertNotNull(res.getData());
    }
    @Test
    void testInsertIngredientSuccess() throws Exception {
        ClientRequest req = new ClientRequest();
        req.setType("CREATE_INGREDIENT");

        Map<String,Object> payload = new HashMap<>();
        payload.put("id", 1006701);
        payload.put("name", "Tomato");
        payload.put("calories", 100.0);
        payload.put("protein", 1.0);
        payload.put("carbs", 4.0);
        payload.put("fat", 0.2);
        req.setPayload(payload);
        writer.println(mapper.writeValueAsString(req));
        String rawJson = reader.readLine();
        ServerResponse res = mapper.readValue(rawJson, ServerResponse.class);
        assertEquals("SUCCESS", res.getStatus());
        assertEquals("Ingredient created", res.getMessage());
        assertNotNull(res.getData());
    }

    @Test
    void testGetAllRecipesSuccess() throws Exception {
        ClientRequest req = new ClientRequest();
        req.setType("GET_ALL_RECIPES");
        req.setPayload(null);
        writer.println(mapper.writeValueAsString(req));
        String rawJson = reader.readLine();
        ServerResponse res = mapper.readValue(rawJson, ServerResponse.class);
        assertEquals("SUCCESS", res.getStatus());
    }

    @Test
    void getAllIngredientsSuccess() throws Exception {
        ClientRequest req = new ClientRequest();
        req.setType("GET_ALL_INGREDIENTS");
        req.setPayload(null);
        writer.println(mapper.writeValueAsString(req));
        String rawJson = reader.readLine();
    }
}
