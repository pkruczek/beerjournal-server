package com.beerjournal.breweriana.exchange.roles.validator;

import com.beerjournal.breweriana.exchange.persistence.ExchangeOffer;
import com.beerjournal.breweriana.exchange.roles.ExchangeRole;

public interface ExchangeRoleValidator {

    ExchangeRole getSupportedRole();

    boolean isValid(ExchangeOffer offer);

}
