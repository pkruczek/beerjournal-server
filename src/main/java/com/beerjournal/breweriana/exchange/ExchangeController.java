package com.beerjournal.breweriana.exchange;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/exchanges")
@RequiredArgsConstructor
class ExchangeController {

    private final ExchangeFindService exchangeFindService;
    private final ExchangeStateService exchangeStateService;
    private final ExchangeUpdateService exchangeUpdateService;

    @GetMapping("{id}")
    ResponseEntity<ExchangeOfferDetailsDto> getExchangeById(@PathVariable String id) {
        return new ResponseEntity<>(exchangeFindService.findExchangeById(id), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<ExchangeOfferDetailsDto> addExchange(@RequestBody @Validated ExchangeOfferCreateDto createDto) {
        return new ResponseEntity<>(exchangeUpdateService.createExchange(createDto), HttpStatus.CREATED);
    }

    @GetMapping(params = {"offerorId"})
    ResponseEntity<Collection<ExchangeOfferDetailsDto>> getExchangesByOfferor(@RequestParam String offerorId) {
        return new ResponseEntity<>(exchangeFindService.findExchangesByOfferor(offerorId), HttpStatus.OK);
    }

    @GetMapping(params = {"ownerId"})
    ResponseEntity<Collection<ExchangeOfferDetailsDto>> getExchangesByOwner(@RequestParam String ownerId) {
        return new ResponseEntity<>(exchangeFindService.findExchangesByOwnerId(ownerId), HttpStatus.OK);
    }

    @PutMapping("{id}")
    ResponseEntity<ExchangeOfferDetailsDto> updateExchange(@RequestParam String exchangeId,
                                                           @RequestBody @Validated ExchangeUpdateDto updateDto) {
        return new ResponseEntity<>(exchangeUpdateService.updateExchange(exchangeId, updateDto), HttpStatus.OK);
    }

    @PutMapping("{id}/state")
    ResponseEntity<ExchangeStateDto> changeState(@PathVariable("id") String exchangeId,
                                                 @RequestBody ExchangeStateDto stateDto) {
        return new ResponseEntity<>(exchangeStateService.updateState(exchangeId, stateDto), HttpStatus.OK);
    }

}
