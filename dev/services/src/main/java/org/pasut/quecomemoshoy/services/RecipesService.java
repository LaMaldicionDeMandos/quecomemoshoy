package org.pasut.quecomemoshoy.services;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/recipes")
public class RecipesService {
	
	@GET
	@Path("/ingredients")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getData(){
		return Arrays.asList("Lechuga", "Cebolla", "Limon", "Merluza", "Arina");
	}
}
