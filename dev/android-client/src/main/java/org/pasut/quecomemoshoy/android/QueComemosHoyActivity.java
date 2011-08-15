package org.pasut.quecomemoshoy.android;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.pasut.quecomemoshoy.domain.Ingredient;
import org.pasut.quecomemoshoy.domain.Recipe;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;

public class QueComemosHoyActivity extends Activity {
	private static String TAG = "quecomemoshoy-android";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
        setContentView(R.layout.main);
        loadRecipes(this);
    }
	
	private void loadRecipes(final Activity app){
		AsyncTask<Void, Void, String> task = new RecipeLoadTask(this);
		task.execute();
	}
	
	private class RecipeLoadTask extends AsyncTask<Void, Void, String>{
		private Activity owner;
		
		public RecipeLoadTask(Activity owner){
			this.owner = owner;
		}
		
		@Override
		protected String doInBackground(Void... arg0) {
			AndroidHttpClient httpClient = AndroidHttpClient.newInstance("QueComemosHoy");
			HttpGet get = new HttpGet("http://192.168.1.101:8080/quecomemoshoy-web/services/recipes/list");
			String data = null;
			try{
				data = httpClient.execute(get, new BasicResponseHandler());	
				System.out.println(data);
			}catch(Exception e){
				e.printStackTrace();
			}
			return data;
		}
		
		 protected void onPostExecute(String result) {
			 List<Recipe> recipes = new ArrayList<Recipe>();
			 try {
				recipes = getRecipes(result);
				 System.out.println(recipes);
			 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }
			 
			 loadQuickSearch(new ArrayList<Recipe>(recipes));
			 loadList(new ArrayList<Recipe>(recipes));
	     }
		 
		 private void loadQuickSearch(List<Recipe> recipes){
			 AutoCompleteTextView quickSearch = (AutoCompleteTextView)findViewById(R.id.quickSearch);
			 quickSearch.setAdapter(new ArrayAdapter<Recipe>(owner, android.R.layout.simple_dropdown_item_1line,recipes));
			 quickSearch.setOnKeyListener(new OnKeyPressListener(owner));
		 }
		 
		 private void loadList(List<Recipe> recipes){
			 ListView recipeList = (ListView)findViewById(R.id.recipeList);
			 recipeList.setAdapter(new ArrayAdapter<Recipe>(owner,android.R.layout.simple_list_item_1,recipes));
			 recipeList.setOnItemClickListener(new OnSelectRecipeListener(owner));
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
			 Recipe recipe = new Recipe(name, "", 1, new ArrayList<Ingredient>());
			 return recipe;
		 }
	}
	
	private class OnSelectRecipeListener implements AdapterView.OnItemClickListener{
		private Activity owner;
		protected OnSelectRecipeListener(Activity owner){
			this.owner = owner;
		}
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			AlertDialog.Builder builder = new AlertDialog.Builder(owner);
			AlertDialog alert = builder.setMessage("Item seleccionado: " + parent.getItemAtPosition(position)).setTitle("Primer Alert").create();
			alert.show();
			
			System.out.println(position);
		}
		
	}
	
	private class OnKeyPressListener implements View.OnKeyListener{
		private Activity owner;
		
		public OnKeyPressListener(Activity owner){
			this.owner = owner;
		}
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			EditText view = (EditText)v;
			System.out.println("key press: " + keyCode + " -- Text: "+ view.getText());
			return false;
		}
		
	}
}
