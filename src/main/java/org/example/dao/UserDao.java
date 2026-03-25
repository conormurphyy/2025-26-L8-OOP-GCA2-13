package org.example.dao;

import org.example.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    User insert(User user) throws Exception;
    Optional<User> findById(int id) throws Exception;
    List<User> findAll() throws Exception;
    boolean updateRating(int id, double rating) throws Exception;
    boolean updateType(int id, String type) throws Exception;
    User updateAll(User user) throws Exception;
    boolean deleteById(int id) throws Exception;
    List<User> filterUsers(String userType, Double minRating) throws Exception;
}
