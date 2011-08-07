RecipeService = function(){
	this.ingredients;
}

RecipeService.ARRIVE_INGREDIENTS_EVENT = "arriveIngredientsEvent";

RecipeService.prototype.loadRecipes = function(callback){
	var that = this;
	$j.ajax({
		type : 'GET',
		url : 'services/recipes/ingredients',
		success : function(ingredients){
			that.ingredients = ingredients;
			ee.emit(RecipeService.ARRIVE_INGREDIENTS_EVENT,ingredients);
			//callback(ingredients);
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
	this.video;
}

Ingredient = function(){
	this.name;
	this.measure;
	this.value;
}