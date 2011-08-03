$(document).ready(function(){
	var callback = function(data){
		rs.recipes = data;
		alert(rs.recipes);
	};
	var rs = new RecipeService();
	rs.loadRecipes(callback);
});

RecipeService = function(){
	this.recipes;
}

RecipeService.prototype.loadRecipes = function(callback){
	$.ajax({
		type : 'GET',
		url : 'services/recipes/ingredients',
		success : callback
	});
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