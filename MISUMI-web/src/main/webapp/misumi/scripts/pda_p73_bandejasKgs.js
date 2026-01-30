function initializeScreen_p73(){
	var fld_bandejas = document.getElementById("pda_p73_bandejasKgs_fld_bandejas");
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
	var fld_stock = document.getElementById("pda_p73_bandejasKgs_fld_kgs");
	if (fld_stock != null && typeof(fld_stock) != 'undefined') {
		fld_stock.onchange = function(e) {
			validacionKgs();
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
	resultadoValidacion = validacionKgsBandejas();
	events_p73_guardar();
	events_73_volver();
}

function validacionBandejas(){
	
	var idActual = "pda_p73_bandejasKgs_fld_bandejas";
	//Obtención de fila y columna actuales
	var objetoActual = document.getElementById("pda_p73_bandejasKgs_fld_bandejas");
	var campoActual = objetoActual.value;
	var campoActualPunto = objetoActual.value.replace(',','.');
	var campoStockTmp = "pda_p73_bandejasKgs_fld_bandejas_tmp";
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
		document.getElementById("pda_p73_bandejasKgs_btn_guardar").style.visibility = "hidden";
	}
	else
	{
		objetoActual.className = objetoActual.className.replace('inputResaltado','');
		document.getElementById("pda_p73_bandejasKgs_fld_bandejas_tmp").value= campoActualPunto;
	}
	
	resultadoValidacion = validacionKgsBandejas();

	//document.getElementById("pda_p30_fld_bandejas_pantalla").value = campoActualPunto;
	
	return resultadoValidacion;
}

function validacionKgs(){
	
	var idActual = "pda_p73_bandejasKgs_fld_kgs";
	var objetoActual = document.getElementById("pda_p73_bandejasKgs_fld_kgs");
	var campoActual = objetoActual.value;
	var campoActualPunto = objetoActual.value.replace(',','.');
	var campoStockTmp = "pda_p73_bandejasKgs_fld_kgs_tmp";
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
		document.getElementById("pda_p73_bandejasKgs_fld_kgs_tmp").value = campoActualPunto;
	}
	
	resultadoValidacion = validacionKgsBandejas();
	
	//document.getElementById("pda_p30_fld_stock_pantalla").value = campoActualPunto;
	
	return resultadoValidacion;
}

// Si un campo tiene valor, el otro no puede ser 0
function validacionKgsBandejas(){
	var bandejas = document.getElementById("pda_p73_bandejasKgs_fld_bandejas").value;
	var kilos = document.getElementById("pda_p73_bandejasKgs_fld_kgs").value;
	
	if (!isNaN(bandejas) && !isNaN(kilos)){
		if ((bandejas > 0 && kilos == 0) || (bandejas == 0 && kilos > 0)){
			// Ocultamos guardar si alguno de los campo es 0
			document.getElementById("pda_p73_bandejasKgs_btn_guardar").style.visibility = "hidden";	
			
			//Miramos si sacar mensaje del maximo sobrepasado
			var valorCantidadMaxima = document.getElementById("pda_p73_cantMaximaLin").value;
			if(valorCantidadMaxima != ""){
				if(parseFloat(kilos.replace(",",".")) > parseFloat(valorCantidadMaxima.replace(",","."))){
					document.getElementById("pda_p73_bandejasKgs_mensajeError2").style.display = "block";	
				}
			}else{
				document.getElementById("pda_p73_bandejasKgs_mensajeError2").style.display = "none";						
			}
			return "N";
		} else {
			var valorCantidadMaxima = document.getElementById("pda_p73_cantMaximaLin").value;
			if(valorCantidadMaxima != ""){
				if(parseFloat(kilos.replace(",",".")) > parseFloat(valorCantidadMaxima.replace(",","."))){
					document.getElementById("pda_p73_bandejasKgs_mensajeError2").style.display = "block";		
					document.getElementById("pda_p73_bandejasKgs_btn_guardar").style.visibility = "hidden";						
					return "N";
				}else{
					document.getElementById("pda_p73_bandejasKgs_mensajeError2").style.display = "none";
					document.getElementById("pda_p73_bandejasKgs_btn_guardar").style.visibility = "visible";						
					return "S";
				}
			}else{
				document.getElementById("pda_p73_bandejasKgs_btn_guardar").style.visibility = "visible";				
				return "S";
			}
		}
	} else {
		document.getElementById("pda_p73_bandejasKgs_btn_guardar").style.visibility = "hidden";		
		return "N";
	}
}

function controlNavegacion(e) {
	e = e || window.event;
	var idActual = (e.target || e.srcElement).id;
	var idFoco;
	var nombreColumna = idActual;
	var validacionNavegacion = 'S';
	
	var key = e.which || e.keyCode; //para soporte de todos los navegadores
 	if (key == 13){//Tecla enter, guardado
 		if(validacionKgsBandejas() == "S"){
			document.getElementById("p73_form").action = "pdaP73BandejasKgs.do?actionSave=yes";    
			document.getElementById("p73_form").submit();
 		}
    }
    //Flechas de cursores para navegación
    if (key == 37){//Tecla izquierda
		if(e.preventDefault) {
	        e.preventDefault();
	    } else {
	        e.returnValue = false;
	    }  
    	if(nombreColumna == "pda_p73_bandejasKgs_fld_kgs"){
        	idFoco = "pda_p73_bandejasKgs_fld_bandejas";
        	validacionNavegacion = validacionKgs();
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
    	if(nombreColumna == "pda_p73_bandejasKgs_fld_bandejas"){
        	idFoco = "pda_p73_bandejasKgs_fld_kgs";
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

function events_p73_guardar(){
	var btn_save =  document.getElementById('pda_p73_bandejasKgs_btn_guardar');

	if (btn_save != null && typeof(btn_save) != 'undefined') {
		btn_save.onclick = function () {
			document.getElementById("p73_form").action = "pdaP73BandejasKgs.do?actionSave=yes";    
			document.getElementById("p73_form").submit();
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	btn_save = null;
}

function events_73_volver(){
	var imagenVolver =  document.getElementById('pda_p10_volver');
	if (imagenVolver != null && typeof(imagenVolver) != 'undefined') {
		imagenVolver.onclick = function () {
			document.getElementById("p73_form").action = "pdaP73BandejasKgs.do?actionPrev=yes";    
			document.getElementById("p73_form").submit();
		};
	}
}



