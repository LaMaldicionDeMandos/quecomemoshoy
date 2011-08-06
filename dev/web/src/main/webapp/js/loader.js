var recipeModel;
var recipeService;
var page;
$j(document).ready(function(){
	page = new Page();
	var callback = function(data){
		recipeModel = new RecipeModel(data);
		//alert(recipeModel.recipes);
	};
	recipeService = new RecipeService();
	recipeService.loadRecipes(callback);
});