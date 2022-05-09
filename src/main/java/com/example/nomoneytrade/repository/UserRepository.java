package com.example.nomoneytrade.repository;


import com.example.nomoneytrade.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    Optional<User> findByUserId(Long id);
    Optional<User> findByUserName(String username);
    Boolean isUsernameOccupied(String username);
    Optional<User> findByEmail(String email);
    Boolean isEmailOccupied(String email);
}
