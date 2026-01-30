var p66BotonAceptar = null;
var gridP66=null;

$(document).ready(function(){

	loadP66(locale);


});

function initializeScreenP66AyudaTextil(){
	$( "#p66_AreaAyudaTextil" ).dialog({
        autoOpen: false,
        height: 385,
        width: 760,
        modal: true,
        resizable: false,
        buttons:[{
            text: p66BotonAceptar,
            click: function() {
                      $(this).dialog('close');
                      
                      //Devolver el foco al campo correspondiente
                      $("#"+$("#p52_campo_foco_vueltaPopUpTextil").val()).focus();
          			  $("#"+$("#p52_campo_foco_vueltaPopUpTextil").val()).select();
          			  $("#p52_campo_foco_vueltaPopUpTextil").val('');
            }
        }],
    
		open: function() {
			$('.ui-dialog-titlebar-close').on('mousedown', function(){
				$("#p66_AreaAyudaTextil").dialog('close');
				
				//Devolver el foco al campo correspondiente
				$("#"+$("#p52_campo_foco_vueltaPopUpTextil").val()).focus();
    			$("#"+$("#p52_campo_foco_vueltaPopUpTextil").val()).select();
    			$("#p52_campo_foco_vueltaPopUpTextil").val('');
			});
			
			$( "#p66_AreaAyudaTextil" ).on('keydown', function(e){
			
				var key = e.which || e.keyCode;
				
				if(key == 13) {

					$("#p66_AreaAyudaTextil").dialog('close');
					
					//Devolver el foco al campo correspondiente
					$("#"+$("#p52_campo_foco_vueltaPopUpTextil").val()).focus();
	    			$("#"+$("#p52_campo_foco_vueltaPopUpTextil").val()).select();
	    			$("#p52_campo_foco_vueltaPopUpTextil").val('');
			 	}
			 	
			});
		}
    });
	
	$(window).bind('resize', function() {
	    $("#p66_AreaAyudaTextil").dialog("option", "position", "center");
	});
}





function loadP66(locale){

	gridP66 = new GridP66AyudaTextil(locale);
	$(gridP66.nameJQuery).jqGrid('setGridWidth', "auto", true);
	var jqxhr = $.getJSON(gridP66.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				gridP66.colNames = data.ColNames;
				gridP66.title = data.GridTitle;
				gridP66.emptyRecords= data.emptyRecords;
				p66BotonAceptar = data.p66BotonAceptar;
				load_gridP66AyudaTextilMock(gridP66);
				initializeScreenP66AyudaTextil();

			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
        });
	

}

function load_gridP66AyudaTextilMock(grid) {

		$(grid.nameJQuery).jqGrid({
			ajaxGridOptions : {
				contentType : 'application/json; charset=utf-8',
				cache : false
			},
			datatype : 'local',
			contentType : 'application/json',
			mtype : 'POST',
			colNames : grid.colNames,
			colModel : grid.cm,
			rowNum : 999,
			//rowList : [ 10, 30, 50 ],
			height : "100%",
			autowidth : true,
			width : "100%",
			rownumbers : grid.rownumbers,
			shrinkToFit:true,
			pager : grid.pagerNameJQuery,
			viewrecords : true,
			caption : grid.title,
			altclass: "ui-priority-secondary",
			altRows: true, //false, para que el grid no muestre cebrado
			hidegrid : false, //false, para ocultar el boton que colapsa el grid.
			sortable : true,
			index: grid.sortIndex,
			sortname: grid.sortIndex,
			sortorder: grid.sortOrder,
			emptyrecords : grid.emptyRecords,
			gridComplete : function() {
			grid.headerHeight("p66_gridHeader");				
					
			},
			loadComplete : function(data) {
				grid.actualPage = data.page;
				grid.localData = data;
				grid.sortIndex = grid.sortIndex;
				grid.sortOrder = grid.sortOrder;	
				
			},
			resizeStop: function () {
				grid.saveColumnState.call($(this),grid.myColumnsState);
            },
			onPaging : function(postdata) {			
				alreadySorted = false;
				grid.sortIndex = grid.sortIndex;
				grid.sortOrder = grid.sortOrder;
				grid.saveColumnState.call($(this), this.p.remapColumns);
				reloadData_gridP66AyudaTextil(grid);
				return 'stop';
			},
			onSelectRow: function(id){

			},
			onSortCol : function (index, columnIndex, sortOrder){
				grid.sortIndex = index;
				grid.sortOrder = sortOrder;
				grid.saveColumnState.call($(this), this.p.remapColumns);
				reloadData_gridP66AyudaTextil(grid);
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


function reloadDataP66(referencia){
	
	reset_p66();
	$( "#p66_AreaAyudaTextil" ).dialog( "open" );
	reloadData_gridP66AyudaTextil(gridP66,referencia);
	
	
}

function reset_p66(){

  	$(gridP66.nameJQuery).jqGrid('clearGridData');
  	
  	
}


function reloadData_gridP66AyudaTextil(grid, referencia) {
	
	var vReferenciasCentro=new ReferenciasCentro(referencia,$("#centerId").val(),'InforDatos');
	var objJson = $.toJSON(vReferenciasCentro);	
	 $.ajax({
		type : 'POST',
		url : './misPedidosTextil/loadDataGrid.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		
			
			$(grid.nameJQuery)[0].addJSONData(data);
			grid.actualPage = data.page;
			grid.localData = data;
			
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
        }			
	});		
}



/*Clase de constantes para el GRID de Textil*/
function GridP66AyudaTextil (locale){
	
	// Atributos
	this.name = "gridP66AyudaTextil"; 
	this.nameJQuery = "#gridP66AyudaTextil"; 
	this.i18nJSON = './misumi/resources/p66AyudaTextilPopUp/p66AyudaTextilPopUp_' + locale + '.json';
	this.colNames = null; //Esta en el json
	this.cm = [
		   		{
		   			"name"  : "modeloProveedor",
					"index" : "modeloProveedor", 
					"width" : 15,	
					"align" : "left"	
				},{
					"name"  : "codArticulo",
					"index" : "codArticulo",
					"width" : 15,	
				    "align" : "left"
				},{
		   			"name"  : "descrColor",
					"index" : "descrColor", 
					"width" : 30,	
					"align" : "left"	
				},{
		   			"name"  : "descrTalla",
					"index" : "descrTalla", 
					"width" : 15,	
					"align" : "left"	
				},{
					"name"  : "stock",
					"index" : "stock", 
					"width" : 15,
					"formatter": camposFormatNumber,
					"align" : "center"
				}
			];
	this.locale = locale;
	this.sortIndex = "referencia";
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerGridP66"; 
	this.pagerNameJQuery = "#pagerGridP66";
	this.title = null; //Esta en el json
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null; //Est� en el json
	this.rownumbers = true;
	this.myColumnStateName = 'gridP66AyudaTextil.colState';
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

function camposFormatNumber(cellValue, opts, rData) {
	
	//Actualizamos el formateo de los campos a tres decimales
	return $.formatNumber(cellValue,{format:"0.00",locale:"es"});
}



