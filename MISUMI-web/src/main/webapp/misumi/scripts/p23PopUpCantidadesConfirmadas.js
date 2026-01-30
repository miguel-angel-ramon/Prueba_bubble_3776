var p23BotonAceptar = null;
var gridP23=null;

$(document).ready(function(){
	loadP23(locale);
});

function initializeScreenP23PopupCantidadesConfirmadas(){
	
	$( "#p23_popupCantidadesConfirmadas" ).dialog({
        autoOpen: false,
        height: 650,
        width: 1000,
        modal: true,
        resizable: false,
        buttons:[{
            text: p23BotonAceptar,
            click: function() {
                      $(this).dialog('close');
                   }
        }],
		open: function() {
			$('.ui-dialog-titlebar-close').on('mousedown', function(){
				$("#p23_popupCantidadesConfirmadas").dialog('close');
			});
		}
    });
	
	$(window).bind('resize', function() {
	    $("#p23_popupCantidadesConfirmadas").dialog("option", "position", "center");
	});
	
}

function reset_p23(){
	$("#p23_lbl_fechaPedidoVal").text("");
    $("#p23_fld_fechaPedidoVal").val("");
  	$("#p23_lbl_areaVal").text("");
  	$("#p23_fld_areaVal").val("");
  	$("#p23_lbl_seccionVal").text("");
  	$("#p23_fld_seccionVal").val("");
  	$("#p23_lbl_categoriaVal").text("");
  	$("#p23_fld_categoriaVal").val("")
  	$("#p23_lbl_mapaVal").text("");
  	$("#p23_fld_mapaVal").val("");
	$("#p23_lbl_totalReferenciasBajoPedidoVal").text("");
	$("#p23_lbl_totalCajasBajoPedidoVal").text("");
	$("#p23_lbl_totalReferenciasEmpujeVal").text("");
	$("#p23_lbl_totalCajasEmpujeVal").text("");
	
	$("#p23_lbl_totalReferenciasIntertiendaVal").text("");
	$("#p23_lbl_totalCajasIntertiendaVal").text("");
	
	
	$("#p23_lbl_totalReferenciasImplantacionCabeceraVal").text("");
	$("#p23_lbl_totalCajasImplantacionCabeceraVal").text("");
	
  	$(gridP23.nameJQuery).jqGrid('clearGridData');
  	$(gridP23.nameJQuery).jqGrid('setGridParam', { rowNum: 10 });
  	$("#p23_popupCantidadesConfirmadas .ui-pg-selbox").val(10);
  	//$(gridP23.nameJQuery).jqGrid( $("#p23_AreaCabecera").width(), true);
}

function events_p23_btn_exportExcel(){
	$("#p23_btn_exportExcel").click(function () {
		exportExcel23();
	});
}

function loadP23(locale){
	gridP23 = new GridP23CantConf(locale);
	if (esCentroVegalsa()){
		$(gridP23.nameJQuery).jqGrid('setGridWidth', $("#p23_AreaCabecera").width());
	}else{
		$(gridP23.nameJQuery).jqGrid('setGridWidth', $("#p23_AreaCabecera").width(), true);
	}
	var jqxhr = $.getJSON(gridP23.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				gridP23.colNames = data.ColNames;
				gridP23.title = data.GridTitle;
				gridP23.emptyRecords= data.emptyRecords;
				p23BotonAceptar = data.p23BotonAceptar;
				load_gridP23CantConfMock(gridP23);
				initializeScreenP23PopupCantidadesConfirmadas();
				events_p23_btn_exportExcel();
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
        });
}

function load_gridP23CantConfMock(grid) {

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
							grid.headerHeight("p23_gridHeader");									
			},
			loadComplete : function(data) {
				grid.actualPage = data.page;
				grid.localData = data;
				grid.sortIndex = grid.sortIndex;
				grid.sortOrder = grid.sortOrder;
				$("#p23_AreaReferencias .loading").css("display", "none");
			},
			resizeStop: function () {
				grid.saveColumnState.call($(this),grid.myColumnsState);
				$(grid.nameJQuery).jqGrid('setGridWidth', $("#p23_AreaCabecera").width(), false);
            },																																																																																																																																																																						
			onPaging : function(postdata) {			
				alreadySorted = false;
				grid.sortIndex = grid.sortIndex;
				grid.sortOrder = grid.sortOrder;
				grid.saveColumnState.call($(this), this.p.remapColumns);
				//$(grid.nameJQuery).jqGrid('setGridWidth', $("#p23_AreaCabecera").width(), true);
				reloadData_gridP23CantConf(grid);
				return 'stop';
			},
			onSelectRow: function(id){
			},
			onSortCol : function (index, columnIndex, sortOrder){
				grid.sortIndex = index;
				grid.sortOrder = sortOrder;
				grid.saveColumnState.call($(this), this.p.remapColumns);
				//$(grid.nameJQuery).jqGrid('setGridWidth', $("#p23_AreaCabecera").width(), true);
				reloadData_gridP23CantConf(grid);
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

function reloadDataP23(seguimientoMiPedido){
	reset_p23();
	$("#p23_popupCantidadesConfirmadas").dialog( "open" );
	reloadData_gridP23CantConf(gridP23,seguimientoMiPedido);
	$("#p23_lbl_fechaPedidoVal").text(seguimientoMiPedido.fechaPedidoPantalla);
	$("#p23_fld_fechaPedidoVal").val(seguimientoMiPedido.fechaPedidoDDMMYYYY);
	$("#p23_fld_areaVal").val(seguimientoMiPedido.codArea);
	$("#p23_fld_seccionVal").val(seguimientoMiPedido.codSeccion);
	$("#p23_fld_mapaVal").val(seguimientoMiPedido.mapa);
	$("#p23_fld_categoriaVal").val(seguimientoMiPedido.codCategoria);
	if (esCentroVegalsa()){
		$('#p23_lbl_fechaReposicion').show();
		$('#p23_lbl_fechaPedido').hide();
	}else{
		$('#p23_lbl_fechaReposicion').hide();
		$('#p23_lbl_fechaPedido').show();
	}
	if (seguimientoMiPedido.codArea!=null && seguimientoMiPedido.codArea!=""){
		$("#p23_lbl_areaVal").text(seguimientoMiPedido.codArea+"-"+seguimientoMiPedido.descArea);
	}else{
		$("#p23_lbl_areaVal").text("");
	}
	if (seguimientoMiPedido.codSeccion!=null && seguimientoMiPedido.codSeccion!=""){
		$("#p23_lbl_seccionVal").text(seguimientoMiPedido.codSeccion+"-"+seguimientoMiPedido.descSeccion);
	}else{
		$("#p23_lbl_seccionVal").text("");
	}
	if (seguimientoMiPedido.codCategoria!=null && seguimientoMiPedido.codCategoria!=""){
		$("#p23_lbl_categoriaVal").text(seguimientoMiPedido.codCategoria+"-"+seguimientoMiPedido.descCategoria);
	}else{
		$("#p23_lbl_categoriaVal").text("");
	}
	if (seguimientoMiPedido.mapa!=null && seguimientoMiPedido.mapa!="" && seguimientoMiPedido.mapa!="0"){
		$("#p23_lbl_mapaVal").text(seguimientoMiPedido.mapa+"-"+seguimientoMiPedido.descrMapa);
	}else if (seguimientoMiPedido.mapa=="0"){
		$("#p23_lbl_mapaVal").text("SIN MAPA");
	}else{
		$("#p23_lbl_mapaVal").text("");
	}
	hideShowColumns23($("#p23_fld_areaVal").val());
	reloadData_cabeceraP23(seguimientoMiPedido);
}

function reloadData_gridP23CantConf(grid,seguimientoMiPedidoParam) {
	
	if (grid.firstLoad) {
		if (esCentroVegalsa()){
			$(grid.nameJQuery).jqGrid('setGridWidth', $("#p23_AreaCabecera").width());
		}else{
			$(grid.nameJQuery).jqGrid('setGridWidth', $("#p23_AreaCabecera").width(), true);
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
		seguimientoMiPedido = new SeguimientoMiPedido($("#centerId").val() , seguimientoMiPedidoParam.fechaPedidoDDMMYYYY, '', seguimientoMiPedidoParam.codArea, '', seguimientoMiPedidoParam.codSeccion, '', seguimientoMiPedidoParam.codCategoria, '', '', seguimientoMiPedidoParam.codArt,seguimientoMiPedidoParam.mapa, seguimientoMiPedidoParam.descrMapa);
	}else{
		seguimientoMiPedido = new SeguimientoMiPedido($("#centerId").val() , $("#p23_fld_fechaPedidoVal").val(), '', $("#p23_fld_areaVal").val(), '', $("#p23_fld_seccionVal").val(), '', $("#p23_fld_categoriaVal").val(), '', '', $("#p20_RefEroski").val(),$("#p23_fld_mapaVal").val(),'');
	}
	
	var objJson = $.toJSON(seguimientoMiPedido.prepareToJsonObject());
	
	$("#p23_AreaReferencias .loading").css("display", "block");
	$.ajax({
		type : 'POST',			
		url : './misPedidos/cantidadesConfirmadas/loadDataGrid.do?page='+grid.getActualPage()+'&max='+grid.getRowNumPerPage()+'&index='+grid.getSortIndex()+'&sortorder='+grid.getSortOrder(),
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		
			$(grid.nameJQuery)[0].addJSONData(data);
			grid.actualPage = data.page;
			grid.localData = data;
			//$(grid.nameJQuery).jqGrid('setGridWidth', $("#p23_AreaCabecera").width(), true);
			$('#p23_popupCantidadesConfirmadas').animate({scrollTop: '0px'}, 0);
			$("#p23_AreaReferencias .loading").css("display", "none");
		},
		error : function (xhr, status, error){
			//$(grid.nameJQuery).jqGrid('setGridWidth', $("#p23_AreaCabecera").width(), true);
			$('#p23_popupCantidadesConfirmadas').animate({scrollTop: '0px'}, 0);
			$("#p23_AreaReferencias .loading").css("display", "none");
			handleError(xhr, status, error, locale);				
        }			
	});		
}

function reloadData_cabeceraP23(seguimientoMiPedidoParam) {

	var seguimientoMiPedido = null;
	if (seguimientoMiPedidoParam != null){
		seguimientoMiPedido = new SeguimientoMiPedido($("#centerId").val() , seguimientoMiPedidoParam.fechaPedidoDDMMYYYY, '', seguimientoMiPedidoParam.codArea, '', seguimientoMiPedidoParam.codSeccion, '', seguimientoMiPedidoParam.codCategoria, '', '', seguimientoMiPedidoParam.codArt,seguimientoMiPedidoParam.mapa, seguimientoMiPedidoParam.descrMapa);
	}else{
		seguimientoMiPedido = new SeguimientoMiPedido($("#centerId").val() , $("#p21_fld_fechaPedidoVal").val(), '', $("#p21_fld_areaVal").val(), '', $("#p21_fld_seccionVal").val(), '', $("#p21_fld_categoriaVal").val(), '', '', $("#p20_fld_referencia_tmp").val(),$("#p23_fld_mapaVal").val(),'');
	}
	
	var objJson = $.toJSON(seguimientoMiPedido.prepareToJsonObject());
	$.ajax({
		type : 'POST',			
		url : './misPedidos/cantidadesConfirmadas/loadDataCabecera.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		
			if (data.confTotalRefBajoPedido!=null && data.confTotalRefBajoPedido!=""){
				$("#p23_lbl_totalReferenciasBajoPedidoVal").text(data.confTotalRefBajoPedido);
			}else{
				$("#p23_lbl_totalReferenciasBajoPedidoVal").text("0");
			}
			if (data.confTotalCajasBajoPedido!=null && data.confTotalCajasBajoPedido!=""){
				$("#p23_lbl_totalCajasBajoPedidoVal").text($.formatNumber(data.confTotalCajasBajoPedido,{format:"0",locale:"es"}));
			}else{
				$("#p23_lbl_totalCajasBajoPedidoVal").text("0");
			}
			if (data.confTotalRefEmpuje!=null && data.confTotalRefEmpuje!=""){
				$("#p23_lbl_totalReferenciasEmpujeVal").text(data.confTotalRefEmpuje);
			}else{
				$("#p23_lbl_totalReferenciasEmpujeVal").text("0");
			}
			if (data.confTotalCajasEmpuje!=null && data.confTotalCajasEmpuje!=""){
				$("#p23_lbl_totalCajasEmpujeVal").text($.formatNumber(data.confTotalCajasEmpuje,{format:"0",locale:"es"}));
			}else{
				$("#p23_lbl_totalCajasEmpujeVal").text("0");
			}
			if (data.confTotalRefImplCab!=null && data.confTotalRefImplCab!=""){
				$("#p23_lbl_totalReferenciasImplantacionCabeceraVal").text(data.confTotalRefImplCab);
			}else{
				$("#p23_lbl_totalReferenciasImplantacionCabeceraVal").text("0");
			}
			if (data.confTotalCajasImplCab!=null && data.confTotalCajasImplCab!=""){
				$("#p23_lbl_totalCajasImplantacionCabeceraVal").text($.formatNumber(data.confTotalCajasImplCab,{format:"0",locale:"es"}));
			}else{
				$("#p23_lbl_totalCajasImplantacionCabeceraVal").text("0");
			}
			if (data.confTotalRefIntertienda!=null && data.confTotalRefIntertienda!=""){
				$("#p23_lbl_totalReferenciasIntertiendaVal").text(data.confTotalRefIntertienda);
			}else{
				$("#p23_lbl_totalReferenciasIntertiendaVal").text("0");
			}
			if (data.confTotalCajasIntertienda!=null && data.confTotalCajasIntertienda!=""){
				$("#p23_lbl_totalCajasIntertiendaVal").text($.formatNumber(data.confTotalCajasIntertienda,{format:"0.0",locale:"es"}));
			}else{
				$("#p23_lbl_totalCajasIntertiendaVal").text("0");
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
        }			
	});		
}

/*Clase de constantes para el GRID de cantidades pedidas*/
function GridP23CantConf (locale){
	// Atributos
	this.name = "gridP23CantConf"; 
	this.nameJQuery = "#gridP23CantConf"; 
	this.i18nJSON = './misumi/resources/p23PopUpCantidadesConfirmadas/p23PopUpCantidadesConfirmadas_' + locale + '.json';
	this.colNames = null; //Está en el json
	this.cm = [
		   		{
					"name"  : "referencia",
					"index" : "referencia",
					"width" : 70,	
				    "align" : "left"
				},{
					"name"  : "descripcion",
					"index" : "descripcion", 
					"width" : 235,	
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
					"width" : 200,	
					"align" : "left"						
		   		},{
					"name"  : "total",
					"index" : "total", 
					"width" : 100,
					"formatter": "number",
					"classes" : "p23_columnaTotal",
					"align" : "center"
				},{
					"name"  : "cajasNormales",
					"index" : "cajasNormales",
					"width" : 135,
					"formatter":"number",
					"align" : "center"
				},{
					"name"  : "cajasEmpuje",
					"index" : "cajasEmpuje",
					"width" : 110,
					"formatter":"number",
					"align" : "center"
				},{
					"name"  : "cajasCabecera",
					"index" : "cajasCabecera",
					"width" : 135,
					"formatter":"number",
					"align" : "center"
				},{
					"name"  : "cajasIntertienda",
					"index" : "cajasIntertienda",
					"width" : 115,
					"formatter":"number",
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
					width   : 70,
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
	this.pagerName = "pagerGridP23"; //"pagerP16Movimientos"; 
	this.pagerNameJQuery = "#pagerGridP23"; //"#pagerP16Movimientos";
	this.title = null; //Está en el json
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null; //Está en el json
	this.rownumbers = true;
	this.myColumnStateName = 'gridP23CantConf.colState';
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

	this.getSortOrder = function getSortOrder() {	
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

function exportExcel23(){
	
	var colModel = $(gridP23.nameJQuery).jqGrid("getGridParam", "colModel");
    var colNames = $(gridP23.nameJQuery).jqGrid("getGridParam", "colNames");
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
    var form = "<form name='csvexportform' action='misPedidos/cantidadesConfirmadas/exportGrid.do'  accept-charset='ISO-8859-1' method='get'>";
	form = form + "<input type='hidden' name='headers' value='"+myColumnsNames+"'>";		
	form = form + "<input type='hidden' name='model' value='"+myColumns+"'>";

	if ($("#centerId").val()!="null" && $("#centerId").val()!=null ){
		form = form + "<input type='hidden' name='codCentro' value='"+$("#centerId").val()+"'>";
	}	
	if ($("#p23_fld_fechaPedidoVal").val()!="null" && $("#p23_fld_fechaPedidoVal").val()!=null ){
		form = form + "<input type='hidden' name='fechaPedidoDDMMYYYY' value='"+$("#p23_fld_fechaPedidoVal").val()+"'>";
	}	
	if ($("#p23_fld_areaVal").val()!="null" && $("#p23_fld_areaVal").val()!=null ){
		form = form + "<input type='hidden' name='codArea' value='"+$("#p23_fld_areaVal").val()+"'>";
	}	
	if ($("#p23_fld_seccionVal").val()!="null" && $("#p23_fld_seccionVal").val()!=null ){
		form = form + "<input type='hidden' name='codSeccion' value='"+$("#p23_fld_seccionVal").val()+"'>";
	}
	if ($("#p23_fld_categoriaVal").val()!="null" && $("#p23_fld_categoriaVal").val()!=null ){
		form = form + "<input type='hidden' name='codCategoria' value='"+$("#p23_fld_categoriaVal").val()+"'>";
	}
	if ($("#p20_fld_referencia").val()!="null" && $("#p20_fld_referencia").val()!=null ){
		form = form + "<input type='hidden' name='codArt' value='"+$("#p20_RefEroski").val()+"'>";
	}
	// MISUMI-299
	var radioValue = $("input[name='p20_rad_tipoFiltro']:checked").val();
	if (radioValue == 3){
		form = form + "<input type='hidden' name='codMapa' value='"+$("#p23_fld_mapaVal").val()+"'>";
		var descMapa = $("#p23_lbl_mapaVal").text();
		form = form + "<input type='hidden' name='descMapa' value='"+descMapa+"'>";
	}
	form = form + "</form><script>document.csvexportform.submit();</script>";
	Show_Popup(form);	 
}

function hideShowColumns23(val){

	if(val==3){
		//Textil
		jQuery(gridP23.nameJQuery).jqGrid('showCol', ["referencia","descripcion","color","talla","modeloProveedor","total","cajasNormales","cajasEmpuje","cajasCabecera","cajasIntertienda"]);
	}else{
		jQuery(gridP23.nameJQuery).jqGrid('hideCol', ["color","talla","modeloProveedor"]);
	}
	var radioValue = $("input[name='p20_rad_tipoFiltro']:checked").val();
	
	// MISUMI-350: MISUMI-JAVA VEGALSA SEGUIMIENTO DE PEDIDOS añadir el motivo de pedido
	if (esCentroVegalsa()){
		jQuery(gridP23.nameJQuery).jqGrid('showCol', ["motivoPedido"]);
		jQuery(gridP23.nameJQuery).jqGrid('showCol', ["cajasCortadas"]);
		jQuery(gridP23.nameJQuery).jqGrid('showCol', ["incPrevisionVenta"]);
		jQuery(gridP23.nameJQuery).jqGrid('showCol', ["smEstatico"]);
		jQuery(gridP23.nameJQuery).jqGrid('showCol', ["facing"]);
		jQuery(gridP23.nameJQuery).jqGrid('showCol', ["origenPedido"]);
		$('.p23_etiqValorCampo').width("40%");
		$('#p23_divArea').width("27%");
		$('#p23_divMapa').width("30%");
		$('#p23_divMapa').show();
	}else{
		jQuery(gridP23.nameJQuery).jqGrid('hideCol', ["motivoPedido"]);
		jQuery(gridP23.nameJQuery).jqGrid('hideCol', ["cajasCortadas"]);
		jQuery(gridP23.nameJQuery).jqGrid('hideCol', ["incPrevisionVenta"]);
		jQuery(gridP23.nameJQuery).jqGrid('hideCol', ["smEstatico"]);
		jQuery(gridP23.nameJQuery).jqGrid('hideCol', ["facing"]);
		jQuery(gridP23.nameJQuery).jqGrid('hideCol', ["origenPedido"]);
		$('.p23_etiqValorCampo').width("48%");
		$('#p23_divMapa').hide();
	}
	
	//redimensionar jqGrid.
	if (esCentroVegalsa()){
		$(gridP23.nameJQuery).jqGrid('setGridWidth', $("#p23_popupCantidadesConfirmadas").width());
	}else{
		$(gridP23.nameJQuery).jqGrid('setGridWidth', $("#p23_popupCantidadesConfirmadas").width(), true);
	}
}
