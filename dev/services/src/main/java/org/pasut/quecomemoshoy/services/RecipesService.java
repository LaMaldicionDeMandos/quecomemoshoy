package org.pasut.quecomemoshoy.services;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.pasut.quecomemoshoy.domain.Recipe;
import org.pasut.quecomemoshoy.repository.RecipeProvider;

import com.google.inject.Inject;

@Path("/recipes")
public class RecipesService {
	private RecipeProvider provider;
	
	@Inject
	public RecipesService(RecipeProvider provider){
		this.provider = provider;
	}
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Recipe> getRecipes(){
		List<Recipe> recipes = provider.getAllRecipes();
		return recipes;
	}
	
}
