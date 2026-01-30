'use strict';
var stockPantallaAntesNavegar;
function initializeScreen_p63(){
	stockModificadoCorrectamente();

	formateoPuntoPorComa();

	//Eventos de cabecera
	events_p63_referencia();
	events_p63_proveedor();

	events_p63_lineasCompletadas();
	events_p63_paginacion();
	events_p63_cant();
	events_p63_bulto();
	events_p63_guardar();
	events_p63_finalizar();
	events_p63_titulo();
	events_p63_stock();
	
// Inicio MISUMI-295
//	var flgBandejas = document.getElementById("flgBandejas").value;
//	if(flgBandejas == 'S'){
	var flgBandejas = document.getElementById("flgBandejas").value;
	var flgPesoVariable = document.getElementById("flgPesoVariable").value;
	if(flgBandejas == 'S' && flgPesoVariable == 'S'){
// FIN MISUMI-295
		events_p63_stockDevuelto();	
	}
	
	posInicialCursorP63();

	events_p63_caducidadLoteLink();
	events_p63_detalleTextilLink();
	events_p63_variosBultos();
	stockPantallaAntesNavegar=document.getElementById('pda_p63_fld_stockDevuelto').value;
	//events_p63_validarStock();
	events_p63_validarEstado();
	events_p63_mostrarFinalizaDevolucion();
}

function events_p63_mostrarFinalizaDevolucion(){
	var devolucionFinalizada =  document.getElementById('pda_p63_devolucionFinalizada').value;
	if(devolucionFinalizada=='SI' || devolucionFinalizada==''){
		document.getElementById('pda_p63_btn_fin').style.display = "block";
	}else{
		document.getElementById('pda_p63_btn_fin').style.display = "none";
	}
}


function events_p63_validarStock(){
	var stckActual =  document.getElementById('pda_p63_valorCampo_stock');
	var numeroStk = stckActual.innerHTML;
	if(numeroStk == 0){
		var stockDevueltoPantalla =  document.getElementById('pda_p63_fld_stockDevuelto').value;
		var bultoPantalla =  document.getElementById('pda_p63_fld_bulto').value;
		if(stockDevueltoPantalla=='' && bultoPantalla==''){
			document.getElementById('pda_p63_fld_stockDevuelto').value=0;
			document.getElementById('pda_p63_fld_bulto').value=0;
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

function events_p63_validarEstado(){
	var stockDevueltoPantalla =  document.getElementById('pda_p63_fld_stockDevuelto').value;
	var bultoPantalla =  document.getElementById('pda_p63_fld_bulto').value;
	var estadoPantalla =  document.getElementById('pda_p63_estadoCerrado').value;
	var variosBultosPantalla =document.getElementById('pda_p63_variosBultos').value;
	if(!esCentroParametrizado() || variosBultosPantalla=="true"){
		document.getElementById("pda_p63_btn_cerrarBulto").style.display = "none";
	}else{
		if(estadoPantalla=='S'){
			document.getElementById("pda_p63_btn_cerrarBulto").style.display = "none";
			document.getElementById('pda_p63_fld_stockDevuelto').readOnly = true;
			document.getElementById('pda_p63_fld_stockDevuelto').className+= ' pda_p63_readonly';
			document.getElementById('pda_p63_fld_bulto').readOnly = true;
			document.getElementById('pda_p63_fld_bulto').className+= ' pda_p63_readonly';
		}else{
			if(bultoPantalla==0 || bultoPantalla==''){
				document.getElementById("pda_p63_btn_cerrarBulto").style.display = "none";
			}
		}
	}
}

//Pinta de verde el link de stock si se ha modificado correctamente
function stockModificadoCorrectamente(){
	//Colorear link stock si se ha modificado correctamente
	var flgBienGuardado = document.getElementById("flgBienGuardado").value;
	if(flgBienGuardado == 'S'){
		var stockActual = document.getElementById("pda_p63_valorCampo_stock");
		stockActual.className += " linkVerde";
	}
}

function formateoPuntoPorComa(){
	var stckActual =  document.getElementById('pda_p63_valorCampo_stock');
	if (stckActual != null && typeof(stckActual) != 'undefined') {
		var numeroStk = stckActual.innerHTML;
		if(numeroStk % 1 != 0){
			stckActual.innerHTML = numeroStk.replace('.',',');
		}else{
			stckActual.innerHTML = parseFloat(numeroStk);
		}
	}

	var formato =  document.getElementById('pda_p63_valorCampo_formato');
	if (formato != null && typeof(formato) != 'undefined') {
		var numeroFormato = formato.innerHTML;
		var partes = numeroFormato.split("&nbsp;");
		var numero = partes[0];
		var tipo = partes[1];
		var tipoRef = partes[2];


		if(numero % 1 != 0){
			formato.innerHTML = numeroFormato.replace('.',',');
		}else{
			formato.innerHTML = parseFloat(numero)+"&nbsp;"+tipo+"&nbsp;"+tipoRef.substring(0,4);
		}
	}
}

function events_p63_stockDevuelto(){
	var stockDevueltoLink =  document.getElementById('pda_p63_fld_stockDevuelto');

	if (stockDevueltoLink != null && typeof(stockDevueltoLink) != 'undefined') {
		stockDevueltoLink.onclick = function () {
			var isReadOnly=document.getElementById('pda_p63_fld_stockDevuelto').readOnly;
			if(!isReadOnly){
				realizarAccionSiValido("StockDevueltoLink", "pdaP63RealizarDevolucionOrdenDeRetirada");
			}
		}
	}	
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	stockDevueltoLink = null;
}

function events_p63_variosBultos(){
	var variosBultos =  document.getElementById('pda_p63_varios_bultos_label');
	if (variosBultos != null && typeof(variosBultos) != 'undefined') {
		variosBultos.onclick = function () {
			var variosBultosPantalla =document.getElementById('pda_p63_variosBultos').value;
			var camposValidos=true;
			if(variosBultosPantalla=="false"){
				camposValidos=validarCamposPantallaP63VariosBultos();
			}
			if(camposValidos){
				document.getElementById("pda_p63_lbl_error").style.display = "none";
				var stockDevueltoPantalla =  document.getElementById('pda_p63_fld_stockDevuelto').value;
				var stockDevueltoOrigPantalla =  document.getElementById("stockDevuelto_orig").value;
				var bultoPantalla =  document.getElementById('pda_p63_fld_bulto').value;
				var bultoOrigPantalla = document.getElementById("bulto_orig").value;
				var codArtPantalla =  document.getElementById('codArticulo').value;
				var devolucionPantalla = document.getElementById('devolucion').value;
				var variosBultosPantalla =document.getElementById('pda_p63_variosBultos').value;
				var estadoCerradoPantalla =document.getElementById('pda_p63_estadoCerrado').value;
//				var mostrarFinDev = document.getElementById('mostrarFinDev').value;
				var origenPantalla = document.getElementById('origenPantalla').value;
				var selProv = document.getElementById('selProv').value;

				if (variosBultosPantalla=="false"){
					var bultoPantalla =  document.getElementById('pda_p63_fld_bulto').value;
					window.location.href = "./pdaP102VariosBultosOrdenRetirada.do?codArt="+codArtPantalla
											+"&devolucionPantalla="+devolucionPantalla
											+"&stockDevueltoPantalla="+stockDevueltoPantalla
											+"&bultoPantalla="+bultoPantalla
											+"&stockDevueltoOrigPantalla="+stockDevueltoOrigPantalla
											+"&bultoPantallaOrig="+bultoOrigPantalla
											+"&variosBultosPantalla="+variosBultosPantalla
											+"&estadoCerradoPantalla="+estadoCerradoPantalla
//											+"&mostrarFinDev="+mostrarFinDev
											+"&origenPantalla="+origenPantalla
											+"&selectProv="+selProv;
				}else{
					window.location.href = "./pdaP102VariosBultosOrdenRetirada.do?codArt="+codArtPantalla
											+"&devolucionPantalla="+devolucionPantalla
											+"&stockDevueltoPantalla="+stockDevueltoPantalla
											+"&stockDevueltoOrigPantalla="+stockDevueltoOrigPantalla
											+"&bultoPantallaOrig="+bultoOrigPantalla
											+"&variosBultosPantalla="+variosBultosPantalla
											+"&estadoCerradoPantalla="+estadoCerradoPantalla
//											+"&mostrarFinDev="+mostrarFinDev
											+"&origenPantalla="+origenPantalla
											+"&selectProv="+selProv;
				}
				
			}
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	variosBultos = null;
	
}

function validarCamposPantallaP63VariosBultos(){
	var camposValidosValidacion=true;
	var cantidad = document.getElementById('pda_p63_fld_stockDevuelto').value;
	var bulto = document.getElementById('pda_p63_fld_bulto').value;
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

function events_p63_referencia(){
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
				document.getElementById("pdaP63RealizarDevolucionOrdenDeRetirada").submit();

				e.stopPropagation();
			}
		}
		codArtCab.onchange = function () {
			document.getElementById("referenciaFiltro").value = document.getElementById("pda_p01_txt_infoRef").value;
			if (selectProveedor != null && typeof(selectProveedor) != 'undefined') {
				document.getElementById("proveedorFiltro").value = selectProveedor.value;
			}
			document.getElementById("accion").value = "Filtrar";
			document.getElementById("pdaP63RealizarDevolucionOrdenDeRetirada").submit();
		}
		codArtCab.onmousedown = function (e) {
			document.getElementById("pda_p01_txt_infoRef").value;
			realizarAccionSiValidoFiltro(e);
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	codArtCab = null;
}

function events_p63_proveedor(){
	var selectProveedor =  document.getElementById('pda_p01_txt_proveedor');
	if (selectProveedor != null && typeof(selectProveedor) != 'undefined') {
		selectProveedor.onchange = function () {
			document.getElementById("referenciaFiltro").value = document.getElementById("pda_p01_txt_infoRef").value;
			document.getElementById("proveedorFiltro").value = document.getElementById('pda_p01_txt_proveedor').value;
			document.getElementById("accion").value = "Filtrar";
			document.getElementById("pdaP63RealizarDevolucionOrdenDeRetirada").submit();
		}
		selectProveedor.onmousedown = function (e) {
			realizarAccionSiValidoFiltro(e);
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	selectProveedor = null;
}

function events_p63_lineasCompletadas(){
	var lineasCompletadas =  document.getElementById('pda_p63_lineasCompletadas');
	if (lineasCompletadas != null && typeof(lineasCompletadas) != 'undefined') {
		lineasCompletadas.onclick = function () {
			realizarAccionSiValido("SiguienteVacio", "pdaP63RealizarDevolucionOrdenDeRetirada");
		}
	}
    //Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	lineasCompletadas = null;
}

function events_p63_paginacion(){
	var pagPrimera =  document.getElementById('pda_p63_lnk_first');
	var pagAnt =  document.getElementById('pda_p63_lnk_prev');
	var pagSig =  document.getElementById('pda_p63_lnk_next');
	var pagUltima =  document.getElementById('pda_p63_lnk_last');

	if (pagPrimera != null && typeof(pagPrimera) != 'undefined') {
		pagPrimera.onclick = function () {
			realizarAccionSiValido("PrimeraPagina", "pdaP63RealizarDevolucionOrdenDeRetirada");
		}
	}
	if (pagAnt != null && typeof(pagAnt) != 'undefined') {
		pagAnt.onclick = function () {
			realizarAccionSiValido("AnteriorPagina", "pdaP63RealizarDevolucionOrdenDeRetirada");
		}
	}
	if (pagSig != null && typeof(pagSig) != 'undefined') {
		pagSig.onclick = function () {
			realizarAccionSiValido("SiguientePagina", "pdaP63RealizarDevolucionOrdenDeRetirada");
		}
	}
	if (pagUltima != null && typeof(pagUltima) != 'undefined') {
		pagUltima.onclick = function () {
			realizarAccionSiValido("UltimaPagina", "pdaP63RealizarDevolucionOrdenDeRetirada");
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	pagPrimera =  null;
	pagAnt =  null;
	pagSig =  null;
	pagUltima = null;
}

function events_p63_cant(){
	var cantidad =  document.getElementById('pda_p63_fld_stockDevuelto');

	puntoPorComaStockDevuelto();

	if (cantidad != null && typeof(cantidad) != 'undefined') {
		cantidad.onfocus = function (e) {
			var cantidadValue = document.getElementById('pda_p63_fld_stockDevuelto').value;
			//Si la cantidad está rellena hay que rellenar el bulto
			if (cantidadValue != null && cantidadValue !=""){
				rellenarBultoSiVacio();
			}
		}
		cantidad.onblur = function(e) {
			e = e || window.event;
			var idActual = (e.target || e.srcElement).id;
			var validacionNavegacion = validacionCantidad();
			if (validacionNavegacion=='N'){
				document.getElementById(idActual).focus();
				document.getElementById(idActual).select();
			}
			else{
				comaPorPuntoStockDevuelto();
				//Formateo la cantidad con 3 decimales
				var stockDevueltoFormateado = "";
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
				
				if(document.getElementById(idActual).value=='0' ){
					document.getElementById('pda_p63_fld_bulto').value=0;
					document.getElementById("pda_p63_btn_cerrarBulto").style.display = "none";
					document.getElementById("pda_p63_lbl_error2").style.display = "none";
				}
				if(document.getElementById(idActual).value==''){
					document.getElementById('pda_p63_fld_bulto').value='';
					document.getElementById("pda_p63_btn_cerrarBulto").style.display = "none";
					document.getElementById("pda_p63_lbl_error2").style.display = "none";
				}
				var bultoValue =  document.getElementById('pda_p63_fld_bulto').value;
				if(bultoValue=='' || bultoValue==0){
					document.getElementById("pda_p63_btn_cerrarBulto").style.display = "none";
					document.getElementById("pda_p63_lbl_error2").style.display = "none";
				}else{
					if(esCentroParametrizado()){
						var isReadOnly=document.getElementById('pda_p63_fld_stockDevuelto').readOnly;
						if(!isReadOnly){
							document.getElementById("pda_p63_btn_cerrarBulto").style.display = "block";
							document.getElementById("pda_p63_lbl_error2").style.display = "none";
						}
						
					}
					
				}
				rellenarBultoSiVacio();
			}

			// Inicio MISUMI-295
			var cantidadObject = document.getElementById(idActual);
			
			var flgPesoVariable = document.getElementById("flgPesoVariable").value;
			var flgBandejas = document.getElementById("flgBandejas").value;

			// Declaro variable con el valor inicial del StockDevuelto
			// para poder posteriormente comprobar si se ha cambiado.
			var cantidadOrig = document.getElementById("stockDevuelto_orig").value;

//			var cantidad = cantidadObject.value;
			var cantidad = document.getElementById("stockDevuelto").value;

			if(cantidadOrig != cantidad && flgBandejas != 'S' && flgPesoVariable == 'S'
				&& cantidad != 0 && cantidad.indexOf(',') == -1){
				document.getElementById("pda_p63_lbl_error").style.display = "block";

				//Volvemos a poner el valor original.
//				cantidadObject.value = document.getElementById("stockDevuelto_orig" + fila).value;
//				cantidadObject.focus();
//				cantidadObject.select();
			}else{
				document.getElementById("pda_p63_lbl_error").style.display = "none";
				
			}
			// FIN MISUMI-295

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
				var validacionNavegacion = validacionCantidad();

				//Si el valor no se ha cambiado o se ha cambiado con datos correctos.
				if (validacionNavegacion=='S' && validacionBulto() == 'S'){
					var cantidadObject = document.getElementById(idActual);
					var cantidadValue = cantidadObject.value;
					var cantidad = document.getElementById("stockDevuelto").value;

					
					//Si el valor de cantidad es vacío y se pulsa enter, regresa al campo donde se introduce la referencia.En el
					//caso de haber borrado el dato y haberlo puesto vacío, guarda el cambio y va a referencia.
					//Si el valor de la cantidad no es vacío se mira si el valor de la cantidad es distinto de 0. Si es distinto
					//de cero, se copia el bulto en el caso de existir un bulto guardado y se coloca el cursor donde la referencia.
					//Si no existe ese bulto guardado, se coloca el cursor sobre el bulto. Si la cantidad es 0, rellena el bulto en 0
					//y se coloca en la referencia. 
					//Si es distinto de 0
					if (!isNaN(parseFloat(cantidadValue)) && parseFloat(cantidadValue) != 0){

						//Se rellena el bulto en el caso de ser vacío con la información de bulto guardada
						rellenarBultoSiVacio();

						//Si el bulto es vacío se posiciona en el bulto. Si no, se ejecuta el formulario
						var bultoObject =  document.getElementById('pda_p63_fld_bulto');
						if (bultoObject.value == ""){
							bultoObject.focus();
						}
						else{
							comaPorPuntoStockDevuelto();
							document.getElementById("accion").value = "Guardar";

							var referencia = document.getElementById("referenciaFiltro").value;
							if(referencia != null && referencia !=''){
								document.getElementById("referenciaFiltroRellenaYCantidadEnter").value = "S";						 			
							}

							if(esCentroParametrizado()){
								var isReadOnly=document.getElementById('pda_p63_fld_stockDevuelto').readOnly;
								if(!isReadOnly){
									var listaBultos=document.getElementById('listaBultos').value;
									var aux = listaBultos.split(","); 
									var existebulto=false;
									var valorBultoComp=document.getElementById('pda_p63_fld_bulto').value;
									for(var i = 0; i<aux.length && !existebulto ; i++){
										if(valorBultoComp==aux[i]){
											existebulto=true;
										}
									}
									if(!existebulto){
										document.getElementById('pda_p63_fld_bulto').value=aux[0];
										document.getElementById("pda_p63_lbl_error2").style.display = "block";
										document.getElementById("pda_p63_btn_cerrarBulto").style.display = "none";
									}else{
										document.getElementById("pdaP63RealizarDevolucionOrdenDeRetirada").submit();
									}
								}
							}else{
								document.getElementById("pdaP63RealizarDevolucionOrdenDeRetirada").submit();
							}
						}
					}
					else{
						comaPorPuntoStockDevuelto();
						document.getElementById("accion").value = "Guardar";

						var realizarSubmit = true;
						//Si el bulto es vacío ponemos 0, si no mantenemos el valor que tenía.
						var bultoObject =  document.getElementById('pda_p63_fld_bulto');
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
								var isReadOnly=document.getElementById('pda_p63_fld_stockDevuelto').readOnly;
								if(!isReadOnly){
									var listaBultos=document.getElementById('listaBultos').value;
									var aux = listaBultos.split(","); 
									var existebulto=false;
									var valorBultoComp=document.getElementById('pda_p63_fld_bulto').value;
									if(bultoObject.value == "0"){
										existebulto=true;
									}
									for(var i = 0; i<aux.length && !existebulto ; i++){
										if(valorBultoComp==aux[i]){
											existebulto=true;
										}
									}
									if(!existebulto){
										document.getElementById('pda_p63_fld_bulto').value=aux[0];
										document.getElementById("pda_p63_lbl_error2").style.display = "block";
										document.getElementById("pda_p63_btn_cerrarBulto").style.display = "none";
									}else{
										document.getElementById("pdaP63RealizarDevolucionOrdenDeRetirada").submit();
									}
								}
							}else{
								document.getElementById("pdaP63RealizarDevolucionOrdenDeRetirada").submit();
							}
						}
					}

				}
			}
		}
		cantidad.onclick = function (e) {
			var variosBultosPantalla = document.getElementById("pda_p63_variosBultos").value;
			if(variosBultosPantalla=="false"){
				clickStockDevueltoP63();
			}else{
				document.getElementById("pda_p63_lbl_error").style.display = "none";
				var stockDevueltoPantalla =  document.getElementById('pda_p63_fld_stockDevuelto').value;
				var stockDevueltoOrigPantalla =  document.getElementById("stockDevuelto_orig").value;
				var bultoPantalla =  document.getElementById('pda_p63_fld_bulto').value;
				var bultoOrigPantalla = document.getElementById("bulto_orig").value;
				var codArtPantalla =  document.getElementById('codArticulo').value;
				var devolucionPantalla = document.getElementById('devolucion').value;
				var estadoCerradoPantalla =document.getElementById('pda_p63_estadoCerrado').value;
				var origenPantalla = document.getElementById('origenPantalla').value;
				var selProv = document.getElementById('selProv').value;

				
				window.location.href = "./pdaP102VariosBultosOrdenRetirada.do?codArt="+codArtPantalla
										+"&devolucionPantalla="+devolucionPantalla
										+"&stockDevueltoPantalla="+stockDevueltoPantalla
										+"&stockDevueltoOrigPantalla="+stockDevueltoOrigPantalla
										+"&bultoPantallaOrig="+bultoOrigPantalla
										+"&variosBultosPantalla="+variosBultosPantalla
										+"&estadoCerradoPantalla="+estadoCerradoPantalla
										+"&origenPantalla="+origenPantalla
										+"&selectProv="+selProv;
			}
			
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	cantidad =  null;
}

function events_p63_bulto(){
	var bulto =  document.getElementById('pda_p63_fld_bulto');

	if (bulto != null && typeof(bulto) != 'undefined') {
		bulto.onblur = function(e) {
			e = e || window.event;
			var idActual = (e.target || e.srcElement).id;
			var validacionNavegacion = validacionBulto();
			if (validacionNavegacion=='N'){
				document.getElementById(idActual).focus();
				document.getElementById(idActual).select();
			}else{
				var bultoValue=document.getElementById(idActual).value;
				if(bultoValue=='' || bultoValue==0){
					document.getElementById("pda_p63_btn_cerrarBulto").style.display = "none";
					document.getElementById("pda_p63_lbl_error2").style.display = "none";
				}else{
					if(esCentroParametrizado()){
						var isReadOnly=document.getElementById('pda_p63_fld_stockDevuelto').readOnly;
						if(!isReadOnly){
							var listaBultos=document.getElementById('listaBultos').value;
							var aux = listaBultos.split(","); 
							var existebulto=false;
							var valorBultoComp=document.getElementById('pda_p63_fld_bulto').value;
							for(var i = 0; i<aux.length && !existebulto ; i++){
								if(valorBultoComp==aux[i]){
									existebulto=true;
								}
							}
							if(!existebulto){
								document.getElementById('pda_p63_fld_bulto').value=aux[0];
								document.getElementById("pda_p63_lbl_error2").style.display = "block";
							}else{
								document.getElementById("pda_p63_lbl_error2").style.display = "none";
							}
							var stockDevueltoPantalla=document.getElementById('pda_p63_fld_stockDevuelto').value;
							if(stockDevueltoPantalla!=''){
								document.getElementById("pda_p63_btn_cerrarBulto").style.display = "block";
							}
						}
					}
				}
			}
		}
//		bulto.onfocus = function (e) {
//			rellenarBultoSiVacio();
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
				var validacionNavegacion = validacionBulto();
				if (validacionNavegacion=='S'){
					comaPorPuntoStockDevuelto();
					document.getElementById("accion").value = "Guardar";

					var referencia = document.getElementById("referenciaFiltro").value;
					if(referencia != null && referencia !=''){
						document.getElementById("referenciaFiltroRellenaYCantidadEnter").value = "S";						 			
					}

					//Miramos si se ha introducido un bulto distinto para guardar su valor en el proveedor
					var bultoOri = document.getElementById("bulto_orig").value;
					var bultoActual = document.getElementById('pda_p63_fld_bulto').value;

					if(bultoOri != bultoActual){
						//Si el nuevo bulto introducido es vacío o cero, se deja el valor anterior.
						if(bultoActual != 0 && bultoActual != ""){
							//Obtener el valor del proveedor de la línea de devolución
							var numeroProveedor = document.getElementById('proveedorDevolucionLinea').value;
							var partes = document.getElementById(numeroProveedor).value.split("*");
							document.getElementById(numeroProveedor).value = partes[0] + "*" + bultoActual;
						}
					}
					if(esCentroParametrizado()){
						var isReadOnly=document.getElementById('pda_p63_fld_stockDevuelto').readOnly;
						if(!isReadOnly){
							var listaBultos=document.getElementById('listaBultos').value;
							var aux = listaBultos.split(","); 
							var existebulto=false;
							var valorBultoComp=document.getElementById('pda_p63_fld_bulto').value;
							if(bultoOri == "0"){
								existebulto=true;
							}
							for(var i = 0; i<aux.length && !existebulto ; i++){
								if(valorBultoComp==aux[i]){
									existebulto=true;
								}
							}
							if(!existebulto){
								document.getElementById('pda_p63_fld_bulto').value=aux[0];
								document.getElementById("pda_p63_lbl_error2").style.display = "block";
								document.getElementById("pda_p63_btn_cerrarBulto").style.display = "none";
							}else{
								document.getElementById("pdaP63RealizarDevolucionOrdenDeRetirada").submit();
							}
						}
					}else{
						document.getElementById("pdaP63RealizarDevolucionOrdenDeRetirada").submit();
					}
				}
			}
		}
		bulto.onclick = function(e) {
			clickBultoP63();
		}
		bulto.onchange = function(e) {
			e = e || window.event;
			var idActual = (e.target || e.srcElement).id;
			var validacionNavegacion = validacionBulto();
			if (validacionNavegacion=='S'){
				var bultoActual = document.getElementById('pda_p63_fld_bulto').value;
				//Si el nuevo bulto introducido es vacío o cero, se deja el valor anterior.
				if(bultoActual != 0 && bultoActual != ""){
					//Obtener el valor del proveedor de la línea de devolución
					var numeroProveedor = document.getElementById('proveedorDevolucionLinea').value;
					var partes = document.getElementById(numeroProveedor).value.split("*");
					document.getElementById(numeroProveedor).value = partes[0] + "*" + bultoActual;
				}
			}
		}
	}
    //Limpiamos las variables DOM que no se utilizan más para evitar memory leak
    bulto = null;
}

function rellenarBultoSiVacio(){
	var idActual = 'pda_p63_fld_bulto';
	var bultoObject = document.getElementById(idActual);
	var bultoValue = bultoObject.value;
	
	var cantidadValue = document.getElementById('pda_p63_fld_stockDevuelto').value;
	if ((bultoValue == "" || bultoValue == "0") && cantidadValue != "" && cantidadValue != "0"){
		//Si está parametrizado hay que cargar el primer bulto abierto si lo hubiera
		var centroParametrizado=document.getElementById('centroParametrizado').value;
		if(centroParametrizado=='false'){
			//Obtenemos el número de proveedor de la línea de devolución del bulto que nos hemos situado
			var numProvr = document.getElementById("proveedorDevolucionLinea").value;
	
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

function events_p63_guardar(){
	var btn_save =  document.getElementById('pda_p63_btn_save');

	if (btn_save != null && typeof(btn_save) != 'undefined') {
		btn_save.onclick = function () {
			realizarAccionSiValido("Guardar", "pdaP63RealizarDevolucionOrdenDeRetirada");
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	btn_save = null;
}

function events_p63_finalizar(){
	var btn_fin =  document.getElementById('pda_p63_btn_fin');

	if (btn_fin != null && typeof(btn_fin) != 'undefined') {
		btn_fin.onclick = function () {
			realizarAccionSiValido("Finalizar", "pdaP63RealizarDevolucionOrdenDeRetirada");
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	btn_fin = null;
}

function events_p63_cerrarBulto(){
	document.getElementById("pda_p63_estadoCerrado").value ='S'  ;
	realizarAccionSiValido("CerrarBulto", "pdaP63RealizarDevolucionOrdenDeRetirada");
}

function events_p63_titulo(){
	var tituloLink =  document.getElementById('pda_p63_titulo');

	if (tituloLink != null && typeof(tituloLink) != 'undefined') {
		tituloLink.onclick = function () {
			realizarAccionSiValido("VerCabecera", "pdaP63RealizarDevolucionOrdenDeRetirada");
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	tituloLink = null;
}

function events_p63_stock(){
	var stockLink =  document.getElementById('pda_p63_valorCampo_stock');

	if (stockLink != null && typeof(stockLink) != 'undefined') {
		stockLink.onclick = function () {
			realizarAccionSiValido("StockLink", "pdaP63RealizarDevolucionOrdenDeRetirada");
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	stockLink = null;
}

function posInicialCursorP63(){
	var referenciaFiltroRellenaYCantidadPulsada =  document.getElementById("referenciaFiltroRellenaYCantidadEnter").value;
	if(referenciaFiltroRellenaYCantidadPulsada != 'S'){
		var referencia = document.getElementById("referenciaFiltro").value;
		var cantidadLabel = document.getElementById("pda_p63_fld_stockDevuelto_label");
		
		//Para ver si se ha pulsado una flecha
		var accionPrevia = document.getElementById("accionAnterior").value;
		if(referencia != null && referencia !='' && (cantidadLabel == null || typeof(cantidadLabel) == 'undefined')){
			var fld_stockDevuelto = document.getElementById("pda_p63_fld_stockDevuelto");
			if (fld_stockDevuelto != null && typeof(fld_stockDevuelto) != 'undefined') {
				fld_stockDevuelto.focus();
				fld_stockDevuelto.select();
			}
			document.getElementById("pda_p01_txt_infoRef").value = '';

			//Cuando se busca por un filtro, se coloca el cursor en stockDevuelto(CAN) y se borra el filtro introducido.
			//Si no se realizara esto, al paginar, buscaría por este filtro y no queremos que eso ocurra.
			document.getElementById("referenciaFiltro").value = '';
		}else if(accionPrevia != null &&  accionPrevia !=''  && (accionPrevia == 'UltimaPagina' || accionPrevia == 'PrimeraPagina' || accionPrevia == 'AnteriorPagina' || accionPrevia == 'SiguientePagina')){
			//Si se ha pulsado alguna de las flechas entra aquí
			var fld_stockDevuelto = document.getElementById("pda_p63_fld_stockDevuelto");
			if(fld_stockDevuelto != null && typeof(fld_stockDevuelto) != 'undefined'){
				fld_stockDevuelto.focus();
				//fld_stockDevuelto.select();
			}
		}else{
			document.getElementById("pda_p01_txt_infoRef").value = '';
			document.getElementById("pda_p01_txt_infoRef").focus();
		}
	}else{
		document.getElementById("referenciaFiltroRellenaYCantidadEnter").value = "";	
		document.getElementById("pda_p01_txt_infoRef").value = '';
		document.getElementById("pda_p01_txt_infoRef").focus();
	}	
}

function clickBultoP63(){
	var fld_bulto = document.getElementById("pda_p63_fld_bulto");
	if (fld_bulto != null && typeof(fld_bulto) != 'undefined') {
		var variosBultosPantalla = document.getElementById("pda_p63_variosBultos").value;
		if(variosBultosPantalla == "false"){
			fld_bulto.focus();
			fld_bulto.select();
		}else{
			document.getElementById("pda_p63_lbl_error").style.display = "none";
			var stockDevueltoPantalla =  document.getElementById('pda_p63_fld_stockDevuelto').value;
			var stockDevueltoOrigPantalla =  document.getElementById("stockDevuelto_orig").value;
			var bultoPantalla =  document.getElementById('pda_p63_fld_bulto').value;
			var bultoOrigPantalla = document.getElementById("bulto_orig").value;
			var codArtPantalla =  document.getElementById('codArticulo').value;
			var devolucionPantalla = document.getElementById('devolucion').value;
			var estadoCerradoPantalla =document.getElementById('pda_p63_estadoCerrado').value;
			var origenPantalla = document.getElementById('origenPantalla').value;
			var selProv = document.getElementById('selProv').value;
			window.location.href = "./pdaP102VariosBultosOrdenRetirada.do?codArt="+codArtPantalla
									+"&devolucionPantalla="+devolucionPantalla
									+"&stockDevueltoPantalla="+stockDevueltoPantalla
									+"&stockDevueltoOrigPantalla="+stockDevueltoOrigPantalla
									+"&bultoPantallaOrig="+bultoOrigPantalla
									+"&variosBultosPantalla="+variosBultosPantalla
									+"&estadoCerradoPantalla="+estadoCerradoPantalla
									+"&origenPantalla="+origenPantalla
									+"&selectProv="+selProv;
		}
	}	
}

function clickStockDevueltoP63(){
	var fld_stockDevuelto = document.getElementById("pda_p63_fld_stockDevuelto");
	if (fld_stockDevuelto != null && typeof(fld_stockDevuelto) != 'undefined') {
		fld_stockDevuelto.focus();
		fld_stockDevuelto.select();
	}	
}

function validacionCantidad(pintarRojoCantidad){
	var resultadoValidacion = 'S';
	var variosBultosPantalla = document.getElementById("pda_p63_variosBultos").value;
	if(variosBultosPantalla == "false"){
		if ( cantidadModificada() ){
			pintarRojoCantidad = (typeof pintarRojoCantidad !== 'undefined' ? pintarRojoCantidad : true);
			var id = 'pda_p63_fld_stockDevuelto';
			var campoActual = document.getElementById(id);
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
			}else{
				//Quitamos el rojo del campo.
				campoActual.className = campoActual.className.replace(/inputCantidadResaltado/g,'');
				resultadoValidacion = 'S';
			}
		}
	}
	return resultadoValidacion;
}

function validacionBulto(){
	var id = 'pda_p63_fld_bulto';
	var resultadoValidacion = 'S';
	var campoActual = document.getElementById(id);
	var variosBultosPantalla = document.getElementById("pda_p63_variosBultos").value;
	if(variosBultosPantalla == "false"){
		var numeroIntegerIsOk = /^\d{0,12}$/.test(campoActual.value);
		if (!numeroIntegerIsOk)
		{
			//Pintamos de rojo el campo.
			campoActual.className = campoActual.className + " inputBultoResaltado";
			resultadoValidacion = 'N';
		}
		else
		{
			//Quitamos el rojo del campo.
			campoActual.className = campoActual.className.replace(/inputBultoResaltado/g,'');
			resultadoValidacion = 'S';
		}
	}
	return resultadoValidacion;
}

function comaPorPuntoStockDevuelto(){
		
	var flgBandejas = document.getElementById("flgBandejas").value;
// Inicio MISUMI-295
//	if (!(flgBandejas == 'S')) {
//		document.getElementById('stockDevuelto').value = document.getElementById('stockDevuelto').value.replace(',','.');
//	}

		document.getElementById('stockDevuelto').value = document.getElementById('pda_p63_fld_stockDevuelto').value;
		document.getElementById('stockDevuelto').value = document.getElementById('stockDevuelto').value.replace(',','.');
// FIN MISUMI-295
}

function puntoPorComaStockDevuelto(){
	var campoActual = document.getElementById('stockDevuelto');
	var campoActualLabel = document.getElementById('pda_p63_fld_stockDevuelto_label');
	if (campoActual != null && typeof(campoActual) != 'undefined') {
		var stockDevueltoFormateado = "";
		var numeroDoubleStockDevuelto = parseFloat(document.getElementById('stockDevuelto').value.replace(',','.'));
		if (!isNaN(numeroDoubleStockDevuelto)){
			//Si no es un string vacio y es un número lo formateo con 3 decimales 
			//y con la coma como separador decimal.
			if(numeroDoubleStockDevuelto % 1 != 0){
				stockDevueltoFormateado = numeroDoubleStockDevuelto.toFixed(3).replace(',','.');
			}else{
				stockDevueltoFormateado = numeroDoubleStockDevuelto;
			}
		}
		document.getElementById('pda_p63_fld_stockDevuelto').value = stockDevueltoFormateado;
		if (campoActualLabel != null && typeof(campoActualLabel) != 'undefined') {
			campoActualLabel.innerHTML = stockDevueltoFormateado;
		}
		document.getElementById('stockDevuelto_orig').value = stockDevueltoFormateado;
	}	
}

function realizarAccionSiValido(accion, pagina){
	var cantidad = document.getElementById('pda_p63_fld_stockDevuelto').value;
	var validacionNavegacion = validacionCantidad(false);
	if (cantidad == "" || validacionNavegacion=='S'){
		validacionNavegacion = validacionBulto();
		if (validacionNavegacion=='S'){
			comaPorPuntoStockDevuelto();
			document.getElementById("accion").value = accion;
			document.getElementById("pda_p63_btn_fin").style.display = "none";
			document.getElementById(pagina).submit();
		}
		else{
			document.getElementById('pda_p63_fld_bulto').focus();
			document.getElementById('pda_p63_fld_bulto').select();
			return false;
		}
	}
	else{
		document.getElementById('pda_p63_fld_stockDevuelto').focus();
		document.getElementById('pda_p63_fld_stockDevuelto').select();
		return false;
	}
}

//Para que no deje usar el combobox si cantidad o bulto están en formato incorrecto
function realizarAccionSiValidoFiltro(e){
	var cantidad = document.getElementById('pda_p63_fld_stockDevuelto').value;
	var validacionNavegacion = validacionCantidad(true);
	if (cantidad == "" || validacionNavegacion=='S'){
		validacionNavegacion = validacionBulto();
		if (validacionNavegacion!='S'){
			e.preventDefault();
			document.getElementById('pda_p63_fld_bulto').focus();
			document.getElementById('pda_p63_fld_bulto').select();
			return false;
		}
	}
	else{
		e.preventDefault();
		document.getElementById('pda_p63_fld_stockDevuelto').focus();
		document.getElementById('pda_p63_fld_stockDevuelto').select();
		return false;
	}
}

function cantidadModificada(){
	var cantidadOriginal = document.getElementById('stockDevuelto_orig').value;
	var cantidadActual = document.getElementById('pda_p63_fld_stockDevuelto').value;
	var cantidadActualIsNumberDouble = /^(-?\d+((\,|\.)\d{0,3}){0,1})?$/.test(cantidadActual);
									 

	var flgBandejas = document.getElementById("flgBandejas").value;
	if ((cantidadActualIsNumberDouble) && !(flgBandejas == 'S')) {
		return parseFloat( cantidadOriginal.replace(',','.') ) != parseFloat( cantidadActual.replace(',','.') );
	}
	else{
		return cantidadOriginal != cantidadActual;
	}
}

function events_p63_caducidadLoteLink(){
	var loteLink = document.getElementById('pdaP63LoteLink');
	if (loteLink != null && typeof(loteLink) != 'undefined') {
		loteLink.onclick = function () {
			realizarAccionSiValido("MasInfo", "pdaP63RealizarDevolucionOrdenDeRetirada");
		}
	}
	var caducidadLink = document.getElementById('pdaP63CaducidadLink');
	if (caducidadLink != null && typeof(caducidadLink) != 'undefined') {
		caducidadLink.onclick = function () {
			realizarAccionSiValido("MasInfo", "pdaP63RealizarDevolucionOrdenDeRetirada");
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	loteLink = null;
	caducidadLink = null;
}

function events_p63_detalleTextilLink(){
	var textilLink = document.getElementById('pda_p63_modelo_proveedor');
	if (textilLink) {
		textilLink.onclick = function () {
			realizarAccionSiValido("MasInfo", "pdaP63RealizarDevolucionOrdenDeRetirada");
		}
	}
	textilLink = document.getElementById('pda_p63_talla');
	if (textilLink) {
		textilLink.onclick = function () {
			realizarAccionSiValido("MasInfo", "pdaP63RealizarDevolucionOrdenDeRetirada");
		}
	}
	textilLink = document.getElementById('pda_p63_color');
	if (textilLink) {
		textilLink.onclick = function () {
			realizarAccionSiValido("MasInfo", "pdaP63RealizarDevolucionOrdenDeRetirada");
		}
	}
	textilLink = null;
}