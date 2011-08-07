var recipeModel;
var recipeService;
var page;
$j(document).ready(function(){
	recipeService = new RecipeService();
	var callback = function(data){
		recipeModel = new RecipeModel(data);
	};
	ee.addListener(RecipeService.ARRIVE_RECIPES_EVENT,callback);
	recipeService.loadRecipes();
	page = new Page();
});