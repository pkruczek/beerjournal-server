package com.beerjournal.breweriana.item.rating;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping("item/{itemId}")
    ResponseEntity<Collection<RatingDto>> getItemRatings(@PathVariable(value = "itemId") String itemId) {
        return new ResponseEntity<>(ratingService.getItemRatings(itemId), HttpStatus.OK);
    }

    @GetMapping("user/{userId}")
    ResponseEntity<Collection<RatingDto>> getUserRatings(@PathVariable(value = "userId") String userId) {
        return new ResponseEntity<>(ratingService.getUserRatings(userId), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<RatingDto> addItemRating(@RequestBody @Validated RatingDto rating) {
        return new ResponseEntity<>(ratingService.addItemRating(rating), HttpStatus.CREATED);
    }

    @DeleteMapping
    ResponseEntity<RatingDto> deleteRating(@PathVariable(value = "ratingId") String ratingId) {
        return new ResponseEntity<>(ratingService.deleteRating(ratingId), HttpStatus.OK);
    }
}
