package com.beerjournal.breweriana.exchange.roles.validator;

import com.beerjournal.breweriana.exchange.persistence.ExchangeOffer;
import com.beerjournal.breweriana.exchange.roles.ExchangeRole;
import com.beerjournal.breweriana.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OfferorValidator implements ExchangeRoleValidator {

    private final SecurityUtils securityUtils;

    @Override
    public ExchangeRole getSupportedRole() {
        return ExchangeRole.OFFEROR;
    }

    @Override
    public boolean isValid(ExchangeOffer offer) {
        return securityUtils.checkIfAuthorized(offer.getOfferorId());
    }

}
