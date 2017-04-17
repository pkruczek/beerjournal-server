package com.beerjournal.breweriana.persistence.item;

import org.springframework.data.mongodb.repository.MongoRepository;

interface ItemDetailsCrudRepository extends MongoRepository<ItemDetails, String> {
}
