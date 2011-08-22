package org.pasut.quecomemoshoy.services.config;

import org.opensource.pasut.persister.mongodb.PersisterService;
import org.pasut.quecomemoshoy.repository.RecipeProvider;
import org.pasut.quecomemoshoy.repository.RecipeProviderImpl;
import org.pasut.quecomemoshoy.repository.config.PersisterServiceProvider;
import org.pasut.quecomemoshoy.services.RecipesService;

import com.google.inject.AbstractModule;

public class Builder extends AbstractModule {

	@Override
	protected void configure() {
		this.bind(PersisterService.class).toProvider(PersisterServiceProvider.class);
		this.bind(RecipeProvider.class).to(RecipeProviderImpl.class);
		this.bind(RecipesService.class);
	}

}
