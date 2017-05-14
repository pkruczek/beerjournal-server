package com.beerjournal.breweriana.event.persistence;

import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;

interface EventCrudRepository extends PagingAndSortingRepository<Event, ObjectId> {

}
