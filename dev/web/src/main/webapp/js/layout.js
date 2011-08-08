Page = function(){
	this.component = $j("body");
	this.header = new Header(this,"send","suggest","search");
	this.sendPanel = new SendPanel(this,"send");
	this.suggestPanel = new SuggestPanel(this,"suggest");
	this.searchPanel = new SearchPanel(this,"search",true);
}

//Header
Header = function(parent){
	var that = this;
	//Functions
	this.toTab = function(tabId){
		if(that.selectedTabName == tabId) return;
		Effect.BlindUp(that.selectedTabName,{duration : 0.3});
		Effect.Appear(tabId,{duration : 0.5});
		that.selectedTabName=tabId;	
	}
	
	parent.component.append("<div id='header' class='header'/>");
	this.component = $j("#header");
	
	this.tabs = new Object();
	if(arguments.length>1) 
		that.selectedTabName = arguments[arguments.length-1];
	for ( var i = 1; i < arguments.length; i++) {
		var tabName = arguments[i];
		this.tabs[tabName] = new HeaderTab(this,tabName+"Tab",tabName,tabName.toUpperCase(),that.toTab);
	}
}

HeaderTab = function (parent,id,targetName,title,clickHandler){
	parent.component.append("<div id='"+id+"' class='tab'>"+title+"</div>");
	var that = this;
	this.targetName = ""+targetName;
	this.component = $j("#"+id);
	this.component.click(function(){
		clickHandler(that.targetName);
	});
}

function getVisibleStyle(visible){
	return visible ? "" : "style='display:none' ";
}

//Send Panel
SendPanel = function(parent, id, visible){
	var style = getVisibleStyle(visible);
	parent.component.append("<div id='"+id+"' class='body' "+style+"/>");
	this.component = $j("#"+id);
	this.component.append("<h1>SEND</h1>");
}


//Suggest Panel
SuggestPanel = function(parent, id, visible){
	var style = getVisibleStyle(visible);
	parent.component.append("<div id='"+id+"' class='body' "+style+"/>");
	this.component = $j("#"+id);
	this.component.append("<h1>SUGGEST</h1>");
}


//Search Panel
SearchPanel = function(parent, id, visible){
	var style = getVisibleStyle(visible);
	parent.component.append("<div id='"+id+"' class='body' "+style+"/>");
	this.component = $j("#"+id);
	this.recipeListPanel = new RecipeListPanel(this,"recipeListPanel", "¡Busca recetas por su nombre!");
	this.recipeViewPanel = new RecipeViewPanel(this,"recipeViewPanel");
}

RecipeListPanel = function(parent, id, title){
	parent.component.append("<div id='"+id+"' class='leftPanel' center/>");
	var that = this;
	this.component = $j("#"+id);
	this.component.append("<h1 class='title'>"+title+"<h1>");
	
	this.quickSearch = new QuickSearchInput(this,"quickSearchInput","quickSearchEvent");
	this.list = new RecipeList(this,"recipeListArea");
	
	ee.addListener(this.quickSearch.EVENT_ID,function(value){
		that.list.filter(value);
	})
}

QuickSearchInput = function(parent,id,eventId){
	var that = this;
	this.EVENT_ID = eventId;
	parent.component.append("<input id='"+id+"' class='textInput'/>");
	this.component = $j("#"+id);
	this.component.keyup(function(){
		ee.emit(that.EVENT_ID,that.component.val());
	});
}

RecipeList = function(parent, id){
	parent.component.append("<div id='"+id+"' class='list'/>");
	this.component = $j("#"+id);
	var that = this;
	this.loadRecipes = function(recipes){
		that.recipeList = [];
		for( var i=0;i<recipes.length;i++) {
			that.recipeList.push(new RecipeListItemRenderer(that,recipes[i]));
		}
	}
	
	this.filter = function(value){
		for(var i=0;i<that.recipeList.length;i++){
			var item = that.recipeList[i];
			if(item.recipe.name.toLowerCase().indexOf(value.toLowerCase())<0){
				item.hide();
			}
			else{
				item.show();
			}
		}
	}
	
	if(!recipeModel)
		ee.addListener(RecipeService.ARRIVE_RECIPES_EVENT,this.loadRecipes);
	else
		this.loadRecipes(recipeModel.recipes);
}

RecipeListItemRenderer=function(parent,recipe){
	var that = this;
	this.recipe = recipe;
	this.visible = true;
	this.selected = false;
	this.id = "renderer"+removeSpaces(recipe.name);
	parent.component.append("<div id='"+this.id+"' class='listItemRenderer'>"+this.recipe.name+"</div>");
	this.component = $j("#"+this.id);
	this.component.click(function(){
		that.select();
		ee.emit(RecipeModel.SELECT_RECIPE_EVENT,that.recipe);
	});
	this.hide = function(){
		this.visible = false;
		this.component.hide();
		if(this.selected){
			this.unselect();
			ee.emit(RecipeModel.UNSELECT_RECIPE_EVENT);
		}
	}
	
	this.show = function(){
		this.visible = true;
		this.component.show();
	}
	ee.addListener(RecipeModel.SELECT_RECIPE_EVENT,function(recipe){
		if(recipe==that.recipe) return;
		that.unselect();
	});
	this.unselect = function(){
		this.selected = false;
		this.component.attr("class","listItemRenderer");
	}
	this.select = function(){
		this.selected = true;
		this.component.attr("class","selectedItemRenderer");
	}
}

RecipeViewPanel = function(parent, id){
	var that = this;
	parent.component.append("<div id='"+id+"' class='rightPanel'/>");
	this.component = $j("#"+id);
	this.emptyView = new RecipeEmptyView(this,"recipeEmptyView");
	this.recipeView = new RecipeView(this,"recipeView");
	
	this.emptyView.show();
	this.recipeView.hide();
	
	ee.addListener(RecipeModel.SELECT_RECIPE_EVENT, function(recipe){
		if(recipe){
			that.emptyView.hide();
			that.recipeView.update(recipe);
			that.recipeView.show();
		}
		else{
			that.emptyView.show();
			that.recipeView.hide();
		}
	});		
	
	ee.addListener(RecipeModel.UNSELECT_RECIPE_EVENT, function(){
		that.emptyView.show();
		that.recipeView.hide();
	});
}

RecipeEmptyView = function(parent, id){
	var that = this;
	parent.component.append("<div id='"+id+"' class='recipeEmptyView'><h1>EMPTY_PANEL</h1>");
	this.component = $j("#"+id);
	this.show = function(){
		that.component.show();
	}
	this.hide = function(){
		that.component.hide();
	}
}

RecipeView = function(parent, id){
	var that = this;
	this.recipe = null;
	parent.component.append("<div id='"+id+"' class='recipeFullView'/>");
	this.component = $j("#"+id);
	this.attributesPanel = new RecipeAttributes(this,"recipeAttributesPanel");
	this.elavorationPanel = new RecipeElavoration(this,"recipeElavorationPanel");
	this.show = function(){
		that.component.show();
	}
	this.hide = function(){
		that.component.hide();
	}
	
	this.update = function(recipe){
		this.recipe = recipe;
		this.attributesPanel.update(recipe);
		this.elavorationPanel.update(recipe);
	}
}

RecipeAttributes = function(parent, id){
	this.recipe = null;
	parent.component.append("<div id='"+id+"' class='recipeAttributesPanel' center='center'/>");
	this.component = $j("#"+id);
	this.component.append("<div class='photoPanel'><h3>Fotos en construccion</h3></div>");
	
	this.peopleLabel = new RecipePeopleAmountPanel(this,"peopleLabel");
	this.ingredientsPanel = new IngredientList(this,"ingredientList");
	
	this.update = function(recipe){
		this.recipe = recipe;
		this.peopleLabel.update(recipe);
		this.ingredientsPanel.update(recipe);
	}
}

RecipePeopleAmountPanel = function(parent, id){
	this.recipe = null;
	this.staticText = "Número de personas: ";
	parent.component.append("<div id='"+id+"' class='peopleAmountPanel'/>");
	this.component = $j("#"+id);
	this.update = function(recipe){
		this.recipe = recipe;
		this.component.html(this.staticText + recipe.peopleAmount);
	}
}

IngredientList = function(parent, id){
	this.recipe = null;
	parent.component.append("<div id='"+id+"' class='ingredientsPanel'/>");
	this.component = $j("#"+id);
	this.update = function(recipe){
		this.recipe = recipe;
		$j("#"+id+" div").remove();
		$j("#"+id+" hr").remove();
		this.component.append("<div class='titleItemRenderer'>Ingredientes:</div>");
		for(var i=0;i<recipe.ingredients.length;i++){
			this.component.append("<div class='listIngredientsItemRenderer'>"+recipe.ingredients[i].toString()+"</div>");
		}
		if(recipe.hasOptionalIngredients){
			this.component.append("<hr/>");
			this.component.append("<div class='titleItemRenderer'>Ingredientes Opcionales:</div>");
		}
		for(var i=0;i<recipe.optionalIngredients.length;i++){
			this.component.append("<div class='listIngredientsItemRenderer'>"+recipe.optionalIngredients[i].toString()+"</div>");
		}
	}
}

RecipeElavoration = function(parent, id){
	this.recipe = null;
	parent.component.append("<div id='"+id+"' class='recipeElavorationPanel' />");
	this.component = $j("#"+id);
	this.update = function(recipe){
		this.recipe = recipe;
	}
}