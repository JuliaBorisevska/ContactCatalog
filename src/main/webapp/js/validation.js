	function validate(divId) {
		var div = document.getElementById(divId);
		var elems = div.getElementsByTagName('INPUT');
		for(var i=0; i<elems.length; i++) {
			var e = elems[i]; 
			var type = e.type.toLowerCase();
			if (type != "text") continue;
			var pattern = e.getAttribute("regex");
			var required = e.getAttribute("necessary") != null;
			if (required && !pattern) {
				pattern = "\\S";
				e.setAttribute("regex", pattern);
			}
			if (pattern) {
				e.onchange = validateOnChange;
				e.className = "without";
			}		
		}
		elems = div.getElementsByTagName('TEXTAREA');
		for(var i=0; i<elems.length; i++) {
			var e = elems[i]; 
			var pattern = e.getAttribute("regex");
			var required = e.getAttribute("necessary") != null;
			if (required && !pattern) {
				pattern = "\\S";
				e.setAttribute("regex", pattern);
			}
			if (pattern) {
				e.onchange = validateOnChange;
				e.className = "without";
			}		
		}
	}

		function validateOnChange() {
			var textfield = this; // Текстовое поле
			var pattern = textfield.getAttribute("regex"); // Шаблон
			var required = textfield.getAttribute("necessary") != null;
			var re = new RegExp(pattern);
			var value = this.value; 
			if (re.test(value) || (!required&&textfield.value=="")){
				textfield.className = "valid";
			}
			else {
				textfield.className = "invalid";
			}
		}
		
		function validateOnFormSubmit(divId) {
			var invalid = false;
			var radio = false;
			var radioElem;
			var radioCount = 0;
			var div = document.getElementById(divId);
			var elems = div.getElementsByTagName('INPUT');
			for(var i=0; i<elems.length; i++) { 
				var e = elems[i];
				var type = e.type.toLowerCase();
				switch (type) {
					case "text" :
						if (e.onchange == validateOnChange) {
							e.onchange( );
							if (e.className == "invalid") {invalid = true;}
						}
						break;
					case "radio" :
						radioElem =e;
						radioCount++;
						if (e.checked){
							radio = true;
						}
						break;
					case "file" :
						if (e.value == "" &&  e.style.display == 'block') {
							invalid = true;
							getHint(e);
							}
						break;
					default :
						break;
				 }
			}
			elems = div.getElementsByTagName('TEXTAREA');
			for(var i=0; i<elems.length; i++) {
				var e = elems[i]; 
				if (e.className == "invalid") {invalid = true;}	
			}
			if (!radio&&radioCount>0){
				invalid = true;
				getHint(radioElem.parentNode.parentNode);
			}
			return !invalid;
		}
		
		function validateOnPhoneButtonClick(divId) {
			if (validateOnFormSubmit(divId)) {
				popup(divId, 450, 600); 
				editPhone();
			}
		}
		
		function validateOnAttachmentButtonClick(divId) {
			if (validateOnFormSubmit(divId)) {
				popup(divId, 300, 600); 
				editAttach();
			}
		}
		
		function getHint(obj){
			var hint = obj.parentNode.getElementsByTagName("SPAN")[0];
			hint.className = "classic";
		}
		
		function hideHint(obj){
			var hint = obj.parentNode.getElementsByTagName("SPAN")[0];
			hint.className = "hidden";
		}

		
		
		
	