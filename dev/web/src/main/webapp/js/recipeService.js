var recipeModel;
var recipeService;
$(document).ready(function(){
	var callback = function(data){
		recipeModel = new RecipeModel(data);
		alert(recipeModel.recipes);
	};
	recipeService = new RecipeService();
	recipeService.loadRecipes(callback);
});

RecipeService = function(){
}

RecipeService.prototype.loadRecipes = function(callback){
	$.ajax({
		type : 'GET',
		url : 'services/recipes/ingredients',
		success : callback
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