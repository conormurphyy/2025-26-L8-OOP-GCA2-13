package org.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testDefaultConstructorAndSettersGetters() {
        User u = new User();

        // Default values
        assertEquals(0, u.getId());
        assertEquals("", u.getUsername());
        assertEquals("", u.getUserType());
        assertEquals(0.0, u.getUserRating());

        // Set values
        u.setId(1);
        u.setUsername("Alice");
        u.setUserType("admin");
        u.setUserRating(4.5);

        assertEquals(1, u.getId());
        assertEquals("Alice", u.getUsername());
        assertEquals("admin", u.getUserType());
        assertEquals(4.5, u.getUserRating());
    }

    @Test
    void testSecondaryConstructor() {
        User u = new User("Bob", "user", 3.0);

        assertEquals(0, u.getId());
        assertEquals("Bob", u.getUsername());
        assertEquals("user", u.getUserType());
        assertEquals(3.0, u.getUserRating());
    }

    @Test
    void testFullConstructor() {
        User u = new User(2, "Charlie", "admin", 5.0);

        assertEquals(2, u.getId());
        assertEquals("Charlie", u.getUsername());
        assertEquals("admin", u.getUserType());
        assertEquals(5.0, u.getUserRating());
    }

    @Test
    void testConstructorExceptions() {
        // Negative ID
        assertThrows(IllegalArgumentException.class, () ->
                new User(-1, "Alice", "user", 3.0));

        // Null username
        assertThrows(IllegalArgumentException.class, () ->
                new User(0, null, "user", 3.0));

        // Blank username
        assertThrows(IllegalArgumentException.class, () ->
                new User(0, "  ", "user", 3.0));

        // Null userType
        assertThrows(IllegalArgumentException.class, () ->
                new User(0, "Alice", null, 3.0));

        // Blank userType
        assertThrows(IllegalArgumentException.class, () ->
                new User(0, "Alice", "   ", 3.0));

        // Rating <0
        assertThrows(IllegalArgumentException.class, () ->
                new User(0, "Alice", "user", -1));

        // Rating >5
        assertThrows(IllegalArgumentException.class, () ->
                new User(0, "Alice", "user", 5.1));
    }

    @Test
    void testEqualsAndHashCode() {
        User u1 = new User(1, "Alice", "user", 4.0);
        User u2 = new User(1, "Alice", "user", 4.0);
        User u3 = new User(2, "Bob", "admin", 3.0);

        // equals
        assertEquals(u1, u2);
        assertNotEquals(u1, u3);
        assertNotEquals(u1, null);
        assertNotEquals(u1, "String");

        // hashCode
        assertEquals(u1.hashCode(), u2.hashCode());
    }

    @Test
    void testToString() {
        User u = new User(1, "Alice", "user", 4.0);
        String s = u.toString();
        assertTrue(s.contains("1"));
        assertTrue(s.contains("Alice"));
        assertTrue(s.contains("user"));
        assertTrue(s.contains("4.0"));
    }
}