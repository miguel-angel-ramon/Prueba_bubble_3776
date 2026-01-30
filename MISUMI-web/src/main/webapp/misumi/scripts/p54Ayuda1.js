var gridP54Ayuda1=null;



function initializeScreenP54(){
	loadP54Ayuda(locale);
}

function reloadAyuda1() {
	$("#p53_pestanaAyuda1Cargada").val("S");
	
	//Calcular el título de la ayuda 1
	var sinOferta = $("#p60_fld_sinOferta").val();
	if (sinOferta != null && sinOferta == "S"){
		$("#p53_fld_pestanaAyuda1").text(literalAyuda1SinOferta);		
	}else{
		$("#p53_fld_pestanaAyuda1").text(literalAyuda1ConOferta);
	}

	loadP54Ayuda(locale);
	reloadDataP54Ayuda(gridP54Ayuda1);
}

function loadP54Ayuda(locale){
	gridP54Ayuda1 = new GridP54Ayuda1(locale);
	var jqxhr = $.getJSON(gridP54Ayuda1.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				gridP54Ayuda1.colNames = data.p54AyudaColNames;
				gridP54Ayuda1.title = data.p54AyudaGridTitle;
				gridP54Ayuda1.emptyRecords= data.emptyRecords;
				loadP54AyudaMock(gridP54Ayuda1);
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
         });
}

function loadP54AyudaMock(gridP54Ayuda1) {
		$(gridP54Ayuda1.nameJQuery).jqGrid({
			ajaxGridOptions : {
				contentType : 'application/json; charset=utf-8',
				cache : false
			},
			url : './misumi/resources/p54Ayuda1/p54ayuda1_' + locale + '.json',
			datatype : 'json',
			contentType : 'application/json',
			mtype : 'POST',
			colNames : gridP54Ayuda1.colNames,
			colModel :gridP54Ayuda1.cm,  
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
			emptyrecords : gridP54Ayuda1.emptyRecords,
			gridComplete : function() {
				
			},
			loadComplete : function(data) {
				gridP54Ayuda1.actualPage = data.page;
				gridP54Ayuda1.localData = data;
				gridP54Ayuda1.sortIndex = null;
				gridP54Ayuda1.sortOrder = null;
			
			},
			onSelectRow: function(id){

			},
			onPaging : function(postdata) {			
				alreadySorted = false;
				gridP54Ayuda1.sortIndex = null;
				gridP54Ayuda1.sortOrder = null;
				gridP54Ayuda1.saveColumnState.call($(this), this.p.remapColumns);
				reloadDataP54Ayuda(gridP54Ayuda1);
				return 'stop';
			},
			onSortCol : function (index, columnIndex, sortOrder){
				gridP54Ayuda1.sortIndex = index;
				gridP54Ayuda1.sortOrder = sortOrder;
				gridP54Ayuda1.saveColumnState.call($(this), this.p.remapColumns);
				reloadDataP54Ayuda(gridP54Ayuda1);
				return 'stop';
			},
			resizeStop: function () {
				gridP54Ayuda1.modificado = true;
				gridP54Ayuda1.saveColumnState.call($(this),grid.myColumnsState);
				jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), false);
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

function p54periodoFormatter(value, options, rData){ 
	
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

function reloadDataP54Ayuda(gridP54Ayuda1) {
	//var v_Oferta = new VOferta ('679','91838079','','','1');
	//var v_Oferta = new VOferta ('679','52498','','','1');
	var v_Oferta = new VOferta ($("#centerId").val(),$("#p60_fld_referencia").val(),'','',$("#p60_fld_tipoOferta").val());
	if (gridP54Ayuda1.firstLoad) {
		gridP54Ayuda1.firstLoad = false;
        if (gridP54Ayuda1.isColState) {
            $(this).jqGrid("remapColumns", gridP54Ayuda1.myColumnsState.permutation, true);
        }
    } else {
    	gridP54Ayuda1.myColumnsState = gridP54Ayuda1.restoreColumnState(gridP54Ayuda1.cm);
    	gridP54Ayuda1.isColState = typeof (gridP54Ayuda1.myColumnsState) !== 'undefined' && gridP54Ayuda1.myColumnsState !== null;
    }
	var objJson = $.toJSON(v_Oferta.prepareToJsonObject());

    $("#p54_AreaAyudaDatos.loading").css("display", "block");
    $("#p54_noData").hide();
	$.ajax({
		type : 'POST',			
		url : './pedidoAdicional/loadAyuda1.do?page='+gridP54Ayuda1.getActualPage()+'&max='+gridP54Ayuda1.getRowNumPerPage()+'&index='+gridP54Ayuda1.getSortIndex()+'&sortorder='+gridP54Ayuda1.getSortOrder(),
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		
			$(gridP54Ayuda1.nameJQuery)[0].addJSONData(data);
			gridP54Ayuda1.actualPage = data.page;
			gridP54Ayuda1.localData = data;
			$("#p54_AreaAyudaDatos .loading").css("display", "none");
			//if (data.total==0){// No hay ofertas --> no mostramos la ayuda
				//$(".p60_bloque3AreaDatosAyuda").css("display", "none");
			//}else {
				$(".p60_bloque3AreaDatosAyuda").css("display", "inherit");
				$("#p54_AreaAyudaDatos").css("height", $("#p54_AreaAyudaDatos .ui-jqgrid").css("height"));
			//}
				
				if (data.records == 0){
					$("#p54_noData").show();
				}
				var altoCampos = $(".p60_bloque3AreaDatosCampos").css("height");
				var altoAyuda = $(".p60_bloque3AreaDatosAyuda").css("height");
				var margenCampos = ((parseInt(altoAyuda, 10) - parseInt(altoCampos,10)) / 2) + 7; //7 es el margen amarillo
				//$(".p60_bloque3AreaDatosCampos").css("margin-top", margenCampos);
				
		},
		error : function (xhr, status, error){
			$("#p54_AreaAyudaDatos .loading").css("display", "none");	
			handleError(xhr, status, error, locale);				
		}			
	});	
	
}

//Clase de constantes para el GRID de ayuda
function GridP54Ayuda1 (locale){
	// Atributos
	this.name = "gridP54Ayuda1"; 
	this.nameJQuery = "#gridP54Ayuda1"; 
	this.i18nJSON = './misumi/resources/p54Ayuda1/p54ayuda1_' + locale + '.json';
	this.colNames = null;
	this.sortIndex = null;
	//los nombres aqui son los que deben de coincidir con los de la clase java
	this.cm =[
			   		   {	"name"  : "cOferta",
							"index" : "oferta",
							"sortable" : false,
							"width" : 63
						},{
							"name"  : "cPeriodo",
							"index" : "periodo",
							"sortable" : false,							
							"formatter" : p54periodoFormatter,
							"width" : 167
						},{							
							"name"  : "cPvp",
							"index" : "precio",
							"sortable" : false,							
							"formatter":p54FormateoPrecio,
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
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP54Ayuda1"; 
	this.pagerNameJQuery = "#pagerP54Ayuda1";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;
	this.myColumnsState = null;
	this.isColState = null;
	this.firstLoad = true;
	
	this.myColumnStateName = 'gridP54Ayuda1.colState';
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
        var colModel =jQuery(gridP54Ayuda1.nameJQuery).jqGrid('getGridParam', 'colModel'); 
        //if (colModel == null)
       //	 colModel = grid.cm;
        var i;
        var l = colModel.length;
        var colItem; 
        var cmName;
        var postData = jQuery(gridP54Ayuda1.nameJQuery).jqGrid('getGridParam', 'postData');
        var columnsState = {
                search: jQuery(gridP54Ayuda1.nameJQuery).jqGrid('getGridParam', 'search'),
                page: jQuery(gridP54Ayuda1.nameJQuery).jqGrid('getGridParam', 'page'),
                sortname: jQuery(gridP54Ayuda1.nameJQuery).jqGrid('getGridParam', 'sortname'),
                sortorder: jQuery(gridP54Ayuda1.nameJQuery).jqGrid('getGridParam', 'sortorder'),
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
            window.localStorage.setItem(gridP54Ayuda1.myColumnStateName, JSON.stringify(columnsState));
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

}

function diasFormatNumber(cellValue, opts, rData) {
	
	//Actualizamos el formateo del campo lmin y lsf
	return $.formatNumber(cellValue,{format:"0.000",locale:"es"});
}

function p54FormateoPrecio(cellValue, opts, rData) {
	
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