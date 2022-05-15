package com.example.nomoneytrade.repository;

import com.example.nomoneytrade.entity.Role;
import com.example.nomoneytrade.utils.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}
