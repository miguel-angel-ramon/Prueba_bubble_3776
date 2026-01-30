var gridP47EncargosCliente=null;
var p47Seleccionados = new Map; //Contiene los cambios realizados en la pantalla actual
var p47SeleccionadosTotal = new Array(); //Contiene el total de registros visualizados en algún momento
var p47FormatoPesoDesde=null;
var p47FormatoPesoDesdePesoHasta=null;
var p47emptyListaSeleccionados = null;
var p47iconoModificado=null;
var p47errorModificacion=null;
var p47errorBorrado=null;
var p47mensajeConfirmadoNsr=null;
var p47mensajeNsr=null;
var p47LiteralEstadoPendiente=null;
// Inicio MISUMI-298
//var p47LiteralEstadoComprometido=null;
var p47LiteralEstadoEnTramite=null;
//FIN MISUMI-298
var p47LiteralEstadoNoServido=null;
var p47LiteralEstadoConfirmado=null;
var p47LiteralEstadoConf=null;
var p47LiteralEstadoNoServ=null;
var p47rowId = null;
var p47subgridDivId = null;


var tableFilter = null;

function initializeP47(){
		loadP47PedidoAdicionalEC(locale);
}

function resetDatosP47(){
	$('#gridP47EncargosCliente').jqGrid('clearGridData');
}

function reloadEncargosCliente() {
	$("#p40_pestanaEncargosClienteCargada").val("S");
	reloadDataP47PedidoAdicionalEC('N');

}

function p47SetHeadersTitles(data){
	
   	var colModel = $(gridP47EncargosCliente.nameJQuery).jqGrid("getGridParam", "colModel");
    $.each(colModel, function(i) {
    	if (colModel[i].name!="rn"){
	    	$("#jqgh_gridP47EncargosCliente_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
    	}
     });
}

function loadP47PedidoAdicionalEC(locale){
	gridP47EncargosCliente = new GridP47EncargosCliente(locale);
	var jqxhr = $.getJSON(gridP47EncargosCliente.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				gridP47EncargosCliente.colNames = data.p47PedidoAdicionalECColNames;
				gridP47EncargosCliente.subgridColNames = data.p47PedidoAdicionalECColNames;
				gridP47EncargosCliente.title = data.p47PedidoAdicionalECGridTitle;
				gridP47EncargosCliente.emptyRecords= data.emptyRecords;
				tableFilter=data.tableFilter;
				p47FormatoPesoDesde = data.p47FormatoPesoDesde;
				p47FormatoPesoDesdePesoHasta = data.p47FormatoPesoDesdePesoHasta;
				p47emptyListaSeleccionados = data.emptyListaSeleccionados;
				p47iconoModificado=data.iconoModificado;
				p47errorModificacion=data.errorModificacion;
				p47errorBorrado=data.errorBorrado;
				p47mensajeConfirmadoNsr=data.mensajeConfirmadoNsr;
				p47mensajeNsr=data.mensajeNsr;
				p47LiteralEstadoPendiente=data.estadoPendiente;
// Inicio MISUMI-298
//				p47LiteralEstadoComprometido=data.estadoComprometido;
				p47LiteralEstadoEnTramite=data.estadoEnTramite;
// FIN MISUMI-298
				p47LiteralEstadoNoServido=data.estadoNoServido;
				p47LiteralEstadoConfirmado=data.estadoConfirmado;
				p47LiteralEstadoConf=data.estadoConf;
				p47LiteralEstadoNoServ=data.estadoNoServ;
				
				loadP47PedidoAdicionalECMock(gridP47EncargosCliente);
				p47SetHeadersTitles(data);
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
         });
}

function loadP47PedidoAdicionalECMock(gridP47EncargosCliente) {
	$(gridP47EncargosCliente.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		datatype : 'local',
		contentType : 'application/json',
		mtype : 'POST',
		colNames : gridP47EncargosCliente.colNames,
		colModel : gridP47EncargosCliente.cm,
		rowNum : 10,
		rowList : [ 10, 20, 30 ],
		height : "auto",
		autowidth : true,
		width : "auto",
		rownumbers : true,
		pager : gridP47EncargosCliente.pagerNameJQuery,
		viewrecords : true,
		caption : gridP47EncargosCliente.title,
		altclass: "ui-priority-secondary",
		altRows: true, //false, para que el grid no muestre cebrado
		hidegrid : false, //false, para ocultar el boton que colapsa el grid.
		sortable : true,
		multiselect: false,
		subGrid : true,
		subGridUrl: './misumi/resources/p47PedidoAdicionalEC/p47mockPedidoAdicionalEC.json',
	    subGridRowExpanded: function (subgridDivId, rowId) {
	    	reloadDataP47PedidoAdicionalECDetalle(rowId, subgridDivId);
	    },

		index: gridP47EncargosCliente.sortIndex,
		sortname: gridP47EncargosCliente.sortIndex,
		sortorder: gridP47EncargosCliente.sortOrder,
		emptyrecords : gridP47EncargosCliente.emptyRecords,
		gridComplete : function() {
			gridP47EncargosCliente.headerHeight("p47_gridEncargosClienteHeader"); 
		},
		loadComplete : function(data) {
			gridP47EncargosCliente.actualPage = data.page;
			gridP47EncargosCliente.localData = data;
			gridP47EncargosCliente.sortIndex = null;
			gridP47EncargosCliente.sortOrder = null;
			if (gridP47EncargosCliente.firstLoad)
				jQuery(gridP47EncargosCliente.nameJQuery).jqGrid('setGridWidth', $("#p47_AreaEncargosCliente").width(), true);
			
			//Ocultamos la check de seleccionar todos.
			$("#cb_gridP47EncargosCliente").attr("style", "display:none");
		},
		onPaging : function(postdata) {			
			alreadySorted = false;
			gridP47EncargosCliente.sortIndex = null;
			gridP47EncargosCliente.sortOrder = null;
			gridP47EncargosCliente.saveColumnState.call($(this), this.p.remapColumns);
			reloadDataP47PedidoAdicionalEC('S');

			return 'stop';
		},
		beforeSelectRow: function (rowid, e) {
		    var $myGrid = $(this),
		        i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),
		        cm = $myGrid.jqGrid('getGridParam', 'colModel');
	    		if (!(cm[i].name === 'cb') && !(cm[i].name === 'codArtFormlog')  && !(cm[i].name === 'estadoGestadic') && !(cm[i].name === 'subgrid'))
		    	{
	    			var localizador = $("#"+rowid+"_localizadorEC").val();
	    			var nivel = $("#"+rowid+"_nivelEC").val();
	    			if (nivel==1 && localizador!=null && localizador!='' && localizador!=undefined){
	    				p47ModificarEncargo(localizador, "", "");
	    			}
		    	}else if (cm[i].name === 'codArtFormlog'){
		    		var relCompraVenta = $("#"+rowid+"_relCompraVentaEC").val();
		    		if (relCompraVenta == 'S'){
			    		var codArticulo = $("#"+rowid+"_codArticuloEC").val();
		    			p47RefCompraVenta(codArticulo);		    		
		    		}else{
		    			var localizador = $("#"+rowid+"_localizadorEC").val();
		    			var nivel = $("#"+rowid+"_nivelEC").val();
		    			if (nivel==1 && localizador!=null && localizador!='' && localizador!=undefined){
		    				p47ModificarEncargo(localizador, "", "");
		    			}
		    		}
		    	}else if (cm[i].name === 'estadoGestadic'){
		    		var estadoGestadic = $("#"+rowid+"_estadoGestadicEC").val();
		    		if (estadoGestadic == 'PDTE' || estadoGestadic == 'OK' || estadoGestadic == 'KO' || estadoGestadic == 'FIN'){
		    			var localizador = $("#"+rowid+"_localizadorEC").val();
		    			p47GestionAdicional(localizador);		    		
		    		}else{
		    			var localizador = $("#"+rowid+"_localizadorEC").val();
		    			var nivel = $("#"+rowid+"_nivelEC").val();
		    			if (nivel==1 && localizador!=null && localizador!='' && localizador!=undefined){
		    				p47ModificarEncargo(localizador, "", "");
		    			}
		    		}		    		
		    	}
	    		
	    	return (cm[i].name === 'cb');
		},
		resizeStop: function () {
			gridP47EncargosCliente.modificado = true;
			gridP47EncargosCliente.saveColumnState.call($(this),gridP47EncargosCliente.myColumnsState);
			jQuery(gridP47EncargosCliente.nameJQuery).jqGrid('setGridWidth', $("#p47_AreaEncargosCliente").width(), false);
        },
		onSortCol : function (index, columnIndex, sortOrder){
			gridP47EncargosCliente.sortIndex = index;
			gridP47EncargosCliente.sortOrder = sortOrder;
			gridP47EncargosCliente.saveColumnState.call($(this), this.p.remapColumns);
			reloadDataP47PedidoAdicionalEC('S');

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

function reloadDataP47PedidoAdicionalEC(recarga) {
	
	//Obtenemos el array con los registros seleccionados
	p47ObtenerSeleccionados();

	var codArea = null;
	var codSeccion = null;
	if ($("#p40_cmb_seccion").combobox('getValue') != null && $("#p40_cmb_seccion").combobox('getValue') != '')
	{
		valorSeccion = $("#p40_cmb_seccion").combobox('getValue').split('*');
		codArea = valorSeccion[0];
		codSeccion = valorSeccion[1];
	}
	var recordPedidoAdicionalEC = new PedidoAdicionalEC (null, $("#centerId").val(), null, null, null, null, 
			null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, p47SeleccionadosTotal);
	
	if (recarga == 'N')
	{
		//Es la primera vez que cargamos tenemos que descheckear todo lo que pudiese haber quedado checkeado.
		//Reseteamos el array de seleccionados.
		p47DeleteSeleccionados();
	}

	var objJson = $.toJSON(recordPedidoAdicionalEC.preparePedidoAdicionalECToJsonObject());

		$("#AreaResultados .loading").css("display", "block");
		
		$.ajax({
			type : 'POST',
			url : './pedidoAdicionalEncargosCliente/loadDataGridEC.do?page='+gridP47EncargosCliente.getActualPage()+'&max='+gridP47EncargosCliente.getRowNumPerPage()+'&index='+gridP47EncargosCliente.getSortIndex()+'&recarga='+recarga +'&sortorder='+gridP47EncargosCliente.getSortOrder(),

			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {		
				$(gridP47EncargosCliente.nameJQuery)[0].addJSONData(data.datos);
				gridP47EncargosCliente.actualPage = data.datos.page;
				gridP47EncargosCliente.localData = data.datos;
				
				if (data != null && data != '')
				{
					if (data.codError == 0){
						$("#p40AreaErrores").attr("style", "display:none");
						$("div#p40_AreaPestanas").attr("style", "display:block");
						
						p47SeleccionadosTotal = data.listadoSeleccionados;
						
						$("#AreaResultados .loading").css("display", "none");	
						if (data.records == 0){
							createAlert(replaceSpecialCharacters(gridP47EncargosCliente.emptyRecords), "ERROR");
						} 
						
						p47ControlesPantalla();
					}else{
						$("#AreaResultados .loading").css("display", "none");
						$("#p40_erroresTexto").text(data.descError);
						$("#p40AreaErrores").attr("style", "display:block");
						$("#p40_AreaPestanas").attr("style", "display:none");
					}
						
				}else{
					$("#p40AreaErrores").attr("style", "display:none");
				}
			},
			error : function (xhr, status, error){
				$("#AreaResultados .loading").css("display", "none");	
				handleError(xhr, status, error, locale);				
	        }			
		});		
}

function reloadDataP47PedidoAdicionalECDetalle(rowId, subgridDivId) {
	
	//Obtenemos el array con los registros seleccionados
	p47ObtenerSeleccionados();

	var codPedidoInterno = rowId.substr(rowId.indexOf("_")+1);
			
	var recordPedidoAdicionalEC = new PedidoAdicionalEC (null, $("#centerId").val(), null, null, null, null, 
			null, null, null, null, null, null, null, null, 
			null, null, null, null, codPedidoInterno, null, null, p47SeleccionadosTotal);
	
	//DesCheckeamos lo seleccionado al recargar la lista.
	jQuery(gridP47EncargosCliente.nameJQuery).jqGrid('resetSelection');

	var objJson = $.toJSON(recordPedidoAdicionalEC.preparePedidoAdicionalECToJsonObject());

		$("#AreaResultados .loading").css("display", "block");
		
		$.ajax({
			type : 'POST',
			url : './pedidoAdicionalEncargosCliente/loadDataGridECDetalle.do',
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {	
				var subgridTableId = subgridDivId + "_t";
		        $("#" + subgridDivId).html("<table id='" + subgridTableId + "'></table>");
		        $("#" + subgridTableId).jqGrid({
		            datatype: 'local',
		            colNames : gridP47EncargosCliente.subgridColNames,
		    		colModel : gridP47EncargosCliente.subgridCm,
		    		multiselect: true,
		    		altclass: "ui-priority-secondary",
		    		altRows: false,
		    		rowNum: data.datos.records,
		    		sortable : true,
		    		height: '100%',
		    		width : "auto",
		    		beforeSelectRow: function (rowid, e) {
		    		    var $myGrid = $(this),
		    		        i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),
		    		        cm = $myGrid.jqGrid('getGridParam', 'colModel');
		    	    		if (!(cm[i].name === 'cb') && !(cm[i].name === 'codArtFormlog') && !(cm[i].name === 'estadoGestadic'))
		    		    	{
		    	    			var localizador = $("#"+rowid+"_localizadorEC").val();
		    	    			if (localizador!=null && localizador!='' && localizador!=undefined){
		    	    				p47ModificarEncargo(localizador, rowId, subgridDivId);
		    	    			}
		    		    	}else if (cm[i].name === 'codArtFormlog'){
		    		    		var relCompraVenta = $("#"+rowid+"_relCompraVentaEC").val();
		    		    		if (relCompraVenta == 'S'){
		    			    		var codArticulo = $("#"+rowid+"_codArticuloEC").val();
		    		    			p47RefCompraVenta(codArticulo);
		    		    	    	return false;
		    		    		}else{
		    		    			var localizador = $("#"+rowid+"_localizadorEC").val();
			    	    			if (localizador!=null && localizador!='' && localizador!=undefined){
			    	    				p47ModificarEncargo(localizador, rowId, subgridDivId);
			    	    			}
		    		    		}
		    		    	}else if (cm[i].name === 'estadoGestadic'){
		    		    		var estadoGestadic = $("#"+rowid+"_estadoGestadicEC").val();
		    		    		if (estadoGestadic == 'PDTE' || estadoGestadic == 'OK' || estadoGestadic == 'KO' || estadoGestadic == 'FIN'){
		    		    			var localizador = $("#"+rowid+"_localizadorEC").val();
		    		    			p47GestionAdicional(localizador);	
		    		    			return false;
		    		    		}else{
		    		    			var localizador = $("#"+rowid+"_localizadorEC").val();
		    		    			var nivel = $("#"+rowid+"_nivelEC").val();
		    		    			if (nivel==1 && localizador!=null && localizador!='' && localizador!=undefined){
		    		    				p47ModificarEncargo(localizador, "", "");
		    		    			}
		    		    		}		    		
		    		    	}
		    	    		return (cm[i].name === 'cb');
		    		}
		        });
		        
		        $("#" + subgridTableId)[0].addJSONData(data.datos);
		        $("#" + subgridTableId).actualPage = data.datos.page;
		        $("#" + subgridTableId).localData = data.datos;
				
		        $("#" + subgridTableId).closest("div.ui-jqgrid-view")
	            .children("div.ui-jqgrid-hdiv")
	            .hide();
		        
				if (data != null && data != '')
				{
					p47SeleccionadosTotal = data.listadoSeleccionados;
				}
		        
		        p47ControlesPantallaDetalle(subgridTableId);
			},
			error : function (xhr, status, error){
				$("#AreaResultados .loading").css("display", "none");	
				handleError(xhr, status, error, locale);				
	        }			
		});		
}

function p47RefCompraVenta(codArticulo){
	//Abrimos el popup
	$( "#p63_popupRefCompraVenta" ).dialog( "open" );
	reloadDataP63(codArticulo); 
}

function p47GestionAdicional(localizador){
	//Abrimos el popup
	reloadDataP67(localizador); 
}

/*Clase de constantes para el GRID de Pedido adicional*/
function GridP47EncargosCliente (locale){
	// Atributos
	this.name = "gridP47EncargosCliente"; 
	this.nameJQuery = "#gridP47EncargosCliente"; 
	this.i18nJSON = './misumi/resources/p47PedidoAdicionalEC/p47pedidoAdicionalEC_' + locale + '.json';
	this.colNames = null;
	
	this.cm = [ {
			"name" : "estado",
			"index":"estado", 
			"formatter": p47ImageFormatMessage,
			"fixed":true,
			"width" : 101,
			"sortable" : true
		},{		
			"name"  : "codArtFormlog",
			"index" : "codArtFormlog",
			"formatter" : p47FormateoReferencia,
			"fixed":true,
			"width" : 58,
			"sortable" : true
		},{
			"name"  : "descriptionArtGrid",
			"index" : "descriptionArtGrid",
			"formatter" : p47FormateoDenominacion,
			"fixed":true,
			"width" : 173,
			"sortable" : true
		},{
			"name"  : "fechaVenta",
			"index" : "fechaVenta",
			"formatter" : p47FormateoDate,
			"fixed":true,
			"width" : 52,
			"sortable" : true
		},{
			"name"  : "cantEncargo",
			"index" : "cantEncargo",
			"formatter" : p47FormateoCantEncargo,
			"fixed":true,
			"width" : 34,
			"sortable" : true
		},{
			"name"  : "cantFinalCompra",
			"index" : "cantFinalCompra", 
			"formatter" : p47FormateoCantFinalCompra,
			"fixed":true,
			"width" : 34,
			"sortable" : true
		},{
			"name"  : "cantServido",
			"index" : "cantServido", 
			"formatter" : p47FormateoCantServido,
			"fixed":true,
			"width" : 34,
			"sortable" : true
		},{
			"name"  : "cantNoServido",
			"index" : "cantNoServido", 
			"formatter" : p47FormateoCantNoServido,
			"fixed":true,
			"width" : 34,
			"sortable" : true
		},{
			"name"  : "pesoDesde",
			"index" : "pesoDesde", 
			"formatter" : p47FormateoPesoDesde,
			"fixed":true,
			"width" : 123,
			"sortable" : true
		},{
			"name"  : "nombreCliente",
			"index" : "nombreCliente", 
			"formatter" : p47FormateoNombreCliente,
			"fixed":true,
			"width" : 102,
			"sortable" : true
		},{
			"name"  : "estadoGestadic",
			"index" : "estadoGestadic", 
			"formatter" : p47FormateoEstadoGestadic,
			"fixed":true,
			"width" : 39,
			"sortable" : true
   		},{
			"name"  : "localizador",
			"index" : "localizador", 
			"formatter" : p47FormateoLocalizador,
			"fixed":true,
			"width" : 48,
			"sortable" : true
		}
	];
	this.subgridColNames = null;
	this.subgridCm = [ {
			"name" : "estado",
			"index":"estado",
			"formatter": p47ImageFormatMessageDetalle,
			"fixed":true,
			"width" : 74,
			"sortable" : true
		},{		
			"name"  : "codArtFormlog",
			"index" : "codArtFormlog",
			"formatter" : p47FormateoReferenciaDetalle,
			"fixed":true,
			"width" : 58,
			"sortable" : true
		},{
			"name"  : "denomArticulo",
			"index" : "denomArticulo",
			"formatter" : p47FormateoDenominacionDetalle,
			"fixed":true,
			"width" : 173,
			"sortable" : true
		},{
			"name"  : "fechaVenta",
			"index" : "fechaVenta",
			"formatter" : p47FormateoDateDetalle,
			"fixed":true,
			"width" : 52,
			"sortable" : true
		},{
			"name"  : "cantEncargo",
			"index" : "cantEncargo",
			"formatter" : p47FormateoCantEncargoDetalle,
			"fixed":true,
			"width" : 34,
			"sortable" : true
		},{
			"name"  : "cantFinalCompra",
			"index" : "cantFinalCompra", 
			"formatter" : p47FormateoCantFinalCompraDetalle,
			"fixed":true,
			"width" : 34,
			"sortable" : true
		},{
			"name"  : "cantServido",
			"index" : "cantServido", 
			"formatter" : p47FormateoCantServidoDetalle,
			"fixed":true,
			"width" : 34,
			"sortable" : true
		},{
			"name"  : "cantNoServido",
			"index" : "cantNoServido", 
			"formatter" : p47FormateoCantNoServidoDetalle,
			"fixed":true,
			"width" : 34,
			"sortable" : true
		},{
			"name"  : "pesoDesde",
			"index" : "pesoDesde", 
			"formatter" : p47FormateoPesoDesdeDetalle,
			"fixed":true,
			"width" : 123,
			"sortable" : true
		},{
			"name"  : "nombreCliente",
			"index" : "nombreCliente", 
			"formatter" : p47FormateoNombreClienteDetalle,
			"fixed":true,
			"width" : 102,
			"sortable" : true
		},{
			"name"  : "estadoGestadic",
			"index" : "estadoGestadic", 
			"formatter" : p47FormateoEstadoGestadicDetalle,
			"fixed":true,
			"width" : 39,
			"sortable" : true
			},{
			"name"  : "localizador",
			"index" : "localizador", 
			"fixed":true,
			"width" : 47,
			"sortable" : true
		}
	];

	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP47EncargosCliente"; 
	this.pagerNameJQuery = "#pagerP47EncargosCliente";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;
	this.myColumnStateName = 'gridP47EncargosCliente.colState';
    this.myColumnsState = null;
    this.isColState = null;
    this.firstLoad = true;
    this.modificado = false;
	this.titleVentaForzada = null;
	this.titleModifPorAjuste = null;
	this.titleModifPorRegularizacion = null;

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
        var colModel =jQuery(gridP47EncargosCliente.nameJQuery).jqGrid('getGridParam', 'colModel'); 
        var i;
        var l = colModel.length;
        var colItem; 
        var cmName;
        var postData = jQuery(gridP47EncargosCliente.nameJQuery).jqGrid('getGridParam', 'postData');
        var columnsState = {
                search: jQuery(gridP47EncargosCliente.nameJQuery).jqGrid('getGridParam', 'search'),
                page: jQuery(gridP47EncargosCliente.nameJQuery).jqGrid('getGridParam', 'page'),
                sortname: jQuery(gridP47EncargosCliente.nameJQuery).jqGrid('getGridParam', 'sortname'),
                sortorder: jQuery(gridP47EncargosCliente.nameJQuery).jqGrid('getGridParam', 'sortorder'),
                permutation: perm,
                colStates: {}
            };
         var colStates = columnsState.colStates;

        if ((typeof(postData) !== 'undefined') && (typeof (postData.filters) !== 'undefined')) {
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
            window.localStorage.setItem(gridP47EncargosCliente.myColumnStateName, JSON.stringify(columnsState));
        }
    }

    this.restoreColumnState = function (colModel) {
        var colItem, i, l = colModel.length, colStates, cmName;
         var columnsState = this.getObjectFromLocalStorage(this.myColumnStateName);
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
	
	this.clearGrid = function clearGrid() {
		$(this.nameJQuery).jqGrid('GridUnload');
	}
	
	this.headerHeight = function headerHeight(cssClass) {
		var colModel = $(this.nameJQuery).jqGrid('getGridParam','colModel');
		var colNames = $(this.nameJQuery).jqGrid('getGridParam','colNames');
		for (i = 0; i < colNames.length; i++){
			$("#gridP47EncargosCliente").setLabel(colModel[i].name, colNames[i], cssClass);
		}
	}
}

function p47ImageFormatMessage(cellValue, opts, rData) {
	
	var imagen = "";
	var mostrarError = "none;";
	var descError = "";
	var mostrarModificado = "none;";
	
	//Controlamos los posibles errores que me lleguen para pintar el icono correspondiente.
	if (parseFloat(rData['codigoError']) == '1')
	{
		//Pintamos el icono de que ha ocurrido un error con la descripción de borrado
		mostrarError = "block;";
		if (rData['descripcionError'] != '')
		{
			descError = rData['descripcionError'];
		}
		else
		{
			descError = replaceSpecialCharacters(p47errorBorrado);
		}
	}
	else if (parseFloat(rData['codigoError']) == '2')
	{
		//Pintamos el icono de que ha ocurrido un error con la descripción de modificación
		mostrarError = "block;";
		if (rData['descripcionError'] != '')
		{
			descError = rData['descripcionError'];
		}
		else
		{
			descError = replaceSpecialCharacters(p47errorModificacion);
		}
	}
	else if (parseFloat(rData['codigoError']) == '8')
	{
		//Pintamos el icono de que se ha guardado, por que se ha realizado la modificación correctamente
		mostrarModificado = "block;";
	}else{
		//Añadimos el literal  en caso de que no se muestre ningún icono 
		if (cellValue == 'PEND' || cellValue == 'PENDIENTE'){
			imagen += "<span class='p47_estadoPendiente'>" + p47LiteralEstadoPendiente + "</span>";
// Inicio MISUMI-298
//		}else if (cellValue == 'EN CUR' || cellValue == 'COMPROMETIDO'){
//			imagen += "<span class='p47_estadoEnCurso'>" + p47LiteralEstadoComprometido + "</span>";
		}else if (cellValue == 'EN CUR' || cellValue == 'EN TRAMITE'){
			imagen += "<span class='p47_estadoEnCurso'>" + p47LiteralEstadoEnTramite + "</span>";
// FIN MISUMI-298
		}else if (cellValue == 'CONF' || cellValue == 'CONFIRMADO'){
			imagen += "<span class='p47_estadoConfirmado'>" +  p47LiteralEstadoConfirmado + "</span>";
		}else if (cellValue == 'CONF/NSR' || cellValue == 'CONFIR/NO SERV'){
			imagen += "<span class='p47_estadoConfirmado'>" + p47LiteralEstadoConf + "</span>/<span class='p47_estadoNoServido'>" + p47LiteralEstadoNoServ + "</span>";
		}else if (cellValue == 'NSR' || cellValue == 'NO SERVIDO'){
			imagen += "<span class='p47_estadoNoServido'>" + p47LiteralEstadoNoServido+ "</span>";
		} 
	}
	
	imagen += "<div id='"+opts.rowId+"_divGuardadoEC' align='center' style='display: "+ mostrarModificado + "'><img id='"+opts.rowId+"_imgGuardado' src='./misumi/images/floppy.png' title='"+p47iconoModificado+"'/></div>"; //Modificado
	imagen += "<div id='"+opts.rowId+"_divErrorEC' align='center' style='display: " + mostrarError + "'><img id='"+opts.rowId+"_imgError' src='./misumi/images/dialog-error-24.png' title='"+descError+"'/></div>"; //Error
	
	//Añadimos los valores de la columna referencia ocultos para poder utilizarlos posteriormente.
	var datoCodArticulo = "<input type='hidden' id='"+opts.rowId+"_codArticuloEC' value='"+rData['codArtFormlog']+"'>";
	imagen +=  datoCodArticulo;
	
	//Añadimos los valores de la columna si es modificable ocultos para poder utilizarlos posteriormente.
	var datoModificable = "<input type='hidden' id='"+opts.rowId+"_modificableEC' value='"+rData['flgModificable']+"'>";
	imagen +=  datoModificable;
	
	//Añadimos los valores de la columna si ha cambiado la referencia ocultos para poder utilizarlos posteriormente.
	var datoCambioRef = "<input type='hidden' id='"+opts.rowId+"_cambioRefEC' value='"+rData['cambioRef']+"'>";
	imagen +=  datoCambioRef;
	
	//Añadimos los valores de la columna relCompraVenta ocultos para poder utilizarlos posteriormente.
	var datoRelCompraVenta = "<input type='hidden' id='"+opts.rowId+"_relCompraVentaEC' value='"+rData['relCompraVenta']+"'>";
	imagen +=  datoRelCompraVenta;
	
	//Añadimos los valores de la columna nivel ocultos para poder utilizarlos posteriormente.
	var datoNivel = "<input type='hidden' id='"+opts.rowId+"_nivelEC' value='"+rData['nivel']+"'>";
	imagen +=  datoNivel;
	
	//Añadimos los valores de la columna localizador ocultos para poder utilizarlos posteriormente.
	var datoLocalizador = "<input type='hidden' id='"+opts.rowId+"_localizadorEC' value='"+rData['localizador']+"'>";
	imagen +=  datoLocalizador;
	
	//Añadimos los valores de la columna estadoGestadic ocultos para poder utilizarlos posteriormente.
	var datoEstadoGestadic = "<input type='hidden' id='"+opts.rowId+"_estadoGestadicEC' value='"+rData['estadoGestadic']+"'>";
	imagen +=  datoEstadoGestadic;

	return imagen;
}

function p47FormateoReferencia(cellValue, opts, rData) {
	
	var referenciaFormateada = '';
	if ((rData['faltaRef']!=null && rData['faltaRef']=='S' && rData['cambioRef']!='S') || ((rData['estado']!=null && (rData['estado']=='PEND' || rData['estado']=='PENDIENTE'))) && (rData['nivel']!=null && rData['nivel']!=1)){
		referenciaFormateada = "-----";
	}else{
		referenciaFormateada = cellValue;
		if (rData['cambioRef']!=null && rData['cambioRef']=='S' && rData['relCompraVenta']!=null && rData['relCompraVenta']=='S'){
			referenciaFormateada = "<span class='p47_referenciaCambiadaEnlace'>"+referenciaFormateada+"</span>";
		}else if (rData['cambioRef']!=null && rData['cambioRef']=='S'){
			referenciaFormateada = "<span class='p47_referenciaCambiada'>"+referenciaFormateada+"</span>";
		}else if (rData['relCompraVenta']!=null && rData['relCompraVenta']=='S'){
			referenciaFormateada = "<span class='p47_referenciaEnlace'>"+referenciaFormateada+"</span>";
		}
	}
	return (referenciaFormateada);
}

function p47FormateoDenominacion(cellValue, opts, rData) {
	
	var denominacionFormateada = '';
	if ((rData['estado']!=null && (rData['estado']=='PEND' || rData['estado']=='PENDIENTE')) && (rData['nivel']!=null && rData['nivel']!=1)){
		denominacionFormateada = "-----";
	}else{
		denominacionFormateada = cellValue;
		if (rData['cambioRef']!=null && rData['cambioRef']=='S' && rData['relCompraVenta']!=null && rData['relCompraVenta']=='S'){
			denominacionFormateada = "<span class='p47_referenciaCambiada'>"+denominacionFormateada+"</span>";
		}else if (rData['cambioRef']!=null && rData['cambioRef']=='S'){
			denominacionFormateada = "<span class='p47_referenciaCambiada'>"+denominacionFormateada+"</span>";
		}
	}
	return (denominacionFormateada);
}

function p47FormateoDate(cellValue, opts, rData) {
	
	var fechaFormateada = '';
	var fechaPantalla = '';
	var fechaModificada = false;
	if ((rData['estado']!=null && (rData['estado']=='PEND' || rData['estado']=='PENDIENTE')) && (rData['nivel']!=null && rData['nivel']!=1)){
		fechaFormateada = "-----";
	}else{
		if (rData['fechaVentaModificada']!=null && rData['fechaVentaModificada']!='' && rData['fechaVentaModificada']!=cellValue){
			fechaPantalla = rData['fechaVentaModificada'];
			fechaModificada = true;
		}else{
			fechaPantalla = cellValue;
		}
		if (fechaPantalla != null)
		{
			var diaFecha = parseInt(fechaPantalla.substring(8,10),10);
			var mesFecha = parseInt(fechaPantalla.substring(5,7),10);
			var anyoFecha = parseInt(fechaPantalla.substring(0,4),10);
			
		
			fechaFormateada = $.datepicker.formatDate("D dd-M", new Date(anyoFecha, mesFecha - 1, diaFecha),{
				dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
				dayNames: $.datepicker.regional[ "es" ].dayNames,
				monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
				monthNames: $.datepicker.regional[ "es" ].monthNames
				});
		}
		
		if (fechaModificada){
			fechaFormateada = "<span class='p47_fechaModificada'>"+fechaFormateada+"</span>";
		}
	}
	return(fechaFormateada);
}

function p47FormateoCantEncargo(cellValue, opts, rData) {
	
	var cantidadFormateada = '';
	if(cellValue != null && cellValue != ""){
		cantidadFormateada = $.formatNumber(cellValue,{format:"0.##",locale:"es"});
		if (rData['estado']!=null && (rData['estado']=='PEND' || rData['estado']=='PENDIENTE'
// Inicio MISUMI-298
//			|| rData['estado']=='EN CUR' || rData['estado']=='COMPROMETIDO')){
			|| rData['estado']=='EN CUR' || rData['estado']=='EN TRAMITE')){
// FIN MISUMI-298
			cantidadFormateada = cantidadFormateada;
		}else{
			cantidadFormateada = "<span class='p47_unidadesCliente'>"+cantidadFormateada+"</span>";
		}
	}
	return (cantidadFormateada);
}

function p47FormateoCantFinalCompra(cellValue, opts, rData) {
	var cantidadFormateada = '';
	if(cellValue != null && cellValue != ""){
		cantidadFormateada = $.formatNumber(cellValue,{format:"0.##",locale:"es"});
		if (rData['estado']!=null && (rData['estado']=='PEND' || rData['estado']=='PENDIENTE'
// Inicio MISUMI-298
//			|| rData['estado']=='EN CUR' || rData['estado']=='COMPROMETIDO')){
			|| rData['estado']=='EN CUR' || rData['estado']=='EN TRAMITE')){
// FIN MISUMI-298
			cantidadFormateada = cantidadFormateada;
		}else{
			cantidadFormateada = "<span class='p47_unidadesPedidas'>"+cantidadFormateada+"</span>";
		}
	}
	return (cantidadFormateada);
}

function p47FormateoCantServido(cellValue, opts, rData) {
	var cantidadFormateada = '';
	if(cellValue != null && cellValue != ""){
		cantidadFormateada = $.formatNumber(cellValue,{format:"0.##",locale:"es"});
		if (rData['estado']!=null && (rData['estado']=='PEND' || rData['estado']=='PENDIENTE'
// Inicio MISUMI-298
//			|| rData['estado']=='EN CUR' || rData['estado']=='COMPROMETIDO')){
			|| rData['estado']=='EN CUR' || rData['estado']=='EN TRAMITE')){
// FIN MISUMI-298
			cantidadFormateada = cantidadFormateada;
		}else{
			cantidadFormateada = "<span class='p47_unidadesConfirmadas'>"+cantidadFormateada+"</span>";
		}
	}
	return (cantidadFormateada);
}

function p47FormateoCantNoServido(cellValue, opts, rData) {
	var cantidadFormateada = '';
	if(cellValue != null && cellValue != ""){
		cantidadFormateada = $.formatNumber(cellValue,{format:"0.##",locale:"es"});
		if (rData['estado']!=null && (rData['estado']=='PEND' || rData['estado']=='PENDIENTE'
// Inicio MISUMI-298
//			|| rData['estado']=='EN CUR' || rData['estado']=='COMPROMETIDO')){
			|| rData['estado']=='EN CUR' || rData['estado']=='EN TRAMITE')){
// FIN MISUMI-298
			cantidadFormateada = cantidadFormateada;
		}else{
			cantidadFormateada = "<span class='p47_unidadesNoServidas'>"+cantidadFormateada+"</span>";
		}
	}
	return (cantidadFormateada);
}

function p47FormateoPesoDesde(cellValue, opts, rData) {
	var pesoDesdeFormateado = '';
	var pesoHastaFormateado = '';
	var especificacionFormateada = '';
	if (rData['nivel']!=null && rData['nivel']==1){
		if (cellValue!=null && cellValue!=0){
			pesoDesdeFormateado = $.formatNumber(cellValue,{format:"0.##",locale:"es"});
		}
		if (pesoDesdeFormateado!=null && pesoDesdeFormateado!=''){
			especificacionFormateada = p47FormatoPesoDesde.replace('{0}',pesoDesdeFormateado);
		}
		if (rData['pesoHasta']!=null && rData['pesoHasta']!=0 && rData['pesoHasta']!=cellValue){
			pesoHastaFormateado = $.formatNumber(rData['pesoHasta'],{format:"0.##",locale:"es"});
			especificacionFormateada = p47FormatoPesoDesdePesoHasta.replace('{0}',pesoDesdeFormateado).replace('{1}',pesoHastaFormateado);
		} 
		if (rData['especificacion']!=null){
			if (especificacionFormateada != ""){
				especificacionFormateada += " / ";
			}
			especificacionFormateada += rData['especificacion'];
		} 
	}
	return (especificacionFormateada);
}

function p47FormateoNombreCliente(cellValue, opts, rData) {
	var clienteFormateada = '';
	if (rData['nivel']!=null && rData['nivel']==1){
		clienteFormateada = cellValue;
		if (rData['apellidoCliente']!=null){
			clienteFormateada += " " + rData['apellidoCliente'];
		}
		if (rData['telefonoCliente']!=null){
			clienteFormateada += " / " + rData['telefonoCliente'];
		}
	}
	return (clienteFormateada);
}

function p47FormateoEstadoGestadic(cellValue, opts, rData) {
	var imagen = "";
	var mostrarPdte = "none;";
	var mostrarOkKo = "none;";
	var confirmarPrecioFormateada = '';
	//Controlamos los posibles valores que me lleguen para pintar el icono correspondiente.
	if (rData['estadoGestadic'] == 'PDTE')
	{
		//Pintamos el icono de que ha ocurrido un error con la descripción de borrado
		mostrarPdte = "block;";
	}
	else if (rData['estadoGestadic'] == 'OK' || rData['estadoGestadic'] == 'KO' || rData['estadoGestadic'] == 'FIN')
	{
		//Pintamos el icono de que ha ocurrido un error con la descripción de modificación
		mostrarOkKo = "block;";
	}
	
	imagen += "<div id='"+opts.rowId+"_divPdteEC' align='center' style='display: "+ mostrarPdte + "'><img id='"+opts.rowId+"_imgPdte' src='./misumi/images/question-24.png'/></div>"; //Pdte
	imagen += "<div id='"+opts.rowId+"_divOkKoEC' align='center' style='display: " + mostrarOkKo + "'><img id='"+opts.rowId+"_imgOkKo' src='./misumi/images/dialog-accept-24.png'/></div>"; //OK o KO

	return imagen;	
}

function p47FormateoLocalizador(cellValue, opts, rData) {
	var localizadorFormateada = '';
	if (rData['nivel']!=null && rData['nivel']==1){
		localizadorFormateada = cellValue;
	}
	return (localizadorFormateada);
}

function p47ImageFormatMessageDetalle(cellValue, opts, rData) {
	var imagen = "";
	var mostrarError = "none;";
	var descError = "";
	var mostrarModificado = "none;";

	//Controlamos los posibles errores que me lleguen para pintar el icono correspondiente.
	if (parseFloat(rData['codigoError']) == '1')
	{
		//Pintamos el icono de que ha ocurrido un error con la descripción de borrado
		mostrarError = "block;";
		if (rData['descripcionError'] != '')
		{
			descError = rData['descripcionError'];
		}
		else
		{
			descError = replaceSpecialCharacters(p47errorBorrado);
		}
	}
	else if (parseFloat(rData['codigoError']) == '2')
	{
		//Pintamos el icono de que ha ocurrido un error con la descripción de modificación
		mostrarError = "block;";
		if (rData['descripcionError'] != '')
		{
			descError = rData['descripcionError'];
		}
		else
		{
			descError = replaceSpecialCharacters(p47errorModificacion);
		}
	}
	else if (parseFloat(rData['codigoError']) == '8')
	{
		//Pintamos el icono de que se ha guardado, por que se ha realizado la modificación correctamente
		mostrarModificado = "block;";
	}
	
	imagen += "<div id='"+opts.rowId+"_divGuardadoEC' align='center' style='display: "+ mostrarModificado + "'><img id='"+opts.rowId+"_imgGuardado' src='./misumi/images/floppy.png' title='"+p47iconoModificado+"'/></div>"; //Modificado
	imagen += "<div id='"+opts.rowId+"_divErrorEC' align='center' style='display: " + mostrarError + "'><img id='"+opts.rowId+"_imgError' src='./misumi/images/dialog-error-24.png' title='"+descError+"'/></div>"; //Error

	//Añadimos los valores de la columna referencia ocultos para poder utilizarlos posteriormente.
	var datoCodArticulo = "<input type='hidden' id='"+opts.rowId+"_codArticuloEC' value='"+rData['codArtFormlog']+"'>";
	imagen +=  datoCodArticulo;
	
	//Añadimos los valores de la columna si es modificable ocultos para poder utilizarlos posteriormente.
	var datoModificable = "<input type='hidden' id='"+opts.rowId+"_modificableEC' value='"+rData['flgModificable']+"'>";
	imagen +=  datoModificable;
	
	//Añadimos los valores de la columna relCompraVenta ocultos para poder utilizarlos posteriormente.
	var datoRelCompraVenta = "<input type='hidden' id='"+opts.rowId+"_relCompraVentaEC' value='"+rData['relCompraVenta']+"'>";
	imagen +=  datoRelCompraVenta;
	
	//Añadimos los valores de la columna localizador ocultos para poder utilizarlos posteriormente.
	var datoLocalizador = "<input type='hidden' id='"+opts.rowId+"_localizadorEC' value='"+rData['localizador']+"'>";
	imagen +=  datoLocalizador;

	//Añadimos los valores de la columna estadoGestadic ocultos para poder utilizarlos posteriormente.
	var datoEstadoGestadic = "<input type='hidden' id='"+opts.rowId+"_estadoGestadicEC' value='"+rData['estadoGestadic']+"'>";
	imagen +=  datoEstadoGestadic;

	return imagen;
}

function p47FormateoReferenciaDetalle(cellValue, opts, rData) {
	var referenciaFormateada = '';
	if (rData['estado']!=null && (rData['estado']=='PEND' || rData['estado']=='PENDIENTE')){
		referenciaFormateada = cellValue;
		if (rData['cambioRef']!=null && rData['cambioRef']=='S' && rData['relCompraVenta']!=null && rData['relCompraVenta']=='S'){
			referenciaFormateada = "<span class='p47_referenciaCambiadaEnlace'>"+referenciaFormateada+"</span>";
		}else if (rData['cambioRef']!=null && rData['cambioRef']=='S'){
			referenciaFormateada = "<span class='p47_referenciaCambiada'>"+referenciaFormateada+"</span>";
		}else if (rData['relCompraVenta']!=null && rData['relCompraVenta']=='S'){
			referenciaFormateada = "<span class='p47_referenciaEnlace'>"+referenciaFormateada+"</span>";
		}else if (rData['faltaRef']!=null && rData['faltaRef']=='S' && rData['cambioRef']!=null && rData['cambioRef']!='S'){
			referenciaFormateada = "-----";
		}

	}
	return referenciaFormateada;
}

function p47FormateoDenominacionDetalle(cellValue, opts, rData) {
	var denominacionFormateada = '';
	if (rData['estado']!=null && (rData['estado']=='PEND' || rData['estado']=='PENDIENTE')){
		denominacionFormateada = cellValue;
		if (rData['cambioRef']!=null && rData['cambioRef']=='S' && rData['relCompraVenta']!=null && rData['relCompraVenta']=='S'){
			denominacionFormateada = "<span class='p47_referenciaCambiada'>"+denominacionFormateada+"</span>";
		}else if (rData['cambioRef']!=null && rData['cambioRef']=='S'){
			denominacionFormateada = "<span class='p47_referenciaCambiada'>"+denominacionFormateada+"</span>";
		}
	}
	return denominacionFormateada;
}

function p47FormateoDateDetalle(cellValue, opts, rData) {
	var dateFormateada = '';
	if (rData['estado']!=null && (rData['estado']=='PEND' || rData['estado']=='PENDIENTE')){
		if (cellValue != null)
		{
			var diaFecha = parseInt(cellValue.substring(8,10),10);
			var mesFecha = parseInt(cellValue.substring(5,7),10);
			var anyoFecha = parseInt(cellValue.substring(0,4),10);
			
		
			fechaFormateada = $.datepicker.formatDate("D dd-M", new Date(anyoFecha, mesFecha - 1, diaFecha),{
				dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
				dayNames: $.datepicker.regional[ "es" ].dayNames,
				monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
				monthNames: $.datepicker.regional[ "es" ].monthNames
				});
		}
		
		dateFormateada = "" + fechaFormateada;
	}
	return dateFormateada;
}

function p47FormateoCantEncargoDetalle(cellValue, opts, rData) {
	
	var cantidadFormateada = '';
	if(cellValue != null && cellValue != ""){
		cantidadFormateada = $.formatNumber(cellValue,{format:"0.##",locale:"es"});
		if (rData['estado']!=null && (rData['estado']=='PEND' || rData['estado']=='PENDIENTE'
// Inicio MISUMI-298
//			|| rData['estado']=='EN CUR' || rData['estado']=='COMPROMETIDO')){
			|| rData['estado']=='EN CUR' || rData['estado']=='EN TRAMITE')){
// FIN MISUMI-298
			cantidadFormateada = cantidadFormateada;
		}else{
			cantidadFormateada = "<span class='p47_unidadesCliente'>"+cantidadFormateada+"</span>";
		}
	}
	return (cantidadFormateada);
}

function p47FormateoCantFinalCompraDetalle(cellValue, opts, rData) {
	var cantidadFormateada = '';
	return (cantidadFormateada);
}

function p47FormateoCantServidoDetalle(cellValue, opts, rData) {
	var cantidadFormateada = '';
	if(cellValue != null && cellValue != "" && cellValue != 0){
		if (rData['estado']!=null && (rData['estado']=='CONF' || rData['estado']=='CONFIRMADO' || rData['estado']=='CONF/NSR' || rData['estado']=='CONFIR/NO SERV' || rData['estado']=='NSR' || rData['estado']=='NO SERVIDO')){
			cantidadFormateada = $.formatNumber(cellValue,{format:"0.##",locale:"es"});
			cantidadFormateada = "<span class='p47_unidadesConfirmadas'>"+cantidadFormateada+"</span>";
		}
	}
	return (cantidadFormateada);
}

function p47FormateoCantNoServidoDetalle(cellValue, opts, rData) {
	var cantidadFormateada = '';
	if(cellValue != null && cellValue != ""){
		cantidadFormateada = $.formatNumber(cellValue,{format:"0.##",locale:"es"});
		cantidadFormateada = "<span class='p47_unidadesNoServidas'>"+cantidadFormateada+"</span>";
	}
	return (cantidadFormateada);
}

function p47FormateoPesoDesdeDetalle(cellValue, opts, rData) {
	var pesoDesdeFormateado = '';
	var pesoHastaFormateado = '';
	var especificacionFormateada = '';
	if (cellValue!=null && cellValue!=0){
		pesoDesdeFormateado = $.formatNumber(cellValue,{format:"0.##",locale:"es"});
	}
	if (pesoDesdeFormateado!=null && pesoDesdeFormateado!=''){
		especificacionFormateada = p47FormatoPesoDesde.replace('{0}',pesoDesdeFormateado);
	}
	if (rData['pesoHasta']!=null && rData['pesoHasta']!=0 && rData['pesoHasta']!=cellValue){
		pesoHastaFormateado = $.formatNumber(rData['pesoHasta'],{format:"0.##",locale:"es"});
		especificacionFormateada = p47FormatoPesoDesdePesoHasta.replace('{0}',pesoDesdeFormateado).replace('{1}',pesoHastaFormateado);
	} 
	if (rData['especificacion']!=null){
		if (especificacionFormateada != ""){
			especificacionFormateada += " / ";
		}
		especificacionFormateada += rData['especificacion'];
	} 
	return (especificacionFormateada);
}

function p47FormateoNombreClienteDetalle(cellValue, opts, rData) {
	var clienteFormateada = cellValue;
	if (rData['apellidoCliente']!=null){
		clienteFormateada += " " + rData['apellidoCliente'];
	}
	if (rData['telefonoCliente']!=null){
		clienteFormateada += " / " + rData['telefonoCliente'];
	}

	return (clienteFormateada);
}

function p47FormateoEstadoGestadicDetalle(cellValue, opts, rData) {
	var imagen = "";
	var mostrarPdte = "none;";
	var mostrarOkKo = "none;";
	var confirmarPrecioFormateada = '';
	//Controlamos los posibles valores que me lleguen para pintar el icono correspondiente.
	if (rData['estadoGestadic'] == 'PDTE')
	{
		//Pintamos el icono de que ha ocurrido un error con la descripción de borrado
		mostrarPdte = "block;";
	}
	else if (rData['estadoGestadic'] == 'OK' || rData['estadoGestadic'] == 'KO' || rData['estadoGestadic'] == 'FIN')
	{
		//Pintamos el icono de que ha ocurrido un error con la descripción de modificación
		mostrarOkKo = "block;";
	}
	imagen += "<div id='"+opts.rowId+"_divPdteEC' align='center' style='display: "+ mostrarPdte + "'><img id='"+opts.rowId+"_imgPdte' src='./misumi/images/question-24.png'/></div>"; //Pdte
	imagen += "<div id='"+opts.rowId+"_divOkKoEC' align='center' style='display: " + mostrarOkKo + "'><img id='"+opts.rowId+"_imgOkKo' src='./misumi/images/dialog-accept-24.png'/></div>"; //OK o KO

	return imagen;	
}

function p47FormateoLocalizadorDetalle(cellValue, opts, rData) {
	var localizadorFormateada = '';
	if (cellValue != null && cellValue != 'null'){
		localizadorFormateada = cellValue;
	}

	return (localizadorFormateada);
}

function p47ControlesPantalla(){
	
	//Nos recorremos los registros existentes en pantalla.
	var rowsInPage = jQuery(gridP47EncargosCliente.nameJQuery).jqGrid('getGridParam', 'rowNum');
	for (i = 0; i < rowsInPage; i++){
		
		//Controlamos bloquear el registro(no permitir checkear), cuando el registro no sea modificable
		//por el WS o cuando el registro ha sido realizado por Central.
		p47ControlBloqueos(i);
		
		//Tenemos que seleccionar las checks que nos llegan de sesión
		p47CheckearSeleccionados(gridP47EncargosCliente.name, i);
	}
}

function p47ControlBloqueos(fila){
	//Control de acceso a subgrid
	//Primero busco el array de rowId's del grid
	var idToDataIndex = $(gridP47EncargosCliente.nameJQuery).jqGrid('getDataIDs');
	//Después busco dentro del array de rowId's el correspondiente a la fila
	var idFila = idToDataIndex[fila];

	flgModificable = $("#"+(idFila)+"_modificableEC").val();
	nivel = $("#"+(idFila)+"_nivelEC").val();

	//Si el nivel es 1 oculto el signo +
	if (nivel == '1'){
		$("#"+idFila+" td.sgcollapsed",gridP47EncargosCliente[0]).unbind('click').html('');
	}

	//Si el nivel es 1 y es modificable añado un checkbox
	if (nivel == '1' && flgModificable == 'S'){
		var checkBox = '<input role="checkbox" id="jqg_'+gridP47EncargosCliente.name+'_'+idFila+'" class="cbox p47_seleccionCheckBoxChild" name="jqg_'+gridP47EncargosCliente.name+'_'+idFila+'" type="checkbox">';
		$("#"+idFila+" td.sgcollapsed").addClass("p47_seleccionCheckBoxParent");
		$("#"+idFila+" td.sgcollapsed").append(checkBox);

		$("#jqg_"+gridP47EncargosCliente.name+"_"+(idFila)).click(function (event) {
			var localizadorFila = $("#"+(idFila)+"_localizadorEC").val();
			if ("S"==p47Seleccionados.get(localizadorFila)){//Si existía es porque estaba clicado el check 
				p47Seleccionados.put(localizadorFila, "N");					
			}else{//Si no existía o estaba no clicado hay que añadirlo a la lista
				p47Seleccionados.put(localizadorFila, "S"); //Sólo interesa guardar los checkeados
			}
		});		
	}
}

function p47ControlesPantallaDetalle(idSubtabla){
	
	$("#jqgh_gridP47EncargosCliente_cb").attr("style", "display:none");
	
	//Nos recorremos los registros existentes en pantalla.
	var rowsInPage = jQuery("#"+idSubtabla).jqGrid('getGridParam', 'rowNum');
	for (i = 0; i < rowsInPage; i++){
		
		//Controlamos bloquear el registro(no permitir checkear), cuando el registro no sea modificable
		//por el WS o cuando el registro ha sido realizado por Central.
		p47ControlBloqueosDetalle(idSubtabla, i);
		
		//Tenemos que seleccionar las checks que nos llegan de sesión
		p47CheckearSeleccionadosDetalle(idSubtabla, i);
	}
}

function p47ControlBloqueosDetalle(idSubtabla, fila){
	
	//Control de acceso a subgrid
	//Primero busco el array de rowId's del grid
	var idToDataIndex = $("#"+idSubtabla).jqGrid('getDataIDs');
	//Después busco dentro del array de rowId's el correspondiente a la fila
	var idFila = idToDataIndex[fila];
	flgModificable = $("#"+(idFila)+"_modificableEC").val();
	
	if (flgModificable != 'S'){
		//Tenemos que deshabilitar la check
		$("#jqg_"+idSubtabla+"_"+(idFila)).attr("style", "display:none");
	}else{
		$("#jqg_"+idSubtabla+"_"+(idFila)).click(function (event) {
			var $focused = $(event.target);
			var id = $focused.attr("id");
			var i = id.indexOf("_");
			var rowId = id.substring(0, i);

			var localizadorFila = $("#"+(idFila)+"_localizadorEC").val();
			if ("S"==p47Seleccionados.get(localizadorFila)){//Si existía es porque estaba clicado el check 
				p47Seleccionados.put(localizadorFila, "N");					
			}else{//Si no existía o estaba no clicado hay que añadirlo a la lista
				p47Seleccionados.put(localizadorFila, "S"); //Sólo interesa guardar los checkeados
			}
		});
	}
}

function p47CheckearSeleccionados(idTabla, fila){
	
	//Control de acceso al grid
	//Primero busco el array de rowId's del grid
	var idToDataIndex = $('#'+idTabla).jqGrid('getDataIDs');
	//Después busco dentro del array de rowId's el correspondiente a la fila
	var idFila = idToDataIndex[fila];

	var localizadorFila = $("#"+(idFila)+"_localizadorEC").val()+"";
	if ("S"==p47Seleccionados.get(localizadorFila)){//Si existía es porque estaba clicado el check 
		$('#jqg_'+idTabla+'_'+idFila).attr('checked', true);
	}
}

function p47CheckearSeleccionadosDetalle(idTabla, fila){
	
	//Control de acceso al grid
	//Primero busco el array de rowId's del grid
	var idToDataIndex = $('#'+idTabla).jqGrid('getDataIDs');
	//Después busco dentro del array de rowId's el correspondiente a la fila
	var idFila = idToDataIndex[fila];

	var localizadorFila = $("#"+(idFila)+"_localizadorEC").val()+"";
	if ("S"==p47Seleccionados.get(localizadorFila)){//Si existía es porque estaba clicado el check 
		jQuery('#'+idTabla).jqGrid('setSelection', idFila);
	}
}
function p47ObtenerSeleccionados(){
	
	//Nos recorremos la lista de seleccionados.
	for (i = 0; i < p47SeleccionadosTotal.length; i++){
		
		var localizadorFila = p47SeleccionadosTotal[i].localizador+"";
		
		//Si es una de las referencias de la página, actualizamos el valor de seleccionado.
		if ("S"==p47Seleccionados.get(localizadorFila)){
			p47SeleccionadosTotal[i].seleccionado = "S";
		}else if("N"==p47Seleccionados.get(localizadorFila)){
			p47SeleccionadosTotal[i].seleccionado = "N";
		}
	}
}

function p47FindValidationRemove(){
	var messageVal = p47emptyListaSeleccionados;
	
	//Nos recorremos la lista de seleccionados.
	for (j = 0; j < p47SeleccionadosTotal.length; j++){
		if (p47SeleccionadosTotal[j].seleccionado == "S")
		{
			messageVal=null;
		}
	}
	return messageVal;
	
}

function p47BorrarDatos(){
	
	//Obtenemos el array con los registros seleccionados
	p47ObtenerSeleccionados();
	
	var messageVal=p47FindValidationRemove();
	
	if (messageVal!=null){
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		
		 $(function() {
			 $( "#p47dialog-confirm" ).dialog({
				 resizable: false,
				 height:140,
				 modal: true,
				 buttons: {
				 "Si": function() {
				 p47Remove();
				 $( this ).dialog( "close" );
				 },
				 "No": function() {
				 $( this ).dialog( "close" );
				 }
				 }
			 });
		});
	}
}

function p47Remove(){
	
	var recordPedidoAdicionalEC = new PedidoAdicionalEC (null, $("#centerId").val(), null, null, null, null, 
			null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, p47SeleccionadosTotal);
	
	//Reseteamos el array de seleccionados.
	p47DeleteSeleccionados();
	p47SeleccionadosTotal = new Array();
	
	var objJson = $.toJSON(recordPedidoAdicionalEC.preparePedidoAdicionalECToJsonObject());

		$("#AreaResultados .loading").css("display", "block");
		
		$.ajax({
			type : 'POST',
			url : './pedidoAdicionalEncargosCliente/removeDataGridEC.do?max='+gridP47EncargosCliente.getRowNumPerPage()+'&index='+gridP47EncargosCliente.getSortIndex()+'&sortorder='+gridP47EncargosCliente.getSortOrder(),
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {		
				$(gridP47EncargosCliente.nameJQuery)[0].addJSONData(data.datos);
				gridP47EncargosCliente.actualPage = data.datos.page;
				gridP47EncargosCliente.localData = data.datos;
				
				if (data != null && data != '')
				{
					if (data.codError == 0){
						$("#p40AreaErrores").attr("style", "display:none");
						$("div#p40_AreaPestanas").attr("style", "display:block");
						
						p47SeleccionadosTotal = data.listadoSeleccionados;
						
						//Actualizamos el contador de encargos de cliente tras el borrado.
						$("#p40_fld_contadorEncargosCliente").text(literalEncargosCliente + " ("+data.contadorEncargosCliente+")");
						
		                
						$("#AreaResultados .loading").css("display", "none");	
						if (data.records == 0){
							createAlert(replaceSpecialCharacters(gridP42PedidoAdicionalM.emptyRecords), "ERROR");
						} 
						
						p47ControlesPantalla();
					}else{
						$("#AreaResultados .loading").css("display", "none");
						$("#p40_erroresTexto").text(data.descError);
						$("#p40AreaErrores").attr("style", "display:block");
						$("#p40_AreaPestanas").attr("style", "display:none");
					}
						
				}else{
					$("#p40AreaErrores").attr("style", "display:none");
				}
			},
			error : function (xhr, status, error){
				$("#AreaResultados .loading").css("display", "none");	
				handleError(xhr, status, error, locale);				
	        }			
		});
}

function p47DeleteSeleccionados(){
	p47Seleccionados.removeAll();
}

function p47ModificarEncargo(localizador, rowId, subgridDivId){
	
	//Variables para saber que recargar tras la modificación
	p47rowId = rowId;
	p47subgridDivId = subgridDivId;

	//Antes de abrir la pantalla de modificación única por referencia, obtenemos la información necesaria para mostrarla posteriormente.
	var recordPedidoAdicionalEC = new PedidoAdicionalEC (localizador, $("#centerId").val(), null, null, null, null, 
			null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, p47SeleccionadosTotal);
	
	var objJson = $.toJSON(recordPedidoAdicionalEC.preparePedidoAdicionalECToJsonObject());

		$("#AreaResultados .loading").css("display", "block");
		
		$.ajax({
			type : 'POST',
			url : './pedidoAdicionalEncargosCliente/openModifyDataGridEC.do?page='+gridP47EncargosCliente.getActualPage()+'&max='+gridP47EncargosCliente.getRowNumPerPage()+'&index='+gridP47EncargosCliente.getSortIndex()+'&sortorder='+gridP47EncargosCliente.getSortOrder(),
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {		
				if (data != null && data != '')
				{
					if (data.codigoError == 0){
						
						p62primeraFechaEntrega = p62getEncargoClientePrimeraFechaEntrega(data.primeraFechaEntrega, data.fechasVenta);
						
						if(data.fechaVenta != data.fechaVentaModificada){
							$("#encargoClienteFechaVentaModificadaCalendario").val(data.fechaVentaModificada);
						}else{
							$("#encargoClienteFechaVentaModificadaCalendario").val("");
						}
						
						p47CargaDatosCalendarios(data);
						
						p62CrearCalendarios();
						
						p62DefinePedidoAdicionalEC(data);
						
						//Primero cargamos los datos.
						p47CargaDatos(data);

						//Muestro o elimino mensaje de aviso en funcion de los datos
						if ((data.pesoDesde != null && data.pesoDesde != "") || (data.pesoHasta != null && data.pesoHasta != "") || (data.especificacion != null && data.especificacion != ""))
							gestionAvisoPedidoEspecial(true);
						else
							gestionAvisoPedidoEspecial(false);

					    if (pedidoAdicionalEC.tipoEncargo=="E"){
				        		 $("#p62_fechaEntregaEncCliDatePicker").removeClass("encargosEspeciales");
				        }else{
							if (new Date(p62primeraFechaEntrega) > new Date(data.fechaVentaPantalla.substring(4), data.fechaVentaPantalla.substring(2,4) - 1, data.fechaVentaPantalla.substring(0,2))){
				 				$("#p62_fechaEntregaEncCliDatePicker").addClass("encargosEspeciales");
				 			} else {
				 				$("#p62_fechaEntregaEncCliDatePicker").removeClass("encargosEspeciales");
				 			}
				        }						
						//Ahora establecemos los campos que son modificables
						p47ControlModificables(data);
						
						//Abrimos el popup
						$( "#p62_AreaModificacion" ).dialog( "open" );

						$("#p40AreaErrores").attr("style", "display:none");
						
						$("#AreaResultados .loading").css("display", "none");	
						
					}else if (data.codigoError == 2){
						$(function() {
							 $( "#p47dialog-confirm-noExiste" ).dialog({
								 resizable: false,
								 height:140,
								 modal: true,
								 buttons: {
								 Aceptar: function() {
								 p47NoExiste();
								 $( this ).dialog( "close" );
								 }
								 }
							 });
						});
					}	
					else{
						$("#AreaResultados .loading").css("display", "none");
						$("#p40_erroresTexto").text(data.descripcionError);
						$("#p40AreaErrores").attr("style", "display:block");
					}
				}
				
			},
			error : function (xhr, status, error){
				$("#AreaResultados .loading").css("display", "none");	
				handleError(xhr, status, error, locale);				
	        }			
		});
}


 
 function p47NoExiste(){

	 reloadDataP47PedidoAdicionalEC('S');
}

function p47CargaDatosCalendarios(data){
	
	//Carga de datos para los calendarios
	$("#codCentroCalendario").val($("#centerId").val());
	$("#codArticuloCalendario").val(data.codArtEroski);
	$("#identificadorCalendario").val("");
	$("#identificadorSIACalendario").val("");
	$("#clasePedidoCalendario").val("EC");
	$("#recargarParametrosCalendario").val("S");
	$("#cargadoDSCalendario").val("S");
	$("#encargoClienteFechaVentaModificada").val("S");
	
	var esEncargoEspecial="false";
	if ("E"==data.tipoEncargo){
		esEncargoEspecial = "true";
	}
	$("#encargoClienteUnidadesPedirCalendario").val(data.cantEncargo);
	$("#encargoClienteEspecialCalendario").val(esEncargoEspecial);
}

function p47CargaDatos(data){
	
	$("#p62_fld_referencia").val(data.codArtFormlog);
	if (data.faltaRef!=null && data.faltaRef=="S"){
		$("#p62_fld_generica").val("S");
		if(data.cambioRef!=null && data.cambioRef!='S'){
			$("#p62_fld_referenciaFormateada").val("-----");
		}else{
			$("#p62_fld_referenciaFormateada").val(data.codArtFormlog);
		}
	}else{
		$("#p62_fld_generica").val("N");
		$("#p62_fld_referenciaFormateada").val(data.codArtFormlog);
	}
	$("#p62_fld_denominacion").val(data.descriptionArtGrid);
	$("#p62_fld_aprov").val(data.tipoAprov);
	$("#p62_fld_uc").val(data.unidServ);
	if (data.fechaVenta != null && data.fechaVenta != '')
	{
	
		var fechaformateada = $.datepicker.formatDate("D dd", new Date(data.fechaVentaPantalla.substring(4), data.fechaVentaPantalla.substring(2,4) - 1, data.fechaVentaPantalla.substring(0,2)),{
			dayNamesShort: $.datepicker.regional[ "es" ].dayNamesMin,
			dayNames: $.datepicker.regional[ "es" ].dayNames,
			monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
			monthNames: $.datepicker.regional[ "es" ].monthNames
			});
		
		$("#p62_lbl_cantidad").text(fechaformateada);
	}

	$("#p62_fld_cantidad").val(data.cantEncargo);
	if (data.pesoDesde != null && data.pesoDesde != 0){
		$("#p62_fld_pesoDesde").val(data.pesoDesde).formatNumber({format:"0.##"});
	}else{
		$("#p62_fld_pesoDesde").val('');
	}
	if (data.pesoHasta != null && data.pesoHasta != 0){
		$("#p62_fld_pesoHasta").val(data.pesoHasta).formatNumber({format:"0.##"});
	}else{
		$("#p62_fld_pesoHasta").val('');
	}
	$("#p62_fld_especificacion").val(data.especificacion);

	if(data.fechaVentaModificadaPantalla != null && data.fechaVentaPantalla != data.fechaVentaModificadaPantalla){
		$("#p62_mensajesFechas").show();
	}else{
		$("#p62_mensajesFechas").hide();
	}

	if (data.fechaVentaPantalla != null && data.fechaVentaPantalla != '')
	{
		$("#p62_fechaEntregaEncCliDatePicker").datepicker( "setDate", new Date(data.fechaVentaPantalla.substring(4), data.fechaVentaPantalla.substring(2,4) - 1, data.fechaVentaPantalla.substring(0,2)) );
		$("#p62_fechaEntregaEncCliDatePicker").datepicker('refresh');
	}
	$("#p62_fld_clienteNombre").val(data.nombreCliente);
	$("#p62_fld_clienteApellido").val(data.apellidoCliente);
	$("#p62_fld_clienteTelefono").val(data.telefonoCliente);
	$("#p62_fld_centroContacto").val(data.contactoCentro);
	$("#p62_fld_localizador").val(data.localizador);
	$("#p62_fld_precio").val(data.confirmarPrecio);
	
	//Control de estados
	$("#p62_mensajeFieldsetPendiente").hide();
	$("#p62_mensajeFieldsetEnCurso").hide();
	$("#p62_mensajeFieldsetConfirmado").hide();
	$("#p62_mensajeFieldsetConfirmadoNsr").hide();
	$("#p62_mensajeFieldsetNsr").hide();
	
	$("#p62_bloque4_1_1_1").removeClass("p62_estadoVerde").addClass("p62_estadoSinColor");
	$("#p62_bloque4_1_1_2").removeClass("p62_estadoAmarillo").removeClass("p62_estadoVerde").addClass("p62_estadoSinColor");
	$("#p62_bloque4_1_1_3").removeClass("p62_estadoVerde").addClass("p62_estadoSinColor");
	$("#p62_bloque4_1_2_2").removeClass("p62_estadoRojoSuave").removeClass("p62_estadoRojoIntenso").addClass("p62_estadoSinColor");

	if (data.estado != null){
		if (data.estado == 'PEND' || data.estado == 'PENDIENTE'){
			$("#p62_bloque4_1_1_1").removeClass("p62_estadoSinColor").addClass("p62_estadoVerde");
			$("#p62_mensajeFieldsetPendiente").show();
// Inicio MISUMI-298
//		}else if (data.estado == 'EN CUR' || data.estado == 'COMPROMETIDO'){
		}else if (data.estado == 'EN CUR' || data.estado == 'EN TRAMITE'){
// Inicio MISUMI-298
			$("#p62_bloque4_1_1_1").removeClass("p62_estadoSinColor").addClass("p62_estadoVerde");
			$("#p62_bloque4_1_1_2").removeClass("p62_estadoSinColor").addClass("p62_estadoAmarillo");
			$("#p62_mensajeFieldsetEnCurso").show();
		}else if (data.estado == 'CONF' || data.estado == 'CONFIRMADO'){
			$("#p62_bloque4_1_1_1").removeClass("p62_estadoSinColor").addClass("p62_estadoVerde");
			$("#p62_bloque4_1_1_2").removeClass("p62_estadoSinColor").addClass("p62_estadoVerde");
			$("#p62_bloque4_1_1_3").removeClass("p62_estadoSinColor").addClass("p62_estadoVerde");
			$("#p62_mensajeFieldsetConfirmado").show();
		}else if (data.estado == 'CONF/NSR' || data.estado == 'CONFIR/NO SERV'){
			$("#p62_bloque4_1_1_1").removeClass("p62_estadoSinColor").addClass("p62_estadoVerde");
			$("#p62_bloque4_1_1_2").removeClass("p62_estadoSinColor").addClass("p62_estadoVerde");
			$("#p62_bloque4_1_1_3").removeClass("p62_estadoSinColor").addClass("p62_estadoVerde");
			$("#p62_bloque4_1_2_2").removeClass("p62_estadoSinColor").addClass("p62_estadoRojoIntenso");
			$("#p62_mensajeFieldsetConfirmadoNsr").show();
			var observacionesConfirmadoNsr = '';
			if (data.observacionesMisumi != null && $.trim(data.observacionesMisumi) != '' && data.observacionesMisumi != 'null' && data.observacionesMisumi != 'undefined'){
				observacionesConfirmadoNsr = '<br>'+data.observacionesMisumi;
			}
			$(".p62_parrafoMensajeConfirmadoNsr").html(p47mensajeConfirmadoNsr+'<br>'+observacionesConfirmadoNsr);
		}else if (data.estado == 'NSR' || data.estado == 'NO SERVIDO'){
			$("#p62_bloque4_1_1_1").removeClass("p62_estadoSinColor").addClass("p62_estadoVerde");
			$("#p62_bloque4_1_1_2").removeClass("p62_estadoSinColor").addClass("p62_estadoVerde");
			$("#p62_bloque4_1_2_2").removeClass("p62_estadoSinColor").addClass("p62_estadoRojoSuave");
			$("#p62_mensajeFieldsetNsr").show();
			var observacionesNsr = '';
			if (data.observacionesMisumi != null && $.trim(data.observacionesMisumi) != '' && data.observacionesMisumi != 'null' && data.observacionesMisumi != 'undefined'){
				observacionesNsr = '<br>'+data.observacionesMisumi;
			}
			$(".p62_parrafoMensajeNsr").html(p47mensajeNsr+observacionesNsr);
		} 
	}
}

function p47ControlModificables(data){
	
	//Primero deshabilitamos todos los campos.
	$("#p62_fechaEntregaEncCliDatePicker").datepicker('disable');
	$("#p62_fld_pesoDesde").attr("disabled", "disabled")
	$("#p62_fld_pesoHasta").attr("disabled", "disabled")
	$("#p62_fld_especificacion").attr("disabled", "disabled")

	$("#p62_fld_tipoPedidoAdicional").attr("disabled", "disabled");
	$("#p62_fld_referenciaFormateada").attr("disabled", "disabled");
	$("#p62_fld_denominacion").attr("disabled", "disabled");
	$("#p62_fld_aprov").attr("disabled", "disabled");
	$("#p62_fld_uc").attr("disabled", "disabled");
	$("#p62_fld_cantidad").attr("disabled", "disabled");
	
	$("#p62_fld_localizador").attr("disabled", "disabled");
	$("#p62_fld_precio").attr("disabled", "disabled");

	p47EstablecerModificables(data);
}

function p47EstablecerModificables(data){

	if (data.flgModificable != null && data.flgModificable=="S"){
		$("#p62_fld_cantidad").removeAttr("disabled");
		$("#p62_fechaEntregaEncCliDatePicker").datepicker('enable');
		$("#p62_fld_pesoDesde").removeAttr("disabled");
		$("#p62_fld_pesoHasta").removeAttr("disabled");
		$("#p62_fld_especificacion").removeAttr("disabled");
		$("#p62_fechaEntregaEncCliDatePicker a.ui-state-default").removeClass('p62_cursorDisabled');
	}
	else{
		$("#p62_fechaEntregaEncCliDatePicker a.ui-state-default").removeClass('p62_cursorDisabled').addClass('p62_cursorDisabled');
	}
	if (data.flgEspec == null || data.flgEspec == "" || data.flgEspec == "S"){
		$( "#p62_AreaBloqueEspecificacion" ).show();
		$( "#p62_AreaBloqueNoEspecificacion" ).hide();
		if ( p47visualizarPesos(data) ) {
			// Se muestran los campos de pesos	
			$( "#p62_lbl_pesoDesde" ).show();
			$( "#p62_lbl_pesoHasta" ).show();
			$( "#p62_fld_pesoDesde" ).show();
			$( "#p62_fld_pesoHasta" ).show();
		}
		else {
			// Se ocultan los campos de pesos
			$( "#p62_lbl_pesoDesde" ).hide();
			$( "#p62_lbl_pesoHasta" ).hide();
			$( "#p62_fld_pesoDesde" ).hide();
			$( "#p62_fld_pesoHasta" ).hide();
		}
	} else {
		$( "#p62_AreaBloqueEspecificacion" ).hide();
		$( "#p62_AreaBloqueNoEspecificacion" ).show();
	}
	
}

function p47visualizarPesos(data) {
	var codArea = "";
	var codSeccion = "";
	var codCategoria = "";
	var codSubcategoria = "";
	
	if (data.area!=null){
		var codAreaCompleto = data.area.split("-");
		codArea = parseInt(codAreaCompleto[0],10);
	}
	if (data.seccion!=null){
		var codSeccionCompleto = data.seccion.split("-");
		codSeccion = parseInt(codSeccionCompleto[0],10);
	}
	if (data.categoria!=null){
		var codCategoriaCompleto = data.categoria.split("-");
		codCategoria = parseInt(codCategoriaCompleto[0],10);
	}
	if (data.subcategoria!=null){
		var codSubcategoriaCompleto = data.subcategoria.split("-");
		codSubcategoria = parseInt(codSubcategoriaCompleto[0],10);
	}
	
	if ((codArea == "1" && codSeccion == "7" && codCategoria == "14") ||
		(codArea == "1" && codSeccion == "2" && codCategoria == "61" && codSubcategoria == "7") ||
		(codArea == "1" && codSeccion == "2" && codCategoria == "62" && codSubcategoria == "6") ||
		(codArea == "1" && codSeccion == "2" && codCategoria == "63" && codSubcategoria == "6") ||
		(codArea == "1" && codSeccion == "2" && codCategoria == "64" && codSubcategoria == "5") ||
		(codArea == "1" && codSeccion == "2" && codCategoria == "65" && codSubcategoria == "12")
			) {
		return true;
	} else {
		return false;
	}
}

function reloadDataP47Modificacion() {
	if (p47rowId != null && p47rowId != ""){//Recargar el detalle
		reloadDataP47PedidoAdicionalECDetalle(p47rowId, p47subgridDivId);
	}else{//Recargar la tabla principal
		reloadDataP47PedidoAdicionalEC('S');
	}
}