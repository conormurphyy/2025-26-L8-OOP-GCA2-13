package org.example.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientRequestTest {

    @Test
    void emptyConstructorSetsDefaults() {
        ClientRequest cr = new ClientRequest();
        assertEquals("", cr.getType());
        assertNull(cr.getPayload());
    }

    @Test
    void constructorWithTypeAndPayload() {
        ClientRequest cr = new ClientRequest("USER_INSERT", null);
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
    void getIntReturnsNegativeOneWhenPayloadIsNull() {
        ClientRequest cr = new ClientRequest();
        assertEquals(-1, cr.getInt("someKey"));
    }

    @Test
    void getDoubleReturnsNegativeOneWhenPayloadIsNull() {
        ClientRequest cr = new ClientRequest();
        assertEquals(-1, cr.getDouble("someKey"));
    }

    @Test
    void getBooleanReturnsFalseWhenPayloadIsNull() {
        ClientRequest cr = new ClientRequest();
        assertFalse(cr.getBoolean("someKey"));
    }

    @Test
    void getStringReturnsEmptyWhenPayloadIsNull() {
        ClientRequest cr = new ClientRequest();
        assertEquals("", cr.getString("someKey"));
    }

}