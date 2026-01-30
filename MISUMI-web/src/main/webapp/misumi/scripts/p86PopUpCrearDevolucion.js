/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/****************************************************************** CONSTANTES ***************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Insertamos el número máximo de enteros y decimales que se podrán introducir. Servirán para P83 y P86
var maxNumEnterosP86 = 15;
var maxNumDecimalesP86 = 3;
var stockPrincipalBandejasP86 = 'B';

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/**************************************************************** VARIABLES ******************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Variable que guarda el texto del combo plataforma
var plataformaCmbP86 = null;

//Variable que guarda el texto del combo proveedor
var proveedorCmbP86 = null;

//Variable que guarda el texto del combo sin recogida.
var sinRecogidaCmbP86 = null;

//En la primera carga se inicia el contador a 1.
var numRowP86 = 1;

//La lista guarda los elementos modificados de cada página. Si hay un elemento modificado, se edita y sale error, recupera los datos por defecto, pero el elemento
//sigue estando en esta lista. 
var seleccionadosP86 = new Array();

//La lista de seleccionados guarda todos los registros que han sido modificados en todas las páginas. En caso de existir un registro modificado y al volverlo a modificar devuelve error
//ese registro se elimina de esta lista, pues el registro obtiene los valores por defecto y por ende deja de estar modificado. Si se vuelve a modificar se inserta
//de nuevo aquí.
var modificadosP86 = new Array();

////Definimos ultimoBultoIntroducidoP86 para guardar el último bulto introducido por proveedor y que se copie en los demás bultos
var ultimoBultoIntroducidoP86 = {};

//Guarda una lista de DevolucionLineaModificado, que contiene por cada línea modificada de la página actual un objeto con 
//datos del: bulto, stockDevuelto,codigoError y codigoArticulo.
var listadoModificadosP86 = new Array();

var modificados = new Array();

//Variables de error
var refExists = null;
var formPartOneRequired = null;
var formPartOneRequiredSave = null;
var noSelectedRows = null;
var errorBorradoSesionGrid = null;
var notPosibleToReturnDataP86 = null;
var stockDevueltoIncorrectoP86 = null;
var stockDevueltoEnteroErroneoP86 = null;
var stockDevueltoDecimalErroneoP86 = null;
var stockDevueltoDecimalErroneo2P86 = null;
var emptyGrid = null;
var platDistintaP86 = null;
var errorPlatP86 = null;
var noMismaSeccionP86 = null;
var emptyGridSave = null;
var cantidadMaximaNoSuperable = null;

//Variables de mensajes.
var guardadoCorrectamenteP86;

//Texto de iconos
var iconoGuardadoP86=null;
var iconoModificadoP86 = null;

//Guarda el valor del bulto anterior
var valorBultoAnterior = null;

var validadoCantMax ;
function initializeScreenP86(){
	//Inicializa el popup que contendrá los formularios para crear las devoluciones 
	inicializarPopUpCrearDevolucion();

	//Inicializa el combobox del tipo de devolución.
	inicializarCmbTipo();

	//Inicializa el grid
	loadP86(locale);


	//Nos posicionamos en el campo al que ha navegado el usuario y que hemos almacenado en el hidden.
	//De esta forma al clicar aceptar en la alerta, vuelve al último campo de edición del grid.
	$("#p03_btn_aceptar").bind("click", function(e) {
		if ($("#p86_fld_StockDevuelto_Selecc").val() != '')
		{
			$("#"+$("#p86_fld_StockDevuelto_Selecc").val()).focus();
			$("#"+$("#p86_fld_StockDevuelto_Selecc").val()).select();
			e.stopPropagation();
			$("#p86_fld_StockDevuelto_Selecc").val('');
		}
	});
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************ INICIALIZACIÓN DE ELEMENTOS E IDIOMA *******************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

/************************ IDIOMA ************************/
//Función para buscar los mensajes de la pantalla en idioma seleccionado, por defecto 'es'
function loadP86(locale){
	//Define la estructura del grid, sus columnas de dónde van a obtener los datos etc.
	gridP86 = new GridP86CrearDevolucion(locale);

	var jqxhr = $.getJSON(gridP86.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		//Carga el nombre de las columnas, titulo, etc.
		gridP86.colNames = data.ColNames;
		gridP86.title = data.GridTitle;
		gridP86.emptyRecords= data.emptyRecords;

		//Variables de los comboboxes
		plataformaCmbP86 = data.plataformaCmb;
		proveedorCmbP86 = data.proveedorCmb;
		sinRecogidaCmbP86 = data.sinRecogidaCmb;

		//Iconos
		iconoGuardadoP86 = data.iconoGuardado; 
		iconoModificadoP86 = data.iconoModificado;

		//Mensajes de error
		refExists = data.refExists;
		formPartOneRequired = data.formPartOneRequired; 
		noSelectedRows = data.noSelectedRows;
		errorBorradoSesionGrid = data.errorBorradoSesionGrid;
		notPosibleToReturnDataP86 = data.notPosibleToReturnData;
		stockDevueltoIncorrectoP86 = data.stockDevueltoIncorrecto;
		stockDevueltoEnteroErroneoP86 = data.stockDevueltoEnteroErroneo;
		stockDevueltoDecimalErroneoP86 = data.stockDevueltoDecimalErroneo;
		stockDevueltoDecimalErroneo2P86 = data.stockDevueltoDecimalErroneo2;
		emptyGrid = data.emptyGrid;
		platDistintaP86 = data.platDistinta;
		errorPlatP86 = data.errorPlat;
		noMismaSeccionP86 = data.noMismaSeccion;
		emptyGridSave = data.emptyGridSave;
		formPartOneRequiredSave = data.formPartOneRequiredSave;
		cantidadMaximaNoSuperable = data.cantidadMaximaNoSuperable;
		
		//Mensaje correcto
		guardadoCorrectamenteP86 = data.guardadoCorrectamente;

		//Inicializa los combos del popup RECOGIDA Y ABONO
		load_cmbP86CrearDevolucion();

		//Inicializa el combo del popup del tipo de devolución.
		load_cmbP86TipoDevolucion();

		//Definimos las columnas, las llamadas al paginar, ordenar etc.
		load_gridP86CrearDevolucionMock(gridP86);

		//Inicializamos los eventos del popup.
		inicializarEventosP86();

		//Ponemos reglas para los input de stockDevuelto(cantidad) y bulto
		regexForInputP86();	

		//Ponemos titulos a las columnas del grid.
		p86SetHeadersTitles(data);
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

/************************ POPUP ************************/
function inicializarPopUpCrearDevolucion(){
	//Definión del popup que contendrá los formularios
	$("#p86_popup").dialog({
		autoOpen: false,
		height: 'auto',
		width: 'auto',
		modal: true,
		title: "",
		resizable: false,
		dialogClass: "",
		open: function() {
			//Si se elimina la sesión incorrectamente, cerramos el popup y mostramos error.
			if(!removeGridP86()){
				createAlert(replaceSpecialCharacters(errorBorradoSesionGrid), "ERROR");
				$(this).dialog('close');
			}
		},
		close:function(){
			resetP86Dialog();
		},
		stack:false
	});
}

/************************ COMBOBOX ************************/
function inicializarCmbTipo(){

}

/************************ RESET P86 ************************/

//Resetea el 2 bloque del dialogo.
function resetP86BloqueDos(){
	$("#p86_fld_cant").attr('disabled','disabled');
	$('#p86_fld_band').attr('disabled','disabled');
	$("#p86_fld_bulto").attr('disabled','disabled');

	$("#p86_fld_ref").val("");
	$("#p86_fld_denom").val("");
	$("#p86_fld_stk").val("");
	$("#p86_fld_cant").val("");
	$("#p86_fld_band").val("");
	$("#p86_fld_bulto").val("");

	$("#p86_fld_cant").removeClass("p86_inputError");
	$("#p86_fld_band").removeClass("p86_inputError");
	$("#p86_fld_bulto").removeClass("p86_inputError");
}

//Resetea el dialogo entero.
function resetP86Dialog(){
	$("#p86_fld_cant").attr('disabled','disabled');
	$('#p86_fld_band').attr('disabled','disabled');
	$("#p86_fld_bulto").attr('disabled','disabled');

	$("#p86_fld_titulo").val("");
	$("#p86_txtArea_Observaciones").val("");
	
	$("#p86_fld_ref").val("");
	$("#p86_fld_denom").val("");
	$("#p86_fld_stk").val("");
	$("#p86_fld_cant").val("");
	$("#p86_fld_band").val("");
	$("#p86_fld_bulto").val("");

	$("#p86_fld_cant").removeClass("p86_inputError");
	$("#p86_fld_band").removeClass("p86_inputError");
	$("#p86_fld_bulto").removeClass("p86_inputError");

	$("#p86_fld_titulo").removeClass("p86_inputError");
	$("#p86_fld_plataforma").removeClass("p86_inputError");
	$("#p86_fld_plataforma2").removeClass("p86_inputError");
	$("#p86_fld_cant").removeClass("p86_inputError");

	//Antes de cerrar el popup reseteamos el array de seleccionados
	seleccionadosP86 = new Array();

	//Reseteamos el valor del bulto anterior
	valorBultoAnterior = null;

	//Iniciar el grid y su cantidad de elementos a mostrar
	$(gridP86.nameJQuery).jqGrid('clearGridData');
	$(gridP86.nameJQuery).jqGrid('setGridParam', { rowNum: 10 });
	$("#p86_crearDevolucion .ui-pg-selbox").val(10);

	//Quitamos las flechas de ordenación del grid de la columna reordenada para que no se mantengan al abrid un nuevo grid.
	$(gridP86.nameJQuery + "_" + gridP86.getSortIndex() + " [sort='asc']").addClass("ui-state-disabled");
	$(gridP86.nameJQuery + "_" + gridP86.getSortIndex() + " [sort='desc']").addClass("ui-state-disabled");

	//Reseteamos la ordenación del grid
	$(gridP86.nameJQuery).jqGrid('setGridParam', {sortname:'', sortorder: 'asc'});

	//Reseteamos el valor del último bulto introducido
	ultimoBultoIntroducidoP86 = {};

	//Iniciar los arrays
	seleccionadosP86 = new Array();
	listadoModificadosP86 = new Array();
	modificadosP86 = new Array();

	//Limpiamos las plataformas
	$("#p86_fld_plataforma").val("");
	$("#p86_fld_plataforma2").val("");

	//Limpiamos la marcas y la seccion de referencias.
	$("#p86_seccionReferencias").val("");
	$("#p86_fld_marca").val("");
	$("#p86_cantidadMaximaFilaNueva").val("");
	
	//Limpiamos el combo.
	$("#p86_cmb_TipoDevol").val(" ");
	$("#p86_combo_tipo :input").removeClass("p86_inputError");
	$("#p86_cmb_TipoDevol").combobox("autocomplete"," ");
}

/************************ DESHABILITAR PRIMERA SECCIÓN DEL FORMULARIO ************************/
function disableTituloObservacionesRecogeAbona(){
	$("#p86_fld_titulo").attr('disabled','disabled');
	$("#p86_txtArea_Observaciones").attr('disabled','disabled');
	$("#p86_txtArea_Observaciones").addClass("p86_txtAreaDisabled");
	$("#p86_cmb_recoge").combobox("disable");
	$("#p86_cmb_abono").combobox("disable");
}

function enableTituloObservacionesRecogeAbona(){
	$("#p86_fld_titulo").removeAttr('disabled');
	$("#p86_txtArea_Observaciones").removeAttr('disabled');
	$("#p86_txtArea_Observaciones").removeClass("p86_txtAreaDisabled");
	$("#p86_cmb_recoge").combobox("enable");
	$("#p86_cmb_abono").combobox("enable");
}
/************************ GRID ************************/

//Define la estructura del grid
function GridP86CrearDevolucion(locale){
	// Atributos
	this.name = "gridP86CrearDevolucion"; 
	this.nameJQuery = "#gridP86CrearDevolucion"; 
	this.i18nJSON = './misumi/resources/p86PopUpCrearDevolucion/p86PopUpCrearDevolucion_' + locale + '.json';
	this.colNames = null; //Está en el json
	this.cm = [
	           {
	        	   "name"      : "marca",
	        	   "index"     : "marca",
	        	   "width"     : 100,
	        	   "align"     : "left",

	           },{
	        	   "name"      : "codArticulo",
	        	   "index"     : "codArticulo",
	        	   "width"     : 110,
	        	   "align"     : "left",
	        	   "sortable" : true
	           },{
	        	   "name"      : "denominacion",
	        	   "index"     : "denominacion", 
	        	   "width"     : 220,	
	        	   "align"     : "left"						
	           },{
	        	   "name"      : "stockActual",
	        	   "index"     : "stockActual",
	        	   "width"     : 120,
	        	   "fixed":true,
	        	   "formatter" :  stockFormatterP86,
	        	   "align"     : "left",

	           },{
	        	   "name"      : "stockDevuelto",
	        	   "index"     : "stockDevuelto",
	        	   "width"     : 130,
	        	   "editable"  : true,
	        	   "fixed" : true,
	        	   "formatter" :  stockFormatterP86,
	        	   "editoptions":{
	        		   "size": "12",
	        		   "maxlength": maxNumEnterosP86 + maxNumDecimalesP86 + 1,
	        		   "dataEvents": [
	        		                  {//control para el keydown
	        		                	  type: 'keydown',
	        		                	  fn: controlNavegacionP86,
	        		                  },
	        		                  {//control para el click
	        		                	  type: 'click',
	        		                	  fn: controlClickP86columnaStockDevueltoP86
	        		                  }
	        		                  ] 
	        	   },
	        	   "align"     : "left",

	           },{
	        	   "name"      : "bulto",
	        	   "index"     : "bulto",
	        	   "width"     : 60,
	        	   "editable"  : true,
	        	   "fixed" : true,
	        	   "editoptions":{
	        		   "size":"3",
	        		   "maxlength":"2",
	        		   "dataEvents": [
	        		                  {//control para el keydown
	        		                	  type: 'keydown',
	        		                	  fn: controlNavegacionP86,
	        		                  },// cierra el control del keydown
	        		                  {//control para el click
	        		                	  type: 'click',
	        		                	  fn: controlClickP86
	        		                  }
	        		                  ] 
	        	   },
	        	   "align"     : "left",

	           },{
	        	   "name" : "mensaje",
	        	   "index":"mensaje", 
	        	   "formatter": imageFormatMessageP86,
	        	   "fixed":true,
	        	   "width" : 50,
	        	   "sortable" : true
	           }
	           ]; 
	this.locale = locale;
	//this.sortIndex = "codigoReferencia";
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerGridp86";  
	this.pagerNameJQuery = "#pagerGridp86";
	this.title = null; //Está en el json
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null; //Está en el json
	this.rownumbers = true;
	this.myColumnsState = null;
	this.isColState = null;
	this.firstLoad = true;
	this.formatoFecha = "";

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

	//Como tenemos un multiselect, para que la última columna también
	//tenga el estilo de altura, se hace un +1.
	this.headerHeight = function headerHeight(cssClass) {
		var colModel = $(this.nameJQuery).jqGrid('getGridParam','colModel');
		for (i = 0; i <= this.colNames.length+1; i++){
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
		var colModel = jQuery(gridP86.nameJQuery).jqGrid('getGridParam', 'colModel'); 
		var i;
		var l = colModel.length;
		var colItem; 
		var cmName;
		var postData = jQuery(gridP86.nameJQuery).jqGrid('getGridParam', 'postData');
		var columnsState = {
				search: jQuery(gridP86.nameJQuery).jqGrid('getGridParam', 'search'),
				page: jQuery(gridP86.nameJQuery).jqGrid('getGridParam', 'page'),
				sortname: jQuery(gridP86.nameJQuery).jqGrid('getGridParam', 'sortname'),
				sortorder: jQuery(gridP86.nameJQuery).jqGrid('getGridParam', 'sortorder'),
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

//Función que sirve para especificar nombres de columnas, y qué ocurren en las paginaciones, ordenaciones etc.
function load_gridP86CrearDevolucionMock(gridP86) {

	$(gridP86.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		datatype : 'local',
		contentType : 'application/json',
		mtype : 'POST',
		colNames : gridP86.colNames,
		colModel : gridP86.cm,
		rowNum : 10,
		rowList : [ 10, 30, 50 ],
		height : "100%",
		autowidth : true,
		width : "100%",
		rownumbers : gridP86.rownumbers,
		pager : gridP86.pagerNameJQuery,
		viewrecords : true,
		caption : gridP86.title,
		altclass: "ui-priority-secondary",
		altRows: true, //false, para que el grid no muestre cebrado
		hidegrid : false, //false, para ocultar el boton que colapsa el grid.
		sortable : true,
		multiselect: true,
		index: gridP86.sortIndex,
		sortname: gridP86.sortIndex,
		sortorder: gridP86.sortOrder,
		emptyrecords : gridP86.emptyRecords,
		gridComplete : function() {
			//Añadimos estilo a la columna del multiselect para que se alinee con las
			//demás columnas
			$("#cb_gridP86CrearDevolucion").addClass("p86_multiselect");

			//Para dar altura a los títulos del grid y que se vean bien.
			gridP86.headerHeight("p86_gridHeader");		

			//Una vez cargado el grid, iniciamos el contador a 1.
			//Al ser una variable global y actualizarse cada vez
			//que se abre un nuevo grid en la función imageFormatMessage
			//obtiene valores no acordes con el número de fila cargado
			//por eso hay que iniciarlo a 1.
			numRowP86 = 1;
		},
		loadComplete : function(data) {
			gridP86.actualPage = data.page;
			gridP86.localData = data;
			gridP86.sortIndex = gridP86.sortIndex;
			gridP86.sortOrder = gridP86.sortOrder;	

			$("#p86_bloqueTabla .loading").css("display", "none");
		},
		resizeStop: function () {
			gridP86.saveColumnState.call($(this),gridP86.myColumnsState);
			$(gridP86.nameJQuery).jqGrid('setGridWidtreloadh', 895, false);
		},
		onPaging : function(postdata) {	
			//Antes de introducir este control, en el caso de estar en un campo editable stockDevuelto y clicar que pagine, se ejecutaba primero el evento focusout y más tarde este evento onPaging.
			//Esto creaba situaciones en las que al introducir un campo erroneo en stockDevuelto (con más de una coma, más de 15 enteros o más de 3 decimales), primero lanzaba el error de que el campo
			//se había introducido mal y más tarde paginaba. Para ello, como el focusout realiza una validación de que el campo editable es correcto o incorrecto y devuelve una variable llamada resultadoValidacion 'S' o 'N',
			//se ha transformado esa variable en global. De esta forma, al realizar el focusout se realiza la validación, en el caso de haber error lo muestra e indica al evento onPaging de que no tiene que realizar
			//la recarga de datos ni la paginación. En caso de que se introduzca un campo correcto en stockDevuelto, el focusout realiza una validación correcta y recalcula los datos del grid.

			//Puede darse la situación de que no se realice el focusout de y por consecuente la validación de stockDevuelto, porque no se haya editado ningún campo o porque el usuario no se haya situado en ninguno de esos campos. Por lo cual, la variable
			//resultadoValidacion se inicializa siempre en 'S'. Cuando se clica a paginar, pagine o no, resultadoValidacion obtiene el valor 'S', puesto que en el caso de haber editado un dato de forma erronea se autocorrige, y queremos que si el usuario
			//clica el elemento paginar al instante, pagine.
			if(resultadoValidacionP86 == 'S'){
				alreadySorted = false;
				gridP86.sortIndex = gridP86.sortIndex;
				gridP86.sortOrder = gridP86.sortOrder;
				gridP86.saveColumnState.call($(this), this.p.remapColumns);
				reloadData_gridP86CrearDevolucion();

				return 'stop';
			}else{
				//Se cambia el estado de la validación a ´S´, porque anque haya error, se corrige el dato y queremos que se pueda paginar.
				resultadoValidacionP86 = 'S';

				//En caso de existir un error, no se pagina y devuelve error. Pero el componente internamente pagina, por ese motivo, hay que restar 1 a la paginación. 
				$('#gridP86CrearDevolucion').setGridParam({page:gridP86.getActualPage()-1});

				return 'stop';			
			}
		},
		onSelectRow: function(id){

		},
		onSortCol : function (index, columnIndex, sortOrder){
			gridP86.sortIndex = index;
			gridP86.sortOrder = sortOrder;
			gridP86.saveColumnState.call($(this), this.p.remapColumns);
			reloadData_gridP86CrearDevolucion();
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

/************************ COMBOS ************************/
function load_cmbP86CrearDevolucion(){

	/*$("#p86_cmb_recoge").combobox(null);
	$("#p86_cmb_abono").combobox(null);

	var optionsRec = "";
	var optionsAbono = "";

	//Creamos el centro para obtener el negocio y así saber si el centro es de tipo super, hiper, etc.
	var codCentro = $("#centerId").val();
	var centro=new Centro(codCentro, null, null, null, null, null, null, null, null, null);
	var objJson = $.toJSON(centro);	

	$.ajax({
		url : './devoluciones/popupCrearDevolucion/cargaComboCrearDevolucion.do',
		type : 'POST',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			if(data != null){
				var negocio = data.negocio;
				if(negocio != null){
					//Si es un hiper
					if(negocio == 'H'){
						optionsRec = "<option value=' '>&nbsp;</option>";
						optionsRec = optionsRec + "<option value='1'>"+plataformaCmbP86+"</option>";
						optionsRec = optionsRec + "<option value='2'>"+proveedorCmbP86+"</option>";
						optionsAbono = "<option value='1'>"+plataformaCmbP86+"</option>";

						$("select#p86_cmb_recoge").html(optionsRec);
						$("select#p86_cmb_abono").html(optionsAbono);

						$("#p86_cmb_recoge").combobox('autocomplete','');
						$("#p86_cmb_abono").combobox('autocomplete',plataformaCmbP86);

						$("#p86_cmb_recoge").combobox('comboautocomplete',"null");
						$("#p86_cmb_abono").combobox('comboautocomplete',"null");
					}//Si es un super
					else if(negocio == 'S'){
						optionsRec = "<option value='1'>"+plataformaCmbP86+"</option>";
						optionsAbono = "<option value='1'>"+plataformaCmbP86+"</option>";

						$("select#p86_cmb_recoge").html(optionsRec);
						$("select#p86_cmb_abono").html(optionsAbono);

						$("#p86_cmb_recoge").combobox('autocomplete',plataformaCmbP86);
						$("#p86_cmb_abono").combobox('autocomplete',plataformaCmbP86);

						$("#p86_cmb_recoge").combobox('comboautocomplete',"null");
						$("#p86_cmb_abono").combobox('comboautocomplete',"null");
					}//Si es franquicia
					else{
						optionsRec = "<option value=''></option>";
						optionsRec = optionsRec + "<option value='1'>"+plataformaCmbP86+"</option>";

						optionsAbono = "<option value='1'>"+plataformaCmbP86+"</option>";

						$("select#p86_cmb_recoge").html(optionsRec);
						$("select#p86_cmb_abono").html(optionsAbono);

						//$("#p84_cmb_proveedor").combobox('autocomplete',"TODOS");
						$("#p86_cmb_recoge").combobox('comboautocomplete',"null");
						$("#p86_cmb_abono").combobox('comboautocomplete',"null");
					}
				}else{

				}
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});	*/

	//Iniciamos los combos
	$("#p86_cmb_recoge").combobox(null);
	$("#p86_cmb_abono").combobox(null);


	//Insertamos los posibles valores de los combos.
	var optionsRec = "";
	optionsRec = optionsRec + "<option value='1'>"+plataformaCmbP86+"</option>";
	optionsRec = optionsRec + "<option value='2'>"+proveedorCmbP86+"</option>";
	optionsRec = optionsRec + "<option value='3'>"+sinRecogidaCmbP86+"</option>";

	var optionsAbono = "";
	optionsAbono = "<option value='1'>"+plataformaCmbP86+"</option>";

	//Rellenamos los combos.
	$("select#p86_cmb_recoge").html(optionsRec);
	$("select#p86_cmb_abono").html(optionsAbono);

	$("#p86_cmb_recoge").combobox('autocomplete',plataformaCmbP86);
	$("#p86_cmb_abono").combobox('autocomplete',plataformaCmbP86);

}

function load_cmbP86TipoDevolucion(){
	$("#p86_cmb_TipoDevol").combobox(null); 

	//Creamos el centro para obtener el negocio y así saber si el centro es de tipo super, hiper, etc.
	var codCentro = $("#centerId").val();
	var centro=new Centro(codCentro, null, null, null, null, null, null, null, null, null);
	var objJson = $.toJSON(centro);	

	//Guarda las opciones del tipo.
	var optionsTipo = "<option value=' '>&nbsp;</option>";
	$.ajax({
		url : './devoluciones/popupCrearDevolucion/obtenerDatosCombo.do',
		type : 'POST',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			if(data != null){
				if(data.pCodError == 0){
					for(var i=0;i<data.devolucionTipoLst.length;i++){
						optionsTipo = optionsTipo + "<option value='" + data.devolucionTipoLst[i].codTpCa + "'>" + data.devolucionTipoLst[i].codTpCa + "-" + data.devolucionTipoLst[i].denominacion  + "</option>";
					}
					$("select#p86_cmb_TipoDevol").html(optionsTipo);
				}
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});	
	$("#p86_cmb_TipoDevol").combobox({
		selected: function(event, ui) {
			if ( ui.item.value != " ") {
				$("#p86_combo_tipo :input").removeClass("p86_inputError");
			}
		}  ,
	}); 
}

/************************ TITULOS COLUMNAS GRID ************************/
function p86SetHeadersTitles(data){
	var colModel = $(gridP86.nameJQuery).jqGrid("getGridParam", "colModel");
	$.each(colModel, function(i) {
		if (colModel[i].name!="rn"){
			$("#jqgh_gridP86CrearDevolucion_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
		}
	});
}

/************************ EVENTOS ************************/
//Función que aglutina todos los eventos.
function inicializarEventosP86(){
	//Eventos de input referencia
	events_p86_keyDown_referencia();
	events_p86_Click_referencia();

	//Eventos de input cantidad
	events_p86_keyUp_cantidad();
	events_p86_keyDown_cantidad();

	//Eventos de input bandeja
	events_p86_keyUp_bandeja();
	events_p86_keyDown_bandeja();

	//Eventos input bulto
	events_p86_keyUp_bulto();
	events_p86_keyDown_bulto();

	//Eventos botón añadir
	events_p86_button_add();

	//Eventos botón eliminar
	events_p86_button_delete();

	//Evento botón guardar
	events_p86_button_save();

	//Evento para quitar color rojo a título al pincharlo.
	events_p86_click_titulo();
}
//Evento cuando se pulsa enter en referencia
function events_p86_keyDown_referencia(){
	$("#p86_fld_ref").keydown(function(event) {
		if(event.which == 13) {
			//Al realizar una búsqueda en el buscador universal, limpiamos los input de denominación, stockActual, bulto y cantidad.
			$("#p86_fld_denom").val("");
			$("#p86_fld_stk").val("");
			$("#p86_fld_cant").val("");
			$("#p86_fld_band").val("");
			$("#p86_fld_bulto").val("");

			$("#p86_fld_cant").removeClass("p86_inputError");
			$("#p86_fld_band").removeClass("p86_inputError");
			$("#p86_fld_bulto").removeClass("p86_inputError");

			findReferenciaP86();
		}        
	});
}

function events_p86_keyUp_cantidad(){
	$("#p86_fld_cant").keyup(function(event) {
		validarCantidadP86();
	});
}

function events_p86_keyDown_cantidad(){
	$("#p86_fld_cant").keydown(function(event) {
		if(event.which == 13) {			
			if(validarCantidadEnterP86()){
				$("#p86_fld_cant").removeClass("p86_inputError");
				$("#p86_fld_band:enabled").add("#p86_fld_bulto").first().focus();

				if(valorBultoAnterior != null){
					$("#p86_fld_bulto").val(valorBultoAnterior);
					$("#p86_fld_bulto").addClass("p86_bultoAnterior");
				}else{
					$("#p86_fld_bulto").removeClass("p86_bultoAnterior");
				}				
			}else{
				$("#p86_fld_cant").addClass("p86_inputError");
			}
		}
	});
}

function events_p86_keyUp_bandeja(){
	$("#p86_fld_band").keyup(function(event) {
		validarBandejaP86();
	});
}

function events_p86_keyDown_bandeja(){
	$("#p86_fld_band").keydown(function(event) {
		if(event.which == 13) {			
			if(validarBandejaEnterP86()){
				$("#p86_fld_band").removeClass("p86_inputError");
				$("#p86_fld_bulto").focus();
			}else{
				$("#p86_fld_band").addClass("p86_inputError");
			}
		}
	});
}

function events_p86_keyUp_bulto(){
	$("#p86_fld_bulto").keyup(function(event) {
		validarBultoP86();

		//Si el bulto actual es igual al bulto anterior se pone en azul. Esto puede ocurrir porque se 
		//haya eliminado el bulto original, el usuario se haya arrepentido y haya metido de nuevo el mismo 
		//número. Colorearíamos de azul, para que se vea que el valor es el mismo que estaba.
		var valorBulto = $("#p86_fld_bulto").val();
		if(valorBulto == valorBultoAnterior){
			$("#p86_fld_bulto").addClass("p86_bultoAnterior");
		}else{
			$("#p86_fld_bulto").removeClass("p86_bultoAnterior");
		}

	});
}

function events_p86_keyDown_bulto(){
	$("#p86_fld_bulto").keydown(function(event) {
		if(event.which == 13) {
			validarAddFila();
		}
	});
}


function events_p86_Click_referencia(){
	$("#p86_fld_ref").click(function(event) {
		//Si no hay datos en el grid, borramos las plataformas, la marcas y la seccion
		if(jQuery(gridP86.nameJQuery).getGridParam("reccount") == 0){
			//Limpiamos las plataformas
			$("#p86_fld_plataforma").val("");
			$("#p86_fld_plataforma2").val("");

			$("#p86_seccionReferencias").val("");
			$("#p86_fld_marca").val("");
		}
		resetP86BloqueDos();    
	});
}

function events_p86_button_add(){
	$("#p86_btn_anadir").click(function(event){
		validarAddFila();
	});
}

function events_p86_button_delete(){
	$("#p86_btn_borrar").click(function(event){
		validarDeleteFila();
	});
}

function events_p86_button_save(){
	$("#p86_btn_guardar").click(function(event){
		validarGuardarDevolucion();
	});
}

function events_p86_click_titulo(){
	$("#p86_fld_titulo").click(function(event){
		$("#p86_fld_titulo").removeClass("p86_inputError");
	});
}
/************************ FUNCION QUE VALIDA Y AÑADE LA FILA ************************/
function validarAddFila(){
	if(checkTituloObservacionesRecogeAbona()){
		if(validarTipoDeDevolucion()){
			var falloValidacionCantBandBulto = false;
			if(!validarBultoEnterP86()){
				falloValidacionCantBandBulto = true;
				$("#p86_fld_bulto").addClass("p86_inputError");
			}
			if(!validarBandejaEnterP86()){
				falloValidacionCantBandBulto = true;
				$("#p86_fld_band").addClass("p86_inputError");
			}
			if(!validarCantidadEnterP86()){
				falloValidacionCantBandBulto = true;
				$("#p86_fld_cant").addClass("p86_inputError");
			}
			if(!falloValidacionCantBandBulto){
				validarCantidadMaximaPermitidaRef();
				if(validadoCantMax){
					$("#p86_fld_bulto").removeClass("p86_inputError");
					$("#p86_fld_cant").removeClass("p86_inputError");
					$("#p86_fld_band").removeClass("p86_inputError");				

					valorBultoAnterior = $("#p86_fld_bulto").val();
					addRowToGrid();		
					resetP86BloqueDos();
					
					//Limpiamos el combo.
					//$("#p86_cmb_TipoDevol").val(" ");
					//$("#p86_combo_tipo :input").removeClass("p86_inputError");
					//$("#p86_cmb_TipoDevol").combobox("autocomplete"," ");

					//Ponemos la cantidad máxima a vacío.
					$("#p86_cantidadMaximaFilaNueva").val();

					$("#p86_fld_ref").focus();
				}
			}
		}else{
			$("#p86_combo_tipo :input").addClass("p86_inputError");
		}
	}else{
		createAlert(replaceSpecialCharacters(formPartOneRequired), "ERROR");
	}
}

/************************ FUNCION QUE VALIDA Y ELIMINA LAS FILAS ************************/

function validarDeleteFila(){
	if(checkExistenFilasEnGrid()){
		if(checkNumeroFilasSeleccionadasMayorUno()){
			deleteRowFromGrid();		
		}else{
			createAlert(replaceSpecialCharacters(noSelectedRows), "ERROR");
		}
	}else{
		createAlert(replaceSpecialCharacters(emptyGrid), "ERROR");		
	}
}

/****** FUNCION QUE VALIDA SI EXISTEN LÍNEAS DE DEVOLUCIÓN Y GUARDA LA DEVOLUCIÓN *****/
function validarGuardarDevolucion(){
	if(checkTituloObservacionesRecogeAbona()){
		if(jQuery(gridP86.nameJQuery).getGridParam("reccount") != 0){
			actualizarDevolucionLineasP86();
		}else{
			createAlert(replaceSpecialCharacters(emptyGridSave), "ERROR");		
		}
	}else{
		createAlert(replaceSpecialCharacters(formPartOneRequiredSave), "ERROR");
	}
}

/************************ EXPRESIONES REGULARES Y VALIDACIONES ************************/

function regexForInputP86(){
	$("#p86_fld_cant").filter_input({regex:'[0-9,]'});
	$("#p86_fld_band").filter_input({regex:'[0-9]'});
	$("#p86_fld_bulto").filter_input({regex:'[0-9]'});
}

//Función que sirve para colorear la caja de cantidad si se introduce más de dos digitos
function validarCantidadP86(){
	//Conseguir numero introducido cantidad
	var numeroCantidad = $("#p86_fld_cant").val();

	var expresionRegular = new RegExp('^([0-9]+(\\,[0-9]{1,2})?)?$');

	var resultado = expresionRegular.test(numeroCantidad);
	if(resultado){
		$("#p86_fld_cant").removeClass("p86_inputError");
	}else{
		$("#p86_fld_cant").addClass("p86_inputError");
	}

	//Si tenemos una cantidad máxima, pintamos de rojo o no si se supera.
	var cantidadMaxima = $("#p86_cantidadMaximaFilaNueva").val();
	var cantidadInsertada = $("#p86_fld_cant").val();

	if(cantidadMaxima != ""){
		if(parseInt(cantidadInsertada) <= parseInt(cantidadMaxima)){
			//Quitamos el color rojo.
			$("#p86_fld_cant").removeClass("p86_inputError");
		}else{
			//Pintamos de color rojo la caja de cantidad.
			$("#p86_fld_cant").addClass("p86_inputError");
		}
	}
}

//Función que sirve para colorear la caja de bandeja si se introduce más de dos digitos
function validarBandejaP86(){
	//Conseguir numero
	var numeroBandeja = $("#p86_fld_band").val();
	var expresionRegular = new RegExp('^[0-9]*$');
	var resultado = expresionRegular.test(numeroBandeja);
	if(resultado){
		$("#p86_fld_band").removeClass("p86_inputError");
	}else{
		$("#p86_fld_band").addClass("p86_inputError");
	}
}

//Función que sirve para colorear la caja de bulto si se introduce un número decimal incorrectamente. Ej: con más de una coma, con más de dos decimales, con coma pero sin decimales, etc.
function validarBultoP86(){
	//Conseguir numero introducido cantidad
	var numeroBulto = $("#p86_fld_bulto").val();

	var expresionRegular = new RegExp('^([0-9]{0,2})$');

	var resultado = expresionRegular.test(numeroBulto);
	if(resultado){
		$("#p86_fld_bulto").removeClass("p86_inputError");
	}else{
		$("#p86_fld_bulto").addClass("p86_inputError");
	}
}

//Función que sirve para tabular a la caja bulto si el número introducido es correcto.
function validarCantidadEnterP86(){
	//Conseguir numero introducido cantidad
	var numeroCantidad = $("#p86_fld_cant").val();

	var expresionRegular = new RegExp('^[0-9]+(\\,[0-9]{1,2})?$');

	var resultado = expresionRegular.test(numeroCantidad);
	return resultado;
}

//Función que sirve para tabular a la caja bandeja si el número introducido es correcto.
function validarBandejaEnterP86(){
	//Conseguir numero introducido cantidad
	var resultado = true;
	if($("#p86_fld_band").is(':enabled')){
		var numeroCantidad = $("#p86_fld_band").val();
	
		var expresionRegular = new RegExp('^[0-9]+$');
	
		resultado = expresionRegular.test(numeroCantidad);
	}else{
		$("#p86_fld_band").val('');
	}
	return resultado;
}

//Función que sirve para tabular a la caja referencia si el número introducido es correcto.
function validarBultoEnterP86(){
	//Conseguir numero introducido cantidad
	var numeroBulto = $("#p86_fld_bulto").val();

	var expresionRegular = new RegExp('^([0-9]{1,2})$');

	var resultado = expresionRegular.test(numeroBulto);
	return resultado;
}

//Mira que se haya elegido un tipo de devolución. CodTcpa.
function validarTipoDeDevolucion(){
	return $("#p86_cmb_TipoDevol").val() == " "?false:true;
}

//Mira que la cantidad de una referencia no supere la permitida.
function validarCantidadMaximaPermitidaRef(){
	var cantidadMaxima = $("#p86_cantidadMaximaFilaNueva").val();

	//Si no se ha buscado la cantidad máxima permitida, se busca. Si ya se ha buscado,
	//significa que el usuario se ha pasado con la cantidad, por lo que cuando pulse guardar de nuevom,
	//tenemos que comparar la cantidad que ha insertado con la calculada.
	if(cantidadMaxima == ""){
		//Buscamos la cantidad máxima.
		var codCentro = $("#centerId").val();

		//Inicializamos la lista que contendrá la información de la línea de devolución
		var lstTDevLin = new Array();

		//Inicializamos y rellenamos la línea de devolución.
		var tDevLin = {}

		tDevLin.denominacion = $("#p86_fld_denom").val();
		tDevLin.stockDevuelto =  $("#p86_fld_cant").val();
		tDevLin.codArticulo = $("#p86_fld_ref").val();

		//Insertamos la línea de devolución en la lista
		lstTDevLin.push(tDevLin);

		var devolucion = new Devolucion(codCentro,null,null,null,null,null,null,null,null,
				null,null,null,null,null,null,null,null,null,null,lstTDevLin,null,null,null);

		var objJson = $.toJSON(devolucion);	
		$.ajax({
			url : './devoluciones/popupCrearDevolucion/obtenerCantidadADevolver.do',
			type : 'POST',
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			cache : false,
			async:false,
			success : function(data) {
				if(data!=null){
					if(data.length>0){
						//Guardamos el dato de la cantidad máxima
						var cantidadMaxima = data[0].cantidadMaximaPermitida;
						$("#p86_cantidadMaximaFilaNueva").val(cantidadMaxima);

						//Lo comparamos con la cantidad insertada.
						var cantidadInsertada = $("#p86_fld_cant").val();
						if(parseInt(cantidadInsertada) <= parseInt(cantidadMaxima)){
							validadoCantMax =  true;
						}else{
							//Mostramos error de cantidad
							createAlert(cantidadMaximaNoSuperable,"ERROR");

							//Cambiamos la cantidad insertada con la máxima
							$("#p86_fld_cant").val(cantidadMaxima);
							validadoCantMax =  false;
						}
					}
				}
			},
			error : function (xhr, status, error){
				handleError(xhr, status, error, locale);
			}			
		});	
	}else{
		var cantidadInsertada = $("#p86_fld_cant").val();
		if(parseInt(cantidadInsertada) <= parseInt(cantidadMaxima)){
			validadoCantMax =  true;
		}else{
			//Mostramos error de cantidad
			createAlert(cantidadMaximaNoSuperable,"ERROR");

			//Ponemos la cantidad insertada como la máxima
			$("#p86_fld_cant").val(cantidadMaxima);

			validadoCantMax =  false;
		}
	}
}
/************************ FUNCIONES BUSCADOR UNIVERSAL ************************/

function findReferenciaP86(){			
	var txtBusqueda=$("#p86_fld_ref").val();
	var messageVal=findReferenciaValidationP86(txtBusqueda);
	var alfaNumerico=null;
	var ean=null;

	if (messageVal==null){		
		//Si se trata de una busqueda alfanumerica o alfabetica
		alfaNumerico = !isInt(txtBusqueda);
		if (alfaNumerico){
			//se buscará como MODELO PROVEEDOR
			//En el caso que no eista MODELO PROVEEDOR si no hay registros como DENOMINACION
			ean = false;		
		}else{
			//Si la REFERENCIA mayor de 8 digitos se busca como EAN, 
			//Si no hay registros como EAN se buscará como MODELO PROVEEDOR
			//En el caso que no eista MODELO PROVEEDOR si no hay registros como DENOMINACION
			ean = txtBusqueda.length > 8;
		}
		reloadDataP34(txtBusqueda, "crearDevolucionP86","", alfaNumerico, ean);
		//Como hemos cambiado de referencia, la cantidad máxima se tiene que volver a calcular y ya no vale la de la referencia anterior.
		//Si no se pone esto, al insertar referencias nuevas, te compara la cantidad de la referencia nueva con alguna anterior buscada y 
		//no funciona correctamente.
		$("#p86_cantidadMaximaFilaNueva").val("");
	}else{		
		$("#p03_btn_aceptar").bind("click", function(e) {
			$("#p01_txt_infoRef").focus();
			$("#p01_txt_infoRef").select();
			e.stopPropagation();
		}); 
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");	
	}		
}

function findReferenciaValidationP86(txtBusqueda){		
	var messageVal=null;		

	if ($("#centerId").val()==null || $("#centerId").val()==""){		
		messageVal=centerRequired;		
	}			
	else if (txtBusqueda==null || txtBusqueda==""){		
		messageVal=referenceRequired;   		
	}		
	else if (!isInt(txtBusqueda) && txtBusqueda.length<4){		
		messageVal=referenceMinCaracters;		
	}					
	return messageVal;		
}

/************************ FUNCION QUE CHECKEA TITULO, OBSERVACIONES, ABONO Y RECOGE ************************/
function checkTituloObservacionesRecogeAbona(){
	var titulo = $("#p86_fld_titulo").val();
	var observaciones = $("#p86_txtArea_Observaciones").val();
	var plataforma1 = $("#p86_fld_plataforma").val();
	var plataforma2 = $("#p86_fld_plataforma2").val();

	if(titulo != "" && plataforma1 != "" && plataforma2 != ""){
		return true;
	}else{
		if(titulo == ""){
			$("#p86_fld_titulo").addClass("p86_inputError");
		}if(plataforma1 == ""){
			$("#p86_fld_plataforma").addClass("p86_inputError");
		}if(plataforma2 == ""){
			$("#p86_fld_plataforma2").addClass("p86_inputError");
		}
		return false;
	}
}

/************************ FUNCION QUE CHEQUEA EL NÚMERO DE FILAS SELECCIONADAS ************************/
function checkNumeroFilasSeleccionadasMayorUno(){
	var selRowIds = jQuery('#gridP86CrearDevolucion').jqGrid('getGridParam', 'selarrrow');
	var numeroFilas = selRowIds.length;

	//Si el número de filas seleccionado es mayor que 0 devuelve true.
	return (numeroFilas > 0);
}

/************************ FUNCION QUE CHEQUEA SI EXISTEN FILAS EN EL GRID ************************/
function checkExistenFilasEnGrid(){
	var numeroFilasGrid = jQuery("#gridP86CrearDevolucion").jqGrid('getGridParam', 'records');
	return numeroFilasGrid > 0;
}
/************************ FUNCION BUSQUEDA STOCKACTUAL Y DENOMINACION ************************/
function findStckActualYDenomP86(codArticulo,alfaNumericoBusqueda){
	//Ponemos el foco en el input de referencia, por si la búsqueda se ha realizado pulsando una imágen del buscador universal. 
	$("#p86_fld_ref").focus();

	var codCentro = $("#centerId").val();

	if(alfaNumericoBusqueda){
		$("#p86_fld_ref").val(codArticulo);
	}

	var referenciasCentro = new ReferenciasCentro(parseInt(codArticulo), codCentro, null,null,null,null,null);
	var objJson = $.toJSON(referenciasCentro.prepareToJsonObject());
	$.ajax({
		url : './devoluciones/popupCrearDevolucion/cargarStockActualYDenom.do',
		type : 'POST',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			if(data != null){
				if(data.diarioArt != null && data.valoresStock != null){
					if(data.diarioArt.descripArt != null && data.valoresStock.stock != null){
						//Miramos si las referencias pertenecen a la misma sección.
						var seccionReferencias = $("#p86_seccionReferencias").val();
						var seccionReferenciaBuscada = data.diarioArt.grupo2;

						//Si no existe sección de referencias y no hay datos en el grid.
						if(seccionReferencias == "" && jQuery(gridP86.nameJQuery).getGridParam("reccount") == 0){
							seccionReferencias = seccionReferenciaBuscada;
							$("#p86_seccionReferencias").val(seccionReferencias);
						}
						
						//Miramos que la seccion de referencia y de la nueva búsqueda coincidan.
						if(seccionReferencias == seccionReferenciaBuscada){
							var denominacion = data.diarioArt.descripArt;
							var stock = data.valoresStock.stock;

							//Insertamos los valores de cantidad y bulto en sus input
							$("#p86_fld_denom").val(denominacion);
							$("#p86_fld_stk").val(stock);

							//Desbloqueamos los campos de cantidad y bulto si están bloqueados
							$("#p86_fld_cant").removeAttr('disabled');
							$("#p86_fld_bulto").removeAttr('disabled');

							//Situamos el cursor en cantidad
							$("#p86_fld_cant").focus();

							if(data.valoresStock.stockPrincipal === stockPrincipalBandejasP86){
								$("#p86_fld_band").removeAttr('disabled');
							}

							//Miramos si el codigo de artículo es numérico o igual al original tras la búsqueda. Ejemplo. El usuario introduce referencia 1222 y se hace una búsqueda a la BD.
							//Mientras se realiza la búsqueda, introduce una letra (Ej. 1222a, 1222ç), la búsqueda se hará bien, pero la introducción de fila dará error porque
							//referencia es de tipo long y al meterle un elemento alfanumérico, no se parsea bien. Por lo cual, para evitar estos errores, se filtra de nuevo y se quitan
							//las letras u otros elementos que pueda haber. Además, es posible que el usuario ponga 1222, pulse enter y cambie el valor a 1088, quedandose el valor en 1088,
							//por lo que por si acaso, se corrige la referencia por la original. 
							$("#p86_fld_ref").val(codArticulo);
						}else{
							//Mostramos error y ponemos el foco en el campo de referencia.
							createAlert(noMismaSeccionP86,"ERROR")
							$("p86_fld_ref").focus();
						}
					}else{
						createAlert(replaceSpecialCharacters(emptyRecordReference), "ERROR");
					}
				}else{
					createAlert(replaceSpecialCharacters(emptyRecordReference), "ERROR");
				}
			}else{
				createAlert(replaceSpecialCharacters(emptyRecordReference), "ERROR");
			}
			$("#infoRef").show();
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});	

}

/************************ FUNCION BUSQUEDA PLATAFORMA DE LA REFERENCIA ************************/
function obtenerPlataformasDevolucionCreadaPorCentroP86(codArticulo){
	//Para poder introducir la cantidad y el bulto y calcular el stock, primero tenemos que mirar si
	//las plataformas de las referencias coinciden.
	var pasaValidacionPlataforma;

	//Si la referencia no existe en el grid, se buscan sus datos en el buscador universal.
	var existeRefEnGrid = checkIfRefExistsInGrid(codArticulo);
	if(existeRefEnGrid == 'N'){
		var codCentro = $("#centerId").val();

		var codRecoge = $("#p86_cmb_recoge").val();
		var codAbona = $("#p86_cmb_abono").val();

		//Buscamos la plataforma de abono y recogida de la referencia.
		var devolucionPlataforma = new DevolucionPlataforma(parseInt(codArticulo), codCentro, codAbona, codRecoge);
		var objJson = $.toJSON(devolucionPlataforma.prepareToJsonObject());

		$.ajax({
			url : './devoluciones/popupCrearDevolucion/obtenerPlataformasDevolucionCreadaPorCentro.do',
			type : 'POST',
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			async:false,
			cache : false,
			success : function(data) {	
				if(data != null){
					if(data.codError == 0){
						var plataformaRecoge = $("#p86_fld_plataforma").val();
						var plataformaAbona = $("#p86_fld_plataforma2").val();

						//Si no existen las plataformas y no se ha introducido ninguna fila, se asignan las de la primera referencia buscada.
						if(plataformaRecoge == "" && plataformaAbona == "" && jQuery(gridP86.nameJQuery).getGridParam("reccount") == 0){
							//Asignamos las plataformas
							$("#p86_fld_plataforma").val(data.codAbono + "-" + data.descripPlatAbono);
							$("#p86_fld_plataforma2").val(data.codRecogida + "-" + data.descripPlatRecogida);

							//Guardamos la marca en un hidden.
							$("#p86_fld_marca").val(data.marca);

							//Ponemos la validación en 'S'
							pasaValidacionPlataforma = "S";

							//Quitamos los posibles campos rojos de las plataformas por intentar guardad o añadir fila sin tener plataformas.
							$("#p86_fld_plataforma").removeClass("p86_inputError");
							$("#p86_fld_plataforma2").removeClass("p86_inputError");
						}else{
							//Si existe plataforma, comparamos la buscada con las existentes y tienen que coincidir.
							var plataformaRecogeNuevaRef = data.codAbono + "-" + data.descripPlatAbono;
							var plataformaAbonaNuevaRef = data.codRecogida + "-" + data.descripPlatRecogida;

							//Si son las mismas plataformas devuelve 'S'.
							if(plataformaRecoge == plataformaRecogeNuevaRef && plataformaAbona == plataformaAbonaNuevaRef){
								//Ponemos la validación en 'S'
								pasaValidacionPlataforma = "S";

								//Guardamos la marca en un hidden.
								$("#p86_fld_marca").val(data.marca);

								//Quitamos los posibles campos rojos de las plataformas por intentar guardad o añadir fila sin tener plataformas.
								$("#p86_fld_plataforma").removeClass("p86_inputError");
								$("#p86_fld_plataforma2").removeClass("p86_inputError");
							}else{
								createAlert(replaceSpecialCharacters(platDistintaP86), "ERROR");	

								//Ponemos la validación en 'N'
								pasaValidacionPlataforma = "N";
							}
						}											
					}else{
						createAlert(replaceSpecialCharacters(errorPlatP86), "ERROR");	

						//Ponemos la validación en 'N'
						pasaValidacionPlataforma = "N";
					}	
				}else{
					createAlert(replaceSpecialCharacters(errorPlatP86), "ERROR");	

					//Ponemos la validación en 'N'
					pasaValidacionPlataforma = "N";
				}
			},
			error : function (xhr, status, error){
				handleError(xhr, status, error, locale);
				pasaValidacionPlataforma = "N";
			}			
		});	
	}else{
		createAlert(replaceSpecialCharacters(refExists), "ERROR");	
		pasaValidacionPlataforma = "N";
	}	
	return pasaValidacionPlataforma;
}

/************************ FUNCION PARA AÑADIR DATOS AL GRID ************************/

function checkIfRefExistsInGrid(codArticulo){
	//Calculamos los datos de la fila
	var denominacion = $("#p86_fld_denom").val();
	var stockActual = $("#p86_fld_stk").val();
	var stockDevuelto = $("#p86_fld_cant").val();
	var bulto = $("#p86_fld_bulto").val();	
	var marca = $("#p86_fld_marca").val();

	//Creamos el objeto js y lo transformamos a json
	var devolucionLinea = new DevolucionLinea(codArticulo,denominacion,marca,null,null,null,null,null,null,null,null,null,null,null,stockActual,null,null,stockDevuelto,null,null,null,null,null,null,null,bulto,null,null,null,null);
	var objJson = $.toJSON(devolucionLinea);

	var existeRefEnGrid = "N";
	$.ajax({
		url : './devoluciones/popupCrearDevolucion/checkIfRefExistsInGrid.do',
		type : 'POST',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "text",
		cache : false,
		async: false,
		success : function(data) {	
			existeRefEnGrid = data;
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});	
	return existeRefEnGrid;
}

function addRowToGrid(){
	//Calculamos los datos de la fila
	var codArticulo = $("#p86_fld_ref").val();
	var denominacion = $("#p86_fld_denom").val();
	var stockActual = $("#p86_fld_stk").val();
	var stockDevuelto = $("#p86_fld_cant").val();
	var stockDevueltoSinComaYConPunto = stockDevuelto.replace(",",".");
	var bandejas = $("#p86_fld_band").val();
	var bulto = $("#p86_fld_bulto").val();	
	var marca = $("#p86_fld_marca").val();
	var tipo = $("#p86_cmb_TipoDevol").val() != " " ? $("#p86_cmb_TipoDevol").val() : null;
	var cantidadMaximaPermitida = $("#p86_cantidadMaximaFilaNueva").val();
	var flagBandejas = bandejas ? 'S' : null;

	//Creamos el objeto js y lo transformamos a json
	var devolucionLinea = new DevolucionLinea(codArticulo,denominacion,marca,null,null,null,null,null,null,null,null,null,null,null,stockActual,null,null,stockDevueltoSinComaYConPunto,null,null,null,null,null,null,null,bulto,null,null,null,null,null,tipo,cantidadMaximaPermitida,bandejas,flagBandejas);

	var objJson = $.toJSON(devolucionLinea);

	$.ajax({
		url : './devoluciones/popupCrearDevolucion/addRowToDataGrid.do',
		type : 'POST',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		async:false,
		success : function(data) {	
			if(data != null){
				if(data.rows != null && data.rows.length > 0){
					configurarGridP86(data);
					generateTooltipP86(data);

					//Al añadir la fila bloqueamos los comboboxes.
					$("#p86_cmb_recoge").combobox("disable");
					$("#p86_cmb_abono").combobox("disable");
				}else{

					//Rellenamos el grid vacío
					configurarGridP86(data);

					//Se pone esta fila porque si paginas y da error de paginación, el grid cambia el número de página internamente pero no externamente (la que se visualiza), y si luego
					//pagina bien, no va a la siguiente página, si no a la que tiene internamente. 
					var valorCajaGridP86 = $("#p86_popUp .ui-pg-input").val();
					$(gridP86.nameJQuery).setGridParam({page:valorCajaGridP86});
				}
			}else{
				createAlert(replaceSpecialCharacters(notPosibleToReturnDataP86), "ERROR");

				//Se pone esta fila porque si paginas y da error de paginación, el grid cambia el número de página internamente pero no externamente (la que se visualiza), y si luego
				//pagina bien, no va a la siguiente página, si no a la que tiene internamente. 
				var valorCajaGridP86 = $("#p86_popUp .ui-pg-input").val();
				$(gridP86.nameJQuery).setGridParam({page:valorCajaGridP86});
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});	
}

//Función que inserta eventos focusout, formateadores de expresiones regulares, ordenaciones de columnas, colores y datos al grid.
function configurarGridP86(data){

	//Se rellena el grid
	$(gridP86.nameJQuery)[0].addJSONData(data);
	gridP86.actualPage = data.page;
	gridP86.localData = data;

	//Por cada fila, formatea los campos editables y les coloca campos con focusout.
	var ids = jQuery(gridP86.nameJQuery).jqGrid('getDataIDs'), i, l = ids.length;
	for (var i = 0; i < l; i++) {
		jQuery(gridP86.nameJQuery).jqGrid('editRow', ids[i], false);

		////Ponemos que solo puedan insertarse valores enteros
		$("#"+ids[i]+"_bulto").filter_input({regex:'[0-9]'});		

		//Si se pierde el foco, se ejecutará la función que muestre el disquete, error, etc.
		$("#"+ids[i]+"_bulto").focusout(function() {
			validacionBultoP86(this.id, 'S');
		});

		//Ponemos que solo puedan insertarse valores decimales
		$("#"+ids[i]+"_stockDevuelto").filter_input({regex:'[0-9,]'});	

		//Si se pierde el foco del campo se ejecuta la función que se utilizará para mostrar
		//un error, el disquete,etc
		$("#"+ids[i]+"_stockDevuelto").focusout(function(e) {
			validacionStockDevueltoP86(this.id, 'S');			
		});

		$("#"+ids[i]+"_stockDevuelto").change(function() {
			controlChangecolumnaStockDevueltoP86(this.id);
		});

		$("#"+ids[i]+"_bulto").focus(function() {
			controlFocuscolumnaBultoP86(this.id);
		});

		$("#"+ids[i]+"_bulto").change(function() {
			controlChangecolumnaBultoP86(this.id);
		});

	}

	//Carga las ordenaciones de las columnas.
	cargarOrdenacionesColumnasP86();
}

function deleteRowFromGrid(){
	//Obtenemos los ids de las filas seleccionadas.
	var idsDeFilasSeleccionadasLst = jQuery('#gridP86CrearDevolucion').jqGrid('getGridParam', 'selarrrow');

	//Inicializamos la variable que guarda el id de la fila
	var idFila;

	//Inicializamos el objeto que guarda la información de la fila
	var rowObject;

	//Inicializamos la lista de  
	var filasSeleccionadasLst = new Array();

	for(var i=0;i<idsDeFilasSeleccionadasLst.length;i++){
		//Obtenemos el id de la fila.
		idFila = idsDeFilasSeleccionadasLst[i];

		//Obtenemos el objeto seleccionado.
		rowObject = jQuery('#gridP86CrearDevolucion').getRowData(idFila);

		//Creamos el objeto js
		var devolucionLinea = new DevolucionLinea();

		//Insertamos el código de artículo que nos servirá para eliminarlo de la tabla temporal
		devolucionLinea.codArticulo = rowObject.codArticulo;
		filasSeleccionadasLst.push(devolucionLinea);
	}

	//Creamos la devolución. Insertamos el número de la devolución (0 en las creadas por el centro) y la lista de elementos a eliminar.
	var devolucionAEliminar =  new Devolucion();
	devolucionAEliminar.devolucion = 0; 
	devolucionAEliminar.tDevLineasLst = filasSeleccionadasLst;

	//Creamos el objeto json.
	var objJson = $.toJSON(devolucionAEliminar);

	$.ajax({
		type : 'POST',			
		url : './devoluciones/popupCrearDevolucion/deleteDataGridRows.do',
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		data : objJson,
		success : function(data) {	
			if(data != null){
				if(data.rows != null && data.rows.length > 0){
					//Borramos los datos del grid. Esto se hace porque al insertar nuevos datos, aunque visualmente los checkboxes
					//estaban en no, internamente se mantenían como si al ocupar el espacio que ocupaban filas eliminadas.
					$(gridP86.nameJQuery).jqGrid('clearGridData');

					configurarGridP86(data);
					generateTooltipP86(data);

					//Deschequeamos el checkbox
					$("#cb_gridP86CrearDevolucion").attr('checked',false);
				}else{
					//Borramos los datos del grid. Esto se hace porque al insertar nuevos datos, aunque visualmente los checkboxes
					//estaban en no, internamente se mantenían como si al ocupar el espacio que ocupaban filas eliminadas.
					$(gridP86.nameJQuery).jqGrid('clearGridData');

					//Rellenamos el grid vacío
					configurarGridP86(data);

					//Deschequeamos el checkbox
					$("#cb_gridP86CrearDevolucion").attr('checked',false);

					//Desbloqueamos la primera parte del formulario.
					enableTituloObservacionesRecogeAbona();

					//Se pone esta fila porque si paginas y da error de paginación, el grid cambia el número de página internamente pero no externamente (la que se visualiza), y si luego
					//pagina bien, no va a la siguiente página, si no a la que tiene internamente. 
					var valorCajaGridP86 = $("#p86_popUp .ui-pg-input").val();
					$(gridP86.nameJQuery).setGridParam({page:valorCajaGridP86});

					//Borramos las plataformas, la seccion y la marca.
					//Limpiamos las plataformas
					$("#p86_fld_plataforma").val("");
					$("#p86_fld_plataforma2").val("");

					$("#p86_seccionReferencias").val("");
					$("#p86_fld_marca").val("");

					//Al eliminar todas las filas desbloqueamos los comboboxesm de abono y recoge.
					$("#p86_cmb_recoge").combobox("enable");
					$("#p86_cmb_abono").combobox("enable");
				}
			}else{
				createAlert(replaceSpecialCharacters(notPosibleToReturnDataP86), "ERROR");

				//Se pone esta fila porque si paginas y da error de paginación, el grid cambia el número de página internamente pero no externamente (la que se visualiza), y si luego
				//pagina bien, no va a la siguiente página, si no a la que tiene internamente. 
				var valorCajaGridP86 = $("##p86_popUp .ui-pg-input").val();
				$(gridP86.nameJQuery).setGridParam({page:valorCajaGridP86});
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});						
}
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/********************************************************** ORDENACIONES POR COLUMNA *********************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

function cargarOrdenacionesColumnasP86(){

	//Cuando pulse la columna para ordenar por icono tenemos que llamar manualmente al reload para que reordene,
	//ya que al tener el jsp campos editables no funciona de la manera convencional.
	$(gridP86.nameJQuery+"_codArticulo").unbind('click');
	$(gridP86.nameJQuery+"_codArticulo").bind("click", function(e) {
		//Antes de introducir esta validación, cuando se clicaba una reordenación de columna, hasta ahora ejecutaba primero este evento click y más tarde el focusout de la columna editable 
		//stockDevuelto (Cantidad a devolver centro). Esto creaba situaciones, en el que en caso de introducir un campo erroneo en stockDevuelto (con más de una coma,
		//más de 15 enteros o más de 3 decimales), no ejecutaba el focusout que se encarga de mostrar que ha habido un error de edición en ese campo y reordenaba directamente
		//la columna, siendo este comportamiento erroneo. Además creaba otro tipo de errores que se pueden ver en la aplicación como: 1. Editas un campo y tabulas, 2. Vuelves al
		//campo editado, 3. Introduces un error en el campo modificado, 4. Reordenas. Se quedaba la pantalla bloqueada!

		//Para realizar la validación cada vez que reordenamos, basta con introducir en este evento la validación del campo stockDevuelto y como id document.activeElement.id (Esto,
		//se encarga de devolver el id del foco actual). Como se ejecuta antes el evento click que el evento focusout, el foco estará en stockDevuelto y podremos realizar la validación correctamente.
		//En el caso de pasarla, se reordenará. En caso contrario devolverá error.

		//En el caso de que no estemos en ningún campo stockDevuelto y al clicar ordenar, se reordenará siempre- De esto se encarga la función validacionStockDevuelto, que controla que el id sea de tipo stockDevuelto,
		//en caso contrario, la reordenación se realiza siempre.
		if(validacionStockDevueltoP86(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionStockDevueltoP86(document.activeElement.id, 'S');

			actualizarSortOrderP86('codArticulo');

			$(gridP86.nameJQuery).setGridParam({sortname:'codArticulo'});
			$(gridP86.nameJQuery).setGridParam({page:1});
			reloadData_gridP86CrearDevolucion();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});

	$(gridP86.nameJQuery+"_denominacion").unbind('click');
	$(gridP86.nameJQuery+"_denominacion").bind("click", function(e) {
		if(validacionStockDevueltoP86(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionStockDevueltoP86(document.activeElement.id, 'S');

			actualizarSortOrderP86('denominacion');

			$(gridP86.nameJQuery).setGridParam({sortname:'denominacion'});
			$(gridP86.nameJQuery).setGridParam({page:1});
			reloadData_gridP86CrearDevolucion();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});

	$(gridP86.nameJQuery+"_marca").unbind('click');
	$(gridP86.nameJQuery+"_marca").bind("click", function(e) {
		if(validacionStockDevueltoP86(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionStockDevueltoP86(document.activeElement.id, 'S')

			actualizarSortOrderP86('marca');

			$(gridP86.nameJQuery).setGridParam({sortname:'marca'});
			$(gridP86.nameJQuery).setGridParam({page:1});
			reloadData_gridP86CrearDevolucion();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});

	$(gridP86.nameJQuery+"_stockActual").unbind('click');
	$(gridP86.nameJQuery+"_stockActual").bind("click", function(e) {
		if(validacionStockDevueltoP86(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionStockDevueltoP86(document.activeElement.id, 'S');

			actualizarSortOrderP86('stockActual');

			$(gridP86.nameJQuery).setGridParam({sortname:'stockActual'});
			$(gridP86.nameJQuery).setGridParam({page:1});
			reloadData_gridP86CrearDevolucion();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});

	$(gridP86.nameJQuery+"_stockDevuelto").unbind('click');
	$(gridP86.nameJQuery+"_stockDevuelto").bind("click", function(e) {
		if(validacionStockDevueltoP86(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionStockDevueltoP86(document.activeElement.id, 'S');

			actualizarSortOrderP86('stockDevuelto');

			$(gridP86.nameJQuery).setGridParam({sortname:'stockDevuelto'});
			$(gridP86.nameJQuery).setGridParam({page:1});
			reloadData_gridP86CrearDevolucion();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});

	$(gridP86.nameJQuery+"_bulto").unbind('click');
	$(gridP86.nameJQuery+"_bulto").bind("click", function(e) {
		if(validacionStockDevueltoP86(document.activeElement.id, 'N') == 'S'){
			//De esta forma si editamos correctamente y reordenamos, primero se guarda el dato en modificados y luego reordena.
			validacionStockDevueltoP86(document.activeElement.id, 'S');

			actualizarSortOrderP86('bulto');

			$(gridP86.nameJQuery).setGridParam({sortname:'bulto'});
			$(gridP86.nameJQuery).setGridParam({page:1});
			reloadData_gridP86CrearDevolucion();
		}else{
			$("#p82_btn_buscar").focus();
		}
	});	 	  
}

function actualizarSortOrderP86(columnaP86) {

	var ultimoSortNameP86 = $(gridP86.nameJQuery).jqGrid('getGridParam','sortname');
	var ultimoSortOrderP86 = $(gridP86.nameJQuery).jqGrid('getGridParam','sortorder');

	if (ultimoSortNameP86 != columnaP86 ) { //Se ha cambiado la columna por la que se quiere ordenar
		$(gridP86.nameJQuery).setGridParam({sortorder:'asc'});
		$(gridP86.nameJQuery + "_" + columnaP86 + " [sort='asc']").removeClass("ui-state-disabled");

		//En la columna anterior quitamos la flechas
		if (ultimoSortOrderP86=='asc'){
			$(gridP86.nameJQuery + "_" + ultimoSortNameP86 + " [sort='asc']").addClass("ui-state-disabled");
		}else{
			$(gridP86.nameJQuery + "_" + ultimoSortNameP86 + " [sort='desc']").addClass("ui-state-disabled");
		}
	} else { //Seguimos en la misma columna
		if (ultimoSortOrderP86 =='asc'){
			$(gridP86.nameJQuery).setGridParam({sortorder:'desc'});
			//Mostramos la flecha descendente y quitamos la flecha ascendente
			$(gridP86.nameJQuery + "_" + columnaP86 + " [sort='desc']").removeClass("ui-state-disabled");
			$(gridP86.nameJQuery + "_" + columnaP86 + " [sort='asc']").addClass("ui-state-disabled");
		} else {
			$(gridP86.nameJQuery).setGridParam({sortorder:'asc'});
			$(gridP86.nameJQuery + "_" + columnaP86 + " [sort='asc']").removeClass("ui-state-disabled");
			$(gridP86.nameJQuery + "_" + columnaP86 + " [sort='desc']").addClass("ui-state-disabled");
		}  		
	}
}

/************************ FUNCION PARA PAGINAR ************************/	
//Función que sirve para paginar o reordenar los elementos del grid.
function reloadData_gridP86CrearDevolucion() {

	//Obtenemos las filas modificadas
	obtenerListadoModificadosP86();

	var devolucion = $("#centerId").val();

	var devolucionEditada = new Devolucion(null,null,null,null,null,null,devolucion,null,null,
			null,null,null,null,null,null,null,null,null,null,null,listadoModificadosP86,null,null);

	//Borrar el listado de modificados
	listadoModificadosP86 = new Array();

	var objJson = $.toJSON(devolucionEditada);

	$.ajax({
		type : 'POST',			
		url : './devoluciones/popupCrearDevolucion/loadDataGridRecarga.do?page='+gridP86.getActualPage()+'&max='+gridP86.getRowNumPerPage()+'&index='+gridP86.getSortIndex()+'&sortorder='+gridP86.getSortOrder()+'&recarga=S',
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		data : objJson,
		success : function(data) {	
			if(data != null){
				if(data.rows != null && data.rows.length > 0){
					configurarGridP86(data);
					generateTooltipP86(data);
				}else{

					//Rellenamos el grid vacío
					configurarGridP86(data);

					//Se pone esta fila porque si paginas y da error de paginación, el grid cambia el número de página internamente pero no externamente (la que se visualiza), y si luego
					//pagina bien, no va a la siguiente página, si no a la que tiene internamente. 
					var valorCajaGridP86 = $("#p86_popUp .ui-pg-input").val();
					$(gridP86.nameJQuery).setGridParam({page:valorCajaGridP86});
				}
			}else{
				createAlert(replaceSpecialCharacters(notPosibleToReturnDataP86), "ERROR");

				//Se pone esta fila porque si paginas y da error de paginación, el grid cambia el número de página internamente pero no externamente (la que se visualiza), y si luego
				//pagina bien, no va a la siguiente página, si no a la que tiene internamente. 
				var valorCajaGridP86 = $("##p86_popUp .ui-pg-input").val();
				$(gridP86.nameJQuery).setGridParam({page:valorCajaGridP86});
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});		
}


//Función que actualiza las líneas de devolución de una devolución.
function actualizarDevolucionLineasP86(){
	//Obtenemos las filas modificadas
	obtenerListadoModificadosP86();		

	var centro = $("#centerId").val();
	var flagHistorico = null;
	var titulo1 = $("#p86_fld_titulo").val();
	var codArticulo = null;		
	var estadoCab = 1;
	var localizador = null;
	var codRma = $("#p86_fld_rma").val();
	//Las líneas de devolución creadas tienen valor de devolución 0.
	var devolucion  = 0;

	var fechaDesde = null;
	var fechaHasta = null;
	var flgRecogida = $("#p86_cmb_recoge").val() == 3 ? "N":"S";
	var abono = $("#p86_cmb_abono").val(); 
	var recogida = $("#p86_cmb_recoge").val(); 
	var codPlataforma = null; 
	var descripcion = $("#p86_txtArea_Observaciones").val();
	var motivo = 3;
	var titulo2 = null;
	var fechaPrecio = null;
	var codError = null;
	var descError = null;		
	var devLineas = null; 
	var devLineasModificadas = listadoModificadosP86;
	var proveedor = null;
	var flagRefPermanentes = null;


	var devolucionAGuardar = new Devolucion(centro,flagHistorico,titulo1,codArticulo,
			estadoCab,localizador,devolucion,fechaDesde,fechaHasta,flgRecogida,abono,
			recogida,codPlataforma,descripcion,motivo,titulo2,fechaPrecio,codError,descError,
			devLineas,devLineasModificadas,proveedor,flagRefPermanentes, codRma);
	

	var objJson = $.toJSON(devolucionAGuardar);

	$.ajax({
		type : 'POST',			
		url : './devoluciones/popupCrearDevolucion/saveDataGrid.do?page='+gridP86.getActualPage()+'&max='+gridP86.getRowNumPerPage()+'&index='+gridP86.getSortIndex()+'&sortorder='+gridP86.getSortOrder(),
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		data : objJson,
		success : function(data) {	
			if(data != null){
				if(data.codErrorPLSQL == 0){
					//Limpiamos el popup entero después del guardado.
					resetP86Dialog();

					//Eliminamos los datos de sesión del grid.
					removeGridP86();

					//Redibujamos las elipses y nubes.
					dibujarDevoluciones();				

					//Redibujamos el combo de las descripciones de las devoluciones.
					load_cmb_DescDevol();

					//Cerramos el dialogo.
					$("#p86_popup").dialog('close');

					//Mostramos mensaje de guardado.
					if(data.countGuardados == 0){
						if(data.countError == 1){										
							createAlert(replaceSpecialCharacters(data.countGuardados + lineasActualizadasP86 + data.countError + lineaErroneaP86), "ERROR");		
						}else{
							createAlert(replaceSpecialCharacters(data.countGuardados + lineasActualizadasP86 + data.countError + lineasErroneasP86), "ERROR");						
						}
					}else{
						if(data.countGuardados == 1){
							if(data.countError == 0){
								createAlert(guardadoCorrectamenteP86, "INFO");
							}else{
								if(data.countError == 1){											
									createAlert(replaceSpecialCharacters(data.countGuardados + lineaActualizadaP86 + data.countError + lineaErroneaP86), "INFO");
								}else{
									createAlert(replaceSpecialCharacters(data.countGuardados + lineaActualizadaP86 + data.countError + lineasErroneasP86), "INFO");
								}
							}
						}else{
							if(data.countError == 0){
								createAlert(guardadoCorrectamenteP86, "INFO");
							}else{
								if(data.countError == 1){											
									createAlert(replaceSpecialCharacters(data.countGuardados + lineasActualizadasP86 + data.countError + lineaErroneaP86), "INFO");
								}else{
									createAlert(replaceSpecialCharacters(data.countGuardados + lineasActualizadasP86 + data.countError + lineasErroneasP86), "INFO");
								}
							}
						}
					};
				}else{
					createAlert(replaceSpecialCharacters(data.descErrorPLSQL), "ERROR");
				}
			}else{
				createAlert(replaceSpecialCharacters(errorActualizacionP86), "ERROR");
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});		
}

function removeGridP86(){
	var borradoSesionGridCorrecto = false;
	$.ajax({
		url : './devoluciones/popupCrearDevolucion/eliminarTablaSesionGrid.do',
		type : 'POST',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		async:false,
		success : function(data) {	
			if(data != null){
				borradoSesionGridCorrecto =  data;
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});	
	return borradoSesionGridCorrecto;
}

//Mediante esta función se obtienen los cambios de las filas cambiadas en una lista. Luego esta lista se utiliza en el controlador
//para que al paginar que se guarden los estados de modificados, guardados, etc.
function obtenerListadoModificadosP86(){

	//A partir del array de seleccionados obtenemos el listado de campos modificados a enviar al controlador.
	var registroListadoModificadoP86 = {};
	var valorStockDevueltoP86 = "";
	var valorBultoP86 = "";
	var valorCodErrorP86 = "";
	var valorCodArticuloP86= "";
	for (i = 0; i < seleccionadosP86.length; i++){
		if (seleccionadosP86[i] != null && seleccionadosP86[i] != '')
		{
			//Por cada elemento seleccionado, tenemos que crear el listado de modificados.
			registroListadoModificadoP86 = {};

			//Se obtiene el valor de stock devuelto y de bulto
			valorStockDevueltoP86 = $("#"+ seleccionadosP86[i] + "_stockDevuelto").val() ? $("#"+ seleccionadosP86[i] + "_stockDevuelto").val().replace(',','.') : "";
			valorBultoP86 = $("#"+ seleccionadosP86[i] + "_bulto").val() ? $("#"+ seleccionadosP86[i] + "_bulto").val().replace(',','.') : "";

			//Se obtiene el codigo error para guardar el estado modificado, error o guardado en la tabla temporal
			//indice = $("#"+ seleccionados[i] + "_stockDevueltoBulto_indice").val();
			valorCodErrorP86 = $("#"+ seleccionadosP86[i] + "_stockDevueltoBulto_codError_orig").val() ? $("#"+ seleccionadosP86[i] + "_stockDevueltoBulto_codError_orig").val() : "";

			//Se obtiene el codigo de articulo para saber qué linea actualizar en la tabla temporal.
			valorCodArticuloP86 = jQuery(gridP86.nameJQuery).jqGrid('getCell',seleccionadosP86[i],'codArticulo');

			//Se rellena el objeto con el error, stock y bulto. Además se inserta el código de artículo para saber que linea de devolución
			//hay que actualizar.

			//registroListadoModificado.indice =  indice;
			registroListadoModificadoP86.codError = valorCodErrorP86;

			registroListadoModificadoP86.stockDevuelto =  valorStockDevueltoP86;
			registroListadoModificadoP86.bulto = valorBultoP86;

			registroListadoModificadoP86.codArticulo = valorCodArticuloP86;

			listadoModificadosP86.push(registroListadoModificadoP86)
		}	
	}

	//Reseteamos el array de los seleccionados.
	seleccionadosP86 = new Array();
}

/************************ FUNCION PARA VALIDAR EL GRID ************************/

function validacionBultoP86(idP86, mostrarAlertaP86){
	var resultadoValidacionP86 = 'S';

	if(mostrarAlertaP86 == "S"){

		//Se obtiene el valor introducido por el usuario
		var campoActualP86 = $("#"+idP86).val();

		//Se sustitullen las comas por los puntos
		var campoActualPuntoP86 = $("#"+idP86).val().replace(',','.');

		//Se obtiene la fila seleccionada
		var filaP86 = idP86.substring(0, idP86.indexOf("_"));

		//Como en principio no ha ocurrido ningún error, el error es 0
		var errorP86 = 0;
		var descErrorP86 = '';

		//Se obtienen las id de los input que contienen el campo original devuelto por el controlador, el actual y el temporal
		var campoBultoActualP86 = filaP86 + "_bulto";
		var campoBultoOrigP86 = filaP86 + "_bulto_orig";
		var campoBultoTmpP86 = filaP86 + "_bulto_tmp";

		//Campo que contiene el código de error equivalente a la imágen del disquete, error, etc.
		var campoErrorOrigP86 = filaP86 + "_stockDevueltoBulto_codError_orig";

		//Div que equivale a la columna Estado de la fila seleccionada.
		//Estos div contienen las imágenes del disquete, ficha y de aviso 
		var campoDivModificadoP86 = filaP86 + "_stockDevueltoBulto_divModificado";
		var campoDivGuardadoP86 = filaP86 + "_stockDevueltoBulto_divGuardado";

		//En este caso la validación devuelve siempre si, pero hay que mirar que haya modificación
		if(($("#"+campoBultoTmpP86).val() != null) && $("#"+campoBultoTmpP86).val() != campoActualP86){

			//En este caso se ha modificado el campo y hay que establecer el icono de modificacion
			$("#"+campoDivGuardadoP86).hide();
			$("#"+campoDivModificadoP86).show();
			$("#"+campoBultoTmpP86).val(campoActualP86);

			//El código de error de la fila será 9 que equivale a modificado
			$("#"+campoErrorOrigP86).val("9");

			//Añadimos la fila al array.
			addSeleccionadosP86(filaP86);

			//Insertamos el elemento de los array de modificados
			var valorP86 = $("#gridP86CrearDevolucion#"+filaP86+" [aria-describedby='gridP86CrearDevolucion_rn']").text();
			if(modificadosP86.indexOf(valorP86+"_bulto") === -1){ 
				modificadosP86.push(valorP86+"_bulto");
			}

		}
	}

	return resultadoValidacionP86;
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/********************************************************** FORMATEADORES GRID ***************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
function stockFormatterP86(cellValue, opts, rData) {
	if(cellValue == null || cellValue.toString() == ""){
		if(opts.colModel.name == "stockActual" || opts.colModel.name == "stockDevolver"){
			cellValue = "0";
			return $.formatNumber(cellValue,{format:"0.###",locale:"es"});
		}else{
			cellValue = "";
			return cellValue;
		}
	}else{
		return $.formatNumber(cellValue,{format:"0.###",locale:"es"});
	}
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************EVENTOS CAMPOS  DEL GRID ********************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

function controlNavegacionP86(e) {

	var idActualP86 = e.target.id;
	var idFocoP86;

	//Obtención de fila y columna actuales
	var filaP86 = idActualP86.substring(0, idActualP86.indexOf("_"));
	var nombreColumnaP86 = idActualP86.substring(idActualP86.indexOf("_")+1);
	var validacionNavegacionP86 = 'S';

	var key = e.which; //para soporte de todos los navegadores
	if (key == 13){//Tecla enter, guardado
		e.preventDefault();

		if(nombreColumnaP86 == "stockDevuelto"){
			validacionNavegacionP86 = validacionStockDevueltoP86(filaP86 + "_stockDevuelto", 'N');
			if (validacionNavegacionP86 =='S'){ 

				//Se llama a la función directamente, porque en esta función se calcula el elemento modificado, que indica si se ha modificado un
				//elemento del grid. En el caso de haberse modificado un elemento, se actualizan las líneas. Anteriormente, se hacía un focusout para
				//que saltara esta misma función, pero al ejecutar focusout, ejecutaba actualizarDevolucionLineas antes que validacionStockDevuelto se ejecutara, por lo que
				//no daba tiempo a calcular que el elemento se había modificado y no se actualizaba la línea.
				validacionStockDevueltoP86(this.id, 'S');

				//Guarda los datos modificados
				actualizarDevolucionLineasP86();
			}else{
				$("#p82_btn_buscar").focus();
			}
		}
		if(nombreColumnaP86 == "bulto"){
			validacionNavegacionP86 = validacionBultoP86(filaP86 + "_bulto", 'N');
			if (validacionNavegacionP86 =='S'){

				//Se llama a la función directamente, porque en esta función se calcula el elemento modificado, que indica si se ha modificado un
				//elemento del grid. En el caso de haberse modificado un elemento, se actualizan las líneas. Anteriormente, se hacía un focusout para
				//que saltara esta misma función, pero al ejecutar focusout, ejecutaba actualizarDevolucionLineas antes que validacionBulto se ejecutara, por lo que
				//no daba tiempo a calcular que el elemento se había modificado y no se actualizaba la línea.
				validacionBultoP86(this.id, 'S');

				//Guarda los datos modificados
				actualizarDevolucionLineasP86();
			}else{
				$("#p82_btn_buscar").focus();	        	
			}
		}
	}

	//Flechas de cursores para navegación	
	//Tecla izquierda
	if (key == 37){
		e.preventDefault();

		if(nombreColumnaP86 == "bulto"){
			idFocoP86 = filaP86 + "_stockDevuelto";
			validacionNavegacionP86 = validacionBultoP86(filaP86 + "_bulto", 'N');
			if (validacionNavegacionP86 =='S'){
				$("#"+idFocoP86).focus();
				$("#"+idFocoP86).select();
				$("#p86_fld_Bulto_Selecc").val(idFocoP86);
				controlClickP86columnaStockDevueltoP86(e,key);
			}else{
				$("#p82_btn_buscar").focus();	        	
			}
		}
	}

	//Tecla derecha
	if (key == 39){
		e.preventDefault();
		if(nombreColumnaP86 == "stockDevuelto"){
			idFocoP86 = filaP86 + "_bulto";
			validacionNavegacionP86 = validacionStockDevueltoP86(filaP86 + "_stockDevuelto", 'N');
			if (validacionNavegacionP86 =='S'){
				$("#"+idFocoP86).focus();
				$("#"+idFocoP86).select();
				$("#p86_fld_Bulto_Selecc").val(idFocoP86);
				controlClickP86(e,key);
			}else{
				$("#p82_btn_buscar").focus();
			}
		}    	  	
	}

	//Tecla arriba
	if (key == 38){
		e.preventDefault();
		idFocoP86 = (parseInt(filaP86,10)-1) + "_" + nombreColumnaP86;
		if(nombreColumnaP86 == "bulto"){
			validacionNavegacionP86 = validacionBultoP86(filaP86 + "_bulto", 'N');
		}
		if(nombreColumnaP86 == "stockDevuelto"){
			validacionNavegacionP86 = validacionStockDevueltoP86(filaP86 + "_stockDevuelto", 'N');
		}
		if (validacionNavegacionP86 =='S'){    		    			    		    		
			$("#"+idFocoP86).focus();
			$("#"+idFocoP86).select();
			$("#p86_fld_Bulto_Selecc").val(idFoco);  

			if(nombreColumnaP86 == "bulto"){
				controlClickP86(e,key);
			}

			if(nombreColumnaP86 == "stockDevuelto"){
				controlClickP86columnaStockDevueltoP86(e,key);
			}
		}else{
			$("#p82_btn_buscar").focus();   	
		}
	}

	//Tecla abajo
	if (key == 40){
		e.preventDefault();
		idFocoP86 = (parseInt(filaP86,10)+1) + "_" + nombreColumnaP86;

		if(nombreColumnaP86 == "bulto"){
			validacionNavegacionP86 = validacionBultoP86(filaP86 + "_bulto", 'N');
		}
		if(nombreColumnaP86 == "stockDevuelto"){
			validacionNavegacionP86 = validacionStockDevueltoP86(filaP86 + "_stockDevuelto", 'N');
		}

		if (validacionNavegacionP86 =='S'){    		    			
			$("#"+idFocoP86).focus();
			$("#"+idFocoP86).select();
			$("#p86_fld_Bulto_Selecc").val(idFocoP86);
			if(nombreColumnaP86 == "bulto"){
				controlClickP86(e,key);
			}

			if(nombreColumnaP86 == "stockDevuelto"){
				controlClickP86columnaStockDevueltoP86(e,key);
			}
		}else{
			$("#p82_btn_buscar").focus();
		}
	}

	//Tecla tabulacion
	if(key == 9){
		e.preventDefault();

		//Si se presiona tabulación más shift
		if(e.shiftKey){
			//Si estamos en stockDevuelto, volvemos a la fila anterior a la sección bulto. Si estamos en bulto, en la misma fila, volvemos a stockDevuelto.
			if(nombreColumnaP86 == "stockDevuelto"){
				idFocoP86 = (parseInt(filaP86,10)-1) + "_bulto";
			}else{
				idFocoP86 = filaP86 + "_stockDevuelto";
			}
		}else{
			//Si estamos en stockDevuelto, en la misma fila, vamos a bulto. Si estamos en bulto, vamos a la fila siguiente a stockDevuelto.
			if(nombreColumnaP86 == "stockDevuelto"){
				idFocoP86 = filaP86 + "_bulto";
			}else{
				idFocoP86 = (parseInt(filaP86,10) + 1) + "_stockDevuelto";
				controlClickP86columnaStockDevueltoP86(e,key);
			}								
		}

		if(nombreColumnaP86 == "stockDevuelto"){
			validacionNavegacionP86 = validacionStockDevueltoP86(filaP86 + "_stockDevuelto", 'N');
			if (validacionNavegacionP86 =='S'){ 
				$("#"+idFocoP86).focus();
				$("#"+idFocoP86).select();
				$("#p86_fld_Bulto_Selecc").val(idFocoP86);

				controlClickP86(e,key);
			}else{
				$("#p82_btn_buscar").focus();
			}
		}
		if(nombreColumnaP86 == "bulto"){
			validacionNavegacionP86 = validacionBultoP86(filaP86 + "_bulto", 'N');
			if (validacionNavegacionP86 =='S'){
				$("#"+idFocoP86).focus();
				$("#"+idFocoP86).select();
				$("#p86_fld_Bulto_Selecc").val(idFoco);

				controlClickP86columnaStockDevueltoP86(e,key);

			}else{
				$("#p82_btn_buscar").focus();	        	
			}
		}
	}
}

//En el caso de no tener valor la 1 fila de bulto pone un 0. En el caso de que el bulto de un elemento sea vacío pero el de su anterior
//no, se pone el valor del bulto anterior en ese elemento.
function controlClickP86(e,key){
	var idActualP86 = e.target.id;

	//Obtención de fila y columna actuales
	var filaP86 = idActualP86.substring(0, idActualP86.indexOf("_"));
	//Si se ha pulsado la flecha de arriba, la fila seleccionada será la actual - 1
	//Si se ha pulsado la flecha de abajo, la fila seleccionada será la actual + 1
	if(key == 38 || e.shiftKey){
		filaP86 = parseInt(filaP86) - 1;
	}else if(key == 40){
		filaP86 = parseInt(filaP86) + 1;
	}	

	var provrGenFilaP86 = "#"+filaP86+"_provrGenLin";
	var provrGenFilaValorP86 = $(provrGenFilaP86).val();

	var filaBultoActualP86 = "#"+filaP86+"_bulto";
	var filaStockDevueltoActualP86 = "#"+filaP86+"_stockDevuelto";
	var valorStockDevueltoActualP86 = $(filaStockDevueltoActualP86).val();

	if (valorIdActualP86 == null && valorStockDevueltoActualP86 != "" && valorStockDevueltoActualP86 != "0") {
		var valorIdActualP86 = $(filaBultoActualP86).val();
		if(valorIdActualP86 == "" || valorIdActualP86 == null || valorIdActualP86 == "null"){
			$(filaBultoActualP86).val(ultimoBultoIntroducidoP86[provrGenFilaValorP86]);
			$(filaBultoActualP86).select();
		}	
	}else if (valorStockDevueltoActualP86 == "0") {
		var valorIdActualP86 = $(filaBultoActualP86).val();
		$(filaBultoActualP86).val("0");
	}
}


function controlClickP86columnaStockDevueltoP86(e,key){
	var idActualP86 = e.target.id;

	//Obtención de fila y columna actuales
	var filaP86 = idActualP86.substring(0, idActualP86.indexOf("_"));

	var provrGenFilaP86 = "#"+filaP86+"_provrGenLin";
	var provrGenFilaValorP86 = $(provrGenFilaP86).val();

	var filaBultoActualP86 = "#"+filaP86+"_bulto";
	var filaStockDevueltoActualP86 = "#"+filaP86+"_stockDevuelto";
	var valorStockDevueltoActualP86 = $(filaStockDevueltoActualP86).val();

	if (valorStockDevueltoActualP86 != "" && valorStockDevueltoActualP86 != "0") {
		var valorIdActualP86 =  $(filaBultoActualP86).val();
		if(valorIdActualP86 == "" || valorIdActualP86 == null || valorIdActualP86 == "null"){
			//Se inserta en la fila actual el valor de la fila anterior
			$(filaBultoActualP86).val(ultimoBultoIntroducidoP86[provrGenFilaValorP86]);

			//Si se ha pulsado la flecha de arriba, la fila seleccionada será la actual - 1
			//Si se ha pulsado la flecha de abajo, la fila seleccionada será la actual + 1
			if(key == 38 || e.shiftKey){
				fila = parseInt(filaP86) - 1;
			}else if(key == 40 || key == 9){
				fila = parseInt(filaP86) + 1;
			}		

			var filaStockDevueltoFocoP86 = "#"+filaP86+"_stockDevuelto";
			$(filaStockDevueltoFocoP86).select();

		}	
	}
}

function controlChangecolumnaStockDevueltoP86(idActualP86){

	//Obtención de fila y columna actuales
	var filaP86 = idActualP86.substring(0, idActualP86.indexOf("_"));

	var provrGenFilaP86 = "#"+filaP86+"_provrGenLin";
	var provrGenFilaValorP86 = $(provrGenFilaP86).val();

	var filaBultoActualP86 = "#"+filaP86+"_bulto";
	var filaStockDevueltoActualP86 = "#"+filaP86+"_stockDevuelto";
	var valorStockDevueltoActualP86 = $(filaStockDevueltoActualP86).val();

	if (valorStockDevueltoActualP86 == "0"){
		$(filaBultoActualP86).val("0");
	}					
}

function controlFocuscolumnaBultoP86(idActualP86){

	//Obtención de fila y columna actuales
	var filaP86 = idActualP86.substring(0, idActualP86.indexOf("_"));

	var filaBultoActualP86 = "#"+filaP86+"_bulto";
	var filaStockDevueltoActualP86 = "#"+filaP86+"_stockDevuelto";
	var valorStockDevueltoActualP86 = $(filaStockDevueltoActualP86).val();

	var provrGenFilaP86 = "#"+filaP86+"_provrGenLin";
	var provrGenFilaValorP86 = $(provrGenFilaP86).val();

	if (valorStockDevueltoActualP86 != null && valorStockDevueltoActualP86 != "" && valorStockDevueltoActualP86 != "0") {
		var valorIdActualP86 =  $(filaBultoActualP86).val();
		if(valorIdActualP86 == "" || valorIdActualP86 == null || valorIdActualP86 == "null"){
			//Se inserta en la fila actual el valor de la fila anterior
			$(filaBultoActualP86).val(ultimoBultoIntroducidoP86[provrGenFilaValorP86]);
		}						
	}else if (valorStockDevueltoActualP86 == "0"){
		$(filaBultoActualP86).val("0");
	}	
}

function controlChangecolumnaBultoP86(idActualP86){

	//Obtención de fila y columna actuales
	var filaP86 = idActualP86.substring(0, idActualP86.indexOf("_"));
	var filaBultoActualP86 = "#"+filaP86+"_bulto";

	var provrGenFilaP86 = "#"+filaP86+"_provrGenLin";
	var provrGenFilaValorP86 = $(provrGenFilaP86).val();

	var valorIdActualP86 =  $(filaBultoActualP86).val();
	if(valorIdActualP86 != "" && valorIdActualP86 != null && valorIdActualP86 != "0" && valorIdActualP86 != "null"){
		//Se inserta en la fila actual el valor de la fila anterior
		ultimoBultoIntroducidoP86[provrGenFilaValorP86]=$("#"+idActualP86).val();
	}						
}

function imageFormatMessageP86(cellValue, opts, rData) {

	var imagenP86 = "";
	var mostrarModificadoP86 = "none;";
	var mostrarGuardadoP86 = "none;";
	var mostrarErrorP86 = "none;";
	var descErrorP86 = "";

	//Controlamos los posibles errores que me lleguen para pintar el icono correspondiente.
	if (parseFloat(rData['codError']) == '8')
	{
		//Pintamos el icono de que se ha guardado
		mostrarGuardadoP86 = "block;";
	}
	else if (parseFloat(rData['codError']) == '9')
	{
		//Pintamos el icono de que se ha modificado
		mostrarModificadoP86 = "block;";
	}
	/*else if (parseFloat(rData['codError']) == '3')
	{
		//Pintamos el icono de que ha ocurrido un error
		descError = replaceSpecialCharacters(errorActualizacionRegistro);
		mostrarError = "block;";
	}
	else if (parseFloat(rData['codError']) == '4')
	{
		//Pintamos el icono de que ha ocurrido un error
		descError = replaceSpecialCharacters(errorActualizacionOrig);
		mostrarError = "block;";
	}*/
	else if (null != rData['codError'] && parseFloat(rData['codError']) != '0'){
		descErrorP86 = rData['descError'];
		mostrarErrorP86 = "block;";
	}


	imagenP86 = "<div id='"+numRowP86+"_stockDevueltoBulto_divGuardado' align='center' style='display: "+ mostrarGuardadoP86 + "'><img id='"+numRowP86+"_stockDevueltoBulto_imgGuardado' src='./misumi/images/floppy.png' title='"+iconoGuardadoP86+"'/></div>"; //Guardado
	imagenP86 += "<div id='"+numRowP86+"_stockDevueltoBulto_divModificado' align='center' style='display: "+ mostrarModificadoP86 + "'><img id='"+numRowP86+"_stockDevueltoBulto_imgModificado' src='./misumi/images/modificado.png' title='"+iconoModificadoP86+"'/></div>"; //Modificado
	imagenP86 += "<div id='"+numRowP86+"_stockDevueltoBulto_divError' align='center' style='display: " + mostrarErrorP86 + "'><img id='"+numRowP86+"_stockDevueltoBulto_imgError' src='./misumi/images/dialog-error-24.png' title='"+descErrorP86+"'/></div>"; //Error		

	var stockOrigP86 = rData['stockDevueltoOrig'];
	var stockP86 = rData['stockDevuelto'];
	var datoStockDevueltoOrigP86;
	var datoStockDevueltoP86;

	if(stockOrigP86 != null){
		datoStockDevueltoOrigP86 = "<input type='hidden' id='"+numRowP86+"_stockDevuelto_orig' value='"+$.formatNumber(stockOrigP86,{format:"0.###",locale:"es"})+"'>";
	}else{
		datoStockDevueltoOrigP86 = "<input type='hidden' id='"+numRowP86+"_stockDevuelto_orig' value=''>";
	}

	if(stockP86 != null){
		datoStockDevueltoP86 = "<input type='hidden' id='"+numRowP86+"_stockDevuelto_tmp' value='"+$.formatNumber(stockP86,{format:"0.###",locale:"es"})+"'>";
	}else{		
		datoStockDevueltoP86 = "<input type='hidden' id='"+numRowP86+"_stockDevuelto_tmp' value=''>";
	}

	imagenP86 +=  datoStockDevueltoOrigP86;
	imagenP86 +=  datoStockDevueltoP86;

	var bultoOrigP86 = rData['bultoOrig'];
	var bultoP86 = rData['bulto'];
	var datoBultoOrigP86;
	var datoBultoP86;

	if(bultoOrigP86 != null){
		datoBultoOrigP86 = "<input type='hidden' id='"+numRowP86+"_bulto_orig' value='"+bultoOrigP86+"'>";
	}else{
		datoBultoOrigP86 = "<input type='hidden' id='"+numRowP86+"_bulto_orig' value=''>";
	}

	if(bultoP86 != null){
		datoBultoP86 = "<input type='hidden' id='"+numRowP86+"_bulto_tmp' value='"+bultoP86+"'>";
	}else{
		datoBultoP86 = "<input type='hidden' id='"+numRowP86+"_bulto_tmp' value=''>";
	}
	imagenP86 +=  datoBultoOrigP86;
	imagenP86 +=  datoBultoP86;

	//Añadimos también el valor del codError de cada registro.
	var datoErrorP86 = "<input type='hidden' id='"+numRowP86+"_stockDevueltoBulto_codError_orig' value='"+rData['codError']+"'>";
	imagenP86 +=  datoErrorP86;

	//Rellenamos un hidden que guardará el dato que contendrá el primer elemento de bulto de cada paginación en el caso de estar vacío. 
	if(numRowP86 == 1){
		var primerElementoBultoP86 = "<input type='hidden' id='primerElementoBulto' value='"+rData['primerElementoBulto']+"'>";	
		imagenP86 += primerElementoBultoP86;
	}
	//Rellenamos un hidden que guardará el dato que contendrá el primer elemento de stockDevuelto de cada paginación en el caso de estar vacío. 
	if(numRowP86 == 1){
		var primerElementoStockDevueltoP86 = "<input type='hidden' id='primerElementoStockDevuelto' value='"+rData['primerElementoStockDevuelto']+"'>";	
		imagenP86 += primerElementoStockDevueltoP86;
	}

	var estadoLineaP86 = "<input type='hidden' id='"+numRowP86+"_estadoLin' value='"+rData['estadoLin']+"'>";	
	imagenP86 += estadoLineaP86;

	var provrGenLineaP86 = "<input type='hidden' id='"+numRowP86+"_provrGenLin' value='"+rData['provrGen']+"'>";	
	imagenP86 += provrGenLineaP86;

	var cantidadMaximaLineaP86 = "<input type='hidden' id='"+numRowP86+"_cantidadMaximaPermitida' value='"+rData['cantidadMaximaPermitida']+"'>";
	imagenP86 +=  cantidadMaximaLineaP86;

	numRowP86++;

	return imagenP86;
}

//Función que sirve para que las fotos se muestren al pasar por encima de ciertas celdas de las columnas
function generateTooltipP86(data){
	var ids = jQuery(gridP86.nameJQuery).jqGrid('getDataIDs'), i, l = ids.length;	
	var colModel = $(gridP86.nameJQuery).jqGrid("getGridParam", "colModel");
	var rowData = null;
	for (i = 0; i < l; i++) {
		rowData = data.rows[i]
		//Si la referencia tiene foto
		if (rowData.flgFoto == "S"){
			var idArticulo = jQuery(gridP86.nameJQuery).jqGrid('getCell',ids[i],'codArticulo');

			var div = $("<div>");
			var idImg = "img_"+ids[i]+"_fotoTooltip"
			var img = $('<img id="'+idImg+'">'); //Equivalent: $(document.createElement('img'))
			var url = "./welcome/getImage.do?codArticulo="+idArticulo;//+"&flgControlCaprabo=S";
			img.attr('src',url);
			img.addClass("p83_img_tooltip");
			img.appendTo(div);
			img.attr("onerror", "redirigirLogin()");

			$.each(colModel, function(j) {
				var nombreColumna = colModel[j].name;
				var columna = $(gridP86.nameJQuery+" tbody #"+ ids[i]+ " td[aria-describedby='"+gridP86.name+"_"+nombreColumna+"']");
				columna.addClass("contieneTooltipFoto");
				columna.attr("title",div.html());
			});

			$(".contieneTooltipFoto").tooltip({
				position: {
					my: "left top",
					at: "right+5 bottom+5"
				}
			});
		}
	}
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************** VALIDACIONES CAMPOS EDITABLES DEL GRID ***************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

function validacionStockDevueltoP86(idP86, mostrarAlertaP86){
	//La validación antes de ningún tratamiento es satisfactoria, después puede no serlo
	resultadoValidacionP86 = 'S';

	//Este if se encarga de que se calcule la validación si venimos de un cmapo stockDevuelto. Se ha introducido para que se puedan realizar validaciones en ordenaciones de columnas
	//del método cargarOrdenacionesColumnasP84(). Hasta ahora, siempre que llegaba a esta función era por estar tratando el campo stockDevuelto. En las reordenaciones de columnas, puede
	//que se clique la columna viniendo de un campo editable de stockDevuelto o de cualquier otra parte de la pantalla. Por eso se ha introducido este control.
	if(idP86.indexOf("_stockDevuelto") != -1){
		//Se obtiene el valor introducido por el usuario
		var campoActualP86 = $("#"+idP86).val();

		//Se sustitullen las comas por los puntos
		var campoActualPuntoP86 = $("#"+idP86).val().replace(',','.');

		//Se obtiene la fila seleccionada
		var filaP86 = idP86.substring(0, idP86.indexOf("_"));

		//Como en principio no ha ocurrido ningún error, el error es 0
		var errorP86 = 0;
		var descErrorP86 = '';

		//Se obtienen las id de los input que contienen el campo original devuelto por el controlador, el actual y el temporal
		var campoStockDevueltoActualP86 = filaP86 + "_stockDevuelto";
		var campoStockDevueltoOrigP86 = filaP86 + "_stockDevuelto_orig";
		var campoStockDevueltoTmpP86 = filaP86 + "_stockDevuelto_tmp";

		var campoErrorOrigP86 = filaP86 + "_stockDevueltoBulto_codError_orig";

		//Div que equivale a la columna Estado de la fila seleccionada.
		//Estos div contienen las imágenes del disquete, ficha y de aviso 
		var campoDivModificadoP86 = filaP86 + "_stockDevueltoBulto_divModificado";
		var campoDivGuardadoP86 = filaP86 + "_stockDevueltoBulto_divGuardado";
		var campoDivErrorP86 = filaP86 + "_stockDevueltoBulto_divError";

		//Contiene el id de la imagen del div campoDivError. De esta forma, al posicional el ratón encima
		//sale la causa del error
		var campoImgErrorP86 = filaP86 + "_stockDevueltoBulto_imgError";

		//var isVisibleError = $("#"+campoDivError).is(':visible');
		//var isVisibleModificado = $("#"+campoDivModificado).is(':visible');

		//En este caso 
		if (mostrarAlertaP86 == 'S'){
			if(campoActualP86 != ""){
				//Si el número introducido es mayor que el número máximo permitido
				var cantidadMaximaPermitida = $("#"+filaP86+"_cantidadMaximaPermitida").val();
				if(parseInt(campoActualP86)> parseInt(cantidadMaximaPermitida)){
					descErrorP86 = replaceSpecialCharacters(cantidadMaximaNoSuperable);
					createAlert(descErrorP86, "ERROR");

					//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
					$("#p86_fld_StockDevuelto_Selecc").val(filaP86 + "_stockDevuelto");

					//Pintamos de rojo el campo.
					$("#"+idP86).removeClass("editable").addClass("editableError");

					//El error se cambia a 1 para realizar su tratamiento
					errorP86 = 1;
				}
				//Si hay más de una coma o el campo introducido es vacío mostrar el error
				else if (campoActualP86.split(",").length > 2){
					descErrorP86 = replaceSpecialCharacters(stockDevueltoIncorrectoP86);
					createAlert(descErrorP86, "ERROR");

					//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
					$("#p86_fld_StockDevuelto_Selecc").val(filaP86 + "_stockDevuelto");

					//Pintamos de rojo el campo.
					$("#"+idP86).removeClass("editable").addClass("editableError");

					//El error se cambia a 1 para realizar su tratamiento
					errorP86 = 1;
				}else{
					//Miramos si el número tiene más de 15 enteros o 3 decimales. Esos valores son constantes que pueden cambiarse
					var numerosEnterosP86 = campoActualP86.split(",")[0];
					var numerosDecimalesP86 = campoActualP86.split(",")[1];

					if(campoActualP86.indexOf(',') > -1){
						if(numerosEnterosP86.length > maxNumEnterosP86 || numerosDecimalesP86.length > maxNumDecimalesP86){
							descErrorP86 = replaceSpecialCharacters(stockDevueltoEnteroErroneoP86 +" "+ maxNumEnterosP86 + " " + stockDevueltoDecimalErroneoP86 +" "+ maxNumDecimalesP86 +" "+ stockDevueltoDecimalErroneo2P86);
							createAlert(descErrorP86, "ERROR");

							//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
							$("#p86_fld_StockDevuelto_Selecc").val(filaP86 + "_stockDevuelto");

							//Pintamos de rojo el campo.
							$("#"+idP86).removeClass("editable").addClass("editableError");

							//El error se cambia a 1 para realizar su tratamiento
							errorP86 = 1;
						}
					}else{
						if(numerosEnterosP86.length > maxNumEnterosP86){
							descErrorP86 = replaceSpecialCharacters(stockDevueltoEnteroErroneoP86 +" "+ maxNumEnterosP86 + " " + stockDevueltoDecimalErroneoP86 +" "+ maxNumDecimalesP86 +" "+ stockDevueltoDecimalErroneo2P86);
							createAlert(descErrorP86, "ERROR");

							//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
							$("#p86_fld_StockDevuelto_Selecc").val(filaP86 + "_stockDevuelto");

							//Pintamos de rojo el campo.
							$("#"+idP86).removeClass("editable").addClass("editableError");

							//El error se cambia a 1 para realizar su tratamiento
							errorP86 = 1;
						}
					}
				}	
				//Miramos que el dato original no sea ""
				var campoStockDevueltoOrigPuntoP86 = "";
				if($("#"+campoStockDevueltoOrigP86).val() != "" && $("#"+campoStockDevueltoOrigP86).val() != "null" && $("#"+campoStockDevueltoOrigP86).val() != null){
					campoStockDevueltoOrigPuntoP86 = $("#"+campoStockDevueltoOrigP86).val().replace(',','.');
				}

				if (errorP86 == 1){
					//En este caso ha ocurrido un error y hay que mostrar el icono de error.
					$("#"+campoDivGuardadoP86).hide();
					$("#"+campoDivModificadoP86).hide();
					$("#"+campoDivErrorP86).show();

					//Cambiamos el title
					$("#"+campoImgErrorP86).attr('title', descErrorP86);

					//Añadimos la fila al array.
					addSeleccionadosP86(filaP86);

					//Eliminamos el elemento de los array de modificados, al haber un error y recuperar sus datos por defecto.
					var valorP86 = $("#gridP86CrearDevolucion #"+filaP86+" [aria-describedby='gridP86CrearDevolucion_rn']").text();
					if(modificadosP86.indexOf(valorP86+"_stockDevuelto") != -1){ 
						var indexP86 = modificadosP86.indexOf(valorP86+"_stockDevuelto");
						modificadosP86.splice(index,1);
					}

					if(modificadosP86.indexOf(valorP86+"_bulto") != -1){ 
						var indexP86 = modificadosP86.indexOf(valor+"_bulto");
						modificadosP86.splice(indexP86,1);
					}

					//Si el campo original es distinto a "" devolvemos el número que nos llega del controlador.
					//Si no devolvemos "".
					if(campoStockDevueltoOrigPuntoP86 != ""){
						//Cargamos los valores anteriores a la modificación.
						$("#"+idP86).val(campoStockDevueltoOrigPuntoP86).formatNumber({format:"0.###",locale:"es"});
						$("#"+campoStockDevueltoTmpP86).val(campoStockDevueltoOrigPuntoP86).formatNumber({format:"0.###",locale:"es"});
					}else{
						$("#"+idP86).val("");
						$("#"+campoStockDevueltoTmpP86).val("");
					}
					//Inicializamos el bulto también
					var valorBultoOrigP86 = $("#"+filaP86+"_bulto_orig").val();
					$("#"+filaP86+"_bulto_tmp").val(valorBultoOrigP86);
					$("#"+filaP86+"_bulto").val(valorBultoOrigP86);


					//El código de error de la fila será 0 que equivale a un error
					$("#"+campoErrorOrigP86).val("0");

					//Sirve para el onPaging del grid.
					resultadoValidacionP86 = 'N';
				}else{
					//No hay error con lo que quitamos el posible icono de error.
					$("#"+campoDivErrorP86).hide();
					$("#"+idP86).removeClass("editableError").addClass("editable");

					var campoActualFormatterP86 = $.formatNumber(campoActualP86.replace(',','.'),{format:"0.###",locale:"es"});
					$("#"+campoStockDevueltoActualP86).val(campoActualFormatterP86);
					if (($("#"+campoStockDevueltoTmpP86).val() != null) && $("#"+campoStockDevueltoTmpP86).val() != campoActualFormatterP86){
						//if (parseFloat($("#"+campobultoOrig).val()) != parseFloat(campoActualPunto))
						//{
						//En este caso se ha modificado el campo y hay que establecer el icono de modificaci?n.
						$("#"+campoDivErrorP86).hide();
						$("#"+campoDivGuardadoP86).hide();
						$("#"+campoDivModificadoP86).show();

						//Es necesario que el campo editable coja formato decimal, si no se hace este paso,
						//al clicar guardar el número del campo editable pasa sin decimales (en el caso de haberlo
						//introducido sin decimales), y se guarda en el hidden temporal sin decimales, al editarlo
						//por el mismo campo pero con decimales, detecta que son dos números distintos y vuelve a entrar
						//sin necesidad (aunque la ejecución salga bien). Además al clicar guardar, queda mejor si el 
						//número coge formato decimal automáticamente pues es el formato que va a devolver después del guardado.

						//Si no hicieramos esto, por ejemplo el usuario introduciría un 3, el campo temporal cogería el valor 3.
						//Más tarde el usuario introduciría el campo 3,000 y detectaría que es distinto, ejecutando de nuevo el código
						//sin ser necesario. Además hasta recibir la respuesta del guardado, el campo no cogería formato decimal 
						//y queda extaño viendo que en las tabulaciones o al presionar enter si lo coge.
						$("#"+campoStockDevueltoTmpP86).val(campoActualFormatterP86);

						//El código de error de la fila será 9 que equivale a modificado
						$("#"+campoErrorOrigP86).val("9");

						//Añadimos la fila al array.
						addSeleccionadosP86(filaP86);

						//Insertamos el elemento de los array de modificados
						var valorP86 = $("#gridP86CrearDevolucion #"+filaP86+" [aria-describedby='gridP86CrearDevolucion_rn']").text();
						if(modificadosP86.indexOf(valorP86+"_stockDevuelto") === -1){ 							
							modificadosP86.push(valorP86+"_stockDevuelto");
						}
					}
				}
			}else{
				//Entra aquí en tres situaciones:
				// 1) El usuario ha pulsado las teclas de las flechas para moverse de campo sin haber introducido ningún valor previamente siendo este vacío
				// 2) Previamente, el usuario ha introducido un campo erroneo ha pulsado las flechas para moverse de campo, ha devuelto error y ha dejado el 
				//valor vacío por defecto devuelto por el controlador. Cuando el usuario vuelve a pulsar una flecha para moverse de campo, entra en esta situación.
				// 3) Existe un dato previo distinto a vacío y lo pones vacío

				//Se elimina el color rojo de error del 2 caso. En el 1 caso esta línea no afecta.
				$("#"+idP86).removeClass("editableError").addClass("editable");

				//Se oculta el mensaje de error del 2 caso. En el 1 caso esta línea no afecta, mi en el caso 3.
				$("#"+campoDivErrorP86).hide();

				//Si el campo previo es un número y ahora se ha cambiado a vacío, guardamos en la lista temporal
				//el campo para actualizar la tabla temporal, esto ocurre en el caso 3.
				if($("#"+campoStockDevueltoTmpP86).val() != campoActualP86){
					$("#"+campoDivErrorP86).hide();
					$("#"+campoDivGuardadoP86).hide();
					$("#"+campoDivModificadoP86).show();

					$("#"+campoStockDevueltoTmpP86).val(campoActualP86);

					addSeleccionadosP86(filaP86);
					//Insertamos el elemento de los array de modificados
					var valorP86 = $("#gridP86CrearDevolucion #"+filaP86+" [aria-describedby='gridP86CrearDevolucion_rn']").text();
					if(modificadosP86.indexOf(valorP86+"_stockDevuelto") === -1){ 							
						modificadosP86.push(valorP86+"_stockDevuelto");
					}

					$("#"+campoErrorOrigP86).val("9");
				}
			}
		}else{
			//Control de campo decimal erroneo
			if (campoActualP86.split(",").length > 2){
				resultadoValidacionP86 = 'N';
			}else{
				if(campoActualP86 != ""){
					var numerosEnterosP86 = campoActualP86.split(",")[0];
					var numerosDecimalesP86 = campoActualP86.split(",")[1];

					if(campoActualP86.indexOf(',') > -1){
						if(numerosEnterosP86.length > maxNumEnterosP86 || numerosDecimalesP86.length > maxNumDecimalesP86){
							resultadoValidacionP86 = 'N';
						}else{
							var campoActualPuntoP86 = $("#"+idP86).val().replace(',','.');
							$("#"+idP86).val(campoActualPuntoP86).formatNumber({format:"0.###",locale:"es"});
						}
					}else{
						if(numerosEnterosP86.length > maxNumEnterosP86){
							resultadoValidacionP86 = 'N';
						}else{
							var campoActualPuntoP86 = $("#"+idP86).val().replace(',','.');
							$("#"+idP86).val(campoActualPuntoP86).formatNumber({format:"0.###",locale:"es"});
						}
					}
				}
			}
		}
	}
	return resultadoValidacionP86;
}

function validacionBultoP86(idP86, mostrarAlertaP86){
	var resultadoValidacionP86 = 'S';

	if(mostrarAlertaP86 == "S"){

		//Se obtiene el valor introducido por el usuario
		var campoActualP86 = $("#"+idP86).val();

		//Se sustitullen las comas por los puntos
		var campoActualPuntoP86 = $("#"+idP86).val().replace(',','.');

		//Se obtiene la fila seleccionada
		var filaP86 = idP86.substring(0, idP86.indexOf("_"));

		//Como en principio no ha ocurrido ningún error, el error es 0
		var errorP86 = 0;
		var descErrorP86 = '';

		//Se obtienen las id de los input que contienen el campo original devuelto por el controlador, el actual y el temporal
		var campoBultoActualP86 = filaP86 + "_bulto";
		var campoBultoOrigP86 = filaP86 + "_bulto_orig";
		var campoBultoTmpP86 = filaP86 + "_bulto_tmp";

		//Campo que contiene el código de error equivalente a la imágen del disquete, error, etc.
		var campoErrorOrigP86 = filaP86 + "_stockDevueltoBulto_codError_orig";

		//Div que equivale a la columna Estado de la fila seleccionada.
		//Estos div contienen las imágenes del disquete, ficha y de aviso 
		var campoDivModificadoP86 = filaP86 + "_stockDevueltoBulto_divModificado";
		var campoDivGuardadoP86 = filaP86 + "_stockDevueltoBulto_divGuardado";

		//En este caso la validación devuelve siempre si, pero hay que mirar que haya modificación
		if(($("#"+campoBultoTmpP86).val() != null) && $("#"+campoBultoTmpP86).val() != campoActualP86){

			//En este caso se ha modificado el campo y hay que establecer el icono de modificacion
			$("#"+campoDivGuardadoP86).hide();
			$("#"+campoDivModificadoP86).show();
			$("#"+campoBultoTmpP86).val(campoActualP86);

			//El código de error de la fila será 9 que equivale a modificado
			$("#"+campoErrorOrigP86).val("9");

			//Añadimos la fila al array.
			addSeleccionadosP86(filaP86);

			//Insertamos el elemento de los array de modificados
			var valorP86 = $("#gridP86CrearDevolucion #"+filaP86+" [aria-describedby='gridP86CrearDevolucion_rn']").text();
			if(modificadosP86.indexOf(valorP86+"_bulto") === -1){ 
				modificadosP86.push(valorP86+"_bulto");
			}

		}
	}

	return resultadoValidacionP86;
}

//Esta lista solo contiene las filas modificadas de la página actual, de esta forma, guarda los registros modificados 
//en la temporal según se va reordenando el grid, paginando, etc.
function addSeleccionadosP86(filaP86){
	seleccionadosP86[filaP86] = filaP86;
}