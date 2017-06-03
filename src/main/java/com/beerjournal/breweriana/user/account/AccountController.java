package com.beerjournal.breweriana.user.account;

import com.beerjournal.breweriana.user.UserDto;
import com.beerjournal.breweriana.user.account.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    ResponseEntity<UserDto> getLoggedInAccount() {
        return new ResponseEntity<>(accountService.getLoggedInAccount(), HttpStatus.OK);
    }

    @PutMapping
    ResponseEntity<UserDto> modifyAccount(@RequestBody @Validated AccountChangeDetailsDto account) {
        return new ResponseEntity<>(accountService.changeAccountDetails(account), HttpStatus.OK);
    }

    @PutMapping("password")
    ResponseEntity<?> modifyAccountPassword(@RequestBody @Validated AccountChangePasswordDto account) {
        accountService.changeAccountPassword(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("password/reset")
    ResponseEntity<?> resetAccountPassword(@RequestBody @Validated AccountResetPasswordDto account) {
        accountService.resetPassword(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("email")
    ResponseEntity<?> modifyAccountEmail(@RequestBody @Validated AccountChangeEmailDto account) {
        accountService.changeAccountEmail(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    ResponseEntity<UserDto> deleteAccount(@RequestBody @Validated AccountDeleteDto account) {
        return new ResponseEntity<>(accountService.deleteAccount(account), HttpStatus.OK);
    }
}

