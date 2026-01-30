/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/****************************************************************** CONSTANTES ***************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
//Estados de las devoluciones
var estadoMercancia = 1;
var estadoPlataforma = 2;
var estadoAbonado = 3;
var estadoIncidencia = 4; 

var totalEstados = 5;



/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/****************************************************************** VARIABLES ****************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Mensajes json de p82
var noDataForCenter=null;
var noDataForEntry=null;

var p82Inicializada = null;

var estado = null;

var abonadoOIncidencia = null;

var flgHistorico = 'N';

//Variable para controlar el mostrado del popup automático al entrar en la pantalla
var mostradoInicialPopup = true;

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************** DOCUMENTO LISTO **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

$(document).ready(function(){
	if($("#centerId").val() != null && $("#centerId").val() != "" && p82Inicializada == null){
		p82Inicializada = 'S';
		loadP82(locale);
		initializeScreenP82();
		resetWindow();
	}
});

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/********************************************************* INICIALIZACIÓN DE PANTALLA ********************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

function initializeScreenP82(){
	//Evento de botón buscar
	events_p82_btn_buscar();

	//Evento al presionar el icono de ayuda
	events_p82_btn_ayudaHistorico();

	//Evento al presionar el icono +
	events_p82_btn_crearDevolucion();

	//Evento que pinta las denominaciones de las devoluciones y sus estados
	events_p82_check_flgHistorico();
	
	//Se crea el combobox
	$("#p82_cmb_DescDevol").combobox(null);

	//Se buscan los elementos que rellenarán el combobox
	load_cmb_DescDevol();

	//Iniciar parámetros del fichero javascript del popup
	initializeScreenP85();
	
	//Iniciar parámetros del fichero javascript del popup de crear devolucion
	initializeScreenP86();
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************** INICIALIZACIÓN DE EVENTOS E IDIOMA *******************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

/************************ IDIOMA ************************/
//Función para buscar los mensajes de la pantalla en idioma seleccionado, por defecto 'es'
function loadP82(locale){
	this.i18nJSON = './misumi/resources/p82Devoluciones/p82Devoluciones_' + locale + '.json';

	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		abonado = data.abonado;	
		incidencia = data.incidencia;
		mercancia = data.mercancia;
		verificarYAbonar = data.verificarYAbonar;
		orden = data.orden;
		recogida = data.recogida;
		imprimir = data.imprimir;
		devol = data.devolucion;
		fin = data.fin;
		tarea = data.tarea;
		eliminar = data.eliminar;
		duplicar = data.duplicar;
		devolucionTxtP86 = data.devolucion;
		textoLinkFinalizar = data.textoLinkFinalizar;
		textoLinkFueraDeFechas = data.textoLinkFueraDeFechas;
		textoLinkSinRealizarPorCentro = data.textoLinkSinRealizarPorCentro;
		tituloPlataforma = data.tituloPlataforma;
		tituloIncidencia = data.tituloIncidencia;
		tituloAbonado = data.tituloAbonado;
		tituloMercancia = data.tituloMercancia;
		notPosibleToReturnData = data.notPosibleToReturnData;
		noHayElementosDeDevoluciones = data.noHayElementosDeDevoluciones;
		devolucionFinalizadaCorrectamente = data.devolucionFinalizadaCorrectamente;
		devolucionDuplicadaCorrectamente = data.devolucionDuplicadaCorrectamente;
		devolucionEliminadaCorrectamente = data.devolucionEliminadaCorrectamente;
		consulta = data.consulta;
		modificacion = data.modificacion;
		cantidad = data.cantidad;
		tooltipConsulta = data.tooltipConsulta;
		tooltipModificacion = data.tooltipModificacion;
		finalizarConRellenarHuecos = data.finalizarConRellenarHuecos;
		finalizarSinRellenarHuecos = data.finalizarSinRellenarHuecos;
		btnSi = data.btnSi;
		btnNo = data.btnNo;
		btnAceptar = data.btnAceptar;
		noDataForCenter =  data.noDataForCenter;	
		noDataForEntry = data.noDataForEntry;
		mensajeAyudaHistorico = data.mensajeAyudaHistorico;
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

/************************ EVENTOS ************************/
//Evento para botón de buscar
function events_p82_btn_buscar(){
	$("#p82_btn_buscar").click(function () {	
		dibujarDevoluciones();
	});	
}

//Buscador al presionar enter.
function finder(){
	dibujarDevoluciones();
}

function abrirOpcionMercancia(){
	//Se guarda el estado seleccionado en una variable global para utilizarla en p85.js a la hora de dibujar los formularios en dibujarFormulariosDeDevolucion()
	estado = estadoMercancia;

	$("#prepararMercancia").val("1"); // se indica que se ha elegido la opción "Preparar Mercancia" en el gráfico.
	
	//Indicamos que no se trata de abonado o incidencia.
	abonadoOIncidencia = false;
	rellenarPopUpFormularioDevol(estadoMercancia);
}

//Evento para mercancia. Abre un popup con las devoluciones con mercancía.
function events_img_mercancia(){
	$(".estadoDevolucion1").click(function () {	
		abrirOpcionMercancia();
	});	
}

//Evento para plataforma. Abre un popup con las devoluciones con plataforma.
function events_p82_img_plataforma(){
	$(".estadoDevolucion2").click(function () {
		//Se guarda el estado seleccionado en una variable global para utilizarla en p85.js a la hora de dibujar los formularios en dibujarFormulariosDeDevolucion()
		estado = estadoPlataforma;

		//Indicamos que no se trata de abonado o incidencia.
		abonadoOIncidencia = false;
		rellenarPopUpFormularioDevol(estadoPlataforma);
	});	
}

//Evento para abonado. Abre un popup con las devoluciones con abonado.
function events_p82_img_abonado(){
	$(".estadoDevolucion3").click(function () {
		//Se guarda el estado seleccionado en una variable global para utilizarla en p85.js a la hora de dibujar los formularios en dibujarFormulariosDeDevolucion()
		estado = estadoAbonado;

		//Indicamos que se trata de abonado o incidencia.
		abonadoOIncidencia = true;
		rellenarPopUpFormularioDevol(estadoAbonado);
	});	
}

//Evento para incidencia. Abre un popup con las devoluciones con incidencia.
function events_p82_img_incidencia(){
	$(".estadoDevolucion4").click(function () {		
		//Se guarda el estado seleccionado en una variable global para utilizarla en p85.js a la hora de dibujar los formularios en dibujarFormulariosDeDevolucion()
		estado = estadoIncidencia;

		//Indicamos que se trata de abonado o incidencia.
		abonadoOIncidencia = true;
		rellenarPopUpFormularioDevol(estadoIncidencia);
	});	
}

//Evento que muestra una ayuda explicando la utilidad del check de Histórico
function events_p82_btn_ayudaHistorico(){
	$("#p82_btn_ayudaHistorico").click(function () {
		createAlert(replaceSpecialCharacters(mensajeAyudaHistorico), "HELP");
	});
}

function events_p82_btn_crearDevolucion(){
	var userId = $("#userId").attr("value").toLowerCase();
	if(userId == "i1251" || userId == "s305060" || userId == "i2003"){
		$("#p82_img_crearDevolucion").click(function () {
			//Abrir diálogo
			$("#p86_popup").dialog('open');
		});
	}
}

function events_p82_check_flgHistorico(){
	$("#p82_chk_historico").click(function () {
		load_cmb_DescDevol();
	});
}
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/********************************************************** RESETEO DE PANTALLA **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
function resetWindow(){
	$( "#centerName" ).bind('click', function() {
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

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************* FUNCIONES DE P82 **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Buscar descripciones de las devoluciones y rellenar el combobox llamado Descrip.devolucion
function load_cmb_DescDevol(){
	var options = "";

	var flgHistorico = 'N';

	if($("#p82_chk_historico").prop('checked')){
		flgHistorico = 'S';
	}
	
	var devolucion = new Devolucion($("#centerId").val(),flgHistorico);
	var objJson = $.toJSON(devolucion);

	$.ajax({
		type : 'POST',
		url : './devoluciones/loadDenominacionesDevoluciones.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {
			/*if(data != null){
				if(data.pCodError == 0){
					if(data.listCatalogoDescripcion != null){
						if(data.listCatalogoDescripcion.length != 0){						
							//Eliminamos las posibles repeticiones de la lista antes de insertarlas en el combo.
							var catalogoDescripcionSinRepetir = [];
							$.each(data.listCatalogoDescripcion, function(i, el){
								if($.inArray(el, catalogoDescripcionSinRepetir) === -1){
									catalogoDescripcionSinRepetir.push(el);
								}
							});

							//Insertamos las opciones sin repetir.
							options = "<option value=' '>TODOS</option>";
							for (i = 0; i < catalogoDescripcionSinRepetir.length; i++){
								options = options + "<option value='" + catalogoDescripcionSinRepetir[i] + "'>" + catalogoDescripcionSinRepetir[i] + "</option>";
							}
							$("select#p82_cmb_DescDevol").html(options);

							$("#p82_cmb_DescDevol").combobox('autocomplete',"TODOS");
							$("#p82_cmb_DescDevol").combobox('comboautocomplete',"null");
						}else{
							//Deshabilitar el botón buscar, el combobox y el checkbox si no existen elementos que buscar.
							disableAreaFiltro();
							createAlert(replaceSpecialCharacters(noDataForCenter), "ERROR");
						}
					}else{
						//Deshabilitar el botón buscar, el combobox y el checkbox si no existen elementos que buscar.
						disableAreaFiltro();
						createAlert(replaceSpecialCharacters(noDataForCenter), "ERROR");
					}
				}else{
					//Deshabilitar el botón buscar, el combobox y el checkbox si no existen elementos que buscar.
					disableAreaFiltro();
					createAlert(replaceSpecialCharacters(data.pDescError), "ERROR");
				}
			}else{				
				//Deshabilitar el botón buscar, el combobox y el checkbox si no existen elementos que buscar.
				disableAreaFiltro();
				createAlert(replaceSpecialCharacters(noDataForCenter), "ERROR");
			}*/
			//Insertamos las opciones sin repetir.
			options = "<option value=' '>TODOS</option>";
			if(data != null){
				if(data.pCodError == 0){
					if(data.listCatalogoDescripcion != null){
						if(data.listCatalogoDescripcion.length != 0){						
							//Eliminamos las posibles repeticiones de la lista antes de insertarlas en el combo.
							var catalogoDescripcionSinRepetir = [];
							$.each(data.listCatalogoDescripcion, function(i, el){
								if($.inArray(el, catalogoDescripcionSinRepetir) === -1){
									catalogoDescripcionSinRepetir.push(el);
								}
							});

							//Insertamos las opciones sin repetir.
							for (i = 0; i < catalogoDescripcionSinRepetir.length; i++){
								options = options + "<option value='" + catalogoDescripcionSinRepetir[i].descripcion + "'>" + catalogoDescripcionSinRepetir[i].localizador + " - " + catalogoDescripcionSinRepetir[i].descripcion + "</option>";
							}
						}
					}
				}
			}
			$("select#p82_cmb_DescDevol").html(options);

			$("#p82_cmb_DescDevol").combobox('autocomplete',"TODOS");
			$("#p82_cmb_DescDevol").combobox('comboautocomplete',"null");

			dibujarDevoluciones();
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});
}

//Deshabilitar el botón buscar, el combobox y el checkbox si no existen elementos que buscar.
function disableAreaFiltro(){
	$("#p82_cmb_DescDevol").combobox("disable");

	$("#p82_chk_historico").attr("disabled", "disabled");
	$("#p82_cmb_referencia_b").attr("disabled", "disabled");
	$("#p82_btn_buscar").attr("disabled", "disabled");
}

//Esconde el area de resultados de la nube
function disableAreaResultados(){
	$("#p82_AreaResultados").hide();
}

//Enseña el area de resultados de la nube
function enableAreaResultados(){
	$("#p82_AreaResultados").show();
}

//Función que sirve para dibujar las imágenes situadas dentro de la nube y las elipses según la búsqueda realizada. 
function dibujarDevoluciones(){
	var centro = $("#centerId").val();
	var titulo1 = $("#p82_cmb_DescDevol").val();
	var codArticulo = $("#p82_cmb_referencia_b").val();
	flgHistorico = 'N';

	if($("#p82_chk_historico").prop('checked')){
		flgHistorico = 'S';
	}

	var devolucion = new Devolucion(centro,flgHistorico,titulo1,codArticulo);
	var objJson = $.toJSON(devolucion);

	$.ajax({
		type : 'POST',
		url : './devoluciones/loadEstadoDevoluciones.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		async: false,
		cache : false,
		success : function(data) {
			
			$("#prepararMercancia").val("0"); // se resetea el valor de "Preparar Mercancia".

			//Si existen datos
			if(data != null){
				//Si no hay error
				if(data.pCodError == 0){
					if(data.listDevolucionEstado != null){
						if(data.listDevolucionEstado.length != 0){
							//Limpiar las imágenes de los estados anteriores.
							//En el caso de no hacer esto, podría ocurrir que una búsqueda devuelva
							//imágenes para algunos estados y para otros no. Al realizar una segunda
							//búsqueda no se eliminarían las imágenes del estado anterior y al final
							//tendríamos las imágenes de los estados desactualizadas.
							
							//Además se quitan los eventos click porsiacaso
							limpiarEstadosAnteriores();														

							//Variables para dibujas las imágenes de las elipses y nubes
							var numeroDeDevoluciones;
							var estadoDevolucion;
							var imagenASeleccionar;

							for(i = 0; i < data.listDevolucionEstado.length; i++){

								//Calcular cuantas devoluciones existen en cada estado, se utilizará para buscar la imagen adecuada
								numeroDeDevoluciones = data.listDevolucionEstado[i].numeroRegistros;

								//Calcular cada estado para así encontrar el id de la imagen y encontrar la imagen adecuada
								estadoDevolucion = data.listDevolucionEstado[i].estado;

								//Si hay más de 3 devoluciones, el número de la imagen de devoluciones será la terminada en 4.
								//Si no, será 0,1,2 o 3
								if(numeroDeDevoluciones > 3){
									numeroDeDevoluciones = 4;
								}

								if(numeroDeDevoluciones > 0){
									//Poner la correspondiente imagen en su correspondiente id
									imagenASeleccionar = "./misumi/images/numeroDevolucion"+estadoDevolucion + numeroDeDevoluciones+".png?version=${misumiVersion}"
									$("#estadoDevolucion"+estadoDevolucion).attr('src',imagenASeleccionar);		

									//Poner la correspondiente clase para el evento clicable
									$("#estadoDevolucion"+estadoDevolucion).addClass("estadoDevolucion"+estadoDevolucion);

									//Poner la correspondiente clase de puntero
									$("#estadoDevolucion"+estadoDevolucion).addClass("estadoClickable");
								}
							}
							//Si existe un estado, muestra el área de los estados de la búsqueda con las nubes y elipses
							enableAreaResultados();

							//Evento dentro de elipse de mercancía
							events_img_mercancia();

							//Evento dentro de nube de plataforma
							events_p82_img_plataforma();

							//Evento dentro de elipse de abonado
							events_p82_img_abonado();

							//Evento dentro de elipse de incidencia
							events_p82_img_incidencia();
						}else{
							disableAreaResultados();
							createAlert(replaceSpecialCharacters(noDataForEntry), "ERROR");
						}
					}else{
						disableAreaResultados();
						createAlert(replaceSpecialCharacters(noDataForEntry), "ERROR");
					}
				}else{
					disableAreaResultados();
					createAlert(replaceSpecialCharacters(data.pDescError), "ERROR");
				}
			}else{
				disableAreaResultados();
				createAlert(replaceSpecialCharacters(noDataForEntry), "ERROR");
			}
			
			//Control para ver si la carga proviene del aviso de devoluciones
			if (mostradoInicialPopup){
				mostradoInicialPopup = false; //Tras el mostrado inicial se marca para no tratar la carga automática del popup				
				if($("#p82_origenPantalla").val() != '' && $("#p82_origenPantalla").val() == 'AVISOS'){
					abrirOpcionMercancia();
				}
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});	
}

//Limpia las imágenes de los estados anteriores y sus eventos
function limpiarEstadosAnteriores(){
	//Al haber 5 estados se hace un loop sobre esos 4 estados
	for(i = 1;i<totalEstados;i++){
		//Quitar imagen de estado
		$("#estadoDevolucion"+i).attr('src','');

		//Quitar clase de estado para evitar que se pueda lanzar evento
		$("#estadoDevolucion"+i).removeClass("estadoDevolucion"+i);

		//Quitar la correspondiente clase de puntero
		$("#estadoDevolucion"+i).removeClass("estadoClickable");
		
		//Quitar el evento click
		$("#estadoDevolucion"+i).unbind("click");
	}
}

