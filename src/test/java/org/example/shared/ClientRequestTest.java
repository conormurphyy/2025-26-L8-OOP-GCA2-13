package org.example.shared;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientRequestTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void emptyConstructorSetsDefaults() {
        ClientRequest cr = new ClientRequest();
        assertEquals("", cr.getType());
        assertNull(cr.getPayload());
    }

    @Test
    void constructorWithTypeAndPayloadCurrentBehaviorKeepsPayloadNull() throws Exception {
        JsonNode payload = MAPPER.readTree("{\"id\":1}");
        ClientRequest cr = new ClientRequest("USER_INSERT", payload);

        assertEquals("USER_INSERT", cr.getType());

        assertNull(cr.getPayload());
    }

    @Test
    void setTypeAndGet() {
        ClientRequest cr = new ClientRequest();
        cr.setType("RECIPE_DELETE");
        assertEquals("RECIPE_DELETE", cr.getType());
    }

    @Test
    void setTypeWithNullDefaultsToEmpty() {
        ClientRequest cr = new ClientRequest();
        cr.setType(null);
        assertEquals("", cr.getType());
    }

    @Test
    void getIntThrowsWhenPayloadIsNull() {
        ClientRequest cr = new ClientRequest();
        assertThrows(NullPointerException.class, () -> cr.getInt("someKey"));
    }

    @Test
    void getDoubleThrowsWhenPayloadIsNull() {
        ClientRequest cr = new ClientRequest();
        assertThrows(NullPointerException.class, () -> cr.getDouble("someKey"));
    }

    @Test
    void getBooleanThrowsWhenPayloadIsNull() {
        ClientRequest cr = new ClientRequest();
        assertThrows(NullPointerException.class, () -> cr.getBoolean("someKey"));
    }

    @Test
    void getStringThrowsWhenPayloadIsNull() {
        ClientRequest cr = new ClientRequest();
        assertThrows(NullPointerException.class, () -> cr.getString("someKey"));
    }

    @Test
    void gettersWorkWhenPayloadIsSet() throws Exception {
        JsonNode payload = MAPPER.readTree("{\"i\":42,\"d\":2.5,\"b\":true,\"s\":\"hello\"}");
        ClientRequest cr = new ClientRequest();
        cr.setPayload(payload);

        assertEquals(42, cr.getInt("i"));
        assertEquals(2.5, cr.getDouble("d"), 0.00001);
        assertTrue(cr.getBoolean("b"));
        assertEquals("\"hello\"", cr.getString("s"));
    }
}