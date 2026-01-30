var p63BotonAceptar = null;
var gridP63=null;

$(document).ready(function(){

	loadP63(locale);

});

function initializeScreenP63PopupRefCompraVenta(){
	$( "#p63_popupRefCompraVenta" ).dialog({
        autoOpen: false,
        height: 400,
        width: 760,
        modal: true,
        resizable: false,
        buttons:[{
            text: p63BotonAceptar,
            click: function() {
                      $(this).dialog('close');
            }
        }],
		open: function() {
			$('.ui-dialog-titlebar-close').on('mousedown', function(){
				$("#p63_popupRefCompraVenta").dialog('close');
			});
		}
    });
	
	$(window).bind('resize', function() {
	    $("#p63_popupRefCompraVenta").dialog("option", "position", "center");
	});
}

function p63SetHeadersTitles(data){
	
   	var colModel = $(gridP63.nameJQuery).jqGrid("getGridParam", "colModel");
    $.each(colModel, function(i) {
    	if (colModel[i].name!="rn"){
	    	$("#jqgh_gridP63RefCompraVenta_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
    	}
     });
}

function reset_p63(){
  	$(gridP63.nameJQuery).jqGrid('clearGridData');
  	$(gridP63.nameJQuery).jqGrid('setGridParam', { rowNum: 10 });
  	$("#p63_popupRefCompraVenta .ui-pg-selbox").val(10);
}

function loadP63(locale){
	gridP63 = new GridP63RefCompraVenta(locale);
	$(gridP63.nameJQuery).jqGrid('setGridWidth', $("#p63_AreaReferencias").width(), true);
	var jqxhr = $.getJSON(gridP63.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				gridP63.colNames = data.ColNames;
				gridP63.title = data.GridTitle;
				gridP63.emptyRecords= data.emptyRecords;
				p63BotonAceptar = data.p63BotonAceptar;
				load_gridP63RefCompraVentaMock(gridP63);
				initializeScreenP63PopupRefCompraVenta();
				p63SetHeadersTitles(data);
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
        });
}

function load_gridP63RefCompraVentaMock(gridP63) {

		$(gridP63.nameJQuery).jqGrid({
			ajaxGridOptions : {
				contentType : 'application/json; charset=utf-8',
				cache : false
			},
			datatype : 'local',
			contentType : 'application/json',
			mtype : 'POST',
			colNames : gridP63.colNames,
			colModel : gridP63.cm,
			rowNum : 10,
			rowList : [ 10, 30, 50 ],
			height : "100%",
			autowidth : true,
			width : "auto",
			rownumbers : gridP63.rownumbers,
			pager : gridP63.pagerNameJQuery,
			viewrecords : true,
			caption : gridP63.title,
			altclass: "ui-priority-secondary",
			altRows: true, //false, para que el grid no muestre cebrado
			hidegrid : false, //false, para ocultar el boton que colapsa el gridP63.
			sortable : true,
			index: gridP63.sortIndex,
			sortname: gridP63.sortIndex,
			sortorder: gridP63.sortOrder,
			emptyrecords : gridP63.emptyRecords,
			gridComplete : function() {
				gridP63.headerHeight("p63_gridHeader");				
			},
			loadComplete : function(data) {
				gridP63.actualPage = data.page;
				gridP63.localData = data;
				gridP63.sortIndex = gridP63.sortIndex;
				gridP63.sortOrder = gridP63.sortOrder;	
				$("#p63_AreaReferencias .loading").css("display", "none");
			},
			resizeStop: function () {
				gridP63.saveColumnState.call($(this),gridP63.myColumnsState);
				$(gridP63.nameJQuery).jqGrid('setGridWidth', $("#p63_AreaReferencias").width(), false);
            },
			onPaging : function(postdata) {			
				alreadySorted = false;
				gridP63.sortIndex = gridP63.sortIndex;
				gridP63.sortOrder = gridP63.sortOrder;
				gridP63.saveColumnState.call($(this), this.p.remapColumns);
				reloadData_gridP63RefCompraVenta(gridP63);
				return 'stop';
			},
			onSelectRow: function(id){

			},
			onSortCol : function (index, columnIndex, sortOrder){
				gridP63.sortIndex = index;
				gridP63.sortOrder = sortOrder;
				gridP63.saveColumnState.call($(this), this.p.remapColumns);
				reloadData_gridP63RefCompraVenta(gridP63);
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
				handleError(xhr, status, error);
	        }
		});

}

function reloadDataP63(referencia){
	reset_p63();
	$("#p63_fld_referencia").val(referencia);
	$( "#p63_popupRefCompraVenta" ).dialog( "open" );
	reloadData_gridP63RefCompraVenta(gridP63);
}

function reloadData_gridP63RefCompraVenta(grid) {
	
	if (grid.firstLoad) {
		$(grid.nameJQuery).jqGrid('setGridWidth', $("#p63_AreaReferencias").width(), true);
		grid.firstLoad = false;
		if (grid.isColState) {
			$(this).jqGrid("remapColumns", grid.myColumnsState.permutation, true);
		}
	} else {
		grid.myColumnsState = grid.restoreColumnState(grid.cm);
		grid.isColState = typeof (grid.myColumnsState) !== 'undefined' && grid.myColumnsState !== null;
	}
	
	var vdatosdiarioart=new VDatosDiarioArt($("#p63_fld_referencia").val(), null, null, null, null, null, null, null, null);
	var objJson = $.toJSON(vdatosdiarioart);	

	$("#p63_AreaReferencias .loading").css("display", "block");
	$.ajax({
		type : 'POST',			
		url : './refCompraVenta/loadDataGrid.do?page='+grid.getActualPage()+'&max='+grid.getRowNumPerPage()+'&index='+grid.getSortIndex()+'&sortorder='+grid.getSortOrder(),
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		
			$(grid.nameJQuery)[0].addJSONData(data);
			grid.actualPage = data.page;
			grid.localData = data;
			$('#p63_popupRefCompraVenta').animate({scrollTop: '0px'}, 0);
			$("#p63_AreaReferencias .loading").css("display", "none");	
		},
		error : function (xhr, status, error){
			$('#p63_popupRefCompraVenta').animate({scrollTop: '0px'}, 0);
			$("#p63_AreaReferencias .loading").css("display", "none");	
			handleError(xhr, status, error, locale);				
        }			
	});		
}

/*Clase de constantes para el GRID de referencias de venta*/
function GridP63RefCompraVenta (locale){
	
	// Atributos
	this.name = "gridP63RefCompraVenta"; 
	this.nameJQuery = "#gridP63RefCompraVenta"; 
	this.i18nJSON = './misumi/resources/p63PopUpRefCompraVenta/p63PopUpRefCompraVenta_' + locale + '.json';
	this.colNames = null; //Está en el json
	this.cm = [
		   		{
					"name"  : "codArtGrid",
					"index" : "codArt",
					"width" : 20,	
				    "align" : "left"
				},{
					"name"  : "descripArtGrid",
					"index" : "descripArt", 
					"width" : 100,	
					"align" : "left"						
				}
			];
	this.locale = locale;
	this.sortIndex = "codArt";
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerGridP63";  
	this.pagerNameJQuery = "#pagerGridP63";
	this.title = null; //Está en el json
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null; //Está en el json
	this.rownumbers = true;
	this.myColumnStateName = 'gridP63RefCompraVenta.colState';
	this.myColumnsState = null;
	this.isColState = null;
	this.firstLoad = true;
	
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
		for (i = 0; i < colModel.length; i++){
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
         var colModel = jQuery(gridP63.nameJQuery).jqGrid('getGridParam', 'colModel'); 
         var i;
         var l = colModel.length;
         var colItem; 
         var cmName;
         var postData = jQuery(gridP63.nameJQuery).jqGrid('getGridParam', 'postData');
         var columnsState = {
                 search: jQuery(gridP63.nameJQuery).jqGrid('getGridParam', 'search'),
                 page: jQuery(gridP63.nameJQuery).jqGrid('getGridParam', 'page'),
                 sortname: jQuery(gridP63.nameJQuery).jqGrid('getGridParam', 'sortname'),
                 sortorder: jQuery(gridP63.nameJQuery).jqGrid('getGridParam', 'sortorder'),
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