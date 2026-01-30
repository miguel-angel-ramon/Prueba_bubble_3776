/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/**************************************************************** VARIABLES ******************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
var popUpTitleP77;
var rolTecnico = 1;
var rolCentro = 2;
var rolConsulta = 3;
var rolAdmin = 4;

//Títulos 
var p77mismoValorEurosFinales;
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************** DOCUMENTO LISTO **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

$(document).ready(function(){
	//Inicializa el popup que contiene el grid con el arbol
	initializeScreenP77DetalladoPedidoArbol();
});

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************ INICIALIZACIÓN DE ELEMENTOS E IDIOMA *******************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
function initializeScreenP77DetalladoPedidoArbol(){
	//Inicializamos el idioma.
	loadP77(locale);

	//Cargar dialogo p77
	//load_dialog_P77();	
}

//Función que define y carga el diálogo. 
function load_dialog_P77(){

	var permisos = $("#userPermision").val();
	if(permisos.indexOf('50_DETALLADO_GESTION_EUROS') > 0){	
			$("#p77_popUpDetalladoPedidoArbol").dialog({
				title: popUpTitleP77,
				autoOpen: false,
				height:'auto',
				//width:'630px',
				width:'800px',
				position: [550,28],
				modal: true,
				resizable: false,
				open: function() {
				},
				close: function( event, ui ) {
				}
			});
	}else{
		
		$("#p77_popUpDetalladoPedidoArbol").dialog({
			title: popUpTitleP77,
			autoOpen: false,
			height:'auto',
			width:'600px',
			position: [550,28],
			modal: true,
			resizable: false,
			open: function() {
			},
			close: function( event, ui ) {
			}
		});
		
	}
	
	
}

//Carga los elementos de idioma del json correspondiente.
function loadP77(locale){
	gridP77 = new GridP77(locale);

	var jqxhr = $.getJSON(gridP77.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		popUpTitleP77 = data.popUpTitleP77;
		gridP77.colNames = data.ColNames;
		gridP77.title = data.GridTitle;
		gridP77.emptyRecords= data.emptyRecords;
		gridP77.emptyRecordsSelecc= data.emptyRecordsSelecc;
		gridP77.expandirTodo = data.expandirTodo;
		gridP77.contraerTodo = data.contraerTodo;
		gridP77.marcaDiaHoy = data.marcaDiaHoy;
		gridP77.formatoFecha = data.formatoFecha;
		p77mismoValorEurosFinales = data.p77mismoValorEurosFinales;
		p77NoDatosFiltros = data.p77NoDatosFiltros;
		
		load_dialog_P77();	

		//Evento de botón calcular.
		events_p77_btn_calcular();
		events_p77_btn_PropuestaInicial();

	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

/*Clase de constantes para el TREE-GRID */
function GridP77 (locale){
	// Atributos
	this.name = "gridP77"; 
	this.nameJQuery = "#gridP77"; 
	this.i18nJSON = './misumi/resources/p77PopUpDetalladoPedidoArbol/p77PopUpDetalladoPedidoArbol_' + locale + '.json';
	this.colNames = null; //Esta en el json

	this.locale = locale;
	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = null; //"pagerP16Movimientos"; 
	this.pagerNameJQuery = null; //"#pagerP16Movimientos";
	this.title = null; //Esta en el json
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null; //Esta en el json
	this.rownumbers = false;
	this.expandirTodo = "";
	this.contraerTodo = "";
	this.marcaDiaHoy = "";
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

	this.headerHeight = function headerHeight(cssClass) {
		var colModel = $(this.nameJQuery).jqGrid('getGridParam','colModel');
		var colNames = $(this.nameJQuery).jqGrid('getGridParam','colNames');
		for (i = 0; i < colNames.length; i++){
			$("#gridP77").setLabel(colModel[i].name, colNames[i], cssClass);
		}

	}

	this.expandirContraerTodo = function expandirContraerTodo(btnName){
		var jqBtnName = "#" + btnName; 
		if ($(jqBtnName).val() == this.expandirTodo){
			$(".treeclick","table#gridP77").each(function() {
				if($(this).hasClass("tree-plus")) {
					$(this).trigger("click");
				}
			});	
			$(jqBtnName).val(this.contraerTodo);
		} else {
			$(".treeclick","table#gridP77").each(function() {
				if($(this).hasClass("tree-minus")) {
					$(this).trigger("click");
				}
			});
			$(jqBtnName).val(this.expandirTodo);
		}				
	}	
}


function loadData_gridP77(grid){
	var seccionArea = $("#p70_cmb_seccion").val();
	var area =  null;
	var seccion = null;
	var codMapa = null; 
	if(seccionArea != "null"){
		area = seccionArea.split("*")[0];
		seccion = seccionArea.split("*")[1];
	}
	if(typeof($('#p70_cmb_mapa').val()) != 'undefined'){
		codMapa=$('#p70_cmb_mapa').val();
	}
	var categoriaPartes = $("#p70_cmb_categoria").val();
	var categoria = null;
	if(categoriaPartes != null){
		categoria = categoriaPartes.split("*")[0];
	}

	$(gridP77.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		url : './detalladoPedido/arbol/loadAllData.do?centerId='+$("#centerId").val()+'&referencia='+$("#p70_fld_referencia").val()+'&referenciaATransformar=&area='+area+'&seccion='+seccion+'&categoria='+categoria+'&mapa='+codMapa,
		datatype : 'json',
		contentType : 'application/json',
		jsonReader : {
			root : "rows",
			page : "page",
			total : "total",
			records : "records",
			repeatitems : false
		},
		treeReader : {
			level_field: "level",
			parent_id_field: "parent", 
			leaf_field: "leaf",
			expanded_field: "expanded"
		},
		postData : {
			nodeid : "",
			parentid : "",
			n_level : 0   

		},
		serializeGridData: function (postData) {
			var jsonObject = null;
			jsonObject = {"nodeid": postData.nodeid,
					"parentid": postData.parentid,
					"n_level": postData.n_level
			}; 
			return JSON.stringify(jsonObject);
		},
		mtype : 'POST',

		treeGrid: true,
		treeGridModel: 'adjacency', //nested o adjacency
		ExpandColClick : true, //para permitir que se pueda expandir/colapsar pulsando en la columna
		ExpandColumn : 'fecha', 
		// See http://jquery.keicode.com/ui/icons.php
		//treeIcons: {plus:'ui-icon-triangle-1-e',minus:'ui-icon-triangle-1-s',leaf:'ui-icon-document'},
		treeIcons: {plus:'ui-icon-circle-plus',minus:'ui-icon-minusthick',leaf:'ui-icon-document'},	    
		colNames : gridP77.colNames, 
		colModel :[ 
		           {name:'descripcion', index:'descripcion', width:'350', resizable: false, sortable:false, formatter: descripcionFormatter}, 
		           {name:'precioCostoFinal', index:'precioCostoFinal', width:'100', resizable: false, sortable:false, formatter: precioCostoFinalFormatter},
		           {name:'cajasFinales', index:'cajasFinales', width:'100', resizable: false, sortable:false, formatter: cajasFinalFormatter},
		           {name:'precioCostoInicial', index:'precioCostoInicial', width:'100', resizable: false, sortable:false, formatter: precioCostoInicialFormatter},
		           {name:'cajasInicial', index:'cajasInicial', width:'100', resizable: false, sortable:false, formatter: cajasInicialFormatter}
		           ],
		           rownumbers : gridP77.rownumbers,
		           pager : gridP77.pagerNameJQuery,
		           rowNum: 200,
		           rowList:[],
		           sortable : false,
		           sortname: gridP77.sortIndex,
		           sortorder: gridP77.sortOrder,
		           viewrecords: true,
		           gridview: true,
		           caption: gridP77.title,
		           altclass: "ui-priority-secondary",
		           altRows: true, //false, para que el grid no muestre cebrado
		           hidegrid : false, //false, para ocultar el boton que colapsa el gridP77.
		           width: 'auto',
		           height: 'auto',
		           gridComplete : function() {			
		        	   gridP77.headerHeight("p77_gridMisPedidosHeader");     	

		        	   //$("td[aria-describedby=gridP77_fecha]").css("border-bottom-width", "0px"); //Para borrar el borde inferior de las celdas de la primera columna				
		        	   //$("td[aria-describedby=gridP77_fecha]").css("border-left-width", "0px"); //Para borrar el borde izquierdo de las celdas de la primera columna

		        	   $("div[class='tree-wrap tree-wrap-ltr']").css("padding-top", "10px"); //Para centrar los iconos de + y - de las celdas de la primera columna
		        	   //$("div[id=gbox_gridP77]").css("border", "0 solid #AAAAAA"); //Para borrar el borde exterior de toda la tabla

		        	   //Centramos los números en las celdas de euros iniciales y cajas iniciales
		        	   $("td[aria-describedby=gridP77_precioCostoInicial]").css("text-align", "center");
		        	   $("td[aria-describedby=gridP77_precioCostoFinal]").css("text-align", "center");
		        	   $("td[aria-describedby=gridP77_cajasInicial]").css("text-align", "center");
		        	   $("td[aria-describedby=gridP77_cajasFinales]").css("text-align", "center");

		        	   //Solo sera modificable si el rol es de centro o tecnico.
		        	   var rol = $("#userPerfil").val();
		        	   if(rol == rolConsulta){
		        		   //Centramos los números en las celdas de euros iniciales y cajas iniciales
		        		   $("td[aria-describedby=gridP77_precioCostoFinal]").css("text-align", "center");
		        		   $("td[aria-describedby=gridP77_cajasFinales]").css("text-align", "center");
		        		   
		        		 
		        	   }
		        	   //Se muestra el area de resultados.
		        	   $("#p77AreaResultados").attr("style", "display:block");		

		        	   //Ponemos expresiones regulares en los inputs
		        	   $(".p77_inputCostoFinal").filter_input({regex:'[0-9,]'});
		        	   $(".p77_inputCajasFinal").filter_input({regex:'[0-9]'});		        	  

		        	   //Ponemos el evento de keyup para que controle cuando bloquear y desbloquear inputs.
		        	   $(".p77_inputChange").unbind("keyup", funcKeyUp);
		        	   $(".p77_inputChange").bind("keyup",funcKeyUp);

		        	   bloquearInputsOtrosNivelesAlDesplegarArbol();
		        	   
		        	   var permisos = $("#userPermision").val();
		        	   if(permisos.indexOf('50_DETALLADO_GESTION_EUROS') <= 0){	
		        		   
		        		   //Quitamos los Euros y cajas iniciales	        		   
		        		   jQuery(gridP77.nameJQuery).jqGrid('hideCol', ["precioCostoInicial"]);
		        		   jQuery(gridP77.nameJQuery).jqGrid('hideCol', ["cajasInicial"]);
		        		   
		        		   //cambiamos los titulos de Euros y cajas finales a Euros y Cajas	     
		        		   $(gridP77.nameJQuery).jqGrid('setLabel', "precioCostoFinal","Euros");
		        		   $(gridP77.nameJQuery).jqGrid('setLabel', "cajasFinales","Cajas");
		        		   
		        	   }

		        	   $("#p77_popUpDetalladoPedidoArbol").dialog("open");		        	   
		           },
		           loadComplete: function (rowid) {
		        	   var ids = jQuery("#gridP77").jqGrid('getDataIDs');
		        	   if (ids == '')
		        	   {
		        		   createAlert(replaceSpecialCharacters(gridP77.emptyRecordsSelecc), "ERROR");
		        	   }		

		           },
		           onCellSelect: function (rowid, iCol, cellcontent, e){
		           }

	});
}

function reloadData_gridP77(grid){
	var seccionArea = $("#p70_cmb_seccion").val();
	var area =  null;
	var seccion = null;
	if(seccionArea != "null"){
		area = seccionArea.split("*")[0];
		seccion = seccionArea.split("*")[1];
	}

	var categoriaPartes = $("#p70_cmb_categoria").val();
	var categoria = null;
	if(categoriaPartes != null){
		categoria = categoriaPartes.split("*")[0];
	}

	$.ajax({
		type : 'POST',
		url : './detalladoPedido/arbol/loadAllData.do?centerId='+$("#centerId").val()+'&referencia='+$("#p70_fld_referencia").val()+'&referenciaATransformar=&area='+area+'&seccion='+seccion+'&categoria='+categoria,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		async:false,
		success : function(data) {	
			if(data != null){
				//Se rellena el grid
				$(gridP77.nameJQuery)[0].addJSONData(data);
			}
		},
		error : function (xhr, status, error){	
			handleError(xhr, status, error, locale);			
		}			
	});	
}
//Se formatean los campos de la columna fecha/area/sección/categoria
function descripcionFormatter(cellvalue, options, rowObject) {
	var formattedCell = "";

	//Se mira el nivel.
	// -> Si es el primer nivel se pone la fecha y el texto HOY, pues en este caso, la base de datos solo va a devolver datos para el dia actual. Con color naranja
	// - Si es el segundo, se pone color verde.
	// -> Si es el tercero, se pone color azul.
	// -> Si es el tercero, se pone color morado.

	
	if (rowObject.level == 0||rowObject.level == 9){
		if($("#p70_cmb_seccion").val() != "null"){
			formattedCell = "<p class=\"p77_gridDetalladoEurosHoy\">" + rowObject.fecha + "<span class=\"p77_gridDetalladoEurosHoyResaltado\">" + gridP77.marcaDiaHoy + "</span></p>";		
		}else{
			formattedCell = "<p class=\"p77_gridDetalladoEurosHoy\">" + cellvalue + "<span class=\"p77_gridDetalladoEurosHoyResaltado\">" + gridP77.marcaDiaHoy + "</span></p>";		
		}		
	}else if (rowObject.level == 1){		
		formattedCell = "<span class=\"p77_gridDetalladoEurosArea\">" + cellvalue + "</span>";
	}else if (rowObject.level == 2){
		formattedCell = "<span class=\"p77_gridDetalladoEurosSeccion\">" + cellvalue + "</span>";
	}else if (rowObject.level == 3){
		formattedCell = "<span class=\"p77_gridDetalladoEurosCategoria\">" + cellvalue + "</span>";
	}else if (rowObject.level == 4){

		formattedCell = "<span class=\"p77_gridDetalladoEurosSubcategoria\">" + cellvalue + "</span>";
	}else if (rowObject.level == 5){
		formattedCell = "<span class=\"p77_gridDetalladoEurosSegmento\">" + cellvalue + "</span>";
	}
	

	return formattedCell;
}

function precioCostoFinalFormatter(cellvalue, options, rowObject) {
	var formattedCell = "";

	//Solo sera modificable si el rol es de centro o tecnico.
	var rol = $("#userPerfil").val();
	var permisos = $("#userPermision").val();
	if((rol == rolTecnico || rol == rolAdmin || rol == rolCentro) && permisos.indexOf('50_DETALLADO_GESTION_EUROS') > 0){	
		var colorClase;
		var levelClase;
		var disabled = "";

		if(rowObject.estado == 0){
			colorClase = "p77_inputBlack ";			
		}else if(rowObject.estado == 1){
			colorClase = "p77_inputBlack ";
		}else if(rowObject.estado == 2){
			colorClase = "p77_inputGreen ";		
		}else if(rowObject.estado == 3){
			colorClase = "p77_inputPink ";
		}else if(rowObject.estado == 4){
			colorClase = "p77_inputRed p77_inputDisabled ";
			disabled = "disabled"
		}

		if(rowObject.level == 0){
			levelClase = "p77_hoy";
		}else if(rowObject.level == 1){
			levelClase =  "p77_area";
		}else if(rowObject.level == 2){
			levelClase = "p77_seccion";
		}else if(rowObject.level == 3){
			levelClase = "p77_categoria";
		}else if(rowObject.level == 4){
			levelClase = "p77_subcategoria";
		}else if(rowObject.level == 5){
			levelClase = "p77_segment";
		}

		formattedCell += " <input id='"+levelClase+"oo"+rowObject.grupo1+"o"+rowObject.grupo2+"o"+rowObject.grupo3+"o"+rowObject.grupo4+"o"+rowObject.grupo5+"' type='text' " + disabled + " class='input85 p77_input p77_inputCostoFinal p77_inputChange " + colorClase + levelClase + "' value='" + cellvalue + "'/> " +
		" <input id='"+levelClase+"oo"+rowObject.grupo1+"o"+rowObject.grupo2+"o"+rowObject.grupo3+"o"+rowObject.grupo4+"o"+rowObject.grupo5+"Hidden' type='hidden' value='" + cellvalue + "'/>";
	}else{
		if(rowObject.estado == 1){
			formattedCell += "<span class='p77_span'>"+cellvalue+"</span>";
		}else if(rowObject.estado == 2){
			formattedCell += "<span class='p77_span'>"+cellvalue+"</span>";
		}else if(rowObject.estado == 3){
			formattedCell += "<span class='p77_span'>"+cellvalue+"</span>";	
		}else if(rowObject.estado == 4){
			formattedCell += "<span class='p77_span'>"+cellvalue+"</span>";
		}		
	}

	return formattedCell;
}

function cajasFinalFormatter(cellvalue, options, rowObject) {
	var formattedCell = "";

	formattedCell += "<span class='p77_span'>"+cellvalue+"</span>"; 

	return formattedCell;
}

function precioCostoInicialFormatter(cellvalue, options, rowObject) {
	var formattedCell = "";

	formattedCell += "<span class='p77_span'>"+cellvalue+"</span>";

	return formattedCell;
}

function cajasInicialFormatter(cellvalue, options, rowObject) {
	var formattedCell = "";

	formattedCell += "<span class='p77_span'>"+cellvalue+"</span>";

	return formattedCell;
}

function resetResultadosP77 (){
	$('#gridP77').jqGrid('clearGridData');
	$("#gridP77").jqGrid('GridUnload');
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************* EVENTOS DE P77 ****************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
function events_p77_btn_calcular(){
	$("#p77_btn_calcular").click(function(){
		p77CalcularGestionEuros();
	});
}

function events_p77_btn_PropuestaInicial(){
	$("#p77_btn_propuestaInicial").click(function(){	
		
		p77PropuestaInicial();
		
	});
}

function p77CalcularGestionEuros(){
	var listaGestionEurosModificados = obtenerListaGestionEurosModificada();
	if(listaGestionEurosModificados.length > 0){
		var detallePedidoLista = new DetallePedidoLista(listaGestionEurosModificados);
		var objJson = $.toJSON(detallePedidoLista);	
		$.ajax({
			type : 'POST',
			url : './detalladoPedido/arbol/calcularGestionEuros.do',
			cache : false,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			data : objJson,
			success : function(data) {
				if(data != null){
					$("#p70_img_eurosCajas").trigger("click");
					//$("#p70_btn_buscar").trigger("click");
										
					//Borramos todas las modificaciones que se hayan podido hacer. Al propuesta inicial debemos volver a estado inicial, perdiendo todos los cambios que no se hayan guardado
					deleteSeleccionadosP75();
					deleteSeleccionadosP76();
					resetDatosP75();
					resetDatosP76();
					loadContadoresDetallado();
					
				}else{
					createAlert(replaceSpecialCharacters(p77NoDatosFiltros), "ERROR");
				}
			},
			error : function (xhr, status, error){
				handleError(xhr, status, error, locale);
			}			
		});	
	}else{
		createAlert(replaceSpecialCharacters(p77mismoValorEurosFinales), "ERROR");
	}
}


function p77PropuestaInicial(){
	var codLoc = $("#centerId").val();
	var respetarImc = $("#p70_chk_imc").is(":checked") ? "S":"N";
	var precioCostoFinal = $("#p70_inputEurosFinalesHidden").val();
	var precioCostoFinalFinal = $("#p70_inputEurosFinales").val();
	
	var rotacion = $("#p70_chk_altaRotacion").is(":checked") && $("#p70_chk_mediaRotacion").is(":checked") && $("#p70_chk_bajaRotacion").is(":checked") ? "AR*MR*BR":
		$("#p70_chk_altaRotacion").is(":checked") && $("#p70_chk_mediaRotacion").is(":checked") ? "AR*MR":
		$("#p70_chk_altaRotacion").is(":checked") && $("#p70_chk_bajaRotacion").is(":checked") ? "AR*BR":
		$("#p70_chk_mediaRotacion").is(":checked") && $("#p70_chk_bajaRotacion").is(":checked") ? "MR*BR":null;
	
	//Si flgOferta es null devuelve todos, si es S devuelve los que son de oferta y si es "N" los sin oferta.
	var flgOferta = $("#p70_chk_enOferta").is(":checked") && $("#p70_chk_sinOferta").is(":checked") ? null: ($("#p70_chk_enOferta").is(":checked") ? "S": ($("#p70_chk_sinOferta").is(":checked") ? "N":null));
	
	var seccion = '';
	var categoria = '';
	var codArticulo = '';
	if ($("input[name='p70_rad_tipoFiltro']:checked").val() == "1"){ //Se esta consultando por estructura
		if (null != seccionSel){
			seccion = seccionSel;
		} else {
			if($("#p70_cmb_seccion").val() != "null" && $("#p70_cmb_seccion").val() != null){
				var arrAreaSeccion = $("#p70_cmb_seccion").val().split("*");
				if ("" != arrAreaSeccion[0] && "null" != arrAreaSeccion[0]){
					area = arrAreaSeccion[0];
				}
				if ("" != arrAreaSeccion[1] && "null" != arrAreaSeccion[1]){
					seccion = arrAreaSeccion[1];
				}
			}
		}
		if (null != categoriaSel){
			categoria = categoriaSel;
		} else {
			if ($("#p70_cmb_categoria").val() != null && $("#p70_cmb_categoria").val() != "null") {
				var arrAreaCategoria = $("#p70_cmb_categoria").val().split("-");
				if ("" != arrAreaCategoria[0] && "null" != arrAreaCategoria[0]){
					categoria = arrAreaCategoria[0];
				}
			}
		}
	} else { //Se esta consultando por referencia
		codArticulo = $("#p70_fld_referencia").val();
	}
	
	var flgIncluirPropPed;
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
	
	var detalladoPedido = new DetalladoPedido(seccion, categoria.split("*")[0], codArticulo, null, null, codLoc, flgIncluirPropPed, flgOferta, respetarImc, precioCostoFinal, precioCostoFinalFinal,rotacion);	
	
	var objJson = $.toJSON(detalladoPedido);	
		$.ajax({
			type : 'POST',
			url : './detalladoPedido/propuestaInicial.do',
			cache : false,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			data : objJson,
			success : function(data) {
				if(data != null){
					if(data.codError != 0){
						createAlert(replaceSpecialCharacters(p70ErrorPropuestaInicial),"ERROR");
					}else{
						
						$("#p70_img_eurosCajas").trigger("click");
						//$("#p70_btn_buscar").trigger("click");
						//reloadGrid('N');
						//finder('N');
						
						//Borramos todas las modificaciones que se hayan podido hacer. Al propuesta inicial debemos volver a estado inicial, perdiendo todos los cambios que no se hayan guardado
						deleteSeleccionadosP75();
						deleteSeleccionadosP76();
						resetDatosP75();
						resetDatosP76();
						loadContadoresDetallado('N');
					}
				}else{
					createAlert(replaceSpecialCharacters(p70ErrorPropuestaInicial),"ERROR");
				}			
			},
			error : function (xhr, status, error){
				handleError(xhr, status, error, locale);
			}			
		});	

}




function obtenerListaGestionEurosModificada(){
	var listaGestionEurosModificados = new Array();

	var codLoc = $("#centerId").val();
	var respetarImc = $("#p70_chk_imc").is(":checked") ? "S":"N";
	var precioCostoFinal = $("#p70_inputEurosFinalesHidden").val();
	var precioCostoFinalFinal = $("#p70_inputEurosFinales").val();

	var rotacion = $("#p70_chk_altaRotacion").is(":checked") && $("#p70_chk_mediaRotacion").is(":checked") && $("#p70_chk_bajaRotacion").is(":checked") ? "AR*MR*BR":
		$("#p70_chk_altaRotacion").is(":checked") && $("#p70_chk_mediaRotacion").is(":checked") ? "AR*MR":
		$("#p70_chk_altaRotacion").is(":checked") && $("#p70_chk_bajaRotacion").is(":checked") ? "AR*BR":
		$("#p70_chk_mediaRotacion").is(":checked") && $("#p70_chk_bajaRotacion").is(":checked") ? "MR*BR":
		$("#p70_chk_altaRotacion").is(":checked") ? "AR":
		$("#p70_chk_mediaRotacion").is(":checked") ? "MR":
		$("#p70_chk_bajaRotacion").is(":checked") ? "BR":null;

	//Si flgOferta es null devuelve todos, si es S devuelve los que son de oferta y si es "N" los sin oferta.
	var flgOferta = $("#p70_chk_enOferta").is(":checked") && $("#p70_chk_sinOferta").is(":checked") ? null: ($("#p70_chk_enOferta").is(":checked") ? "S": ($("#p70_chk_sinOferta").is(":checked") ? "N":null));

	var flgIncluirPropPed;
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


	//Rellenamos una lista de detallado
	var listaModificables = $(".p77_inputChange");

	for(var i = 0; i<listaModificables.length;i++){
		var id = listaModificables[i].id;

		var precioCostoFinalFinal = $("#"+id).val();
		var precioCostoFinal = $("#"+id+"Hidden").val();

		if(precioCostoFinal != precioCostoFinalFinal){
			var idAreaSeccionCategoria = id.split("oo")
			var areaSeccionCategoria = idAreaSeccionCategoria[1];
			var areaSeccionCategoriaPartes = areaSeccionCategoria.split("o");
			var area = areaSeccionCategoriaPartes[0] != 0 ? areaSeccionCategoriaPartes[0]:null;
			var seccion = areaSeccionCategoriaPartes[1] != 0 ? areaSeccionCategoriaPartes[1]:null;
			var categoria = areaSeccionCategoriaPartes[2] != 0 ? areaSeccionCategoriaPartes[2]:null;
			var subcategoria = areaSeccionCategoriaPartes[3] != 0 ? areaSeccionCategoriaPartes[3]:null;
			var segmento = areaSeccionCategoriaPartes[4] != 0 ? areaSeccionCategoriaPartes[4]:null;

			var detalladoPedido = new DetalladoPedido(seccion, categoria, null, null, null, codLoc, flgIncluirPropPed, flgOferta, respetarImc, precioCostoFinal, precioCostoFinalFinal,rotacion,area,subcategoria,segmento);				
			listaGestionEurosModificados.push(detalladoPedido);
		}		
	}
	return listaGestionEurosModificados;
}

var funcKeyUp = function (e) {	
	var tecla = e.which;

	if((tecla > 47 && tecla < 58) || (tecla > 95 && tecla < 106) || tecla == 8 || tecla == 46){		
		var clase = this.id.split("oo")[0].trim();
		if(bloquearInputsOtrosNiveles(this)){
			if(clase == "p77_hoy"){
				//Deshabilitamos campos inferiores que cuelgan de el.
				$(".p77_area").prop('disabled', true);
				$(".p77_seccion").prop('disabled', true);
				$(".p77_categoria").prop('disabled', true);	
				$(".p77_subcategoria").prop('disabled', true);	
				$(".p77_segment").prop('disabled', true);	

				$(".p77_area").addClass("p77_inputDisabled");
				$(".p77_seccion").addClass("p77_inputDisabled");
				$(".p77_categoria").addClass("p77_inputDisabled");
				$(".p77_subcategoria").addClass("p77_inputDisabled");
				$(".p77_segment").addClass("p77_inputDisabled");
				
			}else if(clase == "p77_area"){
				//Deshabilitamos campo superiores
				$(".p77_hoy").prop('disabled', true);
				$(".p77_hoy").addClass("p77_inputDisabled");

				//Obtenemos el idArea
				var idArea = this.id.split("oo")[1].split("o")[0];

				//Bloqueo todas las secciones que cuelguen de ese area.
				var secciones = $(".p77_seccion");				
				for(var i = 0;i<secciones.length;i++){
					var areaSeccion = secciones[i].id.split("oo")[1].split("o")[0];

					if(areaSeccion == idArea){
						$("#"+secciones[i].id).prop('disabled', true);
						$("#"+secciones[i].id).addClass("p77_inputDisabled");
					}
				}

				//Bloqueo todas las categorias que cuelguen de ese area.
				var categorias = $(".p77_categoria");				
				for(var i = 0;i<categorias.length;i++){
					var areaCategoria = categorias[i].id.split("oo")[1].split("o")[0];

					if(areaCategoria == idArea){
						$("#"+categorias[i].id).prop('disabled', true);
						$("#"+categorias[i].id).addClass("p77_inputDisabled");
					}
				}				
				
				//Bloqueo todas las subcategorias que cuelguen de ese area.
				var subcategorias = $(".p77_subcategoria");				
				for(var i = 0;i<subcategorias.length;i++){
					var areaSubcategoria = subcategorias[i].id.split("oo")[1].split("o")[0];

					if(areaSubcategoria == idArea){
						$("#"+subcategorias[i].id).prop('disabled', true);
						$("#"+subcategorias[i].id).addClass("p77_inputDisabled");
					}
				}	
				
				//Bloqueo todas los segmento que cuelguen de ese area.
				var segmentos = $(".p77_segment");				
				for(var i = 0;i<segmentos.length;i++){
					var areaSegmento = segmentos[i].id.split("oo")[1].split("o")[0];

					if(areaSegmento == idArea){
						$("#"+segmentos[i].id).prop('disabled', true);
						$("#"+segmentos[i].id).addClass("p77_inputDisabled");
					}
				}
				
			}else if(clase == "p77_seccion"){
				//Deshabilitamos campo superiores
				$(".p77_hoy").prop('disabled', true);
				$(".p77_hoy").addClass("p77_inputDisabled");

				//Obtenemos el idArea
				var idArea = this.id.split("oo")[1].split("o")[0];

				//Obtenemos el idSeccion
				var idSeccion = this.id.split("oo")[1].split("o")[1];

				//Bloqueo el area superior de la seccion.
				var areas = $(".p77_area");				
				for(var i = 0;i<areas.length;i++){
					var areaAreas = areas[i].id.split("oo")[1].split("o")[0];

					if(areaAreas == idArea){
						$("#"+areas[i].id).prop('disabled', true);
						$("#"+areas[i].id).addClass("p77_inputDisabled");
					}
				}

				//Bloqueo todas las categorias que cuelguen de esa seccion y area.
				var categorias = $(".p77_categoria");				
				for(var i = 0;i<categorias.length;i++){
					var areaCategoria = categorias[i].id.split("oo")[1].split("o")[0];
					var seccionCategoria = categorias[i].id.split("oo")[1].split("o")[1];

					if(areaCategoria == idArea && seccionCategoria == idSeccion){
						$("#"+categorias[i].id).prop('disabled', true);
						$("#"+categorias[i].id).addClass("p77_inputDisabled");
					}
				}	
				
				//Bloqueo todas las subcategorias que cuelguen de ese area.
				var subcategorias = $(".p77_subcategoria");				
				for(var i = 0;i<subcategorias.length;i++){
					var areaSubcategoria = subcategorias[i].id.split("oo")[1].split("o")[0];
					var seccionCategoria = subcategorias[i].id.split("oo")[1].split("o")[1];
					
					if(areaCategoria == idArea && seccionCategoria == idSeccion){
						$("#"+subcategorias[i].id).prop('disabled', true);
						$("#"+subcategorias[i].id).addClass("p77_inputDisabled");
					}
				}	
				
				//Bloqueo todas los segmento que cuelguen de ese area.
				var segmentos = $(".p77_segment");				
				for(var i = 0;i<segmentos.length;i++){
					var areaSegmento = segmentos[i].id.split("oo")[1].split("o")[0];
					var seccionCategoria = segmentos[i].id.split("oo")[1].split("o")[1];
					
					if(areaCategoria == idArea && seccionCategoria == idSeccion){
						$("#"+segmentos[i].id).prop('disabled', true);
						$("#"+segmentos[i].id).addClass("p77_inputDisabled");
					}
				}
			}else if(clase == "p77_categoria"){
				//Deshabilitamos campo superiores
				$(".p77_hoy").prop('disabled', true);
				$(".p77_hoy").addClass("p77_inputDisabled");

				//Obtenemos el idArea
				var idArea = this.id.split("oo")[1].split("o")[0];

				//Obtenemos el idSeccion
				var idSeccion = this.id.split("oo")[1].split("o")[1];
				
				//Obtenemos el idCategoria
				var idCategoria = this.id.split("oo")[1].split("o")[2];

				//Bloqueo el area superior de la seccion.
				var areas = $(".p77_area");				
				for(var i = 0;i<areas.length;i++){
					var areaAreas = areas[i].id.split("oo")[1].split("o")[0];

					if(areaAreas == idArea){
						$("#"+areas[i].id).prop('disabled', true);
						$("#"+areas[i].id).addClass("p77_inputDisabled");
					}
				}

				//Bloqueo todas las secciones que cuelguen de ese area.
				var secciones = $(".p77_seccion");				
				for(var i = 0;i<secciones.length;i++){
					var areaSeccion = secciones[i].id.split("oo")[1].split("o")[0];
					var seccionSeccion = secciones[i].id.split("oo")[1].split("o")[1];

					if(areaSeccion == idArea && seccionSeccion == idSeccion){
						$("#"+secciones[i].id).prop('disabled', true);
						$("#"+secciones[i].id).addClass("p77_inputDisabled");
					}
				}
				
				//Bloqueo todas las subcategorias que cuelguen de ese area.
				var subcategorias = $(".p77_subcategoria");				
				for(var i = 0;i<subcategorias.length;i++){
					var areaSubcategoria = subcategorias[i].id.split("oo")[1].split("o")[0];
					var seccionSubcategoria = subcategorias[i].id.split("oo")[1].split("o")[1];
					var categoriaSubcategoria = subcategorias[i].id.split("oo")[1].split("o")[2];
					
					if(areaSubcategoria == idArea && seccionSubcategoria == idSeccion && categoriaSubcategoria == idCategoria){
						$("#"+subcategorias[i].id).prop('disabled', true);
						$("#"+subcategorias[i].id).addClass("p77_inputDisabled");
					}
				}	
				
				//Bloqueo todas los segmento que cuelguen de ese area.
				var segmentos = $(".p77_segment");				
				for(var i = 0;i<segmentos.length;i++){
					var areaSegmento = segmentos[i].id.split("oo")[1].split("o")[0];
					var seccionSegmento = segmentos[i].id.split("oo")[1].split("o")[1];
					var categoriaSegmento = segmentos[i].id.split("oo")[1].split("o")[2];
					
					if(areaCategoria == idArea && seccionSegmento == idSeccion && categoriaSegmento == idCategoria){
						$("#"+segmentos[i].id).prop('disabled', true);
						$("#"+segmentos[i].id).addClass("p77_inputDisabled");
					}
				}
				
				
			} else if(clase == "p77_subcategoria"){
				//Deshabilitamos campo superiores
				$(".p77_hoy").prop('disabled', true);
				$(".p77_hoy").addClass("p77_inputDisabled");

				//Obtenemos el idArea
				var idArea = this.id.split("oo")[1].split("o")[0];

				//Obtenemos el idSeccion
				var idSeccion = this.id.split("oo")[1].split("o")[1];
				
				//Obtenemos el idCategoria
				var idCategoria = this.id.split("oo")[1].split("o")[2];
				
				//Obtenemos el idCategoria
				var idSubcategoria = this.id.split("oo")[1].split("o")[3];

				//Bloqueo el area superior de la seccion.
				var areas = $(".p77_area");				
				for(var i = 0;i<areas.length;i++){
					var areaAreas = areas[i].id.split("oo")[1].split("o")[0];

					if(areaAreas == idArea){
						$("#"+areas[i].id).prop('disabled', true);
						$("#"+areas[i].id).addClass("p77_inputDisabled");
					}
				}

				//Bloqueo todas las secciones que cuelguen de ese area.
				var secciones = $(".p77_seccion");				
				for(var i = 0;i<secciones.length;i++){
					var areaSeccion = secciones[i].id.split("oo")[1].split("o")[0];
					var seccionSeccion = secciones[i].id.split("oo")[1].split("o")[1];

					if(areaSeccion == idArea && seccionSeccion == idSeccion){
						$("#"+secciones[i].id).prop('disabled', true);
						$("#"+secciones[i].id).addClass("p77_inputDisabled");
					}
				}
				
				
				//Bloqueo todas las categorias que cuelguen de esa seccion y area.
				var categorias = $(".p77_categoria");				
				for(var i = 0;i<categorias.length;i++){
					var areaCategoria = categorias[i].id.split("oo")[1].split("o")[0];
					var seccionCategoria = categorias[i].id.split("oo")[1].split("o")[1];
					var categoriaCategoria = categorias[i].id.split("oo")[1].split("o")[2];


					if(areaCategoria == idArea && seccionCategoria == idSeccion && categoriaCategoria == idCategoria){
						$("#"+categorias[i].id).prop('disabled', true);
						$("#"+categorias[i].id).addClass("p77_inputDisabled");
					}
				}	
				
				//Bloqueo todas los segmento que cuelguen de ese area.
				var segmentos = $(".p77_segment");				
				for(var i = 0;i<segmentos.length;i++){
					var areaSegmento = segmentos[i].id.split("oo")[1].split("o")[0];
					var seccionSegmento = segmentos[i].id.split("oo")[1].split("o")[1];
					var categoriaSegmento = segmentos[i].id.split("oo")[1].split("o")[2];
					var subcategoriaSegmento = segmentos[i].id.split("oo")[1].split("o")[3];
					
					if(areaSegmento == idArea && seccionSegmento == idSeccion && categoriaSegmento == idCategoria  && subcategoriaSegmento == idSubcategoria){
						$("#"+segmentos[i].id).prop('disabled', true);
						$("#"+segmentos[i].id).addClass("p77_inputDisabled");
					}
				}
				
				
			} else if(clase == "p77_segment"){
				//Deshabilitamos campo superiores
				$(".p77_hoy").prop('disabled', true);
				$(".p77_hoy").addClass("p77_inputDisabled");

				//Obtenemos el idArea
				var idArea = this.id.split("oo")[1].split("o")[0];

				//Obtenemos el idSeccion
				var idSeccion = this.id.split("oo")[1].split("o")[1];
				
				//Obtenemos el idCategoria
				var idCategoria = this.id.split("oo")[1].split("o")[2];
				
				//Obtenemos el idSubcategoria
				var idSubcategoria = this.id.split("oo")[1].split("o")[3];
				
				//Obtenemos el idSegmento
				var idSegmento = this.id.split("oo")[1].split("o")[4];

				//Bloqueo el area superior de la seccion.
				var areas = $(".p77_area");				
				for(var i = 0;i<areas.length;i++){
					var areaAreas = areas[i].id.split("oo")[1].split("o")[0];

					if(areaAreas == idArea){
						$("#"+areas[i].id).prop('disabled', true);
						$("#"+areas[i].id).addClass("p77_inputDisabled");
					}
				}

				//Bloqueo todas las secciones que cuelguen de ese area.
				var secciones = $(".p77_seccion");				
				for(var i = 0;i<secciones.length;i++){
					var areaSeccion = secciones[i].id.split("oo")[1].split("o")[0];
					var seccionSeccion = secciones[i].id.split("oo")[1].split("o")[1];

					if(areaSeccion == idArea && seccionSeccion == idSeccion){
						$("#"+secciones[i].id).prop('disabled', true);
						$("#"+secciones[i].id).addClass("p77_inputDisabled");
					}
				}
				
				
				//Bloqueo todas las categorias que cuelguen de esa seccion y area.
				var categorias = $(".p77_categoria");				
				for(var i = 0;i<categorias.length;i++){
					var areaCategoria = categorias[i].id.split("oo")[1].split("o")[0];
					var seccionCategoria = categorias[i].id.split("oo")[1].split("o")[1];
					var categoriaCategoria = categorias[i].id.split("oo")[1].split("o")[2];


					if(areaCategoria == idArea && seccionCategoria == idSeccion && categoriaCategoria == idCategoria){
						$("#"+categorias[i].id).prop('disabled', true);
						$("#"+categorias[i].id).addClass("p77_inputDisabled");
					}
				}	
				
				//Bloqueo todas las subcategorias que cuelguen de ese area.
				var subcategorias = $(".p77_subcategoria");				
				for(var i = 0;i<subcategorias.length;i++){
					var areaSubcategoria = subcategorias[i].id.split("oo")[1].split("o")[0];
					var seccionSubcategoria = subcategorias[i].id.split("oo")[1].split("o")[1];
					var categoriaSubcategoria = subcategorias[i].id.split("oo")[1].split("o")[2];
					var subcategoriaSubcategoria = subcategorias[i].id.split("oo")[1].split("o")[3];
					
					if(areaSubcategoria == idArea && seccionSubcategoria == idSeccion && categoriaSubcategoria == idCategoria  && subcategoriaSubcategoria == idSubcategoria){
						$("#"+subcategorias[i].id).prop('disabled', true);
						$("#"+subcategorias[i].id).addClass("p77_inputDisabled");
					}
				}	
				
				/*
				//Bloqueo todas los segmento que cuelguen de ese area.
				var segmentos = $(".p77_segment");				
				for(var i = 0;i<segmentos.length;i++){
					var areaSegmento = segmentos[i].id.split("oo")[1].split("o")[0];
					var seccionSegmento = segmentos[i].id.split("oo")[1].split("o")[1];
					var categoriaSegmento = segmentos[i].id.split("oo")[1].split("o")[2];
					var subcategoriaSegmento = segmentos[i].id.split("oo")[1].split("o")[3];
					
					if(areaSegmento == idArea && seccionSegmento == idSeccion && categoriaSegmento == idCategoria  && subcategoriaSegmento == idSubcategoria){
						$("#"+segmentos[i].id).prop('disabled', true);
						$("#"+segmentos[i].id).addClass("p77_inputDisabled");
					}
				}
				*/
				
			}
			
			
			
			
			
		}else{
			/*//Si entra aquí significa que el valor esta como estaba originalmente (como en el hidden)
			//Antes de desbloquear los niveles superiores, hay que mirar que no haya otro campo cambiado
			//que los esté bloqueando también. Los niveles inferiores sí se pueden bloquear.
			if(clase == "p77_hoy"){
				//Habilitamos campos inferiores que cuelgan de el.
				$(".p77_area").prop('disabled', false);
				$(".p77_seccion").prop('disabled', false);
				$(".p77_categoria").prop('disabled', false);	

				$(".p77_area").removeClass("p77_inputDisabled");
				$(".p77_seccion").removeClass("p77_inputDisabled");
				$(".p77_categoria").removeClass("p77_inputDisabled");
			}else if(clase == "p77_area"){

				//Obtenemos las areas y vemos si todas están como el campo original. Si 
				//están todas como el campo original, se desbloquea hoy. Si no, no.
				var areas = $(".p70_area");
				var areaNoCambiada = true;
				for(var i = 0;i<areas.length;i++){
					var valorArea = areas[i].value;
					var valorAreaHidden = $("#"+areas[i].id+"Hidden").val();

					if(valorArea != valorAreaHidden){
						areaNoCambiada = false;
						break;
					}
				}

				//Miramis que el area no se haya cambiado.
				if(areaNoCambiada){
					$(".p77_hoy").prop('disabled', false);
					$(".p77_hoy").removeClass("p77_inputDisabled");
				}

				//Habilitamos campos inferiores que cuelgan de el.
				//Obtenemos el idArea
				var idArea = this.id.split("oo")[1].split("o")[0];

				//Bloqueo todas las secciones que cuelguen de ese area.
				var secciones = $(".p77_seccion");				
				for(var i = 0;i<secciones.length;i++){
					var areaSeccion = secciones[i].id.split("oo")[1].split("o")[0];

					if(areaSeccion == idArea){
						$("#"+secciones[i].id).prop('disabled', false);
						$("#"+secciones[i].id).removeClass("p77_inputDisabled");
					}
				}

				//Bloqueo todas las categorias que cuelguen de ese area.
				var categorias = $(".p77_categoria");				
				for(var i = 0;i<categorias.length;i++){
					var areaCategoria = categorias[i].id.split("oo")[1].split("o")[0];

					if(areaCategoria == idArea){
						$("#"+categorias[i].id).prop('disabled', false);
						$("#"+categorias[i].id).removeClass("p77_inputDisabled");
					}
				}		
			}else if(clase == "p77_seccion"){
				//Habilitamos campos inferiores que cuelgan de el.
				//Obtenemos el idArea
				var idArea = this.id.split("oo")[1].split("o")[0];

				//Obtenemos el idSeccion
				var idSeccion = this.id.split("oo")[1].split("o")[1];

				//Bloqueo el area superior de la seccion.
				var areas = $(".p77_area");				
				for(var i = 0;i<areas.length;i++){
					var areaAreas = areas[i].id.split("oo")[1].split("o")[0];

					if(areaAreas == idArea){
						$("#"+areas[i].id).prop('disabled', false);
						$("#"+areas[i].id).removeClass("p77_inputDisabled");
					}
				}

				//Bloqueo todas las categorias que cuelguen de esa seccion y area.
				var categorias = $(".p77_categoria");				
				for(var i = 0;i<categorias.length;i++){
					var areaCategoria = categorias[i].id.split("oo")[1].split("o")[0];
					var seccionCategoria = categorias[i].id.split("oo")[1].split("o")[1];

					if(areaCategoria == idArea && seccionCategoria == idSeccion){
						$("#"+categorias[i].id).prop('disabled', false);
						$("#"+categorias[i].id).removeClass("p77_inputDisabled");
					}
				}	
			}else if(clase == "p77_categoria"){	
				//Habilitamos campos inferiores que cuelgan de el.
				//Obtenemos el idArea
				var idArea = this.id.split("oo")[1].split("o")[0];

				//Obtenemos el idSeccion
				var idSeccion = this.id.split("oo")[1].split("o")[1];

				//Bloqueo el area superior de la seccion.
				var areas = $(".p77_area");				
				for(var i = 0;i<areas.length;i++){
					var areaAreas = areas[i].id.split("oo")[1].split("o")[0];

					if(areaAreas == idArea){
						$("#"+areas[i].id).prop('disabled', false);
						$("#"+areas[i].id).removeClass("p77_inputDisabled");
					}
				}

				//Bloqueo todas las secciones que cuelguen de ese area.
				var secciones = $(".p77_seccion");				
				for(var i = 0;i<secciones.length;i++){
					var areaSeccion = secciones[i].id.split("oo")[1].split("o")[0];
					var seccionSeccion = secciones[i].id.split("oo")[1].split("o")[1];

					if(areaSeccion == idArea && seccionSeccion == idSeccion){
						$("#"+secciones[i].id).prop('disabled', false);
						$("#"+secciones[i].id).removeClass("p77_inputDisabled");
					}
				}			
			}
		}*/
		}
	}
}

function bloquearInputsOtrosNiveles(elemento){
	valorInputActual = elemento.value;
	valorInputHidden = $("#"+elemento.id+"Hidden").val();

	if(valorInputActual != valorInputHidden){
		return true;
	}
	return false;
}

function bloquearInputsOtrosNivelesAlDesplegarArbol(){
	var inputEuroFinal = $(".p77_inputChange");

	for(var j = 0;j<inputEuroFinal.length;j++){
		valorInputActual = inputEuroFinal[j].value;
		valorInputHidden = $("#"+inputEuroFinal[j].id+"Hidden").val();

		//Si el valor actual y el del hidden son distintos, bloqueamos árbol
		if(valorInputActual != valorInputHidden){
			var claseActual = inputEuroFinal[j].id.split("oo")[0];
			//Obtenemos el idArea
			var idArea = inputEuroFinal[j].id.split("oo")[1].split("o")[0];
			//Obtenemos el idSeccion
			var idSeccion = inputEuroFinal[j].id.split("oo")[1].split("o")[1];
			//Obtenemos el idCategoria
			var idCategoria = inputEuroFinal[j].id.split("oo")[1].split("o")[2];
			//Obtenemos el idCategoria
			var idSubcategoria =inputEuroFinal[j].id.split("oo")[1].split("o")[3];
			
			if(claseActual == "p77_hoy"){
				//Deshabilitamos campos inferiores que cuelgan de el.
				$(".p77_area").prop('disabled', true);
				$(".p77_seccion").prop('disabled', true);
				$(".p77_categoria").prop('disabled', true);	
				$(".p77_subcategoria").prop('disabled', true);	
				$(".p77_segment").prop('disabled', true);	

				$(".p77_area").addClass("p77_inputDisabled");
				$(".p77_seccion").addClass("p77_inputDisabled");
				$(".p77_categoria").addClass("p77_inputDisabled");
				$(".p77_subcategoria").addClass("p77_inputDisabled");
				$(".p77_segment").addClass("p77_inputDisabled");
			}else if(claseActual == "p77_area"){
				//Deshabilitamos campo superiores
				$(".p77_hoy").prop('disabled', true);
				$(".p77_hoy").addClass("p77_inputDisabled");

				//Obtenemos el idArea
			//	var idArea = this.id.split("oo")[1].split("o")[0];

				//Bloqueo todas las secciones que cuelguen de ese area.
				var secciones = $(".p77_seccion");				
				for(var i = 0;i<secciones.length;i++){
					var areaSeccion = secciones[i].id.split("oo")[1].split("o")[0];

					if(areaSeccion == idArea){
						$("#"+secciones[i].id).prop('disabled', true);
						$("#"+secciones[i].id).addClass("p77_inputDisabled");
					}
				}

				//Bloqueo todas las categorias que cuelguen de ese area.
				var categorias = $(".p77_categoria");				
				for(var i = 0;i<categorias.length;i++){
					var areaCategoria = categorias[i].id.split("oo")[1].split("o")[0];

					if(areaCategoria == idArea){
						$("#"+categorias[i].id).prop('disabled', true);
						$("#"+categorias[i].id).addClass("p77_inputDisabled");
					}
				}	
				
				//Bloqueo todas las subcategorias que cuelguen de ese area.
				var subcategorias = $(".p77_subcategoria");				
				for(var i = 0;i<subcategorias.length;i++){
					var areaSubcategoria = subcategorias[i].id.split("oo")[1].split("o")[0];

					if(areaSubcategoria == idArea){
						$("#"+subcategorias[i].id).prop('disabled', true);
						$("#"+subcategorias[i].id).addClass("p77_inputDisabled");
					}
				}	
				
				//Bloqueo todas los segmento que cuelguen de ese area.
				var segmentos = $(".p77_segment");				
				for(var i = 0;i<segmentos.length;i++){
					var areaSegmento = segmentos[i].id.split("oo")[1].split("o")[0];

					if(areaSegmento == idArea){
						$("#"+segmentos[i].id).prop('disabled', true);
						$("#"+segmentos[i].id).addClass("p77_inputDisabled");
					}
				}
				
			}else if(claseActual == "p77_seccion"){
				//Deshabilitamos campo superiores
				$(".p77_hoy").prop('disabled', true);
				$(".p77_hoy").addClass("p77_inputDisabled");

				//Obtenemos el idArea
			//	var idArea = this.id.split("oo")[1].split("o")[0];

				//Obtenemos el idSeccion
			//	var idSeccion = this.id.split("oo")[1].split("o")[1];

				//Bloqueo el area superior de la seccion.
				var areas = $(".p77_area");				
				for(var i = 0;i<areas.length;i++){
					var areaAreas = areas[i].id.split("oo")[1].split("o")[0];

					if(areaAreas == idArea){
						$("#"+areas[i].id).prop('disabled', true);
						$("#"+areas[i].id).addClass("p77_inputDisabled");
					}
				}

				//Bloqueo todas las categorias que cuelguen de esa seccion y area.
				var categorias = $(".p77_categoria");				
				for(var i = 0;i<categorias.length;i++){
					var areaCategoria = categorias[i].id.split("oo")[1].split("o")[0];
					var seccionCategoria = categorias[i].id.split("oo")[1].split("o")[1];

					if(areaCategoria == idArea && seccionCategoria == idSeccion){
						$("#"+categorias[i].id).prop('disabled', true);
						$("#"+categorias[i].id).addClass("p77_inputDisabled");
					}
				}	
				
				//Bloqueo todas las subcategorias que cuelguen de ese area.
				var subcategorias = $(".p77_subcategoria");				
				for(var i = 0;i<subcategorias.length;i++){
					var areaSubcategoria = subcategorias[i].id.split("oo")[1].split("o")[0];
					var seccionCategoria = subcategorias[i].id.split("oo")[1].split("o")[1];
					
					if(areaCategoria == idArea && seccionCategoria == idSeccion){
						$("#"+subcategorias[i].id).prop('disabled', true);
						$("#"+subcategorias[i].id).addClass("p77_inputDisabled");
					}
				}	
				
				//Bloqueo todas los segmento que cuelguen de ese area.
				var segmentos = $(".p77_segment");				
				for(var i = 0;i<segmentos.length;i++){
					var areaSegmento = segmentos[i].id.split("oo")[1].split("o")[0];
					var seccionCategoria = segmentos[i].id.split("oo")[1].split("o")[1];
					
					if(areaCategoria == idArea && seccionCategoria == idSeccion){
						$("#"+segmentos[i].id).prop('disabled', true);
						$("#"+segmentos[i].id).addClass("p77_inputDisabled");
					}
				}
			}else if(claseActual == "p77_categoria"){
				//Deshabilitamos campo superiores
				$(".p77_hoy").prop('disabled', true);
				$(".p77_hoy").addClass("p77_inputDisabled");

				//Obtenemos el idArea
			//	var idArea = this.id.split("oo")[1].split("o")[0];

				//Obtenemos el idSeccion
			//	var idSeccion = this.id.split("oo")[1].split("o")[1];
				
				//Obtenemos el idCategoria
			//	var idCategoria = this.id.split("oo")[1].split("o")[2];

				//Bloqueo el area superior de la seccion.
				var areas = $(".p77_area");				
				for(var i = 0;i<areas.length;i++){
					var areaAreas = areas[i].id.split("oo")[1].split("o")[0];

					if(areaAreas == idArea){
						$("#"+areas[i].id).prop('disabled', true);
						$("#"+areas[i].id).addClass("p77_inputDisabled");
					}
				}

				//Bloqueo todas las secciones que cuelguen de ese area.
				var secciones = $(".p77_seccion");				
				for(var i = 0;i<secciones.length;i++){
					var areaSeccion = secciones[i].id.split("oo")[1].split("o")[0];
					var seccionSeccion = secciones[i].id.split("oo")[1].split("o")[1];

					if(areaSeccion == idArea && seccionSeccion == idSeccion){
						$("#"+secciones[i].id).prop('disabled', true);
						$("#"+secciones[i].id).addClass("p77_inputDisabled");
					}
				}
				
				
				
				//Bloqueo todas las subcategorias que cuelguen de ese area.
				var subcategorias = $(".p77_subcategoria");				
				for(var i = 0;i<subcategorias.length;i++){
					var areaSubcategoria = subcategorias[i].id.split("oo")[1].split("o")[0];
					var seccionCategoria = subcategorias[i].id.split("oo")[1].split("o")[1];
					var categoriaCategoria = subcategorias[i].id.split("oo")[1].split("o")[2];
					
					if(areaCategoria == idArea && seccionCategoria == idSeccion && categoriaCategoria == idCategoria){
						$("#"+subcategorias[i].id).prop('disabled', true);
						$("#"+subcategorias[i].id).addClass("p77_inputDisabled");
					}
				}	
				
				//Bloqueo todas los segmento que cuelguen de ese area.
				var segmentos = $(".p77_segment");				
				for(var i = 0;i<segmentos.length;i++){
					var areaSegmento = segmentos[i].id.split("oo")[1].split("o")[0];
					var seccionCategoria = segmentos[i].id.split("oo")[1].split("o")[1];
					var categoriaCategoria = segmentos[i].id.split("oo")[1].split("o")[2];
					
					if(areaCategoria == idArea && seccionCategoria == idSeccion && categoriaCategoria == idCategoria){
						$("#"+segmentos[i].id).prop('disabled', true);
						$("#"+segmentos[i].id).addClass("p77_inputDisabled");
					}
				}
				
			}else if(claseActual == "p77_subcategoria"){
				//Deshabilitamos campo superiores
				$(".p77_hoy").prop('disabled', true);
				$(".p77_hoy").addClass("p77_inputDisabled");

				//Obtenemos el idArea
			//	var idArea = this.id.split("oo")[1].split("o")[0];

				//Obtenemos el idSeccion
			//	var idSeccion = this.id.split("oo")[1].split("o")[1];
				
				//Obtenemos el idCategoria
			//	var idCategoria = this.id.split("oo")[1].split("o")[2];
				
				//Obtenemos el idCategoria
			//	var idSubcategoria = this.id.split("oo")[1].split("o")[3];

				//Bloqueo el area superior de la seccion.
				var areas = $(".p77_area");				
				for(var i = 0;i<areas.length;i++){
					var areaAreas = areas[i].id.split("oo")[1].split("o")[0];

					if(areaAreas == idArea){
						$("#"+areas[i].id).prop('disabled', true);
						$("#"+areas[i].id).addClass("p77_inputDisabled");
					}
				}

				//Bloqueo todas las secciones que cuelguen de ese area.
				var secciones = $(".p77_seccion");				
				for(var i = 0;i<secciones.length;i++){
					var areaSeccion = secciones[i].id.split("oo")[1].split("o")[0];
					var seccionSeccion = secciones[i].id.split("oo")[1].split("o")[1];

					if(areaSeccion == idArea && seccionSeccion == idSeccion){
						$("#"+secciones[i].id).prop('disabled', true);
						$("#"+secciones[i].id).addClass("p77_inputDisabled");
					}
				}
				
				
				//Bloqueo todas las categorias que cuelguen de esa seccion y area.
				var categorias = $(".p77_categoria");				
				for(var i = 0;i<categorias.length;i++){
					var areaCategoria = categorias[i].id.split("oo")[1].split("o")[0];
					var seccionCategoria = categorias[i].id.split("oo")[1].split("o")[1];
					var categoriaCategoria = categorias[i].id.split("oo")[1].split("o")[2];


					if(areaCategoria == idArea && seccionCategoria == idSeccion && categoriaCategoria == idCategoria){
						$("#"+categorias[i].id).prop('disabled', true);
						$("#"+categorias[i].id).addClass("p77_inputDisabled");
					}
				}	
				
				
				//Bloqueo todas los segmento que cuelguen de ese area.
				var segmentos = $(".p77_segment");				
				for(var i = 0;i<segmentos.length;i++){
					var areaSegmento = segmentos[i].id.split("oo")[1].split("o")[0];
					var seccionSegmento = segmentos[i].id.split("oo")[1].split("o")[1];
					var categoriaSegmento = segmentos[i].id.split("oo")[1].split("o")[2];
					var subcategoriaSegmento = segmentos[i].id.split("oo")[1].split("o")[3];
					
					if(areaSegmento == idArea && seccionSegmento == idSeccion && categoriaSegmento == idCategoria  && subcategoriaSegmento == idSubcategoria){
						$("#"+segmentos[i].id).prop('disabled', true);
						$("#"+segmentos[i].id).addClass("p77_inputDisabled");
					}
				}
				
				
			} else if(claseActual == "p77_segment"){
				//Deshabilitamos campo superiores
				$(".p77_hoy").prop('disabled', true);
				$(".p77_hoy").addClass("p77_inputDisabled");

				//Obtenemos el idArea
			//	var idArea = this.id.split("oo")[1].split("o")[0];

				//Obtenemos el idSeccion
			//	var idSeccion = this.id.split("oo")[1].split("o")[1];
				
				//Obtenemos el idCategoria
			//	var idCategoria = this.id.split("oo")[1].split("o")[2];
				
				//Obtenemos el idSubcategoria
			//	var idSubcategoria = this.id.split("oo")[1].split("o")[3];
				
				//Obtenemos el idSegmento
			//	var idSegmento = this.id.split("oo")[1].split("o")[4];

				//Bloqueo el area superior de la seccion.
				var areas = $(".p77_area");				
				for(var i = 0;i<areas.length;i++){
					var areaAreas = areas[i].id.split("oo")[1].split("o")[0];

					if(areaAreas == idArea){
						$("#"+areas[i].id).prop('disabled', true);
						$("#"+areas[i].id).addClass("p77_inputDisabled");
					}
				}

				//Bloqueo todas las secciones que cuelguen de ese area.
				var secciones = $(".p77_seccion");				
				for(var i = 0;i<secciones.length;i++){
					var areaSeccion = secciones[i].id.split("oo")[1].split("o")[0];
					var seccionSeccion = secciones[i].id.split("oo")[1].split("o")[1];

					if(areaSeccion == idArea && seccionSeccion == idSeccion){
						$("#"+secciones[i].id).prop('disabled', true);
						$("#"+secciones[i].id).addClass("p77_inputDisabled");
					}
				}
				
				
				//Bloqueo todas las categorias que cuelguen de esa seccion y area.
				var categorias = $(".p77_categoria");				
				for(var i = 0;i<categorias.length;i++){
					var areaCategoria = categorias[i].id.split("oo")[1].split("o")[0];
					var seccionCategoria = categorias[i].id.split("oo")[1].split("o")[1];
					var categoriaCategoria = categorias[i].id.split("oo")[1].split("o")[2];


					if(areaCategoria == idArea && seccionCategoria == idSeccion && categoriaCategoria == idCategoria){
						$("#"+categorias[i].id).prop('disabled', true);
						$("#"+categorias[i].id).addClass("p77_inputDisabled");
					}
				}	
				
				//Bloqueo todas las subcategorias que cuelguen de ese area.
				var subcategorias = $(".p77_subcategoria");				
				for(var i = 0;i<subcategorias.length;i++){
					var areaSubcategoria = subcategorias[i].id.split("oo")[1].split("o")[0];
					var seccionSubcategoria = subcategorias[i].id.split("oo")[1].split("o")[1];
					var categoriaSubcategoria = subcategorias[i].id.split("oo")[1].split("o")[2];
					var subcategoriaSubcategoria = subcategorias[i].id.split("oo")[1].split("o")[3];
					
					if(areaSubcategoria == idArea && seccionSubcategoria == idSeccion && categoriaSubcategoria == idCategoria  && subcategoriaSubcategoria == idSubcategoria){
						$("#"+subcategorias[i].id).prop('disabled', true);
						$("#"+subcategorias[i].id).addClass("p77_inputDisabled");
					}
				}	
				
				/*
				//Bloqueo todas los segmento que cuelguen de ese area.
				var segmentos = $(".p77_segment");				
				for(var i = 0;i<segmentos.length;i++){
					var areaSegmento = segmentos[i].id.split("oo")[1].split("o")[0];
					var seccionSegmento = segmentos[i].id.split("oo")[1].split("o")[1];
					var categoriaSegmento = segmentos[i].id.split("oo")[1].split("o")[2];
					var subcategoriaSegmento = segmentos[i].id.split("oo")[1].split("o")[3];
					
					if(areaSegmento == idArea && seccionSegmento == idSeccion && categoriaSegmento == idCategoria  && subcategoriaSegmento == idSubcategoria){
						$("#"+segmentos[i].id).prop('disabled', true);
						$("#"+segmentos[i].id).addClass("p77_inputDisabled");
					}
				}
				*/
				
			}
			
			
		}
	}
}