package com.example.nomoneytrade.service;

import com.example.nomoneytrade.entity.User;
import com.example.nomoneytrade.utils.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String username;
    private String email;
    private Boolean isBanned;
    private Collection<? extends GrantedAuthority> roles;

    @JsonIgnore
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public UserDetailsImpl(Long id, String username, String email, Boolean isBanned, Collection<? extends GrantedAuthority> roles, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.isBanned = isBanned;
        this.roles = roles;
        this.password = password;
    }

    static UserDetailsImpl builder(User user) {
        List<GrantedAuthority> roles = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        return new UserDetailsImpl(user.getId(), user.getUsername(), user.getEmail(), user.getBanned(), roles, user.getPassword());
    }
}
