var p24BotonAceptar = null;
var gridP24=null;

$(document).ready(function(){

	loadP24(locale);

});

function initializeScreenP24PopupAlbaranElectronico(){
	$( "#p24_popupAlbaranElectronico" ).dialog({
        autoOpen: false,
        height: 650,
        width: 1000,
        modal: true,
        resizable: false,
        buttons:[{
            text: p24BotonAceptar,
            click: function() {
                      $(this).dialog('close');
            }
        }],
		open: function() {
			$('.ui-dialog-titlebar-close').on('mousedown', function(){
				$("#p24_popupAlbaranElectronico").dialog('close');
			});
		}
    });
	
	$(window).bind('resize', function() {
	    $("#p24_popupAlbaranElectronico").dialog("option", "position", "center");
	});
}

function reset_p24(){
	$("#p24_lbl_fechaPedidoVal").text("");
    $("#p24_fld_fechaPedidoVal").val("");
  	$("#p24_lbl_areaVal").text("");
  	$("#p24_fld_areaVal").val("");
  	$("#p24_lbl_mapaVal").text("");
  	$("#p24_fld_mapaVal").val("");
  	$("#p24_lbl_seccionVal").text("");
  	$("#p24_fld_seccionVal").val("");
  	$("#p24_lbl_categoriaVal").text("");
  	$("#p24_fld_categoriaVal").val("")
  	$(gridP24.nameJQuery).jqGrid('clearGridData');
  	//$(gridP24.nameJQuery).jqGrid('setGridWidth', $("#p24_AreaReferencias").width(), true);
}

function loadP24(locale){
	gridP24 = new GridP24AlbElec(locale);
	$(gridP24.nameJQuery).jqGrid('setGridWidth', $("#p24_AreaCabecera").width(), true);
	var jqxhr = $.getJSON(gridP24.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				gridP24.colNames = data.ColNames;
				gridP24.title = data.GridTitle;
				gridP24.emptyRecords= data.emptyRecords;
				p24BotonAceptar = data.p24BotonAceptar;
				load_gridP24AlbElecMock(gridP24);
				initializeScreenP24PopupAlbaranElectronico();
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
        });
}

function load_gridP24AlbElecMock(grid) {

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
			rowNum : 10,
			rowList : [ 10, 30, 50 ],
			height : "100%",
			autowidth : true,
			width : "auto",
			rownumbers : grid.rownumbers,
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
			//grid.headerHeight("p24_gridHeader");				
					
			},
			loadComplete : function(data) {
				grid.actualPage = data.page;
				grid.localData = data;
				grid.sortIndex = grid.sortIndex;
				grid.sortOrder = grid.sortOrder;	
				$(grid.nameJQuery).jqGrid('setGridWidth', $("#p24_AreaReferencias").width(), true);
				$("#p24_AreaReferencias .loading").css("display", "none");
			},
			resizeStop: function () {
				grid.saveColumnState.call($(this),grid.myColumnsState);
				$(grid.nameJQuery).jqGrid('setGridWidth', $("#p24_AreaCabecera").width(), false);
            },
			onPaging : function(postdata) {			
				alreadySorted = false;
				grid.sortIndex = grid.sortIndex;
				grid.sortOrder = grid.sortOrder;
				grid.saveColumnState.call($(this), this.p.remapColumns);
				reloadData_gridP24AlbElec(grid);
				return 'stop';
			},
			onSelectRow: function(id){

			},
			onSortCol : function (index, columnIndex, sortOrder){
				grid.sortIndex = index;
				grid.sortOrder = sortOrder;
				grid.saveColumnState.call($(this), this.p.remapColumns);
				//$(grid.nameJQuery).jqGrid('setGridWidth', $("#p24_AreaReferencias").width(), true);
				reloadData_gridP24AlbElec(grid);
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

function imageFormatEst(cellValue, opts, rowObject) {
	var imagen = "";
	if (cellValue == "C")	    
		imagen = "<img src='./misumi/images/dialog-accept-24.png'/>";
	else if (cellValue == "I")	    
		imagen = "<img src='./misumi/images/dialog-accept-yellow-24.png'/>";
	else if (cellValue == "N")	    
		imagen = "<img src='./misumi/images/dialog-cancel-24.png'/>";
	else 
		imagen = "";
		
	return imagen;
}

function reloadDataP24(seguimientoMiPedido){
	reset_p24();
	$( "#p24_popupAlbaranElectronico" ).dialog( "open" );
	reloadData_gridP24AlbElec(gridP24,seguimientoMiPedido);
	$("#p24_lbl_fechaPedidoVal").text(seguimientoMiPedido.fechaPedidoPantalla);
	$("#p24_fld_fechaPedidoVal").val(seguimientoMiPedido.fechaPedidoDDMMYYYY);
	$("#p24_fld_areaVal").val(seguimientoMiPedido.codArea);
	$("#p24_fld_seccionVal").val(seguimientoMiPedido.codSeccion);
	$("#p24_fld_mapaVal").val(seguimientoMiPedido.mapa);
	$("#p24_fld_categoriaVal").val(seguimientoMiPedido.codCategoria)
	if (esCentroVegalsa()){
		$('#p24_lbl_fechaReposicion').show();
		$('#p24_lbl_fechaPedido').hide();
	}else{
		$('#p24_lbl_fechaReposicion').hide();
		$('#p24_lbl_fechaPedido').show();
	}
	if (seguimientoMiPedido.codArea!=null && seguimientoMiPedido.codArea!=""){
		$("#p24_lbl_areaVal").text(seguimientoMiPedido.codArea+"-"+seguimientoMiPedido.descArea);
	}else{
		$("#p24_lbl_areaVal").text("");
	}
	if (seguimientoMiPedido.codSeccion!=null && seguimientoMiPedido.codSeccion!=""){
		$("#p24_lbl_seccionVal").text(seguimientoMiPedido.codSeccion+"-"+seguimientoMiPedido.descSeccion);
	}else{
		$("#p24_lbl_seccionVal").text("");
	}
	if (seguimientoMiPedido.codCategoria!=null && seguimientoMiPedido.codCategoria!=""){
		$("#p24_lbl_categoriaVal").text(seguimientoMiPedido.codCategoria+"-"+seguimientoMiPedido.descCategoria);
	}else{
		$("#p24_lbl_categoriaVal").text("");
	}
	if (seguimientoMiPedido.mapa!=null && seguimientoMiPedido.mapa!="" && seguimientoMiPedido.mapa!="0"){
		$("#p24_lbl_mapaVal").text(seguimientoMiPedido.mapa+"-"+seguimientoMiPedido.descrMapa);
	}else if (seguimientoMiPedido.mapa=="0"){
		$("#p24_lbl_mapaVal").text("SIN MAPA");
	}else{
		$("#p24_lbl_mapaVal").text("");
	}
	var radioValue = $("input[name='p20_rad_tipoFiltro']:checked").val();
	if (esCentroVegalsa() && radioValue==3){
		$('.p24_etiqValorCampo').width("40%");
		$('#p24_divArea').width("27%");
		$('#p24_divMapa').width("30%");
		$('#p24_divMapa').show();
	}else{
		$('.p24_etiqValorCampo').width("48%");
		$('#p24_divMapa').hide();
	}
}

function reloadData_gridP24AlbElec(grid, seguimientoMiPedidoParam) {

	if (grid.firstLoad) {
		$(grid.nameJQuery).jqGrid('setGridWidth', $("#p24_AreaCabecera").width(), true);
        grid.firstLoad = false;
        if (grid.isColState) {
            $(this).jqGrid("remapColumns", grid.myColumnsState.permutation, true);
        }
    } else {
    	grid.myColumnsState = grid.restoreColumnState(grid.cm);
    	grid.isColState = typeof (grid.myColumnsState) !== 'undefined' && grid.myColumnsState !== null;
    }
	
	var seguimientoMiPedido = null;
	if (seguimientoMiPedidoParam != null){
		seguimientoMiPedido = new SeguimientoMiPedido($("#centerId").val() , seguimientoMiPedidoParam.fechaPedidoDDMMYYYY, '', seguimientoMiPedidoParam.codArea, '', seguimientoMiPedidoParam.codSeccion, '', seguimientoMiPedidoParam.codCategoria, '', '', seguimientoMiPedidoParam.codArt,seguimientoMiPedidoParam.mapa, seguimientoMiPedidoParam.descrMapa);
	}else{
		seguimientoMiPedido = new SeguimientoMiPedido($("#centerId").val() , $("#p24_fld_fechaPedidoVal").val(), '', $("#p24_fld_areaVal").val(), '', $("#p24_fld_seccionVal").val(), '', $("#p24_fld_categoriaVal").val(), '','', $("#p20_fld_referencia_tmp").val(),$("#p24_fld_mapaVal").val(),'');
	}
	var objJson = $.toJSON(seguimientoMiPedido.prepareToJsonObject());
	$("#p24_AreaReferencias .loading").css("display", "block");
	$.ajax({
		type : 'POST',			
		url : './misPedidos/albaranesElectronicos/loadDataGrid.do?page='+grid.getActualPage()+'&max='+grid.getRowNumPerPage()+'&index='+grid.getSortIndex()+'&sortorder='+grid.getSortOrder(),
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		
			$(grid.nameJQuery)[0].addJSONData(data);
			grid.actualPage = data.page;
			grid.localData = data;
			//$(grid.nameJQuery).jqGrid('setGridWidth', $("#p24_AreaReferencias").width(), true);
			$("#p24_AreaReferencias .loading").css("display", "none");
		},
		error : function (xhr, status, error){
			$("#p24_AreaReferencias .loading").css("display", "none");
			handleError(xhr, status, error, locale);				
        }			
	});		
}

/*Clase de constantes para el GRID de albaranes electr�nicos*/
function GridP24AlbElec (locale){
	
	// Atributos
	this.name = "gridP24AlbElec"; 
	this.nameJQuery = "#gridP24AlbElec"; 
	this.i18nJSON = './misumi/resources/p24PopUpAlbaranesElectronicos/p24PopUpAlbaranesElectronicos_' + locale + '.json';
	this.colNames = null; //Est� en el json
	this.cm = [
		   		{
					"name"  : "numExpedicion",
					"index" : "numExpedicion",
					"width" : 75,	
				    "align" : "left"
				},{
					"name"  : "numAlbaran",
					"index" : "numAlbaran", 
					"width" : 75,	
					"align" : "left"						
		   		},{
					"name"  : "fechaAlbaran",
					"index" : "fechaAlbaran", 
					"width" : 75,	
					"formatter":"date",
					"formatoptions":{
				    	"srcformat": "Y-m-d",
				    	"newformat": "d/m/Y"
				    },	
					"align" : "center"						
		   		},{
					"name"  : "estado",
					"index" : "estado", 
					"width" : 100,
					"formatter": imageFormatEst,	
					"align" : "center"	
				},{
					"name"  : "fechaConfirmadoFormat",
					"index" : "fechaConfirmadoFormat",
					"width" : 160,
					
				    "align" : "center"
				}
			];
	this.locale = locale;
	this.sortIndex = "estado,num_expedicion";
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerGridP24";  
	this.pagerNameJQuery = "#pagerGridP24"; 
	this.title = null; //Est� en el json
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null; //Est� en el json
	this.rownumbers = true;
	 this.myColumnStateName = 'gridP24AlbElec.colState';
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
		for (i = 0; i < this.colNames.length; i++){
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
        var colModel = jQuery(grid.nameJQuery).jqGrid('getGridParam', 'colModel'); 
        var i;
        var l = colModel.length;
        var colItem; 
        var cmName;
        var postData = jQuery(grid.nameJQuery).jqGrid('getGridParam', 'postData');
        var columnsState = {
                search: jQuery(grid.nameJQuery).jqGrid('getGridParam', 'search'),
                page: jQuery(grid.nameJQuery).jqGrid('getGridParam', 'page'),
                sortname: jQuery(grid.nameJQuery).jqGrid('getGridParam', 'sortname'),
                sortorder: jQuery(grid.nameJQuery).jqGrid('getGridParam', 'sortorder'),
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
