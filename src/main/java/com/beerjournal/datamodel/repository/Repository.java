package com.beerjournal.datamodel.repository;

public interface Repository<DataModelClass, EntityClass> {
	public void deleteAll();
	public void delete(EntityClass objectToDelete);
	public DataModelClass save(EntityClass objectToSave);
	public DataModelClass update(EntityClass objectToUpdate);
}
