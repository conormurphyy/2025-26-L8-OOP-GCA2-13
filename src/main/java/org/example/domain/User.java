package org.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    //Fields
    private int fId;
    private String fUsername;
    private String fUserType;
    private double fUserRating;

    //Constructors
    //empty user
    public User() {
        fId = 0;
        fUsername = "";
        fUserType = "";
        fUserRating = 0.0;
    }

    //User constructor
    public User(int id, String username, String userType, double userRating) {
        if (id < 0) {
            throw new IllegalArgumentException("id must be >= 0");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("username is required");
        }
        if (userType == null || userType.isBlank()) {
            throw new IllegalArgumentException("userType is required");
        }
        if (userRating < 0.0 || userRating > 5.0) {
            throw new IllegalArgumentException("userRating must be 0-5");
        }

        fId = id;
        fUsername = username.trim();
        fUserType = userType.trim();
        fUserRating = userRating;
    }

    //Public API
    public int getId() {
        return fId;
    }

    public void setId(int id) {
        fId = id;
    }

    public String getUsername() {
        return fUsername;
    }

    public void setUsername(String username) {
        fUsername = username;
    }

    public String getUserType() {
        return fUserType;
    }

    public void setUserType(String userType) {
        fUserType = userType;
    }

    public double getUserRating() {
        return fUserRating;
    }

    public void setUserRating(double userRating) {
        fUserRating = userRating;
    }

    //Helpers
    @Override
    public int hashCode() {
        return Objects.hash(fId, fUsername, fUserType, fUserRating);
    }

    @Override
    public String toString() {
        return "User{id=" + fId
                + ", username='" + fUsername + '\''
                + ", userType='" + fUserType + '\''
                + ", userRating=" + fUserRating + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User other = (User) o;
        return fId == other.fId
                && Double.compare(fUserRating, other.fUserRating) == 0
                && Objects.equals(fUsername, other.fUsername)
                && Objects.equals(fUserType, other.fUserType);
    }
}