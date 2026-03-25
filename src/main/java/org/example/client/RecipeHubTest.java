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
            request.setRequestType(String.valueOf(RequestType.DISCONNECT));
            request.setPayload(null);

            System.out.println(mapper.writeValueAsString(request));
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
    void testInsertSuccess() throws Exception
    {
        ClientRequest req = new ClientRequest();
        req.setRequestType(String.valueOf(RequestType.USER_INSERT));

        Map<String, Object> payload = new HashMap<>();
        payload.put("username", "testuser");
        payload.put("userType", "user");
        payload.put("userRating", 5.0);

        System.out.println(mapper.writeValueAsString(req));
        String rawJson = reader.readLine();

        ServerResponse res = mapper.readValue(rawJson, ServerResponse.class);

        assertEquals("success",res.getStatus());
        assertEquals("User created",res.getMessage());
        assertNotNull(res.getData());

        Map<?,?> data = (Map<?,?>) res.getData();
        createdId = (int) data.get("id");

        assertTrue(createdId > 0);
        assertEquals("success",res.getStatus());
        assertEquals("User created",res.getMessage());
    }
}
