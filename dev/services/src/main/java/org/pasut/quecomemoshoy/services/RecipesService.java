package org.pasut.quecomemoshoy.services;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.pasut.quecomemoshoy.domain.Ingredient;
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
		//return getRecipesDummy();
		List<Recipe> recipes = provider.getAllRecipes();
		return recipes;
	}

	
	private List<Recipe> getRecipesDummy(){
		Recipe r1 = createArrozConLeche();
		Recipe r2 = createPancho();
		Recipe r3 = createAmburgueza();
		return Arrays.asList(r1,r2,r3);
	}
	
	private Recipe createArrozConLeche(){
		Ingredient arroz = new Ingredient("Arroz", "medio", "Kg");
		Ingredient leche = new Ingredient("Leche", "un", "Litro");
		int porciones = 4;
		String preparacion = "Haces herbir la leche, luego se le agrega el arroz hasta que quede blandito.";
		return new Recipe("Arroz con Leche", preparacion, porciones, Arrays.asList(arroz,leche));
	}
	
	private Recipe createPancho(){
		Ingredient salchicha = new Ingredient("Salchicha", "6", "Unidades");
		Ingredient pan = new Ingredient("Pan de Pancho", "6", "Unidades");
		Ingredient moztaza = new Ingredient("Monztaza", "Un cachito", "");
		Ingredient mayonesa = new Ingredient("Mayonesa", "Un cachito", "");
		Ingredient ketchup = new Ingredient("Ketchup", "Un cachito", "");
		Ingredient aderezo = new Ingredient("Aderezo para pancho", "Un cachito", "");
		int porciones = 3;
		String preparacion = "Metes las salchichas y las pones a herbir hasta que den vueltas, luego cortas el pan y metes la salchicha, por ultimo le agregas cualquier adereso a elección.";
		return new Recipe("Panchos", preparacion, porciones, Arrays.asList(salchicha,pan),Arrays.asList(moztaza,mayonesa,ketchup, aderezo));
	}
	
	private Recipe createAmburgueza(){
		Ingredient amburgueza = new Ingredient("Amburgueza", "2", "Unidades");
		Ingredient pan = new Ingredient("Pan de Amburgueza", "2", "Unidades");
		Ingredient moztaza = new Ingredient("Monztaza", "Un cachito", "");
		Ingredient mayonesa = new Ingredient("Mayonesa", "Un cachito", "");
		Ingredient ketchup = new Ingredient("Ketchup", "Un cachito", "");
		Ingredient cebolla = new Ingredient("Cabolla", "1/2", "Unidades");
		Ingredient tomate = new Ingredient("Tomate", "1/2", "Unidades");
		Ingredient queso = new Ingredient("Queso Cheddar", "2", "Unidades");
		int porciones = 2;
		String preparacion = "Pones las amburguezas en la plancha, cuando está de un lado las das vuelta y pones el queso arriba.\n" +
				"Cortas el pan a la mitad y le pones los aderesos, luego cortas la cebolla en cuadraditos y se la agregas.\n" +
				"Cuando está lista la amburgueza la pones en el pan y a comer.";
		return new Recipe("Amburguezas", preparacion, porciones, Arrays.asList(amburgueza,pan),Arrays.asList(moztaza,mayonesa,ketchup, queso, cebolla, tomate));
	}
	
	
}
