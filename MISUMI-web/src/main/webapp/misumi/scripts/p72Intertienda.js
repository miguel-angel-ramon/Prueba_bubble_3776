var RESET_CONST="reset";
var emptyRecords=null;
var gridEmptyRecords=null;
var recordText=null;
var optionActivaVacia=null;
var optionActivaTodas=null;
var gridResul=null;
var referenciaRequired=null;
var centroRequired=null;
var regionRequired=null;
var zonaRequired=null;
var provinciaRequired=null;
var centroRegionZonaProvinciaRequired=null;
var errorStockActual=null;
var tableFilter=null;
var areaOn=[];


var centroRelacionado;
var defCentroRelacionado;

$(document).ready(function(){
	$(document).on('CargarScriptsPantallas', function(e) { 
		loadP72(locale);
		initializeScreen();
	
	});
	
	$(document).on('CargadoCentro', function(e) { 
		$("#p72_fld_referencia").focus();
	});
	
	initializeScreenComponentsP72();
	
});

function initializeScreenComponentsP72(){
	$("#p72_cmb_region").combobox(null);
	$("#p72_cmb_zona").combobox(null);
	CargadaEstructuraPantalla(2);
}

function initializeScreen(){
	initializeReferencia();
	initializeCentroRegionZonaProvincia();
	resetReferencia();
	resetCentroRegionZonaProvincia();
	resetResultado();
	events_p72_fld_referencia();
	events_p72_fld_centro();
	events_p72_btn_exportExcel();
    controlCentro();
	load_cmbRegion();

	//Inicializamos el mapa de Espana para que se destaquen las provincias
	//cuando pasamos el ratón por encima de ellas
	events_p72_espMisumi();
	
	//Buscamos el centro relacionado
	findCentroRelacionadoIntertienda();
}

function initializeReferencia(){
	//Inicializamos apartado 1) Referencia
	//$("#p72_fld_referencia").filter_input({regex:'[0-9]'});
	$("#p72_fld_referencia").focus();
	$("#p72_fld_denominacion").attr("disabled", "disabled");
}

function initializeCentroRegionZonaProvincia(){
	//Inicializamos apartado 2) Centro o Región/Zona o Provincia
	p72_fld_centro_AutoComplete();
	$("#p72_fld_centro").attr("disabled", "disabled");
	$("#p72_cmb_region").combobox(null);
	$("#p72_cmb_region").combobox("disable");
	$("#p72_cmb_zona").combobox(null);
	$("#p72_cmb_zona").combobox("disable");
	$("#mapaEspana").attr("usemap", "");
	$('.mapEsp').maphilight();
}

function resetReferencia(){
	//Reseteamos apartado 1) Referencia
	$("#p72_fld_referencia").val('');
	$("#p72_fld_denominacion").val('');
}

function resetDatosBusqueda(){
	$("#p72_fld_denominacion").val("");
	$('#gridP72').jqGrid('clearGridData');
	jQuery(gridResul.nameJQuery).jqGrid('hideCol', ["descRegion","descZona"]);
	jQuery(gridResul.nameJQuery).jqGrid('setGridWidth', $("#p72_Resultado").width(), true);
}

function resetCentroRegionZonaProvincia(){
	
	$("#p72_fld_centro").val("");
	$("#p72_fld_centroId").val("");
	//$("select#p72_cmb_region").html("<option value=null selected='selected'>"+''+"</option>");
	$("#p72_cmb_region").combobox('autocomplete','');
	$("#p72_cmb_region").combobox('comboautocomplete',null);
	$("select#p72_cmb_zona").html("<option value=null selected='selected'>"+''+"</option>");
	$("#p72_cmb_zona").combobox('autocomplete','');
	$("#p72_cmb_zona").combobox('comboautocomplete',null);
	$("#mapaEspana").attr("usemap", "");
	areaOnOff(areaOn);
	areaOn = [];
	$("#p72_fld_provincia").val("");
}

function controlCentro(){
	$( "#centerName" ).bind('focus', function() {
		resetResultado();
		resetReferencia();
		resetCentroRegionZonaProvincia();
    });
}

function resetResultado(){
	//Reseteamos apartado 3) Resultado
	$('#gridP72').jqGrid('clearGridData');
}

function disableScreen(){
	$("#p72_capaTransparente").css("z-index", "99");
}

function enableScreen(){
	$("#p72_capaTransparente").css("z-index", "-1");
}

function events_p72_espMisumi(){
	$('#p72_espMisumi area').click(function(e) {
		
		var txtBusqueda=$.trim($("#p72_fld_referencia").val());
		var messageVal=findReferenciaValidation(txtBusqueda);
		var alfaNumerico=null;
		var ean=null;

		$("#p72_fld_referencia").val(txtBusqueda);
		
		if (messageVal==null){
			e.preventDefault();
			areaOnOff(areaOn);
			areaOn = [];
			areaOn = $("#p72_espMisumi").find("area[data-prov='"+$(this).data("prov")+"']");
			areaOnOff(areaOn);
				
			// Añado gestion de foco en provincia
			borrarSeleccionAnterior("");
			load_provincia($(this).data("prov"));
		}else{
			$("#p03_btn_aceptar").bind("click", function(e) {
				$("#p01_txt_infoRef").focus();
				$("#p01_txt_infoRef").select();
				e.stopPropagation();
			});
			//Reseteamos el campo denominación y borramos la tabla del GRID si estuviera cargada.
			resetDatosBusqueda();
			
			createAlert(replaceSpecialCharacters(messageVal), "ERROR");	
		}		
	});
}

function events_p72_btn_exportExcel(){
	$("#p72_btn_Excel").click(function () {
		exportExcel72();
	});
}

function areaOnOff(areas){
	if (areas){
		$.each(areas, function() {
			var data = $(this).mouseout().data('maphilight') || {};
			data.alwaysOn = !data.alwaysOn;
			$(this).data('maphilight', data).trigger('alwaysOn.maphilight');
		});
	}
}

function setHeadersTitles(data){
	
   	var colModel = $(gridResul.nameJQuery).jqGrid("getGridParam", "colModel");
    $.each(colModel, function(i) {
    	if (colModel[i].name!="rn"){
	    	$("#jqgh_gridP72_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
    	}
     });
}

function loadP72(locale){
	
	this.i18nJSON = './misumi/resources/p72Intertienda/p72intertienda_' + locale + '.json';
	
	gridResul = new GridP72(locale);
	
	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				gridResul.colNames = data.p72ColNames;
				gridResul.title = data.p72GridTitle;
				gridResul.emptyRecords = data.emptyRecords;
				emptyRecords = data.emptyRecords;
				gridEmptyRecords= data.emptyRecords;
				recordText = data.recordText;
				optionActivaVacia = data.optionActivaVacia;
				optionActivaTodas = data.optionActivaTodas;
				referenciaRequired = data.referenciaRequired;
				centroRequired = data.centroRequired;
				regionRequired = data.regionRequired;
				zonaRequired = data.zonaRequired;
				provinciaRequired = data.provinciaRequired;
				centroRegionZonaProvinciaRequired = data.centroRegionZonaProvinciaRequired;
				errorStockActual = data.errorStockActual;
				tableFilter = data.tableFilter;
				if  ($("#p72_fld_referencia").val()!=""){
					finder();
				}
				loadP72Mock(gridResul);
				setHeadersTitles(data);
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
           });
}

function events_p72_fld_referencia(){
	$( "#p72_fld_referencia" ).bind('focus', function() {
	//	resetReferencia();
	//	resetCentroRegionZonaProvincia();
	//	resetResultado();
    });
	$("#p72_fld_referencia").bind('keydown',function(e) {
		var key = e.which; //Nos da el número de botón pulsado del raton o el número de tecla pulsado del teclado
 	    if (key == 13){
 	    	e.preventDefault();
	 	   	if($("#p72_fld_referencia").val()!=null && $("#p72_fld_referencia").val()!='' ){
	 	   		findReferenciaP72();
	 		}
 	    }
 	    if (key == 9){
	 	   	if($("#p72_fld_referencia").val()!=null && $("#p72_fld_referencia").val()!='' ){
	 	   		findReferenciaP72();
	 		}
 	    }
	});
}

function findReferenciaP72(){
	var txtBusqueda=$.trim($("#p72_fld_referencia").val());

	var messageVal=findReferenciaValidation(txtBusqueda);
	var alfaNumerico=null;
	var ean=null;

	if (messageVal==null){		
		//Si se trata de una busqueda alfanumerica o alfabetica
		if (!isInt(txtBusqueda)){
			//se buscará como MODELO PROVEEDOR
			//En el caso que no eista MODELO PROVEEDOR si no hay registros como DENOMINACION
			alfaNumerico = true;
			ean = false;
			reloadDataP34(txtBusqueda, "intertienda","", alfaNumerico, ean);
		}else{
			//Si la REFERENCIA mayor de 8 digitos se busca como EAN, 
			//Si no hay registros como EAN se buscará como MODELO PROVEEDOR
			//En el caso que no eista MODELO PROVEEDOR si no hay registros como DENOMINACION
			alfaNumerico = false;
			if(txtBusqueda.length > 8){
				ean = true;
				reloadDataP34(txtBusqueda, "intertienda", "", alfaNumerico, ean);
			}else{
				ean = false;
				reloadDataP34(txtBusqueda, "intertienda", "", alfaNumerico, ean);
			}
		}
	}else{		
		$("#p03_btn_aceptar").bind("click", function(e) {
			$("#p01_txt_infoRef").focus();
			$("#p01_txt_infoRef").select();
			e.stopPropagation();

		});
		//Reseteamos el campo denominación y borramos la tabla del GRID si estuviera cargada.
		resetDatosBusqueda();
		
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");	
	}

}

function events_p72_fld_centro(){
	$( "#p72_fld_centro" ).bind('click', function() {
		$("#p72_fld_centro").val('');
        $("#p72_fld_centroId").val('');
    });
}

function finder(){
	var messageVal=findValidation();
	
	if (messageVal!=null){
		$('#gridP72').jqGrid('clearGridData');
		jQuery(gridResul.nameJQuery).jqGrid('hideCol', ["descRegion","descZona"]);
		jQuery(gridResul.nameJQuery).jqGrid('setGridWidth', $("#p72_Resultado").width(), true);
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		$('#gridP72').setGridParam({page:1,sortname:"",sortorder:""});
		$("span.s-ico").hide();
		reloadData();
	}
}

function loadReferencia() {
	if ($("#centerId").val()==null || $("#centerId").val()==""){
		$("#infoRef").show();
		createAlert(centroRequired,"ERROR");
	} else {
		//Reseteamos el campo denominación
		$("#p72_fld_denominacion").val("");
			
		resetCentroRegionZonaProvincia();
		resetResultado();
		//$("#p72_btn_Excel").css("display","none");
		$("#p72_btn_Excel").attr("disabled", "disabled");
		
		var valueReferencia = $("#p72_fld_referencia").val();
		var valueCentro = "";
		var vReferenciasCentro=new ReferenciasCentro(valueReferencia, valueCentro);
		var objJson = $.toJSON(vReferenciasCentro);	
		$.ajax({
			type : 'POST',
			url : './intertienda/loadReferencia.do',
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			cache : false,
			success : function(data) {	
				if (validateReloading(data)){
					//Actualización campo de la denominación
					$("#p72_fld_denominacion").val(data.descripArt);
					
					//Comento la inicializacion anterior del field de seleccion
					habilitarFieldSetSeleccion();
					
				}else{
					// Uso inicializacion anterior para resetear todo
					initializeCentroRegionZonaProvincia();
					
					var messageVal=null;
					messageVal = emptyRecords;
					if (messageVal!=null){
						createAlert(replaceSpecialCharacters(messageVal), "ERROR");
					}
				}	
				$("#infoRef").show();
			},
			error : function (xhr, status, error){
				handleError(xhr, status, error, locale);
	        }			
		});		
	}
	
}

function validateReloading(data){
	if (data.length == 0 || data.codArt == null || data.codArt == ''){
		return false;
	}else{
		return true;
	}
}

function p72_fld_centro_AutoComplete(){
	var cache = {};
	$( "#p72_fld_centro" ).autocomplete({
		minLength: 1,
		mustMatch:true,
		source: function( request, response ) {
			var term = request.term;

			$.ajax({
				url: "./welcome/loadCentrosPattern.do?term="+ request.term,
				dataType: "json",
				cache : false,

				success: function(data) {
					cache[ term ] = data;
					response($.map(data, function(item) {
						return {
							label: item.codCentro  +"-" +item.descripCentro  ,
							id: item.codCentro ,
							abbrev: item.codCentro
						};
					}));
				}
			});
		},
           
		select: function(event, ui) {
            $("#p72_fld_centroId").val(ui.item.id);
			this.close;
			borrarSeleccionAnterior( $(this).attr("id"));
            finder();
		},
		change: function (event, ui) {
			if (!ui.item) {
				$("#p72_fld_centroId").val('');
                this.value = '';
			}else{
				$("#p72_fld_centroId").val(ui.item.id);
			}
		}
	});
	$('#p72_fld_centro').css('width', '300px');
	
}

function load_cmbRegion(){	
	
	var options = "";
	var region=new Region(null, null, null, null, null);
	var objJson = $.toJSON(region);	
	 $.ajax({
		type : 'POST',
		url : './intertienda/loadListaRegion.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			   for (i = 0; i < data.length; i++){
					options = options + "<option value='" + data[i].codRegion + "'>" + data[i].descripcion + "</option>"; 
			   }
			   $("select#p72_cmb_region").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
	$("#p72_cmb_region").combobox({
        selected: function(event, ui) {
        	if ( ui.item.value!="" && ui.item.value!="null" ) {
        		if ($("#p72_cmb_region").val()!=null){
					$("#p72_cmb_region").combobox('autocomplete',ui.item.value);
					$("#p72_cmb_region").combobox('comboautocomplete',ui.item.value);
        			$("#p72_cmb_zona").combobox("enable");
        			load_cmbZona();
        			//cuando seleccione en zona reseteo centro y provincia
        			borrarSeleccionAnterior( $(this).attr("id"));
        		}
        	}else{
        		$("#p72_cmb_zona").combobox(null);
        		$("#p72_cmb_zona").combobox("disable");
        	}
          
        }
    }); 
	
	//Después de cargar el combo lo inicializamos a vacio 
	$("#p72_cmb_region").combobox('autocomplete',optionActivaVacia);
	$("#p72_cmb_region").combobox('comboautocomplete',null);

}

function load_cmbZona(){
	var options = "";
	$("#p72_cmb_zona").combobox(null);
	var codRegion = $("#p72_cmb_region").combobox('getValue');
	var centro=new Centro(null, null, null, null, null, null, codRegion, null, null, null);
	var objJson = $.toJSON(centro);	
	 $.ajax({
		type : 'POST',
		url : './intertienda/loadListaZona.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {			
			options = options + "<option value='" + optionActivaTodas + "'>" + optionActivaTodas + "</option>"; 
			for (i = 0; i < data.length; i++){
				options = options + "<option value='" + data[i].codZona + "'>" + data[i].descripZona + "</option>"; 
			}
			$("select#p72_cmb_zona").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
		$("#p72_cmb_zona").combobox({
			selected: function(event, ui) {
				if ( ui.item.value!="" && ui.item.value!="null") {
					if ($("#p72_cmb_zona").val()!=null){
						$("#p72_cmb_zona").combobox('autocomplete',ui.item.value);
						$("#p72_cmb_zona").combobox('comboautocomplete',ui.item.value);
						
						//cuando seleccione en zona reseteo centro y provincia
						borrarSeleccionAnterior( $(this).attr("id"));
						finder();
					} 
				} 
			}
	     });
	
		//Después de cargar el combo lo inicializamos a vacio 
	$("#p72_cmb_zona").combobox('autocomplete',optionActivaVacia);
	$("#p72_cmb_zona").combobox('comboautocomplete',null);
}

function load_provincia(strProvincia){
	$("#p72_fld_provincia").val(strProvincia);
    finder();
}

function reloadData() {
	//Inicializamos el número de fila para los campos ocultos que tenemos.
	numRow=1;
	var messageVal=findValidation();
	if (messageVal!=null){
		$('#gridP72').jqGrid('clearGridData');
		jQuery(gridResul.nameJQuery).jqGrid('hideCol', ["descRegion","descZona"]);
		jQuery(gridResul.nameJQuery).jqGrid('setGridWidth', $("#p72_Resultado").width(), true);
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		var codReferencia = null;
		var codCentro = null;
		var codRegion = null;
		var codZona = null;
		var provincia = null;
		
		//Setteo de parametros para la carga del grid
		 
		// Referencia
		if ($("#p72_fld_referencia").val() != null
				&& $("#p72_fld_referencia").val() != "") {
			codReferencia = $("#p72_fld_referencia").val();
		}

		// Se ha seleccionado Centro
		if ($("#p72_fld_centroId").val() != null
				&& $("#p72_fld_centroId").val() != "") {
			codCentro = $("#p72_fld_centroId").val();
		}

		else {
			// Se ha seleccionado Región / Zona
			if ($("#p72_cmb_region").combobox('getValue') != "null"
					&& $("#p72_cmb_region").combobox('getValue') != null) {
				codRegion = $("#p72_cmb_region").combobox('getValue');

				if ($("#p72_cmb_zona").combobox('getValue') != "null"
						&& $("#p72_cmb_zona").combobox('getValue') != null) {
					codZona = $("#p72_cmb_zona").combobox('getValue');
					if (codZona == optionActivaTodas) {
						codZona = null;
					}
				}
			}

			// Se ha seleccionado Provincia
			else if ($("#p72_fld_provincia").val() != null
					&& $("#p72_fld_provincia").val() != "") {
				provincia = $("#p72_fld_provincia").val();
			}

		}
		
		var recordIntertienda = new Intertienda (codReferencia, codCentro, null, codRegion, null, codZona, null, null, provincia, null, null, null, null, false, null);
		
		var objJson = $.toJSON(recordIntertienda.prepareToJsonObject());
	
			$("#AreaResultados .loading").css("display", "block");
			disableScreen();
			if (gridResul.firstLoad) {
				jQuery(gridResul.nameJQuery).jqGrid('setGridWidth', $("#p72_Resultado").width(), true);
		        gridResul.firstLoad = false;
		        if (gridResul.isColState) {
		            $(this).jqGrid("remapColumns", gridResul.myColumnsState.permutation, true);
		        }
		    } else {
		    	gridResul.myColumnsState = gridResul.restoreColumnState(gridResul.cm);
		    	gridResul.isColState = typeof (gridResul.myColumnsState) !== 'undefined' && gridResul.myColumnsState !== null;
		    }
			$.ajax({
				type : 'POST',
				url : './intertienda/loadDataGrid.do?page='+gridResul.getActualPage()+'&max='+gridResul.getRowNumPerPage()+'&index='+gridResul.getSortIndex()+'&sortorder='+gridResul.getSortOrder(),
				data : objJson,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {
					$(gridResul.nameJQuery)[0].addJSONData(data);
					gridResul.actualPage = data.page;
					gridResul.localData = data;
					var ids = jQuery(gridResul.nameJQuery).jqGrid('getDataIDs'), i, l = ids.length;
					
					$("#p72_fld_referencia").focus();
					$("#p72_fld_referencia").select();
					
					$("#AreaResultados").hide();
					var cont = 0;
				    for (i = 0; i < l; i++) {
				    	
			    		jQuery(gridResul.nameJQuery).jqGrid('editRow', ids[i], false);
			    		
				    	cont++;
				    }
					$("#AreaResultados").show();
					
					enableScreen();
					$("#AreaResultados .loading").css("display", "none");	
					if (data.records == null || data.records == 0){
						createAlert(replaceSpecialCharacters(gridEmptyRecords), "ERROR");
					} 
					
					//$("#p72_btn_Excel").css("display","block");
					$("#p72_btn_Excel").removeAttr("disabled");
					
				},
				error : function (xhr, status, error){
					enableScreen();
					$("#AreaResultados .loading").css("display", "none");	
					handleError(xhr, status, error, locale);				
		        }			
			});		
	}
}

function GridP72 (locale){
	// Atributos
	this.name = "gridP72";
	this.nameJQuery = "#gridP72";
	this.colNames = null;
	this.cm = [	{
		"name" : "descRegion",
		"index":"descRegion",
		"width" : 25,
		"formatter": function(value, options, rData){ return rData['codRegion'] + '-' + value  ;  },
		"sortable":true,
	    "hidden" : true
	},{
		"name" : "descZona",
		"index":"descZona",
		"width" : 25,
		"formatter": function(value, options, rData){ return rData['codZona'] + '-' + value  ;  },
		"sortable":true,
	    "hidden" : true 
	},{
		"name" : "descCentro",
		"index":"descCentro", 
		"formatter": function(value, options, rData){ return rData['codCentro'] + '-' + value  ;  },
		"sortable":true,
		"width" : 60
	},{
		"name" : "stock",
		"index":"stock",
		"formatter":formatMessageStock,
		"sortable":false,
		"width" : 20
	},{
		"name" : "ventaMedia",
		"index":"ventaMedia", 
		"formatter":"number",
		"sortable":true,
		"width" : 20
	}			
	]; 
	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP72"; 
	this.pagerNameJQuery = "#pagerP72";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;
	this.myColumnStateName = 'gridP72.colState';
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
		return $(this.nameJQuery).getGridParam('rowNum');
	} 
	
	this.getSortIndex = function getSortIndex () {
		if ($(this.nameJQuery).getGridParam('sortname')!=null){
			return $(this.nameJQuery).getGridParam('sortname');
		}else{
			return "";
		}
	} 
	 
	this.getSortOrder = function getSortOrder () {
		
		if ($(this.nameJQuery).getGridParam('sortorder')!=null){
			return $(this.nameJQuery).getGridParam('sortorder');
		}else{
			return "";
		}
	} 	
	
	this.headerHeight = function headerHeight(cssClass) {
		var colModel = $(this.nameJQuery).jqGrid('getGridParam','colModel');
		for (i = 0; i < this.colNames.length; i++){
			$(this.nameJQuery).setLabel(colModel[i].name, '', cssClass);
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
        var colModel = jQuery(gridResul.nameJQuery).jqGrid('getGridParam', 'colModel'); 
        var i;
        var l = colModel.length;
        var colItem; 
        var cmName;
        var postData = jQuery(gridResul.nameJQuery).jqGrid('getGridParam', 'postData');
        var columnsState = {
                search: jQuery(gridResul.nameJQuery).jqGrid('getGridParam', 'search'),
                page: jQuery(gridResul.nameJQuery).jqGrid('getGridParam', 'page'),
                sortname: jQuery(gridResul.nameJQuery).jqGrid('getGridParam', 'sortname'),
                sortorder: jQuery(gridResul.nameJQuery).jqGrid('getGridParam', 'sortorder'),
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
            window.localStorage.setItem(gridResul.myColumnStateName, JSON.stringify(columnsState));
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

function loadP72Mock(grid) {
	//para hacer cabeceras
		$(grid.nameJQuery).jqGrid({
			ajaxGridOptions : {
				contentType : 'application/json; charset=utf-8',
				cache : false
			},
			datatype : 'local',
			contentType : 'application/json',
			mtype : 'POST',
//			headertitles: true ,
			colNames : grid.colNames,
			colModel : grid.cm,
			rowNum : 10,
			rowList : [ 10, 15, 20 ],
			height : "100%",
			autowidth : true,
			width : "auto",
			rownumbers : true,
			autoencode:true,
			shrinkToFit:true,
			pager : grid.pagerNameJQuery,
			viewrecords : true,
			caption : grid.title,
			altclass: "ui-priority-secondary",
			altRows: true, //false, para que el grid no muestre cebrado
			hidegrid : false, //false, para ocultar el boton que colapsa el grid.
			sortable : true,
			index: grid.sortIndex,
			sortname: grid.sortIndex,
			sortorder: grid.sortOrder,
			emptyrecords : grid.emptyRecords,
			recordtext : recordText,
			gridComplete : function() {
				//grid.headerHeight("gridP72Header");  
			},
			loadComplete : function(data) {
				grid.actualPage = data.page;
				grid.localData = data;
				grid.sortIndex = null;
				grid.sortOrder = null;
				$("#AreaResultados .loading").css("display", "none");	
				if (grid.firstLoad)
					jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#p72_Resultado").width(), true);
			},
			onPaging : function(postdata) {			
				alreadySorted = false;
				grid.sortIndex = null;
				grid.sortOrder = null;
				grid.saveColumnState.call($(this), this.p.remapColumns);
				reloadData();
				return 'stop';
			},
			onSelectRow: function(id){

			},
			resizeStop: function () {
				grid.modificado = true;
				grid.saveColumnState.call($(this),grid.myColumnsState);
				jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#p72_Resultado").width(), false);
			},
			onSortCol : function (index, columnIndex, sortOrder){
				grid.sortIndex = index;
				grid.sortOrder = sortOrder;
				grid.saveColumnState.call($(this), this.p.remapColumns);
				reloadData();
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
		jQuery(grid.nameJQuery).jqGrid('navGrid',grid.pagerNameJQuery,{
				add:false,edit:false,del:false,search:false,refresh:false}
		); 
		jQuery(grid.nameJQuery).jqGrid('navButtonAdd',grid.pagerNameJQuery,{ 
				caption: tableFilter, title: "Reordenar Columnas", 
			onClickButton : function (){ 
				jQuery(grid.nameJQuery).jqGrid('columnChooser',
						{
                    		"done": function(perm) {
                    			if (perm) {
                    				var autowidth = true;
                    				if (grid.modificado == true){
                    					autowidth = false;
                    					this.jqGrid("remapColumns", perm, true);
                    					grid.myColumnsState =  grid.restoreColumnState(grid.cm);
                        		    	grid.isColState = typeof (grid.myColumnsState) !== 'undefined' && grid.myColumnsState !== null;
                        		    	
                        		    	 var colModel =jQuery(grid.nameJQuery).jqGrid('getGridParam', 'colModel'); 
                        		    	 var l = colModel.length;
                        		         var colItem; 
                        		         var cmName;
                        		         var colStates = grid.myColumnsState.colStates;
                        		         var cIndex = 2;
                        		         for (i = 0; i < l; i++) {
                        		             colItem = colModel[i];
                        		             cmName = colItem.name;
                        		             if (colItem.hidden !== true && cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {
                        		            	 
                        		            	 jQuery(grid.nameJQuery).jqGrid('setColProp',cmName,{width:colStates[cmName].width});
                        		            	 var cad =grid.nameJQuery+'_'+cmName;
                        		            	 var ancho = "'"+colStates[cmName].width+"px'";
                        		            	 var cell = jQuery('table'+grid.nameJQuery+' tr.jqgfirstrow td:nth-child(' + (i+1) + ')');
                        		            	 cell.css("width", colStates[cmName].width + "px");
                        		            	
                        		            	 jQuery(cad).css("width", colStates[cmName].width + "px");
                        		            	
                        		             }
                        		         }
                        		         
                    				} else {
                    					this.jqGrid("remapColumns", perm, true);
                    				}
                    				//grid.saveColumnState.call(this, perm);
                    				jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#p72_Resultado").width(), autowidth);
                    				grid.saveColumnState.call(this, perm);
                    			}
                    		}
						}		
				); } });
		
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descRegion"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descZona"]);
}

function formatMessageStock(cellValue, opts, rData) {
	
	//Controlamos los posibles errores que me lleguen para pintar el icono correspondiente.
	if (rData['descError'] == 'stock'){
		//Pintamos el icono de que se ha producido un error
		return "<img src='./misumi/images/dialog-cancel-14.png' style='padding: 2px 0px 0px 2px' title='"+errorStockActual+"' />"; //Error
	}
	else{
		return $.formatNumber(rData['stock'],{format:"0.00",locale:"es"});
	}
}

function exportExcel72(){

	var colModel = $(gridResul.nameJQuery).jqGrid("getGridParam", "colModel");
    var colNames = $(gridResul.nameJQuery).jqGrid("getGridParam", "colNames");
    var myColumns = new Array();
    var myColumnsNames = new Array();
    var j=0;
  
    $.each(colModel, function(i) {
    	if (colModel[i].name!="rn" && colModel[i].name!="cb" && colModel[i].name!="mensaje" && !colModel[i].hidden ){
    		myColumnsNames[j]=colNames[i];
	    	myColumns[j]=colModel[i].name;
	    	j++;	
	    	
    	}
     });
    var tipoB=null;
    
    
   //Nueva gestion para el tipo de busqueda
	var codReferencia = null;
	var descRef = null
	var centro = null;
	var region = null;
	var zona = null;
	var provincia = null;
	
	// Referencia
	if ($("#p72_fld_referencia").val()!=null || $("#p72_fld_referencia").val()!=""){
		codReferencia = $("#p72_fld_referencia").val();
		descRef= $('#p72_fld_denominacion').val();
	}

	// Se ha seleccionado Centro
	if ($("#p72_fld_centro").val()!=null && $("#p72_fld_centro").val()!=""){
		centro = $("#p72_fld_centro").val();
	}
	else{
		// Se ha seleccionado Región / Zona
		if( $("#p72_cmb_region").combobox('getValue')!="null" && $("#p72_cmb_region").combobox('getValue')!=null){
			region =$('#p72_cmb_region option:selected').val()+"-"+$('#p72_cmb_region option:selected').text();
		
			if( $("#p72_cmb_zona").combobox('getValue')!="null" && $("#p72_cmb_zona").combobox('getValue')!=null){
				zona = $('#p72_cmb_zona option:selected').text()
				if (zona == optionActivaTodas){
					zona = null;
				} else {
					zona = $('#p72_cmb_zona option:selected').val()+"-"+$('#p72_cmb_zona option:selected').text()
				}
			}
		}
		// Se ha seleccionado Provincia
		else if ($("#p72_fld_provincia").val()!=null || $("#p72_fld_provincia").val()!=""){
			provincia = $("#p72_fld_provincia").val();
			
		}
	}
	
	var messageVal=findValidation();
	if (messageVal!=null){
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
	
		 var form = "<form name='exportIntertiendaform' action='./intertienda/exportGrid.do'  accept-charset='ISO-8859-1' method='get'>";
		 form = form + "<input type='hidden' name='headers' value='"+myColumnsNames+"'>";		
		 form = form + "<input type='hidden' name='model' value='"+myColumns+"'>";
		 form = form + "<input type='hidden' name='page' value='"+gridResul.getActualPage()+"'>";
		 form = form + "<input type='hidden' name='max' value='"+gridResul.getRowNumPerPage()+"'>";
		 form = form + "<input type='hidden' name='index' value='"+gridResul.getSortIndex()+"'>";
		 form = form + "<input type='hidden' name='sortorder' value='"+gridResul.getSortOrder()+"'>";
		 form = form + "<input type='hidden' name='codReferencia' value='"+codReferencia+"'>";
		 form = form + "<input type='hidden' name='descRef' value='"+descRef+"'>";
		 
		 if (null != centro){
			 form = form + "<input type='hidden' name='centro' value='"+centro+"'>";
		 }
		 
		 if (null != region){
			 form = form + "<input type='hidden' name='region' value='"+region+"'>";
		 }
		
		 if (null != zona){
			 form = form + "<input type='hidden' name='zona' value='"+zona+"'>";
		 }
		 if (null != provincia){
			 form = form + "<input type='hidden' name='provincia' value='"+provincia+"'>";
		 }
		 form = form + "<input type='hidden' name='tipoB' value='"+tipoB+"'>";
		 form = form + "</form><script>document.exportIntertiendaform.submit();</script>";
		 Show_Popup(form);
	}
    
}


/**
 * P-51228
 * Prescindimos de los radio buttons pudiendo realizar cualquier tipo de busqueda sin seleccion previa.
 * @author BICUGUAL
 */
//Inicializar fieldset de seleccion
function habilitarFieldSetSeleccion(){
	
	//Centro
	$("#p72_fld_centro").removeAttr('disabled');
	
	if(centroRelacionado != null && centroRelacionado != "" && centroRelacionado != undefined){
		//Rellenar intertienda.
		$("#p72_fld_centro").val(centroRelacionado +"-" + defCentroRelacionado);
		$("#p72_fld_centroId").val(centroRelacionado);
		reloadData();
	}else{
		//Si no existe centro relacionado, se pone el foco en el centro, para que el usuario introduzca el que quiera.
		$("#p72_fld_centro").focus();
	}
	
	//Region
	$("#p72_cmb_region").combobox("enable");
	$("#p72_cmb_region").combobox('autocomplete',optionActivaVacia);
	$("#p72_cmb_region").combobox('comboautocomplete',null);
	$("#p72_cmb_zona").combobox(null);
	$("#p72_cmb_zona").combobox("disable");
	
	//Provincia
	$("#mapaEspana").attr("usemap", "#espMisumi");
	$('.mapEsp').maphilight();
	
}

function findCentroRelacionadoIntertienda(){
	centroRelacionado = null;
	defCentroRelacionado = null;
	$.ajax({
		type : 'GET',
		url : './intertienda/findCentroRelacionadoIntertienda.do',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {
			//Si existe el centro relacionado, rellenamos el campo con ese centro.
			if(data != null){
				loadDefCentroIntertienda(data);
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
}

function loadDefCentroIntertienda(codCentro){
	$.ajax({
		url: "./welcome/loadCentrosPattern.do?term="+ codCentro,
		dataType: "json",
		cache : false,
		success: function(data) {
			//Guardamos el centro y su definición en variables globales.
			centroRelacionado = data[0].codCentro;
			defCentroRelacionado = data[0].descripCentro; 
		}
	});
}

//Validacion de seleccion
function findValidation(){
	var messageVal=null;
	
	if ($("#centerId").val()==null || $("#centerId").val()==""){
		messageVal = centroRequired;
	} else if ($("#p72_fld_referencia").val()==null || $("#p72_fld_referencia").val()==""){
		messageVal = referenciaRequired;
	} else {
		if ($("#p72_fld_centroId").val()!=null && $("#p72_fld_centroId").val()!=""){
			return messageVal;
		} else if($("#p72_cmb_region").combobox('getValue')!="null" && $("#p72_cmb_region").combobox('getValue')!=null){
			if( $("#p72_cmb_zona").combobox('getValue')!="null" && $("#p72_cmb_zona").combobox('getValue')!=null){
				return messageVal;
			} else{
				messageVal = zonaRequired;
			}
		} else if ($("#p72_fld_provincia").val()!=null && $("#p72_fld_provincia").val()!=""){
			return messageVal;
		} else{
			messageVal = centroRegionZonaProvinciaRequired;
		}
	}
	
	return messageVal;
}

/*
 * Borra/resetea los valores de los elementos de seleccion anterior en funcion del elemento con el que se desea realizar la busqueda 
 * @param idSeleccion Ide del elemento con el que se esta haciendo la busqueda actual
 */
function borrarSeleccionAnterior(idSeleccion){
		
	if (idSeleccion=="p72_fld_centro"){
		//Resetea centro, region y zona 
		$("#p72_cmb_region").combobox("enable");
		$("#p72_cmb_region").combobox('comboautocomplete',null);
		
		$("#p72_cmb_region").combobox('autocomplete',optionActivaVacia);
		$("#p72_cmb_region").combobox('comboautocomplete',null);
		$("#p72_cmb_zona").combobox('autocomplete',optionActivaVacia);
		$("#p72_cmb_zona").combobox('comboautocomplete',null);
		$("#p72_cmb_zona").combobox("disable");
		
		areaOnOff(areaOn);
		areaOn = [];
		$("#p72_fld_provincia").val("");
		
	}
	
	else if (idSeleccion=="p72_cmb_region" || idSeleccion=="p72_cmb_zona"){
		
		$("#p72_fld_centro").val("");
		$("#p72_fld_centroId").val("");
		
		areaOnOff(areaOn);
		areaOn = [];
		$("#p72_fld_provincia").val("");	
	}
	
	else {
		//Resetea centro y provincia
		$("#p72_fld_centro").val("");
		$("#p72_fld_centroId").val("");
		
		$("#p72_cmb_region").combobox('autocomplete',optionActivaVacia);
		$("#p72_cmb_region").combobox('comboautocomplete',null);
		$("#p72_cmb_zona").combobox('autocomplete',optionActivaVacia);
		$("#p72_cmb_zona").combobox('comboautocomplete',null);
		$("#p72_cmb_zona").combobox("disable");
		
	}
	
}