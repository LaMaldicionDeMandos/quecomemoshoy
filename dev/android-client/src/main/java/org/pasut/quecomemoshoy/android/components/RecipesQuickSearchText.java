package org.pasut.quecomemoshoy.android.components;

import java.util.ArrayList;
import java.util.List;

import org.pasut.quecomemoshoy.android.model.ModelLocator;
import org.pasut.quecomemoshoy.domain.Recipe;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

public class RecipesQuickSearchText extends AutoCompleteTextView implements RecipeSelector{
	private List<OnWriteCharacterListener> listeners = new ArrayList<RecipesQuickSearchText.OnWriteCharacterListener>();
	private RecipeSelectorHelper selectorHelper = new RecipeSelectorHelper();
	public RecipesQuickSearchText(Context context) {
		super(context);
		init();	
	}
	
	public RecipesQuickSearchText(Context context, AttributeSet attrs, int defStyle){
		super(context,attrs,defStyle);
		init();
	}
	
	public RecipesQuickSearchText(Context context, AttributeSet attrs){
		super(context,attrs);
		init();
	}
	
	private void init(){
		setAdapter(new ArrayAdapter<Recipe>(getContext(), android.R.layout.simple_dropdown_item_1line,ModelLocator.getInstance().getRecipes()));
		setOnItemClickListener(new OnRecipeClickListener());
		setOnKeyListener(new OnKeyPressListener());
	}
	
	public void addWriteCharacterListener(OnWriteCharacterListener listener){
		listeners.add(listener);
	}
	
	public void removeWriteCharacterListener(OnWriteCharacterListener listener){
		listeners.remove(listener);
	}
	
	public interface OnWriteCharacterListener{
		public void onWriteCharacter(String text);
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
	
	private class OnKeyPressListener implements View.OnKeyListener{
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			EditText view = (EditText)v;
			for (OnWriteCharacterListener listener : listeners) {
				listener.onWriteCharacter(view.getText().toString());
			}
			return false;
		}	
	}

}
