/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/****************************************************************** CONSTANTES ***************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Constante que indica el número de elementos máximo a mostrar por paginación. Cambiar si se quiere mostrar menos formularios
var maxElementosPopup = 6;
var devolucionOrdenRetirada = "Ret";
var devolucionFinCampana = "Fin";
var devolucionSobrestock = "Sob";
var devolucionCreadaCentro = "Cre";

var motivoCreadaCentro = 'Creada por el centro';

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/**************************************************************** VARIABLES ******************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Variables para guardar texto de los json
var abonado=null;
var incidencia=null;
var mercancia=null;
var verificarYAbonar=null;
var orden = null;
var recogida = null;
var imprimir = null;
var devol = null;
var fin = null;
var tarea = null;
var eliminar = null;
var duplicar = null;
var devolucionTxtP86 = null;
var textoLinkFinalizar = null;
var textoLinkFueraDeFechas = null;
var textoLinkSinRealizarPorCentro = null;
var tituloPlataforma = null;
var tituloIncidencia = null;
var tituloAbonado = null;
var tituloMercancia = null;
var notPosibleToReturnData = null;
var noHayElementosDeDevoluciones = null;
var devolucionFinalizadaCorrectamente = null;
var devolucionDuplicadaCorrectamente = null;
var devolucionEliminadaCorrectamente = null;
var consulta = null;
var modificacion = null;
var cantidad = null;
var tooltipConsulta = null;
var tooltipModificacion = null;
var finalizarConRellenarHuecos = null;
var finalizarSinRellenarHuecos = null;
var btnSi = null;
var btnNo = null;

//Indices para saber el color de las flechas de paginación
var idxInicioLista = null;
var idxFinLista = null;
var largoLista = null;

//Lista de devolucion
var listDevolucion = null;

//Variables necesarias para imprimir la devolución después de finalizar una devolución
var listDevolucionImprimir = null;
var idDevolucionImprimir = null;

//Guarda la devolución seleccionada
var devolucionFicha = null;

//Guarda si la ficha de la devolución pinchada tiene un lápiz, esto se utiliza para que no salgan campos editables en el grid si la ficha no tiene un lápiz
//además en el caso de no ser editable, queremos que en el caso de que stockActual sea 0, salgan TODAS las líneas en rojo mediante la función p83ColorearStockActual.
//Como los campos de edición no existirán, por ejemplo, 1_stockActual no existirá y su campo devolverá undefined. En esa función se mira que cambie de color de rojo a negro
//si el stockActual ha cambiado. Para ello mira el 1_stockActual_orig y 1_stockActual. Como 1_stockActual no existe es imposible hacer esa comparación y habrá que pintarlo
//siempre de rojo en el caso de que tieneLapizFicha sea false.
var tieneLapizLaFicha;

//Si hay alguna devolución finalizada, al cerrar el popup se ejecuta dibujarDevoluciones()
var devolucionesFinalizadas = false;

//Si hay alguna devolución duplicada, al cerrar el popup se ejecuta dibujarDevoluciones()
var devolucionesDuplicadas = false;

//Si hay alguna devolución eliminada, al cerrar el popup se ejecuta dibujarDevoluciones()
var devolucionesEliminadas = false;

//Guarda el id de la devolución que hay que colorear para que el usuario sepa que la ha estado viendo recientemente.
var numeroEstructuraAColorear = null;

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/********************************************************* INICIALIZACIÓN DE POPUP P85 *******************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

function initializeScreenP85(){

	//Inicializa el popup que contendrá los formularios de las devoluciones 
	inicializarPopUpFormDevol();

	//Cargar idioma - Comentado ya que los datos se cargan desde loadP82 y no es necesario
//	loadP85(locale);

	//Eventos flecha formularios
	events_p85_flecha_ant();
	events_p85_flecha_sig();

	$("#p03_btn_aceptar").bind("click", function(e) {
		if($("#p85_fld_finalizarPulsado").val() == 'S'){
			imprimirDevolucionesOrdenRecogidaTrasFinalizar();

			//Devolvemos el flag a N por si salen mensajes de otro tipo que no sean tras finalizar correctamente
			$("#p85_fld_finalizarPulsado").val('N');
		}
	});
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************ INICIALIZACIÓN DE ELEMENTOS E IDIOMA *******************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

/************************ IDIOMA ************************/
//Función para buscar los mensajes de la pantalla en idioma seleccionado, por defecto 'es'
function loadP85(locale){
	this.i18nJSON = './misumi/resources/p85PopUpFormDevol/p85PopUpFormDevol_' + locale + '.json';

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
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

/************************ POPUP ************************/
function inicializarPopUpFormDevol(){
	//Definión del popup que contendrá los formularios
	$("#p85_popup").dialog({
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
			if(devolucionesFinalizadas){
				dibujarDevoluciones();
				devolucionesFinalizadas = false;
			}
			if(devolucionesDuplicadas){
				//Actualizamos tanto las imágenes de la nube/elipses como las descripciones del combo.
				load_cmb_DescDevol();
				//dibujarDevoluciones();
				devolucionesDuplicadas = false;
			}
			if(devolucionesEliminadas){
				dibujarDevoluciones();
				devolucionesEliminadas = false;
			}
			numeroEstructuraAColorear = null;
		},
		stack:false
	});
}

/************************ EVENTOS ************************/
//Evento para flechas 
function events_p85_flecha_ant(){
	$("#p85_pagAnterior").click(function () {		
		paginacionFormularios('N');
	});	
}

//Evento para flechas 
function events_p85_flecha_sig(){
	$("#p85_pagSiguiente").click(function () {		
		paginacionFormularios('S');
	});	
}

function events_p85_iluminar_Devolucion(){
	//Iluminar la carta si no ha sido utilizada
	$(".card").mouseover(function () {	
		$(".blueCard").removeClass("blueCard"); //En cuanto actúe sobre la carta se eliminan las cartas iluminadas
		$(this).addClass("iluminar");
	});	
}

function events_p85_desiluminar_Devolucion(){
	//Desiluminar la carta
	$(".card").mouseout(function () {		
		if(!$(this).hasClass("blueCard")){
			$(this).removeClass("iluminar");
		}
	});	
}

//Evento para abrir el grid de líneas de devolucion. Se inicializa después de crear los formularios de devolución en rellenarPopUpFormularioDevol
function events_p85_ficha(){
	$(".formDevolucionFicheroDevolucion").click(function() {
		/*********************** ABRIR FORMULARIO DE DEVOLUCIÓN ************************/
		//Se obtiene el id de la ficha, para saber qué formulario se ha clicado.
		//El id tiene este formato formDevolEstructura1Ficha0. Esto quiere decir
		//que para construir el formulario se ha utilizado la estructura 1 hidden
		//del jsp y que se ha clicado el formulario 0. Por lo cual, en la lista
		//global llamada listDevolucion, habrá que buscar el elemento situado
		//en la posición 0. 
		var id = $(this).attr('id');

		//En esta función se obtiene el número de formulario clicado, se obtiene 
		//su correspondiente devolución de la lista y se buscan las líneas de esa
		//devolución.
		cargarLineaDevolucion(id);

		/****************************** COLOREAR CABECERA ******************************/
		//Eliminamos las posibles sombras azules de las cabeceras clicadas
		$(".blueCard").removeClass("blueCard");
		$(".iluminar").removeClass("iluminar");

		//Obtenemos la estructura de la devolución clicada quitando el string "FICHA" del id de la imagen clicada. Después coloreamos de azul la sombra de la cabecera de la devolución para que el usuario sepa que estaba mirando esa
		//devolución.
		var idEstructuraFicha = id.replace('Ficha','');
		$("#"+idEstructuraFicha).addClass("blueCard");

		//Se obtienen los números del idEstructuraFicha para saber cual es el formulario seleccionado y saber la posición de la devolución en la lista
		//Ejemplo: el id es formDevolEstructura10, se obtiene 10. 1-> Estructura de formulario utilizada 0-> Posicion de devolucion en lista
		var numeroEstructuraNumeroFormulario = idEstructuraFicha.replace(/[^0-9]/g,'');

		//Como sabemos que el primer dígito corresponde a la estructura del formulario se elimina para obtener la posición real.
		//Ejemplo: el id es formDevolEstructura11210, se obtiene 1210. 1210-> Posicion de devolucion en lista
		numeroEstructuraAColorear = numeroEstructuraNumeroFormulario.substr(1);
	});
}

//Evento para abrir el grid de líneas de devolucion. Se inicializa después de crear los formularios de devolución en rellenarPopUpFormularioDevol
function events_p85_fichaLapiz(){
	$(".formDevolucionFicheroDevolucionLapiz").click(function() {		
		/*********************** ABRIR FORMULARIO DE DEVOLUCIÓN ************************/
		//Se obtiene el id de la ficha, para saber qué formulario se ha clicado.
		//El id tiene este formato formDevolEstructura1FichaLapiz0. Esto quiere decir
		//que para construir el formulario se ha utilizado la estructura 1 hidden
		//del jsp y que se ha clicado el formulario 0. Por lo cual, en la lista
		//global llamada listDevolucion, habrá que buscar el elemento situado
		//en la posición 0. 
		var id = $(this).attr('id');

		//En esta función se obtiene el número de formulario clicado, se obtiene 
		//su correspondiente devolución de la lista y se buscan las líneas de esa
		//devolución.
		cargarLineaDevolucion(id);


		/****************************** COLOREAR CABECERA ******************************/
		//Eliminamos las posibles sombras azules de las cabeceras clicadas
		$(".blueCard").removeClass("blueCard");
		$(".iluminar").removeClass("iluminar");

		//Obtenemos la estructura clicada quitando el string "FICHA" del id de la imagen clicada. Después coloreamos la sombre de la cabecera de la devolución para que el usuario sepa que estaba mirando esa
		//devolución.
		var idEstructuraFicha = id.replace('Ficha','');
		$("#"+idEstructuraFicha).addClass("blueCard");

		//Se obtienen los números del idEstructuraFicha para saber cual es el formulario seleccionado y saber la posición de la devolución en la lista
		//Ejemplo: el id es formDevolEstructura10, se obtiene 10. 1-> Estructura de formulario utilizada 0-> Posicion de devolucion en lista
		var numeroEstructuraNumeroFormulario = idEstructuraFicha.replace(/[^0-9]/g,'');

		//Como sabemos que el primer dígito corresponde a la estructura del formulario se elimina para obtener la posición real.
		//Ejemplo: el id es formDevolEstructura11210, se obtiene 1210. 1210-> Posicion de devolucion en lista
		numeroEstructuraAColorear = numeroEstructuraNumeroFormulario.substr(1);
	});
}

//.Se inicializa después de crear los formularios de devolución en rellenarPopUpFormularioDevol
function events_p85_imprimir(){
	$(".formDevolucionImprimirDevolucion").click(function() {
		/*********************** IMPRIMIR FORMULARIOS. SEGUN ESTADOS  LISTADO DE REFERENCIAS o ORDEN RECOGIDA ************************/
		var id = $(this).attr('id');

		imprimirDevolucionesOrdenRecogida(id);

		/****************************** COLOREAR CABECERA ************************************/
		//Eliminamos las posibles sombras azules de las cabeceras clicadas
		$(".blueCard").removeClass("blueCard");

		//Obtenemos la estructura de la devolución clicada quitando el string "FICHA" del id de la imagen clicada. Después coloreamos de azul la sombra de la cabecera de la devolución para que el usuario sepa que estaba mirando esa
		//devolución.
		var idEstructuraFicha = id.replace('Impr','');
		$("#"+idEstructuraFicha).addClass("blueCard");

		//Se obtienen los números del idEstructuraFicha para saber cual es el formulario seleccionado y saber la posición de la devolución en la lista
		//Ejemplo: el id es formDevolEstructura10, se obtiene 10. 1-> Estructura de formulario utilizada 0-> Posicion de devolucion en lista
		var numeroEstructuraNumeroFormulario = idEstructuraFicha.replace(/[^0-9]/g,'');

		//Como sabemos que el primer dígito corresponde a la estructura del formulario se elimina para obtener la posición real.
		//Ejemplo: el id es formDevolEstructura11210, se obtiene 1210. 1210-> Posicion de devolucion en lista
		numeroEstructuraAColorear = numeroEstructuraNumeroFormulario.substr(1);
	});
}

//.Se inicializa después de crear los formularios de devolución en rellenarPopUpFormularioDevol
function events_p85_cancelar(){
	$(".formDevolucionDenegarDevolucion").click(function() {
		var id = $(this).attr('id');
		cargarLineaDevolucion(id);
	});
}

//Evento para finalizar la tarea de devolución.Se inicializa después de crear los formularios de devolución en rellenarPopUpFormularioDevol
function events_p85_finalizarTarea(){
	$(".formDevolucionBanderasFinalizar").click(function() {
		
		//Se obtiene el id del padre de la caja de finalizar tarea, que contiene el número de devolución a finalizar.
		//var id = $(this).parent().attr('id');
		var id = $(this).attr('id');
		
		//Se obtienen los números del id para saber cual es el formulario seleccionado y saber la posición de la devolución en la lista
		//Ejemplo: el id es formDevolEstructura1Ficha0, se obtiene 10. 1-> Estructura de formulario utilizada 0-> Posicion de devolucion en lista
		var numeroEstructuraNumeroFormulario = id.replace(/[^0-9]/g,'');

		//Como sabemos que el primer dígito corresponde a la estructura del formulario se elimina para obtener la posición real.
		//Ejemplo: el id es formDevolEstructura1Ficha1210, se obtiene 1210. 1210-> Posicion de devolucion en lista
		var numeroFormulario = numeroEstructuraNumeroFormulario.substr(1);

		//Se obtiene la devolución
		devolucionFicha = listDevolucion[numeroFormulario];
		
		if (devolucionFicha.tipoRMA != null && (devolucionFicha.codRMA == null || $.trim(devolucionFicha.codRMA) == "")) {
			
				$( "#p85dialog-rellenarRma" ).dialog({
					resizable: false,
					height:160,
					modal: true,
					buttons: [
					          {
					        	  text:btnAceptar,
					        	  click: function() {
					        		  $(this).dialog( "close" );
					        	  }
					          }
					          ]
				});	

		} else {

			var flgRellenarHuecos = finalizarObtenerRellenarHuecos(id);
			if(flgRellenarHuecos != "ERROR"){
				if(flgRellenarHuecos == "S"){
					$("#p85_td_finalizar").text(finalizarConRellenarHuecos);
				}else{
					$("#p85_td_finalizar").text(finalizarSinRellenarHuecos);
				}		
				$( "#p85dialog-confirmFinalizar" ).dialog({
					resizable: false,
					height:160,
					modal: true,
					buttons: [
					          {
					        	  text:btnSi,
					        	  click: function() {
					        		  //Finaliza la tarea de la devolución
					        		  finalizarTareaDeDevolucion(id,flgRellenarHuecos);		
					        		  $(this).dialog( "close" );
					        	  }
					          },{
					        	  text:btnNo,
					        	  click: function() {
					        		  $(this).dialog( "close" );
					        	  }
					          }
					          ]
				});	
			}
		}
	});	
}

//Evento para duplicar la tarea de devolución.Se inicializa después de crear los formularios de devolución en rellenarPopUpFormularioDevol
function events_p85_duplicarDevolucion(){
	$(".formDevolucionFicheroDevolucionDuplicar").click(function() {
		//Se obtiene el id del padre de la caja de duplicar devolución, que contiene el número de devolución a duplicar.
		var id = $(this).attr('id');

		$( "#p85dialog-confirmDuplicarDev" ).dialog({
			resizable: false,
			height:160,
			modal: true,
			buttons: [
			          {
			        	  text:btnSi,
			        	  click: function() {
			        		  //Copia la tarea de la devolución
			        		  duplicarDevolucion(id);
			        		  $(this).dialog( "close" );
			        	  }
			          },{
			        	  text:btnNo,
			        	  click: function() {
			        		  $(this).dialog( "close" );
			        	  }
			          }
			          ]
		});	
	});	
}

//Evento para eliminar la tarea de devolución.Se inicializa después de crear los formularios de devolución en rellenarPopUpFormularioDevol
function events_p85_eliminarDevolucion(){
	$(".formDevolucionFicheroDevolucionEliminar").click(function() {
		//Se obtiene el id del padre de la caja de eliminar devolución, que contiene el número de devolución a eliminar.
		var id = $(this).attr('id');

		$( "#p85dialog-confirmEliminarDev" ).dialog({
			resizable: false,
			height:160,
			modal: true,
			buttons: [
			          {
			        	  text:btnSi,
			        	  click: function() {
			        		  //Copia la tarea de la devolución
			        		  eliminarDevolucion(id);
			        		  $(this).dialog( "close" );
			        	  }
			          },{
			        	  text:btnNo,
			        	  click: function() {
			        		  $(this).dialog( "close" );
			        	  }
			          }
			          ]
		});	
	});	
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/*************************************************************FUNCIONES DE P85 ***************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Función que se utiliza para crear y rellenar los formularios de devoluciones del popup
function rellenarPopUpFormularioDevol(estadoCab){
	var centro = $("#centerId").val();
	var titulo1 = $("#p82_cmb_DescDevol").val();
	var codArticulo = $("#p82_cmb_referencia_b").val();

	var devolucion = new Devolucion(centro,flgHistorico,titulo1,codArticulo,estadoCab);
	var objJson = $.toJSON(devolucion);

	$.ajax({
		type : 'POST',
		url : './devoluciones/popup/loadCabeceraDevoluciones.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		async:false,
		success : function(data) {
			if(data != null){
				if(data.pCodError == 0){
					if(data.listDevolucionEstado != null){
						if(data.listDevolucionEstado.length != 0){
							if(data.listDevolucionEstado[0].listDevolucion != null){
								//Se quitan de la lista los elementos sin motivo
								var listaDevolucionesConMotivo = filtrarDevolucionesSinMotivo(data.listDevolucionEstado[0].listDevolucion);

								if (listaDevolucionesConMotivo.length != 0){
									//Se limpian los formularios anteriores del popup
									limpiarFormDevolAnteriores();

									ponerTituloPopup(estadoCab);

									//Se calcula la lista de devoluciones
									listDevolucion = listaDevolucionesConMotivo;

									//Se calculan los índices de los objetos a mostrar
									//en la lista de inicialización de popup
									largoLista = listaDevolucionesConMotivo.length;
									idxInicioLista = 0; 
									idxFinLista = listaDevolucionesConMotivo.length;

									//Si hay más de maxElementosPopup devoluciones, enseñar hasta la devolución de posición maxElementosPopup - 1
									if(idxFinLista > maxElementosPopup){
										idxFinLista = maxElementosPopup;
									}
									//Función que dibuja los formularios de devoluciones según el estado pulsado y la cantidad de devoluciones.
									dibujarFormulariosDeDevolucion(idxInicioLista,idxFinLista);
									actualizarNumeroDePagina(idxInicioLista,idxFinLista);	

									//Los eventos se colocan aquí porque cuando se carga el .js
									//los formularios todavía no están creados. Una vez creados los
									//formularios, sus ids y clases, se les puede asignar eventos.

									//Eventos apertura grid línea de devolucion
									events_p85_ficha();
									events_p85_fichaLapiz();

									//Eventos imprimir
									events_p85_imprimir();

									//Eventos cancelar
									events_p85_cancelar();

									//Eventos para finalizar tarea
									events_p85_finalizarTarea();
									
									//Eventos para duplicar
									events_p85_duplicarDevolucion();

									//Eventos para eliminar
									events_p85_eliminarDevolucion();

									//Eventos iluminación
									events_p85_iluminar_Devolucion();
									events_p85_desiluminar_Devolucion();

									//Abrir diálogo
									$("#p85_popup").dialog('open');
								}else{
									createAlert(replaceSpecialCharacters(notPosibleToReturnData), "ERROR");
								}
							}else{
								createAlert(replaceSpecialCharacters(notPosibleToReturnData), "ERROR");
							}
						}else{
							createAlert(replaceSpecialCharacters(notPosibleToReturnData), "ERROR");
						}
					}else{
						createAlert(replaceSpecialCharacters(notPosibleToReturnData), "ERROR");
					}
				}else{
					createAlert(replaceSpecialCharacters(data.pDescError), "ERROR");
				}
			}else{
				createAlert(replaceSpecialCharacters(notPosibleToReturnData), "ERROR");
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});	
}

//Función que sirve para limpiar del popup las marcas html de otros estados 
function limpiarFormDevolAnteriores(){
	$("#p85_listaFormularios").empty();
}

//Función que crea las marcas html de los formularios, decide que imágenes utilizar e inserta la información
//de las devoluciones en su lugar correspondiente.
function dibujarFormulariosDeDevolucion(idxInicioLista,idxFinLista){
	//Crear variable que guardará la devolución
	var devolucion;

	//Crear variable que guardará la estructura de la devolución
	var estructuraFormDevol;

	//Crear un bucle que dibuje las devoluciones en el popup según su estado
	for(i=idxInicioLista;i<idxFinLista;i++){

		//Obtener la devolucion a dibujar
		devolucion = listDevolucion[i];

		//Se han creado dos estructuras ocultas distintas en p85.jsp, una para plataforma y mercancia y otra
		//para abonado e incidencia al ser levemente diferentes.

		//A continuación crearemos la devolución con los id adecuados
		if(estado == estadoMercancia || estado == estadoPlataforma){

			//Se obtiene la esctructura 1 y se cambian los ids adecuados al nuevo formulario
			estructuraFormDevol = getEstructura1(i, (estado == estadoMercancia) && (devolucion.fechaDeDevolucionPasada));

			//Se inserta el formulario en su sección correspondiente del popup
			$("#p85_listaFormularios").append(estructuraFormDevol);

			//Una vez insertadas las nuevas marcas html con sus nuevos id, se inserá la información de cada devolución en su sección correspondiente
			//Además de las imágenes correspondientes (impresora, finalizar, ficha) y links correspondientes al corresponder a un estado u otro de devolución.
			showEstructura1CabeceraTituloFechas(i, devolucion);

			//Seún el estado tendrá una información, links, imágenes u otros.
			if(estado == estadoPlataforma){
				
				//Hago visible la hoja sin lapiz
				showEstructura1HojaSinLapiz(i);

				//Se inserta la impresora
				showEstructura1Impresora(i);

				//Inserta el texto de la tarea
				$("#formDevolEstructura1Tarea"+i).html("<label class='formDevolucionColorBlue formDevolucionNegrita etiquetaCampo'>" + verificarYAbonar +"</label>");	
				//$("#formDevolEstructura1Tarea"+i).text(verificarYAbonar);
			}else{
				//Se inserta la impresora. Desde la impresora se llamara a los PDF de fin de campaña o orden de retirada segun corresponda
				showEstructura1Impresora(i);




				//Inserta la caja del link y la hoja con el láìz o sin el lápiz. Si fechaHasta no es mayor que la fecha actual, se podrá finalizar la devolución y la ficha tendrá lapiz
				//pudiendo modificarse las líneas de devolución, si no, no se podrán modificar las líneas de devolución y no se podrá clicar el link.
				if(devolucion.fechaDeDevolucionPasada){

					//Inserta el texto de la tarea
					$("#formDevolEstructura1Tarea"+i).html("<label class='formDevolucionLinkRojo etiquetaCampo'>"+textoLinkFueraDeFechas+"</label>");	

					$("#formDevolEstructura1Link"+i).append("<div class='formDevolucionCajaLinkRojo'><label class='formDevolucionLinkRojo etiquetaCampo'>"+textoLinkFueraDeFechas+"</label></div>");	

					//Hago visible la hoja sin lapiz
					showEstructura1HojaSinLapiz(i);

					//Hago visible el botón de la papelera si el rol es distinto al de consulta
					if(userPerfilMisumi != CONS_CONSULTA_ROLE){
						//Hago visible las banderas de finalizar
						showEstructura1FinalizarFechaPasada(i);
					}

				}else{

					//Inserta el texto de la tarea
					$("#formDevolEstructura1Tarea"+i).html("<label class='formDevolucionColorBlue formDevolucionNegrita etiquetaCampo'>" + mercancia +"</label>");	
					//$("#formDevolEstructura1Tarea"+i).text(mercancia);	

					$("#formDevolEstructura1Link"+i).append("<div class='formDevolucionCajaLinkFinalizar'><label class='formDevolucionLinkFinalizar etiquetaCampo'>"+textoLinkFinalizar+"</label></div>");	

					//Hago visible la hoja con el lápiz
					showEstructura1HojaConLapiz(i);

					//Hago visible el botón de la papelera si el rol es distinto al de consulta
					if(userPerfilMisumi != CONS_CONSULTA_ROLE){
					//	Hago visible las banderas de finalizar
						showEstructura1Finalizar(i);
					}
				}
			}





			//Si el numeroEstructuraAColorear es distinto de nulo miramos que coincida con el número de estructura que se está dibujando
			//y en caso afirmativo, lo coloreamos.
			if(numeroEstructuraAColorear != null){
				if(i == numeroEstructuraAColorear){
					$("#formDevolEstructura1"+i).addClass("blueCard");
				}
			}




		}else{
			
			//Se obtiene la esctructura 2 y se cambian los ids adecuados al nuevo formulario
			estructuraFormDevol = getEstructura2(i);

			$("#p85_listaFormularios").append(estructuraFormDevol);

			//Una vez insertadas las nuevas marcas html con sus nuevos id, se inserá la información de cada devolución en su sección correspondiente
			//Además de las imágenes correspondientes (impresora, finalizar, ficha) y links correspondientes al corresponder a un estado u otro de devolución.
			showEstructura2CabeceraTituloFechas(i, devolucion);

			//Se inserta la hoja sin lapiz
			showEstructura2HojaSinLapiz(i);

			//Se inserta la impresora
			showEstructura2Impresora(i);

			//Seún el estado tendrá una información, links, imágenes u otros.
			if(estado == estadoAbonado){					
				$("#formDevolEstructura2AbonadoIncidencia"+i).text(abonado);
				$("#formDevolEstructura2AbonadoIncidencia"+i).addClass("formDevolucionColorGreen");
			}else{
				if(devolucion.flgFueraFechas == 1){
					$("#formDevolEstructura2AbonadoIncidencia"+i).text(textoLinkSinRealizarPorCentro);	
				}else{
					$("#formDevolEstructura2AbonadoIncidencia"+i).text(incidencia);
				}
				$("#formDevolEstructura2AbonadoIncidencia"+i).addClass("formDevolucionColorRed");

				//Cuando el estado es INCIDENCIA, y el FLG_FUERA_FECHAS es 1, entonces debe indicarse que esa devolución no la ha realizado el centro y
				//no debe mostrarse el icono de orden de recogida
				//if(devolucion.flgFueraFechas == 1){
				//$("#formDevolEstructura2Link"+i).append("<div class='formDevolucionCajaLinkRojo'><label class='formDevolucionLinkRojoSinRealizarPorCentro etiquetaCampo'>"+textoLinkSinRealizarPorCentro+"</label></div>");	

				//Se inserta la impresora
				//$("#formDevolEstructura2Impr"+i).removeClass("formDevolucionImprimirDevolucion");

				//Inserta texto orden y recogida encima y debajo de la imagen de la impresora
				//$("#formDevolEstructura2ImprImprimir"+i).text("");
				//$("#formDevolEstructura2ImprDevolucion"+i).text("");
				//}
			}

			//Si el numeroEstructuraAColorear es distinto de nulo miramos que coincida con el número de estructura que se está dibujando
			//y en caso afirmativo, lo coloreamos.
			if(numeroEstructuraAColorear != null){
				if(i == numeroEstructuraAColorear){
					$("#formDevolEstructura2"+i).addClass("blueCard");
				}
			}
		}

		//BEGIN: botones ELIMINAR y DUPLICAR
		//Aquí gestiono los botones de ELIMINAR y DUPLICAR
		
		//Compruebo si es una DEVOLUCION CREADA POR CENTRO o es una devolución de CENTRAL
		if ( (devolucion.motivo == motivoCreadaCentro) || (devolucion.titulo1.substring(0, 7)  == '(Dupli)') ){
			//Si entramos aquí es que es una DEVOLUCION CREADA POR CENTRO.
			//Entonces la devolución se puede eliminar. Mostramos el botón de la papelera.
			
			if(estado == estadoMercancia){
				//Hago visible el botón de la papelera si el rol es distinto al de consulta
				if(userPerfilMisumi != CONS_CONSULTA_ROLE){
					showEstructura1Papelera(i);
				}
			}
			else if(estado == estadoPlataforma){
				//En este estado no se muestra el botón de la papelera
			}
			else if (estado == estadoAbonado || estado == estadoIncidencia){
				//En este estado no se muestra el botón de la papelera
			}
		}
		else if( !devolucion.fechaDeDevolucionPasada ){
			//Si entramos aquí es que es una devolución de CENTRAL
			//y la fechaHasta no es mayor que la fecha actual.
			//Entonces la devolución se puede duplicar. Mostramos el botón de duplicar.
				
			if(estado == estadoMercancia){
				//En este estado no se muestra el botón duplicar
			}
			else if(estado == estadoPlataforma){
				//Hago visible el botón de la papelera si el rol es distinto al de consulta
				if(userPerfilMisumi != CONS_CONSULTA_ROLE){
					//Hago visible el botón de duplicar
					showEstructura1Duplicar(i);
				}
			}
			else if (estado == estadoAbonado || estado == estadoIncidencia){
				//Hago visible el botón de la papelera si el rol es distinto al de consulta
				if(userPerfilMisumi != CONS_CONSULTA_ROLE){
					//Hago visible el botón de duplicar
					showEstructura2Duplicar(i);
				}
			}
		}
		//END: botones ELIMINAR y DUPLICAR
	}
}

//Función que actualiza las imágenes de flechas del popup. Y el número que aparece 
//debajo de los formularios 6/11, 5/5, 12/24, etc.
function actualizarNumeroDePagina(idxInicioLista,idxFinLista){
	//Eliminar la imagen de la flecha actual
	$("#p85_pagAnterior").removeClass("p85_pagAnt");
	$("#p85_pagAnterior").removeClass("p85_pagAnt_des");

	$("#p85_pagSiguiente").removeClass("p85_pagSig");
	$("#p85_pagSiguiente").removeClass("p85_pagSig_des");

	//Añadir la flecha correspondiente
	if(idxInicioLista == 0){
		$("#p85_pagAnterior").addClass("p85_pagAnt_des");
	}else{
		$("#p85_pagAnterior").addClass("p85_pagAnt");
	}

	//Se hace +1 para saber el número de elemento que estamos obteniendo. Por ejemplo si
	//el último índice de los objetos a mostrar de la lista es el 5 equivaldría al elemento 6.
	//En el caso de que el último indice sea el 5 y la lista tenga 6 elementos, quiere decir que
	//la flecha tiene que estar invalidada, pero 5<6!!! Por eso se hace +1, para que el indice
	//equivalga al número de objeto
	if(idxFinLista < largoLista){
		$("#p85_pagSiguiente").addClass("p85_pagSig");
	}else{
		$("#p85_pagSiguiente").addClass("p85_pagSig_des");
	}

	//Actualiza el número de debajo de los formularios. 
	//Indica cuántos formularios se han mostrado y el total de 
	//formularios a mostrar.
	$("#p85_paginaActual").text(idxFinLista+"/");
	$("#p85_paginaTotal").text(largoLista);
}

//Función que sirve para paginar entre los formularios
function paginacionFormularios(flechaSig){
	var pagina = false;
	//Si se ha presionado la flecha siguiente
	if(flechaSig == 'S'){
		//Si hay más formularios de los que se ven en la página, paginar
		if((idxFinLista < largoLista) && $("#p85_pagSiguiente").hasClass("p85_pagSig")){		

			//Limpiar las devoluciones anteriores
			limpiarFormDevolAnteriores();

			//El inicio de lista se sitúa en el elemento siguiente de los elementos mostrados en la paginación
			idxInicioLista = idxFinLista;

			//Miramos si los siguientes elementos completan la lista. Si la resta del total de la lista es menor que el número de 
			//elementos a mostrar, se hasta el último elemento de la lista. Si no, se muestran los siguientes elementos.
			if((largoLista - idxInicioLista) <= maxElementosPopup){
				idxFinLista = largoLista;		
			}else{
				idxFinLista = idxInicioLista + maxElementosPopup;
			}	
			//Dibujar los formularios
			dibujarFormulariosDeDevolucion(idxInicioLista,idxFinLista);	
			actualizarNumeroDePagina(idxInicioLista,idxFinLista);	
			
			//Ponemos que ha paginado.
			pagina = true;
		}
		//Si se ha presionado la flecha anterior
	}else{
		//Si hay más formularios de los que se ven en la página, paginar
		if((idxInicioLista > 0) && $("#p85_pagAnterior").hasClass("p85_pagAnt")){		
			//Limpiar las devoluciones anteriores
			limpiarFormDevolAnteriores();

			//El inicio de lista se sitúa en el bloque de elementos a mostrar anterior
			idxFinLista = idxInicioLista;
			idxInicioLista = idxInicioLista - maxElementosPopup;

			//Dibujar los formularios
			dibujarFormulariosDeDevolucion(idxInicioLista,idxFinLista);	
			actualizarNumeroDePagina(idxInicioLista,idxFinLista);	
			
			//Ponemos que ha paginado.
			pagina = true;
		}
	}
	//Si las clases de las flechas son las de las flechas habilitadas, poner eventos, si no, no los pongas porque consigues que
	//si un usuario pincha una flecha deshabilitada, los eventos de todos los botones se dupliquen cada vez que pincha la flecha
	//deshabilitada.
	if(pagina){
		//Los eventos se colocan aquí porque cuando se pagina,
		//los formularios todavía no están creados. Una vez creados los
		//formularios, sus ids y clases, se les puede asignar eventos.
	
		//Eventos apertura grid línea de devolucion
		events_p85_ficha();
		events_p85_fichaLapiz();
	
		//Eventos imprimir
		events_p85_imprimir();
	
		//Eventos cancelar
		events_p85_cancelar();
	
		//Eventos finalizar
		events_p85_finalizarTarea();
	
		//Eventos para duplicar
		events_p85_duplicarDevolucion();
	
		//Eventos para eliminar
		events_p85_eliminarDevolucion();
	
		//Eventos iluminación
		events_p85_iluminar_Devolucion();
		events_p85_desiluminar_Devolucion();
	}
}

//Poner título al popup de formularios
function ponerTituloPopup(estadoCab){ 
	if(estadoCab == estadoMercancia){
		$( "#p85_popup" ).dialog({ title:tituloMercancia});
	}else if(estadoCab == estadoPlataforma){
		$( "#p85_popup" ).dialog({ title:tituloPlataforma});		
	}else if(estadoCab == estadoAbonado){
		$( "#p85_popup" ).dialog({ title:tituloAbonado});		
	}else if(estadoCab == estadoIncidencia){
		$( "#p85_popup" ).dialog({ title:tituloIncidencia});		
	}	
}

//Función que obtiene la devolución seleccionada al clicar la ficha del formulario y busca sus líneas de devolución para rellenar el grid.
function cargarLineaDevolucion(id){

	//Se obtienen los números del id para saber cual es el formulario seleccionado y saber la posición de la devolución en la lista
	//Ejemplo: el id es formDevolEstructura1Ficha0, se obtiene 10. 1-> Estructura de formulario utilizada 0-> Posicion de devolucion en lista
	var numeroEstructuraNumeroFormulario = id.replace(/[^0-9]/g,'');

	//Como sabemos que el primer dígito corresponde a la estructura del formulario se elimina para obtener la posición real.
	//Ejemplo: el id es formDevolEstructura1Ficha1210, se obtiene 1210. 1210-> Posicion de devolucion en lista
	var numeroFormulario = numeroEstructuraNumeroFormulario.substr(1);

	//Mirar si la ficha seleccionada tiene un lápiz, si tiene un lápiz, el grid tendrá campos editables, si no, no.
	tieneLapizLaFicha = $("#"+id).hasClass('formDevolucionFicheroDevolucionLapiz');


	//Se obtiene la devolución
	devolucionFicha = listDevolucion[numeroFormulario];
	
	//!!!!!!!!BORRAR !!!!!!!!!!!!
	//devolucionFicha.motivo = "Creada Centro"
	//devolucionFicha.motivo = "Fin de campaña";
	//devolucionFicha.motivo ="Orden Retirada";

	//Según el tipo de devolución que es, se obtendrán primero las líneas de devolución y luego se cargará 
	//el popup de orden de retirada, fin de campana o creada centro
	
	
	if(devolucionFicha.motivo.substring(0,3) == devolucionOrdenRetirada){
		cargarLineasDeDevolucionOrdenDeRetirada(devolucionFicha,tieneLapizLaFicha);
	}else{
		if ((devolucionFicha.motivo.substring(0,3) == devolucionFinCampana) || (devolucionFicha.motivo.substring(0,3) == devolucionSobrestock)) {
			cargarLineasDeDevolucionFinDeCampana(devolucionFicha,tieneLapizLaFicha);
		}else{
			cargarLineasDeDevolucionCreadaCentro(devolucionFicha,tieneLapizLaFicha);
		}
	}
}

//Función que sirve para pasar de estado de devolución. Se elimina la devolución del estado preparar mercancía, se actualiza 
//el dibujado de las devoluciones del popup y se actualiza la imágen que indica la cantidad de imágenes de la elipse de preparar
//mercancía, además, se actualizan las imágenes de las elipses de los estados abonado, incidencia o la nube plataforma, en caso
//de terminar la devolución en ese estado.
function finalizarTareaDeDevolucion(id, flgRellenarHuecos){

	//Se obtienen los números del id para saber cual es el formulario seleccionado y saber la posición de la devolución en la lista
	//Ejemplo: el id es formDevolEstructura1Ficha0, se obtiene 10. 1-> Estructura de formulario utilizada 0-> Posicion de devolucion en lista
	var numeroEstructuraNumeroFormulario = id.replace(/[^0-9]/g,'');

	//Como sabemos que el primer dígito corresponde a la estructura del formulario se elimina para obtener la posición real.
	//Ejemplo: el id es formDevolEstructura1Ficha1210, se obtiene 1210. 1210-> Posicion de devolucion en lista
	var numeroFormulario = numeroEstructuraNumeroFormulario.substr(1);

	//Se obtiene la devolución
	devolucionFicha = listDevolucion[numeroFormulario];

	var objJson = $.toJSON(devolucionFicha);
	$.ajax({
		type : 'POST',
		url : './devoluciones/popup/finalizarTareaDeDevolucion.do?flgRellenarHuecos='+flgRellenarHuecos,
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		async: false,
		cache : false,
		success : function(data) {
			// si no hay errores
			if (data.codError == 0){
				//Como se va a finalizar la tarea, no tiene sentido colorear su cabecera, pues va a desaparecer de la lista.
				numeroEstructuraAColorear = null;

				//Ponemos el flag de finalizar pulsado en S, de esta forma, a la hora de pulsar aceptar en el dialogo de mensajes, sabemos
				//que el dialogo es porque se ha finalizado una devolución y no por cualquier otra cosa.
				$("#p85_fld_finalizarPulsado").val('S');

				//Realizamos la copia de la copia de la lista de devolucion. Esto es necesario, porque después de finalizar
				//queremos imprimir la orden de recogida de esa devolución. Como al finalizar la deovlución la devolución se borra
				//de la lista listDevolucion, nos sería imposible acceder a su devolución, y por eso se copia. Además grabamos la posición
				//de la lista de devolución.
				listDevolucionImprimir = listDevolucion.slice(0);
				idDevolucionImprimir = id;

				//Si la devolución ha cambiado de estado correctamente, eliminamos la devolución de la lista de devoluciones.
				listDevolucion.splice(numeroFormulario,1);

				//Si la lista sigue teniendo elementos
				if(listDevolucion.length > 0){
					//Se calculan los índices de los objetos a mostrar
					//en la lista de inicialización de popup
					largoLista = listDevolucion.length;
					idxInicioLista = 0; 
					idxFinLista = listDevolucion.length;

					//Si hay más de maxElementosPopup devoluciones, enseñar hasta la devolución de posición maxElementosPopup - 1
					if(idxFinLista > maxElementosPopup){
						idxFinLista = maxElementosPopup;
					}

					//Se limpian los formularios anteriores del popup
					limpiarFormDevolAnteriores();

					//Función que dibuja los formularios de devoluciones según el estado pulsado y la cantidad de devoluciones.
					dibujarFormulariosDeDevolucion(idxInicioLista,idxFinLista);
					actualizarNumeroDePagina(idxInicioLista,idxFinLista);	

					//Los eventos se colocan aquí porque cuando se carga el .js
					//los formularios todavía no están creados. Una vez creados los
					//formularios, sus ids y clases, se les puede asignar eventos.

					//Eventos apertura grid línea de devolucion
					events_p85_ficha();
					events_p85_fichaLapiz();

					//Eventos imprimir
					events_p85_imprimir();

					//Eventos cancelar
					events_p85_cancelar();

					//Eventos para finalizar tarea
					events_p85_finalizarTarea();

					//Eventos para duplicar
					events_p85_duplicarDevolucion();

					//Eventos para eliminar
					events_p85_eliminarDevolucion();

					//Eventos de iluminación
					events_p85_iluminar_Devolucion();
					events_p85_desiluminar_Devolucion();

					devolucionesFinalizadas = true;

					//Si la devolución se finaliza correctamente
					createAlert(replaceSpecialCharacters(devolucionFinalizadaCorrectamente), "INFO");
				}else{
					//Si la lista no tiene elementos
					createAlert(replaceSpecialCharacters(noHayElementosDeDevoluciones), "INFO");

					//Dibujamos los nuevos estados de las devoluciones.
					dibujarDevoluciones();

					$("#p85_popup").dialog('close');										
				}
				getAvisosCentro();
			}else{
				createAlert(replaceSpecialCharacters(data.msgError), "ERROR");
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});		
}

//Función que sirve para duplicar una devolución. Se elimina la devolución del estado preparar mercancía, se actualiza 
//el dibujado de las devoluciones del popup y se actualiza la imágen que indica la cantidad de imágenes de la elipse de preparar
//mercancía, además, se actualizan las imágenes de las elipses de los estados abonado, incidencia o la nube plataforma, en caso
//de terminar la devolución en ese estado.
function duplicarDevolucion(id){

	//Se obtienen los números del id para saber cual es el formulario seleccionado y saber la posición de la devolución en la lista
	//Ejemplo: el id es formDevolEstructura1Ficha0, se obtiene 10. 1-> Estructura de formulario utilizada 0-> Posicion de devolucion en lista
	var numeroEstructuraNumeroFormulario = id.replace(/[^0-9]/g,'');

	//Como sabemos que el primer dígito corresponde a la estructura del formulario se elimina para obtener la posición real.
	//Ejemplo: el id es formDevolEstructura1Ficha1210, se obtiene 1210. 1210-> Posicion de devolucion en lista
	var numeroFormulario = numeroEstructuraNumeroFormulario.substr(1);

	//Se obtiene la devolución
	devolucionFicha = listDevolucion[numeroFormulario];

	var objJson = $.toJSON(devolucionFicha);
	$.ajax({
		type : 'POST',
		url : './devoluciones/popup/duplicarDevolucion.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		async: false,
		cache : false,
		success : function(data) {
			if(data.codError == 0){
				//Como se va a duplicar la tarea, no tiene sentido colorear su cabecera.
				numeroEstructuraAColorear = null;

				//Si la lista sigue teniendo elementos
				if(listDevolucion.length > 0){
					//Se calculan los índices de los objetos a mostrar
					//en la lista de inicialización de popup
					largoLista = listDevolucion.length;
					idxInicioLista = 0; 
					idxFinLista = listDevolucion.length;

					//Si hay más de maxElementosPopup devoluciones, enseñar hasta la devolución de posición maxElementosPopup - 1
					if(idxFinLista > maxElementosPopup){
						idxFinLista = maxElementosPopup;
					}

					//Se limpian los formularios anteriores del popup
					limpiarFormDevolAnteriores();

					//Función que dibuja los formularios de devoluciones según el estado pulsado y la cantidad de devoluciones.
					dibujarFormulariosDeDevolucion(idxInicioLista,idxFinLista);
					actualizarNumeroDePagina(idxInicioLista,idxFinLista);	

					//Los eventos se colocan aquí porque cuando se carga el .js
					//los formularios todavía no están creados. Una vez creados los
					//formularios, sus ids y clases, se les puede asignar eventos.

					//Eventos apertura grid línea de devolucion
					events_p85_ficha();
					events_p85_fichaLapiz();

					//Eventos imprimir
					events_p85_imprimir();

					//Eventos cancelar
					events_p85_cancelar();

					//Eventos para finalizar tarea
					events_p85_finalizarTarea();

					//Eventos para duplicar
					events_p85_duplicarDevolucion();

					//Eventos para eliminar
					events_p85_eliminarDevolucion();

					//Eventos de iluminación
					events_p85_iluminar_Devolucion();
					events_p85_desiluminar_Devolucion();

					devolucionesDuplicadas = true;

					//Si la devolución se ha duplicado correctamente
					createAlert(replaceSpecialCharacters(devolucionDuplicadaCorrectamente), "INFO");
				}
				getAvisosCentro();
			}else{
				createAlert(replaceSpecialCharacters(data.descError), "ERROR");
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});		
}

//Función que sirve para eliminar una devolución. Se elimina la devolución del estado preparar mercancía, se actualiza 
//el dibujado de las devoluciones del popup y se actualiza la imágen que indica la cantidad de imágenes de la elipse de preparar
//mercancía, además, se actualizan las imágenes de las elipses de los estados abonado, incidencia o la nube plataforma, en caso
//de terminar la devolución en ese estado.
function eliminarDevolucion(id){

	//Se obtienen los números del id para saber cual es el formulario seleccionado y saber la posición de la devolución en la lista
	//Ejemplo: el id es formDevolEstructura1Ficha0, se obtiene 10. 1-> Estructura de formulario utilizada 0-> Posicion de devolucion en lista
	var numeroEstructuraNumeroFormulario = id.replace(/[^0-9]/g,'');

	//Como sabemos que el primer dígito corresponde a la estructura del formulario se elimina para obtener la posición real.
	//Ejemplo: el id es formDevolEstructura1Ficha1210, se obtiene 1210. 1210-> Posicion de devolucion en lista
	var numeroFormulario = numeroEstructuraNumeroFormulario.substr(1);

	//Se obtiene la devolución
	devolucionFicha = listDevolucion[numeroFormulario];

	var objJson = $.toJSON(devolucionFicha);
	$.ajax({
		type : 'POST',
		url : './devoluciones/popup/eliminarDevolucion.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		async: false,
		cache : false,
		success : function(data) {
			if(data.codError == 0){
				//Como se va a eliminar la tarea, no tiene sentido colorear su cabecera, pues va a desaparecer de la lista.
				numeroEstructuraAColorear = null;

				//Si la devolución se ha eliminado correctamente, eliminamos la devolución de la lista de devoluciones.
				listDevolucion.splice(numeroFormulario,1);

				//Si la lista sigue teniendo elementos
				if(listDevolucion.length > 0){
					//Se calculan los índices de los objetos a mostrar
					//en la lista de inicialización de popup
					largoLista = listDevolucion.length;
					idxInicioLista = 0; 
					idxFinLista = listDevolucion.length;

					//Si hay más de maxElementosPopup devoluciones, enseñar hasta la devolución de posición maxElementosPopup - 1
					if(idxFinLista > maxElementosPopup){
						idxFinLista = maxElementosPopup;
					}

					//Se limpian los formularios anteriores del popup
					limpiarFormDevolAnteriores();

					//Función que dibuja los formularios de devoluciones según el estado pulsado y la cantidad de devoluciones.
					dibujarFormulariosDeDevolucion(idxInicioLista,idxFinLista);
					actualizarNumeroDePagina(idxInicioLista,idxFinLista);	

					//Los eventos se colocan aquí porque cuando se carga el .js
					//los formularios todavía no están creados. Una vez creados los
					//formularios, sus ids y clases, se les puede asignar eventos.

					//Eventos apertura grid línea de devolucion
					events_p85_ficha();
					events_p85_fichaLapiz();

					//Eventos imprimir
					events_p85_imprimir();

					//Eventos cancelar
					events_p85_cancelar();

					//Eventos para finalizar tarea
					events_p85_finalizarTarea();

					//Eventos para duplicar
					events_p85_duplicarDevolucion();

					//Eventos para eliminar
					events_p85_eliminarDevolucion();

					//Eventos de iluminación
					events_p85_iluminar_Devolucion();
					events_p85_desiluminar_Devolucion();

					devolucionesEliminadas = true;

					//Si la devolución se ha eliminado correctamente
					createAlert(replaceSpecialCharacters(devolucionEliminadaCorrectamente), "INFO");
				}else{
					//Si la lista no tiene elementos
					createAlert(replaceSpecialCharacters(devolucionEliminadaCorrectamente), "INFO");

					//Dibujamos los nuevos estados de las devoluciones.
					dibujarDevoluciones();

					$("#p85_popup").dialog('close');										
				}
				getAvisosCentro();
			}else{
				createAlert(replaceSpecialCharacters(data.descError), "ERROR");
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});		
}

//Función que sirve para saber si existen stocks vacíos en una devolución. Devuelve 'S' si existen stocks vacíos para esa devolución y 'N'
//si están llenos.
function finalizarObtenerRellenarHuecos(id){
	var rellenarHuecos = "N";

	//Se obtienen los números del id para saber cual es el formulario seleccionado y saber la posición de la devolución en la lista
	//Ejemplo: el id es formDevolEstructura1Ficha0, se obtiene 10. 1-> Estructura de formulario utilizada 0-> Posicion de devolucion en lista
	var numeroEstructuraNumeroFormulario = id.replace(/[^0-9]/g,'');

	//Como sabemos que el primer dígito corresponde a la estructura del formulario se elimina para obtener la posición real.
	//Ejemplo: el id es formDevolEstructura1Ficha1210, se obtiene 1210. 1210-> Posicion de devolucion en lista
	var numeroFormulario = numeroEstructuraNumeroFormulario.substr(1);

	//Se obtiene la devolución
	devolucionFicha = listDevolucion[numeroFormulario];

	var objJson = $.toJSON(devolucionFicha);
	$.ajax({
		type : 'POST',
		url : './devoluciones/popup/finalizarObtenerRellenarHuecos.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		async: false,
		cache : false,
		success : function(data) {
			rellenarHuecos = data;
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
			rellenarHuecos = "ERROR";
		}	
	});	
	return rellenarHuecos;
}

//Función que elimina de la lista de devoluciones las devoluciones sin motivo.Puede darse la situación
//de que la imágen de las nube/elipses no coincida con el número de devoluciones del popup de devoluciones.
//Esto se debe a que al dibujar la imágen no se hace el filtrado de las devoluciones sin motivo, debido
//a que la llamada del plsql sólo devuelve el número de devoluciones, sin la lista de devoluciones.
//Después de hablarlo con Usoa, se ha decidido que en esos casos no cuadre la imágen con la cantidad
//de devoluciones del popup, de este modo se darán cuenta de que no han puesto el motivo (que debería ser obligatorio)
function filtrarDevolucionesSinMotivo(listaDevolucionSinFiltrar){
	var motivo = null;
	var listaFiltrada = new Array();

	//Por cada devolución miramos su motivo, si es nulo o vacío no lo mostramos
	for(var i = 0;i<listaDevolucionSinFiltrar.length;i++){
		motivo = listaDevolucionSinFiltrar[i].motivo;
		if(motivo != null && motivo != ""){
			listaFiltrada.push(listaDevolucionSinFiltrar[i]);
		}
	}
	return listaFiltrada;
}

function imprimirDevolucionesOrdenRecogida(id){

	//Se obtienen los números del id para saber cual es el formulario seleccionado y saber la posición de la devolución en la lista
	//Ejemplo: el id es formDevolEstructura1Ficha0, se obtiene 10. 1-> Estructura de formulario utilizada 0-> Posicion de devolucion en lista
	var numeroEstructuraNumeroFormulario = id.replace(/[^0-9]/g,'');

	//Como sabemos que el primer dígito corresponde a la estructura del formulario se elimina para obtener la posición real.
	//Ejemplo: el id es formDevolEstructura1Ficha1210, se obtiene 1210. 1210-> Posicion de devolucion en lista
	var numeroFormulario = numeroEstructuraNumeroFormulario.substr(1);

	//Se obtiene la devolución
	devolucionFicha = listDevolucion[numeroFormulario];

	var url = '';

	if(estado == estadoMercancia){ 
		//si el estado es PREPARAR MERCANCIA. Se llama a los PDFs de Listados de Referencia.

		//Según el tipo de devolución que es, se cargará el pdf de orden de retirada o fin de campana
		if(devolucionFicha.motivo.substring(0,3) == devolucionOrdenRetirada){
			url = './p95ListadoDevoluciones/getPdfDevolucionOrdenRetirada.do?localizador='+devolucionFicha.localizador+'&flgHistorico='+flgHistorico;
		}else{
			if ((devolucionFicha.motivo.substring(0,3) == devolucionFinCampana) || (devolucionFicha.motivo.substring(0,3) == devolucionSobrestock)) {
				url = './p95ListadoDevoluciones/getPdfDevolucionFinCampana.do?localizador='+devolucionFicha.localizador+'&flgHistorico='+flgHistorico;
			}else{
				url = './p95ListadoDevoluciones/getPdfDevolucionCreadaCentro.do?localizador='+devolucionFicha.localizador+'&flgHistorico='+flgHistorico;
			}
		}

	} else {
		url = './p95ListadoDevoluciones/getPdfDevolucionOrdenRecogida.do?localizador='+devolucionFicha.localizador+'&flgHistorico='+flgHistorico;
	}

	window.open(url,"_blank");
}

function imprimirDevolucionesOrdenRecogidaTrasFinalizar(){

	//Se obtienen los números del id para saber cual es el formulario seleccionado y saber la posición de la devolución en la lista
	//Ejemplo: el id es formDevolEstructura1Ficha0, se obtiene 10. 1-> Estructura de formulario utilizada 0-> Posicion de devolucion en lista
	var numeroEstructuraNumeroFormulario = idDevolucionImprimir.replace(/[^0-9]/g,'');

	//Como sabemos que el primer dígito corresponde a la estructura del formulario se elimina para obtener la posición real.
	//Ejemplo: el id es formDevolEstructura1Ficha1210, se obtiene 1210. 1210-> Posicion de devolucion en lista
	var numeroFormulario = numeroEstructuraNumeroFormulario.substr(1);

	//Se obtiene la devolución
	devolucionFicha = listDevolucionImprimir[numeroFormulario];

	let tipoPDF=null;
	let url='';
	
	//si el estado es PLATAFORMA o ABONADO o INCIDENCIA siempre se llama a Orden de recogida
	if (devolucionFicha.recogida=="Proveedor"){
		tipoPDF = "1"; // incorpora el código EAN en formato CODE39.
		url = './p95ListadoDevoluciones/getPdfDevolucionOrdenRecogida.do?localizador='+devolucionFicha.localizador+'&flgHistorico='+flgHistorico+'&tipoPDF=1';
	}else{
		tipoPDF = "0";
		url = './p95ListadoDevoluciones/getPdfDevolucionOrdenRecogida.do?localizador='+devolucionFicha.localizador+'&flgHistorico='+flgHistorico+'&tipoPDF=0';
	}
	
	window.open(url,"_blank");
}

function getEstructura1(indice, esMercanciaConFechaDeDevolucionPasada){
	//Se obtiene la esctructura 1 y se cambian los ids adecuados al nuevo formulario
	var estructura1 = $("#formDevolEstructura1").prop('outerHTML');
	estructura1 = estructura1.replace("formDevolEstructura1","formDevolEstructura1"+indice);
	estructura1 = estructura1.replace("formDevolEstructura1Titulo1","formDevolEstructura1Titulo1"+indice);
	estructura1 = estructura1.replace("formDevolucionPeriodoFechas1","formDevolucionPeriodoFechas1"+indice);
	estructura1 = estructura1.replace("formDevolEstructura1FechaDesde","formDevolEstructura1FechaDesde"+indice);
	estructura1 = estructura1.replace("formDevolEstructura1FechaHasta","formDevolEstructura1FechaHasta"+indice);
	estructura1 = estructura1.replace("formDevolucionPeriodoMotivo1","formDevolucionPeriodoMotivo1"+indice);
	estructura1 = estructura1.replace("formDevolEstructura1Motivo","formDevolEstructura1Motivo"+indice);
	
	estructura1 = estructura1.replace("formDevolEstructura1Tarea","formDevolEstructura1Tarea"+indice);

	if (esMercanciaConFechaDeDevolucionPasada) {
		estructura1 = estructura1.replace("formDevolucionBloque30o50","formDevolucionBloque30");
		estructura1 = estructura1.replace("formDevolucionBloque70o50","formDevolucionBloque70");
	} else {
		estructura1 = estructura1.replace("formDevolucionBloque30o50","formDevolucionBloque50");
		estructura1 = estructura1.replace("formDevolucionBloque70o50","formDevolucionBloque50");
	}

	estructura1 = estructura1.replace("formDevolEstructura1Link","formDevolEstructura1Link"+indice);

	estructura1 = estructura1.replace("formDevolucionEstructura1DivBorrar","formDevolucionEstructura1DivBorrar"+indice);
	estructura1 = estructura1.replace("formDevolEstructura1BorrarDev","formDevolEstructura1BorrarDev"+indice);
	estructura1 = estructura1.replace(new RegExp("\\bformDevolEstructura1Borrar\\b"),"formDevolEstructura1Borrar"+indice);
	estructura1 = estructura1.replace("formDevolEstructura1BorrarDevolucion","formDevolEstructura1BorrarDevolucion"+indice);
	estructura1 = estructura1.replace("formDevolEstructura1BorrarLink","formDevolEstructura1BorrarLink"+indice);

	estructura1 = estructura1.replace("formDevolucionEstructura1DivDuplicar","formDevolucionEstructura1DivDuplicar"+indice);
	estructura1 = estructura1.replace("formDevolEstructura1DuplicarDev","formDevolEstructura1DuplicarDev"+indice);
	estructura1 = estructura1.replace(new RegExp("\\bformDevolEstructura1Duplicar\\b"),"formDevolEstructura1Duplicar"+indice);
	estructura1 = estructura1.replace("formDevolEstructura1DuplicarDevolucion","formDevolEstructura1DuplicarDevolucion"+indice);
	estructura1 = estructura1.replace("formDevolEstructura1DuplicarLink","formDevolEstructura1DuplicarLink"+indice);
	
	estructura1 = estructura1.replace("formDevolEstructura1FichaCons","formDevolEstructura1FichaCons"+indice);
	estructura1 = estructura1.replace(new RegExp("\\bformDevolEstructura1Ficha\\b"),"formDevolEstructura1Ficha"+indice);
	estructura1 = estructura1.replace(new RegExp("\\bformDevolEstructura1FichaLapiz\\b"),"formDevolEstructura1FichaLapiz"+indice);
	estructura1 = estructura1.replace("formDevolEstructura1FichaCantidad","formDevolEstructura1FichaCantidad"+indice);
	estructura1 = estructura1.replace("formDevolEstructura1FichaLink","formDevolEstructura1FichaLink"+indice);
	estructura1 = estructura1.replace("formDevolEstructura1FichaLapizLink","formDevolEstructura1FichaLapizLink"+indice);

	estructura1 = estructura1.replace("formDevolEstructura1ImprImprimir","formDevolEstructura1ImprImprimir"+indice);
	//Como existen formEstructura1ImprOrden y formEstructura1ImprRecogida y contienen formDevolEstructura1Impr no quiero
	//que los reemplace, por eso utilizo una expresión regular que indica que cambie exactamente esa palabra.
	estructura1 = estructura1.replace(new RegExp("\\bformDevolEstructura1Impr\\b"),"formDevolEstructura1Impr"+indice);
	estructura1 = estructura1.replace("formDevolEstructura1ImprDevolucion","formDevolEstructura1ImprDevolucion"+indice);
	estructura1 = estructura1.replace("formDevolEstructura1ImprLink","formDevolEstructura1ImprLink"+indice);
	
	estructura1 = estructura1.replace("formDevolucionEstructura1DivBanderaFin","formDevolucionEstructura1DivBanderaFin"+indice);
	estructura1 = estructura1.replace(new RegExp("\\bformDevolEstructura1Bandera\\b"),"formDevolEstructura1Bandera"+indice);
	estructura1 = estructura1.replace("formDevolEstructura1BanderaFin","formDevolEstructura1BanderaFin"+indice);
	estructura1 = estructura1.replace("formDevolEstructura1BanderaTarea","formDevolEstructura1BanderaTarea"+indice);
	estructura1 = estructura1.replace("formDevolEstructura1BanderaLink","formDevolEstructura1BanderaLink"+indice);
	
	return estructura1;
}

function getEstructura2(indice){
	//Se obtiene la esctructura 2 y se cambian los ids adecuados al nuevo formulario
	var estructura2 = $("#formDevolEstructura2").prop('outerHTML');
	estructura2 = estructura2.replace("formDevolEstructura2","formDevolEstructura2"+indice);
	estructura2 = estructura2.replace("formDevolEstructura2Titulo1","formDevolEstructura2Titulo1"+indice);
	estructura2 = estructura2.replace("formDevolucionPeriodoFechas2","formDevolucionPeriodoFechas2"+indice);
	estructura2 = estructura2.replace("formDevolEstructura2FechaDesde","formDevolEstructura2FechaDesde"+indice);
	estructura2 = estructura2.replace("formDevolEstructura2FechaHasta","formDevolEstructura2FechaHasta"+indice);
	estructura2 = estructura2.replace("formDevolEstructura2AbonadoIncidencia","formDevolEstructura2AbonadoIncidencia"+indice);
	estructura2 = estructura2.replace("formDevolEstructura2Link","formDevolEstructura2Link"+indice);
	estructura2 = estructura2.replace("formDevolucionPeriodoMotivo2","formDevolucionPeriodoMotivo2"+indice);
	estructura2 = estructura2.replace("formDevolEstructura2Motivo","formDevolEstructura2Motivo"+indice);

	estructura2 = estructura2.replace("formDevolucionEstructura2DivDuplicar","formDevolucionEstructura2DivDuplicar"+indice);
	estructura2 = estructura2.replace("formDevolEstructura2DuplicarDev","formDevolEstructura2DuplicarDev"+indice);
	estructura2 = estructura2.replace(new RegExp("\\bformDevolEstructura2Duplicar\\b"),"formDevolEstructura2Duplicar"+indice);
	estructura2 = estructura2.replace("formDevolEstructura2DuplicarDevolucion","formDevolEstructura2DuplicarDevolucion"+indice);
	estructura2 = estructura2.replace("formDevolEstructura2DuplicarLink","formDevolEstructura2DuplicarLink"+indice);
		
	estructura2 = estructura2.replace("formDevolEstructura2FichaCons","formDevolEstructura2FichaCons"+indice);
	estructura2 = estructura2.replace(new RegExp("\\bformDevolEstructura2Ficha\\b"),"formDevolEstructura2Ficha"+indice);
	estructura2 = estructura2.replace("formDevolEstructura2FichaCantidad","formDevolEstructura2FichaCantidad"+indice);
	estructura2 = estructura2.replace("formDevolEstructura2FichaLink","formDevolEstructura2FichaLink"+indice);
	
	estructura2 = estructura2.replace("formDevolEstructura2ImprImprimir","formDevolEstructura2ImprImprimir"+indice);
	estructura2 = estructura2.replace(new RegExp("\\bformDevolEstructura2Impr\\b"),"formDevolEstructura2Impr"+indice);
	estructura2 = estructura2.replace("formDevolEstructura2ImprDevolucion","formDevolEstructura2ImprDevolucion"+indice);
	estructura2 = estructura2.replace("formDevolEstructura2ImprLink","formDevolEstructura2ImprLink"+indice);
	
	return estructura2;
}

function showEstructura1CabeceraTituloFechas(indice, devolucion){
	
	$("#formDevolEstructura1Titulo1"+indice).text(devolucion.localizador + "-" + devolucion.titulo1);
	if ( ($.isEmptyObject( devolucion.fechaDesdeStr ) && $.isEmptyObject( devolucion.fechaHastaStr)) || (devolucion.motivo == motivoCreadaCentro) ){
		//Oculto las fechas de la cabecera
		if ( !$("#formDevolucionPeriodoFechas1"+indice).hasClass("formDevolucionCeldaOculto") ){
			$("#formDevolucionPeriodoFechas1"+indice).addClass("formDevolucionCeldaOculto");
		}
		
		//Hago visible el motivo
		if ( $("#formDevolucionPeriodoMotivo1"+indice).hasClass("formDevolucionCeldaOculto") ){
			$("#formDevolucionPeriodoMotivo1"+indice).removeClass("formDevolucionCeldaOculto");
		}
		
		//Inserta texto cons/modi y cantidad encima y debajo de la ficha
		$("#formDevolEstructura1Motivo"+indice).text((devolucion.motivo).toUpperCase());
	}else{
		//Oculto el motivo de la cabecera
		if ( !$("#formDevolucionPeriodoMotivo1"+indice).hasClass("formDevolucionCeldaOculto") ){
			$("#formDevolucionPeriodoMotivo1"+indice).addClass("formDevolucionCeldaOculto");
		}
		
		//Hago visible las fechas
		if ( $("#formDevolucionPeriodoFechas1"+indice).hasClass("formDevolucionCeldaOculto") ){
			$("#formDevolucionPeriodoFechas1"+indice).removeClass("formDevolucionCeldaOculto");
		}
		
		//Inserta texto cons/modi y cantidad encima y debajo de la ficha
		$("#formDevolEstructura1FechaDesde"+indice).text(devolucion.fechaDesdeStr);
		$("#formDevolEstructura1FechaHasta"+indice).text(devolucion.fechaHastaStr);
	}
	
}

function showEstructura2CabeceraTituloFechas(indice, devolucion){
	
	$("#formDevolEstructura2Titulo1"+indice).text(devolucion.localizador + "-" + devolucion.titulo1);
	if ( ($.isEmptyObject( devolucion.fechaDesdeStr ) && $.isEmptyObject( devolucion.fechaHastaStr)) || (devolucion.motivo == motivoCreadaCentro) ){
		//Oculto las fechas de la cabecera
		if ( !$("#formDevolucionPeriodoFechas2"+indice).hasClass("formDevolucionCeldaOculto") ){
			$("#formDevolucionPeriodoFechas2"+indice).addClass("formDevolucionCeldaOculto");
		}
		
		//Hago visible el motivo
		if ( $("#formDevolucionPeriodoMotivo2"+indice).hasClass("formDevolucionCeldaOculto") ){
			$("#formDevolucionPeriodoMotivo2"+indice).removeClass("formDevolucionCeldaOculto");
		}
		
		//Inserta texto cons/modi y cantidad encima y debajo de la ficha
		$("#formDevolEstructura2Motivo"+indice).text((devolucion.motivo).toUpperCase());
	}else{
		//Oculto el motivo de la cabecera
		if ( !$("#formDevolucionPeriodoMotivo2"+indice).hasClass("formDevolucionCeldaOculto") ){
			$("#formDevolucionPeriodoMotivo2"+indice).addClass("formDevolucionCeldaOculto");
		}
		
		//Hago visible las fechas
		if ( $("#formDevolucionPeriodoFechas2"+indice).hasClass("formDevolucionCeldaOculto") ){
			$("#formDevolucionPeriodoFechas2"+indice).removeClass("formDevolucionCeldaOculto");
		}
		
		//Inserta texto cons/modi y cantidad encima y debajo de la ficha
		$("#formDevolEstructura2FechaDesde"+indice).text(devolucion.fechaDesdeStr);
		$("#formDevolEstructura2FechaHasta"+indice).text(devolucion.fechaHastaStr);
	}
	
}

function showEstructura1HojaSinLapiz(indice){
	//Oculto la hoja con el lápiz
	if ( !$("#formDevolEstructura1FichaLapiz"+indice).hasClass("formDevolucionBloque33oculto") ){
		$("#formDevolEstructura1FichaLapiz"+indice).addClass("formDevolucionBloque33oculto");
	}
	
	//Hago visible la hoja sin lapiz
	if ( $("#formDevolEstructura1Ficha"+indice).hasClass("formDevolucionBloque33oculto") ){
		$("#formDevolEstructura1Ficha"+indice).removeClass("formDevolucionBloque33oculto");
	}
	
	//Se inseta la hoja sin lapiz
	$("#formDevolEstructura1FichaLink"+indice).addClass("formDevolucionFicheroDevolucion");

	$("#formDevolEstructura1Ficha"+indice).prop('title',tooltipConsulta);

	//Inserta texto cons/modi y cantidad encima y debajo de la ficha
	$("#formDevolEstructura1FichaCons"+indice).text(consulta);
	$("#formDevolEstructura1FichaCantidad"+indice).text(cantidad);
}

function showEstructura1Impresora(indice){
	//Hago visible la impresora
	$("#formDevolEstructura1ImprLink"+indice).addClass("formDevolucionImprimirDevolucion");

	//Inserta texto Imprimir y devol. encima y debajo de la imagen de la impresora
	$("#formDevolEstructura1ImprImprimir"+indice).text(imprimir);
	$("#formDevolEstructura1ImprDevolucion"+indice).text(devol);
}

function showEstructura1Finalizar(indice){
	//Hago visible las banderas de finalizar
	$("#formDevolucionEstructura1DivBanderaFin"+indice).removeClass("formDevolucionBloque33oculto");
	
	//Se insertan las banderas de finalizar. Desde las banderas se llamara a la accion de Finalizar la devolucion
	$("#formDevolEstructura1BanderaLink"+indice).addClass("formDevolucionBanderasFinalizar");

	//Inserta texto Fin y tarea encima y debajo de la imagen de las banderas
	$("#formDevolEstructura1BanderaFin"+indice).text(fin);
	$("#formDevolEstructura1BanderaTarea"+indice).text(tarea);
}

function showEstructura1FinalizarFechaPasada(indice){
	//Hago visible las banderas de finalizar
	$("#formDevolucionEstructura1DivBanderaFin"+indice).removeClass("formDevolucionBloque33oculto");
	
	//Se insertan las banderas de finalizar. Desde las banderas se llamara a la accion de Finalizar la devolucion
	$("#formDevolEstructura1BanderaLink"+indice).addClass("formDevolucionBanderasFinalizarFechaPasada");

	//Inserta texto Fin y tarea encima y debajo de la imagen de las banderas
	$("#formDevolEstructura1BanderaFin"+indice).text(fin);
	$("#formDevolEstructura1BanderaTarea"+indice).text(tarea);
}

function showEstructura1HojaConLapiz(indice){
	//Oculto la hoja con el lápiz
	if ( !$("#formDevolEstructura1Ficha"+indice).hasClass("formDevolucionBloque33oculto") ){
		$("#formDevolEstructura1Ficha"+indice).addClass("formDevolucionBloque33oculto");
	}
	
	//Hago visible la hoja sin lapiz
	if ( $("#formDevolEstructura1FichaLapiz"+indice).hasClass("formDevolucionBloque33oculto") ){
		$("#formDevolEstructura1FichaLapiz"+indice).removeClass("formDevolucionBloque33oculto");
	}
	
	//Se inserta la hoja con el lápiz
	$("#formDevolEstructura1FichaLapizLink"+indice).addClass("formDevolucionFicheroDevolucionLapiz");

	$("#formDevolEstructura1Ficha"+indice).prop('title',tooltipModificacion);

	//Inserta texto cons/modi y cantidad encima y debajo de la ficha
	$("#formDevolEstructura1FichaCons"+indice).text(modificacion);
	$("#formDevolEstructura1FichaCantidad"+indice).text(cantidad);
}

function showEstructura1Papelera(indice){
	if ( $("#p85_37_eliminar_devoluciones").val() == "true" ){
		//Hago visible el botón de la papelera
		$("#formDevolucionEstructura1DivBorrar"+indice).removeClass("formDevolucionBloque33oculto");
		
		//Se inserta la papelera
		$("#formDevolEstructura1BorrarLink"+indice).addClass("formDevolucionFicheroDevolucionEliminar");

		//Inserta texto Eliminar encima de la imagen de la papelera
		$("#formDevolEstructura1BorrarDevolucion"+indice).text(devolucionTxtP86);
		
		//Inserta texto Eliminar encima de la imagen de la papelera
		$("#formDevolEstructura1BorrarDev"+indice).text(eliminar);
	}
}

function showEstructura1Duplicar(indice){
	if ( $("#p85_36_duplicar_devoluciones").val() == "true" ){
		//Hago visible el botón de duplicar
		$("#formDevolucionEstructura1DivDuplicar"+indice).removeClass("formDevolucionBloque33oculto");
		
		//Se inserta el botón de duplicar
		$("#formDevolEstructura1DuplicarLink"+indice).addClass("formDevolucionFicheroDevolucionDuplicar");

		//Inserta texto Duplicar y devol. encima de la imagen del botón de duplicar
		$("#formDevolEstructura1DuplicarDev"+indice).text(duplicar);
		$("#formDevolEstructura1DuplicarDevolucion"+indice).text(devol);
	}
}

function showEstructura2HojaSinLapiz(indice){
	//Se inserta la hoja sin lapiz
	$("#formDevolEstructura2FichaLink"+indice).addClass("formDevolucionFicheroDevolucion");	

	$("#formDevolEstructura2Ficha"+indice).prop('title',tooltipConsulta);

	//Inserta texto cons/modi y cantidad encima y debajo de la ficha
	$("#formDevolEstructura2FichaCons"+indice).text(consulta);
	$("#formDevolEstructura2FichaCantidad"+indice).text(cantidad);
}

function showEstructura2Impresora(indice){
	//Se inserta la impresora
	$("#formDevolEstructura2ImprLink"+indice).addClass("formDevolucionImprimirDevolucion");

	//Inserta texto Imprimir y devol. encima y debajo de la imagen de la impresora
	$("#formDevolEstructura2ImprImprimir"+indice).text(imprimir);
	$("#formDevolEstructura2ImprDevolucion"+indice).text(devol);
}

function showEstructura2Duplicar(indice){
	if ( $("#p85_36_duplicar_devoluciones").val() == "true" ){
		//Hago visible el botón de duplicar
		$("#formDevolucionEstructura2DivDuplicar"+indice).removeClass("formDevolucionBloque33oculto");
		
		//Se inserta el botón de duplicar
		$("#formDevolEstructura2DuplicarLink"+indice).addClass("formDevolucionFicheroDevolucionDuplicar");

		//Inserta texto Duplicar encima de la imagen del botón de duplicar
		$("#formDevolEstructura2DuplicarDev"+indice).text(duplicar);
		$("#formDevolEstructura2DuplicarDevolucion"+indice).text(devol);
	}
}
