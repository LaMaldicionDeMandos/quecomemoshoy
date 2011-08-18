package org.pasut.quecomemoshoy.android.components;

import java.util.ArrayList;
import java.util.List;

import org.pasut.quecomemoshoy.domain.Recipe;

public class RecipeSelectorHelper implements RecipeSelector {
	private List<OnSelectRecipeListener> listeners = new ArrayList<OnSelectRecipeListener>();
	@Override
	public void addSelectRecipeListener(OnSelectRecipeListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeSelectRecipeListener(OnSelectRecipeListener listener) {
		listeners.remove(listener);
	}
	
	public void dispatch(Recipe recipe){
		for(OnSelectRecipeListener listener : listeners){
			listener.onSelectRecipe(recipe);
		}
	}

}
