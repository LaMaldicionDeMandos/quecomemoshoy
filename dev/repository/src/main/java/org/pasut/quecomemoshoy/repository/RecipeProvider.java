package org.pasut.quecomemoshoy.repository;

import java.util.List;

import org.pasut.quecomemoshoy.domain.Recipe;

public interface RecipeProvider {
	public List<Recipe> getAllRecipes();
}
