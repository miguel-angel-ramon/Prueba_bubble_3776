/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/****************************************************************** CONSTANTES ***************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Insertamos el número máximo de enteros y decimales que se podrán introducir. Servirán para P83 y P83
var maxNumEnteros = 15;
var maxNumDecimales = 3;

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/**************************************************************** VARIABLES ******************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

var p83BotonAceptar = null;

//Variable que guarda el grid
var gridP83=null;

//Variable que guarda el valor del combo seleccionado. Es útil en las paginaicones y ordenaciones 
//para hacer la búsqueda filtrando con este campo sobre la tabla temporal
var comboValue = null;

//La lista guarda los elementos modificados de cada página. Si hay un elemento modificado, se edita y sale error, recupera los datos por defecto, pero el elemento
//sigue estando en esta lista. 
var seleccionados = new Array();

//La lista de seleccionados guarda todos los registros que han sido modificados en todas las páginas. En caso de existir un registro modificado y al volverlo a modificar devuelve error
//ese registro se elimina de esta lista, pues el registro obtiene los valores por defecto y por ende deja de estar modificado. Si se vuelve a modificar se inserta
//de nuevo aquí.
var modificados = new Array();

//En la primera carga se inicia el contador a 1.
var numRow=1;

//Texto de iconos
var iconoGuardado=null;
var iconoModificado = null;

//Texto de errores
var descError = null;
var stockDevueltoIncorrecto = null;
var stockDevueltoEnteroErroneo = null;
var stockDevueltoDecimalErroneo = null;
var stockDevueltoDecimalErroneo2 = null;
var stockDevueltoPesoVariable = null; // MISUMI-259
var sumaSuperaMaximoP83 = null;
var cantidadMaximaLinSuperadaP83 =null;
var emptyListaModificados = null;
var notPosibleToReturnDataP83 = null;
var errorActualizacionRegistro = null;
var errorActualizacion = null;

//Mensaje después de guardado.
var lineasActualizadas = null;
var lineaActualizada = null;
var lineasActualizadasY = null;
var lineaActualizadaY = null;
var lineaErronea = null;
var lineasErroneas = null;
var cabeceraActualizada = null;

//Mensaje titulo popup fin de campaña
var finDeCampana = null;

//Titulos
var stockDevueltoNoPrepararMercanciaTitle = null;
var stockDevueltoPrepararMercanciaTitle = null;
var stockDevueltoTitlePrepararMercancia = null;
var stockDevueltoTitleNoPrepararMercancia = null;

//Guarda una lista de DevolucionLineaModificado, que contiene por cada línea modificada de la página actual un objeto con 
//datos del: bulto, stockDevuelto,codigoError y codigoArticulo.
var listadoModificados = new Array();

var tableFilter=null;

//Se inicializa resultadoValidacionP84 en 'S' para que antes de editar stockDevuelto se pueda paginar sin problemas
var resultadoValidacion = 'S';

////Definimos ultimoBultoIntroducidoP84 para guardar el último bulto introducido por proveedor y que se copie en los demás bultos
var ultimoBultoIntroducido = {};

//Variable para calcular la posicion X del tooltip
var p83PositionTooltipX = 0;
//var pos = $(this).position();

var codRMAModificado83 = false;

var esPrepararMercancia = 0; // permite saber si se ha elegido en el gráfico de pantalla las devoluciones de "Preparar Mercancia"

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************** DOCUMENTO LISTO **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

$(document).ready(function(){
	//Inicializa el grid
	loadP83(locale);

	//Inicializa el popup que contiene el grid con las líneas de devolución
	initializeScreenP83PopupDevolFinCampana();

	//Nos posicionamos en el campo al que ha navegado el usuario y que hemos almacenado en el hidden.
	//De esta forma al clicar aceptar en la alerta, vuelve al último campo de edición del grid.
	$("#p03_btn_aceptar").bind("click", function(e) {
		if ($("#p83_fld_StockDevuelto_Selecc").val() != '' && $("#p83_fld_StockDevuelto_Selecc").val() != undefined)
		{
			$("#"+$("#p83_fld_StockDevuelto_Selecc").val()).focus();
			$("#"+$("#p83_fld_StockDevuelto_Selecc").val()).select();
			e.stopPropagation();
			$("#p83_fld_StockDevuelto_Selecc").val('');
		}
	});

	//Eventos flecha formularios
	events_p83_btn_actualizarDevolucionLineas();
	events_p83_btn_impresora();
});


/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************ INICIALIZACIÓN DE ELEMENTOS E IDIOMA *******************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

/************************ IDIOMA Y GRID ************************/
function loadP83(locale){
	//Define la estructura del grid, sus columnas de dónde van a obtener los datos etc.
	gridP83 = new GridP83DevolFinCampana(locale);

	//Define el ancho del grid.
	$(gridP83.nameJQuery).jqGrid('setGridWidth',950, true);

	//Carga en data los nombres de las colummnas del grid, etc.
	var jqxhr = $.getJSON(gridP83.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		//Carga el nombre de las columnas, titulo, etc.
		gridP83.colNames = data.ColNames;
		gridP83.title = data.GridTitle;
		gridP83.emptyRecords= data.emptyRecords;
		iconoGuardado = data.iconoGuardado; 
		iconoModificado = data.iconoModificado; 	
		stockDevueltoIncorrecto = data.stockDevueltoIncorrecto;
		stockDevueltoEnteroErroneo = data.stockDevueltoEnteroErroneo;
		stockDevueltoDecimalErroneo = data.stockDevueltoDecimalErroneo;
		stockDevueltoDecimalErroneo2 = data.stockDevueltoDecimalErroneo2;
		stockDevueltoPesoVariable = data.stockDevueltoPesoVariable; // MISUMI-259
		sumaSuperaMaximoP83 = data.sumaSuperaMaximoP83;
		emptyListaModificados = data.emptyListaModificados;
		guardadoCorrectamente = data.guardadoCorrectamente;
		lineasActualizadas = data.lineasActualizadas;
		lineaActualizada = data.lineaActualizada;
		lineasActualizadasY = data.lineasActualizadasY;
		lineaActualizadaY = data.lineaActualizadaY;
		finDeCampana = data.finDeCampana;
		sobrestock = data.sobrestock;
		notPosibleToReturnDataP83 = data.notPosibleToReturnData;
		errorActualizacion = data.errorActualizacion;
		errorActualizacionRegistro = data.errorActualizacionRegistro;
		lineasErroneas = data.lineasErroneas;
		lineaErronea = data.lineaErronea;
		cabeceraActualizada = data.cabeceraActualizada;

		stockDevueltoTitleNoPrepararMercancia = data.stockDevueltoTitleNoPrepararMercancia;
		stockDevueltoTitlePrepararMercancia = data.stockDevueltoTitlePrepararMercancia;
		stockDevueltoNoPrepararMercanciaTitle = data.stockDevueltoTitle;
		stockDevueltoPrepararMercanciaTitle = data.stockDevueltoPrepararMercanciaTitle;
		cantidadMaximaLinSuperadaP83 = data.cantidadMaximaLinSuperadaP83;

		tableFilter = data.tableFilter;

		//Definimos las columnas, las llamadas al paginar, ordenar etc.
		load_gridP83DevolFinCampanaMock(gridP83);

		$("#p83_cmb_proveedor").combobox(null);
		p83SetHeadersTitles(data);
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

/************************ GRID ************************/
//Clase de constantes para el GRID de cantidades no servidas para una referencia
//Define la estructura del grid
function GridP83DevolFinCampana (locale){
	// Atributos
	this.name = "gridP83DevolFinCampana"; 
	this.nameJQuery = "#gridP83DevolFinCampana"; 

	this.i18nJSON = './misumi/resources/p83PopUpDevolFinCampana/p83PopUpDevolFinCampana_' + locale + '.json';

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
		"formatter" : costeUnitarioFormatter,
		"width"     : 70,	
		"align"     : "left"						
	},{
		"name"      : "cantidadMaximaLin",
		"index"     : "cantidadMaximaLin", 
		"width"     : 70,	
		"formatter" :  cantidadMaximaLinFormatter,
		"align"     : "left"						
	},
	{
		"name"      : "stockActual",
		"index"     : "stockActual",
		"width"     : 40,
		"fixed":true,
		"formatter" :  stockFormatter,
		"align"     : "left",

	},{
		"name"      : "stockTienda",
		"index"     : "stockTienda",
		"width"     : 40,
		"fixed":true,
		"formatter" :  stockFormatter,
		"align"     : "left"
	},{
		"name"      : "stockDevolver",
		"index"     : "stockDevolver",
		"width"     : 40,
		"formatter" : stockFormatter,
		"align"     : "left"
	},{
		name       : 'stockDevuelto',
		index      : 'stockDevuelto',
		width      : 40,	        	  
		editable   : true,	
		fixed      : true,
		formatter  :  stockFormatter,
		editoptions:{
			size      : '3',
			maxlength : maxNumEnteros + maxNumDecimales + 1,
			dataEvents: [
			               {//control para el keydown
			            	   type: 'keydown',
			            	   fn: controlNavegacionP83,
			               },
			               {//control para el blur
			            	   type: 'blur',
			            	   fn: controlClickP83columnaStockDevuelto
			               }
			               ] 
		},
		align      : 'left'
	},{
		name     : 'cantAbonada',
		index    : 'cantAbonada',
		width    : 70,	
		fixed    : true,
		formatter: cantAbonadaFormatter,
		align    : 'left'
	},{
		name     : 'descAbonoError',
		index    : 'cantAbonada',
		width    : 110,	
		formatter: descAbonoErrorFormatter,
		align    : 'left'
	},{
		name       : 'bulto',
		index      : 'bulto',
		width      : 40,
		editable   : true,
		fixed      : true,
		formatter  : bultoFormatter,
		editoptions:{
			"size":"3",
			"maxlength":"2",
			"dataEvents": [
			               {//control para el keydown
			            	   type: 'keydown',
			            	   fn: controlNavegacionP83,
			               },// cierra el control del keydown
			               {//control para el click
			            	   type: 'click',
			            	   fn: controlClickP83
			               }
			               ] 
		},		
		"align"     : "left"
	},{
		name : 'anadirBulto',
		index: 'anadirBulto',
		width: 20,
		fixed: true,
		align: 'left',
		formatter: function (cellvalue, options, rowObject){
					let numRow = options.rowId; // Usar el rowId de las opciones para obtener el número de fila correcto
   					if ( $("#p83_27_pc_devoluciones_procedimiento").val() == "true" ){
						return "<div id='"+numRow+"_p83_div_anadirBultos' align='center'><img id='"+numRow+"_p83_btn_anadirBultos' src='./misumi/images/plus.png' width='20' height='20'></div>";
					}
				  }
	},{
		name : 'formatoDevuelto',
		index: 'formatoDevuelto',
		width: 70,
		fixed: true,
		align: 'left'
	},{
		name : 'tipoReferencia',
		index: 'tipoReferencia',
		width: 70,
		fixed: true,
		align: 'left'
	},{
		name     : 'mensaje',
		index    : 'mensaje', 
		formatter: imageFormatMessage,
		fixed    : true,
		width    : 30,
		sortable : true
	},{
		name  : 'estadoCerrado',
		index : 'estadoCerrado',
		hidden: true
	},{
		name  : 'bultoEstadoCerrado',
		index : 'bultoEstadoCerrado',
		hidden: true
	}
	];
	this.locale = locale;
	//this.sortIndex = "";
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerGridp83";  
	this.pagerNameJQuery = "#pagerGridp83";
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
		if (this.actualPage == null){
			this.actualPage = 1;
		}else{
			this.actualPage = $(this.nameJQuery).getGridParam('page');
		}

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
		var colModel = jQuery(gridP83.nameJQuery).jqGrid('getGridParam', 'colModel'); 
		var i;
		var l = colModel.length;
		var colItem; 
		var cmName;
		var postData = jQuery(gridP83.nameJQuery).jqGrid('getGridParam', 'postData');
		var columnsState = {
				search: jQuery(gridP83.nameJQuery).jqGrid('getGridParam', 'search'),
				page: jQuery(gridP83.nameJQuery).jqGrid('getGridParam', 'page'),
				sortname: jQuery(gridP83.nameJQuery).jqGrid('getGridParam', 'sortname'),
				sortorder: jQuery(gridP83.nameJQuery).jqGrid('getGridParam', 'sortorder'),
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
function load_gridP83DevolFinCampanaMock(gridP83) {

	$(gridP83.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		datatype : 'local',
		contentType : 'application/json',
		mtype : 'POST',
		colNames : gridP83.colNames,
		colModel : gridP83.cm,
		rowNum : 10,
		rowList : [ 10, 30, 50 ],
		height : "100%",
		autowidth : true,
		width : "100%",
		rownumbers : gridP83.rownumbers,
		pager : gridP83.pagerNameJQuery,
		viewrecords : true,
		caption : gridP83.title,
		altclass: "ui-priority-secondary",
		altRows: true, //false, para que el grid no muestre cebrado
		hidegrid : false, //false, para ocultar el boton que colapsa el grid.
		sortable : true,
		index: gridP83.sortIndex,
		sortname: gridP83.sortIndex,
		sortorder: gridP83.sortOrder,
		emptyrecords : gridP83.emptyRecords,
		gridComplete : function() {
			gridP83.headerHeight("p83_gridHeader");		

			//Una vez cargado el grid, iniciamos el contador a 1.
			//Al ser una variable global y actualizarse cada vez
			//que se abre un nuevo grid en la función imageFormatMessage
			//obtiene valores no acordes con el número de fila cargado
			//por eso hay que iniciarlo a 1.
			numRow = 1;
		},
		loadComplete : function(data) {
			gridP83.actualPage = data.page;
			gridP83.localData = data;
			gridP83.sortIndex = gridP83.sortIndex;
			gridP83.sortOrder = gridP83.sortOrder;	

			//Para el filtro del grid
			$(gridP83.nameJQuery).jqGrid('filterToolbar',{defaultSearch : "cn"});
			$("#gbox_gridP83DevolFinCampana #gs_marca").css("display", "none");
			$("#gbox_gridP83DevolFinCampana #gs_denominacion").css("display", "none");
			$("#gbox_gridP83DevolFinCampana #gs_modelo").css("display", "none");
			$("#gbox_gridP83DevolFinCampana #gs_descrTalla").css("display", "none");
			$("#gbox_gridP83DevolFinCampana #gs_descrColor").css("display", "none");
			$("#gbox_gridP83DevolFinCampana #gs_costeUnitario").css("display", "none");
			$("#gbox_gridP83DevolFinCampana #gs_cantidadMaximaLin").css("display", "none");
			$("#gbox_gridP83DevolFinCampana #gs_modeloProveedor").css("display", "none");
			$("#gbox_gridP83DevolFinCampana #gs_stockActual").css("display", "none");
			$("#gbox_gridP83DevolFinCampana #gs_stockTienda").css("display", "none");
			$("#gbox_gridP83DevolFinCampana #gs_stockDevuelto").css("display", "none");
			$("#gbox_gridP83DevolFinCampana #gs_bulto").css("display", "none");

			if ($("#prepararMercancia").val()=="1"){
				// comprobar si el centro está parametrizado.
				if ( $("#p83_27_pc_devoluciones_procedimiento").val() == "true" ){
					$("#gbox_gridP83DevolFinCampana #gs_bulto").closest("th").css("border-right-color","transparent");
					$("#p83_MensajeAvisoAreaCombos").show();
				}
				$("#gbox_gridP83DevolFinCampana #gs_anadirBulto").css("display", "none");
			}else{
				$("#gbox_gridP83DevolFinCampana #gs_bulto").closest("th").css("border-right-color","ligthgray");
				$("#p83_MensajeAvisoAreaCombos").hide();
			}
			
			$("#gbox_gridP83DevolFinCampana #gs_formatoDevuelto").css("display", "none");	
			$("#gbox_gridP83DevolFinCampana #gs_tipoReferencia").css("display", "none");	
			$("#gbox_gridP83DevolFinCampana #gs_mensaje").css("display", "none");
			$("#gbox_gridP83DevolFinCampana #gs_cantAbonada").css("display", "none");
			$("#gbox_gridP83DevolFinCampana #gs_descAbonoError").css("display", "none");
			$("#gbox_gridP83DevolFinCampana #gs_estadoCerrado").css("display", "none");

			$("#gbox_gridP83DevolFinCampana #gs_codArticulo").on("keydown", function(e) {
				var classActual = e.target.className;
				if(e.which == 13) {
					if (classActual != "ui-pg-input"){
						e.preventDefault();
						reset_p83(0);
						reloadData_FiltroP83($("#gbox_gridP83DevolFinCampana #gs_codArticulo").val());
					}
				}
			});

			//Evento que autocompleta el filtro de las referencias al pulsar un número.
			//Por ejemplo, Si metemos un 1 nos saldría un combo con las referencias que
			//contengan un 1.
			refAutoCompleteP83();

			$("#p83_AreaReferencias .loading").css("display", "none");	
		},
		resizeStop: function () {
			gridP83.saveColumnState.call($(this),gridP83.myColumnsState);
			$(gridP83.nameJQuery).jqGrid('setGridWidth', 950, false);
		},
		onPaging : function(postdata) {	
			//Antes de introducir este control, en el caso de estar en un campo editable stockDevuelto y clicar que pagine, se ejecutaba primero el evento focusout y más tarde este evento onPaging.
			//Esto creaba situaciones en las que al introducir un campo erroneo en stockDevuelto (con más de una coma, más de 15 enteros o más de 3 decimales), primero lanzaba el error de que el campo
			//se había introducido mal y más tarde paginaba. Para ello, como el focusout realiza una validación de que el campo editable es correcto o incorrecto y devuelve una variable llamada resultadoValidacion 'S' o 'N',
			//se ha transformado esa variable en global. De esta forma, al realizar el focusout se realiza la validación, en el caso de haber error lo muestra e indica al evento onPaging de que no tiene que realizar
			//la recarga de datos ni la paginación. En caso de que se introduzca un campo correcto en stockDevuelto, el focusout realiza una validación correcta y recalcula los datos del grid.

			//Puede darse la situación de que no se realice el focusout de y por consecuente la validación de stockDevuelto, porque no se haya editado ningún campo o porque el usuario no se haya situado en ninguno de esos campos. Por lo cual, la variable
			//resultadoValidacion se inicializa siempre en 'S'. Cuando se clica a paginar, pagine o no, resultadoValidacion obtiene el valor 'S', puesto que en el caso de haber editado un dato de forma erronea se autocorrige, y queremos que si el usuario
			//clica el elemento paginar al instante, pagine.
			if(resultadoValidacion == 'S'){
				alreadySorted = false;
				gridP83.sortIndex = gridP83.sortIndex;
				gridP83.sortOrder = gridP83.sortOrder;
				gridP83.saveColumnState.call($(this), this.p.remapColumns);
				reloadData_gridP83DevolFinCampana();

				return 'stop';
			}else{
				//Se cambia el estado de la validación a ´S´, porque anque haya error, se corrige el dato y queremos que se pueda paginar.
				resultadoValidacion = 'S';

				//En caso de existir un error, no se pagina y devuelve error. Pero el componente internamente pagina, por ese motivo, hay que restar 1 a la paginación. 
				$('#gridP83DevolFinCampana').setGridParam({page:gridP83.getActualPage()-1});

				return 'stop';			
			}
		},
		onSelectRow: function(id){

		},
		onSortCol: function (index, columnIndex, sortOrder){
			gridP83.sortIndex = index;
			gridP83.sortOrder = sortOrder;
			gridP83.saveColumnState.call($(this), this.p.remapColumns);
			reloadData_gridP83DevolFinCampana();
			return 'stop';
		},
		afterEditCell: function(rowid, colname, value, iRow, iCol) {
			// Aquí puedes realizar acciones cuando la celda pierde el foco
							alert("La celda ha perdido el foco: " + rowid + ", " + colname);
		},
		jsonReader : {
			root : "rows",
			page : "page",
			total : "total",
			records : "records",
			repeatitems : false
		},
		loadError: function (xhr, status, error){
			handleError(xhr, status, errgridale);
		}
	});
}


/************************ POPUP ************************/
function initializeScreenP83PopupDevolFinCampana(){

	$( "#p83_popUpDevolFinCampana" ).dialog({
		autoOpen: false,
		height: 'auto',
		width: 'auto',
		modal: true,
		resizable: false,
		open: function() {
			$('#p83_popUpDevolFinCampana .ui-dialog-titlebar-close').on('mousedown', function(){
				//Antes de cerrar el popup reseteamos el array de seleccionados
				seleccionados = new Array();
				$("#p83_popUpDevolFinCampana").dialog('close');
			});
		},
		close: function( event, ui ) {
			//Al salir eliminar la lista de seleccionados y de listadoModificados.
			//Además limpiar grid y poner elementos a buscar en 10
			reset_p83(1);
		},
		//Fijar el z-index del popup p56 para que las opciones del combobox no se queden detrás del popup 
		//al autoincrementarse el z-index.
		stack: false

	});

	$(window).bind('resize', function() {
		$("#p83_popUpDevolFinCampana").dialog("option", "position", "center");
	});
}

/************************ TITULOS COLUMNAS GRID ************************/
function p83SetHeadersTitles(data){
	var colModel = $(gridP83.nameJQuery).jqGrid("getGridParam", "colModel");
	$.each(colModel, function(i) {
		if (colModel[i].name!="rn"){
			$("#jqgh_gridP83DevolFinCampana_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
		}
	});
}

/************************ EVENTOS ************************/
//Evento para botón guardar cambios de la ventana principal. 
function events_p83_btn_actualizarDevolucionLineas(){
	$("#p83_btn_guardado").click(function () {		
		actualizarDevolucionLineas();
	});	
}

//Evento para botón de buscar
function events_p83_btn_impresora(){
	$("#p83_btn_impresora" ).unbind("click");
	$("#p83_btn_impresora").click(function () {		
		imprimirDevolucionesFinCampana();
	});	
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************EVENTOS CAMPOS  DEL GRID ********************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

// Se encarga de asignar el primer bulto abierto para
// cada proveedor cuando no tiene ninguno asignado.
function cargarBultoAbiertoPorProveedor(listaProveedores){
	let clavesProveedores = Object.keys(listaProveedores);

	for (var i=0;i<clavesProveedores.length;i++){
		let valorBuscado = clavesProveedores[i];

		if (listaProveedores[valorBuscado]==""){
			let idElemento = $('input[type="hidden"]').filter(function() { return $(this).val() == valorBuscado; }).first().attr('id');

			if (idElemento!=undefined){
				let idProveedor = idElemento.substr(0,idElemento.indexOf("_"));
				let bultoAbierto=$("#"+idProveedor+"_bultoAbierto").val().substr(0,$("#"+idProveedor+"_bultoAbierto").val().indexOf(","));
				listaProveedores[valorBuscado]=bultoAbierto;
			}
		}
	}
}

function controlNavegacionP83(e) {

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
			validacionNavegacion = validacionStockDevuelto(fila + "_stockDevuelto", 'N');
			
			//MISUMI-269
			var valorCantidadMaximaLin = jQuery(gridP83.nameJQuery).jqGrid('getCell',fila,'cantidadMaximaLin');
			var filaStockDevueltoActual = "#"+fila+"_stockDevuelto";
			var valorStockDevueltoActual = $(filaStockDevueltoActual).val();
			//Si la cantidad maxima tiene valor
			if(valorCantidadMaximaLin != ""){
				//Si el stock devuelto introducido es mayor a la cantidad maxima lin 
				if(parseFloat(valorStockDevueltoActual.replace(",",".")) > parseFloat(valorCantidadMaximaLin.replace(",","."))){
					//Ponemos el stockdevueltoactual con el valor de cantidad maxima lin
					$("#" + fila + "_stockDevuelto").addClass("errCantMaxLin");
					
					//Si hay error de cantidad máxima, ponemos N en validacionNavegacion para que lance un evento focusout del stock que lance el error.
					//El validacionNavegacion de antes del actualizar, no valida errores, sirve para insertar los elementos CORRECTOS en modificados.
					//Si tratasemos el error de cantidadMaximaLin ahí, lanzaría un error. Al mostrar el mensaje, se haría focusout del stock y lanzaría
					//otro error que atasca la pantalla.
					validacionNavegacion = "N";
				}
			}			
			if (validacionNavegacion =='S'){ 
				//Se llama a la función directamente, porque en esta función se calcula el elemento modificado, que indica si se ha modificado un
				//elemento del grid. En el caso de haberse modificado un elemento, se actualizan las líneas. Anteriormente, se hacía un focusout para
				//que saltara esta misma función, pero al ejecutar focusout, ejecutaba actualizarDevolucionLineas antes que validacionStockDevuelto se ejecutara, por lo que
				//no daba tiempo a calcular que el elemento se había modificado y no se actualizaba la línea.
				
				//Es necesario que esta función SÓLO sirva aquí para actualizar los modificados. Si queremos que valide errores, es mejor que no entre en
				//este if y que validacionNavegacion = "N". Así se hará un focus en el botón, y stockdevuelto realizará un focusout que trate los errores.
				//En caso de tratar aquí errores, es posible que se atasque la pantalla, ya que al saltar alertas, se lanzan los focusout del stock devuelto
				//y se pueden crear más alertas, cada una con su ui-widget-overlay (capa gris). Al cerrar la alerta se elimina una de las capas, pero las de las
				//demás alarmas se mantienen y se atasca. 
				validacionStockDevuelto(this.id, 'S');
				
				//Guarda los datos modificados
				actualizarDevolucionLineas();
			}else{
				$("#p82_btn_buscar").focus();
			}
		}
		if(nombreColumna == "bulto"){
			validacionNavegacion = validacionBulto(fila + "_bulto", 'N');
			if (validacionNavegacion =='S'){

				//Se llama a la función directamente, porque en esta función se calcula el elemento modificado, que indica si se ha modificado un
				//elemento del grid. En el caso de haberse modificado un elemento, se actualizan las líneas. Anteriormente, se hacía un focusout para
				//que saltara esta misma función, pero al ejecutar focusout, ejecutaba actualizarDevolucionLineas antes que validacionBulto se ejecutara, por lo que
				//no daba tiempo a calcular que el elemento se había modificado y no se actualizaba la línea.
				validacionBulto(this.id, 'S');

				//Guarda los datos modificados
				actualizarDevolucionLineas();
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
			validacionNavegacion = validacionBulto(fila + "_bulto", 'N');
			if (validacionNavegacion =='S'){
				$("#"+idFoco).focus();
				$("#"+idFoco).select();
				$("#p83_fld_Bulto_Selecc").val(idFoco);
				controlClickP83(e,key);
			}else{
				$("#p82_btn_buscar").focus();	        	
			}
		}
	}

	//Tecla derecha
	if (key == 39){
		e.preventDefault();
		if(nombreColumna == "stockDevuelto"){

// Inicio MISUMI-259
//			var filaFlgBandejas = "#"+fila+"_flgBandejas";
//            var valorFlgBandejas = $(filaFlgBandejas).val();
//            
//            var filaFlgPesoVariable = "#"+fila+"_flgPesoVariable";
//            var valorFlgPesoVariable = $(filaFlgPesoVariable).val();
//
//            var filaCampoActual = "#"+fila+"_stockDevuelto";
//            var campoActual = $(filaCampoActual).val();
//            
//            if(valorFlgBandejas != 'S' && valorFlgPesoVariable != 'S' && campoActual != 0 && campoActual.indexOf(',') == -1){
//    			validacionNavegacion = validacionStockDevuelto(fila + "_stockDevuelto", 'S');
//            }else{
// FIN MISUMI-259
            	validacionNavegacion = validacionStockDevuelto(fila + "_stockDevuelto", 'N');
//            }

			if (validacionNavegacion=='S'){
				idFoco = fila + "_bulto";
				$("#"+idFoco).focus();
				$("#"+idFoco).select();
				$("#p83_fld_Bulto_Selecc").val(idFoco);
				controlClickP83(e,key);
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
			validacionNavegacion = validacionBulto(fila + "_bulto", 'N');
		}
		if(nombreColumna == "stockDevuelto"){
			validacionNavegacion = validacionStockDevuelto(fila + "_stockDevuelto", 'N');
		}
		if (validacionNavegacion=='S'){    		    			    		    		
			$("#"+idFoco).focus();
			$("#"+idFoco).select();
			$("#p83_fld_Bulto_Selecc").val(idFoco);  

			if(nombreColumna == "bulto"){
				controlClickP83(e,key);
			}

			if(nombreColumna == "stockDevuelto"){
				controlClickP83columnaStockDevuelto(e,key);
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
			validacionNavegacion = validacionBulto(fila + "_bulto", 'N');
		}
		if(nombreColumna == "stockDevuelto"){
			validacionNavegacion = validacionStockDevuelto(fila + "_stockDevuelto", 'N');
		}

		if (validacionNavegacion=='S'){    		    			
			$("#"+idFoco).focus();
			$("#"+idFoco).select();
			$("#p83_fld_Bulto_Selecc").val(idFoco);
			if(nombreColumna == "bulto"){
				controlClickP83(e,key);
			}

			if(nombreColumna == "stockDevuelto"){
				controlClickP83columnaStockDevuelto(e,key);
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
				controlClickP83columnaStockDevuelto(e,key);
			}								
		}

		if(nombreColumna == "stockDevuelto"){
			validacionNavegacion = validacionStockDevuelto(fila + "_stockDevuelto", 'N');
			if (validacionNavegacion =='S'){ 
				$("#"+idFoco).focus();
				$("#"+idFoco).select();
				$("#p83_fld_Bulto_Selecc").val(idFoco);

				controlClickP83(e,key);
			}else{
				$("#p82_btn_buscar").focus();
			}
		}
		if(nombreColumna == "bulto"){
			validacionNavegacion = validacionBulto(fila + "_bulto", 'N');
			if (validacionNavegacion =='S'){
				$("#"+idFoco).focus();
				$("#"+idFoco).select();
				$("#p83_fld_Bulto_Selecc").val(idFoco);

				controlClickP83columnaStockDevuelto(e,key);

			}else{
				$("#p82_btn_buscar").focus();	        	
			}
		}
	}
}

//En el caso de no tener valor la 1 fila de bulto pone un 0. En el caso de que el bulto de un elemento sea vacío pero el de su anterior
//no, se pone el valor del bulto anterior en ese elemento.
function controlClickP83(e,key){
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
//		var valorIdActual = $(filaBultoActual).val();
		$(filaBultoActual).val("0");
	}
}


function controlClickP83columnaStockDevuelto(e,key){
	var idActual = e.target.id;

	//Obtención de fila y columna actuales
	var fila = idActual.substring(0, idActual.indexOf("_"));

	var provrGenFila = "#"+fila+"_provrGenLin";
	var provrGenFilaValor = $(provrGenFila).val();

	var filaBultoActual = "#"+fila+"_bulto";
	var filaStockDevueltoActual = "#"+fila+"_stockDevuelto";
	var valorStockDevueltoActual = $(filaStockDevueltoActual).val();

	if (valorStockDevueltoActual != "" && valorStockDevueltoActual != "0") {
		var valorIdActual = $(filaBultoActual).val();
		if (valorIdActual == "" || valorIdActual == null || valorIdActual == "null" || valorIdActual == "0"){

			//Se inserta en la fila actual el valor de la fila anterior
			$(filaBultoActual).val(ultimoBultoIntroducido[provrGenFilaValor]);

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

function controlChangecolumnaStockDevuelto(idActual, gridActual){

	//Obtención de fila y columna actuales
	var fila = idActual.substring(0, idActual.indexOf("_"));

	var provrGenFila = "#"+fila+"_provrGenLin";
	var provrGenFilaValor = $(provrGenFila).val();

	var filaBultoActual = "#"+fila+"_bulto";
	var filaStockDevueltoActual = "#"+fila+"_stockDevuelto";
	var valorStockDevueltoActual = $(filaStockDevueltoActual).val();

	var numerosEnteros = valorStockDevueltoActual.split(",")[0];
	var numerosDecimales = valorStockDevueltoActual.split(",")[1];

//	var filaFlgBandejas = "#"+fila+"_flgBandejas";
//	var valorFlgBandejas = $(filaFlgBandejas).val();
//	
//	var filaFlgPesoVariable = "#"+fila+"_flgPesoVariable";
//	var valorFlgPesoVariable = $(filaFlgPesoVariable).val();

	
	//Antes de calcular la suma se mira que el formato del stock sea correcto
	if (!((valorStockDevueltoActual.split(",").length > 2) || 
			(valorStockDevueltoActual.indexOf(',') > -1 && (numerosEnteros.length > maxNumEnteros || numerosDecimales.length > maxNumDecimales)) ||
			(numerosEnteros.length > maxNumEnteros))){
		//MISUMI-269
		var valorCantidadMaximaLin = jQuery(gridP83.nameJQuery).jqGrid('getCell',fila,'cantidadMaximaLin');
		//Si la cantidad maxima tiene valor
		if(valorCantidadMaximaLin != ""){
			//Si el stock devuelto introducido es mayor a la cantidad maxima lin 
			if(parseFloat(valorStockDevueltoActual.replace(",",".")) > parseFloat(valorCantidadMaximaLin.replace(",","."))){
				//Ponemos el stockdevueltoactual con el valor de cantidad maxima lin
				$("#" + fila + "_stockDevuelto").addClass("errCantMaxLin");
			}
		}

		//BAZAR
		var valorCosteFinalActual = $(gridActual + " #"+fila+"_costeFinal").val();  //Valor del precio costo final antes de modificar 
		var sumaAux = $("#p83_lbl_ValorSumaSinFormateo").val(); //Valor de del campo sumatorio
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

		var maximo = Number($("#p83_lbl_ValorCosteMaximoHidden").val()); //Valor de del campo Maximo

//		if (valorFlgBandejas == 'N' && valorFlgPesoVariable == 'S'){
//			$("#"+fila+"_stockDevuelto").removeAttr( "style" );
//			$("#"+fila+"_stockDevuelto").attr("style", "color:red; font-size:10px;");
//		}

		if (suma > maximo) { // si la suma supera a los euros maximos, ambos campos hay que pintarlos en rojo
			$("#p83_lbl_TextoSuma").addClass("p83TextoRojo");
			$("#p83_lbl_ValorSuma").removeAttr( "style" );
			$("#p83_lbl_ValorSuma").attr("style", "color:red; font-size:12px;");
			$("#p83_lbl_TextoCosteMaximo").addClass("p83TextoRojo");
			$("#p83_lbl_ValorCosteMaximo").addClass("p83TextoRojo");
		} else {
			$("#p83_lbl_TextoSuma").removeClass("p83TextoRojo");
			$("#p83_lbl_ValorSuma").removeAttr( "style" );
			$("#p83_lbl_ValorSuma").attr("style", "font-size:12px;");
			$("#p83_lbl_TextoCosteMaximo").removeClass("p83TextoRojo");
			$("#p83_lbl_ValorCosteMaximo").removeClass("p83TextoRojo");
		}

		$("#p83_lbl_ValorSumaSinFormateo").val(suma);
		$("#p83_lbl_ValorSuma").val($.formatNumber(suma,{format:"0.#",locale:"es"}));
		$(gridActual + " #"+fila+"_costeFinal").val(costeFinal);

		if (valorStockDevueltoActual == "0"){
			$(filaBultoActual).val("0");
		}

	}
}

// obtiene el siguiente valor de bulto válido.
//function obtenerBultoLibre(i){
//	let stockDevueltoValue=$("#"+i+"_stockDevuelto").val();
//
//	if(validarStockDevuelto(i)=='S'){
//
//		let bultoValue=$("#"+i+"_bulto").val();
//			
//		if (bultoValue=="" && stockDevueltoValue!=""){
//				let listaBultos=$("#listaBultos").val();
//				let listaBultosAux=listaBultos.split(",");
//				
//				for(var idx = 0; i<listaBultosAux.length && bultoValue=="" ; idx++){
//					
//					let existeBulto=false;
//					
//					for(var j = 1; j<11 && !existeBulto; j++){
//						let valorBultoComparacion=$("#"+j+"_bulto").val();
//						if(valorBultoComparacion==listaBultosAux[idx]){
//							existeBulto=true;
//						}
//					}
//					if(!existeBulto){
////						$(gridP83.nameJQuery).jqGrid('setCell',i,'bulto',listaBultosAux[idx]);
//						bultoValue=$("#"+i+"_bulto").val(listaBultosAux[idx]);
//					}
//					
//				}
////		}else if(stockDevueltoValue==""){
////				document.getElementById(campoBultoCaja).style.display = "none";
////				document.getElementById("pda_p103_lbl_Error4").style.display = "none";
////		}else if(stockDevueltoValue!=""){
////				var valorEstadoCerrado="pda_p103_estadoCerrado"+indice;
////				document.getElementById(campoBultoCaja).style.display = "block";
////				document.getElementById(valorEstadoCerrado).value='N';
////				document.getElementById("pda_p103_lbl_Error4").style.display = "none";
//		}
////	}else{
////		if(valorCantidad!=''){
////			document.getElementById(campoCantidad).focus();
////			document.getElementById(campoCantidad).select();
////			//ERRORES EN LA VALIDACIÓN DE LA CANTIDAD POR LO QUE PONEMOS EL FOCO EN LA CANTIDAD Y MOSTRAMOS EL ERROR
////			document.getElementById("pda_p103_lbl_Error").style.display = "block";
////			document.getElementById("pda_p103_lbl_Error2").style.display = "none";
////			document.getElementById("pda_p103_lbl_Error3").style.display = "none";
////			document.getElementById("pda_p103_lbl_Error4").style.display = "none";
////			document.getElementById(campoBultoCaja).style.display = "none";
////		}else{
////			document.getElementById("pda_p103_lbl_Error").style.display = "none";
////			document.getElementById(campoBultoCaja).style.display = "none";
////			document.getElementById("pda_p103_lbl_Error4").style.display = "none";
////		}
//	}
//}

function validarStockDevuelto(i){
	let resultadoValidacion = 'S';
//	let stockDevueltoValue = $(gridP83.nameJQuery).jqGrid('getCell', i, 'stockDevuelto').val();
	let stockDevueltoValue = $("#"+i+"_stockDevuelto").val();
	
	if (stockDevueltoValue != null && typeof(stockDevueltoValue) != 'undefined') {
		let flgPesoVariablePantalla=$("#"+i+"_flgPesoVariable").val();
		let stockDevueltoIsOk;
		if(flgPesoVariablePantalla=='S'){
			//Validación número decimal
			stockDevueltoIsOk = /^(-?\d+((\,|\.)\d{0,3}){0,1})?$/.test(stockDevueltoValue);
		}else{
			//Validación número entero
			stockDevueltoIsOk = /^\d{0,2}$/.test(stockDevueltoValue);
		}
		if (!stockDevueltoIsOk){
			resultadoValidacion = 'N';
		}
	}
	
	return resultadoValidacion;
}

function validarBulto(indice){
	var resultadoValidacion = 'S';
	var idBulto = 'pda_p103_bulto'+indice;
	var campoActualBulto = document.getElementById(idBulto);
	if (campoActualBulto != null && typeof(campoActualBulto) != 'undefined') {
		var numeroBultoIsOk = /^\d{0,2}$/.test(campoActualBulto.value);
		if (!numeroBultoIsOk){
			resultadoValidacion = 'N';
		}
	}
	return resultadoValidacion;
}


/** valida si la suma excede demasiado el maximo */
function validateMaximoCambioStockP83(idActual, gridActual){

	var valido = true;

	//Valor de del campo Maximo
	var maximo = Number($("#p83_lbl_ValorCosteMaximoHidden").val());

	if(maximo > 0){

		//Obtención de fila y columna actuales
		var fila = idActual.substring(0, idActual.indexOf("_"));

		var valorStockDevueltoActual = $("#"+fila+"_stockDevuelto").val();

		if(valorStockDevueltoActual){
			valorStockDevueltoActual = Number(valorStockDevueltoActual.replace(',','.'));
		}

		//BAZAR
		var valorCosteFinalActual = $(gridActual + " #"+fila+"_costeFinal").val();  //Valor del precio costo final antes de modificar 
		var sumaAux = $("#p83_lbl_ValorSumaSinFormateo").val(); //Valor de del campo sumatorio
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

//Valida si el stock actual es mayor a la cantidad maxima de la linea
function validateCantidadMaximaLinP83(idActual, gridActual){
	var valido = true;

	//Si tiene el error de cantidad maxima, vamos a mostrar error
	if($("#"+idActual).hasClass("errCantMaxLin")){
		valido = false;

		//Quitamos la clase del error por si el usuario vuelve a cambiar el valor.
		$("#"+idActual).removeClass("errCantMaxLin")
	}

	return valido;
}

function controlFocuscolumnaBulto(idActual){

	//Obtención de fila y columna actuales
	let fila = idActual.substring(0, idActual.indexOf("_"));

	let filaBultoActual = "#"+fila+"_bulto";
	let filaStockDevueltoActual = "#"+fila+"_stockDevuelto";
	let valorStockDevueltoActual = $(filaStockDevueltoActual).val();

	let provrGenFila = "#"+fila+"_provrGenLin";
	let provrGenFilaValor = $(provrGenFila).val();

	if (valorStockDevueltoActual != null && valorStockDevueltoActual != "" && valorStockDevueltoActual != "0") {
		let valorIdActual =  $(filaBultoActual).val();
		if(valorIdActual == "" || valorIdActual == null || valorIdActual == "null"){
			//Se inserta en la fila actual el valor de la fila anterior
			$(filaBultoActual).val(ultimoBultoIntroducido[provrGenFilaValor]);
		}						
	}else if (valorStockDevueltoActual == "0"){
		$(filaBultoActual).val("0");
	}	
}

// comprueba si hay que actualizar el bulto abierto asignado
// al proveedor de la referencia en la que se está actuando.
function controlChangecolumnaBulto(idActual){

	//Obtención de fila y columna actuales
	let fila = idActual.substring(0, idActual.indexOf("_"));
	let filaBultoActual = "#"+fila+"_bulto";

	let provrGenFila = "#"+fila+"_provrGenLin";
	let provrGenFilaValor = $(provrGenFila).val();

	let valorIdActual =  $(filaBultoActual).val();
	// compruebo si la celda del Bulto tiene valor.
	if (valorIdActual != "" && valorIdActual != null && valorIdActual != "0" && valorIdActual != "null"){
		
		let valoresBultoAbierto=$("#"+fila+"_bultoAbierto").val();
		let valorBultoGrid=parseInt($("#"+idActual).val());
		let primerBultoAbierto=0;
		let ultimoBultoAbierto=0;
			
		if (valoresBultoAbierto!=""){
			primerBultoAbierto=parseInt(valoresBultoAbierto.substr(0,valoresBultoAbierto.indexOf(",")));
			ultimoBultoAbierto=parseInt(valoresBultoAbierto.substr(valoresBultoAbierto.lastIndexOf(",")+1));
		}
				
		// antes de actualizar el valor del bulto abierto en la lista de proveedores
		// , se comprueba que el bulto indicado es uno de los bultos abiertos disponibles.
		if (ultimoBultoAbierto != -1
			&& $("#"+idActual).val() >= primerBultoAbierto && $("#"+idActual).val() <= ultimoBultoAbierto){
			//se actualiza en la lista de proveedores el último bulto abierto asignado.
			ultimoBultoIntroducido[provrGenFilaValor]=$("#"+idActual).val();
		}else{
			if (ultimoBultoIntroducido[provrGenFilaValor] != ""){
				$("#"+idActual).val(ultimoBultoIntroducido[provrGenFilaValor]);
			}else{
				ultimoBultoIntroducido[provrGenFilaValor]=$("#"+idActual).val();
//				$("#"+fila+"_bultoAbierto").val($("#"+idActual).val());
			}
		}
	}						
}

function imageFormatMessage(cellValue, opts, rData) {

	var imagen = "";
	var mostrarModificado = "none;";
	var mostrarGuardado = "none;";
	var mostrarError = "none;";
	var descError = "";

	//Controlamos los posibles errores que me lleguen para pintar el icono correspondiente.
	if (parseFloat(rData['codError']) == '8'){
		//Pintamos el icono de que se ha guardado
		mostrarGuardado = "block;";
	}else if (parseFloat(rData['codError']) == '9'){
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

	imagen = "<div id='"+numRow+"_stockDevueltoBulto_divGuardado' align='center' style='display: "+ mostrarGuardado + "'><img id='"+numRow+"_stockDevueltoBulto_imgGuardado' src='./misumi/images/floppy.png' title='"+iconoGuardado+"'/></div>"; //Guardado
	imagen += "<div id='"+numRow+"_stockDevueltoBulto_divModificado' align='center' style='display: "+ mostrarModificado + "'><img id='"+numRow+"_stockDevueltoBulto_imgModificado' src='./misumi/images/modificado.png' title='"+iconoModificado+"'/></div>"; //Modificado
	imagen += "<div id='"+numRow+"_stockDevueltoBulto_divError' align='center' style='display: " + mostrarError + "'><img id='"+numRow+"_stockDevueltoBulto_imgError' src='./misumi/images/dialog-error-24.png' title='"+descError+"'/></div>"; //Error		

	var stockOrig = rData['stockDevueltoOrig'];
	var stock = rData['stockDevuelto'];
	var datoStockDevueltoOrig;
	var datoStockDevuelto;

	if(stockOrig != null){
		datoStockDevueltoOrig = "<input type='hidden' id='"+numRow+"_stockDevuelto_orig' value='"+$.formatNumber(stockOrig,{format:"0.###",locale:"es"})+"'>";
	}else{
		datoStockDevueltoOrig = "<input type='hidden' id='"+numRow+"_stockDevuelto_orig' value=''>";
	}

	if(stock != null){
		datoStockDevuelto = "<input type='hidden' id='"+numRow+"_stockDevuelto_tmp' value='"+$.formatNumber(stock,{format:"0.###",locale:"es"})+"'>";
	}else{		
		datoStockDevuelto = "<input type='hidden' id='"+numRow+"_stockDevuelto_tmp' value=''>";
	}

	imagen +=  datoStockDevueltoOrig;
	imagen +=  datoStockDevuelto;

	var bultoOrig = rData['bultoOrig'];
	var bulto = rData['bulto'];
	var datoBultoOrig;
	var datoBulto;

	if(bultoOrig != null){
		datoBultoOrig = "<input type='hidden' id='"+numRow+"_bulto_orig' value='"+bultoOrig+"'>";
	}else{
		datoBultoOrig = "<input type='hidden' id='"+numRow+"_bulto_orig' value=''>";
	}

	if(bulto != null){
		datoBulto = "<input type='hidden' id='"+numRow+"_bulto_tmp' value='"+bulto+"'>";
	}else{
		datoBulto = "<input type='hidden' id='"+numRow+"_bulto_tmp' value=''>";
	}
	imagen +=  datoBultoOrig;
	imagen +=  datoBulto;

	//Se guarda en un hidden el flag de las bandejas. Si es S, se muestra el popup, si no, no se deja
	//el input.
	var flgBandejas = rData['flgBandejas'];

// Inicio MISUMI-259
	var flgPesoVariable = rData['flgPesoVariable'];

	var datoFlgBandejas;
	var datoFlgPesoVariable;
// FIN MISUMI-259

	if(flgBandejas != null){
		datoFlgBandejas = "<input type='hidden' id='"+numRow+"_flgBandejas' value='"+flgBandejas+"'>";
	}else{
		datoFlgBandejas = "<input type='hidden' id='"+numRow+"_flgBandejas' value=''>";
	}

// Inicio MISUMI-259
	if(flgPesoVariable != null){
		datoFlgPesoVariable = "<input type='hidden' id='"+numRow+"_flgPesoVariable' value='"+flgPesoVariable+"'>";
	}else{
		datoFlgPesoVariable = "<input type='hidden' id='"+numRow+"_flgPesoVariable' value=''>";
	}
// FIN MISUMI-259
	
	imagen += datoFlgBandejas;
	imagen += datoFlgPesoVariable; // MISUMI-259

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
	imagen += datoStockDevueltoBandejasOrig;

	//Añadimos también el valor del codError de cada registro.
	var datoError = "<input type='hidden' id='"+numRow+"_stockDevueltoBulto_codError_orig' value='"+rData['codError']+"'>";
	imagen += datoError;

	//Rellenamos un hidden que guardará el dato que contendrá el primer elemento de bulto de cada paginación en el caso de estar vacío. 
	if(numRow == 1){
		var primerElementoBulto = "<input type='hidden' id='primerElementoBulto' value='"+rData['primerElementoBulto']+"'>";	
		imagen += primerElementoBulto;
	}
	//Rellenamos un hidden que guardará el dato que contendrá el primer elemento de stockDevuelto de cada paginación en el caso de estar vacío. 
	if(numRow == 1){
		var primerElementoStockDevuelto = "<input type='hidden' id='primerElementoStockDevuelto' value='"+rData['primerElementoStockDevuelto']+"'>";	
		imagen += primerElementoStockDevuelto;
	}

	var estadoLinea = "<input type='hidden' id='"+numRow+"_estadoLin' value='"+rData['estadoLin']+"'>";	
	imagen += estadoLinea;

	var provrGenLinea = "<input type='hidden' id='"+numRow+"_provrGenLin' value='"+rData['provrGen']+"'>";	
	imagen += provrGenLinea;

	var costeFinal = "<input type='hidden' id='"+numRow+"_costeFinal' value='"+rData['costeFinal']+"'>";	
	imagen += costeFinal;

	var costeUnitarioAux = "<input type='hidden' id='"+numRow+"_costeUnitarioAux' value='"+rData['costeUnitario']+"'>";	
	imagen += costeUnitarioAux;
	
	var variosBultos = "<input type='hidden' id='"+numRow+"_variosBultos' value='"+rData['variosBultos']+"'>";	
	imagen += variosBultos;
	
	var devolucion = "<input type='hidden' id='"+numRow+"_devolucion' value='"+rData['devolucion']+"'>";	
	imagen += devolucion;

	let bultoAbierto = "<input type='hidden' id='"+numRow+"_bultoAbierto' value='"+rData['listaBultos']+"'>";
	imagen += bultoAbierto;
	
	numRow++;

	return imagen;
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************** VALIDACIONES CAMPOS EDITABLES DEL GRID ***************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

function validacionStockDevuelto(id, mostrarAlerta){
	//La validación antes de ningún tratamiento es satisfactoria, después puede no serlo
	resultadoValidacion = 'S';

	//Este if se encarga de que se calcule la validación si venimos de un campo stockDevuelto. Se ha introducido para que se puedan realizar validaciones en ordenaciones de columnas
	//del método cargarOrdenacionesColumnasP84(). Hasta ahora, siempre que llegaba a esta función era por estar tratando el campo stockDevuelto. En las reordenaciones de columnas, puede
	//que se clique la columna viniendo de un campo editable de stockDevuelto o de cualquier otra parte de la pantalla. Por eso se ha introducido este control.
	if (id.indexOf("_stockDevuelto") != -1){
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

// Inicio MISUMI-259
		    var filaFlgBandejas = "#"+fila+"_flgBandejas";
		    var valorFlgBandejas = $(filaFlgBandejas).val();
		    
		    var filaFlgPesoVariable = "#"+fila+"_flgPesoVariable";
		    var valorFlgPesoVariable = $(filaFlgPesoVariable).val();

			//Miramos que el dato original no sea ""
			var campoStockDevueltoOrigPunto = "";
			if($("#"+campoStockDevueltoOrig).val() != "" && $("#"+campoStockDevueltoOrig).val() != "null" && $("#"+campoStockDevueltoOrig).val() != null){
				campoStockDevueltoOrigPunto = $("#"+campoStockDevueltoOrig).val().replace(',','.');
			}
// FIN MISUMI-259
		    
			if(campoActual != ""){
				//Si hay más de una coma o el campo introducido es vacío mostrar el error
				if (campoActual.split(",").length > 2){
					descError = replaceSpecialCharacters(stockDevueltoIncorrecto);
					createAlert(descError, "ERROR");

					//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
					$("#p83_fld_StockDevuelto_Selecc").val(fila + "_stockDevuelto");

					//Pintamos de rojo el campo.
					$("#"+id).removeClass("editable").addClass("editableError");

					//El error se cambia a 1 para realizar su tratamiento
					error = 1;
			// Inicio MISUMI-259
				// Se mostrará aviso cuando se haya cambiado el valor de carga inicial, el FLG_BANDEJAS != 'S'
				// , el FLG_PESO_VARIABLE = 'S', el valor indicado != 0 y no tenga decimales.
				}else if(campoActual != campoStockDevueltoOrigPunto && valorFlgBandejas != 'S'
						 && valorFlgPesoVariable == 'S' && campoActual != 0 && campoActual.indexOf(',') == -1){
					descError = replaceSpecialCharacters(stockDevueltoPesoVariable);
//					createAlert(descError, "ERROR");

					//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
					$("#p83_fld_StockDevuelto_Selecc").val(fila + "_stockDevuelto");

					//Pintamos de rojo el campo.
					$("#"+id).removeClass("editable").addClass("editableError");

					//Se asigna el valor 2 a la variable "error" para indicar que se trata de un AVISO. 
					error = 2;
			// FIN MISUMI-259
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
				var errorMaximoCambioStock = 0;
				var errorStr = "";
				if(!error && !validateMaximoCambioStockP83(id, gridP83.nameJQuery)) {
					errorStr = sumaSuperaMaximoP83;
					errorMaximoCambioStock = 1;
				}

				// Si no hay error previo, miramos la cantidad máxima.
				var errorCantidadMaximaLin = 0;
				if(!error && !validateCantidadMaximaLinP83(id, gridP83.nameJQuery)) {
					errorStr = errorStr + cantidadMaximaLinSuperadaP83;
					errorCantidadMaximaLin = 1;
				}
				
				//Si cualquiera de las dos validaciones anteriores da error, se pone mensaje y error a 1.
				if(errorMaximoCambioStock > 0 || errorCantidadMaximaLin > 0){
					createAlert(errorStr, "ERROR");
					error = 1;
				}
				
// Inicio MISUMI-259
// Se traslada la carga del valor de "campoStockDevueltoOrigPunto" justo antes de cuando
// se determina el valor de la variable "error".
				//Miramos que el dato original no sea ""
//				var campoStockDevueltoOrigPunto = "";
//				if($("#"+campoStockDevueltoOrig).val() != "" && $("#"+campoStockDevueltoOrig).val() != "null" && $("#"+campoStockDevueltoOrig).val() != null){
//					campoStockDevueltoOrigPunto = $("#"+campoStockDevueltoOrig).val().replace(',','.');
//				}
// FIN MISUMI-259

				if (error == 1){
					//En este caso ha ocurrido un error y hay que mostrar el icono de error.
					$("#"+campoDivGuardado).hide();
					$("#"+campoDivModificado).hide();
					$("#"+campoDivError).show();

					//Cambiamos el title
					$("#"+campoImgError).attr('title', descError);

					//Añadimos la fila al array.
					addSeleccionados(fila);

					//Eliminamos el elemento de los array de modificados, al haber un error y recuperar sus datos por defecto.
					var valor = $("#gridP83DevolFinCampana #"+fila+" [aria-describedby='gridP83DevolFinCampana_rn']").text();
					if(modificados.indexOf(valor+"_stockDevuelto") != -1){ 
						var index = modificados.indexOf(valor+"_stockDevuelto");
						modificados.splice(index,1);
					}

					if(modificados.indexOf(valor+"_bulto") != -1){ 
						var index = modificados.indexOf(valor+"_bulto");
						modificados.splice(index,1);
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
					
					var variosBultos = $("#"+fila+"_variosBultos").val();
					if(!variosBultos){
						//Inicializamos el bulto también
						var valorBultoOrig = $("#"+fila+"_bulto_orig").val();
						$("#"+fila+"_bulto_tmp").val(valorBultoOrig);
						$("#"+fila+"_bulto").val(valorBultoOrig);
					}

					//Inicializamos las bandejas
					var valorBandejasOrig = $("#"+fila+"_stockDevueltoBandejas_orig").val();
					$("#"+fila+"_stockDevueltoBandejas_orig").val(valorBandejasOrig);
					$("#"+fila+"_stockDevueltoBandejas_tmp").val(valorBandejasOrig);
					
					// recalculamos y asignamos el valor de la suma ahora que hemos modificado el valor del stock
					controlChangecolumnaStockDevuelto(id, gridP83.nameJQuery);


					//El código de error de la fila será 0 que equivale a un error
					$("#"+campoErrorOrig).val("0");

					//Sirve para el onPaging del grid.
					resultadoValidacion = 'N';
					
			// Inicio MISUMI-259
				}else if (error == 2 && campoActual != campoStockDevueltoOrigPunto){
					//En este caso ha ocurrido un error y hay que mostrar el icono de error.
					$("#"+campoDivGuardado).hide();
					$("#"+campoDivModificado).hide();
					$("#"+campoDivError).show();

					//Cambiamos el title
					$("#"+campoImgError).attr('title', descError);

					var campoActualFormatter = $.formatNumber(campoActual.replace(',','.'),{format:"0.###",locale:"es"});
					$("#"+campoStockDevueltoActual).val(campoActualFormatter);
					if (($("#"+campoStockDevueltoTmp).val() != null) && $("#"+campoStockDevueltoTmp).val() != campoActualFormatter){
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
						addSeleccionados(fila);

						//Insertamos el elemento de los array de modificados
						var valor = $("#gridP83DevolFinCampana #"+fila+" [aria-describedby='gridP83DevolFinCampana_rn']").text();
						if(modificados.indexOf(valor+"_stockDevuelto") === -1){ 							
							modificados.push(valor+"_stockDevuelto");
						}
					}
						
			// FIN MISUMI-259
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
						addSeleccionados(fila);

						//Insertamos el elemento de los array de modificados
						var valor = $("#gridP83DevolFinCampana #"+fila+" [aria-describedby='gridP83DevolFinCampana_rn']").text();
						if(modificados.indexOf(valor+"_stockDevuelto") === -1){ 							
							modificados.push(valor+"_stockDevuelto");
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

					addSeleccionados(fila);
					//Insertamos el elemento de los array de modificados
					var valor = $("#gridP83DevolFinCampana #"+fila+" [aria-describedby='gridP83DevolFinCampana_rn']").text();
					if(modificados.indexOf(valor+"_stockDevuelto") === -1){ 							
						modificados.push(valor+"_stockDevuelto");
					}

					$("#"+campoErrorOrig).val("9");
				}
			}

			//Colorear de negro la fila si stockActual es 0,000  y actualizamos el campo. Si vuelves a poner el campo original 
			//lo pondrá en rojo.
			p83ColorearStockActual(fila);
		}else{
			// Cuando no hay que sacar mensajes
			//Control de campo decimal erroneo
			if (campoActual.split(",").length > 2){
				resultadoValidacion = 'N';
			}else if(campoActual != ""){
				var numerosEnteros = campoActual.split(",")[0];
				var numerosDecimales = campoActual.split(",")[1];

				if(numerosDecimales && numerosDecimales.length > maxNumDecimales ||
					numerosEnteros && numerosEnteros.length > maxNumEnteros) {
					resultadoValidacion = 'N';
				}else if(!validateMaximoCambioStockP83(id, gridP83.nameJQuery)) {
					resultadoValidacion = 'N';
				}else{
					var campoActualPunto = $("#"+id).val().replace(',','.');
					$("#"+id).val(campoActualPunto).formatNumber({format:"0.###",locale:"es"});
				}
			}
		}
	}
	return resultadoValidacion;
}

function validacionBulto(id, mostrarAlerta){
	var resultadoValidacion = 'S';

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
			addSeleccionados(fila);

			//Insertamos el elemento de los array de modificados
			var valor = $("#gridP83DevolFinCampana #"+fila+" [aria-describedby='gridP83DevolFinCampana_rn']").text();
			if(modificados.indexOf(valor+"_bulto") === -1){ 
				modificados.push(valor+"_bulto");
			}

		}
	}

	return resultadoValidacion;
}

//Esta lista solo contiene las filas modificadas de la página actual, de esta forma, guarda los registros modificados 
//en la temporal según se va reordenando el grid, paginando, etc.
function addSeleccionados(fila){
	seleccionados[fila] = fila;
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/*************************************************************FUNCIONES DE P83 ***************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

function cargarLineasDeDevolucionFinDeCampana(devolucion,tieneLapizLaFicha){
	
	// guardo el valor del tipo de devolución en la variable global que se ha marcado en el gráfico de pantalla.
	// Me permite saber si se ha pinchado en Preparar Mercancia para activar o desactivar el botón "Añadir Bulto" (+)
	esPrepararMercancia = devolucion.estadoCab;
	
	//Si se ha clicado una ficha con lapiz será un grid editable, si no no.
	if(!tieneLapizLaFicha || userPerfilMisumi == CONS_CONSULTA_ROLE){
		$(gridP83.nameJQuery).jqGrid('setColProp', 'stockDevuelto', {editable:false});
		$(gridP83.nameJQuery).jqGrid('setColProp', 'bulto', {editable:false});

		$(gridP83.nameJQuery).jqGrid('hideCol','mensaje');

		$("#p83_btn_guardado").hide();
		$("#p83_MensajeAvisoAreaPie").hide();
	}else{
		$(gridP83.nameJQuery).jqGrid('setColProp', 'stockDevuelto', {editable:true});
		$(gridP83.nameJQuery).jqGrid('setColProp', 'bulto', {editable:true});

		$(gridP83.nameJQuery).jqGrid('showCol','mensaje');

		$("#p83_btn_guardado").show();
		$("#p83_MensajeAvisoAreaPie").show();
	}

	//Se muestra la columna stockActual o no
	if(devolucion.estadoCab > 1){
		$(gridP83.nameJQuery).jqGrid('hideCol','stockActual');
		$(gridP83.nameJQuery).jqGrid('hideCol','stockTienda');
		$(gridP83.nameJQuery).jqGrid('hideCol','stockDevolver');

		if(devolucion.estadoCab == 3 || devolucion.estadoCab == 4){ // Abono o Incidencia
			$(gridP83.nameJQuery).jqGrid('showCol','cantAbonada');
		} else {
			$(gridP83.nameJQuery).jqGrid('hideCol','cantAbonada');
		}

		//Ponemos nombre a la columna stockDevuelto
		$(gridP83.nameJQuery).jqGrid('setLabel', "stockDevuelto",stockDevueltoTitleNoPrepararMercancia);

		//Insertamos el titulo
		setTitleOnColumnHeader("stockDevuelto",stockDevueltoNoPrepararMercanciaTitle);
	}else{//Preparar mercancia
		$(gridP83.nameJQuery).jqGrid('showCol','stockActual');
		$(gridP83.nameJQuery).jqGrid('showCol','stockTienda');
		$(gridP83.nameJQuery).jqGrid('hideCol','stockDevolver');
		$(gridP83.nameJQuery).jqGrid('hideCol','cantAbonada');

		//Ponemos nombre a la columna stockDevuelto
		$(gridP83.nameJQuery).jqGrid('setLabel', "stockDevuelto",stockDevueltoTitlePrepararMercancia);

		//Insertamos el titulo
		setTitleOnColumnHeader("stockDevuelto",stockDevueltoPrepararMercanciaTitle);
	}

	//BAZAR. Si el valor de coste maximo es distinto de null, es BAZAR. Mostrar columna Coste Unitario
	if (devolucion.costeMaximo != null) {
		$(gridP83.nameJQuery).jqGrid('showCol','costeUnitario');
		$("#p83_AreaCosteMaximo").attr("style", "display:inline");
		$("#p83_AreaSuma").attr("style", "display:inline");

	} else {
		$(gridP83.nameJQuery).jqGrid('hideCol','costeUnitario');
		$("#p83_AreaCosteMaximo").attr("style", "display:none");
		$("#p83_AreaSuma").attr("style", "display:none");
	}

	//Se muestran más columnas o menos.
	if(abonadoOIncidencia){
		//Se enseñan las columnas
		$(gridP83.nameJQuery).jqGrid('showCol','cantAbonada');
		$(gridP83.nameJQuery).jqGrid('showCol','descAbonoError');
	}else{
		//Se ocultan las columnas si no son de tipo abonado o incidencia
		$(gridP83.nameJQuery).jqGrid('hideCol','cantAbonada');
		$(gridP83.nameJQuery).jqGrid('hideCol','descAbonoError');
	}

	// por defecto
	configurarColumnasSegunResultadosP83();

	devolucion.flagHistorico = flgHistorico;

	$("#gbox_gridP83DevolFinCampana #gs_codArticulo").val("");

	//Poner ancho de grid, para que aunque haya más o menos columnas siempre ocupe lo mismo
	$(gridP83.nameJQuery).jqGrid('setGridWidth',950,true);

	var objJson = $.toJSON(devolucion);
	
//	console.log("0-"+objJson);
	
	$.ajax({
		type : 'POST',
		url : './devoluciones/popup/finDeCampana/loadDataGrid.do',
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
					var comboBienCargado = cargarComboboxProveedor(objJson);

					if(comboBienCargado){

						//Insertamos en la cabecera la fecha
						$("#p83_lbl_ValorFecha").html((devolucion.fechaDesdeStr != null ? devolucion.fechaDesdeStr:"") + " / </BR>" + (devolucion.fechaHastaStr != null ? devolucion.fechaHastaStr:""));

						//Insertamos el título
						$("#p83_lbl_ValorTitulo").text(devolucion.titulo1 != null ? devolucion.titulo1:"");

						//Observaciones
						$("#p83_lbl_ValorObservaciones").text(devolucion.descripcion != null ? devolucion.descripcion:"");

						//Abono y recogida
						$("#p83_lbl_ValorAbono").text(devolucion.abono != null ? devolucion.abono:"");
						$("#p83_lbl_ValorRecogida").text(devolucion.recogida != null ? devolucion.recogida:"");

						//Localizador
						$("#p83_lbl_ValorLocalizador").text(devolucion.localizador != null ? devolucion.localizador:"");

						//Coste Maximo
						$("#p83_lbl_ValorCosteMaximo").text(devolucion.costeMaximo != null ? devolucion.costeMaximo:"");
						$("#p83_lbl_ValorCosteMaximoHidden").val(devolucion.costeMaximo != null ? devolucion.costeMaximo:"");

						//RMA
						controlCampoRMA83(devolucion);

						//SUMA
						getSumaCosteFinalP83(devolucion);

						if (devolucionFicha.motivo.substring(0,3) == "Fin"){
							//Título
							$("#p83_popUpDevolFinCampana").dialog({ title:finDeCampana+ " - " + (devolucion.titulo1 != null ? devolucion.titulo1.toUpperCase():"")});
						} else {
							//Título
							$("#p83_popUpDevolFinCampana").dialog({ title:sobrestock+ " - " + (devolucion.titulo1 != null ? devolucion.titulo1.toUpperCase():"")});
						}
						configurarGrid(data);
						generateTooltip(data);
//						rellenadoDeFilas();
						$("#p83_popUpDevolFinCampana").dialog('open');
					}else{
						createAlert(replaceSpecialCharacters(notPosibleToReturnDataP83), "ERROR");
					}
				}else{
					createAlert(replaceSpecialCharacters(notPosibleToReturnDataP83), "ERROR");
				}
			}else{
				createAlert(replaceSpecialCharacters(notPosibleToReturnDataP83), "ERROR");
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});		
}


function controlCampoRMA83(devolucion){	

	if (devolucion.tipoRMA != null &&  devolucion.tipoRMA != "") {

		$("#p83_input_rma").val(devolucion.codRMA != null ? devolucion.codRMA:"");
		$("#p83_input_rmaHidden").val(devolucion.codRMA != null ? devolucion.codRMA:"");

		$("#p83_AreaRma").attr("style", "display:inline");

		if (devolucion.tipoRMA == 'T') {
			$("#p83_input_rma").removeAttr("disabled");
		} else {
			if (devolucion.tipoRMA == 'C') {
				$("#p83_input_rma").attr("disabled", "disabled");
			}
		}

	} else {
		$("#p83_AreaRma").attr("style", "display:none");
	}


}


function getSumaCosteFinalP83(devolucion){	

	var objJson = $.toJSON(devolucion);
	$.ajax({
		type : 'POST',
		url : './devoluciones/popup/finDeCampana/getSumaCosteFinal.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {		

			if (data != null) {
				//Coste MAXIMO
				$("#p83_lbl_ValorSuma").filter_input({regex:'[0-9]'});
				$("#p83_lbl_ValorSuma").val($.formatNumber(data,{format:"0.#",locale:"es"}));
				$("#p83_lbl_ValorSumaSinFormateo").val(data);
			} else {
				$("#p83_lbl_ValorSuma").filter_input({regex:'[0-9]'});
				$("#p83_lbl_ValorSuma").val(0);
				$("#p83_lbl_ValorSumaSinFormateo").val(0);
			}

			if (data > devolucion.costeMaximo) { // si la suma supera a los euros maximos, ambos campos hay que pintarlos en rojo

				$("#p83_lbl_TextoSuma").addClass("p83TextoRojo");
				$("#p83_lbl_ValorSuma").removeAttr( "style" );
				$("#p83_lbl_ValorSuma").attr("style", "color:red; font-size:12px;");
				$("#p83_lbl_TextoCosteMaximo").addClass("p83TextoRojo");
				$("#p83_lbl_ValorCosteMaximo").addClass("p83TextoRojo");

			} else {
				$("#p83_lbl_TextoSuma").removeClass("p83TextoRojo");
				$("#p83_lbl_ValorSuma").removeAttr( "style" );
				$("#p83_lbl_ValorSuma").attr("style", "font-size:12px;");
				$("#p83_lbl_TextoCosteMaximo").removeClass("p83TextoRojo");
				$("#p83_lbl_ValorCosteMaximo").removeClass("p83TextoRojo");
			}

		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		 
}

//Función que sirve para cargar el combobox con nombre PROVEEDOR. Si se carga correctamente, cargamos el grid y abrimos el dialogo con el grid, si no, no.
//Se pone async false, para que no chequee en cargarLineasDeDevolucionFinDeCampana si se ha cargado el combo antes de que se pueda llegar a cargar.
function cargarComboboxProveedor(objJson){
	var comboBienCargado = false;
	var options = "";
	$.ajax({
		type : 'POST',
		url : './devoluciones/popup/finDeCampana/loadComboProveedor.do',
		data : objJson,
		async: false,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {
			if (data != null){
				options = "<option value=' '>TODOS</option>";

				for (i = 0; i < data.length; i++){
					//Se formatea la opción fija de REFERENCIAS PERMANENTES para que aparezca en negrita
					if (i==0){
						options = options + "<option id='p83RefPermanentes' class='p83FormatoRefPermanentes' value='" + data[i].codigo + "'>" + data[i].descripcion + "</option>";
					}else{
						let validado = data[i].descripcion.substr(0,3);
						
						if (validado=="OK "){
							claseNegrita = 'p83FormatoRefPermanentes';
						}else{
							claseNegrita = '';
						}
						options = options + "<option id='"+ data[i].codigo + "' class='" + claseNegrita + "' value='" + data[i].codigo + "'>" + data[i].descripcion + "</option>";
					}
					ultimoBultoIntroducido[data[i].codigo] = "";//Se inicializan todos los bultos de los proveedores a ""
				}
				comboValue = " ";
				$("select#p83_cmb_proveedor").html(options);
				
				$("#p83_cmb_proveedor").combobox('autocomplete',"TODOS");
				$("#p83_cmb_proveedor").combobox('comboautocomplete',"null");
				$("#p83RefPermanentes").html("<span class='p83FormatoRefPermanentes'>" + $("#p83RefPermanentes").text() +"</span>");  
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

	$("#p83_cmb_proveedor").combobox({
		selected: function(event, ui) {
			if ( ui.item.value!="" && ui.item.value!="null" ) {
				//Si el valor seleccionado difiere del valor seleccionado anterior
				//buscamos, si es el mismo no.
				if(ui.item.value != comboValue){							
					comboValue = $("#p83_cmb_proveedor").val();
					if (comboValue !=null){
						$("#gbox_gridP83DevolFinCampana #gs_codArticulo").val("");
						$('#gridP83DevolFinCampana').setGridParam({page:1});
						reloadData_gridP83DevolFinCampana();	
					}
				}
			}
		}  ,
	}); 
	return comboBienCargado;
}

function reset_p83(resetear){
	//Iniciar los arrays
	seleccionados = new Array();
	listadoModificados = new Array();
	modificados = new Array();

	//Iniciar el grid y su cantidad de elementos a mostrar
	$(gridP83.nameJQuery).jqGrid('clearGridData');
	$(gridP83.nameJQuery).jqGrid('setGridParam', { rowNum: 10 });
	$("#p83_popUpDevolFinCampana .ui-pg-selbox").val(10);

	//Quitamos las flechas de ordenación del grid de la columna reordenada para que no se mantengan al abrid un nuevo grid.
	$(gridP83.nameJQuery + "_" + gridP83.getSortIndex() + " [sort='asc']").addClass("ui-state-disabled");
	$(gridP83.nameJQuery + "_" + gridP83.getSortIndex() + " [sort='desc']").addClass("ui-state-disabled");

	//Reseteamos la ordenación del grid
	$(gridP83.nameJQuery).jqGrid('setGridParam', {sortname:'', sortorder: 'asc'});

	if (resetear==1){
		//Reseteamos el valor del último bulto introducido
		ultimoBultoIntroducido = {};
	}
}

//Mediante esta función se obtienen los cambios de las filas cambiadas en una lista. Luego esta lista se utiliza en el controlador
//para que al paginar que se guarden los estados de modificados, guardados, etc.
function obtenerListadoModificados(){

	//A partir del array de seleccionados obtenemos el listado de campos modificados a enviar al controlador.
	var registroListadoModificado = {};
	var valorStockDevuelto = "";
	var valorBulto = "";
	var valorBandejas = "";
	var valorCodError = "";
	var valorCodArticulo= "";
	for (i = 0; i < seleccionados.length; i++){
		if (seleccionados[i] != null && seleccionados[i] != ''){
			
			//Por cada elemento seleccionado, tenemos que crear el listado de modificados.
			registroListadoModificado = {};

			//Se obtiene el valor de stock devuelto y de bulto y de kgs.
			valorStockDevuelto = $("#"+ seleccionados[i] + "_stockDevuelto").val() ? $("#"+ seleccionados[i] + "_stockDevuelto").val().replace(',','.') : "";
			valorBulto = $("#"+ seleccionados[i] + "_bulto").val() ? $("#"+ seleccionados[i] + "_bulto").val().replace(',','.') : "";
			valorBandejas = $("#"+ seleccionados[i] + "_stockDevueltoBandejas_tmp").val() ? $("#"+ seleccionados[i] + "_stockDevueltoBandejas_tmp").val().replace(',','.') : "";

			//Se obtiene el codigo error para guardar el estado modificado, error o guardado en la tabla temporal
			//indice = $("#"+ seleccionados[i] + "_stockDevueltoBulto_indice").val();
			valorCodError = $("#"+ seleccionados[i] + "_stockDevueltoBulto_codError_orig").val() ? $("#"+ seleccionados[i] + "_stockDevueltoBulto_codError_orig").val() : "";

			//Se obtiene el codigo de articulo para saber qué linea actualizar en la tabla temporal.
			valorCodArticulo = jQuery(gridP83.nameJQuery).jqGrid('getCell',seleccionados[i],'codArticulo');

			//Se rellena el objeto con el error, stock y bulto. Además se inserta el código de artículo para saber que linea de devolución
			//hay que actualizar.

			//registroListadoModificado.indice =  indice;
			registroListadoModificado.codError = valorCodError;

			registroListadoModificado.stockDevuelto =  valorStockDevuelto;
			registroListadoModificado.bulto = valorBulto;

			registroListadoModificado.codArticulo = valorCodArticulo;
			registroListadoModificado.stockDevueltoBandejas = valorBandejas;

			listadoModificados.push(registroListadoModificado)
		}	
	}

	//Reseteamos el array de los seleccionados.
	seleccionados = new Array();
}

//Función que sirve para paginar o reordenar los elementos del grid.
function reloadData_gridP83DevolFinCampana() {

	//Obtenemos las filas modificadas
	obtenerListadoModificados();

	var devolucionEditada = new Devolucion(devolucionFicha.centro,flgHistorico,devolucionFicha.titulo1,devolucionFicha.flgCodArticulo,
			devolucionFicha.estadoCab,devolucionFicha.localizador,devolucionFicha.devolucion,devolucionFicha.fechaDesde,devolucionFicha.fechaHasta,
			devolucionFicha.flgRecogida,devolucionFicha.abono,devolucionFicha.recogida,devolucionFicha.codPlataforma,devolucionFicha.descripcion,
			devolucionFicha.motivo,devolucionFicha.titulo2,devolucionFicha.fechaPrecio,devolucionFicha.codError,devolucionFicha.descError,
			devolucionFicha.devLineas,listadoModificados,comboValue,null);

	//Borrar el listado de modificados
	listadoModificados = new Array();

	var objJson = $.toJSON(devolucionEditada);
//	console.log("1-"+objJson);

	$.ajax({
		type : 'POST',			
		url : './devoluciones/popup/finDeCampana/loadDataGrid.do?page='+gridP83.getActualPage()+'&max='+gridP83.getRowNumPerPage()+'&index='+gridP83.getSortIndex()+'&sortorder='+gridP83.getSortOrder()+'&recarga=S',
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		data : objJson,
		success : function(data) {	
			if(data != null){
				if(data.rows != null && data.rows.length > 0){
					configurarGrid(data);
					$("#p83_cmb_proveedor").next().focus();
					generateTooltip(data);
//					rellenadoDeFilas();
				}else{
					/*createAlert(replaceSpecialCharacters(notPosibleToReturnDataP83), "ERROR");*/

					//Rellenamos el grid vacío
					configurarGrid(data);

					//Rellenamos las líneas vacías
					//rellenadoDeFilasNoEncontradas();

					//Se pone esta fila porque si paginas y da error de paginación, el grid cambia el número de página internamente pero no externamente (la que se visualiza), y si luego
					//pagina bien, no va a la siguiente página, si no a la que tiene internamente. 
					var valorCajaGrid = $("#p83_popUpDevolFinCampana .ui-pg-input").val();
					$(gridP83.nameJQuery).setGridParam({page:valorCajaGrid});
				}
			}else{
				createAlert(replaceSpecialCharacters(notPosibleToReturnDataP84), "ERROR");

				//Se pone esta fila porque si paginas y da error de paginación, el grid cambia el número de página internamente pero no externamente (la que se visualiza), y si luego
				//pagina bien, no va a la siguiente página, si no a la que tiene internamente. 
				var valorCajaGrid = $("#p83_popUpDevolFinCampana .ui-pg-input").val();
				$(gridP83.nameJQuery).setGridParam({page:valorCajaGrid});
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});		
}


//Función que sirve para paginar o reordenar los elementos del grid.
function reloadData_FiltroP83(filtroReferencia) {

	var devolucionEditada = new Devolucion(devolucionFicha.centro,flgHistorico,devolucionFicha.titulo1,devolucionFicha.flgCodArticulo,
			devolucionFicha.estadoCab,devolucionFicha.localizador,devolucionFicha.devolucion,devolucionFicha.fechaDesde,devolucionFicha.fechaHasta,
			devolucionFicha.flgRecogida,devolucionFicha.abono,devolucionFicha.recogida,devolucionFicha.codPlataforma,devolucionFicha.descripcion,
			devolucionFicha.motivo,devolucionFicha.titulo2,devolucionFicha.fechaPrecio,devolucionFicha.codError,devolucionFicha.descError,
			devolucionFicha.devLineas,listadoModificados,comboValue,null);

	//Borrar el listado de modificados
	listadoModificados = new Array();

	var objJson = $.toJSON(devolucionEditada);

	$.ajax({
		type : 'POST',			
//		url : './devoluciones/popup/creadaCentro/loadDataGrid.do?page='+gridP83.getActualPage()+'&max='+gridP83.getRowNumPerPage()+'&index='+gridP83.getSortIndex()+'&sortorder='+gridP83.getSortOrder()+'&recarga=S&filtroReferencia='+filtroReferencia,
		url : './devoluciones/popup/finDeCampana/loadDataGridRecarga.do?page='+gridP83.getActualPage()+'&max='+gridP83.getRowNumPerPage()+'&index='+gridP83.getSortIndex()+'&sortorder='+gridP83.getSortOrder()+'&recarga=S&filtroReferencia='+filtroReferencia,
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		data : objJson,
		success : function(data) {	
			if(data != null){
				if(data.rows != null && data.rows.length > 0){
					configurarGrid(data);
					//$("#p83_cmb_proveedor").next().focus();
					$("#gbox_gridP83DevolFinCampana #gs_codArticulo").focus();
					generateTooltip(data);

				}else{
					/*createAlert(replaceSpecialCharacters(notPosibleToReturnDataP87), "ERROR");*/

					//Rellenamos el grid vacío
					configurarGrid(data);

					//Se pone esta fila porque si paginas y da error de paginación, el grid cambia el número de página internamente pero no externamente (la que se visualiza), y si luego
					//pagina bien, no va a la siguiente página, si no a la que tiene internamente. 
					var valorCajaGrid = $("#p83_popUpDevolCreadaCentro .ui-pg-input").val();
					$(gridP83.nameJQuery).setGridParam({page:valorCajaGrid});


				}
			}else{
				createAlert(replaceSpecialCharacters(notPosibleToReturnDataP83), "ERROR");

				//Se pone esta fila porque si paginas y da error de paginación, el grid cambia el número de página internamente pero no externamente (la que se visualiza), y si luego
				//pagina bien, no va a la siguiente página, si no a la que tiene internamente. 
				var valorCajaGrid = $("#p83_popUpDevolCreadaCentro .ui-pg-input").val();
				$(gridP83.nameJQuery).setGridParam({page:valorCajaGrid});
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});		
}

//Función que actualiza las líneas de devolución de una devolución.
function actualizarDevolucionLineas(){

	//Obtener el campo RMA
	var codRMA = $("#p83_input_rma").val();
	var codRMAInicial = $("#p83_input_rmaHidden").val();

	codRMAModificado83 = false;
	if (codRMA != codRMAInicial) {
		codRMAModificado83 = true;
	}

	//Si se ha modificado algún registro de cualquier página, se guarda. Si no se ha modificado ningún registro muestra error.
	if ((modificados.length > 0) || codRMAModificado83 ){
		//Obtenemos las filas modificadas
		obtenerListadoModificados();

		var devolucionEditada = new Devolucion(devolucionFicha.centro,devolucionFicha.flagHistorico,devolucionFicha.titulo1,devolucionFicha.flgCodArticulo,
				devolucionFicha.estadoCab,devolucionFicha.localizador,devolucionFicha.devolucion,devolucionFicha.fechaDesde,devolucionFicha.fechaHasta,
				devolucionFicha.flgRecogida,devolucionFicha.abono,devolucionFicha.recogida,devolucionFicha.codPlataforma,devolucionFicha.descripcion,
				devolucionFicha.motivo,devolucionFicha.titulo2,devolucionFicha.fechaPrecio,devolucionFicha.codError,devolucionFicha.descError,
				devolucionFicha.devLineas,listadoModificados,comboValue,null);

		var objJson = $.toJSON(devolucionEditada);

		$.ajax({
			type : 'POST',			
			url : './devoluciones/popup/finDeCampana/saveDataGrid.do?page='+gridP83.getActualPage()+'&max='+gridP83.getRowNumPerPage()+'&index='+gridP83.getSortIndex()+'&sortorder='+gridP83.getSortOrder() +'&codRMA='+codRMA,
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			data : objJson,
			success : function(data) {	
				if (data != null){
					if (data.codErrorPLSQL == 0){

						//Se actualiza el valor RMA en la lista de devoluciones
						devolucionFicha.codRMA = codRMA;
						$("#p83_input_rmaHidden").val(codRMA);

						//Control de mensaje de actualización de cabecera
						var mensajeActualizacionCabecera = "";
						var mensajeActualizacionCabeceraY = ""; //Utilizado para componer el guardado de cabecera con error en lineas
						if (codRMAModificado83) {
							mensajeActualizacionCabecera = cabeceraActualizada;
							mensajeActualizacionCabeceraY = cabeceraActualizada + "<br/>";
						}

						//Restauramos el valor de modificados.
						modificados = new Array();

						//Borrar el listado de modificados
						listadoModificados = new Array();

						configurarGrid(data.datos);

						// ***********************************************************************************/
						// Después de guardar los cambios, volver a cargar la lista del combo de proveedores
						// por si ha cambiado el estado de proveedor completado. "OK <CodProveedor>".
						let provSeleccionado = $('#p83_cmb_proveedor').val();
						
						recargaCmbProvSelec(objJson, provSeleccionado);
						
						// ***********************************************************************************/

						//Miramos que el guardado no se haya hecho después de que un resulado de búsqueda de combos sea vacío
						if (data.datos.rows != null && data.datos.rows.length > 0){
							generateTooltip(data.datos);
							//rellenadoDeFilas();
						}else{
							//rellenadoDeFilasNoEncontradas();
						}
						
						if (data.countGuardados == 0){
							if (data.countError == 0){
								createAlert(mensajeActualizacionCabecera, "INFO");
							}else if (data.countError == 1){										
								createAlert(replaceSpecialCharacters(mensajeActualizacionCabeceraY + data.countGuardados + lineasActualizadas + data.countError + lineaErronea), "ERROR");		
							}else{
								createAlert(replaceSpecialCharacters(mensajeActualizacionCabeceraY + data.countGuardados + lineasActualizadas + data.countError + lineasErroneas), "ERROR");						
							}
						}else{
							if (data.countGuardados == 1){
								if (data.countError == 0){
									createAlert(guardadoCorrectamente, "INFO");
								}else{
									if (data.countError == 1){											
										createAlert(replaceSpecialCharacters(mensajeActualizacionCabeceraY + data.countGuardados + lineaActualizada + data.countError + lineaErronea), "INFO");
									}else{
										createAlert(replaceSpecialCharacters(mensajeActualizacionCabeceraY + data.countGuardados + lineaActualizada + data.countError + lineasErroneas), "INFO");
									}
								}
							}else{
								if (data.countError == 0){
									createAlert(guardadoCorrectamente, "INFO");
								}else{
									if (data.countError == 1){											
										createAlert(replaceSpecialCharacters(mensajeActualizacionCabeceraY + data.countGuardados + lineasActualizadas + data.countError + lineaErronea), "INFO");
									}else{
										createAlert(replaceSpecialCharacters(mensajeActualizacionCabeceraY + data.countGuardados + lineasActualizadas + data.countError + lineasErroneas), "INFO");
									}
								}
							}
						}

						//SUMA
						getSumaCosteFinalP83(devolucionEditada);
						
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

function descAbonoErrorFormatter(cellValue, opts, rData) {
	if(cellValue == null || cellValue.toString() == ""){
		cellValue = "";
		return cellValue;
	}else{
		descripcion = '<span id="p83_descAbonoErrorFormatter">' + cellValue + '</span>';
		return descripcion;
	}
}

function stockFormatter(cellValue, opts, rData) {
	//Si el campo es stockdevuelto y la cantidadmaxima es 0, no queremos que se pueda editar.
	/*if(opts.colModel.name == "stockDevuelto"){
		if(rData.cantidadMaximaLin == 0){
			opts.colModel.editable = false;
		}
	}*/

	if(cellValue == null || cellValue.toString() == ""){
		if(opts.colModel.name == "stockActual" || opts.colModel.name == "stockDevolver"){
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

function bultoFormatter(cellValue, opts, rData) {
	//Si la cantidadmaxima es 0, no queremos que se pueda editar.
	/*if(rData.cantidadMaximaLin == 0){
		//opts.colModel.editable = false;
		$(gridP83.nameJQuery).jqGrid('setCell', opts.rowId, 'ColName', '', 'not-editable-cell');
	}*/

	if(cellValue == null || cellValue.toString() == ""){
		cellValue = "";
		return cellValue;
	}else{	
		if(rData.variosBultos){
			return rData.bultoStr;
		}else{
			return $.formatNumber(cellValue,{format:"0.###",locale:"es"});
		}
	}
}

function cantidadMaximaLinFormatter(cellValue, opts, rData) {
	if(cellValue == null || cellValue.toString() == ""){
		cellValue = "";
		return cellValue;
	}else{		
		return $.formatNumber(cellValue,{format:"0.###",locale:"es"});
	}
}

function cantAbonadaFormatter(cellValue, opts, rData) {
	if(cellValue == null || cellValue.toString() == ""){
		cellValue = "";
		return cellValue;
	}else{
		return $.formatNumber(cellValue,{format:"0.###",locale:"es"});
	}
}

function costeUnitarioFormatter(cellValue, opts, rData) {
	if(cellValue == null || cellValue.toString() == ""){
		cellValue = "";
		return cellValue;
	}else{
		return $.formatNumber(cellValue,{format:"0.###",locale:"es"});
	}
}

//Conseguimos el número de filas mostradas y las coloreamos si el stockActual es 0
function p83ControlesPantalla(){
	//Nos recorremos los registros existentes en pantalla.
	var rowsInPage = jQuery(gridP83.nameJQuery).jqGrid('getGridParam', 'reccount');
	for (var i = 0; i < rowsInPage; i++){
		//Controlamos las filas que vengan realizadas por Central o modificable a N del WS para pintarlas de Rojo.
		p83ColorearStockActual(i+1);
	}		
}

//Coloreamos las filas que tienen stockActual 0,000. Si no se ha modificado la columna de Cantidad a Devolver Centro, se colorea en rojo y si no en negro.
//Además si el estado de las lineas es abonado o incidencia se colorean las filas en verde o rojo. En estado abonado todas iran en verde, en incidencia algunas
//en rojo y otras en verde, según el estado de cada linea
function p83ColorearStockActual(filaAColorear){
	if (devolucionFicha.estadoCab==1 || devolucionFicha.estadoCab==2){
		var filaActualStockActualVal = jQuery(gridP83.nameJQuery).jqGrid('getCell',filaAColorear,'stockActual');

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
				$(gridP83.nameJQuery).find("#"+(filaAColorear)).find("td").addClass("p83_columnaResaltada").removeClass("p83_columnaResaltadaNegro");
			}else{
				$(gridP83.nameJQuery).find("#"+(filaAColorear)).find("td").addClass("p83_columnaResaltadaNegro").removeClass("p83_columnaResaltada");
			}
		}
	}
	else if (devolucionFicha.estadoCab==3 || devolucionFicha.estadoCab==4){
		//Según el estado de la linea, se dibuja en verde o rojo.
		var estadoLin = $("#"+filaAColorear+"_estadoLin").val();
		if(estadoLin == 3){
			$(gridP83.nameJQuery).find("#"+(filaAColorear)).find("td").addClass("p83_columnaResaltadaVerde").removeClass("p83_columnaResaltadaNegro").removeClass("p83_columnaResaltada");
		}else if(estadoLin == 4){
			$(gridP83.nameJQuery).find("#"+(filaAColorear)).find("td").addClass("p83_columnaResaltada").removeClass("p83_columnaResaltadaNegro").removeClass("p83_columnaResaltadaVerde");
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
function configurarColumnasSegunResultadosP83(data){
	var seccionValue = (!data || !data.rows || !data.rows[0]) ? null : data.rows[0].seccion;
	// son textil, secciones del 15 al 18
	var isReferenciaTextil = /^001[5-8]/.test(seccionValue);

	// ocultar o mostrar columnas
	var showHideColSoloTextil = isReferenciaTextil ? 'showCol' : 'hideCol';
	$(gridP83.nameJQuery).jqGrid(showHideColSoloTextil,'modelo');
	$(gridP83.nameJQuery).jqGrid(showHideColSoloTextil,'descrTalla');
	$(gridP83.nameJQuery).jqGrid(showHideColSoloTextil,'descrColor');
	$(gridP83.nameJQuery).jqGrid(showHideColSoloTextil,'modeloProveedor');

	// que ocupe lo mismo independiente del numero de columnas
	$(gridP83.nameJQuery).jqGrid('setGridWidth',950,true);
}

//Función que inserta eventos focusout, formateadores de expresiones regulares, ordenaciones de columnas, colores y datos al grid.
function configurarGrid(data){

	let hideLineaVertical=$("#p83_27_pc_devoluciones_procedimiento").val();
	
	//Se rellena el grid
	$(gridP83.nameJQuery)[0].addJSONData(data);
	gridP83.actualPage = data.page;
	gridP83.localData = data;

	// carga del primer bulto abierto a cada uno de los proveedores.
	cargarBultoAbiertoPorProveedor(ultimoBultoIntroducido);
	
	configurarColumnasSegunResultadosP83(data);

	// comprobar que se trata de una devolución de "Preparar Mercancia"
	// y el centro está parametrizado para "27_PC_DEVOLUCIONES_PROCEDIMIENTO".
	if (esPrepararMercancia==1 && hideLineaVertical=="true"){
		// hacer trasparente la línea vertical derecha.
		$("#gridP83DevolFinCampana_bulto").attr("style","border-right-color:transparent;");
		$(gridP83.nameJQuery).jqGrid('showCol','anadirBulto');
		// comprobar si el centro está parametrizado.
		if ( $("#p83_27_pc_devoluciones_procedimiento").val() == "true" ){
			$("#gbox_gridP83DevolFinCampana #gs_bulto").closest("th").css("border-right-color","transparent");
		}
		$("#gbox_gridP83DevolFinCampana #gs_anadirBulto").css("display", "none");
	}else{
		$("#gridP83DevolFinCampana_bulto").attr("style","border-right-color:ligthgray;");
		$(gridP83.nameJQuery).jqGrid('hideCol','anadirBulto');
	}

	//Por cada fila, formatea los campos editables y les coloca campos con focusout.
	var ids = $(gridP83.nameJQuery).jqGrid('getDataIDs'), l = ids.length;
	for (var i = 0; i < l; i++) {
		$(gridP83.nameJQuery).jqGrid('editRow', ids[i], false);
		
		if(!data.rows[i].variosBultos){
			////Ponemos que solo puedan insertarse valores enteros si no está en varios bultos
			$("#"+ids[i]+"_bulto").filter_input({regex:'[0-9]'});	
		}
		
		// comprobar que se trata de una devolución de "Preparar Mercancia"
		// y el centro está parametrizado para "27_PC_DEVOLUCIONES_PROCEDIMIENTO".
		if (esPrepararMercancia==1 && hideLineaVertical == "true"){
			// hacer trasparente la línea vertical derecha.
			$("#"+ ids[i]).find('td[aria-describedby="gridP83DevolFinCampana_bulto"]').attr("style","border-right-color:transparent;");
//			$("#p83_MensajeAvisoAreaCombos").show();

			// Desactivar el botón "+" para los casos en los que el peso es variable.
			let flgBandejas = $("#"+ids[i]+"_flgBandejas").val();
			let flgPesoVariable = $("#"+ids[i]+"_flgPesoVariable").val();
			
			if (flgBandejas == "S" && flgPesoVariable == "S"){
				$("#"+ids[i]+"_p83_div_anadirBultos").hide();
			}
		}else{
			$("#gridP83DevolFinCampana_bulto").attr("style","border-right-color:ligthgray;");
//			$("#p83_MensajeAvisoAreaCombos").hide();
		}

		let cellEstadoCerradoValue=$("#"+ ids[i]).find('td[aria-describedby="gridP83DevolFinCampana_bultoEstadoCerrado"]').text();
		let esEstadoCerrado="N";
		
		// si dentro de la cadena Estado Cerrado existe el carácter "*"
		// , nos indica que la referencia está en más de un bulto.
		//
		// Nos podemos encontrar con la siguiente cadena "1-N*2-S*3-N"
		// Significa lo siguiente,
		//
		// Bulto 1 NO CERRADO.
		// Bulto 2 SI CERRADO.
		// Bulto 3 NO CERRADO.
		let bultoSeparado="";
		// compruebo si la referencia está en varios bultos.
		if (cellEstadoCerradoValue.indexOf("*")!== -1){
			let bultoEstado = cellEstadoCerradoValue.split("*");
			for (let bultoE of bultoEstado){
				bultoSeparado = bultoE.split("-");
				// compruebo si alguno de los bultos está CERRADO.
				if (bultoSeparado[1]=="S"){
					esEstadoCerrado=bultoSeparado[1];
				}
			}
		}else{
			bultoSeparado = cellEstadoCerradoValue.split("-");

			if (bultoSeparado[1]=="S"){
				esEstadoCerrado=bultoSeparado[1];
			}
		}
		
		// si Bulto Cerrado hacemos no editables los campos "Cantidad a Devolver" y "Bulto".
		nuevoI=i+1;
		if (esEstadoCerrado=='S'){
			$("#"+nuevoI+"_stockDevuelto").css("background", "lightgray");
			$("#"+nuevoI+"_stockDevuelto").attr('readonly', true);
			$("#"+nuevoI+"_bulto").css("background", "lightgray");
			$("#"+nuevoI+"_bulto").attr('readonly', true);
		}else{
			$("#"+nuevoI+"_stockDevuelto").css("background", "white");
			$("#"+nuevoI+"_stockDevuelto").attr('readonly', false);
			$("#"+nuevoI+"_bulto").css("background", "white");
			$("#"+nuevoI+"_bulto").attr('readonly', false);
		}
		
		//Si cantidad maxima es 0, ponemos los campos como no editables.
		var valorCantMax = $("#"+ ids[i]).find('td[aria-describedby="gridP83DevolFinCampana_cantidadMaximaLin"]').text();	
		if(valorCantMax == "0"){
			jQuery(gridP83.nameJQuery).jqGrid('setCell',ids[i],'bulto',data.rows[i].bulto);
			jQuery(gridP83.nameJQuery).jqGrid('setCell',ids[i],'stockDevuelto',data.rows[i].stockDevuelto);
		}

		////Ponemos que solo puedan insertarse valores enteros
		$("#"+ids[i]+"_bulto").filter_input({regex:'[0-9]'});		

		//Si se pierde el foco, se ejecutará la función que muestre el disquete, error, etc.
		$("#"+ids[i]+"_bulto").focusout(function() {
			var fila = this.id.split("_")[0];
			var variosBultos = $("#"+fila+"_variosBultos").val();
			if(variosBultos=='false'){
				validacionBulto(this.id, 'S');
			}
		});

		//Ponemos que solo puedan insertarse valores decimales
		$("#"+ids[i]+"_stockDevuelto").filter_input({regex:'[0-9,]'});	

		//Si se pierde el foco del campo se ejecuta la función que se utilizará para mostrar
		//un error, el disquete,etc. Se realiza cuando no existe el campo de bandejas que abre
		//el popup de bandejas y kgs.
		//if($("#"+ids[i]+"_flgBandejas").val() != "S"){
			$("#"+ids[i]+"_stockDevuelto").focusout(function(e) {
				validacionStockDevuelto(this.id, 'S');							
			});
		//}

		$("#"+ids[i]+"_stockDevuelto").change(function() {
			controlChangecolumnaStockDevuelto(this.id,gridP83.nameJQuery);
		});

		$("#"+ids[i]+"_bulto").focus(function() {
			var fila = this.id.split("_")[0];
			var variosBultos = $("#"+fila+"_variosBultos").val();
			if(variosBultos=='false'){
				controlFocuscolumnaBulto(this.id);
				//Miramos el campo bandejas de la fila y si es S al pasar al bulto, cierra el popup.
				if($("#"+this.id.split("_")[0]+"_flgBandejas").val() == "S"){
					$("#p88_popUpCantidadDevueltaCentroKgs").dialog("close");
				}
			}else{
				$("#"+fila+"_bulto").attr('readonly', true);
				openPopUpVariosBultosFinCampana(fila,gridP83.name,"P83");
			}
		});

		if (esPrepararMercancia==1){
			$("#"+ids[i]+"_p83_btn_anadirBultos").click(function() {
				var fila = this.id.split("_")[0];
				openPopUpVariosBultosFinCampana(fila,gridP83.name,"P83");
			});
		}
		
		$("#"+ids[i]+"_bulto").change(function() {
			controlChangecolumnaBulto(this.id);
		});
	}
	

	let hayRefsPdtes='NO';
	if (data && data.rows && data.rows.length > 0 && typeof data.rows[0].hayRefsPdtes !== 'undefined') {
		hayRefsPdtes = data.rows[0].hayRefsPdtes;
	}

	// comprobar que se trata de una devolución de "Preparar Mercancia"
	// y el centro está parametrizado para "27_PC_DEVOLUCIONES_PROCEDIMIENTO".
	if (esPrepararMercancia==1 && hideLineaVertical=="true"){
		if (hayRefsPdtes=="SI"){
			$("#p83_MensajeAvisoAreaCombos").show();
		}else{
			$("#p83_MensajeAvisoAreaCombos").hide();
		}
	}else{
		$("#p83_MensajeAvisoAreaCombos").hide();
	}

	
	//Añadimos a todos los elementos de tipo "[0-9]+_stockDevuelto" (inputs de stockdevuelto) los eventos
	//click y select, para que cuando el usuario clique o se posicione en el input, se abra el popup de kilos
	//y bandejas o lo cierre. Antes, este control estaba hecho con focus únicamente y dentro del bucle de
	//arriba, pero así como en firefox funcionaba bien, en internet explorer no. Se podría haber metido en el
	//bucle con select, pero es indiferente donde situarlo y así se ve más claro. El problema de internet explorer
	//es que por una razón que no comprendo, después de dibujar todas las filas al paginar o hacer una ordenación o
	//guardar, tras hacer todas las operaciones correctamente realizaba un focus de todos los input de stockdevuelto
	//abriendo el popup de kilos y bandejas. No comprendo por qué realizaba dichos eventos en pila, pero creo que
	//era por como construye los elementos en el DOM, ya que en FIREFOX funcionaba perfectamente. Con el evento select
	//se soluciona.
	$('#gridP83DevolFinCampana input[id$=_stockDevuelto]').select(function(){
		var fila = this.id.split("_")[0];
		var flgBandejas = $("#"+fila+"_flgBandejas").val();
		var flgPesoVariable = $("#"+fila+"_flgPesoVariable").val(); // MISUMI-259
		var variosBultos = $("#"+fila+"_variosBultos").val(); // MISUMI-527
		if(flgBandejas == "S" && flgPesoVariable == "S"){
			$("#p116_popUpVariosBultos").dialog("close");
			openPopUpCantidadDevueltaCentroYKgs(fila,gridP83.name,"P83");
		}else{
			if(variosBultos=='true'){
				$("#"+fila+"_stockDevuelto").attr('readonly', true);
				openPopUpVariosBultosFinCampana(fila,gridP83.name,"P83");
			}else{
				$("#p88_popUpCantidadDevueltaCentroKgs").dialog("close");
				$("#p116_popUpVariosBultos").dialog("close");
			}
			
		}
	});
	$('#gridP83DevolFinCampana input[id$=_stockDevuelto]').click(function(){
		var fila = this.id.split("_")[0];
		var flgBandejas = $("#"+fila+"_flgBandejas").val();
		var flgPesoVariable = $("#"+fila+"_flgPesoVariable").val(); // MISUMI-259
		var variosBultos = $("#"+fila+"_variosBultos").val(); // MISUMI-527
		//Si flgBandejas = 'S' y el flgPesoVariable = 'S', hay que tener en cuenta los kilos y las bandejas.Si no, 
		//se cierra el posible popup de kilos y bandejas.
		if(flgBandejas == "S" && flgPesoVariable == "S"){
			$("#"+fila+"_stockDevuelto").attr('readonly', true);
			openPopUpCantidadDevueltaCentroYKgs(fila,gridP83.name,"P83");
		}else{
			if(variosBultos=='true'){
				$("#"+fila+"_stockDevuelto").attr('readonly', true);
				openPopUpVariosBultosFinCampana(fila,gridP83.name,"P83");
			}else{
				$("#p88_popUpCantidadDevueltaCentroKgs").dialog("close");
			}
			
		}
	});

	//Carga las ordenaciones de las columnas.
	cargarOrdenacionesColumnas();

	//Colorea de rojo las líneas que tengan el stockActual en 0
	p83ControlesPantalla();	
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/********************************************************** ORDENACIONES POR COLUMNA *********************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

function cargarOrdenacionesColumnas(){

	//Cuando pulse la columna para ordenar por icono tenemos que llamar manualmente al reload para que reordene,
	//ya que al tener el jsp campos editables no funciona de la manera convencional.
	$(gridP83.nameJQuery+"_codArticulo").unbind('click');
	$(gridP83.nameJQuery+"_codArticulo").bind("click", function(e) {
		//Antes de introducir esta validación, cuando se clicaba una reordenación de columna, hasta ahora ejecutaba primero este evento click y más tarde el focusout de la columna editable 
		//stockDevuelto (Cantidad a devolver centro). Esto creaba situaciones, en el que en caso de introducir un campo erroneo en stockDevuelto (con más de una coma,
		//más de 15 enteros o más de 3 decimales), no ejecutaba el focusout que se encarga de mostrar que ha habido un error de edición en ese campo y reordenaba directamente
		//la columna, siendo este comportamiento erroneo. Además creaba otro tipo de errores que se pueden ver en la aplicación como: 1. Editas un campo y tabulas, 2. Vuelves al
		//campo editado, 3. Introduces un error en el campo modificado, 4. Reordenas. Se quedaba la pantalla bloqueada!

		//Para realizar la validación cada vez que reordenamos, basta con introducir en este evento la validación del campo stockDevuelto y como id document.activeElement.id (Esto,
		//se encarga de devolver el id del foco actual). Como se ejecuta antes el evento click que el evento focusout, el foco estará en stockDevuelto y podremos realizar la validación correctamente.
		//En el caso de pasarla, se reordenará. En caso contrario devolverá error.

		//En el caso de que no estemos en ningún campo stockDevuelto y al clicar ordenar, se reordenará siempre- De esto se encarga la función validacionStockDevuelto, que controla que el id sea de tipo stockDevuelto,
		//en caso contrario, la reordenación se realiza siempre.
		if(validacionStockDevuelto(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionStockDevuelto(document.activeElement.id, 'S');

			actualizarSortOrder('codArticulo');

			$(gridP83.nameJQuery).setGridParam({sortname:'codArticulo'});
			$(gridP83.nameJQuery).setGridParam({page:1});
			reloadData_gridP83DevolFinCampana();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});

	$(gridP83.nameJQuery+"_denominacion").unbind('click');
	$(gridP83.nameJQuery+"_denominacion").bind("click", function(e) {
		if(validacionStockDevuelto(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionStockDevuelto(document.activeElement.id, 'S');

			actualizarSortOrder('denominacion');

			$(gridP83.nameJQuery).setGridParam({sortname:'denominacion'});
			$(gridP83.nameJQuery).setGridParam({page:1});
			reloadData_gridP83DevolFinCampana();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});

	$(gridP83.nameJQuery+"_modelo").unbind('click');
	$(gridP83.nameJQuery+"_modelo").bind("click", function(e) {
		if(validacionStockDevuelto(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionStockDevuelto(document.activeElement.id, 'S');

			actualizarSortOrder('modelo');

			$(gridP83.nameJQuery).setGridParam({sortname:'modelo'});
			$(gridP83.nameJQuery).setGridParam({page:1});
			reloadData_gridP83DevolFinCampana();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});

	$(gridP83.nameJQuery+"_descrTalla").unbind('click');
	$(gridP83.nameJQuery+"_descrTalla").bind("click", function(e) {
		if(validacionStockDevuelto(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionStockDevuelto(document.activeElement.id, 'S');

			actualizarSortOrder('descrTalla');

			$(gridP83.nameJQuery).setGridParam({sortname:'descrTalla'});
			$(gridP83.nameJQuery).setGridParam({page:1});
			reloadData_gridP83DevolFinCampana();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});

	$(gridP83.nameJQuery+"_descrColor").unbind('click');
	$(gridP83.nameJQuery+"_descrColor").bind("click", function(e) {
		if(validacionStockDevuelto(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionStockDevuelto(document.activeElement.id, 'S');

			actualizarSortOrder('descrColor');

			$(gridP83.nameJQuery).setGridParam({sortname:'descrColor'});
			$(gridP83.nameJQuery).setGridParam({page:1});
			reloadData_gridP83DevolFinCampana();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});

	$(gridP83.nameJQuery+"_modeloProveedor").unbind('click');
	$(gridP83.nameJQuery+"_modeloProveedor").bind("click", function(e) {
		if(validacionStockDevuelto(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionStockDevuelto(document.activeElement.id, 'S');

			actualizarSortOrder('modeloProveedor');

			$(gridP83.nameJQuery).setGridParam({sortname:'modeloProveedor'});
			$(gridP83.nameJQuery).setGridParam({page:1});
			reloadData_gridP83DevolFinCampana();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});

	$(gridP83.nameJQuery+"_costeUnitario").unbind('click');
	$(gridP83.nameJQuery+"_costeUnitario").bind("click", function(e) {
		if(validacionStockDevuelto(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionStockDevuelto(document.activeElement.id, 'S');

			actualizarSortOrder('costeUnitario');

			$(gridP83.nameJQuery).setGridParam({sortname:'_costeUnitario'});
			$(gridP83.nameJQuery).setGridParam({page:1});
			reloadData_gridP83DevolFinCampana();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});

	$(gridP83.nameJQuery+"_marca").unbind('click');
	$(gridP83.nameJQuery+"_marca").bind("click", function(e) {
		if(validacionStockDevuelto(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionStockDevuelto(document.activeElement.id, 'S')

			actualizarSortOrder('marca');

			$(gridP83.nameJQuery).setGridParam({sortname:'marca'});
			$(gridP83.nameJQuery).setGridParam({page:1});
			reloadData_gridP83DevolFinCampana();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});

	$(gridP83.nameJQuery+"_stockActual").unbind('click');
	$(gridP83.nameJQuery+"_stockActual").bind("click", function(e) {
		if(validacionStockDevuelto(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionStockDevuelto(document.activeElement.id, 'S');

			actualizarSortOrder('stockActual');

			$(gridP83.nameJQuery).setGridParam({sortname:'stockActual'});
			$(gridP83.nameJQuery).setGridParam({page:1});
			reloadData_gridP83DevolFinCampana();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});

	$(gridP83.nameJQuery+"_stockDevolver").unbind('click');
	$(gridP83.nameJQuery+"_stockDevolver").bind("click", function(e) {
		if(validacionStockDevuelto(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionStockDevuelto(document.activeElement.id, 'S');

			actualizarSortOrder('stockDevolver');

			$(gridP83.nameJQuery).setGridParam({sortname:'stockDevolver'});
			$(gridP83.nameJQuery).setGridParam({page:1});
			reloadData_gridP83DevolFinCampana();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});

	$(gridP83.nameJQuery+"_stockDevuelto").unbind('click');
	$(gridP83.nameJQuery+"_stockDevuelto").bind("click", function(e) {
		if(validacionStockDevuelto(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionStockDevuelto(document.activeElement.id, 'S');

			actualizarSortOrder('stockDevuelto');

			$(gridP83.nameJQuery).setGridParam({sortname:'stockDevuelto'});
			$(gridP83.nameJQuery).setGridParam({page:1});
			reloadData_gridP83DevolFinCampana();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});

	$(gridP83.nameJQuery+"_bulto").unbind('click');
	$(gridP83.nameJQuery+"_bulto").bind("click", function(e) {
		if(validacionStockDevuelto(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionStockDevuelto(document.activeElement.id, 'S');

			actualizarSortOrder('bulto');

			$(gridP83.nameJQuery).setGridParam({sortname:'bulto'});
			$(gridP83.nameJQuery).setGridParam({page:1});
			reloadData_gridP83DevolFinCampana();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});	 
	$(gridP83.nameJQuery+"_formatoDevuelto").unbind('click');
	$(gridP83.nameJQuery+"_formatoDevuelto").bind("click", function(e) {
		if(validacionStockDevuelto(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionStockDevuelto(document.activeElement.id, 'S');

			actualizarSortOrder('formatoDevuelto');

			$(gridP83.nameJQuery).setGridParam({sortname:'formDevuelto'});
			$(gridP83.nameJQuery).setGridParam({page:1});
			reloadData_gridP83DevolFinCampana();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});	 
	$(gridP83.nameJQuery+"_tipoReferencia").unbind('click');
	$(gridP83.nameJQuery+"_tipoReferencia").bind("click", function(e) {
		if(validacionStockDevuelto(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionStockDevuelto(document.activeElement.id, 'S');

			actualizarSortOrder('tipoReferencia');

			$(gridP83.nameJQuery).setGridParam({sortname:'tipoReferencia'});
			$(gridP83.nameJQuery).setGridParam({page:1});
			reloadData_gridP83DevolFinCampana();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});	 
	$(gridP83.nameJQuery+"_cantAbonada").unbind('click');
	$(gridP83.nameJQuery+"_cantAbonada").bind("click", function(e) {
		if(validacionStockDevuelto(document.activeElement.id, 'N') == 'S'){			
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionStockDevuelto(document.activeElement.id, 'S');

			actualizarSortOrder('cantAbonada');

			$(gridP83.nameJQuery).setGridParam({sortname:'cantAbonada'});
			$(gridP83.nameJQuery).setGridParam({page:1});
			reloadData_gridP83DevolFinCampana();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});	 
	$(gridP83.nameJQuery+"_descAbonoError").unbind('click');
	$(gridP83.nameJQuery+"_descAbonoError").bind("click", function(e) {
		if(validacionStockDevuelto(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionStockDevuelto(document.activeElement.id, 'S');

			actualizarSortOrder('descAbonoError');

			$(gridP83.nameJQuery).setGridParam({sortname:'descAbonoError'});
			$(gridP83.nameJQuery).setGridParam({page:1});
			reloadData_gridP83DevolFinCampana();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});	 
}

function actualizarSortOrder (columna) {

	var ultimoSortName = $(gridP83.nameJQuery).jqGrid('getGridParam','sortname');
	var ultimoSortOrder = $(gridP83.nameJQuery).jqGrid('getGridParam','sortorder');

	if (ultimoSortName != columna ) { //Se ha cambiado la columna por la que se quiere ordenar
		$(gridP83.nameJQuery).setGridParam({sortorder:'asc'});
		$(gridP83.nameJQuery + "_" + columna + " [sort='asc']").removeClass("ui-state-disabled");

		//En la columna anterior quitamos la flechas
		if (ultimoSortOrder=='asc'){
			$(gridP83.nameJQuery + "_" + ultimoSortName + " [sort='asc']").addClass("ui-state-disabled");
		}else{
			$(gridP83.nameJQuery + "_" + ultimoSortName + " [sort='desc']").addClass("ui-state-disabled");
		}
	} else { //Seguimos en la misma columna
		if (ultimoSortOrder=='asc'){
			$(gridP83.nameJQuery).setGridParam({sortorder:'desc'});
			//Mostramos la flecha descendente y quitamos la flecha ascendente
			$(gridP83.nameJQuery + "_" + columna + " [sort='desc']").removeClass("ui-state-disabled");
			$(gridP83.nameJQuery + "_" + columna + " [sort='asc']").addClass("ui-state-disabled");
		} else {
			$(gridP83.nameJQuery).setGridParam({sortorder:'asc'});
			$(gridP83.nameJQuery + "_" + columna + " [sort='asc']").removeClass("ui-state-disabled");
			$(gridP83.nameJQuery + "_" + columna + " [sort='desc']").addClass("ui-state-disabled");
		}  		
	}
}


function imprimirDevolucionesFinCampana(){

	var tipoImpresion = $("input[name='p83_rad_tipoimpresion']:checked").val();

	var url = './p95ListadoDevoluciones/getPdfDevolucionFinCampana.do?localizador='+devolucionFicha.localizador+'&tipoImpresion='+tipoImpresion+'&filtroProveedor='+comboValue;
	window.open(url,"_blank");
}

//Función que rellena las filas restantes de la última página como filas invalidadas
//function rellenadoDeFilas() {
//	//Numero de filas a mostras obtenidas
//	var numeroDeFilasTotalReal = jQuery("#gridP83DevolFinCampana").jqGrid('getGridParam', 'records');
//
//	//Numero de filas que hay que mostrar por página 10,20,50
//	var numeroDeFilasPorPagina = jQuery("#gridP83DevolFinCampana").jqGrid('getGridParam', 'rowNum');
//
//	//Indica la página actual que estamos viendo
//	var numeroPaginaActual = jQuery("#gridP83DevolFinCampana").jqGrid('getGridParam', 'page');
//
//	//Indica la cantidad de páginas que hay
//	var numeroUltimaPagina = jQuery("#gridP83DevolFinCampana").jqGrid('getGridParam', 'lastpage');
//
//	//Indica el número de filas que le corresponde a la última fila
//	var numeroLineasUltimaPaginaReal = numeroDeFilasTotalReal % numeroDeFilasPorPagina;
//
//	var ids = jQuery("#gridP83DevolFinCampana").jqGrid('getDataIDs');
//	
//	for (var i = 0; i < ids.length; i++) {
//		var cellValue = jQuery("#gridP83DevolFinCampana").jqGrid('getCell',ids[i],'bulto');
//
//		var indice = i+1;
//		$("#"+indice).find('td[aria-describedby="gridP83DevolFinCampana_bulto"]').html(cellValue+"&nbsp;<img src='./misumi/images/plus.png' width='20' height='20'>");
//	}
//}

//Función que rellena todas las filas en estado invalidado.
//function rellenadoDeFilasNoEncontradas() {
//	var numeroDeFilasPorPagina = jQuery("#gridP83DevolFinCampana").jqGrid('getGridParam', 'rowNum');
//	for (var i = 0; i < numeroDeFilasPorPagina; i++) {
//		//Se añade la fila vacía
//		$("#gridP83DevolFinCampana").addRowData(i, {});
//
//		//Se añade la clase que pone gris las celdas de la fila
//		$(gridP83.nameJQuery).find("#"+(i)).find("td").addClass("p83_filaInvalidada");
//
//		//Se quita la clase de la fila para evitar que salgan opacidades, colores o líneas que separen las celdas
//		$("#"+i).attr('class','');
//
//		//Se elimina el 0 formateado de la fila stockActual por un vacío.
//		$("#"+i).find('td[aria-describedby="gridP83DevolFinCampana_stockActual"]').html("");
//
//		//Se elimina el 0 formateado de la fila formato por un vacío.
//		$("#"+i).find('td[aria-describedby="gridP83DevolFinCampana_formato"]').html("");
//
//		//Se quitan las numeraciones de las filas invalidadas
//		//$("#"+i).find('td[aria-describedby="gridP84DevolOrdenRetirada_rn"]').html("");
//		//$("#"+i).find('td[aria-describedby="gridP84DevolOrdenRetirada_rn"]').attr('class','');
//		//$("#"+i).find('td[aria-describedby="gridP84DevolOrdenRetirada_rn"]').addClass("p84_filaInvalidada");			
//	}
//	$("#pagerGridp83_right .ui-paging-info").html(gridP83.emptyRecords);
//}

//Función que inserta titulos
function setTitleOnColumnHeader(nombreColumna, titulo){
	$("#jqgh_gridP83DevolFinCampana_"+nombreColumna).prop('title',titulo);
}

function redirigirLogin() {
	window.location='./login.do';
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

function refAutoCompleteP83(){
	var cache = {};
	$("#gbox_gridP83DevolFinCampana #gs_codArticulo").autocomplete({
		minLength: 1,
		mustMatch:true,
		source: function( request, response ) {
			//Miramos si el grid tiene al menos una fila para hacer la búsqueda.
			var gridConFila = jQuery(gridP83.nameJQuery).jqGrid('getGridParam', 'records') > 0;
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
					url: "./devoluciones/popup/finDeCampana/loadRefPattern.do?term="+ request.term,
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
			reset_p83(0);
			reloadData_FiltroP83(ui.item.id);
			this.close;
		}
	});
	
}

// Vuelve a obtener la lista de proveedores del combo con los cambios que hayan podido tener.
// y dejando seleccionado el proveedor que estaba seleccionado antes de guardar.
function recargaCmbProvSelec(objJSON, provSeleccionado) {
	// Volver a cargar los valores de la combo de proveedores
	// porque puede variar el completado o no del proveedor.
	cargarComboboxProveedor(objJSON);

	let cmbProveedores = $('#p83_cmb_proveedor option');
    
    cmbProveedores.each(function() {
    	if ($(this).val() == provSeleccionado) {
			$("#p83_cmb_proveedor").combobox('autocomplete',$(this).text());
			$("#p83_cmb_proveedor").val(provSeleccionado);
			
			comboValue = provSeleccionado;
			if (comboValue !=null){
				$("#gbox_gridP83DevolFinCampana #gs_codArticulo").val("");
				$('#gridP83DevolFinCampana').setGridParam({page:1});
				reloadData_gridP83DevolFinCampana();	
			}
        }
    });
}
