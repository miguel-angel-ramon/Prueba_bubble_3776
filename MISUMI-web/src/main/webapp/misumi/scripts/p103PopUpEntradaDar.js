/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/**************************************************************    CONSTANTES   **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
var maxNumEnteros = 7;
var maxNumDecimales = 3;

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************** DOCUMENTO LISTO **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Variable del objeto que guarda el grid
var gridP103 = null;

//En la primera carga se inicia el contador a 1. Esta variable sirve para ponerla en los ids de los inputs del grid y saber de cual estamos hablando.
var numRowP103 = 1;

//Se inicializa resultadoValidacionP103 en 'S' para que antes de editar totalUnidadesRecepcionadas se pueda paginar sin problemas
var resultadoValidacionP103 = 'S';

var seleccionadosP103 = new Array();
var modificadosP103 = new Array();

//Guarda una lista de EntradaLineaModificado, que contiene por cada línea modificada de la página actual un objeto con 
//datos del: numeroCajasRecepcionadas, totalUnidadesRecepcionadas, totalBandejasRecepcionadas, codigoError y codArticulo.
var listadoModificadosP103 = new Array();

//Texto de iconos del cod error. 
var iconoGuardadoP103 = null;
var iconoModificadoP103 = null;

//Errores de totsl unidades recepcionadas a mostrar
var totalUnidadesRecepcionadasIncorrectoP103 = null;
var totalUnidadesRecepcionadasEnteroErroneoP103 = null;
var totalUnidadesRecepcionadasDecimalErroneoP1032 = null;
var totalUnidadesRecepcionadasDecimalErroneoP103 = null;
var guardadoCorrectamenteP103 = null;
var emptyListaModificadosP103 = null;
var sinDato = null;

//Texto tipoRecepcion
var tipoRecepcionDESADV = null;
var tipoRecepcionLoPedido = null;

//Titulo popup
var tituloEntradaDescentralizada = null;

$(document).ready(function(){
	//Inicializa el grid
	loadP103(locale);

	//Inicializa el popup que contiene el grid con las líneas de la entrada
	initializeScreenP103PopupEntradaDar();

	//Nos posicionamos en el campo al que ha navegado el usuario y que hemos almacenado en el hidden.
	//De esta forma al clicar aceptar en la alerta, vuelve al último campo de edición del grid.
	$("#p03_btn_aceptar").bind("click", function(e) {
		if ($("#p103_fld_totalUnidadesRecepcionadas_Selecc").val() != '' && $("#p103_fld_totalUnidadesRecepcionadas_Selecc").val() != undefined)
		{
			$("#"+$("#p103_fld_totalUnidadesRecepcionadas_Selecc").val()).focus();
			$("#"+$("#p103_fld_totalUnidadesRecepcionadas_Selecc").val()).select();
			e.stopPropagation();
			$("#p103_fld_totalUnidadesRecepcionadas_Selecc").val('');
		}
	});

	//Eventos flecha formularios
	events_p103_btn_actualizarEntradaLineas();

	//events_P103_btn_impresora();
});

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************ INICIALIZACIÓN DE ELEMENTOS E IDIOMA *******************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

/************************ IDIOMA Y GRID ************************/
function loadP103(locale){
	//Define la estructura del grid, sus columnas de dónde van a obtener los datos etc.
	gridP103 = new GridP103DarEntrada(locale);

	//Define el ancho del grid.
	$(gridP103.nameJQuery).jqGrid('setGridWidth',950, true);

	//Carga en data los nombres de las colummnas del grid, etc.
	var jqxhr = $.getJSON(gridP103.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		//Carga el nombre de las columnas, titulo, etc.
		gridP103.colNames = data.ColNames;
		gridP103.title = data.GridTitle;
		gridP103.emptyRecords= data.emptyRecords;

		//Definimos las columnas, las llamadas al paginar, ordenar etc.
		load_gridP103DarEntradaMock(gridP103);

		//Pone los titles al grid ()
		p103SetHeadersTitles(data);	

		//Texto de los iconos de cod error.
		iconoGuardadoP103 = data.iconoGuardado;
		iconoModificadoP103 = data.iconoModificado; 

		//Mensajes de error.
		notPosibleToReturnDataP103 = data.notPosibleToReturnDataP103;
		errorActualizacionP103 = data.errorActualizacionP103;
		errorActualizacionRegistroP103 = data.errorActualizacionRegistroP103;
		emptyListaModificadosP103 = data.emptyListaModificadosP103;

		//Mnesajes de error de campos.
		totalUnidadesRecepcionadasIncorrectoP103 = data.totalUnidadesRecepcionadasIncorrectoP103;
		totalUnidadesRecepcionadasEnteroErroneoP103 = data.totalUnidadesRecepcionadasEnteroErroneoP103;
		totalUnidadesRecepcionadasDecimalErroneoP1032 = data.totalUnidadesRecepcionadasDecimalErroneoP1032;
		totalUnidadesRecepcionadasDecimalErroneoP103 = data.totalUnidadesRecepcionadasDecimalErroneoP103;
		guardadoCorrectamenteP103 = data.guardadoCorrectamenteP103;
		emptyListaModificadosP103 = data.emptyListaModificadosP103;
		sinDato = data.sinDato;
		
		//
		tableFilterP103 = data.tableFilter;

		//Texto tipo recepción.
		tipoRecepcionDESADV = data.tipoRecepcionDESADV;
		tipoRecepcionLoPedido = data.tipoRecepcionLoPedido;	
		
		//Titulo popup
		tituloEntradaDescentralizada = data.tituloEntradaDescentralizada;
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

/************************ TITULOS COLUMNAS GRID ************************/
//Ponemos los titles en el grid. Todos excepto el rn. Para obtener el texto de los
//titles, tenemos que crear las variables en el json con nombreColumnaTitle.
function p103SetHeadersTitles(data){
	var colModel = $(gridP103.nameJQuery).jqGrid("getGridParam", "colModel");
	$.each(colModel, function(i) {
		if (colModel[i].name!="rn"){
			$("#jqgh_gridP103DarEntrada_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
		}
	});
}

/************************ GRID ************************/
//Define la estructura del grid
function GridP103DarEntrada(locale){
	// Atributos
	this.name = "gridP103DarEntrada"; 
	this.nameJQuery = "#gridP103DarEntrada"; 
	this.i18nJSON = './misumi/resources/p103PopUpEntradaDar/p103PopUpEntradaDar_' + locale + '.json';
	this.colNames = null; //Está en el json
	this.cm = [
	           {
	        	   "name"      : "codArticulo",
	        	   "index"     : "codArticulo",
	        	   "width"     : 80,
	        	   "align"     : "left",

	           },{
	        	   "name"      : "denomCodArticulo",
	        	   "index"     : "denomCodArticulo",
	        	   "width"     : 140,
	        	   "align"     : "left"
	           },{
	        	   "name"      : "uc",
	        	   "index"     : "uc", 
	        	   "width"     : 40,	
	        	   "align"     : "left"						
	           },{
	        	   "name"      : "numeroCajasPedidas",
	        	   "index"     : "numeroCajasPedidas", 
	        	   "width"     : 40,	
	        	   "align"     : "left"						
	           },{
	        	   "name"      : "totalUnidadesPedidas",
	        	   "index"     : "totalUnidadesPedidas", 
	        	   "width"     : 40,	
	        	   "align"     : "left"						
	           },{
	        	   "name"      : "totalBandejasPedidas",
	        	   "index"     : "totalBandejasPedidas",
	        	   hidden:true, 
	        	   "width"     : 40,	
	        	   "align"     : "left"						
	           },{
	        	   "name"      : "numeroCajasRecepcionadas",
	        	   "index"     : "numeroCajasRecepcionadas",
	        	   "width"     : 55,	
	        	   "editable" : true,
	        	   "editoptions":{
	        		   "size":maxNumEnteros,
	        		   "maxlength":maxNumEnteros,
	        		   "dataEvents": [
	        		                  {//control para el keydown
	        		                	  type: 'keydown',
	        		                	  fn: controlNavegacionP103,
	        		                  },{//control para el keydown
	        		                	  type: 'keyup',
	        		                	  fn: controlDatoCajasRecepcionadasP103,
	        		                  },
	        		                  // cierra el control del keydown
	        		                  {//control para el click
	        		                	  type: 'click',
	        		                	  fn: controlClickNumeroCajasRecepcionadasP103
	        		                  }
	        		                  ] 
	        	   },
	        	   "align"     : "left"						
	           },{
	        	   "name"      : "totalUnidadesRecepcionadas",
	        	   "index"     : "totalUnidadesRecepcionadas",	        	  
	        	   "width"     : 60,
	        	   "editable": true,	        
	        	   "editoptions":{
	        		   "size": maxNumEnteros + maxNumDecimales +1,
	        		   "maxlength": maxNumEnteros + maxNumDecimales +1,
	        		   "dataEvents": [
	        		                  {//control para el keydown
	        		                	  type: 'keydown',
	        		                	  fn: controlNavegacionP103,
	        		                  },// cierra el control del keydown
	        		                  {//control para el click
	        		                	  type: 'click',
	        		                	  fn: controlClickTotalUnidadesRecepcionadasP103
	        		                  }
	        		                  ] 
	        	   },
	        	   "formatter" :  totalUnidadesRecepcionadasFormatterP103,
	        	   "align"     : "left",
	           },{
	        	   "name"      : "totalBandejasRecepcionadas",
	        	   "index"     : "totalBandejasRecepcionadas", 
	        	   hidden:true, 
	        	   "width"     : 40,	
	        	   "align"     : "left"						
	           },{
	        	   "name" : "mensaje",
	        	   "index":"mensaje", 
	        	   "formatter": imageFormatMessageP103,
	        	   "fixed":true,
	        	   "width" : 50,
	        	   "sortable" : true
	           }
	           ]; 
	this.locale = locale;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerGridp103";  
	this.pagerNameJQuery = "#pagerGridp103";
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
		var colModel = jQuery(gridP103.nameJQuery).jqGrid('getGridParam', 'colModel'); 
		var i;
		var l = colModel.length;
		var colItem; 
		var cmName;
		var postData = jQuery(gridP103.nameJQuery).jqGrid('getGridParam', 'postData');
		var columnsState = {
				search: jQuery(gridP103.nameJQuery).jqGrid('getGridParam', 'search'),
				page: jQuery(gridP103.nameJQuery).jqGrid('getGridParam', 'page'),
				sortname: jQuery(gridP103.nameJQuery).jqGrid('getGridParam', 'sortname'),
				sortorder: jQuery(gridP103.nameJQuery).jqGrid('getGridParam', 'sortorder'),
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
function load_gridP103DarEntradaMock(gridP103) {
	$(gridP103.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		datatype : 'local',
		contentType : 'application/json',
		mtype : 'POST',
		colNames : gridP103.colNames,
		colModel : gridP103.cm,
		rowNum : 10,
		rowList : [ 10, 30, 50 ],
		height : "100%",
		autowidth : true,
		width : "100%",
		rownumbers : gridP103.rownumbers,
		pager : gridP103.pagerNameJQuery,
		viewrecords : true,
		caption : gridP103.title,
		altclass: "ui-priority-secondary",
		altRows: true, //false, para que el grid no muestre cebrado
		hidegrid : false, //false, para ocultar el boton que colapsa el grid.
		sortable : true,
		index: gridP103.sortIndex,
		sortname: gridP103.sortIndex,
		sortorder: gridP103.sortOrder,
		emptyrecords : gridP103.emptyRecords,
		gridComplete : function() {
			gridP103.headerHeight("p103_gridHeader");				

			//Una vez cargado el grid, iniciamos el contador a 1.
			//Al ser una variable global y actualizarse cada vez
			//que se abre un nuevo grid en la función imageFormatMessageP103
			//obtiene valores no acordes con el número de fila cargado
			//por eso hay que iniciarlo a 1.
			numRowP103 = 1;
		},
		loadComplete : function(data) {
			gridP103.actualPage = data.page;
			gridP103.localData = data;
			gridP103.sortIndex = gridP103.sortIndex;
			gridP103.sortOrder = gridP103.sortOrder;	

			if (gridP103.firstLoad)
				jQuery(gridP103.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
			/*//Para el filtro del grid
			$(gridP103.nameJQuery).jqGrid('filterToolbar',{defaultSearch : "cn"});
			$("#gbox_gridP103DarEntrada #gs_codArticulo").css("display", "none");
			$("#gbox_gridP103DarEntrada #gs_denomCodArticulo").css("display", "none");
			$("#gbox_gridP103DarEntrada #gs_uc").css("display", "none");
			$("#gbox_gridP103DarEntrada #gs_numeroCajasPedidas").css("display", "none");
			$("#gbox_gridP103DarEntrada #gs_numeroCajasRecepcionadas").css("display", "none");
			$("#gbox_gridP103DarEntrada #gs_totalBandejasPedidas").css("display", "none");
			$("#gbox_gridP103DarEntrada #gs_totalBandejasRecepcionadas").css("display", "none");
			$("#gbox_gridP103DarEntrada #gs_totalUnidadesPedidas").css("display", "none");
			$("#gbox_gridP103DarEntrada #gs_totalUnidadesRecepcionadas").css("display", "none");
			$("#gbox_gridP103DarEntrada #gs_mensaje").css("display", "none");*/
		},
		resizeStop: function () {
			gridP103.saveColumnState.call($(this),gridP103.myColumnsState);
			$(gridP103.nameJQuery).jqGrid('setGridWidth',950,true);
		},
		onPaging : function(postdata) {	
			//Antes de introducir este control, en el caso de estar en un campo editable totalUnidadesRecepcionadas y clicar que pagine, se ejecutaba primero el evento focusout y más tarde este evento onPaging.
			//Esto creaba situaciones en las que al introducir un campo erroneo en totalUnidadesRecepcionadas (con más de una coma, más de 15 enteros o más de 3 decimales), primero lanzaba el error de que el campo
			//se había introducido mal y más tarde paginaba. Para ello, como el focusout realiza una validación de que el campo editable es correcto o incorrecto y devuelve una variable llamada resultadoValidacionP103 'S' o 'N',
			//se ha transformado esa variable en global. De esta forma, al realizar el focusout se realiza la validación, en el caso de haber error lo muestra e indica al evento onPaging de que no tiene que realizar
			//la recarga de datos ni la paginación. En caso de que se introduzca un campo correcto en totalUnidadesRecepcionadas, el focusout realiza una validación correcta y recalcula los datos del grid.

			//Puede darse la situación de que no se realice el focusout de y por consecuente la validación de totalUnidadesRecepcionadas, porque no se haya editado ningún campo o porque el usuario no se haya situado en ninguno de esos campos. Por lo cual, la variable
			//resultadoValidacionP103 se inicializa siempre en 'S'. Cuando se clica a paginar, pagine o no, resultadoValidacionP103 obtiene el valor 'S', puesto que en el caso de haber editado un dato de forma erronea se autocorrige, y queremos que si el usuario
			//clica el elemento paginar al instante, pagine.
			if(resultadoValidacionP103 == 'S'){
				alreadySorted = false;
				gridP103.sortIndex = gridP103.sortIndex;
				gridP103.sortOrder = gridP103.sortOrder;
				gridP103.saveColumnState.call($(this), this.p.remapColumns);
				reloadData_gridP103DarEntrada();

				return 'stop';
			}else{
				//Se cambia el estado de la validación a ´S´, porque anque haya error, se corrige el dato y queremos que se pueda paginar.
				resultadoValidacionP103 = 'S';

				//En caso de existir un error, no se pagina y devuelve error. Pero el componente internamente pagina, por ese motivo, hay que restar 1 a la paginación. 
				$('#gridP103DarEntrada').setGridParam({page:gridP103.getActualPage()-1});

				return 'stop';		
			}
		},
		onSelectRow: function(id){

		},
		onSortCol : function (index, columnIndex, sortOrder){
			gridP103.sortIndex = index;
			gridP103.sortOrder = sortOrder;
			gridP103.saveColumnState.call($(this), this.p.remapColumns);
			reloadData_gridP103DarEntrada();
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

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/********************************************************** FORMATEADORES GRID ***************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

function totalUnidadesRecepcionadasFormatterP103(cellValue, opts, rData) {
	if(cellValue == null || cellValue.toString() == ""){
		if(opts.colModel.name == "totalUnidadesRecepcionadas"){
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

//Sirve para guardar campos ocultos de imágenes y datos originales de los campos editables.
//Se guarda dentro de estado mediante un hidden.
function imageFormatMessageP103(cellValue, opts, rData) {

	var imagen = "";
	var mostrarModificado = "none;";
	var mostrarGuardado = "none;";
	var mostrarError = "none;";
	var descErrorP103 = "";

	//Controlamos los posibles estados que me lleguen para pintar el icono correspondiente.
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
	else if (null != rData['codError'] && parseFloat(rData['codError']) != '0'){
		descErrorP103 = rData['descError'];
		mostrarError = "block;";
	}

	//Creamos los divs con las imagenes de edición, error y modificación ocultas.
	imagen = "<div id='"+numRowP103+"_numeroCajasRecepcionadasTotalUnidadesRecepcionadas_divGuardado' align='center' style='display: "+ mostrarGuardado + "'><img id='"+numRowP103+"_numeroCajasRecepcionadasTotalUnidadesRecepcionadas_imgGuardado' src='./misumi/images/floppy.png' title='"+iconoGuardadoP103+"'/></div>"; //Guardado
	imagen += "<div id='"+numRowP103+"_numeroCajasRecepcionadasTotalUnidadesRecepcionadas_divModificado' align='center' style='display: "+ mostrarModificado + "'><img id='"+numRowP103+"_numeroCajasRecepcionadasTotalUnidadesRecepcionadas_imgModificado' src='./misumi/images/modificado.png' title='"+iconoModificadoP103+"'/></div>"; //Modificado
	imagen += "<div id='"+numRowP103+"_numeroCajasRecepcionadasTotalUnidadesRecepcionadas_divError' align='center' style='display: " + mostrarError + "'><img id='"+numRowP103+"_numeroCajasRecepcionadasTotalUnidadesRecepcionadas_imgError' src='./misumi/images/dialog-error-24.png' title='"+descErrorP103+"'/></div>"; //Error		

	var numeroCajasRecepcionadasOrig = rData['numeroCajasRecepcionadasOrig'];
	var datoNumeroCajasRecepcionadasOrig;

	if(numeroCajasRecepcionadasOrig != null){
		datoNumeroCajasRecepcionadasOrig = "<input type='hidden' id='"+numRowP103+"_numeroCajasRecepcionadas_orig' value='"+numeroCajasRecepcionadasOrig+"'>";
	}else{
		datoNumeroCajasRecepcionadasOrig = "<input type='hidden' id='"+numRowP103+"numeroCajasRecepcionadas_orig' value=''>";
	}
	imagen +=  datoNumeroCajasRecepcionadasOrig;

	var datoNumeroCajasRecepcionadas;
	var numeroCajasRecepcionadas = rData['numeroCajasRecepcionadas'];

	if(numeroCajasRecepcionadas != null){
		datoNumeroCajasRecepcionadas = "<input type='hidden' id='"+numRowP103+"_numeroCajasRecepcionadas_tmp' value='"+numeroCajasRecepcionadas+"'>";
	}else{		
		datoNumeroCajasRecepcionadas = "<input type='hidden' id='"+numRowP103+"_numeroCajasRecepcionadas_tmp' value=''>";
	}
	imagen +=  datoNumeroCajasRecepcionadas;

	var totalUnidadesRecepcionadasOrig = rData['totalUnidadesRecepcionadasOrig'];
	var datoTotalUnidadesRecepcionadasOrig;

	if(totalUnidadesRecepcionadasOrig != null){
		datoTotalUnidadesRecepcionadasOrig = "<input type='hidden' id='"+numRowP103+"_totalUnidadesRecepcionadas_orig' value='"+$.formatNumber(totalUnidadesRecepcionadasOrig,{format:"0.###",locale:"es"})+"'>";
	}else{
		datoTotalUnidadesRecepcionadasOrig = "<input type='hidden' id='"+numRowP103+"_totalUnidadesRecepcionadas_orig' value=''>";
	}
	imagen +=  datoTotalUnidadesRecepcionadasOrig;

	var totalUnidadesRecepcionadas = rData['totalUnidadesRecepcionadas'];
	var datoTotalUnidadesRecepcionadas;

	if(totalUnidadesRecepcionadas != null){
		datoTotalUnidadesRecepcionadas = "<input type='hidden' id='"+numRowP103+"_totalUnidadesRecepcionadas_tmp' value='"+$.formatNumber(totalUnidadesRecepcionadas,{format:"0.###",locale:"es"})+"'>";
	}else{
		datoTotalUnidadesRecepcionadas = "<input type='hidden' id='"+numRowP103+"_totalUnidadesRecepcionadas_tmp' value=''>";
	}
	imagen +=  datoTotalUnidadesRecepcionadas;

	//Se guarda en un hidden el dato de las bandejas.
	var totalBandejasRecepcionadas = rData['totalBandejasRecepcionadas'];
	var datoTotalBandejasRecepcionadas;

	if(totalBandejasRecepcionadas != null){
		datoTotalBandejasRecepcionadas = "<input type='hidden' id='"+numRowP103+"_totalBandejasRecepcionadas_tmp' value='"+totalBandejasRecepcionadas+"'>";
	}else{
		datoTotalBandejasRecepcionadas = "<input type='hidden' id='"+numRowP103+"_totalBandejasRecepcionadas_tmp' value=''>";
	}
	imagen +=  datoTotalBandejasRecepcionadas;

	//Se guarda en un hidden el dato de los kgs original.
	var totalBandejasRecepcionadasOrig = rData['totalBandejasRecepcionadasOrig'];
	var datoTotalBandejasRecepcionadasOrig;

	if(totalBandejasRecepcionadasOrig != null){
		datoTotalBandejasRecepcionadasOrig = "<input type='hidden' id='"+numRowP103+"_totalBandejasRecepcionadas_orig' value='"+totalBandejasRecepcionadasOrig+"'>";
	}else{
		datoTotalBandejasRecepcionadasOrig = "<input type='hidden' id='"+numRowP103+"_totalBandejasRecepcionadas_orig' value=''>";
	}
	imagen +=  datoTotalBandejasRecepcionadasOrig;


	//Añadimos también el valor del codError de cada registro.
	var datoError = "<input type='hidden' id='"+numRowP103+"_numeroCajasRecepcionadasTotalUnidadesRecepcionadas_codError_orig' value='"+rData['codError']+"'>";
	imagen +=  datoError;

	/*//Guardamos el estado de la fila
	var estado = "<input type='hidden' id='"+numRowP103+"_estado' value='"+rData['estado']+"'>";	
	imagen += estado;*/

	numRowP103++;
	return imagen;
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/**************************************************** INICIALIZAR PORPUP Y DATEPICKER ********************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

function initializeScreenP103PopupEntradaDar(){
	initializeP103PopUp();
	initializeP103Datepicker();
}

function initializeP103PopUp(){
	$( "#p103_popUpEntradaDar" ).dialog({
		autoOpen: false,
		height: 'auto',
		width: 'auto',
		modal: true,
		resizable: false,
		open: function() {
			$('#p103_popUpEntradaDar .ui-dialog-titlebar-close').on('mousedown', function(){
				//Antes de cerrar el popup reseteamos el array de seleccionadosP103
				seleccionadosP103 = new Array();
				$("#p103_popUpEntradaDar").dialog('close');
			});
		},
		close: function( event, ui ) {
			//Al salir eliminar la lista de seleccionadosP103 y de listadoModificadosP103.
			//Además limpiar grid y poner elementos a buscar en 10
			reset_p103();
		},
		//Fijar el z-index del popup p56 para que las opciones del combobox no se queden detrás del popup 
		//al autoincrementarse el z-index.
		stack: false
	});
	$(window).bind('resize', function() {
		$("#p103_popUpEntradaDar").dialog("option", "position", "center");
	});
}

//Definimos el datepicker.
function initializeP103Datepicker(){
	$.datepicker.setDefaults($.datepicker.regional['es']);
	$("#p103_areaCabecera_datepicker_fechaEntrada").datepicker({
		//minDate: new Date(anio, 1, 1),
		maxDate: new Date(),
		onSelect: function(date,inst) {
			//changeDatepickerValue(date,this,"S",inst);
		}
	});
}




/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/********************************************************* FUNCIONES DE P103 GRID ************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Función de carga de grid
function cargarLineasDarEntrada(entrada){
	//Ponemos el campo centro a la entrada.
	var codLoc = $("#centerId").val();
	entrada.codLoc = codLoc;

	//Poner ancho de grid, para que aunque haya más o menos columnas siempre ocupe lo mismo
	$(gridP103.nameJQuery).jqGrid('setGridWidth',950,true);
	var objJson = $.toJSON(entrada);
	$.ajax({
		type : 'POST',
		url : './entradasDescentralizadas/popup/dar/loadDataGrid.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		async: false,
		cache : false,
		success : function(data) {
			if(data != null){
				var filasGrid = data.datos;
				if(filasGrid != null){
					if(filasGrid.rows != null && filasGrid.rows.length > 0){
						//Gestionamos los campos del popup (filas grid, campos de cabecera, etc) según el perfil.
						gestionarPerfil();
						
						//Configuramos la cabecera del grid
						configurarCabecera103(data.entradaCab);

						//Configuramos el grid.
						configurarGridP103(filasGrid);

						//Ponemos las fotos en los elementos que tengan.
						generateTooltipP103(filasGrid);

						//Actualizamos la entrada con la que estamos usando
						entradaCabecera = data.entradaCab;
						
						//Abrimos el popup
						$("#p103_popUpEntradaDar").dialog('open');
					}else{
						createAlert(replaceSpecialCharacters(notPosibleToReturnDataP103), "ERROR");
					}
				}else{
					createAlert(replaceSpecialCharacters(notPosibleToReturnDataP103), "ERROR");
				}
			}else{
				createAlert(replaceSpecialCharacters(notPosibleToReturnDataP103), "ERROR");
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});	
}

//Función que sirve para paginar o reordenar los elementos del grid.
function reloadData_gridP103DarEntrada() {
	//Datos entrada
	var codLoc = $("#centerId").val();
	var codCabPedido = $("#p103_areaCabecera_numPed").text();

	//Obtenemos las filas modificadas
	obtenerListadoModificadosP103();

	var entradaEditada = new Entrada(codLoc,codCabPedido,null,null,null,null,listadoModificadosP103);

	//Borrar el listado de modificadosP103
	listadoModificadosP103 = new Array();

	var objJson = $.toJSON(entradaEditada);

	$.ajax({
		type : 'POST',			
		url : './entradasDescentralizadas/popup/dar/loadDataGrid.do?page='+gridP103.getActualPage()+'&max='+gridP103.getRowNumPerPage()+'&index='+gridP103.getSortIndex()+'&sortorder='+gridP103.getSortOrder()+'&recarga=S',
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		data : objJson,
		success : function(data) {	
			if(data != null){
				var filasGrid = data.datos;
				if(filasGrid.rows != null && filasGrid.rows.length > 0){
					configurarGridP103(filasGrid);
					generateTooltipP103(filasGrid);
				}else{
					configurarGridP103(filasGrid);

					//Se pone esta fila porque si paginas y da error de paginación, el grid cambia el número de página internamente pero no externamente (la que se visualiza), y si luego
					//pagina bien, no va a la siguiente página, si no a la que tiene internamente. 
					var valorCajaGrid = $("#p103_popUpEntradaDar .ui-pg-input").val();
					$(gridP103.nameJQuery).setGridParam({page:valorCajaGrid});
				}
			}else{
				createAlert(replaceSpecialCharacters(notPosibleToReturnDataP103), "ERROR");

				//Se pone esta fila porque si paginas y da error de paginación, el grid cambia el número de página internamente pero no externamente (la que se visualiza), y si luego
				//pagina bien, no va a la siguiente página, si no a la que tiene internamente. 
				var valorCajaGrid = $("#p103_popUpEntradaDar .ui-pg-input").val();
				$(gridP103.nameJQuery).setGridParam({page:valorCajaGrid});
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});		
}

//Función que actualiza las líneas de entrada de una entrada.
function actualizarEntradaLineasP103(){	
	//Miramos si tenemos cambio de cabecera.
	var isChangeFechaEntrada = $("#p103_areaCabecera_datepicker_fechaEntrada").val() != $("#p103_areaCabecera_datepicker_fechaEntrada_orig").val();
	var isChangeAlbaran = $("#p103_areaCabecera_albaran").val() != $("#p103_areaCabecera_albaran_orig").val();
	var isChangeNumInc = $("#p103_areaCabecera_numeroIncidencia").val() != $("#p103_areaCabecera_numeroIncidencia_orig").val();

	//Si se ha modificado algún registro de cualquier página, se guarda. Si no se ha modificado ningún registro muestra error.
	if (modificadosP103.length > 0 || isChangeFechaEntrada || isChangeAlbaran || isChangeNumInc){
		//Obtenemos las filas modificadas
		obtenerListadoModificadosP103();

		//Obtenemos el código del centro y de cabecera de pedido.
		var codLoc = $("#centerId").val();
		var codCabPedido = $("#p103_areaCabecera_numPed").text();
		var codAlbProv = $("#p103_areaCabecera_albaran").val() != "" ? $("#p103_areaCabecera_albaran").val() : null;
		var fechaEntrada = $("#p103_areaCabecera_datepicker_fechaEntrada").datepicker("getDate") != "" ? $("#p103_areaCabecera_datepicker_fechaEntrada").datepicker("getDate") : null;
		var numIncidencia = $("#p103_areaCabecera_numeroIncidencia").val() != "" ? $("#p103_areaCabecera_numeroIncidencia").val() : null;

		var entradaEditada = new Object();
		entradaEditada.codLoc = codLoc;
		entradaEditada.codCabPedido = codCabPedido;
		entradaEditada.codAlbProv = codAlbProv;
		entradaEditada.fechaEntrada = fechaEntrada;
		entradaEditada.numIncidencia = numIncidencia;
		entradaEditada.lstModificados = listadoModificadosP103;
		entradaEditada.codProvGen = entradaCabecera.codProvGen;
		entradaEditada.codProvTrab = entradaCabecera.codProvTrab;
		entradaEditada.denomProvTrab = entradaCabecera.denomProvTrab;
		entradaEditada.fechaTarifa = entradaCabecera.fechaTarifa;
		entradaEditada.tipoRecepcion = entradaCabecera.tipoRecepcion;

		var objJson = $.toJSON(entradaEditada);

		$.ajax({
			type : 'POST',			
			url : './entradasDescentralizadas/popup/dar/saveDataGrid.do?page='+gridP103.getActualPage()+'&max='+gridP103.getRowNumPerPage()+'&index='+gridP103.getSortIndex()+'&sortorder='+gridP103.getSortOrder(),
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			data : objJson,
			success : function(data) {	
				if(data != null){	
					//Cuando llamamos a saveDataGrid, lo primero que se hace es actualizar los modificados de la temporal. Por este motivo, 
					//actualice o no actualice, hay que limpiar estos valores.

					//Restauramos el valor de modificados.
					modificadosP103 = new Array();

					//Borrar el listado de modificados
					listadoModificadosP103 = new Array();

					//Si se devuelven líneas se pintan. Puede que se actualice bien, entonces se pintarán todo disquetes.
					//Si se actualiza mal, como se ha realizado el update de los modificados, hay que pintar sus "hojas" para
					//indicar que están moficiados. Si no se repintaran las líneas, en BD estarían modificados, pero no se verían las
					//hojas de modificado.
					if(data.datos.rows != null && data.datos.rows.length > 0){
						//Si se devuelven líneas, se dibujan.
						configurarGridP103(data.datos);

						//Se dibujan los tooltip
						generateTooltipP103(data.datos);

						if(data.codError == 0){	
							//Si se ha guardado correctamente, actualizar datos ori de la cabecera y actualizar entrada cabecera.
							if(isChangeFechaEntrada) {
								$("#p103_areaCabecera_datepicker_fechaEntrada_orig").val($("#p103_areaCabecera_datepicker_fechaEntrada").val());
							}						
							if(isChangeAlbaran){
								$("#p103_areaCabecera_albaran_orig").val($("#p103_areaCabecera_albaran").val());
							}
							if(isChangeNumInc){
								$("#p103_areaCabecera_numeroIncidencia_orig").val($("#p103_areaCabecera_numeroIncidencia").val());
							}
							createAlert(replaceSpecialCharacters(guardadoCorrectamenteP103), "INFO");	
						}else{
							createAlert(replaceSpecialCharacters(data.descError), "ERROR");	
						}										
					}else{
						//No se repintan las filas. Ha ocurrido algún error y no queremos que se quiten las filas que estamos visualizando.
						createAlert(replaceSpecialCharacters(data.descError), "ERROR");	
					}	
				}else{
					createAlert(replaceSpecialCharacters(errorActualizacionP103), "ERROR");
				}
			},
			error : function (xhr, status, error){
				handleError(xhr, status, error, locale);				
			}			
		});	
	}else{
		createAlert(replaceSpecialCharacters(emptyListaModificadosP103), "ERROR");
	}	
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/***************************************************** RELLENADO DE DATOS GRID Y CABECERA ****************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Configuramos los datos de la cabecera de la entrada.
function configurarCabecera103(entrada){
	$("#p103_popUpEntradaDar").dialog({title: tituloEntradaDescentralizada + entrada.codCabPedido});
	
	//Insertamos el código de la cabecera
	$("#p103_areaCabecera_numPed").text(entrada.codCabPedido);

	//Insertamos la fecha tarifa
	$("#p103_areaCabecera_fechaTarifa").text(entrada.fechaTarifaStr);

	//Insertamos el proveedor
	$("#p103_areaCabecera_proveedor").text(entrada.codProvGen + "-" + entrada.codProvTrab + "/" + entrada.denomProvTrab);

	//Fecha entrada
	$("#p103_areaCabecera_datepicker_fechaEntrada").val(entrada.fechaEntradaStr);
	$("#p103_areaCabecera_datepicker_fechaEntrada_orig").val(entrada.fechaEntradaStr);
	$("#p103_lbl_areaCabecera_fechaEntrada").text(entrada.fechaEntradaStr != null ? entrada.fechaEntradaStr : sinDato);
	
	//Albarán
	$("#p103_areaCabecera_albaran").val(entrada.codAlbProv);
	$("#p103_areaCabecera_albaran_orig").val(entrada.codAlbProv);
	$("#p103_lbl_areaCabecera_albaran").text(entrada.codAlbProv != null ? entrada.codAlbProv : sinDato);
	
	//Numero de incidencia
	$("#p103_areaCabecera_numeroIncidencia").val(entrada.numIncidencia);
	$("#p103_areaCabecera_numeroIncidencia_orig").val(entrada.numIncidencia);
	$("#p103_lbl_areaCabecera_numeroIncidencia").text(entrada.numIncidencia != null ? entrada.numIncidencia : sinDato);
	
	if(entrada.tipoRecepcion == 1){
		//Insertamos desadv
		$("#p103_areaCabecera_tipoRecepcion").text(tipoRecepcionLoPedido);
	}else{
		//Insertamos lo pedido
		$("#p103_areaCabecera_tipoRecepcion").text(tipoRecepcionDESADV);
	}

	//Indicamos que el campo es numérico.
	$("#p103_areaCabecera_numeroIncidencia").filter_input({regex:'[0-9]'});	
}

//Función que inserta eventos focusout, formateadores de expresiones regulares, ordenaciones de columnas, colores y datos al grid.
function configurarGridP103(data){

	//Se rellena el grid
	$(gridP103.nameJQuery)[0].addJSONData(data);
	gridP103.actualPage = data.page;
	gridP103.localData = data;

	//configurarColumnasSegunResultadosP103(data);

	//Por cada fila, formatea los campos editables y les coloca campos con focusout.
	var ids = $(gridP103.nameJQuery).jqGrid('getDataIDs'), l = ids.length;
	var i = 0;
	for (i; i < l; i++) {
		$(gridP103.nameJQuery).jqGrid('editRow', ids[i], false);

		//Ponemos que solo puedan insertarse valores enteros en numero de cajas recepcionadas
		$("#"+ids[i]+"_numeroCajasRecepcionadas").filter_input({regex:'[0-9]'});		

		//Si se pierde el foco, se ejecutará la función que muestre el disquete, error, etc.
		$("#"+ids[i]+"_numeroCajasRecepcionadas").focusout(function() {
			validacionNumeroCajasRecepcionadasP103(this.id, 'S');
		});

		/*$("#"+ids[i]+"_numeroCajasRecepcionadas").focus(function() {
			controlFocuscolumnaNumeroCajasRecepcionadasP103(this.id);
		});*/

		/*$("#"+ids[i]+"_numeroCajasRecepcionadas").change(function() {
			controlChangecolumnaNumeroCajasRecepcionadasP103(this.id);
		});	*/

		//Ponemos que solo puedan insertarse valores decimales
		$("#"+ids[i]+"_totalUnidadesRecepcionadas").filter_input({regex:'[0-9,]'});	

		/*$("#"+ids[i]+"_totalUnidadesRecepcionadas").change(function() {
			controlChangecolumnaTotalUnidadesRecepcionadasP103(this.id);
		});*/

		$("#"+ids[i]+"_totalUnidadesRecepcionadas").focusout(function(e) {
			validacionTotalUnidadesRecepcionadasP103(this.id, 'S');							
		});
	}
	//Carga las ordenaciones de las columnas.
	cargarOrdenacionesColumnasP103();
}


/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/********************************************************** ORDENACIONES POR COLUMNA *********************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

function cargarOrdenacionesColumnasP103(){

	//Cuando pulse la columna para ordenar por icono tenemos que llamar manualmente al reload para que reordene,
	//ya que al tener el jsp campos editables no funciona de la manera convencional.
	$(gridP103.nameJQuery+"_codArticulo").unbind('click');
	$(gridP103.nameJQuery+"_codArticulo").bind("click", function(e) {
		//Antes de introducir esta validación, cuando se clicaba una reordenación de columna, hasta ahora ejecutaba primero este evento click y más tarde el focusout de la columna editable 
		//totalUnidadesRecepcionadas. Esto creaba situaciones, en el que en caso de introducir un campo erroneo en totalUnidadesRecepcionadas (con más de una coma,
		//más de 15 enteros o más de 3 decimales), no ejecutaba el focusout que se encarga de mostrar que ha habido un error de edición en ese campo y reordenaba directamente
		//la columna, siendo este comportamiento erroneo. Además creaba otro tipo de errores que se pueden ver en la aplicación como: 1. Editas un campo y tabulas, 2. Vuelves al
		//campo editado, 3. Introduces un error en el campo modificado, 4. Reordenas. Se quedaba la pantalla bloqueada!

		//Para realizar la validación cada vez que reordenamos, basta con introducir en este evento la validación del campo totalUnidadesRecepcionadas y como id document.activeElement.id (Esto,
		//se encarga de devolver el id del foco actual). Como se ejecuta antes el evento click que el evento focusout, el foco estará en totalUnidadesRecepcionadas y podremos realizar la validación correctamente.
		//En el caso de pasarla, se reordenará. En caso contrario devolverá error.

		//En el caso de que no estemos en ningún campo totalUnidadesRecepcionadas y al clicar ordenar, se reordenará siempre- De esto se encarga la función validacionTotalUnidadesRecepcionadas, que controla que el id sea de tipo totalUnidadesRecepcionadas,
		//en caso contrario, la reordenación se realiza siempre.
		if(validacionNumeroCajasRecepcionadasP103(document.activeElement.id, 'N') == 'S' && validacionTotalUnidadesRecepcionadasP103(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionNumeroCajasRecepcionadasP103(document.activeElement.id, 'S');
			validacionTotalUnidadesRecepcionadasP103(document.activeElement.id, 'S');
			
			actualizarSortOrderP103('codArticulo');

			$(gridP103.nameJQuery).setGridParam({sortname:'codArticulo'});
			$(gridP103.nameJQuery).setGridParam({page:1});
			reloadData_gridP103DarEntrada();
		}else{
			$("#p101_btn_buscar").focus();
		}
	});

	$(gridP103.nameJQuery+"_denomCodArticulo").unbind('click');
	$(gridP103.nameJQuery+"_denomCodArticulo").bind("click", function(e) {
		if(validacionNumeroCajasRecepcionadasP103(document.activeElement.id, 'N') == 'S' && validacionTotalUnidadesRecepcionadasP103(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionNumeroCajasRecepcionadasP103(document.activeElement.id, 'S');
			validacionTotalUnidadesRecepcionadasP103(document.activeElement.id, 'S');
			
			actualizarSortOrderP103('denomCodArticulo');

			$(gridP103.nameJQuery).setGridParam({sortname:'denomCodArticulo'});
			$(gridP103.nameJQuery).setGridParam({page:1});
			reloadData_gridP103DarEntrada();
		}else{
			$("#p101_btn_buscar").focus();
		}
	});

	$(gridP103.nameJQuery+"_uc").unbind('click');
	$(gridP103.nameJQuery+"_uc").bind("click", function(e) {
		if(validacionNumeroCajasRecepcionadasP103(document.activeElement.id, 'N') == 'S' && validacionTotalUnidadesRecepcionadasP103(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionNumeroCajasRecepcionadasP103(document.activeElement.id, 'S');
			validacionTotalUnidadesRecepcionadasP103(document.activeElement.id, 'S');
			
			actualizarSortOrderP103('uc');

			$(gridP103.nameJQuery).setGridParam({sortname:'uc'});
			$(gridP103.nameJQuery).setGridParam({page:1});
			reloadData_gridP103DarEntrada();
		}else{
			$("#p101_btn_buscar").focus();
		}
	});

	$(gridP103.nameJQuery+"_numeroCajasPedidas").unbind('click');
	$(gridP103.nameJQuery+"_numeroCajasPedidas").bind("click", function(e) {
		if(validacionNumeroCajasRecepcionadasP103(document.activeElement.id, 'N') == 'S' && validacionTotalUnidadesRecepcionadasP103(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionNumeroCajasRecepcionadasP103(document.activeElement.id, 'S');
			validacionTotalUnidadesRecepcionadasP103(document.activeElement.id, 'S');
			
			actualizarSortOrderP103('numeroCajasPedidas');

			$(gridP103.nameJQuery).setGridParam({sortname:'numeroCajasPedidas'});
			$(gridP103.nameJQuery).setGridParam({page:1});
			reloadData_gridP103DarEntrada();
		}else{
			$("#p101_btn_buscar").focus();
		}
	});

	$(gridP103.nameJQuery+"_numeroCajasRecepcionadas").unbind('click');
	$(gridP103.nameJQuery+"_numeroCajasRecepcionadas").bind("click", function(e) {
		if(validacionNumeroCajasRecepcionadasP103(document.activeElement.id, 'N') == 'S' && validacionTotalUnidadesRecepcionadasP103(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionNumeroCajasRecepcionadasP103(document.activeElement.id, 'S');
			validacionTotalUnidadesRecepcionadasP103(document.activeElement.id, 'S');
			
			actualizarSortOrderP103('numeroCajasRecepcionadas');

			$(gridP103.nameJQuery).setGridParam({sortname:'numeroCajasRecepcionadas'});
			$(gridP103.nameJQuery).setGridParam({page:1});
			reloadData_gridP103DarEntrada();
		}else{
			$("#p101_btn_buscar").focus();
		}
	});

	$(gridP103.nameJQuery+"_totalBandejasPedidas").unbind('click');
	$(gridP103.nameJQuery+"_totalBandejasPedidas").bind("click", function(e) {
		if(validacionNumeroCajasRecepcionadasP103(document.activeElement.id, 'N') == 'S' && validacionTotalUnidadesRecepcionadasP103(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionNumeroCajasRecepcionadasP103(document.activeElement.id, 'S');
			validacionTotalUnidadesRecepcionadasP103(document.activeElement.id, 'S');
			
			actualizarSortOrderP103('totalBandejasPedidas');

			$(gridP103.nameJQuery).setGridParam({sortname:'totalBandejasPedidas'});
			$(gridP103.nameJQuery).setGridParam({page:1});
			reloadData_gridP103DarEntrada();
		}else{
			$("#p101_btn_buscar").focus();
		}
	});

	$(gridP103.nameJQuery+"_totalBandejasRecepcionadas").unbind('click');
	$(gridP103.nameJQuery+"_totalBandejasRecepcionadas").bind("click", function(e) {
		if(validacionNumeroCajasRecepcionadasP103(document.activeElement.id, 'N') == 'S' && validacionTotalUnidadesRecepcionadasP103(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionNumeroCajasRecepcionadasP103(document.activeElement.id, 'S');
			validacionTotalUnidadesRecepcionadasP103(document.activeElement.id, 'S');
			
			actualizarSortOrderP103('totalBandejasRecepcionadas');

			$(gridP103.nameJQuery).setGridParam({sortname:'totalBandejasRecepcionadas'});
			$(gridP103.nameJQuery).setGridParam({page:1});
			reloadData_gridP103DarEntrada();
		}else{
			$("#p101_btn_buscar").focus();
		}
	});

	$(gridP103.nameJQuery+"_totalUnidadesPedidas").unbind('click');
	$(gridP103.nameJQuery+"_totalUnidadesPedidas").bind("click", function(e) {
		if(validacionNumeroCajasRecepcionadasP103(document.activeElement.id, 'N') == 'S' && validacionTotalUnidadesRecepcionadasP103(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionNumeroCajasRecepcionadasP103(document.activeElement.id, 'S');
			validacionTotalUnidadesRecepcionadasP103(document.activeElement.id, 'S');
			
			actualizarSortOrderP103('totalUnidadesPedidas');

			$(gridP103.nameJQuery).setGridParam({sortname:'totalUnidadesPedidas'});
			$(gridP103.nameJQuery).setGridParam({page:1});
			reloadData_gridP103DarEntrada();
		}else{
			$("#p101_btn_buscar").focus();
		}
	});

	$(gridP103.nameJQuery+"_totalUnidadesRecepcionadas").unbind('click');
	$(gridP103.nameJQuery+"_totalUnidadesRecepcionadas").bind("click", function(e) {
		if(validacionNumeroCajasRecepcionadasP103(document.activeElement.id, 'N') == 'S' && validacionTotalUnidadesRecepcionadasP103(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionNumeroCajasRecepcionadasP103(document.activeElement.id, 'S');
			validacionTotalUnidadesRecepcionadasP103(document.activeElement.id, 'S');
			
			actualizarSortOrderP103('totalUnidadesRecepcionadas');

			$(gridP103.nameJQuery).setGridParam({sortname:'totalUnidadesRecepcionadas'});
			$(gridP103.nameJQuery).setGridParam({page:1});
			reloadData_gridP103DarEntrada();
		}else{
			$("#p101_btn_buscar").focus();
		}
	});

	$(gridP103.nameJQuery+"_mensaje").unbind('click');
	$(gridP103.nameJQuery+"_mensaje").bind("click", function(e) {
		if(validacionNumeroCajasRecepcionadasP103(document.activeElement.id, 'N') == 'S' && validacionTotalUnidadesRecepcionadasP103(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionNumeroCajasRecepcionadasP103(document.activeElement.id, 'S');
			validacionTotalUnidadesRecepcionadasP103(document.activeElement.id, 'S');
			
			actualizarSortOrderP103('mensaje');

			$(gridP103.nameJQuery).setGridParam({sortname:'mensaje'});
			$(gridP103.nameJQuery).setGridParam({page:1});
			reloadData_gridP103DarEntrada();
		}else{
			$("#p101_btn_buscar").focus();
		}
	});
}

function actualizarSortOrderP103(columna) {

	var ultimoSortName = $(gridP103.nameJQuery).jqGrid('getGridParam','sortname');
	var ultimoSortOrder = $(gridP103.nameJQuery).jqGrid('getGridParam','sortorder');

	if (ultimoSortName != columna ) { //Se ha cambiado la columna por la que se quiere ordenar
		$(gridP103.nameJQuery).setGridParam({sortorder:'asc'});
		$(gridP103.nameJQuery + "_" + columna + " [sort='asc']").removeClass("ui-state-disabled");

		//En la columna anterior quitamos la flechas
		if (ultimoSortOrder=='asc'){
			$(gridP103.nameJQuery + "_" + ultimoSortName + " [sort='asc']").addClass("ui-state-disabled");
		}else{
			$(gridP103.nameJQuery + "_" + ultimoSortName + " [sort='desc']").addClass("ui-state-disabled");
		}
	} else { //Seguimos en la misma columna
		if (ultimoSortOrder=='asc'){
			$(gridP103.nameJQuery).setGridParam({sortorder:'desc'});
			//Mostramos la flecha descendente y quitamos la flecha ascendente
			$(gridP103.nameJQuery + "_" + columna + " [sort='desc']").removeClass("ui-state-disabled");
			$(gridP103.nameJQuery + "_" + columna + " [sort='asc']").addClass("ui-state-disabled");
		} else {
			$(gridP103.nameJQuery).setGridParam({sortorder:'asc'});
			$(gridP103.nameJQuery + "_" + columna + " [sort='asc']").removeClass("ui-state-disabled");
			$(gridP103.nameJQuery + "_" + columna + " [sort='desc']").addClass("ui-state-disabled");
		}  		
	}
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************EVENTOS CAMPOS  DEL GRID ********************************************************/
/****************************************************  TABULACIONES, FLECHAS, ENTERS, ETC. ***************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

function controlNavegacionP103(e) {
	var idActual = e.target.id;
	var idFoco;

	//Obtención de fila y columna actuales
	var fila = idActual.substring(0, idActual.indexOf("_"));
	var nombreColumna = idActual.substring(idActual.indexOf("_")+1);
	var validacionNavegacion = 'S';

	var key = e.which; //para soporte de todos los navegadores
	if (key == 13){//Tecla enter, guardado
		e.preventDefault();

		if(nombreColumna == "totalUnidadesRecepcionadas"){
			validacionNavegacion = validacionTotalUnidadesRecepcionadasP103(fila + "_totalUnidadesRecepcionadas", 'N');
			if (validacionNavegacion == 'S'){

				//Se llama a la función directamente, porque en esta función se calcula el elemento modificado, que indica si se ha modificado un
				//elemento del grid. En el caso de haberse modificado un elemento, se actualizan las líneas. Anteriormente, se hacía un focusout para
				//que saltara esta misma función, pero al ejecutar focusout, ejecutaba actualizarEntradaLineas antes que validacionTotalUnidadesRecepcionadasP103 se ejecutara, por lo que
				//no daba tiempo a calcular que el elemento se había modificado y no se actualizaba la línea.
				validacionTotalUnidadesRecepcionadasP103(this.id, 'S');

				//Guarda los datos modificados
				actualizarEntradaLineasP103();

			}else{
				$("#p101_btn_buscar").focus();	
			}
		}
		if(nombreColumna == "numeroCajasRecepcionadas"){
			validacionNavegacion = validacionNumeroCajasRecepcionadasP103(fila + "_numeroCajasRecepcionadas", 'N');
			if (validacionNavegacion =='S'){

				//Se llama a la función directamente, porque en esta función se calcula el elemento modificado, que indica si se ha modificado un
				//elemento del grid. En el caso de haberse modificado un elemento, se actualizan las líneas. Anteriormente, se hacía un focusout para
				//que saltara esta misma función, pero al ejecutar focusout, ejecutaba actualizarEntradaLineasP103 antes que validacionBulto se ejecutara, por lo que
				//no daba tiempo a calcular que el elemento se había modificado y no se actualizaba la línea.
				validacionNumeroCajasRecepcionadasP103(this.id, 'S');

				//Guarda los datos modificados
				actualizarEntradaLineasP103();
			}else{
				$("#p101_btn_buscar").focus();	        	
			}
		}
	}

	//Flechas de cursores para navegación	
	//Tecla izquierda
	if (key == 37){
		e.preventDefault();

		if(nombreColumna == "totalUnidadesRecepcionadas"){
			idFoco = fila + "_numeroCajasRecepcionadas";
			validacionNavegacion = validacionTotalUnidadesRecepcionadasP103(fila + "_totalUnidadesRecepcionadas", 'N');
			if (validacionNavegacion == 'S'){
				$("#"+idFoco).focus();
				$("#"+idFoco).select();
				$("#p103_fld_totalUnidadesRecepcionadas_Selecc").val(idFoco);
				controlClickNumeroCajasRecepcionadasP103(e,key);
			}else{
				$("#p101_btn_buscar").focus();	        	
			}
		}
	}

	//Tecla derecha
	if (key == 39){
		e.preventDefault();
		if(nombreColumna == "numeroCajasRecepcionadas"){
			idFoco = fila + "_totalUnidadesRecepcionadas";
			validacionNavegacion = validacionNumeroCajasRecepcionadasP103(fila + "_numeroCajasRecepcionadas", 'N');
			if (validacionNavegacion=='S'){
				$("#"+idFoco).focus();
				$("#"+idFoco).select();
				$("#p103_fld_totalUnidadesRecepcionadas_Selecc").val(idFoco);
				controlClickTotalUnidadesRecepcionadasP103(e,key);
			}else{
				$("#p101_btn_buscar").focus();
			}
		}    	  	
	}

	//Tecla arriba
	if (key == 38){
		e.preventDefault();
		idFoco = (parseInt(fila,10)-1) + "_" + nombreColumna;
		if(nombreColumna == "numeroCajasRecepcionadas"){
			validacionNavegacion = validacionNumeroCajasRecepcionadasP103(fila + "_numeroCajasRecepcionadas", 'N');
		}
		if(nombreColumna == "totalUnidadesRecepcionadas"){
			validacionNavegacion = validacionTotalUnidadesRecepcionadasP103(fila + "_totalUnidadesRecepcionadas", 'N');
		}
		if (validacionNavegacion=='S'){    		    			    		    		
			$("#"+idFoco).focus();
			$("#"+idFoco).select();
			$("#p103_fld_totalUnidadesRecepcionadas_Selecc").val(idFoco);  

			if(nombreColumna == "numeroCajasRecepcionadas"){
				controlClickNumeroCajasRecepcionadasP103(e,key);
			}
			if(nombreColumna == "totalUnidadesRecepcionadas"){
				controlClickTotalUnidadesRecepcionadasP103(e,key);
			}
		}else{
			$("#p101_btn_buscar").focus();   	
		}
	}

	//Tecla abajo
	if (key == 40){
		e.preventDefault();
		idFoco = (parseInt(fila,10)+1) + "_" + nombreColumna;

		if(nombreColumna == "numeroCajasRecepcionadas"){
			validacionNavegacion = validacionNumeroCajasRecepcionadasP103(fila + "_numeroCajasRecepcionadas", 'N');
		}
		if(nombreColumna == "totalUnidadesRecepcionadas"){
			validacionNavegacion = validacionTotalUnidadesRecepcionadasP103(fila + "_totalUnidadesRecepcionadas", 'N');
		}

		if (validacionNavegacion=='S'){    		    			
			$("#"+idFoco).focus();
			$("#"+idFoco).select();
			$("#p103_fld_totalUnidadesRecepcionadas_Selecc").val(idFoco);
			if(nombreColumna == "numeroCajasRecepcionadas"){
				controlClickNumeroCajasRecepcionadasP103(e,key);
			}
			if(nombreColumna == "totalUnidadesRecepcionadas"){
				controlClickTotalUnidadesRecepcionadasP103(e,key);
			}
		}else{
			$("#p101_btn_buscar").focus();
		}
	}
	//Tecla tabulacion
	if(key == 9){
		e.preventDefault();

		//Si se presiona tabulación más shift
		if(e.shiftKey){
			//Si estamos en numeroCajasRecepcionadas, volvemos a la fila anterior a la sección totalUnidadesRecepcionadas. Si estamos en numeroCajasRecepcionadas, en la misma fila, volvemos a stockDevuelto.
			if(nombreColumna == "numeroCajasRecepcionadas"){
				idFoco = (parseInt(fila,10)-1) + "_totalUnidadesRecepcionadas";
			}else{
				idFoco = fila + "_numeroCajasRecepcionadas";
			}
		}else{
			//Si estamos en totalUnidadesRecepcionadas, en la misma fila, vamos a numeroCajasRecepcionadas. Si estamos en numeroCajasRecepcionadas, vamos a la fila siguiente a totalUnidadesRecepcionadas.
			if(nombreColumna == "numeroCajasRecepcionadas"){
				idFoco = fila + "_totalUnidadesRecepcionadas";
			}else{
				idFoco = (parseInt(fila,10) + 1) + "_numeroCajasRecepcionadas";
				controlClickTotalUnidadesRecepcionadasP103(e,key);
			}								
		}

		if(nombreColumna == "totalUnidadesRecepcionadas"){
			validacionNavegacion = validacionTotalUnidadesRecepcionadasP103(fila + "_totalUnidadesRecepcionadas", 'N');
			if (validacionNavegacion =='S'){ 
				$("#"+idFoco).focus();
				$("#"+idFoco).select();
				$("#p103_fld_totalUnidadesRecepcionadas_Selecc").val(idFoco);

				controlClickNumeroCajasRecepcionadasP103(e,key);
			}else{
				$("#p101_btn_buscar").focus();
			}
		}
		if(nombreColumna == "numeroCajasRecepcionadas"){
			validacionNavegacion = validacionNumeroCajasRecepcionadasP103(fila + "_bulto", 'N');
			if (validacionNavegacion =='S'){
				$("#"+idFoco).focus();
				$("#"+idFoco).select();
				$("#p103_fld_totalUnidadesRecepcionadas_Selecc").val(idFoco);
				controlClickTotalUnidadesRecepcionadasP103(e,key);
			}else{
				$("#p101_btn_buscar").focus();	        	
			}
		}
	}
}

function controlDatoCajasRecepcionadasP103(e){
	//Obtenemos la fila modificada.
	var fila = event.target.id.substring(0, event.target.id.indexOf("_"));

	//Si hemos introducido en cajas recepcionadas un numérico, 
	//actualizamos las unidades recepcionadas con: 
	//total unidades recepcionadas = (cajas recepcionadas * u/c)
	
	//Si se inserta un numérico o se elimina un campo, se borra el area de resultados.
	var isNumber = /^[0-9]$/i.test(e.key);
	if(isNumber || e.keyCode == 46 || e.keyCode == 8){
		//total unidades recepcionadas = (cajas recepcionadas * u/c)
		var valorCajasRecepcionadas = $(this).val() != "" ? parseInt($(this).val()) : 0;
		var valorUc = parseInt($("#gridP103DarEntrada").find("#"+fila).find("[aria-describedby='gridP103DarEntrada_uc']").html());
		var valorTotalUnidadesRecepcionadas = valorCajasRecepcionadas * valorUc;
			
		//Lo pintamos en la columna Total unidades recepcionadas correspondiente y tambien en el hidden del campo totalUnidadesRecepcionadas.
		$("#"+fila+"_totalUnidadesRecepcionadas").val(valorTotalUnidadesRecepcionadas);
		$("#"+fila+"_totalUnidadesRecepcionadas_tmp").val(valorTotalUnidadesRecepcionadas);
	}
}

function controlClickNumeroCajasRecepcionadasP103(e,key){
	/*var idActual = e.target.id;

	//Obtención de fila y columna actuales
	var fila = idActual.substring(0, idActual.indexOf("_"));
	//Si se ha pulsado la flecha de arriba, la fila seleccionada será la actual - 1
	//Si se ha pulsado la flecha de abajo, la fila seleccionada será la actual + 1
	if(key == 38 || e.shiftKey){
		fila = parseInt(fila) - 1;
	}else if(key == 40){
		fila = parseInt(fila) + 1;
	}	

	var filaNumeroCajasRecepcionadasActual = "#"+fila+"_numeroCajasRecepcionadas";
	var filaTotalUnidadesRecepcionadasActual = "#"+fila+"_totalUnidadesRecepcionadas";
	var valorTotalUnidadesRecepcionadasActual = $(filaTotalUnidadesRecepcionadasActual).val();

	if (valorIdActual == null && valorStockDevueltoActual != "" && valorStockDevueltoActual != "0") {
		var valorIdActual = $(filaNumeroCajasRecepcionadasActual).val();
		if(valorIdActual == "" || valorIdActual == null || valorIdActual == "null"){
			$(filaNumeroCajasRecepcionadasActual).val(ultimoBultoIntroducidoP103[provrGenFilaValor]);
			$(filaNumeroCajasRecepcionadasActual).select();
		}		
	}else if (valorStockDevueltoActual == "0") {
		var valorIdActual = $(filaBultoActual).val();
		$(filaBultoActual).val("0");
	}*/
}


function controlClickTotalUnidadesRecepcionadasP103(e,key){
	/*var idActual = e.target.id;

	//Obtención de fila y columna actuales
	var fila = idActual.substring(0, idActual.indexOf("_"));
	//Si se ha pulsado la flecha de arriba, la fila seleccionada será la actual - 1
	//Si se ha pulsado la flecha de abajo, la fila seleccionada será la actual + 1
	if(key == 38 || e.shiftKey){
		fila = parseInt(fila) - 1;
	}else if(key == 40 || key == 9){
		fila = parseInt(fila) + 1;
	}
	var provrGenFila = "#"+fila+"_provrGenLin";
	var provrGenFilaValor = $(provrGenFila).val();

	var filaBultoActual = "#"+fila+"_bulto";
	var filaStockDevueltoActual = "#"+fila+"_stockDevuelto";
	var valorStockDevueltoActual = $(filaStockDevueltoActual).val();

	if (valorStockDevueltoActual != "" && valorStockDevueltoActual != "0") {
		var valorIdActual =  $(filaBultoActual).val();
		if(valorIdActual == "" || valorIdActual == null || valorIdActual == "null"){
			//Se inserta en la fila actual el valor de la fila anterior
			$(filaBultoActual).val(ultimoBultoIntroducidoP103[provrGenFilaValor]);

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
	}*/
}
/*function controlFocusColumnaNumeroCajasRecepcionadasP103(){
	//Obtención de fila y columna actuales
	var fila = idActual.substring(0, idActual.indexOf("_"));

	var filaNumeroCajasRecepcionadasActual = "#"+fila+"_numeroCajasRecepcionadas";
	var filaTotalUnidadesRecepcionadasActual = "#"+fila+"_totalUnidadesRecepcionadas";
	var valorTotalUnidadesRecepcionadasActual = $(filaTotalUnidadesRecepcionadasActual).val();

	var provrGenFila = "#"+fila+"_provrGenLin";
	var provrGenFilaValor = $(provrGenFila).val();


	if (valorStockDevueltoActual != null && valorStockDevueltoActual != "" && valorStockDevueltoActual != "0") {
		var valorIdActual =  $(filaBultoActual).val();
		if(valorIdActual == "" || valorIdActual == null || valorIdActual == "null"){
			//Se inserta en la fila actual el valor de la fila anterior
			$(filaBultoActual).val(ultimoBultoIntroducidoP103[provrGenFilaValor]);
		}						
	}else if (valorStockDevueltoActual == "0"){
		$(filaBultoActual).val("0");
	}	
}*/

function controlChangeColumnaTotalUnidadesRecepcionadasP103(){

}

function controlChangeColumnaNumeroCajasRecepcionadasP103(){

}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************           VALIDACIONES DE LOS EDITABLES        *********************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

function validacionNumeroCajasRecepcionadasP103(id, mostrarAlerta){
	var resultadoValidacionP103 = 'S';

	if(mostrarAlerta == "S"){

		//Se obtiene el valor introducido por el usuario
		var campoActual = $("#"+id).val();

		//Se sustitullen las comas por los puntos
		var campoActualPunto = $("#"+id).val().replace(',','.');

		//Se obtiene la fila seleccionada
		var fila = id.substring(0, id.indexOf("_"));

		//Como en principio no ha ocurrido ningún error, el error es 0
		var error = 0;
		var descErrorP103 = '';

		//Se obtienen las id de los input que contienen el campo original devuelto por el controlador, el actual y el temporal
		var campoNumeroCajasRecepcionadasActual = fila + "_numeroCajasRecepcionadas";
		var campoNumeroCajasRecepcionadasOrig = fila + "_numeroCajasRecepcionadas_orig";
		var campoNumeroCajasRecepcionadasTmp = fila + "_numeroCajasRecepcionadas_tmp";

		//Campo que contiene el código de error equivalente a la imágen del disquete, error, etc.
		var campoErrorOrig = fila + "_numeroCajasRecepcionadasTotalUnidadesRecepcionadas_codError_orig";

		//Div que equivale a la columna Estado de la fila seleccionada.
		//Estos div contienen las imágenes del disquete, ficha y de aviso 
		var campoDivModificado = fila + "_numeroCajasRecepcionadasTotalUnidadesRecepcionadas_divModificado";
		var campoDivGuardado = fila + "_numeroCajasRecepcionadasTotalUnidadesRecepcionadas_divGuardado";

		//En este caso la validación devuelve siempre si, pero hay que mirar que haya modificación
		if(($("#"+campoNumeroCajasRecepcionadasTmp).val() != null) && $("#"+campoNumeroCajasRecepcionadasTmp).val() != campoActual){

			//En este caso se ha modificado el campo y hay que establecer el icono de modificacion
			$("#"+campoDivGuardado).hide();
			$("#"+campoDivModificado).show();
			$("#"+campoNumeroCajasRecepcionadasTmp).val(campoActual);

			//El código de error de la fila será 9 que equivale a modificado
			$("#"+campoErrorOrig).val("9");

			//Añadimos la fila al array.
			addSeleccionadosP103(fila);

			//Insertamos el elemento de los array de modificados
			var valor = $("#gridP103DarEntrada #"+fila+" [aria-describedby='gridP103DarEntrada_rn']").text();
			if(modificadosP103.indexOf(valor+"_numeroCajasRecepcionadas") === -1){ 
				modificadosP103.push(valor+"_numeroCajasRecepcionadas");
			}
		}
	}
	return resultadoValidacionP103;
}

function validacionTotalUnidadesRecepcionadasP103(id, mostrarAlerta){
	//La validación antes de ningún tratamiento es satisfactoria, después puede no serlo
	resultadoValidacionP103 = 'S';

	//Este if se encarga de que se calcule la validación si venimos de un campo totalUnidadesRecepcionadas. Se ha introducido para que se puedan realizar validaciones en ordenaciones de columnas
	//del método cargarOrdenacionesColumnasP103(). Hasta ahora, siempre que llegaba a esta función era por estar tratando el campo totalUnidadesRecepcionadas. En las reordenaciones de columnas, puede
	//que se clique la columna viniendo de un campo editable de totalUnidadesRecepcionadas o de cualquier otra parte de la pantalla. Por eso se ha introducido este control.
	if(id.indexOf("_totalUnidadesRecepcionadas") != -1){
		//Se obtiene el valor introducido por el usuario
		var campoActual = $("#"+id).val();

		//Se sustitullen las comas por los puntos
		var campoActualPunto = $("#"+id).val().replace(',','.');

		//Se obtiene la fila seleccionada
		var fila = id.substring(0, id.indexOf("_"));

		//Como en principio no ha ocurrido ningún error, el error es 0
		var error = 0;
		var descErrorP103 = '';

		//Se obtienen las id de los input que contienen el campo original devuelto por el controlador, el actual y el temporal
		var campoTotalUnidadesRecepcionadasActual = fila + "_totalUnidadesRecepcionadas";
		var campoTotalUnidadesRecepcionadasOrig = fila + "_totalUnidadesRecepcionadas_orig";
		var campoTotalUnidadesRecepcionadasTmp = fila + "_totalUnidadesRecepcionadas_tmp";

		var campoErrorOrig = fila + "_numeroCajasRecepcionadasTotalUnidadesRecepcionadas_codError_orig";

		//Div que equivale a la columna Estado de la fila seleccionada.
		//Estos div contienen las imágenes del disquete, ficha y de aviso 
		var campoDivModificado = fila + "_numeroCajasRecepcionadasTotalUnidadesRecepcionadas_divModificado";
		var campoDivGuardado = fila + "_numeroCajasRecepcionadasTotalUnidadesRecepcionadas_divGuardado";
		var campoDivError = fila + "_numeroCajasRecepcionadasTotalUnidadesRecepcionadas_divError";

		//Contiene el id de la imagen del div campoDivError. De esta forma, al posicional el ratón encima
		//sale la causa del error
		var campoImgError = fila + "_numeroCajasRecepcionadasTotalUnidadesRecepcionadas_imgError";

		//En este caso 
		if (mostrarAlerta == 'S'){
			if(campoActual != ""){
				//Si hay más de una coma o el campo introducido es vacío mostrar el error
				if (campoActual.split(",").length > 2){
					descErrorP103 = replaceSpecialCharacters(totalUnidadesRecepcionadasIncorrectoP103);
					createAlert(descErrorP103, "ERROR");

					//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
					$("#p103_fld_totalUnidadesRecepcionadas_Selecc").val(fila + "_totalUnidadesRecepcionadas");

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
							descErrorP103 = replaceSpecialCharacters(totalUnidadesRecepcionadasEnteroErroneoP103 +" "+ maxNumEnteros + " " + totalUnidadesRecepcionadasDecimalErroneoP103 +" "+ maxNumDecimales +" "+ totalUnidadesRecepcionadasDecimalErroneoP1032);
							createAlert(descErrorP103, "ERROR");

							//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
							$("#p103_fld_totalUnidadesRecepcionadas_Selecc").val(fila + "_totalUnidadesRecepcionadas");

							//Pintamos de rojo el campo.
							$("#"+id).removeClass("editable").addClass("editableError");

							//El error se cambia a 1 para realizar su tratamiento
							error = 1;
						}
					}else{
						if(numerosEnteros.length > maxNumEnteros){
							descErrorP103 = replaceSpecialCharacters(totalUnidadesRecepcionadasEnteroErroneoP103 +" "+ maxNumEnteros + " " + totalUnidadesRecepcionadasDecimalErroneoP103 +" "+ maxNumDecimales +" "+ totalUnidadesRecepcionadasDecimalErroneoP1032);
							createAlert(descErrorP103, "ERROR");

							//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
							$("#p103_fld_totalUnidadesRecepcionadas_Selecc").val(fila + "_totalUnidadesRecepcionadas");

							//Pintamos de rojo el campo.
							$("#"+id).removeClass("editable").addClass("editableError");

							//El error se cambia a 1 para realizar su tratamiento
							error = 1;
						}
					}
				}

				// validacion del maximo y la suma
				/*if(!error && !validateMaximoCambioStockP103(id, gridP103.nameJQuery)) {
					createAlert(sumaSuperaMaximoP103, "ERROR");
					error = 1;
				}*/

				//Miramos que el dato original no sea ""
				var campoTotalUnidadesRecepcionadasOrigPunto = "";
				if($("#"+campoTotalUnidadesRecepcionadasOrig).val() != "" && $("#"+campoTotalUnidadesRecepcionadasOrig).val() != "null" && $("#"+campoTotalUnidadesRecepcionadasOrig).val() != null){
					campoTotalUnidadesRecepcionadasOrigPunto = $("#"+campoTotalUnidadesRecepcionadasOrig).val().replace(',','.');
				}

				if (error == 1){
					//En este caso ha ocurrido un error y hay que mostrar el icono de error.
					$("#"+campoDivGuardado).hide();
					$("#"+campoDivModificado).hide();
					$("#"+campoDivError).show();

					//Cambiamos el title
					$("#"+campoImgError).attr('title', descErrorP103);

					//Añadimos la fila al array.
					addSeleccionadosP103(fila);

					//Eliminamos el elemento de los array de modificados, al haber un error y recuperar sus datos por defecto.
					var valor = $("#gridP103DarEntrada #"+fila+" [aria-describedby='gridP103DarEntrada_rn']").text();
					if(modificadosP103.indexOf(valor+"_totalUnidadesRecepcionadas") != -1){ 
						var index = modificadosP103.indexOf(valor+"_totalUnidadesRecepcionadas");
						modificadosP103.splice(index,1);
					}

					if(modificadosP103.indexOf(valor+"_numeroCajasRecepcionadas") != -1){ 
						var index = modificadosP103.indexOf(valor+"_numeroCajasRecepcionadas");
						modificadosP103.splice(index,1);
					}

					//Si el campo original es distinto a "" devolvemos el número que nos llega del controlador.
					//Si no devolvemos "".
					if(campoTotalUnidadesRecepcionadasOrigPunto != ""){
						//Cargamos los valores anteriores a la modificación.
						$("#"+id).val(campoTotalUnidadesRecepcionadasOrigPunto).formatNumber({format:"0.###",locale:"es"});
						$("#"+campoTotalUnidadesRecepcionadasTmp).val(campoTotalUnidadesRecepcionadasOrigPunto).formatNumber({format:"0.###",locale:"es"});
					}else{
						$("#"+id).val("");
						$("#"+campoTotalUnidadesRecepcionadasTmp).val("");
					}				
					//Inicializamos el bulto también
					var valorNumeroCajasRecepcionadasOrig = $("#"+fila+"_numeroCajasRecepcionadas_orig").val();
					$("#"+fila+"_numeroCajasRecepcionadas_tmp").val(valorNumeroCajasRecepcionadasOrig);
					$("#"+fila+"_numeroCajasRecepcionadas").val(valorNumeroCajasRecepcionadasOrig);

					// recalculamos y asignamos el valor de la suma ahora que hemos modificado el valor del stock
					//controlChangeColumnaTotalUnidadesRecepcionadasP103(id, gridP103.nameJQuery);


					//El código de error de la fila será 0 que equivale a un error
					$("#"+campoErrorOrig).val("0");

					//Sirve para el onPaging del grid.
					resultadoValidacionP103 = 'N';
				}else{
					//No hay error con lo que quitamos el posible icono de error.
					$("#"+campoDivError).hide();
					$("#"+id).removeClass("editableError").addClass("editable");

					var campoActualFormatter = $.formatNumber(campoActual.replace(',','.'),{format:"0.###",locale:"es"});
					$("#"+campoTotalUnidadesRecepcionadasActual).val(campoActualFormatter);
					if (($("#"+campoTotalUnidadesRecepcionadasTmp).val() != null) && $("#"+campoTotalUnidadesRecepcionadasTmp).val() != campoActualFormatter){
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
						$("#"+campoTotalUnidadesRecepcionadasTmp).val(campoActualFormatter);

						//El código de error de la fila será 9 que equivale a modificado
						$("#"+campoErrorOrig).val("9");

						//Añadimos la fila al array.
						addSeleccionadosP103(fila);

						//Insertamos el elemento de los array de modificados
						var valor = $("#gridP103DarEntrada #"+fila+" [aria-describedby='gridP103DarEntrada_rn']").text();
						if(modificadosP103.indexOf(valor+"_totalUnidadesRecepcionadas") === -1){ 							
							modificadosP103.push(valor+"_totalUnidadesRecepcionadas");
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
				if($("#"+campoTotalUnidadesRecepcionadasTmp).val() != campoActual){
					$("#"+campoDivError).hide();
					$("#"+campoDivGuardado).hide();
					$("#"+campoDivModificado).show();

					$("#"+campoTotalUnidadesRecepcionadasTmp).val(campoActual);

					addSeleccionadosP103(fila);
					//Insertamos el elemento de los array de modificados
					var valor = $("#gridP103DarEntrada #"+fila+" [aria-describedby='gridP103DarEntrada_rn']").text();
					if(modificadosP103.indexOf(valor+"_totalUnidadesRecepcionadas") === -1){ 							
						modificadosP103.push(valor+"_totalUnidadesRecepcionadas");
					}

					$("#"+campoErrorOrig).val("9");
				}
			}
		}else{
			// Cuando no hay que sacar mensajes
			//Control de campo decimal erroneo
			if (campoActual.split(",").length > 2){
				resultadoValidacionP103 = 'N';
			}else if(campoActual != ""){
				var numerosEnteros = campoActual.split(",")[0];
				var numerosDecimales = campoActual.split(",")[1];

				if(numerosDecimales && numerosDecimales.length > maxNumDecimales ||
						numerosEnteros && numerosEnteros.length > maxNumEnteros) {
					resultadoValidacionP103 = 'N';
				}else{
					var campoActualPunto = $("#"+id).val().replace(',','.');
					$("#"+id).val(campoActualPunto).formatNumber({format:"0.###",locale:"es"});
				}
			}
		}
	}
	return resultadoValidacionP103;
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/*************************************      GESTIONA LOS CAMPOS, COLUMNAS, ETC VISIBLES O NO SEGÚN EL ROL    *********************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
function gestionarPerfil(){
	if(userPerfilMisumi == CONS_CONSULTA_ROLE){
		//Ocultamos los input del grid.
		$(gridP103.nameJQuery).jqGrid('setColProp', 'numeroCajasRecepcionadas', {editable:false});
		$(gridP103.nameJQuery).jqGrid('setColProp', 'totalUnidadesRecepcionadas', {editable:false});
		
		//Ocultamos los campos de cabecera de input
		$("#p103_areaCabecera_datepicker_fechaEntrada").hide();
		$("#p103_areaCabecera_albaran").hide();
		$("#p103_areaCabecera_numeroIncidencia").hide();
		
		//Ocultamos el botón de guardar
		$("#p103_btn_guardado").hide();
		
		//Mostramos los lbl de cabecera
		$("#p103_lbl_areaCabecera_fechaEntrada").show();
		$("#p103_lbl_areaCabecera_albaran").show();
		$("#p103_lbl_areaCabecera_numeroIncidencia").show();
	}else{
		//Mostramos los input del grid.
		$(gridP103.nameJQuery).jqGrid('setColProp', 'numeroCajasRecepcionadas', {editable:true});
		$(gridP103.nameJQuery).jqGrid('setColProp', 'totalUnidadesRecepcionadas', {editable:true});
		
		//Mostramos los campos de cabecera de input
		$("#p103_areaCabecera_datepicker_fechaEntrada").show();
		$("#p103_areaCabecera_albaran").show();
		$("#p103_areaCabecera_numeroIncidencia").show();
		
		//Ocultamos el botón de guardar
		$("#p103_btn_guardado").show();
		
		//Ocultamos los lbl de cabecera
		$("#p103_lbl_areaCabecera_fechaEntrada").hide();
		$("#p103_lbl_areaCabecera_albaran").hide();
		$("#p103_lbl_areaCabecera_numeroIncidencia").hide();
	}
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/*********************************************      MOSTRADO DE FOTOS AL PASAR POR LOS EDITABLES    ******************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Función que sirve para que las fotos se muestren al pasar por encima de ciertas celdas de las columnas
function generateTooltipP103(data){
	var ids = jQuery(gridP103.nameJQuery).jqGrid('getDataIDs'), i, l = ids.length;	
	var colModel = $(gridP103.nameJQuery).jqGrid("getGridParam", "colModel");
	var rowData = null;
	for (i = 0; i < l; i++) {
		rowData = data.rows[i]
		//Si la referencia tiene foto
		if (rowData.flgFoto == "S"){
			var idArticulo = jQuery(gridP103.nameJQuery).jqGrid('getCell',ids[i],'codArticulo');

			var div = $("<div>");
			var idImg = "img_"+ids[i]+"_fotoTooltip"
			var img = $('<img id="'+idImg+'">'); //Equivalent: $(document.createElement('img'))
			var url = "./welcome/getImage.do?codArticulo="+idArticulo;//+"&flgControlCaprabo=S";
			img.attr('src',url);
			img.addClass("p103_img_tooltip");
			img.appendTo(div);
			img.attr("onerror", "redirigirLogin()");

			$.each(colModel, function(j) {
				var nombreColumna = colModel[j].name;
				var columna = $(gridP103.nameJQuery+" tbody #"+ ids[i]+ " td[aria-describedby='"+gridP103.name+"_"+nombreColumna+"']");
				columna.addClass("contieneTooltipFotoP103");
				columna.attr("title",div.html());
			});
		}
	}
	$(".contieneTooltipFotoP103").tooltip({
		position: {
			my: "left top",
			at: "right+5 bottom+5"
		}
	});
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/****************************************************     TRATAMIENTO LISTADO MODIFICADOS     ************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Esta lista solo contiene las filas modificadas de la página actual, de esta forma, guarda los registros modificadosP103 
//en la temporal según se va reordenando el grid, paginando, etc.
function addSeleccionadosP103(fila){
	seleccionadosP103[fila] = fila;
}

//Mediante esta función se obtienen los cambios de las filas cambiadas en una lista. Luego esta lista se utiliza en el controlador
//para que al paginar que se guarden los estados de modificadosP103, guardados, etc.
function obtenerListadoModificadosP103(){

	//A partir del array de seleccionadosP103 obtenemos el listado de campos modificadosP103 a enviar al controlador.
	var registroListadoModificado = {};
	var valorTotalUnidadesRecepcionadas = "";
	var valorNumeroCajasRecepcionadas= "";
	var valorTotalBandejasRecepcionadas= "";

	//Se obtiene el codigo de articulo para saber qué linea actualizar en la tabla temporal.
	var valorCodArticulo= "";

	for (i = 0; i < seleccionadosP103.length; i++){
		if (seleccionadosP103[i] != null && seleccionadosP103[i] != '')
		{
			//Por cada elemento seleccionado, tenemos que crear el listado de modificados103.
			registroListadoModificado = {};

			//Se obtiene el valor de totalUnidadesRecepcionadas y numeroCajasRecepcionadas
			valorNumeroCajasRecepcionadas = $("#"+ seleccionadosP103[i] + "_numeroCajasRecepcionadas").val() ? $("#"+ seleccionadosP103[i] + "_numeroCajasRecepcionadas").val() : "";
			
			//var valorUc = parseInt($("#gridP103DarEntrada").find("#"+ seleccionadosP103[i]).find("[aria-describedby='gridP103DarEntrada_uc']").html());
			valorTotalUnidadesRecepcionadas = $("#"+ seleccionadosP103[i] + "_totalUnidadesRecepcionadas").val() ? $("#"+ seleccionadosP103[i] + "_totalUnidadesRecepcionadas").val() : "";
			
			valorTotalBandejasRecepcionadas = $("#"+ seleccionadosP103[i] + "_totalBandejasRecepcionadas").val() ? $("#"+ seleccionadosP103[i] + "_totalBandejasRecepcionadas").val() : "";

			//Se obtiene el codigo error para guardar el estado modificado, error o guardado en la tabla temporal
			valorCodError = $("#"+ seleccionadosP103[i] + "_numeroCajasRecepcionadasTotalUnidadesRecepcionadas_codError_orig").val() ? $("#"+ seleccionadosP103[i] + "_numeroCajasRecepcionadasTotalUnidadesRecepcionadas_codError_orig").val() : "";

			//Se obtiene el codigo de articulo para saber qué linea actualizar en la tabla temporal.
			valorCodArticulo = jQuery(gridP103.nameJQuery).jqGrid('getCell',seleccionadosP103[i],'codArticulo');

			//Se rellena el objeto con el error, numeroCajasRecepcionadas y totalUnidadesRecepcionadas. Además se inserta el código de artículo para saber que linea de entrada
			//hay que actualizar.
			registroListadoModificado.codError = valorCodError;
			registroListadoModificado.numeroCajasRecepcionadas =  valorNumeroCajasRecepcionadas;
			registroListadoModificado.totalUnidadesRecepcionadas = valorTotalUnidadesRecepcionadas;
			registroListadoModificado.codArticulo = valorCodArticulo;
			registroListadoModificado.totalBandejasRecepcionadas = valorTotalBandejasRecepcionadas;

			//Insertamos el registro en la tabla.
			listadoModificadosP103.push(registroListadoModificado)
		}	
	}
	//Reseteamos el array de los seleccionadosP103.
	seleccionadosP103 = new Array();
}

//Evento para flechas 
function events_p103_btn_actualizarEntradaLineas(){
	$("#p103_btn_guardado").click(function () {		
		actualizarEntradaLineasP103();
	});	
}


/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************      RESET DEL GRID     ********************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Función que resetea el GRID
function reset_p103(){
	//Iniciar los arrays
	seleccionadosP103 = new Array();
	listadoModificadosP103 = new Array();
	modificadosP103 = new Array();

	//Iniciar el grid y su cantidad de elementos a mostrar
	$(gridP103.nameJQuery).jqGrid('clearGridData');
	$(gridP103.nameJQuery).jqGrid('setGridParam', { rowNum: 10 });
	$("#p103_popUpEntradaDar .ui-pg-selbox").val(10);

	//Quitamos las flechas de ordenación del grid de la columna reordenada para que no se mantengan al abrid un nuevo grid.
	$(gridP103.nameJQuery + "_" + gridP103.getSortIndex() + " [sort='asc']").addClass("ui-state-disabled");
	$(gridP103.nameJQuery + "_" + gridP103.getSortIndex() + " [sort='desc']").addClass("ui-state-disabled");

	//Reseteamos la ordenación del grid
	$(gridP103.nameJQuery).jqGrid('setGridParam', {sortname:'', sortorder: 'asc'});
}