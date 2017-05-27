package com.beerjournal.breweriana.exchange.roles.validator;

import com.beerjournal.breweriana.exchange.roles.ExchangeRole;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ExchangeRoleValidatorFactory {

    private final Set<ExchangeRoleValidator> validators;
    private final Map<ExchangeRole, ExchangeRoleValidator> validatorMapping;

    public ExchangeRoleValidatorFactory(Set<ExchangeRoleValidator> validators) {
        this.validators = validators;
        this.validatorMapping = validatorMapping();
    }

    public ExchangeRoleValidator getValidator(ExchangeRole role) {
        return validatorMapping.get(role);
    }

    private Map<ExchangeRole, ExchangeRoleValidator> validatorMapping() {
        return validators.stream()
                .collect(Collectors.toMap(
                        ExchangeRoleValidator::getSupportedRole,
                        Function.identity()));
    }
}
