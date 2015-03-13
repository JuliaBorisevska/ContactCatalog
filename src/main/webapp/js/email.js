function getLetter(select) {
	var i;
	for (i=0;i<select.options.length;i++){
		if (i==select.selectedIndex){
			document.getElementById(select.options[i].value).style.display = "block"; 
		}else{
			document.getElementById(select.options[i].value).style.display = "none"; 
		}
	}
	
  
}