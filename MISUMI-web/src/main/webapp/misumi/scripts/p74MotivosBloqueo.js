var p74BotonAceptar = null;
var gridP74Motivo=null;
var gridP74Bloqueo=null;
var tituloVentanaPopupMotivosBloqueo = null;
var textoMotivoBloqueo = null;
var textoBloqueoEncargoFecha = null;
var textoBloqueoMontajeFechas = null;
var textoBloqueoPlanogramadaImplIni = null;
var textoBloqueoPlanogramadaImplFin = null;
var textoBloqueoEncargoCantidad = null;
var textoBloqueoMontajeCantidad = null;
var codArt = null;

var recordMotivoBloqueoP74 = null;

$(document).ready(function(){

	loadP74(locale);
});

function initializeScreenPopupMotivosBloqueo(){
	
	$( "#p74_popupMotivosBloqueo" ).dialog({
        autoOpen: false,
        height: 'auto',
        width: 'auto',
        modal: true,
        title: tituloVentanaPopupMotivosBloqueo,
        resizable: false,
        dialogClass: "popupResaltado",
        buttons:[{
            text: p74BotonAceptar,
            click: function() {
                      $(this).dialog('close');
                   }
        }],
		open: function() {
			$('.ui-dialog-titlebar-close').on('mousedown', function(){
				$("#p74_popupMotivosBloqueo").dialog('close');
			});
		}
    });
	
	$(window).bind('resize', function() {
	    $("#p74_popupMotivosBloqueo").dialog("option", "position", "center");
	});
}

function loadP74(locale){
	gridP74Motivo = new GridP74Motivo(locale);
	gridP74Bloqueo = new GridP74Bloqueo(locale);
	
	var jqxhr = $.getJSON(gridP74Motivo.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				tituloVentanaPopupMotivosBloqueo = data.tituloVentanaPopupMotivosBloqueo;
				p74BotonAceptar = data.p74BotonAceptar;
				
				gridP74Motivo.colNames = data.p74MotivoColNames;
				gridP74Motivo.emptyRecords= data.emptyRecords;
				gridP74Bloqueo.colNames = data.p74BloqueoColNames;
				gridP74Bloqueo.emptyRecords= data.emptyRecords;

				textoMotivoBloqueo = data.textoMotivoBloqueo;
				textoBloqueoEncargoFecha = data.textoBloqueoEncargoFecha;
				textoBloqueoMontajeFechas = data.textoBloqueoMontajeFechas;
				textoBloqueoPlanogramadaImplIni = data.textoBloqueoPlanogramadaImplIni;
				textoBloqueoPlanogramadaImplFin = data.textoBloqueoPlanogramadaImplFin;
				textoBloqueoEncargoCantidad = data.textoBloqueoEncargoCantidad;
				textoBloqueoMontajeCantidad = data.textoBloqueoMontajeCantidad;

				initializeScreenPopupMotivosBloqueo();
				
				loadP74MotivosMock(gridP74Motivo);
				loadP74BloqueosMock(gridP74Bloqueo);
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
         });
}

function loadP74MotivosMock(gridP74Motivo) {
	//para hacer cabeceras
		$(gridP74Motivo.nameJQuery).jqGrid({
			ajaxGridOptions : {
				contentType : 'application/json; charset=utf-8',
				cache : false
			},
			datatype : 'local',
			contentType : 'application/json',
			mtype : 'POST',
			colNames : gridP74Motivo.colNames,
			colModel :[
			   		{
						"name"  : "textoMotivo",
						"index" : "textoMotivo", 
						"width" : 410,
						"formatter": p74MotivoFormatter,
						"sortable" : false
					}
				],  
			rowNum : 4,
			height : "100%",
			autowidth : true,
			width : "auto",
			pginput: false,
			rownumbers : false,
			pager : gridP74Motivo.pagerNameJQuery,
			viewrecords : false,
			//caption : gridP74Motivo.title,
			altclass: "ui-priority-secondary",
			altRows: true, //false, para que el grid no muestre cebrado
			hidegrid : false, //false, para ocultar el boton que colapsa el grid.
			sortable : false,
			index: gridP74Motivo.sortIndex,
			sortname: gridP74Motivo.sortIndex,
			sortorder: gridP74Motivo.sortOrder,
			emptyrecords : gridP74Motivo.emptyRecords,
			gridComplete : function() {

				
			},
			loadComplete : function(data) {
				gridP74Motivo.actualPage = data.page;
				gridP74Motivo.localData = data;
				gridP74Motivo.sortIndex = null;
				gridP74Motivo.sortOrder = null;
				$("#p74_AreaPopupMotivosBloqueoTabla .loading").css("display", "none");	
			},
			onPaging : function(postdata) {			
				alreadySorted = false;
				gridP74Motivo.sortIndex = null;
				gridP74Motivo.sortOrder = null;
				reloadDataMotivosP74(gridP74Motivo);
				return 'stop';
			},
			onSelectRow: function(id){

			},
			onSortCol : function (index, columnIndex, sortOrder){
				gridP74Motivo.sortIndex = index;
				gridP74Motivo.sortOrder = sortOrder;
				reloadDataMotivosP74(gridP74Motivo);
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

function loadP74BloqueosMock(gridP74Bloqueo) {
	//para hacer cabeceras
		$(gridP74Bloqueo.nameJQuery).jqGrid({
			ajaxGridOptions : {
				contentType : 'application/json; charset=utf-8',
				cache : false
			},
			datatype : 'local',
			contentType : 'application/json',
			mtype : 'POST',
			colNames : gridP74Motivo.colNames,
			colModel :[
			   		{
						"name"  : "textoBloqueo",
						"index" : "textoBloqueo", 
						"width" : 410,
						"formatter": p74BloqueoFormatter,
						"sortable" : false
					}
				],  
			rowNum : 4,
			height : "100%",
			autowidth : true,
			width : "auto",
			pginput: false,
			rownumbers : false,
			pager : gridP74Bloqueo.pagerNameJQuery,
			viewrecords : false,
			//caption : gridP74Bloqueo.title,
			altclass: "ui-priority-secondary",
			altRows: true, //false, para que el grid no muestre cebrado
			hidegrid : false, //false, para ocultar el boton que colapsa el grid.
			sortable : false,
			index: gridP74Bloqueo.sortIndex,
			sortname: gridP74Bloqueo.sortIndex,
			sortorder: gridP74Bloqueo.sortOrder,
			emptyrecords : gridP74Bloqueo.emptyRecords,
			gridComplete : function() {

				
			},
			loadComplete : function(data) {
				gridP74Bloqueo.actualPage = data.page;
				gridP74Bloqueo.localData = data;
				gridP74Bloqueo.sortIndex = null;
				gridP74Bloqueo.sortOrder = null;
				$("#p74_AreaPopupMotivosBloqueosCentralTabla .loading").css("display", "none");	
			},
			onPaging : function(postdata) {			
				alreadySorted = false;
				gridP74Bloqueo.sortIndex = null;
				gridP74Bloqueo.sortOrder = null;
				reloadDataBloqueosP74(gridP74Bloqueo);
				return 'stop';
			},
			onSelectRow: function(id){

			},
			onSortCol : function (index, columnIndex, sortOrder){
				gridP74Bloqueo.sortIndex = index;
				gridP74Bloqueo.sortOrder = sortOrder;
				reloadDataBloqueosP74(gridP74Bloqueo);
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

/*Clase de constantes para el GRID de motivos*/
function GridP74Motivo (locale){
	// Atributos
	this.name = "gridP74Motivo"; 
	this.nameJQuery = "#gridP74Motivo"; 
	this.i18nJSON = './misumi/resources/p74MotivosBloqueo/p74motivosBloqueo_' + locale + '.json';
	this.colNames = null;
	
	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP74Motivo"; 
	this.pagerNameJQuery = "#pagerP74Motivo";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;

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
			return "grupo1";
		}
	} 
	 
	this.getSortOrder = function getSortOrder () {
		
		if ($(this.nameJQuery).getGridParam('sortorder')!=null){
			return $(this.nameJQuery).getGridParam('sortorder');
		}else{
			return "asc";
		}
	} 	
}

/*Clase de constantes para el GRID de bloqueos*/
function GridP74Bloqueo (locale){
	// Atributos
	this.name = "gridP74Bloqueo"; 
	this.nameJQuery = "#gridP74Bloqueo"; 
	this.i18nJSON = './misumi/resources/p74MotivosBloqueo/p74motivosBloqueo_' + locale + '.json';
	this.colNames = null;
	
	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP74Bloqueo"; 
	this.pagerNameJQuery = "#pagerP74Bloqueo";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;

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
			return "grupo1";
		}
	} 
	 
	this.getSortOrder = function getSortOrder () {
		
		if ($(this.nameJQuery).getGridParam('sortorder')!=null){
			return $(this.nameJQuery).getGridParam('sortorder');
		}else{
			return "asc";
		}
	} 	
}

function p74MotivoFormatter(cellvalue, options, rowObject) {
	
	var textoMotivoBloqueoLinea = textoMotivoBloqueo;
	
	var fechaFechaInicioFormateada = p74FechaFormatter(rowObject.fecIniDDMMYYYY);
	var fechaFechaFinFormateada = p74FechaFormatter(rowObject.fecFinDDMMYYYY);

	textoMotivoBloqueoLinea = textoMotivoBloqueoLinea.replace('{0}',fechaFechaInicioFormateada);
	textoMotivoBloqueoLinea = textoMotivoBloqueoLinea.replace('{1}',fechaFechaFinFormateada);
	
    var textoFormateado = "<span class=\"p74MotivoBloqueoTexto\">- " + textoMotivoBloqueoLinea + "</span>";

    return textoFormateado;
};

function p74BloqueoFormatter(cellvalue, options, rowObject) {
	
	var paginaActual = gridP74Bloqueo.getActualPage();
	var numElemsPag = gridP74Bloqueo.getRowNumPerPage();
	var rowIdPag = parseInt(options.rowId,10);
	var fila = ((paginaActual - 1)*numElemsPag)+rowIdPag-1;
	
	var esPlanograma = rowObject.esPlanograma;
	var textoFormateado = "<ol id='p74BloqueoCentralFila' type='a' style='counter-reset: item "+fila+";'>"
	
	if ("S"==esPlanograma){
		//Formateo de planograma
		var textoBloqueoMontajeFechasLinea = textoBloqueoMontajeFechas;
		var textoBloqueoPlanogramadaImplIniLinea = textoBloqueoPlanogramadaImplIni;
		var textoBloqueoPlanogramadaImplFinLinea = textoBloqueoPlanogramadaImplFin;
		textoBloqueoMontajeFechasLinea = textoBloqueoMontajeFechasLinea.replace('{0}',p74FechaFormatter(rowObject.fechaInicio));
		textoBloqueoMontajeFechasLinea = textoBloqueoMontajeFechasLinea.replace('{1}',p74FechaFormatter(rowObject.fechaFin));
		textoBloqueoPlanogramadaImplIniLinea = textoBloqueoPlanogramadaImplIniLinea.replace('{0}',rowObject.capMax);
		textoBloqueoPlanogramadaImplFinLinea = textoBloqueoPlanogramadaImplFinLinea.replace('{0}',rowObject.capMin);
		
		textoFormateado += "<li><span class=\"p74BloqueoCentralTextoEncMon\">" + textoBloqueoMontajeFechasLinea+"</span></li>" 
	    +"<div class='p74BloqueoCentralTextoCantDiv'><span class=\"p74BloqueoCentralTextoCant\">" + textoBloqueoPlanogramadaImplIniLinea+"</span></div>"
	    +"<div class='p74BloqueoCentralTextoCantDiv'><span class=\"p74BloqueoCentralTextoCant\">" + textoBloqueoPlanogramadaImplFinLinea+"</span></div>";
	}else{
		if (rowObject.fecha2 != null){
			//Montaje
			var textoBloqueoMontajeFechasLinea = textoBloqueoMontajeFechas;
			textoBloqueoMontajeFechasLinea = textoBloqueoMontajeFechasLinea.replace('{0}',p74FechaFormatter(rowObject.fechaInicio));
			textoBloqueoMontajeFechasLinea = textoBloqueoMontajeFechasLinea.replace('{1}',p74FechaFormatter(rowObject.fechaFin));
			
			textoFormateado += "<li><span class=\"p74BloqueoCentralTextoEncMon\">" + textoBloqueoMontajeFechasLinea+"</span></li>";
			
			var textoBloqueoMontajeCantidadLinea = textoBloqueoMontajeCantidad;
			textoBloqueoMontajeCantidadLinea = textoBloqueoMontajeCantidadLinea.replace('{0}','1');
			textoBloqueoMontajeCantidadLinea = textoBloqueoMontajeCantidadLinea.replace('{1}',rowObject.cantidad1);
			textoBloqueoMontajeCantidadLinea = textoBloqueoMontajeCantidadLinea.replace('{2}',p74FechaFormatter(rowObject.fechaInicio));
			textoFormateado +="<div class='p74BloqueoCentralTextoCantDiv'><span class=\"p74BloqueoCentralTextoCant\">" + textoBloqueoMontajeCantidadLinea+"</span></div>";
			
			textoBloqueoMontajeCantidadLinea = textoBloqueoMontajeCantidad;
			textoBloqueoMontajeCantidadLinea = textoBloqueoMontajeCantidadLinea.replace('{0}','2');
			textoBloqueoMontajeCantidadLinea = textoBloqueoMontajeCantidadLinea.replace('{1}',rowObject.cantidad2);
			textoBloqueoMontajeCantidadLinea = textoBloqueoMontajeCantidadLinea.replace('{2}',p74FechaFormatter(rowObject.fecha2));
			textoFormateado +="<div class='p74BloqueoCentralTextoCantDiv'><span class=\"p74BloqueoCentralTextoCant\">" + textoBloqueoMontajeCantidadLinea+"</span></div>";
			
		    if (rowObject.fecha3 != null){
				textoBloqueoMontajeCantidadLinea = textoBloqueoMontajeCantidad;
				textoBloqueoMontajeCantidadLinea = textoBloqueoMontajeCantidadLinea.replace('{0}','3');
				textoBloqueoMontajeCantidadLinea = textoBloqueoMontajeCantidadLinea.replace('{1}',rowObject.cantidad3);
				textoBloqueoMontajeCantidadLinea = textoBloqueoMontajeCantidadLinea.replace('{2}',p74FechaFormatter(rowObject.fecha3));
				textoFormateado +="<div class='p74BloqueoCentralTextoCantDiv'><span class=\"p74BloqueoCentralTextoCant\">" + textoBloqueoMontajeCantidadLinea+"</span></div>";
				
		    	if (rowObject.fecha4 != null){
					textoBloqueoMontajeCantidadLinea = textoBloqueoMontajeCantidad;
					textoBloqueoMontajeCantidadLinea = textoBloqueoMontajeCantidadLinea.replace('{0}','4');
					textoBloqueoMontajeCantidadLinea = textoBloqueoMontajeCantidadLinea.replace('{1}',rowObject.cantidad4);
					textoBloqueoMontajeCantidadLinea = textoBloqueoMontajeCantidadLinea.replace('{2}',p74FechaFormatter(rowObject.fecha4));
					textoFormateado +="<div class='p74BloqueoCentralTextoCantDiv'><span class=\"p74BloqueoCentralTextoCant\">" + textoBloqueoMontajeCantidadLinea+"</span></div>";

		    		if (rowObject.fecha5 != null){
						textoBloqueoMontajeCantidadLinea = textoBloqueoMontajeCantidad;
						textoBloqueoMontajeCantidadLinea = textoBloqueoMontajeCantidadLinea.replace('{0}','5');
						textoBloqueoMontajeCantidadLinea = textoBloqueoMontajeCantidadLinea.replace('{1}',rowObject.cantidad5);
						textoBloqueoMontajeCantidadLinea = textoBloqueoMontajeCantidadLinea.replace('{2}',p74FechaFormatter(rowObject.fecha5));
						textoFormateado +="<div class='p74BloqueoCentralTextoCantDiv'><span class=\"p74BloqueoCentralTextoCant\">" + textoBloqueoMontajeCantidadLinea+"</span></div>";
		    		}
		    	}
		    }

		}else{
			//Encargo
			var textoBloqueoEncargoFechaLinea = textoBloqueoEncargoFecha;
			var textoBloqueoEncargoCantidadLinea = textoBloqueoEncargoCantidad;
			textoBloqueoEncargoFechaLinea = textoBloqueoEncargoFechaLinea.replace('{0}',p74FechaFormatter(rowObject.fechaInicio));
			textoBloqueoEncargoCantidadLinea = textoBloqueoEncargoCantidadLinea.replace('{0}',rowObject.cantidad1);
			textoFormateado += "<li><span class=\"p74BloqueoCentralTextoEncMon\">" + textoBloqueoEncargoFechaLinea+"</span></li>"
		    +"<div class='p74BloqueoCentralTextoCantDiv'><span class=\"p74BloqueoCentralTextoCant\">" + textoBloqueoEncargoCantidadLinea+"</span></div>";

		}
	}

    return "</ol>"+textoFormateado;
};

function reloadDataP74(gridP74Motivo, gridP74Bloqueo, buscarPedidos) {

	var objJson = $.toJSON(recordMotivoBloqueoP74.prepareToJsonObject());

	$("#p74_AreaPopupMotivosBloqueoTabla .loading").css("display", "block");
	$("#p74_AreaPopupMotivosBloqueosCentralTabla .loading").css("display", "block");

	$.ajax({
		type : 'POST',
		url : './motivosBloqueo/loadMotivosBloqueos.do?buscarPedidos='+buscarPedidos,
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		

			if ((data.motivos.records == 0 || data.motivos.records == null) && (data.pedidos.records == 0 || data.pedidos.records == null)) {
				//Mostrar leyenda de no hay registros en la tabla
				$('#p74_resultadosNoHayRegistros').show();
				$('#p74_AreaPopupMotivosBloqueoTabla').hide();
				$('#p74_AreaPopupMotivosBloqueosCentralTabla').hide();
			}else{
				$('#p74_resultadosNoHayRegistros').hide();
			} 

			if (data.motivos.records == 0 || data.motivos.records == null){
				$('#p74_AreaPopupMotivosBloqueoTabla').hide();
				$('#p74_motivosBloqueoTitulo').hide();
				$('#gridP74Motivo').hide();
				$('#pagerP74Motivo').hide();
			}else{
				$('#p74_AreaPopupMotivosBloqueoTabla').show();
				$('#p74_motivosBloqueoTitulo').show();
				$('#gridP74Motivo').show();
				if (data.motivos.records > 4){
					$('#pagerP74Motivo').show();
				}else{
					$('#pagerP74Motivo').hide();
				}
				$(gridP74Motivo.nameJQuery)[0].addJSONData(data.motivos);
				gridP74Motivo.actualPage = data.motivos.page;
				gridP74Motivo.localData = data.motivos;
			}
			if (data.pedidos.records == 0 || data.pedidos.records == null) {
				$('#p74_AreaPopupMotivosBloqueosCentralTabla').hide();
				$('#p74_bloqueosCentralTitulo').hide();
				$('#gridP74Bloqueo').hide();
				$('#pagerP74Bloqueo').hide();
			}else{
				if (data.motivos.records == 0 || data.motivos.records == null) {
					$('#p74_bloqueosCentralTitulo1').show();
					$('#p74_bloqueosCentralTitulo2').hide();
				}else{
					$('#p74_bloqueosCentralTitulo1').hide();
					$('#p74_bloqueosCentralTitulo2').show();
				}
				$('#p74_AreaPopupMotivosBloqueosCentralTabla').show();
				$('#p74_bloqueosCentralTitulo').show();
				$('#gridP74Bloqueo').show();
				if (data.pedidos.records > 4){
					$('#pagerP74Bloqueo').show();
				}else{
					$('#pagerP74Bloqueo').hide();
				}
				$(gridP74Bloqueo.nameJQuery)[0].addJSONData(data.pedidos);
				gridP74Bloqueo.actualPage = data.pedidos.page;
				gridP74Bloqueo.localData = data.pedidos;
			}
			$("#p74_AreaPopupMotivosBloqueoTabla .loading").css("display", "none");
			$("#p74_AreaPopupMotivosBloqueosCentralTabla .loading").css("display", "none");
			$("#p74_popupMotivosBloqueo").dialog("option", "position", "center");
		},
		error : function (xhr, status, error){
			$("#p74_AreaPopupMotivosBloqueoTabla .loading").css("display", "none");
			$("#p74_AreaPopupMotivosBloqueosCentralTabla .loading").css("display", "none");
			handleError(xhr, status, error, locale);				
        }			
	});		
}

function reloadDataP74_2(gridP74Motivo, gridP74Bloqueo, buscarPedidos) {

	var objJson = $.toJSON(recordMotivoBloqueoP74.prepareToJsonObject());

	$("#p74_AreaPopupMotivosBloqueoTabla .loading").css("display", "block");
	$("#p74_AreaPopupMotivosBloqueosCentralTabla .loading").css("display", "block");

	$.ajax({
		type : 'POST',
		url : './motivosBloqueo/loadMotivosBloqueos.do?buscarPedidos='+buscarPedidos,
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		

			if (data.motivos.records == 0 && data.pedidos.records == 0){
				//Mostrar leyenda de no hay registros en la tabla
				$('#p74_resultadosNoHayRegistros').show();
				$('#p74_AreaPopupMotivosBloqueoTabla').hide();
				$('#p74_AreaPopupMotivosBloqueosCentralTabla').hide();
			}else{
				$('#p74_resultadosNoHayRegistros').hide();
			} 

			if (data.motivos.records == 0){
				$('#p74_AreaPopupMotivosBloqueoTabla').hide();
				$('#p74_motivosBloqueoTitulo').hide();
				$('#gridP74Motivo').hide();
				$('#pagerP74Motivo').hide();
			}else{
				$('#p74_AreaPopupMotivosBloqueoTabla').hide();
				$('#p74_motivosBloqueoTitulo').hide();
				$('#gridP74Motivo').hide();
				if (data.motivos.records > 4){
					$('#pagerP74Motivo').hide();
				}else{
					$('#pagerP74Motivo').hide();
				}
		
				p74Enlace();
			}
			if (data.pedidos.records == 0){
				$('#p74_AreaPopupMotivosBloqueosCentralTabla').hide();
				$('#p74_bloqueosCentralTitulo').hide();
				$('#gridP74Bloqueo').hide();
				$('#pagerP74Bloqueo').hide();
			}else{
				if (data.motivos.records == 0){
					$('#p74_bloqueosCentralTitulo3').show();
					$('#p74_bloqueosCentralTitulo2').hide();
				}else{
					$('#p74_bloqueosCentralTitulo3').hide();
					$('#p74_bloqueosCentralTitulo2').show();
				}
				$('#p74_AreaPopupMotivosBloqueosCentralTabla').show();
				$('#p74_bloqueosCentralTitulo').show();
				$('#gridP74Bloqueo').show();
				if (data.pedidos.records > 4){
					$('#pagerP74Bloqueo').hide();
				}else{
					$('#pagerP74Bloqueo').hide();
				}
				p74Enlace();
			}
			$("#p74_AreaPopupMotivosBloqueoTabla .loading").css("display", "none");
			$("#p74_AreaPopupMotivosBloqueosCentralTabla .loading").css("display", "none");
			$("#p74_popupMotivosBloqueo").dialog("option", "position", "center");
		},
		error : function (xhr, status, error){
			$("#p74_AreaPopupMotivosBloqueoTabla .loading").css("display", "none");
			$("#p74_AreaPopupMotivosBloqueosCentralTabla .loading").css("display", "none");
			handleError(xhr, status, error, locale);				
        }			
	});		
}


function reloadDataMotivosP74(gridP74Motivo) {

	var objJson = $.toJSON(recordMotivoBloqueoP74.prepareToJsonObject());

	$("#p74_AreaPopupMotivosBloqueoTabla .loading").css("display", "block");

	$.ajax({
		type : 'POST',
		url : './motivosBloqueo/loadMotivos.do?page='+gridP74Motivo.getActualPage()+'&max='+gridP74Motivo.getRowNumPerPage()+'&index='+gridP74Motivo.getSortIndex()+'&sortorder='+gridP74Motivo.getSortOrder(),
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		
			$(gridP74Motivo.nameJQuery)[0].addJSONData(data);
			gridP74Motivo.actualPage = data.page;
			gridP74Motivo.localData = data;

			$("#p74_AreaPopupMotivosBloqueoTabla .loading").css("display", "none");	
			$("#p74_popupMotivosBloqueo").dialog("option", "position", "center");
		},
		error : function (xhr, status, error){
			$("#p74_AreaPopupMotivosBloqueoTabla .loading").css("display", "none");	
			handleError(xhr, status, error, locale);				
        }			
	});		
}

function reloadDataBloqueosP74(gridP74Bloqueo) {

	var objJson = $.toJSON(recordMotivoBloqueoP74.prepareToJsonObject());

	$("#p74_AreaPopupMotivosBloqueosCentralTabla .loading").css("display", "block");

	$.ajax({
		type : 'POST',
		url : './motivosBloqueo/loadBloqueos.do?page='+gridP74Bloqueo.getActualPage()+'&max='+gridP74Bloqueo.getRowNumPerPage()+'&index='+gridP74Bloqueo.getSortIndex()+'&sortorder='+gridP74Bloqueo.getSortOrder(),
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		
			$(gridP74Bloqueo.nameJQuery)[0].addJSONData(data);
			gridP74Bloqueo.actualPage = data.page;
			gridP74Bloqueo.localData = data;

			$("#p74_AreaPopupMotivosBloqueosCentralTabla .loading").css("display", "none");	
			$("#p74_popupMotivosBloqueo").dialog("option", "position", "center");
		},
		error : function (xhr, status, error){
			$("#p74_AreaPopupMotivosBloqueosCentralTabla .loading").css("display", "none");	
			handleError(xhr, status, error, locale);				
        }			
	});		
}

function reloadPopupP74(codArticulo, codTpBloqueo, fechaInicioDDMMYYYY, fecha2DDMMYYYY, fecha3DDMMYYYY, fecha4DDMMYYYY, fecha5DDMMYYYY, fechaInPilDDMMYYYY, fechaFinDDMMYYYY, buscarPedidos, clasePedido) {
	recordMotivoBloqueoP74 = new MotivoBloqueo (codArticulo, codTpBloqueo, fechaInicioDDMMYYYY, fecha2DDMMYYYY, fecha3DDMMYYYY, fecha4DDMMYYYY, fecha5DDMMYYYY, fechaInPilDDMMYYYY, fechaFinDDMMYYYY, clasePedido);
  	
	//Se elimina el mensaje de no hay registros si lo hubiese y cada una de las secciones
	$('#p74_resultadosNoHayRegistros').hide();

	$('#p74_AreaPopupMotivosBloqueoTabla').hide();
	$('#p74_AreaPopupMotivosBloqueosCentralTabla').hide();
	$('#p74_motivosBloqueoTitulo').hide();
	$('#gridP74Motivo').hide();
	$('#pagerP74Motivo').hide();
	$('#p74_bloqueosCentralTitulo').hide();
	$('#gridP74Bloqueo').hide();
	$('#pagerP74Bloqueo').hide();

    $( "#p74_popupMotivosBloqueo" ).dialog( "open" );
	reloadDataP74(gridP74Motivo, gridP74Bloqueo, buscarPedidos);
}

function reloadPopupP74_2(codArticulo, codTpBloqueo, fechaInicioDDMMYYYY, fecha2DDMMYYYY, fecha3DDMMYYYY, fecha4DDMMYYYY, fecha5DDMMYYYY, fechaInPilDDMMYYYY, fechaFinDDMMYYYY, buscarPedidos, clasePedido) {
	//Se recoge el codigo del articulo en var global.
	codArt = codArticulo;
	
	recordMotivoBloqueoP74 = new MotivoBloqueo (codArticulo, codTpBloqueo, fechaInicioDDMMYYYY, fecha2DDMMYYYY, fecha3DDMMYYYY, fecha4DDMMYYYY, fecha5DDMMYYYY, fechaInPilDDMMYYYY, fechaFinDDMMYYYY, clasePedido);
  	
	//Se elimina el mensaje de no hay registros si lo hubiese y cada una de las secciones
	$('#p74_resultadosNoHayRegistros').hide();

	$('#p74_AreaPopupMotivosBloqueoTabla').hide();
	$('#p74_AreaPopupMotivosBloqueosCentralTabla').hide();
	$('#p74_motivosBloqueoTitulo').hide();
	$('#gridP74Motivo').hide();
	$('#pagerP74Motivo').hide();
	$('#p74_bloqueosCentralTitulo').hide();
	$('#gridP74Bloqueo').hide();
	$('#pagerP74Bloqueo').hide();

    $( "#p74_popupMotivosBloqueo" ).dialog( "open" );
	reloadDataP74_2(gridP74Motivo, gridP74Bloqueo, buscarPedidos);
}

function p74FechaFormatter(fecha) {
	
	var fechaFormateada = "";
	if (fecha != null){
		var diaFechaSt = fecha.substring(0,2);
		var mesFechaSt = fecha.substring(2,4);
		var anyoFechaSt = fecha.substring(4);
		var diaFecha = parseInt(diaFechaSt,10);
		var mesFecha = parseInt(mesFechaSt,10);
		var anyoFecha = parseInt(anyoFechaSt,10);
		var fechaCompleta = anyoFecha + "-" + mesFecha + "-" + diaFecha
		 
		fechaFormateada = $.datepicker.formatDate("D dd-M", devuelveDate(fechaCompleta),{
			dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
			dayNames: $.datepicker.regional[ "es" ].dayNames,
			monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
			monthNames: $.datepicker.regional[ "es" ].monthNames
		});
	}
	return fechaFormateada;
}

function p74Enlace(){

$("#p11_mensajeRecordatorioValidarCantidadesExtraEnlace").click(function () {
	loadDataPopUpP30('es', codArt);
});
	
	
}
