function initializeScreen_p42(){
	//Eventos de cabecera
	events_p42_nuevo();
	events_p42_seccion();
	events_p42_volver();
	
	posInicialCursorP42();
	var origenGISAE = document.getElementById('origenGISAE');
	if (origenGISAE.value != "SI"){
	fld_txt_infoRef = document.getElementById("pda_p01_txt_infoRef");
	fld_txt_infoRef.onkeydown = function(e) {
		e = e || window.event;
		var key = e.which || e.keyCode; //para soporte de todos los navegadores
	 	if (key == 13){//Tecla enter, guardado
	 		if(e.preventDefault) {
		        e.preventDefault();
		    } else {
		        e.returnValue = false;
		    }
	 		document.getElementById("pda_p42_fld_codArtCab").value = document.getElementById("pda_p01_txt_infoRef").value;
			document.getElementById("actionNuevo").value = "S";
			document.getElementById("pdaP42InventarioLibre").submit();
	    }
	}
	}
	fld_camara_bandeja = document.getElementById("pda_p42_fld_camaraBandeja");
	if (fld_camara_bandeja != null && typeof(fld_camara_bandeja) != 'undefined') {
		fld_camara_bandeja.onblur = function() {
			validacionCamaraBandeja();
		}
		fld_camara_bandeja.onclick = function(e) {
			e = e || window.event;
			this.select();
			if(e.stopPropagation) {
			    e.stopPropagation();
			} else {
				e.cancelBubble = true;
			}
		}
		fld_camara_bandeja.onkeydown = function(e) {
			controlNavegacion(e);
		}
	}

	fld_camara_stock = document.getElementById("pda_p42_fld_camaraStock");
	if (fld_camara_stock != null && typeof(fld_camara_stock) != 'undefined') {
		fld_camara_stock.onblur = function() {
			validacionCamaraStock();
		}
		fld_camara_stock.onclick = function(e) {
			e = e || window.event;
			this.select();
			if(e.stopPropagation) {
			    e.stopPropagation();
			} else {
				e.cancelBubble = true;
			}
		}
		fld_camara_stock.onkeydown = function(e) {
			controlNavegacion(e);
		}
	}

	fld_sala_bandeja = document.getElementById("pda_p42_fld_salaBandeja");
	if (fld_sala_bandeja != null && typeof(fld_sala_bandeja) != 'undefined') {
		fld_sala_bandeja.onblur = function() {
			validacionSalaBandeja();
		}
		fld_sala_bandeja.onclick = function(e) {
			e = e || window.event;
			this.select();
			if(e.stopPropagation) {
			    e.stopPropagation();
			} else {
				e.cancelBubble = true;
			}
		}
		fld_sala_bandeja.onkeydown = function(e) {
			controlNavegacion(e);
		}
	}

	fld_sala_stock = document.getElementById("pda_p42_fld_salaStock");
	if (fld_sala_stock != null && typeof(fld_sala_stock) != 'undefined') {
		fld_sala_stock.onblur = function() {
			validacionSalaStock();
		}
		fld_sala_stock.onclick = function(e) {
			e = e || window.event;
			this.select();
			if(e.stopPropagation) {
			    e.stopPropagation();
			} else {
				e.cancelBubble = true;
			}
		}
		fld_sala_stock.onkeydown = function(e) {
			controlNavegacion(e);
		}
	}

	fld_camara_bandejaLink = document.getElementById("pda_p42_fld_camaraBandejaLink");
	fld_camara_stockLink = document.getElementById("pda_p42_fld_camaraStockLink");
	fld_sala_bandejaLink = document.getElementById("pda_p42_fld_salaBandejaLink");
	fld_sala_stockLink = document.getElementById("pda_p42_fld_salaStockLink");
	fld_referenciaActual = document.getElementById("referenciaActual");
	fld_mmc = document.getElementById("pda_p42_fld_mmc");
	

	if (fld_camara_bandejaLink != null && typeof(fld_camara_bandejaLink) != 'undefined') {
		fld_camara_bandejaLink.onclick = function(e) {
			e = e || window.event;
			valueNoGuardar = "";
			fld_chkNoGuardar1 = document.getElementById("chkNoGuardar1");
			if (fld_chkNoGuardar1){
				valueNoGuardar = fld_chkNoGuardar1.checked;
			}
			valueSeccion = "";
			fld_Seccion = document.getElementById("pda_p42_fld_seccion");
			if (fld_Seccion){
				valueSeccion = fld_Seccion.value;
			}
			window.location = "./pdaP42InventarioLibre.do?codArt="+fld_referenciaActual.value+"&origenInventario=CA&mmc="+fld_mmc.value+"&origenGISAE="+origenGISAE.value+"&noGuardar="+valueNoGuardar+"&seccion="+valueSeccion;
		}
	}

	if (fld_camara_stockLink != null && typeof(fld_camara_stockLink) != 'undefined') {
		
		fld_camara_stockLink.onclick = function(e) {
			e = e || window.event;
			valueNoGuardar = "";
			fld_chkNoGuardar1 = document.getElementById("chkNoGuardar1");
			if (fld_chkNoGuardar1){
				valueNoGuardar = fld_chkNoGuardar1.checked;
			}
			valueSeccion = "";
			fld_Seccion = document.getElementById("pda_p42_fld_seccion");
			if (fld_Seccion){
				valueSeccion = fld_Seccion.value;
			}
			window.location = "./pdaP42InventarioLibre.do?codArt="+fld_referenciaActual.value+"&origenInventario=CA&mmc="+fld_mmc.value+"&origenGISAE="+origenGISAE.value+"&noGuardar="+valueNoGuardar+"&seccion="+valueSeccion;
			
		}
	}

	if (fld_sala_bandejaLink != null && typeof(fld_sala_bandejaLink) != 'undefined') {
		fld_sala_bandejaLink.onclick = function(e) {
			e = e || window.event;
			valueNoGuardar = "";
			fld_chkNoGuardar1 = document.getElementById("chkNoGuardar1");
			if (fld_chkNoGuardar1){
				valueNoGuardar = fld_chkNoGuardar1.checked;
			}
			valueSeccion = "";
			fld_Seccion = document.getElementById("pda_p42_fld_seccion");
			if (fld_Seccion){
				valueSeccion = fld_Seccion.value;
			}
			window.location = "./pdaP42InventarioLibre.do?codArt="+fld_referenciaActual.value+"&origenInventario=SV&mmc="+fld_mmc.value+"&origenGISAE="+origenGISAE.value+"&noGuardar="+valueNoGuardar+"&seccion="+valueSeccion;
		}
	}

	if (fld_sala_stockLink != null && typeof(fld_sala_stockLink) != 'undefined') {
		fld_sala_stockLink.onclick = function(e) {
			e = e || window.event;
			valueNoGuardar = "";
			fld_chkNoGuardar1 = document.getElementById("chkNoGuardar1");
			if (fld_chkNoGuardar1){
				valueNoGuardar = fld_chkNoGuardar1.checked;
			}
			valueSeccion = "";
			fld_Seccion = document.getElementById("pda_p42_fld_seccion");
			if (fld_Seccion){
				valueSeccion = fld_Seccion.value;
			}
			window.location = "./pdaP42InventarioLibre.do?codArt="+fld_referenciaActual.value+"&origenInventario=SV&mmc="+fld_mmc.value+"&origenGISAE="+origenGISAE.value+"&noGuardar="+valueNoGuardar+"&seccion="+valueSeccion;
			
		}
	}
	if(document.getElementById("pda_p42_fld_descDiferencia")){
		if(document.getElementById("pda_p42_fld_descDiferencia").value == "DIFERENCIA: Error"){
			fld_camara_stockLink.disabled = true;
			fld_sala_stockLink.disabled = true;
			document.getElementById("pda_p42_fld_totalStock").disabled=true;
		}
	}
	
	validacionStockBandejas();
}

function validacionCamaraBandeja(){
	var objetoActual = document.getElementById("pda_p42_fld_camaraBandeja");
	var campoActual = objetoActual.value;
	var campoActualPunto = objetoActual.value.replace(',','.');
	var campoCamaraBandejaTmp = "pda_p42_fld_camaraBandeja_tmp";
	var campoSalaBandejaTmp = "pda_p42_fld_salaBandeja_tmp";
	var campoTotalBandeja = "pda_p42_fld_totalBandeja";
	var campoStockActual = "pda_p42_fld_stockActual";
	var campoCamaraStock = "pda_p42_fld_camaraStock";
	var campoCamaraStockTmp = "pda_p42_fld_camaraStock_tmp";
	var campoSalaStockTmp = "pda_p42_fld_salaStock_tmp";
	var campoTotalStock = "pda_p42_fld_totalStock";
	var campoKgs = "pda_p42_fld_kgs";
	var campoDiferencia = "pda_p42_fld_diferencia";
	var campoDescDiferencia = "pda_p42_fld_descDiferencia";
	
	var resultadoValidacion = 'S';
	
	//Formateamos el valor introducido por el usuario
	if (campoActual == '')
	{
		campoActualPunto = "0";
	}
	//Sólo si ha cambiado el valor del campo.
	if ((isNaN(parseFloat(campoActualPunto)) || objetoActual.value.split(",").length > 2) &&
			(parseFloat(document.getElementById(campoCamaraBandejaTmp).value) != parseFloat(campoActualPunto)))
	{
		//Pintamos de rojo el campo.
		objetoActual.className = objetoActual.className + " inputResaltado";
	}
	else
	{
		objetoActual.className = objetoActual.className.replace('inputResaltado','');
		objetoActual.value = parseFloat(campoActualPunto).toFixed(0).replace(',','.');
		if ((document.getElementById(campoCamaraBandejaTmp).value != null) && (document.getElementById(campoCamaraBandejaTmp).value != objetoActual.value))
		{
			document.getElementById(campoCamaraBandejaTmp).value = objetoActual.value;
			document.getElementById(campoTotalBandeja).value = (parseFloat(campoActualPunto)+parseFloat(document.getElementById(campoSalaBandejaTmp).value.replace(',','.'))).toFixed(0).replace('.',','); 
			
			document.getElementById(campoCamaraStock).value= (parseFloat(campoActualPunto)*parseFloat(document.getElementById(campoKgs).value.replace(',','.'))).toFixed(2);
			document.getElementById(campoCamaraStockTmp).value= document.getElementById(campoCamaraStock).value;
			document.getElementById(campoTotalStock).value = (parseFloat(document.getElementById(campoCamaraStock).value.replace(',','.'))+parseFloat(document.getElementById(campoSalaStockTmp).value.replace(',','.'))).toFixed(2); 
			
			document.getElementById(campoDiferencia).value = (parseFloat(document.getElementById(campoTotalBandeja).value.replace(',','.')) - parseFloat(document.getElementById(campoStockActual).value.replace(',','.'))).toFixed(2).replace('.',',');
			if (parseFloat(document.getElementById(campoDiferencia).value) > 0) {
				document.getElementById(campoDescDiferencia).value = document.getElementById(campoDescDiferencia).value.substring(0,document.getElementById(campoDescDiferencia).value.indexOf(":")+1) +  " +" + document.getElementById(campoDiferencia).value;
			} else {
				document.getElementById(campoDescDiferencia).value = document.getElementById(campoDescDiferencia).value.substring(0,document.getElementById(campoDescDiferencia).value.indexOf(":")+1) +  " " + document.getElementById(campoDiferencia).value;
		
			}
		}
	}
	
	validacionStockBandejas();
	
	return resultadoValidacion;
}

function validacionSalaBandeja(){
	var objetoActual = document.getElementById("pda_p42_fld_salaBandeja");
	var campoActual = objetoActual.value;
	var campoActualPunto = objetoActual.value.replace(',','.');
	var campoCamaraBandejaTmp = "pda_p42_fld_camaraBandeja_tmp";
	var campoSalaBandejaTmp = "pda_p42_fld_salaBandeja_tmp";
	var campoTotalBandeja = "pda_p42_fld_totalBandeja";
	var campoStockActual = "pda_p42_fld_stockActual";
	var campoSalaStock = "pda_p42_fld_salaStock";
	var campoSalaStockTmp = "pda_p42_fld_salaStock_tmp";
	var campoCamaraStockTmp = "pda_p42_fld_camaraStock_tmp";
	var campoTotalStock = "pda_p42_fld_totalStock";
	var campoKgs = "pda_p42_fld_kgs";
	var campoDiferencia = "pda_p42_fld_diferencia";
	var campoDescDiferencia = "pda_p42_fld_descDiferencia";
	
	var resultadoValidacion = 'S';
	
	//Formateamos el valor introducido por el usuario
	if (campoActual == '')
	{
		campoActualPunto = "0";
	}
	//Sólo si ha cambiado el valor del campo.
	if ((isNaN(parseFloat(campoActualPunto)) || objetoActual.value.split(",").length > 2) &&
			(parseFloat(document.getElementById(campoSalaBandejaTmp).value) != parseFloat(campoActualPunto)))
	{
		//Pintamos de rojo el campo.
		objetoActual.className = objetoActual.className + " inputResaltado";
	}
	else
	{
		objetoActual.className = objetoActual.className.replace('inputResaltado','');
		objetoActual.value = parseFloat(campoActualPunto).toFixed(0).replace(',','.');
		if ((document.getElementById(campoSalaBandejaTmp).value != null) && (document.getElementById(campoSalaBandejaTmp).value != objetoActual.value))
		{
			document.getElementById(campoSalaBandejaTmp).value = objetoActual.value;
			document.getElementById(campoTotalBandeja).value = (parseFloat(campoActualPunto)+parseFloat(document.getElementById(campoCamaraBandejaTmp).value.replace(',','.'))).toFixed(0).replace('.',','); 
			
			document.getElementById(campoSalaStock).value= (parseFloat(campoActualPunto)*parseFloat(document.getElementById(campoKgs).value.replace(',','.'))).toFixed(2);
			document.getElementById(campoSalaStockTmp).value= document.getElementById(campoSalaStock).value;
			document.getElementById(campoTotalStock).value = (parseFloat(document.getElementById(campoSalaStock).value.replace(',','.'))+parseFloat(document.getElementById(campoCamaraStockTmp).value.replace(',','.'))).toFixed(2); 
			
			document.getElementById(campoDiferencia).value = (parseFloat(document.getElementById(campoTotalBandeja).value.replace(',','.')) - parseFloat(document.getElementById(campoStockActual).value.replace(',','.'))).toFixed(2).replace('.',',');
			
			if (parseFloat(document.getElementById(campoDiferencia).value) > 0) {
				document.getElementById(campoDescDiferencia).value = document.getElementById(campoDescDiferencia).value.substring(0,document.getElementById(campoDescDiferencia).value.indexOf(":")+1) +  " +" + document.getElementById(campoDiferencia).value;
			} else {
				document.getElementById(campoDescDiferencia).value = document.getElementById(campoDescDiferencia).value.substring(0,document.getElementById(campoDescDiferencia).value.indexOf(":")+1) +  " " + document.getElementById(campoDiferencia).value;
		
			}
		
		}
	}
	
	validacionStockBandejas();
	
	return resultadoValidacion;
}

function validacionCamaraStock(){
	var objetoActual = document.getElementById("pda_p42_fld_camaraStock");
	var campoActual = objetoActual.value;
	var campoActualPunto = objetoActual.value.replace(',','.');
	var campoCamaraStockTmp = "pda_p42_fld_camaraStock_tmp";
	var campoSalaStockTmp = "pda_p42_fld_salaStock_tmp";
	var campoTotalStock = "pda_p42_fld_totalStock";
	var campoStockActual = "pda_p42_fld_stockActual";
	var campoDiferencia = "pda_p42_fld_diferencia";
	var campoDescDiferencia = "pda_p42_fld_descDiferencia";
	
	var resultadoValidacion = 'S';
	
	//Formateamos el valor introducido por el usuario
	if (campoActual == '')
	{
		campoActualPunto = "0.000";
	}
	//Sólo si ha cambiado el valor del campo.
	if ((isNaN(parseFloat(campoActualPunto)) || objetoActual.value.split(",").length > 2) &&
			(parseFloat(document.getElementById(campoCamaraStockTmp).value) != parseFloat(campoActualPunto)))
	{
		//Pintamos de rojo el campo.
		objetoActual.className = objetoActual.className + " inputResaltado";
	}
	else
	{
		objetoActual.className = objetoActual.className.replace('inputResaltado','');
		objetoActual.value = parseFloat(campoActualPunto).toFixed(2).replace(',','.');
		if ((document.getElementById(campoCamaraStockTmp).value != null) && (document.getElementById(campoCamaraStockTmp).value != objetoActual.value))
		{
			document.getElementById(campoCamaraStockTmp).value = objetoActual.value;
			document.getElementById(campoTotalStock).value = (parseFloat(campoActualPunto)+parseFloat(document.getElementById(campoSalaStockTmp).value.replace(',','.'))).toFixed(2); 
			
			if (fld_camara_bandeja == null || typeof(fld_camara_bandeja) == 'undefined') {//Si hay bandejas el cálculo se delega a las bandejas
				document.getElementById(campoDiferencia).value = (parseFloat(document.getElementById(campoTotalStock).value.replace(',','.')) - parseFloat(document.getElementById(campoStockActual).value.replace(',','.'))).toFixed(2).replace('.',',');
				
			
				if (parseFloat(document.getElementById(campoDiferencia).value) > 0) {
					document.getElementById(campoDescDiferencia).value = document.getElementById(campoDescDiferencia).value.substring(0,document.getElementById(campoDescDiferencia).value.indexOf(":")+1) +  " +" + document.getElementById(campoDiferencia).value;
				} else {
					document.getElementById(campoDescDiferencia).value = document.getElementById(campoDescDiferencia).value.substring(0,document.getElementById(campoDescDiferencia).value.indexOf(":")+1) +  " " + document.getElementById(campoDiferencia).value;
			
				}
			
				
			}
		}
	}

	validacionStockBandejas();
	
	return resultadoValidacion;
}

function validacionSalaStock(){
	var objetoActual = document.getElementById("pda_p42_fld_salaStock");
	var campoActual = objetoActual.value;
	var campoActualPunto = document.getElementById("pda_p42_fld_salaStock").value.replace(',','.');
	var campoCamaraStockTmp = "pda_p42_fld_camaraStock_tmp";
	var campoSalaStockTmp = "pda_p42_fld_salaStock_tmp";
	var campoTotalStock = "pda_p42_fld_totalStock";
	var campoStockActual = "pda_p42_fld_stockActual";
	var campoDiferencia = "pda_p42_fld_diferencia";
	var campoDescDiferencia = "pda_p42_fld_descDiferencia";
	
	var resultadoValidacion = 'S';
	
	//Formateamos el valor introducido por el usuario
	if (campoActual == '')
	{
		campoActualPunto = "0.000";
	}
	//Sólo si ha cambiado el valor del campo.
	if ((isNaN(parseFloat(campoActualPunto)) || objetoActual.value.split(",").length > 2) &&
			(parseFloat(document.getElementById(campoSalaStockTmp).value) != parseFloat(campoActualPunto)))
	{
		//Pintamos de rojo el campo.
		objetoActual.className = objetoActual.className + " inputResaltado";
	}
	else
	{
		objetoActual.className = objetoActual.className.replace('inputResaltado','');
		objetoActual.value = parseFloat(campoActualPunto).toFixed(2).replace(',','.');
		if ((document.getElementById(campoSalaStockTmp).value != null) && (document.getElementById(campoSalaStockTmp).value != objetoActual.value))
		{
			document.getElementById(campoSalaStockTmp).value = objetoActual.value;
			document.getElementById(campoTotalStock).value = (parseFloat(campoActualPunto)+parseFloat(document.getElementById(campoCamaraStockTmp).value.replace(',','.'))).toFixed(2); 
			
			if (fld_camara_bandeja == null || typeof(fld_camara_bandeja) == 'undefined') {//Si hay bandejas el cálculo se delega a las bandejas
				document.getElementById(campoDiferencia).value = (parseFloat(document.getElementById(campoTotalStock).value.replace(',','.')) - parseFloat(document.getElementById(campoStockActual).value.replace(',','.'))).toFixed(2).replace('.',',');
				
				if (parseFloat(document.getElementById(campoDiferencia).value) > 0) {
					document.getElementById(campoDescDiferencia).value = document.getElementById(campoDescDiferencia).value.substring(0,document.getElementById(campoDescDiferencia).value.indexOf(":")+1) +  " +" + document.getElementById(campoDiferencia).value;
				} else {
					document.getElementById(campoDescDiferencia).value = document.getElementById(campoDescDiferencia).value.substring(0,document.getElementById(campoDescDiferencia).value.indexOf(":")+1) +  " " + document.getElementById(campoDiferencia).value;
			
				}
			}
		}
	}
		
	validacionStockBandejas();
	
	return resultadoValidacion;
}

//Si un campo tiene valor, el otro no puede ser 0
function validacionStockBandejas(){
	if (null != document.getElementById("pda_p42_fld_totalBandeja") && null != document.getElementById("pda_p42_fld_totalStock")){
		
		var bandejas = document.getElementById("pda_p42_fld_totalBandeja").value;
		var kilos = document.getElementById("pda_p42_fld_totalStock").value.replace(',','.');
		
		if (!isNaN(bandejas) && !isNaN(kilos)){
			if ((bandejas > 0 && kilos == 0) || (bandejas == 0 && kilos > 0)){
				// Ocultamos guardar si alguno de los campo es 0
				document.getElementById("pda_p42_btn_guardarTodo").style.visibility = "hidden";
				document.getElementById("pda_p42_btn_guardar").style.visibility = "hidden";
			} else {
				document.getElementById("pda_p42_btn_guardarTodo").style.visibility = "visible";
				document.getElementById("pda_p42_btn_guardar").style.visibility = "visible";
			}
		} else {
			document.getElementById("pda_p42_btn_guardarTodo").style.visibility = "hidden";
			document.getElementById("pda_p42_btn_guardar").style.visibility = "hidden";
		}
	}
}

function events_p42_volver(){
	var imagenVolver =  document.getElementById('pda_p10_volver');
	if (imagenVolver != null && typeof(imagenVolver) != 'undefined') {
		imagenVolver.onclick = function () {
			document.getElementById("actionVolver").value = "S";
			document.getElementById("pdaP42InventarioLibre").submit();
		}
	}
}

function events_p42_nuevo(){
	var btnNuevo =  document.getElementById('pda_p01_btn_aceptar');
	if (btnNuevo != null && typeof(btnNuevo) != 'undefined') {
		btnNuevo.onclick = function () {
			document.getElementById("pda_p42_fld_codArtCab").value = document.getElementById("pda_p01_txt_infoRef").value;
			document.getElementById("actionNuevo").value = "S";
			document.getElementById("pdaP42InventarioLibre").submit();
		}
	}
}

function events_p42_seccion(){
	var selectSeccion =  document.getElementById('pda_p01_txt_infoSeccion');
	if (selectSeccion != null && typeof(selectSeccion) != 'undefined') {
		selectSeccion.onchange = function () {
			document.getElementById("pda_p42_fld_seccion").value = selectSeccion.value;
			document.getElementById("actionSeccion").value = "S";
			document.getElementById("pdaP42InventarioLibre").submit();
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
 		if(e.preventDefault) {
	        e.preventDefault();
	    } else {
	        e.returnValue = false;
	    }
 		idFoco = "pda_p01_txt_infoRef";
 		document.getElementById(idFoco).focus();
 		document.getElementById(idFoco).select();
    }
    //Flechas de cursores para navegación
    if (key == 37){//Tecla izquierda
		if(e.preventDefault) {
	        e.preventDefault();
	    } else {
	        e.returnValue = false;
	    }  
		var bandejas =  document.getElementById('pda_p42_fld_camaraBandeja');
		if (bandejas != null && typeof(bandejas) != 'undefined') {
	    	if(nombreColumna == "pda_p42_fld_camaraStock"){
	        	idFoco = "pda_p42_fld_camaraBandeja";
	        	validacionNavegacion = validacionCamaraStock();
	        	if (validacionNavegacion=='S'){
	        		document.getElementById(idFoco).focus();
	        		document.getElementById(idFoco).select();
	        	}
	    	}
	    	if(nombreColumna == "pda_p42_fld_salaStock"){
	        	idFoco = "pda_p42_fld_salaBandeja";
	        	validacionNavegacion = validacionSalaStock();
	        	if (validacionNavegacion=='S'){
	        		document.getElementById(idFoco).focus();
	        		document.getElementById(idFoco).select();
	        	}
	    	}
		}
    }
    if (key == 39){//Tecla derecha
		if(e.preventDefault) {
	        e.preventDefault();
	    } else {
	        e.returnValue = false;
	    }  
    	if(nombreColumna == "pda_p42_fld_camaraBandeja"){
        	idFoco = "pda_p42_fld_camaraStock";
        	validacionNavegacion = validacionCamaraBandeja();
        	if (validacionNavegacion=='S'){
        		document.getElementById(idFoco).focus();
        		document.getElementById(idFoco).select();
        	}
    	}
    	if(nombreColumna == "pda_p42_fld_salaBandeja"){
        	idFoco = "pda_p42_fld_salaStock";
        	validacionNavegacion = validacionSalaBandeja();
        	if (validacionNavegacion=='S'){
        		document.getElementById(idFoco).focus();
        		document.getElementById(idFoco).select();
        	}
    	}
    }
    if (key == 38){//Tecla arriba
		if(e.preventDefault) {
	        e.preventDefault();
	    } else {
	        e.returnValue = false;
	    }  
    	if(nombreColumna == "pda_p42_fld_salaBandeja"){
        	idFoco = "pda_p42_fld_camaraBandeja";
        	validacionNavegacion = validacionSalaBandeja();
        	if (validacionNavegacion=='S'){
        		document.getElementById(idFoco).focus();
        		document.getElementById(idFoco).select();
        	}
    	}
    	if(nombreColumna == "pda_p42_fld_salaStock"){
        	idFoco = "pda_p42_fld_camaraStock";
        	validacionNavegacion = validacionSalaStock();
        	if (validacionNavegacion=='S'){
        		document.getElementById(idFoco).focus();
        		document.getElementById(idFoco).select();
        	}
    	}
    }
    if (key == 40){//Tecla abajo
		if(e.preventDefault) {
	        e.preventDefault();
	    } else {
	        e.returnValue = false;
	    }  
		if(nombreColumna == "pda_p42_fld_camaraBandeja"){
        	idFoco = "pda_p42_fld_salaBandeja";
        	validacionNavegacion = validacionCamaraBandeja();
        	if (validacionNavegacion=='S'){
        		document.getElementById(idFoco).focus();
        		document.getElementById(idFoco).select();
        	}
    	}
    	if(nombreColumna == "pda_p42_fld_camaraStock"){
        	idFoco = "pda_p42_fld_salaStock";
        	validacionNavegacion = validacionCamaraStock();
        	if (validacionNavegacion=='S'){
        		document.getElementById(idFoco).focus();
        		document.getElementById(idFoco).select();
        	}
    	}
    }
}

function posInicialCursorP42(){
	var camaraBandejas = document.getElementById('pda_p42_fld_camaraBandeja');
	var camaraStock = document.getElementById('pda_p42_fld_camaraStock');
	var salaBandejas = document.getElementById('pda_p42_fld_salaBandeja');
	var salaStock = document.getElementById('pda_p42_fld_salaStock');
	var origenGISAE = document.getElementById('origenGISAE');
	var origenContinuar = document.getElementById('origenContinuar');
	var aviso = document.getElementById('pda_p42_aviso');
	var AVISO_EXCEDIDO_LIMITE_DIF = "EXCEDIDO EL LIMITE DE DIF";
	idFoco = "pda_p01_txt_infoRef";
	
	if (!(aviso != null && typeof(aviso) != 'undefined' && aviso.innerHTML == AVISO_EXCEDIDO_LIMITE_DIF) && origenContinuar.value!="S"){
		if (camaraBandejas != null && typeof(camaraBandejas) != 'undefined' && (camaraBandejas.value == "" || parseFloat(camaraBandejas.value) == parseFloat(0))) {
	    	idFoco = "pda_p42_fld_camaraBandeja";
	   	}else if (camaraStock != null && typeof(camaraStock) != 'undefined' && (camaraStock.value == "" || parseFloat(camaraStock.value) == parseFloat(0))) {
	    	idFoco = "pda_p42_fld_camaraStock";
	   	}else if (salaBandejas != null && typeof(salaBandejas) != 'undefined' && (salaBandejas.value == "" || parseFloat(salaBandejas.value) == parseFloat(0))) {
	    	idFoco = "pda_p42_fld_salaBandeja";
	   	}else if (salaStock != null && typeof(salaStock) != 'undefined' && (salaStock.value == "" || parseFloat(salaStock.value) == parseFloat(0))) {
	    	idFoco = "pda_p42_fld_salaStock";
	   	}
		if (!(idFoco == "pda_p01_txt_infoRef" && origenGISAE.value == "SI")){
			document.getElementById(idFoco).focus();
			document.getElementById(idFoco).select();
		}
	}else{
		document.getElementById(idFoco).focus();
		document.getElementById(idFoco).select();
	}
}