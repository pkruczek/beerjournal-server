package com.beerjournal.datamodel.repository;

import java.util.Collection;

public interface Repository<EntityClass> {
	public Collection<EntityClass> getAll();
	public void deleteAll();
	public void delete(EntityClass objectToDelete);
	public void save(EntityClass objectToSave);
	public void update(EntityClass objectToUpdate);
}
