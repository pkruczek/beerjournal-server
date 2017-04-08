package com.beerjournal.security.service;

import com.beerjournal.datamodel.entity.UserEntity;
import com.beerjournal.datamodel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = repository.getByUsername(username);

        if (userEntity == null) {
            throw new UsernameNotFoundException("Incorrect username");
        }

        Set<GrantedAuthority> userRoles = userEntity
                .getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails
                .User(userEntity.getUsername(), userEntity.getPassword(), userRoles);
    }
}
