package org.pasut.quecomemoshoy.repository;

import java.util.List;

import org.opensource.pasut.persister.mongodb.PersisterService;
import org.pasut.quecomemoshoy.domain.Recipe;

import com.google.inject.Inject;

public class RecipeProviderImpl implements RecipeProvider {
	private PersisterService persisterService;
	
	@Inject
	public RecipeProviderImpl(PersisterService service){
		this.persisterService = service;
	}
	@Override
	public List<Recipe> getAllRecipes() {
		try {
			return persisterService.find(Recipe.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
