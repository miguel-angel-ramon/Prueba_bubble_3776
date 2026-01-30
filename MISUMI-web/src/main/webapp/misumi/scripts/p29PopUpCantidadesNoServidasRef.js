var p29BotonAceptar = null;
var gridP29=null;
	
$(document).ready(function(){

	loadP29(locale);

});

function initializeScreenP29PopupCantidadesNoServidasRef(){
	
	$( "#p29_popupCantidadesNoServidasRef" ).dialog({
        autoOpen: false,
        height: 470,
        width: 650,
        modal: true,
        resizable: false,
        buttons:[{
            text: p29BotonAceptar,
            click: function() {
                      $(this).dialog('close');
                   }
        }],
		open: function() {
			$('#p27_AreaPopupsCantNoServ .ui-dialog-titlebar-close').on('mousedown', function(){
				$("#p29_popupCantidadesNoServidasRef").dialog('close');
			});
		}
    });
	
	$(window).bind('resize', function() {
	    $("#p29_popupCantidadesNoServidasRef").dialog("option", "position", "center");
	});
	
}

function p29SetHeadersTitles(data){
	
   	var colModel = $(gridP29.nameJQuery).jqGrid("getGridParam", "colModel");
    $.each(colModel, function(i) {
    	if (colModel[i].name!="rn"){
	    	$("#jqgh_gridp29CantNoServRef_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
    	}
     });
}

function reset_p29(){
	$("#p29_oferta").hide();
	$("#p29_campana").show();
	$("#p29_lbl_campanaVal").text("");
	$("#p29_lbl_campanaVal").attr("title", "");
    $("#p29_fld_campanaVal").val("");
	$("#p29_lbl_ofertaVal").text("");
	$("#p29_lbl_ofertaVal").attr("title", "");
    $("#p29_fld_ofertaVal").val("");
    $("#p29_fld_tipoOCVal").val("");
    $("#p29_fld_fechaInicioVal").val("");
  	$("#p29_fld_fechaFinVal").val("");
  	$("#p29_lbl_referenciaVal").text("");
  	$("#p29_fld_referenciaVal").val("");
  	$("#p29_lbl_descripcionVal").text("");
  	$("#p29_lbl_descripcionVal").attr("title", "");
  	$("#p29_fld_descripcionVal").val("");
  	
  	$(gridP29.nameJQuery).jqGrid('clearGridData');
  	$(gridP29.nameJQuery).jqGrid('setGridParam', { rowNum: 10 });
  	$("#p29_popupCantidadesNoServidasRef .ui-pg-selbox").val(10);
}

function loadP29(locale){
	gridP29 = new GridP29CantNoServRef(locale);
	$(gridP29.nameJQuery).jqGrid('setGridWidth', $("#p29_AreaCabecera").width(), true);
	var jqxhr = $.getJSON(gridP29.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				gridP29.colNames = data.ColNames;
				gridP29.title = data.GridTitle;
				gridP29.emptyRecords= data.emptyRecords;
				gridP29.formatoFecha = data.formatoFecha;
				p29BotonAceptar = data.p29BotonAceptar;
				load_gridP29CantNoServRefMock(gridP29);
				initializeScreenP29PopupCantidadesNoServidasRef();
				p29SetHeadersTitles(data);
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
        });
}

function load_gridP29CantNoServRefMock(gridP29) {

		$(gridP29.nameJQuery).jqGrid({
			ajaxGridOptions : {
				contentType : 'application/json; charset=utf-8',
				cache : false
			},
			datatype : 'local',
			contentType : 'application/json',
			mtype : 'POST',
			colNames : gridP29.colNames,
			colModel : gridP29.cm,
			rowNum : 10,
			rowList : [ 10, 30, 50 ],
			height : "100%",
			autowidth : true,
			width : "auto",
			rownumbers : gridP29.rownumbers,
			pager : gridP29.pagerNameJQuery,
			viewrecords : true,
			caption : gridP29.title,
			altclass: "ui-priority-secondary",
			altRows: true, //false, para que el grid no muestre cebrado
			hidegrid : false, //false, para ocultar el boton que colapsa el grid.
			sortable : true,
			index: gridP29.sortIndex,
			sortname: gridP29.sortIndex,
			sortorder: gridP29.sortOrder,
			emptyrecords : gridP29.emptyRecords,
			gridComplete : function() {
			gridP29.headerHeight("p29_gridHeader");				
					
			},
			loadComplete : function(data) {
				gridP29.actualPage = data.page;
				gridP29.localData = data;
				gridP29.sortIndex = gridP29.sortIndex;
				gridP29.sortOrder = gridP29.sortOrder;	
				$("#p29_AreaReferencias .loading").css("display", "none");	
			},
			resizeStop: function () {
				gridP29.saveColumnState.call($(this),gridP29.myColumnsState);
				$(gridP29.nameJQuery).jqGrid('setGridWidth', $("#p29_AreaCabecera").width(), false);
            },
			onPaging : function(postdata) {			
				alreadySorted = false;
				gridP29.sortIndex = gridP29.sortIndex;
				gridP29.sortOrder = gridP29.sortOrder;
				gridP29.saveColumnState.call($(this), this.p.remapColumns);
				reloadData_gridP29CantNoServRef(gridP29);
				return 'stop';
			},
			onSelectRow: function(id){

			},
			onSortCol : function (index, columnIndex, sortOrder){
				gridP29.sortIndex = index;
				gridP29.sortOrder = sortOrder;
				gridP29.saveColumnState.call($(this), this.p.remapColumns);
				reloadData_gridP29CantNoServRef(gridP29);
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

function reloadDataP29(seguimientoCampanasNoServRef){
	reset_p29();
	$( "#p29_popupCantidadesNoServidasRef" ).dialog( "open" );
	reloadData_gridP29CantNoServRef(gridP29,seguimientoCampanasNoServRef);
	
	//Datos para maquetación
	if (seguimientoCampanasNoServRef.tipoOC === "C") {
		// Es una campaña
		$("#p29_oferta").hide();
		$("#p29_campana").show();
		$("#p29_lbl_campanaVal").text( p29FormateoTextoCampanaOferta(seguimientoCampanasNoServRef.campana, seguimientoCampanasNoServRef.fechaInicioDDMMYYYY, seguimientoCampanasNoServRef.fechaFinDDMMYYYY) );
		$("#p29_lbl_campanaVal").attr("title", eliminarBlancos(p29FormateoTextoCampanaOfertaTitle(seguimientoCampanasNoServRef.campana, seguimientoCampanasNoServRef.fechaInicioDDMMYYYY, seguimientoCampanasNoServRef.fechaFinDDMMYYYY)));
	    $("#p29_fld_campanaVal").val(seguimientoCampanasNoServRef.campana);
	} else {
		// Es una oferta
		$("#p29_campana").hide();
		$("#p29_oferta").show();
		$("#p29_lbl_ofertaVal").text( p29FormateoTextoCampanaOferta(seguimientoCampanasNoServRef.oferta, seguimientoCampanasNoServRef.fechaInicioDDMMYYYY, seguimientoCampanasNoServRef.fechaFinDDMMYYYY) );
		$("#p29_lbl_ofertaVal").attr("title", eliminarBlancos(p29FormateoTextoCampanaOfertaTitle(seguimientoCampanasNoServRef.oferta, seguimientoCampanasNoServRef.fechaInicioDDMMYYYY, seguimientoCampanasNoServRef.fechaFinDDMMYYYY)));
	    $("#p29_fld_ofertaVal").val(seguimientoCampanasNoServRef.oferta);
	}
    $("#p29_fld_tipoOCVal").val(seguimientoCampanasNoServRef.tipoOC);
    $("#p29_fld_fechaInicioVal").val(seguimientoCampanasNoServRef.fechaInicioDDMMYYYY);
  	$("#p29_fld_fechaFinVal").val(seguimientoCampanasNoServRef.fechaFinDDMMYYYY);
  	$("#p29_lbl_referenciaVal").text(seguimientoCampanasNoServRef.codArt);
  	$("#p29_fld_referenciaVal").val(seguimientoCampanasNoServRef.codArt);
  	$("#p29_lbl_descripcionVal").text(seguimientoCampanasNoServRef.descCodArt);
  	$("#p29_lbl_descripcionVal").attr("title", eliminarBlancos(seguimientoCampanasNoServRef.descCodArt));
  	$("#p29_fld_descripcionVal").val(seguimientoCampanasNoServRef.descCodArt);
}

function reloadData_gridP29CantNoServRef(gridP29,seguimientoCampanasNoServRef) {
	
	if (gridP29.firstLoad) {
		$(gridP29.nameJQuery).jqGrid('setGridWidth', $("#p29_AreaCabecera").width(), true);
        gridP29.firstLoad = false;
        if (gridP29.isColState) {
            $(this).jqGrid("remapColumns", gridP29.myColumnsState.permutation, true);
        }
    } else {
    	gridP29.myColumnsState = gridP29.restoreColumnState(gridP29.cm);
    	gridP29.isColState = typeof (gridP29.myColumnsState) !== 'undefined' && gridP29.myColumnsState !== null;
    }
	
	var seguimientoCampanas = null;
	if (seguimientoCampanasNoServRef != null){
		seguimientoCampanas = new SeguimientoCampanas(seguimientoCampanasNoServRef.codCentro, seguimientoCampanasNoServRef.campana, seguimientoCampanasNoServRef.oferta, seguimientoCampanasNoServRef.tipoOC, seguimientoCampanasNoServRef.fechaInicioDDMMYYYY, seguimientoCampanasNoServRef.fechaInicioPantalla, seguimientoCampanasNoServRef.fechaFinDDMMYYYY, seguimientoCampanasNoServRef.fechaFinPantalla,
				seguimientoCampanasNoServRef.codArea, seguimientoCampanasNoServRef.descArea, seguimientoCampanasNoServRef.codSeccion, seguimientoCampanasNoServRef.descSeccion,
				seguimientoCampanasNoServRef.codCategoria, seguimientoCampanasNoServRef.descCategoria, seguimientoCampanasNoServRef.codSubcategoria, seguimientoCampanasNoServRef.descSubcategoria, seguimientoCampanasNoServRef.codSegmento, seguimientoCampanasNoServRef.descSegmento, seguimientoCampanasNoServRef.codArt, seguimientoCampanasNoServRef.descCodArt);
	}else{
		seguimientoCampanas = new SeguimientoCampanas($("#centerId").val(), $("#p29_fld_campanaVal").val(), $("#p29_fld_ofertaVal").val(), $("#p29_fld_tipoOCVal").val(), $("#p29_fld_fechaInicioVal").val(), "", $("#p29_fld_fechaFinVal").val(), "",
				"", "", "", "", "", "", "", "", "", "", $("#p29_fld_referenciaVal").val(), $("#p29_fld_descripcionVal").val());
	}
	
	var objJson = $.toJSON(seguimientoCampanas.prepareToJsonObject());
	$("#p29_AreaReferencias .loading").css("display", "block");
	$.ajax({
		type : 'POST',			
		url : './segCampanas/cantidadesNoServidasRef/loadDataGrid.do?page='+gridP29.getActualPage()+'&max='+gridP29.getRowNumPerPage()+'&index='+gridP29.getSortIndex()+'&sortorder='+gridP29.getSortOrder(),
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		
			$(gridP29.nameJQuery)[0].addJSONData(data);
			gridP29.actualPage = data.page;
			gridP29.localData = data;
			$('#p29_popupCantidadesNoServidasRef').animate({scrollTop: '0px'}, 0);
			$("#p29_AreaReferencias .loading").css("display", "none");	
		},
		error : function (xhr, status, error){
			$('#p29_popupCantidadesNoServidasRef').animate({scrollTop: '0px'}, 0);
			$("#p29_AreaReferencias .loading").css("display", "none");	
			handleError(xhr, status, error, locale);				
        }			
	});		
}

/*Clase de constantes para el GRID de cantidades no servidas para una referencia*/
function GridP29CantNoServRef (locale){
	// Atributos
	this.name = "gridp29CantNoServRef"; 
	this.nameJQuery = "#gridp29CantNoServRef"; 
	this.i18nJSON = './misumi/resources/p29PopUpCantidadesNoServidasRef/p29PopUpCantidadesNoServidasRef_' + locale + '.json';
	this.colNames = null; //Está en el json
	this.cm = [
		   		{
					"name"      : "fechaPrevisEntDDMMYYYY",
					"index"     : "fechaPrevisEnt",
					"width"     : 72,
					"formatter" : p29fechaPrevisEntFormatter,
				    "align"     : "left"
				},{
					"name"      : "uniNoServ",
					"index"     : "uniNoServ", 
					"width"     : 48,	
					"formatter" : p29camposUnDecFormatNumber,
					"align"     : "left"						
				},{
					"name"      : "motivoDes",
					"index"     : "motivoDes",
					"width"     : 200,
					"align"     : "left",
					"classes"   : "p29_columnaResaltada"
				}
			]; 
	this.locale = locale;
	this.sortIndex = "fechaPrevisEnt";
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerGridp29";  
	this.pagerNameJQuery = "#pagerGridp29";
	this.title = null; //Está en el json
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null; //Está en el json
	this.rownumbers = true;
	this.myColumnStateName = 'gridP29CantNoServRef.colState';
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
        var colModel = jQuery(gridP29.nameJQuery).jqGrid('getGridParam', 'colModel'); 
        var i;
        var l = colModel.length;
        var colItem; 
        var cmName;
        var postData = jQuery(gridP29.nameJQuery).jqGrid('getGridParam', 'postData');
        var columnsState = {
                search: jQuery(gridP29.nameJQuery).jqGrid('getGridParam', 'search'),
                page: jQuery(gridP29.nameJQuery).jqGrid('getGridParam', 'page'),
                sortname: jQuery(gridP29.nameJQuery).jqGrid('getGridParam', 'sortname'),
                sortorder: jQuery(gridP29.nameJQuery).jqGrid('getGridParam', 'sortorder'),
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

function p29camposUnDecFormatNumber(cellValue, opts, rData) {
	
	return $.formatNumber(cellValue,{format:"#,##0.#",locale:"es"});
}

function p29fechaPrevisEntFormatter(cellvalue, options, rowObject) {
    var fechaPrevisEntDDMMYYYY = cellvalue;
	var diaFechaSt = fechaPrevisEntDDMMYYYY.substring(0,2);
	var mesFechaSt = fechaPrevisEntDDMMYYYY.substring(2,4);
	var anyoFechaSt = fechaPrevisEntDDMMYYYY.substring(4);
 
	var diaFecha = parseInt(diaFechaSt,10);
	var mesFecha = parseInt(mesFechaSt,10);
	var anyoFecha = parseInt(anyoFechaSt,10);
	var fechaCompleta = anyoFecha + "-" + mesFecha + "-" + diaFecha
	fechaPrevisEntFormateada = $.datepicker.formatDate("D dd/mm/yy", devuelveDate(fechaCompleta),{
		dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
		dayNames: $.datepicker.regional[ "es" ].dayNames,
		monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
		monthNames: $.datepicker.regional[ "es" ].monthNames
		});

    return fechaPrevisEntFormateada;
}

function p29FormateoDate(fechaDDMMYYYY) {
	var fechaFormateada = "";
	if (null != fechaDDMMYYYY && "" != fechaDDMMYYYY){
		var diaFecha = parseInt(fechaDDMMYYYY.substring(0,2),10);
		var mesFecha = parseInt(fechaDDMMYYYY.substring(2,4),10);
		var anyoFecha = parseInt(fechaDDMMYYYY.substring(4),10);
		var fechaCompleta = anyoFecha + "-" + mesFecha + "-" + diaFecha
		
		fechaFormateada = $.datepicker.formatDate("D dd/mm/yy", devuelveDate(fechaCompleta),{
			dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
			dayNames: $.datepicker.regional[ "es" ].dayNames,
			monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
			monthNames: $.datepicker.regional[ "es" ].monthNames
			});
	}
	return(fechaFormateada);
}

function p29FormateoTextoCampanaOferta(txtCampanaOferta, fechaInicioDDMMYYYY, fechaFinDDMMYYYY) {
	var txtCampanaOfertaFormateada = "";
	if (null != txtCampanaOferta && "" != txtCampanaOferta){
		if (txtCampanaOferta.length > 40) {
			var txtCampanaOfertaFormateada = txtCampanaOferta.substring(0,40) + "...";
		}
		else {
			var txtCampanaOfertaFormateada = txtCampanaOferta;
		}
		txtCampanaOfertaFormateada = txtCampanaOfertaFormateada + " (" + p29FormateoDate(fechaInicioDDMMYYYY) + " - " + p29FormateoDate(fechaFinDDMMYYYY) + ")";
	}
	return txtCampanaOfertaFormateada;
}

function p29FormateoTextoCampanaOfertaTitle(txtCampanaOferta, fechaInicioDDMMYYYY, fechaFinDDMMYYYY) {
	var txtCampanaOfertaFormateada = "";
	if (null != txtCampanaOferta && "" != txtCampanaOferta){
		txtCampanaOfertaFormateada = txtCampanaOferta + " (" + p29FormateoDate(fechaInicioDDMMYYYY) + " - " + p29FormateoDate(fechaFinDDMMYYYY) + ")";
	}
	return txtCampanaOfertaFormateada;
}
