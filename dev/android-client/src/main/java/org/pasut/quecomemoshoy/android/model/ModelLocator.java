package org.pasut.quecomemoshoy.android.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.pasut.quecomemoshoy.domain.Ingredient;
import org.pasut.quecomemoshoy.domain.Recipe;

import android.net.http.AndroidHttpClient;

public class ModelLocator {
	private Recipe currentRecipe;
	
	private ModelLocator(){}
	
	private static class LazyHolder{
		public static final ModelLocator INSTANCE = new ModelLocator(); 
	}
	
	public static ModelLocator getInstance(){
		return LazyHolder.INSTANCE;
	}
	
	private Object lock = new Object();
	private Future<List<Recipe>> future;
	
	public List<Recipe> getRecipes(){
		Future<List<Recipe>> recipes = future;
		if(recipes==null){
			synchronized(lock){
				recipes = this.future;
				if(recipes==null){
					future = loadRecipes();
				}
			}
		}
		try {
			return new ArrayList<Recipe>(future.get());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Future<List<Recipe>> loadRecipes(){
		return Executors.newSingleThreadExecutor().submit(new LoadRecipesTask());
	}
	
	private class LoadRecipesTask implements Callable<List<Recipe>>{

		@Override
		public List<Recipe> call() throws Exception {
			String data = getDataFromServer("http://192.168.1.101:8080/quecomemoshoy-web/services/recipes/list");
			return createRecipes(data);
		}
		
		private String getDataFromServer(String url){
			AndroidHttpClient httpClient = AndroidHttpClient.newInstance("QueComemosHoy");
			HttpGet get = new HttpGet(url);
			String data = null;
			try{
				data = httpClient.execute(get, new BasicResponseHandler());	
				System.out.println(data);
			}catch(Exception e){
				e.printStackTrace();
			}
			return data;
		}
		
		private List<Recipe> createRecipes(String data){
			List<Recipe> recipes = new ArrayList<Recipe>();
			 try {
				recipes = getRecipes(data);
				 System.out.println(recipes);
			 } catch (Exception e) {
				e.printStackTrace();
			 }
			 return recipes;
		}
		
		 private List<Recipe> getRecipes(String data) throws JSONException{
			 JSONArray jsonArray = new JSONArray(data);
			 List<Recipe> recipes = new ArrayList<Recipe>();
			 for(int i=0;i<jsonArray.length();i++){
				 recipes.add(createRecipe(jsonArray.get(i).toString()));
			 }
			 return recipes;
		 }
		 
		 private Recipe createRecipe(String data) throws JSONException{
			 JSONObject json = new JSONObject(data);
			 String name = json.getString("name");
			 String elavoration = json.getString("elaboration");
			 int peopleAmount = json.getInt("peopleAmount");
			 List<Ingredient> ingredients = getIngredients(json.getJSONArray("ingredients"));
			 List<Ingredient> optionalIngredients = getIngredients(json.getJSONArray("optionalIngredients"));
			 List<String> photos = getPhotos(json.getJSONArray("photos"));
			 String video = json.getString("video");
			 Recipe recipe = new Recipe(name, elavoration, peopleAmount, ingredients, optionalIngredients);
			 for (String photo : photos) {
				recipe.addPhoto(photo);
			 }
			 recipe.setVideo(video);
			 return recipe;
		 }
		 
		 private List<Ingredient> getIngredients(JSONArray list) throws JSONException{
			 List<Ingredient> ingredients = new ArrayList<Ingredient>();
			 for(int i=0;i<list.length();i++){
				 ingredients.add(createIngredient(list.getJSONObject(i)));
			 }
			 return ingredients;
		 }
		 
		 private Ingredient createIngredient(JSONObject json) throws JSONException{
			 String name = json.getString("name");
			 String measure = json.getString("measure");
			 String value = json.getString("value");
			 return new Ingredient(name,value,measure);
		 }
		 
		 private List<String> getPhotos(JSONArray list) throws JSONException{
			 List<String> photos = new ArrayList<String>();
			 for(int i=0;i<list.length();i++){
				 photos.add(list.getString(i));
			 }
			 return photos;
		 }
	}

	public Recipe getCurrentRecipe() {
		return currentRecipe;
	}

	public void setCurrentRecipe(Recipe currentRecipe) {
		this.currentRecipe = currentRecipe;
	}
}
