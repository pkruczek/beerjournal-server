package com.beerjournal.infrastructure.security.facebook;

import com.beerjournal.breweriana.user.persistence.User;
import com.beerjournal.breweriana.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FacebookConnectionSignup implements ConnectionSignUp {

    private final UserRepository userRepository;

    @Override
    public String execute(Connection<?> connection) {
        UserProfile userProfile = connection.fetchUserProfile();

        User user = userRepository.findOneByEmail(userProfile.getEmail())
                .orElseGet(() -> createUser(userProfile));

        return user.getEmail();
    }

    private User createUser(UserProfile userProfile) {
        return userRepository.save(User.builder()
                .firstName(userProfile.getFirstName())
                .lastName(userProfile.getLastName())
                .email(userProfile.getEmail())
                .password(randomPassword())
                .build());
    }

    private String randomPassword() {
        return RandomStringUtils.randomAlphanumeric(8);
    }

}
