/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/****************************************************************** VARIABLES ****************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
//Guarda el objeto del grid
var gridP117 = null;

//Mensajes json de p101
var noDataForCenter=null;
var noDataForEntry=null;

//En la primera carga se inicia el contador a 1.
var numRow = 1;

//Control de que la pantalla se ha inicializado.
var p101Inicializada = null;

//Guardamos la lista del combo para cuando la filtremos.
var lstCombo = null;

//Valor combobox P103
var comboValueP103 = null;

var iconoGuardado = null;

var noHayRegistrosSeleccionados = null;
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************** DOCUMENTO LISTO **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

$(document).ready(function(){
	loadP117(locale);
	initializeScreenP117();

	//Inicializamos el PopUp
	initializeScreenP118();
});


/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/********************************************************* INICIALIZACIÓN DE PANTALLA ********************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

function initializeScreenP117(){
	//Evento de botón buscar
	events_p117_btn_buscar();

	//Evento de botón borrar
	events_p117_btn_borrar();

	//Evento de botón nuevo
	events_p117_btn_nuevo();
	
}

function AvisosSiec(codAviso, fechaIni, horaIni, fechaFin, horaFin,	mensajePc, mensajePda, flgHiper, flgSuper, flgFranquicia, flgEroski, flgCpb, 
		flgVegalsa, flgMercat, lstBorrar){

	this.codAviso = codAviso;
	this.fechaIni = fechaIni;
	this.horaIni = horaIni;
	this.fechaFin = fechaFin;
	this.horaFin = horaFin;
	this.mensajePc = mensajePc;
	this.mensajePda = mensajePda;
	this.flgHiper = flgHiper;
	this.flgSuper = flgSuper;
	this.flgFranquicia = flgFranquicia;
	this.flgEroski = flgEroski;
	this.flgCpb = flgCpb;
	this.flgVegalsa = flgVegalsa;
	this.flgMercat = flgMercat;
	this.lstBorrar = lstBorrar;

	this.prepareToJsonObject = function(){
		var jsonObject = null;
		jsonObject = {
				'codAviso': this.codAviso,
				'fechaIni': this.fechaIni,
				'horaIni': this.horaIni,
				'fechaFin': this.fechaFin,
				'horaFin': this.horaFin,
				'mensajePc': this.mensajePc,
				'mensajePda': this.mensajePda,
				'flgHiper': this.flgHiper,
				'flgSuper': this.flgSuper,
				'flgFranquicia': this.flgFranquicia,
				'flgEroski': this.flgEroski,
				'flgCpb': this.flgCpb,
				'flgVegalsa': this.flgVegalsa,
				'flgMercat': this.flgMercat,
				'lstBorrar': this.lstBorrar,
		};
		return jsonObject;
	};

};

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************** INICIALIZACIÓN DE ELEMENTOS E IDIOMA *******************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

/************************ IDIOMA ************************/
//Función para buscar los mensajes de la pantalla en idioma seleccionado, por defecto 'es'
function loadP117(locale){
	//Define la estructura del grid, sus columnas de dónde van a obtener los datos etc.
	gridP117 = new GridP117GestionAvisosSiec(locale);

	//Define el ancho del grid.
	$(gridP117.nameJQuery).jqGrid('setGridWidth',950, true);

	//Carga en data los nombres de las colummnas del grid, etc.
	var jqxhr = $.getJSON(gridP117.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		//Carga el nombre de las columnas, titulo, etc.
		gridP117.colNames = data.ColNames;
		gridP117.title = data.GridTitle;
		gridP117.emptyRecords= data.emptyRecords;

		iconoGuardado = data.iconoGuardado;

		noHayRegistrosSeleccionados = data.noHayRegistrosSeleccionados;
		
		//Definimos las columnas, las llamadas al paginar, ordenar etc.
		load_gridP117GestionAvisosSiec(gridP117);

		p117SetHeadersTitles(data);
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

/************************ GRID ************************/
//Clase de constantes para el GRID de parametrización cestas
function GridP117GestionAvisosSiec (locale){
	// Atributos
	this.name = "gridP117GestionAvisosSiec"; 
	this.nameJQuery = "#gridP117GestionAvisosSiec"; 

	this.i18nJSON = './misumi/resources/p117GestionAvisosSiec/p117GestionAvisosSiec_' + locale + '.json';

	this.colNames = null; //Está en el json
	this.cm = [
	           {
	        	   "name"      : "codAviso",
	        	   "index"     : "codAviso",
	        	   "hidden"	   : true,
	        	   "align"     : "left",
	           },{
	        	   "name"      : "fechaIni",
	        	   "index"     : "fechaIni",
	        	   "formatter": fechaFormatter,
	        	   "width"     : 38,
	        	   "resizable":false,
	        	   "align"     : "center"						
	           },{
	        	   "name"      : "horaIni",
	        	   "index"     : "horaIni",
	        	   "width"     : 35,
	        	   "resizable":false,
	        	   "align"     : "center"						
	           },{
	        	   "name"      : "fechaFin",
	        	   "index"     : "fechaFin", 
	        	   "formatter": fechaFormatter,
	        	   "width"     : 38,	
	        	   "resizable":false,
	        	   "align"     : "center"					
	           },{
	        	   "name"      : "horaFin",
	        	   "index"     : "horaFin",
	        	   "width"     : 35,	
	        	   "resizable":false,
	        	   "align"     : "center"						
	           },{
	        	   "name"      : "mensajePc",
	        	   "index"     : "mensajePc",
	        	   "width"     : 80,
	        	   "resizable":false,
	        	   "align"     : "left"
	           },{
	        	   "name"      : "mensajePda",
	        	   "index"     : "mensajePda",
	        	   "width"     : 50,
	        	   "resizable":false,
	        	   "align"     : "left"
	           },{
	        	   "name"      : "flgEroski",
	        	   "index"     : "flgEroski",
	        	   "width"     : 30,
	        	   "formatter" : checkFormatterSN,
	        	   "resizable":false,
	        	   "align"     : "center",

	           },{
	        	   "name"      : "flgCpb",
	        	   "index"     : "flgCpb",
	        	   "width"     : 30,
	        	   "formatter" : checkFormatterSN,
	        	   "resizable":false,
	        	   "align"     : "center",

	           },{
	        	   "name"      : "flgVegalsa",
	        	   "index"     : "flgVegalsa",
	        	   "width"     : 30,
	        	   "formatter" : checkFormatterSN,
	        	   "resizable":false,
	        	   "align"     : "center",

	           },{
	        	   "name"      : "flgMercat",
	        	   "index"     : "flgMercat",
	        	   "width"     : 30,
	        	   "formatter" : checkFormatterSN,
	        	   "resizable":false,
	        	   "align"     : "center",

	           },{
	        	   "name"      : "flgHiper",
	        	   "index"     : "flgHiper",
	        	   "width"     : 30,
	        	   "formatter" : checkFormatterHN,
	        	   "resizable":false,
	        	   "align"     : "center",

	           },{
	        	   "name"      : "flgSuper",
	        	   "index"     : "flgSuper",
	        	   "width"     : 30,
	        	   "formatter" : checkFormatterSN,
	        	   "resizable":false,
	        	   "align"     : "center",

	           },{
	        	   "name"      : "flgFranquicia",
	        	   "index"     : "flgFranquicia",
	        	   "width"     : 30,
	        	   "formatter" : checkFormatterFN,
	        	   "resizable":false,
	        	   "align"     : "center",

	           }	
	           ]; 
	this.locale = locale;
	//this.sortIndex = "";
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerGridp117";  
	this.pagerNameJQuery = "#pagerGridp117";
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

	this.getSelectedRows = function getSelectedRows(){ 
		return $(this.nameJQuery).getGridParam('selarrrow');
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
		var colModel = jQuery(gridP117.nameJQuery).jqGrid('getGridParam', 'colModel'); 
		var i;
		var l = colModel.length;
		var colItem; 
		var cmName;
		var postData = jQuery(gridP117.nameJQuery).jqGrid('getGridParam', 'postData');
		var columnsState = {
				search: jQuery(gridP117.nameJQuery).jqGrid('getGridParam', 'search'),
				page: jQuery(gridP117.nameJQuery).jqGrid('getGridParam', 'page'),
				sortname: jQuery(gridP117.nameJQuery).jqGrid('getGridParam', 'sortname'),
				sortorder: jQuery(gridP117.nameJQuery).jqGrid('getGridParam', 'sortorder'),
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

	this.getRowValue = function getCellValue(rowId){ 
		return $(this.nameJQuery).getRowData(rowId); 
	}
}

//Función que sirve para especificar nombres de columnas, y qué ocurren en las paginaciones, ordenaciones etc.
function load_gridP117GestionAvisosSiec(gridP117) {

	$(gridP117.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		datatype : 'local',
		contentType : 'application/json',
		mtype : 'POST',
		colNames : gridP117.colNames,
		colModel : gridP117.cm,
		rowNum : 10,
		rowList : [ 10, 30, 50 ],
		height : "100%",
		autowidth : true,
		width : "100%",
		rownumbers : gridP117.rownumbers,
		pager : gridP117.pagerNameJQuery,
		viewrecords : true,
		caption : gridP117.title,
		altclass: "ui-priority-secondary",
		altRows: true, //false, para que el grid no muestre cebrado
		hidegrid : false, //false, para ocultar el boton que colapsa el grid.
		sortable : true,
		multiselect: true,
		index: gridP117.sortIndex,
		sortname: gridP117.sortIndex,
		sortorder: gridP117.sortOrder,
		emptyrecords : gridP117.emptyRecords,
		gridComplete : function() {
			gridP117.headerHeight("p83_gridHeader");		

			//Una vez cargado el grid, iniciamos el contador a 1.
			//Al ser una variable global y actualizarse cada vez
			//que se abre un nuevo grid en la función imageFormatterMessage
			//obtiene valores no acordes con el número de fila cargado
			//por eso hay que iniciarlo a 1.
			numRow = 1;
		},
		loadComplete : function(data) {
			gridP117.actualPage = data.page;
			gridP117.localData = data;
			gridP117.sortIndex = gridP117.sortIndex;
			gridP117.sortOrder = gridP117.sortOrder;	

			//Ocultamos la check de seleccionar todos.
			$("#cb_gridP117GestionAvisosSiec").attr("style", "display:none");

			$("#p117_AreaReferencias .loading").css("display", "none");	
		},
		resizeStop: function () {
			gridP117.saveColumnState.call($(this),gridP117.myColumnsState);
			$(gridP117.nameJQuery).jqGrid('setGridWidth', 950, false);
		},
		onPaging : function(postdata) {	
			alreadySorted = false;
			gridP117.sortIndex = gridP117.sortIndex;
			gridP117.sortOrder = gridP117.sortOrder;
			gridP117.saveColumnState.call($(this), this.p.remapColumns);
			loadData_gridP117GestionAvisosSiec();

			return 'stop';
		},beforeSelectRow: function (rowid, e) {
			var $myGrid = $(this),
			i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),
			cm = $myGrid.jqGrid('getGridParam', 'colModel');

			if (!(cm[i].name === 'cb' || cm[i].name === 'rn'))
			{
				p118OpenPopUp(getRowDataByRowId(rowid));
			}

			return true;
		},onSelectRow: function(id){

		},
		onSortCol : function (index, columnIndex, sortOrder){
			gridP117.sortIndex = index;
			gridP117.sortOrder = sortOrder;
			gridP117.saveColumnState.call($(this), this.p.remapColumns);
			loadData_gridP117GestionAvisosSiec();
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

/************************ TITULOS COLUMNAS GRID ************************/
function p117SetHeadersTitles(data){
	var colModel = $(gridP117.nameJQuery).jqGrid("getGridParam", "colModel");
	$.each(colModel, function(i) {
		if (colModel[i].name!="rn"){
			$("#jqgh_gridP117GestionAvisosSiec_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
		}
	});
}

/************************* FORMATEADORES DE COLUMNAS **************************/
function fechaFormatter(cellValue, opts, rData){
	if(cellValue == null || cellValue.toString() == ""){
		return "";
	}else{
		var fecha = cellValue.split("-");
		return fecha[2] + "-" + fecha[1] + "-" + fecha[0]
	}
}

function checkFormatterSN(cellValue, opts, rowObject) {
	var fila = JSON.stringify(rowObject);
	if (cellValue == "S") {
		return "<input id='" + opts.rowId  + "_" + opts.colModel.name + "'' data-value='" + cellValue + "' type='checkbox' checked='checked' title=" + cellValue +" disabled/>";
	}
	return "<input id='" + opts.rowId  + "_" + opts.colModel.name + "' data-value='" + cellValue + "' type='checkbox' title=" + cellValue +" disabled/>";
}

function checkFormatterHN(cellValue, opts, rowObject) {
	var fila = JSON.stringify(rowObject);
	if (cellValue == "H") {
		return "<input id='" + opts.rowId  + "_" + opts.colModel.name + "'' data-value='" + cellValue + "' type='checkbox' checked='checked' title=" + cellValue +" disabled/>";
	}
	return "<input id='" + opts.rowId  + "_" + opts.colModel.name + "' data-value='" + cellValue + "' type='checkbox' title=" + cellValue +" disabled/>";
}

function checkFormatterFN(cellValue, opts, rowObject) {
	var fila = JSON.stringify(rowObject);
	if (cellValue == "F") {
		return "<input id='" + opts.rowId  + "_" + opts.colModel.name + "'' data-value='" + cellValue + "' type='checkbox' checked='checked' title=" + cellValue +" disabled/>";
	}
	return "<input id='" + opts.rowId  + "_" + opts.colModel.name + "' data-value='" + cellValue + "' type='checkbox' title=" + cellValue +" disabled/>";
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************* FUNCIONES DE P117 **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
//Función que sirve para paginar o reordenar los elementos del grid.
function loadData_gridP117GestionAvisosSiec() {
	
	//Poner ancho de grid, para que aunque haya más o menos columnas siempre ocupe lo mismo
	//$(gridP117.nameJQuery).jqGrid('setGridWidth',950,true);

	$.ajax({
		type : 'GET',			
		url : './gestionAvisosSiec/loadDataGrid.do?page='+gridP117.getActualPage()+'&max='+gridP117.getRowNumPerPage()+'&index='+gridP117.getSortIndex()+'&sortorder='+gridP117.getSortOrder(),
		success : function(data) {	
			configurarGrid(data);
			$("#p117_AreaResultados").show();
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});		
}

function getRowDataByRowId(id){
	return gridP117.getRowValue(id);
}

//Función que inserta eventos focusout, formateadores de expresiones regulares, ordenaciones de columnas, colores y datos al grid.
function configurarGrid(data){
	//Se rellena el grid
	$(gridP117.nameJQuery)[0].addJSONData(data);
	gridP117.actualPage = data.page;
	gridP117.localData = data;
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************* EVENTOS DE P117 **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Evento de botón buscar
function events_p117_btn_buscar(){
	$("#p117_btn_buscar").click(function () {
		$("#p117_AreaResultados").hide();
		loadData_gridP117GestionAvisosSiec();
	});
}

//Evento de botón borrar
function events_p117_btn_borrar(){
	$("#p117_btn_delete").click(function () {
		var selectedRows=getSelectedDataIDs();
		if (selectedRows.length>0){
			borrarSeleccionados(selectedRows);
		}else{
			createAlert(replaceSpecialCharacters(noHayRegistrosSeleccionados), "ERROR");
		}
	});
}
function getSelectedDataIDs(){
	return gridP117.getSelectedRows();
}
//Evento de botón nuevo
function events_p117_btn_nuevo(){
	$("#p117_btn_new").click(function () {
		p118OpenPopUp();
	});
}

function borrarSeleccionados(selectedRows){
	var idsSeleccionados = [];
	for(var i = 0; i<selectedRows.length; i++){
		var id=getRowDataByRowId(selectedRows[i]);
		idsSeleccionados.push(id.codAviso);
	}
	var url='./gestionAvisosSiec/deleteRows.do';
	var avisosSiec = new AvisosSiec(null,null,null,null,null,null,null,null,null,null,null,null,null,null,idsSeleccionados);
	var objJson = $.toJSON(avisosSiec);	
	$.ajax({
		type : 'POST',
		url : url,
		data: objJson,
		contentType : "application/json; charset=utf-8",
		success : function(data) {	
			//Si hay error, repintamos el grid.
			if(data != 0){
				createAlert(replaceSpecialCharacters(noHayRegistrosSeleccionados), "ERROR");
			}
			loadData_gridP117GestionAvisosSiec();
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}
	});	
}

