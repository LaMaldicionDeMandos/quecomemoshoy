$(document).ready(function(){
	RecipeService.loadIngredients(onArriveData);
});

function onArriveData(data){	
	var body = $("body");
 	for(i=0; i<data.length;i++){
		var ingredientBox = document.createTextNode(data[i]);
		var div = document.createElement("div");
		div.appendChild(ingredientBox);
		body.append("<div>"+data[i]+"</div>");
	}
}