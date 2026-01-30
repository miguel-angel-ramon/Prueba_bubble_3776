var gridP16NSR=null;
var gridP16Movimientos=null;
var sigRotura = 0;

$(document).ready(function(){

	initializeScreenMovimientos()

});

function loadP16Stock(){
	//	$("#p14_gamaFinSemanaTablaTdL").text(data.variablesPedido.gamaDiscLunes);
		
	var movimientoStock = new MovimientoStock ( 
			$("#centerId").val() , 
			$("#p13_fld_referenciaEroski").val());
	
	var objJson = $.toJSON(movimientoStock.prepareToJsonObject_2());
	
	$("#p16_AreaMovimientos .loading").css("display", "block");
	
	$.ajax({
		type : 'POST',			
		url : './referenciasCentro/movimientoStockDiasAnteriores.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		
			
			sigRotura = 0; 
			for (i = 21; i > 0; i--){
				escribirStock("#p16_stockTd" + i,  "#p16_stockDTd" + i ,data[21-i]);
			}
			
			var x = 1;
			for (j = 20; j > -1; j--){
				controlarRoturaFestivos("#p16_stockTd" + x,  "#p16_stockDTd" + x ,data[j]);
				x++
			}
			
	
			//escribirStock("#p16_stock3TdD",  "#p16_stock3DTdD" ,data[0]);
				
			$("#p16_AreaMovimientos .loading").css("display", "none");	 
		},
		error : function (xhr, status, error){
			$("#p16_AreaMovimientos .loading").css("display", "none");	
			handleError(xhr, status, error, locale);				
		}			
	});		

	
				
}


function redirigirLogin() {
	window.location='./login.do';
}
function loadP16Foto(){
		if ($("#p13_fld_tieneFoto").val() == "S"){
			var url = "./welcome/getImage.do?codArticulo="+$("#p13_fld_referenciaEroski").val();
			 $('#p16_img_referencia').attr('src',url);
			 $('#p16_img_referencia').attr("onerror", "redirigirLogin()");
			 
			$("#p16_AreaFotos").show();
		} else {
			$("#p16_AreaFotos").hide();
		}
				
}

function controlarRoturaFestivos(tdName, tdFechaName, datos){
	
	if (datos.esFestivo == "T")
	{
		if (sigRotura == 1)
		{
			$(tdName).removeClass("p16_stockTablaTd").addClass("p16_stockRoturaTablaTd");
		}
	}
	else
	{
		if (datos.rotura != "SI")
			sigRotura = 0;
	}
	
	if (datos.rotura == "SI")
		sigRotura = 1;
}

function escribirStock(tdName, tdFechaName, datos){
	
	$(tdFechaName).text(datos.fecha);
	
	if (datos.stockFinal == -999999){
		$(tdName).html("<br/>");
	} else {
		if (datos.stockFinal == null){ 
			$(tdName).html("<br/>");
		} else
			$(tdName).text(datos.stockFinal).formatNumber({format:"0.00"});
	}
	
	if (datos.rotura == "SI")
		$(tdName).removeClass("p16_stockTablaTd").addClass("p16_stockRoturaTablaTd");
	else
		$(tdName).removeClass("p16_stockRoturaTablaTd").addClass("p16_stockTablaTd");
	
	if (datos.esFestivo == "T")
		$(tdFechaName).addClass("partyDay");
	
	if (datos.esHoy == "S")
	{
		$(tdFechaName).addClass("esHoy");
		if (datos.flgErrorWSStockTienda == 0){
			$("#p16_stockTablaMensajeErrorHoy").css("display", "none");
			$(tdName).addClass("esHoy");
		}else{ //Error del WS
			$("#p16_stockTablaMensajeErrorHoy").css("display", "inline-block");
			$(tdName).addClass("p16_stockHoyErrorTablaTd");
			$(tdName).html(datos.mensajeErrorWSStockTienda);
		}
	}
}

function initializeScreenMovimientos(){
	
	$("#p16_fld_pendienteRecibir1").attr("disabled", "disabled");
	$("#p16_fld_pendienteRecibir2").attr("disabled", "disabled");
	$("#p16_stockTablaMensajeErrorHoy").css("display", "none");
	
}

function resetResultadosM(){
	
	for (i = 1; i <= 21; i++){
		if ($("#p16_stockTd" + i).hasClass("p16_stockHoyErrorTablaTd")){
			//Borramos el class de error del stock actual y de los últimos 15 días, si lo tiene
			$("#p16_stockTd" + i).removeClass("p16_stockHoyErrorTablaTd");
		}
		//Borramos los datos del stock actual y de los últimos 15 días
		$("#p16_stockTd" + i).text("");
	}
	$("#p16_stockTablaMensajeErrorHoy").css("display", "none");
	
	$("#p16_fld_pendienteRecibir1").val("");
	$("#p16_fld_pendienteRecibir2").val("");
	
	if (gridP16NSR != null) {
		gridP16NSR.clearGrid();
	}
	if (gridP16Movimientos != null) {
		gridP16Movimientos.clearGrid();
	}
	
	$("#p16_AreaFotos").hide();
		
}

function reloadMovimientos() {
	$("#p13_pestanaMovimientosCargada").val("S");

	// eliminar el DIV del footerData antes de realizar la carga.
	$("div.ui-jqgrid-sdiv").remove();

	loadP16Stock();
	loadP16Movimientos(locale);
	loadP16PendientesRecibir(locale);
	loadP16NSR(locale);
	loadP16Foto();
}

function loadP16NSR(locale){
	 gridP16NSR = new GridP16NSR(locale);
	
	var jqxhr = $.getJSON(gridP16NSR.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				gridP16NSR.colNames = data.p16NSRColNames;
				gridP16NSR.title = data.p16NSRGridTitle;
				gridP16NSR.emptyRecords= data.emptyRecords;
				loadP16NSRMock(gridP16NSR);
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
           });
}

function loadP16NSRMock(gridP16NSR) {
	//para hacer cabeceras
	//jQuery("#ghwcs").jqGrid('setGroupHeaders', { useColSpanStyle: true, groupHeaders:[ {startColumnName: 'amount', numberOfColumns: 3, titleText: '<em>Price</em>'}, {startColumnName: 'closed', numberOfColumns: 2, titleText: 'Shiping'} ] });
		$(gridP16NSR.nameJQuery).jqGrid({
			ajaxGridOptions : {
				contentType : 'application/json; charset=utf-8',
				cache : false
			},
			//url : './misumi/resources/p16ReferenciasCentroM/p16mockNSR.json',
			datatype : 'local',
			contentType : 'application/json',
			mtype : 'POST',
			colNames : gridP16NSR.colNames,
			colModel :gridP16NSR.cm,  
			rowNum : 4,
			rowList : [ 4, 8 ],
			height : "100%",
			autowidth : true,
			width :  "auto",
			rownumbers : true,
			pager : gridP16NSR.pagerNameJQuery,
			viewrecords : true,
			caption : gridP16NSR.title,
			altclass: "ui-priority-secondary",
			altRows: true, //false, para que el grid no muestre cebrado
			hidegrid : false, //false, para ocultar el boton que colapsa el grid.
			sortable : true,
			index: gridP16NSR.sortIndex,
			sortname: gridP16NSR.sortIndex,
			sortorder: gridP16NSR.sortOrder,
			emptyrecords : gridP16NSR.emptyRecords,
			gridComplete : function() {

				
			},
			loadComplete : function(data) {
				gridP16NSR.actualPage = data.page;
				gridP16NSR.localData = data;
				gridP16NSR.sortIndex = null;
				gridP16NSR.sortOrder = null;
				reloadDataP16NSR(gridP16NSR);
			},
			onPaging : function(postdata) {			
				alreadySorted = false;
				gridP16NSR.sortIndex = null;
				gridP16NSR.sortOrder = null;
				gridP16NSR.saveColumnState.call($(this), this.p.remapColumns);
				reloadDataP16NSR(gridP16NSR);
				return 'stop';
			},
			onSelectRow: function(id){

			},
			resizeStop: function () {
				gridP16NSR.saveColumnState.call($(this),gridP16NSR.myColumnsState);
            },
			onSortCol : function (index, columnIndex, sortOrder){
				gridP16NSR.sortIndex = index;
				gridP16NSR.sortOrder = sortOrder;
				gridP16NSR.saveColumnState.call($(this), this.p.remapColumns);
				reloadDataP16NSR(gridP16NSR);
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

function reloadDataP16NSR(grid) {
	
	var stockNoServido = new StockNoServido ( 
			$("#centerId").val() , 
			$("#p13_fld_referenciaEroski").val());
	if (grid.firstLoad) {
        grid.firstLoad = false;
        if (grid.isColState) {
            $(this).jqGrid("remapColumns", grid.myColumnsState.permutation, true);
        }
    } else {
    	grid.myColumnsState = grid.restoreColumnState(grid.cm);
    	grid.isColState = typeof (grid.myColumnsState) !== 'undefined' && grid.myColumnsState !== null;
    }
	var objJson = $.toJSON(stockNoServido.prepareToJsonObject_2());
	
	$("#p16_AreaNSR .loading").css("display", "block");
	$.ajax({
		type : 'POST',			
		url : './referenciasCentro/loadNSR.do?page='+grid.getActualPage()+'&max='+grid.getRowNumPerPage()+'&index='+grid.getSortIndex()+'&sortorder='+grid.getSortOrder(),
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		
			$(grid.nameJQuery)[0].addJSONData(data);
			grid.actualPage = data.page;
			grid.localData = data;
			$("#p16_AreaNSR .loading").css("display", "none");	
			$("#p16_AreaNSR").css("height", $("#p16_AreaNSR .ui-jqgrid").css("height"));
			
			// eliminar el DIV del footerData antes de realizar la carga.
			$("#gview_gridP16NSR div.ui-jqgrid-sdiv").remove();
			$("#gview_gridP18 div.ui-jqgrid-sdiv").remove();

		},
		error : function (xhr, status, error){
			$("#p16_AreaNSR .loading").css("display", "none");	
			handleError(xhr, status, error, locale);				
		}			
	});		

	
}

function loadP16PendientesRecibir(locale){
	
	var pendientesRecibir = new PendientesRecibir ( 
			$("#centerId").val() , 
			$("#p13_fld_referenciaEroski").val() ,
			null ,
			null);
	
	var objJson = $.toJSON(pendientesRecibir.prepareToJsonObject_2());
	
	$.ajax({
		type : 'POST',			
		url : './referenciasCentro/pendientesRecibir.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {	
			var tipoAprov = data.tipoAprov;
			if (tipoAprov != null && tipoAprov=='D'){
				$("#p16_AreaPendienteRecibir").css("display", "none");
				$("#p16_AreaNSR").css("width", "100%");
			}else{
				$("#p16_AreaNSR").css("width", "720px");
				$("#p16_AreaPendienteRecibir").css("display", "block");
			}
			jQuery(gridP16NSR.nameJQuery).jqGrid('setGridWidth', $("#p16_AreaNSR").width(), true);
			$("#p16_fld_pendienteRecibir1").val(data.cantHoy);
			$("#p16_fld_pendienteRecibir2").val(data.cantFutura);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});		
}

function loadP16Movimientos(locale){
	 gridP16Movimientos = new GridP16Movimientos(locale);
	 gridP16Movimientos.myColumnsState = gridP16Movimientos.restoreColumnState(gridP16Movimientos.cm);
	 gridP16Movimientos.isColState = typeof (gridP16Movimientos.myColumnsState) !== 'undefined' && gridP16Movimientos.myColumnsState !== null;
	 var jqxhr = $.getJSON(gridP16Movimientos.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				gridP16Movimientos.colNames = data.p16MovimientosColNames;
				gridP16Movimientos.title = data.p16MovimientosGridTitle;
				gridP16Movimientos.emptyRecords= data.emptyRecords;
				gridP16Movimientos.titleVentaForzada= data.titleVentaForzada;
				gridP16Movimientos.titleModifPorAjuste= data.titleModifPorAjuste;
				gridP16Movimientos.titleModifPorRegularizacion= data.titleModifPorRegularizacion;
				loadP16MovimientosMock(gridP16Movimientos);
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
         });
}

function loadP16MovimientosMock(gridP16Movimientos) {
	
	var totalMostrado = false;
	
	// eliminar los DIV del footerData para que no se acumulen en cada consulta.
	$("div.ui-jqgrid-sdiv").remove();

	//para hacer cabeceras
	//jQuery("#ghwcs").jqGrid('setGroupHeaders', { useColSpanStyle: true, groupHeaders:[ {startColumnName: 'amount', numberOfColumns: 3, titleText: '<em>Price</em>'}, {startColumnName: 'closed', numberOfColumns: 2, titleText: 'Shiping'} ] });
		$(gridP16Movimientos.nameJQuery).jqGrid({
			ajaxGridOptions : {
				contentType : 'application/json; charset=utf-8',
				cache : false
			},
			//url : './misumi/resources/p16ReferenciasCentroM/p16mockMovimientos.json',
			datatype : 'local',
			contentType : 'application/json',
			mtype : 'POST',
			colNames : gridP16Movimientos.colNames,
			colModel : gridP16Movimientos.cm, 
			rowNum : 7,
			rowList : [ 7, 14 ],
			height : "100%",
			autowidth : true,
			width : "auto",
			rownumbers : true,
			pager : gridP16Movimientos.pagerNameJQuery,
			viewrecords : true,
			caption : gridP16Movimientos.title,
			altclass: "ui-priority-secondary",
			altRows: true, //false, para que el grid no muestre cebrado
			hidegrid : false, //false, para ocultar el boton que colapsa el grid.
			sortable : true,
			footerrow: true,
			index: gridP16Movimientos.sortIndex,
			sortname: gridP16Movimientos.sortIndex,
			sortorder: gridP16Movimientos.sortOrder,
			emptyrecords : gridP16Movimientos.emptyRecords,
			gridComplete : function() {

			},
			loadComplete : function(data) {
				gridP16Movimientos.actualPage = data.page;
				gridP16Movimientos.localData = data;
				gridP16Movimientos.sortIndex = null;
				gridP16Movimientos.sortOrder = null;
				
				reloadDataP16Movimientos(gridP16Movimientos);

				var stockInicial = $("#p16_fld_stockInicial").val();
				var entradas = $("#p16_fld_entradas").val();
				var ventaTarifa = $("#p16_fld_ventaTarifa").val();
				var ventaPromocional = $("#p16_fld_ventaPromocional").val();
				var ventaForzada = $("#p16_fld_ventaForzada").val();
				var modifAjuste = $("#p16_fld_modifAjuste").val();
				var modifRegul = $("#p16_fld_modifRegul").val();
				var stockFinal = $("#p16_fld_stockFinal").val();
				var centroParametrizado = $("#p16_fld_centroParametrizado").val();

				if (centroParametrizado==1
					&& stockInicial != null && typeof(stockInicial) != 'undefined'
					&& stockFinal != null && typeof(stockFinal) != 'undefined'
					&& stockInicial != stockFinal
					){
					$(this).jqGrid("footerData", "set", {fechaGen: "HOY", stockInicial: stockInicial
						  , entradas: entradas, salidas: ventaTarifa, salidasPromo: ventaPromocional, salidasForz: ventaForzada
						  , ajusteConteo: modifAjuste, regular: modifRegul, stockFinal: stockFinal}
						  );
				}else{
					// eliminar el DIV del footerData ya que si entra
					// en esta rama es porque se habrá dado algún error.
					$("div.ui-jqgrid-sdiv").remove();
				}
				$("div.ui-jqgrid-bdiv").before($("div.ui-jqgrid-sdiv"));
			},
			onPaging : function(postdata) {			
				alreadySorted = false;
				gridP16Movimientos.sortIndex = null;
				gridP16Movimientos.sortOrder = null;
				gridP16Movimientos.saveColumnState.call($(this), this.p.remapColumns);
				reloadDataP16Movimientos(gridP16Movimientos);
				return 'stop';
			},
			resizeStop: function () {
				gridP16Movimientos.saveColumnState.call($(this),gridP16Movimientos.myColumnsState);
            },
			onSelectRow: function(id){

			},
			onSortCol : function (index, columnIndex, sortOrder){
				gridP16Movimientos.sortIndex = index;
				gridP16Movimientos.sortOrder = sortOrder;
				gridP16Movimientos.saveColumnState.call($(this), this.p.remapColumns);
				reloadDataP16Movimientos(gridP16Movimientos);
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
		
		/*$(gridP16Movimientos.nameJQuery).jqGrid('setGroupHeaders', {
			  useColSpanStyle: true, 
			  groupHeaders:[
				{startColumnName: "tarifa", numberOfColumns: 3, titleText: "Salidas"}
			  ]
		});*/	
		
		$('#jqgh_gridP16Movimientos_salidasForz').attr('title', gridP16Movimientos.titleVentaForzada);
		$('#jqgh_gridP16Movimientos_ajusteConteo').attr('title', gridP16Movimientos.titleModifPorAjuste);
		$('#jqgh_gridP16Movimientos_regular').attr('title', gridP16Movimientos.titleModifPorRegularizacion);
}


function reloadDataP16Movimientos(grid) {

		var movimientoStock = new MovimientoStock ( 
									$("#centerId").val() , 
									$("#p13_fld_referenciaEroski").val());
		if (grid.firstLoad) {
	        grid.firstLoad = false;
	        if (grid.isColState) {
	            $(this).jqGrid("remapColumns", grid.myColumnsState.permutation, true);
	        }
	    } else {
	    	grid.myColumnsState = grid.restoreColumnState(grid.cm);
	    	grid.isColState = typeof (grid.myColumnsState) !== 'undefined' && grid.myColumnsState !== null;
	    }
		var objJson = $.toJSON(movimientoStock.prepareToJsonObject_2());
			$("#p16_AreaMovimientos .loading").css("display", "block");
			$.ajax({
				type : 'POST',			
				url : './referenciasCentro/movimientoStockDetalle.do?page='+grid.getActualPage()+'&max='+grid.getRowNumPerPage()+'&index='+grid.getSortIndex()+'&sortorder='+grid.getSortOrder(),
				data : objJson,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {
					$(grid.nameJQuery)[0].addJSONData(data);
					grid.actualPage = data.page;
					grid.localData = data;

//					var allRecords = data.rows;

					$("#p16_AreaMovimientos .loading").css("display", "none");	
					$("#p16_AreaMovimientos").css("height", $("#p16_AreaMovimientos .ui-jqgrid").css("height"));
					
				},
				error : function (xhr, status, error){
					$("#p16_AreaMovimientos .loading").css("display", "none");	
					handleError(xhr, status, error, locale);				
		        }			
			});		
		
}

/*Clase de constantes para el GRID de NSR*/
function GridP16NSR (locale){
	// Atributos
	this.name = "gridP16NSR"; 
	this.nameJQuery = "#gridP16NSR"; 
	this.i18nJSON = './misumi/resources/p16ReferenciasCentroM/p16referenciasCentroMNSR_' + locale + '.json';
	this.colNames = null;
	this.cm = [
		   		{
					"name"  : "fechaNsr",
					"index" : "fechaNsr",
					"width" : 100,
					"formatter":"date",
					"formatoptions":{
				    	"srcformat": "Y/m/d",
				    	"newformat": "d/m/Y"
				    }	
		        },{
					"name"  : "uniNoServ",
					"index" : "uniNoServ", 
					"formatter":"number",
					"width" : 100
				},{
					"name"  : "motivoDes",
					"index" : "motivoDes", 
					"width" : 350,
					"classes" : "NSRMotivoResaltado"
				},{
					"name"  : "codPlat",
					"index" : "codPlat", 
					"width" : 100
				}
			];
	this.sortIndex = "FECHANSR";
	this.sortOrder = "desc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP16NSR"; 
	this.pagerNameJQuery = "#pagerP16NSR";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;
	 this.myColumnStateName = 'gridP16NSR.colState';
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
	
	this.clearGrid = function clearGrid() {
		$(this.nameJQuery).jqGrid('GridUnload');
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
        var colModel = jQuery(gridP16NSR.nameJQuery).jqGrid('getGridParam', 'colModel'); 
        var i;
        var l = colModel.length;
        var colItem; 
        var cmName;
        var postData = jQuery(gridP16NSR.nameJQuery).jqGrid('getGridParam', 'postData');
        var columnsState = {
                search: jQuery(gridP16NSR.nameJQuery).jqGrid('getGridParam', 'search'),
                page: jQuery(gridP16NSR.nameJQuery).jqGrid('getGridParam', 'page'),
                sortname: jQuery(gridP16NSR.nameJQuery).jqGrid('getGridParam', 'sortname'),
                sortorder: jQuery(gridP16NSR.nameJQuery).jqGrid('getGridParam', 'sortorder'),
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
	
/*Clase de constantes para el GRID de movimientos*/
function GridP16Movimientos (locale){
	// Atributos
	this.name = "gridP16Movimientos"; 
	this.nameJQuery = "#gridP16Movimientos"; 
	this.i18nJSON = './misumi/resources/p16ReferenciasCentroM/p16referenciasCentroMMovimientos_' + locale + '.json';
	this.colNames = null;
	this.cm =[
		   		{
					"name"  : "fechaGen",
					"index" : "fechaGen",
					"width" : 80,
					"formatter":"date",
					"formatoptions":{
				    	"srcformat": "Y/m/d",
				    	"newformat": "d/m/Y"
				    }	
				},{
					"name"  : "stockInicial",
					"index" : "stockInicial", 
					"formatter":"number",
					"width" : 90
		   		},{
					"name"  : "entradas",
					"index" : "entradas", 
					"formatter":"number",
					"width" : 80
				},{
					"name"  : "salidas",
					"index" : "salidas",
					"formatter":"number",
					"width" : 100
				},{
					"name"  : "salidasPromo",
					"index" : "salidasPromo",
					"formatter":"number",
					"width" : 140
				},{
					"name"  : "salidasForz",
					"index" : "salidasForz",
					"formatter":"number",
					"width" : 110
				},{
					"name"  : "ajusteConteo",
					"index" : "ajusteConteo",
					"formatter":"number",
					"width" : 130
				},{
					"name"  : "regular",
					"index" : "regular", 
					"formatter":"number",
					"width" : 190
				},{
					"name"  : "stockFinal",
					"index" : "stockFinal", 
					"formatter":"number",
					"width" : 90
				}
			];
	this.sortIndex = "FECHAGEN";
	this.sortOrder = "desc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP16Movimientos"; 
	this.pagerNameJQuery = "#pagerP16Movimientos";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;
	this.titleVentaForzada = null;
	this.titleModifPorAjuste = null;
	this.titleModifPorRegularizacion = null;
	 this.myColumnStateName = 'gridP16Movimientos.colState';
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
	
	this.clearGrid = function clearGrid() {
		$(this.nameJQuery).jqGrid('GridUnload');
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
        var colModel = jQuery(gridP16Movimientos.nameJQuery).jqGrid('getGridParam', 'colModel'); 
        var i;
        var l = colModel.length;
        var colItem; 
        var cmName;
        var postData = jQuery(gridP16Movimientos.nameJQuery).jqGrid('getGridParam', 'postData');
        var columnsState = {
                search: jQuery(gridP16Movimientos.nameJQuery).jqGrid('getGridParam', 'search'),
                page: jQuery(gridP16Movimientos.nameJQuery).jqGrid('getGridParam', 'page'),
                sortname: jQuery(gridP16Movimientos.nameJQuery).jqGrid('getGridParam', 'sortname'),
                sortorder: jQuery(gridP16Movimientos.nameJQuery).jqGrid('getGridParam', 'sortorder'),
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