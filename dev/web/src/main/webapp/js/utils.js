//Utils Functions

//Te elimina los espacios en un string
function removeSpaces(value){
	var replaced = value.replace(" ","");
	while(replaced.indexOf(" ")>=0)
		replaced = replaced.replace(" ","");
	return replaced;
}