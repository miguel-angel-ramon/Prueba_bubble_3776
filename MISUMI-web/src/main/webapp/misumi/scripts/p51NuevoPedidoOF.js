var RESET_CONST_P51="reset";
var OFERTA_CONST_P51="oferta";
var SECTION_CONST_P51="section";
var CATEGORY_CONST_P51="category";
var gridP51=null;
var gridP51FP=null;
var gridP51AFI=null;
var gridColNamesP51FP = null;
var gridColNamesP51AFI = null;
var gridTitleP51FP = null;
var gridTitleP51AFI = null;
var gridEmptyRecordsP51 = null;
var gridEmptyRecordsP51FP = null;
var gridEmptyRecordsP51AFI = null;
var emptyListaModificadosP51 = null;
var centerRequiredP51=null;
var ofertaRequiredP51=null;
var referenceRequiredP51=null;
var tableFilterP51=null;
var numRowP51=1;
var cantidadIncorrectaP51=null;
var iconoModificadoP51=null;
var errorGuardadoP51=null;
var seleccionadosP51 = new Array();
var listadoModificadosP51 = new Array();
var ofertaCombo = null;
var invalidCant1P51 = null;
var invalidCant2P51 = null;
var invalidCant3P51 = null;
var invalidImplInicialP51 = null;
var invalidImplFinalP51 = null;
var datosObligatoriosFilaP51 = null;
var literalAyuda1ConOferta = null;
var literalInvalidDates = null;
var impFinalModif = false;
var mensajeAyudaNuevoOferta = null;
var sectionRequiredP51 = null;
var gondolaValorTodos = null;
var p51fechaSelBloqueosEncargosMontajes = null;
var p51fechaSelBloqueosEncargos = null;
var p51fechaSelBloqueosMontajes = null;


function initializeP51(){

}

//Si se pulsa intro, volvemos a lanzar el buscar.

function initializeScreenComponentsP51(){
	$("#p51_cmb_numeroOferta").combobox(null);
	$("#p51_cmb_seccion").combobox(null);
	$("#p51_cmb_categoria").combobox(null);
	//$("#p51_cmb_gondola").combobox(null);
	
	$.datepicker.setDefaults($.datepicker.regional['esDiasServicio']);
	$( "#p51_fechaInicioDatePicker" ).datepicker({
        onSelect: function(dateText, inst) {
        	$('#gridP51FP').jqGrid('clearGridData');
        	$('#gridP51AFI').jqGrid('clearGridData');
        
        	var message = p51ValidarFechas();
        	if (null != message){
        		createAlert(message,"ERROR");
        	}
       }
   }).prop({
	   disableBeforeDay:'S',
	   dayControl: new Date()
	});

	$("#p51_fechaFinDatePicker" ).datepicker({
        onSelect: function(dateText, inst) {
        	$('#gridP51FP').jqGrid('clearGridData');
        	$('#gridP51AFI').jqGrid('clearGridData');
        
        	var message = p51ValidarFechas();
        	if (null != message){
        		createAlert(message,"ERROR");
        	}
       }
   });
	//Se deshabilitan los calendarios porque son de consulta
	$("#p51_fechaInicioDatePicker" ).datepicker("disable");
	$("#p51_fechaFinDatePicker" ).datepicker("disable");
}

function initializeScreenP51(){
	
	cargarParametrosCalendarioCabP51();
	
	$.datepicker.setDefaults($.datepicker.regional['esDiasServicio']);
	$( "#p51_fechaInicioDatePicker" ).datepicker({
        onSelect: function(dateText, inst) {
        	$('#gridP51FP').jqGrid('clearGridData');
        	$('#gridP51AFI').jqGrid('clearGridData');
        
        	var message = p51ValidarFechas();
        	if (null != message){
        		createAlert(message,"ERROR");
        	}
       }
   }).prop({
	   disableBeforeDay:'S',
	   dayControl: new Date()
	});
	cargarParametrosCalendarioCabP51();

	$("#p51_fechaFinDatePicker" ).datepicker({
        onSelect: function(dateText, inst) {
        	$('#gridP51FP').jqGrid('clearGridData');
        	$('#gridP51AFI').jqGrid('clearGridData');
        
        	var message = p51ValidarFechas();
        	if (null != message){
        		createAlert(message,"ERROR");
        	}
       }
   });
	//Se deshabilitan los calendarios porque son de consulta
	$("#p51_fechaInicioDatePicker" ).datepicker("disable");
	$("#p51_fechaFinDatePicker" ).datepicker("disable");
	
	var result=cleanFilterSelectionP51(RESET_CONST_P51);
	if ($("#centerId").val()!=null && $("#centerId").val()!=''){
		load_cmbOfertaP51();
	}
	controlCentroP51();
	
	$("#p51_rad_tipoFiltro").filter('[value=1]').attr('checked', true);
	$('#p51_fld_referencia').filter_input({regex:'[0-9]'});
	
	$(".controlReturnP51").keydown(function(e) {
	    if(e.which == 13) {
	    	e.preventDefault();
	    	finderP51();
	    }
	});
	$("#p52_btn_ayudaNuevoOferta").click(function () {
   		createAlert(replaceSpecialCharacters(mensajeAyudaNuevoOferta), "HELP");
	});
	events_p51_rad_tipoFiltro();
	events_p51_btn_buscar();
	events_p51_btn_guardar();
	events_p51_btn_cancelar();
}

function cargarParametrosCalendarioCabP51(){
	$("#codCentroCalendario").val($("#centerId").val());
	$("#identificadorCalendario").val("");
	$("#identificadorSIACalendario").val("");
	$("#clasePedidoCalendario").val("");
	$("#recargarParametrosCalendario").val("S");
	$("#cargadoDSCalendario").val("N");
}

function load_cmbSeccionP51(oferta){
	var options = "";
	var optionNull = "";
	$("#p51_cmb_seccion").combobox(null);
	var ofer = ofertaCombo.split('-');
	var vAgruComerRefPedidos=new VAgruComerOfertaPa($("#centerId").val(), ofer[0], ofer[1], "I2");
	var objJson = $.toJSON(vAgruComerRefPedidos);	
	if ($.browser.msie){
		$("#cargandoIE").show();
	} else {
		$("#cargando").css("position", "fixed" );
	}
	 $.ajax({
		type : 'POST',
		url : './nuevoPedidoAdicionalOferta/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {		
			   for (i = 0; i < data.length; i++){
					options = options + "<option value='" + data[i].grupo1 + "*" + data[i].grupo2 + "'>" + p51_formatDescripcionCombo(data[i].grupo2, data[i].descripcion) + "</option>"; 
			   }
			   $("select#p51_cmb_seccion").html(options);
			   if ($.browser.msie){
					$("#cargandoIE").hide();
				} else {
					$("#cargando").css("position", "relative" );
				}
			   
		},
		error : function (xhr, status, error){
			if ($.browser.msie){
				$("#cargandoIE").hide();
			} else {
				$("#cargando").css("position", "relative" );
			}
			handleError(xhr, status, error, locale);
        }			
	});
	$("#p51_cmb_seccion").combobox({
        selected: function(event, ui) {
        	
         if ( ui.item.value!="" && ui.item.value!="null") {
	           if ($("#p51_cmb_seccion").val()!=null){
	        	   var result=cleanFilterSelectionP51(SECTION_CONST_P51);
	        	   load_cmbCategoryP51();
	        	   //load_cmbGondolaP51();
	           }
         }  else{
        	   var result=cleanFilterSelectionP51(OFERTA_CONST_P51);
        	}  
        }  
        ,	
        changed: function(event, ui) {
			   if (ui.item==null || ui.item.value!="" || ui.item.value!="null"){
				   return result=cleanFilterSelectionP51(OFERTA_CONST_P51);
				   
			   }	 
		   }
     });

	$("#p51_cmb_seccion").combobox('autocomplete',optionNull);
	$("#p51_cmb_seccion").combobox('comboautocomplete',null);
	$("#p51_cmb_seccion").combobox("enable");
}

function load_cmbOfertaP51(){
	var options = "";
	var optionNull = "";
	$("#p51_cmb_numeroOferta").combobox(null);
	

	 $.ajax({
		type : 'POST',
		url : './nuevoPedidoAdicionalOferta/loadOfertas.do',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			   for (i = 0; i < data.length; i++){
					options = options + "<option value='" + data[i].anoOferta + "-" + data[i].numOferta + "'>" + data[i].anoOferta + "-" + data[i].numOferta + "</option>"; 
			   }
			   $("select#p51_cmb_numeroOferta").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});
	$("#p51_cmb_numeroOferta").combobox({
        selected: function(event, ui) {
         if ( ui.item.value!="" && ui.item.value!="null") {
        	 ofertaCombo = ui.item.value;
        	 var result=cleanFilterSelectionP51(OFERTA_CONST_P51);
        	 loadOfertaP51(ui.item.value);
         }else{
        	 var result=cleanFilterSelectionP51(RESET_CONST_P51);
         }
        }  
        ,	
        changed: function(event, ui) { 
			   if (ui.item==null || ui.item.value!="" || ui.item.value!="null"){
				   return result=cleanFilterSelectionP51(RESET_CONST_P51);
			   }	 
		   }
     });
	$("#p51_cmb_numeroOferta").combobox('autocomplete',optionNull);
	$("#p51_cmb_numeroOferta").combobox('comboautocomplete',null);
}

function loadOfertaP51(oferta){
	if ($("#centerId").val()==null || $("#centerId").val()==""){
		createAlert(centerRequiredP51, "ERROR");
	} else {
		if ($.browser.msie){
			$("#cargandoIE").show();
		} else {
			$("#cargando").css("position", "fixed" );
		}
		
	 $.ajax({
		type : 'POST',
		url : './nuevoPedidoAdicionalOferta/getOferta.do?oferta='+oferta,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {
			var fechaIni;
			fechaIni = devuelveDate(data.fechaIni);
			//$("#p51_fechaInicioDatePicker").datepicker( 'option', 'minDate', new Date());	
			$("#p51_fechaFinDatePicker").datepicker( 'option', 'minDate', new Date());	
			$("#p51_fechaInicioDatePicker").datepicker("setDate", fechaIni);
			$("#p51_fechaFinDatePicker").datepicker("setDate", devuelveDate(data.fechaFin));
			$("#p51_fechaInicioDatePicker" ).datepicker("enable");
			$("#p51_fechaFinDatePicker" ).datepicker("enable");

			$("#p51_txt_tipoOferta").val(data.tipoOferta);
			
			load_cmbSeccionP51(oferta);
			
		},
		error : function (xhr, status, error){
			
			if ($.browser.msie){
				$("#cargandoIE").hide();
			} else {
				$("#cargando").css("position", "relative" );
			}
			handleError(xhr, status, error, locale);
        }			
	});		
	}

}


function load_cmbCategoryP51(){
	var options = "";
	var optionNull = "";
	$("#p51_cmb_categoria").combobox(null);
	var ofer = ofertaCombo.split('-');
	var valorSeccion = $("#p51_cmb_seccion").val().split('*');
	var codArea = valorSeccion[0];
	var codSeccion = valorSeccion[1];
	var vAgruComerRefPedidos=new VAgruComerOfertaPa($("#centerId").val(),ofer[0], ofer[1], "I3",codArea,codSeccion);
	var objJson = $.toJSON(vAgruComerRefPedidos);	
	 $.ajax({
		type : 'POST',
		url : './nuevoPedidoAdicionalOferta/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			   for (i = 0; i < data.length; i++){
					options = options + "<option value='" + data[i].grupo3 + "'>" + p51_formatDescripcionCombo(data[i].grupo3, data[i].descripcion) + "</option>"; 
			   }
			   $("select#p51_cmb_categoria").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
	
	$("#p51_cmb_categoria").combobox('autocomplete',optionNull);
	$("#p51_cmb_categoria").combobox('comboautocomplete',null);
}

/*function load_cmbGondolaP51(){
	var options = "";
	var optionNull = "";
	$("#p51_cmb_gondola").combobox(null);
	var ofer = ofertaCombo.split('-');
	var valorSeccion = $("#p51_cmb_seccion").val().split('*');
	var codArea = valorSeccion[0];
	var codSeccion = valorSeccion[1];
	var codCategoria = $("#p51_cmb_categoria").combobox('getValue');
	var vSicFPromociones=new VSicFPromociones(ofer[0], ofer[1], $("#centerId").val(), $("#centerNegocio").val(),$("#centerEnsena").val(),$("#centerArea").val(), codArea, codSeccion, codCategoria, null);
	var objJson = $.toJSON(vSicFPromociones);	
	 $.ajax({
		type : 'POST',
		url : './nuevoPedidoAdicionalOferta/loadGondola.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			   for (i = 0; i < data.length; i++){
				   if (data[i].cabGondola == null || data[i].cabGondola == ''){
					   options = options + "<option value='-1'>" + gondolaValorTodos + "</option>";
				   }else{
						options = options + "<option value='" + data[i].cabGondola + "'>" + data[i].cabGondola + "</option>";
				   }
			   }
			   $("select#p51_cmb_gondola").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
	
	$("#p51_cmb_gondola").combobox('autocomplete',optionNull);
	$("#p51_cmb_gondola").combobox('comboautocomplete',null);
}*/

function cleanFilterSelectionP51(componentName){
	if (componentName == RESET_CONST_P51){
		ofertaCombo = null;
		$("#p51_cmb_numeroOferta").combobox(null);
		$("select#p51_cmb_numeroOferta").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p51_cmb_numeroOferta").combobox('autocomplete','');
		$("#p51_cmb_numeroOferta").combobox('comboautocomplete',null);
	}
	if (componentName == OFERTA_CONST_P51 || componentName == RESET_CONST_P51){		
		$("select#p51_cmb_seccion").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p51_cmb_seccion").combobox('autocomplete','');
		$("#p51_cmb_seccion").combobox('comboautocomplete',null);
		if ($("#centerId").val()!=null && $("#centerId").val()!='' && $("#p51_cmb_numeroOferta").combobox('getValue') != null){
			load_cmbSeccionP51(null);
		}
	}
	if (componentName == SECTION_CONST_P51 || componentName == OFERTA_CONST_P51 || componentName == RESET_CONST_P51){
		$("select#p51_cmb_categoria").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p51_cmb_categoria").combobox('autocomplete','');
		$("#p51_cmb_categoria").combobox('comboautocomplete',null);
		
		//$("select#p51_cmb_gondola").html("<option value=null selected='selected'>"+''+"</option>");
		//$("#p51_cmb_gondola").combobox('autocomplete','');
		//$("#p51_cmb_gondola").combobox('comboautocomplete',null);
	}
	//Ante cualquier cambio en los combos se inicializan las tablas
	$('#gridP51FP').jqGrid('clearGridData');
	$('#gridP51AFI').jqGrid('clearGridData');
	//jQuery(gridP51FP.nameJQuery).jqGrid('setGridWidth', $("#p51_areaCondicionFiltro").width()-4, true);
	//jQuery(gridP51AFI.nameJQuery).jqGrid('setGridWidth', $("#p51_areaCondicionFiltro").width()-4, true);

	disableFilterSelectionP51(componentName);
	return true;
}

function disableFilterSelectionP51(componentName){
	if (componentName == RESET_CONST_P51){
		if ($("#centerId").val()!=null && $("#centerId").val()!=''){
			$("#p51_cmb_numeroOferta").combobox("enable");
		}else{
			$("#p51_cmb_numeroOferta").combobox("disable");
		}
		$("#p51_cmb_seccion").combobox("disable");
		$("#p51_cmb_categoria").combobox("disable");
		//$("#p51_cmb_gondola").combobox("disable");
	}else if(componentName==OFERTA_CONST_P51 ){
		if ($("#p51_cmb_numeroOferta").combobox('getValue')!='' && $("#p51_cmb_numeroOferta").combobox('getValue')!=null && $("#p51_cmb_numeroOferta").combobox('getValue')!='null'){
			$("#p51_cmb_seccion").combobox("enable");
		}else{
			$("#p51_cmb_seccion").combobox("disable");
		}
		$("#p51_cmb_categoria").combobox("disable");
		//$("#p51_cmb_gondola").combobox("disable");
	}else if(componentName==SECTION_CONST_P51 ){
		$("#p51_cmb_categoria").combobox("enable");
		//$("#p51_cmb_gondola").combobox("enable");
	}
}

function events_p51_rad_tipoFiltro(){
    $("input[name='p51_rad_tipoFiltro']").click(function () {
    	if ($("input[name='p51_rad_tipoFiltro']:checked").val() == "1"){
    		//Estructura comercial
    		 $("div#p51_filterEstructura").attr("style", "display:inherit");
    		 $("div#p51_filtroReferencia").attr("style", "display:none");
    		 $("#p51_fld_referencia").val("");
    		 var result= cleanFilterSelectionP51(OFERTA_CONST_P51);
    		 $('#gridP51FP').jqGrid('clearGridData');
    		 $('#gridP51AFI').jqGrid('clearGridData');
    		 jQuery(gridP51FP.nameJQuery).jqGrid('setGridWidth', $("#p51_AreaOfertaDatos").width()-4, true);
    		 jQuery(gridP51AFI.nameJQuery).jqGrid('setGridWidth', $("#p51_AreaOfertaDatos").width()-4, true);
    		 
    	} else { //Referencias
    		$("div#p51_filterEstructura").attr("style", "display:none");
   		 	$("div#p51_filtroReferencia").attr("style", "display:inherit");
   		    var result= cleanFilterSelectionP51(OFERTA_CONST_P51);
	 		$('#gridP51FP').jqGrid('clearGridData');
	 		$('#gridP51AFI').jqGrid('clearGridData');
	   		jQuery(gridP51FP.nameJQuery).jqGrid('setGridWidth', $("#p51_AreaOfertaDatos").width()-4, true);
			jQuery(gridP51AFI.nameJQuery).jqGrid('setGridWidth', $("#p51_AreaOfertaDatos").width()-4, true);
    	}    	 
    });
}

function findValidationP51(){
	var messageVal=null;
	
	if ( null == $("#p51_cmb_numeroOferta").combobox('getValue')){
		messageVal = ofertaRequiredP51;
	} else if ($("input[name='p51_rad_tipoFiltro']:checked").val() == "1"){
		//Estructura comercial
		if ($("#centerId").val()==null || $("#centerId").val()==""){
			messageVal = centerRequiredP51;
     	}else if( $("#p51_cmb_seccion").combobox('getValue')=="null" || $("#p51_cmb_seccion").combobox('getValue')==null){
			messageVal = sectionRequiredP51;
     	}

	} else { //Referencias
		if ($("#centerId").val()==null || $("#centerId").val()==""){
			messageVal = centerRequiredP51;
			
		}else if($("#p51_fld_referencia").val()==null || $("#p51_fld_referencia").val()=='' ){
			messageVal = referenceRequiredP51;
		}	
		
	}   
	var messAux = p51ValidarFechas();
	if (null != messAux){
		messageVal = messAux;
	}
	return messageVal;
	
}

function findValidationSaveP51(){
	var messageVal=null;
	obtenerListadoModificadosP51();
	
	if (listadoModificadosP51== null || listadoModificadosP51.length == 0){
		messageVal = emptyListaModificadosP51;
	}
	return messageVal;
	
}

function events_p51_btn_buscar(){
	$("#p51_btn_find").click(function () {
		finderP51();
	});	
}

function events_p51_btn_guardar(){
	$("#p51_btn_save").click(function () {
		guardarDatosP51();
	});	
}

function finderP51(){
	var messageVal=findValidationP51();
	
	if (messageVal!=null){
		$('#gridP51FP').jqGrid('clearGridData');
		$('#gridP51AFI').jqGrid('clearGridData');
		jQuery(gridP51FP.nameJQuery).jqGrid('setGridWidth', $("#p51_AreaOfertaDatos").width()-4, true);
		jQuery(gridP51AFI.nameJQuery).jqGrid('setGridWidth', $("#p51_AreaOfertaDatos").width()-4, true);
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		$('#gridP51FP').setGridParam({page:1});
		$('#gridP51AFI').setGridParam({page:1});
		reloadDataP51('N');
	}
}

function guardarDatosP51(){
	
	//Inicializamos el número de fila para los campos ocultos que tenemos.
	numRowP51=1;

	//Se carga el centro para los calendarios
	$("#codCentroCalendario").val($("#centerId").val());
	$("#clasePedidoCalendario").val("M");
	$("#cargadoDSCalendario").val("S");

	var messageVal=findValidationSaveP51();
	var tipoListado;
	if ($("#p51_txt_flgFrescoPuro").val()=='S'){
		tipoListado = 'fp';
	}else{
		tipoListado = 'afi';
	}
	if (messageVal!=null){
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		
		var codArea = null;
		var valorSeccion = null;
		var codSeccion = null;
		var codCategoria = null;
		var codArticulo = null;

		if ($("input[name='p51_rad_tipoFiltro']:checked").val() == "1"){
			if( $("#p51_cmb_seccion").combobox('getValue')!="null" && $("#p51_cmb_seccion").combobox('getValue')!=null){
				valorSeccion = $("#p51_cmb_seccion").combobox('getValue').split('*');
				codArea = valorSeccion[0];
				codSeccion = valorSeccion[1];
				if( $("#p51_cmb_categoria").combobox('getValue')!="null" && $("#p51_cmb_categoria").combobox('getValue')!=null){
					codCategoria = $("#p51_cmb_categoria").combobox('getValue');
				}
			}
		} else {
			codArticulo = $("#p51_fld_referencia").val();
		}
		
		var recordNuevoPedidoOferta = new NuevoPedidoOferta();
		recordNuevoPedidoOferta.codCentro = $("#centerId").val();
		var ofer = ofertaCombo.split('-');
		recordNuevoPedidoOferta.anoOferta = ofer[0];
		recordNuevoPedidoOferta.numOferta = ofer[1];
		recordNuevoPedidoOferta.grupo1 = codArea;
		recordNuevoPedidoOferta.grupo2 = codSeccion;
		recordNuevoPedidoOferta.grupo3 = codCategoria;
		recordNuevoPedidoOferta.codArticulo = codArticulo;
		recordNuevoPedidoOferta.fechaInicio =   $.datepicker.formatDate("ddmmyy",$("#p51_fechaInicioDatePicker").datepicker("getDate"));;
		recordNuevoPedidoOferta.fechaFin =  $.datepicker.formatDate("ddmmyy",$("#p51_fechaFinDatePicker").datepicker("getDate"));
		recordNuevoPedidoOferta.listadoModificados = listadoModificadosP51;
		
		if ($("#p51_txt_flgFrescoPuro").val()=='S'){
			recordNuevoPedidoOferta.flgTipoListado = "FP";
		}else{
			recordNuevoPedidoOferta.flgTipoListado = "AFI";
		}
		
		//Reseteamos el array de modificados.
		listadoModificadosP51 = new Array();
		
		var objJson = $.toJSON(recordNuevoPedidoOferta.prepareNuevoPedidoOfertaToJsonObject());
	
			$("#p51_AreaOferta .loading").css("display", "block");
			if ($("#p51_txt_flgFrescoPuro").val()=='S'){
				gridP51 = gridP51FP;
			}else{
				gridP51 = gridP51AFI;
			}
			if (gridP51.firstLoad) {
				jQuery(gridP51.nameJQuery).jqGrid('setGridWidth', $("#p51_AreaOfertaDatos").width()-4, true);
				gridP51.firstLoad = false;
		        if (gridP51.isColState) {
		            $(this).jqGrid("remapColumns", gridP51.myColumnsState.permutation, true);
		        }
		    } else {
		    	gridP51.myColumnsState = gridP51.restoreColumnState(gridP51.cm);
		    	gridP51.isColState = typeof (gridP51.myColumnsState) !== 'undefined' && gridP51.myColumnsState !== null;
		    }
			$.ajax({
				type : 'POST',
				url : './nuevoPedidoAdicionalOferta/saveDataGrid.do?page=1&max='+gridP51.getRowNumPerPage()+'&index='+gridP51.getSortIndex()+'&sortorder='+gridP51.getSortOrder(),
				data : objJson,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {	
					
					if ($("#p51_txt_flgFrescoPuro").val()=='S'){
						$(gridP51FP.nameJQuery)[0].addJSONData(data.datos);
						gridP51FP.actualPage = data.datos.page;
						gridP51FP.localData = data.datos;
					}else{
						$(gridP51AFI.nameJQuery)[0].addJSONData(data.datos);
						gridP51AFI.actualPage = data.datos.page;
						gridP51AFI.localData = data.datos;
					}
					var ids = jQuery(gridP51.nameJQuery).jqGrid('getDataIDs'), i, l = ids.length;
					var cont = 0;
					
					$("#p51_AreaOferta").hide();
				    for (i = 0; i < l; i++) {
				    	if ($("#p51_txt_flgFrescoPuro").val()=='S'){
				    		//Se indican los parámetros para los calendarios
				    		$("#identificadorCalendario").val("");
				    		$("#identificadorSIACalendario").val("");
			    			$("#clasePedidoCalendario").val("M");
				    		
				    		var bloqueado = $("#"+ids[i]+"_fp_bloqueado").val();
					    	if (bloqueado != 'S'){
					    		jQuery(gridP51FP.nameJQuery).jqGrid('editRow', ids[i], false);
						    	
						    	
						    	$("#"+ids[i]+"_cantidad1").filter_input({regex:'[0-9,]'});
						    	$("#"+ids[i]+"_cantidad2").filter_input({regex:'[0-9,]'});
						    	$("#"+ids[i]+"_cantidad3").filter_input({regex:'[0-9,]'});
	
						    	$("#"+ids[i]+"_cantidad1").formatNumber({format:"0.00",locale:"es"});
						    	$("#"+ids[i]+"_cantidad2").formatNumber({format:"0.00",locale:"es"});
						    	$("#"+ids[i]+"_cantidad3").formatNumber({format:"0.00",locale:"es"});
						    	
								$("#"+ids[i]+"_cantidad1").click(function(event) {
									loadPopUpAyudaP51(event);
								});
								$("#"+ids[i]+"_cantidad2").click(function(event) {
									loadPopUpAyudaP51(event);
								});
								$("#"+ids[i]+"_cantidad3").click(function(event) {
									loadPopUpAyudaP51(event);
								});
								$("#"+ids[i]+"_cantidad1").change(function(event) {
									validacionCantidad(this.id, 'S', tipoListado, '1');
								});
								$("#"+ids[i]+"_cantidad2").change(function(event) {
									validacionCantidad(this.id, 'S', tipoListado, '2');
								});
								$("#"+ids[i]+"_cantidad3").change(function(event) {
									validacionCantidad(this.id, 'S', tipoListado, '3');
								});
								
								
								controlVisibilidadCantFP(ids[i]);
								//Pintado de errores en los campos
								if($("#"+ ids[i] + "_fp_codError_orig").val() != null &&  $("#"+ ids[i] + "_fp_codError_orig").val() == '99'){
									marcarValidacionFila(ids[i]);
					    		}
					    	}
				    	}
				    	else
				    	{
				    		//Se indican los parámetros para los calendarios
				    		$("#identificadorCalendario").val("");
				    		$("#identificadorSIACalendario").val("");
			    			$("#clasePedidoCalendario").val("M");
				    		
				    		var bloqueado = $("#"+ids[i]+"_afi_bloqueado").val();
					    	if (bloqueado != 'S'){
					    		jQuery(gridP51AFI.nameJQuery).jqGrid('editRow', ids[i], false);
						    	
						    	
					    		$("#"+ids[i]+"_implInicial").filter_input({regex:'[0-9,]'});
					    		$("#"+ids[i]+"_implFinal").filter_input({regex:'[0-9,]'});
					    		
						    	$("#"+ids[i]+"_implInicial").formatNumber({format:"0.00",locale:"es"});
						    	$("#"+ids[i]+"_implFinal").formatNumber({format:"0.00",locale:"es"});
						    	$("#"+ids[i]+"_implInicial").focus(function(e) {
						    		var id = e.target.id;
						    		var idCompleto = id.split("_");
						    		var fila = parseInt(idCompleto[0],10);
						    		if ($("#"+fila+"_afi_divModificado").is(":hidden") && $("#"+fila+"_afi_divError").is(":hidden")){
						    			var fin = $("#"+fila+"_fechaFin").datepicker('getDate');
						    	        var inicio = $("#"+fila+"_fechaInicio").datepicker('getDate');
						    	        inicio = $.datepicker.formatDate('ddmmyy',inicio);
					    	        	fin = $.datepicker.formatDate('ddmmyy', fin);
						    	        controlFechasAFIP51(inicio, fin, fila, id);
						    		}
								});
								$("#"+ids[i]+"_implFinal").focus(function(e) {
									var id = e.target.id;
									var idCompleto = id.split("_");
						    		var fila = parseInt(idCompleto[0],10);
						    		if ($("#"+fila+"_afi_divModificado").is(":hidden") && $("#"+fila+"_afi_divError").is(":hidden")){
						    			var fin = $("#"+fila+"_fechaFin").datepicker('getDate');
						    	        var inicio = $("#"+fila+"_fechaInicio").datepicker('getDate');
						    	        inicio = $.datepicker.formatDate('ddmmyy',inicio);
					    	        	fin = $.datepicker.formatDate('ddmmyy', fin);
						    	        controlFechasAFIP51(inicio, fin, fila, id);
						    		}
								});
								$("#"+ids[i]+"_implInicial").click(function(event) {
									loadPopUpAyudaP51(event);
								});
								$("#"+ids[i]+"_implFinal").click(function(event) {
									loadPopUpAyudaP51(event);
								});
								$("#"+ids[i]+"_implInicial").change(function() {
									validacionImplInicial(this.id, 'S', tipoListado);
								});
								$("#"+ids[i]+"_implFinal").change(function() {
									validacionImplFinal(this.id, 'S', tipoListado);
								});
								
								
								controlVisibilidadImplAFI(ids[i]);
								//Pintado de errores en los campos
								if($("#"+ ids[i] + "_afi_codError_orig").val() != null &&  $("#"+ ids[i] + "_afi_codError_orig").val() == '99'){
									marcarValidacionFila(ids[i]);
					    		}
					    	}								
								
				    	}
		    		//Las líneas bloquedas se muestran sin editar y sin resaltar

				    	cont++;
				    }
				  
				    $("#p51_AreaOferta").show();

					//Cuando pulse la columna para ordenar por icono tenemos que llamar manualmente al reload para que reordene,
					//ya que al tener el jsp campos editables no funciona de la manera convencional.
					//$("#gridP51FP_estado").unbind('click');
					//$("#gridP51FP_estado").bind("click", function(e) {
						//Establecemos el parámetro de ordenación por el campo de los iconos.
						//$('#gridP51FP').setGridParam({sortname:'estado'});
						//$('#gridP51FP').setGridParam({page:1});
						//reloadDataP51('S');
					 //}); 

					//$("#gridP51AFI_estado").unbind('click');
					//$("#gridP51AFI_estado").bind("click", function(e) {
						//Establecemos el parámetro de ordenación por el campo de los iconos.
						//$('#gridP51AFI').setGridParam({sortname:'estado'});
						//$('#gridP51AFI').setGridParam({page:1});
						//reloadDataP51('S');
					 //}); 

					$("#p51_btn_find").focus();

					$("#p51_AreaOferta .loading").css("display", "none");	
					if (data.datos.records == null || data.datos.records == 0){
						if ($("input[name='p51_rad_tipoFiltro']:checked").val() == "1"){
							createAlert(replaceSpecialCharacters(gridEmptyRecordsP51), "ERROR");
						}
						else
						{
							if ($("#p51_txt_flgFrescoPuro").val()=='S'){
								createAlert(replaceSpecialCharacters(gridEmptyRecordsP51FP), "ERROR");
							}
							else
							{
								createAlert(replaceSpecialCharacters(gridEmptyRecordsP51AFI), "ERROR");
							}
						}
					} 
					
					//Recuperación de datos para mensaje de guardado
					var totalError = data.totalErroneos;
					p51ResultadoSaveGrid(totalError);

				},
				error : function (xhr, status, error){
					$("#p51_AreaOferta .loading").css("display", "none");	
					handleError(xhr, status, error, locale);				
		        }			
			});		
	}
}

function reloadDatosOferta() {

	$("#p50_pestanaOfertaCargada").val("S");
	loadP51Oferta(locale);
}

function loadP51Oferta(locale){
	gridP51FP = new GridP51FP(locale);
	gridP51AFI = new GridP51AFI(locale);
	
	var jqxhr = $.getJSON(gridP51FP.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				gridP51FP.colNames = data.gridColNamesP51FP;
				gridP51AFI.colNames = data.gridColNamesP51AFI;
				gridTitleP51FP = data.gridTitleP51FP;
				gridTitleP51AFI = data.gridTitleP51AFI;
				gridEmptyRecordsP51 = data.gridEmptyRecordsP51;
				gridEmptyRecordsP51FP = data.gridEmptyRecordsP51FP;
				gridEmptyRecordsP51AFI = data.gridEmptyRecordsP51AFI;
				emptyListaModificadosP51 = data.emptyListaModificadosP51;
				centerRequiredP51=data.centerRequiredP51;
				ofertaRequiredP51=data.ofertaRequiredP51;
				referenceRequiredP51=data.referenceRequiredP51;
				iconoModificadoP51 = data.iconoModificadoP51;
				tableFilterP51=data.tableFilterP51;
				numRowP51=1;
				cantidadIncorrectaP51=data.cantidadIncorrectaP51;
				loadP51FPMock(gridP51FP);
				loadP51AFIMock(gridP51AFI);
				p51SetHeadersTitlesFP(data);
				p51SetHeadersTitlesAFI(data);
				invalidCant1P51 = data.invalidCant1P51;
				invalidCant2P51 = data.invalidCant2P51;
				invalidCant3P51 = data.invalidCant3P51;
				invalidImplInicialP51 = data.invalidImplInicialP51;
				invalidImplFinalP51 = data.invalidImplFinalP51;
				datosObligatoriosFilaP51 = data.datosObligatoriosFilaP51;
				literalAyuda1ConOferta = data.literalAyuda1ConOferta;
				literalInvalidDates = data.literalInvalidDates;
				mensajeAyudaNuevoOferta = data.mensajeAyudaNuevoOferta;
				nuevoOfertaSaveResultOK = data.nuevoOfertaSaveResultOK;
				nuevoOfertaSaveResultError = data.nuevoOfertaSaveResultError;
				sectionRequiredP51 = data.sectionRequiredP51;
				gondolaValorTodos = data.gondolaValorTodos;
				p51fechaSelBloqueosEncargosMontajes = data.p51fechaSelBloqueosEncargosMontajes;
				p51fechaSelBloqueosEncargos = data.p51fechaSelBloqueosEncargos;
				p51fechaSelBloqueosMontajes = data.p51fechaSelBloqueosMontajes;
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
         });
}

function loadP51FPMock(gridP51FP) {
	//para hacer cabeceras
		$(gridP51FP.nameJQuery).jqGrid({
			ajaxGridOptions : {
				contentType : 'application/json; charset=utf-8',
				cache : false
			},
			url : './misumi/resources/mock.json',
			datatype : 'json',
			contentType : 'application/json',
			mtype : 'POST',
//			headertitles: true ,
			colNames : gridP51FP.colNames,
			colModel : gridP51FP.cm,
			rowNum : 30,
			rowList : [ 30, 40, 50 ],
			height : "100%",
			autowidth : true,
			width : "auto",
			rownumbers : true,
			autoencode:true,
			shrinkToFit:true,
			pager : gridP51FP.pagerNameJQuery,
			viewrecords : true,
			caption : gridTitleP51FP,
			altclass: "ui-priority-secondary",
			altRows: true, //false, para que el grid no muestre cebrado
			hidegrid : false, //false, para ocultar el boton que colapsa el grid.
			sortable : true,
			index: gridP51FP.sortIndex,
			sortname: gridP51FP.sortIndex,
			sortorder: gridP51FP.sortOrder,
			emptyrecords : gridP51FP.emptyRecords,
			gridComplete : function() {
				gridP51FP.headerHeight("gridP51FPHeader");  
			},
			loadComplete : function(data) {
				gridP51FP.actualPage = data.page;
				gridP51FP.localData = data;
				gridP51FP.sortIndex = null;
				gridP51FP.sortOrder = null;
				$("#p51_AreaOferta .loading").css("display", "none");	
				if (gridP51FP.firstLoad){
					jQuery(gridP51FP.nameJQuery).jqGrid('setGridWidth', $("#p51_AreaOfertaDatos").width()-4, true);
				}
			},
			onPaging : function(postdata) {			
				alreadySorted = false;
				gridP51FP.sortIndex = null;
				gridP51FP.sortOrder = null;
				gridP51FP.saveColumnState.call($(this), this.p.remapColumns);
				reloadDataP51('S');
				return 'stop';
			},
			onSelectRow: function(id){

			},
			resizeStop: function () {
				gridP51FP.modificado = true;
				gridP51FP.saveColumnState.call($(this),gridP51FP.myColumnsState);
				jQuery(gridP51FP.nameJQuery).jqGrid('setGridWidth', $("#p51_AreaOfertaDatos").width()-4, false);
				
            },
			onSortCol : function (index, columnIndex, sortOrder){
				gridP51FP.sortIndex = index;
				gridP51FP.sortOrder = sortOrder;
				gridP51FP.saveColumnState.call($(this), this.p.remapColumns);
				reloadDataP51('S');
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
		jQuery(gridP51FP.nameJQuery).jqGrid('navGrid',gridP51FP.pagerNameJQuery,{
				add:false,edit:false,del:false,search:false,refresh:false}
		); 
		jQuery(gridP51FP.nameJQuery).jqGrid('navButtonAdd',gridP51FP.pagerNameJQuery,{ 
				caption: tableFilterP51, title: "Reordenar Columnas", 
			onClickButton : function (){ 
				jQuery(gridP51FP.nameJQuery).jqGrid('columnChooser',
						{
                    		"done": function(perm) {
                    			if (perm) {
                    				var autowidth = true;
                    				if (gridP51FP.modificado == true){
                    					this.jqGrid("remapColumns", perm, true);
                    					autowidth = false;
                    					gridP51FP.myColumnsState =  gridP51FP.restoreColumnState(gridP51FP.cm);
                        		    	gridP51FP.isColState = typeof (gridP51FP.myColumnsState) !== 'undefined' && gridP51FP.myColumnsState !== null;
                        		    	
                        		    	 var colModel =jQuery(gridP51FP.nameJQuery).jqGrid('getGridParam', 'colModel'); 
                        		    	 var l = colModel.length;
                        		         var colItem; 
                        		         var cmName;
                        		         var colStates = gridP51FP.myColumnsState.colStates;
                        		         var cIndex = 2;
                        		         for (i = 0; i < l; i++) {
                        		             colItem = colModel[i];
                        		             cmName = colItem.name;
                        		             if (colItem.hidden !== true && cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {
                        		            	 
                        		            	 jQuery(gridP51FP.nameJQuery).jqGrid('setColProp',cmName,{width:colStates[cmName].width});
                        		            	 var cad =gridP51FP.nameJQuery+'_'+cmName;
                        		            	 var ancho = "'"+colStates[cmName].width+"px'";
                        		            	 var cell = jQuery('table'+gridP51FP.nameJQuery+' tr.jqgfirstrow td:nth-child(' + (i+1) + ')');
                        		            	 cell.css("width", colStates[cmName].width + "px");
                        		            	
                        		            	 jQuery(cad).css("width", colStates[cmName].width + "px");
                        		            	
                        		             }
                        		         }
                        		         
                    				} else {
                    					this.jqGrid("remapColumns", perm, true);
                    				}
                    				gridP51FP.saveColumnState.call(this, perm);
                    				jQuery(gridP51FP.nameJQuery).jqGrid('setGridWidth', $("#p51_AreaOfertaDatos").width()-4, autowidth);
                    			}
                    			
                    		}
						}		
				); } });
}

function loadP51AFIMock(gridP51AFI) {
	//para hacer cabeceras
		$(gridP51AFI.nameJQuery).jqGrid({
			ajaxGridOptions : {
				contentType : 'application/json; charset=utf-8',
				cache : false
			},
			url : './misumi/resources/mock.json',
			datatype : 'json',
			contentType : 'application/json',
			mtype : 'POST',
//			headertitles: true ,
			colNames : gridP51AFI.colNames,
			colModel : gridP51AFI.cm,
			rowNum : 30,
			rowList : [ 30, 40, 50 ],
			height : "100%",
			autowidth : true,
			width : "auto",
			rownumbers : true,
			autoencode:true,
			shrinkToFit:true,
			pager : gridP51AFI.pagerNameJQuery,
			viewrecords : true,
			caption : gridTitleP51AFI,
			altclass: "ui-priority-secondary",
			altRows: true, //false, para que el grid no muestre cebrado
			hidegrid : false, //false, para ocultar el boton que colapsa el grid.
			sortable : true,
			index: gridP51AFI.sortIndex,
			sortname: gridP51AFI.sortIndex,
			sortorder: gridP51AFI.sortOrder,
			emptyrecords : gridP51AFI.emptyRecords,
			gridComplete : function() {
				gridP51AFI.headerHeight("gridP51AFIHeader");  
			},
			loadComplete : function(data) {
				gridP51AFI.actualPage = data.page;
				gridP51AFI.localData = data;
				gridP51AFI.sortIndex = null;
				gridP51AFI.sortOrder = null;
				$("#p51_AreaOferta .loading").css("display", "none");	
				if (gridP51AFI.firstLoad){
					jQuery(gridP51AFI.nameJQuery).jqGrid('setGridWidth', $("#p51_AreaOfertaDatos").width()-4, true);
				}
			},
			onPaging : function(postdata) {			
				alreadySorted = false;
				gridP51AFI.sortIndex = null;
				gridP51AFI.sortOrder = null;
				gridP51AFI.saveColumnState.call($(this), this.p.remapColumns);
				reloadDataP51('S');
				return 'stop';
			},
			onSelectRow: function(id){

			},
			resizeStop: function () {
				gridP51AFI.modificado = true;
				gridP51AFI.saveColumnState.call($(this),gridP51AFI.myColumnsState);
				jQuery(gridP51AFI.nameJQuery).jqGrid('setGridWidth', $("#p51_AreaOfertaDatos").width()-4, false);
			},
			onSortCol : function (index, columnIndex, sortOrder){
				gridP51AFI.sortIndex = index;
				gridP51AFI.sortOrder = sortOrder;
				gridP51AFI.saveColumnState.call($(this), this.p.remapColumns);
				reloadDataP51('S');
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
		jQuery(gridP51AFI.nameJQuery).jqGrid('navGrid',gridP51AFI.pagerNameJQuery,{
				add:false,edit:false,del:false,search:false,refresh:false}
		); 
		jQuery(gridP51AFI.nameJQuery).jqGrid('navButtonAdd',gridP51AFI.pagerNameJQuery,{ 
				caption: tableFilterP51, title: "Reordenar Columnas", 
			onClickButton : function (){ 
				jQuery(gridP51AFI.nameJQuery).jqGrid('columnChooser',
						{
                    		"done": function(perm) {
                    			if (perm) {
                    				var autowidth = true;
                    				if (gridP51AFI.modificado == true){
                    					autowidth = false;
                    					this.jqGrid("remapColumns", perm, true);
                    					gridP51AFI.myColumnsState =  gridP51AFI.restoreColumnState(gridP51AFI.cm);
                        		    	gridP51AFI.isColState = typeof (gridP51AFI.myColumnsState) !== 'undefined' && gridP51AFI.myColumnsState !== null;
                        		    	
                        		    	 var colModel =jQuery(gridP51AFI.nameJQuery).jqGrid('getGridParam', 'colModel'); 
                        		    	 var l = colModel.length;
                        		         var colItem; 
                        		         var cmName;
                        		         var colStates = gridP51AFI.myColumnsState.colStates;
                        		         var cIndex = 2;
                        		         for (i = 0; i < l; i++) {
                        		             colItem = colModel[i];
                        		             cmName = colItem.name;
                        		             if (colItem.hidden !== true && cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {
                        		            	 
                        		            	 jQuery(gridP51AFI.nameJQuery).jqGrid('setColProp',cmName,{width:colStates[cmName].width});
                        		            	 var cad =gridP51AFI.nameJQuery+'_'+cmName;
                        		            	 var ancho = "'"+colStates[cmName].width+"px'";
                        		            	 var cell = jQuery('table'+gridP51AFI.nameJQuery+' tr.jqgfirstrow td:nth-child(' + (i+1) + ')');
                        		            	 cell.css("width", colStates[cmName].width + "px");
                        		            	
                        		            	 jQuery(cad).css("width", colStates[cmName].width + "px");
                        		            	
                        		             }
                        		         }
                        		         
                    				} else {
                    					this.jqGrid("remapColumns", perm, true);
                    				}
                    				//gridP51AFI.saveColumnState.call(this, perm);
                    				jQuery(gridP51AFI.nameJQuery).jqGrid('setGridWidth', $("#p51_AreaOfertaDatos").width()-4, autowidth);
                    				gridP51AFI.saveColumnState.call(this, perm);
                    			}
                    		}
						}		
				); } });

}

function p51SetHeadersTitlesFP(data){
   	var colModel = $(gridP51FP.nameJQuery).jqGrid("getGridParam", "colModel");
    $.each(colModel, function(i) {
    	if (colModel[i].name!="rn"){
	    	$("#jqgh_gridP51FP_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
    	}
     });
}

function p51SetHeadersTitlesAFI(data){
	
   	var colModel = $(gridP51AFI.nameJQuery).jqGrid("getGridParam", "colModel");
    $.each(colModel, function(i) {
    	if (colModel[i].name!="rn"){
	    	$("#jqgh_gridP51AFI_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
    	}
     });
}

function reloadDataP51(recarga) {
	//Inicializamos el número de fila para los campos ocultos que tenemos.
	numRowP51=1;
	//Se carga el centro para los calendarios
	$("#codCentroCalendario").val($("#centerId").val());
	$("#clasePedidoCalendario").val("M");
	$("#cargadoDSCalendario").val("S");
	
	var messageVal=findValidationP51();
	var tipoListado;
	if ($("#p51_txt_flgFrescoPuro").val()=='S'){
		tipoListado = 'fp';
	}else{
		tipoListado = 'afi';
	}
	if (messageVal!=null){
		$('#gridP51FP').jqGrid('clearGridData');
		$('#gridP51AFI').jqGrid('clearGridData');
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		obtenerListadoModificadosP51();
		
		var codArea = null;
		var valorSeccion = null;
		var codSeccion = null;
		var codCategoria = null;
		var codArticulo = null;
		var cabGondola = null;
		var nelCodN1 = null;
		var nelCodN2 = null;
		var nelCodN3 = null;

		if ($("input[name='p51_rad_tipoFiltro']:checked").val() == "1"){
			if( $("#p51_cmb_seccion").combobox('getValue')!="null" && $("#p51_cmb_seccion").combobox('getValue')!=null){
				valorSeccion = $("#p51_cmb_seccion").combobox('getValue').split('*');
				codArea = valorSeccion[0];
				codSeccion = valorSeccion[1];
				if( $("#p51_cmb_categoria").combobox('getValue')!="null" && $("#p51_cmb_categoria").combobox('getValue')!=null){
					codCategoria = $("#p51_cmb_categoria").combobox('getValue');
				}
				//Ocultado temporal de góndola
				//if( $("#p51_filtroGondola").is(":visible") && $("#p51_cmb_gondola").combobox('getValue')!="null" && $("#p51_cmb_gondola").combobox('getValue')!=null){
				//	cabGondola = $("#p51_cmb_gondola").combobox('getValue');
				//}
				nelCodN1 = $("#centerNegocio").val();
				nelCodN2 = $("#centerEnsena").val();
				nelCodN3 = $("#centerArea").val();
			}
		} else {
			codArticulo = $("#p51_fld_referencia").val();
		}
		var recordNuevoPedidoOferta = new NuevoPedidoOferta();

		recordNuevoPedidoOferta.codCentro = $("#centerId").val();
		var ofer = ofertaCombo.split('-');
		recordNuevoPedidoOferta.anoOferta = ofer[0];
		recordNuevoPedidoOferta.numOferta = ofer[1];
		recordNuevoPedidoOferta.grupo1 = codArea;
		recordNuevoPedidoOferta.grupo2 = codSeccion;
		recordNuevoPedidoOferta.grupo3 = codCategoria;
		recordNuevoPedidoOferta.codArticulo = codArticulo;
		recordNuevoPedidoOferta.fechaInicio =   $.datepicker.formatDate("ddmmyy",$("#p51_fechaInicioDatePicker").datepicker("getDate"));;
		recordNuevoPedidoOferta.fechaFin =  $.datepicker.formatDate("ddmmyy",$("#p51_fechaFinDatePicker").datepicker("getDate"));
		recordNuevoPedidoOferta.listadoModificados = listadoModificadosP51;
		if ($("#p51_txt_flgFrescoPuro").val()=='S'){
			recordNuevoPedidoOferta.flgTipoListado = "FP";
		}else{
			recordNuevoPedidoOferta.flgTipoListado = "AFI";
		}
		//Datos de góndola
		recordNuevoPedidoOferta.cabGondola = cabGondola;
		recordNuevoPedidoOferta.nelCodN1 = nelCodN1;
		recordNuevoPedidoOferta.nelCodN2 = nelCodN2;
		recordNuevoPedidoOferta.nelCodN3 = nelCodN3;
		
		//Reseteamos el array de modificados.
		listadoModificadosP51 = new Array();
		var objJson = $.toJSON(recordNuevoPedidoOferta.prepareNuevoPedidoOfertaToJsonObject());
			$("#p51_AreaOferta .loading").css("display", "block");
			if ($("#p51_txt_flgFrescoPuro").val()=='S'){
				gridP51 = gridP51FP;
			}else{
				gridP51 = gridP51AFI;
			}
			if (gridP51.firstLoad) {
				jQuery(gridP51.nameJQuery).jqGrid('setGridWidth', $("#p51_AreaOfertaDatos").width()-4, true);
				gridP51.firstLoad = false;
		        if (gridP51.isColState) {
		            $(this).jqGrid("remapColumns", gridP51.myColumnsState.permutation, true);
		        }
		    } else {
		    	gridP51.myColumnsState = gridP51.restoreColumnState(gridP51.cm);
		    	gridP51.isColState = typeof (gridP51.myColumnsState) !== 'undefined' && gridP51.myColumnsState !== null;
		    }
			$.ajax({
				type : 'POST',
				url : './nuevoPedidoAdicionalOferta/loadDataGrid.do?page='+gridP51.getActualPage()+'&max='+gridP51.getRowNumPerPage()+'&index='+gridP51.getSortIndex()+'&sortorder='+gridP51.getSortOrder()+'&recarga='+recarga,
				data : objJson,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {	
					//Actualización de tipo de listado
					if (data.flgTipoListado == 'FP'){
						$("#p51_txt_flgFrescoPuro").val('S');
						gridP51 = gridP51FP;
						tipoListado = "fp";
						$("#gbox_gridP51AFI").hide();
						$("#gbox_gridP51FP").show();
						$("#p51_AreaOferta").show();
						$(gridP51FP.nameJQuery)[0].addJSONData(data.datos);
						gridP51FP.actualPage = data.datos.page;
						gridP51FP.localData = data.datos;
					}else{
						$("#p51_txt_flgFrescoPuro").val('N');
						gridP51 = gridP51AFI;
						tipoListado = "afi";
						$("#gbox_gridP51AFI").show();
						$("#gbox_gridP51FP").hide();
						$("#p51_AreaOferta").show();
						$(gridP51AFI.nameJQuery)[0].addJSONData(data.datos);
						gridP51AFI.actualPage = data.datos.page;
						gridP51AFI.localData = data.datos;
					}
					
					$("#p51_AreaOferta").show();
					var ids = jQuery(gridP51.nameJQuery).jqGrid('getDataIDs'), i, l = ids.length;
					$("#p51_AreaOferta").hide();
					var cont = 0;
				    for (i = 0; i < l; i++) {
				    		
					    	if (data.flgTipoListado == 'FP'){
					    		//Se indican los parámetros para los calendarios
					    		$("#identificadorCalendario").val("");
					    		$("#identificadorSIACalendario").val("");
				    			$("#clasePedidoCalendario").val("M");
					    		
					    		var bloqueado = $("#"+ids[i]+"_fp_bloqueado").val();
						    	if (bloqueado != 'S'){
						    		jQuery(gridP51FP.nameJQuery).jqGrid('editRow', ids[i], false);
							    	
							    	
							    	$("#"+ids[i]+"_cantidad1").filter_input({regex:'[0-9,]'});
							    	$("#"+ids[i]+"_cantidad2").filter_input({regex:'[0-9,]'});
							    	$("#"+ids[i]+"_cantidad3").filter_input({regex:'[0-9,]'});
		
							    	$("#"+ids[i]+"_cantidad1").formatNumber({format:"0.00",locale:"es"});
							    	$("#"+ids[i]+"_cantidad2").formatNumber({format:"0.00",locale:"es"});
							    	$("#"+ids[i]+"_cantidad3").formatNumber({format:"0.00",locale:"es"});
							    	
									$("#"+ids[i]+"_cantidad1").click(function(event) {
										loadPopUpAyudaP51(event);
									});
									$("#"+ids[i]+"_cantidad2").click(function(event) {
										loadPopUpAyudaP51(event);
									});
									$("#"+ids[i]+"_cantidad3").click(function(event) {
										loadPopUpAyudaP51(event);
									});
									$("#"+ids[i]+"_cantidad1").change(function(event) {
										validacionCantidad(this.id, 'S', tipoListado, '1');
									});
									$("#"+ids[i]+"_cantidad2").change(function(event) {
										validacionCantidad(this.id, 'S', tipoListado, '2');
									});
									$("#"+ids[i]+"_cantidad3").change(function(event) {
										validacionCantidad(this.id, 'S', tipoListado, '3');
									});
									
									
									controlVisibilidadCantFP(ids[i]);
									//Pintado de errores en los campos
									if($("#"+ ids[i] + "_fp_codError_orig").val() != null &&  $("#"+ ids[i] + "_fp_codError_orig").val() == '99'){
										marcarValidacionFila(ids[i]);
						    		}
						    	}
				    	}
				    	else
				    	{
				    		//Se indican los parámetros para los calendarios
				    		$("#identificadorCalendario").val("");
				    		$("#identificadorSIACalendario").val("");
			    			$("#clasePedidoCalendario").val("M");
				    		
				    		var bloqueado = $("#"+ids[i]+"_afi_bloqueado").val();
					    	if (bloqueado != 'S'){
					    		jQuery(gridP51AFI.nameJQuery).jqGrid('editRow', ids[i], false);


					    		$("#"+ids[i]+"_implInicial").filter_input({regex:'[0-9,]'});
					    		$("#"+ids[i]+"_implFinal").filter_input({regex:'[0-9,]'});
					    		
						    	$("#"+ids[i]+"_implInicial").formatNumber({format:"0.00",locale:"es"});
						    	$("#"+ids[i]+"_implFinal").formatNumber({format:"0.00",locale:"es"});
						    	$("#"+ids[i]+"_implInicial").change(function(e) {
						    		var id = e.target.id;
						    		var idCompleto = id.split("_");
						    		var fila = parseInt(idCompleto[0],10);
						    		var fin = $("#"+fila+"_fechaFin").datepicker('getDate');
						    	    var inicio = $("#"+fila+"_fechaInicio").datepicker('getDate');
						    	    inicio = $.datepicker.formatDate('ddmmyy',inicio);
					    	        fin = $.datepicker.formatDate('ddmmyy', fin);
						    	    controlFechasAFIP51(inicio, fin, fila, id);
								});
								$("#"+ids[i]+"_implFinal").focus(function(e) {
									var id = e.target.id;
									var idCompleto = id.split("_");
						    		var fila = parseInt(idCompleto[0],10);
						    		if ($("#"+fila+"_afi_divModificado").is(":hidden") && $("#"+fila+"_afi_divError").is(":hidden")){
						    			var fin = $("#"+fila+"_fechaFin").datepicker('getDate');
						    	        var inicio = $("#"+fila+"_fechaInicio").datepicker('getDate');
						    	        inicio = $.datepicker.formatDate('ddmmyy',inicio);
					    	        	fin = $.datepicker.formatDate('ddmmyy', fin);
						    	        controlFechasAFIP51(inicio, fin, fila, id);
						    		}
								});
								$("#"+ids[i]+"_implInicial").click(function(event) {
									loadPopUpAyudaP51(event);
								});
								$("#"+ids[i]+"_implFinal").click(function(event) {
									loadPopUpAyudaP51(event);
								});
								$("#"+ids[i]+"_implInicial").change(function() {
									validacionImplInicial(this.id, 'S', tipoListado);
								});
								$("#"+ids[i]+"_implFinal").change(function() {
									validacionImplFinal(this.id, 'S', tipoListado);
								});
								
								
								controlVisibilidadImplAFI(ids[i]);
								//Pintado de errores en los campos
								if($("#"+ ids[i] + "_afi_codError_orig").val() != null &&  $("#"+ ids[i] + "_afi_codError_orig").val() == '99'){
									marcarValidacionFila(ids[i]);
					    		}
					    	}								
								
				    	}
			    		//Las líneas bloquedas se muestran sin editar y sin resaltar
			    	
				    	cont++;
				    }
					$("#p51_AreaOferta").show();
					
					//Cuando pulse la columna para ordenar por icono tenemos que llamar manualmente al reload para que reordene,
					//ya que al tener el jsp campos editables no funciona de la manera convencional.
					//$("#gridP51FP_estado").unbind('click');
					//$("#gridP51FP_estado").bind("click", function(e) {
						//Establecemos el parámetro de ordenación por el campo de los iconos.
						//$('#gridP51FP').setGridParam({sortname:'estado'});
						//$('#gridP51FP').setGridParam({page:1});
						//reloadDataP51('S');
					 //}); 

					//$("#gridP51AFI_estado").unbind('click');
					//$("#gridP51AFI_estado").bind("click", function(e) {
						//Establecemos el parámetro de ordenación por el campo de los iconos.
						//$('#gridP51AFI').setGridParam({sortname:'estado'});
						//$('#gridP51AFI').setGridParam({page:1});
						//reloadDataP51('S');
					 //}); 

					$("#p51_btn_find").focus();
					
					$("#p51_AreaOferta .loading").css("display", "none");	
					if (data.datos.records == null || data.datos.records == 0){
						if ($("input[name='p51_rad_tipoFiltro']:checked").val() == "1"){
							createAlert(replaceSpecialCharacters(gridEmptyRecordsP51), "ERROR");
						}
						else
						{
							if ($("#p51_txt_flgFrescoPuro").val()=='S'){
								createAlert(replaceSpecialCharacters(gridEmptyRecordsP51FP), "ERROR");
							}
							else
							{
								createAlert(replaceSpecialCharacters(gridEmptyRecordsP51AFI), "ERROR");
								
							}
						}
					} 
				},
				error : function (xhr, status, error){
					$("#p51_AreaOferta .loading").css("display", "none");	
					handleError(xhr, status, error, locale);				
		        }			
			});		
	}
}
				    	
/*Clase de constantes para le GRID*/
function GridP51FP (locale){
	// Atributos
	this.name = "gridP51FP"; 
	this.nameJQuery = "#gridP51FP"; 
	this.i18nJSON = './misumi/resources/p51NuevoPedidoAdicionalOF/p51nuevoPedidoAdicionalOF_' + locale + '.json';
	this.colNames = null;
	this.cm =[ { 
		"name"  : "tipoAprov",
		"index" : "tipoAprov",
		"fixed":true,
		"sortable" : false,
		"width" : 50
	},{				
		"name"  : "codArticulo",
		"index" : "codArticulo",
		"fixed":true,
		"sortable" : false,
		"width" : 70
	},{
		"name"  : "descriptionArt",
		"index" : "descriptionArt", 
		"sortable" : false,
		"width" : 190
	},{
		"name"  : "fechaInicio",
		"index" : "fechaInicio",
		"editable": true,
		"edittype":"text",
		"editoptions":{
            "size":"10",
            "maxlength":"15",
            "dataInit": function(el) { setTimeout(function() {  
												            	var idCompleto = el.id.split("_");
																var index = parseInt(idCompleto[0],10);
																$(el).datepicker( "option", $.datepicker.regional[ 'esDiasServicio' ] );
																
													    		var fechaInicioCompleta = $("#"+index+"_fp_fechaInicioCompleta").val();
														    	var fechaInicio = fechaInicioCompleta.substring(4)+"-"+fechaInicioCompleta.substring(2,4)+"-"+fechaInicioCompleta.substring(0,2);
														    	var fechaMinima = $("#"+index+"_fp_fechaMinima").val();
														    	var min = fechaMinima.substring(4)+"-"+fechaMinima.substring(2,4)+"-"+fechaMinima.substring(0,2);
														    	
													    		$(el).datepicker({
            											    		minDate: devuelveDate(min),
           											    		    dateFormat: "D dd-M",
													    			onSelect: function(dateText, inst) {
														            	var idCompleto = el.id.split("_");
															    		var fila = parseInt(idCompleto[0],10);
													            		var fin = $("#"+fila+"_fp_fechaFinCompleta").val();
														    	        var inicio = $.formatNumber(inst.selectedDay,{format:"00"})+$.formatNumber((inst.selectedMonth + 1),{format:"00"})+$.formatNumber(inst.selectedYear,{format:"0000"})
														    	        $("#"+fila+"_fp_fechaInicioCompleta").val( inicio);
														    	        if (p51validarDatesGrid(fila, "fp")){    
													    	        	//inicio = $.datepicker.formatDate('ddmmyy',inicio);
													    	        	//fin = $.datepicker.formatDate('ddmmyy', fin);
													    	        	controlFechasFPP51(inicio, fin, fila, el.id);
														    		}
														    		}
													    		});
            											    	$(el).datepicker().prop({
            											    		cargadosParametros:'S',
            														identificador: '',
            													    clasePedido: 'M',
           															cargadoDS: 'N',
           															codCentroCalendario: $("#centerId").val(),
            											    		codArticulo: $("#"+index+"_fp_codArticulo").val(),
            											    		esFresco:'S'
            											    	});
            										    		$(el).click(function (e) {
            											    		var id = e.target.id;
            											    		var idCompleto = id.split("_");
            											    		var index = parseInt(idCompleto[0],10);
            											    		var fechaInicioCompleta = $("#"+index+"_fp_fechaInicioCompleta").val();
            												    	var fechaInicio = fechaInicioCompleta.substring(4)+"-"+fechaInicioCompleta.substring(2,4)+"-"+fechaInicioCompleta.substring(0,2);
            												    	$("#"+id).datepicker('setDate', devuelveDate(fechaInicio));
            											    		$("#"+id).datepicker('refresh');
            											    	 });
            										    		$(el).bind('focus', function() {
            										    			var id = el.id;
            										    			if(!($("#"+id).datepicker('widget').is(":visible"))){
	            											    		var idCompleto = id.split("_");
	            											    		var index = parseInt(idCompleto[0],10);
	            											    		var fechaInicioCompleta = $("#"+index+"_fp_fechaInicioCompleta").val();
	            												    	var fechaInicio = fechaInicioCompleta.substring(4)+"-"+fechaInicioCompleta.substring(2,4)+"-"+fechaInicioCompleta.substring(0,2);
	            												    	$("#"+id).datepicker('setDate', devuelveDate(fechaInicio));
	            											    		$("#"+id).datepicker('refresh');
            										    			}
            										    	    });
            												 }, 200); },
            "dataEvents": [
                           {//control para el keydown
                         	  type: 'keydown',
                               fn: controlNavegacionP51,
                           }// cierra el control del keydown
                          ]   
         	},
		"cellEdit" : true,
		"cellsubmit" : "clientArray",
		"sortable" : false,
		"formatter" : p51FormateoDate,
		"fixed":true,
		"width" : 70
		},{
		"name"  : "cantidad1",
		"index" : "cantidad1",
		"editable": true,
		"edittype":"text",
		"editoptions":{
            "size":"10",
            "maxlength":"15",
            "dataEvents": [
                           {//control para el keydown
                          	  type: 'keydown',
                                fn: controlNavegacionP51,
                            }// cierra el control del keydown
                          ]   
         	},
		"cellEdit" : true,
		"cellsubmit" : "clientArray",
		"sortable" : false,
		"fixed":true,
		"width" : 70
		},{
		"name"  : "fecha2",
		"index" : "fecha2",
		"formatter" : p51FormateoDate,
		"sortable" : false,
		"fixed":true,
		"width" : 70
		},{
		"name"  : "cantidad2",
		"index" : "cantidad2",
		"editable": true,
		"edittype":"text",
		"editoptions":{
            "size":"10",
            "maxlength":"15",
            "dataEvents": [
                           {//control para el keydown
                          	  type: 'keydown',
                                fn: controlNavegacionP51,
                            }// cierra el control del keydown
                          ]   
         	},
		"cellEdit" : true,
		"cellsubmit" : "clientArray",
		"sortable" : false,
		"fixed":true,
		"width" : 70
		},{
		"name"  : "fecha3",
		"index" : "fecha3",
		"formatter" : p51FormateoDate,
		"sortable" : false,
		"fixed":true,
		"width" : 70
		},{
		"name"  : "cantidad3",
		"index" : "cantidad3",
		"editable": true,
		"edittype":"text",
		"editoptions":{
            "size":"10",
            "maxlength":"15",
            "dataEvents": [
                           {//control para el keydown
                          	  type: 'keydown',
                                fn: controlNavegacionP51,
                            }// cierra el control del keydown
                          ]   
         	},
		"cellEdit" : true,
		"cellsubmit" : "clientArray",
		"sortable" : false,
		"fixed":true,
		"width" : 70
		},{
		"name"  : "fechaFin",
		"index" : "fechaFin",
		"editable": true,
		"edittype":"text",
		"editoptions":{
            "size":"10",
            "maxlength":"15",
            "dataInit": function(el) { setTimeout(function() {  
												            	var idCompleto = el.id.split("_");
																var index = parseInt(idCompleto[0],10);
																$(el).datepicker( "option", $.datepicker.regional[ 'esDiasServicio' ] );
																
													    		var fechaFinCompleta = $("#"+index+"_fp_fechaFinCompleta").val();
														    	var fechaFin = fechaFinCompleta.substring(4)+"-"+fechaFinCompleta.substring(2,4)+"-"+fechaFinCompleta.substring(0,2);
														    	var fechaMinima = $("#"+index+"_fp_fechaMinima").val();
														    	var min = fechaMinima.substring(4)+"-"+fechaMinima.substring(2,4)+"-"+fechaMinima.substring(0,2);
														    	
														    	$(el).datepicker({
            											    		minDate: devuelveDate(min),
           											    		    dateFormat: "D dd-M",
														    		onSelect: function(dateText, inst) {
														    			var idCompleto = el.id.split("_");
															    		var fila = parseInt(idCompleto[0],10);
														            	var fin = $.formatNumber(inst.selectedDay,{format:"00"})+$.formatNumber((inst.selectedMonth + 1),{format:"00"})+$.formatNumber(inst.selectedYear,{format:"0000"})
														    	        var inicio = $("#"+fila+"_fp_fechaInicioCompleta").val();
														    	        $("#"+fila+"_fp_fechaFinCompleta").val(fin);
														    	        if (p51validarDatesGrid(fila, "fp")){
															    	       
														    	        //inicio = $.datepicker.formatDate('ddmmyy',inicio);
													    	        	//fin = $.datepicker.formatDate('ddmmyy', fin);
														    	        controlFechasFPP51(inicio, fin, fila, el.id);
														             }
														             }
														    	});
            											    	$(el).datepicker().prop({
            											    		cargadosParametros:'S',
            														identificador: '',
            													    clasePedido: 'M',
           															cargadoDS: 'N',
           															codCentroCalendario: $("#centerId").val(),
            											    		codArticulo: $("#"+index+"_fp_codArticulo").val(),
            											    		esFresco:'S'
            											    	});
            										    		$(el).click(function (e) {
            											    		var id = e.target.id;
            											    		var idCompleto = id.split("_");
            											    		var index = parseInt(idCompleto[0],10);
            											    		var fechaFinCompleta = $("#"+index+"_fp_fechaFinCompleta").val();
            												    	var fechaFin = fechaFinCompleta.substring(4)+"-"+fechaFinCompleta.substring(2,4)+"-"+fechaFinCompleta.substring(0,2);
            												    	$("#"+id).datepicker('setDate', devuelveDate(fechaFin));
            											    		$("#"+id).datepicker('refresh');
            											    	 });
            										    		$(el).bind('focus', function() {
            										    			var id = el.id;
            										    			if(!($("#"+id).datepicker('widget').is(":visible"))){
	            											    		var idCompleto = id.split("_");
	            											    		var index = parseInt(idCompleto[0],10);
	            											    		var fechaFinCompleta = $("#"+index+"_fp_fechaFinCompleta").val();
	            												    	var fechaFin = fechaFinCompleta.substring(4)+"-"+fechaFinCompleta.substring(2,4)+"-"+fechaFinCompleta.substring(0,2);
	            												    	$("#"+id).datepicker('setDate', devuelveDate(fechaFin));
	            											    		$("#"+id).datepicker('refresh');
            										    			}
            										    	    });
															 }, 200); },
            "dataEvents": [
                           {//control para el keydown
                          	  type: 'keydown',
                                fn: controlNavegacionP51,
                            }// cierra el control del keydown
                          ]   
         	},
		"cellEdit" : true,
		"cellsubmit" : "clientArray",
		"sortable" : false,
		"formatter" : p51FormateoDate,
		"fixed":true,
		"width" : 70
		},{
		"name"  : "uniCajaServ",
		"index" : "uniCajaServ", 
		"formatter" : p51FormateoUnidadesCaja,
		"sortable" : false,
		"fixed":true,
		"width" : 40
	},{
		"name"  : "estado",
		"index" : "estado", 
		"formatter" : p51imageFormatEstadoFP,
		"sortable" : false,
		"fixed":true,
		"width" : 50
	}
	]; 
	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP51FP"; 
	this.pagerNameJQuery = "#pagerP51FP";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;
	this.myColumnStateName = 'gridP51FP.colState';
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
			return "codArticulo";
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
        var colModel = jQuery(gridP51FP.nameJQuery).jqGrid('getGridParam', 'colModel'); 
        var i;
        var l = colModel.length;
        var colItem; 
        var cmName;
        var postData = jQuery(gridP51FP.nameJQuery).jqGrid('getGridParam', 'postData');
        var columnsState = {
                search: jQuery(gridP51FP.nameJQuery).jqGrid('getGridParam', 'search'),
                page: jQuery(gridP51FP.nameJQuery).jqGrid('getGridParam', 'page'),
                sortname: jQuery(gridP51FP.nameJQuery).jqGrid('getGridParam', 'sortname'),
                sortorder: jQuery(gridP51FP.nameJQuery).jqGrid('getGridParam', 'sortorder'),
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
        	
            window.localStorage.setItem(gridP51FP.myColumnStateName, JSON.stringify(columnsState));
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
	
function GridP51AFI (locale){
	// Atributos
	this.name = "gridP51AFI"; 
	this.nameJQuery = "#gridP51AFI"; 
	this.i18nJSON = './misumi/resources/p51NuevoPedidoAdicionalOF/p51nuevoPedidoAdicionalOF_' + locale + '.json';
	this.colNames = null;
	this.cm =[ { 
		"name"  : "tipoAprov",
		"index" : "tipoAprov",
		"sortable" : false,
		"fixed":true,
		"width" : 50
	},{				
		"name"  : "codArticulo",
		"index" : "codArticulo",
		"sortable" : false,
		"fixed":true,
		"width" : 70
	},{
		"name"  : "descriptionArt",
		"index" : "descriptionArt", 
		"sortable" : false,
		"width" : 250
	},{
		"name"  : "fechaInicio",
		"index" : "fechaInicio",

		"editable": true,
		"edittype":"text",
		"editoptions":{
            "size":"10",
            "maxlength":"15",
            "dataInit": function(el) { setTimeout(function() {
            													var idCompleto = el.id.split("_");
            													var index = parseInt(idCompleto[0],10);
            													$(el).datepicker( "option", $.datepicker.regional[ 'esDiasServicio' ] );
            										    		var fechaInicioCompleta = $("#"+index+"_afi_fechaInicioCompleta").val();
            											    	var fechaInicio = fechaInicioCompleta.substring(4)+"-"+fechaInicioCompleta.substring(2,4)+"-"+fechaInicioCompleta.substring(0,2);
            											    	var fechaMinima = $("#"+index+"_afi_fechaMinima").val();
            											    	var min = fechaMinima.substring(4)+"-"+fechaMinima.substring(2,4)+"-"+fechaMinima.substring(0,2);
            											    	
            										    		$(el).datepicker({
            											    		minDate: devuelveDate(min),
           											    		    dateFormat: "D dd-M",
            										    			onSelect: function(dateText, inst) {
            											            	var idCompleto = el.id.split("_");
            												    		var fila = parseInt(idCompleto[0],10);
            										            		var fin = $("#"+fila+"_afi_fechaFinCompleta").val();
            											    	        var inicio = $.formatNumber(inst.selectedDay,{format:"00"})+$.formatNumber((inst.selectedMonth + 1),{format:"00"})+$.formatNumber(inst.selectedYear,{format:"0000"})
            											    	        $("#"+fila+"_afi_fechaInicioCompleta").val( inicio);
            											    	        if (p51validarDatesGrid(fila, "afi")){    
            										    	        	//inicio = $.datepicker.formatDate('ddmmyy',inicio);
            										    	        	//fin = $.datepicker.formatDate('ddmmyy', fin);
            										    	        	controlFechasAFIP51(inicio, fin, fila, el.id);
            											    		}
            											    		}
            										    		});
            											    	$(el).datepicker().prop({
            											    		cargadosParametros:'S',
            														identificador: '',
            													    clasePedido: 'M',
           															cargadoDS: 'N',
           															codCentroCalendario: $("#centerId").val(),
        											    			codArticulo: $("#"+index+"_afi_codArticulo").val(),
        											    			esFresco:'N'
            											    	});
            										    		$(el).click(function (e) {
            											    		var id = e.target.id;
            											    		var idCompleto = id.split("_");
            											    		var index = parseInt(idCompleto[0],10);
            											    		var fechaInicioCompleta = $("#"+index+"_afi_fechaInicioCompleta").val();
            												    	var fechaInicio = fechaInicioCompleta.substring(4)+"-"+fechaInicioCompleta.substring(2,4)+"-"+fechaInicioCompleta.substring(0,2);
            												    	
            												    	$("#"+id).datepicker('setDate', devuelveDate(fechaInicio));
            											    		$("#"+id).datepicker('refresh');
            											    	 });
            										    		$(el).bind('focus', function() {
            										    			var id = el.id;
            										    			if(!($("#"+id).datepicker('widget').is(":visible"))){
	            											    		var idCompleto = id.split("_");
	            											    		var index = parseInt(idCompleto[0],10);
	            											    		var fechaInicioCompleta = $("#"+index+"_afi_fechaInicioCompleta").val();
	            												    	var fechaInicio = fechaInicioCompleta.substring(4)+"-"+fechaInicioCompleta.substring(2,4)+"-"+fechaInicioCompleta.substring(0,2);
	            												    	$("#"+id).datepicker('setDate', devuelveDate(fechaInicio));
	            											    		$("#"+id).datepicker('refresh');
            										    			}
            										    	    });
            												 }, 200); },
            "dataEvents": [
                           {//control para el keydown
                          	  type: 'keydown',
                                fn: controlNavegacionP51,
                            }// cierra el control del keydown
                          ]   
         	},
		"cellEdit" : true,
		"cellsubmit" : "clientArray",
		"sortable" : false,
		"formatter" : p51FormateoDate,
		"fixed":true,
		"width" : 70
	},{
		"name"  : "fechaFin",
		"index" : "fechaFin",

		"editable": true,
		"edittype":"text",
		"editoptions":{
            "size":"10",
            "maxlength":"15",
            "dataInit": function(el) {setTimeout(function() {  
            													var idCompleto = el.id.split("_");
            													var index = parseInt(idCompleto[0],10);
            													$(el).datepicker( "option", $.datepicker.regional[ 'esDiasServicio' ] );
            										    		var fechaFinCompleta = $("#"+index+"_afi_fechaFinCompleta").val();
            											    	var fechaFin = fechaFinCompleta.substring(4)+"-"+fechaFinCompleta.substring(2,4)+"-"+fechaFinCompleta.substring(0,2);
            											    	var fechaMinima = $("#"+index+"_afi_fechaMinima").val();
            											    	var min = fechaMinima.substring(4)+"-"+fechaMinima.substring(2,4)+"-"+fechaMinima.substring(0,2);
            											    	
            											    	$(el).datepicker({
            											    		minDate: devuelveDate(min),
           											    		    dateFormat: "D dd-M",
            											    		onSelect: function(dateText, inst) {
            											    			var idCompleto = el.id.split("_");
            												    		var fila = parseInt(idCompleto[0],10);
            											            	var fin = $.formatNumber(inst.selectedDay,{format:"00"})+$.formatNumber((inst.selectedMonth + 1),{format:"00"})+$.formatNumber(inst.selectedYear,{format:"0000"})
            											    	        var inicio = $("#"+fila+"_afi_fechaInicioCompleta").val();
            											    	        $("#"+fila+"_afi_fechaFinCompleta").val(fin);
            											    	        if (p51validarDatesGrid(fila, "afi")){
            											    				if(!$("#"+fila+"_implFinal").is(':disabled')){
            											    					impFinalModif = true;
            											    				} 
            											    	        //inicio = $.datepicker.formatDate('ddmmyy',inicio);
            										    	        	//fin = $.datepicker.formatDate('ddmmyy', fin);
            											    	        controlFechasAFIP51(inicio, fin, fila, el.id);
            											             }
            											             }
            											    	});
            											    	$(el).datepicker().prop({
            											    		cargadosParametros:'S',
            														identificador: '',
            													    clasePedido: 'M',
           															cargadoDS: 'N',
           															codCentroCalendario: $("#centerId").val(),
            											    		codArticulo: $("#"+index+"_afi_codArticulo").val(),
            											    		esFresco:'N'
            											    	});
            										    		$(el).click(function (e) {
            											    		var id = e.target.id;
            											    		var idCompleto = id.split("_");
            											    		var index = parseInt(idCompleto[0],10);
            											    		var fechaFinCompleta = $("#"+index+"_afi_fechaFinCompleta").val();
            												    	var fechaFin = fechaFinCompleta.substring(4)+"-"+fechaFinCompleta.substring(2,4)+"-"+fechaFinCompleta.substring(0,2);
            												    	$("#"+id).datepicker('setDate', devuelveDate(fechaFin));
            											    		$("#"+id).datepicker('refresh');
            											    	 });
            										    		$(el).bind('focus', function() {
            										    			var id = el.id;
            										    			if(!($("#"+id).datepicker('widget').is(":visible"))){
	            											    		var idCompleto = id.split("_");
	            											    		var index = parseInt(idCompleto[0],10);
	            											    		var fechaFinCompleta = $("#"+index+"_afi_fechaFinCompleta").val();
	            												    	var fechaFin = fechaFinCompleta.substring(4)+"-"+fechaFinCompleta.substring(2,4)+"-"+fechaFinCompleta.substring(0,2);
	            												    	$("#"+id).datepicker('setDate', devuelveDate(fechaFin));
	            											    		$("#"+id).datepicker('refresh');
            										    			}
            										    	    });
															 }, 200); },
            "dataEvents": [
                           {//control para el keydown
                          	  type: 'keydown',
                                fn: controlNavegacionP51,
                            }// cierra el control del keydown
                          ]   
         	},
		"cellEdit" : true,
		"cellsubmit" : "clientArray",
		"sortable" : false,
		"formatter" : p51FormateoDate,
		"fixed":true,
		"width" : 70
	},{
		"name"  : "implInicial",
		"index" : "implInicial",
		"editable": true,
		"edittype":"text",
		"editoptions":{
            "size":"10",
            "maxlength":"15",
            "dataEvents": [
                           {//control para el keydown
                          	  type: 'keydown',
                                fn: controlNavegacionP51,
                            }// cierra el control del keydown
                          ]   
         	},
		"cellEdit" : true,
		"cellsubmit" : "clientArray",
		"sortable" : false,
		"fixed":true,
		"width" : 70
	},{
		"name"  : "implFinal",
		"index" : "implFinal",
		"editable": true,
		"edittype":"text",
		"editoptions":{
            "size":"10",
            "maxlength":"15",
            "dataEvents": [
                           {//control para el keydown
                          	  type: 'keydown',
                                fn: controlNavegacionP51,
                            }// cierra el control del keydown
                          ]   
         	},
		"cellEdit" : true,
		"cellsubmit" : "clientArray",
		"sortable" : false,
		"fixed":true,
		"width" : 70
	},{
		"name"  : "uniCajaServ",
		"index" : "uniCajaServ", 
		"formatter" : p51FormateoUnidadesCaja,
		"sortable" : false,
		"fixed":true,
		"width" : 40
	},{
		"name"  : "estado",
		"index" : "estado", 
		"formatter" : p51imageFormatEstadoAFI,
		"sortable" : false,
		"fixed":true,
		"width" : 50
	}
	]; 
	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP51AFI"; 
	this.pagerNameJQuery = "#pagerP51AFI";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;
	this.myColumnStateName = 'gridP51AFI.colState';
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
			return "codArticulo";
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
        var colModel = jQuery(gridP51AFI.nameJQuery).jqGrid('getGridParam', 'colModel'); 
        var i;
        var l = colModel.length;
        var colItem; 
        var cmName;
        var postData = jQuery(gridP51AFI.nameJQuery).jqGrid('getGridParam', 'postData');
        var columnsState = {
                search: jQuery(gridP51AFI.nameJQuery).jqGrid('getGridParam', 'search'),
                page: jQuery(gridP51AFI.nameJQuery).jqGrid('getGridParam', 'page'),
                sortname: jQuery(gridP51AFI.nameJQuery).jqGrid('getGridParam', 'sortname'),
                sortorder: jQuery(gridP51AFI.nameJQuery).jqGrid('getGridParam', 'sortorder'),
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
        	
            window.localStorage.setItem(gridP51AFI.myColumnStateName, JSON.stringify(columnsState));
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
	
function controlCentroP51(){
	$( "#centerName" ).bind('focus', function() {
		if ($("#centerName").val()==null || $("#centerName").val()==''){
			cleanFilterSelectionP51(RESET_CONST_P51);
		}
    });
}

function events_p51_btn_cancelar(){
	$("#p51_btn_cancel").click(function () {
		
		//Primero antes de volver tenemos que comprobar que no haya nada pendiente de guardar.

		obtenerListadoModificadosP51();
		if (listadoModificadosP51== null || listadoModificadosP51.length == 0){
			p51RetornoPedidoAdicional();
		}
		else
		{
			//En este caso debemos mostrar un aviso de que hay cosas pendientes de guardar y que se pueden perder.
			$(function() {
				 $( "#p51dialogCancel-confirm" ).dialog({
					 resizable: false,
					 height:160,
					 modal: true,
					 buttons: {
					 "Si": function() {
					 p51RetornoPedidoAdicional();
					 $( this ).dialog( "close" );
					 },
					 "No": function() {
					 $( this ).dialog( "close" );
					 }
					 }
				 });
			});
		}
	});	
}


function p51RetornoPedidoAdicional(){
	
	window.location='./pedidoAdicional.do?flagCancelarNuevo=S&pestanaOrigen='+$("#p50_pestanaOrigen").val()+'&codArea='+$("#p50_codArea").val()+
	'&codSeccion='+$("#p50_codSeccion").val()+'&codCategoria='+$("#p50_codCategoria").val()+'&referencia='+$("#p50_referencia").val();
}



function p51FormateoDatePicker(cellValue, opts, rowObject) {
	

	var fechaFormateada = "";
	var diaFecha = parseInt(cellValue.substring(0,2),10);
	var mesFecha = parseInt(cellValue.substring(2,4),10);
	var anyoFecha = parseInt(cellValue.substring(4),10);


	
	fechaFormateada = diaFecha+"/"+mesFecha+"/"+anyoFecha;
	return(fechaFormateada);

	//Poner una fila como si fuera central, que no ser�a editable y habr�a que formatear, 
	//las filas editables las devolvemos sin formatear.
	/*if (opts.rowId!=4){
		return(cellValue);
	}
	else
	{
		return(fechaFormateada);
	}*/
	
}

function p51FormateoDate(cellValue, opts, rowObject) {
	
	var fechaFormateada = "";
	if (null != cellValue && "" != cellValue){
		var diaFecha = parseInt(cellValue.substring(0,2),10);
		var mesFecha = parseInt(cellValue.substring(2,4),10);
		var anyoFecha = parseInt(cellValue.substring(4),10);
		var fechaCompleta = anyoFecha + "-" + mesFecha + "-" + diaFecha
		
		fechaFormateada = $.datepicker.formatDate("D dd-M", devuelveDate(fechaCompleta),{
			dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
			dayNames: $.datepicker.regional[ "es" ].dayNames,
			monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
			monthNames: $.datepicker.regional[ "es" ].monthNames
			});
	}
	return(fechaFormateada);
	
}

function p51imageFormatEstadoFP(cellValue, opts, rData) {
	
	var imagen = "";
	var mostrarError = "none;";
	var descError = "";
	var mostrarModificado = "none;";
	
	//Controlamos los posibles errores que me lleguen para pintar el icono correspondiente.

	if (parseFloat(rData['codError']) == '9')
	{
		//Pintamos el icono de modificado
		mostrarModificado = "block;";
		addSeleccionadosP51(opts.rowId);
	}
	else if (parseFloat(rData['codError']) == '5')
	{
		//Pintamos el icono de error
		mostrarError = "block;";
		descError = rData['descError'];
	}
	else if (parseFloat(rData['codError']) >= '99') //Error de fila
	{
		//Pintamos el icono de que ha ocurrido un error con la descripción de modificación
		mostrarError = "block;";
		descError = rData['descError'];
		addSeleccionadosP51(opts.rowId);
	}

	imagen = "<div id='"+opts.rowId+"_fp_divModificado' align='center' style='display: "+ mostrarModificado + "'><img id='"+opts.rowId+"_fp_imgModificado' src='./misumi/images/modificado.png' title='"+iconoModificadoP51+"'/></div>"; //Modificado
	imagen += "<div id='"+opts.rowId+"_fp_divError' align='center' style='display: " + mostrarError + "'><img id='"+opts.rowId+"_fp_imgError' src='./misumi/images/dialog-error-24.png' title='"+descError+"'/></div>"; //Error
	
	//Añadimos los valores de la columna referencia ocultos para poder utilizarlos posteriormente.
	var fechaMinima = "<input type='hidden' id='"+opts.rowId+"_fp_fechaMinima' value='"+rData['fechaMinima']+"'>";
	imagen +=  fechaMinima;
	
	//Añadimos los valores de la columna referencia ocultos para poder utilizarlos posteriormente.
	var fechaInicioCompleta = "<input type='hidden' id='"+opts.rowId+"_fp_fechaInicioCompleta' value='"+rData['fechaInicio']+"'>";
	imagen +=  fechaInicioCompleta;
	
	//Añadimos los valores de la columna si es modificable ocultos para poder utilizarlos posteriormente.
	var fecha2 = "<input type='hidden' id='"+opts.rowId+"_fp_fecha2' value='"+rData['fecha2']+"'>";
	imagen +=  fecha2;

	//Añadimos los valores de la columna si es modificable ocultos para poder utilizarlos posteriormente.
	var fecha3 = "<input type='hidden' id='"+opts.rowId+"_fp_fecha3' value='"+rData['fecha3']+"'>";
	imagen +=  fecha3;

	//Añadimos los valores de la columna si es modificable ocultos para poder utilizarlos posteriormente.
	var fechaPilada = "<input type='hidden' id='"+opts.rowId+"_fp_fechaPilada' value='"+rData['fechaPilada']+"'>";
	imagen +=  fechaPilada;
	
	var fechaFinCompleta = "<input type='hidden' id='"+opts.rowId+"_fp_fechaFinCompleta' value='"+rData['fechaFin']+"'>";
	imagen +=  fechaFinCompleta;
	
	//Añadimos los valores de la columna si es modificable ocultos para poder utilizarlos posteriormente.
	var bloqueado = "<input type='hidden' id='"+opts.rowId+"_fp_bloqueado' value='"+rData['bloqueado']+"'>";
	imagen +=  bloqueado;
	
	var datoCantidad1Orig = "<input type='hidden' id='"+opts.rowId+"_cantidad1_fp_orig' value='"+rData['cantidad1Orig']+"'>";
	imagen +=  datoCantidad1Orig;
	
	var datoCantidad1 = "<input type='hidden' id='"+opts.rowId+"_cantidad1_fp_tmp' value='"+rData['cantidad1Orig']+"'>";
	imagen +=  datoCantidad1;

	var datoCantidad2Orig = "<input type='hidden' id='"+opts.rowId+"_cantidad2_fp_orig' value='"+rData['cantidad2Orig']+"'>";
	imagen +=  datoCantidad2Orig;
	
	var datoCantidad2 = "<input type='hidden' id='"+opts.rowId+"_cantidad2_fp_tmp' value='"+rData['cantidad2Orig']+"'>";
	imagen +=  datoCantidad2;
	
	var datoCantidad3Orig = "<input type='hidden' id='"+opts.rowId+"_cantidad3_fp_orig' value='"+rData['cantidad3Orig']+"'>";
	imagen +=  datoCantidad3Orig;
	
	var datoCantidad3 = "<input type='hidden' id='"+opts.rowId+"_cantidad3_fp_tmp' value='"+rData['cantidad3Orig']+"'>";
	imagen +=  datoCantidad3;

	//Añadimos también el índice de la fila en la que estamos oculta para poder utilizarla posteriormente.
	var datoIndice = "<input type='hidden' id='"+opts.rowId+"_fp_indice' value='"+rData['indice']+"'>";
	imagen +=  datoIndice;

	var datoCodAticulo = "<input type='hidden' id='"+opts.rowId+"_fp_codArticulo' value='"+rData['codArticulo']+"'>";
	imagen +=  datoCodAticulo;

	//Añadimos también el valor del codError de cada registro.
	var datoError = "<input type='hidden' id='"+opts.rowId+"_fp_codError_orig' value='"+rData['codError']+"'>";
	imagen +=  datoError;
	
	//Añadimos también el valor del codError de cada registro.
	var datoError = "<input type='hidden' id='"+opts.rowId+"_fp_descError_orig' value='"+rData['descError']+"'>";
	imagen +=  datoError;

	//Añadimos los valores para el control de bloqueos.
	var fechaBloqueoEncargo = "<input type='hidden' id='"+opts.rowId+"_fp_fechaBloqueoEncargo' value='"+rData['fechaBloqueoEncargo']+"'>";
	imagen +=  fechaBloqueoEncargo;

	var fechaBloqueoEncargoPilada = "<input type='hidden' id='"+opts.rowId+"_fp_fechaBloqueoEncargoPilada' value='"+rData['fechaBloqueoEncargoPilada']+"'>";
	imagen +=  fechaBloqueoEncargoPilada;

	return imagen;
}

function p51imageFormatEstadoAFI(cellValue, opts, rData) {
	
	var imagen = "";
	var mostrarError = "none;";
	var descError = "";
	var mostrarModificado = "none;";
	
	//Controlamos los posibles errores que me lleguen para pintar el icono correspondiente.

	if (parseFloat(rData['codError']) == '9')
	{
		//Pintamos el icono de modificado
		mostrarModificado = "block;";
		addSeleccionadosP51(opts.rowId);
	}
	else if (parseFloat(rData['codError']) == '5')
	{
		//Pintamos el icono de error
		mostrarError = "block;";
		descError = rData['descError'];
	}
	else if (parseFloat(rData['codError']) >= '99') //Error de fila
	{
		//Pintamos el icono de que ha ocurrido un error con la descripción de modificación
		mostrarError = "block;";
		descError = rData['descError'];
		addSeleccionadosP51(opts.rowId);
	}

	imagen = "<div id='"+opts.rowId+"_afi_divModificado' align='center' style='display: "+ mostrarModificado + "'><img id='"+opts.rowId+"_afi_imgModificado' src='./misumi/images/modificado.png' title='"+iconoModificadoP51+"'/></div>"; //Modificado
	imagen += "<div id='"+opts.rowId+"_afi_divError' align='center' style='display: " + mostrarError + "'><img id='"+opts.rowId+"_afi_imgError' src='./misumi/images/dialog-error-24.png' title='"+descError+"'/></div>"; //Error
	
	//Añadimos los valores de la columna referencia ocultos para poder utilizarlos posteriormente.
	var fechaMinima = "<input type='hidden' id='"+opts.rowId+"_afi_fechaMinima' value='"+rData['fechaMinima']+"'>";
	imagen +=  fechaMinima;
	
	//Añadimos los valores de la columna referencia ocultos para poder utilizarlos posteriormente.
	var fechaInicioCompleta = "<input type='hidden' id='"+opts.rowId+"_afi_fechaInicioCompleta' value='"+rData['fechaInicio']+"'>";
	imagen +=  fechaInicioCompleta;
	
	var fechaFinCompleta = "<input type='hidden' id='"+opts.rowId+"_afi_fechaFinCompleta' value='"+rData['fechaFin']+"'>";
	imagen +=  fechaFinCompleta;
	
	//Añadimos los valores de la columna si es modificable ocultos para poder utilizarlos posteriormente.
	var bloqueado = "<input type='hidden' id='"+opts.rowId+"_afi_bloqueado' value='"+rData['bloqueado']+"'>";
	imagen +=  bloqueado;
	
	var datoImplInicialOrig = "<input type='hidden' id='"+opts.rowId+"_implInicial_afi_orig' value='"+rData['implInicialOrig']+"'>";
	imagen +=  datoImplInicialOrig;
	
	var datoImplInicial = "<input type='hidden' id='"+opts.rowId+"_implInicial_afi_tmp' value='"+rData['implInicialOrig']+"'>";
	imagen +=  datoImplInicial;

	var datoImplFinalOrig = "<input type='hidden' id='"+opts.rowId+"_implFinal_afi_orig' value='"+rData['implFinalOrig']+"'>";
	imagen +=  datoImplFinalOrig;
	
	var datoImplFinal = "<input type='hidden' id='"+opts.rowId+"_implFinal_afi_tmp' value='"+rData['implFinalOrig']+"'>";
	imagen +=  datoImplFinal;
	
	//Añadimos también el índice de la fila en la que estamos oculta para poder utilizarla posteriormente.
	var datoIndice = "<input type='hidden' id='"+opts.rowId+"_afi_indice' value='"+rData['indice']+"'>";
	imagen +=  datoIndice;
	
	var datoCodAticulo = "<input type='hidden' id='"+opts.rowId+"_afi_codArticulo' value='"+rData['codArticulo']+"'>";
	imagen +=  datoCodAticulo;

	//Añadimos también el valor del codError de cada registro.
	var datoError = "<input type='hidden' id='"+opts.rowId+"_afi_codError_orig' value='"+rData['codError']+"'>";
	imagen +=  datoError;
	
	//Añadimos también el valor del codError de cada registro.
	var datoError = "<input type='hidden' id='"+opts.rowId+"_afi_descError_orig' value='"+rData['descError']+"'>";
	imagen +=  datoError;

	//Añadimos los valores para el control de bloqueos.
	var fechaBloqueoEncargoPilada = "<input type='hidden' id='"+opts.rowId+"_afi_fechaBloqueoEncargoPilada' value='"+rData['fechaBloqueoEncargoPilada']+"'>";
	imagen +=  fechaBloqueoEncargoPilada;

	return imagen;
}

function loadPopUpAyudaP51(event){
	var $focused = $(event.target);
	var id = $focused.attr("id");
	var i = id.indexOf("_");
	var rowId = id.substring(0, i);
	var g = null;
	if ($("#gbox_gridP51AFI").is(":visible")){
		g =gridP51AFI;
	} else {
		g = gridP51FP;
	}
	defineCampoFoco($focused);
	var rowData = jQuery(g.nameJQuery).getRowData(rowId);
	var codArt = rowData['codArticulo'];
	v_Oferta = new VOferta($("#centerId").val(),rowData['codArticulo']);
	vReferenciasCentro=new ReferenciasCentro(rowData['codArticulo'], $("#centerId").val());
	v_Oferta.tipoOferta = $("#p51_txt_tipoOferta").val(); 
	//Calcular el título de la ayuda 1
	$("#p56_fld_pestanaAyuda1").text(literalAyuda1ConOferta);
	
	$( "#p56_AreaAyuda" ).dialog("open");
	$focused.focus();
}

function p51_formatDescripcionCombo(codigo, descripcion){
	return codigo + "-" + descripcion;
}

function daysDiffP51(inicio, fin){
	var inicioTmp = inicio.substring(4)+"-"+inicio.substring(2,4)+"-"+inicio.substring(0,2);
	var finTmp = fin.substring(4)+"-"+fin.substring(2,4)+"-"+fin.substring(0,2);
	var fechaInicio = devuelveDate(inicioTmp).getTime();
	var fechaFin = devuelveDate(finTmp).getTime();

	var c = 24*60*60*1000;
    var diffDays = Math.round(Math.abs((fechaFin - fechaInicio)/(c)));
 	return diffDays;
}

function obtenerListadoModificadosP51(){
	
	//A partir del array de seleccionados obtenemos el listado de campos modificados a enviar al controlador.
	var registroListadoModificado = {};
	var valorFechaIni = "";
	var valorFechaFin = "";
	var valorFecha2 = "";
	var valorFecha3 = "";
	var valorFechaPilada = "";
	var valorCantidad1 = "";
	var valorCantidad2 = "";
	var valorCantidad3 = "";
	var valorImplInicial = "";
	var valorImplFinal = "";
	var valorCodError = "";
	for (i = 0; i < seleccionadosP51.length; i++){
		if (seleccionadosP51[i] != null && seleccionadosP51[i] != '')
		{
			//Por cada elemento seleccionado, tenemos que crear el listado de modificados.
			registroListadoModificado = {};
			valorFechaIni = "";
			valorFechaFin = "";
			valorFecha2 = "";
			valorFecha3 = "";
			valorFechaPilada = "";
			valorCantidad1 = "";
			valorCantidad2 = "";
			valorCantidad3 = "";
			valorImplInicial = "";
			valorImplFinal = "";
			valorCodError = "";

			if ($("#p51_txt_flgFrescoPuro").val()=='S'){
				valorFechaIni = $("#"+ seleccionadosP51[i] + "_fp_fechaInicioCompleta").val() ? $("#"+ seleccionadosP51[i] + "_fp_fechaInicioCompleta").val().replace(',','.') : "";
				valorFechaFin = $("#"+ seleccionadosP51[i] + "_fp_fechaFinCompleta").val() ? $("#"+ seleccionadosP51[i] + "_fp_fechaFinCompleta").val().replace(',','.') : "";
				valorFecha2 = $("#"+ seleccionadosP51[i] + "_fp_fecha2").val() && $("#"+ seleccionadosP51[i] + "_fp_fecha2").val()!='null'? $("#"+ seleccionadosP51[i] + "_fp_fecha2").val() : "";
				valorFecha3 = $("#"+ seleccionadosP51[i] + "_fp_fecha3").val() && $("#"+ seleccionadosP51[i] + "_fp_fecha3").val()!='null' ? $("#"+ seleccionadosP51[i] + "_fp_fecha3").val() : "";
				valorFechaPilada = $("#"+ seleccionadosP51[i] + "_fp_fechaPilada").val() && $("#"+ seleccionadosP51[i] + "_fp_fechaPilada").val()!='null' ? $("#"+ seleccionadosP51[i] + "_fp_fechaPilada").val() : "";
				valorCantidad1 = $("#"+ seleccionadosP51[i] + "_cantidad1").val() ? $("#"+ seleccionadosP51[i] + "_cantidad1").val().replace(',','.') : "";
				valorCantidad2 = $("#"+ seleccionadosP51[i] + "_cantidad2").val() ? $("#"+ seleccionadosP51[i] + "_cantidad2").val().replace(',','.') : "";
				valorCantidad3 = $("#"+ seleccionadosP51[i] + "_cantidad3").val() ? $("#"+ seleccionadosP51[i] + "_cantidad3").val().replace(',','.') : "";
				registroListadoModificado.fechaInicio =  valorFechaIni;
				registroListadoModificado.fechaFin =  valorFechaFin;
				registroListadoModificado.fecha2 =  valorFecha2;
				registroListadoModificado.fecha3 =  valorFecha3;
				registroListadoModificado.fechaPilada =  valorFechaPilada;
				registroListadoModificado.cantidad1 =  valorCantidad1;
				registroListadoModificado.cantidad2 =  valorCantidad2;
				registroListadoModificado.cantidad3 =  valorCantidad3;
				registroListadoModificado.codArticulo =  $("#"+ seleccionadosP51[i] + "_fp_codArticulo").val();
				registroListadoModificado.codError = $("#"+ seleccionadosP51[i] + "_fp_codError_orig").val() ? $("#"+ seleccionadosP51[i] + "_fp_codError_orig").val() : "";
				registroListadoModificado.descError = $("#"+ seleccionadosP51[i] + "_fp_descError_orig").val() ? $("#"+ seleccionadosP51[i] + "_fp_descError_orig").val() : "";
			}else{
				valorFechaIni = $("#"+ seleccionadosP51[i] + "_afi_fechaInicioCompleta").val() ? $("#"+ seleccionadosP51[i] + "_afi_fechaInicioCompleta").val().replace(',','.') : "";
				valorFechaFin = $("#"+ seleccionadosP51[i] + "_afi_fechaFinCompleta").val() ? $("#"+ seleccionadosP51[i] + "_afi_fechaFinCompleta").val().replace(',','.') : "";
				valorImplInicial = $("#"+ seleccionadosP51[i] + "_implInicial").val() ? $("#"+ seleccionadosP51[i] + "_implInicial").val().replace(',','.') : "";
				valorImplFinal = $("#"+ seleccionadosP51[i] + "_implFinal").val() ? $("#"+ seleccionadosP51[i] + "_implFinal").val().replace(',','.') : "";
				registroListadoModificado.fechaInicio =  valorFechaIni;
				registroListadoModificado.fechaFin =  valorFechaFin;
				registroListadoModificado.implInicial = valorImplInicial;
				registroListadoModificado.implFinal = valorImplFinal;
				registroListadoModificado.codArticulo = $("#"+ seleccionadosP51[i] + "_afi_codArticulo").val();
				registroListadoModificado.codError = $("#"+ seleccionadosP51[i] + "_afi_codError_orig").val() ? $("#"+ seleccionadosP51[i] + "_afi_codError_orig").val() : "";
				registroListadoModificado.descError = $("#"+ seleccionadosP51[i] + "_afi_descError_orig").val() ? $("#"+ seleccionadosP51[i] + "_afi_descError_orig").val() : "";
			}

			listadoModificadosP51.push(registroListadoModificado)
		}	
	}

	//Reseteamos el array de los seleccionados.
	seleccionadosP51 = new Array();
}

function validacionFecha(fila, tipoListado){
	var campoDivModificado = fila + "_"+tipoListado+"_divModificado";
	var campoDivError = fila + "_"+tipoListado+"_divError";
	var campoImgError = fila + "_"+tipoListado+"_imgError";
	var campoErrorOrig = fila + "_"+tipoListado+"_codError_orig";	
	var campoDescErrorOrig = fila + "_"+tipoListado+"_descError_orig";	
	var errorFila = 0;
	var errorBloqueos = 0;
	
	//Control de bloqueos
	if ($("#p51_txt_flgFrescoPuro").val()=='S'){
		if($("#"+fila+"_fp_fechaBloqueoEncargo").val()=='S' && $("#"+fila+"_fp_fechaBloqueoEncargoPilada").val()=='S'){
			descError = replaceSpecialCharacters(p51fechaSelBloqueosEncargosMontajes);
			errorBloqueos = 1;
		}else if($("#"+fila+"_fp_fechaBloqueoEncargo").val()=='S'){
			descError = replaceSpecialCharacters(p51fechaSelBloqueosEncargos);
			errorBloqueos = 1;
		}else if($("#"+fila+"_fp_fechaBloqueoEncargoPilada").val()=='S'){
			descError = replaceSpecialCharacters(p51fechaSelBloqueosMontajes);
			errorBloqueos = 1;
		}
	}
	
	if (!validacionFila(fila)){
		errorFila = 1;
		if (errorBloqueos != 1){//Prevalece el error por bloqueos
			descError = replaceSpecialCharacters(datosObligatoriosFilaP51);
		}
	}

	if (errorBloqueos == 1 || errorFila == 1)
	{
		marcarValidacionFila(fila);
		if (errorBloqueos == 1 && $("#p51_txt_flgFrescoPuro").val()=='S'){
			if($("#"+fila+"_fp_fechaBloqueoEncargo").val()=='S' && $("#"+fila+"_fp_fechaBloqueoEncargoPilada").val()=='S'){
				$("#"+fila+"_fechaInicio").removeClass("editable").addClass("editableError");
				$("#"+fila+"_fechaFin").removeClass("editable").addClass("editableError");
			}else if($("#"+fila+"_fp_fechaBloqueoEncargo").val()=='S'){
				$("#"+fila+"_fechaInicio").removeClass("editable").addClass("editableError");
				$("#"+fila+"_fechaFin").removeClass("editable").addClass("editableError");
			}else if($("#"+fila+"_fp_fechaBloqueoEncargoPilada").val()=='S'){
				$("#"+fila+"_fechaFin").removeClass("editable").addClass("editableError");
			}
		}

		//En este caso ha ocurrido un error y hay que mostrar el icono de error.
		$("#"+campoDivModificado).hide();
		$("#"+campoDivError).show();
		$("#"+campoErrorOrig).val('99');
		//Cambiamos el title
		$("#"+campoImgError).attr('title', descError);
		$("#"+campoDescErrorOrig).val(descError);
		addSeleccionadosP51(fila);
	}else{
	//En este caso se ha modificado el campo y hay que establecer el icono de modificación.
	//Una modificación de fechas no indica un cambio, sólo es cambio si se modifican las cantidades (María 18/09/2013)
		$("#"+campoDivError).hide();
		$("#"+campoDivModificado).show();
		$("#"+campoErrorOrig).val('9');
		$("#"+campoDescErrorOrig).val('');
		addSeleccionadosP51(fila);
	}
}
function addSeleccionadosP51(fila){
	seleccionadosP51[fila] = fila;
}

function deleteSeleccionadosP51(fila){
	
	seleccionadosP51[fila] = null;
}

function controlFechasAFIP51(fInicio, fFin, fila, idCampoActual){

	var nuevoPedidoOferta = new NuevoPedidoOferta();
	nuevoPedidoOferta.fechaInicio = fInicio;
	nuevoPedidoOferta.fechaFin = fFin;
	nuevoPedidoOferta.codCentro = $("#centerId").val();
	nuevoPedidoOferta.flgTipoListado = "AFI";
	nuevoPedidoOferta.codArticulo = $("#"+fila+"_afi_codArticulo").val()
	var objJson = $.toJSON(nuevoPedidoOferta.prepareNuevoPedidoOfertaToJsonObject());
	$.ajax({
		type : 'POST',
		url : './nuevoPedidoAdicionalOferta/loadDates.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {	
			var campoErrorOrig = fila + "_afi_codError_orig";
			var campoDivModificado = fila + "_afi_divModificado";
			var campoDivError = fila + "_afi_divError";
			var campoImgError = fila + "_afi_imgError";
			var campoDescErrorOrig = fila + "_afi_descError_orig";	

			if('S' == data.fechaBloqueoEncargoPilada){
        		$("#p03_btn_aceptar").unbind("click");
				$("#p03_btn_aceptar").bind("click", function(e) {
					if (null != data.fechaBloqueoEncargoPilada){
						$("#"+fila+"_afi_fechaBloqueoEncargoPilada").val(data.fechaBloqueoEncargoPilada);
					} else {
						$("#"+fila+"_afi_fechaBloqueoEncargoPilada").val("N");
					} 
					
					$("#"+$("#p51_fld_NuevoOf_Selecc").val()).focus();
					$("#"+$("#p51_fld_NuevoOf_Selecc").val()).select();
					e.stopPropagation();
					$("#p51_fld_NuevoOf_Selecc").val('');
					
					$("#"+campoDivModificado).hide();
					$("#"+campoDivError).show();
					$("#"+campoErrorOrig).val('99');
					$("#"+campoImgError).attr('title', p51fechaSelBloqueosMontajes);
					$("#"+campoDescErrorOrig).val(p51fechaSelBloqueosMontajes);	
					addSeleccionadosP51(fila);

				});
				
				//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
				$("#p51_fld_NuevoOf_Selecc").val(idCampoActual);
				
				createAlert(p51fechaSelBloqueosMontajes, "ERROR");
        		
        		$("#p03_btn_aceptar").focus();

			}else{
				var implInicial = $("#"+fila+"_implInicial").val().replace(',','.');
				
				if (daysDiffP51(fInicio, fFin) > 7){
					$("#"+fila+"_implFinal").removeAttr("disabled");
					if (!impFinalModif){
						
						var implFinal = Math.ceil(implInicial * 0.2);
						
						$("#"+fila+"_implFinal").val(p52FormateoCantidades(implFinal));
						$("#"+fila+"_implFinal_afi_tmp").val(p52FormateoCantidades(implFinal));

					}
					
				} else {
					$("#"+fila+"_implFinal").removeAttr("disabled");
					$("#"+fila+"_implFinal").val(p52FormateoCantidades(implInicial));
					$("#"+fila+"_implFinal_afi_tmp").val(p52FormateoCantidades(implInicial));
					$("#"+fila+"_implFinal").attr("disabled", "disabled");
					
				}
				$("#"+fila+"_implInicial").val(p52FormateoCantidades(implInicial));
				$("#"+fila+"_implInicial_afi_tmp").val(p52FormateoCantidades(implInicial));
				
				var descError = replaceSpecialCharacters(datosObligatoriosFilaP51);
					
				if (validacionFila(fila)){
					marcarValidacionFila(fila);
					if ($("#"+campoErrorOrig).val()!=null && $("#"+campoErrorOrig).val()!='9' && $("#"+campoErrorOrig).val()!=''){
						$("#"+campoDivError).hide();
						$("#"+campoDivModificado).show();
						$("#"+campoErrorOrig).val('9');
						$("#"+campoDescErrorOrig).val('')	
						addSeleccionadosP51(fila);
					}
				}else{
					$("#"+campoDivModificado).hide();
					$("#"+campoDivError).show();
					$("#"+campoErrorOrig).val('99');
					$("#"+campoImgError).attr('title', descError);
					$("#"+campoDescErrorOrig).val(descError);	
					addSeleccionadosP51(fila);
				}
			}	
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
        }			
	});	

}

function controlFechasFPP51(fInicio, fFin, fila, idCampoActual){
	
	var nuevoPedidoOferta = new NuevoPedidoOferta();
	nuevoPedidoOferta.fechaInicio = fInicio;
	nuevoPedidoOferta.fechaFin = fFin;
	nuevoPedidoOferta.codCentro = $("#centerId").val();
	nuevoPedidoOferta.flgTipoListado = "FP";
	nuevoPedidoOferta.codArticulo = $("#"+fila+"_fp_codArticulo").val()
	var objJson = $.toJSON(nuevoPedidoOferta.prepareNuevoPedidoOfertaToJsonObject());
	$.ajax({
		type : 'POST',
		url : './nuevoPedidoAdicionalOferta/loadDates.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {	
			if('S' == data.fechaBloqueoEncargo || 'S' == data.fechaBloqueoEncargoPilada){
        		$("#p03_btn_aceptar").unbind("click");
				$("#p03_btn_aceptar").bind("click", function(e) {
					if (null != data.fecha2){
						$("#gridP51FP").find("#"+fila).find("[aria-describedby='gridP51FP_fecha2']").html(p51FormateoDate(data.fecha2));
						$("#"+fila+"_fp_fecha2").val(data.fecha2);
						$("#"+fila+"_cantidad2").removeAttr("disabled");
						
					} else {
						$("#gridP51FP").find("#"+fila).find("[aria-describedby='gridP51FP_fecha2']").html("");
						$("td[aria-describedby='gridP51FP_fecha2']:nth-child("+fila+")").html("");
						$("#"+fila+"_fp_fecha2").val("");
						$("#"+fila+"_cantidad2").val("");
						$("#"+fila+"_cantidad2").attr("disabled", "disabled");
					} 
					if (null != data.fecha3){
						$("#gridP51FP").find("#"+fila).find("[aria-describedby='gridP51FP_fecha3']").html(p51FormateoDate(data.fecha3));
						$("#"+fila+"_fp_fecha3").val(data.fecha3);
						$("#"+fila+"_cantidad3").removeAttr("disabled");
						
					} else {
						$("#gridP51FP").find("#"+fila).find("[aria-describedby='gridP51FP_fecha3']").html("");
						$("#"+fila+"_fp_fecha3").val("");
						$("#"+fila+"_cantidad3").val("");
						$("#"+fila+"_cantidad3").attr("disabled", "disabled");
					} 
					if (null != data.fechaPilada){
						$("#"+fila+"_fp_fechaPilada").val(data.fechaPilada);
					} else {
						$("#"+fila+"_fp_fechaPilada").val("");
					} 
					
					if (null != data.fechaBloqueoEncargo){
						$("#"+fila+"_fp_fechaBloqueoEncargo").val(data.fechaBloqueoEncargo);
					} else {
						$("#"+fila+"_fp_fechaBloqueoEncargo").val("N");
					} 
		
					if (null != data.fechaBloqueoEncargoPilada){
						$("#"+fila+"_fp_fechaBloqueoEncargoPilada").val(data.fechaBloqueoEncargoPilada);
					} else {
						$("#"+fila+"_fp_fechaBloqueoEncargoPilada").val("N");
					} 
		
					//Bloqueo de cantidades si hay bloqueo de encargo
					if('S' == data.fechaBloqueoEncargo){
						$("#"+fila+"_cantidad1").attr("disabled", "disabled");
						$("#"+fila+"_cantidad2").attr("disabled", "disabled");
						$("#"+fila+"_cantidad3").attr("disabled", "disabled");
					}else{
						$("#"+fila+"_cantidad1").removeAttr("disabled");
					}
					
					validacionFecha(fila, 'fp');

					$("#"+$("#p51_fld_NuevoOf_Selecc").val()).focus();
					$("#"+$("#p51_fld_NuevoOf_Selecc").val()).select();
					e.stopPropagation();
					$("#p51_fld_NuevoOf_Selecc").val('');
				});
				
				//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
				$("#p51_fld_NuevoOf_Selecc").val(idCampoActual);
				
				if('S' == data.fechaBloqueoEncargo && 'S' == data.fechaBloqueoEncargoPilada){
					createAlert(p51fechaSelBloqueosEncargosMontajes, "ERROR");
				}else if('S' == data.fechaBloqueoEncargo ){
					createAlert(p51fechaSelBloqueosEncargos, "ERROR");	
				}else{//'S' == data.fechaBloqueoEncargoPilada
					createAlert(p51fechaSelBloqueosMontajes, "ERROR");	
				}
        		
        		$("#p03_btn_aceptar").focus();

			}else{		
				if (null != data.fecha2){
					$("#gridP51FP").find("#"+fila).find("[aria-describedby='gridP51FP_fecha2']").html(p51FormateoDate(data.fecha2));
					$("#"+fila+"_fp_fecha2").val(data.fecha2);
					$("#"+fila+"_cantidad2").removeAttr("disabled");
					
				} else {
					$("#gridP51FP").find("#"+fila).find("[aria-describedby='gridP51FP_fecha2']").html("");
					$("td[aria-describedby='gridP51FP_fecha2']:nth-child("+fila+")").html("");
					$("#"+fila+"_fp_fecha2").val("");
					$("#"+fila+"_cantidad2").val("");
					$("#"+fila+"_cantidad2").attr("disabled", "disabled");
				} 
				if (null != data.fecha3){
					$("#gridP51FP").find("#"+fila).find("[aria-describedby='gridP51FP_fecha3']").html(p51FormateoDate(data.fecha3));
					$("#"+fila+"_fp_fecha3").val(data.fecha3);
					$("#"+fila+"_cantidad3").removeAttr("disabled");
					
				} else {
					$("#gridP51FP").find("#"+fila).find("[aria-describedby='gridP51FP_fecha3']").html("");
					$("#"+fila+"_fp_fecha3").val("");
					$("#"+fila+"_cantidad3").val("");
					$("#"+fila+"_cantidad3").attr("disabled", "disabled");
				} 
				if (null != data.fechaPilada){
					$("#"+fila+"_fp_fechaPilada").val(data.fechaPilada);
				} else {
					$("#"+fila+"_fp_fechaPilada").val("");
				} 
				
				if (null != data.fechaBloqueoEncargo){
					$("#"+fila+"_fp_fechaBloqueoEncargo").val(data.fechaBloqueoEncargo);
				} else {
					$("#"+fila+"_fp_fechaBloqueoEncargo").val("N");
				} 
	
				if (null != data.fechaBloqueoEncargoPilada){
					$("#"+fila+"_fp_fechaBloqueoEncargoPilada").val(data.fechaBloqueoEncargoPilada);
				} else {
					$("#"+fila+"_fp_fechaBloqueoEncargoPilada").val("N");
				} 
	
				validacionFecha(fila, 'fp');
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
        }			
	});	
}

function controlVisibilidadCantFP(fila){
	var fecha2 = $("#"+fila+"_fp_fecha2").val();
	var fecha3 = $("#"+fila+"_fp_fecha3").val();
	if (null == fecha2 || "null" == fecha2){
		$("#"+fila+"_cantidad2").attr("disabled", "disabled");
		$("#"+fila+"_cantidad2").val("");
	} else {
		$("#"+fila+"_cantidad2").removeAttr("disabled");
	} 
	if (null == fecha3 || "null" == fecha3){
		$("#"+fila+"_cantidad3").attr("disabled", "disabled");
		$("#"+fila+"_cantidad3").val("");
	} else {
		$("#"+fila+"_cantidad3").removeAttr("disabled");
	} 
	
	//Bloqueo de cantidades si hay bloqueo de encargo
	if('S' == $("#"+fila+"_fp_fechaBloqueoEncargo").val()){
		$("#"+fila+"_cantidad2").attr("disabled", "disabled");
		$("#"+fila+"_cantidad3").attr("disabled", "disabled");
	}

}

function controlVisibilidadImplAFI(fila){
	var fin = $("#"+fila+"_afi_fechaFinCompleta").val();
    var inicio = $("#"+fila+"_afi_fechaInicioCompleta").val();
	if (daysDiffP51(inicio, fin) > 7){
		$("#"+fila+"_implFinal").removeAttr("disabled");
	} else {
		$("#"+fila+"_implFinal").val($("#"+fila+"_implInicial").val());
		$("#"+fila+"_implFinal").attr("disabled", "disabled");
	}
}

function validacionCantidad(id, mostrarAlerta, tipoListado, numCampo){
	
	//No se hacen validaciones, sólo formateamos el valor introducido por el usuario.
	var campoActual = $("#"+id).val();
	var campoActualPunto = $("#"+id).val().replace(',','.');
	var fila = id.substring(0, id.indexOf("_"));
	var error = 0;
	var errorFila = 0;
	var errorBloqueos = 0;
	var descError = '';
	var campoCantidadOrig = id + "_" + tipoListado +  "_orig";
	var campoCantidadTmp = id + "_" + tipoListado + "_tmp";
	var campoDivModificado = fila + "_"+tipoListado+"_divModificado";
	var campoDivError = fila + "_"+tipoListado+"_divError";
	var campoImgError = fila + "_"+tipoListado+"_imgError";
	var campoErrorOrig = fila + "_"+tipoListado+"_codError_orig";	
	var campoDescErrorOrig = fila + "_"+tipoListado+"_descError_orig";	
	var resultadoValidacion = 'S';
	var mensajeInvalid = null;
	
	if (mostrarAlerta == 'S'){
		if (campoActual == '')
		{
			campoActualPunto = "0.000";
		}
		$("#"+id).val(campoActualPunto).formatNumber({format:"0.00",locale:"es"});
		
		if (isNaN(parseFloat(campoActualPunto)) || campoActual.split(",").length > 2 || campoActualPunto <= 0 || campoActualPunto > 9999){
			if (numCampo=='1'){
				mensajeInvalid = invalidCant1P51;
			}else if (numCampo=='2') {
				mensajeInvalid = invalidCant2P51;
			}else{
				mensajeInvalid = invalidCant3P51;
			}
			descError = replaceSpecialCharacters(mensajeInvalid);
			createAlert(descError, "ERROR");
			
			//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
			$("#p51_fld_NuevoOf_Selecc").val(id);
			
			//Pintamos de rojo el campo.
			$("#"+id).removeClass("editable").addClass("editableError");
			
			error = 1;
		}

		var campoCantidadOrigPunto = $("#"+campoCantidadOrig).val().replace(',','.');

		//Control de bloqueos
		if ($("#p51_txt_flgFrescoPuro").val()=='S'){
			if($("#"+fila+"_fp_fechaBloqueoEncargo").val()=='S' && $("#"+fila+"_fp_fechaBloqueoEncargoPilada").val()=='S'){
				descError = replaceSpecialCharacters(p51fechaSelBloqueosEncargosMontajes);
				errorBloqueos = 1;
			}else if($("#"+fila+"_fp_fechaBloqueoEncargo").val()=='S'){
				descError = replaceSpecialCharacters(p51fechaSelBloqueosEncargos);
				errorBloqueos = 1;
			}else if($("#"+fila+"_fp_fechaBloqueoEncargoPilada").val()=='S'){
				descError = replaceSpecialCharacters(p51fechaSelBloqueosMontajes);
				errorBloqueos = 1;
			}
		}
		
		if (error != 1 && !validacionFila(fila)){
			errorFila = 1;
			if (errorBloqueos != 1){//Prevalece el error por bloqueos
				descError = replaceSpecialCharacters(datosObligatoriosFilaP51);
			}
		}
		if (errorBloqueos == 1 || error == 1 || errorFila == 1)
		{
			//Cargamos los valores anteriores a la modificación.
			if (error == 1){
				$("#"+id).val(campoCantidadOrigPunto).formatNumber({format:"0.00",locale:"es"});
				$("#"+campoCantidadTmp).val(campoCantidadOrigPunto).formatNumber({format:"0.00",locale:"es"});
			}else{
				marcarValidacionFila(fila);
				if (errorBloqueos == 1 && $("#p51_txt_flgFrescoPuro").val()=='S'){
					if($("#"+fila+"_fp_fechaBloqueoEncargo").val()=='S' && $("#"+fila+"_fp_fechaBloqueoEncargoPilada").val()=='S'){
						$("#"+fila+"_fechaInicio").removeClass("editable").addClass("editableError");
						$("#"+fila+"_fechaFin").removeClass("editable").addClass("editableError");
					}else if($("#"+fila+"_fp_fechaBloqueoEncargo").val()=='S'){
						$("#"+fila+"_fechaInicio").removeClass("editable").addClass("editableError");
						$("#"+fila+"_fechaFin").removeClass("editable").addClass("editableError");
					}else if($("#"+fila+"_fp_fechaBloqueoEncargoPilada").val()=='S'){
						$("#"+fila+"_fechaFin").removeClass("editable").addClass("editableError");
					}
				}
				$("#"+campoCantidadTmp).val(campoActual);
			}

			//En este caso ha ocurrido un error y hay que mostrar el icono de error.
			$("#"+campoDivModificado).hide();
			$("#"+campoDivError).show();
			$("#"+campoErrorOrig).val('99');
			//Cambiamos el title
			$("#"+campoImgError).attr('title', descError);
			$("#"+campoDescErrorOrig).val(descError);
			//Añadimos la fila al array.
			addSeleccionadosP51(fila);
		}
		else
		{
			//No hay error con lo que quitamos el posible icono de error.
			$("#"+campoDivError).hide();
			$("#"+id).removeClass("editableError").addClass("editable");
			
			//En este caso se ha modificado el campo y hay que establecer el icono de modificación.
			$("#"+campoDivError).hide();
			$("#"+campoDivModificado).show();
			$("#"+campoCantidadTmp).val(campoActual);
			$("#"+campoErrorOrig).val('9');
			$("#"+campoDescErrorOrig).val('');	
			//Añadimos la fila al array.
			addSeleccionadosP51(fila);
		}
	}else{
		//Control de campo decimal erroneo
		if (isNaN(parseFloat(campoActualPunto)) || campoActual.split(",").length > 2){
			resultadoValidacion = 'N';
		}
	}
	return resultadoValidacion;
}

function validacionImplInicial(id, mostrarAlerta, tipoListado){
	
	//No se hacen validaciones, sólo formateamos el valor introducido por el usuario.
	var campoActual = $("#"+id).val();
	var campoActualPunto = $("#"+id).val().replace(',','.');
	var fila = id.substring(0, id.indexOf("_"));
	var error = 0;
	var errorFila = 0;
	var errorValor = 0;
	var descError = '';
	var campoCantidadOrig = id + "_" + tipoListado +  "_orig";
	var campoCantidadTmp = id + "_" + tipoListado + "_tmp";
	var campoArticulo = fila + "_"+tipoListado+"_codArticulo";
	var campoImplFinal = fila + "_implFinal";
	var campoFechaInicio = fila + "_" + tipoListado + "_fechaInicioCompleta";
	var campoFechaFin = fila + "_" + tipoListado + "_fechaFinCompleta";
	var campoDivModificado = fila + "_"+tipoListado+"_divModificado";
	var campoDivError = fila + "_"+tipoListado+"_divError";
	var campoImgError = fila + "_"+tipoListado+"_imgError";
	var campoErrorOrig = fila + "_"+tipoListado+"_codError_orig";	
	var campoDescErrorOrig = fila + "_"+tipoListado+"_descError_orig";	
	var resultadoValidacion = 'S';
	
	if (mostrarAlerta == 'S'){
		if (campoActual == '')
		{
			campoActualPunto = "0.000";
		}
		$("#"+id).val(campoActualPunto).formatNumber({format:"0.00",locale:"es"});
		
		if (isNaN(parseFloat(campoActualPunto)) || campoActual.split(",").length > 2 || campoActualPunto <= 0 || campoActualPunto > 9999){
			descError = replaceSpecialCharacters(invalidImplInicialP51);
			createAlert(descError, "ERROR");
			
			//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
			$("#p51_fld_NuevoOf_Selecc").val(id);
			
			//Pintamos de rojo el campo.
			$("#"+id).removeClass("editable").addClass("editableError");
			
			error = 1;
		}else{//Validación de impl inicial
			//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
			$("#p51_fld_NuevoOf_Selecc").val(id);
			var recordNuevoPedidoOferta = new NuevoPedidoOferta();

			recordNuevoPedidoOferta.codCentro = $("#centerId").val();
			recordNuevoPedidoOferta.codArticulo = $("#"+campoArticulo).val();
			recordNuevoPedidoOferta.implInicial = campoActualPunto;
			
			if (!$("#"+campoImplFinal).attr("disabled")=='disabled'){
				$("#"+campoImplFinal).val(p52FormateoCantidades(recordNuevoPedidoOferta.implInicial));
			} else {
				recordNuevoPedidoOferta.implFinal = Math.ceil(recordNuevoPedidoOferta.implInicial * 0.2);
				$("#"+campoImplFinal).val(p52FormateoCantidades(recordNuevoPedidoOferta.implFinal));
				
			}
		}

		var campoCantidadOrigPunto = $("#"+campoCantidadOrig).val().replace(',','.');
		
		if (error != 1 && !validacionFila(fila)){
			errorFila = 1;
			descError = replaceSpecialCharacters(datosObligatoriosFilaP51);
		}

		if (error == 1 || errorFila == 1)
		{
			//Cargamos los valores anteriores a la modificación.
			if (error == 1){
				$("#"+id).val(campoCantidadOrigPunto).formatNumber({format:"0.00",locale:"es"});
				$("#"+campoCantidadTmp).val(campoCantidadOrigPunto).formatNumber({format:"0.00",locale:"es"});
			}else{
				marcarValidacionFila(fila);
				$("#"+campoCantidadTmp).val(campoActual);
			}

			//En este caso ha ocurrido un error y hay que mostrar el icono de error.
			$("#"+campoDivModificado).hide();
			$("#"+campoDivError).show();
			$("#"+campoErrorOrig).val('99');
			//Cambiamos el title
			$("#"+campoImgError).attr('title', descError);
			$("#"+campoDescErrorOrig).val(descError);	
			//Añadimos la fila al array.
			addSeleccionadosP51(fila);
		}
		else
		{
			//Si está oculta la implFinal se rellena con la inicial
			if ($("#"+campoImplFinal).attr("disabled")=='disabled'){
				$("#"+campoImplFinal).val(campoActual);
			}
			
			//No hay error con lo que quitamos el posible icono de error.
			$("#"+campoDivError).hide();
			$("#"+id).removeClass("editableError").addClass("editable");
			
			//En este caso se ha modificado el campo y hay que establecer el icono de modificación.
			$("#"+campoDivError).hide();
			$("#"+campoDivModificado).show();
			$("#"+campoCantidadTmp).val(campoActual);
			$("#"+campoErrorOrig).val('9');
			$("#"+campoDescErrorOrig).val('');	
			//Añadimos la fila al array.
			addSeleccionadosP51(fila);
		}
	}else{
		//Control de campo decimal erroneo
		if (isNaN(parseFloat(campoActualPunto)) || campoActual.split(",").length > 2 || campoActualPunto <= 0 || campoActualPunto > 9999){
			resultadoValidacion = 'N';
		}
	}
	return resultadoValidacion;
}

function validacionImplFinal(id, mostrarAlerta, tipoListado){
	
	//No se hacen validaciones, sólo formateamos el valor introducido por el usuario.
	var campoActual = $("#"+id).val();
	var campoActualPunto = $("#"+id).val().replace(',','.');
	var fila = id.substring(0, id.indexOf("_"));
	var error = 0;
	var errorFila = 0;
	var descError = '';
	var campoCantidadOrig = id + "_" + tipoListado +  "_orig";
	var campoCantidadTmp = id + "_" + tipoListado + "_tmp";
	var campoDivModificado = fila + "_"+tipoListado+"_divModificado";
	var campoDivError = fila + "_"+tipoListado+"_divError";
	var campoImgError = fila + "_"+tipoListado+"_imgError";
	var campoErrorOrig = fila + "_"+tipoListado+"_codError_orig";	
	var campoDescErrorOrig = fila + "_"+tipoListado+"_descError_orig";	
	var resultadoValidacion = 'S';
	var implInicial = $("#"+fila+"_implInicial").val().replace(',','.');
	if (mostrarAlerta == 'S'){
		if (campoActual == '')
		{
			campoActualPunto = "0.000";
		}
		$("#"+id).val(campoActualPunto).formatNumber({format:"0.000",locale:"es"});
		
		if (isNaN(parseFloat(campoActualPunto)) || campoActual.split(",").length > 2 || 
				campoActualPunto <= 0 || campoActualPunto > 9999 || (implInicial * 0.2) > campoActualPunto){
			descError = replaceSpecialCharacters(invalidImplFinalP51);
			createAlert(descError, "ERROR");
			
			//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
			$("#p51_fld_NuevoOf_Selecc").val(id);
			
			//Pintamos de rojo el campo.
			$("#"+id).removeClass("editable").addClass("editableError");
			
			error = 1;
		}

		var campoCantidadOrigPunto = $("#"+campoCantidadOrig).val().replace(',','.');
		
		if (error != 1 && !validacionFila(fila)){
			errorFila = 1;
			descError = replaceSpecialCharacters(datosObligatoriosFilaP51);
		}

		if (error == 1 || errorFila == 1)
		{
			//Cargamos los valores anteriores a la modificación.
			if (error == 1){
				$("#"+id).val(campoCantidadOrigPunto).formatNumber({format:"0.00",locale:"es"});
				$("#"+campoCantidadTmp).val(campoCantidadOrigPunto).formatNumber({format:"0.00",locale:"es"});
			}else{
				marcarValidacionFila(fila);
				$("#"+campoCantidadTmp).val(campoActual);
			}

			//En este caso ha ocurrido un error y hay que mostrar el icono de error.
			$("#"+campoDivModificado).hide();
			$("#"+campoDivError).show();
			$("#"+campoErrorOrig).val('99');
			//Cambiamos el title
			$("#"+campoImgError).attr('title', descError);
			$("#"+campoDescErrorOrig).val(descError);	
			//Añadimos la fila al array.
			addSeleccionadosP51(fila);
		}
		else
		{
			//No hay error con lo que quitamos el posible icono de error.
			$("#"+campoDivError).hide();
			$("#"+id).removeClass("editableError").addClass("editable");
			
			//En este caso se ha modificado el campo y hay que establecer el icono de modificación.
			$("#"+campoDivError).hide();
			$("#"+campoDivModificado).show();
			$("#"+campoCantidadTmp).val(campoActual);
			$("#"+campoErrorOrig).val('9');
			$("#"+campoDescErrorOrig).val('');	
			//Añadimos la fila al array.
			addSeleccionadosP51(fila);
		}
	}else{
		//Control de campo decimal erroneo
		if (isNaN(parseFloat(campoActualPunto)) || campoActual.split(",").length > 2 || 
				campoActualPunto <= 0 || campoActualPunto > 9999 || (implInicial * 0.2) > campoActualPunto){
			resultadoValidacion = 'N';
		}
	}
	return resultadoValidacion;
}

function validacionFila(fila){
	
	var validaLinea = true;
	
	if(p51EsCampoFechaVacio(fila+"_fechaInicio")){
		validaLinea = false;
	}

	if(p51EsCampoFechaVacio(fila+"_fechaFin")){
		validaLinea = false;
	}
	
	if (validaLinea){
	var fin = $("#"+fila+"_fechaFin").datepicker('getDate');
	 var inicio = $("#"+fila+"_fechaInicio").datepicker('getDate');
	 if (inicio > fin){
		 validaLinea = false;
	 }
	}
	if ($("#p51_txt_flgFrescoPuro").val()=='S'){
		
		if(p51EsCampoCantidadVacio(fila+"_cantidad1")){
			validaLinea = false;
		}

		if (!p51EsCampoFechaVacio(fila+"_fp_fecha2") && p51EsCampoCantidadVacio(fila+"_cantidad2")){
			validaLinea = false;
		}

		if (!p51EsCampoFechaVacio(fila+"_fp_fecha3") && p51EsCampoCantidadVacio(fila+"_cantidad3")){
			validaLinea = false;
		}

	} else {
		if(p51EsCampoCantidadVacio(fila+"_implInicial") ){
			validaLinea = false;
		}
 
		if ($("#"+fila+"_implFinal").attr("disabled")!='disabled' && p51EsCampoCantidadVacio(fila+"_implFinal")){
			validaLinea = false;
		}
 
	}
	return validaLinea;
}
		
function marcarValidacionFila(fila){
	
	var validaLinea = true;
	if(p51EsCampoFechaVacio(fila+"_fechaInicio")){
		validaLinea = false;
		//Pintamos de rojo el campo.
		$("#"+fila+"_fechaInicio").removeClass("editable").addClass("editableError");
	}else{
		$("#"+fila+"_fechaInicio").removeClass("editableError").addClass("editable");
	}

	if(p51EsCampoFechaVacio(fila+"_fechaFin")){
		validaLinea = false;
		//Pintamos de rojo el campo.
		$("#"+fila+"_fechaFin").removeClass("editable").addClass("editableError");
	}else{
		$("#"+fila+"_fechaFin").removeClass("editableError").addClass("editable");
	}
	 if(validaLinea){
		 var fin = $("#"+fila+"_fechaFin").datepicker('getDate');
		 var inicio = $("#"+fila+"_fechaInicio").datepicker('getDate');
		 if (fin >= inicio){
		 	$("#"+fila+"_fechaFin").removeClass("editableError").addClass("editable");
		 	$("#"+fila+"_fechaInicio").removeClass("editableError").addClass("editable");
		 } else {
		 	validaLinea = false;
		 	$("#"+fila+"_fechaFin").removeClass("editable").addClass("editableError");
		 	$("#"+fila+"_fechaInicio").removeClass("editable").addClass("editableError");
		 }

 		 if ($("#p51_txt_flgFrescoPuro").val()=='S'){
			
			if(p51EsCampoCantidadVacio(fila+"_cantidad1")){
				//Pintamos de rojo el campo.
				$("#"+fila+"_cantidad1").removeClass("editable").addClass("editableError");
			}else{
				$("#"+fila+"_cantidad1").removeClass("editableError").addClass("editable");
			}
	
			if (!p51EsCampoFechaVacio(fila+"_fp_fecha2") && p51EsCampoCantidadVacio(fila+"_cantidad2")){
				//Pintamos de rojo el campo.
				$("#"+fila+"_cantidad2").removeClass("editable").addClass("editableError");
			}else{
				$("#"+fila+"_cantidad2").removeClass("editableError").addClass("editable");
			}
	
			if (!p51EsCampoFechaVacio(fila+"_fp_fecha3") && p51EsCampoCantidadVacio(fila+"_cantidad3")){
				//Pintamos de rojo el campo.
				$("#"+fila+"_cantidad3").removeClass("editable").addClass("editableError");
			}else{
				$("#"+fila+"_cantidad3").removeClass("editableError").addClass("editable");
			}
	
		} else {
			if(p51EsCampoCantidadVacio(fila+"_implInicial") ){
				//Pintamos de rojo el campo.
				$("#"+fila+"_implInicial").removeClass("editable").addClass("editableError");
			}else{
				$("#"+fila+"_implInicial").removeClass("editableError").addClass("editable");
			}
	 
			if ($("#"+fila+"_implFinal").attr("disabled")!='disabled' && p51EsCampoCantidadVacio(fila+"_implFinal")){
				//Pintamos de rojo el campo.
				$("#"+fila+"_implFinal").removeClass("editable").addClass("editableError");
			}else{
				$("#"+fila+"_implFinal").removeClass("editableError").addClass("editable");
			}
	 
		}
	 }
}

function p51EsCampoCantidadVacio(id){
	var valorCampo = $("#"+id).val();
	var valorCampoPunto = null;
	var valorCampoVacio = $.formatNumber(0,{format:"0.00",locale:"es"});
	if (valorCampo == null || valorCampo == "null" || valorCampo == ''){
		return true
	}else{
		valorCampoPunto = $.formatNumber(valorCampo.replace(',','.'),{format:"0.00",locale:"es"});
		if (isNaN(parseFloat(valorCampoPunto)) || (valorCampoPunto == valorCampoVacio)){
			return true;
		}else{
			return false;
		}
	}
}

function p51EsCampoFechaVacio(id){
	var valorCampo = $("#"+id).val();
	if (valorCampo == null || valorCampo == "null" || valorCampo == ''){
		return true
	}else{
		return false;
	}
}

function p51ValidarFechas(){
	var message;
	var fechaIni = $( "#p51_fechaInicioDatePicker" ).datepicker("getDate");
	var fechaFin = $( "#p51_fechaFinDatePicker" ).datepicker("getDate");
	if (fechaIni > fechaFin){
		message = literalInvalidDates;
	}
	return message;
}

function p51validarDatesGrid(fila, tipo){
	var validDates = true;
	var fin = $.datepicker.parseDate("ddmmyy",$("#"+fila + "_"+tipo+"_fechaFinCompleta").val());
    var inicio = $.datepicker.parseDate("ddmmyy",$("#"+fila + "_"+tipo+"_fechaInicioCompleta").val());
	var campoErrorOrig = fila + "_"+tipo+"_codError_orig";
	var campoDescErrorOrig = fila + "_"+tipo+"_descError_orig";
	var campoDivModificado = fila + "_"+tipo+"_divModificado";
	var campoDivError = fila + "_"+tipo+"_divError";
	var campoImgError = fila + "_"+tipo+"_imgError";
	
	if (fin >= inicio){
		$("#"+fila+"_fechaFin").removeClass("editableError").addClass("editable");
		$("#"+fila+"_fechaInicio").removeClass("editableError").addClass("editable");
		if ($("#"+campoErrorOrig).val()!=null && $("#"+campoErrorOrig).val()!='9' && $("#"+campoErrorOrig).val()!=''){
			$("#"+campoDivError).hide();
			$("#"+campoDivModificado).show();
			$("#"+campoErrorOrig).val('9');
			$("#"+campoDescErrorOrig).val('');
			addSeleccionadosP51(fila);
		}
	} else {
		validDates = false;
		$("#"+fila+"_fechaFin").removeClass("editable").addClass("editableError");
		$("#"+fila+"_fechaInicio").removeClass("editable").addClass("editableError");
		if (tipo == "fp"){
			$("#gridP51FP").find("#"+fila).find("[aria-describedby='gridP51FP_fecha2']").html("");
			$("td[aria-describedby='gridP51FP_fecha2']:nth-child("+fila+")").html("");
			$("#"+fila+"_fp_fecha2").val("");
			$("#"+fila+"_cantidad2").val("");
			$("#"+fila+"_cantidad2").attr("disabled", "disabled");
			$("#gridP51FP").find("#"+fila).find("[aria-describedby='gridP51FP_fecha3']").html("");
			$("#"+fila+"_fp_fecha3").val("");
			$("#"+fila+"_cantidad3").val("");
			$("#"+fila+"_cantidad3").attr("disabled", "disabled");
			$("#"+fila+"_fp_fechaPilada").val("");	
			if ($("#"+campoErrorOrig).val() == 99){
				$("#"+fila+"_cantidad1").removeClass("editableError").addClass("editable");
				$("#"+fila+"_cantidad2").removeClass("editableError").addClass("editable");
				$("#"+fila+"_cantidad3").removeClass("editableError").addClass("editable");
			}
		} else {
			if ($("#"+campoErrorOrig).val() == 99){
				$("#"+fila+"_implInicial").removeClass("editableError").addClass("editable");
				$("#"+fila+"_implFinal").removeClass("editableError").addClass("editable");
			}
		}

		$("#"+campoDivModificado).hide();
		$("#"+campoDivError).show();
		$("#"+campoErrorOrig).val('99');
		$("#"+campoImgError).attr('title', literalInvalidDates);
		$("#"+campoDescErrorOrig).val(literalInvalidDates);
		addSeleccionadosP51(fila);
	}
	return validDates;
}

function controlNavegacionP51(e) {
	var idActual = e.target.id;
	var tipoListado;
	if ($("#p51_txt_flgFrescoPuro").val()=='S'){
		tipoListado = 'fp';
	}else{
		tipoListado = 'afi';
	}
	var idActualTmp = idActual+"_"+tipoListado+"_tmp";
	var campoActualTmpPunto;
	var idFoco;
	//Obtención de fila y columna actuales
	var fila = idActual.substring(0, idActual.indexOf("_"));
	var filaFoco;
	var nombreColumna = idActual.substring(idActual.indexOf("_")+1);
	var validacionNavegacion = 'S';
	var key = e.which; //para soporte de todos los navegadores
 	if (key == 13){//Tecla enter, buscar
 		//e.preventDefault();
		finderP51();
    }
 	//Para los campos de fecha no se permite la modificación en el input
 	if(nombreColumna == "fechaInicio" || nombreColumna == "fechaFin"){
 		if (key != 37 && key != 38 && key != 39 && key != 40 && key != 9 && key != 13){
 			e.preventDefault();
 		}
 	}
 	//Se recupera el valor temporal para saber si ha cambiado y lanzar la validación
 	if (nombreColumna == "cantidad1" || nombreColumna == "cantidad2" || nombreColumna == "cantidad3" ||
 		nombreColumna == "implInicial" || nombreColumna == "implFinal"){
 			if (!p51EsCampoCantidadVacio(idActualTmp)){
 				campoActualTmpPunto = $("#"+idActualTmp).val().replace(',','.');
 			}else{
 				campoActualTmpPunto = "";
 			}
 	}
 	
    //Flechas de cursores para navegación
 	//Se controla el tipo de listado porque los campos a controlar son diferentes
 	if ($("#p51_txt_flgFrescoPuro").val()=='S'){
	    if (key == 37){//Tecla izquierda
	    	e.preventDefault();
	    	if(nombreColumna == "cantidad1"){
	        	idFoco = fila + "_fechaInicio";
	        	validacionNavegacion = validacionCantidad(fila + "_cantidad1", tipoListado, '1');
	    	}
	    	if(nombreColumna == "cantidad2"){
	        	idFoco = fila + "_cantidad1";
	        	validacionNavegacion = validacionCantidad(fila + "_cantidad2", tipoListado, '2');
	    	}
	    	if(nombreColumna == "cantidad3"){
	        	idFoco = fila + "_cantidad2";
	        	validacionNavegacion = validacionCantidad(fila + "_cantidad3", tipoListado, '3');
	    	}
	    	if(nombreColumna == "fechaFin"){
	    		if($("#"+fila + "_cantidad3").is(':visible') && 'disabled' !=$("#"+fila + "_cantidad3").attr('disabled')){
	    			idFoco = fila + "_cantidad3";
	        	}else if($("#"+fila + "_cantidad2").is(':visible') && 'disabled' !=$("#"+fila + "_cantidad2").attr('disabled')){
	    			idFoco = fila + "_cantidad2";
	        	}else if($("#"+fila + "_cantidad1").is(':visible') && 'disabled' !=$("#"+fila + "_cantidad1").attr('disabled')){
	    			idFoco = fila + "_cantidad1";
	        	}else{
	        		idFoco = fila + "_fechaInicio";
	        	}
	    	}
	    	if (validacionNavegacion=='S'){
	        	$("#"+idFoco).focus();
	        	$("#"+idFoco).select();
	        	$("#p51_fld_NuevoOf_Selecc").val(idFoco);
	    	}
	    }
	    if (key == 38){//Tecla arriba
	    	e.preventDefault();
	    	var trAnterior = $("#"+idActual).parent().parent().prevAll("tr[editable='1']").eq(0);
	        if(trAnterior.length != 0){
	        	filaFoco = trAnterior.attr('id');
		    	idFoco = filaFoco + "_" + nombreColumna;
		    	if(nombreColumna == "cantidad1"){
		    		if($("#"+filaFoco + "_cantidad1").is(':visible') && 'disabled' !=$("#"+filaFoco + "_cantidad1").attr('disabled')){
		    			idFoco = filaFoco + "_cantidad1";
		        	}else{
		        		idFoco = filaFoco + "_fechaInicio";
		        	}
		        	validacionNavegacion = validacionCantidad(fila + "_cantidad1", tipoListado, '1');
		    	}
		    	if(nombreColumna == "cantidad2"){
		    		if($("#"+filaFoco + "_cantidad2").is(':visible') && 'disabled' !=$("#"+filaFoco + "_cantidad2").attr('disabled')){
		    			idFoco = filaFoco + "_cantidad2";
		    		}else if($("#"+filaFoco + "_cantidad1").is(':visible') && 'disabled' !=$("#"+filaFoco + "_cantidad1").attr('disabled')){
		    			idFoco = filaFoco + "_cantidad1";
		        	}else{
		        		idFoco = filaFoco + "_fechaInicio";
		        	}
		        	validacionNavegacion = validacionCantidad(fila + "_cantidad2", tipoListado, '2');
		    	}
		    	if(nombreColumna == "cantidad3"){
		    		if($("#"+filaFoco + "_cantidad3").is(':visible') && 'disabled' !=$("#"+filaFoco + "_cantidad3").attr('disabled')){
		    			idFoco = filaFoco + "_cantidad3";
		    		}else if($("#"+filaFoco + "_cantidad2").is(':visible') && 'disabled' !=$("#"+filaFoco + "_cantidad2").attr('disabled')){
		    			idFoco = filaFoco + "_cantidad2";
		    		}else if($("#"+filaFoco + "_cantidad1").is(':visible') && 'disabled' !=$("#"+filaFoco + "_cantidad1").attr('disabled')){
		    			idFoco = filaFoco + "_cantidad1";
		        	}else{
		        		idFoco = filaFoco + "_fechaInicio";
		        	}
		        	validacionNavegacion = validacionCantidad(fila + "_cantidad3", tipoListado, '3');
		    	}
		    	if (validacionNavegacion=='S'){
			    	$("#"+idFoco).focus();
			    	$("#"+idFoco).select();
			    	$("#p51_fld_NuevoOf_Selecc").val(idFoco);
		    	}
	        }
	    }
	    if (key == 39){//Tecla derecha
	    	e.preventDefault();
	    	if(nombreColumna == "fechaInicio"){
	    		if($("#"+fila + "_cantidad1").is(':visible') && 'disabled' !=$("#"+fila + "_cantidad1").attr('disabled')){
	    			idFoco = fila + "_cantidad1";
	        	}else{
	        		idFoco = fila + "_fechaFin";
	        	}
	    	}
	    	if(nombreColumna == "cantidad1"){
	    		if($("#"+fila + "_cantidad2").is(':visible') && 'disabled' !=$("#"+fila + "_cantidad2").attr('disabled')){
	    			idFoco = fila + "_cantidad2";
	        	}else{
	        		idFoco = fila + "_fechaFin";
	        	}
	        	validacionNavegacion = validacionCantidad(fila + "_cantidad1", tipoListado, '1');
	    	}
	    	if(nombreColumna == "cantidad2"){
	    		if($("#"+fila + "_cantidad3").is(':visible') && 'disabled' !=$("#"+fila + "_cantidad3").attr('disabled')){
	    			idFoco = fila + "_cantidad3";
	        	}else{
	        		idFoco = fila + "_fechaFin";
	        	}
	        	validacionNavegacion = validacionCantidad(fila + "_cantidad2", tipoListado, '2');
	    	}
	    	if(nombreColumna == "cantidad3"){
	        	idFoco = fila + "_fechaFin";
	        	validacionNavegacion = validacionCantidad(fila + "_cantidad3", tipoListado, '3');
	    	}
	    	if (validacionNavegacion=='S'){
	        	$("#"+idFoco).focus();
	        	$("#"+idFoco).select();
	        	$("#p51_fld_NuevoOf_Selecc").val(idFoco);
	    	}
	    }
	    if (key == 40){//Tecla abajo
	    	e.preventDefault();
	    	var trSiguiente = $("#"+idActual).parent().parent().nextAll("tr[editable='1']").eq(0);
	        if(trSiguiente.length != 0){
	        	filaFoco = trSiguiente.attr('id');
		    	idFoco = filaFoco + "_" + nombreColumna;
		    	if(nombreColumna == "cantidad1"){
		    		if($("#"+filaFoco + "_cantidad1").is(':visible') && 'disabled' !=$("#"+filaFoco + "_cantidad1").attr('disabled')){
		    			idFoco = filaFoco + "_cantidad1";
		        	}else{
		        		idFoco = filaFoco + "_fechaInicio";
		        	}
		        	validacionNavegacion = validacionCantidad(fila + "_cantidad1", tipoListado, '1');
		    	}
		    	if(nombreColumna == "cantidad2"){
		    		if($("#"+filaFoco + "_cantidad2").is(':visible') && 'disabled' !=$("#"+filaFoco + "_cantidad2").attr('disabled')){
		    			idFoco = filaFoco + "_cantidad2";
		    		}else if($("#"+filaFoco + "_cantidad1").is(':visible') && 'disabled' !=$("#"+filaFoco + "_cantidad1").attr('disabled')){
		    			idFoco = filaFoco + "_cantidad1";
		        	}else{
		        		idFoco = filaFoco + "_fechaInicio";
		        	}
		        	validacionNavegacion = validacionCantidad(fila + "_cantidad2", tipoListado, '2');
		    	}
		    	if(nombreColumna == "cantidad3"){
		    		if($("#"+filaFoco + "_cantidad3").is(':visible') && 'disabled' !=$("#"+filaFoco + "_cantidad3").attr('disabled')){
		    			idFoco = filaFoco + "_cantidad3";
		    		}else if($("#"+filaFoco + "_cantidad2").is(':visible') && 'disabled' !=$("#"+filaFoco + "_cantidad2").attr('disabled')){
		    			idFoco = filaFoco + "_cantidad2";
		    		}else if($("#"+filaFoco + "_cantidad1").is(':visible') && 'disabled' !=$("#"+filaFoco + "_cantidad1").attr('disabled')){
		    			idFoco = filaFoco + "_cantidad1";
		        	}else{
		        		idFoco = filaFoco + "_fechaInicio";
		        	}
		        	validacionNavegacion = validacionCantidad(fila + "_cantidad3", tipoListado, '3');
		    	}
		    	if (validacionNavegacion=='S'){
			    	$("#"+idFoco).focus();
			    	$("#"+idFoco).select();
			    	$("#p51_fld_NuevoOf_Selecc").val(idFoco);
		    	}
	        }
	    }
	}else{//Alimentación
	    if (key == 37){//Tecla izquierda
	    	e.preventDefault();
	    	if(nombreColumna == "implInicial"){
	        	idFoco = fila + "_fechaFin";
	        	validacionNavegacion = validacionImplInicial(fila + "_implInicial", 'S', tipoListado);
	    	}
	    	if(nombreColumna == "implFinal"){
	        	idFoco = fila + "_implInicial";
	        	validacionNavegacion = validacionImplFinal(fila + "_implFinal", 'S', tipoListado);
	    	}
	    	if(nombreColumna == "fechaFin"){
        		idFoco = fila + "_fechaInicio";
	    	}
	    	if (validacionNavegacion=='S'){
	        	$("#"+idFoco).focus();
	        	$("#"+idFoco).select();
	        	$("#p51_fld_NuevoOf_Selecc").val(idFoco);
	    	}
	    }
	    if (key == 38){//Tecla arriba
	    	e.preventDefault();
	    	var trAnterior = $("#"+idActual).parent().parent().prevAll("tr[editable='1']").eq(0);
	        if(trAnterior.length != 0){
	        	filaFoco = trAnterior.attr('id');
		    	idFoco = filaFoco + "_" + nombreColumna;
		    	if(nombreColumna == "implInicial"){
	        		idFoco = filaFoco + "_implInicial";
		    		validacionNavegacion = validacionImplInicial(fila + "_implInicial", 'S', tipoListado);
		    	}
		    	if(nombreColumna == "implFinal"){
		    		if($("#"+filaFoco + "implFinal").is(':visible') && 'disabled' !=$("#"+filaFoco + "implFinal").attr('disabled')){
		    			idFoco = filaFoco + "_implFinal";
		        	}else{
		        		idFoco = filaFoco + "_implInicial";
		        	}
		    		validacionNavegacion = validacionImplFinal(fila + "_implFinal", 'S', tipoListado);
		    	}
		    	if (validacionNavegacion=='S'){
			    	$("#"+idFoco).focus();
			    	$("#"+idFoco).select();
			    	$("#p51_fld_NuevoOf_Selecc").val(idFoco);
		    	}
	        }
	    }
	    if (key == 39){//Tecla derecha
	    	e.preventDefault();
	    	if(nombreColumna == "fechaInicio"){
        		idFoco = fila + "_fechaFin";
	    	}
	    	if(nombreColumna == "fechaFin"){
        		idFoco = fila + "_implInicial";
	    	}
	    	if(nombreColumna == "implInicial"){
	    		if($("#"+fila + "_implFinal").is(':visible') && 'disabled' !=$("#"+fila + "_implFinal").attr('disabled')){
	    			idFoco = fila + "_implFinal";
	        	}
	    		validacionNavegacion = validacionImplInicial(fila + "_implInicial", 'S', tipoListado);
	    	}
	    	if (validacionNavegacion=='S'){
	        	$("#"+idFoco).focus();
	        	$("#"+idFoco).select();
	        	$("#p51_fld_NuevoOf_Selecc").val(idFoco);
	    	}
	    }
	    if (key == 40){//Tecla abajo
	    	e.preventDefault();
	    	var trSiguiente = $("#"+idActual).parent().parent().nextAll("tr[editable='1']").eq(0);
	        if(trSiguiente.length != 0){
	        	filaFoco = trSiguiente.attr('id');
		    	idFoco = filaFoco + "_" + nombreColumna;
		    	if(nombreColumna == "implInicial"){
	        		idFoco = filaFoco + "_implInicial";
	        		validacionNavegacion = validacionImplInicial(fila + "_implInicial", 'S', tipoListado);
		    	}
		    	if(nombreColumna == "implFinal"){
		    		if($("#" + filaFoco + "_implFinal").is(':visible') && 'disabled' !=$("#"+filaFoco + "_implFinal").attr('disabled')){
		    			idFoco = filaFoco + "_implFinal";
		        	}else{
		        		idFoco = filaFoco + "_implInicial";
		        	}
		    		validacionNavegacion = validacionImplFinal(fila + "_implFinal", 'S', tipoListado);
		    	}
		    	if (validacionNavegacion=='S'){
			    	$("#"+idFoco).focus();
			    	$("#"+idFoco).select();
			    	$("#p51_fld_NuevoOf_Selecc").val(idFoco);
		    	}
	        }
	    }
	}
}

function p51FormateoUnidadesCaja(cantidad) {
	
	if (cantidad != '')
	{
		//Actualizamos el formateo de los campos a dos decimales
		return $.formatNumber(cantidad,{format:"0.##",locale:"es"});
	}
	else
	{
		return '';
	}
}

function p51ResultadoSaveGrid(error){
	var message = null;
	var level = null;
	if (error == 0){
		message = nuevoOfertaSaveResultOK;
		level = "INFO";
	} else {
		message = nuevoOfertaSaveResultError;
	message = message.replace('{0}',error);
		level = "ERROR";
}
	createAlert(message, level);
}

function p51AlertBotonAceptarClick(){
	//Nos posicionamos en el campo al que ha navegado el usuario y que hemos almacenado en el hidden.
	$("#p03_btn_aceptar").unbind("click");
	$("#p03_btn_aceptar").bind("click", function(e) {
		if ($("#p51_fld_NuevoOf_Selecc").val() != '')
		{
			$("#"+$("#p51_fld_NuevoOf_Selecc").val()).focus();
			$("#"+$("#p51_fld_NuevoOf_Selecc").val()).select();
			e.stopPropagation();
			$("#p51_fld_NuevoOf_Selecc").val('');
		}
	 });
}
