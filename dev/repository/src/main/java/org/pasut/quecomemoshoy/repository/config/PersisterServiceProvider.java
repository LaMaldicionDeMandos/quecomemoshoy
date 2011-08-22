package org.pasut.quecomemoshoy.repository.config;

import org.opensource.pasut.persister.mongodb.MongoPersisterService;
import org.opensource.pasut.persister.mongodb.PersisterService;

import com.google.inject.Provider;

public class PersisterServiceProvider implements Provider<PersisterService> {

	@Override
	public PersisterService get() {
		PersisterService service = null;
		try {
			service = new MongoPersisterService("qch", "localhost", 27017);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return service;
	}

}
