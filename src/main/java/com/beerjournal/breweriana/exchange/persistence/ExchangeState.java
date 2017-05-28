package com.beerjournal.breweriana.exchange.persistence;

import com.beerjournal.breweriana.exchange.roles.ExchangeRole;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static com.beerjournal.breweriana.exchange.roles.ExchangeRole.OFFEROR;
import static com.beerjournal.breweriana.exchange.roles.ExchangeRole.OWNER;
import static com.google.common.collect.Sets.immutableEnumSet;


@RequiredArgsConstructor
public enum ExchangeState {

    WAITING_FOR_OWNER(immutableEnumSet(OWNER)),
    WAITING_FOR_OFFEROR(immutableEnumSet(OFFEROR)),
    ACCEPTED_BY_OWNER(Collections.emptySet()),
    ACCEPTED_BY_OFFEROR(Collections.emptySet()),
    REJECTED(Collections.emptySet()),
    CANCELED(Collections.emptySet()),
    COMPLETED(Collections.emptySet());

    private Map<ExchangeState, Set<ExchangeRole>> rolesAllowedToChangeState;

    @Getter
    private final Set<ExchangeRole> rolesAllowedToUpdateExchange;

    static {
        WAITING_FOR_OWNER.rolesAllowedToChangeState = ImmutableMap.<ExchangeState, Set<ExchangeRole>>builder()
                .put(WAITING_FOR_OFFEROR, immutableEnumSet(OWNER))
                .put(ACCEPTED_BY_OWNER, immutableEnumSet(OWNER))
                .put(REJECTED, immutableEnumSet(OWNER))
                .put(CANCELED, immutableEnumSet(OFFEROR))
                .build();

        WAITING_FOR_OFFEROR.rolesAllowedToChangeState = ImmutableMap.<ExchangeState, Set<ExchangeRole>>builder()
                .put(WAITING_FOR_OWNER, immutableEnumSet(OFFEROR))
                .put(ACCEPTED_BY_OFFEROR, immutableEnumSet(OFFEROR))
                .put(REJECTED, immutableEnumSet(OFFEROR))
                .build();

        ACCEPTED_BY_OWNER.rolesAllowedToChangeState = ImmutableMap.<ExchangeState, Set<ExchangeRole>>builder()
                .put(REJECTED, immutableEnumSet(OFFEROR))
                .put(COMPLETED, immutableEnumSet(OFFEROR))
                .build();

        ACCEPTED_BY_OFFEROR.rolesAllowedToChangeState = ImmutableMap.<ExchangeState, Set<ExchangeRole>>builder()
                .put(REJECTED, immutableEnumSet(OWNER))
                .put(COMPLETED, immutableEnumSet(OWNER))
                .build();

        REJECTED.rolesAllowedToChangeState = Collections.emptyMap();
        CANCELED.rolesAllowedToChangeState = Collections.emptyMap();
        COMPLETED.rolesAllowedToChangeState = Collections.emptyMap();
    }

    public Set<ExchangeRole> getRolesAllowedToChangeState(ExchangeState newState) {
        return rolesAllowedToChangeState.getOrDefault(newState, Collections.emptySet());
    }

    public boolean canChangeTo(ExchangeState newState) {
        return rolesAllowedToChangeState.containsKey(newState);
    }

    public boolean isModifiable() {
        return !rolesAllowedToUpdateExchange.isEmpty();
    }

}
