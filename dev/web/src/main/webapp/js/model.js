RecipeModel = function(recipes){
	this.recipes = recipes;
}

RecipeModel.SELECT_RECIPE_EVENT = "selectRecipeEvent";
RecipeModel.UNSELECT_RECIPE_EVENT = "unselectRecipeEvent";

//Los objetos se arman a partir de su correspondiente Json
Ingredient = function(ingredient){
	this.name = ingredient.name;
	this.measure = ingredient.measure;
	this.value = ingredient.value;
	
	this.toString = function(){
		return this.name + ": " + this.value + " " + this.measure+".";
	}
}

Recipe = function(recipe){
	this.name = recipe.name;
	this.peopleAmount = recipe.peopleAmount;
	this.elaboration = recipe.elaboration;
	this.video = recipe.video;
	this.photos = recipe.photos;
	
	this.ingredients = [];
	this.optionalIngredients = [];
	for(var i=0;i<recipe.ingredients.length;i++){
		this.ingredients.push(new Ingredient(recipe.ingredients[i]));
	}
	for(var i=0;i<recipe.optionalIngredients.length;i++){
		this.optionalIngredients.push(new Ingredient(recipe.optionalIngredients[i]));
	}
	this.hasOptionalIngredients = this.optionalIngredients.length>0;
}