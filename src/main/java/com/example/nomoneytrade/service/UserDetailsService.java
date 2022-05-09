package com.example.nomoneytrade.service;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.HttpClientErrorException;

public interface UserDetailsService {
    UserDetails loadUserById(Long id) throws UsernameNotFoundException;
}
