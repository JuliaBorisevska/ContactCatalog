function fillPhone(obj, id, country, operator, number, type, comment) {
	document.getElementById('phoneId').value=id;
	document.getElementById('edit').value=obj.parentNode.parentNode.rowIndex;
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

function editPhone() {
	var i = document.getElementById('edit').value;
	//alert(i);
	var htmlTable = document.getElementById('phoneTable');
	var tableRows = htmlTable.rows;  
	for (var j = 0; j < contactForm.phoneType.length; j++) {
		if(contactForm.phoneType[j].checked) {
			//alert(i);
			document.getElementById('phoneTable').rows[i].cells[2].innerText = contactForm.phoneType[j].value;
		} 
	}
    tableRows[i].cells[1].innerText = "";
    var ref = document.createElement("A");   
    ref.href = "#";
    ref.className = "name";
    ref.onclick = function(){
    	popup('popUpDivPhone', 450, 600);
    	fillPhone(this, document.getElementById('phoneId').value, document.getElementById('countryCode').value,document.getElementById('operatorCode').value, document.getElementById('number').value, tableRows[i].cells[2].firstChild.data, contactForm.phoneComment.value);  
    };
    //ref.onclick = fillPhone(document.getElementById('phoneTable').rows[i], document.getElementById('phoneId').value, document.getElementById('countryCode').value,document.getElementById('operatorCode').value, document.getElementById('number').value, tableRows[i].cells[2].firstChild.data, contactForm.phoneComment.value);                          
    refText = document.createTextNode("+"+document.getElementById('countryCode').value+" ("+document.getElementById('operatorCode').value+") "+document.getElementById('number').value);
    ref.appendChild(refText);         
    tableRows[i].cells[1].appendChild(ref);
    tableRows[i].cells[3].innerText = contactForm.phoneComment.value;
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


