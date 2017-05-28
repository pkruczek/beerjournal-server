package com.beerjournal.breweriana.exchange.roles.validator;

import com.beerjournal.breweriana.exchange.persistence.ExchangeOffer;
import com.beerjournal.breweriana.exchange.roles.ExchangeRole;
import com.beerjournal.breweriana.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor(access = PRIVATE)
class OwnerValidator implements ExchangeRoleValidator {

    private final SecurityUtils securityUtils;

    @Override
    public ExchangeRole getSupportedRole() {
        return ExchangeRole.OWNER;
    }

    @Override
    public boolean isValid(ExchangeOffer offer) {
        return securityUtils.checkIfAuthorized(offer.getOwnerId());
    }

}
