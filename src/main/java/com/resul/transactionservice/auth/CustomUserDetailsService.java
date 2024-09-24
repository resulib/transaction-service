package com.resul.transactionservice.auth;

import com.resul.transactionservice.dto.UserDto;
import com.resul.transactionservice.service.UserServiceClient;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserServiceClient userServiceClient;

    public CustomUserDetailsService(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userServiceClient.getUserByUsername(username);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole())));
    }

}