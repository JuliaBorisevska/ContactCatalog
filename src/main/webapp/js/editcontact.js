function fillPhone(obj) {
	var index = obj.parentNode.parentNode.rowIndex;
	document.getElementById('editRow').value=index;
	var elems = document.getElementById('phoneTable').rows[index].cells[1].getElementsByTagName('INPUT');
	var str=elems[0].value;
	//alert(elems.length);
	//alert(str);
	var arr = str.split(':');
	document.getElementById('phoneId').value=arr[0];
	document.getElementById('countryCode').value=arr[1];
	document.getElementById('operatorCode').value=arr[2];
	document.getElementById('number').value=arr[3];
	contactForm.phoneComment.value=arr[5];
	for (var i = 0; i < contactForm.phoneType.length; i++) {
		if(contactForm.phoneType[i].value==arr[4]) {
			contactForm.phoneType[i].checked = true;
		} 
	}
}

function fillAttach(obj) {
	var index = obj.parentNode.parentNode.rowIndex;
	document.getElementById('editAttach').value=index;
	var elems = document.getElementById('attachTable').rows[index].cells[1].getElementsByTagName('INPUT');
	var str=elems[0].value;
	var arr = str.split('#');
	document.getElementById('attachId').value=arr[0];
	document.getElementById('attachName').value=arr[1];
	document.getElementById('dateField').value=arr[2];
	contactForm.attachComment.value=arr[3];
	document.getElementById("attach").style.display = 'none';
}

function clearAttach(){
	document.getElementById('editAttach').value=0;
	document.getElementById('attachId').value=0;
	document.getElementById('attachName').value="";
	contactForm.attachComment.value="";
	document.getElementById("attach").style.display = 'block';
}

function convertDate(){
	var i;
	var table=document.getElementById('attachTable');
	var rowCount=table.rows.length;
	for(i=1;i<rowCount;i++){
		var row=table.rows[i];
		var elems = document.getElementById('attachTable').rows[i].cells[1].getElementsByTagName('INPUT');
		var str=elems[0].value;
		var arr = str.split('#');
		var dateTime = arr[2].split('-');
		var date = new Date(dateTime[0], dateTime[1]-1, dateTime[2], dateTime[3], dateTime[4], dateTime[5]);
		date.setHours(+date.getHours()-date.getTimezoneOffset()/60);
		var now = date.getFullYear()+"-"+('0' + (date.getMonth()+1)).slice(-2)+"-"+('0' + date.getDate()).slice(-2)+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
		row.cells[2].innerText = now;
		
	}
	
	
}


function editAttach(){
	var htmlTable = document.getElementById('attachTable');
	var tableRows = htmlTable.rows; 
    var id = document.getElementById('attachId').value;
    var utcDate;
	var date = new Date();
    var now; 
	var i;
	if (document.getElementById('editAttach').value==0){
		now = date.getFullYear()+"-"+('0' + (date.getMonth() + 1)).slice(-2)+"-"+('0' + date.getDate()).slice(-2)+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
		utcDate = date.getUTCFullYear()+"-"+('0' + (date.getUTCMonth() + 1)).slice(-2)+"-"+('0' + date.getUTCDate()).slice(-2)+"-"+date.getUTCHours()+"-"+date.getUTCMinutes()+"-"+date.getUTCSeconds();
		alert(utcDate);
		i =tableRows.length;
		var row=htmlTable.insertRow(i);
		var colCount=tableRows[0].cells.length;
		for(var k=0;k<colCount;k++){
			var newcell=row.insertCell(k);
		}
		var box = document.createElement("INPUT");
		box.type="checkbox";
		box.checked=false;
		tableRows[i].cells[0].appendChild(box);
	    var x = document.createElement("INPUT");
	    x.type="hidden";
	    x.name="attachment";
	    x.value=id+"#"+document.getElementById('attachName').value+"#"+utcDate+"#"+contactForm.attachComment.value;
	    tableRows[i].cells[1].appendChild(x);
	    var ref = document.createElement("A");   
	    ref.href = "#";
	    ref.className = "name";
	    ref.onclick = function(){
	    	popup('popUpDivAttach', 300, 600);
	    	fillAttach(this);  
	    };         
	    var refText = document.createTextNode(document.getElementById('attachName').value);
	    ref.appendChild(refText);         
	    tableRows[i].cells[1].appendChild(ref);
	    var itm = document.getElementById("attach");
	    var cln = itm.cloneNode(true);
	    itm.id = i;
	    itm.style.display = 'none';
	    itm.name=i;
	    tableRows[i].cells[1].appendChild(itm);
	    var attachDiv = document.getElementById("attachFileDiv");
	    attachDiv.appendChild(cln);
	    tableRows[i].cells[2].innerText = now;
	}else{
		i = document.getElementById('editAttach').value;
		utcDate = document.getElementById('dateField').value;
		var elems = document.getElementById('attachTable').rows[i].cells[1].getElementsByTagName('INPUT');
		elems[0].value=id+"#"+document.getElementById('attachName').value+"#"+utcDate+"#"+contactForm.attachComment.value;
		var aElems = document.getElementById('attachTable').rows[i].cells[1].getElementsByTagName('A');
		aElems[0].innerText = document.getElementById('attachName').value;
	}
    tableRows[i].cells[3].innerText = contactForm.attachComment.value;
}


function clearPhoto(){
	var itm = document.getElementById("photo");
    var cln = itm.cloneNode(true);
    var photoDiv = document.getElementById("imageFile");
    photoDiv.removeChild(itm);
    photoDiv.appendChild(cln);
}

function editPhone() {
	var htmlTable = document.getElementById('phoneTable');
	var tableRows = htmlTable.rows; 
	var i;
	if (document.getElementById('editRow').value==0){
		i =tableRows.length;
		var row=htmlTable.insertRow(i);
		var colCount=tableRows[0].cells.length;
		for(var k=0;k<colCount;k++){
			var newcell=row.insertCell(k);
		}
		var box = document.createElement("INPUT");
		box.type="checkbox";
		box.checked=false;
		tableRows[i].cells[0].appendChild(box);
		//tableRows[i].cells[0].innerHTML=tableRows[0].cells[0].innerHTML;
		//tableRows[i].cells[0].childNodes[0].checked=false;
	}else{
		i = document.getElementById('editRow').value;
	}
	for (var j = 0; j < contactForm.phoneType.length; j++) {
		if(contactForm.phoneType[j].checked) {
			var type = contactForm.phoneType[j].value;
			document.getElementById('phoneTable').rows[i].cells[2].innerText = contactForm.phoneType[j].value;
		} 
	}
    tableRows[i].cells[1].innerText = "";               
    var id = document.getElementById('phoneId').value;
    var x = document.createElement("INPUT");
    x.type="hidden";
    x.name="phone";    
    x.value=id+":"+document.getElementById('countryCode').value+":"+document.getElementById('operatorCode').value+":"+document.getElementById('number').value+":"+type+":"+contactForm.phoneComment.value;
    tableRows[i].cells[1].appendChild(x);
    var ref = document.createElement("A");   
    ref.href = "#";
    ref.className = "name";
    ref.onclick = function(){
    	popup('popUpDivPhone', 450, 600);
    	fillPhone(this);  
    };         
    var refText = document.createTextNode("+"+document.getElementById('countryCode').value+" ("+document.getElementById('operatorCode').value+") "+document.getElementById('number').value);
    ref.appendChild(refText);         
    tableRows[i].cells[1].appendChild(ref);
    tableRows[i].cells[3].innerText = contactForm.phoneComment.value;
}

function clearFields(){
	document.getElementById('editRow').value=0;
	document.getElementById('phoneId').value=0;
	document.getElementById('countryCode').value="";
	document.getElementById('operatorCode').value="";
	document.getElementById('number').value="";
	contactForm.phoneComment.value="";
}


function deleteRow(tableID){
	try{
		var table=document.getElementById(tableID);
		var rowCount=table.rows.length;
		for(var i=0;i<rowCount;i++){
			var row=table.rows[i];
			var chkbox=row.cells[0].childNodes[0];
			if(null!=chkbox&&true==chkbox.checked){
				if(rowCount<=1){
					alert("Cannot delete all the rows.");
					break;
				}
				table.deleteRow(i);
				rowCount--;
				i--;
			}
		}
	}catch(e){
		alert(e);
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


