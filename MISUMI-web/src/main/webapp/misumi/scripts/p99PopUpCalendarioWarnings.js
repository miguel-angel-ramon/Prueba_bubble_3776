var gridP99CalendarioWarnings=null;
var p99AvisoBorradoTodo = null;
var p99CambiadosDatos = false;

$(document).ready(function(){

	initializeScreenP99PopupCalendarioWarnings();
});

function loadP99CalendarioWarnings(locale, datos){
	var jqxhr = $.getJSON('./misumi/resources/p99PopUpCalendarioWarnings/p99PopUpCalendarioWarnings_' + locale + '.json',
			function(data) {

	})
	.success(function(data) {
		p99AvisoBorradoTodo = data.avisoBorradoTodo;
		
		gridP99CalendarioWarnings = new GridP99CalendarioWarnings(locale);
		gridP99CalendarioWarnings.title = data.p99GridTitle;
		gridP99CalendarioWarnings.emptyRecords= data.p99EmptyRecords;
		gridP99CalendarioWarnings.colNames = data.p99ColNames;

		load_gridP99CalendarioWarningsMock(datos)
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});

}

function initializeScreenP99PopupCalendarioWarnings(){

	$( "#p99_popupCalendarioWarnings" ).dialog({
		autoOpen: false,
		height: "auto",
		width: "auto",
		modal: true,
		resizable: false,
		buttons:[{
			text: "Aceptar",
			click: function() {
				$(this).dialog('close');
				if (p99CambiadosDatos){
					load_diasCalendarioP96(comboValueAnioP96,comboValueServicioP96,null,"S",mesAnioLst[idxActual],null);
				}
			}
		}],
		open: function() {
			p99CambiadosDatos = false;
			$(gridP99CalendarioWarnings.nameJQuery).jqGrid('setGridWidth',600, true);
			$('.ui-dialog-titlebar-close').on('mousedown', function(){
				$('#p99_popupCalendarioWarnings').dialog('close');
				if (p99CambiadosDatos){
					load_diasCalendarioP96(comboValueAnioP96,comboValueServicioP96,null,"S",mesAnioLst[idxActual],null);
				}
			});
		}
	});

	$(window).bind('resize', function() {
		$("#p99_popupCalendarioWarnings").dialog("option", "position", "center");
	});
}

function load_gridP99CalendarioWarningsMock(datos) {

	$(gridP99CalendarioWarnings.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		loadonce: true,
		colNames : gridP99CalendarioWarnings.colNames,
		colModel :[
	           {
	        	   "name"  : "fechaAfectadaDDMMYYYY",
	        	   "index" : "fechaAfectadaDDMMYYYY",
	        	   "width" : 80,
	        	   "formatter":p99FechaFormatter,
	        	   "align" : "center",
	        	   "sortable" : false
	           },{
	        	   "name"  : "descripcionAviso",
	        	   "index" : "descripcionAviso",
	        	   "width" : 500,
	        	   "align" : "center"	,
	        	   "sortable" : false						
	           },{
	        	   "name"  : "leido",
	        	   "index" : "leido",
	        	   "width" : 40,
	        	   "fixed": true,
	        	   "formatter": p99CheckEliminadoWarningFormatter,
	        	   "align" : "center",
	        	   "sortable" : false
	           }
	           ], 
		data:datos,
        datatype: "local",
        height: 200,
        scroll: true,
        scrollbar:'auto',
        rowNum:1000,
        gridview: true,
        autowidth : true,
        shrinkToFit:true,
        gridComplete : function() {
        	gridP99CalendarioWarnings.headerHeight("p99_gridHeader");
		},
        loadError : function (xhr, status, error){
        	handleError(xhr, status, error, locale);
        }
	});

}

function p99FechaFormatter(cellvalue, options, rowObject) {
    var fechaAfectadaDDMMYYYY = cellvalue;
	var diaFechaSt = fechaAfectadaDDMMYYYY.substring(0,2);
	var mesFechaSt = fechaAfectadaDDMMYYYY.substring(2,4);
	var anyoFechaSt = fechaAfectadaDDMMYYYY.substring(4);
 
	var diaFecha = parseInt(diaFechaSt,10);
	var mesFecha = parseInt(mesFechaSt,10);
	var anyoFecha = parseInt(anyoFechaSt,10);
	var fechaCompleta = anyoFecha + "-" + mesFecha + "-" + diaFecha
	fechaPrevisEntFormateada = $.datepicker.formatDate("dd/mm/yy", devuelveDate(fechaCompleta),{
		dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
		dayNames: $.datepicker.regional[ "es" ].dayNames,
		monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
		monthNames: $.datepicker.regional[ "es" ].monthNames
		});

    return fechaPrevisEntFormateada;
}

function p99CheckEliminadoWarningFormatter(cellValue, opts, rowObject) {

// Inicio MISUMI-294. ECG 28-10-2021
//	var imagen = "<img src='./misumi/images/dialog-cancel-16.gif' onclick=\"p99EliminarWarning('"+opts.rowId+"','"+ rowObject['fechaAfectadaDDMMYYYY']+"')\" class=\"p99_borrarAviso\"/>" ;
	var imagen;
	if (rowObject['leido']=='N'){
		imagen = "<img src='./misumi/images/dialog-cancel-16.gif' onclick=\"p99EliminarWarning('"+opts.rowId+"','"+ rowObject['fechaAfectadaDDMMYYYY']+"')\" class=\"p99_borrarAviso\"/>" ;
	} else{
		imagen = "";
	}
// FIN MISUMI-294. ECG 28-10-2021
		
	return imagen;
}

function p99EliminarWarning(fila, fecha) {
	//Miramos el tipo y código de ejercicio.
	var codigoEjercicio = comboValueAnioP96;
	var tipoEjercicio = $("#tipoEjercicio"+codigoEjercicio).val();
	var anioEjercicio = $("#anioEjercicio"+comboValueAnioP96).val();
	
	var options = "";
	$.ajax({
		type : 'POST',
		url : './calendario/popup/warnings/eliminarWarning.do?fecha='+fecha+'&codigoEjercicio='+anioEjercicio+'&tipoEjercicio='+tipoEjercicio,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		async :false,
		success : function(data) {
			if(data != null && data.codError == 0){
				p99CambiadosDatos = true;
				$(gridP99CalendarioWarnings.nameJQuery).jqGrid('delRowData',fila);
				$(gridP99CalendarioWarnings.nameJQuery).jqGrid('setGridWidth',600, true);
				p96NumAvisos = p96NumAvisos -1;
				if (p96NumAvisos == 0){
					createAlert(replaceSpecialCharacters(p99AvisoBorradoTodo), "INFO");
					$(".p96_aviso").removeClass("parpadeo");
					$("#p96_btn_avisosBloque").css('visibility', 'hidden');
					$(".p96_aviso").css('visibility', 'hidden');
				}
			}					
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});	
}

//**************
function GridP99CalendarioWarnings (locale){
	// Atributos
	this.name = "gridP99CalendarioWarnings"; 
	this.nameJQuery = "#gridP99CalendarioWarnings"; 
	this.i18nJSON = './misumi/resources/p99PopUpCalendarioWarnings/p99PopUpCalendarioWarnings_' + locale + '.json';
	this.colNames = null;

	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP99CalendarioWarnings"; 
	this.pagerNameJQuery = "#pagerP99CalendarioWarnings";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;

	//Metodos
	this.getActualPage = function getActualPage(){ 
		if (this.actualPage == null)
			this.actualPage = 1;
		else
			this.actualPage = $(this.nameJQuery).getGridParam('page');

		return this.actualPage;
	}

	this.getSelectedRow = function getSelectedRow(){ 
		return $(this.nameJQuery).getGridParam('selrow'); 
	}

	this.getCellValue = function getCellValue(rowId, colName){ 
		return $(this.nameJQuery).getRowData(rowId)[colName]; 
	}

	this.getRowNumPerPage = function getRowNumPerPage () {
		return $(this.nameJQuery).getGridParam('rowNum');
	} 

	this.getSortIndex = function getSortIndex () {
		if ($(this.nameJQuery).getGridParam('sortname')!=null){
			return $(this.nameJQuery).getGridParam('sortname');
		}else{
			return "fechaAfectada";
		}
	} 

	this.getSortOrder = function getSortOrder () {

		if ($(this.nameJQuery).getGridParam('sortorder')!=null){
			return $(this.nameJQuery).getGridParam('sortorder');
		}else{
			return "asc";
		}
	} 	
	
	this.headerHeight = function headerHeight(cssClass) {
		var colModel = $(this.nameJQuery).jqGrid('getGridParam','colModel');
		var colNames = $(this.nameJQuery).jqGrid('getGridParam','colNames');
		for (i = 0; i < colNames.length; i++){
			$("#gridP99CalendarioWarnings").setLabel(colModel[i].name, colNames[i], cssClass);
		}
	}
}

function reloadDataP99CalendarioWarnings(datosWarnings) {
	loadP99CalendarioWarnings(locale, datosWarnings.listaWarnings);
}