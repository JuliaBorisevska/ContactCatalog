function createPageLinks(contextPath ,count, pageNumber){
	var i;
	var el= document.getElementById("pageart");
	if (count<2){
	    el.style.display = 'none';  
	}else{
		el.style.display = 'block';
		for (i=1; i<=count; i++){
			var ref = document.createElement("A");   
		    ref.href = contextPath+"/getContactList.do?page="+i;
		    if (i==pageNumber){
		    	ref.className = "active";
		    }
		    ref.onclick = function(){
		    	setParameter(this);  
		    };
		    var refText = document.createTextNode(i);
		    ref.appendChild(refText);
		    document.getElementById("buttons").appendChild(ref);
		}
	}
}

function getIdString(){
	var hiddenInput= document.getElementById("checkedRows");
	var str=hiddenInput.value;
	var arr = str.split(',');
	var elems = document.getElementsByName("row");
	var i;
	var j;
	var newStrId="";
	var flag=false;
	for (i=0; i<elems.length; i++){
		for (j=0; j<arr.length; j++){
			if (elems[i].value==arr[j]){
				flag=true;
				if(!elems[i].checked){
					arr[j]=0;
				}
			}
		}
		if (!flag && elems[i].checked){
			newStrId = newStrId + ","+ elems[i].value;
		}
	}
	
	for (i=0; i<arr.length; i++){
		if (arr[i]!=0){
			newStrId = newStrId + ","+ arr[i];
		}
	}
	hiddenInput.value = newStrId;
}

function setParameter(element){
	getIdString();
	var hiddenInput= document.getElementById("checkedRows");
	var link=element.href;
	element.href=link+"&"+"idString="+hiddenInput.value;
}

function setCheckBoxes(){
	var hiddenInput= document.getElementById("checkedRows");
	var str=hiddenInput.value;
	var arr = str.split(',');
	var elems = document.getElementsByName("row");
	var i;
	for (i=0; i<elems.length; i++){
		for (j=0; j<arr.length; j++){
			if (elems[i].value==arr[j]){
				elems[i].checked=true;
			}
		}
	}
}

