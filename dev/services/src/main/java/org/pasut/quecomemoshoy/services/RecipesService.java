package org.pasut.quecomemoshoy.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/recipes")
public class RecipesService {
	
	@GET
	@Path("/list")
	public String getData(){
		return "JOJOJO";
	}
}
