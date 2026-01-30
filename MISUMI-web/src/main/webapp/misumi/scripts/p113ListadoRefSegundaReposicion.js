var P113_AREA_CONST="area";
var P113_RESET_CONST="reset";
var p113Seccion = "Sección";
var p113Categoria = null;
var p113SeleccionadosEstructuraTotal = new Array();
var p113SeleccionadosEstructuraTotalText= new Array();
var grid=null;
var centerRequired=null;
var areaRequired=null;
var sectionRequired=null;
var categoryRequired=null;
var p113Categoria = null;
var emptyRecords=null;
var colReset=true;

$(document).ready(function(){
	$(document).on('CargadoCentro', function(e) { 
		initializeScreenP113();
		loadP113(locale);
	});
});

function initializeScreenP113(){
	events_p113_btn_buscar();
	events_p113_btn_limpiar();
	events_p113_btn_exportExcel();
	p113load_cmbArea();
	p113cleanFilterSelection(P113_RESET_CONST);
	inicializarChkMeses();
	init_buttons();
}

function init_buttons(){
	$("#p113_btn_excel").prop("disabled", true);
}

function loadP113(locale){
	grid = new GridP113(locale);
	
	var jqxhr = $.getJSON(grid.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				grid.colNames = data.p113ColNames;
				index = '';
				sortOrder = 'asc';
				grid.title = data.p113GridTitle;
				grid.emptyRecords= data.emptyRecords;
				centerRequired=data.centerRequired;
				areaRequired=data.areaRequired;
				sectionRequired=data.sectionRequired;
				categoryRequired=data.categoryRequired;
				p113Categoria=data.p113Categoria;
				tableFilter= data.tableFilter;
				emptyRecords=data.emptyRecords;
				loadP113Mock(grid);
				setHeadersTitles(data);
				
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
            });
}

function loadP113Mock(grid) {
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
			rowList : [ 10, 20, 30 ],
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
			gridComplete : function() {

				
			},
			loadComplete : function(data) {
				grid.actualPage = data.page;
				grid.localData = data;
				grid.sortIndex = null;
				grid.sortOrder = null;
				$("#p113_AreaResultados .loading").css("display", "none");	
				if (grid.firstLoad){
					jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
				}
				
			},
			onPaging : function(postdata) {			
				alreadySorted = false;
				grid.sortIndex = null;
				grid.sortOrder = null;
				grid.saveColumnState.call($(this), this.p.remapColumns);
				//flag para resetear as columnas. True - Resetea. False - No resetea
				colReset=false;
				reloadData('S');
				return 'stop';
			},
			onSelectRow: function(id){

			},
			resizeStop: function () {
				grid.modificado = true;
				grid.saveColumnState.call($(this),grid.myColumnsState);
				jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), false);
            },
			onSortCol : function (index, columnIndex, sortOrder){
				grid.sortIndex = index;
				grid.sortOrder = sortOrder;
				grid.saveColumnState.call($(this), this.p.remapColumns);
				reloadData('S');
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
                    					grid.myColumnsState =  grid.restoreColumnState(grid.cm);
                        		    	grid.isColState = typeof (grid.myColumnsState) !== 'undefined' && grid.myColumnsState !== null;
                        		    	this.jqGrid("remapColumns", perm, true);
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
                    				grid.saveColumnState.call(this, perm);
                    				jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), autowidth);
                    				
                    				
                    			}
                    			//
                    		}
						}		
				); } });
		
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["area"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["seccion"]);
		jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);

}

/*Clase de constantes para le GRID*/
function GridP113 (locale){
		// Atributos
		this.name = "gridP113"; 
		this.nameJQuery = "#gridP113"; 
		this.i18nJSON = './misumi/resources/p113ListadoRefSegundaReposicion/p113ListadoRefSegundaReposicion_' + locale + '.json';
		this.colNames = null;
		this.cm = [	{
			"name" : "descCodN1",
			"index":"descCodN1",
			"width" : 25,
			"formatter": function(value, options, rData){ return rData['codN1'] + '-' +value  ;  },
		    "hidden" : true
		},{
			"name" : "descCodN2",
			"index":"descCodN2",
			"width" : 25,
			"formatter": function(value, options, rData){ return rData['codN2'] + '-' +value  ;  },
		    "hidden" : true
		},{
			"name" : "descCodN3",
			"index":"descCodN3",
			"width" : 25,
			"formatter": function(value, options, rData){ return rData['codN3'] + '-' +value  ;  },
		    "hidden" : true,
		    "editable": true,
		    "editrules": {edithidden:true}
		},{
			"name" : "descCodN4",
			"index":"descCodN4",
			"width" : 25,
			"formatter": function(value, options, rData){ return rData['codN4'] + '-' +value  ;  },
		    "hidden" : true,
		    "editable": true,
		    "editrules": {edithidden:true}
		},{
			"name" : "descCodN5",
			"index":"descCodN5",
			"width" : 25,
			"formatter": function(value, options, rData){ return rData['codN5'] + '-' +value  ;  },
		    "hidden" : true,
		    "editable": true,
		    "editrules": {edithidden:true}
		},{
			"name" : "referencia",
			"index":"referencia", 
			"width" : 30
			     
		},{
			"name" : "referenciaDesc",
			"index":"referenciaDesc", 
			"width" : 100						
		},{
			"name" : "facing",
			"index":"facing", 
			"width" : 30
		},{
			"name" : "capacidad",
			"index":"capacidad", 
			"width" : 30
		},{
			"name" : "cajaExpositora",
			"index":"cajaExpositora", 
			"width" : 30
		},{
			"name" : "tendencia",
			"index":"tendencia", 
			"formatter":"number",
			"width" : 30,
			"hidden" : true
		},{
			"name" : "ventaPrevista",
			"index":"ventaPrevista",
			"formatter":"number",
			"width" : 30,
			"hidden" : true
		}
	];
	
		
	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP113"; 
	this.pagerNameJQuery = "#pagerP113";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;
	this.myColumnStateName = 'gridP113.colState';
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
			return "estructura";
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
         var colModel =jQuery(grid.nameJQuery).jqGrid('getGridParam', 'colModel'); 
         //if (colModel == null)
        //	 colModel = grid.cm;
         var i;
         var l = colModel.length;
         var colItem; 
         var cmName;
         var postData = jQuery(grid.nameJQuery).jqGrid('getGridParam', 'postData');
         var columnsState = {
                 search: jQuery(grid.nameJQuery).jqGrid('getGridParam', 'search'),
                 page: jQuery(grid.nameJQuery).jqGrid('getGridParam', 'page'),
                 sortname: jQuery(grid.nameJQuery).jqGrid('getGridParam', 'sortname'),
                 sortorder: jQuery(grid.nameJQuery).jqGrid('getGridParam', 'sortorder'),
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
             window.localStorage.setItem(grid.myColumnStateName, JSON.stringify(columnsState));
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

function setHeadersTitles(data){
	
   	var colModel = $(grid.nameJQuery).jqGrid("getGridParam", "colModel");
    $.each(colModel, function(i) {
    	if (colModel[i].name!="rn"){
	    	$("#jqgh_gridP113_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
    	}
     });
}

function p113load_cmbArea(){	
	
	var options = "";
	var optionNull = "";
	var vAgruComerRef=new VAgruComerRef("I1");
	var objJson = $.toJSON(vAgruComerRef);	
	$.ajax({
		type : 'POST',
		url : './listadoRefSegundaReposicion/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			   for (i = 0; i < data.length; i++){
					options = options + "<option value='" + data[i].grupo1 + "'>" + p113formatDescripcionCombo(data[i].grupo1, data[i].descripcion) + "</option>"; 
			   }
			   $("select#p113_cmb_area").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
	$("#p113_cmb_area").combobox({
        selected: function(event, ui) {
        	if ( ui.item.value!="" && ui.item.value!="null" ) {
	           if ($("#p113_cmb_area").val()!=null){
	        	   
	        	   p113load_cmbSeccion();
	        	   var result = p113cleanFilterSelection(P113_AREA_CONST);
	           }
        	}else{
        		  var result= p113cleanFilterSelection(P113_RESET_CONST);
        	}
        }  ,
   
	    changed: function(event, ui) { 
		    if (ui.item==null || ui.item.value!="" || ui.item.value!="null"){
			   result=p113cleanFilterSelection(P113_RESET_CONST);
		    }	 
	    }
     }); 
	
	$("#p113_cmb_area").combobox('autocomplete',optionNull);
	$("#p113_cmb_area").combobox('comboautocomplete',null);
	limpiarErroresCombos();
}

function p113load_cmbSeccion(){	
	var options = "";
	var optionNull = "";
	//var registrosEstructura = "";
	$("#p113_cmb_seccion").combobox(null);
	var vAgruComerRef=new VAgruComerRef("I2",$("#p113_cmb_area").val());
	var objJson = $.toJSON(vAgruComerRef);	
	 $.ajax({
		type : 'POST',
		url : './listadoRefSegundaReposicion/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			   for (i = 0; i < data.length; i++){
					options = options + "<option value='" + data[i].grupo2 + "'>" + p113formatDescripcionCombo(data[i].grupo2, data[i].descripcion) + "</option>";
					//registrosEstructura = registrosEstructura + "<tr><td class=\"p113_tdCheck\"><input type=\"checkbox\" class=\"case\" name=\"case\" onclick=\"controlCheckSeccion(this)\" value=\""+data[i].grupo2+"\"/></td><td class=\"p113_tdDato\">" + p113formatDescripcionCombo(data[i].grupo2, data[i].descripcion) + "</td><td class=\"p113_tdErrorCaja\"><img class =\"p113_imgErrorCaja\" src=\"./misumi/images/dialog-error-24.png\" title=\"\"/></td></tr>";
			   }
			   $("select#p113_cmb_seccion").html(options);
			   //$("#p113_table_Estructura").html(registrosEstructura);
			   //$("#p113_estructuraMultipleLegend").html(p113Seccion);
			   //$("div#p113_imagenFlechaDiv").attr("style", "display:block");
			   //$("div#p113_TablaEstructuraDiv").attr("style", "display:block");
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
		$("#p113_cmb_seccion").combobox({
	        selected: function(event, ui) {
	        	
	         if ( ui.item.value!="" && ui.item.value!="null") {
		           if ($("#p113_cmb_seccion").val()!=null){
		        	   p113load_cmbCategory();
		           }
	         }  else{
	        	   var result=p113cleanFilterSelection(P113_AREA_CONST);
	        	}  
	        }  
	        ,		       
	        changed: function(event, ui) { 
				   if (ui.item==null ||  ui.item.value!="" || ui.item.value!="null"){
		        	   if (p113cleanFilterSelection(P113_AREA_CONST)){
		        		   p113load_cmbSeccion();
				      }
				   }	 
			   }
	       
	     });
	
	$("#p113_cmb_seccion").combobox('autocomplete',optionNull);
	$("#p113_cmb_seccion").combobox('comboautocomplete',null);
	limpiarErroresCombos();
}

function p113load_cmbCategory(){	
	var options = "";
	var optionNull = "";
	var registrosEstructura = "";
	$("#p113_cmb_categoria").combobox(null);
		var vAgruComerRef=new VAgruComerRef("I3",$("#p113_cmb_area").val(),$("#p113_cmb_seccion").val());
	var objJson = $.toJSON(vAgruComerRef);	
	 $.ajax({
		type : 'POST',
		url : './exclusionVentas/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			   for (i = 0; i < data.length; i++){
					registrosEstructura = registrosEstructura + "<tr><td class=\"p113_tdCheck\"><input type=\"checkbox\" class=\"case\" name=\"case\" onclick=\"controlCheckCategoria(this)\" value=\""+data[i].grupo3+"\"/></td><td class=\"p113_tdDato\">" + p113formatDescripcionCombo(data[i].grupo3, data[i].descripcion) + "</td><td class=\"p113_tdErrorCaja\"><img class =\"p113_imgErrorCaja\" src=\"./misumi/images/dialog-error-24.png\" title=\"\"/></td></tr>";
			   }
			   $("#p113_table_Estructura").html(registrosEstructura);
			   $("#p113_estructuraMultipleLegend").html(p113Categoria);
			   $("div#p113_imagenFlechaDiv").attr("style", "display:block");
			   $("div#p113_TablaEstructuraDiv").attr("style", "display:block");
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
	
}



function events_p113_btn_buscar(){
	$("#p113_btn_search").click(function () {
		colReset=false;
		gridP113.firstLoad = true;
		findListado();
	});	
}

function events_p113_btn_limpiar(){
	$("#p113_btn_clean").click(function () {
		colReset=true;
		p113cleanFilterSelection(P113_RESET_CONST);
		//$("#p113_AreaResultados").hide();
		$('#gridP113').jqGrid('clearGridData');
		inicializarChkMeses();
	});	
}

function events_p113_btn_exportExcel(){
	$("#p113_btn_excel").click(function () {	
		var messageVal=findValidation();
		if (messageVal!=null){
			createAlert(replaceSpecialCharacters(messageVal), "ERROR");
		}else{
			exportExcel();
		}
	});	
}

function findListado(){
	var messageVal=findValidation();
	
	if (messageVal!=null){
		$('#gridP113').jqGrid('clearGridData');
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["area","seccion","categoria","subcategoria","descripcion"]);
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		$('#gridP113').setGridParam({page:1});
		reloadData('N');
		
	}
}

function reloadData(recarga) {
	var gridP113 = grid;
	if(recarga == "N"){
		if(colReset){
			hideShowColumns();
		}
	}
	
	if (gridP113.firstLoad) {
		jQuery(gridP113.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
		gridP113.firstLoad = false;
        if (gridP113.isColState) {
            $(this).jqGrid("remapColumns", gridP113.myColumnsState.permutation, true);
        }
    } else {
    	jQuery(gridP113.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), false);
    }
	
	
	var messageVal=findValidation();
	if (messageVal!=null){
		$('#gridP113').jqGrid('clearGridData');
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
	
		var listadoRefSegundaReposicion = new ListadoRefSegundaReposicion ($("#centerId").val(),$("#p113_cmb_area").combobox('getValue'), $("#p113_cmb_seccion").combobox('getValue'),
				 p113SeleccionadosEstructuraTotal, $("input[name='p113_rad_mes']:checked").val());
	
		var objJson = $.toJSON(listadoRefSegundaReposicion.prepareListadoRefSegundaReposicionToJsonObject());
		
		$.ajax({
			type : 'POST',
			url : './listadoRefSegundaReposicion/loadDataGrid.do?page='+gridP113.getActualPage()+'&max='+ gridP113.getRowNumPerPage()+ '&index=' + gridP113.getSortIndex()+ '&sortorder=' + gridP113.getSortOrder(),
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				if (data != null && data != '')	{	
					$("#p113_AreaResultados").show();
					$(grid.nameJQuery)[0].addJSONData(data);
					grid.actualPage = data.page;
					grid.localData = data; 
				}
				$("#p113_AreaResultados .loading").css("display", "none");	
				if (data.records == 0){
					createAlert(replaceSpecialCharacters(emptyRecords), "ERROR");
					$(grid.nameJQuery).jqGrid('clearGridData');
				} else{
					$("#p113_btn_excel").prop("disabled", false);
				}
			},
			error : function(xhr, status, error) {
				$("#p113_AreaResultados .loading").css("display", "none");
				handleError(xhr, status, error, locale);
			}
		});
	}
}

function findValidation(){
	var messageVal=null;
	if ($("#centerId").val()==null || $("#centerId").val()==""){
		messageVal = centerRequired;
		 
	} else if($("#p113_cmb_area").combobox('getValue')=="null" || $("#p113_cmb_area").combobox('getValue')==null){
		messageVal = areaRequired;
	}else if( $("#p113_cmb_seccion").combobox('getValue')=="null" || $("#p113_cmb_seccion").combobox('getValue')==null){
		messageVal = sectionRequired;
		
    }else{
    	p113ObtenerSeleccionadosConsulta();
    	if (p113SeleccionadosEstructuraTotal.length == 0) {
    		messageVal =categoryRequired;
    	}
    }
	
	return messageVal;
	
}



function p113ObtenerSeleccionadosConsulta() {

	p113SeleccionadosEstructuraTotal = new Array();
	$("input[name='case']").each(function() {
		var camposSeleccionadosListadoCategoria = new Array();
		//Se comprueba para que grupo se elige
		if ($( this ).is(':checked')){
			p113SeleccionadosEstructuraTotal.push($( this ).val());
		}
	});

}

function p113formatDescripcionCombo(codigo, descripcion){
	return codigo + "-" + descripcion;
}

function p113cleanFilterSelection(componentName){
	$("#p113_table_Estructura").html('');
	if (componentName == P113_RESET_CONST){
		$("#p113_cmb_area").combobox(null);
		$("select#p113_cmb_area").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p113_cmb_area").combobox('autocomplete',"");
		$("#p113_cmb_area").combobox('comboautocomplete',null);
		$("#p113_cmb_seccion").combobox(null);
		$("#p113_btn_excel").prop("disabled", true);
		if ($("#centerId").val()!=null && $("#centerId").val()!=''){
			p113load_cmbArea();
		}
	}
	if (componentName == P113_AREA_CONST || componentName == P113_RESET_CONST){
		$("select#p113_cmb_seccion").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p113_cmb_seccion").combobox('autocomplete','');
		$("#p113_cmb_seccion").combobox('comboautocomplete',null);
	}
	p113disableFilterSelection(componentName);
	return true;
}

function p113disableFilterSelection(componentName){
	if (componentName == P113_RESET_CONST){
		if ($("#centerId").val()!=null && $("#centerId").val()!=''){
			$("#p113_cmb_area").combobox("enable");
		}else{
			$("#p113_cmb_area").combobox("disable");
		}
		$("#p113_cmb_seccion").combobox("disable");
		$("#p113_cmb_categoria").combobox("disable");
		$("#p113_cmb_subcategoria").combobox("disable");
		$("#p113_cmb_segmento").combobox("disable");
		$("div#p113_imagenFlechaDiv").attr("style", "display:none");
		$("div#p113_TablaEstructuraDiv").attr("style", "display:none");
		
	}else if (componentName == P113_AREA_CONST){
		$("#p113_cmb_seccion").combobox("enable");
		$("div#p113_imagenFlechaDiv").attr("style", "display:none");
		$("div#p113_TablaEstructuraDiv").attr("style", "display:none");
		
	}
}

function controlCheckSeccion(obj) {
	var objDisable = false;
    if (!obj.checked){
    	 $("input[name='case']").each(function() {
    	    	$( this ).removeAttr("disabled");
    	    });
    }else{
	    $("input[name='case']").each(function() {
	    	if (!$( this ).is(':checked')){
	    		$( this ).attr("disabled", "disabled");
	    	}
	    });
	    objDisable = true;
    }
    if (objDisable){
    	$("#p113_cmb_area").combobox("disable");
    }else{
    	$("#p113_cmb_area").combobox("enable");
    }
    limpiarErroresCombos();
}

function controlCheckCategoria(obj) {
	var objDisable = false;
	$("input[name='case']").each(function() {
		 if ($( this ).is(':checked')){
			 objDisable = true;
		 }
	});
    if (objDisable){
    	$("#p113_cmb_area").combobox("disable");
    	$("#p113_cmb_seccion").combobox("disable");
    }else{
    	$("#p113_cmb_area").combobox("enable");
    	$("#p113_cmb_seccion").combobox("enable");
    }
    limpiarErroresCombos();
}

function limpiarErroresCombos(){ 
	//Borrado de errores previos
	$("#p113_area_imgError").hide();
	$("#p113_seccion_imgError").hide();
}

function inicializarChkMeses(){
	$("#p113_chk_mes").attr("checked", true);
	$("#p113_chk_mes1").attr("checked", false);
	$("#p113_chk_mes2").attr("checked", false);
}

function hideShowColumns(){
	jQuery(grid.nameJQuery).jqGrid('hideCol', ["area"]);
	jQuery(grid.nameJQuery).jqGrid('hideCol', ["seccion"]);
}

function exportExcel(){
	var messageVal=findValidation();
	if (messageVal!=null){
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		var colModel = $(grid.nameJQuery).jqGrid("getGridParam", "colModel");
	    var colNames = $(grid.nameJQuery).jqGrid("getGridParam", "colNames");
		
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
	  
	   
	    var url = 'listadoRefSegundaReposicion/exportGrid.do';
	    var objJson = prepareObjectJSONExcel();
	    var form = $('<form/>', { action: url, method: 'GET', name: 'csvexportform'});
	    form.append( $('<input />', {type: 'hidden', name: 'headers', value: myColumnsNames}) );
	    form.append( $('<input />', {type: 'hidden', name: 'model', value: myColumns}) );
	    form.append( $('<input />', {type: 'hidden', name: 'listadoRefSegundaReposicionExcel', value: objJson}) );
	    form.appendTo('body').submit();
	     
	}
}

function prepareObjectJSONExcel(){
	var centro = $("#centerId").val();
	var area = $("#p113_cmb_area").combobox('getValue');
	var seccion = $("#p113_cmb_seccion").combobox('getValue');
	var mes = $("input[name='p113_rad_mes']:checked").val();
	p113ObtenerSeleccionadosConsulta();
	var listadoSeleccionadosExcel=p113SeleccionadosEstructuraTotal;
	//DESCRIPCIONES
	var centroDesc = $("#centerName").val();
	var areaDesc = $("#p113_cmb_area :selected").text();
	var seccionDesc = $("#p113_cmb_seccion :selected").text();
	
	var buscador = {
		"codCentro" : centro,
		"codgrupo1" : area,
		"codgrupo2" : seccion,
		"descCentro" : centroDesc,
		"descgrupo1" : areaDesc,
		"descgrupo2" : seccionDesc,
		"mes" : mes,
		"listadoSeleccionados" : listadoSeleccionadosExcel
	};
	return $.toJSON(buscador);
}


