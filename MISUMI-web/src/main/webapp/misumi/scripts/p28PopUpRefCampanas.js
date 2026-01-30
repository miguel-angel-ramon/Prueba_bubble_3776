var p28BotonAceptar = null;
var p28textoColumn1 = null;
var gridP28=null;

$(document).ready(function(){

	loadP28(locale);

});

function initializeScreenP28PopupRefCampanas(){
	$( "#p28_popupRefCampanas" ).dialog({
        autoOpen: false,
        height: 620,
        width: 930,
        modal: true,
        resizable: false,
        buttons:[{
            text: p28BotonAceptar,
            click: function() {
                      $(this).dialog('close');
            }
        }],
		open: function() {
			$('#p27_AreaPopupsRefCampanas .ui-dialog-titlebar-close').on('mousedown', function(){
				$("#p28_popupRefCampanas").dialog('close');
			});
		}
    });
	
	$(window).bind('resize', function() {
	    $("#p28_popupRefCampanas").dialog("option", "position", "center");
	});
}

function reset_p28(){
	$("#p28_lbl_campanaVal").text("");
	$("#p28_lbl_campanaVal").attr("title", "");
    $("#p28_fld_campanaVal").val("");
	$("#p28_lbl_ofertaVal").text("");
	$("#p28_lbl_ofertaVal").attr("title", "");
    $("#p28_fld_ofertaVal").val("");
    $("#p28_fld_tipoOCVal").val("");
    $("#p28_fld_fechaInicioVal").val("");
  	$("#p28_fld_fechaFinVal").val("");
    $("#p28_lbl_areaVal").text("");
    $("#p28_lbl_areaVal").attr("title", "");
  	$("#p28_fld_areaVal").val("");
  	$("#p28_lbl_seccionVal").text("");
  	$("#p28_lbl_seccionVal").attr("title", "");
  	$("#p28_fld_seccionVal").val("");
  	$("#p28_lbl_categoriaVal").text("");
  	$("#p28_lbl_categoriaVal").attr("title", "");
  	$("#p28_fld_categoriaVal").val("")
  	$("#p28_lbl_subcategoriaVal").text("");
  	$("#p28_lbl_subcategoriaVal").attr("title", "");
  	$("#p28_fld_subcategoriaVal").val("")
  	$("#p28_lbl_segmentoVal").text("");
  	$("#p28_lbl_segmentoVal").attr("title", "");
  	$("#p28_fld_segmentoVal").val("");
  	$("#p28_fld_codArtVal").val("");
  	
  	$(gridP28.nameJQuery).jqGrid('clearGridData');
  	$(gridP28.nameJQuery).jqGrid('setGridParam', { rowNum: 10 });
  	$("#p28_popupRefCampanas .ui-pg-selbox").val(10);
}

function events_p28_btn_exportExcel(){
	$("#p28_btn_exportExcel").click(function () {
		exportExcel28();
	});
}

function loadP28(locale){
	gridP28 = new GridP28Ref(locale);
	$(gridP28.nameJQuery).jqGrid('setGridWidth', $("#p28_AreaCabecera").width(), true);
	var jqxhr = $.getJSON(gridP28.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				gridP28.colNames = data.ColNames;
				gridP28.title = data.GridTitle;
				gridP28.emptyRecords= data.emptyRecords;
				p28BotonAceptar = data.p28BotonAceptar;
				p28textoColumn1 = data.p28textoColumn1;
				load_gridP28Ref(gridP28);
				initializeScreenP28PopupRefCampanas();
				events_p28_btn_exportExcel();
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
        });
}

function load_gridP28Ref(grid) {

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
			grid.headerHeight("p28_gridHeader");				
					
			},
			loadComplete : function(data) {
				grid.actualPage = data.page;
				grid.localData = data;
				grid.sortIndex = grid.sortIndex;
				grid.sortOrder = grid.sortOrder;	
				$("#p28_AreaReferencias .loading").css("display", "none");
			},
			beforeSelectRow: function (rowid, e) {
			    var $myGrid = $(this),
			        i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),
			        cm = $myGrid.jqGrid('getGridParam', 'colModel');
			    
			    	if (cm[i].name === 'noServido') 
			    	{
			    		return true;
			    	} else {
			    		return false;
			    	}

			},
	        onCellSelect: function (rowid, iCol, cellcontent, e){
	        	var seguimientoCampanasRef = new SeguimientoCampanas($("#centerId").val() , $("#p28_fld_campanaVal").val(), $("#p28_fld_ofertaVal").val(), $("#p28_fld_tipoOCVal").val(), $("#p28_fld_fechaInicioVal").val(), "", $("#p28_fld_fechaFinVal").val(), "", $("#p28_fld_areaVal").val(), "", $("#p28_fld_seccionVal").val(), "", $("#p28_fld_categoriaVal").val(), "", $("#p28_fld_subcategoriaVal").val(), "", $("#p28_fld_segmentoVal").val(), "", $("#p28_refereciaVal" + rowid).val(), $("#p28_refereciaDesc" + rowid).val());
	        		reloadDataP29(seguimientoCampanasRef);
	        },
			resizeStop: function () {
				grid.saveColumnState.call($(this),grid.myColumnsState);
				$(grid.nameJQuery).jqGrid('setGridWidth', $("#p28_AreaCabecera").width(), false);
            },
			onPaging : function(postdata) {			
				alreadySorted = false;
				grid.sortIndex = grid.sortIndex;
				grid.sortOrder = grid.sortOrder;
				grid.saveColumnState.call($(this), this.p.remapColumns);
				reloadData_gridP28Ref(grid);
				return 'stop';
			},
			onSelectRow: function(id){

			},
			onSortCol : function (index, columnIndex, sortOrder){
				grid.sortIndex = index;
				grid.sortOrder = sortOrder;
				grid.saveColumnState.call($(this), this.p.remapColumns);
				reloadData_gridP28Ref(grid);
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

function reloadDataP28(seguimientoCampanas){
	reset_p28();
	
	 var fechaInicioDDMMYYYY = seguimientoCampanas.fechaInicioDDMMYYYY;
	 var fechaFechaInicioFormateada = "";

	 if (fechaInicioDDMMYYYY != null && fechaInicioDDMMYYYY != ""){
		 var diaFechaInicioSt = fechaInicioDDMMYYYY.substring(0,2);
		 var mesFechaInicioSt = fechaInicioDDMMYYYY.substring(2,4);
		 var anyoFechaInicioSt = fechaInicioDDMMYYYY.substring(4);
		 var diaFechaInicio = parseInt(diaFechaInicioSt,10);
		 var mesFechaInicio = parseInt(mesFechaInicioSt,10);
		 var anyoFechaInicio = parseInt(anyoFechaInicioSt,10);
		 fechaFechaInicioFormateada = $.datepicker.formatDate(grid.formatoFecha, new Date(anyoFechaInicio, mesFechaInicio - 1, diaFechaInicio));
	 }	 

	 var fechaFinDDMMYYYY = seguimientoCampanas.fechaFinDDMMYYYY;
	 var fechaFechaFinFormateada = "";

	 if (fechaFinDDMMYYYY != null && fechaFinDDMMYYYY != ""){
		 var diaFechaFinSt = fechaFinDDMMYYYY.substring(0,2);
		 var mesFechaFinSt = fechaFinDDMMYYYY.substring(2,4);
		 var anyoFechaFinSt = fechaFinDDMMYYYY.substring(4);
		 var diaFechaFin = parseInt(diaFechaFinSt,10);
		 var mesFechaFin = parseInt(mesFechaFinSt,10);
		 var anyoFechaFin = parseInt(anyoFechaFinSt,10);
		 fechaFechaFinFormateada = $.datepicker.formatDate(grid.formatoFecha, new Date(anyoFechaFin, mesFechaFin - 1, diaFechaFin));
	 }

	 //hideShowColumns($("#p28_fld_areaVal").val());
	 //alert("Area de Campaña: "+$("#p28_fld_areaVal").val());

	$( "#p28_popupRefCampanas" ).dialog( "open" );
	 
	var campana = "";
	var oferta = "";
	if(seguimientoCampanas.campana == 'C'){
		campana = seguimientoCampanas.campana + " (" + fechaFechaInicioFormateada + " - " + fechaFechaFinFormateada + ")";
	}else{
		oferta = seguimientoCampanas.oferta + " (" + fechaFechaInicioFormateada + " - " + fechaFechaFinFormateada + ")";
	}

	if (seguimientoCampanas.tipoOC == "C") {
		// Es una campaña
		$("#p28_oferta").hide();
		$("#p28_campana").show();
		$("#p28_lbl_campanaVal").text( p28FormateoTextoCampanaOferta(seguimientoCampanas.campana, seguimientoCampanas.fechaInicioDDMMYYYY, seguimientoCampanas.fechaFinDDMMYYYY) );
		$("#p28_lbl_campanaVal").attr( "title",eliminarBlancos(p28FormateoTextoCampanaOfertaTitle(seguimientoCampanas.campana, seguimientoCampanas.fechaInicioDDMMYYYY, seguimientoCampanas.fechaFinDDMMYYYY)) );
	    $("#p28_fld_campanaVal").val(seguimientoCampanas.campana);
	    
	    
	} else {
		// Es una oferta
		$("#p28_campana").hide();
		$("#p28_oferta").show();
		$("#p28_lbl_ofertaVal").text( p28FormateoTextoCampanaOferta(seguimientoCampanas.oferta, seguimientoCampanas.fechaInicioDDMMYYYY, seguimientoCampanas.fechaFinDDMMYYYY) );
		$("#p28_lbl_ofertaVal").attr( "title",eliminarBlancos(p28FormateoTextoCampanaOfertaTitle(seguimientoCampanas.campana, seguimientoCampanas.fechaInicioDDMMYYYY, seguimientoCampanas.fechaFinDDMMYYYY)) );
		$("#p28_fld_ofertaVal").val(seguimientoCampanas.oferta);
	}
	$("#p28_fld_tipoOCVal").val(seguimientoCampanas.tipoOC);
	$("#p28_fld_fechaInicioVal").val(seguimientoCampanas.fechaInicioDDMMYYYY);
	$("#p28_fld_fechaFinVal").val(seguimientoCampanas.fechaFinDDMMYYYY);
	$("#p28_fld_areaVal").val(seguimientoCampanas.codArea);
	$("#p28_fld_seccionVal").val(seguimientoCampanas.codSeccion);
	$("#p28_fld_categoriaVal").val(seguimientoCampanas.codCategoria);
	$("#p28_fld_subcategoriaVal").val(seguimientoCampanas.codSubcategoria);
	$("#p28_fld_segmentoVal").val(seguimientoCampanas.codSegmento);
	$("#p28_fld_codArtVal").val(seguimientoCampanas.codArt);

	if (seguimientoCampanas.codArea!=null && seguimientoCampanas.codArea!=""){
		$("#p28_lbl_areaVal").text(seguimientoCampanas.codArea+"-"+seguimientoCampanas.descArea);
		$("#p28_lbl_areaVal").attr("title", eliminarBlancos(seguimientoCampanas.codArea+"-"+seguimientoCampanas.descArea));
	}else{
		$("#p28_lbl_areaVal").text("");
		$("#p28_lbl_areaVal").attr("title", "");
	}
	if (seguimientoCampanas.codSeccion!=null && seguimientoCampanas.codSeccion!=""){
		$("#p28_lbl_seccionVal").text(seguimientoCampanas.codSeccion+"-"+seguimientoCampanas.descSeccion);
		$("#p28_lbl_seccionVal").attr("title", eliminarBlancos(seguimientoCampanas.codSeccion+"-"+seguimientoCampanas.descSeccion));
	}else{
		$("#p28_lbl_seccionVal").text("");
		$("#p28_lbl_seccionVal").attr("title", "");
	}
	if (seguimientoCampanas.codCategoria!=null && seguimientoCampanas.codCategoria!=""){
		$("#p28_lbl_categoriaVal").text(seguimientoCampanas.codCategoria+"-"+seguimientoCampanas.descCategoria);
		$("#p28_lbl_categoriaVal").attr("title", eliminarBlancos(seguimientoCampanas.codCategoria+"-"+seguimientoCampanas.descCategoria));
	}else{
		$("#p28_lbl_categoriaVal").text("");
		$("#p28_lbl_categoriaVal").attr("title", "");
	}
	if (seguimientoCampanas.codSubcategoria!=null && seguimientoCampanas.codSubcategoria!=""){
		$("#p28_lbl_subcategoriaVal").text(seguimientoCampanas.codSubcategoria+"-"+seguimientoCampanas.descSubcategoria);
		$("#p28_lbl_subcategoriaVal").attr("title", eliminarBlancos(seguimientoCampanas.codSubcategoria+"-"+seguimientoCampanas.descSubcategoria));
	}else{
		$("#p28_lbl_subcategoriaVal").text("");
		$("#p28_lbl_subcategoriaVal").attr("title", "");
	}
	if (seguimientoCampanas.codSegmento!=null && seguimientoCampanas.codSegmento!=""){
		$("#p28_lbl_segmentoVal").text(seguimientoCampanas.codSegmento+"-"+seguimientoCampanas.descSegmento);
		$("#p28_lbl_segmentoVal").attr("title", eliminarBlancos(seguimientoCampanas.codSegmento+"-"+seguimientoCampanas.descSegmento));
	}else{
		$("#p28_lbl_segmentoVal").text("");
		$("#p28_lbl_segmentoVal").attr("title", "");
	}
	
	hideShowColumns($("#p28_fld_areaVal").val());

	reloadData_gridP28Ref(gridP28,seguimientoCampanas);

}

function reloadData_gridP28Ref(grid, seguimientoCampanasParam) {

	if (grid.firstLoad) {
	$(grid.nameJQuery).jqGrid('setGridWidth', $("#p28_AreaCabecera").width(), true);
        grid.firstLoad = false;
        if (grid.isColState) {
            $(this).jqGrid("remapColumns", grid.myColumnsState.permutation, true);
        }
    } else {
    	grid.myColumnsState = grid.restoreColumnState(grid.cm);
    	grid.isColState = typeof (grid.myColumnsState) !== 'undefined' && grid.myColumnsState !== null;
    }
	
	var seguimientoCampanas = null;
	if (seguimientoCampanasParam != null){
		var seguimientoCampanas = new SeguimientoCampanas($("#centerId").val(), seguimientoCampanasParam.campana, seguimientoCampanasParam.oferta, seguimientoCampanasParam.tipoOC, seguimientoCampanasParam.fechaInicioDDMMYYYY, "", seguimientoCampanasParam.fechaFinDDMMYYYY, "",
				seguimientoCampanasParam.codArea, "", seguimientoCampanasParam.codSeccion, "",
				seguimientoCampanasParam.codCategoria, "", seguimientoCampanasParam.codSubcategoria, "", seguimientoCampanasParam.codSegmento, "", seguimientoCampanasParam.codArt, "");
	}else{
		var seguimientoCampanas = new SeguimientoCampanas($("#centerId").val(), $("#p28_fld_campanaVal").val(), $("#p28_fld_ofertaVal").val(), $("#p28_fld_tipoOCVal").val(), $("#p28_fld_fechaInicioVal").val(), "", $("#p28_fld_fechaFinVal").val(), "",
				$("#p28_fld_areaVal").val(), "", $("#p28_fld_seccionVal").val(), "",
				$("#p28_fld_categoriaVal").val(), "", $("#p28_fld_subcategoriaVal").val(), "", $("#p28_fld_segmentoVal").val(), "", $("#p28_fld_codArtVal").val(), "");
	}
	
	var objJson = $.toJSON(seguimientoCampanas.prepareToJsonObject());
	$("#p28_AreaReferencias .loading").css("display", "block");
	$.ajax({
		type : 'POST',			
		url : './segCampanas/referencias/loadDataGrid.do?page='+grid.getActualPage()+'&max='+grid.getRowNumPerPage()+'&index='+grid.getSortIndex()+'&sortorder='+grid.getSortOrder(),
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		
			$(grid.nameJQuery)[0].addJSONData(data);
			grid.actualPage = data.page;
			grid.localData = data;
			
			var ids = $(grid.nameJQuery).jqGrid('getDataIDs'), i, l = ids.length;
			
		    for (i = 0; i < l; i++) {
		    	//Servido/pendiente
		    	var progressbar = $("#p28_progressbar_1_" + ids[i]);
		    	var progressLabel = $("#p28_progress-label1_" + ids[i]);
		    	var progressbarValue1 = parseInt($("#p28_progressbar_value_1_1_" + ids[i]).val(),10);
		    	var progressbarValue2 = parseInt($("#p28_progressbar_value_1_2_" + ids[i]).val(),10);
		    	progressbar.progressbar({value: false});
				progressbar.progressbar( "option", {
					value: ((progressbarValue2 != 0)?((progressbarValue1 > progressbarValue2)?100:Math.floor( (progressbarValue1*100)/ progressbarValue2)):(progressbarValue1 > 0?100:0))
				});
				progressLabel.text( $.formatNumber(progressbarValue1,{format:"#,##0",locale:"es"})+"/"+$.formatNumber(progressbarValue2,{format:"#,##0",locale:"es"}));

		    	//No servidos unidades
		    	progressbar = $("#p28_progressbar_2_" + ids[i]);
		    	progressLabel = $("#p28_progress-label2_1_" + ids[i]);
		    	progressbarValue1 = parseInt($("#p28_progressbar_value_2_1_" + ids[i]).val(),10);
		    	progressbarValue2 = parseInt($("#p28_progressbar_value_2_2_" + ids[i]).val(),10);
		    	var progressLabel2 = $("#p28_progress-label2_2_" + ids[i]);
		    	progressbar.progressbar({value: false});
				progressbar.progressbar( "option", {
					value: ((progressbarValue1 > 25)?100:Math.floor( (progressbarValue1* 100 )/25))
				});
				progressLabel.text( $.formatNumber(progressbarValue1,{format:"#,##0.#",locale:"es"}));
				progressLabel2.text( $.formatNumber(progressbarValue2,{format:"#,##0",locale:"es"}));
				
				$("#gridP28Ref").find("#"+ids[i]).find("[aria-describedby='gridP28Ref_noServido']").addClass("p28_columnNoServido");

				//Ventas actuales previsión
		    	progressbar = $("#p28_progressbar_3_" + ids[i]);
		    	progressLabel = $("#p28_progress-label3_" + ids[i]);
		    	progressbarValue1 = parseInt($("#p28_progressbar_value_3_1_" + ids[i]).val(),10);
		    	progressbarValue2 = parseInt($("#p28_progressbar_value_3_2_" + ids[i]).val(),10);
		    	progressbar.progressbar({value: false});
				progressbar.progressbar( "option", {
					value: ((progressbarValue2 != 0)?((progressbarValue1 > progressbarValue2)?100:Math.floor( (progressbarValue1*100)/ progressbarValue2)):(progressbarValue1 > 0?100:0))
				});
				progressLabel.text( $.formatNumber(progressbarValue1,{format:"#,##0",locale:"es"})+"/"+$.formatNumber(progressbarValue2,{format:"#,##0",locale:"es"}));
		    }

			$('#p28_popupRefCampanas').animate({scrollTop: '0px'}, 0);
			$("#p28_AreaReferencias .loading").css("display", "none");
		},
		error : function (xhr, status, error){
			$('#p28_popupRefCampanas').animate({scrollTop: '0px'}, 0);
			$("#p28_AreaReferencias .loading").css("display", "none");
			handleError(xhr, status, error, locale);				
        }			
	});		
}

/*Clase de constantes para el GRID de cantidades pedidas*/
function GridP28Ref (locale){
	
	/*P-56402
	 * BICUGUAL
	 * Añadido el campo "denominacion" oculto al GRID.
	 */
	
	// Atributos
	this.name = "gridP28Ref"; 
	this.nameJQuery = "#gridP28Ref"; 
	this.i18nJSON = './misumi/resources/p28PopUpRefCampanas/p28PopUpRefCampanas_' + locale + '.json';
	this.colNames = null; //Está en el json
	this.cm = [
		   		{
					"name"  : "referencia",
					"index" : "referencia",
					"width" : 300,
					"formatter": p28referenciaFormatter,
				    "align" : "left"
		   		},
		   		{
					"name"  : "denominacion",
					"index" : "denominacion",
					"hidden": true,
		   		},
		   		
		   		{
		   			"name"  : "color",
					"index" : "color", 
					"width" : 150,	
					"align" : "left"	
				},{
		   			"name"  : "talla",
					"index" : "talla", 
					"width" : 150,	
					"align" : "left"	
				},{
		   			"name"  : "modeloProveedor",
					"index" : "modeloProveedor", 
					"width" : 300,	
					"align" : "left"	
				},{
					"name"  : "servidoPendiente",
					"index" : "servidoPendiente", 
					"width" : 250,
					"fixed" : true,
					"formatter": p28servidoPendienteFormatter,
					"align" : "center"
				},{
					"name"  : "noServido",
					"index" : "noServido",
					"width" : 120,
					"fixed" : true,
					"formatter":p28noServidoFormatter,
					"align" : "center"
				},{
					"name"  : "ventasPrevision",
					"index" : "ventasPrevision",
					"width" : 120,
					"fixed" : true,
					"formatter":p28ventasPrevisionFormatter,
					"align" : "center"
				},{
					"name"  : "stock",
					"index" : "stock",
					"width" : 100,
					"fixed" : true,
					"formatter":p28stockFormatter,
					"align" : "center"
				}
			];
	this.locale = locale;
	this.sortIndex = "referencia";
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerGridP28";  
	this.pagerNameJQuery = "#pagerGridP28"; 
	this.title = null; //Está en el json
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null; //Está en el json
	this.rownumbers = true;
	 this.myColumnStateName = 'gridP28Ref.colState';
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

function exportExcel28(){
	
	var colModel = $(gridP28.nameJQuery).jqGrid("getGridParam", "colModel");
    var colNames = $(gridP28.nameJQuery).jqGrid("getGridParam", "colNames");
    var myColumns = new Array();
    var myColumnsNames = new Array();
    var j=0;
    var currentName;
  
    $.each(colModel, function(i) {
    	/*P-56402
    	 * BICUGUAL
    	 * Añadido el campo "denominacion" oculto al GRID gridP28 para enviar la columna el en model
    	 * al exportar a excel. 
    	 */
    	if ((colModel[i].name!="rn" && !colModel[i].hidden) || colModel[i].name=="denominacion"  ){
    		//Transformación del nombre de la columna para eliminar estilo
    		currentName = colNames[i];
    		if (currentName!=null && currentName!="null" && currentName!=undefined && currentName!="undefined"){
	    		//currentName = currentName.replace("<br/>", " ");
	    		currentName = currentName.split("<br/>").join(" "); // Es como hacer replaceAll
	    		if (currentName.indexOf(">")>0 && currentName.lastIndexOf("<")>0){
	    			currentName = currentName.substring(currentName.indexOf(">")+1 , currentName.lastIndexOf("<"))
	    		}
    		}
    		myColumnsNames[j]=currentName;
	    	
	    	myColumns[j]=colModel[i].name;
	    	j++;	
	    	
    	}
     });
    var form = "<form name='csvexportform' action='segCampanas/referencias/exportGrid.do'  accept-charset='ISO-8859-1' method='get'>";
	form = form + "<input type='hidden' name='headers' value='"+myColumnsNames+"'>";		
	form = form + "<input type='hidden' name='model' value='"+myColumns+"'>";

	if ($("#centerId").val()!="null" && $("#centerId").val()!=null ){
		form = form + "<input type='hidden' name='codCentro' value='"+$("#centerId").val()+"'>";
	}	
	if ($("#p28_fld_campanaVal").val()!="null" && $("#p28_fld_campanaVal").val()!=null ){
		form = form + "<input type='hidden' name='campana' value='"+$("#p28_fld_campanaVal").val()+"'>";
		form = form + "<input type='hidden' name='campanaCompleta' value='"+$("#p28_lbl_campanaVal").text()+'**'+$("#p28_fld_fechaInicioVal").val()+'**'+$("#p28_fld_fechaFinVal").val()+"'>";
	}	
	if ($("#p28_fld_ofertaVal").val()!="null" && $("#p28_fld_ofertaVal").val()!=null ){
		form = form + "<input type='hidden' name='oferta' value='"+$("#p28_fld_ofertaVal").val()+"'>";
		form = form + "<input type='hidden' name='ofertaCompleta' value='"+$("#p28_lbl_ofertaVal").text()+'**'+$("#p28_fld_fechaInicioVal").val()+'**'+$("#p28_fld_fechaFinVal").val()+"'>";
	}	
	if ($("#p28_fld_fechaInicioVal").val()!="null" && $("#p28_fld_fechaInicioVal").val()!=null ){
		form = form + "<input type='hidden' name='fechaInicio' value='"+$("#p28_fld_fechaInicioVal").val()+"'>";
	}
	if ($("#p28_fld_fechaFinVal").val()!="null" && $("#p28_fld_fechaFinVal").val()!=null ){
		form = form + "<input type='hidden' name='fechaFin' value='"+$("#p28_fld_fechaFinVal").val()+"'>";
	}
	if ($("#p28_fld_tipoOCVal").val()!="null" && $("#p28_fld_tipoOCVal").val()!=null ){
		form = form + "<input type='hidden' name='tipoOC' value='"+$("#p28_fld_tipoOCVal").val()+"'>";
	}
	if ($("#p28_fld_areaVal").val()!="null" && $("#p28_fld_areaVal").val()!=null ){
		form = form + "<input type='hidden' name='codArea' value='"+$("#p28_fld_areaVal").val()+"'>";
	}
	if ($("#p28_fld_seccionVal").val()!="null" && $("#p28_fld_seccionVal").val()!=null ){
		form = form + "<input type='hidden' name='codSeccion' value='"+$("#p28_fld_seccionVal").val()+"'>";
	}
	if ($("#p28_fld_categoriaVal").val()!="null" && $("#p28_fld_categoriaVal").val()!=null ){
		form = form + "<input type='hidden' name='codCategoria' value='"+$("#p28_fld_categoriaVal").val()+"'>";
	}
	if ($("#p28_fld_subcategoriaVal").val()!="null" && $("#p28_fld_subcategoriaVal").val()!=null ){
		form = form + "<input type='hidden' name='codSubcategoria' value='"+$("#p28_fld_subcategoriaVal").val()+"'>";
	}
	if ($("#p28_fld_segmentoVal").val()!="null" && $("#p28_fld_segmentoVal").val()!=null ){
		form = form + "<input type='hidden' name='codSegmento' value='"+$("#p28_fld_segmentoVal").val()+"'>";
	}
	if ($("#p28_fld_codArtVal").val()!="null" && $("#p28_fld_codArtVal").val()!=null ){
		form = form + "<input type='hidden' name='codArt' value='"+$("#p28_fld_codArtVal").val()+"'>";
	}

	form = form + "</form><script>document.csvexportform.submit();</script>";
	Show_Popup(form);	 
}

function p28referenciaFormatter(cellvalue, options, rowObject) {
    var formattedCell = "";
    
    formattedCell = cellvalue + "-" + rowObject.descripcion;
	formattedCell += "<input id=\"p28_refereciaVal" + options.rowId + "\" type=\"hidden\" value=\"" + cellvalue + "\"></input>";
	formattedCell += "<input id=\"p28_refereciaDesc" + options.rowId + "\" type=\"hidden\" value=\"" + rowObject.descripcion + "\"></input>";

    return formattedCell;
}


function p28servidoPendienteFormatter(cellvalue, options, rowObject) {
    var cellData = cellvalue.split("**");
	
	var formattedCell = "";
	formattedCell += "<div class=\"p28_gridSegCampanasColum1\">";
	formattedCell += "<div class=\"p28_progressbar_1_label\"><span class=\"p28_gridSegCampanasColum1Texto1\">" + p28textoColumn1 + $.formatNumber( cellData[0],{format:"#,##0",locale:"es"}) + "</span></div>";	
	formattedCell += "<div id=\"p28_progressbar_1_" + options.rowId + "\" class=\"p28_progressbar_1_img\"><div id=\"p28_progress-label1_" + options.rowId + "\" class=\"p28_progress-label1\"></div></div>";
	formattedCell += "<input id=\"p28_progressbar_value_1_1_" + options.rowId + "\" type=\"hidden\" value=\"" + cellData[1] + "\"></input>";
	formattedCell += "<input id=\"p28_progressbar_value_1_2_" + options.rowId + "\" type=\"hidden\" value=\"" + cellData[2] + "\"></input>";
	formattedCell += "</div>";
	        
    return formattedCell;
}

function p28noServidoFormatter(cellvalue, options, rowObject) {
    var cellData = cellvalue.split("**");
    
	var formattedCell = "";
	
	formattedCell += "<div class=\"p28_gridSegCampanasColum2\">";
	formattedCell += "<div id=\"p28_progressbar_2_" + options.rowId + "\" class=\"p28_progressbar_2_img\"><div id=\"p28_progress-label2_1_" + options.rowId + "\" class=\"p28_progress-label2_1\"></div><div id=\"p28_progress-label2_2_" + options.rowId + "\" class=\"p28_progress-label2_2\"></div></div>";
	formattedCell += "<input id=\"p28_progressbar_value_2_1_" + options.rowId + "\" type=\"hidden\" value=\"" + cellData[0] + "\"></input>";
	formattedCell += "<input id=\"p28_progressbar_value_2_2_" + options.rowId + "\" type=\"hidden\" value=\"" + cellData[1] + "\"></input>";
	formattedCell += "</div>";
	        
    return formattedCell;
}

function p28ventasPrevisionFormatter(cellvalue, options, rowObject) {
    var cellData = cellvalue.split("**");
    
	var formattedCell = "";
	
	formattedCell += "<div class=\"p28_gridSegCampanasColum3\">";
	formattedCell += "<div id=\"p28_progressbar_3_" + options.rowId + "\" class=\"p28_progressbar_3_img\"><div  id=\"p28_progress-label3_" + options.rowId + "\" class=\"p28_progress-label3\"></div></div>";
	formattedCell += "<input id=\"p28_progressbar_value_3_1_" + options.rowId + "\" type=\"hidden\" value=\"" + cellData[0] + "\"></input>";
	formattedCell += "<input id=\"p28_progressbar_value_3_2_" + options.rowId + "\" type=\"hidden\" value=\"" + cellData[1] + "\"></input>";
	formattedCell += "</div>";
	        
    return formattedCell;
}

function p28stockFormatter(cellvalue, opts, rData) {
	var cellData = cellvalue.split("**");
	var formattedCell = "";
	
	if (cellData[0].indexOf("-") > -1){ //Es un valor negativo, se sustituye por 0		
		cellData[0] = "0"
	}
	
	if (cellData[1].indexOf("-") > -1){ //Es un valor negativo, se sustituye por 0
		cellData[1] = "0"
	}
    
    formattedCell = "<div class=\"p28stock1\"><span>" + $.formatNumber(cellData[0],{format:"#,##0.#",locale:"es"}) + "</span></div><div class=\"p28stockSeparador\"><span>/</span></div><div class=\"p28stock2\"><span>" + $.formatNumber(cellData[1],{format:"#,##0.#",locale:"es"}) + "</span></div>";

    return formattedCell;
}

function p28FormateoDate(fechaDDMMYYYY) {
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

function p28FormateoTextoCampanaOferta(txtCampanaOferta, fechaInicioDDMMYYYY, fechaFinDDMMYYYY) {
	var txtCampanaOfertaFormateada = "";
	if (null != txtCampanaOferta && "" != txtCampanaOferta){
		if (txtCampanaOferta.length > 40) {
			var txtCampanaOfertaFormateada = txtCampanaOferta.substring(0,40) + "...";
		}
		else {
			var txtCampanaOfertaFormateada = txtCampanaOferta;
		}
		txtCampanaOfertaFormateada = txtCampanaOfertaFormateada + " (" + p28FormateoDate(fechaInicioDDMMYYYY) + " - " + p28FormateoDate(fechaFinDDMMYYYY) + ")";
	}
	return txtCampanaOfertaFormateada;
}

function p28FormateoTextoCampanaOfertaTitle(txtCampanaOferta, fechaInicioDDMMYYYY, fechaFinDDMMYYYY) {
	var txtCampanaOfertaFormateada = "";
	if (null != txtCampanaOferta && "" != txtCampanaOferta){
		txtCampanaOfertaFormateada = txtCampanaOferta + " (" + p28FormateoDate(fechaInicioDDMMYYYY) + " - " + p28FormateoDate(fechaFinDDMMYYYY) + ")";
	}
	return txtCampanaOfertaFormateada;
}

function hideShowColumns(val){

	if(val==3){
		//Textil
		jQuery(gridP28.nameJQuery).jqGrid('showCol', ["referencia","servidoPendiente","color","talla","modeloProveedor","noServido","ventasPrevision","stock"]);
	}else{
		jQuery(gridP28.nameJQuery).jqGrid('hideCol', ["color","talla","modeloProveedor"]);
	}
	//redimensionar jqGrid.
	jQuery(gridP28.nameJQuery).jqGrid('setGridWidth', $("#p28_popupRefCampanas").width(), true);
}
