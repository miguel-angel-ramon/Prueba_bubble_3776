var P112_AREA_CONST="area";
var P112_RESET_CONST="reset";
var P112_SECTION_CONST="section";

var fieldsRequired1 = null;
var fieldsRequired2 = null;
var fieldsRequired3 = null;

var gridRefConsumoRapido = null;

$(document).ready(function(){
	$(document).on('CargadoCentro', function(e) { 
		//loadP112Literales(locale);
		initializeScreenP112();
	});
	
	$("#p112_btn_buscar").click(function(event) {
		search();
    });
	
	$("#p112_btn_excel").click(function(event) {
		exportarAExcel();
    });
});

function show_error(msg){
	if (msg!=null){
		$('#p112AreaErrores').show();
		$('#p112_erroresTexto').text(msg);
	}else{
		$('#p112AreaErrores').hide();
		$('#p112_erroresTexto').text('');
	}

}

function do_search(){
	p112CargarDatos(gridRefConsumoRapido);
}

function search(){
	var msg = validateFilters();
	if (msg == ''){
		do_search();
		show_error(null);
		$('#p112_AreaListado').show();
	}else{
		$('#p112_AreaListado').hide();
		show_error(msg);
	}
}

function validateFilters(){
	var msg = validateSection();
	if (msg!=''){
		return msg;
	}else{
		return validateDates();
	}
}

function validateSection(){
	var msg = '';
	var area = $('#p112_cmb_area').val();
	var seccion = $('#p112_cmb_seccion').val();
	if (area == '' || area == null || seccion == '' || seccion == null){
		msg = fieldsRequired1;
	}
	return msg;
}

function validateDates(){
	var fecha_ini = $('#p112_fecha_inicio').datepicker('getDate');
	var fecha_fin = $('#p112_fecha_fin').datepicker('getDate');

	if (fecha_ini > fecha_fin){

		return fieldsRequired2
	}else {
		var dif_fechas = dateDiffInDays(fecha_ini, fecha_fin);

		if (dif_fechas > 20){
			return fieldsRequired3;
		}
	}
	return '';
}

function exportarAExcel(){

	var colModel = $(gridRefConsumoRapido.nameJQuery).jqGrid("getGridParam", "colModel");
    var colNames = $(gridRefConsumoRapido.nameJQuery).jqGrid("getGridParam", "colNames");
    
	var myColumns = new Array();
    var myColumnsNames = new Array();
    var j=0;
  
    $.each(colModel, function(i) {
    	if (colModel[i].name!="rn" && !colModel[i].hidden ){
    			myColumnsNames[j]=colNames[i];
	    	myColumns[j]=colModel[i].name;
	    	j++;	
	    	
    	}
     });
    
    var form = "<form name='consumoRapidoExportExcelForm' action='consumoRapido/exportGrid.do'  accept-charset='ISO-8859-1' method='get'>";
	form = form + "<input type='hidden' name='headers' value='"+myColumnsNames+"'>";		
	form = form + "<input type='hidden' name='model' value='"+myColumns+"'>";
		
	var area = $("#p112_cmb_area").val();
	var seccion = $("#p112_cmb_seccion").val();
	var fechaIni = $('#p112_fecha_inicio').datepicker({ dateFormat: "dd-mm-yyyy"}).val();
	var fechaFin = $('#p112_fecha_fin').datepicker({ dateFormat: "dd-mm-yyyy"}).val();
	
	form = form + "<input type='hidden' name='area' value='"+area+"'>";
	form = form + "<input type='hidden' name='seccion' value='"+seccion+"'>";
	form = form + "<input type='hidden' name='fechaIni' value='"+fechaIni+"'>";
	form = form + "<input type='hidden' name='fechaFin' value='"+fechaFin+"'>";

	form = form + "</form><script>document.consumoRapidoExportExcelForm.submit();</script>";
	var previous_form = document.getElementsByName('consumoRapidoExportExcelForm')[0];
	if (previous_form != undefined){
		document.body.removeChild(previous_form);	
	}
	
	$(form).appendTo('body').submit();
	
}
//a and b are javascript Date objects
function dateDiffInDays(a, b) {
  const _MS_PER_DAY = 1000 * 60 * 60 * 24;
  // Discard the time and time-zone information.
  const utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
  const utc2 = Date.UTC(b.getFullYear(), b.getMonth(), b.getDate());

  return Math.floor((utc2 - utc1) / _MS_PER_DAY);
}

function initializeScreenP112(){
	$( "#p112_popupRefConsumoRapido" ).dialog({
		autoOpen: false,
		height: 600,
		width: 'auto',
		modal: true,
		resizable: false,
		open: function() {
			$('#p112_popupRefConsumoRapido .ui-dialog-titlebar-close').on('mousedown', function(){
				$('#p112_popupRefConsumoRapido').dialog('close');
			});
			
			p112_load_cmbArea();
			cleanP112();
		},
		close:function(){
			cleanP112();

		}
	});
	$.datepicker.setDefaults($.datepicker.regional['es']);
	$( "#p112_fecha_inicio" ).datepicker({
        onSelect: function(dateText, inst) {
        	//Pendiente de guardado de fecha inicio
       }
   });
	
	$( "#p112_fecha_fin" ).datepicker({
        onSelect: function(dateText, inst) {
        	//Pendiente de guardado de fecha fin
       }
    });
	

   $( "#p112_btn_excel" ).attr("disabled", "disabled");
   
   $('#p112_AreaListado').hide();
   loadgrid(locale);
	
}

function cleanP112(){
	show_error(null);

	$( "#p112_fecha_inicio" ).datepicker('setDate', new Date());

	$( "#p112_fecha_fin" ).datepicker('setDate', new Date());
	
   $( "#p112_btn_excel" ).attr("disabled", "disabled");
   $('#p112_AreaListado').hide();
   $(gridRefConsumoRapido.nameJQuery).clearGridData(true).trigger("reloadGrid");
}

function p112formatDescripcionCombo(codigo, descripcion){
	return codigo + "-" + descripcion;
}

function p112_load_cmbArea(){	
	var vAgruComerRef=new VAgruComerRef("I1");
	var objJson = $.toJSON(vAgruComerRef);	
	$.ajax({
		type : 'POST',
		url : './consumoRapido/loadComboData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			var options = "<option value='' disabled selected></option>";

			for (i = 0; i < data.length; i++){
				options = options + "<option value='" + data[i].grupo1 + "'>" + p112formatDescripcionCombo(data[i].grupo1, data[i].descripcion) + "</option>"; 
			}
			$("select#p112_cmb_area").html(options);
			cleanSection();
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
	$("#p112_cmb_area").combobox({
        selected: function(event, ui) {
        	if ( ui.item.value!="" && ui.item.value!="null" ) {
	        	   p112_load_cmbSeccion();
        	}
        }
     }); 
	
	$("#p112_cmb_area").combobox('autocomplete',"");
	$("#p112_cmb_area").combobox('comboautocomplete',null);
	$("#p112_cmb_area").next().attr('placeholder','Seleccione area...');

}

function p112_load_cmbSeccion(){	
	$("#p112_cmb_seccion").combobox(null);
	var vAgruComerRef=new VAgruComerRef("I2",$("#p112_cmb_area").val());
	var objJson = $.toJSON(vAgruComerRef);	
	 $.ajax({
		type : 'POST',
		url : './consumoRapido/loadComboData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			var options = "<option value='' disabled selected></option>";
		    for (i = 0; i < data.length; i++){
				options = options + "<option value='" + data[i].grupo2 + "'>" + p112formatDescripcionCombo(data[i].grupo2, data[i].descripcion) + "</option>";
		    }
		    $("select#p112_cmb_seccion").html(options);
		    $("#p112_cmb_seccion").combobox('enable');
		    $("#p112_cmb_seccion").combobox('autocomplete',"");
		    $("#p112_cmb_seccion").combobox('comboautocomplete',null);
		    $("#p112_cmb_seccion").next().attr('placeholder','Seleccione sección...');

		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
	
	

}

function cleanSection(){
	$("#p112_cmb_seccion").combobox(null);
	$("#p112_cmb_seccion").combobox('autocomplete','');
	$("#p112_cmb_seccion").combobox('comboautocomplete',null);

	$("#p112_cmb_seccion").combobox('disable');
}

function loadArea(){
	if ($("#centerId").val()!=null && $("#centerId").val()!=''){
		p112_load_cmbArea();
	}	
}

function loadgrid(locale){
	gridRefConsumoRapido = new GridP112(locale);
	var jqxhr = $.getJSON(gridRefConsumoRapido.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				gridRefConsumoRapido.colNames = data.colNames;
				gridRefConsumoRapido.title = data.gridTitle;
				gridRefConsumoRapido.emptyRecords= data.emptyRecords;
				fieldsRequired1 = data.fieldsRequired1;
				fieldsRequired2 = data.fieldsRequired2;
				fieldsRequired3 = data.fieldsRequired3;
				gridRefConsumoRapido.tableFilter = data.tableFilter;
				index = '';
				sortOrder = 'asc';
				initgrid(gridRefConsumoRapido);
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
           });
}

function initgrid(grid3){
	$(gridRefConsumoRapido.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		datatype : 'local',
		contentType : 'application/json',
		mtype : 'POST',
		colNames : grid3.colNames,
		colModel : grid3.cm,
		rowNum : 10,
		rowList : [ 10, 20, 50 ],
		height : "100%",
		autowidth : true,
		width : "auto",
		rownumbers : true,
		autoencode:true,
		shrinkToFit:true,
		pager : grid3.pagerNameJQuery,
		viewrecords : true,
		caption : grid3.title,
		altclass: "ui-priority-secondary",
		altRows: true, //false, para que el grid no muestre cebrado
		hidegrid : false, //false, para ocultar el boton que colapsa el grid.
		sortable : true,
		//multiselect : true,
		index: grid3.sortIndex,
		sortname: grid3.sortIndex,
		sortorder: grid3.sortOrder,
		emptyrecords : grid3.emptyRecords,
		gridComplete : function() {},
		loadComplete : function(data) {
//			grid.actualPage = data.page;
//			grid.localData = data;
//			grid.sortIndex = null;
//			grid.sortOrder = null;
//			$("#p112_AreaListado .loading").css("display", "none");	
//			if (grid.firstLoad)
//				jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
//
//			// Ocultamos la check de seleccionar todos.
//			$("#cb_" + grid.name).attr("style",
//					"display:none");
			
		},
		onPaging : function(postdata) {			
			alreadySorted = false;
			grid3.sortIndex = null;
			grid3.sortOrder = null;
			grid3.saveColumnState.call($(this), this.p.remapColumns);
			p112CargarDatos(grid3);
			return 'stop';
		},
		onSelectRow: function(id){

		},
		resizeStop: function () {
			grid3.modificado = true;
			grid3.saveColumnState.call($(this),grid3.myColumnsState);
			jQuery(gridRefConsumoRapido.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), false);
       },
		onSortCol : function (index, columnIndex, sortOrder){
			grid3.sortIndex = index;
			grid3.sortOrder = sortOrder;
			grid3.saveColumnState.call($(this), this.p.remapColumns);
			p112CargarDatos(grid3);
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
	jQuery(gridRefConsumoRapido.nameJQuery).jqGrid('navGrid',grid3.pagerNameJQuery,{
			add:false,edit:false,del:false,search:false,refresh:false}
	); 
	jQuery(gridRefConsumoRapido.nameJQuery).jqGrid('navButtonAdd',grid3.pagerNameJQuery,{ 
			caption: grid3.tableFilter, title: "Reordenar Columnas", 
		onClickButton : function (){ 
			jQuery(gridRefConsumoRapido.nameJQuery).jqGrid('columnChooser',
					{
               		"done": function(perm) {
               			if (perm) {
               				var autowidth = true;
               				if (grid3.modificado == true){
               					autowidth = false;
               					grid3.myColumnsState =  grid3.restoreColumnState(grid3.cm);
               					grid3.isColState = typeof (grid3.myColumnsState) !== 'undefined' && grid3.myColumnsState !== null;
                   		    	this.jqGrid("remapColumns", perm, true);
                   		    	 var colModel =jQuery(gridRefConsumoRapido.nameJQuery).jqGrid('getGridParam', 'colModel'); 
                   		    	 var l = colModel.length;
                   		         var colItem; 
                   		         var cmName;
                   		         var colStates = grid3.myColumnsState.colStates;
                   		         var cIndex = 2;
                   		         for (i = 0; i < l; i++) {
                   		             colItem = colModel[i];
                   		             cmName = colItem.name;
                   		             if (colItem.hidden !== true && cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {
                   		            	 
                   		            	 jQuery(gridRefConsumoRapido.nameJQuery).jqGrid('setColProp',cmName,{width:colStates[cmName].width});
                   		            	 var cad =grid3.nameJQuery+'_'+cmName;
                   		            	 var ancho = "'"+colStates[cmName].width+"px'";
                   		            	 var cell = jQuery('table'+grid3.nameJQuery+' tr.jqgfirstrow td:nth-child(' + (i+1) + ')');
                   		            	 cell.css("width", colStates[cmName].width + "px");
                   		            	
                   		            	 jQuery(cad).css("width", colStates[cmName].width + "px");
                   		            	
                   		             }
                   		         }
                   		         
               				} else {
               					this.jqGrid("remapColumns", perm, true);
               				}
               				grid3.saveColumnState.call(this, perm);
               				jQuery(gridRefConsumoRapido.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), autowidth);
               				
               				
               			}
               		}
					}		
			); } });
}

function p112CargarDatos(grid3) {
	
	// PRUEBAS
//	return true;
	
	if (grid3.firstLoad) {
		jQuery(gridRefConsumoRapido.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
        grid3.firstLoad = false;
        if (grid3.isColState) {
            $(this).jqGrid("remapColumns", grid3.myColumnsState.permutation, true);
        }
    } else {
    	grid3.myColumnsState = grid3.restoreColumnState(grid3.cm);
    	grid3.isColState = typeof (grid3.myColumnsState) !== 'undefined' && grid3.myColumnsState !== null;
    	jQuery(gridRefConsumoRapido.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), false);
    }
	var area = $("#p112_cmb_area").val();
	var seccion = $("#p112_cmb_seccion").val();
	var fecha_ini = $('#p112_fecha_inicio').datepicker({ dateFormat: "dd-mm-yyyy"}).val();
	var fecha_fin = $('#p112_fecha_fin').datepicker({ dateFormat: "dd-mm-yyyy"}).val();
	
	$("#p112_AreaResultados .loading").css("display", "block");
	
	var sort = '';
	if (grid3.getSortIndex()!=null){
		sort = '&index='+grid3.getSortIndex()
	}
	$.ajax({
		type : 'GET',
		url : './consumoRapido/loadDataGrid.do?page='+grid3.getActualPage()+'&max='+grid3.getRowNumPerPage()+sort+'&sortorder='+grid3.getSortOrder()+'&area='+area+'&seccion='+seccion+'&fechaIni='+fecha_ini+'&fechaFin='+fecha_fin,
		contentType : "application/json; charset=utf-8",
		success : function(data) {		
			if (data.rows.length > 0){
				$( "#p112_btn_excel" ).removeAttr("disabled");
			}else{
				$( "#p112_btn_excel" ).attr("disabled","disabled");
			}
			$(gridRefConsumoRapido.nameJQuery)[0].addJSONData(data);
			gridRefConsumoRapido.actualPage = data.page;
			gridRefConsumoRapido.localData = data;
			$("#p112_AreaResultados .loading").css("display", "none");	

		},
		error : function (xhr, status, error){
			$("#p112_AreaResultados .loading").css("display", "none");	
			handleError(xhr, status, error, locale);				
        }			
	});		
}

/*Clase de constantes para le GRID*/
function GridP112 (locale){
	// Atributos
	this.name = "gridP112"; 
	this.nameJQuery = "#gridP112"; 
	this.i18nJSON = './misumi/resources/p112popupRefConsumoRapido/p112popupRefConsumoRapido_' + locale + '.json';
	this.colNames = null;
	this.cm = [	{
		"name" : "descripGrupo1",
		"index":"descripGrupo1",
		"width" : 25,
		"sortable": false
	},{
		"name" : "descripGrupo2",
		"index":"descripGrupo2", 
		"width" : 25,
		"sortable": false
	},{
		"name" : "descripGrupo3",
		"index":"descripGrupo3", 
		"width" : 25
	},{
		"name" : "descripGrupo4",
		"index":"descripGrupo4", 
		"width" : 25
	},{
		"name" : "descripGrupo5",
		"index":"descripGrupo5", 
		"width" : 25
	},{
		"name" : "codArt",
		"index": "codArt", 
		"width" : 25,
		"formatter" : function(value, options, rData){ return ((value!=null && value!=0) ? value : '')  ;  }
	},{
		"name" : "descripArt",
		"index": "descripArt", 
		"width" : 40						
	},{
		"name" : "fechaGrab",
		"index": "fechaGrab", 
		"width" : 40
	},{
		"name" : "cantidad",
		"index": "cantidad", 
		"width" : 25,
		"align"     : "center"
	},{
		"name" : "vidaUtil",
		"index": "vidaUtil", 
		"width" : 15,
		"align"     : "center"
	},{
		"name" : "imc",
		"index": "imc", 
		"width" : 15,
		"sortable": false,
		"align"     : "center"
	}
	];
	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP112"; 
	this.pagerNameJQuery = "#pagerP112";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;
	this.tableFilter = null;
	this.myColumnStateName = 'gridP112.colState';
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
         var colModel =jQuery(gridRefConsumoRapido.nameJQuery).jqGrid('getGridParam', 'colModel'); 
         var i;
         var l = colModel.length;
         var colItem; 
         var cmName;
         var postData = jQuery(gridRefConsumoRapido.nameJQuery).jqGrid('getGridParam', 'postData');
         var columnsState = {
                 search: jQuery(gridRefConsumoRapido.nameJQuery).jqGrid('getGridParam', 'search'),
                 page: jQuery(gridRefConsumoRapido.nameJQuery).jqGrid('getGridParam', 'page'),
                 sortname: jQuery(gridRefConsumoRapido.nameJQuery).jqGrid('getGridParam', 'sortname'),
                 sortorder: jQuery(gridRefConsumoRapido.nameJQuery).jqGrid('getGridParam', 'sortorder'),
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
         
         if (typeof window.localStorage !== 'undefined') {
             window.localStorage.setItem(gridRefConsumoRapido.myColumnStateName, JSON.stringify(columnsState));
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