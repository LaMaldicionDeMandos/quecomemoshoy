RecipeService = function(){
	this.recipes;
}

RecipeService.ARRIVE_RECIPES_EVENT = "arriveRecipesEvent";

RecipeService.prototype.loadRecipes = function(callback){
	var that = this;
	$j.ajax({
		type : 'GET',
		url : 'services/recipes/list',
		success : function(recipes){
			that.recipes = [];
			for(var i=0;i<recipes.length;i++){
				that.recipes.push(new Recipe(recipes[i]));
			}
			ee.emit(RecipeService.ARRIVE_RECIPES_EVENT,that.recipes);
			//callback(ingredients);
		}
	});
}