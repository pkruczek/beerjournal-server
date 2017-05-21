package com.beerjournal.breweriana.user.account;

import com.beerjournal.breweriana.user.UserDto;
import com.beerjournal.breweriana.user.account.dto.*;
import com.beerjournal.breweriana.user.persistence.User;
import com.beerjournal.breweriana.user.persistence.UserRepository;
import com.beerjournal.breweriana.utils.SecurityUtils;
import com.beerjournal.infrastructure.error.BeerJournalException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.beerjournal.infrastructure.error.ErrorInfo.*;

@Service
@RequiredArgsConstructor
class AccountService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtils securityUtils;

    UserDto getLoggedInAccount() {
        ObjectId userObjectId = securityUtils.getCurrentlyLoggedInUserId();
        User currentUser = verifyUser(userObjectId);
        return UserDto.of(currentUser);
    }

    UserDto changeAccountDetails(AccountChangeDetailsDto accountDetails) {
        User currentUser = getUserForModification(accountDetails.getPassword());
        User modifiedUser = currentUser
                .withFirstName(accountDetails.getFirstName())
                .withLastName(accountDetails.getLastName());
        User updatedUser = userRepository.update(modifiedUser);
        return UserDto.of(updatedUser);
    }

    UserDto deleteAccount(AccountDeleteDto account) {
        User currentUser = getUserForModification(account.getPassword());
        verifyEmailEquality(account.getEmail(), currentUser);
        User deletedUser = userRepository.deleteOneById(currentUser.getId());
        return UserDto.of(deletedUser);
    }

    void changeAccountPassword(AccountChangePasswordDto account) {
        User currentUser = getUserForModification(account.getPassword());
        User modifiedUser = currentUser.withPassword(encodePassword(account.getNewPassword()));
        userRepository.update(modifiedUser);
    }

    void changeAccountEmail(AccountChangeEmailDto account) {
        User currentUser = getUserForModification(account.getPassword());
        verifyEmailEquality(account.getEmail(), currentUser);
        User modifiedUser = currentUser.withEmail(account.getNewEmail());
        userRepository.update(modifiedUser);
    }

    private User getUserForModification(String password) {
        ObjectId userObjectId = securityUtils.getCurrentlyLoggedInUserId();
        User currentUser = verifyUser(userObjectId);
        verifyPasswordEquality(password, currentUser);
        return currentUser;
    }

    private User verifyUser(ObjectId userObjectId) {
        return userRepository
                .findOneById(userObjectId)
                .orElseThrow(() -> new BeerJournalException(USER_NOT_FOUND));
    }

    private void verifyEmailEquality(String email, User currentUser) {
        boolean emailCorrect = email.equals(currentUser.getEmail());
        if (!emailCorrect) {
            throw new BeerJournalException(USER_INCORRECT_EMAIL);
        }
    }

    private void verifyPasswordEquality(String rawPassword, User currentUser) {
        boolean passwordCorrect = passwordEncoder.matches(rawPassword, currentUser.getPassword());
        if (!passwordCorrect) {
            throw new BeerJournalException(USER_INCORRECT_PASSWORD);
        }
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
