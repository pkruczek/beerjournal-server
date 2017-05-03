package com.beerjournal.infrastructure.security;

import com.beerjournal.breweriana.user.persistence.UserRepository;
import com.beerjournal.breweriana.user.persistence.User;
import com.google.common.collect.ImmutableSet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BjUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findOneByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return BjPrincipal.builder()
                .dbUser(user)
                .username(username)
                .password(user.getPassword())
                .enabled(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .authorities(ImmutableSet.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
    }
}
