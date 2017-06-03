package com.beerjournal.breweriana.user.account;

import com.beerjournal.breweriana.user.UserDto;
import com.beerjournal.breweriana.user.account.dto.*;
import com.beerjournal.breweriana.user.persistence.User;
import com.beerjournal.breweriana.user.persistence.UserRepository;
import com.beerjournal.breweriana.utils.SecurityUtils;
import com.beerjournal.infrastructure.error.BeerJournalException;
import com.beerjournal.infrastructure.mail.MailModel;
import com.beerjournal.infrastructure.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.beerjournal.infrastructure.error.ErrorInfo.*;

@Service
@RequiredArgsConstructor
class AccountService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final SecurityUtils securityUtils;

    UserDto getLoggedInAccount() {
        ObjectId userObjectId = securityUtils.getCurrentlyLoggedInUserId();
        User currentUser = verifyUserById(userObjectId);
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
        User currentUser = verifyUserById(userObjectId);
        verifyPasswordEquality(password, currentUser);
        return currentUser;
    }

    private User verifyUserById(ObjectId userObjectId) {
        return userRepository
                .findOneById(userObjectId)
                .orElseThrow(() -> new BeerJournalException(USER_NOT_FOUND_BY_ID));
    }

    private User verifyUserByMail(String mail) {
        return userRepository
                .findOneByEmail(mail)
                .orElseThrow(() -> new BeerJournalException(USER_NOT_FOUND_BY_MAIL));
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

    void resetPassword(AccountResetPasswordDto account) {
        User currentUser = verifyUserByMail(account.getEmail());
        String generatedPassword = RandomStringUtils.randomAlphabetic(1).toUpperCase() + UUID.randomUUID().toString();
        User modifiedUser = currentUser.withPassword(encodePassword(generatedPassword));
        notifyChangePassword(modifiedUser, generatedPassword);
        userRepository.update(modifiedUser);
    }

    private void notifyChangePassword(User user, String password) {
        MailModel mailModel = MailModel.builder()
                .body("<p>Your password was reset to </p></br><h3>" + password + "</h3>")
                .to(user.getEmail())
                .subject("Password change")
                .html(true)
                .build();

        mailService.sendMail(mailModel);
    }
}
