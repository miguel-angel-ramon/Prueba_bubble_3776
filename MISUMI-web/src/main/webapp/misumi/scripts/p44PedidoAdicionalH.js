var gridP44PedidoAdicionalH=null;

$(document).ready(function(){

	initializeScreenPedidoAdicionalH();

});

function initializeScreenPedidoAdicionalH(){
	
}

function resetResultadosPedidoAdicionalH(){
	
	if (gridP44PedidoAdicionalH != null) {
		gridP44PedidoAdicionalH.clearGrid();
	}
	
}

function reloadHistorico() {

	$("#p40_pestanaHistoricoCargada").val("S");
	
	loadP44PedidoAdicionalH(locale);
}

function loadP44PedidoAdicionalH(locale){
	gridP44PedidoAdicionalH = new GridP44PedidoAdicionalH(locale);
	
	var jqxhr = $.getJSON(gridP44PedidoAdicionalH.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				gridP44PedidoAdicionalH.colNames = data.p44PedidoAdicionalHColNames;
				gridP44PedidoAdicionalH.title = data.p44PedidoAdicionalHGridTitle;
				gridP44PedidoAdicionalH.emptyRecords= data.emptyRecords;
				loadP44PedidoAdicionalHMock(gridP44PedidoAdicionalH);
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
         });
}

function loadP44PedidoAdicionalHMock(gridP44PedidoAdicionalH) {
	$(gridP44PedidoAdicionalH.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		url : './misumi/resources/p44PedidoAdicionalH/p44mockPedidoAdicionalH.json',
		datatype : 'json',
		contentType : 'application/json',
		mtype : 'POST',
		colNames : gridP44PedidoAdicionalH.colNames,
		colModel :[
		   		{
					"name"  : "referencia",
					"index" : "referencia",
					"width" : 90
                },{
					"name"  : "denominacion",
					"index" : "denominacion",
					"width" : 140						
		   		},{
					"name"  : "fechaInicio",
					"index" : "fechaInicio",
					"width" : 90	
		   		},{
					"name"  : "fechaFin",
					"index" : "fechaFin",
					"width" : 90
		   		},{
					"name"  : "tipoPedido",
					"index" : "tipoPedido", 
					"width" : 100
		   		},{
					"name"  : "capMax",
					"index" : "capMax", 
					"width" : 90
		   		},{
					"name"  : "capMin",
					"index" : "capMin", 
					"width" : 90
		   		},{
					"name"  : "cantidad1",
					"index" : "cantidad1", 
					"width" : 80
		   		},{
					"name"  : "cantidad2",
					"index" : "cantidad2", 
					"width" : 80
		   		},{
					"name"  : "cantidad3",
					"index" : "cantidad3", 
					"width" : 80
		   		},{
					"name"  : "uc",
					"index" : "uc", 
					"width" : 50
		   		},{
					"name"  : "cjas",
					"index" : "cjas", 
					"width" : 50
				},{
					"name"  : "excl",
					"index" : "excl",
					"width" : 50
				},{
					"name"  : "ofer",
					"index" : "ofer",
					"width" : 50
				}
			], 
		rowNum : 10,
		rowList : [ 10, 20, 30 ],
		height : "100%",
		autowidth : true,
		width : "auto",
		rownumbers : true,
		pager : gridP44PedidoAdicionalH.pagerNameJQuery,
		viewrecords : true,
		caption : gridP44PedidoAdicionalH.title,
		altclass: "ui-priority-secondary",
		altRows: true, //false, para que el grid no muestre cebrado
		hidegrid : false, //false, para ocultar el boton que colapsa el grid.
		sortable : true,
		index: gridP44PedidoAdicionalH.sortIndex,
		sortname: gridP44PedidoAdicionalH.sortIndex,
		sortorder: gridP44PedidoAdicionalH.sortOrder,
		emptyrecords : gridP44PedidoAdicionalH.emptyRecords,
		gridComplete : function() {
			
			
		},
		loadComplete : function(data) {
			gridP44PedidoAdicionalH.actualPage = data.page;
			gridP44PedidoAdicionalH.localData = data;
			gridP44PedidoAdicionalH.sortIndex = null;
			gridP44PedidoAdicionalH.sortOrder = null;
			reloadDataP44PedidoAdicionalH(gridP44PedidoAdicionalH);
		},
		onPaging : function(postdata) {			
			alreadySorted = false;
			gridP44PedidoAdicionalH.sortIndex = null;
			gridP44PedidoAdicionalH.sortOrder = null;
			reloadDataP44PedidoAdicionalH(gridP44PedidoAdicionalH);
			return 'stop';
		},
		onSelectRow: function(id){

		},
		onSortCol : function (index, columnIndex, sortOrder){
			gridP44PedidoAdicionalH.sortIndex = index;
			gridP44PedidoAdicionalH.sortOrder = sortOrder;
			reloadDataP44PedidoAdicionalH(gridP44PedidoAdicionalH);
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

function reloadDataP44PedidoAdicionalH(grid) {

	var ids = jQuery(grid.nameJQuery).jqGrid('getDataIDs');
	//Poner una fila como si fuera central
	//Estaría marcada en rojo
	$("#gridP44Historico").find("#"+ids[3]).find("td").addClass("p44_columnaResaltada");
}

/*Clase de constantes para el GRID de Pedido adicional oferta*/
function GridP44PedidoAdicionalH (locale){
		// Atributos
		this.name = "gridP44Historico"; 
		this.nameJQuery = "#gridP44Historico"; 
		this.i18nJSON = './misumi/resources/p44PedidoAdicionalH/p44pedidoAdicionalH_' + locale + '.json';
		this.colNames = null;
		
		this.sortIndex = "referencia";
		this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
		this.pagerName = "pagerP44Historico"; 
		this.pagerNameJQuery = "#pagerP44Historico";
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
}
