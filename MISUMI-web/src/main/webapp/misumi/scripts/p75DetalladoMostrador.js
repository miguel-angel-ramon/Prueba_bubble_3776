var emptyRecords=null;
var gridP75DetalladoMostrador=null;
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
	loadP75Mostrador(locale);
}

function resetDatosP75(){
	$('#gridP75PedidoDatosMostrador').jqGrid('clearGridData');
}

function resetDatosP75Save(){
	//Al estar guardando, queremos borrar los datos del grid, pero mantenerlo luego en la hoja, ordenacion, etc. que estaba.
	var actualPage = gridP75DetalladoMostrador.getActualPage();
	var max= gridP75DetalladoMostrador.getRowNumPerPage();
	var index = gridP75DetalladoMostrador.getSortIndex();
	var sortOrder = gridP75DetalladoMostrador.getSortOrder();

	//Borramos los datos del grid.
	$('#gridP75PedidoDatosMostrador').jqGrid('clearGridData');

	//Introducimos los datos de paginacion, etc. anteriores.
	$('#gridP75PedidoDatosMostrador').setGridParam({page:actualPage});
	$('#gridP75PedidoDatosMostrador').setGridParam({max:max});
	$('#gridP75PedidoDatosMostrador').setGridParam({index:index});
	$('#gridP75PedidoDatosMostrador').setGridParam({sortOrder:sortOrder});
}

function reloadDatosP75(saveChanges) {
	reloadGridP75(saveChanges);
}

function loadP75Mostrador(locale){
	gridP75DetalladoMostrador = new GridP75DetalladoMostrador(locale);

	var jqxhr = $.getJSON(gridP75DetalladoMostrador.i18nJSON, function(data) {})
		.success(function(data) {
			gridP75DetalladoMostrador.colNames = data.p75DetalladoMostradorColNames;
			gridP75DetalladoMostrador.title = data.p75DetalladoMostradorGridTitle;
			gridP75DetalladoMostrador.emptyRecords= data.emptyRecords;
			referenceRequired = data.referenceRequired;
			centerRequired = data.centerRequired;
			sectionRequired = data.sectionRequired;
			boxesIncorrect=data.boxesIncorrect;
			literalCajas=data.literalCajas;
			tableFilter= data.tableFilter;
			
			loadGridStructureP75(gridP75DetalladoMostrador);
			
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
	$(gridP75DetalladoMostrador.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		url : './misumi/resources/mock.json',
		datatype : 'json',
		contentType : 'application/json',
		mtype : 'POST',
		sortable : true,
		colNames : gridP75DetalladoMostrador.colNames,
		colModel :[
		{
			"name"  : "seccionGrid",
			"index" : "seccionGrid",
			"sortable" : false,
			"width" : 20,
//			"formatter" : p75FormatAgrup,
			"editable": false
		},{
			"name"  : "referencia",
			"index" : "referencia",
			"sortable":false,
			"fixed":true,
			"resizable":false,
			"width" : 62,
			"editable": false
		},{
			"name"  : "descripcion",
			"index" : "descripcion",
			"sortable":false,
			"fixed":true,
			"resizable":false,
			"width" : 120,
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
			"name"  : "pdteRecibirManana",
			"index" : "pdteRecibirManana", 
			"sortable":false,
			"fixed":true,
			"resizable":false,
			//"formatter":formatNumberDecimalEnCurso,
			"width" : 30,
			"editable": false
		},{
			"name"  : "tirado",
			"index" : "tirado", 
			"sortable":false,
			"fixed":true,
			"resizable":false,
			"width" : 30,
			"editable": false
		},{
			"name"  : "totImporteVentasEspejo",
			"index" : "totImporteVentasEspejo", 
			"sortable":false,
			"fixed":true,
			"resizable":false,
			"width" : 30,
			"editable": false
		},{
			"name"  : "previsionVenta",
			"index" : "previsionVenta", 
			"sortable":false,
			"fixed":true,
			"resizable":false,
			"width" : 30,
			"editable": false
		}
		], 

		pager: gridP75DetalladoMostrador.pagerNameJQuery,
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
		autowidth: true,
		localReader : {id:'referencia'},
		index:"referencia",
		sortname: 'referencia',
		sortorder: 'asc',
		cellsubmit: 'clientArray',
		shrinkToFit:true,
		emptyrecords : gridP75DetalladoMostrador.emptyRecords,
		caption : gridP75DetalladoMostrador.title,
		hidegrid : false, // false, para ocultar el boton que colapsa el grid.
//		subGrid : true,
//	    subGridRowExpanded: function (subgridDivId, rowId) {
//	    	reloadDataP75Subgrid(rowId, subgridDivId);
//	    },

		gridComplete : function() {
			$("#p75_AreaMostrador .loading").css("display", "none");	
		},
		loadComplete : function(data) {
			gridP75DetalladoMostrador.actualPage = data.page;
			gridP75DetalladoMostrador.localData = data;
			gridP75DetalladoMostrador.sortIndex = null;
			gridP75DetalladoMostrador.sortOrder = null;
			$("#p75_AreaMostrador .loading").css("display", "none");	
		},
		resizeStop: function () {
			gridP75DetalladoMostrador.modificado = true;
			saveColumnState(gridP75DetalladoMostrador.myColumnsState);
			jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('setGridWidth', $("#p75_AreaMostrador").width(), false);
		},
		onSortCol : function (index, columnIndex, sortOrder){
			if (activaPantalla!=1){
				return 'stop';
			}else{
				gridP75DetalladoMostrador.sortIndex = index;
				gridP75DetalladoMostrador.sortOrder = sortOrder;	
				saveColumnState(this.p.remapColumns);

				reloadGridP75('S');
				return 'stop';
			}
		},
		onPaging : function(postdata) {
			if (activaPantalla=1){
				alreadySorted = false;
				gridP75DetalladoMostrador.sortIndex = null;
				gridP75DetalladoMostrador.sortOrder = null;
				saveColumnState(this.p.remapColumns);


				reloadGridP75('S');
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
		}
	});

	jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('navGrid',gridP75DetalladoMostrador.pagerNameJQuery,{
		add:false,edit:false,del:false,search:false,refresh:false}
	); 
	jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('navButtonAdd',gridP75DetalladoMostrador.pagerNameJQuery,{ 
		caption: tableFilter, title: "Reordenar Columnas", 
		onClickButton : function (){ 
			jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('columnChooser',
					{
				"done": function(perm) {
					if (perm) {
						var autowidth = true;
						if (gridP75DetalladoMostrador.modificado == true){
							autowidth = false;
							gridP75DetalladoMostrador.myColumnsState =  gridP75DetalladoMostrador.restoreColumnState(gridP75DetalladoMostrador.cm);
							gridP75DetalladoMostrador.isColState = typeof(gridP75DetalladoMostrador.myColumnsState) !== 'undefined' && gridP75DetalladoMostrador.myColumnsState !== null;
							this.jqGrid("remapColumns", perm, true);
							var colModel =jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('getGridParam', 'colModel'); 
							var l = colModel.length;
							var colItem; 
							var cmName;
							var colStates = gridP75DetalladoMostrador.myColumnsState.colStates;
							var cIndex = 2;
							for (i = 0; i < l; i++) {
								colItem = colModel[i];
								cmName = colItem.name;
								if (colItem.hidden !== true && cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {

									jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('setColProp',cmName,{width:colStates[cmName].width});
									var cad =gridP75DetalladoMostrador.nameJQuery+'_'+cmName;
									var ancho = "'"+colStates[cmName].width+"px'";
									var cell = jQuery('table'+gridP75DetalladoMostrador.nameJQuery+' tr.jqgfirstrow td:nth-child(' + (i+1) + ')');
									cell.css("width", colStates[cmName].width + "px");

									jQuery(cad).css("width", colStates[cmName].width + "px");

								}
							}

						} else {
							this.jqGrid("remapColumns", perm, true);
						}
						saveColumnState(perm);
						jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('setGridWidth', $("#p75_AreaMostrador").width(), autowidth);
					}

				}
					}		
			); } });

	jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('setGridWidth', $("#p75_AreaMostrador").width(), true);

}

function saveColumnState (perm) {
	var colModel =jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('getGridParam', 'colModel'); 
	//if (colModel == null)
	//	 colModel = grid.cm;
	var i;
	var l = colModel.length;
	var colItem; 
	var cmName;
	var postData = jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('getGridParam', 'postData');
	var columnsState = {
			search: jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('getGridParam', 'search'),
			page: jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('getGridParam', 'page'),
			sortname: jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('getGridParam', 'sortname'),
			sortorder: jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('getGridParam', 'sortorder'),
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
		window.localStorage.setItem(gridP75DetalladoMostrador.myColumnStateName, JSON.stringify(columnsState));
	}
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


	}else if ( cellValue == "5" )		{    
		mostrarModificado = "block;";


	}else if (cellValue == "3" )		{    
		mostrarGuardado = "block;";

	}else if (cellValue == "6" )		{    
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
function GridP75DetalladoMostrador(locale){
	// Atributos
	this.name = "gridP75PedidoDatosMostrador"; 
	this.nameJQuery = "#gridP75PedidoDatosMostrador"; 
	this.i18nJSON = './misumi/resources/p75DetalladoMostrador/p75detalladoMostrador_' + locale + '.json';
	this.colNames = null;

	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP75PedidoDatosMostrador"; 
	this.pagerNameJQuery = "#pagerP75PedidoDatosMostrador";
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

}

function p75FormateoABlanco(cellValue, opts, rData) {
	var celdaVacia = '';
	return (celdaVacia);
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
	
	if (cellValue == "N"){
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
	var detalladoMostrador = null;	
	var detalladoPedido = null;	

	// Seccion
	var seccion = '';
	if (null != seccionSel){
		seccion = seccionSel;
	} else {
		if ($("#p70_cmb_seccion").val() != 'null') {
			var arrAreaSeccion = $("#p70_cmb_seccion").val().split("*");
			if ("" != arrAreaSeccion[0] && 'null' != arrAreaSeccion[0]){
				area = arrAreaSeccion[0];
			}
			if ("" != arrAreaSeccion[1] && 'null' != arrAreaSeccion[1]){
				seccion = arrAreaSeccion[1];
			}
		}
	}

	// Categoria
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

	// Subcategoria
	var subcategoria = '';
	if (null != subcategoriaSel){
		subcategoria = subcategoriaSel;
	} else {
		if ($("#p70_cmb_subcategoria").val() != null) {
			var arrAreaSubcategoria = $("#p70_cmb_subcategoria").val().split("*");
			if ("" != arrAreaSubcategoria[0] && "null" != arrAreaSubcategoria[0]){
				subcategoria = arrAreaSubcategoria[0];
			}
		}
	}

	// Segmento
	var segmento = '';
	if (null != segmentoSel){
		segmento = segmentoSel;
	} else {
		if ($("#p70_cmb_segmento").val() != null) {
			var arrAreaSegmento = $("#p70_cmb_segmento").val().split("*");
			if ("" != arrAreaSegmento[0] && "null" != arrAreaSegmento[0]){
				segmento = arrAreaSegmento[0];
			}
		}
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
	
	listaModificadosP75 = obtenerListaP75Modificados('S');

	var gridP75 = null;
	gridP75 = gridP75DetalladoMostrador;

	
//	detalladoPedido= new DetalladoPedido(flgIncluirPropPed);
//	var objJson = $.toJSON(detalladoPedido);

	detalladoMostrador = new DetalladoMostrador(seccion,categoria,subcategoria,segmento,$("#centerId").val(),flgIncluirPropPed,listaModificadosP75);
	var objJson = $.toJSON(detalladoMostrador);

	$.ajax({
		type : 'POST',
		url : './detalladoMostrador/loadDataGridMostrador.do?saveChanges='+saveChanges+'&page='+gridP75.getActualPage()+'&max='+gridP75.getRowNumPerPage()+'&index='+gridP75.getSortIndex()+'&sortorder='+gridP75.getSortOrder(),
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {
			if (data.datos.records > 0){
				
				var permisos = $("#userPermision").val();

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
				$("#p70_inputEurosFinales").text(data.eurosFinales);
				$("#p70_inputEurosPVPIniciales").text(data.eurosPVPIniciales);
				$("#p70_inputEurosPVPFinales").text(data.eurosPVPFinales);
//				$("#p70_inputEurosFinalesHidden").val(data.eurosFinales); // Campo oculto para saber si se produce cambio de valor.
				$("#p70_inputCajasIniciales").text(data.cajasIniciales);
				$("#p70_inputCajasFinales").text(data.cajasFinales);
//				$("#p70_inputCajasFinalesHidden").val(data.cajasFinales); // Campo oculto para saber si se produce cambio de valor.

				if(permisos.indexOf('50_DETALLADO_GESTION_EUROS') > 0 || permisos.indexOf('51_DETALLADO_GESTION_EUROS_CONS') > 0){	
					jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('showCol', ["precioCostoFinal"]);
				}else{
					jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('hideCol', ["precioCostoFinal"]);
				}

				jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('setGridWidth', $("#p75_AreaMostrador").width(), true);
				$("#gbox_gridP75PedidoDatosMostrador").show();

				$("#gridP75PedidoDatosMostrador")[0].addJSONData(data.datos);
				gridP75DetalladoMostrador.actualPage = data.datos.page;
				gridP75DetalladoMostrador.localData = data.datos;

				fulfilInformation();

				var ids = jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('getDataIDs'), i, l = ids.length;

				$("#p70_AreaPestanas").hide();
				$("#p70_AreaResultados").hide();
				
				for (i = 0; i < l; i++) {						

					//var estado=$("#gridP70PedidoDatos").getRowData(ids[i]).estadoPedido; //B o I
					var estado= data.datos.rows[i].estadoPedido; //B o I
					var estadoGrid= data.datos.rows[i].estadoGrid; 
					var tipoReferencia = data.datos.rows[i].grupo2; 

					if (estado=="I"){
						//Integrado --> No modificable
						$("#gridP75PedidoDatosMostrador").find("#"+ids[i]).find("[aria-describedby='gridP75PedidoDatosMostrador_cantidad']").addClass("p75_columnaResaltadaIntegradaFila");
						$("#gridP75PedidoDatosMostrador #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaIntegradaFila");														
					}else if (estado=="E"){
						//Empuje -->  No modificable
						$("#gridP75PedidoDatosMostrador").find("#"+ids[i]).find("[aria-describedby='gridP75PedidoDatosMostrador_cantidad']").addClass("p75_columnaResaltadaEmpujeFila");
						$("#gridP75PedidoDatosMostrador #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaEmpujeFila");
					}else if (estado=="R"){
						//Guardado sin integrar
						jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('editRow', ids[i], false);
						$("#gridP75PedidoDatosMostrador #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaGuardadoSinIntegrarFila");
						$("#gridP75PedidoDatosMostrador #"+(i+1)+"_cantidad").filter_input({regex:'[0-9]'});
						$("#gridP75PedidoDatosMostrador #"+(i+1)+"_cantidad").change(function(event) {
							validacionCantidadP75(event);
						});
						var idRedondeo = '<span id="' + (i+1)+ '_cantidadRedondeo" class="p75_tipoRedondeoOculto">R</span>';
						$("#"+(i+1)+"_cantidad").after(idRedondeo);
					}else if (estado=="B"){
						//Bloqueado
						$("#gridP75PedidoDatosMostrador").find("#"+ids[i]).find("[aria-describedby='gridP75PedidoDatosMostrador_cantidad']").addClass("p75_columnaResaltadaBloqueadaFila").removeClass("p75_columnaResaltadaOferta");
						$("#gridP75PedidoDatosMostrador #"+(i+1)+"_cantidad").removeClass("editable").addClass("p75_columnaResaltadaBloqueadaFila").removeClass("p75_columnaResaltadaOferta");												
					}else if (estado=="A"){
						//Bloqueado al Alza
						jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('editRow', ids[i], false);
						$("#gridP75PedidoDatosMostrador #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaBloqueadaAlAlzaFila");
						$("#gridP75PedidoDatosMostrador #"+(i+1)+"_cantidad").filter_input({regex:'[0-9]'});
						$("#gridP75PedidoDatosMostrador #"+(i+1)+"_cantidad").change(function(event) {
							validacionCantidadP75(event);
						});
						var idRedondeo = '<span id="' + (i+1)+ '_cantidadRedondeo" class="p75_tipoRedondeoOculto">R</span>';
						$("#gridP75PedidoDatosMostrador #"+(i+1)+"_cantidad").after(idRedondeo);

						$("#p70_AreaNotaBloqueoAlAlza").show();
					}else{
							jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('editRow', ids[i], false);
							$("#gridP75PedidoDatosMostrador #"+(i+1)+"_cantidad").addClass("p75_columnaResaltadaSinGuardarFila");
							$("#gridP75PedidoDatosMostrador #"+(i+1)+"_cantidad").filter_input({regex:'[0-9]'});
							$("#gridP75PedidoDatosMostrador #"+(i+1)+"_cantidad").change(function(event) {
								validacionCantidadP75(event);
							});
							var idRedondeo = '<span id="' + (i+1)+ '_cantidadRedondeo" class="p75_tipoRedondeoOculto">R</span>';
							$("#gridP75PedidoDatosMostrador #"+(i+1)+"_cantidad").after(idRedondeo);

					}
					$("#gridP75PedidoDatosMostrador #"+(i+1)+"_cantidad").after(p75FormatCampoCantidad(estado,tipoReferencia,data.datos.rows[i].tipoUFP, data.datos.rows[i].dexenx, data.datos.rows[i].diferencia, i));

					if (estadoGrid == 5 || estadoGrid == 6) { //modificado/nuevo con redondeo
							$("#gridP75PedidoDatosMostrador #"+(i+1)+"_cantidadRedondeo").removeClass("p75_tipoRedondeoOculto");
							$("#gridP75PedidoDatosMostrador #"+(i+1)+"_cantidadRedondeo").removeClass("p75_tipoRedondeoVisible");

							$("#gridP75PedidoDatosMostrador #"+(i+1)+"_cantidadRedondeo").addClass("p75_tipoRedondeoVisible");

							$("#p70_AreaNotaRedondeo").show();
					}

					//GESTION EUROS - resaltar linea en rojo cuando refCumple == N
					var refCumple=data.datos.rows[i].refCumple; 
					if (refCumple == "N") {//Pintamos la linea en rojo
							$("#gridP75PedidoDatosMostrador").find("#"+ids[i]).find("td").addClass("p75_lineaResaltadaRojo");

							//pintamos la columna P.Costo tambien en rojo
							$("#gridP75PedidoDatosMostrador #"+ (i+1) +"_precioCostoFinal").addClass("p75_precioCostofinalRojo");
					}

					cargarOrdenacionesColumnasP75();
				}
				
				$("#p70_AreaCajasEuros").show();

				$("#p70_AreaPestanas").show();
				$("#p70_AreaResultados").show();
				$("#p70_AreaEstadosPedido").show();
				$("#p70_btn_buscar").focus();

			// Si NO se han recuperado datos de la consulta.
			} else {
				$("#p70_AreaPestanas").show();
				$("#p70_AreaResultados").show();
				$("#p70_AreaEstadosPedido").show();
				$("#p70_btn_buscar").focus();

				$("#gbox_gridP75PedidoDatosMostrador").show();
				$('#gridP75PedidoDatosMostrador').jqGrid('clearGridData');
			}
			
			//Muestra u oculta la columna de empuje.
//			hideShowColumnEmpujeP75(data.mostrarEmpuje);
			
			// Ocultar la imagen de "Cargando ...
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
		jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('showCol', ["unidFlEmpuje"]);
	} else{
		//Si no existe un empuje >0, no debe aparecer la columna de unidFlEmpuje.
		jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('hideCol', ["unidFlEmpuje"]);
	}
}

function getCellValue(rowId, columnName) {
	var rowData = $("#gridP75PedidoDatosMostrador").getRowData(rowId)[columnName];
	return rowData;
}
function getCellAtrribute(rowId, columnName) {
	var rowData = $("#gridP75PedidoDatosMostrador").getRowData(rowId)[columnName].attr('class');
	return rowData;
}

function controlNavegacionP75(e) {
	var idActual = e.target.id;
	var idFoco;
	//Obtención de fila y columna actuales
	var fila = idActual.substring(0, idActual.indexOf("_"));
	var nombreColumna = idActual.substring(idActual.indexOf("_")+1);
	var validacionNavegacion = 'S';

	var gridActual = "gridP75PedidoDatosMostrador";

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

function hideShowColumnsP75(codArea){

	if( null != codArea && codArea < 3){
		//Si el area es mayor que 3, debe aparecer la columna Nuevo
		jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('showCol', ["tipo"]);
		jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('setGridWidth', $("#p75_AreaMostrador").width(), true);
		$("#p70_AreaNotaPie").show();
	} else{
		//Si el area es 1,2 o 3, no debe aparecer la columna Nuevo
		jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('hideCol', ["tipo"]);
		jQuery(gridP75DetalladoMostrador.nameJQuery).jqGrid('setGridWidth', $("#p75_AreaMostrador").width(), true);
		$("#p70_AreaNotaPie").hide();
	}

}

// Recupera los registros que se hayan modificado.
function obtenerListaP75Modificados(saveChanges){

	if (saveChanges!='N'){

		var ids = $("#gridP75PedidoDatosMostrador").jqGrid('getDataIDs'), i, l = ids.length;
		var j=0;
		for (i = 0; i < l; i++) {	
			var estado=$("#gridP75PedidoDatosMostrador #"+ids[i]+"_estadoGrid").val();

			// (Estado = 2) - Modificado
			// (Estado = 5) - Redondeo. Pone "R".
			if (estado == "2" || estado == "-2" || estado == "5"){
				addSeleccionadosP75($("#gridP75PedidoDatosMostrador").getRowData(ids[i]), ids[i]);
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

function addSeleccionadosP75(rowData, fila){
	var gridActual = "gridP75PedidoDatosMostrador";
	mapP75.put(rowData.codArticulo
			  ,new DetalladoPedidoModif( rowData.codArticulo
					  				   , $("#" + gridActual + " #"+fila+"_propuestaPedido").val()
					  				   , $("#" + gridActual + " #"+fila+"_cantidadOriginal").val()
					  				   , $("#" + gridActual + " #"+fila+"_cantidadUltValida").val()
					  				   , 2 // estado_grid (Modificado)
					  				   , "" // Fecha Pedido
					  				   , $("#" + gridActual + " #"+fila+"_grupo1").val()
					  				   , $("#" + gridActual + " #"+fila+"_grupo2").val()
					  				   , $("#" + gridActual + " #"+fila+"_grupo3").val()
					  				   , $("#" + gridActual + " #"+fila+"_grupo4").val()
					  				   , $("#" + gridActual + " #"+fila+"_grupo5").val()
									   , trasformToStringP75(rowData.pendienteRecibirManana)
									   , $("#" + gridActual + " #"+fila+"_unidadesCaja").val()
									   , rowData.descriptionArt
//									   , $("#" + gridActual + " #"+fila+"_resultadoWS").val()
									   , $("#" + gridActual + " #"+fila+"_estadoGrid").val()
									   )
			);
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
	var colModel = $("#gridP75PedidoDatosMostrador").jqGrid("getGridParam", "colModel");
	$.each(colModel, function(i) {
		if (colModel[i].name!="rn"){
			$("#jqgh_gridP75PedidoDatosMostrador_"+colModel[i].name).attr("title",eval("data."+colModel[i].name+"Title"));
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

	var gridActual = "gridP75PedidoDatosMostrador";

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

	var rowData = jQuery(gridP75DetalladoMostrador.nameJQuery).getRowData(fila);
	var codArticulo = rowData['codArticulo'];

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
//	$("#p70_inputCajasFinalesHidden").val(cajasFinales);
	event.target.value = cantidad;

}

function calcularRedondeoP75(cantidad, codArticulo, unidadesCaja,tipoUFP){

	var resultado = cantidad;

	$.ajax({
		type : 'POST',
		url : './detalladoMostrador/calcularRedondeo.do?codCentro=' + $("#centerId").val() + '&codArticulo=' + codArticulo +'&cantidad='+cantidad  +'&unidadesCaja='+unidadesCaja +'&tipoUFP='+ tipoUFP,
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
		url : './detalladoMostrador/actualizarPrecioCostoFinal.do?codCentro=' + $("#centerId").val() + '&codArticulo=' + codArticulo +'&cantidad='+cantidad  +'&precioCostoArticulo='+precioCostoArticulo +'&unidadesCaja='+unidadesCaja,
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

// Establece la posibilidad de ordenar los campos pinchando en las columnas.
function cargarOrdenacionesColumnasP75(){

	var moved=false;	
	var colModel = $("#gridP75PedidoDatosMostrador").jqGrid("getGridParam", "colModel");

	$.each(colModel, function(i) {
		if (colModel[i].name!="rn"){

			$("#jqgh_gridP75PedidoDatosMostrador_" + colModel[i].name).unbind('mousedown');
			$("#jqgh_gridP75PedidoDatosMostrador_" + colModel[i].name).unbind('mousemove');
			$("#jqgh_gridP75PedidoDatosMostrador_" + colModel[i].name).unbind('mouseup');
			$("#jqgh_gridP75PedidoDatosMostrador_" + colModel[i].name).bind("mousedown", function() {
				moved=false;
			});
			$("#jqgh_gridP75PedidoDatosMostrador_" + colModel[i].name).bind("mousemove", function() {
				moved=true;
			});
			$("#jqgh_gridP75PedidoDatosMostrador_" + colModel[i].name).bind("mouseup", function() {
				if(!moved){
					actualizarSortOrderP75(colModel[i].name);
					$('#gridP75PedidoDatosMostrador').setGridParam({sortname:colModel[i].name});
					$('#gridP75PedidoDatosMostrador').setGridParam({page:1});
					reloadGridP75('S');
				}
			});

		}
	});
}

function actualizarSortOrderP75 (columna) {

	var ultimoSortName =  $('#gridP75PedidoDatosMostrador').jqGrid('getGridParam','sortname');
	var ultimoSortOrder =  $('#gridP75PedidoDatosMostrador').jqGrid('getGridParam','sortorder');

	if (ultimoSortName != columna ) { //Se ha cambiado la columna por la que se quiere ordenar


		$('#gridP75PedidoDatosMostrador').setGridParam({sortorder:'asc'});
		$("#gridP75PedidoDatosMostrador_" + columna + " [sort='asc']").removeClass("ui-state-disabled");

		//En la columna anterior quitamos la flechas
		if (ultimoSortOrder=='asc'){
			$("#gridP75PedidoDatosMostrador_" + ultimoSortName + " [sort='asc']").addClass("ui-state-disabled");
		}else{
			$("#gridP75PedidoDatosMostrador_" + ultimoSortName + " [sort='desc']").addClass("ui-state-disabled");
		}

	} else { //Seguimos en la misma columna

		if (ultimoSortOrder=='asc'){
			$('#gridP75PedidoDatosMostrador').setGridParam({sortorder:'desc'});
			//Mostramos la flecha descendente y quitamos la flecha ascendente
			$("#gridP75PedidoDatosMostrador_"  + columna + " [sort='desc']").removeClass("ui-state-disabled");
			$("#gridP75PedidoDatosMostrador_"  + columna + " [sort='asc']").addClass("ui-state-disabled");
		} else {
			$('#gridP75PedidoDatosMostrador').setGridParam({sortorder:'asc'});
			$("#gridP75PedidoDatosMostrador_"  + columna + " [sort='asc']").removeClass("ui-state-disabled");
			$("#gridP75PedidoDatosMostrador_"  + columna + " [sort='desc']").addClass("ui-state-disabled");
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

