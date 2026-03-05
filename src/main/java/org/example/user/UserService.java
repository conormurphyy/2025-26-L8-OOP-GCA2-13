package org.example.user;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class UserService
{
    public static Set<String> ALLOWED = Set.of("admin", "user");

    private UserDao _userDao;

    public UserService(UserDao dao)
    {
        if(dao == null)
        {
            throw new IllegalArgumentException("dao must not be null");
        }
        _userDao = dao;
    }

    public int addUser(String username) throws Exception
    {
        return _userDao.insert(username);
    }

    public Optional<User> get(int id) throws Exception
    {
        return _userDao.findById(id);
    }
    public List<User> list() throws Exception
    {
        return _userDao.findAll();
    }
    public void updateRating(int id, double rating) throws Exception
    {
        _userDao.updateRating(id, rating);
    }
    public void updateType(int id, String type) throws Exception
    {
        _userDao.updateType(id, type);
    }
    public void delete(int id) throws Exception
    {
        _userDao.deleteById(id);
    }
}
