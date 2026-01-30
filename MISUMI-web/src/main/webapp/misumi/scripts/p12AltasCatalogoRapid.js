var AREA_CONST="area";
var RESET_CONST="reset";
var SECTION_CONST="section";
var CATEGORY_CONST="category";
var SUBCATEGORY_CONST="subcategory"; 
var SEGMENT_CONST="segment";
var grid=null;
var centerRequired=null;
var areaRequired=null;
var sectionRequired=null;
var categoryRequired=null;
var referenceRequired=null;
var centerRequided=null;
var tableFilter=null;
var allOptions=null;
var emptyRecords=null;
var P12Inicializada = null;
var colReset=true;

var optionNull = "";
$(document).ready(function(){
	$(document).on('CargarScriptsPantallas', function(e) { 
		$(document).on('CargadoCentro', function(e) { 
			if(P12Inicializada == null){
				P12Inicializada = 'S';
				loadP12(locale);
			}
		});
	
		if($("#centerId").val() != null && $("#centerId").val() != "" && P12Inicializada == null){
			P12Inicializada = 'S';
			loadP12(locale);
		}
		
		events_p12_rad_tipoFiltro();
		events_p12_btn_reset();
		events_p12_btn_buscar();
		events_p12_btn_exportExcel();
	});
	initializeScreenComponents();
});

function initializeScreenComponents(){
	$("#p12_cmb_area").combobox(null);
	$("#p12_cmb_seccion").combobox(null);
	$("#p12_cmb_subcategoria").combobox(null);
	$("#p12_cmb_categoria").combobox(null);
	$("#p12_cmb_segmento").combobox(null);

	CargadaEstructuraPantalla(2);
}

function initializeScreen(){
	var result=cleanFilterSelection(RESET_CONST);
	controlCentro();
	
	$("#p12_rad_tipoFiltro").filter('[value=1]').attr('checked', true);
	$('#p12_fld_referencia').filter_input({regex:'[0-9]'});
}

function load_cmbArea(){		
	var options = "";
	var optionNull = "";
	//$( "#p12_cmb_area" ).prepend("<option value=null selected='selected'>"+optionNull+"</option>");
	var vAgruComerRef=new VAgruComerRef("I1");
	var objJson = $.toJSON(vAgruComerRef);	
	 $.ajax({
		type : 'POST',
		url : './altasCatalogoRapid/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			//options = "<option value='null' selected='selected'>"+optionNull+"</option>";
			   for (i = 0; i < data.length; i++){
					options = options + "<option value='" + data[i].grupo1 + "'>" + formatDescripcionCombo(data[i].grupo1, data[i].descripcion) + "</option>"; 
			   }
			   $("select#p12_cmb_area").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
	$("#p12_cmb_area").combobox({
        selected: function(event, ui) {
        	if ( ui.item.value!="" && ui.item.value!="null" ) {
	           if ($("#p12_cmb_area").val()!=null){
	        	   load_cmbSeccion();
	        	   var result = cleanFilterSelection(AREA_CONST);
	        	   jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
	           }
        	}else{
        		  var result= cleanFilterSelection(RESET_CONST);
        	}      
        },	     
		   changed: function(event, ui) { 
			   if (ui.item==null || ui.item.value!="" || ui.item.value!="null"){
				   return result=cleanFilterSelection(RESET_CONST);
				   
			   }
		   }
    }); 
	$("#p12_cmb_area").combobox('autocomplete',optionNull);
	$("#p12_cmb_area").combobox('comboautocomplete',null);
}

function load_cmbSeccion(){	
	var options = "";
	var optionNull = "";
	$("#p12_cmb_seccion").combobox(null);
	//$( "#p12_cmb_seccion" ).prepend("<option value='null' selected='selected'>"+optionNull+"</option>");
	var vAgruComerRef=new VAgruComerRef("I2",$("#p12_cmb_area").val());
	var objJson = $.toJSON(vAgruComerRef);	
	 $.ajax({
		type : 'POST',
		url : './altasCatalogoRapid/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			   for (i = 0; i < data.length; i++){
					options = options + "<option value='" + data[i].grupo2 + "'>" + formatDescripcionCombo(data[i].grupo2, data[i].descripcion) + "</option>"; 
			   }
			   $("select#p12_cmb_seccion").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
	$("#p12_cmb_seccion").combobox({
        selected: function(event, ui) {      	
         if ( ui.item.value!="" && ui.item.value!="null") {
	           if ($("#p12_cmb_seccion").val()!=null){
	        	   load_cmbCategory();
	        	   var result=cleanFilterSelection(SECTION_CONST);
	        	
	           }
         } else {
        	   var result=cleanFilterSelection(AREA_CONST);
         }  
        },		       
        changed: function(event, ui) { 
			   if (ui.item==null ||  ui.item.value!="" || ui.item.value!="null"){
	        	   if (cleanFilterSelection(AREA_CONST)){
				   load_cmbSeccion();
			      }
			   }	 
		   }
    });
	$("#p12_cmb_seccion").combobox('autocomplete',optionNull);
	$("#p12_cmb_seccion").combobox('comboautocomplete',null);
}

function load_cmbCategory(){	
	var options = "";
	var optionNull = "";
	$("#p12_cmb_categoria").combobox(null);
		var vAgruComerRef=new VAgruComerRef("I3",$("#p12_cmb_area").val(),$("#p12_cmb_seccion").val());
		var objJson = $.toJSON(vAgruComerRef);	
	 $.ajax({
		type : 'POST',
		url : './altasCatalogoRapid/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			   for (i = 0; i < data.length; i++){
					options = options + "<option value='" + data[i].grupo3 + "'>" + formatDescripcionCombo(data[i].grupo3, data[i].descripcion) + "</option>"; 
			   }
			   $("select#p12_cmb_categoria").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
	$("#p12_cmb_categoria").combobox({
        selected: function(event, ui) {
        	 if ( ui.item.value!="" && ui.item.value!="null") {
	           if ($("#p12_cmb_categoria").val()!=null){
	        	
	        	   load_cmbSubCategory();
	        	   var result=cleanFilterSelection(CATEGORY_CONST);

	           } 
        	 }  else{
        		 var result=cleanFilterSelection(SECTION_CONST);
	        	}  
        },		       
        changed: function(event, ui) { 
			   if (ui.item==null || ui.item.value!="" || ui.item.value!="null"){
				   if (cleanFilterSelection(SECTION_CONST)){ 
				      load_cmbCategory();
				   }
			    }	    
		   }
    }); 
	$("#p12_cmb_categoria").combobox('autocomplete',optionNull);
	$("#p12_cmb_categoria").combobox('comboautocomplete',null);	
}

function load_cmbSubCategory(){	
	var options = "";
	var optionNull = "";
	$("#p12_cmb_subcategoria").combobox(null);
	var vAgruComerRef=new VAgruComerRef("I4",$("#p12_cmb_area").val(),$("#p12_cmb_seccion").val(),$("#p12_cmb_categoria").val());
	var objJson = $.toJSON(vAgruComerRef);	
	 $.ajax({
		type : 'POST',
		url : './altasCatalogoRapid/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			   for (i = 0; i < data.length; i++){
					options = options + "<option value='" + data[i].grupo4 + "'>" + formatDescripcionCombo(data[i].grupo4, data[i].descripcion) + "</option>"; 
			   }
			   $("select#p12_cmb_subcategoria").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
		$("#p12_cmb_subcategoria").combobox({
	        selected: function(event, ui) {
	          if ( ui.item.value!="" && ui.item.value!="null") {
		           if ($("#p12_cmb_subcategoria").val()!=null){
		        	   load_cmbSegment();
		        	   var result=cleanFilterSelection(SUBCATEGORY_CONST);  
		           } 
	          } else {
	        	  var result=cleanFilterSelection(CATEGORY_CONST);
	          } 
	        },		       
	        changed: function(event, ui) { 
				   if (ui.item==null ||ui.item.value!="" || ui.item.value!="null"){
					   if (cleanFilterSelection(CATEGORY_CONST)){ 
						   load_cmbSubCategory();
					   }	   
				   }	 
			   }
	     });
	$("#p12_cmb_subcategoria").combobox('autocomplete',optionNull);
	$("#p12_cmb_subcategoria").combobox('comboautocomplete',null);	
}

function load_cmbSegment(){	
	var options = "";
	var optionNull = "";
	$("#p12_cmb_segmento").combobox(null);
	var vAgruComerRef=new VAgruComerRef("I5",$("#p12_cmb_area").val(),$("#p12_cmb_seccion").val(),$("#p12_cmb_categoria").val(),$("#p12_cmb_subcategoria").val());
	var objJson = $.toJSON(vAgruComerRef);	
	 $.ajax({
		type : 'POST',
		url : './altasCatalogoRapid/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			   for (i = 0; i < data.length; i++){
					options = options + "<option value='" + data[i].grupo5 + "'>" + formatDescripcionCombo(data[i].grupo5, data[i].descripcion) + "</option>"; 
			   }
			   $("select#p12_cmb_segmento").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
	$("#p12_cmb_segmento").combobox({
        selected: function(event, ui) {
          if ( ui.item.value!="" && ui.item.value!="null") {
	           if ($("#p12_cmb_segmento").val()!=null){
	        	   $("#p12_cmb_segmento").combobox('comboautocomplete',ui.item.value);
	        	   $("#p12_cmb_segmento").combobox('autocomplete',ui.item.label);
	        	   var result=cleanFilterSelection(SEGMENT_CONST);
	        	   
	           } 
          }else{
        	  var result= cleanFilterSelection(SUBCATEGORY_CONST);
          } 
        },		       
        changed: function(event, ui) { 
			   if (ui.item==null || ui.item.value!="" || ui.item.value!="null"){
				   if (cleanFilterSelection(SUBCATEGORY_CONST)){ 
					   load_cmbSegment();
				   }   
			   }	 
		   }
          
    });
	$("#p12_cmb_segmento").combobox('autocomplete',optionNull);
	$("#p12_cmb_segmento").combobox('comboautocomplete',null);
}

function events_p12_rad_tipoFiltro(){
    $("input[name='p12_rad_tipoFiltro']").change(function () {
   		$('#gridP12').jqGrid('clearGridData');
    	if ($("input[name='p12_rad_tipoFiltro']:checked").val() == "1"){
    		//Estructura comercial
    		 $("div#p12_rapid_filtroEstructura").attr("style", "display:block");
    		 $("div#p12_filtroReferencia").attr("style", "display:none");
    		 $("#p12_fld_referencia").val("");	 
    	} else { //Referencias
    		$("div#p12_rapid_filtroEstructura").attr("style", "display:none");
   		 	$("div#p12_filtroReferencia").attr("style", "display:inline");
   		    var result= cleanFilterSelection(RESET_CONST);
	 		$("#p12_fld_referencia").focus();
	 		
    	}   
    	jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
    });
}

function events_p12_btn_reset(){
	$("#p12_btn_reset").click(function () {
		colReset=true;
		 var result=cleanFilterSelection(RESET_CONST);
		$("#p12_fld_referencia").val("");
		$('#gridP12').jqGrid('clearGridData');
	   	jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
	});
}

function findValidation(){
	var messageVal=null;
	if ($("input[name='p12_rad_tipoFiltro']:checked").val() == "1"){
		//Estructura comercial
		if ($("#centerId").val()==null || $("#centerId").val()==""){
			messageVal = centerRequided;	 
		} 
	} else { //Referencias
		if ($("#centerId").val()==null || $("#centerId").val()==""){
			messageVal = centerRequided;
			
		}else if($("#p12_fld_referencia").val()==null || $("#p12_fld_referencia").val()=='' ){
			messageVal = referenceRequired;
		}	
	}    
	return messageVal;
}

function events_p12_btn_buscar(){
	$("#p12_btn_buscar").click(function () {
		//flag para resetear as columnas. True - Resetea. False - No resetea
		colReset=false;
		finder();
	});	
}

function finder(){
	var messageVal=findValidation();
	
	if (messageVal!=null){
		$('#gridP12').jqGrid('clearGridData');
		//jQuery(grid.nameJQuery).jqGrid('hideCol', ["area","seccion","categoria","subcategoria","descripcion"]);
		//jQuery(grid.nameJQuery).jqGrid('hideCol', ["categoria"]);
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		$('#gridP12').setGridParam({page:1});
		reloadData('N');
	}
}

function events_p12_btn_exportExcel(){
	$("#p12_btn_exportExcel").click(function () {	
		var messageVal=findValidation();
		if (messageVal!=null){
			createAlert(replaceSpecialCharacters(messageVal), "ERROR");	
		}else{
			exportExcel();
		}
		
	});	
}

function setHeadersTitles(data){
   	var colModel = $(grid.nameJQuery).jqGrid("getGridParam", "colModel");
    $.each(colModel, function(i) {
    	if (colModel[i].name!="rn"){
	    	$("#jqgh_gridP12_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
    	}
     });
}

function loadP12(locale){
	grid = new GridP12(locale);
	//gridTextil = new GridP12Textil(locale);
	
	var jqxhr = $.getJSON(grid.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				grid.colNames = data.p12ColNames;
				index = '';
				sortOrder = 'asc';
				grid.title = data.p12GridTitle;
				grid.emptyRecords= data.emptyRecords;
				centerRequired=data.centerRequired;
				areaRequired=data.areaRequired;
				sectionRequired=data.sectionRequired;
				categoryRequired=data.categoryRequired;
				referenceRequired = data.referenceRequired;
				centerRequided=data.centerRequided;
				tableFilter= data.tableFilter;
				allOptions=data.allOptions;
				emptyRecords=data.emptyRecords;
				initializeScreen();
				loadP12Mock(grid);
				setHeadersTitles(data);
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
            });
}

function loadP12Mock(grid) {
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
				$("#AreaResultados .loading").css("display", "none");	
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
                		}
					}		
				);} 
		});
		jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
}

function transformRowFilter(recordAlta){
	return recordAlta;
}

function reloadData(recarga) {
	var gridP12 = grid;
	var messageVal=findValidation();

	if (gridP12.firstLoad) {
		jQuery(gridP12.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
		gridP12.firstLoad = false;
        if (gridP12.isColState) {
            $(this).jqGrid("remapColumns", gridP12.myColumnsState.permutation, true);
        }
    } else {
    	jQuery(gridP12.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), false);
    }
	
	
	var messageVal=findValidation();
	if (messageVal!=null){
		$('#gridP12').jqGrid('clearGridData');	
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		
		var recordAlta = new ArtGamaRapid ($("#centerId").val(), $("#p12_fld_referencia").val(), $("#p12_cmb_area").combobox('getValue'), $("#p12_cmb_seccion").combobox('getValue'), $("#p12_cmb_categoria").combobox('getValue'),
				$("#p12_cmb_subcategoria").combobox('getValue'), $("#p12_cmb_segmento").combobox('getValue'));
	
		var objJson = $.toJSON(recordAlta.prepareArtGamaRapidToJsonObject());
	
			//$("#AreaResultados .loading").css("display", "block");
			
			$.ajax({
				type : 'POST',
				url : './altasCatalogoRapid/loadDataGrid.do?page='+gridP12.getActualPage()+'&max='+gridP12.getRowNumPerPage()+'&index='+gridP12.getSortIndex()+'&sortorder='+gridP12.getSortOrder(),
				data : objJson,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {	
					if (data.records != 0){
						$("#gbox_gridP12").show();
						$("#AreaResultados").show();
						$(grid.nameJQuery)[0].addJSONData(data);
						grid.actualPage = data.page;
						grid.localData = data;  
						
						if ($("input[name='p12_rad_tipoFiltro']:checked").val() == "2") {
							colReset = true;
						}

						if(colReset){
							if($("#p12_cmb_area").combobox('getValue')=="null" || $("#p12_cmb_area").combobox('getValue')=="" || $("#p12_cmb_area").combobox('getValue')==null){
			        			jQuery(grid.nameJQuery).jqGrid('showCol', ["area"]);
			        		} else{
			        			jQuery(grid.nameJQuery).jqGrid('hideCol', ["area"]);
			        		}
							if($("#p12_cmb_seccion").combobox('getValue')=="null" || $("#p12_cmb_seccion").combobox('getValue')=="" || $("#p12_cmb_seccion").combobox('getValue')==null){
			        			jQuery(grid.nameJQuery).jqGrid('showCol', ["seccion"]);
			        		} else{
			        			jQuery(grid.nameJQuery).jqGrid('hideCol', ["seccion"]);
			        		}
							if($("#p12_cmb_categoria").combobox('getValue')=="null" || $("#p12_cmb_categoria").combobox('getValue')=="" || $("#p12_cmb_categoria").combobox('getValue')==null){
			        			jQuery(grid.nameJQuery).jqGrid('showCol', ["categoria"]);
			        		} else{
			        			jQuery(grid.nameJQuery).jqGrid('hideCol', ["categoria"]);
			        		}
							if($("#p12_cmb_subcategoria").combobox('getValue')=="null" || $("#p12_cmb_subcategoria").combobox('getValue')=="" || $("#p12_cmb_subcategoria").combobox('getValue')==null){
								jQuery(grid.nameJQuery).jqGrid('showCol', ["subcategoria"]);
							}else{
								jQuery(grid.nameJQuery).jqGrid('hideCol', ["subcategoria"]);
							}
							if($("#p12_cmb_segmento").combobox('getValue')=="null"  || $("#p12_cmb_segmento").combobox('getValue')=="" || $("#p12_cmb_segmento").combobox('getValue')==null){
								jQuery(grid.nameJQuery).jqGrid('showCol', ["descripcion"]);
							}else{
								jQuery(grid.nameJQuery).jqGrid('hideCol', ["descripcion"]);
							}		
						}
						
						if (recarga == "N") {
							jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
						} else {
							jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), false);
						}	
					}
					
					$("#AreaResultados .loading").css("display", "none");	
					if (data.records == 0){
						createAlert(replaceSpecialCharacters(emptyRecords), "ERROR");
						$(grid.nameJQuery).jqGrid('clearGridData');
					} 
				},
				error : function (xhr, status, error){
					$("#AreaResultados .loading").css("display", "none");	
					handleError(xhr, status, error, locale);				
		        }			
			});		
	}
}

/*Clase de constantes para le GRID*/
function GridP12 (locale){
		// Atributos
		this.name = "gridP12"; 
		this.nameJQuery = "#gridP12"; 
		this.i18nJSON = './misumi/resources/p12AltasCatalogoRapid/p12altascatalogorapid_' + locale + '.json';
		this.colNames = null;
		this.cm = [	{
			"name" : "area",
			"index":"area",
			"width" : 25,
			"formatter": function(value, options, rData){ return rData['grupo1'] + '-' +value  ;  },
		    "hidden" : false,
		    "editable": true, 
	        "editrules": {edithidden:true} 
		},{
			"name" : "seccion",
			"index":"seccion", 
			"width" : 25,
			"formatter": function(value, options, rData){ return rData['grupo2'] + '-' +value  ;  },
		    "hidden" : false,
		    "editable": true, 
	         "editrules": {edithidden:true} 
		},{
			"name" : "categoria",
			"index":"categoria", 
			"width" : 25,
			"formatter": function(value, options, rData){ return rData['grupo3'] + '-' +value  ;  },
		    "hidden" : false,
		    "editable": true, 
	        "editrules": {edithidden:true} 
		},{
			"name" : "subcategoria",
			"index":"subcategoria", 
			"width" : 25,
			"formatter": function(value, options, rData){ return rData['grupo4'] + '-' +value  ;  },
			"hidden" : false,
			"editable": true, 
	        "editrules": {edithidden:true} 
		
		},{
			"name" : "segmento",
			"index":"segmento", 
			"width" : 25,
			"formatter": function(value, options, rData){ return rData['grupo5'] + '-' +value  ;  },
			"hidden" : false,
			"editable": true, 
	        "editrules": {edithidden:true} 
			     
		},{
			"name" : "codArticulo",
			"index":"codArticulo", 
			"width" : 20     
		},{
			"name" : "descriptionArt",
			"index":"descriptionArt", 
			"width" : 70						
		},{
			"name" : "tpGama",
			"index":"tpGama",
			"width" : 20
		},{
			"name" : "marca",
			"index":"marca", 
			"width" : 25
		},{
			"name" : "uniCajaServ",
			"index":"uniCajaServ", 
			"formatter":"number",
			"width" : 13
		},{
			"name" : "precioCosto",
			"index":"precioCosto",
			"formatter":"number",
			"width" : 15
		},{
			"name" : "pvp",
			"index":"pvp", 
			"formatter":"number",
			"width" : 14
		},{
			"name" : "pvpOferta",
			"index":"pvpOferta",
			"hidden" : true,
			"formatter":"number",
			"width" : 15
		},{
			"name" : "kgUnidad",
			"index":"kgUnidad", 
			"formatter":"number",
			"width" : 14
		},{
			"name" : "formato",
			"index":"formato",
			"width" : 17
		}
	];
	
		
	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP12"; 
	this.pagerNameJQuery = "#pagerP12";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;
	this.myColumnStateName = 'gridP12.colState';
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
			return "grupo1, grupo2, grupo3, grupo4, grupo5, cod_art";
		}
		
	} 
	this.getSortOrder = function getSortOrder () {
		
		if ($(this.nameJQuery).getGridParam('sortorder')!=null){
			return $(this.nameJQuery).getGridParam('sortorder');
		}else{
			return "grupo1, grupo2, grupo3, grupo4, grupo5, cod_art";
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

function p12FormateoABlanco(cellValue, opts, rData) {
	var celdaVacia = '';
	return (celdaVacia);
}

function controlCentro(){
	$( "#centerName" ).bind('focus', function() {
		if ($("#centerName").val()==null || $("#centerName").val()==''){
			cleanFilterSelection(RESET_CONST);
		}
    });
}

function cleanFilterSelection(componentName){
	if (componentName == RESET_CONST){
		$("#p12_cmb_area").combobox(null);
		$("select#p12_cmb_area").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p12_cmb_area").combobox('autocomplete',"");
		$("#p12_cmb_area").combobox('comboautocomplete',null);
		$("#p12_cmb_seccion").combobox(null);
		$("#p12_cmb_subcategoria").combobox(null);
		$("#p12_cmb_categoria").combobox(null);
		$("#p12_cmb_segmento").combobox(null);
		if ($("#centerId").val()!=null && $("#centerId").val()!=''){
			load_cmbArea();
		}
	}
	
	if (componentName == AREA_CONST || componentName == RESET_CONST){
//		$("#p12_cmb_seccion").combobox(null);
		$("select#p12_cmb_seccion").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p12_cmb_seccion").combobox('autocomplete','');
		$("#p12_cmb_seccion").combobox('comboautocomplete',null);
	}
	if(componentName==SECTION_CONST || componentName==AREA_CONST || componentName == RESET_CONST){
	//	$("#p12_cmb_categoria").combobox(null);
		$("select#p12_cmb_categoria").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p12_cmb_categoria").combobox('autocomplete','');
		$("#p12_cmb_categoria").combobox('comboautocomplete',null);
	}
	if(componentName==CATEGORY_CONST || componentName==SECTION_CONST || componentName==AREA_CONST || componentName == RESET_CONST){
		$("select#p12_cmb_subcategoria").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p12_cmb_subcategoria").combobox('autocomplete',"");
		$("#p12_cmb_subcategoria").combobox('comboautocomplete',null);
	}
	if(componentName==SUBCATEGORY_CONST || componentName==CATEGORY_CONST || componentName==SECTION_CONST || componentName==AREA_CONST || componentName == RESET_CONST){
		$("select#p12_cmb_segmento").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p12_cmb_segmento").combobox('autocomplete',"");
		$("#p12_cmb_segmento").combobox('comboautocomplete',null);
	}
	disableFilterSelection(componentName);
	return true;
}

function disableFilterSelection(componentName){
	if (componentName == RESET_CONST){
		if ($("#centerId").val()!=null && $("#centerId").val()!=''){
			$("#p12_cmb_area").combobox("enable");
		}else{
			$("#p12_cmb_area").combobox("disable");
		}
		$("#p12_cmb_seccion").combobox("disable");
		$("#p12_cmb_categoria").combobox("disable");
		$("#p12_cmb_subcategoria").combobox("disable");
		$("#p12_cmb_segmento").combobox("disable");
	}else if (componentName == AREA_CONST){
		$("#p12_cmb_seccion").combobox("enable");
		$("#p12_cmb_categoria").combobox("disable");
		$("#p12_cmb_subcategoria").combobox("disable");
		$("#p12_cmb_segmento").combobox("disable");
		
	} else	if(componentName==SECTION_CONST ){
		$("#p12_cmb_categoria").combobox("enable");
		$("#p12_cmb_subcategoria").combobox("disable");
		$("#p12_cmb_segmento").combobox("disable");
	}
	if(componentName==CATEGORY_CONST ){
		$("#p12_cmb_subcategoria").combobox("enable");
		$("#p12_cmb_segmento").combobox("disable");

	}
	if(componentName==SUBCATEGORY_CONST){
		$("#p12_cmb_segmento").combobox("enable");
	}
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

	    var form = "<form name='csvexportform' action='altasCatalogoRapid/exportGrid.do'  accept-charset='ISO-8859-1' method='get'>";
		form = form + "<input type='hidden' name='headers' value='"+myColumnsNames+"'>";		
		form = form + "<input type='hidden' name='model' value='"+myColumns+"'>";
		if ($("input[name='p12_rad_tipoFiltro']:checked").val() == "1"){
			if ($("#p12_cmb_area").combobox('getValue')!="null" && $("#p12_cmb_area").combobox('getValue')!=null  ){
				form = form + "<input type='hidden' name='grupo1' value='"+$("#p12_cmb_area").val()+"'>";
			}	
			if ($("#p12_cmb_seccion").combobox('getValue')!="null" && $("#p12_cmb_seccion").combobox('getValue')!=null  ){
				form = form + "<input type='hidden' name='grupo2' value='"+$("#p12_cmb_seccion").val()+"'>";
			}	
			if ($("#p12_cmb_categoria").combobox('getValue')!="null" && $("#p12_cmb_categoria").combobox('getValue')!=null  ){
				form = form + "<input type='hidden' name='grupo3' value='"+$("#p12_cmb_categoria").val()+"'>";
			}	
			if ($("#p12_cmb_subcategoria").combobox('getValue')!="null" && $("#p12_cmb_subcategoria").combobox('getValue')!=null  ){
				form = form + "<input type='hidden' name='grupo4' value='"+$("#p12_cmb_subcategoria").val()+"'>";
			}
			if ($("#p12_cmb_segmento").combobox('getValue')!="null" && $("#p12_cmb_segmento").combobox('getValue')!=null ){
				form = form + "<input type='hidden' name='grupo5' value='"+$("#p12_cmb_segmento").val()+"'>";
			}	
		}
		if ($("input[name='p12_rad_tipoFiltro']:checked").val() != "1"){
			if ($("#p12_fld_referencia").val()!="" && $("#p12_fld_referencia").val()!=null ){
				form = form + "<input type='hidden' name='codigoArticulo' value='"+$("#p12_fld_referencia").val()+"'>";
			}
		}	
		form = form + "</form><script>document.csvexportform.submit();</script>";
		Show_Popup(form);	 
	}
}

function mine(value, options, rData){ if (value==0){return '';}else{return value;}}

function formatDescripcionCombo(codigo, descripcion){
	return codigo + "-" + descripcion;
}

function p12FormateoDecimales(valor) {
	if (valor != '')
	{
		//Actualizamos el formateo de los campos a dos decimales
		return $.formatNumber(valor,{format:"0.##",locale:"es"});
	}
	else
	{
		return '';
	}
}
