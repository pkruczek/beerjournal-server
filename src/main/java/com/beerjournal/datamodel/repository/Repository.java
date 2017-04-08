package com.beerjournal.datamodel.repository;

import java.util.Collection;

public interface Repository<EntityClass> {
	Collection<EntityClass> getAll();
	void deleteAll();
	void delete(EntityClass objectToDelete);
	void save(EntityClass objectToSave);
	void update(EntityClass objectToUpdate);
}
