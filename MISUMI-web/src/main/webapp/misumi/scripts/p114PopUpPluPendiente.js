/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/****************************************************************** CONSTANTES ***************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

var indiceListaReferenciasSinPLU;
var totalListaReferenciasSinPLU;
var insertarPlu=true;
var pluValido=true;

$(document).ready(function(){
	initializeScreenP114PopupPluPendiente();

});

function initializeScreenP114PopupPluPendiente(){
	$( "#p114_popupPluPendiente").dialog({
		autoOpen: false,
		height: 'auto',
		width: 'auto',
		modal: true,
		resizable: false,
		buttons:[{
			id:"p114_btn_guardar",
			text: "Guardar",
			click: function() {
				$("#p114_btn_guardar").prop("disabled", true);
				guardarP114();
			}
		}],
		open: function() {
			$('#p114_popupPluPendiente .ui-dialog-titlebar-close').on('mousedown', function(){
				$('#p114_popupPluPendiente').dialog('close');
			});
			totalListaReferenciasSinPLU=listaReferenciasSinPLU.length;
			indiceListaReferenciasSinPLU=1;
			events_p114_flecha_sig();
			events_p114_flecha_ant();
			cargarDatosPantalla(indiceListaReferenciasSinPLU);
			events_p114_focusOut();
			actualizarNumeroDePaginaP114(indiceListaReferenciasSinPLU,totalListaReferenciasSinPLU);
			events_p114_mensajeError();
		},
		close:function(){
			cleanP114();
		}
	});
	
}

function cleanP114(){
	listaReferenciasSinPLU = new Array();
	$("#p114_pagSiguiente").unbind("click");
	$("#p114_pagAnterior").unbind("click");
	listaReferenciasSinPLU =null;
}


function guardarP114(){
	if(pluValido){
		var pluPantalla=$("#p114_plu_propuesto").val();
		listaReferenciasSinPLU[indiceListaReferenciasSinPLU-1].plu=pluPantalla;
		var objJson = prepareObjectJSON();
		$.ajax({
			url : './detalladoPedido/guardarPLUsPendientes.do',
			type: 'POST',
			contentType: "application/json",
			dataType : "json",
			data: objJson,
			success : function(data) {
				if(data != null && data.length > 0){
					cleanP114();
					listaReferenciasSinPLU = data;
					totalListaReferenciasSinPLU=listaReferenciasSinPLU.length;
					indiceListaReferenciasSinPLU=1;
					events_p114_flecha_sig();
					events_p114_flecha_ant();
					cargarDatosPantalla(indiceListaReferenciasSinPLU);
					events_p114_focusOut();
					actualizarNumeroDePaginaP114(indiceListaReferenciasSinPLU,totalListaReferenciasSinPLU);
					events_p114_mensajeError();
					var datosConError=false;
					for (i = 0; i < data.length; i++){
						if(data[i].codError != null && data[i].codError != 0){
							datosConError=true;
						}
					}
					if(datosConError){
						createAlert("Hay alguna(s) referencia(s) que no se ha(n) guardado", "INFO");
					}else{
						createAlert("GUARDADO CORRECTAMENTE", "INFO");
					}
				}else{
					createAlert("GUARDADO CORRECTAMENTE", "INFO");
					cleanP114();
					$('#p114_popupPluPendiente').dialog('close');
				}
				
				$("#p114_btn_guardar").prop("disabled", false);
			},
			error : function (xhr, status, error){
				handleError(xhr, status, error, locale);
			}			
		});		
		
	}
	
}

function prepareObjectJSON(){
	var buscador = {
		"listadoSeleccionados" : listaReferenciasSinPLU
	};
	return $.toJSON(buscador);
}

function events_p114_focusOut(){
	$("#p114_plu_propuesto").keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	});
	
	$("#p114_plu_propuesto").focusout(function(){
		var plu = $("#p114_plu_propuesto").val();
		if (plu!="" ){
			var validation = validate_plu(plu);
			if (validation!=""){
				$("#p114_mensaje_plu_propuesto").text(validation);
				$("#p114_mensaje_plu_propuesto").show();
			}else{
				$("#p114_mensaje_plu_propuesto").hide();
			}
		}else{
			pluValido=false;
			$("#p114_mensaje_plu_propuesto").text("Debe introducir un PLU");
			$("#p114_mensaje_plu_propuesto").show();
		}
	});
}

function events_p114_mensajeError(){
	
	$("#p114_lbl_mensajeErrorGuardado").text(listaReferenciasSinPLU[indiceListaReferenciasSinPLU-1].descError);
	$("#p114_div_mensajeErrorGuardado").show();
	
	$("#p114_mensaje_plu_propuesto").hide();
}

function validate_plu(plu){
	var lista_plus_libres = $("#p114_plus_libres").prop("title");
	lista_plus_libres = lista_plus_libres.split(":")[1];
	lista_plus_libres = lista_plus_libres.split(",");
	
	var found = false;
	$.each(lista_plus_libres, function(index,value){
		var item = lista_plus_libres[index].trim();
		if (item == plu){
			found = true;
			return;
		}
	});
	if (!found && plu!=0){
		pluValido=false;
		return "El PLU introducido no está libre";
	}else{
		pluValido=true;
		return "";
	}
	
}

function load_plus_libres(indice){
	
	$.ajax({
		type : 'GET',			
		url : './alarmasPLU/loadPLUsLibres.do' + createParamsUrl(indice),
		success : function(data) {	
			if(data != null){
				var itemsStr="";
				data.forEach(function(item, index) {
					// La primera posición de la lista de PLUs contiene el Nº Máximo de PLUs de la balanza.
					if (index==0){
						// Si el valor del Nº máximo de PLUs viene negativo significa que no existía
						// el registro en la tabla y por lo tanto hay que marcar el valor en rojo.
						if (item < 0){
							item = (-1)*item;
							$("#p114_plu_max_libre").attr("style", "color:red");
						}else{
							$("#p114_plu_max_libre").attr("style", "color:black");
						}
						$('#p114_plu_max_libre').val(item);
						$('#p114_plu_max_libre_copia').val(item);
					// La segunda posición de la lista de PLUs contiene el Nº Máximo de PLUs asignado de la balanza.
					}else if (index==1){
						$('#p114_plu_max_asignado').val(item);
					}else{
						insertarPlu=true;
						for(var i = 0; i<listaReferenciasSinPLU.length; i++){
							if(listaReferenciasSinPLU[i].plu == item && i!=indiceListaReferenciasSinPLU-1 && listaReferenciasSinPLU[i].codAgrupacionBalanza==listaReferenciasSinPLU[indiceListaReferenciasSinPLU-1].codAgrupacionBalanza){
								//existe en la lista de la pantalla por lo que el plu no puede ser asignado
								insertarPlu=false;
							}
						}
						if(insertarPlu){
							itemsStr+= item+", ";
						}
					}
				});						
				
				itemsStr = itemsStr.slice(0,-2);
				
				$('#p114_plus_libres').prop("title", function() {
				    return "Lista de PLUs libres: " + itemsStr;
				});
				var lista_plus_libres_pantalla = itemsStr.split(",");
				var pluStr="";
				for(var j = 0; j<4 && j<lista_plus_libres_pantalla.length; j++){
					pluStr+= lista_plus_libres_pantalla[j]+", ";
				}
				var pluPropuesto=listaReferenciasSinPLU[indice-1].plu;
				if(pluPropuesto==0){
					$("#p114_plu_propuesto").val(lista_plus_libres_pantalla[0]);
				}else{
					$("#p114_plu_propuesto").val(pluPropuesto);
				}
				
				// Borramos la coma del final
				pluStr = pluStr.slice(0,-2);
				if (lista_plus_libres_pantalla.length>4){
					pluStr = pluStr+"...";
				}
				$('#p114_plus_libres').text(pluStr);
			}

		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});	
}

function createParamsUrl(indice){
	var codCentro = listaReferenciasSinPLU[indice-1].codCentro;
	var url = "?codCentro="+codCentro;
	
	var codAgrupacion = listaReferenciasSinPLU[indice-1].codAgrupacionBalanza;
	url +=  "&agrupacion="+codAgrupacion;
	
	return url;
}
/************************ EVENTOS ************************/
//Evento para flechas 
function events_p114_flecha_ant(){
	$("#p114_pagAnterior").click(function () {		
		paginacionFormulariosP114('N');
	});	
}

//Evento para flechas 
function events_p114_flecha_sig(){
	$("#p114_pagSiguiente").click(function () {		
		paginacionFormulariosP114('S');
	})
}

//Función que sirve para paginar entre los formularios
function paginacionFormulariosP114(flechaSig){
	if(pluValido){
		var pluPantalla=$("#p114_plu_propuesto").val();
		listaReferenciasSinPLU[indiceListaReferenciasSinPLU-1].plu=pluPantalla;
		if(flechaSig == 'S'){
			//Si se ha presionado la flecha siguiente
			indiceListaReferenciasSinPLU++;
		}else{
			//Si se ha presionado la flecha anterior
			indiceListaReferenciasSinPLU--;
		}
		cargarDatosPantalla(indiceListaReferenciasSinPLU);
		actualizarNumeroDePaginaP114(indiceListaReferenciasSinPLU,totalListaReferenciasSinPLU);
		events_p114_mensajeError();
	}
}

function actualizarNumeroDePaginaP114(idxIndiceListaP114,idxTamanoListaP114){

	//Si es el primer registro de la lista se tiene q ocultar las flechas de anterior porque no existe
	if(idxIndiceListaP114 - 1 == 0){
		$("#p114_pagAnterior").hide();
	}else{
		$("#p114_pagAnterior").show();
	}
	//Si es el último registro de la lista se tiene q ocultar las flechas de posterior porque no existe
	if(idxIndiceListaP114 < idxTamanoListaP114){
		$("#p114_pagSiguiente").show();
	}else{
		$("#p114_pagSiguiente").hide();
	}

	//Actualiza el número de debajo de los formularios. 
	//Indica cuántos formularios se han mostrado y el total de 
	//formularios a mostrar.
	$("#p114_lbl_indiceLista").text(idxIndiceListaP114+ " / " +idxTamanoListaP114);

	//Enseñamos el número de página
	$("#p114_componenteNavegacion").show();
}

//Función que sirve para recuperar todos los datos de la referencia que se muestran en pantalla
function cargarDatosPantalla(indice){
	$("#p114_codArtVal").val(listaReferenciasSinPLU[indice-1].codArt);
	$("#p114_descArtVal").val(listaReferenciasSinPLU[indice-1].descArt);
	$('#p114_agrupacionBalanza').text(listaReferenciasSinPLU[indice-1].codAgrupacionBalanza+ " - " +listaReferenciasSinPLU[indice-1].descAgrupacionBalanza);
	load_plus_libres(indice);
	
	actualizarNumeroDePaginaP114(indice,totalListaReferenciasSinPLU)
	
}


