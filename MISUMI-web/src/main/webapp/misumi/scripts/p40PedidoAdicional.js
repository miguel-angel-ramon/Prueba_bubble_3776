var RESET_CONST="reset";
var SECTION_CONST="section";
var CATEGORY_CONST="category";
var referenceRequired=null;
var centerRequired=null;
var emptyRecords=null;
var literalEncargos=null;
var literalMontajeAdic=null;
var literalMontajeAdicEnOf=null;
var literalEmpujes = null;
var literalValidarCant = null;
var literalEncargosCliente=null;
var literalNoModifyEncargos = null;
var literalNoModifyEncargosCliente = null;
var literalNoModifyEmpujes = null;
var literalNoModifyValidar = null;
var literalNoDeleteEmpujes = null;
var noExcelData = null;
var mensajeAyudaMca = null;
var clicked = false;
var mostrarDesdeAvisoValidarCantExtra = null;
var mostrarDesdeAvisoPedidosAdicionales = null;
var defaultDescription = null;
var P40Inicializada = null;

var mostrarDesdeAvisoMontajeAdicional = null;

//Define si la consulta viene de consultas datos referencia.
var pantallaOrigen;
$(document).ready(function(){
	$(document).on('CargarScriptsPantallas', function(e) { 
		events_p40_btn_buscar();
		events_p40_btn_modificar();
		events_p40_btn_borrar();
		events_p40_btn_nuevo();	
		events_p40_btn_excel();	
		events_p40_btn_ayudaMca();
		loadP40(locale);
		events_p40_rad_tipoFiltro();

		//Si se pulsa intro, volvemos a lanzar el buscar.
		$(".controlReturnP40").keydown(function(e) {
			if(e.which == 13) {
				e.preventDefault();
				loadContadoresPestañas();
			}
		});
		//Esperamos a que se cargue el centerName en el metodo getCentroUsuarioSession()
		$(document).on('CargadoCentro', function(e) { 
			if(P40Inicializada == null){
				P40Inicializada = 'S';
				initializeScreenP40();
				initializeP56();
			}

			$("#p40_chk_mca").prop('checked',false);
			$("#p40_chk_mca").prop('disabled',false);
			
		});

		if($("#centerId").val() != null && $("#centerId").val() != "" && P40Inicializada == null){
			pantallaOrigen = $("#p40_pantallaOrigen").val();

			P40Inicializada = 'S';
			initializeScreenP40();
			initializeP56();
		}

		initializeP41();
		initializeP42();
		initializeP43();
		initializeP45();
		initializeP46();
		initializeP47();
		initializeP60();
		initializeP61();
		initializeP62();
	});
	initializeScreenComponentsP40();
});

function initializeScreenComponentsP40(){
	$("#p40_cmb_seccion").combobox(null);
	$("#p40_cmb_categoria").combobox(null);

	CargadaEstructuraPantalla(2);
}

$(document).mousedown(function (e)
		{
	if($("#p56_AreaAyuda").is(':visible')){
		var container = $(".p56_popupResaltado");
		//Si el popup 17 está abierto, si se clica el popup o algúno de sus hijos no cerrar popup p56
		var container2 = $(".p17_popupResaltado");

		var $el = $(e.target);
		var containerClassName = e.target.className;
		var hide = false;
		if (!container.is(e.target) // if the target of the click isn't the container...
				&& container.has(e.target).length === 0 
				&& !container2.is(e.target) // if the target of the click isn't the container...
				&& container2.has(e.target).length === 0 
				&& !$("#p17_popupVentas").is(':visible')
				&& (null == containerClassName || containerClassName.indexOf("conAyuda") == -1)) // ... nor a descendant of the container
		{
			$("#p56_AreaAyuda").dialog('close');
		}
	}
	//Si se clica fuera del popup p17 cerrar el popup p17
	if($("#p17_popupVentas").is(':visible')){
		var container2 = $(".p17_popupResaltado");

		if (!container2.is(e.target)
				&& container2.has(e.target).length === 0 ){
			$("#p17_popupVentas").dialog('close');
		}
	}
		});

$(document).focus(function (e)
		{
	if($("#p56_AreaAyuda").is(':visible')){
		var container = $(".p56_popupResaltado");
		var containerClassName = e.target.className;
		var $el = $(e.target);
		var hide = false;
		if (!container.is(e.target) // if the target of the click isn't the container...
				&& container.has(e.target).length === 0
				&& (null == containerClassName || containerClassName.indexOf("conAyuda") == -1)) // ... nor a descendant of the container
		{
			$("#p56_AreaAyuda").dialog('close');
		}
	}
		});

function resetearVariablesCancelarPedido(){
	$("#p40_pestanaOrigen").val("");
	$("#p40_flagCancelarNuevo").val("");
	$("#p40_codArea").val("");
	$("#p40_codSeccion").val("");
	$("#p40_codCategoria").val("");
	$("#p40_referencia").val("");
	$("#p40_mac").val("");
}

function initializeScreenP40(){

	$("#p40_cmb_seccion").combobox(null);
	$("#p40_cmb_categoria").combobox(null);

	/**
	 * P-52083
	 * Solicitud de filtro por parte de Maria para evitar casque con referencias alfanumericas
	 * @author BICUGUAL 
	 */
	$('#p40_fld_referencia').filter_input({regex:'[0-9]'});

	var result=cleanFilterSelection(RESET_CONST);
	controlCentro();

	$("#p40_pestanas").tabs({ 
		select:
			function (event, ui) {
			$("#p56_AreaAyuda").dialog('close');
			var nombrePestanaActivada = ui.panel.id;
			var valorPestanaCargada = $("#" + nombrePestanaActivada + "Cargada").val();
			//Sólo se ejecuta la carga de datos si no se ha cargado antes
			if (valorPestanaCargada == null || !(valorPestanaCargada == "S")){
				if (nombrePestanaActivada == "p40_pestanaEncargos"){
					//Carga de la pestaña de encargos
					resetDatosP41();
					reloadDatosEncargos();
				}else if (nombrePestanaActivada == "p40_pestanaMontaje") {
					//Carga de la pestaña de montaje
					resetDatosP42();
					reloadMontaje();
				}else if (nombrePestanaActivada == "p40_pestanaMontajeOferta") {
					//Carga de la pestaña de montaje oferta
					resetDatosP43();
					reloadMontajeOferta();
				}else if (nombrePestanaActivada == "p40_pestanaEmpuje") {
					//Carga de la pestaña de montaje oferta
					resetDatosP45();
					reloadEmpuje();
				}else if (nombrePestanaActivada == "p40_pestanaValidarCant") {
					//Carga de la pestaña de validar cantidades extra
					resetDatosP46();
					reloadValidarCant();
				}else if (nombrePestanaActivada == "p40_pestanaEncargosCliente") {
					//Carga de la pestaña de encargos de cliente
					resetDatosP47();
					reloadEncargosCliente();
				}
			} 

			// Habilitar o deshabilitar botón de borrar
			if (nombrePestanaActivada == "p40_pestanaValidarCant") {
				// Deshabilitar el botón borrar cuando es pestaña de validar cantidades extra
				$("#p40_btn_borrar").attr("disabled", "disabled");
			} 
			else{
				if ( !($("#p40_chk_mca").is(":checked")) && $('#p40_btn_borrar').is(':disabled') ){
					// Habilitar el botón borrar cuando no es pestaña de validar cantidades extra y no es MAC
					$("#p40_btn_borrar").removeAttr("disabled");
				}
			}

			// Habilitar o deshabilitar botón de excel
			//if (nombrePestanaActivada == "p40_pestanaEncargosCliente") {
			// Deshabilitar el botón excel cuando es pestaña de encargos de cliente
			//$("#p40_btn_excel").attr("disabled", "disabled");
			//}
			//else{
			if ($('#p40_btn_excel').is(':disabled') ){
				// Habilitar el botón excel cuando no es pestaña de encargos de cliente
				$("#p40_btn_excel").removeAttr("disabled");
			}
			//}
			//Se actualiza el control de carga de datos en pestaña
			$(nombrePestanaActivada + "Cargada").val("S");

			return true;
		}
	});
}

function events_p40_btn_buscar(){
	$("#p40_btn_buscar").click(function () {
		var messageVal=findValidation();
		if (messageVal!=null){
			createAlert(replaceSpecialCharacters(messageVal), "ERROR");
		}else{
			//Cargamos los contadores de las pestañas.
			loadContadoresPestañas();
		}
	});	
}
function finder(){
	var messageVal=findValidation();
	resetResultados();

	if (messageVal!=null){
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		if (mostrarDesdeAvisoValidarCantExtra != null && mostrarDesdeAvisoValidarCantExtra == 'S'){
			mostrarDesdeAvisoValidarCantExtra = null;
			$("#p40_pestanas").tabs("option", "active", 4);
			resetDatosP46();
			reloadValidarCant();

			// Deshabilitar el botón borrar cuando es pestaña de validar cantidades extra
			$("#p40_btn_borrar").attr("disabled", "disabled");

			//Se actualiza el control de carga de datos en pesta�a
			$("#p40_pestanaValidarCantCargada").val("S");
		}else{
			if (mostrarDesdeAvisoPedidosAdicionales != null && mostrarDesdeAvisoPedidosAdicionales == 'S'){
				mostrarDesdeAvisoPedidosAdicionales = null;
				$("#p40_pestanas").tabs("option", "active", 3);
				//Carga de la pestaña de montaje oferta
				resetDatosP43();
				reloadMontajeOferta();

				// Deshabilitar el botón borrar cuando es pestaña de validar cantidades extra
				$("#p40_btn_borrar").attr("disabled", "disabled");

				//Se actualiza el control de carga de datos en pesta�a
				$("#p40_pestanaMontajeOfertaCargada").val("S");



			} else if(mostrarDesdeAvisoMontajeAdicional != null && mostrarDesdeAvisoMontajeAdicional == 'S'){
				mostrarDesdeAvisoMontajeAdicional = null;
				$("#p40_pestanas").tabs("option", "active", 2);
				//Carga de la pestaña de montaje oferta
				resetDatosP42();
				reloadMontaje();

				// Deshabilitar el botón borrar cuando es pestaña de validar cantidades extra
				$("#p40_btn_borrar").attr("disabled", "disabled");

				//Se actualiza el control de carga de datos en pesta�a
				$("#p40_pestanaMontajeCargada").val("S");
			}else {
				if ($("#p40_chk_mca").is(":checked")){
					reloadEmpuje();
				} else {
					reloadDatosEncargos();
				}
			}
		}


		actualizarAvisosValidarCant();
		clicked = false;
	}
}

function findValidation(){
	var messageVal=null;
	if ($("input[name='p40_rad_tipoFiltro']:checked").val() == "1"){
		//Estructura comercial
		if ($("#centerId").val()==null || $("#centerId").val()==""){
			messageVal = centerRequired;
		}
	} else { //Referencias
		if ($("#centerId").val()==null || $("#centerId").val()==""){
			messageVal = centerRequired;
		}	
	}    
	return messageVal;
}

function events_p40_btn_excel(){
	$("#p40_btn_excel").click(function () {
		exportExcel();
	});
}


function events_p40_btn_modificar(){
	$("#p40_btn_modificar").click(function () {
		if ($("#p40_pestanaMontaje").is (":visible")) 
		{

			p42ModificarDatos();
		} 
		else if ($("#p40_pestanaMontajeOferta").is (":visible")) 
		{
			p43ModificarDatos();
		}
		else if ($("#p40_pestanaEncargosCliente").is (":visible")) 
		{
			createAlert(replaceSpecialCharacters(literalNoModifyEncargosCliente), "ERROR");

		} else if ($("#p40_pestanaEmpuje").is (":visible"))  {
			createAlert(replaceSpecialCharacters(literalNoModifyEmpujes), "ERROR");
		} else if ($("#p40_pestanaValidarCant").is (":visible"))  {
			createAlert(replaceSpecialCharacters(literalNoModifyValidar), "ERROR");
		} else {
			createAlert(replaceSpecialCharacters(literalNoModifyEncargos), "ERROR");
		}
	});	
}

function events_p40_btn_borrar(){
	$("#p40_btn_borrar").click(function () {
		if ($("#p40_pestanaEncargos").is (":visible")) 
		{
			p41BorrarDatos();
		}
		else if ($("#p40_pestanaMontaje").is (":visible")) 
		{
			p42BorrarDatos();
		}
		else if ($("#p40_pestanaMontajeOferta").is (":visible")) 
		{
			p43BorrarDatos();
		}
		else if ($("#p40_pestanaEncargosCliente").is (":visible")) 
		{
			p47BorrarDatos();
		} 
		else {
			createAlert(replaceSpecialCharacters(literalNoDeleteEmpujes), "ERROR");
		}
	});
}

function events_p40_btn_nuevo(){
	$("#p40_btn_nuevo").click(function () {
		var pestanaOrigen='';
		if ($("#p40_pestanaEncargos").is (":visible")){
			pestanaOrigen = "E";
		} else if ($("#p40_pestanaMontaje").is (":visible")){
			pestanaOrigen = "M";
		} else if ($("#p40_pestanaMontajeOferta").is (":visible")){
			pestanaOrigen = "MO";
		} else if ($("#p40_pestanaEmpuje").is (":visible")){
			pestanaOrigen = "EM";
		} else if ($("#p40_pestanaValidarCant").is (":visible")){
			pestanaOrigen = "VC";
		} else if ($("#p40_pestanaEncargosCliente").is (":visible")){
			pestanaOrigen = "EC";
		}

		var codArea = '';
		var codSeccion = '';
		var codCategoria = '';

		if ($("#p40_cmb_seccion").combobox('getValue') != null && $("#p40_cmb_seccion").combobox('getValue') != ''){
			valorSeccion = $("#p40_cmb_seccion").combobox('getValue').split('*');
			codArea = valorSeccion[0];
			codSeccion = valorSeccion[1];
		}

		if ($("#p40_cmb_categoria").combobox('getValue')!= null){
			codCategoria = $("#p40_cmb_categoria").combobox('getValue');
		}

		window.location='./nuevoPedidoAdicional.do?pestanaOrigen='+pestanaOrigen+'&codArea='+codArea+'&codSeccion='+codSeccion+
		'&codCategoria='+codCategoria+'&referencia='+$("#p40_fld_referencia").val();
	});
}

function events_p40_btn_ayudaMca(){
	$("#p40_btn_ayudaMca").click(function () {
		createAlert(replaceSpecialCharacters(mensajeAyudaMca), "HELP");
	});
}

function loadP40(locale){

	this.i18nJSON = './misumi/resources/p40PedidoAdicional/p40pedidoAdicional_' + locale + '.json';

	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		emptyRecords= data.emptyRecords;
		centerRequired = data.centerRequired;
		literalEncargos = data.encargos;
		literalMontajeAdic = data.montajeAdicional;
		literalMontajeAdicEnOf = data.montajeAdicionalOferta;
		literalNoModifyEncargos = data.noModifyEncargos;
		literalNoModifyEncargosCliente = data.noModifyEncargosCliente;
		literalNoModifyEmpujes = data.noModifyEmpujes;
		literalNoModifyValidar = data.noModifyValidar;
		literalNoDeleteEmpujes = data.noDeleteEmpujes;
		literalEmpujes = data.empujes;
		literalValidarCant = data.validarCant;
		literalEncargosCliente = data.encargosCliente;
		noExcelData = data.noExcelData;
		mensajeAyudaMca = data.mensajeAyudaMca;
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

function loadContadoresPestañas(){

	if (!clicked){
		clicked = true;
		var codArea = null;
		var codSeccion = null;
		var mca = "N";
		if ($("#p40_cmb_seccion").combobox('getValue') != null && $("#p40_cmb_seccion").combobox('getValue') != '')
		{
			valorSeccion = $("#p40_cmb_seccion").combobox('getValue').split('*');
			codArea = valorSeccion[0];
			codSeccion = valorSeccion[1];
		}
		if($("#p40_chk_mca").is(':checked')) {
			mca = "S";
		}
		var recordPedidoAdicionalE = new PedidoAdicionalE ($("#centerId").val() , codArea, codSeccion, $("#p40_cmb_categoria").combobox('getValue'),
				$("#p40_fld_referencia").val(), null, null,null, null, null, null, null, null, null, mca, null, null);
		if ($("#p40_flgReferenciaCentro").val() == 'S'){
			recordPedidoAdicionalE.consultaAlmacenada = true;
			$("#p40_flgReferenciaCentro").val("");
		}
		var objJson = $.toJSON(recordPedidoAdicionalE.preparePedidoAdicionalEToJsonObject());

		$.ajax({
			type : 'POST',
			url : './pedidoAdicional/loadContadores.do',
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			cache : false,
			success : function(data) {	

				if (data != null && data != '')
				{
					if (data.codError == 0){
						$("#p40AreaErrores").attr("style", "display:none");
						$("#p40_fld_contadorEncargos").text(literalEncargos + " ("+data.contadorEncargos+")");
						$("#p40_fld_contadorMontaje").text(literalMontajeAdic + " ("+data.contadorMontaje+")");
						$("#p40_fld_contadorMontajeOferta").text(literalMontajeAdicEnOf + " ("+data.contadorMontajeOferta+")");			    
						$("#p40_fld_contadorEmpuje").text(literalEmpujes + " ("+data.contadorEmpuje+")");
						$("#p40_fld_contadorValidarCant").text(literalValidarCant + " ("+data.contadorValidarCantExtra+")");
						$("#p40_fld_contadorEncargosCliente").text(literalEncargosCliente + " ("+data.contadorEncargosCliente+")");
						defaultDescription = data.defaultDescription;


						//Llamamos al buscar
						if ($("#p40_chk_mca").is(":checked")){
							$("#p40_pestanaEmpujeTab").show();
							$("#p40_pestanaEncargoTab").hide();
							$("#p40_pestanaValidarCantTab").hide();
							$("#p40_pestanaEncargoClienteTab").hide();
						} else {
							$("#p40_pestanaEmpujeTab").hide();
							$("#p40_pestanaEncargoTab").show();
							$("#p40_pestanaValidarCantTab").show();
							if (data.contadorEncargosCliente != null && data.contadorEncargosCliente > 0){
								$("#p40_pestanaEncargoClienteTab").show();
							}else{
								$("#p40_pestanaEncargoClienteTab").hide();
							}
						}
						//Si es MAC deshabilitamos el botón de borrar
						if ($("#p40_chk_mca").is(":checked")){
							$("#p40_btn_borrar").attr("disabled", "disabled");
						} else {
							$("#p40_btn_borrar").removeAttr("disabled");
						}
						
						//Si la pantalla de origen es consulta datos referencia,
						//no queremos realizar búsqueda, pues ya se ha realizado en
						//otros eventos del código. Una vez dentro de pedidos, funcionaremos
						//normal y por eso pantallaOrigen lo ponemos a null, pero la primera vez
						//que accedemos, si es por haber pinchado el link en consulta datos referencia
						//hay que evitar la búsqueda pues ya se está haciendo en otros eventos.
						if(pantallaOrigen != "consultaDatosRef"){
							finder();
						}else{
							//Reseteamos el campo de pantalla para que si venimos del link de consultas datos ref 
							//las siguientes búsquedas vaya al finder.
							pantallaOrigen = null;
							
							//Dejamos que el usuario pueda pulsar buscar.
							clicked = false;
						}
					}else{
						$("#p40_erroresTexto").text(data.descError);
						$("#p40AreaErrores").attr("style", "display:block");
						$("#p40_AreaPestanas").attr("style", "display:none");
					}

				}else{
					$("#p40AreaErrores").attr("style", "display:none");
				}
			},
			error : function (xhr, status, error){
				handleError(xhr, status, error, locale);
			}			
		});		

	}

} 

function load_cmbSeccion(){
	var options = "";
	var optionNull = "";
	var claveNuevo = "";
	var descNuevo = "";
	$("#p40_cmb_seccion").combobox(null);

	var vAgruComerRefPedidos=new VAgruComerRefPedidos($("#centerId").val(), "I2");
	var objJson = $.toJSON(vAgruComerRefPedidos);	
	$.ajax({
		type : 'POST',
		url : './pedidoAdicional/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			options = "<option value=null>&nbsp;</option>";
			for (i = 0; i < data.length; i++){

				if (($("#p40_flagCancelarNuevo").val() == 'S')&&(($("#p40_codArea").val()+ "*" +$("#p40_codSeccion").val())==(data[i].grupo1 + "*" + data[i].grupo2)))
				{
					descNuevo = p40_formatDescripcionCombo(data[i].grupo2, data[i].descripcion);
					claveNuevo = data[i].grupo1 + "*" + data[i].grupo2;
				}

				options = options + "<option value='" + data[i].grupo1 + "*" + data[i].grupo2 + "'>" + p40_formatDescripcionCombo(data[i].grupo2, data[i].descripcion) + "</option>";

			}
			$("select#p40_cmb_seccion").html(options);

			if (($("#p40_flagCancelarNuevo").val() == 'S')&&(descNuevo != '')){
				$("#p40_cmb_seccion").combobox('autocomplete',descNuevo);
				$("#p40_cmb_seccion").val(claveNuevo);
				if ($("#p40_codCategoria").val() != '')
				{
					load_cmbCategory();
				}
				else
				{
					controlBotonCancelarNuevo();
				}
			}
			if (($("#p40_flagCancelarNuevo").val() == 'S')&&(descNuevo == '')){
				controlBotonCancelarNuevo();
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});

	$("#p40_cmb_seccion").combobox({
		selected: function(event, ui) {

			if ( ui.item.value!="" && ui.item.value!="null") {
				if ($("#p40_cmb_seccion").val()!=null){
					var result=cleanFilterSelection(SECTION_CONST);
					load_cmbCategory();	        	   

				}
			}  else{
				var result=cleanFilterSelection(RESET_CONST);
			}  
		}  
	,	
	changed: function(event, ui) { 
		if (ui.item==null || ui.item.value!="" || ui.item.value!="null"){
			return result=cleanFilterSelection(RESET_CONST);

		}	 
	}
	});

	if ($("#p40_flagCancelarNuevo").val() != 'S')
	{
		$("#p40_cmb_seccion").combobox('autocomplete',optionNull);
		$("#p40_cmb_seccion").combobox('comboautocomplete',null);
	}

}

function load_cmbCategory(){
	var options = "";
	var optionNull = "";
	var claveCatNuevo = "";
	var descCatNuevo = "";
	$("#p40_cmb_categoria").combobox(null);
	var valorSeccion = $("#p40_cmb_seccion").val().split('*');
	var codArea = valorSeccion[0];
	var codSeccion = valorSeccion[1];
	if ($("#p40_flagCancelarNuevo").val() == 'S')
	{
		codArea = $("#p40_codArea").val();
		codSeccion = $("#p40_codSeccion").val();
	}
	var vAgruComerRefPedidos=new VAgruComerRefPedidos($("#centerId").val(), "I3",codArea,codSeccion);
	var objJson = $.toJSON(vAgruComerRefPedidos);	
	$.ajax({
		type : 'POST',
		url : './pedidoAdicional/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {		
			options = "<option value=null>&nbsp;</option>";
			for (i = 0; i < data.length; i++){
				if (($("#p40_flagCancelarNuevo").val() == 'S')&&($("#p40_codCategoria").val()==data[i].grupo3))
				{
					descCatNuevo = p40_formatDescripcionCombo(data[i].grupo3, data[i].descripcion);
					claveCatNuevo = data[i].grupo3;
				}
				options = options + "<option value='" + data[i].grupo3 + "'>" + p40_formatDescripcionCombo(data[i].grupo3, data[i].descripcion) + "</option>"; 
			}
			$("select#p40_cmb_categoria").html(options);

			if (($("#p40_flagCancelarNuevo").val() == 'S')&&(descCatNuevo != '')){
				$("#p40_cmb_categoria").combobox("enable");
				$("#p40_cmb_categoria").combobox('autocomplete',descCatNuevo);
				$("#p40_cmb_categoria").val(claveCatNuevo);

				controlBotonCancelarNuevo();
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		


	if ($("#p40_flagCancelarNuevo").val() != 'S')
	{
		$("#p40_cmb_categoria").combobox('autocomplete',optionNull);
		$("#p40_cmb_categoria").combobox('comboautocomplete',null);
	}


}

function p40_formatDescripcionCombo(codigo, descripcion){
	return codigo + "-" + descripcion;
}

function events_p40_rad_tipoFiltro(){
	$("input[name='p40_rad_tipoFiltro']").change(function () {

		if ($("input[name='p40_rad_tipoFiltro']:checked").val() == "1"){
			//Estructura comercial
			$("div#p40_filtroEstructura").attr("style", "display:block");
			$("div#p40_filtroReferencia").attr("style", "display:none");
			$("#p40_fld_referencia").val("");
			resetResultados();
		} else { 
			//Referencias
			$("div#p40_filtroEstructura").attr("style", "display:none");
			$("div#p40_filtroReferencia").attr("style", "display:inline");
			var result= cleanFilterSelection(RESET_CONST);
			resetResultados();
			$("#p40_fld_referencia").focus();
		}   
		// Habilitamos el botón borrar
		$("#p40_btn_borrar").removeAttr("disabled");
	});
}

function controlCentro(){
	$( "#centerName" ).bind('focus', function() {
		$("#p40_AreaPestanas").attr("style", "display:none");

		$("#p40_cmb_seccion").combobox(null);
		$("select#p40_cmb_seccion").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p40_cmb_seccion").combobox('autocomplete','');
		$("#p40_cmb_seccion").combobox('comboautocomplete',null);
		$("#p40_cmb_categoria").combobox(null);
		$("select#p40_cmb_categoria").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p40_cmb_categoria").combobox('autocomplete','');
		$("#p40_cmb_categoria").combobox('comboautocomplete',null);

		$("#p40_cmb_seccion").combobox("disable");
		$("#p40_cmb_categoria").combobox("disable");
	});
}

function cleanFilterSelection(componentName){

	if (componentName == RESET_CONST){
		$("#p40_cmb_seccion").combobox(null);
		$("select#p40_cmb_seccion").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p40_cmb_seccion").combobox('autocomplete','');
		$("#p40_cmb_seccion").combobox('comboautocomplete',null);
		$("#p40_cmb_categoria").combobox(null);
		if ($("#centerId").val()!=null && $("#centerId").val()!=''){
			load_cmbSeccion();
		}
	}
	if (componentName == SECTION_CONST || componentName == RESET_CONST){
		$("select#p40_cmb_categoria").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p40_cmb_categoria").combobox('autocomplete','');
		$("#p40_cmb_categoria").combobox('comboautocomplete',null);

	}
	disableFilterSelection(componentName);
	return true;
}

function disableFilterSelection(componentName){
	if (componentName == RESET_CONST){
		if ($("#centerId").val()!=null && $("#centerId").val()!=''){
			$("#p40_cmb_seccion").combobox("enable");

		}else{
			$("#p40_cmb_seccion").combobox("disable");
		}
		$("#p40_cmb_categoria").combobox("disable");
	}else if(componentName==SECTION_CONST ){
		$("#p40_cmb_categoria").combobox("enable");
	}
}

function resetResultados (){
	$("div#p40_AreaPestanas").attr("style", "display:none");
	if ($("#p40_chk_mca").is(":checked")){
		$("#p40_pestanas").tabs("option", "active", 1);
	} else {
		$("#p40_pestanas").tabs("option", "active", 0);
	}
	$("#p40_pestanaEncargosCargada").val("");
	$("#p40_pestanaMontajeCargada").val("");
	$("#p40_pestanaMontajeOfertaCargada").val("");
	$("#p40_pestanaHistoricoCargada").val("");
	$("#p40_pestanaEmpujeCargada").val("");
	$("#p40_pestanaValidarCantCargada").val("");
	$("#p40_pestanaEncargosClienteCargada").val("");
	descripcion = null;
	$("#p46_cmb_descripcion").combobox('autocomplete',"");
	$("#p46_cmb_descripcion").combobox('comboautocomplete',null);
	p43DescPeriodo = null;
	p43EspacioPromo = null;

	p42DescPeriodo = null;
	p42EspacioPromo = null;

	$("#p43_cmb_oferta").combobox('autocomplete',"");
	$("#p43_cmb_oferta").combobox('comboautocomplete',null);
	$("#p43_cmb_promocion").combobox('autocomplete',"");
	$("#p43_cmb_promocion").combobox('comboautocomplete',null);

	$("#p42_cmb_oferta").combobox('autocomplete',"");
	$("#p42_cmb_oferta").combobox('comboautocomplete',null);
	$("#p42_cmb_promocion").combobox('autocomplete',"");
	$("#p42_cmb_promocion").combobox('comboautocomplete',null);
}

function controlBotonCancelarNuevo(){

	if ($("#p40_flagCancelarNuevo").val() == 'S')
	{

		if ($("#p40_referencia").val() != '')
		{
			//Referencias
			$('input:radio[name=p40_rad_tipoFiltro]')[0].checked = false;
			$('input:radio[name=p40_rad_tipoFiltro]')[1].checked = true;
			$("div#p40_filtroEstructura").attr("style", "display:none");
			$("div#p40_filtroReferencia").attr("style", "display:inline");
			$("#p40_fld_referencia").val($("#p40_referencia").val());
		}
		else
		{
			//Estructura comercial
			$('input:radio[name=p40_rad_tipoFiltro]')[1].checked = false;
			$('input:radio[name=p40_rad_tipoFiltro]')[0].checked = true;
			$("div#p40_filtroEstructura").attr("style", "display:block");
			$("div#p40_filtroReferencia").attr("style", "display:none");
		}
		if ($("#p40_mac").val() != ''){
			if ($("#p40_mac").val() == 'S'){
				$("#p40_chk_mca").attr('checked', true);
			} else {
				$("#p40_chk_mca").attr('checked', false);
			}
		}

		//Ahora tenemos que cargar la pestaña correspondiente y llamar a cargar el listado.
		if ($("#p40_pestanaOrigen").val() != ''){
			loadContadoresPestañas();
			//$("div#p40_AreaPestanas").attr("style", "display:block");
			$("#p40_pestanaEncargosCargada").val("S");
		}
		if ($("#p40_pestanaOrigen").val() == 'E'){
			//Carga de la pestaña de encargos
			reloadDatosEncargos();
			$("#p40_pestanaMontajeCargada").val("N");
			$("#p40_pestanaMontajeOfertaCargada").val("N");
			$("#p40_pestanaEmpujeCargada").val("N");
			$("#p40_pestanaValidarCantCargada").val("N");
			$("#p40_pestanaEncargosClienteCargada").val("N");

			//Seleccionamos la pestaña por id Encargo
			var index = $('#p40_pestanas li').index($('#p40_pestanaEncargoTab'));
			$('#p40_pestanas').tabs('select', index);
		}else if ($("#p40_pestanaOrigen").val() == 'M') {
			//Carga de la pestaña de montaje
			reloadMontaje();
			$("#p40_pestanaEncargosCargada").val("N");
			$("#p40_pestanaMontajeCargada").val("S");
			$("#p40_pestanaMontajeOfertaCargada").val("N");
			$("#p40_pestanaEmpujeCargada").val("N");
			$("#p40_pestanaValidarCantCargada").val("N");
			$("#p40_pestanaEncargosClienteCargada").val("N");

			//Seleccionamos la pestaña por id Montaje
			var index = $('#p40_pestanas li').index($('#p40_pestanaMontajeTab'));
			$('#p40_pestanas').tabs('select', index);
		}else if ($("#p40_pestanaOrigen").val() == 'MO') {
			//Carga de la pestaña de montaje oferta
			reloadMontajeOferta();
			$("#p40_pestanaEncargosCargada").val("N");
			$("#p40_pestanaMontajeCargada").val("N");
			$("#p40_pestanaMontajeOfertaCargada").val("S");
			$("#p40_pestanaEmpujeCargada").val("N");
			$("#p40_pestanaValidarCantCargada").val("N");
			$("#p40_pestanaEncargosClienteCargada").val("N");

			//Seleccionamos la pestaña por id Montaje oferta
			var index = $('#p40_pestanas li').index($('#p40_pestanaMontajeOfertaTab'));
			$('#p40_pestanas').tabs('select', index);
		}else if ($("#p40_pestanaOrigen").val() == 'EM') {
			//Carga de la pestaña de montaje oferta
			reloadEmpuje();
			$("#p40_pestanaEncargosCargada").val("N");
			$("#p40_pestanaMontajeCargada").val("N");
			$("#p40_pestanaMontajeOfertaCargada").val("N");
			$("#p40_pestanaEmpujeCargada").val("S");
			$("#p40_pestanaValidarCantCargada").val("N");
			$("#p40_pestanaEncargosClienteCargada").val("N");
			
			//Seleccionamos la pestaña por id Empuje
			var index = $('#p40_pestanas li').index($('#p40_pestanaEmpujeTab'));
			$('#p40_pestanas').tabs('select', index);
		}else if ($("#p40_pestanaOrigen").val() == 'VC') {
			//Carga de la pestaña de validar cantidades extra
			reloadValidarCant();
			$("#p40_pestanaEncargosCargada").val("N");
			$("#p40_pestanaMontajeCargada").val("N");
			$("#p40_pestanaMontajeOfertaCargada").val("N");
			$("#p40_pestanaEmpujeCargada").val("N");
			$("#p40_pestanaValidarCantCargada").val("S");
			$("#p40_pestanaEncargosClienteCargada").val("N");
			
			//Seleccionamos la pestaña por id Validar cantidades extra.
			var index = $('#p40_pestanas li').index($('#p40_pestanaValidarCantTab'));
			$('#p40_pestanas').tabs('select', index);	
		}else if ($("#p40_pestanaOrigen").val() == 'EC') {
			//Carga de la pestaña encargos de cliente
			reloadEncargosCliente();
			$("#p40_pestanaEncargosCargada").val("N");
			$("#p40_pestanaMontajeCargada").val("N");
			$("#p40_pestanaMontajeOfertaCargada").val("N");
			$("#p40_pestanaEmpujeCargada").val("N");
			$("#p40_pestanaValidarCantCargada").val("N");
			$("#p40_pestanaEncargosClienteCargada").val("S");
			
			//Seleccionamos la pestaña por id Encargos cliente.
			var index = $('#p40_pestanas li').index($('#p40_pestanaEncargoClienteTab'));
			$('#p40_pestanas').tabs('select', index);	
		}


		//Control para ver si la carga proviene del aviso de validar cantidades extra
		if ($("#p40_pestanaOrigen").val() != '' && $("#p40_pestanaOrigen").val() == 'AVISOS_VC'){

			mostrarDesdeAvisoValidarCantExtra = 'S';
		}

		//Control para ver si la carga proviene del aviso de pedidos adicionales
		if ($("#p40_pestanaOrigen").val() != '' && $("#p40_pestanaOrigen").val() == 'AVISOS_MO'){

			mostrarDesdeAvisoPedidosAdicionales = 'S';
		}

		//Control para ver si la carga proviene del aviso de pedidos adicionales
		if ($("#p40_pestanaOrigen").val() != '' && $("#p40_pestanaOrigen").val() == 'AVISOS_M'){

			mostrarDesdeAvisoMontajeAdicional = 'S';
		}
		//Por último reiniciamos las variables que me podrían habe llegado del botón cancelar de nuevo pedido.
		resetearVariablesCancelarPedido();

	}


}

function exportExcel(){
	var messageVal=findValidation();
	var grid;
	var clasePedidoAdicional;
	if (messageVal!=null){
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		if ($("#p40_AreaPestanas").is (":visible")){
			if ($("#p40_pestanaEncargos").is (":visible")) 
			{
				grid = gridP41Encargos;
				clasePedidoAdicional = 1;
			}
			else if ($("#p40_pestanaMontaje").is (":visible")) 
			{
				grid = gridP42PedidoAdicionalM;
				clasePedidoAdicional = 2;
			}
			else if ($("#p40_pestanaMontajeOferta").is (":visible")) 
			{
				grid = gridP43PedidoAdicionalMO;
				clasePedidoAdicional = 3;
			}
			else if ($("#p40_pestanaValidarCant").is (":visible")) 
			{
				grid = gridP46PedidoAdicionalVC;
				clasePedidoAdicional = 4;
			}
			else if ($("#p40_pestanaEmpuje").is (":visible")) 
			{
				grid = gridP45PedidoAdicionalEmpuje;
				clasePedidoAdicional = 6;
			}
			else if ($("#p40_pestanaEncargosCliente").is (":visible")) 
			{
				grid = gridP47EncargosCliente;
				clasePedidoAdicional = 7;
			}

			var colModel = $(grid.nameJQuery).jqGrid("getGridParam", "colModel");
			var colNames = $(grid.nameJQuery).jqGrid("getGridParam", "colNames");
			var myColumns = new Array();
			var myColumnsNames = new Array();
			var myColumnsWidth = new Array();
			var j=0;
			if (clasePedidoAdicional != 7){
				$.each(colModel, function(i) {
					if (colModel[i].name!="rn" && colModel[i].name!="cb" && colModel[i].name!="mensaje" && !colModel[i].hidden ){
						myColumnsNames[j]=colNames[i];
						myColumns[j]=colModel[i].name;
						myColumnsWidth[j]=colModel[i].width;
						j++;	

					}
				});
			}

//			var form = "<form name='csvexportform' action='altasCatalogo/exportGrid.do' accept-charset='ISO-8859-15'  method='get'>";
			var form = "<form name='csvexportform' action='pedidoAdicional/exportGrid.do'  accept-charset='ISO-8859-1' method='get'>";
			form = form + "<input type='hidden' name='headers' value='"+myColumnsNames+"'>";		
			form = form + "<input type='hidden' name='model' value='"+myColumns+"'>";
			form = form + "<input type='hidden' name='widths' value='"+myColumnsWidth+"'>";
			form = form + "<input type='hidden' name='clasePedidoAdicional' value='"+clasePedidoAdicional+"'>";
			if ($("input[name='p40_rad_tipoFiltro']:checked").val() == "1"){

				if ($("#p40_cmb_seccion").combobox('getValue')!="null" && $("#p40_cmb_seccion").combobox('getValue')!=null  ){
					form = form + "<input type='hidden' name='seccion' value='"+$("#p40_cmb_seccion").val()+"'>";
				}	
				if ($("#p40_cmb_categoria").combobox('getValue')!="null" && $("#p40_cmb_categoria").combobox('getValue')!=null  ){
					form = form + "<input type='hidden' name='grupo3' value='"+$("#p40_cmb_categoria").val()+"'>";
				}	
			}
			if ($("input[name='p40_rad_tipoFiltro']:checked").val() != "1"){
				if ($("#p40_fld_referencia").val()!="" && $("#p40_fld_referencia").val()!=null ){
					form = form + "<input type='hidden' name='codigoArticulo' value='"+$("#p40_fld_referencia").val()+"'>";
				}
			}	
			form = form + "<input type='hidden' name='mac' value='"+$("#p40_chk_mca").is(":checked")+"'>";
			if (null != descripcion && "null" != descripcion){
				form = form + "<input type='hidden' name='descripcion' value='"+descripcion+"'>";
			}
			if ($("#p40_chk_mca").is(":checked") && $("#p40_pestanaMontajeOferta").is (":visible")){
				form = form + p43CargarParametrosExcel();
			}	
			form = form + "</form><script>document.csvexportform.submit();</script>";
			Show_Popup(form);	
		} else {
			createAlert(replaceSpecialCharacters(noExcelData), "ERROR");
		}
	}
}
