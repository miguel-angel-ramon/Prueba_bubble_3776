function initializeScreen_p44(){
	var fld_bandejas = document.getElementById("pda_p44_fld_bandejas");
	if (fld_bandejas != null && typeof(fld_bandejas) != 'undefined') {
		fld_bandejas.onchange = function(e) {
			validacionBandejas();
		}
		fld_bandejas.onclick = function(e) {
			e = e || window.event;
			this.select();
			if(e.stopPropagation) {
			    e.stopPropagation();
			} else {
				e.cancelBubble = true;
			}
		}
		fld_bandejas.onkeydown = function(e) {
			controlNavegacion(e);
		}
	}
	var fld_stock = document.getElementById("pda_p44_fld_stock");
	if (fld_stock != null && typeof(fld_stock) != 'undefined') {
		fld_stock.onchange = function(e) {
			validacionStock();
		}
		fld_stock.onclick = function(e) {
			e = e || window.event;
			this.select();
			if(e.stopPropagation) {
			    e.stopPropagation();
			} else {
				e.cancelBubble = true;
			}
		}
		fld_stock.onkeydown = function(e) {
			controlNavegacion(e);
		}
	}
	events_44_volver();
}

function validacionBandejas(){
	
	var idActual = "pda_p44_fld_bandejas";
	//Obtención de fila y columna actuales
	var objetoActual = document.getElementById("pda_p44_fld_bandejas");
	var campoActual = objetoActual.value;
	var campoActualPunto = objetoActual.value.replace(',','.');
	var campoStockTmp = "pda_p44_fld_bandejas_tmp";
	var resultadoValidacion = 'S';
	
	//Formateamos el valor introducido por el usuario
	if (campoActual == '')
	{
		campoActualPunto = "0.000";
	}
	
	//Sólo si ha cambiado el valor del campo.
	if ((isNaN(parseFloat(campoActualPunto)) || campoActual.split(",").length > 2) &&
			(parseFloat(document.getElementById(campoStockTmp).value) != parseFloat(campoActualPunto)))
	{
		//Pintamos de rojo el campo.
		objetoActual.className = objetoActual.className + " inputResaltado";
	}
	else
	{
		objetoActual.className = objetoActual.className.replace('inputResaltado','');
		document.getElementById("pda_p44_fld_stock").value= (parseFloat(campoActualPunto)*parseFloat(document.getElementById("pda_p44_fld_kgs").value.replace(',','.'))).toFixed(2).replace('.',',');
		document.getElementById("pda_p44_fld_bandejas_tmp").value= campoActualPunto;
		document.getElementById("pda_p44_fld_stock_tmp").value = document.getElementById("pda_p44_fld_stock").value.replace(',','.');
		document.getElementById("pda_p44_fld_stock_pantalla").value = document.getElementById("pda_p44_fld_stock").value.replace(',','.');
	}

	document.getElementById("pda_p44_fld_bandejas_pantalla").value = campoActualPunto;
	
	return resultadoValidacion;
}

function validacionStock(){
	
	var idActual = "pda_p44_fld_stock";
	var objetoActual = document.getElementById("pda_p44_fld_stock");
	var campoActual = objetoActual.value;
	var campoActualPunto = objetoActual.value.replace(',','.');
	var campoStockTmp = "pda_p44_fld_stock_tmp";
	var resultadoValidacion = 'S';
	
	//Formateamos el valor introducido por el usuario
	if (campoActual == '')
	{
		campoActualPunto = "0.000";
	}
	
	//Sólo si ha cambiado el valor del campo.
	if ((isNaN(parseFloat(campoActualPunto)) || campoActual.split(",").length > 2) &&
			(parseFloat(document.getElementById(campoStockTmp).value) != parseFloat(campoActualPunto)))
	{
		//Pintamos de rojo el campo.
		objetoActual.className = objetoActual.className + " inputResaltado";
	}
	else
	{
		objetoActual.className = objetoActual.className.replace('inputResaltado','');
		document.getElementById("pda_p44_fld_stock_tmp").value = campoActualPunto;
	}
	
	document.getElementById("pda_p44_fld_stock_pantalla").value = campoActualPunto;
	
	return resultadoValidacion;
}

function controlNavegacion(e) {
	e = e || window.event;
	var idActual = (e.target || e.srcElement).id;
	var idFoco;
	var nombreColumna = idActual;
	var validacionNavegacion = 'S';
	
	var key = e.which || e.keyCode; //para soporte de todos los navegadores
 	if (key == 13){//Tecla enter, guardado
 		if(e.preventDefault) {
	        e.preventDefault();
	    } else {
	        e.returnValue = false;
	    }
 		if(nombreColumna == "pda_p44_fld_bandejas"){
 			validacionBandejas();
 		}else{//pda_p44_fld_stock
 			validacionStock();
 		}
 		var bnt_save = document.getElementById("pda_p44_btn_guardar");
 		if (bnt_save != null && typeof(bnt_save) != 'undefined') {
			bnt_save.click();
		}
    }
    //Flechas de cursores para navegación
    if (key == 37){//Tecla izquierda
		if(e.preventDefault) {
	        e.preventDefault();
	    } else {
	        e.returnValue = false;
	    }  
    	if(nombreColumna == "pda_p44_fld_stock"){
        	idFoco = "pda_p44_fld_bandejas";
        	validacionNavegacion = validacionStock();
        	if (validacionNavegacion=='S'){
        		document.getElementById(idFoco).focus();
        		document.getElementById(idFoco).select();
        	}
    	}
    }
    if (key == 39){//Tecla derecha
		if(e.preventDefault) {
	        e.preventDefault();
	    } else {
	        e.returnValue = false;
	    }  
    	if(nombreColumna == "pda_p44_fld_bandejas"){
        	idFoco = "pda_p44_fld_stock";
        	validacionNavegacion = validacionBandejas();
        	if (validacionNavegacion=='S'){
        		document.getElementById(idFoco).focus();
        		document.getElementById(idFoco).select();
        	}
    	}
    }
}

function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : event.keyCode
   if (charCode > 31 && (charCode < 48 || charCode > 57))
      return false;

   return true;
}

function events_44_volver(){
	var imagenVolver =  document.getElementById('pda_p10_volver');
	if (imagenVolver != null && typeof(imagenVolver) != 'undefined') {
		imagenVolver.onclick = function () {
			document.forms[0].action = "pdaP44InvLibCorreccionStockPCS.do?actionPrev=yes";    
			document.forms[0].submit();

		};
	}
}
