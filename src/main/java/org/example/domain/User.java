package org.example.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private int id;
    private String username;
    private String userType;
    private double userRating;

    public User()
    {
        this.username="";
        this.userType="";
        this.userRating=0;
        this.id=0;
    }

    public User(int id, String username, String userType, double userRating) {
        if(id<0)
        {
            throw new IllegalArgumentException("Id must be positive");
        }
        if(username == null || username.isEmpty())
        {
            throw new IllegalArgumentException("Username must not be empty");
        }
        if(userType == null || userType.isEmpty())
        {
            throw new IllegalArgumentException("UserType must not be empty");
        }
        if(userRating < 0 || userRating > 5)
        {
            throw new IllegalArgumentException("UserRating must be between 0 and 5");
        }

        this.id = id;
        this.username = username;
        this.userType = userType;
        this.userRating = userRating;
    }

    public int getId()
    {
        return id;
    }
    public String getUsername()
    {
        return username;
    }

    public String getUserType()
    {
        return userType;
    }
    public double getUserRating()
    {
        return userRating;
    }


    @Override
    public int hashCode()
    {
        return Integer.hashCode(id);
    }
    @Override
    public boolean equals (Object o)
    {
        if (this ==o)
        {
            return true;
        }
        else if(!(o instanceof User))
        {
            return false;
        }
        User u = (User) o;
        return id == u.id && username.equals(u.username) && userType.equals(u.userType) && userRating == u.userRating ;
    }
    @Override
    public String toString()
    {
        return "User{" + "id=" + id + ", username='" + username + '\'' + ", getUserType='" + userType + '\'' + ", getUserRating=" + userRating + '}';
    }
}
