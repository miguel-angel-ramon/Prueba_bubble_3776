var gridP57Ayuda=null;

function initializeP57(){
	//Sólo para la Maquetación
	initializeScreenP57();
	loadP57Ayuda(locale);
}

function initializeScreenP57(){
	
}
function reloadAyudaPopup1() {
	$("#p56_pestanaAyuda1Cargada").val("S");
	loadP57Ayuda(locale);
	reloadDataP57Ayuda();
	
}

function loadP57Ayuda(locale){

	gridP57Ayuda = new GridP57Ayuda(locale);
	var jqxhr = $.getJSON(gridP57Ayuda.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				gridP57Ayuda.colNames = data.p57AyudaColNames;
				gridP57Ayuda.emptyRecords= data.emptyRecords;
				loadP57AyudaMock();
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
         });
}

function loadP57AyudaMock() {
		$(gridP57Ayuda.nameJQuery).jqGrid({
			ajaxGridOptions : {
				contentType : 'application/json; charset=utf-8',
				cache : false
			},
			datatype : 'local',
			contentType : 'application/json',
			mtype : 'POST',
			colNames : gridP57Ayuda.colNames,
			colModel :gridP57Ayuda.cm, 
			rowNum : 4,
			height : "auto",
			autowidth : true,
			width : "auto",
			rownumbers : false,
			viewrecords : true,
			caption : null,
			altclass: "ui-priority-secondary",
			altRows: true, //false, para que el grid no muestre cebrado
			hidegrid : false, //false, para ocultar el boton que colapsa el grid.
			sortable : false,
			emptyrecords : gridP57Ayuda.emptyRecords,
			gridComplete : function() {
				
				
			},
			loadComplete : function(data) {
				gridP57Ayuda.actualPage = data.page;
				gridP57Ayuda.localData = data;
				gridP57Ayuda.sortIndex = null;
				gridP57Ayuda.sortOrder = null;
			},
			onSelectRow: function(id){

			},
			onSortCol : function (index, columnIndex, sortOrder){
				reloadDataP57Ayuda(gridP57Ayuda);
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

function p57periodoFormatter(value, options, rData){ 
	
	var textoFechaInicio = rData['fechaIniPeriodo'];
	var textoFechaFin = rData['fechaFinPeriodo'];

	var fechaInicioFormateada = '';
	var fechaFinFormateada = '';
	if (textoFechaInicio != null)
	{
		var diaFechaInicio = parseInt(textoFechaInicio.substring(0,2),10);
		var mesFechaInicio = parseInt(textoFechaInicio.substring(2,4),10);
		var anyoFechaInicio = parseInt(textoFechaInicio.substring(4),10);
	
		fechaInicioFormateada = $.datepicker.formatDate("D dd/mm/yy", new Date(anyoFechaInicio, mesFechaInicio - 1, diaFechaInicio),{
			dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
			dayNames: $.datepicker.regional[ "es" ].dayNames,
			monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
			monthNames: $.datepicker.regional[ "es" ].monthNames
			});
	}

	if (textoFechaFin != null)
	{
		var diaFechaFin = parseInt(textoFechaFin.substring(0,2),10);
		var mesFechaFin = parseInt(textoFechaFin.substring(2,4),10);
		var anyoFechaFin = parseInt(textoFechaFin.substring(4),10);
	
		fechaFinFormateada = $.datepicker.formatDate("D dd/mm/yy", new Date(anyoFechaFin, mesFechaFin - 1, diaFechaFin),{
			dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
			dayNames: $.datepicker.regional[ "es" ].dayNames,
			monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
			monthNames: $.datepicker.regional[ "es" ].monthNames
			});
	}

	var textoFormateado = fechaInicioFormateada + " - " + fechaFinFormateada;
	
    return textoFormateado;
}

function reloadDataP57Ayuda() {
	//var v_Oferta = new VOferta ('679','91838079','','','1');
	//var v_Oferta = new VOferta ('679','52498','','','1');
	//var v_Oferta = new VOferta ($("#centerId").val(),$("#p52_fld_referencia").val(),'','',$("#p52_fld_tipoOferta").val());
	var objJson = $.toJSON(v_Oferta.prepareToJsonObject());
    $("#p57_AreaAyudaDatos.loading").css("display", "block");
    $("#p57_noData").hide();
	$.ajax({
		type : 'POST',			
		url : './nuevoPedidoAdicional/loadAyuda1.do?page='+gridP57Ayuda.getActualPage()+'&max='+gridP57Ayuda.getRowNumPerPage()+'&index='+gridP57Ayuda.getSortIndex()+'&sortorder='+gridP57Ayuda.getSortOrder(),
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {	
			$(gridP57Ayuda.nameJQuery)[0].addJSONData(data);
			gridP57Ayuda.actualPage = data.page;
			gridP57Ayuda.localData = data;
			$("#p57_AreaAyudaDatos .loading").css("display", "none");
				$("#p57_AreaAyudaDatos").css("height", $("#p57_AreaAyudaDatos .ui-jqgrid").css("height"));
				//alert(data.cantHoy);
				//alert(data.cantFutura);
			if (data.records == 0){
				$("#p57_noData").show();
			}
		},
		error : function (xhr, status, error){
			$("#p57_AreaAyudaDatos .loading").css("display", "none");
			handleError(xhr, status, error, locale);				
		},
		complete : function (xhr, status){
			
			//Petición 55001
			//La siguiente llamada ajax la he puesto después de terminar la anterior
			//por que parece que da error si las lanzamos a la vez.
			
			//pedido hoy y mañana
			
			if(v_Oferta.codArt){
				var pendientesRecibir = new PendientesRecibir ( 
						$("#centerId").val() , 
						v_Oferta.codArt ,
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
						$("#p57_cantidades_hoy").text(data.cantHoy);
						$("#p57_cantidades_mannana").text(data.cantFutura);
					},
					error : function (xhr, status, error){
						handleError(xhr, status, error, locale);				
					}			
				});			
			}
		},
	});
	
}
	

/*Clase de constantes para el GRID de ayuda*/
function GridP57Ayuda (locale){
	// Atributos
	this.name = "gridP57Ayuda1"; 
	this.nameJQuery = "#gridP57Ayuda1"; 
	this.i18nJSON = './misumi/resources/p57AyudaPopup1/p57ayudaPopup1_' + locale + '.json';
	this.colNames = null;
	this.cm =[
	   {	"name"  : "cOferta",
							"index" : "oferta",
							"sortable" : false,
							"width" : 63
						},{
							"name"  : "cPeriodo",
							"index" : "periodo",
							"sortable" : false,							
							"formatter" : p57periodoFormatter,
							"width" : 187
						},{							
							"name"  : "cPvp",
							"index" : "precio",
							"sortable" : false,							
							"formatter":p57FormateoPrecio,
							"width" : 47
						},						{							
							"name"  : "cVD1",
							"index" : "dia1", 
							"sortable" : false,							
							"formatter":diasFormatNumber,
							"width" : 55
						},						{							
							"name"  : "cVD2",
							"index" : "dia2",
							"sortable" : false,							
							"formatter":diasFormatNumber,
							"width" : 55
						},						{							
							"name"  : "cVD3",
							"index" : "dia3", 
							"sortable" : false,							
							"formatter":diasFormatNumber,
							"width" : 55
						},						{							
							"name"  : "totalVentas",
							"index" : "totalVentas", 
							"sortable" : false,							
							"formatter":diasFormatNumber,
							"width" : 62
						}
	];
	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;
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
}

function diasFormatNumber(cellValue, opts, rData) {
	
	//Actualizamos el formateo del campo lmin y lsf
	return $.formatNumber(cellValue,{format:"0.000",locale:"es"});
}

function p57FormateoPrecio(cellValue, opts, rData) {
	
	if (cellValue != '')
	{
		//Actualizamos el formateo de los campos a tres decimales
		return $.formatNumber(cellValue,{format:"0.00",locale:"es"});
	}
	else
	{
		return '';
	}
}