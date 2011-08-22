package org.pasut.quecomemoshoy.android;

import java.util.List;

import org.pasut.quecomemoshoy.android.model.ModelLocator;
import org.pasut.quecomemoshoy.domain.Ingredient;
import org.pasut.quecomemoshoy.domain.Recipe;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Esta tengo que crearla dinamocamente por que le tengo que agregar cosas en runtime
 * @author Marcelo
 *
 */

public class RecipeDetailActivity extends Activity {
	private Recipe recipe;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipe = ModelLocator.getInstance().getCurrentRecipe();
        setContentView(R.layout.recipe_detail);
        
        putTitle(recipe.getName());
        setPeopleAmount(recipe.getPeopleAmount());
        setIngredients((LinearLayout)findViewById(R.id.ingredientBox),recipe.getIngredients());
        setIngredients((LinearLayout)findViewById(R.id.optionalIngredientBox), recipe.getOptionalIngredients());
        setElavoration(recipe.getElaboration());
	}
	
	private void putTitle(String title){
		TextView titleView = (TextView)findViewById(R.id.title);
        titleView.setText(title);
	}
	
	private void setPeopleAmount(int count){
		TextView peopleAmount = (TextView)findViewById(R.id.peopleAmountContainer);
        peopleAmount.setText(getResources().getQuantityString(R.plurals.peopleAmount, count,count));
	}
	
	private void setIngredients(LinearLayout view, List<Ingredient> ingredients){
		TextView textView = new TextView(this);
		textView.setTextColor(0xff000000);
		StringBuilder s = new StringBuilder("");
		for(Ingredient ingredient : ingredients){
			s.append(ingredient+"\n");
		}
		textView.setText(s.toString());
		textView.setTextSize(14);
		view.addView(textView);
	}
	
	private void setElavoration(String elavoration){
		TextView peopleAmount = (TextView)findViewById(R.id.elavoration);
        peopleAmount.setText(elavoration);
	}
}
