package org.pasut.quecomemoshoy.services;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.pasut.quecomemoshoy.model.Ingredient;
import org.pasut.quecomemoshoy.model.Recipe;

@Path("/recipes")
public class RecipesService {
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Recipe> getRecipes(){
		return getRecipesDummy();
	}
	
	private List<Recipe> getRecipesDummy(){
		Recipe r1 = createArrozConLeche();
		Recipe r2 = createPancho();
		return Arrays.asList(r1,r2);
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
	
	
}
