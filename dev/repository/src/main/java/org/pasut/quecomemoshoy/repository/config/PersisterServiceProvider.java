package org.pasut.quecomemoshoy.repository.config;

import org.opensource.pasut.persister.mongodb.MongoPersisterService;
import org.opensource.pasut.persister.mongodb.PersisterService;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class PersisterServiceProvider implements Provider<PersisterService> {
	private String name;
	private String host;
	private int port;
	
	@Inject
	public PersisterServiceProvider(
			@Named("db.name")String dbName, 
			@Named("db.host")String dbHost,
			@Named("db.port")int dbPort){
		name = dbName;
		host = dbHost;
		port = dbPort;
	}
	@Override
	public PersisterService get() {
		PersisterService service = null;
		try {
			service = new MongoPersisterService(name, host, port);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return service;
	}

}
