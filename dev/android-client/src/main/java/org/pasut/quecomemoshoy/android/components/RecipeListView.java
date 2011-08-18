package org.pasut.quecomemoshoy.android.components;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.pasut.quecomemoshoy.android.R;
import org.pasut.quecomemoshoy.android.components.RecipesQuickSearchText.OnWriteCharacterListener;
import org.pasut.quecomemoshoy.android.model.ModelLocator;
import org.pasut.quecomemoshoy.domain.Recipe;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RecipeListView extends ListView implements OnWriteCharacterListener, RecipeSelector{
	private RecipeSelectorHelper selectorHelper = new RecipeSelectorHelper();
	
	public RecipeListView(Context context) {
		super(context);
		init();
	}
	
	public RecipeListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public RecipeListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void updateAdapter(List<Recipe> recipes){
		setAdapter(new ArrayAdapter<Recipe>(getContext(),R.layout.notepad_list_renderer,recipes));
		setOnItemClickListener(new OnRecipeClickListener());
	}
	
	private void init(){
		updateAdapter(ModelLocator.getInstance().getRecipes());
	}

	@Override
	public void onWriteCharacter(String text) {
		filter(text);
	}
	
	private void filter(final String pattern){
		List<Recipe> recipes = ModelLocator.getInstance().getRecipes();
		CollectionUtils.filter(recipes, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				if(!(object instanceof Recipe)) return false;
				Recipe recipe = (Recipe)object;
				return recipe.getName().toLowerCase().indexOf(pattern.toLowerCase())>=0;
			}
		});
		updateAdapter(recipes);
	}

	@Override
	public void addSelectRecipeListener(OnSelectRecipeListener listener) {
		selectorHelper.addSelectRecipeListener(listener);
		
	}

	@Override
	public void removeSelectRecipeListener(OnSelectRecipeListener listener) {
		selectorHelper.removeSelectRecipeListener(listener);
	}
	
	private class OnRecipeClickListener implements AdapterView.OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Recipe recipe = (Recipe)parent.getItemAtPosition(position);
			selectorHelper.dispatch(recipe);
		}
		
	}
}
