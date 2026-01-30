/*
 * MISUMI-300
 */

var dataFromDetalladoPedidoJSP = {
	idJquery: "#detalladoPedidoJsId",
	isCentroVegalsa : function(){
		return $("#detalladoPedidoJsId").data("iscentrovegalsa");
	},
	hasAnyRecordFromSIA : function(){
		return $("#detalladoPedidoJsId").data("hasanyrecordfromsia");
	}
	
}

//Cuando queden menos de 5 minutos, se muestra en rojo.
var minutoLimiteBomba = 5;

var emptyRecords=null;
var gridP70DetalladoPedido=null;
var gridP70DetalladoPedidoTextil=null;
var noDataForCenter=null;
var lastSel;
var selICol; //iCol of selected cell
var selIRow; //iRow of selected cell
var myGrid;
var map = new Map;
var mapInsertados = new Map;
var boxesIncorrect=null;
var activaPantalla=0;
var selectReference=null;
var referencePreviouslyInserted=null;
var errorRedondeoCantidadInsert=null;
var agrupacionNoPermitida = null;
var referenceIncluded=null;
var ischanged=false;
var listadoModificados = new Array();
var literalCajas=null;
var saveResultOK = null;
var saveResultError = null;
var saveResultNoDatos = null;
var referenceEmpty = null;
var areaSel = null;
var seccionSel = null;
var categoriaSel = null;
var refsDeListado = null;
var p70clicked = false;
var tableFilter=null;
var numRow=1;
var invalidCantidadCarne = null;
var flgTodoSIA = null;

var literalSinOferta=null;
var literalOferta=null;

var clicked = false;

var refreshIntervalId = null;

var literalAyuda1ConOferta = null;
var literalAyuda1SinOferta = null;

var listaReferenciasSinPLU=null;

var filaAyuda = null;

var p70mismoValorEurosFinales;
var p70ErrorCalculo;
var p70ErrorPropuestaInicial;

var p70ReferenciasSinPlu ;
var btnSiP70;
var btnNoP70;

var existenDatosSinGuardar = false;

var mostrarMensaje = true;

(function($) {
	$.fn.forceNumeric = function() {
		return this.each(function() {

			$(this).keyup(function() {
				if (!/^[0-9]+$/.test($(this).val())) {
					$(this).val($(this).val().replace(/[^0-9]/g, ''));
				}
			});
		});
	};

})(jQuery);

$(document).ready(
	function() {
		$("#p70_AreaEstadosPedido").hide();
		$('#p70_img_cajasVegalsa').hide();
		//$('#p70_img_eurosVegalsa').hide();
		initializeScreenP70();
		events_p70_btn_buscar();
		events_p70_btn_guardar();
		events_p70_btn_excel();
		events_p70_btn_nuevo();
		events_p70_rad_tipoFiltro();
		
		/*
		 * MISUMI-300
		 */
		if (dataFromDetalladoPedidoJSP.isCentroVegalsa()){
			// seleccionar radio Mapa y disparar evento "change()" para que
			// desencadene la ejecucion de events_p70_rad_tipoFiltro();
			$("input[name=p70_rad_tipoFiltro][value='3']").attr("checked", true);
			$("input[name='p70_rad_tipoFiltro']").change();
	
			//Inicializar objeto
			$("#p70_cmb_mapa").combobox(null);
		}
	
		///Si se pulsa intro, volvemos a lanzar el buscar.
		$(".controlReturnP70").keydown(function(e) {
			if(e.which == 13) {
				e.preventDefault();
				loadContadoresDetallado();
			}
		});
	
		loadP70(locale);
		$(document).on('CargadoCentro', function(e) { 
	
			load_combosP70();
					//MISUMI-349				
	//				load_checkPropuestaP70();
			resetWindow();
			//initializeP56();
		});

			$(document).on(
					'InicioRestaCrono',
					function(e) {
		refreshIntervalId  = setInterval(function() {
			hora = difHorMinSeg.getHours();
			minuto = difHorMinSeg.getMinutes();
			segundo = difHorMinSeg.getSeconds();

			if(hora.toString().length == 1){
				hora = "0" + hora;
			}
			if(minuto.toString().length == 1){
				minuto = "0" + minuto;
			}
			if(segundo.toString().length == 1){
				segundo = "0" + segundo;
			}

							// Puede que antes haya habido una búsqueda que haya
							// llegado a 00:00:00 y se haya quedado en hide()
							// el label que contiene la hora, por eso enseñamos
							// porsiacaso el label.
			$("#p70_cronoKingBomba").show();

							// Insertamos la hora regresiva en el label de la
							// bomba
							$("#p70_cronoKingBomba").text(
									hora + ":" + minuto + ":" + segundo);

							// Cuando queden 5 minutos justos, si hay
							// modificados (existe alerta moficados), mostramos
							// popup aviso.
							if (hora == "00"
									&& parseInt(minuto) == minutoLimiteBomba
									&& parseInt(segundo) == 0
									&& $("#p70_alerta").is(':visible')) {
				createAlert(p70GuardarModificados, "INFO");
			}

							// Si entramos en los últimos 5 minutos parpadea en
							// rojo
							if (hora == "00"
									&& parseInt(minuto) < minutoLimiteBomba) {
								$("#p70_cronoKingBomba").addClass(
										"p70_explotaBomba").removeClass(
										"p70_bombaNoExplota");

				if(count % 2 == 0){
					$("#p70_cronoKingBomba").show();
					count = 1;
				}else{
					$("#p70_cronoKingBomba").hide();
					count = 0;
				}
			}
							if (hora != "00" || minuto != "00"
									|| segundo != "00") {
				//Recalculamos la hora
								difHorMinSeg = new Date().subTime(0, 0, 1,
										difHorMinSeg);
			}											

		}, 1000);
	});

	//Aceptamos solo enteros en el input.
			$("#p70_inputEurosIniciales").filter_input({
				regex : '[0-9]'
			});
			$("#p70_inputEurosFinales").filter_input({
				regex : '[0-9]'
			});

	initializeP75();
	initializeP76();

	events_p70_link_horariosPlataforma();

	//Evento de cajas y euros.
	events_p70_btn_cajasEuros();

	//Evento de botón calcular.
	events_p70_btn_calcular();
	//Evento de botón propuesta inicial
	events_p70_btn_PropuestaInicial();
	//Al pinchar fuera del aplicativo de alarmas PLU, si existen datos sin guardar se avisa al usuario.
	window.onbeforeunload = function(event) {
		
		if(existenDatosSinGuardar){ 
			event.preventDefault();
			// Chrome requires returnValue to be set.
			event.returnValue = "Hay datos del detallado sin guardar. ¿Deseas ir a otra parte del aplicativo?";
		}else{
			//exportarAExcel = false;
			return;
		}
	};
});
function events_p70_rad_tipoFiltro(){
	$("input[name='p70_rad_tipoFiltro']").change(function () {

		if ($("input[name='p70_rad_tipoFiltro']:checked").val() == "1"){
			//Estructura comercial
			$("div#p70_filtroEstructura").attr("style", "display:block");
			$("div#p70_filtroReferencia").attr("style", "display:none");
			$("#p70_filtroMapa").attr("style", "display:none");
			
			$("#p70_fld_referencia").val("");

			//Mostramos el checkbox y deseleccionamos
			$("div#p70_checkBoxIncluirPedido").attr("style", "display:none");
			$('#p70_chk_IncluirPedido').attr('checked', false);
			
			//reseteo busqueda por mapa
			$("#p70_cmb_mapa").combobox('autocomplete',null);
			$("#p70_cmb_mapa").combobox('comboautocomplete',null);
			$('#p70_img_cajasVegalsa').hide();
			//$('#p70_img_eurosVegalsa').hide();
			resetResultados();

		} else if ($("input[name='p70_rad_tipoFiltro']:checked").val() == "2"){ //Referencias
			$("#p70_AreaInformacionCajas").hide();

			$("div#p70_filtroEstructura").attr("style", "display:none");
			$("div#p70_filtroReferencia").attr("style", "display:block");
			$("#p70_filtroMapa").attr("style", "display:none");
			
			$("#p70_cmb_seccion").combobox('autocomplete',null);
			$("#p70_cmb_seccion").combobox('comboautocomplete',null);
			$("#p70_cmb_categoria").combobox('autocomplete',null);
			$("#p70_cmb_categoria").combobox('comboautocomplete',null);
			$("#p70_fld_referencia").focus();

			//Ocultamos el checkbox y seleccionamos
			$("div#p70_checkBoxIncluirPedido").attr("style", "display:none");
			$('#p70_chk_IncluirPedido').attr('checked', true);
			
			//reseteo busqueda por mapa
			$("#p70_cmb_mapa").combobox('autocomplete',null);
			$("#p70_cmb_mapa").combobox('comboautocomplete',null);
			$('#p70_img_cajasVegalsa').hide();
			//$('#p70_img_eurosVegalsa').hide();
			resetResultados();

		} else if ($("input[name='p70_rad_tipoFiltro']:checked").val() == "3") {

			//Mostrar la capa de Mapa y ocultar el resto
			$("#p70_filtroEstructura").attr("style", "display:none");
			$("#p70_filtroReferencia").attr("style", "display:none");
			$("#p70_filtroMapa").attr("style", "display:block");
			
			// Reseteo de busqueda por Estructura comercial
			$("#p70_cmb_seccion").combobox('autocomplete',null);
			$("#p70_cmb_seccion").combobox('comboautocomplete',null);
			$("#p70_cmb_categoria").combobox('autocomplete',null);
			$("#p70_cmb_categoria").combobox('comboautocomplete',null);			
			
			//Oculto informacion de cajas
			$("#p70_AreaInformacionCajas").hide();

			//Resetear de busqueda por Referencia			
			$("#p70_fld_referencia").val("");
			
			//Ocultamos el checkbox y seleccionamos
			$("div#p70_checkBoxIncluirPedido").attr("style", "display:block");
			$('#p70_chk_IncluirPedido').attr('checked', false);
			$('#p70_img_cajasVegalsa').hide();
			//$('#p70_img_eurosVegalsa').hide();
			
			resetResultados();
		}
		
	});
}

function resetResultados (){
	$("div#p70_AreaPestanas").attr("style", "display:none");

	$("#p70_pestanas").tabs("option", "active", 0);

	$("#p70_pestanaSinOfertaCargada").val("");
	$("#p70_pestanaOfertaCargada").val("");

	//Reset datos de la gestion de Euros
	$("#p70_inputEurosIniciales").val("");
	$("#p70_inputEurosFinales").val("");
	$("#p70_inputCajasIniciales").val("");
	$("#p70_inputCajasFinales").val("");

}

var nombrePestanaActivada = "";
function initializeScreenP70(){

	$("#p70_cmb_seccion").combobox(null);

	$("#p70_cmb_categoria").combobox(null);
	$("#p70_cmb_categoria").combobox("disable");

	$("#p70_cmb_seccion").combobox(null);
	
	$("#p70_pestanas")
			.tabs(
					{
						select : function(event, ui) {

			nombrePestanaActivada = ui.panel.id;
							var valorPestanaCargada = $(
									"#" + nombrePestanaActivada + "Cargada")
									.val();
							// Sólo se ejecuta la carga de datos si no se ha
							// cargado antes
							if (valorPestanaCargada == null
									|| !(valorPestanaCargada == "S")) {
				if (nombrePestanaActivada == "p70_pestanaSinOferta"){
					//Carga de la pestaña de Sin Oferta
					resetDatosP75();
					reloadDatosP75('S');
				}else if (nombrePestanaActivada == "p70_pestanaOferta") {
					//Carga de la pestaña de con Oferta
					resetDatosP76();
					reloadDatosP76('S');
				}
			} 

							// Se actualiza el control de carga de datos en
							// pestaña
			$(nombrePestanaActivada + "Cargada").val("S");

			return true;
		}
	});

}

function resetWindow(){
	$( "#centerName" ).bind('focus', function() {
		clearSessionCenter();
	});
}
function clearSessionCenter(){

	$.ajax({
		type : 'GET',
		url : './detalladoPedido/clearSessionCenter.do',
		cache : false,
		dataType : "json",
		success : function(data) {				
			window.location='./welcome.do';
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});	
}
function events_p70_btn_buscar(){
	$("#p70_btn_buscar").click(function () {

		//Reset datos de la gestion de Euros
		$("#p70_inputEurosIniciales").val("");
		$("#p70_inputEurosFinales").val("");
		$("#p70_inputCajasIniciales").val("");
		$("#p70_inputCajasFinales").val("");
		limpiarFiltrosTabla();
		loadContadoresDetallado();
		
		//finder();
	});	
}

function limpiarFiltrosTabla(){
	//limpiar filtros tabla sin oferta
	$(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('clearGridData');
	$("#gbox_gridP75PedidoDatosSinOferta #gs_codArticulo").val("");
	$("#gbox_gridP75PedidoDatosSinOferta #gs_agrupacion").val("");
	$("#gbox_gridP75PedidoDatosSinOferta #gs_codMapa").val("");
	$("#gbox_gridP75PedidoDatosSinOferta #gs_tipo").val("");
	$("#gbox_gridP75PedidoDatosSinOferta #gs_descriptionArt").val("");
	$("#gbox_gridP75PedidoDatosSinOferta #gs_tipoAprovisionamiento").val("");
	$("#gbox_gridP75PedidoDatosSinOferta #gs_stock").val("");
	$("#gbox_gridP75PedidoDatosSinOferta #gs_facing").val("");
	$("#gbox_gridP75PedidoDatosSinOferta #gs_enCurso1").val("");
	$("#gbox_gridP75PedidoDatosSinOferta #gs_enCurso2").val("");
	$("#gbox_gridP75PedidoDatosSinOferta #gs_unidadesCaja").val("");
	$("#gbox_gridP75PedidoDatosSinOferta #gs_cajasPedidas").val("");
	$("#gbox_gridP75PedidoDatosSinOferta #gs_propuesta").val("");
	$("#gbox_gridP75PedidoDatosSinOferta #gs_cantidad").val("");
	$("#gbox_gridP75PedidoDatosSinOferta #gs_motivoPedido").val("");
	$("#gbox_gridP75PedidoDatosSinOferta #gs_precioCostoFinal").val("");
	$("#gbox_gridP75PedidoDatosSinOferta #gs_unidFlEmpuje").val("");
	$("#gbox_gridP75PedidoDatosSinOferta #gs_fechaEntrega").val("");
	$("#gbox_gridP75PedidoDatosSinOferta #gs_nextDayPedido").val("");
	$("#gbox_gridP75PedidoDatosSinOferta #gs_rotacion").val("");
	$("#gbox_gridP75PedidoDatosSinOferta #gs_cajasCortadas").val("");
	$("#gbox_gridP75PedidoDatosSinOferta #gs_incPrevisionVenta").val("");
	$("#gbox_gridP75PedidoDatosSinOferta #gs_smEstatico").val("");
	//limpiar filtros tabla sin oferta textil
	$(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).jqGrid('clearGridData');
	$("#gbox_gridP75PedidoDatosSinOfertaTextil #gs_codArticulo").val("");
	$("#gbox_gridP75PedidoDatosSinOfertaTextil #gs_temporada").val("");
	$("#gbox_gridP75PedidoDatosSinOfertaTextil #gs_estructura").val("");
	$("#gbox_gridP75PedidoDatosSinOfertaTextil #gs_numOrden").val("");
	$("#gbox_gridP75PedidoDatosSinOfertaTextil #gs_modeloProveedor").val("");
	$("#gbox_gridP75PedidoDatosSinOfertaTextil #gs_descrTalla").val("");
	$("#gbox_gridP75PedidoDatosSinOfertaTextil #gs_descrColor").val("");
	$("#gbox_gridP75PedidoDatosSinOfertaTextil #gs_descriptionArt").val("");
	$("#gbox_gridP75PedidoDatosSinOfertaTextil #gs_tipoAprovisionamiento").val("");
	$("#gbox_gridP75PedidoDatosSinOfertaTextil #gs_stock").val("");
	$("#gbox_gridP75PedidoDatosSinOfertaTextil #gs_facing").val("");
	$("#gbox_gridP75PedidoDatosSinOfertaTextil #gs_enCurso1").val("");
	$("#gbox_gridP75PedidoDatosSinOfertaTextil #gs_unidadesCaja").val("");
	$("#gbox_gridP75PedidoDatosSinOfertaTextil #gs_cajasPedidas").val("");
	$("#gbox_gridP75PedidoDatosSinOfertaTextil #gs_propuesta").val("");
	$("#gbox_gridP75PedidoDatosSinOfertaTextil #gs_cantidad").val("");
	$("#gbox_gridP75PedidoDatosSinOfertaTextil #gs_precioCostoFinal").val("");
	$("#gbox_gridP75PedidoDatosSinOfertaTextil #gs_unidFlEmpuje").val("");
	$("#gbox_gridP75PedidoDatosSinOfertaTextil #gs_fechaEntrega").val("");
	$("#gbox_gridP75PedidoDatosSinOfertaTextil #gs_nextDayPedido").val("");
	
	//limpiar filtros tabla con oferta
	$(gridP76DetalladoPedidoOferta.nameJQuery).jqGrid('clearGridData');
	$("#gbox_gridP76PedidoDatosOferta #gs_codArticulo").val("");
	$("#gbox_gridP76PedidoDatosOferta #gs_agrupacion").val("");
	$("#gbox_gridP76PedidoDatosOferta #gs_codMapa").val("");
	$("#gbox_gridP76PedidoDatosOferta #gs_tipo").val("");
	$("#gbox_gridP76PedidoDatosOferta #gs_descriptionArt").val("");
	$("#gbox_gridP76PedidoDatosOferta #gs_tipoAprovisionamiento").val("");
	$("#gbox_gridP76PedidoDatosOferta #gs_stock").val("");
	$("#gbox_gridP76PedidoDatosOferta #gs_facing").val("");
	$("#gbox_gridP76PedidoDatosOferta #gs_enCurso1").val("");
	$("#gbox_gridP76PedidoDatosOferta #gs_enCurso2").val("");
	$("#gbox_gridP76PedidoDatosOferta #gs_unidadesCaja").val("");
	$("#gbox_gridP76PedidoDatosOferta #gs_cajasPedidas").val("");
	$("#gbox_gridP76PedidoDatosOferta #gs_propuesta").val("");
	$("#gbox_gridP76PedidoDatosOferta #gs_cantidad").val("");
	$("#gbox_gridP76PedidoDatosOferta #gs_motivoPedido").val("");
	$("#gbox_gridP76PedidoDatosOferta #gs_precioCostoFinal").val("");
	$("#gbox_gridP76PedidoDatosOferta #gs_unidFlEmpuje").val("");
	$("#gbox_gridP76PedidoDatosOferta #gs_fechaEntrega").val("");
	$("#gbox_gridP76PedidoDatosOferta #gs_nextDayPedido").val("");
	$("#gbox_gridP76PedidoDatosOferta #gs_rotacion").val("");
	$("#gbox_gridP76PedidoDatosOferta #gs_cajasCortadas").val("");
	$("#gbox_gridP76PedidoDatosOferta #gs_incPrevisionVenta").val("");
	$("#gbox_gridP76PedidoDatosOferta #gs_smEstatico").val("");
	
	//limpiar filtros tabla con oferta textil
	$(gridP76DetalladoPedidoOfertaTextil.nameJQuery).jqGrid('clearGridData');
	$("#gbox_gridP76PedidoDatosOfertaTextil #gs_codArticulo").val("");
	$("#gbox_gridP76PedidoDatosOfertaTextil #gs_temporada").val("");
	$("#gbox_gridP76PedidoDatosOfertaTextil #gs_estructura").val("");
	$("#gbox_gridP76PedidoDatosOfertaTextil #gs_numOrden").val("");
	$("#gbox_gridP76PedidoDatosOfertaTextil #gs_modeloProveedor").val("");
	$("#gbox_gridP76PedidoDatosOfertaTextil #gs_descrTalla").val("");
	$("#gbox_gridP76PedidoDatosOfertaTextil #gs_descrColor").val("");
	$("#gbox_gridP76PedidoDatosOfertaTextil #gs_descriptionArt").val("");
	$("#gbox_gridP76PedidoDatosOfertaTextil #gs_tipoAprovisionamiento").val("");
	$("#gbox_gridP76PedidoDatosOfertaTextil #gs_stock").val("");
	$("#gbox_gridP76PedidoDatosOfertaTextil #gs_facing").val("");
	$("#gbox_gridP76PedidoDatosOfertaTextil #gs_enCurso1").val("");
	$("#gbox_gridP76PedidoDatosOfertaTextil #gs_unidadesCaja").val("");
	$("#gbox_gridP76PedidoDatosOfertaTextil #gs_cajasPedidas").val("");
	$("#gbox_gridP76PedidoDatosOfertaTextil #gs_propuesta").val("");
	$("#gbox_gridP76PedidoDatosOfertaTextil #gs_cantidad").val("");
	$("#gbox_gridP76PedidoDatosOfertaTextil #gs_precioCostoFinal").val("");
	$("#gbox_gridP76PedidoDatosOfertaTextil #gs_unidFlEmpuje").val("");
	$("#gbox_gridP76PedidoDatosOfertaTextil #gs_fechaEntrega").val("");
	$("#gbox_gridP76PedidoDatosOfertaTextil #gs_nextDayPedido").val("");
}
function mostrarOcultarBotonDetalladoVegalsa(){
	// Si el boton existe en la pagina (el centro esta parametrizado para
	// mostrarlo)
	var selectedRadioButton = $("input[name='p70_rad_tipoFiltro']:checked")
			.val();

	if (selectedRadioButton=="3"){
		var codMapa = $('#p70_cmb_mapa').val();
		$.ajax({
			type : 'GET',
			url : './detalladoPedido/resumenPropuestaVegalsa.do?codMapa='
					+ codMapa,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			cache : false,
			success : function(data) {	
				if (data!=null && data.length>0){
					$('#p70_img_cajasVegalsa').show();
					//$('#p70_img_eurosVegalsa').show();
				}else{
					$('#p70_img_cajasVegalsa').hide();
					//$('#p70_img_eurosVegalsa').hide();
				}
			},
			error : function (xhr, status, error){
				handleError(xhr, status, error, locale);
			}			
		});	

	}
}

function fulfilInformation(){
	if ($("#p70_cmb_categoria").combobox('getValue') != "null"
			&& $("#p70_cmb_categoria").combobox('getValue') != null) {
		$("#p70_AreaInformacionCajas").show();
		var literal=jQuery("#p70_cmb_categoria :selected").text();
		$("#p70_lbl_informacionSeccion").text(literal+":" );
		loadBoxes();

	} else if ($("#p70_cmb_seccion").combobox('getValue') != "null"
			&& $("#p70_cmb_seccion").combobox('getValue') != null) {
		var literal=jQuery("#p70_cmb_seccion :selected").text();
		$("#p70_lbl_informacionSeccion").text(literal+":" );
		$("#p70_AreaInformacionCajas").show();
		loadBoxes();
	}else{

		$("#p70_AreaInformacionCajas").hide();
	}
}
function loadBoxes(){
	var seccion = '';
	if ("" != $("#p70_cmb_seccion").combobox('getValue')
			&& "null" != $("#p70_cmb_seccion").combobox('getValue')) {
		var arrAreaSeccion = $("#p70_cmb_seccion").combobox('getValue').split(
				"*");
		seccion = arrAreaSeccion[1];  

	}
	var categoria = '';
	if ($("#p70_cmb_categoria").val() != null) {
		if ("" != $("#p70_cmb_categoria").combobox('getValue')
				&& "null" != $("#p70_cmb_categoria").combobox('getValue')) {
			var arrAreaCategoria = $("#p70_cmb_categoria").val().split("*");
			if ("" != arrAreaCategoria[0] && "null" != arrAreaCategoria[0]){
				categoria = arrAreaCategoria[0];
			}
		}
	}

	var detalladoPedido = new DetalladoPedido(seccion, categoria, $(
			"#p70_fld_referencia").val(), null);
	var objJson = $.toJSON(detalladoPedido);	

	$.ajax({
		type : 'POST',
		url : './detalladoPedido/loadCantidades.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		cache : false,
		dataType : "json",
		success : function(data) {	
			if (data != null && data != ""){
				$("#p70_lbl_informacionCajas").text(data+" "+literalCajas);
			}else{
				$("#p70_lbl_informacionCajas").text(refsDeListado);
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});	
}

function loadContadoresDetallado(saveChanges){

	var messageVal=findValidation();

	if (messageVal!=null){

		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
		activaPantalla=0;

	}else{
		var selectedRadioButton = $("input[name='p70_rad_tipoFiltro']:checked")
				.val();
		var detalladoPedido = null;

		if (selectedRadioButton == "1") { // Se esta consultando por
											// estructura
			var seccion = '';
			if (null != seccionSel){
				seccion = seccionSel;
			} else {
				var arrAreaSeccion = $("#p70_cmb_seccion").val().split("*");
				if ("" != arrAreaSeccion[1] && "null" != arrAreaSeccion[1]){
					seccion = arrAreaSeccion[1];
				}

				var arrAreaSeccion = $("#p70_cmb_seccion").val().split("*");
				if ("" != arrAreaSeccion[0] && "null" != arrAreaSeccion[0]){
					area = arrAreaSeccion[0];
				}
			}
			var categoria = '';
			if (null != categoriaSel){
				categoria = categoriaSel;
			} else {

				if ($("#p70_cmb_categoria").val() != null) {
					var arrAreaCategoria = $("#p70_cmb_categoria").val().split(
							"*");
					if ("" != arrAreaCategoria[0]
							&& "null" != arrAreaCategoria[0]) {
						categoria = arrAreaCategoria[0];
					}
				}
			}

			detalladoPedido = new DetalladoPedido(seccion, categoria, null, null);

		} else if (selectedRadioButton == "2") { // Se esta consultando por REFERENCIA

			detalladoPedido = new DetalladoPedido(null, null, $("#p70_fld_referencia").val(), null);
		
		} else if (selectedRadioButton == "3"){//Se esta consultando por mapa

			let codMapa = $("#p70_cmb_mapa").val();

			detalladoPedido = new DetalladoPedido ();
			
			if (null != codMapa && codMapa > 0){
				detalladoPedido.codMapa = codMapa;
			}
			  
		}

		if ($("#p70_chk_IncluirPedido").is(':visible')) { // Si el check es
															// visible, la
															// busqueda
															// dependera si el
															// checkbox está
															// seleccionado o
															// no.
			//Miramos si el checkbox está seleccionado o no.
			flgIncluirPropPed = 'N';
			if($("#p70_chk_IncluirPedido").prop('checked')){
				flgIncluirPropPed = 'S';
			}
		} else { // Si el check NO es visible, la busqueda actuara como si el
					// check estuviese seleccionado.
			flgIncluirPropPed = 'S';
		}
		//Introducimos el valor del checkbox para realizar la búsqueda.
		detalladoPedido.flgIncluirPropPed = flgIncluirPropPed;

		var objJson = $.toJSON(detalladoPedido);	

		// Carga de la estructura buscada para controlarla en la ventana de
		// nuevo
		$("#p70_cmb_seccion_b").val(seccion);
		$("#p70_cmb_categoria_b").val(categoria);
		//$('#gridP70PedidoDatos').setGridParam({page:1});
		//$('#gridP70PedidoDatosTextil').setGridParam({page:1});

		if(saveChanges != 'S'){
			$('#gridP75PedidoDatosSinOferta').setGridParam({
				page : 1
			});
			$('#gridP75PedidoDatosSinOfertaTextil').setGridParam({
				page : 1
			});
			$('#gridP76PedidoDatosOferta').setGridParam({
				page : 1
			});
			$('#gridP76PedidoDatosOfertaTextil').setGridParam({
				page : 1
			});
		}

		// Si area es 1 y seccion es 1-FRUTA o seccion es 7-PESCADERÍA y su
		// categoría es 12-ESPECIALIDADES enseñamos el mensaje en rojo
		if(area == 1 && (seccion == 1 || (seccion == 7 && categoria == 12))){
			$("#p70_lbl_msgCantidades").show();
		}else{
			$("#p70_lbl_msgCantidades").hide();
		}

		$.ajax({
			type : 'POST',
			url : './detalladoPedido/loadContadoresDetallado.do',
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			cache : false,
			success : function(data) {	
				mostrarOcultarBotonDetalladoVegalsa();
				if (data != null && data != '') {

					$("#p70_fld_contadorSinOferta").text(
							literalSinOferta + " (" + data.contadorSinOferta
									+ ")");
					$("#p70_fld_contadorOferta").text(
							literalOferta + " (" + data.contadorOferta + ")");
					defaultDescription = data.defaultDescription;

					$("#p70_pestanaSinOfertaTab").show();

					// $("#p70_AreaPestanas").attr("style", "display:block");
					// Esto tiene que ir en la carga de los pedidon sin oferta

					//Llamamos al buscar
					finder(saveChanges);

				}else{
					$("#p70_AreaPestanas").attr("style", "display:none");
				}

			},
			error : function (xhr, status, error){
				handleError(xhr, status, error, locale);
			}			
		});		

	}

} 

function finder(saveChanges){
	var messageVal=findValidation();
	resetResultados();

	if (messageVal!=null){

		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
		activaPantalla=0;

	}else{
		var seccion = '';
		if (null != seccionSel){
			seccion = seccionSel;
		} else {
			var arrAreaSeccion = $("#p70_cmb_seccion").val().split("*");
			if ("" != arrAreaSeccion[1] && "null" != arrAreaSeccion[1]){
				seccion = arrAreaSeccion[1];
			}
		}
		var categoria = '';
		if (null != categoriaSel){
			categoria = categoriaSel;
		} else {
			if ($("#p70_cmb_categoria").val() != null) {
				var arrAreaCategoria = $("#p70_cmb_categoria").val().split("*");
				if ("" != arrAreaCategoria[0] && "null" != arrAreaCategoria[0]){
					categoria = arrAreaCategoria[0];
				}
			}
		}
		// Carga de la estructura buscada para controlarla en la ventana de
		// nuevo
		$("#p70_cmb_seccion_b").val(seccion);
		$("#p70_cmb_categoria_b").val(categoria);

		//reloadGrid('N');
		reloadGrid(saveChanges);
		calculoCronometro();
		activaPantalla=1;
	}

}
function findValidation(){
	var messageVal=null;
	
	var selectedRadioButton = $("input[name='p70_rad_tipoFiltro']:checked")
			.val();
	
	if (selectedRadioButton == "1" || selectedRadioButton == "3"){
		//Estructura comercial o Mapa

		if ($("#centerId").val()==null || $("#centerId").val()==""){
			messageVal = centerRequired;
		} 

	} else { 
		//Referencias
		
		if ($("#centerId").val()==null || $("#centerId").val()==""){
			messageVal = centerRequired;

		} else if ($("#p70_fld_referencia").val() == null
				|| $("#p70_fld_referencia").val() == '') {
			messageVal = referenceRequired;
		}	

	} 
	
	return messageVal;

}

function events_p70_btn_guardar(){
	$("#p70_btn_guardar").click(function () {
		// si pulsamos guardar entonces tiene que dejar salir de la pantalla sin el mensaje de confirmación
		existenDatosSinGuardar = false;
		// Guardamos los datos y nos mantenemos en
		// la hoja y ordenación seleccionados.
		saveData();
	});	
}

function events_p70_btn_excel(){
	$("#p70_btn_excel").click(function () {
		if($("#p70_alerta").is(':visible')){
			createAlert(p70GuardarModificados,"INFO");
		}
		exportExcel();
	});
}

function events_p70_btn_nuevo(){
	$("#p70_btn_nuevo").click(function () {
		limpiarMensajesP71();

		//Escondemos la caja de calcular antes de sacar los resultados
		//de las tablas.
		$("#p70_AreaCajasEuros").hide();

		// Guardamos en la temporal los posibles cambios realizados en SIN
		// OFERTA y en OFERTA,
		// para que al pulsar volver en nuevo, los tenga en cuenta y no los
		// pierda.
		reloadGridP75("S");
		reloadGridP76("S");

		$( "#p71_AreaModificacion" ).dialog( "open" );
	});	
}

function load_combosP70(){

	load_cmbSeccion();
}

function load_checkPropuestaP70(){
	getTotalFlgPropuesta();
}

function loadP70(locale){

	this.i18nJSON = './misumi/resources/p70DetalladoPedido/p70detalladoPedido_'
			+ locale + '.json';

	var jqxhr = $.getJSON(this.i18nJSON, function(data) {

	}).success(function(data) {

		referenceRequired = data.referenceRequired;
		centerRequired = data.centerRequired;
		sectionRequired = data.sectionRequired;
		boxesIncorrect=data.boxesIncorrect;
		literalCajas=data.literalCajas;
		tableFilter= data.tableFilter;

		noDataForCenter =  data.noDataForCenter;
		selectReference=data.selectReference;
		referencePreviouslyInserted=data.referencePreviouslyInserted;
		errorRedondeoCantidadInsert=data.errorRedondeoCantidadInsert;
		agrupacionNoPermitida=data.agrupacionNoPermitida;
		referenceIncluded=data.referenceIncluded;
		referenceEmpty = data.referenceEmpty;
		referenceEmpuje = data.referenceEmpuje;
		saveResultOK = data.saveResultOK;
		saveResultError = data.saveResultError;
		saveResultNoDatos = data.saveResultNoDatos;
		saveResultErrorModificar = data.saveResultErrorModificar;
		refsDeListado = data.refsDeListado;
		invalidCantidadCarne = data.invalidCantidadCarne;
		msgBloqueoAlAlza = data.msgBloqueoAlAlza;

		literalAyuda1ConOferta = data.literalAyuda1ConOferta;
		literalAyuda1SinOferta = data.literalAyuda1SinOferta;
		literalSinOferta = data.literalSinOferta;
		literalOferta = data.literalOferta;

		p70mismoValorEurosFinales = data.p70mismoValorEurosFinales;
		p70ErrorCalculo = data.p70ErrorCalculo;
		p70ErrorPropuestaInicial = data.p70ErrorPropuestaInicial;
		p70SeleccionarRotacion = data.p70SeleccionarRotacion;
		p70SeleccionarOferta = data.p70SeleccionarOferta;
		p70GuardarModificados = data.p70GuardarModificados;
		p70ReferenciasSinPlu = data.p70ReferenciasSinPlu;
		btnSiP70 = data.btnSiP70;
		btnNoP70 = data.btnNoP70;
		if ($("#p70_wsResult").val()==null || $("#p70_wsResult").val()==0 ){
			disableScreen();
			createAlert(replaceSpecialCharacters(noDataForCenter), "ERROR");
		}else{
			enableScreen();
		}

	}).error(function(xhr, status, error) {
		handleError(xhr, status, error, locale);
	});
}
function disableScreen(){
	$("input[name='p70_rad_tipoFiltro']").attr("disabled","disabled");
	$("#p70_cmb_seccion").combobox("disable");
	$("#p70_cmb_categoria").combobox("disable");

	$("#p70_btn_buscar").attr("disabled", "disabled");
	$("#p70_btn_excel").attr("disabled", "disabled");
	$("#p70_btn_nuevo").attr("disabled", "disabled");
	$("#p70_btn_guardar").attr("disabled", "disabled");

	//Deshabilitamos el check
	$("#p70_chk_IncluirPedido").attr("disabled", "disabled");

	activaPantalla=0;

}
function enableScreen(){
	$("input[name='p70_rad_tipoFiltro']").attr("disabled",false);
	$("#p70_cmb_seccion").combobox("enable");
	$("#p70_cmb_categoria").combobox("disable");

	$("#p70_btn_buscar").attr("disabled", false);
	$("#p70_btn_excel").attr("disabled", false);
	$("#p70_btn_nuevo").attr("disabled", false);
	$("#p70_btn_guardar").attr("disabled", false);

	//Habilitamos el check
	$("#p70_chk_IncluirPedido").attr("disabled", false);

}

function reloadGrid(saveChanges){

	var messageVal=findValidation();
	resetResultados();

	if (messageVal!=null){
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		reloadDatosP75(saveChanges);
		clicked = false;
	}

}

Date.prototype.subTime= function(h,m,s,horaResta){
	if(horaResta != null){
		horaResta.setHours(horaResta.getHours() - h);
		horaResta.setMinutes(horaResta.getMinutes() - m);
		horaResta.setSeconds(horaResta.getSeconds() - s);		
		return horaResta;
	}else{
		this.setHours(h - this.getHours());
		this.setMinutes(m - this.getMinutes());
		if(this.getSeconds() < s){
			this.setMinutes(this.getMinutes() - 1);
		}
		this.setSeconds(s - this.getSeconds());	

		return this;
	}
}

function calculoCronometro(){
	//Paramos el trigger. Es necesario pararlo, porque si no
	//habría múltiples triggers encendidos y los segundos no 
	//bajarían de 1 en 1. Por cada trigger se restaría 1 y cada
	//buscar generaría un trigger haciendo que el segundero se
	//reduzca a una velocidad desproporcionada.
	clearInterval(refreshIntervalId);

	var detalladoPedido = new DetalladoPedido($("#p70_cmb_seccion_b").val(), $(
			"#p70_cmb_categoria_b").val(), $("#p70_fld_referencia").val(),
			null, null, $("#centerId").val());
	
	let codMapa = $("#p70_cmb_mapa").val();
	
	if (null != codMapa && codMapa > 0){
		detalladoPedido.codMapa = codMapa;
	}
	
	var objJson = $.toJSON(detalladoPedido);	

	$.ajax({
		type : 'POST',
		url : './detalladoPedido/calculoCronometro.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {
			if(data != null){
				$("#p70_cronoKingBomba").text("");
				if(!(data.numHorasLimite <= 1 && data.cuenta > 0)){
					// Si ha habido modificacion en el grid se muestra la
					// alerta. Si no, no se muestra nada.
					if($("#p70_modificado").val() == "S"){
						//Mostramos el div de la bomba.
						$("#p70_textoYKingBomba").show();

						//Mostramos el texto.
						$("#p70_textoKingBomba").show();

						//Mostramos la alerta.
						$("#p70_alerta").show();

						//Ocultamos la bomba
						$("#p70_kingBomba").hide();
					}else{
						// No mostramos nada porque no es hora de mostrar el
						// segundero y no hay modificaciones.
						$("#p70_textoYKingBomba").hide();	
					}
				}else{	
					$("#p70_cronoKingBomba").removeClass("p70_explotaBomba")
							.addClass("p70_bombaNoExplota");
					//Obtenemos la hora y minuto exacto limite
					var horaMinutoLimite = data.horaLimite.split(":");
					horaLimite = horaMinutoLimite[0];
					minutoLimite = horaMinutoLimite[1]; 

					// Calculamos la hora actual para compararla con la hora
					// límite.
					var fechaAhora =  new Date();

					// Si la hora limite es mayor a la hora actual se calcula la
					// diferencia horaria
					if(horaLimite > fechaAhora.getHours()){
						// Calculamos la diferencia de horas, minutos y
						// segundos.
						difHorMinSeg = new Date().subTime(horaLimite,
								minutoLimite, 60, null);
					}// Si la hora límite es igual a la hora actual, nos
						// fijamos en los minutos
					else if(horaLimite == fechaAhora.getHours()){
						// Si los minutos limite son mayores a los de la hora
						// actual se calcula la diferencia de horas.
						//Si no se pone 00:00:00
						if(minutoLimite > fechaAhora.getMinutes()){
							// Calculamos la diferencia de horas, minutos y
							// segundos.
							difHorMinSeg = new Date().subTime(horaLimite,
									minutoLimite, 60, null);
						}else{
							difHorMinSeg = new Date();
							difHorMinSeg.setHours(0);
							difHorMinSeg.setMinutes(0);
							difHorMinSeg.setSeconds(0);
						}	
					}// Si la hora limite es menor que la hora actual,
						// mostramos 00:00:00
					else{
						difHorMinSeg = new Date();
						difHorMinSeg.setHours(0);
						difHorMinSeg.setMinutes(0);
						difHorMinSeg.setSeconds(0);
					}

					// Variable que sirve para el parpadeo cuando está en
					// 00:00:00
					count = 0;		

					//Lanzamos el trigger regresivo
					$(document).trigger('InicioRestaCrono');

					//Mostramos el div de la bomba
					$("#p70_textoYKingBomba").show();

					//Mostramos el texto.
					$("#p70_textoKingBomba").show();

					//Mostramos la alerta si hay modificados.
					if($("#p70_modificado").val() == "S"){
						$("#p70_alerta").show();
					}

					//Mostramos la bomba.
					$("#p70_kingBomba").show();					
				}				
			}else{
				$("#p70_textoYKingBomba").hide();
			}
		},
		error : function (xhr, status, error){
			$("#AreaResultados .loading").css("display", "none");	
			handleError(xhr, status, error, locale);				
		}	
	});
}

function saveData(){

	if (!p70clicked) {
		p70clicked = true;

		var area = null;
		var seccion = null;
		var detalladoPedido = null;

		let filtroTipo = $("input[name='p70_rad_tipoFiltro']:checked").val();
		
		// Se esta consultando "Por estructura"
		if (filtroTipo == "1") {
			
			if (null != $("#p70_cmb_seccion").val()){
				var arrAreaSeccion = $("#p70_cmb_seccion").val().split("*");
				area = arrAreaSeccion[0];
				seccion = arrAreaSeccion[1];
			}

			// Si se trata de TEXTIL.
			if (area==3){
				listaModificadosP75 = obtenerListaP75ModificadosTextil('S',dataFromDetalladoPedidoJSP.isCentroVegalsa());
				listaModificadosP76 = obtenerListaP76ModificadosTextil('S',dataFromDetalladoPedidoJSP.isCentroVegalsa());
			} else {
				listaModificadosP75 = obtenerListaP75Modificados('S',dataFromDetalladoPedidoJSP.isCentroVegalsa());
				listaModificadosP76 = obtenerListaP76Modificados('S',dataFromDetalladoPedidoJSP.isCentroVegalsa());
			}

			listadoModificados = listaModificadosP75.concat(listaModificadosP76);
			detalladoPedido = new DetalladoPedido(
					$("#p70_cmb_seccion_b").val(), $("#p70_cmb_categoria_b")
							.val(), null, listadoModificados);
			
		//Se esta consultando "Por referencia" o "Por mapa".
		} else { 

			area = $("#1_grupo1").val();
//			seccion = $("#1_grupo2").val();
			
			// Si se trata de TEXTIL.
			if (area==3){
				listaModificadosP75 = obtenerListaP75ModificadosTextil('S',dataFromDetalladoPedidoJSP.isCentroVegalsa());
				listaModificadosP76 = obtenerListaP76ModificadosTextil('S',dataFromDetalladoPedidoJSP.isCentroVegalsa());
			} else {
				listaModificadosP75 = obtenerListaP75Modificados('S',dataFromDetalladoPedidoJSP.isCentroVegalsa());
				listaModificadosP76 = obtenerListaP76Modificados('S',dataFromDetalladoPedidoJSP.isCentroVegalsa());
			}

			listadoModificados = listaModificadosP75.concat(listaModificadosP76);

			detalladoPedido = new DetalladoPedido(null, null, $("#p70_fld_referencia").val(), listadoModificados);
			
			let codMapa = $("#p70_cmb_mapa").val();
			if (null != codMapa && codMapa > 0){
				detalladoPedido.codMapa = codMapa;
			}

		}

		// Introducimos el valor del checkbox para realizar la búsqueda después
		// de guardar los datos.
		detalladoPedido.flgIncluirPropPed = flgIncluirPropPed;

		if (filtroTipo == "3"){
			detalladoPedido.filtroMapa = true;
		}

		var objJson = $.toJSON(detalladoPedido);

		$.ajax({
			type : 'POST',
					// url :
					// './detalladoPedido/saveData.do?center='+$("#centerId").val()+'&page='+gridP70.getActualPage()+'&max='+gridP70.getRowNumPerPage()+'&index='+gridP70.getSortIndex()+'&sortorder='+gridP70.getSortOrder(),
			url : './detalladoPedido/saveData.do?center='+ $("#centerId").val(),
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			cache : false,
			success : function(data) {	
				var seccionAntesActualizar=$("#p70_cmb_seccion_b").val();
				if (data.datos.records > 0){
					cargarPestañasDespuesDeSalvar();
				} else {					
					createAlert(replaceSpecialCharacters(gridP70DetalladoPedido.emptyRecords), "ERROR");
				}

				$("#p70_btn_buscar").focus();
						$("#p70_AreaResultados .loading")
								.css("display", "none");
				getPLUsPendientes(seccionAntesActualizar);
				
				countModificados();
				
			},
			error : function (xhr, status, error){	
				$("#p70_AreaResultados .loading").css("display", "none");
				handleError(xhr, status, error, locale);				
			},	
			complete : function (xhr, status){
				p70clicked = false;
			}
		});	

	}

}	

function cargarPestañasDespuesDeSalvar() {

	// Reseteamos los modificados para que no me pinte la libreta en los
	// modificados, en todos debe mostrar el disquete de guardado
	deleteSeleccionadosP75();
	deleteSeleccionadosP76();

	// Borramos los grid para volver a cargarlo con lo actualizado en el
	// T_DETALLADO PEDIDO
	resetDatosP75Save();
	resetDatosP75TextilSave();
	resetDatosP76Save();
	resetDatosP76TextilSave();

	// Calculamos los contadores, con una 'S', no nos interesa resetear los
	// estados del T_DETALLADO_PEDIDO, tenemos que mostrarlo en el grid.
	loadContadoresDetallado('S');
}

function controlNavegacion(e) {
	var idActual = e.target.id;
	var idFoco;
	//Obtención de fila y columna actuales
	var fila = idActual.substring(0, idActual.indexOf("_"));
	var nombreColumna = idActual.substring(idActual.indexOf("_")+1);
	var validacionNavegacion = 'S';

	var key = e.which; //para soporte de todos los navegadores
	if (key == 13){//Tecla enter, guardado
		e.preventDefault();
		finder();
		e.stopPropagation();
	}
	//Flechas de cursores para navegación
	if (key == 37){//Tecla izquierda
		e.preventDefault();
	}
	if (key == 38){//Tecla arriba
		e.preventDefault();
		idFoco = (parseInt(fila,10)-1) + "_" + nombreColumna;
		$("#"+idFoco).focus();
		$("#"+idFoco).select();
	}
	if (key == 39){//Tecla derecha
		e.preventDefault();
	}
	if (key == 40){//Tecla abajo
		e.preventDefault();
		idFoco = (parseInt(fila,10)+1) + "_" + nombreColumna;
		$("#"+idFoco).focus();
		$("#"+idFoco).select();
	}
}

function exportExcel(){

	var messageVal=findValidation();
	if (messageVal!=null){
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{

		var area = null;

		//if ($("#gridP75PedidoDatosSinOfertaTextil").is(':visible')) {
		//var gridActual = "gridP75PedidoDatosSinOfertaTextil";
		//} else {
		//var gridActual = "gridP75PedidoDatosSinOferta";
		//}

		// Miramos cual de las dos pestañas activadas, ya que el excel mostrara
		// los datos de Sin Oferta o los de Oferta.

		if ($("#p70_chk_IncluirPedido").is(':visible')) { // Si el check es
															// visible, la
															// busqueda
															// dependera si el
															// checkbox está
															// seleccionado o
															// no.
			//Miramos si el checkbox está seleccionado o no.
			flgIncluirPropPed = 'N';
			if($("#p70_chk_IncluirPedido").prop('checked')){
				flgIncluirPropPed = 'S';
			}
		} else { // Si el check NO es visible, la busqueda actuara como si el
					// check estuviese seleccionado.
			flgIncluirPropPed = 'S';
		}

		var flgOferta = "";

		if ((nombrePestanaActivada == "")
				|| (nombrePestanaActivada == "p70_pestanaSinOferta")) {// Esta
																		// activa
																		// la
																		// primera
																		// pestaña,
																		// la de
																		// Sin
																		// Oferta
			flgOferta = "N";
			if ($("input[name='p70_rad_tipoFiltro']:checked").val() == "1") { // Se
																				// esta
																				// consultando
																				// por
																				// estructura

				if (null != $("#p70_cmb_seccion").val()){
					var arrAreaSeccion = $("#p70_cmb_seccion").val().split("*");
					area = arrAreaSeccion[0]
				}

				if(null != arrAreaSeccion && arrAreaSeccion[0]==3){
					var colModel = $("#gridP75PedidoDatosSinOfertaTextil")
							.jqGrid("getGridParam", "colModel");
					var colNames = $("#gridP75PedidoDatosSinOfertaTextil")
							.jqGrid("getGridParam", "colNames");
					var sortIndex = gridP75DetalladoPedidoSinOfertaTextil
							.getSortIndex();
					var sortOrder = gridP75DetalladoPedidoSinOfertaTextil
							.getSortOrder();

				} else {
					var colModel = $("#gridP75PedidoDatosSinOferta").jqGrid(
							"getGridParam", "colModel");
					var colNames = $("#gridP75PedidoDatosSinOferta").jqGrid(
							"getGridParam", "colNames");
					var sortIndex = gridP75DetalladoPedidoSinOferta
							.getSortIndex();
					var sortOrder = gridP75DetalladoPedidoSinOferta
							.getSortOrder();
				}

			} else { //Se esta consultando por referencia

				area = $("#1_grupo1").val();

				if( area ==3){
					var colModel = $("#gridP75PedidoDatosSinOfertaTextil")
							.jqGrid("getGridParam", "colModel");
					var colNames = $("#gridP75PedidoDatosSinOfertaTextil")
							.jqGrid("getGridParam", "colNames");
					var sortIndex = gridP75DetalladoPedidoSinOfertaTextil
							.getSortIndex();
					var sortOrder = gridP75DetalladoPedidoSinOfertaTextil
							.getSortOrder();

				} else {
					var colModel = $("#gridP75PedidoDatosSinOferta").jqGrid(
							"getGridParam", "colModel");
					var colNames = $("#gridP75PedidoDatosSinOferta").jqGrid(
							"getGridParam", "colNames");
					var sortIndex = gridP75DetalladoPedidoSinOferta
							.getSortIndex();
					var sortOrder = gridP75DetalladoPedidoSinOferta
							.getSortOrder();
				}

			}

		} else { //Esta activa la pestaña de Oferta

			flgOferta = "S";

			if ($("input[name='p70_rad_tipoFiltro']:checked").val() == "1") { // Se
																				// esta
																				// consultando
																				// por
																				// estructura

				if (null != $("#p70_cmb_seccion").val()){
					var arrAreaSeccion = $("#p70_cmb_seccion").val().split("*");
					area = arrAreaSeccion[0]
				}

				if(null != arrAreaSeccion && arrAreaSeccion[0]==3){
					var colModel = $("#gridP76PedidoDatosOfertaTextil").jqGrid(
							"getGridParam", "colModel");
					var colNames = $("#gridP76PedidoDatosOfertaTextil").jqGrid(
							"getGridParam", "colNames");
					var sortIndex = gridP76DetalladoPedidoOfertaTextil
							.getSortIndex();
					var sortOrder = gridP76DetalladoPedidoOfertaTextil
							.getSortOrder();

				} else {
					var colModel = $("#gridP76PedidoDatosOferta").jqGrid(
							"getGridParam", "colModel");
					var colNames = $("#gridP76PedidoDatosOferta").jqGrid(
							"getGridParam", "colNames");
					var sortIndex = gridP76DetalladoPedidoOferta.getSortIndex();
					var sortOrder = gridP76DetalladoPedidoOferta.getSortOrder();
				}

			} else { //Se esta consultando por referencia

				area = $("#1_grupo1").val();

				if( area ==3){
					var colModel = $("#gridP76PedidoDatosOfertaTextil").jqGrid(
							"getGridParam", "colModel");
					var colNames = $("#gridP76PedidoDatosOfertaTextil").jqGrid(
							"getGridParam", "colNames");
					var sortIndex = gridP76DetalladoPedidoOfertaTextil
							.getSortIndex();
					var sortOrder = gridP76DetalladoPedidoOfertaTextil
							.getSortOrder();
				} else {
					var colModel = $("#gridP76PedidoDatosOferta").jqGrid(
							"getGridParam", "colModel");
					var colNames = $("#gridP76PedidoDatosOferta").jqGrid(
							"getGridParam", "colNames");
					var sortIndex = gridP76DetalladoPedidoOferta.getSortIndex();
					var sortOrder = gridP76DetalladoPedidoOferta.getSortOrder();
				}

			}

		}

		var newColname=new Array();
		var myColumns = new Array();
		var myColumnsWidth = new Array();

		j=0;
		for (i=0;i<colModel.length;i++){
			if (colModel[i].hidden==false){

				//Hay que proporcionar todas las columnas excepto la de estado
				if (colModel[i].name != "rn"
						&& colModel[i].name != 'estadoGrid'
						&& colModel[i].name != 'subgrid' && !colModel[i].hidden) {
					newColname[j]=colNames [i];
					myColumns[j]=colModel[i].name;
					myColumnsWidth[j] = colModel[i].width;
					j++;
				}	    	
			}

		}

		var form = "<form name='csvexportform' action='detalladoPedido/exportGrid.do'  accept-charset='ISO-8859-1' method='get'>";
		form = form + "<input type='hidden' name='headers' value='"
				+ newColname + "'>";
		form = form + "<input type='hidden' name='model' value='" + myColumns
				+ "'>";
		form = form + "<input type='hidden' name='widths' value='"
				+ myColumnsWidth + "'>";
		form = form + "<input type='hidden' name='index' value='" + sortIndex
				+ "'>";
		form = form + "<input type='hidden' name='sortOrder' value='"
				+ sortOrder + "'>";
		
		//MISUMI-300
		var codMapa = $("#p70_cmb_mapa").val();
		if (null != codMapa && codMapa > 0){
			form = form + "<input type='hidden' name='codMapa' value='"
					+ codMapa + "'>";
		}

		if ($("input[name='p70_rad_tipoFiltro']:checked").val() == "1"){

			if ($("#p70_cmb_seccion").combobox('getValue') != "null"
					&& $("#p70_cmb_seccion").combobox('getValue') != null) {
				var arrAreaSeccion = $("#p70_cmb_seccion").val().split("*");
				form = form + "<input type='hidden' name='grupo2' value='"
						+ arrAreaSeccion[1] + "'>";
			}	
			if ($("#p70_cmb_categoria").combobox('getValue') != "null"
					&& $("#p70_cmb_categoria").combobox('getValue') != null) {
				var arrAreaCategoria = $("#p70_cmb_categoria").val().split("*");
				form = form + "<input type='hidden' name='grupo3' value='"
						+ arrAreaCategoria[0] + "'>";
			}	
			//Introducimos el valor del checkbox para realizar la búsqueda.
			form = form
					+ "<input type='hidden' name='flgIncluirPropPed' value='"
					+ flgIncluirPropPed + "'>";

			// Introducimos el valor del flgOferta para saber si hay que mostrar
			// los pedidos de Oferta o SinOferta.
			form = form + "<input type='hidden' name='flgOferta' value='"
					+ flgOferta + "'>";
		}
		if ($("input[name='p70_rad_tipoFiltro']:checked").val() != "1"){
			if ($("#p70_fld_referencia").val() != ""
					&& $("#p70_fld_referencia").val() != null) {
				form = form
						+ "<input type='hidden' name='codigoArticulo' value='"
						+ $("#p70_fld_referencia").val() + "'>";
			}
			if ($("input[name='p70_rad_tipoFiltro']:checked").val() == "3") {
				//Incluir el valor del Checkbox "Inc. Prop. Ped" 
				// para obtener los datos del EXCEL.
				form = form + "<input type='hidden' name='flgIncluirPropPed' value='"
					+ flgIncluirPropPed + "'>";
				// Introducimos el valor del flgOferta para saber si hay que mostrar
				// los pedidos de Oferta o SinOferta.
				form = form + "<input type='hidden' name='flgOferta' value='"
						+ flgOferta + "'>";
			}
		}	

		form = form
				+ "</form><script>document.csvexportform.submit();</script>";
		Show_Popup(form);	 
	}

}

function load_cmbSeccion(){	
	var options = "";
	var optionNull = "";
	var vAgruComerRef=new VAgruComerRef("I2",null);
	var objJson = $.toJSON(vAgruComerRef);	
	$.ajax({
		type : 'POST',
		url : './detalladoPedido/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			options = "<option value=null>&nbsp;</option>";
			for (i = 0; i < data.length; i++){
				options = options
						+ "<option value='"
						+ data[i].grupo1
						+ "*"
						+ data[i].grupo2
						+ "*"
						+ data[i].flgPropuesta
						+ "'>"
						+ formatDescripcionCombo(data[i].grupo2,
								data[i].descripcion) + "</option>";
			}
			$("select#p70_cmb_seccion").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		

	$("#p70_cmb_seccion").combobox(
			{
		selected: function(event, ui) {
			seccionSel = null;
			categoriaSel = null;
			if ( ui.item.value!="" && ui.item.value!="null" ) {
						var arrAreaSeccion = $("#p70_cmb_seccion").val().split(
								"*");
				if (arrAreaSeccion[1]!=null){
//					$("#p12_cmb_area").combobox('autocomplete',ui.item.label);
//					$("#p12_cmb_area").combobox('comboautocomplete',ui.item.value);

					if (arrAreaSeccion[2]=='X'){	
								$("div#p70_checkBoxIncluirPedido").attr(
										"style", "display:block");
					} else {
						//Ocultamos el checkbox y seleccionamos
								$("div#p70_checkBoxIncluirPedido").attr(
										"style", "display:none");
					}

					load_cmbCategory();

					$("#p70_cmb_categoria").combobox("enable");

				}
			}else{
				$("#p70_cmb_categoria").combobox(null);
						$("select#p70_cmb_categoria").html(
								"<option value=null selected='selected'>" + ''
										+ "</option>");
				$("#p70_cmb_categoria").combobox('autocomplete',"");
						$("#p70_cmb_categoria").combobox('comboautocomplete',
								null);
				$("#p70_cmb_categoria").combobox("disable");
			}

		} ,

		changed: function(event, ui) {
			seccionSel = null;
			categoriaSel = null;
					if (ui.item == null || ui.item.value == ""
							|| ui.item.value == "null") {
				$("#p70_cmb_categoria").combobox(null);
						$("select#p70_cmb_categoria").html(
								"<option value=null selected='selected'>" + ''
										+ "</option>");
				$("#p70_cmb_categoria").combobox('autocomplete',"");
						$("#p70_cmb_categoria").combobox('comboautocomplete',
								null);
				$("#p70_cmb_categoria").combobox("disable");
				//  return result=cleanFilterSelection(RESET_CONST);

			}	 
		}
	}); 
	//$("select#p70_cmb_seccion").combobox('autocomplete',null);
	//$("select#p70_cmb_seccion").combobox('comboautocomplete',null);
}
function formatDescripcionCombo(codigo, descripcion){
	return codigo + "-" + descripcion;
}
function load_cmbCategory(){	
	var options = "";
	var optionNull = "";
	$("#p70_cmb_categoria").combobox(null);
	var arrAreaSeccion = $("#p70_cmb_seccion").val().split("*");	
	var vAgruComerRef=new VAgruComerRef("I3",null,arrAreaSeccion[1]);
	var objJson = $.toJSON(vAgruComerRef);	
	$.ajax({
		type : 'POST',
		url : './detalladoPedido/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			options = "<option value=null>&nbsp;</option>";
			for (i = 0; i < data.length; i++){
				options = options
						+ "<option value='"
						+ data[i].grupo3
						+ "*"
						+ data[i].flgPropuesta
						+ "'>"
						+ formatDescripcionCombo(data[i].grupo3,
								data[i].descripcion) + "</option>";
			}
			$("select#p70_cmb_categoria").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		 
	$("#p70_cmb_categoria").combobox(
			{
		selected: function(event, ui) {
			categoriaSel = null;
			if ( ui.item.value!="" && ui.item.value!="null" ) {
						var arrAreaSeccionCategoria = $("#p70_cmb_categoria")
								.val().split("*");
				if (arrAreaSeccionCategoria[0]!=null){
					if (arrAreaSeccionCategoria[1]=='X'){	
								$("div#p70_checkBoxIncluirPedido").attr(
										"style", "display:block");
					} else {
						//Ocultamos el checkbox y seleccionamos
								$("div#p70_checkBoxIncluirPedido").attr(
										"style", "display:none");
					}
				}
			}

		} ,

		changed: function(event, ui) {
			categoriaSel = null;

		}
	}); 
	$("#p70_cmb_categoria").combobox('autocomplete',optionNull);
	$("#p70_cmb_categoria").combobox('comboautocomplete',null);
}

function resultadoSaveGrid(error, total,totalErrorWS,totalErrorProc){
	var message = null;
	var level = null;

	// Puede que algunos datos del WS se hayan modificado bien pero del
	// Procedimiento no y vicebersa.
	if(totalErrorWS == -1 || totalErrorProc == -1){
		message = saveResultErrorModificar;
		level = "ERROR";			
	}else if (total == 0){
		message = saveResultNoDatos;
		level = "INFO";
	} else if (error == 0) {
		message = saveResultOK;
		level = "INFO";
	} else {
		message = saveResultError;
		message = message.replace('{0}',error);
		level = "ERROR";
	}
	
	if(listaReferenciasSinPLU!= null && listaReferenciasSinPLU.length > 0 && mostrarMensaje){
		createMensajeConfirmacionPLU(message, level);
	}else{
		createAlert(message, level);
	}
}

function createMensajeConfirmacionPLU(message,level){	
	$("#p70dialog-confirm" ).dialog({
		resizable: false,
		height:160,
		width: 360,
		modal: true,
		buttons: [
		          {
		        	  text:btnSiP70,
		        	  click: function() {
		        		  //Finaliza la tarea de la entrada
		        		  $("#p114_popupPluPendiente").dialog("open");		
		        		  $(this).dialog( "close" );
		        	  }
		          },{
		        	  text:btnNoP70,
		        	  click: function() {
		        		  mostrarMensaje=false;
		        		  $(this).dialog( "close" );
		        	  }
		          }
		],
		open: function() {
			$('#p70dialog-confirm .ui-dialog-titlebar-close').on('mousedown', function(){
				 $(this).dialog( "close" );
			});
			$("#p70_lbl_mensajeGuardado").text(message);
			$("#p70_lbl_mensajePlu").text(p70ReferenciasSinPlu);
			
			// mensaje a mostrar ademas del q viene informado -> p70ReferenciasSinPlu
		},
		close:function(){
			 $(this).dialog( "close" );
		}
	});
}

function getTotalFlgPropuesta(seccion,categoria){	

	$.ajax({
		type : 'POST',
		url : './detalladoPedido/getTotalFlgPropuesta.do',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			var totalFlgPropuesta = data.totalFlgPropuesta;

			if (totalFlgPropuesta == 0) {
				//Ocultamos el checkbox y seleccionamos
				$("div#p70_checkBoxIncluirPedido")
						.attr("style", "display:none");

			} else {
				$("div#p70_checkBoxIncluirPedido").attr("style",
						"display:block");
			}

		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		 
}

function getCounter(){	

	$.ajax({
		type : 'POST',
		url : './detalladoPedido/getCounter.do',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			var totalGuardado = data.totalGuardado;
			var totalError = data.totalError;
			var totalErrorWS = data.totalErrorWS;
			var totalErrorProc = data.totalErrorProc;
			resultadoSaveGrid(totalError, totalGuardado, totalErrorWS,
					totalErrorProc);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		 
}

function getPLUsPendientes(seccionAntesActualizar){	
	$.ajax({
		type : 'POST',
		url : './detalladoPedido/getPLUsPendientes.do?seccion='+ seccionAntesActualizar,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {
			if(data != null && data.length > 0){
				listaReferenciasSinPLU = data;
			}else{
				listaReferenciasSinPLU=null;
			}
			getCounter();
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		 
}

function countModificados(){	

	$.ajax({
		type : 'GET',
		url : './detalladoPedido/countModificados.do',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			//Si no existen modificados, ocultamos la alerta.
			if (data == 0){
				//Ocultamos la alerta.
				$("#p70_alerta").hide();

				//Si la bomba no tiene numérico, ocultamos todo.
				if($("#p70_kingBomba").text().trim() == ""){
					$("#p70_textoYKingBomba").hide();
				}

				//Ponemos el flag de modificado en N.
				$("#p70_modificado").val("N");
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		 
}

// Cuando modificamos una fila nos interesa que se muestre la alerta y el
// mensaje de alerta.
function ponerModificado(){		
	// Si no hay recuadro de la bomba, nos interesa mostrar el mensaje pero no
	// la bomba, por lo que
	//ponemos el div del mensaje y la bomba visible pero ocultamos la bomba.
	if(!$("#p70_textoYKingBomba").is(':visible')){
		$("#p70_textoYKingBomba").show();
		$("#p70_kingBomba").hide();
	}
	//Mostramos la alerta siempre.
	if(!$("#p70_alerta").is(':visible')){
		$("#p70_alerta").show();
	}

	//Ponemos el flag de modificado en S.
	$("#p70_modificado").val("S");
}

function events_p70_link_horariosPlataforma(){

	$("#p70_informacionHorarios")
			.click(
					function() {
		var formHorarios = "<form name='horariosform' action='detalladoPedido/downloadHorariosPlataforma.do'  accept-charset='ISO-8859-1' method='get'>";
						formHorarios = formHorarios
								+ "</form><script>document.horariosform.submit();</script>";
		Show_PopupManual(formHorarios);

	});
}

function events_p70_btn_cajasEuros(){
	$("#p70_img_eurosCajas").click(function(){	
		resetResultadosP77();
		loadData_gridP77(gridP77);
	});
	
	// MISUMI-359
	$("#p70_img_cajasVegalsa").click(function(){	
		getDataPopupVegalsa();
	});
	$("#p70_img_eurosVegalsa").click(function(){	
		resetResultadosP77();
		loadData_gridP77(gridP77);
	});

}

function showPopupVegalsa(data){
	var divTable = $('<div class="ui-jqgrid ui-widget ui-widget-content ui-corner-all"></div>');
	var table = $('<table style="border-collapse: collapse; width: 100%; table-layout:fixed;" class="ui-jqgrid-htable;"></table>');
	
	var tHead = $("<thead>");
	var row = $('<tr class="ui-jqgrid-labels"></tr>');
	var thStyleVerySmall='style="text-align: center; width:50px;" class="ui-state-default ui-th-column ui-th-ltr"';
	var thStyleSmall='style="text-align: center; width:65px;" class="ui-state-default ui-th-column ui-th-ltr"';
	var thStyleMed='style="text-align: center; width:78px;" class="ui-state-default ui-th-column ui-th-ltr"';
	var thStyleBig='style="text-align: center; width:84px;" class="ui-state-default ui-th-column ui-th-ltr"';
	var thStyleLittle='style="text-align: center; width:48px;" class="ui-state-default ui-th-column ui-th-ltr"';
	var thStyleVeryLittle='style="text-align: center; width:35px;" class="ui-state-default ui-th-column ui-th-ltr"';
	row.append($('<th title="Mapa" ' + thStyleVeryLittle + '></th>')
			.text("Mapa"));
	row.append($('<th title="Hora de lanzamiento" style="text-align: center; width:45px;" class="ui-state-default ui-th-column ui-th-ltr"></th>')
			.text("H. Lanz"));
	row.append($('<th title="Fecha de reposicion" ' + thStyleLittle + '></th>')
			.text("F. Repo"));
	row.append($('<th title="Jornada" ' + thStyleVerySmall + '></th>').text(
			"Jornada"));
	row.append($('<th title="Umbral de palets" style="text-align: center; width:45px;" class="ui-state-default ui-th-column ui-th-ltr"></th>')
			.text("Umbral"));
	row.append($('<th title="Palets pedidos" ' + thStyleLittle + '></th>').text(
			"Pal Ped"));
	row.append($('<th title="Palets cortados" ' + thStyleLittle + '></th>')
			.text("Pal Cort"));
	row.append($('<th title="Palets adelantados" ' + thStyleLittle + '></th>')
			.text("Pal Adel"));
	row.append($('<th title="FFPP+UFP" ' + thStyleSmall + '></th>').text(
			"FFPP+UFP"));
	// MISUMI-434 FFPP+UFP editado
	row.append($('<th title="FFPP+UFP editado" ' + thStyleBig + '></th>').text(
	"Edit FFPP+UFP"));
	// FIN MISUMI-434
	row.append($('<th title="Cajas pedidas" style="text-align: center; width:37px;" class="ui-state-default ui-th-column ui-th-ltr"></th>')
			.text("Cj Ped"));
	//MISUMI-434 Cj Ped editado
	row.append($('<th title="Cajas pedidas editado" ' + thStyleSmall + '></th>').text(
	"Edit Cj Ped"));
	// FIN MISUMI-434
	row.append($('<th title="Cajas cortadas" style="text-align: center; width:43px;" class="ui-state-default ui-th-column ui-th-ltr"></th>').text(
			"Cj Cort"));
	row.append($('<th title="Cajas adelantadas" style="text-align: center; width:44px;" class="ui-state-default ui-th-column ui-th-ltr"></th>')
			.text("Cj Adel"));
	row.append($(
			'<th title="Fecha del siguiente pedido" ' + thStyleMed + '></th>')
			.text("F. Sig. Pedido"));
	
	tHead.append(row);
	
	table.append(tHead);
	
	var tBody = $("<tbody>");
	$.each(data,function(index,record){
	  var classOdd = ''
	  if (index%2==1){
		  classOdd = 'ui-priority-secondary';
	  }
		row = $('<tr class="ui-widget-content jqgrow ui-row-ltr ' + classOdd
				+ '"></tr>');
	  
	  var tdStyleLeft='style="text-align: left;"';
	  var tdStyleRight='style="text-align: right;"';
	  var tdStyleCenter='style="text-align: center;"';
	  
	  row.append($('<td '+tdStyleRight+'></td>').text(record.codMapa));
		row.append($('<td ' + tdStyleRight + '></td>').text(
				record.horaLanzamiento));
	  
	  var fechaReposicion = parseDateTo_DDD_dd_mm(record.fechaReposicion);
		
	  row.append($('<td '+tdStyleCenter+'></td>').text(fechaReposicion));
		row.append($('<td ' + tdStyleRight + '></td>').text(
				record.jornadaReposicion));
		row.append($('<td ' + tdStyleRight + '></td>')
				.text(record.umbralPalets));
		row.append($('<td ' + tdStyleRight + '></td>').text(
				record.paletsPedidos));
		row.append($('<td ' + tdStyleRight + '></td>').text(
				record.paletsCortados));
		row.append($('<td ' + tdStyleRight + '></td>').text(
				record.paletsAdelantados));
		row.append($('<td '+tdStyleRight+'></td>')
				.text(record.ffppUfp));
		row.append($('<td '+tdStyleRight+'></td>')
				.text(record.ffppUfpEdit));
		row.append($('<td ' + tdStyleRight + '></td>')
				.text(record.cajasPedidas));
		row.append($('<td ' + tdStyleRight + '></td>')
				.text(record.cajasPedidasEdit));
		row.append($('<td ' + tdStyleRight + '></td>').text(
				record.cajasCortadas));
		row.append($('<td ' + tdStyleRight + '></td>').text(
				record.cajasAdelantadas));
	  
	  var fechaSigPedido = parseDateTo_DDD_dd_mm(record.fechaSigPedido);
		  
	  row.append($('<td '+tdStyleCenter+'></td>').text(fechaSigPedido));
	  
	  tBody.append(row);
	});
	table.append(tBody);
	divTable.append(table);
	// Create popup
	$("#resumenVegalsa").empty();
	$("#resumenVegalsa").append(divTable);
	$("#resumenVegalsa").dialog({
		title: "Resumen Propuesta de HOY",
		height:'auto',
		width:'982px',
		modal: true,
		resizable: false
	});
	$(".ui-dialog-title").css("font-weight","bold");
}

function parseDateTo_DDD_dd_mm(fecha){
	var fechaDate = new Date(fecha);
	
	var days = ["D","L","M","M","J","V","S"];
	var dayOfTheWeek = days[fechaDate.getDay()];
	
	var day = fechaDate.getDate();

	var month = fechaDate.toLocaleString('es-es', {
		month : 'short'
	}).toUpperCase();

	return dayOfTheWeek+" "+day+"-"+month;
}

function getDataPopupVegalsa(){
	var codMapa = $('#p70_cmb_mapa').val();
	$
			.ajax({
		type : 'GET',
				url : './detalladoPedido/resumenPropuestaVegalsa.do?codMapa='
						+ codMapa,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	

			showPopupVegalsa(data);

		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		 
}

function events_p70_btn_calcular(){
	$("#p70_btn_calcular")
			.click(
					function() {
		if(validarPrecioCostoFinalFinal()){
			if(validarCheckOfertaSNSeleccionado()){
				if(validarRotacionSeleccionado()){
					p70CalcularGestionEuros();
				}else{
									createAlert(
											replaceSpecialCharacters(p70SeleccionarRotacion),
											"ERROR");
				}
			}else{
								createAlert(
										replaceSpecialCharacters(p70SeleccionarOferta),
										"ERROR");
			}
		}else{
							createAlert(
									replaceSpecialCharacters(p70mismoValorEurosFinales),
									"ERROR");
		}
	});
}

function events_p70_btn_PropuestaInicial(){
	$("#p70_btn_propuestaInicial").click(function(){	

		p70PropuestaInicial();

	});
}

//Si el precioFinal original y el introducido son iguales devuelve false.
function validarPrecioCostoFinalFinal(){
	return !($("#p70_inputEurosFinales").val() == $(
			"#p70_inputEurosFinalesHidden").val());
}

function validarCheckOfertaSNSeleccionado(){
	return $("#p70_chk_enOferta").is(":checked")
			|| $("#p70_chk_sinOferta").is(":checked");
}

function validarRotacionSeleccionado(){
	return $("#p70_chk_altaRotacion").is(":checked")
			|| $("#p70_chk_mediaRotacion").is(":checked")
			|| $("#p70_chk_bajaRotacion").is(":checked");
}

function p70CalcularGestionEuros(){

	var codLoc = $("#centerId").val();
	var respetarImc = $("#p70_chk_imc").is(":checked") ? "S":"N";
	var precioCostoFinal = $("#p70_inputEurosFinalesHidden").val();
	var precioCostoFinalFinal = $("#p70_inputEurosFinales").val();

	var rotacion = $("#p70_chk_altaRotacion").is(":checked")
			&& $("#p70_chk_mediaRotacion").is(":checked")
			&& $("#p70_chk_bajaRotacion").is(":checked") ? "AR*MR*BR" : $(
			"#p70_chk_altaRotacion").is(":checked")
			&& $("#p70_chk_mediaRotacion").is(":checked") ? "AR*MR" : $(
			"#p70_chk_altaRotacion").is(":checked")
			&& $("#p70_chk_bajaRotacion").is(":checked") ? "AR*BR" : $(
			"#p70_chk_mediaRotacion").is(":checked")
			&& $("#p70_chk_bajaRotacion").is(":checked") ? "MR*BR" : $(
			"#p70_chk_altaRotacion").is(":checked") ? "AR" : $(
			"#p70_chk_mediaRotacion").is(":checked") ? "MR" : $(
			"#p70_chk_bajaRotacion").is(":checked") ? "BR" : null;

	// Si flgOferta es null devuelve todos, si es S devuelve los que son de
	// oferta y si es "N" los sin oferta.
	var flgOferta = $("#p70_chk_enOferta").is(":checked")
			&& $("#p70_chk_sinOferta").is(":checked") ? null : ($(
			"#p70_chk_enOferta").is(":checked") ? "S"
			: ($("#p70_chk_sinOferta").is(":checked") ? "N" : null));

	var seccion = '';
	var categoria = '';
	var codArticulo = '';
	if ($("input[name='p70_rad_tipoFiltro']:checked").val() == "1") { // Se
																		// esta
																		// consultando
																		// por
																		// estructura
		if (null != seccionSel){
			seccion = seccionSel;
		} else {
			if ($("#p70_cmb_seccion").val() != "null"
					&& $("#p70_cmb_seccion").val() != null) {
				var arrAreaSeccion = $("#p70_cmb_seccion").val().split("*");
				if ("" != arrAreaSeccion[0] && "null" != arrAreaSeccion[0]){
					area = arrAreaSeccion[0];
				}
				if ("" != arrAreaSeccion[1] && "null" != arrAreaSeccion[1]){
					seccion = arrAreaSeccion[1];
				}
			}
		}
		if (null != categoriaSel){
			categoria = categoriaSel;
		} else {
			if ($("#p70_cmb_categoria").val() != null
					&& $("#p70_cmb_categoria").val() != "null") {
				var arrAreaCategoria = $("#p70_cmb_categoria").val().split("-");
				if ("" != arrAreaCategoria[0] && "null" != arrAreaCategoria[0]){
					categoria = arrAreaCategoria[0];
				}
			}
		}
	} else { //Se esta consultando por referencia
		codArticulo = $("#p70_fld_referencia").val();
	}

	var flgIncluirPropPed;
	//Miramos si el checkbox está seleccionado o no.
	if ($("#p70_chk_IncluirPedido").is(':visible')) { // Si el check es
														// visible, la busqueda
														// dependera si el
														// checkbox está
														// seleccionado o no.
		//Miramos si el checkbox está seleccionado o no.
		flgIncluirPropPed = 'N';
		if($("#p70_chk_IncluirPedido").prop('checked')){
			flgIncluirPropPed = 'S';
		}
	} else { // Si el check NO es visible, la busqueda actuara como si el
				// check estuviese seleccionado.
		flgIncluirPropPed = 'S';
	}

	var detalladoPedido = new DetalladoPedido(seccion, categoria.split("*")[0],
			codArticulo, null, null, codLoc, flgIncluirPropPed, flgOferta,
			respetarImc, precioCostoFinal, precioCostoFinalFinal, rotacion);

	var objJson = $.toJSON(detalladoPedido);	
	$.ajax({
		type : 'POST',
		url : './detalladoPedido/calcularGestionEuros.do',
		cache : false,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : objJson,
		success : function(data) {		
			if(data != null){
				if(data.codError != 0){
					createAlert(replaceSpecialCharacters(p70ErrorCalculo),
							"ERROR");
				}else{

					//$("#p70_btn_buscar").trigger("click");

					// Borramos todas las modificaciones que se hayan podido
					// hacer. Al propuesta inicial debemos volver a estado
					// inicial, perdiendo todos los cambios que no se hayan
					// guardado
					deleteSeleccionadosP75();
					deleteSeleccionadosP76();
					resetDatosP75();
					resetDatosP76();
					loadContadoresDetallado();

				}
			}else{
				createAlert(replaceSpecialCharacters(p77NoDatosFiltros),
						"ERROR");
			}			
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});	
}

function p70PropuestaInicial(){

	var codLoc = $("#centerId").val();
	var respetarImc = $("#p70_chk_imc").is(":checked") ? "S":"N";
	var precioCostoFinal = $("#p70_inputEurosFinalesHidden").val();
	var precioCostoFinalFinal = $("#p70_inputEurosFinales").val();

	var rotacion = $("#p70_chk_altaRotacion").is(":checked")
			&& $("#p70_chk_mediaRotacion").is(":checked")
			&& $("#p70_chk_bajaRotacion").is(":checked") ? "AR*MR*BR" : $(
			"#p70_chk_altaRotacion").is(":checked")
			&& $("#p70_chk_mediaRotacion").is(":checked") ? "AR*MR" : $(
			"#p70_chk_altaRotacion").is(":checked")
			&& $("#p70_chk_bajaRotacion").is(":checked") ? "AR*BR" : $(
			"#p70_chk_mediaRotacion").is(":checked")
			&& $("#p70_chk_bajaRotacion").is(":checked") ? "MR*BR" : null;

	// Si flgOferta es null devuelve todos, si es S devuelve los que son de
	// oferta y si es "N" los sin oferta.
	var flgOferta = $("#p70_chk_enOferta").is(":checked")
			&& $("#p70_chk_sinOferta").is(":checked") ? null : ($(
			"#p70_chk_enOferta").is(":checked") ? "S"
			: ($("#p70_chk_sinOferta").is(":checked") ? "N" : null));

	var seccion = '';
	var categoria = '';
	var codArticulo = '';
	if ($("input[name='p70_rad_tipoFiltro']:checked").val() == "1") { // Se
																		// esta
																		// consultando
																		// por
																		// estructura
		if (null != seccionSel){
			seccion = seccionSel;
		} else {
			if ($("#p70_cmb_seccion").val() != "null"
					&& $("#p70_cmb_seccion").val() != null) {
				var arrAreaSeccion = $("#p70_cmb_seccion").val().split("*");
				if ("" != arrAreaSeccion[0] && "null" != arrAreaSeccion[0]){
					area = arrAreaSeccion[0];
				}
				if ("" != arrAreaSeccion[1] && "null" != arrAreaSeccion[1]){
					seccion = arrAreaSeccion[1];
				}
			}
		}
		if (null != categoriaSel){
			categoria = categoriaSel;
		} else {
			if ($("#p70_cmb_categoria").val() != null
					&& $("#p70_cmb_categoria").val() != "null") {
				var arrAreaCategoria = $("#p70_cmb_categoria").val().split("-");
				if ("" != arrAreaCategoria[0] && "null" != arrAreaCategoria[0]){
					categoria = arrAreaCategoria[0];
				}
			}
		}
	} else { //Se esta consultando por referencia
		codArticulo = $("#p70_fld_referencia").val();
	}

	var flgIncluirPropPed;
	//Miramos si el checkbox está seleccionado o no.
	if ($("#p70_chk_IncluirPedido").is(':visible')) { // Si el check es
														// visible, la busqueda
														// dependera si el
														// checkbox está
														// seleccionado o no.
		//Miramos si el checkbox está seleccionado o no.
		flgIncluirPropPed = 'N';
		if($("#p70_chk_IncluirPedido").prop('checked')){
			flgIncluirPropPed = 'S';
		}
	} else { // Si el check NO es visible, la busqueda actuara como si el
				// check estuviese seleccionado.
		flgIncluirPropPed = 'S';
	}

	var detalladoPedido = new DetalladoPedido(seccion, categoria.split("*")[0],
			codArticulo, null, null, codLoc, flgIncluirPropPed, flgOferta,
			respetarImc, precioCostoFinal, precioCostoFinalFinal, rotacion);

	var objJson = $.toJSON(detalladoPedido);	
	$.ajax({
		type : 'POST',
		url : './detalladoPedido/propuestaInicial.do',
		cache : false,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : objJson,
		success : function(data) {		
			if(data != null){
				if(data.codError != 0){
					createAlert(
							replaceSpecialCharacters(p70ErrorPropuestaInicial),
							"ERROR");
				}else{
					//$("#p70_btn_buscar").trigger("click");
					//reloadGrid('N');
					//finder('N');

					// Borramos todas las modificaciones que se hayan podido
					// hacer. Al propuesta inicial debemos volver a estado
					// inicial, perdiendo todos los cambios que no se hayan
					// guardado
					deleteSeleccionadosP75();
					deleteSeleccionadosP76();
					resetDatosP75();
					resetDatosP76();
					loadContadoresDetallado('N');
				}
			}else{
				createAlert(replaceSpecialCharacters(p70ErrorPropuestaInicial),
						"ERROR");
			}			
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});	
}

//Función que colorea las filas y pone los eventos del popup de ofertas
function colorearFilasEnOfertaYEventoPopUpOferta(fila,tabla){
	var filaOferta = $("#"+(fila+1)+"_flgOferta").val();
	if(filaOferta == "S"){
		$(tabla).find("#" + (fila + 1)).find("td").addClass(
				"p70_columnaResaltadaOferta");
		//event_loadPopUpAyudaP70(fila+1,tabla);
	}else{
		//event_closePopUpAyudaP70(fila+1);
	}
}

function event_loadPopUpAyudaP70(fila,tabla){
	$("#"+fila+"_cantidad").focus(function(event) {
		if(!$("#p56_AreaAyuda").is(':visible')){
			loadPopUpAyudaP70(event,tabla)
		}else{
			if (controlMostradoAyuda(event)){
				$("#p56_AreaAyuda").dialog('close');
				loadPopUpAyudaP70(event,tabla)	
			}
		}
	});
}

function event_closePopUpAyudaP70(fila){
	$("#"+fila+"_cantidad").focus(function(event) {
		if($("#p56_AreaAyuda").is(':visible')){
			$("#p56_AreaAyuda").dialog('close');
		}
	});
}

function controlMostradoAyuda(event){
	// Si la fila para la que se va a tratar la ayuda es diferente a la que se
	// está tratando hasta ahora
	//se forzará para generar una nueva ayuda
	var $focused = $(event.target);
	var id = $focused.attr("id");
	var i = id.indexOf("_");
	var rowId = id.substring(0, i);

	var llamarAyuda = false;
	if ($("#p46_fld_ValidarCant_Selecc").val() == null
			|| $("#p46_fld_ValidarCant_Selecc").val() == '') {
		if (filaAyuda == null || filaAyuda!=rowId){
			filaAyuda = rowId;
			llamarAyuda = true;
		}
	}
	return llamarAyuda;
}

function loadPopUpAyudaP70(event,tabla){
	var $focused = $(event.target);
	var id = $focused.attr("id");
	var i = id.indexOf("_");
	var rowId = id.substring(0, i);
	defineCampoFoco($focused);
	var rowData = jQuery(tabla).getRowData(rowId);

	var codArt = $("#"+rowId+"_codArticulo").val();
	var oferta = ($("#" + rowId + "_oferta").val() == "null" ? null : $(
			"#" + rowId + "_oferta").val());

	var v_Oferta = new VOferta($("#centerId").val(),codArt);
	load_cmbVentasUltimasOfertasP56(v_Oferta,oferta);
	//$focused.focus();
}

function trasformToString(value){
	if(value != null) {
		var stringDouble= value.replace(".","");
		var stringDoubleFinal= stringDouble.replace(",",".");
	} else {
		var stringDoubleFinal = null;
	}

	return stringDoubleFinal;
}