package com.example.nomoneytrade.service;

import com.example.nomoneytrade.entity.User;
import com.example.nomoneytrade.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;

public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(id)
                .orElseThrow(() -> new UsernameNotFoundException("Can`t find user with associated ID!" + id.toString()));
        return UserDetailsImpl.builder(user);
    }
}
