function initializeScreen_p45(){
	var contador = 0;
	var objeto = document.getElementById("pda_p45_fld_stock_" + contador);
	while (objeto != null && typeof(objeto) != 'undefined') {
		objeto.onclick = function(e) {
			e = e || window.event;
			this.select();
			if(e.stopPropagation) {
			    e.stopPropagation();
			} else {
				e.cancelBubble = true;
			}
		}
		objeto.onkeydown = function(e) {
			controlNavegacion(e)
		}
	    contador++;
		objeto = document.getElementById("pda_p45_fld_stock_" + contador);
	}
	
	document.getElementById("pda_p45_fld_stock_0").select();
	
	document.onkeydown = function(e) {
		e = e || window.event;
		var key = e.which || e.keyCode; //para soporte de todos los navegadores
		if(key == 13) {
			if(e.preventDefault) {
		        e.preventDefault();
		    } else {
		        e.returnValue = false;
		    }  
			
			var bnt_save = document.getElementById("pda_p45_btn_save");
			if (bnt_save != null && typeof(bnt_save) != 'undefined' && bnt_save.className.indexOf('operacionDefecto') != -1) {
				bnt_save.click();
			}
			var btn_volverConsulta = document.getElementById("pda_p45_btn_volverConsulta");
			if (btn_volverConsulta != null && typeof(btn_volverConsulta) != 'undefined' && btn_volverConsulta.className.indexOf('operacionDefecto') != -1) {
				btn_volverConsulta.click();
			}
		}
    }
	events_45_volver();
}

function validacionStock(id){
	
	var idActual = id;
	//Obtención de fila y columna actuales
	var fila = idActual.substring(idActual.lastIndexOf("_")+1);
	var campoActual = document.getElementById(id).value;
	var campoActualPunto = document.getElementById(id).value.replace(',','.');
	var campoStockTmp = "pda_p45_fld_stock_" + fila + "_tmp";
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
		document.getElementById(id).className = document.getElementById(id).className + " inputResaltado";
	}
	else
	{
		document.getElementById(id).className = document.getElementById(id).className.replace('inputResaltado','');
	}
	
	return resultadoValidacion;
}

function controlNavegacion(e) {
	e = e || window.event;
	var idActual = (e.target || e.srcElement).id;
	var idFoco;
	//Obtención de fila y columna actuales
	var fila = idActual.substring(idActual.lastIndexOf("_")+1);
	var validacionNavegacion = 'S';
	
	var key = e.which || e.keyCode; //para soporte de todos los navegadores
    //Flechas de cursores para navegación
    if (key == 38){//Tecla arriba
		if(e.preventDefault) {
	        e.preventDefault();
	    } else {
	        e.returnValue = false;
	    }  
    	idFoco = "pda_p45_fld_stock_" + (parseInt(fila,10)-1);
    	objFoco = document.getElementById(idFoco);
		if (objFoco != null && typeof(objFoco) != 'undefined') {
	   		validacionNavegacion = validacionStock("pda_p45_fld_stock_"+fila);
	    	if (validacionNavegacion=='S'){
	    		objFoco.focus();
	    		objFoco.select();
	    	}
		}
    }
    if (key == 40){//Tecla abajo
		if(e.preventDefault) {
	        e.preventDefault();
	    } else {
	        e.returnValue = false;
	    }  
    	idFoco = "pda_p45_fld_stock_" + (parseInt(fila,10)+1);
    	objFoco = document.getElementById(idFoco);
		if (objFoco != null && typeof(objFoco) != 'undefined') {
	    	validacionNavegacion = validacionStock("pda_p45_fld_stock_"+fila);
	    	if (validacionNavegacion=='S'){
	    		objFoco.focus();
	    		objFoco.select();
	    	}
		}
    }
}

function events_45_volver(){
	var imagenVolver =  document.getElementById('pda_p10_volver');
	if (imagenVolver != null && typeof(imagenVolver) != 'undefined') {
		imagenVolver.onclick = function () {
			var origenInventario = document.getElementById('origenInventario').value;
			var tipoMensaje = document.getElementById('pda_p45_fld_tipoMensaje');
			if (tipoMensaje.value == "CE"){
				document.forms[0].action = "pdaP45InvLibCorreccionStockPCN.do?actionPrev=yes&origenInventario="+origenInventario;
			} else {
				document.forms[0].action = "pdaP45InvLibCorreccionStockPCN.do?actionVolverConsulta=yes&origenInventario="+origenInventario;
			}
			document.forms[0].submit();

		};
	}
}
