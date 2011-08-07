package org.pasut.quecomemoshoy.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;
import org.opensource.pasut.persister.mongodb.annotaions.Persistable;

@Persistable("recipes")
public final class Recipe {
	
	@JsonProperty
	private String id;
	private String name;
	private List<Ingredient> ingredients;
	private List<Ingredient> optionalIngredients;
	private int peopleAmount;
	private String elavoration;
	private List<String> photos;
	private String video;
	
	public Recipe(){}
	
	public Recipe(String name, String elavoration, int peopleAmount, List<Ingredient> ingredients){
		this(name,elavoration,peopleAmount,ingredients,new ArrayList<Ingredient>());
	}
	
	public Recipe(String name, String elavoration, int peopleAmount, List<Ingredient> ingredients, List<Ingredient> optionalIngredients){
		this.name = name;
		this.elavoration = elavoration;
		this.peopleAmount = peopleAmount;
		this.ingredients = ingredients;
		this.optionalIngredients = optionalIngredients;
		
		this.photos = new ArrayList<String>();
		this.video = "";
	}
	
	public void addPhoto(String photo){
		this.photos.add(photo);
	}
	
	public boolean removePhoto(String photo){
		return this.photos.remove(photo);
	}
	
	public void setVideo(String video){
		this.video = video;
	}
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public List<Ingredient> getIngredients() {
		return new ArrayList<Ingredient>(ingredients);
	}
	public int getPeopleAmount() {
		return peopleAmount;
	}
	public String getElavoration() {
		return elavoration;
	}
	public List<String> getPhotos() {
		return new ArrayList<String>(photos);
	}
	public String getVideo() {
		return video;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Recipe) return false;
		Recipe r = (Recipe)o;
		return  this.name.equals(r.getName()) && 
				this.elavoration.equals(r.getElavoration()) &&
				this.ingredients.equals(r.getIngredients()) &&
				this.optionalIngredients.equals(r.getOptionalIngredients()) &&
				this.peopleAmount == this.getPeopleAmount();
	}
	
	@Override
	public int hashCode(){
		return  this.name.hashCode() + 
				this.elavoration.hashCode() + 
				this.ingredients.hashCode() +
				this.optionalIngredients.hashCode() +
				this.peopleAmount;
	}
	
	@Override
	public String toString(){
		return new ObjectMapper().convertValue(this, HashMap.class).toString();
	}

	public List<Ingredient> getOptionalIngredients() {
		return optionalIngredients;
	}
}
