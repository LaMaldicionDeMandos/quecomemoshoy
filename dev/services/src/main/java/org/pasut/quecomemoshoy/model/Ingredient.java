package org.pasut.quecomemoshoy.model;


public final class Ingredient {
	
	private String name;
	private String measure;
	private String value;
	
	public Ingredient(){}
	
	public Ingredient(String name, String value, String measure){
		this.name = name;
		this.measure = measure;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}

	public String getMeasure() {
		return measure;
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Ingredient) return false;
		Ingredient i = (Ingredient)o;
		return  this.name.equals(i.getName()) && 
				this.measure.equals(i.getMeasure()) &&
				this.value.equals(i.getValue());
	}
	
	@Override
	public int hashCode(){
		return this.name.hashCode() + this.measure.hashCode() + this.value.hashCode();
	}
	
	@Override
	public String toString(){
		return this.name + " " + value + " " + measure;
	}
	
}
