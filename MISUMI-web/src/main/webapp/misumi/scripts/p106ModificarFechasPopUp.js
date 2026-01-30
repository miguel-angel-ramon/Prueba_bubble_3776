var okActualizacionFechas = null;
var errorActualizacionFechas = null;

function initializeScreenP106(){
	loadP106(locale);

	p106_inicializarPopUp();
	p106_inicializarFechas();

	events_p106_modificar();
}

function loadP106(locale){

	this.i18nJSON = './misumi/resources/p106ModificacionFechasPopUp/p106ModificacionFechasPopUp_' + locale + '.json';

	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		okActualizacionFechas = data.okActualizacionFechas;
		errorActualizacionFechas = data.errorActualizacionFechas;
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

function p106_inicializarPopUp(){
	//Definión del popup que contendrá los formularios
	$("#p106_popup").dialog({
		autoOpen: false,
		height: 'auto',
		width: 'auto',
		modal: true,
		title: "",
		resizable: false,
		dialogClass: "",
		open: function() {

		},
		close:function(){
			//Limpiamos el popup
			p106_limpiarPopUp();						
		},
		stack:false
	});
}

function p106_inicializarFechas(){
	$.datepicker.setDefaults($.datepicker.regional["esMisumi"]);
	$("#p106_fld_fechaIni").datepicker({
		onSelect: function(dateText, inst) {
			var fecFin = $("#p106_fld_fechaFin").datepicker("getDate");
			var fecIni = $("#p106_fld_fechaIni").datepicker("getDate");
			if(fecFin != null && fecIni > fecFin){
				createAlert(fechaIniPosteriorFechaFin, "ERROR");
				var fechaIniAnterior = ($('#p106_fld_fechaIni').data("oldDate") != undefined && $('#p106_fld_fechaIni').data("oldDate") != null) ? $('#p106_fld_fechaIni').data("oldDate") : fecFin;
				$('#p106_fld_fechaIni').datepicker('setDate', fechaIniAnterior);
			}else{
				$('#p106_fld_fechaIni').data("oldDate",fecIni);
			}
		}
	});

	$("#p106_fld_fechaFin" ).datepicker({
		onSelect: function(dateText, inst) {
			var fecFin = $("#p106_fld_fechaFin").datepicker("getDate");
			var fecIni = $("#p106_fld_fechaIni").datepicker("getDate");
			if(fecIni != null && fecFin < fecIni){
				createAlert(fechaFinAnteriorFechaIni, "ERROR");				
				var fechaFinAnterior = ($('#p106_fld_fechaFin').data("oldDate") != undefined && $('#p106_fld_fechaFin').data("oldDate") != null) ? $('#p106_fld_fechaFin').data("oldDate") : fecIni;
				$('#p106_fld_fechaFin').datepicker('setDate', fechaFinAnterior)
			}else{
				$('#p106_fld_fechaFin').data("oldDate",fecFin);
			}	
		}
	});
}

function p106_limpiarPopUp(){
	//Reseteamos el popup
	$('#p106_fld_fechaIni').datepicker('setDate',null);
	$('#p106_fld_fechaIni').data("oldDate",null);
	$('#p106_fld_fechaFin').datepicker('setDate', null);
	$('#p106_fld_fechaFin').data("oldDate",null);
}

function events_p106_modificar(){
	$("#p106_btn_modify").click(function(){
		if(!p106_camposVaciosModificar()){
			//Fechas
			var fechaIniActual = $.datepicker.formatDate("dd-mm-yy", $('#p106_fld_fechaIni').datepicker('getDate'));
			var fechaIniActualDatePartes = fechaIniActual.split("-");
			var fechaIniAnio = fechaIniActualDatePartes[2];
			var fechaIniMes = fechaIniActualDatePartes[1] - 1;
			var fechaIniDia = fechaIniActualDatePartes[0];
			var fechaIniDateActual = new Date(fechaIniAnio,fechaIniMes,fechaIniDia);

			var fechaFinActual = $.datepicker.formatDate("dd-mm-yy", $('#p106_fld_fechaFin').datepicker('getDate'));
			var fechaFinActualDatePartes = fechaFinActual.split("-");
			var fechaFinAnio = fechaFinActualDatePartes[2];
			var fechaFinMes = fechaFinActualDatePartes[1] -1 ;
			var fechaFinDia = fechaFinActualDatePartes[0];
			var fechaFinDateActual = new Date(fechaFinAnio,fechaFinMes,fechaFinDia);

			var cestasNavidad = new CestasNavidad(
					null,null, 
					null,null, null,
					null, null,
					fechaIniDateActual,fechaFinDateActual,
					null,null, null,
					8, null,
					null, null, null
			);

			var url='./parametrizacionCestas/updateFechas.do';
			var objJson = $.toJSON(cestasNavidad);	
			$.ajax({
				type : 'POST',
				url : url,
				data: objJson,
				contentType : "application/json; charset=utf-8",
				success : function(data) {	
					if(data != 0){
						createAlert(errorActualizacionFechas,"ERROR");
					}else{
						//Refrescamos el grid
						loadData_gridP104ParametrizacionCestas();

						//Mostramos alerta de fila insertada.
						createAlert(okActualizacionFechas,"INFO");

						//Cerramos el popup
						$("#p106_popup").dialog("close");	
					}
				},
				error : function (xhr, status, error){
					handleError(xhr, status, error, locale);
				}
			});	
		}
	});
}

function p106_camposVaciosModificar(){
	var camposVacios = false;
	var msgError = "";

	//Fechas
	var fechaIniActual = $.datepicker.formatDate("dd-mm-yy", $('#p106_fld_fechaIni').datepicker('getDate'));
	var fechaFinActual = $.datepicker.formatDate("dd-mm-yy", $('#p106_fld_fechaFin').datepicker('getDate'));
	if(fechaIniActual == ""){
		msgError = msgError + ", "+ fechaInicioLote;
		camposVacios = true;
	}

	if(fechaFinActual == ""){
		msgError = msgError + ", "+ fechaFinLote;
		camposVacios = true;
	}

	//Si hay campos vacíos mostramos error.
	if(camposVacios){
		//Quitamos la primera coma
		msgError = msgError.substring(1)
		createAlert(rellenarCampos + msgError,"ERROR");
	}

	return camposVacios;
}