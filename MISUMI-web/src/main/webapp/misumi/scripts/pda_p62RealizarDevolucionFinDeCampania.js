'use strict';
//Si hay cantidad maxima permitida en la pantalla
//este campo varia entre true y false. Cuando cambiamos el stock devuelto (CAN)
//por una cantidad superior a la maxima y CLICAMOS la foto, paginacion,
//stock, referencias completadas, finalizar, etc, esta mostrando error y acto seguido
//realizando la accion del click, por lo que el usuario poduede por ejemplo paginar sin darse cuenta
//de que ha metido una cantidad erronea (aunque se corrige por el valor correcto).

//Para solucionar ese problema, si metemos un CAN superior a la cantidad maxima, se transforma en false y así si
//CLICAMOS en cualquiera de los casos mencionados, no realiza la accion y pone el flag en true
//para que si el usuario vuelve a pinchar, si la realice.

//Si el usuario introduce un CAN superior a cantidad maxima y CLICA cualquier elemento de la pantalla que NO sea
//ninguno de los anteriormente mencionados (paginar, finalizar, etc) cantMaxValida será true, ya que queremos que si acto seguido
//CLICA cualquiera de esos elementos, ejecute la acción.
var cantMaxValida = true;
var stockPantallaAntesNavegar;
function initializeScreen_p62(){
	document.onclick = function (e) {
		//Evento que pone el cantMaxValida a true. Si el usuario clica cualquier parte de la pantalla
		//que no son paginacion, finalizar, stock, etc. 
		e = e || window.event;

		var pagPrimera =  document.getElementById('pda_p62_lnk_first');
		var pagAnt =  document.getElementById('pda_p62_lnk_prev');
		var pagSig =  document.getElementById('pda_p62_lnk_next');
		var pagUltima =  document.getElementById('pda_p62_lnk_last');
		var lineasCompletadas =  document.getElementById('pda_p62_lineasCompletadas');
		var tituloLink =  document.getElementById('pda_p62_titulo');
		var stockActua0 = document.getElementById("pda_p62_valorCampo_stock0");
		var stockActua1 = document.getElementById("pda_p62_valorCampo_stock1");
		var btn_fin =  document.getElementById('pda_p62_btn_fin');
		var foto =  document.getElementById('pda_p62_img_referencia');

		var elementoPulsado = e.target.id;

		if(elementoPulsado != pagPrimera && 
				elementoPulsado != pagAnt &&
				elementoPulsado != pagSig &&
				elementoPulsado != pagUltima &&
				elementoPulsado != lineasCompletadas &&
				elementoPulsado != tituloLink &&
				elementoPulsado != stockActua0 &&
				elementoPulsado != stockActua1 &&
				elementoPulsado != btn_fin &&
				elementoPulsado != foto){

			cantMaxValida = true;
		}
	}

	stockModificadoCorrectamente();

	formateoPuntoPorComa(0);
	formateoPuntoPorComa(1);

	//Eventos de cabecera
	events_p62_referencia();
	events_p62_proveedor();

	events_p62_lineasCompletadas();
	events_p62_paginacion();
	events_p62_cant(0);
	events_p62_cant(1);
	events_p62_bulto(0);
	events_p62_bulto(1);
	events_p62_guardar();
	events_p62_finalizar();
	events_p62_titulo();
	events_p62_foto();
	events_p62_stock(0);
	events_p62_stock(1);
	events_p62_detalleTextilLink();
	events_p62_variosBultos();
	stockPantallaAntesNavegar=document.getElementById('pda_p62_fld_stockDevuelto0').value;
	//events_p62_variosBultos_readOnly();
	// Inicio MISUMI-295
//	var flgBandejas = document.getElementById("flgBandejas").value;
//	if(flgBandejas == 'S'){
	var flgBandejas = document.getElementById("flgBandejas").value;
	var flgPesoVariable = document.getElementById("flgPesoVariable").value;
	if(flgBandejas == 'S' && flgPesoVariable == 'S'){
// FIN MISUMI-295
		events_p62_stockDevuelto(0);
		events_p62_stockDevuelto(1);
	}
	posInicialCursorP62();
	//events_p62_validarStock();
	events_p62_validarEstado();
	events_p62_mostrarFinalizaDevolucion();
}

function events_p62_mostrarFinalizaDevolucion(){
	var devolucionFinalizada =  document.getElementById('pda_p62_devolucionFinalizada').value;
	if(devolucionFinalizada=='SI' || devolucionFinalizada==''){
		document.getElementById('pda_p62_btn_fin').style.display = "block";
	}else{
		document.getElementById('pda_p62_btn_fin').style.display = "none";
	}
}


function events_p62_validarStock(){
	var stckActual =  document.getElementById('pda_p62_valorCampo_stock0');
	var numeroStk = stckActual.innerHTML;
	if(numeroStk == 0){
		var stockDevueltoPantalla =  document.getElementById('pda_p62_fld_stockDevuelto0').value;
		var variosBultosPantalla =document.getElementById('pda_p62_variosBultos').value;
		if(variosBultosPantalla=="false"){
			var bultoPantalla =  document.getElementById('tDevLineasLst0.bulto').value;
			if(stockDevueltoPantalla=='' && bultoPantalla==''){
				document.getElementById('pda_p62_fld_stockDevuelto0').value=0;
				document.getElementById('tDevLineasLst0.bulto').value=0;
			}
		}
	}
}

function esCentroParametrizado(){
	var centroParametrizado=document.getElementById('centroParametrizado').value;
	if(centroParametrizado=='false'){
		return false;
	}else{
		return true;
	}
}

function events_p62_validarEstado(){
	var stockDevueltoPantalla =  document.getElementById('pda_p62_fld_stockDevuelto0').value;
	var estadoPantalla =  document.getElementById('pda_p62_estadoCerrado').value;
	var variosBultosPantalla =document.getElementById('pda_p62_variosBultos').value;
	if(!esCentroParametrizado() || variosBultosPantalla=="true"){
		document.getElementById("pda_p62_btn_cerrarBulto").style.display = "none";
	}else{
		if(estadoPantalla=='S'){
			document.getElementById("pda_p62_btn_cerrarBulto").style.display = "none";
			document.getElementById('pda_p62_fld_stockDevuelto0').readOnly = true;
			document.getElementById('pda_p62_fld_stockDevuelto0').className+= ' pda_p62_readonly';
			document.getElementById('tDevLineasLst0.bulto').readOnly = true;
			document.getElementById('tDevLineasLst0.bulto').className+= ' pda_p62_readonly';
		}else{
			var bultoPantalla =  document.getElementById('tDevLineasLst0.bulto').value;
			if(bultoPantalla==0 || bultoPantalla==''){
				document.getElementById("pda_p62_btn_cerrarBulto").style.display = "none";
			}
		}
	}
}

function events_p62_variosBultos(){
	var variosBultos =  document.getElementById('pda_p62_varios_bultos_label');
	if (variosBultos != null && typeof(variosBultos) != 'undefined') {
		variosBultos.onclick = function () {
			var variosBultosPantalla =document.getElementById('pda_p62_variosBultos').value;
			var camposValidos=true;
			if(variosBultosPantalla=="false"){
				camposValidos=validarCamposPantallaP62VariosBultos();
			}
			if(camposValidos){
				var campoActualCantMax = document.getElementById('pda_p62_valorCampo_cantMax');
				var valorCantidadMax = campoActualCantMax != null ? campoActualCantMax.innerText.replace(',','.') : "";
				//Si cantidad max tiene valor y el número de cantidad es valido.
				if(valorCantidadMax=="" || (stockPantallaAntesNavegar <= parseFloat(valorCantidadMax))){
					document.getElementById("pda_p62_lbl_error").style.display = "none";
					var stockDevueltoPantalla = document.getElementById('pda_p62_fld_stockDevuelto0').value;
					var stockDevueltoOrigPantalla = document.getElementById("stockDevuelto_orig0").value;
					var bultoOrigPantalla = document.getElementById("bulto_orig0").value;
					var codArtPantalla = document.getElementById('codArticulo').value;
					var devolucionPantalla = document.getElementById('devolucion').value;
					var estadoCerradoPantalla = document.getElementById('pda_p62_estadoCerrado').value;
//					var mostrarFinDev = document.getElementById('mostrarFinDev').value;
					var origenPantalla = document.getElementById('origenPantalla').value;
					var selProv = document.getElementById('selProv').value;
					
					if (variosBultosPantalla=="false"){
						var bultoPantalla =  document.getElementById('tDevLineasLst0.bulto').value;
						window.location.href = "./pdaP103VariosBultosFinCampana.do?codArt="+codArtPantalla
											+"&devolucionPantalla="+devolucionPantalla
											+"&stockDevueltoPantalla="+stockDevueltoPantalla
											+"&bultoPantalla="+bultoPantalla
											+"&stockDevueltoOrigPantalla="+stockDevueltoOrigPantalla
											+"&bultoPantallaOrig="+bultoOrigPantalla
											+"&variosBultosPantalla="+variosBultosPantalla
											+"&estadoCerradoPantalla="+estadoCerradoPantalla
											+"&origenPantalla="+origenPantalla
											+"&selectProv="+selProv;
					}else{
						window.location.href = "./pdaP103VariosBultosFinCampana.do?codArt="+codArtPantalla
												+"&devolucionPantalla="+devolucionPantalla
												+"&stockDevueltoPantalla="+stockDevueltoPantalla
												+"&stockDevueltoOrigPantalla="+stockDevueltoOrigPantalla
												+"&bultoPantallaOrig="+bultoOrigPantalla
												+"&variosBultosPantalla="+variosBultosPantalla
												+"&estadoCerradoPantalla="+estadoCerradoPantalla
												+"&origenPantalla="+origenPantalla
												+"&selectProv="+selProv;
					}

				}else{
					stockPantallaAntesNavegar=document.getElementById("stockDevuelto_orig0").value;
				}
			}
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	variosBultos = null;
	
}

function validarCamposPantallaP62VariosBultos(){
	var camposValidosValidacion=true;
	var cantidad = document.getElementById('pda_p62_fld_stockDevuelto0').value;
	var bulto = document.getElementById('tDevLineasLst0.bulto').value;
	var flgPesoVariable = document.getElementById("flgPesoVariable").value;
	//Validación para ver si los 2 registros están rellenos de una misma fila
	if((cantidad=="" && bulto!="") || (cantidad!="" && bulto=="")){
		camposValidosValidacion=false;
	//Validación para ver si los 2 registros están vacios
	}else if(cantidad=="" && bulto==""){
		camposValidosValidacion=true;
	}else{
		//Los registros de cantidad y bulto están rellenos y se valida que si es peso variable 
		var numeroCantidadIsOk;
		if(flgPesoVariable=='S'){
			//Validación número decimal
			numeroCantidadIsOk = /^(-?\d+((\,|\.)\d{0,3}){0,1})?$/.test(cantidad);
		}else{
			//Validación número entero
			numeroCantidadIsOk = /^\d{0,12}$/.test(cantidad);
		}
		if (!numeroCantidadIsOk)
		{
			camposValidosValidacion=false;
		}
		var numeroBulto0IsOk = /^\d{0,2}$/.test(bulto);
		if (!numeroBulto0IsOk)
		{
			camposValidosValidacion=false;
		}
	}
	return camposValidosValidacion;
}

//Pinta de verde el link de stock si se ha modificado correctamente
function stockModificadoCorrectamente(){
	//Colorear link stock si se ha modificado correctamente
	var flgBienGuardado = document.getElementById("flgBienGuardado").value;
	if(flgBienGuardado == 'S'){
		var fila = document.getElementById("fila").value;
		var stockActual = document.getElementById("pda_p62_valorCampo_stock"+fila);
		stockActual.className += " linkVerde";
	}
}

function formateoPuntoPorComa(fila){
	var stckActual =  document.getElementById('pda_p62_valorCampo_stock'+fila);
	if (stckActual != null && typeof(stckActual) != 'undefined') {
		var numeroStk = stckActual.innerHTML;
		if(numeroStk % 1 != 0){
			stckActual.innerHTML = numeroStk.replace('.',',');
		}else{
			stckActual.innerHTML = parseFloat(numeroStk);
		}
	}

	var formato =  document.getElementById('pda_p62_valorCampo_formato'+fila);
	if (formato != null && typeof(formato) != 'undefined') {
		var numeroFormato = formato.innerHTML;

		var partes = numeroFormato.split("&nbsp;");
		var numero = partes[0];
		var tipo = partes[1];
		var tipoRef = partes[2];

		if(numero % 1 != 0){
			formato.innerHTML = numero.replace('.',',')+"&nbsp;"+tipo+"&nbsp;"+tipoRef.substring(0,4);
		}else{
			formato.innerHTML = parseFloat(numero)+"&nbsp;"+tipo+"&nbsp;"+tipoRef.substring(0,4);
		}
	}

	var dej =  document.getElementById('pda_p62_valorCampo_dej'+fila);
	if (dej != null && typeof(dej) != 'undefined') {
		var numeroDej = dej.innerHTML;

		if(numeroDej % 1 != 0){
			dej.innerHTML = numeroFormato.replace('.',',');
		}else{
			dej.innerHTML = parseFloat(numeroDej);
		}
	}
}

function events_p62_referencia(){
	var codArtCab =  document.getElementById('pda_p01_txt_infoRef');
	var selectProveedor =  document.getElementById('pda_p01_txt_proveedor');
	if (codArtCab != null && typeof(codArtCab) != 'undefined') {
		codArtCab.onkeydown = function (e) {
			e = e || window.event;
			var key = e.which || e.keyCode; //para soporte de todos los navegadores
			if (key == 13){//Tecla enter, calcular siguiente campo vacío
				if(e.preventDefault) {
					e.preventDefault();
				} else {
					e.returnValue = false;
				}
				document.getElementById("referenciaFiltro").value = document.getElementById("pda_p01_txt_infoRef").value;
				if (selectProveedor != null && typeof(selectProveedor) != 'undefined') {
					document.getElementById("proveedorFiltro").value = selectProveedor.value;
				}
				document.getElementById("accion").value = "Filtrar";
				document.getElementById("pdaP62RealizarDevolucionFinCampania").submit();

				e.stopPropagation();
			}
		}
		codArtCab.onchange = function () {
			document.getElementById("referenciaFiltro").value = document.getElementById("pda_p01_txt_infoRef").value;
			if (selectProveedor != null && typeof(selectProveedor) != 'undefined') {
				document.getElementById("proveedorFiltro").value = selectProveedor.value;
			}
			document.getElementById("accion").value = "Filtrar";
			document.getElementById("pdaP62RealizarDevolucionFinCampania").submit();
		}
		codArtCab.onmousedown = function (e) {
			document.getElementById("pda_p01_txt_infoRef").value;
			realizarAccionSiValidoFiltro(e);
		}
	}	
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	codArtCab = null;
}

function events_p62_proveedor(){
	var selectProveedor =  document.getElementById('pda_p01_txt_proveedor');
	if (selectProveedor != null && typeof(selectProveedor) != 'undefined') {
		selectProveedor.onchange = function () {
			document.getElementById("referenciaFiltro").value = document.getElementById("pda_p01_txt_infoRef").value;
			document.getElementById("proveedorFiltro").value = document.getElementById('pda_p01_txt_proveedor').value;
			document.getElementById("accion").value = "Filtrar";
			document.getElementById("pdaP62RealizarDevolucionFinCampania").submit();
		}
		selectProveedor.onmousedown = function (e) {
			realizarAccionSiValidoFiltro(e);
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	selectProveedor = null;
}

function events_p62_lineasCompletadas(){
	var lineasCompletadas =  document.getElementById('pda_p62_lineasCompletadas');
	if (lineasCompletadas != null && typeof(lineasCompletadas) != 'undefined') {
		lineasCompletadas.onclick = function () {
			realizarAccionSiValido("SiguienteVacio", "pdaP62RealizarDevolucionFinCampania");
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	lineasCompletadas = null;
}

function events_p62_paginacion(){
	var pagPrimera =  document.getElementById('pda_p62_lnk_first');
	var pagAnt =  document.getElementById('pda_p62_lnk_prev');
	var pagSig =  document.getElementById('pda_p62_lnk_next');
	var pagUltima =  document.getElementById('pda_p62_lnk_last');

	if (pagPrimera != null && typeof(pagPrimera) != 'undefined') {
		pagPrimera.onclick = function () {
			// Desactivar botones de navegación.
//			let enlaces = document.querySelectorAll(".paginacion");
//
//			console.log('LLEGO 0');
//				      			
//			enlaces.forEach(function(enlace) {
//				enlace.addEventListener("click", function() {
//					console.log('LLEGO 1');
//					document.getElementById("bloqueFlechas").style.pointerEvents = "none";
//					console.log('Desactivando botones de paginación');
//				});
//			});
			realizarAccionSiValido("PrimeraPagina", "pdaP62RealizarDevolucionFinCampania");
		}
	}
	if (pagAnt != null && typeof(pagAnt) != 'undefined') {
		pagAnt.onclick = function () {
			// Desactivar botones de navegación.
//			let enlaces = document.querySelectorAll(".paginacion");
//
//			console.log('LLEGO 0');
//				      			
//			enlaces.forEach(function(enlace) {
//				enlace.addEventListener("click", function() {
//					console.log('LLEGO 1');
//					document.getElementById("bloqueFlechas").style.pointerEvents = "none";
//					console.log('Desactivando botones de paginación');
//				});
//			});
			realizarAccionSiValido("AnteriorPagina", "pdaP62RealizarDevolucionFinCampania");
		}
	}
	if (pagSig != null && typeof(pagSig) != 'undefined') {
		pagSig.onclick = function () {
			// Desactivar botones de navegación.
//			let enlaces = document.querySelectorAll(".paginacion");
//
//			console.log('LLEGO 0');
//				      			
//			enlaces.forEach(function(enlace) {
//				enlace.addEventListener("click", function() {
//					console.log('LLEGO 1');
//					document.getElementById("bloqueFlechas").style.pointerEvents = "none";
//					console.log('Desactivando botones de paginación');
//				});
//			});
			realizarAccionSiValido("SiguientePagina", "pdaP62RealizarDevolucionFinCampania");
		}
	}
	if (pagUltima != null && typeof(pagUltima) != 'undefined') {
		pagUltima.onclick = function () {
			// Desactivar botones de navegación.
//			let enlaces = document.querySelectorAll(".paginacion");
//
//			console.log('LLEGO 0');
//				      			
//			enlaces.forEach(function(enlace) {
//				enlace.addEventListener("click", function() {
//					console.log('LLEGO 1');
//					document.getElementById("bloqueFlechas").style.pointerEvents = "none";
//					console.log('Desactivando botones de paginación');
//				});
//			});
			realizarAccionSiValido("UltimaPagina", "pdaP62RealizarDevolucionFinCampania");
		}
	}

	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	pagPrimera =  null;
	pagAnt =  null;
	pagSig =  null;
	pagUltima = null;
}

function events_p62_cant(fila){
	var cantidad =  document.getElementById('pda_p62_fld_stockDevuelto'+fila);

	puntoPorComaStockDevuelto(fila);

	if (cantidad != null && typeof(cantidad) != 'undefined') {
		cantidad.onfocus = function (e) {
			var cantidadValue = document.getElementById('pda_p62_fld_stockDevuelto'+fila).value;
			//Si la cantidad está rellena hay que rellenar el bulto
			if (cantidadValue != null && cantidadValue!=""){
				var variosBultosValue =document.getElementById('pda_p62_variosBultos').value;
				if(variosBultosValue == "false"){
					rellenarBultoSiVacio(fila);
				}
				
			}
		}
		cantidad.onblur = function(e) {
			e = e || window.event;
			var idActual = (e.target || e.srcElement).id;
			var validacionNavegacion = validacionCantidad(fila);
			
			if (validacionNavegacion=='N'){
				document.getElementById(idActual).focus();
				document.getElementById(idActual).select();
			}
			else{
				
				comaPorPuntoStockDevuelto(fila);
				//Formateo la cantidad con 3 decimales
				var stockDevueltoFormateado = "";
				//alert(document.getElementById(idActual).value);
				var numeroDoubleStockDevuelto = document.getElementById(idActual).value != "" ? parseFloat(document.getElementById(idActual).value.toString().replace(',','.')) : "";
				
				if (!isNaN(numeroDoubleStockDevuelto)){
					//Si no es un string vacio y es un número lo formateo con 3 decimales 
					//y con la coma como separador decimal. Si es un numero decimal. Si no, se deja como está
					if(numeroDoubleStockDevuelto % 1 != 0){
						stockDevueltoFormateado = numeroDoubleStockDevuelto.toFixed(3).replace(',','.');
						document.getElementById(idActual).value = stockDevueltoFormateado;
					}else{
						document.getElementById(idActual).value = numeroDoubleStockDevuelto;
					}
				}
				if(document.getElementById(idActual).value=='0'){
					document.getElementById(idActual).value=0;
					document.getElementById('tDevLineasLst0.bulto').value=0;
					document.getElementById("pda_p62_btn_cerrarBulto").style.display = "none";
					document.getElementById("pda_p62_lbl_error3").style.display = "none";
				}
				if(document.getElementById(idActual).value==''){
					document.getElementById('tDevLineasLst0.bulto').value='';
					document.getElementById("pda_p62_btn_cerrarBulto").style.display = "none";
					document.getElementById("pda_p62_lbl_error3").style.display = "none";
				}
				var bultoValue =  document.getElementById('tDevLineasLst0.bulto').value;
				if(bultoValue=='' || bultoValue==0){
					document.getElementById("pda_p62_btn_cerrarBulto").style.display = "none";
					document.getElementById("pda_p62_lbl_error3").style.display = "none";
				}else{
					if(esCentroParametrizado()){
						var isReadOnly=document.getElementById('pda_p62_fld_stockDevuelto0').readOnly;
						if(!isReadOnly){
							document.getElementById("pda_p62_btn_cerrarBulto").style.display = "block";
							document.getElementById("pda_p62_lbl_error3").style.display = "none";
						}
						
					}
					
				}
				rellenarBultoSiVacio(fila);
			}
			
			// Inicio MISUMI-259
			var cantidadObject = document.getElementById(idActual);
			var flgPesoVariable = document.getElementById("flgPesoVariable").value;
			var flgBandejas = document.getElementById("flgBandejas").value;

			// Declaro variable con el valor inicial del StockDevuelto
			// para poder posteriormente comprobar si se ha cambiado.
			var cantidadOrig = document.getElementById("stockDevuelto_orig" + fila).value;
			var cantidad = cantidadObject.value;

			if(cantidadOrig != cantidad && flgBandejas != 'S' && flgPesoVariable == 'S'
				&& cantidad != 0 && cantidad.indexOf(',') == -1){
				document.getElementById("pda_p62_lbl_error2").style.display = "block";

				//Volvemos a poner el valor original.
//				cantidadObject.value = document.getElementById("stockDevuelto_orig" + fila).value;
//				cantidadObject.focus();
//				cantidadObject.select();
			}else{
				document.getElementById("pda_p62_lbl_error2").style.display = "none";
				
			}
			// FIN MISUMI-259
			
		}
		cantidad.onkeydown = function (e) {
			e = e || window.event;
			var key = e.which || e.keyCode; //para soporte de todos los navegadores
			if (key == 13){//Tecla enter, calcular siguiente campo vacío
				if(e.preventDefault) {
					e.preventDefault();
				} else {
					e.returnValue = false;
				}
				var idActual = (e.target || e.srcElement).id;

				//Obtener si la cantidad es correcta. Será correcta si no se ha modificado o si se ha modificado con valores correctos
				var validacionNavegacion = validacionCantidad(fila);
				var validacionCantidadMaxima = validacionCantidadMaximaLin(idActual, fila);
				//Si el valor no se ha cambiado o se ha cambiado con datos correctos.
				if (validacionNavegacion=='S' && validacionBulto(fila) == 'S' && validacionCantidadMaxima == "S"){
					var cantidadObject = document.getElementById(idActual);
					var cantidadValue = cantidadObject.value;

					//Si el valor de cantidad es vacío y se pulsa enter, regresa al campo donde se introduce la referencia.En el
					//caso de haber borrado el dato y haberlo puesto vacío, guarda el cambio y va a referencia.
					//Si el valor de la cantidad no es vacío se mira si el valor de la cantidad es distinto de 0. Si es distinto
					//de cero, se copia el bulto en el caso de existir un bulto guardado y se coloca el cursor donde la referencia.
					//Si no existe ese bulto guardado, se coloca el cursor sobre el bulto. Si la cantidad es 0, rellena el bulto en 0
					//y se coloca en la referencia. 
					//Si es distinto de 0
					if (!isNaN(parseFloat(cantidadValue)) && parseFloat(cantidadValue) != 0){

						//Se rellena el bulto en el caso de ser vacío con la información de bulto guardada
						rellenarBultoSiVacio(fila);

						//Si el bulto es vacío se posiciona en el bulto. Si no, se ejecuta el formulario
						var bultoObject =  document.getElementById('tDevLineasLst'+fila+'.bulto');
						if (bultoObject.value == ""){
							bultoObject.focus();
						}
						else{
							comaPorPuntoStockDevuelto(fila);
							document.getElementById("accion").value = "Guardar";

							var referencia = document.getElementById("referenciaFiltro").value;
							if(referencia != null && referencia !=''){
								document.getElementById("referenciaFiltroRellenaYCantidadEnter").value = "S";						 			
							}
							if(esCentroParametrizado()){
								var isReadOnly=document.getElementById('pda_p62_fld_stockDevuelto0').readOnly;
								if(!isReadOnly){
									var listaBultos=document.getElementById('listaBultos').value;
									var aux = listaBultos.split(","); 
									var existebulto=false;
									var valorBultoComp=document.getElementById('tDevLineasLst0.bulto').value;
									for(var i = 0; i<aux.length && !existebulto ; i++){
										if(valorBultoComp==aux[i]){
											existebulto=true;
										}
									}
									if(!existebulto){
										document.getElementById('tDevLineasLst0.bulto').value=aux[0];
										document.getElementById("pda_p62_lbl_error3").style.display = "block";
										document.getElementById("pda_p62_btn_cerrarBulto").style.display = "none";
									}else{
										//Realizamos un submit
										document.getElementById("pdaP62RealizarDevolucionFinCampania").submit();
									}
								}
							}else{
								document.getElementById("pdaP62RealizarDevolucionFinCampania").submit();
							}
						}
					}
					else{
						comaPorPuntoStockDevuelto(fila);
						document.getElementById("accion").value = "Guardar";

						var realizarSubmit = true;
						//Si el bulto es vacío ponemos 0, si no mantenemos el valor que tenía.
						var idActual = 'tDevLineasLst'+(fila)+'.bulto';
						var bultoObject = document.getElementById(idActual);
						if(bultoObject.value == ""){
							if(cantidadValue == "0"){
								bultoObject.value = "0";								
							}else if(cantidadValue == ""){
								bultoObject.focus();
								realizarSubmit = false;
							}
						}else{
							bultoObject.value = "0";
						}

						//Si la cantidad es vacía y el bulto es vacío colocarse en el bulto y no hacer submit.
						if(realizarSubmit){
							var referencia = document.getElementById("referenciaFiltro").value;
							if(referencia != null && referencia !=''){
								document.getElementById("referenciaFiltroRellenaYCantidadEnter").value = "S";						 			
							}							
							if(esCentroParametrizado()){
								var isReadOnly=document.getElementById('pda_p62_fld_stockDevuelto0').readOnly;
								if(!isReadOnly){
									var listaBultos=document.getElementById('listaBultos').value;
									var aux = listaBultos.split(","); 
									var existebulto=false;
									var valorBultoComp=document.getElementById('tDevLineasLst0.bulto').value;
									if(bultoObject.value == "0"){
										existebulto=true;
									}
									for(var i = 0; i<aux.length && !existebulto ; i++){
										if(valorBultoComp==aux[i]){
											existebulto=true;
										}
									}
									if(!existebulto){
										document.getElementById('tDevLineasLst0.bulto').value=aux[0];
										document.getElementById("pda_p62_lbl_error3").style.display = "block";
										document.getElementById("pda_p62_btn_cerrarBulto").style.display = "none";
									}else{
										//Realizamos un submit
										document.getElementById("pdaP62RealizarDevolucionFinCampania").submit();
									}
								}
							}else{
								document.getElementById("pdaP62RealizarDevolucionFinCampania").submit();
							}
						}
					}
				}
			}
		}
		cantidad.onclick = function(e) {
			var variosBultosPantalla = document.getElementById("pda_p62_variosBultos").value;
			if(variosBultosPantalla=="false"){
				clickStockDevueltoP62(fila);
			}else{
				var campoActualCantMax = document.getElementById('pda_p62_valorCampo_cantMax');
				var valorCantidadMax = campoActualCantMax != null ? campoActualCantMax.innerText.replace(',','.') : "";
				//Si cantidad max tiene valor y el número de cantidad es valido.
				if(valorCantidadMax=="" || (stockPantallaAntesNavegar <= parseFloat(valorCantidadMax))){
					document.getElementById("pda_p62_lbl_error").style.display = "none";
					var stockDevueltoPantalla = document.getElementById('pda_p62_fld_stockDevuelto0').value;
					var stockDevueltoOrigPantalla = document.getElementById("stockDevuelto_orig0").value;
					var bultoOrigPantalla = document.getElementById("bulto_orig0").value;
					var codArtPantalla = document.getElementById('codArticulo').value;
					var devolucionPantalla = document.getElementById('devolucion').value;
					var estadoCerradoPantalla = document.getElementById('pda_p62_estadoCerrado').value;
					var origenPantalla = document.getElementById('origenPantalla').value;
					var selProv = document.getElementById('selProv').value;
					window.location.href = "./pdaP103VariosBultosFinCampana.do?codArt="+codArtPantalla
											+"&devolucionPantalla="+devolucionPantalla
											+"&stockDevueltoPantalla="+stockDevueltoPantalla
											+"&stockDevueltoOrigPantalla="+stockDevueltoOrigPantalla
											+"&bultoPantallaOrig="+bultoOrigPantalla
											+"&variosBultosPantalla="+variosBultosPantalla
											+"&estadoCerradoPantalla="+estadoCerradoPantalla
											+"&origenPantalla="+origenPantalla
											+"&selectProv="+selProv;

				}else{
					stockPantallaAntesNavegar=document.getElementById("stockDevuelto_orig0").value;
				}
			
			}
			
		}

		cantidad.onchange = function(e) {
			e = e || window.event;

			var idActual = (e.target || e.srcElement).id;

			var campoActualCantidad = document.getElementById(idActual);
			var valorCantidad = campoActualCantidad.value.replace(',','.');
			stockPantallaAntesNavegar=valorCantidad;
			var idCantMax = 'pda_p62_valorCampo_cantMax';
			var campoActualCantMax = document.getElementById(idCantMax);
			var valorCantidadMax = campoActualCantMax != null ? campoActualCantMax.innerText.replace(',','.') : "";

			//Si cantidad max tiene valor y el número de cantidad es valido.
			if(valorCantidadMax != "" && validacionCantidad(fila) == "S"){
				if(parseFloat(valorCantidad) > parseFloat(valorCantidadMax)){
					document.getElementById("pda_p62_lbl_error").style.display = "block";
					

					//Volvemos a poner el valor original.
					campoActualCantidad.value = document.getElementById("stockDevuelto_orig" + fila).value;
					campoActualCantidad.focus();
					campoActualCantidad.select();
					
					//Si el CAN es incorrecto ponemos este flag a false para que no deje paginar, finalizar, pulsar stock, foto, etc.
					cantMaxValida = false;
				}else{
					document.getElementById("pda_p62_lbl_error").style.display = "none";
					
				}
			}
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	cantidad =  null;
}

function events_p62_bulto(fila){
	var bulto =  document.getElementById('tDevLineasLst'+fila+'.bulto');

	if (bulto != null && typeof(bulto) != 'undefined') {
		bulto.onblur = function(e) {
			e = e || window.event;
			var idActual = (e.target || e.srcElement).id;
			var validacionNavegacion = validacionBulto(fila);
			if (validacionNavegacion=='N'){
				document.getElementById(idActual).focus();
				document.getElementById(idActual).select();
			}else{
				var bultoValue=document.getElementById(idActual).value;
				if(bultoValue=='' || bultoValue==0){
					document.getElementById("pda_p62_btn_cerrarBulto").style.display = "none";
					document.getElementById("pda_p62_lbl_error3").style.display = "none";
				}else{
					if(esCentroParametrizado()){
						var isReadOnly=document.getElementById('pda_p62_fld_stockDevuelto0').readOnly;
						if(!isReadOnly){
							var listaBultos=document.getElementById('listaBultos').value;
							var aux = listaBultos.split(","); 
							var existebulto=false;
							var valorBultoComp=document.getElementById('tDevLineasLst0.bulto').value;
							for(var i = 0; i<aux.length && !existebulto ; i++){
								if(valorBultoComp==aux[i]){
									existebulto=true;
								}
							}
							if(!existebulto){
								document.getElementById('tDevLineasLst0.bulto').value=aux[0];
								document.getElementById("pda_p62_lbl_error3").style.display = "block";
							}else{
								document.getElementById("pda_p62_lbl_error3").style.display = "none";
							}
							var stockDevueltoPantalla=document.getElementById('pda_p62_fld_stockDevuelto0').value;
							if(stockDevueltoPantalla!=''){
								document.getElementById("pda_p62_btn_cerrarBulto").style.display = "block";
							}
						}
					}
				}
			}
		}
//		bulto.onfocus = function (e) {
//			rellenarBultoSiVacio(fila);
//		}
		bulto.onkeydown = function (e) {
			e = e || window.event;
			var key = e.which || e.keyCode; //para soporte de todos los navegadores
			if (key == 13){//Tecla enter, calcular siguiente campo vacío
				if(e.preventDefault) {
					e.preventDefault();
				} else {
					e.returnValue = false;
				}
				var idActual = (e.target || e.srcElement).id;
				var validacionNavegacion = validacionBulto(fila);
				var idStockActual = 'pda_p62_fld_stockDevuelto'+fila;
				var validacionCantidadMaxima = validacionCantidadMaximaLin(idStockActual, fila);
				if (validacionNavegacion=='S' && validacionCantidad(fila) == 'S' && validacionCantidadMaxima == "S"){
					comaPorPuntoStockDevuelto(fila);
					document.getElementById("accion").value = "Guardar";

					var referencia = document.getElementById("referenciaFiltro").value;
					if(referencia != null && referencia !=''){
						document.getElementById("referenciaFiltroRellenaYCantidadEnter").value = "S";						 			
					}

					//Miramos si se ha introducido un bulto distinto para guardar su valor en el proveedor
					var bultoOri = document.getElementById("bulto_orig"+fila).value;
					var bultoActual = document.getElementById('tDevLineasLst'+fila+'.bulto').value;

					if(bultoOri != bultoActual){
						//Si el nuevo bulto introducido es vacío o cero, se deja el valor anterior.
						if(bultoActual != 0 && bultoActual != ""){
							//Obtener el valor del proveedor de la línea de devolución
							var numeroProveedor = document.getElementById('proveedorDevolucionLinea'+fila).value;
							var partes = document.getElementById(numeroProveedor).value.split("*");
							document.getElementById(numeroProveedor).value = partes[0] + "*" + bultoActual;
						}
					}
					if(esCentroParametrizado()){
						var isReadOnly=document.getElementById('pda_p62_fld_stockDevuelto0').readOnly;
						if(!isReadOnly){
							var listaBultos=document.getElementById('listaBultos').value;
							var aux = listaBultos.split(","); 
							var existebulto=false;
							var valorBultoComp=document.getElementById('tDevLineasLst0.bulto').value;
							if(bultoOri == "0"){
								existebulto=true;
							}
							for(var i = 0; i<aux.length && !existebulto ; i++){
								if(valorBultoComp==aux[i]){
									existebulto=true;
								}
							}
							if(!existebulto){
								document.getElementById('tDevLineasLst0.bulto').value=aux[0];
								document.getElementById("pda_p62_lbl_error3").style.display = "block";
								document.getElementById("pda_p62_btn_cerrarBulto").style.display = "none";
							}else{
								document.getElementById("pdaP62RealizarDevolucionFinCampania").submit();
							}
						}
					}else{
						document.getElementById("pdaP62RealizarDevolucionFinCampania").submit();
					}
				}
			}
		}
		bulto.onclick = function(e){
			var variosBultosPantalla = document.getElementById("pda_p62_variosBultos").value;
			if(variosBultosPantalla=="false"){
				clickBultoP62(fila);
			}else{
				var campoActualCantMax = document.getElementById('pda_p62_valorCampo_cantMax');
				var valorCantidadMax = campoActualCantMax != null ? campoActualCantMax.innerText.replace(',','.') : "";
				//Si cantidad max tiene valor y el número de cantidad es valido.
				if(valorCantidadMax=="" || (stockPantallaAntesNavegar <= parseFloat(valorCantidadMax))){
					document.getElementById("pda_p62_lbl_error").style.display = "none";
					var stockDevueltoPantalla = document.getElementById('pda_p62_fld_stockDevuelto0').value;
					var stockDevueltoOrigPantalla = document.getElementById("stockDevuelto_orig0").value;
					var bultoOrigPantalla = document.getElementById("bulto_orig0").value;
					var codArtPantalla = document.getElementById('codArticulo').value;
					var devolucionPantalla = document.getElementById('devolucion').value;
					var estadoCerradoPantalla = document.getElementById('pda_p62_estadoCerrado').value;
					var origenPantalla = document.getElementById('origenPantalla').value;
					var selProv = document.getElementById('selProv').value;
					window.location.href = "./pdaP103VariosBultosFinCampana.do?codArt="+codArtPantalla
											+"&devolucionPantalla="+devolucionPantalla
											+"&stockDevueltoPantalla="+stockDevueltoPantalla
											+"&stockDevueltoOrigPantalla="+stockDevueltoOrigPantalla
											+"&bultoPantallaOrig="+bultoOrigPantalla
											+"&variosBultosPantalla="+variosBultosPantalla
											+"&estadoCerradoPantalla="+estadoCerradoPantalla
											+"&origenPantalla="+origenPantalla
											+"&selectProv="+selProv;

				}else{
					stockPantallaAntesNavegar=document.getElementById("stockDevuelto_orig0").value;
				}
			
			
			}
		}
		bulto.onchange = function(e) {
			e = e || window.event;
			var idActual = (e.target || e.srcElement).id;
			var validacionNavegacion = validacionBulto(fila);
			if (validacionNavegacion=='S'){		
				var bultoActual = document.getElementById('tDevLineasLst'+fila+'.bulto').value;
				//Si el nuevo bulto introducido es vacío o cero, se deja el valor anterior.
				if(bultoActual != 0 && bultoActual != ""){
					//Obtener el valor del proveedor de la línea de devolución
					var numeroProveedor = document.getElementById('proveedorDevolucionLinea'+fila).value;
					var partes = document.getElementById(numeroProveedor).value.split("*");
					document.getElementById(numeroProveedor).value = partes[0] + "*" + bultoActual;
				}
			}
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	bulto = null;
}

function rellenarBultoSiVacio(fila){
	var idActual = 'tDevLineasLst'+(fila)+'.bulto';
	var bultoObject = document.getElementById(idActual);
	var bultoValue = bultoObject.value;

	var cantidadValue = document.getElementById('pda_p62_fld_stockDevuelto'+fila).value;
	if ((bultoValue == "" || bultoValue == "0") && cantidadValue != "" && cantidadValue != "0"){
		//Si está parametrizado hay que cargar el primer bulto abierto si lo hubiera
		var centroParametrizado=document.getElementById('centroParametrizado').value;
		if(centroParametrizado=='false'){
			//Obtenemos el número de proveedor de la línea de devolución del bulto que nos hemos situado
			var numProvr = document.getElementById("proveedorDevolucionLinea"+fila).value;
	
			//Conseguimos numeroProveedor*Bulto del campo oculto que guarda los bultos por proveedor
			var provrBulto = document.getElementById(numProvr).value;
	
			//Sacamos el bulto del proveedor
			var partes = provrBulto.split("*");
			var numBultoACopiar = partes[1];
	
			//Asignamos al bulto el valor del bulto del proveedor guardado
			bultoObject.value = numBultoACopiar;
		}else{
			//Si está parametrizado hay que cargar el primer bulto abierto si lo hubiera
			var listaBultos=document.getElementById('listaBultos').value;
			var aux = listaBultos.split(","); 
			bultoObject.value = aux[0];
		}
	}else if(cantidadValue == "0"){
		bultoObject.value = "0";
	}	
}

function events_p62_guardar(){
	var btn_save =  document.getElementById('pda_p62_btn_save');

	if (btn_save != null && typeof(btn_save) != 'undefined') {
		btn_save.onclick = function () {
			realizarAccionSiValido("Guardar", "pdaP62RealizarDevolucionFinCampania");
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	btn_save = null;
}

function events_p62_finalizar(){
	var btn_fin =  document.getElementById('pda_p62_btn_fin');

	if (btn_fin != null && typeof(btn_fin) != 'undefined') {
		btn_fin.onclick = function () {
			realizarAccionSiValido("Finalizar", "pdaP62RealizarDevolucionFinCampania");
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	btn_fin = null;
}

function events_p62_cerrarBulto(){
	document.getElementById("pda_p62_estadoCerrado").value ='S'  ;
	realizarAccionSiValido("CerrarBulto", "pdaP62RealizarDevolucionFinCampania");
}

function events_p62_titulo(){
	var tituloLink =  document.getElementById('pda_p62_titulo');

	if (tituloLink != null && typeof(tituloLink) != 'undefined') {
		tituloLink.onclick = function () {
			realizarAccionSiValido("VerCabecera", "pdaP62RealizarDevolucionFinCampania");
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	tituloLink = null;
}

function events_p62_foto(){
	var foto =  document.getElementById('pda_p62_img_referencia');

	if (foto != null && typeof(foto) != 'undefined') {
		foto.onclick = function () {
			realizarAccionSiValido("VerFoto", "pdaP62RealizarDevolucionFinCampania");
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	foto = null;
}

function events_p62_stock(fila){
	var stockLink =  document.getElementById('pda_p62_valorCampo_stock'+fila);

	if (stockLink != null && typeof(stockLink) != 'undefined') {
		stockLink.onclick = function () {
			document.getElementById("fila").value = fila;
			realizarAccionSiValido("StockLink", "pdaP62RealizarDevolucionFinCampania");
		}
	}	
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	stockLink = null;
}

function events_p62_stockDevuelto(fila){
	var stockDevueltoLink =  document.getElementById('pda_p62_fld_stockDevuelto'+fila);

	if (stockDevueltoLink != null && typeof(stockDevueltoLink) != 'undefined') {
		stockDevueltoLink.onclick = function () {
			var isReadOnly=document.getElementById('pda_p62_fld_stockDevuelto0').readOnly;
			if(!isReadOnly){
				document.getElementById("fila").value = fila;
				realizarAccionSiValido("StockDevueltoLink", "pdaP62RealizarDevolucionFinCampania");
			}
		}
	}	
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	stockDevueltoLink = null;
}

function events_p62_detalleTextilLink(){
	var textilLink = document.getElementById('pda_p62_modelo_proveedor');
	if (textilLink) {
		textilLink.onclick = function () {
			realizarAccionSiValido("MasInfo", "pdaP62RealizarDevolucionFinCampania");
		}
	}
	textilLink = document.getElementById('pda_p62_talla');
	if (textilLink) {
		textilLink.onclick = function () {
			realizarAccionSiValido("MasInfo", "pdaP62RealizarDevolucionFinCampania");
		}
	}
	textilLink = document.getElementById('pda_p62_color');
	if (textilLink) {
		textilLink.onclick = function () {
			realizarAccionSiValido("MasInfo", "pdaP62RealizarDevolucionFinCampania");
		}
	}
	textilLink = null;
}


function posInicialCursorP62(){
	var referenciaFiltroRellenaYCantidadPulsada =  document.getElementById("referenciaFiltroRellenaYCantidadEnter").value;
	var flgBandejas = document.getElementById("flgBandejas").value;
	if(referenciaFiltroRellenaYCantidadPulsada != 'S'){
		var referencia = document.getElementById("referenciaFiltro").value;
		var cantidadLabel = document.getElementById("pda_p62_fld_stockDevuelto0_label");

		//Para ver si se ha pulsado una flecha
		var accionPrevia = document.getElementById("accionAnterior").value;
		if(referencia != null && referencia !='' && (cantidadLabel == null || typeof(cantidadLabel) == 'undefined')){
			var filaPrimerElementoVacio = document.getElementById("primerElementoBultoFila").value;
			var fld_stockDevuelto = document.getElementById("pda_p62_fld_stockDevuelto"+filaPrimerElementoVacio);
			if(fld_stockDevuelto != null && typeof(fld_stockDevuelto) != 'undefined' && !(flgBandejas == 'S')) {
				fld_stockDevuelto.focus();
				fld_stockDevuelto.select();
			}
			document.getElementById("pda_p01_txt_infoRef").value = '';

			//Cuando se busca por un filtro, se coloca el cursor en stockDevuelto(CAN) y se borra el filtro introducido.
			//Si no se realizara esto, al paginar, buscaría por este filtro y no queremos que eso ocurra.
			document.getElementById("referenciaFiltro").value = '';
		}else if(accionPrevia != null &&  accionPrevia !=''  && (accionPrevia == 'UltimaPagina' || accionPrevia == 'PrimeraPagina' || accionPrevia == 'AnteriorPagina' || accionPrevia == 'SiguientePagina')){
			//Si se ha pulsado alguna de las flechas entra aquí
			var fld_stockDevuelto = document.getElementById("pda_p62_fld_stockDevuelto0");
			if(fld_stockDevuelto != null && typeof(fld_stockDevuelto) != 'undefined' && !(flgBandejas == 'S')) {
				var variosBultosPantalla = document.getElementById("pda_p62_variosBultos").value;
				if(variosBultosPantalla == "false"){
					fld_stockDevuelto.focus();
				}
				
				//fld_stockDevuelto.select();
			}
		}else{
			//Entra aquí si no se ha buscado una referencia, no se ha pulsado ninguna flecha. Un caso puede ser nada más entrar en una
			//devolución. Se situa el cursor en búsqueda de referencia.
			document.getElementById("pda_p01_txt_infoRef").value = '';
			document.getElementById("pda_p01_txt_infoRef").focus();
		}				
	}else{
		document.getElementById("referenciaFiltroRellenaYCantidadEnter").value = "";	
		document.getElementById("pda_p01_txt_infoRef").value = '';
		document.getElementById("pda_p01_txt_infoRef").focus();
	}
}

//Si la cantidad no se ha modificado, devuelve true. Si se ha modificado, mira que tenga un valor numérico correcto y si lo tiene devuelve true. Si no colorea
//la caja de rojo y devuelve false.
function validacionCantidad(fila,pintarRojoCantidad){
	var resultadoValidacion = 'S';
	var id = 'pda_p62_fld_stockDevuelto'+fila;
	var campoActual = document.getElementById(id);
	var variosBultosPantalla = document.getElementById("pda_p62_variosBultos").value;
	if(variosBultosPantalla == "false"){
		if (campoActual != null && typeof(campoActual) != 'undefined') {
			if ( cantidadModificada(fila) ){
				pintarRojoCantidad = (typeof pintarRojoCantidad !== 'undefined' ? pintarRojoCantidad : true);
				var flgPesoVaribalePantalla=document.getElementById("flgPesoVariable").value;
				var numeroCantidadIsOk;
				if(flgPesoVaribalePantalla=='S'){
					//Validación número decimal
					numeroCantidadIsOk = /^(-?\d+((\,|\.)\d{0,3}){0,1})?$/.test(campoActual.value);
				}else{
					//Validación número entero
					numeroCantidadIsOk = /^\d*$/.test(campoActual.value);
				}
				if (!numeroCantidadIsOk){
					//Pintamos de rojo
					if (pintarRojoCantidad){
						campoActual.className = campoActual.className + " inputCantidadResaltado";
					}
					resultadoValidacion = 'N';
				}
				else
				{
					//Quitamos el rojo del campo.
					campoActual.className = campoActual.className.replace(/inputCantidadResaltado/g,'');
					resultadoValidacion = 'S';
				}
			}
		}
	}
	return resultadoValidacion;
}

function validacionBulto(fila){
	var id = 'tDevLineasLst'+fila+'.bulto';
	var resultadoValidacion = 'S';
	var campoActual = document.getElementById(id);
	var variosBultosPantalla = document.getElementById("pda_p62_variosBultos").value;
	if(variosBultosPantalla == "false"){
		if (campoActual != null && typeof(campoActual) != 'undefined') {
			var numeroIntegerIsOk = /^\d{0,2}$/.test(campoActual.value);
			if (!numeroIntegerIsOk){
				//Pintamos de rojo el campo.
				campoActual.className = campoActual.className + " inputBultoResaltado";
				resultadoValidacion = 'N';
			}else{
				//Quitamos el rojo del campo.
				campoActual.className = campoActual.className.replace(/inputBultoResaltado/g,'');
				resultadoValidacion = 'S';
			}
		}
	}
	return resultadoValidacion;
}

function comaPorPuntoStockDevuelto(fila){
	var flgBandejas = document.getElementById("flgBandejas").value;
	var campoActual = document.getElementById('tDevLineasLst'+fila+'.stockDevuelto');

// Inicio MISUMI-295
//	if (campoActual != null && typeof(campoActual) != 'undefined' && !(flgBandejas == 'S')) {
//		document.getElementById('tDevLineasLst'+fila+'.stockDevuelto').value = document.getElementById('pda_p62_fld_stockDevuelto'+fila).value.replace(',','.');
//	}

	if (campoActual != null && typeof(campoActual) != 'undefined') {
		document.getElementById('tDevLineasLst'+fila+'.stockDevuelto').value = document.getElementById('pda_p62_fld_stockDevuelto'+fila).value;
		document.getElementById('tDevLineasLst'+fila+'.stockDevuelto').value = document.getElementById('tDevLineasLst'+fila+'.stockDevuelto').value.replace(',','.');
	}
// FIN MISUMI-295

}

function puntoPorComaStockDevuelto(fila){
	var campoActual = document.getElementById('tDevLineasLst'+fila+'.stockDevuelto');
	var campoActualLabel = document.getElementById('pda_p62_fld_stockDevuelto'+fila+'_label');
	if (campoActual != null && typeof(campoActual) != 'undefined') {
		var stockDevueltoFormateado = "";
		var numeroDoubleStockDevuelto = parseFloat(document.getElementById('tDevLineasLst'+fila+'.stockDevuelto').value.replace(',','.'));
		if (!isNaN(numeroDoubleStockDevuelto)){
			//Si no es un string vacio y es un número lo formateo con 3 decimales 
			//y con la coma como separador decimal.
			if(numeroDoubleStockDevuelto % 1 != 0){
				stockDevueltoFormateado = numeroDoubleStockDevuelto.toFixed(3).replace(',','.');
			}else{
				stockDevueltoFormateado = numeroDoubleStockDevuelto;
			}
		}
		document.getElementById('pda_p62_fld_stockDevuelto'+fila).value = stockDevueltoFormateado;
		if (campoActualLabel != null && typeof(campoActualLabel) != 'undefined') {
			campoActualLabel.innerHTML = stockDevueltoFormateado;
		}
		document.getElementById('stockDevuelto_orig'+fila).value = stockDevueltoFormateado;
	}	
}

function realizarAccionSiValido(accion, pagina){
	var campoCantidad = document.getElementById('pda_p62_fld_stockDevuelto0');
	var campoCantidad2 = document.getElementById('pda_p62_fld_stockDevuelto1');
	var cantidad = "";
	var cantidad2 = "";
	if (campoCantidad != null && typeof(campoCantidad) != 'undefined') {
		cantidad = document.getElementById('pda_p62_fld_stockDevuelto0').value;
	}
	if (campoCantidad2 != null && typeof(campoCantidad2) != 'undefined') {
		cantidad2 = document.getElementById('pda_p62_fld_stockDevuelto1').value;
	}
	var validacionNavegacion = (validacionCantidad(0,false) == 'S' && validacionCantidad(1,false) == 'S');
	if ((cantidad == "" && cantidad2 == "") || validacionNavegacion){
		validacionNavegacion = (validacionBulto(0)=='S' && validacionBulto(1)=='S');
		if (validacionNavegacion){
			if(cantMaxValida){
				if (campoCantidad != null && typeof(campoCantidad) != 'undefined') {
					comaPorPuntoStockDevuelto(0);
				}
				if (campoCantidad2 != null && typeof(campoCantidad2) != 'undefined') {
					comaPorPuntoStockDevuelto(1);
				}
				document.getElementById("accion").value = accion;
				document.getElementById("pda_p62_btn_fin").style.display = "none";
				document.getElementById(pagina).submit();
			}else{
				//Una vez no ha dejado pulsar foto, paginar, pulsar stock, etc ponemos el flag a true para que si vuelve a clicar
				//cualquiera de esos elementos si deje realizar la accion.
				cantMaxValida = true;
			}
		}
		else{
			if (validacionBulto(0)!='S'){
				document.getElementById('tDevLineasLst0.bulto').focus();
				document.getElementById('tDevLineasLst0.bulto').select();
			}else{
				document.getElementById('tDevLineasLst1.bulto').focus();
				document.getElementById('tDevLineasLst1.bulto').select();
			}
			return false;
		}
	}
	else{
		if(validacionCantidad(0,false) != 'S'){
			document.getElementById('pda_p62_fld_stockDevuelto0').focus();
			document.getElementById('pda_p62_fld_stockDevuelto0').select();
		}else{
			document.getElementById('pda_p62_fld_stockDevuelto1').focus();
			document.getElementById('pda_p62_fld_stockDevuelto1').select();
		}
		return false;
	}
}

//Para que no deje usar el combobox si cantidad o bulto están en formato incorrecto
function realizarAccionSiValidoFiltro(e){
	var campoCantidad = document.getElementById('pda_p62_fld_stockDevuelto0');
	var campoCantidad2 = document.getElementById('pda_p62_fld_stockDevuelto1');
	var cantidad = "";
	var cantidad2 = "";
	if (campoCantidad != null && typeof(campoCantidad) != 'undefined') {
		cantidad = document.getElementById('pda_p62_fld_stockDevuelto0').value;
	}
	if (campoCantidad2 != null && typeof(campoCantidad2) != 'undefined') {
		cantidad2 = document.getElementById('pda_p62_fld_stockDevuelto1').value;
	}
	var validacionNavegacion = (validacionCantidad(0,true) == 'S' && validacionCantidad(1,true) == 'S');
	if ((cantidad == "" && cantidad2 == "") || validacionNavegacion){
		validacionNavegacion = (validacionBulto(0)=='S' && validacionBulto(1)=='S');
		if (!validacionNavegacion){
			e.preventDefault();
			if (validacionBulto(0)!='S'){
				document.getElementById('tDevLineasLst0.bulto').focus();
				document.getElementById('tDevLineasLst0.bulto').select();
			}else{
				document.getElementById('tDevLineasLst1.bulto').focus();
				document.getElementById('tDevLineasLst1.bulto').select();
			}
			return false;
		}
	}
	else{
		e.preventDefault();
		if(validacionCantidad(0,false) != 'S'){
			document.getElementById('pda_p62_fld_stockDevuelto0').focus();
			document.getElementById('pda_p62_fld_stockDevuelto0').select();
		}else{
			document.getElementById('pda_p62_fld_stockDevuelto1').focus();
			document.getElementById('pda_p62_fld_stockDevuelto1').select();
		}
		return false;
	}
}

//Función que mira si la cantidad actual y la original son diferentes, tanto si es un entero como si es un numero decimal
function cantidadModificada(fila){
	var cantidadOriginal = document.getElementById('stockDevuelto_orig'+fila).value;
	var cantidadActual = document.getElementById('pda_p62_fld_stockDevuelto'+fila).value;
	var cantidadActualIsNumberDouble = /^(-?\d+((\,|\.)\d{0,3}){0,1})?$/.test(cantidadActual);

	var flgBandejas = document.getElementById("flgBandejas").value;

	if ((cantidadActualIsNumberDouble) && !(flgBandejas == 'S')) {
		return parseFloat( cantidadOriginal.replace(',','.') ) != parseFloat( cantidadActual.replace(',','.') );
	}
	else{
		return cantidadOriginal != cantidadActual;
	}
}

function clickBultoP62(fila){
	var fld_bulto = document.getElementById("tDevLineasLst"+fila+".bulto");
	if (fld_bulto != null && typeof(fld_bulto) != 'undefined') {
		fld_bulto.focus();
		fld_bulto.select();
	}	
}

function navegarVariosBultos(){
	var variosBultosPantalla = document.getElementById("pda_p62_variosBultos").value;
	var campoActualCantMax = document.getElementById('pda_p62_valorCampo_cantMax');
	var valorCantidadMax = campoActualCantMax != null ? campoActualCantMax.innerText.replace(',','.') : "";
	//Si cantidad max tiene valor y el número de cantidad es valido.
	if(valorCantidadMax=="" || (stockPantallaAntesNavegar <= parseFloat(valorCantidadMax))){
		document.getElementById("pda_p62_lbl_error").style.display = "none";
		var stockDevueltoPantalla = document.getElementById('pda_p62_fld_stockDevuelto0').value;
		var stockDevueltoOrigPantalla = document.getElementById("stockDevuelto_orig0").value;
		var bultoOrigPantalla = document.getElementById("bulto_orig0").value;
		var codArtPantalla = document.getElementById('codArticulo').value;
		var devolucionPantalla = document.getElementById('devolucion').value;
		var estadoCerradoPantalla = document.getElementById('pda_p62_estadoCerrado').value;
		var origenPantalla = document.getElementById('origenPantalla').value;
		var selProv = document.getElementById('selProv').value;
		window.location.href = "./pdaP103VariosBultosFinCampana.do?codArt="+codArtPantalla
								+"&devolucionPantalla="+devolucionPantalla
								+"&stockDevueltoPantalla="+stockDevueltoPantalla
								+"&stockDevueltoOrigPantalla="+stockDevueltoOrigPantalla
								+"&bultoPantallaOrig="+bultoOrigPantalla
								+"&variosBultosPantalla="+variosBultosPantalla
								+"&estadoCerradoPantalla="+estadoCerradoPantalla
								+"&origenPantalla="+origenPantalla
								+"&selectProv="+selProv;

	}else{
		stockPantallaAntesNavegar=document.getElementById("stockDevuelto_orig0").value;
	}



}

function clickStockDevueltoP62(fila){
	var fld_stockDevuelto = document.getElementById("pda_p62_fld_stockDevuelto"+fila);
	if (fld_stockDevuelto != null && typeof(fld_stockDevuelto) != 'undefined') {
		fld_stockDevuelto.focus();
		fld_stockDevuelto.select();
	}	
}

function validacionCantidadMaximaLin(idActual, fila){
	var campoActualCantidad = document.getElementById(idActual);
	var valorCantidad = campoActualCantidad.value.replace(',','.');

	var idCantMax = 'pda_p62_valorCampo_cantMax';
	var campoActualCantMax = document.getElementById(idCantMax);
	var valorCantidadMax = campoActualCantMax != null ? campoActualCantMax.innerText.replace(',','.') : "";

	//Si cantidad max tiene valor y el número de cantidad es valido.
	if(valorCantidadMax != "" && validacionCantidad(fila) == "S"){
		if(parseFloat(valorCantidad) > parseFloat(valorCantidadMax)){
			document.getElementById("pda_p62_lbl_error").style.display = "block";
			

			//Volvemos a poner el valor original.
			campoActualCantidad.value = document.getElementById("stockDevuelto_orig" + fila).value;
			campoActualCantidad.focus();
			campoActualCantidad.select();

			return "N";
		}else{
			document.getElementById("pda_p62_lbl_error").style.display = "none";
			

			return "S";
		}
	}
	return "S";
}

