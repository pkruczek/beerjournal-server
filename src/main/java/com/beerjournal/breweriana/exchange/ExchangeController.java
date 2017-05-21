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

    private final ExchangeService exchangeService;

    @GetMapping("{id}")
    ResponseEntity<ExchangeItemOfferDetailsDto> getExchangeById(@PathVariable String id) {
        return new ResponseEntity<>(exchangeService.findExchangeById(id), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<ExchangeItemOfferDetailsDto> addExchange(@RequestBody @Validated ExchangeItemOfferCreateDto exchangeDto) {
        return new ResponseEntity<>(exchangeService.createExchange(exchangeDto), HttpStatus.CREATED);
    }

    @GetMapping(params = {"offerorId"})
    ResponseEntity<Collection<ExchangeItemOfferDetailsDto>> getExchangesByOfferor(@RequestParam String offerorId) {
        return new ResponseEntity<>(exchangeService.findExchangesByOfferor(offerorId), HttpStatus.OK);
    }

    @GetMapping(params = {"ownerId"})
    ResponseEntity<Collection<ExchangeItemOfferDetailsDto>> getExchangesByOwner(@RequestParam String ownerId) {
        return new ResponseEntity<>(exchangeService.findExchangesByOwnerId(ownerId), HttpStatus.OK);
    }

    @PutMapping("{id}/status")
    ResponseEntity<ExchangeStatusDto> accept(@PathVariable("id") String exchangeId, @RequestBody ExchangeStatusDto statusDto) {
        return new ResponseEntity<>(exchangeService.updateStatus(exchangeId, statusDto), HttpStatus.OK);
    }

}
