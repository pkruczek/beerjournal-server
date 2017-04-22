package com.beerjournal.security;

import com.beerjournal.breweriana.persistence.UserRepository;
import com.beerjournal.breweriana.persistence.user.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String execute(Connection<?> connection) {
        UserProfile userProfile = connection.fetchUserProfile();
        String generatedPassword = RandomStringUtils.randomAlphanumeric(8);
        User user = User.of(userProfile.getFirstName(), userProfile.getLastName(), userProfile.getEmail(), generatedPassword);
        userRepository.save(user);
        return user.getEmail();
    }
}
