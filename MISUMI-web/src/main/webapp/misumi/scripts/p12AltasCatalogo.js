var AREA_CONST="area";
var RESET_CONST="reset";
var SECTION_CONST="section";
var CATEGORY_CONST="category";
var SUBCATEGORY_CONST="subcategory"; 
var SEGMENT_CONST="segment";
var grid=null;
var gridTextil=null;
var consultaTextil=null;
var centerRequired=null;
var areaRequired=null;
var sectionRequired=null;
var categoryRequired=null;
var referenceRequired=null;
var centerRequided=null;
var tableFilter=null;
var allOptions=null;
var mensajeAyudaActiva=null;
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
		events_p12_rad_tipoListado(locale);
		events_p12_chk_facingCero();
		events_p12_btn_reset();
		events_p12_btn_buscar();
		events_p12_btn_exportExcel();
		events_p12_btn_ayudaActiva();
	});
	initializeScreenComponents();
});

function initializeScreenComponents(){
	$("#p12_cmb_area").combobox(null);
	$("#p12_cmb_seccion").combobox(null);
	$("#p12_cmb_subcategoria").combobox(null);
	$("#p12_cmb_categoria").combobox(null);
	$("#p12_cmb_segmento").combobox(null);

	$("#p12_cmb_mmc").combobox(null);
	$("#p12_cmb_pedir").combobox(null);
	$("#p12_cmb_activableFacing").combobox(null);
	$("#p12_cmb_catalogo").combobox(null);
	$("#p12_cmb_loteSN").combobox(null);
	
	CargadaEstructuraPantalla(2);
}

function initializeScreen(){
	
	$("#p12_cmb_mmc").combobox(null);
	$("#p12_cmb_pedir").combobox(null);
	$("#p12_cmb_catalogo").combobox(null);
	$("#p12_cmb_activableFacing").combobox(null);
	$("#p12_cmb_loteSN").combobox(null);
	if ($("#centerId").val()!=null && $("#centerId").val()!=''){
		$("#p12_cmb_mmc").combobox('autocomplete',allOptions);
		$("#p12_cmb_pedir").combobox('autocomplete',allOptions);
		$("#p12_cmb_catalogo").combobox('autocomplete',allOptions);
		$("#p12_cmb_activableFacing").combobox('autocomplete',allOptions);
		$("#p12_cmb_loteSN").combobox('autocomplete',allOptions);
	}else{
		$("#p12_cmb_mmc").combobox('autocomplete',"");
		$("#p12_cmb_pedir").combobox('autocomplete',"");
		$("#p12_cmb_catalogo").combobox('autocomplete',"");
		$("#p12_cmb_activableFacing").combobox('autocomplete',"");
		$("#p12_cmb_loteSN").combobox('autocomplete',"");
	}
	$("#p12_cmb_mmc").combobox('comboautocomplete',"null");
	$("#p12_cmb_pedir").combobox('comboautocomplete',"null");
	$("#p12_cmb_catalogo").combobox('comboautocomplete',"null");
	$("#p12_cmb_activableFacing").combobox('comboautocomplete',"null");
	$("#p12_cmb_loteSN").combobox('comboautocomplete',"null");
	
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
		url : './altasCatalogo/loadAreaData.do',
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
//		        	   $("#p12_cmb_area").combobox('autocomplete',ui.item.label);
//		        		$("#p12_cmb_area").combobox('comboautocomplete',ui.item.value);
		        	   
		        	   load_cmbSeccion();
		        	   var result = cleanFilterSelection(AREA_CONST);
			        	hideShowColumns();
			        	jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
		           }
	        	}else{
	        		  var result= cleanFilterSelection(RESET_CONST);
	        	}
	          
	        }  ,
	       
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
		url : './altasCatalogo/loadAreaData.do',
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
	         }  else{
	        	   var result=cleanFilterSelection(AREA_CONST);
	        	}  
	        }  
	        ,		       
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
		url : './altasCatalogo/loadAreaData.do',
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
		url : './altasCatalogo/loadAreaData.do',
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
	          }     else{
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
		url : './altasCatalogo/loadAreaData.do',
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
   		$('#gridP12Textil').jqGrid('clearGridData');
   		
    	if ($("input[name='p12_rad_tipoFiltro']:checked").val() == "1"){
//    		jQuery(grid.nameJQuery).jqGrid('hideCol', ["area","seccion","categoria","subcategoria","descripcion"]);
    		//Estructura comercial
    		$("div#p12_filtroEstructura").attr("style", "display:block");
    		$("div#p12_filtroReferencia").attr("style", "display:none");
    		$("#p12_fld_referencia").val("");
 	   		if (($("input[name='p12_rad_tipoListado']:checked").val() == "1") || ($("input[name='p12_rad_tipoListado']:checked").val() == "3")){
	   			//Datos generales
	   			$("#p12_cmb_pedir").combobox("enable");
		 		$("#p12_cmb_pedir").combobox('autocomplete',allOptions);
		 		$("#p12_cmb_pedir").combobox('comboautocomplete',"null");
	   		} else {
	   			//SFM/CAP
	   			$("#p12_cmb_pedir").combobox('autocomplete','SI');
		 		$("#p12_cmb_pedir").combobox('comboautocomplete',"S");
	   			$("#p12_cmb_pedir").combobox("disable");
	   		}

    	} else { //Referencias
    		
    		$("div#p12_filtroEstructura").attr("style", "display:none");
   		 	$("div#p12_filtroReferencia").attr("style", "display:inline");
   		     var result= cleanFilterSelection(RESET_CONST);
//	   		jQuery(grid.nameJQuery).jqGrid('hideCol', ["area","seccion","categoria","subcategoria","descripcion"]);
	 		$("#p12_cmb_mmc").combobox('autocomplete',allOptions);
	 		$("#p12_cmb_mmc").combobox('comboautocomplete',"null");
	 		if (($("input[name='p12_rad_tipoListado']:checked").val() == "1") || ($("input[name='p12_rad_tipoListado']:checked").val() == "3")){
	   			//Datos generales
	   			$("#p12_cmb_pedir").combobox("enable");
		 		$("#p12_cmb_pedir").combobox('autocomplete',allOptions);
		 		$("#p12_cmb_pedir").combobox('comboautocomplete',"null");
	   		} else {
	   			//SFM/CAP
	   			$("#p12_cmb_pedir").combobox('autocomplete','SI');
		 		$("#p12_cmb_pedir").combobox('comboautocomplete',"S");
	   			$("#p12_cmb_pedir").combobox("disable");
	   		}
	 		$("#p12_cmb_activableFacing").combobox('autocomplete',allOptions);
	 		$("#p12_cmb_activableFacing").combobox('comboautocomplete',"null");
	 		$("#p12_cmb_loteSN").combobox('autocomplete',allOptions);
	 		$("#p12_cmb_loteSN").combobox('comboautocomplete',"null");
	 		$("#p12_fld_referencia").focus();

    	}   
    	
    	hideShowColumns();

    	jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
    	jQuery(gridTextil.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);

    });
}

function events_p12_rad_tipoListado(locale){
    $("input[name='p12_rad_tipoListado']").change(function () {

   		$('#gridP12').jqGrid('clearGridData');
   		
   		$('#gridP12Textil').jqGrid('clearGridData');

   		hideShowColumns();

   		//Control del combo ACTIVA
   		if (($("input[name='p12_rad_tipoListado']:checked").val() == "1") || ($("input[name='p12_rad_tipoListado']:checked").val() == "3")){
   			//Datos generales, en este caso el campo de Activa si se podr� modificar y ser� por defecto TODOS
   			$("#p12_cmb_pedir").combobox("enable");
   			$("#p12_cmb_pedir").combobox('autocomplete',allOptions);
	 		$("#p12_cmb_pedir").combobox('comboautocomplete',"null");
   			
   		} else { 
   			//SFM/CAP, en este caso el campo de Activa no se podr� modificar y ser� por defecto S
   			$("#p12_cmb_pedir").combobox('autocomplete','SI');
	 		$("#p12_cmb_pedir").combobox('comboautocomplete',"S");
   			$("#p12_cmb_pedir").combobox("disable");
   		} 
   		
   		jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
   		jQuery(gridTextil.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
    });
}

function events_p12_chk_facingCero(){
    $('input:checkbox').change(function () {
    	if ($('#p12_chk_facingCero').prop('checked')){
   			$("#p12_cmb_mmc").combobox('autocomplete','SI');
	 		$("#p12_cmb_mmc").combobox('comboautocomplete',"S");
   			$("#p12_cmb_mmc").combobox("disable");

   			$("#p12_cmb_catalogo").combobox('autocomplete','ALTA');
	 		$("#p12_cmb_catalogo").combobox('comboautocomplete',"A");
   			$("#p12_cmb_catalogo").combobox("disable");
    	}else{
   			$("#p12_cmb_mmc").combobox("enable");
	 		$("#p12_cmb_mmc").combobox('autocomplete',allOptions);
	 		$("#p12_cmb_mmc").combobox('comboautocomplete',"null");

	 		$("#p12_cmb_catalogo").combobox("enable");
	 		$("#p12_cmb_catalogo").combobox('autocomplete',allOptions);
	 		$("#p12_cmb_catalogo").combobox('comboautocomplete',"null");
    	}
    });
}

function events_p12_btn_reset(){
	$("#p12_btn_reset").click(function () {
		colReset=true;
		var result=cleanFilterSelection(RESET_CONST);

		$("#p12_cmb_mmc").combobox('autocomplete',allOptions);
		$("#p12_cmb_mmc").combobox('comboautocomplete',"null");
		$("#p12_cmb_catalogo").combobox('autocomplete',allOptions);
		$("#p12_cmb_catalogo").combobox('comboautocomplete',"null");

		//Control del combo ACTIVA
		if (($("input[name='p12_rad_tipoListado']:checked").val() == "1") || ($("input[name='p12_rad_tipoListado']:checked").val() == "3")){
   			//Datos generales, en este caso el campo de Activa si se podr� modificar y ser� por defecto TODOS
   			$("#p12_cmb_pedir").combobox("enable");
   			$("#p12_cmb_pedir").combobox('autocomplete',allOptions);
	 		$("#p12_cmb_pedir").combobox('comboautocomplete',"null");
   			
   		} else { 
   			//SFM/CAP, en este caso el campo de Activa no se podr� modificar y sera por defecto S
   			$("#p12_cmb_pedir").combobox('autocomplete','SI');
	 		$("#p12_cmb_pedir").combobox('comboautocomplete',"S");
   			$("#p12_cmb_pedir").combobox("disable");
   		} 
		$("#p12_cmb_activableFacing").combobox('autocomplete',allOptions);
 		$("#p12_cmb_activableFacing").combobox('comboautocomplete',"null");
 		$("#p12_cmb_loteSN").combobox('autocomplete',allOptions);
 		$("#p12_cmb_loteSN").combobox('comboautocomplete',"null");
		$("#p12_fld_referencia").val("");
		
		$("#p12_chk_facingCero").prop("checked", false);
		
		$('#gridP12').jqGrid('clearGridData');
		$('#gridP12Textil').jqGrid('clearGridData');
		hideShowColumns();
	   	jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
		jQuery(gridTextil.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
	});
}

function events_p12_btn_ayudaActiva(){
	$("#p12_btn_ayudaActiva").click(function () {
   		createAlert(replaceSpecialCharacters(mensajeAyudaActiva), "HELP");
	});
}

function findValidation(){
	var messageVal=null;
	if ($("input[name='p12_rad_tipoFiltro']:checked").val() == "1"){
		//Estructura comercial
		//if ($("#p01_cmb_centerHeaderData").val()=="null" || $("#p01_cmb_centerHeaderData").text()==""){
		if ($("#centerId").val()==null || $("#centerId").val()==""){
			messageVal = centerRequided;
			 
		}else if($("#p12_cmb_area").combobox('getValue')=="null" || $("#p12_cmb_area").combobox('getValue')==null){
			messageVal = areaRequired;
		}else if( $("#p12_cmb_seccion").combobox('getValue')=="null" || $("#p12_cmb_seccion").combobox('getValue')==null){
			if (!$('#p12_chk_facingCero').prop('checked')||($('#p12_chk_facingCero').prop('checked') && $("#p12_cmb_area").combobox('getValue')=="3")){
				messageVal = sectionRequired;
			}
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
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["area","seccion","categoria","subcategoria","descripcion"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["categoria"]);
		
		$('#gridP12Textil').jqGrid('clearGridData');
		jQuery(gridTextil.nameJQuery).jqGrid('hideCol', ["area","seccion","categoria","subcategoria","descripcion"]);
		jQuery(gridTextil.nameJQuery).jqGrid('hideCol', ["categoria"]);
		//jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		$('#p12_btn_buscar').prop("disabled",true); // Desactivar le botón "Buscar" hasta qeu se finalice la operación.
		$('#gridP12').setGridParam({page:1});
		$('#gridP12Textil').setGridParam({page:1});
	//	hideShowColumns();
    	//jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
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

function setHeadersTitlesTextil(data){
	
   	var colModel = $(gridTextil.nameJQuery).jqGrid("getGridParam", "colModel");
    $.each(colModel, function(i) {
    	if (colModel[i].name!="rn"){
	    	$("#jqgh_gridP12Textil_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
    	}
     });
}

function loadP12(locale){
	grid = new GridP12(locale);
	gridTextil = new GridP12Textil(locale);
	
	var jqxhr = $.getJSON(grid.i18nJSON,
			function(data) {

			})
			.success(function(data) {
				grid.colNames = data.p12ColNames;
				gridTextil.colNames = data.p12ColNamesTextil;
				gridTextil.subgridColNames = data.p12ColNamesTextil;
				index = '';
				sortOrder = 'asc';
				grid.title = data.p12GridTitle;
				gridTextil.title = data.p12GridTitle;
				grid.emptyRecords= data.emptyRecords;
				gridTextil.emptyRecords= data.emptyRecords;
				centerRequired=data.centerRequired;
				areaRequired=data.areaRequired;
				sectionRequired=data.sectionRequired;
				categoryRequired=data.categoryRequired;
				referenceRequired = data.referenceRequired;
				centerRequided=data.centerRequided;
				tableFilter= data.tableFilter;
				allOptions=data.allOptions;
				mensajeAyudaActiva=data.mensajeAyudaActiva;
				emptyRecords=data.emptyRecords;
				initializeScreen();
				loadP12Mock(grid);
				loadP12TextilMock(gridTextil);
				setHeadersTitles(data);
				setHeadersTitlesTextil(data);
				
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
                    			//
                    		}
						}		
				); } });
		
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["area"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["seccion"]);
//		jQuery(grid.nameJQuery).jqGrid('hideCol', ["categoria"]);
//		jQuery(grid.nameJQuery).jqGrid('hideCol', ["subcategoria"]);
//		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descripcion"]);
		//jQuery(grid.nameJQuery).jqGrid('hideCol', ["pedible","temporada","anioColeccion","modeloProveedor","talla","color","lote","tempColNumOrden"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["pedible","temporada","anioColeccion","modeloProveedor","talla","color","lote","modeloProveedor"]);
		jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);

}

function loadP12TextilMock(gridTextil) {
	//para hacer cabeceras
		$(gridTextil.nameJQuery).jqGrid({
			ajaxGridOptions : {
				contentType : 'application/json; charset=utf-8',
				cache : false
			},
			datatype : 'local',
			contentType : 'application/json',
			mtype : 'POST',
//			headertitles: true ,
			colNames : gridTextil.colNames,
			colModel : gridTextil.cm,
			rowNum : 10,
			rowList : [ 10, 20, 30 ],
			height : "100%",
			autowidth : true,
			width : "auto",
			rownumbers : true,
			autoencode:true,
			shrinkToFit:true,
			pager : gridTextil.pagerNameJQuery,
			viewrecords : true,
			caption : gridTextil.title,
			altclass: "ui-priority-secondary",
			altRows: true, //false, para que el grid no muestre cebrado
			hidegrid : false, //false, para ocultar el boton que colapsa el grid.
			sortable : false,
			
			subGrid : true,
			//subGridUrl: './misumi/resources/p47PedidoAdicionalEC/p47mockPedidoAdicionalEC.json',
		    subGridRowExpanded: function (subgridDivId, rowId) {
		    	reloadDataP12AltaCatalogoSubgridN2(rowId, subgridDivId);
		    },
			
			index: gridTextil.sortIndex,
			sortname: gridTextil.sortIndex,
			sortorder: gridTextil.sortOrder,
			emptyrecords : gridTextil.emptyRecords,
			gridComplete : function() {

				
			},
			loadComplete : function(data) {
				gridTextil.actualPage = data.page;
				gridTextil.localData = data;
				gridTextil.sortIndex = null;
				gridTextil.sortOrder = null;
				$("#AreaResultados .loading").css("display", "none");	
				if (gridTextil.firstLoad){
					jQuery(gridTextil.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
				}
				
			},
			onPaging : function(postdata) {			
				alreadySorted = false;
				gridTextil.sortIndex = null;
				gridTextil.sortOrder = null;
				gridTextil.saveColumnState.call($(this), this.p.remapColumns);
				//flag para resetear as columnas. True - Resetea. False - No resetea
				colReset=false;
				reloadData('S');
				return 'stop';
			},
			onSelectRow: function(id){

			},
			resizeStop: function () {
				gridTextil.modificado = true;
				gridTextil.saveColumnState.call($(this),gridTextil.myColumnsState);
				jQuery(gridTextil.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), false);
            },
			onSortCol : function (index, columnIndex, sortOrder){
				gridTextil.sortIndex = index;
				gridTextil.sortOrder = sortOrder;
				gridTextil.saveColumnState.call($(this), this.p.remapColumns);
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
		jQuery(gridTextil.nameJQuery).jqGrid('navGrid',gridTextil.pagerNameJQuery,{
				add:false,edit:false,del:false,search:false,refresh:false}
		); 

		
		jQuery(gridTextil.nameJQuery).jqGrid('hideCol', ["area"]);
		jQuery(gridTextil.nameJQuery).jqGrid('hideCol', ["seccion"]);

		jQuery(gridTextil.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);

}

function transformRowFilter(recordAlta){
	return recordAlta;
}

function hideShowColumns(codArea){
//	if (grid.firstLoad == true){
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["cc"]);
		if ($("input[name='p12_rad_tipoFiltro']:checked").val() == "1"){
			//Estructura comercial
			jQuery(grid.nameJQuery).jqGrid('hideCol', ["area"]);
			jQuery(grid.nameJQuery).jqGrid('hideCol', ["seccion"]);
			if($("#p12_cmb_categoria").combobox('getValue')=="null" || $("#p12_cmb_categoria").combobox('getValue')=="" || $("#p12_cmb_categoria").combobox('getValue')==null){
				jQuery(grid.nameJQuery).jqGrid('showCol', ["categoria"]);
			}else{
				jQuery(grid.nameJQuery).jqGrid('hideCol', ["categoria"]);
			}
			if($("#p12_cmb_subcategoria").combobox('getValue')=="null" || $("#p12_cmb_subcategoria").combobox('getValue')=="" || $("#p12_cmb_subcategoria").combobox('getValue')==null){
				jQuery(grid.nameJQuery).jqGrid('showCol', ["subcategoria"]);
			}else{
				jQuery(grid.nameJQuery).jqGrid('hideCol', ["subcategoria"]);
			}
			if($("#p12_cmb_segmento").combobox('getValue')=="null" || $("#p12_cmb_segmento").combobox('getValue')=="" || $("#p12_cmb_segmento").combobox('getValue')==null){
				jQuery(grid.nameJQuery).jqGrid('showCol', ["descripcion"]);
			}else{
				jQuery(grid.nameJQuery).jqGrid('hideCol', ["descripcion"]);
			}
	
		} else { //Referencias
			//jQuery(grid.nameJQuery).jqGrid('showCol', ["area","seccion","categoria","subcategoria","descripcion"]);
		}   
		
		if ($("input[name='p12_rad_tipoListado']:checked").val() == "1"){
			//Datos generales
			//jQuery(grid.nameJQuery).jqGrid('showCol', ["pedir","marcaMaestroCentro","tpGama","tipoAprov","capacidadMax","stockMinComer","capaciMa1","facingMa1","capaciMa2","facingMa2","stockFinMinS","codArtRela","multipli","numeroOferta","ufp","gamaDiscontinua"]);
			jQuery(grid.nameJQuery).jqGrid('showCol', ["pedir","marcaMaestroCentro","catalogo","tpGama","tipoAprov","capacidadMax","stockMinComer","capaciMa1","facingMa1","stockFinMinS","codArtRela","multipli","numeroOferta","ufp","gamaDiscontinua"]);
			jQuery(grid.nameJQuery).jqGrid('hideCol', ["marca","lmin","lsf","tipoImplantacion","coberturaSfm","ventaMedia","ventaAnticipada","diasStock","vidaUtil"]);
			
			if( $("#p12_cmb_area").val()==3 || (codArea != null && codArea == "3") ){
				//Begin columnas de textil
				jQuery(grid.nameJQuery).jqGrid('showCol', ["categoria","temporada","anioColeccion","subcategoria","descripcion","numeroOferta","codArticulo","descriptionArt","color","talla","modeloProveedor","pedible","cc","catalogo"]);
				jQuery(grid.nameJQuery).jqGrid('hideCol', ["capacidadMax","capaciMa1","facingMa1","capaciMa2","facingMa2","stockFinMinS","codArtRela","ufp","gamaDiscontinua","tpGama", "tipoRotacion"]);
			}else{
				jQuery(grid.nameJQuery).jqGrid('hideCol', ["pedible","temporada","anioColeccion","talla","color","lote","modeloProveedor","cc","orden"]);
				jQuery(grid.nameJQuery).jqGrid('showCol', ["tipoRotacion"]);
			}
			
			if( $("#p12_cmb_area").val()==4 || (codArea != null && codArea == "4") ){
				//Begin columnas de BAZAR					
				jQuery(grid.nameJQuery).jqGrid('hideCol', ["capacidadMax","capaciMa1","facingMa1","capaciMa2","facingMa2","stockFinMinS","codArtRela","ufp","gamaDiscontinua"]);
				jQuery(grid.nameJQuery).jqGrid('showCol', ["cc"]);
			} else{
				//jQuery(grid.nameJQuery).jqGrid('showCol', ["capacidadMax","capaciMa1","facingMa1","capaciMa2","facingMa2","stockFinMinS","codArtRela","ufp","gamaDiscontinua"]);
				jQuery(grid.nameJQuery).jqGrid('showCol', ["capacidadMax","capaciMa1","facingMa1","stockFinMinS","codArtRela","ufp","gamaDiscontinua"]);
				jQuery(grid.nameJQuery).jqGrid('hideCol', ["cc"]);
			}
			
			if($("#p12_cmb_area").val()==5 || (codArea != null && codArea == "5")){
				jQuery(grid.nameJQuery).jqGrid('hideCol', ["ufp","area","seccion","categoria","subcategoria","descripcion","codArticulo","descriptionArt","pedir","marcaMaestroCentro","tpGama","marca","stock","diasStock","tipoAprov","uniCajaServ","capacidadMax","lmin","lsf", "stockMinComer","capaciMa1","facingMa1","capaciMa2","facingMa2","stockFinMinS","coberturaSfm","codArtRela","numeroOferta", "ufp","gamaDiscontinua","ventaMedia","ventaAnticipada","vidaUtil","cc","pedible","temporada","anioColeccion","talla","color","lote","tempColNumOrden","modeloProveedor"]);
				jQuery(grid.nameJQuery).jqGrid('showCol', ["categoria","subcategoria","descripcion","codArticulo","descriptionArt","cc","activa","pedir","marcaMaestroCentro","tpGama","stock","diasStock","tipoAprov", "uniCajaServ","stockMinComer"]);
			}
			
		} else { 
			
			//SFM/CAP
			jQuery(grid.nameJQuery).jqGrid('showCol', ["marca","lmin","lsf","coberturaSfm","ventaMedia","ventaAnticipada","diasStock","vidaUtil"]);
	
			if ($("input[name='p12_rad_tipoFiltro']:checked").val() == "1" ){
				jQuery(grid.nameJQuery).jqGrid('hideCol', ["area","seccion","categoria","subcategoria","descripcion","pedir","marcaMaestroCentro","catalogo","tipoImplantacion","tpGama","tipoAprov","stockMinComer","capaciMa1","facingMa1","capaciMa2","facingMa2","codArtRela","multipli","numeroOferta","ufp","gamaDiscontinua"]);
			}else{
				jQuery(grid.nameJQuery).jqGrid('hideCol', ["pedir","marcaMaestroCentro","catalogo","tipoImplantacion","tpGama","tipoAprov","stockMinComer","capaciMa1","facingMa1","capaciMa2","facingMa2","codArtRela","multipli","numeroOferta","ufp","gamaDiscontinua"]);
			}
			
			if( $("#p12_cmb_area").val()==3 || (codArea != null && codArea == "3") ){
				//Begin columnas de textil
				jQuery(grid.nameJQuery).jqGrid('showCol', ["pedible","temporada","anioColeccion","modeloProveedor","talla","color","lote","tempColNumOrden"]);
			}else{
				jQuery(grid.nameJQuery).jqGrid('hideCol', ["pedible","temporada","anioColeccion","talla","color","lote","modeloProveedor","catalogo","tipoImplantacion"]);
			}
		}
}

function reloadData(recarga) {

	var gridP12 = null;
	var messageVal=findValidation();
		if ($("input[name='p12_rad_tipoListado']:checked").val() == "3"){ 
			gridP12 = gridTextil;
		} else { 
			gridP12 = grid;
		}	
	
	if(recarga == "N"){
		if(colReset){
		hideShowColumns();
		}
	}
	
	if (gridP12.firstLoad) {
		jQuery(gridP12.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
		gridP12.firstLoad = false;
        if (gridP12.isColState) {
            $(this).jqGrid("remapColumns", gridP12.myColumnsState.permutation, true);
        }
    } else {
    	//gridP12.myColumnsState = gridP12.restoreColumnState(gridP12.cm);
    	//gridP12.isColState = typeof (gridP12.myColumnsState) !== 'undefined' && gridP12.myColumnsState !== null;
    	jQuery(gridP12.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), false);
    }
	
	var messageVal=findValidation();
	if (messageVal!=null){
		$('#gridP12').jqGrid('clearGridData');
		$('#gridP12Textil').jqGrid('clearGridData');
		//jQuery(gridP12.nameJQuery).jqGrid('hideCol', ["area","seccion","categoria","subcategoria","descripcion"]);
		//jQuery(gridTextil.nameJQuery).jqGrid('hideCol', ["area","seccion","categoria","subcategoria","descripcion"]);
		
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		
		var recordAlta = new VArtCentroAlta (new Centro( $("#centerId").val(),null,null), $("#p12_fld_referencia").val(), null, 'I5', $("#p12_cmb_area").combobox('getValue'), $("#p12_cmb_seccion").combobox('getValue'), $("#p12_cmb_categoria").combobox('getValue'),
				$("#p12_cmb_subcategoria").combobox('getValue'), $("#p12_cmb_segmento").combobox('getValue'), null, $("#p12_cmb_pedir").combobox('getValue'), $("#p12_cmb_mmc").combobox('getValue'), 
				$("#p12_cmb_catalogo").combobox('getValue'), 
				null,null,null,	null, null, null, null, null, null,	null, null, null,null, 
				$("#p12_cmb_activableFacing").combobox('getValue'),null,$("#p12_cmb_loteSN").combobox('getValue'),
				$('#p12_chk_facingCero').prop('checked') ? 'S': 'N' /*facing cero*/
				);
	
		var objJson = $.toJSON(recordAlta.prepareVArtCentroAltaToJsonObject());

			//$("#AreaResultados .loading").css("display", "block");
			$.ajax({
				type : 'POST',
				url : './altasCatalogo/loadDataGrid.do?page='+gridP12.getActualPage()+'&max='+gridP12.getRowNumPerPage()+'&index='+gridP12.getSortIndex()+'&sortorder='+gridP12.getSortOrder(),
				data : objJson,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {	
							
					//if( $("#p12_cmb_area").val()==3 )	{ //Cargamos el grid de textil
					if (data.records != 0){
						if ((( data.rows[0].grupo1 == 3 ) && ($("input[name='p12_rad_tipoListado']:checked").val() == "3")) ||
						   (( data.rows[0].grupo1 == 3 ) && ($("input[name='p12_rad_tipoFiltro']:checked").val() == "2"))) {
							$("#gbox_gridP12").hide();
							$("#gbox_gridP12Textil").show();
							consultaTextil = "S"
							$("#AreaResultados").show();
							
							$(gridTextil.nameJQuery)[0].addJSONData(data);
							gridTextil.actualPage = data.page;
							gridTextil.localData = data;  
							
							p12ControlesPantallaTextil();

							if (($("input[name='p12_rad_tipoFiltro']:checked").val() == "2") && (data.rows[0].nivelLote >0)){ //La consulta ha sido por referencia 
								$("#"+data.rows[0].id+" td.sgcollapsed",gridTextil[0]).click();
							}

							/*
							jQuery(gridTextil.nameJQuery).jqGrid('hideCol', ["area"]);
							jQuery(gridTextil.nameJQuery).jqGrid('hideCol', ["seccion"]);
							if($("#p12_cmb_categoria").combobox('getValue')=="null" || $("#p12_cmb_categoria").combobox('getValue')=="" || $("#p12_cmb_categoria").combobox('getValue')==null){
			        			jQuery(gridTextil.nameJQuery).jqGrid('showCol', ["categoria"]);
			        		} else{
			        			jQuery(gridTextil.nameJQuery).jqGrid('hideCol', ["categoria"]);
			        		}
							if($("#p12_cmb_subcategoria").combobox('getValue')=="null" || $("#p12_cmb_subcategoria").combobox('getValue')=="" || $("#p12_cmb_subcategoria").combobox('getValue')==null){
								jQuery(gridTextil.nameJQuery).jqGrid('showCol', ["subcategoria"]);
							}else{
								jQuery(gridTextil.nameJQuery).jqGrid('hideCol', ["subcategoria"]);
							}
							if($("#p12_cmb_segmento").combobox('getValue')=="null"  || $("#p12_cmb_segmento").combobox('getValue')=="" || $("#p12_cmb_segmento").combobox('getValue')==null){
								jQuery(gridTextil.nameJQuery).jqGrid('showCol', ["descripcion"]);
							}else{
								jQuery(gridTextil.nameJQuery).jqGrid('hideCol', ["descripcion"]);
							}
							*/
							
							if (recarga == "N") {
								jQuery(gridTextil.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
							}
							
						}else {
							
							$("#gbox_gridP12Textil").hide();
							$("#gbox_gridP12").show();
							$("#AreaResultados").show();
							
							consultaTextil = "N";

							$(grid.nameJQuery)[0].addJSONData(data);
							grid.actualPage = data.page;
							grid.localData = data;  

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
							
							if ($("input[name='p12_rad_tipoFiltro']:checked").val() == "2") {
								colReset = true;
							}

							if(colReset){
								jQuery(grid.nameJQuery).jqGrid('hideCol', ["area"]);
								jQuery(grid.nameJQuery).jqGrid('hideCol', ["seccion"]);
								
								if (($("input[name='p12_rad_tipoListado']:checked").val() == "1") 
										&& (data.rows[0].grupo1 == "4")){ //datos generales y para una referencia de BAZAR
									
									jQuery(grid.nameJQuery).jqGrid('hideCol', ["capacidadMax","capaciMa1","facingMa1","capaciMa2","facingMa2","stockFinMinS","codArtRela","ufp","gamaDiscontinua"]);
									jQuery(grid.nameJQuery).jqGrid('showCol', ["cc"]);

								} else {
									
									//jQuery(grid.nameJQuery).jqGrid('showCol', ["capacidadMax","capaciMa1","facingMa1","capaciMa2","facingMa2","stockFinMinS","codArtRela","ufp","gamaDiscontinua"]);
									jQuery(grid.nameJQuery).jqGrid('showCol', ["capacidadMax","capaciMa1","facingMa1","stockFinMinS","codArtRela","ufp","gamaDiscontinua"]);
									jQuery(grid.nameJQuery).jqGrid('hideCol', ["cc"]);
								}

								if (($("input[name='p12_rad_tipoListado']:checked").val() == "1") 
										&& (data.rows[0].grupo1 == "5")){ //datos generales y para una referencia de ELECTRO
									
									jQuery(grid.nameJQuery).jqGrid('hideCol', ["ufp","area","seccion","categoria","subcategoria","descripcion","codArticulo","descriptionArt","pedir","marcaMaestroCentro","tpGama","marca","stock","diasStock","tipoAprov","uniCajaServ","capacidadMax","lmin","lsf", "stockMinComer","capaciMa1","facingMa1","capaciMa2","facingMa2","stockFinMinS","coberturaSfm","codArtRela","numeroOferta", "ufp","gamaDiscontinua","ventaMedia","ventaAnticipada","vidaUtil","cc","pedible","temporada","anioColeccion","talla","color","lote","tempColNumOrden","modeloProveedor"]);
									jQuery(grid.nameJQuery).jqGrid('showCol', ["categoria","subcategoria","descripcion","codArticulo","descriptionArt","cc","activa","pedir","marcaMaestroCentro","tpGama","stock","diasStock","tipoAprov", "uniCajaServ","stockMinComer","tipoRotacion"]);
									
								}
							}

							if (recarga == "N") {
								jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
							} else {
								jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), false);
							}

						}
					}

					$("#AreaResultados .loading").css("display", "none");
					$('#p12_btn_buscar').prop("disabled",false); // Activar el botón "Buscar".

					if (data.records == 0){
						createAlert(replaceSpecialCharacters(emptyRecords), "ERROR");
						$(grid.nameJQuery).jqGrid('clearGridData');
					}
				},
				error : function (xhr, status, error){
					$("#AreaResultados .loading").css("display", "none");
					$('#p12_btn_buscar').prop("disabled",false); // Activar el botón "Buscar".
					handleError(xhr, status, error, locale);
		        }
			});
	}
}

function p12ControlesPantallaTextil(){

	//Nos recorremos los registros existentes en pantalla.
	var rowsInPage = jQuery(gridTextil.nameJQuery).jqGrid('getGridParam', 'rowNum');
	var existenRefConHijas = 0;
	
	for (i = 0; i < rowsInPage; i++){
		var idToDataIndex = $(gridTextil.nameJQuery).jqGrid('getDataIDs');
		//Después busco dentro del array de rowId's el correspondiente a la fila
		var idFila = idToDataIndex[i];

		nivel = $("#"+(idFila)+"_nivelLote").val();

		//Si el nivel es 0 oculto el signo +
		if (nivel == '0'){
			$("#"+idFila+" td.sgcollapsed",gridTextil[0]).unbind('click').html('');
			
		} else {
			existenRefConHijas = 1;
		}
	}
	
	
	if (existenRefConHijas == 1) {
		$("#jqgh_gridP12Textil_subgrid").html("<span id ='iconPlusExpandir' class='ui-icon ui-icon-plus'></span>");
		$("#iconPlusExpandir").click(function() {
			p12expandirLotes();
		});
		existenRefConHijas = 0;
	} else {
		$("#jqgh_gridP12Textil_subgrid").html("");
	} 
	
}

function p12expandirLotes() {
	
	//Nos recorremos los registros existentes en pantalla.
	var rowsInPage = jQuery(gridTextil.nameJQuery).jqGrid('getGridParam', 'rowNum');
	
	for (i = 0; i < rowsInPage; i++){
		var idToDataIndex = $(gridTextil.nameJQuery).jqGrid('getDataIDs');
		//Después busco dentro del array de rowId's el correspondiente a la fila
		var idFila = idToDataIndex[i];
		nivel = $("#"+(idFila)+"_nivelLote").val();
		
		//Si el nivel es distinto de  0. LLamamos almetodo que obtiene las referencias hijas
		if (nivel != '0'){
			
			$("#"+idFila+" td.sgcollapsed",gridTextil[0]).click();
		//	$("#"+idFila+" td.sgexpanded",gridTextil[0]).click();
			
		} 
	}
}

function reloadDataP12AltaCatalogoSubgridN2(rowId, subgridDivId) {
	
	var codId = rowId.substr(rowId.indexOf("_")+1); //Identificador para enlazar con las referencias hijas
			
	var recordAlta = new VArtCentroAlta (new Centro( $("#centerId").val(),null,null) , null, null, null, null, null, null,
										null, null, null, null, null,
										null, /*catalogo*/
										null,null,null,	null, null, null, null, null, null,
										null, null, null,null, null,codId,null, 
										null, /*facing cero*/
										);

	var objJson = $.toJSON(recordAlta.prepareVArtCentroAltaToJsonObject());

		//$("#AreaResultados .loading").css("display", "block");
		
		$.ajax({
			type : 'POST',
			url : './altasCatalogo/loadDataSubGridTextil.do?',
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {	

				var subgridTableId = subgridDivId + "_t";
		        $("#" + subgridDivId).html("<table id='" + subgridTableId + "'></table>");
		        $("#" + subgridTableId).jqGrid({
		            datatype: 'local',
		            colNames : gridTextil.subgridColNames,
		    		colModel : gridTextil.subgridCm,
		    		//multiselect: true,
		    		altclass: "ui-priority-secondary",
		    		altRows: false,
		    		//rowNum: data.datos.records,
		    		sortable : true,
		    		height: '100%',
		    		width : "auto"
		        });

		        $("#" + subgridTableId)[0].addJSONData(data);
		        $("#" + subgridTableId).actualPage = data.page;
		        $("#" + subgridTableId).localData = data;

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



/*Clase de constantes para le GRID*/
function GridP12 (locale){
		// Atributos
		this.name = "gridP12"; 
		this.nameJQuery = "#gridP12"; 
		this.i18nJSON = './misumi/resources/p12AltasCatalogo/p12altascatalogo_' + locale + '.json';
		this.colNames = null;
		this.cm = [	{
			"name" : "area",
			"index":"area",
		
			"width" : 25,
			"formatter": function(value, options, rData){ return rData['grupo1'] + '-' +value  ;  },
		    "hidden" : true,
		    "editable": true, 
	        "editrules": {edithidden:true} 
		}
		,{
			"name" : "seccion",
			"index":"seccion", 
			"width" : 25,
			"formatter": function(value, options, rData){ return rData['grupo2'] + '-' +value  ;  },
		    "hidden" : true,
		    "editable": true, 
	          "editrules": {edithidden:true} 
		}
		,{
			"name" : "categoria",
			"index":"categoria", 
			"width" : 25,
			"formatter": function(value, options, rData){ return rData['grupo3'] + '-' +value  ;  },
		   "hidden" : false,
		    "editable": true, 
	          "editrules": {edithidden:true} 
		}
		,{
			"name" : "subcategoria",
			"index":"subcategoria", 
			"width" : 25,
			"formatter": function(value, options, rData){ return rData['grupo4'] + '-' +value  ;  },
			   "hidden" : false,
			    "editable": true, 
	              "editrules": {edithidden:true} 
		
		},
	  {
			"name" : "descripcion",
			"index":"descripcion", 
			"width" : 15,
			
			"formatter": function(value, options, rData){ return rData['grupo5'] + '-' +value  ;  },
			  "hidden" : false,
			    "editable": true, 
	              "editrules": {edithidden:true} 
			     
		},
		{
			"name" : "codArticulo",
			"index":"codArticulo", 
			"width" : 30
			     
		},{
			"name" : "descriptionArt",
			"index":"descriptionArt", 
			"width" : 70						
		},{
			"name" : "cc",
			"index":"cc", 
			"sortable":false,
			"hidden" : true,
			"width" : 6
		},{
			"name" : "pedir",
			"index":"pedir", 
			"width" : 6
		},{                                                                                                                    
			"name" : "marcaMaestroCentro",
			"index":"marcaMaestroCentro", 
			"width" : 9
		},{                                                                                                                    
			"name" :"catalogo",
			"index":"catalogo", 
			"width":9
		},{
			"name" :"tpGama",
			"index":"tpGama",
			"width" : 20
		},{
			"name" : "marca",
			"index":"marca", 
			"hidden" : true,
			"width" : 15
		},{
			"name" : "stock",
			"index":"stock", 
			"formatter":"number",
			"width" : 15
		},{
			"name" : "diasStock",
			"index":"diasStock", 
			"hidden" : true,
			"formatter":"number",
			"width" : 15
		},{
			"name" : "tipoRotacion",
			"index":"TIPO_ROT", 
			"sortable":true,
			"width" : 20,
			//"fixed":true,
			//"resizable":false
		},{
			"name" : "tipoAprov",
			"index":"tipoAprov", 
			"width" : 10
		},{
			"name" : "uniCajaServ",
			"index":"uniCajaServ", 
			"formatter":"number",
			"width" : 15
		},{
			"name" : "capacidadMax",
			"index":"capacidadMax", 
			"formatter":"integer",
			"width" : 14
		},{
			"name" : "lmin",
			"index":"lmin",
			"formatter":"number",
			"hidden" : true,
			"width" : 15
		},{
			"name" : "lsf",
			"index":"lsf", 
			"formatter":"number",
			"hidden" : true,
			"width" : 15
		},{
			"name" : "stockMinComer",
			"index":"stockMinComer", 
			"formatter":"integer",
			"width" : 14
		},{
			"name" : "tipoImplantacion",
			"index":"tipoImplantacion", 
			"hidden" : true,
			"width" : 15,
		},{
			"name" : "alto",
			"index":"alto", 
			"hidden" : true,
			"width" : 15,
		},{
			"name" : "ancho",
			"index":"ancho", 
			"hidden" : true,
			"width" : 15,
		},{
			"name" : "capaciMa1",
			"index":"capaciMa1", 
			"formatter":"integer",
			"width" : 17
		},{
			"name" : "facingMa1",
			"index":"facingMa1", 
			"formatter":"integer",
			"width" : 16
		},{
			"name" : "capaciMa2",
			"index":"capaciMa2", 
			"formatter":"integer",
			"hidden" : true,
			"width" : 17
		},{
			"name" : "facingMa2",
			"index":"facingMa2", 
			"formatter":"integer",
			"hidden" : true,
			"width" : 16
		},{
			"name" : "stockFinMinS",
			"index":"stockFinMinS", 
			"formatter":"number",
			"width" : 15
		},{
			"name" : "coberturaSfm",
			"index":"coberturaSfm", 
			"formatter":"number",
			"hidden" : true,
			"width" : 15
		},{
			"name" : "codArtRela",
			"index":"codArtRela", 
			"width" : 15	,
			"formatter": FFPPFormatter
		},{
			"name" : "multipli",
			"index":"multipli", 
			"width" : 15
		},{
			"name" : "imc",
			"index":"imc", 
			"formatter":"integer",
			"width" : 15
		},{
			"name" : "numeroOferta",
			"index":"numeroOferta", 
			"width" : 18
		},{
			"name" : "ufp",
			"index":"ufp 	",
			"width" : 15
		},{
			"name" : "gamaDiscontinua",
			"index":"gamaDiscontinua", 
			"width" : 15
		},{
			"name" : "ventaMedia",
			"index":"ventaMedia", 
			"formatter":"number",
			"hidden" : true,
			"width" : 15
		},{
			"name" : "ventaAnticipada",
			"index":"ventaAnticipada", 
			"formatter":"number",
			"hidden" : true,
			"width" : 15
		},{
			"name" : "vidaUtil",
			"index":"vidaUtil", 
			"formatter":"number",
			"hidden" : true,
			"width" : 15
		},{
			"name" : "pedible",
			"index":"pedible", 
			"sortable":false,
			"width" : 15
		},{
			"name" : "temporada",
			"index":"temporada", 
			"sortable":false,
			"width" : 15
		},{
			"name" : "anioColeccion",
			"index":"anioColeccion", 
			"sortable":false,
			"width" : 15
		},{
			"name" : "talla",
			"index":"talla", 
			"sortable":false,
			"width" : 15
		},{
			"name" : "color",
			"index":"color", 
			"sortable":false,
			"width" : 15
		},{
			"name" : "lote",
			"index":"lote", 
			"sortable":false,
			"width" : 15
		},/*{
			"name" : "tempColNumOrden",
			"index":"tempColNumOrden", 
			"sortable":false,
			"width" : 15
		}*/
		{
			"name" : "modeloProveedor",
			"index":"modeloProveedor", 
			"sortable":false,
			"width" : 15,
			"hidden":false
		} ,
		{
			"name" : "orden",
			"index":"orden", 
			"sortable":false,
			"width" : 40,
			"formatter": p12FormatOrden,
			"hidden":true
		},{
			"name" : "accion",
			"index":"accion", 
			"sortable":true,
			"width" : 20,
			//"fixed":true,
			//"resizable":false
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


/*Clase de constantes para el GRID de Textil*/
function GridP12Textil (locale){
		// Atributos
		this.name = "gridP12Textil"; 
		this.nameJQuery = "#gridP12Textil"; 
		this.i18nJSON = './misumi/resources/p12AltasCatalogo/p12altascatalogo_' + locale + '.json';
		this.colNames = null;
		this.cm = [	
		{
			"name" : "nivelLote",
			"index":"nivelLote",
			"formatter": p12FormaterNivelLote,
		    "hidden" : true
		}
		,{
			"name" : "area",
			"index":"area",
			"formatter": function(value, options, rData){ return rData['grupo1'] + '-' +value  ;  },
		    "hidden" : true
		}
		,{
			"name" : "seccion",
			"index":"seccion", 
			"formatter": function(value, options, rData){ return rData['grupo2'] + '-' +value  ;  },
		    "hidden" : true
		}
		,{
			"name" : "categoria",
			"index":"categoria", 
			"formatter": function(value, options, rData){ return rData['grupo3'] + '-' +value  ;  },
		    "hidden" : true
		}
		,{
			"name" : "subcategoria",
			"index":"subcategoria", 
			"formatter": function(value, options, rData){ return rData['grupo4'] + '-' +value  ;  },
		    "hidden" : true
		
		},{
			"name" : "descripcion",
			"index":"descripcion", 
			"formatter": function(value, options, rData){ return rData['grupo5'] + '-' +value  ;  },
		    "hidden" : true
		},
		{
			"name" : "codArticulo",
			"index":"codArticulo", 
			"width" : 62,
			"fixed":true,
			"resizable":false
		},{
			"name" : "descriptionArt",
			"index":"descriptionArt", 
			"width" : 150,
			"fixed":true,
			"resizable":false
		},{
			"name" : "pedir",
			"index":"pedir", 
			"width" : 40,
			"fixed":true,
			"resizable":false
		},{                                                                                                                    
			"name":"marcaMaestroCentro",
			"index":"marcaMaestroCentro", 
			"width":35,
			"fixed":true,
			"resizable":false
		},{                                                                                                                    
			"name":"catalogo",
			"index":"catalogo", 
			"width":10,
			"fixed":true,
			"resizable":false
		},{
			"name" : "cc",
			"index":"cc",
			"width" : 25,
			"fixed":true,
			"resizable":false
		},{
			"name" : "stock",
			"index":"stock", 
			"formatter": p12FormateoDecimales,
			"width" : 40,
			"fixed":true,
			"resizable":false
		},{
			"name" : "tipoAprov",
			"index":"tipoAprov", 
			"width" : 30,
			"fixed":true,
			"resizable":false
		},{
			"name" : "uniCajaServ",
			"index":"uniCajaServ", 
			"formatter": p12FormateoDecimales,
			"width" : 30,
			"fixed":true,
			"resizable":false
		},{
			"name" : "stockMinComer",
			"index":"stockMinComer", 
			"formatter":"integer",
			"width" : 14,
			"width" : 30,
			"fixed":true,
			"resizable":false
		},{
			"name":"tipoImplantacion",
			"index":"tipoImplantacion", 
			"hidden":true,
			"width":15
		},{
			"name" : "alto",
			"index":"alto", 
			"hidden" : true,
			"width" : 15
		},{
			"name" : "ancho",
			"index":"ancho", 
			"hidden" : true,
			"width" : 15
		},/*{
			"name" : "facingMa1",
			"index":"facingMa1", 
			"formatter":"integer",
			"width" : 40,
			"fixed":true,
			"resizable":false
		},*/{
			"name" : "multipli",
			"index":"multipli", 
			"width" : 30,
			"fixed":true,
			"resizable":false
		},{
			"name" : "imc",
			"index":"imc", 
			"width" : 30,
			"fixed":true,
			"resizable":false
		},{
			"name" : "numeroOferta",
			"index":"numeroOferta", 
			"width" : 70,
			"fixed":true,
			"resizable":false
		},{
			"name" : "pedible",
			"index":"pedible", 
			"sortable":false,
			"width" : 30,
			"fixed":true,
			"resizable":false
		},{
			"name" : "temporada",
			"index":"temporada", 
			"width" : 40,
			"fixed":true,
			"resizable":false
		},{
			"name" : "anioColeccion",
			"index":"anioColeccion", 
			"width" : 40,
			"fixed":true,
			"resizable":false
		},{
			"name" : "modeloProveedor",
			"index":"modeloProveedor", 	
			"width" : 55,
			"fixed":true,
			"resizable":false
		},{
			"name" : "talla",
			"index":"talla", 
			"width" : 40,
			"fixed":true,
			"resizable":false,
			"frozen":true
		
		},{
			"name" : "color",
			"index":"color", 
			"width" : 45,
			"fixed":true,
			"resizable":false
		},{
			"name" : "accion",
			"index":"accion", 
			"sortable":true,
			"width" : 20,
			"fixed":true,
			"resizable":false
		}
//		,{
//			"name" : "tipoRotacion",
//			"index":"TIPO_ROT", 
//			"sortable":true,
//			"width" : 20,
//			"hidden" : true,
//			//"resizable":false
//		}  
		
	];	
	this.subgridColNames = null;
	this.subgridCm = [{
			"name" : "nivelLote",
			"index":"nivelLote",
			"formatter": p12FormaterNivelLote,
		    "hidden" : true
		},{
			"name" : "area",
			"index":"area",
			"formatter": function(value, options, rData){ return rData['grupo1'] + '-' +value  ;  },
			"hidden" : true
		},{
			"name" : "seccion",
			"index":"seccion", 
			"formatter": function(value, options, rData){ return rData['grupo2'] + '-' +value  ;  },
			"hidden" : true
		},{
			"name" : "categoria",
			"index":"categoria", 
			"formatter": function(value, options, rData){ return rData['grupo3'] + '-' +value  ;  },
			"hidden" : true
		},{
			"name" : "subcategoria",
			"index":"subcategoria", 
			"formatter": function(value, options, rData){ return rData['grupo4'] + '-' +value  ;  },
			"hidden" : true
		
		},{
			"name" : "descripcion",
			"index":"descripcion", 
			"formatter": function(value, options, rData){ return rData['grupo5'] + '-' +value  ;  },
			"hidden" : true	     
		},{
			"name" : "codArticulo",
			"index":"codArticulo", 
			"width" : 60,
			"fixed":true,
			"resizable":false
		},{
			"name" : "descriptionArt",
			"index":"descriptionArt", 
			"formatter":p12FormateoABlanco,
			"width" : 155,
			"fixed":true,
			"resizable":false
		},{
			"name" : "pedir",
			"index":"pedir", 
			"formatter":p12FormateoABlanco,
			"width" : 40,
			"fixed":true,
			"resizable":false
		},{                                                                                                                    
			"name" : "marcaMaestroCentro",
			"index":"marcaMaestroCentro", 
			"width" : 35,
			"fixed":true,
			"resizable":false
		},{                                                                                                                    
			"name":"catalogo",
			"index":"catalogo", 
			"width":10
		},{
			"name":"cc",
			"index":"cc",
			"formatter":p12FormateoABlanco,
			"width" : 25,
			"fixed":true,
			"resizable":false
		},{
			"name" : "stock",
			"index":"stock", 
			"formatter":"number",
			"width" : 40,
			"fixed":true,
			"resizable":false
		},{
			"name" : "tipoAprov",
			"index":"tipoAprov", 
			"formatter":p12FormateoABlanco,
			"width" : 30,
			"fixed":true,
			"resizable":false
		},{
			"name" : "uniCajaServ",
			"index":"uniCajaServ", 
			"formatter":"number",
			"width" : 30,
			"fixed":true,
			"resizable":false
		},{
			"name" : "stockMinComer",
			"index":"stockMinComer", 
			"formatter":"integer",
			"width" : 30,
			"fixed":true,
			"resizable":false
		},{
			"name" : "tipoImplantacion",
			"index":"tipoImplantacion", 
			"hidden" : true,
			"width" : 15,
		},{
			"name" : "alto",
			"index":"alto", 
			"hidden" : true,
			"width" : 15,
		},{
			"name" : "ancho",
			"index":"ancho", 
			"hidden" : true,
			"width" : 15,
		},{
			"name" : "multipli",
			"index":"multipli", 
			"formatter":p12FormateoABlanco,
			"width" : 30,
			"fixed":true,
			"resizable":false
		},{
			"name" : "imc",
			"index":"imc", 
			"width" : 30,
			"fixed":true,
			"resizable":false
		},{
			"name" : "numeroOferta",
			"index":"numeroOferta", 
			"width" : 70,
			"fixed":true,
			"resizable":false
		},{
			"name" : "pedible",
			"index":"pedible", 
			"formatter":p12FormateoABlanco,
			"sortable":false,
			"width" : 30,
			"fixed":true,
			"resizable":false
		},{
			"name" : "temporada",
			"index":"temporada", 
			"formatter":p12FormateoABlanco,
			"sortable":false,
			"width" : 40,
			"fixed":true,
			"resizable":false
		},{
			"name" : "anioColeccion",
			"index":"anioColeccion", 
			"formatter":p12FormateoABlanco,
			"sortable":false,
			"width" : 40,
			"fixed":true,
			"resizable":false
		},{
			"name" : "modeloProveedor",
			"index":"modeloProveedor", 	
			"formatter":p12FormateoABlanco,
			"sortable":false,
			"width" : 55,
			"fixed":true,
			"resizable":false
		},{
			"name" : "talla",
			"index":"talla", 
			"sortable":false,
			"width" : 40,
			"fixed":true,
			"resizable":false
		},{
			"name" : "color",
			"index":"color", 
			"sortable":false,
			"width" : 44,
			"fixed":true,
			"resizable":false
		},{
			"name" : "accion",
			"index":"accion", 
			"sortable":true,
			"width" : 20,
			"fixed":true,
			"resizable":false
		}
//	,{
//		"name" : "tipoRotacion",
//		"index":"TIPO_ROT", 
//		"sortable":true,
//		"width" : 20,
//		"hidden" : true,
//		//"resizable":false
//	}
   ];		
		
	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP12Textil"; 
	this.pagerNameJQuery = "#pagerP12Textil";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;
	this.myColumnStateName = 'gridP12Textil.colState';
    this.myColumnsState = null;
    this.isColState = null;
    this.firstLoad = true;
    this.modificado = false;
    
	//Metodos
	this.getActualPage = function getActualPage(){ 
		if (this.actualPage == null){
			this.actualPage = 1;
		}else{
			this.actualPage = $(this.nameJQuery).getGridParam('page');
		}
		
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
         var colModel =jQuery(gridTextil.nameJQuery).jqGrid('getGridParam', 'colModel'); 
         //if (colModel == null)
        //	 colModel = grid.cm;
         var i;
         var l = colModel.length;
         var colItem; 
         var cmName;
         var postData = jQuery(gridTextil.nameJQuery).jqGrid('getGridParam', 'postData');
         var columnsState = {
                 search: jQuery(gridTextil.nameJQuery).jqGrid('getGridParam', 'search'),
                 page: jQuery(gridTextil.nameJQuery).jqGrid('getGridParam', 'page'),
                 sortname: jQuery(gridTextil.nameJQuery).jqGrid('getGridParam', 'sortname'),
                 sortorder: jQuery(gridTextil.nameJQuery).jqGrid('getGridParam', 'sortorder'),
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
             window.localStorage.setItem(gridTextil.myColumnStateName, JSON.stringify(columnsState));
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
			$("#p12_cmb_mmc").combobox('autocomplete',"");
	 		$("#p12_cmb_mmc").combobox('comboautocomplete',"null");
			$("#p12_cmb_pedir").combobox('autocomplete',"");
	 		$("#p12_cmb_pedir").combobox('comboautocomplete',"null");
			$("#p12_cmb_catalogo").combobox('autocomplete',"");
	 		$("#p12_cmb_catalogo").combobox('comboautocomplete',"null");
			$("#p12_cmb_activableFacing").combobox('autocomplete',"");
	 		$("#p12_cmb_activableFacing").combobox('comboautocomplete',"null");
	 		$("#p12_cmb_loteSN").combobox('autocomplete',"");
	 		$("#p12_cmb_loteSN").combobox('comboautocomplete',"null");
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
	

		//Mostramos el combo de Activable por Facing solo si
		//el area seleccionado es 3-TEXTIL
		if ($( "#p12_cmb_area").val()=="3" ){
			$("#p12_div_activableFacing").show();
			$("#p12_div_loteSN").show();
			$("[radDesTextil=S]").show();
			$("#p12_lbl_radioDesglose").show();
		}
		else{
			$("#p12_div_activableFacing").hide();
	 		$("#p12_cmb_activableFacing").combobox('autocomplete',allOptions);
	 		$("#p12_cmb_activableFacing").combobox('comboautocomplete',"null");
	 		
	 		$("#p12_div_loteSN").hide();
	 		$("#p12_cmb_loteSN").combobox('autocomplete',allOptions);
	 		$("#p12_cmb_loteSN").combobox('comboautocomplete',"null");
	 		
	 		$("[radDesTextil=S]").hide();
	 		$("#p12_lbl_radioDesglose").hide();
	 		if ($("input[name='p12_rad_tipoListado']:checked").val() == "3"){
				$('input:radio[name=p12_rad_tipoListado]')[0].checked = true;
				$('input:radio[name=p12_rad_tipoListado]')[1].checked = false;
				$('input:radio[name=p12_rad_tipoListado]')[2].checked = false;
	 		}
		}
		
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
			$("#p12_cmb_mmc").combobox("enable");
			$("#p12_cmb_pedir").combobox("enable");
			$("#p12_cmb_catalogo").combobox("enable");
			$("#p12_cmb_activableFacing").combobox("enable");
			$("#p12_cmb_loteSN").combobox("enable");
		}else{
			$("#p12_cmb_area").combobox("disable");
			$("#p12_cmb_mmc").combobox("disable");
			$("#p12_cmb_pedir").combobox("disable");
			$("#p12_cmb_catalogo").combobox("disable");
			$("#p12_cmb_activableFacing").combobox("disable");
			$("#p12_cmb_loteSN").combobox("disable");
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


function p12FormaterNivelLote(cellValue, opts, rData) {
	//Añadimos los valores de la columna referencia ocultos para poder utilizarlos posteriormente.
	var datoNivelLote= "<input type='hidden' id='"+opts.rowId+"_nivelLote' value='"+rData['nivelLote']+"'>";
	
	return datoNivelLote;
}

function exportExcel(){
	var messageVal=findValidation();
	if (messageVal!=null){
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		$('#p12_btn_exportExcel').prop('disabled',true); // Desactivar el botón hasta que finalizace la exportacion.

		if (consultaTextil == "S") {
			var colModel = $(gridTextil.nameJQuery).jqGrid("getGridParam", "colModel");
		    var colNames = $(gridTextil.nameJQuery).jqGrid("getGridParam", "colNames");
		} else {
			var colModel = $(grid.nameJQuery).jqGrid("getGridParam", "colModel");
		    var colNames = $(grid.nameJQuery).jqGrid("getGridParam", "colNames");
		}
	    
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

//		var form = "<form name='csvexportform' action='altasCatalogo/exportGrid.do' accept-charset='ISO-8859-15'  method='get'>";
	    var form = "<form name='csvexportform' action='altasCatalogo/exportGrid.do'  accept-charset='ISO-8859-1' method='get'>";
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
			if ($("#p12_cmb_mmc").combobox('getValue')!="null" && $("#p12_cmb_mmc").combobox('getValue')!=null ){
				form = form + "<input type='hidden' name='marcaMaestroCentro' value='"+ $("#p12_cmb_mmc").val()+"'>";
			}
			if ($("#p12_cmb_catalogo").combobox('getValue')!="null" && $("#p12_cmb_catalogo").combobox('getValue')!=null ){
				form = form + "<input type='hidden' name='catalogo' value='"+ $("#p12_cmb_catalogo").val()+"'>";
			}
			if ($("#p12_cmb_pedir").combobox('getValue')!="null" && $("#p12_cmb_pedir").combobox('getValue')!=null ){
				form = form + "<input type='hidden' name='pedir' value='"+$("#p12_cmb_pedir").val()+"'>";
			}
			if ($("#p12_cmb_activableFacing").combobox('getValue')!="null" && $("#p12_cmb_activableFacing").combobox('getValue')!=null ){
				form = form + "<input type='hidden' name='pedible' value='"+$("#p12_cmb_activableFacing").val()+"'>";
			}
			if ($("#p12_cmb_loteSN").combobox('getValue')!="null" && $("#p12_cmb_loteSN").combobox('getValue')!=null ){
				form = form + "<input type='hidden' name='loteSN' value='"+$("#p12_cmb_loteSN").val()+"'>";
			}
			form = form + "<input type='hidden' name='facingCero' value='"+($('#p12_chk_facingCero').prop('checked') ? 'S': 'N')+"'>";
		}
		if ($("input[name='p12_rad_tipoFiltro']:checked").val() != "1"){
			if ($("#p12_fld_referencia").val()!="" && $("#p12_fld_referencia").val()!=null ){
				form = form + "<input type='hidden' name='codigoArticulo' value='"+$("#p12_fld_referencia").val()+"'>";
			}
		}	
		form = form + "<input type='hidden' name='consultaTextil' value='"+consultaTextil+"'>";
		form = form + "<input type='hidden' name='tipolistado' value='"+$("input[name='p12_rad_tipoListado']:checked").val()+"'>";
		form = form + "</form><script>document.csvexportform.submit();</script>";
		Show_Popup(form);
		
		$('#p12_btn_exportExcel').prop('disabled',true); // Desactiva el botón <Excel>.
	    comprobarGeneracionExcel();
	}
}

function comprobarGeneracionExcel(){
	var urlComprobarGeneracionExcel = './common/comprobarGeneracionExcel.do';

	$.ajax({
		type : 'POST',
		url : urlComprobarGeneracionExcel,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
			if(data=='0'){
//				console.log('Generando EXCEL...');
				comprobarGeneracionExcel();
			}else{
				$('#p12_btn_exportExcel').prop("disabled",false); // Vuelvo a activar el botón <Excel>.
//				console.log('EXCEL generado.');
			}
			
		},
		error : function (xhr, status, error){
			$("#AreaResultados .loading").css("display", "none");
			$('#p12_btn_exportExcel').prop("disabled",false); // Vuelvo a activar el botón <Excel>.
			handleError(xhr, status, error, locale);
        }			
	});
}

function mine(value, options, rData){ if (value==0){return '';}else{return value;}}

function formatDescripcionCombo(codigo, descripcion){
	return codigo + "-" + descripcion;
}

function p12FormateoDecimales(valor) {
	
	if (valor != ''){
		//Actualizamos el formateo de los campos a dos decimales
		return $.formatNumber(valor,{format:"0.##",locale:"es"});
	}else{
		return '';
	}
}

function FFPPFormatter(value, options, rData){ 
	var textoFormateado = "";
	var textoDescripcion = rData['descripRela'];
	if (value==0){
		textoFormateado = "";
	}else{
		textoFormateado = value;
	}
	if (textoDescripcion != null && textoDescripcion != ""){
		if (textoFormateado != ""){
			textoFormateado += "-";
		}
		textoFormateado += textoDescripcion;
	}
    return textoFormateado;
}


function p12FormatOrden(cellValue, opts, rData){

	var codigoOrdenaciónLote = "";
	
	if (rData['grupo1'] == 3) {
	
		//(TEMPORADA (2 dígitos) + blanco + AÑO + blanco + COMERCIAL (todo menos área) + blanco + NUMERO ORDEN (3 dígitos)
	 
		if (rData['temporada'] != null && rData['temporada'] != ""){
			var temporada= rData['temporada'].substring(0,2);
		}
		var anno= rData['anioColeccion'];
		var comercial= rData['grupo2'] +  rData['grupo3'] +  rData['grupo4'] +  rData['grupo5'];
		
		var numeroOrden = "";

	    var aux = rData['numOrden'];
	    var lg = rData['numOrden'].length;
	
	    if (lg >= 3) {
	
	    	 numeroOrden =  aux;
	    } else {
	   
		    for (i = lg; i < 3; i++) {
		
		        aux = "0" + aux;
		    }
	    
	    	numeroOrden =  aux;
	    }

		codigoOrdenaciónLote = temporada + " " + anno  + " " + comercial  + " " + numeroOrden;
	}
	
	return  codigoOrdenaciónLote;
}