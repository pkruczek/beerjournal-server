package com.beerjournal.infrastructure.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BeerJournalException extends RuntimeException {
    private final ErrorInfo info;
}
