package com.beerjournal.breweriana.exchange;

import com.beerjournal.breweriana.exchange.persistence.ExchangeOffer;
import com.beerjournal.breweriana.exchange.persistence.ExchangeState;
import com.beerjournal.breweriana.exchange.roles.validator.ExchangeRoleValidatorFactory;
import com.beerjournal.infrastructure.error.BeerJournalException;
import com.beerjournal.infrastructure.error.ErrorInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ExchangeUpdatePermissionService {

    private final ExchangeRoleValidatorFactory roleValidatorFactory;

    void validateExchangeStatusUpdater(ExchangeOffer exchangeOffer, ExchangeState newState) {
        if(!isAllowedToChangeState(exchangeOffer, newState)) {
            throw new BeerJournalException(ErrorInfo.EXCHANGE_FORBIDDEN_STATE_MODIFICATION);
        }
    }

    void validateExchangeUpdater(ExchangeOffer exchangeOffer) {
        if(!isAllowedToUpdateExchange(exchangeOffer)) {
            throw new BeerJournalException(ErrorInfo.EXCHANGE_FORBIDDEN_STATE_MODIFICATION);
        }
    }

    private boolean isAllowedToChangeState(ExchangeOffer exchangeOffer, ExchangeState newState) {
        return exchangeOffer.getState()
                .getRolesAllowedToChangeState(newState)
                .stream()
                .anyMatch(role -> roleValidatorFactory.getValidator(role).isValid(exchangeOffer));
    }

    private boolean isAllowedToUpdateExchange(ExchangeOffer exchangeOffer) {
        return exchangeOffer.getState()
                .getRolesAllowedToUpdateExchange()
                .stream()
                .anyMatch(role -> roleValidatorFactory.getValidator(role).isValid(exchangeOffer));
    }

}
