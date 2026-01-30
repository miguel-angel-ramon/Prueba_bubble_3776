/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/****************************************************************** VARIABLES ****************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
//Guarda el objeto del grid
var gridP104 = null;

//Se guarda el estado seleccionado en una variable global para utilizarla en p102.js a la hora de dibujar los formularios en dibujarFormulariosDeEntradas()
var estado = null;

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
	loadP104(locale);
	initializeScreenP104();

	//Inicializamos el PopUp
	initializeScreenP105();
	initializeScreenP106();
});


/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/********************************************************* INICIALIZACIÓN DE PANTALLA ********************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

function initializeScreenP104(){
	//Evento de botón buscar
	events_p104_btn_buscar();

	//Evento de botón borrar
	events_p104_btn_borrar();

	//Evento de botón nuevo
	events_p104_btn_nuevo();
	
	//Evento de botón modificar fecha
	events_p104_btn_modificarFechas();
}

function CestasNavidad(
		codArtLote, descrLoteMisumi,
		imagen1, imagen2, imagen3, 
		codArtLoteCpb, descripcionLoteCpb, 
		fecIni, fecFin,
		flgEroski, flgCaprabo, orden,
		estado, borrar,
		lstBorrados, lstModificados, lstNuevos){

	this.codArtLote = codArtLote;
	this.descrLoteMisumi = descrLoteMisumi;
	this.imagen1 = imagen1;
	this.imagen2 = imagen2;
	this.imagen3 = imagen3;
	this.codArtLoteCpb = codArtLoteCpb;
	this.descripcionLoteCpb = descripcionLoteCpb;
	this.fecIni = fecIni;
	this.fecFin = fecFin;
	this.flgEroski = flgEroski;
	this.flgCaprabo = flgCaprabo;
	this.orden = orden;
	this.estado = estado;
	this.borrar = borrar;
	this.lstBorrados = lstBorrados;
	this.lstModificados = lstModificados;
	this.lstNuevos = lstNuevos;

	this.prepareToJsonObject = function(){
		var jsonObject = null;
		jsonObject = {
				'codArtLote': this.codArtLote,
				'descrLoteMisumi': this.descrLoteMisumi,
				'imagen1': this.imagen1,
				'imagen2': this.imagen2,
				'imagen3': this.imagen3,
				'codArtLoteCpb': this.codArtLoteCpb,
				'descripcionLoteCpb': this.descripcionLoteCpb,
				'fecIni': this.fecIni,
				'fecFin': this.fecFin,
				'flgEroski': this.flgEroski,
				'flgCaprabo': this.flgCaprabo,
				'orden': this.orden,
				'estado': this.estado,
				'borrar': this.borrar,
				'lstBorrados': this.lstBorrados,
				'lstModificados': this.lstModificados,
				'lstNuevos': this.lstNuevos
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
function loadP104(locale){
	//Define la estructura del grid, sus columnas de dónde van a obtener los datos etc.
	gridP104 = new GridP104ParametrizacionCestas(locale);

	//Define el ancho del grid.
	$(gridP104.nameJQuery).jqGrid('setGridWidth',950, true);

	//Carga en data los nombres de las colummnas del grid, etc.
	var jqxhr = $.getJSON(gridP104.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		//Carga el nombre de las columnas, titulo, etc.
		gridP104.colNames = data.ColNames;
		gridP104.title = data.GridTitle;
		gridP104.emptyRecords= data.emptyRecords;

		iconoGuardado = data.iconoGuardado;

		noHayRegistrosSeleccionados = data.noHayRegistrosSeleccionados;
		
		//Definimos las columnas, las llamadas al paginar, ordenar etc.
		load_gridP104ParametrizacionCestas(gridP104);

		p104SetHeadersTitles(data);
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

/************************ GRID ************************/
//Clase de constantes para el GRID de parametrización cestas
function GridP104ParametrizacionCestas (locale){
	// Atributos
	this.name = "gridP104ParametrizacionCestas"; 
	this.nameJQuery = "#gridP104ParametrizacionCestas"; 

	this.i18nJSON = './misumi/resources/p104ParametrizacionCestas/p104ParametrizacionCestas_' + locale + '.json';

	this.colNames = null; //Está en el json
	this.cm = [
	           {
	        	   "name" : "borrar",
	        	   "index": "BORRRAR",
	        	   "width" : 8,
	        	   "formatter" : checkFormatter,
	        	   "sortable": false
	           },
	           {
	        	   "name"      : "codArtLote",
	        	   "index"     : "codArtLote",
	        	   "width"     : 50,
	        	   "align"     : "left",
	           },{
	        	   "name"      : "descrLoteMisumi",
	        	   "index"     : "descrLoteMisumi",
	        	   "width"     : 80,
	        	   "align"     : "left"
	           },{
	        	   "name"      : "fecIni",
	        	   "index"     : "fecIni",
	        	   "formatter": fechaFormatter,
	        	   "width"     : 30,	
	        	   "align"     : "left"						
	           },{
	        	   "name"      : "fecFin",
	        	   "index"     : "fecFin", 
	        	   "formatter": fechaFormatter,
	        	   "width"     : 30,	
	        	   "align"     : "left"					
	           },{
	        	   "name"      : "imagen1",
	        	   "index"     : "imagen1", 
	        	   "width"     : 20,	
	        	   "align"     : "center",
	        	   "formatter": imageFormatter,
	        	   "sortable" : false						
	           },{
	        	   "name"      : "imagen2",
	        	   "index"     : "imagen2", 
	        	   "width"     : 20,	
	        	   "align"     : "center",
	        	   "formatter": imageFormatter,
	        	   "sortable" : false
	           },{
	        	   "name"      : "imagen3",
	        	   "index"     : "imagen3", 
	        	   "width"     : 20,	
	        	   "align"     : "center",
	        	   "hidden"	   : true,
	        	   "formatter": imageFormatter,
	        	   "sortable" : false					
	           },{
	        	   "name"      : "codArtLoteCpb",
	        	   "index"     : "codArtLoteCpb", 
	        	   "hidden"	   : true,
	        	   "width"     : 50,	
	        	   "align"     : "left"						
	           },{
	        	   "name"      : "descripcionLoteCpb",
	        	   "index"     : "descripcionLoteCpb",
	        	   "hidden"	   : true,
	        	   "width"     : 80,
	        	   "align"     : "left"
	           },{
	        	   "name"      : "flgEroski",
	        	   "index"     : "flgEroski",
	        	   "width"     : 10,
	        	   "align"     : "left",

	           },{
	        	   "name"      : "flgCaprabo",
	        	   "index"     : "flgCaprabo",
	        	   "width"     : 30,
	        	   "align"     : "left",

	           },{
	        	   "name"      : "orden",
	        	   "index"     : "orden",
	        	   "width"     : 30,
	        	   "align"     : "left",

	           },{
	        	   "name" : "mensaje",
	        	   "index":"mensaje", 
	        	   "formatter": estadoFormatMessage,
	        	   "fixed":true,
	        	   "width" : 30,
	        	   "sortable" : true
	           }	
	           ]; 
	this.locale = locale;
	//this.sortIndex = "";
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerGridp104";  
	this.pagerNameJQuery = "#pagerGridp104";
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
		var colModel = jQuery(gridP104.nameJQuery).jqGrid('getGridParam', 'colModel'); 
		var i;
		var l = colModel.length;
		var colItem; 
		var cmName;
		var postData = jQuery(gridP104.nameJQuery).jqGrid('getGridParam', 'postData');
		var columnsState = {
				search: jQuery(gridP104.nameJQuery).jqGrid('getGridParam', 'search'),
				page: jQuery(gridP104.nameJQuery).jqGrid('getGridParam', 'page'),
				sortname: jQuery(gridP104.nameJQuery).jqGrid('getGridParam', 'sortname'),
				sortorder: jQuery(gridP104.nameJQuery).jqGrid('getGridParam', 'sortorder'),
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
function load_gridP104ParametrizacionCestas(gridP104) {

	$(gridP104.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		datatype : 'local',
		contentType : 'application/json',
		mtype : 'POST',
		colNames : gridP104.colNames,
		colModel : gridP104.cm,
		rowNum : 10,
		rowList : [ 10, 30, 50 ],
		height : "100%",
		autowidth : true,
		width : "100%",
		rownumbers : gridP104.rownumbers,
		pager : gridP104.pagerNameJQuery,
		viewrecords : true,
		caption : gridP104.title,
		altclass: "ui-priority-secondary",
		altRows: true, //false, para que el grid no muestre cebrado
		hidegrid : false, //false, para ocultar el boton que colapsa el grid.
		sortable : true,
		multiselect: false,
		index: gridP104.sortIndex,
		sortname: gridP104.sortIndex,
		sortorder: gridP104.sortOrder,
		emptyrecords : gridP104.emptyRecords,
		gridComplete : function() {
			gridP104.headerHeight("p83_gridHeader");		

			//Una vez cargado el grid, iniciamos el contador a 1.
			//Al ser una variable global y actualizarse cada vez
			//que se abre un nuevo grid en la función imageFormatterMessage
			//obtiene valores no acordes con el número de fila cargado
			//por eso hay que iniciarlo a 1.
			numRow = 1;
		},
		loadComplete : function(data) {
			gridP104.actualPage = data.page;
			gridP104.localData = data;
			gridP104.sortIndex = gridP104.sortIndex;
			gridP104.sortOrder = gridP104.sortOrder;	

			//Ocultamos la check de seleccionar todos.
			$("#cb_gridP104ParametrizacionCestas").attr("style", "display:none");

			$("#p104_AreaReferencias .loading").css("display", "none");	
		},
		resizeStop: function () {
			gridP104.saveColumnState.call($(this),gridP104.myColumnsState);
			$(gridP104.nameJQuery).jqGrid('setGridWidth', 950, false);
		},
		onPaging : function(postdata) {	
			alreadySorted = false;
			gridP104.sortIndex = gridP104.sortIndex;
			gridP104.sortOrder = gridP104.sortOrder;
			gridP104.saveColumnState.call($(this), this.p.remapColumns);
			loadData_gridP104ParametrizacionCestas('S');

			return 'stop';
		},beforeSelectRow: function (rowid, e) {
			var $myGrid = $(this),
			i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),
			cm = $myGrid.jqGrid('getGridParam', 'colModel');

			if (!(cm[i].name === 'borrar'))
			{
				p105OpenPopUp(getRowDataByRowId(rowid));
			}

			return true;
		},onSelectRow: function(id){

		},
		onSortCol : function (index, columnIndex, sortOrder){
			gridP104.sortIndex = index;
			gridP104.sortOrder = sortOrder;
			gridP104.saveColumnState.call($(this), this.p.remapColumns);
			loadData_gridP104ParametrizacionCestas('S');
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
function p104SetHeadersTitles(data){
	var colModel = $(gridP104.nameJQuery).jqGrid("getGridParam", "colModel");
	$.each(colModel, function(i) {
		if (colModel[i].name!="rn"){
			$("#jqgh_gridP104ParametrizacionCestas_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
		}
	});
}

/************************* FORMATEADORES DE COLUMNAS **************************/
function imageFormatter(cellValue, opts, rData) {
	if(cellValue == null || cellValue.toString() == ""){
		cellValue = "X";
		return cellValue;
	}else{
		// MISUMI-284: Si estamos en la columna 7 y la cesta tiene texto, se muestra una X siempre
		if (opts.pos==7 && rData.tieneTexto){
			cellValue = "X";
			return cellValue;
		}else{
			//Crea una foto y mete el B64 obtenido de BD como parámetro
			var imgTool =  document.createElement('img'); 
			imgTool.src = 'data:image/png;base64,' + cellValue;

			//Crea una foto que corresponde al icono de photo.png que se muestra en la celda.
			//Se le añade al title la foto de BD. Más tarde en generateTooltip, el tooltip
			//mostrará la imagen de este title, que corresponde a la imagen de BD.
			var img = document.createElement('img'); 
			img.src = './misumi/images/photo.png';

			//Mediante este id, en el popup de modificacion obtendremos la imagen para mostrar al poner el ratón encima del campo de selección de imagen.
			img.id = "p105_" + opts.colModel.name + rData["codArtLote"] + "-" + rData["codArtLoteCpb"];
			img.title = imgTool.outerHTML;
			return img.outerHTML;
		}

	}
}

function fechaFormatter(cellValue, opts, rData){
	if(cellValue == null || cellValue.toString() == ""){
		return "";
	}else{
		var fecha = cellValue.split("-");
		return fecha[2] + "-" + fecha[1] + "-" + fecha[0]
	}
}

function checkFormatter(cellValue, opts, rowObject) {
	var fila = JSON.stringify(rowObject);
	if (cellValue == "S") {
		return "<input id='" + opts.rowId  + "_" + opts.colModel.name + "'' data-value='" + cellValue + "' type='checkbox' checked='checked' onclick='controlClickCheck("+ opts.rowId + ", this)'/>";
	}
	return "<input id='" + opts.rowId  + "_" + opts.colModel.name + "' data-value='" + cellValue + "' type='checkbox' onclick='controlClickCheck(" + opts.rowId + ", this)'/>";
}

function estadoFormatMessage(cellValue, opts, rData) {

	var imagen = "";
	var mostrarModificado = "none;";
	var mostrarGuardado = "none;";
	var mostrarError = "none;";
	var descError = "";

	//Controlamos los posibles errores que me lleguen para pintar el icono correspondiente.
	if (parseFloat(rData['estado']) == '8')
	{
		//Pintamos el icono de que se ha guardado
		mostrarGuardado = "block;";
	}

	imagen = "<div id='p105_"+numRow+"_parametrizacionCestas_divGuardado' align='center' style='display: "+ mostrarGuardado + "'><img id='p105_"+numRow+"_parametrizacionCestas_imgGuardado' src='./misumi/images/floppy.png' title='"+iconoGuardado+"'/></div>"; //Guardado
	
	numRow++;

	return imagen;
}
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************* FUNCIONES DE P104 **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
//Función que sirve para paginar o reordenar los elementos del grid.
function loadData_gridP104ParametrizacionCestas(recarga) {
	
	//Poner ancho de grid, para que aunque haya más o menos columnas siempre ocupe lo mismo
	$(gridP104.nameJQuery).jqGrid('setGridWidth',950,true);

	$.ajax({
		type : 'GET',			
		url : './parametrizacionCestas/loadDataGrid.do?page='+gridP104.getActualPage()+'&max='+gridP104.getRowNumPerPage()+'&index='+gridP104.getSortIndex()+'&sortorder='+gridP104.getSortOrder()+'&recarga='+recarga,
		success : function(data) {	
			if(data != null){
				if(data.rows != null && data.rows.length > 0){
					configurarGrid(data);
					generateTooltipP104(data);
				}else{
					//Rellenamos el grid vacío
					configurarGrid(data);


					//Se pone esta fila porque si paginas y da error de paginación, el grid cambia el número de página internamente pero no externamente (la que se visualiza), y si luego
					//pagina bien, no va a la siguiente página, si no a la que tiene internamente. 
					var valorCajaGrid = $("#p104_popUpDevolFinCampana .ui-pg-input").val();
					$(gridP104.nameJQuery).setGridParam({page:valorCajaGrid});
				}
				$("#p104_AreaResultados").show();
			}else{
				createAlert(replaceSpecialCharacters(notPosibleToReturnDataP84), "ERROR");

				//Se pone esta fila porque si paginas y da error de paginación, el grid cambia el número de página internamente pero no externamente (la que se visualiza), y si luego
				//pagina bien, no va a la siguiente página, si no a la que tiene internamente. 
				var valorCajaGrid = $("#p104_popUpDevolFinCampana .ui-pg-input").val();
				$(gridp104.nameJQuery).setGridParam({page:valorCajaGrid});
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});		
}

/**
 * Actualizamos los datos de la fila.
 */
function updateRowDeleteData(cestasNavidad) {	
	var url='./parametrizacionCestas/updateRowDelete.do';
	var objJson = $.toJSON(cestasNavidad);	
	$.ajax({
		type : 'POST',
		url : url,
		data: objJson,
		contentType : "application/json; charset=utf-8",
		success : function(data) {	
			//Si hay error, repintamos el grid.
			if(data != 0){
				loadData_gridP104ParametrizacionCestas("S");
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}
	});	
}

/**
 * Funcion que borra los datos de parametrización del detallado
 */
function p104BorrarBTN() {
	$.ajax({
		type : 'DELETE',
		url : './parametrizacionCestas/deleteRows.do',
		contentType : "application/json; charset=utf-8",
		dataType : "text",
		success : function(data) {		
			//Si el borrado es correcto, refrescamos los grids.
			if(data == 0){
				loadData_gridP104ParametrizacionCestas('S');
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});	
}

function controlClickCheck(idFila, check){
	//Obtenemos la clave y el valor del check borrar para actualizarlo en la bd.
	var fila = getRowDataByRowId(idFila);
	var codArtLote = fila.codArtLote;
	var borrar = check.checked ? "S" : "N";
	var cestasNavidad = new CestasNavidad(codArtLote,null,null,null,null,null,null,null,null,null,null,null,null,borrar);

	//Actualizamos la fila.
	updateRowDeleteData(cestasNavidad);
}

function getRowDataByRowId(id){
	return gridP104.getRowValue(id);
}

//Función que inserta eventos focusout, formateadores de expresiones regulares, ordenaciones de columnas, colores y datos al grid.
function configurarGrid(data){
	//Se rellena el grid
	$(gridP104.nameJQuery)[0].addJSONData(data);
	gridP104.actualPage = data.page;
	gridP104.localData = data;
}

//Función que sirve para que las fotos se muestren al pasar por encima de ciertas celdas de las columnas
function generateTooltipP104(data){
	var ids = jQuery(gridP104.nameJQuery).jqGrid('getDataIDs'), i, l = ids.length;	
	var colModel = $(gridP104.nameJQuery).jqGrid("getGridParam", "colModel");
	var rowData = null;
	for (i = 0; i < l; i++) {
		var columna = $(gridP104.nameJQuery+" tbody #"+ ids[i]+ " td[aria-describedby='"+gridP104.name+"_imagen1']");
		var columna2 = $(gridP104.nameJQuery+" tbody #"+ ids[i]+ " td[aria-describedby='"+gridP104.name+"_imagen2']");
		columna.addClass("contieneTooltipFotoP104");
		columna2.addClass("contieneTooltipFotoP104");
	}
	$(".contieneTooltipFotoP104").tooltip({
		position: {
			my: "left top",
			at: "right+5 bottom+5"
		}
	});
}
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************* EVENTOS DE P104 **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Evento de botón buscar
function events_p104_btn_buscar(){
	$("#p104_btn_buscar").click(function () {
		$("#p104_AreaResultados").hide();
		loadData_gridP104ParametrizacionCestas('N');
	});
}

//Evento de botón borrar
function events_p104_btn_borrar(){
	$("#p104_btn_delete").click(function () {
		contarSeleccionados(p104BorrarBTN);
	});
}

//Evento de botón nuevo
function events_p104_btn_nuevo(){
	$("#p104_btn_new").click(function () {
		p105OpenPopUp();
	});
}

function events_p104_btn_modificarFechas(){
	$("#p104_btn_modify").click(function () {
		contarSeleccionados(modificarFechas);
	});
}


function modificarFechas(){
	$("#p106_popup").dialog("open");
}

function contarSeleccionados(miFuncion){
	$.ajax({
		type : 'GET',			
		url : './parametrizacionCestas/countSeleccionados.do',
		success : function(data) {	
			if(data != null && data > 0){
				miFuncion();
			}else{
				createAlert(noHayRegistrosSeleccionados,"ERROR");
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});	
}