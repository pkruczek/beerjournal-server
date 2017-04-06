package com.beerjournal.datamodel;

import java.util.Collection;

import com.beerjournal.datamodel.repository.Repository;

public abstract class DatamodelTestUtils {
	public static void clearData(Collection<Repository<?>> repositories) {
		repositories.forEach(Repository::deleteAll);
	}
}
