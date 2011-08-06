RecipeService = function(){
	this.ingredients;
}

RecipeService.prototype.loadRecipes = function(callback){
	var that = this;
	$j.ajax({
		type : 'GET',
		url : 'services/recipes/ingredients',
		success : function(ingredients){
			that.ingredients = ingredients;
			callback(ingredients);
		}
	});
}

RecipeModel = function(recipes){
	this.recipes = recipes;
}

Recipe = function(){
	this.name;
	this.ingredient;
	this.peopleAmountl;
	this.elavoration;
	this.photos;
}

Ingredient = function(){
	this.name;
	this.measure;
	this.value;
}