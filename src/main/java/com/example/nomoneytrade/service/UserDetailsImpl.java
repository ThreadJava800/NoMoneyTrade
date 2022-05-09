package com.example.nomoneytrade.service;

import com.example.nomoneytrade.entity.User;
import com.example.nomoneytrade.utils.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String username;
    private String email;
    private Boolean isBanned;
    private RoleEnum role;

    @JsonIgnore
    private String password;

    public UserDetailsImpl(Long id, String username, String email, Boolean isBanned, RoleEnum role, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.isBanned = isBanned;
        this.role = role;
        this.password = password;
    }

    public static UserDetailsImpl builder(User user) {
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.isBanned(),
                user.getRole(),
                user.getPassword()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public RoleEnum getRole() {
        return role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isBanned;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isBanned;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isBanned;
    }

    @Override
    public boolean isEnabled() {
        return isBanned;
    }
}
