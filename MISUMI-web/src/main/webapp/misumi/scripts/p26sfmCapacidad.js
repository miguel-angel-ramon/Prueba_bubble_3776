var AREA_CONST="area";
var RESET_CONST="reset";
var SECTION_CONST="section";
var CATEGORY_CONST="category";
var SUBCATEGORY_CONST="subcategory";
var SEGMENT_CONST="segment";
var grid=null;
var gridSfm=null;
var gridCap=null;
var gridFac=null;
var gridFacTextil=null;
var gridColNamesCapacidad = null;
var gridTitleCapacidad = null;
var gridColNamesSFM = null;
var gridTitleSFM = null;
var gridEmptyRecords = null;
var gridEmptyRecordsSfm = null;
var gridEmptyRecordsSfmCap = null;
var gridEmptyRecordsSfmFac = null;
var gridEmptyRecordsEstructuraNoParam = null;
var noReferenceError = null;
var emptyListaModificados = null;
var centerRequired=null;
var areaRequired=null;
var sectionRequired=null;
var categoryRequired=null;
var referenceRequired=null;
var centerRequided=null;
var tableFilter=null;
var optionNull = "";
var numRow=1;
var SFMIncorrecto=null;
var SFMCoberturaIncorrecto=null;
var iconoGuardado=null;
var iconoModificado=null;
var CapacidadIncorrecto=null;
var CapacidadMayorPermitida=null;
var FacingIncorrecto=null;
var errorActualizacionEstra=null;
var SFMmenorLimInf=null;
var SFMmayorLimSup=null;
var errorActualizacion=null;
var errorActualizacionOrig=null;
var seleccionados = new Array();
var listadoModificados = new Array();
var optionActiva=null;
var optionActivaTodos=null;
var mensajeAyudaActiva = null;
var mensajeAyudaSoloImagen = null;
var expositor = null;
var expositorTitle = null;
var pedible = null;
var temporada = null;
var anioColeccion = null;
var modeloProveedor = null;
var talla = null;
var color = null;
var lote = null;
var tempColNumOrden = null;
var P26Inicializada = null;
var huecosFacing = null;
var flgDespliegueSubgrid = true;
var subgridActual = null;
var idFocoSubgrid;
var flgActualizarFacing = "";

$(document).ready(function(){
	$(document).on('CargarScriptsPantallas', function(e) { 
		$(document).on('CargadoCentro', function(e) { 
			if(P26Inicializada == null){
				P26Inicializada = 'S';
				loadP26(locale);
			}
		});
	
		if($("#centerId").val() != null && $("#centerId").val() != "" && P26Inicializada == null){
			P26Inicializada = 'S';
			loadP26(locale);
		}
		
		events_p26_rad_tipoFiltro();
		events_p26_btn_reset();
		events_p26_btn_buscar();
		events_p26_btn_exportExcel();
		events_p26_btn_guardar();
		events_p26_btn_ayudaActiva();
		events_p26_btn_ayudaSoloImagen();
		
		/**
		 *P52083
		 * Evento return en el campo de busqueda
		 * @author BICUGUAL 
		 */
		$("#p26_fld_referencia").keydown(function(e) {
	 	    if(e.which == 13) {
	 	    	//Se ha pulsado intro
	 	    	e.preventDefault();
	 	    	finder();
	 	    }
		});
		
	});
	initializeScreenComponentsP26();
});	





function initializeScreenComponentsP26(){
	$("#p26_cmb_mmc").combobox(null);
	$("#p26_cmb_pedir").combobox(null);
	$("#p26_cmb_loteSN").combobox(null);
	$("#p26_cmb_area").combobox(null);
	$("#p26_cmb_seccion").combobox(null);
	$("#p26_cmb_subcategoria").combobox(null);
	$("#p26_cmb_categoria").combobox(null);
	$("#p26_cmb_segmento").combobox(null);
	

	CargadaEstructuraPantalla(2);
}

function initializeScreen(){
	if ($("#p01_txt_flgFacing").val() == 'S'){
		$("#p26_div_mmc").show();
		$("#p26_cmb_mmc").combobox(null);
		$("#p26_cmb_mmc").combobox("disable");
	}else{
		$("#p26_div_mmc").hide();
	}
	$("#p26_cmb_pedir").combobox(null);
	$("#p26_cmb_loteSN").combobox(null);
	
	if ($("#p01_txt_flgFacing").val() == 'N'){
		//<option value="S" selected="selected"><spring:message code="p26_sfmCapacidad.si" /></option><option value="N"><spring:message code="p26_sfmCapacidad.no" /></option>
		$("#p26_cmb_pedir").combobox('autocomplete',optionActiva);
		$("#p26_cmb_pedir").combobox('comboautocomplete','S');
		$("#p26_cmb_pedir").combobox("disable");
	}else{
		//option value="null" selected="selected"><spring:message code="p26_sfmCapacidad.all" /></option><option value="S"><spring:message code="p26_sfmCapacidad.si" /></option><option value="N"><spring:message code="p26_sfmCapacidad.no" /></option>
		//$("#p26_cmb_pedir").combobox(null);
	    //$("select#p26_cmb_pedir").html(options);
		$("#p26_cmb_pedir").combobox('autocomplete',optionActivaTodos);
		$("#p26_cmb_pedir").combobox('comboautocomplete','null');
	}
	
	$("#p26_cmb_loteSN").combobox('autocomplete',optionActivaTodos);
	$("#p26_cmb_loteSN").combobox('comboautocomplete','null');
	
	//Nos posicionamos en el campo al que ha navegado el usuario y que hemos almacenado en el hidden.
	$("#p03_btn_aceptar").bind("click", function(e) {
			if ($("#p26_fld_SFM_Selecc").val() != '')
			{
				$("#"+$("#p26_fld_SFM_Selecc").val()).focus();
				$("#"+$("#p26_fld_SFM_Selecc").val()).select();
				e.stopPropagation();
				$("#p26_fld_SFM_Selecc").val('');
			}
		 });  
	
	var result=cleanFilterSelection(RESET_CONST);
	controlCentro();
	
	$("#p26_rad_tipoFiltro").filter('[value=1]').attr('checked', true);
	
	/**
	 *P52083
	 * Comento el filtro para que acepte letras
	 * @author BICUGUAL 
	 */
	//$('#p26_fld_referencia').filter_input({regex:'[0-9]'});
}

function excelReferencia(){
	var vAgruComerParamSfmcap=new VAgruComerParamSfmcap($("#centerId").val(), "I5");
	var objJson = $.toJSON(vAgruComerParamSfmcap);	
	 $.ajax({
			type : 'POST',
			url : './sfmCapacidad/obtenerEstructura.do?codigoArticulo='+$("#p26_fld_referencia").val(),
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			cache : false,
			success : function(data) {	
				controlTipoListado(data.grupo5 + "*" + data.flgStockFinal + "*" + data.flgCapacidad + "*" + data.flgFacing + "*" + data.flgFacingCapacidad);
				exportExcel();
			},
			error : function (xhr, status, error){
				handleError(xhr, status, error, locale);
			}
	  });	
}
	 
function load_cmbArea(){	
	
	var options = "";
	var optionNull = "";
	var vAgruComerParamSfmcap=new VAgruComerParamSfmcap($("#centerId").val(), "I1");
	var objJson = $.toJSON(vAgruComerParamSfmcap);	
	 $.ajax({
		type : 'POST',
		url : './sfmCapacidad/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	

			   for (i = 0; i < data.length; i++){
				   options = options + "<option value='" + data[i].grupo1 + "*" + data[i].flgStockFinal + "*" + data[i].flgCapacidad + "*" + data[i].flgFacing + "*" + data[i].flgFacingCapacidad + "'>" + formatDescripcionCombo(data[i].grupo1, data[i].descripcion) + "</option>";  
			   }
			   $("select#p26_cmb_area").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
	$("#p26_cmb_area").combobox({
        selected: function(event, ui) {
        	if ( ui.item.value!="" && ui.item.value!="null" ) {
	           if ($("#p26_cmb_area").val()!=null){
	        	   load_cmbSeccion();
	        	   controlTipoListado($("#p26_cmb_area").val());
	        	   var result = cleanFilterSelection(AREA_CONST);
	           }
        	}else{
        		  var result= cleanFilterSelection(RESET_CONST);
        	}
          
        }  ,
       
	    changed: function(event, ui) { 
		   if (!ui.item || !ui.item.value){
			   return result=cleanFilterSelection(RESET_CONST);
			   
		   }	 
	    }
    }); 
	
	$("#p26_cmb_area").combobox('autocomplete',optionNull);
	$("#p26_cmb_area").combobox('comboautocomplete',null);

}

function load_cmbSeccion(){	
	var options = "";
	var optionNull = "";
	$("#p26_cmb_seccion").combobox(null);
	var valorArea = $("#p26_cmb_area").val().split('*');
	var codArea = valorArea[0];
	var vAgruComerParamSfmcap=new VAgruComerParamSfmcap($("#centerId").val(), "I2", codArea);
	var objJson = $.toJSON(vAgruComerParamSfmcap);	
	 $.ajax({
		type : 'POST',
		url : './sfmCapacidad/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			   for (i = 0; i < data.length; i++){
				   options = options + "<option value='" + data[i].grupo2 + "*" + data[i].flgStockFinal + "*" + data[i].flgCapacidad + "*" + data[i].flgFacing + "*" + data[i].flgFacingCapacidad + "'>" + formatDescripcionCombo(data[i].grupo2, data[i].descripcion) + "</option>";  
			   }
			   $("select#p26_cmb_seccion").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
	$("#p26_cmb_seccion").combobox({
        selected: function(event, ui) {
	        if (ui.item.value) {
	           if ($("#p26_cmb_seccion").val()){
	        	   load_cmbCategory();
	        	   controlTipoListado($("#p26_cmb_seccion").val());
	        	   cleanFilterSelection(SECTION_CONST);
	           }
	        } else{
	        	 cleanFilterSelection(AREA_CONST);
        	}  
        }  
        ,		       
        changed: function(event, ui) { 
		   if (!ui.item || !ui.item.value){
        	  if (cleanFilterSelection(AREA_CONST)){
        		   load_cmbSeccion();
		      }
		   }	 
	    }
     });
	
	$("#p26_cmb_seccion").combobox('autocomplete',optionNull);
	$("#p26_cmb_seccion").combobox('comboautocomplete',null);
}

function load_cmbCategory(){	
	var options = "";
	var optionNull = "";
	$("#p26_cmb_categoria").combobox(null);
	var valorArea = $("#p26_cmb_area").val().split('*');
	var codArea = valorArea[0];
	var valorSeccion = $("#p26_cmb_seccion").val().split('*');
	var codSeccion = valorSeccion[0];
	var vAgruComerParamSfmcap=new VAgruComerParamSfmcap($("#centerId").val(), "I3", codArea, codSeccion);
	var objJson = $.toJSON(vAgruComerParamSfmcap);	
	 $.ajax({
		type : 'POST',
		url : './sfmCapacidad/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			   for (i = 0; i < data.length; i++){
				   options = options + "<option value='" + data[i].grupo3 + "*" + data[i].flgStockFinal + "*" + data[i].flgCapacidad + "*" + data[i].flgFacing + "*" + data[i].flgFacingCapacidad + "'>" + formatDescripcionCombo(data[i].grupo3, data[i].descripcion) + "</option>";  
			   }
			   $("select#p26_cmb_categoria").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
		$("#p26_cmb_categoria").combobox({
	        selected: function(event, ui) {
	        	 if (ui.item.value) {
		           if ($("#p26_cmb_categoria").val()!=null){
		        	   load_cmbSubCategory();
		        	   controlTipoListado($("#p26_cmb_categoria").val());
		        	   cleanFilterSelection(CATEGORY_CONST);
		           } 
	        	 } else{
	        		 cleanFilterSelection(SECTION_CONST);
		         }  
	        },		       
	        changed: function(event, ui) { 
			   if (!ui.item || !ui.item.value){
				   if (cleanFilterSelection(SECTION_CONST)){ 
				      load_cmbCategory();
				   }
			   }		   
			}
	          
	         
	     }); 
	
	$("#p26_cmb_categoria").combobox('autocomplete',optionNull);
	$("#p26_cmb_categoria").combobox('comboautocomplete',null);
}

function load_cmbSubCategory(){	
	var options = "";
	var optionNull = "";
	$("#p26_cmb_subcategoria").combobox(null);
	var valorArea = $("#p26_cmb_area").val().split('*');
	var codArea = valorArea[0];
	var valorSeccion = $("#p26_cmb_seccion").val().split('*');
	var codSeccion = valorSeccion[0];
	var valorCategoria = $("#p26_cmb_categoria").val().split('*');
	var codCategoria = valorCategoria[0];
	var vAgruComerParamSfmcap=new VAgruComerParamSfmcap($("#centerId").val(), "I4", codArea, codSeccion, codCategoria);
	var objJson = $.toJSON(vAgruComerParamSfmcap);	
	 $.ajax({
		type : 'POST',
		url : './sfmCapacidad/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			   for (i = 0; i < data.length; i++){
				   options = options + "<option value='" + data[i].grupo4 + "*" + data[i].flgStockFinal + "*" + data[i].flgCapacidad + "*" + data[i].flgFacing + "*" + data[i].flgFacingCapacidad + "'>" + formatDescripcionCombo(data[i].grupo4, data[i].descripcion) + "</option>";  
			   }
			   $("select#p26_cmb_subcategoria").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
	$("#p26_cmb_subcategoria").combobox({
        selected: function(event, ui) {
          if (ui.item.value) {
	           if ($("#p26_cmb_subcategoria").val()!=null){
	        	   load_cmbSegment();
	        	   controlTipoListado($("#p26_cmb_subcategoria").val());
	        	   cleanFilterSelection(SUBCATEGORY_CONST);
	           } 
          } else{
        	  cleanFilterSelection(CATEGORY_CONST);
          } 
        },		       
        changed: function(event, ui) { 
			   if (!ui.item || !ui.item.value){
				   if (cleanFilterSelection(CATEGORY_CONST)){ 
					   load_cmbSubCategory();
				   }	   
			   }	 
		   }
     });
	
	$("#p26_cmb_subcategoria").combobox('autocomplete',optionNull);
	$("#p26_cmb_subcategoria").combobox('comboautocomplete',null);
}

function load_cmbSegment(){	
	var options = "";
	var optionNull = "";
	$("#p26_cmb_segmento").combobox(null);
	var valorArea = $("#p26_cmb_area").val().split('*');
	var codArea = valorArea[0];
	var valorSeccion = $("#p26_cmb_seccion").val().split('*');
	var codSeccion = valorSeccion[0];
	var valorCategoria = $("#p26_cmb_categoria").val().split('*');
	var codCategoria = valorCategoria[0];
	var valorSubcategoria = $("#p26_cmb_subcategoria").val().split('*');
	var codSubcategoria = valorSubcategoria[0];
	var vAgruComerParamSfmcap=new VAgruComerParamSfmcap($("#centerId").val(), "I5", codArea, codSeccion, codCategoria, codSubcategoria);
	var objJson = $.toJSON(vAgruComerParamSfmcap);	
	 $.ajax({
		type : 'POST',
		url : './sfmCapacidad/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			   for (i = 0; i < data.length; i++){
				   options = options + "<option value='" + data[i].grupo5 + "*" + data[i].flgStockFinal + "*" + data[i].flgCapacidad + "*" + data[i].flgFacing + "*" + data[i].flgFacingCapacidad + "'>" + formatDescripcionCombo(data[i].grupo5, data[i].descripcion) + "</option>";  
			   }
			   $("select#p26_cmb_segmento").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
		$("#p26_cmb_segmento").combobox({
	        selected: function(event, ui) {
	          if ( ui.item.value!="" && ui.item.value!="null") {
		           if ($("#p26_cmb_segmento").val()!=null){
		        	   $("#p26_cmb_segmento").combobox('comboautocomplete',ui.item.value);
		        	   $("#p26_cmb_segmento").combobox('autocomplete',ui.item.label);
		        	   controlTipoListado($("#p26_cmb_segmento").val());
		        	   cleanFilterSelection(SEGMENT_CONST);
		           } 
	          } else{
	        	  cleanFilterSelection(SUBCATEGORY_CONST);
	          } 
	          
	        },		       
	        changed: function(event, ui) { 
				   if (!ui.item || !ui.item.value){
					   if (cleanFilterSelection(SUBCATEGORY_CONST)){ 
						   load_cmbSegment();
					   }
				   }	 
			   }
	          
	     });
	
	$("#p26_cmb_segmento").combobox('autocomplete',optionNull);
	$("#p26_cmb_segmento").combobox('comboautocomplete',null);
}

function events_p26_rad_tipoFiltro(){
    $("input[name='p26_rad_tipoFiltro']").click(function () {
 
    	if ($("input[name='p26_rad_tipoFiltro']:checked").val() == "1"){
    		
    		jQuery(gridSfm.nameJQuery).jqGrid('hideCol', ["descCodN1","descCodN2","descCodN3","descCodN4","descCodN5","marca"]);
    		jQuery(gridCap.nameJQuery).jqGrid('hideCol', ["descCodN1","descCodN2","descCodN3","descCodN4","descCodN5","marca"]);
    		jQuery(gridFac.nameJQuery).jqGrid('hideCol', ["descCodN1","descCodN2","descCodN3","descCodN4","descCodN5","marca"]);
    		
    		//Estructura comercial
    		$("#p26AreaErrores").attr("style", "display:none");
    		 $("div#p26_filtroEstructura").attr("style", "display:block");
    		 $("div#p26_filtroReferencia").attr("style", "display:none");
    		 $("#p26_fld_referencia").val("");
    		 $('#gridP26Sfm').jqGrid('clearGridData');
    		 $('#gridP26Cap').jqGrid('clearGridData');
    		 $('#gridP26Fac').jqGrid('clearGridData');
    		 $('#gridP26FacTextil').jqGrid('clearGridData');
    		
    		 jQuery(gridSfm.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
    		 jQuery(gridCap.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
    		 jQuery(gridFac.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
    		 jQuery(gridFacTextil.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
    		
    		 
    	} else { //Referencias
    		$("#p26AreaErrores").attr("style", "display:none");
    		$("div#p26_filtroEstructura").attr("style", "display:none");
   		 	$("div#p26_filtroReferencia").attr("style", "display:inline-block");
   		 	if (esCentroCaprabo){
		  		$("#p26_fld_referencia").filter_input({regex:'[0-9]'});
		  	}
   		    var result= cleanFilterSelection(RESET_CONST);
	   		jQuery(gridSfm.nameJQuery).jqGrid('hideCol', ["descCodN1","descCodN2","descCodN3","descCodN4","descCodN5","marca"]);
	   		jQuery(gridCap.nameJQuery).jqGrid('hideCol', ["descCodN1","descCodN2","descCodN3","descCodN4","descCodN5","marca"]);
	   		jQuery(gridFac.nameJQuery).jqGrid('hideCol', ["descCodN1","descCodN2","descCodN3","descCodN4","descCodN5","marca"]);
	 		$('#gridP26Sfm').jqGrid('clearGridData');
	 		$('#gridP26Cap').jqGrid('clearGridData');
	 		$('#gridP26Fac').jqGrid('clearGridData');
	 		$('#gridP26FacTextil').jqGrid('clearGridData');
	 		
	 		jQuery(gridSfm.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
	 		jQuery(gridCap.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
	 		jQuery(gridFac.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
	 		jQuery(gridFacTextil.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
	 		
	 		$("#p26_fld_referencia").focus();
    	}    	 
    });
}

function events_p26_btn_reset(){
	$("#p26_btn_reset").click(function () {
		 var result=cleanFilterSelection(RESET_CONST);
		 $("#p26AreaErrores").attr("style", "display:none");
		$("#p26_fld_referencia").val("");
		 $('#gridP26Sfm').jqGrid('clearGridData');
		 $('#gridP26Cap').jqGrid('clearGridData');
		 $('#gridP26Fac').jqGrid('clearGridData');
		 $('#gridP26FacTextil').jqGrid('clearGridData');
		 $("#AreaResultados").hide();
		 
		 ocultarHuecos();
	});
}

function findValidation(){
	var messageVal=null;
	if ($("input[name='p26_rad_tipoFiltro']:checked").val() == "1"){
		//Control de tipo de listado en funcion de la ultima combo informada
		var ultimaCmbInformada;
		if($("#p26_cmb_segmento").combobox('getValue')){
			controlTipoListado($("#p26_cmb_segmento").combobox('getValue'));
		}else if($("#p26_cmb_subcategoria").combobox('getValue')){
			controlTipoListado($("#p26_cmb_subcategoria").combobox('getValue'));
		}else if($("#p26_cmb_categoria").combobox('getValue')){
			controlTipoListado($("#p26_cmb_categoria").combobox('getValue'));
		}
		
		//Estructura comercial
		
		if ($("#centerId").val()==null || $("#centerId").val()==""){
			messageVal = centerRequided;
		} else if($("#p26_cmb_area").combobox('getValue')=="null" || $("#p26_cmb_area").combobox('getValue')==null){
			messageVal = areaRequired;
		}else if( $("#p26_cmb_seccion").combobox('getValue')=="null" || $("#p26_cmb_seccion").combobox('getValue')==null){
			messageVal = sectionRequired;
		}else if( $("#p26_cmb_categoria").combobox('getValue')=="null" || $("#p26_cmb_categoria").combobox('getValue')==null){
			messageVal = categoryRequired;
     	}else if($('#p26_txt_flgStock').val()!='S' && $('#p26_txt_flgFacing').val()!='S' && $('#p26_txt_flgCapacidad').val()!='S'){
     		// estan las tres primeras informadas pero no hay marca SFM, FAC o CAP
			messageVal = nivelesInsuficientesEstructura;
     	}

	} else { //Referencias
		/**
		 * P52083
		 * Modifico validacion para referencia
		 * 
		 * MISUMI-376
		 * Eliminar espacios en blanco por detras y por delante del texto de busqueda para validar que tenga mas de 4 caracteres.
		 * @author BICUGUAL 
		 */
		var txtBusqueda=$("#p26_fld_referencia").val()
		
		if ($("#centerId").val()==null || $("#centerId").val()==""){		
			messageVal=centerRequired;		
		}			
		else if (txtBusqueda==null || txtBusqueda==""){		
			messageVal=referenceRequired;   		
		}		
		else if (!isInt(txtBusqueda) && txtBusqueda.trim().length<4){		
			messageVal=referenceMinCaracters;		
		}
		
	}    
	return messageVal;
	
}

function findValidationSave(){
	var messageVal=null;
	obtenerListadoModificados();
	
	if (listadoModificados== null || listadoModificados.length == 0){
		messageVal = emptyListaModificados;
	}
	return messageVal;
	
}

function events_p26_btn_buscar(){
	$("#p26_btn_buscar").click(function () {
		ocultarHuecos();
		finder();
		
	});	
}

function events_p26_btn_exportExcel(){
	excel();
}


function events_p26_btn_guardar(){
	$("#p26_btn_save").click(function () {
		guardarDatos();
	});	
}

function events_p26_btn_ayudaActiva(){
	$("#p26_btn_ayudaActiva").click(function () {
   		createAlert(replaceSpecialCharacters(mensajeAyudaActiva), "HELP");
	});
}

function events_p26_btn_ayudaSoloImagen(){
	$("#p26_btn_ayudaSoloImagen").click(function () {
   		createAlert(replaceSpecialCharacters(mensajeAyudaSoloImagen), "HELP");
	});
}

function excel(){
	var messageVal=findValidation();
	if (messageVal!=null){
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	} else {
		$("#p26_btn_excel").click(function () {	
			if ($("input[name='p26_rad_tipoFiltro']:checked").val() == "1"){//Estructura comercial
				exportExcel();
			} else if ($("input[name='p26_rad_tipoFiltro']:checked").val() == "2"){//Referencia
				excelReferencia();
			}
		});	
	}
}

function finder(){
	var messageVal=findValidation();

	var txtBusqueda= $("#p26_fld_referencia").val();
	
	if (messageVal!=null){
		$("#p26AreaErrores").attr("style", "display:none");
		$('#gridP26Sfm').jqGrid('clearGridData');
		$('#gridP26Cap').jqGrid('clearGridData');
		$('#gridP26Fac').jqGrid('clearGridData');
		$('#gridP26FacTextil').jqGrid('clearGridData');
		jQuery(gridSfm.nameJQuery).jqGrid('hideCol', ["descCodN1","descCodN2","descCodN3","descCodN4","descCodN5","marca"]);
		jQuery(gridSfm.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
		jQuery(gridCap.nameJQuery).jqGrid('hideCol', ["descCodN1","descCodN2","descCodN3","descCodN4","descCodN5","marca"]);
		jQuery(gridCap.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
		jQuery(gridFac.nameJQuery).jqGrid('hideCol', ["descCodN1","descCodN2","descCodN3","descCodN4","descCodN5","marca"]);
		jQuery(gridFac.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
		jQuery(gridFacTextil.nameJQuery).jqGrid('hideCol', ["descCodN1","descCodN2","descCodN3","descCodN4","descCodN5","marca"]);
		jQuery(gridFacTextil.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
		
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		$('#gridP26Sfm').setGridParam({page:1});
		$('#gridP26Cap').setGridParam({page:1});
		$('#gridP26Fac').setGridParam({page:1});
		$('#gridP26FacTextil').setGridParam({page:1});
		
		//if (gridSfm != null)
		//	gridSfm.firstLoad = true;
		//if (gridCap != null)
		//	gridCap.firstLoad = true;
		hideShowColumns(gridSfm);
		hideShowColumns(gridCap);
		hideShowColumns(gridFac);
		hideShowColumns(gridFacTextil);
	
		if ($("input[name='p26_rad_tipoFiltro']:checked").val() == "1"){
			reloadData('N');
		}else{
			
			//Si se trata de una busqueda alfanumerica o alfabetica
			if (!isInt(txtBusqueda)){
				//se buscará como MODELO PROVEEDOR
				//En el caso que no eista MODELO PROVEEDOR si no hay registros como DENOMINACION
				alfaNumerico= true;
				ean = false;
				reloadDataP34(txtBusqueda, "informacionSfm","Sfm", alfaNumerico, ean);
			}
			
			else
			{
				//Si la REFERENCIA mayor de 8 digitos se busca como EAN, 
				//Si no hay registros como EAN se buscará como MODELO PROVEEDOR
				//En el caso que no eista MODELO PROVEEDOR si no hay registros como DENOMINACION
				alfaNumerico = false;
				if(txtBusqueda.length > 8)
				{
					ean= true;
					reloadDataP34(txtBusqueda, "informacionSfm","Sfm",alfaNumerico,ean);
				}
				else
				{
					ean = false;
					reloadDataP34(txtBusqueda, "informacionSfm","Sfm",alfaNumerico,ean);
				}
			}	
		}
	}
}

function guardarDatos(){
	
	//Inicializamos el número de fila para los campos ocultos que tenemos.
	numRow=1;
	var messageVal=findValidationSave();
	var tipoListado;
	if ($("#p26_txt_flgFacing").val()=='S'){
		tipoListado = 'FAC';
	}else if ($("#p26_txt_flgCapacidad").val()=='S'){
		tipoListado = 'CAP';
	}else{
		tipoListado = 'SFM';
	}
	if (messageVal!=null){
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		//ocultarHuecos();
		//jQuery(gridSfm.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
		//jQuery(gridCap.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);


		var recordSfm = new VArtSfm (null, null, null, null, null, null, null, null, null,
				null, null, null, null, null,null,
				null, null, null, null, null, null,	null, null, null, null, listadoModificados);
		

		
		//Reseteamos el array de modificados.
		listadoModificados = new Array();
		
		var objJson = $.toJSON(recordSfm.prepareVArtSfmToJsonObject());



			if ($("#p26_txt_flgFacing").val()=='S'){
				grid = gridFac;
				
				//Mirar si es textil, en este caso habra que cargar el grid de textil
				if ($("input[name='p26_rad_tipoFiltro']:checked").val() == "1"){//Estructura comercial
					//Mirar la selección del combo de Area
					var valor = $("#p26_cmb_area").val().split('*');
					var cod = valor[0];
					if (cod=="3" ){
						grid = gridFacTextil;
					}
				}

			}else if ($("#p26_txt_flgCapacidad").val()=='S'){
				grid = gridCap;
			}else{
				grid = gridSfm;
			}

			if (grid.firstLoad) {
				jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
		        grid.firstLoad = false;
		        if (grid.isColState) {
		            $(this).jqGrid("remapColumns", grid.myColumnsState.permutation, true);
		        }
		    } else {
		    	grid.myColumnsState = grid.restoreColumnState(grid.cm);
		    	grid.isColState = typeof (grid.myColumnsState) !== 'undefined' && grid.myColumnsState !== null;
		    }
			
			$.ajax({
				type : 'POST',
				url : './sfmCapacidad/saveDataGrid.do?page='+grid.getActualPage()+'&max='+grid.getRowNumPerPage()+'&index='+grid.getSortIndex()+'&sortorder='+grid.getSortOrder()+'&tipoListado='+tipoListado,
				data : objJson,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {	
					if (data != null && data != '')
					{
						if (data.estado == 0){
							$("#p26AreaErrores").attr("style", "display:none");
						}else{
							$("#p26AreaErrores").attr("style", "display:block");
						}
							
					}else{
						$("#p26AreaErrores").attr("style", "display:none");
					}
					
					 $("#p26_AreaNotaPie").hide();
					 $("#p26_Area_mensajes").hide();
		    		 $("#p26_AreaEstados").hide();
					 
					if ($("#p26_txt_flgFacing").val()=='S'){
						
						if (data.datos != null && data.datos.records != null && data.datos.records != 0){
							if (data.datos.rows[0] != null && data.datos.rows[0].codN1 != null && data.datos.rows[0].codN1 == 3){
								$(gridFacTextil.nameJQuery)[0].addJSONData(data.datos);
								gridFacTextil.actualPage = data.datos.page;
								gridFacTextil.localData = data.datos;
								
								p26ControlesPantallaTextil();
								
								if (($("input[name='p26_rad_tipoFiltro']:checked").val() == "2") && (data.datos.rows[0].nivelLote >0)){ //La consulta ha sido por referencia 
									$("#1 td.sgcollapsed",gridFacTextil[0]).click();
										
								}
								
							//	$('#gridP26FacTextil').setGridParam({sortname:''});
								
								grid = gridFacTextil;
								
							} else {
								$(gridFac.nameJQuery)[0].addJSONData(data.datos);
								gridFac.actualPage = data.datos.page;
								gridFac.localData = data.datos;
							//	$('#gridP26Fac').setGridParam({sortname:''});
								grid = gridFac;
								
								 $("#p26_AreaNotaPie").show();
							}
						}
						
						/*
						 * P-50987
						 * Si el sumatorio es menor que los huecos muestro mensaje de aviso
						 * @BICUGUAL 
						 */
						if (huecosFacing!=null && data.sumatorio!=null){
							if (data.sumatorio < huecosFacing){
								var mensaje=numHuecosLibres;
								mensaje = numHuecosLibres.replace('{0}',(huecosFacing - data.sumatorio));
								createAlert(mensaje, "ERROR");	
							}
						}
						
					}else if ($("#p26_txt_flgCapacidad").val()=='S'){
						$(gridCap.nameJQuery)[0].addJSONData(data.datos);
						gridCap.actualPage = data.datos.page;
						gridCap.localData = data.datos;
					
						//$('#gridP26Cap').setGridParam({sortname:''});
					}else{
						$(gridSfm.nameJQuery)[0].addJSONData(data.datos);
						gridSfm.actualPage = data.datos.page;
						gridSfm.localData = data.datos;
						//$('#gridP26Sfm').setGridParam({sortname:''});
					}
					var ids = jQuery(grid.nameJQuery).jqGrid('getDataIDs'), i, l = ids.length;
					var cont = 0;
					
					$("#AreaResultados").hide();
				    for (i = 0; i < l; i++) {
				    	
				    	if ($("#p26_txt_flgFacing").val()=='S'){
							
						
							if ($("#"+ids[i]+"_expositor").val() == "S"){
								jQuery(gridFac.nameJQuery).jqGrid('setCell',ids[i],'facingCentro',expositor,{'font-weight':'bold','color':'#ff4aff','text-align':'center'},{ title: expositorTitle});
							}  else {
					    		
								if (data.datos.rows[i].flgSfmFijo !='B'){ //el campo Facing sera editable
						    		
									jQuery(grid.nameJQuery).jqGrid('editRow', ids[i], false);
									
									$("#"+ids[i]+"_facingCentro").filter_input({regex:'[0-9]'});

						    		$("#"+ids[i]+"_facingCentro").formatNumber({format:"0",locale:"es", round:false});
						    		$("#"+ids[i]+"_facingCentro_tmp").formatNumber({format:"0",locale:"es", round:false});
						    			
								    $("#"+ids[i]+"_facingCentro").focusout(function() {
								    	validacionFacing(this.id, 'S');
								    });
						    	
								    if (data.datos.rows[0] != null && data.datos.rows[0].codN1 != null && data.datos.rows[0].codN1 == 3 && $("#"+ids[i]+"_nivelLote").val() != '0') {
							    		//Si es textil, el campo facin aparecera en blanco
								    	
							    		//	$("#"+ids[i]+"_facingCentro").val('');	
							    		//	$("#"+ids[i]+"_facingCentro_tmp").val('');	 
							    	}
							    	// Pet. 54019
						    		if (data.datos.rows[i].flgSoloImagen =='S'){ //Es solo imagen, el texto se pintara en naranja
						    			
						    			$("#"+ids[i]+"_facingCentro").addClass("p26_ReferenciaSoloImagen");
						    			
						    			$("#p26_Area_mensajes").show().css('display', 'inline-block');
						    			$("#p26_AreaSoloImagen").show().css('display', 'inline-block');
						    		}
								}  else { //El campo facing sera no editable 
									
									$("#p26_Area_mensajes").show().css('display', 'inline-block');
					    			$("#p26_AreaEstados").show().css('display', 'inline-block');
									
						    		if (data.datos.rows[0] != null && data.datos.rows[0].codN1 != null && data.datos.rows[0].codN1 == 3 && $("#"+ids[i]+"_nivelLote").val() != '0') {
							    		//Si es textil y lote, el campo aparecera vacio
						    			
							    		jQuery(grid.nameJQuery).jqGrid('setCell',ids[i],'facingCentro',' ',{'font-weight':'bold','color':'#ff4aff','text-align':'center'});
							
							    	} else {
							    		
							    		//el valor aparecera en rosa
							    		jQuery(grid.nameJQuery).jqGrid('setCell',ids[i],'facingCentro',data.datos.rows[i].facingCentro,{'font-weight':'bold','color':'#ff4aff','text-align':'center'});
							    	}
									// Pet. 54019
						    		if (data.datos.rows[i].flgSoloImagen =='S'){
						    			//El valor aparecera en naranja
						    			jQuery(grid.nameJQuery).jqGrid('setCell',ids[i],'facingCentro',data.datos.rows[i].facingCentro,{'font-weight':'bold','color':'#fd9852','text-align':'center'});	
						    			
						    		} else {
						    			//El valor aparecera en rosa
							    		jQuery(grid.nameJQuery).jqGrid('setCell',ids[i],'facingCentro',data.datos.rows[i].facingCentro,{'font-weight':'bold','color':'#ff4aff','text-align':'center'});
						    		}
								}

							}
							
						//(Pet.169-sfm quitar la foto)	generateTooltip(ids[i],data.datos.rows[i]);
							
						}else if ($("#p26_txt_flgCapacidad").val()=='S'){
							
							if (data.datos.rows[i].flgSfmFijo !='B'){ //el campo Capacidad sera editable
				    		
								jQuery(grid.nameJQuery).jqGrid('editRow', ids[i], false);
								
								$("#"+ids[i]+"_capacidad").filter_input({regex:'[0-9]'});
					    		
					    		//Formateamos el campo de capacidad
						    	//$("#"+ids[i]+"_capacidad").formatNumber({format:"0.000",locale:"es"});
						    	//$("#"+ids[i]+"_capacidad_tmp").formatNumber({format:"0.000",locale:"es"});
						    	
						    	$("#"+ids[i]+"_capacidad").formatNumber({format:"0",locale:"es", round:false});
						    	$("#"+ids[i]+"_capacidad_tmp").formatNumber({format:"0",locale:"es", round:false});
						    	
						    	$("#"+ids[i]+"_capacidad").focusout(function() {
						    		validacionCapacidad(this.id, 'S');
						    	});

							} else { //El campo capacidad sera no editable  y en rosa
								
								$("#p26_Area_mensajes").show().css('display', 'inline-block');
				    			$("#p26_AreaEstados").show().css('display', 'inline-block');
				    			
								jQuery(grid.nameJQuery).jqGrid('setCell',ids[i],'capacidad',data.datos.rows[i].capacidad,{'font-weight':'bold','color':'#ff4aff','text-align':'center'});	
								
							}
				    		
				    	}
				    	else
				    	{
				    		if (data.datos.rows[i].flgSfmFijo !='B'){ //el campo sfm sera editable
				    			
				    			jQuery(grid.nameJQuery).jqGrid('editRow', ids[i], false);
				    			
				    			$("#"+ids[i]+"_sfm").filter_input({regex:'[0-9,]'});
						    	$("#"+ids[i]+"_coberturaSfm").filter_input({regex:'[0-9,]'});

						    	//Formateamos los campos de SFM y cobertura SFM
						    	if ($("#"+ids[i]+"_porcionConsumidor").val() == 'S' ){ 
						    		//Si el valor de porcion consumidor es igual a S, el campo SFM debe aparecer sin decimales
						    		$("#"+ids[i]+"_sfm").val(Math.floor($("#"+ids[i]+"_sfm").val())).formatNumber({format:"0",locale:"es"});
						    		
						    	} else {
						    		$("#"+ids[i]+"_sfm").formatNumber({format:"0.000",locale:"es"});
						    	
						    	}
						    		
						    	$("#"+ids[i]+"_coberturaSfm").formatNumber({format:"0.000",locale:"es"});
						    	$("#"+ids[i]+"_sfm_tmp").formatNumber({format:"0.000",locale:"es"});
						    	$("#"+ids[i]+"_coberturaSfm_tmp").formatNumber({format:"0.000",locale:"es"});
						    	
						    	$("#"+ids[i]+"_sfm").focusout(function() {
						    		validacionSfm(this.id, 'S');
						    	});
						    	
						    	$("#"+ids[i]+"_coberturaSfm").focusout(function() {
						    		validacionCoberturaSfm(this.id, 'S');
						    	});
						    	
				    		} else { //El campo sfm sera no editable  y en rosa
				    		
				    			$("#p26_Area_mensajes").show().css('display', 'inline-block');
				    			$("#p26_AreaEstados").show().css('display', 'inline-block');
				    			
				    			jQuery(grid.nameJQuery).jqGrid('setCell',ids[i],'sfm',data.datos.rows[i].sfm,{'font-weight':'bold','color':'#ff4aff','text-align':'center'});	
				    			jQuery(grid.nameJQuery).jqGrid('setCell',ids[i],'coberturaSfm',data.datos.rows[i].coberturaSfm,{'font-weight':'bold','color':'#ff4aff','text-align':'center'});	
				    		}
				    			
		
				    		
				    	}
				    	cont++;
				    }
				    cargarOrdenacionesColumnas();
				    $("#AreaResultados").show();

	
					$("#p26_btn_buscar").focus();

					$("#AreaResultados .loading").css("display", "none");	
				},
				error : function (xhr, status, error){
					$("#AreaResultados .loading").css("display", "none");	
					handleError(xhr, status, error, locale);				
		        },
		        complete:function (){
		        	var totalRegistrosFac=$(gridFac.nameJQuery).getGridParam("reccount");
		        	//Si existen registros cargados en la tabla, llamo a gestionar huecos
		        	if (totalRegistrosFac!=null && totalRegistrosFac>0)
		        		mostrarHuecos();
		        }
			});		
	}
}


function setSfmHeadersTitles(data){
	
   	var colModel = $(gridSfm.nameJQuery).jqGrid("getGridParam", "colModel");
    $.each(colModel, function(i) {
    	if (colModel[i].name!="rn"){
	    	$("#jqgh_gridP26Sfm_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
    	}
     });
}

function setFacHeadersTitles(data){
	
   	var colModel = $(gridFac.nameJQuery).jqGrid("getGridParam", "colModel");
    $.each(colModel, function(i) {
    	if (colModel[i].name!="rn"){
	    	$("#jqgh_gridP26Fac_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
    	}
     });
}

function setFacTextilHeadersTitles(data){
	
   	var colModel = $(gridFacTextil.nameJQuery).jqGrid("getGridParam", "colModel");
   	
   	$.each(colModel, function(i) {
    	if (colModel[i].name!="rn"){
	    	$("#jqgh_gridP26FacTextil_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
    	}
     });
    
}

function setCapHeadersTitles(data){
	
   	var colModel = $(gridCap.nameJQuery).jqGrid("getGridParam", "colModel");
    $.each(colModel, function(i) {
    	if (colModel[i].name!="rn"){
	    	$("#jqgh_gridP26Cap_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
    	}
     });
}

function loadP26(locale){
	 
	gridFac = new GridP26Fac(locale);
	gridCap = new GridP26Cap(locale);
	gridSfm = new GridP26Sfm(locale);
	gridFacTextil = new GridP26FacTextil(locale);
	var jqxhr = $.getJSON(gridSfm.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				$("#p26_txt_flgCapacidad").val($("#p01_txt_flgCapacidad").val());
				$("#p26_txt_flgFacing").val($("#p01_txt_flgFacing").val());
				gridFac.colNames = data.p26ColNamesFacing;
				gridFac.title = data.p26GridTitleFacing;
				gridCap.colNames = data.p26ColNamesCapacidad;
				gridCap.title = data.p26GridTitleCapacidad;
				gridSfm.colNames = data.p26ColNamesSFM;
				gridSfm.title = data.p26GridTitleSFM;
				gridFacTextil.colNames = data.p26ColNamesFacingTextil;
				gridFacTextil.subgridColNames = data.p26ColNamesFacingTextil;
				gridFacTextil.title = data.p26GridTitleFacingTextil;	
				gridColNamesFacing = data.p26ColNamesFacing;
				gridTitleFacing = data.p26GridTitleFacing;
				gridColNamesCapacidad = data.p26ColNamesCapacidad;
				gridTitleCapacidad = data.p26GridTitleCapacidad;
				gridColNamesSFM = data.p26ColNamesSFM;
				gridTitleSFM = data.p26GridTitleSFM;
				gridColNamesFacingTextil = data.p26ColNamesFacingTextil;
				gridTitleFacingTextil = data.p26GridTitleFacingTextil;
				index = '';
				sortOrder = 'asc';
				gridSfm.emptyRecords= data.emptyRecords;
				gridCap.emptyRecords= data.emptyRecords;
				gridFac.emptyRecords= data.emptyRecords;
				gridFacTextil.emptyRecords= data.emptyRecords;
				gridEmptyRecords= data.emptyRecords;
				noReferenceError = data.noReferenceError;
				gridEmptyRecordsSfm=data.emptyRecordsRefSfm;
				gridEmptyRecordsRefSfmNoStockFinalMinimo=data.emptyRecordsRefSfmNoStockFinalMinimo;
				gridEmptyRecordsSfmCap=data.emptyRecordsRefSfmCap;
				gridEmptyRecordsSfmFac=data.emptyRecordsRefSfmFac;
				gridEmptyRecordsSfmFacTextil=data.emptyRecordsRefSfmFacTextil;
				gridEmptyRecordsEstructuraNoParam = data.emptyRecordsEstructuraNoParam;
				emptyListaModificados = data.emptyListaModificados;
				mensajeAyudaActiva = data.mensajeAyudaActiva;
				mensajeAyudaSoloImagen = data.mensajeAyudaSoloImagen;
				centerRequired=data.centerRequired;
				areaRequired=data.areaRequired;
				sectionRequired=data.sectionRequired;
				categoryRequired=data.categoryRequired;
				referenceRequired = data.referenceRequired;
				centerRequided=data.centerRequided;
				mayorNivelRequired=data.mayorNivelRequired;
				nivelesInsuficientes=data.nivelesInsuficientes;
				nivelesInsuficientesEstructura=data.nivelesInsuficientesEstructura;
				SFMIncorrecto=data.SFMIncorrecto;
				SFMCoberturaIncorrecto=data.SFMCoberturaIncorrecto;
				iconoGuardado=data.iconoGuardado;
				iconoModificado=data.iconoModificado;
				CapacidadIncorrecto=data.CapacidadIncorrecto;
				CapacidadMayorPermitida=data.CapacidadMayorPermitida;
				FacingIncorrecto=data.FacingIncorrecto;
				errorActualizacionEstra=data.errorActualizacionEstra;
				SFMmenorLimInf=data.SFMmenorLimInf;
				SFMmayorLimSup=data.SFMmayorLimSup;
				errorActualizacion=data.errorActualizacion;
				errorActualizacionOrig=data.errorActualizacionOrig;
				tableFilter= data.tableFilter;
				optionActiva=data.optionActiva;
				optionActivaTodos=data.optionActivaTodos;
				expositor = data.expositor;
				expositorTitle = data.expositorTitle;
				pedible = data.pedible;
				temporada = data.temporada;
				anioColeccion = data.anioColeccion;
				modeloProveedor = data.modeloProveedor;
				talla = data.talla;
				color = data.color;
				lote = data.lote;
				tempColNumOrden = data.tempColNumOrden;
				initializeScreen();
				loadP26FacingMock(gridFac);
				loadP26CapacidadMock(gridCap);
				loadP26SFMMock(gridSfm);	
				loadP26FacingTextilMock(gridFacTextil);
				setSfmHeadersTitles(data);
				setFacHeadersTitles(data);
				setCapHeadersTitles(data);
				setFacTextilHeadersTitles(data);
				numHuecosLibres= data.msgHuecosLibres;
				huecosSegmento= data.huecosSegmento;
				huecosSubcategoria= data.huecosSubcategoria;
				avisoHuecos= data.avisoHuecos;
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
            });
}

function loadP26SFMMock(grid) {
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
			rowNum : 30,
			rowList : [ 30, 40, 50 ],
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
				grid.headerHeight("gridP26Header");  
			},
			loadComplete : function(data) {
				grid.actualPage = data.page;
				grid.localData = data;
				grid.sortIndex = null;
				grid.sortOrder = null;
				$("#AreaResultados .loading").css("display", "none");	
				if (grid.firstLoad)
					jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
			},
			onPaging : function(postdata) {			
				alreadySorted = false;
				grid.sortIndex = null;
				grid.sortOrder = null;
				grid.saveColumnState.call($(this), this.p.remapColumns);
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
				); } });
		
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN1"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN2"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN3"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN4"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN5"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["marca"]);

}

function loadP26CapacidadMock(grid) {
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
			rowNum : 30,
			rowList : [ 30, 40, 50 ],
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
				grid.headerHeight("gridP26Header");  
			},
			loadComplete : function(data) {
				grid.actualPage = data.page;
				grid.localData = data;
				grid.sortIndex = null;
				grid.sortOrder = null;
				$("#AreaResultados .loading").css("display", "none");	
				if (grid.firstLoad)
					jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
			},
			onPaging : function(postdata) {			
				alreadySorted = false;
				grid.sortIndex = null;
				grid.sortOrder = null;
				grid.saveColumnState.call($(this), this.p.remapColumns);
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
				); } });
		
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN1"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN2"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN3"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN4"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN5"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["marca"]);

}

function loadP26FacingMock(grid) {
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
			rowNum : 30,
			rowList : [ 30, 40, 50 ],
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
				grid.headerHeight("gridP26Header");  
			},
			loadComplete : function(data) {
				grid.actualPage = data.page;
				grid.localData = data;
				grid.sortIndex = null;
				grid.sortOrder = null;
				$("#AreaResultados .loading").css("display", "none");	
				if (grid.firstLoad)
					jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
			},
			onPaging : function(postdata) {			
				alreadySorted = false;
				grid.sortIndex = null;
				grid.sortOrder = null;
				grid.saveColumnState.call($(this), this.p.remapColumns);
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
			); } });
		
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN1"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN2"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN3"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN4"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN5"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["marca"]);

}


function loadP26FacingTextilMock(grid) {
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
			//rowNum : 30,
			rowNum : 30,
			rowList : [ 30, 40, 50 ],
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
			sortable : false,
			subGrid : true,
			//subGridUrl: './misumi/resources/p47PedidoAdicionalEC/p47mockPedidoAdicionalEC.json',
		    subGridRowExpanded: function (subgridDivId, rowId) {
		    	reloadDataP26Subgrid(rowId, subgridDivId);
		    	flgActualizarFacing = "";
		    },
			index: grid.sortIndex,
			sortname: grid.sortIndex,
			sortorder: grid.sortOrder,
			emptyrecords : grid.emptyRecords,
			gridComplete : function() {
				//grid.headerHeight("gridP26Header");  
			},
			loadComplete : function(data) {
				grid.actualPage = data.page;
				grid.localData = data;
				grid.sortIndex = null;
				grid.sortOrder = null;
				
				$("#AreaResultados .loading").css("display", "none");	
				if (grid.firstLoad)
					jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
			},
			onPaging : function(postdata) {			
				alreadySorted = false;
				grid.sortIndex = null;
				grid.sortOrder = null;
				grid.saveColumnState.call($(this), this.p.remapColumns);
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

		
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN1"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN2"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN3"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN4"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN5"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["marca"]);

}



function transformRowFilter(recordAlta){
	return recordAlta;
}
function hideShowColumns(grid){
	/**Se elimina el control de la estructura comercial porque no se mostrará por defecto***/
	/*if ($("input[name='p26_rad_tipoFiltro']:checked").val() == "1"){
		//Estructura comercial
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN1"]);
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN2"]);
		if($("#p26_cmb_categoria").combobox('getValue')=="null" || $("#p26_cmb_categoria").combobox('getValue')=="" || $("#p26_cmb_categoria").combobox('getValue')==null){
			jQuery(grid.nameJQuery).jqGrid('showCol', ["descCodN3"]);
		} else{
			jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN3"]);
		}
		if($("#p26_cmb_subcategoria").combobox('getValue')=="null" || $("#p26_cmb_subcategoria").combobox('getValue')=="" || $("#p26_cmb_subcategoria").combobox('getValue')==null){
			jQuery(grid.nameJQuery).jqGrid('showCol', ["descCodN4"]);
		}else{
			jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN4"]);
		}
		if($("#p26_cmb_segmento").combobox('getValue')=="null"  || $("#p26_cmb_segmento").combobox('getValue')=="" || $("#p26_cmb_segmento").combobox('getValue')==null){
			jQuery(grid.nameJQuery).jqGrid('showCol', ["descCodN5"]);
		}else{
			jQuery(grid.nameJQuery).jqGrid('hideCol', ["descCodN5"]);
		}
		
		
	} else { //Referencias
		jQuery(grid.nameJQuery).jqGrid('showCol', ["descCodN1","descCodN2","descCodN3","descCodN4","descCodN5"]);
		
	} */   
	
}

function reloadData(recarga) {
	//Inicializamos el número de fila para los campos ocultos que tenemos.
	numRow=1;
	var messageVal=findValidation();
	var tipoListado;
	if ($("#p26_txt_flgFacing").val()=='S'){ //Textil también será Facing
		tipoListado = 'FAC';
	}else if ($("#p26_txt_flgCapacidad").val()=='S'){
		tipoListado = 'CAP';
	}else{
		tipoListado = 'SFM';
	}
	$("#p26AreaErrores").attr("style", "display:none");
	if (messageVal!=null){
		$('#gridP26Sfm').jqGrid('clearGridData');
		$('#gridP26Cap').jqGrid('clearGridData');
		$('#gridP26Fac').jqGrid('clearGridData');
		$('#gridP26FacTextil').jqGrid('clearGridData');
		jQuery(gridSfm.nameJQuery).jqGrid('hideCol', ["descCodN1","descCodN2","descCodN3","descCodN4","descCodN5","marca"]);
		jQuery(gridSfm.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
		jQuery(gridCap.nameJQuery).jqGrid('hideCol', ["descCodN1","descCodN2","descCodN3","descCodN4","descCodN5","marca"]);
		jQuery(gridCap.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
		jQuery(gridFac.nameJQuery).jqGrid('hideCol', ["descCodN1","descCodN2","descCodN3","descCodN4","descCodN5","marca"]);
		jQuery(gridFac.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
		jQuery(gridFacTextil.nameJQuery).jqGrid('hideCol', ["descCodN1","descCodN2","descCodN3","descCodN4","descCodN5","marca"]);
		jQuery(gridFacTextil.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		//jQuery(gridSfm.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
		//jQuery(gridCap.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
		
		
		var valorArea = null;
		var codArea = null;
		var valorSeccion = null;
		var codSeccion = null;
		var valorCategoria = null;
		var codCategoria = null;
		var valorSubcategoria = null;
		var codSubcategoria = null;
		var valorSegmento = null;
		var codSegmento = null;
		
		if( $("#p26_cmb_area").combobox('getValue')!="null" && $("#p26_cmb_area").combobox('getValue')!=null){
			valorArea = $("#p26_cmb_area").combobox('getValue').split('*');
			codArea = valorArea[0];
		}
		if( $("#p26_cmb_seccion").combobox('getValue')!="null" && $("#p26_cmb_seccion").combobox('getValue')!=null){
			valorSeccion = $("#p26_cmb_seccion").combobox('getValue').split('*');
			codSeccion = valorSeccion[0];
		}
		if( $("#p26_cmb_categoria").combobox('getValue')!="null" && $("#p26_cmb_categoria").combobox('getValue')!=null){
			valorCategoria = $("#p26_cmb_categoria").combobox('getValue').split('*');
			codCategoria = valorCategoria[0];
		}
		if( $("#p26_cmb_subcategoria").combobox('getValue')!="null" && $("#p26_cmb_subcategoria").combobox('getValue')!=null){
			valorSubcategoria = $("#p26_cmb_subcategoria").combobox('getValue').split('*');
			codSubcategoria = valorSubcategoria[0];
		}
		if( $("#p26_cmb_segmento").combobox('getValue')!="null" && $("#p26_cmb_segmento").combobox('getValue')!=null){
			valorSegmento = $("#p26_cmb_segmento").combobox('getValue').split('*');
			codSegmento = valorSegmento[0];
		}
			
		if (recarga == "N"){
			listadoModificados = new Array();
			seleccionados = new Array();
		} else {
			obtenerListadoModificados();
		}
	
		var recordSfm = new VArtSfm ($("#centerId").val(), $("#p26_fld_referencia").val(), null, null, codArea, null, codSeccion, null, codCategoria,
				null, codSubcategoria, null, codSegmento, null,null,
				null, null, null, null, null, null,	null, null, null, null, listadoModificados, $("#p26_cmb_pedir").combobox('getValue'),null,$("#p26_cmb_loteSN").combobox('getValue'));
		
		//Reseteamos el array de modificados.
		listadoModificados = new Array();
		
		var objJson = $.toJSON(recordSfm.prepareVArtSfmToJsonObject());
		
	
			//$("#AreaResultados .loading").css("display", "block");
			if ($("#p26_txt_flgFacing").val()=='S'){
				//Mirar si es textil, en este caso habra que cargar el grid de textil
				
				if ($("input[name='p26_rad_tipoFiltro']:checked").val() == "1"){//Estructura comercial
					//Mirar la selección del combo de Area
					var valor = $("#p26_cmb_area").val().split('*');
					var cod = valor[0];
					if (cod=="3" ){
						grid = gridFacTextil;
					} else {
						grid = gridFac;
					}
				} else {
					grid = gridFac;
				}
					
				
			}else if ($("#p26_txt_flgCapacidad").val()=='S'){
				grid = gridCap;
			}else{
				grid = gridSfm;
			}
			if (grid.firstLoad) {
				jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
		        grid.firstLoad = false;
		        if (grid.isColState) {
		            $(this).jqGrid("remapColumns", grid.myColumnsState.permutation, true);
		        }
		    } else {
		    	grid.myColumnsState = grid.restoreColumnState(grid.cm);
		    	grid.isColState = typeof (grid.myColumnsState) !== 'undefined' && grid.myColumnsState !== null;
		    	//jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), false);
		    }
			$.ajax({
				type : 'POST',
				//url : './misumi/resources/p26SfmCapacidad/mockFacing.json',
				url : './sfmCapacidad/loadDataGrid.do?page='+grid.getActualPage()+'&max='+grid.getRowNumPerPage()+'&index='+grid.getSortIndex()+'&sortorder='+grid.getSortOrder()+'&recarga='+recarga + '&tipoListado='+tipoListado,
				data : objJson,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {	
					//Si se busca por referencia y no existe se muestra error
					if(data.estado != 0){
						createAlert(replaceSpecialCharacters(data.descEstado), "ERROR");
						$("#AreaResultados .loading").css("display", "none");	
						$(grid.nameJQuery).jqGrid('clearGridData');
					} else{
						
						$(grid.nameJQuery).jqGrid('clearGridData');
						
						
						 $("#p26_AreaNotaPie").hide();
						 $("#p26_AreaEstados").hide();
						 $("#p26_AreaSoloImagen").hide();
						 $("#p26_Area_mensajes").hide();
	
						
					if (data.flgFacing != null){
						$("#p26_txt_flgFacing").val(data.flgFacing);
					}
					if (data.flgCapacidad != null){
						$("#p26_txt_flgCapacidad").val(data.flgCapacidad);
					}
					if (data.flgStock){
						$("#p26_txt_flgStock").val(data.flgStock);
					}
						 
					if ($("#p26_txt_flgFacing").val()=='S'){
						if (data.datos != null && data.datos.records != null && data.datos.records != 0){
							if (data.datos.rows[0] != null && data.datos.rows[0].codN1 != null && data.datos.rows[0].codN1 == 3){
								grid = gridFacTextil;
							} else {
								grid = gridFac;
							}
						}
					}else if ($("#p26_txt_flgCapacidad").val()=='S'){
						grid = gridCap;
					}else{
						grid = gridSfm;
					}
					

					//Comprobación para datos de textil y facing-capacidad
					if ($("#p26_txt_flgFacing").val()=='S'){
						if (data.datos != null && data.datos.records != null && data.datos.records != 0){
							
							
							/* P-55928
							 * Oculto la columna pedir (Activa) para grig FAC CAP
							 * @BICUGUAL 
							 */
							if ($("#p26_txt_flgFacingCapacidad").val()=='S'){
								jQuery(gridFac.nameJQuery).jqGrid('hideCol', ["facingPrevio"]);
								jQuery(gridFac.nameJQuery).jqGrid('showCol', ["capacidad"]);
								
								jQuery(gridFac.nameJQuery).jqGrid('hideCol', ["pedir"]);//<-P-55928
								
							} else {
								jQuery(gridFac.nameJQuery).jqGrid('hideCol', ["capacidad"]);
								jQuery(gridFac.nameJQuery).jqGrid('showCol', ["facingPrevio"]);
								
								jQuery(gridFac.nameJQuery).jqGrid('showCol', ["pedir"]);//<-P-55928
							}
							
							
							if (recarga == "N"){
								jQuery(gridFac.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
							}
						}
					}
					
					if ($("#p26_txt_flgFacing").val()=='S'){
						$("#gbox_gridP26Sfm").hide();
						$("#gbox_gridP26Cap").hide();
						$("#gbox_gridP26FacTextil").hide();
						$("#gbox_gridP26Fac").hide();
						
						if (data.datos != null && data.datos.records != null && data.datos.records != 0){
							if (data.datos.rows[0] != null && data.datos.rows[0].codN1 != null && data.datos.rows[0].codN1 == 3){
								$("#gbox_gridP26FacTextil").show(); 
								$("#gbox_gridP26Fac").hide();
								
								$('#gridP26Fac').jqGrid('clearGridData');
								
								
								flgDespliegueSubgrid = false;
								$("#AreaResultados").show();
								$(gridFacTextil.nameJQuery)[0].addJSONData(data.datos);
								gridFacTextil.actualPage = data.datos.page;
								gridFacTextil.localData = data.datos;
							
								
								p26ControlesPantallaTextil();
								
								
								if (($("input[name='p26_rad_tipoFiltro']:checked").val() == "2") && (data.datos.rows[0].nivelLote >0)){ //La consulta ha sido por referencia 
									$("#1 td.sgcollapsed",gridFacTextil[0]).click();
										
								}
								
								//$('#gridP26FacTextil').setGridParam({sortname:''});
							} else {
								$("#gbox_gridP26Fac").show();
								$("#gbox_gridP26FacTextil").hide();
								
								 $("#p26_AreaNotaPie").show();
								
								$('#gridP26FacTextil').jqGrid('clearGridData');
								
								
								$("#AreaResultados").show();
								$(gridFac.nameJQuery)[0].addJSONData(data.datos);
								gridFac.actualPage = data.datos.page;
								gridFac.localData = data.datos;
								//$('#gridP26Fac').setGridParam({sortname:''});
							}
						}
						
					}else if ($("#p26_txt_flgCapacidad").val()=='S'){
						$("#gbox_gridP26Sfm").hide();
						$("#gbox_gridP26Cap").show();
						$("#gbox_gridP26Fac").hide();
						$("#gbox_gridP26FacTextil").hide();
						$("#AreaResultados").show();
						$(gridCap.nameJQuery)[0].addJSONData(data.datos);
						gridCap.actualPage = data.datos.page;
						gridCap.localData = data.datos;
						//$('#gridP26Cap').setGridParam({sortname:''});
					}else{
						$("#gbox_gridP26Sfm").show();
						$("#gbox_gridP26Cap").hide();
						$("#gbox_gridP26Fac").hide();
						$("#gbox_gridP26FacTextil").hide();
						$("#AreaResultados").show();
						$(gridSfm.nameJQuery)[0].addJSONData(data.datos);
						gridSfm.actualPage = data.datos.page;
						gridSfm.localData = data.datos;
						//$('#gridP26Sfm').setGridParam({sortname:''});
					}
					var ids = jQuery(grid.nameJQuery).jqGrid('getDataIDs'), i, l = ids.length;
					
					$("#AreaResultados").hide();
					var cont = 0;
				    for (i = 0; i < l; i++) {
				    		    	
				    	
						if ($("#p26_txt_flgFacing").val()=='S'){
							
						
				    		
							if ($("#"+ids[i]+"_expositor").val() == "S"){
								jQuery(gridFac.nameJQuery).jqGrid('setCell',ids[i],'facingCentro',expositor,{'font-weight':'bold','color':'#ff4aff','text-align':'center'},{ title: expositorTitle});
							}  else {
								
							
					    		
								if (data.datos.rows[i].flgSfmFijo !='B'){ //el campo Facing sera editable
						    		
									jQuery(grid.nameJQuery).jqGrid('editRow', ids[i], false);
									
									$("#"+ids[i]+"_facingCentro").filter_input({regex:'[0-9]'});

						    		$("#"+ids[i]+"_facingCentro").formatNumber({format:"0",locale:"es", round:false});
						    		$("#"+ids[i]+"_facingCentro_tmp").formatNumber({format:"0",locale:"es", round:false});
						    		
						    		if (data.datos.rows[i].flgSoloImagen =='S'){ //Es solo imagen, el texto se pintara en naranja
						    			
						    			$("#"+ids[i]+"_facingCentro").addClass("p26_ReferenciaSoloImagen");
						    			
						    			$("#p26_Area_mensajes").show().css('display', 'inline-block');
						    			$("#p26_AreaSoloImagen").show().css('display', 'inline-block');
						    		}
						    			
								    $("#"+ids[i]+"_facingCentro").focusout(function() {
								    	validacionFacing(this.id, 'S');
								    });
						    	
								    if (data.datos.rows[0] != null && data.datos.rows[0].codN1 != null && data.datos.rows[0].codN1 == 3 && $("#"+ids[i]+"_nivelLote").val() != '0') {
							    		//Si es textil, el campo facin aparecera en blanco
								    	
							    			//$("#"+ids[i]+"_facingCentro").val('');	
							    			//$("#"+ids[i]+"_facingCentro_tmp").val('');	 
							    	}
								}  else { //El campo facing sera no editable 
									
								    $("#p26_Area_mensajes").show().css('display', 'inline-block');
									$("#p26_AreaEstados").show().css('display', 'inline-block');
									
						    		if (data.datos.rows[0] != null && data.datos.rows[0].codN1 != null && data.datos.rows[0].codN1 == 3 && $("#"+ids[i]+"_nivelLote").val() != '0') {
							    		//Si es textil y lote, el campo aparecera vacio
						    			
							    		jQuery(grid.nameJQuery).jqGrid('setCell',ids[i],'facingCentro',' ',{'font-weight':'bold','color':'#ff4aff','text-align':'center'});
							
							    	} else {
							    		
							    		if (data.datos.rows[i].flgSoloImagen =='S'){
							    			//El valor aparecera en naranja
							    			jQuery(grid.nameJQuery).jqGrid('setCell',ids[i],'facingCentro',data.datos.rows[i].facingCentro,{'font-weight':'bold','color':'#fd9852','text-align':'center'});
							    			$("#p26_Area_mensajes").show().css('display', 'inline-block');
							    			$("#p26_AreaSoloImagen").show().css('display', 'inline-block');
							    			
							    			
							    		} else {
							    			//El valor aparecera en rosa
								    		jQuery(grid.nameJQuery).jqGrid('setCell',ids[i],'facingCentro',data.datos.rows[i].facingCentro,{'font-weight':'bold','color':'#ff4aff','text-align':'center'});
							    		}
							    		
							    	}
								}

							}
							
							//(Pet.169-sfm quitar la foto)   generateTooltip(ids[i],data.datos.rows[i]);
							
						}else if ($("#p26_txt_flgCapacidad").val()=='S'){
							
							if (data.datos.rows[i].flgSfmFijo !='B'){ //el campo Capacidad sera editable
				    		
								jQuery(grid.nameJQuery).jqGrid('editRow', ids[i], false);
								
								$("#"+ids[i]+"_capacidad").filter_input({regex:'[0-9]'});
					    		
					    		//Formateamos el campo de capacidad
						    	//$("#"+ids[i]+"_capacidad").formatNumber({format:"0.000",locale:"es"});
						    	//$("#"+ids[i]+"_capacidad_tmp").formatNumber({format:"0.000",locale:"es"});
								$("#"+ids[i]+"_capacidad").formatNumber({format:"0",locale:"es", round:false});
						    	$("#"+ids[i]+"_capacidad_tmp").formatNumber({format:"0",locale:"es", round:false});
						    	
						    	$("#"+ids[i]+"_capacidad").focusout(function() {
						    		validacionCapacidad(this.id, 'S');
						    	});
							
							} else { //El campo capacidad sera no editable  y en rosa
								
								$("#p26_Area_mensajes").show().css('display', 'inline-block');
								$("#p26_AreaEstados").show().css('display', 'inline-block');
								
								jQuery(grid.nameJQuery).jqGrid('setCell',ids[i],'capacidad',data.datos.rows[i].capacidad,{'font-weight':'bold','color':'#ff4aff','text-align':'center'});	
							}
				    		
							//(Pet.169-sfm quitar la foto)    generateTooltip(ids[i],data.datos.rows[i]); //<- P-55928 
				    	}
				    	else
				    	{
				    		if (data.datos.rows[i].flgSfmFijo !='B'){ //el campo sfm sera editable
				    			
				    			jQuery(grid.nameJQuery).jqGrid('editRow', ids[i], false);
				    			
				    			$("#"+ids[i]+"_sfm").filter_input({regex:'[0-9,]'});
						    	$("#"+ids[i]+"_coberturaSfm").filter_input({regex:'[0-9,]'});

						    	//Formateamos los campos de SFM y cobertura SFM
						    	if ($("#"+ids[i]+"_porcionConsumidor").val() == 'S' ){ 
						    		//Si el valor de porcion consumidor es igual a S, el campo SFM debe aparecer sin decimales
						    		$("#"+ids[i]+"_sfm").val(Math.floor($("#"+ids[i]+"_sfm").val())).formatNumber({format:"0",locale:"es"});
						    		
						    	} else {
						    		$("#"+ids[i]+"_sfm").formatNumber({format:"0.000",locale:"es"});
						    	
						    	}
						    		
						    	$("#"+ids[i]+"_coberturaSfm").formatNumber({format:"0.000",locale:"es"});
						    	$("#"+ids[i]+"_sfm_tmp").formatNumber({format:"0.000",locale:"es"});
						    	$("#"+ids[i]+"_coberturaSfm_tmp").formatNumber({format:"0.000",locale:"es"});
						    	
						    	$("#"+ids[i]+"_sfm").focusout(function() {
						    		validacionSfm(this.id, 'S');
						    	});
						    	
						    	$("#"+ids[i]+"_coberturaSfm").focusout(function() {
						    		validacionCoberturaSfm(this.id, 'S');
						    	});
						    	
				    		} else { //El campo sfm sera no editable  y en rosa
				    			
				    			$("#p26_Area_mensajes").show().css('display', 'inline-block');
				    			$("#p26_AreaEstados").show().css('display', 'inline-block');
				    			
				    			jQuery(grid.nameJQuery).jqGrid('setCell',ids[i],'sfm',data.datos.rows[i].sfm,{'font-weight':'bold','color':'#ff4aff','text-align':'center'});	
				    			jQuery(grid.nameJQuery).jqGrid('setCell',ids[i],'coberturaSfm',data.datos.rows[i].coberturaSfm,{'font-weight':'bold','color':'#ff4aff','text-align':'center'});	
				    		}
				    		
				    		//(Pet.169-sfm quitar la foto)    generateTooltip(ids[i],data.datos.rows[i]);//<- P-55928 
				    		
				    	}
				    	cont++;
				    }
				    cargarOrdenacionesColumnas();
				    var totalRegistrosFac=$(gridFac.nameJQuery).getGridParam("reccount");
		        	//Si existen registros cargados en la tabla, llamo a gestionar huecos
		        	if (totalRegistrosFac!=null && totalRegistrosFac>0){
		        		mostrarHuecos();
		        	}
					$("#AreaResultados").show();
					
				

					$("#p26_btn_buscar").focus();

					$("#AreaResultados .loading").css("display", "none");	
					if (data.datos.records == null || data.datos.records == 0){
						if ($("input[name='p26_rad_tipoFiltro']:checked").val() == "1"){
							createAlert(replaceSpecialCharacters(gridEmptyRecords), "ERROR");
						}
						else
						{
							if (data.estructuraArticulo != null && 'S'==data.estructuraArticulo.flgFacing){
								createAlert(replaceSpecialCharacters(gridEmptyRecordsSfmFac), "ERROR");
							}
							else if (data.estructuraArticulo != null && 'S'==data.estructuraArticulo.flgCapacidad){
								createAlert(replaceSpecialCharacters(gridEmptyRecordsSfmCap), "ERROR");
							}
							else if (data.estructuraArticulo != null && 'S'==data.estructuraArticulo.flgStockFinal){
								if ((data.pedir == 'N') || (data.pedir == '') || (data.pedir == null)) {
									createAlert(replaceSpecialCharacters(gridEmptyRecordsSfm), "ERROR");
								} else {
									createAlert(replaceSpecialCharacters(gridEmptyRecordsRefSfmNoStockFinalMinimo), "ERROR");
								}
							}else{
								createAlert(replaceSpecialCharacters(gridEmptyRecordsEstructuraNoParam), "ERROR");
							}
						}
					} 
					}
					$("#infoRef").show();
					flgDespliegueSubgrid = true;
				},
				error : function (xhr, status, error){
					$("#AreaResultados .loading").css("display", "none");	
					handleError(xhr, status, error, locale);				
		        }
			});		
	}
}


function reloadDataP26Subgrid(rowId, subgridDivId) {

	subgridActual = subgridDivId;
	var rowData = jQuery(gridFacTextil.nameJQuery).getRowData(rowId);
	var codId = rowData['codArticulo'];
	

	var ids = jQuery(gridFacTextil.nameJQuery).jqGrid('getDataIDs');

	
	var facingCentro;
	if($(gridFacTextil.nameJQuery + " [aria-describedby='gridP26FacTextil_facingCentro'] #"+rowId+"_facingCentro") && $(gridFacTextil.nameJQuery + " [aria-describedby='gridP26FacTextil_facingCentro'] #"+rowId+"_facingCentro").val()){
		facingCentro = $(gridFacTextil.nameJQuery + " [aria-describedby='gridP26FacTextil_facingCentro'] #"+rowId+"_facingCentro").val();
	}
	

//	var mostrarIconoModificado = "";
//	if (facingCentro != -1) { //El facing ha sido modificado, hay que mostrar el icono de modificado en la referencia lote.
//		mostrarIconoModificado = "si";	
//	}
	
	
	//$(gridFacTextil.nameJQuery + " [aria-describedby='gridP26FacTextil_facingCentro'] #"+rowId+"_facingCentro").val('');

	//Petición MISUMI-34 - Si la fila está editada (9), al llamar a loadDataSubGridTextil, queremos que las referencias hijas, hereden el valor de la madre,
	//por eso pasamos btnGuardarClick = "N". Si sin embargo, vienen de guardarse (8), queremos que las hijas no hereden el valor de la madre 
	//y que las hijas saquen sus propios valores, por eso pasamos btnGuardarClick = "S"; 
	var estadoFila = $("#"+rowId+"_fac_codError_orig").val();
	if(estadoFila == 8){
		btnGuardarClick = "S";
	}else{
		btnGuardarClick = "N";
	}
	
	var recordSfm = new VArtSfm ($("#centerId").val(), codId, null, null, null, null, null, null, null,
			null, null, null, null, null,null,
			null, null, null, null, null, null,	null, null, null, null, null, 
			$("#p26_cmb_pedir").combobox('getValue'),facingCentro,$("#p26_cmb_loteSN").combobox('getValue'),null,flgActualizarFacing);
	
	var objJson = $.toJSON(recordSfm.prepareVArtSfmToJsonObject());	
	
	$.ajax({
		type : 'POST',
		url : './sfmCapacidad/loadDataSubGridTextil.do?btnGuardarClick='+btnGuardarClick,
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
			
			
				if (subgridDivId == subgridActual) { //Si bajamos con las fechas muy rapido, puede dar error porque no sabe en que subgrid cargar.
													 //De esta manera controlamos que solo haga la carga en el ultimo.
					var subgridTableId = subgridDivId + "_t";
					
					//Si el subgrid ya esta abierto no lo volvemos a crear. solo lo cargamos con los nuevos datos
					if  (!($("#" + subgridTableId).is(':visible'))) {
						
				        $("#" + subgridDivId).html("<table id='" + subgridTableId + "'></table>"); 
				      
				        $("#" + subgridTableId).jqGrid({
				            datatype: 'local',
				            colNames : gridFacTextil.subgridColNames,
				    		colModel : gridFacTextil.subgridCm,
				    		//multiselect: true,
				    		altclass: "ui-priority-secondary",
				    		altRows: false,
				    		//rowNum: data.datos.records,
				    		sortable : true,
				    		height: '100%',
				    		width : "auto"
				        });
				       
			        
				   }
	
			        $("#" + subgridTableId)[0].addJSONData(data.datos);
			        $("#" + subgridTableId).actualPage = data.datos.page;
			        $("#" + subgridTableId).localData = data.datos;
			       
			        
			        $("#" + subgridTableId).closest("div.ui-jqgrid-view")
		            .children("div.ui-jqgrid-hdiv")
		            .hide();
			        
			        var ids = $("#" + subgridTableId).jqGrid('getDataIDs'), i, l = ids.length;
					
		//			$("#AreaResultados").hide();
					
					
				    for (i = 0; i < l; i++) {
				    	
				    	
				    	if (data.datos.rows[i].flgSfmFijo !='B'){ //Deshabilitamos 
				    		
				    		$("#" + subgridTableId).jqGrid('editRow', ids[i], false);
				    	} else {
				    	
				    		$("#" + subgridTableId).jqGrid('setCell',ids[i],'facingCentro',data.datos.rows[i].facingCentro,{'font-weight':'bold','color':'#ff4aff','text-align':'center'});
				    	}
				    	

						$("#" + subgridTableId + " #"+ids[i]+"_facingCentro").filter_input({regex:'[0-9]'});
					    		
					    //Formateamos el campo de facing
						 $("#" + subgridTableId + " #"+ids[i]+"_facingCentro").formatNumber({format:"0",locale:"es", round:false});
						 $("#" + subgridTableId.nameJQuery + " #"+ids[i]+"_facingCentro_tmp").formatNumber({format:"0",locale:"es", round:false});
						    	
				    	$("#" + subgridTableId + " #"+ids[i]+"_facingCentro").focusout(function(e) {
				    		controlCampoFacingTextilSubgrid(e);
				    	});
				    	
				    	$("#" + subgridTableId + " #"+ids[i]+"_facingCentro").keydown(function(e) {
				    		controlNavegacionSubgrid(e);
				    	});
				    	
				    	
				    }		
			        
				   
					$("#AreaResultados").show();
			        $("#AreaResultados .loading").css("display", "none");	
			        
			        

			        /*
				    if (mostrarIconoModificado == "si") { //Tenemos que marcar la referencia lote como modificada
				    
				    	var valor = subgridTableId.split('_');
				    	var fila = valor[1];

				    	
				    	//En este caso se ha modificado el campo y hay que establecer el icono de modificación.
						$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_mensaje'] #"+ fila + "_fac_divError").hide();
						$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_mensaje'] #"+ fila + "_fac_divGuardado").hide();
						$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_mensaje'] #"+ fila + "_fac_divModificado").show();
						$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_mensaje'] #"+ fila + "_facingCentro_tmp").val(facingCentro);
						$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_mensaje'] #"+ fila + "_fac_codError_orig").val('9');

						//Añadimos la fila al array.
						addSeleccionados(fila);
				    	
				    }
				    */
		
				    $(gridFacTextil.nameJQuery + " [aria-describedby='gridP26FacTextil_facingCentro'] #"+rowId+"_facingCentro").focus();
				    $(gridFacTextil.nameJQuery + " [aria-describedby='gridP26FacTextil_facingCentro'] #"+rowId+"_facingCentro").select();
				   
				    
		        
				}
			},
			error : function (xhr, status, error){
				$("#AreaResultados .loading").css("display", "none");	
				handleError(xhr, status, error, locale);				
	        }	
			
		});		
	
}




function actualizarFacingSubgrid(rowId, subgridDivId, event) {

	var rowData = jQuery("#" + subgridDivId).getRowData(rowId);
	var codId = rowData['codArticulo'];


	var facingCentro =  $("#" + subgridDivId + " #"+rowId+"_facingCentro").val();
	var codArticuloLote =  $("#" + subgridDivId + " #"+rowId+"_codArticuloLote").val();

	
//	idFocoSubgrid = rowId + "_facingCentro";

	
	var recordSfm = new VArtSfm ($("#centerId").val(), codId, null, null, null, null, null, null, null,
			null, null, null, null, null,null,
			null, null, null, null, null, null,	null, null, null, null, null, 
			$("#p26_cmb_pedir").combobox('getValue'),facingCentro,$("#p26_cmb_loteSN").combobox('getValue'),codArticuloLote);

	var objJson = $.toJSON(recordSfm.prepareVArtSfmToJsonObject());	
	
	$.ajax({
		type : 'POST',
		url : './sfmCapacidad/actualizarFacingSubgrid.do?',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
				
			var subgridTableId = subgridDivId;
		

	       // $("#" + subgridTableId)[0].addJSONData(data.datos);
	       // $("#" + subgridTableId).actualPage = data.datos.page;
	       // $("#" + subgridTableId).localData = data.datos;
	       
	        
	        $("#" + subgridTableId).closest("div.ui-jqgrid-view")
            .children("div.ui-jqgrid-hdiv")
            .hide();
	        
	        var ids = $("#" + subgridTableId).jqGrid('getDataIDs'), i, l = ids.length;
			

	    	//El campo modificado lo ponemos con el icono de modificación.
			$("#" + subgridTableId + " #"+ rowId + "_fac_divError").hide();
			$("#" + subgridTableId + " #"+ rowId + "_fac_divGuardado").hide();
			$("#" + subgridTableId + " #"+ rowId + "_fac_divModificado").show();
			$("#" + subgridTableId + " #"+ rowId + "_facingCentro_tmp").val(facingCentro);
			$("#" + subgridTableId + " #"+ rowId + "_fac_codError_orig").val('9');

			
			var estadoReferenciaLote = "";
			var referenciaLoteACero = ""
			
		    for (i = 0; i < l; i++) {
		    	
		    	//Cada vez que cambiamos el facing de alguna de la referencias hijas, miramos 
		    	//si el campo codError es = 9. Si es asi, cambiamos el icono 
		    	//de la referencia madre a modificado.
		    	if ($("#" + subgridTableId + " #"+ids[i]+"_fac_codError_orig").val() == 9) {
		    		estadoReferenciaLote = "modificado";
		    	}
		    	
		    	if ($("#" + subgridTableId + " #"+ids[i]+"_facingCentro").val() != 0) { // Si alguna de la hijas es distinta de cero, 
																						//la referencia lote no sera cero
		    		 referenciaLoteACero= "N";
		    	}
    	
		    }

		   
		    if (estadoReferenciaLote == "modificado") { //Tenemos que marcar la referencia lote como modificada
		    
		    	var valor = subgridTableId.split('_');
		    	var fila = valor[1];

 	
		    	//En este caso se ha modificado el campo y hay que establecer el icono de modificación.
				$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_mensaje'] #"+ fila + "_fac_divError").hide();
				$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_mensaje'] #"+ fila + "_fac_divGuardado").hide();
				$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_mensaje'] #"+ fila + "_fac_divModificado").show();
				$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_mensaje'] #"+ fila + "_facingCentro_tmp").val(facingCentro);
				$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_mensaje'] #"+ fila + "_fac_codError_orig").val('9');
				
				/*
				if ((referenciaLoteACero == "N") || (referenciaLoteACero == "")) { 
					$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_facingCentro'] #"+ fila + "_facingCentro").val('1');
					
				} else {
					//La referencia lote hay que ponerla a 1, porque alguna de las refencias hijas no tiene el valor 0
					$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_facingCentro'] #"+ fila + "_facingCentro").val('0');
				} */
				
				if (referenciaLoteACero != "N") { 
					//La referencia lote hay que ponerla a 0, porque todas las refencias hijas son cero
					$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_facingCentro'] #"+ fila + "_facingCentro").val('0');
				} 
				//MISUMI-468  -  Lo comentamos para que mantenga el valor en pantalla aun modificando a las hijas
				//else {
					//ANTIGUO ----- La referencia lote hay que ponerla a 1, porque alguna de las refencias hijas no tiene el valor 0 -----
				//	$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_facingCentro'] #"+ fila + "_facingCentro").val('1');
				//}

				//Añadimos la fila al array.
				addSeleccionados(fila);
		    	
		    }
		    
		    

		   
		   
		   $("#" + subgridTableId + " #"+idFocoSubgrid).focus();
		   $("#" + subgridTableId + " #"+idFocoSubgrid).select();
		    
		    
		    
		   
			$("#AreaResultados").show();
	        $("#AreaResultados .loading").css("display", "none");	
        
	       
			
			},
			error : function (xhr, status, error){
				$("#AreaResultados .loading").css("display", "none");	
				handleError(xhr, status, error, locale);				
	        }	
			
		});		

}



/*Clase de constantes para le GRID*/
function GridP26Sfm (locale){
	// Atributos
	this.name = "gridP26Sfm"; 
	this.nameJQuery = "#gridP26Sfm"; 
	this.i18nJSON = './misumi/resources/p26SfmCapacidad/p26sfmCapacidad_' + locale + '.json';
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
	    "hidden" : true
	},{
		"name" : "descCodN4",
		"index":"descCodN4", 
		"width" : 25,
		"formatter": function(value, options, rData){ return rData['codN4'] + '-' +value  ;  },
		"hidden" : true
	},{
		"name" : "descCodN5",
		"index":"descCodN5", 
		"width" : 25,
		"formatter": function(value, options, rData){ return rData['codN5'] + '-' +value  ;  },
		"hidden" : true
	},{
		"name" : "codArticulo",
		"index":"codArticulo", 
		"width" : 20,
		"sortable" : true
	},{
		"name" : "denomInforme",
		"index":"denomInforme", 
		"width" : 60,
		"sortable" : true
	},{
		"name" : "marca",
		"index":"marca", 
		"width" : 15,
		"sortable" : true
	},{                                                                                                                    
		"name" : "lmin",
		"index":"lmin",
		"formatter": limiteInferiorSFMFormatNumber,
		"width" : 13,
		"sortable" : true
	},{                                                                                                                    
		"name" : "lsf",
		"index":"lsf",
		"formatter": limiteSuperiorSFMFormatNumber,
		"width" : 13,
		"sortable" : true
	},{
		"name" : "sfm",
		"index":"sfm",
		"fixed":true,
		"width" : 70,
		"editable": true,
		"edittype":"text",
        "editoptions":{
        	"autocomplete": "off",
            "size":"10",
            "maxlength":"15",
            "dataEvents": [
	                              {//control para el keypress
	                            	  type: 'keydown',
	                                  fn: controlNavegacion,
	                              }// cierra el control del keypress
	                              
                           ]   
         	},
		"cellEdit" : true,
		"cellsubmit" : "clientArray",
		"sortable" : true
	},{
		"name" : "coberturaSfm",
		"index":"coberturaSfm", 
		"fixed":true,
		"width" : 70,
		"editable": true,
		"edittype":"text",
        "editoptions":{
        	"autocomplete": "off",
            "size":"10",
            "maxlength":"15",
            "dataEvents": [
	                              {//control para el keypress
	                            	  type: 'keydown',
	                                  fn: controlNavegacion,
	                              }// cierra el control del keypress
	                          ]   
        	},
		"cellEdit" : true,
		"cellsubmit" : "clientArray",
		"sortable" : true
	},{
		"name" : "ventaMedia",
		"index":"ventaMedia", 
		"formatter":limitesFormatNumber,
		"width" : 10,
		"sortable" : true
	},{
		"name" : "ventaAnticipada",
		"index":"ventaAnticipada", 
		"formatter":"number",
		"width" : 15,
		"sortable" : true
	},{
		"name" : "uc",
		"index":"uc", 
		"formatter":p26FormatUC,
		"width" : 10,
		"sortable" : true
	},{
		"name" : "diasStock",
		"index":"diasStock", 
		"formatter":formatNumberDecimales,
		"width" : 10,
		"sortable" : true
	},{
		"name" : "stock",
		"index":"stock", 
		"formatter":formatNumberDecimales,
		"width" : 10,
		"sortable" : true
	},{
		"name" : "vidaUtil",
		"index":"vidaUtil", 
		"formatter":"number",
		"width" : 10,
		"sortable" : true
	},{
		"name" : "mensaje",
		"index":"mensaje", 
		"formatter": imageFormatMessageSfm,
		"fixed":true,
		"width" : 50,
		"sortable" : true
	}			
	];
	this.sortIndex = null;
	//this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP26Sfm"; 
	this.pagerNameJQuery = "#pagerP26Sfm";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;
    this.myColumnStateName = 'gridP26Sfm.colState';
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
        var colModel = jQuery(grid.nameJQuery).jqGrid('getGridParam', 'colModel'); 
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
        	
            window.localStorage.setItem(gridSfm.myColumnStateName, JSON.stringify(columnsState));
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
	
function GridP26Cap (locale){
	// Atributos
	this.name = "gridP26Cap"; 
	this.nameJQuery = "#gridP26Cap"; 
	this.i18nJSON = './misumi/resources/p26SfmCapacidad/p26sfmCapacidad_' + locale + '.json';
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
	    "hidden" : true 
	},{
		"name" : "descCodN4",
		"index":"descCodN4", 
		"width" : 25,
		"formatter": function(value, options, rData){ return rData['codN4'] + '-' +value  ;  },
		"hidden" : true 
	},{
		"name" : "descCodN5",
		"index":"descCodN5", 
		"width" : 25,
		"formatter": function(value, options, rData){ return rData['codN5'] + '-' +value  ;  },
		"hidden" : true 
	},{
		"name" : "codArticulo",
		"index":"codArticulo", 
		"width" : 20
	},{
		"name" : "denomInforme",
		"index":"denomInforme", 
		"width" : 58						
	},{
		"name" : "marca",
		"index":"marca", 
		"width" : 15
	},{                                                                                                                    
		"name" : "capacidad",
		"index":"capacidad", 
		"fixed":true,
		"width" : 70,
		"editable": true,
		"edittype":"text",
        "editoptions":{
        	"autocomplete": "off",
            "size":"10",
            "maxlength":"15",
            "dataEvents": [
	                              {//control para el keypress
	                            	  type: 'keydown',
	                                  fn: controlNavegacion,
	                              }// cierra el control del keypress
	                          ]   
        	},
		"cellEdit" : true,
		"cellsubmit" : "clientArray"
	},{
		"name" : "multiplicadorFacing",
		"index":"multiplicadorFacing", 
		"formatter":formatNumberDecimales,
		"width" : 12,
		"hidden" : true
	},{
		"name" : "imagenComercialMin",
		"index":"imagenComercialMin", 
		"formatter":formatNumberDecimales,
		"width" : 12,
		 "hidden" : true
	},{
		"name" : "uc",
		"index":"uc", 
		"formatter":formatNumberDecimales,
		"width" : 10
	},{
		"name" : "diasStock",
		"index":"diasStock", 
		"formatter":formatNumberDecimales,
		"width" : 10
	},{
		"name" : "stock",
		"index":"stock",
		"formatter":formatNumberDecimales,
		"width" : 10
	},{
		"name" : "vidaUtil",
		"index":"vidaUtil", 
		"formatter":"number",
		"width" : 10,
		"sortable" : true
	},{
		"name" : "mensaje",
		"index":"mensaje", 
		"formatter": imageFormatMessageCap,	
		"fixed":true,
		"width" : 50
	}			
	]; 
	this.sortIndex = null;
	//this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP26Cap"; 
	this.pagerNameJQuery = "#pagerP26Cap";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;
	 this.myColumnStateName = 'gridP26Cap.colState';
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
        var colModel = jQuery(grid.nameJQuery).jqGrid('getGridParam', 'colModel'); 
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
            window.localStorage.setItem(gridCap.myColumnStateName, JSON.stringify(columnsState));
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

function GridP26Fac (locale){
	// Atributos
	this.name = "gridP26Fac"; 
	this.nameJQuery = "#gridP26Fac"; 
	this.i18nJSON = './misumi/resources/p26SfmCapacidad/p26sfmCapacidad_' + locale + '.json';
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
	    "hidden" : true 
	},{
		"name" : "descCodN4",
		"index":"descCodN4", 
		"width" : 25,
		"formatter": function(value, options, rData){ return rData['codN4'] + '-' +value  ;  },
		"hidden" : true 
	},{
		"name" : "descCodN5",
		"index":"descCodN5", 
		"width" : 25,
		"formatter": function(value, options, rData){ return rData['codN5'] + '-' +value  ;  },
		"hidden" : true 
	},{
		"name" : "flgNueva",
		"index":"flgNueva", 
		"formatter" : p26FormatFlgNueva,
		"width" : 53
	},{
		"name" : "codArticulo",
		"index":"codArticulo", 
		"width" : 84
	},{
		"name" : "denomInforme",
		"index":"denomInforme", 
		"width" : 205						
	},{
		"name" : "ccEstr",
		"index":"ccEstr", 
		"width" : 43
	},{
		"name" : "cc",
		"index":"cc", 
		"width" : 30
	},{
		"name" : "pedible",
		"index":"pedible", 
		"width" : 43,
		"hidden" : true //<- P-55928 (Modificada de false a true)
	},{
		"name" : "marca",
		"index":"marca", 
		"width" : 43
	},{                                                                                                                    
		"name" : "pedir",
		"index":"pedir", 
		"width" : 43
	},{                                                                                                                    
		"name" : "tipoGama",
		"index":"tipoGama", 
		"width" : 64
	},{                                                                                                                    
		"name" : "diasStock",
		"index":"stock",
		"formatter":formatNumberDecimales,
		"width" : 43
	},{                                                                                                                    
		"name" : "stock",
		"index":"stock",
		"formatter":p26FormatStock,
		"width" : 43
	},{
		"name" : "tipoAprov",
		"index":"tipoAprov", 
		"width" : 43
	},{                                                                                                                    
		"name" : "uc",
		"index":"uc", 
		"formatter":formatNumberDecimales,
		"width" : 43
	},{                                                                                                                    
		"name" : "capacidad",
		"index":"capacidad", 
		"formatter":"number",
		"width" : 54
	},{
		"name" : "facingCentro",
		"index":"facingCentro", 
		"fixed":true,
		"width" : 70,
		"editable": true,
		"edittype":"text",
        "editoptions":{
        	"autocomplete": "off",
            "size":"7",
            "maxlength":"3",
            "dataEvents": [
                                  //{//control para el click
                                  //    type: 'click', 
                                  //    fn: controlCellFacingCentro,                           
                                  //},
                                  {//control para el keypress
                                      type: 'keydown',
                                      fn: controlNavegacion,
                                  }// cierra el control del keypress
                              ]   
        	},
		"cellEdit" : true,
		"title" : false,
		"cellsubmit" : "clientArray"
	},{
		"name" : "multiplicadorFacing",
		"index":"multiplicadorFacing", 
		"formatter":formatNumberDecimales,
		"width" : 40
	},{
		"name" : "imagenComercialMin",
		"index":"imagenComercialMin", 
		"formatter":formatNumberDecimales,
		"width" : 30,
		 "hidden" : false
	},{
		"name" : "facingPrevio",
		"index":"facingPrevio", 
		"formatter":function(value, options, rData){ if (value != 0) {return value;} else {return '';}  },
		"width" : 54
	},/*{
		"name" : "temporada",
		"index":"temporada", 
		"width" : 43
	},{
		"name" : "anioColeccion",
		"index":"anioColeccion", 
		"width" : 43
	},{
		"name" : "modeloProveedor",
		"index":"modeloProveedor", 
		"width" : 43
	},{
		"name" : "talla",
		"index":"talla", 
		"width" : 43
	},{
		"name" : "color",
		"index":"color", 
		"width" : 43
	},{
		"name" : "lote",
		"index":"lote", 
		"width" : 43
	},{
		"name" : "tempColNumOrden",
		"index":"tempColNumOrden", 
		"width" : 43
	},*/{
		"name" : "flgNsr",
		"index":"flgNsr", 
		"width" : 30
	},{
		"name" : "mensaje",
		"index":"mensaje", 
		"formatter": imageFormatMessageFac,	
		"fixed":true,
		"width" : 35
	}			
	]; 
	this.sortIndex = null;
	//this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP26Fac"; 
	this.pagerNameJQuery = "#pagerP26Fac";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;
	 this.myColumnStateName = 'gridP26Fac.colState';
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
        var colModel = jQuery(grid.nameJQuery).jqGrid('getGridParam', 'colModel'); 
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
            window.localStorage.setItem(gridFac.myColumnStateName, JSON.stringify(columnsState));
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

function GridP26FacTextil (locale){
	// Atributos
	this.name = "gridP26FacTextil"; 
	this.nameJQuery = "#gridP26FacTextil"; 
	this.i18nJSON = './misumi/resources/p26SfmCapacidad/p26sfmCapacidad_' + locale + '.json';
	this.colNames = null;
	this.subgridColNames = null;
	this.cm = [
		{
			"name" : "nivelLote",
			"index":"nivelLote",
			"formatter": p26FormaterNivelLote,
		    "hidden" : true
	
	},{
		"name" : "descCodN1",
		"index":"descCodN1",
		"formatter": function(value, options, rData){ return rData['codN1'] + '-' +value  ;  },
		"sortable":false,
		"fixed":true,
		"resizable":false,
	    "hidden" : true
	},{
		"name" : "descCodN2",
		"index":"descCodN2", 
		"formatter": function(value, options, rData){ return rData['codN2'] + '-' +value  ;  },
		"sortable":false,
		"fixed":true,
		"resizable":false,
	    "hidden" : true 
	},{
		"name" : "descCodN3",
		"index":"descCodN3", 
		"formatter": function(value, options, rData){ return rData['codN3'] + '-' +value  ;  },
		"sortable":false,
		"fixed":true,
		"resizable":false,
	    "hidden" : true 
	},{
		"name" : "descCodN4",
		"index":"descCodN4", 
		"formatter": function(value, options, rData){ return rData['codN4'] + '-' +value  ;  },
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"hidden" : true 
	},{
		"name" : "descCodN5",
		"index":"descCodN5", 
		"formatter": function(value, options, rData){ return rData['codN5'] + '-' +value  ;  },
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"hidden" : true 
	},{
		"name" : "codArticulo",
		"index":"codArticulo", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 60
	},{
		"name" : "denomInforme",
		"index":"denomInforme", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 107					
	},{
		"name" : "ccEstr",
		"index":"ccEstr", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 30
	},{
		"name" : "cc",
		"index":"cc", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 25
	},{
		"name" : "pedible",
		"index":"pedible", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 25
	},{                                                                                                                    
		"name" : "pedir",
		"index":"pedir", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 25
	},{                                                                                                                    
		"name" : "diasStock",
		"index":"stock",
		"formatter":formatNumberDecimales,
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 30
	},{                                                                                                                    
		"name" : "stock",
		"index":"stock",
		"formatter":formatNumberDecimales,
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 25
	},{
		"name" : "tipoAprov",
		"index":"tipoAprov", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 25
	},{                                                                                                                    
		"name" : "uc",
		"index":"uc", 
		"formatter":formatNumberDecimales,
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 25
	},{
		"name" : "facingCentro",
		"index":"facingCentro", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 70,
		"editable": true,
		"edittype":"text",
        "editoptions":{
        	"autocomplete": "off",
            "size":"7",
            "maxlength":"3",
            "dataEvents": [
                                  //{//control para el click
                                  //    type: 'click', 
                                  //    fn: controlCellFacingCentro,                           
                                  //},
                                  {//control para el keypress
                                      type: 'keydown',
                                      fn: controlNavegacionFacingLote,
                                    
                                  }// cierra el control del keypress
                                //  ,
                                //  {//control para el keypress
                                //      type: 'focus',
                                //      fn: abrirSubgrid,
                                //  }// cierra el control del keypress
                                  ,
                                  {//control para el keypress
                                      type: 'keyup',
                                      fn: controlCampoFacingTextil,
                                  }// cierra el control del keypress
                              ]   
        	},
		"cellEdit" : true,
		"title" : false,
		"cellsubmit" : "clientArray"
	},{
		"name" : "multiplicadorFacing",
		"index":"multiplicadorFacing", 
		"formatter":formatNumberDecimales,
		"fixed":true,
		"resizable":false,
		"width" : 40
	},{
		"name" : "imagenComercialMin",
		"index":"imagenComercialMin", 
		"formatter":formatNumberDecimales,
		"fixed":true,
		"resizable":false,
		"width" : 30,
		 "hidden" : true
	},{
		"name" : "facingPrevio",
		"index":"facingPrevio", 
		"formatter":function(value, options, rData){ if (value != 0) {return value;} else {return '';}  },
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 40
	},{
		"name" : "temporada",
		"index":"temporada", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 25
	},{
		"name" : "anioColeccion",
		"index":"anioColeccion", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 30
	},{
		"name" : "modeloProveedor",
		"index":"modeloProveedor", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 60
	},{
		"name" : "talla",
		"index":"talla", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 30
	},{
		"name" : "color",
		"index":"color", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 35
	},{
		"name" : "tempColNumOrden",
		"index":"tempColNumOrden", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 55
	},{
		"name" : "flgNsr",
		"index":"flgNsr", 
		"fixed":true,
		"resizable":false,
		"width" : 20
	},{
		"name" : "mensaje",
		"index":"mensaje", 
		"formatter": imageFormatMessageFac,	
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 30
	}			
	]; 
	this.subgridColNames = null;
	this.subgridCm = [	
		{
			"name" : "nivelLote",
			"index":"nivelLote",
			"formatter": p26FormaterNivelLote,
			"sortable":false,
			"fixed":true,
			"resizable":false,
		    "hidden" : true
		
		}
		,
	   {
		"name" : "descCodN1",
		"index":"descCodN1",
		"formatter": function(value, options, rData){ return rData['codN1'] + '-' +value  ;  },
		"sortable":false,
		"fixed":true,
		"resizable":false,
	    "hidden" : true
	},{
		"name" : "descCodN2",
		"index":"descCodN2", 
		"formatter": function(value, options, rData){ return rData['codN2'] + '-' +value  ;  },
		"sortable":false,
		"fixed":true,
		"resizable":false,
	    "hidden" : true 
	},{
		"name" : "descCodN3",
		"index":"descCodN3", 
		"formatter": function(value, options, rData){ return rData['codN3'] + '-' +value  ;  },
		"sortable":false,
		"fixed":true,
		"resizable":false,
	    "hidden" : true 
	},{
		"name" : "descCodN4",
		"index":"descCodN4", 
		"formatter": function(value, options, rData){ return rData['codN4'] + '-' +value  ;  },
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"hidden" : true 
	},{
		"name" : "descCodN5",
		"index":"descCodN5", 
		"formatter": function(value, options, rData){ return rData['codN5'] + '-' +value  ;  },
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"hidden" : true 
	},{
		"name" : "codArticulo",
		"index":"codArticulo", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 58
	},{
		"name" : "denomInforme",
		"index":"denomInforme", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"formatter":p26FormateoABlanco,
		"width" : 107					
	},{
		"name" : "ccEstr",
		"index":"ccEstr", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"formatter":p26FormateoABlanco,
		"width" : 30
	},{
		"name" : "cc",
		"index":"cc", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"formatter":p26FormateoABlanco,
		"width" : 25
	},{
		"name" : "pedible",
		"index":"pedible", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"formatter":p26FormateoABlanco,
		"width" : 25
	},{                                                                                                                    
		"name" : "pedir",
		"index":"pedir", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"formatter":p26FormateoABlanco,
		"width" : 25
	},{                                                                                                                    
		"name" : "diasStock",
		"index":"stock",
		"formatter":formatNumberDecimales,
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 30
	},{                                                                                                                    
		"name" : "stock",
		"index":"stock",
		"formatter":formatNumberDecimales,
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 25
	},{
		"name" : "tipoAprov",
		"index":"tipoAprov", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"formatter":p26FormateoABlanco,
		"width" : 25
	},{                                                                                                                    
		"name" : "uc",
		"index":"uc", 
		"formatter":formatNumberDecimales,
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 25
	},{
		"name" : "facingCentro",
		"index":"facingCentro", 
		//"formatter":function(value, options, rData){ if (value != 0) {return value;} else {return '';}  },
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 70,
		"editable": true,
		"edittype":"text",
        "editoptions":{
        	"autocomplete": "off",
            "size":"7",
            "maxlength":"3"  
        	},
		"cellEdit" : true,
		"cellsubmit" : "clientArray"
		
	},{
		"name" : "multiplicadorFacing",
		"index":"multiplicadorFacing", 
		"formatter":formatNumberDecimales,
		"fixed":true,
		"resizable":false,
		"width" : 40
	},{
		"name" : "imagenComercialMin",
		"index":"imagenComercialMin", 
		"formatter":formatNumberDecimales,
		"fixed":true,
		"resizable":false,
		"width" : 30,
		 "hidden" : true
	},{
		"name" : "facingPrevio",
		"index":"facingPrevio", 
		"formatter":function(value, options, rData){ if (value != 0) {return value;} else {return '';}  },
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 40
	},{
		"name" : "temporada",
		"index":"temporada", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"formatter":p26FormateoABlanco,
		"width" : 25
	},{
		"name" : "anioColeccion",
		"index":"anioColeccion", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"formatter":p26FormateoABlanco,
		"width" : 30
	},{
		"name" : "modeloProveedor",
		"index":"modeloProveedor", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"formatter":p26FormateoABlanco,
		"width" : 60
	},{
		"name" : "talla",
		"index":"talla", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 30
	},{
		"name" : "color",
		"index":"color", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 35
	},{
		"name" : "tempColNumOrden",
		"index":"tempColNumOrden", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"formatter":p26FormateoABlanco,
		"width" : 55
	},{
		"name" : "flgNsr",
		"index":"flgNsr", 
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 20
	},{
		"name" : "mensaje",
		"index":"mensaje", 
		"formatter": imageFormatMessageFac,	
		"sortable":false,
		"fixed":true,
		"resizable":false,
		"width" : 29
	}			
	]; 

	this.sortIndex = null;
	//this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP26FacTextil"; 
	this.pagerNameJQuery = "#pagerP26FacTextil";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;
	 this.myColumnStateName = 'gridP26FacTextil.colState';
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
        var colModel = jQuery(grid.nameJQuery).jqGrid('getGridParam', 'colModel'); 
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
            window.localStorage.setItem(gridFacTextil.myColumnStateName, JSON.stringify(columnsState));
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


function p26FormateoABlanco(cellValue, opts, rData) {
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
		$("#p26_cmb_area").combobox(null);
		$("select#p26_cmb_area").html("<option value='' selected='selected'></option>");
		$("#p26_cmb_area").combobox('autocomplete',"");
		$("#p26_cmb_area").combobox('comboautocomplete',null);
		$("#p26_cmb_seccion").combobox(null);
		$("#p26_cmb_subcategoria").combobox(null);
		$("#p26_cmb_categoria").combobox(null);
		$("#p26_cmb_segmento").combobox(null);
		if ($("#centerId").val()!=null && $("#centerId").val()!=''){
			load_cmbArea();
		}
		if ($("#p01_txt_flgFacing").val() == 'N'){
			$("#p26_cmb_pedir").combobox('autocomplete',optionActiva);
			$("#p26_cmb_pedir").combobox('comboautocomplete','S');
		}else{
			$("#p26_cmb_pedir").combobox('autocomplete',optionActivaTodos);
			$("#p26_cmb_pedir").combobox('comboautocomplete','null');
		}
		$("#p26_txt_flgFacing").val($("#p01_txt_flgFacing").val());
	}
	if (componentName == AREA_CONST || componentName == RESET_CONST){
		$("select#p26_cmb_seccion").html("<option value='' selected='selected'></option>");
		$("#p26_cmb_seccion").combobox('autocomplete','');
		$("#p26_cmb_seccion").combobox('comboautocomplete',null);
	}
	if(componentName==SECTION_CONST || componentName==AREA_CONST || componentName == RESET_CONST){
		$("select#p26_cmb_categoria").html("<option value='' selected='selected'></option>");
		$("#p26_cmb_categoria").combobox('autocomplete','');
		$("#p26_cmb_categoria").combobox('comboautocomplete',null);
	}
	if(componentName==CATEGORY_CONST || componentName==SECTION_CONST || componentName==AREA_CONST || componentName == RESET_CONST){
		$("select#p26_cmb_subcategoria").html("<option value='' selected='selected'></option>");
		$("#p26_cmb_subcategoria").combobox('autocomplete','');
		$("#p26_cmb_subcategoria").combobox('comboautocomplete',null);
	}
	if(componentName==SUBCATEGORY_CONST || componentName==CATEGORY_CONST || componentName==SECTION_CONST || componentName==AREA_CONST || componentName == RESET_CONST){
		$("select#p26_cmb_segmento").html("<option value='' selected='selected'></option>");
		$("#p26_cmb_segmento").combobox('autocomplete','');
		$("#p26_cmb_segmento").combobox('comboautocomplete',null);
	}
	//Para activar o desactivar el combo Activa
	//Si es facing    -> activar combo Activa
	//Si no es facing -> desactivar combo Activa
	


	if ($("#p26_txt_flgFacing").val() == 'N'){
		$("#p26_cmb_pedir").combobox('autocomplete',optionActiva);
		$("#p26_cmb_pedir").combobox('comboautocomplete','S');
		$("#p26_cmb_pedir").combobox("disable");
	}else if ($("#p26_txt_flgFacing").val() == 'S' &&  $("#p26_txt_flgFacingCapacidad").val() == 'S'){
		$("#p26_cmb_pedir").combobox('autocomplete',optionActiva);
		$("#p26_cmb_pedir").combobox('comboautocomplete','S');
		$("#p26_cmb_pedir").combobox("disable");
	} else {
		$("#p26_cmb_pedir").combobox('autocomplete',optionActivaTodos);
		$("#p26_cmb_pedir").combobox('comboautocomplete','null');
		$("#p26_cmb_pedir").combobox("enable");
	}
	
	

	//Mostramos el combo de Lote S/N solo si
	//el area seleccionado es 3-TEXTIL
	var valor = $("#p26_cmb_area").val().split('*');
	var cod = valor[0];
	if (cod=="3" ){
		$("#p26_div_loteSN").show();
		
	} else{
 		$("#p26_div_loteSN").hide();
 		$("#p26_cmb_loteSN").combobox('autocomplete',optionActivaTodos);
 		$("#p26_cmb_loteSN").combobox('comboautocomplete',"null");
	}
	
	
	disableFilterSelection(componentName);
	return true;
}

function p26ControlesPantallaTextil(){
	
	
	//Nos recorremos los registros existentes en pantalla.
	var rowsInPage = jQuery(gridFacTextil.nameJQuery).jqGrid('getGridParam', 'rowNum');
	var existenRefConHijas = 0;
	
	for (i = 0; i < rowsInPage; i++){
		var idToDataIndex = $(gridFacTextil.nameJQuery).jqGrid('getDataIDs');
		//Después busco dentro del array de rowId's el correspondiente a la fila
		var idFila = idToDataIndex[i];

		nivel = $("#"+(idFila)+"_nivelLote").val();
		
		//Si el nivel es 0 oculto el signo +
		if (nivel == '0'){
			
			$("#gridP26FacTextil #"+idFila+" td.sgcollapsed",gridFacTextil[0]).unbind('click').html('');
			
		} else {
			existenRefConHijas = 1;
		}
	}
	
	/*
	if (existenRefConHijas == 1) {
		$("#jqgh_gridP12Textil_subgrid").html("<span id ='iconPlusExpandir' class='ui-icon ui-icon-plus'></span>");
		$("#iconPlusExpandir").click(function() {
			expandirLotes();
		});
		existenRefConHijas = 0;
	} else {
		$("#jqgh_gridP12Textil_subgrid").html("");
		
	} 
	*/
	
}

function controlTipoListado(valueComboBox){
	if( valueComboBox!="null" && valueComboBox!=null){
		var valor = valueComboBox.split('*');
		$("#p26_txt_flgStock").val(valor[1]);
		$("#p26_txt_flgCapacidad").val(valor[2]);
		$("#p26_txt_flgFacing").val(valor[3]);
		$("#p26_txt_flgFacingCapacidad").val(valor[4]);
	}
}

function disableFilterSelection(componentName){
	if (componentName == RESET_CONST){
		if ($("#centerId").val()!=null && $("#centerId").val()!=''){
			$("#p26_cmb_area").combobox("enable");
			if ($("#p01_txt_flgFacing").val() != 'N'){
				$("#p26_cmb_pedir").combobox("enable");
			}
		}else{
			$("#p26_cmb_area").combobox("disable");
			$("#p26_cmb_pedir").combobox("disable");
		}
		$("#p26_cmb_seccion").combobox("disable");
		$("#p26_cmb_categoria").combobox("disable");
		$("#p26_cmb_subcategoria").combobox("disable");
		$("#p26_cmb_segmento").combobox("disable");
	}else if (componentName == AREA_CONST){
		$("#p26_cmb_seccion").combobox("enable");
		$("#p26_cmb_categoria").combobox("disable");
		$("#p26_cmb_subcategoria").combobox("disable");
		$("#p26_cmb_segmento").combobox("disable");
	} else	if(componentName==SECTION_CONST ){
		$("#p26_cmb_categoria").combobox("enable");
		$("#p26_cmb_subcategoria").combobox("disable");
		$("#p26_cmb_segmento").combobox("disable");
	}
	if(componentName==CATEGORY_CONST ){
		$("#p26_cmb_subcategoria").combobox("enable");
		$("#p26_cmb_segmento").combobox("disable");
	}
	if(componentName==SUBCATEGORY_CONST){
		$("#p26_cmb_segmento").combobox("enable");
	}
}

function mine(value, options, rData){ if (value==0){return '';}else{return value;}}

function formatDescripcionCombo(codigo, descripcion){
	return codigo + "-" + descripcion;
}
function imageFormatMessageCap(cellValue, opts, rData) {
	
	var imagen = "";
	var mostrarModificado = "none;";
	var mostrarGuardado = "none;";
	var mostrarError = "none;";
	var descError = "";
	
	//Controlamos los posibles errores que me lleguen para pintar el icono correspondiente.
	if (parseFloat(rData['codError']) == '8')
	{
		//Pintamos el icono de que se ha guardado
		mostrarGuardado = "block;";
	}
	else if (parseFloat(rData['codError']) == '9')
	{
		//Pintamos el icono de que se ha modificado
		addSeleccionados(numRow);
		mostrarModificado = "block;";
	}
	else if (parseFloat(rData['codError']) == '1')
	{
		//Pintamos el icono de que ha ocurrido un error
		descError = replaceSpecialCharacters(errorActualizacion);
		mostrarError = "block;";
	}	
	else if (parseFloat(rData['codError']) == '2')
	{
		//Pintamos el icono de que ha ocurrido un error
		descError = replaceSpecialCharacters(errorActualizacion);
		mostrarError = "block;";
	}
	else if (parseFloat(rData['codError']) == '3')
	{
		//Pintamos el icono de que ha ocurrido un error
		descError = replaceSpecialCharacters(errorActualizacion);
		mostrarError = "block;";
	}
	else if (parseFloat(rData['codError']) == '4')
	{
		//Pintamos el icono de que ha ocurrido un error
		descError = replaceSpecialCharacters(errorActualizacionOrig);
		mostrarError = "block;";
	} else if (null != rData['codError'] && parseFloat(rData['codError']) != '0'){
		descError = rData['descError'];
		mostrarError = "block;";
	}
	
	imagen = "<div id='"+numRow+"_cap_divGuardado' align='center' style='display: "+ mostrarGuardado + "'><img id='"+numRow+"_cap_imgGuardado' src='./misumi/images/floppy.png' title='"+iconoGuardado+"'/></div>"; //Guardado
	imagen += "<div id='"+numRow+"_cap_divModificado' align='center' style='display: "+ mostrarModificado + "'><img id='"+numRow+"_cap_imgModificado' src='./misumi/images/modificado.png' title='"+iconoModificado+"'/></div>"; //Modificado
	imagen += "<div id='"+numRow+"_cap_divError' align='center' style='display: " + mostrarError + "'><img id='"+numRow+"_cap_imgError' src='./misumi/images/dialog-error-24.png' title='"+descError+"'/></div>"; //Error

	//Añadimos también los valores de la columna capacidad ocultos para poder utilizarlos posteriormente.
	var datoCapacidadOrig = "<input type='hidden' id='"+numRow+"_capacidad_orig' value='"+rData['capacidadOrig']+"'>";
	imagen +=  datoCapacidadOrig;
	
	var datoCapacidad = "<input type='hidden' id='"+numRow+"_capacidad_tmp' value='"+rData['capacidadOrig']+"'>";
	imagen +=  datoCapacidad;
	
	//Añadimos también el índice de la fila en la que estamos oculta para poder utilizarla posteriormente.
	var datoIndice = "<input type='hidden' id='"+numRow+"_cap_indice' value='"+rData['indice']+"'>";
	imagen +=  datoIndice;
	
	//Añadimos también el valor del codError de cada registro.
	var datoError = "<input type='hidden' id='"+numRow+"_cap_codError_orig' value='"+rData['codError']+"'>";
	imagen +=  datoError;
	
	numRow++;
	return imagen;
}

function limitesFormatNumber(cellValue, opts, rData) {
	
	//Actualizamos el formateo del campo ventaMedia
	return $.formatNumber(cellValue,{format:"0.000",locale:"es"});
}

function limiteInferiorSFMFormatNumber(cellValue, opts, rData) {
	
	//Actualizamos el formateo del campo lmin
	if (rData['porcionConsumidor'] == 'S'){
		// Si Porcion consumidor es igual a S, el campo Limite Superior debe mostrarse si decimales

		return $.formatNumber(Math.floor(cellValue),{format:"0",locale:"es"});
	} else {
		return $.formatNumber(cellValue,{format:"0.000",locale:"es"});
	}
	
}

function formatNumberDecimales(cellValue, opts, rData) {
	
	return $.formatNumber(Math.floor(cellValue),{format:"0.##",locale:"es"});

}

function limiteSuperiorSFMFormatNumber(cellValue, opts, rData) {

//	//Si la el limite inferior es superior al limite superior, hay que actuar sobre lo que se visualiza en la columna "Limite Superior". Cuando se de
//	// esta situación se optara por dos opciones segun la diferencia entre la fecha actual y la fecha de creación
//	if (rData['lmin'] > rData['lsf'] && rData['fechaSfmDDMMYYYY']!=null && rData['fechaSfmDDMMYYYY']!="") {
//
		//Formateamos la fechaSFM a date
		//var fechaSfm =  new Date(rData['fechaSfmDDMMYYYY'].substring(4), rData['fechaSfmDDMMYYYY'].substring(2,4), rData['fechaSfmDDMMYYYY'].substring(0,2)).getTime();
		var fechaSfm =  devuelveDate(rData['fechaSfmDDMMYYYY'].substring(4) +"-"+ rData['fechaSfmDDMMYYYY'].substring(2,4)  +"-"+ rData['fechaSfmDDMMYYYY'].substring(0,2)).getTime();
		
		//Obtenemos la fecha actual
		var fechaActual = new Date().getTime();
		
		//Obtenemos  la diferencia entre las dos fechas 
		var c = 24*60*60*1000;
		var diffDays = Math.floor((fechaActual - fechaSfm)/(c));
		
		if (diffDays <= 21) {
			//Si la diferencia entre fechas es menor o igual que 21, entonces el limite superior se pondra a ""
			return "";
		}

//	} else {
//		//Actualizamos el formateo del campo lmin y lsf
//		if (rData['porcionConsumidor'] == 'S'){
//			// Si Porcion consumidor es igual a S, el campo Limite Superior debe mostrarse si decimales
//			return $.formatNumber(Math.floor(cellValue),{format:"0",locale:"es"});
//		} else {
//			return $.formatNumber(cellValue,{format:"0.000",locale:"es"});
//		}
//	}
	
	//Actualizamos el formateo del campo lmin y lsf
	if (rData['porcionConsumidor'] == 'S'){
		// Si Porcion consumidor es igual a S, el campo Limite Superior debe mostrarse si decimales
		return $.formatNumber(Math.floor(cellValue),{format:"0",locale:"es"});
	} else {
		return $.formatNumber(cellValue,{format:"0.000",locale:"es"});
	}
		
}


function p26FormatFlgNueva(cellValue, opts, rData){
	var flgNueva = "";
	if (cellValue == "S"){
		flgNueva = '<span class="p26_flgNueva">NUEVA</span>';
	}
	return flgNueva;
}

function p26FormatStock(cellValue, opts, rData){
	
	
	var stockFormateado =  $.formatNumber(cellValue,{format:"0.##",locale:"es"});

	if (rData['flgPintarLinkTengoPoco'] == 'S'){
		 
		//Referencia con poco stock, el stock saldra en rojo con una barra baja y al pinchar se mostraran los motivos de TENGO POCO.
		var codLoc= rData['codLoc'];
		var codArticulo= rData['codArticulo'];
		var stock= rData['stock'];
		var ventaMedia= rData['ventaMedia'];	
		var stockBajo= rData['stockBajo'];
		var sobreStockInferior = stockBajo;
		
		//En el onclick, ademas de la funcion javascript que abre la ventana popup, se añade un return false; 
		//esta acción provoca que el scroll no suba para arriba y se mantenga donde estaba. 
		return "<div id='"+opts.rowId+"_divStock'><a href='#' id='"+opts.rowId+"_p26_stock' class='p26_pocoStockLink' onclick='javascript:p26MotivosTengoPoco("+ codLoc + "," + codArticulo + "," + stockBajo + "," + sobreStockInferior + "," + stock + ");return false;' title='"+stock+"'>"+stockFormateado+"</a></div>"; 
	
	} else {
		if (rData['flgPintarTengoPoco'] == 'S'){
			return "<div id='"+opts.rowId+"_divStock' class='p26_pocoStock'>"+stockFormateado+"</div>"; 
		}
		else{
			return stockFormateado;
		}
	}
	

}



function p26FormatUC(cellValue, opts, rData){
	
	var ucFormateado =  $.formatNumber(cellValue,{format:"0.##",locale:"es"});
	
	
	//Si el FLG_UFP = S, entonces el valor UC tiene que aparecer en negrita.
	//Cambio petición 53653: hasta ahora se miraba la sección y el FLG_UFP
	//para saber si el valor UC tenía que aparecer en negrita. A partir de la
	//petición 53653 sólo hay que mirar el FLG_UFP. 
	if (rData['flgUfp'] == 'S'){ 
		// Pitamos el campo UC en negrita
		return '<span style="font-size: 11px; font-weight: bold;">'+ucFormateado+'</span>';
		
	} else {
	
		return ucFormateado;

	}	

}



function p26MotivosTengoPoco(codLoc,codArticulo,stockBajo,sobreStockInferior,stock){

	reloadPopupP31(codLoc, 'P', codArticulo, stockBajo, sobreStockInferior, "", stock);

}

function p26FormaterNivelLote(cellValue, opts, rData) {

	//Añadimos los valores de la columna referencia ocultos para poder utilizarlos posteriormente.
	var datoNivelLote= "<input type='hidden' id='"+opts.rowId+"_nivelLote' value='"+rData['nivelLote']+"'>";
	
	return datoNivelLote;
}

function imageFormatMessageSfm(cellValue, opts, rData) {
	
	var imagen = "";
	var mostrarModificado = "none;";
	var mostrarGuardado = "none;";
	var mostrarError = "none;";
	var descError = "";
	
	//Controlamos los posibles errores que me lleguen para pintar el icono correspondiente.
	if (parseFloat(rData['codError']) == '8')
	{
		//Pintamos el icono de que se ha guardado
		mostrarGuardado = "block;";
	}
	else if (parseFloat(rData['codError']) == '9')
	{
		//Pintamos el icono de que se ha modificado
		mostrarModificado = "block;";
		addSeleccionados(numRow);
	}
	else if (parseFloat(rData['codError']) == '1')
	{
		//Pintamos el icono de que ha ocurrido un error
		descError = replaceSpecialCharacters(SFMmenorLimInf);
		mostrarError = "block;";
	}	
	else if (parseFloat(rData['codError']) == '2')
	{
		//Pintamos el icono de que ha ocurrido un error
		descError = replaceSpecialCharacters(SFMmayorLimSup);
		mostrarError = "block;";
	}
	else if (parseFloat(rData['codError']) == '3')
	{
		//Pintamos el icono de que ha ocurrido un error
		descError = replaceSpecialCharacters(errorActualizacion);
		mostrarError = "block;";
	}
	else if (parseFloat(rData['codError']) == '4')
	{
		//Pintamos el icono de que ha ocurrido un error
		descError = replaceSpecialCharacters(errorActualizacionOrig);
		mostrarError = "block;";
	}
	 else if (null != rData['codError'] && parseFloat(rData['codError']) != '0'){
		descError = rData['descError'];
		mostrarError = "block;";
	}

	
	imagen = "<div id='"+numRow+"_sfm_divGuardado' align='center' style='display: "+ mostrarGuardado + "'><img id='"+numRow+"_sfm_imgGuardado' src='./misumi/images/floppy.png' title='"+iconoGuardado+"'/></div>"; //Guardado
	imagen += "<div id='"+numRow+"_sfm_divModificado' align='center' style='display: "+ mostrarModificado + "'><img id='"+numRow+"_sfm_imgModificado' src='./misumi/images/modificado.png' title='"+iconoModificado+"'/></div>"; //Modificado
	imagen += "<div id='"+numRow+"_sfm_divError' align='center' style='display: " + mostrarError + "'><img id='"+numRow+"_sfm_imgError' src='./misumi/images/dialog-error-24.png' title='"+descError+"'/></div>"; //Error

	
	//Añadimos los valores de la columna límite inferior ocultos para poder utilizarlos posteriormente.
	var datoLmin = "<input type='hidden' id='"+numRow+"_lmin' value='"+rData['lmin']+"'>";
	imagen +=  datoLmin;
	
	
	//Si el limite inferior es superior al limite superior, hay que actuar sobre lo que se visualiza en la columna "Limite Superior". Cuando se de
	// esta situación se optara por dosopciones segun la diferencia entre la fecha actual y la fecha de creación del registro(SFM)
	if (rData['lmin'] > rData['lsf']){

		//Formateamos la fechaSFM a date
		var fechaSfm =  new Date(rData['fechaSfmDDMMYYYY'].substring(4), rData['fechaSfmDDMMYYYY'].substring(2,4), rData['fechaSfmDDMMYYYY'].substring(0,2));
		
		//Obtenemos la fecha actual
		var fechaActual = new Date();
		
		//Obtenemos  la diferencia entre las dos fechas 
		var c = 24*60*60*1000;
		var diffDays = Math.round((fechaActual - fechaSfm)/(c));
		
		if (diffDays <= 21) {
			//Si la diferencia entre fechas es menor o igual que 21, entonces el limite superior se pondra a ""
			//Añadimos también los valores de la columna l?mite superior ocultos para poder utilizarlos posteriormente.
			var datoLsup = "<input type='hidden' id='"+numRow+"_lsf' value=''>";
		}else {
			//Si la diferencia entre fechas es mayor que 21, entonces el limite superior se igualara al limite inferior
			//Añadimos también los valores de la columna l?mite superior ocultos para poder utilizarlos posteriormente.
			var datoLsup = "<input type='hidden' id='"+numRow+"_lsf' value='"+rData['lmin']+"'>";
		}
	
	} else {

		//Añadimos también los valores de la columna l?mite superior ocultos para poder utilizarlos posteriormente.
		var datoLsup = "<input type='hidden' id='"+numRow+"_lsf' value='"+rData['lsf']+"'>";
		
	}
	imagen +=  datoLsup;
	
	
	
	//Añadimos también los valores de la columna sfm ocultos para poder utilizarlos posteriormente.
	var datoSfmOrig = "<input type='hidden' id='"+numRow+"_sfm_orig' value='"+rData['sfmOrig']+"'>";
	imagen +=  datoSfmOrig;
	var datoSfm = "<input type='hidden' id='"+numRow+"_sfm_tmp' value='"+rData['sfmOrig']+"'>";
	imagen +=  datoSfm;
	
	//Añadimos también los valores de la columna cobertura sfm ocultos para poder utilizarlos posteriormente.
	var datoCoberturaSfmOrig = "<input type='hidden' id='"+numRow+"_coberturaSfm_orig' value='"+rData['coberturaSfmOrig']+"'>";
	imagen +=  datoCoberturaSfmOrig;
	var datoCoberturaSfm = "<input type='hidden' id='"+numRow+"_coberturaSfm_tmp' value='"+rData['coberturaSfmOrig']+"'>";
	imagen +=  datoCoberturaSfm;
	
	//Añadimos también el índice de la fila en la que estamos oculta para poder utilizarla posteriormente.
	var datoIndice = "<input type='hidden' id='"+numRow+"_sfm_indice' value='"+rData['indice']+"'>";
	imagen +=  datoIndice;
	
	//Añadimos también el valor del flag de cada registro.
	var datoFlag = "<input type='hidden' id='"+numRow+"_flgFecsfm' value='"+rData['flgFecsfm']+"'>";
	imagen +=  datoFlag;
	
	//Añadimos también el valor del codError de cada registro.
	var datoError = "<input type='hidden' id='"+numRow+"_sfm_codError_orig' value='"+rData['codError']+"'>";
	imagen +=  datoError;
	
	//Añadimos los valores de la columna venta media ocultos para poder utilizarlos posteriormente.
	var datoVentaMedia = "<input type='hidden' id='"+numRow+"_ventaMedia' value='"+rData['ventaMedia']+"'>";
	imagen +=  datoVentaMedia;
	
	//Añadimos los valores de la columna fechaSfm (Fecha de creacion) ocultos para poder utilizarlos posteriormente.
	var datoFechaSfm = "<input type='hidden' id='"+numRow+"_fechaSfm' value='"+rData['fechaSfmDDMMYYYY']+"'>";
	imagen +=  datoFechaSfm;
	
	//Añadimos los valores de la columna porcion consumidor ocultos para poder utilizarlos posteriormente.
	var porcionConsumidor = "<input type='hidden' id='"+numRow+"_porcionConsumidor' value='"+rData['porcionConsumidor']+"'>";
	imagen +=  porcionConsumidor;
	
	numRow++;
	return imagen;
}

function controlCampoFacingTextil (e) {
	
	
	
	var idActual = e.target.id;
	//Obtención de fila y columna actuales
	var fila = idActual.substring(0, idActual.indexOf("_"));
	var nombreColumna = idActual.substring(idActual.indexOf("_")+1);
	
	//var idFoco;
	var key = e.which; //para soporte de todos los navegadores

	if (!((key == 13) || (key == 37) || (key == 38) || (key == 39) || (key == 40) || (key == 8))) {
		//Tecla enter, guardado, Tecla Izquierda, 
		//Tecla arriba, Tecla derecha, Tecla Abajo. Se llama al control de Navegavion
		
		flgActualizarFacing = 'S';
				
		validacionNavegacion = validacionFacing(fila + "_facingCentro", 'S');

		if(validacionNavegacion == 'S') {
			
			if ($("#gridP26FacTextil #"+fila+" td.sgcollapsed .ui-icon-plus").is(':visible')) {
				
				//if ($("#gridP26FacTextil [aria-describedby='gridP26FacTextil_nivelLote'] #"+ fila + "_nivelLote").val() != 0) {
					$("#gridP26FacTextil #"+fila+" td.sgcollapsed",gridFacTextil[0]).click();
				
				//}	
	
			} else {
				
				if ($("#gridP26FacTextil [aria-describedby='gridP26FacTextil_nivelLote'] #"+ fila + "_nivelLote").val() != 0) {
					var subgrid = "gridP26FacTextil_" + fila; 
					reloadDataP26Subgrid(fila, subgrid);
				}
				
				
			}
		}

	  }
	
	
}

function abrirSubgrid(e) {
	
	var idActual = e.target.id;
	var idFoco;
	//Obtención de fila y columna actuales
	var fila = idActual.substring(0, idActual.indexOf("_"));
	var nombreColumna = idActual.substring(idActual.indexOf("_")+1);
	var validacionNavegacion = 'S';
	
	idFoco = fila + "_" + nombreColumna;

	var key = e.which; //para soporte de todos los navegadores

	//Si es textil, ademas debemos mirar si el si el nivel de la referencia es mayor que 0, si es asi debemos abrir el subgrid.
	//if ($(gridFacTextil.nameJQuery).is(':visible')) {
	
	if ($("#gridP26FacTextil #"+fila+" td.sgcollapsed .ui-icon-plus").is(':visible')) {
		
		if (flgDespliegueSubgrid) {
			
			var idToDataIndex = $(gridFacTextil.nameJQuery).jqGrid('getDataIDs');
			var nivelLote = $("#gridP26FacTextil [aria-describedby='gridP26FacTextil_nivelLote'] #"+ fila + "_nivelLote").val();	
			
			if (nivelLote != '0') {
				$("#gridP26FacTextil #"+fila+" td.sgcollapsed",gridFacTextil[0]).click();
			//	$("#"+idFoco).focus();
			}	
		}
	}
	//}
    	
    	
}


function controlCampoFacingTextilSubgrid (e) {

	
	
	var idActual = e.target.id;

	 
	//Obtención de fila y columna actuales
	var fila = idActual.substring(0, idActual.indexOf("_"));
	var nombreColumna = idActual.substring(idActual.indexOf("_")+1);
	

	
	//var idFoco;
	//idFoco = fila + "_" + nombreColumna;
	//var key = e.which; //para soporte de todos los navegadores

	var subgrid  = $(e.target).closest( ".ui-jqgrid-btable" ).attr('id');
	var facingActual = $("#" + subgrid + " #"+fila+"_facingCentro").val();
	var facingAnterior = $("#" + subgrid + " #"+fila+"_facingCentro_tmp").val();
	
	
	
	if (facingActual != facingAnterior) {	
		
		actualizarFacingSubgrid(fila, subgrid, e);
	}// else {
		//controlNavegacionSubgrid(e);
	//}


	
}



function controlNavegacion(e,origen) {
	

	var idActual = e.target.id;
	var idFoco;
	//Obtención de fila y columna actuales
	var fila = idActual.substring(0, idActual.indexOf("_"));
	var nombreColumna = idActual.substring(idActual.indexOf("_")+1);
	var validacionNavegacion = 'S';
	
	
	var key = e.which; //para soporte de todos los navegadores
 	if (key == 13){//Tecla enter, guardado
 		//e.preventDefault();
		finder();
    }
    //Flechas de cursores para navegación
    if (key == 37){//Tecla izquierda
    	e.preventDefault();
    	
    	if(nombreColumna == "coberturaSfm"){
        	idFoco = fila + "_sfm";
        	validacionNavegacion = validacionCoberturaSfm(fila + "_coberturaSfm", 'N');
        	if (validacionNavegacion=='S'){
	        	$("#"+idFoco).focus();
	        	$("#"+idFoco).select();
	        	$("#p26_fld_SFM_Selecc").val(idFoco);
        	}else{
        		$("#p26_btn_buscar").focus();	        	
        	}
    	}
    	
    	if(nombreColumna == "facingCentro"){
    		validacionNavegacion = validacionFacing(fila + "_facingCentro", 'N');
    	
    		if (origen != 'H') {//No estamos en una hija, si estamos en una hija no debe hacer nada
	    		//Si estamos en textil, y pulsamos la fecha izquierda, al salir debemos cerrar el lote.
	    		if ($(gridFacTextil.nameJQuery).is(':visible')) {
	    			if ($("#gridP26FacTextil #"+fila+" td.sgcollapsed .ui-icon-plus").is(':visible')) {
		    			$("#gridP26FacTextil #"+fila+" td.sgcollapsed",gridFacTextil[0]).click();	
		    		}	
	    		}
    		}
    	}
	
    }
    if (key == 38){//Tecla arriba
    	e.preventDefault();
    	idFoco = (parseInt(fila,10)-1) + "_" + nombreColumna;
    	if(nombreColumna == "coberturaSfm"){
    		validacionNavegacion = validacionCoberturaSfm(fila + "_coberturaSfm", 'N');
    	}
    	if(nombreColumna == "sfm"){
    		validacionNavegacion = validacionSfm(fila + "_sfm", 'N');
    	}
    	if(nombreColumna == "capacidad"){
    		validacionNavegacion = validacionCapacidad(fila + "_capacidad", 'N');
    	}
    	if(nombreColumna == "facingCentro"){
    		validacionNavegacion = validacionFacing(fila + "_facingCentro", 'N');
	
    		/*
    		//Si estamos en textil, y navegamos entre referencias lote, al salir debemos cerrar el lote.
    		if ($(gridFacTextil.nameJQuery).is(':visible')) {
				var idToDataIndex = $(gridFacTextil.nameJQuery).jqGrid('getDataIDs');
	    		var nivelLote = $("#"+(fila)+"_nivelLote").val();
	    		if (nivelLote != '0') {
	    			$("#"+fila+" td.sgexpanded",gridFacTextil[0]).click();
	    	//		$("#"+fila+"_facingCentro").val('');	
	    		}	
    		}
    		*/
    	}
    	if (validacionNavegacion=='S'){
    		
    		if ($(gridFacTextil.nameJQuery).is(':visible')) { //Estamos en textil

	    		if (origen == 'L') {//Partimos de un lote
	    			
	    			if ($("#gridP26FacTextil #"+(parseInt(fila,10)-1)+" td.sgexpanded .ui-icon-minus").is(':visible')) {
	    				//El lote de arriba esta abierto, debe navegar a la ultima hija del lote de arriba 
	    				
	    	    		var nivelLote = $("#gridP26FacTextil [aria-describedby='gridP26FacTextil_nivelLote'] #"+(parseInt(fila,10)-1)+"_nivelLote").val();
	    	    
	    	    		if (nivelLote != '0') {	
	    	    			idFocoSubgrid = nivelLote + "_" + nombreColumna;  	    			
	    	    			var subgrid = "gridP26FacTextil_" + (parseInt(fila,10)-1) + "_t"; 
	    	    	    	$("#" + subgrid + " #"+idFocoSubgrid).focus();
	    	    	    	$("#" + subgrid + " #"+idFocoSubgrid).select();
	    	    		}	
	    				
	    			} else {
	    				//Debe navegar a una madre
	    				
		    			$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_facingCentro'] #"+idFoco).focus();
		    	    	$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_facingCentro'] #"+idFoco).select();
		    	    	$("#p26_fld_SFM_Selecc").val(idFoco);
	    				
	    			}
	    			
	    			
	    		} else { //origen igual a 'H', partimos de una hija
	    			
	    			
	    			var subgrid  = $(e.target).closest( ".ui-jqgrid-btable" ).attr('id');
	    			var valores = subgrid.split('_');
	    			var indiceLote = valores[1]; //obteniendo el indice del subgrid, sabemos en que lote estamos
	    			
	    			if (fila == 1) { //Esta en la primera hija, debe navegar a la madre/lote

    	    			idFoco = indiceLote + "_" + nombreColumna;
    	    			
    	    			$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_facingCentro'] #"+idFoco).focus();
		    	    	$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_facingCentro'] #"+idFoco).select();
		    	    	$("#p26_fld_SFM_Selecc").val(idFoco);

	    				
	    			} else { //No estamos en la primera hija, debemos navegar a la hija superior
	    				
	    				idFocoSubgrid = (parseInt(fila,10)-1) + "_" + nombreColumna;

	    		    	$("#" + subgrid + " #"+idFocoSubgrid).focus();
	    		    	$("#" + subgrid + " #"+idFocoSubgrid).select();
	    				
	    			}
	    			
	    		}

    			
    		} else {
    			
    			$("#"+idFoco).focus();
    	    	$("#"+idFoco).select();
    	    	$("#p26_fld_SFM_Selecc").val(idFoco);
    		}

    	}else{
    		$("#p26_btn_buscar").focus();   	
    	}
    	//if(nombreColumna == "facingCentro"){
    	//	controlCellFacingCentro(e);
    	//}
    }
    if (key == 39){//Tecla derecha
    	e.preventDefault();
    	if(nombreColumna == "sfm"){
        	idFoco = fila + "_coberturaSfm";
        	validacionNavegacion = validacionSfm(fila + "_sfm", 'N');
        	if (validacionNavegacion=='S'){
	        	$("#"+idFoco).focus();
	        	$("#"+idFoco).select();
	        	$("#p26_fld_SFM_Selecc").val(idFoco);
        	}else{
        		$("#p26_btn_buscar").focus();
        	}
    	}
    	
    	if(nombreColumna == "facingCentro"){
    		validacionNavegacion = validacionFacing(fila + "_facingCentro", 'N');
    		if (origen != 'H') {//No estamos en una hija, si estamos en una hija no debe hacer nada
	    		//Si estamos en textil, y pulsamos la fecha izquierda, al salir debemos cerrar el lote.
	    		if ($(gridFacTextil.nameJQuery).is(':visible')) {
	    			if ($("#gridP26FacTextil #"+fila+" td.sgexpanded .ui-icon-minus").is(':visible')) {
		    			$("#gridP26FacTextil #"+fila+" td.sgexpanded",gridFacTextil[0]).click();	
		    		}	
	    		}
    		}
    	}
    	
    	
    	
    }
    if (key == 40){//Tecla abajo
    	e.preventDefault();
    	idFoco = (parseInt(fila,10)+1) + "_" + nombreColumna;

    	
    	if(nombreColumna == "coberturaSfm"){
    		validacionNavegacion = validacionCoberturaSfm(fila + "_coberturaSfm", 'N');
    	}
    	if(nombreColumna == "sfm"){
    		validacionNavegacion = validacionSfm(fila + "_sfm", 'N');
    	}
    	if(nombreColumna == "capacidad"){
    		validacionNavegacion = validacionCapacidad(fila + "_capacidad", 'N');
    	}
    	if(nombreColumna == "facingCentro"){
    	
    		validacionNavegacion = validacionFacing(fila + "_facingCentro", 'N');
    		
    		/*
    		//Si estamos en textil, y navegamos entre referencias lote, al salir debemos cerrar el lote.
    		if ($(gridFacTextil.nameJQuery).is(':visible')) {
				var idToDataIndex = $(gridFacTextil.nameJQuery).jqGrid('getDataIDs');
	    		var nivelLote = $("#"+(fila)+"_nivelLote").val();
	    		if (nivelLote != '0') {
	    			$("#"+fila+" td.sgexpanded",gridFacTextil[0]).click();
	    	//		$("#"+fila+"_facingCentro").val('');
	    		}	
	    			
    		}
    		*/

    	}
    	if (validacionNavegacion=='S'){
    		
    		
    		if ($(gridFacTextil.nameJQuery).is(':visible')) { //Estamos en textil

	    		if (origen == 'L') {//Partimos de un lote
	    			
	    			if ($("#gridP26FacTextil #"+fila+" td.sgexpanded .ui-icon-minus").is(':visible')) {
	    				//El lote actual esta abierto, debe navegar a la primera hija del lote actual 
	    				
    	    			idFocoSubgrid = "1_" + nombreColumna;  	    			
    	    			var subgrid = "gridP26FacTextil_" + fila + "_t"; 
    	    	    	$("#" + subgrid + " #"+idFocoSubgrid).focus();
    	    	    	$("#" + subgrid + " #"+idFocoSubgrid).select();

	    			} else {
	    				
	    				//Debe navegar a una madre
		    			$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_facingCentro'] #"+idFoco).focus();
		    	    	$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_facingCentro'] #"+idFoco).select();
		    	    	$("#p26_fld_SFM_Selecc").val(idFoco);
	    				
	    			}
	    			
	    		} else { //origen igual a 'H', partimos de una hija
	    			
	    			
	    			var subgrid  = $(e.target).closest( ".ui-jqgrid-btable" ).attr('id');
	    			var valores = subgrid.split('_');
	    			var indiceLote = valores[1]; //obteniendo el indice del subgrid, sabemos en que lote estamos
	    			var nivelLote = $("#gridP26FacTextil [aria-describedby='gridP26FacTextil_nivelLote'] #"+indiceLote+"_nivelLote").val();

	    			
    	    		if (nivelLote > fila) {	//No estamos en la ultima hija, navegará a la siguiente hija
    	    			
    	    			idFocoSubgrid = (parseInt(fila,10)+1) + "_" + nombreColumna;  	    			
    	    	    	$("#" + subgrid + " #"+idFocoSubgrid).focus();
    	    	    	$("#" + subgrid + " #"+idFocoSubgrid).select();
    	    	
    	    		}	else { 
    	    			//Estamos en la ultima hija, navegará al siguiente lote/madre.
    	    			
    	    			idFoco = (parseInt(indiceLote,10)+1) + "_" + nombreColumna;
    	    			
    	    			$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_facingCentro'] #"+idFoco).focus();
		    	    	$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_facingCentro'] #"+idFoco).select();
		    	    	$("#p26_fld_SFM_Selecc").val(idFoco);
    	    			
    	    		}
	    			
	    		}

    		} else {
    			
    			$("#"+idFoco).focus();
    	    	$("#"+idFoco).select();
    	    	$("#p26_fld_SFM_Selecc").val(idFoco);
    		}

    	}else{
    		$("#p26_btn_buscar").focus();
    	}
    	//if(nombreColumna == "facingCentro"){
    	//	controlCellFacingCentro(e);
    	//}
    }
}



function controlNavegacionSubgrid(e) {
	
	/*
	var idActual = e.target.id;
	
	//Obtención de fila y columna actuales
	var fila = idActual.substring(0, idActual.indexOf("_"));
	var nombreColumna = idActual.substring(idActual.indexOf("_")+1);
	
	var subgrid  = $(e.target).closest( ".ui-jqgrid-btable" ).attr('id');
	
	
	var key = e.which; //para soporte de todos los navegadores

   
    if (key == 38){//Tecla arriba
    	e.preventDefault();
    	idFocoSubgrid = (parseInt(fila,10)-1) + "_" + nombreColumna;

    	$("#" + subgrid + " #"+idFocoSubgrid).focus();
    	$("#" + subgrid + " #"+idFocoSubgrid).select();

    }
   
    if (key == 40){//Tecla abajo
    	e.preventDefault();
    	idFocoSubgrid = (parseInt(fila,10)+1) + "_" + nombreColumna;

    	$("#" + subgrid + " #"+idFocoSubgrid).focus();
    	$("#" + subgrid + " #"+idFocoSubgrid).select();

    }
    
    */
	
	
	controlNavegacion(e,'H');
    
   
}

function controlNavegacionFacingLote(e) {
	

	controlNavegacion(e,'L');
    
   
}




function imageFormatMessageFac(cellValue, opts, rData) {
	
	var imagen = "";
	var mostrarModificado = "none;";
	var mostrarGuardado = "none;";
	var mostrarError = "none;";
	var descError = "";
	
	//Controlamos los posibles errores que me lleguen para pintar el icono correspondiente.
	if (parseFloat(rData['codError']) == '8')
	{
		//Pintamos el icono de que se ha guardado
		mostrarGuardado = "block;";
	}
	else if (parseFloat(rData['codError']) == '9')
	{
		//Pintamos el icono de que se ha modificado
//		addSeleccionados(numRow);
		mostrarModificado = "block;";
	}
	
	else if (parseFloat(rData['codError']) == '1')
	{
		//Pintamos el icono de que ha ocurrido un error
		descError = replaceSpecialCharacters(errorActualizacion);
		mostrarError = "block;";
	}	
	else if (parseFloat(rData['codError']) == '2')
	{
		//Pintamos el icono de que ha ocurrido un error
		descError = replaceSpecialCharacters(errorActualizacion);
		mostrarError = "block;";
	}
	else if (parseFloat(rData['codError']) == '3')
	{
		//Pintamos el icono de que ha ocurrido un error
		descError = replaceSpecialCharacters(errorActualizacion);
		mostrarError = "block;";
	}
	else if (parseFloat(rData['codError']) == '4')
	{
		//Pintamos el icono de que ha ocurrido un error
		descError = replaceSpecialCharacters(errorActualizacionOrig);
		mostrarError = "block;";
	}
	else if (parseFloat(rData['codError']) == '5')
	{
		//Pintamos el icono de que ha ocurrido un error
		descError = replaceSpecialCharacters(errorActualizacionEstra);
		mostrarError = "block;";
	} else if (null != rData['codError'] && parseFloat(rData['codError']) != '0'){
		descError = rData['descError'];
		mostrarError = "block;";
	}
	
	imagen = "<div id='"+opts.rowId+"_fac_divGuardado' align='center' style='display: "+ mostrarGuardado + "'><img id='"+opts.rowId+"_fac_imgGuardado' src='./misumi/images/floppy.png' title='"+iconoGuardado+"'/></div>"; //Guardado
	imagen += "<div id='"+opts.rowId+"_fac_divModificado' align='center' style='display: "+ mostrarModificado + "'><img id='"+opts.rowId+"_fac_imgModificado' src='./misumi/images/modificado.png' title='"+iconoModificado+"'/></div>"; //Modificado
	imagen += "<div id='"+opts.rowId+"_fac_divError' align='center' style='display: " + mostrarError + "'><img id='"+opts.rowId+"_fac_imgError' src='./misumi/images/dialog-error-24.png' title='"+descError+"'/></div>"; //Error

	//Añadimos también los valores de la columna facingPrevio ocultos para poder utilizarlos posteriormente.
	var datoFacingOrig = "<input type='hidden' id='"+opts.rowId+"_facingCentro_orig' value='"+rData['facingCentro']+"'>";
	imagen +=  datoFacingOrig;
	
	var datoFacing = "<input type='hidden' id='"+opts.rowId+"_facingCentro_tmp' value='"+rData['facingCentro']+"'>";
	imagen +=  datoFacing;
	
	//Añadimos también el valor del facing previo.
	var datoFacingPrevio = "<input type='hidden' id='"+opts.rowId+"_facingPrevio' value='"+rData['facingPrevio']+"'>";
	imagen +=  datoFacingPrevio;
	
	//Añadimos también el valor del flgEstrategica de cada registro.
	var datoFlgEstrategica = "<input type='hidden' id='"+opts.rowId+"_flgEstrategica' value='"+rData['flgEstrategica']+"'>";
	imagen +=  datoFlgEstrategica;
	
	//Añadimos también el índice de la fila en la que estamos oculta para poder utilizarla posteriormente.
	var datoIndice = "<input type='hidden' id='"+opts.rowId+"_fac_indice' value='"+rData['indice']+"'>";
	imagen +=  datoIndice;
	
	//Añadimos también el valor del codError de cada registro.
	var datoError = "<input type='hidden' id='"+opts.rowId+"_fac_codError_orig' value='"+rData['codError']+"'>";
	imagen +=  datoError;
	
	//Añadimos también el valor del codArticuloLote de cada registro (Esto solo sera util para las referencias hijas de un lote).
	var datoCodArticuloLote = "<input type='hidden' id='"+opts.rowId+"_codArticuloLote' value='"+rData['codArticuloLote']+"'>";
	imagen +=  datoCodArticuloLote;
	
	//Añadimos también el valor del expositor de cada registro.
	var expositor = "<input type='hidden' id='"+opts.rowId+"_expositor' value='"+rData['tipoExpositor']+"'>";
	imagen +=  expositor;
	
//	numRow++;
	return imagen;
}

/*
function imageFormatMessageFac(cellValue, opts, rData) {
	
	var imagen = "";
	var mostrarModificado = "none;";
	var mostrarGuardado = "none;";
	var mostrarError = "none;";
	var descError = "";
	
	//Controlamos los posibles errores que me lleguen para pintar el icono correspondiente.
	if (parseFloat(rData['codError']) == '8')
	{
		//Pintamos el icono de que se ha guardado
		mostrarGuardado = "block;";
	}
	else if (parseFloat(rData['codError']) == '9')
	{
		//Pintamos el icono de que se ha modificado
		addSeleccionados(numRow);
		mostrarModificado = "block;";
	}
	else if (parseFloat(rData['codError']) == '1')
	{
		//Pintamos el icono de que ha ocurrido un error
		descError = replaceSpecialCharacters(errorActualizacion);
		mostrarError = "block;";
	}	
	else if (parseFloat(rData['codError']) == '2')
	{
		//Pintamos el icono de que ha ocurrido un error
		descError = replaceSpecialCharacters(errorActualizacion);
		mostrarError = "block;";
	}
	else if (parseFloat(rData['codError']) == '3')
	{
		//Pintamos el icono de que ha ocurrido un error
		descError = replaceSpecialCharacters(errorActualizacion);
		mostrarError = "block;";
	}
	else if (parseFloat(rData['codError']) == '4')
	{
		//Pintamos el icono de que ha ocurrido un error
		descError = replaceSpecialCharacters(errorActualizacionOrig);
		mostrarError = "block;";
	}
	else if (parseFloat(rData['codError']) == '5')
	{
		//Pintamos el icono de que ha ocurrido un error
		descError = replaceSpecialCharacters(errorActualizacionEstra);
		mostrarError = "block;";
	}
	
	imagen = "<div id='"+numRow+"_fac_divGuardado' align='center' style='display: "+ mostrarGuardado + "'><img id='"+numRow+"_fac_imgGuardado' src='./misumi/images/floppy.png' title='"+iconoGuardado+"'/></div>"; //Guardado
	imagen += "<div id='"+numRow+"_fac_divModificado' align='center' style='display: "+ mostrarModificado + "'><img id='"+numRow+"_fac_imgModificado' src='./misumi/images/modificado.png' title='"+iconoModificado+"'/></div>"; //Modificado
	imagen += "<div id='"+numRow+"_fac_divError' align='center' style='display: " + mostrarError + "'><img id='"+numRow+"_fac_imgError' src='./misumi/images/dialog-error-24.png' title='"+descError+"'/></div>"; //Error

	//Añadimos también los valores de la columna facingPrevio ocultos para poder utilizarlos posteriormente.
	var datoFacingOrig = "<input type='hidden' id='"+numRow+"_facingCentro_orig' value='"+rData['facingCentro']+"'>";
	imagen +=  datoFacingOrig;
	
	var datoFacing = "<input type='hidden' id='"+numRow+"_facingCentro_tmp' value='"+rData['facingCentro']+"'>";
	imagen +=  datoFacing;
	
	//Añadimos también el valor del facing previo.
	var datoFacingPrevio = "<input type='hidden' id='"+numRow+"_facingPrevio' value='"+rData['facingPrevio']+"'>";
	imagen +=  datoFacingPrevio;
	
	//Añadimos también el valor del flgEstrategica de cada registro.
	var datoFlgEstrategica = "<input type='hidden' id='"+numRow+"_flgEstrategica' value='"+rData['flgEstrategica']+"'>";
	imagen +=  datoFlgEstrategica;
	
	//Añadimos también el índice de la fila en la que estamos oculta para poder utilizarla posteriormente.
	var datoIndice = "<input type='hidden' id='"+numRow+"_fac_indice' value='"+rData['indice']+"'>";
	imagen +=  datoIndice;
	
	//Añadimos también el valor del codError de cada registro.
	var datoError = "<input type='hidden' id='"+numRow+"_fac_codError_orig' value='"+rData['codError']+"'>";
	imagen +=  datoError;
	
	//Añadimos también el valor del codArticuloLote de cada registro (Esto solo sera util para las referencias hijas de un lote).
	var datoCodArticuloLote = "<input type='hidden' id='"+numRow+"_codArticuloLote' value='"+rData['codArticuloLote']+"'>";
	imagen +=  datoCodArticuloLote;
	
	//Añadimos también el valor del expositor de cada registro.
	var expositor = "<input type='hidden' id='"+opts.rowId+"_expositor' value='"+rData['tipoExpositor']+"'>";
	imagen +=  expositor;
	
	numRow++;
	return imagen;
}
*/

function controlCellFacingCentro(e){
	var idActual = e.target.id;
	var valueActual = null;
	var fila = parseInt(idActual.substring(0, idActual.indexOf("_")));
	var tipo = e.originalEvent.type; //Nos da el origen del evento, raton o teclado
	var key = e.which; //Nos da el número de botón pulsado del raton o el número de tecla pulsado del teclado
	var valuePrevio = null;
	if (tipo == "click"){ //Ratón
		if ((key == 1) || (key == 0)){//Click del ratón. key igual a 0 es botón izquierdo para IE, 1 para el resto
			idActual = e.target.id;
			valueActual = $("#"+idActual).val();
		}
	}
	else{ //Teclado
		if (key == 38){//Tecla arriba
			fila--;
			idActual = fila+"_facingCentro";
			if ($("#"+idActual).length){
				valueActual = $("#"+idActual).val();
				//Seleccionamos el texto para editar
				//$("#"+idActual).select();
			}
		}
		else if (key == 40){//Tecla abajo
			fila++;
			idActual = fila+"_facingCentro";
			if ($("#"+idActual).length){
				valueActual = $("#"+idActual).val();
				//Seleccionamos el texto para editar
				//$("#"+idActual).select();
			}
		}
	}
	if (parseInt(valueActual) == 0){
		//Obtención del facingPrevio
		valuePrevio = $("#"+fila+"_facingPrevio").val();
		//Asignamos el facingPrevio al facingCentro
		$("#"+idActual).val(valuePrevio);
	}
}

function validacionFacing(id, mostrarAlerta){

		//No se hacen validaciones, sólo formateamos el valor introducido por el usuario.
	if ($("#gridP26FacTextil").is(':visible')) {
		var campoActual = $("#gridP26FacTextil [aria-describedby='gridP26FacTextil_facingCentro'] #"+id).val();
	} else {
		var campoActual = $("#gridP26Fac [aria-describedby='gridP26Fac_facingCentro'] #"+id).val();
	}

	var campoActualPunto = $("#"+id).val();
	var fila = id.substring(0, id.indexOf("_"));
	var error = 0;
	var descError = '';
	var campoFacingOrig = fila + "_facingCentro_orig";
	var campoFacingTmp = fila + "_facingCentro_tmp";
	var valorCampoFacingTmp = $("#"+campoFacingTmp).val();
	var campoDivModificado = fila + "_fac_divModificado";
	var campoDivGuardado = fila + "_fac_divGuardado";
	var campoDivError = fila + "_fac_divError";
	var campoImgError = fila + "_fac_imgError";
	var campoErrorOrig = fila + "_fac_codError_orig";	
	var campoFlgEstrategica = fila + "_flgEstrategica";
	var resultadoValidacion = 'S';
	
	
	if (mostrarAlerta == 'S'){
		if (campoActual == '')
		{
			campoActualPunto = "0";
		}
	//	$("#"+id).val(campoActualPunto).formatNumber({format:"0",locale:"es"});
		
		if (isNaN(parseInt(campoActualPunto))){
			descError = replaceSpecialCharacters(FacingIncorrecto);
			createAlert(descError, "ERROR");
			
			//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
			$("#p26_fld_SFM_Selecc").val(fila + "_facingCentro");
			
			//Pintamos de rojo el campo.
			$("#"+id).removeClass("editable").addClass("editableError");
			
			error = 1;
		}
		else if ( ($("#"+campoFlgEstrategica).val() == "S") && (campoActualPunto == 0) ) {
			descError = replaceSpecialCharacters(errorActualizacionEstra);
			createAlert(descError, "ERROR");
			
			//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
			$("#p26_fld_SFM_Selecc").val(fila + "_facingCentro");
			
			//Pintamos de rojo el campo.
			$("#"+id).removeClass("editable").addClass("editableError");
			
			error = 1;
		}
		
		var campoFacingOrigPunto = $("#"+campoFacingOrig).val();
		
		if (error == 1)
		{

			//En este caso ha ocurrido un error y hay que mostrar el icono de error.
			$("#"+campoDivGuardado).hide();
			$("#"+campoDivModificado).hide();
			$("#"+campoDivError).show();
			//Cambiamos el title
			$("#"+campoImgError).attr('title', descError);
			
			//Añadimos la fila al array.
			addSeleccionados(fila);
			//Cargamos los valores anteriores a la modificación.
			$("#"+id).val(campoFacingOrigPunto).formatNumber({format:"0",locale:"es"});
			$("#"+campoFacingTmp).val(campoFacingOrigPunto).formatNumber({format:"0",locale:"es"});
			$("#"+campoErrorOrig).val('0');
		}
		else
		{
			//No hay error con lo que quitamos el posible icono de error.
			$("#"+campoDivError).hide();
			$("#"+id).removeClass("editableError").addClass("editable");
			
			//Además se establece si se ha modificado el campo
			
			if ($("#gridP26FacTextil").is(':visible')) {
				
				if (($("#gridP26FacTextil [aria-describedby='gridP26FacTextil_mensaje'] #"+campoFacingTmp).val() != null) && ($("#gridP26FacTextil [aria-describedby='gridP26FacTextil_mensaje'] #"+campoFacingTmp).val() != campoActual))
				{
					//En este caso se ha modificado el campo y hay que establecer el icono de modificaci?n.
					$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_mensaje'] #"+campoDivError).hide();
					$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_mensaje'] #"+campoDivGuardado).hide();
					$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_mensaje'] #"+campoDivModificado).show();
					$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_mensaje'] #"+campoFacingTmp).val(campoActual);
					$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_mensaje'] #"+campoErrorOrig).val('9');
					
					//En el caso de ser una consulta de Facing-capacidad
					//Al modificar el facing hay que calcular la capacidad.
					if ($("#p26_txt_flgFacing").val()=='S' && $("#p26_txt_flgFacingCapacidad").val()=='S'){
						recalcularCapacidad(id,campoActual);
					}
					
					//Añadimos la fila al array.
					addSeleccionados(fila);
	
				}
				
			} else {
				
				if (($("#gridP26Fac [aria-describedby='gridP26Fac_mensaje'] #"+campoFacingTmp).val() != null) && ($("#gridP26FacTextil [aria-describedby='gridP26Fac_mensaje'] #"+campoFacingTmp).val() != campoActual))
				{
					//En este caso se ha modificado el campo y hay que establecer el icono de modificaci?n.
					$("#gridP26Fac [aria-describedby='gridP26Fac_mensaje'] #"+campoDivError).hide();
					$("#gridP26Fac [aria-describedby='gridP26Fac_mensaje'] #"+campoDivGuardado).hide();
					$("#gridP26Fac [aria-describedby='gridP26Fac_mensaje'] #"+campoDivModificado).show();
					$("#gridP26Fac [aria-describedby='gridP26Fac_mensaje'] #"+campoFacingTmp).val(campoActual);
					$("#gridP26Fac [aria-describedby='gridP26Fac_mensaje'] #"+campoErrorOrig).val('9');
					
					//En el caso de ser una consulta de Facing-capacidad
					//Al modificar el facing hay que calcular la capacidad.
					if ($("#p26_txt_flgFacing").val()=='S' && $("#p26_txt_flgFacingCapacidad").val()=='S'){
						recalcularCapacidad(id,campoActual);
					}
					
					//Añadimos la fila al array.
					addSeleccionados(fila);
	
				}
				
			}
		}
	}else{
		if (campoActual == '')
		{ //En textil puede ser vacio.
			campoActualPunto = "0";
		}
		//Control de campo decimal erroneo
		if (isNaN(parseInt(campoActualPunto))){
			resultadoValidacion = 'N';
		}
	}
	return resultadoValidacion;
}



function recalcularCapacidad(id, facingCentro){	

	 //Pasarle, el valor del facing , el centro  y el codigo del articulo
	
	var fila = id.substring(0, id.indexOf("_"));
	var rowData = jQuery($('#gridP26Fac')).getRowData(fila);     
	var codId = rowData['codArticulo'];
	
	
	var recordSfm = new VArtSfm ($("#centerId").val(), codId, null, null, null, null, null, null, null,
				null, null, null, null, null,null,
				null, null, null, null, null, null,	null, null, null, null, null, null, facingCentro);
		
	
	var objJson = $.toJSON(recordSfm.prepareVArtSfmToJsonObject());


	$.ajax({
		type : 'POST',
		data : objJson,
		url : './sfmCapacidad/recalcularCapacidad.do',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {
			if (data != null) {

				 jQuery($('#gridP26Fac')).jqGrid('setCell',fila,'capacidad',data,{},{ title: data});
	 
			}
			
		},
		error : function(xhr, status, error) {
			handleError(xhr, status, error, locale);
		}
	});	

}

function validacionCapacidad(id, mostrarAlerta){
	
	//No se hacen validaciones, s?lo formateamos el valor introducido por el usuario.
	var campoActual = $("#"+id).val();
	var campoActualPunto = $("#"+id).val().replace(',','.');
	var fila = id.substring(0, id.indexOf("_"));
	var error = 0;
	var descError = '';
	var campoCapacidadOrig = fila + "_capacidad_orig";
	var campoCapacidadTmp = fila + "_capacidad_tmp";
	var campoDivModificado = fila + "_cap_divModificado";
	var campoDivGuardado = fila + "_cap_divGuardado";
	var campoDivError = fila + "_cap_divError";
	var campoImgError = fila + "_cap_imgError";
	var campoErrorOrig = fila + "_cap_codError_orig";	
	var resultadoValidacion = 'S';
	
	if (mostrarAlerta == 'S'){
		if (campoActual == '')
		{
			//campoActualPunto = "0.000";
			campoActualPunto = "0";
		}
		//$("#"+id).val(campoActualPunto).formatNumber({format:"0.000",locale:"es"});
		$("#"+id).val(campoActualPunto).formatNumber({format:"0",locale:"es"});
		
		if ((isNaN(parseFloat(campoActualPunto)) || campoActual.split(",").length > 2)|| 
		((parseFloat(campoActualPunto) >= parseFloat("100000")))){
			
			if (isNaN(parseFloat(campoActualPunto)) || campoActual.split(",").length > 2){
				descError = replaceSpecialCharacters(CapacidadIncorrecto);
			}
			else //Capacidad mayor de la permitida
			{
				descError = replaceSpecialCharacters(CapacidadMayorPermitida);
			}
			createAlert(descError, "ERROR");
			
			//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
			$("#p26_fld_SFM_Selecc").val(fila + "_capacidad");
			
			//Pintamos de rojo el campo.
			$("#"+id).removeClass("editable").addClass("editableError");
			
			error = 1;
		}

		var campoCapacidadOrigPunto = $("#"+campoCapacidadOrig).val().replace(',','.');
		
		if (error == 1)
		{
			//En este caso ha ocurrido un error y hay que mostrar el icono de error.
			$("#"+campoDivGuardado).hide();
			$("#"+campoDivModificado).hide();
			$("#"+campoDivError).show();
			//Cambiamos el title
			$("#"+campoImgError).attr('title', descError);
			
			//Añadimos la fila al array.
			addSeleccionados(fila);
			//Cargamos los valores anteriores a la modificación.
			//$("#"+id).val(campoCapacidadOrigPunto).formatNumber({format:"0.000",locale:"es"});
			//$("#"+campoCapacidadTmp).val(campoCapacidadOrigPunto).formatNumber({format:"0.000",locale:"es"});
			$("#"+id).val(campoCapacidadOrigPunto).formatNumber({format:"0",locale:"es"});
			$("#"+campoCapacidadTmp).val(campoCapacidadOrigPunto).formatNumber({format:"0",locale:"es"});
			$("#"+campoErrorOrig).val('0');
		}
		else
		{
			//No hay error con lo que quitamos el posible icono de error.
			$("#"+campoDivError).hide();
			$("#"+id).removeClass("editableError").addClass("editable");
			
			//Además se establece si se ha modificado el campo
			if (($("#"+campoCapacidadTmp).val() != null) && ($("#"+campoCapacidadTmp).val() != campoActual))
			{
				//if (($("#"+campoCapacidadOrig).val() != null)&&((parseFloat($("#"+campoCapacidadOrig).val())) != (parseFloat(campoActualPunto))))
				//{
					
						//En este caso se ha modificado el campo y hay que establecer el icono de modificaci?n.
						$("#"+campoDivError).hide();
						$("#"+campoDivGuardado).hide();
						$("#"+campoDivModificado).show();
						$("#"+campoCapacidadTmp).val(campoActual);
						$("#"+campoErrorOrig).val('9');
						
						//Añadimos la fila al array.
						addSeleccionados(fila);
				/*}
				else
				{
					//En este caso no se ha modificado el campo y hay que quitar el posible icono de modificaci?n.
					$("#"+campoDivError).hide();
					$("#"+campoDivModificado).hide();
					$("#"+campoDivGuardado).hide();
					$("#"+campoCapacidadTmp).val(campoActual);
					$("#"+campoErrorOrig).val('0');
					
					//Eliminamos la fila al array.
					addSeleccionados(fila);
				}*/
			}
		}
	}else{
		//Control de campo decimal erroneo
		if (isNaN(parseFloat(campoActualPunto)) || campoActual.split(",").length > 2){
			resultadoValidacion = 'N';
		}
	}
	return resultadoValidacion;
}

function validacionCoberturaSfm(id, mostrarAlerta){

	//En el caso de la cobertura no se realizar? ning?n control, lo ?nico que tenemos que hacer es si se ha modificado el 
	//valor del campo y no ha habido un error en el campo SFM, mostrar el icono de modificado.
	var campoActual = $("#"+id).val();
	var campoActualPunto = $("#"+id).val().replace(',','.');
	var fila = id.substring(0, id.indexOf("_"));
	var error = 0;
	var descError = '';
	var campoSfmActual = fila + "_sfm";
	var campoCoberturaSfmOrig = fila + "_coberturaSfm_orig";
	var campoCoberturaSfmTmp = fila + "_coberturaSfm_tmp";
	var campoSfmOrig = fila + "_sfm_orig";
	var campoSfmTmp = fila + "_sfm_tmp";
	var campoErrorOrig = fila + "_sfm_codError_orig";	
	var campoDivModificado = fila + "_sfm_divModificado";
	var campoDivGuardado = fila + "_sfm_divGuardado";
	var campoDivError = fila + "_sfm_divError";
	var campoImgError = fila + "_sfm_imgError";
	var campoVentaMediaActual = fila + "_ventaMedia";
	var resultadoValidacion = 'S';
	
	var isVisibleError = $("#"+campoDivError).is(':visible');
	var isVisibleModificado = $("#"+campoDivModificado).is(':visible');
	
	if (mostrarAlerta == 'S'){
		//Formateamos el valor introducido por el usuario
		if (campoActual == '')
		{
			campoActualPunto = "0.000";
		}
		$("#"+id).val(campoActualPunto).formatNumber({format:"0.000",locale:"es"});
		
		if (isNaN(parseFloat(campoActualPunto)) || campoActual.split(",").length > 2){
			descError = replaceSpecialCharacters(SFMCoberturaIncorrecto);
			createAlert(descError, "ERROR");
			
			//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
			$("#p26_fld_SFM_Selecc").val(fila + "_coberturaSfm");
			
			//Pintamos de rojo el campo.
			$("#"+id).removeClass("editable").addClass("editableError");
			
			error = 1;
		}
		
		var campoCoberturaSfmOrigPunto = $("#"+campoCoberturaSfmOrig).val().replace(',','.');
		
		if (error == 1)
		{
			//En este caso ha ocurrido un error y hay que mostrar el icono de error.
			$("#"+campoDivGuardado).hide();
			$("#"+campoDivModificado).hide();
			$("#"+campoDivError).show();
			//Cambiamos el title
			$("#"+campoImgError).attr('title', descError);
			
			//Añadimos la fila al array.
			addSeleccionados(fila);
			//Cargamos los valores anteriores a la modificación.
			$("#"+id).val(campoCoberturaSfmOrigPunto).formatNumber({format:"0.000",locale:"es"});
			$("#"+campoCoberturaSfmTmp).val(campoCoberturaSfmOrigPunto).formatNumber({format:"0.000",locale:"es"});
			$("#"+campoErrorOrig).val('0');
		}
		else
		{
			//No hay error con lo que quitamos el posible icono de error.
			$("#"+campoDivError).hide();
			$("#"+id).removeClass("editableError").addClass("editable");
			
			if (($("#"+campoCoberturaSfmTmp).val() != null) && $("#"+campoCoberturaSfmTmp).val() != campoActual)
			{
				//if (parseFloat($("#"+campoCoberturaSfmOrig).val()) != parseFloat(campoActualPunto))
				//{
					//En este caso se ha modificado el campo y hay que establecer el icono de modificaci?n.
					$("#"+campoDivError).hide();
					$("#"+campoDivGuardado).hide();
					$("#"+campoDivModificado).show();
					$("#"+campoCoberturaSfmTmp).val(campoActual);
					$("#"+campoErrorOrig).val('9');
				/*}
				else
				{
					//En este caso no se ha modificado el campo y hay que quitar el posible icono de modificaci?n.
					$("#"+campoDivError).hide();
					$("#"+campoDivModificado).hide();
					$("#"+campoDivGuardado).hide();
					$("#"+campoCoberturaSfmTmp).val(campoActual);
					$("#"+campoErrorOrig).val('0');
				}*/
				//Añadimos la fila al array.
				addSeleccionados(fila);
		
				if (parseFloat($("#"+campoVentaMediaActual).val()) == 0)
				{
					$("#"+campoSfmActual).val($("#"+id).val());
					$("#"+campoSfmTmp).val($("#"+id).val());
				}
				else
				{
					$("#"+campoSfmActual).val($.formatNumber(parseFloat(campoActualPunto)*parseFloat($("#"+campoVentaMediaActual).val().replace(',','.')),{format:"0.000",locale:"es"})); 
					$("#"+campoSfmTmp).val($.formatNumber(parseFloat(campoActualPunto)*parseFloat($("#"+campoVentaMediaActual).val().replace(',','.')),{format:"0.000",locale:"es"}));
				}
				
				//Además situamos el foco en el campo para que haga las validaciones y lo volvemos a quitar.
				var idSfm = (parseInt(fila,10)) + "_sfm";
				resultadoValidacion = validacionSfm(idSfm, mostrarAlerta);
			}
		}
	}else{
		//Control de campo decimal erroneo
		if (isNaN(parseFloat(campoActualPunto)) || campoActual.split(",").length > 2){
			resultadoValidacion = 'N';
		}
	}
	return resultadoValidacion;
}

function obtenerListadoModificados(){
	
	//A partir del array de seleccionados obtenemos el listado de campos modificados a enviar al controlador.
	var registroListadoModificado = {};
	var valorCapacidad = "";
	var valorSfm = "";
	var valorCoberturaSfm = "";
	var valorCodError = "";
	for (i = 0; i < seleccionados.length; i++){
		if (seleccionados[i] != null && seleccionados[i] != '')
		{
			//Por cada elemento seleccionado, tenemos que crear el listado de modificados.
			registroListadoModificado = {};
			valorSfm = "";
			valorCoberturaSfm = "";
			valorCapacidad = "";
			valorCodError = "";
			valorSfm = $("#"+ seleccionados[i] + "_sfm").val() ? $("#"+ seleccionados[i] + "_sfm").val().replace(',','.') : "";
			valorCoberturaSfm = $("#"+ seleccionados[i] + "_coberturaSfm").val() ? $("#"+ seleccionados[i] + "_coberturaSfm").val().replace(',','.') : "";
			
			if ($("#p26_txt_flgFacing").val()=='S' && $("#p26_txt_flgFacingCapacidad").val()=='S') {
				rowData = jQuery($('#gridP26Fac')).getRowData(i);     
				valorCapacidad = rowData['capacidad'];
			} else {
				valorCapacidad = $("#"+ seleccionados[i] + "_capacidad").val() ? $("#"+ seleccionados[i] + "_capacidad").val().replace(',','.') : "";
			}
			
			valorFacingCentro = $("#"+ seleccionados[i] + "_facingCentro").val() ? $("#"+ seleccionados[i] + "_facingCentro").val().replace(',','.') : "";

			if ($("#p26_txt_flgFacing").val()=='S'){
				if  ($("#gbox_gridP26FacTextil").is(':visible')) {
					registroListadoModificado.indice =  $("#gridP26FacTextil [aria-describedby='gridP26FacTextil_mensaje'] #" + seleccionados[i] + "_fac_indice").val();	
					registroListadoModificado.codError = $("#gridP26FacTextil [aria-describedby='gridP26FacTextil_mensaje'] #"+ seleccionados[i] + "_fac_codError_orig").val() ? $("#gridP26FacTextil [aria-describedby='gridP26FacTextil_mensaje'] #"+ seleccionados[i] + "_fac_codError_orig").val() : "";
				} else {
					registroListadoModificado.indice =  $("#"+ seleccionados[i] + "_fac_indice").val();
					registroListadoModificado.codError = $("#"+ seleccionados[i] + "_fac_codError_orig").val() ? $("#"+ seleccionados[i] + "_fac_codError_orig").val() : "";
				}
			}else if ($("#p26_txt_flgCapacidad").val()=='S'){
				registroListadoModificado.indice =  $("#"+ seleccionados[i] + "_cap_indice").val();
				registroListadoModificado.codError = $("#"+ seleccionados[i] + "_cap_codError_orig").val() ? $("#"+ seleccionados[i] + "_cap_codError_orig").val() : "";
			}else{
				registroListadoModificado.indice =  $("#"+ seleccionados[i] + "_sfm_indice").val();
				registroListadoModificado.codError = $("#"+ seleccionados[i] + "_sfm_codError_orig").val() ? $("#"+ seleccionados[i] + "_sfm_codError_orig").val() : "";
			}
			
			registroListadoModificado.sfm =  valorSfm;
			registroListadoModificado.coberturaSfm = valorCoberturaSfm;
			registroListadoModificado.capacidad = valorCapacidad;
			registroListadoModificado.facingCentro = valorFacingCentro;

			listadoModificados.push(registroListadoModificado)
		}	
	}

	//Reseteamos el array de los seleccionados.
	seleccionados = new Array();
}

function addSeleccionados(fila){
	
	seleccionados[fila] = fila;
}

function deleteSeleccionados(fila){
	
	seleccionados[fila] = null;
}

function validacionSfm(id, mostrarAlerta){
	
	//Tenemos que comprobar en primer lugar que el valor introducido sea mayor o igual que el l?mite inferior.
	var fila = id.substring(0, id.indexOf("_"));
	var campoLimInf = fila + "_lmin";
	var campoLimSup = fila + "_lsf";
	var campoFlag = fila + "_flgFecsfm";
	var error = 0;
	var descError = '';
	var campoActual = $("#"+id).val();
	var campoActualPunto = $("#"+id).val().replace(',','.');
	var campoSfmOrig = fila + "_sfm_orig";
	var campoSfmTmp = fila + "_sfm_tmp";
	var campoCoberturaSfmOrig = fila + "_coberturaSfm_orig";
	var campoCoberturaSfmTmp = fila + "_coberturaSfm_tmp";
	var campoErrorOrig = fila + "_sfm_codError_orig";
	var resultadoValidacion = 'S';
	
	if (mostrarAlerta == 'S'){
		
		//Formateamos el valor introducido por el usuario
		if (campoActual == '')
		{
			campoActualPunto = "0.000";
		}
		
		if(campoActual.indexOf(".")>= 0 || campoActual.indexOf(",")>= 0)
		$("#"+id).val(campoActualPunto).formatNumber({format:"0.000",locale:"es"});
		
		if ((isNaN(parseFloat(campoActualPunto)) || campoActual.split(",").length > 2) || 
				((parseFloat(campoActualPunto) < parseFloat($("#"+campoLimInf).val()))||
				((parseFloat($("#"+campoFlag).val()) == '1') && (parseFloat(campoActualPunto) > parseFloat($("#"+campoLimSup).val()))))&&
				(parseFloat($("#"+campoSfmOrig).val()) != parseFloat(campoActualPunto)))
		{
			
			//Mostramos la alerta.
			resultadoValidacion = 'N';
			if (isNaN(parseFloat(campoActualPunto)) || campoActual.split(",").length > 2){
				descError = replaceSpecialCharacters(SFMIncorrecto);
				createAlert(descError, "ERROR");
			}
			else if (parseFloat(campoActualPunto) < parseFloat($("#"+campoLimInf).val()))
			{
				descError = replaceSpecialCharacters(SFMmenorLimInf);
				createAlert(descError, "ERROR");
			}
			else
			{
				descError = replaceSpecialCharacters(SFMmayorLimSup);
				createAlert(descError, "ERROR");
			}
			
			//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
			$("#p26_fld_SFM_Selecc").val(fila + "_sfm");
			
			//Pintamos de rojo el campo.
			$("#"+id).removeClass("editable").addClass("editableError");
			
			error = 1;
		}
		
		//Tendremos que cambiar el icono de escritura o de error en función de lo que haya ocurrido.
		//Sólo si ha cambiado el valor del campo.
		//Obtenemos el valor original del campo SFM
		
		var campoCoberturaSfmActual = fila + "_coberturaSfm";
		
		var campoDivModificado = fila + "_sfm_divModificado";
		var campoDivGuardado = fila + "_sfm_divGuardado";
		var campoDivError = fila + "_sfm_divError";
		var campoImgError = fila + "_sfm_imgError";
		var campoVentaMediaActual = fila + "_ventaMedia";
		var campoSfmOrigPunto = $("#"+campoSfmOrig).val().replace(',','.');
		var campoCoberturaSfmOrigPunto = $("#"+campoCoberturaSfmOrig).val().replace(',','.');
		
		if (error == 1)
		{
			//En este caso ha ocurrido un error y hay que mostrar el icono de error.
			$("#"+campoDivGuardado).hide();
			$("#"+campoDivModificado).hide();
			$("#"+campoDivError).show();
			//Cambiamos el title
			$("#"+campoImgError).attr('title', descError);
			
			//Añadimos la fila al array.
			addSeleccionados(fila);
			//Cargamos los valores anteriores a la modificación.
			$("#"+id).val(campoSfmOrigPunto).formatNumber({format:"0.000",locale:"es"});
			$("#"+campoSfmTmp).val(campoSfmOrigPunto).formatNumber({format:"0.000",locale:"es"});
			$("#"+campoCoberturaSfmActual).val(campoCoberturaSfmOrigPunto).formatNumber({format:"0.000",locale:"es"});
			$("#"+campoCoberturaSfmTmp).val(campoCoberturaSfmOrigPunto).formatNumber({format:"0.000",locale:"es"});
			$("#"+campoErrorOrig).val('0');

		}
		else
		{
			//No hay error con lo que quitamos el posible icono de error.
			$("#"+campoDivError).hide();
			$("#"+id).removeClass("editableError").addClass("editable");
			
			if (($("#"+campoSfmTmp).val() != null) && ($("#"+campoSfmTmp).val() != campoActual))
			{
				//if (parseFloat($("#"+campoSfmOrig).val()) != parseFloat(campoActualPunto))
				//{
					//En este caso se ha modificado el campo y hay que establecer el icono de modificación.
					$("#"+campoDivError).hide();
					$("#"+campoDivGuardado).hide();
					$("#"+campoDivModificado).show();
					$("#"+campoSfmTmp).val(campoActual);
					$("#"+campoErrorOrig).val('9');
					
					//Añadimos la fila al array.
					addSeleccionados(fila);
					
					if (parseFloat($("#"+campoVentaMediaActual).val()) == 0)
					{
						$("#"+campoCoberturaSfmActual).val($("#"+id).val());
						$("#"+campoCoberturaSfmTmp).val($("#"+id).val());
					}
					else
					{
						$("#"+campoCoberturaSfmActual).val($.formatNumber(parseFloat(campoActualPunto)/parseFloat($("#"+campoVentaMediaActual).val().replace(',','.')),{format:"0.000",locale:"es"})); 
						$("#"+campoCoberturaSfmTmp).val($.formatNumber(parseFloat(campoActualPunto)/parseFloat($("#"+campoVentaMediaActual).val().replace(',','.')),{format:"0.000",locale:"es"}));
					}
					
				//}
				/*else
				{
					//En este caso no se ha modificado el campo y hay que quitar el posible icono de modificación.
					$("#"+campoDivError).hide();
					$("#"+campoDivGuardado).hide();
					$("#"+campoDivModificado).hide();
					$("#"+campoSfmTmp).val(campoActual);
					$("#"+campoErrorOrig).val('0');
					
					//Eliminamos la fila al array.
					addSeleccionados(fila);
				}*/
			}
		}
	}else{
		//Control de campo decimal erroneo
		if (isNaN(parseFloat(campoActualPunto)) || campoActual.split(",").length > 2){
			resultadoValidacion = 'N';
		}else{
			//Formateamos el valor introducido por el usuario
			if (campoActual == '')
			{
				campoActualPunto = "0.000";
			}
			
			if(campoActualPunto.indexOf(".")>= 0 || campoActualPunto.indexOf(",")>= 0 )
			$("#"+id).val(campoActualPunto).formatNumber({format:"0.000",locale:"es"});
			
			if (((parseFloat(campoActualPunto) < parseFloat($("#"+campoLimInf).val()))||
					((parseFloat($("#"+campoFlag).val()) == '1') && (parseFloat(campoActualPunto) > parseFloat($("#"+campoLimSup).val()))))&&
					(parseFloat($("#"+campoSfmOrig).val()) != parseFloat(campoActualPunto)))
			{
				resultadoValidacion = 'N';
			}
		}
	}	
	return resultadoValidacion;
}

function actualizarSortOrder (columna) {
	
	    var ultimoSortName = $(grid.nameJQuery).jqGrid('getGridParam','sortname');
	    var ultimoSortOrder = $(grid.nameJQuery).jqGrid('getGridParam','sortorder');
		
	    
	    
	    
		if (ultimoSortName != columna ) { //Se ha cambiado la columna por la que se quiere ordenar
			
			if(columna == 'flgNueva' || columna == 'flgNsr'){
				$(grid.nameJQuery).setGridParam({sortorder:'desc'});
				$(grid.nameJQuery + "_" + columna + " [sort='desc']").removeClass("ui-state-disabled");
			}else{
				$(grid.nameJQuery).setGridParam({sortorder:'asc'});
				$(grid.nameJQuery + "_" + columna + " [sort='asc']").removeClass("ui-state-disabled");
			}
			
			//En la columna anterior quitamos la flechas
			if (ultimoSortOrder=='asc'){
				$(grid.nameJQuery + "_" + ultimoSortName + " [sort='asc']").addClass("ui-state-disabled");
			}else{
				$(grid.nameJQuery + "_" + ultimoSortName + " [sort='desc']").addClass("ui-state-disabled");
			}
			
		} else { //Seguimos en la misma columna
			
	  		if (ultimoSortOrder=='asc'){
	  			$(grid.nameJQuery).setGridParam({sortorder:'desc'});
	  			//Mostramos la flecha descendente y quitamos la flecha ascendente
	  			$(grid.nameJQuery + "_" + columna + " [sort='desc']").removeClass("ui-state-disabled");
	  			$(grid.nameJQuery + "_" + columna + " [sort='asc']").addClass("ui-state-disabled");
	  		} else {
	  			$(grid.nameJQuery).setGridParam({sortorder:'asc'});
	  			$(grid.nameJQuery + "_" + columna + " [sort='asc']").removeClass("ui-state-disabled");
	  			$(grid.nameJQuery + "_" + columna + " [sort='desc']").addClass("ui-state-disabled");
	  		}
	  		
		}
}

function cargarOrdenacionesColumnas(){
	
	  //Cuando pulse la columna para ordenar por icono tenemos que llamar manualmente al reload para que reordene,
	  //ya que al tener el jsp campos editables no funciona de la manera convencional.
	  var moved=false;
	  $("#jqgh_"+grid.name+"_descCodN1").unbind('mousedown');
	  $("#jqgh_"+grid.name+"_descCodN1").unbind('mousemove');
	  $("#jqgh_"+grid.name+"_descCodN1").unbind('mouseup');
	  $("#jqgh_"+grid.name+"_descCodN1").bind("mousedown", function() {
		  moved=false;
	  });
	  $("#jqgh_"+grid.name+"_descCodN1").bind("mousemove", function() {
		  moved=true;
	  });
	  $("#jqgh_"+grid.name+"_descCodN1").bind("mouseup", function() {
		  if(!moved){
			    actualizarSortOrder('descCodN1');
				$(grid.nameJQuery).setGridParam({sortname:'descCodN1'});
				$(grid.nameJQuery).setGridParam({page:1});
				reloadData('S');
	    	}
	  });
	  
	  $("#jqgh_"+grid.name+"_descCodN2").unbind('mousedown');
	  $("#jqgh_"+grid.name+"_descCodN2").unbind('mousemove');
	  $("#jqgh_"+grid.name+"_descCodN2").unbind('mouseup');
	  $("#jqgh_"+grid.name+"_descCodN2").bind("mousedown", function() {
		  moved=false;
	  });
	  $("#jqgh_"+grid.name+"_descCodN2").bind("mousemove", function() {
		  moved=true;
	  });
	  $("#jqgh_"+grid.name+"_descCodN2").bind("mouseup", function() {
		  if(!moved){
			    actualizarSortOrder('descCodN2');
				$(grid.nameJQuery).setGridParam({sortname:'descCodN2'});
				$(grid.nameJQuery).setGridParam({page:1});
				reloadData('S');
	    	}
	  });
	  
	  $("#jqgh_"+grid.name+"_descCodN3").unbind('mousedown');
	  $("#jqgh_"+grid.name+"_descCodN3").unbind('mousemove');
	  $("#jqgh_"+grid.name+"_descCodN3").unbind('mouseup');
	  $("#jqgh_"+grid.name+"_descCodN3").bind("mousedown", function() {
		  moved=false;
	  });
	  $("#jqgh_"+grid.name+"_descCodN3").bind("mousemove", function() {
		  moved=true;
	  });
	  $("#jqgh_"+grid.name+"_descCodN3").bind("mouseup", function() {
		  if(!moved){
			    actualizarSortOrder('descCodN3');
				$(grid.nameJQuery).setGridParam({sortname:'descCodN3'});
				$(grid.nameJQuery).setGridParam({page:1});
				reloadData('S');
	    	}
	  });
	  
	  $("#jqgh_"+grid.name+"_descCodN4").unbind('mousedown');
	  $("#jqgh_"+grid.name+"_descCodN4").unbind('mousemove');
	  $("#jqgh_"+grid.name+"_descCodN4").unbind('mouseup');
	  $("#jqgh_"+grid.name+"_descCodN4").bind("mousedown", function() {
		  moved=false;
	  });
	  $("#jqgh_"+grid.name+"_descCodN4").bind("mousemove", function() {
		  moved=true;
	  });
	  $("#jqgh_"+grid.name+"_descCodN4").bind("mouseup", function() {
		  if(!moved){
			    actualizarSortOrder('descCodN4');
				$(grid.nameJQuery).setGridParam({sortname:'descCodN4'});
				$(grid.nameJQuery).setGridParam({page:1});
				reloadData('S');
	    	}
	  });
	  
	  $("#jqgh_"+grid.name+"_descCodN5").unbind('click');
	  $("#jqgh_"+grid.name+"_descCodN5").unbind('mousedown');
	  $("#jqgh_"+grid.name+"_descCodN5").unbind('mousemove');
	  $("#jqgh_"+grid.name+"_descCodN5").unbind('mouseup');
	  $("#jqgh_"+grid.name+"_descCodN5").bind("mousedown", function() {
		  moved=false;
	  });
	  $("#jqgh_"+grid.name+"_descCodN5").bind("mousemove", function() {
		  moved=true;
	  });
	  $("#jqgh_"+grid.name+"_descCodN5").bind("mouseup", function() {
		  if(!moved){
			    actualizarSortOrder('descCodN5');
				$(grid.nameJQuery).setGridParam({sortname:'descCodN5'});
				$(grid.nameJQuery).setGridParam({page:1});
				reloadData('S');
	    	}
	  });
	  
	  $("#jqgh_"+grid.name+"_codArticulo").unbind('mousedown');
	  $("#jqgh_"+grid.name+"_codArticulo").unbind('mousemove');
	  $("#jqgh_"+grid.name+"_codArticulo").unbind('mouseup');
	  $("#jqgh_"+grid.name+"_codArticulo").bind("mousedown", function() {
		  moved=false;
	  });
	  $("#jqgh_"+grid.name+"_codArticulo").bind("mousemove", function() {
		  moved=true;
	  });
	  $("#jqgh_"+grid.name+"_codArticulo").bind("mouseup", function() {
		  if(!moved){
			    actualizarSortOrder('codArticulo');
				$(grid.nameJQuery).setGridParam({sortname:'codArticulo'});
				$(grid.nameJQuery).setGridParam({page:1});
				reloadData('S');
	    	}
	  });
	  
	  $("#jqgh_"+grid.name+"_denomInforme").unbind('mousedown');
	  $("#jqgh_"+grid.name+"_denomInforme").unbind('mousemove');
	  $("#jqgh_"+grid.name+"_denomInforme").unbind('mouseup');
	  $("#jqgh_"+grid.name+"_denomInforme").bind("mousedown", function() {
		  moved=false;
	  });
	  $("#jqgh_"+grid.name+"_denomInforme").bind("mousemove", function() {
		  moved=true;
	  });
	  $("#jqgh_"+grid.name+"_denomInforme").bind("mouseup", function() {
		  if(!moved){
			    actualizarSortOrder('denomInforme');
				$(grid.nameJQuery).setGridParam({sortname:'denomInforme'});
				$(grid.nameJQuery).setGridParam({page:1});
				reloadData('S');
	    	}
	  });
	  
	  $("#jqgh_"+grid.name+"_marca").unbind('mousedown');
	  $("#jqgh_"+grid.name+"_marca").unbind('mousemove');
	  $("#jqgh_"+grid.name+"_marca").unbind('mouseup');
	  $("#jqgh_"+grid.name+"_marca").bind("mousedown", function() {
		  moved=false;
	  });
	  $("#jqgh_"+grid.name+"_marca").bind("mousemove", function() {
		  moved=true;
	  });
	  $("#jqgh_"+grid.name+"_marca").bind("mouseup", function() {
		  if(!moved){
			    actualizarSortOrder('marca');
				$(grid.nameJQuery).setGridParam({sortname:'marca'});
				$(grid.nameJQuery).setGridParam({page:1});
				reloadData('S');
	    	}
	  });
	  
	  $("#jqgh_"+grid.name+"_stock").unbind('mousedown');
	  $("#jqgh_"+grid.name+"_stock").unbind('mousemove');
	  $("#jqgh_"+grid.name+"_stock").unbind('mouseup');
	  $("#jqgh_"+grid.name+"_stock").bind("mousedown", function() {
		  moved=false;
	  });
	  $("#jqgh_"+grid.name+"_stock").bind("mousemove", function() {
		  moved=true;
	  });
	  $("#jqgh_"+grid.name+"_stock").bind("mouseup", function() {
		  if(!moved){
			    actualizarSortOrder('stock');
				$(grid.nameJQuery).setGridParam({sortname:'stock'});
				$(grid.nameJQuery).setGridParam({page:1});
				reloadData('S');
	    	}
	  });
	  
	  $("#jqgh_"+grid.name+"_uc").unbind('mousedown');
	  $("#jqgh_"+grid.name+"_uc").unbind('mousemove');
	  $("#jqgh_"+grid.name+"_uc").unbind('mouseup');
	  $("#jqgh_"+grid.name+"_uc").bind("mousedown", function() {
		  moved=false;
	  });
	  $("#jqgh_"+grid.name+"_uc").bind("mousemove", function() {
		  moved=true;
	  });
	  $("#jqgh_"+grid.name+"_uc").bind("mouseup", function() {
		  if(!moved){
			    actualizarSortOrder('uc');
				$(grid.nameJQuery).setGridParam({sortname:'uc'});
				$(grid.nameJQuery).setGridParam({page:1});
				reloadData('S');
	    	}
	  });
	  
	  $("#jqgh_"+grid.name+"_flgNsr").unbind('mousedown');
	  $("#jqgh_"+grid.name+"_flgNsr").unbind('mousemove');
	  $("#jqgh_"+grid.name+"_flgNsr").unbind('mouseup');
	  $("#jqgh_"+grid.name+"_flgNsr").bind("mousedown", function() {
		  moved=false;
	  });
	  $("#jqgh_"+grid.name+"_flgNsr").bind("mousemove", function() {
		  moved=true;
	  });
	  $("#jqgh_"+grid.name+"_flgNsr").bind("mouseup", function() {
		  if(!moved){
			    actualizarSortOrder('flgNsr');
				$(grid.nameJQuery).setGridParam({sortname:'flgNsr'});
				$(grid.nameJQuery).setGridParam({page:1});
				reloadData('S');
	    	}
	  });
	  
	  $("#jqgh_"+grid.name+"_flgNueva").unbind('mousedown');
	  $("#jqgh_"+grid.name+"_flgNueva").unbind('mousemove');
	  $("#jqgh_"+grid.name+"_flgNueva").unbind('mouseup');
	  $("#jqgh_"+grid.name+"_flgNueva").bind("mousedown", function() {
		  moved=false;
	  });
	  $("#jqgh_"+grid.name+"_flgNueva").bind("mousemove", function() {
		  moved=true;
	  });
	  $("#jqgh_"+grid.name+"_flgNueva").bind("mouseup", function() {
		  if(!moved){
			    actualizarSortOrder('flgNueva');
				$(grid.nameJQuery).setGridParam({sortname:'flgNueva'});
				$(grid.nameJQuery).setGridParam({page:1});
				reloadData('S');
	    	}
	  });
	  
	  $("#jqgh_"+grid.name+"_mensaje").unbind('mousedown');
	  $("#jqgh_"+grid.name+"_mensaje").unbind('mousemove');
	  $("#jqgh_"+grid.name+"_mensaje").unbind('mouseup');
	  $("#jqgh_"+grid.name+"_mensaje").bind("mousedown", function() {
		  moved=false;
	  });
	  $("#jqgh_"+grid.name+"_mensaje").bind("mousemove", function() {
		  moved=true;
	  });
	  $("#jqgh_"+grid.name+"_mensaje").bind("mouseup", function() {
		  if(!moved){
			    actualizarSortOrder('mensaje');
				$(grid.nameJQuery).setGridParam({sortname:'mensaje'});
				$(grid.nameJQuery).setGridParam({page:1});
				reloadData('S');
	    	}
	  });
	  
	  $("#jqgh_"+grid.name+"_ccEstr").unbind('mousedown');
	  $("#jqgh_"+grid.name+"_ccEstr").unbind('mousemove');
	  $("#jqgh_"+grid.name+"_ccEstr").unbind('mouseup');
	  $("#jqgh_"+grid.name+"_ccEstr").bind("mousedown", function() {
		  moved=false;
	  });
	  $("#jqgh_"+grid.name+"_ccEstr").bind("mousemove", function() {
		  moved=true;
	  });
	  $("#jqgh_"+grid.name+"_ccEstr").bind("mouseup", function() {
		  if(!moved){
			    actualizarSortOrder('ccEstr');
				$(grid.nameJQuery).setGridParam({sortname:'ccEstr'});
				$(grid.nameJQuery).setGridParam({page:1});
				reloadData('S');
	    	}
	  });
	  
	  if ($("#p26_txt_flgFacing").val()=='S'){
		  $("#jqgh_"+grid.name+"_cc").unbind('mousedown');
		  $("#jqgh_"+grid.name+"_cc").unbind('mousemove');
		  $("#jqgh_"+grid.name+"_cc").unbind('mouseup');
		  $("#jqgh_"+grid.name+"_cc").bind("mousedown", function() {
			  moved=false;
		  });
		  $("#jqgh_"+grid.name+"_cc").bind("mousemove", function() {
			  moved=true;
		  });
		  $("#jqgh_"+grid.name+"_cc").bind("mouseup", function() {
			  if(!moved){
				    actualizarSortOrder('cc');
					$(grid.nameJQuery).setGridParam({sortname:'cc'});
					$(grid.nameJQuery).setGridParam({page:1});
					reloadData('S');
		    	}
		  });
		  
		  $("#jqgh_"+grid.name+"_diasStock").unbind('mousedown');
		  $("#jqgh_"+grid.name+"_diasStock").unbind('mousemove');
		  $("#jqgh_"+grid.name+"_diasStock").unbind('mouseup');
		  $("#jqgh_"+grid.name+"_diasStock").bind("mousedown", function() {
			  moved=false;
		  });
		  $("#jqgh_"+grid.name+"_diasStock").bind("mousemove", function() {
			  moved=true;
		  });
		  $("#jqgh_"+grid.name+"_diasStock").bind("mouseup", function() {
			  if(!moved){
				    actualizarSortOrder('diasStock');
					$(grid.nameJQuery).setGridParam({sortname:'diasStock'});
					$(grid.nameJQuery).setGridParam({page:1});
					reloadData('S');
		    	}
		  });
		  
		  $("#jqgh_"+grid.name+"_pedir").unbind('mousedown');
		  $("#jqgh_"+grid.name+"_pedir").unbind('mousemove');
		  $("#jqgh_"+grid.name+"_pedir").unbind('mouseup');
		  $("#jqgh_"+grid.name+"_pedir").bind("mousedown", function() {
			  moved=false;
		  });
		  $("#jqgh_"+grid.name+"_pedir").bind("mousemove", function() {
			  moved=true;
		  });
		  $("#jqgh_"+grid.name+"_pedir").bind("mouseup", function() {
			  if(!moved){
				    actualizarSortOrder('pedir');
					$(grid.nameJQuery).setGridParam({sortname:'pedir'});
					$(grid.nameJQuery).setGridParam({page:1});
					reloadData('S');
		    	}
		  });
		  
		  $("#jqgh_"+grid.name+"_tipoGama").unbind('mousedown');
		  $("#jqgh_"+grid.name+"_tipoGama").unbind('mousemove');
		  $("#jqgh_"+grid.name+"_tipoGama").unbind('mouseup');
		  $("#jqgh_"+grid.name+"_tipoGama").bind("mousedown", function() {
			  moved=false;
		  });
		  $("#jqgh_"+grid.name+"_tipoGama").bind("mousemove", function() {
			  moved=true;
		  });
		  $("#jqgh_"+grid.name+"_tipoGama").bind("mouseup", function() {
			  if(!moved){
				    actualizarSortOrder('tipoGama');
					$(grid.nameJQuery).setGridParam({sortname:'tipoGama'});
					$(grid.nameJQuery).setGridParam({page:1});
					reloadData('S');
		    	}
		  });
		  
		  $("#jqgh_"+grid.name+"_tipoAprov").unbind('mousedown');
		  $("#jqgh_"+grid.name+"_tipoAprov").unbind('mousemove');
		  $("#jqgh_"+grid.name+"_tipoAprov").unbind('mouseup');
		  $("#jqgh_"+grid.name+"_tipoAprov").bind("mousedown", function() {
			  moved=false;
		  });
		  $("#jqgh_"+grid.name+"_tipoAprov").bind("mousemove", function() {
			  moved=true;
		  });
		  $("#jqgh_"+grid.name+"_tipoAprov").bind("mouseup", function() {
			  if(!moved){
				    actualizarSortOrder('tipoAprov');
					$(grid.nameJQuery).setGridParam({sortname:'tipoAprov'});
					$(grid.nameJQuery).setGridParam({page:1});
					reloadData('S');
		    	}
		  });
		  
		  $("#jqgh_"+grid.name+"_capacidad").unbind('mousedown');
		  $("#jqgh_"+grid.name+"_capacidad").unbind('mousemove');
		  $("#jqgh_"+grid.name+"_capacidad").unbind('mouseup');
		  $("#jqgh_"+grid.name+"_capacidad").bind("mousedown", function() {
			  moved=false;
		  });
		  $("#jqgh_"+grid.name+"_capacidad").bind("mousemove", function() {
			  moved=true;
		  });
		  $("#jqgh_"+grid.name+"_capacidad").bind("mouseup", function() {
			  if(!moved){
				    actualizarSortOrder('capacidad');
					$(grid.nameJQuery).setGridParam({sortname:'capacidad'});
					$(grid.nameJQuery).setGridParam({page:1});
					reloadData('S');
		    	}
		  });
		  
		  $("#jqgh_"+grid.name+"_facingCentro").unbind('mousedown');
		  $("#jqgh_"+grid.name+"_facingCentro").unbind('mousemove');
		  $("#jqgh_"+grid.name+"_facingCentro").unbind('mouseup');
		  $("#jqgh_"+grid.name+"_facingCentro").bind("mousedown", function() {
			  moved=false;
		  });
		  $("#jqgh_"+grid.name+"_facingCentro").bind("mousemove", function() {
			  moved=true;
		  });
		  $("#jqgh_"+grid.name+"_facingCentro").bind("mouseup", function() {
			  if(!moved){
				    actualizarSortOrder('facingCentro');
					$(grid.nameJQuery).setGridParam({sortname:'facingCentro'});
					$(grid.nameJQuery).setGridParam({page:1});
					reloadData('S');
		    	}
		  });
		  
		  $("#jqgh_"+grid.name+"_facingPrevio").unbind('mousedown');
		  $("#jqgh_"+grid.name+"_facingPrevio").unbind('mousemove');
		  $("#jqgh_"+grid.name+"_facingPrevio").unbind('mouseup');
		  $("#jqgh_"+grid.name+"_facingPrevio").bind("mousedown", function() {
			  moved=false;
		  });
		  $("#jqgh_"+grid.name+"_facingPrevio").bind("mousemove", function() {
			  moved=true;
		  });
		  $("#jqgh_"+grid.name+"_facingPrevio").bind("mouseup", function() {
			  if(!moved){
				    actualizarSortOrder('facingPrevio');
					$(grid.nameJQuery).setGridParam({sortname:'facingPrevio'});
					$(grid.nameJQuery).setGridParam({page:1});
					reloadData('S');
		    	}
		  });
		  
		  $("#jqgh_"+grid.name+"_pedible").unbind('mousedown');
		  $("#jqgh_"+grid.name+"_pedible").unbind('mousemove');
		  $("#jqgh_"+grid.name+"_pedible").unbind('mouseup');
		  $("#jqgh_"+grid.name+"_pedible").bind("mousedown", function() {
			  moved=false;
		  });
		  $("#jqgh_"+grid.name+"_pedible").bind("mousemove", function() {
			  moved=true;
		  });
		  $("#jqgh_"+grid.name+"_pedible").bind("mouseup", function() {
			  if(!moved){
				    actualizarSortOrder('pedible');
					$(grid.nameJQuery).setGridParam({sortname:'pedible'});
					$(grid.nameJQuery).setGridParam({page:1});
					reloadData('S');
		    	}
		  });
		  
		  $("#jqgh_"+grid.name+"_temporada").unbind('mousedown');
		  $("#jqgh_"+grid.name+"_temporada").unbind('mousemove');
		  $("#jqgh_"+grid.name+"_temporada").unbind('mouseup');
		  $("#jqgh_"+grid.name+"_temporada").bind("mousedown", function() {
			  moved=false;
		  });
		  $("#jqgh_"+grid.name+"_temporada").bind("mousemove", function() {
			  moved=true;
		  });
		  $("#jqgh_"+grid.name+"_temporada").bind("mouseup", function() {
			  if(!moved){
				    actualizarSortOrder('temporada');
					$(grid.nameJQuery).setGridParam({sortname:'temporada'});
					$(grid.nameJQuery).setGridParam({page:1});
					reloadData('S');
		    	}
		  });
		  
		  $("#jqgh_"+grid.name+"_anioColeccion").unbind('mousedown');
		  $("#jqgh_"+grid.name+"_anioColeccion").unbind('mousemove');
		  $("#jqgh_"+grid.name+"_anioColeccion").unbind('mouseup');
		  $("#jqgh_"+grid.name+"_anioColeccion").bind("mousedown", function() {
			  moved=false;
		  });
		  $("#jqgh_"+grid.name+"_anioColeccion").bind("mousemove", function() {
			  moved=true;
		  });
		  $("#jqgh_"+grid.name+"_anioColeccion").bind("mouseup", function() {
			  if(!moved){
				    actualizarSortOrder('anioColeccion');
					$(grid.nameJQuery).setGridParam({sortname:'anioColeccion'});
					$(grid.nameJQuery).setGridParam({page:1});
					reloadData('S');
		    	}
		  });
		  
		  $("#jqgh_"+grid.name+"_modeloProveedor").unbind('mousedown');
		  $("#jqgh_"+grid.name+"_modeloProveedor").unbind('mousemove');
		  $("#jqgh_"+grid.name+"_modeloProveedor").unbind('mouseup');
		  $("#jqgh_"+grid.name+"_modeloProveedor").bind("mousedown", function() {
			  moved=false;
		  });
		  $("#jqgh_"+grid.name+"_modeloProveedor").bind("mousemove", function() {
			  moved=true;
		  });
		  $("#jqgh_"+grid.name+"_modeloProveedor").bind("mouseup", function() {
			  if(!moved){
				    actualizarSortOrder('modeloProveedor');
					$(grid.nameJQuery).setGridParam({sortname:'modeloProveedor'});
					$(grid.nameJQuery).setGridParam({page:1});
					reloadData('S');
		    	}
		  });
		  
		  $("#jqgh_"+grid.name+"_talla").unbind('mousedown');
		  $("#jqgh_"+grid.name+"_talla").unbind('mousemove');
		  $("#jqgh_"+grid.name+"_talla").unbind('mouseup');
		  $("#jqgh_"+grid.name+"_talla").bind("mousedown", function() {
			  moved=false;
		  });
		  $("#jqgh_"+grid.name+"_talla").bind("mousemove", function() {
			  moved=true;
		  });
		  $("#jqgh_"+grid.name+"_talla").bind("mouseup", function() {
			  if(!moved){
				    actualizarSortOrder('talla');
					$(grid.nameJQuery).setGridParam({sortname:'talla'});
					$(grid.nameJQuery).setGridParam({page:1});
					reloadData('S');
		    	}
		  });
		  
		  $("#jqgh_"+grid.name+"_color").unbind('mousedown');
		  $("#jqgh_"+grid.name+"_color").unbind('mousemove');
		  $("#jqgh_"+grid.name+"_color").unbind('mouseup');
		  $("#jqgh_"+grid.name+"_color").bind("mousedown", function() {
			  moved=false;
		  });
		  $("#jqgh_"+grid.name+"_color").bind("mousemove", function() {
			  moved=true;
		  });
		  $("#jqgh_"+grid.name+"_color").bind("mouseup", function() {
			  if(!moved){
				    actualizarSortOrder('color');
					$(grid.nameJQuery).setGridParam({sortname:'color'});
					$(grid.nameJQuery).setGridParam({page:1});
					reloadData('S');
		    	}
		  });
		  
		  $("#jqgh_"+grid.name+"_lote").unbind('mousedown');
		  $("#jqgh_"+grid.name+"_lote").unbind('mousemove');
		  $("#jqgh_"+grid.name+"_lote").unbind('mouseup');
		  $("#jqgh_"+grid.name+"_lote").bind("mousedown", function() {
			  moved=false;
		  });
		  $("#jqgh_"+grid.name+"_lote").bind("mousemove", function() {
			  moved=true;
		  });
		  $("#jqgh_"+grid.name+"_lote").bind("mouseup", function() {
			  if(!moved){
				    actualizarSortOrder('lote');
					$(grid.nameJQuery).setGridParam({sortname:'lote'});
					$(grid.nameJQuery).setGridParam({page:1});
					reloadData('S');
		    	}
		  });
		  
		  $("#jqgh_"+grid.name+"_tempColNumOrden").unbind('mousedown');
		  $("#jqgh_"+grid.name+"_tempColNumOrden").unbind('mousemove');
		  $("#jqgh_"+grid.name+"_tempColNumOrden").unbind('mouseup');
		  $("#jqgh_"+grid.name+"_tempColNumOrden").bind("mousedown", function() {
			  moved=false;
		  });
		  $("#jqgh_"+grid.name+"_tempColNumOrden").bind("mousemove", function() {
			  moved=true;
		  });
		  $("#jqgh_"+grid.name+"_tempColNumOrden").bind("mouseup", function() {
			  if(!moved){
				    actualizarSortOrder('tempColNumOrden');
					$(grid.nameJQuery).setGridParam({sortname:'tempColNumOrden'});
					$(grid.nameJQuery).setGridParam({page:1});
					reloadData('S');
		    	}
		  });
		  
		}else if ($("#p26_txt_flgCapacidad").val()=='S'){
			
			  $("#jqgh_"+grid.name+"_capacidad").unbind('mousedown');
			  $("#jqgh_"+grid.name+"_capacidad").unbind('mousemove');
			  $("#jqgh_"+grid.name+"_capacidad").unbind('mouseup');
			  $("#jqgh_"+grid.name+"_capacidad").bind("mousedown", function() {
				  moved=false;
			  });
			  $("#jqgh_"+grid.name+"_capacidad").bind("mousemove", function() {
				  moved=true;
			  });
			  $("#jqgh_"+grid.name+"_capacidad").bind("mouseup", function() {
				  if(!moved){
					    actualizarSortOrder('capacidad');
						$(grid.nameJQuery).setGridParam({sortname:'capacidad'});
						$(grid.nameJQuery).setGridParam({page:1});
						reloadData('S');
			    	}
			  });
			 
			  $("#jqgh_"+grid.name+"_diasStock").unbind('mousedown');
			  $("#jqgh_"+grid.name+"_diasStock").unbind('mousemove');
			  $("#jqgh_"+grid.name+"_diasStock").unbind('mouseup');
			  $("#jqgh_"+grid.name+"_diasStock").bind("mousedown", function() {
				  moved=false;
			  });
			  $("#jqgh_"+grid.name+"_diasStock").bind("mousemove", function() {
				  moved=true;
			  });
			  $("#jqgh_"+grid.name+"_diasStock").bind("mouseup", function() {
				  if(!moved){
					    actualizarSortOrder('diasStock');
						$(grid.nameJQuery).setGridParam({sortname:'diasStock'});
						$(grid.nameJQuery).setGridParam({page:1});
						reloadData('S');
			    	}
			  });

			  
			  $("#jqgh_"+grid.name+"_vidaUtil").unbind('mousedown');
			  $("#jqgh_"+grid.name+"_vidaUtil").unbind('mousemove');
			  $("#jqgh_"+grid.name+"_vidaUtil").unbind('mouseup');
			  $("#jqgh_"+grid.name+"_vidaUtil").bind("mousedown", function() {
				  moved=false;
			  });
			  $("#jqgh_"+grid.name+"_vidaUtil").bind("mousemove", function() {
				  moved=true;
			  });
			  $("#jqgh_"+grid.name+"_vidaUtil").bind("mouseup", function() {
				  if(!moved){
					    actualizarSortOrder('vidaUtil');
						$(grid.nameJQuery).setGridParam({sortname:'vidaUtil'});
						$(grid.nameJQuery).setGridParam({page:1});
						reloadData('S');
			    	}
			  });
			  
		}else{
			  $("#jqgh_"+grid.name+"_lmin").unbind('mousedown');
			  $("#jqgh_"+grid.name+"_lmin").unbind('mousemove');
			  $("#jqgh_"+grid.name+"_lmin").unbind('mouseup');
			  $("#jqgh_"+grid.name+"_lmin").bind("mousedown", function() {
				  moved=false;
			  });
			  $("#jqgh_"+grid.name+"_lmin").bind("mousemove", function() {
				  moved=true;
			  });
			  $("#jqgh_"+grid.name+"_lmin").bind("mouseup", function() {
				  if(!moved){
					    actualizarSortOrder('lmin');
						$(grid.nameJQuery).setGridParam({sortname:'lmin'});
						$(grid.nameJQuery).setGridParam({page:1});
						reloadData('S');
			    	}
			  });
			  
			  $("#jqgh_"+grid.name+"_lsf").unbind('mousedown');
			  $("#jqgh_"+grid.name+"_lsf").unbind('mousemove');
			  $("#jqgh_"+grid.name+"_lsf").unbind('mouseup');
			  $("#jqgh_"+grid.name+"_lsf").bind("mousedown", function() {
				  moved=false;
			  });
			  $("#jqgh_"+grid.name+"_lsf").bind("mousemove", function() {
				  moved=true;
			  });
			  $("#jqgh_"+grid.name+"_lsf").bind("mouseup", function() {
				  if(!moved){
					    actualizarSortOrder('lsf');
						$(grid.nameJQuery).setGridParam({sortname:'lsf'});
						$(grid.nameJQuery).setGridParam({page:1});
						reloadData('S');
			    	}
			  });
			  
			  $("#jqgh_"+grid.name+"_sfm").unbind('mousedown');
			  $("#jqgh_"+grid.name+"_sfm").unbind('mousemove');
			  $("#jqgh_"+grid.name+"_sfm").unbind('mouseup');
			  $("#jqgh_"+grid.name+"_sfm").bind("mousedown", function() {
				  moved=false;
			  });
			  $("#jqgh_"+grid.name+"_sfm").bind("mousemove", function() {
				  moved=true;
			  });
			  $("#jqgh_"+grid.name+"_sfm").bind("mouseup", function() {
				  if(!moved){
					    actualizarSortOrder('sfm');
						$(grid.nameJQuery).setGridParam({sortname:'sfm'});
						$(grid.nameJQuery).setGridParam({page:1});
						reloadData('S');
			    	}
			  });
			  
			  $("#jqgh_"+grid.name+"_coberturaSfm").unbind('mousedown');
			  $("#jqgh_"+grid.name+"_coberturaSfm").unbind('mousemove');
			  $("#jqgh_"+grid.name+"_coberturaSfm").unbind('mouseup');
			  $("#jqgh_"+grid.name+"_coberturaSfm").bind("mousedown", function() {
				  moved=false;
			  });
			  $("#jqgh_"+grid.name+"_coberturaSfm").bind("mousemove", function() {
				  moved=true;
			  });
			  $("#jqgh_"+grid.name+"_coberturaSfm").bind("mouseup", function() {
				  if(!moved){
					    actualizarSortOrder('coberturaSfm');
						$(grid.nameJQuery).setGridParam({sortname:'coberturaSfm'});
						$(grid.nameJQuery).setGridParam({page:1});
						reloadData('S');
			    	}
			  });
			  
			  $("#jqgh_"+grid.name+"_ventaMedia").unbind('mousedown');
			  $("#jqgh_"+grid.name+"_ventaMedia").unbind('mousemove');
			  $("#jqgh_"+grid.name+"_ventaMedia").unbind('mouseup');
			  $("#jqgh_"+grid.name+"_ventaMedia").bind("mousedown", function() {
				  moved=false;
			  });
			  $("#jqgh_"+grid.name+"_ventaMedia").bind("mousemove", function() {
				  moved=true;
			  });
			  $("#jqgh_"+grid.name+"_ventaMedia").bind("mouseup", function() {
				  if(!moved){
					    actualizarSortOrder('ventaMedia');
						$(grid.nameJQuery).setGridParam({sortname:'ventaMedia'});
						$(grid.nameJQuery).setGridParam({page:1});
						reloadData('S');
			    	}
			  });
			  
			  $("#jqgh_"+grid.name+"_ventaAnticipada").unbind('mousedown');
			  $("#jqgh_"+grid.name+"_ventaAnticipada").unbind('mousemove');
			  $("#jqgh_"+grid.name+"_ventaAnticipada").unbind('mouseup');
			  $("#jqgh_"+grid.name+"_ventaAnticipada").bind("mousedown", function() {
				  moved=false;
			  });
			  $("#jqgh_"+grid.name+"_ventaAnticipada").bind("mousemove", function() {
				  moved=true;
			  });
			  $("#jqgh_"+grid.name+"_ventaAnticipada").bind("mouseup", function() {
				  if(!moved){
					    actualizarSortOrder('ventaAnticipada');
						$(grid.nameJQuery).setGridParam({sortname:'ventaAnticipada'});
						$(grid.nameJQuery).setGridParam({page:1});
						reloadData('S');
			    	}
			  });
			  
			  $("#jqgh_"+grid.name+"_diasStock").unbind('mousedown');
			  $("#jqgh_"+grid.name+"_diasStock").unbind('mousemove');
			  $("#jqgh_"+grid.name+"_diasStock").unbind('mouseup');
			  $("#jqgh_"+grid.name+"_diasStock").bind("mousedown", function() {
				  moved=false;
			  });
			  $("#jqgh_"+grid.name+"_diasStock").bind("mousemove", function() {
				  moved=true;
			  });
			  $("#jqgh_"+grid.name+"_diasStock").bind("mouseup", function() {
				  if(!moved){
					    actualizarSortOrder('diasStock');
						$(grid.nameJQuery).setGridParam({sortname:'diasStock'});
						$(grid.nameJQuery).setGridParam({page:1});
						reloadData('S');
			    	}
			  });
			  
			  $("#jqgh_"+grid.name+"_vidaUtil").unbind('mousedown');
			  $("#jqgh_"+grid.name+"_vidaUtil").unbind('mousemove');
			  $("#jqgh_"+grid.name+"_vidaUtil").unbind('mouseup');
			  $("#jqgh_"+grid.name+"_vidaUtil").bind("mousedown", function() {
				  moved=false;
			  });
			  $("#jqgh_"+grid.name+"_vidaUtil").bind("mousemove", function() {
				  moved=true;
			  });
			  $("#jqgh_"+grid.name+"_vidaUtil").bind("mouseup", function() {
				  if(!moved){
					    actualizarSortOrder('vidaUtil');
						$(grid.nameJQuery).setGridParam({sortname:'vidaUtil'});
						$(grid.nameJQuery).setGridParam({page:1});
						reloadData('S');
			    	}
			  });
		}
	 
}


function mostrarHuecos(){
	
	var valorArea = null;
	var codArea = null;
	var valorSeccion = null;
	var codSeccion = null;
	var valorCategoria = null;
	var codCategoria = null;
	var valorSubcategoria = null;
	var codSubcategoria = null;
	var valorSegmento = null;
	var codSegmento = null;
	
	//Capturo el valor de area
	if( $("#p26_cmb_area").combobox('getValue')!="null" && $("#p26_cmb_area").combobox('getValue')!=null){
		valorArea = $("#p26_cmb_area").combobox('getValue').split('*');
		codArea = valorArea[0];
	}
	
	//Si hay centro y area de electro seleccionadas consulto para ver si tiene configurados los huecos 
	if (centerName!=null && centerName!='' && codArea!=null && (codArea=="5")){
		
		if( $("#p26_cmb_seccion").combobox('getValue')!="null" && $("#p26_cmb_seccion").combobox('getValue')!=null){
			valorSeccion = $("#p26_cmb_seccion").combobox('getValue').split('*');
			codSeccion = valorSeccion[0];
		}
		if( $("#p26_cmb_categoria").combobox('getValue')!="null" && $("#p26_cmb_categoria").combobox('getValue')!=null){
			valorCategoria = $("#p26_cmb_categoria").combobox('getValue').split('*');
			codCategoria = valorCategoria[0];
		}
		if( $("#p26_cmb_subcategoria").combobox('getValue')!="null" && $("#p26_cmb_subcategoria").combobox('getValue')!=null){
			valorSubcategoria = $("#p26_cmb_subcategoria").combobox('getValue').split('*');
			codSubcategoria = valorSubcategoria[0];
		}
		if( $("#p26_cmb_segmento").combobox('getValue')!="null" && $("#p26_cmb_segmento").combobox('getValue')!=null){
			valorSegmento = $("#p26_cmb_segmento").combobox('getValue').split('*');
			codSegmento = valorSegmento[0];
		}
		
		var recordSfm = new VArtSfm (null, null, null, null, codArea, null, codSeccion, null, codCategoria,
				null, codSubcategoria, null, codSegmento, null,null,
				null, null, null, null, null, null,	null, null, null, null, null, null);
		
		
		var objJson = $.toJSON(recordSfm.prepareVArtSfmToJsonObject());
		

		$.ajax({
			type : 'POST',
			data : objJson,
			url : './sfmCapacidad/getHuecos.do?',
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			cache : false,
			success : function(data) {
				if (data != null) {
					mostrarNumHuecos(data);
				}else{
					ocultarHuecos();
				}

			},
			error : function(xhr, status, error) {
				handleError(xhr, status, error, locale);
			}
		});	
		
		
	}
	else
		ocultarHuecos();
	
}

/*
 * P-50987
 * Gestion de mensaje para los huecos. Para area 5 electro.
 * Se muestra aviso cuando la busqueda dinamica de huecos devuelve registros pero no se ha seleccionado segmento
 * Se muestra numero de huecos cuando la busqueda dinamica de huecos devuelve registros y esta seleccionado segmento.
 * Cuando se guarda, se ha de mostrar mensaje de alerta si se ha seleccionado hasta segmento y el sumatorio de los campos facing es menor que el de numero de huecos.
 * No se mostraran mensajes si no existen registros.
 * @BICUGUAL 
 */
function mostrarAvisoHuecos(){
	huecosFacing=null;
	
	$("#p26_Area_mensajes").show().css('display', 'inline-block');
	
	$("#p26_mensajeHuecos").show().css('display', 'inline-block');
	$("#p26_mensajeHuecos img").show();
	$("#p26_mensajeHuecos .warn").show().css('display', 'inline-block');
	$("#p26_mensajeHuecos .info").hide();
}

function mostrarNumHuecos(numHuecos){
	var mensajeHuecos;
	huecosFacing=numHuecos;
	
	if ($("#p26_cmb_subcategoria").combobox('getValue') == null) {
		mensajeHuecos = avisoHuecos;
	} else {
		if ($("#p26_cmb_segmento").combobox('getValue') == null) {
			mensajeHuecos = huecosSubcategoria.replace("#huecos#", numHuecos);
		} else {
			//mensajeHuecos = huecosSegmento.replace("#huecos#", $.formatNumber(numHuecos,{format:"0.000",locale:"es"}));
			mensajeHuecos = huecosSegmento.replace("#huecos#", numHuecos);
		}
	}
	
	$("#num_huecos_lbl").html(mensajeHuecos);
	
	$("#p26_Area_mensajes").show().css('display', 'inline-block');
	$("#p26_mensajeHuecos").show().css('display', 'inline-block');
	

	
}
function ocultarHuecos(){
	
	$("#p26_mensajeHuecos").hide();

};

function funcionTest(rData) {
	var fechaSfm =  devuelveDate(rData['fechaSfmDDMMYYYY'].substring(4) +"-"+ rData['fechaSfmDDMMYYYY'].substring(2,4)  +"-"+ rData['fechaSfmDDMMYYYY'].substring(0,2)).getTime();
	
	//Obtenemos la fecha actual
	var fechaActual = new Date().getTime();
	
	//Obtenemos  la diferencia entre las dos fechas 
	var c = 24*60*60*1000;
	var diffDays = Math.round((fechaActual - fechaSfm)/(c));
	
	if (diffDays <= 21) {
	//Si la diferencia entre fechas es menor o igual que 21, entonces el limite superior se pondra a ""
		return true;
		
	}else{
		return false;
	}
}

/* P-55928
 * Utilizo la funcion para mostrar imagenes en todos los tipos de grid
 * @BICUGUAL 
 */
function generateTooltip(rowId, rowData){
	
	
/* Comento para modificacion P-55928 */
//	if (rowData.flgFoto == "S"){
//		var idArticulo = jQuery(grid.nameJQuery).jqGrid('getCell',rowId,'codArticulo');
//		var div = $("<div>");
//		var idImg = "img_"+rowId+"_fotoTooltip"
//		var img = $('<img id="'+idImg+'">'); //Equivalent: $(document.createElement('img'))
//		var url = "./welcome/getImage.do?codArticulo="+idArticulo+"&flgControlCaprabo=S";
//		img.attr('src',url);
//		img.addClass("p26_img_tooltip");
//		img.appendTo(div);
//		
//		if (rowData.flgSfmFijo !='B'){
//			var campoFacing = $(grid.nameJQuery+" tbody #"+ rowId + " td[aria-describedby='"+grid.name+"_facingCentro'] #"+rowId+"_facingCentro ");
//			
//			campoFacing.addClass("contieneTooltipFoto");
//			campoFacing.attr("title",div.html());
//		} else {
//			var facingCentro = jQuery(grid.nameJQuery).getCell(rowId,'facingCentro');
//			jQuery(grid.nameJQuery).setCell(rowId,'facingCentro',facingCentro, 'contieneTooltipFoto',{ 'title': div.html()});
//		}
//		
//		$(".contieneTooltipFoto").tooltip({
//			position: {
//				my: "left top",
//	            at: "right+5 top-5"	  
//	        }
//	    });
//	}
	
	
	/* Inicio modificacion P-55928 */	
	if ($("#p26_txt_flgFacing").val()=='S'){
		
		if (rowData.flgFoto == "S"){
			var idArticulo = jQuery(grid.nameJQuery).jqGrid('getCell',rowId,'codArticulo');
			var div = $("<div>");
			var idImg = "img_"+rowId+"_fotoTooltip"
			var img = $('<img id="'+idImg+'">'); //Equivalent: $(document.createElement('img'))
			var url = "./welcome/getImage.do?codArticulo="+idArticulo+"&flgControlCaprabo=S";
			img.attr('src',url);
			img.addClass("p26_img_tooltip");
			img.appendTo(div);
			img.attr("onerror", "redirigirLogin()");
			
			if (rowData.flgSfmFijo !='B'){
				var campoFacing = $(grid.nameJQuery+" tbody #"+ rowId + " td[aria-describedby='"+grid.name+"_facingCentro'] #"+rowId+"_facingCentro ");
				
				campoFacing.addClass("contieneTooltipFoto");
				campoFacing.attr("title",div.html());
			} else {
				var facingCentro = jQuery(grid.nameJQuery).getCell(rowId,'facingCentro');
				jQuery(grid.nameJQuery).setCell(rowId,'facingCentro',facingCentro, 'contieneTooltipFoto',{ 'title': div.html()});
			}
			
			$(".contieneTooltipFoto").tooltip({
				position: {
					my: "left top",
		          //  at: "right+5 top-5"	
					 at: "right+5 top+23"
		        }
		    });
		}
		
	}
	
	else if ($("#p26_txt_flgCapacidad").val()=='S'){
		
		if (rowData.flgFoto == "S"){
			var idArticulo = jQuery(grid.nameJQuery).jqGrid('getCell',rowId,'codArticulo');
			 
			var div = $("<div>");
			var idImg = "img_"+rowId+"_fotoTooltip"
			var img = $('<img id="'+idImg+'">'); //Equivalent: $(document.createElement('img'))
			var url = "./welcome/getImage.do?codArticulo="+idArticulo+"&flgControlCaprabo=S";
			img.attr('src',url);
			img.addClass("p26_img_tooltip");
			img.appendTo(div);
			img.attr("onerror", "redirigirLogin()");
			
			if (rowData.flgSfmFijo !='B'){				
				var campoCapacidad = $(grid.nameJQuery+" tbody #"+ rowId + " td[aria-describedby='"+grid.name+"_capacidad'] #"+rowId+"_capacidad ");
				campoCapacidad.addClass("contieneTooltipFoto");
				campoCapacidad.attr("title",div.html());
				
			} else {
				var capacidad = jQuery(grid.nameJQuery).getCell(rowId,'capacidad');
				jQuery(grid.nameJQuery).setCell(rowId,'capacidad',capacidad, 'contieneTooltipFoto',{ 'title': div.html()});
			}
			
			$(".contieneTooltipFoto").tooltip({
				position: {
					my: "left top",
					 at: "right+5 top+23"
		        }
		    });
		}
		
	}
	else{
		
		if (rowData.flgFoto == "S"){
			var idArticulo = jQuery(grid.nameJQuery).jqGrid('getCell',rowId,'codArticulo');
			var div = $("<div>");
			var idImg = "img_"+rowId+"_fotoTooltip"
			var img = $('<img id="'+idImg+'">'); //Equivalent: $(document.createElement('img'))
			var url = "./welcome/getImage.do?codArticulo="+idArticulo+"&flgControlCaprabo=S";
			img.attr('src',url);
			img.addClass("p26_img_tooltip");
			img.appendTo(div);
			img.attr("onerror", "redirigirLogin()");
			
			if (rowData.flgSfmFijo !='B'){				
				var campoSfm = $(grid.nameJQuery+" tbody #"+ rowId + " td[aria-describedby='"+grid.name+"_sfm'] #"+rowId+"_sfm ");
				campoSfm.addClass("contieneTooltipFoto");
				campoSfm.attr("title",div.html());
				
				var campoCoberturaSfm = $(grid.nameJQuery+" tbody #"+ rowId + " td[aria-describedby='"+grid.name+"_coberturaSfm'] #"+rowId+"_coberturaSfm ");
				campoCoberturaSfm.addClass("contieneTooltipFoto");
				campoCoberturaSfm.attr("title",div.html());
				
			} else {
				var sfm = jQuery(grid.nameJQuery).getCell(rowId,'sfm');
				jQuery(grid.nameJQuery).setCell(rowId,'sfm',sfm, 'contieneTooltipFoto',{ 'title': div.html()});
				var coberturaSfm = jQuery(grid.nameJQuery).getCell(rowId,'coberturaSfm');
				jQuery(grid.nameJQuery).setCell(rowId,'coberturaSfm',sfm, 'contieneTooltipFoto',{ 'title': div.html()});
			}
						
			$(".contieneTooltipFoto").tooltip({
				position: {
					my: "left top",
					at: "right+5 top+23"
		        }
		    });
		}
	}
	/*Fin modificacion P-55928 */
	
}



function redirigirLogin() {
	window.location='./login.do';
}

function exportExcel(){
var messageVal=findValidation();
if (messageVal!=null){
	createAlert(replaceSpecialCharacters(messageVal), "ERROR");
} else {
	if ($("#p26_txt_flgFacing").val()=='S'){
		
		if ($("#p26_txt_flgFacingCapacidad").val()=='S'){
			jQuery(gridFac.nameJQuery).jqGrid('hideCol', ["facingPrevio"]);
			jQuery(gridFac.nameJQuery).jqGrid('showCol', ["capacidad"]);		
		} else {
			jQuery(gridFac.nameJQuery).jqGrid('hideCol', ["capacidad"]);
			jQuery(gridFac.nameJQuery).jqGrid('showCol', ["facingPrevio"]);									
		}
		//Mirar si es textil, en este caso habra que cargar el grid de textil
		if ($("input[name='p26_rad_tipoFiltro']:checked").val() == "1"){//Estructura comercial
			//Mirar la selección del combo de Area
			var valor = $("#p26_cmb_area").val().split('*');
			var cod = valor[0];
			if (cod=="3" ){
				grid = gridFacTextil;
			} else {
				grid = gridFac;
			}
		} else {
			grid = gridFac;
		}

	}else if ($("#p26_txt_flgCapacidad").val()=='S'){
		grid = gridCap;
	}else{
		grid = gridSfm;
	}
	
	var colModel = $(grid.nameJQuery).jqGrid("getGridParam", "colModel");      
	var myColumns = new Array();
	var myColumnsWidth = new Array();
	 
	var j=0;		  
	$.each(colModel, function(i) {
		  if (colModel[i].name!="rn" && colModel[i].name!== 'subgrid' && !colModel[i].hidden ){
			    myColumns[j]=colModel[i].name;
			    myColumnsWidth[j]=colModel[i].width;
			    j++;		    	
		  }
	 });

	var form = "<form name='csvexportform' action='sfmCapacidad/exportGrid.do'  accept-charset='ISO-8859-1' method='get'>";	
	form = form + "<input type='hidden' name='model' value='"+myColumns+"'>";
	form = form + "<input type='hidden' name='widths' value='"+myColumnsWidth+"'>";
	form = form + "<input type='hidden' name='centro' value='"+$("#centerId").val()+"'>";
	if ($("input[name='p26_rad_tipoFiltro']:checked").val() == "1"){
			if ($("#p26_cmb_area").combobox('getValue')!="" && $("#p26_cmb_area").combobox('getValue')!=null  ){
				valorArea = $("#p26_cmb_area").combobox('getValue').split('*');
				form = form + "<input type='hidden' name='grupo1' value='"+valorArea[0]+"'>";
			}	
			if ($("#p26_cmb_seccion").combobox('getValue')!="" && $("#p26_cmb_seccion").combobox('getValue')!=null  ){
				valorSeccion = $("#p26_cmb_seccion").combobox('getValue').split('*');
				form = form + "<input type='hidden' name='grupo2' value='"+valorSeccion[0]+"'>";
			}	
			if ($("#p26_cmb_categoria").combobox('getValue')!="" && $("#p26_cmb_categoria").combobox('getValue')!=null  ){
				valorCategoria = $("#p26_cmb_categoria").combobox('getValue').split('*');
				form = form + "<input type='hidden' name='grupo3' value='"+valorCategoria[0]+"'>";
			}	
			if ($("#p26_cmb_subcategoria").combobox('getValue')!="" && $("#p26_cmb_subcategoria").combobox('getValue')!=null  ){
				valorSubCategoria = $("#p26_cmb_subcategoria").combobox('getValue').split('*');
				form = form + "<input type='hidden' name='grupo4' value='"+valorSubCategoria[0]+"'>";
			}
			if ($("#p26_cmb_segmento").combobox('getValue')!="" && $("#p26_cmb_segmento").combobox('getValue')!=null ){
				valorSegmento = $("#p26_cmb_segmento").combobox('getValue').split('*');
				form = form + "<input type='hidden' name='grupo5' value='"+valorSegmento[0]+"'>";
			}
			if ($("#p26_div_mmc").is(':visible') && $("#p26_cmb_mmc").combobox('getValue')!=null ){
				form = form + "<input type='hidden' name='marcaMaestroCentro' value='"+ $("#p26_cmb_mmc").val()+"'>";
			}	
			if ($("#p26_cmb_pedir").combobox('getValue')!=null ){
				form = form + "<input type='hidden' name='pedir' value='"+ $("#p26_cmb_pedir").val()+"'>";
			}	
			if ($("#p26_div_loteSN").is(':visible') && $("#p26_cmb_loteSN").combobox('getValue')!=null ){
				form = form + "<input type='hidden' name='loteSN' value='"+ $("#p26_cmb_loteSN").val()+"'>";
			}	
		}
	if ($("input[name='p26_rad_tipoFiltro']:checked").val() != "1"){
		if ($("#p26_fld_referencia").val()!="" && $("#p26_fld_referencia").val()!=null ){
			form = form + "<input type='hidden' name='codigoArticulo' value='"+$("#p26_fld_referencia").val()+"'>";
		}
	}
	//Gestionar tipoListado
	var tipoListado;
	if ($("#p26_txt_flgFacing").val()=='S'){ //Textil también será Facing
		tipoListado = 'FAC';
	}else if ($("#p26_txt_flgCapacidad").val()=='S'){
		tipoListado = 'CAP';
	}else{
		tipoListado = 'SFM';
	}
	form = form + "<input type='hidden' name='tipoListado' value='"+ tipoListado +"'>";
	
	form = form + "</form><script>document.csvexportform.submit();</script>";
	Show_Popup(form);	
 }
}	

