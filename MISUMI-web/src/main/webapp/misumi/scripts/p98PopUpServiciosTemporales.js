/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/****************************************************************** CONSTANTES ***************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/


/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/**************************************************************** VARIABLES ******************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
//Título del popup
var popUpTitleP98;

//Mensajes de error y guardado.
var noHayCambiosEnServiciosTemporalesP98;
var serviciosTemporalesValidadosExitoP98;
var fechaFinNoMenorFechaInicioP98;
var fechaInicioNoMayorFechaFinP98;
var calendarioDiasMayorMax;

//Botones dialogo pregunta.
var btnSiP98; 
var btnNoP98;

//Guarda el hashMap de las validaciones.
var listadoValidacionesTratadas;

//Variable que guarda un flag para el guardado de cambios estacionales.
var cambiosEstacionalesP98;

var cerrarDialogoP98 = "N";
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************** DOCUMENTO LISTO **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
$(document).ready(function(){
	//Inicializa el popup que contiene el grid con las líneas de devolución
	initializeScreenP98ServiciosTemporales();
});


/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************ INICIALIZACIÓN DE ELEMENTOS E IDIOMA *******************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
function initializeScreenP98ServiciosTemporales(){
	//Inicializamos el idioma.
	loadP98(locale);

	//Inicializamos el popup
	load_dialog_P98();	

	//Eventos de guardado.
	events_p98_btnGuardar();
}

//Carga el diálogo de los servicios estacionales.
function load_dialog_P98(){
	$("#p98_popUpServiciosTemporales").dialog({
		autoOpen: false,
		height:'500',
		width:'800',
		modal: true,
		resizable: false,
		open: function() {
		},
		beforeClose: function(event, ui){	
			//Si se han cambiado datos pero no se han guardado, avisar al usuario de que no se han guardado los datos
			//y preguntar si realmente quiere cerrar el dialogo. Si el usuario pulsa si, se cierra, por lo que volvería aquí
			//y como hay cambios nos volvería a preguntar así hasta el infinito, por lo que hay que usar un flag que evite
			//que entre si se ha pulsado Si, para ello utilizamos cerrarDialogoP98. Al cerrar se pone en N y así si se vuelve
			//a cerrar volverá a preguntar si hay cambios.
			if(existeCambioDeFechaServicioTemporal() && cerrarDialogoP98 == "N"){
				event.preventDefault();
				event.stopPropagation();

				$("#p98dialog-confirmCerrarPopUp").dialog({
					resizable: false,
					height:160,
					modal: true,
					buttons: [
					          {
					        	  text:btnSiP98,
					        	  click: function() {
					        		  //Indicamos que se cierre el dialogo y así evitamos que vuelva a entrar aquí al cerrar el dialogo.
					        		  cerrarDialogoP98 = "S";
					        		  $(this).dialog("close");

					        		  //Cerrar el popup de cambios estacionales y cerrar el dialogo de pregunta.
					        		  $("#p98_popUpServiciosTemporales").dialog('close');
					        	  }
					          },{
					        	  text:btnNoP98,
					        	  click: function() {					        		  					        	
					        		  //Cerrar el dialogo de pregunta.
					        		  $(this).dialog( "close" );


					        		  //Evitar que se cierre el popup.
					        		  return false;
					        	  }
					          }
					          ]
				});	
			}
		},
		close: function( event, ui ) {
			cerrarDialogoP98 = "N";
			//Si no se ha calculado calendario todavía, calcularlo al cerrar el popup. O si se ha hecho un guardado del popup, calcular de nuevo los días
			//pues los cambios en cambios estacionales cambian el calendario.
			//if(!$('#p96_calendario_dias').children().length > 0 || cambiosEstacionalesP98 == "S"){
			/*if(!$('#p96_calendario_dias').children().length > 0){
				//Recalculamos los días del calendario porque cambiar los dias estacionales hace que haya cambios en el calendario.
				//Cargamos los días en la temporal después de consultar el PLSQL, por eso la recarga es N.
				load_diasCalendarioP96(comboValueAnioP96,comboValueServicioP96,"N",null);

				//Ponemos el flag a N para que si hay errores, y se pulsa aceptar, no me cierre el dialogo.
				//cambiosEstacionalesP98 = "N";
			}*/	
		}
	});
}

//Función que carga las variables de lenguaje json.
function loadP98(locale){
	this.i18nJSON = './misumi/resources/p98PopUpServiciosTemporales/p98PopUpServiciosTemporales_' + locale + '.json';

	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {
		popUpTitleP98 = data.popUpTitle;
		noHayCambiosEnServiciosTemporalesP98 = data.noHayCambiosEnServiciosTemporales;
		serviciosTemporalesValidadosExitoP98 = data.serviciosTemporalesValidadosExito;
		fechaFinNoMenorFechaInicioP98 = data.fechaFinNoMenorFechaInicio;
		fechaInicioNoMayorFechaFinP98 = data.fechaInicioNoMayorFechaFin;
		btnSiP98 = data.btnSi; 
		btnNoP98 = data.btnNo;
		calendarioDiasMayorMax = data.calendarioDiasMayorMax;
	})
	.success(function(data) {

	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

//Carga los servicios temporales relacionados con un ejercicio.
function load_serviciosTemporalesP98(){
	//Inicializamos el combo y obtenemos el código del ejercicio.
	var options = "";
	var codigoEjercicio = comboValueAnioP96.substring(0,4);

	//var anio = $("#p98_cmb_anio").val();
	$.ajax({
		type : 'POST',
		url : './calendario/popup/serviciosTemporales/consultarCambiosEstacionales.do?codigoEjercicio='+codigoEjercicio,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {
			//Asignamos al diálogo el título con el día pinchado.
			$("#p98_popUpServiciosTemporales").dialog({ title:popUpTitleP98});

			if(data != null){
				if(data.pCodError == 0){
					//Busca los servicios de los cambios estacionales y rellena el combo.
					listadoValidacionesTratadas = data.listadoValidacionesTratadas;
					for (var key in listadoValidacionesTratadas) {
						options = options + "<option value='" + key + "'>" + key + "-" +  listadoValidacionesTratadas[key].denominacionServicio + "</option>";									
					}
					//Rellena el combo de servicios.
					$("select#p98_cmb_servicio").html(options);

					//Obtiene el primer valor dcel combo
					comboValueP98 = $("#p98_cmb_servicio option:first").val();

					//Indica que dibuje los cambios estacionales del primer servicio del combo.
					dibujarServiciosTemporales(comboValueP98);

					//Selecciona el primer elemento del combo.
					$("#p98_cmb_servicio").combobox('autocomplete',$("#p98_cmb_servicio option:first").val()+"-"+listadoValidacionesTratadas[$("#p98_cmb_servicio option:first").val()].denominacionServicio);
					$("#p98_cmb_servicio").combobox('comboautocomplete',$("#p98_cmb_servicio option:first").val());


					showHideButtonsP98(comboValueAnioP96); //Se le pasa el codigo compuesto por año y tipo de Ejercicio

					//Si pendiente de validar es S se muestra el popup nada más pintarlo.
					/*if(pendienteDeValidar == "S" && flgCambioEstacional == "S"){
						$("#p98_popUpServiciosTemporales").dialog('open');
					}*/


					$("#p98_popUpServiciosTemporales").dialog('open');


					$("#p96_btn_cambiosEstacionalesBloque").show();
				}else{
					//createAlert(data.pDescError,"ERROR");
					//Si no hay datos o hay error se oculta el botón.
					$("#p96_btn_cambiosEstacionalesBloque").hide();
				}
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});	

	$("#p98_cmb_servicio").combobox({
		selected: function(event, ui) {
			if ( ui.item.value!="" && ui.item.value!="null" ) {
				//Si el valor seleccionado difiere del valor seleccionado anterior
				//buscamos, si es el mismo no.
				if(ui.item.value != comboValueP98){							
					comboValueP98 = $("#p98_cmb_servicio").val();
					if (comboValueP98 !=null){						
						dibujarServiciosTemporales(comboValueP98);
					}
				}
			}
		}  ,
	}); 
}



//Mostramos los botones según el rol y los flags correspondientes del año seleccionado.
function showHideButtonsP98(anioTipoEjercicio){
	//Obtenemos los flags para pintar los botones.
	var flgPendienteValidarEjercicio98 = $("#flgPendienteValidarEjercicio"+anioTipoEjercicio).val();
	var flgModificarServicioCentro98 = $("#flgModificarServicioCentro"+anioTipoEjercicio).val();
	var flgModificarCalendarioCentro98 = $("#flgModificarCalendarioCentro"+anioTipoEjercicio).val();
	var flgModificarServicioTecnico98 = $("#flgModificarServicioTecnico"+anioTipoEjercicio).val();
	var flgModificarCalendarioTecnico98 = $("#flgModificarCalendarioTecnico"+anioTipoEjercicio).val();
	var tipoEjercicio98 = $("#tipoEjercicio"+anioTipoEjercicio).val();

	if (tipoEjercicio98 == "P") { // Si el Tipo de Ejercicio es 'Planificacion'

		//if(flgPendienteValidarEjercicio98 == "S" && ((flgModificarServicioTecnico98 == "S" && (rol == "1" || rol == "4"))||(flgModificarCalendarioCentro98 == "S" && rol == "2"))){
		if  ((rol == "4") || (flgPendienteValidarEjercicio98 == "S" && ((flgModificarServicioTecnico98 == "S" && (rol == "1"))||(flgModificarCalendarioCentro98 == "S" && rol == "2")))) {
			$("#p98_buttons").show();
		}else{
			$("#p98_buttons").hide();
		}

	} else {

		if (tipoEjercicio98 == "E") { // Si el Tipo de Ejercicio es 'Ejecución'
			if  (rol == "4") {
				$("#p98_buttons").show();
			} else {
				$("#p98_buttons").hide();
			}
		}
	}
}

//Función que dibuja los servicios habituales de un servicio y los servicios temporales.
function dibujarServiciosTemporales(comboValue){
	//Reseteamos los servicios temporales y habituales.
	reset_p98();

	//Obtiene un servicio que tiene una lsita de servicios habituales
	var valorCombo = listadoValidacionesTratadas[comboValue];

	//Guardan las estructuras
	var estructuraFormServicioHabitual;
	var estructuraFormServicioTemporal;

	var estructuraFormServicioHabitualTitulo;
	var estructuraFormServicioTemporalTitulo;

	for(var i = 0; i< valorCombo.calServHabLst.length; i++){
		//Se obtiene el servicio habitual
		var servHab = valorCombo.calServHabLst[i];

		//Obtenemos la estructura del título azul de servicio habitual
		estructuraFormServicioHabitualTitulo = getEstructuraServicioHabitualTitulo(comboValue,i);

		//Se obtiene la esctructura de los días con sus hiddens y se cambian los ids adecuados al nuevo formulario. Además se le añade la estructura del
		//título azul
		estructuraFormServicioHabitual = estructuraFormServicioHabitualTitulo + getEstructuraServicioHabitualTemporalP98(comboValue,i,"");

		//Se inserta el formulario en su sección correspondiente del popup
		$("#p98_calendarios").append(estructuraFormServicioHabitual);

		//Se llama a la función que pinta la información del servicio habitual. Camiones, etc.
		rellenarServicioHabitualTemporalP98(comboValue,i,"",servHab,"S",valorCombo.denominacionServicio);

		//Se llama a la función que rellena la información del servicio habitual oculto.
		rellenarInformacionServicioHabitualTemporalHiddenP98(comboValue,i,"",servHab,"S");	

		//Obtenemos la estructura del titulo de los servicios temporales.
		estructuraFormServicioTemporalTitulo = getEstructuraServicioTemporalTitulo(comboValue,i,j);

		//Por cada servicio habitual pintamos los servicios temporales.
		for(var j = 0;j<servHab.calendarioServicioLst.length;j++){
			//Obtenemos el servicio temporal.
			var servTemp = servHab.calendarioServicioLst[j];

			//Por cada servicio temporal obtenemos su fecha inicio y fecha fin.
			estructuraFormServicioTemporalFechaInicioFin = getEstructuraServicioTemporalFechaInicioFin(comboValue,i,j);

			//Se obtiene la esctructura y se cambian los ids adecuados al nuevo formulario.Si es el primer bloque temporal se pone titulo (Servicio temporal), si no, no.
			if(j==0){
				estructuraFormServicioTemporal =  estructuraFormServicioTemporalTitulo + estructuraFormServicioTemporalFechaInicioFin + getEstructuraServicioHabitualTemporalP98(comboValue,i,j);
			}else{
				estructuraFormServicioTemporal = estructuraFormServicioTemporalFechaInicioFin + getEstructuraServicioHabitualTemporalP98(comboValue,i,j);
			}

			//Se inserta el formulario en su sección correspondiente del popup
			$("#p98_calendarios").append(estructuraFormServicioTemporal);

			//Se transforman los input de fecha de input a datepicker. Es necesario hacerlo una vez se han añadido al dom
			//html.
			transformToDatePicker(comboValue,i,j,servTemp);

			//Asignamos evento al check.
			events_p98_checkBox();

			//Se llama a la función que pinta la información del servicio temporal del servicio habitual.
			rellenarServicioHabitualTemporalP98(comboValue,i,j,servTemp,"N",valorCombo.denominacionServicio);

			//Se llama a la función que rellena la información del servicio temporal del servicio habitual oculta.
			rellenarInformacionServicioHabitualTemporalHiddenP98(comboValue,i,j,servTemp,"N");	

			//Checkear o descheckear el checkbox
			rellenarCheckBoxServicioTemporalP98(comboValue,i,j,servTemp);
		}


	}
}

//La estructura del servicio temporal y habitual es compartida, por lo que la usamos para los dos casos
function getEstructuraServicioHabitualTemporalP98(servicio,indiceServHab, indiceServTemp){
	var estrucutraDiasDeLaSemana = $("#p98_calendario_diasDeLaSemana").prop('outerHTML');

	estrucutraDiasDeLaSemana = estrucutraDiasDeLaSemana.replace(new RegExp("\\p98_calendario_diasDeLaSemana\\b"),"p98_calendario_diasDeLaSemana"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estrucutraDiasDeLaSemana = estrucutraDiasDeLaSemana.replace(new RegExp("\\p98_Lunes\\b"),"p98_Lunes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estrucutraDiasDeLaSemana = estrucutraDiasDeLaSemana.replace(new RegExp("\\p98_Martes\\b"),"p98_Martes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estrucutraDiasDeLaSemana = estrucutraDiasDeLaSemana.replace(new RegExp("\\p98_Miercoles\\b"),"p98_Miercoles"+servicio+"o"+indiceServHab+"o"+indiceServTemp);	
	estrucutraDiasDeLaSemana = estrucutraDiasDeLaSemana.replace(new RegExp("\\p98_Jueves\\b"),"p98_Jueves"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estrucutraDiasDeLaSemana = estrucutraDiasDeLaSemana.replace(new RegExp("\\p98_Viernes\\b"),"p98_Viernes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estrucutraDiasDeLaSemana = estrucutraDiasDeLaSemana.replace(new RegExp("\\p98_Sabado\\b"),"p98_Sabado"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estrucutraDiasDeLaSemana = estrucutraDiasDeLaSemana.replace(new RegExp("\\p98_Domingo\\b"),"p98_Domingo"+servicio+"o"+indiceServHab+"o"+indiceServTemp);

	//Obtenemos los días del calendario
	var estructuraDiasCalendario = $("#p98_calendario_dias").prop('outerHTML');
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\p98_calendario_dias\\b"),"p98_calendario_dias"+servicio+"o"+indiceServHab+"o"+indiceServTemp);


	//Para la estructura LUNES
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraLunes\\b"),"formDiaEstructuraLunes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraDiaMesLunes\\b"),"formDiaEstructuraDiaMesLunes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraDiaMesLunesLbl\\b"),"formDiaEstructuraDiaMesLunesLbl"+servicio+"o"+indiceServHab+"o"+indiceServTemp);	
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoLunes\\b"),"formDiaEstructuraInfoLunes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSuministroLunes\\b"),"formDiaEstructuraInfoSuministroLunes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSuministroCamionCerradoLunes\\b"),"formDiaEstructuraInfoSuministroCamionCerradoLunes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSuministroCamionAbiertoLunes\\b"),"formDiaEstructuraInfoSuministroCamionAbiertoLunes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraEstadoLunes\\b"),"formDiaEstructuraEstadoLunes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);

	//Para la estructura MARTES
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraMartes\\b"),"formDiaEstructuraMartes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraDiaMesMartes\\b"),"formDiaEstructuraDiaMesMartes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraDiaMesMartesLbl\\b"),"formDiaEstructuraDiaMesMartesLbl"+servicio+"o"+indiceServHab+"o"+indiceServTemp);	
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoMartes\\b"),"formDiaEstructuraInfoMartes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSuministroMartes\\b"),"formDiaEstructuraInfoSuministroMartes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSuministroCamionCerradoMartes\\b"),"formDiaEstructuraInfoSuministroCamionCerradoMartes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSuministroCamionAbiertoMartes\\b"),"formDiaEstructuraInfoSuministroCamionAbiertoMartes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraEstadoMartes\\b"),"formDiaEstructuraEstadoMartes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);

	//Para la estructura MIERCOLES
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraMiercoles\\b"),"formDiaEstructuraMiercoles"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraDiaMesMiercoles\\b"),"formDiaEstructuraDiaMesMiercoles"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraDiaMesMiercolesLbl\\b"),"formDiaEstructuraDiaMesMiercolesLbl"+servicio+"o"+indiceServHab+"o"+indiceServTemp);	
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoMiercoles\\b"),"formDiaEstructuraInfoMiercoles"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSuministroMiercoles\\b"),"formDiaEstructuraInfoSuministroMiercoles"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSuministroCamionCerradoMiercoles\\b"),"formDiaEstructuraInfoSuministroCamionCerradoMiercoles"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSuministroCamionAbiertoMiercoles\\b"),"formDiaEstructuraInfoSuministroCamionAbiertoMiercoles"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraEstadoMiercoles\\b"),"formDiaEstructuraEstadoMiercoles"+servicio+"o"+indiceServHab+"o"+indiceServTemp);

	//Para la estructura JUEVES
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraJueves\\b"),"formDiaEstructuraJueves"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraDiaMesJueves\\b"),"formDiaEstructuraDiaMesJueves"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraDiaMesJuevesLbl\\b"),"formDiaEstructuraDiaMesJuevesLbl"+servicio+"o"+indiceServHab+"o"+indiceServTemp);	
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoJueves\\b"),"formDiaEstructuraInfoJueves"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSuministroJueves\\b"),"formDiaEstructuraInfoSuministroJueves"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSuministroCamionCerradoJueves\\b"),"formDiaEstructuraInfoSuministroCamionCerradoJueves"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSuministroCamionAbiertoJueves\\b"),"formDiaEstructuraInfoSuministroCamionAbiertoJueves"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraEstadoJueves\\b"),"formDiaEstructuraEstadoJueves"+servicio+"o"+indiceServHab+"o"+indiceServTemp);

	//Para la estructura VIERNES
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraViernes\\b"),"formDiaEstructuraViernes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraDiaMesViernes\\b"),"formDiaEstructuraDiaMesViernes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraDiaMesViernesLbl\\b"),"formDiaEstructuraDiaMesViernesLbl"+servicio+"o"+indiceServHab+"o"+indiceServTemp);	
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoViernes\\b"),"formDiaEstructuraInfoViernes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSuministroViernes\\b"),"formDiaEstructuraInfoSuministroViernes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSuministroCamionCerradoViernes\\b"),"formDiaEstructuraInfoSuministroCamionCerradoViernes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSuministroCamionAbiertoViernes\\b"),"formDiaEstructuraInfoSuministroCamionAbiertoViernes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraEstadoViernes\\b"),"formDiaEstructuraEstadoViernes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);

	//Para la estructura SABADO
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraSabado\\b"),"formDiaEstructuraSabado"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraDiaMesSabado\\b"),"formDiaEstructuraDiaMesSabado"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraDiaMesSabadoLbl\\b"),"formDiaEstructuraDiaMesSabadoLbl"+servicio+"o"+indiceServHab+"o"+indiceServTemp);	
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSabado\\b"),"formDiaEstructuraInfoSabado"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSuministroSabado\\b"),"formDiaEstructuraInfoSuministroSabado"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSuministroCamionCerradoSabado\\b"),"formDiaEstructuraInfoSuministroCamionCerradoSabado"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSuministroCamionAbiertoSabado\\b"),"formDiaEstructuraInfoSuministroCamionAbiertoSabado"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraEstadoSabado\\b"),"formDiaEstructuraEstadoSabado"+servicio+"o"+indiceServHab+"o"+indiceServTemp);

	//Para la estructura DOMINGO
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraDomingo\\b"),"formDiaEstructuraDomingo"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraDiaMesDomingo\\b"),"formDiaEstructuraDiaMesDomingo"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraDiaMesDomingoLbl\\b"),"formDiaEstructuraDiaMesDomingoLbl"+servicio+"o"+indiceServHab+"o"+indiceServTemp);	
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoDomingo\\b"),"formDiaEstructuraInfoDomingo"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSuministroDomingo\\b"),"formDiaEstructuraInfoSuministroDomingo"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSuministroCamionCerradoDomingo\\b"),"formDiaEstructuraInfoSuministroCamionCerradoDomingo"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSuministroCamionAbiertoDomingo\\b"),"formDiaEstructuraInfoSuministroCamionAbiertoDomingo"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bformDiaEstructuraEstadoDomingo\\b"),"formDiaEstructuraEstadoDomingo"+servicio+"o"+indiceServHab+"o"+indiceServTemp);

	//Para los datos no visibles LUNES	
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bservicioLunes\\b"),"servicioLunes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);

	//Para los datos no visibles MARTES	
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bservicioMartes\\b"),"servicioMartes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);

	//Para los datos no visibles MIERCOLES	
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bservicioMiercoles\\b"),"servicioMiercoles"+servicio+"o"+indiceServHab+"o"+indiceServTemp);

	//Para los datos no visibles JUEVES
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bservicioJueves\\b"),"servicioJueves"+servicio+"o"+indiceServHab+"o"+indiceServTemp);

	//Para los datos no visibles VIERNES	
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bservicioViernes\\b"),"servicioViernes"+servicio+"o"+indiceServHab+"o"+indiceServTemp);

	//Para los datos no visibles SABADO	
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bservicioSabado\\b"),"servicioSabado"+servicio+"o"+indiceServHab+"o"+indiceServTemp);

	//Para los datos no visibles DOMINGO	
	estructuraDiasCalendario = estructuraDiasCalendario.replace(new RegExp("\\bservicioDomingo\\b"),"servicioDomingo"+servicio+"o"+indiceServHab+"o"+indiceServTemp);

	return estrucutraDiasDeLaSemana + estructuraDiasCalendario;
}

//Obtiene la estructura del título azul.
function getEstructuraServicioHabitualTitulo(servicio,indiceServHab){
	var estrucutraServicioHabitualTitulo = $("#p98_servicioHabitualSelec").prop('outerHTML');

	estrucutraServicioHabitualTitulo = estrucutraServicioHabitualTitulo.replace(new RegExp("\\bp98_servicioHabitualSelec\\b"),"p98_servicioHabitualSelec"+servicio+"o"+indiceServHab);
	estrucutraServicioHabitualTitulo = estrucutraServicioHabitualTitulo.replace(new RegExp("\\bp98_lbl_servicioHabitual\\b"),"p98_lbl_servicioHabitual"+servicio+"o"+indiceServHab);
	estrucutraServicioHabitualTitulo = estrucutraServicioHabitualTitulo.replace(new RegExp("\\bp98_lbl_servicioHabitualSelec\\b"),"p98_lbl_servicioHabitualSelec"+servicio+"o"+indiceServHab);

	return estrucutraServicioHabitualTitulo;
}

//Obtiene la estructura del título rojo.
function getEstructuraServicioTemporalTitulo(servicio,indiceServHab,indiceServTemp){
	var estrucutraServicioTemporalTitulo = $("#p98_servicioTemporalSelec").prop('outerHTML');

	estrucutraServicioTemporalTitulo = estrucutraServicioTemporalTitulo.replace(new RegExp("\\bp98_servicioTemporalSelec\\b"),"p98_servicioTemporalSelec"+servicio+"o"+indiceServHab);
	estrucutraServicioTemporalTitulo = estrucutraServicioTemporalTitulo.replace(new RegExp("\\bp98_lbl_servicioTemporal\\b"),"p98_lbl_servicioTemporal"+servicio+"o"+indiceServHab);
	estrucutraServicioTemporalTitulo = estrucutraServicioTemporalTitulo.replace(new RegExp("\\bp98_lbl_servicioTemporalSelec\\b"),"p98_lbl_servicioTemporalSelec"+servicio+"o"+indiceServHab);

	return estrucutraServicioTemporalTitulo;
}

//Obtiene la estructura de los input del datepicker.
function getEstructuraServicioTemporalFechaInicioFin(servicio,indiceServHab,indiceServTemp){
	var estrucutraServicioTemporalFechaInicioFin = $("#p98_fechaCalendarioSelec").prop('outerHTML');

	estrucutraServicioTemporalFechaInicioFin = estrucutraServicioTemporalFechaInicioFin.replace(new RegExp("\\bp98_fechaCalendarioSelec\\b"),"p98_fechaCalendarioSelec"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estrucutraServicioTemporalFechaInicioFin = estrucutraServicioTemporalFechaInicioFin.replace(new RegExp("\\bp98_fechaInicioServ\\b"),"p98_fechaInicioServ"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estrucutraServicioTemporalFechaInicioFin = estrucutraServicioTemporalFechaInicioFin.replace(new RegExp("\\bp98_datepickerInicio\\b"),"p98_datepickerInicio"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estrucutraServicioTemporalFechaInicioFin = estrucutraServicioTemporalFechaInicioFin.replace(new RegExp("\\bp98_fechaFinServ\\b"),"p98_fechaFinServ"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estrucutraServicioTemporalFechaInicioFin = estrucutraServicioTemporalFechaInicioFin.replace(new RegExp("\\bp98_datepickerFin\\b"),"p98_datepickerFin"+servicio+"o"+indiceServHab+"o"+indiceServTemp);
	estrucutraServicioTemporalFechaInicioFin = estrucutraServicioTemporalFechaInicioFin.replace(new RegExp("\\bp98_checkNoLoQuiero\\b"),"p98_checkNoLoQuiero"+servicio+"o"+indiceServHab+"o"+indiceServTemp);

	return estrucutraServicioTemporalFechaInicioFin;
}

//Según L,M,X,J,V,S,D se pinta el camión o no. Esta función la comparten los servicios temporales y estacionales.
function rellenarServicioHabitualTemporalP98(comboValue,i,j,servHab,flgServHab,denominacionServicio){
	if(flgServHab == "S"){
		if(servHab.servicioHabitualLunes == "S"){
			$("#formDiaEstructuraInfoSuministroCamionCerradoLunes"+comboValue+"o"+i+"o"+j).addClass("p98_camionSuperior");
			$("#formDiaEstructuraInfoSuministroCamionAbiertoLunes"+comboValue+"o"+i+"o"+j).addClass("p98_camionInferior");
		}

		if(servHab.servicioHabitualMartes == "S"){
			$("#formDiaEstructuraInfoSuministroCamionCerradoMartes"+comboValue+"o"+i+"o"+j).addClass("p98_camionSuperior");
			$("#formDiaEstructuraInfoSuministroCamionAbiertoMartes"+comboValue+"o"+i+"o"+j).addClass("p98_camionInferior");
		}
		if(servHab.servicioHabitualMiercoles == "S"){
			$("#formDiaEstructuraInfoSuministroCamionCerradoMiercoles"+comboValue+"o"+i+"o"+j).addClass("p98_camionSuperior");
			$("#formDiaEstructuraInfoSuministroCamionAbiertoMiercoles"+comboValue+"o"+i+"o"+j).addClass("p98_camionInferior");
		}
		if(servHab.servicioHabitualJueves == "S"){
			$("#formDiaEstructuraInfoSuministroCamionCerradoJueves"+comboValue+"o"+i+"o"+j).addClass("p98_camionSuperior");
			$("#formDiaEstructuraInfoSuministroCamionAbiertoJueves"+comboValue+"o"+i+"o"+j).addClass("p98_camionInferior");
		}
		if(servHab.servicioHabitualViernes == "S"){
			$("#formDiaEstructuraInfoSuministroCamionCerradoViernes"+comboValue+"o"+i+"o"+j).addClass("p98_camionSuperior");
			$("#formDiaEstructuraInfoSuministroCamionAbiertoViernes"+comboValue+"o"+i+"o"+j).addClass("p98_camionInferior");
		}
		if(servHab.servicioHabitualSabado == "S"){
			$("#formDiaEstructuraInfoSuministroCamionCerradoSabado"+comboValue+"o"+i+"o"+j).addClass("p98_camionSuperior");
			$("#formDiaEstructuraInfoSuministroCamionAbiertoSabado"+comboValue+"o"+i+"o"+j).addClass("p98_camionInferior");
		}
		if(servHab.servicioHabitualDomingo == "S"){
			$("#formDiaEstructuraInfoSuministroCamionCerradoDomingo"+comboValue+"o"+i+"o"+j).addClass("p98_camionSuperior");
			$("#formDiaEstructuraInfoSuministroCamionAbiertoDomingo"+comboValue+"o"+i+"o"+j).addClass("p98_camionInferior");
		}
		//Añadimos el servicio habitual seleccionado del combo.
		$("#p98_lbl_servicioHabitualSelec"+comboValue+"o"+i).text("("+denominacionServicio+")");
	}else{
		if(servHab.servicioEstacionalLunes == "S"){
			$("#formDiaEstructuraInfoSuministroCamionCerradoLunes"+comboValue+"o"+i+"o"+j).addClass("p98_camionSuperior");
			$("#formDiaEstructuraInfoSuministroCamionAbiertoLunes"+comboValue+"o"+i+"o"+j).addClass("p98_camionInferior");
		}

		if(servHab.servicioEstacionalMartes == "S"){
			$("#formDiaEstructuraInfoSuministroCamionCerradoMartes"+comboValue+"o"+i+"o"+j).addClass("p98_camionSuperior");
			$("#formDiaEstructuraInfoSuministroCamionAbiertoMartes"+comboValue+"o"+i+"o"+j).addClass("p98_camionInferior");
		}
		if(servHab.servicioEstacionalMiercoles == "S"){
			$("#formDiaEstructuraInfoSuministroCamionCerradoMiercoles"+comboValue+"o"+i+"o"+j).addClass("p98_camionSuperior");
			$("#formDiaEstructuraInfoSuministroCamionAbiertoMiercoles"+comboValue+"o"+i+"o"+j).addClass("p98_camionInferior");
		}
		if(servHab.servicioEstacionalJueves == "S"){
			$("#formDiaEstructuraInfoSuministroCamionCerradoJueves"+comboValue+"o"+i+"o"+j).addClass("p98_camionSuperior");
			$("#formDiaEstructuraInfoSuministroCamionAbiertoJueves"+comboValue+"o"+i+"o"+j).addClass("p98_camionInferior");
		}
		if(servHab.servicioEstacionalViernes == "S"){
			$("#formDiaEstructuraInfoSuministroCamionCerradoViernes"+comboValue+"o"+i+"o"+j).addClass("p98_camionSuperior");
			$("#formDiaEstructuraInfoSuministroCamionAbiertoViernes"+comboValue+"o"+i+"o"+j).addClass("p98_camionInferior");
		}
		if(servHab.servicioEstacionalSabado == "S"){
			$("#formDiaEstructuraInfoSuministroCamionCerradoSabado"+comboValue+"o"+i+"o"+j).addClass("p98_camionSuperior");
			$("#formDiaEstructuraInfoSuministroCamionAbiertoSabado"+comboValue+"o"+i+"o"+j).addClass("p98_camionInferior");
		}
		if(servHab.servicioEstacionalDomingo == "S"){
			$("#formDiaEstructuraInfoSuministroCamionCerradoDomingo"+comboValue+"o"+i+"o"+j).addClass("p98_camionSuperior");
			$("#formDiaEstructuraInfoSuministroCamionAbiertoDomingo"+comboValue+"o"+i+"o"+j).addClass("p98_camionInferior");
		}

		//Añadimos el servicio habitual seleccionado del combo.
		$("#p98_lbl_servicioTemporalSelec"+comboValue+"o"+i).text("("+denominacionServicio+")");
	}
}

//Checkea y descheckea los checkboxes según el valor de noloquiero
function rellenarCheckBoxServicioTemporalP98(servicio,indiceServHab,indiceServTemp,servTemp){
	//Si noloquiero es 'S', no se puede elegir del datepicker y checkeamos el checkbox.
	if(servTemp.noLoQuieroCentro == "S"){
		$('#p98_checkNoLoQuiero'+servicio+"o"+indiceServHab+"o"+indiceServTemp).attr('checked', true);

		$("#p98_fechaInicioServ"+servicio+"o"+indiceServHab+"o"+indiceServTemp).css('visibility', 'hidden');
		$("#p98_fechaFinServ"+servicio+"o"+indiceServHab+"o"+indiceServTemp).css('visibility', 'hidden');
	}else{
		$('#p98_checkNoLoQuiero'+servicio+"o"+indiceServHab+"o"+indiceServTemp).attr('checked', false);

		$("#p98_fechaInicioServ"+servicio+"o"+indiceServHab+"o"+indiceServTemp).css('visibility', 'visible');
		$("#p98_fechaFinServ"+servicio+"o"+indiceServHab+"o"+indiceServTemp).css('visibility', 'visible');
	}	
}

//Rellena la información oculta de los servicios habituales y temporales. (la de si se pinta el camión o no).
function rellenarInformacionServicioHabitualTemporalHiddenP98(servicio,indiceServHab,indiceServTemp,servHabTemp,flgServHab){	
	if(flgServHab == "S"){
		$("#servicioLunes"+servicio+"o"+indiceServHab+"o"+indiceServTemp).val(servHabTemp.servicioHabitualLunes);
		$("#servicioMartes"+servicio+"o"+indiceServHab+"o"+indiceServTemp).val(servHabTemp.servicioHabitualMartes);
		$("#servicioMiercoles"+servicio+"o"+indiceServHab+"o"+indiceServTemp).val(servHabTemp.servicioHabitualMiercoles);
		$("#servicioJueves"+servicio+"o"+indiceServHab+"o"+indiceServTemp).val(servHabTemp.servicioHabitualJueves);
		$("#servicioViernes"+servicio+"o"+indiceServHab+"o"+indiceServTemp).val(servHabTemp.servicioHabitualViernes);
		$("#servicioSabado"+servicio+"o"+indiceServHab+"o"+indiceServTemp).val(servHabTemp.servicioHabitualSabado);
		$("#servicioDomingo"+servicio+"o"+indiceServHab+"o"+indiceServTemp).val(servHabTemp.servicioHabitualDomingo);
	}else{
		$("#servicioLunes"+servicio+"o"+indiceServHab+"o"+indiceServTemp).val(servHabTemp.servicioEstacionalLunes);
		$("#servicioMartes"+servicio+"o"+indiceServHab+"o"+indiceServTemp).val(servHabTemp.servicioEstacionalMartes);
		$("#servicioMiercoles"+servicio+"o"+indiceServHab+"o"+indiceServTemp).val(servHabTemp.servicioEstacionalMiercoles);
		$("#servicioJueves"+servicio+"o"+indiceServHab+"o"+indiceServTemp).val(servHabTemp.servicioEstacionalJueves);
		$("#servicioViernes"+servicio+"o"+indiceServHab+"o"+indiceServTemp).val(servHabTemp.servicioEstacionalViernes);
		$("#servicioSabado"+servicio+"o"+indiceServHab+"o"+indiceServTemp).val(servHabTemp.servicioEstacionalSabado);
		$("#servicioDomingo"+servicio+"o"+indiceServHab+"o"+indiceServTemp).val(servHabTemp.servicioEstacionalDomingo);
	}
}

//Transforma al datepicker con formato español los input de los servicios habituales.
function transformToDatePicker(servicio,indiceServHab,indiceServTemp,servTemp){
	$.datepicker.setDefaults($.datepicker.regional["esMisumi"]);
	var anio = comboValueAnioP96.substring(0,4);
	var tipoEjecucion = comboValueAnioP96.substring(4);

	var fechaInicio = new Date(servTemp.fechaEstacionalInicio);
	var fechaInicioMin = new Date(fechaInicio);
	fechaInicioMin.setDate(fechaInicio.getDate() - servTemp.plazoMaxAdelantarAtrasar);
	var fechaInicioMax = new Date(fechaInicio);
	fechaInicioMax.setDate(fechaInicio.getDate() + servTemp.plazoMaxAdelantarAtrasar);
	//Obtenemos el año y ponemos que va desde el 1 de febrero hasta el 31 de enero del año siguiente.
	$("#p98_datepickerInicio"+servicio+"o"+indiceServHab+"o"+indiceServTemp).datepicker({
		beforeShowDay: function(day) {
			var day = day.getDay();
			if (day != 1) {
				return [false,'']
			} else {
				return [true,'']
			}

		},
		minDate: fechaInicioMin,
		maxDate: fechaInicioMax,
		onSelect: function(date,inst) {
			changeDatepickerValue(date,this,"S",inst);
		}
	});

	var fechaFin = new Date(servTemp.fechaEstacionalFin);
	var fechaFinMin = new Date(fechaFin);
	fechaFinMin.setDate(fechaFin.getDate() - servTemp.plazoMaxAdelantarAtrasar);
	var fechaFinMax = new Date(fechaFin);
	fechaFinMax.setDate(fechaFin.getDate() + servTemp.plazoMaxAdelantarAtrasar);

	//Obtenemos el año y ponemos que va desde el 1 de febrero hasta el 31 de enero del año siguiente.
	$("#p98_datepickerFin"+servicio+"o"+indiceServHab+"o"+indiceServTemp).datepicker({
		beforeShowDay: function(day) {
			var day = day.getDay();
			if (day != 0) {
				return [false,'']
			} else {
				return [true,'']
			}

		},
		minDate: fechaFinMin,
		maxDate: fechaFinMax,
		onSelect: function(date,inst) {
			changeDatepickerValue(date,this,"N",inst);
		}
	});

	var fechaFormateada = $.datepicker.formatDate("D dd-M yy", new Date(servTemp.fechaEstacionalInicioCentro),{
		dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
		dayNames: $.datepicker.regional[ "es" ].dayNames,
		monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
		monthNames: $.datepicker.regional[ "es" ].monthNames
	});

	var fechaFormateada2 = $.datepicker.formatDate("D dd-M yy", new Date(servTemp.fechaEstacionalFinCentro),{
		dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
		dayNames: $.datepicker.regional[ "es" ].dayNames,
		monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
		monthNames: $.datepicker.regional[ "es" ].monthNames
	});

	$("#p98_datepickerInicio"+servicio+"o"+indiceServHab+"o"+indiceServTemp).datepicker("setDate",fechaFormateada );
	$("#p98_datepickerFin"+servicio+"o"+indiceServHab+"o"+indiceServTemp).datepicker("setDate", fechaFormateada2);

	if (tipoEjecucion == 'E') {
		$("#p98_datepickerInicio"+servicio+"o"+indiceServHab+"o"+indiceServTemp).attr('readonly',true).datepicker("destroy");
		$("#p98_datepickerFin"+servicio+"o"+indiceServHab+"o"+indiceServTemp).attr('readonly',true).datepicker("destroy");
	} else {
		$("#p98_datepickerInicio"+servicio+"o"+indiceServHab+"o"+indiceServTemp).attr('readonly',false).datepicker();
		$("#p98_datepickerFin"+servicio+"o"+indiceServHab+"o"+indiceServTemp).attr('readonly',false).datepicker();
	}

}

//Vacía la información del popup.
function reset_p98(){
	$("#p98_calendarios").empty();
}

//Cambia el valor del datepicker y actualiza la información a guardar de los cambios estacionales.
function changeDatepickerValue(dia,elementoDom,fechaInicio,inst){
	var id = elementoDom.id;
	var posServHabTemp;

	//Obtenemos el servicio, posición del array del servicio habilitado y dentro del servicio habilitado la posicion del array del servicio temporal tocado.
	if(fechaInicio == "S"){
		posServHabTemp = id.replace("p98_datepickerInicio","");
	}else{
		posServHabTemp = id.replace("p98_datepickerFin","");
	}
	var posServHabTempParts = posServHabTemp.split("o");

	//Obtenemos las ids
	var idServicio = posServHabTempParts[0];
	var posServHab = posServHabTempParts[1];
	var posServTemp = posServHabTempParts[2];

	var anio = parseInt(inst.currentYear);
	var mes = parseInt(inst.currentMonth);
	var dia = parseInt(parseInt(inst.currentDay));

	var fecha = new Date(anio,mes,dia).getTime();

	if(validarFecha(fechaInicio,fecha,id,inst.lastVal,posServHabTemp)){
		if(fechaInicio == "S"){
			listadoValidacionesTratadas[idServicio].calServHabLst[posServHab].calendarioServicioLst[posServTemp].fechaEstacionalInicioCentro = fecha;
			//Abrimos el datepicker de la fecha fin.
			setTimeout(function(){
				$("#p98_datepickerFin"+posServHabTemp).datepicker('show');
			}, 16); 
		}else{	
			listadoValidacionesTratadas[idServicio].calServHabLst[posServHab].calendarioServicioLst[posServTemp].fechaEstacionalFinCentro = fecha;
			//Hacemos focus fuera del input de fecha para que en IE no se ejecute el evento de abrir calendario y que al seleccionar una fecha no se abra de nuevo.
			$("#p98_btn_guardado").focus();
		}
	}
}

function validarFecha(flgFechaInicio,fechaInsertada,idElemento,fechaUltimoValor,posServHabTemp){
	//Ponemos la flgFechaValdada en true.
	var flgFechaValdada = true;

	//Si estamos hablando de la fecha de inicio, hay que evitar que sea mayor que la fecha fin.
	//Si estamos hablando de la fecha fin, hay que evitar que sea menor que la fecha inicio.
	if(flgFechaInicio == "S"){
		//Obtenemos la fecha fin.
		var fechaFinP98Str = $("#p98_datepickerFin"+posServHabTemp).val();
		var fechaFinP98 = $.datepicker.parseDate("D dd-M yy",fechaFinP98Str);
		var fechaFinP98Mili = fechaFinP98.getTime();

		//Comparamos la fecha fin con la insertada.Si la insertada es mayor, devolvemos error ya que la fecha inicio no puede
		//ser mayor que la fecha fin.
		if(fechaInsertada > fechaFinP98Mili){
			//Mostramos error.
			createAlert(fechaInicioNoMayorFechaFinP98,"ERROR");
			$("#"+idElemento).val(fechaUltimoValor);
			flgFechaValdada =  false;
		}
	}else{
		//Obtenemos la fecha inicio.
		var fechaInicioP98Str = $("#p98_datepickerInicio"+posServHabTemp).val();
		var fechaInicioP98 = $.datepicker.parseDate("D dd-M yy",fechaInicioP98Str);
		var fechaInicioP98Mili = fechaInicioP98.getTime();

		//Comparamos la fecha inicio con la insertada.Si la insertada es menor, devolvemos error ya que la fecha fin no puede
		//ser menor que la fecha inicio.
		if(fechaInsertada < fechaInicioP98Mili){
			createAlert(fechaFinNoMenorFechaInicioP98,"ERROR");
			$("#"+idElemento).val(fechaUltimoValor);
			flgFechaValdada =  false;
		}
	}
	return flgFechaValdada;
}
//Evento de guardado para los cambios estacionales.
function events_p98_btnGuardar(){
	$("#p98_btn_guardado").click(function(){
		//Si se ha cambiado alguna fecha de los servicios temporales.
		//if(existeCambioDeFechaServicioTemporal()){
		var calendarioErroneo = null;
		$.each(listadoValidacionesTratadas, function(i, servicio){
			$.each(servicio.calServHabLst, function(i, servicioHabitual){
				$.each(servicioHabitual.calendarioServicioLst, function(i, calendario){
					var fechaEstacionalInicioCentro = new Date(calendario.fechaEstacionalInicioCentro);
					var fechaEstacionalFinCentro = new Date(calendario.fechaEstacionalFinCentro);

					var diferencia = (fechaEstacionalFinCentro - fechaEstacionalInicioCentro) / (1000 * 60 * 60 * 24);

					if(diferencia > calendario.plazoMaxDuracionTotal){
						calendarioErroneo =  calendario;
						return false;
					}

				});
			});
		});
		if(calendarioErroneo == null){
			var codigoEjercicioTipo = comboValueAnioP96;
			var codigoEjercicio = $("#anioEjercicio"+codigoEjercicioTipo).val();
			//Calendario cambio estacional
			var calendarioCambioEstacional = new CalendarioCambioEstacional(listadoValidacionesTratadas);
			var objJson = $.toJSON(calendarioCambioEstacional);

			$.ajax({
				type : 'POST',
				url : './calendario/popup/serviciosTemporales/guardarCambioEstacional.do?codigoEjercicio='+codigoEjercicio,
				data : objJson,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				cache : false,
				success : function(data) {
					if(data.codError == 0){					

						//Actualizamos los cambios manuales del calendario
						actualizarCambiosManualesCalendario();

						//Ponemos el flag en S
						//cambiosEstacionalesP98 = "S";
					}else if(data.codError == 1){
						createAlert(data.descError,"INFO");
					}else{
						createAlert(data.descError,"ERROR");
					}
				},
				error : function (xhr, status, error){
					handleError(xhr, status, error, locale);
				}	
			});	
		}else{
			createAlert(calendarioDiasMayorMax + calendarioErroneo.plazoMaxDuracionTotal, "ERROR");
		}
		/*}else{
			createAlert(noHayCambiosEnServiciosTemporalesP98,"ERROR");
		}*/
	});
}

//Función que indica si quieres o no los cambios estacionales.
function events_p98_checkBox(){
	$(".p98_checkBoxClicable").click(function(){
		var id = this.id;
		var posServHabTemp = id.replace("p98_checkNoLoQuiero","");

		var posServHabTempParts = posServHabTemp.split("o");

		//Obtenemos las ids
		var idServicio = posServHabTempParts[0];
		var posServHab = posServHabTempParts[1];
		var posServTemp = posServHabTempParts[2];

		//Actualizamos el campo no lo quiero.
		listadoValidacionesTratadas[idServicio].calServHabLst[posServHab].calendarioServicioLst[posServTemp].noLoQuieroCentro = this.checked ? "S":"N";

		//Si seleccionamos el checkbox queremos bloquear las fechas y poner en nulo los valores de fecha inicio y fin.
		if(this.checked){
			$("#p98_fechaInicioServ"+posServHabTemp).css('visibility', 'hidden');
			$("#p98_fechaFinServ"+posServHabTemp).css('visibility', 'hidden');

			//Ponemos a nulos las fecha fin centro y las fechas inicio centro.
			listadoValidacionesTratadas[idServicio].calServHabLst[posServHab].calendarioServicioLst[posServTemp].fechaEstacionalInicioCentro = null;
			listadoValidacionesTratadas[idServicio].calServHabLst[posServHab].calendarioServicioLst[posServTemp].fechaEstacionalFinCentro = null;
		}else{
			$("#p98_fechaInicioServ"+posServHabTemp).css('visibility', 'visible');
			$("#p98_fechaFinServ"+posServHabTemp).css('visibility', 'visible');

			//Obtenemos la fecha fin.
			var fechaFinP98Str = $("#p98_datepickerFin"+posServHabTemp).val();
			var fechaFinP98 = $.datepicker.parseDate("D dd-M yy",fechaFinP98Str);
			var fechaFinP98Mili = fechaFinP98.getTime();

			//Obtenemos la fecha inicio.
			var fechaInicioP98Str = $("#p98_datepickerInicio"+posServHabTemp).val();
			var fechaInicioP98 = $.datepicker.parseDate("D dd-M yy",fechaInicioP98Str);
			var fechaInicioP98Mili = fechaInicioP98.getTime();

			//Ponemos la fecha inicio y fecha fin.
			listadoValidacionesTratadas[idServicio].calServHabLst[posServHab].calendarioServicioLst[posServTemp].fechaEstacionalInicioCentro = fechaInicioP98Mili;
			listadoValidacionesTratadas[idServicio].calServHabLst[posServHab].calendarioServicioLst[posServTemp].fechaEstacionalFinCentro = fechaFinP98Mili;
		}
	});
}

//Función que tras el guardado actualiza las fechas de inicio y fin con el valor de fechainiciocentro y fechafincentro
function actualizarFechaInicioFechaFinServiciosTemporales(){
	for (var key in listadoValidacionesTratadas) {
		var valorCombo = listadoValidacionesTratadas[key];
		for(var i = 0; i< valorCombo.calServHabLst.length; i++){
			//Se obtiene el servicio habitual
			var servHab = valorCombo.calServHabLst[i];		
			//Por cada servicio habitual obtenemos los servicios temporales.
			for(var j = 0;j<servHab.calendarioServicioLst.length;j++){
				//Obtenemos el servicio temporal.
				var servTemp = servHab.calendarioServicioLst[j];
				servTemp.fechaEstacionalInicio = servTemp.fechaEstacionalInicioCentro;
				servTemp.fechaEstacionalFin= servTemp.fechaEstacionalFinCentro;
				servTemp.noLoQuiero= servTemp.noLoQuieroCentro;
			}
		}
	}	
}

//Función que se ejecuta una vez se han guardado los cambios estacionales. En vez de recargar el calendario tirando de procedimiento,
//borrando la tabla temporal y insertandola de nuevo, obtiene los datos del procedimiento y actualiza las filas existentes con los datos
//nuevos que llegan desde el procedimiento.
function actualizarCambiosManualesCalendario(){
	//Metemos los flag de existe en los códigos de servicios cambiados.
	insertaFlagSiexisteCambioDeFechaServicioTemporal();

	var codigoEjercicioTipo = comboValueAnioP96;
	var codigoEjercicio = $("#anioEjercicio"+codigoEjercicioTipo).val();

	//Calendario cambio estacional
	var calendarioCambioEstacional = new CalendarioCambioEstacional(listadoValidacionesTratadas);
	var objJson = $.toJSON(calendarioCambioEstacional);

	//Obtenemos el tipo de ejercicio.
	var tipoEjercicio = $("#tipoEjercicio"+comboValueAnioP96).val();
	$.ajax({
		type : 'POST',
		url : './calendario/popup/serviciosTemporales/actualizarCambiosManualesCalendario.do?codigoEjercicio='+codigoEjercicio+"&codigoServicio="+comboValueServicioP96+"&mesAnio="+mesAnioLst[idxActual]+'&tipoEjercicio='+tipoEjercicio,
		contentType : "application/json; charset=utf-8",
		data : objJson,
		dataType : "json",
		cache : false,
		success : function(data) {
			if(data.pCodError == 0){
				//Se ha devuelto la tabla temporal con los cambios estacionales actualizados, repintamos.
				if(data.listadoFechaTemporal != null){
					//Limpiamos el calendario.
					limpiarCalendarioP96();

					//Repintamos el calendario.
					dibujarDiasCalendarioP96(data.listadoFechaTemporal);

					//Actualizamos la fecha inicio con fecha inicio centro y la fecha fin con fecha fin centro. Si no, al volver a pulsar el guardar,
					//volvería a intentar guardar pues detecta que son distintas.
					actualizarFechaInicioFechaFinServiciosTemporales();

					createAlert(serviciosTemporalesValidadosExitoP98,"INFO");
				}
			}else{
				createAlert(data.pDescError,"ERROR");
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});	
}
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/***********************************************************VALIDACIONES DE P98 **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Función que mira si ha habido cambios en las fechas de los servicios habituales.
function existeCambioDeFechaServicioTemporal(){
	var existeCambio = false;
	for (var key in listadoValidacionesTratadas) {
		var valorCombo = listadoValidacionesTratadas[key];
		for(var i = 0; i< valorCombo.calServHabLst.length; i++){
			//Se obtiene el servicio habitual
			var servHab = valorCombo.calServHabLst[i];		
			//Por cada servicio habitual obtenemos los servicios temporales.
			for(var j = 0;j<servHab.calendarioServicioLst.length;j++){
				//Obtenemos el servicio temporal.
				var servTemp = servHab.calendarioServicioLst[j];
				if(servTemp.fechaEstacionalInicioCentro != servTemp.fechaEstacionalInicio || servTemp.fechaEstacionalFinCentro != servTemp.fechaEstacionalFin || servTemp.noLoQuieroCentro != servTemp.noLoQuiero){
					//if(servTemp.fechaEstacionalInicioCentro != servTemp.fechaEstacionalInicio || servTemp.fechaEstacionalFinCentro != servTemp.fechaEstacionalFin){
					existeCambio = true;
					return existeCambio;
				}
			}
		}	
	}
	return existeCambio;
}

//Función que inserta un flag si ha habido cambios en las fechas de los servicios habituales.
function insertaFlagSiexisteCambioDeFechaServicioTemporal(){
	var existeCambio;
	for (var key in listadoValidacionesTratadas) {
		//Inicializamos existe cambio a false.
		existeCambio = false;
		var valorCombo = listadoValidacionesTratadas[key];
		for(var i = 0; i< valorCombo.calServHabLst.length; i++){
			//Se obtiene el servicio habitual
			var servHab = valorCombo.calServHabLst[i];		
			//Por cada servicio habitual obtenemos los servicios temporales.
			for(var j = 0;j<servHab.calendarioServicioLst.length;j++){
				//Obtenemos el servicio temporal.
				var servTemp = servHab.calendarioServicioLst[j];
				if(servTemp.fechaEstacionalInicioCentro != servTemp.fechaEstacionalInicio || servTemp.fechaEstacionalFinCentro != servTemp.fechaEstacionalFin || servTemp.noLoQuieroCentro != servTemp.noLoQuiero){
					//if(servTemp.fechaEstacionalInicioCentro != servTemp.fechaEstacionalInicio || servTemp.fechaEstacionalFinCentro != servTemp.fechaEstacionalFin){
					existeCambio = true;
					break;
				}
			}
			//Si hay cambio, ya sabemos que ese servicio tiene cambio, así que no hace alta mirar más habituales.
			if(existeCambio){
				break;
			}
		}
		valorCombo.existeCambio = existeCambio;
	}
}
