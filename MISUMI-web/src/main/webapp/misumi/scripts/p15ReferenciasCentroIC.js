/******************************************************
 ********************* CONSTANTES *********************
 ******************************************************/

//Inicio variables misumi-124
var esPlanogramaRef = 1;
var esSfmRef = 2;
var esFacCapRef = 3;
var esCapRef = 4;
var esFacNoAliRef  = 5;

var esCajaExpositoraRef = 1;
var esMadreRef = 2;
var esFFPPRef = 3;

var esPlanogramadoRef = "PLANOGRAMADO";

//Variable global que guarda el objeto con los datos de consultarImc. Es necesario guardarlo en una variable global, 
//porque varios de los datos de este objeto, sirven para simularImc y modificarImc.
var imcOriginal;

// Contiene los valores que llegan desde el servidor.
var facingVegalsaOri;

//Fin variables misumi-124

var descripcionPlanogramaSimulado=null;
var KG=null;
var bandejas=null;
var mensajeImpSFM=null;
var mensajeCapSFM=null;
var mensajeFacing=null;
var mensajeFacingCapacidad = null;
var tituloVentasMedias=null;
var tituloVentasMediasMP=null;
var tituloVentasMediasP=null;

/********************* 55119 NUEVO ********************/
var listaMesesAnioPeriodo;
var elementoComboSeleccionado;
var idxMes;
var idxAnio;
var anioActual

var pagAnt = 0;
var pagSig = 0;
/******************************************************/

var planogramado;
var calculado;

//Mensaje de error si no introduces capacidad o facing.
var capFacVacio;

//Mensaje de error si no cambias fac y cap.
var facCapOrig;

//Mensajes al actualizar el planograma si eres técnico.
var noHaPodidoModificarsePlanograma;
var valoresPlanogramaOriginalesCambiados;
var planogramaModificadoCorrectamente;
var preguntaGuardar;
//Para mostrar en el título del desglose del facing.
var tituloDesgloseFacingFFPP;
var tituloDesgloseFacingNoFFPP;
var tituloDesgloseFacingNoFFPPCajas;

var desgloseFacingTitle;

var guardadoOk;
var guardadoError;

$(document).ready(function(){
	$(document).on('CargadoCentro', function(e) { 
		loadP15(locale);
		initializeScreenIC();
	});
	
});

function esCentroVegalsa(){
	var centerSoc = $('#centerSoc').val();
	return centerSoc == 13;
}

function esHipermercado(){
	var tipoNegocio = $('#centerTipoNegocio').val();
	return tipoNegocio == 'H';
}

function comprobarVegalsa(){
	var codCentro = $("#centerId").val();
	var codArt = $("#p13_fld_referenciaEroski").val();
//	console.log("comprobarVegalsa - codCentro = "+codCentro+" - codArt = "+codArt);
	$.ajax({
		type : 'GET',
		url : './referenciasCentro/vieneDeSIA.do?codCentro='+codCentro+"&codArt="+codArt,
		cache : false,
		success : function(data) {	
			var vieneDeSIA = data;
			var vegalsa = esCentroVegalsa();
			var hipermercado = esHipermercado();
//			console.log("comprobarVegalsa - vieneDeSIA = "+vieneDeSIA);
//			console.log("comprobarVegalsa - esCentroVegalsa = "+vegalsa);
//			console.log("comprobarVegalsa - esHipermercado = "+hipermercado);
			if ( (vegalsa && !hipermercado) || (vegalsa && hipermercado && !vieneDeSIA) ){
				showVegalsa();
			}else{
				hideVegalsa();
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});	
}

function showVegalsa(){
	$('#p15_vegalsa_div').attr("style", "display:inline-block");
	$('#p15_vegalsa_extra_div').attr("style", "display:inline-block");
	
	$('.campoNoVegalsa').attr("style", "display:none");
}

function hideVegalsa(){
	$('#p15_vegalsa_div').attr("style", "display:none");
	$('#p15_vegalsa_extra_div').attr("style", "display:none");

	$('.campoNoVegalsa').attr("style", "display:inline-block");
}

function initializeScreenIC(){
	$("#p15_cmb_mesAnio").combobox(null);
	$("#p15_fld_descripcionPlanograma").attr("disabled", "disabled");
	$("#p15_fld_tipoPlanograma").attr("disabled", "disabled");
	$("#p15_fld_linealFacingFacingX").attr("disabled","disabled");
	$("#p15_fld_linealCapacidadFacingX").attr("disabled","disabled");
	$("#p15_fld_linealCapacidad").attr("disabled", "disabled");
	$("#p15_fld_linealFecha").attr("disabled", "disabled");
	$("#p15_fld_linealFacing").attr("disabled", "disabled");
	$("#p15_fld_linealMultiplicador").attr("disabled", "disabled");
	$("#p15_fld_linealImc").attr("disabled", "disabled");

	$("#p15_fld_linealCapacidadFacingX").attr("disabled", "disabled");
	$("#p15_fld_linealFechaFacingX").attr("disabled", "disabled");
	$("#p15_fld_linealFacingFacingX").attr("disabled", "disabled");
	$("#p15_fld_linealMultiplicadorFacingX").attr("disabled", "disabled");
	$("#p15_fld_linealImcFacingX").attr("disabled", "disabled");

	$("#p15_fld_linealFacingPuro").attr("disabled", "disabled");
	$("#p15_fld_linealFacingPrevio").attr("disabled", "disabled");
	$("#p15_fld_linealMultiplicadorFacingPuro").attr("disabled", "disabled");
	$("#p15_fld_linealImcFacingPuro").attr("disabled", "disabled");
	$("#p15_fld_linealFechaBloque3").attr("disabled", "disabled");
	$("#p15_fld_montaje1Capacidad").attr("disabled", "disabled");
	$("#p15_fld_montaje1Facing").attr("disabled", "disabled");
	$("#p15_chk_montaje1Cabecera").attr("disabled", "disabled");
	$("#p15_chk_montaje1Oferta").attr("disabled", "disabled");
	$("#p15_chk_montaje1Campana").attr("disabled", "disabled");
	$("#p15_fld_montaje2Capacidad").attr("disabled", "disabled");
	$("#p15_fld_montaje2Facing").attr("disabled", "disabled");
	$("#p15_chk_montaje2Cabecera").attr("disabled", "disabled");
	$("#p15_chk_montaje2Oferta").attr("disabled", "disabled");
	$("#p15_chk_montaje2Campana").attr("disabled", "disabled");
	$("#p15_fld_linealFecha").attr("disabled", "disabled");
	$("#p15_fld_multiplicadorFacing").attr("disabled", "disabled");
	
	//MISUMI-428
	$("#p15_input_montajeCapacidadCentro").attr("disabled", "disabled");
	$("#p15_input_montajeFechaInicioCentro").attr("disabled", "disabled");
	$("#p15_input_montajeFechaFinCentro").attr("disabled", "disabled");
	$("#p15_input_montajeOfertaCentro").attr("disabled", "disabled");

	//Eventos
	//event_btn_modificar();
	//event_changeFacingIMC();
	//event_changeCapacidad();

	//Regex de campos
	$("#p15_fld_linealCapacidadFacingX").filter_input({regex:'[0-9]'});
	$("#p15_fld_linealFacingFacingX").filter_input({regex:'[0-9]'});
}

/********************* 55119 NUEVO ********************/

function load_cmbMes(locale){
	//Se crean las variables para guardar los elementos html del combobox.
	var options = "";

	$.ajax({
		type : 'POST',
		url : './referenciasCentro/loadMesesAnioPeriodo.do?locale='+locale,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			//Rellena el combo de los meses
			for (i = 0; i < data.length; i++){	   
				options = options + "<option value='"+i+"'>"+data[i].mes+" - "+data[i].anio+"</option>"; 
			}

			//Se inseta el c�digo html en su correspondiente componente combobox jquery.
			$("select#p15_cmb_mesAnio").html(options);

			//Elige el primer elemento del combobox del mes y lo selecciona.
			var primerElemento = 0;
			var primerElementoCombo = data[primerElemento].mes+" - "+data[primerElemento].anio;
			$("#p15_cmb_mesAnio").combobox('autocomplete',primerElementoCombo);

			//Indica que el mes seleccionado es el que se encuentra en la posici�n 0.
			idxMes = 0;

			//Guardar en variable global LISTA con meses, a�os y periodos
			listaMesesAnioPeriodo = data;

			load_diasPeriodoCalendario(0);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});	
	//Ver que elemento del combobox se ha seleccionado y completar los a�os del combo
	$("#p15_cmb_mesAnio").combobox({
		selected: function(event, ui) {
			if ( ui.item.value!="" && ui.item.value!="null") {
				//Conseguir el valor del combo seleccionado.
				var comboValue = $("#p15_cmb_mesAnio").val();
				if (comboValue !=null){
					//Cargar los a�os relacionados con ese combobox
					load_diasPeriodoCalendario(comboValue);	
				}
			}  
		}  
	});
}

function load_diasPeriodoCalendario(elementoCombo){
	//Indica que el a�o y mes seleccionado es el que se encuentra en la posici�n X.
	elementoComboSeleccionado = listaMesesAnioPeriodo[elementoCombo];

	//Calcula los periodos del mes
	var periodoIni = elementoComboSeleccionado.periodoIni;
	var periodoFin = elementoComboSeleccionado.periodoFin;

	var diaDesde = parseInt(periodoIni.substring(0, 2), 10);
	var mesDesde= parseInt(periodoIni.substring(2, 4), 10);
	var anyoDesde = parseInt(periodoIni.substring(4), 10);

	var diaHasta = parseInt(periodoFin.substring(0, 2), 10);
	var mesHasta= parseInt(periodoFin.substring(2, 4), 10);
	var anyoHasta = parseInt(periodoFin.substring(4), 10);

	//Consigue la lista de d�as del mes
	refArticulo = $("#p13_fld_referencia").val();
	var v_Oferta = new VOferta($("#centerId").val(),refArticulo,'','','',new Date(anyoDesde,mesDesde-1,diaDesde),new Date(anyoHasta,mesHasta-1,diaHasta),'');

	//Si es suma anticipada
	sumVentaAnticipada=1;

	var objJson = $.toJSON(v_Oferta);

	$.ajax({
		type : 'POST',
		url : './referenciasCentro/loadDiasPeriodo.do?sumVentaAnticipada='+sumVentaAnticipada,
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			if(data.listaDiaVentasUltimasOfertas.length > 0 ){
				//Desliga posibles eventos de clickado de d�as
				eliminarEventosP15();

				//Dibuja el calendario
				dibujarCalendario(data.listaDiaVentasUltimasOfertas, 6,7, "#p15_ventasUltimasOfertasDiaTd", "#p15_ventasUltimasOfertasCantidadMesTd","#p15_ventasUltimasOfertasCantidadMesFechaTd",null,null,null,null);
			}else{
				//Recalcula los colores de las flechas de al lado de los combos
				//recalcularFlechasAdelanteAtras();

				//Limpiar el calendario
				limpiarCalendario(6,7, "#p15_ventasUltimasOfertasDiaTd", "#p15_ventasUltimasOfertasCantidadMesTd","#p15_ventasUltimasOfertasCantidadMesFechaTd");
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		
}

function eliminarEventosP15(){
	//Desasigno los eventos que se hab�an puesto en los objetos
	$(".hayVentasUltimasOfertas").unbind('click');
}
/********************* ANTERIOR A 55119 ********************/

function loadP15(locale){

	this.i18nJSON = './misumi/resources/p15ReferenciasCentroIC/p15referenciasCentroIC_' + locale + '.json';

	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		descripcionPlanogramaSimulado= data.descripcionPlanogramaSimulado;
		KG= data.KG;
		bandejas= data.bandejas;
		mensajeImpSFM=data.mensajeImpSFM;
		mensajeCapSFM=data.mensajeCapSFM;
		mensajeFacing=data.mensajeFacing;
		mensajeFacingCapacidad = data.mensajeFacingCapacidad;
		tituloVentasMedias=data.tituloVentasMedias;
		tituloVentasMediasMP=data.tituloVentasMediasMP;
		tituloVentasMediasP=data.tituloVentasMediasP;
		tituloDesgloseFacingFFPP = data.tituloDesgloseFacingFFPP;
		tituloDesgloseFacingNoFFPP = data.tituloDesgloseFacingNoFFPP;
		tituloDesgloseFacingNoFFPPCajas = data.tituloDesgloseFacingNoFFPPCajas;
		planogramado = data.planogramado;
		calculado = data.calculado;
		desgloseFacingTitle = data.desgloseFacingTitle;
		capFacVacio = data.capFacVacio;
		facCapOrig = data.facCapOrig;
		noHaPodidoModificarsePlanograma = data.noHaPodidoModificarsePlanograma;
		valoresPlanogramaOriginalesCambiados = data.valoresPlanogramaOriginalesCambiados;
		planogramaModificadoCorrectamente = data.planogramaModificadoCorrectamente;
		preguntaGuardar = data.preguntaGuardar;
		guardadoOk = data.guardadoOk;
		guardadoError = data.guardadoError;
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

function reloadImagenComercial() {
	comprobarVegalsa();

	$("#p13_pestanaImagenComercialCargada").val("S");
	$("#p15_AreaStockFinalMinimo").attr("style", "display:none");
	$("#p15_AreaPlanogramas").attr("style", "display:none");
	//loadImagenComercial();
	loadImagenComercialNuevo();
}


function loadPlanogramasWS(){
	//loadWS
	var vReferenciasCentro=new ReferenciasCentro($("#p13_fld_referenciaEroski").val(), $("#centerId").val());
	var objJson = $.toJSON(vReferenciasCentro);	


	$.ajax({
		type : 'POST',
		url : './referenciasCentro/loadPlanogramaWS.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		cahe:false,
		success : function(data) {			

			$("#p15_fld_descripcionPlanograma").val(data.descripcionPlanograma);
			var campo = null;
			if ($('#p15_AreaPlanogramas').is(':visible')){
				campo = $("#p15_ParrafoImplantacion");
			} else {
				campo = $("#p15_ParrafoImplantacionFac");
			}

			if(data.colorImagenComercial == "ROJO") {
				campo.text(data.implantacion);
				campo.addClass( "p15_ParrafoImplantacionRojo" );
				$(".p15_EstructuraLinealBloque1").attr("style", "display:inline");

			} else {
				if(data.colorImagenComercial == "VERDE") {
					campo.text(data.implantacion);
					campo.addClass( "p15_ParrafoImplantacionVerde" );
					$(".p15_EstructuraLinealBloque1").attr("style", "display:inline");
				} else {
					campo.text(data.implantacion);
					campo.addClass( "p15_ParrafoImplantacionNormal" );
					$(".p15_EstructuraLinealBloque1").attr("style", "display:none");
				}
			} 
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		
}
function loadImagenComercial(){
	var vReferenciasCentro=new ReferenciasCentro($("#p13_fld_referenciaEroski").val(), $("#centerId").val());
	var objJson = $.toJSON(vReferenciasCentro);	
	$.ajax({
		type : 'POST',
		url : './referenciasCentro/loadDatosImagenComercial.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			if (validateReloadingIC(data)){

				//Actualización de los datos de "Ventas media"
				loadImagenComercialVentasMedias();

				//ActualizaciÃ³n de los datos de "Ventas Ãºltimo mes"
				//loadImagenComercialVentasUltMes();

				//Carga el combobox del mes con idioma español. Locale es variable global.
				load_cmbMes(locale);

				if (data.flgFacingCapacidad == "S"){ //Referencia de Facing-Capacidad

					loadImagenComercialPlanogramas(data.flgFacing,data.flgCapacidad,data.flgFacingCapacidad);
				} else {	
					if (data.flgFacing == "S"){ // Referencia de Facing Puro
						loadImagenComercialPlanogramas(data.flgFacing,data.flgCapacidad,data.flgFacingCapacidad);
					} else { 
						if (data.flgStockFinal=="1"){ //Referencia de SFM
							//Actualización de los datos de "Stock final mínimo"
							loadImagenComercialSfm();
							loadPlanogramasWS();
						}
						if (data.flgStockFinal!="1"){ //Referencia de Capacidad
							//Actualización de los datos de "Planogramas"
							loadImagenComercialPlanogramas(data.flgFacing,data.flgCapacidad,data.flgFacingCapacidad);
						}
					}
				}

			}else{
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

function loadImagenComercialNuevo(){
	console.log("loadImagenComercialNuevo");
	var vReferenciasCentro=new ReferenciasCentro($("#p13_fld_referenciaEroski").val(), $("#centerId").val());
	var objJson = $.toJSON(vReferenciasCentro);	
	$.ajax({
		type : 'POST',
		url : './referenciasCentro/loadDatosImagenComercialNuevo.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			if (validateReloadingIC(data)){
				//Actualización de los datos de "Ventas Medias"
				loadImagenComercialVentasMedias();

				//Carga el combobox del mes con idioma español. Locale es variable global.
				load_cmbMes(locale);

				/* Obtenemos el método de la referencia.
					Puede ser:
				 		o PLANOGRAMA
						o	2-SFM
						o	3-FAC-CAP
						o	4-CAP
						o	5-FAC NO ALI */
				var metodo = data.metodo;

				//Referencia de SFM
				if (data.tratamientoVegalsa == 1 || metodo != esSfmRef){
					//Muestra el bloque de planogramas, que encapsula los bloques de FACING PURO NO ALI Y FACING CAPACIDAD o CAPACIDAD
					mostrarAreaPlanogramas(data,metodo);
				}else{ 
					//Muestra el bloque del SFM
					mostrarAreaSFM(data);
				}	
			}else{
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

//Obtenemos el UC
function loadUnidadesCaja(){
	var vReferenciasCentro=new ReferenciasCentro($("#p13_fld_referenciaEroski").val(), $("#centerId").val());
	var objJson = $.toJSON(vReferenciasCentro);	
	$.ajax({
		type : 'POST',
		url : './referenciasCentro/loadUnidadesCaja.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			//Insertamos el unidades caja en el hidden.
			if(data != null){
				$("#p15_uc").val(data.uniCajaServ);
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		
}

//Muestra el bloque del SFM
function mostrarAreaSFM(data){
	//ActualizaciÃ³n de los datos de "Stock final mÃ­nimo"
	//Mostramos el area de stock final minimo y ocultamos el de facing y planogramas.
	$("#p15_AreaStockFinalMinimo").attr("style", "display:inline-block");
	$("#p15_AreaPlanogramas").attr("style", "display:none");

	//Si existe SFM y diasDeVenta los mostramos.
	var sfmAMostrar = data.sfm;
	var diasVentaAMostrar = data.diasDeVenta;
	if (sfmAMostrar != null && diasVentaAMostrar != null){
		$("#p15_stockFinalMinimoUnidadesTdS").text(sfmAMostrar).formatNumber();
		$("#p15_stockFinalMinimoDiasVentaTdS").text(diasVentaAMostrar).formatNumber();

		//Si existe un aviso lo mostramos.
		var avisoAMostrar = data.avisoCambio;
		if (avisoAMostrar != null){									
			$("#p15_stockFinalMinimoMensajeTexto2").text(avisoAMostrar);
			$("#p15_stockFinalMinimoMensaje").attr("style", "display:inline-block");
		}else{
			$("#p15_stockFinalMinimoMensaje").attr("style", "display:none;");
		}
	}
	//Mostramos la información de los planogramas obtenidas del WS, descripción e implantación.
	loadPlanogramasWS();
}

//Muestra el bloque de planogramas según si es facing puro no ali, facing capacidad o capacidad
function mostrarAreaPlanogramas(data,metodo){
	//Mostramos el area de planogramas y facing y ocultamos el area de stock final minimo.
	$("#p15_AreaStockFinalMinimo").attr("style", "display:none");
	$("#p15_AreaPlanogramas").attr("style", "display:inline-block");
	$("#p15_referenciasCentroImagenComercialMensajeError").css("display", "none");
	
	//Este bloque calcula los montajes
	mostrarBloquePlanogramasMontajes(metodo);
	
	if (data.tratamientoVegalsa == 1){
		mostrarBloqueFacingVegalsa(data);
	}else{
		//Este bloque es común para fac, faccap y cap
		mostrarBloquePlanogramasDescripcion(data);

		//Este bloque varía según si es (fac) o (faccap y cap)
		mostrarBloquePlanogramasLineal(data,metodo);

		//Este bloque es común para fac, faccap y cap
		mostrarBloquePlanogramasAviso(data);
	}
	
	if (data.tratamientoVegalsa == 1){
		$("#p15_guardarCambiosFacingVegalsaDiv").attr("style", "display:inline-block;vertical-align:top");
	}else{
		$("#p15_guardarCambiosFacingVegalsaDiv").attr("style", "display:none");
	}
	
}

// Muestra los datos para el caso de Facing Vegalsa.
function mostrarBloqueFacingVegalsa(data){

	//Mostramos la información de los planogramas obtenidas del WS, descripción e implantación.
	loadPlanogramasWS();
	
	facingVegalsaOri = data;
	
	// Si no se ha producido ningún error del WS.
	if (facingVegalsaOri.flgErrorWSFacingVegalsa == 0){
		$("#p15_referenciasCentroImagenComercialMensajeError").css("display", "none");

		//Mostramos el bloque que muestra la capacidad y el facing con el multiplicador y el imc.
		$(".p15_EstructuraLinealBloque2FacingX").attr("style", "display:inline-block");

		//Ocultamos el bloque que muestra IMC(Imagen Comercial)
		$("#p15_lbl_linealImcFacingXDiv").attr("style", "display:none");
		$("#p15_fld_linealImcFacingXDiv").attr("style", "display:none");
		
		//Ocultamos el bloque que muestra la capacidad y el facing
		$(".p15_EstructuraLinealBloque2").attr("style", "display:none");

		//Ocultamos el bloque del facing, multiplicador, imc. Luego habrá que mirar si se muestra el multiplicador o no.
		$(".p15_EstructuraLinealBloque3").attr("style", "display:none;");

		//Si como en este caso planogramaVigente es distinto de nulo y es una referencia de
		//capacidad o de facing-capacidad, significa que es de facingX.
		if (data.capacidad != null){
			$("#p15_fld_linealCapacidadFacingX").val(data.capacidad).formatNumber();
			$("#p15_capacidadOrig").val(data.capacidad).formatNumber();
		}

		//Insertamos el facing
		if (data.facing != null){
			$("#p15_fld_linealFacingFacingX").val(data.facing).formatNumber();
			$("#p15_facingOrig").val(data.facing).formatNumber();
		}
		
		// Si el centro tiene permiso para modificar el facing
		//	--> Habilitar el campo para poder modificar el valor.
		if (data.facingModificable == 1){
			
	        //Habilitar el campo "facing"
	        $("#p15_fld_linealFacingFacingX").prop("disabled", false);
	        
	        // Si se trata de un centro VEGALSA el número de dígitos de FACING será 2
	        // , sin embargo, para un centro EROSKI el tamaño será de 3 que ya está indicado
	        // en el INPUT correspondiente del JSP.
	        $("#p15_fld_linealFacingFacingX").attr('maxlength','2');

	        //Mostrar el botón para guardar.
	        // NOTA: Se ha pasado a gestionar en el JSP
//	        $("#p15_guardarCambiosFacingVegalsa").attr("style", "display:inline-block");
	        
			$("#p15_EstructuraLinealBloqueFacingVegalsa").show();

		}else{
			
	        //Deshabilitar el campo "facing"
	        $("#p15_fld_linealFacingFacingX").prop("disabled", true);

	        //Ocultar el botón para guardar.
	        // NOTA: Se ha pasado a gestionar en el JSP
//	        $("#p15_guardarCambiosFacingVegalsa").attr("style", "display:none");

			$("#p15_EstructuraLinealBloqueFacingVegalsa").hide();
			
		}

		$("#p15_lbl_linealMultiplicadorFacingX").text('Fondo');
		
		//Insertamos el multiplicador.
		if (data.multiplicador != null){
			$("#p15_fld_linealMultiplicadorFacingX").val(data.multiplicador);
		}

		asignarEventosFacingVegalsa()
		
	// Si ERROR del WS --> Mostrar mensaje de error en pantalla.
	}else{
		$("#p15_referenciasCentroImagenComercialMensajeError").css("display", "inline-block");
	}
	
	// Ocultar el mensaje de guardado.
	$("#p15_lbl_guardadoVegalsa").css("visibility","hidden");

}

//Muestra el bloque de la descripción que contiene el planograma y la descripción.
function mostrarBloquePlanogramasDescripcion(data){
	//Se rellena el texto de la descripción y la implantación
	loadPlanogramasWS();

	if(data.tipoPlano != null){
		//Si es planogramado, se muestra el texto planogramado y si no, calculado. Este texto se muestra al lado de la descripción obtenida mediante el WS.
		if(data.tipoPlano.toLowerCase() != 'planogramado'){
			$("#p15_fld_tipoPlanograma").val(calculado);
		}else{
			$("#p15_fld_tipoPlanograma").val(planogramado);
		}
	}
}

function mostrarBloquePlanogramasLineal(data,metodo){
	//Ocultamos el bloque del dibujo, por si no es necesario mostrarlo.
	$("#p15_EstructuraLinealBloqueDesgloseFacing").hide();

	//Si es una referencia de facing puro No Ali
	if (metodo == esFacNoAliRef){ 
		//Ocultamos el bloque que muestra la capacidad y el facing
		$(".p15_EstructuraLinealBloque2").attr("style", "display:none;");

		//Ocultamos el bloque que muestra la capacidad y el facing con el multiplicador y el imc.
		$(".p15_EstructuraLinealBloque2FacingX").attr("style", "display:none;");

		//Ocultamos el bloque que muestra los montajes y que tiene capacidad como campo.
		$(".p15_planogramasEstructuraMontaje2Field").attr("style", "display:none;");

		//Mostramos el bloque del facing, multoplicador, imc. Luego habrá que mirar si se muestra el multiplicador o no.
		$(".p15_EstructuraLinealBloque3").attr("style", "display:inline-block");

		//Pintamos el facing
		if (data.facing != null){
			//Si facing es >0 no mostramos el facing previo.
			/*if(data.facing > 0){
				$("#p15_EstructuraLinealFacingPrevio").hide();
			}else{
				$("#p15_EstructuraLinealFacingPrevio").show();
			}*/
			$("#p15_fld_linealFacingPuro").val(data.facing).formatNumber();
		}/*else{
			$("#p15_EstructuraLinealFacingPrevio").hide();
		}*/

		//Si la referencia no es FFPP, se enseña el multiplicador.
		if(data.tipoReferencia != esFFPPRef){
			//Si existe el multiplicador (que debería)
			if (data.multiplicador != null){
				$("#p15_fld_linealMultiplicadorFacingPuro").val(data.multiplicador);

				//Quitamos la clase que centra la caja del facing con la de imagen comercial para que salgan en paralelo
				//la caja del facing y la del multiplicador.
				$("#p15_EstructuraLinealBloque3_1FacingPuro").removeClass("p15_correccion_FFPP_multiplicador");

				//Mostramos la llave y la caja del multiplicador.
				$("#p15_linealMultiplicadorFacingPuroDiv").attr("style", "display:inline-block");
				$("#p15_linealImagenLLaveFacingPuroDiv").attr("style", "display:inline-block");
			}
		}else{
			//Si la referencia es FFPP, no queremos multiplicador por lo que centramos la caja del facing con 
			//la del imc mediante css
			$("#p15_EstructuraLinealBloque3_1FacingPuro").addClass("p15_correccion_FFPP_multiplicador");

			//Ocultamos la llave y el multiplicador para que el facing y el imc se vean en la misma línea.
			$("#p15_linealMultiplicadorFacingPuroDiv").attr("style", "display:none");
			$("#p15_linealImagenLLaveFacingPuroDiv").attr("style", "display:none");
		}

		//Mostramos el imc
		if (data.imc != null){
			$("#p15_fld_linealImcFacingPuro").val(data.imc);
		}

		/*
		 * OCULTAR POR AHORA EN MISUMI-124 PORQUE NO ALCARAN QUE VA A PASAR CON EL FACING PREVIO NO LA FECHAGENLINEAL
		 * 
		 * //???????????????
			if (data.stockFinalMinimo.facingPrevio != null){
				$("#p15_fld_linealFacingPrevio").val(data.stockFinalMinimo.facingPrevio).formatNumber();
			}
			//???????????????
			if (data.planogramaVigente.fechaGenLinealPantalla != null){
				$("#p15_fld_linealFechaBloque3").val(data.planogramaVigente.fechaGenLinealPantalla);
			}

			//Petición 54823. En las referencias de FAcing solo mostrar el facing Previo si el facing esta a 0
			if (data.planogramaVigente.stockMinComerLineal != '0') {
				$("#p15_fld_linealFacingPrevio").hide();
				$("#p15_lbl_linealFacingPrevio").hide();		
			} else {
				$("#p15_fld_linealFacingPrevio").show();
				$("#p15_lbl_linealFacingPrevio").show();
			}*/
	}
	//Si es una referencia de cap, faccap o planogramada
	else if(metodo == esFacCapRef || metodo == esCapRef || metodo == esPlanogramaRef){
		//Mostramos el bloque que muestra la capacidad y el facing con el multiplicador y el imc.
		$(".p15_EstructuraLinealBloque2FacingX").attr("style", "display:inline-block");
		
		//Mostrar el bloque que muestra IMC(Imagen Comercial)
		$("#p15_lbl_linealImcFacingXDiv").attr("style", "display:inline-block");
		$("#p15_fld_linealImcFacingXDiv").attr("style", "display:inline-block");

		//Ocultamos el bloque que muestra la capacidad y el facing
		$(".p15_EstructuraLinealBloque2").attr("style", "display:none");

		//Ocultamos el bloque del facing, multiplicador, imc. Luego habrá que mirar si se muestra el multiplicador o no.
		$(".p15_EstructuraLinealBloque3").attr("style", "display:none;");

		//Si como en este caso planogramaVigente es distinto de nulo y es una referencia de
		//capacidad o de facing-capacidad, significa que es de facingX.
		if (data.capacidad != null){
			$("#p15_fld_linealCapacidadFacingX").val(data.capacidad).formatNumber();
			$("#p15_capacidadOrig").val(data.capacidad).formatNumber();
		}

		//Insertamos el facing
		if (data.facing != null){
			$("#p15_fld_linealFacingFacingX").val(data.facing).formatNumber();
			$("#p15_facingOrig").val(data.facing).formatNumber();
		}

		/*//????????????????????????????? No sé de donde se saca
		if (data.planogramaVigente.fechaGenLinealPantalla != null){
			$("#p15_fld_linealFechaFacingX").val(data.planogramaVigente.fechaGenLinealPantalla);
		}*/

		//Dependiendo de si es un centro VEGALSA será etiqueta "Multiplicador" o "Fondo".
		if (data.tratamientoVegalsa == 1){
			$("#p15_lbl_linealMultiplicadorFacingX").text('Fondo');
		} else {
			$("#p15_lbl_linealMultiplicadorFacingX").text('Multiplicador');
		}
		
		
		//Insertamos el multiplicador.
		if (data.multiplicador != null){
			$("#p15_fld_linealMultiplicadorFacingX").val(data.multiplicador);
		}

		//Insertamos el Imc.
		if (data.imc != null){
			$("#p15_fld_linealImcFacingX").val(data.imc);
		}

		//Control de multiplicador para FFPP
		if (data.tipoReferencia == esFFPPRef){
			//Se alinean el facing y la capacidad con las fechas mediante esta clase
			$("#p15_EstructuraLinealBloque2_1FacingX").addClass("p15_correccion_FFPP_multiplicador");

			//Se oculta el multiplciador
			$("#p15_EstructuraLinealBloque2_1_2FacingX").attr("style", "display:none");

			//Se oculta la llave
			$("#p15_linealImagenLLaveFacingXDiv").attr("style", "display:none");

			//Se quita la clase para que la fecha se vea alineada con facing y capacidad porque ahora no la necesitamos
			$("#p15_tb_fechaFacingX").removeClass("p15_tb_fechaFacingXCajasExpositoras");
		}else{
			//Si la referencia planogramada es de tipo P (o sea planogramada)
			//if(data.tipoPlano == esPlanogramadoRef){		
			//Quitamos la clase que centra la caja del facing con la de imagen comercial para que salgan en paralelo
			//la caja del facing y la del multiplicador.
			$("#p15_EstructuraLinealBloque2_1FacingX").removeClass("p15_correccion_FFPP_multiplicador");

			//Se muestra el multiplciador
			$("#p15_EstructuraLinealBloque2_1_2FacingX").attr("style", "display:inline-block");

			//Se mujestra la llave
			$("#p15_linealImagenLLaveFacingXDiv").attr("style", "display:inline-block");

			//Se quita la clase para que la fecha se vea alineada con facing y capacidad porque ahora no la necesitamos
			$("#p15_tb_fechaFacingX").removeClass("p15_tb_fechaFacingXCajasExpositoras");
			/*}else{
				//Se muestra el multiplciador
				$("#p15_EstructuraLinealBloque2_1_2FacingX").attr("style", "display:inline-block");

				//Se mujestra la llave
				$("#p15_linealImagenLLaveFacingXDiv").attr("style", "display:inline-block");
			}*/
		}

		//Si el metodo es planograma, se muestra el dibujo del líneal. 
		if(metodo == esPlanogramaRef){
			mostrarImplantacion(data);
		}
	}
}

function mostrarBloquePlanogramasAviso(data){
	//Mostramos u ocultamos el aviso de este area
	var avisoAMostrar = data.avisoCambio;
	if(avisoAMostrar != null){
		$("#p15_planogramasEstructuraLinealMensajeTexto2").text(avisoAMostrar);
		$("#p15_planogramasEstructuraLinealMensaje").attr("style", "display:inline-block");
	}else{
		$("#p15_planogramasEstructuraLinealMensaje").attr("style", "display:none;");
	}
}

function asignarEventosAreaPlanogramas(){
	//Eventos para la simulación
	eventKeyUpFacingAlto();
	eventKeyUpFacingAncho();
	eventKeyUpMultiplicador();
	eventKeyUpFacingX();
	eventKeyUpCapacidadFacingX();

	//Focusout capacidadfacing x. La capacidad se calcula al hacer focusout del campo
	//de capacidad y no en el keyup porque si no, al cambiar la capacidad y compararla
	//con el imc, siempre te pondría el valor del imc y no dejaría cambiar el campo.
	//Ejemplo. Tengo 50 en capacidad, en imc 24. Para cambiar la capacidad tengo que borrar
	//primero el 0 y luego el 5 de 50. Al borrar el 0, recalcula la capacidad 5<24 entonces te
	//pone 24 en capacidad, ¡pero tu la querías cambiar! Al intentar cambiar el 24, tienes que
	//borrar el 4 y el 2. Al quitar el 4, 4<24, entonces ¡te copia de nuevo el 24! Nunca vas a 
	//poder cambiar la capacidad. Por eso se necesita el focusout
	eventFocusOutCapacidadFacingX();

	//Si es caja expositora, queremos que si hacemos un focusout del facing, recalcule todo
	//porque puede que pongas un facing, te recalcule el alto y el ancho y ese facing que has puesto
	//tenga que ir a la baja para que tenga sentido.
	if(imcOriginal.tipoReferencia == esCajaExpositoraRef){
		eventFocusOutFacingX();
	}

	//Evento del botón guardar planograma
	eventBtnGuardarImplantacion();
}

function asignarEventosFacingVegalsa(){
	// Evento para calcular la Capacidad en función del valor del Fondo.
	event_changeCapacidadFacingVegalsa();
	
	//Evento del botón guardar facing VEGALSA
	eventBtnGuardarFacingVegalsa();
}

function eventBtnGuardarImplantacion(){
	$("#p15_btn_guardadoPlanograma").off('click').on('click',function(){
		//modificarImc();
		comprobarCambiosEnFacingYGuardar();
	});
}
function comprobarCambiosEnFacingYGuardar(){
	var altoTieneDato = $("#p15_lbl_desgloseFacingValorAltoInput").val() != ""; 
	var anchoTieneDato = $("#p15_lbl_desgloseFacingValorAnchoInput").val() != "";

	var alto = altoTieneDato ? $("#p15_lbl_desgloseFacingValorAltoInput").val() : 1;
	var ancho = anchoTieneDato ? $("#p15_lbl_desgloseFacingValorAnchoInput").val() : 1;
		
	var anchoOrig = parseInt($("#p15_anchoOrig").val());
	var altoOrig = parseInt($("#p15_altoOrig").val());
		
//	if ((alto-altoOrig)>=2 || (ancho-anchoOrig)>=2){
//		preguntarSiGuardar(alto,ancho);
//	}else if (alto!=altoOrig || ancho!=anchoOrig){
//		modificarImc();
//	}
	/**
	 * MISUMI-387
	 * @author BICUGUAL
	 */
	if (Math.abs(alto-altoOrig)>=2 || Math.abs(ancho-anchoOrig)>=2){
		preguntarSiGuardar(alto,ancho);
	}else {
		modificarImc();
	}
}

function preguntarSiGuardar(alto,ancho){
	var facingOrig = parseInt($("#p15_facingOrig").val());
	//Calculamos el facing nuevo
	var facingNuevo;
	if(imcOriginal.tipoReferencia == esCajaExpositoraRef){
		var anchoOrig = parseInt($("#p15_anchoOrig").val());
		var altoOrig = parseInt($("#p15_altoOrig").val());
		var facingOrig = parseInt($("#p15_facingOrig").val());

		facingNuevo = (facingOrig/(anchoOrig*altoOrig))*(parseInt(alto)*parseInt(ancho));

		//Calculamos la capacidad
		var capacidadOrig = parseInt($("#p15_capacidadOrig").val());
		var calculoCapacidad = capacidadOrig/facingOrig;
		var capacidad = Math.floor(calculoCapacidad * facingNuevo);

		$("#p15_fld_linealCapacidadFacingX").val(capacidad);
	}else if(imcOriginal.tipoReferencia == esMadreRef){
		facingNuevo = alto*ancho;

		//Calculamos la capacidad
		var capacidadOrig = parseInt($("#p15_capacidadOrig").val());
		var facingOrig = parseInt($("#p15_facingOrig").val());
		var calculoCapacidad = capacidadOrig/facingOrig;
		var capacidad = Math.floor(calculoCapacidad * facingNuevo);

		$("#p15_fld_linealCapacidadFacingX").val(capacidad);
	}else{
		facingNuevo = alto*ancho;
	}

	
	var msg = $('#p15_mensajeDialogConfirm').text();

	
	msg = msg.replace('{0}',facingOrig);
	msg = msg.replace('{1}',facingNuevo);

	$('#p15_mensajeDialogConfirm').text(msg);
	
	$(function() {
		 $( "#p15dialog-confirm" ).dialog({
			 resizable: false,
			 height:140,
			 modal: true,
			 buttons: {
				 "Si": function() {
					 modificarImc();
					 var msg = $('#p15_mensajeDialogConfirm').text();

						
					 msg = msg.replace(facingOrig,'{0}');
					 msg = msg.replace(facingNuevo,'{1}');

					 $('#p15_mensajeDialogConfirm').text(msg);
					$( this ).dialog( "close" );
				 },
				 "No": function() {
					 var anchoOrig = parseInt($("#p15_anchoOrig").val());
					 var altoOrig = parseInt($("#p15_altoOrig").val());
					 $("#p15_lbl_desgloseFacingValorAltoInput").val(altoOrig);
					 $("#p15_lbl_desgloseFacingValorAnchoInput").val(anchoOrig);
					 
					 var msg = $('#p15_mensajeDialogConfirm').text();

						
					 msg = msg.replace(facingOrig,'{0}');
					 msg = msg.replace(facingNuevo,'{1}');

					 $('#p15_mensajeDialogConfirm').text(msg);
					 
					 recalcular();
					 $( this ).dialog( "close" );
				 }
			 }
		 });
	});
}

function eventBtnGuardarFacingVegalsa(){
	$("#p15_btn_guardarFacingVegalsa").off('click').on('click',function(){
		modificarFacingVegalsa();
	});
}

function eventKeyUpFacingAlto(){
	$("#p15_lbl_desgloseFacingValorAltoInput").off('keyup').on("keyup",function(e){
		recalcular();
	});
}

function eventKeyUpFacingAncho(){
	$("#p15_lbl_desgloseFacingValorAnchoInput").off('keyup').on("keyup",function(e){
		recalcular();
	});
}

function recalcular(){
	//Calculamos los nuevos campos y luego ocultamos o no el disquete.
	var altoTieneDato = $("#p15_lbl_desgloseFacingValorAltoInput").val() != ""; 
	var anchoTieneDato = $("#p15_lbl_desgloseFacingValorAnchoInput").val() != "";

	var alto = altoTieneDato ? $("#p15_lbl_desgloseFacingValorAltoInput").val() : 1;
	var ancho = anchoTieneDato ? $("#p15_lbl_desgloseFacingValorAnchoInput").val() : 1;

	//Calculamos el facing nuevo
	var facingNuevo;
	if(imcOriginal.tipoReferencia == esCajaExpositoraRef){
		var anchoOrig = parseInt($("#p15_anchoOrig").val());
		var altoOrig = parseInt($("#p15_altoOrig").val());
		var facingOrig = parseInt($("#p15_facingOrig").val());

		facingNuevo = (facingOrig/(anchoOrig*altoOrig))*(parseInt(alto)*parseInt(ancho));

		//Calculamos la capacidad
		var capacidadOrig = parseInt($("#p15_capacidadOrig").val());
		var calculoCapacidad = capacidadOrig/facingOrig;
		var capacidad = Math.floor(calculoCapacidad * facingNuevo);

		$("#p15_fld_linealCapacidadFacingX").val(capacidad);
	}else if(imcOriginal.tipoReferencia == esMadreRef){
		facingNuevo = alto*ancho;

		//Calculamos la capacidad
		var capacidadOrig = parseInt($("#p15_capacidadOrig").val());
		var facingOrig = parseInt($("#p15_facingOrig").val());
		var calculoCapacidad = capacidadOrig/facingOrig;
		var capacidad = Math.floor(calculoCapacidad * facingNuevo);

		$("#p15_fld_linealCapacidadFacingX").val(capacidad);
	}else{
		facingNuevo = alto*ancho;
	}

	//Actualizamos el valor del facing.
	$("#p15_fld_linealFacingFacingX").val(facingNuevo);

	simularImc();

	//Escondemos el mensaje de error
	$("#p15_lbl_guardado").css("visibility","hidden");

	//Miramos si el alto, el ancho, multip y facingX tienen dato.
	var capacidadFacingXTieneDato = $("#p15_fld_linealCapacidadFacingX").val() != "";
	var capacidadFacingXEsCero = $("#p15_fld_linealCapacidadFacingX").val() == "0"; 
	var facingXTieneDato = $("#p15_fld_linealFacingFacingX").val() != "";
	var facingXEsCero = $("#p15_fld_linealFacingFacingX").val() == "0"; 
	var multipTieneDato = $("#p15_lbl_desgloseFacingValorMultiplicadorInput").val() != "";
	var multipEsCero = $("#p15_lbl_desgloseFacingValorMultiplicadorInput").val() == "0"; 
	var altoTieneDato = $("#p15_lbl_desgloseFacingValorAltoInput").val() != ""; 
	var altoEsCero = $("#p15_lbl_desgloseFacingValorAltoInput").val() == "0"; 
	var anchoTieneDato = $("#p15_lbl_desgloseFacingValorAnchoInput").val() != "";
	var anchoEsCero = $("#p15_lbl_desgloseFacingValorAnchoInput").val() == "0"; 

	if(capacidadFacingXTieneDato && !capacidadFacingXEsCero
			&& facingXTieneDato && !facingXEsCero 
			&& multipTieneDato && !multipEsCero 
			&& altoTieneDato && !altoEsCero 
			&& anchoTieneDato && !anchoEsCero){
		$("#p15_btn_guardadoPlanograma").css("visibility","visible");
	}else{
		$("#p15_btn_guardadoPlanograma").css("visibility","hidden");
	}
}

function eventKeyUpMultiplicador(){
	$("#p15_lbl_desgloseFacingValorMultiplicadorInput").off('keyup').on("keyup",function(e){
		var datoMultip = $("#p15_lbl_desgloseFacingValorMultiplicadorInput").val();
		$("#p15_fld_linealMultiplicadorFacingX").val(datoMultip);

		//Hacemos los calculos y luego miramos si hay que ocultar o no el disquete.
		simularImc();

		//Escondemos el mensaje de error
		$("#p15_lbl_guardado").css("visibility","hidden");

		//Miramos si el alto, el ancho, multip y facingX tienen dato.
		var capacidadFacingXTieneDato = $("#p15_fld_linealCapacidadFacingX").val() != "";
		var capacidadFacingXEsCero = $("#p15_fld_linealCapacidadFacingX").val() == "0"; 
		var facingXTieneDato = $("#p15_fld_linealFacingFacingX").val() != "";
		var facingXEsCero = $("#p15_fld_linealFacingFacingX").val() == "0"; 
		var multipTieneDato = $("#p15_lbl_desgloseFacingValorMultiplicadorInput").val() != "";
		var multipEsCero = $("#p15_lbl_desgloseFacingValorMultiplicadorInput").val() == "0"; 
		var altoTieneDato = $("#p15_lbl_desgloseFacingValorAltoInput").val() != ""; 
		var altoEsCero = $("#p15_lbl_desgloseFacingValorAltoInput").val() == "0"; 
		var anchoTieneDato = $("#p15_lbl_desgloseFacingValorAnchoInput").val() != "";
		var anchoEsCero = $("#p15_lbl_desgloseFacingValorAnchoInput").val() == "0"; 

		if(capacidadFacingXTieneDato && !capacidadFacingXEsCero
				&& facingXTieneDato && !facingXEsCero 
				&& multipTieneDato && !multipEsCero 
				&& altoTieneDato && !altoEsCero 
				&& anchoTieneDato && !anchoEsCero){
			$("#p15_btn_guardadoPlanograma").css("visibility","visible");
		}else{
			$("#p15_btn_guardadoPlanograma").css("visibility","hidden");
		}
	});
}

function eventKeyUpFacingX(){
	$("#p15_fld_linealFacingFacingX").off('keyup').on("keyup",function(e){
		//Calculamos el facing nuevo, una vez calculado todo, miramos si hay que ocultar o no el disquete.
		var facingXTieneDato = $("#p15_fld_linealFacingFacingX").val() != "";
		var facingX = facingXTieneDato ? $("#p15_fld_linealFacingFacingX").val() : 1;

		//Calculamos el alto y ancho
		var alto;
		var ancho;
		var capacidad;
		if(imcOriginal.tipoReferencia == esCajaExpositoraRef){
			var anchoOrig = parseInt($("#p15_anchoOrig").val());
			var altoOrig = parseInt($("#p15_altoOrig").val());
			var facingOrig = parseInt($("#p15_facingOrig").val());
			var capacidadOrig = parseInt($("#p15_capacidadOrig").val());

			//Nuevos valores de alto y ancho.
			alto = 1;
			ancho= Math.floor(facingX/(facingOrig/(anchoOrig*altoOrig)));

			var calculoCapacidad = capacidadOrig/facingOrig;
			var capacidad = Math.floor(calculoCapacidad * facingX);

			$("#p15_fld_linealCapacidadFacingX").val(capacidad);
		}else if(imcOriginal.tipoReferencia == esMadreRef){
			//Nuevos valores de alto y ancho.
			alto = 1;
			ancho= facingX;

			var facingOrig = parseInt($("#p15_facingOrig").val());
			var capacidadOrig = parseInt($("#p15_capacidadOrig").val());

			var calculoCapacidad = capacidadOrig/facingOrig;
			var capacidad = Math.floor(calculoCapacidad * facingX);

			$("#p15_fld_linealCapacidadFacingX").val(capacidad);
		}else{
			//Nuevos valores de alto y ancho.
			alto = 1;
			ancho= facingX;
		}


		//Actualizamos el valor del facing.
		$("#p15_lbl_desgloseFacingValorAltoInput").val(alto);
		$("#p15_lbl_desgloseFacingValorAnchoInput").val(ancho);		

		simularImc();

		//Escondemos el mensaje de error
		$("#p15_lbl_guardado").css("visibility","hidden");

		//Miramos si el alto, el ancho, multip y facingX tienen dato.
		var capacidadFacingXTieneDato = $("#p15_fld_linealCapacidadFacingX").val() != "";
		var capacidadFacingXEsCero = $("#p15_fld_linealCapacidadFacingX").val() == "0"; 
		var facingXEsCero = $("#p15_fld_linealFacingFacingX").val() == "0"; 
		var multipTieneDato = $("#p15_lbl_desgloseFacingValorMultiplicadorInput").val() != "";
		var multipEsCero = $("#p15_lbl_desgloseFacingValorMultiplicadorInput").val() == "0"; 
		var altoTieneDato = $("#p15_lbl_desgloseFacingValorAltoInput").val() != ""; 
		var altoEsCero = $("#p15_lbl_desgloseFacingValorAltoInput").val() == "0"; 
		var anchoTieneDato = $("#p15_lbl_desgloseFacingValorAnchoInput").val() != "";
		var anchoEsCero = $("#p15_lbl_desgloseFacingValorAnchoInput").val() == "0"; 

		if(capacidadFacingXTieneDato && !capacidadFacingXEsCero
				&& facingXTieneDato && !facingXEsCero 
				&& multipTieneDato && !multipEsCero 
				&& altoTieneDato && !altoEsCero 
				&& anchoTieneDato && !anchoEsCero){
			$("#p15_btn_guardadoPlanograma").css("visibility","visible");
		}else{
			$("#p15_btn_guardadoPlanograma").css("visibility","hidden");
		}
	});
}

function eventKeyUpCapacidadFacingX(){
	$("#p15_fld_linealCapacidadFacingX").off('keyup').on("keyup",function(e){
		//Escondemos el mensaje de error
		$("#p15_lbl_guardado").css("visibility","hidden");

		//Miramos si el alto, el ancho, multip y facingX tienen dato.
		var capacidadFacingXTieneDato = $("#p15_fld_linealCapacidadFacingX").val() != "";
		var capacidadFacingXEsCero = $("#p15_fld_linealCapacidadFacingX").val() == "0"; 
		var facingXTieneDato = $("#p15_fld_linealFacingFacingX").val() != "";
		var facingXEsCero = $("#p15_fld_linealFacingFacingX").val() == "0"; 
		var multipTieneDato = $("#p15_lbl_desgloseFacingValorMultiplicadorInput").val() != "";
		var multipEsCero = $("#p15_lbl_desgloseFacingValorMultiplicadorInput").val() == "0"; 
		var altoTieneDato = $("#p15_lbl_desgloseFacingValorAltoInput").val() != ""; 
		var altoEsCero = $("#p15_lbl_desgloseFacingValorAltoInput").val() == "0"; 
		var anchoTieneDato = $("#p15_lbl_desgloseFacingValorAnchoInput").val() != "";
		var anchoEsCero = $("#p15_lbl_desgloseFacingValorAnchoInput").val() == "0"; 

		if(capacidadFacingXTieneDato && !capacidadFacingXEsCero
				&& facingXTieneDato && !facingXEsCero 
				&& multipTieneDato && !multipEsCero 
				&& altoTieneDato && !altoEsCero 
				&& anchoTieneDato && !anchoEsCero){
			$("#p15_btn_guardadoPlanograma").css("visibility","visible");
		}else{
			$("#p15_btn_guardadoPlanograma").css("visibility","hidden");
		}

	});
}

function eventFocusOutCapacidadFacingX(){
	$("#p15_fld_linealCapacidadFacingX").off('focusout').on("focusout",function(e){
		var capacidad = $("#p15_fld_linealCapacidadFacingX").val() != "" ? parseInt($("#p15_fld_linealCapacidadFacingX").val()) : 0;
		var imc = parseInt($("#p15_fld_linealImcFacingX").val());

		if(capacidad < imc){
			$("#p15_fld_linealCapacidadFacingX").val(imc);
		}

		//Miramos si el alto, el ancho, multip y facingX tienen dato.
		var capacidadFacingXTieneDato = $("#p15_fld_linealCapacidadFacingX").val() != "";
		var capacidadFacingXEsCero = $("#p15_fld_linealCapacidadFacingX").val() == "0"; 
		var facingXTieneDato = $("#p15_fld_linealFacingFacingX").val() != "";
		var facingXEsCero = $("#p15_fld_linealFacingFacingX").val() == "0"; 
		var multipTieneDato = $("#p15_lbl_desgloseFacingValorMultiplicadorInput").val() != "";
		var multipEsCero = $("#p15_lbl_desgloseFacingValorMultiplicadorInput").val() == "0"; 
		var altoTieneDato = $("#p15_lbl_desgloseFacingValorAltoInput").val() != ""; 
		var altoEsCero = $("#p15_lbl_desgloseFacingValorAltoInput").val() == "0"; 
		var anchoTieneDato = $("#p15_lbl_desgloseFacingValorAnchoInput").val() != "";
		var anchoEsCero = $("#p15_lbl_desgloseFacingValorAnchoInput").val() == "0"; 

		if(capacidadFacingXTieneDato && !capacidadFacingXEsCero
				&& facingXTieneDato && !facingXEsCero 
				&& multipTieneDato && !multipEsCero 
				&& altoTieneDato && !altoEsCero 
				&& anchoTieneDato && !anchoEsCero){
			$("#p15_btn_guardadoPlanograma").css("visibility","visible");
		}else{
			$("#p15_btn_guardadoPlanograma").css("visibility","hidden");
		}
	});
}

function eventFocusOutFacingX(){
	$("#p15_fld_linealFacingFacingX").off('focusout').on("focusout",function(e){
		//Calculamos los nuevos campos y luego ocultamos o no el disquete.
		var altoTieneDato = $("#p15_lbl_desgloseFacingValorAltoInput").val() != ""; 
		var anchoTieneDato = $("#p15_lbl_desgloseFacingValorAnchoInput").val() != "";

		var alto = altoTieneDato ? $("#p15_lbl_desgloseFacingValorAltoInput").val() : 1;
		var ancho = anchoTieneDato ? $("#p15_lbl_desgloseFacingValorAnchoInput").val() : 1;

		//Calculamos el facing nuevo
		var facingNuevo;
		if(imcOriginal.tipoReferencia == esCajaExpositoraRef){
			var anchoOrig = parseInt($("#p15_anchoOrig").val());
			var altoOrig = parseInt($("#p15_altoOrig").val());
			var facingOrig = parseInt($("#p15_facingOrig").val());
			var capacidadOrig = $("#p15_capacidadOrig").val();

			facingNuevo = (facingOrig/(anchoOrig*altoOrig))*(parseInt(alto)*parseInt(ancho));

			//Calculamos la capacidad
			var capacidadOrig = parseInt($("#p15_capacidadOrig").val());
			var calculoCapacidad = capacidadOrig/facingOrig;
			var capacidad = Math.floor(calculoCapacidad * facingNuevo);

			$("#p15_fld_linealCapacidadFacingX").val(capacidad);
		}else if(imcOriginal.tipoReferencia == esMadreRef){
			facingNuevo = alto*ancho;

			//Calculamos la capacidad
			var capacidadOrig = parseInt($("#p15_capacidadOrig").val());
			var facingOrig = parseInt($("#p15_facingOrig").val());
			var calculoCapacidad = capacidadOrig/facingOrig;
			var capacidad = Math.floor(calculoCapacidad * facingNuevo);

			$("#p15_fld_linealCapacidadFacingX").val(capacidad);
		}else{
			facingNuevo = alto*ancho;
		}


		//Actualizamos el valor del facing.
		$("#p15_fld_linealFacingFacingX").val(facingNuevo);

		simularImc();

		//Escondemos el mensaje de error
		$("#p15_lbl_guardado").css("visibility","hidden");

		//Miramos si el alto, el ancho, multip y facingX tienen dato.
		var capacidadFacingXTieneDato = $("#p15_fld_linealCapacidadFacingX").val() != "";
		var capacidadFacingXEsCero = $("#p15_fld_linealCapacidadFacingX").val() == "0"; 
		var facingXTieneDato = $("#p15_fld_linealFacingFacingX").val() != "";
		var facingXEsCero = $("#p15_fld_linealFacingFacingX").val() == "0"; 
		var multipTieneDato = $("#p15_lbl_desgloseFacingValorMultiplicadorInput").val() != "";
		var multipEsCero = $("#p15_lbl_desgloseFacingValorMultiplicadorInput").val() == "0"; 
		var altoEsCero = $("#p15_lbl_desgloseFacingValorAltoInput").val() == "0"; 
		var anchoEsCero = $("#p15_lbl_desgloseFacingValorAnchoInput").val() == "0"; 

		if(capacidadFacingXTieneDato && !capacidadFacingXEsCero
				&& facingXTieneDato && !facingXEsCero 
				&& multipTieneDato && !multipEsCero 
				&& altoTieneDato && !altoEsCero 
				&& anchoTieneDato && !anchoEsCero){
			$("#p15_btn_guardadoPlanograma").css("visibility","visible");
		}else{
			$("#p15_btn_guardadoPlanograma").css("visibility","hidden");
		}
	});
}

function simularImc(){
	var centro = imcOriginal.centro;
	var referencia = imcOriginal.referencia;
	//var facing = $("#p15_fld_linealFacingFacingX").val();

	//var capacidad = $("#p15_fld_linealCapacidadFacingX").val();

	//Si el input del capcidadFacingX se deja vacío, se considera 1.
	var capacidadFacingXInput = $("#p15_fld_linealCapacidadFacingX").val();
	var capacidad;
	if(capacidadFacingXInput == ""){
		capacidad = 1;
	}else{
		capacidad = capacidadFacingXInput;
	}

	//Si el input del facingX se deja vacío, se considera 1.
	var facingXInput = $("#p15_fld_linealFacingFacingX").val();
	var facing;
	if(facingXInput == ""){
		facing = 1;
	}else{
		facing = facingXInput;
	}

	//Si el input del facing alto se deja vacío, se considera 1.
	var facingAltoInput = $("#p15_lbl_desgloseFacingValorAltoInput").val();
	var facingAlto;
	if(facingAltoInput == ""){
		facingAlto = 1;
	}else{
		facingAlto = facingAltoInput;
	}

	//Si el input del facing ancho se deja vacío, se considera 1.
	var facingAnchoInput = $("#p15_lbl_desgloseFacingValorAnchoInput").val(); 	
	var facingAncho;
	if(facingAnchoInput == ""){
		facingAncho = 1;
	}else{
		facingAncho = facingAnchoInput;
	}

	var tipoReferencia = imcOriginal.tipoReferencia;
	var tipoPlano = imcOriginal.tipoPlano;

	//Si el input del multiplicador se deja vacío, se considera 1.
	var multiplicadorInput = $("#p15_lbl_desgloseFacingValorMultiplicadorInput").val();
	var multiplicador;
	if(multiplicadorInput == ""){
		multiplicador = 1;
	}else{
		multiplicador = multiplicadorInput;
	}

	var imc = $("#p15_fld_linealImcFacingX").val();
	var avisoCambio = imcOriginal.avisoCambio;
	var metodo = imcOriginal.metodo;
	var sfm = imcOriginal.sfm;
	var diasDeVenta = imcOriginal.diasDeVenta;
    var tratamientoVegalsa = imcOriginal.tratamientoVegalsa;
    var facingModificable = imcOriginal.facingModificable;
	
    var imagenComercial = new ImagenComercial( centro, referencia, facing, capacidad
								             , facingAlto, facingAncho, tipoReferencia
								             , tipoPlano, multiplicador, imc, avisoCambio
								             , metodo, sfm, diasDeVenta
								             , tratamientoVegalsa, facingModificable
								             );
	
	var objJson = $.toJSON(imagenComercial);
	
	$.ajax({
		type : 'POST',
		url : './referenciasCentro/simularImc.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			//Rellenamos los datos con los que nos viene del controlador.
			/*if (data.capacidad != null){
				$("#p15_fld_linealCapacidadFacingX").val(data.capacidad).formatNumber();
				$("#p15_capacidadOrig").val(data.capacidad).formatNumber();
			}*/

			//Insertamos el facing
			/*if (data.facing != null){
				$("#p15_fld_linealFacingFacingX").val(data.facing).formatNumber();
				$("#p15_facingOrig").val(data.facing).formatNumber();
			}*/

			/*//????????????????????????????? No sé de donde se saca
				if (data.planogramaVigente.fechaGenLinealPantalla != null){
					$("#p15_fld_linealFechaFacingX").val(data.planogramaVigente.fechaGenLinealPantalla);
				}*/

			//Insertamos el multiplicador.
			/*if (data.multiplicador != null){
				$("#p15_fld_linealMultiplicadorFacingX").val(data.multiplicador);
			}*/

			//Insertamos el Imc.
			if (data.imc != null){
				$("#p15_fld_linealImcFacingX").val(data.imc);
			}

			//Si es ffpp, calculamos también la capacidad, que es el facing * uc.
			//Miramos esto después de simular el imc.
			if(imcOriginal.tipoReferencia == esFFPPRef){
				var uc = parseInt($("#p15_uc").val());
				var capacidadNuevo = parseInt(facing) * uc;

				//Si el imc > capacidadNuevo, ponemos en capacidad el IMC.
				var imc = parseInt($("#p15_fld_linealImcFacingX").val());			
				if(imc > capacidadNuevo){
					capacidadNuevo = imc;
				}
				//Ponemos la capacidad nueva
				$("#p15_fld_linealCapacidadFacingX").val(capacidadNuevo);
			}else{
				var capacidad = $("#p15_fld_linealCapacidadFacingX").val();
				if(data.imc > capacidad){
					$("#p15_fld_linealCapacidadFacingX").val(data.imc);
				}
			}
			/*if(capacidadFacingXInput != ""){
				$("#p15_fld_linealCapacidadFacingX").val(data.capacidad).formatNumber();
				//$("#p15_facingOrig").val(data.facing).formatNumber();
			}*/

			//Si el imc es mayor que la capacidad, se actualiza la capacidad
			/*var capacidad = $("#p15_fld_linealCapacidadFacingX").val();
			if(data.imc > capacidad){
				$("#p15_fld_linealCapacidadFacingX").val(data.imc);

				//Como la capacidad no se queda vacia, se 
				$("#p15_btn_guardadoPlanograma").css("visibility","visible");
			}*/

			//Rellenamos los input. Si son vacíos, se han considerado como 1 para los cálculos,
			//pero hay que dejar los input vacíos y no actualizarlos con 1 para que el usuario
			//pueda introducir lo que quiera con el campo vacío. Si no siempre que borrara todos
			//los datos del input, como se consideran 1, el input se rellenaría con 1 automáticamente
			//y no dejaría al usuario meter lo que quiera, resultando molesto.
			/*if(facingAnchoInput != ""){
				$("#p15_lbl_desgloseFacingValorAnchoInput").val(data.facingAncho);
			}

			if(facingXInput != ""){
				$("#p15_fld_linealFacingFacingX").val(data.facing);
				$("#p15_facingOrig").val(data.facing).formatNumber();
			}

			if(multiplicadorInput != ""){
				$("#p15_lbl_desgloseFacingValorMultiplicadorInput").val(data.multiplicador);
			}*/
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		
}

function modificarImc(){
	var centro = imcOriginal.centro;
	var referencia = imcOriginal.referencia;
	var facing = $("#p15_fld_linealFacingFacingX").val();
	var capacidad = $("#p15_fld_linealCapacidadFacingX").val();
	var facingAlto = $("#p15_lbl_desgloseFacingValorAltoInput").val();
	var facingAncho = $("#p15_lbl_desgloseFacingValorAnchoInput").val(); 
	var tipoReferencia = imcOriginal.tipoReferencia;
	var tipoPlano = imcOriginal.tipoPlano;
	var multiplicador = $("#p15_lbl_desgloseFacingValorMultiplicadorInput").val();
	var imc = $("#p15_fld_linealImcFacingX").val();
	var avisoCambio = imcOriginal.avisoCambio;
	var metodo = imcOriginal.metodo;
	var sfm = imcOriginal.sfm;
	var diasDeVenta = imcOriginal.diasDeVenta;
    var tratamientoVegalsa = imcOriginal.tratamientoVegalsa;
    var facingModificable = imcOriginal.facingModificable;

    var imagenComercial = new ImagenComercial( centro, referencia, facing, capacidad
								             , facingAlto, facingAncho, tipoReferencia
								             , tipoPlano, multiplicador, imc, avisoCambio
								             , metodo, sfm, diasDeVenta
								             , tratamientoVegalsa, facingModificable
								             );

	var objJson = $.toJSON(imagenComercial);
	
	$.ajax({
		type : 'POST',
		url : './referenciasCentro/modificarImc.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			if(data != null){
				console.log("modificarImc");
				console.log(data);
				if(data.codError == 0){
					//Rellenamos los datos con los que nos viene del controlador.
					if (data.capacidad != null){
						$("#p15_fld_linealCapacidadFacingX").val(data.capacidad).formatNumber();
						$("#p15_capacidadOrig").val(data.capacidad).formatNumber();
					}

					//Insertamos el facing
					if (data.facing != null){
						$("#p15_fld_linealFacingFacingX").val(data.facing).formatNumber();
						$("#p15_facingOrig").val(data.facing).formatNumber();
					}

					/*//????????????????????????????? No sé de donde se saca
				if (data.planogramaVigente.fechaGenLinealPantalla != null){
					$("#p15_fld_linealFechaFacingX").val(data.planogramaVigente.fechaGenLinealPantalla);
				}*/

					//Insertamos el multiplicador.
					if (data.multiplicador != null){
						$("#p15_fld_linealMultiplicadorFacingX").val(data.multiplicador);
					}

					//Insertamos el Imc.
					if (data.imc != null){
						$("#p15_fld_linealImcFacingX").val(data.imc);
					}

					//Rellenamos los input y los orig de ancho y alto.
					$("#p15_lbl_desgloseFacingValorAnchoInput").val(data.facingAncho);
					$("#p15_anchoOrig").val(data.facingAncho);

					$("#p15_lbl_desgloseFacingValorAltoInput").val(data.facingAlto);
					$("#p15_altoOrig").val(data.facingAlto);

					$("#p15_lbl_desgloseFacingValorMultiplicadorInput").val(data.multiplicador);

					//Ponemos el texto guardado OK en verde
					$("#p15_lbl_guardado").removeClass("p15_imagencomercialGuardadoError");
					$("#p15_lbl_guardado").addClass("p15_imagencomercialGuardadoOk");

					$("#p15_lbl_guardado").text(guardadoOk);
					$("#p15_lbl_guardado").css("visibility","visible");
				}else{
					//Ponemos el texto guardado error en rojo
					$("#p15_lbl_guardado").removeClass("p15_imagencomercialGuardadoOk");
					$("#p15_lbl_guardado").addClass("p15_imagencomercialGuardadoError");

					$("#p15_lbl_guardado").text(data.descError);
					$("#p15_lbl_guardado").css("visibility","visible");
				}
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		

}

function modificarFacingVegalsa(){

	var centro = facingVegalsaOri.centro;
	var referencia = facingVegalsaOri.referencia;
	var facing = $("#p15_fld_linealFacingFacingX").val();
	var capacidad = $("#p15_fld_linealCapacidadFacingX").val();
	var facingAlto = facingVegalsaOri.facingAlto;
	var facingAncho = facingVegalsaOri.facingAncho;
	var tipoReferencia = facingVegalsaOri.tipoReferencia;
	var tipoPlano = facingVegalsaOri.tipoPlano;
	var multiplicador = $("#p15_fld_linealMultiplicadorFacingX").val();
	var imc = facingVegalsaOri.imc;
	var avisoCambio = facingVegalsaOri.avisoCambio;
	var metodo = facingVegalsaOri.metodo;
	var sfm = facingVegalsaOri.sfm;
	var diasDeVenta = facingVegalsaOri.diasDeVenta;
	var tratamientoVegalsa = facingVegalsaOri.tratamientoVegalsa;
	var facingModificable = facingVegalsaOri.facingModificable;

	var imagenComercial = new ImagenComercial( centro, referencia, facing, capacidad
											 , facingAlto, facingAncho, tipoReferencia
											 , tipoPlano, multiplicador, imc, avisoCambio
											 , metodo, sfm, diasDeVenta
											 , tratamientoVegalsa, facingModificable
											 );
	var objJson = $.toJSON(imagenComercial);

	$.ajax({
		type : 'POST',
		url : './referenciasCentro/modificarFacingVegalsa.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			if(data != null){
				if(data.codError == 0){
					//Rellenamos los datos con los que nos viene del controlador.
					if (data.capacidad != null){
						$("#p15_fld_linealCapacidadFacingX").val(data.capacidad).formatNumber();
						$("#p15_capacidadOrig").val(data.capacidad).formatNumber();
					}

					//Insertamos el facing
					if (data.facing != null){
						$("#p15_fld_linealFacingFacingX").val(data.facing).formatNumber();
						$("#p15_facingOrig").val(data.facing).formatNumber();
					}

					//Insertamos el fondo.
					if (data.fondo != null){
						$("#p15_fld_linealMultiplicadorFacingX").val(data.fondo);
					}

					//Ponemos el texto guardado OK en verde
					$("#p15_lbl_guardadoVegalsa").removeClass("p15_imagencomercialGuardadoError");
					$("#p15_lbl_guardadoVegalsa").addClass("p15_imagencomercialGuardadoOk");

					$("#p15_lbl_guardadoVegalsa").text(guardadoOk);
					$("#p15_lbl_guardadoVegalsa").css("visibility","visible");
				}else{
					//Ponemos el texto guardado error en rojo
					$("#p15_lbl_guardadoVegalsa").removeClass("p15_imagencomercialGuardadoOk");
					$("#p15_lbl_guardadoVegalsa").addClass("p15_imagencomercialGuardadoError");

					$("#p15_lbl_guardadoVegalsa").text(data.descripcionError);
					$("#p15_lbl_guardadoVegalsa").css("visibility","visible");
				}
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		

}


function loadImagenComercialVentasMedias(){
	var vReferenciasCentro=new ReferenciasCentro($("#p13_fld_referenciaEroski").val(), $("#centerId").val());
	var objJson = $.toJSON(vReferenciasCentro);	
	$.ajax({
		type : 'POST',
		url : './referenciasCentro/loadDatosImagenComercialVentasMedias.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	

			//ActualizaciÃ³n de los datos de "Ventas media"
			updateResultadosICVentasMedia(data);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		
}

function loadImagenComercialVentasUltMes(){
	var vReferenciasCentro=new ReferenciasCentro($("#p13_fld_referenciaEroski").val(), $("#centerId").val());
	var objJson = $.toJSON(vReferenciasCentro);	
	$.ajax({
		type : 'POST',
		url : './referenciasCentro/loadDatosImagenComercialVentasUltMes.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			//ActualizaciÃ³n de los datos de "Ventas Ãºltimo mes"
			updateResultadosICVentasUltimoMes(data);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		
}

function loadImagenComercialSfm(){
	$("#p15_AreaStockFinalMinimo").attr("style", "display:inline-block");
	$("#p15_AreaPlanogramas").attr("style", "display:none");
	var vReferenciasCentro=new ReferenciasCentro($("#p13_fld_referenciaEroski").val(), $("#centerId").val());
	var objJson = $.toJSON(vReferenciasCentro);	
	$.ajax({
		type : 'POST',
		url : './referenciasCentro/loadDatosImagenComercialSfm.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			//ActualizaciÃ³n de los datos de "Stock final mÃ­nimo"
			updateResultadosICStockFinalMinimo(data);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		
}


function mostrarBloquePlanogramasMontajes(metodo){

	//Los flg tendrán S si cumplen las premisas.
	var flgFacingCapacidad = metodo == esFacCapRef ? "S":"N";
	var flgFacing =  metodo == esFacNoAliRef ? "S":"N";
	var flgCapacidad =  metodo == esCapRef ? "S":"N";

	var vReferenciasCentro=new ReferenciasCentro($("#p13_fld_referenciaEroski").val(), $("#centerId").val());
	vReferenciasCentro.flgFacing = flgFacing;
	vReferenciasCentro.flgCapacidad = flgCapacidad;
	vReferenciasCentro.flgFacingCapacidad = flgFacingCapacidad;
	var objJson = $.toJSON(vReferenciasCentro);	

	$.ajax({
		type : 'POST',
		url : './referenciasCentro/loadDatosImagenComercialPlanogramas.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	

			//ActualizaciÃ³n de los datos de "Planogramas"
			//Por la incidencia 284: Cuando se muestra el mensaje de pedido no activo
			//el montaje adicional 1 y 2 se muestran vacios. Por eso, sólo se rellenan los datos de montaje adicional 1 y 2
			//cuando el booleanMostrarMensajeNoActivo es false
			var pedir = $("#p13_fld_refActiva").val();
			var mapaHoy = $("#p13_fld_mapaHoy").val();
			var numeroPedidosOtroDia = $("#p13_numeroPedidosOtroDia").val();
			var booleanMostrarMensajeNoActivo = (pedir == null || pedir == 'N' || mapaHoy == null || (mapaHoy == 'N' && numeroPedidosOtroDia!="" && numeroPedidosOtroDia == 0));
			
			if (esCentroVegalsa()){
				booleanMostrarMensajeNoActivo=false;
				if (data.planogramaVigente.incPrevisionVenta != null){
					$("#p15_fld_montaje1IncPrevVenta").val(data.planogramaVigente.incPrevisionVenta+'%');
				}
				if (data.planogramaVigente.smEstatico != null){
					$("#p15_fld_montaje1SMEstatico").val(data.planogramaVigente.smEstatico);
				}
			}
			if ( booleanMostrarMensajeNoActivo == false && data.planogramaVigente!=null){
				if (null != data.planogramaVigente.fechaGenMontaje1){
					if (data.planogramaVigente.capacidadMontaje1 != null){
						$("#p15_fld_montaje1Capacidad").val(data.planogramaVigente.capacidadMontaje1).formatNumber();
					}
					if (data.planogramaVigente.facingMontaje1 != null){
						$("#p15_fld_montaje1Facing").val(data.planogramaVigente.facingMontaje1).formatNumber();
					}
					if (data.planogramaVigente.flgCabeceraMontaje1 != null && (data.planogramaVigente.flgCabeceraMontaje1 == 'S' || data.planogramaVigente.flgCabeceraMontaje1 == 'X')){
						$("#p15_chk_montaje1Cabecera").attr("checked", true);
					}
					if (data.planogramaVigente.flgOfertaMontaje1 != null && data.planogramaVigente.flgOfertaMontaje1 == 'S'){
						$("#p15_chk_montaje1Oferta").attr("checked", true);
					}
					if (data.planogramaVigente.flgCampanaMontaje1 != null && data.planogramaVigente.flgCampanaMontaje1 == 'S'){
						$("#p15_chk_montaje1Campana").attr("checked", true);
					}
					if (esCentroVegalsa()){
						if (data.planogramaVigente.ofertaProm != null){
							$("#p15_fld_montaje1Oferta").val(data.planogramaVigente.ofertaProm);
						}
						if (data.planogramaVigente.espacioProm != null){
							$("#p15_fld_montaje1EspacioProm").val(data.planogramaVigente.espacioProm);
						}
					}
					if (null != data.planogramaVigente.fechaGenMontaje2){
						if (data.planogramaVigente.capacidadMontaje2 != null){
							$("#p15_fld_montaje2Capacidad").val(data.planogramaVigente.capacidadMontaje2).formatNumber();
						}
						if (data.planogramaVigente.facingMontaje2 != null){
							$("#p15_fld_montaje2Facing").val(data.planogramaVigente.facingMontaje2).formatNumber();
						}
						if (data.planogramaVigente.flgCabeceraMontaje2 != null && (data.planogramaVigente.flgCabeceraMontaje2 == 'S' || data.planogramaVigente.flgCabeceraMontaje2 == 'X')){
							$("#p15_chk_montaje2Cabecera").attr("checked", true);
						}
						if (data.planogramaVigente.flgOfertaMontaje2 != null && data.planogramaVigente.flgOfertaMontaje2 == 'S'){
							$("#p15_chk_montaje2Oferta").attr("checked", true);
						}
						if (data.planogramaVigente.flgCampanaMontaje2 != null && data.planogramaVigente.flgCampanaMontaje2 == 'S'){
							$("#p15_chk_montaje2Campana").attr("checked", true);
						}
					} else if (null != data.planogramaVigente.fechaGenCabecera) {
						if (data.planogramaVigente.capacidadMaxCabecera != null){
							$("#p15_fld_montaje2Capacidad").val(data.planogramaVigente.capacidadMaxCabecera).formatNumber();
						}
						if (data.planogramaVigente.stockMinComerCabecera != null){
							$("#p15_fld_montaje2Facing").val(data.planogramaVigente.stockMinComerCabecera).formatNumber();
						}
					}
				} else if (null != data.planogramaVigente.fechaGenCabecera){
					if (data.planogramaVigente.capacidadMaxCabecera != null){
						$("#p15_fld_montaje1Capacidad").val(data.planogramaVigente.capacidadMaxCabecera).formatNumber();
					}
					if (data.planogramaVigente.stockMinComerCabecera != null){
						$("#p15_fld_montaje1Facing").val(data.planogramaVigente.stockMinComerCabecera).formatNumber();
					}
					
				}
				
			}
			//MISUMI-428
			if(null != data.capacidadMontajeAdicionalCentro && "" != data.capacidadMontajeAdicionalCentro){
				$("#p15_montajeAdicionalCentroBloque_div").attr("style", "display:inline-block");
				
				$("#p15_input_montajeCapacidadCentro").val(data.capacidadMontajeAdicionalCentro);
				$("#p15_input_montajeFechaInicioCentro").val(p15FormateoFechaEntrega(data.fechaInicioMontajeAdicionalCentro));
				$("#p15_input_montajeFechaFinCentro").val(p15FormateoFechaEntrega(data.fechaFinMontajeAdicionalCentro));
				$("#p15_input_montajeOfertaCentro").val(data.ofertaMontajeAdicionalCentro);
			}else{
				$("#p15_montajeAdicionalCentroBloque_div").attr("style", "display:none");
				
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		
}

function loadImagenComercialPlanogramas(flgFacing,flgCapacidad,flgFacingCapacidad){
	comprobarVegalsa();

	$("#p15_AreaStockFinalMinimo").attr("style", "display:none");
	$("#p15_AreaPlanogramas").attr("style", "display:inline-block");
	var vReferenciasCentro=new ReferenciasCentro($("#p13_fld_referenciaEroski").val(), $("#centerId").val());
	vReferenciasCentro.flgFacing = flgFacing;
	vReferenciasCentro.flgCapacidad = flgCapacidad;
	vReferenciasCentro.flgFacingCapacidad = flgFacingCapacidad;
	var objJson = $.toJSON(vReferenciasCentro);	

	$.ajax({
		type : 'POST',
		url : './referenciasCentro/loadDatosImagenComercialPlanogramas.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			//Actualización de los datos de "Planogramas"
			updateResultadosICPlanogramas(data);
			loadPlanogramasWS();
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		
}

function updateResultadosICStockFinalMinimo (data){

	if (data.stockFinalMinimo != null){
		$("#p15_stockFinalMinimoUnidadesTdS").text(data.stockFinalMinimo.stockFinMinS).formatNumber();
		$("#p15_stockFinalMinimoDiasVentaTdS").text(data.stockFinalMinimo.diasVentaS).formatNumber();
		if (data.stockFinalMinimo.cantidadManualSIA!=null && data.stockFinalMinimo.cantidadManualSIA!=data.stockFinalMinimo.stockFinMinS){
			var mensajeImpFormateado = mensajeImpSFM.replace("#stockFinalMinimo#", $.formatNumber(data.stockFinalMinimo.cantidadManualSIA,{format:"0.000",locale:"es"}));
			$("#p15_stockFinalMinimoMensajeTexto2").text(mensajeImpFormateado);
			$("#p15_stockFinalMinimoMensaje").attr("style", "display:inline-block");
		}else{
			$("#p15_stockFinalMinimoMensaje").attr("style", "display:none;");
		}
	}

}

function updateResultadosICFacing (data){

	if (data.stockFinalMinimo != null){
		$("#p15_facingCentroTdS").text(data.stockFinalMinimo.facingCentro).formatNumber();
		$("#p15_facingPrevioTdS").text(data.stockFinalMinimo.facingPrevio).formatNumber();
		//Si el facing centro es cero y el facing previo es distinto de cero se mostrará  el facing previo, en otro caso estará oculto
		if (data.stockFinalMinimo.facingCentro == 0 && data.stockFinalMinimo.facingPrevio != 0){
			$("#p15_tr_facing_previo").attr("style", "");
		}else{
			$("#p15_tr_facing_previo").attr("style", "display:none");
		}
		if (data.stockFinalMinimo.facingCentroSIA!=null && data.stockFinalMinimo.facingCentroSIA!=data.stockFinalMinimo.facingCentro){
			var mensajeFacingFormateado = mensajeFacing.replace("#facing#", $.formatNumber(data.stockFinalMinimo.facingCentroSIA,{format:"0.000",locale:"es"}));
			$("#p15_facingMensajeTexto2").text(mensajeFacingFormateado);
			$("#p15_facingMensaje").attr("style", "display:inline-block");
		}else{
			$("#p15_facingMensaje").attr("style", "display:none;");
		}
	}

}

function mostrarImplantacion(data){
	console.log("mostrarImplantacion");
	console.log(data);
	//Si tiene facing alto y ancho
	if(data.facingAlto != null && data.facingAlto != 0 && data.facingAncho != null && data.facingAncho != 0){
		//Añadimos el título del desglose facing
		$("#p15_lbl_desgloseFacingTitle").text(desgloseFacingTitle);

		//Rellenamos los labels
		$("#p15_lbl_desgloseFacingValorAncho").text(data.facingAncho);
		$("#p15_lbl_desgloseFacingValorAlto").text(data.facingAlto);
		$("#p15_lbl_desgloseFacingValorMultiplicador").text(data.multiplicador);

		//Rellenamos los input
		$("#p15_lbl_desgloseFacingValorAnchoInput").val(data.facingAncho);
		$("#p15_lbl_desgloseFacingValorAltoInput").val(data.facingAlto);
		$("#p15_lbl_desgloseFacingValorMultiplicadorInput").val(data.multiplicador);

		//Ponemos que los input solo sean enteros
		$("#p15_lbl_desgloseFacingValorAnchoInput").filter_input({regex:'[0-9]'});
		$("#p15_lbl_desgloseFacingValorAltoInput").filter_input({regex:'[0-9]'});
		$("#p15_lbl_desgloseFacingValorMultiplicadorInput").filter_input({regex:'[0-9]'});
		$("#p15_fld_linealCapacidadFacingX").filter_input({regex:'[0-9]'});
		$("#p15_fld_linealFacingFacingX").filter_input({regex:'[0-9]'});

		//Rellenamos el tipo de planograma
		$("#p15_fld_planogramado").val(data.tipoPlano);

		//Si es ffpp solo mostramos el ancho y la imagen de la caja.
		var titulo;
		if(data.tipoReferencia == esFFPPRef){ //Es FFPP
			titulo = $("#p15_lbl_desgloseFacingTitle").text();
			titulo = titulo.replace('{0}',tituloDesgloseFacingFFPP);
			$("#p15_lbl_desgloseFacingTitle").text(titulo);

			//Ocultamos el alto
			$("#p15_lbl_desgloseFacingAlto").css("visibility","hidden");
			$("#p15_lbl_desgloseFacingValorAlto").css("visibility","hidden");
			$("#p15_lbl_desgloseFacingValorAltoInput").css("visibility","hidden");
			$("#p15_desgloseFacingAlto").hide();

			//Ocultamos el multiplicador
			$("#p15_lbl_desgloseFacingMultiplicador").css("visibility","hidden");
			$("#p15_lbl_desgloseFacingValorMultiplicador").css("visibility","hidden");
			$("#p15_lbl_desgloseFacingValorMultiplicadorInput").css("visibility","hidden");
			$("#p15_desgloseFacingMul").hide();

			//Mostramos la imagen correspondiente
			$("#p15_img_desgloseFacingPale").hide();
			$("#p15_img_desgloseFacingCajaExpositora").hide();
			$("#p15_img_desgloseFacingCaja").show();


			//Ajustamos el css del ancho para que coincida con el espacio de la imagen.
			$("#p15_desgloseFacingAncho").addClass("p15_desgloseFacingAnchoCaja");
			$("#p15_desgloseFacingAncho").removeClass("p15_desgloseFacingAnchoPale");
			$("#p15_desgloseFacingAncho").removeClass("p15_desgloseFacingAnchoCajaExpositora");

			//Quitamos la clase para que se vea bien el alto
			$("#p15_desgloseFacingAlto").removeClass("p15_desgloseFacingAltoCajaExpositora");

		}
		//MISUMI-100 -- Se ha preguntado a Maria si hay que quitarlo o sustituir la condición por --if(data.vPlanogramaTipoP.esCajaExp != "S"){--
		else if(data.tipoReferencia == esCajaExpositoraRef){ //Es Caja Expositora

			titulo = $("#p15_lbl_desgloseFacingTitle").text();
			titulo = titulo.replace('{0}',tituloDesgloseFacingNoFFPPCajas);
			$("#p15_lbl_desgloseFacingTitle").text(titulo);

			//Mostramos el alto
			$("#p15_lbl_desgloseFacingAlto").css("visibility","visible");
			$("#p15_lbl_desgloseFacingValorAlto").css("visibility","visible");
			$("#p15_lbl_desgloseFacingValorAltoInput").css("visibility","visible");
			$("#p15_desgloseFacingAlto").show();

			//Mostramos el multiplicador
			$("#p15_lbl_desgloseFacingMultiplicador").css("visibility","visible");
			$("#p15_lbl_desgloseFacingValorMultiplicador").css("visibility","visible");
			$("#p15_lbl_desgloseFacingValorMultiplicadorInput").css("visibility","visible");
			$("#p15_desgloseFacingMul").show();

			//Mostramos la imagen correspondiente
			$("#p15_img_desgloseFacingPale").hide();
			$("#p15_img_desgloseFacingCajaExpositora").show();
			$("#p15_img_desgloseFacingCaja").hide();


			//Ajustamos el css del ancho para que coincida con el espacio de la imagen.
			$("#p15_desgloseFacingAncho").addClass("p15_desgloseFacingAnchoCajaExpositora");
			$("#p15_desgloseFacingAncho").removeClass("p15_desgloseFacingAnchoPale");
			$("#p15_desgloseFacingAncho").removeClass("p15_desgloseFacingAnchoCaja");

			//Añadimos la clase para que se vea bien el alto
			$("#p15_desgloseFacingAlto").addClass("p15_desgloseFacingAltoCajaExpositora");				

		}else if(data.tipoReferencia == esMadreRef){ // Resto (Unitaria...)

			titulo = $("#p15_lbl_desgloseFacingTitle").text();
			titulo = titulo.replace('{0}',tituloDesgloseFacingNoFFPP);
			$("#p15_lbl_desgloseFacingTitle").text(titulo);

			//Mostramos el alto
			$("#p15_lbl_desgloseFacingAlto").css("visibility","visible");
			$("#p15_lbl_desgloseFacingValorAlto").css("visibility","visible");
			$("#p15_lbl_desgloseFacingValorAltoInput").css("visibility","visible");
			$("#p15_desgloseFacingAlto").show();

			//Mostramos el multiplicador
			$("#p15_lbl_desgloseFacingMultiplicador").css("visibility","visible");
			$("#p15_lbl_desgloseFacingValorMultiplicador").css("visibility","visible");
			$("#p15_lbl_desgloseFacingValorMultiplicadorInput").css("visibility","visible");
			$("#p15_desgloseFacingMul").show();

			//Mostramos la imagen correspondiente
			$("#p15_img_desgloseFacingPale").show();
			$("#p15_img_desgloseFacingCajaExpositora").hide();
			$("#p15_img_desgloseFacingCaja").hide();

			//Ajustamos el css del ancho para que coincida con el espacio de la imagen.
			$("#p15_desgloseFacingAncho").addClass("p15_desgloseFacingAnchoPale");
			$("#p15_desgloseFacingAncho").removeClass("p15_desgloseFacingAnchoCajaExpositora");
			$("#p15_desgloseFacingAncho").removeClass("p15_desgloseFacingAnchoCaja");

			//Quitamos la clase para que se vea bien el alto
			$("#p15_desgloseFacingAlto").removeClass("p15_desgloseFacingAltoCajaExpositora");
		}
		//Rellenamos el ancho y el alto orig.
		$("#p15_anchoOrig").val(data.facingAncho);
		$("#p15_altoOrig").val(data.facingAlto);
		//Mostramos el bloque de modificación
		mostrarModificacionImc(data);

		//Ocultamos el posible mensaje de error o de guardado.
		$("#p15_lbl_guardado").css("visibility","hidden");

		//Mostramos la imagen
		$("#p15_EstructuraLinealBloqueDesgloseFacing").show();
	}
}

//Muestra el botón de modificar el imc.
function mostrarModificacionImc(data){
	/** Guardamos en una variable global los datos originales obtenidos del procedimiento consultarImc:
	  	•	CENTRO	            
		•	REFERENCIA          
		•	FACING				
		•	CAPACIDAD           
		•	FACING_ALTO			
		•	FACING_ANCHO		
		•	TIPO_REFERENCIA 
		•	TIPO_PLANO      
		•	MULTIPLICADOR		
		•	IMC					
		•	AVISO_CAMBIO		
		•	METODO		
		•	SFM 				
		•	DIAS_DE_VENTA		
		•	COD_ERROR           
		•	DESCRIPCION_ERROR
		   

		Algunos de estos datos nos servirán cuando llamemos a los procedimientos de simular o modificar.
	 **/

	imcOriginal = data;

	//Mostramos u ocultamos el botón de modificación del facing si el usuario es tecnico,admin, de consulta, etc.
	var tienePermisoImc = $("#p15_permiso_modif_imc_plano").val();
	if((userPerfilMisumi == CONS_TECNIC_ROLE || userPerfilMisumi == CONS_ADMIN_ROLE)){
		//Habilitamos los input
		$("#p15_lbl_desgloseFacingValorAnchoInput").show();
		$("#p15_lbl_desgloseFacingValorAltoInput").show();
		$("#p15_lbl_desgloseFacingValorMultiplicadorInput").show();

		//Esacondemos los lbl
		$("#p15_lbl_desgloseFacingValorAncho").hide();
		$("#p15_lbl_desgloseFacingValorAlto").hide();
		$("#p15_lbl_desgloseFacingValorMultiplicador").hide();

		//Habilitamos el facing 
		$("#p15_fld_linealFacingFacingX").prop("disabled", false);

		//Habilitamos el input de capacidad si su valor es diferente de FFPP para que el usuario
		//pueda cambiarlo. Si es FFPP, calculamos unidades caja porque: capacidad = facing * uc;
		if(data.tipoReferencia != esFFPPRef){
			$("#p15_fld_linealCapacidadFacingX").prop("disabled", false);
		}else{
			loadUnidadesCaja();
		}

		//Asigna los eventos del area de los planogramas
		asignarEventosAreaPlanogramas();

		//Mostamos el botón guardar
		$("#p15_guardarCambios").show();
	}else if(userPerfilMisumi == CONS_CENTER_ROLE && tienePermisoImc == "true"){
		//Si es FFPP, calculamos unidades caja porque: capacidad = facing * uc;
		//Además no deja modificar nada
		if(data.tipoReferencia == esFFPPRef){
			loadUnidadesCaja();
			
			//Escondemos todos los input
			$("#p15_lbl_desgloseFacingValorAnchoInput").hide();
			$("#p15_lbl_desgloseFacingValorAltoInput").hide();
			$("#p15_lbl_desgloseFacingValorMultiplicadorInput").hide();

			//Mostramos los lbl
			$("#p15_lbl_desgloseFacingValorAncho").show();
			
			//Habilitamos el facing 
			$("#p15_fld_linealFacingFacingX").prop("disabled", true);
			
			//Ocultamos el botón guardar
			$("#p15_guardarCambios").hide();
		}else{
			//Habilitamos los input y el lbl de multiplicador
			$("#p15_lbl_desgloseFacingValorAnchoInput").show();
			$("#p15_lbl_desgloseFacingValorAltoInput").show();
			$("#p15_lbl_desgloseFacingValorMultiplicador").show();

			//Esacondemos los lbl
			$("#p15_lbl_desgloseFacingValorAncho").hide();
			$("#p15_lbl_desgloseFacingValorAlto").hide();
			$("#p15_lbl_desgloseFacingValorMultiplicadorInput").hide();

			//Habilitamos el facing 
			$("#p15_fld_linealFacingFacingX").prop("disabled", false);
			
			//Asigna los eventos del area de los planogramas
			asignarEventosAreaPlanogramas();

			//Mostamos el botón guardar
			$("#p15_guardarCambios").show();
		}				
	}else{
		//Esacondemos los input
		$("#p15_lbl_desgloseFacingValorAnchoInput").hide();
		$("#p15_lbl_desgloseFacingValorAltoInput").hide();
		$("#p15_lbl_desgloseFacingValorMultiplicadorInput").hide();

		//Habilitamos los lbl
		$("#p15_lbl_desgloseFacingValorAncho").show();
		$("#p15_lbl_desgloseFacingValorAlto").show();
		$("#p15_lbl_desgloseFacingValorMultiplicador").show();

		//Deshabilitamos el facing 
		$("#p15_fld_linealFacingFacingX").prop("disabled", true);

		//Deshabilitamos la capacidad
		$("#p15_fld_linealCapacidadFacingX").prop("disabled", true);

		//Mostamos el botón guardar
		$("#p15_guardarCambios").hide();
	}
}
function updateResultadosICPlanogramas (data){
	if (data.planogramaVigente != null){

		//Añadimos el título del desglose facing
		$("#p15_lbl_desgloseFacingTitle").text(desgloseFacingTitle);

		//QUITAR ESTO --------------------------------------
		var planogramaCalculado = data.planogramaVigente.esPlanogramaCalculado;
		data.planogramaVigente.esPlanogramaCalculado = true;
		//HASTA AQUÍ ----------------------------------------

		//Si el programa es calculado, se pone el texto "Calculado" y si no "Planogramado"
		if(data.planogramaVigente.esPlanogramaCalculado){
			$("#p15_fld_tipoPlanograma").val(calculado);

			$("#p15_fld_linealFacingFacingX").attr("disabled","disabled");
			$("#p15_fld_linealCapacidadFacingX").attr("disabled","disabled");

			//Ocultamos el botón de modificar.
			$("#p15_areaBtn").hide();
		}else{
			$("#p15_fld_tipoPlanograma").val(planogramado);

			//Si es planogramado y además el usuario es un técnico, se puede modificar.
			if(data.planogramaVigente.esTecnico == "S"){
				//Habilitamos los campos a modificar.
				$("#p15_fld_linealFacingFacingX").attr("disabled",false);
				$("#p15_fld_linealCapacidadFacingX").attr("disabled",false);

				//Enseñamos el botón de modificar.
				$("#p15_areaBtn").show();
			}else{
				//Deshabilitamos los campos a modificar.
				$("#p15_fld_linealFacingFacingX").attr("disabled","disabled");
				$("#p15_tb_linealCapacidadFacingX").attr("disabled","disabled");

				//Ocultamos el botón de modificar.
				$("#p15_areaBtn").hide();
			}
		}
		//MISUMI-47 QUITAR DESDE AQUI ---------------------
		if(planogramaCalculado){
			$("#p15_fld_tipoPlanograma").val(calculado);
		}else{
			$("#p15_fld_tipoPlanograma").val(planogramado);
		}
		//HASTA AQUI ---------------------------------------
//		if (data.planogramaVigente.simuladoLineal != null && data.planogramaVigente.simuladoLineal == 'S'){
//		//Planograma simulado
//		$("#p15_fld_descripcionPlanograma").val(descripcionPlanogramaSimulado);
//		}else{
//		//Llamada al webservice
//		//Pendiente de implementar
//		$("#p15_fld_descripcionPlanograma").val("Pendiente valor del webservice");
//		}

		if ((data.flgFacingCapacidad == 'S') ||  (data.flgFacing != 'S' && data.flgCapacidad == 'S')) { //Si es una referencia de Facing-Capacidad 
			//o de Capacidad

			if (data.planogramaVigente.esFacingX){
				if (data.planogramaVigente.capacidadMaxLineal != null){
					$("#p15_fld_linealCapacidadFacingX").val(data.planogramaVigente.capacidadMaxLineal).formatNumber();
					$("#p15_capacidadOrig").val(data.planogramaVigente.capacidadMaxLineal).formatNumber();
				}
				if (data.planogramaVigente.stockMinComerLineal != null){
					$("#p15_fld_linealFacingFacingX").val(data.planogramaVigente.stockMinComerLineal).formatNumber();
					$("#p15_facingOrig").val(data.planogramaVigente.stockMinComerLineal).formatNumber();
					//$("#p15_fld_linealFacing").val(data.stockFinalMinimo.facingCentro).formatNumber();
				}
				if (data.planogramaVigente.fechaGenLinealPantalla != null){
					$("#p15_fld_linealFechaFacingX").val(data.planogramaVigente.fechaGenLinealPantalla);
				}
				if (data.planogramaVigente.multiplicadorFac != null){
					$("#p15_fld_linealMultiplicadorFacingX").val(data.planogramaVigente.multiplicadorFac);
				}
				if (data.planogramaVigente.imc != null){
					$("#p15_fld_linealImcFacingX").val(data.planogramaVigente.imc);
				}

				//Control de multiplicador para FFPP
				if (data.esFFPP){ //Es FFPP
					$("#p15_EstructuraLinealBloque2_1FacingX").addClass("p15_correccion_FFPP_multiplicador");
					$("#p15_EstructuraLinealBloque2_1_2FacingX").attr("style", "display:none");
					$("#p15_linealImagenLLaveFacingXDiv").attr("style", "display:none");

					//Se quita la clase para que la fecha se vea alineada con facing y capacidad porque ahora no la necesitamos
					$("#p15_tb_fechaFacingX").removeClass("p15_tb_fechaFacingXCajasExpositoras");
				}else{
					//MISUMI-100 -- Se ha preguntado a Maria si hay que quitarlo o sustituir la condición por --if(data.vPlanogramaTipoP.esCajaExp != "S"){--

					if(data.vPlanogramaTipoP != null){
						if(data.vPlanogramaTipoP.esCajaExp == "S"){ //Es Caja Expositora

							$("#p15_EstructuraLinealBloque2_1FacingX").removeClass("p15_correccion_FFPP_multiplicador");
							$("#p15_EstructuraLinealBloque2_1_2FacingX").attr("style", "display:inline-block");
							$("#p15_linealImagenLLaveFacingXDiv").attr("style", "display:inline-block");

							//Se quita la clase para que la fecha se vea alineada con facing y capacidad porque ahora no la necesitamos
							$("#p15_tb_fechaFacingX").removeClass("p15_tb_fechaFacingXCajasExpositoras");

						}else{ //Resto (Unitaria...)


							$("#p15_EstructuraLinealBloque2_1FacingX").removeClass("p15_correccion_FFPP_multiplicador");
							$("#p15_EstructuraLinealBloque2_1_2FacingX").attr("style", "display:inline-block");
							$("#p15_linealImagenLLaveFacingXDiv").attr("style", "display:inline-block");

							//Se quita la clase para que la fecha se vea alineada con facing y capacidad porque ahora no la necesitamos
							$("#p15_tb_fechaFacingX").removeClass("p15_tb_fechaFacingXCajasExpositoras");

							/*
							$("#p15_EstructuraLinealBloque2_1FacingX").removeClass("p15_correccion_FFPP_multiplicador");

							$("#p15_linealImagenLLaveFacingXDiv").attr("style", "display:none");
							$("#p15_EstructuraLinealBloque2_1_2FacingX").attr("style", "display:none");
							$("#p15_lbl_linealImcFacingXDiv").attr("style", "display:none");
							$("#p15_fld_linealImcFacingXDiv").attr("style", "display:none");

							//Para que la fecha se vea alineada con facing y capacidad
							$("#p15_tb_fechaFacingX").addClass("p15_tb_fechaFacingXCajasExpositoras");
							 */
						}
					}

				}

				$(".p15_EstructuraLinealBloque2FacingX").attr("style", "display:inline-block");
				$(".p15_EstructuraLinealBloque2").attr("style", "display:none");
			}else{
				if (data.planogramaVigente.capacidadMaxLineal != null){
					$("#p15_fld_linealCapacidad").val(data.planogramaVigente.capacidadMaxLineal).formatNumber();
				}
				if (data.planogramaVigente.stockMinComerLineal != null){
					$("#p15_fld_linealFacing").val(data.planogramaVigente.stockMinComerLineal).formatNumber();
					//$("#p15_fld_linealFacing").val(data.stockFinalMinimo.facingCentro).formatNumber();
				}
				if (data.planogramaVigente.fechaGenLinealPantalla != null){
					$("#p15_fld_linealFecha").val(data.planogramaVigente.fechaGenLinealPantalla);
				}
				$(".p15_EstructuraLinealBloque2FacingX").attr("style", "display:none");
				$(".p15_EstructuraLinealBloque2").attr("style", "display:inline-block");				
			}
			//$(".p15_planogramasEstructuraMontaje2Field").attr("style", "display:inline-block");
			$(".p15_EstructuraLinealBloque3").attr("style", "display:none;");
		}

		if (data.flgFacing == 'S' && data.flgFacingCapacidad != 'S') { //Facing puro
			if (data.planogramaVigente.capacidadMaxLineal != null){
				$("#p15_fld_linealFacingPuro").val(data.planogramaVigente.stockMinComerLineal).formatNumber();
				//$("#p15_fld_linealFacingPuro").val(data.stockFinalMinimo.facingCentro).formatNumber();
			}
			if (data.planogramaVigente.multiplicadorFac != null){
				$("#p15_fld_linealMultiplicadorFacingPuro").val(data.planogramaVigente.multiplicadorFac);
			}
			if (data.planogramaVigente.imc != null){
				$("#p15_fld_linealImcFacingPuro").val(data.planogramaVigente.imc);
			}
			if (data.stockFinalMinimo.facingPrevio != null){
				$("#p15_fld_linealFacingPrevio").val(data.stockFinalMinimo.facingPrevio).formatNumber();
			}
			if (data.planogramaVigente.fechaGenLinealPantalla != null){
				$("#p15_fld_linealFechaBloque3").val(data.planogramaVigente.fechaGenLinealPantalla);
			}

			$(".p15_EstructuraLinealBloque2").attr("style", "display:none;");
			$(".p15_EstructuraLinealBloque2FacingX").attr("style", "display:none;");
			$(".p15_planogramasEstructuraMontaje2Field").attr("style", "display:none;");

			//Petición 54823. En las referencias de FAcing solo mostrar el facing Previo si el facing esta a 0
			if (data.planogramaVigente.stockMinComerLineal != '0') {
				$("#p15_fld_linealFacingPrevio").hide();
				$("#p15_lbl_linealFacingPrevio").hide();		
			} else {
				$("#p15_fld_linealFacingPrevio").show();
				$("#p15_lbl_linealFacingPrevio").show();
			}

			//Control de multiplicador para FFPP
			if (data.esFFPP){
				$("#p15_EstructuraLinealBloque3_1FacingPuro").addClass("p15_correccion_FFPP_multiplicador");
				$("#p15_linealMultiplicadorFacingPuroDiv").attr("style", "display:none");
				$("#p15_linealImagenLLaveFacingPuroDiv").attr("style", "display:none");
			}else{
				$("#p15_EstructuraLinealBloque3_1FacingPuro").removeClass("p15_correccion_FFPP_multiplicador");
				$("#p15_linealMultiplicadorFacingPuroDiv").attr("style", "display:inline-block");
				$("#p15_linealImagenLLaveFacingPuroDiv").attr("style", "display:inline-block");
			}

			$(".p15_EstructuraLinealBloque3").attr("style", "display:inline-block");
		}



		//Por la incidencia 284: Cuando se muestra el mensaje de pedido no activo
		//el montaje adicional 1 y 2 se muestran vacios. Por eso, sólo se rellenan los datos de montaje adicional 1 y 2
		//cuando el booleanMostrarMensajeNoActivo es false
		var pedir = $("#p13_fld_refActiva").val();
		var mapaHoy = $("#p13_fld_mapaHoy").val();
		var numeroPedidosOtroDia = $("#p13_numeroPedidosOtroDia").val();
		var booleanMostrarMensajeNoActivo = (pedir == null || pedir == 'N' || mapaHoy == null || (mapaHoy == 'N' && numeroPedidosOtroDia == 0));
		if ( booleanMostrarMensajeNoActivo == false ){
			if (null != data.planogramaVigente.fechaGenMontaje1){
				if (data.planogramaVigente.capacidadMontaje1 != null){
					$("#p15_fld_montaje1Capacidad").val(data.planogramaVigente.capacidadMontaje1).formatNumber();
				}
				if (data.planogramaVigente.facingMontaje1 != null){
					$("#p15_fld_montaje1Facing").val(data.planogramaVigente.facingMontaje1).formatNumber();
				}
				if (data.planogramaVigente.flgCabeceraMontaje1 != null && (data.planogramaVigente.flgCabeceraMontaje1 == 'S' || data.planogramaVigente.flgCabeceraMontaje1 == 'X')){
					$("#p15_chk_montaje1Cabecera").attr("checked", true);
				}
				if (data.planogramaVigente.flgOfertaMontaje1 != null && data.planogramaVigente.flgOfertaMontaje1 == 'S'){
					$("#p15_chk_montaje1Oferta").attr("checked", true);
				}
				if (data.planogramaVigente.flgCampanaMontaje1 != null && data.planogramaVigente.flgCampanaMontaje1 == 'S'){
					$("#p15_chk_montaje1Campana").attr("checked", true);
				}

				if (null != data.planogramaVigente.fechaGenMontaje2){
					if (data.planogramaVigente.capacidadMontaje2 != null){
						$("#p15_fld_montaje2Capacidad").val(data.planogramaVigente.capacidadMontaje2).formatNumber();
					}
					if (data.planogramaVigente.facingMontaje2 != null){
						$("#p15_fld_montaje2Facing").val(data.planogramaVigente.facingMontaje2).formatNumber();
					}
					if (data.planogramaVigente.flgCabeceraMontaje2 != null && (data.planogramaVigente.flgCabeceraMontaje2 == 'S' || data.planogramaVigente.flgCabeceraMontaje2 == 'X')){
						$("#p15_chk_montaje2Cabecera").attr("checked", true);
					}
					if (data.planogramaVigente.flgOfertaMontaje2 != null && data.planogramaVigente.flgOfertaMontaje2 == 'S'){
						$("#p15_chk_montaje2Oferta").attr("checked", true);
					}
					if (data.planogramaVigente.flgCampanaMontaje2 != null && data.planogramaVigente.flgCampanaMontaje2 == 'S'){
						$("#p15_chk_montaje2Campana").attr("checked", true);
					}
				} else if (null != data.planogramaVigente.fechaGenCabecera) {
					if (data.planogramaVigente.capacidadMaxCabecera != null){
						$("#p15_fld_montaje2Capacidad").val(data.planogramaVigente.capacidadMaxCabecera).formatNumber();
					}
					if (data.planogramaVigente.stockMinComerCabecera != null){
						$("#p15_fld_montaje2Facing").val(data.planogramaVigente.stockMinComerCabecera).formatNumber();
					}
				}
			} else if (null != data.planogramaVigente.fechaGenCabecera){
				if (data.planogramaVigente.capacidadMaxCabecera != null){
					$("#p15_fld_montaje1Capacidad").val(data.planogramaVigente.capacidadMaxCabecera).formatNumber();
				}
				if (data.planogramaVigente.stockMinComerCabecera != null){
					$("#p15_fld_montaje1Facing").val(data.planogramaVigente.stockMinComerCabecera).formatNumber();
				}
			}
		}

		//El multiplicador Facing de momento va vacÃ­o
		$("#p15_fld_multiplicadorFacing").val("").formatNumber();

		if (data.planogramaVigente.recalculado != null && data.planogramaVigente.recalculado == 'SI')
		{
			//En este caso se debe mostrar el mensaje de que se ha recalculado la capacidad y el facing.
			var mensajeCapFormateado = mensajeCapSFM.replace("#capacidadMaxLineal#", data.planogramaVigente.capacidadMaxLinealRecal);
			mensajeCapFormateado = mensajeCapFormateado.replace("#stockMinComerLineal#", data.planogramaVigente.stockMinComerLinealRecal);
			$("#p15_planogramasEstructuraLinealMensajeTexto2").text(mensajeCapFormateado);
			$("#p15_planogramasEstructuraLinealMensaje").attr("style", "display:inline-block");
		} else if (data.flgFacing == 'S' && data.flgFacingCapacidad != 'S' && data.stockFinalMinimo.facingCentroSIA!=null && data.stockFinalMinimo.facingCentroSIA!=data.stockFinalMinimo.facingCentro){
			var mensajeFacingFormateado = mensajeFacing.replace("#facing#", $.formatNumber(data.stockFinalMinimo.facingCentroSIA,{format:"0.###",locale:"es"}));
			$("#p15_planogramasEstructuraLinealMensajeTexto2").text(mensajeFacingFormateado);
			$("#p15_planogramasEstructuraLinealMensaje").attr("style", "display:inline-block");
		} else if (data.flgFacingCapacidad == 'S' && data.stockFinalMinimo.facingCentroSIA!=null && data.stockFinalMinimo.facingCentroSIA!=data.stockFinalMinimo.facingCentro){
			var mensajeFacingCapacidadFormateado = mensajeFacingCapacidad.replace("#facing#", $.formatNumber(data.stockFinalMinimo.facingCentroSIA,{format:"0.###",locale:"es"}));
			mensajeFacingCapacidadFormateado = mensajeFacingCapacidadFormateado.replace("#capacidadLineal#", $.formatNumber(data.stockFinalMinimo.capacidadSIA,{format:"0.###",locale:"es"}));
			$("#p15_planogramasEstructuraLinealMensajeTexto2").text(mensajeFacingCapacidadFormateado);
			$("#p15_planogramasEstructuraLinealMensaje").attr("style", "display:inline-block");
		}
		else
		{
			$("#p15_planogramasEstructuraLinealMensaje").attr("style", "display:none;");
		}
	} else {
		$("#p15_planogramasEstructuraLinealMensaje").attr("style", "display:none;");

		$(".p15_EstructuraLinealBloque2FacingX").attr("style", "display:none;");

		if (data.flgFacing == 'S' && data.flgFacingCapacidad != 'S') { //Facing puro Pet. 55464
			//Control de multiplicador para FFPP
			if (data.esFFPP){
				$("#p15_linealMultiplicadorFacingPuroDiv").attr("style", "display:none");
				$("#p15_linealImagenLLaveFacingPuroDiv").attr("style", "display:none");
			}else{
				$("#p15_linealMultiplicadorFacingPuroDiv").attr("style", "display:inline-block");
				$("#p15_linealImagenLLaveFacingPuroDiv").attr("style", "display:inline-block");
			}
			$(".p15_EstructuraLinealBloque3").attr("style", "display:inline-block;");
			$(".p15_planogramasEstructuraMontaje2Field").attr("style", "display:none");
			$(".p15_EstructuraLinealBloque2").attr("style", "display:none;");
		} else {
			$(".p15_EstructuraLinealBloque3").attr("style", "display:none;"); 
			//$(".p15_planogramasEstructuraMontaje2Field").attr("style", "display:inline-block");	
			$(".p15_EstructuraLinealBloque2").attr("style", "display:inline-block;");
		}
	}
	//Si es de tipo P vPlanogramaTipoP no será nulo y tendrá tanto facingAlto como facingAncho.
	//Estos valores vienen ya del controlador, por lo que solo hay que controlar que el objeto
	//vPlanogramaTipoP no sea nulo para rellenar los datos y mostrar el div.
	//if(data.vPlanogramaTipoP != null){
	if(data.vPlanogramaTipoP != null && data.vPlanogramaTipoP.facingAlto != null &&  data.vPlanogramaTipoP.facingAlto != 0 && data.vPlanogramaTipoP.facingAncho != null &&  data.vPlanogramaTipoP.facingAncho != 0){
		$("#p15_lbl_desgloseFacingValorAncho").text(data.vPlanogramaTipoP.facingAncho);
		$("#p15_lbl_desgloseFacingValorAlto").text(data.vPlanogramaTipoP.facingAlto);
		$("#p15_lbl_desgloseFacingValorMultiplicador").text(data.planogramaVigente.multiplicadorFac);

		//Si es ffpp solo mostramos el ancho y la imagen de la caja.
		var titulo;
		if(data.esFFPP){ //Es FFPP
			titulo = $("#p15_lbl_desgloseFacingTitle").text();
			titulo = titulo.replace('{0}',tituloDesgloseFacingFFPP);
			$("#p15_lbl_desgloseFacingTitle").text(titulo);

			//Ocultamos el alto
			$("#p15_lbl_desgloseFacingAlto").css("visibility","hidden");
			$("#p15_lbl_desgloseFacingValorAlto").css("visibility","hidden");

			//Ocultamos el multiplicador
			$("#p15_lbl_desgloseFacingMultiplicador").css("visibility","hidden");
			$("#p15_lbl_desgloseFacingValorMultiplicador").css("visibility","hidden");

			//Mostramos la imagen correspondiente
			$("#p15_img_desgloseFacingPale").hide();
			$("#p15_img_desgloseFacingCajaExpositora").hide();
			$("#p15_img_desgloseFacingCaja").show();


			//Ajustamos el css del ancho para que coincida con el espacio de la imagen.
			$("#p15_desgloseFacingAncho").addClass("p15_desgloseFacingAnchoCaja");
			$("#p15_desgloseFacingAncho").removeClass("p15_desgloseFacingAnchoPale");
			$("#p15_desgloseFacingAncho").removeClass("p15_desgloseFacingAnchoCajaExpositora");

			//Quitamos la clase para que se vea bien el alto
			$("#p15_desgloseFacingAlto").removeClass("p15_desgloseFacingAltoCajaExpositora");

		}else{

			//MISUMI-100 -- Se ha preguntado a Maria si hay que quitarlo o sustituir la condición por --if(data.vPlanogramaTipoP.esCajaExp != "S"){--
			if(data.vPlanogramaTipoP.esCajaExp == "S"){ //Es Caja Expositora

				titulo = $("#p15_lbl_desgloseFacingTitle").text();
				titulo = titulo.replace('{0}',tituloDesgloseFacingNoFFPPCajas);
				$("#p15_lbl_desgloseFacingTitle").text(titulo);

				//Mostramos el alto
				$("#p15_lbl_desgloseFacingAlto").css("visibility","visible");
				$("#p15_lbl_desgloseFacingValorAlto").css("visibility","visible");

				//Mostramos el multiplicador
				$("#p15_lbl_desgloseFacingMultiplicador").css("visibility","hidden");
				$("#p15_lbl_desgloseFacingValorMultiplicador").css("visibility","hidden");

				//Mostramos la imagen correspondiente
				$("#p15_img_desgloseFacingPale").hide();
				$("#p15_img_desgloseFacingCajaExpositora").show();
				$("#p15_img_desgloseFacingCaja").hide();


				//Ajustamos el css del ancho para que coincida con el espacio de la imagen.
				$("#p15_desgloseFacingAncho").addClass("p15_desgloseFacingAnchoCajaExpositora");
				$("#p15_desgloseFacingAncho").removeClass("p15_desgloseFacingAnchoPale");
				$("#p15_desgloseFacingAncho").removeClass("p15_desgloseFacingAnchoCaja");

				//Añadimos la clase para que se vea bien el alto
				$("#p15_desgloseFacingAlto").addClass("p15_desgloseFacingAltoCajaExpositora");				

			}else{ // Resto (Unitaria...)

				titulo = $("#p15_lbl_desgloseFacingTitle").text();
				titulo = titulo.replace('{0}',tituloDesgloseFacingNoFFPP);
				$("#p15_lbl_desgloseFacingTitle").text(titulo);

				//Mostramos el alto
				$("#p15_lbl_desgloseFacingAlto").css("visibility","visible");
				$("#p15_lbl_desgloseFacingValorAlto").css("visibility","visible");

				//Mostramos el multiplicador
				$("#p15_lbl_desgloseFacingMultiplicador").css("visibility","visible");
				$("#p15_lbl_desgloseFacingValorMultiplicador").css("visibility","visible");
				/*
				$("#p15_lbl_desgloseFacingMultiplicador").css("visibility","hidden");
				$("#p15_lbl_desgloseFacingValorMultiplicador").css("visibility","hidden");
				 */

				//Mostramos la imagen correspondiente
				$("#p15_img_desgloseFacingPale").show();
				$("#p15_img_desgloseFacingCajaExpositora").hide();
				$("#p15_img_desgloseFacingCaja").hide();

				//Ajustamos el css del ancho para que coincida con el espacio de la imagen.
				$("#p15_desgloseFacingAncho").addClass("p15_desgloseFacingAnchoPale");
				$("#p15_desgloseFacingAncho").removeClass("p15_desgloseFacingAnchoCajaExpositora");
				$("#p15_desgloseFacingAncho").removeClass("p15_desgloseFacingAnchoCaja");

				//Quitamos la clase para que se vea bien el alto
				$("#p15_desgloseFacingAlto").removeClass("p15_desgloseFacingAltoCajaExpositora");
			}

		}

		$("#p15_EstructuraLinealBloqueDesgloseFacing").show();
	}else{
		$("#p15_EstructuraLinealBloqueDesgloseFacing").hide();
	}
}

function updateResultadosICVentasMedia (data){

	if (data.historicoVentaMedia != null ){
		if (data.historicoVentaMedia.tipoVenta != null ){
			//if (data.historicoVentaMedia.tipoVenta == "K"){
			//$("#p15_ventasMediaTipoVenta").text(KG);
			//}else{
			//$("#p15_ventasMediaTipoVenta").text(bandejas);
			//}
		}

		if (data.historicoVentaMedia.tarifa != null ){
			$("#p15_ventasMediaTdTarifa").text(data.historicoVentaMedia.tarifa).formatNumber();
		}else{
			$("#p15_ventasMediaTdTarifa").text("0").formatNumber();
		}
		if (data.historicoVentaMedia.competencia != null ){
			$("#p15_ventasMediaTdCompetencia").text(data.historicoVentaMedia.competencia).formatNumber();
		}else{
			$("#p15_ventasMediaTdCompetencia").text("0").formatNumber();
		}
		if (data.historicoVentaMedia.oferta != null ){
			$("#p15_ventasMediaTdOferta").text(data.historicoVentaMedia.oferta).formatNumber();
		}else{
			$("#p15_ventasMediaTdOferta").text("0").formatNumber();
		}
		if (data.historicoVentaMedia.anticipada != null ){
			$("#p15_ventasMediaTdAnticipada").text(data.historicoVentaMedia.anticipada).formatNumber();
		}else{
			$("#p15_ventasMediaTdAnticipada").text("0").formatNumber();
		}
		if (data.historicoVentaMedia.media != null ){
			$("#p15_ventasMediaTdMedia").text(data.historicoVentaMedia.media).formatNumber();
		}else{
			$("#p15_ventasMediaTdMedia").text("0").formatNumber();
		}
	}else{
		$("#p15_ventasMediaTdTarifa").text("0").formatNumber();
		$("#p15_ventasMediaTdCompetencia").text("0").formatNumber();
		$("#p15_ventasMediaTdOferta").text("0").formatNumber();
		$("#p15_ventasMediaTdAnticipada").text("0").formatNumber();
		$("#p15_ventasMediaTdMedia").text("0").formatNumber();
	}
}

function updateResultadosICVentasUltimoMes (data){

	var titulo = null;
	//Fijamos el título a Ventas último mes
	//if (null != data.promocional){
	//	if (data.promocional == "MP"){
	//		titulo = tituloVentasMediasMP;
	//	} else {
	//		titulo = tituloVentasMediasP;
	//	}
	//} else {
	titulo = tituloVentasMedias;
	//}

	$("#p15_ventasUltimoMesLegend").html(titulo);

	if (data.listaHistoricoVentaUltimoMes != null){
		escribirVentaUltimoMes("#p15_ventasUltimoMes1TdL", "#p15_ventasUltimoMesFecha1TdL", data.listaHistoricoVentaUltimoMes[34].anticipada, data.listaHistoricoVentaUltimoMes[34].unidades, data.listaHistoricoVentaUltimoMes[34].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd1",  data.listaHistoricoVentaUltimoMes[34].fechaDia, data.listaHistoricoVentaUltimoMes[34].esFestivo, data.listaHistoricoVentaUltimoMes[34].esOferta);    
		escribirVentaUltimoMes("#p15_ventasUltimoMes1TdM", "#p15_ventasUltimoMesFecha1TdM", data.listaHistoricoVentaUltimoMes[33].anticipada, data.listaHistoricoVentaUltimoMes[33].unidades, data.listaHistoricoVentaUltimoMes[33].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd2",  data.listaHistoricoVentaUltimoMes[33].fechaDia, data.listaHistoricoVentaUltimoMes[33].esFestivo, data.listaHistoricoVentaUltimoMes[33].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes1TdX", "#p15_ventasUltimoMesFecha1TdX", data.listaHistoricoVentaUltimoMes[32].anticipada, data.listaHistoricoVentaUltimoMes[32].unidades, data.listaHistoricoVentaUltimoMes[32].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd3",  data.listaHistoricoVentaUltimoMes[32].fechaDia, data.listaHistoricoVentaUltimoMes[32].esFestivo, data.listaHistoricoVentaUltimoMes[32].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes1TdJ", "#p15_ventasUltimoMesFecha1TdJ", data.listaHistoricoVentaUltimoMes[31].anticipada, data.listaHistoricoVentaUltimoMes[31].unidades, data.listaHistoricoVentaUltimoMes[31].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd4",  data.listaHistoricoVentaUltimoMes[31].fechaDia, data.listaHistoricoVentaUltimoMes[31].esFestivo, data.listaHistoricoVentaUltimoMes[31].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes1TdV", "#p15_ventasUltimoMesFecha1TdV", data.listaHistoricoVentaUltimoMes[30].anticipada, data.listaHistoricoVentaUltimoMes[30].unidades, data.listaHistoricoVentaUltimoMes[30].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd5",  data.listaHistoricoVentaUltimoMes[30].fechaDia, data.listaHistoricoVentaUltimoMes[30].esFestivo, data.listaHistoricoVentaUltimoMes[30].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes1TdS", "#p15_ventasUltimoMesFecha1TdS", data.listaHistoricoVentaUltimoMes[29].anticipada, data.listaHistoricoVentaUltimoMes[29].unidades, data.listaHistoricoVentaUltimoMes[29].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd6",  data.listaHistoricoVentaUltimoMes[29].fechaDia, data.listaHistoricoVentaUltimoMes[29].esFestivo, data.listaHistoricoVentaUltimoMes[29].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes1TdD", "#p15_ventasUltimoMesFecha1TdD", data.listaHistoricoVentaUltimoMes[28].anticipada, data.listaHistoricoVentaUltimoMes[28].unidades, data.listaHistoricoVentaUltimoMes[28].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd7",  data.listaHistoricoVentaUltimoMes[28].fechaDia, data.listaHistoricoVentaUltimoMes[28].esFestivo, data.listaHistoricoVentaUltimoMes[28].esOferta);

		escribirVentaUltimoMes("#p15_ventasUltimoMes2TdL", "#p15_ventasUltimoMesFecha2TdL", data.listaHistoricoVentaUltimoMes[27].anticipada, data.listaHistoricoVentaUltimoMes[27].unidades, data.listaHistoricoVentaUltimoMes[27].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd8",  data.listaHistoricoVentaUltimoMes[27].fechaDia, data.listaHistoricoVentaUltimoMes[27].esFestivo, data.listaHistoricoVentaUltimoMes[27].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes2TdM", "#p15_ventasUltimoMesFecha2TdM", data.listaHistoricoVentaUltimoMes[26].anticipada, data.listaHistoricoVentaUltimoMes[26].unidades, data.listaHistoricoVentaUltimoMes[26].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd9",  data.listaHistoricoVentaUltimoMes[26].fechaDia, data.listaHistoricoVentaUltimoMes[26].esFestivo, data.listaHistoricoVentaUltimoMes[26].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes2TdX", "#p15_ventasUltimoMesFecha2TdX", data.listaHistoricoVentaUltimoMes[25].anticipada, data.listaHistoricoVentaUltimoMes[25].unidades, data.listaHistoricoVentaUltimoMes[25].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd10", data.listaHistoricoVentaUltimoMes[25].fechaDia, data.listaHistoricoVentaUltimoMes[25].esFestivo, data.listaHistoricoVentaUltimoMes[25].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes2TdJ", "#p15_ventasUltimoMesFecha2TdJ", data.listaHistoricoVentaUltimoMes[24].anticipada, data.listaHistoricoVentaUltimoMes[24].unidades, data.listaHistoricoVentaUltimoMes[24].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd11", data.listaHistoricoVentaUltimoMes[24].fechaDia, data.listaHistoricoVentaUltimoMes[24].esFestivo, data.listaHistoricoVentaUltimoMes[24].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes2TdV", "#p15_ventasUltimoMesFecha2TdV", data.listaHistoricoVentaUltimoMes[23].anticipada, data.listaHistoricoVentaUltimoMes[23].unidades, data.listaHistoricoVentaUltimoMes[23].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd12", data.listaHistoricoVentaUltimoMes[23].fechaDia, data.listaHistoricoVentaUltimoMes[23].esFestivo, data.listaHistoricoVentaUltimoMes[23].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes2TdS", "#p15_ventasUltimoMesFecha2TdS", data.listaHistoricoVentaUltimoMes[22].anticipada, data.listaHistoricoVentaUltimoMes[22].unidades, data.listaHistoricoVentaUltimoMes[22].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd13", data.listaHistoricoVentaUltimoMes[22].fechaDia, data.listaHistoricoVentaUltimoMes[22].esFestivo, data.listaHistoricoVentaUltimoMes[22].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes2TdD", "#p15_ventasUltimoMesFecha2TdD", data.listaHistoricoVentaUltimoMes[21].anticipada, data.listaHistoricoVentaUltimoMes[21].unidades, data.listaHistoricoVentaUltimoMes[21].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd14", data.listaHistoricoVentaUltimoMes[21].fechaDia, data.listaHistoricoVentaUltimoMes[21].esFestivo, data.listaHistoricoVentaUltimoMes[21].esOferta);

		escribirVentaUltimoMes("#p15_ventasUltimoMes3TdL", "#p15_ventasUltimoMesFecha3TdL", data.listaHistoricoVentaUltimoMes[20].anticipada, data.listaHistoricoVentaUltimoMes[20].unidades, data.listaHistoricoVentaUltimoMes[20].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd15", data.listaHistoricoVentaUltimoMes[20].fechaDia, data.listaHistoricoVentaUltimoMes[20].esFestivo, data.listaHistoricoVentaUltimoMes[20].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes3TdM", "#p15_ventasUltimoMesFecha3TdM", data.listaHistoricoVentaUltimoMes[19].anticipada, data.listaHistoricoVentaUltimoMes[19].unidades, data.listaHistoricoVentaUltimoMes[19].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd16", data.listaHistoricoVentaUltimoMes[19].fechaDia, data.listaHistoricoVentaUltimoMes[19].esFestivo, data.listaHistoricoVentaUltimoMes[19].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes3TdX", "#p15_ventasUltimoMesFecha3TdX", data.listaHistoricoVentaUltimoMes[18].anticipada, data.listaHistoricoVentaUltimoMes[18].unidades, data.listaHistoricoVentaUltimoMes[18].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd17", data.listaHistoricoVentaUltimoMes[18].fechaDia, data.listaHistoricoVentaUltimoMes[18].esFestivo, data.listaHistoricoVentaUltimoMes[18].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes3TdJ", "#p15_ventasUltimoMesFecha3TdJ", data.listaHistoricoVentaUltimoMes[17].anticipada, data.listaHistoricoVentaUltimoMes[17].unidades, data.listaHistoricoVentaUltimoMes[17].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd18", data.listaHistoricoVentaUltimoMes[17].fechaDia, data.listaHistoricoVentaUltimoMes[17].esFestivo, data.listaHistoricoVentaUltimoMes[17].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes3TdV", "#p15_ventasUltimoMesFecha3TdV", data.listaHistoricoVentaUltimoMes[16].anticipada, data.listaHistoricoVentaUltimoMes[16].unidades, data.listaHistoricoVentaUltimoMes[16].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd19", data.listaHistoricoVentaUltimoMes[16].fechaDia, data.listaHistoricoVentaUltimoMes[16].esFestivo, data.listaHistoricoVentaUltimoMes[16].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes3TdS", "#p15_ventasUltimoMesFecha3TdS", data.listaHistoricoVentaUltimoMes[15].anticipada, data.listaHistoricoVentaUltimoMes[15].unidades, data.listaHistoricoVentaUltimoMes[15].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd20", data.listaHistoricoVentaUltimoMes[15].fechaDia, data.listaHistoricoVentaUltimoMes[15].esFestivo, data.listaHistoricoVentaUltimoMes[15].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes3TdD", "#p15_ventasUltimoMesFecha3TdD", data.listaHistoricoVentaUltimoMes[14].anticipada, data.listaHistoricoVentaUltimoMes[14].unidades, data.listaHistoricoVentaUltimoMes[14].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd21", data.listaHistoricoVentaUltimoMes[14].fechaDia, data.listaHistoricoVentaUltimoMes[14].esFestivo, data.listaHistoricoVentaUltimoMes[14].esOferta);

		escribirVentaUltimoMes("#p15_ventasUltimoMes4TdL", "#p15_ventasUltimoMesFecha4TdL", data.listaHistoricoVentaUltimoMes[13].anticipada, data.listaHistoricoVentaUltimoMes[13].unidades, data.listaHistoricoVentaUltimoMes[13].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd22", data.listaHistoricoVentaUltimoMes[13].fechaDia, data.listaHistoricoVentaUltimoMes[13].esFestivo, data.listaHistoricoVentaUltimoMes[13].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes4TdM", "#p15_ventasUltimoMesFecha4TdM", data.listaHistoricoVentaUltimoMes[12].anticipada, data.listaHistoricoVentaUltimoMes[12].unidades, data.listaHistoricoVentaUltimoMes[12].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd23", data.listaHistoricoVentaUltimoMes[12].fechaDia, data.listaHistoricoVentaUltimoMes[12].esFestivo, data.listaHistoricoVentaUltimoMes[12].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes4TdX", "#p15_ventasUltimoMesFecha4TdX", data.listaHistoricoVentaUltimoMes[11].anticipada, data.listaHistoricoVentaUltimoMes[11].unidades, data.listaHistoricoVentaUltimoMes[11].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd24", data.listaHistoricoVentaUltimoMes[11].fechaDia, data.listaHistoricoVentaUltimoMes[11].esFestivo, data.listaHistoricoVentaUltimoMes[11].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes4TdJ", "#p15_ventasUltimoMesFecha4TdJ", data.listaHistoricoVentaUltimoMes[10].anticipada, data.listaHistoricoVentaUltimoMes[10].unidades, data.listaHistoricoVentaUltimoMes[10].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd25", data.listaHistoricoVentaUltimoMes[10].fechaDia, data.listaHistoricoVentaUltimoMes[10].esFestivo, data.listaHistoricoVentaUltimoMes[10].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes4TdV", "#p15_ventasUltimoMesFecha4TdV", data.listaHistoricoVentaUltimoMes[9].anticipada, data.listaHistoricoVentaUltimoMes[9].unidades, data.listaHistoricoVentaUltimoMes[9].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd26", data.listaHistoricoVentaUltimoMes[9].fechaDia, data.listaHistoricoVentaUltimoMes[9].esFestivo, data.listaHistoricoVentaUltimoMes[9].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes4TdS", "#p15_ventasUltimoMesFecha4TdS", data.listaHistoricoVentaUltimoMes[8].anticipada, data.listaHistoricoVentaUltimoMes[8].unidades, data.listaHistoricoVentaUltimoMes[8].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd27", data.listaHistoricoVentaUltimoMes[8].fechaDia, data.listaHistoricoVentaUltimoMes[8].esFestivo, data.listaHistoricoVentaUltimoMes[8].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes4TdD", "#p15_ventasUltimoMesFecha4TdD", data.listaHistoricoVentaUltimoMes[7].anticipada, data.listaHistoricoVentaUltimoMes[7].unidades, data.listaHistoricoVentaUltimoMes[7].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd28", data.listaHistoricoVentaUltimoMes[7].fechaDia, data.listaHistoricoVentaUltimoMes[7].esFestivo, data.listaHistoricoVentaUltimoMes[7].esOferta);

		escribirVentaUltimoMes("#p15_ventasUltimoMes5TdL", "#p15_ventasUltimoMesFecha5TdL", data.listaHistoricoVentaUltimoMes[6].anticipada, data.listaHistoricoVentaUltimoMes[6].unidades, data.listaHistoricoVentaUltimoMes[6].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd29", data.listaHistoricoVentaUltimoMes[6].fechaDia, data.listaHistoricoVentaUltimoMes[6].esFestivo, data.listaHistoricoVentaUltimoMes[6].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes5TdM", "#p15_ventasUltimoMesFecha5TdM", data.listaHistoricoVentaUltimoMes[5].anticipada, data.listaHistoricoVentaUltimoMes[5].unidades, data.listaHistoricoVentaUltimoMes[5].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd30", data.listaHistoricoVentaUltimoMes[5].fechaDia, data.listaHistoricoVentaUltimoMes[5].esFestivo, data.listaHistoricoVentaUltimoMes[5].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes5TdX", "#p15_ventasUltimoMesFecha5TdX", data.listaHistoricoVentaUltimoMes[4].anticipada, data.listaHistoricoVentaUltimoMes[4].unidades, data.listaHistoricoVentaUltimoMes[4].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd31", data.listaHistoricoVentaUltimoMes[4].fechaDia, data.listaHistoricoVentaUltimoMes[4].esFestivo, data.listaHistoricoVentaUltimoMes[4].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes5TdJ", "#p15_ventasUltimoMesFecha5TdJ", data.listaHistoricoVentaUltimoMes[3].anticipada, data.listaHistoricoVentaUltimoMes[3].unidades, data.listaHistoricoVentaUltimoMes[3].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd32", data.listaHistoricoVentaUltimoMes[3].fechaDia, data.listaHistoricoVentaUltimoMes[3].esFestivo, data.listaHistoricoVentaUltimoMes[3].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes5TdV", "#p15_ventasUltimoMesFecha5TdV", data.listaHistoricoVentaUltimoMes[2].anticipada, data.listaHistoricoVentaUltimoMes[2].unidades, data.listaHistoricoVentaUltimoMes[2].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd33", data.listaHistoricoVentaUltimoMes[2].fechaDia, data.listaHistoricoVentaUltimoMes[2].esFestivo, data.listaHistoricoVentaUltimoMes[2].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes5TdS", "#p15_ventasUltimoMesFecha5TdS", data.listaHistoricoVentaUltimoMes[1].anticipada, data.listaHistoricoVentaUltimoMes[1].unidades, data.listaHistoricoVentaUltimoMes[1].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd34", data.listaHistoricoVentaUltimoMes[1].fechaDia, data.listaHistoricoVentaUltimoMes[1].esFestivo, data.listaHistoricoVentaUltimoMes[1].esOferta);
		escribirVentaUltimoMes("#p15_ventasUltimoMes5TdD", "#p15_ventasUltimoMesFecha5TdD", data.listaHistoricoVentaUltimoMes[0].anticipada, data.listaHistoricoVentaUltimoMes[0].unidades, data.listaHistoricoVentaUltimoMes[0].fechaVentaDDMMYYYY, "#p15_ventasUltimoMesDTd35", data.listaHistoricoVentaUltimoMes[0].fechaDia, data.listaHistoricoVentaUltimoMes[0].esFestivo, data.listaHistoricoVentaUltimoMes[0].esOferta);

		recalcularEnlacesPopupVentas();
	}
}

function escribirVentaUltimoMes(tdName, tdFechaName, anticipada, unidades, fechaVenta, tdFechaDiaName, diaMes, esFestivo, esOferta){
	$(tdFechaDiaName).text(diaMes);
	if (unidades == -99){
		$(tdName).html("<br/>");
		$(tdFechaName).val(" ");
		$(tdName).removeClass("p15_ventasUltimoMesTablaTd").removeClass("p15_ventasUltimoMesAnticipadaTablaTd").addClass("p15_ventasUltimoMesSinEnlaceTablaTd");
	}else if (fechaVenta == null || fechaVenta == "" || fechaVenta == "undefined"){
		$(tdName).text(unidades).formatNumber();
		$(tdFechaName).val(" ");
		$(tdName).removeClass("p15_ventasUltimoMesTablaTd").removeClass("p15_ventasUltimoMesAnticipadaTablaTd").addClass("p15_ventasUltimoMesSinEnlaceTablaTd");
	}else{ 
		$(tdName).text(unidades).formatNumber();
		$(tdFechaName).val(fechaVenta);
		if (anticipada == "SI")
			$(tdName).removeClass("p15_ventasUltimoMesSinEnlaceTablaTd").removeClass("p15_ventasUltimoMesTablaTd").addClass("p15_ventasUltimoMesAnticipadaTablaTd");
		else
			$(tdName).removeClass("p15_ventasUltimoMesSinEnlaceTablaTd").removeClass("p15_ventasUltimoMesAnticipadaTablaTd").addClass("p15_ventasUltimoMesTablaTd");
	}


	if (esFestivo == "T")
		$(tdFechaDiaName).addClass("partyDay").removeClass("ofertaDay");
	else if (esOferta == "T")
		$(tdFechaDiaName).addClass("ofertaDay").removeClass("partyDay");
	else
		$(tdFechaDiaName).removeClass("ofertaDay").removeClass("partyDay");

}

function validateReloadingIC(data){
	if (data.length == 0){

		return false;
	}else{
		return true;
	}
}

function resetResultadosIC(){
	resetResultadosICStockMinimo();
	resetResultadosICPlanogramas();
	resetResultadosICFacing();
	resetResultadosICVentas();
	resetResultadosICVentasUltimoMes();
}

function resetResultadosICStockMinimo(){
	$("#p15_stockFinalMinimoUnidadesTdS").text("");
	$("#p15_stockFinalMinimoDiasVentaTdS").text("");
	$("#p15_AreaStockFinalMinimo").attr("style", "display:none");

}

function resetResultadosICPlanogramas(){
	$("#p15_fld_descripcionPlanograma").val("");
	$("#p15_fld_tipoPlanograma").val("");
	$("#p15_fld_linealFacingFacingX").attr("disabled","disabled");
	$("#p15_fld_linealCapacidadFacingX").attr("disabled","disabled");
	$("#p15_ParrafoImplantacion").text("");
	$("#p15_ParrafoImplantacion").removeClass().addClass( "p15_ParrafoImplantacion" );
	$(".p15_EstructuraLinealBloque1").attr("style", "display:none");
	$("#p15_fld_linealCapacidad").val("");
	$("#p15_fld_linealFacing").val("");
	$("#p15_fld_linealFecha").val("");
	$("#p15_fld_linealMultiplicador").val("");
	$("#p15_fld_linealImc").val("");
	$("#p15_fld_linealCapacidadFacingX").val("");
	$("#p15_fld_linealFechaFacingX").val("");
	$("#p15_fld_linealFacingFacingX").val("");
	$("#p15_fld_linealMultiplicadorFacingX").val("");
	$("#p15_fld_linealImcFacingX").val("");
	$("#p15_fld_linealFacingPuro").val("");
	$("#p15_fld_linealMultiplicadorFacingPuro").val("");
	$("#p15_fld_linealImcFacingPuro").val("");
	$("#p15_fld_linealFacingPrevio").val("");;
	$("#p15_fld_linealFechaBloque3").val("");
	$("#p15_fld_montaje1Capacidad").val("");
	$("#p15_fld_montaje1Facing").val("");
	$("#p15_fld_montaje1Oferta").val("");
	$("#p15_fld_montaje1EspacioProm").val("");
	$("#p15_chk_montaje1Cabecera").attr("checked", false);
	$("#p15_chk_montaje1Oferta").attr("checked", false);
	$("#p15_chk_montaje1Campana").attr("checked", false);
	$("#p15_fld_montaje2Capacidad").val("");
	$("#p15_fld_montaje2Facing").val("");
	$("#p15_chk_montaje2Cabecera").attr("checked", false);
	$("#p15_chk_montaje2Oferta").attr("checked", false);
	$("#p15_chk_montaje2Campana").attr("checked", false);
	$("#p15_fld_multiplicadorFacing").val("");

	$("#p15_AreaPlanogramas").attr("style", "display:none");

	//Añadimos el título original del desglose facing
	$("#p15_lbl_desgloseFacingTitle").text(desgloseFacingTitle);
}

function resetResultadosICFacing(){
	$("#p15_ParrafoImplantacionFac").text("");
	$("#p15_ParrafoImplantacionFac").removeClass().addClass( "p15_ParrafoImplantacion" );
	$("#p15_facingCentroTdS").text("");
	$("#p15_facingPrevioTdS").text("");
	$("#p15_tr_facing_previo").attr("style", "display:none");
	$("#p15_facingMensaje").attr("style", "display:none;");
}

function resetResultadosICVentas(){
	//$("#p15_ventasMediaTipoVenta").text("");

	$("#p15_ventasMediaTdTarifa").text("");
	$("#p15_ventasMediaTdCompetencia").text("");
	$("#p15_ventasMediaTdOferta").text("");
	$("#p15_ventasMediaTdAnticipada").text("");
	$("#p15_ventasMediaTdMedia").text("");
}

function resetResultadosICVentasUltimoMes(){
	$("#p15_ventasUltimoMes1TdL").text("");
	$("#p15_ventasUltimoMes1TdM").text("");
	$("#p15_ventasUltimoMes1TdX").text("");
	$("#p15_ventasUltimoMes1TdJ").text("");
	$("#p15_ventasUltimoMes1TdV").text("");
	$("#p15_ventasUltimoMes1TdS").text("");
	$("#p15_ventasUltimoMes1TdD").text("");

	$("#p15_ventasUltimoMes2TdL").text("");
	$("#p15_ventasUltimoMes2TdM").text("");
	$("#p15_ventasUltimoMes2TdX").text("");
	$("#p15_ventasUltimoMes2TdJ").text("");
	$("#p15_ventasUltimoMes2TdV").text("");
	$("#p15_ventasUltimoMes2TdS").text("");
	$("#p15_ventasUltimoMes2TdD").text("");

	$("#p15_ventasUltimoMes3TdL").text("");
	$("#p15_ventasUltimoMes3TdM").text("");
	$("#p15_ventasUltimoMes3TdX").text("");
	$("#p15_ventasUltimoMes3TdJ").text("");
	$("#p15_ventasUltimoMes3TdV").text("");
	$("#p15_ventasUltimoMes3TdS").text("");
	$("#p15_ventasUltimoMes3TdD").text("");

	$("#p15_ventasUltimoMes4TdL").text("");
	$("#p15_ventasUltimoMes4TdM").text("");
	$("#p15_ventasUltimoMes4TdX").text("");
	$("#p15_ventasUltimoMes4TdJ").text("");
	$("#p15_ventasUltimoMes4TdV").text("");
	$("#p15_ventasUltimoMes4TdS").text("");
	$("#p15_ventasUltimoMes4TdD").text("");

	$("#p15_ventasUltimoMes5TdL").text("");
	$("#p15_ventasUltimoMes5TdM").text("");
	$("#p15_ventasUltimoMes5TdX").text("");
	$("#p15_ventasUltimoMes5TdJ").text("");
	$("#p15_ventasUltimoMes5TdV").text("");
	$("#p15_ventasUltimoMes5TdS").text("");
	$("#p15_ventasUltimoMes5TdD").text("");

	$("#p15_ventasUltimoMesFecha1TdL").val("");
	$("#p15_ventasUltimoMesFecha1TdM").val("");
	$("#p15_ventasUltimoMesFecha1TdX").val("");
	$("#p15_ventasUltimoMesFecha1TdJ").val("");
	$("#p15_ventasUltimoMesFecha1TdV").val("");
	$("#p15_ventasUltimoMesFecha1TdS").val("");
	$("#p15_ventasUltimoMesFecha1TdD").val("");

	$("#p15_ventasUltimoMesFecha2TdL").val("");
	$("#p15_ventasUltimoMesFecha2TdM").val("");
	$("#p15_ventasUltimoMesFecha2TdX").val("");
	$("#p15_ventasUltimoMesFecha2TdJ").val("");
	$("#p15_ventasUltimoMesFecha2TdV").val("");
	$("#p15_ventasUltimoMesFecha2TdS").val("");
	$("#p15_ventasUltimoMesFecha2TdD").val("");

	$("#p15_ventasUltimoMesFecha3TdL").val("");
	$("#p15_ventasUltimoMesFecha3TdM").val("");
	$("#p15_ventasUltimoMesFecha3TdX").val("");
	$("#p15_ventasUltimoMesFecha3TdJ").val("");
	$("#p15_ventasUltimoMesFecha3TdV").val("");
	$("#p15_ventasUltimoMesFecha3TdS").val("");
	$("#p15_ventasUltimoMesFecha3TdD").val("");

	$("#p15_ventasUltimoMesFecha4TdL").val("");
	$("#p15_ventasUltimoMesFecha4TdM").val("");
	$("#p15_ventasUltimoMesFecha4TdX").val("");
	$("#p15_ventasUltimoMesFecha4TdJ").val("");
	$("#p15_ventasUltimoMesFecha4TdV").val("");
	$("#p15_ventasUltimoMesFecha4TdS").val("");
	$("#p15_ventasUltimoMesFecha4TdD").val("");

	$("#p15_ventasUltimoMesFecha5TdL").val("");
	$("#p15_ventasUltimoMesFecha5TdM").val("");
	$("#p15_ventasUltimoMesFecha5TdX").val("");
	$("#p15_ventasUltimoMesFecha5TdJ").val("");
	$("#p15_ventasUltimoMesFecha5TdV").val("");
	$("#p15_ventasUltimoMesFecha5TdS").val("");
	$("#p15_ventasUltimoMesFecha5TdD").val("");

}

//Evento que se dispara cuando se pulsa el botón modificar.
function event_btn_modificar(){
	$("#p15_areaBtn").click(function(){
		modificarActualizarReferenciaPlanogramada();
	});
}

//Función que sirve para actualizar o modificar la referencia planogramada
function modificarActualizarReferenciaPlanogramada(){
	//Si se han informado la capacidad y el facing
	var validarFacCap = validarFacingCapacidad();
	if(validarFacCap == 'S'){
		var centro =  $("#centerId").val();
		var referencia = $("#p13_fld_referenciaEroski").val();
		var capacidadOrig = $("#p15_capacidadOrig").val();
		var facingOrig = $("#p15_facingOrig").val();
		var capacidadNueva = $("#p15_fld_linealCapacidadFacingX").val();
		var facingNuevo = $("#p15_fld_linealFacingFacingX").val();

		var planogramaVigente = new PlanogramaVigente(centro,referencia,capacidadNueva,facingNuevo,capacidadOrig,facingOrig);
		var objJson = $.toJSON(planogramaVigente);

		$.ajax({
			type : 'POST',
			url : './referenciasCentro/actualizarPlanogramaVigente.do',
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			data: objJson,
			cache : false,
			success : function(data) {	
				//Si se ha actualizado correctamente.
				if(data.msgError == '0'){
					actualizarModificarPlanograma(objJson);

					//Actualizar los datos originales
					$("#p15_fld_linealCapacidadFacingX").val(data.capacidadMaxLinealOrig);
					$("#p15_fld_linealFacingFacingX").val(data.stockMinComerLinealOrig);
					$("p15_capacidadOrig").val(data.capacidadMaxLinealOrig);
					$("#p15_facingOrig").val(data.stockMinComerLinealOrig);
				}else{
					//Si hay un error al actualizar.
					if(data.msgError == '1'){
						createAlert(noHaPodidoModificarsePlanograma,"ERROR");
					}//Si la capacidad o facing originales guardadas no coinciden con las tuyas. Esto quiere decir que otro usuario ha actualizado antes que tu.
					else if(data.msgError == '2'){
						//Actualizar los datos originales
						$("#p15_fld_linealCapacidadFacingX").val(data.capacidadMaxLinealOrig);
						$("#p15_fld_linealFacingFacingX").val(data.stockMinComerLinealOrig);
						$("p15_capacidadOrig").val(data.capacidadMaxLinealOrig);
						$("#p15_facingOrig").val(data.stockMinComerLinealOrig);

						//Actualizamos el multiplicador
						var multiplicador = $("#p15_fld_linealMultiplicadorFacingX").val();
						var facing = $("#p15_fld_linealFacingFacingX").val();
						$("#p15_fld_linealImcFacingX").val(facing*multiplicador);

						createAlert(valoresPlanogramaOriginalesCambiados,"ERROR");
					}//Si ha ocurrido un error inesperado.
					else if(data.msgError == '3'){
						createAlert(noHaPodidoModificarsePlanograma,"ERROR");
					}
				}
			},
			error : function (xhr, status, error){
				handleError(xhr, status, error, locale);
			}			
		});	
	}else{
		if(validarFacCap == 'F'){
			createAlert(capFacVacio,"ERROR");
		}else if(validarFacCap == 'I'){
			createAlert(facCapOrig,"ERROR");
		}
	}
}

function actualizarModificarPlanograma(objJson){
	$.ajax({
		type : 'POST',
		url : './referenciasCentro/actualizarModificarPlanograma.do',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data: objJson,
		cache : false,
		success : function(data) {	
			if(data == 0){
				createAlert(planogramaModificadoCorrectamente,"INFO");
			}else{
				//Si hay un error al actualizar.
				if(data == '1'){
					createAlert(noHaPodidoModificarsePlanograma,"ERROR");
				}//Si la capacidad o facing originales guardadas no coinciden con las tuyas. Esto quiere decir que otro usuario ha actualizado antes que tu.
				else if(data == '2'){
					createAlert(noHaPodidoModificarsePlanograma,"ERROR");
				}//Si ha ocurrido un error inesperado.
				else if(data == '3'){
					createAlert(noHaPodidoModificarsePlanograma,"ERROR");
				}
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});	
}

//Función que actualiza al instante el IMC según el valor del facing.
function event_changeFacingIMC(){
	$("#p15_fld_linealFacingFacingX").on("keyup",function(e){
		//Si es un número o eliminar
		var valorNumerico = (e.keyCode >= 48 && e.keyCode <= 57) || (e.keyCode >= 96 && e.keyCode <= 105);
		var eliminar = (e.keyCode == 8);
		if (valorNumerico || eliminar) {
			// Number
			var facing;
			if($("#p15_fld_linealFacingFacingX").val()!= ''){
				facing = $("#p15_fld_linealFacingFacingX").val();
			}else{
				facing = 0;
			}
			var multiplicador = $("#p15_fld_linealMultiplicadorFacingX").val();
			$("#p15_fld_linealImcFacingX").val(facing*multiplicador);

			//if(valorNumerico){
			if($("#p15_fld_linealFacingFacingX").hasClass("p15_campoError")){
				$("#p15_fld_linealFacingFacingX").removeClass("p15_campoError");
				//Si está en rojo porque ha habido error de no haber cambiado ningún campo,
				//al introducir valor en facing, el de capacidad tiene que desaparecer.
				if($("#p15_fld_linealCapacidadFacingX").val() != ''){
					if($("#p15_fld_linealCapacidadFacingX").hasClass("p15_campoError")){
						$("#p15_fld_linealCapacidadFacingX").removeClass("p15_campoError");
					}
				}
			}
			//}
		}
	});
}

function event_changeCapacidad(){
	$("#p15_fld_linealCapacidadFacingX").on("keyup",function(e){
		//Si es un número 
		//var valorNumerico = (e.keyCode >= 48 && e.keyCode <= 57) || (e.keyCode >= 96 && e.keyCode <= 105);
		//if (valorNumerico){
		if($("#p15_fld_linealCapacidadFacingX").hasClass("p15_campoError")){
			$("#p15_fld_linealCapacidadFacingX").removeClass("p15_campoError");
			//Si está en rojo porque ha habido error de no haber cambiado ningún campo,
			//al introducir valor en capacidad, el de facing tiene que desaparecer.
			if($("#p15_fld_linealFacingFacingX").val() != ''){
				if($("#p15_fld_linealFacingFacingX").hasClass("p15_campoError")){
					$("#p15_fld_linealFacingFacingX").removeClass("p15_campoError");
				}
			}
		}
		//}
	});
}

function event_changeCapacidadFacingVegalsa(){
	$("#p15_fld_linealFacingFacingX").off('keyup').on("keyup",function(e){
		//Si es un número o eliminar
		var valorNumerico = (e.keyCode >= 48 && e.keyCode <= 57) || (e.keyCode >= 96 && e.keyCode <= 105);
		var eliminar = (e.keyCode == 8);
		if (valorNumerico || eliminar) {

			var fondo = $("#p15_fld_linealMultiplicadorFacingX").val();

			if (fondo != ''){
				// Number
				var facing = $("#p15_fld_linealFacingFacingX").val();
				if (facing != ''){
					facing = facing;
				}else{
					facing = 0;
				}

				$("#p15_fld_linealCapacidadFacingX").val(facing*fondo);
				
			}else{
				$("#p15_fld_linealCapacidadFacingX").val(0);
			}
			
			//if(valorNumerico){
			if($("#p15_fld_linealFacingFacingX").hasClass("p15_campoError")){
				$("#p15_fld_linealFacingFacingX").removeClass("p15_campoError");
				//Si está en rojo porque ha habido error de no haber cambiado ningún campo,
				//al introducir valor en facing, el de capacidad tiene que desaparecer.
				if($("#p15_fld_linealCapacidadFacingX").val() != ''){
					if($("#p15_fld_linealCapacidadFacingX").hasClass("p15_campoError")){
						$("#p15_fld_linealCapacidadFacingX").removeClass("p15_campoError");
					}
				}
			}
		}

		// Ocultar el mensaje de guardado.
		$("#p15_lbl_guardadoVegalsa").css("visibility","hidden");

	});
}

//Chequea a ver si el facing o la cantidad es vacía y si se han cambiado los datos.
//Si no se ha cambiado ningún dato salta error 'I', si no hay dato salta error 'F'
function validarFacingCapacidad(){
	var validado = 'S';
	var facing = $("#p15_fld_linealFacingFacingX").val();
	var capacidad = $("#p15_fld_linealCapacidadFacingX").val();

	var facingOrig = $("#p15_facingOrig").val();
	var capacidadOrig = $("#p15_capacidadOrig").val();

	if(facing == ''){
		$("#p15_fld_linealFacingFacingX").addClass("p15_campoError");
		validado = 'F';
	}if(capacidad == ''){
		$("#p15_fld_linealCapacidadFacingX").addClass("p15_campoError");
		validado = 'F';
	}if(facingOrig == facing && capacidadOrig == capacidad){
		$("#p15_fld_linealFacingFacingX").addClass("p15_campoError");
		$("#p15_fld_linealCapacidadFacingX").addClass("p15_campoError");
		validado = 'I';
	}

	return validado;
}

function p15FormateoFechaEntrega(fecha) {

	var fechaFormateada = '';
	if (fecha != null)
	{
		var anyoFecha = parseInt(fecha.substring(6),10);
		var mesFecha = parseInt(fecha.substring(3,5),10);
		var diaFecha = parseInt(fecha.substring(0,2),10);



		fechaFormateada = $.datepicker.formatDate("D dd-M", new Date(anyoFecha, mesFecha - 1, diaFecha),{
			dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
			dayNames: $.datepicker.regional[ "es" ].dayNames,
			monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
			monthNames: $.datepicker.regional[ "es" ].monthNames
		});
	}

	return fechaFormateada;

}

