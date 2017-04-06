package com.beerjournal.datamodel.repository;

import java.util.Collection;

public interface Repository<DataModelClass, EntityClass> {
	public Collection<EntityClass> getAll();
	public void deleteAll();
	public void delete(EntityClass objectToDelete);
	public DataModelClass save(EntityClass objectToSave);
	public DataModelClass update(EntityClass objectToUpdate);
}
