var RESET_CONST="reset";
var referenceRequired=null;
var centerRequired=null;
var emptyRecords=null;
var noHayPedidoNingunDia=null;
var hoyNoHayPedidoPeroSiOtroDia=null;
var stockBajo = null;
var stockAlto = null;
var parrafoPedidoAutomaticoActivo="";
var codArtRelacionado;
var codArtSustitutaDe;
var codArtSustituidaPor;
var valStock;
var P13Page = false;

$(document).ready(function(){

	events_p13_caprabo_btn_reset();
	events_p13_caprabo_btn_buscar();
	
	events_p13_caprabo_btn_refActiva();

	event_Sustituye_A_Ref();
	event_Sustituida_Por_Ref();
	
	
	$(document).on('CargadoCentro', function(e) { 
		loadP13(locale);
		initializeScreen();
		$("#p13_caprabo_fld_referencia").focus();
	});
	

	
	/* Existen eventos que se disparan al interactuar con el area de resultados. Estos eventos solo es posible dispararlo cuando existe una referencia cargada,
	 * pero alguno de ellos, realiza una nueva consulta al ser disparado con los datos existentes en el input de consulta. Este comportamiento da lugar a que
	 * con una consulta cargada, si se introduce una nueva referencia en el input, provoque errores. 
	 * Por ello, cada vez que hagamos foco en el input, reseteo el area de resultados. 
	*/
	$("#p13_caprabo_fld_referencia").focusin(function(e) {
		//resetResultados();
		$("#p13_caprabo_fld_referencia").select();
	});
	
  });

function initializeScreen(){

	$("#p13_caprabo_fld_descripcionRef").attr("disabled", "disabled");

}


function events_p13_caprabo_btn_reset(){
	$("#p13_caprabo_btn_reset").click(function () {
		$("#p13_caprabo_fld_referencia").val("");
		$("#p13_caprabo_fld_referencia").focus();
		resetResultados();
	});
}

function events_p13_caprabo_btn_buscar(){
	$("#p13_caprabo_btn_buscar").click(function () {
		p13Page = true;
		finder();
	});	
}


function event_Sustituye_A_Ref(){

	$( "#p13_caprabo_SustituyeARefFieldsetEnlace" )
    .click(function() {
    	$("#p13_caprabo_fld_referencia").val(codArtSustitutaDe);
    	finder();
    });
}

function event_Sustituida_Por_Ref(){

	$( "#p13_caprabo_SustituidaPorRefFieldsetEnlace" )
    .click(function() {
    	$("#p13_caprabo_fld_referencia").val(codArtSustituidaPor);
    	finder();
    });
}


function finder(arg){
		
		findReferenciaP13();

}

	
function findReferenciaP13(){			

	var messageVal=null;
	resetResultados();
	
	//Limpiar elementos de correccion
	//$("#p13_caprabo_divStockActualOkCorreccion").hide();
	//$("#p13_caprabo_divStockActualErroneoCorreccion").hide();
	
	$("#p13_caprabo_divActivoCorreccion").hide();
	$("#p13_caprabo_divNoActivoCorreccion").hide();

	$("#p13_caprabo_divSustituyeARefCorreccion").hide();
	$("#p13_caprabo_divSustituidaPorRefCorreccion").hide();
	
	var txtBusqueda= $("#p13_caprabo_fld_referencia").val();
	var messageVal=findReferenciaValidation(txtBusqueda);
	
	if (messageVal==null){	
		
		/*
		//Comprueba si estoy estoy intentando relaizar la consulta desde la pagina P13 o desde informacion Basica de referencia	
		if ($('#p13_caprabo_fld_refPagina').val()=='p30_btn_masInfoRef') {
			$('#p13_caprabo_fld_refPagina').val('');
			reloadMaestros();
			
		}else{
			
			//Si se trata de una busqueda alfanumerica o alfabetica
			if (!isInt(txtBusqueda)){
				reloadDataP34(txtBusqueda, "consultaDatos","", true, false);
			}
			//Si se trata de una busqueda numerica
			else{
				//Si la REFERENCIA mayor de 8 digitos se busca como EAN, 
				//Si no hay registros como EAN se buscará como MODELO PROVEEDOR
				//En el caso que no eista MODELO PROVEEDOR si no hay registros como DENOMINACION
				alfaNumerico = false;
				//reloadMaestros();
				if(txtBusqueda.length > 8)
				{
					ean= true;
					reloadDataP34(txtBusqueda, "consultaDatos","",false,ean);
				}
				else
				{
					ean = false;
					reloadDataP34(txtBusqueda, "consultaDatos","",false,ean);//reloadMaestros();			}
					
				}
				
			}
		}
		*/
		
		reloadMaestros();
	}
	else		
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");		
			
}
function findMasInfReferenciaP13(){			

	var messageVal=null;
	resetResultados();
	
	//Limpiar elementos de correccion
	//$("#p13_caprabo_divStockActualOkCorreccion").hide();
	//$("#p13_caprabo_divStockActualErroneoCorreccion").hide();
	$("#p13_caprabo_divActivoCorreccion").hide();
	$("#p13_caprabo_divNoActivoCorreccion").hide();
	$("#p13_caprabo_divPedidoAdicionalCorreccion").hide();
	//$("#p13_caprabo_divNoActivoCorreccion").show();
	//$("#p13_caprabo_divPedidoAdicionalCorreccion").show();
	$("#p13_caprabo_divFfppActivaCorreccion").hide();
	$("#p13_caprabo_divUnitariaCorreccion").hide();
	$("#p13_caprabo_divSustituyeARefCorreccion").hide();
	$("#p13_caprabo_divSustituidaPorRefCorreccion").hide();
	
	var txtBusqueda= $("#p13_caprabo_fld_referencia").val();
	var messageVal=findReferenciaValidation(txtBusqueda);
			
	if (messageVal==null){		
		//Si se trata de una busqueda alfanumerica o alfabetica
		if (isNaN(txtBusqueda))
			reloadDataP34(txtBusqueda, "consultaDatos","", true, false);
			
		//Si se trata de una busqueda numerica
		else
			reloadMaestros();
		
	}
	else		
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");		
			
}




function findReferenciaValidation(txtBusqueda){		
	var messageVal=null;		
			
	if ($("#centerId").val()==null || $("#centerId").val()==""){		
		messageVal=centerRequired;		
	}			
	else if (txtBusqueda==null || txtBusqueda==""){		
		messageVal=referenceRequired;   		
	}		
	else if (isNaN(txtBusqueda) && txtBusqueda.length<4){		
		messageVal=referenceMinCaracters;		
	}		
			
	return messageVal;		
}


function events_p13_caprabo_btn_refActiva(){
	$("#p13_caprabo_btn_refActiva").click(function () {
		createAlert(replaceSpecialCharacters("Has pulsado en pedir. Pendiente de implementar."), "INFO");
	});	
}


function resetDatosMaestroFijo (){
	$("#p13_caprabo_fld_descripcionRef").val("");
	$("#p13_caprabo_fld_refActiva").val("");
	$("#p13_caprabo_fld_mapaHoy").val("");
	$("#p13_caprabo_fld_tieneFoto").val("N");

	$("#p13_caprabo_pedidoAutomaticoFieldsetActivo").attr("style", "display:none");
	$("#p13_caprabo_pedidoAutomaticoFieldsetNoActivo").attr("style", "display:none");
	
}


function resetResultados (){
	$("#p13_caprabo_descripcionRef").hide();
	$("div#AreaMaestrosFijoCaprabo").attr("style", "display:none");
	$("div#p13_caprabo_AreaPestanas").attr("style", "display:none");

	resetDatosMaestroFijo();	
}

function loadP13(locale){
	
	this.i18nJSON = './misumi/resources/p13ReferenciasCentroCaprabo/p13referenciasCentroCaprabo_' + locale + '.json';
	
	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				emptyRecords= data.emptyRecords;
				centerRequired=data.centerRequired;
				referenceRequired = data.referenceRequired;
				noHayPedidoNingunDia=data.noHayPedidoNingunDia;
				hoyNoHayPedidoPeroSiOtroDia=data.hoyNoHayPedidoPeroSiOtroDia;
				stockBajo = data.stockBajo;
				stockAlto = data.stockAlto;
				parrafoPedidoAutomaticoActivo = data.parrafoPedidoAutomaticoActivo;
				if  ($("#p13_caprabo_fld_referencia").val()!=""){
					finder();
				}

			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
           });
}

function updateResultadosMaestroFijo (data){
	
	
	resetearStyleReferenciasCentroPedir();
	
	$("#p13_caprabo_fld_descripcionRef").val(data.descArtCaprabo);
	$("#p13_caprabo_fld_refActiva").val(data.surtidoTienda.pedir);
	$("#p13_caprabo_fld_mapaHoy").val(data.surtidoTienda.mapaHoy);
	$("#p13_caprabo_fld_tieneFoto").val(data.tieneFoto);
	
	
	//Evaluación del dato pedir para y mapaHoy para mostrar el mensaje de pedido automático
	if (data.surtidoTienda.pedir != null && data.surtidoTienda.pedir == 'S'){
		
		if (data.strFechaGen != null && data.mostrarFechaGen){ //Si tiene strFechaGen hay que indicar desde cuando esta activo el aprovisionamiento centralizado
			var diaFecha = parseInt(data.strFechaGen.substring(0,2),10);
			var mesFecha = parseInt(data.strFechaGen.substring(2,4),10);
			var anyoFecha = parseInt(data.strFechaGen.substring(4),10);
			var fechaCompleta = anyoFecha + "-" + mesFecha + "-" + diaFecha;
			
			fechaFormateada = $.datepicker.formatDate("D dd-M-yy",  devuelveDate(fechaCompleta),{
				dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
				dayNames: $.datepicker.regional[ "es" ].dayNames,
				monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
				monthNames: $.datepicker.regional[ "es" ].monthNames
				});
			
			$(".p13_caprabo_parrafoPedidoAutomaticoActivo").text(parrafoPedidoAutomaticoActivo.replace("#VALOR#", "desde el " + fechaFormateada));
		} 
		else{
			$(".p13_caprabo_parrafoPedidoAutomaticoActivo").text(parrafoPedidoAutomaticoActivo.replace("#VALOR#", ""));
		}
	
		mostrarMensajePedidoActivo();

	}else{
		mostrarMensajePedidoNoActivo(data);
	}
	
	
	
	//Evaluación del mostrado del Filedset  "Sustituye a la Ref" o "Sustituida por la ref"
	if ((data.sustitutaDe != null) && (data.sustitutaDe != "0")) {
		codArtSustitutaDe = data.sustitutaDe;
		mostrarMensajeSustituyeARef();
	} else if ((data.sustituidaPor != null) && (data.sustituidaPor != "0")){
			codArtSustituidaPor = data.sustituidaPor;
			mostrarMensajeSustituidaPorRef();
	} else {
		ocultarMensajeSustituyeYSustituida();
	}

	/*if (data.valoresStock){
		valStock = data.valoresStock;
		
		$("#p13_caprabo_referenciasCentroStockMensajeError").css("display", "none");
	
		if(valStock.stock >= 0){
			$("#p13_caprabo_stockActualFieldsetOk").attr("style", "display:inline");
			$("#p13_caprabo_stockActualFieldsetErroneo").attr("style", "display:none");
			$(".p13_caprabo_parrafoStockActualOk").text($.formatNumber(valStock.stock,{format:"0.#",locale:"es"}) + " -> " + $.formatNumber(valStock.diasStock,{format:"0.#",locale:"es"}) + " días");
	
		} else {
			$("#p13_caprabo_stockActualFieldsetErroneo").attr("style", "display:inline");
			$("#p13_caprabo_stockActualFieldsetOk").attr("style", "display:none");
			$(".p13_caprabo_parrafoStockActualErroneo").text($.formatNumber(valStock.stock,{format:"0.#",locale:"es"}) + " -> " + $.formatNumber(valStock.diasStock,{format:"0.#",locale:"es"}) + " días");

		}
	
	} else {
		valStock = null;
		$("#p13_caprabo_stockActualFieldsetOk").attr("style", "display:none");
		$("#p13_caprabo_stockActualFieldsetErroneo").attr("style", "display:none");
		
	}*/
	


}




function mostrarMensajePedidoActivo(){
	//Mostrar el mensaje de pedido automático activo
	$("#p13_caprabo_pedidoAutomaticoFieldsetActivo").attr("style", "display:inline");
	$("#p13_caprabo_pedidoAutomaticoFieldsetNoActivo").attr("style", "display:none");
}

function mostrarMensajePedidoNoActivo(data){
	
	$("#p13_caprabo_parrafoPedidoAutomaticoNoActivoConEnlace").attr("style", "display:none");
	$("#p13_caprabo_parrafoPedidoAutomaticoNoActivoSinEnlace").attr("style", "display:none");
	//Mostrar el mensaje de pedido automático no activo;
	if (data.motivoCaprabo!=null && data.motivoCaprabo !=null && data.motivoCaprabo !="") {
		$("#p13_caprabo_parrafoPedidoAutomaticoNoActivoConEnlace").attr("style", "display:inline");
		
		$( "#p15_caprabo_pedidoAutomaticoFieldsetNoActivoConEnlace" ).unbind("click");
		//Asignamos evento al link para que habra el popUp con los motivos
		$( "#p15_caprabo_pedidoAutomaticoFieldsetNoActivoConEnlace" )
		    .bind("click",function() {
		    	$( "#p18_caprabo_popupPedir" ).dialog( "option", "title", tituloVentanaPopupPedir);
		    	$( "#p18_caprabo_popupPedir" ).dialog( "open" );
		    	reloadDataP18();
		  });

	} else {
		$("#p13_caprabo_parrafoPedidoAutomaticoNoActivoSinEnlace").attr("style", "display:inline");
	}
	
	$("#p13_caprabo_pedidoAutomaticoFieldsetActivo").attr("style", "display:none");
	$("#p13_caprabo_pedidoAutomaticoFieldsetNoActivo").attr("style", "display:inline");
}


function mostrarMensajeSustituyeARef(){
	//Mostrar el mensaje de Suatituye a la ref
	$("#p13_caprabo_SustituyeARefFieldset").attr("style", "display:inline");
	$("#p13_caprabo_SustituidaPorRefFieldset").attr("style", "display:none");
}

function mostrarMensajeSustituidaPorRef(){
	//Mostrar el mensaje de Suatituye a la ref
	$("#p13_caprabo_SustituidaPorRefFieldset").attr("style", "display:inline");
	$("#p13_caprabo_SustituyeARefFieldset").attr("style", "display:none");
	
}

function ocultarMensajeSustituyeYSustituida() {
	$("#p13_caprabo_SustituidaPorRefFieldset").attr("style", "display:none");
	$("#p13_caprabo_SustituyeARefFieldset").attr("style", "display:none");
}

function reloadMaestros() {
	
	//En los centro caprabo, el numero referencia corresponde a COD_ART_CAPRABO.
	var vReferenciasCentro=new ReferenciasCentro(null, $("#centerId").val(), 'InforDatos', null,null,null,$("#p13_caprabo_fld_referencia").val());
	var objJson = $.toJSON(vReferenciasCentro.prepareToJsonObject());	
	
	
	
	 $.ajax({
		type : 'POST',
		url : './referenciasCentroCaprabo/loadDatosMaestrosFijo.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			if (validateReloading(data)){
				
				$("#p13_caprabo_descripcionRef").show();
				//Actualizaci�n de los datos de "Datos maestro fijo"
				updateResultadosMaestroFijo(data);

				$("div#AreaMaestrosFijoCaprabo").attr("style", "display:block");
				$("div#p13_caprabo_AreaPestanas").attr("style", "display:block");
				
				//Control alturas
				var altura = calcularAlturaMaximaReferenciasCentroPedir();

				//Para stylizar ReferenciasCentroPedir
				//styleStockActualOk(altura);
				//styleStockActualError(altura);
				//styleStockActualNoDefinido(altura);
			
				stylePedidoAutomaticoActivo(altura);
				stylePedidoAutomaticoNoActivo(altura);
		
				styleSustituyeSustituta(altura);
				
				reloadImagenComercial(data); //Esta en el p15ReferenciasCentroICCaprabo.js

			}else{
				$("#p13_caprabo_descripcionRef").hide();
				var messageVal=null;
				messageVal = emptyRecords;
				if (messageVal!=null){
					createAlert(replaceSpecialCharacters(messageVal), "ERROR");
				}
			}	
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
}

function validateReloading(data){
	if (data.length == 0 || 
			data.surtidoTienda == null || 
			data.surtidoTienda.length == 0){

		return false;
	}else{
		return true;
	}
}

function recalcularEnlacePedidoAdicional(contadores){

	if (contadores.codError == 0){
		var url = './pedidoAdicional.do?flagCancelarNuevo=S&referencia='+$("#p13_caprabo_fld_referencia").val()+'&pestanaOrigen=E&flgReferenciaCentro=S';
		if (contadores.contadorMAC > 0 && contadores.contadorEncargos == 0 && contadores.contadorMontaje == 0){
			url += '&mac=S';
		}
		$( "#p13_caprabo_pedidosAdicionalesFieldsetEnlace" ).unbind("click");
		$( "#p13_caprabo_pedidosAdicionalesFieldsetEnlace" ).click(function() {
	    	window.location=url;
	    });
	}
}

function recalcularEnlaceStockActualError(data){

	var valoresStock = data.valoresStock;
	
	$("#p13_caprabo_stockActualErroneoInfoEnlace" ).unbind("click");
	$("#p13_caprabo_stockActualErroneoInfoEnlace").click(function () {
		if(valStock.stock > valStock.stockBajo){
			reloadPopupP31(data.codCentro, 'M', data.codArt, valoresStock.stockBajo, valoresStock.sobreStockInferior, "", valoresStock.stock);
		}else{
			reloadPopupP31(data.codCentro, 'P', data.codArt, valoresStock.stockBajo, valoresStock.sobreStockInferior, "", valoresStock.stock);
		}
	});
}

function calcularAlturaMaximaReferenciasCentroPedir(){
	var alturaMax = 30; //Para establecer un minimo de altura debido a la altura del velocimetro
	var alturaAux = 0;
	
	/*if ($("#p13_caprabo_stockActualFieldsetOk").is(":visible")) {
		if (alturaMax < $("#p13_caprabo_stockActualFieldsetOk").height()) {
			alturaMax = $("#p13_caprabo_stockActualFieldsetOk").height();
		}
	} 
	if ($("#p13_caprabo_stockActualFieldsetErroneo").is(":visible")) {
		if (alturaMax < ($("#p13_caprabo_stockActualFieldsetErroneo").height())) {
			alturaMax = $("#p13_caprabo_stockActualFieldsetErroneo").height();
		}
	}
	if ($("#p13_caprabo_stockActualFieldsetNoDefinido").is(":visible")) {
		if (alturaMax < $("#p13_caprabo_stockActualFieldsetNoDefinido").height()) {
			alturaMax = $("#p13_caprabo_stockActualFieldsetNoDefinido").height();
		}
	}*/ 
 
	if ($("#p13_caprabo_pedidoAutomaticoFieldsetActivo").is(":visible")) {
		if (alturaMax < $("#p13_caprabo_pedidoAutomaticoFieldsetActivo").height()) {
			alturaMax = $("#p13_caprabo_pedidoAutomaticoFieldsetActivo").height();
			
		}
	} 
	if ($("#p13_caprabo_pedidoAutomaticoFieldsetNoActivo").is(":visible")) {
		if (alturaMax < ($("#p13_caprabo_pedidoAutomaticoFieldsetNoActivo").height())) {
			alturaMax = $("#p13_caprabo_pedidoAutomaticoFieldsetNoActivo").height();
			
		}
	}

	return alturaMax;
}

function resetearStyleReferenciasCentroPedir(){
	//$(".p13_caprabo_divStockActualOk").removeAttr( "style" );
	//$(".p13_caprabo_parrafoStockActualOk").removeAttr( "style" );
	//$(".p13_caprabo_divStockActualErroneo").removeAttr( "style" );
	//$(".p13_caprabo_parrafoStockActualErroneo").removeAttr( "style" );
	//$(".p13_caprabo_divStockActualNoDefinido").removeAttr( "style" );
	//$(".p13_caprabo_parrafoStockActualNoDefinido").removeAttr( "style" );
	//$("#p13_caprabo_stockActualErroneoInfo").removeClass( "p13_caprabo_divStockActualErrorInfo" );
	$(".p13_caprabo_divPedidoAutomaticoActivo").removeAttr( "style" );
	$(".p13_caprabo_parrafoPedidoAutomaticoActivo").removeAttr( "style" );
	$(".p13_caprabo_divPedidoAutomaticoNoActivo").removeAttr( "style" );
	$(".p13_caprabo_parrafoPedidoAutomaticoNoActivo").removeAttr( "style" );
	
}

/*function styleStockActualError(alto){
	//Para dar altura al fieldset STOCK ACTUAL ERROR
	$("#p13_caprabo_stockActualFieldsetErroneo").height(alto);
	//Para centrar los textos en el fieldset STOCK ACTUAL ERROR
	if ($("#p13_caprabo_stockActualFieldsetErroneo").is(":visible")) {
		var alturaDivStockActualError = alto - 24;
		$(".p13_caprabo_divPedidoAutomaticoNoActivo").height( alturaDivStockActualError );
		$(".p13_caprabo_divStockActualErroneo").css( "top", "20px" );
		$(".p13_caprabo_divStockActualErroneo").css( "text-align", "center");
		var alturaStockActualError = $(".p13_caprabo_parrafoStockActualErroneo").height();
		
		
		$("#p13_caprabo_stockActualErroneoInfo").addClass( "p13_caprabo_divStockActualErrorInfo" );
		$("#p13_caprabo_divStockActualErroneoCorreccion").show();
		if(valStock.mostrarMotivosStock != null &&  valStock.mostrarMotivosStock == 'S'){
			$(".p13_caprabo_divStockActualErroneo").css( "margin-top", ((alturaDivStockActualError-alturaStockActualError)/2)-15);
		}else{
			$(".p13_caprabo_divStockActualErroneo").css( "margin-top", ((alturaDivStockActualError-alturaStockActualError)/2)-10);
		}
	}
}

function styleStockActualOk(alto){
	//Para dar altura al fieldset STOCK ACTUAL OK
	$("#p13_caprabo_stockActualFieldsetOk").height(alto);
	//Para centrar los textos en el fieldset STOCK ACTUAL OK
	if ($("#p13_caprabo_stockActualFieldsetOk").is(":visible")) {
		var alturaDivStockActualOk = alto - 24;
		$(".p13_caprabo_divStockActualOk").height( alturaDivStockActualOk );
		$(".p13_caprabo_divStockActualOk").css( "top", "20px" );
		$(".p13_caprabo_divStockActualOk").css( "text-align", "center");
		var alturaStockActualOk = $(".p13_caprabo_parrafoStockActualOk").height();
		$(".p13_caprabo_parrafoStockActualOk").css( "margin-top",  ((alturaDivStockActualOk-alturaStockActualOk)/2)-10);
		$("#p13_caprabo_divStockActualOkCorreccion").show();
	}
}

function styleStockActualNoDefinido(alto){
	//Para dar altura al fieldset STOCK ACTUAL NO DEFINIDO
	$("#p13_caprabo_stockActualFieldsetNoDefinido").height(alto);
	//Para centrar los textos en el fieldset STOCK ACTUAL NO DEFINIDO
	if ($("#p13_caprabo_stockActualFieldsetNoDefinido").is(":visible")) {
		var alturaDivStockActualNoDefinido = alto - 24;
		$(".p13_caprabo_divStockActualNoDefinido").height( alturaDivStockActualNoDefinido );
		$(".p13_caprabo_divStockActualNoDefinido").css( "top", "20px" );
		$(".p13_caprabo_divStockActualNoDefinido").css( "text-align", "center");
		var alturaStockActualNoDefinido = $(".p13_caprabo_parrafoStockActualNoDefinido").height();
		$(".p13_caprabo_parrafoStockActualNoDefinido").css( "margin-top",  ((alturaDivStockActualNoDefinido-alturaStockActualNoDefinido)/2)-10);
		$("#p13_caprabo_divStockActualNoDefinidoCorreccion").show();
	}
}*/



function stylePedidoAutomaticoActivo(alto){
	//Para dar altura al fieldset APROVISIONAMIENTO CENTRALIZADO
	$("#p13_caprabo_pedidoAutomaticoFieldsetActivo").height(alto);
	//Para centrar los textos en el fieldset APROVISIONAMIENTO CENTRALIZADO
	if ($("#p13_caprabo_pedidoAutomaticoFieldsetActivo").is(":visible")) {
		//var alturaDivPedidosAutomaticoActivo = alto - 10;
		var alturaDivPedidosAutomaticoActivo = alto - 24;
		$(".p13_caprabo_divPedidoAutomaticoActivo").height( alturaDivPedidosAutomaticoActivo );
		//$(".p13_caprabo_divPedidoAutomaticoActivo").css( "position", "absolute" );
		$(".p13_caprabo_divPedidoAutomaticoActivo").css( "float", "left" );
		$(".p13_caprabo_divPedidoAutomaticoActivo").css( "top", "20px" );
		var alturaPedidoAutomaticoActivo = $(".p13_caprabo_parrafoPedidoAutomaticoActivo").height();
		$(".p13_caprabo_parrafoPedidoAutomaticoActivo").css( "margin-top",  ((alturaDivPedidosAutomaticoActivo-alturaPedidoAutomaticoActivo)/2)-10);
		//$(".p13_caprabo_parrafoPedidoAutomaticoActivo").css( "position", "absolute" );
		$(".p13_caprabo_parrafoPedidoAutomaticoActivo").css( "float", "left" );
		//$(".p13_caprabo_parrafoPedidoAutomaticoActivo").css( "top", "50%" );
		$("#p13_caprabo_divActivoCorreccion").show();
		
	}
}

function stylePedidoAutomaticoNoActivo(alto){
	//Para dar altura al fieldset APROVISIONAMIENTO CENTRALIZADO
	$("#p13_caprabo_pedidoAutomaticoFieldsetNoActivo").height(alto);
	//Para centrar los textos en el fieldset APROVISIONAMIENTO CENTRALIZADO
	if ($("#p13_caprabo_pedidoAutomaticoFieldsetNoActivo").is(":visible")) {
		var alturaDivPedidosAutomaticoNoActivo = alto - 24;
		$(".p13_caprabo_divPedidoAutomaticoNoActivo").height( alturaDivPedidosAutomaticoNoActivo );
		//$(".p13_caprabo_divPedidoAutomaticoNoActivo").css( "position", "absolute" );
		$(".p13_caprabo_divPedidoAutomaticoNoActivo").css( "float", "left" );
		$(".p13_caprabo_divPedidoAutomaticoNoActivo").css( "top", "20px" );
		var alturaPedidoAutomaticoNoActivo = $(".p13_caprabo_parrafoPedidoAutomaticoNoActivo").height();
		$(".p13_caprabo_parrafoPedidoAutomaticoNoActivo").css( "margin-top", ((alturaDivPedidosAutomaticoNoActivo-alturaPedidoAutomaticoNoActivo)/2)-10);
		//$(".p13_caprabo_parrafoPedidoAutomaticoNoActivo").css( "position", "absolute" );
		$(".p13_caprabo_parrafoPedidoAutomaticoNoActivo").css( "float", "left" );
		//$(".p13_caprabo_parrafoPedidoAutomaticoNoActivo").css( "top", "50%" );
		$("#p13_caprabo_divNoActivoCorreccion").show();
	}
}


function styleFfppActiva(alto){
	//Para dar altura al fieldset FFPP ACTIVA
	
	if ($("#p13_caprabo_esExplorer").val()== "ie") { //Estamos con Explorer
		alto = alto + 16;	
	}
	
	$("#p13_ffppActivaFieldset").height((alto/2) - 1);
	//Para centrar los textos en el fieldset FFPP ACTIVA
//	if ($("#p13_ffppActivaFieldset").is(":visible")) {
	//	var alturaDivFfppActiva = (alto/2) - 24;
	//	$(".p13_divFfppActiva").height( alturaDivFfppActiva );
	//	var alturaFfppActiva = $(".p13_parrafoFfppActiva").height();
	//	if ($("#p13_esExplorer").val()== "ie") { //Estamos con Explorer
	//		$(".p13_divFfppActiva").css( "margin-top", alturaDivFfppActiva/2);
	//	}else{
	//		$(".p13_parrafoFfppActiva").css( "margin-top", ((alturaDivFfppActiva/2)-10));
	//	}
	//	$("#p13_divFfppActivaCorreccion").show();
	//}
}



function styleSustituyeARef(alto){
	//Para dar altura al fieldset SustituyeARef

	if ($("#p13_caprabo_esExplorer").val()== "ie") { //Estamos con Explorer
		alto = alto + 16;	
	}
	
	$("#p13_caprabo_SustituyeARefFieldset").height((alto/2) - 1);
	//Para centrar los textos en el fieldset FFPP ACTIVA
	if ($("#p13_caprabo_SustituyeARefFieldset").is(":visible")) {
		var alturaDivSustituyeARef = (alto/2) - 24;
		$(".p13_caprabo_divSustituyeARef").height( alturaDivSustituyeARef );
		var alturaSustituyeARef = $(".p13_caprabo_parrafoSustituyeARef").height();
		if ($("#p13_caprabo_esExplorer").val()== "ie") { //Estamos con Explorer
			if (!$("#p13_caprabo_ffppActivaFieldset").is(":visible") && !$("#p13_caprabo_unitariaFieldset").is(":visible")) {
				$("#p13_caprabo_SustituyeARefFieldset").css( "margin-top", 17);
			}else{
				$("#p13_caprabo_SustituyeARefFieldset").css( "margin-top", 4);
			}
			$(".p13_caprabo_divSustituyeARef").css( "margin-top", alturaDivSustituyeARef/2);
		}else{
			$(".p13_caprabo_parrafoSustituyeARef").css( "margin-top", ((alturaDivSustituyeARef/2)-10));
		}
		$("#p13_caprabo_divSustituyeARefCorreccion").show();
	}
}

function styleSustituidaPorRef(alto){
	//Para dar altura al fieldset SustituyeARef
	
	if ($("#p13_caprabo_esExplorer").val()== "ie") { //Estamos con Explorer
		alto = alto + 16;	
	}
	
	$("#p13_caprabo_SustituidaPorRefFieldset").height((alto/2) - 1);
	//Para centrar los textos en el fieldset FFPP ACTIVA
	if ($("#p13_caprabo_SustituidaPorRefFieldset").is(":visible")) {
		var alturaDivSustituidaPorRef = (alto/2) - 24;
		$(".p13_caprabo_divSustituidaPorRef").height( alturaDivSustituidaPorRef );
		var alturaSustituidaPorRef = $(".p13_caprabo_parrafoSustituidaPorRef").height();
		if ($("#p13_caprabo_esExplorer").val()== "ie") { //Estamos con Explorer
			if (!$("#p13_caprabo_ffppActivaFieldset").is(":visible") && !$("#p13_caprabo_unitariaFieldset").is(":visible")) {
				$("#p13_caprabo_SustituidaPorRefFieldset").css( "margin-top", 17);
			}else{
				$("#p13_caprabo_SustituidaPorRefFieldset").css( "margin-top", 4);
			}
			$(".p13_caprabo_divSustituidaPorRef").css( "margin-top", alturaDivSustituidaPorRef/2);
		}else{
			$(".p13_caprabo_parrafoSustituidaPorRef").css( "margin-top", ((alturaDivSustituidaPorRef/2)-10));
		}
		$("#p13_caprabo_divSustituidaPorRefCorreccion").show();
	}
}

function styleSustituyeSustituta(alto){

	if ($("#p13_caprabo_SustituyeARefFieldset").is(":visible")) {
		$("#p13_caprabo_SustituidaPorRef").css( "height", "0px" );
		
	} else {
		if ($("#p13_caprabo_SustituidaPorRefFieldset").is(":visible")) {
			$("#p13_caprabo_SustituyeARef").css( "height", "0px" );
		} else {
			$("#p13_caprabo_SustituidaPorRef").css( "height", "0px" );
			$("#p13_caprabo_SustituyeARef").css( "height", "0px" );
		}
	}
	
	if (!$("#p13_caprabo_ffppActivaFieldset").is(":visible") && !$("#p13_caprabo_unitariaFieldset").is(":visible")) {
		if ($("#p13_caprabo_SustituyeARefFieldset").is(":visible")) {
			$("#p13_caprabo_SustituyeARef").css( "margin-top", (alto - ((alto/2) - 1) + 10)+"px" );
		}else{
			$("#p13_caprabo_SustituidaPorRef").css( "margin-top", (alto - ((alto/2) - 1) + 10)+"px" );
		}
	}
	
		
	if ($("#p13_caprabo_SustituyeARefFieldset").is(":visible")) {
		styleSustituyeARef(alto);
	}
	
	if ($("#p13_caprabo_SustituidaPorRefFieldset").is(":visible")) {
		styleSustituidaPorRef(alto);
	}
	
}




