//Obtiene los elementos por atributo
function getElementsByAttribute(attribute, value, context) {
	var nodeList = (context || document).getElementsByTagName('*');
	var nodeArray = [];
	var iterator = 0;
	var node = null;

	while (node = nodeList[iterator++]) {
		if (node.getAttribute(attribute) === String(value)) {
			nodeArray.push(node);
		}	    	
	}
	return nodeArray;
}

//Función que obtiene los todos los elementos del DOM que contienen esa clase.
function getElementsByClassName(node, classname) {
	var a = [];
	var re = new RegExp('(^| )'+classname+'( |$)');
	var els = node.getElementsByTagName("*");
	for(var i=0,j=els.length; i<j; i++)
		if(re.test(els[i].className))a.push(els[i]);
	return a;
}

//Función ajax genérica
function postData(url, method, obj, async, formatoEnvio, funcionOk, funcionErr) {
	var http;

	if (window.XMLHttpRequest) {
		// code for IE7+, Firefox, Chrome, Opera, Safari
		http = new XMLHttpRequest();
	}else {
		// code for IE6, IE5
		http = new ActiveXObject("Microsoft.XMLHTTP");
	}
	http.open(method, url,async);

	//Send the proper header information along with the request
	http.setRequestHeader("Content-type", formatoEnvio);
	//http.setRequestHeader("Content-length", obj.length);
	//http.setRequestHeader("Connection", "close");
	http.onreadystatechange = function() {
		//Si readyDtate es 4, significa que la llamada ya se ha hecho.
		if(http.readyState == 4){

			//Si no ha ocurrido error
			if(http.status == 200){
				var jsonObj = JSON.parse(http.responseText);
				funcionOk(jsonObj);    		   
			}else{
				funcionErr();    		   
			}
		}
	}
	http.send(obj);
}

function hasClass(element, cls) {
    return (' ' + element.className + ' ').indexOf(' ' + cls + ' ') > -1;
}

