/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/****************************************************************** CONSTANTES ***************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
var estadoDarEntrada = 1;
var estadoEntradaConfirmada = 2;



/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/****************************************************************** VARIABLES ****************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
//Se guarda el estado seleccionado en una variable global para utilizarla en p102.js a la hora de dibujar los formularios en dibujarFormulariosDeEntradas()
var estado = null;

//Mensajes json de p101
var noDataForCenter=null;
var noDataForEntry=null;


//Control de que la pantalla se ha inicializado.
var p101Inicializada = null;

//Guardamos la lista del combo para cuando la filtremos.
var lstCombo = null;

//Valor combobox P103
var comboValueP103 = null;
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************** DOCUMENTO LISTO **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

$(document).ready(function(){
	if($("#centerId").val() != null && $("#centerId").val() != "" && p101Inicializada == null){
		p101Inicializada = 'S';
		loadP101(locale);
		initializeScreenP101();
		//resetWindow();
	}
});


/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/********************************************************* INICIALIZACIÓN DE PANTALLA ********************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

function initializeScreenP101(){
	//Evento de botón buscar o enter.
	events_p101_btn_buscar();

	//Evento al presionar el icono de ayuda
	events_p101_btn_ayudaHistorico();

	//Evento que pinta las denominaciones de las entradas y sus estados
	events_p101_check_flgHistorico();

	//Filtros de combo y de lista.
	events_p101_provGen();
	events_p101_provTrab();

	//Evento que oculta las elipses al escribir en albaran.
	events_p101_alb();
	events_p101_ref();

	//Se crea el combobox
	$("#p101_cmb_descEntradasDescentralizadas").combobox(null);

	//Se indica que los campos del filtro son sólo de tipo integer.
	$("#p101_provGen_b").filter_input({regex:'[0-9]'});
	//$("#p101_albaranProveedor_b").filter_input({regex:'[0-9]'});
	$("#p101_provTrabajo_b").filter_input({regex:'[0-9]'});	
	$("#p101_referencia_b").filter_input({regex:'[0-9]'});	

	//Se buscan los elementos que rellenarán el combobox
	load_cmb_entradasDescentralizadas();

	//Iniciar parámetros del fichero javascript del popup de las cabeceras.
	initializeScreenP102();
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************** INICIALIZACIÓN DE EVENTOS E IDIOMA *******************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

/************************ IDIOMA ************************/
//Función para buscar los mensajes de la pantalla en idioma seleccionado, por defecto 'es'
function loadP101(locale){
	this.i18nJSON = './misumi/resources/p101EntradasDescentralizadas/p101EntradasDescentralizadas_' + locale + '.json';

	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		noDataForCenter =  data.noDataForCenter;	
		noDataForEntry = data.noDataForEntry;
		mensajeAyudaHistorico = data.mensajeAyudaHistorico;
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}


/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************* FUNCIONES DE P82 **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Buscar descripciones de las entradas descentralizadas y rellenar el combobox llamado Pedido/ proveedor
function load_cmb_entradasDescentralizadas(){
	var flgHistorico = 'N';

	if($("#p101_chk_historico").prop('checked')){
		flgHistorico = 'S';
	}

	var codLoc = $("#centerId").val();
	$.ajax({
		type : 'GET',
		url : './entradasDescentralizadas/loadDenominacionesEntradasDescentralizadas.do?flgHistorico='+ flgHistorico + '&codLoc='+codLoc,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {
			//Insertamos las opciones sin repetir.
			var options = "<option value=' '>TODOS</option>";
			if(data != null){
				if(data.codError == 0){
					var lstEntrada = data.lstEntrada;
					if(lstEntrada != null){
						if(lstEntrada.length != 0){						
							/*lstEntrada.forEach((entrada) =>{
								options = options + "<option value='" + entrada.codCabPedido + "'>"+ entrada.codCabPedido + '-' + entrada.codProvGen + '-' + entrada.codProvTrab + '/' + entrada.denomProvTrab +"</option>";
							});*/
							//Insertamos las opciones sin repetir.
							var i = 0;
							for (i; i < lstEntrada.length; i++){
								options = options + "<option value='" + lstEntrada[i].codCabPedido + "'>"+ lstEntrada[i].codCabPedido + '-' + lstEntrada[i].codProvGen + '-' + lstEntrada[i].codProvTrab + '/' + lstEntrada[i].denomProvTrab +"</option>";
							}
						}
					}
				}
			}
			$("select#p101_cmb_descEntradasDescentralizadas").html(options);

			$("#p101_cmb_descEntradasDescentralizadas").combobox('autocomplete',"TODOS");
			$("#p101_cmb_descEntradasDescentralizadas").combobox('comboautocomplete',"null");

			//Guardamos el combo en una variable global para luego filtrar.
			lstCombo = $("#p101_cmb_descEntradasDescentralizadas option").clone();

			//Dibujamos las imágenes de los estados.
			dibujarEntradas();
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});

	$("#p101_cmb_descEntradasDescentralizadas").combobox({
		selected: function(event, ui) {
			if ( ui.item.value!="" && ui.item.value!="null" ) {
				//Si el valor seleccionado difiere del valor seleccionado anterior
				if(ui.item.value != comboValueP103){							
					comboValueP103 = $("#p101_cmb_descEntradasDescentralizadas").val();
					if (comboValueP103 != null){

						//Si hemos rellenado provGen_b o provTrabajo, hemos filtrado sobre los elementos
						//html, por lo que repintamos el combo entero al seleccionar una opción y borramos
						//los campos de provGen y provTrab. Si ha sido filtrado por escribir en el combo,
						//se utiliza el componente interno del combo, por lo que no hay que repintar.
						var provGen = $("#p101_provGen_b").val();
						var provTrabajo = $("#p101_provTrabajo_b").val();

						if(provGen != "" || provTrabajo != ""){
							//Una vez seleccionado, vaciamos los posibles filtros.
							$("#p101_provGen_b").val("");
							$("#p101_provTrabajo_b").val("");

							//Rellenamos el combo con todos los elementos.
							lstCombo.appendTo('#p101_cmb_descEntradasDescentralizadas');
						}

						//Desactivamos las elipses
						disableAreaResultados();
					}
				}
			}
		},
	});
}


//Función que sirve para dibujar las imágenes situadas dentro de las elipses según la búsqueda realizada. 
function dibujarEntradas(){
	var codLoc = $("#centerId").val().trim();
	var codCabPedido = $("#p101_cmb_descEntradasDescentralizadas").val().trim() != "" ? $("#p101_cmb_descEntradasDescentralizadas").val().trim() : null;
	var codArticulo = $("#p101_referencia_b").val().trim() != "" ? $("#p101_referencia_b").val().trim() : "";
	var codProvGen = $("#p101_provGen_b").val().trim() != "" ? $("#p101_provGen_b").val().trim() : null;
	var codProvTrab = $("#p101_provTrabajo_b").val().trim() != "" ? $("#p101_provTrabajo_b").val().trim() : null;
	var codAlbProv = $("#p101_albaranProveedor_b").val().trim() != "" ? $("#p101_albaranProveedor_b").val().trim() : null;

	var flgHistorico = 'N';

	if($("#p101_chk_historico").prop('checked')){
		flgHistorico = 'S';
	}

	var entrada = new Entrada(codLoc,codCabPedido,codProvGen,codProvTrab,codAlbProv);
	var objJson = $.toJSON(entrada);

	$.ajax({
		type : 'POST',
		url : './entradasDescentralizadas/loadEstadoEntradasDescentralizadas.do?flgHistorico='+ flgHistorico + "&codArticulo=" + codArticulo,
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		async: false,
		cache : false,
		success : function(data) {
			//Si existen datos
			if(data != null){
				//Si no hay error
				if(data.codError == 0){
					var lstEntradaEstado = data.lstEntradaEstado;
					if(lstEntradaEstado != null){
						if(lstEntradaEstado.length != 0){
							//Limpiar las imágenes de los estados anteriores.
							//En el caso de no hacer esto, podría ocurrir que una búsqueda devuelva
							//imágenes para algunos estados y para otros no. Al realizar una segunda
							//búsqueda no se eliminarían las imágenes del estado anterior y al final
							//tendríamos las imágenes de los estados desactualizadas.

							//Además se quitan los eventos click porsiacaso
							limpiarEstadosAnteriores(lstEntradaEstado.length);														

							//Variables para dibujas las imágenes de las elipses y nubes
							var numeroDeEntradas;
							var estadoEntrada;
							var imagenASeleccionar;

							var i = 0;

							for(i = 0; i < lstEntradaEstado.length; i++){

								//Calcular cuantas entradas existen en cada estado, se utilizará para buscar la imagen adecuada
								numeroDeEntradas = lstEntradaEstado[i].numeroEntradas;

								//Calcular cada estado para así encontrar el id de la imagen y encontrar la imagen adecuada
								estadoEntrada = lstEntradaEstado[i].estado;

								//Si hay más de 3 entradas, el número de la imagen de entradas será la terminada en 4.
								//Si no, será 0,1,2 o 3
								if(numeroDeEntradas > 3){
									numeroDeEntradas = 4;
								}

								if(numeroDeEntradas > 0){
									//Poner la correspondiente imagen en su correspondiente id.
									imagenASeleccionar = "./misumi/images/numeroEntrada" + estadoEntrada + numeroDeEntradas+".png?version=${misumiVersion}"
									$("#estadoEntrada"+estadoEntrada).attr('src',imagenASeleccionar);		

									//Poner la correspondiente clase de puntero
									$("#estadoEntrada"+estadoEntrada).addClass("estadoClickableEntrada");
								}
							}
							//Si existe un estado, muestra el área de los estados de la búsqueda con las nubes
							enableAreaResultados();

							//Evento dentro de elipse de dar entrada
							events_p101_img_darEntrada();

							//Evento dentro de nube de plataforma
							events_p101_img_entradaConfirmada();
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
					createAlert(replaceSpecialCharacters(data.descError), "ERROR");
				}
			}else{
				disableAreaResultados();
				createAlert(replaceSpecialCharacters(noDataForEntry), "ERROR");
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});	
}

//Enseña el area de resultados de elipses
function enableAreaResultados(){
	$("#p101_AreaResultados").show();
}

//Esconde el area de resultados de elipses
function disableAreaResultados(){
	$("#p101_AreaResultados").hide();
}

//Limpia las imágenes de los estados anteriores y sus eventos
function limpiarEstadosAnteriores(totalEstados){
	var i = 1;
	for(i;i<=totalEstados;i++){
		//Quitar imagen de estado
		$("#estadoEntrada"+i).attr('src','');

		//Quitar la correspondiente clase de puntero
		$("#estadoEntrada"+i).removeClass("estadoClickable");

		//Quitar el evento click
		$("#estadoEntrada"+i).unbind("click");
	}
}
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************* EVENTOS DE P101 **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Evento que muestra una ayuda explicando la utilidad del check de Histórico
function events_p101_btn_ayudaHistorico(){
	$("#p101_btn_ayudaHistorico").click(function () {
		createAlert(replaceSpecialCharacters(mensajeAyudaHistorico), "HELP");
	});
}

function events_p101_check_flgHistorico(){
	$("#p101_chk_historico").click(function () {
		disableAreaResultados();

		//Reseteamos los valores del filtro ya qye vamos  a recargar el combo.
		$("#p101_provGen_b").val("");
		$("#p101_provTrabajo_b").val("");
		$("#p101_albaranProveedor_b").val("");
		$("#p101_referencia_b").val("");

		//Cargamos el combo de nuevo.
		load_cmb_entradasDescentralizadas();
	});
}

//Evento para botón de buscar
function events_p101_btn_buscar(){
	$("#p101_btn_buscar").click(function () {	
		dibujarEntradas();
	});	
	$(".controlReturnP103").on("keydown", function(e) {
		if (e.which == 13) {
			e.preventDefault();
			dibujarEntradas();
		}
	});
}

//Evento para plataforma. Abre un popup con las entradas a dar.
function events_p101_img_darEntrada(){
	$("#estadoEntrada1").click(function () {
		//Se guarda el estado seleccionado en una variable global para utilizarla en p102.js a la hora de dibujar los formularios en dibujarFormulariosDeEntradas()
		estado = estadoDarEntrada;
		rellenarPopUpFormularioEntrada(estado);
	});	
}

//Evento para abonado. Abre un popup con lasentradas confirmadas.
function events_p101_img_entradaConfirmada(){
	$("#estadoEntrada2").click(function () {
		//Se guarda el estado seleccionado en una variable global para utilizarla en p102.js a la hora de dibujar los formularios en dibujarFormulariosDeEntradas()
		estado = estadoEntradaConfirmada;
		rellenarPopUpFormularioEntrada(estado);
	});	
}

function events_p101_provGen(){
	$("#p101_provGen_b").keyup(function(e){
		var valCodProvGen = $(this).val(); 
		var valCodProvTrab = $("#p101_provTrabajo_b").val();

		//Si valCodProvGen o valCodProvTrab tienen dato se filtra el combo.
		//Si no, se meustra todo
		if(valCodProvGen != "" || valCodProvTrab != ""){
			$('#p101_cmb_descEntradasDescentralizadas').empty();
			lstCombo.filter(function(idx, el) {
				var comboOption = $(el).text();

				if(comboOption != "TODOS"){
					//Obtenemos el codProvgen de la fila y el codProvTrab
					var partesCombo = $(el).text().split("-");
					var codProvGen = partesCombo[1];
					var codProvTrab = partesCombo[2].split("/")[0];

					//Lo comparamos con el valor que estamos metiendo. Si cumple con el patrón codProvGen
					//del combo lo devolvemos.
					if(codProvGen.lastIndexOf(valCodProvGen, 0) === 0){

						//Miramos si hay dato en valCodProvTrab. Si hay dato, se busca
						//entre los elementos del combo filtrados por codProvGen que también
						//cumpla con codProvTrab. Si no hay dato, se muestran filtrados por valCodProvGen
						if(valCodProvTrab != ""){
							//Filtramos por valCodProvTrab
							if(codProvTrab.lastIndexOf(valCodProvTrab, 0) === 0){
								return $(el).text();
							}
						}else{	
							//Solo filtrados por valCodProvgen
							return $(el).text();
						}
					}
				}else{
					//Devolvemos el todos también.
					return $(el).text();
				}
			}).appendTo('#p101_cmb_descEntradasDescentralizadas');
		}else{
			lstCombo.appendTo('#p101_cmb_descEntradasDescentralizadas');
		}

		//Una vez dibujamos los elementos del combo, seleccionamos todos por defecto siempre.
		$("#p101_cmb_descEntradasDescentralizadas").combobox('autocomplete',"TODOS");
		$("#p101_cmb_descEntradasDescentralizadas").combobox('comboautocomplete',"null");
		comboValueP103 = " ";
	});

	$("#p101_provGen_b").keydown(function(e){
		//Si se inserta un numérico o se elimina un campo, se borra el area de resultados.
		var isNumber = /^[0-9]$/i.test(e.key);
		if(isNumber || e.keyCode == 46 || e.keyCode == 8){
			//Al escribir en en input de proveedor generico, ocultar area de resultados de elipses, para obligar a buscar y recalcular.
			disableAreaResultados();
		}
	});
}

function events_p101_provTrab(){
	$("#p101_provTrabajo_b").keyup(function(e){
		var valCodProvTrab = $(this).val(); 
		var valCodProvGen = $("#p101_provGen_b").val();

		//Si valCodProvGen o valCodProvTrab tienen dato se filtra el combo.
		//Si no, se meustra todo
		if(valCodProvGen != "" || valCodProvTrab != ""){
			$('#p101_cmb_descEntradasDescentralizadas').empty();
			lstCombo.filter(function(idx, el) {
				var comboOption = $(el).text();

				if(comboOption != "TODOS"){
					//Obtenemos el codProvgen de la fila y el codProvTrab
					var partesCombo = $(el).text().split("-");
					var codProvGen = partesCombo[1];
					var codProvTrab = partesCombo[2].split("/")[0];

					//Lo comparamos con el valor que estamos metiendo. Si cumple con el patrón codProvTrab
					//del combo lo devolvemos.
					if(codProvTrab.lastIndexOf(valCodProvTrab, 0) === 0){

						//Miramos si hay dato en valCodProvGen. Si hay dato, se busca
						//entre los elementos del combo filtrados por codProvTrab que también
						//cumpla con codProvGen. Si no hay dato, se muestran filtrados por valCodProvTrab
						if(valCodProvGen != ""){
							//Filtramos por valCodProvTrab
							if(codProvGen.lastIndexOf(valCodProvGen, 0) === 0){
								return $(el).text();
							}
						}else{	
							//Solo filtrados por valCodProvgen
							return $(el).text();
						}
					}
				}else{
					//Devolvemos el todos también.
					return $(el).text();
				}
			}).appendTo('#p101_cmb_descEntradasDescentralizadas');
		}else{
			lstCombo.appendTo('#p101_cmb_descEntradasDescentralizadas');
		}

		//Una vez dibujamos los elementos del combo, seleccionamos todos por defecto siempre.
		$("#p101_cmb_descEntradasDescentralizadas").combobox('autocomplete',"TODOS");
		$("#p101_cmb_descEntradasDescentralizadas").combobox('comboautocomplete',"null");
		comboValueP103 = " ";
	});

	$("#p101_provTrabajo_b").keydown(function(e){
		//Si se inserta un numérico o se elimina un campo, se borra el area de resultados.
		var isNumber = /^[0-9]$/i.test(e.key);
		if(isNumber || e.keyCode == 46 || e.keyCode == 8){
			//Al escribir en en input de proveedor generico, ocultar area de resultados de elipses, para obligar a buscar y recalcular.
			disableAreaResultados();
		}
	});
}

function events_p101_alb(){
	$("#p101_albaranProveedor_b").keydown(function(e){
		//Si se inserta un numérico o se elimina un campo, se borra el area de resultados.
		var isNumber = /^[0-9]$/i.test(e.key);
		if(isNumber || e.keyCode == 46 || e.keyCode == 8){
			//Al escribir en en input de proveedor generico, ocultar area de resultados de elipses, para obligar a buscar y recalcular.
			disableAreaResultados();
		}
	});
}

function events_p101_ref(){
	$("#p101_referencia_b").keydown(function(e){
		//Si se inserta un numérico o se elimina un campo, se borra el area de resultados.
		var isNumber = /^[0-9]$/i.test(e.key);
		if(isNumber || e.keyCode == 46 || e.keyCode == 8){
			//Al escribir en en input de proveedor generico, ocultar area de resultados de elipses, para obligar a buscar y recalcular.
			disableAreaResultados();
		}
	});
}
