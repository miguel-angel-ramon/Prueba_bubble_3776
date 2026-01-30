/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/****************************************************************** VARIABLES ****************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
//Guarda el objeto del grid
var gridP107 = null;

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

var iconoGuardado = null;

var noHayRegistrosSeleccionados = null;

var exportarAExcel = false;

var existenDatosSinGuardar = false;

var codCentroActual = -1;

var dialogPopupChangeStock = null;
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************** DOCUMENTO LISTO **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

$(document).ready(function(){
	loadP107(locale);
	initializeScreenP107();
	initCheckHour();
});

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/********************************************************* INICIALIZACIÓN DE PANTALLA ********************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

function initCheckHour(){
	const now = new Date();
	const currentHour = now.getHours();
	const currentMinute = now.getMinutes();
	const isMsgVisible = $("#p107_AreaMensajes").is(":visible");
	if ( currentHour>=9 && currentHour<=22){
		if (!isMsgVisible){
			$("#p107_AreaMensajes").show();
		}
		
	}else{
		if (isMsgVisible){
			$("#p107_AreaMensajes").hide();
		}
	}
	setTimeout(initCheckHour, 60000);
}

function initializeScreenP107(){
	var codCentro = $("#centerId").val();
	//Evento de botón buscar
	events_p107_btn_buscar();
	events_p107_btn_exportExcel();
	events_p107_btn_limpiar();
	events_p107_btn_guardar();
	events_p107_btn_asignar();
	events_p107_plu_max_libre();
	load_cmbArea();
	init_buttons();
	clear_messages();
	
	//Al pinchar fuera del aplicativo de alarmas PLU, si existen datos sin guardar se avisa al usuario.
	window.onbeforeunload = function(event) {
		
		if(existenDatosSinGuardar && !exportarAExcel){ 
			event.preventDefault();
			// Chrome requires returnValue to be set.
			event.returnValue = "Hay datos de alarmas sin guardar. ¿Deseas ir a otra parte del aplicativo?";
		}else{
			exportarAExcel = false;
			return;
		}
	};

}


function clear_messages(){
	$("#p107_plu_libre").val("");
	$("#p107_referencia").val("");
	$('#p107_alert_referencias').hide();
	$("#p107_mensaje_asignar_plu").hide();
	$("#p107_mensaje_error_plu_max").hide();
}

function init_buttons(){
	$("#p107_btn_excel").prop("disabled", true);
	$("#p107_btn_save").prop("disabled", true);
}

function reset_cmb(cmb){
	$(cmb).combobox();
	$(cmb).empty();
	$(cmb).combobox('autocomplete',null);
	$(cmb).combobox('comboautocomplete',null);
}


function load_cmbArea(){	
	
	var codCentro = $("#centerId").val();
	$.ajax({
		type : 'GET',
		url : './alarmasPLU/loadAreaData.do?codCentro='+codCentro,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {
			$("#p107_cmb_area").empty();
			
			var option = "<option></option>"; 
			$("#p107_cmb_area").append(option);
			if (data!=null){
    			for (var key in data) {
    				var option = "<option value='" + key + "'>" + key + '-' + data[key] + "</option>"; 
					$("#p107_cmb_area").append(option);
    			};
				load_cmbAgrupacion();
				$("#p107_cmb_area").val("");
			}
			
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
	reset_cmb("#p107_cmb_seccion");
	$('#p107_cmb_seccion').combobox("disable");
	
	$("#p107_cmb_area").combobox({
        selected: function(event, ui) {
        	if ( ui.item.value!="" && ui.item.value!="null" ) {
        		$('#p107_cmb_seccion').combobox("enable");

            	load_cmbSeccion();
            	load_cmbAgrupacion();
        	}else{
        		reset_cmb("#p107_cmb_seccion");
        		reset_cmb("#p107_cmb_agrupacion");
        	}
        }
	});
}

function load_cmbSeccion(){	
	var codCentro = $("#centerId").val();
	var codArea = $("#p107_cmb_area").val();
	var codAreaStr = "";
	
	if (codArea != null || codArea != ''){
		codAreaStr = '&codArea='+codArea;
	}

	$.ajax({
		type : 'GET',
		url : './alarmasPLU/loadSeccionData.do?codCentro='+codCentro+codAreaStr,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			reset_cmb("#p107_cmb_seccion");
			reset_cmb("#p107_cmb_agrupacion");
    		if (data!=null){
    			var option = "<option></option>"; 
    			$("#p107_cmb_seccion").append(option);
    			for (var key in data) {
    				var option = "<option value='" + key + "'>" + key + '-' + data[key] + "</option>"; 
					$("#p107_cmb_seccion").append(option);
    			};
    			
    			load_cmbAgrupacion();
    		}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
	
	$("#p107_cmb_seccion").combobox({
	        selected: function(event, ui) {
	        	if ( ui.item.value!="" && ui.item.value!="null" ) {
	            	load_cmbAgrupacion();
	        	}else{
	        		reset_cmb("#p107_cmb_agrupacion");
	        	}
	        }
	});

}

function load_cmbAgrupacion(){	
	
	var codCentro = $("#centerId").val();
	var codArea = $("#p107_cmb_area").val();
	var codSeccion = $("#p107_cmb_seccion").val();
	
	var codAreaStr = "";
	
	if (codArea != null && codArea != ''){
		codAreaStr = '&codArea='+codArea;
	}
	var codSeccionStr = "";
	if (codSeccion!=null && codSeccion != ''){
		codSeccionStr = '&codSeccion='+codSeccion;
	}
	
	$.ajax({
		type : 'GET',
		url : './alarmasPLU/loadAgrupacionData.do?codCentro='+codCentro+codAreaStr+codSeccionStr,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			if (codCentroActual!=codCentro){
				codCentroActual = codCentro;
				checkCargando();
			}else{
				reset_cmb("#p107_cmb_agrupacion");
	    		if (data!=null){
	    			var option = "<option></option>"; 
	    			$("#p107_cmb_agrupacion").append(option);
	    			for (var key in data) {
	    				var option = "<option value='" + key + "'>" + key + '-' + data[key] + "</option>"; 
						$("#p107_cmb_agrupacion").append(option);
	    			};
	    		}
			}
			
			
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
	
	$("#p107_cmb_agrupacion").combobox();
}


/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************** INICIALIZACIÓN DE ELEMENTOS E IDIOMA *******************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

/************************ IDIOMA ************************/
//Función para buscar los mensajes de la pantalla en idioma seleccionado, por defecto 'es'
function loadP107(locale){
	
	//Define la estructura del grid, sus columnas de dónde van a obtener los datos etc.
	gridP107 = new GridP107AlarmasPLU(locale);

	//Define el ancho del grid.
	$(gridP107.nameJQuery).jqGrid('setGridWidth',980, true);

	//Carga en data los nombres de las colummnas del grid, etc.
	var jqxhr = $.getJSON(gridP107.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		//Carga el nombre de las columnas, titulo, etc.
		gridP107.colNames = data.ColNames;
		gridP107.title = data.GridTitle;
		gridP107.emptyRecords= data.emptyRecords;
		
		iconoGuardado = data.iconoGuardado;

		noHayRegistrosSeleccionados = data.noHayRegistrosSeleccionados;
		
		//Definimos las columnas, las llamadas al paginar, ordenar etc.
		load_gridP107AlarmasPLU(gridP107);
		p107SetHeadersTitles(gridP107);
		
		
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}


/************************ GRID ************************/
//Clase de constantes para el GRID 
function GridP107AlarmasPLU (locale){
	// Atributos
	this.name = "gridP107AlarmasPLU"; 
	this.nameJQuery = "#gridP107AlarmasPLU"; 

	this.i18nJSON = './misumi/resources/p107AlarmasPLU/p107AlarmasPLU_' + locale + '.json';

	this.colNames = null; //Está en el json
	this.cm = [
	          
	           {
	        	   "name"      : "codArticulo",
	        	   "index"     : "REF",
	        	   "width"     : 45,
	        	   "align"     : "center"
	           },{
	        	   "name"      : "denominacion",
	        	   "index"     : "DENOMINACIÓN",
	        	   "width"     : 80,
	        	   "align"     : "left"
	           },{
	        	   "name"      : "agrupacion",
	        	   "index"     : "AGRUPACIÓN",
	        	   "width"     : 65,	
	        	   "align"     : "center"											
	           },{
	        	   "name"      : "denomSegmento",
	        	   "index"     : "DENO. \nSEG",
	        	   "width"     : 80,
	        	   "align"     : "left"
	           },{
	        	   "name"      : "marca",
	        	   "index"     : "MARCA",
	        	   "width"     : 40,
	        	   "align"     : "left"

	           },{
	        	   "name"      : "grupoBalanza",
	        	   "index"     : "COD",
	        	   "width"     : 15,
	        	   "align"     : "center"

	           },{
	        	   "name"      : "plu",
	        	   "index"     : "PLUs",
	        	   "width"     : 20,
	        	   "align"     : "center",
	        	   "edittype"  : "text",
	        	   "formatter" : pluFormatter

	           },{
	        	   "name"      : "pluAnt",
	        	   "index"     : "PLU_ANT",
	        	   "hidden"    : true,
	        	   "hidedlg"   : true,
	        	   "width"     : 10,
	        	   "align"     : "center"

	           },{
	        	   "name"      : "diasCaducidad",
	        	   "index"     : "CAD",
	        	   "width"     : 15,
	        	   "align"     : "center"

	           },{
	        	   "name"      : "mmc",
	        	   "index"     : "MMC",
	        	   "width"     : 15,
	        	   "align"     : "center"

	           },{
	        	   "name"      : "eb",
	        	   "index"     : "ELABORABLE",
	        	   "width"     : 15,
	        	   "hidden"	   : true,
	        	   "align"     : "center"

	           },{
	        	   "name"      : "fechaUltimaVenta",
	        	   "index"     : "ÚLTIMA VENTA",
	        	   "width"     : 65,
	        	   "formatter": fechaFormatter,
	        	   "align"     : "center"

	           },{
	        	   "name"      : "stock",
	        	   "index"     : "STOCK",
	        	   "width"     : 55,
	        	   "align"     : "center",
	        	   "formatter": stockFormatter
	           },{
	        	   "name"      : "stockModificado",
	        	   "index"     : "STOCK_MODIFICADO",
	        	   "width"     : 15,
	        	   "align"     : "center",
	        	   "hidedlg"   : true,
	        	   "hidden"	   : true
	           },{
	        	   "name"      : "cajasPendientesRecibir",
	        	   "index"     : "PENDIENTE RECIBIR",
	        	   "width"     : 10,
	        	   "align"     : "center",
	        	   "formatter" : tooltipFormatter
	        },{
	        	   "name"      : "compraVenta",
	        	   "index"     : "COMPRA-VENTA/SOLO-VENTA",
	        	   "width"     : 60,
	        	   "align"     : "left"

	           },{
	        	   "name"      : "estadoGrid",
	        	   "index"     : "ESTADO",
	        	   "width"     : 15,
	        	   "hidedlg"   : true,
	        	   "hidden"	   : true,
	        	   "align"     : "left"

	           },{
	        	   "name"      : "stockBandejas",
	        	   "index"     : "STOCK_BANDEJAS",
	        	   "width"     : 15,
	        	   "hidedlg"   : true,
	        	   "hidden"	   : true,
	        	   "align"     : "left"

	           },{
	        	   "name"      : "fechaActualizacion",
	        	   "index"     : "ULTIMA MODIFICACION",
	        	   "width"     : 10,
//	        	   "hidedlg"   : true,
	        	   "hidden"	   : true,
	        	   "formatter": formatDateHora,
	        	   "align"     : "left"

	           },{
	        	   "name"      : "msgError",
	        	   "index"     : "MSG ERROR",
	        	   "width"     : 10,
	        	   "hidedlg"   : true,
	        	   "hidden"	   : true,
	        	   "align"     : "left"
	           },{

	        	   "name"      : "codError",
	        	   "index"     : "COD ERROR",
	        	   "width"     : 10,
	        	   "hidedlg"   : true,
	        	   "hidden"	   : true,
	        	   "align"     : "left"

	           },{
	        	   "name" : "eliminar",
	        	   "index": "ELIMINAR",
	        	   "width" : 16,
	        	   "hidedlg"   : true,
	        	   "align"     : "center",
	        	   "editable": true,
	        	   "edittype": 'checkbox', 
	        	   "editoptions": { value: "True:False" }, 
	        	   "formatter": checkboxFormatterEliminar, 
	        	   "formatoptions": { disabled: false},
	        	   "sortable": false
	           },{
	        	   "name" : "reasignar",
	        	   "index": "REASIGNAR",
	        	   "width" : 75,
	        	   "align"     : "center",
	        	   "editable": true,
	        	   "hidedlg"   : true,
	        	   "sortable": false,
	        	   "formatter": inputFormatterReasignar
	           },{	        	   
		           "name"      : "estado",
	        	   "index"     : "ESTADO",
	        	   "width"     : 16,
	        	   "hidedlg"   : true,
	        	   "editable"  : true,
	        	   "formatter": imageFormatEst,
	        	   "align"     : "center",
	        	   "sortable": false
				},{
	        	   "name"      : "lstOtrasAgrupacionesBalanza",
	        	   "index"     : "OTRAS AGRUPACIONES BALANZA",
	        	   "width"     : 10,
	        	   "hidedlg"   : true,
	        	   "hidden"	   : true,
	        	   "align"     : "left"
	           }
	           ]; 
	this.locale = locale;
	this.sortIndex = "STOCK";
	this.sortOrder = "desc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerGridp107";  
	this.pagerNameJQuery = "#pagerGridp107";
	this.title = null; //Está en el json
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null; //Está en el json
	this.rownumbers = true;
	this.myColumnsState = null;
	this.isColState = null;
	this.firstLoad = true;
	this.modificado = false;
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
		if ($(this.nameJQuery).getGridParam('rowNum')!=null){
			return $(this.nameJQuery).getGridParam('rowNum');
		}else{
			return 10;
		}
		
	} ;

	this.getSortIndex = function getSortIndex () {
		if (gridP107.sortIndex!==null && gridP107.sortIndex!==undefined){
			return gridP107.sortIndex;
		}else{
			return "";
		}
//		if ($(this.nameJQuery).getGridParam('sortname')!=null){
//			return $(this.nameJQuery).getGridParam('sortname');
//		}else{
//			return null;
//		}

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
		var colModel = jQuery(gridP107.nameJQuery).jqGrid('getGridParam', 'colModel'); 
		var i;
		var l = colModel.length;
		var colItem; 
		var cmName;
		var postData = jQuery(gridP107.nameJQuery).jqGrid('getGridParam', 'postData');
		var columnsState = {
				search: jQuery(gridP107.nameJQuery).jqGrid('getGridParam', 'search'),
				page: jQuery(gridP107.nameJQuery).jqGrid('getGridParam', 'page'),
				sortname: jQuery(gridP107.nameJQuery).jqGrid('getGridParam', 'sortname'),
				sortorder: jQuery(gridP107.nameJQuery).jqGrid('getGridParam', 'sortorder'),
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

function imageFormatEst(cellValue, opts, rowObject) {
	
	// Obtener el estado de la celda: 
	// Si se ha modficiado el PLU (plu<>plu_ant) --> modificar
	var plu = rowObject.plu;
	var pluAnt = rowObject.pluAnt;
	var msgError = rowObject.msgError;
		
	var fechaActualizacion = formatDateHora(rowObject.fechaActualizacion);
	if (plu != pluAnt){    
		existenDatosSinGuardar = true;
		return "<img src='./misumi/images/modificado.png' class='p107_imgCenter' title='MODIFICADO: El plu actual ("+plu+") es distinto del anterior ("+pluAnt+")'/>";
	}else if (msgError!=null){
		if (msgError.indexOf("OK#")!=-1){
			var msgErrorSplit = msgError.split("#");
			var user = msgErrorSplit[1];
			return "<img src='./misumi/images/floppy.png' class='p107_imgCenter' title='GUARDADO (USUARIO: "+user+") - (HORA: "+fechaActualizacion+")'/>";	
		}else{
			existenDatosSinGuardar = true;
			return "<img src='./misumi/images/dialog-error-24.png' class='p107_imgCenter' title='"+msgError+"'/>";
		}
	}	    
	return "";
		
}

function reasignar(event, row_id){

	if (event.keyCode == 13) {
		var ref1 = $("#p107_reasignar"+row_id).val();
		var ref2= $(gridP107.nameJQuery).jqGrid('getCell',row_id,'codArticulo');
		var plu = $(gridP107.nameJQuery).jqGrid('getCell',row_id,'plu');
		var grupoBalanza = $("#p107_cmb_agrupacion").val();
		// VALIDATE
		if (ref1==ref2){
			createAlert("La referencia introducida es la misma que la referencia de la fila.","ERROR");
		}else if (plu=="0"){
			createAlert("La fila selecciada no tiene un PLU que poder reasignar.","ERROR");
		}else if (!find_referencia(ref1)){
			createAlert("Referencia NO susceptible de introducir PLU.","ERROR");
		}else{
			var codCentro = $("#centerId").val();
			$.ajax({
				type : 'GET',			
				url : './alarmasPLU/reasignar.do?codCentro='+codCentro+'&plu='+plu+'&ref1='+ref1+'&ref2='+ref2+'&grupoBalanza='+grupoBalanza,
				success : function() {	
					//$("#p107_btn_search").click();
					loadData_gridP107AlarmasPLU('S',null);
				},
				error : function (xhr, status, error){
					handleError(xhr, status, error, locale);				
				}			
			});	
		}
	}
	
}

function stockFormatter(value, options, rowObject){
	var modificado = rowObject.stockModificado;
	var isGreen = "";
	if (modificado){
		isGreen = ';color: #32CD32;font-weight: bold;';	
	}
	
	if (userPerfilMisumi!=3){
		return '<a href="javascript:void(0);" id="p107_stock'+options.rowId+'" style="cursor:pointer'+isGreen+'"; onclick="showPopupChangeStock('+ options.rowId+');" >'+value+'</a>';
		}else{
		return '<a href="javascript:void(0);" id="p107_stock'+options.rowId+'" style="cursor:pointer'+isGreen+'"; onclick="" >'+value+'</a>';
		}
	}

function afterIframe(data){

	document.getElementById('iframePopupChangeStock').contentWindow.document.getElementById("LogoCerrar").style.display = "none"; 

	const action = document.getElementById('iframePopupChangeStock').contentWindow.document.getElementsByTagName('form')[0].action;
	if (action.indexOf("P12")!=-1 && dialogPopupChangeStock!=null){
		dialogPopupChangeStock.dialog('close');
		loadData_gridP107AlarmasPLU('S',null);
		//$("#p107_btn_search").click();
	}
}

function showPopupChangeStock(id){
	$('#popupChangeStock_Identificador').val(id);
	
	var codArticulo= $(gridP107.nameJQuery).jqGrid('getCell',id,'codArticulo');
	
	var mmc= $(gridP107.nameJQuery).jqGrid('getCell',id,'mmc');
	
	var url = "./pdaP28CorreccionStockInicio.do?codArt="+codArticulo+"&origen=DR&mmc="+mmc+"&calculoCC=S";
	
    var iframe = $('<iframe id="iframePopupChangeStock" onload="afterIframe(this);" frameborder="0" marginwidth="0" marginheight="0" allowfullscreen></iframe>');
    
    iframe.attr({
        width: 240,
        height: 320,
        src: url
    });

	dialogPopupChangeStock = $("<div id='popupChangeStock'></div>").append(iframe).appendTo("body").dialog({
        autoOpen: false,
        modal: true,
        resizable: false,
        width: "auto",
        height: "auto",
        close: function () {
            iframe.remove();
        }
    });
	
	dialogPopupChangeStock.dialog("option", "title", "Cambiar Stock").dialog("open");
		

}

function inputFormatterReasignar(value, options, rowObject){
	return '<input id="p107_reasignar'+options.rowId+'" type="text" class="p107_center" onkeypress="reasignar(event,'+ options.rowId+');" />';
}

function checkboxFormatterEliminar(cellValue, options, rowObject)
{
	return '<input id="p107_eliminar'+options.rowId+'" class="p107_checkboxEliminar" type="checkbox"' + (cellValue ? ' checked="checked"' : '') + '/>';
}

//Función que sirve para especificar nombres de columnas, y qué ocurren en las paginaciones, ordenaciones etc.
function load_gridP107AlarmasPLU(gridP107) {
	$(gridP107.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		datatype : 'local',
		contentType : 'application/json',
		mtype : 'POST',
		colNames : gridP107.colNames,
		colModel : gridP107.cm,
		rowNum : 10,
		rowList : [ 10, 30, 50 ],
		height : "100%",
		autowidth : true,
		width : "100%",
		rownumbers : gridP107.rownumbers,
		pager : gridP107.pagerNameJQuery,
		viewrecords : true,
		caption : gridP107.title,
		altclass: "ui-priority-secondary",
		altRows: false, //false, para que el grid no muestre cebrado
		hidegrid : false, //false, para ocultar el boton que colapsa el grid.
		sortable : true, 
		multiselect: false,
		autoencode:true,
		shrinkToFit:true,
		index: gridP107.sortIndex,
		sortname: gridP107.sortIndex,
		sortorder: gridP107.sortOrder,
		emptyrecords : gridP107.emptyRecords,
		gridComplete : function() {
			gridP107.headerHeight("p83_gridHeader");		

			//Una vez cargado el grid, iniciamos el contador a 1.
			//Al ser una variable global y actualizarse cada vez
			//que se abre un nuevo grid en la función imageFormatterMessage
			//obtiene valores no acordes con el número de fila cargado
			//por eso hay que iniciarlo a 1.
			numRow = 1;
			
		},
		loadComplete : function(data) {
			$(gridP107.nameJQuery).jqGrid('filterToolbar',{defaultSearch : "cn"});
			$("#gbox_gridP107AlarmasPLU input").css("display", "none");
			$("#gbox_gridP107AlarmasPLU #gs_codArticulo")
				.css("display", "block")
				.on("keyup", function(e) {
		  	    	const codArt = $("#gbox_gridP107AlarmasPLU #gs_codArticulo").val();
		  	    	loadData_gridP107AlarmasPLU('N', codArt);
				});

			gridP107.actualPage = data.page;
			gridP107.localData = data;

			//Ocultamos la check de seleccionar todos.
			//$("#cb_gridP107AlarmasPLU").attr("style", "display:none");

			$("#p107_AreaReferencias .loading").css("display", "none");	
			$("#p107_btn_excel").prop("disabled", true);
			
			
		},
		resizeStop: function () {
			gridP107.modificado = true;
			gridP107.saveColumnState.call($(this),gridP107.myColumnsState);
			$(gridP107.nameJQuery).jqGrid('setGridWidth', 990, false);
		},
		onPaging : function(postdata) {	
			alreadySorted = false;
			gridP107.sortIndex = gridP107.sortIndex;
			gridP107.sortOrder = gridP107.sortOrder;
			gridP107.saveColumnState.call($(this), this.p.remapColumns);
			const codArt = $("#gbox_gridP107AlarmasPLU #gs_codArticulo").val();
	    	loadData_gridP107AlarmasPLU('S', codArt);

			return 'stop';
		},
		onSortCol : function (index, columnIndex, sortOrder){
			
			gridP107.sortIndex = index;
			gridP107.sortOrder = sortOrder;
			gridP107.saveColumnState.call($(this), this.p.remapColumns);
			const codArt = $("#gbox_gridP107AlarmasPLU #gs_codArticulo").val();
	    	loadData_gridP107AlarmasPLU('S', codArt);
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
		},
		beforeSelectRow: function() {
		     return false;
		},
		onSelectRow: function() {
		     return false;
		},
		onRightClickRow: function () {
			$(gridP107.nameJQuery).jqGrid('resetSelection');
		    return false;
		}
		
	});
	
	$(gridP107.nameJQuery).jqGrid('navGrid',gridP107.pagerNameJQuery,{
		add:false,edit:false,del:false,search:false,refresh:false}
	);
	
	// Boton reordenar columnas
	$(gridP107.nameJQuery).jqGrid('navButtonAdd',gridP107.pagerNameJQuery,{ 
		caption: "Filtrado de columnas", 
		title: "Reordenar Columnas", 
	onClickButton : function (){ 
		$(gridP107.nameJQuery).jqGrid('columnChooser',
				{
            		"done": function(perm) {
            			if (perm) {
            				var autowidth = true;
            				if (gridP107.modificado == true){
            					autowidth = false;
            					gridP107.myColumnsState =  gridP107.restoreColumnState(gridP107.cm);
                		    	gridP107.isColState = typeof (gridP107.myColumnsState) !== 'undefined' && gridP107.myColumnsState !== null;
                		    	this.jqGrid("remapColumns", perm, true);
                		    	 var colModel =jQuery(gridP107.nameJQuery).jqGrid('getGridParam', 'colModel'); 
                		    	 var l = colModel.length;
                		         var colItem; 
                		         var cmName;
                		         var colStates = gridP107.myColumnsState.colStates;
                		         var cIndex = 2;
                		         for (i = 0; i < l; i++) {
                		             colItem = colModel[i];
                		             cmName = colItem.name;
                		             if (colItem.hidden !== true && cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {
                		            	 
                		            	 $(gridP107.nameJQuery).jqGrid('setColProp',cmName,{width:colStates[cmName].width});
                		            	 var cad =gridP107.nameJQuery+'_'+cmName;
                		            	 var ancho = "'"+colStates[cmName].width+"px'";
                		            	 var cell = jQuery('table'+gridP107.nameJQuery+' tr.jqgfirstrow td:nth-child(' + (i+1) + ')');
                		            	 cell.css("width", colStates[cmName].width + "px");
                		            	
                		            	 $(cad).css("width", colStates[cmName].width + "px");
                		            	
                		             }
                		         }
                		         
            				} else {
            					this.jqGrid("remapColumns", perm, true);
            				}
            				gridP107.saveColumnState.call(this, perm);
            				$(gridP107.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), autowidth);
            				
            				
            			}
            		}
				}		
		); } });
	
	$(gridP107.nameJQuery).jqGrid('setLabel', 'eliminar', 
	  '<img src=./misumi/images/cruz_82x82.png width=16 height=16 class="p107_imgCenter" style="padding-top:8px" title="Eliminar PLU">');
		
	
}

/************************ TITULOS COLUMNAS GRID ************************/
function p107SetHeadersTitles(gridP107){
	var colModel = gridP107.cm;
	$.each(colModel, function(i) {
		if (colModel[i].name!="rn"){
			$("#jqgh_gridP107AlarmasPLU_"+colModel[i].name).attr("title", colModel[i].index);
		}
	});
}

/************************* FORMATEADORES DE COLUMNAS **************************/


function fechaFormatter(cellValue, opts, rData){
	if(cellValue == null || cellValue.toString() == ""){
		return "";
	}else{
		var fecha = cellValue.split("-");
		return fecha[2] + "-" + fecha[1] + "-" + fecha[0];
	}
}

function tooltipFormatter(cellValue, opts, rData){
	if(cellValue == null || cellValue.toString() == "" || cellValue==0){
		return "";
	}else{
		if (cellValue==1){
			return "<label title='1 caja pendiente de recibir MAÑANA'>*</label>";
		}else{
			return "<label title='"+cellValue+" cajas pendientes de recibir MAÑANA'>*</label>";
		}
		
	}
}

function pluFormatter(cellValue, opts, rData){
	var lstOtrasAgrupacionesBalanza = rData['lstOtrasAgrupacionesBalanza'];
	if (lstOtrasAgrupacionesBalanza!=null){
		return "<label class='p107_bold' title='Otras Agrupaciones:"+lstOtrasAgrupacionesBalanza+"'>"+cellValue+"</label>";
	}else{
		return "<label title='"+cellValue+"'>"+cellValue+"</label>";
	}
		
	
}

function formatDateHora(timestamp){
	if(timestamp == null || timestamp.toString() == ""){
		return "";
	}else{
		var date = new Date(timestamp);
		var hours = date.getHours();
		var minutes = date.getMinutes();
		var seconds = date.getSeconds();
		return hours + ":" + minutes + ":" + seconds;
	}
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************* FUNCIONES DE P107 **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
//Función que sirve para paginar o reordenar los elementos del grid.
function loadData_gridP107AlarmasPLU(recarga, referencia) {
	// Al cargar sin recarga ponemos que se muestre la pagina 1
	if (recarga=="N"){
		$(gridP107.nameJQuery).setGridParam({page:1});
	}
	//Poner ancho de grid, para que aunque haya más o menos columnas siempre ocupe lo mismo
	$(gridP107.nameJQuery).jqGrid('setGridWidth',980,true);
	var filtroReferencia = "";
	if (referencia!=null && referencia!=""){
		filtroReferencia="&filtroReferencia="+referencia
	}
	var url = './alarmasPLU/loadDataGrid.do' + createParamsUrl() + filtroReferencia + '&page='+gridP107.getActualPage()+'&max='+gridP107.getRowNumPerPage()+'&index='+gridP107.getSortIndex()+'&sortorder='+gridP107.getSortOrder()+'&recarga='+recarga;
	
	$.ajax({
		type : 'GET',			
		url : url,
		success : function(data) {	
			
			if(data != null){
				configurarGrid(data);
				$("#p107_AreaResultados").show();
			}
			$("#gbox_gridP107AlarmasPLU .ui-pg-input").show();
			
			clear_messages();
			load_contadores();
			load_plus_libres();
			check_teclas_balanza();
			color_a_filas();
			
			$("#p107_plu_libre").val("");
			$("#p107_referencia").val("");
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});		
}

function checkCargando(){
	var codCentro = $("#centerId").val();
	var url = './alarmasPLU/checkCargando.do?codCentro='+codCentro;
	
	$.ajax({
		type : 'GET',			
		url : url,
		success : function(data) {	
			if(data){
				createAlert("Se están actualizando los datos de los PLUs para el centro "+codCentro+". Recargue la página después de unos instantes.","INFO");
				reset_cmb("#p107_cmb_area");
				reset_cmb("#p107_cmb_seccion");
				reset_cmb("#p107_cmb_agrupacion");
			}else{
				load_cmbArea();
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});		
	

	
}

function color_a_filas(){
	var rowIds = $(gridP107.nameJQuery).jqGrid('getDataIDs');
    for (i = 1; i <= rowIds.length; i++) {//iterate over each row
        var rowData = $(gridP107.nameJQuery).jqGrid('getRowData', i);
        
        var estado = rowData['estadoGrid'];
        if (estado == 1){
        	$(gridP107.nameJQuery).jqGrid('setRowData', i, false, "p107_backgroundRed");
        	// DESHABILITAR EL CAMPO ELIMINAR Y REASIGNAR
        	$('#p107_reasignar'+i).prop("disabled",true);
        	$('#p107_eliminar'+i).prop("disabled",true);
        	$('#p107_reasignar'+i).addClass("p107_hidden");
        	$('#p107_eliminar'+i).addClass("p107_hidden");
        }else if (estado == 2){
        	$(gridP107.nameJQuery).jqGrid('setRowData', i, false, "p107_backgroundYellow");
        	// HABILITAR EL CAMPO ELIMINAR Y REASIGNAR
        	$('#p107_reasignar'+i).prop("disabled",false);
        	$('#p107_eliminar'+i).prop("disabled",false);
        	$('#p107_reasignar'+i).removeClass("p107_hidden");
        	$('#p107_eliminar'+i).removeClass("p107_hidden");
        }else{
        	$(gridP107.nameJQuery).jqGrid('setRowData', i, false, "p107_backgroundGreen");
        	// HABILITAR EL CAMPO ELIMINAR Y REASIGNAR
        	$('#p107_reasignar'+i).prop("disabled",false);
        	$('#p107_eliminar'+i).prop("disabled",false);
        	$('#p107_reasignar'+i).removeClass("p107_hidden");
        	$('#p107_eliminar'+i).removeClass("p107_hidden");
        }
       
    } 
}

function load_contadores(){
	
	$.ajax({
		type : 'GET',			
		url : './alarmasPLU/loadContadores.do' + createParamsUrl(),
		success : function(data) {	
			if(data != null){
				var counter1=data[0];
				var counter2=data[1];
				var counter3=data[2];
				
				$('#p107_counter1').val(counter1);
				$('#p107_counter2').val(counter2);
				$('#p107_counter3').val(counter3);
			}else{
				$('#p107_counter1').val("ERROR");
				$('#p107_counter2').val("ERROR");
				$('#p107_counter3').val("ERROR");
			}

		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});		
}

function load_plus_libres(){
	
	$.ajax({
		type : 'GET',			
		url : './alarmasPLU/loadPLUsLibres.do' + createParamsUrl(),
		success : function(data) {	
			if(data != null){
				var itemsStr="";
				data.forEach(function(item, index) {
					// La primera posición de la lista de PLUs contiene el Nº Máximo de PLUs de la balanza.
					if (index==0){
						// Si el valor del Nº máximo de PLUs viene negativo significa que no existía
						// el registro en la tabla y por lo tanto hay que marcar el valor en rojo.
						if (item < 0){
							item = (-1)*item;
							$("#p107_plu_max_libre").attr("style", "color:red");
						}
						$('#p107_plu_max_libre').val(item);
						$('#p107_plu_max_libre_copia').val(item);
					// La segunda posición de la lista de PLUs contiene el Nº Máximo de PLUs asignado de la balanza.
					}else if (index==1){
						$('#p107_plu_max_asignado').val(item);
					}else{
						itemsStr+= item+", ";
					}
				});						
				
				itemsStr = itemsStr.slice(0,-2);
				
				$('#p107_free_plus').prop("title", function() {
				    return "Lista de PLUs libres: " + itemsStr;
				});
				
				// Comienzo apartir de la 3 posición ya que las dos primera están reservadas para
				// el Nº Máximo de PLUs de la balanza y el nº máximo de PLU asignado.
				var items = data.slice(2,6);
				itemsStr="";
				items.forEach(function(item, index) {
					itemsStr+= item+", ";
				});	
				// Borramos la coma del final
				itemsStr = itemsStr.slice(0,-2);
				if (data.length>4){
					itemsStr = itemsStr+"...";
				}
				$('#p107_free_plus').text(itemsStr);

				
			}

		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});	
	
	
}

function createParamsUrl(){
	var codCentro = $("#centerId").val();
	var url = "?codCentro="+codCentro;
	
	var codArea = $("#p107_cmb_area").val();
	var codSeccion = $("#p107_cmb_seccion").val();
	var codAgrupacion = $("#p107_cmb_agrupacion").val();
	
	if (codArea!=null && codArea != ""){
		url += "&codArea="+codArea;
	}
	if (codSeccion!=null && codSeccion != ""){
		url +=  "&codSeccion="+codSeccion;
	}
	if (codAgrupacion!=null){
		url +=  "&agrupacion="+codAgrupacion;
	}
	return url;
}

function check_teclas_balanza(){
			
	$.ajax({
		type : 'GET',			
		url : './alarmasPLU/checkTeclasBalanza.do' + createParamsUrl(),
		success : function(data) {	
			if(data!=null && data != ""){
				itemsStr = 'HAY MÁS REFERENCIAS DE "' + data + '" CON STOCK QUE TECLAS TIENE LA BALANZA';
				
				$('#p107_alert_referencias').text(itemsStr);
				$('#p107_alert_referencias').show();
				
			}

		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});	
	
}


//Función que inserta eventos focusout, formateadores de expresiones regulares, ordenaciones de columnas, colores y datos al grid.
function configurarGrid(data){
	//Se rellena el grid
	$(gridP107.nameJQuery)[0].addJSONData(data);
	gridP107.actualPage = data.page;
	gridP107.localData = data;
	$("#jqgh_gridP107AlarmasPLU_codArticulo").attr("title","Código de referencia");
	$("#jqgh_gridP107AlarmasPLU_denominacion").attr("title","Nombre de la referencia");
	$("#jqgh_gridP107AlarmasPLU_grupo3").attr("title","Código de categoría");
	$("#jqgh_gridP107AlarmasPLU_grupo4").attr("title","Código de subcategoría");
	$("#jqgh_gridP107AlarmasPLU_grupo5").attr("title","Código de segmento");
	$("#jqgh_gridP107AlarmasPLU_denomSegmento").attr("title","Nombre del segmento");
	$("#jqgh_gridP107AlarmasPLU_grupoBalanza").attr("title","Grupo de balanza");
	$("#jqgh_gridP107AlarmasPLU_diasCaducidad").attr("title","Días de caducidad");
	$("#jqgh_gridP107AlarmasPLU_fechaUltimaVenta").attr("title","Fecha de última venta");
	$("#jqgh_gridP107AlarmasPLU_reasignar").attr("title","Reasignar el PLU de esta referencia a otra: introduzca un código de referencia.");

}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************* EVENTOS DE P107 **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Evento de botón buscar
function events_p107_btn_buscar(){
	$("#p107_btn_search").click(function () {
		var agrupacionBalanza = $("#p107_cmb_agrupacion").val();
		if (agrupacionBalanza==null || agrupacionBalanza==''){
			createAlert("Debe seleccionar una agrupación de balanza","ERROR");
		}else{
			$("#p107_AreaResultados").hide();
			$("#gbox_gridP107AlarmasPLU #gs_codArticulo").val("");
			$("#p107_plu_max_libre").attr("style", "color:gray");
			loadData_gridP107AlarmasPLU('N', null);
			$("#p107_btn_excel").prop("disabled", false);
			if (userPerfilMisumi!=3){
				$("#p107_btn_save").prop("disabled", false);
				}
		}

	});
}

function events_p107_btn_exportExcel(){
	$("#p107_btn_excel").click(function () {	
		exportExcel();
	});	
	
}

function events_p107_btn_limpiar(){
	$("#p107_btn_clean").click(function () {
		$("#p107_plu_libre").val("");
		$("#p107_referencia").val("");
		$("#p107_AreaResultados").hide();
		$("#p107_btn_save").prop("disabled", true);
		$("#p107_plu_max_libre").attr("style", "color:gray");
		reset_cmb("#p107_cmb_area");
		reset_cmb("#p107_cmb_seccion");
		reset_cmb("#p107_cmb_agrupacion");
		load_cmbArea();
	});	
	
}



function imprimir(data){

	if (data.length>0){
		var codArts = data.join(",");
		var url = "./alarmasPLU/imprimir.do?etiquetas="+data;
		$.ajax({
			type : 'GET',			
			url : url,
			success : function(data) {	
				$("#p107_btn_search").click();
			},
			error : function (xhr, status, error){
				handleError(xhr, status, error, locale);				
			}			
		});
	}else{
		$("#p107_btn_search").click();
	}
	
}

function eliminar_guardar_e_imprimir(){
	var rowIds = $(gridP107.nameJQuery).jqGrid('getDataIDs');
	var referencias = [];
	
    for (i = 1; i <= rowIds.length; i++) {//iterate over each row
        var isDelete = $("#p107_eliminar"+i).prop("checked");
        if (isDelete){
        	var rowData = $(gridP107.nameJQuery).jqGrid('getRowData', i);
        	var ref = rowData['codArticulo'];
        	referencias.push(ref.toString());
        }
    } 
    
    if (referencias.length > 0){
		$('#p107_mensajeDialogConfirm').text();
		
		$(function() {
			 $( "#p107dialog-confirm" ).dialog({
				 resizable: false,
				 height:150,
				 modal: true,
				 buttons: {
					 "TODAS": function() {
						 var codCentro = $("#centerId").val();
						 var grupoBalanza = $("#p107_cmb_agrupacion").val();
						 var urlDelete = './alarmasPLU/eliminar.do?codCentro='+codCentro+'&grupoBalanza='+grupoBalanza+'&referencias='+referencias.join(',');
						 $.ajax({
							 type : 'GET',			
							 url : urlDelete,
							 success : function() {	
								 guardarTodos();
								 $("#p107_plu_max_libre").attr("style", "color:gray");
							 },
							 error : function (xhr, status, error){
								 handleError(xhr, status, error, locale);				
							 }			
						 });	
						 $('#p107_mensajeDialogConfirm').text();
						 $(this).dialog( "close" );
					 },
					 "SOLO ESTA": function() {
						 var codCentro = $("#centerId").val();
						 var grupoBalanza = $("#p107_cmb_agrupacion").val();
						 var urlDelete = './alarmasPLU/eliminar.do?codCentro='+codCentro+'&grupoBalanza='+grupoBalanza+'&referencias='+referencias.join(',');
						 $.ajax({
							 type : 'GET',			
							 url : urlDelete,
							 success : function() {	
								 guardar();
								 $("#p107_plu_max_libre").attr("style", "color:gray");
							 },
							 error : function (xhr, status, error){
								 handleError(xhr, status, error, locale);				
							 }			
						 });	
						 $('#p107_mensajeDialogConfirm').text();
						 $(this).dialog( "close" );
					 }
				 }
			 });
		});
    }else{
    	guardar();
    	$("#p107_plu_max_libre").attr("style", "color:gray");
    }
	
}

function guardar(){
	var codCentro = $("#centerId").val();
	var agru = $("#p107_cmb_agrupacion").val();
	var respuesta;
	$.ajax({
		type : 'GET',			
		url : './alarmasPLU/guardar.do?codCentro='+codCentro+'&agrupacionBalanza='+agru,
		contentType : "application/json; charset=utf-8",
		async:false,
		success : function(data) {	
			imprimir(data);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}
	});
}

function guardarTodos(){
	var codCentro = $("#centerId").val();
	var agru = $("#p107_cmb_agrupacion").val();
	var respuesta;
	$.ajax({
		type : 'GET',			
		url : './alarmasPLU/guardarTodos.do?codCentro='+codCentro+'&agrupacionBalanza='+agru,
		contentType : "application/json; charset=utf-8",
		async:false,
		success : function(data) {	
			imprimir(data);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}
	});
}

function events_p107_btn_guardar(){
	$("#p107_btn_save").click(function () {
		eliminar_guardar_e_imprimir();
	});
}

function actualizarPLU(maxPlu){
	var codCentro = $("#centerId").val();
	var agru = $("#p107_cmb_agrupacion").val();
	var maxPLU = $("#p107_plu_max_libre").val();
	$.ajax({
		type : 'POST',			
		url : './alarmasPLU/actualizarPLU.do?codCentro='+codCentro+'&agrupacionBalanza='+agru+'&numMaxPLU='+maxPlu,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
//		async:false,
		success : function(data) {
			// Actualizo el valor de copia con el nuevo valor Max de PLU.
			$("#p107_plu_max_libre_copia").val(maxPLU);
			$("#p107_plu_max_libre").attr("style", "color:gray");
//			imprimir(data);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
			var maxPluCopia = $("#p107_plu_max_libre_copia").val();
			$("#p107_plu_max_libre").val(maxPluCopia);
			$("#p107_plu_max_libre").attr("style", "color:red");
		}
	});
}

function events_p107_plu_max_libre(){
	
	$("#p107_plu_max_libre").keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	});

	$("#p107_plu_max_libre").focusout(function(){
		var maxPlu = $("#p107_plu_max_libre").val();
		var maxPluCopia = $("#p107_plu_max_libre_copia").val();

		if (maxPlu != maxPluCopia){
			guardarMaxPLU(false);
		}
	}).keyup(function(e){
		var maxPlu = $("#p107_plu_max_libre").val();
		var maxPluCopia = $("#p107_plu_max_libre_copia").val();

		if(e.which == 13 && maxPlu != maxPluCopia) {
			guardarMaxPLU(false);
		}
	});
	
}

function events_p107_btn_asignar(){
	$("#p107_referencia").prop("disabled",true);
	$("#p107_btn_asignar").addClass("p107_hidden");
	
	$("#p107_plu_libre").keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	});
	
	$("#p107_referencia").keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	});
	
	$("#p107_plu_libre").focusout(function(){
		var plu = $("#p107_plu_libre").val();
		if (plu!=""){
			var validation = validate_plu(plu);
			if (validation!=""){
				$("#p107_mensaje_asignar_plu").text(validation);
				$("#p107_mensaje_asignar_plu").show();
				$("#p107_referencia").prop("disabled",true);
				$("#p107_referencia").val("");
				$("#p107_btn_asignar").addClass("p107_hidden");
			}else{
				$("#p107_mensaje_asignar_plu").hide();
				$("#p107_referencia").val("");
				$("#p107_referencia").prop("disabled",false);
			}
		}
	});
	
	$("#p107_plu_libre").keyup(function(){
		var plu = $("#p107_plu_libre").val();
		if (plu!=""){
			var validation = validate_plu(plu);
			if (validation!=""){
				$("#p107_btn_asignar").addClass("p107_hidden");
				$("#p107_referencia").val("");
				$("#p107_referencia").prop("disabled",true);
			}else{
				$("#p107_mensaje_asignar_plu").hide();
				$("#p107_referencia").val("");
				$("#p107_btn_asignar").addClass("p107_hidden");
				$("#p107_referencia").prop("disabled",false);
			}
		}else{
			$("#p107_mensaje_asignar_plu").hide();
			$("#p107_referencia").val("");
			$("#p107_btn_asignar").addClass("p107_hidden");
			$("#p107_referencia").prop("disabled",true);
		}
	});
	
	$("#p107_referencia").focusout(function(){
		validate_referencia();
	}).keyup(function(e){
		if(e.which == 13) {
			validate_referencia();
		}else{
			$("#p107_btn_asignar").addClass("p107_hidden");
			$("#p107_mensaje_asignar_plu").hide();
			$("#p107_btn_alta").addClass("p107_hidden");
		}
	});
	
	$("#p107_btn_asignar").click(function () {
		var plu = $("#p107_plu_libre").val();
		var ref = $("#p107_referencia").val();
		var agru = $("#p107_cmb_agrupacion").val();
		ajax_call_asignar(plu,ref,agru);
	});	
	
	$("#p107_btn_alta").click(function () {
		var plu = $("#p107_plu_libre").val();
		var ref = $("#p107_referencia").val();
		var agru = $("#p107_cmb_agrupacion").val();
		ajax_call_alta(plu,ref,agru);
	});
}

function esErrorMaxPLU(maxPlu){
	if (maxPlu < 10 || maxPlu > 999){
		return true;
	}else{
		return false;
	}
}

function guardarMaxPLU(){
	var maxPlu = $("#p107_plu_max_libre").val();
	var maxPluCopia = $("#p107_plu_max_libre_copia").val();
	
	var maxPluAsignado = $("#p107_plu_max_asignado").val();
	
	if (maxPlu!=""){
		
		if (esErrorMaxPLU(maxPlu)){
//			$("#p107_mensaje_error_plu_max").show();
			var displayPopUp = document.getElementById('popupShowMessage').style.display;
			
			if(displayPopUp=='none'){
				$("#p107_plu_max_libre").val(maxPluCopia);
				$("#p107_plu_max_libre").attr("style", "color:red");
				createAlert("El valor debe estar comprendido entre 10 y 999.", "ERROR");
			}
		}else{
//			$("#p107_mensaje_error_plu_max").hide();
			
			// Si se ha modificado el Nº Max de PLU se actualiza en la BBDD.
			if (maxPlu != maxPluCopia){

				var pluAsignado = hayPLUAsignado(maxPlu, maxPluAsignado);
				var displayPopUp = document.getElementById('popupShowMessage').style.display;
				
				if (pluAsignado && displayPopUp=='none'){
					$("#p107_plu_max_libre").val(maxPluCopia);
					$("#p107_plu_max_libre").attr("style", "color:red");
					createAlert("Existe al menos un Nº de PLU asignado mayor que el máximo indicado.", "ERROR");
				}else{
					// Preparar llamada AJAX a /actualizarPLU
					actualizarPLU(maxPlu);
				}

			}
		}

	}
}

// Determina si el Nº Max de PLUs es inferior a un PLU ya asignado.
function hayPLUAsignado(maxPlu, maxPluAsignado){
	if (parseInt(maxPlu) < parseInt(maxPluAsignado)){
		return true;
	}else{
		return false;
	}
}

function validate_plu(plu){
	var lista_plus_libres = $("#p107_free_plus").prop("title");
	lista_plus_libres = lista_plus_libres.split(":")[1];
	lista_plus_libres = lista_plus_libres.split(",");
	
	var found = false;
	$.each(lista_plus_libres, function(index,value){
		var item = lista_plus_libres[index].trim();
		if (item == plu){
			found = true;
			return;
		}
	});
	if (!found){
		return "El PLU introducido no está libre";
	}else{
		return "";
	}
	
}

function validate_referencia(){
	var referencia = $("#p107_referencia").val();
	if (referencia!=""){
		// BUSCAR ENTRE LAS REFERENCIAS DE LA TABLA
		if (!find_referencia(referencia)){
			// SI NO ESTA, LLAMAR AL WS PARA BUSCAR EN GISAE
			var codCentro = $("#centerId").val();
	    	var grupoBalanza = $("#p107_cmb_agrupacion").val();
	    	var url = './alarmasPLU/checkReferencia.do?codCentro='+codCentro+'&grupoBalanza='+grupoBalanza+'&referencia='+referencia;
			$.ajax({
				type : 'GET',			
				url : url,
				success : function(data) {	
					if (data!=""){
						var codigoAviso = null;
						codigoAviso = data.split("**");
						codigo = codigoAviso[0];
						aviso = codigoAviso[1]; 
						if(codigo != null && codigo == '0'){
							$("#p107_mensaje_asignar_plu").text(aviso);
							$("#p107_mensaje_asignar_plu").show();
							$("#p107_btn_asignar").addClass("p107_hidden");
							$("#p107_btn_alta").addClass("p107_hidden");
						}else if(codigo != null && codigo == '1'){
							$("#p107_mensaje_asignar_plu").hide();
							$("#p107_btn_asignar").addClass("p107_hidden");
							$("#p107_btn_alta").removeClass("p107_hidden");
						}
					}else{
						$("#p107_mensaje_asignar_plu").hide();
						$("#p107_btn_asignar").removeClass("p107_hidden");
						$("#p107_btn_alta").addClass("p107_hidden");
					}
				},
				error : function (xhr, status, error){
					handleError(xhr, status, error, locale);				
				}			
			});	
			
			//return "Referencia NO susceptible de introducir PLU";
		}else{
			$("#p107_mensaje_asignar_plu").hide();
			$("#p107_btn_asignar").removeClass("p107_hidden");
		}
	
	}
	
}

function find_referencia(referencia){
	var rowIds = $(gridP107.nameJQuery).jqGrid('getDataIDs');
    for (i = 1; i <= rowIds.length; i++) {//iterate over each row
        var rowData = $(gridP107.nameJQuery).jqGrid('getRowData', i);
        if (rowData['codArticulo'] == referencia){
        	return true;
        }
    }
	return false;
}

function ajax_call_asignar(plu, referencia,agru){
	var codCentro = $("#centerId").val();
	$.ajax({
		type : 'GET',			
		url : './alarmasPLU/asignar.do?codCentro='+codCentro+'&plu='+plu+'&referencia='+referencia+'&grupoBalanza='+agru,
		success : function() {	
			$("#p107_referencia").prop("disabled",true);
			$("#p107_btn_asignar").addClass("p107_hidden");
			loadData_gridP107AlarmasPLU('S',null);
			//$("#p107_btn_search").click();
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});	
	
}

function ajax_call_alta(plu, referencia,agru){
	var codCentro = $("#centerId").val();
	$.ajax({
		type : 'GET',			
		url : './alarmasPLU/alta.do?codCentro='+codCentro+'&plu='+plu+'&referencia='+referencia+'&grupoBalanza='+agru,
		success : function(data) {	
			if(data==""){
				validate_referencia();
				$("#p107_mensaje_asignar_plu").hide();
				$("#p107_btn_asignar").removeClass("p107_hidden");
				$("#p107_btn_alta").addClass("p107_hidden");
			}else{
				$("#p107_mensaje_asignar_plu").text(data);
				$("#p107_mensaje_asignar_plu").show();
				$("#p107_btn_asignar").addClass("p107_hidden");
				$("#p107_btn_alta").addClass("p107_hidden");
			}
			
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});	
	
}

function exportExcel(){
	exportarAExcel = true;
	var colModel = $(gridP107.nameJQuery).jqGrid("getGridParam", "colModel");
	
    var myColumnsNames = new Array();
  
    $.each(colModel, function(i) {
    	if (!colModel[i].hidden && !colModel[i].editable ){
    		myColumnsNames.push(colModel[i].index)
    	}
    });
    // Incluimos el estado de la fila para que se muestren los colores en el excel
    myColumnsNames.push("HIDDEN_STATUS");
    
    var sortIndex = gridP107.getSortIndex();
    var sortAscDesc = gridP107.getSortOrder();
    
    $form = $("<form>");
    $form.attr("action","./alarmasPLU/exportGrid.do");
    $form.attr("accept-charset","ISO-8859-1");
    $form.attr("method","GET");
    $form.append("<input type='hidden' name='headers' value='"+myColumnsNames+"'>");
    
	$form.append("<input type='hidden' name='centro' value='"+$("#centerId").val()+"'>");
	$form.append("<input type='hidden' name='centroStr' value='"+$("#centerName").val()+"'>");
    
	if (sortIndex!=null){
		$form.append("<input type='hidden' name='index' value='"+sortIndex+"'>");
	}
	if (sortAscDesc!=null){
		$form.append("<input type='hidden' name='sortorder' value='"+sortAscDesc+"'>");
	}
	if ($("#p107_cmb_area").val()!=null && $("#p107_cmb_area").val()!="" ){
		$form.append("<input type='hidden' name='area' value='"+$("#p107_cmb_area").find('option:selected').val()+"'>");
		$form.append("<input type='hidden' name='areaStr' value='"+$("#p107_cmb_area").find('option:selected').text()+"'>");
	}	
	
	if ($("#p107_cmb_seccion").val()!=null && $("#p107_cmb_seccion").val()!="" ){
		$form.append("<input type='hidden' name='seccion' value='"+$("#p107_cmb_seccion").find('option:selected').val()+"'>");
		$form.append("<input type='hidden' name='seccionStr' value='"+$("#p107_cmb_seccion").find('option:selected').text()+"'>");
	}	
	
	if ($("#p107_cmb_agrupacion").val()!=null && $("#p107_cmb_agrupacion").val()!="" ){
		$form.append("<input type='hidden' name='agrupacion' value='"+$("#p107_cmb_agrupacion").find('option:selected').val()+"'>");
		$form.append("<input type='hidden' name='agrupacionStr' value='"+$("#p107_cmb_agrupacion").find('option:selected').text()+"'>");
	}		
	$form.append('</form>');
		
	$form.appendTo(document.body).submit().remove();	 
	
}
