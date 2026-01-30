/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/****************************************************************** CONSTANTES ***************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Constante que indica el número de elementos máximo a mostrar por paginación. Cambiar si se quiere mostrar menos formularios
var maxElementosPopup = 6;

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/**************************************************************** VARIABLES GLOBALES ******************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
//Guarda la lista de entradas.
var lstEntradaGlobal;

//Variables necesarias para imprimir la entrada después de finalizar una entrada.
var lstEntradaImprimir = null;
var idEntradaImprimir = null;

//Si hay alguna entrada finalizada, al cerrar el popup se ejecuta dibujarEntradas()
var entradasFinalizadas = false;

//Guarda el id de la estructura a colorear.

var numeroEstructuraAColorear = null;
//DEL JSON DEL FICHERO

//Títulos popup general
var tituloDarEntrada;
var tituloEntradaConfirmada;

//Mensajes de error 
var entradaFinalizadaCorrectamenteP102 = null;
var noHayElementosDeEntradaP102 = null;
var notPosibleToReturnDataP102 = null;

//Botones
var btnSiP102 = null;
var btnNoP102 = null;


/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/********************************************************* INICIALIZACIÓN DE POPUP P102 *******************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

function initializeScreenP102(){

	//Inicializa el popup que contendrá los formularios de las entradas descentralizadas 
	inicializarPopUpFormEntradasDescentralizadas();

	//Cargar idioma
	loadP102(locale);

	//Eventos flecha formularios
	events_p102_flecha_ant();
	events_p102_flecha_sig();

	$("#p03_btn_aceptar").bind("click", function(e) {
		if($("#p102_fld_finalizarPulsado").val() == 'S'){
			//Ejecutamos el imprimir
			//imprimirTrasFinalizar(idEntradaImprimir);

			//Devolvemos el flag a N por si salen mensajes de otro tipo que no sean tras finalizar correctamente
			$("#p102_fld_finalizarPulsado").val('N');
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
function loadP102(locale){
	this.i18nJSON = './misumi/resources/p102PopUpFormEntradasDescentralizadas/p102PopUpFormEntradasDescentralizadas_' + locale + '.json';

	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		//Títulos popup general
		tituloDarEntrada = data.tituloDarEntrada;
		tituloEntradaConfirmada = data.tituloEntradaConfirmada;
		
		//Mensajes de error
		entradaFinalizadaCorrectamenteP102 = data.entradaFinalizadaCorrectamenteP102;
		noHayElementosDeEntradaP102 = data.noHayElementosDeEntradaP102;
		notPosibleToReturnDataP102 = data.notPosibleToReturnDataP102;

		//Botones sí y no
		btnSiP102 = data.btnSiP102;
		btnNoP102 = data.btnNoP102;
		
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

/************************ POPUP ************************/
function inicializarPopUpFormEntradasDescentralizadas(){
	//Definión del popup que contendrá los formularios
	$("#p102_popup").dialog({
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
			if(entradasFinalizadas){
				dibujarEntradas();
				entradasFinalizadas = false;
			}
			numeroEstructuraAColorear = null;
		},
		stack:false
	});
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/*************************************************************FUNCIONES DE P102 ***************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Función que se utiliza para crear y rellenar los formularios de devoluciones del popup
function rellenarPopUpFormularioEntrada(estadoCab){
	var codLoc = $("#centerId").val();
	var codCabPedido = $("#p101_cmb_descEntradasDescentralizadas").val();
	var codArticulo = $("#p101_referencia_b").val();
	var codProvGen = $("#p101_provGen_b").val();
	var codProvTrab = $("#p101_provTrabajo_b").val();
	var codAlbProv = $("#p101_albaranProveedor_b").val();

	var flgHistorico = 'N';
	if($("#p101_chk_historico").prop('checked')){
		flgHistorico = 'S';
	}

	var entrada = new Entrada(codLoc,codCabPedido,codProvGen,codProvTrab,codAlbProv,estadoCab);
	var objJson = $.toJSON(entrada);

	$.ajax({
		type : 'POST',
		url : './entradasDescentralizadas/popup/loadCabeceraEntradasDescentralizadas.do?codArticulo='+ codArticulo + '&flgHistorico=' + flgHistorico ,
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		async:false,
		success : function(data) {
			if(data != null){
				if(data.codError == 0){
					var lstEntradaEstado = data.lstEntradaEstado;
					if(lstEntradaEstado != null){
						if(lstEntradaEstado.length != 0){
							//Se guarda en variable global la lista de entradas
							lstEntradaGlobal = data.lstEntradaEstado[0].lstEntrada;
							if(lstEntradaGlobal != null){
								limpiarFormEntradasDescentralizadasAnteriores();

								//Pone el título al popup
								ponerTituloPopup(estadoCab);

								//Se calculan los índices de los objetos a mostrar
								//en la lista de inicialización de popup
								largoLista = lstEntradaGlobal.length;
								idxInicioLista = 0; 
								idxFinLista = lstEntradaGlobal.length;

								//Si hay más de maxElementosPopup entrada, enseñar hasta la entrada de posición maxElementosPopup - 1
								if(idxFinLista > maxElementosPopup){
									idxFinLista = maxElementosPopup;
								}
								//Función que dibuja los formularios de entradas según el estado pulsado y la cantidad de entradas.
								dibujarFormulariosDeEntradasDescentralizadas(idxInicioLista,idxFinLista);
								actualizarNumeroDePagina(idxInicioLista,idxFinLista);	

								//Gestionamos las opciones según el perfil del usuario
								gestionarPerfilCabecera();
								
								//Los eventos se colocan aquí porque cuando se carga el .js
								//los formularios todavía no están creados. Una vez creados los
								//formularios, sus ids y clases, se les puede asignar eventos.

								//Eventos iluminación
								events_p102_iluminar_entradaDescentralizada();
								events_p102_desiluminar_entradaDescentralizada();

								//Evento de abrir popup lineas entradas
								events_p102_fichaLapiz();

								//Evento de imprimir
								events_p102_imprimir();

								//Evento de finalizar
								events_p102_finalizarTarea();

								//Abrir diálogo
								$("#p102_popup").dialog('open');
							}else{
								createAlert(replaceSpecialCharacters(notPosibleToReturnDataP102), "ERROR");
							}
						}else{
							createAlert(replaceSpecialCharacters(notPosibleToReturnDataP102), "ERROR");
						}
					}else{
						createAlert(replaceSpecialCharacters(notPosibleToReturnDataP102), "ERROR");
					}
				}else{
					createAlert(replaceSpecialCharacters(data.descError), "ERROR");
				}
			}else{
				createAlert(replaceSpecialCharacters(notPosibleToReturnDataP102), "ERROR");
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});	
}

//Función que sirve para limpiar del popup las marcas html de otros estados 
function limpiarFormEntradasDescentralizadasAnteriores(){
	$("#p102_listaFormularios").empty();
}

//Función que crea las marcas html de los formularios, decide que imágenes utilizar e inserta la información
//de las entradas en su lugar correspondiente.
function dibujarFormulariosDeEntradasDescentralizadas(idxInicioLista,idxFinLista){
	//Crear variable que guardará la entrada
	var entrada;

	//Crear variable que guardará la estructura de la entrada.
	var estructuraFormEntrada;

	//Crear un bucle que dibuje las entradas en el popup según su estado.
	var i = idxInicioLista;
	for(i=idxInicioLista;i<idxFinLista;i++){

		//Obtener la entrada a dibujar
		entrada = lstEntradaGlobal[i];

		//Se obtiene la esctructura oculta de p102 correspondiente a la carta y se cambian los ids adecuados al nuevo formulario.
		estructuraFormEntrada = getEstructura(i);

		//Se inserta el formulario en su sección correspondiente del popup. 
		$("#p102_listaFormularios").append(estructuraFormEntrada);

		//Una vez insertadas las nuevas marcas html con sus nuevos id, se inserá la información de cada entrada en su sección correspondiente.
		//Además se decidirá si se muestra el texto --- ENTRADA CONFIRMADA --- o PENDIENTE DE DAR ENTRADA
		rellenarEntrada(i, entrada);

		//Si el numeroEstructuraAColorear es distinto de nulo miramos que coincida con el número de estructura que se está dibujando
		//y en caso afirmativo, lo coloreamos. Esto se hace porque después de ver las línes de una entrada, al cerrar el formulario,
		//queremos saber cuál de los formularios estábamos mirando. Si paginamos, al volver a la página del formulario que mirabamos,
		//queremos saber cuál era.
		if(numeroEstructuraAColorear != null){
			if(i == numeroEstructuraAColorear){
				$("#formEntradaEstructura"+i).addClass("blueCard");
			}
		}
	}
}

//Función que actualiza las imágenes de flechas del popup. Y el número que aparece 
//debajo de los formularios 6/11, 5/5, 12/24, etc.
function actualizarNumeroDePagina(idxInicioLista,idxFinLista){
	//Eliminar la imagen de la flecha actual
	$("#p102_pagAnterior").removeClass("p102_pagAnt");
	$("#p102_pagAnterior").removeClass("p102_pagAnt_des");

	$("#p102_pagSiguiente").removeClass("p102_pagSig");
	$("#p102_pagSiguiente").removeClass("p102_pagSig_des");

	//Añadir la flecha correspondiente
	if(idxInicioLista == 0){
		$("#p102_pagAnterior").addClass("p102_pagAnt_des");
	}else{
		$("#p102_pagAnterior").addClass("p102_pagAnt");
	}

	//Se hace +1 para saber el número de elemento que estamos obteniendo. Por ejemplo si
	//el último índice de los objetos a mostrar de la lista es el 5 equivaldría al elemento 6.
	//En el caso de que el último indice sea el 5 y la lista tenga 6 elementos, quiere decir que
	//la flecha tiene que estar invalidada, pero 5<6!!! Por eso se hace +1, para que el indice
	//equivalga al número de objeto
	if(idxFinLista < largoLista){
		$("#p102_pagSiguiente").addClass("p102_pagSig");
	}else{
		$("#p102_pagSiguiente").addClass("p102_pagSig_des");
	}

	//Actualiza el número de debajo de los formularios. 
	//Indica cuántos formularios se han mostrado y el total de 
	//formularios a mostrar.
	$("#p102_paginaActual").text(idxFinLista+"/");
	$("#p102_paginaTotal").text(largoLista);
}

//Función que sirve para paginar entre los formularios
function paginacionFormularios(flechaSig){
	var pagina = false;
	//Si se ha presionado la flecha siguiente
	if(flechaSig == 'S'){
		//Si hay más formularios de los que se ven en la página, paginar
		if((idxFinLista < largoLista) && $("#p102_pagSiguiente").hasClass("p102_pagSig")){		

			//Limpiar las entradas anteriores
			limpiarFormEntradasDescentralizadasAnteriores();

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
			dibujarFormulariosDeEntradasDescentralizadas(idxInicioLista,idxFinLista);	
			actualizarNumeroDePagina(idxInicioLista,idxFinLista);	
			
			//Gestionamos las opciones según el perfil del usuario
			gestionarPerfilCabecera();
			
			//Ponemos que ha paginado.
			pagina = true;
		}
		//Si se ha presionado la flecha anterior
	}else{
		//Si hay más formularios de los que se ven en la página, paginar
		if((idxInicioLista > 0) && $("#p102_pagAnterior").hasClass("p102_pagAnt")){		
			//Limpiar las devoluciones anteriores
			limpiarFormEntradasDescentralizadasAnteriores();

			//El inicio de lista se sitúa en el bloque de elementos a mostrar anterior
			idxFinLista = idxInicioLista;
			idxInicioLista = idxInicioLista - maxElementosPopup;

			//Dibujar los formularios
			dibujarFormulariosDeEntradasDescentralizadas(idxInicioLista,idxFinLista);	
			actualizarNumeroDePagina(idxInicioLista,idxFinLista);	

			//Gestionamos las opciones según el perfil del usuario
			gestionarPerfilCabecera();
			
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

		//Evento apertura grid línea de entrada
		events_p102_fichaLapiz();

		//Eventos imprimir
		events_p102_imprimir();

		//Eventos finalizar
		events_p102_finalizarTarea();

		//Eventos iluminación
		events_p102_iluminar_entradaDescentralizada();
		events_p102_desiluminar_entradaDescentralizada();
	}
}

//Poner título al popup de formularios
function ponerTituloPopup(estadoCab){ 
	if(estadoCab == estadoDarEntrada){
		$( "#p102_popup" ).dialog({ title:tituloDarEntrada});
	}else if(estadoCab == estadoEntradaConfirmada){
		$( "#p102_popup" ).dialog({ title:tituloEntradaConfirmada});		
	}
}

//Función que recupera la entrada en función del número del formulario
function getEntrada(id) {
	//Se obtienen el id para saber cual es el formulario seleccionado y saber la posición de la entrada en la lista
	//Ejemplo: el id es formEntradaEstructuraFichaLapiz0, se obtiene 0. 0-> Posicion de entrada en lista
	var numeroFormulario = id.replace(/[^0-9]/g,'');

	//Se obtiene la entrada
	var entrada = lstEntradaGlobal[numeroFormulario];

	//Ponemos el codLoc a la entrada
	var codLoc = $("#centerId").val();
	entrada.codLoc = codLoc;
	
	return entrada;
}

//Función que recupera la entrada a imprimir en función del número del formulario
function getEntradaImprimir(id) {
	//Se obtienen el id para saber cual es el formulario seleccionado y saber la posición de la entrada en la lista
	//Ejemplo: el id es formEntradaEstructuraFichaLapiz0, se obtiene 0. 0-> Posicion de entrada en lista
	var numeroFormulario = id.replace(/[^0-9]/g,'');

	//Se obtiene la entrada
	var entrada = lstEntradaImprimir[numeroFormulario];

	//Ponemos el codLoc a la entrada
	var codLoc = $("#centerId").val();
	entrada.codLoc = codLoc;
	
	return entrada;
}

//Función que obtiene la entrada seleccionada al clicar la ficha del formulario y busca sus líneas de entrada para rellenar el grid.
function cargarLineaEntrada(id){
	// Se obtiene la entrada a partir del id
	var entradaSeleccionada = getEntrada(id);

	//Según el estado, abrimos un popup u otro.
	if(estado == 1){
		cargarLineasDarEntrada(entradaSeleccionada);
	}else{
		cargarLineasDarEntrada(entradaSeleccionada);
	}
}

//Función que sirve para finalizar entrada. 
function finalizarTareaDeEntrada(id){

	var entradaSeleccionada = getEntrada(id);

	var objJson = $.toJSON(entradaSeleccionada);
	$.ajax({
		type : 'POST',
		url : './entradasDescentralizadas/popup/finalizarTareaDeEntrada.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		async: false,
		cache : false,
		success : function(data) {
			if(data.codError == 0){
				//Como se va a finalizar la tarea, no tiene sentido colorear su cabecera, pues va a desaparecer de la lista.
				numeroEstructuraAColorear = null;

				//Ponemos el flag de finalizar pulsado en S, de esta forma, a la hora de pulsar aceptar en el dialogo de mensajes, sabemos
				//que el dialogo es porque se ha finalizado una devolución y no por cualquier otra cosa.
				$("#p102_fld_finalizarPulsado").val('S');

				//Realizamos la copia de la copia de la lista de entradas. Esto es necesario, porque después de finalizar
				//queremos imprimir la entrada. Como al finalizar la entrada la entrada se borra
				//de la lista lstEntradaGlobal, nos sería imposible acceder a su entrada, y por eso se copia. Además grabamos la posición
				//de la lista de entrada.
				lstEntradaImprimir = lstEntradaGlobal.slice(0);
				idEntradaImprimir = id;

				var numeroFormulario = id.replace(/[^0-9]/g,'');
				
				//Si la entrada ha cambiado de estado correctamente, eliminamos la entrada de la lista de entradas.
				lstEntradaGlobal.splice(numeroFormulario,1);

				//Si la lista sigue teniendo elementos
				if(lstEntradaGlobal.length > 0){
					//Se calculan los índices de los objetos a mostrar
					//en la lista de inicialización de popup
					largoLista = lstEntradaGlobal.length;
					idxInicioLista = 0; 
					idxFinLista = lstEntradaGlobal.length;

					//Si hay más de maxElementosPopup entradas, enseñar hasta la entrada de posición maxElementosPopup - 1
					if(idxFinLista > maxElementosPopup){
						idxFinLista = maxElementosPopup;
					}

					//Se limpian los formularios anteriores del popup
					limpiarFormEntradasDescentralizadasAnteriores();

					//Función que dibuja los formularios de devoluciones según el estado pulsado y la cantidad de devoluciones.
					dibujarFormulariosDeEntradasDescentralizadas(idxInicioLista,idxFinLista);
					actualizarNumeroDePagina(idxInicioLista,idxFinLista);	

					//Gestionamos las opciones según el perfil del usuario
					gestionarPerfilCabecera();
					
					//Los eventos se colocan aquí porque cuando se carga el .js
					//los formularios todavía no están creados. Una vez creados los
					//formularios, sus ids y clases, se les puede asignar eventos.

					//Eventos apertura grid línea de entrada
					events_p102_fichaLapiz();

					//Eventos imprimir
					events_p102_imprimir();

					//Eventos para finalizar tarea
					events_p102_finalizarTarea();

					//Eventos de iluminación
					events_p102_iluminar_entradaDescentralizada();
					events_p102_desiluminar_entradaDescentralizada();

					entradasFinalizadas = true;

					//Si la devolución se finaliza correctamente
					createAlert(replaceSpecialCharacters(entradaFinalizadaCorrectamenteP102), "INFO");
				}else{
					//Si la lista no tiene elementos
					createAlert(replaceSpecialCharacters(noHayElementosDeEntradaP102), "INFO");

					//Dibujamos los nuevos estados de las devoluciones.
					dibujarEntradas();

					$("#p102_popup").dialog('close');										
				}
				getAvisosCentro();
			}else{
				//Mostramos error y cerramos popup
				$("#p102dialog-confirmFinalizar").dialog("close");
				createAlert(replaceSpecialCharacters(data.descError), "ERROR");
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});		
}

//Obtiene la estructura de la carta y cambia sus ids para adaptarla a cada carta del popup.
function getEstructura(indice){
	//Se obtiene la esctructura y se cambian los ids adecuados al nuevo formulario
	var estructura = $("#formEntradaEstructura").prop('outerHTML');

	//Copiamos la estructura entera de la carta y le damos id
	estructura = estructura.replace("formEntradaEstructura","formEntradaEstructura"+indice);

	//Cambiamos el id del lbl codCabPedido por cada carta. puede estar oculto según el estado.
	estructura = estructura.replace("formEntradaEstructuraLblCodCabPedido","formEntradaEstructuraLblCodCabPedido"+indice);

	//Cambiamos el id del lbl pendiente dar entrada por cada carta. Puede estar oculto según el estado.
	estructura = estructura.replace("formatoEntradaPendienteDar","formatoEntradaPendienteDar"+indice);

	//Cambiamos el id del lbl entrada confirmada por cada carta. Puede estar oculto según el estado.
	estructura = estructura.replace("formatoEntradaConfirmar","formatoEntradaConfirmar"+indice);

	//Cambiamos el id del lbl proveedor por cada carta
	estructura = estructura.replace("formEntradaEstructuraLblProveedor","formEntradaEstructuraLblProveedor"+indice);

	//Cambiamos los id del lapiz, impresora y finalizar. De esta forma cuando los clicamos, podemos extraer el id del elemento
	//que hemos clicado y extraer la entrada de la lista que nos interesa.
	estructura = estructura.replace("formEntradaEstructuraFichaLapiz","formEntradaEstructuraFichaLapiz"+indice);
	estructura = estructura.replace("formEntradaEstructuraImpresion","formEntradaEstructuraImpresion"+indice);
	estructura = estructura.replace("formEntradaEstructuraFin","formEntradaEstructuraFin"+indice);

	return estructura;
}

//Rellena los labels de la cabecera de la entrada.
function rellenarEntrada(indice, entrada){
	//Según el estado, rellenamos la entrada de una forma u otra y mostramos el mensaje 
	//PENDIENTE DAR ENTRADA o ENTRADA CONFIRMADA. Ese estado es una variable global definida en
	//p101. En devoluciones vendría en el objeto, peroaquí no lo devuelve el PLSQL.
	if(estado == 1){
		$("#formatoEntradaPendienteDar"+indice).removeClass("formEntradaCeldaOculto");
	}else{
		$("#formatoEntradaConfirmar"+indice).removeClass("formEntradaCeldaOculto");
	}
	//Rellenamos los labels de codigo cabecera y de proveedor.
	$("#formEntradaEstructuraLblCodCabPedido"+indice).text(entrada.codCabPedido);
	$("#formEntradaEstructuraLblProveedor"+indice).text(entrada.codProvGen + '-' + entrada.codProvTrab + '/' + entrada.denomProvTrab);
	$("#formEntradaEstructuraLblProveedor"+indice).attr("title",entrada.codProvGen + '-' + entrada.codProvTrab + '/' + entrada.denomProvTrab);
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************* EVENTOS DE P102 **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Evento para flechas 
function events_p102_flecha_ant(){
	$("#p102_pagAnterior").click(function () {		
		paginacionFormularios('N');
	});	
}

//Evento para flechas 
function events_p102_flecha_sig(){
	$("#p102_pagSiguiente").click(function () {		
		paginacionFormularios('S');
	});	
}

//Iluminar la carta si no ha sido utilizada
function events_p102_iluminar_entradaDescentralizada(){
	$(".card").mouseover(function () {	
		$(".blueCard").removeClass("blueCard"); //En cuanto actúe sobre la carta se eliminan las cartas iluminadas
		$(this).addClass("iluminar");
	});	
}

//Desiluminar la carta
function events_p102_desiluminar_entradaDescentralizada(){
	$(".card").mouseout(function () {		
		if(!$(this).hasClass("blueCard")){
			$(this).removeClass("iluminar");
		}
	});	
}

function events_p102_fichaLapiz(){
	$(".formEntradaLapiz").click(function () {		
		/*********************** ABRIR FORMULARIO DE ENTRADA ************************/
		//Se obtiene el id de la ficha, para saber qué formulario se ha clicado.
		//El id tiene este formato formEntradaEstructuraFichaLapiz(ID). Esto quiere
		//decir que se ha clicado el formulario número(ID). Por lo cual, en la lista
		//global llamada lstEntradaGlobal, habrá que buscar el elemento situado
		//en la posición (ID). 
		var id = $(this).attr('id');

		//En esta función se obtiene el número de formulario clicado, se obtiene 
		//su correspondiente entrada de la lista y se buscan las líneas de esa
		//entrada.
		cargarLineaEntrada(id);


		/****************************** COLOREAR CABECERA ******************************/
		//Eliminamos las posibles sombras azules de las cabeceras clicadas
		$(".blueCard").removeClass("blueCard");
		$(".iluminar").removeClass("iluminar");

		//Obtenemos la estructura clicada quitando el string "FICHALAPIZ" del id de la imagen clicada. Después coloreamos la sombrA de la cabecera de la entrada para que el usuario sepa que estaba mirando esa
		//entrada.
		var idEstructuraFicha = id.replace('FichaLapiz','');
		$("#"+idEstructuraFicha).addClass("blueCard");

		//Se obtienen el (ID) del formEntradaEstructura(ID) para saber cual es el formulario seleccionado y saber la posición de la entrada en la lista
		//Ejemplo: el id es formEntradaEstructura0, se obtiene 0. 0-> Posicion de devolucion en lista. Se guarda en variable global porque es posible que
		//al paginar entre las entradas, necesitemos este id para pintar de azul el último formulario que hemos visitado cuando toque.
		numeroEstructuraAColorear = idEstructuraFicha.replace(/[^0-9]/g,'');
	});	
}
//TODO elena
function events_p102_imprimir(){
	$(".formEntradaImprimir").click(function () {		
		//Se obtiene el id de la ficha, para saber qué formulario se ha clicado.
		//El id tiene este formato formEntradaEstructuraFichaLapiz(ID). Esto quiere
		//decir que se ha clicado el formulario número(ID). Por lo cual, en la lista
		//global llamada lstEntradaGlobal, habrá que buscar el elemento situado
		//en la posición (ID). 
		var id = $(this).attr('id');
		imprimir(id);
	});	

}

function imprimir(id){
	var entrada = getEntrada(id);
	
	var codLoc = $("#centerId").val();
	// la variable entrada es la entrada sobre la que se ha pinchado la impresora
	var urlImpresion = './entradasDescentralizadas/popup/imprimirEntradaDescentralizada.do?codLoc='+entrada.codLoc+'&codCabPedido='+entrada.codCabPedido
	window.open(urlImpresion,"_blank");
}

function imprimirTrasFinalizar(id){
	var entrada = getEntradaImprimir(id);
	
	var codLoc = $("#centerId").val();
	// la variable entrada es la entrada sobre la que se ha pinchado la impresora
	var urlImpresion = './entradasDescentralizadas/popup/imprimirEntradaDescentralizada.do?codLoc='+entrada.codLoc+'&codCabPedido='+entrada.codCabPedido
	window.open(urlImpresion,"_blank");
}


function events_p102_finalizarTarea(){
	$(".formEntradaFinalizar").click(function () {		

		//Se obtiene el id de la ficha, para saber qué formulario se ha clicado.
		//El id tiene este formato formEntradaEstructuraFichaLapiz(ID). Esto quiere
		//decir que se ha clicado el formulario número(ID). Por lo cual, en la lista
		//global llamada lstEntradaGlobal, habrá que buscar el elemento situado
		//en la posición (ID). 
		var id = $(this).attr('id');

		$("#p102dialog-confirmFinalizar" ).dialog({
			resizable: false,
			height:160,
			modal: true,
			buttons: [
			          {
			        	  text:btnSiP102,
			        	  click: function() {
			        		  //Finaliza la tarea de la entrada
			        		  finalizarTareaDeEntrada(id);		
			        		  $(this).dialog( "close" );
			        	  }
			          },{
			        	  text:btnNoP102,
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
/*************************************************      GESTIONA LOS CAMPOS DE FINALIZAR, ETC    *********************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
function gestionarPerfilCabecera(){
	if(userPerfilMisumi == CONS_CONSULTA_ROLE){
		//Ocultamos los finalizar
		$(".formEntradaFinalizar").parent().hide();
		
		//Cambiamos las fichas con lapiz por las fichas sin lapiz
		var lstImg = $(".formEntradaLapiz img");
		var i = 0;
		
		for(i;i< lstImg.size();i++){
			lstImg[i].src = './misumi/images/ficheroDevolucion2.jpg';
		}
	}else{
		//Mostramos los finalizar
		$(".formEntradaFinalizar").parent().show();
		
		//Ponemos las fichas con lapiz por las fichas sin lapiz
		var lstImg = $(".formEntradaLapiz img");
		var i = 0;
		
		for(i;i< lstImg.size();i++){
			lstImg[i].src = './misumi/images/ficheroDevolucionLapiz2.jpg';
		}
	}
}










