package org.pasut.quecomemoshoy.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.opensource.pasut.persister.mongodb.annotaions.Persistable;

@Persistable("ingredients")
public final class IngredientName {
	
	@JsonProperty("_id")
	private String id;
	private String name;
	
	public IngredientName(){}
	
	public IngredientName(String name){
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof IngredientName) return false;
		IngredientName i = (IngredientName)o;
		return  this.name.equals(i.getName());
	}
	
	@Override
	public int hashCode(){
		return this.name.hashCode();
	}
	
	@Override
	public String toString(){
		return this.name;
	}
}
