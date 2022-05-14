package com.example.nomoneytrade.payload.responses;

import java.util.List;

public class UserCredentials {
    private Long id;
    private String username;
    private String email;
    private Boolean isBanned;
    private String password;
    private List<String> roles;

    public UserCredentials(Long id, String username, String email, Boolean isBanned, String password, List<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.isBanned = isBanned;
        this.password = password;
        this.roles = roles;
    }
}
