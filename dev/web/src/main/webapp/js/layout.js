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
/*
function createRecipeList(parent){
	parent.append("<div id='recipeList' class='leftPanel'/>");
}

function createRecipeView(parent){
	parent.append("<div id='recipeView' class='rightPanel'/>");
}
*/
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
	this.recipeListPanel = new RecipeListPanel(this,"recipeList", "¡Busca recetas por su nombre!");
	this.recipeViewPanel = new RecipeViewPanel(this,"recipeView");
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
			if(item.recipe.toLowerCase().indexOf(value.toLowerCase())<0){
				item.hide();
			}
			else{
				item.show();
			}
		}
	}
	
	if(!recipeModel)
		ee.addListener(RecipeService.ARRIVE_INGREDIENTS_EVENT,this.loadRecipes);
	else
		this.loadRecipes(recipeModel.recipes);
}

RecipeListItemRenderer=function(parent,recipe){
	this.recipe = recipe;
	this.visible = true;
	parent.component.append("<div id='renderer"+recipe+"' class='listItemRenderer'>"+this.recipe+"</div>");
	this.component = $j("#renderer"+recipe);
	this.hide = function(){
		this.visible = false;
		this.component.hide();
	}
	
	this.show = function(){
		this.visible = true;
		this.component.show();
	}
}

RecipeViewPanel = function(parent, id){
	parent.component.append("<div id='"+id+"' class='rightPanel'/>");
	this.component = $j("#"+id);
}