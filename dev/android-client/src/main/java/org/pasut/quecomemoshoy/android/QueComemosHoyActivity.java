package org.pasut.quecomemoshoy.android;

import org.pasut.quecomemoshoy.android.components.OnSelectRecipeListener;
import org.pasut.quecomemoshoy.android.components.RecipeListView;
import org.pasut.quecomemoshoy.android.components.RecipesQuickSearchText;
import org.pasut.quecomemoshoy.domain.Recipe;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;

public class QueComemosHoyActivity extends Activity implements OnSelectRecipeListener{
	private static String TAG = "quecomemoshoy-android";

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
        setContentView(R.layout.main);
        RecipesQuickSearchText quickSearch = (RecipesQuickSearchText)findViewById(R.id.quickSearch);
        RecipeListView listView = (RecipeListView)findViewById(R.id.recipeList);
        quickSearch.addWriteCharacterListener(listView);
        quickSearch.addSelectRecipeListener(this);
        listView.addSelectRecipeListener(this);
    }

	@Override
	public void onSelectRecipe(Recipe recipe) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		AlertDialog alert = builder.setMessage(recipe.toString()).setTitle("Item Seleccionado:").create();
		alert.show();		
	}
}
