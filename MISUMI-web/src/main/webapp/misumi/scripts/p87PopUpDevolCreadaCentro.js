/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/****************************************************************** CONSTANTES ***************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Insertamos el número máximo de enteros y decimales que se podrán introducir. Servirán para P87 y P87
var maxNumEnteros = 15;
var maxNumDecimales = 3;





/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/**************************************************************** VARIABLES ******************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

var p87BotonAceptar = null;

//Variable que guarda el grid
var gridP87=null;

//Variable que guarda el valor del combo seleccionado. Es útil en las paginaicones y ordenaciones 
//para hacer la búsqueda filtrando con este campo sobre la tabla temporal
var comboValue = null;

//La lista guarda los elementos modificadosP87 de cada página. Si hay un elemento modificado, se edita y sale error, recupera los datos por defecto, pero el elemento
//sigue estando en esta lista. 
var seleccionadosP87 = new Array();

//La lista de seleccionadosP87 guarda todos los registros que han sido modificadosP87 en todas las páginas. En caso de existir un registro modificado y al volverlo a modificar devuelve error
//ese registro se elimina de esta lista, pues el registro obtiene los valores por defecto y por ende deja de estar modificado. Si se vuelve a modificar se inserta
//de nuevo aquí.
var modificadosP87 = new Array();

//En la primera carga se inicia el contador a 1.
var numRowP87=1;

//Texto de iconos
var iconoGuardado=null;
var iconoModificado = null;

//Texto de errores
var descError = null;
var stockDevueltoIncorrecto = null;
var stockDevueltoEnteroErroneo = null;
var stockDevueltoDecimalErroneo = null;
var stockDevueltoDecimalErroneo2 = null;
var sumaSuperaMaximoP87 = null;
var emptyListaModificados = null;
var notPosibleToReturnDataP87 = null;
var errorActualizacionRegistro = null;
var errorActualizacion = null;
var cantidadMaximaNoSuperableP87 = null;

//Mensaje después de guardado.
var lineasActualizadas = null;
var lineaActualizada = null;
var lineasActualizadasY = null;
var lineaActualizadaY = null;
var lineaErronea = null;
var lineasErroneas = null;
var cabeceraActualizada = null;

//Mensaje titulo popup fin de campaña
var creadaCentro = null;

//Titulos
var stockDevueltoNoPrepararMercanciaTitle = null;
var stockDevueltoPrepararMercanciaTitle = null;
var stockDevueltoTitlePrepararMercancia = null;
var stockDevueltoTitleNoPrepararMercancia = null;

//Guarda una lista de DevolucionLineaModificado, que contiene por cada línea modificada de la página actual un objeto con 
//datos del: bulto, stockDevuelto,codigoError y codigoArticulo.
var listadoModificadosP87 = new Array();

var tableFilter=null;

//Se inicializa resultadoValidacionP87P84 en 'S' para que antes de editar stockDevuelto se pueda paginar sin problemas
var resultadoValidacionP87 = 'S';

////Definimos ultimoBultoIntroducidoP84 para guardar el último bulto introducido por proveedor y que se copie en los demás bultos
var ultimoBultoIntroducido = {};

var codRMAModificado87 = false;

//Variable para calcular la posicion X del tooltip
var p87PositionTooltipX = 0;
//var pos = $(this).position();
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************** DOCUMENTO LISTO **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

$(document).ready(function(){
	//Inicializa el grid
	loadP87(locale);

	//Inicializa el popup que contiene el grid con las líneas de devolución
	initializeScreenP87PopupDevolCreadaCentro();

	//Nos posicionamos en el campo al que ha navegado el usuario y que hemos almacenado en el hidden.
	//De esta forma al clicar aceptar en la alerta, vuelve al último campo de edición del grid.
	$("#p03_btn_aceptar").bind("click", function(e) {
		if ($("#p87_fld_StockDevuelto_Selecc").val() != '' && $("#p87_fld_StockDevuelto_Selecc").val() != undefined)
		{
			$("#"+$("#p87_fld_StockDevuelto_Selecc").val()).focus();
			$("#"+$("#p87_fld_StockDevuelto_Selecc").val()).select();
			e.stopPropagation();
			$("#p87_fld_StockDevuelto_Selecc").val('');
		}
	});

	//Eventos flecha formularios
	events_p87_btn_actualizarDevolucionLineasP87();

	events_p87_btn_impresora();
});


/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************ INICIALIZACIÓN DE ELEMENTOS E IDIOMA *******************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

/************************ IDIOMA Y GRID ************************/
function loadP87(locale){
	//Define la estructura del grid, sus columnas de dónde van a obtener los datos etc.
	gridP87 = new GridP87DevolCreadaCentro(locale);

	//Define el ancho del grid.
	$(gridP87.nameJQuery).jqGrid('setGridWidth',950, true);
	

	//Carga en data los nombres de las colummnas del grid, etc.
	var jqxhr = $.getJSON(gridP87.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		//Carga el nombre de las columnas, titulo, etc.
		gridP87.colNames = data.ColNames;
		gridP87.title = data.GridTitle;
		gridP87.emptyRecords= data.emptyRecords;
		iconoGuardado = data.iconoGuardado; 
		iconoModificado = data.iconoModificado; 	
		stockDevueltoIncorrecto = data.stockDevueltoIncorrecto;
		stockDevueltoEnteroErroneo = data.stockDevueltoEnteroErroneo;
		stockDevueltoDecimalErroneo = data.stockDevueltoDecimalErroneo;
		stockDevueltoDecimalErroneo2 = data.stockDevueltoDecimalErroneo2;
		sumaSuperaMaximoP87 = data.sumaSuperaMaximoP87;
		emptyListaModificados = data.emptyListaModificados;
		guardadoCorrectamente = data.guardadoCorrectamente;
		lineasActualizadas = data.lineasActualizadas;
		lineaActualizada = data.lineaActualizada;
		cabeceraActualizada = data.cabeceraActualizada;
		lineasActualizadasY = data.lineasActualizadasY;
		lineaActualizadaY = data.lineaActualizadaY;
		creadaCentro = data.creadaCentro;
		notPosibleToReturnDataP87 = data.notPosibleToReturnData;
		errorActualizacion = data.errorActualizacion;
		errorActualizacionRegistro = data.errorActualizacionRegistro;
		lineasErroneas = data.lineasErroneas;
		lineaErronea = data.lineaErronea;
		cantidadMaximaNoSuperableP87 = data.cantidadMaximaNoSuperable;

		stockDevueltoTitleNoPrepararMercancia = data.stockDevueltoTitleNoPrepararMercancia;
		stockDevueltoTitlePrepararMercancia = data.stockDevueltoTitlePrepararMercancia;
		stockDevueltoNoPrepararMercanciaTitle = data.stockDevueltoTitle;
		stockDevueltoPrepararMercanciaTitle = data.stockDevueltoPrepararMercanciaTitle;


		tableFilter = data.tableFilter;

		//Definimos las columnas, las llamadas al paginar, ordenar etc.
		load_gridP87DevolCreadaCentroMock(gridP87);

		$("#p87_cmb_proveedor").combobox(null);
		p87SetHeadersTitles(data);
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

/************************ GRID ************************/
//Clase de constantes para el GRID de cantidades no servidas para una referencia
//Define la estructura del grid
function GridP87DevolCreadaCentro (locale){
	// Atributos
	this.name = "gridP87DevolCreadaCentro"; 
	this.nameJQuery = "#gridP87DevolCreadaCentro"; 



	this.i18nJSON = './misumi/resources/p87PopUpDevolCreadaCentro/p87PopUpDevolCreadaCentro_' + locale + '.json';


	this.colNames = null; //Está en el json
	this.cm = [{
		"name"      : "marca",
		"index"     : "marca",
		"width"     : 50,
		"align"     : "left",
	},{
		"name"      : "codArticulo",
		"index"     : "codArticulo",
		"width"     : 80,
		"align"     : "left"
	},{
		"name"      : "denominacion",
		"index"     : "denominacion", 
		"width"     : 135,	
		"align"     : "left"						
	},{
		"name"      : "modelo",
		"index"     : "modelo", 
		"width"     : 150,	
		"align"     : "left"						
	},{
		"name"      : "descrTalla",
		"index"     : "descrTalla", 
		"width"     : 70,	
		"align"     : "left"						
	},{
		"name"      : "descrColor",
		"index"     : "descrColor", 
		"width"     : 80,	
		"align"     : "left"						
	},{
		"name"      : "modeloProveedor",
		"index"     : "modeloProveedor", 
		"width"     : 100,	
		"align"     : "left"						
	},{
		"name"      : "costeUnitario",
		"index"     : "costeUnitario", 
		"width"     : 70,	
		"formatter" : costeUnitarioFormatterP87,
		"align"     : "left"						
	},{
		"name"      : "stockActual",
		"index"     : "stockActual",
		"width"     : 40,
		"fixed":true,
		"formatter" :  stockFormatterP87,
		"align"     : "left",

	},{
		"name"      : "stockTienda",
		"index"     : "stockTienda",
		"width"     : 40,
		"fixed":true,
		"formatter" :  stockFormatterP87,
		"align"     : "left",

	},{
		"name"      : "stockDevolver",
		"index"     : "stockDevolver",
		"width"     : 40,
		"formatter" : stockFormatterP87,
		"align"     : "left",

	},{
		"name"      : "stockDevuelto",
		"index"     : "stockDevuelto",
		"width"     : 40,	        	  
		"editable"  : true,	
		"fixed" : true,
		"formatter" :  stockFormatterP87,
		"editoptions":{
			"size":"3",
			"maxlength": maxNumEnteros + maxNumDecimales + 1,
			"dataEvents": [
			               {//control para el keydown
			            	   type: 'keydown',
			            	   fn: controlNavegacionP87,
			               },
			               {//control para el click
			            	   type: 'click',
			            	   fn: controlClickP87columnaStockDevuelto
			               }
			               ] 
		},
		"align"     : "left",
	},{
		"name"      : "cantAbonada",
		"index"     : "cantAbonada",
		"width"     : 40,	
		"fixed":true,
		"formatter" : cantAbonadaFormatterP87,
		"align"     : "left",

	},{
		"name"      : "descAbonoError",
		"index"     : "cantAbonada",
		"width"     : 40,	
		"formatter" : descAbonoErrorFormatterP87,
		"align"     : "left",

	},{
		"name"      : "bulto",
		"index"     : "bulto",
		"width"     : 40,
		"editable"  : true,
		"fixed" : true,
		"editoptions":{
			"size":"3",
			"maxlength":"2",
			"dataEvents": [
			               {//control para el keydown
			            	   type: 'keydown',
			            	   fn: controlNavegacionP87,
			               },// cierra el control del keydown
			               {//control para el click
			            	   type: 'click',
			            	   fn: controlClickP87
			               }
			               ] 
		},
		"align"     : "left"

	},{
		"name"      : "formatoDevuelto",
		"index"     : "formatoDevuelto",
		"width"     : 70,
		"fixed":true,
		"align"     : "left",

	},{
		"name"      : "tipoReferencia",
		"index"     : "tipoReferencia",
		"width"     : 70,
		"fixed":true,
		"align"     : "left",

	},{
		"name" : "mensaje",
		"index":"mensaje", 
		"formatter": imageFormatMessageP87,
		"fixed":true,
		"width" : 30,
		"sortable" : true
	}	
	]; 
	this.locale = locale;
	//this.sortIndex = "";
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerGridp87";  
	this.pagerNameJQuery = "#pagerGridp87";
	this.title = null; //Está en el json
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null; //Está en el json
	this.rownumbers = true;
	this.myColumnsState = null;
	this.isColState = null;
	this.firstLoad = true;
	this.formatoFecha = "";

	//Metodos		
	this.getActualPage = function getActualPage(){ 
		if (this.actualPage == null)
			this.actualPage = 1;
		else
			this.actualPage = $(this.nameJQuery).getGridParam('page');

		return this.actualPage;
	};

	this.getSelectedRow = function getSelectedRow(){ 
		return $(this.nameJQuery).getGridParam('selrow'); 
	};

	this.getCellValue = function getCellValue(rowId, colName){ 
		return $(this.nameJQuery).getRowData(rowId)[colName]; 
	};

	this.getRowNumPerPage = function getRowNumPerPage () {
		return $(this.nameJQuery).getGridParam('rowNum');
	} ;

	this.getSortIndex = function getSortIndex () {
		if ($(this.nameJQuery).getGridParam('sortname')!=null){
			return $(this.nameJQuery).getGridParam('sortname');
		}else{
			return null;
		}

	} 

	this.getSortOrder = function getSortOrder () {

		if ($(this.nameJQuery).getGridParam('sortorder')!=null){
			return $(this.nameJQuery).getGridParam('sortorder');
		}else{
			return "asc";
		}
	} 	

	this.clearGrid = function clearGrid() {
		$(this.nameJQuery).jqGrid('GridUnload');
	}

	this.headerHeight = function headerHeight(cssClass) {
		var colModel = $(this.nameJQuery).jqGrid('getGridParam','colModel');
		for (i = 0; i <= this.colNames.length; i++){
			$(this.nameJQuery).setLabel(colModel[i].name, '', cssClass);
		}
	}

	this.saveObjectInLocalStorage = function (storageItemName, object) {
		if (typeof window.localStorage !== 'undefined') {
			window.localStorage.setItem(storageItemName, JSON.stringify(object));
		}
	}

	this.removeObjectFromLocalStorage = function (storageItemName) {
		if (typeof window.localStorage !== 'undefined') {
			window.localStorage.removeItem(storageItemName);
		}
	}

	this.getObjectFromLocalStorage = function (storageItemName) {
		if (typeof window.localStorage !== 'undefined') {
			return JSON.parse(window.localStorage.getItem(storageItemName));
		}
	}

	this.saveColumnState = function (perm) {
		var colModel = jQuery(gridP87.nameJQuery).jqGrid('getGridParam', 'colModel'); 
		var i;
		var l = colModel.length;
		var colItem; 
		var cmName;
		var postData = jQuery(gridP87.nameJQuery).jqGrid('getGridParam', 'postData');
		var columnsState = {
				search: jQuery(gridP87.nameJQuery).jqGrid('getGridParam', 'search'),
				page: jQuery(gridP87.nameJQuery).jqGrid('getGridParam', 'page'),
				sortname: jQuery(gridP87.nameJQuery).jqGrid('getGridParam', 'sortname'),
				sortorder: jQuery(gridP87.nameJQuery).jqGrid('getGridParam', 'sortorder'),
				permutation: perm,
				colStates: {}
		};
		var colStates = columnsState.colStates;

		if (typeof (postData.filters) !== 'undefined') {
			columnsState.filters = postData.filters;
		}

		for (i = 0; i < l; i++) {
			colItem = colModel[i];
			cmName = colItem.name;
			if (cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {
				colStates[cmName] = {
						width: colItem.width,
						hidden: colItem.hidden
				};
			}
		}
		//this.saveObjectInLocalStorage(this.myColumnStateName, columnsState);
		if (typeof window.localStorage !== 'undefined') {
			window.localStorage.setItem(this.myColumnStateName, JSON.stringify(columnsState));
		}
	}

	this.restoreColumnState = function (colModel) {
		var colItem, i, l = colModel.length, colStates, cmName,
		columnsState = this.getObjectFromLocalStorage(this.myColumnStateName);

		if (columnsState) {
			colStates = columnsState.colStates;
			for (i = 0; i < l; i++) {
				colItem = colModel[i];
				cmName = colItem.name;
				if (cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {
					colModel[i] = $.extend(true, {}, colModel[i], colStates[cmName]);
				}
			}
		}
		return columnsState;
	}
}

//Función que sirve para especificar nombres de columnas, y qué ocurren en las paginaciones, ordenaciones etc.
function load_gridP87DevolCreadaCentroMock(gridP87) {

	$(gridP87.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		datatype : 'local',
		contentType : 'application/json',
		mtype : 'POST',
		colNames : gridP87.colNames,
		colModel : gridP87.cm,
		rowNum : 10,
		rowList : [ 10, 30, 50 ],
		height : "100%",
		autowidth : true,
		width : "100%",
		rownumbers : gridP87.rownumbers,
		pager : gridP87.pagerNameJQuery,
		viewrecords : true,
		caption : gridP87.title,
		altclass: "ui-priority-secondary",
		altRows: true, //false, para que el grid no muestre cebrado
		hidegrid : false, //false, para ocultar el boton que colapsa el grid.
		sortable : true,
		index: gridP87.sortIndex,
		sortname: gridP87.sortIndex,
		sortorder: gridP87.sortOrder,
		emptyrecords : gridP87.emptyRecords,
		gridComplete : function() {
			gridP87.headerHeight("p87_gridHeader");		

			//Una vez cargado el grid, iniciamos el contador a 1.
			//Al ser una variable global y actualizarse cada vez
			//que se abre un nuevo grid en la función imageFormatMessageP87
			//obtiene valores no acordes con el número de fila cargado
			//por eso hay que iniciarlo a 1.
			numRowP87 = 1;
		},
		loadComplete : function(data) {
			gridP87.actualPage = data.page;
			gridP87.localData = data;
			gridP87.sortIndex = gridP87.sortIndex;
			gridP87.sortOrder = gridP87.sortOrder;	

			//Para el filtro del grid
			$(gridP87.nameJQuery).jqGrid('filterToolbar',{defaultSearch : "cn"});
			$("#gbox_gridP87DevolCreadaCentro #gs_marca").css("display", "none");
			$("#gbox_gridP87DevolCreadaCentro #gs_denominacion").css("display", "none");
			$("#gbox_gridP87DevolCreadaCentro #gs_modelo").css("display", "none");
			$("#gbox_gridP87DevolCreadaCentro #gs_descrTalla").css("display", "none");
			$("#gbox_gridP87DevolCreadaCentro #gs_descrColor").css("display", "none");
			$("#gbox_gridP87DevolCreadaCentro #gs_costeUnitario").css("display", "none");
			$("#gbox_gridP87DevolCreadaCentro #gs_modeloProveedor").css("display", "none");
			$("#gbox_gridP87DevolCreadaCentro #gs_stockActual").css("display", "none");
			$("#gbox_gridP87DevolCreadaCentro  #gs_stockTienda").css("display", "none");
			$("#gbox_gridP87DevolCreadaCentro #gs_stockDevuelto").css("display", "none");
			$("#gbox_gridP87DevolCreadaCentro #gs_bulto").css("display", "none");
			$("#gbox_gridP87DevolCreadaCentro  #gs_formatoDevuelto").css("display", "none");	
			$("#gbox_gridP87DevolCreadaCentro  #gs_tipoReferencia").css("display", "none");
			$("#gbox_gridP87DevolCreadaCentro #gs_mensaje").css("display", "none");
			$("#gbox_gridP87DevolCreadaCentro  #gs_cantAbonada").css("display", "none");
			$("#gbox_gridP87DevolCreadaCentro #gs_descAbonoError").css("display", "none");
			
			$("#gbox_gridP87DevolCreadaCentro #gs_codArticulo").on("keydown", function(e) {
				var classActual = e.target.className;
			  	    if(e.which == 13) {
			  	    	 if (classActual != "ui-pg-input"){
			  	    		  e.preventDefault();
			  	    		  reset_p87();
			  	    		  reloadData_FiltroP87($("#gbox_gridP87DevolCreadaCentro #gs_codArticulo").val());
					  	  
			  	    	 }
			  	    }
			  	});
			
			//Evento que autocompleta el filtro de las referencias al pulsar un número.
			//Por ejemplo, Si metemos un 1 nos saldría un combo con las referencias que
			//contengan un 1.
			refAutoCompleteP87();
			
			$("#p87_AreaReferencias .loading").css("display", "none");	
		},
		resizeStop: function () {
			gridP87.saveColumnState.call($(this),gridP87.myColumnsState);
			$(gridP87.nameJQuery).jqGrid('setGridWidth', 950, false);
		},
		onPaging : function(postdata) {	
			//Antes de introducir este control, en el caso de estar en un campo editable stockDevuelto y clicar que pagine, se ejecutaba primero el evento focusout y más tarde este evento onPaging.
			//Esto creaba situaciones en las que al introducir un campo erroneo en stockDevuelto (con más de una coma, más de 15 enteros o más de 3 decimales), primero lanzaba el error de que el campo
			//se había introducido mal y más tarde paginaba. Para ello, como el focusout realiza una validación de que el campo editable es correcto o incorrecto y devuelve una variable llamada resultadoValidacionP87 'S' o 'N',
			//se ha transformado esa variable en global. De esta forma, al realizar el focusout se realiza la validación, en el caso de haber error lo muestra e indica al evento onPaging de que no tiene que realizar
			//la recarga de datos ni la paginación. En caso de que se introduzca un campo correcto en stockDevuelto, el focusout realiza una validación correcta y recalcula los datos del grid.

			//Puede darse la situación de que no se realice el focusout de y por consecuente la validación de stockDevuelto, porque no se haya editado ningún campo o porque el usuario no se haya situado en ninguno de esos campos. Por lo cual, la variable
			//resultadoValidacionP87 se inicializa siempre en 'S'. Cuando se clica a paginar, pagine o no, resultadoValidacionP87 obtiene el valor 'S', puesto que en el caso de haber editado un dato de forma erronea se autocorrige, y queremos que si el usuario
			//clica el elemento paginar al instante, pagine.
			if(resultadoValidacionP87 == 'S'){
				alreadySorted = false;
				gridP87.sortIndex = gridP87.sortIndex;
				gridP87.sortOrder = gridP87.sortOrder;
				gridP87.saveColumnState.call($(this), this.p.remapColumns);
				reloadData_gridP87DevolCreadaCentro();

				return 'stop';
			}else{
				//Se cambia el estado de la validación a ´S´, porque anque haya error, se corrige el dato y queremos que se pueda paginar.
				resultadoValidacionP87 = 'S';

				//En caso de existir un error, no se pagina y devuelve error. Pero el componente internamente pagina, por ese motivo, hay que restar 1 a la paginación. 
				$('#gridP87DevolCreadaCentro').setGridParam({page:gridP87.getActualPage()-1});

				return 'stop';			
			}
		},
		onSelectRow: function(id){

		},
		onSortCol : function (index, columnIndex, sortOrder){
			gridP87.sortIndex = index;
			gridP87.sortOrder = sortOrder;
			gridP87.saveColumnState.call($(this), this.p.remapColumns);
			reloadData_gridP87DevolCreadaCentro();
			return 'stop';
		},
		jsonReader : {
			root : "rows",
			page : "page",
			total : "total",
			records : "records",
			repeatitems : false
		},
		loadError : function (xhr, status, error){
			handleError(xhr, status, errgridale);
		}
	});
}


/************************ POPUP ************************/
function initializeScreenP87PopupDevolCreadaCentro(){

	$( "#p87_popUpDevolCreadaCentro" ).dialog({
		autoOpen: false,
		height: 'auto',
		width: 'auto',
		modal: true,
		resizable: false,
		open: function() {
			$('#p87_popUpDevolCreadaCentro .ui-dialog-titlebar-close').on('mousedown', function(){
				//Antes de cerrar el popup reseteamos el array de seleccionadosP87
				seleccionadosP87 = new Array();
				$("#p87_popUpDevolCreadaCentro").dialog('close');
			});
		},
		close: function( event, ui ) {
			//Al salir eliminar la lista de seleccionadosP87 y de listadoModificadosP87.
			//Además limpiar grid y poner elementos a buscar en 10
			reset_p87();
		},
		//Fijar el z-index del popup p56 para que las opciones del combobox no se queden detrás del popup 
		//al autoincrementarse el z-index.
		stack: false

	});

	$(window).bind('resize', function() {
		$("#p87_popUpDevolCreadaCentro").dialog("option", "position", "center");
	});
}

/************************ TITULOS COLUMNAS GRID ************************/
function p87SetHeadersTitles(data){
	var colModel = $(gridP87.nameJQuery).jqGrid("getGridParam", "colModel");
	$.each(colModel, function(i) {
		if (colModel[i].name!="rn"){
			$("#jqgh_gridP87DevolCreadaCentro_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
		}
	});
}

/************************ EVENTOS ************************/
//Evento para flechas 
function events_p87_btn_actualizarDevolucionLineasP87(){
	$("#p87_btn_guardado").click(function () {		
		actualizarDevolucionLineasP87();
	});	
}
//Evento para botón de buscar
function events_p87_btn_impresora(){
	$("#p87_btn_impresora" ).unbind("click");
	$("#p87_btn_impresora").click(function () {		
		imprimirDevolucionesCreadaCentroP87();
	});	
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************EVENTOS CAMPOS  DEL GRID ********************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

function controlNavegacionP87(e) {

	var idActual = e.target.id;
	var idFoco;
	
	//Obtención de fila y columna actuales
	var fila = idActual.substring(0, idActual.indexOf("_"));
	var nombreColumna = idActual.substring(idActual.indexOf("_")+1);
	var validacionNavegacion = 'S';

	var key = e.which; //para soporte de todos los navegadores
	if (key == 13){//Tecla enter, guardado
		e.preventDefault();

		if(nombreColumna == "stockDevuelto"){
			validacionNavegacion = validacionStockDevueltoP87(fila + "_stockDevuelto", 'N');
			if (validacionNavegacion =='S'){

				//Se llama a la función directamente, porque en esta función se calcula el elemento modificado, que indica si se ha modificado un
				//elemento del grid. En el caso de haberse modificado un elemento, se actualizan las líneas. Anteriormente, se hacía un focusout para
				//que saltara esta misma función, pero al ejecutar focusout, ejecutaba actualizarDevolucionLineasP87 antes que validacionStockDevueltoP87 se ejecutara, por lo que
				//no daba tiempo a calcular que el elemento se había modificado y no se actualizaba la línea.
				validacionStockDevueltoP87(this.id, 'S');
				
				//Guarda los datos modificadosP87
				actualizarDevolucionLineasP87();
			}else{
				$("#p82_btn_buscar").focus();
			}
		}
		if(nombreColumna == "bulto"){
			validacionNavegacion = validacionBultoP87(fila + "_bulto", 'N');
			if (validacionNavegacion =='S'){

				//Se llama a la función directamente, porque en esta función se calcula el elemento modificado, que indica si se ha modificado un
				//elemento del grid. En el caso de haberse modificado un elemento, se actualizan las líneas. Anteriormente, se hacía un focusout para
				//que saltara esta misma función, pero al ejecutar focusout, ejecutaba actualizarDevolucionLineasP87 antes que validacionBultoP87 se ejecutara, por lo que
				//no daba tiempo a calcular que el elemento se había modificado y no se actualizaba la línea.
				validacionBultoP87(this.id, 'S');

				//Guarda los datos modificadosP87
				actualizarDevolucionLineasP87();
			}else{
				$("#p82_btn_buscar").focus();	        	
			}
		}
	}

	//Flechas de cursores para navegación	
	//Tecla izquierda
	if (key == 37){
		e.preventDefault();

		if(nombreColumna == "bulto"){
			idFoco = fila + "_stockDevuelto";
			validacionNavegacion = validacionBultoP87(fila + "_bulto", 'N');
			if (validacionNavegacion =='S'){
				$("#"+idFoco).focus();
				$("#"+idFoco).select();
				$("#p87_fld_Bulto_Selecc").val(idFoco);
				controlClickP87columnaStockDevuelto(e,key);
			}else{
				$("#p82_btn_buscar").focus();	        	
			}
		}
	}

	//Tecla derecha
	if (key == 39){
		e.preventDefault();
		if(nombreColumna == "stockDevuelto"){
			idFoco = fila + "_bulto";
			validacionNavegacion = validacionStockDevueltoP87(fila + "_stockDevuelto", 'N');
			if (validacionNavegacion=='S'){
				$("#"+idFoco).focus();
				$("#"+idFoco).select();
				$("#p87_fld_Bulto_Selecc").val(idFoco);
				controlClickP87(e,key);
			}else{
				$("#p82_btn_buscar").focus();
			}
		}    	  	
	}

	//Tecla arriba
	if (key == 38){
		e.preventDefault();
		idFoco = (parseInt(fila,10)-1) + "_" + nombreColumna;
		if(nombreColumna == "bulto"){
			validacionNavegacion = validacionBultoP87(fila + "_bulto", 'N');
		}
		if(nombreColumna == "stockDevuelto"){
			validacionNavegacion = validacionStockDevueltoP87(fila + "_stockDevuelto", 'N');
		}
		if (validacionNavegacion=='S'){    		    			    		    		
			$("#"+idFoco).focus();
			$("#"+idFoco).select();
			$("#p87_fld_Bulto_Selecc").val(idFoco);  

			if(nombreColumna == "bulto"){
				controlClickP87(e,key);
			}

			if(nombreColumna == "stockDevuelto"){
				controlClickP87columnaStockDevuelto(e,key);
			}
		}else{
			$("#p82_btn_buscar").focus();   	
		}
	}

	//Tecla abajo
	if (key == 40){
		e.preventDefault();
		idFoco = (parseInt(fila,10)+1) + "_" + nombreColumna;

		if(nombreColumna == "bulto"){
			validacionNavegacion = validacionBultoP87(fila + "_bulto", 'N');
		}
		if(nombreColumna == "stockDevuelto"){
			validacionNavegacion = validacionStockDevueltoP87(fila + "_stockDevuelto", 'N');
		}

		if (validacionNavegacion=='S'){    		    			
			$("#"+idFoco).focus();
			$("#"+idFoco).select();
			$("#p87_fld_Bulto_Selecc").val(idFoco);
			if(nombreColumna == "bulto"){
				controlClickP87(e,key);
			}

			if(nombreColumna == "stockDevuelto"){
				controlClickP87columnaStockDevuelto(e,key);
			}
		}else{
			$("#p82_btn_buscar").focus();
		}
	}

	//Tecla tabulacion
	if(key == 9){
		e.preventDefault();

		//Si se presiona tabulación más shift
		if(e.shiftKey){
			//Si estamos en stockDevuelto, volvemos a la fila anterior a la sección bulto. Si estamos en bulto, en la misma fila, volvemos a stockDevuelto.
			if(nombreColumna == "stockDevuelto"){
				idFoco = (parseInt(fila,10)-1) + "_bulto";
			}else{
				idFoco = fila + "_stockDevuelto";
			}
		}else{
			//Si estamos en stockDevuelto, en la misma fila, vamos a bulto. Si estamos en bulto, vamos a la fila siguiente a stockDevuelto.
			if(nombreColumna == "stockDevuelto"){
				idFoco = fila + "_bulto";
			}else{
				idFoco = (parseInt(fila,10) + 1) + "_stockDevuelto";
				controlClickP87columnaStockDevuelto(e,key);
			}								
		}

		if(nombreColumna == "stockDevuelto"){
			validacionNavegacion = validacionStockDevueltoP87(fila + "_stockDevuelto", 'N');
			if (validacionNavegacion =='S'){ 
				$("#"+idFoco).focus();
				$("#"+idFoco).select();
				$("#p87_fld_Bulto_Selecc").val(idFoco);

				controlClickP87(e,key);
			}else{
				$("#p82_btn_buscar").focus();
			}
		}
		if(nombreColumna == "bulto"){
			validacionNavegacion = validacionBultoP87(fila + "_bulto", 'N');
			if (validacionNavegacion =='S'){
				$("#"+idFoco).focus();
				$("#"+idFoco).select();
				$("#p87_fld_Bulto_Selecc").val(idFoco);

				controlClickP87columnaStockDevuelto(e,key);

			}else{
				$("#p82_btn_buscar").focus();	        	
			}
		}
	}
}

//En el caso de no tener valor la 1 fila de bulto pone un 0. En el caso de que el bulto de un elemento sea vacío pero el de su anterior
//no, se pone el valor del bulto anterior en ese elemento.
function controlClickP87(e,key){
	var idActual = e.target.id;

	//Obtención de fila y columna actuales
	var fila = idActual.substring(0, idActual.indexOf("_"));
	//Si se ha pulsado la flecha de arriba, la fila seleccionada será la actual - 1
	//Si se ha pulsado la flecha de abajo, la fila seleccionada será la actual + 1
	if(key == 38 || e.shiftKey){
		fila = parseInt(fila) - 1;
	}else if(key == 40){
		fila = parseInt(fila) + 1;
	}	
	
	var provrGenFila = "#"+fila+"_provrGenLin";
	var provrGenFilaValor = $(provrGenFila).val();

	var filaBultoActual = "#"+fila+"_bulto";
	var filaStockDevueltoActual = "#"+fila+"_stockDevuelto";
	var valorStockDevueltoActual = $(filaStockDevueltoActual).val();

	if (valorIdActual == null && valorStockDevueltoActual != "" && valorStockDevueltoActual != "0") {
		var valorIdActual = $(filaBultoActual).val();
		if(valorIdActual == "" || valorIdActual == null || valorIdActual == "null"){
			$(filaBultoActual).val(ultimoBultoIntroducido[provrGenFilaValor]);
			$(filaBultoActual).select();
		}	
	}else if (valorStockDevueltoActual == "0") {
		var valorIdActual = $(filaBultoActual).val();
		$(filaBultoActual).val("0");
	}
}


function controlClickP87columnaStockDevuelto(e,key){
	
	console.log("Dentro de controlClickP87columnaStockDevuelto");
	var idActual = e.target.id;

	//Obtención de fila y columna actuales
	var fila = idActual.substring(0, idActual.indexOf("_"));

	var provrGenFila = "#"+fila+"_provrGenLin";
	var provrGenFilaValor = $(provrGenFila).val();

	var filaBultoActual = "#"+fila+"_bulto";
	var filaStockDevueltoActual = "#"+fila+"_stockDevuelto";
	var valorStockDevueltoActual = $(filaStockDevueltoActual).val();

	console.log("valorStockDevueltoActual " + valorStockDevueltoActual);
	if (valorStockDevueltoActual != "" && valorStockDevueltoActual != "0") {
		var valorIdActual =  $(filaBultoActual).val();
		console.log("valorIdActual(bulto) " + valorIdActual);
		if(valorIdActual == "" || valorIdActual == null || valorIdActual == "null"){
			console.log("Dentro del If");
			//Se inserta en la fila actual el valor de la fila anterior
			$(filaBultoActual).val(ultimoBultoIntroducido[provrGenFilaValor]);

			console.log("	$(filaBultoActual).val() " + 	$(filaBultoActual).val());
			//Si se ha pulsado la flecha de arriba, la fila seleccionada será la actual - 1
			//Si se ha pulsado la flecha de abajo, la fila seleccionada será la actual + 1
			if(key == 38 || e.shiftKey){
				fila = parseInt(fila) - 1;
			}else if(key == 40 || key == 9){
				fila = parseInt(fila) + 1;
			}		
			
			var filaStockDevueltoFoco = "#"+fila+"_stockDevuelto";
			$(filaStockDevueltoFoco).select();

		}	
	}
}

function controlChangecolumnaStockDevueltoP87(idActual, gridActual){

	//Obtención de fila y columna actuales
	var fila = idActual.substring(0, idActual.indexOf("_"));

	var provrGenFila = "#"+fila+"_provrGenLin";
	var provrGenFilaValor = $(provrGenFila).val();

	var filaBultoActual = "#"+fila+"_bulto";
	var filaStockDevueltoActual = "#"+fila+"_stockDevuelto";
	var valorStockDevueltoActual = $(filaStockDevueltoActual).val();
	
	//BAZAR
	var valorCosteFinalActual = $(gridActual + " #"+fila+"_costeFinal").val();  //Valor del precio costo final antes de modificar 
	var sumaAux = $("#p87_lbl_ValorSumaSinFormateo").val(); //Valor de del campo sumatorio
	var valorCosteUnitarioActual = $(gridActual + " #"+fila+"_costeUnitarioAux").val();  //Valor del precio costo unitario antes de modificar

	if(sumaAux){
		sumaAux = Number(sumaAux.replace(',','.'));
	}
	if(valorStockDevueltoActual){
		valorStockDevueltoActual = Number(valorStockDevueltoActual.replace(',','.'));
	}
	if(valorCosteUnitarioActual){
		valorCosteUnitarioActual = Number(valorCosteUnitarioActual.replace(',','.'));
	}
	
	var suma = sumaAux - valorCosteFinalActual; //Primero restamos el costo final de la referencia que estamos tratando.
	var costeFinal = valorCosteUnitarioActual * valorStockDevueltoActual;
	suma = suma + costeFinal;
	
    var maximo = Number($("#p87_lbl_ValorCosteMaximoHidden").val()); //Valor de del campo Maximo
	
	if (suma > maximo) { // si la suma supera a los euros maximos, ambos campos hay que pintarlos en rojo
		
		$("#p87_lbl_TextoSuma").addClass("p87TextoRojo");
		$("#p87_lbl_ValorSuma").removeAttr( "style" );
		$("#p87_lbl_ValorSuma").attr("style", "color:red; font-size:12px;");
		$("#p87_lbl_TextoCosteMaximo").addClass("p87TextoRojo");
		$("#p87_lbl_ValorCosteMaximo").addClass("p87TextoRojo");
		
	} else {
		$("#p87_lbl_TextoSuma").removeClass("p87TextoRojo");
		$("#p87_lbl_ValorSuma").removeAttr( "style" );
		$("#p87_lbl_ValorSuma").attr("style", "font-size:12px;");
		$("#p87_lbl_TextoCosteMaximo").removeClass("p87TextoRojo");
		$("#p87_lbl_ValorCosteMaximo").removeClass("p87TextoRojo");
	}
	
	$("#p87_lbl_ValorSumaSinFormateo").val(suma);
	$("#p87_lbl_ValorSuma").val($.formatNumber(suma,{format:"0.#",locale:"es"}));
	$(gridActual + " #"+fila+"_costeFinal").val(costeFinal);

	if (valorStockDevueltoActual == "0"){
		$(filaBultoActual).val("0");
	}					
}

/** valida si la suma excede demasiado el maximo */
function validateMaximoCambioStockP87(idActual, gridActual){
	
	var valido = true;
	
	 //Valor de del campo Maximo
    var maximo = Number($("#p87_lbl_ValorCosteMaximoHidden").val());
    
    if(maximo > 0){

		//Obtención de fila y columna actuales
		var fila = idActual.substring(0, idActual.indexOf("_"));
		
		var valorStockDevueltoActual = $("#"+fila+"_stockDevuelto").val();
	
		if(valorStockDevueltoActual){
			valorStockDevueltoActual = Number(valorStockDevueltoActual.replace(',','.'));
		}
		
		//BAZAR
		var valorCosteFinalActual = $(gridActual + " #"+fila+"_costeFinal").val();  //Valor del precio costo final antes de modificar 
		var sumaAux = $("#p87_lbl_ValorSumaSinFormateo").val(); //Valor de del campo sumatorio
		var valorCosteUnitarioActual = $(gridActual + " #"+fila+"_costeUnitarioAux").val();  //Valor del precio costo unitario antes de modificar
	
		if(sumaAux){
			sumaAux = Number(sumaAux.replace(',','.'));
		}
		if(valorCosteFinalActual){
			valorCosteFinalActual = Number(valorCosteFinalActual.replace(',','.'));
		}
		if(valorCosteUnitarioActual){
			valorCosteUnitarioActual = Number(valorCosteUnitarioActual.replace(',','.'));
		}
		
		var suma = sumaAux - valorCosteFinalActual; //Primero restamos el costo final de la referencia que estamos tratando.
		var costeFinal = valorCosteUnitarioActual * valorStockDevueltoActual;
		suma += costeFinal;
		
		valido = suma <= maximo * 1.01;
    }
    
    return valido;
}

function controlChangecolumnaBultoP87(idActual){

	//Obtención de fila y columna actuales
	var fila = idActual.substring(0, idActual.indexOf("_"));

	var filaBultoActual = "#"+fila+"_bulto";
	var filaStockDevueltoActual = "#"+fila+"_stockDevuelto";
	var valorStockDevueltoActual = $(filaStockDevueltoActual).val();
	
	var provrGenFila = "#"+fila+"_provrGenLin";
	var provrGenFilaValor = $(provrGenFila).val();

	if (valorStockDevueltoActual != null && valorStockDevueltoActual != "" && valorStockDevueltoActual != "0") {
		var valorIdActual =  $(filaBultoActual).val();
		if(valorIdActual == "" || valorIdActual == null || valorIdActual == "null"){
			//Se inserta en la fila actual el valor de la fila anterior
			$(filaBultoActual).val(ultimoBultoIntroducido[provrGenFilaValor]);
		}						
	}else if (valorStockDevueltoActual == "0"){
		$(filaBultoActual).val("0");
	}	
}

function controlChangecolumnaBultoP87(idActual){

	//Obtención de fila y columna actuales
	var fila = idActual.substring(0, idActual.indexOf("_"));
	var filaBultoActual = "#"+fila+"_bulto";
	
	var provrGenFila = "#"+fila+"_provrGenLin";
	var provrGenFilaValor = $(provrGenFila).val();

	var valorIdActual =  $(filaBultoActual).val();
	if(valorIdActual != "" && valorIdActual != null && valorIdActual != "0" && valorIdActual != "null"){
		//Se inserta en la fila actual el valor de la fila anterior
		ultimoBultoIntroducido[provrGenFilaValor]=$("#"+idActual).val();
	}						
}

function imageFormatMessageP87(cellValue, opts, rData) {

	var imagen = "";
	var mostrarModificado = "none;";
	var mostrarGuardado = "none;";
	var mostrarError = "none;";
	var descError = "";

	//Controlamos los posibles errores que me lleguen para pintar el icono correspondiente.
	if (parseFloat(rData['codError']) == '8')
	{
		//Pintamos el icono de que se ha guardado
		mostrarGuardado = "block;";
	}
	else if (parseFloat(rData['codError']) == '9')
	{
		//Pintamos el icono de que se ha modificado
		mostrarModificado = "block;";
	}
	/*else if (parseFloat(rData['codError']) == '3')
	{
		//Pintamos el icono de que ha ocurrido un error
		descError = replaceSpecialCharacters(errorActualizacionRegistro);
		mostrarError = "block;";
	}
	else if (parseFloat(rData['codError']) == '4')
	{
		//Pintamos el icono de que ha ocurrido un error
		descError = replaceSpecialCharacters(errorActualizacionOrig);
		mostrarError = "block;";
	}*/
	else if (null != rData['codError'] && parseFloat(rData['codError']) != '0'){
		descError = rData['descError'];
		mostrarError = "block;";
	}


	imagen = "<div id='"+numRowP87+"_stockDevueltoBulto_divGuardado' align='center' style='display: "+ mostrarGuardado + "'><img id='"+numRowP87+"_stockDevueltoBulto_imgGuardado' src='./misumi/images/floppy.png' title='"+iconoGuardado+"'/></div>"; //Guardado
	imagen += "<div id='"+numRowP87+"_stockDevueltoBulto_divModificado' align='center' style='display: "+ mostrarModificado + "'><img id='"+numRowP87+"_stockDevueltoBulto_imgModificado' src='./misumi/images/modificado.png' title='"+iconoModificado+"'/></div>"; //Modificado
	imagen += "<div id='"+numRowP87+"_stockDevueltoBulto_divError' align='center' style='display: " + mostrarError + "'><img id='"+numRowP87+"_stockDevueltoBulto_imgError' src='./misumi/images/dialog-error-24.png' title='"+descError+"'/></div>"; //Error		

	var stockOrig = rData['stockDevueltoOrig'];
	var stock = rData['stockDevuelto'];
	var datoStockDevueltoOrig;
	var datoStockDevuelto;

	if(stockOrig != null){
		datoStockDevueltoOrig = "<input type='hidden' id='"+numRowP87+"_stockDevuelto_orig' value='"+$.formatNumber(stockOrig,{format:"0.###",locale:"es"})+"'>";
	}else{
		datoStockDevueltoOrig = "<input type='hidden' id='"+numRowP87+"_stockDevuelto_orig' value=''>";
	}

	if(stock != null){
		datoStockDevuelto = "<input type='hidden' id='"+numRowP87+"_stockDevuelto_tmp' value='"+$.formatNumber(stock,{format:"0.###",locale:"es"})+"'>";
	}else{		
		datoStockDevuelto = "<input type='hidden' id='"+numRowP87+"_stockDevuelto_tmp' value=''>";
	}

	imagen +=  datoStockDevueltoOrig;
	imagen +=  datoStockDevuelto;

	var bultoOrig = rData['bultoOrig'];
	var bulto = rData['bulto'];
	var datoBultoOrig;
	var datoBulto;

	if(bultoOrig != null){
		datoBultoOrig = "<input type='hidden' id='"+numRowP87+"_bulto_orig' value='"+bultoOrig+"'>";
	}else{
		datoBultoOrig = "<input type='hidden' id='"+numRowP87+"_bulto_orig' value=''>";
	}

	if(bulto != null){
		datoBulto = "<input type='hidden' id='"+numRowP87+"_bulto_tmp' value='"+bulto+"'>";
	}else{
		datoBulto = "<input type='hidden' id='"+numRowP87+"_bulto_tmp' value=''>";
	}
	imagen +=  datoBultoOrig;
	imagen +=  datoBulto;

	//Se guarda en un hidden el flag de las bandejas. Si es S, se muestra el popup, si no, no se deja
	//el input.
	var flgBandejas = rData['flgBandejas'];
	var datoFlgBandejas;

	if(flgBandejas != null){
		datoFlgBandejas = "<input type='hidden' id='"+numRowP87+"_flgBandejas' value='"+flgBandejas+"'>";
	}else{
		datoFlgBandejas = "<input type='hidden' id='"+numRowP87+"_flgBandejas' value=''>";
	}
	imagen +=  datoFlgBandejas;
	
	//Se guarda en un hidden el dato de las bandejas.
	var stockDevueltoBandejas = rData['stockDevueltoBandejas'];
	var datoStockDevueltoBandejas;

	if(stockDevueltoBandejas != null){
		datoStockDevueltoBandejas = "<input type='hidden' id='"+numRow+"_stockDevueltoBandejas_tmp' value='"+$.formatNumber(stockDevueltoBandejas,{format:"0.###",locale:"es"})+"'>";
	}else{
		datoStockDevueltoBandejas = "<input type='hidden' id='"+numRow+"_stockDevueltoBandejas_tmp' value=''>";
	}
	imagen +=  datoStockDevueltoBandejas;
	
	//Se guarda en un hidden el dato de los kgs original.
	var stockDevueltoBandejasOrig = rData['stockDevueltoBandejasOrig'];
	var datoStockDevueltoBandejasOrig;

	if(stockDevueltoBandejasOrig != null){
		datoStockDevueltoBandejasOrig = "<input type='hidden' id='"+numRow+"_stockDevueltoBandejas_orig' value='"+$.formatNumber(stockDevueltoBandejasOrig,{format:"0.###",locale:"es"})+"'>";
	}else{
		datoStockDevueltoBandejasOrig = "<input type='hidden' id='"+numRow+"_stockDevueltoBandejas_orig' value=''>";
	}
	imagen +=  datoStockDevueltoBandejasOrig;
	
	//Añadimos también el valor del codError de cada registro.
	var datoError = "<input type='hidden' id='"+numRowP87+"_stockDevueltoBulto_codError_orig' value='"+rData['codError']+"'>";
	imagen +=  datoError;

	//Rellenamos un hidden que guardará el dato que contendrá el primer elemento de bulto de cada paginación en el caso de estar vacío. 
	if(numRowP87 == 1){
		var primerElementoBulto = "<input type='hidden' id='primerElementoBulto' value='"+rData['primerElementoBulto']+"'>";	
		imagen += primerElementoBulto;
	}
	//Rellenamos un hidden que guardará el dato que contendrá el primer elemento de stockDevuelto de cada paginación en el caso de estar vacío. 
	if(numRowP87 == 1){
		var primerElementoStockDevuelto = "<input type='hidden' id='primerElementoStockDevuelto' value='"+rData['primerElementoStockDevuelto']+"'>";	
		imagen += primerElementoStockDevuelto;
	}

	var estadoLinea = "<input type='hidden' id='"+numRowP87+"_estadoLin' value='"+rData['estadoLin']+"'>";	
	imagen += estadoLinea;

	var provrGenLinea = "<input type='hidden' id='"+numRowP87+"_provrGenLin' value='"+rData['provrGen']+"'>";	
	imagen += provrGenLinea;
	
	var costeFinal = "<input type='hidden' id='"+numRowP87+"_costeFinal' value='"+rData['costeFinal']+"'>";	
	imagen += costeFinal;
	
	var costeUnitarioAux = "<input type='hidden' id='"+numRowP87+"_costeUnitarioAux' value='"+rData['costeUnitario']+"'>";	
	imagen += costeUnitarioAux;

	// no espero que ahora venga informado este campo, pero por si en un futuro llegara
	var cantidadMaximaLineaP87 = "<input type='hidden' id='"+numRowP87+"_cantidadMaximaPermitida' value='"+ (rData.cantidadMaximaPermitida || '') +"'>";
	imagen +=  cantidadMaximaLineaP87;
	
	numRowP87++;

	return imagen;
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************** VALIDACIONES CAMPOS EDITABLES DEL GRID ***************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

/** Valida la cantidad comparandola con la cantidad maxima, sobre un registro de la tabla 
 * @param cantidadTarget Input a validar con el valor de la cantidad
 * @param rowTarget Registro (td) de la tabla 
 */
function validarCantidadMaximaRowP87(cantidadTarget, filaTarget){
	var $fila = $(filaTarget);
	var fila = $fila[0].id;
	var $stockDevuelto = $(cantidadTarget);
	var $cantidadMaxima = $("#" + fila +"_cantidadMaximaPermitida", $fila);
	var esValido = true;

	// Si no está definida la cantidad maxima la consultamos
	if(!$cantidadMaxima.val() && $cantidadMaxima.val() !== 0){
		//Inicializamos y rellenamos la línea de devolución.
		var tDevLin = {
			denominacion: $("[aria-describedby=gridP87DevolCreadaCentro_denominacion]", $fila).html(),
			stockDevuelto: $stockDevuelto.val(),
			codArticulo: $("[aria-describedby=gridP87DevolCreadaCentro_codArticulo]", $fila).html()
		};
		var lstTDevLin = [tDevLin];
		var devolucion = new Devolucion(devolucionFicha.centro,null,null,null,null,null,null,null,null,
				null,null,null,null,null,null,null,null,null,null,lstTDevLin,null,null,null);
		var objJson = $.toJSON(devolucion);	
		$.ajax({
			url : './devoluciones/popup/creadaCentro/obtenerCantidadADevolver.do',
			type : 'POST',
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			cache : false,
			async: false,
			success : function(data) {
				if(data && data[0]){
					//Guardamos el dato de la cantidad máxima
					$cantidadMaxima.val(data[0].cantidadMaximaPermitida);
				}
			},
			error : function (xhr, status, error){
				handleError(xhr, status, error, locale);
			}			
		});
	}

	// validar
	if(parseInt($stockDevuelto.val()) > parseInt($cantidadMaxima.val())){
		//Mostramos error de cantidad
		createAlert(cantidadMaximaNoSuperableP87 + $cantidadMaxima.val(),"ERROR");
		esValido = false;
	}
	return esValido;
}

function validacionStockDevueltoP87(id, mostrarAlerta){
	//La validación antes de ningún tratamiento es satisfactoria, después puede no serlo
	resultadoValidacionP87 = 'S';

	//Este if se encarga de que se calcule la validación si venimos de un campo stockDevuelto. Se ha introducido para que se puedan realizar validaciones en ordenaciones de columnas
	//del método cargarOrdenacionesColumnasP87P84(). Hasta ahora, siempre que llegaba a esta función era por estar tratando el campo stockDevuelto. En las reordenaciones de columnas, puede
	//que se clique la columna viniendo de un campo editable de stockDevuelto o de cualquier otra parte de la pantalla. Por eso se ha introducido este control.
	if(id.indexOf("_stockDevuelto") != -1){
		//Se obtiene el valor introducido por el usuario
		var campoActual = $("#"+id).val();

		//Se sustitullen las comas por los puntos
		var campoActualPunto = $("#"+id).val().replace(',','.');

		//Se obtiene la fila seleccionada
		var fila = id.substring(0, id.indexOf("_"));

		//Como en principio no ha ocurrido ningún error, el error es 0
		var error = 0;
		var descError = '';

		//Se obtienen las id de los input que contienen el campo original devuelto por el controlador, el actual y el temporal
		var campoStockDevueltoActual = fila + "_stockDevuelto";
		var campoStockDevueltoOrig = fila + "_stockDevuelto_orig";
		var campoStockDevueltoTmp = fila + "_stockDevuelto_tmp";

		var campoErrorOrig = fila + "_stockDevueltoBulto_codError_orig";

		//Div que equivale a la columna Estado de la fila seleccionada.
		//Estos div contienen las imágenes del disquete, ficha y de aviso 
		var campoDivModificado = fila + "_stockDevueltoBulto_divModificado";
		var campoDivGuardado = fila + "_stockDevueltoBulto_divGuardado";
		var campoDivError = fila + "_stockDevueltoBulto_divError";

		//Contiene el id de la imagen del div campoDivError. De esta forma, al posicional el ratón encima
		//sale la causa del error
		var campoImgError = fila + "_stockDevueltoBulto_imgError";

		//var isVisibleError = $("#"+campoDivError).is(':visible');
		//var isVisibleModificado = $("#"+campoDivModificado).is(':visible');

		//En este caso 
		if (mostrarAlerta == 'S'){

			if(!validarCantidadMaximaRowP87("#"+id, '#' + fila)){
				error = 1;
			}

			if(campoActual != ""){
				//Si hay más de una coma o el campo introducido es vacío mostrar el error
				if (campoActual.split(",").length > 2){
					descError = replaceSpecialCharacters(stockDevueltoIncorrecto);
					createAlert(descError, "ERROR");

					//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
					$("#p87_fld_StockDevuelto_Selecc").val(fila + "_stockDevuelto");

					//Pintamos de rojo el campo.
					$("#"+id).removeClass("editable").addClass("editableError");

					//El error se cambia a 1 para realizar su tratamiento
					error = 1;
				}else{
					//Miramos si el número tiene más de 15 enteros o 3 decimales. Esos valores son constantes que pueden cambiarse
					var numerosEnteros = campoActual.split(",")[0];
					var numerosDecimales = campoActual.split(",")[1];

					if(campoActual.indexOf(',') > -1){
						if(numerosEnteros.length > maxNumEnteros || numerosDecimales.length > maxNumDecimales){
							descErrorP84 = replaceSpecialCharacters(stockDevueltoEnteroErroneo +" "+ maxNumEnteros + " " + stockDevueltoDecimalErroneo +" "+ maxNumDecimales +" "+ stockDevueltoDecimalErroneo2);
							createAlert(descErrorP84, "ERROR");

							//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
							$("#p84_fld_StockDevuelto_Selecc").val(fila + "_stockDevuelto");

							//Pintamos de rojo el campo.
							$("#"+id).removeClass("editable").addClass("editableError");

							//El error se cambia a 1 para realizar su tratamiento
							error = 1;
						}
					}else{
						if(numerosEnteros.length > maxNumEnteros){
							descErrorP84 = replaceSpecialCharacters(stockDevueltoEnteroErroneo +" "+ maxNumEnteros + " " + stockDevueltoDecimalErroneo +" "+ maxNumDecimales +" "+ stockDevueltoDecimalErroneo2);
							createAlert(descErrorP84, "ERROR");

							//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
							$("#p84_fld_StockDevuelto_Selecc").val(fila + "_stockDevuelto");

							//Pintamos de rojo el campo.
							$("#"+id).removeClass("editable").addClass("editableError");

							//El error se cambia a 1 para realizar su tratamiento
							error = 1;
						}
					}
				}	

				// validacion del maximo y la suma
				if(!error && !validateMaximoCambioStockP87(id, gridP87.nameJQuery)) {
					createAlert(sumaSuperaMaximoP87, "ERROR");
					error = 1;
				}
				
				//Miramos que el dato original no sea ""
				var campoStockDevueltoOrigPunto = "";
				if($("#"+campoStockDevueltoOrig).val() != "" && $("#"+campoStockDevueltoOrig).val() != "null" && $("#"+campoStockDevueltoOrig).val() != null){
					campoStockDevueltoOrigPunto = $("#"+campoStockDevueltoOrig).val().replace(',','.');
				}

				if (error == 1){
					//En este caso ha ocurrido un error y hay que mostrar el icono de error.
					$("#"+campoDivGuardado).hide();
					$("#"+campoDivModificado).hide();
					$("#"+campoDivError).show();

					//Cambiamos el title
					$("#"+campoImgError).attr('title', descError);

					//Añadimos la fila al array.
					addSeleccionadosP87(fila);

					//Eliminamos el elemento de los array de modificadosP87, al haber un error y recuperar sus datos por defecto.
					var valor = $("#gridP87DevolCreadaCentro #"+fila+" [aria-describedby='gridP87DevolCreadaCentro_rn']").text();
					if(modificadosP87.indexOf(valor+"_stockDevuelto") != -1){ 
						var index = modificadosP87.indexOf(valor+"_stockDevuelto");
						modificadosP87.splice(index,1);
					}

					if(modificadosP87.indexOf(valor+"_bulto") != -1){ 
						var index = modificadosP87.indexOf(valor+"_bulto");
						modificadosP87.splice(index,1);
					}

					//Si el campo original es distinto a "" devolvemos el número que nos llega del controlador.
					//Si no devolvemos "".
					if(campoStockDevueltoOrigPunto != ""){
						//Cargamos los valores anteriores a la modificación.
						$("#"+id).val(campoStockDevueltoOrigPunto).formatNumber({format:"0.###",locale:"es"});
						$("#"+campoStockDevueltoTmp).val(campoStockDevueltoOrigPunto).formatNumber({format:"0.###",locale:"es"});
					}else{
						$("#"+id).val("");
						$("#"+campoStockDevueltoTmp).val("");
					}
					//Inicializamos el bulto también
					var valorBultoOrig = $("#"+fila+"_bulto_orig").val();
					$("#"+fila+"_bulto_tmp").val(valorBultoOrig);
					$("#"+fila+"_bulto").val(valorBultoOrig);
					
					// recalculamos y asignamos el valor de la suma ahora que hemos modificado el valor del stock
					controlChangecolumnaStockDevueltoP87(id, gridP87.nameJQuery);
					

					//El código de error de la fila será 0 que equivale a un error
					$("#"+campoErrorOrig).val("0");

					//Sirve para el onPaging del grid.
					resultadoValidacionP87 = 'N';
				}else{
					//No hay error con lo que quitamos el posible icono de error.
					$("#"+campoDivError).hide();
					$("#"+id).removeClass("editableError").addClass("editable");

					var campoActualFormatter = $.formatNumber(campoActual.replace(',','.'),{format:"0.###",locale:"es"});
					$("#"+campoStockDevueltoActual).val(campoActualFormatter);
					if (($("#"+campoStockDevueltoTmp).val() != null) && $("#"+campoStockDevueltoTmp).val() != campoActualFormatter){
						//if (parseFloat($("#"+campobultoOrig).val()) != parseFloat(campoActualPunto))
						//{
						//En este caso se ha modificado el campo y hay que establecer el icono de modificaci?n.
						$("#"+campoDivError).hide();
						$("#"+campoDivGuardado).hide();
						$("#"+campoDivModificado).show();

						//Es necesario que el campo editable coja formato decimal, si no se hace este paso,
						//al clicar guardar el número del campo editable pasa sin decimales (en el caso de haberlo
						//introducido sin decimales), y se guarda en el hidden temporal sin decimales, al editarlo
						//por el mismo campo pero con decimales, detecta que son dos números distintos y vuelve a entrar
						//sin necesidad (aunque la ejecución salga bien). Además al clicar guardar, queda mejor si el 
						//número coge formato decimal automáticamente pues es el formato que va a devolver después del guardado.

						//Si no hicieramos esto, por ejemplo el usuario introduciría un 3, el campo temporal cogería el valor 3.
						//Más tarde el usuario introduciría el campo 3,000 y detectaría que es distinto, ejecutando de nuevo el código
						//sin ser necesario. Además hasta recibir la respuesta del guardado, el campo no cogería formato decimal 
						//y queda extaño viendo que en las tabulaciones o al presionar enter si lo coge.
						$("#"+campoStockDevueltoTmp).val(campoActualFormatter);

						//El código de error de la fila será 9 que equivale a modificado
						$("#"+campoErrorOrig).val("9");

						//Añadimos la fila al array.
						addSeleccionadosP87(fila);

						//Insertamos el elemento de los array de modificadosP87
						var valor = $("#gridP87DevolCreadaCentro #"+fila+" [aria-describedby='gridP87DevolCreadaCentro_rn']").text();
						if(modificadosP87.indexOf(valor+"_stockDevuelto") === -1){ 							
							modificadosP87.push(valor+"_stockDevuelto");
						}
					}
				}
			}else{
				//Entra aquí en tres situaciones:
				// 1) El usuario ha pulsado las teclas de las flechas para moverse de campo sin haber introducido ningún valor previamente siendo este vacío
				// 2) Previamente, el usuario ha introducido un campo erroneo ha pulsado las flechas para moverse de campo, ha devuelto error y ha dejado el 
				//valor vacío por defecto devuelto por el controlador. Cuando el usuario vuelve a pulsar una flecha para moverse de campo, entra en esta situación.
				// 3) Existe un dato previo distinto a vacío y lo pones vacío

				//Se elimina el color rojo de error del 2 caso. En el 1 caso esta línea no afecta.
				$("#"+id).removeClass("editableError").addClass("editable");

				//Se oculta el mensaje de error del 2 caso. En el 1 caso esta línea no afecta, mi en el caso 3.
				$("#"+campoDivError).hide();

				//Si el campo previo es un número y ahora se ha cambiado a vacío, guardamos en la lista temporal
				//el campo para actualizar la tabla temporal, esto ocurre en el caso 3.
				if($("#"+campoStockDevueltoTmp).val() != campoActual){
					$("#"+campoDivError).hide();
					$("#"+campoDivGuardado).hide();
					$("#"+campoDivModificado).show();

					$("#"+campoStockDevueltoTmp).val(campoActual);

					addSeleccionadosP87(fila);
					//Insertamos el elemento de los array de modificadosP87
					var valor = $("#gridP87DevolCreadaCentro #"+fila+" [aria-describedby='gridP87DevolCreadaCentro_rn']").text();
					if(modificadosP87.indexOf(valor+"_stockDevuelto") === -1){ 							
						modificadosP87.push(valor+"_stockDevuelto");
					}

					$("#"+campoErrorOrig).val("9");
				}
			}

			//Colorear de negro la fila si stockActual es 0,000  y actualizamos el campo. Si vuelves a poner el campo original 
			//lo pondrá en rojo.
			p87ColorearStockActual(fila);
		}else{
			// Cuando no hay que sacar mensajes
			//Control de campo decimal erroneo
			if (campoActual.split(",").length > 2){
				resultadoValidacionP87 = 'N';
			}else if(campoActual != ""){
				var numerosEnteros = campoActual.split(",")[0];
				var numerosDecimales = campoActual.split(",")[1];

				if(numerosDecimales && numerosDecimales.length > maxNumDecimales ||
						numerosEnteros && numerosEnteros.length > maxNumEnteros) {
					resultadoValidacionP87 = 'N';
				}else if(!validateMaximoCambioStockP87(id, gridP87.nameJQuery)) {
					resultadoValidacionP87 = 'N';
				}else{
					var campoActualPunto = $("#"+id).val().replace(',','.');
					$("#"+id).val(campoActualPunto).formatNumber({format:"0.###",locale:"es"});
				}
			}
		}
	}
	return resultadoValidacionP87;
}

function validacionBultoP87(id, mostrarAlerta){
	var resultadoValidacionP87 = 'S';

	if(mostrarAlerta == "S"){

		//Se obtiene el valor introducido por el usuario
		var campoActual = $("#"+id).val();

		//Se sustitullen las comas por los puntos
		var campoActualPunto = $("#"+id).val().replace(',','.');

		//Se obtiene la fila seleccionada
		var fila = id.substring(0, id.indexOf("_"));

		//Como en principio no ha ocurrido ningún error, el error es 0
		var error = 0;
		var descError = '';

		//Se obtienen las id de los input que contienen el campo original devuelto por el controlador, el actual y el temporal
		var campoBultoActual = fila + "_bulto";
		var campoBultoOrig = fila + "_bulto_orig";
		var campoBultoTmp = fila + "_bulto_tmp";

		//Campo que contiene el código de error equivalente a la imágen del disquete, error, etc.
		var campoErrorOrig = fila + "_stockDevueltoBulto_codError_orig";

		//Div que equivale a la columna Estado de la fila seleccionada.
		//Estos div contienen las imágenes del disquete, ficha y de aviso 
		var campoDivModificado = fila + "_stockDevueltoBulto_divModificado";
		var campoDivGuardado = fila + "_stockDevueltoBulto_divGuardado";

		//En este caso la validación devuelve siempre si, pero hay que mirar que haya modificación
		if(($("#"+campoBultoTmp).val() != null) && $("#"+campoBultoTmp).val() != campoActual){

			//En este caso se ha modificado el campo y hay que establecer el icono de modificacion
			$("#"+campoDivGuardado).hide();
			$("#"+campoDivModificado).show();
			$("#"+campoBultoTmp).val(campoActual);

			//El código de error de la fila será 9 que equivale a modificado
			$("#"+campoErrorOrig).val("9");

			//Añadimos la fila al array.
			addSeleccionadosP87(fila);

			//Insertamos el elemento de los array de modificadosP87
			var valor = $("#gridP87DevolCreadaCentro #"+fila+" [aria-describedby='gridP87DevolCreadaCentro_rn']").text();
			if(modificadosP87.indexOf(valor+"_bulto") === -1){ 
				modificadosP87.push(valor+"_bulto");
			}

		}
	}

	return resultadoValidacionP87;
}

//Esta lista solo contiene las filas modificadas de la página actual, de esta forma, guarda los registros modificadosP87 
//en la temporal según se va reordenando el grid, paginando, etc.
function addSeleccionadosP87(fila){
	seleccionadosP87[fila] = fila;
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/*************************************************************FUNCIONES DE P87 ***************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

function cargarLineasDeDevolucionCreadaCentro(devolucion,tieneLapizLaFicha){
	//Si se ha clicado una ficha con lapiz será un grid editable, si no no.
	if(!tieneLapizLaFicha || userPerfilMisumi == CONS_CONSULTA_ROLE){
		$(gridP87.nameJQuery).jqGrid('setColProp', 'stockDevuelto', {editable:false});
		$(gridP87.nameJQuery).jqGrid('setColProp', 'bulto', {editable:false});

		$(gridP87.nameJQuery).jqGrid('hideCol','mensaje');

		$("#p87_btn_guardado").hide();
	}else{
		$(gridP87.nameJQuery).jqGrid('setColProp', 'stockDevuelto', {editable:true});
		$(gridP87.nameJQuery).jqGrid('setColProp', 'bulto', {editable:true});

		$(gridP87.nameJQuery).jqGrid('showCol','mensaje');

		$("#p87_btn_guardado").show();
	}


	//Se muestra la columna stockActual o no
	if(devolucion.estadoCab > 1){
		$(gridP87.nameJQuery).jqGrid('hideCol','stockActual');
		$(gridP87.nameJQuery).jqGrid('hideCol','stockTienda');
		$(gridP87.nameJQuery).jqGrid('hideCol','stockDevolver');

		if(devolucion.estadoCab == 3 || devolucion.estadoCab == 4){ // Abono o Incidencia
			$(gridP87.nameJQuery).jqGrid('showCol','cantAbonada');
			$(gridP87.nameJQuery).jqGrid('showCol','descAbonoError');
		} else {
			$(gridP87.nameJQuery).jqGrid('hideCol','cantAbonada');
			$(gridP87.nameJQuery).jqGrid('hideCol','descAbonoError');
		}
		
		//Ponemos nombre a la columna stockDevuelto
		$(gridP87.nameJQuery).jqGrid('setLabel', "stockDevuelto",stockDevueltoTitleNoPrepararMercancia);
		
		//Ponemos nombre a la columna stockDevuelto
		$(gridP87.nameJQuery).jqGrid('setLabel', "stockDevuelto",stockDevueltoTitleNoPrepararMercancia);
		$(gridP87.nameJQuery).jqGrid('hideCol','formatoDevuelto');
		
		//Mostramos las columnas de formato devuelto y tipo referencia
		$(gridP87.nameJQuery).jqGrid('showCol','formatoDevuelto');
		$(gridP87.nameJQuery).jqGrid('showCol','tipoReferencia');
		
		//Insertamos el titulo
		setTitleOnColumnHeaderP87("stockDevuelto",stockDevueltoNoPrepararMercanciaTitle);
	}else{//Preparar mercancia

		$(gridP87.nameJQuery).jqGrid('hideCol','formatoDevuelto');
		$(gridP87.nameJQuery).jqGrid('hideCol','stockTienda');
		$(gridP87.nameJQuery).jqGrid('hideCol','stockDevolver');
		$(gridP87.nameJQuery).jqGrid('hideCol','cantAbonada');
		$(gridP87.nameJQuery).jqGrid('hideCol','tipoReferencia');
		$(gridP87.nameJQuery).jqGrid('hideCol','descAbonoError');
		
		//Ponemos nombre a la columna stockDevuelto
		$(gridP87.nameJQuery).jqGrid('setLabel', "stockDevuelto",stockDevueltoTitlePrepararMercancia);

		//Insertamos el titulo
		setTitleOnColumnHeaderP87("stockDevuelto",stockDevueltoPrepararMercanciaTitle);
	}
	
	
	//BAZAR. Si el valor de coste maximo es distinto de null, es BAZAR. Mostrar columna Coste Unitario
	if (devolucion.costeMaximo != null) {
		$(gridP87.nameJQuery).jqGrid('showCol','costeUnitario');
		$("#p87_AreaCosteMaximo").attr("style", "display:inline");
		$("#p87_AreaSuma").attr("style", "display:inline");

	} else {
		$(gridP87.nameJQuery).jqGrid('hideCol','costeUnitario');
		$("#p87_AreaCosteMaximo").attr("style", "display:none");
		$("#p87_AreaSuma").attr("style", "display:none");
	}

	//Se muestran más columnas o menos.
	/*if(abonadoOIncidencia){
		//Se enseñan las columnas
		$(gridP87.nameJQuery).jqGrid('showCol','cantAbonada');
		$(gridP87.nameJQuery).jqGrid('showCol','descAbonoError');
	}else{
		//Se ocultan las columnas si no son de tipo abonado o incidencia
		$(gridP87.nameJQuery).jqGrid('hideCol','cantAbonada');
		$(gridP87.nameJQuery).jqGrid('hideCol','descAbonoError');
	}*/

	// por defecto
	configurarColumnasSegunResultadosP87();
	
	devolucion.flagHistorico = flgHistorico;
	
	$("#gbox_gridP87DevolCreadaCentro #gs_codArticulo").val("");

	//Poner ancho de grid, para que aunque haya más o menos columnas siempre ocupe lo mismo
	$(gridP87.nameJQuery).jqGrid('setGridWidth',950,true);

	var objJson = $.toJSON(devolucion);
	$.ajax({
		type : 'POST',
		url : './devoluciones/popup/creadaCentro/loadDataGrid.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		async: false,
		cache : false,
		success : function(data) {
			if(data != null){
				if(data.rows != null && data.rows.length > 0){
					//Una vez sabemos que hay líneas de devolución, cargamos sus proveedores, sin repetir y ordenados
					//alfabéticamente. Para ello tiramos de la tabla temporal, pues hacer esa ordenación por javascript
					//sin repetir es complicado
					var comboBienCargado = cargarComboboxProveedorP87(objJson);

					if(comboBienCargado){

						//Insertamos en la cabecera la fecha
						$("#p87_lbl_ValorFecha").html((devolucion.fechaDesdeStr != null ? devolucion.fechaDesdeStr:"") + " / </BR>" + (devolucion.fechaHastaStr != null ? devolucion.fechaHastaStr:""));

						//Insertamos el título
						$("#p87_lbl_ValorTitulo").text(devolucion.titulo1 != null ? devolucion.titulo1:"");

						//Observaciones
						$("#p87_lbl_ValorObservaciones").text(devolucion.descripcion != null ? devolucion.descripcion:"");

						//Abono y recogida
						$("#p87_lbl_ValorAbono").text(devolucion.abono != null ? devolucion.abono:"");
						$("#p87_lbl_ValorRecogida").text(devolucion.recogida != null ? devolucion.recogida:"");

						//Localizador
						$("#p87_lbl_ValorLocalizador").text(devolucion.localizador != null ? devolucion.localizador:"");

						//Título
						$("#p87_popUpDevolCreadaCentro").dialog({ title:creadaCentro+ " - " + (devolucion.titulo1 != null ? devolucion.titulo1.toUpperCase():"")});
						
						//Coste MAXIMO
						$("#p87_lbl_ValorCosteMaximo").text(devolucion.costeMaximo != null ? devolucion.costeMaximo:"");
						$("#p87_lbl_ValorCosteMaximoHidden").val(devolucion.costeMaximo != null ? devolucion.costeMaximo:"");
						
						//RMA
						controlCampoRMA87(devolucion);
						
						
						//SUMA
						getSumaCosteFinalP87(devolucion);

						configurarGridP87(data);
						generateTooltipP87(data);
						//rellenadoDeFilasP87();
						$("#p87_popUpDevolCreadaCentro").dialog('open');
					}else{
						createAlert(replaceSpecialCharacters(notPosibleToReturnDataP87), "ERROR");
					}
				}else{
					createAlert(replaceSpecialCharacters(notPosibleToReturnDataP87), "ERROR");
				}
			}else{
				createAlert(replaceSpecialCharacters(notPosibleToReturnDataP87), "ERROR");
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});		
	
	
}


function controlCampoRMA87(devolucion){	
	
	if (devolucion.tipoRMA != null &&  devolucion.tipoRMA != "") {

		$("#p87_input_rma").val(devolucion.codRMA != null ? devolucion.codRMA:"");
		$("#p87_input_rmaHidden").val(devolucion.codRMA != null ? devolucion.codRMA:"");
		
		$("#p87_AreaRma").attr("style", "display:inline");
		
		if (devolucion.tipoRMA == 'T') {
				$("#p87_input_rma").removeAttr("disabled");
			} else {
				if (devolucion.tipoRMA == 'C') {
					$("#p87_input_rma").attr("disabled", "disabled");
				}
			}
		
	} else {
		$("#p87_AreaRma").attr("style", "display:none");
	}

	
}


function getSumaCosteFinalP87(devolucion){	

	var objJson = $.toJSON(devolucion);
	$.ajax({
		type : 'POST',
		url : './devoluciones/popup/creadaCentro/getSumaCosteFinal.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {		
			
			if (data != null) {
				//Coste MAXIMO
				$("#p87_lbl_ValorSuma").filter_input({regex:'[0-9]'});
				$("#p87_lbl_ValorSuma").val($.formatNumber(data,{format:"0.#",locale:"es"}));
				$("#p87_lbl_ValorSumaSinFormateo").val(data);
			} else {
				$("#p87_lbl_ValorSuma").filter_input({regex:'[0-9]'});
				$("#p87_lbl_ValorSuma").val(0);
				$("#p87_lbl_ValorSumaSinFormateo").val(0);
			}
			
			
			if (devolucion.suma > devolucion.maximo) { // si la suma supera a los euros maximos, ambos campos hay que pintarlos en rojo
				
				$("#p87_lbl_TextoSuma").addClass("p87TextoRojo");
				$("#p87_lbl_ValorSuma").removeAttr( "style" );
				$("#p87_lbl_ValorSuma").attr("style", "color:red; font-size:12px;");
				$("#p87_lbl_TextoCosteMaximo").addClass("p87TextoRojo");
				$("#p87_lbl_ValorCosteMaximo").addClass("p87TextoRojo");
				
			} else {
				$("#p87_lbl_TextoSuma").removeClass("p87TextoRojo");
				$("#p87_lbl_ValorSuma").removeAttr( "style" );
				$("#p87_lbl_ValorSuma").attr("style", "font-size:12px;");
				$("#p87_lbl_TextoCosteMaximo").removeClass("p87TextoRojo");
				$("#p87_lbl_ValorCosteMaximo").removeClass("p87TextoRojo");
			}
			
			
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		 
}

//Función que sirve para cargar el combobox con nombre PROVEEDOR. Si se carga correctamente, cargamos el grid y abrimos el dialogo con el grid, si no, no.
//Se pone async false, para que no chequee en cargarLineasDeDevolucionCreadaCentroP87 si se ha cargado el combo antes de que se pueda llegar a cargar.
function cargarComboboxProveedorP87(objJson){
	var comboBienCargado = false;
	var options = "";
	$.ajax({
		type : 'POST',
		url : './devoluciones/popup/creadaCentro/loadComboProveedor.do',
		data : objJson,
		async: false,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {
			if(data != null){
				options = "<option value=' '>TODOS</option>";
				for (i = 0; i < data.length; i++){
					//Se formatea la opción fija de REFERENCIAS PERMANENTES para que aparezca en negrita
					if (i==0){
						options = options + "<option id='p87RefPermanentes' class='p87FormatoRefPermanentes' value='" + data[i].codigo + "'><span class='p87FormatoRefPermanentes'>" + data[i].descripcion + "</span></option>";
					}else{
						options = options + "<option value='" + data[i].codigo + "'>" + data[i].descripcion + "</option>";
					}
					ultimoBultoIntroducido[data[i].codigo] = "";//Se inicializan todos los bultos de los proveedores a ""
				}
				comboValue = " ";
				$("select#p87_cmb_proveedor").html(options);

				$("#p87_cmb_proveedor").combobox('autocomplete',"TODOS");
				$("#p87_cmb_proveedor").combobox('comboautocomplete',"null");
				$("#p87RefPermanentes").html("<span class='p87FormatoRefPermanentes'>" + $("#p87RefPermanentes").text() +"</span>");  
				comboBienCargado = true;
			}else{
				comboBienCargado =  false;
			}			
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
			comboBienCargado =  false;
		}	
	});	

	$("#p87_cmb_proveedor").combobox({
		selected: function(event, ui) {
			if ( ui.item.value!="" && ui.item.value!="null" ) {
				//Si el valor seleccionado difiere del valor seleccionado anterior
				//buscamos, si es el mismo no.
				if(ui.item.value != comboValue){							
					comboValue = $("#p87_cmb_proveedor").val();
					if (comboValue !=null){
						$('#gridP87DevolCreadaCentro').setGridParam({page:1});
						reloadData_gridP87DevolCreadaCentro();	
					}
				}
			}
		}  ,
	}); 
	return comboBienCargado;
}

function reset_p87(){
	//Iniciar los arrays
	seleccionadosP87 = new Array();
	listadoModificadosP87 = new Array();
	modificadosP87 = new Array();

	//Iniciar el grid y su cantidad de elementos a mostrar
	$(gridP87.nameJQuery).jqGrid('clearGridData');
	$(gridP87.nameJQuery).jqGrid('setGridParam', { rowNum: 10 });
	$("#p87_popUpDevolCreadaCentro .ui-pg-selbox").val(10);

	//Quitamos las flechas de ordenación del grid de la columna reordenada para que no se mantengan al abrid un nuevo grid.
	$(gridP87.nameJQuery + "_" + gridP87.getSortIndex() + " [sort='asc']").addClass("ui-state-disabled");
	$(gridP87.nameJQuery + "_" + gridP87.getSortIndex() + " [sort='desc']").addClass("ui-state-disabled");

	//Reseteamos la ordenación del grid
	$(gridP87.nameJQuery).jqGrid('setGridParam', {sortname:'', sortorder: 'asc'});
	
	//Reseteamos el valor del último bulto introducido
	ultimoBultoIntroducido = {};
}

//Mediante esta función se obtienen los cambios de las filas cambiadas en una lista. Luego esta lista se utiliza en el controlador
//para que al paginar que se guarden los estados de modificadosP87, guardados, etc.
function obtenerListadoModificadosP87(){

	//A partir del array de seleccionadosP87 obtenemos el listado de campos modificadosP87 a enviar al controlador.
	var registroListadoModificado = {};
	var valorStockDevuelto = "";
	var valorBulto = "";
	var valorBandejas = "";
	var valorCodError = "";
	var valorCodArticulo= "";
	for (i = 0; i < seleccionadosP87.length; i++){
		if (seleccionadosP87[i] != null && seleccionadosP87[i] != '')
		{
			//Por cada elemento seleccionado, tenemos que crear el listado de modificadosP87.
			registroListadoModificado = {};

			//Se obtiene el valor de stock devuelto y de bulto y de kgs.
			valorStockDevuelto = $("#"+ seleccionadosP87[i] + "_stockDevuelto").val() ? $("#"+ seleccionadosP87[i] + "_stockDevuelto").val().replace(',','.') : "";
			valorBulto = $("#"+ seleccionadosP87[i] + "_bulto").val() ? $("#"+ seleccionadosP87[i] + "_bulto").val().replace(',','.') : "";
			valorBandejas = $("#"+ seleccionadosP87[i] + "_stockDevueltoBandejas_tmp").val() ? $("#"+ seleccionadosP87[i] + "_stockDevueltoBandejas_tmp").val().replace(',','.') : "";
			
			//Se obtiene el codigo error para guardar el estado modificado, error o guardado en la tabla temporal
			//indice = $("#"+ seleccionadosP87[i] + "_stockDevueltoBulto_indice").val();
			valorCodError = $("#"+ seleccionadosP87[i] + "_stockDevueltoBulto_codError_orig").val() ? $("#"+ seleccionadosP87[i] + "_stockDevueltoBulto_codError_orig").val() : "";

			//Se obtiene el codigo de articulo para saber qué linea actualizar en la tabla temporal.
			valorCodArticulo = jQuery(gridP87.nameJQuery).jqGrid('getCell',seleccionadosP87[i],'codArticulo');

			//Se rellena el objeto con el error, stock y bulto. Además se inserta el código de artículo para saber que linea de devolución
			//hay que actualizar.

			//registroListadoModificado.indice =  indice;
			registroListadoModificado.codError = valorCodError;

			registroListadoModificado.stockDevuelto =  valorStockDevuelto;
			registroListadoModificado.bulto = valorBulto;

			registroListadoModificado.codArticulo = valorCodArticulo;
			registroListadoModificado.stockDevueltoBandejas = valorBandejas;
			
			listadoModificadosP87.push(registroListadoModificado)
		}	
	}

	//Reseteamos el array de los seleccionadosP87.
	seleccionadosP87 = new Array();
}

//Función que sirve para paginar o reordenar los elementos del grid.
function reloadData_gridP87DevolCreadaCentro() {

	//Obtenemos las filas modificadas
	obtenerListadoModificadosP87();

	var devolucionEditada = new Devolucion(devolucionFicha.centro,flgHistorico,devolucionFicha.titulo1,devolucionFicha.flgCodArticulo,
			devolucionFicha.estadoCab,devolucionFicha.localizador,devolucionFicha.devolucion,devolucionFicha.fechaDesde,devolucionFicha.fechaHasta,
			devolucionFicha.flgRecogida,devolucionFicha.abono,devolucionFicha.recogida,devolucionFicha.codPlataforma,devolucionFicha.descripcion,
			devolucionFicha.motivo,devolucionFicha.titulo2,devolucionFicha.fechaPrecio,devolucionFicha.codError,devolucionFicha.descError,
			devolucionFicha.devLineas,listadoModificadosP87,comboValue,null);

	//Borrar el listado de modificadosP87
	listadoModificadosP87 = new Array();

	var objJson = $.toJSON(devolucionEditada);

	$.ajax({
		type : 'POST',			
		url : './devoluciones/popup/creadaCentro/loadDataGrid.do?page='+gridP87.getActualPage()+'&max='+gridP87.getRowNumPerPage()+'&index='+gridP87.getSortIndex()+'&sortorder='+gridP87.getSortOrder()+'&recarga=S',
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		data : objJson,
		success : function(data) {	
			if(data != null){
				if(data.rows != null && data.rows.length > 0){
					configurarGridP87(data);
					$("#gbox_gridP87DevolCreadaCentro #gs_codArticulo").focus();
					//$("#p87_cmb_proveedor").next().focus();
					generateTooltipP87(data);
					//rellenadoDeFilasP87();
				}else{
					/*createAlert(replaceSpecialCharacters(notPosibleToReturnDataP87), "ERROR");*/
					
					//Rellenamos el grid vacío
					configurarGridP87(data);
					
					//Rellenamos las líneas vacías
					//rellenadoDeFilasNoEncontradasP87();

					//Se pone esta fila porque si paginas y da error de paginación, el grid cambia el número de página internamente pero no externamente (la que se visualiza), y si luego
					//pagina bien, no va a la siguiente página, si no a la que tiene internamente. 
					var valorCajaGrid = $("#p87_popUpDevolCreadaCentro .ui-pg-input").val();
					$(gridP87.nameJQuery).setGridParam({page:valorCajaGrid});
				}
			}else{
				createAlert(replaceSpecialCharacters(notPosibleToReturnDataP87), "ERROR");

				//Se pone esta fila porque si paginas y da error de paginación, el grid cambia el número de página internamente pero no externamente (la que se visualiza), y si luego
				//pagina bien, no va a la siguiente página, si no a la que tiene internamente. 
				var valorCajaGrid = $("#p87_popUpDevolCreadaCentro .ui-pg-input").val();
				$(gridP87.nameJQuery).setGridParam({page:valorCajaGrid});
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});		
}



//Función que sirve para paginar o reordenar los elementos del grid.
function reloadData_FiltroP87(filtroReferencia) {

	var devolucionEditada = new Devolucion(devolucionFicha.centro,flgHistorico,devolucionFicha.titulo1,devolucionFicha.flgCodArticulo,
			devolucionFicha.estadoCab,devolucionFicha.localizador,devolucionFicha.devolucion,devolucionFicha.fechaDesde,devolucionFicha.fechaHasta,
			devolucionFicha.flgRecogida,devolucionFicha.abono,devolucionFicha.recogida,devolucionFicha.codPlataforma,devolucionFicha.descripcion,
			devolucionFicha.motivo,devolucionFicha.titulo2,devolucionFicha.fechaPrecio,devolucionFicha.codError,devolucionFicha.descError,
			devolucionFicha.devLineas,listadoModificadosP87,comboValue,null);

	//Borrar el listado de modificadosP87
	listadoModificadosP87 = new Array();

	var objJson = $.toJSON(devolucionEditada);

	$.ajax({
		type : 'POST',			
		url : './devoluciones/popup/creadaCentro/loadDataGrid.do?page='+gridP87.getActualPage()+'&max='+gridP87.getRowNumPerPage()+'&index='+gridP87.getSortIndex()+'&sortorder='+gridP87.getSortOrder()+'&recarga=S&filtroReferencia='+filtroReferencia,
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		data : objJson,
		success : function(data) {	
			if(data != null){
				if(data.rows != null && data.rows.length > 0){
					configurarGridP87(data);
					$("#p87_cmb_proveedor").next().focus();
					generateTooltipP87(data);
					//rellenadoDeFilasP87();
				}else{
					/*createAlert(replaceSpecialCharacters(notPosibleToReturnDataP87), "ERROR");*/
					
					//Rellenamos el grid vacío
					configurarGridP87(data);
					
					//Rellenamos las líneas vacías
					//rellenadoDeFilasNoEncontradasP87();

					//Se pone esta fila porque si paginas y da error de paginación, el grid cambia el número de página internamente pero no externamente (la que se visualiza), y si luego
					//pagina bien, no va a la siguiente página, si no a la que tiene internamente. 
					var valorCajaGrid = $("#p87_popUpDevolCreadaCentro .ui-pg-input").val();
					$(gridP87.nameJQuery).setGridParam({page:valorCajaGrid});
				}
			}else{
				createAlert(replaceSpecialCharacters(notPosibleToReturnDataP87), "ERROR");

				//Se pone esta fila porque si paginas y da error de paginación, el grid cambia el número de página internamente pero no externamente (la que se visualiza), y si luego
				//pagina bien, no va a la siguiente página, si no a la que tiene internamente. 
				var valorCajaGrid = $("#p87_popUpDevolCreadaCentro .ui-pg-input").val();
				$(gridP87.nameJQuery).setGridParam({page:valorCajaGrid});
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});		
}


//Función que actualiza las líneas de devolución de una devolución.
function actualizarDevolucionLineasP87(){
	
	//Obtener el campo RMA
	var codRMA = $("#p87_input_rma").val();
	var codRMAInicial = $("#p87_input_rmaHidden").val();
	
	
	codRMAModificado87 = false;
	if (codRMA != codRMAInicial) {
		codRMAModificado87 = true;
	}
	
	
	//Si se ha modificado algún registro de cualquier página, se guarda. Si no se ha modificado ningún registro muestra error.
	if ((modificadosP87.length > 0) || codRMAModificado87 ){
	
		//Obtenemos las filas modificadas
		obtenerListadoModificadosP87();

		var devolucionEditada = new Devolucion(devolucionFicha.centro,devolucionFicha.flagHistorico,devolucionFicha.titulo1,devolucionFicha.flgCodArticulo,
				devolucionFicha.estadoCab,devolucionFicha.localizador,devolucionFicha.devolucion,devolucionFicha.fechaDesde,devolucionFicha.fechaHasta,
				devolucionFicha.flgRecogida,devolucionFicha.abono,devolucionFicha.recogida,devolucionFicha.codPlataforma,devolucionFicha.descripcion,
				devolucionFicha.motivo,devolucionFicha.titulo2,devolucionFicha.fechaPrecio,devolucionFicha.codError,devolucionFicha.descError,
				devolucionFicha.devLineas,listadoModificadosP87,comboValue,null);

		var objJson = $.toJSON(devolucionEditada);

		$.ajax({
			type : 'POST',			
			url : './devoluciones/popup/creadaCentro/saveDataGrid.do?page='+gridP87.getActualPage()+'&max='+gridP87.getRowNumPerPage()+'&index='+gridP87.getSortIndex()+'&sortorder='+gridP87.getSortOrder() +'&codRMA='+codRMA,
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			data : objJson,
			success : function(data) {	
				if(data != null){
					if(data.codErrorPLSQL == 0){
						
						//Se actualiza el valor RMA en la lista de devoluciones
						devolucionFicha.codRMA = codRMA;
						$("#p87_input_rmaHidden").val(codRMA);
						
						//Control de mensaje de actualización de cabecera
						var mensajeActualizacionCabecera = "";
						var mensajeActualizacionCabeceraY = ""; //Utilizado para componer el guardado de cabecera con error en lineas 
						if (codRMAModificado87) {
							mensajeActualizacionCabecera = cabeceraActualizada;
							mensajeActualizacionCabeceraY = cabeceraActualizada + "<br/>"; 
						}
						
						//Restauramos el valor de modificadosP87.
						modificadosP87 = new Array();

						//Borrar el listado de modificadosP87
						listadoModificadosP87 = new Array();

						configurarGridP87(data.datos);
						
						//Miramos que el guardado no se haya hecho después de que un resulado de búsqueda de combos sea vacío
						if(data.datos.rows != null && data.datos.rows.length > 0){
							generateTooltipP87(data.datos);
							//rellenadoDeFilasP87();
						}else{
							//rellenadoDeFilasNoEncontradasP87();
						}						
						if(data.countGuardados == 0){
							if(data.countError == 0){
								createAlert(mensajeActualizacionCabecera, "INFO");
							}else if(data.countError == 1){										
								createAlert(replaceSpecialCharacters(mensajeActualizacionCabeceraY + data.countGuardados + lineasActualizadas + data.countError + lineaErronea), "ERROR");		
							}else{
								createAlert(replaceSpecialCharacters(mensajeActualizacionCabeceraY + data.countGuardados + lineasActualizadas + data.countError + lineasErroneas), "ERROR");						
							}
						}else{
							if(data.countGuardados == 1){
								if(data.countError == 0){
									createAlert(guardadoCorrectamente, "INFO");
								}else{
									if(data.countError == 1){											
										createAlert(replaceSpecialCharacters(mensajeActualizacionCabeceraY + data.countGuardados + lineaActualizada + data.countError + lineaErronea), "INFO");
									}else{
										createAlert(replaceSpecialCharacters(mensajeActualizacionCabeceraY + data.countGuardados + lineaActualizada + data.countError + lineasErroneas), "INFO");
									}
								}
							}else{
								if(data.countError == 0){
									createAlert(guardadoCorrectamente, "INFO");
								}else{
									if(data.countError == 1){											
										createAlert(replaceSpecialCharacters(mensajeActualizacionCabeceraY + data.countGuardados + lineasActualizadas + data.countError + lineaErronea), "INFO");
									}else{
										createAlert(replaceSpecialCharacters(mensajeActualizacionCabeceraY + data.countGuardados + lineasActualizadas + data.countError + lineasErroneas), "INFO");
									}
								}
							}
						}

						//SUMA
						getSumaCosteFinalP87(devolucionEditada);
					}else{
						createAlert(replaceSpecialCharacters(data.descErrorPLSQL), "ERROR");
					}
				}else{
					createAlert(replaceSpecialCharacters(errorActualizacion), "ERROR");
				}
			},
			error : function (xhr, status, error){
				handleError(xhr, status, error, locale);				
			}			
		});	
	}else{
		createAlert(replaceSpecialCharacters(emptyListaModificados), "ERROR");
	}	
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/***************************************************** FORMATEADORES GRID Y COLOREAR *********************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/


function descAbonoErrorFormatterP87(cellValue, opts, rData) {
	if(cellValue == null || cellValue.toString() == ""){
		cellValue = "";
		return cellValue;
	}else{

		descripcion = '<span id="p87_descAbonoErrorFormatterP87">' + cellValue + '</span>';
		return descripcion;

	}
}




function stockFormatterP87(cellValue, opts, rData) {
	if(cellValue == null || cellValue.toString() == ""){
		if(opts.colModel.name == "stockActual"){
			cellValue = "0";
			return $.formatNumber(cellValue,{format:"0.###",locale:"es"});
		}else{
			cellValue = "";
			return cellValue;
		}
	}else{
		return $.formatNumber(cellValue,{format:"0.###",locale:"es"});
	}
}

function cantAbonadaFormatterP87(cellValue, opts, rData) {
	if(cellValue == null || cellValue.toString() == ""){
		cellValue = "";
		return cellValue;
	}else{
		return $.formatNumber(cellValue,{format:"0.###",locale:"es"});
	}
}

function costeUnitarioFormatterP87(cellValue, opts, rData) {
	if(cellValue == null || cellValue.toString() == ""){
		cellValue = "";
		return cellValue;
	}else{
		return $.formatNumber(cellValue,{format:"0.###",locale:"es"});
	}
}

//Conseguimos el número de filas mostradas y las coloreamos si el stockActual es 0
function p87ControlesPantalla(){
	//Nos recorremos los registros existentes en pantalla.
	var rowsInPage = jQuery(gridP87.nameJQuery).jqGrid('getGridParam', 'reccount');
	for (var i = 0; i < rowsInPage; i++){
		//Controlamos las filas que vengan realizadas por Central o modificable a N del WS para pintarlas de Rojo.
		p87ColorearStockActual(i+1);
	}		
}

//Coloreamos las filas que tienen stockActual 0,000. Si no se ha modificado la columna de Cantidad a Devolver Centro, se colorea en rojo y si no en negro.
//Además si el estado de las lineas es abonado o incidencia se colorean las filas en verde o rojo. En estado abonado todas iran en verde, en incidencia algunas
//en rojo y otras en verde, según el estado de cada linea
function p87ColorearStockActual(filaAColorear){
	if (devolucionFicha.estadoCab==1 || devolucionFicha.estadoCab==2){
		var filaActualStockActualVal = jQuery(gridP87.nameJQuery).jqGrid('getCell',filaAColorear,'stockActual');

		var filaStockDevueltoActual = "#"+filaAColorear+"_stockDevuelto";
		var filaActualStockDevueltoVal = $(filaStockDevueltoActual).val();

		var filaActualStockActualValPunto = filaActualStockActualVal.replace(',','.');

		if((filaActualStockActualValPunto == "0.000" || filaActualStockActualValPunto == "0")  && filaActualStockDevueltoVal == ""){	
			var stockDevueltoOrig = filaAColorear+"_stockDevuelto_orig";
			var stockDevueltoActual = filaAColorear+"_stockDevuelto";

			var stockDevueltoValOrig = $("#"+stockDevueltoOrig).val();
			var stockDevueltoValActual = $("#"+stockDevueltoActual).val();

			var color = "R";
			if(tieneLapizLaFicha && (stockDevueltoValOrig != stockDevueltoValActual)){
				color = "N";
			}

			if(color == "R"){
				$(gridP87.nameJQuery).find("#"+(filaAColorear)).find("td").addClass("p87_columnaResaltada").removeClass("p87_columnaResaltadaNegro");
			}else{
				$(gridP87.nameJQuery).find("#"+(filaAColorear)).find("td").addClass("p87_columnaResaltadaNegro").removeClass("p87_columnaResaltada");
			}
		}
	}
	else if (devolucionFicha.estadoCab==3 || devolucionFicha.estadoCab==4){
		//Según el estado de la linea, se dibuja en verde o rojo.
		var estadoLin = $("#"+filaAColorear+"_estadoLin").val();
		if(estadoLin == 3){
			$(gridP87.nameJQuery).find("#"+(filaAColorear)).find("td").addClass("p87_columnaResaltadaVerde").removeClass("p87_columnaResaltadaNegro").removeClass("p87_columnaResaltada");
		}else if(estadoLin == 4){
			$(gridP87.nameJQuery).find("#"+(filaAColorear)).find("td").addClass("p87_columnaResaltada").removeClass("p87_columnaResaltadaNegro").removeClass("p87_columnaResaltadaVerde");
		}
	}
}


/**
 * En lugar de saber que columnas hay que poner en la tabla antes de consultar
 * vamos a coger los resultados y pintar las columnas en funcion del valor 
 * de la seccion de la primera linea (deberian ser todas iguales).
 * En caso de que sea la situacion inicial lanzarlo sin argumentos
 * para dejar la situacion por defecto.
 * @param data data.rows contiene DevolucionLinea. Si viene sin definir
 * se considera situacion inicial.
 */
function configurarColumnasSegunResultadosP87(data){
	var seccionValue = (!data || !data.rows || !data.rows[0]) ? null : data.rows[0].seccion;
	// son textil, secciones del 15 al 18
	var isReferenciaTextil = /^001[5-8]/.test(seccionValue);
	
	// ocultar o mostrar columnas
	var showHideColSoloTextil = isReferenciaTextil ? 'showCol' : 'hideCol';
	$(gridP87.nameJQuery).jqGrid(showHideColSoloTextil,'modelo');
	$(gridP87.nameJQuery).jqGrid(showHideColSoloTextil,'descrTalla');
	$(gridP87.nameJQuery).jqGrid(showHideColSoloTextil,'descrColor');
	$(gridP87.nameJQuery).jqGrid(showHideColSoloTextil,'modeloProveedor');
	
	// que ocupe lo mismo independiente del numero de columnas
	$(gridP87.nameJQuery).jqGrid('setGridWidth',950,true);
}

//Función que inserta eventos focusout, formateadores de expresiones regulares, ordenaciones de columnas, colores y datos al grid.
function configurarGridP87(data){

	//Se rellena el grid
	$(gridP87.nameJQuery)[0].addJSONData(data);
	gridP87.actualPage = data.page;
	gridP87.localData = data;
	
	configurarColumnasSegunResultadosP87(data);

	//Por cada fila, formatea los campos editables y les coloca campos con focusout.
	var ids = $(gridP87.nameJQuery).jqGrid('getDataIDs'), i, l = ids.length;
	for (var i = 0; i < l; i++) {
		$(gridP87.nameJQuery).jqGrid('editRow', ids[i], false);

		////Ponemos que solo puedan insertarse valores enteros
		$("#"+ids[i]+"_bulto").filter_input({regex:'[0-9]'});		

		//Si se pierde el foco, se ejecutará la función que muestre el disquete, error, etc.
		$("#"+ids[i]+"_bulto").focusout(function() {
			validacionBultoP87(this.id, 'S');
		});

		//Ponemos que solo puedan insertarse valores decimales
		$("#"+ids[i]+"_stockDevuelto").filter_input({regex:'[0-9,]'});	

		//Si se pierde el foco del campo se ejecuta la función que se utilizará para mostrar
		//un error, el disquete,etc. Se realiza cuando no existe el campo de bandejas que abre
		//el popup de bandejas y kgs.
		if($("#"+ids[i]+"_flgBandejas").val() != "S"){
			$("#"+ids[i]+"_stockDevuelto").focusout(function(e) {
				validacionStockDevueltoP87(this.id, 'S');							
			});
		}
		
		$("#"+ids[i]+"_stockDevuelto").change(function() {
			controlChangecolumnaStockDevueltoP87(this.id, gridP87.nameJQuery);
		});

		$("#"+ids[i]+"_bulto").focus(function() {
			controlChangecolumnaBultoP87(this.id);
			//Miramos el campo bandejas de la fila y si es S al pasar al bulto, cierra el popup.
			if($("#"+this.id.split("_")[0]+"_flgBandejas").val() == "S"){
				$("#p88_popUpCantidadDevueltaCentroKgs").dialog("close");
			}
		});

		$("#"+ids[i]+"_bulto").change(function() {
			controlChangecolumnaBultoP87(this.id);
		});
	}
	
	//Añadimos a todos los elementos de tipo "[0-9]+_stockDevuelto" (inputs de stockdevuelto) los eventos
	//click y select, para que cuando el usuario clique o se posicione en el input, se abra el popup de kilos
	//y bandejas o lo cierre. Antes, este contros estaba hecho con focus únicamente y dentro del blucle de
	//arriba, pero así como en firefox funcionaba bien, en internet explorer no. Se podría haber metido en el
	//bucle con select, pero es indiferente donde situarlo y así se ve más claro. El problema de internet explorer
	//es que por una razón que no comprendo, después de dibujar todas las filas al paginar o hacer una ordenación o
	//guardar, tras hacer todas las operaciones correctamente realizaba un focus de todos los input de stockdevuelto
	//abriendo el popup de kilos y bandejas. No comprendo por qué realizaba dichos eventos en pila, pero creo que
	//era por como construye los elementos en el DOM, ya que en FIREFOX funcionaba perfectamente. Con el evento select
	//se soluciona.
	$('#gridP87DevolCreadaCentro input[id$=_stockDevuelto]').select(function(){
		var fila = this.id.split("_")[0];
		var flgBandejas = $("#"+fila+"_flgBandejas").val();
		
		if(flgBandejas == "S"){
			$("#"+fila+"_stockDevuelto").attr('readonly', true);
			openPopUpCantidadDevueltaCentroYKgs(fila,gridP87.name,"P87");
		}else{
			$("#p88_popUpCantidadDevueltaCentroKgs").dialog("close");
		}
	});
	$('#gridP87DevolCreadaCentro input[id$=_stockDevuelto]').click(function(){
		var fila = this.id.split("_")[0];
		var flgBandejas = $("#"+fila+"_flgBandejas").val();
		
		if(flgBandejas == "S"){
			openPopUpCantidadDevueltaCentroYKgs(fila,gridP87.name,"P87");
		}else{
			$("#p88_popUpCantidadDevueltaCentroKgs").dialog("close");
		}
	});

	//Carga las ordenaciones de las columnas.
	cargarOrdenacionesColumnasP87();

	//Colorea de rojo las líneas que tengan el stockActual en 0
	p87ControlesPantalla();	
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/********************************************************** ORDENACIONES POR COLUMNA *********************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

function cargarOrdenacionesColumnasP87(){

	//Cuando pulse la columna para ordenar por icono tenemos que llamar manualmente al reload para que reordene,
	//ya que al tener el jsp campos editables no funciona de la manera convencional.
	$(gridP87.nameJQuery+"_codArticulo").unbind('click');
	$(gridP87.nameJQuery+"_codArticulo").bind("click", function(e) {
		//Antes de introducir esta validación, cuando se clicaba una reordenación de columna, hasta ahora ejecutaba primero este evento click y más tarde el focusout de la columna editable 
		//stockDevuelto (Cantidad a devolver centro). Esto creaba situaciones, en el que en caso de introducir un campo erroneo en stockDevuelto (con más de una coma,
		//más de 15 enteros o más de 3 decimales), no ejecutaba el focusout que se encarga de mostrar que ha habido un error de edición en ese campo y reordenaba directamente
		//la columna, siendo este comportamiento erroneo. Además creaba otro tipo de errores que se pueden ver en la aplicación como: 1. Editas un campo y tabulas, 2. Vuelves al
		//campo editado, 3. Introduces un error en el campo modificado, 4. Reordenas. Se quedaba la pantalla bloqueada!

		//Para realizar la validación cada vez que reordenamos, basta con introducir en este evento la validación del campo stockDevuelto y como id document.activeElement.id (Esto,
		//se encarga de devolver el id del foco actual). Como se ejecuta antes el evento click que el evento focusout, el foco estará en stockDevuelto y podremos realizar la validación correctamente.
		//En el caso de pasarla, se reordenará. En caso contrario devolverá error.

		//En el caso de que no estemos en ningún campo stockDevuelto y al clicar ordenar, se reordenará siempre- De esto se encarga la función validacionStockDevueltoP87, que controla que el id sea de tipo stockDevuelto,
		//en caso contrario, la reordenación se realiza siempre.
		if(validacionStockDevueltoP87(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificadosP87 y luego reordena.
			validacionStockDevueltoP87(document.activeElement.id, 'S');

			actualizarSortOrderP87('codArticulo');

			$(gridP87.nameJQuery).setGridParam({sortname:'codArticulo'});
			$(gridP87.nameJQuery).setGridParam({page:1});
			reloadData_gridP87DevolCreadaCentro();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});

	$(gridP87.nameJQuery+"_denominacion").unbind('click');
	$(gridP87.nameJQuery+"_denominacion").bind("click", function(e) {
		if(validacionStockDevueltoP87(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificadosP87 y luego reordena.
			validacionStockDevueltoP87(document.activeElement.id, 'S');

			actualizarSortOrderP87('denominacion');

			$(gridP87.nameJQuery).setGridParam({sortname:'denominacion'});
			$(gridP87.nameJQuery).setGridParam({page:1});
			reloadData_gridP87DevolCreadaCentro();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});

	$(gridP87.nameJQuery+"_modelo").unbind('click');
	$(gridP87.nameJQuery+"_modelo").bind("click", function(e) {
		if(validacionStockDevueltoP87(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificadosP87 y luego reordena.
			validacionStockDevueltoP87(document.activeElement.id, 'S');

			actualizarSortOrderP87('modelo');

			$(gridP87.nameJQuery).setGridParam({sortname:'modelo'});
			$(gridP87.nameJQuery).setGridParam({page:1});
			reloadData_gridP87DevolCreadaCentro();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});

	$(gridP87.nameJQuery+"_descrTalla").unbind('click');
	$(gridP87.nameJQuery+"_descrTalla").bind("click", function(e) {
		if(validacionStockDevueltoP87(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificadosP87 y luego reordena.
			validacionStockDevueltoP87(document.activeElement.id, 'S');

			actualizarSortOrderP87('descrTalla');

			$(gridP87.nameJQuery).setGridParam({sortname:'descrTalla'});
			$(gridP87.nameJQuery).setGridParam({page:1});
			reloadData_gridP87DevolCreadaCentro();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});

	$(gridP87.nameJQuery+"_descrColor").unbind('click');
	$(gridP87.nameJQuery+"_descrColor").bind("click", function(e) {
		if(validacionStockDevueltoP87(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificadosP87 y luego reordena.
			validacionStockDevueltoP87(document.activeElement.id, 'S');

			actualizarSortOrderP87('descrColor');

			$(gridP87.nameJQuery).setGridParam({sortname:'descrColor'});
			$(gridP87.nameJQuery).setGridParam({page:1});
			reloadData_gridP87DevolCreadaCentro();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});

	$(gridP87.nameJQuery+"_modeloProveedor").unbind('click');
	$(gridP87.nameJQuery+"_modeloProveedor").bind("click", function(e) {
		if(validacionStockDevueltoP87(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificadosP87 y luego reordena.
			validacionStockDevueltoP87(document.activeElement.id, 'S');

			actualizarSortOrderP87('modeloProveedor');

			$(gridP87.nameJQuery).setGridParam({sortname:'modeloProveedor'});
			$(gridP87.nameJQuery).setGridParam({page:1});
			reloadData_gridP87DevolCreadaCentro();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});
	
	$(gridP87.nameJQuery+"_costeUnitario").unbind('click');
	$(gridP87.nameJQuery+"_costeUnitario").bind("click", function(e) {
		if(validacionStockDevueltoP87(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificadosP87 y luego reordena.
			validacionStockDevueltoP87(document.activeElement.id, 'S');

			actualizarSortOrderP87('costeUnitario');

			$(gridP87.nameJQuery).setGridParam({sortname:'costeUnitario'});
			$(gridP87.nameJQuery).setGridParam({page:1});
			reloadData_gridP87DevolCreadaCentro();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});

	$(gridP87.nameJQuery+"_marca").unbind('click');
	$(gridP87.nameJQuery+"_marca").bind("click", function(e) {
		if(validacionStockDevueltoP87(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificadosP87 y luego reordena.
			validacionStockDevueltoP87(document.activeElement.id, 'S')

			actualizarSortOrderP87('marca');

			$(gridP87.nameJQuery).setGridParam({sortname:'marca'});
			$(gridP87.nameJQuery).setGridParam({page:1});
			reloadData_gridP87DevolCreadaCentro();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});

	$(gridP87.nameJQuery+"_stockActual").unbind('click');
	$(gridP87.nameJQuery+"_stockActual").bind("click", function(e) {
		if(validacionStockDevueltoP87(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificadosP87 y luego reordena.
			validacionStockDevueltoP87(document.activeElement.id, 'S');

			actualizarSortOrderP87('stockActual');

			$(gridP87.nameJQuery).setGridParam({sortname:'stockActual'});
			$(gridP87.nameJQuery).setGridParam({page:1});
			reloadData_gridP87DevolCreadaCentro();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});


	$(gridP87.nameJQuery+"_stockDevuelto").unbind('click');
	$(gridP87.nameJQuery+"_stockDevuelto").bind("click", function(e) {
		if(validacionStockDevueltoP87(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificadosP87 y luego reordena.
			validacionStockDevueltoP87(document.activeElement.id, 'S');

			actualizarSortOrderP87('stockDevuelto');

			$(gridP87.nameJQuery).setGridParam({sortname:'stockDevuelto'});
			$(gridP87.nameJQuery).setGridParam({page:1});
			reloadData_gridP87DevolCreadaCentro();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});

	$(gridP87.nameJQuery+"_bulto").unbind('click');
	$(gridP87.nameJQuery+"_bulto").bind("click", function(e) {
		if(validacionStockDevueltoP87(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificadosP87 y luego reordena.
			validacionStockDevueltoP87(document.activeElement.id, 'S');

			actualizarSortOrderP87('bulto');

			$(gridP87.nameJQuery).setGridParam({sortname:'bulto'});
			$(gridP87.nameJQuery).setGridParam({page:1});
			reloadData_gridP87DevolCreadaCentro();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});	 
	$(gridP87.nameJQuery+"_formatoDevuelto").unbind('click');
	$(gridP87.nameJQuery+"_formatoDevuelto").bind("click", function(e) {
		if(validacionStockDevueltoP87(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificadosP87 y luego reordena.
			validacionStockDevueltoP87(document.activeElement.id, 'S');

			actualizarSortOrderP87('formatoDevuelto');

			$(gridP87.nameJQuery).setGridParam({sortname:'formDevuelto'});
			$(gridP87.nameJQuery).setGridParam({page:1});
			reloadData_gridP87DevolCreadaCentro();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});	 
	$(gridP87.nameJQuery+"_cantAbonada").unbind('click');
	$(gridP87.nameJQuery+"_cantAbonada").bind("click", function(e) {
		if(validacionStockDevueltoP87(document.activeElement.id, 'N') == 'S'){			
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificadosP87 y luego reordena.
			validacionStockDevueltoP87(document.activeElement.id, 'S');

			actualizarSortOrderP87('cantAbonada');

			$(gridP87.nameJQuery).setGridParam({sortname:'cantAbonada'});
			$(gridP87.nameJQuery).setGridParam({page:1});
			reloadData_gridP87DevolCreadaCentro();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});	 
	$(gridP87.nameJQuery+"_descAbonoError").unbind('click');
	$(gridP87.nameJQuery+"_descAbonoError").bind("click", function(e) {
		if(validacionStockDevueltoP87(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificadosP87 y luego reordena.
			validacionStockDevueltoP87(document.activeElement.id, 'S');

			actualizarSortOrderP87('descAbonoError');

			$(gridP87.nameJQuery).setGridParam({sortname:'descAbonoError'});
			$(gridP87.nameJQuery).setGridParam({page:1});
			reloadData_gridP87DevolCreadaCentro();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});	 
}

function actualizarSortOrderP87 (columna) {

	var ultimoSortName = $(gridP87.nameJQuery).jqGrid('getGridParam','sortname');
	var ultimoSortOrder = $(gridP87.nameJQuery).jqGrid('getGridParam','sortorder');

	if (ultimoSortName != columna ) { //Se ha cambiado la columna por la que se quiere ordenar
		$(gridP87.nameJQuery).setGridParam({sortorder:'asc'});
		$(gridP87.nameJQuery + "_" + columna + " [sort='asc']").removeClass("ui-state-disabled");

		//En la columna anterior quitamos la flechas
		if (ultimoSortOrder=='asc'){
			$(gridP87.nameJQuery + "_" + ultimoSortName + " [sort='asc']").addClass("ui-state-disabled");
		}else{
			$(gridP87.nameJQuery + "_" + ultimoSortName + " [sort='desc']").addClass("ui-state-disabled");
		}
	} else { //Seguimos en la misma columna
		if (ultimoSortOrder=='asc'){
			$(gridP87.nameJQuery).setGridParam({sortorder:'desc'});
			//Mostramos la flecha descendente y quitamos la flecha ascendente
			$(gridP87.nameJQuery + "_" + columna + " [sort='desc']").removeClass("ui-state-disabled");
			$(gridP87.nameJQuery + "_" + columna + " [sort='asc']").addClass("ui-state-disabled");
		} else {
			$(gridP87.nameJQuery).setGridParam({sortorder:'asc'});
			$(gridP87.nameJQuery + "_" + columna + " [sort='asc']").removeClass("ui-state-disabled");
			$(gridP87.nameJQuery + "_" + columna + " [sort='desc']").addClass("ui-state-disabled");
		}  		
	}
}


function imprimirDevolucionesCreadaCentroP87(){

	var tipoImpresion = $("input[name='p87_rad_tipoimpresion']:checked").val();

	var url = './p95ListadoDevoluciones/getPdfDevolucionCreadaCentro.do?localizador='+devolucionFicha.localizador+'&tipoImpresion='+tipoImpresion+'&filtroProveedor='+comboValue;
	window.open(url,"_blank");
}

//Función que rellena las filas restantes de la última página como filas invalidadas
function rellenadoDeFilasP87() {
	//Numero de filas a mostras obtenidas
	var numeroDeFilasTotalReal = jQuery("#gridP87DevolCreadaCentro").jqGrid('getGridParam', 'records');

	//Numero de filas que hay que mostrar por página 10,20,50
	var numeroDeFilasPorPagina = jQuery("#gridP87DevolCreadaCentro").jqGrid('getGridParam', 'rowNum');

	//Indica la página actual que estamos viendo
	var numeroPaginaActual = jQuery("#gridP87DevolCreadaCentro").jqGrid('getGridParam', 'page');

	//Indica la cantidad de páginas que hay
	var numeroUltimaPagina = jQuery("#gridP87DevolCreadaCentro").jqGrid('getGridParam', 'lastpage');

	//Indica el número de filas que le corresponde a la última fila
	var numeroLineasUltimaPaginaReal = numeroDeFilasTotalReal % numeroDeFilasPorPagina;

	//Si el numero de líneas mostradas es menor que las líneas por página se rellena hasta alcanzar el total de líneas por página
	//Este caso sólo se va a producir en la última página porque las anteriores están totalmente rellenas
	if (numeroLineasUltimaPaginaReal!=0 && numeroPaginaActual == numeroUltimaPagina){
		for (var i = numeroLineasUltimaPaginaReal+1; i <= numeroDeFilasPorPagina; i++) {
			//Se añade la fila vacía
			$("#gridP87DevolCreadaCentro").addRowData(i, {});

			//Se añade la clase que pone gris las celdas de la fila
			$(gridP87.nameJQuery).find("#"+(i)).find("td").addClass("p87_filaInvalidada");

			//Se quita la clase de la fila para evitar que salgan opacidades, colores o líneas que separen las celdas
			$("#"+i).attr('class','');

			//Se elimina el 0 formateado de la fila stockActual por un vacío.
			$("#"+i).find('td[aria-describedby="gridP87DevolCreadaCentro_stockActual"]').html("");

			//Se quitan las numeraciones de las filas invalidadas
			//$("#"+i).find('td[aria-describedby="gridP87DevolCreadaCentro_rn"]').html("");
			//$("#"+i).find('td[aria-describedby="gridP87DevolCreadaCentro_rn"]').attr('class','');
			//$("#"+i).find('td[aria-describedby="gridP87DevolCreadaCentro_rn"]').addClass("p87_filaInvalidada");			
		}
		var recordText = $("#gridP87DevolCreadaCentro").jqGrid('getGridParam','recordtext');
		var nuevoRecordText =$.jgrid.format(recordText,(((numeroUltimaPagina-1)*numeroDeFilasPorPagina)+1),numeroDeFilasTotalReal,numeroDeFilasTotalReal);
		$("#pagerGridp87_right .ui-paging-info").html(nuevoRecordText);
	}	
}

//Función que rellena todas las filas en estado invalidado.
function rellenadoDeFilasNoEncontradasP87() {
	var numeroDeFilasPorPagina = jQuery("#gridP87DevolCreadaCentro").jqGrid('getGridParam', 'rowNum');
	for (var i = 0; i < numeroDeFilasPorPagina; i++) {
		//Se añade la fila vacía
		$("#gridP87DevolCreadaCentro").addRowData(i, {});
		
		//Se añade la clase que pone gris las celdas de la fila
		$(gridP87.nameJQuery).find("#"+(i)).find("td").addClass("p87_filaInvalidada");

		//Se quita la clase de la fila para evitar que salgan opacidades, colores o líneas que separen las celdas
		$("#"+i).attr('class','');
		
		//Se elimina el 0 formateado de la fila stockActual por un vacío.
		$("#"+i).find('td[aria-describedby="gridP87DevolCreadaCentro_stockActual"]').html("");
		
		//Se elimina el 0 formateado de la fila formato por un vacío.
		$("#"+i).find('td[aria-describedby="gridP87DevolCreadaCentro_formato"]').html("");
		
		//Se quitan las numeraciones de las filas invalidadas
		//$("#"+i).find('td[aria-describedby="gridP84DevolOrdenRetirada_rn"]').html("");
		//$("#"+i).find('td[aria-describedby="gridP84DevolOrdenRetirada_rn"]').attr('class','');
		//$("#"+i).find('td[aria-describedby="gridP84DevolOrdenRetirada_rn"]').addClass("p84_filaInvalidada");			
	}
	$("#pagerGridp87_right .ui-paging-info").html(gridP87.emptyRecords);
}

//Función que inserta titulos
function setTitleOnColumnHeaderP87(nombreColumna, titulo){
	$("#jqgh_gridP87DevolCreadaCentro_"+nombreColumna).prop('title',titulo);
}

function redirigirLogin() {
	window.location='./login.do';
}

//Función que sirve para que las fotos se muestren al pasar por encima de ciertas celdas de las columnas
function generateTooltipP87(data){
	var ids = jQuery(gridP87.nameJQuery).jqGrid('getDataIDs'), i, l = ids.length;	
	var colModel = $(gridP87.nameJQuery).jqGrid("getGridParam", "colModel");
	var rowData = null;
	for (i = 0; i < l; i++) {
		rowData = data.rows[i]
		//Si la referencia tiene foto
		if (rowData.flgFoto == "S"){
			var idArticulo = jQuery(gridP87.nameJQuery).jqGrid('getCell',ids[i],'codArticulo');

			var div = $("<div>");
			var idImg = "img_"+ids[i]+"_fotoTooltip"
			var img = $('<img id="'+idImg+'">'); //Equivalent: $(document.createElement('img'))
			var url = "./welcome/getImage.do?codArticulo="+idArticulo;//+"&flgControlCaprabo=S";
			img.attr('src',url);
			img.addClass("p87_img_tooltip");
			img.appendTo(div);
			img.attr("onerror", "redirigirLogin()");

			$.each(colModel, function(j) {
				var nombreColumna = colModel[j].name;
				var columna = $(gridP87.nameJQuery+" tbody #"+ ids[i]+ " td[aria-describedby='"+gridP87.name+"_"+nombreColumna+"']");
				columna.addClass("contieneTooltipFoto");
				columna.attr("title",div.html());
			});
			
			$(".contieneTooltipFoto").tooltip({
				position: {
					my: "left top",
					at: "right+5 bottom+5"
				}
			});
		}
	}
}

//Función que sirve para que las fotos se muestren al pasar por encima de ciertas celdas de las columnas
function generateTooltip(data){
	var ids = jQuery(gridP83.nameJQuery).jqGrid('getDataIDs'), i, l = ids.length;	
	var colModel = $(gridP83.nameJQuery).jqGrid("getGridParam", "colModel");
	var rowData = null;
	for (i = 0; i < l; i++) {
		rowData = data.rows[i]
		//Si la referencia tiene foto
		if (rowData.flgFoto == "S"){
			var idArticulo = jQuery(gridP83.nameJQuery).jqGrid('getCell',ids[i],'codArticulo');

			var div = $("<div>");
			var idImg = "img_"+ids[i]+"_fotoTooltip"
			var img = $('<img id="'+idImg+'">'); //Equivalent: $(document.createElement('img'))
			var url = "./welcome/getImage.do?codArticulo="+idArticulo;//+"&flgControlCaprabo=S";
			img.attr('src',url);
			img.addClass("p83_img_tooltip");
			img.appendTo(div);
			img.attr("onerror", "redirigirLogin()");

			$.each(colModel, function(j) {
				var nombreColumna = colModel[j].name;
				var columna = $(gridP83.nameJQuery+" tbody #"+ ids[i]+ " td[aria-describedby='"+gridP83.name+"_"+nombreColumna+"']");
				columna.addClass("contieneTooltipFoto");
				columna.attr("title",div.html());
			});

			$(".contieneTooltipFoto").tooltip({
				position: {
					my: "left top",
					at: "right+5 bottom+5"
				}
			});
		}
	}
}

function refAutoCompleteP87(){
	var cache = {};
	$("#gbox_gridP87DevolCreadaCentro #gs_codArticulo").autocomplete({
		minLength: 1,
		mustMatch:true,
		source: function( request, response ) {
			//Miramos si el grid tiene al menos una fila para hacer la búsqueda.
			var gridConFila = jQuery(gridP87.nameJQuery).jqGrid('getGridParam', 'records') > 0;
			if(gridConFila){
				var term = request.term;

				var devolucion = new Devolucion(devolucionFicha.centro,flgHistorico,devolucionFicha.titulo1,devolucionFicha.flgCodArticulo,
						devolucionFicha.estadoCab,devolucionFicha.localizador,devolucionFicha.devolucion,devolucionFicha.fechaDesde,devolucionFicha.fechaHasta,
						devolucionFicha.flgRecogida,devolucionFicha.abono,devolucionFicha.recogida,devolucionFicha.codPlataforma,devolucionFicha.descripcion,
						devolucionFicha.motivo,devolucionFicha.titulo2,devolucionFicha.fechaPrecio,devolucionFicha.codError,devolucionFicha.descError,
						devolucionFicha.devLineas,listadoModificados,comboValue,null);

				var objJson = $.toJSON(devolucion);
				$.ajax({
					type : 'POST',
					url: "./devoluciones/popup/creadaCentro/loadRefPattern.do?term="+ request.term,
					data : objJson,
					contentType : "application/json; charset=utf-8",
					dataType: "json",
					cache : false,

					success: function(data) {
						cache[ term ] = data;
						response($.map(data, function(item) {
							return {
								label: item  ,
								id: item ,
								abbrev: item
							};
						}));
					}
				});
			}
		},

		select: function(event, ui) {
			oneEventFired = true;
			reset_p87();
			reloadData_FiltroP87(ui.item.id);
			this.close;
		}
	});
}