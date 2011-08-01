package org.pasut.quecomemoshoy.config;

import org.pasut.quecomemoshoy.services.RecipesService;

import com.google.inject.AbstractModule;

public class Builder extends AbstractModule {

	@Override
	protected void configure() {
		this.bind(RecipesService.class);
	}

}
