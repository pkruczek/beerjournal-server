package com.beerjournal.breweriana.utils;

import com.beerjournal.infrastructure.security.BjPrincipal;
import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public final class SecurityUtils {

    public boolean checkIfAuthorized(String userId) {
        return checkAuthorization(Converters.toObjectId(userId));
    }

    public boolean checkIfAuthorized(ObjectId userId) {
        return checkAuthorization(userId);
    }

    private boolean checkAuthorization(ObjectId userId) {
        BjPrincipal currentUser = getCurrentlyLoggedInUser();
        ObjectId currentUserId = currentUser.getDbUser().getId();
        return currentUserId.equals(userId);
    }

    private BjPrincipal getCurrentlyLoggedInUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("User not found!");
        }
        return (BjPrincipal) authentication.getPrincipal();
    }

}
