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
	this.recipeListPanel = new RecipeListPanel(this,"recipeList");
	this.recipeViewPanel = new RecipeViewPanel(this,"recipeView");
}

RecipeListPanel = function(parent, id){
	parent.component.append("<div id='"+id+"' class='leftPanel'/>");
	this.component = $j("#"+id);
}

RecipeViewPanel = function(parent, id){
	parent.component.append("<div id='"+id+"' class='rightPanel'/>");
	this.component = $j("#"+id);
}