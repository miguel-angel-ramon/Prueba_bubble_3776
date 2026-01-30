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

	events_p13_btn_reset();
	events_p13_btn_buscar();
	events_p13_btn_refActiva();
	event_ffpp_Activa();
	event_Sustituye_A_Ref();
	event_Sustituida_Por_Ref();
	$(document).on('CargadoCentro', function(e) { 
		loadP13(locale);
		initializeScreen();
		$("#p13_fld_referencia").focus();
	});
	
	event_enlace_unitaria();
	
	/* Existen eventos que se disparan al interactuar con el area de resultados. Estos eventos solo es posible dispararlo cuando existe una referencia cargada,
	 * pero alguno de ellos, realiza una nueva consulta al ser disparado con los datos existentes en el input de consulta. Este comportamiento da lugar a que
	 * con una consulta cargada, si se introduce una nueva referencia en el input, provoque errores. 
	 * Por ello, cada vez que hagamos foco en el input, reseteo el area de resultados. 
	*/
	$("#p13_fld_referencia").focusin(function(e) {
		//resetResultados();
		$("#p13_fld_referencia").select();
	});
	
  });

function initializeScreen(){
	
	//Si es un centro Caprabo sólo se permiten valores numéricos en la búsqueda
	if (esCentroCaprabo){
		$("#p13_fld_referencia").filter_input({regex:'[0-9]'});
	}

	$("#p13_fld_descripcionRef").attr("disabled", "disabled");
	
	
	$("#p13_pestanas").tabs({ 
	    select:
	        function (event, ui) {
	    		var nombrePestanaActivada = ui.panel.id;
	    		var valorPestanaCargada = $("#" + nombrePestanaActivada + "Cargada").val();
	    		
	    		//Sólo se ejecuta la carga de datos si no se ha cargado antes
	    		if (valorPestanaCargada == null || !(valorPestanaCargada == "S")){
		    		if (nombrePestanaActivada == "p13_pestanaDatosMaestro"){
		    			//Carga de la pestaña de datos maestro
		    			reloadDatosMaestro();
		    		}else if (nombrePestanaActivada == "p13_pestanaImagenComercial") {
		    			//Carga de la pestaña de imagen comercial
						reloadImagenComercial();
					}else{
						//Carga de la pestaña de movimientos
						reloadMovimientos(); //Implementado en p16ReferenciasCentroM.js 
					}
	    		}
	    		
	    		//Se actualiza el control de carga de datos en pestaña
	    		$(nombrePestanaActivada + "Cargada").val("S");
	    		
	            return true;
	        }
	});
}



function events_p13_btn_reset(){
	$("#p13_btn_reset").click(function () {
		$("#p13_fld_referencia").val("");
		$("#p13_fld_referenciaEroski").val("");
		$("#p13_fld_referencia").focus();
		resetResultados();
	});
}

function events_p13_btn_buscar(){
	$("#p13_btn_buscar").click(function () {
		p13Page = true;
		finder();
	});	
}

function event_ffpp_Activa(){

	$( "#p13_ffppActivaFieldsetEnlace" )
    .click(function() {
    	$("#p13_fld_referencia").val(codArtRelacionado);
    	finder();
    });
}

function event_enlace_unitaria(){

	$( "#p13_unitariaFieldsetEnlace" )
    .click(function() {
    	$("#p13_fld_referencia").val(codArtRelacionado);
    	finder();
    });
}

function event_Sustituye_A_Ref(){

	$( "#p13_SustituyeARefFieldsetEnlace" )
    .click(function() {
    	$("#p13_fld_referencia").val(codArtSustitutaDe);
    	finder();
    });
}

function event_Sustituida_Por_Ref(){

	$( "#p13_SustituidaPorRefFieldsetEnlace" )
    .click(function() {
    	$("#p13_fld_referencia").val(codArtSustituidaPor);
    	finder();
    });
}

/**
 * P-49364
 * Modificacion para busqueda de referencias por denominacion
 * @author BICUGUAL
 */
function finder(arg){
	findReferenciaP13();
}

/**     
 * P-49364
 * Busqueda de referencia pasando por validador. 
 * Este metodo se utiliza tanto para la busqueda desde el boton como para la busqueda a traves del return
 * @author BICUGUAL		
 */		
function findReferenciaP13(){			

	var messageVal=null;
	resetResultados();
	
	//Limpiar elementos de correccion
	$("#p13_divStockActualOkCorreccion").hide();
	$("#p13_divStockActualErroneoCorreccion").hide();
	$("#p13_divActivoCorreccion").hide();
	$("#p13_divActivoCorreccionVegalsa").hide();
	$("#p13_divNoActivoCorreccion").hide();
	$("#p13_divNoActivoCorreccionVegalsa").hide();
	$("#p13_divPedidoAdicionalCorreccion").hide();
	//$("#p13_divNoActivoCorreccion").show();
	//$("#p13_divPedidoAdicionalCorreccion").show();
	$("#p13_divFfppActivaCorreccion").hide();
	$("#p13_divUnitariaCorreccion").hide();
	$("#p13_divSustituyeARefCorreccion").hide();
	$("#p13_divSustituidaPorRefCorreccion").hide();
	
	var txtBusqueda= $("#p13_fld_referencia").val();
	var messageVal=findReferenciaValidation(txtBusqueda);
	
	if (messageVal==null){	
		
		//Comprueba si estoy estoy intentando relaizar la consulta desde la pagina P13 o desde informacion Basica de referencia	
		if ($('#p13_fld_refPagina').val()=='p30_btn_masInfoRef') {
			$('#p13_fld_refPagina').val('');
			reloadMaestros();
			
		}else{
			//Si se trata de una busqueda alfanumerica o alfabetica
			if (!isInt(txtBusqueda)){
				reloadDataP34(txtBusqueda, "consultaDatos","", true, false);
			//Si se trata de una busqueda numerica
			}else{
				//Si la REFERENCIA mayor de 8 digitos se busca como EAN, 
				//Si no hay registros como EAN se buscará como MODELO PROVEEDOR
				//En el caso que no eista MODELO PROVEEDOR si no hay registros como DENOMINACION
				alfaNumerico = false;
				//reloadMaestros();
				if(txtBusqueda.length > 8){
					ean= true;
					reloadDataP34(txtBusqueda, "consultaDatos","",false,ean);
				}else{
					ean = false;
					reloadDataP34(txtBusqueda, "consultaDatos","",false,ean);//reloadMaestros();			}
					
				}
				
			}
		}
	}else{
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");		
	}			
}

function findMasInfReferenciaP13(){			
	$("#p30_respuestaWS").hide();
	var messageVal=null;
	resetResultados();
	
	//Limpiar elementos de correccion
	$("#p13_divStockActualOkCorreccion").hide();
	$("#p13_divStockActualErroneoCorreccion").hide();
	$("#p13_divActivoCorreccion").hide();
	$("#p13_divActivoCorreccionVegalsa").hide();
	$("#p13_divNoActivoCorreccion").hide();
	$("#p13_divNoActivoCorreccionVegalsa").hide();
	$("#p13_divPedidoAdicionalCorreccion").hide();
	//$("#p13_divNoActivoCorreccion").show();
	//$("#p13_divPedidoAdicionalCorreccion").show();
	$("#p13_divFfppActivaCorreccion").hide();
	$("#p13_divUnitariaCorreccion").hide();
	$("#p13_divSustituyeARefCorreccion").hide();
	$("#p13_divSustituidaPorRefCorreccion").hide();
	
	var txtBusqueda= $("#p13_fld_referencia").val();
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


/**
 * P-49364
 * Validacion para la busqueda de referencias. 
 * Centro cargado, input de busqueda cargado. Que el input de busqueda tenga mas de 4 caracteres si se ha introducido una secuencia alfanumerica.
 * MISUMI-376
 * Eliminar espacios en blanco por detras y por delante del texto de busqueda para validar que tenga mas de 4 caracteres.
 * @author BICUGUAL		
 * @param txtBusqueda
 * @returns mensaje de error
 */	
function findReferenciaValidation(txtBusqueda){		
	var messageVal=null;		
			
	if ($("#centerId").val()==null || $("#centerId").val()==""){		
		messageVal=centerRequired;		
	}			
	else if (txtBusqueda==null || txtBusqueda==""){		
		messageVal=referenceRequired;   		
	}
	else if (!isInt(txtBusqueda) && txtBusqueda.trim().length<4){	
		messageVal=referenceMinCaracters;		
	}
	return messageVal;		
}


function events_p13_btn_refActiva(){
	$("#p13_btn_refActiva").click(function () {
		createAlert(replaceSpecialCharacters("Has pulsado en pedir. Pendiente de implementar."), "INFO");
	});	
}

function resetResultadosPedidoAdicional(){
	//var strE = $("#p13_pedidosAdicionalesEncargos").text().substr(0,$("#p13_pedidosAdicionalesEncargos").text().indexOf("=")+1)
	//$("#p13_pedidosAdicionalesEncargos").text(strE);
	//var strMA = $("#p13_pedidosAdicionalesMontajesAdicionales").text().substr(0,$("#p13_pedidosAdicionalesMontajesAdicionales").text().indexOf("=")+1)
	//$("#p13_pedidosAdicionalesMontajesAdicionales").text(strMA);
	//var strMAC = $("#p13_pedidosAdicionalesMac").text().substr(0,$("#p13_pedidosAdicionalesMac").text().indexOf("=")+1)
	//$("#p13_pedidosAdicionalesMac").text(strMAC);
}

function resetDatosMaestroFijo (){
	$("#p13_fld_descripcionRef").val("");
	$("#p13_fld_refActiva").val("");
	$("#p13_fld_mapaHoy").val("");
	$("#p13_fld_tieneFoto").val("N");
	$("#p13_pedidoAutomaticoFieldsetDepositoBrita").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetPorCatalogo").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetActivo").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetActivoVegalsa").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetNoActivo").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetNoActivoVegalsa").attr("style", "display:none");
	resetResultadosPedidoAdicional();
}



function resetResultados (){
	$("#p13_descripcionRef").hide();
	$("div#AreaMaestrosFijo").attr("style", "display:none");
	$("div#p13_AreaPestanas").attr("style", "display:none");
	$("#p13_pestanas").tabs("option", "active", 0);
	$("#p13_pestanaDatosMaestroCargada").val("");
	$("#p13_pestanaImagenComercialCargada").val("");
	$("#p13_pestanaMovimientosCargada").val("");
	
	resetDatosMaestroFijo();
	resetResultadosDM();
	resetResultadosIC();
	resetResultadosM(); //Implementado en p16ReferenciasCentroM.js 
}

function loadP13(locale){
	
	this.i18nJSON = './misumi/resources/p13ReferenciasCentro/p13referenciasCentro_' + locale + '.json';
	
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
				if  ($("#p13_fld_referencia").val()!=""){
					finder();
				}

			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
           });
}

function updateResultadosMaestroFijo (data){
	
	esCentroCaprabo = data.esCentroCaprabo;
	esCentroCapraboEspecial = data.esCentroCapraboEspecial;
	esCentroCapraboNuevo = data.esCentroCapraboNuevo;
	resetearStyleReferenciasCentroPedir();
	
	$("#p13_fld_referenciaEroski").val(data.codArtEroski);
	$("#p13_fld_descripcionRef").val(data.diarioArt.descripArt);
	$("#p13_fld_refActiva").val(data.surtidoTienda != null ? (data.surtidoTienda.pedir != null ? data.surtidoTienda.pedir : 'N'):'N');
	$("#p13_fld_mapaHoy").val(data.surtidoTienda != null ? (data.surtidoTienda.mapaHoy != null ? data.surtidoTienda.mapaHoy : 'N'):'N');
	$("#p13_fld_tieneFoto").val(data.tieneFoto);
	
	if (data.flgDepositoBrita != null && data.flgDepositoBrita == 'S'){
		mostrarMensajePedidoDepositoBrita();
	}else if (data.flgPorCatalogo != null && data.flgPorCatalogo == 'S'){
		mostrarMensajePedidoPorCatalogo();
	} else if (data.esTratamientoVegalsa){
		if (data.surtidoTienda != null && data.surtidoTienda.marcaMaestroCentro != null && data.surtidoTienda.marcaMaestroCentro == 'S'){
			mostrarMensajePedidoActivoVegalsa(data);
		} else {
			mostrarMensajePedidoNoActivoVegalsa(data);
		}
	}else{
	
		//Evaluación del dato pedir para y mapaHoy para mostrar el mensaje de pedido automático
		if (data.surtidoTienda != null && data.surtidoTienda.pedir != null && data.surtidoTienda.pedir == 'S'){
			if (data.surtidoTienda.mapaHoy != null){
				$("#p13_fld_mapaHoy").val(data.surtidoTienda.mapaHoy);
				$("#p13_numeroPedidosOtroDia").val(data.surtidoTienda.numeroPedidosOtroDia);
				
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
					
					$(".p13_parrafoPedidoAutomaticoActivo").text(parrafoPedidoAutomaticoActivo.replace("#VALOR#", "desde el " + fechaFormateada));
				} 
				else{
					$(".p13_parrafoPedidoAutomaticoActivo").text(parrafoPedidoAutomaticoActivo.replace("#VALOR#", ""));
				}
				if (data.surtidoTienda.mapaHoy == 'N'){
					if (data.surtidoTienda.numeroPedidosOtroDia == 0){
						//No hay pedido ningún día
						mostrarMensajePedidoNoActivo(data);
					}else{
						//Hoy no hay pedido pero si algún otro día
						mostrarMensajePedidoActivo();
					}
				}else{
					mostrarMensajePedidoActivo();
				}
			}else{
				mostrarMensajePedidoNoActivo(data);
			}
		}else{
			mostrarMensajePedidoNoActivo(data);
		}
	}
	
	//Evaluación del mostrado del mensaje de ffpp activo o unitaria
	if (data.tieneFfppActivo){
		codArtRelacionado = data.codArtRelacionado;
		mostrarMensajeFfppActivo();
	}else if (data.tieneUnitaria){
		codArtRelacionado = data.codArtRelacionado;
		mostrarMensajeUnitaria();
	}else{
		ocultarMensajeUnitaria();
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

	
	if (data.pedidoAdicionalContadores){
		mostrarPedidoAdicional(data.pedidoAdicionalContadores);
	} else {
		ocultarPedidoAdicional();
	}
	

	if (data.valoresStock){
		valStock = data.valoresStock;

		var stockInicial = valStock.stockInicial;
		var entradas = valStock.totalEntradas;
		
		var salidas = valStock.salidas;
		var ventaPromocional = valStock.totalVentaOferta;
		var ventaForzada = valStock.totalVentaAnticipada;
		var modifAjuste = valStock.totalModifAjuste;
		var modifRegul = valStock.totalModifRegul;
		var stockFinal = valStock.stock;
		var centroParametrizado = valStock.centroParametrizado;
		
		$("#p16_fld_stockInicial").val(stockInicial);
		$("#p16_fld_entradas").val(entradas);
		$("#p16_fld_ventaTarifa").val(salidas);
		$("#p16_fld_ventaPromocional").val(ventaPromocional);
		$("#p16_fld_ventaForzada").val(ventaForzada);
		$("#p16_fld_modifAjuste").val(modifAjuste);
		$("#p16_fld_modifRegul").val(modifRegul);
		$("#p16_fld_stockFinal").val(stockFinal);
		$("#p16_fld_centroParametrizado").val(centroParametrizado);

		if (valStock.flgErrorWSVentasTienda == null || valStock.flgErrorWSVentasTienda == 0){
			$("#p13_referenciasCentroStockMensajeError").css("display", "none");

			if (valStock.stockBajo <= valStock.sobreStockInferior
				&& valStock.sobreStockInferior <= valStock.sobreStockSuperior
				&& valStock.stockBajo >= 0
				&& (valStock.stock > 0 || valStock.stockBajo > 0 || valStock.sobreStockInferior > 0 || valStock.sobreStockSuperior > 0)){
				
				$("#p13_stockChart").show();
	
				if(valStock.stock >= valStock.stockBajo && valStock.stock <= valStock.sobreStockInferior){
					$("#p13_stockActualFieldsetOk").attr("style", "display:inline");
					$("#p13_stockActualFieldsetErroneo").attr("style", "display:none");
					$("#p13_stockActualFieldsetNoDefinido").attr("style", "display:none");
					//$(".p13_parrafoStockActualOk").text( $.formatNumber(valStock.stock,{format:"0.#",locale:"es"}));
					
					if (valStock.existeVentaMedia && valStock.diasStock >= 0) {
						$(".p13_parrafoStockActualOk").text($.formatNumber(valStock.stock,{format:"0.#",locale:"es"}) + "->" + $.formatNumber(valStock.diasStock,{format:"0.#",locale:"es"}) + " días");
						
						//$(".p13_parrafoDiasStockActualOk").text( $.formatNumber(valStock.diasStock,{format:"0.#",locale:"es"}) + " días");
						//$(".p13_parrafoDiasStockActualOk").show();
						//$(".p13_divDiasStockActual").show();
						//$(".p13_divImgStockActual").show();
					} else {
						$(".p13_parrafoStockActualOk").text( $.formatNumber(valStock.stock,{format:"0.#",locale:"es"}));
						//$(".p13_parrafoDiasStockActualOk").hide();
						//$(".p13_divDiasStockActual").hide();
						//$(".p13_divImgStockActual").hide();
						//$(".p13_divStockActual").attr("style", "margin-left:45px");
					}
				} else {
					$("#p13_stockActualFieldsetErroneo").attr("style", "display:inline");
					$("#p13_stockActualFieldsetOk").attr("style", "display:none");
					$("#p13_stockActualFieldsetNoDefinido").attr("style", "display:none");
					//$(".p13_parrafoStockActualErroneo").text($.formatNumber(valStock.stock,{format:"0.#",locale:"es"}));
					$(".p13_parrafoStockActualErroneo").text($.formatNumber(valStock.stock,{format:"0.#",locale:"es"}) + " -> " + $.formatNumber(valStock.diasStock,{format:"0.#",locale:"es"}) + " días");
					$(".p13_parrafoStockActualErroneoDias").text('');
					
					if (valStock.existeVentaMedia && valStock.diasStock >= 0) {
						$(".p13_parrafoStockActualErroneo").text($.formatNumber(valStock.stock,{format:"0.#",locale:"es"})  + "->");
						$(".p13_parrafoStockActualErroneoDias").text($.formatNumber(valStock.diasStock,{format:"0.#",locale:"es"}) + " días");
						
						//$(".p13_parrafoDiasStockActualErroneo").text( $.formatNumber(valStock.diasStock,{format:"0.#",locale:"es"}) + " días");
						//$(".p13_parrafoDiasStockActualErroneo").show();
						//$(".p13_divDiasStockActualErroneo").show();
						//$(".p13_divImgStockActual").show();
					} else {
						$(".p13_parrafoStockActualErroneo").text($.formatNumber(valStock.stock,{format:"0.#",locale:"es"}));
						//$(".p13_parrafoDiasStockActualErroneo").hide();
						//$(".p13_divDiasStockActualErroneo").hide();
						//$(".p13_divImgStockActual").hide();
						//$(".p13_divStockActualErroneo").attr("style", "margin-left:45px");
					}

					if(valStock.mostrarMotivosStock != null &&  valStock.mostrarMotivosStock == 'S'){
						$("#p13_divStockActualErroneoInfo").attr("style", "display:inline");
						recalcularEnlaceStockActualError(data);
					}else{
						$("#p13_divStockActualErroneoInfo").attr("style", "display:none");
					}
					
					//Comprobar si bajo o alto
					if(valStock.stock > valStock.sobreStockInferior){
						$("#p13_stockActualLegendErroneo").text(stockAlto);
						$("#p13_stockUp").attr("style", "display:inline-block");
						$("#p13_stockDown").attr("style", "display:none");
						
					} else {
						$("#p13_stockActualLegendErroneo").text(stockBajo);
						$("#p13_stockUp").attr("style", "display:none");
						$("#p13_stockDown").attr("style", "display:inline-block");
					}
				}
			} else {
				$("#p13_stockActualFieldsetOk").attr("style", "display:none");
				$("#p13_stockActualFieldsetNoDefinido").attr("style", "display:inline");
				$("#p13_stockActualFieldsetErroneo").attr("style", "display:none");
				
				if (valStock.existeVentaMedia && valStock.diasStock >= 0) {
					$(".p13_parrafoStockActualNoDefinido").text($.formatNumber(valStock.stock,{format:"0.#",locale:"es"}) + "->" + $.formatNumber(valStock.diasStock,{format:"0.#",locale:"es"}) + " días");
					//$(".p13_parrafoStockActualNoDefinido").text($.formatNumber(valStock.stock,{format:"0.#",locale:"es"}));
					//$(".p13_parrafoStockActualNoDefinidoDias").text($.formatNumber(valStock.diasStock,{format:"0.#",locale:"es"}) + " días");
				} else {
					$(".p13_parrafoStockActualNoDefinido").text( $.formatNumber(valStock.stock,{format:"0.#",locale:"es"}));
				}
				valStock = null;
				$("#p13_stockChart").hide();
			}
		}else{ //Error del WS
			valStock = null;
			$("#p13_referenciasCentroStockMensajeError").css("display", "inline-block");
			$("#p13_stockActualFieldsetOk").attr("style", "display:none");
			$("#p13_stockActualFieldsetErroneo").attr("style", "display:none");
			$("#p13_stockActualFieldsetNoDefinido").attr("style", "display:none");
			$("#p13_stockChart").hide();
		}
	} else {
		valStock = null;
		$("#p13_stockActualFieldsetOk").attr("style", "display:none");
		$("#p13_stockActualFieldsetErroneo").attr("style", "display:none");
		$("#p13_stockActualFieldsetNoDefinido").attr("style", "display:none");
		$("#p13_stockChart").hide();
	}
	
	if ((!esCentroCaprabo) || (esCentroCaprabo && esCentroCapraboNuevo)){
		//Control motivos
		loadMotivosFromWS();
	}
}

function mostrarMensajePedidoDepositoBrita(){
	//Mostrar el mensaje de pedido deposito brita
	$("#p13_pedidoAutomaticoFieldsetDepositoBrita").attr("style", "display:inline");
	$("#p13_pedidoAutomaticoFieldsetPorCatalogo").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetActivo").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetActivoVegalsa").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetNoActivo").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetNoActivoVegalsa").attr("style", "display:none");
}

function mostrarMensajePedidoPorCatalogo(){
	//Mostrar el mensaje de pedido por catálogo
	$("#p13_pedidoAutomaticoFieldsetDepositoBrita").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetPorCatalogo").attr("style", "display:inline");
	$("#p13_pedidoAutomaticoFieldsetActivo").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetActivoVegalsa").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetNoActivo").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetNoActivoVegalsa").attr("style", "display:none");
}

function mostrarMensajePedidoActivo(){
	//Mostrar el mensaje de pedido automático activo
	$("#p13_pedidoAutomaticoFieldsetDepositoBrita").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetPorCatalogo").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetActivo").attr("style", "display:inline");
	$("#p13_pedidoAutomaticoFieldsetActivoVegalsa").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetNoActivo").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetNoActivoVegalsa").attr("style", "display:none");
}

function mostrarMensajePedidoActivoVegalsa(data){
	//Mostrar el mensaje de pedido automático activo
	$("#p13_pedidoAutomaticoFieldsetDepositoBrita").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetPorCatalogo").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetActivo").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetActivoVegalsa").attr("style", "display:inline");
	$("#p13_pedidoAutomaticoFieldsetNoActivo").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetNoActivoVegalsa").attr("style", "display:none");
	
	$("#p13_TextoFecha").html(reemplazarFecha($("#p13_fld_TextoFechaBasico").val(), data.surtidoTienda.fechaMmcStr, data.bloqueo));
}

function reemplazarFecha(texto, fecha, bloqueo) {
	if (fecha != null && fecha != "" && typeof(fecha) != 'undefined') {
		if (bloqueo != null && bloqueo != "" && typeof(bloqueo) != 'undefined') {
			return texto + "  con fecha " + fecha + ' <span style="color:red;font:bold 12px Verdana, Arial, Tahoma, Helvetica, sans-serif;">con estado ' + bloqueo + '</span>';
		} else {
			return texto + "  con fecha " + fecha;
		}
	} else {
		if (bloqueo != null && bloqueo != "" && typeof(bloqueo) != 'undefined') {
			return texto + ' <span style="color:red;font:bold 12px Verdana, Arial, Tahoma, Helvetica, sans-serif;">con estado ' + bloqueo + '</span>';
		} else {
			return texto;
		}
	}
}

function mostrarMensajePedidoNoActivo(data){
	//Mostrar el mensaje de pedido automático no activo
	
	
	if (esCentroCaprabo){
		//En caso de un centro Caprabo
		$("#p13_parrafoPedidoAutomaticoNoActivo").attr("style", "display:none");
		$("#p13_parrafoPedidoAutomaticoNoActivoSinEnlace").attr("style", "display:none");
		//Mostrar el mensaje de pedido automático no activo;
		
		//if (data.motivoCaprabo!=null && data.motivoCaprabo !=null && data.motivoCaprabo !="") {
			$("#p13_parrafoPedidoAutomaticoNoActivo").attr("style", "display:inline");
			
			$( "#p15_pedidoAutomaticoFieldsetNoActivoConEnlace" ).unbind("click");
			//Asignamos evento al link para que habra el popUp con los motivos
			$( "#p15_pedidoAutomaticoFieldsetNoActivoConEnlace" )
			    .bind("click",function() {
			    	$( "#p18_popupPedir" ).dialog( "option", "title", tituloVentanaPopupPedir);
			    	$( "#p18_popupPedir" ).dialog( "open" );
			    	reloadDataP18();
			  });
	
		//} else {
			//$("#p13_parrafoPedidoAutomaticoNoActivoSinEnlace").attr("style", "display:inline");
		//}
	}
	
	//En caso de un centro Eroski
	$("#p13_pedidoAutomaticoFieldsetDepositoBrita").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetPorCatalogo").attr("style", "display:none");
	$("#p15_pedidoAutomaticoFieldsetNoActivoEnlace").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetActivo").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetActivoVegalsa").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetNoActivo").attr("style", "display:inline");
}

function mostrarMensajePedidoNoActivoVegalsa(data){
	//Mostrar el mensaje de pedido automático no activo
	//En caso de un centro Eroski
	$("#p13_pedidoAutomaticoFieldsetDepositoBrita").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetPorCatalogo").attr("style", "display:none");
	$("#p15_pedidoAutomaticoFieldsetNoActivoEnlace").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetActivo").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetActivoVegalsa").attr("style", "display:none");
	$("#p13_pedidoAutomaticoFieldsetNoActivo").attr("style", "display:none");
	
	if (null != data.surtidoTienda){
		// Tratamiento Vegalsa
		$("#p13_pedidoAutomaticoFieldsetNoActivoVegalsa").attr("style", "display:inline");
		$("#p13_parrafoPedidoAutomaticoNoActivoVegalsa").attr("style", "display:inline");
		//Asignamos evento al link para que habra el popUp con los motivos
		$( "#p15_pedidoAutomaticoFieldsetNoActivoEnlaceVegalsa" )
		    .bind("click",function() {
		    	$( "#p18_popupPedir" ).dialog( "option", "title", tituloVentanaPopupPedir);
		    	$( "#p18_popupPedir" ).dialog( "open" );
		    	reloadDataP18();
		  });
		
//		$("#p13_fld_TextoFechaNoActivoBasico").append("<span id='p15_pedidoAutomaticoFieldsetNoActivoEnlaceVegalsa'>MMC=N</span>");
//		$("#p13_parrafoPedidoAutomaticoNoActivoVegalsa").text(reemplazarFecha($("#p13_fld_TextoFechaNoActivoBasico").val(), data.surtidoTienda.fechaMmcStr));
//		$("#p13_parrafoPedidoAutomaticoNoActivoVegalsa").text(reemplazarFecha($("#p13_TextoFechaNo").text(), data.surtidoTienda.fechaMmcStr));
		
		$("#p13_TextoFechaNo").text("con fecha ")
		$("#p13_TextoFechaNo").text(reemplazarFecha($("#p13_TextoFechaNo").text(), data.surtidoTienda.fechaMmcStr));
		
	}
}

function mostrarPedidoAdicional(contadores){
	//Mostrar el mensaje de pedido adicional
	if (contadores.contadorEncargos != 0 || contadores.contadorMontaje != 0 || contadores.contadorMAC != 0){
		$("#p13_pedidosAdicionalesFieldset").attr("style", "display:inline");
	}else{
		$("#p13_pedidosAdicionalesFieldset").attr("style", "display:none");
	}
	recalcularEnlacePedidoAdicional(contadores);

	if (contadores.codError == 0){

		$("#p13_pedidosAdicionalesError").hide();
		if (contadores.contadorEncargos != 0){
			$("#p13_pedidosAdicionalesEncargos").show();
			$("#p13_pedidosAdicionalesEncargosCont").text(contadores.contadorEncargos);
		} else{
			$("#p13_pedidosAdicionalesEncargos").hide();
		}
		if (contadores.contadorMontaje != 0){
			$("#p13_pedidosAdicionalesMontajesAdicionales").show();
			$("#p13_pedidosAdicionalesMontajesAdicionalesCont").text(contadores.contadorMontaje);
		} else {
			$("#p13_pedidosAdicionalesMontajesAdicionales").hide();
		}
		if (contadores.contadorMAC != 0){
			$("#p13_pedidosAdicionalesMac").show();
			$("#p13_pedidosAdicionalesMacCont").text(contadores.contadorMAC);
		} else {
			$("#p13_pedidosAdicionalesMac").hide();
		}
	} else {
		$("#p13_pedidosAdicionalesError").show();
		$("#p13_pedidosAdicionalesEncargos").hide();
		$("#p13_pedidosAdicionalesMontajesAdicionales").hide();
		$("#p13_pedidosAdicionalesMac").hide();
	}
	
	//$("#p13_pedidosAdicionalesMontajesAdicionales").append(contadores.contadorMontaje);
	//$("#p13_pedidosAdicionalesMac").append(contadores.contadorMAC);
}

function ocultarPedidoAdicional(){
	//Mostrar el mensaje de pedido adicional
	$("#p13_pedidosAdicionalesFieldset").attr("style", "display:none");
}

function mostrarMensajeFfppActivo(){
	//Mostrar el mensaje de ffpp activo
	$("#p13_ffppActivaFieldset").attr("style", "display:inline");
	$("#p13_unitariaFieldset").attr("style", "display:none");
}

function mostrarMensajeUnitaria(){
	//Mostrar el mensaje de unitaria
	$("#p13_ffppActivaFieldset").attr("style", "display:none");
	$("#p13_unitariaFieldset").attr("style", "display:inline");
}

function ocultarMensajeUnitaria(){
	//Mostrar el mensaje de unitaria
	$("#p13_ffppActivaFieldset").attr("style", "display:none");
	$("#p13_unitariaFieldset").attr("style", "display:none");
}

function mostrarMensajeSustituyeARef(){
	//Mostrar el mensaje de Suatituye a la ref
	$("#p13_SustituyeARefFieldset").attr("style", "display:inline");
	$("#p13_SustituidaPorRefFieldset").attr("style", "display:none");
}

function mostrarMensajeSustituidaPorRef(){
	//Mostrar el mensaje de Suatituye a la ref
	$("#p13_SustituidaPorRefFieldset").attr("style", "display:inline");
	$("#p13_SustituyeARefFieldset").attr("style", "display:none");
	
}

function ocultarMensajeSustituyeYSustituida() {
	$("#p13_SustituidaPorRefFieldset").attr("style", "display:none");
	$("#p13_SustituyeARefFieldset").attr("style", "display:none");
}

function reloadMaestros() {
	var vReferenciasCentro=new ReferenciasCentro($("#p13_fld_referencia").val(), $("#centerId").val(), 'InforDatos');
	var objJson = $.toJSON(vReferenciasCentro);	
	 $.ajax({
		type : 'POST',
		url : './referenciasCentro/loadDatosMaestrosFijo.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			if (validateReloading(data)){
				
				$("#p13_descripcionRef").show();
				//Actualización de los datos de "Datos maestro fijo"
				updateResultadosMaestroFijo(data);

				//Carga por defecto de la pestaña de movimientos
				reloadMovimientos();
			
				$("div#AreaMaestrosFijo").attr("style", "display:block");
				$("div#p13_AreaPestanas").attr("style", "display:block");
				
				//Control alturas
				var altura = calcularAlturaMaximaReferenciasCentroPedir();

				//Para stylizar ReferenciasCentroPedir
				styleStockActualOk(altura);
				styleStockActualError(altura);
				styleStockActualNoDefinido(altura);
				stylePedidoAutomaticoDepositoBrita(altura);
				stylePedidoAutomaticoPorCatalogo(altura);
				stylePedidoAutomaticoActivo(altura);
				stylePedidoAutomaticoActivoVegalsa(altura);
				stylePedidoAutomaticoNoActivo(altura);
				stylePedidoAutomaticoNoActivoVegalsa(altura);
				stylePedidosAdicionales(altura);
				styleFfppActiva(altura);
				styleUnitaria(altura);
				styleSustituyeSustituta(altura);
				if (null != valStock){
					stockGauge(valStock);
				}
				
				//Control para comprobar si la pestaña de Imagen Comercial debe pintarse en rojo o verde.
				if(data.flgColorImplantacion == "ROJO") {
					$("#p13_StylePestanaImagenComercialSpan").removeClass().addClass( "p13_StylePestanaImagenComercialSpanRojo" );
				} else {
					if(data.flgColorImplantacion == "VERDE") {
						$("#p13_StylePestanaImagenComercialSpan").removeClass().addClass( "p13_StylePestanaImagenComercialSpanVerde" );
					} else {
						$("#p13_StylePestanaImagenComercialSpan").removeClass().addClass( "p13_StylePestanaImagenComercialSpan" );
					}
					
				}
				
				$("#p15_lbl_desgloseEnUnidadesTitle").text(data.msgUNIDADESCAJAS);
				
			}else{
				$("#p13_descripcionRef").hide();
				var messageVal=null;
				messageVal = emptyRecords;
				if (messageVal!=null){
					createAlert(replaceSpecialCharacters(messageVal), "ERROR");
				}
			}	
			$("#infoRef").show();
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
}

function validateReloading(data){
	if (data.length == 0 || 
			data.diarioArt == null  || 
			data.diarioArt.length ==0){

		return false;
	}else{
		return true;
	}
}
function loadMotivosFromWS(){
	//loadWS
	var vReferenciasCentro=new ReferenciasCentro($("#p13_fld_referenciaEroski").val(), $("#centerId").val(), 'InforDatos');
	if ($("#p13_fld_refActiva").val()!="S"){
		var objJson = $.toJSON(vReferenciasCentro);	
		$.ajax({
			type : 'POST',
			url : './referenciasCentro/loadMotivosFromWS.do',
			data : objJson,
			contentType : "application/json; charset=utf-8",
			cahe:false,
		success : function(data){
			visibleLink();
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
		});		
	}else{
		visibleLink();
	}
	
}

function visibleLink(){
	if ($("#p13_fld_refActiva").val()!= null && $("#p13_fld_refActiva").val()=='S' ){
		if ($("#p13_fld_mapaHoy").val() != null && $("#p13_fld_mapaHoy").val() =='N'){
				if ($("#p13_numeroPedidosOtroDia").val() == 0){
					//No hay pedido ningún día
					$("#p15_pedidoAutomaticoFieldsetNoActivoEnlace").attr("style", "display:inline");
				}
			
		}else{
			$("#p15_pedidoAutomaticoFieldsetNoActivoEnlace").attr("style", "display:inline");
		}
	}else{
		$("#p15_pedidoAutomaticoFieldsetNoActivoEnlace").attr("style", "display:inline");
	}
}



function recalcularEnlacePedidoAdicional(contadores){

	if (contadores.codError == 0){
		var pestanaOrigen = 'E';
		if(contadores.contadorEmpuje > 0){
			pestanaOrigen = 'EM';
		}else if(contadores.contadorEncargos >0){
			pestanaOrigen = 'E';
		}else if(contadores.contadorEncargosCliente > 0){
			pestanaOrigen = 'EC';
		}else if(contadores.contadorMAC > 0){
			pestanaOrigen = 'EM';
		}else if(contadores.contadorMontaje > 0){
			pestanaOrigen = 'EM';
		}else if(contadores.contadorMontajeOferta > 0){
			pestanaOrigen = 'MO';
		}else if(contadores.contadorValidarCantExtra > 0){
			pestanaOrigen = 'VC';
		}
		var url = './pedidoAdicional.do?flagCancelarNuevo=S&referencia='+$("#p13_fld_referencia").val()+'&pestanaOrigen='+pestanaOrigen+'&flgReferenciaCentro=S&pantallaOrigen=consultaDatosRef';
		if (contadores.contadorMAC > 0 && contadores.contadorEncargos == 0 && contadores.contadorMontaje == 0){
			url += '&mac=S';
		}
		$( "#p13_pedidosAdicionalesFieldsetEnlace" ).unbind("click");
		$( "#p13_pedidosAdicionalesFieldsetEnlace" ).click(function() {
	    	window.location=url;
	    });
	}
}

function recalcularEnlaceStockActualError(data){

	var valoresStock = data.valoresStock;
	
	$("#p13_stockActualErroneoInfoEnlace" ).unbind("click");
	$("#p13_stockActualErroneoInfoEnlace").click(function () {
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
	
	if ($("#p13_pedidosAdicionalesFieldset").is(":visible")) {
		if (alturaMax < $("#p13_pedidosAdicionalesFieldset").height()) {
			alturaMax = $("#p13_pedidosAdicionalesFieldset").height();
		}
	}
	if ($("#p13_stockActualFieldsetOk").is(":visible")) {
		if (alturaMax < $("#p13_stockActualFieldsetOk").height()) {
			alturaMax = $("#p13_stockActualFieldsetOk").height();
		}
	} 
	if ($("#p13_stockActualFieldsetErroneo").is(":visible")) {
		if (alturaMax < ($("#p13_stockActualFieldsetErroneo").height())) {
			alturaMax = $("#p13_stockActualFieldsetErroneo").height();
		}
	}
	if ($("#p13_stockActualFieldsetNoDefinido").is(":visible")) {
		if (alturaMax < $("#p13_stockActualFieldsetNoDefinido").height()) {
			alturaMax = $("#p13_stockActualFieldsetNoDefinido").height();
		}
	} 
	if ($("#p13_pedidoAutomaticoFieldsetDepositoBrita").is(":visible")) {
		if (alturaMax < $("#p13_pedidoAutomaticoFieldsetDepositoBrita").height()) {
			alturaMax = $("#p13_pedidoAutomaticoFieldsetDepositoBrita").height();
			
		}
	} 
	if ($("#p13_pedidoAutomaticoFieldsetPorCatalogo").is(":visible")) {
		if (alturaMax < $("#p13_pedidoAutomaticoFieldsetPorCatalogo").height()) {
			alturaMax = $("#p13_pedidoAutomaticoFieldsetPorCatalogo").height();
			
		}
	} 
	if ($("#p13_pedidoAutomaticoFieldsetActivo").is(":visible")) {
		if (alturaMax < $("#p13_pedidoAutomaticoFieldsetActivo").height()) {
			alturaMax = $("#p13_pedidoAutomaticoFieldsetActivo").height();
			
		}
	} 
	if ($("#p13_pedidoAutomaticoFieldsetActivoVegalsa").is(":visible")) {
		if (alturaMax < $("#p13_pedidoAutomaticoFieldsetActivoVegalsa").height()) {
			alturaMax = $("#p13_pedidoAutomaticoFieldsetActivoVegalsa").height();
			
		}
	} 
	if ($("#p13_pedidoAutomaticoFieldsetNoActivo").is(":visible")) {
		if (alturaMax < ($("#p13_pedidoAutomaticoFieldsetNoActivo").height())) {
			alturaMax = $("#p13_pedidoAutomaticoFieldsetNoActivo").height();
			
		}
	}
	if ($("#p13_pedidoAutomaticoFieldsetNoActivoVegalsa").is(":visible")) {
		if (alturaMax < ($("#p13_pedidoAutomaticoFieldsetNoActivoVegalsa").height())) {
			alturaMax = $("#p13_pedidoAutomaticoFieldsetNoActivoVegalsa").height();
			
		}
	}

	return alturaMax;
}

function resetearStyleReferenciasCentroPedir(){
	$(".p13_divStockActualOk").removeAttr( "style" );
	$(".p13_parrafoStockActualOk").removeAttr( "style" );
	$(".p13_divStockActualErroneo").removeAttr( "style" );
	$(".p13_parrafoStockActualErroneo").removeAttr( "style" );
	$(".p13_divStockActualNoDefinido").removeAttr( "style" );
	$(".p13_parrafoStockActualNoDefinido").removeAttr( "style" );
	$("#p13_stockActualErroneoInfo").removeClass( "p13_divStockActualErrorInfo" );
	$(".p13_divPedidoAutomaticoActivo").removeAttr( "style" );
	$(".p13_parrafoPedidoAutomaticoActivo").removeAttr( "style" );
	$(".p13_divPedidoAutomaticoNoActivo").removeAttr( "style" );
	$(".p13_parrafoPedidoAutomaticoNoActivo").removeAttr( "style" );
	$(".p13_divPedidosAdicionales").removeAttr( "style" );
	$(".p13_pedidosAdicionales").removeAttr( "style" );
	$(".p13_divFfppActiva").removeAttr( "style" );
	$(".p13_parrafoFfppActiva").removeAttr( "style" );
	$(".p13_divUnitaria").removeAttr( "style" );
	$(".p13_parrafoUnitaria").removeAttr( "style" );
	
}

function styleStockActualError(alto){
	//Para dar altura al fieldset STOCK ACTUAL ERROR
	$("#p13_stockActualFieldsetErroneo").height(alto);
	//Para centrar los textos en el fieldset STOCK ACTUAL ERROR
	if ($("#p13_stockActualFieldsetErroneo").is(":visible")) {
		var alturaDivStockActualError = alto - 24;
		$(".p13_divPedidoAutomaticoNoActivo").height( alturaDivStockActualError );
		$(".p13_divStockActualErroneo").css( "top", "20px" );
		$(".p13_divStockActualErroneo").css( "text-align", "center");
		var alturaStockActualError = $(".p13_parrafoStockActualErroneo").height();
		
		
		$("#p13_stockActualErroneoInfo").addClass( "p13_divStockActualErrorInfo" );
		$("#p13_divStockActualErroneoCorreccion").show();
		if(valStock.mostrarMotivosStock != null &&  valStock.mostrarMotivosStock == 'S'){
			$(".p13_divStockActualErroneo").css( "margin-top", ((alturaDivStockActualError-alturaStockActualError)/2)-15);
		}else{
			$(".p13_divStockActualErroneo").css( "margin-top", ((alturaDivStockActualError-alturaStockActualError)/2)-10);
		}
	}
}

function styleStockActualOk(alto){
	//Para dar altura al fieldset STOCK ACTUAL OK
	$("#p13_stockActualFieldsetOk").height(alto);
	//Para centrar los textos en el fieldset STOCK ACTUAL OK
	if ($("#p13_stockActualFieldsetOk").is(":visible")) {
		var alturaDivStockActualOk = alto - 24;
		$(".p13_divStockActualOk").height( alturaDivStockActualOk );
		$(".p13_divStockActualOk").css( "top", "20px" );
		$(".p13_divStockActualOk").css( "text-align", "center");
		var alturaStockActualOk = $(".p13_parrafoStockActualOk").height();
		$(".p13_parrafoStockActualOk").css( "margin-top",  ((alturaDivStockActualOk-alturaStockActualOk)/2)-10);
		$("#p13_divStockActualOkCorreccion").show();
	}
}

function styleStockActualNoDefinido(alto){
	//Para dar altura al fieldset STOCK ACTUAL NO DEFINIDO
	$("#p13_stockActualFieldsetNoDefinido").height(alto);
	//Para centrar los textos en el fieldset STOCK ACTUAL NO DEFINIDO
	if ($("#p13_stockActualFieldsetNoDefinido").is(":visible")) {
		var alturaDivStockActualNoDefinido = alto - 24;
		$(".p13_divStockActualNoDefinido").height( alturaDivStockActualNoDefinido );
		$(".p13_divStockActualNoDefinido").css( "top", "20px" );
		$(".p13_divStockActualNoDefinido").css( "text-align", "center");
		var alturaStockActualNoDefinido = $(".p13_parrafoStockActualNoDefinido").height();
		$(".p13_parrafoStockActualNoDefinido").css( "margin-top",  ((alturaDivStockActualNoDefinido-alturaStockActualNoDefinido)/2)-10);
		$("#p13_divStockActualNoDefinidoCorreccion").show();
	}
}

function stylePedidoAutomaticoDepositoBrita(alto){
	//Para dar altura al fieldset DEPOSITO BRITA
	$("#p13_pedidoAutomaticoFieldsetDepositoBrita").height(alto);
	//Para centrar los textos en el fieldset DEPOSITO BRITA
	if ($("#p13_pedidoAutomaticoFieldsetDepositoBrita").is(":visible")) {
		//var alturaDivPedidosAutomaticoDepositoBrita = alto - 10;
		var alturaDivPedidosAutomaticoDepositoBrita = alto - 24;
		$(".p13_divPedidoAutomaticoDepositoBrita").height( alturaDivPedidosAutomaticoDepositoBrita );
		//$(".p13_divPedidoAutomaticoDepositoBrita").css( "position", "absolute" );
		$(".p13_divPedidoAutomaticoDepositoBrita").css( "float", "left" );
		$(".p13_divPedidoAutomaticoDepositoBrita").css( "top", "20px" );
		var alturaPedidoAutomaticoDepositoBrita = $(".p13_parrafoPedidoAutomaticoDepositoBrita").height();
		$(".p13_parrafoPedidoAutomaticoDepositoBrita").css( "margin-top",  ((alturaDivPedidosAutomaticoDepositoBrita-alturaPedidoAutomaticoDepositoBrita)/2)-10);
		//$(".p13_parrafoPedidoAutomaticoDepositoBrita").css( "position", "absolute" );
		$(".p13_parrafoPedidoAutomaticoDepositoBrita").css( "float", "left" );
		//$(".p13_parrafoPedidoAutomaticoDepositoBrita").css( "top", "50%" );
		$("#p13_divDepositoBritaCorreccion").show();
		
	}
}

function stylePedidoAutomaticoPorCatalogo(alto){
	//Para dar altura al fieldset POR CATALOGO
	$("#p13_pedidoAutomaticoFieldsetPorCatalogo").height(alto);
	//Para centrar los textos en el fieldset POR CATALOGO
	if ($("#p13_pedidoAutomaticoFieldsetPorCatalogo").is(":visible")) {
		//var alturaDivPedidosAutomaticoPorCatalogo = alto - 10;
		var alturaDivPedidosAutomaticoPorCatalogo = alto - 24;
		$(".p13_divPedidoAutomaticoPorCatalogo").height( alturaDivPedidosAutomaticoPorCatalogo );
		//$(".p13_divPedidoAutomaticoPorCatalogo").css( "position", "absolute" );
		$(".p13_divPedidoAutomaticoPorCatalogo").css( "float", "left" );
		$(".p13_divPedidoAutomaticoPorCatalogo").css( "top", "20px" );
		var alturaPedidoAutomaticoPorCatalogo = $(".p13_parrafoPedidoAutomaticoPorCatalogo").height();
		$(".p13_parrafoPedidoAutomaticoPorCatalogo").css( "margin-top",  ((alturaDivPedidosAutomaticoPorCatalogo-alturaPedidoAutomaticoPorCatalogo)/2)-10);
		//$(".p13_parrafoPedidoAutomaticoPorCatalogo").css( "position", "absolute" );
		$(".p13_parrafoPedidoAutomaticoPorCatalogo").css( "float", "left" );
		//$(".p13_parrafoPedidoAutomaticoPorCatalogo").css( "top", "50%" );
		$("#p13_divPorCatalogoCorreccion").show();
		
	}
}

function stylePedidoAutomaticoActivo(alto){
	//Para dar altura al fieldset APROVISIONAMIENTO CENTRALIZADO
	$("#p13_pedidoAutomaticoFieldsetActivo").height(alto);
	//Para centrar los textos en el fieldset APROVISIONAMIENTO CENTRALIZADO
	if ($("#p13_pedidoAutomaticoFieldsetActivo").is(":visible")) {
		//var alturaDivPedidosAutomaticoActivo = alto - 10;
		var alturaDivPedidosAutomaticoActivo = alto - 24;
		$(".p13_divPedidoAutomaticoActivo").height( alturaDivPedidosAutomaticoActivo );
		//$(".p13_divPedidoAutomaticoActivo").css( "position", "absolute" );
		$(".p13_divPedidoAutomaticoActivo").css( "float", "left" );
		$(".p13_divPedidoAutomaticoActivo").css( "top", "20px" );
		var alturaPedidoAutomaticoActivo = $(".p13_parrafoPedidoAutomaticoActivo").height();
		$(".p13_parrafoPedidoAutomaticoActivo").css( "margin-top",  ((alturaDivPedidosAutomaticoActivo-alturaPedidoAutomaticoActivo)/2)-10);
		//$(".p13_parrafoPedidoAutomaticoActivo").css( "position", "absolute" );
		$(".p13_parrafoPedidoAutomaticoActivo").css( "float", "left" );
		//$(".p13_parrafoPedidoAutomaticoActivo").css( "top", "50%" );
		$("#p13_divActivoCorreccion").show();
		
	}
}

function stylePedidoAutomaticoActivoVegalsa(alto){
	//Para dar altura al fieldset APROVISIONAMIENTO CENTRALIZADO
	$("#p13_pedidoAutomaticoFieldsetActivoVegalsa").height(alto);
	//Para centrar los textos en el fieldset APROVISIONAMIENTO CENTRALIZADO
	if ($("#p13_pedidoAutomaticoFieldsetActivoVegalsa").is(":visible")) {
		//var alturaDivPedidosAutomaticoActivo = alto - 10;
		var alturaDivPedidosAutomaticoActivo = alto - 24;
		$(".p13_divPedidoAutomaticoActivo").height( alturaDivPedidosAutomaticoActivo );
		//$(".p13_divPedidoAutomaticoActivo").css( "position", "absolute" );
		$(".p13_divPedidoAutomaticoActivo").css( "float", "left" );
		$(".p13_divPedidoAutomaticoActivo").css( "top", "20px" );
		var alturaPedidoAutomaticoActivo = $(".p13_parrafoPedidoAutomaticoActivo").height();
		$(".p13_parrafoPedidoAutomaticoActivo").css( "margin-top",  ((alturaDivPedidosAutomaticoActivo-alturaPedidoAutomaticoActivo)/2)-10);
		//$(".p13_parrafoPedidoAutomaticoActivo").css( "position", "absolute" );
		$(".p13_parrafoPedidoAutomaticoActivo").css( "float", "left" );
		//$(".p13_parrafoPedidoAutomaticoActivo").css( "top", "50%" );
		$("#p13_divActivoCorreccionVegalsa").show();
		
	}
}

function stylePedidoAutomaticoNoActivo(alto){
	//Para dar altura al fieldset APROVISIONAMIENTO CENTRALIZADO
	$("#p13_pedidoAutomaticoFieldsetNoActivo").height(alto);
	//Para centrar los textos en el fieldset APROVISIONAMIENTO CENTRALIZADO
	if ($("#p13_pedidoAutomaticoFieldsetNoActivo").is(":visible")) {
		var alturaDivPedidosAutomaticoNoActivo = alto - 24;
		$(".p13_divPedidoAutomaticoNoActivo").height( alturaDivPedidosAutomaticoNoActivo );
		//$(".p13_divPedidoAutomaticoNoActivo").css( "position", "absolute" );
		$(".p13_divPedidoAutomaticoNoActivo").css( "float", "left" );
		$(".p13_divPedidoAutomaticoNoActivo").css( "top", "20px" );
		var alturaPedidoAutomaticoNoActivo = $(".p13_parrafoPedidoAutomaticoNoActivo").height();
		$(".p13_parrafoPedidoAutomaticoNoActivo").css( "margin-top", ((alturaDivPedidosAutomaticoNoActivo-alturaPedidoAutomaticoNoActivo)/2)-10);
		//$(".p13_parrafoPedidoAutomaticoNoActivo").css( "position", "absolute" );
		$(".p13_parrafoPedidoAutomaticoNoActivo").css( "float", "left" );
		//$(".p13_parrafoPedidoAutomaticoNoActivo").css( "top", "50%" );
		$("#p13_divNoActivoCorreccion").show();
	}
}

function stylePedidoAutomaticoNoActivoVegalsa(alto){
	//Para dar altura al fieldset APROVISIONAMIENTO CENTRALIZADO
	$("#p13_pedidoAutomaticoFieldsetNoActivoVegalsa").height(alto);
	//Para centrar los textos en el fieldset APROVISIONAMIENTO CENTRALIZADO
	if ($("#p13_pedidoAutomaticoFieldsetNoActivoVegalsa").is(":visible")) {
		var alturaDivPedidosAutomaticoNoActivo = alto - 24;
		$(".p13_divPedidoAutomaticoNoActivo").height( alturaDivPedidosAutomaticoNoActivo );
		//$(".p13_divPedidoAutomaticoNoActivo").css( "position", "absolute" );
		$(".p13_divPedidoAutomaticoNoActivo").css( "float", "left" );
		$(".p13_divPedidoAutomaticoNoActivo").css( "top", "20px" );
		var alturaPedidoAutomaticoNoActivo = $(".p13_parrafoPedidoAutomaticoNoActivo").height();
		$(".p13_parrafoPedidoAutomaticoNoActivo").css( "margin-top", ((alturaDivPedidosAutomaticoNoActivo-alturaPedidoAutomaticoNoActivo)/2)-10);
		//$(".p13_parrafoPedidoAutomaticoNoActivo").css( "position", "absolute" );
		//$(".p13_parrafoPedidoAutomaticoNoActivo").css( "float", "left" );
		//$(".p13_parrafoPedidoAutomaticoNoActivo").css( "top", "50%" );
		$("#p13_divNoActivoCorreccionVegalsa").show();
	}
}

function stylePedidosAdicionales(alto){
	//Para dar altura al fieldset PEDIDOS ADICIONALES

	$("#p13_pedidosAdicionalesFieldset").height(alto);
	//Para centrar los textos en el fieldset PEDIDOS ADICIONALES
	if ($("#p13_pedidosAdicionalesFieldset").is(":visible")) {
		var alturaDivPedidosAdicionales = alto - 24;
		$(".p13_divPedidosAdicionales").height( alturaDivPedidosAdicionales );
		//$(".p13_divPedidosAdicionales").css( "position", "absolute" );
		$(".p13_divPedidosAdicionales").css( "float", "left" );
		$(".p13_divPedidosAdicionales").css( "top", "20px" );
		var alturaPedidosAdicionales = $(".p13_pedidosAdicionales").height();
		$(".p13_pedidosAdicionales").css( "margin-top", ((alturaDivPedidosAdicionales-alturaPedidosAdicionales)/2)-10);
		//$(".p13_pedidosAdicionales").css( "position", "absolute" );
		$(".p13_pedidosAdicionales").css( "float", "left" );
		//$(".p13_pedidosAdicionales").css( "top", "50%" );
		$("#p13_pedidoAutomaticoLegendActivoCorreccion").show();
		$("#p13_divPedidoAdicionalCorreccion").show();
	}
}

function styleFfppActiva(alto){
	//Para dar altura al fieldset FFPP ACTIVA
	
	if ($("#p13_esExplorer").val()== "ie") { //Estamos con Explorer
		alto = alto + 16;	
	}
	
	$("#p13_ffppActivaFieldset").height((alto/2) - 1);
	//Para centrar los textos en el fieldset FFPP ACTIVA
	if ($("#p13_ffppActivaFieldset").is(":visible")) {
		var alturaDivFfppActiva = (alto/2) - 24;
		$(".p13_divFfppActiva").height( alturaDivFfppActiva );
		var alturaFfppActiva = $(".p13_parrafoFfppActiva").height();
		if ($("#p13_esExplorer").val()== "ie") { //Estamos con Explorer
			$(".p13_divFfppActiva").css( "margin-top", alturaDivFfppActiva/2);
		}else{
			$(".p13_parrafoFfppActiva").css( "margin-top", ((alturaDivFfppActiva/2)-10));
		}
		$("#p13_divFfppActivaCorreccion").show();
	}
}

function styleUnitaria(alto){
	//Para dar altura al fieldset UNITARIA
	
	if ($("#p13_esExplorer").val()== "ie") { //Estamos con Explorer
		alto = alto + 16;	
	}
	
	$("#p13_unitariaFieldset").height((alto/2) - 1);
	//Para centrar los textos en el fieldset UNITARIA
	if ($("#p13_unitariaFieldset").is(":visible")) {
		var alturaDivUnitaria = (alto/2) - 24;
		$(".p13_divUnitaria").height( alturaDivUnitaria );
		var alturaUnitaria = $(".p13_parrafoUnitaria").height();
		if ($("#p13_esExplorer").val()== "ie") { //Estamos con Explorer
			$(".p13_divUnitaria").css( "margin-top", alturaDivUnitaria/2);
		}else{
			$(".p13_parrafoUnitaria").css( "margin-top", ((alturaDivUnitaria/2)-10));
		}
		$("#p13_divUnitariaCorreccion").show();
	}
}


function styleSustituyeARef(alto){
	//Para dar altura al fieldset SustituyeARef

	if ($("#p13_esExplorer").val()== "ie") { //Estamos con Explorer
		alto = alto + 16;	
	}
	
	$("#p13_SustituyeARefFieldset").height((alto/2) - 1);
	//Para centrar los textos en el fieldset FFPP ACTIVA
	if ($("#p13_SustituyeARefFieldset").is(":visible")) {
		var alturaDivSustituyeARef = (alto/2) - 24;
		$(".p13_divSustituyeARef").height( alturaDivSustituyeARef );
		var alturaSustituyeARef = $(".p13_parrafoSustituyeARef").height();
		if ($("#p13_esExplorer").val()== "ie") { //Estamos con Explorer
			if (!$("#p13_ffppActivaFieldset").is(":visible") && !$("#p13_unitariaFieldset").is(":visible")) {
				$("#p13_SustituyeARefFieldset").css( "margin-top", 17);
			}else{
				$("#p13_SustituyeARefFieldset").css( "margin-top", 4);
			}
			$(".p13_divSustituyeARef").css( "margin-top", alturaDivSustituyeARef/2);
		}else{
			$(".p13_parrafoSustituyeARef").css( "margin-top", ((alturaDivSustituyeARef/2)-10));
		}
		$("#p13_divSustituyeARefCorreccion").show();
	}
}

function styleSustituidaPorRef(alto){
	//Para dar altura al fieldset SustituyeARef
	
	if ($("#p13_esExplorer").val()== "ie") { //Estamos con Explorer
		alto = alto + 16;	
	}
	
	$("#p13_SustituidaPorRefFieldset").height((alto/2) - 1);
	//Para centrar los textos en el fieldset FFPP ACTIVA
	if ($("#p13_SustituidaPorRefFieldset").is(":visible")) {
		var alturaDivSustituidaPorRef = (alto/2) - 24;
		$(".p13_divSustituidaPorRef").height( alturaDivSustituidaPorRef );
		var alturaSustituidaPorRef = $(".p13_parrafoSustituidaPorRef").height();
		if ($("#p13_esExplorer").val()== "ie") { //Estamos con Explorer
			if (!$("#p13_ffppActivaFieldset").is(":visible") && !$("#p13_unitariaFieldset").is(":visible")) {
				$("#p13_SustituidaPorRefFieldset").css( "margin-top", 17);
			}else{
				$("#p13_SustituidaPorRefFieldset").css( "margin-top", 4);
			}
			$(".p13_divSustituidaPorRef").css( "margin-top", alturaDivSustituidaPorRef/2);
		}else{
			$(".p13_parrafoSustituidaPorRef").css( "margin-top", ((alturaDivSustituidaPorRef/2)-10));
		}
		$("#p13_divSustituidaPorRefCorreccion").show();
	}
}

function styleSustituyeSustituta(alto){

	if ($("#p13_SustituyeARefFieldset").is(":visible")) {
		$("#p13_SustituidaPorRef").css( "height", "0px" );
		
	} else {
		if ($("#p13_SustituidaPorRefFieldset").is(":visible")) {
			$("#p13_SustituyeARef").css( "height", "0px" );
		} else {
			$("#p13_SustituidaPorRef").css( "height", "0px" );
			$("#p13_SustituyeARef").css( "height", "0px" );
		}
	}
	
	if (!$("#p13_ffppActivaFieldset").is(":visible") && !$("#p13_unitariaFieldset").is(":visible")) {
		if ($("#p13_SustituyeARefFieldset").is(":visible")) {
			$("#p13_SustituyeARef").css( "margin-top", (alto - ((alto/2) - 1) + 10)+"px" );
		}else{
			$("#p13_SustituidaPorRef").css( "margin-top", (alto - ((alto/2) - 1) + 10)+"px" );
		}
	}
	//if (!$("#p13_ffppActivaFieldset").is(":visible") && !$("#p13_unitariaFieldset").is(":visible")) {
		//$("#p13_divFfppActivaUnitaria").css( "height", "0px" );
		
		if ($("#p13_SustituyeARefFieldset").is(":visible")) {
			styleSustituyeARef(alto);
		}
		
		if ($("#p13_SustituidaPorRefFieldset").is(":visible")) {
			styleSustituidaPorRef(alto);
		}
	//}
}

function stockGauge(valStock){
	var valorMaximo = valStock.sobreStockSuperior;
	var valorStockActual = valStock.stock;
	var limiteRango1 = 0;
	var limiteRango2 = 0;
	var limiteRango3 = 0;
	var numeroRangos = 0;
	var colorRango1;
	var colorRango2;
	var colorRango3;
	
	if (valorMaximo > 2000){
		valorMaximo = Math.ceil(valorMaximo/8.0) * 8;
	} else {
		valorMaximo = Math.ceil(valorMaximo/4.0) * 4;
	}
	if (valorStockActual>valorMaximo){
		s1 = [valorMaximo];
	}else{
		s1 = [Math.ceil(valStock.stock)];
	}
	
	//Establecer rangos
	if(valStock.stockBajo > 0){
		limiteRango1 = Math.ceil(valStock.stockBajo);
		colorRango1 = '#FF0000';
		numeroRangos++;
	}
	if(valStock.sobreStockInferior > valStock.stockBajo && valStock.sobreStockInferior > 0){
		if (limiteRango1 > 0){
			limiteRango2 = Math.ceil(valStock.sobreStockInferior);
			colorRango2 = '#00FF00';
		}else{
			limiteRango1 = Math.ceil(valStock.sobreStockInferior);
			colorRango1 = '#00FF00';
		}
		numeroRangos++;
	}
	if(valorMaximo > valStock.sobreStockInferior && valorMaximo > 0){
		if (limiteRango2 > 0){
			limiteRango3 = valorMaximo;
			colorRango3 = '#FF0000';
		}else{
			if (limiteRango1 > 0){
				limiteRango2 = valorMaximo;
				colorRango2 = '#FF0000';
			}else{
				limiteRango1 = valorMaximo;
				colorRango1 = '#FF0000';
			}
		}
		numeroRangos++;
	}

	if (numeroRangos == 1){
		   plot = $.jqplot('p13_stockChart',[s1],{
			   gridPadding: {top:0, bottom:0, left:0, right:0},
			   seriesDefaults: {            
				   renderer: $.jqplot.MeterGaugeRenderer,            
				   rendererOptions: { 
					   min: 0,
		               max: valorMaximo,
					   intervals:[limiteRango1],                
					   intervalColors:[colorRango1]            
				   }
			   } 
		   });
	}else if (numeroRangos == 2){
		   plot = $.jqplot('p13_stockChart',[s1],{
			   gridPadding: {top:0, bottom:0, left:0, right:0},
			   seriesDefaults: {            
				   renderer: $.jqplot.MeterGaugeRenderer,            
				   rendererOptions: { 
					   min: 0,
		               max: valorMaximo,
					   intervals:[limiteRango1, limiteRango2],                
					   intervalColors:[colorRango1, colorRango2]            
				   }
			   } 
		   });
	}else if (numeroRangos == 3){
	   plot = $.jqplot('p13_stockChart',[s1],{
		   gridPadding: {top:0, bottom:0, left:0, right:0},
		   seriesDefaults: {            
			   renderer: $.jqplot.MeterGaugeRenderer,            
			   rendererOptions: { 
				   min: 0,
	               max: valorMaximo,
				   intervals:[limiteRango1, limiteRango2, limiteRango3],                
				   intervalColors:[colorRango1, colorRango2, colorRango3]            
			   }
		   } 
	   });
	}
}

