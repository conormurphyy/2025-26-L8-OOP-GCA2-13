/*package org.example.recipe;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserJsonTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private User user;
    @BeforeEach
    void setUp() throws Exception {
          user = new User (99,"Alexis","user",4);

    }

    @Test
    void playerToJson_andBack_returnsSameObject() throws Exception {
        String json = MAPPER.writeValueAsString(user);
        User recon = MAPPER.readValue(json,User.class);
        assertEquals(user,recon);

    }

    @Test
    void playerListToJsonAndBackPreserveAllPlayer() throws Exception {
        List<User> og = List.of(user, new User(67,"Hugo","user",3));
        String json = MAPPER.writeValueAsString(og);

        List <User> recon = MAPPER.readValue(json, new TypeReference<List<User>>(){});

        assertEquals(2,recon.size());
        assertEquals(og.get(1),recon.get(1));
    }

    @Test
    void response_withPlayer_roundTripPreserveData() throws Exception {
        Response<User> og = Response.success("Success",user);
        String json = MAPPER.writeValueAsString(og);
        Response<User> recon = MAPPER.readValue(json, new TypeReference<Response<User>>() {});

        assertEquals("SUCCESS",recon.getStatus());
        assertEquals(user,recon.getData());
    }

    @Test
    void responseFailureRoundTripPreserveNullDataAndMessage() throws Exception{
        Response<User> og = Response.failure("Failure");
        String json = MAPPER.writeValueAsString(og);
        Response<User> recon = MAPPER.readValue(json, new TypeReference<Response<User>>() {});

        assertEquals("Failure",recon.getStatus());
        assertNull(recon.getData());
        assertEquals("Failure",recon.getMessage());
    }




}
*/