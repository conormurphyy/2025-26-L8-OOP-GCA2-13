package org.example.user;

public class User {
    private int id;
    private String username;
    private String userType;
    private double userRating;

    public User(int id, String username, String userType, double userRating) {
        if(id<0)
        {
            throw new IllegalArgumentException("id must be positive");
        }
        if(username == null || username.isEmpty())
        {
            throw new IllegalArgumentException("username must not be empty");
        }
        if(userType == null || userType.isEmpty())
        {
            throw new IllegalArgumentException("userType must not be empty");
        }
        if(userRating < 0 || userRating > 5)
        {
            throw new IllegalArgumentException("userRating must be between 0 and 5");
        }

        this.id = id;
        this.username = username;
        this.userType = userType;
        this.userRating = userRating;
    }

    public int id()
    {
        return id;
    }
    public String username()
    {
        return username;
    }

    public String userType()
    {
        return userType;
    }
    public double userRating()
    {
        return userRating;
    }

    @Override
    public String toString()
    {
        return "User{" + "id=" + id + ", username='" + username + '\'' + ", userType='" + userType + '\'' + ", userRating=" + userRating + '}';
    }
}
