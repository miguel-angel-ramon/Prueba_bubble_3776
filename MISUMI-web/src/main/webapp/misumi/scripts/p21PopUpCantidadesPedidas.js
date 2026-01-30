var p21BotonAceptar = null;
var gridP21=null;

$(document).ready(function(){
	loadP21(locale);
});

function initializeScreenP21PopupCantidadesPedidas(){
	$( "#p21_popupCantidadesPedidas" ).dialog({
        autoOpen: false,
        height: 650,
        width: 1000,
        modal: true,
        resizable: false,
        buttons:[{
            text: p21BotonAceptar,
            click: function() {
                      $(this).dialog('close');
            }
        }],
		open: function() {
			$('.ui-dialog-titlebar-close').on('mousedown', function(){
				$("#p21_popupCantidadesPedidas").dialog('close');
			});
		}
    });
	
	$(window).bind('resize', function() {
	    $("#p21_popupCantidadesPedidas").dialog("option", "position", "center");
	});
}

function reset_p21(){
	$("#p21_lbl_fechaPedidoVal").text("");
    $("#p21_fld_fechaPedidoVal").val("");
  	$("#p21_lbl_areaVal").text("");
  	$("#p21_fld_areaVal").val("");
  	$("#p21_lbl_seccionVal").text("");
  	$("#p21_fld_seccionVal").val("");
  	$("#p21_lbl_categoriaVal").text("");
  	$("#p21_fld_categoriaVal").val("")
  	$("#p21_lbl_mapaVal").text("");
  	$("#p21_fld_mapaVal").val("");
  	
	$("#p21_lbl_totalReferenciasBajoPedidoVal").text("");
	$("#p21_lbl_totalCajasBajoPedidoVal").text("");
	$("#p21_lbl_totalReferenciasEmpujeVal").text("");
	$("#p21_lbl_totalCajasEmpujeVal").text("");
	$("#p21_lbl_totalReferenciasImplantacionCabeceraVal").text("");
	$("#p21_lbl_totalCajasImplantacionCabeceraVal").text("");

	$("#p21_lbl_totalReferenciasIntertiendaVal").text("");
	$("#p21_lbl_totalCajasIntertiendaVal").text("");
	
	$("#p21_lbl_color").text("");
	$("#p21_lbl_talla").text("");
	$("#p21_lbl_modeloProveedor").text("");

	
	
  	$(gridP21.nameJQuery).jqGrid('clearGridData');
  	$(gridP21.nameJQuery).jqGrid('setGridParam', { rowNum: 10 });
  	$("#p21_popupCantidadesPedidas .ui-pg-selbox").val(10);
  	//$(gridP21.nameJQuery).jqGrid('setGridWidth', $("#p21_AreaCabecera").width(), true);
}

function events_p21_btn_exportExcel(){
	$("#p21_btn_exportExcel").click(function () {
		exportExcel21();
	});
}

function loadP21(locale){
	gridP21 = new GridP21CantPed(locale);
	if (esCentroVegalsa()){
		$(gridP21.nameJQuery).jqGrid('setGridWidth', $("#p21_AreaCabecera").width());
	}else{
		$(gridP21.nameJQuery).jqGrid('setGridWidth', $("#p21_AreaCabecera").width(),true);
	}
	var jqxhr = $.getJSON(gridP21.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				gridP21.colNames = data.ColNames;
				gridP21.title = data.GridTitle;
				gridP21.emptyRecords= data.emptyRecords;
				p21BotonAceptar = data.p21BotonAceptar;
				load_gridP21CantPedMock(gridP21);
				initializeScreenP21PopupCantidadesPedidas();
				events_p21_btn_exportExcel();
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
        });
}

function load_gridP21CantPedMock(grid) {

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
			shrinkToFit:false,
			forceFit:true,
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
			grid.headerHeight("p21_gridHeader");				
					
			},
			loadComplete : function(data) {
				grid.actualPage = data.page;
				grid.localData = data;
				grid.sortIndex = grid.sortIndex;
				grid.sortOrder = grid.sortOrder;	
				$("#p21_AreaReferencias .loading").css("display", "none");
			},
			resizeStop: function () {
				grid.saveColumnState.call($(this),grid.myColumnsState);
				$(grid.nameJQuery).jqGrid('setGridWidth', $("#p21_AreaCabecera").width(), false);
            },
			onPaging : function(postdata) {			
				alreadySorted = false;
				grid.sortIndex = grid.sortIndex;
				grid.sortOrder = grid.sortOrder;
				grid.saveColumnState.call($(this), this.p.remapColumns);
				//$(grid.nameJQuery).jqGrid('setGridWidth', $("#p21_AreaCabecera").width(), true);
				reloadData_gridP21CantPed(grid);
				return 'stop';
			},
			onSelectRow: function(id){

			},
			onSortCol : function (index, columnIndex, sortOrder){
				grid.sortIndex = index;
				grid.sortOrder = sortOrder;
				grid.saveColumnState.call($(this), this.p.remapColumns);
				//$(grid.nameJQuery).jqGrid('setGridWidth', $("#p21_AreaCabecera").width(), true);
				reloadData_gridP21CantPed(grid);
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

function reloadDataP21(seguimientoMiPedido){
	reset_p21();
	$( "#p21_popupCantidadesPedidas" ).dialog( "open" );
	reloadData_gridP21CantPed(gridP21,seguimientoMiPedido);
	$("#p21_lbl_fechaPedidoVal").text(seguimientoMiPedido.fechaPedidoPantalla);
	$("#p21_fld_fechaPedidoVal").val(seguimientoMiPedido.fechaPedidoDDMMYYYY);
	$("#p21_fld_areaVal").val(seguimientoMiPedido.codArea);
	//comprobamos si pertenece a textil y formateamos las columnas a mostrar.
	hideShowColumns21($("#p21_fld_areaVal").val());
	$("#p21_fld_seccionVal").val(seguimientoMiPedido.codSeccion);
	$("#p21_fld_mapaVal").val(seguimientoMiPedido.mapa);
	$("#p21_fld_categoriaVal").val(seguimientoMiPedido.codCategoria)
	if (esCentroVegalsa()){
		$('#p21_lbl_fechaReposicion').show();
		$('#p21_lbl_fechaPedido').hide();
	}else{
		$('#p21_lbl_fechaReposicion').hide();
		$('#p21_lbl_fechaPedido').show();
	}
	if (seguimientoMiPedido.codArea!=null && seguimientoMiPedido.codArea!=""){
		$("#p21_lbl_areaVal").text(seguimientoMiPedido.codArea+"-"+seguimientoMiPedido.descArea);
	}else{
		$("#p21_lbl_areaVal").text("");
	}
	if (seguimientoMiPedido.codSeccion!=null && seguimientoMiPedido.codSeccion!=""){
		$("#p21_lbl_seccionVal").text(seguimientoMiPedido.codSeccion+"-"+seguimientoMiPedido.descSeccion);
	}else{
		$("#p21_lbl_seccionVal").text("");
	}
	if (seguimientoMiPedido.codCategoria!=null && seguimientoMiPedido.codCategoria!=""){
		$("#p21_lbl_categoriaVal").text(seguimientoMiPedido.codCategoria+"-"+seguimientoMiPedido.descCategoria);
	}else{
		$("#p21_lbl_categoriaVal").text("");
	}
	if (seguimientoMiPedido.mapa!=null && seguimientoMiPedido.mapa!="" && seguimientoMiPedido.mapa!="0"){
		$("#p21_lbl_mapaVal").text(seguimientoMiPedido.mapa+"-"+seguimientoMiPedido.descrMapa);
	}else if (seguimientoMiPedido.mapa=="0"){
		$("#p21_lbl_mapaVal").text("SIN MAPA");
	}else{
		$("#p21_lbl_mapaVal").text("");
	}
	reloadData_cabeceraP21(seguimientoMiPedido);
}

function reloadData_gridP21CantPed(grid, seguimientoMiPedidoParam) {

	if (grid.firstLoad) {
		if (esCentroVegalsa()){
			$(grid.nameJQuery).jqGrid('setGridWidth', $("#p21_AreaCabecera").width());
		}else{
			$(grid.nameJQuery).jqGrid('setGridWidth', $("#p21_AreaCabecera").width(),true);
		}
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
		seguimientoMiPedido = new SeguimientoMiPedido($("#centerId").val() , seguimientoMiPedidoParam.fechaPedidoDDMMYYYY, '', seguimientoMiPedidoParam.codArea, '', seguimientoMiPedidoParam.codSeccion, '', seguimientoMiPedidoParam.codCategoria, '', '', seguimientoMiPedidoParam.codArt, seguimientoMiPedidoParam.mapa, seguimientoMiPedidoParam.descrMapa);
	}else{
		seguimientoMiPedido = new SeguimientoMiPedido($("#centerId").val() , $("#p21_fld_fechaPedidoVal").val(), '', $("#p21_fld_areaVal").val(), '', $("#p21_fld_seccionVal").val(), '', $("#p21_fld_categoriaVal").val(), '', '', $("#p20_RefEroski").val(),$("#p21_fld_mapaVal").val(),'');
	}
	
	var objJson = $.toJSON(seguimientoMiPedido.prepareToJsonObject());
	$("#p21_AreaReferencias .loading").css("display", "block");
	$.ajax({
		type : 'POST',			
		url : './misPedidos/cantidadesPedidas/loadDataGrid.do?page='+grid.getActualPage()+'&max='+grid.getRowNumPerPage()+'&index='+grid.getSortIndex()+'&sortorder='+grid.getSortOrder(),
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		
			$(grid.nameJQuery)[0].addJSONData(data);
			grid.actualPage = data.page;
			grid.localData = data;
			//$(grid.nameJQuery).jqGrid('setGridWidth', $("#p21_AreaCabecera").width(), true);
			$('#p21_popupCantidadesPedidas').animate({scrollTop: '0px'}, 0);
			$("#p21_AreaReferencias .loading").css("display", "none");
		},
		error : function (xhr, status, error){
			//$(grid.nameJQuery).jqGrid('setGridWidth', $("#p21_AreaCabecera").width(), true);
			$('#p21_popupCantidadesPedidas').animate({scrollTop: '0px'}, 0);
			$("#p21_AreaReferencias .loading").css("display", "none");
			handleError(xhr, status, error, locale);				
        }			
	});		
}

function reloadData_cabeceraP21(seguimientoMiPedidoParam) {

	var seguimientoMiPedido = null;
	if (seguimientoMiPedidoParam != null){
		seguimientoMiPedido = new SeguimientoMiPedido($("#centerId").val() , seguimientoMiPedidoParam.fechaPedidoDDMMYYYY, '', seguimientoMiPedidoParam.codArea, '', seguimientoMiPedidoParam.codSeccion, '', seguimientoMiPedidoParam.codCategoria, '', '', seguimientoMiPedidoParam.codArt, seguimientoMiPedidoParam.mapa, seguimientoMiPedidoParam.descrMapa);
	}else{
		seguimientoMiPedido = new SeguimientoMiPedido($("#centerId").val() , $("#p21_fld_fechaPedidoVal").val(), '', $("#p21_fld_areaVal").val(), '', $("#p21_fld_seccionVal").val(), '', $("#p21_fld_categoriaVal").val(), '', '', $("#p20_fld_referencia_tmp").val(),$("#p21_fld_mapaVal").val(),'');
	}
	
	var objJson = $.toJSON(seguimientoMiPedido.prepareToJsonObject());
	$.ajax({
		type : 'POST',			
		url : './misPedidos/cantidadesPedidas/loadDataCabecera.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		
			if (data.pedTotalRefBajoPedido!=null && data.pedTotalRefBajoPedido!=""){
				$("#p21_lbl_totalReferenciasBajoPedidoVal").text(data.pedTotalRefBajoPedido);
			}else{
				$("#p21_lbl_totalReferenciasBajoPedidoVal").text("0");
			}
			if (data.pedTotalCajasBajoPedido!=null && data.pedTotalCajasBajoPedido!=""){
				$("#p21_lbl_totalCajasBajoPedidoVal").text($.formatNumber(data.pedTotalCajasBajoPedido,{format:"0.0",locale:"es"}));
			}else{
				$("#p21_lbl_totalCajasBajoPedidoVal").text("0,0");
			}
			
			if (data.pedTotalRefEmpuje!=null && data.pedTotalRefEmpuje!=""){
				$("#p21_lbl_totalReferenciasEmpujeVal").text(data.pedTotalRefEmpuje);
			}else{
				$("#p21_lbl_totalReferenciasEmpujeVal").text("0");
			}
			if (data.pedTotalCajasEmpuje!=null && data.pedTotalCajasEmpuje!=""){
				$("#p21_lbl_totalCajasEmpujeVal").text($.formatNumber(data.pedTotalCajasEmpuje,{format:"0.0",locale:"es"}));
			}else{
				$("#p21_lbl_totalCajasEmpujeVal").text("0,0");
			}
			
			if (data.pedTotalRefImplCab!=null && data.pedTotalRefImplCab!=""){
				$("#p21_lbl_totalReferenciasImplantacionCabeceraVal").text(data.pedTotalRefImplCab);
			}else{
				$("#p21_lbl_totalReferenciasImplantacionCabeceraVal").text("0");
			}
			if (data.pedTotalCajasImplCab!=null && data.pedTotalCajasImplCab!=""){
				$("#p21_lbl_totalCajasImplantacionCabeceraVal").text($.formatNumber(data.pedTotalCajasImplCab,{format:"0.0",locale:"es"}));
			}else{
				$("#p21_lbl_totalCajasImplantacionCabeceraVal").text("0,0");
			}
			
			if (data.pedTotalRefIntertienda!=null && data.pedTotalRefIntertienda!=""){
				$("#p21_lbl_totalReferenciasIntertiendaVal").text(data.pedTotalRefIntertienda);
			}else{
				$("#p21_lbl_totalReferenciasIntertiendaVal").text("0");
			}
			if (data.pedTotalCajasIntertienda!=null && data.pedTotalCajasIntertienda!=""){
				$("#p21_lbl_totalCajasIntertiendaVal").text($.formatNumber(data.pedTotalCajasIntertienda,{format:"0.0",locale:"es"}));
			}else{
				$("#p21_lbl_totalCajasIntertiendaVal").text("0,0");
			}
			
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
        }			
	});		
}

/*Clase de constantes para el GRID de cantidades pedidas*/
function GridP21CantPed (locale){
	
	// Atributos
	this.name = "gridP21CantPed"; 
	this.nameJQuery = "#gridP21CantPed"; 
	this.i18nJSON = './misumi/resources/p21PopUpCantidadesPedidas/p21PopUpCantidadesPedidas_' + locale + '.json';
	this.colNames = null; //Esta en el json
	this.cm = [
		   		{
					"name"  : "referencia",
					"index" : "referencia",
					"width" : 68,	
				    "align" : "left"
				},{
					"name"  : "descripcion",
					"index" : "descripcion", 
					"width" : 200,	
					"align" : "left"						
		   		},{
		   			"name"  : "color",
					"index" : "color", 
					"width" : 80,	
					"align" : "left"	
				},{
		   			"name"  : "talla",
					"index" : "talla", 
					"width" : 80,	
					"align" : "left"	
				},{
		   			"name"  : "modeloProveedor",
					"index" : "modeloProveedor", 
					"width" : 200,	
					"align" : "left"	
				},{
					"name"  : "motivoPedido",
					"index" : "motivoPedido", 
					"width" : 100,	
					"align" : "left"						
		   		},{
					"name"  : "total",
					"index" : "total", 
					"width" : 80,
					"formatter": camposUnDecFormatNumber,
					"classes" : "p21_columnaTotal",
					"align" : "center"
				},{
					"name"  : "cajasAntesAjuste",
					"index" : "cajasAntesAjuste",
					"width" : 140,
					"formatter":camposSinDecFormatNumber,
					"align" : "center"
				},{
					"name"  : "cajasNormales",
					"index" : "cajasNormales",
					"width" : 70,
					"formatter":camposUnDecFormatNumber,
					"align" : "center"
				},{
					"name"  : "cajasEmpuje",
					"index" : "cajasEmpuje",
					"width" : 90,
					"formatter":camposUnDecFormatNumber,
					"align" : "center"
				},{
					"name"  : "cajasCabecera",
					"index" : "cajasCabecera",
					"width" : 140,
					"formatter":camposUnDecFormatNumber,
					"align" : "center"
				},{
					"name"  : "cajasIntertienda",
					"index" : "cajasIntertienda",
					"width" : 95,
					"formatter":camposUnDecFormatNumber,
					"align" : "center"
				},{
					name    : 'cajasCortadas',
					index   : 'cajasCortadas',
					width   : 70,
					formatter:camposUnDecFormatNumber,
					align   : 'center'
				},{
					name    : 'incPrevisionVenta',
					index   : 'incPrevisionVenta',
					width   : 60,
					formatter: 'number',
					formatoptions: {thousandsSeparator: ".", decimalPlaces: 0, defaultValue: '0%', suffix: "%"},
					align   : 'center'
				},{
					name    : 'smEstatico',
					index   : 'smEstatico',
					width   : 60,
					align   : 'center'
				},{
					name    : 'facing',
					index   : 'facing',
					width   : 50,
					align   : 'center'
				},{
					name    : 'origenPedido',
					index   : 'origenPedido',
					width   : 90,
					align   : 'left'
				}
			];
	this.locale = locale;
	this.sortIndex = "referencia";
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerGridP21"; //"pagerP16Movimientos"; 
	this.pagerNameJQuery = "#pagerGridP21"; //"#pagerP16Movimientos";
	this.title = null; //Esta en el json
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null; //Esta en el json
	this.rownumbers = true;
	this.myColumnStateName = 'gridP21CantPed.colState';
    this.myColumnsState = null;
    this.isColState = null;
    this.firstLoad = true;
	
	//Metodos		
	this.getActualPage = function getActualPage(){ 
		if (this.actualPage == null){
			this.actualPage = 1;
		}else{
			this.actualPage = $(this.nameJQuery).getGridParam('page');
		}
		
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

function camposUnDecFormatNumber(cellValue, opts, rData) {
	//Actualizamos el formateo de los campos a un decimal.
	return $.formatNumber(cellValue,{format:"0.0",locale:"es"});
}

function camposSinDecFormatNumber(cellValue, opts, rData) {
	//Actualizamos el formateo de los campos sin decimales.
	var cajasAntesAjust=$.formatNumber(cellValue,{format:"0",locale:"es"});
	if(cajasAntesAjust!=0){
		return cajasAntesAjust;
	}else{
		return "";
	}
}

function exportExcel21(){
	var colModel = $(gridP21.nameJQuery).jqGrid("getGridParam", "colModel");
    var colNames = $(gridP21.nameJQuery).jqGrid("getGridParam", "colNames");
    var myColumns = new Array();
    var myColumnsNames = new Array();
    var j=0;
    var currentName;
  
    $.each(colModel, function(i) {
    	if (colModel[i].name!="rn" && !colModel[i].hidden ){
    		//Transformación del nombre de la columna para eliminar estilo
    		currentName = colNames[i];
    		if (currentName!=null && currentName!="null" && currentName!=undefined && currentName!="undefined"){
	    		currentName = currentName.replace("<br/>", " ");
	    		if (currentName.indexOf(">")>0 && currentName.lastIndexOf("<")>0){
	    			currentName = currentName.substring(currentName.indexOf(">")+1 , currentName.lastIndexOf("<"))
	    		}
    		}
    		myColumnsNames[j]=currentName;
	    	
	    	myColumns[j]=colModel[i].name;
	    	j++;	
    	}
     });
    var form = "<form name='csvexportform' action='misPedidos/cantidadesPedidas/exportGrid.do'  accept-charset='ISO-8859-1' method='get'>";
	form = form + "<input type='hidden' name='headers' value='"+myColumnsNames+"'>";		
	form = form + "<input type='hidden' name='model' value='"+myColumns+"'>";

	if ($("#centerId").val()!="null" && $("#centerId").val()!=null ){
		form = form + "<input type='hidden' name='codCentro' value='"+$("#centerId").val()+"'>";
	}	
	if ($("#p21_fld_fechaPedidoVal").val()!="null" && $("#p21_fld_fechaPedidoVal").val()!=null ){
		form = form + "<input type='hidden' name='fechaPedidoDDMMYYYY' value='"+$("#p21_fld_fechaPedidoVal").val()+"'>";
	}	
	if ($("#p21_fld_areaVal").val()!="null" && $("#p21_fld_areaVal").val()!=null ){
		form = form + "<input type='hidden' name='codArea' value='"+$("#p21_fld_areaVal").val()+"'>";
	}	
	if ($("#p21_fld_seccionVal").val()!="null" && $("#p21_fld_seccionVal").val()!=null ){
		form = form + "<input type='hidden' name='codSeccion' value='"+$("#p21_fld_seccionVal").val()+"'>";
	}
	if ($("#p21_fld_categoriaVal").val()!="null" && $("#p21_fld_categoriaVal").val()!=null ){
		form = form + "<input type='hidden' name='codCategoria' value='"+$("#p21_fld_categoriaVal").val()+"'>";
	}
	if ($("#p20_fld_referencia").val()!="null" && $("#p20_fld_referencia").val()!=null ){
		form = form + "<input type='hidden' name='codArt' value='"+$("#p20_RefEroski").val()+"'>";
	}
	// MISUMI-299
	var radioValue = $("input[name='p20_rad_tipoFiltro']:checked").val();
	if (radioValue == 3){
		form = form + "<input type='hidden' name='codMapa' value='"+$("#p21_fld_mapaVal").val()+"'>";
		var descMapa = $("#p21_lbl_mapaVal").text();
		form = form + "<input type='hidden' name='descMapa' value='"+descMapa+"'>";
	}
	form = form + "</form><script>document.csvexportform.submit();</script>";
	Show_Popup(form);	 
}

function hideShowColumns21(val){

	if(val==3){
		//Textil
		jQuery(gridP21.nameJQuery).jqGrid('showCol', ["referencia","descripcion","color","talla","modeloProveedor","total","cajasNormales","cajasEmpuje","cajasCabecera","cajasIntertienda"]);
	}else{
		jQuery(gridP21.nameJQuery).jqGrid('hideCol', ["color","talla","modeloProveedor"]);
	}
	
	var permisoAjustePedidos = $("#p20_permiso_ajuste_pedidos").val();
	
	if (permisoAjustePedidos != 'true' ){
		jQuery(gridP21.nameJQuery).jqGrid('hideCol', ["cajasAntesAjuste"]);
	}
	
	var radioValue = $("input[name='p20_rad_tipoFiltro']:checked").val();
	
	// MISUMI-350: MISUMI-JAVA VEGALSA SEGUIMIENTO DE PEDIDOS añadir el motivo de pedido
	if (esCentroVegalsa()){
		jQuery(gridP21.nameJQuery).jqGrid('showCol', ["motivoPedido"]);
		jQuery(gridP21.nameJQuery).jqGrid('showCol', ["cajasCortadas"]);
		jQuery(gridP21.nameJQuery).jqGrid('showCol', ["incPrevisionVenta"]);
		jQuery(gridP21.nameJQuery).jqGrid('showCol', ["smEstatico"]);
		jQuery(gridP21.nameJQuery).jqGrid('showCol', ["facing"]);
		jQuery(gridP21.nameJQuery).jqGrid('showCol', ["origenPedido"]);
		$('.p21_etiqValorCampo').width("40%");
		$('#p21_divArea').width("27%");
		$('#p21_divMapa').width("30%");
		$('#p21_divMapa').show();
	}else{
		jQuery(gridP21.nameJQuery).jqGrid('hideCol', ["motivoPedido"]);
		jQuery(gridP21.nameJQuery).jqGrid('hideCol', ["cajasCortadas"]);
		jQuery(gridP21.nameJQuery).jqGrid('hideCol', ["incPrevisionVenta"]);
		jQuery(gridP21.nameJQuery).jqGrid('hideCol', ["smEstatico"]);
		jQuery(gridP21.nameJQuery).jqGrid('hideCol', ["facing"]);
		jQuery(gridP21.nameJQuery).jqGrid('hideCol', ["origenPedido"]);
		$('.p21_etiqValorCampo').width("48%");
		$('#p21_divMapa').hide();
	}
	
	//redimensionar jqGrid.
	if (esCentroVegalsa()){
		$(gridP21.nameJQuery).jqGrid('setGridWidth', $("#p21_popupCantidadesPedidas").width());
	}else{
		$(gridP21.nameJQuery).jqGrid('setGridWidth', $("#p21_popupCantidadesPedidas").width(),true);
	}
}
