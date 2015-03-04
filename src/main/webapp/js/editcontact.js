function fillPhone(country, operator, number, type, comment) {
   document.getElementById('countryCode').value=country;
   document.getElementById('operatorCode').value=operator;
   document.getElementById('number').value=number;
   contactForm.phoneComment.value=comment;
   for (var i = 0; i < contactForm.phoneType.length; i++) {
	   if(contactForm.phoneType[i].value==type) {
		   contactForm.phoneType[i].checked = true;
	   } 
   }
}


function fillContact(txtCheckObj, txtSelectObj) {
	for (var i = 0; i < contactForm.sex.length; i++) {
		   if(contactForm.sex[i].value==txtCheckObj) {
			   contactForm.sex[i].checked = true;
		   } 
	   }
	   for(var j = 0; j < contactForm.status.length; j++) {
		     if(contactForm.status.options[j].value == txtSelectObj){
		    	 contactForm.status.selectedIndex = j;
		     }
		  }
}
