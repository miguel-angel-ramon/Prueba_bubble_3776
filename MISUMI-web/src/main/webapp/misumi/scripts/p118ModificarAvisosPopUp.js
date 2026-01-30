/**
 * Variables globales
 */

//Guarda la fila actual que se esta tratando.
var filaActual = null;

var datosObligatoriosAviso = null;
var horaIniFormatoAviso = null;
var horaFinFormatoAviso = null;
var errorActualizar = null;
var errorInsertar = null;
var mensajeActualizarOk = null;
var mensajeInsertarOk = null;
var sinModificacion = null;

var fechaFinAnteriorFechaIni = null;
var fechaIniPosteriorFechaFin = null;

//Variables de texto
var modificar = null;
var nuevo = null;


function AvisosSiecClass(codAviso, fechaIni, horaIni, fechaFin, horaFin,	mensajePc, mensajePda, flgHiper, flgSuper, flgFranquicia,flgEroski, flgCpb, 
		flgVegalsa, flgMercat, lstBorrar){

	this.codAviso = codAviso;
	this.fechaIni = fechaIni;
	this.horaIni = horaIni;
	this.fechaFin = fechaFin;
	this.horaFin = horaFin;
	this.mensajePc = mensajePc;
	this.mensajePda = mensajePda;
	this.flgHiper = flgHiper;
	this.flgSuper = flgSuper;
	this.flgFranquicia = flgFranquicia;
	this.flgEroski = flgEroski;
	this.flgCpb = flgCpb;
	this.flgVegalsa = flgVegalsa;
	this.flgMercat = flgMercat;
	this.lstBorrar = lstBorrar;

	this.prepareToJsonObject = function(){
		var jsonObject = null;
		jsonObject = {
				'codAviso': this.codAviso,
				'fechaIni': this.fechaIni,
				'horaIni': this.horaIni,
				'fechaFin': this.fechaFin,
				'horaFin': this.horaFin,
				'mensajePc': this.mensajePc,
				'mensajePda': this.mensajePda,
				'flgEroski': this.flgEroski,
				'flgCpb': this.flgCpb,
				'flgVegalsa': this.flgVegalsa,
				'flgMercat': this.flgMercat,
				'flgHiper': this.flgHiper,
				'flgSuper': this.flgSuper,
				'flgFranquicia': this.flgFranquicia,
				'lstBorrar': this.lstBorrar,
		};
		return jsonObject;
	};

};

function initializeScreenP118(){
	loadP118(locale);

	p118_inicializarPopUp();
	p118_inicializarFechas();

	events_p118_nuevoModificar();
}

function loadP118(locale){

	this.i18nJSON = './misumi/resources/p118ModificacarAvisosSiecPopUp/p118ModificacarAvisosSiecPopUp_' + locale + '.json';

	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		btnModificar = data.btnModificar;
		btnNuevo = data.btnNuevo;
		errorActualizar = data.errorActualizar;
		errorInsertar = data.errorInsertar;
		mensajeActualizarOk = data.mensajeActualizarOk;
		mensajeInsertarOk = data.mensajeInsertarOk;
		datosObligatoriosAviso = data.datosObligatoriosAviso;
		horaIniFormatoAviso = data.horaIniFormatoAviso;
		horaFinFormatoAviso = data.horaFinFormatoAviso;
		sinModificacion = data.sinModificacion;
		fechaFinAnteriorFechaIni = data.fechaFinAnteriorFechaIni;
		fechaIniPosteriorFechaFin = data.fechaIniPosteriorFechaFin;
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

function p118_inicializarPopUp(){
	//Definión del popup que contendrá los formularios
	$("#p118_popup").dialog({
		autoOpen: false,
		height: 'auto',
		width: 'auto',
		modal: true,
		title: "",
		resizable: false,
		dialogClass: "",
		open: function() {
			$("#p118_fld_codAviso").hide();
		},
		close:function(){
			//Limpiamos el popup
			p118_limpiarPopUp();		
			$("#p118_fld_codAviso").show();
			$(gridP117.nameJQuery).jqGrid('resetSelection');
		},
		stack:false
	});
}

function p118_inicializarFechas(){
	$.datepicker.setDefaults($.datepicker.regional["esMisumi"]);
	$("#p118_fld_fechaIni").datepicker({
		onSelect: function(dateText, inst) {
			var fecFin = $("#p118_fld_fechaFin").datepicker("getDate");
			var fecIni = $("#p118_fld_fechaIni").datepicker("getDate");
			if(fecFin != null && fecIni > fecFin){
				createAlert(fechaIniPosteriorFechaFin, "ERROR");
				var fechaIniAnterior = ($('#p118_fld_fechaIni').data("oldDate") != undefined && $('#p118_fld_fechaIni').data("oldDate") != null) ? $('#p118_fld_fechaIni').data("oldDate") : fecFin;
				$('#p118_fld_fechaIni').datepicker('setDate', fechaIniAnterior);
			}else{
				$('#p118_fld_fechaIni').data("oldDate",fecIni);
			}
		}
	});

	$("#p118_fld_fechaFin" ).datepicker({
		onSelect: function(dateText, inst) {
			var fecFin = $("#p118_fld_fechaFin").datepicker("getDate");
			var fecIni = $("#p118_fld_fechaIni").datepicker("getDate");
			if(fecIni != null && fecFin < fecIni){
				createAlert(fechaFinAnteriorFechaIni, "ERROR");				
				var fechaFinAnterior = ($('#p118_fld_fechaFin').data("oldDate") != undefined && $('#p118_fld_fechaFin').data("oldDate") != null) ? $('#p118_fld_fechaFin').data("oldDate") : fecIni;
				$('#p118_fld_fechaFin').datepicker('setDate', fechaFinAnterior)
			}else{
				$('#p118_fld_fechaFin').data("oldDate",fecFin);
			}
			
		}
	});
}

function p118OpenPopUp(fila){
	if(fila != null){
		filaActual = fila;

		$.ajax({
			type : 'GET',			
			url : './gestionAvisosSiec/popup/loadAvisosSiec.do?codAviso='+ fila.codAviso,
			success : function(data) {	

				var fechaIniSplit = fila.fechaIni.split("-");
				var anioIni = fechaIniSplit[2];
				var mesIni = fechaIniSplit[1] - 1;
				var diaIni = fechaIniSplit[0];

				var fechaIni = new Date(anioIni,mesIni,diaIni);
				$('#p118_fld_fechaIni').datepicker({ dateFormat: 'yy-mm-dd' });
				$('#p118_fld_fechaIni').datepicker('setDate', fechaIni);
				$('#p118_fld_fechaIni').data('oldDate', fechaIni);
				$("#p118_fld_horaIni").val(fila.horaIni);
				
				var fechaFinSplit = fila.fechaFin.split("-");
				var anioFin = fechaFinSplit[2];
				var mesFin = fechaFinSplit[1] - 1;
				var diaFin = fechaFinSplit[0];
				if(fechaFinSplit.length==3){
					var fechaFin = new Date(anioFin,mesFin,diaFin);
					$('#p118_fld_fechaFin').datepicker({ dateFormat: 'yy-mm-dd' });
					$('#p118_fld_fechaFin').datepicker('setDate', fechaFin);
					$('#p118_fld_fechaFin').data('oldDate', fechaFin); 
				}
				
				$("#p118_fld_horaFin").val(fila.horaFin);
				
				$("#p118_fld_mensajePc_val").val(fila.mensajePc);
				
				$("#p118_fld_mensajePda_val").val(fila.mensajePda);
				
				if(data.flgEroski=='N'){
					$("#p118_fld_eroski").prop("checked", false);
				}else{
					$("#p118_fld_eroski").prop("checked", true);
				}
				
				if(data.flgCpb=='N'){
					$("#p118_fld_cpb").prop("checked", false);
				}else{
					$("#p118_fld_cpb").prop("checked", true);
				}
				
				if(data.flgVegalsa=='N'){
					$("#p118_fld_vegalsa").prop("checked", false);
				}else{
					$("#p118_fld_vegalsa").prop("checked", true);
				}
				
				if(data.flgMercat=='N'){
					$("#p118_fld_mercat").prop("checked", false);
				}else{
					$("#p118_fld_mercat").prop("checked", true);
				}
				
				if(data.flgHiper=='N'){
					$("#p118_fld_hiper").prop("checked", false);
				}else{
					$("#p118_fld_hiper").prop("checked", true);
				}
				
				if(data.flgSuper=='N'){
					$("#p118_fld_super").prop("checked", false);
				}else{
					$("#p118_fld_super").prop("checked", true);
				}
				
				if(data.flgFranquicia=='N'){
					$("#p118_fld_franquicia").prop("checked", false);
				}else{
					$("#p118_fld_franquicia").prop("checked", true);
				}
				
				//Texto botón
				$("#p118_btn_new").val(btnModificar);

			},
			error : function (xhr, status, error){
				handleError(xhr, status, error, locale);				
			}			
		});		
	}else{
		var fechaIni = new Date();
		var hora= fechaIni.getHours();
		if(hora<10){
			hora="0"+hora;
		}
		var minutos= fechaIni.getMinutes();
		if(minutos<10){
			minutos="0"+minutos;
		}
		$('#p118_fld_fechaIni').datepicker({ dateFormat: 'yy-mm-dd' });
		$('#p118_fld_fechaIni').datepicker('setDate', fechaIni);
		$('#p118_fld_fechaIni').data('oldDate', fechaIni);
		
		$("#p118_fld_horaIni").val(hora+":"+minutos);
		
		$("#p118_fld_horaFin").val("");
		$("#p118_fld_mensajePc_val").val("");
		$("#p118_fld_mensajePda_val").val("");
		$("#p118_fld_eroski").prop("checked", true);
		$("#p118_fld_cpb").prop("checked", false);
		$("#p118_fld_vegalsa").prop("checked", false);
		$("#p118_fld_mercat").prop("checked", false);
		$("#p118_fld_hiper").prop("checked", false);
		$("#p118_fld_super").prop("checked", false);
		$("#p118_fld_franquicia").prop("checked", false);
		$("#p118_btn_new").val(btnNuevo);
	}
	$("#p118_popup").dialog("open");
}

function events_p118_nuevoModificar(){
	$("#p118_btn_new").click(function(){
		//Si la fila actual tiene valor, es modificación.
		if(!comprobarCamposVacios()){
			// Fecha inicio
			var fechaIniActual = $.datepicker.formatDate("dd-mm-yy", $('#p118_fld_fechaIni').datepicker('getDate'));
			var fechaIniActualDatePartes = fechaIniActual.split("-");
			var fechaIniAnio = fechaIniActualDatePartes[2];
			var fechaIniMes = fechaIniActualDatePartes[1] - 1;
			var fechaIniDia = fechaIniActualDatePartes[0];
			var fechaIniModi = new Date(fechaIniAnio,fechaIniMes,fechaIniDia);
			// Hora inicio
			var horaIniModi = $("#p118_fld_horaIni").val();
			// Fecha fin
			var fechaFinActual = $.datepicker.formatDate("dd-mm-yy", $('#p118_fld_fechaFin').datepicker('getDate'));
			var fechaFinActualDatePartes = fechaFinActual.split("-");
			var fechaFinAnio = fechaFinActualDatePartes[2];
			var fechaFinMes = fechaFinActualDatePartes[1] - 1;
			var fechaFinDia = fechaFinActualDatePartes[0];
			var fechaFinModi = new Date(fechaFinAnio,fechaFinMes,fechaFinDia);
			// Hora fin
			var horaFinModi = $("#p118_fld_horaFin").val();
			// Mensajes
			var mensajePcModi = $("#p118_fld_mensajePc_val").val();
			var mensajePdaModi = $("#p118_fld_mensajePda_val").val();
			// Checks sociedades afectadas
			var flgEroskiModi = ($("#p118_fld_eroski").prop('checked')?"S":"N");
			var flgCpbModi = ($("#p118_fld_cpb").prop('checked')?"S":"N");
			var flgVegalsaModi = ($("#p118_fld_vegalsa").prop('checked')?"S":"N");
			var flgMercatModi = ($("#p118_fld_mercat").prop('checked')?"S":"N");
			// Checks sociedades afectadas
			var flgHiperModi = ($("#p118_fld_hiper").prop('checked')?"H":"N");
			var flgSuperModi = ($("#p118_fld_super").prop('checked')?"S":"N");
			var flgFranquiciaModi = ($("#p118_fld_franquicia").prop('checked')?"F":"N");
			if(filaActual != null){
				var avisosSiec = new AvisosSiecClass(filaActual.codAviso,fechaIniModi, horaIniModi,fechaFinModi, horaFinModi, mensajePcModi, mensajePdaModi,
						 flgHiperModi, flgSuperModi, flgFranquiciaModi,flgEroskiModi, flgCpbModi, flgVegalsaModi,flgMercatModi, null);
				var url='./gestionAvisosSiec/updateRow.do';
				var objJson = $.toJSON(avisosSiec);	
				$.ajax({
					type : 'POST',
					url : url,
					data: objJson,
					contentType : "application/json; charset=utf-8",
					success : function(data) {	
						//Si hay error, repintamos el grid.
						if(data == "0"){	
							$("#p117_AreaResultados").hide();
							//Refrescamos el grid
							loadData_gridP117GestionAvisosSiec();
							//Mostramos alerta de fila insertada.
							createAlert(mensajeActualizarOk,"INFO");
							//Cerramos el popup
							$("#p118_popup").dialog("close");	
						}else{								
							createAlert(errorActualizar,"ERROR");
							//Cerramos el popup
							$("#p118_popup").dialog("close");	
						}	
						$(gridP117.nameJQuery).jqGrid('resetSelection');
					},
					error : function (xhr, status, error){
						handleError(xhr, status, error, locale);
					}
				});
			}else{
				var avisosSiec = new AvisosSiecClass(null,fechaIniModi, horaIniModi,fechaFinModi, horaFinModi, mensajePcModi, mensajePdaModi,
						 flgHiperModi, flgSuperModi, flgFranquiciaModi,flgEroskiModi, flgCpbModi, flgVegalsaModi,flgMercatModi, null);
				var url='./gestionAvisosSiec/insertRow.do';
				var objJson = $.toJSON(avisosSiec);	
				$.ajax({
					type : 'POST',
					url : url,
					data: objJson,
					contentType : "application/json; charset=utf-8",
					success : function(data) {	
						//Si hay error, repintamos el grid.
						if(data == "0"){			
							$("#p117_AreaResultados").hide();
							//Refrescamos el grid
							loadData_gridP117GestionAvisosSiec();
							//Mostramos alerta de fila insertada.
							createAlert(mensajeInsertarOk,"INFO");
							//Cerramos el popup
							$("#p118_popup").dialog("close");	
						}else{
							createAlert(errorInsertar,"ERROR");
							//Cerramos el popup
							$("#p118_popup").dialog("close");	
						}
						$(gridP117.nameJQuery).jqGrid('resetSelection');
					},
					error : function (xhr, status, error){
						handleError(xhr, status, error, locale);
					}
				});
			}
		}
	});
}

function comprobarCamposVacios(){
	var camposVacios = false;
	var msgError = "";

	var fechaIniActual = $.datepicker.formatDate("dd-mm-yy", $('#p118_fld_fechaIni').datepicker('getDate'));
	var horaIniActual = $("#p118_fld_horaIni").val();
	var mensajePcActual = $("#p118_fld_mensajePc_val").val();
	var mensajePdaActual = $("#p118_fld_mensajePda_val").val();
	var flgEroskiActual = $("#p118_fld_eroski").prop('checked');
	var flgCpbActual = $("#p118_fld_cpb").prop('checked');
	var flgVegalsaActual = $("#p118_fld_vegalsa").prop('checked');
	var flgMercatActual = $("#p118_fld_mercat").prop('checked');
	var flgHiperActual = $("#p118_fld_hiper").prop('checked');
	var flgSuperActual = $("#p118_fld_super").prop('checked');
	var flgFranquiciaActual = $("#p118_fld_franquicia").prop('checked');
	
	if(fechaIniActual == "" || horaIniActual == "" || mensajePcActual == "" || mensajePdaActual == "" 
		|| (!flgEroskiActual && !flgCpbActual && !flgVegalsaActual && !flgMercatActual) || (!flgHiperActual && !flgSuperActual && !flgFranquiciaActual)){
			msgError = datosObligatoriosAviso;
			camposVacios = true;
	}else{
		//Validar formato hora ini
		if(horaIniActual.indexOf(":") != -1 || horaIniActual.indexOf(":") != 2){
			var horasMinutos = horaIniActual.split(":");
			if(horasMinutos.length==2){
				var horasIniActual=horasMinutos[0];
				var minutosIniActual=horasMinutos[1];
				if(horasIniActual.length==2 && minutosIniActual.length==2){
					if(isNaN(parseInt(horasIniActual)) || isNaN(parseInt(minutosIniActual))){
						msgError = horaIniFormatoAviso;
						camposVacios = true;
					}else{
						if(parseInt(horasIniActual)<0 || parseInt(horasIniActual)>23 || parseInt(minutosIniActual)<0 || parseInt(minutosIniActual)>59){
							msgError = horaIniFormatoAviso;
							camposVacios = true;
						}
					}
				}else{
					msgError = horaIniFormatoAviso;
					camposVacios = true;
				}
			}else{
				msgError = horaIniFormatoAviso;
				camposVacios = true;
			}
		}else{
			msgError = horaIniFormatoAviso;
			camposVacios = true;
		}
	}
	
	//Validar formato hora fin
	var horafinActual = $("#p118_fld_horaFin").val();
	if(horafinActual != "" && !camposVacios){
		if(horafinActual.indexOf(":") != -1 || horafinActual.indexOf(":") != 2){
			var horasMinutosFin = horafinActual.split(":");
			if(horasMinutosFin.length==2){
				var horasFinActual=horasMinutosFin[0];
				var minutosFinActual=horasMinutosFin[1];
				if(horasFinActual.length==2 && minutosFinActual.length==2){
					if(isNaN(parseInt(horasFinActual)) || isNaN(parseInt(minutosFinActual))){
						msgError = horaFinFormatoAviso;
						camposVacios = true;
					}else{
						if(parseInt(horasFinActual)<0 || parseInt(horasFinActual)>23 || parseInt(minutosFinActual)<0 || parseInt(minutosFinActual)>59){
							msgError = horaFinFormatoAviso;
							camposVacios = true;
						}
					}
				}else{
					msgError = horaFinFormatoAviso;
					camposVacios = true;
				}
			}else{
				msgError = horaFinFormatoAviso;
				camposVacios = true;
			}
		}else{
			msgError = horaFinFormatoAviso;
			camposVacios = true;
		}
	}
	
//	
//	if(horaIniActual != "" && !camposVacios)
	//Si hay campos vacíos mostramos error.
	if(camposVacios){
		//Quitamos la primera coma
		createAlert(msgError,"ERROR");
	}

	return camposVacios;
}

function p118_limpiarPopUp(){
	//Reseteamos el popup

	//Vaciamos todos los campos
	$('#p118_fld_fechaIni').datepicker('setDate',null);
	$('#p118_fld_fechaIni').data("oldDate",null);
	$('#p118_fld_fechaFin').datepicker('setDate', null);
	$('#p118_fld_fechaFin').data("oldDate",null);
	$("#p118_fld_horaIni").val("");
	$("#p118_fld_horaFin").val("");
	$("#p118_fld_mensajePc_val").val("");
	$("#p118_fld_mensajePda_val").val("");
	$("#p118_fld_eroski").prop("checked", false);
	$("#p118_fld_cpb").prop("checked", false);
	$("#p118_fld_vegalsa").prop("checked", false);
	$("#p118_fld_mercat").prop("checked", false);
	$("#p118_fld_hiper").prop("checked", false);
	$("#p118_fld_super").prop("checked", false);
	$("#p118_fld_franquicia").prop("checked", false);
	filaActual=null;
	//Texto botón
	$("#p118_btn_new").val(btnNuevo);

}