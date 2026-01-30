var p22BotonAceptar = null;
var gridP22=null;

$(document).ready(function(){
	loadP22(locale);
});

function initializeScreenP22PopupCantidadesNoServidas(){
	
	$( "#p22_popupCantidadesNoServidas" ).dialog({
        autoOpen: false,
        height: 650,
        width: 1000,
        modal: true,
        resizable: false,
        buttons:[{
            text: p22BotonAceptar,
            click: function() {
                      $(this).dialog('close');
                   }
        }],
		open: function() {
			$('.ui-dialog-titlebar-close').on('mousedown', function(){
				$("#p22_popupCantidadesNoServidas").dialog('close');
			});
		}
    });
	
	$(window).bind('resize', function() {
	    $("#p22_popupCantidadesNoServidas").dialog("option", "position", "center");
	});
	
}

function reset_p22(){
	$("#p22_lbl_fechaPedidoVal").text("");
    $("#p22_fld_fechaPedidoVal").val("");
  	$("#p22_lbl_areaVal").text("");
  	$("#p22_lbl_mapaVal").text("");
  	$("#p22_fld_areaVal").val("");
  	$("#p22_lbl_mapaVal").val("");
  	$("#p22_lbl_seccionVal").text("");
  	$("#p22_fld_seccionVal").val("");
  	$("#p22_lbl_categoriaVal").text("");
  	$("#p22_fld_categoriaVal").val("");
  	
	$("#p22_lbl_color").text("");
	$("#p22_lbl_talla").text("");
	$("#p22_lbl_modeloProveedor").text("");
  	
	$("#p22_lbl_totalReferenciasNoServidasVal").text("");
	$("#p22_lbl_totalCajasNoServidasVal").text("");

  	$(gridP22.nameJQuery).jqGrid('clearGridData');
  	$(gridP22.nameJQuery).jqGrid('setGridParam', { rowNum: 10 });
  	$("#p22_popupCantidadesNoServidas .ui-pg-selbox").val(10);
  	//$(gridP22.nameJQuery).jqGrid('setGridWidth', $("#p22_AreaCabecera").width(), true);
}

function events_p22_btn_exportExcel(){
	$("#p22_btn_exportExcel").click(function () {
		exportExcel22();
	});
}

function loadP22(locale){
	gridP22 = new GridP22CantNoServ(locale);
	if (esCentroVegalsa()){
		$(gridP22.nameJQuery).jqGrid('setGridWidth', $("#p22_AreaCabecera").width());
	}else{
		$(gridP22.nameJQuery).jqGrid('setGridWidth', $("#p22_AreaCabecera").width(),true);
	}
	var jqxhr = $.getJSON(gridP22.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				gridP22.colNames = data.ColNames;
				gridP22.title = data.GridTitle;
				gridP22.emptyRecords= data.emptyRecords;
				p22BotonAceptar = data.p22BotonAceptar;
				load_gridP22CantNoServMock(gridP22);
				initializeScreenP22PopupCantidadesNoServidas();
				events_p22_btn_exportExcel();
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
        });
}

function load_gridP22CantNoServMock(grid) {

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
			grid.headerHeight("p22_gridHeader");				
					
			},
			loadComplete : function(data) {
				grid.actualPage = data.page;
				grid.localData = data;
				grid.sortIndex = grid.sortIndex;
				grid.sortOrder = grid.sortOrder;	
				$("#p22_AreaReferencias .loading").css("display", "none");	
			},
			resizeStop: function () {
				grid.saveColumnState.call($(this),grid.myColumnsState);
				$(grid.nameJQuery).jqGrid('setGridWidth', $("#p22_AreaCabecera").width(), false);
            },
			onPaging : function(postdata) {			
				alreadySorted = false;
				grid.sortIndex = grid.sortIndex;
				grid.sortOrder = grid.sortOrder;
				grid.saveColumnState.call($(this), this.p.remapColumns);
				//$(grid.nameJQuery).jqGrid('setGridWidth', $("#p22_AreaCabecera").width(), true);
				reloadData_gridP22CantNoServ(grid);
				return 'stop';
			},
			onSelectRow: function(id){

			},
			onSortCol : function (index, columnIndex, sortOrder){
				grid.sortIndex = index;
				grid.sortOrder = sortOrder;
				grid.saveColumnState.call($(this), this.p.remapColumns);
				//$(grid.nameJQuery).jqGrid('setGridWidth', $("#p22_AreaCabecera").width(), true);
				reloadData_gridP22CantNoServ(grid);
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

function reloadDataP22(seguimientoMiPedido){

	reset_p22();
	$( "#p22_popupCantidadesNoServidas" ).dialog( "open" );
	reloadData_gridP22CantNoServ(gridP22,seguimientoMiPedido);
	$("#p22_lbl_fechaPedidoVal").text(seguimientoMiPedido.fechaPedidoPantalla);
	$("#p22_fld_fechaPedidoVal").val(seguimientoMiPedido.fechaPedidoDDMMYYYY);
	$("#p22_fld_areaVal").val(seguimientoMiPedido.codArea);
	$("#p22_fld_seccionVal").val(seguimientoMiPedido.codSeccion);
	$("#p22_fld_mapaVal").val(seguimientoMiPedido.mapa);
	$("#p22_fld_categoriaVal").val(seguimientoMiPedido.codCategoria);
	$("#p22_lbl_color").text("");
	$("#p22_lbl_talla").text("");
	$("#p22_lbl_modeloProveedor").text("");
	if (esCentroVegalsa()){
		$('#p22_lbl_fechaReposicion').show();
		$('#p22_lbl_fechaPedido').hide();
	}else{
		$('#p22_lbl_fechaReposicion').hide();
		$('#p22_lbl_fechaPedido').show();
	}
	//comprobamos si pertenece a textil y formateamos las columnas a mostrar.
	hideShowColumns22($("#p22_fld_areaVal").val());
	if (seguimientoMiPedido.mapa!=null && seguimientoMiPedido.mapa!="" && seguimientoMiPedido.mapa!="0"){
		$("#p22_lbl_mapaVal").text(seguimientoMiPedido.mapa+"-"+seguimientoMiPedido.descrMapa);
	}else if (seguimientoMiPedido.mapa=="0"){
		$("#p22_lbl_mapaVal").text("SIN MAPA");
	}else{
		$("#p22_lbl_mapaVal").text("");
	}
	if (seguimientoMiPedido.codArea!=null && seguimientoMiPedido.codArea!=""){
		$("#p22_lbl_areaVal").text(seguimientoMiPedido.codArea+"-"+seguimientoMiPedido.descArea);
	}else{
		$("#p22_lbl_areaVal").text("");
	}
	if (seguimientoMiPedido.codSeccion!=null && seguimientoMiPedido.codSeccion!=""){
		$("#p22_lbl_seccionVal").text(seguimientoMiPedido.codSeccion+"-"+seguimientoMiPedido.descSeccion);
	}else{
		$("#p22_lbl_seccionVal").text("");
	}
	if (seguimientoMiPedido.codCategoria!=null && seguimientoMiPedido.codCategoria!=""){
		$("#p22_lbl_categoriaVal").text(seguimientoMiPedido.codCategoria+"-"+seguimientoMiPedido.descCategoria);
	}else{
		$("#p22_lbl_categoriaVal").text("");
	}
	
	reloadData_cabeceraP22(seguimientoMiPedido);
}

function reloadData_gridP22CantNoServ(grid,seguimientoMiPedidoParam) {
	
	if (grid.firstLoad) {
		if (esCentroVegalsa()){
			$(grid.nameJQuery).jqGrid('setGridWidth', $("#p22_AreaCabecera").width());
		}else{
			$(grid.nameJQuery).jqGrid('setGridWidth', $("#p22_AreaCabecera").width(),true);
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
		seguimientoMiPedido = new SeguimientoMiPedido($("#centerId").val() , $("#p22_fld_fechaPedidoVal").val(), '', $("#p22_fld_areaVal").val(), '', $("#p22_fld_seccionVal").val(), '', $("#p22_fld_categoriaVal").val(), '', '', $("#p20_RefEroski").val(),$("#p22_fld_mapaVal").val(),'');
	}
	
	var objJson = $.toJSON(seguimientoMiPedido.prepareToJsonObject());
	$("#p22_AreaReferencias .loading").css("display", "block");
	$.ajax({
		type : 'POST',			
		url : './misPedidos/cantidadesNoServidas/loadDataGrid.do?page='+grid.getActualPage()+'&max='+grid.getRowNumPerPage()+'&index='+grid.getSortIndex()+'&sortorder='+grid.getSortOrder(),
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		
			$(grid.nameJQuery)[0].addJSONData(data);
			grid.actualPage = data.page;
			grid.localData = data;
			//$(grid.nameJQuery).jqGrid('setGridWidth', $("#p22_AreaCabecera").width(), true);
			$('#p22_popupCantidadesNoServidas').animate({scrollTop: '0px'}, 0);
			$("#p22_AreaReferencias .loading").css("display", "none");	
		},
		error : function (xhr, status, error){
			//$(grid.nameJQuery).jqGrid('setGridWidth', $("#p22_AreaCabecera").width(), true);
			$('#p22_popupCantidadesNoServidas').animate({scrollTop: '0px'}, 0);
			$("#p22_AreaReferencias .loading").css("display", "none");	
			handleError(xhr, status, error, locale);				
        }			
	});		
}

function reloadData_cabeceraP22(seguimientoMiPedidoParam) {

	var seguimientoMiPedido = null;
	if (seguimientoMiPedidoParam != null){
		seguimientoMiPedido = new SeguimientoMiPedido($("#centerId").val() , seguimientoMiPedidoParam.fechaPedidoDDMMYYYY, '', seguimientoMiPedidoParam.codArea, '', seguimientoMiPedidoParam.codSeccion, '', seguimientoMiPedidoParam.codCategoria, '', '', seguimientoMiPedidoParam.codArt,seguimientoMiPedidoParam.mapa, seguimientoMiPedidoParam.descrMapa);
	}else{
		seguimientoMiPedido = new SeguimientoMiPedido($("#centerId").val() , $("#p22_fld_fechaPedidoVal").val(), '', $("#p22_fld_areaVal").val(), '', $("#p22_fld_seccionVal").val(), '', $("#p22_fld_categoriaVal").val(), '', '', $("#p20_fld_referencia_tmp").val(),$("#p22_fld_mapaVal").val(),'');
	}
	
	var objJson = $.toJSON(seguimientoMiPedido.prepareToJsonObject());
	$.ajax({
		type : 'POST',			
		url : './misPedidos/cantidadesNoServidas/loadDataCabecera.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		
			if (data.nsrTotalRefNoServidas!=null && data.nsrTotalRefNoServidas!=""){
				$("#p22_lbl_totalReferenciasNoServidasVal").text(data.nsrTotalRefNoServidas);
			}else{
				$("#p22_lbl_totalReferenciasNoServidasVal").text("0");
			}
			if (data.nsrTotalCajasNoServidas!=null && data.nsrTotalCajasNoServidas!=""){
				$("#p22_lbl_totalCajasNoServidasVal").text($.formatNumber(data.nsrTotalCajasNoServidas,{format:"0.0",locale:"es"}));
			}else{
				$("#p22_lbl_totalCajasNoServidasVal").text("0,0");
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
        }			
	});		
}

/*Clase de constantes para el GRID de cantidades pedidas*/
function GridP22CantNoServ (locale){
	// Atributos
	this.name = "gridP22CantNoServ"; 
	this.nameJQuery = "#gridP22CantNoServ"; 
	this.i18nJSON = './misumi/resources/p22PopUpCantidadesNoServidas/p22PopUpCantidadesNoServidas_' + locale + '.json';
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
					"name"  : "motivoPedido",
					"index" : "motivoPedido",
					"width" : 200,
					"align" : "left"
				},{
					"name"  : "color",
					"index" : "color", 
					"width" : 100,	
					"align" : "left"						
				},{
					"name"  : "talla",
					"index" : "talla", 
					"width" : 100,	
					"align" : "left"						
				},{
					"name"  : "modeloProveedor",
					"index" : "modeloProveedor", 
					"width" : 150,	
					"align" : "left"						
				},{
					"name"  : "cajasNoServidas",
					"index" : "cajasNoServidas",
					"width" : 80,
					"formatter":camposUnDecFormatNumber,
					"align" : "center"
				},{
					"name"  : "motivo",
					"index" : "motivo",
					"width" : 200,
					"align" : "left"
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
	this.pagerName = "pagerGridP22"; //"pagerP16Movimientos"; 
	this.pagerNameJQuery = "#pagerGridP22"; //"#pagerP16Movimientos";
	this.title = null; //Está en el json
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null; //Está en el json
	this.rownumbers = true;
	this.myColumnStateName = 'gridP22CantNoServ.colState';
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
	};
	
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

function camposUnDecFormatNumber(cellValue, opts, rData) {
	//Actualizamos el formateo de los campos a tres decimales
	return $.formatNumber(cellValue,{format:"0.0",locale:"es"});
}

function exportExcel22(){
	
	var colModel = $(gridP22.nameJQuery).jqGrid("getGridParam", "colModel");
    var colNames = $(gridP22.nameJQuery).jqGrid("getGridParam", "colNames");
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
    var form = "<form name='csvexportform' action='misPedidos/cantidadesNoServidas/exportGrid.do'  accept-charset='ISO-8859-1' method='get'>";
	form = form + "<input type='hidden' name='headers' value='"+myColumnsNames+"'>";		
	form = form + "<input type='hidden' name='model' value='"+myColumns+"'>";

	if ($("#centerId").val()!="null" && $("#centerId").val()!=null ){
		form = form + "<input type='hidden' name='codCentro' value='"+$("#centerId").val()+"'>";
	}	
	if ($("#p22_fld_fechaPedidoVal").val()!="null" && $("#p22_fld_fechaPedidoVal").val()!=null ){
		form = form + "<input type='hidden' name='fechaPedidoDDMMYYYY' value='"+$("#p22_fld_fechaPedidoVal").val()+"'>";
	}	
	if ($("#p22_fld_areaVal").val()!="null" && $("#p22_fld_areaVal").val()!=null ){
		form = form + "<input type='hidden' name='codArea' value='"+$("#p22_fld_areaVal").val()+"'>";
	}	
	if ($("#p22_fld_seccionVal").val()!="null" && $("#p22_fld_seccionVal").val()!=null ){
		form = form + "<input type='hidden' name='codSeccion' value='"+$("#p22_fld_seccionVal").val()+"'>";
	}
	if ($("#p22_fld_categoriaVal").val()!="null" && $("#p22_fld_categoriaVal").val()!=null ){
		form = form + "<input type='hidden' name='codCategoria' value='"+$("#p22_fld_categoriaVal").val()+"'>";
	}
	if ($("#p20_fld_referencia").val()!="null" && $("#p20_fld_referencia").val()!=null ){
		form = form + "<input type='hidden' name='codArt' value='"+$("#p20_RefEroski").val()+"'>";
	}
	// MISUMI-299
	var radioValue = $("input[name='p20_rad_tipoFiltro']:checked").val();
	if (radioValue == 3){
		form = form + "<input type='hidden' name='codMapa' value='"+$("#p22_fld_mapaVal").val()+"'>";
		var descMapa = $("#p22_lbl_mapaVal").text();
		form = form + "<input type='hidden' name='descMapa' value='"+descMapa+"'>";
	}

	form = form + "</form><script>document.csvexportform.submit();</script>";
	Show_Popup(form);	 

}

function hideShowColumns22(val){

	if(val==3){
		//Textil
		jQuery(gridP22.nameJQuery).jqGrid('showCol', ["referencia","descripcion","color","talla","modeloProveedor","cajasNoServidas","motivo"]);
	}else{
		jQuery(gridP22.nameJQuery).jqGrid('hideCol', ["color","talla","modeloProveedor"]);
	}
	
	var radioValue = $("input[name='p20_rad_tipoFiltro']:checked").val();
	// MISUMI-350: MISUMI-JAVA VEGALSA SEGUIMIENTO DE PEDIDOS añadir el motivo de pedido
	if (esCentroVegalsa()){
		jQuery(gridP22.nameJQuery).jqGrid('showCol', ["motivoPedido"]);
		jQuery(gridP22.nameJQuery).jqGrid('showCol', ["cajasCortadas"]);
		jQuery(gridP22.nameJQuery).jqGrid('showCol', ["incPrevisionVenta"]);
		jQuery(gridP22.nameJQuery).jqGrid('showCol', ["smEstatico"]);
		jQuery(gridP22.nameJQuery).jqGrid('showCol', ["facing"]);
		jQuery(gridP22.nameJQuery).jqGrid('showCol', ["origenPedido"]);
		$('.p22_etiqValorCampo').width("40%");
		$('#p22_divArea').width("27%");
		$('#p22_divMapa').width("30%");
		$('#p22_divMapa').show();
	}else{
		jQuery(gridP22.nameJQuery).jqGrid('hideCol', ["motivoPedido"]);
		jQuery(gridP22.nameJQuery).jqGrid('hideCol', ["cajasCortadas"]);
		jQuery(gridP22.nameJQuery).jqGrid('hideCol', ["incPrevisionVenta"]);
		jQuery(gridP22.nameJQuery).jqGrid('hideCol', ["smEstatico"]);
		jQuery(gridP22.nameJQuery).jqGrid('hideCol', ["facing"]);
		jQuery(gridP22.nameJQuery).jqGrid('hideCol', ["origenPedido"]);
		$('.p22_etiqValorCampo').width("48%");
		$('#p22_divMapa').hide();
	}
	
	//redimensionar jqGrid.
	if (esCentroVegalsa()){
		jQuery(gridP22.nameJQuery).jqGrid('setGridWidth', $("#p22_popupCantidadesNoServidas").width());
	}else{
		jQuery(gridP22.nameJQuery).jqGrid('setGridWidth', $("#p22_popupCantidadesNoServidas").width(),true);
	}
}
