package org.example.service;

import org.example.dao.UserDao;
import org.example.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

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
    public Optional<User> findById (int id) throws Exception
    {
        return _userDao.findById(id);
    }
    public User addUser(User user) throws Exception
    {
        return _userDao.insert(user);
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
    public static List <User> filter(List <User> users,Predicate <User> filter)
    {
        List <User> res = new ArrayList<>();

        for(User u : users)
        {
            if(filter.test(u))
            {
                res.add(u);
            }

        }
        return res;
    }




}
