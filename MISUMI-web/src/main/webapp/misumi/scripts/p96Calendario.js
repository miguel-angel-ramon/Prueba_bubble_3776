/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/****************************************************************** CONSTANTES ***************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
var maxFestivoLocal = 4;
var minFestivoLocal = 0;
var origenAvisosE = 'AVISOSE';
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/**************************************************************** VARIABLES ******************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
//Guarda el valor del combo
var comboValueServicioP96;
var comboValueAnioP96;
var comboAnioTipoEjercicioP96;

//Guarda si hay que validar el calendario.
var validarCalendarioP96;

//Guarda los días que hay que pintar en verde.
var numeroDiasAdelanteAtrasVerdeP96;


//Cargamos los datos del JSON.
var diaCerrado;
var noSePuedeQuitarDiaServicio;
var consultarServicios;
var anQuitarDiasComoServicios;
var anQuitarServicios;
var sinRegistrosModificados;
var calendarioGuardadoCorrectamente;
var ejercicioPropuestaInicialP96;
var ejercicioCalendarioActualP96;
var calendarioValidadoConExitoP96;

//Flag que guarda si se ha guardado en la temporal al menos un elemento.
var flgAlMenosUnElementoGuardado = "N";

//Variable global que guarda la lista de meses y años que se usarán para paginar.
var mesAnioLst;

//Indices de paginaciones entre meses.
var idxActual;
var idxFinLista;

//Guarda el rol 
var rol;

//Lista de servicios en vasiable global.
var listaDeServicios;

//Guarda el día a iluminar
var diaAIluminarCalendario = "";

//Variable que sirve para el parpadeo
var countParpadeo = 0;		

//Numero de avisos del centro
var p96NumAvisos=0;

//MISUMI-158
var hashContadorFestivos = new Object();
var maxFestivosLocalesAlcanzado;
//FIN MISUMI-158

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************** DOCUMENTO LISTO **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
$(document).ready(function(){
	initializeScreenP96();
});


/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************ INICIALIZACIÓN DE ELEMENTOS E IDIOMA *******************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
function existenDatosSinGuardar(){
	var existenDatos;
	$.ajax({
		type : 'GET',			
		url : './calendario/existenDatosSinGuardar.do',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		async:false,
		success : function(data) {			
			existenDatos = data;
		},
		error : function (xhr, status, error){
			//$("#p59_AreaStockDatos .loading").css("display", "none");	
			handleError(xhr, status, error, locale);				
		}
	});
	return existenDatos;
}

function initializeScreenP96(){	

	//Inicializamos el idioma.
	loadP96(locale);

	//Iluminamos, des iluminamos el botón.
	//events_p96_btnIluminar();

	//Guardamos los datos.
	events_p96_btnGuardar();

	//Abrimos los servicios temporales
	events_p96_btnServiciosTemporales();


	//Validamos el calendario
	events_p96_btnValidar();

	//Abrimos calendario anual
	events_p100_btnCalendarioAnual();

	//Eventos de paginacion de calendario.
	events_p96_flecha_ant();	
	events_p96_flecha_sig();


	//Al pinchar fuera del aplicativo del calendario, si existen datos sin guardar se avisa al usuario.
	window.onbeforeunload = function(event) {
		//var existenDatos = existenDatosSinGuardar();

		//Si es distinto de cero hay datos sin guardar.
		if(flgAlMenosUnElementoGuardado == "S"){ 
			// Cancel the event as stated by the standard.
			event.preventDefault();

			// Chrome requires returnValue to be set.
			event.returnValue = "Hay datos del calendario sin guardar y las modificaciones se perderán ¿Deseas ir a otra parte del aplicativo?";
		}
	};

	$("#p03_btn_aceptar").bind("click", function(e) {
		//Al pulsar aceptar si es un guardado de los servicios de un dia se cierra el popup de servicios diarios.
		if(serviciosGuardadosCorrectamente97 == "S"){
			//Ponemos el flag en N para que si hay error, no cierre el popup y solo cerrarlo si se guarda correctamente.
			serviciosGuardadosCorrectamente97 = "N";

			//Se cierra el popup de servicios.
			$("#p97_popUpServicios").dialog("close");
		}//Al pulsar aceptar si es un guardado de los cambios estacionales de un dia se cierra el popup de cambios estacionales y 
		//se recargan los dias.
		/*else if(cambiosEstacionalesP98 == "S"){
			//Se cierra el popup de cambios estacionales.
			$("#p98_popUpServiciosTemporales").dialog("close");

			//Al cerrar el calendario, se pone el flag de cambiosEstacionalesP98 en N.
		}*/
	});
}

//Función para buscar los mensajes de la pantalla en idioma seleccionado, por defecto 'es'
function loadP96(locale){
	this.i18nJSON = './misumi/resources/p96Calendario/p96Calendario_' + locale + '.json';

	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		diaCerrado = data.diaCerrado;
		noSePuedeQuitarDiaServicio = data.noSePuedeQuitarDiaServicio;
		consultarServicios =  data.consultarServicios;
		anQuitarDiasComoServicios  = data.anQuitarDiasComoServicios;
		anQuitarServicios = data.anQuitarServicios;
		sinRegistrosModificados = data.sinRegistrosModificados;
		calendarioGuardadoCorrectamente = data.calendarioGuardadoCorrectamente;
		ejercicioPropuestaInicialP96 = data.ejercicioPropuestaInicial;
		ejercicioCalendarioActualP96 = data.ejercicioCalendarioActual;
		calendarioValidadoConExitoP96 = data.calendarioValidadoConExito;
		maxFestivosLocalesAlcanzado = data.maxFestivosLocalesAlcanzado;

		//Una vez cargados todos los textos json, cargamos los avisos y el combo de años, para que no salga undefined en algunos textos en el caso
		//de que se ejecute y cargue antes la llamada al procedimiento al archivo json.
		load_cmb_anioCalendarioP96();
		load_warningsP96();
		iniciarParpadeo();
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/*************************************************************FUNCIONES DE P96 ***************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
//Función que carga el combo de los servicios.
function load_cmb_servicioP96(){
	//Se crea el combobox
	$("#p96_cmb_servicio").combobox(null);


	//var codigoEjercicio = comboValueAnioP96


	var codigoEjercicio = comboValueAnioP96.substring(0,4);


	var tipoEjercicio = $("#tipoEjercicio"+comboValueAnioP96).val();
	var options = "";
	$.ajax({
		type : 'POST',
		url : './calendario/consultarServiciosCombo.do?codigoEjercicio='+codigoEjercicio+'&tipoEjercicio='+tipoEjercicio,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {
			if(data != null){
				//Si todo ha ido bien carga las opciones de servicios
				if(data.pCodError == 0){					
					if(data.listCatalogoDescripcion != null && data.listCatalogoDescripcion.length >0){
						options = "<option value=' '>TODOS</option>";
						for (i = 0; i < data.listCatalogoDescripcion.length; i++){							
							options = options + "<option value='" + data.listCatalogoDescripcion[i].localizador + "'>" + data.listCatalogoDescripcion[i].descripcion + "</option>";					
						}
						$("select#p96_cmb_servicio").html(options);

						//Ponemos la opción TODOS por defecto.
						$("#p96_cmb_servicio").combobox('autocomplete',"TODOS");
						$("#p96_cmb_servicio").combobox('comboautocomplete',"null");
						$("select#p96_cmb_servicio").html(options);

						//Indicamos que el valor del servicio por defecto es vacío
						comboValueServicioP96 = " ";

						//Guardamos la lista de servicios en una variable global.
						listaDeServicios = data;

						//Quitamos el día a iluminar por consultar sus servicios porque vamos a refrescar el calendario completamente.
						diaAIluminarCalendario  = "";

						//Cargamos los días en la temporal después de consultar el PLSQL, por eso la recarga es N.
						load_diasCalendarioP96(comboValueAnioP96," ",data,"N",null,null);
					}else{

					}					
				}else{
					createAlert(data.pDescError,"ERROR");					
				}		
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});	

	$("#p96_cmb_servicio").combobox({
		selected: function(event, ui) {
			if ( ui.item.value!="" && ui.item.value!="null" ) {
				//Si el valor seleccionado difiere del valor seleccionado anterior
				//buscamos, si es el mismo no.
				if(ui.item.value != comboValueServicioP96){							
					comboValueServicioP96 = $("#p96_cmb_servicio").val();
					if (comboValueServicioP96 !=null){
						//Quitamos el día a iluminar por consultar sus servicios porque vamos a refrescar el calendario completamente.
						diaAIluminarCalendario  = "";

						//Al cambiar el valor del combobox, es una recarga de la temporal.
						load_diasCalendarioP96(comboValueAnioP96,comboValueServicioP96,null,"S",mesAnioLst[idxActual],"N");							
					}
				}
			}
		}  ,
	}); 
}

function load_cmb_anioCalendarioP96(){
	//Se crea el combobox
	$("#p96_cmb_anio").combobox(null);

	var options = "";
	$.ajax({
		type : 'POST',
		url : './calendario/consultarAnioCalendarioCombo.do',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		async: false,
		success : function(data) {
			if(data != null){
				if(data.codError == 0){					
					if(data.lstCalendarioEjercicio != null && data.lstCalendarioEjercicio.length >0){
						var opcionPreferente = null;
						var anioPreferente = null;
						//Pintamos las opciones del combo
						for (i = 0; i < data.lstCalendarioEjercicio.length; i++){							
							var ejercicio = data.lstCalendarioEjercicio[i];
							//Si es de tipo P ponemos un texto delante del año y si no otro
							if(ejercicio.tipoEjercicio == "P"){
								//Si validar ejercicio es S pintamos la opción de rojom si no se mantiene en negro.
								if(ejercicio.flgPendienteValidarEjercicio == "S" && ejercicio.flgEjercicioValidado == "N"){
									options = options + "<option id='p96_option_"+ejercicio.anioEjercicio+"' value='" + ejercicio.anioEjercicio + ejercicio.tipoEjercicio + "' class='p96_optionRojo'>" + ejercicioPropuestaInicialP96 + "-" + ejercicio.anioEjercicio + "</option>";			
								}else{
									options = options + "<option id='p96_option_"+ejercicio.anioEjercicio+"' value='" + ejercicio.anioEjercicio +  ejercicio.tipoEjercicio + "' class='p96_optionNegro'>" + ejercicioPropuestaInicialP96 + "-" + ejercicio.anioEjercicio + "</option>";			
								}																	
							}else{
								//Si validar ejercicio es S pintamos la opción de rojom si no se mantiene en negro.
								if(ejercicio.flgPendienteValidarEjercicio == "S" && ejercicio.flgEjercicioValidado == "N"){
									options = options + "<option id='p96_option_"+ejercicio.anioEjercicio+"' value='" + ejercicio.anioEjercicio +  ejercicio.tipoEjercicio + "' class='p96_optionRojo'>" + ejercicioCalendarioActualP96 + "-" + ejercicio.anioEjercicio + "</option>";										
								}else{
									options = options + "<option id='p96_option_"+ejercicio.anioEjercicio+"' value='" + ejercicio.anioEjercicio +  ejercicio.tipoEjercicio + "' class='p96_optionNegro'>" + ejercicioCalendarioActualP96 + "-" + ejercicio.anioEjercicio + "</option>";	
								}
							}							
							//Si el origen de la pantalla es avisos, queremos mostrar el calendario con avisos, que siempre es el de tipo E.
							var origenPantalla = $("#p96_origenPantalla").val();
							if(origenPantalla == origenAvisosE){
								if(ejercicio.tipoEjercicio == 'E'){
									//Si hay más de un tipo E, se selecciona el de año ejercicio mayor.
									//Si sólo hay uno, se selecciona ese. Al venir del aviso sabemos que
									//hay un tipo E como mínimo.
									if(anioPreferente == null){
										opcionPreferente = i;
										anioPreferente = ejercicio.anioEjercicio;
									}else{
										if(anioPreferente < ejercicio.anioEjercicio){
											opcionPreferente = i;
											anioPreferente = ejercicio.anioEjercicio;
										}
									}
								}
							}else{
								//Obtenemos el índice del año a mostrar por defecto. Si pendiente validar ejercicio es S esa será la opción preferente,
								//si no existe ninguno, miramos que tipo ejercicio sea E.
								if(ejercicio.flgPendienteValidarEjercicio == 'S'){
									opcionPreferente = i;
									anioPreferente = ejercicio.anioEjercicio;	
								}else{
									if(ejercicio.tipoEjercicio == 'E' && opcionPreferente == null){
										opcionPreferente = i;
										anioPreferente = ejercicio.anioEjercicio;
									}
								}
							}

							//Rellenamos los hiddens con la información de cada opción del combo.
							//Esos hiddens sirven para mostrar y ocultar botones, etc. por lo que es
							//necesario guardar su información en algún sitio.
							rellenarHiddensComboEjercicio(ejercicio);			
						}

						//Si no existe opción preferente, selecciona la primera opción (0).
						if(opcionPreferente == null){
							opcionPreferente = 0;
							anioPreferente = data.lstCalendarioEjercicio[0].anioEjercicio;
						}
						//Obtenemos el texto a mostrar 
						var txtEjercicio;
						if(data.lstCalendarioEjercicio[opcionPreferente].tipoEjercicio == "P"){
							txtEjercicio = ejercicioPropuestaInicialP96;
						}else{
							txtEjercicio = ejercicioCalendarioActualP96;
						}

						//Rellenamos el combo con las opciones
						$("select#p96_cmb_anio").html(options);

						//Indicamos la opción seleccionada y su texto. Es necesario txtEjercicio porque si no, muestra solo el año.
						$("#p96_cmb_anio").combobox('autocomplete',txtEjercicio + "-" + data.lstCalendarioEjercicio[opcionPreferente].anioEjercicio);
						$("#p96_cmb_anio").combobox('comboautocomplete',txtEjercicio + "-" + data.lstCalendarioEjercicio[opcionPreferente].anioEjercicio);					

						//Ponemos el valor.
						$("#p96_cmb_anio").val(data.lstCalendarioEjercicio[opcionPreferente].anioEjercicio + data.lstCalendarioEjercicio[opcionPreferente].tipoEjercicio);

						//Introducimos el estilo del primer elemento seleccionado. Para ello utilizamos el id que hemos creado
						//para los option.
						$("#p96_anio >input").addClass($("#p96_option_"+anioPreferente)[0].className);

						//Indicamos que el valor del combo es el seleccionado

						comboValueAnioP96 = $("#p96_cmb_anio").val(); //Contiene AnioEjercicioTipoEjercio

						//Mostramos u ocultamos los botones del calendario.
						rol = data.rol;
						//showHideButtonsP96(data.lstCalendarioEjercicio[opcionPreferente].anioEjercicio);
						showHideButtonsP96(comboValueAnioP96);

						//Mostramos u ocultamos las alertas del calendario.
						//showHideAlertaP96(data.lstCalendarioEjercicio[opcionPreferente].anioEjercicio);
						showHideAlertaP96(comboValueAnioP96);

						//Inicializamos el combo de servicios y buscamos el calendario con el servicio por defecto.
						load_cmb_servicioP96();								
					}
				}else{
					createAlert(data.descrError,"ERROR");
				}					
			}else{

			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});	

	$("#p96_cmb_anio").combobox({
		selected: function(event, ui) {
			if ( ui.item.value!="" && ui.item.value!="null" ) {
				//Si el valor seleccionado difiere del valor seleccionado anterior
				//buscamos, si es el mismo no.
				if(ui.item.value != comboValueAnioP96){		

					comboValueAnioP96 = $("#p96_cmb_anio").val();
					if (comboValueAnioP96 !=null){
						//Borramos los posibles estilos
						$("#p96_anio >input").removeClass("p96_optionRojo");
						$("#p96_anio >input").removeClass("p96_optionNegro");

						//Introducimos el estilo nuevo.
						$("#p96_anio >input").addClass(ui.item.className);

						//Quitamos el día a iluminar por consultar sus servicios porque vamos a refrescar el calendario completamente.
						diaAIluminarCalendario  = "";

						//Al cambiar el valor del combobox, no es una recarga de la temporal. Por lo que hay que volver a llamar al PLSQL.
						load_diasCalendarioP96(comboValueAnioP96,comboValueServicioP96,listaDeServicios,"N",null,"N");	

						//Mostramos u ocultamos las alertas del calendario.
						showHideAlertaP96(comboValueAnioP96);

						//Mostramos u ocultamos los botones del calendario.
						showHideButtonsP96(comboValueAnioP96);	

						//Mostramos u ocultamos el icono de avisos
						showHideWarningsP96();

						//Ponemos a ese año los festivos locales que se han alimentado.						
						var festivosLocalesAlimentados = hashContadorFestivos[$("#p96_cmb_anio").val()];
						$("#p96_lbl_diasRestantes").text(festivosLocalesAlimentados);
					}
				}
			}
		}  ,
	}); 
}

function countFestivosLocales(anioEjercicio,tipoEjercicio){
	$.ajax({
		type : 'GET',			
		url : './calendario/countFestivosLocales.do?codigoEjercicio='+anioEjercicio+'&tipoEjercicio='+tipoEjercicio,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		async:false,
		success : function(data) {			
			//Rellenamos el hash
			hashContadorFestivos[anioEjercicio+tipoEjercicio] = data;

			//Ponemos el valor
			$("#p96_lbl_diasRestantes").text(data);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}
	});
}

function recargaP96Anio(){

	comboValueAnioP96 = $("#p96_cmb_anio").val();
	if (comboValueAnioP96 !=null){

		//Quitamos el día a iluminar por consultar sus servicios porque vamos a refrescar el calendario completamente.
		diaAIluminarCalendario  = "";

		//Al cambiar el valor del combobox, no es una recarga de la temporal. Por lo que hay que volver a llamar al PLSQL.
		load_diasCalendarioP96(comboValueAnioP96,comboValueServicioP96,listaDeServicios,"N",null,"N");	

		//Mostramos u ocultamos las alertas del calendario.
		showHideAlertaP96(comboValueAnioP96);

		//Mostramos u ocultamos los botones del calendario.
		showHideButtonsP96(comboValueAnioP96);		

		//Mostramos u ocultamos el icono de avisos
		showHideWarningsP96();
	}
}

//Función que muestra una alerta u otra y pinta la fecha limite de validacion en la alerta correspondiente
function showHideAlertaP96(AnioTipoEjercicio){
	var flgPendienteValidarEjercicio = $("#flgPendienteValidarEjercicio"+AnioTipoEjercicio).val()
	var flgEjercicioValidado = $("#flgEjercicioValidado"+AnioTipoEjercicio).val();
	var flgModificarCalendarioCentro = $("#flgModificarCalendarioCentro"+AnioTipoEjercicio).val();

	//Obtenemos el tipo de ejercicio. Los mensajes de validación solo se muestran si tipoEjercicio='P'
	var tipoEjercicio = $("#tipoEjercicio"+comboValueAnioP96).val();

	if(!(rol == 2 && flgModificarCalendarioCentro  == "N")){
		if (flgEjercicioValidado == 'S') {
			//Mostramos el mensaje de validado
			if(tipoEjercicio == "P"){
				$("#p96_mensajeValidado").attr("style", "display:inline; visibility:visible;");
			}else{
				$("#p96_mensajeValidado").attr("style", "display:inline; visibility:hidden;");
			}
			$("#p96_alerta").attr("style", "display:none;");
			$("#p96_mensajeNoValidado").attr("style", "display:none;");
		} else {
			if(flgPendienteValidarEjercicio == "S"){
				//Mostramos la alerta con la Fecha limite de validación
				//Obtenemos la fehca limite de validación que corresponde al Año del ejercicio consultado.
				strFechaLimiteValidacion = $("#strFechaLimiteValidacion"+AnioTipoEjercicio).val();

				//De la fecha limite obtenemos el dia por un lado y el mes por otro
				fechaLimiteAux  = strFechaLimiteValidacion.split("-");
				diaFechaLimite  = fechaLimiteAux[0];
				mesFechaLimite  = fechaLimiteAux[1]; 
				anioFechaLimite = fechaLimiteAux[2]; 

				//Obtenemo literal del mes
				literalMesFechaLimite = obtenerLiteralMes(diaFechaLimite,mesFechaLimite,anioFechaLimite);

				$("#p96_diaCalendario").text(diaFechaLimite);
				$("#p96_mesCalendario").text(literalMesFechaLimite);

				$("#p96_alerta").attr("style", "display:inline;");
				$("#p96_mensajeValidado").attr("style", "display:none;");
				$("#p96_mensajeNoValidado").attr("style", "display:none;");
			}else{
				if(tipoEjercicio == "P"){
					$("#p96_mensajeNoValidado").attr("style", "display:inline; visibility:visible;");
				}else{
					$("#p96_mensajeNoValidado").attr("style", "display:inline; visibility:hidden;");
				}
				$("#p96_mensajeValidado").attr("style", "display:none;");
				$("#p96_alerta").attr("style", "display:none;");	
			}		
		}	
	}
}


function obtenerLiteralMes(dia, mes, anio){


	// ENERO FEBRERO MARZO ABRIL MAYO JUNIO JULIO AGOSTO SEPTIEMBRE OCTUBRE NOVIEMBRE DICIEMBRE
	var nombreMes = $.datepicker.formatDate("MM", new Date(anio, mes - 1, dia),{
		monthNames: $.datepicker.regional[ "es" ].monthNames
	});

	//Pasar de ENERO a Enero, FEBRERO Febrero
	//nombreMes = nombreMes.replace(/\w\S*/g, nombreMes.charAt(0).toUpperCase() + nombreMes.substr(1).toLowerCase());


	return nombreMes;
}


function load_diasCalendarioP96(codigoEjercicio, codigoServicio, lstCodigosServicio,recarga,mesAnio,eliminarTemporal){
	//Evitamos que el usuario pueda pulsar el botón de calendario dos veces.
	calendarioEnProcesoDeCarga = "S";

	//Ocultamos el calendario
	$("#p96_calendario").hide();

	//Borramos los elementos html de días de calendario que pueda haber en el calendario.
	//Limpiamos el calendario.
	limpiarCalendarioP96();

	//Limpiamos el mes del calendario
	$("#p96_calendario_mes").empty();

	var anioEjercicio = codigoEjercicio.substring(0,4);

	var tipoEjercicio = $("#tipoEjercicio"+codigoEjercicio).val();

	var objJson = $.toJSON(lstCodigosServicio);
	//Pasamos fecha desde, fecha hasta y servicio.
	$.ajax({
		type : 'POST',
		url : "./calendario/consultarCalendario.do?codigoEjercicio="+anioEjercicio+"&codigoServicio="+codigoServicio+"&recarga="+recarga+"&mesAnio="+mesAnio+"&tipoEjercicio="+tipoEjercicio+"&eliminarTemporal="+eliminarTemporal,
		contentType : "application/json; charset=utf-8",
		data : objJson,
		dataType : "json",
		cache : false,
		success : function(data) {
			//Para que el usuario pueda volver a cargar el calendario.
			calendarioEnProcesoDeCarga = "N";

			if(data != null){
				if(data.pCodError == 0){
					//Mostramos el combo de servicios.
					$("#p96_filtroServicio").show();

					//Como hemos cargado el calendario entero del PLSQL, indicamos que todavía no se ha realizado ningún cambio sobre la tabla temporal.
					flgAlMenosUnElementoGuardado = "N";

					//Si no es recarga, guardamos en variables globales los valores de validarCalendario, numeroDiasAdelanteAtrasVerde. Así no tenemos que volver a calcularlas.
					if(recarga == "N"){
						//Miramos si hay que validar el calendario
						validarCalendarioP96 = data.validarCalendario;

						//Mostramos u ocultamos el calendario según el flag de validar calendario
						if(validarCalendarioP96 == "S"){
							$("#p96_pg").removeClass("p96_pgNoVisible");
						}else{
							$("#p96_pg").addClass("p96_pgNoVisible");							
						}
						//Indicamos el número de días que hay que pintar delante y detrás del festivo.
						numeroDiasAdelanteAtrasVerdeP96 = data.numeroDiasAdelanteAtrasVerde;

						//Guardamos la lista de meses y años en una variable global para usarla al paginar.
						mesAnioLst = data.mesAnioLst;												

						//Indicamos que el idxActual es el 0 de los meses y el largo de la lista.

						if (tipoEjercicio == "E") { //Nos situamos en el mes del dia actual
							//Obtenemos el mes y el año actual.
							var mesAnioActual = data.listadoFechaTemporal[0].mesAnio;

							//Localizamo el mes-ano actual en que posicion esta en la lista
							for (i = 0; i < mesAnioLst.length; i++){		
								if (mesAnioLst[i] == mesAnioActual) {
									idxActual = i;
								}	
							}	

						} else {

							idxActual = 0;
						}

						idxFinLista = data.mesAnioLst.length;

						var flgCambioEstacional = $("#flgCambioEstacional"+comboValueAnioP96).val();
						var flgPendienteValidarEjercicio = $("#flgPendienteValidarEjercicio"+comboValueAnioP96).val();


						if (flgPendienteValidarEjercicio != "N") {
							if(tipoEjercicio != "E" && flgCambioEstacional == "S"){
								//Cargamos los servicios temporales del ejercicio.
								load_serviciosTemporalesP98();
							}
						}
					} 

					//Obtenemos el mes y el año actual. MM-YYYY
					var partesMes = mesAnioLst[idxActual].split("-");

					var anioActual = partesMes[0];
					var mesActual = partesMes[1];



					var nombreMes = $.datepicker.formatDate("MM", new Date(anioActual, mesActual - 1, '01'),{
						monthNames: $.datepicker.regional[ "es" ].monthNames
					});

					//Mes del calendario  y año en el título.
					$("#p96_calendario_mes").text(nombreMes + "-" + anioActual);

					//Dibujamos el calendario.
					dibujarDiasCalendarioP96(data.listadoFechaTemporal);

					//Actualizamos las flechas
					actualizarNumeroDePaginaP96(idxActual,idxFinLista);

					//Si hay día a iluminar y además es verde, se ilumina.
					if(diaAIluminarCalendario != "" && $("#formDiaEstructuraInfoSuministro"+diaAIluminarCalendario).hasClass("p96_diaVerde")){
						//Desiluminamos el día seleccionado e iluminamos el nuevo día seleccionado.
						$(".iluminar").removeClass("iluminar")
						$("#formDiaEstructura"+diaAIluminarCalendario).addClass("iluminar");
					}

					/***************** Inicio Misumi 158 *****************/
					//Si no es una recarga, se supone que es o porque hemos cambiado el combo del año, que tiene que calcular 
					//todos los días de ese año o porque acabamos de entrar en el aplicativo de calendario, que también
					//tiene que calcular todos los días del año por defecto. Por este motivo, si la recarga es N, significa
					//que todavía no están cargadas en la temporal los días de ese año, por lo que hasta que se ejecute esta 
					//llamada al controlador, no podemos saber cuántos festivos locales hay. Una vez se ejecuta, hay que mirar
					//si el hash con este año y tipo tiene datos ya. Hay que recordar, que aunque recarga sea N, siempre que se
					//cambia el combo del año se pasa una N, y puede que los datos ya se hayan calculado al haber accedido
					//a esa opción previamente (en el controlador se mira que aunque recarga sea N, no existan esos datos en la
					//temporal. Si existen la temporal se queda como está, si no se rellena), por lo que hay que mirar que el hash
					//no tenga ya valor para no actualizarlo en vano.
					if(recarga == "N"){
						var contadorFestivoLocal = hashContadorFestivos[$("#p96_cmb_anio").val()];
						if(contadorFestivoLocal == undefined){
							countFestivosLocales(anioEjercicio,tipoEjercicio);							
						}else{
							$("#p96_lbl_diasRestantes").text(contadorFestivoLocal);
						}
					}else{
						var contadorFestivoLocal = hashContadorFestivos[$("#p96_cmb_anio").val()];
						$("#p96_lbl_diasRestantes").text(contadorFestivoLocal);
					}					
					/******************* Fin Misumi 158 ******************/

					//Mostramos el calendario
					$("#p96_calendario").show();
				}else{
					createAlert(data.pDescError,"ERROR");

					//Ocultamos el combo de servicios.
					$("#p96_filtroServicio").hide();
				}				
			}else{

			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});	
}

//Mostramos los ejercicios según el rol y los flags correspondientes del año seleccionado.
function showHideButtonsP96(anioTipoEjercicio){
	//Obtenemos los flags para pintar los botones.


	var flgPendienteValidarEjercicio = $("#flgPendienteValidarEjercicio"+anioTipoEjercicio).val();
	var flgModificarServicioCentro = $("#flgModificarServicioCentro"+anioTipoEjercicio).val();
	var flgModificarCalendarioCentro = $("#flgModificarCalendarioCentro"+anioTipoEjercicio).val();
	var flgModificarServicioTecnico = $("#flgModificarServicioTecnico"+anioTipoEjercicio).val();
	var flgModificarCalendarioTecnico = $("#flgModificarCalendarioTecnico"+anioTipoEjercicio).val();
	var tipoEjercicio = $("#tipoEjercicio"+anioTipoEjercicio).val();

	if (tipoEjercicio == "P") { // Si el Tipo de Ejercicio es 'Planificacion'

		if  ((rol == "4") || (flgPendienteValidarEjercicio == "S" && ((flgModificarServicioTecnico == "S" && (rol == "1"))||(flgModificarCalendarioCentro == "S" && rol == "2")))){
			$("#p96_btn_guardadoBloque").css('visibility', 'visible');
		}else{
			$("#p96_btn_guardadoBloque").css('visibility', 'hidden');
		}

		if(flgPendienteValidarEjercicio == "S" && ((flgModificarServicioTecnico == "S" && (rol == "1" || rol == "4"))||(flgModificarCalendarioCentro == "S" && rol == "2"))){
			$("#p96_btn_finalizadoBloque").css('visibility', 'visible');
		}else{
			$("#p96_btn_finalizadoBloque").css('visibility', 'hidden');
		}
	} else {

		if (tipoEjercicio == "E") { // Si el Tipo de Ejercicio es 'Ejecución'
			$("#p96_btn_finalizadoBloque").css('visibility', 'hidden');
			if (rol == "1" || rol == "4" || rol == "2") { // Si el rol del usuario es 1 o 2, se pintara el botón GUARDAR
				$("#p96_btn_guardadoBloque").css('visibility', 'visible')
			} else {
				$("#p96_btn_guardadoBloque").css('visibility', 'hidden');
			}
		}
	}


	//Mostrar u ocultar botones de cambio estacional.
	var flgCambioEstacional = $("#flgCambioEstacional"+anioTipoEjercicio).val();

	//Solo sale en tipo ejercicio P
	//if(flgCambioEstacional == "S" && tipoEjercicio == "P"){
	if(flgCambioEstacional == "S"){
		$("#p96_btn_cambiosEstacionalesBloque").css('visibility', 'visible');
	}else{
		$("#p96_btn_cambiosEstacionalesBloque").css('visibility', 'hidden');
	}


}

//Función que crea las marcas html de los días del calendario, inserta la información
//de los días del calendario en su lugar correspondiente.
function dibujarDiasCalendarioP96(listaDiasTemporal){
	//Crear variable que guardará el día del calendario
	var diaCalendario;

	//Crear variable que guardará la estructura de la devolución
	var estructuraFormDiaCalendario;

	//Conseguimos el día de la semana que cae el primer día del calendario.
	var primerDiaCalendario = listaDiasTemporal[0];

	//Conseguimos qué día de la semana cae ese día 1 para pintar los días de la semana anteriores de blanco.
	var primerDiaMes = primerDiaCalendario.fechaCalendario;

	//Conseguir día de la semana primer día
	var anyo = parseInt(primerDiaMes.substring(0,4),10);
	var mes = parseInt(primerDiaMes.substring(5,7),10);
	var dia = parseInt(primerDiaMes.substring(8),10);

	// L-> 1 , M->2, X->3, J->4, V->5, S->6, D->7
	var fechaDesdeFormateada = $.datepicker.formatDate("D", new Date(anyo, mes - 1, dia),{
		dayNamesShort: [7,1,2,3,4,5,6]
	});

	//Días anteriores no rellenables.
	var diasPreNoRellenablesP96 = fechaDesdeFormateada - 1;

	var diasParaNoMostrarP96 = listaDiasPreviosP96(diasPreNoRellenablesP96);
	listaDiasTemporal = diasParaNoMostrarP96.concat(listaDiasTemporal);


	//Crear un bucle que dibuje las devoluciones en el popup según su estado
	for(var i=0;i<listaDiasTemporal.length;i++){

		//Obtener el día del calendario a dibujar
		diaCalendario = listaDiasTemporal[i];

		//Se obtiene la esctructura 1 y se cambian los ids adecuados al nuevo formulario
		estructuraFormDiaCalendario = getEstructuraDiaCalendarioP96(diaCalendario,i);

		//Se inserta el formulario en su sección correspondiente del popup
		$("#p96_calendario_dias").append(estructuraFormDiaCalendario);

		//Se llama a la función que pinta la información del día.
		rellenarInformacionDiaria(i,diaCalendario);

		//Se llama a la función que rellena la información del día oculta.
		rellenarInformacionDiariaHidden(i,diaCalendario);				
	}
	asignarEventos();	
}

//Se obtiene la esctructura del dia del calendario y se cambian los ids adecuados al nuevo dia.Si es un día vacío, se devuelve la estructura vacía.
function getEstructuraDiaCalendarioP96(diaCalendario, indice){
	var estructuraDiaCalendario;
	if(diaCalendario.mostrarDia == null){
		estructuraDiaCalendario = $("#formDiaEstructura").prop('outerHTML');

		//Para la estructura visible
		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\bformDiaEstructura\\b"),"formDiaEstructura"+indice);
		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\bformDiaEstructuraDiaMes\\b"),"formDiaEstructuraDiaMes"+indice);
		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\bformDiaEstructuraDiaMesLbl\\b"),"formDiaEstructuraDiaMesLbl"+indice);	
		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\bformDiaEstructuraInfo\\b"),"formDiaEstructuraInfo"+indice);
		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSuministro\\b"),"formDiaEstructuraInfoSuministro"+indice);
		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSuministroCamionCerrado\\b"),"formDiaEstructuraInfoSuministroCamionCerrado"+indice);
		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\bformDiaEstructuraInfoSuministroCamionAbierto\\b"),"formDiaEstructuraInfoSuministroCamionAbierto"+indice);
		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\bformDiaEstructuraInfoImgWarning\\b"),"formDiaEstructuraInfoImgWarning"+indice);
		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\bformDiaEstructuraEstado\\b"),"formDiaEstructuraEstado"+indice);
		//estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\bformDiaEstructuraEstadoLbl\\b"),"formDiaEstructuraEstadoLbl"+indice);


		//Para los datos no visibles	
		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\bfechaCalendario\\b"),"fechaCalendario"+indice);
		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\bfestivo\\b"),"festivo"+indice);
		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\bponerDiaVerde\\b"),"ponerDiaVerde"+indice);
		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\bcerrado\\b"),"cerrado"+indice);	
		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\bservicioHabitual\\b"),"servicioHabitual"+indice);
		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\bcambioEstacional\\b"),"cambioEstacional"+indice);
		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\bcambioManual\\b"),"cambioManual"+indice);

		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\becambioPlataforma\\b"),"ecambioPlataforma"+indice);
		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\besePuedeModificarServicio\\b"),"esePuedeModificarServicio"+indice);
		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\beaprobadoCambio\\b"),"eaprobadoCambio"+indice);

		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\bsuministro\\b"),"suministro"+indice);
		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\bflgServiciosBuscados\\b"),"flgServiciosBuscados"+indice);
		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\bflgCambioManualServicioNulo\\b"),"flgCambioManualServicioNulo"+indice);
		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\bdiaPasado\\b"),"diaPasado"+indice);
		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\bverdePlataforma\\b"),"verdePlataforma"+indice);
		estructuraDiaCalendario = estructuraDiaCalendario.replace(new RegExp("\\bpuedeSolicitarServicio\\b"),"puedeSolicitarServicio"+indice);
	}else{
		estructuraDiaCalendario = $("#formDiaEstructuraHidden").prop('outerHTML');
	}

	return estructuraDiaCalendario;
}

//Se obtiene la esctructura de los hiddens del año y se cambian los ids adecuados al año.
function getEstructuraHiddensComboAnioP96(anio, tipoEjercicio){

	estructuraHiddensAnio = $("#formHiddensAnioEstructura").prop('outerHTML');

	//Para los datos no visibles	
	estructuraHiddensAnio = estructuraHiddensAnio.replace(new RegExp("\\bformHiddensAnioEstructura\\b"),"formHiddensAnioEstructura"+anio+tipoEjercicio);
	estructuraHiddensAnio = estructuraHiddensAnio.replace(new RegExp("\\btipoEjercicio\\b"),"tipoEjercicio"+anio+tipoEjercicio);
	estructuraHiddensAnio = estructuraHiddensAnio.replace(new RegExp("\\banioEjercicio\\b"),"anioEjercicio"+anio+tipoEjercicio);
	estructuraHiddensAnio = estructuraHiddensAnio.replace(new RegExp("\\bflgPendienteValidarEjercicio\\b"),"flgPendienteValidarEjercicio"+anio+tipoEjercicio);
	estructuraHiddensAnio = estructuraHiddensAnio.replace(new RegExp("\\bfechaLimiteValidacion\\b"),"fechaLimiteValidacion"+anio+tipoEjercicio);	
	estructuraHiddensAnio = estructuraHiddensAnio.replace(new RegExp("\\bstrFechaLimiteValidacion\\b"),"strFechaLimiteValidacion"+anio+tipoEjercicio);	
	estructuraHiddensAnio = estructuraHiddensAnio.replace(new RegExp("\\bflgEjercicioValidado\\b"),"flgEjercicioValidado"+anio+tipoEjercicio);
	estructuraHiddensAnio = estructuraHiddensAnio.replace(new RegExp("\\bflgCambioEstacional\\b"),"flgCambioEstacional"+anio+tipoEjercicio);
	estructuraHiddensAnio = estructuraHiddensAnio.replace(new RegExp("\\bflgModificarServicioCentro\\b"),"flgModificarServicioCentro"+anio+tipoEjercicio);
	estructuraHiddensAnio = estructuraHiddensAnio.replace(new RegExp("\\bflgModificarCalendarioCentro\\b"),"flgModificarCalendarioCentro"+anio+tipoEjercicio);
	estructuraHiddensAnio = estructuraHiddensAnio.replace(new RegExp("\\bflgModificarServicioTecnico\\b"),"flgModificarServicioTecnico"+anio+tipoEjercicio);
	estructuraHiddensAnio = estructuraHiddensAnio.replace(new RegExp("\\bflgModificarCalendarioTecnico\\b"),"flgModificarCalendarioTecnico"+anio+tipoEjercicio);

	return estructuraHiddensAnio;
}

//Función que rellena los hiddens con la información del ejercicio/año
function rellenarHiddensComboEjercicio(ejercicio){
	//Obtenemos la estructura con los hiddens de cada año del calendario.
	var estructuraFormHiddensCalendario = getEstructuraHiddensComboAnioP96(ejercicio.anioEjercicio, ejercicio.tipoEjercicio);

	//Se inserta el formulario en su sección correspondiente del popup
	$("#p96_anio").append(estructuraFormHiddensCalendario);

	//Se llama a la función que rellena la información del día oculta.
	rellenarInformacionHiddenEjercicio(ejercicio);	
}

//Rellena los hiddens con los datos del anio/ejercicio
function rellenarInformacionHiddenEjercicio(ejercicio){
	$("#tipoEjercicio"+ejercicio.anioEjercicio+ejercicio.tipoEjercicio).val(ejercicio.tipoEjercicio);
	$("#anioEjercicio"+ejercicio.anioEjercicio+ejercicio.tipoEjercicio).val(ejercicio.anioEjercicio);
	$("#flgPendienteValidarEjercicio"+ejercicio.anioEjercicio+ejercicio.tipoEjercicio).val(ejercicio.flgPendienteValidarEjercicio);
	$("#fechaLimiteValidacion"+ejercicio.anioEjercicio+ejercicio.tipoEjercicio).val(ejercicio.fechaLimiteValidacion);
	$("#strFechaLimiteValidacion"+ejercicio.anioEjercicio+ejercicio.tipoEjercicio).val(ejercicio.strFechaLimiteValidacion);
	$("#flgEjercicioValidado"+ejercicio.anioEjercicio+ejercicio.tipoEjercicio).val(ejercicio.flgEjercicioValidado);
	$("#flgCambioEstacional"+ejercicio.anioEjercicio+ejercicio.tipoEjercicio).val(ejercicio.flgCambioEstacional);
	$("#flgModificarServicioCentro"+ejercicio.anioEjercicio+ejercicio.tipoEjercicio).val(ejercicio.flgModificarServicioCentro);
	$("#flgModificarCalendarioCentro"+ejercicio.anioEjercicio+ejercicio.tipoEjercicio).val(ejercicio.flgModificarCalendarioCentro);
	$("#flgModificarServicioTecnico"+ejercicio.anioEjercicio+ejercicio.tipoEjercicio).val(ejercicio.flgModificarServicioTecnico);
	$("#flgModificarCalendarioTecnico"+ejercicio.anioEjercicio+ejercicio.tipoEjercicio).val(ejercicio.flgModificarCalendarioTecnico);
}

//Función que pinta los datos en los días. Si es festivo, si tiene suministro, si está cerrado, etc.
function rellenarInformacionDiaria(indice,diaCalendario){
	if(diaCalendario.mostrarDia == "N"){
		$("#formDiaEstructura"+indice).addClass("visibility","hidden");
	}else{
		//Insertamos el día en su label
		var dia = parseInt(diaCalendario.fechaCalendario.substring(8),10);
		$("#formDiaEstructuraDiaMesLbl"+indice).text(dia);

		//Ponemos la clase correspondiente a cada festivo. 
		if(diaCalendario.festivo == "N"){
			$("#formDiaEstructuraDiaMes"+indice).addClass("p96_festivoNacional")

			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoLocal");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoProvincial");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoAutonomico");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_sinFestivo");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoPlataforma");
			
			$("#formDiaEstructura"+indice).addClass("p96_colorVerdeBorde");
		}else if(diaCalendario.festivo == "L" || diaCalendario.festivo == "Y"){
			$("#formDiaEstructuraDiaMes"+indice).addClass("p96_festivoLocal");

			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoNacional");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoProvincial");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoAutonomico");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_sinFestivo");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoPlataforma");
			
			$("#formDiaEstructura"+indice).addClass("p96_colorVerdeBorde");
		}else if(diaCalendario.festivo == "X"){
			$("#formDiaEstructuraDiaMes"+indice).addClass("p96_festivoPlataforma");

			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoLocal");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoNacional");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoProvincial");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoAutonomico");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_sinFestivo");

			$("#formDiaEstructura"+indice).addClass("p96_colorVerdeBorde");
		}
		else if(diaCalendario.festivo == "P"){
			$("#formDiaEstructuraDiaMes"+indice).addClass("p96_festivoProvincial");

			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoLocal");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoNacional");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoAutonomico");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_sinFestivo");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoPlataforma");
			
			$("#formDiaEstructura"+indice).addClass("p96_colorVerdeBorde");
		}else if(diaCalendario.festivo == "A"){
			$("#formDiaEstructuraDiaMes"+indice).addClass("p96_festivoAutonomico");

			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoLocal");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoProvincial");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoNacional");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_sinFestivo");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoPlataforma");
			
			$("#formDiaEstructura"+indice).addClass("p96_colorVerdeBorde");
		}else if(diaCalendario.festivo == "D"){
			$("#formDiaEstructuraDiaMes"+indice).addClass("p96_festivoDomingo");

			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoLocal");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoNacional");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoAutonomico");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_sinFestivo");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoPlataforma");
			
			//$("#formDiaEstructura"+indice).addClass("p96_colorVerdeBorde");
		}else{
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoLocal");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoProvincial");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoAutonomico");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoNacional");
			$("#formDiaEstructuraDiaMes"+indice).removeClass("p96_festivoPlataforma");

			var flgPendienteValidarEjercicio = $("#flgPendienteValidarEjercicio"+comboValueAnioP96).val();

			var flgModificarCalendarioCentro = $("#flgModificarCalendarioCentro"+comboValueAnioP96).val();
			var flgModificarCalendarioTecnico = $("#flgModificarCalendarioTecnico"+comboValueAnioP96).val();

			//Ponemos la clase sin festivo que tiene un cursor si realmente se puede elegir festivo local.
			if(flgPendienteValidarEjercicio == "S" && ((flgModificarCalendarioCentro == "S" && rol == "2")||(flgModificarCalendarioTecnico == "S" && (rol == "1" || rol == "4")))){
				$("#formDiaEstructuraDiaMes"+indice).addClass("p96_sinFestivo");				
			}
		}

		//Añadimos el borde rojo.
		if(diaCalendario.cambioManual == null){
			if(diaCalendario.cambioEstacional == "S" || diaCalendario.cambioEstacional == "N"){
				$("#formDiaEstructura"+indice).addClass("p96_colorRojoBorde");
			}
		}


		//Tipo ejercicio
		var tipoEjercicio = $("#tipoEjercicio"+comboValueAnioP96).val();


		//Quitamos o Añadjmo el icono del warning al dia. Si eaprobadoCambio = N entonces pintmos el icono del warning.
		if(diaCalendario.eaprobadoCambio == "N"){
			$("#formDiaEstructuraInfoImgWarning"+indice).attr("style", "display:inline");
			$("#formDiaEstructuraInfoImgWarning"+indice).addClass("parpadeo");
		} 


		//Para el pintado de cambiones, para que el código sea mas legible, se diferencia por un lado tipo de Ejercicio 'P' (Planidicacion) y por otro tipo de Ejercicio 'E' (Ejecución)
		//Indicamos si el centro está abierto o cerrado. Si está cerrado ponemos una C roja.

		if ((diaCalendario.cerrado == "N") || (diaCalendario.cerrado == "M")){ //Centro Abierto /Cerrado medio dia
			var hayCamion = false;

			//Si el estado de cerrado es "M", abierto medio dia, ponemos la imagen correspondiene
			if (diaCalendario.cerrado == "M") {
				$("#formDiaEstructuraEstado"+indice).addClass("p96_centroMedioDia");
			}

			// Si es tipo de Ejercio P, el orden de los parametros a mirar es : cambioManual, cambioEstacional y servicioHabitual
			if(tipoEjercicio == "P") {
				if(diaCalendario.cambioManual == "S" && diaCalendario.festivo != "D"){
					$("#formDiaEstructuraInfoSuministroCamionCerrado"+indice).addClass("p96_camionSuperior");
					$("#formDiaEstructuraInfoSuministroCamionAbierto"+indice).addClass("p96_camionInferior");
					hayCamion= true;

					//Si el servicio es todos, ponemos el dedo en el camión abierto
					if(comboValueServicioP96 == " "){
						$("#formDiaEstructuraInfoSuministroCamionAbierto"+indice).addClass("p96_pointer");
					}

					//Si además es día verde, ponemos el dedo tanto en TODOS como en cualquier servicio.
					if(diaCalendario.ponerDiaVerde == "S" || diaCalendario.verdePlataforma == "S"){
						$("#formDiaEstructuraInfoSuministroCamionCerrado"+indice).addClass("p96_pointer");
					}
				}else if(diaCalendario.cambioManual == null || diaCalendario.cambioManual == ""){
					if(diaCalendario.cambioEstacional == "S" && diaCalendario.festivo != "D"){
						$("#formDiaEstructuraInfoSuministroCamionCerrado"+indice).addClass("p96_camionSuperior");
						$("#formDiaEstructuraInfoSuministroCamionAbierto"+indice).addClass("p96_camionInferior");
						hayCamion = true;

						//Si el servicio es todos, ponemos el dedo en el camión abierto
						if(comboValueServicioP96 == " "){
							$("#formDiaEstructuraInfoSuministroCamionAbierto"+indice).addClass("p96_pointer");
						}

						//Si además es día verde, ponemos el dedo tanto en TODOS como en cualquier servicio.
						if(diaCalendario.ponerDiaVerde == "S" || diaCalendario.verdePlataforma == "S"){
							$("#formDiaEstructuraInfoSuministroCamionCerrado"+indice).addClass("p96_pointer");
						}
					}else if(diaCalendario.cambioEstacional == null || diaCalendario.cambioEstacional == ""){
						if(diaCalendario.servicioHabitual == "S" && diaCalendario.festivo != "D"){
							$("#formDiaEstructuraInfoSuministroCamionCerrado"+indice).addClass("p96_camionSuperior");
							$("#formDiaEstructuraInfoSuministroCamionAbierto"+indice).addClass("p96_camionInferior");
							hayCamion = true;

							//Si el servicio es todos, ponemos el dedo en el camión abierto
							if(comboValueServicioP96 == " "){
								$("#formDiaEstructuraInfoSuministroCamionAbierto"+indice).addClass("p96_pointer");
							}

							//Si además es día verde, ponemos el dedo tanto en TODOS como en cualquier servicio.
							if(diaCalendario.ponerDiaVerde == "S" || diaCalendario.verdePlataforma == "S"){
								$("#formDiaEstructuraInfoSuministroCamionCerrado"+indice).addClass("p96_pointer");
							}
						}
					}
				}	

			} else {


				// Si es tipo de Ejercio E, el orden de los parametros a mirar es : ecambioPlataforma, cambioManual, cambioEstacional y servicioHabitual

				if(tipoEjercicio == "E") {
					/*if(diaCalendario.ecambioPlataforma == "S" && diaCalendario.festivo != "D"){
						$("#formDiaEstructuraInfoSuministroCamionCerrado"+indice).addClass("p96_camionSuperior");
						$("#formDiaEstructuraInfoSuministroCamionAbierto"+indice).addClass("p96_camionInferior");
						hayCamion= true;

						//Si el servicio es todos, ponemos el dedo en el camión abierto
						if(comboValueServicioP96 == " "){
							$("#formDiaEstructuraInfoSuministroCamionAbierto"+indice).addClass("p96_pointer");
						}

						//Si además es día verde, ponemos el dedo tanto en TODOS como en cualquier servicio.
						if((diaCalendario.ponerDiaVerde == "S" || diaCalendario.verdePlataforma == "S") && diaCalendario.esePuedeModificarServicio == "S") { //Si es Ejecución, tambien miramo ademas de dia verde que el parametro esePuedeModificarServicio sea S
							$("#formDiaEstructuraInfoSuministroCamionCerrado"+indice).addClass("p96_pointer");
						}
					} else if(diaCalendario.ecambioPlataforma == null || diaCalendario.ecambioPlataforma == ""){*/

					if(diaCalendario.cambioManual == "S" && diaCalendario.festivo != "D"){
						$("#formDiaEstructuraInfoSuministroCamionCerrado"+indice).addClass("p96_camionSuperior");
						$("#formDiaEstructuraInfoSuministroCamionAbierto"+indice).addClass("p96_camionInferior");
						hayCamion= true;

						//Si el servicio es todos, ponemos el dedo en el camión abierto
						if(comboValueServicioP96 == " "){
							$("#formDiaEstructuraInfoSuministroCamionAbierto"+indice).addClass("p96_pointer");
						}

						//Si además es día verde, ponemos el dedo tanto en TODOS como en cualquier servicio.
						if((diaCalendario.ponerDiaVerde == "S" || diaCalendario.verdePlataforma == "S") && diaCalendario.esePuedeModificarServicio == "S") { //Si es Ejecución, tambien miramo ademas de dia verde que el parametro esePuedeModificarServicio sea S
							$("#formDiaEstructuraInfoSuministroCamionCerrado"+indice).addClass("p96_pointer");
						}
					}else if(diaCalendario.cambioManual == null || diaCalendario.cambioManual == ""){
						if(diaCalendario.cambioEstacional == "S" && diaCalendario.festivo != "D"){
							$("#formDiaEstructuraInfoSuministroCamionCerrado"+indice).addClass("p96_camionSuperior");
							$("#formDiaEstructuraInfoSuministroCamionAbierto"+indice).addClass("p96_camionInferior");
							hayCamion = true;

							//Si el servicio es todos, ponemos el dedo en el camión abierto
							if(comboValueServicioP96 == " "){
								$("#formDiaEstructuraInfoSuministroCamionAbierto"+indice).addClass("p96_pointer");
							}

							//Si además es día verde, ponemos el dedo tanto en TODOS como en cualquier servicio.
							if((diaCalendario.ponerDiaVerde == "S" || diaCalendario.verdePlataforma == "S") && diaCalendario.esePuedeModificarServicio == "S") { //Si es Ejecución, tambien miramo ademas de dia verde que el parametro esePuedeModificarServicio sea S
								$("#formDiaEstructuraInfoSuministroCamionCerrado"+indice).addClass("p96_pointer");
							}
						}else if(diaCalendario.cambioEstacional == null || diaCalendario.cambioEstacional == ""){
							if(diaCalendario.servicioHabitual == "S" && diaCalendario.festivo != "D"){
								$("#formDiaEstructuraInfoSuministroCamionCerrado"+indice).addClass("p96_camionSuperior");
								$("#formDiaEstructuraInfoSuministroCamionAbierto"+indice).addClass("p96_camionInferior");
								hayCamion = true;

								//Si el servicio es todos, ponemos el dedo en el camión abierto
								if(comboValueServicioP96 == " "){
									$("#formDiaEstructuraInfoSuministroCamionAbierto"+indice).addClass("p96_pointer");
								}

								//Si además es día verde, ponemos el dedo tanto en TODOS como en cualquier servicio.
								if((diaCalendario.ponerDiaVerde == "S" || diaCalendario.verdePlataforma == "S") && diaCalendario.esePuedeModificarServicio == "S") { //Si es Ejecución, tambien miramo ademas de dia verde que el parametro esePuedeModificarServicio sea S
									$("#formDiaEstructuraInfoSuministroCamionCerrado"+indice).addClass("p96_pointer");
								}
							}
						}
					}	
					//}

				}


			}	


			//Ponemos los días verdes y los tooltip de los camiones.
			if((diaCalendario.ponerDiaVerde == "S" || diaCalendario.verdePlataforma == "S") && diaCalendario.festivo != "D"){
				//Para saber si poner el día verde, además, se tiene en cuenta también estos campos.
				var flgModificarServicioCentro = $("#flgModificarServicioCentro"+comboValueAnioP96).val();
				var flgModificarServicioTecnico = $("#flgModificarServicioTecnico"+comboValueAnioP96).val();


				//En el caso de que no cumpla la condición, es necesario decir que dia verde es N. Esto sucede, porque puede
				//que un centro sea solo de consulta y tenga el dia verde en S. Entonces aparecería la opción de modificar los
				//servicios. Como no queremos que se puedan modificar, ponemos el flag en N y así aparece el día en blanco y no
				//modificable con el botón guardar quitado además.
				if((flgModificarServicioCentro == "S" && rol == "2")||(flgModificarServicioTecnico == "S" && (rol == "1")) || rol == "4"){
					if ((tipoEjercicio == "E" && diaCalendario.esePuedeModificarServicio == "S") || (tipoEjercicio == "P") || (rol == "4")) {
						$("#formDiaEstructuraInfoSuministro"+indice).addClass("p96_diaVerde");

						//Si el servicio es todos, ponemos el dedo en el camión abierto
						if(comboValueServicioP96 == " "){
							$("#formDiaEstructuraInfoSuministroCamionAbierto"+indice).addClass("p96_pointer");
						}
						//Ponemos dedo en el camión cerrado.
						$("#formDiaEstructuraInfoSuministroCamionCerrado"+indice).addClass("p96_pointer");		
					}
				}else{
					$("#formDiaEstructuraInfoSuministro"+indice).removeClass("p96_diaVerde");
					diaCalendario.ponerDiaVerde = "N";
				}
				if(hayCamion){
					$('#formDiaEstructuraInfoSuministroCamionCerrado'+indice).prop('title', anQuitarDiasComoServicios );
					$('#formDiaEstructuraInfoSuministroCamionAbierto'+indice).prop('title', anQuitarServicios);
				}
			}else{
				$("#formDiaEstructuraInfoSuministro"+indice).removeClass("p96_diaVerde")				
				if(hayCamion){
					$('#formDiaEstructuraInfoSuministroCamionCerrado'+indice).prop('title', noSePuedeQuitarDiaServicio );
					$('#formDiaEstructuraInfoSuministroCamionAbierto'+indice).prop('title', consultarServicios);
				}

				//Quitamos el puntero del camión de arriba
				$("#formDiaEstructuraInfoSuministro"+indice).removeClass("p96_pointer");
			}

			//Ponemos puntero (dedo si cumple las condiciones) en la opción de abierto y cerrado.
			var flgModificarCalendarioCentro = $("#flgModificarCalendarioCentro"+comboValueAnioP96).val();
			var flgModificarCalendarioTecnico = $("#flgModificarCalendarioTecnico"+comboValueAnioP96).val();

			if((flgModificarCalendarioCentro == "S" && rol == "2")||(flgModificarCalendarioTecnico == "S" && (rol == "1" || rol == "4"))){
				$("#formDiaEstructuraEstado"+indice).addClass("p96_pointer");
			}


		}else{ //diaCalendario.cerrado != "N" && diaCalendario.cerrado != "M"
			$("#formDiaEstructuraEstado"+indice).addClass("p96_centroCerrado");

			//Ponemos puntero (dedo si cumple las condiciones) en la opción de abierto y cerrado.
			var flgModificarCalendarioCentro = $("#flgModificarCalendarioCentro"+comboValueAnioP96).val();
			var flgModificarCalendarioTecnico = $("#flgModificarCalendarioTecnico"+comboValueAnioP96).val();

			if((flgModificarCalendarioCentro == "S" && rol == "2")||(flgModificarCalendarioTecnico == "S" && (rol == "1" || rol == "4"))){
				$("#formDiaEstructuraEstado"+indice).addClass("p96_pointer");
			}
		}
	}
}

//Función que pinta los datos en los hiddens
function rellenarInformacionDiariaHidden(indice,diaCalendario){
	$("#fechaCalendario"+indice).val(diaCalendario.fechaCalendario);
	$("#festivo"+indice).val(diaCalendario.festivo);
	$("#ponerDiaVerde"+indice).val(diaCalendario.ponerDiaVerde);
	$("#cerrado"+indice).val(diaCalendario.cerrado);
	$("#servicioHabitual"+indice).val(diaCalendario.servicioHabitual);
	$("#cambioEstacional"+indice).val(diaCalendario.cambioEstacional);
	$("#cambioManual"+indice).val(diaCalendario.cambioManual);

	$("#ecambioPlataforma"+indice).val(diaCalendario.ecambioPlataforma);
	$("#esePuedeModificarServicio"+indice).val(diaCalendario.esePuedeModificarServicio);
	$("#eaprobadoCambio"+indice).val(diaCalendario.eaprobadoCambio);

	$("#suministro"+indice).val(diaCalendario.suministro);
	$("#flgServiciosBuscados"+indice).val(diaCalendario.flgServiciosBuscados);
	$("#flgCambioManualServicioNulo"+indice).val(diaCalendario.cambioManualServiciosNulos);
	$("#diaPasado"+indice).val(diaCalendario.diaPasado);
	$("#verdePlataforma"+indice).val(diaCalendario.verdePlataforma);
	$("#puedeSolicitarServicio"+indice).val(diaCalendario.puedeSolicitarServicio);	
}

function paginacionFormulariosP96(flechaSig){
	//Si se ha presionado la flecha siguiente
	if(flechaSig == 'S'){
		//Si hay más formularios de los que se ven en la página, paginar
		if($("#p96_pagSiguiente").hasClass("p96_pagSig")){
			//Quitamos el día a iluminar por consultar sus servicios porque vamos a refrescar el calendario completamente.
			diaAIluminarCalendario  = "";

			idxActual = idxActual + 1;
			load_diasCalendarioP96(comboValueAnioP96,comboValueServicioP96,null,"S",mesAnioLst[idxActual],null);

			//Actualizar el estado de las flechas
			actualizarNumeroDePaginaP96(idxActual,idxFinLista);
		}
	}else{
		//Si hay más formularios de los que se ven en la página, paginar
		if($("#p96_pagAnterior").hasClass("p96_pagAnt")){
			//Quitamos el día a iluminar por consultar sus servicios porque vamos a refrescar el calendario completamente.
			diaAIluminarCalendario  = "";

			idxActual = idxActual + -1;
			load_diasCalendarioP96(comboValueAnioP96,comboValueServicioP96,null,"S",mesAnioLst[idxActual],null);

			//Actualizar el estado de las flechas
			actualizarNumeroDePaginaP96(idxActual,idxFinLista);
		}
	}
}

//Función que actualiza las imágenes de flechas del popup. Y el número que aparece 
//debajo de los formularios 6/11, 5/5, 12/24, etc.
function actualizarNumeroDePaginaP96(idxInicioLista,idxFinLista){
	//Eliminar la imagen de la flecha actual
	$("#p96_pagAnterior").removeClass("p96_pagAnt");
	$("#p96_pagAnterior").removeClass("p96_pagAnt_Des");

	$("#p96_pagSiguiente").removeClass("p96_pagSig");
	$("#p96_pagSiguiente").removeClass("p96_pagSig_Des");

	//Añadir la flecha correspondiente
	if(idxInicioLista == 0){
		$("#p96_pagAnterior").addClass("p96_pagAnt_Des");
	}else{
		$("#p96_pagAnterior").addClass("p96_pagAnt");
	}

	//Se hace +1 para saber el número de elemento que estamos obteniendo. Por ejemplo si
	//el último índice de los objetos a mostrar de la lista es el 5 equivaldría al elemento 6.
	//En el caso de que el último indice sea el 5 y la lista tenga 6 elementos, quiere decir que
	//la flecha tiene que estar invalidada, pero 5<6!!! Por eso se hace +1, para que el indice
	//equivalga al número de objeto
	if(idxInicioLista < idxFinLista-1){
		$("#p96_pagSiguiente").addClass("p96_pagSig");
	}else{
		$("#p96_pagSiguiente").addClass("p96_pagSig_Des");
	}
}

//Devuelve una lista con los días anteriores especificados a no enseñar.
function listaDiasPreviosP96(days){
	var lista = [];
	for(var i = days; i>=1;i--){
		lista.push(diasPreviosP96());
	}
	return lista;
}

//Crea un objeto que indicará que no se tiene que mostrar el día.
function diasPreviosP96() {
	var diaCalendario = {mostrarDia:"N"}
	return diaCalendario;
}

//Función que sirve para limpiar el calendario.
function limpiarCalendarioP96(){
	$("#p96_calendario_dias").empty();
}

//Función que actualiza la tabla temporal. Se pasa el número del día para saber qué datos hay que actualizar
//y el diaVerde para indicar si ha afectado un día verde al tener un tratamiento especial. Si no se ha actualizado el día verde,
//se actualiza la tabla temporal y ya. Si se toca un día verde o un festivo, es posible que haya que redibujar el calendario entero
//y sus días verdes, por lo que es más sencillo actualizar la temporal, devolver los datos y repintar todo.
function updateDiaCalendarioTemporal(numeroDia,flgDiaVerde,flgCambioManualServicioNulo,tCalendarioDia){
	//Obtenemos el mes y año actuales.
	var mesAnio = mesAnioLst[idxActual];

	//Tipo ejercicio
	var tipoEjercicio = $("#tipoEjercicio"+comboValueAnioP96).val();
	var anioEjercicio = $("#anioEjercicio"+comboValueAnioP96).val();

	//Insertamos el objeto del dia.
	var objJson = $.toJSON(tCalendarioDia);
	$.ajax({
		type : 'POST',
		url : './calendario/updateDiaCalendarioTemporal.do?flgDiaVerde='+flgDiaVerde+'&numeroDiasAdelanteAtrasVerdeP96='+numeroDiasAdelanteAtrasVerdeP96+'&mesAnio='+mesAnio+'&codigoServicio='+comboValueServicioP96+"&codigoEjercicio="+anioEjercicio+"&tipoEjercicio="+tipoEjercicio,
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		async:false,
		success : function(data) {
			//Si se devuelven datos
			if(data != null){
				//No hay error
				if(data.pCodError == "0"){
					flgAlMenosUnElementoGuardado = "S";
					//Se han actualizado los verdes
					if(flgDiaVerde == "S"){
						//Se ha devuelto la tabla temporal con los verdes actualizados, repintamos.
						if(data.listadoFechaTemporal != null){
							//Limpiamos el calendario.
							limpiarCalendarioP96();

							//Repintamos el calendario.
							dibujarDiasCalendarioP96(data.listadoFechaTemporal);
						}
					}
					//Si flgCambioManualServicioNulo es 'S', significa que se ha puesto/quitado el camión y que si consultamos los servicios
					//de ese día, en vez de los servicios que nos devuelve el procedimiento hay que devolver los servicios con el cambio manual
					//en null.
					if(flgCambioManualServicioNulo == "S"){
						$("#flgCambioManualServicioNulo"+numeroDia).val("SQ");
					}

					//Si  se ha cambiado el cambio manual, significa que se ha puesto o quitado un camión, Eso implica que si
					//para ese día no se había cargado todavía t_servicios_dias_calendario, se ha cargado y actualizado para ese día, por lo que hay que poner
					//flgServiciosBuscados en S.  En el caso de no existir camión y ponerlo, no se cargan los servicos temporales y se cargan al pulsar el camión
					//por lo que en ese caso no pone la S.
					if((comboValueServicioP96 != " " && tCalendarioDia.cambioManual != null) || (comboValueServicioP96 == " " && tCalendarioDia.cambioManual == "N")){
						$("#flgServiciosBuscados"+numeroDia).val("S");
					}
				}
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});	
}

//Función que valida los cambios del calendario.
function validarCalendario(){
	//Obtenemos el tipo de ejercicio
	var codigoEjercicio = comboValueAnioP96;
	var tipoEjercicio = $("#tipoEjercicio"+comboValueAnioP96).val();
	var anioEjercicio = $("#anioEjercicio"+comboValueAnioP96).val();

	$.ajax({
		type : 'POST',
		url : './calendario/validarCalendario.do?codigoEjercicio='+anioEjercicio,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {
			//Si devuelve 0, se muestra que se ha validado todo OK, si no muestra el error que devuelve el controlador.
			if(data.codError == 0){
				//Ya hemos validado todo, por lo que cambiamos el flagPendienteValidar a N y ponemos el combo de color negro.
				//Además cambiamos la alerta. Lo demás se mantiene.
				$("#p96_option_"+comboValueAnioP96).removeClass("p96_optionRojo");
				$("#p96_option_"+comboValueAnioP96).addClass("p96_optionNegro");

				//Ponemos en negro el elemento seleccionado
				$("#p96_anio >input").removeClass("p96_optionRojo");
				$("#p96_anio >input").addClass("p96_optionNegro");				

				//Ponemos a N flagPendienteValidar
				$("#flgEjercicioValidado"+comboValueAnioP96).val("S");

				//Mostramos u ocultamos las alertas del calendario.
				showHideAlertaP96(comboValueAnioP96);

				createAlert(calendarioValidadoConExitoP96,"INFO");
			}else{
				createAlert(data.descError,"ERROR");
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});	
}

//Función que se ejecuta al cambiar de servicio en el Combobox. En vez de recargar el calendario tirando de procedimiento,
//borrando la tabla temporal y insertandola de nuevo, obtiene los datos del procedimiento y actualiza las filas existentes con los datos
//nuevos que llegan desde el procedimiento.
/*function actualizarCalendarioTrasCambioDeServicio(){
	//Obtenemos el tipo de ejercicio.
	var tipoEjercicio = $("#tipoEjercicio"+comboValueAnioP96).val();
	var anioEjercicio = $("#anioEjercicio"+comboValueAnioP96).val();

	$.ajax({
		type : 'POST',
		url : './calendario/actualizarCalendarioTrasCambioDeServicio.do?codigoEjercicio='+anioEjercicio+"&codigoServicio="+comboValueServicioP96+"&mesAnio="+mesAnioLst[idxActual]+'&tipoEjercicio='+tipoEjercicio,
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
				}
			}else{
				createAlert(data.pDescError,"ERROR");
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});	
}*/

function load_warningsP96(){
	var options = "";
	$.ajax({
		type : 'POST',
		url : './calendario/popup/warnings/consultarWarnings.do',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		async:false,
		success : function(data) {
			if(data != null && data.codError == 0){		
				if(data.listaWarnings != null && data.listaWarnings.length >0){
					reloadDataP99CalendarioWarnings(data);
					contarNumeroAvisos(data);
					$( "#p96_btn_avisosBloque" ).bind("click",function() {
						$( "#p99_popupCalendarioWarnings" ).dialog( "open" );
					});
				}
			}					
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});	
	showHideWarningsP96();
}

function contarNumeroAvisos(datosWarnings) {
	if(datosWarnings.listaWarnings != null && datosWarnings.listaWarnings.length >0){
		p96NumAvisos = datosWarnings.listaWarnings.length;
	}else{
		p96NumAvisos = 0;
	}
}

function showHideWarningsP96(){
	//Obtenemos el tipo de ejercicio.
	var tipoEjercicio = $("#tipoEjercicio"+comboValueAnioP96).val();
	if(tipoEjercicio == "E" && p96NumAvisos > 0){
		$("#p96_btn_avisosBloque").css('visibility', 'visible');
	}else{
		$("#p96_btn_avisosBloque").css('visibility', 'hidden');
	}			
}

function iniciarParpadeo() {    	
	refreshIntervalId  = setInterval(function() {
		//Puede que antes haya habido una búsqueda que haya llegado a 00:00:00 y se haya quedado en hide()
		//el label que contiene la hora, por eso enseñamos porsiacaso el label.
		$(".parpadeo").show();

		if(countParpadeo % 2 == 0){
			$(".parpadeo").css('visibility', 'visible');
			countParpadeo = 1;
		}else{
			$(".parpadeo").css('visibility', 'hidden');
			countParpadeo = 0;
		}
	}, 1000);
}
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/***********************************************************VALIDACIONES DE P96 **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/


/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************* EVENTOS DE P96 ****************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Asigna eventos a los días del mes.
function asignarEventos(){
	var flgPendienteValidarEjercicio = $("#flgPendienteValidarEjercicio"+comboValueAnioP96).val();

	var flgModificarCalendarioCentro = $("#flgModificarCalendarioCentro"+comboValueAnioP96).val();
	var flgModificarCalendarioTecnico = $("#flgModificarCalendarioTecnico"+comboValueAnioP96).val();

	var flgModificarServicioCentro = $("#flgModificarServicioCentro"+comboValueAnioP96).val();
	var flgModificarServicioTecnico = $("#flgModificarServicioTecnico"+comboValueAnioP96).val();

	var tipoEjercicio = $("#tipoEjercicio"+comboValueAnioP96).val();

	//Evento que pone y quita festivo local.
	if (tipoEjercicio == "P") {
		//if(flgPendienteValidarEjercicio == "S" && ((flgModificarCalendarioCentro == "S" && rol == "2")||(flgModificarCalendarioTecnico == "S" && (rol == "1")) || (rol == "4"))){  //&& AÑADIR PASADO, SI ES EJECUCUION NO MIRO FLG PENDIENTE VALIDAR EJERCICIO
		if  ((rol == "4") || (flgPendienteValidarEjercicio == "S" && ((flgModificarCalendarioCentro == "S" && rol == "2")||(flgModificarCalendarioTecnico == "S" && (rol == "1")))) ){ 
			events_p96_ponerQuitarFestivoLocal();
			events_p96_centroCerrado();
		}
	} else {
		if (tipoEjercicio == "E") { // Si es EJECUCION, no se mira el flag flgPendienteValidarEjercicio
			if ((flgModificarCalendarioCentro == "S" && rol == "2")||(flgModificarCalendarioTecnico == "S" && rol == "1") || (rol == "4")) {  //&& AÑADIR PASADO, SI ES EJECUCUION NO MIRO FLG PENDIENTE VALIDAR EJERCICIO
				events_p96_ponerQuitarFestivoLocal();
				events_p96_centroCerrado();
			}
		}
	}

	//Eventos de los camiones.
	events_p96_camiones();

	//Evento que pone el aura y lo quita de los camiones.
	events_p96_noIluminar();
	events_p96_iluminar();

	//Evento que pone el aura y lo quita de los días.
	events_p96_noIluminarDia();
	events_p96_iluminarDia();
}

//Evento que gestiona si el centro se cierra o no.
function events_p96_centroCerrado(){
	$(".p96_estadoDia").click(function(){

		//Obtenemos el id y mediante el id el número del día con el que trabajamos.
		var id = this.id;
		var numeroDia = id.replace("formDiaEstructuraEstado","");

		var tipoEjercicio = $("#tipoEjercicio"+comboValueAnioP96).val();
		var esePuedeModificarServicio =  $("#esePuedeModificarServicio"+numeroDia).val();

		//MISUMI-158 Control ejecutar evento
		//Si entra aquí siendo tipo P, sgnifica que ya tiene los permisos necesarios para ejecutar el evento (al asignar el evento se han calculado roles, si es centro o técnico...), si es de tipo E,
		//también se han mirado los permisos a la hora de asignar el evento (roles, si es de tipo centro o técnico...), pero hay un campo llamado esePuedeModificarServicio que depende del día y no del calendario
		//en general, por lo que ese control se tiene que hacer dentro del evento. Además coincide en que el rol tiene que ser 2 para tenerlo en cuenta. Los roles ya se han tenido en cuenta a la hora de asignar
		//el evento y la información de este if es redundante (si entra en el evento, es porque el rol es de tipo 1 o 2), pero como en uno de los casos esePuedeModificarServicio tiene que ser S además del rol 2
		//para ejecutar el código, tenemos que repetir el código. Se podría haber puesto aquí el control de roles, técnico o centro y demás, pero se ha puesto en asignarEventos() para intentar ahorrar asignaciones
		//de eventos innecesarias y ahorrar tiempo.
		var ejecutarEvento = ((tipoEjercicio == 'P') || (tipoEjercicio == 'E' && (rol == "4" || ((rol == "1" || rol == "2") && esePuedeModificarServicio == "S")))? true:false);
		if(ejecutarEvento){
			var cerrado = $("#cerrado"+numeroDia).val();
			var diaPasado = $("#diaPasado"+numeroDia).val();

			if (diaPasado == "N" || (rol == "4" && tipoEjercicio == 'E')) { //Si no es un dia pasado, controlamos que eventos activar. Si es un dia pasado no se activa ningun dia

				//Si el centro no está cerrado lo cerramos y actualizamos su valor en el hidden.
				if(cerrado == "N"){ //Estaba Abierto el centro pasa a estar Abierto Medio dia 
					$("#cerrado"+numeroDia).val("M");
					$("#"+this.id).addClass("p96_centroMedioDia");

					var tipoEjercicio = $("#tipoEjercicio"+comboValueAnioP96).val();
					var esePuedeModificarServicio =  $("#esePuedeModificarServicio"+numeroDia).val();

					//Si el día no es domingo al quitar el cerrado y existe ponerDiaVerde, se pinta el verde.
					if($("#festivo"+numeroDia).val() != "D"){
						if($("#ponerDiaVerde"+numeroDia).val() == "S" || $("#verdePlataforma"+numeroDia).val() == "S"){
							if ((tipoEjercicio == "E" && esePuedeModificarServicio == "S") || (tipoEjercicio == "P") || (rol == 4)) {
								$("#formDiaEstructuraInfoSuministro"+numeroDia).addClass("p96_diaVerde");
							}
						}
					}

					//Obtenemos los datos del día modificado y actualizamos. 
					//Actualizamos la tabla temporal. Si el día es verde y se quita, habrá que repintar todo.
					var fechaCalendario = $("#fechaCalendario"+numeroDia).val();
					var cerrado = $("#cerrado"+numeroDia).val();

					var tCalendarioDia = new TCalendarioDia(fechaCalendario,null,null,cerrado,null,null,'',null,null);
					updateDiaCalendarioTemporal(numeroDia,"N",flgCambioManualServicioNulo,tCalendarioDia);


					//Ponemos los servicios buscados a N, porque al cerrar el centro, se han eliminado los servicios para que vaya al
					//procedimiento de nuevo.
					$("#flgServiciosBuscados"+numeroDia).val("");

					var hayCamion = false;

					if(tipoEjercicio == "P") {
						if($("#cambioManual"+numeroDia).val() == "S" && $("#festivo"+numeroDia).val() != "D"){
							$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_camionSuperior");
							$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_camionInferior");
							hayCamion= true;			

							//Si el servicio es todos, ponemos el dedo en el camión abierto
							if(comboValueServicioP96 == " "){
								$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_pointer");
							}

							//Si además es día verde, ponemos el dedo tanto en TODOS como en cualquier servicio.
							if($("#ponerDiaVerde"+numeroDia).val() == "S" || $("#verdePlataforma"+numeroDia).val() == "S"){
								$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_pointer");
							}				
						}else if($("#cambioManual"+numeroDia).val() == null || $("#cambioManual"+numeroDia).val() == ""){
							if($("#cambioEstacional"+numeroDia).val() == "S" && $("#festivo"+numeroDia).val() != "D"){
								$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_camionSuperior");
								$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_camionInferior");
								hayCamion = true;

								//Si el servicio es todos, ponemos el dedo en el camión abierto
								if(comboValueServicioP96 == " "){
									$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_pointer");
								}

								//Si además es día verde, ponemos el dedo tanto en TODOS como en cualquier servicio.
								if($("#ponerDiaVerde"+numeroDia).val() == "S" || $("#verdePlataforma"+numeroDia).val() == "S"){
									$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_pointer");
								}
							}else if($("#cambioEstacional"+numeroDia).val() == null || $("#cambioEstacional"+numeroDia).val() == ""){
								if($("#servicioHabitual"+numeroDia).val() == "S" && $("#festivo"+numeroDia).val() != "D"){
									$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_camionSuperior");
									$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_camionInferior");
									hayCamion = true;

									//Si el servicio es todos, ponemos el dedo en el camión abierto
									if(comboValueServicioP96 == " "){
										$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_pointer");
									}

									//Si además es día verde, ponemos el dedo tanto en TODOS como en cualquier servicio.
									if($("#ponerDiaVerde"+numeroDia).val() == "S" || $("#verdePlataforma"+numeroDia).val() == "S"){
										$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_pointer");
									}
								}
							}
						}
					}else{
						// Si es tipo de Ejercio E, el orden de los parametros a mirar es : ecambioPlataforma, cambioManual, cambioEstacional y servicioHabitual
						if(tipoEjercicio == "E") {
							/*if($("#ecambioPlataforma"+numeroDia).val() == "S" && $("#ecambioPlataforma"+numeroDia).val() != "D"){
								$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_camionSuperior");
								$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_camionInferior");
								hayCamion= true;

								//Si el servicio es todos, ponemos el dedo en el camión abierto
								if(comboValueServicioP96 == " "){
									$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_pointer");
								}

								//Si además es día verde, ponemos el dedo tanto en TODOS como en cualquier servicio.
								if($("#ponerDiaVerde"+numeroDia).val() == "S" &&  $("#esePuedeModificarServicio"+numeroDia).val() == "S") { //Si es Ejecución, tambien miramo ademas de dia verde que el parametro esePuedeModificarServicio sea S
									$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_pointer");
								}
							} else if($("#ecambioPlataforma"+numeroDia).val() == null || $("#ecambioPlataforma"+numeroDia).val() == ""){*/
							if($("#cambioManual"+numeroDia).val() == "S" && $("#festivo"+numeroDia).val() != "D"){
								$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_camionSuperior");
								$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_camionInferior");
								hayCamion= true;			

								//Si el servicio es todos, ponemos el dedo en el camión abierto
								if(comboValueServicioP96 == " "){
									$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_pointer");
								}

								//Si además es día verde, ponemos el dedo tanto en TODOS como en cualquier servicio.
								if($("#ponerDiaVerde"+numeroDia).val() == "S" || $("#verdePlataforma"+numeroDia).val() == "S"){
									$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_pointer");
								}				
							}else if($("#cambioManual"+numeroDia).val() == null || $("#cambioManual"+numeroDia).val() == ""){
								if($("#cambioEstacional"+numeroDia).val() == "S" && $("#festivo"+numeroDia).val() != "D"){
									$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_camionSuperior");
									$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_camionInferior");
									hayCamion = true;

									//Si el servicio es todos, ponemos el dedo en el camión abierto
									if(comboValueServicioP96 == " "){
										$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_pointer");
									}

									//Si además es día verde, ponemos el dedo tanto en TODOS como en cualquier servicio.
									if($("#ponerDiaVerde"+numeroDia).val() == "S" || $("#verdePlataforma"+numeroDia).val() == "S"){
										$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_pointer");
									}
								}else if($("#cambioEstacional"+numeroDia).val() == null || $("#cambioEstacional"+numeroDia).val() == ""){
									if($("#servicioHabitual"+numeroDia).val() == "S" && $("#festivo"+numeroDia).val() != "D"){
										$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_camionSuperior");
										$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_camionInferior");
										hayCamion = true;

										//Si el servicio es todos, ponemos el dedo en el camión abierto
										if(comboValueServicioP96 == " "){
											$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_pointer");
										}

										//Si además es día verde, ponemos el dedo tanto en TODOS como en cualquier servicio.
										if($("#ponerDiaVerde"+numeroDia).val() == "S" || $("#verdePlataforma"+numeroDia).val() == "S"){
											$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_pointer");
										}
									}
								}
							}
							//}
						}
					}
					//Ponemos los días verdes y los tooltip de los camiones.
					if(($("#ponerDiaVerde"+numeroDia).val() == "S" || $("#verdePlataforma"+numeroDia).val() == "S") && $("#festivo"+numeroDia).val() != "D"){

						if ((tipoEjercicio == "E" && $("#esePuedeModificarServicio"+numeroDia).val() == "S") || (tipoEjercicio == "P")) {
							$("#formDiaEstructuraInfoSuministro"+numeroDia).addClass("p96_diaVerde");

							if(hayCamion){
								$('#formDiaEstructuraInfoSuministroCamionCerrado'+numeroDia).prop('title', anQuitarDiasComoServicios );
								$('#formDiaEstructuraInfoSuministroCamionAbierto'+numeroDia).prop('title', anQuitarServicios);
							}
						} 
					}else{
						$("#formDiaEstructuraInfoSuministro"+numeroDia).removeClass("p96_diaVerde")				
						if(hayCamion){
							$('#formDiaEstructuraInfoSuministroCamionCerrado'+numeroDia).prop('title', noSePuedeQuitarDiaServicio );
							$('#formDiaEstructuraInfoSuministroCamionAbierto'+numeroDia).prop('title', consultarServicios);
						}
					}


				}else{//A
					if(cerrado == "M") { // Si el centro esta Medio Abierto el centro pasa a Cerrado


						$("#cerrado"+numeroDia).val("S");
						$("#"+this.id).removeClass("p96_centroMedioDia");	
						$("#"+this.id).addClass("p96_centroCerrado");

						//Quitamos los toolbox con el texto de los camiones.
						$('#formDiaEstructuraInfoSuministroCamionCerrado'+numeroDia).prop('title', "");
						$('#formDiaEstructuraInfoSuministroCamionAbierto'+numeroDia).prop('title', "");

						//Quitamos los eventos de iluminar del elemento.
						$('#formDiaEstructuraInfoSuministroCamionAbierto'+numeroDia).unbind("mouseover");
						$('#formDiaEstructuraInfoSuministroCamionAbierto'+numeroDia).unbind("mouseout");

						//Quitamos los eventos de iluminar del elemento.
						$('#formDiaEstructuraInfoSuministroCamionCerrado'+numeroDia).unbind("mouseover");
						$('#formDiaEstructuraInfoSuministroCamionCerrado'+numeroDia).unbind("mouseout");

						//Quitamos los camiones
						$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).removeClass("p96_camionSuperior");
						$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).removeClass("p96_camionInferior");

						//Quitamos los dedos del camión por que se ha cerrado el centro.
						$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).removeClass("p96_pointer");
						$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).removeClass("p96_pointer");

						if($("#ponerDiaVerde"+numeroDia).val() == "S" || $("#verdePlataforma"+numeroDia).val() == "S"){
							$("#formDiaEstructuraInfoSuministro"+numeroDia).removeClass("p96_diaVerde");
						}

						//Obtenemos los datos del día modificado y actualizamos. 
						//Actualizamos la tabla temporal. Si el día es verde y se quita, habrá que repintar todo.
						var fechaCalendario = $("#fechaCalendario"+numeroDia).val();
						var cerrado = $("#cerrado"+numeroDia).val();
						var tCalendarioDia = new TCalendarioDia(fechaCalendario,null,null,cerrado,null,null,'',null,null);
						updateDiaCalendarioTemporal(numeroDia,"N","N",tCalendarioDia);


					} else { //El centro esta Cerrado (S) pasa a estar Abierto(N) //B


						//Si el centro está cerrado, se quita la imagen de cerrado y actualizamos su valor en el hidden.
						$("#cerrado"+numeroDia).val("N");
						$("#"+this.id).removeClass("p96_centroCerrado");	

						var tipoEjercicio = $("#tipoEjercicio"+comboValueAnioP96).val();
						var esePuedeModificarServicio =  $("#esePuedeModificarServicio"+numeroDia).val();

						//Si el día no es domingo al quitar el cerrado y existe ponerDiaVerde, se pinta el verde.
						if($("#festivo"+numeroDia).val() != "D"){
							if($("#ponerDiaVerde"+numeroDia).val() == "S" || $("#verdePlataforma"+numeroDia).val() == "S"){
								if ((tipoEjercicio == "E" && esePuedeModificarServicio == "S") || (tipoEjercicio == "P") || (rol == 4)) {
									$("#formDiaEstructuraInfoSuministro"+numeroDia).addClass("p96_diaVerde");
								}
							}
						}

						//Obtenemos los datos del día modificado y actualizamos. 
						//Actualizamos la tabla temporal. Si el día es verde y se quita, habrá que repintar todo.
						var fechaCalendario = $("#fechaCalendario"+numeroDia).val();
						var cerrado = $("#cerrado"+numeroDia).val();

						//Si es un dia verde, es festivo qur no sea domingo y quito el cerrado, en caso de haber, quiero quitar el camión. Si no es dia verde 
						//cuando quito el cerrado, no quiero quitar el camión
						var cambioManualEnvio = null;
						var flgServiciosBuscados = null;
						var diaVerde = $("#ponerDiaVerde"+numeroDia).val();
						var flgCambioManualServicioNulo = "N";
						var tipoFiesta = $("#festivo"+numeroDia).val() != "" ? $("#festivo"+numeroDia).val():null;
						var eaprobadoCambio = null;
						if(diaVerde == "S" && (tipoFiesta != "D" && tipoFiesta != null)){
							//Quitamos el camión si no tiene ecambioPlataforma. Si lo tiene, se deja el flag.
							if($("#ecambioPlataforma"+numeroDia).val() == ""){
								$("#cambioManual"+numeroDia).val("N");
							}

							//Pasamos que el cambio manual es N al controlador, aunque solo se actualizarán los que no tengan ecambioPlataforma con N
							cambioManualEnvio = "N";

							flgServiciosBuscados = $("#flgServiciosBuscados"+numeroDia).val();
							flgCambioManualServicioNulo = "S";

							var fecha = $("#fechaCalendario"+numeroDia).val();
							var fechaSplit = fecha.split("-");	
							var fechaFormat = fechaSplit[2] + "/" + fechaSplit[1] + "/" + fechaSplit[0]; 

							obtenerFilaYBorrar(fechaFormat);

							//Ponemos eaprobadocambio en N para que no salga la alerta.
							$("#eaprobadoCambio"+numeroDia).val("N");

							//Quitamos la alerta parpadeante
							$("#formDiaEstructuraInfoImgWarning"+numeroDia).attr("style", "display:none");
							$("#formDiaEstructuraInfoImgWarning"+numeroDia).removeClass("parpadeo");
						}

						//var tCalendarioDia = new TCalendarioDia(fechaCalendario,null,null,cerrado,null,null,cambioManual,null,flgServiciosBuscados);
						var tCalendarioDia = new TCalendarioDia(fechaCalendario,null,null,cerrado,null,null,cambioManualEnvio,null,null);
						updateDiaCalendarioTemporal(numeroDia,"N",flgCambioManualServicioNulo,tCalendarioDia);

						var hayCamion = false;

						if(tipoEjercicio == "P") {
							if($("#cambioManual"+numeroDia).val() == "S" && $("#festivo"+numeroDia).val() != "D"){
								$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_camionSuperior");
								$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_camionInferior");
								hayCamion= true;			

								//Si el servicio es todos, ponemos el dedo en el camión abierto
								if(comboValueServicioP96 == " "){
									$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_pointer");
								}

								//Si además es día verde, ponemos el dedo tanto en TODOS como en cualquier servicio.
								if($("#ponerDiaVerde"+numeroDia).val() == "S" || $("#verdePlataforma"+numeroDia).val() == "S"){
									$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_pointer");
								}				
							}else if($("#cambioManual"+numeroDia).val() == null || $("#cambioManual"+numeroDia).val() == ""){
								if($("#cambioEstacional"+numeroDia).val() == "S" && $("#festivo"+numeroDia).val() != "D"){
									$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_camionSuperior");
									$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_camionInferior");
									hayCamion = true;

									//Si el servicio es todos, ponemos el dedo en el camión abierto
									if(comboValueServicioP96 == " "){
										$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_pointer");
									}

									//Si además es día verde, ponemos el dedo tanto en TODOS como en cualquier servicio.
									if($("#ponerDiaVerde"+numeroDia).val() == "S" || $("#verdePlataforma"+numeroDia).val() == "S"){
										$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_pointer");
									}
								}else if($("#cambioEstacional"+numeroDia).val() == null || $("#cambioEstacional"+numeroDia).val() == ""){
									if($("#servicioHabitual"+numeroDia).val() == "S" && $("#festivo"+numeroDia).val() != "D"){
										$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_camionSuperior");
										$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_camionInferior");
										hayCamion = true;

										//Si el servicio es todos, ponemos el dedo en el camión abierto
										if(comboValueServicioP96 == " "){
											$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_pointer");
										}

										//Si además es día verde, ponemos el dedo tanto en TODOS como en cualquier servicio.
										if($("#ponerDiaVerde"+numeroDia).val() == "S" || $("#verdePlataforma"+numeroDia).val() == "S"){
											$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_pointer");
										}
									}
								}
							}
						}else{
							// Si es tipo de Ejercio E, el orden de los parametros a mirar es : ecambioPlataforma, cambioManual, cambioEstacional y servicioHabitual
							if(tipoEjercicio == "E") {
								/*if($("#ecambioPlataforma"+numeroDia).val() == "S" && $("#ecambioPlataforma"+numeroDia).val() != "D"){
									$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_camionSuperior");
									$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_camionInferior");
									hayCamion= true;

									//Si el servicio es todos, ponemos el dedo en el camión abierto
									if(comboValueServicioP96 == " "){
										$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_pointer");
									}

									//Si además es día verde, ponemos el dedo tanto en TODOS como en cualquier servicio.
									if($("#ponerDiaVerde"+numeroDia).val() == "S" &&  $("#esePuedeModificarServicio"+numeroDia).val() == "S") { //Si es Ejecución, tambien miramo ademas de dia verde que el parametro esePuedeModificarServicio sea S
										$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_pointer");
									}
								} else if($("#ecambioPlataforma"+numeroDia).val() == null || $("#ecambioPlataforma"+numeroDia).val() == ""){*/
								if($("#cambioManual"+numeroDia).val() == "S" && $("#festivo"+numeroDia).val() != "D"){
									$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_camionSuperior");
									$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_camionInferior");
									hayCamion= true;			

									//Si el servicio es todos, ponemos el dedo en el camión abierto
									if(comboValueServicioP96 == " "){
										$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_pointer");
									}

									//Si además es día verde, ponemos el dedo tanto en TODOS como en cualquier servicio.
									if($("#ponerDiaVerde"+numeroDia).val() == "S" || $("#verdePlataforma"+numeroDia).val() == "S"){
										$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_pointer");
									}				
								}else if($("#cambioManual"+numeroDia).val() == null || $("#cambioManual"+numeroDia).val() == ""){
									if($("#cambioEstacional"+numeroDia).val() == "S" && $("#festivo"+numeroDia).val() != "D"){
										$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_camionSuperior");
										$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_camionInferior");
										hayCamion = true;

										//Si el servicio es todos, ponemos el dedo en el camión abierto
										if(comboValueServicioP96 == " "){
											$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_pointer");
										}

										//Si además es día verde, ponemos el dedo tanto en TODOS como en cualquier servicio.
										if($("#ponerDiaVerde"+numeroDia).val() == "S" || $("#verdePlataforma"+numeroDia).val() == "S"){
											$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_pointer");
										}
									}else if($("#cambioEstacional"+numeroDia).val() == null || $("#cambioEstacional"+numeroDia).val() == ""){
										if($("#servicioHabitual"+numeroDia).val() == "S" && $("#festivo"+numeroDia).val() != "D"){
											$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_camionSuperior");
											$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_camionInferior");
											hayCamion = true;

											//Si el servicio es todos, ponemos el dedo en el camión abierto
											if(comboValueServicioP96 == " "){
												$("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).addClass("p96_pointer");
											}

											//Si además es día verde, ponemos el dedo tanto en TODOS como en cualquier servicio.
											if($("#ponerDiaVerde"+numeroDia).val() == "S" || $("#verdePlataforma"+numeroDia).val() == "S"){
												$("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).addClass("p96_pointer");
											}
										}
									}
								}
								//}
							}
						}
						//Ponemos los días verdes y los tooltip de los camiones.
						if(($("#ponerDiaVerde"+numeroDia).val() == "S" || $("#verdePlataforma"+numeroDia).val() == "S") && $("#festivo"+numeroDia).val() != "D"){

							if ((tipoEjercicio == "E" && $("#esePuedeModificarServicio"+numeroDia).val() == "S") || (tipoEjercicio == "P")) {
								$("#formDiaEstructuraInfoSuministro"+numeroDia).addClass("p96_diaVerde");

								if(hayCamion){
									$('#formDiaEstructuraInfoSuministroCamionCerrado'+numeroDia).prop('title', anQuitarDiasComoServicios );
									$('#formDiaEstructuraInfoSuministroCamionAbierto'+numeroDia).prop('title', anQuitarServicios);
								}
							} 
						}else{
							$("#formDiaEstructuraInfoSuministro"+numeroDia).removeClass("p96_diaVerde")				
							if(hayCamion){
								$('#formDiaEstructuraInfoSuministroCamionCerrado'+numeroDia).prop('title', noSePuedeQuitarDiaServicio );
								$('#formDiaEstructuraInfoSuministroCamionAbierto'+numeroDia).prop('title', consultarServicios);
							}
						}


					} //B

				} //A


			}// Si es un dia Pasado deshabilitamos todos los eventoe	

		} //Fin Ejecutar Evento
	}); 	
}

//Función que quita y pone festivos locales. Sólo se podrán poner en días sin festivos.
function events_p96_ponerQuitarFestivoLocal(){
	$(".p96_numeroDia").click(function(){		
		//Obtenemos el id y mediante el id el número del día con el que trabajamos.
		var id = this.id;
		var numeroDia = id.replace("formDiaEstructuraDiaMes","");

		var tipoEjercicio = $("#tipoEjercicio"+comboValueAnioP96).val();
		var esePuedeModificarServicio =  $("#esePuedeModificarServicio"+numeroDia).val();

		//MISUMI-158 Control ejecutar evento
		//Si entra aquí siendo tipo P, sgnifica que ya tiene los permisos necesarios para ejecutar el evento (al asignar el evento se han calculado roles, si es centro o técnico...), si es de tipo E,
		//también se han mirado los permisos a la hora de asignar el evento (roles, si es de tipo centro o técnico...), pero hay un campo llamado esePuedeModificarServicio que depende del día y no del calendario
		//en general, por lo que ese control se tiene que hacer dentro del evento. Además coincide en que el rol tiene que ser 2 para tenerlo en cuenta. Los roles ya se han tenido en cuenta a la hora de asignar
		//el evento y la información de este if es redundante (si entra en el evento, es porque el rol es de tipo 1 o 2), pero como en uno de los casos esePuedeModificarServicio tiene que ser S además del rol 2
		//para ejecutar el código, tenemos que repetir el código. Se podría haber puesto aquí el control de roles, técnico o centro y demás, pero se ha puesto en asignarEventos() para intentar ahorrar asignaciones
		//de eventos innecesarias y ahorrar tiempo.
		var ejecutarEvento = ((tipoEjercicio == 'P') || (tipoEjercicio == 'E' && (rol == "4" || ((rol == "1" || rol == "2") && esePuedeModificarServicio == "S")))? true:false);
		if(ejecutarEvento){
			//Miramos qué tipo de fiesta es.
			var tipoFiesta = $("#festivo"+numeroDia).val();

			//Miramos si no es un día pasado
			var diaPasado  = $("#diaPasado"+numeroDia).val();

			if (diaPasado == "N") { //Si no es un dia pasado activamos los eventos de festivos

				//Miramos la cantidad de festivos locales alimentados.
				var festivosLocalesAlimentados = hashContadorFestivos[$("#p96_cmb_anio").val()];

				//Si es festivo local o no tiene festivo, actuamos como siempre.
				if(tipoFiesta == "L" || tipoFiesta == ""){
					//Si es un festivo local o un dia sin festivo
					if(tipoFiesta == "L"){
						//Si esa cantidad de festivos locales alimentados es mayor que el mínimo de festivos mínimo, se puede
						//quitar el festivo local.
						if(festivosLocalesAlimentados > minFestivoLocal){
							//Obtenemos los datos del día modificado y actualizamos. 
							//Actualizamos la tabla temporal. Si el día es verde y se quita, habrá que repintar todo.
							//Si el centro está cerrado, se quita la imagen de cerrado y actualizamos su valor en el hidden.
							$("#cerrado"+numeroDia).val("N");

							//Ponemos el día blanco.
							$("#festivo"+numeroDia).val("");

							var fechaCalendario = $("#fechaCalendario"+numeroDia).val();
							var festivo = $("#festivo"+numeroDia).val();
							var cerrado = $("#cerrado"+numeroDia).val();
							var tCalendarioDia = new TCalendarioDia(fechaCalendario,festivo,null,cerrado,null,null,null,null);
							updateDiaCalendarioTemporal(numeroDia,"S","N",tCalendarioDia);

							//Obtenemos los festivos locales alimentados.
							var festivosLocalesAlimentados = hashContadorFestivos[$("#p96_cmb_anio").val()];

							//Les quitamos uno porque hemos quitado un festivo
							festivosLocalesAlimentados = festivosLocalesAlimentados - 1;

							//Actualizamos el hash y el label de festivos.
							hashContadorFestivos[$("#p96_cmb_anio").val()] = festivosLocalesAlimentados;
							$("#p96_lbl_diasRestantes").text(festivosLocalesAlimentados);
						}
					}else if(tipoFiesta == ""){
						//Si los festivos locales alimentados son menores que el máximo permitido, dejamos poner festivo.
						if(festivosLocalesAlimentados < maxFestivoLocal){
							//Actualizamos la tabla temporal. Como los festivos influyen en los verdes, se pasa el flgDiaVerde en S.
							//Ponemos el festivo como local
							$("#festivo"+numeroDia).val("L");

							//Cerramos el centro y actualizamos su valor en el hidden. Petición 06/08/19
							$("#cerrado"+numeroDia).val("S");

							var fechaCalendario = $("#fechaCalendario"+numeroDia).val();
							var cerrado = $("#cerrado"+numeroDia).val();
							var festivo = $("#festivo"+numeroDia).val();
							var tCalendarioDia = new TCalendarioDia(fechaCalendario,festivo,null,cerrado,null,null,null,null);	

							updateDiaCalendarioTemporal(numeroDia,"S","N",tCalendarioDia);


							//Obtenemos los festivos restantes
							var festivosLocalesAlimentados = hashContadorFestivos[$("#p96_cmb_anio").val()];

							//Les restamos uno porque hemos puesto un festivo
							festivosLocalesAlimentados = festivosLocalesAlimentados + 1;

							//Actualizamos el hash y el label de festivos.
							hashContadorFestivos[$("#p96_cmb_anio").val()] = festivosLocalesAlimentados;
							$("#p96_lbl_diasRestantes").text(festivosLocalesAlimentados);
						}else{
							createAlert(maxFestivosLocalesAlcanzado,"ERROR");
						}
					}
				}else if(tipoFiesta == "X" || tipoFiesta == "Y"){
					//Si es festivo plataforma, pasamos a festivo local
					if(tipoFiesta == "X"){
						//Si los festivos locales alimentados son menores que el máximo permitido, dejamos poner festivo.
						if(festivosLocalesAlimentados < maxFestivoLocal){
							//Actualizamos la tabla temporal. Como los festivos influyen en los verdes, se pasa el flgDiaVerde en S.
							//Ponemos el festivo como local Y (Que es plataforma y de centro)
							$("#festivo"+numeroDia).val("Y");

							//Cerramos el centro y actualizamos su valor en el hidden. Petición 06/08/19
							$("#cerrado"+numeroDia).val("S");

							var fechaCalendario = $("#fechaCalendario"+numeroDia).val();
							var cerrado = $("#cerrado"+numeroDia).val();
							var festivo = $("#festivo"+numeroDia).val();
							var tCalendarioDia = new TCalendarioDia(fechaCalendario,festivo,null,cerrado,null,null,null,null);	

							updateDiaCalendarioTemporal(numeroDia,"S","N",tCalendarioDia);

							//Obtenemos los festivos restantes
							var festivosLocalesAlimentados = hashContadorFestivos[$("#p96_cmb_anio").val()];

							//Les restamos uno porque hemos puesto un festivo
							festivosLocalesAlimentados = festivosLocalesAlimentados + 1;

							//Actualizamos el hash y el label de festivos.
							hashContadorFestivos[$("#p96_cmb_anio").val()] = festivosLocalesAlimentados;
							$("#p96_lbl_diasRestantes").text(festivosLocalesAlimentados);
						}else{
							createAlert(maxFestivosLocalesAlcanzado,"ERROR");
						}	
					}else if(tipoFiesta == "Y"){
						//Si esa cantidad de festivos locales alimentados es mayor que el mínimo de festivos mínimo, se puede
						//quitar el festivo local.
						if(festivosLocalesAlimentados > minFestivoLocal){
							//Obtenemos los datos del día modificado y actualizamos. 
							//Actualizamos la tabla temporal. Si el día es verde y se quita, habrá que repintar todo.
							//Si el centro está cerrado, se quita la imagen de cerrado y actualizamos su valor en el hidden.
							$("#cerrado"+numeroDia).val("N");

							//Ponemos el día en azul
							$("#festivo"+numeroDia).val("X");

							var fechaCalendario = $("#fechaCalendario"+numeroDia).val();
							var festivo = $("#festivo"+numeroDia).val();
							var cerrado = $("#cerrado"+numeroDia).val();
							var tCalendarioDia = new TCalendarioDia(fechaCalendario,festivo,null,cerrado,null,null,null,null);
							updateDiaCalendarioTemporal(numeroDia,"S","N",tCalendarioDia);

							//Obtenemos los festivos locales alimentados.
							var festivosLocalesAlimentados = hashContadorFestivos[$("#p96_cmb_anio").val()];

							//Les quitamos uno porque hemos quitado un festivo
							festivosLocalesAlimentados = festivosLocalesAlimentados - 1;

							//Actualizamos el hash y el label de festivos.
							hashContadorFestivos[$("#p96_cmb_anio").val()] = festivosLocalesAlimentados;
							$("#p96_lbl_diasRestantes").text(festivosLocalesAlimentados);
						}
					}
				}
			}
		}
	});
}

//Función que trata los camiones. Si el día está en verde, se podrán añadir y quitar camiones
function events_p96_camiones(){
	$(".p96_suministro1").click(function(){
		//Obtenemos el id y mediante el id el número del día con el que trabajamos.
		var id = this.id;
		var numeroDia = id.replace("formDiaEstructuraInfoSuministroCamionCerrado","");

		var eCambioPlataforma = $("#ecambioPlataforma"+numeroDia).val();

		//Si el ecambio plataforma es vacío deja modificar o no es vacío pero estamos en todos.
		if(eCambioPlataforma == '' || (eCambioPlataforma != '' && comboValueServicioP96 == " ")){
			//Miramos si tiene día verde o no para mostrar los temporales o no.
			//var diaVerde = $("#formDiaEstructuraInfoSuministro"+numeroDia).hasClass("p96_diaVerde") ? "S":"N";

			//A partir de misumi-237 que tenga el día pintado de verde no significa que pueda poner o quitar camión.
			//Podrá hacerlo si ponerDiaVerde es S o si verdePlataforma es S y además puedeSolicitarServicio es S.
			var tieneCamionSuperior = $("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).hasClass("p96_camionSuperior");
			var diaVerde = (($("#ponerDiaVerde"+numeroDia).val() == "S") || ($("#verdePlataforma"+numeroDia).val() == "S" && $("#puedeSolicitarServicio"+numeroDia).val() == "S") || ($("#verdePlataforma"+numeroDia).val() == "S" && $("#puedeSolicitarServicio"+numeroDia).val() == "" && !tieneCamionSuperior)) ? "S" : "N"; 
			var eCambioPlataforma  = $("#ecambioPlataforma"+numeroDia).val();
			var eSePuedeModificarServicio  = $("#esePuedeModificarServicio"+numeroDia).val();
			var tipoEjercicio = $("#tipoEjercicio"+comboValueAnioP96).val();



			//Si es tipo de Ejercicio 'Ejecucion' y el flag ECambioPlataforma es distinto de nulo o vacio no se pueden quitar o poner camiones. Ese evento estara deshabilitado

			//if ((tipoEjercicio != "E") || (tipoEjercicio == "E" && (eCambioPlataforma == null || eCambioPlataforma == ""))) {
			if ((tipoEjercicio != "E") || (tipoEjercicio == "E" && ((rol == "4") || eSePuedeModificarServicio == "S"))) {
				//Si el día es verde, miramos si tiene la clase del camión. Si el día no es verde no se hace nada, si lo
				//es, se le le añade o quita los camiones y sus eventos.		
				if(diaVerde == "S"){
					var tieneCamionSuperior = $("#formDiaEstructuraInfoSuministroCamionCerrado"+numeroDia).hasClass("p96_camionSuperior");
					//Si tiene camión superior, quitamos tanto el camión superior e inferior y sus eventos. Si no lo tiene, se añaden el camión superior e inferior y sus
					//eventos
					if(tieneCamionSuperior){
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

						//Ponemos cambio manual en N
						$("#cambioManual"+numeroDia).val("N");

						var fecha = $("#fechaCalendario"+numeroDia).val();
						var fechaSplit = fecha.split("-");
						var fechaFormat = fechaSplit[2] + "/" + fechaSplit[1] + "/" + fechaSplit[0]; 

						obtenerFilaYBorrar(fechaFormat);					

						//Ponemos eaprobadocambio en N para que no salga la alerta.
						$("#eaprobadoCambio"+numeroDia).val("N");

						//Quitamos la alerta parpadeante
						$("#formDiaEstructuraInfoImgWarning"+numeroDia).attr("style", "display:none");
						$("#formDiaEstructuraInfoImgWarning"+numeroDia).removeClass("parpadeo");

						//Actualizamos la tabla temporal con el cambioManual.
						var fechaCalendario = $("#fechaCalendario"+numeroDia).val();
						var cambioManual = $("#cambioManual"+numeroDia).val();
						var flgServiciosBuscados = $("#flgServiciosBuscados"+numeroDia).val();

						var tCalendarioDia = new TCalendarioDia(fechaCalendario,null,null,null,null,null,cambioManual,null,flgServiciosBuscados);
						updateDiaCalendarioTemporal(numeroDia,"N","S",tCalendarioDia);

						//Ponemos ecambioPlataforma a NULL para que no salga el bloque de plataforma en caso de quitar y poner el camión.
						$("#ecambioPlataforma"+numeroDia).val(null);
					}else{
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

						//Ponemos cambio manual en "S"
						$("#cambioManual"+numeroDia).val("S");
						
						//Actualizamos la tabla temporal con el cambioManual.
						var fechaCalendario = $("#fechaCalendario"+numeroDia).val();
						var cambioManual = $("#cambioManual"+numeroDia).val();
						var flgServiciosBuscados = $("#flgServiciosBuscados"+numeroDia).val();

						var tCalendarioDia = new TCalendarioDia(fechaCalendario,null,null,null,null,null,cambioManual,null,flgServiciosBuscados);
						updateDiaCalendarioTemporal(numeroDia,"N","S",tCalendarioDia);

						//Abrimos popup si no hay servicio seleccionado
						if(comboValueServicioP96 == " "){
							//Consultamos los servicios del día.
							consultarProcesosDiarios(numeroDia,diaVerde);
						}
					}			
				}
			}
		}
	});
	$(".p96_suministro2").click(function(){
		//Obtenemos el id y mediante el id el número del día con el que trabajamos.
		var id = this.id;
		var numeroDia = id.replace("formDiaEstructuraInfoSuministroCamionAbierto","");

		//Miramos si tiene día verde o no para mostrar los temporales o no.
		var diaVerde = $("#formDiaEstructuraInfoSuministro"+numeroDia).hasClass("p96_diaVerde") ? "S":"N";

		//Solo consultaremos los servicios si tiene camión inferior.
		var tieneCamionInferior = $("#formDiaEstructuraInfoSuministroCamionAbierto"+numeroDia).hasClass("p96_camionInferior") ? "S":"N";
		if(tieneCamionInferior == "S"){
			//Abrimos popup si no hay servicio seleccionado
			if(comboValueServicioP96 == " "){
				//Consultamos los servicios del día.
				consultarProcesosDiarios(numeroDia,diaVerde);}
		}		
	});
}

function obtenerFilaYBorrar(fechaFormat){
	var filas = $("#gridP99CalendarioWarnings").find("[aria-describedby='gridP99CalendarioWarnings_fechaAfectadaDDMMYYYY']");

	for(var i = 0; i< filas.length;i++){
		var fila = filas[i];

		if(fila.title == fechaFormat){
			var aspaRoja = $("#gridP99CalendarioWarnings").
			find("[aria-describedby='gridP99CalendarioWarnings_fechaAfectadaDDMMYYYY']")[i]
						.parentElement.childNodes[2].firstChild;
			if (aspaRoja!=null && aspaRoja!==undefined){
				aspaRoja.click();	
			}

			break;
		} 
	}
}
//Evento para flechas 
function events_p96_flecha_ant(){
	$("#p96_pagAnterior").click(function () {		
		paginacionFormulariosP96('N');
	});	
}

//Evento para flechas 
function events_p96_flecha_sig(){
	$("#p96_pagSiguiente").click(function () {		
		paginacionFormulariosP96('S');
	});	
}

function events_p96_iluminar(){
	//Iluminar
	$(".p96_camionSuperior").bind("mouseover",funcIlumiSup);	
	$(".p96_camionInferior").bind("mouseover",funcIlumiInf);		
}

function events_p96_noIluminar(){
	//Desiluminar 
	$(".p96_camionSuperior").bind("mouseout",funcNoIlumiSup);	
	$(".p96_camionInferior").bind("mouseout",funcNoIlumiInf);		
}

//Como se quiere hacer bind y unbind de los eventos, es necesario encapsularlos en funciones.
var funcNoIlumiSup = function () {
	//Obtenemos el id y mediante el id el número del día con el que trabajamos.
	var id = this.id;
	var numeroDia = id.replace("formDiaEstructuraInfoSuministroCamionCerrado","");

	var esVerde = $("#formDiaEstructuraInfoSuministro"+numeroDia).hasClass("p96_diaVerde");
	//Si el día no es verde, no queremos que el camión superior se desilumine.
	if(esVerde){
		$(this).addClass("p96_desIluminar");
	}
}

var funcNoIlumiInf = function () {		
	$(this).addClass("p96_desIluminar");
}

var funcIlumiSup = function () {
	//Obtenemos el id y mediante el id el número del día con el que trabajamos.
	var id = this.id;
	var numeroDia = id.replace("formDiaEstructuraInfoSuministroCamionCerrado","");

	var esVerde = $("#formDiaEstructuraInfoSuministro"+numeroDia).hasClass("p96_diaVerde");
	//Si el día no es verde, no queremos que el camión superior se ilumine.
	if(esVerde){
		//$(this).addClass("p96_iluminar");
		$(this).removeClass("p96_desIluminar");
	}
}

var funcIlumiInf = function () {	
	$(this).removeClass("p96_desIluminar");
}


//Evento que ilumina, desilumina el botón de guardado, cambio estacional y el de validar.
function events_p96_btnIluminar(){
	$("#p96_btn_guardado").mouseover(function(){
		$("#p96_btn_guardado").removeClass("p96_desIluminar");
		$("#p96_btn_guardado").addClass("p96_btn_habilitado");
	});
	$("#p96_btn_guardado").mouseout(function(){
		$("#p96_btn_guardado").removeClass("p96_btn_desHabilitado");
		$("#p96_btn_guardado").addClass("p96_desIluminar");
	});
	$("#p96_btn_estacion").mouseover(function(){
		$("#p96_btn_estacion").removeClass("p96_desIluminar");
		$("#p96_btn_estacion").addClass("p96_btn_habilitado");
	});
	$("#p96_btn_estacion").mouseout(function(){
		$("#p96_btn_estacion").removeClass("p96_btn_desHabilitado");
		$("#p96_btn_estacion").addClass("p96_desIluminar");
	});
	$("#p96_btn_finalizado").mouseover(function(){
		$("#p96_btn_finalizado").removeClass("p96_desIluminar");
		$("#p96_btn_finalizado").addClass("p96_btn_habilitado");
	});
	$("#p96_btn_finalizado").mouseout(function(){
		$("#p96_btn_finalizado").removeClass("p96_btn_desHabilitado");
		$("#p96_btn_finalizado").addClass("p96_desIluminar");
	});
}

//Al clicar guardar, se guardan los datos modificados en el PLSQL si existe algún cambio.
function events_p96_btnGuardar(){
	$("#p96_btn_guardado").click(function(){
		//Se mira si hay algún elemento guardado, esta opción viene bien si no se ha modificado el calendario. En el caso de haber modificado algo
		//y más tarde haberlo dejado como estaba, será inevitable llamar al controlador y este, devolverá un error de que no hay elementos guardados.
		/*if(flgAlMenosUnElementoGuardado != "S"){
			createAlert(sinRegistrosModificados,"ERROR");
		}else{*/
		//Obtenemos el tipo de ejercicio

		//}
		guardarCalendario();
	});
}

function guardarCalendario(){
	var codigoEjercicio = comboValueAnioP96;
	var tipoEjercicio = $("#tipoEjercicio"+comboValueAnioP96).val();
	var anioEjercicio = $("#anioEjercicio"+comboValueAnioP96).val();

	$.ajax({
		type : 'POST',
		url : './calendario/guardarCalendario.do?tipoEjercicio='+tipoEjercicio+"&codigoServicio="+comboValueServicioP96+"&codigoEjercicio="+anioEjercicio,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {
			//Si devuelve 0, se muestra que se ha guardado todo OK, si no muestra el error que devuelve el controlador.
			if(data.codError == 0){
				flgAlMenosUnElementoGuardado = "N";
				createAlert(calendarioGuardadoCorrectamente,"INFO");				
			}else if(data.codError == 1){
				flgAlMenosUnElementoGuardado = "N";
				createAlert(data.descError,"CANCEL");	
			}else{
				createAlert(data.descError,"ERROR");
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});	
}

//Evento que abre el popup de servicios temporales de un ejercicio.
function events_p96_btnServiciosTemporales(){
	$("#p96_btn_estacion").click(function(){
		load_serviciosTemporalesP98();
		//$("#p98_popUpServiciosTemporales").dialog('open');
	});
}

//Evento que se ejecuta al pulsar validar calendario
function events_p96_btnValidar(){
	$("#p96_btn_finalizado").click(function(){
		validarCalendario();
	});
}

function events_p96_iluminarDia(){
	//Iluminar la foto
	$(".p96_dia").mouseover(function () {	
		$(".iluminar").removeClass("iluminar");
		$(this).addClass("iluminar");
	});	
}

function events_p96_noIluminarDia(){
	//Desiluminar foto
	$(".p96_dia").mouseout(function () {		
		$(this).removeClass("iluminar");
	});	
}

//Al clicar el calendario anual, se muestra el calendario anual con sus festivos
function events_p100_btnCalendarioAnual(){
	$("#p96_btn_calendarioAnual").click(function(){
		load_calendarioAnualP100();
	});
}