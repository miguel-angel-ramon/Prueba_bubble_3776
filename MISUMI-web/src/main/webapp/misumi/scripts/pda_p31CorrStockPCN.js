var controlReturn = false;
var limite = 9999;
function initializeScreen_p31(){
	//Si existe un error, no queremos que se tengan en cuenta los siguientes eventos ni
	//funcionalidades, porque puede ser que referencien elementos que no existen en el árbol DOM.
	var exiteError = document.getElementById("pda_p31_error_bloque1");

	if (typeof(exiteError) == 'undefined' || exiteError == null){
		var contador = 0;
		var objeto = document.getElementById("pda_p31_fld_stock_" + contador);
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
			//Solo queremos este evento en los centros caprabo
			var esCentroCaprabo = document.getElementById("pda_p31_esCaprabo").value;
			if(esCentroCaprabo == "true"){
				objeto.onchange = function(e) {
					//Si no se ha pulsado return.
					if(!controlReturn){
						controlExcedido(e);
					}else{
						//Desactivamos el flag del return para que si hay un change sin return funcione las siguientes veces.
						controlReturn = false;
					}
				}
			}
			contador++;
			objeto = document.getElementById("pda_p31_fld_stock_" + contador);
		}

		document.getElementById("pda_p31_fld_stock_0").select();

		document.onkeydown = function(e) {
			e = e || window.event;
			var key = e.which || e.keyCode; //para soporte de todos los navegadores
			if(key == 13) {
				if(e.preventDefault) {
					e.preventDefault();
				} else {
					e.returnValue = false;
				}  
				//Miramos en centro caprabo si se ha excedido el limite. Si es caprabo hay que mirar si ha excedido el limite para guardar o no.
				//Si lo ha excedido no se guarda
				var esCentroCaprabo = document.getElementById("pda_p31_esCaprabo").value;
				if(esCentroCaprabo == "true"){
					//Si no se ha excedido ningún límite en ningún campo se guarda.
					if(!controlExcedido(e)){
						var bnt_save = document.getElementById("pda_p31_btn_save");
						if (bnt_save != null && typeof(bnt_save) != 'undefined' && bnt_save.className.indexOf('operacionDefecto') != -1) {
							bnt_save.click();
						}
					}else{
						//Si presionas enter para guardar y el número excede límites,
						//se hace la validación, se muestra error y se mantiene el foco en el mismo número.
						//Si cambias ese mismo número a uno válido y te mueves a otro con la flecha, no está ejecutando
						//el onchange por causas que desconozco y no quita el azul. 

						//Si presionas enter para guardar y el número excede límites,
						//se hace la validación, se muestra error y NO se mantiene el foco en el mismo número, si nos colocamos
						//en ese número de nuevo y ponemos un número válido y te mueves a otro con la flecha, si ejecuta el onchange,
						//por lo que obligamos a que haga blur del campo en el caso de presionar enter.

						//Obtenemos el input actual y hacemos blur.
						var idActual = (e.target || e.srcElement).id;

						//Como vamos a hacer blur, se va a ejecutar el evento de cambio de valor. Como ya se ha ejecutado, 
						//ponemos flag a true para que no ejecute de nuevo controlExcedido.
						controlReturn = true;

						//Obtenemos el input actual
						document.getElementById(idActual).blur();

						//Obtenemos el input actual
						document.getElementById(idActual).focus();
						document.getElementById(idActual).select();

						var bnt_save = document.getElementById("pda_p31_btn_save");
						if (bnt_save != null && typeof(bnt_save) != 'undefined' && bnt_save.className.indexOf('operacionDefecto') != -1) {
							bnt_save.click();
						}
					}
				}else{
					var bnt_save = document.getElementById("pda_p31_btn_save");
					if (bnt_save != null && typeof(bnt_save) != 'undefined' && bnt_save.className.indexOf('operacionDefecto') != -1) {
						bnt_save.click();
					}
				}

				var btn_volverConsulta = document.getElementById("pda_p31_btn_volverConsulta");
				if (btn_volverConsulta != null && typeof(btn_volverConsulta) != 'undefined' && btn_volverConsulta.className.indexOf('operacionDefecto') != -1) {
					btn_volverConsulta.click();
				}
			}
		}
		var btnSave = document.getElementById("pda_p31_btn_save");		
		btnSave.onclick = function(e) {
			var esCentroCaprabo = document.getElementById("pda_p31_esCaprabo").value;
			if(esCentroCaprabo == "true"){
				//Obtenemos los elementos que puedan tener borde azul
				var inputAzul =  getElementsByClassName(document.body,'inputAzul');
				if(inputAzul.length == 0){
					document.getElementById("p31_form").action = "pdaP31CorrStockPCN.do?actionSave=yes";
					document.getElementById("p31_form").submit();
				}else{
					var txtLimites = document.getElementById("pda_p31_msgExcedido").value;
					var respuesta = confirm(txtLimites);
					if (respuesta == true) {
						document.getElementById("p31_form").action = "pdaP31CorrStockPCN.do?actionSave=yes";
						document.getElementById("p31_form").submit();
					} 
				}
			}else{
				document.getElementById("p31_form").action = "pdaP31CorrStockPCN.do?actionSave=yes";
				document.getElementById("p31_form").submit();
			}
		}
		events_31_volver();
	}

	function validacionStock(id){

		var idActual = id;
		//Obtención de fila y columna actuales
		var fila = idActual.substring(idActual.lastIndexOf("_")+1);
		var campoActual = document.getElementById(id).value;
		var campoActualPunto = document.getElementById(id).value.replace(',','.');
		var campoStockTmp = "pda_p31_fld_stock_" + fila + "_tmp";
		var resultadoValidacion = 'S';

		//Formateamos el valor introducido por el usuario
		if (campoActual == '')
		{
			campoActualPunto = "0.000";
		}

		//Sólo si ha cambiado el valor del campo.
		if ((isNaN(parseFloat(campoActualPunto)) || campoActual.split(",").length > 2 || campoActual.split(".").length > 2) &&
				(parseFloat(document.getElementById(campoStockTmp).value) != parseFloat(campoActualPunto)))
		{
			//Quitamos posibles rojos anteriores
			document.getElementById(id).className = document.getElementById(id).className.replace('inputResaltado','');

			//Pintamos de rojo el campo.
			document.getElementById(id).className = document.getElementById(id).className + " inputResaltado";

			//Quitamos posibles errores de exceso stock.
			document.getElementById(id).className = document.getElementById(id).className.replace('inputAzul','');
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
			idFoco = "pda_p31_fld_stock_" + (parseInt(fila,10)-1);
			objFoco = document.getElementById(idFoco);
			if (objFoco != null && typeof(objFoco) != 'undefined') {
				validacionNavegacion = validacionStock("pda_p31_fld_stock_"+fila);
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
			idFoco = "pda_p31_fld_stock_" + (parseInt(fila,10)+1);
			objFoco = document.getElementById(idFoco);
			if (objFoco != null && typeof(objFoco) != 'undefined') {
				validacionNavegacion = validacionStock("pda_p31_fld_stock_"+fila);
				if (validacionNavegacion=='S'){
					objFoco.focus();
					objFoco.select();
				}
			}
		}
	}

	function events_31_volver(){
		var imagenVolver =  document.getElementById('pda_p10_volver');
		if (imagenVolver != null && typeof(imagenVolver) != 'undefined') {
			imagenVolver.onclick = function () {
				var tipoMensaje = document.getElementById('pda_p31_fld_tipoMensaje');
				if (tipoMensaje.value == "CE"){
					document.forms[0].action = "pdaP31CorrStockPCN.do?actionPrev=yes";
				} else {
					document.forms[0].action = "pdaP31CorrStockPCN.do?actionVolverConsulta=yes";
				}
				document.forms[0].submit();

			};
		}
	}

	function controlExcedido(e){
		//Aqui miramos si 
		e = e || window.event;	
		var idActual = (e.target || e.srcElement).id;

		//Validamos el stock
		var fila = idActual.substring(idActual.lastIndexOf("_"));
		validacionStock("pda_p31_fld_stock"+fila);

		//Obtenemos el input actual
		var inputActual = document.getElementById(idActual);

		//Mostramos aviso.
		var aviso = document.getElementById("pda_p31_mensajeAviso");
		var error = document.getElementById("pda_p31_mensajeError");
		//Miramos si tiene error de validacionStock
		if(!hasClass(inputActual, "inputResaltado")){
			//Si no tiene error, de validacion stock miramos si el cambio
			//no es menos que 1/limite o mayor que *limite del campo temporal.
			var campoActualPunto = parseFloat(document.getElementById(idActual).value.replace(',','.'));
			var campoStockTmp = parseFloat(document.getElementById(idActual + "_tmp").value.replace(',','.'));

			//Si el stock es menor de 0, tomamos el límite por debajo como 0.
			var campoStockTmpEntreLim = (campoStockTmp < 0) ? 0 : Math.floor(campoStockTmp / limite);

			//Si el stock es igual a 0 o menor, tomamos que el límite superior es limite.
			var campoStockTmpPorLim = (campoStockTmp == 0 || campoStockTmp < 0) ?  (limite): (campoStockTmp * limite);

			//Botones siguiente, anterior, guardar.
			var guardar = document.getElementById("pda_p31_btn_save");
			var pagAnt = document.getElementById("pda_p31_btn_pagAnt");
			var pagSig = document.getElementById("pda_p31_btn_pagSig");

			if(campoActualPunto < campoStockTmpEntreLim || campoActualPunto > campoStockTmpPorLim) {
				//Si el inputActual no tiene la clase inputAzul, se pone. Si no se pone este control puede poner varias veces la clase.
				if(!hasClass(inputActual,"inputAzul")){
					//Pintamos de azul el campo.
					inputActual.className = inputActual.className + " inputAzul";
				}

				aviso.style.display = "block";
				error.style.display = "none";

				//Si hay aviso de exceso de cantidad, no dejamos guardar ni paginar hasta que corrija
				//guardar.style.display = "none";

				//La paginación solo existe si es una lista. Puede que no sea lista, por lo que ocultamos.
				if(pagAnt != null && pagSig != null){
					pagAnt.style.display = "none";
					pagSig.style.display = "none";
				}
				//Devolvemos true. Este true indica que se ha excedido el limite.
				return true;
			}else{
				//Quitamos el borde azul
				inputActual.className = inputActual.className.replace('inputAzul','');

				//Obtenemos los elementos que puedan tenr borde azul
				var inputAzul =  getElementsByClassName(document.body,'inputAzul');

				//Si no existe ninguno, ocultamos el mensaje de alerta.
				if(inputAzul.length == 0){
					aviso.style.display = "none";

					//Si no hay aviso de exceso de cantidad, dejamos guardar y paginar
					//guardar.style.display = "block";

					//La paginación solo existe si es una lista. Puede que no sea lista, por lo que ocultamos.
					if(pagAnt != null && pagSig != null){
						pagAnt.style.display = "block";
						pagSig.style.display = "block";
					}
					//Devolvemos false. Este false indica que no se ha excedido el limite
					return false;
				}else{
					//Devolvemos true. Este true nos indica que se ha excedido el limite.
					return true;
				}
			}
		}
	}
}