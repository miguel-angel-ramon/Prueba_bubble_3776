var p18BotonAceptar = null;
var gridP18=null;
var gridP18MMC=null;
var mensajeMapaHoyN = null;
var mensajeMapaHoySPedirS = null;
var tituloVentanaPopupPedir = null;

$(document).ready(function(){

	loadP18(locale);

});

function initializeScreenPopupPedir(){
	
	$( "#p18_caprabo_popupPedir" ).dialog({
        autoOpen: false,
        height: 'auto',
        width: 'auto',
        modal: true,
        title: "",
        resizable: false,
        dialogClass: "popupResaltado",
        buttons:[{
            text: p18BotonAceptar,
            click: function() {
                      $(this).dialog('close');
                   }
        }],
		open: function() {
			$('.ui-dialog-titlebar-close').on('mousedown', function(){
				$("#p18_caprabo_popupPedir").dialog('close');
			});
		}
    });
	
	$(window).bind('resize', function() {
	    $("#p18_caprabo_popupPedir").dialog("option", "position", "center");
	});
	
	recalcularEnlacePopupPedir();
}

function recalcularEnlacePopupPedir(){

	$( "#p15_caprabo_pedidoAutomaticoFieldsetNoActivoConEnlace" ).unbind("click");
	$( "#p15_caprabo_pedidoAutomaticoFieldsetNoActivoConEnlace" )
    .click(function() {
    	
    	$( "#p18_caprabo_popupPedir" ).dialog( "option", "title", tituloVentanaPopupPedir);
    	$( "#p18_caprabo_popupPedir" ).dialog( "open" );
    	reloadDataP18();
    });

}

function loadP18(locale){
	//Ambos grid tienen la misma estructura y comparten el mismo fichero de configuración
	 gridP18 = new GridP18(locale);
	
	var jqxhr = $.getJSON(gridP18.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				tituloVentanaPopupPedir = data.tituloVentanaPopupPedir;
				p18BotonAceptar = data.p18BotonAceptar;
				mensajeMapaHoyN = data.mensajeMapaHoyN;
				mensajeMapaHoySPedirS = data.mensajeMapaHoySPedirS;
				
				gridP18.colNames = data.p18ColNames;
				gridP18.emptyRecords= data.emptyRecords;

				initializeScreenPopupPedir();
				
				loadP18Mock(gridP18);
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
         });
}

function loadP18Mock(gridP18) {
	//para hacer cabeceras
	//jQuery("#ghwcs").jqGrid('setGroupHeaders', { useColSpanStyle: true, groupHeaders:[ {startColumnName: 'amount', numberOfColumns: 3, titleText: '<em>Price</em>'}, {startColumnName: 'closed', numberOfColumns: 2, titleText: 'Shiping'} ] });
		$(gridP18.nameJQuery).jqGrid({
			ajaxGridOptions : {
				contentType : 'application/json; charset=utf-8',
				cache : false
			},
			
			datatype : 'local',
			contentType : 'application/json',
			mtype : 'POST',
			colNames : gridP18.colNames,
			colModel :[
			   		{
						"name"  : "textoMotivo",
						"index" : "textoMotivo", 
						"width" : 350,
						"formatter": MotivoFormatter,
						"sortable" : false
					}
				],  
			rowNum : 5,
			height : "100%",
			autowidth : true,
			width : "auto",
			pginput: false,
			rownumbers : true,
			pager : gridP18.pagerNameJQuery,
			viewrecords : false,
			//caption : gridP18.title,
			altclass: "ui-priority-secondary",
			altRows: true, //false, para que el grid no muestre cebrado
			hidegrid : false, //false, para ocultar el boton que colapsa el grid.
			sortable : false,
			index: gridP18.sortIndex,
			sortname: gridP18.sortIndex,
			sortorder: gridP18.sortOrder,
			emptyrecords : gridP18.emptyRecords,
			gridComplete : function() {

				
			},
			loadComplete : function(data) {
				gridP18.actualPage = data.page;
				gridP18.localData = data;
				gridP18.sortIndex = null;
				gridP18.sortOrder = null;
				$("#p18_caprabo_AreaPopupPedirGeneral .loading").css("display", "none");	
				
				//reloadDataP18();
			},
			onPaging : function(postdata) {			
				alreadySorted = false;
				gridP18.sortIndex = null;
				gridP18.sortOrder = null;
				reloadDataP18();
				return 'stop';
			},
			onSelectRow: function(id){

			},
			onSortCol : function (index, columnIndex, sortOrder){
				gridP18.sortIndex = index;
				gridP18.sortOrder = sortOrder;
				reloadDataP18();
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
				handleError(xhr, status, error, locale);
	        }
		});
}



/*Clase de constantes para el GRID de motivos*/
function GridP18 (locale){
	// Atributos
	this.name = "gridP18"; 
	this.nameJQuery = "#gridP18"; 
	this.i18nJSON = './misumi/resources/p18ReferenciasCentroPopupPedirCaprabo/p18referenciasCentroPopupPedirCaprabo_' + locale + '.json';
	this.colNames = null;
	
	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP18"; 
	this.pagerNameJQuery = "#pagerP18";
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
			return "grupo1";
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


function MotivoFormatter(cellvalue, options, rowObject) {
    var cellData = rowObject.textoMotivo; 
    var cellDataWebservice = rowObject.motivoWebservice;
    var cellDataPedir = rowObject.pedir;
    var cellDataMapaHoy = rowObject.mapaHoy;
    var textoFormateado = "";
    var formato=rowObject.tipoMensaje;
    if (formato!=null && formato!=""){
    	if (formato!="SF"){
    		textoFormateado= "<span class=\"p18Caprabo_MotivoMapaHoySPedirS\">" + cellData.texto1 + "</span>";
    	}else{
    		textoFormateado = cellData.texto1 + " " + cellData.texto2;
    	}
    }else{
    	textoFormateado = "<span class=\"p18Caprabo_MotivoTexto1\">" + cellData.texto1 + "</span>";
		textoFormateado += " <span class=\"p18Caprabo_MotivoTexto2\">" + cellData.texto2 + "</span>";
    }

    return textoFormateado;
};

function reloadDataP18(grid) {

	var recordMotivo = new Motivo ($("#p13_caprabo_fld_mapaHoy").val(), $("#p13_caprabo_fld_refActiva").val(), $("#p13_caprabo_fld_referencia").val(), $("#centerId").val(), 'InforDatos');
	
	var objJson = $.toJSON(recordMotivo.prepareToJsonObject());

	$("#p18_caprabo_AreaPopupPedirGeneral .loading").css("display", "block");
	if ( $("#p13_caprabo_fld_refActiva").val()!=true){
		
		$.ajax({
			type : 'POST',
			url : './referenciasCentroCaprabo/loadMotivosNoActiva.do?page='+gridP18.getActualPage()+'&max='+gridP18.getRowNumPerPage()+'&index='+gridP18.getSortIndex()+'&sortorder='+gridP18.getSortOrder(),
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {		
				$(gridP18.nameJQuery)[0].addJSONData(data);
				gridP18.actualPage = data.page;
				gridP18.localData = data;
				$("#p18_caprabo_AreaPopupPedirGeneral .loading").css("display", "none");	
				if (data.records == 0){
					createAlert(replaceSpecialCharacters(gridP18.emptyRecords), "ERROR");
				} 
			},
			error : function (xhr, status, error){
				$("#p18_caprabo_AreaPopupPedirGeneral .loading").css("display", "none");	
				handleError(xhr, status, error, locale);				
	        }			
		});		
	}
}


