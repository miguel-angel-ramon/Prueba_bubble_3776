/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/**************************************************************** VARIABLES ******************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
//Variables json.
var errorValidacion;
var warningValidacion;
var listBultoCantidadPantalla = new Array();
var sumatorioCantidad=0;
var bultoStr= "";
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************** DOCUMENTO LISTO **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
$(document).ready(function(){
	initializeScreenP116PopupVariosBultos();
	event_btn_guardadoVariosBultosFinCampana();
	regexForInputFinCampana();
	loadP116(locale);
	$("#")
});

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/********************************************************** INICIALIZACIÓN  IDIOMA ***********************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
function loadP116(locale){
	this.i18nJSON = './misumi/resources/p116PopUpVariosBultos/p116PopUpVariosBultos_' + locale + '.json';
	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		errorValidacion = data.errorValidacion;
		warningValidacion = data.warningValidacion;
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/********************************************************** POPUP Y FUNCIONES  ***************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
//Inicializa el diálogo.
function initializeScreenP116PopupVariosBultos(){
	$( "#p116_popUpVariosBultos").dialog({
		autoOpen: false,
		height: 'auto',
		width: 'auto',
		modal: true,
		resizable: false,
		open: function() {
			document.getElementById("p116_lbl_Error").style.display = "none";
			document.getElementById("p116_lbl_Error2").style.display = "none";
			document.getElementById("p116_lbl_Error3").style.display = "none";
			document.getElementById("p116_lbl_Error4").style.display = "none";
		},
		close: function( event, ui ) {
			//Limpiamos el popup p116
			limpiarPopUpP116();
		},
		//Fijar el z-index del popup p56 para que las opciones del combobox no se queden detrás del popup 
		//al autoincrementarse el z-index.
		stack: false
	});
}

//Función que limpia el popup.
function limpiarPopUpP116(){	
	for(var i = 0; i<5; i++){
		var campoCantidad="p116_fld_cantidad"+i;
		var campoBulto="p116_fld_bulto"+i;
		document.getElementById(campoCantidad).value="";
		document.getElementById(campoBulto).value="";
	}
}

//Abrimos el popup p116
function openPopUpVariosBultosFinCampana(fila,tabla,nombrePopUp){			
	//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente al guardar.
	$("#p116_fld_StockDevuelto_Selecc").val(fila + "_stockDevuelto");
	$("#p116_fld_StockDevueltoOrig_Selecc").val(fila + "_stockDevuelto_orig");
	$("#p116_fld_StockDevueltoTmp_Selecc").val(fila + "_stockDevuelto_tmp");
	$("#p116_fld_Bulto_Selecc").val(fila + "_bulto");
	
	$("#p116_popUpVariosBultos").dialog("open");			

	//Obtenemos la referencia de la fila
	var ref = $("#"+fila).find("td[aria-describedby='"+tabla+"_codArticulo']").text();

	//Obtenemos la descripcion de la fila
	var descripcion = $("#"+fila).find("td[aria-describedby='"+tabla+"_denominacion']").text();

	//Juntar la referencia y la descripcion.
	var refDescripcion = ref + "-" + descripcion;

	//Insertamos la descripción de la fila.
	$("#p116_lbl_refDescripcion").text(refDescripcion);	

	var devolucionId = "#"+fila+"_devolucion";
	var devolucionPantalla = $(devolucionId).val();
	cargarDatosPantallaFinCampana(fila,ref,devolucionPantalla);
	//Ponemos el cursor en el primer campo de stock de la pantalla de varios bultos.
	$("#p116_fld_cantidad0").focus();
}

//Cuando pulsamos guardar, se validan los campos de cantidad y bulto 
function event_btn_guardadoVariosBultosFinCampana(){
	$("#p116_btn_guardado").click(function(e){
		if (validarCantidadesyBultosFinCampana()){
			if (hayBultosIgualesFinCampana()){
				//ERRORES EN LA VALIDACIÓN: HAY BULTOS INTRODUCIDOS IGUALES POR LO QUE VOLVEMOS A LA PANTALLA Y MOSTRAMOS EL DIV DE ERROR EN LA PARTE INFERIOR
				document.getElementById("p116_lbl_Error2").style.display = "block";
				document.getElementById("p116_lbl_Error").style.display = "none";
				document.getElementById("p116_lbl_Error3").style.display = "none";
				document.getElementById("p116_lbl_Error4").style.display = "none";
			}else{
				//AQUI HAY Q VALIDAR CUANDO EL VALOR DE CANTIDAD MAX ESTE RELLENO,EL SUMATORIO DE LAS CANTIDADES NO SEA SUPERIOR
				var cantidadMax = document.getElementById("p116_cantidadMax").value;
				if (cantidadMax!= 'undefined' && cantidadMax!=null && parseFloat(sumatorioCantidad) > parseFloat(cantidadMax)){
					document.getElementById("p116_lbl_Error2").style.display = "none";
					document.getElementById("p116_lbl_Error").style.display = "none";
					document.getElementById("p116_lbl_Error3").style.display = "block";
					document.getElementById("p116_lbl_Error4").style.display = "none";
				}else{
					//AQUI HAY Q GUARDAR LOS DATOS DE LA PANTALLA Y REDIRECCIONAR A LA PANTALLA DE DEVOLUCION
					listBultoCantidadPantalla= new Array();
					listBultoCantidadPantalla=rellenarListaDatosPantallaFinCampana();
					
					var objJson = prepareObjectJSONFinCampana();
					$.ajax({
						url : './devoluciones/popup/finDeCampana/guardarBultoCantidad.do',
						type: 'POST',
						contentType: "application/json",
						dataType : "json",
						data: objJson,
						success : function(data) {
							if(data != null ){
								if(data.codError==1){
									//mostrar error al guardar
									document.getElementById("p116_lbl_Error4").style.display = "block";
									document.getElementById("p116_lbl_Error2").style.display = "none";
									document.getElementById("p116_lbl_Error").style.display = "none";
									document.getElementById("p116_lbl_Error3").style.display = "none";
								}else{
									//Evitas la ejecución de otros posibles eventos.
									e.stopPropagation();
									//Cerramos el dialogo.
									$("#p116_popUpVariosBultos").dialog("close");

									let sumatorioCantidad=0;
									let bultoStr="";
									let numBultos=0;
									for (var i = 0; i<5; i++){
										//Los registros de cantidad y bulto están rellenos y se valida que sea un número entero
										let cantidad=data.listaBultoCantidad[i].stock;
										//cantidad=cantidad.replace('.',',');
										let numeroDoubleIsOk = /^(-?\d+((\,|\.)\d{0,3}){0,1})?$/.test(cantidad);
										if (numeroDoubleIsOk){
											sumatorioCantidad=sumatorioCantidad+parseFloat(cantidad);
										}
										let bulto=data.listaBultoCantidad[i].bulto;
										if(bulto!=null){
											bultoStr+=bulto+",";
											numBultos++;
										}
									}

									let valorBultoSelec=$("#p116_fld_Bulto_Selecc").val();
									let posRefGrid = valorBultoSelec.substr(0,valorBultoSelec.indexOf("_"));
									if (numBultos > 1){
										$("#"+posRefGrid+"_variosBultos").val(true);
									}else{
										$("#"+posRefGrid+"_variosBultos").val(false);
									}

									// Limpio los estados del grid y asigno el estado "guardado".
									let ids = $(gridP83.nameJQuery).jqGrid('getDataIDs'), l = ids.length;
									for (var j = 0; j < l; j++) {
										$("#"+j+"_stockDevueltoBulto_divGuardado").hide();
									}

									$("#"+posRefGrid+"_stockDevueltoBulto_divGuardado").show();
									// Actualizo cod_error=8 que significa que ha sido guardado.
									$("#"+posRefGrid+"_stockDevueltoBulto_codError_orig").val("8");

									//Si tenemos guardado el id del input de cantidad devolver centro del que venimos cuando hemos abierto el popup.
									if ($("#p116_fld_StockDevuelto_Selecc").val() != '')	{
										if(sumatorioCantidad!=null){
											$("#"+$("#p116_fld_StockDevuelto_Selecc").val()).val($.formatNumber(sumatorioCantidad,{format:"0.###",locale:"es"}));	
											$("#"+$("#p116_fld_StockDevueltoOrig_Selecc").val()).val($.formatNumber(sumatorioCantidad,{format:"0.###",locale:"es"}));
											$("#"+$("#p116_fld_StockDevueltoTmp_Selecc").val()).val($.formatNumber(sumatorioCantidad,{format:"0.###",locale:"es"}));
										}
										
									}
									if ($("#p116_fld_Bulto_Selecc").val() != '')	{
										$("#"+$("#p116_fld_Bulto_Selecc").val()).val(bultoStr.substring(0,bultoStr.length-1));
									}

									// ***********************************************************************************/
									// Después de guardar los cambios, volver a cargar la lista del combo de proveedores
									// por si ha cambiado el estado de proveedor completado. "OK <CodProveedor>".
									let provSeleccionado = $('#p83_cmb_proveedor').val();
									
									var devolucionEditada = new Devolucion(null,null,null,null,null,null
																		  ,$("#p116_fld_devolucion").val(),null,null
																		  ,null,null,null,null,null
																		  ,null,null,null,null,null
																		  ,null,null,provSeleccionado,null
																		  );

									var objJsonProv = $.toJSON(devolucionEditada);
									
									recargaCmbProvSelec(objJsonProv,provSeleccionado);
									// ***********************************************************************************/

									let hayRefsPdtes = data.hayRefsPdtes;

									// comprobar que se trata de una devolución de "Preparar Mercancia"
									// y el centro está parametrizado para "27_PC_DEVOLUCIONES_PROCEDIMIENTO".
									if (hayRefsPdtes=="SI"){
										$("#p83_MensajeAvisoAreaCombos").show();
									}else{
										$("#p83_MensajeAvisoAreaCombos").hide();
									}

								}

							}
						},
						error : function (xhr, status, error){
							handleError(xhr, status, error, locale);
						}			
					});
				}
			}
		}else{
			//ERRORES EN LA VALIDACIÓN POR LO QUE VOLVEMOS A LA PANTALLA Y MOSTRAMOS EL DIV DE ERROR EN LA PARTE INFERIOR
			document.getElementById("p116_lbl_Error").style.display = "block";
			document.getElementById("p116_lbl_Error2").style.display = "none";
			document.getElementById("p116_lbl_Error4").style.display = "none";
			document.getElementById("p116_lbl_Error3").style.display = "none";
		}
	});
}
function prepareObjectJSONFinCampana(){
	var devolucionPantalla = $("#p116_fld_devolucion").val();
	var codArtPantalla = $("#p116_fld_codArt").val();
	var datosPantalla = {
		"devolucion" : devolucionPantalla,
		"codArt" : codArtPantalla,
		"listaBultoCantidad" : listBultoCantidadPantalla
	};
	return $.toJSON(datosPantalla);
}
function rellenarListaDatosPantallaFinCampana(){
	let listaBultoCantidadPantalla = new Array();
	let registroListadoModificado = {};
	let codError = "";
	let descError = "";
	for(var i = 0; i<5; i++){
		registroListadoModificado = {};

		let campoCantidad="p116_fld_cantidad"+i;
		let campoBulto="p116_fld_bulto"+i;
		let stockDevuelto = document.getElementById(campoCantidad).value;
		let bulto = document.getElementById(campoBulto).value;
		registroListadoModificado.stock=stockDevuelto.replace(',','.');
		registroListadoModificado.bulto=bulto;
		registroListadoModificado.estadoCerrado=$("#p116_estadoCerrado"+i).val();
		registroListadoModificado.codError=codError;
		registroListadoModificado.descError=descError;
		listaBultoCantidadPantalla.push(registroListadoModificado);
	}
	return listaBultoCantidadPantalla;
}
//Función que sirve para recuperar todos los datos de la referencia que se muestran en pantalla
function cargarDatosPantallaFinCampana(fila,ref,devolucionPantalla){
	$.ajax({
		type : 'GET',	
		url : './devoluciones/popup/finDeCampana/cargarDatosPantallaVariosBultos.do'+ createParamsUrlFinCampana(ref,devolucionPantalla),
		success : function(data) {	
			if (data != null){
				$("#p116_fld_devolucion").val(data.devolucion);
				$("#p116_fld_codArt").val(data.codArticulo);
				$("#p116_fld_flgPesoVariable").val(data.flgPesoVariable);
				$("#p116_cantidadMax").val(data.cantidadMaximaLin);
				$("#p116_valorCampo_cantMax").text(data.cantidadMaximaLin);
				$("#p116_listaBultos").val(data.listaBultos);
				
				if(data.flgPesoVariable=='S'){
					document.getElementById("p116_lbl_avisoPesoVariable").style.display = "block";
				}else{
					document.getElementById("p116_lbl_avisoPesoVariable").style.display = "none";
				}
				
//	            let ariaDescribedBy = 'gridP83DevolFinCampana_bultoEstadoCerrado'; // Atributo aria-describedby conocido
//	            let cellEstadoCerradoValue = $("#"+ fila).find('td[aria-describedby="' + ariaDescribedBy + '"]').text();

	            for (var j = 0; j<5; j++){
					let campoCantidad="p116_fld_cantidad"+j;
					let campoBulto="p116_fld_bulto"+j;
					let stock=data.listTDevolucionLinea[j].stock;
					if(stock!=null){
						stock=$.formatNumber(stock,{format:"0.###",locale:"es"});
					}
					
					//Obtenemos la cantidad y bulto de la fila.
					let cantidad = $("#"+fila+"_stockDevuelto").val();
					let bulto = $("#"+fila+"_bulto").val();

					if (j==0 && stock == null && bulto != null){
						document.getElementById(campoCantidad).value=cantidad;
						document.getElementById(campoBulto).value=bulto;
					}else{
						document.getElementById(campoCantidad).value=stock;
						document.getElementById(campoBulto).value=data.listTDevolucionLinea[j].bulto;
					}
					
//					let esEstadoCerrado="N";
					let esEstadoCerrado=data.listTDevolucionLinea[j].estadoCerrado;
//
//	            	// compruebo si la referencia está en varios bultos.
//	        		if (cellEstadoCerradoValue.indexOf("*")!== -1){
//	        			let bultoEstado = cellEstadoCerradoValue.split("*");
//	        			if (bultoEstado[i]!=undefined){
//	        				
//	        				let bultoE = bultoEstado[i].split("-");
//        					if (data.listTDevolucionLinea[i].bulto==bultoE[0]){
//    	        				if (bultoE[1]=="S"){
//            						esEstadoCerrado="S";
//    	        				}
//        					}
//	        			}
//	        		}else{
//	        			if (cellEstadoCerradoValue!=undefined || cellEstadoCerradoValue===""){
//	        				bultoSeparado = cellEstadoCerradoValue.split("-");
//
//        					if (data.listTDevolucionLinea[i].bulto==bultoSeparado[0]){
//		        				if (bultoSeparado[1]=="S"){
//		    						esEstadoCerrado="S";
//		        				}
//        					}
//	        			}
//	        		}

                	$("#p116_estadoCerrado"+j).val(esEstadoCerrado);

					// si Bulto Cerrado hacemos no editables los campos "Cantidad a Devolver" y "Bulto".
					if (esEstadoCerrado=="S"){
						$("#p116_fld_cantidad"+j).css("background", "lightgray");
						$("#p116_fld_cantidad"+j).attr('readonly', true);
						$("#p116_fld_bulto"+j).css("background", "lightgray");
						$("#p116_fld_bulto"+j).attr('readonly', true);
						$("#p116_btn_eliminar"+j).hide();
					}else{
						$("#p116_fld_cantidad"+j).css("background", "white");
						$("#p116_fld_cantidad"+j).attr('readonly', false);
						$("#p116_fld_bulto"+j).css("background", "white");
						$("#p116_fld_bulto"+j).attr('readonly', false);
						$("#p116_btn_eliminar"+j).show();
					}

//					$("#p116_fld_bulto"+j).change(function() {
//						controlChangeBulto(this.id);
//					});

					$("#p116_fld_bulto"+j).focus(function() {
						controlFocusBulto(this.id);
					});

	            }
				if(data.cantidadMaximaLin!=null){
					document.getElementById("p116_bloqueDatos_cantidadMax").style.display = "block";
				}else{
					document.getElementById("p116_bloqueDatos_cantidadMax").style.display = "none";
				}

			}

		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});
}

function createParamsUrlFinCampana(ref,devolucionPantalla){
	var url = "?devolucion="+devolucionPantalla;
	url +=  "&codArt="+ref;
	return url;
}

function eliminarLinea(indice){
	var botonEliminarId="p116_btn_eliminar"+indice
	var eliminar =  document.getElementById(botonEliminarId);
	if (eliminar != null && typeof(eliminar) != 'undefined') {
		for(var i = indice; i<4; i++){
			var idCopiar=i+1;
			var campoCantidad="p116_fld_cantidad"+i;
			var campoBulto="p116_fld_bulto"+i;
			var campoCantidadCopiar="p116_fld_cantidad"+idCopiar;
			var campoBultoCopiar="p116_fld_bulto"+idCopiar;
			document.getElementById(campoCantidad).value=document.getElementById(campoCantidadCopiar).value;
			document.getElementById(campoBulto).value=document.getElementById(campoBultoCopiar).value;
		}
		document.getElementById("p116_fld_cantidad4").value="";
		document.getElementById("p116_fld_bulto4").value="";
		
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	eliminar = null;
}

function regexForInputFinCampana(){
	for(var i = 0; i<5; i++){
		var campoCantidad="p116_fld_cantidad"+i;
		var campoBulto="p116_fld_bulto"+i;
		$(campoCantidad).filter_input({regex:'[0-9,]'});	
		$(campoBulto).filter_input({regex:'[0-9]'});
	}
	
}

function validarCantidadesyBultosFinCampana(){
	var camposValidos=true;
	var cantidad0 = document.getElementById("p116_fld_cantidad0").value;
	var bulto0 = document.getElementById("p116_fld_bulto0").value;
	var cantidad1 = document.getElementById("p116_fld_cantidad1").value;
	var bulto1 = document.getElementById("p116_fld_bulto1").value;
	var cantidad2 = document.getElementById("p116_fld_cantidad2").value;
	var bulto2 = document.getElementById("p116_fld_bulto2").value;
	var cantidad3 = document.getElementById("p116_fld_cantidad3").value;
	var bulto3 = document.getElementById("p116_fld_bulto3").value;
	var cantidad4 = document.getElementById("p116_fld_cantidad4").value;
	var bulto4 = document.getElementById("p116_fld_bulto4").value;
	//Validacion si todos los campos están vacios
	if((cantidad0=="" && bulto0=="") && (cantidad1=="" && bulto1=="") && (cantidad2=="" && bulto2=="") && (cantidad3=="" && bulto3=="") && (cantidad4=="" && bulto4=="")){
		camposValidos=true;
	}else{
		sumatorioCantidad=0;
		for(var i = 0; i<5 && camposValidos; i++){
			var campoCantidad="p116_fld_cantidad"+i;
			var campoBulto="p116_fld_bulto"+i;
			
			var cantidadPantalla = document.getElementById(campoCantidad).value;
			var bultoPantalla = document.getElementById(campoBulto).value;
			camposValidos=validarCamposPantalla(cantidadPantalla,bultoPantalla);
		}
	}
	return camposValidos;
}

function validarCamposPantalla(cantidad,bulto){
	var camposValidosValidacion=true;
	//Validación para ver si los 2 registros están rellenos de una misma fila
	if((cantidad=="" && bulto!="") || (cantidad!="" && bulto=="")){
		camposValidosValidacion=false;
	//Validación para ver si los 2 registros están vacios
	}else if(cantidad=="" && bulto==""){
		camposValidosValidacion=true;
	}else{
		var flgPesoVariable=document.getElementById("p116_fld_flgPesoVariable").value;
		var numeroCantidadIsOk;
		if(flgPesoVariable=='S'){
			numeroCantidadIsOk = /^(-?\d+((\,|\.)\d{0,3}){0,1})?$/.test(cantidad);
		}else{
			numeroCantidadIsOk = /^\d{0,12}$/.test(cantidad);
		}
		
		if (!numeroCantidadIsOk){
			camposValidosValidacion=false;
		}else{
			sumatorioCantidad=sumatorioCantidad+parseFloat(cantidad);
		}

		var numeroBultoIsOk = /^\d{0,2}$/.test(bulto);
		if (!numeroBultoIsOk){
			camposValidosValidacion=false;
		}
	}
	return camposValidosValidacion;
}

function hayBultosIgualesFinCampana(){
	var bultosIguales=false;
	for(var i = 0; i<5 && !bultosIguales; i++){
		var idCampoBulto="p116_fld_bulto"+i;
		var campoBulto = document.getElementById(idCampoBulto).value;
		if(campoBulto!=""){
			for(var j = i+1; j<5; j++){
				var idCampoBultoComparar="p116_fld_bulto"+j;
				var campoBultoComparar = document.getElementById(idCampoBultoComparar).value;
				if( campoBultoComparar!="" && campoBulto==campoBultoComparar){	
					bultosIguales=true;
					break;
				}
			}
		}
	}
	return bultosIguales;
}

function controlFocusBulto(idActual){
	//Obtención de fila y columna actuales
	let fila = idActual.charAt(idActual.length - 1);

	let filaBultoActual = "#p116_fld_bulto"+fila;
	let filaStockDevueltoActual = "#p116_fld_cantidad"+fila;
	let valorStockDevueltoActual = $(filaStockDevueltoActual).val();

	let provrGenFila = "#"+fila+"_provrGenLin";
	let provrGenFilaValor = $(provrGenFila).val();

	$("#p116_lbl_Error5").css("display", "none");

	if (valorStockDevueltoActual != null && valorStockDevueltoActual != "" && valorStockDevueltoActual != "0") {
		let valorIdActual = $(filaBultoActual).val();
		if(valorIdActual == "" || valorIdActual == null || valorIdActual == "null"){
			//Se inserta en la fila actual el valor de la fila anterior
			$(filaBultoActual).val(ultimoBultoIntroducido[provrGenFilaValor]);
		}
	}else if (valorStockDevueltoActual == "0"){
		$(filaBultoActual).val("0");
	}	
}

// comprueba si hay que actualizar el bulto abierto asignado
// al proveedor de la referencia en la que se está actuando.
function controlChangeBulto(idActual){

	//Obtención de fila y columna actuales
	let fila = idActual.charAt(idActual.length - 1);
	let filaBultoActual = "#p116_fld_bulto"+fila;

	let provrGenFila = "#"+fila+"_provrGenLin";
	let provrGenFilaValor = $(provrGenFila).val();

	let valorIdActual =  $(filaBultoActual).val();

	$("#p116_lbl_Error5").css("display", "none");

	if (valorIdActual != "" && valorIdActual != null && valorIdActual != "0" && valorIdActual != "null"){
		
//		let valoresBultoAbierto=$("#"+fila+"_bultoAbierto").val();
		let valoresBultoAbierto=$("#p116_listaBultos").val();
		let primerBultoAbierto=parseInt(valoresBultoAbierto.substr(0,valoresBultoAbierto.indexOf(",")));
		let ultimoBultoAbierto=parseInt(valoresBultoAbierto.substr(valoresBultoAbierto.lastIndexOf(",")+1));
		let valorBultoGrid=parseInt($("#"+idActual).val());

//		events_116_calcularBulto(fila);
		
		// antes de actualizar el valor del bulto abierto en la lista de proveedores
		// , se comprueba que el bulto indicado es uno de los bultos abiertos disponibles.
		if (ultimoBultoAbierto != -1
			&& $("#"+idActual).val() >= primerBultoAbierto && $("#"+idActual).val() <= ultimoBultoAbierto){
			//se actualiza en la lista de proveedores el último bulto abierto asignado.
			ultimoBultoIntroducido[provrGenFilaValor]=$("#"+idActual).val();
		}else{
			$("#"+idActual).val(ultimoBultoIntroducido[provrGenFilaValor]);
			$("#p116_lbl_Error5").css("display", "block");
//			document.getElementById("p116_lbl_Error5").style.display = "block";
		}
	
	}
}

function events_116_validarBulto(indice){
	
	let campoCantidad="p116_fld_cantidad"+indice;
	let campoBulto="p116_fld_bulto"+indice;
	let valorBulto=document.getElementById(campoBulto).value;
//	let campoBultoCaja="pda_p103_btn_cajaBulto"+indice;
//	let bultoCerrado=document.getElementById(campoBulto).className.indexOf('readonly');
	
	if (!document.getElementById(campoCantidad).hasAttribute("readonly")){
		
		if (validacionBultoP116(indice)=='S' && valorBulto!=''){
			document.getElementById("p116_lbl_Error").style.display = "none";
			let listaBultos=document.getElementById('p116_listaBultos').value;
			let aux = listaBultos.split(",");
			let bultoValido=false;
			
			for(var i = 0; i<aux.length && !bultoValido; i++){
				if(valorBulto==aux[i]){
					bultoValido=true;
				}
			}
			
			let campoCantidad="p116_fld_cantidad"+indice;
			let valorCampoCantidad = document.getElementById(campoCantidad).value;
			if (!bultoValido){
				document.getElementById(campoBulto).value="";
				document.getElementById("p116_lbl_Error5").style.display = "block";
				document.getElementById("p116_lbl_Error").style.display = "none";
				document.getElementById("p116_lbl_Error2").style.display = "none";
				document.getElementById("p116_lbl_Error4").style.display = "none";
//				document.getElementById(campoBultoCaja).style.display = "none";
//				var listaBultos=document.getElementById('p116_listaBultos').value;
//				let aux = listaBultos.split(","); 
				for(var i = 0; i<aux.length && document.getElementById(campoBulto).value=="" ; i++){
					let existebulto=false;
					for(var j = 0; j<5 ; j++){
						let campoBultoComp="p116_fld_bulto"+j;
						let valorBultoComp=document.getElementById(campoBultoComp).value;
						if(valorBultoComp==aux[i]){
							existebulto=true;
						}
					}
					if(!existebulto){
						document.getElementById(campoBulto).value=aux[i];
					}
				}
//				if(valorCampoCantidad!=''){
//					document.getElementById(campoBultoCaja).style.display = "block";
//				}
			}else{
				let valorEstadoCerrado="p116_estadoCerrado"+indice;
				
				if(valorCampoCantidad!=''){
					document.getElementById("p116_lbl_Error").style.display = "none";
					document.getElementById("p116_lbl_Error2").style.display = "none";
					document.getElementById("p116_lbl_Error4").style.display = "none";
					document.getElementById(valorEstadoCerrado).value='N';
//					document.getElementById(campoBultoCaja).style.display = "block";
				}else{
					document.getElementById("p116_lbl_Error").style.display = "none";
					document.getElementById("p116_lbl_Error2").style.display = "none";
					document.getElementById("p116_lbl_Error4").style.display = "none";
					document.getElementById(valorEstadoCerrado).value='N';
//					document.getElementById(campoBultoCaja).style.display = "none";
				}
			}
		}else{
			if(valorBulto!=''){
				document.getElementById(campoBulto).focus();
				document.getElementById(campoBulto).select();
				//ERRORES EN LA VALIDACIÓN DE LA CANTIDAD POR LO QUE PONEMOS EL FOCO EN LA CANTIDAD Y MOSTRAMOS EL ERROR
				document.getElementById("p116_lbl_Error").style.display = "block";
				document.getElementById("p116_lbl_Error2").style.display = "none";
				document.getElementById("p116_lbl_Error4").style.display = "none";
//				document.getElementById(campoBultoCaja).style.display = "none";
			}else{
				document.getElementById("p116_lbl_Error").style.display = "none";
				document.getElementById("p116_lbl_Error2").style.display = "none";
				document.getElementById("p116_lbl_Error4").style.display = "none";
//				document.getElementById(campoBultoCaja).style.display = "none";
			}
		}
	}
}

function events_116_calcularBulto(indice){

	//Obtención de fila y columna actuales
//	let indice = idActual.charAt(idActual.length - 1);

	let filaBultoActual = "#p116_fld_bulto"+indice;

	let campoCantidad = "p116_fld_cantidad"+indice;
	let valorCantidad = document.getElementById(campoCantidad).value;
//	var campoBultoCaja = "pda_p103_btn_cajaBulto"+indice;
//	var bultoCerrado = document.getElementById(campoCantidad).className.indexOf('pda_p103_readonly');
//	
	if (!document.getElementById(campoCantidad).hasAttribute("readonly")){

		if (validacionCantidadP116(indice) == 'S'){
			
			document.getElementById("p116_lbl_Error").style.display = "none";
			let campoBulto="p116_fld_bulto"+indice;
			let valorBulto=document.getElementById(campoBulto).value;
			
			if (valorBulto=="" && valorCantidad!=""){
				
				let listaBultos=document.getElementById('p116_listaBultos').value;
				let aux = listaBultos.split(","); 
				let valorEstadoCerrado="p116_estadoCerrado"+indice;
//				document.getElementById(campoBultoCaja).style.display = "block";
				document.getElementById(valorEstadoCerrado).value='N';
				document.getElementById("p116_lbl_Error4").style.display = "none";
				
				for (var i = 0; i<aux.length && document.getElementById(campoBulto).value=="" ; i++){
					let existebulto=false;
					for(var j = 0; j<5 ; j++){
						let campoBultoComp="p116_fld_bulto"+j;
						let valorBultoComp=document.getElementById(campoBultoComp).value;
						if(valorBultoComp==aux[i]){
							existebulto=true;
						}
					}
					if(!existebulto){
						document.getElementById(campoBulto).value=aux[i];
					}
					
				}
			}else if( valorCantidad==""){
//				document.getElementById(campoBultoCaja).style.display = "none";
				document.getElementById("p116_lbl_Error4").style.display = "none";
			}else if(valorCantidad!=""){
				var valorEstadoCerrado="p116_estadoCerrado"+indice;
//				document.getElementById(campoBultoCaja).style.display = "block";
				document.getElementById(valorEstadoCerrado).value='N';
				document.getElementById("p116_lbl_Error4").style.display = "none";
			}
		}else{
			if(valorCantidad!=''){
				document.getElementById(campoCantidad).focus();
				document.getElementById(campoCantidad).select();
				//ERRORES EN LA VALIDACIÓN DE LA CANTIDAD POR LO QUE PONEMOS EL FOCO EN LA CANTIDAD Y MOSTRAMOS EL ERROR
				document.getElementById("p116_lbl_Error").style.display = "block";
				document.getElementById("p116_lbl_Error2").style.display = "none";
				document.getElementById("p116_lbl_Error3").style.display = "none";
				document.getElementById("p116_lbl_Error4").style.display = "none";
//				document.getElementById(campoBultoCaja).style.display = "none";
			}else{
				document.getElementById("p116_lbl_Error").style.display = "none";
//				document.getElementById(campoBultoCaja).style.display = "none";
				document.getElementById("p116_lbl_Error4").style.display = "none";
			}
		}
	}
}

function events_p116_eliminar(indice){
	let campoCantidadEliminar='p116_fld_cantidad'+indice;
	let campoBultoEliminar='p116_fld_bulto'+indice;
//	let campoEstadoEliminar='p116_estadoCerrado'+indice;
//	document.getElementById(campoCantidadEliminar).value='';
//	document.getElementById(campoBultoEliminar).value='';
//	document.getElementById(campoEstadoEliminar).value='N';
	$("#p116_fld_cantidad"+indice).val('');
	$("#p116_fld_bulto"+indice).val('');
	$("#p116_estadoCerrado"+indice).val('N');
	document.getElementById(campoCantidadEliminar).className = document.getElementById(campoCantidadEliminar).className.replaceAll(' pda_p103_readonly','');
	document.getElementById(campoBultoEliminar).className = document.getElementById(campoBultoEliminar).className.replaceAll(' pda_p103_readonly','');	
}

function validacionCantidadP116(indice){

	const regexEntero = /^-?\d+$/;

//	let idx = indice;
//	
//	if (indice === 0){
//		idx = idx + 1;
//	}

	let resultadoValidacion = 'S';
	let id = 'p116_fld_cantidad'+indice;
	let campoActual = document.getElementById(id);
	
	if (campoActual != null && typeof(campoActual) != 'undefined') {

//		let flgPesoVariablePantalla=document.getElementById(idx+'_flgPesoVariable').value;
		let flgPesoVariablePantalla=document.getElementById("p116_fld_flgPesoVariable").value;
		let numeroCantidadIsOk;
		
		if (flgPesoVariablePantalla=='S'){
			//Validación número decimal
			numeroCantidadIsOk = /^(-?\d+((\,|\.)\d{0,3}){0,1})?$/.test(campoActual.value);
		}else{
			//Validación número entero
			numeroCantidadIsOk = regexEntero.test(campoActual.value);
		}
		if (!numeroCantidadIsOk){
			resultadoValidacion = 'N';
		}else{
			resultadoValidacion = 'S';
		}
	}
	
	return resultadoValidacion;
}

function validacionBultoP116(indice){
	var resultadoValidacion = 'S';
	var idBulto = 'p116_fld_bulto'+indice;
	var campoActualBulto = document.getElementById(idBulto);
	if (campoActualBulto != null && typeof(campoActualBulto) != 'undefined') {
		var numeroBultoIsOk = /^\d{0,2}$/.test(campoActualBulto.value);
		if (!numeroBultoIsOk){
			resultadoValidacion = 'N';
		}
	}
	return resultadoValidacion;
}
