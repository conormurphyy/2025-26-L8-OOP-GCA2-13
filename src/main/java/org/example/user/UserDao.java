package org.example.user;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    int insert(String username) throws Exception;
    Optional<User> findById(int id) throws Exception;
    List<User> findAll() throws Exception;
    User updateRating(int id, double rating) throws Exception;
    User updateType(int id, String type) throws Exception;
    boolean deleteById(int id) throws Exception;
}
