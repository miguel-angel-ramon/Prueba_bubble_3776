/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/****************************************************************** CONSTANTES ***************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Constante que indica el número de elementos máximo a mostrar por paginación. Cambiar si se quiere mostrar menos formularios
var maxElementosPopupP34 = 20;


/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/* Peticion 49634 - Busqueda Referencias popup universal
 * @author BICUGUAL
 */
var gridP34 = null;
var p34Generico = false;
var p34Seccion = null;
var p34Categoria= null;
var p34Dialog = null;
var p34DialogE = null;
var p34DialogM = null;
var p34Title = null;
var p34TitleE = null;
var p34TitleM = null;
var p34CodReferencia=null;
var descripcion=null;
var alfaNumericoBusqueda=null;
var eanBusqueda =null;
var pantallaDeBusqueda=null;
var emptyRecordSelection = null;
var queryRef = null;
var p34TipoPedido = null;
var comboTextil = false;
var seccionUnicaTextil=false;

var loteAlerta = null;
var modeloProveedorAlerta = null;

var comboPopup = null;
var primeraDescripcion = null;

var emptyRecordSelection = null;
var infoBasicaConsultaDatos = null;
var montajeAdicional = null;
var encargos = null;
var sfm = null; 
/*
 *Para determinar si el combo debe de cargarse.
 *Tan solo debe realizarse la carga cuando no se trate de una regarga de grid, es decir, 
 * cuando se una busqueda nueva cuya funcion de entrada es reloadDataP34.  
 */
var cargarCombo=null;

var referenciasByDescr = null;
//Variable que guarda el número de página que nos encontramos en el popup.
var numeroDePaginaPopupP34 = 1;
var idxInicioListaP34 = null;
var idxFinListaP34 = null;
var largoListaP34 = null;

var cargarGrid = "N";

var p34SeccionFoto = null;

//Flag que indica si una seferencia es de tipo MP o LOT. Sirve por si un usuario introduce un número en el buscador universal, y devuelve más de una referencia.
//Este flag será "S" cuando buscando una referencia, indique que existe más de una referencia relacionada (>1)
var flgIsMPorLot = "N";

//Flag que indica si una referencia es de tipo modelo proveedor o no. Es necesario para el whiteFormatter y el hiddenFormatter
//del grid con subgrid.
var nivelModProv = null;

var p34GamaActiva;

$(document).ready(function(){
	loadP34(locale);
//	$(gridP34.nameJQuery).jqGrid('setGridWidth', $("#p34_AreaReferencias").width(), true);
});


/**
 * Inicializa  las propiedades de los popUp
 */
function initializeScreenP34ReferenciasFoto(){

	$( "#p34_popupSelReferenciasFoto" ).dialog({
		autoOpen: false,
		height: 'auto',
		width: 'auto',
		modal: true,
		resizable: false,
		close: function( event, ui ) {
			//Al salir del popup situo el foco y la seleccion en el input de busqueda.
			if (pantallaDeBusqueda=="informacionBasica"){
				$("#p01_txt_infoRef").focus();
				$("#p01_txt_infoRef").select();
				$("#p34_cmb_seccionFoto").combobox(null);
				comboTextil=null;
			}
			else if (pantallaDeBusqueda=="consultaDatos"){
				$("#p13_fld_referencia").focus();
				$("#p13_fld_referencia").select();
			}
			else if (pantallaDeBusqueda=="intertienda"){
				$("#p72_fld_referencia").focus();
				$("#p72_fld_referencia").select();
			}
			else{
				$("#p52_fld_cantidad1").focus();
				$("#p52_fld_cantidad1").select();
			}

			$(gridP34.nameJQuery).hideCol("talla");
			$(gridP34.nameJQuery).hideCol("color");
			$(gridP34.nameJQuery).hideCol("modelo_proveedor");

			//comboPopup = "Foto";
			numeroDePaginaPopupP34 = 1;
			idxInicioListaP34 = null;
			idxFinListaP34 = null;
			largoListaP34 = null;

			comboPopup = "Foto";
			$("#p34_cmb_categoria").combobox("disable");

			$("#p34_cmb_seccionFoto").combobox('autocomplete',"");
			$("#p34_cmb_seccionFoto").empty();
		}
	});

	$(window).bind('resize', function() {
		$("#p34_popupSelReferenciasFoto").dialog("option", "position", "center");
	});

	$("#p34_cmb_seccionFoto").combobox(null);
	$("#p34_cmb_categoria").combobox(null);
	$("#p34_cmb_categoria").combobox("disable");
	/**
	 * Observo cuando se pulsa return y el foco se encuentra en el combo de seccion para recargar el grid.
	 */
	$(".controlReturnP34").on("keydown", function(e) {
		if (e.which == 13) {
			e.preventDefault();
			reloadData_gridP34SelReferencias();

		}
	});

	events_p34_flecha_sig();
	events_p34_flecha_ant();
}

function initializeScreenP34Referencias(){

	$( "#p34_popupSelReferencias").dialog({
		autoOpen: false,
		height: 420,
		width: 780,
		modal: true,
		resizable: false,
		close: function( event, ui ) {
			queryRef.descripcion = primeraDescripcion;
			queryRef.codArticulo = null;
			queryRef.alfaNumerico = primerAlfanumerico;

			descripcion = primeraDescripcion;
			alfaNumericoBusqueda = primerAlfanumerico;

			p34Seccion = p34SeccionFoto;

			cargarGrid = "N";
			comboPopup = "Foto";

			//Ponemos el flag de nivel modelo proveedor a N, porque si abrimos el grid de modelo proveedor y lo cerramos, se queda 
			//puesto en S y a la hora de paginar entre las fotos, realiza la búsqueda como si estuviera buscando modelos proveedores
			//y nosotros no queremos eso.
			queryRef.flgNivelModProv = "N";
		}
	});

	$(window).bind('resize', function() {
		$("#p34_popupSelReferencias").dialog("option", "position", "center");
	});

	$("#p34_cmb_seccion").combobox(null);

	/**
	 * Observo cuando se pulsa return y el foco se encuentra en el combo de seccion para recargar el grid.
	 */
	/*$(".controlReturnP34").on("keydown", function(e) {
		if (e.which == 13) {
			e.preventDefault();
			reloadData_gridP34SelReferencias();

		}
	});*/
}

/**
 * Carga las referencias encontradas y abre el dialog donde se muestran
 */
function loadP34(locale){

	gridP34 = new GridP34SelRefeferencias(locale);
	var jqxhr = $.getJSON(gridP34.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		gridP34.colNames = data.ColNames;
		gridP34.subgridColNames = data.ColNames;
		gridP34.title = data.GridTitle;
		index='';
		sortOrder = 'asc';
		p34Dialog = data.DialogTitle;
		p34DialogE = data.DialogTitleE;
		p34DialogM = data.DialogTitleM;
		p34DialogSfm = data.DialogTitleSfm;
		p34Title = data.GridTitle;
		p34TitleE = data.GridTitleE;
		p34TitleM = data.GridTitleM;
		p34TitleSfm=data.GridTitleSfm;
		gridP34.emptyRecords= data.emptyRecords;
		emptyRecordSelection = data.emptyRecordSelection;
		loteAlerta = data.loteAlerta;
		modeloProveedorAlerta = data.modeloProveedorAlerta;

		infoBasicaConsultaDatos = data.infoBasicaConsultaDatos;
		montajeAdicional = data.montajeAdicional;
		encargos = data.encargos;
		sfm = data.sfm;

		p34RefActiva = data.p34RefActiva;
		p34AltaCatalogo = data.p34AltaCatalogo;
		
		p34GamaActiva = data.p34GamaActiva;
		
		initializeScreenP34Referencias();
		initializeScreenP34ReferenciasFoto();

		load_gridP34Mock(gridP34);
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});

}

/**
 * Carga del comportamiento del grid con sus propiedades.
 * @param grid
 */
function load_gridP34Mock(grid) {

	$(grid.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		url : './misumi/resources/mock.json',
		datatype : 'json',
		contentType : 'application/json',
		mtype : 'POST',
		colNames : grid.colNames,
		colModel : grid.cm,
		rowNum : 10,
		rowList : [ 10, 20, 30 ],
		//height : "100%",
		height : 230,
		width : 700,
		rownumbers : grid.rownumbers,
//		shrinkToFit:true,
		viewrecords : true,
		caption : grid.title,
		altclass: "ui-priority-secondary",
		pager : grid.pagerNameJQuery,
		altRows: true, //false, para que el grid no muestre cebrado
		hidegrid : false, //false, para ocultar el boton que colapsa el grid.
		sortable : true,
		subGrid : true,
		//subGridUrl: './misumi/resources/p47PedidoAdicionalEC/p47mockPedidoAdicionalEC.json',
		subGridRowExpanded: function (subgridDivId, rowId) {

			reloadDataP34AltaCatalogoSubgridN2(rowId, subgridDivId);
		},

		index: grid.sortIndex,
		sortname: grid.sortIndex,
		sortorder: grid.sortOrder,
		emptyrecords : grid.emptyRecords,
		gridComplete : function() {
		},
		loadComplete : function(data) {
			grid.actualPage = data.page;
			grid.localData = data;
			grid.sortIndex = null;
			grid.sortOrder = null;
			//$(grid.nameJQuery).jqGrid('setGridWidth', $("#p34_AreaReferencias").width(), true);
		},
		resizeStop: function () {
			grid.saveColumnState.call($(this),grid.myColumnsState);

		},
		onPaging : function(postdata) {			
			grid.sortIndex = null;
			grid.sortOrder = null;
			grid.saveColumnState.call($(this), this.p.remapColumns);
			reloadData_gridP34SelReferencias();
			return 'stop';
		},
		onSelectRow: function(id){
			if(nivelModProv != "S"){
				p34CodReferencia =$(grid.nameJQuery).getCell(id, "codArticulo");
				//En información básica, queremos que si seleccionamos una referencia del buscador universal, se abra el popup
				//de esa referencia con la información, pero además que se mantenga detras el popup del buscador universal,
				//para que al cerrar el popup de información, tengamos accesibles las demás referencias.
				if (pantallaDeBusqueda!="informacionBasica"){ 
					$("#p34_popupSelReferencias").dialog('close');
					$("#p34_popupSelReferenciasFoto").dialog('close');
				}
				loadSelection(p34CodReferencia);
			}
		},
		onSortCol : function (index, columnIndex, sortOrder){
			grid.sortIndex = index;
			grid.sortOrder = sortOrder;
			grid.saveColumnState.call($(this), this.p.remapColumns);
			reloadData_gridP34SelReferencias();
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
			handleError(xhr, status, error);
		}
	});

	//$(grid.nameJQuery).jqGrid('setGridWidth', $("#p34_AreaReferencias").width(), true);

}


/**
 * Carga de los datos del popUp limpiando los datos anteriores.
 * @param txtDescripcion. Descripcion introducida para la busqueda.
 * @param pantalla. Pantalla desde la que se llama a la funcion.
 * @param tipoPedido. Se añade al titulo del popup "Seleccionar referencias"
 * @param alfaNumerico. Flag para determinar si se trata de una busqueda por alfanumerica
 * @param ean.Flag para determinar si se trata de una busqueda por ean
 */

function reloadDataP34(txtDescripcion, pantalla, tipoPedido, alfaNumerico, ean){
	if ($.browser.msie){
		$("#cargandoIE").show();
	} else {
		$("#cargando").css("position", "fixed" );
	}
	primeraDescripcion = txtDescripcion;
	primerAlfanumerico = alfaNumerico;

	descripcion=txtDescripcion;
	pantallaDeBusqueda=pantalla;
	alfaNumericoBusqueda = alfaNumerico;
	eanBusqueda = ean;
	reset_p34();
	$("#p34_cmb_seccionFoto").combobox('autocomplete',"");
	$("#p34_cmb_seccionFoto").empty();

	p34TipoPedido = tipoPedido;

	if (p34TipoPedido){
		$(gridP34.nameJQuery).jqGrid('setCaption', eval( "p34Title" + p34TipoPedido ));
		$('#p34_popupSelReferenciasFoto').dialog('option', 'title', eval( "p34Dialog" + p34TipoPedido ));
		$(gridP34.nameJQuery).jqGrid('setGridParam',{sortname: "codArticulo", sortorder: "asc"});

	} else {
		$(gridP34.nameJQuery).jqGrid('setCaption', p34Title);
		$(gridP34.nameJQuery).jqGrid('setGridParam',{sortname: null, sortorder: null});
		$('#p34_popupSelReferenciasFoto').dialog('option', 'title', p34Dialog);
	}

	cargarCombo=true;
	reloadData_gridP34SelReferencias();

	if ($.browser.msie){
		$("#cargandoIE").hide();
	} else {
		$("#cargando").css("position", "relative" );
	}

	var textoTituloPopup = null;
	if ((pantallaDeBusqueda=='informacionBasica') || (pantallaDeBusqueda=='consultaDatos') || (pantallaDeBusqueda == 'intertienda')){
		textoTituloPopup = infoBasicaConsultaDatos;
	}else if (pantallaDeBusqueda=='nuevoPedidoAdicional-encargos'){
		textoTituloPopup = encargos;
	}else if( pantallaDeBusqueda=='nuevoPedidoAdicional-montaje'){
		textoTituloPopup = montajeAdicional;
	}else if(pantallaDeBusqueda=='informacionSfm'){
		textoTituloPopup = sfm;
	}
	$("#p34_tituloEstructuraFoto").text(textoTituloPopup);
}


/**
 * Clase de constantes para el GRID de referencias de alta de plataforma
 * @param locale
 * @returns
 */
function GridP34SelRefeferencias (locale){

	// Atributos
	this.name = "gridP34SelReferencias";
	this.nameJQuery = "#gridP34SelReferencias"; 
	this.i18nJSON = './misumi/resources/p34PopupSelReferencias/p34PopUpSelReferencias_' + locale + '.json';
	this.colNames = null; //Está en el json
	this.cm = [
	           {
	        	   "name"  : "codArticulo",
	        	   "index" : "codArticulo",
	        	   "formatter" : hiddenFormatter,
	        	   "width" : 20,	
	        	   "sortable" : true,
	        	   "align" : "left"
	           },{
	        	   "name"  : "descripcion",
	        	   "index" : "descripcion", 
	        	   "formatter" : whiteFormatter,
	        	   "width" : 80,	
	        	   "sortable" : true,
	        	   "align" : "left"						
	           },{
	        	   "name"  : "catalogo",
	        	   "index" : "catalogo", 
	        	   "width" : 60,
	        	   "formatter" : imagenCatalogoFormatter,
	        	   "fixed":true,
	        	   "sortable" : true,
	        	   "align" : "center",
	        	   "hidden": true
	           },{
	        	   "name"  : "unidadesCaja",
	        	   "index" : "unidadesCaja", 
	        	   "width" : 10,
	        	   "sortable" : false,
	        	   "align" : "left",
	        	   "hidden": true
	           },{
	        	   "name"  : "talla",
	        	   "index" : "talla", 
	        	   "width" : 25,
	        	   "formatter" : whiteFormatter,
	        	   "sortable" : false,
	        	   "align" : "left",
	        	   "hidden": true
	           },{
	        	   "name"  : "color",
	        	   "index" : "color", 
	        	   "width" : 25,
	        	   "formatter" : whiteFormatter,
	        	   "sortable" : false,
	        	   "align" : "left",
	        	   "hidden": true
	           },{
	        	   "name"  : "modelo_proveedor",
	        	   "index" : "modelo_proveedor", 
	        	   "width" : 25,
	        	   "sortable" : false,
	        	   "align" : "left",
	        	   "hidden": true
	           },{
	        	   "name"  : "activa",
	        	   "index" : "activa", 
	        	   "width" : 50,
	        	   "formatter" : whiteFormatter,
	        	   "fixed":true,
	        	   "sortable" : true,
	        	   "align" : "center",
	        	   "hidden": true
	           }
	           ];
	this.subgridColNames = null;
	this.subgridCm = [
	                  {
	                	  "name"  : "codArticulo",
	                	  "index" : "codArticulo",
	                	  "width" : 19,	
	                	  "sortable" : true,
	                	  "align" : "left"
	                  },{
	                	  "name"  : "descripcion",
	                	  "index" : "descripcion", 
	                	  "width" : 80,	
	                	  "sortable" : true,
	                	  "align" : "left"						
	                  },{
	                	  "name"  : "catalogo",
	                	  "index" : "catalogo", 
	                	  "width" : 60,
	                	  "formatter" : imagenCatalogoFormatter,
	                	  "fixed":true,
	                	  "sortable" : true,
	                	  "align" : "center",
	                	  "hidden": true
	                  },{
	                	  "name"  : "unidadesCaja",
	                	  "index" : "unidadesCaja", 
	                	  "width" : 10,
	                	  "sortable" : false,
	                	  "align" : "left",
	                	  "hidden": true
	                  },{
	                	  "name"  : "talla",
	                	  "index" : "talla", 
	                	  "width" : 25,
	                	  "sortable" : false,
	                	  "align" : "left",
	                	  "hidden": true
	                  },{
	                	  "name"  : "color",
	                	  "index" : "color", 
	                	  "width" :25,
	                	  "sortable" : false,
	                	  "align" : "left",
	                	  "hidden": true
	                  },{
	                	  "name"  : "modelo_proveedor",
	                	  "index" : "modelo_proveedor", 
	                	  "width" : 25,
	                	  "sortable" : false,
	                	  "align" : "left",
	                	  "hidden": true
	                  },{
	                	  "name"  : "activa",
	                	  "index" : "activa", 
	                	  "width" : 49,
	                	  "formatter" : imagenPedirFormatter,
	                	  "fixed":true,
	                	  "sortable" : true,
	                	  "align" : "center",
	                	  "hidden": true
	                  }
	                  ];
	this.locale = locale;
	this.sortIndex = null;
	this.sortOrder = ""; // Valores posibles "asc" o "desc"
	this.title = null; //Está en el json
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null; //Está en el json
	this.pagerName = "pagerP34"; 
	this.pagerNameJQuery = "#pagerP34";
	this.rownumbers = true;
	this.myColumnStateName = 'gridP34SelReferencias.colState';
	this.myColumnsState = null;
	this.isColState = null;
	this.firstLoad = true;
	this.scrollerbar=true;


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
		var colModel = jQuery(gridP34.nameJQuery).jqGrid('getGridParam', 'colModel'); 
		var i;
		var l = colModel.length;
		var colItem; 
		var cmName;
		var postData = jQuery(gridP34.nameJQuery).jqGrid('getGridParam', 'postData');
		var columnsState = {
				search: jQuery(gridP34.nameJQuery).jqGrid('getGridParam', 'search'),
				page: jQuery(gridP34.nameJQuery).jqGrid('getGridParam', 'page'),
				sortname: jQuery(gridP34.nameJQuery).jqGrid('getGridParam', 'sortname'),
				sortorder: jQuery(gridP34.nameJQuery).jqGrid('getGridParam', 'sortorder'),
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

function whiteFormatter(cellValue, opts, rData) {
	if(nivelModProv == "S"){
		return "";
	}else{
		if(cellValue != null){
			if(opts.colModel.name == "activa"){
				var pedir=rData['activa'];		
				return "<img src='./misumi/images/"+getImage(pedir)+"' style='width:15px;vertical-align: middle'>";
			}else{
				return cellValue;
			}
		}else{
			return "";
		}
	}
}
function hiddenFormatter(cellValue, opts, rData) {
	if(nivelModProv == "S"){
		var codArticulo = rData['codArticulo'];
		var codArticuloHidden = "<input type='hidden' id='"+opts.rowId+"_codArticuloGridOculto' value='"+codArticulo+"'>";
		return codArticuloHidden;
	}else{
		return cellValue;
	}
}
/**
 * Resetea el combo y el grid
 */
function reset_p34(){
	$(gridP34.nameJQuery).jqGrid('clearGridData');
	p34Seccion = null;
	p34Categoria = null;
	$("#p34_cmb_seccion").combobox('autocomplete',"");
	$("#p34_cmb_seccion").empty();

	$("#p34_cmb_categoria").combobox('autocomplete',"");
	$("#p34_cmb_categoria").empty();

	//Reseteo el rowNum (valor y comportamiento del grid para reseterar el scroll)
	$("#p34_popupSelReferencias .ui-pg-selbox").val(10);
	$(gridP34.nameJQuery).setGridParam({rowNum:10});

	//Ocultar las leyendas de MP o Lot
	$("#p34_leyendaMP").hide();
	$("#p34_leyendaLot").hide();
}

/**
 * Formatea el texto del combo con "codigo - descripcion"
 * @param codigo
 * @param descripcion
 * @returns {String}
 */
function formatDescripcionCombo(codigo, descripcion){
	return codigo + "-" + descripcion;
}

/**
 * Carga el codigo de la referencia seleccionada en el campo de busqueda para emular la busqueda por referencia.
 * Y lanza la busqueda llamando al metodo findReferencia que cada js tiene.
 * @param codArticulo
 */
function loadSelection(codArticulo){

	if (pantallaDeBusqueda=='informacionBasica'){
		$("#p01_txt_infoRef").val(codArticulo);
		loadDataPopUpP30(locale, codArticulo);
	}
	else if (pantallaDeBusqueda=='intertienda'){
		$("#p72_fld_referencia").val(codArticulo);
		loadReferencia();
	}
	else if (pantallaDeBusqueda=='consultaDatos'){
		$("#p13_fld_referencia").val(codArticulo);
		reloadMaestros();
	}
	else if (pantallaDeBusqueda=='nuevoPedidoAdicional-encargos' || pantallaDeBusqueda=='nuevoPedidoAdicional-montaje'){
		$("#p52_fld_referencia").val(codArticulo);
		getReferencia();

	}else if(pantallaDeBusqueda=='informacionSfm'){
		$("#p26_fld_referencia").val(codArticulo);
		reloadData('N');
	}else if(pantallaDeBusqueda == "crearDevolucionP86"){
		//Si la plataforma coincide, calcula los stocks. Si no, muestra error y pide que introduzca una referencia valida.
		var obtenerPlat = obtenerPlataformasDevolucionCreadaPorCentroP86(codArticulo);
		if(obtenerPlat == "S"){
			findStckActualYDenomP86(codArticulo,alfaNumericoBusqueda);
		}else{
			//Ponemos el foco en la referencia.
			$("#p86_fld_ref").focus();
		}
	}
}

/**
 * Construye la imagen de la columna Activa que se visualizará en cada row
 */
function imagenPedirFormatter(cellValue, opts, rData) {
	var pedir=rData['activa'];		
	return "<img src='./misumi/images/"+getImage(pedir)+"' style='width:15px;vertical-align: middle'>";
}

/**
 * Construye la imagen de la columna Catalogo que se visualizará en cada row
 */
function imagenCatalogoFormatter(cellValue, opts, rData) {
	var catalogo=rData['catalogo'];
	return "<img src='./misumi/images/"+getImage(catalogo)+"' style='width:15px;vertical-align: middle'>";
}

/**
 * En base al valor que recibe devuelve una u otra imagen
 * @param valorColumna
 * @returns {String}
 */
function getImage(valorColumna){
	if (valorColumna=='S' || valorColumna=='A')
		return "dialog-accept-24.png";
	else
		return imagen="dialog-cancel-24.png";
}

function p34FormaterNivelLote(cellValue, opts, rData) {

	cellValue = 0;
	//Añadimos los valores de la columna referencia ocultos para poder utilizarlos posteriormente.
	var datoNivelLote= "<input type='hidden' id='"+opts.rowId+"_nivelLote' value='"+rData['nivel']+"'>";

	return datoNivelLote;
}

function p34ControlesPantalla(data){


	//Nos recorremos los registros existentes en pantalla.
	var rowsInPage = data.rows.length;
	//jQuery(gridP34.nameJQuery).jqGrid('getGridParam', 'rowNum');
	var existenRefConHijas = 0;

	for (i = 0; i < rowsInPage; i++){
		var idToDataIndex = $(gridP34.nameJQuery).jqGrid('getDataIDs');
		//Después busco dentro del array de rowId's el correspondiente a la fila
		var idFila = idToDataIndex[i];

		nivel = data.rows[i].nivel_lote;
		modeloProveedor = data.rows[i].nivel_mod_prov;
		
		//Obtenemos el número de orden para la búsqueda de textil.
		numOrden = data.rows[i].numOrden;

		//Si el nivel es 0 oculto el signo +
		if (nivel == 0 && modeloProveedor == 0){
			$("#gridP34SelReferencias #"+idFila+" td.sgcollapsed",gridP34[0]).unbind('click').html('');
			//$("#"+idFila+" td.sgcollapsed",gridP34[0]).unbind('click').html('');
			existenRefConHijas = 0;

		} else {

			existenRefConHijas = 1;

		}

		$("#jqgh_gridP34_subgrid").html("");
	}


	if ((existenRefConHijas == 1) && (rowsInPage == 1)) {
		//Miramos el textil
		p34expandirLotes();
		existenRefConHijas = 0;
	} else {
		$("#jqgh_gridP34_subgrid").html("");

	} 

}

function p34expandirLotes() {
	//Buscamos con número de orden.
	queryRef.numOrden = numOrden;
	
	//Expandimos el subgrid
	//$("#1 td.sgcollapsed",gridP34[0]).click();
	$('.ui-icon-plus').trigger('click');
}

function reloadDataP34AltaCatalogoSubgridN2(rowId,subgridDivId) {

	var codId = rowId.substr(rowId.indexOf("_")+1); //Identificador para enlazar con las referencias hijas

	if(nivelModProv == "S"){
		queryRef.codArticulo = $("#"+rowId+"_codArticuloGridOculto").val();
	}else{
		queryRef.codArticulo = $(gridP34.nameJQuery).getCell(rowId, "codArticulo");
	}

	var objJson = $.toJSON(queryRef.prepareToJsonObject());

	$.ajax({
		type : 'POST',
		url : './seleccionReferencia/loadDataSubGridTextil.do?page='+gridP34.getActualPage()+'&max='+gridP34.getRowNumPerPage()+'&index='+gridP34.getSortIndex()+'&sortorder='+gridP34.getSortOrder(),
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		async:false,
		success : function(data) {	
			var subgridTableId = subgridDivId + "_t";
			$("#" + subgridDivId).html("<table id='" + subgridTableId + "'></table>	");
			$("#" + subgridTableId).jqGrid({
				datatype: 'local',
				colNames :  gridP34.subgridColNames,
				colModel :  gridP34.subgridCm,
				//multiselect: true,
				altclass: "ui-priority-secondary",
				altRows: false,
				rowNum: data.records,
				sortable : true,
				subGridOptions: { expandOnLoad: true },
				height: '100%',
				//autowidth : true,
				//width : 200,
				onSelectRow: function(id){
					p34CodReferencia =$("#gridP34SelReferencias_"+codId+"_t").jqGrid('getCell', id ,'codArticulo');
					//En información básica, queremos que si seleccionamos una referencia del buscador universal, se abra el popup
					//de esa referencia con la información, pero además que se mantenga detras el popup del buscador universal,
					//para que al cerrar el popup de información, tengamos accesibles las demás referencias.
					if (pantallaDeBusqueda!="informacionBasica"){ 
						$("#p34_popupSelReferencias").dialog('close');
						$("#p34_popupSelReferenciasFoto").dialog('close');
					}
					loadSelection(p34CodReferencia);	
				}
			});


			$("#" + subgridTableId)[0].addJSONData(data);
			$("#" + subgridTableId).actualPage = data.page;
			$("#" + subgridTableId).localData = data;

			$(gridP34.nameJQuery).jqGrid('setGridWidth', $("#p34_AreaReferencias").width(), true);

			if(comboTextil){
				$("#" + subgridTableId).showCol("color"); 
				$("#" + subgridTableId).showCol("talla");     
				$("#" + subgridTableId).showCol("modelo_proveedor"); 
				$("#" + subgridTableId).showCol("activa"); 
				$("#" + subgridTableId).jqGrid('setGridWidth',682);


			}else{
				$("#" + subgridTableId).hideCol("color"); 
				$("#" + subgridTableId).hideCol("talla");     
				$("#" + subgridTableId).hideCol("modelo_proveedor");
				$("#" + subgridTableId).showCol("activa"); 
				$("#" + subgridTableId).jqGrid('setGridWidth', 682);

			}

			$(gridP34.nameJQuery).jqGrid('setGridWidth', $("#p34_AreaReferencias").width()+5, true);


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


/**
 *  P-52083
 *  Inicializa el objeto QueryReferenciasByDescr que contiene los atributos para realizar las querys en la busqueda de referencias alfanumericas
 *  @author BICUGUAL
 */
function getQueryRefFilters(){

	queryRef = new QueryReferenciasByDescr( $("#centerId").val());
	queryRef.descripcion = descripcion;
	queryRef.ean = eanBusqueda;
	queryRef.alfaNumerico = alfaNumericoBusqueda;
	queryRef.paginaConsulta = pantallaDeBusqueda;

	if (p34TipoPedido){
		if (p34TipoPedido == "E"){
			queryRef.altaCatalogo = true;
		} else if (p34TipoPedido == "M") {
			queryRef.activo = true;
		}
	}

	if(typeof(centroRelacionado) != "undefined"){ 
	
		//Si tiene centro relacionado, las búsquedas se hacen sobre ese centro.
		if(centroRelacionado != null && centroRelacionado != ""){
			queryRef.codCentroRelacionado = centroRelacionado;
		}
	}
}

/**
 * P-52083
 * Carga los parametros para realizar las busquedas.
 * Comprueba si se viene de reloadDataP34 para realizar las comprobaciones necesarias para ver si ha de cargar el popup de referencias
 * o si se viene de una recarga del grid y debe recargar los datos de busqueda
 * @author BICUGUAL
 */
function reloadData_gridP34SelReferencias() {	
	//Cargo los parametros para realizar las querys, tanto de cargarCombo como de cargarReferencias
	getQueryRefFilters();

	// Si se trata de busqueda alfanumerica de ean llamamos al metodo de carga original
	if(eanBusqueda){
		cargarReferenciaUnica();
	}
	else{
		//Si se viene de reloadDataP34, es la primera vez que se muestra la pantalla, debemos de cargar el combo.
		if(cargarCombo){
			cargaComboSecciones(descripcion);
		}
		else{
			//Al paginar el grid.
			if(cargarGrid == "S"){
				cargarReferenciasGrid();
			}else{
				cargarReferenciasFotos();
			}
		}
	}
}

/**
 * P-52083
 * Comienza a cargar el combo de secciones teniendo en cuenta el numero de secciones y el area a la que pertenecen.
 * @author BICUGUAL
 */
function cargaComboSecciones(descripcionActual){

	var objJson = $.toJSON(queryRef.prepareToJsonObject());

	$.ajax({
		type : 'POST',
		url : './seleccionReferencia/loadCombo.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {		
			//Inicializamos el flag a N
			flgIsMPorLot = "N";

			options="";

			var filasGrid = data.numLoteMpGrid.filasGrid;
			var numLote = data.numLoteMpGrid.nivelLote;
			var numMp = data.numLoteMpGrid.nivelModProv;

			//Pregunto si existen referencias
			if (filasGrid == 0 && numLote == 0 && numMp == 0){
				//Si buscas en el buscador y no encuentra referencias, vuelve al input de información básica.
				if (pantallaDeBusqueda=="informacionBasica"){
					$("#p03_btn_aceptar").bind("click", function(e) {
						$("#p01_txt_infoRef").focus();
						$("#p01_txt_infoRef").select();
						e.stopPropagation();}); 
				//Si buscas en el buscador y no encuentra referencias, vuelve al input de intertienda
				}else if (pantallaDeBusqueda=="intertienda"){
					$("#p03_btn_aceptar").bind("click", function(e) {
						$("#p72_fld_referencia").focus();
						$("#p72_fld_referencia").select();
						e.stopPropagation();}); 
				}

				if (!alfaNumericoBusqueda){
					//En información básica, queremos que si seleccionamos una referencia del buscador universal, se abra el popup
					//de esa referencia con la información, pero además que se mantenga detras el popup del buscador universal,
					//para que al cerrar el popup de información, tengamos accesibles las demás referencias.
					loadSelection(descripcionActual);
					if (pantallaDeBusqueda!="informacionBasica"){
						$("#p34_popupSelReferenciasFoto").dialog('close');
					}
				}else{
					$("#infoRef").show();

					//Si no existen muestro mensaje de error
					createAlert(replaceSpecialCharacters(emptyRecordSelection), "ERROR");
				}
			//Si existe una sola referencia
			}else if(filasGrid == 1 && numLote == 1 && numMp == 1){
				//En información básica, queremos que si seleccionamos una referencia del buscador universal, se abra el popup
				//de esa referencia con la información, pero además que se mantenga detras el popup del buscador universal,
				//para que al cerrar el popup de información, tengamos accesibles las demás referencias.
				cargarReferenciaUnica();
				if (pantallaDeBusqueda!="informacionBasica"){ 
					$("#p34_popupSelReferenciasFoto").dialog('close');
				}
			//Si existe mas de una referencia
			}else if(filasGrid >1 || numLote > 1 || numMp >1){
				if(alfaNumericoBusqueda || (filasGrid > 1 && (numLote > 1 || numMp > 1))){
					comboPopup = "Foto";
				}else{
					comboPopup = "";

					//Si entra aquí, significa que has buscado un número en el buscador universal
					//y te está indicando de que ha encontrado más de una referencia, por lo que es modelo proveedor o un lote
					flgIsMPorLot = "S";
				}

				//Compruebo si existe solo una seccion para cargar el combo
				if (data.lstSecciones.length==1){
					var codigo=data.lstSecciones[0].codigo;
					var descripcion=data.lstSecciones[0].descripcion;
					var area=data.lstSecciones[0].codArea;

					//Guardo la opcion para meterla despues en el combo
					options = options
					+ "<option value='"+ codigo + "'  data-area='"	+ area+ "'>"
					+ formatDescripcionCombo(codigo,descripcion) 
					+ "</option>";

					//Selecciono opcion que aparecera en el combo 
					$("#p34_cmb_seccion"+comboPopup).combobox('autocomplete',
							formatDescripcionCombo(codigo, descripcion));

					//Configuro el codigo de seleccion para que se realice busqueda de referencias por esa seccion
					p34Seccion=codigo;

					//Si el La seccion seleccionada en el combo es TEXTIL Se muestran las columnas
					if(area == 3){
						comboTextil=true;
						$(gridP34.nameJQuery).showCol("talla");
						$(gridP34.nameJQuery).showCol("color");
						$(gridP34.nameJQuery).showCol("modelo_proveedor");

					}else{
						comboTextil=false;
						$(gridP34.nameJQuery).hideCol("talla");
						$(gridP34.nameJQuery).hideCol("color");
						$(gridP34.nameJQuery).hideCol("modelo_proveedor");
					}
					//Cargo los datos del combo
					$("select#p34_cmb_seccion"+comboPopup).html(options);
					
					//Seleccionamos el primer elemento.
					$("#p34_cmb_seccion"+comboPopup).val(codigo)
					
					//Cargamos la categoria.
					loadP34_cmbCategory();
				}else{
					options = "<option value='0'>&nbsp;</option>";
					//Recorro los resultados para formatear los options
					for (i = 0; i < data.lstSecciones.length; i++) {

						options = options
						+ "<option value='"	+ data.lstSecciones[i].codigo+ "'  data-area='"	+ data.lstSecciones[i].codArea+ "'>"
						+ formatDescripcionCombo(data.lstSecciones[i].codigo,
								data.lstSecciones[i].descripcion) + "</option>";
					}
					//Cargo los datos del combo
					$("select#p34_cmb_seccion"+comboPopup).html(options);
				}

				//Cargo el comportamiento del combo cuando se selecciona
				$("#p34_cmb_seccion"+comboPopup).combobox({
					selected: function(event, ui) {

						if ( ui.item.value!="" && ui.item.value!="null") {
							if ($("#p34_cmb_seccion"+comboPopup).val()!=null){
								$("#p34_cmb_seccion"+comboPopup).combobox('autocomplete',$("#p34_cmb_seccion"+comboPopup).val());
								p34Seccion=$("#p34_cmb_seccion"+comboPopup).combobox('getValue');
								$(gridP34.nameJQuery).jqGrid('clearGridData');

								//Si el combo seleccionado es el de las fotos, se inicializan todos los datos de paginación etc, para que los datos comiencen
								//a mostrarse de la página 1.
								if(cargarGrid == "N"){
									p34Categoria = null;
									loadP34_cmbCategory();
									numeroDePaginaPopupP34 = 1;
									idxInicioListaP34 = null;
									idxFinListaP34 = null;
									largoListaP34 = null;
								}
								reloadData_gridP34SelReferencias();
							}
						}
					}
				});

				//Si la búsqueda es alfanumérica se buscan fotos, si no se muestra el grid.
				if(alfaNumericoBusqueda || (filasGrid > 1 && (numLote > 1 || numMp > 1))){
					cargarReferenciasFotos();
				}else{
					cargarReferenciasGrid();
				}
			}
			
		},
		error : function (xhr, status, error){
			$("#infoRef").show();
			handleError(xhr, status, error, locale);
		}			
	});
}

function loadP34_cmbCategory(){
	var options = "";
	var optionNull = "";
	var descCatNuevo = "";
	$("#p34_cmb_categoria").combobox(null);

	queryRef.codSeccion = $("#p34_cmb_seccionFoto").val();
	var objJson = $.toJSON(queryRef.prepareToJsonObject());
	$.ajax({
		type : 'POST',
		url : './seleccionReferencia/loadCategoria.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			if(data != null){
				if(data.length > 0){
					options = "<option value='0'>&nbsp;</option>";
					for (i = 0; i < data.length; i++){
						options = options + "<option value='" + data[i].codCategoria + "'>" +formatDescripcionCombo(data[i].codCategoria,data[i].descripcionCat) + "</option>";

					}
					$("select#p34_cmb_categoria").html(options);


					$("#p34_cmb_categoria").combobox("enable");
					$("#p34_cmb_categoria").combobox('autocomplete',descCatNuevo);

				}else{
					$("#p34_cmb_categoria").combobox("disable");
				}
			}else{
				$("#p34_cmb_categoria").combobox("disable");
			}
			//Ocultamos las esferas de LOT y MP si el codArea = 3. Si no las mostramos al cambiar se sección.
			if($('#p34_cmb_seccionFoto option:selected').data('area') == 3){
				$("#p34_leyendaLot").show();
				$("#p34_leyendaMP").show();
			}else{
				$("#p34_leyendaLot").hide();
				$("#p34_leyendaMP").hide();
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		
	$("#p34_cmb_categoria").combobox({
		selected: function(event, ui) {
			if ( ui.item.value!="" && ui.item.value!="null") {
				if ($("#p34_cmb_categoria").val()!=null){
					$("#p34_cmb_categoria").combobox('autocomplete',$("#p34_cmb_categoria").val());
					p34Categoria=$("#p34_cmb_categoria").combobox('getValue');
					numeroDePaginaPopupP34 = 1;
					idxInicioListaP34 = null;
					idxFinListaP34 = null;
					largoListaP34 = null;
					cargarReferenciasFotos();
				}
			}
		}  
	,	
	changed: function(event, ui) { 
		if ( ui.item.value!="" && ui.item.value!="null") {
			if ($("#p34_cmb_categoria").val()!=null){
				$("#p34_cmb_categoria").combobox('autocomplete',$("#p34_cmb_categoria").val());
				p34Categoria=$("#p34_cmb_categoria").combobox('getValue');
				numeroDePaginaPopupP34 = 1;
				idxInicioListaP34 = null;
				idxFinListaP34 = null;
				largoListaP34 = null;
				cargarReferenciasFotos(); 
			}
		}
	}
	});

	$("#p34_cmb_categoria").combobox('autocomplete',optionNull);
	$("#p34_cmb_categoria").combobox('comboautocomplete',null);
}

/**
 * P-52083
 * Carga los parametros para realizar las busquedas.
 * Comprueba si se viene de reloadDataP34 para realizar las comprobaciones necesarias para ver si ha de cargar el popup de referencias
 * o si se viene de una recarga del grid y debe recargar los datos de busqueda
 * @author BICUGUAL
 */
function cargarReferenciasGrid(){
	if (p34Seccion==null || p34Seccion==''){
		p34Seccion=$("#p34_cmb_seccion").combobox('getValue');
	}

	queryRef.codSeccion = p34Seccion;

	var objJson = $.toJSON(queryRef.prepareToJsonObject());

	$('#p34_AreaReferencias .loading').css("display", "block");
	$.ajax({
		type : 'POST',
		url : './seleccionReferencia/loadDataGrid.do?page='+gridP34.getActualPage()+'&max='+gridP34.getRowNumPerPage()+'&index='+gridP34.getSortIndex()+'&sortorder='+gridP34.getSortOrder(),
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {	
			//Miramos si la referencia buscada en el buscador universal es MP o Lot
			if(flgIsMPorLot == "S"){
				//Indicamos que no es de tipo modelo proveedor, por lo que es de tipo lote.
				nivelModProv = "N";
				//Si cumple esta condición es de tipo modelo proveedor.
				if(data.rows[0].nivel_mod_prov > 0 && data.rows[0].nivel_lote == 0){
					nivelModProv = "S";
				}
			}

			//cargamos el grid Normal
			$(gridP34.nameJQuery)[0].addJSONData(data);
			gridP34.actualPage = data.page;
			gridP34.localData = data;

			p34ControlesPantalla(data);

			//Control para mostrar las columnas activa o catalogo
			if (pantallaDeBusqueda == "informacionBasica" || pantallaDeBusqueda == "consultaDatos" || pantallaDeBusqueda == "intertienda"){

				$(gridP34.nameJQuery).hideCol("catalogo");
				$(gridP34.nameJQuery).showCol("activa");
			}
			else if (pantallaDeBusqueda =="nuevoPedidoAdicional-encargos" || pantallaDeBusqueda == "nuevoPedidoAdicional-montaje"){
				$(gridP34.nameJQuery).hideCol("activa");
				$(gridP34.nameJQuery).hideCol("catalogo");
			}
			//Para el popup de busquedas de sfm mostramos la misma informacion que para pedido adicional
			else if (pantallaDeBusqueda == "informacionSfm"){
				$(gridP34.nameJQuery).hideCol("activa");
				$(gridP34.nameJQuery).hideCol("catalogo");
			}

			if (!cargarCombo){
				//Si el La seccion seleccionada en el combo es TEXTIL Se muestran las columnas
				if($('#p34_cmb_seccion option:selected').data('area') == 3){
					comboTextil=true;
					$(gridP34.nameJQuery).showCol("talla");
					$(gridP34.nameJQuery).showCol("color");
					$(gridP34.nameJQuery).showCol("modelo_proveedor");

				}else{
					comboTextil=false;
					$(gridP34.nameJQuery).hideCol("talla");
					$(gridP34.nameJQuery).hideCol("color");
					$(gridP34.nameJQuery).hideCol("modelo_proveedor");

				}
			}

			$("#gbox_gridP34SelReferencias").show();
			//Ajustamos el tamaño
			$(gridP34.nameJQuery).jqGrid('setGridWidth', $("#p34_AreaReferencias").width(), true);

			$("#p34_AreaReferencias .loading").css("display", "none");

			//Abrimos el popup
			$("#p34_popupSelReferencias").dialog( "open" );

			cargarCombo=false;

			//Indicamos que se ha cargado el grid.
			cargarGrid = "S";
			$("#infoRef").show();
		},
		error : function (xhr, status, error){
			$("#AreaResultados .loading").css("display", "none");	
			handleError(xhr, status, error, locale);				
		}
	});

}

function cargarReferenciasFotos(){

	if (p34Seccion==null || p34Seccion==''){
		p34Seccion=$("#p34_cmb_seccionFoto").combobox('getValue');
	}

	if(p34Categoria == null || p34Categoria == ''){
		p34Categoria=$("#p34_cmb_categoria").combobox('getValue');
	}
	queryRef.codSeccion = p34Seccion;
	queryRef.codCategoria = p34Categoria;

	var objJson = $.toJSON(queryRef.prepareToJsonObject());

	$("#p34_AreaReferenciasFoto .loading").css("display", "block");
	$.ajax({
		type : 'POST',
		url : './seleccionReferencia/loadDataGrid.do?page='+numeroDePaginaPopupP34+'&max='+maxElementosPopupP34+'&index=null&sortorder=asc',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
			limpiarFotosAnteriores();
			dibujarFotosBuscadorUniversal(data);

			//Mostramos lo de leyenda inactiva sólo si no Pedido Adicional búsqueda por montajes adicionales o encargos.
			//En estos casos, se decidió solo sacar las referencias activas, por lo que nunca va a salir una referencia
			//que no esté activa y no tiene sentido mantener la leyenda.
			
			//Misumi-157
			//Para intertienda se quieren sacar solo las activas, por lo que hacemos lo mismo que en montajes.
			if( pantallaDeBusqueda == "nuevoPedidoAdicional-montaje"){
				//Ocultamos leyenda referencias inactivas
				$("#p34_leyendaInactiva").hide();
				
				//Poenemos texto alta catálogo
				$("#p34_leyendaTxtRefActiva").text(p34RefActiva);
				
				//Ocultamos el texto de gama tienda
				$("#p34_areaGamaActiva").hide();
			}else if(pantallaDeBusqueda == "nuevoPedidoAdicional-encargos"){
				//Ocultamos leyenda referencias inactivas
				$("#p34_leyendaInactiva").hide();
				
				//Ponemos texto referencia activa
				$("#p34_leyendaTxtRefActiva").text(p34AltaCatalogo);
				
				//Ocultamos el texto de gama tienda
				$("#p34_areaGamaActiva").hide();
			}else if(pantallaDeBusqueda == "intertienda"){
				//Ocultamos leyenda referencias inactivas
				$("#p34_leyendaInactiva").hide();
				
				//Ponemos texto referencia activa
				$("#p34_leyendaTxtRefActiva").text(p34RefActiva);
				
				if(centroRelacionado != null && centroRelacionado != "" && centroRelacionado != undefined){
					//Mostramos el mensaje de gama activa y el centro.
					$("#p34_lbl_gamaActiva").text(p34GamaActiva + " " + centroRelacionado +"-" + defCentroRelacionado);
				}
				
				//Mostramos el tecto de gama tienda
				$("#p34_areaGamaActiva").show();
			}else{
				//Ocultamos leyenda referencias inactivas
				$("#p34_leyendaInactiva").show();
				
				//Ponemos texto referencia activa
				$("#p34_leyendaTxtRefActiva").text(p34RefActiva);
				
				//Ocultamos el texto de gama tienda
				$("#p34_areaGamaActiva").hide();
			}
			//Calculamos el inicio de la lista y el final
			idxInicioListaP34 = (numeroDePaginaPopupP34-1)*maxElementosPopupP34;
			idxFinListaP34 = (numeroDePaginaPopupP34)*maxElementosPopupP34;
			largoListaP34 = data.records;

			if(idxFinListaP34 > largoListaP34){
				idxFinListaP34 = largoListaP34;
			}			
			actualizarNumeroDePaginaP34(idxInicioListaP34,idxFinListaP34,largoListaP34);
			cargarCombo=false;
			$("#infoRef").show();
			$( "#p34_popupSelReferenciasFoto" ).dialog( "open" );
		},
		error : function (xhr, status, error){
			$("#AreaResultados .loading").css("display", "none");	
			handleError(xhr, status, error, locale);				
		}
	});

}

function dibujarFotosBuscadorUniversal(data){
	var estructuraFotoBuscadorUniversal = null;

	for(var i = 0; i<data.rows.length;i++){
		referenciasByDescr = data.rows[i];

		//Se obtiene la esctructura y se cambian los ids adecuados al nuevo formulario
		estructuraFotoBuscadorUniversal = $("#formFotoBUEstructura1").prop('outerHTML');
		estructuraFotoBuscadorUniversal = estructuraFotoBuscadorUniversal.replace("formFotoBUEstructura1","formFotoBUEstructura1"+i);
		estructuraFotoBuscadorUniversal = estructuraFotoBuscadorUniversal.replace("formFotoBUEstructura1FotoProducto","formFotoBUEstructura1FotoProducto"+i);
		estructuraFotoBuscadorUniversal = estructuraFotoBuscadorUniversal.replace("formFotoBUEstructura1FotoProductoImg","formFotoBUEstructura1FotoProductoImg"+i);
		estructuraFotoBuscadorUniversal = estructuraFotoBuscadorUniversal.replace("formFotoBUEstructura1FotoAlerta1","formFotoBUEstructura1FotoAlerta1"+i);
		estructuraFotoBuscadorUniversal = estructuraFotoBuscadorUniversal.replace("formFotoBUEstructura1FotoAlerta2","formFotoBUEstructura1FotoAlerta2"+i);
		estructuraFotoBuscadorUniversal = estructuraFotoBuscadorUniversal.replace("formFotoBUEstructura1InfoAlerta1","formFotoBUEstructura1InfoAlerta1"+i);
		estructuraFotoBuscadorUniversal = estructuraFotoBuscadorUniversal.replace("formFotoBUEstructura1InfoProducto","formFotoBUEstructura1InfoProducto"+i);
		estructuraFotoBuscadorUniversal = estructuraFotoBuscadorUniversal.replace("formFotoBUEstructura1InfoAlerta1UC","formFotoBUEstructura1InfoAlerta1UC"+i);
		estructuraFotoBuscadorUniversal = estructuraFotoBuscadorUniversal.replace("formFotoBUEstructura1CodArt","formFotoBUEstructura1CodArt"+i);
		estructuraFotoBuscadorUniversal = estructuraFotoBuscadorUniversal.replace("formFotoBUEstructura1Lote","formFotoBUEstructura1Lote"+i);
		estructuraFotoBuscadorUniversal = estructuraFotoBuscadorUniversal.replace("formFotoBUEstructura1ModeloProveedor","formFotoBUEstructura1ModeloProveedor"+i);

		//Se inserta el formulario en su sección correspondiente del popup
		$("#p34_listaFotos").append(estructuraFotoBuscadorUniversal);

		loadP34Foto(referenciasByDescr,i);

		//Hiddens
		$("#formFotoBUEstructura1Lote"+i).text("N");
		$("#formFotoBUEstructura1ModeloProveedor"+i).text("N");
		$("#formFotoBUEstructura1CodArt"+i).text(referenciasByDescr.codArticulo);

		//Si la referencia no es de tipo modelo proveedor (es decir es una referencia normal o de lote), se muestra el tick y la cruz
		if(referenciasByDescr.nivel_mod_prov == 0 || referenciasByDescr.nivel_mod_prov == null || referenciasByDescr.nivel_lote > 0){
			var alertaTick = false;
			var alertaCruz = false;
			if (pantallaDeBusqueda == 'informacionBasica' || pantallaDeBusqueda == 'consultaDatos' || pantallaDeBusqueda == "crearDevolucionP86"  || pantallaDeBusqueda=='nuevoPedidoAdicional-montaje' || pantallaDeBusqueda == 'intertienda'){
				if(referenciasByDescr.activa == 'S'){
					$("#formFotoBUEstructura1FotoAlerta1"+i).addClass("tick");
					alertaTick = true;
				}else{
					$("#formFotoBUEstructura1FotoAlerta1"+i).addClass("cruz");
					alertaCruz = true;
				}
			}else if(pantallaDeBusqueda=='nuevoPedidoAdicional-encargos'){
				$("#formFotoBUEstructura1FotoAlerta1"+i).addClass("tick");
				alertaTick = true;
			}else if(!(pantallaDeBusqueda=='informacionBasica' || pantallaDeBusqueda=='consultaDatos' || pantallaDeBusqueda=='nuevoPedidoAdicional-encargos' || pantallaDeBusqueda=='nuevoPedidoAdicional-montaje' || pantallaDeBusqueda=='informacionSfm' || pantallaDeBusqueda == 'intertienda')){
				//Mostramos el catalogo
			}

			//Miramos si es de tipo lote.
			if(referenciasByDescr.nivel_lote > 0){
				/*if(!alertaTick && !alertaCruz){
					$("#formFotoBUEstructura1FotoAlerta1"+i).addClass("esferaVerde");
					$("#formFotoBUEstructura1FotoAlerta1"+i).append("<div class='p34_textoCentradoHV p34_colorTexto'>"+loteAlerta+"</div>");
					$("#formFotoBUEstructura1Lote"+i).text("S");
				}else{
					$("#formFotoBUEstructura1FotoAlerta2"+i).addClass("esferaVerde");
					$("#formFotoBUEstructura1FotoAlerta2"+i).append("<div class='p34_textoCentradoHV p34_colorTexto'>"+loteAlerta+"</div>");
					$("#formFotoBUEstructura1Lote"+i).text("S");
				}*/
				$("#formFotoBUEstructura1FotoAlerta2"+i).addClass("esferaVerde");
				$("#formFotoBUEstructura1FotoAlerta2"+i).append("<div class='p34_textoCentradoHV p34_colorTexto'>"+loteAlerta+"</div>");
				$("#formFotoBUEstructura1Lote"+i).text("S");
			}

			//Introducimos referencia-descripción en los casos de referencia normal o lote.
			$("#formFotoBUEstructura1InfoProducto"+i).text(referenciasByDescr.codArticulo + "-" + referenciasByDescr.descripcion);
		}else{
			$("#formFotoBUEstructura1FotoAlerta2"+i).addClass("esferaVerde");			
			$("#formFotoBUEstructura1FotoAlerta2"+i).append("<div class='p34_textoCentradoHV p34_colorTexto'>"+modeloProveedorAlerta+"</div>");

			//Introducimos modelo_proveedor-descripción
			$("#formFotoBUEstructura1InfoProducto"+i).text(referenciasByDescr.modelo_proveedor + "-" + referenciasByDescr.descripcion);
			$("#formFotoBUEstructura1ModeloProveedor"+i).text("S");
		}
	}
	//Añadimos evento
	event_loadSelection();
	event_iluminar();
	event_noIluminar();

}

function loadP34Foto(referenciasByDescr,pos){
	if (referenciasByDescr.tieneFoto == "S"){
		var url = "./welcome/getImage.do?codArticulo="+referenciasByDescr.codArticulo;
		$('#formFotoBUEstructura1FotoProductoImg'+pos).attr('src',url);
	} else {
		$('#formFotoBUEstructura1FotoProductoImg'+pos).attr('src','./misumi/images/nofotoRecortada.png?version{misumiVersion}');
	}			
}

//Función que sirve para limpiar del popup las fotos anteriores y para ocultar la paginación. 
function limpiarFotosAnteriores(){
	$("#p34_listaFotos").empty();
	$("#p34_AreaReferenciasFotos").hide();
}

//Función que sirve para paginar entre los formularios
function paginacionFormulariosP34(flechaSig){
	//Si se ha presionado la flecha siguiente
	if(flechaSig == 'S'){
		//Si hay más formularios de los que se ven en la página, paginar
		if((idxFinListaP34 < largoListaP34) && $("#p34_pagSiguiente").hasClass("p34_pagSig")){		
			//Limpiar las devoluciones anteriores
			limpiarFotosAnteriores();

			//Actualizamos el número de página del popup
			numeroDePaginaPopupP34 ++;

			//Dibujar los formularios
			cargarReferenciasFotos();	
		}
		//Si se ha presionado la flecha anterior
	}else{
		//Si hay más formularios de los que se ven en la página, paginar
		if((idxInicioListaP34 > 0) && $("#p34_pagAnterior").hasClass("p34_pagAnt")){		
			//Limpiar las devoluciones anteriores
			limpiarFotosAnteriores();

			//Actualizamos el número de página del popup
			numeroDePaginaPopupP34 --;

			//Dibujar los formularios
			cargarReferenciasFotos();
		}
	}
}

/************************ EVENTOS ************************/
function event_iluminar(){
	//Iluminar la foto
	$(".fotoBU").mouseover(function () {		
		$(this).addClass("iluminar");
	});	
}

function event_noIluminar(){
	//Desiluminar foto
	$(".fotoBU").mouseout(function () {		
		$(this).removeClass("iluminar");
	});	
}

//Evento para flechas 
function events_p34_flecha_ant(){
	$("#p34_pagAnterior").click(function () {		
		paginacionFormulariosP34('N');
	});	
}

//Evento para flechas 
function events_p34_flecha_sig(){
	$("#p34_pagSiguiente").click(function () {		
		paginacionFormulariosP34('S');
	});	
}

//Evento cuando se clica la imagen. Así se abre la información de esa referencia.
function event_loadSelection(){
	$(".fotoBU").click(function () {	
		var idFicha = $(this).attr('id');
		var numIdFicha = idFicha.replace("formFotoBUEstructura1",'');
		var codArticulo = $("#formFotoBUEstructura1CodArt"+numIdFicha).text();

		var lote = $("#formFotoBUEstructura1Lote"+numIdFicha).text();
		nivelModProv = $("#formFotoBUEstructura1ModeloProveedor"+numIdFicha).text();
		if(lote == "S" || nivelModProv == "S"){
			descripcion=codArticulo;
			alfaNumericoBusqueda = false;
			eanBusqueda = false;
			cargarCombo=true;
			comboPopup = "";
			p34SeccionFoto = p34Seccion;
			reset_p34();
			getQueryRefFilters();
			queryRef.flgNivelModProv = nivelModProv;
			queryRef.codArticulo = codArticulo;
			cargaComboSecciones(descripcion);
		}else{
			//En información básica, queremos que si seleccionamos una referencia del buscador universal, se abra el popup
			//de esa referencia con la información, pero además que se mantenga detras el popup del buscador universal,
			//para que al cerrar el popup de información, tengamos accesibles las demás referencias.
			if (pantallaDeBusqueda!="informacionBasica"){ 
				$("#p34_popupSelReferenciasFoto").dialog('close');
			}
			loadSelection(codArticulo);
		}
	});	
}

//Función que actualiza las imágenes de flechas del popup. Y el número que aparece 
//debajo de las fotos 6/11, 5/5, 12/24, etc.
function actualizarNumeroDePaginaP34(idxInicioListaP34,idxFinListaP34,largoListaP34){
	//Eliminar la imagen de la flecha actual
	$("#p34_pagAnterior").removeClass("p34_pagAnt");
	$("#p34_pagAnterior").removeClass("p34_pagAnt_Des");

	$("#p34_pagSiguiente").removeClass("p34_pagSig");
	$("#p34_pagSiguiente").removeClass("p34_pagSig_Des");

	//Añadir la flecha correspondiente
	if(idxInicioListaP34 == 0){
		$("#p34_pagAnterior").addClass("p34_pagAnt_Des");
	}else{
		$("#p34_pagAnterior").addClass("p34_pagAnt");
	}

	//Se hace +1 para saber el número de elemento que estamos obteniendo. Por ejemplo si
	//el último índice de los objetos a mostrar de la lista es el 5 equivaldría al elemento 6.
	//En el caso de que el último indice sea el 5 y la lista tenga 6 elementos, quiere decir que
	//la flecha tiene que estar invalidada, pero 5<6!!! Por eso se hace +1, para que el indice
	//equivalga al número de objeto
	if(idxFinListaP34 < largoListaP34){
		$("#p34_pagSiguiente").addClass("p34_pagSig");
	}else{
		$("#p34_pagSiguiente").addClass("p34_pagSig_Des");
	}

	//Actualiza el número de debajo de los formularios. 
	//Indica cuántos formularios se han mostrado y el total de 
	//formularios a mostrar.
	$("#p34_paginaActual").text(idxFinListaP34+"/");
	$("#p34_paginaTotal").text(largoListaP34);

	//Enseñamos el número de página
	$("#p34_AreaReferenciasFotos").show();
}
/**
 * P-52083
 * Este metodo se utiliza bien porque la busqueda es por EAN o porque se ha introducido una descripcion que solo devuelve un registro.
 * @author BICUGUAL
 */
function cargarReferenciaUnica(){
	var objJson = $.toJSON(queryRef.prepareToJsonObject());

	$.ajax({
		type : 'POST',
		url : './seleccionReferencia/loadDataGrid.do?page='+gridP34.getActualPage()+'&max='+gridP34.getRowNumPerPage()+'&index='+gridP34.getSortIndex()+'&sortorder='+gridP34.getSortOrder(),
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
			//Si la busqueda devuelve registros
			if (data.records>0 && data.rows.length != 0){
				loadSelection(data.rows[0].codArticulo);
			}else{
				$("#infoRef").show();
				createAlert(replaceSpecialCharacters(emptyRecordSelection), "ERROR");
			}
		},
		error : function (xhr, status, error){
			$("#AreaResultados .loading").css("display", "none");	
			handleError(xhr, status, error, locale);				
		}
	});
}
