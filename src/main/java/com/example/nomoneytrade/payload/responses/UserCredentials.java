package com.example.nomoneytrade.payload.responses;

import java.util.List;


/*
    Clean user class (without db staff) for returning user
    as response
 */
public class UserCredentials {
    private Long id;
    private String username;
    private String email;
    private String imagePath;
    private Boolean isEnabled;
    private String password;

    public UserCredentials(Long id, String username, String email, Boolean isBanned, String password, String imagePath) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.isEnabled = isBanned;
        this.password = password;
        this.imagePath = imagePath;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public String getPassword() {
        return password;
    }

    public String getImagePath() {
        return imagePath;
    }
}
