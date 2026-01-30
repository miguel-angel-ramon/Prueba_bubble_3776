var gridP59Stock=null;
var recordText=null;
var errorStockActual=null;
var tableFilter=null;
var codReferencia = null;
var mostrarTitle = null;
var title = null;

function defineP59(codArt, showTitle){
	codReferencia = codArt;
	mostrarTitle = showTitle;
	//gridP59Stock.title = codArt;
}

function initializeP59(){
	//Sólo para la Maquetación
	loadP59Stock(locale);
	$( "#p59_AreaStock" ).dialog({
	    autoOpen: false,
	    modal: false,
	    width: 490,
	    resizable: false,
	    dialogClass: "p59_popupResaltado",
	    //Nos posicionamos en la mitad de la pantalla, le sumamos la mitad del marco para situarnos
	    //al final del marco y le restamos el tama�o del popup.
	    //position:[($(document).width()/2)+490-650,0],
	    //Cambio para que aparezca pegado a la izda de la pantalla
	    position:[0,0],
		open: function(e) {
			//$('#gridP59Stock').jqGrid('clearGridData');
			reloadStock();
			$('.ui-dialog-titlebar-close').on('mousedown', function(){
				$("#p59_AreaStock").dialog('close');
			});
			//campo.focus();
			//campo.select();
			//reload();
		}
	});
}


function reloadStock() {
	loadTitleP59();
	reloadDataP59Stock();
}

function loadP59Stock(locale){
	
	this.i18nJSON = './misumi/resources/p59Stock/p59Stock_' + locale + '.json';
	
	gridP59Stock = new GridP59Stock(locale);
	
	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				gridP59Stock.colNames = data.p59StockColNames;
				title = data.p59GridTitle;
				gridP59Stock.emptyRecords = data.emptyRecords;
				emptyRecords = data.emptyRecords;
				gridEmptyRecords= data.emptyRecords;
				recordText = data.recordText;
				errorStockActual = data.errorStockActual;
				tableFilter = data.tableFilter;
				
				loadP59Mock();
				setHeadersTitles(data);
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
           });
}

function loadP59Mock() {
	//para hacer cabeceras
		$(gridP59Stock.nameJQuery).jqGrid({
			ajaxGridOptions : {
				contentType : 'application/json; charset=utf-8',
				cache : false
			},
			datatype : 'local',
			contentType : 'application/json',
			mtype : 'POST',
//			headertitles: true ,
			colNames : gridP59Stock.colNames,
			colModel : gridP59Stock.cm,
			rowNum : 12,
			rowList : [ 12, 24, 36 ],
			height : "100%",
			autowidth : true,
			width : "auto",
			rownumbers : true,
			autoencode:true,
			shrinkToFit:true,
			pager : gridP59Stock.pagerNameJQuery,
			viewrecords : true,
			caption : gridP59Stock.title,
			altclass: "ui-priority-secondary",
			altRows: true, //false, para que el grid no muestre cebrado
			hidegrid : false, //false, para ocultar el boton que colapsa el grid.
			sortable : true,
			index: gridP59Stock.sortIndex,
			sortname: gridP59Stock.sortIndex,
			sortorder: gridP59Stock.sortOrder,
			emptyrecords : gridP59Stock.emptyRecords,
			recordtext : recordText ,
			gridComplete : function() {
				//grid.headerHeight("gridP72Header");  
			},
			loadComplete : function(data) {
				gridP59Stock.actualPage = data.page;
				gridP59Stock.localData = data;
				gridP59Stock.sortIndex = null;
				gridP59Stock.sortOrder = null;
				//$("#AreaResultados .loading").css("display", "none");	
				//if (grid.firstLoad)
					jQuery(gridP59Stock.nameJQuery).jqGrid('setGridWidth', $("#p59_AreaStockDatos").width(), true);
			},
			onPaging : function(postdata) {			
				alreadySorted = false;
				gridP59Stock.sortIndex = null;
				gridP59Stock.sortOrder = null;
				//grid.saveColumnState.call($(this), this.p.remapColumns);
				reloadDataP59Stock();
				return 'stop';
			},
			onSelectRow: function(id){

			},
			resizeStop: function () {
				//grid.modificado = true;
				//grid.saveColumnState.call($(this),grid.myColumnsState);
				jQuery(gridP59Stock.nameJQuery).jqGrid('setGridWidth', $("#p59_AreaStockDatos").width(), false);
			},
			onSortCol : function (index, columnIndex, sortOrder){
				gridP59Stock.sortIndex = index;
				gridP59Stock.sortOrder = sortOrder;
				//grid.saveColumnState.call($(this), this.p.remapColumns);
				reloadDataP59Stock();
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
		jQuery(gridP59Stock.nameJQuery).jqGrid('navGrid',gridP59Stock.pagerNameJQuery,{
			add:false,edit:false,del:false,search:false,refresh:false}
	); 
	jQuery(gridP59Stock.nameJQuery).jqGrid('navButtonAdd',gridP59Stock.pagerNameJQuery,{ 
			caption: tableFilter, title: "Filtrado columnas", 
		onClickButton : function (){ 
			jQuery(gridP59Stock.nameJQuery).jqGrid('columnChooser',
					{
                		done: function(perm) {
                			if (perm) {
                				this.jqGrid("remapColumns", perm, true);
                			}
                				//grid.saveColumnState.call(this, perm);
                				jQuery(gridP59Stock.nameJQuery).jqGrid('setGridWidth',  $("#p59_AreaStockDatos").width(), true);
                				//grid.saveColumnState.call(this, perm);;
                				defineP59(codReferencia, mostrarTitle);
                				$("#p59_AreaStock").dialog('open');
                				
                		},
                		dialog_opts: {
                            beforeClose: function () {
                				defineP59(codReferencia, mostrarTitle);
                				$("#p59_AreaStock").dialog('open');
                            }
                		}
                		
					}		
			); } });

	$(gridP59Stock.pagerNameJQuery+"_left").css("width", '');
		//jQuery(grid.nameJQuery).jqGrid('hideCol', ["descRegion"]);
		//jQuery(grid.nameJQuery).jqGrid('hideCol', ["descZona"]);
}



function reloadDataP59Stock() {

    //$("#p59_AreaStockDatos.loading").css("display", "block");
    $("#p59_noData").hide();
	$.ajax({
		type : 'GET',			
		url : './nuevoPedidoAdicionalReferencia/loadDataStock.do?codArt='+ codReferencia+'&page='+gridP59Stock.getActualPage()+'&max='+gridP59Stock.getRowNumPerPage()+'&index='+gridP59Stock.getSortIndex()+'&sortorder='+gridP59Stock.getSortOrder(),
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {	
			$(gridP59Stock.nameJQuery)[0].addJSONData(data);
			gridP59Stock.actualPage = data.page;
			gridP59Stock.localData = data;
			//$("#p59_AreaStockDatos .loading").css("display", "none");
			//$("#p59_AreaStockDatos").css("height", $("#p59_AreaStockDatos .ui-jqgrid").css("height"));
			var width = $("#p59_AreaStockDatos").width();
			var height = $("#p59_AreaStockDatos").height();
			$( "#p59_AreaStock" ).dialog( "option", "width", (width + 15));
			$( "#p59_AreaStock" ).dialog( "option", "height", (height + 50) );

		},
		error : function (xhr, status, error){
			//$("#p59_AreaStockDatos .loading").css("display", "none");	
			handleError(xhr, status, error, locale);				
		}			
	});	}

/*Clase de constantes para el GRID de ayuda*/
function GridP59Stock (locale){
	// Atributos
	this.name = "gridP59Stock";
	this.nameJQuery = "#gridP59Stock";
	this.colNames = null;
	this.cm = [	{
		"name" : "descRegion",
		"index":"descRegion",
		"width" : 25,
		"formatter": function(value, options, rData){ return rData['codRegion'] + '-' + value  ;  },
		"sortable":true,
	    "hidden" : true
	},{
		"name" : "descZona",
		"index":"descZona",
		"width" : 25,
		"formatter": function(value, options, rData){ return rData['codZona'] + '-' + value  ;  },
		"sortable":true,
	    "hidden" : true 
	},{
		"name" : "descCentro",
		"index":"descCentro", 
		"formatter": function(value, options, rData){ return rData['codCentro'] + '-' + value  ;  },
		"sortable":true,
		"width" : 60
	},{
		"name" : "stock",
		"index":"stock",
		"formatter":formatMessageStock,
		"sortable":false,
		"width" : 20
	},{
		"name" : "ventaMedia",
		"index":"ventaMedia", 
		"formatter":"number",
		"sortable":true,
		"width" : 20
	}			
	]; 
	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP59Stock"; 
	this.pagerNameJQuery = "#pagerP59Stock";
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
			return "";
		}
	} 
	 
	this.getSortOrder = function getSortOrder () {
		
		if ($(this.nameJQuery).getGridParam('sortorder')!=null){
			return $(this.nameJQuery).getGridParam('sortorder');
		}else{
			return "";
		}
	} 	
	
	this.headerHeight = function headerHeight(cssClass) {
		var colModel = $(this.nameJQuery).jqGrid('getGridParam','colModel');
		for (i = 0; i < this.colNames.length; i++){
			$(this.nameJQuery).setLabel(colModel[i].name, '', cssClass);
		}
	}
	
	this.clearGrid = function clearGrid() {
		$(this.nameJQuery).jqGrid('GridUnload');
	}
}

function formatMessageStock(cellValue, opts, rData) {
	
	//Controlamos los posibles errores que me lleguen para pintar el icono correspondiente.
	if (rData['descError'] == 'stock'){
		//Pintamos el icono de que se ha producido un error
		return "<img src='./misumi/images/dialog-cancel-14.png' style='padding: 2px 0px 0px 2px' title='"+errorStockActual+"' />"; //Error
	}
	else{
		return $.formatNumber(rData['stock'],{format:"0.00",locale:"es"});
	}
}

function setHeadersTitles(data){
	
   	var colModel = $(gridP59Stock.nameJQuery).jqGrid("getGridParam", "colModel");
    $.each(colModel, function(i) {
    	if (colModel[i].name!="rn"){
	    	$("#jqgh_gridP59Stock_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
    	}
     });
}

function loadTitleP59(){
	var dialogTitle = title;
	if (mostrarTitle){
		
		$.ajax({
			type : 'GET',			
			url : './nuevoPedidoAdicionalReferencia/loadStockTitle.do?codArt='+ codReferencia,
			//contentType : "application/json; charset=utf-8",
			//dataType : "json",
			success : function(data) {	
				 if (null != data){
					 dialogTitle += " " + data;
					 $('#p59_AreaStock').dialog('option', 'title', dialogTitle);
				 }

			},
			error : function (xhr, status, error){
				//$("#p59_AreaStockDatos .loading").css("display", "none");	
				handleError(xhr, status, error, locale);				
			}			
		});	
	} else {
		 dialogTitle += " " + codReferencia;
		 $('#p59_AreaStock').dialog('option', 'title', dialogTitle);
	}
}
