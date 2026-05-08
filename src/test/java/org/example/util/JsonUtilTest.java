package org.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonUtilTest {

    static class User {
        public String name;
        public int age;

        public User() {
        }

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    @Test
    void toJsonValidObjectReturnsJsonString() throws JsonProcessingException {
        User user = new User("Conor", 30);

        String json = JsonUtil.toJson(user);

        assertNotNull(json);
        assertTrue(json.contains("Conor"));
        assertTrue(json.contains("30"));

    }

    @Test
    void toJsonNullObjectThrowsIllegalArgument() {

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> JsonUtil.toJson(null)
        );

        assertEquals("entity must not be null", ex.getMessage());

    }

    @Test
    void fromJsonValidJsonReturnsObject() throws JsonProcessingException {

        String json = "{\"name\":\"Conor\",\"age\":30}";

        User user = JsonUtil.fromJson(json, User.class);

        assertNotNull(user);
        assertEquals("Conor", user.name);
        assertEquals(30, user.age);

    }

    @Test
    void fromJsonBlankJsonThrowsIllegalArgument() {

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> JsonUtil.fromJson(" ", User.class)
        );

        assertEquals("json must not be blank", ex.getMessage());

    }

    @Test
    void fromJsonNullClassThrowsIllegalArgument() {

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> JsonUtil.fromJson("{\"name\":\"Conor\"}", null)
        );

        assertEquals("clazz must not be null", ex.getMessage());

    }

    @Test
    void listFromJsonValidJsonReturnsList() throws JsonProcessingException {

        String json = """
                [
                    {"name":"Conor","age":30},
                    {"name":"Niall","age":25}
                ]
                """;

        List<User> users = JsonUtil.listFromJson(json, User.class);

        assertNotNull(users);
        assertEquals(2, users.size());

        assertEquals("Conor", users.get(0).name);
        assertEquals(30, users.get(0).age);

        assertEquals("Niall", users.get(1).name);
        assertEquals(25, users.get(1).age);

    }

    @Test
    void listFromJsonBlankJsonThrowsIllegalArgument() {

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> JsonUtil.listFromJson("", User.class)
        );

        assertEquals("json must not be blank", ex.getMessage());

    }

    @Test
    void listFromJsonNullElementTypeThrowsIllegalArgument() {

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> JsonUtil.listFromJson("[]", null)
        );

        assertEquals("elementType must not be null", ex.getMessage());

    }

    @Test
    void getMapperNotNullReturnsSameInstance() {

        assertNotNull(JsonUtil.getMapper());
        assertSame(JsonUtil.getMapper(), JsonUtil.getMapper());

    }
}