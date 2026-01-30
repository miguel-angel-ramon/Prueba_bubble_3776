var emptyRecords=null;
var gridP75DetalladoPedidoSinOferta=null;
var gridP75DetalladoPedidoSinOfertaTextil=null;
var noDataForCenter=null;
var mapP75 = new Map;
var boxesIncorrect=null;
var activaPantalla=0;
var selectReference=null;
var referencePreviouslyInserted=null;
var errorRedondeoCantidadInsert=null;
var agrupacionNoPermitida = null;
var referenceIncluded=null;
var ischanged=false;
var listadoModificadosP75 = new Array();
var literalCajas=null;
var saveResultOK = null;
var saveResultError = null;
var saveResultNoDatos = null;
var referenceEmpty = null;
var refsDeListado = null;
var tableFilter=null;

var numRow=1;
var invalidCantidadCarne = null;
var literalSinOferta=null;
var literalOferta=null;
var literalAyuda1ConOferta = null;
var literalAyuda1SinOferta = null;
var filaAyuda = null;

var area = null;

function initializeP75(){
	loadP75SinOferta(locale);
}

function resetDatosP75(){
	$('#gridP75PedidoDatosSinOferta').jqGrid('clearGridData');
}

function resetDatosP75Save(){
	//Al estar guardando, queremos borrar los datos del grid, pero mantenerlo luego en la hoja, ordenacion, etc. que estaba.
	var actualPage = gridP75DetalladoPedidoSinOferta.getActualPage();
	var max= gridP75DetalladoPedidoSinOferta.getRowNumPerPage();
	var index = gridP75DetalladoPedidoSinOferta.getSortIndex();
	var sortOrder = gridP75DetalladoPedidoSinOferta.getSortOrder();

	//Borramos los datos del grid.
	$('#gridP75PedidoDatosSinOferta').jqGrid('clearGridData');

	//Introducimos los datos de paginacion, etc. anteriores.
	$('#gridP75PedidoDatosSinOferta').setGridParam({page:actualPage});
	$('#gridP75PedidoDatosSinOferta').setGridParam({max:max});
	$('#gridP75PedidoDatosSinOferta').setGridParam({index:index});
	$('#gridP75PedidoDatosSinOferta').setGridParam({sortOrder:sortOrder});
}

function resetDatosP75Textil(){
	$('#gridP75PedidoDatosSinOfertaTextil').jqGrid('clearGridData');
}

function resetDatosP75TextilSave(){
	//Al estar guardando, queremos borrar los datos del grid, pero mantenerlo luego en la hoja, ordenacion, etc. que estaba.

	var actualPage = gridP75DetalladoPedidoSinOfertaTextil.getActualPage();
	var max= gridP75DetalladoPedidoSinOfertaTextil.getRowNumPerPage();
	var index = gridP75DetalladoPedidoSinOfertaTextil.getSortIndex();
	var sortOrder = gridP75DetalladoPedidoSinOfertaTextil.getSortOrder();

	//Borramos los datos del grid.
	$('#gridP75PedidoDatosSinOfertaTextil').jqGrid('clearGridData');

	//Introducimos los datos de paginacion, etc. anteriores.
	$('#gridP75PedidoDatosSinOfertaTextil').setGridParam({page:actualPage});
	$('#gridP75PedidoDatosSinOfertaTextil').setGridParam({max:max});
	$('#gridP75PedidoDatosSinOfertaTextil').setGridParam({index:index});
	$('#gridP75PedidoDatosSinOfertaTextil').setGridParam({sortOrder:sortOrder});
}

function reloadDatosP75(saveChanges) {
	reloadGridP75(saveChanges);
}

function loadP75SinOferta(locale){
	gridP75DetalladoPedidoSinOferta = new GridP75DetalladoPedidoSinOferta(locale);
	gridP75DetalladoPedidoSinOfertaTextil = new GridP75DetalladoPedidoSinOfertaTextil(locale);

	var jqxhr = $.getJSON(gridP75DetalladoPedidoSinOferta.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		gridP75DetalladoPedidoSinOferta.colNames = data.p75DetalladoPedidoSinOfertaColNames;
		gridP75DetalladoPedidoSinOferta.title = data.p75DetalladoPedidoSinOfertaGridTitle;
		gridP75DetalladoPedidoSinOferta.emptyRecords= data.emptyRecords;
		gridP75DetalladoPedidoSinOfertaTextil.colNames = data.p75DetalladoPedidoSinOfertaColNamesTextil;
		gridP75DetalladoPedidoSinOfertaTextil.subgridColNames = data.p75DetalladoPedidoSinOfertaColNamesTextil;
		gridP75DetalladoPedidoSinOfertaTextil.title = data.p75DetalladoPedidoSinOfertaGridTitle;
		gridP75DetalladoPedidoSinOfertaTextil.emptyRecords= data.emptyRecords;
		referenceRequired = data.referenceRequired;
		centerRequired = data.centerRequired;
		sectionRequired = data.sectionRequired;
		boxesIncorrect=data.boxesIncorrect;
		literalCajas=data.literalCajas;
		tableFilter= data.tableFilter;
		loadGridStructureP75(gridP75DetalladoPedidoSinOferta);
		loadGridStructureTextilP75(gridP75DetalladoPedidoSinOfertaTextil);
		noDataForCenter =  data.noDataForCenter;
		selectReference=data.selectReference;
		referencePreviouslyInserted=data.referencePreviouslyInserted;
		errorRedondeoCantidadInsert=data.errorRedondeoCantidadInsert;
		agrupacionNoPermitida=data.agrupacionNoPermitida;
		referenceIncluded=data.referenceIncluded;
		referenceEmpty = data.referenceEmpty;
		saveResultOK = data.saveResultOK;
		saveResultError = data.saveResultError;
		saveResultNoDatos = data.saveResultNoDatos;
		refsDeListado = data.refsDeListado;
		invalidCantidadCarne = data.invalidCantidadCarne;
		msgBloqueoAlAlza = data.msgBloqueoAlAlza;
		p75SetHeadersTitles(data);
		p75SetHeadersTitlesTextil(data);
		literalAyuda1ConOferta = data.literalAyuda1ConOferta;
		literalAyuda1SinOferta = data.literalAyuda1SinOferta;
		literalSinOferta = data.literalSinOferta;
		literalOferta = data.literalOferta;
		avisoInterroganteRecalculo = data.avisoInterroganteRecalculo;
		//if ($("#p70_wsResult").val()==null || $("#p70_wsResult").val()==0 ){
		//disableScreen();
		//createAlert(replaceSpecialCharacters(noDataForCenter), "ERROR");
		//}else{
		//enableScreen();
		//}


	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

function loadGridStructureP75(){
	$(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		url : './misumi/resources/mock.json',
		datatype : 'json',
		contentType : 'application/json',
		mtype : 'POST',
		sortable : true,
		colNames : gridP75DetalladoPedidoSinOferta.colNames,
		colModel :[{
			"name"  : "agrupacion",
			"index" : "agrupacion",
			"sortable" : false,
			"width" : 40,
			"formatter" : p75FormatAgrup,
			"editable": false,
			"hidden" : true
		},{
			"name"  : "codMapa",
			"index" : "codMapa", 
			"sortable" : false,
			"width" : 15,
			//"fixed":true,
			"editable": false
		},{
			"name"  : "tipo",
			"index" : "tipo",
			"sortable" : false,
			"formatter" : p75FormatTipo,
			"width" : 25,
			"editable": false
		},{
			"name"  : "codArticulo",
			"index" : "codArticulo",
			"sortable" : false,
			"width" : 40,
			"editable": false
		},{
			"name"  : "descriptionArt",
			"index" : "descriptionArt",
			"sortable" : false,
			"formatter" : p75TooltipDenominacion,
			"width" : 120,
			"editable": false 
		},{
			"name"  : "tipoAprovisionamiento",
			"index" : "tipoAprovisionamiento", 
			"sortable" : false,
			"width" : 15,
			"editable": false
		},{
			"name"  : "stock",
			"index" : "stock", 
			"sortable" : false,
			"formatter":formatNumberDecimal,
			"width" : 25,
			"editable": false
		},{
			"name" : "facing",  //(MISUMI-414)
			"index":"facing", 
			"formatter":"integer",
			"sortable":false,
			"fixed":true,
			"resizable":false,
			"width" : 25
		},{
			"name"  : "enCurso1",
			"index" : "enCurso1", 
			"sortable" : false,
			//"formatter":formatNumberDecimalEnCurso,
			"width" : 20,
			"editable": false
		},{
			"name"  : "enCurso2",
			"index" : "enCurso2",
			"sortable" : false,
			//"formatter":formatNumberDecimalEnCurso,
			"width" : 20,
			"editable": false
		},{
			"index" : "unidadesCaja",
			"name"  : "unidadesCaja",
			"formatter" : p75FormatUnidadesCaja,
			"sortable" : false,
			"width" : 15,
			"editable": false
		},{
			"name"  : "cajasPedidas",
			"index" : "cajasPedidas", 
			"sortable" : false,
			"width" : 22,
			"editable": false
		},{
			"name"  : "propuesta",
			"index" : "propuesta", 
			"sortable" : false,
			"width" : 21,
			"editable": false
		},{
			"name"  : "cantidad", //cantidad modificada
			"index" : "cantidad",
			"sortable" : false,
			editable: true,
			"editoptions":{
				"autocomplete": "off",
				"size" : "3",
				"maxlength":"3",//,
				"dataEvents": [
				               {//control para el keypress
				            	   type: 'keydown',
				            	   fn:controlNavegacionP75
				               },
				               {"type":"focus","fn":function(e) {ischanged=false}
				               },
				               {"type":"change","fn":function(e) {ischanged=true}}
				               ]   
			},

			//formatter:formatNumber,
			"formatter" : p75FormateoCantidad,
			cellattr: function(rowId, val, rawObject) {
			},
			"fixed":true,
			"cellEdit" : true,
			"cellsubmit" : "clientArray",
			"width" : dataFromDetalladoPedidoJSP.isCentroVegalsa() ? 120 : 120
		},{
			"name"  : "motivoPedido",
			"index" : "motivoPedido", 
			"sortable" : false,
			"width" : 60,
			//"fixed":true,
			"editable": false
		},{
			"name"  : "precioCostoFinal",
			"index" : "precioCostoFinal", 
			"formatter" : p75FormateoPrecioCostoFinal,
			"sortable" : false,
			"width" : 21,
			"editable": false
		},{
			"name"  : "unidFlEmpuje",
			"index" : "unidFlEmpuje", 
			"sortable" : false,
			"width" : 21,
			"editable": false
		}
		,{
			"name"  : "fechaEntrega",
			"index" : "fechaEntrega", 
			"sortable" : false,
			"formatter" : p75FormateoFechaEntrega,
			"width" : 55,
			"fixed":true,
			"editable": false
		},{
			"name"  : "nextDayPedido",
			"index" : "nexDayPedido", 
			"sortable" : false,
			"formatter" : p75FormateoDate,
			"width" : 55,
			"fixed":true,
			"editable": false
		},{
			"name"  : "rotacion",
			"index" : "rotacion", 
			"width" : 40,
			"hidden" : true
		},{
			"name"  : "cajasCortadas",
			"index" : "cajasCortadas", 
			"sortable" : false,
			"width" : 31,
			"editable": false
		},{
			name:'incPrevisionVenta',
			index:'incPrevisionVenta', 
			sortable:false,
			width: 31,
			formatter: "number",
			formatoptions: {thousandsSeparator: ".", decimalPlaces: 0, defaultValue: '0%', suffix: "%"},
			editable: false
		},{
			"name"  : "smEstatico",
			"index" : "smEstatico", 
			"sortable" : false,
			"width" : 31,
			"editable": false
		},{
			"name"  : "estadoGrid",
			"index" : "estadoGrid", 
			"sortable" : false,
			"formatter" : p75imageFormatEstado,
			"width" : 30,
			"fixed" : true,
			"editable": false
		}
		
		], 

		pager: gridP75DetalladoPedidoSinOferta.pagerNameJQuery,
		rowNum: 10,
		restoreAfterSelect: false,
		rowList: [10, 20, 30],
		viewrecords: true,
		loadonce: true,
		height : "auto",
		rownumbers : true,
		width : "auto",
		altclass: "ui-priority-secondary",
		altRows: true, //false, para que el grid no muestre cebrado
		autowidth:true,
		shrinkToFit:true,
		localReader : {id:'codArticulo'},
		index:"agrupacion",
		sortname:'agrupacion',
		sortorder:'asc',
		cellsubmit:'clientArray',
		emptyrecords:gridP75DetalladoPedidoSinOferta.emptyRecords,
		caption:gridP75DetalladoPedidoSinOferta.title,
		hidegrid : false, // false, para ocultar el boton que colapsa el grid.
		gridComplete : function() {
			$("#p75_AreaSinOferta .loading").css("display", "none");	
		},
		loadComplete : function(data) {
			gridP75DetalladoPedidoSinOferta.actualPage = data.page;
			gridP75DetalladoPedidoSinOferta.localData = data;
			gridP75DetalladoPedidoSinOferta.sortIndex = null;
			gridP75DetalladoPedidoSinOferta.sortOrder = null;
			$("#p75_AreaSinOferta .loading").css("display", "none");	
			$(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('filterToolbar',{defaultSearch : "cn"});
			$("#gbox_gridP75PedidoDatosSinOferta #gs_estadoGrid").css("display", "none");

		},
		resizeStop: function () {
			gridP75DetalladoPedidoSinOferta.modificado = true;
			saveColumnState(gridP75DetalladoPedidoSinOferta.myColumnsState);
			jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('setGridWidth', $("#p75_AreaSinOferta").width(), false);
		},
		onSortCol : function (index, columnIndex, sortOrder){
			if (activaPantalla!=1){
				return 'stop';
			}else{
				gridP75DetalladoPedidoSinOferta.sortIndex = index;
				gridP75DetalladoPedidoSinOferta.sortOrder = sortOrder;	
				saveColumnState(this.p.remapColumns);
				reloadGridP75Filtros('S');
				return 'stop';
			}
		},
		onPaging : function(postdata) {
			if (activaPantalla=1){
				alreadySorted = false;
				gridP75DetalladoPedidoSinOferta.sortIndex = null;
				gridP75DetalladoPedidoSinOferta.sortOrder = null;
				saveColumnState(this.p.remapColumns);
				reloadGridP75Filtros('S');
				return 'stop';
			}else{
				return 'stop';
			}	
		},
		onSelectRow: function(id){
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
		},
		beforeRequest : function() {
			var postData = jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('getGridParam', 'postData');
			if ((typeof(postData) !== 'undefined') && (typeof (postData.filters) !== 'undefined')) {
				reloadGridP75Filtros('S');
			}
		}
	});

	jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('navGrid',gridP75DetalladoPedidoSinOferta.pagerNameJQuery,{
		add:false,edit:false,del:false,search:false,refresh:false}
	); 
	jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('navButtonAdd',gridP75DetalladoPedidoSinOferta.pagerNameJQuery,{ 
		caption: tableFilter, title: "Reordenar Columnas", 
		onClickButton : function (){ 
			jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('columnChooser',
					{
				"done": function(perm) {
					if (perm) {
						var autowidth = true;
						if (gridP75DetalladoPedidoSinOferta.modificado == true){
							autowidth = false;
							gridP75DetalladoPedidoSinOferta.myColumnsState =  gridP75DetalladoPedidoSinOferta.restoreColumnState(gridP75DetalladoPedidoSinOferta.cm);
							gridP75DetalladoPedidoSinOferta.isColState = typeof (gridP75DetalladoPedidoSinOferta.myColumnsState) !== 'undefined' && gridP75DetalladoPedidoSinOferta.myColumnsState !== null;
							this.jqGrid("remapColumns", perm, true);
							var colModel =jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('getGridParam', 'colModel'); 
							var l = colModel.length;
							var colItem; 
							var cmName;
							var colStates = gridP75DetalladoPedidoSinOferta.myColumnsState.colStates;
							var cIndex = 2;
							for (i = 0; i < l; i++) {
								colItem = colModel[i];
								cmName = colItem.name;
								if (colItem.hidden !== true && cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {

									jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('setColProp',cmName,{width:colStates[cmName].width});
									var cad =gridP75DetalladoPedidoSinOferta.nameJQuery+'_'+cmName;
									var ancho = "'"+colStates[cmName].width+"px'";
									var cell = jQuery('table'+gridP75DetalladoPedidoSinOferta.nameJQuery+' tr.jqgfirstrow td:nth-child(' + (i+1) + ')');
									cell.css("width", colStates[cmName].width + "px");

									jQuery(cad).css("width", colStates[cmName].width + "px");

								}
							}

						} else {
							this.jqGrid("remapColumns", perm, true);
						}
						saveColumnState(perm);
						jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('setGridWidth', $("#p75_AreaSinOferta").width(), autowidth);

					}

				}
			}
			); } });

	jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('setGridWidth', $("#p75_AreaSinOferta").width(), true);
}

function saveColumnState (perm) {
	var colModel =jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('getGridParam', 'colModel'); 
	//if (colModel == null)
	//	 colModel = grid.cm;
	var i;
	var l = colModel.length;
	var colItem; 
	var cmName;
	var postData = jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('getGridParam', 'postData');
	var columnsState = {
			search: jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('getGridParam', 'search'),
			page: jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('getGridParam', 'page'),
			sortname: jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('getGridParam', 'sortname'),
			sortorder: jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('getGridParam', 'sortorder'),
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
		window.localStorage.setItem(gridP75DetalladoPedidoSinOferta.myColumnStateName, JSON.stringify(columnsState));
	}
}


function loadGridStructureTextilP75(){
	$(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		url : './misumi/resources/mock.json',
		datatype : 'json',
		contentType : 'application/json',
		mtype : 'POST',
		sortable : false,

		subGrid : true,
		subGridRowExpanded: function (subgridDivId, rowId) {
			reloadSubgridTextil(rowId, subgridDivId);
		},

		colNames : gridP75DetalladoPedidoSinOfertaTextil.colNames,
		colModel :[{
			"name" : "nivelLote",
			"index":"nivelLote",
			"hidden" : true
		},{
			"name" : "temporada",
			"index":"temporada", 
			"sortable":false,
			"fixed":true,
			"resizable":false,
			"width" : 45
		},{
			"name" : "estructura",
			"index":"estructura", 
			"sortable":false,
			"fixed":true,
			"resizable":false,
			"width" : 58,
			"formatter" : p75FormatEstructura,
			"editable": false
		},{
			"name" : "numOrden",
			"index":"numOrden", 
			"sortable":false,
			"fixed":true,
			"resizable":false,
			"width" : 30
		},{
			"name" : "modeloProveedor",
			"index":"modeloProveedor", 
			"sortable":false,
			"fixed":true,
			"resizable":false,
			"width" : 45
		},{
			"name" : "descrTalla",
			"index":"descrTalla", 
			"sortable":false,
			"fixed":true,
			"resizable":false,
			"width" : 35
		},{
			"name" : "descrColor",
			"index":"descrColor", 
			"sortable":false,
			"fixed":true,
			"resizable":false,
			"width" : 50
		},{
			"name"  : "codArticulo",
			"index" : "codArticulo",
			"sortable":false,
			"fixed":true,
			"resizable":false,
			"width" : 60,
			"editable": false
		},{
			"name"  : "descriptionArt",
			"index" : "descriptionArt",
			"sortable":false,
			"fixed":true,
			"resizable":false,
			"width" : 120,
			"editable": false 
		},{
			"name"  : "tipoAprovisionamiento",
			"index" : "tipoAprovisionamiento", 
			"sortable":false,
			"fixed":true,
			"resizable":false,
			"width" : 20,
			"editable": false
		},{
			"name"  : "stock",
			"index" : "stock", 
			"sortable":false,
			"fixed":true,
			"resizable":false,
			"formatter":formatNumberDecimal,
			"width" : 25,
			"editable": false
		},{
			"name" : "facing",
			"index":"facing", 
			"formatter":"integer",
			"sortable":false,
			"fixed":true,
			"resizable":false,
			"width" : 25
		},{
			"name"  : "enCurso1",
			"index" : "enCurso1", 
			"sortable":false,
			"fixed":true,
			"resizable":false,
			//"formatter":formatNumberDecimalEnCurso,
			"width" : 30,
			"editable": false
		},{
			"index" : "unidadesCaja",
			"name"  : "unidadesCaja",
			"formatter" : p75FormatUnidadesCaja,
			"sortable":false,
			"fixed":true,
			"resizable":false,
			"width" : 25,
			"editable": false
		},{
			"name"  : "cajasPedidas",
			"index" : "cajasPedidas", 
			"sortable":false,
			"fixed":true,
			"resizable":false,
			"width" : 30,
			"editable": false
		},{
			"name"  : "propuesta",
			"index" : "propuesta", 
			"sortable":false,
			"fixed":true,
			"resizable":false,
			"width" : 30,
			"editable": false
		},{
			"name"  : "cantidad", //cantidad modificada
			"index" : "cantidad",
			"sortable":false,
			"fixed":true,
			"resizable":false,
			"editable": true,
			"editoptions":{
				"autocomplete": "off",
				"size" : "3",
				"maxlength":"3",//,
				"dataEvents": [
				               {//control para el keypress
				            	   type: 'keydown',
				            	   fn:controlNavegacion
				            	   /*fn:  function (e) {
			                                	  var key = e.charCode || e.keyCode;
				                                  if (key == 9 || key == 13 )//tab
				                                  {
				                                	  $("#gridP70PedidoDatos").setCell(lastgridsel,'estadoGrid',  '2', '',{});     
				                                      var grid = $('#gridP70PedidoDatos');
				                                      //Save editing for current row
				                                      grid.jqGrid('saveRow', lastgridsel, false, 'clientArray');
				                                      //If at bottom of grid, create new row
	//			                                      if (selIRow++ == grid.getDataIDs().length) {
	//			                                           grid.addRowData(selIRow, {});
	//			                                      }
				                                      //Enter edit row for next row in grid
				                                      grid.jqGrid('editRow', lastgridsel, true, 'clientArray');

				                                      jQuery('#gridP70PedidoDatos').restoreRow(lastgridsel); 

				                                  }
				                               }*/
				               },// cierra el control del keypress,
				               /*{ type: 'blur',
		                                  fn: function (e) {
		                                	  if(ischanged) { 
		                                		  $("#gridP70PedidoDatos").setCell(lastgridsel,'estadoGrid',  '2', '',{});     
			                                      var grid = $('#gridP70PedidoDatos');
			                                      //Save editing for current row
			                                      grid.jqGrid('saveRow', lastgridsel, false, 'clientArray');
			                                      grid.jqGrid('editRow', lastgridsel, true, 'clientArray');

			                                      jQuery('#gridP70PedidoDatos').restoreRow(lastgridsel); 
			                                      ischanged=false

		                                	  }else{
		                                		  jQuery('#gridP70PedidoDatos').restoreRow(lastgridsel); 
		                                	  }

		                                  }
		                                },*/
				               {"type":"focus","fn":function(e) {ischanged=false}
				               },
				               {"type":"change","fn":function(e) {ischanged=true}}
				               ]   
			},

			//formatter:formatNumber,
			"formatter" : p75FormateoCantidad,
			cellattr: function(rowId, val, rawObject) {
				/*var rowDataEstado= rawObject.estadoPedido;
					    if (rowDataEstado=='I'){
					    	 return " class='p70_columnaResaltadaIntegradaFila not-editable-cell ' "; //not-editable-cell
					    }else if (rowDataEstado=='R'){
					    	return " class='p70_columnaResaltadaGuardadoSinIntegrarFila' ";
					    }else if (rowDataEstado=='P'){
					    	return " class='p70_columnaResaltadaSinGuardarFila' ";					    	
					    }else if (rowDataEstado=='B'){
					    	return " class='p70_columnaResaltadaBloqueadaFila not-editable-cell ' ";		
					    }else{
					    	return " class='p70_columnaResaltadaSinGuardarFila' ";	
					    }*/


			},
			"fixed":true,
			"cellEdit" : true,
			"cellsubmit" : "clientArray",
			"width" : 70
		},{
			"name"  : "precioCostoFinal",
			"index" : "precioCostoFinal", 
			"formatter" : p75FormateoPrecioCostoFinal,
			"sortable" : false,
			"width" : 21,
			"editable": false
		},{
			"name"  : "unidFlEmpuje",
			"index" : "unidFlEmpuje", 
			"sortable" : false,
			"width" : 21,
			"editable": false
		}
		,{
			"name"  : "fechaEntrega",
			"index" : "fechaEntrega", 
			"sortable" : false,
			"formatter" : p75FormateoFechaEntrega,
			"width" : 20,
			"fixed":true,
			"editable": false
		},{
			"name"  : "nextDayPedido",
			"index" : "nexDayPedido", 
			"sortable" : false,
			"formatter" : p75FormateoDate,
			"width" : 40,
			"fixed":true,
			"editable": false
		},{
			"name"  : "rotacion",
			"index" : "rotacion", 
			"width" : 40,
			"hidden" : true
		},{
			"name"  : "estadoGrid",
			"index" : "estadoGrid", 
			"sortable":false,
			"fixed":true,
			"resizable":false,
			"formatter" : p75imageFormatEstado,
			"width" : 34,
			"editable": false
		}

		], 

		pager: gridP75DetalladoPedidoSinOfertaTextil.pagerNameJQuery,
		rowNum: 10,
		restoreAfterSelect: false,
		rowList: [10, 20, 30],
		viewrecords: true,
		loadonce: true,
		height:"auto",
		rownumbers:true,
		width:"auto",
		altclass:"ui-priority-secondary",
		altRows:true, //false, para que el grid no muestre cebrado
		autowidth:true,
		shrinkToFit:true,
		localReader:{id:'codArticulo'},
		index:"temporada",
		sortname:'temporada',
		sortorder:'asc',
		cellsubmit:'clientArray',
		emptyrecords:gridP75DetalladoPedidoSinOfertaTextil.emptyRecords,
		caption:gridP75DetalladoPedidoSinOfertaTextil.title,
		hidegrid:false, // false, para ocultar el boton que colapsa el grid.
		gridComplete : function() {
			$("#p70_AreaResultados .loading").css("display", "none");	
		},
		loadComplete : function(data) {
			gridP75DetalladoPedidoSinOfertaTextil.actualPage = data.page;
			gridP75DetalladoPedidoSinOfertaTextil.localData = data;
			gridP75DetalladoPedidoSinOfertaTextil.sortIndex = null;
			gridP75DetalladoPedidoSinOfertaTextil.sortOrder = null;
			$("#p70_AreaResultados .loading").css("display", "none");	
			$(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).jqGrid('filterToolbar',{defaultSearch : "cn"});
			$("#gbox_gridP75PedidoDatosSinOfertaTextil #gs_estadoGrid").css("display", "none");

		},
		resizeStop: function () {
			gridP75DetalladoPedidoSinOfertaTextil.modificado = true;

			jQuery(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).jqGrid('setGridWidth', $("#p75_AreaSinOferta").width(), false);
		},
		onSortCol : function (index, columnIndex, sortOrder){
			if (activaPantalla!=1){
				return 'stop';
			}else{
				gridP75DetalladoPedidoSinOfertaTextil.sortIndex = index;
				gridP75DetalladoPedidoSinOfertaTextil.sortOrder = sortOrder;
				reloadGrid('S');
				return 'stop';
			}
		},
		onPaging : function(postdata) {
			if (activaPantalla=1){
				alreadySorted = false;
				gridP75DetalladoPedidoSinOfertaTextil.sortIndex = null;
				gridP75DetalladoPedidoSinOfertaTextil.sortOrder = null;
				reloadGrid('S');
				return 'stop';
			}else{
				return 'stop';
			}	
		},
		onSelectRow: function(id){
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
		},
		beforeRequest : function() {
			var postData = jQuery(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).jqGrid('getGridParam', 'postData');
			if ((typeof(postData) !== 'undefined') && (typeof (postData.filters) !== 'undefined')) {
				reloadGridP75Filtros('S');
			}
		}
	});

	jQuery(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).jqGrid('navGrid',gridP75DetalladoPedidoSinOfertaTextil.pagerNameJQuery,{
		add:false,edit:false,del:false,search:false,refresh:false}
	); 

	jQuery(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).jqGrid('setGridWidth', $("#p75_AreaSinOferta").width(), true);

}

function p75FormateoDate(cellValue, opts, rowObject) {

	var fechaFormateada = '';
	if (cellValue != null){
		var diaFecha = parseInt(cellValue.substring(0,2),10);
		var mesFecha = parseInt(cellValue.substring(2,4),10);
		var anyoFecha = parseInt(cellValue.substring(4),10);

		fechaFormateada = $.datepicker.formatDate("D dd-M", new Date(anyoFecha, mesFecha - 1, diaFecha),{
			dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
			dayNames: $.datepicker.regional[ "es" ].dayNames,
			monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
			monthNames: $.datepicker.regional[ "es" ].monthNames
		});
	}

	return(fechaFormateada);
}

function p75FormateoFechaEntrega(cellValue, opts, rowObject) {

	var fechaFormateada = '';
	
	if (cellValue != null){
		var anyoFecha = parseInt(cellValue.substring(0,4),10);
		var mesFecha = parseInt(cellValue.substring(5,7),10);
		var diaFecha = parseInt(cellValue.substring(8),10);

		fechaFormateada = $.datepicker.formatDate("D dd-M", new Date(anyoFecha, mesFecha - 1, diaFecha),{
			dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
			dayNames: $.datepicker.regional[ "es" ].dayNames,
			monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
			monthNames: $.datepicker.regional[ "es" ].monthNames
		});
	}
	
	return(fechaFormateada);

}

function p75imageFormatEstado(cellValue, opts, rData) {
	var imagen = "";
	var mostrarNuevo = "none;"
	var mostrarModificado = "none;";
	var mostrarGuardado = "none;";
	var mostrarError = "none;";
	var mostrarInterrogante = "none;";
	var descError = "";
	var descInterrogante = "";

	if (cellValue == "0")	    {
		mostrarGuardado = "block;";

	}else if ( cellValue == "1" )		{    
		mostrarNuevo = "block";

	}else if ( cellValue == "2" )		{    
		//mostrarModificado = "block;";

		if (rData.aviso!=null && rData.aviso!="" && rData.aviso!="null"){
			var mostrarError = "block;";

			descError = rData.aviso;
			imagen = "<div align='center'><img src='./misumi/images/dialog-error-24.png'  title='"+rData.aviso+"'/></div>";
		} else {
			mostrarModificado = "block;";
		}

	}else if ( cellValue == "4" )		{     //GESTION DE EUROS, si estadoGRid == 4, ademas de pintar la linea en rojo, hay que poner en el campo estado el simbolo interrogante.
		mostrarInterrogante = "block";

		descInterrogante = avisoInterroganteRecalculo;
		imagen = "<div align='center'><img src='./misumi/images/dialog-help-24.png'  title='"+descError+"'/></div>";

	}else if ( cellValue == "5" ){
		mostrarModificado = "block;";
	}else if (cellValue == "3" ){
		mostrarGuardado = "block;";
	}else if (cellValue == "6" ){
		mostrarNuevo = "block";
	}else if (cellValue == "-1" || cellValue == "-3" || cellValue == "-2")		{   
		var mostrarError = "block;";
		if (rData.resultadoWS!=null && rData.resultadoWS!="" && rData.resultadoWS!="null"){
			descError = rData.resultadoWS;
			imagen = "<div align='center'><img src='./misumi/images/dialog-error-24.png'  title='"+rData.resultadoWS+"'/></div>";
		}

	}

	imagen = "<div id='"+opts.rowId+"_det_divNuevo' align='center' style='display: "+ mostrarNuevo + "'><img id='"+opts.rowId+"_det_imgNuevo' src='./misumi/images/nuevo.png' /></div>"; //Guardado
	imagen += "<div id='"+opts.rowId+"_det_divGuardado' align='center' style='display: "+ mostrarGuardado + "'><img id='"+opts.rowId+"_det_imgGuardado' src='./misumi/images/floppy.png' /></div>"; //Guardado
	imagen += "<div id='"+opts.rowId+"_det_divModificado' align='center' style='display: "+ mostrarModificado + "'><img id='"+opts.rowId+"_det_imgModificado' src='./misumi/images/modificado.png' /></div>"; //Modificado
	imagen += "<div id='"+opts.rowId+"_det_divError' align='center' style='display: " + mostrarError + "'><img id='"+opts.rowId+"_det_imgError' src='./misumi/images/dialog-error-24.png' title='"+descError+"'/></div>"; //Error
	imagen += "<div id='"+opts.rowId+"_det_divInterrogante' align='center' style='display: " + mostrarInterrogante + "'><img id='"+opts.rowId+"_det_imgInterrogante' src='./misumi/images/dialog-help-24.png' title='"+descInterrogante+"'/></div>"; //Interrogante

	//Añadimos las columnas hidden
	var datoTipoDetallado = "<input type='hidden' id='"+opts.rowId+"_tipoDetallado' value='"+ rData.tipoDetallado +"'>";
	imagen +=  datoTipoDetallado;
	var datoEstadoPedido = "<input type='hidden' id='"+opts.rowId+"_estadoPedido' value='"+rData.estadoPedido +"'>";
	imagen +=  datoEstadoPedido;
	var datoGrupo1 = "<input type='hidden' id='"+opts.rowId+"_grupo1' value='"+rData.grupo1 +"'>";
	imagen +=  datoGrupo1;
	var datoGrupo2 = "<input type='hidden' id='"+opts.rowId+"_grupo2' value='"+rData.grupo2 +"'>";
	imagen +=  datoGrupo2;
	var datoGrupo3 = "<input type='hidden' id='"+opts.rowId+"_grupo3' value='"+rData.grupo3 +"'>";
	imagen +=  datoGrupo3;
	var datoGrupo4 = "<input type='hidden' id='"+opts.rowId+"_grupo4' value='"+rData.grupo4 +"'>";
	imagen +=  datoGrupo4;
	var datoGrupo5 = "<input type='hidden' id='"+opts.rowId+"_grupo5' value='"+rData.grupo5 +"'>";
	imagen +=  datoGrupo5;
	var datoResultadoWS = "<input type='hidden' id='"+opts.rowId+"_resultadoWS' value='"+rData.resultadoWS +"'>";
	imagen +=  datoResultadoWS;
	var estadoGrid = "<input type='hidden' id='"+opts.rowId+"_estadoGrid' value='"+cellValue +"'>";
	imagen +=  estadoGrid;
	var unidadesCaja = "<input type='hidden' id='"+opts.rowId+"_unidadesCaja' value='"+rData.unidadesCaja +"'>";
	imagen +=  unidadesCaja;
	var ufp = "<input type='hidden' id='"+opts.rowId+"_ufp' value='"+rData.ufp +"'>";
	imagen +=  ufp;
	var nivelLote = "<input type='hidden' id='"+opts.rowId+"_nivelLote' value='"+rData.nivelLote +"'>";
	imagen +=  nivelLote;
	var flgSIA = "<input type='hidden' id='"+opts.rowId+"_flgSIA' value='"+rData.flgSIA +"'>";
	imagen +=  flgSIA;
	var cantidadPropuesta = "<input type='hidden' id='"+opts.rowId+"_cantidadPropuesta' value='"+rData.propuesta +"'>";
	imagen +=  cantidadPropuesta;
	var cantidadOriginal = "<input type='hidden' id='"+opts.rowId+"_cantidadOriginal' value='"+rData.cantidadOriginal +"'>";
	imagen +=  cantidadOriginal;
	var cantidadUltValida = "<input type='hidden' id='"+opts.rowId+"_cantidadUltValida' value='"+rData.cantidadUltValida +"'>";
	imagen +=  cantidadUltValida;
	var flgOferta = "<input type='hidden' id='"+opts.rowId+"_flgOferta' value='"+rData.flgOferta +"'>";
	imagen +=  flgOferta;	
	var codArticulo = "<input type='hidden' id='"+opts.rowId+"_codArticulo' value='"+rData.codArticulo +"'>";
	imagen +=  codArticulo;
	var dexenx = "<input type='hidden' id='"+opts.rowId+"_dexenx' value='"+rData.dexenx +"'>";
	imagen +=  dexenx;
	var tipoUfp = "<input type='hidden' id='"+opts.rowId+"_tipoUfp' value='"+rData.tipoUFP +"'>";
	imagen +=  tipoUfp;
	var precioCostoArticulo = "<input type='hidden' id='"+opts.rowId+"_precioCostoArticulo' value='"+rData.precioCostoArticulo +"'>";
	imagen +=  precioCostoArticulo;
	var precioCostoFinalAux = "<input type='hidden' id='"+opts.rowId+"_precioCostoFinalAux' value='"+rData.precioCostoFinal +"'>";
	imagen +=  precioCostoFinalAux;
	var cantidadAux = "<input type='hidden' id='"+opts.rowId+"_cantidadAux' value='"+rData.cantidadOriginal +"'>";
	imagen +=  cantidadAux;
	var refCumple = "<input type='hidden' id='"+opts.rowId+"_cantidadAux' value='"+rData.refCumple +"'>";
	imagen +=  refCumple;

	var oferta = "<input type='hidden' id='"+opts.rowId+"_oferta' value='"+rData.oferta +"'>";
	imagen +=  oferta;

	numRow++;

	return imagen;
}
function p75FormatAgrup(cellValue, opts, rData) {
	return  rData.grupo1 +"-"+ rData.grupo2+"-"+rData.grupo3+"-"+rData.grupo4+"-"+rData.grupo5;
}

function p75FormatEstructura(cellValue, opts, rData) {
	return  $.formatNumber(rData.grupo2,{format:"00"}) +""+ $.formatNumber(rData.grupo3,{format:"00"})+""+$.formatNumber(rData.grupo4,{format:"00"}) +""+ $.formatNumber(rData.grupo5,{format:"00"});
}

/*Clase de constantes para el GRID de detallado de pedido Sin Oferta*/
function GridP75DetalladoPedidoSinOferta (locale){
	// Atributos
	this.name = "gridP75PedidoDatosSinOferta"; 
	this.nameJQuery = "#gridP75PedidoDatosSinOferta"; 
	this.i18nJSON = './misumi/resources/p75DetalladoPedidoSinOferta/p75detalladoPedidoSinOferta_' + locale + '.json';
	this.colNames = null;

	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP75PedidoDatosSinOferta"; 
	this.pagerNameJQuery = "#pagerP75PedidoDatosSinOferta";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;

	this.myColumnStateName = 'gridP75.colState';
	this.myColumnsState = null;
	this.isColState = true;
	this.firstLoad = true;
	this.modificado = false;

	this.titleVentaForzada = null;
	this.titleModifPorAjuste = null;
	this.titleModifPorRegularizacion = null;

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
	}; 

	this.getSortOrder = function getSortOrder () {

		if ($(this.nameJQuery).getGridParam('sortorder')!=null){
			return $(this.nameJQuery).getGridParam('sortorder');
		}else{
			return "asc";
		}
	};	

	this.clearGrid = function clearGrid() {
		$(this.nameJQuery).jqGrid('GridUnload');
	};
	
	this.getFilters = function (){
		var postData = $(this.nameJQuery).jqGrid('getGridParam', 'postData');
   	 	return postData;
    };
    
    this.getSearch= function (){
   	 	var postData = $(this.nameJQuery).jqGrid('getGridParam', 'postData');
   	 	if (typeof(postData) !== 'undefined')
   	 		return postData._seach;
   	 	return false;
    };

}

/*Clase de constantes para el GRID de detallado de pedido Textil*/
function GridP75DetalladoPedidoSinOfertaTextil (locale){
	// Atributos
	this.name = "gridP75PedidoDatosSinOfertaTextil"; 
	this.nameJQuery = "#gridP75PedidoDatosSinOfertaTextil"; 
	this.i18nJSON = './misumi/resources/p75DetalladoPedidoSinOferta/p75detalladoPedidoSinOferta_' + locale + '.json';
	this.colNames = null;
	this.subgridColNames = null;

	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP75PedidoDatossinOfertaTextil"; 
	this.pagerNameJQuery = "#pagerP75PedidoDatosSinOfertaTextil";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;

	this.myColumnStateName = 'gridP75.colState';
	this.myColumnsState = null;
	this.isColState = true;
	this.firstLoad = true;
	this.modificado = false;

	this.titleVentaForzada = null;
	this.titleModifPorAjuste = null;
	this.titleModifPorRegularizacion = null;

	this.subgridCm = [{
		"name" : "nivelLote",
		"index":"nivelLote",
		"formatter": p75imageFormatEstado,
		"hidden" : true
	},{
		"name" : "temporada",
		"index":"temporada", 
		"formatter":p75FormateoABlanco,
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 43
	},{
		"name" : "estructura",
		"index":"estructura", 
		"formatter":p75FormateoABlanco,
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 58,
		"editable": false
	},{
		"name" : "numOrden",
		"index":"numOrden", 
		"formatter":p75FormateoABlanco,
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 30
	},{
		"name" : "modeloProveedor",
		"index":"modeloProveedor", 
		"formatter":p75FormateoABlanco,
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 45
	},{
		"name" : "descrTalla",
		"index":"descrTalla", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 35
	},{
		"name" : "descrColor",
		"index":"descrColor", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 50
	},{
		"name"  : "codArticulo",
		"index" : "codArticulo",
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 60,
		"editable": false
	},{
		"name"  : "descriptionArt",
		"index" : "descriptionArt",
		"formatter":p75FormateoABlanco,
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 120,
		"editable": false 
	},{
		"name"  : "tipoAprovisionamiento",
		"index" : "tipoAprovisionamiento", 
		"formatter":p75FormateoABlanco,
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 20,
		"editable": false
	},{
		"name"  : "stock",
		"index" : "stock", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"formatter":formatNumberDecimal,
		"width" : 25,
		"editable": false
	},{
		"name" : "facing",
		"index":"facing", 
		"formatter":"integer",
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 25
	},{
		"name"  : "enCurso1",
		"index" : "enCurso1", 
		"formatter":p75FormateoABlanco,
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 30,
		"editable": false
	},{
		"index" : "unidadesCaja",
		"name"  : "unidadesCaja",
		"formatter" : p75FormatUnidadesCaja,
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 25,
		"editable": false
	},{
		"name"  : "cajasPedidas",
		"index" : "cajasPedidas", 
		"formatter":p75FormateoABlanco,
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 30,
		"editable": false
	},{
		"name"  : "propuesta",
		"index" : "propuesta", 
		"formatter":p75FormateoABlanco,
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 30,
		"editable": false
	},{
		"name"  : "cantidad", 
		"index" : "cantidad",
		"formatter":p75FormateoABlanco,
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 70,
		"editable": false	
	},{
		"name"  : "precioCostoFinal",
		"index" : "precioCostoFinal", 
		"formatter" : p75FormateoPrecioCostoFinal,
		"sortable" : false,
		"width" : 21,
		"editable": false
	},{
		"name"  : "unidFlEmpuje",
		"index" : "unidFlEmpuje", 
		"sortable" : false,
		"width" : 21,
		"editable": false
	}
	,{
		"name"  : "fechaEntrega",
		"index" : "fechaEntrega", 
		"sortable" : false,
		"formatter" : p75FormateoFechaEntrega,
		"width" : 20,
		"fixed":true,
		"editable": false
	},{
		"name"  : "nextDayPedido",
		"index" : "nexDayPedido", 
		"sortable" : false,
		"formatter" : p75FormateoDate,
		"width" : 40,
		"fixed":true,
		"editable": false
	},{
		"name"  : "rotacion",
		"index" : "rotacion", 
		"width" : 40,
		"hidden" : true
	},{
		"name"  : "estadoGrid",
		"index" : "estadoGrid", 
		"formatter":p75FormateoABlanco,
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 33,
		"editable": false
	}
	];

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

	this.getCellValue = function getCellValueTextil(rowId, colName){ 
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

	}; 

	this.getSortOrder = function getSortOrder () {

		if ($(this.nameJQuery).getGridParam('sortorder')!=null){
			return $(this.nameJQuery).getGridParam('sortorder');
		}else{
			return "asc";
		}
	}; 	

	this.clearGrid = function clearGrid() {
		$(this.nameJQuery).jqGrid('GridUnload');
	};
	
	this.getFilters = function (){
		var postData = $(this.nameJQuery).jqGrid('getGridParam', 'postData');
   	 	return postData;
    };
    
    this.getSearch= function (){
   	 	var postData = $(this.nameJQuery).jqGrid('getGridParam', 'postData');
   	 	if (typeof(postData) !== 'undefined')
   	 		return postData._seach;
   	 	return false;
    };

}

function p75FormateoABlanco(cellValue, opts, rData) {
	let celdaVacia = '';
	return (celdaVacia);
}

function p75TooltipDenominacion(cellvalue, options, rowData) {
	let descripcion 	   	   = cellvalue,
		titleImgDescripcion    ='', 
		imgDescripcion 		   ='';

	if (rowData.tipo != null){
		if (rowData.tipo == 'N'){
			titleImgDescripcion += i18nModule._("p70_detallado.pedido.descripcion.title.tipoN");
			imgDescripcion = "<img src='./misumi/images/dialog-confirm-12.png' title='"+titleImgDescripcion+"'>";
		}else if (rowData.tipo == 'NF'){
		   	titleImgDescripcion += i18nModule._("p70_detallado.pedido.descripcion.title.tipoNF");
		   	imgDescripcion = "<img src='./misumi/images/dialog-confirm-12.png' title='"+titleImgDescripcion+"'>";
		}
	   	
	   	return imgDescripcion + descripcion;
	}else{
	   	return descripcion;
	}
	   		
}

function formatNumberDecimalEnCurso(cellValue, opts, rData) {
	var total = cellValue/rData.unidadesCaja ;
	return $.formatNumber(total,{format:"0.000",locale:"es"});
}

function p75FormatUnidadesCaja(cellValue, opts, rData){
	/*
	if ( (rData.grupo2 == 2 || (rData.grupo2 == 4 && rData.grupo3 == 25) || (rData.grupo2 == 7 && rData.grupo3 == 12) ) && rData.ufp != 0 ){
		return '<span style="font-size: 11px; font-weight: bold;">'+rData.ufp+'</span>';
	} else {
		return cellValue;
	} 
	 */
	return cellValue;
}


function p75FormateoPrecioCostoFinal(cellValue, opts, rData){
	return '<span  id="' + opts.rowId + '_precioCostoFinal">'+$.formatNumber(rData.precioCostoFinal,{format:"0.#",locale:"es"})+'</span>';
}

function p75FormateounidFlEmpuje(cellValue, opts, rData){
	if ((null == cellValue) || (cellValue == 0)) {
		return '';
	} else {
		return cellValue;
	}
}

function p75FormateoCantidad(cellValue, opts, rData){

	/*
	if (rData.estadoPedido == "B") {
		if (rData.tipoUFP == "U") {
			cantidadIntegrada = '<span class="p75_cantidadIntegrada">'+cellValue+'</span><span class="p75_cantidadIntegradaU">' + rData.dexenx + '</span>';
			return cantidadIntegrada;
		} else {
			cantidadIntegrada = '<span class="p75_cantidadIntegrada">'+cellValue+'</span>';
			return cantidadIntegrada;
		}
	}//En el caso de ser tipo 2 y 4 tampoco queremos que salga la U.
	//Antes de 5608 si no se cumplía la condición para sacar la U devolvíamos
	//lo siguiente. Ahora siempre se devuelve.
	else if(rData.estadoPedido == "I"){
		cantidadIntegrada = '<span class="p75_cantidadIntegrada">'+cellValue+'</span>';
		return cantidadIntegrada;
	}else {
		if (rData.estadoPedido == "E") {
			return "";
		} else {
			return cellValue;
		}
	}
	 */

	if (rData.estadoPedido == "E") {
		return "";
	}else if(rData.estadoPedido == "B" || rData.estadoPedido == "I"){
		if(parseInt(rData.unidFlEmpuje) > 0 && parseInt(rData.cantidad) == 0){
			return "";
		}else{
			return cellValue;
		}
	}else {
		return cellValue;
	}
}

function p75FormatTipo(cellValue, opts, rData){
	var tipo = "";
	if (cellValue == "N" || cellValue == "NF"){
		tipo = '<span class="p75_tipoNuevo">NUEVA</span>';
	} 
	return tipo;
}

function p75FormatCampoCantidad(estado,tipoReferencia,tipoUFP,dexenx,diferencia, linea){
	var tipo = "";
	var formateoDif = "";
	linea = linea + 1;
	if ((null != diferencia) && (diferencia != 0)) {

		if (diferencia > 0) { //si la diferencia es mayor que cero lo pintamos en azul
			formateoDif = '<span id="' + linea + '_diferencia" class="p75_diferenciaEnAzul p75_diferenciaVisible">' + '+' + diferencia + '</span>';

		} else { //si la diferencia es menor que cero lo pintamos en rojo
			formateoDif = '<span id="' + linea + '_diferencia" class="p75_diferenciaEnRojo p75_diferenciaVisible">' + diferencia + '</span>';
		}

		tipo = formateoDif;

	} else {
		formateoDif = '<span id="' + linea + '_diferencia" class="p75_diferenciaEnRojo p75_diferenciaOculto"> </span>';
	}

	if (null != tipoUFP){
		if(estado != "B" && estado != "I") {
			if (dexenx > 1){
				tipo = tipo + '<span class="p75_tipoCarne">' + 'De' + dexenx + 'en' + dexenx +'</span>';
			}
		}
	} 
	return tipo;
}

function p75FormatEnCurso1(cellValue, opts, rData) {
	var total=$.formatNumber(rData.enCurso1,{format:"0.000"});
	return total;
}

function p75FormatEnCurso2(cellValue, opts, rData) {
	var total=$.formatNumber(rData.enCurso2,{format:"0.000"});
	return total;
}

function formatNumberDecimal(cellValue, opts, rData) {
	return $.formatNumber(cellValue,{format:"0.##",locale:"es"});
}

function formatNumber(cellValue, opts, rData) {
	return $.formatNumber(cellValue,{format:"0",locale:"es"});
}

function reloadGridP75(saveChanges){
	var listaModificadosP75 = null;

	area = null;
	var seccion = null;
	var detalladoPedido = null;

	var selectedRadioButton = $("input[name='p70_rad_tipoFiltro']:checked").val();
	
	//Se esta consultando "Por estructura"
	if (selectedRadioButton == "1"){

		var seccion = '';
		if (null != seccionSel){
			seccion = seccionSel;
		} else {
			var arrAreaSeccion = $("#p70_cmb_seccion").val().split("*");
			if ("" != arrAreaSeccion[0] && "null" != arrAreaSeccion[0]){
				area = arrAreaSeccion[0];
			}
			if ("" != arrAreaSeccion[1] && "null" != arrAreaSeccion[1]){
				seccion = arrAreaSeccion[1];
			}
		}
		
		var categoria = '';
		if (null != categoriaSel){
			categoria = categoriaSel;
		} else {
			if ($("#p70_cmb_categoria").val() != null) {
				var arrAreaCategoria = $("#p70_cmb_categoria").val().split("*");
				if ("" != arrAreaCategoria[0] && "null" != arrAreaCategoria[0]){
					categoria = arrAreaCategoria[0];
				}
			}
		}
		
		if(area==3){
			listaModificadosP75 = obtenerListaP75ModificadosTextil('S', dataFromDetalladoPedidoJSP.isCentroVegalsa());
			listaModificadosP76 = obtenerListaP76ModificadosTextil('S', dataFromDetalladoPedidoJSP.isCentroVegalsa());
		} else {
			listaModificadosP75 = obtenerListaP75Modificados('S', dataFromDetalladoPedidoJSP.isCentroVegalsa());
			listaModificadosP76 = obtenerListaP76Modificados('S', dataFromDetalladoPedidoJSP.isCentroVegalsa());
		}

		detalladoPedido = new DetalladoPedido (seccion,categoria,null,listaModificadosP75);

	//Se esta consultando "Por referencia"
	} else if (selectedRadioButton == "2"){

		var rowData = jQuery(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).getRowData(1);
		area = $("#1_grupo1").val();
//		seccion = $("#1_grupo2").val();
		if (area ==3){
			listaModificadosP75 = obtenerListaP75ModificadosTextil('S', dataFromDetalladoPedidoJSP.isCentroVegalsa());
			listaModificadosP76 = obtenerListaP76ModificadosTextil('S', dataFromDetalladoPedidoJSP.isCentroVegalsa());
		} else {
			listaModificadosP75 = obtenerListaP75Modificados('S', dataFromDetalladoPedidoJSP.isCentroVegalsa());
			listaModificadosP76 = obtenerListaP76Modificados('S', dataFromDetalladoPedidoJSP.isCentroVegalsa());
		}
		detalladoPedido= new DetalladoPedido (null,null,$("#p70_fld_referencia").val(),listaModificadosP75);
		
	//Se esta consultando "Por mapa"
	} else if (selectedRadioButton == "3"){

		listaModificadosP75 = obtenerListaP75Modificados('S', dataFromDetalladoPedidoJSP.isCentroVegalsa());
		listaModificadosP76 = obtenerListaP76Modificados('S', dataFromDetalladoPedidoJSP.isCentroVegalsa());

		let codMapa = $("#p70_cmb_mapa").val();

		detalladoPedido = new DetalladoPedido ();
		
		if (null != codMapa && codMapa > 0){
			detalladoPedido.codMapa = codMapa; 
		}
		  
	}

	var gridP75 = null;
	if (area==3) {
		gridP75 = gridP75DetalladoPedidoSinOfertaTextil;
	} else { 
		gridP75 = gridP75DetalladoPedidoSinOferta;
	}	
	//Miramos si el checkbox está seleccionado o no.
	if ($("#p70_chk_IncluirPedido").is(':visible')) { //Si el check es visible, la busqueda dependera si el checkbox está seleccionado o no.
		//Miramos si el checkbox está seleccionado o no.
		flgIncluirPropPed = 'N';
		if($("#p70_chk_IncluirPedido").prop('checked')){
			flgIncluirPropPed = 'S';
		}
	} else { //Si el check NO es visible, la busqueda actuara como si el check estuviese seleccionado.
		flgIncluirPropPed = 'S';
	}
	
	//Introducimos el valor del checkbox para realizar la búsqueda.
	detalladoPedido.flgIncluirPropPed = flgIncluirPropPed;

	var objJson = $.toJSON(detalladoPedido);	

	$.ajax({
		type : 'POST',
		url : './detalladoPedido/loadDataGridSinOferta.do?saveChanges='+saveChanges+'&page='+gridP75.getActualPage()+'&max='+gridP75.getRowNumPerPage()+'&index='+gridP75.getSortIndex()+'&sortorder='+gridP75.getSortOrder(),
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {
			if (data.datos.records > 0){

				var permisos = $("#userPermision").val();

				/*
				 * MISUMI-300
				 */
				if (dataFromDetalladoPedidoJSP.isCentroVegalsa()){
					//Si no hay registros de SIA 
					if (!dataFromDetalladoPedidoJSP.hasAnyRecordFromSIA()){
						//ocutar columnas Aprov, Cur2, C.encargos
						jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('hideCol', ["tipoAprovisionamiento"]);
						jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('hideCol', ["enCurso2"]);
						jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('hideCol', ["cajasPedidas"]);
						$(".ui-jqgrid-bdiv").css('overflow-x', 'auto');
//						jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('setGridWidth', $("#p75_AreaSinOferta").width(), false);
					}
				}else{
					//Ocultar columnas mapa, motivo y facing (MISUMI-414)
					jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('hideCol', ["codMapa"]);
					jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('hideCol', ["motivoPedido"]);
					jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('hideCol', ["facing"]);
					jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('hideCol', ["cajasCortadas"]);
					jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('hideCol', ["incPrevisionVenta"]);
					jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('hideCol', ["smEstatico"]);
					$(".ui-jqgrid-bdiv").css('overflow-x', 'visible');
//					jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('setGridWidth', $("#p75_AreaSinOferta").width(), true);
				}

				$("#p70_informacion").show();

				$("#p70_AreaPestanas").show();
				$("#p70_AreaResultados").show();

				$("#p70_AreaEstadosPedido").show();

				//$("#p70_AreaNotaCarne").hide();
				$("#p70_AreaNotaDexenx").hide();
				$("#p70_AreaNotaPie").hide();
				$("#p70_AreaNotaBloqueoAlAlza").hide();
				$("#p70_AreaNotaRedondeo").hide();

				$("div#p70_AreaPestanas").attr("style", "display:block");

				$("#p70_pestanaSinOfertaCargada").val("S");
				//$("#p70_pestanaOfertaCargada").val("");

				//GESTION DE EUROS
				//$("#p70_inputEurosIniciales").text(Math.round(data.eurosIniciales));
				//$("#p70_inputEurosFinales").val(Math.round(data.eurosFinales));
				//$("#p70_inputEurosFinalesHidden").val(Math.round(data.eurosFinales));
				$("#p70_inputEurosFinales").filter_input({regex:'[0-9]'});

				$("#p70_inputEurosIniciales").text(data.eurosIniciales);
				$("#p70_inputEurosFinales").val(data.eurosFinales);
				$("#p70_inputEurosFinalesHidden").val(data.eurosFinales);
				$("#p70_inputCajasIniciales").text(data.cajasIniciales);
				$("#p70_inputCajasFinales").text(data.cajasFinales);
				$("#p70_inputCajasFinalesHidden").val(data.cajasFinales);

				if ($("input[name='p70_rad_tipoFiltro']:checked").val() == "2"){
					area = data.datos.rows[0].grupo1;
					seccion = data.datos.rows[0].grupo2;
				}
				//if( arrAreaSeccion[0]==3){ // Se va a mostrar el grid de textil
				if (area ==3 ){

					if(permisos.indexOf('50_DETALLADO_GESTION_EUROS') > 0 || permisos.indexOf('51_DETALLADO_GESTION_EUROS_CONS') > 0){	
						jQuery(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).jqGrid('showCol', ["precioCostoFinal"]);
					}else{
						jQuery(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).jqGrid('hideCol', ["precioCostoFinal"]);
					}

					$("#gbox_gridP75PedidoDatosSinOferta").hide();
					$("#gbox_gridP75PedidoDatosSinOfertaTextil").show();


					$('#gridP75PedidoDatosSinOferta').jqGrid('clearGridData');

					$("#gridP75PedidoDatosSinOfertaTextil")[0].addJSONData(data.datos);
					gridP75DetalladoPedidoSinOfertaTextil.actualPage = data.datos.page;
					gridP75DetalladoPedidoSinOfertaTextil.localData = data.datos;

					fulfilInformation();

					p75ControlesPantallaTextil();

					if (($("input[name='p70_rad_tipoFiltro']:checked").val() == "2") && (data.datos.rows[0].nivelLote >0)){ //La consulta ha sido por referencia 
						$("#1 td.sgcollapsed",gridP75DetalladoPedidoSinOfertaTextil[0]).click();
					}

					var ids = jQuery(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).jqGrid('getDataIDs'), i, l = ids.length;

					$("#p70_AreaPestanas").hide();
					$("#p70_AreaResultados").hide();

					for (i = 0; i < l; i++) {

						var estado= data.datos.rows[i].estadoPedido; //B o I
						var estadoGrid= data.datos.rows[i].estadoGrid; 
						let tipo=data.datos.rows[i].tipo; 

						if (estado=="I"){
							//Integrado --> No modificable
							$("#gridP75PedidoDatosSinOfertaTextil").find("#"+ids[i]).find("[aria-describedby='gridP75PedidoDatosSinOfertaTextil_cantidad']").addClass("p75_columnaResaltadaIntegradaFila");
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaIntegradaFila");														
						}else if (estado=="E"){
							//Empuje -->  No modificable
							$("#gridP75PedidoDatosSinOfertaTextil").find("#"+ids[i]).find("[aria-describedby='gridP75PedidoDatosSinOfertaTextil_cantidad']").addClass("p75_columnaResaltadaEmpujeFila");
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaEmpujeFila");
						}else if (estado=="R"){
							//Guardado sin integrar
							jQuery(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).jqGrid('editRow', ids[i], false);
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaGuardadoSinIntegrarFila");
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").filter_input({regex:'[0-9]'});
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").change(function(event) {
								existenDatosSinGuardar = true;
								validacionCantidadP75(event);
							});
							var idRedondeo = '<span id="' + (i+1)+ '_cantidadRedondeo" class="p75_tipoRedondeoOculto">R</span>';
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").after(idRedondeo);
						}else if (estado=="B"){
							//Bloqueado
							$("#gridP75PedidoDatosSinOfertaTextil").find("#"+ids[i]).find("[aria-describedby='gridP75PedidoDatosSinOfertaTextil_cantidad']").addClass("p75_columnaResaltadaBloqueadaFila").removeClass("p75_columnaResaltadaOferta");
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").removeClass("editable").addClass("p75_columnaResaltadaBloqueadaFila").removeClass("p75_columnaResaltadaOferta");													
						}	
						else if (estado=="A"){
							//Bloqueado al Alza
							jQuery(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).jqGrid('editRow', ids[i], false);
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaBloqueadaAlAlzaFila");
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").filter_input({regex:'[0-9]'});
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").change(function(event) {
								existenDatosSinGuardar = true;
								validacionCantidadP75(event);
							});
							var idRedondeo = '<span id="' + (i+1)+ '_cantidadRedondeo" class="p75_tipoRedondeoOculto">R</span>';
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").after(idRedondeo);
							$("#p70_AreaNotaBloqueoAlAlza").show();
						}
						else{
							jQuery(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).jqGrid('editRow', ids[i], false);
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaSinGuardarFila");
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").filter_input({regex:'[0-9]'});
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").change(function(event) {
								existenDatosSinGuardar = true;
								validacionCantidadP75(event);
							});
							var idRedondeo = '<span id="' + (i+1)+ '_cantidadRedondeo" class="p75_tipoRedondeoOculto">R</span>';
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").after(idRedondeo);
						}

						if (estadoGrid == 5 || estadoGrid == 6) { //modificado/nuevo con redondeo
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidadRedondeo").removeClass("p75_tipoRedondeoOculto");
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidadRedondeo").removeClass("p75_tipoRedondeoVisible");

							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidadRedondeo").addClass("p75_tipoRedondeoVisible");

							$("#p70_AreaNotaRedondeo").show();
						}

						//GESTION EUROS - resaltar linea en rojo cuando refCumple == N
						var refCumple= data.datos.rows[i].refCumple; 
						if (refCumple == "N") {//Pintamos la linea en rojo
							$("#gridP75PedidoDatosSinOfertaTextil").find("#"+ids[i]).find("td").addClass("p75_lineaResaltadaRojo");

							//pintamos la columna P.Costo tambien en rojo
							$("#gridP75PedidoDatosSinOfertaTextil #"+ (i+1) +"_precioCostoFinal").addClass("p75_precioCostofinalRojo");
						}
						
						if (tipo=='F' || tipo=='NF'){
							$("#gridP75PedidoDatosSinOfertaTextil").find("#"+ids[i]).find("[aria-describedby='gridP75PedidoDatosSinOfertaTextil_fechaEntrega']").addClass("p75_columnaResaltadaIntegradaFila");
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_fechaEntrega").addClass("p75_columnaResaltadaIntegradaFila");														
						}

					}

					cargarOrdenacionesColumnasP75Textil();

				} else {
					//Mostrará o quitara las columnas de textil
					//Indica que se está llamando en la carga inicial del grid.
					hideShowColumnsP75(area, 0);

					if (seccion == 2 || seccion == 4 || seccion == 7){

						jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('showCol', ["tipoUFP"]);
						//$("#p70_AreaNotaCarne").show();
						$("#p70_AreaNotaDexenx").show();
					} else {
						jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('hideCol', ["tipoUFP"]);
						//$("#p70_AreaNotaCarne").hide();
						$("#p70_AreaNotaDexenx").hide();
					}

					if(permisos.indexOf('50_DETALLADO_GESTION_EUROS') > 0 || permisos.indexOf('51_DETALLADO_GESTION_EUROS_CONS') > 0){	
						jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('showCol', ["precioCostoFinal"]);
					}else{
						jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('hideCol', ["precioCostoFinal"]);
					}

					jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('setGridWidth', $("#p75_AreaSinOferta").width(), true);
					$("#gbox_gridP75PedidoDatosSinOfertaTextil").hide();
					$("#gbox_gridP75PedidoDatosSinOferta").show();

					$('#gridP75PedidoDatosSinOfertaTextil').jqGrid('clearGridData');


					$("#gridP75PedidoDatosSinOferta")[0].addJSONData(data.datos);
					gridP75DetalladoPedidoSinOferta.actualPage = data.datos.page;
					gridP75DetalladoPedidoSinOferta.localData = data.datos;

					fulfilInformation();

					var ids = jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('getDataIDs'), i, l = ids.length;

					$("#p70_AreaPestanas").hide();
					$("#p70_AreaResultados").hide();
					for (i = 0; i < l; i++) {						

						//var estado=$("#gridP70PedidoDatos").getRowData(ids[i]).estadoPedido; //B o I
						var estado= data.datos.rows[i].estadoPedido; //B o I
						var estadoGrid= data.datos.rows[i].estadoGrid; 
						var tipoReferencia = data.datos.rows[i].grupo2; 
						let tipo = data.datos.rows[i].tipo; 

						if (estado=="I"){
							//Integrado --> No modificable
							$("#gridP75PedidoDatosSinOferta").find("#"+ids[i]).find("[aria-describedby='gridP75PedidoDatosSinOferta_cantidad']").addClass("p75_columnaResaltadaIntegradaFila");
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaIntegradaFila");														
						}else if (estado=="E"){
							//Empuje -->  No modificable
							$("#gridP75PedidoDatosSinOferta").find("#"+ids[i]).find("[aria-describedby='gridP75PedidoDatosSinOferta_cantidad']").addClass("p75_columnaResaltadaEmpujeFila");
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaEmpujeFila");
						}else if (estado=="R"){
							//Guardado sin integrar
							jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('editRow', ids[i], false);
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaGuardadoSinIntegrarFila");
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").filter_input({regex:'[0-9]'});
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").change(function(event) {
								existenDatosSinGuardar = true;
								validacionCantidadP75(event);
							});
							var idRedondeo = '<span id="' + (i+1)+ '_cantidadRedondeo" class="p75_tipoRedondeoOculto">R</span>';
							$("#"+(i+1)+"_cantidad").after(idRedondeo);
						}else if (estado=="B"){
							//Bloqueado
							$("#gridP75PedidoDatosSinOferta").find("#"+ids[i]).find("[aria-describedby='gridP75PedidoDatosSinOferta_cantidad']").addClass("p75_columnaResaltadaBloqueadaFila").removeClass("p75_columnaResaltadaOferta");
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").removeClass("editable").addClass("p75_columnaResaltadaBloqueadaFila").removeClass("p75_columnaResaltadaOferta");												
						}else if (estado=="A"){
							//Bloqueado al Alza
							jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('editRow', ids[i], false);
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaBloqueadaAlAlzaFila");
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").filter_input({regex:'[0-9]'});
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").change(function(event) {
								existenDatosSinGuardar = true;
								validacionCantidadP75(event);
							});
							var idRedondeo = '<span id="' + (i+1)+ '_cantidadRedondeo" class="p75_tipoRedondeoOculto">R</span>';
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").after(idRedondeo);

							$("#p70_AreaNotaBloqueoAlAlza").show();
						}else{
							jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('editRow', ids[i], false);
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaSinGuardarFila");
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").filter_input({regex:'[0-9]'});
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").change(function(event) {
								existenDatosSinGuardar = true;
								validacionCantidadP75(event);
							});
							var idRedondeo = '<span id="' + (i+1)+ '_cantidadRedondeo" class="p75_tipoRedondeoOculto">R</span>';
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").after(idRedondeo);

						}
						$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").after(p75FormatCampoCantidad(estado,tipoReferencia,data.datos.rows[i].tipoUFP, data.datos.rows[i].dexenx, data.datos.rows[i].diferencia, i));

						if (estadoGrid == 5 || estadoGrid == 6) { //modificado/nuevo con redondeo
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidadRedondeo").removeClass("p75_tipoRedondeoOculto");
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidadRedondeo").removeClass("p75_tipoRedondeoVisible");

							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidadRedondeo").addClass("p75_tipoRedondeoVisible");

							$("#p70_AreaNotaRedondeo").show();
						}

						//GESTION EUROS - resaltar linea en rojo cuando refCumple == N
						var refCumple= data.datos.rows[i].refCumple; 
						if (refCumple == "N") {//Pintamos la linea en rojo
							$("#gridP75PedidoDatosSinOferta").find("#"+ids[i]).find("td").addClass("p75_lineaResaltadaRojo");

							//pintamos la columna P.Costo tambien en rojo
							$("#gridP75PedidoDatosSinOferta #"+ (i+1) +"_precioCostoFinal").addClass("p75_precioCostofinalRojo");

						}
						
						if (tipo=='F' || tipo=='NF'){
							$("#gridP75PedidoDatosSinOferta").find("#"+ids[i]).find("[aria-describedby='gridP75PedidoDatosSinOferta_fechaEntrega']").addClass("p75_columnaResaltadaIntegradaFila");
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_fechaEntrega").addClass("p75_columnaResaltadaIntegradaFila");														
						}

					}

					//Si todos los registros a mostrar son de SIA, quitar la columna EnCurso2
					//if (data.esTodoSIA == 'S'){
					//jQuery(gridP70DetalladoPedido.nameJQuery).jqGrid('hideCol', ["enCurso2"]);
					//jQuery(gridP70DetalladoPedido.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
					//} else {
					//jQuery(gridP70DetalladoPedido.nameJQuery).jqGrid('showCol', ["enCurso2"]);
					//jQuery(gridP70DetalladoPedido.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
					//}

					cargarOrdenacionesColumnasP75();

				}
				$("#p70_AreaCajasEuros").show();

				$("#p70_AreaPestanas").show();
				$("#p70_AreaResultados").show();
				$("#p70_AreaEstadosPedido").show();
				$("#p70_btn_buscar").focus();

			} else {

				//$("#p70_informacion").hide();
				//$("#p70_AreaPestanas").hide();
				//$("#p70_AreaResultados").hide();

				$("#p70_AreaPestanas").show();
				$("#p70_AreaResultados").show();
				$("#p70_AreaEstadosPedido").show();
				$("#p70_btn_buscar").focus();
				$("#gbox_gridP75PedidoDatosSinOfertaTextil").hide();
				$("#gbox_gridP75PedidoDatosSinOferta").show();

				$('#gridP75PedidoDatosSinOfertaTextil').jqGrid('clearGridData');
				$('#gridP75PedidoDatosSinOferta').jqGrid('clearGridData');

			}		    
			//Muestra u oculta la columna de empuje.
			hideShowColumnEmpujeP75(data.mostrarEmpuje);
			$("#p70_AreaResultados .loading").css("display", "none");			
			
		},
		error : function (xhr, status, error){
			$("#p70_AreaResultados .loading").css("display", "none");	
			handleError(xhr, status, error, locale);				
		}			
	});		

}	
//Muestra la columna unidFlEmpuje si existe algún valor > 0 en las filas.
function hideShowColumnEmpujeP75(mostrarColumnaEmpuje){
	if(mostrarColumnaEmpuje == "S"){
		//Si existe un empuje >0, debe aparecer la columna de unidFlEmpuje.
		jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('showCol', ["unidFlEmpuje"]);
	} else{
		//Si no existe un empuje >0, no debe aparecer la columna de unidFlEmpuje.
		jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('hideCol', ["unidFlEmpuje"]);
	}
}

function reloadSubgridTextil(rowId, subgridDivId) {

	var rowData = jQuery(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).getRowData(rowId);
	var codId = rowData['codArticulo'];

	//var codId = rowId.substr(rowId.indexOf("_")+1); //Identificador para enlazar con las referencias hijas

	var detalladoPedido= new DetalladoPedido ($("#p70_cmb_seccion_b").val(),$("#p70_cmb_categoria_b").val(),
			$("#p70_fld_referencia").val(),null,codId, $("#centerId").val());

	var objJson = $.toJSON(detalladoPedido);	



	$.ajax({
		type : 'POST',
		url : './detalladoPedido/loadDataSubGridTextil.do?',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {	

			var subgridTableId = subgridDivId + "_t";
			$("#" + subgridDivId).html("<table id='" + subgridTableId + "'></table>");
			$("#" + subgridTableId).jqGrid({
				datatype: 'local',
				colNames : gridP75DetalladoPedidoSinOfertaTextil.subgridColNames,
				colModel : gridP75DetalladoPedidoSinOfertaTextil.subgridCm,
				//multiselect: true,
				altclass: "ui-priority-secondary",
				altRows: false,
				//rowNum: data.datos.records,
				sortable : true,
				height: '100%',
				width : "auto"
			});


			$("#" + subgridTableId)[0].addJSONData(data);
			$("#" + subgridTableId).actualPage = data.page;
			$("#" + subgridTableId).localData = data;


			$("#" + subgridTableId).closest("div.ui-jqgrid-view")
			.children("div.ui-jqgrid-hdiv")
			.hide();


			$("#AreaResultados .loading").css("display", "none");	


		},
		error : function (xhr, status, error){
			$("#AreaResultados .loading").css("display", "none");	
			handleError(xhr, status, error, locale);				
		}			
	});		

}

function p75ControlesPantallaTextil(){

	//Nos recorremos los registros existentes en pantalla.
	var rowsInPage = jQuery(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).jqGrid('getGridParam', 'rowNum');
	var existenRefConHijas = 0;

	for (i = 0; i < rowsInPage; i++){
		var idToDataIndex = $(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).jqGrid('getDataIDs');
		//Después busco dentro del array de rowId's el correspondiente a la fila
		var idFila = idToDataIndex[i];

		nivel = $("#"+(idFila)+"_nivelLote").val();

		//Si el nivel es 0 oculto el signo +
		if ((nivel == '0') || (nivel == 'null') || (nivel == null)){
			$("#"+idFila+" td.sgcollapsed",gridP75DetalladoPedidoSinOfertaTextil[0]).unbind('click').html('');

		} else {
			existenRefConHijas = 1;
		}
	}

}

function getCellValue(rowId, columnName) {
	var rowData = $("#gridP75PedidoDatosSinOferta").getRowData(rowId)[columnName];
	return rowData;
}
function getCellAtrribute(rowId, columnName) {
	var rowData = $("#gridP75PedidoDatosSinOferta").getRowData(rowId)[columnName].attr('class');
	return rowData;
}

function getCellValueTextil(rowId, columnName) {
	var rowData = $("#gridP75PedidoDatosSinOfertaTextil").getRowData(rowId)[columnName];
	return rowData;
}
function getCellAtrributeTextil(rowId, columnName) {
	var rowData = $("#gridP75PedidoDatosSinOfertaTextil").getRowData(rowId)[columnName].attr('class');
	return rowData;
}

function controlNavegacionP75(e) {
	var idActual = e.target.id;
	var idFoco;
	//Obtención de fila y columna actuales
	var fila = idActual.substring(0, idActual.indexOf("_"));
	var nombreColumna = idActual.substring(idActual.indexOf("_")+1);
	var validacionNavegacion = 'S';

	if ($("#gridP75PedidoDatosSinOfertaTextil").is(':visible')) {
		var gridActual = "gridP75PedidoDatosSinOfertaTextil";
	} else {
		var gridActual = "gridP75PedidoDatosSinOferta";
	}

	var key = e.which; //para soporte de todos los navegadores
	if (key == 13){//Tecla enter, guardado
		e.preventDefault();
		finder();
		e.stopPropagation();
	}
	//Flechas de cursores para navegación
	if (key == 37){//Tecla izquierda
		e.preventDefault();
	}
	if (key == 38){//Tecla arriba
		e.preventDefault();
		idFoco = (parseInt(fila,10)-1) + "_" + nombreColumna;
		$("#"+idFoco).focus();
		$("#"+idFoco).select();
	}
	if (key == 39){//Tecla derecha
		e.preventDefault();
	}
	if (key == 40){//Tecla abajo
		e.preventDefault();
		idFoco = (parseInt(fila,10)+1) + "_" + nombreColumna;
		$("#"+idFoco).focus();
		$("#"+idFoco).select();
	}
}


/*
function exportExcel(){
	var messageVal=findValidation();
	if (messageVal!=null){
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{

		var area = null;

		if ($("input[name='p70_rad_tipoFiltro']:checked").val() == "1"){ //Se esta consultando por estructura

			if (null != $("#p70_cmb_seccion").val()){
				var arrAreaSeccion = $("#p70_cmb_seccion").val().split("*");
				area = arrAreaSeccion[0]
			}

			if(null != arrAreaSeccion && arrAreaSeccion[0]==3){
				var colModel =$("#gridP70PedidoDatosTextil").jqGrid("getGridParam", "colModel");
				var colNames = $("#gridP70PedidoDatosTextil").jqGrid("getGridParam", "colNames");

			} else {
				var colModel =$("#gridP70PedidoDatos").jqGrid("getGridParam", "colModel");
				var colNames = $("#gridP70PedidoDatos").jqGrid("getGridParam", "colNames");
			}

		} else { //Se esta consultando por referencia

			var rowData = jQuery(gridP70DetalladoPedidoTextil.nameJQuery).getRowData(1);
			area = $("#1_grupo1").val();

			if( area ==3){
				var colModel =$("#gridP70PedidoDatosTextil").jqGrid("getGridParam", "colModel");
				var colNames = $("#gridP70PedidoDatosTextil").jqGrid("getGridParam", "colNames");

			} else {
				var colModel =$("#gridP70PedidoDatos").jqGrid("getGridParam", "colModel");
				var colNames = $("#gridP70PedidoDatos").jqGrid("getGridParam", "colNames");
			}

		}



		var newColname=new Array();
		var myColumns = new Array();
		var myColumnsWidth = new Array();

		j=0;
		for (i=0;i<colModel.length;i++){
			if (colModel[i].hidden==false){

				//Hay que proporcionar todas las columnas excepto la de estado
				if (colModel[i].name!="rn" && colModel[i].name != 'estadoGrid' && colModel[i].name != 'subgrid' && !colModel[i].hidden){
					newColname[j]=colNames [i];
					myColumns[j]=colModel[i].name;
					myColumnsWidth[j] = colModel[i].width;
					j++;
				}	    	
			}

		}

		var form = "<form name='csvexportform' action='detalladoPedido/exportGrid.do'  accept-charset='ISO-8859-1' method='get'>";
		form = form + "<input type='hidden' name='headers' value='"+newColname+"'>";
		form = form + "<input type='hidden' name='model' value='"+myColumns+"'>";
		form = form + "<input type='hidden' name='widths' value='"+myColumnsWidth+"'>";
		form = form + "<input type='hidden' name='index' value='"+gridP70DetalladoPedido.getSortIndex()+"'>";
		form = form + "<input type='hidden' name='sortOrder' value='"+gridP70DetalladoPedido.getSortOrder()+"'>";

		if ($("input[name='p70_rad_tipoFiltro']:checked").val() == "1"){

			if ($("#p70_cmb_seccion").combobox('getValue')!="null" && $("#p70_cmb_seccion").combobox('getValue')!=null  ){
				var arrAreaSeccion = $("#p70_cmb_seccion").val().split("*");
				form = form + "<input type='hidden' name='grupo2' value='"+arrAreaSeccion[1]+"'>";
			}	
			if ($("#p70_cmb_categoria").combobox('getValue')!="null" && $("#p70_cmb_categoria").combobox('getValue')!=null  ){
				form = form + "<input type='hidden' name='grupo3' value='"+$("#p70_cmb_categoria").val()+"'>";
			}	
			//Introducimos el valor del checkbox para realizar la búsqueda.
			form = form + "<input type='hidden' name='flgIncluirPropPed' value='"+flgIncluirPropPed+"'>";
		}
		if ($("input[name='p70_rad_tipoFiltro']:checked").val() != "1"){
			if ($("#p70_fld_referencia").val()!="" && $("#p70_fld_referencia").val()!=null ){
				form = form + "<input type='hidden' name='codigoArticulo' value='"+$("#p70_fld_referencia").val()+"'>";
			}
		}	

		form = form + "</form><script>document.csvexportform.submit();</script>";
		Show_Popup(form);	 
	}
}

 */


function hideShowColumnsP75(codArea, origen){

	// Permite conocer si se debe ejecutar un grupo de instrucciones.
	let ejecutar=0;
	
	if (null != codArea && codArea < 3){
		//Si el area es mayor que 3, debe aparecer la columna Nuevo
		jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('showCol', ["tipo"]);
		jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('setGridWidth', $("#p75_AreaSinOferta").width(), true);
		$("#p70_AreaNotaPie").show();
	//Si el area es 1,2 o 3, no debe aparecer la columna Nuevo
	} else{
		if (origen==1){
			if (!isColumnVisible('tipo')){
				ejecutar=1;
			}
		}else{
			ejecutar=1;
		}

		if (ejecutar==1){
			jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('hideCol', ["tipo"]);
			jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('setGridWidth', $("#p75_AreaSinOferta").width(), true);
			$("#p70_AreaNotaPie").hide();
		}
	}

}

function obtenerListaP75Modificados(saveChanges, esVegalsa){

	if (saveChanges!='N'){

		var ids = $("#gridP75PedidoDatosSinOferta").jqGrid('getDataIDs'), i, l = ids.length;
		var j=0;
		for (i = 0; i < l; i++) {	
			let estado=$("#gridP75PedidoDatosSinOferta #"+ids[i]+"_estadoGrid").val();

			if (esVegalsa){
				
				// Registro modificado.
				if (estado=='2'){
					addSeleccionadosP75($("#gridP75PedidoDatosSinOferta").getRowData(ids[i]), ids[i],'N');
				}

			}else{
				if (estado == "2" || estado == "-2" || estado == "5"){
					addSeleccionadosP75($("#gridP75PedidoDatosSinOferta").getRowData(ids[i]), ids[i],'S');
				}
			}
			
		}	
		for(var i = 0; i++ < mapP75.size; mapP75.next()){
			listadoModificadosP75[j] = mapP75.value();
			j=j+1;
		}
		return listadoModificadosP75;
	}else{
		deleteSeleccionadosP75();

		mapP75 = new Map;
		listadoModificadosP75 = new Array();
		return new Array();
	}

}

function obtenerListaP75ModificadosTextil(saveChanges, esVegalsa){

	if (saveChanges!='N'){

		var ids = $("#gridP75PedidoDatosSinOfertaTextil").jqGrid('getDataIDs'), i, l = ids.length;
		var j=0;
		for (i = 0; i < l; i++) {	
			let estado=$("#gridP75PedidoDatosSinOfertaTextil #"+ids[i]+"_estadoGrid").val();

			if (esVegalsa){
				
				// Registro modificado.
				if (estado=='2'){
					addSeleccionadosP75($("#gridP75PedidoDatosSinOfertaTextil").getRowData(ids[i]), ids[i],'N');
				}
				
			}else{
				if (estado == "2" || estado == "-2" || estado == "5"){
					addSeleccionadosP75($("#gridP75PedidoDatosSinOfertaTextil").getRowData(ids[i]), ids[i],'S');
				}
			}
			
		}	
		for(var i = 0; i++ < mapP75.size; mapP75.next()){
			listadoModificadosP75[j] = mapP75.value();
			j=j+1;

		}
		return listadoModificadosP75;
	}else{

		deleteSeleccionadosP75();

		mapP75 = new Map;
		listadoModificadosP75 = new Array();
		return new Array();
	}

}

function addSeleccionadosP75(rowData, fila, flgSIA){

	let gridActual;
	
	// Si TEXTIL.
	if (area==3){
		//if ($("#gridP75PedidoDatosSinOfertaTextil").is(':visible')) {
		gridActual = "gridP75PedidoDatosSinOfertaTextil";
	} else {
		gridActual = "gridP75PedidoDatosSinOferta";
	}
	
	let codCentro = $("#centerName").val().substring(0, $("#centerName").val().indexOf("-"));
//	let codMapa = $("#centerName").val().substring(0, $("#centerName").val().indexOf("-"));
	let codMapa = $("#p70_cmb_mapa").val();

	mapP75.put(rowData.codArticulo, new DetalladoPedidoModif( rowData.codArticulo,$("#" + gridActual + " #"+fila+"_cantidad").val()
															, $("#" + gridActual + " #"+fila+"_cantidadOriginal").val()
															, $("#" + gridActual + " #"+fila+"_cantidadUltValida").val()
															, 2, ""
															, $("#" + gridActual + " #"+fila+"_grupo1").val()
															, $("#" + gridActual + " #"+fila+"_grupo2").val()
															, $("#" + gridActual + " #"+fila+"_grupo3").val()
															, $("#" + gridActual + " #"+fila+"_grupo4").val()
															, $("#" + gridActual + " #"+fila+"_grupo5").val()
															, trasformToStringP75(rowData.stock)
															, trasformToStringP75(rowData.enCurso1)
															, trasformToStringP75(rowData.enCurso2)
															, $("#" + gridActual + " #"+fila+"_unidadesCaja").val()
															, $("#" + gridActual + " #"+fila+"_tipoDetallado").val()
															, rowData.descriptionArt,$("#" + gridActual + " #"+fila+"_resultadoWS").val()
															, $("#" + gridActual + " #"+fila+"_estadoGrid").val()
															, flgSIA
															, codCentro
															, codMapa
															));
}

function deleteSeleccionadosP75(){
	mapP75.removeAll();
	mapP75 = new Map;
	listadoModificadosP75 = new Array();
}

function trasformToStringP75(value){
	if(value != null) {
		var stringDouble= value.replace(".","");
		var stringDoubleFinal= stringDouble.replace(",",".");
	} else {
		var stringDoubleFinal = null;
	}

	return stringDoubleFinal;

}





function p75SetHeadersTitles(data){

	var colModel = $("#gridP75PedidoDatosSinOferta").jqGrid("getGridParam", "colModel");
	$.each(colModel, function(i) {
		if (colModel[i].name!="rn"){
			$("#jqgh_gridP75PedidoDatosSinOferta_"+colModel[i].name).attr("title",eval("data."+colModel[i].name+"Title"));
		}
	});
}

function p75SetHeadersTitlesTextil(data){

	var colModel = $("#gridP75PedidoDatosSinOfertaTextil").jqGrid("getGridParam", "colModel");
	$.each(colModel, function(i) {
		if (colModel[i].name!="rn"){
			$("#jqgh_gridP75PedidoDatosSinOfertaTextil_"+colModel[i].name).attr("title",eval("data."+colModel[i].name+"Title"));
		}
	});
}

function validacionCantidadP75(event){

	var fila = event.target.id.substring(0, event.target.id.indexOf("_"));

	var seccion = $("#"+fila+"_grupo2").val();
	var campoDivModificado = fila + "_det_divModificado";
	var campoDivGuardado = fila + "_det_divGuardado";
	var campoDivNuevo = fila + "_det_divNuevo";
	var campoDivError = fila + "_det_divError";	
	var campoDivInterrogante = fila + "_det_divInterrogante";
	var campoImgError = fila + "_det_imgError";


	if ($("#gridP75PedidoDatosSinOfertaTextil").is(':visible')) {
		var gridActual = "gridP75PedidoDatosSinOfertaTextil";
	} else {
		var gridActual = "gridP75PedidoDatosSinOferta";
	}

	var flgSIA = $("#" + gridActual + " #"+fila+"_flgSIA").val();
	var flgRedondeo ="";
	var estadoGrid =$("#" + gridActual + " #"+fila+"_estadoGrid").val();
	var unidadesCaja = $("#" + gridActual + " #"+fila+"_unidadesCaja").val()



	var cantidad = Number(event.target.value);
	var cantidadOriginal = Number($("#" + gridActual + " #"+fila+"_cantidadOriginal").val());
	var cantidadUltValida = Number($("#" + gridActual + " #"+fila+"_cantidadUltValida").val());
	var cantidadPropuesta = Number($("#" + gridActual + " #"+fila+"_cantidadPropuesta").val());
	var estadoPedido = $("#" + gridActual + " #"+fila+"_estadoPedido").val();



	//Si tipoUfp es U, redondeamos la cantida a un multiplo de dexenx
	if ($("#" + gridActual + " #"+fila+"_tipoUfp").val() == "U") {	
		var mod = cantidad%$("#" + gridActual + " #"+fila+"_dexenx").val();
		if( mod != 0){
			var suma = $("#" + gridActual + " #"+fila+"_dexenx").val() - mod;
			cantidad = cantidad + suma;
		} 
	}


	if ($(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).is(':visible')) {
		var rowData = jQuery(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).getRowData(fila);
		var codArticulo = rowData['codArticulo'];
	} else {
		var rowData = jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).getRowData(fila);
		var codArticulo = rowData['codArticulo'];
	}

	if (flgSIA == 'S') {

		//LLamar al redondeo de la cantidad

		//Buscamos la U para saber si es de tipiUFP
		var tipoUFP = $("#" + gridActual + " #"+fila+"_tipoUfp").val()
		var cantidadRedondeo = calcularRedondeoP75(cantidad, codArticulo, unidadesCaja,tipoUFP);
		if (cantidad != cantidadRedondeo) {
			cantidad = cantidadRedondeo;
			//if (estadoGrid != 5 && estadoGrid != 6) { //no se ha puesto con anteriordad la R
			//$("#"+ fila +"_cantidad").after('<span class="p70_tipoRedondeo">R</span>');
			//}
			$("#" + gridActual + " #"+fila+"_cantidadRedondeo").removeClass("p75_tipoRedondeoOculto");
			$("#" + gridActual + " #"+fila+"_cantidadRedondeo").removeClass("p75_tipoRedondeoVisible");	
			$("#" + gridActual + " #"+fila+"_cantidadRedondeo").addClass("p75_tipoRedondeoVisible");
			$("#p70_AreaNotaRedondeo").show();
			flgRedondeo = "S";
		} else {
			$("#" + gridActual + " #"+fila+"_cantidadRedondeo").removeClass("p75_tipoRedondeoOculto");
			$("#" + gridActual + " #"+fila+"_cantidadRedondeo").removeClass("p75_tipoRedondeoVisible");	
			$("#" + gridActual + " #"+fila+"_cantidadRedondeo").addClass("p75_tipoRedondeoOculto");
		}

	} 


	//if ((estadoPedido == "A") && (cantidadOriginal >= cantidad)) {
	if ((estadoPedido == "A") && (cantidadPropuesta >= cantidad)) {

		cantidad = cantidadUltValida;
		createAlert(msgBloqueoAlAlza, "ERROR");


	} else {


		$("#" + gridActual + " #"+fila+"_cantidadUltValida").val(cantidad);

		$("#" + gridActual + " #"+campoDivError).hide();
		$("#" + gridActual + " #"+campoDivGuardado).hide();
		$("#" + gridActual + " #"+campoDivNuevo).hide();
		$("#" + gridActual + " #"+campoDivInterrogante).hide();
		$("#" + gridActual + " #"+campoDivModificado).show(); 
		if (flgRedondeo == "S") {
			$("#" + gridActual + " #"+fila+"_estadoGrid").val("5"); //modificado con redondeo
		} else {
			$("#" + gridActual + " #"+fila+"_estadoGrid").val("2");
		}

	} 


	//GESTION DE EUROS, 

	//actualizacion del precio costo final en base al valor de la cantidad en el grid y tambien actualizamos la caja de Euros Finales.

	var precioCostoFinal =$("#" + gridActual + " #"+fila+"_precioCostoFinalAux").val();  //Valor del precio costo final antes de modificar las cajas y volver a recalcularlo 
	var eurosFinales = $("#p70_inputEurosFinales").val(); //Valor de del campo sumatorio Euros finales antes de actualizarlo

	eurosFinales = eurosFinales - precioCostoFinal; //Primero restamos el precio costo final de la referencia que estamos tratando.

	//Calculamos el nuevo precio costo final del la referencia que estamos tratando en base a la nueva cantidad
	var precioCostoArticulo =$("#" + gridActual + " #"+fila+"_precioCostoArticulo").val();


	//Atualizamos tambien el precio costo final en la tabla T_DETALLADO_PEDIDO
	precioCostoFinal = actualizarPrecioCostoFinalP75(codArticulo,precioCostoArticulo,cantidad,unidadesCaja);

	//Mostramos la alerta.
	ponerModificado();

	//Borramos la diferencia
	$("#" + gridActual + " #"+fila+"_diferencia").removeClass("p75_diferenciaVisible");
	$("#" + gridActual + " #"+fila+"_diferencia").addClass("p75_diferenciaOculto");

	//Lo pintamos en la columna P.Costo correspondiente y tambien en el hidden del campo estado
	$("#" + gridActual + " #"+fila+"_precioCostoFinal").html(precioCostoFinal);
	$("#" + gridActual + " #"+fila+"_precioCostoFinalAux").val(precioCostoFinal);

	//y Finalmente lo añadimos al campo Euros finales (que es la suma de todos los precios finales del grid)
	eurosFinales = eurosFinales + precioCostoFinal;
	$("#p70_inputEurosFinales").val($.formatNumber(eurosFinales,{format:"0",locale:"es"}));

	//actualizamos tambien la caja de cajas Finales.

	var cantidadAux =$("#" + gridActual + " #"+fila+"_cantidadAux").val();  //Valor de la cantidad antes de modificar las cajas y volver a recalcularlo 
	var cajasFinales = $("#p70_inputCajasFinalesHidden").val(); //Valor de del campo sumatorio Cajas finales antes de actualizarlo

	cajasFinales = cajasFinales - cantidadAux; //Primero restamos la cantidad de la referencia que estamos tratando.

	$("#" + gridActual + " #"+fila+"_cantidadAux").val(cantidad); //Guardamos la nueva cantidad en cantidadAux para proximas ocasiones

	//y Finalmente añadimos la nueva cantidad de cajas al campo Cajas finales (que es la suma de todas las cajas del grid)
	cajasFinales = cajasFinales + cantidad;
	$("#p70_inputCajasFinales").text(cajasFinales);
	$("#p70_inputCajasFinalesHidden").val(cajasFinales);
	event.target.value = cantidad;

}


function calcularRedondeoP75(cantidad, codArticulo, unidadesCaja,tipoUFP){

	var resultado = cantidad;

	$.ajax({
		type : 'POST',
		url : './detalladoPedido/calcularRedondeo.do?codCentro=' + $("#centerId").val() + '&codArticulo=' + codArticulo +'&cantidad='+cantidad  +'&unidadesCaja='+unidadesCaja +'&tipoUFP='+ tipoUFP,
		//data : objJson,
		contentType : "application/json; charset=utf-8",
		cache : false,
		dataType : "json",
		async : false,
		success : function(data) {	

			if (data != null && data != ""){
				resultado = data;
			}

		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});	

	return resultado;
}


function actualizarPrecioCostoFinalP75(codArticulo, precioCostoArticulo,cantidad, unidadesCaja){

	$.ajax({
		type : 'POST',
		url : './detalladoPedido/actualizarPrecioCostoFinal.do?codCentro=' + $("#centerId").val() + '&codArticulo=' + codArticulo +'&cantidad='+cantidad  +'&precioCostoArticulo='+precioCostoArticulo +'&unidadesCaja='+unidadesCaja,
		//data : objJson,
		contentType : "application/json; charset=utf-8",
		cache : false,
		dataType : "json",
		async : false,
		success : function(data) {	
			if (data !== null && data !== ""){
				resultado = data;
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});	

	return resultado;

}

function cargarOrdenacionesColumnasP75(){

	var moved=false;	
	var colModel = $("#gridP75PedidoDatosSinOferta").jqGrid("getGridParam", "colModel");

	$.each(colModel, function(i) {
		if (colModel[i].name!="rn"){


			$("#jqgh_gridP75PedidoDatosSinOferta_" + colModel[i].name).unbind('mousedown');
			$("#jqgh_gridP75PedidoDatosSinOferta_" + colModel[i].name).unbind('mousemove');
			$("#jqgh_gridP75PedidoDatosSinOferta_" + colModel[i].name).unbind('mouseup');
			$("#jqgh_gridP75PedidoDatosSinOferta_" + colModel[i].name).bind("mousedown", function() {
				moved=false;
			});
			$("#jqgh_gridP75PedidoDatosSinOferta_" + colModel[i].name).bind("mousemove", function() {
				moved=true;
			});
			$("#jqgh_gridP75PedidoDatosSinOferta_" + colModel[i].name).bind("mouseup", function() {
				if(!moved){
					actualizarSortOrderP75(colModel[i].name);
					$('#gridP75PedidoDatosSinOferta').setGridParam({sortname:colModel[i].name});
					$('#gridP75PedidoDatosSinOferta').setGridParam({page:1});
					reloadGridP75Filtros('S');
				}
			});

		}
	});
}

function cargarOrdenacionesColumnasP75Textil(){

	var moved=false;	
	var colModel = $("#gridP75PedidoDatosSinOfertaTextil").jqGrid("getGridParam", "colModel");

	$.each(colModel, function(i) {
		if (colModel[i].name!="rn"){


			$("#jqgh_gridP75PedidoDatosSinOfertaTextil_" + colModel[i].name).unbind('mousedown');
			$("#jqgh_gridP75PedidoDatosSinOfertaTextil_" + colModel[i].name).unbind('mousemove');
			$("#jqgh_gridP75PedidoDatosSinOfertaTextil_" + colModel[i].name).unbind('mouseup');
			$("#jqgh_gridP75PedidoDatosSinOfertaTextil_" + colModel[i].name).bind("mousedown", function() {
				moved=false;
			});
			$("#jqgh_gridP75PedidoDatosSinOfertaTextil_" + colModel[i].name).bind("mousemove", function() {
				moved=true;
			});
			$("#jqgh_gridP75PedidoDatosSinOfertaTextil_" + colModel[i].name).bind("mouseup", function() {
				if(!moved){
					actualizarSortOrderTextilP75(colModel[i].name);
					$('#gridP75PedidoDatosSinOfertaTextil').setGridParam({sortname:colModel[i].name});
					$('#gridP75PedidoDatosSinOfertaTextil').setGridParam({page:1});
					reloadGridP75Filtros('S');
				}
			});

		}
	});
}



function actualizarSortOrderP75 (columna) {

	var ultimoSortName =  $('#gridP75PedidoDatosSinOferta').jqGrid('getGridParam','sortname');
	var ultimoSortOrder =  $('#gridP75PedidoDatosSinOferta').jqGrid('getGridParam','sortorder');


	if (ultimoSortName != columna ) { //Se ha cambiado la columna por la que se quiere ordenar


		$('#gridP75PedidoDatosSinOferta').setGridParam({sortorder:'asc'});
		$("#gridP75PedidoDatosSinOferta_" + columna + " [sort='asc']").removeClass("ui-state-disabled");

		//En la columna anterior quitamos la flechas
		if (ultimoSortOrder=='asc'){
			$("#gridP75PedidoDatosSinOferta_" + ultimoSortName + " [sort='asc']").addClass("ui-state-disabled");
		}else{
			$("#gridP75PedidoDatosSinOferta_" + ultimoSortName + " [sort='desc']").addClass("ui-state-disabled");
		}

	} else { //Seguimos en la misma columna

		if (ultimoSortOrder=='asc'){
			$('#gridP75PedidoDatosSinOferta').setGridParam({sortorder:'desc'});
			//Mostramos la flecha descendente y quitamos la flecha ascendente
			$("#gridP75PedidoDatosSinOferta_"  + columna + " [sort='desc']").removeClass("ui-state-disabled");
			$("#gridP75PedidoDatosSinOferta_"  + columna + " [sort='asc']").addClass("ui-state-disabled");
		} else {
			$('#gridP75PedidoDatosSinOferta').setGridParam({sortorder:'asc'});
			$("#gridP75PedidoDatosSinOferta_"  + columna + " [sort='asc']").removeClass("ui-state-disabled");
			$("#gridP75PedidoDatosSinOferta_"  + columna + " [sort='desc']").addClass("ui-state-disabled");
		}

	}
}

function actualizarSortOrderTextilP75 (columna) {

	var ultimoSortName =  $('#gridP75PedidoDatosSinOfertaTextil').jqGrid('getGridParam','sortname');
	var ultimoSortOrder =  $('#gridP75PedidoDatosSinOfertaTextil').jqGrid('getGridParam','sortorder');


	if (ultimoSortName != columna ) { //Se ha cambiado la columna por la que se quiere ordenar


		$('#gridP75PedidoDatosSinOfertaTextil').setGridParam({sortorder:'asc'});
		$("#gridP75PedidoDatosSinOfertaTextil_" + columna + " [sort='asc']").removeClass("ui-state-disabled");

		//En la columna anterior quitamos la flechas
		if (ultimoSortOrder=='asc'){
			$("#gridP75PedidoDatosSinOfertaTextil_" + ultimoSortName + " [sort='asc']").addClass("ui-state-disabled");
		}else{
			$("#gridP75PedidoDatosSinOfertaTextil_" + ultimoSortName + " [sort='desc']").addClass("ui-state-disabled");
		}

	} else { //Seguimos en la misma columna

		if (ultimoSortOrder=='asc'){
			$('#gridP75PedidoDatosSinOfertaTextil').setGridParam({sortorder:'desc'});
			//Mostramos la flecha descendente y quitamos la flecha ascendente
			$("#gridP75PedidoDatosSinOfertaTextil_"  + columna + " [sort='desc']").removeClass("ui-state-disabled");
			$("#gridP75PedidoDatosSinOfertaTextil_"  + columna + " [sort='asc']").addClass("ui-state-disabled");
		} else {
			$('#gridP75PedidoDatosSinOfertaTextil').setGridParam({sortorder:'asc'});
			$("#gridP75PedidoDatosSinOfertaTextil_"  + columna + " [sort='asc']").removeClass("ui-state-disabled");
			$("#gridP75PedidoDatosSinOfertaTextil_"  + columna + " [sort='desc']").addClass("ui-state-disabled");
		}

	}
}



//EL RESTO DE FUNCIONES SON PARA COLOREAR LA FILA DE AZUL CUANDO TIENE OFERTA (ESTO PARA EL P75 CREO QUE NO DEBERIA ESTAR...)

//Función que colorea las filas y pone los eventos del popup de ofertas
function colorearFilasEnOfertaYEventoPopUpOferta(fila,tabla){
	var filaOferta = $("#"+(fila+1)+"_flgOferta").val();
	if(filaOferta == "S"){
		$(tabla).find("#"+(fila+1)).find("td").addClass("p70_columnaResaltadaOferta");
		//event_loadPopUpAyudaP70(fila+1,tabla);
	}else{
		//event_closePopUpAyudaP70(fila+1);
	}
}

function event_loadPopUpAyudaP70(fila,tabla){
	$("#"+fila+"_cantidad").focus(function(event) {
		if(!$("#p56_AreaAyuda").is(':visible')){
			loadPopUpAyudaP70(event,tabla)
		}else{
			if (controlMostradoAyuda(event)){
				$("#p56_AreaAyuda").dialog('close');
				loadPopUpAyudaP70(event,tabla)	
			}
		}
	});
}

function event_closePopUpAyudaP70(fila){
	$("#"+fila+"_cantidad").focus(function(event) {
		if($("#p56_AreaAyuda").is(':visible')){
			$("#p56_AreaAyuda").dialog('close');
		}
	});
}

function controlMostradoAyuda(event){
	//Si la fila para la que se va a tratar la ayuda es diferente a la que se está tratando hasta ahora
	//se forzará para generar una nueva ayuda
	var $focused = $(event.target);
	var id = $focused.attr("id");
	var i = id.indexOf("_");
	var rowId = id.substring(0, i);

	var llamarAyuda = false;
	if($("#p46_fld_ValidarCant_Selecc").val() == null || $("#p46_fld_ValidarCant_Selecc").val()==''){
		if (filaAyuda == null || filaAyuda!=rowId){
			filaAyuda = rowId;
			llamarAyuda = true;
		}
	}
	return llamarAyuda;
}

function loadPopUpAyudaP70(event,tabla){
	var $focused = $(event.target);
	var id = $focused.attr("id");
	var i = id.indexOf("_");
	var rowId = id.substring(0, i);
	defineCampoFoco($focused);
	var rowData = jQuery(tabla).getRowData(rowId);

	var codArt = $("#"+rowId+"_codArticulo").val();
	var oferta = ($("#"+rowId+"_oferta").val()=="null"?null:$("#"+rowId+"_oferta").val());

	var v_Oferta = new VOferta($("#centerId").val(),codArt);
	load_cmbVentasUltimasOfertasP56(v_Oferta,oferta);
	//$focused.focus();
}

/*
 * Obtiene todos los filtros en json para ser pasados como parametro GridFilters
 * @param grid
 * @returns filtros en json para obtener los registros deseados
 */ 
var getFiltrosJson = function (grid){
	
	var filtros="";
	var rules=new Array();
	var postData = jQuery(grid.nameJQuery).jqGrid('getGridParam', 'postData');
	var filtroPostData=postData.filters;
	if (typeof filtroPostData != 'undefined'){
		var objRules=JSON.parse(filtroPostData).rules;
		//Si se han introducido parametros (en los campos de busqueda de la tabla) los cargo
		if (objRules.length>0){
			for(var i=0;i<objRules.length; i++){
				var objRules=JSON.parse(filtroPostData).rules;
				filtros+=objRules[i].field+"="+objRules[i].data+"**";
			}
		}
	}
	return filtros;
}

function reloadGridP75Filtros(saveChanges){
	var listaModificadosP75 = null;

	area = null;
	var seccion = null;	
	var detalladoPedido = null;	

	var selectedRadioButton = $("input[name='p70_rad_tipoFiltro']:checked").val();
	
	//Se esta consultando "Por estructura"
	if (selectedRadioButton == "1"){

		var seccion = '';
		if (null != seccionSel){
			seccion = seccionSel;
		} else {
			var arrAreaSeccion = $("#p70_cmb_seccion").val().split("*");
			if ("" != arrAreaSeccion[0] && "null" != arrAreaSeccion[0]){
				area = arrAreaSeccion[0];
			}
			if ("" != arrAreaSeccion[1] && "null" != arrAreaSeccion[1]){
				seccion = arrAreaSeccion[1];
			}
		}
		
		var categoria = '';
		if (null != categoriaSel){
			categoria = categoriaSel;
		} else {
			if ($("#p70_cmb_categoria").val() != null) {
				var arrAreaCategoria = $("#p70_cmb_categoria").val().split("*");
				if ("" != arrAreaCategoria[0] && "null" != arrAreaCategoria[0]){
					categoria = arrAreaCategoria[0];
				}
			}
		}
		
		if(area==3){
			listaModificadosP75 = obtenerListaP75ModificadosTextil('S', dataFromDetalladoPedidoJSP.isCentroVegalsa());
			listaModificadosP76 = obtenerListaP76ModificadosTextil('S', dataFromDetalladoPedidoJSP.isCentroVegalsa());
		} else {
			listaModificadosP75 = obtenerListaP75Modificados('S', dataFromDetalladoPedidoJSP.isCentroVegalsa());
			listaModificadosP76 = obtenerListaP76Modificados('S', dataFromDetalladoPedidoJSP.isCentroVegalsa());
		}

		detalladoPedido = new DetalladoPedido (seccion,categoria,null,listaModificadosP75);

	//Se esta consultando "Por referencia"
	} else if (selectedRadioButton == "2"){

		var rowData = jQuery(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).getRowData(1);
		area = $("#1_grupo1").val();
//		seccion = $("#1_grupo2").val();
		if (area ==3){
			listaModificadosP75 = obtenerListaP75ModificadosTextil('S', dataFromDetalladoPedidoJSP.isCentroVegalsa());
			listaModificadosP76 = obtenerListaP76ModificadosTextil('S', dataFromDetalladoPedidoJSP.isCentroVegalsa());
		} else {
			listaModificadosP75 = obtenerListaP75Modificados('S', dataFromDetalladoPedidoJSP.isCentroVegalsa());
			listaModificadosP76 = obtenerListaP76Modificados('S', dataFromDetalladoPedidoJSP.isCentroVegalsa());
		}
		detalladoPedido= new DetalladoPedido (null,null,$("#p70_fld_referencia").val(),listaModificadosP75);
		
	//Se esta consultando "Por mapa"
	} else if (selectedRadioButton == "3"){

		listaModificadosP75 = obtenerListaP75Modificados('S', dataFromDetalladoPedidoJSP.isCentroVegalsa());
		listaModificadosP76 = obtenerListaP76Modificados('S', dataFromDetalladoPedidoJSP.isCentroVegalsa());

		let codMapa = $("#p70_cmb_mapa").val();

		detalladoPedido = new DetalladoPedido ();
		
		if (null != codMapa && codMapa > 0){
			detalladoPedido.codMapa = codMapa; 
		}
		  
	}

	var gridP75 = null;
	if (area==3) {
		gridP75 = gridP75DetalladoPedidoSinOfertaTextil;
	} else { 
		gridP75 = gridP75DetalladoPedidoSinOferta;
	}	
	var filtrosTabla = getFiltrosJson(gridP75);
	//Miramos si el checkbox está seleccionado o no.
	if ($("#p70_chk_IncluirPedido").is(':visible')) { //Si el check es visible, la busqueda dependera si el checkbox está seleccionado o no.
		//Miramos si el checkbox está seleccionado o no.
		flgIncluirPropPed = 'N';
		if($("#p70_chk_IncluirPedido").prop('checked')){
			flgIncluirPropPed = 'S';
		}
	} else { //Si el check NO es visible, la busqueda actuara como si el check estuviese seleccionado.
		flgIncluirPropPed = 'S';
	}
	
	//Introducimos el valor del checkbox para realizar la búsqueda.
	detalladoPedido.flgIncluirPropPed = flgIncluirPropPed;

	var objJson = $.toJSON(detalladoPedido);	

	$.ajax({
		type : 'POST',
		url : './detalladoPedido/loadDataGridSinOferta.do?saveChanges='+saveChanges+'&page='+gridP75.getActualPage()+'&max='+gridP75.getRowNumPerPage()+'&index='+gridP75.getSortIndex()+'&sortorder='+gridP75.getSortOrder()+'&filtrosTabla='+filtrosTabla,
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {
			if (data.datos.records > 0){

				var permisos = $("#userPermision").val();

				/*
				 * MISUMI-300
				 */
				if (dataFromDetalladoPedidoJSP.isCentroVegalsa()){
					//Si no hay registros de SIA 
					//ocultar columnas Aprov, Cur2, C.encargos
					if (!dataFromDetalladoPedidoJSP.hasAnyRecordFromSIA()){
						if (!isColumnVisible('tipoAprovisionamiento')){
							jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('hideCol', ["tipoAprovisionamiento"]);
						}
						if (!isColumnVisible('enCurso2')){
							jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('hideCol', ["enCurso2"]);
						}
						if (!isColumnVisible('cajasPedidas')){
							jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('hideCol', ["cajasPedidas"]);
						}
						$(".ui-jqgrid-bdiv").css('overflow-x', 'auto');
//						jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('setGridWidth', $("#p75_AreaSinOferta").width(), false);
					}
				}else{
					//Ocultar columnas mapa, motivo y facing (MISUMI-414)
					// SÓLO se ocultará la columna si previamente no se había elegido
					// que fuera visible añadiéndola desde el "Filtro de Columnas".
					if (!isColumnVisible('codMapa')){
						jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('hideCol', ["codMapa"]);
					}
					if (!isColumnVisible('motivoPedido')){
						jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('hideCol', ["motivoPedido"]);
					}
					if (!isColumnVisible('facing')){
						jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('hideCol', ["facing"]);
					}
					if (!isColumnVisible('cajasCortadas')){
						jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('hideCol', ["cajasCortadas"]);
					}
					if (!isColumnVisible('incPrevisionVenta')){
						jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('hideCol', ["incPrevisionVenta"]);
					}
					if (!isColumnVisible('smEstatico')){
						jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('hideCol', ["smEstatico"]);
					}
					$(".ui-jqgrid-bdiv").css('overflow-x', 'visible');
//					jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('setGridWidth', $("#p75_AreaSinOferta").width(), true);
				}

				$("#p70_informacion").show();

				$("#p70_AreaPestanas").show();
				$("#p70_AreaResultados").show();

				$("#p70_AreaEstadosPedido").show();

				//$("#p70_AreaNotaCarne").hide();
				$("#p70_AreaNotaDexenx").hide();
				$("#p70_AreaNotaPie").hide();
				$("#p70_AreaNotaBloqueoAlAlza").hide();
				$("#p70_AreaNotaRedondeo").hide();

				$("div#p70_AreaPestanas").attr("style", "display:block");

				$("#p70_pestanaSinOfertaCargada").val("S");
				//$("#p70_pestanaOfertaCargada").val("");

				//GESTION DE EUROS
				//$("#p70_inputEurosIniciales").text(Math.round(data.eurosIniciales));
				//$("#p70_inputEurosFinales").val(Math.round(data.eurosFinales));
				//$("#p70_inputEurosFinalesHidden").val(Math.round(data.eurosFinales));
				$("#p70_inputEurosFinales").filter_input({regex:'[0-9]'});

				$("#p70_inputEurosIniciales").text(data.eurosIniciales);
				$("#p70_inputEurosFinales").val(data.eurosFinales);
				$("#p70_inputEurosFinalesHidden").val(data.eurosFinales);
				$("#p70_inputCajasIniciales").text(data.cajasIniciales);
				$("#p70_inputCajasFinales").text(data.cajasFinales);
				$("#p70_inputCajasFinalesHidden").val(data.cajasFinales);

				if ($("input[name='p70_rad_tipoFiltro']:checked").val() == "2"){
					area = data.datos.rows[0].grupo1;
					seccion = data.datos.rows[0].grupo2;
				}
				
				//if( arrAreaSeccion[0]==3){ // Se va a mostrar el grid de textil
				if (area==3){

					if(permisos.indexOf('50_DETALLADO_GESTION_EUROS') > 0 || permisos.indexOf('51_DETALLADO_GESTION_EUROS_CONS') > 0){	
						jQuery(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).jqGrid('showCol', ["precioCostoFinal"]);
					}else{
						jQuery(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).jqGrid('hideCol', ["precioCostoFinal"]);
					}

					$("#gbox_gridP75PedidoDatosSinOferta").hide();
					$("#gbox_gridP75PedidoDatosSinOfertaTextil").show();


					$('#gridP75PedidoDatosSinOferta').jqGrid('clearGridData');

					$("#gridP75PedidoDatosSinOfertaTextil")[0].addJSONData(data.datos);
					gridP75DetalladoPedidoSinOfertaTextil.actualPage = data.datos.page;
					gridP75DetalladoPedidoSinOfertaTextil.localData = data.datos;

					fulfilInformation();

					p75ControlesPantallaTextil();

					if (($("input[name='p70_rad_tipoFiltro']:checked").val() == "2") && (data.datos.rows[0].nivelLote >0)){ //La consulta ha sido por referencia 
						$("#1 td.sgcollapsed",gridP75DetalladoPedidoSinOfertaTextil[0]).click();
					}

					var ids = jQuery(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).jqGrid('getDataIDs'), i, l = ids.length;

					$("#p70_AreaPestanas").hide();
					$("#p70_AreaResultados").hide();

					for (i = 0; i < l; i++) {

						var estado= data.datos.rows[i].estadoPedido; //B o I
						var estadoGrid= data.datos.rows[i].estadoGrid; 
						let tipo=data.datos.rows[i].tipo; 

						if (estado=="I"){
							//Integrado --> No modificable
							$("#gridP75PedidoDatosSinOfertaTextil").find("#"+ids[i]).find("[aria-describedby='gridP75PedidoDatosSinOfertaTextil_cantidad']").addClass("p75_columnaResaltadaIntegradaFila");
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaIntegradaFila");														
						}else if (estado=="E"){
							//Empuje -->  No modificable
							$("#gridP75PedidoDatosSinOfertaTextil").find("#"+ids[i]).find("[aria-describedby='gridP75PedidoDatosSinOfertaTextil_cantidad']").addClass("p75_columnaResaltadaEmpujeFila");
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaEmpujeFila");
						}else if (estado=="R"){
							//Guardado sin integrar
							jQuery(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).jqGrid('editRow', ids[i], false);
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaGuardadoSinIntegrarFila");
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").filter_input({regex:'[0-9]'});
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").change(function(event) {
								existenDatosSinGuardar = true;
								validacionCantidadP75(event);
							});
							var idRedondeo = '<span id="' + (i+1)+ '_cantidadRedondeo" class="p75_tipoRedondeoOculto">R</span>';
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").after(idRedondeo);
						}else if (estado=="B"){
							//Bloqueado
							$("#gridP75PedidoDatosSinOfertaTextil").find("#"+ids[i]).find("[aria-describedby='gridP75PedidoDatosSinOfertaTextil_cantidad']").addClass("p75_columnaResaltadaBloqueadaFila").removeClass("p75_columnaResaltadaOferta");
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").removeClass("editable").addClass("p75_columnaResaltadaBloqueadaFila").removeClass("p75_columnaResaltadaOferta");													
						}	
						else if (estado=="A"){
							//Bloqueado al Alza
							jQuery(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).jqGrid('editRow', ids[i], false);
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaBloqueadaAlAlzaFila");
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").filter_input({regex:'[0-9]'});
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").change(function(event) {
								existenDatosSinGuardar = true;
								validacionCantidadP75(event);
							});
							var idRedondeo = '<span id="' + (i+1)+ '_cantidadRedondeo" class="p75_tipoRedondeoOculto">R</span>';
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").after(idRedondeo);
							$("#p70_AreaNotaBloqueoAlAlza").show();
						}
						else{
							jQuery(gridP75DetalladoPedidoSinOfertaTextil.nameJQuery).jqGrid('editRow', ids[i], false);
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaSinGuardarFila");
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").filter_input({regex:'[0-9]'});
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").change(function(event) {
								existenDatosSinGuardar = true;
								validacionCantidadP75(event);
							});
							var idRedondeo = '<span id="' + (i+1)+ '_cantidadRedondeo" class="p75_tipoRedondeoOculto">R</span>';
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidad").after(idRedondeo);
						}

						if (estadoGrid == 5 || estadoGrid == 6) { //modificado/nuevo con redondeo
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidadRedondeo").removeClass("p75_tipoRedondeoOculto");
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidadRedondeo").removeClass("p75_tipoRedondeoVisible");

							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_cantidadRedondeo").addClass("p75_tipoRedondeoVisible");

							$("#p70_AreaNotaRedondeo").show();
						}

						//GESTION EUROS - resaltar linea en rojo cuando refCumple == N
						var refCumple= data.datos.rows[i].refCumple; 
						if (refCumple == "N") {//Pintamos la linea en rojo
							$("#gridP75PedidoDatosSinOfertaTextil").find("#"+ids[i]).find("td").addClass("p75_lineaResaltadaRojo");

							//pintamos la columna P.Costo tambien en rojo
							$("#gridP75PedidoDatosSinOfertaTextil #"+ (i+1) +"_precioCostoFinal").addClass("p75_precioCostofinalRojo");
						}
						
						if (tipo=='F' || tipo=='NF'){
							$("#gridP75PedidoDatosSinOfertaTextil").find("#"+ids[i]).find("[aria-describedby='gridP75PedidoDatosSinOfertaTextil_fechaEntrega']").addClass("p75_columnaResaltadaIntegradaFila");
							$("#gridP75PedidoDatosSinOfertaTextil #"+(i+1)+"_fechaEntrega").addClass("p75_columnaResaltadaIntegradaFila");														
						}

					}

					cargarOrdenacionesColumnasP75Textil();

				} else {
					//Mostrará o quitara las columnas de textil
					//El parámetro con valor 1 indica que se está llamando posterior a la llamada inicial de la carga del grid.(Paginación, ordenación, ...).
					hideShowColumnsP75(area, 1);

					if (seccion == 2 || seccion == 4 || seccion == 7){

						jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('showCol', ["tipoUFP"]);
						//$("#p70_AreaNotaCarne").show();
						$("#p70_AreaNotaDexenx").show();
					} else {
						jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('hideCol', ["tipoUFP"]);
						//$("#p70_AreaNotaCarne").hide();
						$("#p70_AreaNotaDexenx").hide();
					}

					if(permisos.indexOf('50_DETALLADO_GESTION_EUROS') > 0 || permisos.indexOf('51_DETALLADO_GESTION_EUROS_CONS') > 0){	
						jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('showCol', ["precioCostoFinal"]);
					}else{
						jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('hideCol', ["precioCostoFinal"]);
					}

					jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('setGridWidth', $("#p75_AreaSinOferta").width(), true);
					$("#gbox_gridP75PedidoDatosSinOfertaTextil").hide();
					$("#gbox_gridP75PedidoDatosSinOferta").show();

					$('#gridP75PedidoDatosSinOfertaTextil').jqGrid('clearGridData');


					$("#gridP75PedidoDatosSinOferta")[0].addJSONData(data.datos);
					gridP75DetalladoPedidoSinOferta.actualPage = data.datos.page;
					gridP75DetalladoPedidoSinOferta.localData = data.datos;

					fulfilInformation();

					var ids = jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('getDataIDs'), i, l = ids.length;

					$("#p70_AreaPestanas").hide();
					$("#p70_AreaResultados").hide();
					for (i = 0; i < l; i++) {						

						//var estado=$("#gridP70PedidoDatos").getRowData(ids[i]).estadoPedido; //B o I
						var estado= data.datos.rows[i].estadoPedido; //B o I
						var estadoGrid= data.datos.rows[i].estadoGrid; 
						var tipoReferencia = data.datos.rows[i].grupo2; 
						let tipo = data.datos.rows[i].tipo; 

						if (estado=="I"){
							//Integrado --> No modificable
							$("#gridP75PedidoDatosSinOferta").find("#"+ids[i]).find("[aria-describedby='gridP75PedidoDatosSinOferta_cantidad']").addClass("p75_columnaResaltadaIntegradaFila");
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaIntegradaFila");														
						}else if (estado=="E"){
							//Empuje -->  No modificable
							$("#gridP75PedidoDatosSinOferta").find("#"+ids[i]).find("[aria-describedby='gridP75PedidoDatosSinOferta_cantidad']").addClass("p75_columnaResaltadaEmpujeFila");
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaEmpujeFila");
						}else if (estado=="R"){
							//Guardado sin integrar
							jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('editRow', ids[i], false);
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaGuardadoSinIntegrarFila");
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").filter_input({regex:'[0-9]'});
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").change(function(event) {
								existenDatosSinGuardar = true;
								validacionCantidadP75(event);
							});
							var idRedondeo = '<span id="' + (i+1)+ '_cantidadRedondeo" class="p75_tipoRedondeoOculto">R</span>';
							$("#"+(i+1)+"_cantidad").after(idRedondeo);
						}else if (estado=="B"){
							//Bloqueado
							$("#gridP75PedidoDatosSinOferta").find("#"+ids[i]).find("[aria-describedby='gridP75PedidoDatosSinOferta_cantidad']").addClass("p75_columnaResaltadaBloqueadaFila").removeClass("p75_columnaResaltadaOferta");
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").removeClass("editable").addClass("p75_columnaResaltadaBloqueadaFila").removeClass("p75_columnaResaltadaOferta");												
						}else if (estado=="A"){
							//Bloqueado al Alza
							jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('editRow', ids[i], false);
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaBloqueadaAlAlzaFila");
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").filter_input({regex:'[0-9]'});
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").change(function(event) {
								existenDatosSinGuardar = true;
								validacionCantidadP75(event);
							});
							var idRedondeo = '<span id="' + (i+1)+ '_cantidadRedondeo" class="p75_tipoRedondeoOculto">R</span>';
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").after(idRedondeo);

							$("#p70_AreaNotaBloqueoAlAlza").show();
						}else{
							jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('editRow', ids[i], false);
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaSinGuardarFila");
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").filter_input({regex:'[0-9]'});
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").change(function(event) {
								existenDatosSinGuardar = true;
								validacionCantidadP75(event);
							});
							var idRedondeo = '<span id="' + (i+1)+ '_cantidadRedondeo" class="p75_tipoRedondeoOculto">R</span>';
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").after(idRedondeo);

						}
						$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidad").after(p75FormatCampoCantidad(estado,tipoReferencia,data.datos.rows[i].tipoUFP, data.datos.rows[i].dexenx, data.datos.rows[i].diferencia, i));

						if (estadoGrid == 5 || estadoGrid == 6) { //modificado/nuevo con redondeo
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidadRedondeo").removeClass("p75_tipoRedondeoOculto");
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidadRedondeo").removeClass("p75_tipoRedondeoVisible");

							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_cantidadRedondeo").addClass("p75_tipoRedondeoVisible");

							$("#p70_AreaNotaRedondeo").show();
						}

						//GESTION EUROS - resaltar linea en rojo cuando refCumple == N
						var refCumple= data.datos.rows[i].refCumple; 
						if (refCumple == "N") {//Pintamos la linea en rojo
							$("#gridP75PedidoDatosSinOferta").find("#"+ids[i]).find("td").addClass("p75_lineaResaltadaRojo");

							//pintamos la columna P.Costo tambien en rojo
							$("#gridP75PedidoDatosSinOferta #"+ (i+1) +"_precioCostoFinal").addClass("p75_precioCostofinalRojo");

						}
						
						if (tipo=='F' || tipo=='NF'){
							$("#gridP75PedidoDatosSinOferta").find("#"+ids[i]).find("[aria-describedby='gridP75PedidoDatosSinOferta_fechaEntrega']").addClass("p75_columnaResaltadaIntegradaFila");
							$("#gridP75PedidoDatosSinOferta #"+(i+1)+"_fechaEntrega").addClass("p75_columnaResaltadaIntegradaFila");														
						}

					}

					//Si todos los registros a mostrar son de SIA, quitar la columna EnCurso2
					//if (data.esTodoSIA == 'S'){
					//jQuery(gridP70DetalladoPedido.nameJQuery).jqGrid('hideCol', ["enCurso2"]);
					//jQuery(gridP70DetalladoPedido.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
					//} else {
					//jQuery(gridP70DetalladoPedido.nameJQuery).jqGrid('showCol', ["enCurso2"]);
					//jQuery(gridP70DetalladoPedido.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
					//}

					cargarOrdenacionesColumnasP75();

				}
				$("#p70_AreaCajasEuros").show();

				$("#p70_AreaPestanas").show();
				$("#p70_AreaResultados").show();
				$("#p70_AreaEstadosPedido").show();
				$("#p70_btn_buscar").focus();

			} else {

				//$("#p70_informacion").hide();
				//$("#p70_AreaPestanas").hide();
				//$("#p70_AreaResultados").hide();

				$("#p70_AreaPestanas").show();
				$("#p70_AreaResultados").show();
				$("#p70_AreaEstadosPedido").show();
				$("#p70_btn_buscar").focus();
				$("#gbox_gridP75PedidoDatosSinOfertaTextil").hide();
				$("#gbox_gridP75PedidoDatosSinOferta").show();

				$('#gridP75PedidoDatosSinOfertaTextil').jqGrid('clearGridData');
				$('#gridP75PedidoDatosSinOferta').jqGrid('clearGridData');

			}		    
			//Muestra u oculta la columna de empuje.
			hideShowColumnEmpujeP75(data.mostrarEmpuje);
			$("#p70_AreaResultados .loading").css("display", "none");			
			
		},
		error : function (xhr, status, error){
			$("#p70_AreaResultados .loading").css("display", "none");	
			handleError(xhr, status, error, locale);				
		}			
	});		

}	

// Comprueba si una columna del grid indicado está visible o no.
function isColumnVisible(columnName) {
	
	let isVisible;
	
	if (columnName!='undefined' && columnName!=null && columnName != ""){
		isVisible = jQuery(gridP75DetalladoPedidoSinOferta.nameJQuery).jqGrid('getGridParam', 'colModel')
						.filter(function(col) { return col.name === columnName; })[0].hidden === false;
	}
    return isVisible;
    
}

