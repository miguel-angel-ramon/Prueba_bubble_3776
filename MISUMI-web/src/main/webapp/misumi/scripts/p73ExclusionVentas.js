var P73_AREA_CONST="area";
var P73_RESET_CONST="reset";
var P73_SECTION_CONST="section";
var P73_CATEGORY_CONST="category";
var P73_SUBCATEGORY_CONST="subcategory";
var P73_SEGMENT_CONST="segment";
var p73Guardar = null;
var p73Volver = null;
var grid=null;
var p73GridTitle = null;
var emptyRecords = null;
var estructuraRequired = null;
var referenceRequired = null;
var tableFilter =  null;
var p73TitBloque1Estructura = null;
var p73TitBloque1Referencia = null;
var p73Seccion = null;
var p73Categoria = null;
var p73Subcategoria = null;
var p73Segmento = null;
var p73emptyListaSeleccionados;
var p73emptyListaSeleccionadosNuevo;
var p73SeleccionadosEstructuraTotal = new Array();
var p73SeleccionadosNuevoTotal = new Array();
var p73Seleccionados = new Array();
var p73SeleccionadosTotal = new Array();
var p73iconoGuardado=null;
var p73saveResultOK=null;
var p73saveResultError= null;

$(document).ready(function(){
	$(document).on('CargadoCentro', function(e) { 
		loadP73Literales(locale);
	});


	$("#p73_AreaNuevo").keydown(function(event) {
		if(event.which == 13) {
			save();
		}        
    });
});

function initializeScreenP73(){
	controlCentro();
	p73CrearCalendario();
	p73cleanFilterSelection(P73_RESET_CONST);
	events_p73_rad_tipoFiltro();
	events_p73_btn_guardar();
	events_p73_btn_borrar();
	loadP73(locale);
	
	//Nos posicionamos en el campo de referencia al aceptar la inserción de datos.
	$("#p03_btn_aceptar").bind("click", function(e) {
		if ($("#p73_fld_referencia").is(":visible")) 
		{
			$("#p73_fld_referencia").focus();
			$("#p73_fld_referencia").select();
			e.stopPropagation();
		}
	 }); 
}

function resetWindow(){
	$( "#centerName" ).bind('focus', function() {
		clearSessionCenter();
    });
}

function clearSessionCenter(){
	$.ajax({
		type : 'GET',
		url : './exclusionVentas/clearSessionCenter.do',
		cache : false,
		dataType : "json",
		success : function(data) {				
			window.location='./welcome.do';
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});	
}

function loadP73Literales(locale){
	var jqxhr = $.getJSON('./misumi/resources/p73ExclusionVentas/p73exclusionVentas_' + locale + '.json',
			function(data) {
											
			})
			.success(function(data) {
				p73Guardar = data.p73Guardar;
				p73Borrar = data.p73Borrar;
				p73GridTitle = data.p73GridTitle;
				emptyRecords = data.emptyRecords;
				estructuraRequired = data.estructuraRequired;
				referenceRequired = data.referenceRequired;
				tableFilter =  data.tableFilter;
				p73TitBloque1Estructura = data.p73TitBloque1Estructura;
				p73TitBloque1Referencia = data.p73TitBloque1Referencia;
				p73Seccion = data.p73Seccion;
				p73Categoria = data.p73Categoria;
				p73Subcategoria = data.p73Subcategoria;
				p73Segmento = data.p73Segmento;
				p73emptyListaSeleccionados = data.p73emptyListaSeleccionados;
				p73emptyListaSeleccionadosNuevo = data.p73emptyListaSeleccionadosNuevo;
				p73iconoGuardado=data.p73iconoGuardado;
				p73saveResultOK =data.p73saveResultOK;
				p73saveResultError=data.p73saveResultError;
				initializeScreenP73();
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
         });
}

function p73CrearCalendario(){
	$("#codCentroCalendario").val($("#centerId").val());
	$("#recargarParametrosCalendario").val("S");
	$("#cargadoDSCalendario").val("N");
	
	$.datepicker.setDefaults($.datepicker.regional['es']);
	$( "#p73_fechaDatePicker" ).datepicker({
        onSelect: function(dateText, inst) {
        	//Pendiente de guardado de fecha en variable
       }
   });
}

function p73load_cmbArea(){	
	
	var options = "";
	var optionNull = "";
	var vAgruComerRef=new VAgruComerRef("I1");
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
					options = options + "<option value='" + data[i].grupo1 + "'>" + p73formatDescripcionCombo(data[i].grupo1, data[i].descripcion) + "</option>"; 
			   }
			   $("select#p73_cmb_area").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
	$("#p73_cmb_area").combobox({
        selected: function(event, ui) {
        	if ( ui.item.value!="" && ui.item.value!="null" ) {
	           if ($("#p73_cmb_area").val()!=null){
	        	   
	        	   p73load_cmbSeccion();
	        	   var result = p73cleanFilterSelection(P73_AREA_CONST);
	           }
        	}else{
        		  var result= p73cleanFilterSelection(P73_RESET_CONST);
        	}
        }  ,
   
	    changed: function(event, ui) { 
		    if (ui.item==null || ui.item.value!="" || ui.item.value!="null"){
			   result=p73cleanFilterSelection(P73_RESET_CONST);
		    }	 
	    }
     }); 
	
	$("#p73_cmb_area").combobox('autocomplete',optionNull);
	$("#p73_cmb_area").combobox('comboautocomplete',null);
	limpiarErroresCombos();
}

function p73load_cmbSeccion(){	
	var options = "";
	var optionNull = "";
	var registrosEstructura = "";
	$("#p73_cmb_seccion").combobox(null);
	var vAgruComerRef=new VAgruComerRef("I2",$("#p73_cmb_area").val());
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
					options = options + "<option value='" + data[i].grupo2 + "'>" + p73formatDescripcionCombo(data[i].grupo2, data[i].descripcion) + "</option>";
					registrosEstructura = registrosEstructura + "<tr><td class=\"p73_tdCheck\"><input type=\"checkbox\" class=\"case\" name=\"case\" onclick=\"controlCheckSeccion(this)\" value=\""+data[i].grupo2+"\"/></td><td class=\"p73_tdDato\">" + p73formatDescripcionCombo(data[i].grupo2, data[i].descripcion) + "</td><td class=\"p73_tdErrorCaja\"><img class =\"p73_imgErrorCaja\" src=\"./misumi/images/dialog-error-24.png\" title=\"\"/></td></tr>";
			   }
			   $("select#p73_cmb_seccion").html(options);
			   $("#p73_table_Estructura").html(registrosEstructura);
			   $("#p73_estructuraMultipleLegend").html(p73Seccion);
			   $("div#p73_imagenFlechaDiv").attr("style", "display:block");
			   $("div#p73_TablaEstructuraDiv").attr("style", "display:block");
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
		$("#p73_cmb_seccion").combobox({
	        selected: function(event, ui) {
	        	
	         if ( ui.item.value!="" && ui.item.value!="null") {
		           if ($("#p73_cmb_seccion").val()!=null){
		        	   p73load_cmbCategory();
		        	   var result=p73cleanFilterSelection(P73_SECTION_CONST);
		        	
		           }
	         }  else{
	        	   var result=p73cleanFilterSelection(P73_AREA_CONST);
	        	}  
	        }  
	        ,		       
	        changed: function(event, ui) { 
				   if (ui.item==null ||  ui.item.value!="" || ui.item.value!="null"){
		        	   if (p73cleanFilterSelection(P73_AREA_CONST)){
		        		   p73load_cmbSeccion();
				      }
				   }	 
			   }
	       
	     });
	
	$("#p73_cmb_seccion").combobox('autocomplete',optionNull);
	$("#p73_cmb_seccion").combobox('comboautocomplete',null);
	limpiarErroresCombos();
}

function p73load_cmbCategory(){	
	var options = "";
	var optionNull = "";
	var registrosEstructura = "";
	$("#p73_cmb_categoria").combobox(null);
		var vAgruComerRef=new VAgruComerRef("I3",$("#p73_cmb_area").val(),$("#p73_cmb_seccion").val());
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
					options = options + "<option value='" + data[i].grupo3 + "'>" + p73formatDescripcionCombo(data[i].grupo3, data[i].descripcion) + "</option>";
					registrosEstructura = registrosEstructura + "<tr><td class=\"p73_tdCheck\"><input type=\"checkbox\" class=\"case\" name=\"case\" onclick=\"controlCheckCategoria(this)\" value=\""+data[i].grupo3+"\"/></td><td class=\"p73_tdDato\">" + p73formatDescripcionCombo(data[i].grupo3, data[i].descripcion) + "</td><td class=\"p73_tdErrorCaja\"><img class =\"p73_imgErrorCaja\" src=\"./misumi/images/dialog-error-24.png\" title=\"\"/></td></tr>";
			   }
			   $("select#p73_cmb_categoria").html(options);
			   $("#p73_table_Estructura").html(registrosEstructura);
			   $("#p73_estructuraMultipleLegend").html(p73Categoria);
			   $("div#p73_imagenFlechaDiv").attr("style", "display:block");
			   $("div#p73_TablaEstructuraDiv").attr("style", "display:block");
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
		$("#p73_cmb_categoria").combobox({
	        selected: function(event, ui) {
	        	 if ( ui.item.value!="" && ui.item.value!="null") {
		           if ($("#p73_cmb_categoria").val()!=null){
		        	
		        	   p73load_cmbSubCategory();
		        	   var result=p73cleanFilterSelection(P73_CATEGORY_CONST);
		        	   
		           } 
	        	 }  else{
	        		 var result=p73cleanFilterSelection(P73_SECTION_CONST);
		        	}  
	        },		       
	        changed: function(event, ui) { 
				   if (ui.item==null || ui.item.value!="" || ui.item.value!="null"){
					   if (p73cleanFilterSelection(P73_SECTION_CONST)){ 
						   p73load_cmbCategory();
					   }
				    }	 
					   
			   }
	          
	         
	     }); 
	
	$("#p73_cmb_categoria").combobox('autocomplete',optionNull);
	$("#p73_cmb_categoria").combobox('comboautocomplete',null);
	limpiarErroresCombos();
}
function p73load_cmbSubCategory(){	
	var options = "";
	var optionNull = "";
	var registrosEstructura = "";
	$("#p73_cmb_subcategoria").combobox(null);
	var vAgruComerRef=new VAgruComerRef("I4",$("#p73_cmb_area").val(),$("#p73_cmb_seccion").val(),$("#p73_cmb_categoria").val());
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
					options = options + "<option value='" + data[i].grupo4 + "'>" + p73formatDescripcionCombo(data[i].grupo4, data[i].descripcion) + "</option>";
					registrosEstructura = registrosEstructura + "<tr><td class=\"p73_tdCheck\"><input type=\"checkbox\" class=\"case\" name=\"case\" onclick=\"controlCheckSubcategoria(this)\" value=\""+data[i].grupo4+"\"/></td><td class=\"p73_tdDato\">" + p73formatDescripcionCombo(data[i].grupo4, data[i].descripcion) + "</td><td class=\"p73_tdErrorCaja\"><img class =\"p73_imgErrorCaja\" src=\"./misumi/images/dialog-error-24.png\" title=\"\"/></td></tr>";					
			   }
			   $("select#p73_cmb_subcategoria").html(options);
			   $("#p73_table_Estructura").html(registrosEstructura);
			   $("#p73_estructuraMultipleLegend").html(p73Subcategoria);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
		$("#p73_cmb_subcategoria").combobox({
	        selected: function(event, ui) {
	          if ( ui.item.value!="" && ui.item.value!="null") {
		           if ($("#p73_cmb_subcategoria").val()!=null){
		        	   p73load_cmbSegment();
		        	   var result=p73cleanFilterSelection(P73_SUBCATEGORY_CONST);
		        	   
		           } 
	          }     else{
	        	  var result=p73cleanFilterSelection(P73_CATEGORY_CONST);
	        	} 
	        },		       
	        changed: function(event, ui) { 
				   if (ui.item==null ||ui.item.value!="" || ui.item.value!="null"){
					   if (p73cleanFilterSelection(P73_CATEGORY_CONST)){ 
						   p73load_cmbSubCategory();
					   }	   
				   }	 
			   }
	          
	          
	        
	     });
	
	$("#p73_cmb_subcategoria").combobox('autocomplete',optionNull);
	$("#p73_cmb_subcategoria").combobox('comboautocomplete',null);
	limpiarErroresCombos();
}
function p73load_cmbSegment(){	
	var options = "";
	var optionNull = "";
	var registrosEstructura = "";
	$("#p73_cmb_segmento").combobox(null);
	var vAgruComerRef=new VAgruComerRef("I5",$("#p73_cmb_area").val(),$("#p73_cmb_seccion").val(),$("#p73_cmb_categoria").val(),$("#p73_cmb_subcategoria").val());
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
					options = options + "<option value='" + data[i].grupo5 + "'>" + p73formatDescripcionCombo(data[i].grupo5, data[i].descripcion) + "</option>";
					registrosEstructura = registrosEstructura + "<tr><td class=\"p73_tdCheck\"><input type=\"checkbox\" class=\"case\" name=\"case\" onclick=\"controlCheckSegmento(this)\" value=\""+data[i].grupo5+"\"/></td><td class=\"p73_tdDato\">" + p73formatDescripcionCombo(data[i].grupo5, data[i].descripcion) + "</td><td class=\"p73_tdErrorCaja\"><img class =\"p73_imgErrorCaja\" src=\"./misumi/images/dialog-error-24.png\" title=\"\"/></td></tr>";
			   }
			   $("select#p73_cmb_segmento").html(options);
			   $("#p73_table_Estructura").html(registrosEstructura);
			   $("#p73_estructuraMultipleLegend").html(p73Segmento);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
		$("#p73_cmb_segmento").combobox({
	        selected: function(event, ui) {
	          if ( ui.item.value!="" && ui.item.value!="null") {
		           if ($("#p73_cmb_segmento").val()!=null){
		        	   $("#p73_cmb_segmento").combobox('comboautocomplete',ui.item.value);
		        	   $("#p73_cmb_segmento").combobox('autocomplete',ui.item.label);
		        	   var result=p73cleanFilterSelection(P73_SEGMENT_CONST);
		        	   limpiarErroresCombos();
		           } 
	          }   else{
	        	  var result= p73cleanFilterSelection(P73_SUBCATEGORY_CONST);
	        	} 
	          
	        }  ,		       
	        changed: function(event, ui) { 
				   if (ui.item==null || ui.item.value!="" || ui.item.value!="null"){
					   if (p73cleanFilterSelection(P73_SUBCATEGORY_CONST)){ 
						   p73load_cmbSegment();
					   }   
				   }	 
			   }
	          
	     });
	
	$("#p73_cmb_segmento").combobox('autocomplete',optionNull);
	$("#p73_cmb_segmento").combobox('comboautocomplete',null);
	limpiarErroresCombos();
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
    	$("#p73_cmb_area").combobox("disable");
    	$("#p73_cmb_seccion").combobox("disable");
    }else{
    	$("#p73_cmb_area").combobox("enable");
    	$("#p73_cmb_seccion").combobox("enable");
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
    	$("#p73_cmb_area").combobox("disable");
    	$("#p73_cmb_seccion").combobox("disable");
    	$("#p73_cmb_categoria").combobox("disable");
    }else{
    	$("#p73_cmb_area").combobox("enable");
    	$("#p73_cmb_seccion").combobox("enable");
    	$("#p73_cmb_categoria").combobox("enable");
    }
    limpiarErroresCombos();
}

function controlCheckSubcategoria(obj) {
	var objDisable = false;
	$("input[name='case']").each(function() {
		 if ($( this ).is(':checked')){
			 objDisable = true;
		 }
	});
    if (objDisable){
    	$("#p73_cmb_area").combobox("disable");
    	$("#p73_cmb_seccion").combobox("disable");
    	$("#p73_cmb_categoria").combobox("disable");
    	$("#p73_cmb_subcategoria").combobox("disable");
    }else{
    	$("#p73_cmb_area").combobox("enable");
    	$("#p73_cmb_seccion").combobox("enable");
    	$("#p73_cmb_categoria").combobox("enable");
    	$("#p73_cmb_subcategoria").combobox("enable");
    }
    limpiarErroresCombos();
}

function controlCheckSegmento(obj) {
	var objDisable = false;
	$("input[name='case']").each(function() {
		 if ($( this ).is(':checked')){
			 objDisable = true;
		 }
	});
    if (objDisable){
    	$("#p73_cmb_area").combobox("disable");
    	$("#p73_cmb_seccion").combobox("disable");
    	$("#p73_cmb_categoria").combobox("disable");
    	$("#p73_cmb_subcategoria").combobox("disable");
    	$("#p73_cmb_segmento").combobox("disable");
    }else{
    	$("#p73_cmb_area").combobox("enable");
    	$("#p73_cmb_seccion").combobox("enable");
    	$("#p73_cmb_categoria").combobox("enable");
    	$("#p73_cmb_subcategoria").combobox("enable");
    	$("#p73_cmb_segmento").combobox("enable");
    }
    limpiarErroresCombos();
}

function events_p73_rad_tipoFiltro(){
    $("input[name='p73_rad_tipoFiltro']").change(function () {
 
    	if ($("input[name='p73_rad_tipoFiltro']:checked").val() == "1"){
    		//Estructura comercial
    		 $("div#p73_filtroEstructura").attr("style", "display:block");
    		 $("div#p73_filtroReferencia").attr("style", "display:none");
    		 $("#p73_estructuraReferenciaLegend").html(p73TitBloque1Estructura);
    		 $("#p73_referencia_imgError").hide();
    	} else { //Referencias
    		$("div#p73_filtroEstructura").attr("style", "display:none");
   		 	$("div#p73_filtroReferencia").attr("style", "display:block");
   		 	$("div#p73_imagenFlechaDiv").attr("style", "display:none");
   		 	$("div#p73_TablaEstructuraDiv").attr("style", "display:none");
   		 	$("#p73_estructuraReferenciaLegend").html(p73TitBloque1Referencia);
   		 	var result= p73cleanFilterSelection(P73_RESET_CONST);
   		 	$("#p73_fld_referencia").focus();
    	}   
    });
}

function events_p73_btn_guardar(){
	$("#p73_btn_save").click(function () {
		save();
	});	
}

function events_p73_btn_borrar(){
	$("#p73_btn_delete").click(function () {
		borrar();
	});	
}

function p73cleanFilterSelection(componentName){
	$("#p73_table_Estructura").html('');
	if (componentName == P73_RESET_CONST){
		$("#p73_cmb_area").combobox(null);
		$("select#p73_cmb_area").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p73_cmb_area").combobox('autocomplete',"");
		$("#p73_cmb_area").combobox('comboautocomplete',null);
		$("#p73_cmb_seccion").combobox(null);
		$("#p73_cmb_subcategoria").combobox(null);
		$("#p73_cmb_categoria").combobox(null);
		$("#p73_cmb_segmento").combobox(null);
		if ($("#centerId").val()!=null && $("#centerId").val()!=''){
			p73load_cmbArea();
		}
	}
	if (componentName == P73_AREA_CONST || componentName == P73_RESET_CONST){
		$("select#p73_cmb_seccion").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p73_cmb_seccion").combobox('autocomplete','');
		$("#p73_cmb_seccion").combobox('comboautocomplete',null);
	}
	if(componentName==P73_SECTION_CONST || componentName==P73_AREA_CONST || componentName == P73_RESET_CONST){
		$("select#p73_cmb_categoria").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p73_cmb_categoria").combobox('autocomplete','');
		$("#p73_cmb_categoria").combobox('comboautocomplete',null);
	}
	if(componentName==P73_CATEGORY_CONST || componentName==P73_SECTION_CONST || componentName==P73_AREA_CONST || componentName == P73_RESET_CONST){
		$("select#p73_cmb_subcategoria").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p73_cmb_subcategoria").combobox('autocomplete',"");
		$("#p73_cmb_subcategoria").combobox('comboautocomplete',null);
	}
	if(componentName==P73_SUBCATEGORY_CONST || componentName==P73_CATEGORY_CONST || componentName==P73_SECTION_CONST || componentName==P73_AREA_CONST || componentName == P73_RESET_CONST){
		$("select#p73_cmb_segmento").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p73_cmb_segmento").combobox('autocomplete',"");
		$("#p73_cmb_segmento").combobox('comboautocomplete',null);
	}
	p73disableFilterSelection(componentName);
	return true;
}

function p73disableFilterSelection(componentName){
	if (componentName == P73_RESET_CONST){
		if ($("#centerId").val()!=null && $("#centerId").val()!=''){
			$("#p73_cmb_area").combobox("enable");
		}else{
			$("#p73_cmb_area").combobox("disable");
		}
		$("#p73_cmb_seccion").combobox("disable");
		$("#p73_cmb_categoria").combobox("disable");
		$("#p73_cmb_subcategoria").combobox("disable");
		$("#p73_cmb_segmento").combobox("disable");
		$("div#p73_imagenFlechaDiv").attr("style", "display:none");
		$("div#p73_TablaEstructuraDiv").attr("style", "display:none");
		
	}else if (componentName == P73_AREA_CONST){
		$("#p73_cmb_seccion").combobox("enable");
		$("#p73_cmb_categoria").combobox("disable");
		$("#p73_cmb_subcategoria").combobox("disable");
		$("#p73_cmb_segmento").combobox("disable");
		$("div#p73_imagenFlechaDiv").attr("style", "display:none");
		$("div#p73_TablaEstructuraDiv").attr("style", "display:none");
		
	} else	if(componentName==P73_SECTION_CONST ){
		$("#p73_cmb_categoria").combobox("enable");
		$("#p73_cmb_subcategoria").combobox("disable");
		$("#p73_cmb_segmento").combobox("disable");
	}
	if(componentName==P73_CATEGORY_CONST ){
		$("#p73_cmb_subcategoria").combobox("enable");
		$("#p73_cmb_segmento").combobox("disable");

	}
	if(componentName==P73_SUBCATEGORY_CONST){
		$("#p73_cmb_segmento").combobox("enable");

	}
}

function p73formatDescripcionCombo(codigo, descripcion){
	return codigo + "-" + descripcion;
}

function controlCentro(){
	$("#p73_rad_tipoFiltro").filter('[value=1]').attr('checked', true);
	if ($("#centerId").val()==null || $("#centerId").val()==''){
		$("#p73_fld_referencia").attr("disabled", "disabled");
		$("#p73_btn_save").attr("disabled", "disabled");
		$("#p73_btn_delete").attr("disabled", "disabled");
	}else{
		$("#p73_fld_referencia").removeAttr("disabled");
		$("#p73_btn_save").removeAttr("disabled");
		$("#p73_btn_delete").removeAttr("disabled");
	}
	$( "#centerName" ).bind('focus', function() {
		if ($("#centerName").val()==null || $("#centerName").val()==''){
			$('#gridP73ExclusionVentas').jqGrid('clearGridData');
			mostrarNoHayRegistros(true);
			p73cleanFilterSelection(P73_RESET_CONST);
			$("#p73_fld_referencia").val('');
			$("#p73_referencia_imgError").hide();
			$("#p73_referencia_imgError").attr('title', '');
			$("#p73_fld_referencia").attr("disabled", "disabled");
			$("#p73_btn_save").attr("disabled", "disabled");
			$("#p73_btn_delete").attr("disabled", "disabled");
		}
    });
}

function save(){
	p73InsertarDatos();
}

function borrar(){
	p73BorrarDatos();
}

function formatDescripcionCombo(codigo, descripcion){
	return codigo + "-" + descripcion;
}

function loadP73(locale){
	grid = new GridP73(locale);
	var jqxhr = $.getJSON(grid.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				grid.colNames = data.p73ColNames;
				grid.title = p73GridTitle
				grid.emptyRecords= emptyRecords;
				index = '';
				sortOrder = 'asc';
				loadP73Mock(grid);
				//setHeadersTitles(data);
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
           });
}

function loadP73Mock(grid) {
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
			multiselect : true,
			index: grid.sortIndex,
			sortname: grid.sortIndex,
			sortorder: grid.sortOrder,
			emptyrecords : grid.emptyRecords,
			gridComplete : function() {},
			loadComplete : function(data) {
				grid.actualPage = data.page;
				grid.localData = data;
				grid.sortIndex = null;
				grid.sortOrder = null;
				$("#p73_AreaResultados .loading").css("display", "none");	
				if (grid.firstLoad)
					jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);

				// Ocultamos la check de seleccionar todos.
				$("#cb_" + grid.name).attr("style",
						"display:none");
				
				p73CargarDatos('N',grid);
			},
			onPaging : function(postdata) {			
				alreadySorted = false;
				grid.sortIndex = null;
				grid.sortOrder = null;
				grid.saveColumnState.call($(this), this.p.remapColumns);
				p73CargarDatos('S',grid);
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
				p73CargarDatos('S',grid);
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
}

function mostrarNoHayRegistros(mostrar)
{
    var container = $('#gview_gridP73ExclusionVentas');
    //Se elimina el mensaje anterior si lo hubiese para sólo se escriba una vez
    container.find('#p73_resultadosNoHayRegistros' ).remove();
    if (mostrar) {
        container.find('.ui-jqgrid-hdiv').after('<div id="p73_resultadosNoHayRegistros">' + emptyRecords + '</div>'); 
    }
}


/*Clase de constantes para le GRID*/
function GridP73 (locale){
	// Atributos
	this.name = "gridP73ExclusionVentas"; 
	this.nameJQuery = "#gridP73ExclusionVentas"; 
	this.i18nJSON = './misumi/resources/p73ExclusionVentas/p73exclusionVentas_' + locale + '.json';
	this.colNames = null;
	this.cm = [	{
		
		"name" : "descripGrupo1",
		"index":"descripGrupo1",
		"width" : 25,
		"formatter": function(value, options, rData){ return ((rData['grupo1']!=null && rData['grupo1']!=0) ? rData['grupo1'] + '-' +value : '')  ;  }
	},{
		"name" : "descripGrupo2",
		"index":"descripGrupo2", 
		"width" : 25,
		"formatter": function(value, options, rData){ return ((rData['grupo2']!=null && rData['grupo2']!=0) ? rData['grupo2'] + '-' +value : '')  ;  }
	},{
		"name" : "descripGrupo3",
		"index":"descripGrupo3", 
		"width" : 25,
		"formatter": function(value, options, rData){ return ((rData['grupo3']!=null && rData['grupo3']!=0) ? rData['grupo3'] + '-' +value : '')  ;  }
	},{
		"name" : "descripGrupo4",
		"index":"descripGrupo4", 
		"width" : 25,
		"formatter": function(value, options, rData){ return ((rData['grupo4']!=null && rData['grupo4']!=0) ? rData['grupo4'] + '-' +value : '')  ;  }
	},{
		"name" : "descripGrupo5",
		"index":"descripGrupo5", 
		"width" : 25,
		"formatter": function(value, options, rData){ return ((rData['grupo5']!=null && rData['grupo5']!=0) ? rData['grupo5'] + '-' +value : '')  ;  }
	},{
		"name" : "codArt",
		"index": "codArt", 
		"width" : 15,
		"formatter" : function(value, options, rData){ return ((value!=null && value!=0) ? value : '')  ;  }
	},{
		"name" : "descripArt",
		"index": "descripArt", 
		"width" : 25						
	},{
		"name" : "fecha",
		"index": "fecha", 
		"formatter" : p73FormateoDate,
		"width" : 15
	},{
		"name" : "codError",
		"index": "codError", 
		"formatter" : p73ImageFormatMessage,
		"fixed" : true,
		"width" : 50
	}
		
		];
	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP73ExclusionVentas"; 
	this.pagerNameJQuery = "#pagerP73ExclusionVentas";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;
	 this.myColumnStateName = 'gridP73.colState';
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
			return "cod_error_orden, grupo1, grupo2, grupo3, grupo4, grupo5, cod_art, fecha";
		}
		
	} 
	this.getSortOrder = function getSortOrder () {
		
		if ($(this.nameJQuery).getGridParam('sortorder')!=null){
			return $(this.nameJQuery).getGridParam('sortorder');
		}else{
			return "cod_error_orden, grupo1, grupo2, grupo3, grupo4, grupo5, cod_art, fecha";
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

function p73CargarDatos(recarga, grid) {

	if (recarga!=null && recarga == 'N'){
		p73SeleccionadosTotal = new Array();
	}else{
		p73ObtenerSeleccionados();
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
    	jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), false);
    }

	var exclusionVentas = new ExclusionVentas ($("#centerId").val(), $("#p73_fld_referencia").val(), $("#p73_cmb_area").combobox('getValue'), $("#p73_cmb_seccion").combobox('getValue'), $("#p73_cmb_categoria").combobox('getValue'),
			$("#p73_cmb_subcategoria").combobox('getValue'), $("#p73_cmb_segmento").combobox('getValue'), null, p73SeleccionadosTotal, null);

	// DesCheckeamos lo seleccionado al llamar a insertar.
	p73DesCheckearSeleccionados();

	var objJson = $.toJSON(exclusionVentas.prepareExclusionVentasToJsonObject());

		$("#p73_AreaResultados .loading").css("display", "block");
		
		$.ajax({
			type : 'POST',
			url : './exclusionVentas/loadDataGrid.do?page='+grid.getActualPage()+'&max='+grid.getRowNumPerPage()+'&index='+grid.getSortIndex()+'&sortorder='+grid.getSortOrder()+ '&recarga=' + recarga,
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {		
				$(grid.nameJQuery)[0].addJSONData(data.datos);
				grid.actualPage = data.datos.page;
				grid.localData = data.datos;

				if (data.codError <= 0){
					$("#p73AreaErrores").attr("style", "display:none");
					$("#p73_AreaResultados .loading").css("display", "none");	
					if (data.records == 0){
						createAlert(replaceSpecialCharacters(emptyRecords), "ERROR");
					}
					p73SeleccionadosTotal = data.listadoSeleccionados;
				}else{
					$("#p73_erroresTexto").text(data.descError);
					$("#p73AreaErrores").attr("style", "display:block");
					$("#p73_AreaPestanas").attr("style", "display:none");
				}
				p73ControlesPantalla();
			},
			error : function (xhr, status, error){
				$("#p73_AreaResultados .loading").css("display", "none");	
				handleError(xhr, status, error, locale);				
	        }			
		});		
}

function p73InsertarDatos(){
	
	p73ObtenerSeleccionadosInsercion();
	
	var messageVal = p73FindValidationInsert();

	if (messageVal != null) {
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	} else {
		var fecha = $.datepicker.formatDate("ddmmyy",$("#p73_fechaDatePicker").datepicker("getDate"));
		var exclusionVentas = new ExclusionVentas ($("#centerId").val(),null, null, null, null, null, null, null, p73SeleccionadosEstructuraTotal, fecha);
		var objJson = $.toJSON(exclusionVentas.prepareExclusionVentasToJsonObject());
	
		// Reseteamos el array de seleccionados.
		p73Seleccionados = new Array();

		$("#AreaResultados .loading").css("display", "block");
	
		$.ajax({
				type : 'POST',
				url : './exclusionVentas/insertDataGrid.do?max='+ grid.getRowNumPerPage()+ '&index=' + grid.getSortIndex()+ '&sortorder=' + grid.getSortOrder(),
				data : objJson,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {
					$(grid.nameJQuery)[0]
							.addJSONData(data.datos);
					grid.actualPage = data.datos.page;
					grid.localData = data.datos;
					p73SeleccionadosTotal = data.listadoSeleccionados;
					p73SeleccionadosEstructuraTotal = data.listadoSeleccionadosNuevo;
					
					if (data != null && data != '')
					{
						if (data.codError <= 0){
							$("#p73AreaErrores").attr("style", "display:none");
							$("#p73_AreaResultados .loading").css("display", "none");	
							if (data.records == 0){
								createAlert(replaceSpecialCharacters(emptyRecords), "ERROR");
							}
							p73SeleccionadosTotal = data.listadoSeleccionados;
							p73SeleccionadosNuevoTotal = data.listadoSeleccionadosNuevo;
							// DesCheckeamos lo seleccionado al llamar a insertar.
							p73DesCheckearSeleccionados();

						}else{
							$("#p73_erroresTexto").text(data.descError);
							$("#p73AreaErrores").attr("style", "display:block");
							$("#p73_AreaPestanas").attr("style", "display:none");
						}
	
					}else{
						$("#p73AreaErrores").attr("style", "display:none");
					}
					p73ControlesPantalla();
					p73ControlErroresInsercion();				
				},
				error : function(xhr, status, error) {
					$("#AreaResultados .loading").css("display", "none");
					handleError(xhr, status, error, locale);
				}
			});
	}	
}

function p73ObtenerSeleccionadosInsercion() {

	p73SeleccionadosEstructuraTotal = new Array();

	// Obtenemos las referencias seleccionadas para insertar
	var camposSeleccionadosExclVenta = {};
	var valorCodArt = $("#p73_fld_referencia").val();
	var valorGrupo1 = $("#p73_cmb_area").combobox('getValue');
	var valorGrupo2 = $("#p73_cmb_seccion").combobox('getValue');
	var valorGrupo3 = $("#p73_cmb_categoria").combobox('getValue');
	var valorGrupo4 = $("#p73_cmb_subcategoria").combobox('getValue');
	var valorGrupo5 = $("#p73_cmb_segmento").combobox('getValue');
	var seleccionadaEstructuraFinal = false;
	
	camposSeleccionadosExclVenta.codArt = valorCodArt;
	camposSeleccionadosExclVenta.grupo1 = valorGrupo1;
	camposSeleccionadosExclVenta.grupo2 = valorGrupo2;
	camposSeleccionadosExclVenta.grupo3 = valorGrupo3;
	camposSeleccionadosExclVenta.grupo4 = valorGrupo4;
	camposSeleccionadosExclVenta.grupo5 = valorGrupo5;
	
	if(valorCodArt !=null && valorCodArt != ''){
		camposSeleccionadosExclVenta.seleccionado = "S";
		p73SeleccionadosEstructuraTotal.push(camposSeleccionadosExclVenta);
	}else{
		$("input[name='case']").each(function() {
			var camposSeleccionadosExclVentaDetalle = {};
			camposSeleccionadosExclVentaDetalle.codArt = valorCodArt;
			camposSeleccionadosExclVentaDetalle.grupo1 = valorGrupo1;
			camposSeleccionadosExclVentaDetalle.grupo2 = valorGrupo2;
			camposSeleccionadosExclVentaDetalle.grupo3 = valorGrupo3;
			camposSeleccionadosExclVentaDetalle.grupo4 = valorGrupo4;
			camposSeleccionadosExclVentaDetalle.grupo5 = valorGrupo5;
			
			//Se comprueba para que grupo se elige
			if ($( this ).is(':checked')){
				if (valorGrupo2 == null || valorGrupo2 == ''){
					camposSeleccionadosExclVentaDetalle.grupo2 = $( this ).val();
				}else if(valorGrupo3 == null || valorGrupo3 == ''){
					camposSeleccionadosExclVentaDetalle.grupo3 = $( this ).val();
				}else if(valorGrupo4 == null || valorGrupo4 == ''){
					camposSeleccionadosExclVentaDetalle.grupo4 = $( this ).val();
				}else if(valorGrupo5 == null || valorGrupo5 == ''){		
					camposSeleccionadosExclVentaDetalle.grupo5 = $( this ).val();
				}
				seleccionadaEstructuraFinal = true;
				camposSeleccionadosExclVentaDetalle.seleccionado = "S";
				p73SeleccionadosEstructuraTotal.push(camposSeleccionadosExclVentaDetalle);
			}
		});

		//Selección de estructuras a través de combos
		if (!seleccionadaEstructuraFinal && (valorGrupo1!=null || valorGrupo2!=null || valorGrupo3!=null || valorGrupo4!=null || valorGrupo5!=null)){
			camposSeleccionadosExclVenta.seleccionado = "S";
			p73SeleccionadosEstructuraTotal.push(camposSeleccionadosExclVenta);	
		}
	}
}

function p73ControlErroresInsercion() {

	var valorCodArt = $("#p73_fld_referencia").val();
	var valorGrupo1 = ($("#p73_cmb_area").combobox('getValue')!=null?$("#p73_cmb_area").combobox('getValue'):0);
	var valorGrupo2 = ($("#p73_cmb_seccion").combobox('getValue')!=null?$("#p73_cmb_seccion").combobox('getValue'):0);
	var valorGrupo3 = ($("#p73_cmb_categoria").combobox('getValue')!=null?$("#p73_cmb_categoria").combobox('getValue'):0);
	var valorGrupo4 = ($("#p73_cmb_subcategoria").combobox('getValue')!=null?$("#p73_cmb_subcategoria").combobox('getValue'):0);
	var valorGrupo5 = ($("#p73_cmb_segmento").combobox('getValue')!=null?$("#p73_cmb_segmento").combobox('getValue'):0);
	
	var seleccionadaEstructuraFinal = false;
	var totalErrores = 0;
	
	if(valorCodArt !=null && valorCodArt != ''){
		if (p73SeleccionadosNuevoTotal!=null && p73SeleccionadosNuevoTotal[0].codError!= null && p73SeleccionadosNuevoTotal[0].codError > 0 ){
			$("#p73_referencia_imgError").show();
			$("#p73_referencia_imgError").attr('title', p73SeleccionadosNuevoTotal[0].descripError);
			totalErrores++;
		}else{
			$("#p73_fld_referencia").val('');
			$("#p73_referencia_imgError").hide();
			$("#p73_referencia_imgError").attr('title', '');
		}
	}else{
		var imgCampoCheck = null;
		var mostrarErrorInsertados = false;
		$("input[name='case']").each(function() {
			imgCampoCheck = $( this ).parent().parent().find(".p73_imgErrorCaja");
			var mostrarErrorCase = false;
			var textTitle = '';
			//Se comprueba para que grupo se elige
			if ($( this ).is(':checked')){
				seleccionadaEstructuraFinal = true;
				for (j = 0; j < p73SeleccionadosNuevoTotal.length; j++) {
					
					var valorGrupo2Comp = valorGrupo2;
					var valorGrupo3Comp = valorGrupo3;
					var valorGrupo4Comp = valorGrupo4;
					var valorGrupo5Comp = valorGrupo5;

					if (valorGrupo2 == null || valorGrupo2 == 0){
						valorGrupo2Comp = $( this ).val();
					}else if(valorGrupo3 == null || valorGrupo3 == 0){
						valorGrupo3Comp = $( this ).val();
					}else if(valorGrupo4 == null || valorGrupo4 == 0){
						valorGrupo4Comp = $( this ).val();
					}else if(valorGrupo5 == null || valorGrupo5 == 0){		
						valorGrupo5Comp = $( this ).val();
					}
					imgCampoCheck = $( this ).parent().parent().find(".p73_imgErrorCaja");

					var nuevoGrupo1 = (p73SeleccionadosNuevoTotal[j].grupo1!=null?p73SeleccionadosNuevoTotal[j].grupo1:0);
					var nuevoGrupo2 = (p73SeleccionadosNuevoTotal[j].grupo2!=null?p73SeleccionadosNuevoTotal[j].grupo2:0);
					var nuevoGrupo3 = (p73SeleccionadosNuevoTotal[j].grupo3!=null?p73SeleccionadosNuevoTotal[j].grupo3:0);
					var nuevoGrupo4 = (p73SeleccionadosNuevoTotal[j].grupo4!=null?p73SeleccionadosNuevoTotal[j].grupo4:0);
					var nuevoGrupo5 = (p73SeleccionadosNuevoTotal[j].grupo5!=null?p73SeleccionadosNuevoTotal[j].grupo5:0);
					
					if ((nuevoGrupo1 == valorGrupo1)&&(nuevoGrupo2 == valorGrupo2Comp)&&(nuevoGrupo3 == valorGrupo3Comp)&&
							(nuevoGrupo4 == valorGrupo4Comp)&&(nuevoGrupo5 == valorGrupo5Comp) && (p73SeleccionadosNuevoTotal[j].codError > 0))
					{

						mostrarErrorCase = true;
						textTitle = p73SeleccionadosNuevoTotal[j].descripError;
						totalErrores++;
					}
				}
			}
			if (mostrarErrorCase)
			{
				imgCampoCheck.show();
				imgCampoCheck.attr('title', textTitle);
				mostrarErrorInsertados = true;
			}else{
				imgCampoCheck.hide();
				$( this ).attr('checked', false)
			}

		});

		//Selección de estructuras a través de combos
		if (!seleccionadaEstructuraFinal){
			limpiarErroresCombos();

			var imgCampoCombo = null;
			if (valorGrupo5!=null && valorGrupo5!=0){
				imgCampoCombo = $("#p73_segmento_imgError");
			}else if(valorGrupo4!=null && valorGrupo4!=0){
				imgCampoCombo = $("#p73_subcategoria_imgError");
			}else if(valorGrupo3!=null && valorGrupo3!=0){
				imgCampoCombo = $("#p73_categoria_imgError");
			}else if(valorGrupo2!=null && valorGrupo2!=0){
				imgCampoCombo = $("#p73_seccion_imgError");
			}else if(valorGrupo1!=null && valorGrupo1!=0){
				imgCampoCombo = $("#p73_area_imgError");
			}
			
			if(p73SeleccionadosNuevoTotal[0]!=null){
				
				var nuevoGrupo1 = (p73SeleccionadosNuevoTotal[0].grupo1!=null?p73SeleccionadosNuevoTotal[0].grupo1:0);
				var nuevoGrupo2 = (p73SeleccionadosNuevoTotal[0].grupo2!=null?p73SeleccionadosNuevoTotal[0].grupo2:0);
				var nuevoGrupo3 = (p73SeleccionadosNuevoTotal[0].grupo3!=null?p73SeleccionadosNuevoTotal[0].grupo3:0);
				var nuevoGrupo4 = (p73SeleccionadosNuevoTotal[0].grupo4!=null?p73SeleccionadosNuevoTotal[0].grupo4:0);
				var nuevoGrupo5 = (p73SeleccionadosNuevoTotal[0].grupo5!=null?p73SeleccionadosNuevoTotal[0].grupo5:0);
				
				if ((nuevoGrupo1 == valorGrupo1)&&(nuevoGrupo2 == valorGrupo2)&&(nuevoGrupo3 == valorGrupo3)&&
						(nuevoGrupo4 == valorGrupo4)&&(nuevoGrupo5 == valorGrupo5) && (p73SeleccionadosNuevoTotal[0].codError > 0))
				{
					imgCampoCombo.show();
					imgCampoCombo.attr('title', p73SeleccionadosNuevoTotal[0].descripError);
					mostrarErrorInsertados = true;
					totalErrores++;
				}
			}

		}
		
		if (!mostrarErrorInsertados){//Si no hay errores se 
			p73cleanFilterSelection(P73_RESET_CONST);
		}
	}
	
	//Recuperación de datos para mensaje de guardado
	p73ResultadoSave(totalErrores);

}

function p73BorrarDatos() {

	p73ObtenerSeleccionados();
	
	var messageVal = p73FindValidationRemove();

	if (messageVal != null) {
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	} else {
		var exclusionVentas = new ExclusionVentas ($("#centerId").val(),null, null, null, null, null, null, null, p73SeleccionadosTotal, null);
	
		// Reseteamos el array de seleccionados.
		p73Seleccionados = new Array();
		p73SeleccionadosTotal = new Array();

		var objJson = $.toJSON(exclusionVentas.prepareExclusionVentasToJsonObject());
	
		$("#AreaResultados .loading").css("display", "block");
	
		$.ajax({
				type : 'POST',
				url : './exclusionVentas/removeDataGrid.do?max='+ grid.getRowNumPerPage()+ '&index=' + grid.getSortIndex()+ '&sortorder=' + grid.getSortOrder(),
				data : objJson,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {
					$(grid.nameJQuery)[0]
							.addJSONData(data.datos);
					grid.actualPage = data.datos.page;
					grid.localData = data.datos;
					p73SeleccionadosTotal = data.listadoSeleccionados;
					
					if (data != null && data != '')
					{
						if (data.codError <= 0){
							$("#p73AreaErrores").attr("style", "display:none");
							$("#p73_AreaResultados .loading").css("display", "none");	
							if (data.records == 0){
								createAlert(replaceSpecialCharacters(emptyRecords), "ERROR");
							}
							p73SeleccionadosTotal = data.listadoSeleccionados;
							// DesCheckeamos lo seleccionado al llamar a borrar.
							p73DesCheckearSeleccionados();

						}else{
							$("#p73_erroresTexto").text(data.descError);
							$("#p73AreaErrores").attr("style", "display:block");
							$("#p73_AreaPestanas").attr("style", "display:none");
						}
	
					}else{
						$("#p73AreaErrores").attr("style", "display:none");
					}
					p73ControlesPantalla();
				},
				error : function(xhr, status, error) {
					$("#AreaResultados .loading").css("display", "none");
					handleError(xhr, status, error, locale);
				}
			});
	}
}

function p73ObtenerSeleccionados() {

	p73Seleccionados = new Array();

	var selectedRowIDs = jQuery(grid.nameJQuery).jqGrid(
			'getGridParam', 'selarrrow');
	var rowsInPage = jQuery(grid.nameJQuery).jqGrid(
			'getGridParam', 'rowNum');

	// Obtenemos las referencias seleccionadas 
	for (i = 0; i < selectedRowIDs.length; i++) {
		if (selectedRowIDs[i] != null && selectedRowIDs[i] != '') {
			p73Seleccionados.push($("#"+selectedRowIDs[i]+"_grupo1").val()+"#"+$("#"+selectedRowIDs[i]+"_grupo2").val()+"#"+$("#"+selectedRowIDs[i]+"_grupo3").val()+"#"+$("#"+selectedRowIDs[i]+"_grupo4").val()+"#"+$("#"+selectedRowIDs[i]+"_grupo5").val()+"#"+$("#"+selectedRowIDs[i]+"_codArt").val()+"#"+$("#"+selectedRowIDs[i]+"_fecha").val());
		}
	}

	// Nos recorremos los registros existentes en pantalla.
	for (i = 0; i < rowsInPage; i++) {

		grupo1 = $("#"+(i+1)+"_grupo1").val();
		grupo2 = $("#"+(i+1)+"_grupo2").val();
		grupo3 = $("#"+(i+1)+"_grupo3").val();
		grupo4 = $("#"+(i+1)+"_grupo4").val();
		grupo5 = $("#"+(i+1)+"_grupo5").val();
		codArt = $("#"+(i+1)+"_codArt").val();
		fecha  = $("#"+(i+1)+"_fecha").val();
		// Nos recorremos la lista de seleccionados.
		if(p73SeleccionadosTotal!=null){
			for (j = 0; j < p73SeleccionadosTotal.length; j++) {
				if ((p73SeleccionadosTotal[j].grupo1 == grupo1)&&(p73SeleccionadosTotal[j].grupo2 == grupo2)&&(p73SeleccionadosTotal[j].grupo3 == grupo3)&&
						(p73SeleccionadosTotal[j].grupo4 == grupo4)&&(p73SeleccionadosTotal[j].grupo5 == grupo5)&&(p73SeleccionadosTotal[j].codArt == codArt)&&(p73SeleccionadosTotal[j].fecha == fecha))
				{
					// Si es una de las referencias de la página, actualizamos el
					// valor de seleccionado.
					if ($.inArray(grupo1+"#"+grupo2+"#"+grupo3+"#"+grupo4+"#"+grupo5+"#"+codArt+"#"+fecha, p73Seleccionados) > -1)
					{
						p73SeleccionadosTotal[j].seleccionado = "S";
					} else {
						p73SeleccionadosTotal[j].seleccionado = "N";
					}
				}
			}
		}
	}
}

function p73CheckearSeleccionados(fila) {

	grupo1 = $("#"+(fila)+"_grupo1").val();
	grupo2 = $("#"+(fila)+"_grupo2").val();
	grupo3 = $("#"+(fila)+"_grupo3").val();
	grupo4 = $("#"+(fila)+"_grupo4").val();
	grupo5 = $("#"+(fila)+"_grupo5").val();
	codArt = $("#"+(fila)+"_codArt").val();
	fecha  = $("#"+(fila)+"_fecha").val();

	// Nos recorremos la lista de seleccionados.
	if (p73SeleccionadosTotal!=null){
		for (j = 0; j < p73SeleccionadosTotal.length; j++) {
			if ((p73SeleccionadosTotal[j].grupo1 == grupo1)&&(p73SeleccionadosTotal[j].grupo2 == grupo2)&&(p73SeleccionadosTotal[j].grupo3 == grupo3)&&
					(p73SeleccionadosTotal[j].grupo4 == grupo4)&&(p73SeleccionadosTotal[j].grupo5 == grupo5)&&(p73SeleccionadosTotal[j].codArt == codArt)&&(p73SeleccionadosTotal[j].fecha == fecha)&&(p73SeleccionadosTotal[j].seleccionado == "S"))
			{
				jQuery(grid.nameJQuery).jqGrid('setSelection',fila);
			}
		}
	}
}


function p73DesCheckearSeleccionados() {

	jQuery(grid.nameJQuery).jqGrid().resetSelection();
}

function p73ControlesPantalla() {

	$("div#p73_Area").attr("style", "display:block");
	if (jQuery(grid.nameJQuery).getGridParam('records') == 0){ 
		mostrarNoHayRegistros(true);
	}
    else{
    	mostrarNoHayRegistros(false);
    }

	// Nos recorremos los registros existentes en pantalla.
	var rowsInPage = jQuery(grid.nameJQuery).jqGrid(
			'getGridParam', 'rowNum');
	for (i = 0; i < rowsInPage; i++) {
		// Tenemos que seleccionar las checks que nos llegan de sesión
		p73CheckearSeleccionados(i + 1);
	}
}

function p73FormateoDate(cellValue, opts, rowObject) {
	
	var fechaFormateada = '';
	if (cellValue != null)
	{
		var diaFecha = parseInt(cellValue.substring(0,2),10);
		var mesFecha = parseInt(cellValue.substring(2,4),10);
		var anyoFecha = parseInt(cellValue.substring(4),10);
		
		fechaFormateada = $.datepicker.formatDate("dd/mm/yy", new Date(anyoFecha, mesFecha - 1, diaFecha),{
			dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
			dayNames: $.datepicker.regional[ "es" ].dayNames,
			monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
			monthNames: $.datepicker.regional[ "es" ].monthNames
			});
	}
	
	return(fechaFormateada);
	
}

function p73ImageFormatMessage(cellValue, opts, rData) {

	var imagen = "";
	var mostrarGuardado = "none;";
	var mostrarError = "none;";
	var descError = "";
	//Controlamos los posibles errores que me lleguen para pintar el icono correspondiente.
	if (parseFloat(rData['codError']) == '-1')
	{
		//Pintamos el icono de que se ha guardado
		mostrarGuardado = "block;";
	}else if (parseFloat(rData['codError']) > '0')
	{
		//Pintamos el icono de que ha ocurrido un error con la descripción de borrado
		mostrarError = "block;";
		if (rData['descripError'] != '')
		{
			descError = rData['descripError'];
		}
	}
	imagen = "<div id='"+opts.rowId+"_divGuardado' align='center' style='display: "+ mostrarGuardado + "'><img id='"+opts.rowId+"_imgGuardado' src='./misumi/images/floppy.png' title='"+p73iconoGuardado+"'/></div>"; //Guardado
	imagen += "<div id='"+opts.rowId+"_divError' align='center' style='display: " + mostrarError + "'><img id='"+opts.rowId+"_imgError' src='./misumi/images/dialog-error-24.png' title='"+descError+"'/></div>"; //Error

	//Añadimos los valores de las columnas de estructura para poder utilizarlos posteriormente.
	var datoGrupo1 = "<input type='hidden' id='"+opts.rowId+"_grupo1' value='"+rData['grupo1']+"'>";
	imagen +=  datoGrupo1;
	var datoGrupo2 = "<input type='hidden' id='"+opts.rowId+"_grupo2' value='"+rData['grupo2']+"'>";
	imagen +=  datoGrupo2;
	var datoGrupo3 = "<input type='hidden' id='"+opts.rowId+"_grupo3' value='"+rData['grupo3']+"'>";
	imagen +=  datoGrupo3;
	var datoGrupo4 = "<input type='hidden' id='"+opts.rowId+"_grupo4' value='"+rData['grupo4']+"'>";
	imagen +=  datoGrupo4;
	var datoGrupo5 = "<input type='hidden' id='"+opts.rowId+"_grupo5' value='"+rData['grupo5']+"'>";
	imagen +=  datoGrupo5;
	var datoCodArt = "<input type='hidden' id='"+opts.rowId+"_codArt' value='"+rData['codArt']+"'>";
	imagen +=  datoCodArt;
	var datoFecha = "<input type='hidden' id='"+opts.rowId+"_fecha' value='"+rData['fecha']+"'>";
	imagen +=  datoFecha;

	return imagen;
}

function p73FindValidationInsert() {
	var messageVal = p73emptyListaSeleccionadosNuevo;

	// Nos recorremos la lista de seleccionados.
	if (p73SeleccionadosEstructuraTotal.length > 0) {
		messageVal = null;
	}
	return messageVal;
}

function p73FindValidationRemove() {
	var messageVal = p73emptyListaSeleccionados;

	// Nos recorremos la lista de seleccionados.
	for (j = 0; j < p73SeleccionadosTotal.length; j++) {
		if (p73SeleccionadosTotal[j].seleccionado == "S") {
			messageVal = null;
		}
	}
	return messageVal;
}

function limpiarErroresCombos(){ 
	//Borrado de errores previos
	$("#p73_area_imgError").hide();
	$("#p73_seccion_imgError").hide();
	$("#p73_categoria_imgError").hide();
	$("#p73_subcategoria_imgError").hide();
	$("#p73_segmento_imgError").hide();
}

function p73ResultadoSave(error){
	var message = null;
	var level = null;
	if (error == 0){
		message = p73saveResultOK;
		level = "INFO";
	} else {
		message = p73saveResultError;
		message = message.replace('{0}',error);
		level = "ERROR";
}
	createAlert(message, level);
}
