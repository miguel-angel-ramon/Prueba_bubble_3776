var gridP45PedidoAdicionalEmpuje=null;
var tableFilter = null;
var p45EstadoNoActiva = null;
var p45ConsultaMotivosBloqueo = null;

function initializeP45(){
		initializeScreenPedidoAdicionalEmpuje();
}

function initializeScreenPedidoAdicionalEmpuje(){
	loadP45PedidoAdicionalEmpuje(locale);
}

function resetResultadosPedidoAdicionalEmpuje(){
	
	if (gridP45PedidoAdicionalEmpuje != null) {
		gridP45PedidoAdicionalEmpuje.clearGrid();
	}
	
}

function resetDatosP45(){
	$('#gridP45Empuje').jqGrid('clearGridData');
}

function reloadEmpuje() {

	$("#p40_pestanaEmpuje").val("S");
	
	reloadDataP45PedidoAdicionalEmpuje(gridP45PedidoAdicionalEmpuje);
}

function loadP45PedidoAdicionalEmpuje(locale){
	gridP45PedidoAdicionalEmpuje = new GridP45PedidoAdicionalEmpuje(locale);
	
	var jqxhr = $.getJSON(gridP45PedidoAdicionalEmpuje.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				gridP45PedidoAdicionalEmpuje.colNames = data.p45PedidoAdicionalEmpujeColNames;
				gridP45PedidoAdicionalEmpuje.title = data.p45PedidoAdicionalEmpujeGridTitle;
				gridP45PedidoAdicionalEmpuje.emptyRecords= data.emptyRecords;
				tableFilter = data.tableFilter;
				p45EstadoNoActiva = data.p45EstadoNoActiva;
				p45ConsultaMotivosBloqueo = data.p45ConsultaMotivosBloqueo;
				loadP45PedidoAdicionalEmpujeMock(gridP45PedidoAdicionalEmpuje);
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
         });
}

function loadP45PedidoAdicionalEmpujeMock(gridP45PedidoAdicionalEmpuje) {
	$(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		datatype : 'local',
		contentType : 'application/json',
		mtype : 'POST',
		colNames : gridP45PedidoAdicionalEmpuje.colNames,
		colModel :gridP45PedidoAdicionalEmpuje.cm, 
		rowNum : 10,
		rowList : [ 10, 20, 30 ],
		height : "100%",
		autowidth : true,
		width : "auto",
		rownumbers : true,
		pager : gridP45PedidoAdicionalEmpuje.pagerNameJQuery,
		viewrecords : true,
		caption : gridP45PedidoAdicionalEmpuje.title,
		altclass: "ui-priority-secondary",
		altRows: true, //false, para que el grid no muestre cebrado
		hidegrid : false, //false, para ocultar el boton que colapsa el grid.
		sortable : true,
		index: gridP45PedidoAdicionalEmpuje.sortIndex,
		sortname: gridP45PedidoAdicionalEmpuje.sortIndex,
		sortorder: gridP45PedidoAdicionalEmpuje.sortOrder,
		emptyrecords : gridP45PedidoAdicionalEmpuje.emptyRecords,
		gridComplete : function() {
			
			
		},
		loadComplete : function(data) {
			gridP45PedidoAdicionalEmpuje.actualPage = data.page;
			gridP45PedidoAdicionalEmpuje.localData = data;
			gridP45PedidoAdicionalEmpuje.sortIndex = null;
			gridP45PedidoAdicionalEmpuje.sortOrder = null;
			if (gridP45PedidoAdicionalEmpuje.firstLoad){
				jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid('setGridWidth',$("#p45_AreaMontajeAdicionalEmpuje").width(),true);
			}
			//reloadDataP45PedidoAdicionalEmpuje(gridP45PedidoAdicionalEmpuje);
		},
		onPaging : function(postdata) {			
			alreadySorted = false;
			gridP45PedidoAdicionalEmpuje.sortIndex = null;
			gridP45PedidoAdicionalEmpuje.sortOrder = null;
			reloadDataP45PedidoAdicionalEmpuje(gridP45PedidoAdicionalEmpuje);
			return 'stop';
		},
		resizeStop: function () {
			gridP45PedidoAdicionalEmpuje.modificado = true;
			gridP45PedidoAdicionalEmpuje.saveColumnState.call($(this),gridP45PedidoAdicionalEmpuje.myColumnsState);
			jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid('setGridWidth', $("#p45_AreaMontajeAdicionalEmpuje").width(), false);
        },
		onSelectRow: function(id){

		},
		onSortCol : function (index, columnIndex, sortOrder){
			gridP45PedidoAdicionalEmpuje.sortIndex = index;
			gridP45PedidoAdicionalEmpuje.sortOrder = sortOrder;
			reloadDataP45PedidoAdicionalEmpuje(gridP45PedidoAdicionalEmpuje);
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
	jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid('navGrid',gridP45PedidoAdicionalEmpuje.pagerNameJQuery,{
		add:false,edit:false,del:false,search:false,refresh:false}
	); 
	
	jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid('navButtonAdd',gridP45PedidoAdicionalEmpuje.pagerNameJQuery,{ 
		caption: tableFilter, title: "Reordenar Columnas", 
	onClickButton : function (){ 
		jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid('columnChooser',
				{
            		"done": function(perm) {
            			if (perm) {
            				var autowidth = true;
            				if (gridP45PedidoAdicionalEmpuje.modificado == true){
            					autowidth = false;
            					gridP45PedidoAdicionalEmpuje.myColumnsState =  gridP45PedidoAdicionalEmpuje.restoreColumnState(gridP45PedidoAdicionalEmpuje.cm);
            					gridP45PedidoAdicionalEmpuje.isColState = typeof (gridP45PedidoAdicionalEmpuje.myColumnsState) !== 'undefined' && gridP45PedidoAdicionalEmpuje.myColumnsState !== null;
                		    	this.jqGrid("remapColumns", perm, true);
                		    	 var colModel =jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid('getGridParam', 'colModel'); 
                		    	 var l = colModel.length;
                		         var colItem; 
                		         var cmName;
                		         var colStates = gridP45PedidoAdicionalEmpuje.myColumnsState.colStates;
                		         var cIndex = 2;
                		         for (i = 0; i < l; i++) {
                		             colItem = colModel[i];
                		             cmName = colItem.name;
                		             if (colItem.hidden !== true && cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {
                		            	 
                		            	 jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid('setColProp',cmName,{width:colStates[cmName].width});
                		            	 var cad =gridP45PedidoAdicionalEmpuje.nameJQuery+'_'+cmName;
                		            	 var ancho = "'"+colStates[cmName].width+"px'";
                		            	 var cell = jQuery('table'+gridP45PedidoAdicionalEmpuje.nameJQuery+' tr.jqgfirstrow td:nth-child(' + (i+1) + ')');
                		            	 cell.css("width", colStates[cmName].width + "px");
                		            	
                		            	 jQuery(cad).css("width", colStates[cmName].width + "px");
                		            	
                		             }
                		         }
                		         
            				} else {
            					this.jqGrid("remapColumns", perm, true);
            				}
            				gridP45PedidoAdicionalEmpuje.saveColumnState.call(this, perm);
            				jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid('setGridWidth', $("#p45_AreaMontajeAdicionalEmpuje").width(), autowidth);
            			}
            		}
				}		
		); } });
}

function reloadDataP45PedidoAdicionalEmpuje(grid) {

	if (grid.firstLoad) {
		jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#p45_AreaMontajeAdicionalEmpuje").width(), true);
		grid.firstLoad = false;
		if (grid.isColState) {
			$(this).jqGrid("remapColumns", grid.myColumnsState.permutation,true);
		}
	} else {
		grid.myColumnsState = grid.restoreColumnState(grid.cm);
		grid.isColState = typeof (grid.myColumnsState) !== 'undefined'
				&& grid.myColumnsState !== null;
		jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#p45_AreaMontajeAdicionalEmpuje").width(), false);
	}


	var codArea = null;
	var codSeccion = null;
	var mca = "N";
	if ($("#p40_cmb_seccion").combobox('getValue') != null && $("#p40_cmb_seccion").combobox('getValue') != '' && $("#p40_cmb_seccion").combobox('getValue') != 'null') {
		valorSeccion = $("#p40_cmb_seccion").combobox('getValue').split('*');
		codArea = valorSeccion[0];
		codSeccion = valorSeccion[1];
	}
	var codCategoria = null;
	if($("#p40_cmb_categoria").combobox('getValue') != 'null' && $("#p40_cmb_categoria").combobox('getValue') != null && $("#p40_cmb_categoria").combobox('getValue') != ''){
		codCategoria = $("#p40_cmb_categoria").combobox('getValue');
	}
	if($("#p40_chk_mca").is(':checked')) {
		mca = "S";
	}
	var recordPedidoAdicionalEM = new PedidoAdicionalEM();

	recordPedidoAdicionalEM.codCentro = $("#centerId").val();
	recordPedidoAdicionalEM.grupo1 = codArea;
	recordPedidoAdicionalEM.grupo2 = codSeccion;
	recordPedidoAdicionalEM.grupo3 = codCategoria;
	//recordPedidoAdicionalEM.grupo3 = $("#p40_cmb_categoria").combobox('getValue');
	recordPedidoAdicionalEM.codArticulo = $("#p40_fld_referencia").val();
	recordPedidoAdicionalEM.clasePedido = 6;
	recordPedidoAdicionalEM.mca = mca;
	// Reseteamos el array de seleccionados.

	
	

	var objJson = $.toJSON(recordPedidoAdicionalEM.preparePedidoAdicionalEMToJsonObject());

	$("#AreaResultados .loading").css("display", "block");

	$.ajax({
		type : 'POST',
		url : './pedidoAdicionalEmpuje/loadDataGridEM.do?page='+ grid.getActualPage() + '&max=' + grid.getRowNumPerPage()+ '&index=' + grid.getSortIndex() + '&sortorder=' + grid.getSortOrder(),
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
			$(grid.nameJQuery)[0].addJSONData(data.datos);
			grid.actualPage = data.datos.page;
			grid.localData = data.datos;
			$("div#p40_AreaPestanas").attr("style", "display:block");
			var ids = jQuery(grid.nameJQuery).jqGrid('getDataIDs'), i, l = ids.length;
			for (i = 0; i < l; i++) {
				$(grid.nameJQuery).find("#" + (ids[i])).find("td").addClass("p45_columnaResaltada");
			}
			
		},
		error : function(xhr, status, error) {
			$("#AreaResultados .loading").css("display", "none");
			handleError(xhr, status, error, locale);
		}
	});
	
	hideShowColumnsEmpuje();
}

/*Clase de constantes para el GRID de Pedido adicional oferta*/
function GridP45PedidoAdicionalEmpuje (locale){
		// Atributos
		this.name = "gridP45Empuje"; 
		this.nameJQuery = "#gridP45Empuje"; 
		this.i18nJSON = './misumi/resources/p45PedidoAdicionalEmpuje/p45pedidoAdicionalEmpuje_' + locale + '.json';
		this.colNames = null;
		this.cm  = [
			   		{
			   			"name" : "mensaje",
						"index": "mensaje", 
						"formatter": p45EstadoFormatMessage,
						"fixed":true,
						"width" : 75,
						"sortable" : true
					},{			   			
						"name"  : "tipoAprovisionamiento",
						"index" : "tipoAprovisionamiento", 
						"width" : 50,
						"hidden" : true,
						"sortable" : true
			   		},{
						"name"  : "codArticuloGrid",
						"index" : "codArticuloGrid",
						"width" : 65,
						"sortable" : true
	                },{
						"name"  : "descriptionArtGrid",
						"index" : "descriptionArtGrid",
						"width" : 230,
						"sortable" : true
			   		},{
						"name"  : "uniCajaServ",
						"index" : "uniCajaServ",
						"formatter" : p45FormateoUnidadesCaja,
						"hidden" : true,
						"width" : 50,
						"sortable" : true
			   		},{
						"name"  : "fechaInicio",
						"index" : "fechaInicio",
						"formatter" : p45FormateoDate,
						"width" : 60,
						"sortable" : true
			   		},{
						"name"  : "fechaFin",
						"index" : "fechaFin",
						"formatter" : p45FormateoDate,
						"width" : 60,
						"sortable" : true
			   		},{
						"name"  : "cantidad1",
						"index" : "cantidad1", 
						"formatter" : p45FormateoCantidades,
						"width" : 80,
						"sortable" : true
			   		},{
						"name"  : "cantidad2",
						"index" : "cantidad2", 
						"formatter" : p45FormateoCantidades,
						"width" : 60,
						"sortable" : true
			   		},{
						"name"  : "cantidad3",
						"index" : "cantidad3", 
						"formatter" : p45FormateoCantidades,
						"width" : 60,
						"sortable" : true
			   		},{
						"name"  : "cantidad4",
						"index" : "cantidad4", 
						"formatter" : p45FormateoCantidades,
						"width" : 60,
						"sortable" : true
			   		},{
						"name"  : "cantidad5",
						"index" : "cantidad5", 
						"formatter" : p45FormateoCantidades,
						"width" : 60,
						"sortable" : true
			   		},{
						"name"  : "cantMax",
						"index" : "cantMax", 
						"formatter" : p45FormateoCantidades,
						"width" : 60,
						"hidden" : true,
						"sortable" : true
			   		},{
						"name"  : "cantMin",
						"index" : "cantMin", 
						"formatter" : p45FormateoCantidades,
						"width" : 60,
						"hidden" : true,
						"sortable" : true
			   		},{
						"name"  : "descOferta",
						"index" : "descOferta", 
						"width" : 50,
						"hidden" : true,
						"sortable" : true
					},{
						"name"  : "color",
						"index" : "color", 
						"width" : 30,
						"hidden" : true,
						"sortable" : true
					},{
						"name"  : "talla",
						"index" : "talla", 
						"width" : 30,
						"hidden" : true,
						"sortable" : true
					},{
						"name"  : "modeloProveedor",
						"index" : "modeloProveedor", 
						"width" : 60,
						"hidden" : true,
						"sortable" : true
					}
				];
		this.sortIndex = null;
		this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
		this.pagerName = "pagerP45Empuje"; 
		this.pagerNameJQuery = "#pagerP45Empuje";
		this.title = null;
		this.actualPage = null;
		this.localdata = null;
		this.emptyRecords = null;
		this.myColumnStateName = 'gridP45PedidoAdicionalEM.colState';
		this.myColumnsState = null;
		this.isColState = null;
		this.firstLoad = true;
		this.modificado = false;

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
			var rowNumPerPage = $(this.nameJQuery).getGridParam('rowNum');
			if (rowNumPerPage!=null && rowNumPerPage!== undefined){
				return rowNumPerPage;
			}else{
				return 10;
			}
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
		
		this.saveObjectInLocalStorage = function(storageItemName, object) {
			if (typeof window.localStorage !== 'undefined') {
				window.localStorage.setItem(storageItemName, JSON.stringify(object));
			}
		}

		this.removeObjectFromLocalStorage = function(storageItemName) {
			if (typeof window.localStorage !== 'undefined') {
				window.localStorage.removeItem(storageItemName);
			}
		}

		this.getObjectFromLocalStorage = function(storageItemName) {
			if (typeof window.localStorage !== 'undefined') {
				return JSON.parse(window.localStorage.getItem(storageItemName));
			}
		}

		this.saveColumnState = function(perm) {
			var colModel = jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid(
					'getGridParam', 'colModel');
			// if (colModel == null)
			// colModel = grid.cm;
			var i;
			var l = colModel.length;
			var colItem;
			var cmName;
			var postData = jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid(
					'getGridParam', 'postData');
			var columnsState = {
				search : jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid(
						'getGridParam', 'search'),
				page : jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid(
						'getGridParam', 'page'),
				sortname : jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid(
						'getGridParam', 'sortname'),
				sortorder : jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid(
						'getGridParam', 'sortorder'),
				permutation : perm,
				colStates : {}
			};
			var colStates = columnsState.colStates;

			if ((typeof (postData) !== 'undefined')
					&& (typeof (postData.filters) !== 'undefined')) {
				columnsState.filters = postData.filters;
			}

			for (i = 0; i < l; i++) {
				colItem = colModel[i];
				cmName = colItem.name;
				if (cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {
					colStates[cmName] = {
						width : colItem.width,
						hidden : colItem.hidden
					};
				}
			}

			// this.saveObjectInLocalStorage(this.myColumnStateName, columnsState);
			if (typeof window.localStorage !== 'undefined') {
				window.localStorage.setItem(
						gridP45PedidoAdicionalEmpuje.myColumnStateName, JSON
								.stringify(columnsState));
			}
		}

		this.restoreColumnState = function(colModel) {
			var colItem, i, l = colModel.length, colStates, cmName;
			var columnsState = this
					.getObjectFromLocalStorage(this.myColumnStateName);
			if (columnsState) {
				colStates = columnsState.colStates;
				for (i = 0; i < l; i++) {
					colItem = colModel[i];
					cmName = colItem.name;
					if (cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {
						colModel[i] = $.extend(true, {}, colModel[i],
								colStates[cmName]);
					}
				}
			}
			return columnsState;
		}
		
		this.clearGrid = function clearGrid() {
			$(this.nameJQuery).jqGrid('GridUnload');
		}
}

function p45FormateoDate(cellValue, opts, rowObject) {

	var fechaFormateada = '';
	if (cellValue != null)
	{
		
		var diaFecha = parseInt(cellValue.substring(0, 2), 10);
		var mesFecha = parseInt(cellValue.substring(2, 4), 10);
		var anyoFecha = parseInt(cellValue.substring(4), 10);
	
		fechaFormateada = $.datepicker.formatDate("D dd-M", new Date(anyoFecha,
				mesFecha - 1, diaFecha), {
			dayNamesShort : $.datepicker.regional["es"].dayNamesShort,
			dayNames : $.datepicker.regional["es"].dayNames,
			monthNamesShort : $.datepicker.regional["es"].monthNamesShort,
			monthNames : $.datepicker.regional["es"].monthNames
		});
	}

	return (fechaFormateada);

}

function p45FormateoCantidades(cantidad) {
	
	if (null != cantidad && cantidad != '')
	{
		//Actualizamos el formateo de los campos a dos decimales
		return $.formatNumber(cantidad,{format:"0.##",locale:"es"});
	}
	else
	{
		return '';
	}
}

function p45FormateoUnidadesCaja(cantidad) {
	
	if (cantidad != '')
	{
		//Actualizamos el formateo de los campos a dos decimales
		return $.formatNumber(cantidad,{format:"0.##",locale:"es"});
	}
	else
	{
		return '';
	}
}

function p45EstadoFormatMessage(cellValue, opts, rData) {
	
	var mensaje = "";
	var mostrarNoActiva = "none;";
	var p45EstadoNoActivaValor = "";
	//Añadimos el literal de no activa en caso de que esté bloquedo
	if (rData['estado'] != '' && rData['estado'] == "NOACT")
	{
		mostrarNoActiva = "block";
		p45EstadoNoActivaValor = p45EstadoNoActiva;
	}

	mensaje += "<div id='"+opts.rowId+"_divNoActiva' align='center' style='display: " + mostrarNoActiva + "'><a href='#' id='"+opts.rowId+"_p45_estado' class='p45Bloqueada' onclick='javascript:p45ReloadMotivosBloqueo("+opts.rowId+")' title='"+p45ConsultaMotivosBloqueo+"'>"+p45EstadoNoActivaValor+"</a></div>"; //No Activa
	
	//Añadimos los valores de la columna referencia ocultos para poder utilizarlos posteriormente.
	var datoCodArticulo = "<input type='hidden' id='"+opts.rowId+"_codArticuloEM' value='"+rData['codArticulo']+"'>";
	mensaje +=  datoCodArticulo;
	
	//Añadimos los valores de la columna tipo de pedido ocultos para poder utilizarlos posteriormente.
	var datoTipoPedido = "<input type='hidden' id='"+opts.rowId+"_tipoPedidoEM' value='"+rData['tipoPedido']+"'>";
	mensaje +=  datoTipoPedido;

	//Añadimos los valores de la columna fecha inicio con formato ocultos para poder utilizarlos posteriormente.
	var datofechaInicio = "<input type='hidden' id='"+opts.rowId+"_fechaInicioEM' value='"+(rData['fechaInicio']!=null?rData['fechaInicio']:'')+"'>";
	mensaje +=  datofechaInicio;
	
	//Añadimos los valores de la columna fecha2 con formato ocultos para poder utilizarlos posteriormente.
	var datofecha2 = "<input type='hidden' id='"+opts.rowId+"_fecha2EM' value='"+(rData['fecha2']!=null?rData['fecha2']:'')+"'>";
	mensaje +=  datofecha2;

	//Añadimos los valores de la columna fecha3 con formato ocultos para poder utilizarlos posteriormente.
	var datofecha3 = "<input type='hidden' id='"+opts.rowId+"_fecha3EM' value='"+(rData['fecha3']!=null?rData['fecha3']:'')+"'>";
	mensaje +=  datofecha3;

	//Añadimos los valores de la columna fecha4 con formato ocultos para poder utilizarlos posteriormente.
	var datofecha4 = "<input type='hidden' id='"+opts.rowId+"_fecha4EM' value='"+(rData['fecha4']!=null?rData['fecha4']:'')+"'>";
	mensaje +=  datofecha4;

	//Añadimos los valores de la columna fecha5 con formato ocultos para poder utilizarlos posteriormente.
	var datofecha5 = "<input type='hidden' id='"+opts.rowId+"_fecha5EM' value='"+(rData['fecha5']!=null?rData['fecha5']:'')+"'>";
	mensaje +=  datofecha5;

	//Añadimos los valores de la columna fechaInPil con formato ocultos para poder utilizarlos posteriormente.
	var datofechaInPil = "<input type='hidden' id='"+opts.rowId+"_fechaInPilEM' value='"+(rData['fechaPilada']!=null?rData['fechaPilada']:'')+"'>";
	mensaje +=  datofechaInPil;
	
	//Añadimos los valores de la columna fecha fin con formato ocultos para poder utilizarlos posteriormente.
	var datofechaFin = "<input type='hidden' id='"+opts.rowId+"_fechaFinEM' value='"+(rData['fechaFin']!=null?rData['fechaFin']:'')+"'>";
	mensaje +=  datofechaFin;
	
	//Añadimos los valores para el bloqueo
	var estado = "<input type='hidden' id='"+opts.rowId+"_estadoEM' value='"+rData['estado']+"'>";
	mensaje +=  estado;
	
	//Añadimos los valores para el bloqueo
	var noGestionaPbl = "<input type='hidden' id='"+opts.rowId+"_noGestionaPblEM' value='"+rData['noGestionaPbl']+"'>";
	mensaje +=  noGestionaPbl;
	
	return mensaje;
}

function p45ReloadMotivosBloqueo(rowid){
	var tipoPedido = $("#"+(rowid)+"_tipoPedidoEM").val();
	var codTpBloqueo = "";
	//Control de tipo de bloqueo a controlar
	if ("P" == tipoPedido){
		codTpBloqueo = "P";
	}else if ("E" == tipoPedido){
		if ($("#"+(rowid)+"_fechaInPilEM").val() != null && $("#"+(rowid)+"_fechaInPilEM").val()!=''){
			codTpBloqueo = "EP";
		}else{
			codTpBloqueo = "E";
		}
	}
	reloadPopupP74($("#"+(rowid)+"_codArticuloEM").val(), codTpBloqueo, $("#"+(rowid)+"_fechaInicioEM").val(), $("#"+(rowid)+"_fecha2EM").val(), $("#"+(rowid)+"_fecha3EM").val(), $("#"+(rowid)+"_fecha4EM").val(), $("#"+(rowid)+"_fecha5EM").val(), $("#"+(rowid)+"_fechaInPilEM").val(), $("#"+(rowid)+"_fechaFinEM").val(), "N", "6");
	//reloadPopupP74_2($("#"+(rowid)+"_codArticuloEM").val(), codTpBloqueo, $("#"+(rowid)+"_fechaInicioEM").val(), $("#"+(rowid)+"_fecha2EM").val(), $("#"+(rowid)+"_fecha3EM").val(), $("#"+(rowid)+"_fecha4EM").val(), $("#"+(rowid)+"_fecha5EM").val(), $("#"+(rowid)+"_fechaInPilEM").val(), $("#"+(rowid)+"_fechaFinEM").val(), "N", "6");
}

function hideShowColumnsEmpuje(){
	
	codArea = null;
	codSeccion = null;
	if ($("#p40_cmb_seccion").combobox('getValue') != null&& $("#p40_cmb_seccion").combobox('getValue') != '') {
		valorSeccion = $("#p40_cmb_seccion").combobox('getValue').split('*');
		codArea = valorSeccion[0];
		codSeccion = valorSeccion[1];
		if(codArea==3){
			//Si es textil, se cambia el nombre de las coumnas cantidad1, cantidad2, cantidad3, cantidad4 por C 1, C 2, C 3, C 4
			jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid('setLabel', 'cantidad1', 'C 1');
			jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid('setLabel', 'cantidad2', 'C 2');
			jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid('setLabel', 'cantidad3', 'C 3');
			jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid('setLabel', 'cantidad4', 'C 4');
			jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid('setLabel', 'cantidad5', 'C 5');
			//Añadir columna nueva
			jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid('showCol',["color","talla","modeloProveedor"]);
			jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid('setGridWidth',$("#p45_AreaMontajeAdicionalEmpuje").width(),false);
			//COLOR, TALLA, MODELO, PROVEEDOR
			
			
		}else{
			
			//Se restablecen los nombre de las cantidades
			jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid('setLabel', 'cantidad1', 'Cantid. 1');
			jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid('setLabel', 'cantidad2', 'Cantid. 2');
			jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid('setLabel', 'cantidad3', 'Cantid. 3');
			jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid('setLabel', 'cantidad4', 'Cantid. 4');
			jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid('setLabel', 'cantidad5', 'Cantid. 5');
			
			jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid('hideCol',["color","talla","modeloProveedor"]);
			jQuery(gridP45PedidoAdicionalEmpuje.nameJQuery).jqGrid('setGridWidth',$("#p45_AreaMontajeAdicionalEmpuje").width(),false);
			
			
		}
	}
	
	
}