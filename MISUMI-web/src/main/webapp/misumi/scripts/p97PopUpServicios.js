/* NADA MÁS CARGAR LA PANTALLA, SE CREAN DOS HIDDENS POR CADA CHECKBOX DE LOS SERVICIOS 
 * 1 - Guarda los valores originales de los checkboxes nada más cargar los servicios. Se 
 *     utilizará para saber si se ha realizado algún cambio en la temporal al cerrar el popup
 *     y así saber si recargar el calendario. Para ello se compara con los hiddens de los checkboxes
 *     temporales, que guardan los últimos estados guardados en la temporal.
 *     
 * 2 - Guarda los últimos valores guardados en la temporal. Sirve para compararlos con los valores actuales
 *     de los checkboxes y así si se pulsa el tick, guardar si ha habido cambios o en caso contrario mostrar
 *     mensaje avisando de que no.
 *     
 * 3 - Los valores de los checkboxes actuales (los que se ven) se obtienen directamente del valor, y no tienen hiddens.*/


/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/**************************************************************** VARIABLES ******************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
//Guarda el title del popup
var popUpTitle;

var serviciosGuardadosCorrectamente97 = "N";

//Mensaje error de no hay mensajes.
var noHayCambiosP97;
var cambiosGuardadosP97;
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************** DOCUMENTO LISTO **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
$(document).ready(function(){
	//Inicializa el popup que contiene el grid con las líneas de devolución
	initializeScreenP97Servicios();
});


/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************ INICIALIZACIÓN DE ELEMENTOS E IDIOMA *******************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
function initializeScreenP97Servicios(){
	//Inicializamos el idioma.
	loadP97(locale);

	//Cargar dialogo p97
	load_dialog_P97();	

	//Asignamos eventos a los botones.
	events_p97_tick();
}

//Función que define y carga el diálogo. 
function load_dialog_P97(){
	$("#p97_popUpServicios").dialog({
		autoOpen: false,
		height:'auto',
		width:'auto',
		modal: true,
		resizable: false,
		open: function() {
		},
		close: function( event, ui ) {
			var serviciosPuestosEnNulo = $("#flgCambioManualServicioNulo"+diaAIluminarCalendario).val();
			var festivo =  $("#festivo"+diaAIluminarCalendario).val() != "" ? $("#festivo"+diaAIluminarCalendario).val():null;
			//El calendario se está actualizando al cambiar de servicios. Puede que un día que se ve rojo tenga que dejar de ser rojo
			//y otros cambios más. Como son muchas situaciones y es dificil controlarlo por software, lo más facil es repintar todo con los datos
			//actualizados. Si se ha consultado un día que NO es verde, no se repinta porque no habrá cambios.

			//Si es SQ, significa que se ha quitado el camión pero si además es festivo, significa que se han puesto los cambios manuales de todos los
			//servicios a N, además del cambioManual a N del día en TODOS. Si todos los cambios manuales están a N, y el usuario no realiza cambios, queremos que 
			//si se cierra el popup, se repinte el calendario para que quite el camión en ese día. Si no se hiciese este control, detectaría que los checknoxes no
			//tienen cambios tras abrir el popup, no repintaría y saldría un camión que no interesa.
			if(($("#ponerDiaVerde"+diaAIluminarCalendario).val() == "S" || $("#verdePlataforma"+diaAIluminarCalendario).val() == "S") && (existenCambiosOrigP97() || (serviciosPuestosEnNulo == "SQ" && festivo != null))){
				load_diasCalendarioP96(comboValueAnioP96,comboValueServicioP96,null,"S",mesAnioLst[idxActual]);
			}
		}
	});
}

//Carga los elementos de idioma del json correspondiente.
function loadP97(locale){
	this.i18nJSON = './misumi/resources/p97PopUpServicios/p97PopUpServicios_' + locale + '.json';

	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {
		popUpTitle = data.popUpTitle;
		noHayCambiosP97 = data.noHayCambios;
		cambiosGuardadosP97 = data.cambiosGuardados;
	})
	.success(function(data) {

	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

function reset_p97(){

}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/*************************************************************FUNCIONES DE P97 ***************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Consultamos los procesos diarios de un día.
function consultarProcesosDiarios(numeroDia,diaVerde){

	//Obtenemos los datos del día.
	var fechaCalendario = $("#fechaCalendario"+numeroDia).val();

	//Consultamos si ha habido cambios en el cambio manual que hagan que no tengamos que hacer caso a lo que viene del procedimiento.
	var flgCambioManualServicioNulo = $("#flgCambioManualServicioNulo"+numeroDia).val();

	//Miramos si es una recarga (llama a la temporal) o no.
	var recarga = $("#flgServiciosBuscados"+numeroDia).val();

	//Miramos el tipo y código de ejercicio.
	var codigoEjercicio = comboValueAnioP96;
	var tipoEjercicio = $("#tipoEjercicio"+codigoEjercicio).val();
	var anioEjercicio = $("#anioEjercicio"+comboValueAnioP96).val();

	//Pasamos el festivo al controlador. Se da la circunstancia de que si ponemos un camión en un día verde, 
	//y además es festivo, queremos poner el cambioManual en N para que salgan deshabilitados al abrir el popup.
	var festivo = $("#festivo"+numeroDia).val() == "" ? null:$("#festivo"+numeroDia).val();

	//Obtenemos los datos del día modificado.
	var calendarioDia = new CalendarioDia(fechaCalendario, festivo);
	var objJson = $.toJSON(calendarioDia);

	var checkBoxHabitual = "";
	var checkBoxTemporal = "";
	var checkBoxEstacional = "";
	var checkBoxPlataforma = "";
	$.ajax({
		type : 'POST',
		url : './calendario/popup/servicios/consultarProcesosDiarios.do?recarga='+recarga+'&codigoEjercicio='+anioEjercicio+'&tipoEjercicio='+tipoEjercicio+'&flgCambioManualServicioNulo='+flgCambioManualServicioNulo,
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {
			if(data != null){
				if(data.pCodError == 0){
					if(data.listadoServiciosCentroTemporal != null){
						//Obtenemos el día verde y el cambio estacional del día pulsado para ver sus bloques de servicios. Hay 3 bloques,
						//pero no siempre salen todos. 
						var diaVerde = ($("#ponerDiaVerde"+numeroDia).val() == "S" || $("#verdePlataforma"+numeroDia).val() == "S") ? "S" : "N";
						var cambioEstacional = $("#cambioEstacional"+numeroDia).val();					
						var esePuedeModificarServicio = $("#esePuedeModificarServicio"+numeroDia).val();

						//Según el valor del día verde y del cambio estacional mostramos unos bloques u otros.
						if(cambioEstacional != null && cambioEstacional != ""){
							if(diaVerde == "S"){
								$("#p97_flecha").show();
								$("#p97_flecha2").show();
								$("#p97_habitual").show();
								$("#p97_temporal").show();
								$("#p97_estacional").show();

								//Mostramos el botón de guardado porque solo va a haber consulta
								$("#p97_save").show();
							}else{
								var cambioManual = $("#cambioManual"+numeroDia).val();
								if(cambioManual != null && cambioManual != ""){
									$("#p97_flecha").show();
									$("#p97_flecha2").hide();
									$("#p97_habitual").show();
									if(cambioManual == "S"){										
										$("#p97_flecha2").show();
										$("#p97_temporal").show();
									}else{
										$("#p97_flecha2").hide();
										$("#p97_temporal").hide();
									}
									$("#p97_estacional").show();														
								}else{
									$("#p97_flecha").show();
									$("#p97_flecha2").hide();
									$("#p97_habitual").show();
									$("#p97_temporal").hide();
									$("#p97_estacional").show();	
								}
								//Ocultamos el botón de guardado porque solo va a haber consulta
								$("#p97_save").hide();	
							}
						}else{
							if(diaVerde == "S"){
								$("#p97_flecha").hide();
								$("#p97_flecha2").show();
								$("#p97_habitual").show();
								$("#p97_temporal").show();
								$("#p97_estacional").hide();

								//Mostramos el botón de guardado porque solo va a haber consulta
								$("#p97_save").show();
							}else{
								var cambioManual = $("#cambioManual"+numeroDia).val();
								if(cambioManual != null && cambioManual != ""){
									$("#p97_flecha").hide();
									$("#p97_flecha2").hide();
									$("#p97_habitual").show();

									if(cambioManual == "S"){
										$("#p97_flecha2").show();
										$("#p97_temporal").show();
									}else{
										$("#p97_flecha2").hide();
										$("#p97_temporal").hide();
									}
									$("#p97_estacional").hide();
								}else{
									$("#p97_flecha").hide();
									$("#p97_flecha2").hide();
									$("#p97_habitual").show();
									$("#p97_temporal").hide();
									$("#p97_estacional").hide();
								}

								//Ocultamos el botón de guardado porque solo va a haber consulta
								$("#p97_save").hide();
							}
						}


						//Toda la gestion de los bloques anteriores esta en el codigo previo. Ahora solo se va a tratar si aparece el bloque de Servicio Plataforma o no.
						var cambioPlataforma = $("#ecambioPlataforma"+numeroDia).val();					

						//Según el valor del día verde y del cambio estacional mostramos unos bloques u otros.
						if(cambioPlataforma != null && cambioPlataforma != ""){
							//Mostramos el bloque Plataforma y la fecha correspondiente.
							$("#p97_flecha3").show();
							$("#p97_plataforma").show();
						} else {
							$("#p97_flecha3").hide();
							$("#p97_plataforma").hide();
						}



						//Asignamos a un hidden el día del servicio con el que estamos trabajando y el numeroDia con el que trabajamos para acceder a sus hiddens.
						//Este valor nos sirve por si hay updates en los servicios, para saber qué dia
						//de la tabla temporal hay que modificar.
						$("#diaServiciosSeleccionado").val(fechaCalendario +"*"+ numeroDia);

						//Obtenemos el año, ms y día para crear la fecha y conseguir formatearla a fecha misumi.
						var fechaCalendarioSplit = fechaCalendario.split("-");

						var fechaFormatP98 = $.datepicker.formatDate("D dd-M", new Date(parseInt(fechaCalendarioSplit[0]),parseInt(fechaCalendarioSplit[1])-1,parseInt(fechaCalendarioSplit[2])),{
							dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
							dayNames: $.datepicker.regional[ "es" ].dayNames,
							monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
							monthNames: $.datepicker.regional[ "es" ].monthNames
						});

						//Asignamos al diálogo el título con el día pinchado.
						$("#p97_popUpServicios").dialog({ title:popUpTitle + fechaFormatP98});

						//Indicamos que los servicios se han buscado. Así las próximas veces, no llamará al PLSQL y tirará de la temporal 
						//al consultar los días.
						$("#flgServiciosBuscados"+numeroDia).val("S");

						//Pintamos los checkboxes. Los checkboxes de servicio habitual y cambio estacional pintan los valores obtenidos.
						//Los checkboxes de servicio temporal, pintan sus valores a no ser que sean nulos. Si son nulos pintan los estacionales
						//y si el estacional es nulo pinta el habitual.
						for (i = 0; i < data.listadoServiciosCentroTemporal.length; i++){
							var tCalendarioDiaCambioServicio = data.listadoServiciosCentroTemporal[i];

							//Según el valor del día verde y del cambio estacional mostramos unos bloques u otros.
							if(cambioEstacional != null && cambioEstacional != ""){
								if(($("#ponerDiaVerde"+numeroDia).val() == "S") || ($("#verdePlataforma"+numeroDia).val() == "S" && tCalendarioDiaCambioServicio.puedeSolicitarServicio == "S")){
									if(tCalendarioDiaCambioServicio.servicioHabitual == "S"){
										checkBoxHabitual = checkBoxHabitual + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
									}else{
										checkBoxHabitual = checkBoxHabitual + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
									}
									if(tCalendarioDiaCambioServicio.cambioEstacional == "S"){
										checkBoxEstacional = checkBoxEstacional + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
									}else if(tCalendarioDiaCambioServicio.cambioEstacional == "N"){
										checkBoxEstacional = checkBoxEstacional + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
									}else{
										if(tCalendarioDiaCambioServicio.servicioHabitual == "S"){
											checkBoxEstacional = checkBoxEstacional + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}else{
											checkBoxEstacional = checkBoxEstacional + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}
									}
									//Si es tipo P, permitimos que los checks puedan seleccionarse
									if(tCalendarioDiaCambioServicio.tipoEjercicio == "P"){
										if(tCalendarioDiaCambioServicio.cambioManual == "S"){
											checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable' checked>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}else if(tCalendarioDiaCambioServicio.cambioManual == "N"){
											checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable'>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}else{
											if(tCalendarioDiaCambioServicio.cambioEstacional == "S"){
												checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable' checked>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
											}else if(tCalendarioDiaCambioServicio.cambioEstacional == "N"){
												checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable'>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
											}else{
												if(tCalendarioDiaCambioServicio.servicioHabitual == "S"){
													checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable' checked>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
												}else{
													checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable'>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
												}
											}
										}
									}else if(tCalendarioDiaCambioServicio.tipoEjercicio == "E"){
										if(esePuedeModificarServicio == "S" || (esePuedeModificarServicio == "N" && rol == "4")){
											if(tCalendarioDiaCambioServicio.cambioManual == "S"){
												checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable' checked>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
											}else if(tCalendarioDiaCambioServicio.cambioManual == "N"){
												checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable'>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
											}else{
												if(tCalendarioDiaCambioServicio.cambioEstacional == "S"){
													checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable' checked>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
												}else if(tCalendarioDiaCambioServicio.cambioEstacional == "N"){
													checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable'>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
												}else{
													if(tCalendarioDiaCambioServicio.servicioHabitual == "S"){
														checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable' checked>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
													}else{
														checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable'>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
													}
												}
											}
										}else{
											if(tCalendarioDiaCambioServicio.cambioManual == "S"){
												checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable' checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
											}else if(tCalendarioDiaCambioServicio.cambioManual == "N"){
												checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable' disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
											}else{
												if(tCalendarioDiaCambioServicio.cambioEstacional == "S"){
													checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable' checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
												}else if(tCalendarioDiaCambioServicio.cambioEstacional == "N"){
													checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable' disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
												}else{
													if(tCalendarioDiaCambioServicio.servicioHabitual == "S"){
														checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable' checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
													}else{
														checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable' disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
													}
												}
											}
										}
									}
								}else{
									var cambioManual = $("#cambioManual"+numeroDia).val();
									if(cambioManual != null && cambioManual != ""){
										if(tCalendarioDiaCambioServicio.servicioHabitual == "S"){
											checkBoxHabitual = checkBoxHabitual + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}else{
											checkBoxHabitual = checkBoxHabitual + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}
										if(tCalendarioDiaCambioServicio.cambioEstacional == "S"){
											checkBoxEstacional = checkBoxEstacional + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}else if(tCalendarioDiaCambioServicio.cambioEstacional == "N"){
											checkBoxEstacional = checkBoxEstacional + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}else{
											if(tCalendarioDiaCambioServicio.servicioHabitual == "S"){
												checkBoxEstacional = checkBoxEstacional + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
											}else{
												checkBoxEstacional = checkBoxEstacional + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
											}
										}
										if(tCalendarioDiaCambioServicio.cambioManual == "S"){
											checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}else if(tCalendarioDiaCambioServicio.cambioManual == "N"){
											checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}else{
											if(tCalendarioDiaCambioServicio.cambioEstacional == "S"){
												checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
											}else if(tCalendarioDiaCambioServicio.cambioEstacional == "N"){
												checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
											}else{
												if(tCalendarioDiaCambioServicio.servicioHabitual == "S"){
													checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
												}else{
													checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
												}
											}
										}												
									}else{
										if(tCalendarioDiaCambioServicio.servicioHabitual == "S"){
											checkBoxHabitual = checkBoxHabitual + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}else{
											checkBoxHabitual = checkBoxHabitual + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}
										if(tCalendarioDiaCambioServicio.cambioEstacional == "S"){
											checkBoxEstacional = checkBoxEstacional + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}else if(tCalendarioDiaCambioServicio.cambioEstacional == "N"){
											checkBoxEstacional = checkBoxEstacional + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}else{
											if(tCalendarioDiaCambioServicio.servicioHabitual == "S"){
												checkBoxEstacional = checkBoxEstacional + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
											}else{
												checkBoxEstacional = checkBoxEstacional + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
											}
										}
									}								
								}
							}else{
								if(($("#ponerDiaVerde"+numeroDia).val() == "S") || ($("#verdePlataforma"+numeroDia).val() == "S" && tCalendarioDiaCambioServicio.puedeSolicitarServicio == "S")){
									if(tCalendarioDiaCambioServicio.servicioHabitual == "S"){
										checkBoxHabitual = checkBoxHabitual + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
									}else{
										checkBoxHabitual = checkBoxHabitual + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
									}
									
									//Si es tipo P, permitimos que los checks puedan seleccionarse
									if(tCalendarioDiaCambioServicio.tipoEjercicio == "P"){
										if(tCalendarioDiaCambioServicio.cambioManual == "S"){
											checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable' checked>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}else if(tCalendarioDiaCambioServicio.cambioManual == "N"){
											checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable'>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}else{
											if(tCalendarioDiaCambioServicio.cambioEstacional == "S"){
												checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable' checked>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
											}else if(tCalendarioDiaCambioServicio.cambioEstacional == "N"){
												checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable'>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
											}else{
												if(tCalendarioDiaCambioServicio.servicioHabitual == "S"){
													checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable' checked>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
												}else{
													checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable'>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
												}
											}
										}	
									}else if(tCalendarioDiaCambioServicio.tipoEjercicio == "E"){
										if(esePuedeModificarServicio == "S" || (esePuedeModificarServicio == "N" && rol == "4")){
											if(tCalendarioDiaCambioServicio.cambioManual == "S"){
												checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable' checked>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
											}else if(tCalendarioDiaCambioServicio.cambioManual == "N"){
												checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable'>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
											}else{
												if(tCalendarioDiaCambioServicio.cambioEstacional == "S"){
													checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable' checked>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
												}else if(tCalendarioDiaCambioServicio.cambioEstacional == "N"){
													checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable'>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
												}else{
													if(tCalendarioDiaCambioServicio.servicioHabitual == "S"){
														checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable' checked>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
													}else{
														checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable'>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
													}
												}
											}											
										}else{
											if(tCalendarioDiaCambioServicio.cambioManual == "S"){
												checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable' checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
											}else if(tCalendarioDiaCambioServicio.cambioManual == "N"){
												checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable' disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
											}else{
												if(tCalendarioDiaCambioServicio.cambioEstacional == "S"){
													checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable' checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
												}else if(tCalendarioDiaCambioServicio.cambioEstacional == "N"){
													checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable' disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
												}else{
													if(tCalendarioDiaCambioServicio.servicioHabitual == "S"){
														checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable' checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
													}else{
														checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl p96_pointer'><input type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox p97_checkBoxClicable' disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
													}
												}
											}											
										}
									}									
								}else{
									var cambioManual = $("#cambioManual"+numeroDia).val();
									if((cambioManual != null && cambioManual != "") || ($("#verdePlataforma"+numeroDia).val() == "S")){
										if(tCalendarioDiaCambioServicio.servicioHabitual == "S"){
											checkBoxHabitual = checkBoxHabitual + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}else{
											checkBoxHabitual = checkBoxHabitual + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}
										if(tCalendarioDiaCambioServicio.cambioManual == "S"){
											checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}else if(tCalendarioDiaCambioServicio.cambioManual == "N"){
											checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}else{
											if(tCalendarioDiaCambioServicio.cambioEstacional == "S"){
												checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
											}else if(tCalendarioDiaCambioServicio.cambioEstacional == "N"){
												checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
											}else{
												if(tCalendarioDiaCambioServicio.servicioHabitual == "S"){
													checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
												}else{
													checkBoxTemporal = checkBoxTemporal + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
												}
											}
										}												
									}else{
										if(tCalendarioDiaCambioServicio.servicioHabitual == "S"){
											checkBoxHabitual = checkBoxHabitual + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}else{
											checkBoxHabitual = checkBoxHabitual + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.codigoServicio+"' class='p97_checkBox' disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}
									}
								}
							}		

							//completamos los servicios habituales con las observaciones. Si E_OBSERVA-CONFIRMA_PLATAFORMA tiene valor se lo añadimos junto con un aspa roja al servicio correspondiente

							if(checkBoxTemporal != null && checkBoxTemporal != ""){
								
								if($("#p97_temporal").css("display") == 'block'){
									if (tCalendarioDiaCambioServicio.eObservaConfirmaPlataforma != null && tCalendarioDiaCambioServicio.eObservaConfirmaPlataforma !="") {
										var p97AspaImg = 'dialog-accept-24.png';
										if(tCalendarioDiaCambioServicio.eObservaConfirmaPlataforma.indexOf("FESTIVO PLATAFORMA")>=0){
											p97AspaImg = 'dialog-cancel-blue-24.png';
										} else if (tCalendarioDiaCambioServicio.eCambioPlataforma != null && tCalendarioDiaCambioServicio.eCambioPlataforma != ""){
											p97AspaImg = 'dialog-cancel-24.png';
										} 
										checkBoxTemporal = checkBoxTemporal +" <div class='p97_observacionesPlataforma'><img class='p97_img_aspa'  src='./misumi/images/" + p97AspaImg + "?version=${misumiVersion}'> <span class='p97_observaciones_aspa'>" +tCalendarioDiaCambioServicio.eObservaConfirmaPlataforma + " </span></div> ";									
									}
								}else{
									if (tCalendarioDiaCambioServicio.eObservaConfirmaPlataforma != null && tCalendarioDiaCambioServicio.eObservaConfirmaPlataforma !="") {
										var p97AspaImg = 'dialog-accept-24.png';
										if(tCalendarioDiaCambioServicio.eObservaConfirmaPlataforma.indexOf("FESTIVO PLATAFORMA")>=0){
											p97AspaImg = 'dialog-cancel-blue-24.png';
										} else if (tCalendarioDiaCambioServicio.eCambioPlataforma != null && tCalendarioDiaCambioServicio.eCambioPlataforma != ""){
											p97AspaImg = 'dialog-cancel-24.png';
										} 
										
										checkBoxHabitual = checkBoxHabitual +" <div class='p97_observacionesPlataforma'><img class='p97_img_aspa'  src='./misumi/images/" + p97AspaImg + "?version=${misumiVersion}'> <span class='p97_observaciones_aspa'>" +tCalendarioDiaCambioServicio.eObservaConfirmaPlataforma + " </span></div> ";
									}
								}
							} else { //si no existen servicios temporales, y existen observaciones de plataforma, estos ultimos se pintan en los servicios habituales
								if (tCalendarioDiaCambioServicio.eObservaConfirmaPlataforma != null && tCalendarioDiaCambioServicio.eObservaConfirmaPlataforma !="") {
									var p97AspaImg = 'dialog-accept-24.png';
									if(tCalendarioDiaCambioServicio.eObservaConfirmaPlataforma.indexOf("FESTIVO PLATAFORMA")>=0){
										p97AspaImg = 'dialog-cancel-blue-24.png';
									} else if (tCalendarioDiaCambioServicio.eCambioPlataforma != null && tCalendarioDiaCambioServicio.eCambioPlataforma != ""){
										p97AspaImg = 'dialog-cancel-24.png';
									} 
									
									checkBoxHabitual = checkBoxHabitual +" <div class='p97_observacionesPlataforma'><img class='p97_img_aspa'  src='./misumi/images/" + p97AspaImg + "?version=${misumiVersion}'> <span class='p97_observaciones_aspa'>" +tCalendarioDiaCambioServicio.eObservaConfirmaPlataforma + " </span></div> ";
								} 
							}



							if(tCalendarioDiaCambioServicio.eCambioPlataforma != null && tCalendarioDiaCambioServicio.eCambioPlataforma != ""){
								if(tCalendarioDiaCambioServicio.eCambioPlataforma == "S"){
									checkBoxPlataforma = checkBoxPlataforma + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.eCambioPlataforma+"' class='p97_checkBox flgDisabledServTemp'  checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
								}else{
									checkBoxPlataforma = checkBoxPlataforma + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.eCambioPlataforma+"' class='p97_checkBox flgDisabledServTemp'   disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
								}

							} else {

								if(tCalendarioDiaCambioServicio.cambioManual != null && tCalendarioDiaCambioServicio.cambioManual != ""){

									if(tCalendarioDiaCambioServicio.cambioManual == "S"){
										checkBoxPlataforma = checkBoxPlataforma + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.eCambioPlataforma+"' class='p97_checkBox'  checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
									}else{
										checkBoxPlataforma = checkBoxPlataforma + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.eCambioPlataforma+"' class='p97_checkBox'  disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
									}
								} else {

									if(tCalendarioDiaCambioServicio.cambioEstacional != null && tCalendarioDiaCambioServicio.cambioEstacional != ""){
										if(tCalendarioDiaCambioServicio.cambioEstacional == "S"){
											checkBoxPlataforma = checkBoxPlataforma + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.eCambioPlataforma+"' class='p97_checkBox'  checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}else{
											checkBoxPlataforma = checkBoxPlataforma + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.eCambioPlataforma+"' class='p97_checkBox'  disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}
									} else {
										if(tCalendarioDiaCambioServicio.servicioHabitual == "S"){
											checkBoxPlataforma = checkBoxPlataforma + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.eCambioPlataforma+"' class='p97_checkBox'  checked disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}else{
											checkBoxPlataforma = checkBoxPlataforma + "<label class='p97_checkLbl'><input onclick='return false;' type='checkbox' value='"+tCalendarioDiaCambioServicio.eCambioPlataforma+"' class='p97_checkBox'  disabled>"+tCalendarioDiaCambioServicio.denominacionServicio+"</label>";
										}
									}
								}

							}



						}



						//Asignamos los checkboxes a su div correspondiente.
						$("#p97_listaCheckServicioHabitualDiv").html(checkBoxHabitual);
						$("#p97_listaCheckServicioEstacionalDiv").html(checkBoxEstacional);
						$("#p97_listaCheckServicioTemporalDiv").html(checkBoxTemporal);
						$("#p97_listaCheckServicioPlataformaDiv").html(checkBoxPlataforma);

						if (checkBoxTemporal != null && checkBoxTemporal!= "") {
							//Recorremos los servicios temporales revisando por cada servicio si esta checkeado o no. Si esta checkeado tenemos que deshabilitar el mismo servicio en en los servicios temporales
							deshabilitarServiciosTemporales();
						}

						//Se abre el popup.
						$("#p97_popUpServicios").dialog("open");

						//Desiluminamos el día seleccionado e iluminamos el nuevo día seleccionado.						
						//Además mostramos los botones de grabar y de resetear cambios.
						if($("#formDiaEstructuraInfoSuministro"+numeroDia).hasClass("p96_diaVerde")){
							$(".iluminar").removeClass("iluminar")
							$("#formDiaEstructura"+numeroDia).addClass("iluminar");

							//Mostramos los botones.
							//$(".p97_btn").show();


							//events_p97_cross();
						}else{
							//Ocultamos los botones.
							//$(".p97_btn").hide();
						}



						//Guardamos en una variable global el día a iluminar, porque en ocasiones querremos pintar de azul el
						//día seleccionado para cambiar sus servicios y como se repinta al cerrar el popup, perdemos el borde
						//iluminado
						diaAIluminarCalendario = numeroDia;

						//Se asignan los eventos para los checkboxes editables.
						//events_p97_checkBoxClicable();

						//Rellenar datos originales de servicios
						datosOriginalesServiciosP97();
					}
				}else{
					createAlert(data.pDescError,"ERROR");
				}				
			}else{
				
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
			comboBienCargado =  false;
		}	
	});	
}



function deshabilitarServiciosTemporales(){
	var fila = 0;
	$('#p97_listaCheckServicioPlataformaDiv input').each(function()


			{
		fila = fila+1;

		if($(this).hasClass("flgDisabledServTemp")){
			//$("#p97_listaCheckServicioTemporalDiv :nth-child("+fila+")").find(".p97_checkBox").attr("disabled","disabled");
			$("#p97_listaCheckServicioTemporalDiv input")[fila-1].setAttribute("disabled", true);

		}


			});
}


/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************* EVENTOS DE P97 ****************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Función que actualiza la tabla temporal de los servicios asignados a un día.
/*function events_p97_checkBoxClicable(){
	$(".p97_checkBoxClicable").click(function(){
		//Fecha calendario y dia calendario
		var fechaNumeroDia = $("#diaServiciosSeleccionado").val().split("*");

		//Obtenemos el día al que están asignados los servicios
		var fechaCalendario = fechaNumeroDia[0];

		//Obtenemos el numero del dia.
		var numeroDia = fechaNumeroDia[1];

		//Obtenemos el código del servicio.
		var codigoServicio = this.value;

		//Miramos si el check está o no seleccionado.
		var valorCheck = this.checked ? "S":"N";

		//Obtenemos los datos del día modificado.
		var calendarioDia = new CalendarioDia(fechaCalendario);
		var objJson = $.toJSON(calendarioDia);

		$.ajax({
			type : 'POST',
			url : "./calendario/popup/servicios/updateServicioDiaCalendarioTemporal.do?codigoServicio="+codigoServicio+"&valorCheck="+valorCheck,
			contentType : "application/json; charset=utf-8",
			data : objJson,
			dataType : "json",
			cache : false,
			success : function(data) {			
				//Si hay un update de los servicios, indicamos que ese día ya no tiene que tener SQ y que al abrir y cerrar el camoón, queremos que nos enseñoe klos datos de la tabla temporal.
				$("#flgCambioManualServicioNulo"+numeroDia).val("");

				//Actualizamos el cambio manual porque puede que haya que poner o quitar el camión.
				$("#flgCambioManual"+numeroDia).val(data.cambioManual);

				//Quitamos o ponemos los camiones.				
				if(data.cambioManual == "S"){
					$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_camionInferior");
					$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_camionSuperior");

					$('#formDiaEstructuraInfoSuministroCamionCerrado'+numeroDia).prop('title', anQuitarDiasComoServicios );
					$('#formDiaEstructuraInfoSuministroCamionAbierto'+numeroDia).prop('title', anQuitarServicios);	

					//Poner los eventos de iluminar del elemento.
					$('#formDiaEstructuraInfoSuministroCamionAbierto'+numeroDia).bind("mouseover",funcIlumiInf);
					$('#formDiaEstructuraInfoSuministroCamionAbierto'+numeroDia).bind("mouseout",funcNoIlumiInf);

					//Quitamos los eventos de iluminar del elemento.
					$('#formDiaEstructuraInfoSuministroCamionCerrado'+numeroDia).bind("mouseover",funcIlumiSup);
					$('#formDiaEstructuraInfoSuministroCamionCerrado'+numeroDia).bind("mouseout",funcNoIlumiSup);

					//Ponemos el puntero
					$("#formDiaEstructuraInfoSuministro"+numeroDia).addClass("p96_pointer");

				}else if(data.cambioManual == "N"){
					$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).removeClass("p96_camionInferior");
					$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).removeClass("p96_camionSuperior");

					//Quitamos los toolbox con el texto de los camiones.
					$('#formDiaEstructuraInfoSuministroCamionCerrado'+numeroDia).prop('title', "");
					$('#formDiaEstructuraInfoSuministroCamionAbierto'+numeroDia).prop('title', "");

					//Quitamos la clase de iluminar por si acaso se queda puesta.
					$('#formDiaEstructuraInfoSuministroCamionAbierto'+numeroDia).removeClass("p96_iluminar");
					$('#formDiaEstructuraInfoSuministroCamionAbierto'+numeroDia).removeClass("p96_iluminar");
					$('#formDiaEstructuraInfoSuministroCamionCerrado'+numeroDia).removeClass("p96_iluminar");
					$('#formDiaEstructuraInfoSuministroCamionCerrado'+numeroDia).removeClass("p96_iluminar");

					//Quitamos los eventos de iluminar del elemento.
					$('#formDiaEstructuraInfoSuministroCamionAbierto'+numeroDia).unbind("mouseover");
					$('#formDiaEstructuraInfoSuministroCamionAbierto'+numeroDia).unbind("mouseout");

					//Quitamos los eventos de iluminar del elemento.
					$('#formDiaEstructuraInfoSuministroCamionCerrado'+numeroDia).unbind("mouseover");
					$('#formDiaEstructuraInfoSuministroCamionCerrado'+numeroDia).unbind("mouseout");

					//Quitamos el puntero
					$("#formDiaEstructuraInfoSuministro"+numeroDia).removeClass("p96_pointer");
				}
			},
			error : function (xhr, status, error){
				handleError(xhr, status, error, locale);

			}	
		});	
	});
}*/

//Al clicar guardar, se guardan los datos modificados en el PLSQL si existe algún cambio.
function events_p97_btnGuardar(){
	$("#p97_btn_guardado").click(function(){
		//Fecha calendario y dia calendario
		var fechaNumeroDia = $("#diaServiciosSeleccionado").val().split("*");

		//Obtenemos el día al que están asignados los servicios
		var fechaCalendario = fechaNumeroDia[0];

		//Obtenemos los datos del día modificado.
		var calendarioDia = new CalendarioDia(fechaCalendario);

		var tipoEjercicio = $("#tipoEjercicio"+comboValueAnioP96).val();
		var anioEjercicio = $("#anioEjercicio"+comboValueAnioP96).val();

		var objJson = $.toJSON(calendarioDia);
		$.ajax({
			type : 'POST',
			url : './calendario/popup/servicios/guardarCalendario.do?tipoEjercicio='+tipoEjercicio&"anioEjercicio="+anioEjercicio,
			contentType : "application/json; charset=utf-8",
			data : objJson,
			dataType : "json",
			cache : false,
			success : function(data) {
				if(data.codError == 0){
					//Ya hemos guardado todo, por lo que indicamos que no hay elementos que difieren entre los datos reales y los temporales.
					//flgAlMenosUnElementoGuardado = "N";
					serviciosGuardadosCorrectamente97 = "S";
					createAlert(calendarioGuardadoCorrectamente,"INFO");
				}else{
					createAlert(data.descError,"ERROR");
				}
			},
			error : function (xhr, status, error){
				handleError(xhr, status, error, locale);
			}	
		});		
	});
}

function events_p97_tick(){
	$("#p97_btn_tick").click(function(){
			//Fecha calendario y dia calendario
			var fechaNumeroDia = $("#diaServiciosSeleccionado").val().split("*");

			var tipoEjercicio = $("#tipoEjercicio"+comboValueAnioP96).val();

			var codigoEjercicio = comboValueAnioP96;
			var anioEjercicio = $("#anioEjercicio"+comboValueAnioP96).val();

			//Obtenemos el día al que están asignados los servicios
			var fechaCalendario = fechaNumeroDia[0];

			//Obtenemos el numero del dia.
			var numeroDia = fechaNumeroDia[1];

			//Obtenemos los servicios cambiados y los pasamos al controlador.
			var calendarioProcesosDiarios = obtenerCalendarioProcesosDiarios();

			//Obtenemos los datos del día modificado.
			var calendarioDia = new CalendarioDia(fechaCalendario,null,null,null,null,null,null,null,calendarioProcesosDiarios);
			var objJson = $.toJSON(calendarioDia);

			$.ajax({
				type : 'POST',
				url : './calendario/popup/servicios/updateServicioDiaCalendarioTemporal.do?tipoEjercicio='+tipoEjercicio+'&codigoEjercicio='+anioEjercicio,
				contentType : "application/json; charset=utf-8",
				data : objJson,
				dataType : "json",
				cache : false,
				success : function(data) {			
					//Si hay un update de los servicios, indicamos que ese día ya no tiene que tener SQ y que al abrir y cerrar el camión, queremos que nos enseñoe los datos de la tabla temporal.
					$("#flgCambioManualServicioNulo"+numeroDia).val("");

					//Actualizamos el cambio manual porque puede que haya que poner o quitar el camión.
					$("#flgCambioManual"+numeroDia).val(data.cambioManual);

					//Quitamos o ponemos los camiones.				
					if(data.cambioManual == "S"){
						$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_camionInferior");
						$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_camionSuperior");

						$('#formDiaEstructuraInfoSuministroCamionCerrado'+numeroDia).prop('title', anQuitarDiasComoServicios );
						$('#formDiaEstructuraInfoSuministroCamionAbierto'+numeroDia).prop('title', anQuitarServicios);	

						//Poner los eventos de iluminar del elemento.
						$('#formDiaEstructuraInfoSuministroCamionAbierto'+numeroDia).bind("mouseover",funcIlumiInf);
						$('#formDiaEstructuraInfoSuministroCamionAbierto'+numeroDia).bind("mouseout",funcNoIlumiInf);

						//Quitamos los eventos de iluminar del elemento.
						$('#formDiaEstructuraInfoSuministroCamionCerrado'+numeroDia).bind("mouseover",funcIlumiSup);
						$('#formDiaEstructuraInfoSuministroCamionCerrado'+numeroDia).bind("mouseout",funcNoIlumiSup);

						//Ponemos el puntero
						$("#formDiaEstructuraInfoSuministro"+numeroDia).addClass("p96_pointer");
					}else if(data.cambioManual == "N"){
						$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).removeClass("p96_camionInferior");
						$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).removeClass("p96_camionSuperior");

						//Quitamos los toolbox con el texto de los camiones.
						$('#formDiaEstructuraInfoSuministroCamionCerrado'+numeroDia).prop('title', "");
						$('#formDiaEstructuraInfoSuministroCamionAbierto'+numeroDia).prop('title', "");

						//Quitamos la clase de iluminar por si acaso se queda puesta.
						$('#formDiaEstructuraInfoSuministroCamionAbierto'+numeroDia).removeClass("p96_iluminar");
						$('#formDiaEstructuraInfoSuministroCamionAbierto'+numeroDia).removeClass("p96_iluminar");
						$('#formDiaEstructuraInfoSuministroCamionCerrado'+numeroDia).removeClass("p96_iluminar");
						$('#formDiaEstructuraInfoSuministroCamionCerrado'+numeroDia).removeClass("p96_iluminar");

						//Quitamos los eventos de iluminar del elemento.
						$('#formDiaEstructuraInfoSuministroCamionAbierto'+numeroDia).unbind("mouseover");
						$('#formDiaEstructuraInfoSuministroCamionAbierto'+numeroDia).unbind("mouseout");

						//Quitamos los eventos de iluminar del elemento.
						$('#formDiaEstructuraInfoSuministroCamionCerrado'+numeroDia).unbind("mouseover");
						$('#formDiaEstructuraInfoSuministroCamionCerrado'+numeroDia).unbind("mouseout");

						//Quitamos el puntero
						$("#formDiaEstructuraInfoSuministro"+numeroDia).removeClass("p96_pointer");
					}

					//Actualizamos los valores de los checks temporales con los valores temporales.
					actualizarCheckBoxesTemporales();

					//Monstramos mensaje satisfactorio
					//createAlert(cambiosGuardadosP97,"INFO");

					//Cerramos el popup
					$("#p97_popUpServicios").dialog('close');
				},
				error : function (xhr, status, error){
					handleError(xhr, status, error, locale);

				}	
			});	
		
	});
}

//Al pulsar la cruz, deja los checkboxes como el último estado guardado en la temporal.
function events_p97_cross(){
	$("#p97_btn_cross").click(function(){
		var checkBoxes = $(".p97_checkBoxClicable");
		var codigoServicio;
		var checkeadoOrig;
		for(var i = 0;i<checkBoxes.length;i++){
			codigoServicio = checkBoxes[i].value;
			checkeadoOrig = $("#estadoCheckTmp"+codigoServicio).val();

			if(checkeadoOrig == "S"){
				checkBoxes[i].checked = true;
			}else{
				checkBoxes[i].checked = false;
			}			
		}
	});
}

//Inserta los hiddens de los datos temporales y originales.
function datosOriginalesServiciosP97(){
	//Vacíamos los valores de los checkboxes
	$("#p97_checksOriginales").empty();

	var checkBoxes = $(".p97_checkBoxClicable");

	var codigoServicio;
	var checkeado;
	var estructuraCheckBox;

	//Por cada servicio crea dos hiddens, uno mantendrá el valor de nada más abrir el popup y el otro
	//el último estado guardado en la temporal.
	for(var i = 0;i<checkBoxes.length;i++){
		codigoServicio = checkBoxes[i].value;
		checkeado = checkBoxes[i].checked ? "S":"N";

		estructuraCheckBox = "<input type='hidden' id='estadoCheckTmp"+codigoServicio+"' value='"+checkeado+"'>";
		estructuraCheckBox = estructuraCheckBox + "<input type='hidden' id='estadoCheckOrig"+codigoServicio+"' value='"+checkeado+"'>";
		$("#p97_checksOriginales").append(estructuraCheckBox);
	}
}

//Mira si existen cambios sobre los valores originales en comparación con los temporales, que guardan el último estado 
//guardado en la temporal.
function existenCambiosOrigP97(){
	var checkBoxes = $(".p97_checkBoxClicable");
	var codigoServicio;
	var checkeado;
	for(var i = 0;i<checkBoxes.length;i++){
		codigoServicio = checkBoxes[i].value;

		if($("#estadoCheckOrig"+codigoServicio).val() != $("#estadoCheckTmp"+codigoServicio).val()){
			return true;
		}
	}
	return false;
}

//Mira si existen cambios sobre los valores temporales (que corresponden a los últimos cambios guardados en la temporal)
//y lo que tenemos actualmente en los checks.
function existenCambiosTmpP97(){
	var checkBoxes = $(".p97_checkBoxClicable");
	var codigoServicio;
	var checkeado;
	for(var i = 0;i<checkBoxes.length;i++){
		codigoServicio = checkBoxes[i].value;
		checkeado = checkBoxes[i].checked ? "S":"N";

		if($("#estadoCheckTmp"+codigoServicio).val() != checkeado){
			return true;
		}
	}
	return false;
}

//Sirve para mandar al controlador los cambios de los servicios.
function obtenerCalendarioProcesosDiarios(){
	var listadoServiciosCentroTemporal = [];

	var checkBoxes = $(".p97_checkBoxClicable");
	var codigoServicio;
	var checkeado;
	for(var i = 0;i<checkBoxes.length;i++){
		codigoServicio = checkBoxes[i].value;
		checkeado = checkBoxes[i].checked ? "S":"N";

		if($("#estadoCheckTmp"+codigoServicio).val() != checkeado){
			tCalendarioDiaCambioServicio = new TCalendarioDiaCambioServicio(codigoServicio,checkeado);
			listadoServiciosCentroTemporal.push(tCalendarioDiaCambioServicio);
		}
	}
	var calendarioProcesosDiarios = new CalendarioProcesosDiarios(listadoServiciosCentroTemporal);
	return calendarioProcesosDiarios;
} 

//Actualizamos los checkboxes temporales con los valores de la temporal después de guardar.
function actualizarCheckBoxesTemporales(){
	var checkBoxes = $(".p97_checkBoxClicable");
	var codigoServicio;
	var checkeado;
	for(var i = 0;i<checkBoxes.length;i++){
		codigoServicio = checkBoxes[i].value;
		checkeado = checkBoxes[i].checked ? "S":"N";

		if($("#estadoCheckTmp"+codigoServicio).val() != checkeado){
			$("#estadoCheckTmp"+codigoServicio).val(checkeado);
		}
	}
}