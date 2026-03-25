package org.example.service;

import org.example.dao.UserDao;
import org.example.domain.User;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    // Stub DAO
    static class StubDao implements UserDao {
        @Override
        public Optional<User> findById(int id) { return Optional.of(new User()); }
        @Override
        public User insert(User user) { return user; }
        @Override
        public List<User> findAll() { return List.of(new User()); }
        @Override
        public boolean updateRating(int id, double rating) {
            return true;
        }
        @Override
        public boolean updateType(int id, String type) {
            return true;
        }

        @Override
        public User updateAll(User user) throws Exception {
            return null;
        }

        @Override
        public boolean deleteById(int id) {
            return true;
        }

        @Override
        public List<User> filterUsers(String userType, Double minRating) throws Exception {
            return List.of();
        }
    }

    @Test
    void testUserServiceSimple() throws Exception {
        StubDao dao = new StubDao();
        UserService service = new UserService(dao);

        User user = new User();

        // addUser
        assertEquals(user, service.addUser(user));

        // findById
        assertTrue(service.findById(1).isPresent());

        // get
        assertTrue(service.get(1).isPresent());

        // list
        assertEquals(1, service.list().size());

        // void methods — just call them
        service.updateRating(1, 4.5);
        service.updateType(1, "admin");
        service.delete(1);

        // static filter
        Predicate<User> alwaysTrue = u -> true;
        Predicate<User> alwaysFalse = u -> false;
        List<User> users = List.of(user);

        assertEquals(1, UserService.filter(users, alwaysTrue).size());
        assertEquals(0, UserService.filter(users, alwaysFalse).size());
    }
    @Test
    void testConstructorNullDao() {
        assertThrows(IllegalArgumentException.class, () -> new UserService(null));
    }
}

// This test was written using chatgpt