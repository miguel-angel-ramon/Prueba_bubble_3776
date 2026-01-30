var controlReturn = false;
var limite = 9999;
function initializeScreen_p30(){
	var fld_bandejas = document.getElementById("pda_p30_fld_bandejas");
	if (fld_bandejas != null && typeof(fld_bandejas) != 'undefined') {
		fld_bandejas.onchange = function(e) {
			//Solo queremos este evento en los centros caprabo
			var esCentroCaprabo = document.getElementById("pda_p30_esCaprabo").value;
			if(esCentroCaprabo == "true"){
				//Si no se ha pulsado return.
				if(!controlReturn){
					controlExcedido(e);
				}else{
					//Desactivamos el flag del return para que si hay un change sin return funcione las siguientes veces.
					controlReturn = false;
				}
			}
			
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
	var fld_stock = document.getElementById("pda_p30_fld_stock");
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
	
	var btnSave = document.getElementById("pda_p30_btn_guardar");		
	btnSave.onclick = function(e) {
		var esCentroCaprabo = document.getElementById("pda_p30_esCaprabo").value;
		if(esCentroCaprabo == "true"){
			//Obtenemos los elementos que puedan tener borde azul
			var inputAzul =  getElementsByClassName(document.body,'inputAzul');
			if(inputAzul.length == 0){
				document.getElementById("p30_form").submit();
			}else{
				var txtLimites = document.getElementById("pda_p30_msgExcedido").value;
				var respuesta = confirm(txtLimites);
				if (respuesta == true) {
					document.getElementById("p30_form").submit();
				} 
			}
		}else{
			document.getElementById("p30_form").submit();
		}
	}
	
	p30CheckDisabledStock();
	validacionStockBandejas();
	events_30_volver();
}

/**
 * Valida si el numero de bandejas es bueno.
 * @param bolRestaurable true si se puede restaurar el valor anterior
 * @returns "N" o "S"
 */
function validacionBandejas(restaurable){
	
	var idActual = "pda_p30_fld_bandejas";
	//Obtención de fila y columna actuales
	var objetoActual = document.getElementById("pda_p30_fld_bandejas");
	var campoActual = objetoActual.value;
	var campoActualPunto = objetoActual.value.replace(',','.');
	var objetoTmp = document.getElementById("pda_p30_fld_bandejas_tmp");
	var objetoLblMensaje = document.getElementById("pda_p30_lbl_mensaje");
	var objetoStockTeo = document.getElementById("pda_p30_fld_stock");
	var objetoPesoVariable = document.getElementById("pda_p30_fld_peso_variable");
	var pesoVariable = objetoPesoVariable && objetoPesoVariable.value === 'S';
	var resultadoValidacion = 'S';
	
	//Formateamos el valor introducido por el usuario
	if (campoActual == '')
	{
		campoActualPunto = "0.000";
	}
	
	//Sólo si ha cambiado el valor del campo.
	if ((isNaN(parseFloat(campoActualPunto)) || campoActual.split(",").length > 2) &&
			(parseFloat(objetoTmp.value) != parseFloat(campoActualPunto)))
	{
		//Pintamos de rojo el campo.
		objetoActual.className = objetoActual.className + " inputResaltado";
		resultadoValidacion = 'N';
	}
	else if(pesoVariable &&
			parseFloat(campoActualPunto) != 0 && // permitido
			parseFloat(campoActualPunto) < 6 && // limite de edicion manual
			parseFloat(objetoTmp.value) != parseFloat(campoActualPunto))
	{
		// Error por superar el limite de edicion manual
		// machacamos el valor que ha puesto el usuario con el valor anterior y le avisamos
		if(restaurable)
		{
			objetoActual.value = parseFloat(objetoTmp.value).toFixed(0);
			campoActualPunto = objetoActual.value.replace(',','.');
		}
		if(objetoLblMensaje){
			objetoLblMensaje.className += ' pda_p30_error';
		}
		resultadoValidacion = 'N';
	}
	
	if(resultadoValidacion === 'S')
	{
		objetoActual.className = objetoActual.className.replace('inputResaltado','');
		if(objetoLblMensaje){
			objetoLblMensaje.className = objetoLblMensaje.className.replace(' pda_p30_error','');
		}
		
		var kgsPunto = document.getElementById("pda_p30_fld_kgs").value.replace(',','.');
		var kgsFloat = parseFloat(kgsPunto);
		
		if(parseFloat(campoActualPunto) == 0) {
			objetoStockTeo.value= 0;
		}else if(kgsFloat == 1 || !pesoVariable || parseFloat(campoActualPunto) > 5){
			objetoStockTeo.value= (parseFloat(campoActualPunto)*parseFloat(document.getElementById("pda_p30_fld_kgs").value.replace(',','.'))).toFixed(2);
		}
		
		objetoTmp.value= campoActualPunto;
		document.getElementById("pda_p30_fld_stock_tmp").value = document.getElementById("pda_p30_fld_stock").value.replace(',','.');
		document.getElementById("pda_p30_fld_stock_pantalla").value = document.getElementById("pda_p30_fld_stock").value.replace(',','.');
	}
	p30CheckDisabledStock();
	document.getElementById("pda_p30_fld_bandejas_pantalla").value = campoActualPunto;
	
	validacionStockBandejas();
	
	return resultadoValidacion;
}

function validacionStock(){
	
	var idActual = "pda_p30_fld_stock";
	var objetoActual = document.getElementById("pda_p30_fld_stock");
	var campoActual = objetoActual.value;
	var campoActualPunto = objetoActual.value.replace(',','.');
	var campoStockTmp = "pda_p30_fld_stock_tmp";
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
		document.getElementById("pda_p30_fld_stock_tmp").value = campoActualPunto;
	}
	
	document.getElementById("pda_p30_fld_stock_pantalla").value = campoActualPunto;
	
	validacionStockBandejas();
	
	return resultadoValidacion;
}


//Si un campo tiene valor, el otro no puede ser 0
function validacionStockBandejas(){
	var inputBandejas = document.getElementById("pda_p30_fld_bandejas");
	var bandejas = document.getElementById("pda_p30_fld_bandejas").value;
	var kilos = document.getElementById("pda_p30_fld_stock").value.replace(',','.');
	
	if (!isNaN(bandejas) && !isNaN(kilos)){
		//Si existe input azul hay error excedido en caprabo.
		//if ((bandejas > 0 && kilos == 0) || (bandejas == 0 && kilos > 0) || hasClass(inputBandejas, "inputAzul")){
		if ((bandejas > 0 && kilos == 0) || (bandejas == 0 && kilos > 0)){
			// Ocultamos guardar si alguno de los campo es 0
			document.getElementById("pda_p30_btn_guardar").style.visibility = "hidden";
		} else {
			document.getElementById("pda_p30_btn_guardar").style.visibility = "visible";
		}
	} else {
		document.getElementById("pda_p30_btn_guardar").style.visibility = "hidden";
	}
}


function controlExcedido(e){
	//Aqui miramos si 
	e = e || window.event;	
	var idActual = (e.target || e.srcElement).id;
	
	//Obtenemos el input actual
	var inputActual = document.getElementById(idActual);
	
	//Mostramos aviso.
	var aviso = document.getElementById("pda_p30_mensajeAviso");
	var error = document.getElementById("pda_p30_mensajeError");
	//Miramos si tiene error de validacionStock
	if(!hasClass(inputActual, "inputResaltado")){
		//Si no tiene error, de validacion bandejas miramos si el cambio
		//no es menos que 1/limite o mayor que *limite del campo temporal.
		var campoActualPunto = parseFloat(document.getElementById(idActual).value.replace(',','.'));
		var campoBandejasTmp = parseFloat(document.getElementById(idActual + "_tmpOri").value.replace(',','.'));

		//Si las bandejas son menor de 0, tomamos el límite por debajo como 0.
		var campoBandejasTmpEntreLim = (campoBandejasTmp < 0) ? 0 : Math.floor(campoBandejasTmp / limite);
		
		//Si las bandejas es igual a 0 o menor, tomamos que el límite superior es limite.
		var campoBandejasTmpPorLim = (campoBandejasTmp == 0 || campoBandejasTmp < 0) ? (limite) : (campoBandejasTmp * limite);
		
		if(campoActualPunto < campoBandejasTmpEntreLim || campoActualPunto > campoBandejasTmpPorLim) {
			//Si el inputActual no tiene la clase inputAzul, se pone. Si no se pone este control puede poner varias veces la clase.
			if(!hasClass(inputActual,"inputAzul")){
				//Pintamos de azul el campo.
				inputActual.className = inputActual.className + " inputAzul";
			}
									
			aviso.style.display = "block";
			error.style.display = "none";
			
			//Devolvemos true. Este true indica que se ha excedido el limite.
			return true;
		}else{
			//Quitamos el borde azul
			inputActual.className = inputActual.className.replace('inputAzul','');
			
			//Obtenemos los elementos que puedan tenr borde azul
			var inputAzul =  getElementsByClassName(document.body,'inputAzul');
			
			//Ocultamos el mensaje de alerta.
			aviso.style.display = "none";	
			
			//Devolvemos false. Este false indica que no se ha excedido el limite
			return false;
		}
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
 		if(nombreColumna == "pda_p30_fld_bandejas"){
        	validacionNavegacion = validacionBandejas(true);
    	}
 		//Solo queremos este evento en los centros caprabo
		var esCentroCaprabo = document.getElementById("pda_p30_esCaprabo").value;
		if(esCentroCaprabo == "true"){
			if(controlExcedido(e)){
				validacionNavegacion = "N";
				
				//Creamos este control para que después de pulsar return no se active el evento change de nuevo.
				controlReturn = true;
			}
		}
 		if(validacionNavegacion !== 'S'){
 			if(e.preventDefault) {
 		        e.preventDefault();
 		    } else {
 		        e.returnValue = false;
 		    }  
 		}
    }
    //Flechas de cursores para navegación
    if (key == 37){//Tecla izquierda
		if(e.preventDefault) {
	        e.preventDefault();
	    } else {
	        e.returnValue = false;
	    }  
    	if(nombreColumna == "pda_p30_fld_stock"){
        	idFoco = "pda_p30_fld_bandejas";
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
    	if(nombreColumna == "pda_p30_fld_bandejas"){
        	idFoco = "pda_p30_fld_stock";
        	validacionNavegacion = validacionBandejas(true);
        	if (validacionNavegacion=='S'){
        		document.getElementById(idFoco).focus();
        		document.getElementById(idFoco).select();
        	}
    	}
    }
    if (key == 9){//Tecla tab
 		if(nombreColumna == "pda_p30_fld_bandejas"){
 			// validamos para quitar el feedback en ese caso
        	validacionBandejas(true);
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

/** Habilita o bloquea, el campo de stock si procede */
function p30CheckDisabledStock(){
	var objetoPesoVariable = document.getElementById("pda_p30_fld_peso_variable");
	var pesoVariable = objetoPesoVariable && objetoPesoVariable.value === 'S';
	if(pesoVariable){
		var objetoBandejas = document.getElementById("pda_p30_fld_bandejas");
		var objetoStock = document.getElementById("pda_p30_fld_stock");
		var habilitarStock = Number(String(objetoBandejas.value).replace(',','.')) >= 6;
		objetoStock.readOnly = !habilitarStock;
		if(habilitarStock){
			objetoStock.className = objetoStock.className.replace(' pda_p30_readonly','');
		}else{
			objetoStock.className += ' pda_p30_readonly';
		}
	}
}

function events_30_volver(){
	var imagenVolver =  document.getElementById('pda_p10_volver');
	if (imagenVolver != null && typeof(imagenVolver) != 'undefined') {
		imagenVolver.onclick = function () {
			document.forms[0].action = "pdaP30CorrStockPCS.do?actionPrev=yes";    
			document.forms[0].submit();
		};
	}
}