package org.example.client;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.example.shared.ClientRequest;
import org.example.shared.RequestType;
import org.example.shared.ServerResponse;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.IOException;
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
            request.setPayload(new com.fasterxml.jackson.databind.JsonNode() {
                @Override
                public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {

                }

                @Override
                public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {

                }

                @Override
                public <T extends JsonNode> T deepCopy() {
                    return null;
                }

                @Override
                public JsonToken asToken() {
                    return null;
                }

                @Override
                public JsonParser.NumberType numberType() {
                    return null;
                }

                @Override
                public JsonNode get(int index) {
                    return null;
                }

                @Override
                public JsonNode path(String fieldName) {
                    return null;
                }

                @Override
                public JsonNode path(int index) {
                    return null;
                }

                @Override
                public JsonParser traverse() {
                    return null;
                }

                @Override
                public JsonParser traverse(ObjectCodec codec) {
                    return null;
                }

                @Override
                protected JsonNode _at(JsonPointer ptr) {
                    return null;
                }

                @Override
                public JsonNodeType getNodeType() {
                    return null;
                }

                @Override
                public String asText() {
                    return "";
                }

                @Override
                public JsonNode findValue(String fieldName) {
                    return null;
                }

                @Override
                public JsonNode findPath(String fieldName) {
                    return null;
                }

                @Override
                public JsonNode findParent(String fieldName) {
                    return null;
                }

                @Override
                public List<JsonNode> findValues(String fieldName, List<JsonNode> foundSoFar) {
                    return List.of();
                }

                @Override
                public List<String> findValuesAsText(String fieldName, List<String> foundSoFar) {
                    return List.of();
                }

                @Override
                public List<JsonNode> findParents(String fieldName, List<JsonNode> foundSoFar) {
                    return List.of();
                }

                @Override
                public String toString() {
                    return "";
                }

                @Override
                public boolean equals(Object o) {
                    return false;
                }
            });
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
        req.setPayload((JsonNode) payload);

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
    void testUserUpdateSuccess() throws Exception {
        ClientRequest req = new ClientRequest();
        req.setType("UPDATE_USER");
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", 10101010);
        payload.put("username", "testuser2");
        payload.put("userType", "user");
        payload.put("userRating", 5.0);
        req.setPayload((JsonNode) payload);
        writer.println(mapper.writeValueAsString(req));
        String rawJson = reader.readLine();
        ServerResponse res = mapper.readValue(rawJson, ServerResponse.class);
        assertEquals("SUCCESS", res.getStatus());
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
        req.setPayload((JsonNode) payload);
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
        req.setPayload((JsonNode) payload);
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

    @Test
    void getUserByIDSuccess() throws Exception {
        ClientRequest req = new ClientRequest();
        req.setType("GET_USER_BY_ID");
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", 10101010);
        req.setPayload((JsonNode) payload);

        writer.println(mapper.writeValueAsString(req));
        String rawJson = reader.readLine();

        ServerResponse res = mapper.readValue(rawJson, ServerResponse.class);
        assertEquals("SUCCESS", res.getStatus());

    }
    @Test
    void getRecipeByIDSuccess() throws Exception {
        ClientRequest req = new ClientRequest();
        req.setType("GET_RECIPE_BY_ID");
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", 200001);
        req.setPayload((JsonNode) payload);
    }

}
