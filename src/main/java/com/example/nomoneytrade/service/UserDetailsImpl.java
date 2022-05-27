package com.example.nomoneytrade.service;

import com.example.nomoneytrade.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/*
    Interlayer between authentication object and User entity
 */
public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String username;
    private String email;
    private Boolean isEnabled;
    private String imagePath;
    private String city;
    private String address;
    private String phoneNumber;
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

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isEnabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public UserDetailsImpl(Long id, String username, String email, Boolean isBanned, Collection<? extends GrantedAuthority> roles,
                           String password, String imagePath, String city, String address, String phoneNumber) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.isEnabled = isBanned;
        this.roles = roles;
        this.password = password;
        this.imagePath = imagePath;
        this.city = city;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    static UserDetailsImpl builder(User user) {
        List<GrantedAuthority> roles = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        return new UserDetailsImpl(user.getId(), user.getUsername(), user.getEmail(), user.getBanned(), roles, user.getPassword(), user.getImagePath(), user.getCity(), user.getAddress(), user.getPhoneNumber());
    }
}
