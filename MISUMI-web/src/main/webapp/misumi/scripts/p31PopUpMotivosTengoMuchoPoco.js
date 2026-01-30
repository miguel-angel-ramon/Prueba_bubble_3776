var p31BotonAceptar = null;
var gridP31Motivo=null;
var tituloVentanaPopupMotivosTengoMuchoPoco = null;
var tituloVentanaPopupMotivosTengoMucho = null;
var tituloVentanaPopupMotivosTengoPoco = null;

var recordMotivoTengoMuchoPocoP31 = null;

$(document).ready(function(){

	loadP31(locale);
});

function initializeScreenPopupMotivosTengoMuchoPoco(){
	
	$( "#p31_popupMotivosTengoMuchoPoco" ).dialog({
        autoOpen: false,
        height: 'auto',
        width: 'auto',
        modal: true,
        title: tituloVentanaPopupMotivosTengoMuchoPoco,
        resizable: false,
        dialogClass: "popupResaltado",
        buttons:[{
            text: p31BotonAceptar,
            click: function() {
                      $(this).dialog('close');
                   }
        }],
		open: function() {
			$('.ui-dialog-titlebar-close').on('mousedown', function(){
				
				$("#p31_popupMotivosTengoMuchoPoco").dialog('close');
				
				
			});
		}
    });
	
	$(window).bind('resize', function() {
	    $("#p31_popupMotivosTengoMuchoPoco").dialog("option", "position", "center");
	});
}

function loadP31(locale){
	gridP31Motivo = new GridP31Motivo(locale);
	var jqxhr = $.getJSON(gridP31Motivo.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				tituloVentanaPopupMotivosTengoMuchoPoco = data.tituloVentanaPopupMotivosTengoMuchoPoco;
				tituloVentanaPopupMotivosTengoMucho = data.tituloVentanaPopupMotivosTengoMucho;
				tituloVentanaPopupMotivosTengoPoco = data.tituloVentanaPopupMotivosTengoPoco;
				p31BotonAceptar = data.p31BotonAceptar;
				gridP31Motivo.colNames = data.p31MotivoColNames;
				gridP31Motivo.emptyRecords= data.emptyRecords;

				initializeScreenPopupMotivosTengoMuchoPoco();
				
				loadP31MotivosMock(gridP31Motivo);
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
         });
}

function loadP31MotivosMock(gridP31Motivo) {
	 
	//para hacer cabeceras
	$(gridP31Motivo.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		datatype : 'local',
		contentType : 'application/json',
		mtype : 'POST',
		colNames : gridP31Motivo.colNames,
		colModel :[
		   		{
					"name"  : "descripcion",
					"index" : "descripcion", 
					"width" : 380,
					"formatter": p31MotivoFormatter,
					"sortable" : false
				}
			],  
		rowNum : 4,
		height : "100%",
		autowidth : true,
		width : "auto",
		pginput: false,
		rownumbers : true,
		pager : gridP31Motivo.pagerNameJQuery,
		gridview:true,
		viewrecords : false,
		//caption : gridP31Motivo.title,
		altclass: "ui-priority-secondary",
		altRows: true, //false, para que el grid no muestre cebrado
		hidegrid : false, //false, para ocultar el boton que colapsa el grid.
		sortable : false,
		index: gridP31Motivo.sortIndex,
		sortname: gridP31Motivo.sortIndex,
		sortorder: gridP31Motivo.sortOrder,
		emptyrecords : gridP31Motivo.emptyRecords,
		gridComplete : function() {
		},
		loadError : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }
	});
}

/*Clase de constantes para el GRID de motivos*/
function GridP31Motivo (locale){
	// Atributos
	this.name = "gridP31Motivo"; 
	this.nameJQuery = "#gridP31Motivo"; 
	this.i18nJSON = './misumi/resources/p31PopUpMotivosTengoMuchoPoco/p31PopUpMotivosTengoMuchoPoco_' + locale + '.json';
	this.colNames = null;
	
	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP31Motivo"; 
	this.pagerNameJQuery = "#pagerP31Motivo";
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
			return "descripcion";
		}
	} 
	 
	this.getSortOrder = function getSortOrder () {
		
		if ($(this.nameJQuery).getGridParam('sortorder')!=null){
			return $(this.nameJQuery).getGridParam('sortorder');
		}else{
			return "asc";
		}
	} 	
}

function p31MotivoFormatter(cellvalue, options, rowObject) {
	
	var textoMotivoLinea = cellvalue;
    var textoFormateado = "<span class=\"p31MotivoTengoMuchoPocoTexto\">" + textoMotivoLinea + "</span>";

    return textoFormateado;
};

function reloadDataP31(gridP31Motivo) {

	var objJson = $.toJSON(recordMotivoTengoMuchoPocoP31.prepareToJsonObject());

	$("#p31_AreaPopupMotivosTengoMuchoPocoTabla .loading").css("display", "block");

	$.ajax({
		type : 'POST',
		url : './motivosTengoMuchoPoco/loadMotivosTengoMuchoPoco.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		

			if (data.datos != null && data.datos.length > 0){
				$('#p31_resultadosNoHayRegistros').hide();
				
				$('#p31_AreaPopupMotivosTengoMuchoPocoTabla').show();
				$('#gridP31Motivo').show();
				if (data.datos != null && data.datos.length > 4){
					$('#pagerP31Motivo').show();
				}else{
					$('#pagerP31Motivo').hide();
				}
					
				$(gridP31Motivo.nameJQuery).jqGrid('clearGridData').jqGrid('setGridParam', { data: data.datos }).trigger('reloadGrid', [{ page: 1}]);

			}else{
				//Mostrar leyenda de no hay registros en la tabla
				$('#p31_resultadosNoHayRegistros').show();
				$('#p31_AreaPopupMotivosTengoMuchoPocoTabla').hide();
				
				$('#p31_AreaPopupMotivosTengoMuchoPocoTabla').hide();
				$('#gridP31Motivo').hide();
				$('#pagerP31Motivo').hide();
			} 

			$("#p31_AreaPopupMotivosTengoMuchoPocoTabla .loading").css("display", "none");
			$("#p31_popupMotivosTengoMuchoPoco").dialog("option", "position", "center");
		},
		error : function (xhr, status, error){
			$("#p31_AreaPopupMotivosTengoMuchoPocoTabla .loading").css("display", "none");
			handleError(xhr, status, error, locale);				
        }			
	});		
}

function reloadPopupP31(codCentro, tipo, codArticulo, stockBajo, stockAlto, descripcion, stock) {
	

	
	recordMotivoTengoMuchoPocoP31 = new MotivoTengoMuchoPoco (codCentro, tipo, codArticulo, stockBajo, stockAlto, descripcion, stock);
	//Se elimina el mensaje de no hay registros si lo hubiese y los motivos
	$('#p31_resultadosNoHayRegistros').hide();

	$('#p31_AreaPopupMotivosTengoMuchoPocoTabla').hide();
	$('#gridP31Motivo').hide();
	$('#pagerP31Motivo').hide();
	if (tipo != null && tipo=="M"){
		$( "#p31_popupMotivosTengoMuchoPoco" ).dialog( "option", "title", tituloVentanaPopupMotivosTengoMucho);
	}else if (tipo != null && tipo=="P"){
		$( "#p31_popupMotivosTengoMuchoPoco" ).dialog( "option", "title", tituloVentanaPopupMotivosTengoPoco);
	}else{
		$( "#p31_popupMotivosTengoMuchoPoco" ).dialog( "option", "title", tituloVentanaPopupMotivosTengoMuchoPoco);
	}
    $( "#p31_popupMotivosTengoMuchoPoco" ).dialog( "open" );
	reloadDataP31(gridP31Motivo);
	
}