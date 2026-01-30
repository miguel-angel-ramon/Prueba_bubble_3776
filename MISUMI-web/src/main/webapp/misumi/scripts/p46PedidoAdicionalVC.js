var gridP46PedidoAdicionalVC=null;
var tableFilter = null;
var invalidCant1 = null;
var invalidCant2 = null;
var invalidCant3 = null;
var invalidCant4 = null;
var invalidCant5 = null;
var seleccionadosP46 = new Array();
var listadoModificadosP46 = new Array();
var saveResultOK = null;
var saveResultError = null;
var descComboOferta = null;
var errorValidacion = null;
var mensajeFechaHasta = null;
var filaAyuda = null;
var disabledValidation = false;
var p46EstadoNoActiva = null;
var p46ConsultaMotivosBloqueo = null;

function initializeP46(){
		initializeScreenPedidoAdicionalVC();
}

function initializeScreenPedidoAdicionalVC(){
	$("#p46_cmb_descripcion").combobox(null);
	$("#p46_cmb_descripcion").combobox({
        selected: function(event, ui) {
        	if (null != ui.item.value && ui.item.value != ""){
				var comboValorCompleto = ui.item.value;
				var comboValor = comboValorCompleto.split("**");
				var comboDescripcion = comboValor[0];
				var comboFecha = comboValor[1];
				descComboOferta = comboDescripcion;
	        	dibujarFechaHasta(comboFecha);
        	} else {
        		descComboOferta = null;
        		dibujarFechaHasta(null);
        	}
        	

        	
        	reloadDataP46PedidoAdicionalVC('N');  
        }  
        ,	
        changed: function(event, ui) { 
        	if (null != ui.item && null != ui.item.value && ui.item.value != ""){
			var comboValorCompleto = ui.item.value;
			var comboValor = comboValorCompleto.split("**");
			var comboDescripcion = comboValor[0];
			var comboFecha = comboValor[1];
			descComboOferta = comboDescripcion;

        	dibujarFechaHasta(comboFecha);
        	} else {
        		descComboOferta = null;
        		dibujarFechaHasta(null);
        	}
        	
        	reloadDataP46PedidoAdicionalVC('N');
		   }
     });
	$("#p46_btn_validar").click(function () {
		saveDataP46PedidoAdicionalVC();
	});
	
	//Nos posicionamos en el campo al que ha navegado el usuario y que hemos almacenado en el hidden.
	$("#p03_btn_aceptar").bind("click", function(e) {
		if ($("#p46_fld_ValidarCant_Selecc").val() != '')
		{
			$("#"+$("#p46_fld_ValidarCant_Selecc").val()).focus();
			$("#"+$("#p46_fld_ValidarCant_Selecc").val()).select();
			e.stopPropagation();
			$("#p46_fld_ValidarCant_Selecc").val('');
		}
	 });

	loadP46PedidoAdicionalVC(locale);
}

function resetResultadosPedidoAdicionalVC(){
	
	if (gridP46PedidoAdicionalVC != null) {
		gridP46PedidoAdicionalVC.clearGrid();
	}
	
}

function resetDatosP46(){
	//Borrado de mensaje de fecha hasta
	$("#p46_lbl_fechaHasta").html("");
	$("#p46_fld_fechaHasta").val("");
	//Borrado de datos de grid
	$('#gridP46VC').jqGrid('clearGridData');

}

function reloadValidarCant() {

	$("#p40_pestanaValidarCant").val("S");
	
	reloadDataP46PedidoAdicionalVC('N');
}

function loadP46PedidoAdicionalVC(locale){
	gridP46PedidoAdicionalVC = new GridP46PedidoAdicionalVC(locale);
	
	var jqxhr = $.getJSON(gridP46PedidoAdicionalVC.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				gridP46PedidoAdicionalVC.colNames = data.p46PedidoAdicionalVCColNames;
				gridP46PedidoAdicionalVC.title = data.p46PedidoAdicionalVCGridTitle;
				gridP46PedidoAdicionalVC.emptyRecords= data.emptyRecords;
				tableFilter = data.tableFilter;
				invalidCant1 = data.invalidCant1;
				invalidCant2 = data.invalidCant2;
				invalidCant3 = data.invalidCant3;
				invalidCant4 = data.invalidCant4;
				invalidCant5 = data.invalidCant5;
				saveResultOK = data.saveResultOK;
				saveResultError = data.saveResultError;
				errorValidacion = data.errorValidacion;
				mensajeFechaHasta = data.mensajeFechaHasta;
				p46EstadoNoActiva = data.p46EstadoNoActiva;
				p46ConsultaMotivosBloqueo = data.p46ConsultaMotivosBloqueo;
				loadP46PedidoAdicionalVCMock(gridP46PedidoAdicionalVC);
				setHeadersTitles(data);
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
         });
}

function loadP46PedidoAdicionalVCMock(grid) {
	$(grid.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		datatype : 'local',
		contentType : 'application/json',
		mtype : 'POST',
		colNames : grid.colNames,
		colModel :grid.cm, 
		rowNum : 10,
		rowList : [ 10, 20, 30 ],
		height : "100%",
		autowidth : false,
		width : "auto",
		rownumbers : true,
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
			if (grid.firstLoad){
				jQuery(grid.nameJQuery).jqGrid('setGridWidth',$("#p46_AreaVC").width(),false);
			}
		},
		onPaging : function(postdata) {			
			alreadySorted = false;
			grid.sortIndex = null;
			grid.sortOrder = null;
			reloadDataP46PedidoAdicionalVC('S');
			return 'stop';
		},
		resizeStop: function () {
			grid.modificado = true;
			grid.saveColumnState.call($(this),grid.myColumnsState);
			jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#p46_AreaVC").width(), false);
        },
		onSelectRow: function(id){

		},
		onSortCol : function (index, columnIndex, sortOrder){
			grid.sortIndex = index;
			grid.sortOrder = sortOrder;
			reloadDataP46PedidoAdicionalVC('S');
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
            				var autowidth = false;
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
                		            	 var cad =gridP46PedidoAdicionalVC.nameJQuery+'_'+cmName;
                		            	 var ancho = "'"+colStates[cmName].width+"px'";
                		            	 var cell = jQuery('table'+gridP46PedidoAdicionalVC.nameJQuery+' tr.jqgfirstrow td:nth-child(' + (i+1) + ')');
                		            	 cell.css("width", colStates[cmName].width + "px");
                		            	
                		            	 jQuery(cad).css("width", colStates[cmName].width + "px");
                		            	
                		             }
                		         }
                		         
            				} else {
            					this.jqGrid("remapColumns", perm, true);
            				}
            				gridP46PedidoAdicionalVC.saveColumnState.call(this, perm);
            				jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('setGridWidth', $("#p46_AreaVC").width(), autowidth);
            			}
            		}
				}		
		); } });
}

function reloadDataP46PedidoAdicionalVC(recarga) {

	if (gridP46PedidoAdicionalVC.firstLoad) {
		jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('setGridWidth', $("#p46_AreaVC").width(), false);
		gridP46PedidoAdicionalVC.firstLoad = false;
		if (gridP46PedidoAdicionalVC.isColState) {
			$(this).jqGrid("remapColumns", gridP46PedidoAdicionalVC.myColumnsState.permutation,true);
		}
	} else {
		gridP46PedidoAdicionalVC.myColumnsState = gridP46PedidoAdicionalVC.restoreColumnState(gridP46PedidoAdicionalVC.cm);
		gridP46PedidoAdicionalVC.isColState = typeof (gridP46PedidoAdicionalVC.myColumnsState) !== 'undefined'
				&& gridP46PedidoAdicionalVC.myColumnsState !== null;
		jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('setGridWidth', $("#p46_AreaVC").width(), false);
	}


	var codArea = null;
	var codSeccion = null;
	var mca = "N";
	var cantidad1 = null;
	var cantidad2 = null;
	var cantidad3 = null;
	var initialValue = "N"
	if ($("#p40_cmb_seccion").combobox('getValue') != null&& $("#p40_cmb_seccion").combobox('getValue') != '') {
		valorSeccion = $("#p40_cmb_seccion").combobox('getValue').split('*');
		codArea = valorSeccion[0];
		codSeccion = valorSeccion[1];
	}
	if($("#p40_chk_mca").is(':checked')) {
		mca = "S";
	}
	var recordPedidoAdicionalVC = new PedidoAdicionalVC($("#centerId").val(),codArea, codSeccion, $("#p40_cmb_categoria").combobox('getValue'),
			$("#p40_fld_referencia").val(), null, null, null, null, null, null, null, null, cantidad1, cantidad2, cantidad3, null, null, null, null, null, null, mca, [4,5]);

	if (null != defaultDescription){
		var comboValor = defaultDescription.split("**");
		descComboOferta = comboValor[0];
		comboFecha = comboValor[1];
		dibujarFechaHasta(comboFecha);
		initialValue = "S";
		defaultDescription = null;
	}
	if (null != descComboOferta && "null" != descComboOferta){
		recordPedidoAdicionalVC.descOferta = descComboOferta;
		//descComboOferta = null;
	} else {
		recordPedidoAdicionalVC.descOferta = null;
	}
	if (recarga == "S"){
		obtenerListadoModificadosP46();
		
		recordPedidoAdicionalVC.listadoSeleccionados = listadoModificadosP46;
	}
	
	listadoModificadosP46 = new Array();
	if (recarga == "N"){
		$('#gridP46VC').setGridParam({page:1});
	}

	var objJson = $.toJSON(recordPedidoAdicionalVC.preparePedidoAdicionalVCToJsonObject());

	$("#AreaResultados .loading").css("display", "block");
	$.ajax({
		type : 'POST',
		url : './pedidoAdicionalValidarCant/loadDataGridVC.do?page='+ gridP46PedidoAdicionalVC.getActualPage() + '&max=' + gridP46PedidoAdicionalVC.getRowNumPerPage()+ '&index=' + gridP46PedidoAdicionalVC.getSortIndex() +'&initialValue=' + initialValue + '&recarga=' + recarga  + '&sortorder=' + gridP46PedidoAdicionalVC.getSortOrder(),
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
			combo = data.listadoComboDescripciones;
			if (null != combo && combo.length > 0){
			options = "<option value=null>&nbsp;</option>";
			
				for (i = 0; i < combo.length; i++){
					var comboValorCompleto = combo[i];
					var comboValor = comboValorCompleto.split("**");
					var comboDescripcion = comboValor[0];
					var comboFecha = comboValor[1];	
					
					options = options + "<option value='" + comboValorCompleto+ "'>" + comboDescripcion + "</option>";
			   }
			   $("select#p46_cmb_descripcion").html(options);
			   if (null != data.defaultDescription){
				   defaultDescription = data.defaultDescription;
			   }

			   if (null != defaultDescription){
				   text = $("#p46_cmb_descripcion option[value='"+defaultDescription+"']").text();
					 $("#p46_cmb_descripcion").val(defaultDescription);
					 $("#p46_cmb_descripcion").combobox('autocomplete',text);
					 var comboValor = defaultDescription.split("**");
						var comboDescripcion = comboValor[0];
						var comboFecha = comboValor[1];	
					 dibujarFechaHasta(comboFecha);
					 
					 defaultDescription = null;
				}
			} else {
				$("select#p46_cmb_descripcion").html("");
			}
			$(gridP46PedidoAdicionalVC.nameJQuery)[0].addJSONData(data.datos);
			gridP46PedidoAdicionalVC.actualPage = data.datos.page;
			gridP46PedidoAdicionalVC.localData = data.datos;
			$("div#p40_AreaPestanas").attr("style", "display:block");
			var ids = jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('getDataIDs'), i, l = ids.length;
			var cont = 0;
			$("#p46_AreaVC").hide();
		    for (i = 0; i < l; i++) {
		    	$(gridP46PedidoAdicionalVC.nameJQuery).find("#" + (ids[i])).find("td").addClass("p46_columnaResaltada");
		    	   
		    	   //Control de campos modificables por fecha hasta
		    	   var modificableFechaHasta = controlModifFechaHasta(ids[i]);
				   
		    	   jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('editRow', ids[i], false);
				   var fechaInicio = $(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('getCell',ids[i],'fechaInicio');
				   var fecha2 = $(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('getCell',ids[i],'fecha2');
				   var fecha3 = $(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('getCell',ids[i],'fecha3');
				   var fecha4 = $(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('getCell',ids[i],'fecha4');
				   var fecha5 = $(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('getCell',ids[i],'fecha5');
				   var noGestionaPbl = $("#"+ids[i]+"_noGestionaPblVC").val();
				   var modificable = $("#"+ids[i]+'_modificableIndivVC').val();
					$("#"+ids[i]+"_cantidad1").filter_input({regex:'[0-9,]'});
					$("#"+ids[i]+"_cantidad1").formatNumber({format:"0.00",locale:"es"});
					$("#"+ids[i]+"_cantidad1").addClass("conAyuda");
					
					if (modificableFechaHasta && modificable.charAt(0) == "S"){
						$("#"+ids[i]+"_cantidad1").removeAttr("disabled");
						$("#"+ids[i]+"_cantidad1").focus(function(event) {
							if(!$(".p56_popupResaltado").is(':visible')){
								loadPopUpAyudaP46(event);
							}else{
								if (controlMostradoAyuda(event)){
									$("#p56_AreaAyuda").dialog('close');
									loadPopUpAyudaP46(event);
								}
							}
						});
						$("#"+ids[i]+"_cantidad1").change(function(event) {
							validacionCantidad(this.id, '1');
						});
					} else {
						$("#"+ids[i]+"_cantidad1").attr("disabled", "disabled");
					}
					if (null != fecha2 && fecha2 != ""){
						$("#"+ids[i]+"_cantidad2").filter_input({regex:'[0-9,]'});
						$("#"+ids[i]+"_cantidad2").formatNumber({format:"0.00",locale:"es"});
						$("#"+ids[i]+"_cantidad2").addClass("conAyuda");
						if (modificableFechaHasta && modificable.length > 1 && modificable.charAt(1) == "S"){
							$("#"+ids[i]+"_cantidad2").removeAttr("disabled");
							$("#"+ids[i]+"_cantidad2").focus(function(event) {
								if(!$("#p56_AreaAyuda").is(':visible')){
									loadPopUpAyudaP46(event);
								}else{
									if (controlMostradoAyuda(event)){
										$("#p56_AreaAyuda").dialog('close');
										loadPopUpAyudaP46(event);
									}
								}
							});
							$("#"+ids[i]+"_cantidad2").change(function(event) {
								validacionCantidad(this.id, '2');
							});
						} else {
							$("#"+ids[i]+"_cantidad2").attr("disabled", "disabled");
						}
					} else {
						$("#"+ids[i]+"_cantidad2").hide();
					}
					if (null != fecha3 && fecha3 != ""){
						$("#"+ids[i]+"_cantidad3").filter_input({regex:'[0-9,]'});
						$("#"+ids[i]+"_cantidad3").formatNumber({format:"0.00",locale:"es"});
						$("#"+ids[i]+"_cantidad3").addClass("conAyuda");
						if (modificableFechaHasta && modificable.length > 2 && modificable.charAt(2) == "S"){
							$("#"+ids[i]+"_cantidad3").removeAttr("disabled");
							$("#"+ids[i]+"_cantidad3").focus(function(event) {
								if(!$("#p56_AreaAyuda").is(':visible')){
									loadPopUpAyudaP46(event);
								}else{
									if (controlMostradoAyuda(event)){
										$("#p56_AreaAyuda").dialog('close');
										loadPopUpAyudaP46(event);
									}
								}
							});
							$("#"+ids[i]+"_cantidad3").change(function(event) {
								validacionCantidad(this.id, '3');
							});
						} else {
							$("#"+ids[i]+"_cantidad3").attr("disabled", "disabled");
						}
					} else{
						$("#"+ids[i]+"_cantidad3").hide();
					}
					if (null != fecha4 && fecha4 != ""){
						$("#"+ids[i]+"_cantidad4").filter_input({regex:'[0-9,]'});
						$("#"+ids[i]+"_cantidad4").formatNumber({format:"0.00",locale:"es"});
						$("#"+ids[i]+"_cantidad4").addClass("conAyuda");
						if (modificableFechaHasta && modificable.length > 3 && modificable.charAt(3) == "S"){
							$("#"+ids[i]+"_cantidad4").removeAttr("disabled");
							$("#"+ids[i]+"_cantidad4").focus(function(event) {
								if(!$("#p56_AreaAyuda").is(':visible')){
									loadPopUpAyudaP46(event);
								}else{
									if (controlMostradoAyuda(event)){
										$("#p56_AreaAyuda").dialog('close');
										loadPopUpAyudaP46(event);
									}
								}
							});
							$("#"+ids[i]+"_cantidad4").change(function(event) {
								validacionCantidad(this.id, '4');
							});
						} else {
							$("#"+ids[i]+"_cantidad4").attr("disabled", "disabled");
						}
					} else{
						$("#"+ids[i]+"_cantidad4").hide();
					}
					if (null != fecha5 && fecha5 != ""){
						$("#"+ids[i]+"_cantidad5").filter_input({regex:'[0-9,]'});
						$("#"+ids[i]+"_cantidad5").formatNumber({format:"0.00",locale:"es"});	
						$("#"+ids[i]+"_cantidad5").addClass("conAyuda");
						if (modificableFechaHasta && modificable.length > 4 && modificable.charAt(4) == "S"){
							$("#"+ids[i]+"_cantidad5").removeAttr("disabled");
							$("#"+ids[i]+"_cantidad5").focus(function(event) {
								if(!$("#p56_AreaAyuda").is(':visible')){
									loadPopUpAyudaP46(event);
								}else{
									if (controlMostradoAyuda(event)){
										$("#p56_AreaAyuda").dialog('close');
										loadPopUpAyudaP46(event);
									}
								}
							});
							$("#"+ids[i]+"_cantidad5").change(function(event) {
								validacionCantidad(this.id, '5');
							});
						} else {
							$("#"+ids[i]+"_cantidad5").attr("disabled", "disabled");
						}
					} else{
						$("#"+ids[i]+"_cantidad5").hide();
					}
		    	}
		    $("#p46_AreaVC").show();
		    cargarOrdenacionesColumnas();
		    $("#p46_AreaVC .ui-pg-input").focus();
		    disabledValidation = false;
			
		},
		error : function(xhr, status, error) {
			$("#AreaResultados .loading").css("display", "none");
			handleError(xhr, status, error, locale);
		}
	});
	
	hideShowColumnsVC();
}

function saveDataP46PedidoAdicionalVC() {

	if (gridP46PedidoAdicionalVC.firstLoad) {
		jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('setGridWidth', $("#p46_AreaVC").width(), false);
		gridP46PedidoAdicionalVC.firstLoad = false;
		if (gridP46PedidoAdicionalVC.isColState) {
			$(this).jqGrid("remapColumns", gridP46PedidoAdicionalVC.myColumnsState.permutation,true);
		}
	} else {
		gridP46PedidoAdicionalVC.myColumnsState = gridP46PedidoAdicionalVC.restoreColumnState(gridP46PedidoAdicionalVC.cm);
		gridP46PedidoAdicionalVC.isColState = typeof (gridP46PedidoAdicionalVC.myColumnsState) !== 'undefined'
				&& gridP46PedidoAdicionalVC.myColumnsState !== null;
		jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('setGridWidth', $("#p46_AreaVC").width(), false);
	}


	var codArea = null;
	var codSeccion = null;
	var mca = "N";
	var cantidad1 = null;
	var cantidad2 = null;
	var cantidad3 = null;
	if ($("#p40_cmb_seccion").combobox('getValue') != null&& $("#p40_cmb_seccion").combobox('getValue') != '') {
		valorSeccion = $("#p40_cmb_seccion").combobox('getValue').split('*');
		codArea = valorSeccion[0];
		codSeccion = valorSeccion[1];
	}
	if($("#p40_chk_mca").is(':checked')) {
		mca = "S";
	}
	var recordPedidoAdicionalVC = new PedidoAdicionalVC($("#centerId").val(),codArea, codSeccion, $("#p40_cmb_categoria").combobox('getValue'),
			$("#p40_fld_referencia").val(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, mca, [4,5]);

	if (null != descComboOferta && "null" != descComboOferta){
		recordPedidoAdicionalVC.descOferta = descComboOferta;
	}
		obtenerListadoModificadosP46();
		
		recordPedidoAdicionalVC.listadoSeleccionados = listadoModificadosP46;
	
	listadoModificadosP46 = new Array();
	

	var objJson = $.toJSON(recordPedidoAdicionalVC.preparePedidoAdicionalVCToJsonObject());

	$("#AreaResultados .loading").css("display", "block");

	$.ajax({
		type : 'POST',
		url : './pedidoAdicionalValidarCant/saveDataGridVC.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
			combo = data.listadoComboDescripciones;
			if (data.contadorErroneos == 0){
				$("#p46_cmb_descripcion").combobox('autocomplete',"");
				$("#p46_cmb_descripcion").combobox('comboautocomplete',null);
				descComboOferta = null;
			}

			if (null != combo && combo.length > 0){
				options = "<option value=null>&nbsp;</option>";
				
				for (i = 0; i < combo.length; i++){
					var comboValorCompleto = combo[i];
					var comboValor = comboValorCompleto.split("**");
					var comboDescripcion = comboValor[0];
					var comboFecha = comboValor[1];	
					options = options + "<option value='" + comboValorCompleto+ "'>" + comboDescripcion + "</option>";
			   }
			   $("select#p46_cmb_descripcion").html(options);
			   if (null != data.defaultDescription){
					var comboValor = data.defaultDescription.split("**");
					descComboOferta = comboValor[0];
					comboFecha = comboValor[1];
					dibujarFechaHasta(comboFecha);
				   text = $("#p46_cmb_descripcion option[value='"+data.defaultDescription+"']").text();
					 $("#p46_cmb_descripcion").val(data.defaultDescription);
					 $("#p46_cmb_descripcion").combobox('autocomplete',text);
				}
			} else {
			   $("select#p46_cmb_descripcion").html("");
			}
		
			$("#p40_fld_contadorValidarCant").text(literalValidarCant + " ("+data.contadorValidarCantExtra+")");

			$(gridP46PedidoAdicionalVC.nameJQuery)[0].addJSONData(data.datos);
			gridP46PedidoAdicionalVC.actualPage = data.datos.page;
			gridP46PedidoAdicionalVC.localData = data.datos;

			var ids = jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('getDataIDs'), i, l = ids.length;
			var cont = 0;
			$("#p46_AreaVC").hide();
		    for (i = 0; i < l; i++) {
		    	
		    	//Control de campos modificables por fecha hasta
		    	var modificableFechaHasta = controlModifFechaHasta(ids[i]);

		    	$(gridP46PedidoAdicionalVC.nameJQuery).find("#" + (ids[i])).find("td").addClass("p46_columnaResaltada");
				   jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('editRow', ids[i], true);
				   var fechaInicio = jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('getCell',ids[i],'fechaInicio');
				   var fecha2 = $(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('getCell',ids[i],'fecha2');
				   var fecha3 = $(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('getCell',ids[i],'fecha3');
				   var fecha4 = $(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('getCell',ids[i],'fecha4');
				   var fecha5 = $(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('getCell',ids[i],'fecha5');
				   var noGestionaPbl = $("#"+ids[i]+"_noGestionaPblVC").val();
				   var modificable = $("#"+ids[i]+'_modificableIndivVC').val();
					$("#"+ids[i]+"_cantidad1").filter_input({regex:'[0-9,]'});
					$("#"+ids[i]+"_cantidad1").formatNumber({format:"0.00",locale:"es"});
					$("#"+ids[i]+"_cantidad1").addClass("conAyuda");
				    //En caso de que no esté gestionado por PBL habrá que controlar si la fecha de la cantidad es >= hoy + 1
					if (modificableFechaHasta && modificable.charAt(0) == "S"){
						$("#"+ids[i]+"_cantidad1").removeAttr("disabled");
						$("#"+ids[i]+"_cantidad1").focus(function(event) {
							if(!$("#p56_AreaAyuda").is(':visible')){
								loadPopUpAyudaP46(event);
							}else{
								if (controlMostradoAyuda(event)){
									$("#p56_AreaAyuda").dialog('close');
									loadPopUpAyudaP46(event);
								}
							}
						});
						$("#"+ids[i]+"_cantidad1").change(function(event) {
							validacionCantidad(this.id, '1');
						});
					} else {
						$("#"+ids[i]+"_cantidad1").attr("disabled", "disabled");
					}
					
					if (null != fecha2 && fecha2 != ""){
						$("#"+ids[i]+"_cantidad2").filter_input({regex:'[0-9,]'});
						$("#"+ids[i]+"_cantidad2").formatNumber({format:"0.00",locale:"es"});
						$("#"+ids[i]+"_cantidad2").addClass("conAyuda");
						if (modificableFechaHasta && modificable.length > 1 && modificable.charAt(1) == "S"){
							$("#"+ids[i]+"_cantidad2").removeAttr("disabled");
							$("#"+ids[i]+"_cantidad2").focus(function(event) {
								if(!$("#p56_AreaAyuda").is(':visible')){
									loadPopUpAyudaP46(event);
								}else{
									if (controlMostradoAyuda(event)){
										$("#p56_AreaAyuda").dialog('close');
										loadPopUpAyudaP46(event);
									}
								}
							});
							$("#"+ids[i]+"_cantidad2").change(function(event) {
								validacionCantidad(this.id, '2');
							});
						} else {
							$("#"+ids[i]+"_cantidad2").attr("disabled", "disabled");
						}
					} else {
						$("#"+ids[i]+"_cantidad2").hide();
					}
					if (null != fecha3 && fecha3 != ""){
						$("#"+ids[i]+"_cantidad3").filter_input({regex:'[0-9,]'});
						$("#"+ids[i]+"_cantidad3").formatNumber({format:"0.00",locale:"es"});	
						$("#"+ids[i]+"_cantidad3").addClass("conAyuda");
						if (modificableFechaHasta && modificable.length > 2 && modificable.charAt(2) == "S"){
							$("#"+ids[i]+"_cantidad3").removeAttr("disabled");
							$("#"+ids[i]+"_cantidad3").focus(function(event) {
								if(!$("#p56_AreaAyuda").is(':visible')){
									loadPopUpAyudaP46(event);
								}else{
									if (controlMostradoAyuda(event)){
										$("#p56_AreaAyuda").dialog('close');
										loadPopUpAyudaP46(event);
									}
								}
							});
							$("#"+ids[i]+"_cantidad3").change(function(event) {
								validacionCantidad(this.id, '3');
							});
						} else {
							$("#"+ids[i]+"_cantidad3").attr("disabled", "disabled");
						}
					} else{
						$("#"+ids[i]+"_cantidad3").hide();
					}
					if (null != fecha4 && fecha4 != ""){
						$("#"+ids[i]+"_cantidad4").filter_input({regex:'[0-9,]'});
						$("#"+ids[i]+"_cantidad4").formatNumber({format:"0.00",locale:"es"});
						$("#"+ids[i]+"_cantidad4").addClass("conAyuda");
						if (modificableFechaHasta && modificable.length > 3 && modificable.charAt(3) == "S"){
							$("#"+ids[i]+"_cantidad4").removeAttr("disabled");
							$("#"+ids[i]+"_cantidad4").focus(function(event) {
								if(!$("#p56_AreaAyuda").is(':visible')){
									loadPopUpAyudaP46(event);
								}else{
									if (controlMostradoAyuda(event)){
										$("#p56_AreaAyuda").dialog('close');
										loadPopUpAyudaP46(event);
									}
								}
							});
							$("#"+ids[i]+"_cantidad4").change(function(event) {
								validacionCantidad(this.id, '4');
							});
						} else {
							$("#"+ids[i]+"_cantidad4").attr("disabled", "disabled");
						}
					} else{
						$("#"+ids[i]+"_cantidad4").hide();
					}
					if (null != fecha5 && fecha5 != ""){
						$("#"+ids[i]+"_cantidad5").filter_input({regex:'[0-9,]'});
						$("#"+ids[i]+"_cantidad5").formatNumber({format:"0.00",locale:"es"});	
						$("#"+ids[i]+"_cantidad5").addClass("conAyuda");
						if (modificableFechaHasta && modificable.length > 4 && modificable.charAt(4) == "S"){
							$("#"+ids[i]+"_cantidad5").removeAttr("disabled");
							$("#"+ids[i]+"_cantidad5").focus(function(event) {
								if(!$("#p56_AreaAyuda").is(':visible')){
									loadPopUpAyudaP46(event);
								}else{
									if (controlMostradoAyuda(event)){
										$("#p56_AreaAyuda").dialog('close');
										loadPopUpAyudaP46(event);
									}
								}
							});
							$("#"+ids[i]+"_cantidad5").change(function(event) {
								validacionCantidad(this.id, '5');
							});
						} else {
							$("#"+ids[i]+"_cantidad5").attr("disabled", "disabled");
						}
					} else{
						$("#"+ids[i]+"_cantidad5").hide();
					}
	
		    	}
			$("#p46_AreaVC").show();
		    cargarOrdenacionesColumnas();
		    $("#p46_AreaVC .ui-pg-input").focus();
		    resultadoSaveGrid(data.contadorErroneos);
		    actualizarAvisosValidarCant();
			
		},
		error : function(xhr, status, error) {
			$("#AreaResultados .loading").css("display", "none");
			handleError(xhr, status, error, locale);
		}
	});
}

/*Clase de constantes para el GRID de Pedido adicional oferta*/
function GridP46PedidoAdicionalVC (locale){
		// Atributos
		this.name = "gridP46VC"; 
		this.nameJQuery = "#gridP46VC"; 
		this.i18nJSON = './misumi/resources/p46PedidoAdicionalVC/p46pedidoAdicionalVC_' + locale + '.json';
		this.colNames = null;
		this.cm  = [{
						"name" : "mensaje",
						"index": "mensaje", 
						"formatter": p46ImageFormatMessage,
						"fixed":true,
						"width" : 75,
						"sortable" : true
					},{
						"name"  : "codArticuloGrid",
						"index" : "codArticuloGrid",
						"width" : 80,
						"sortable" : true
	                },{
						"name"  : "descriptionArtGrid",
						"index" : "descriptionArtGrid",
						"width" : 210,
						"sortable" : true
			   		},{
						"name"  : "tipoAprovisionamiento",
						"index" : "tipoAprovisionamiento", 
						"width" : 50,
						"hidden" : true,
						"sortable" : true
			   		},{
						"name"  : "uniCajaServ",
						"index" : "uniCajaServ",
						"formatter" : p46FormateoUnidadesCaja,
						"width" : 30,
						"sortable" : true
			   		},{
						"name"  : "fechaInicio",
						"index" : "fechaInicio",
						"formatter" : p46FormateoDate,
						"width" : 60,
						"sortable" : true
			   		},{
			   			"name"  : "cantidad1",
			   			"index" : "cantidad1",
			   			"formatter" : p46FormateoCantidades,
			   			"editable": true,
			   			"edittype":"text",
			   			"editoptions":{
			   	            "size":"6",
			   	            "maxlength":"5",
			   	            "dataEvents": [
			                            {//control para el keydown
			                           	  type: 'keydown',
			                                 fn: controlNavegacionP46,
			                             },// cierra el control del keydown
			                             {//control para el click
			                            	 type: 'click',
			                            	 fn: controlClickP46
			                             }
			                           ]
			   	         	},
			   			"cellEdit" : true,
			   			"cellsubmit" : "clientArray",
			   			"sortable" : false,
			   			"fixed":true,
			   			"width" : 47
			   		},{
						"name"  : "fecha2",
						"index" : "fecha2",
						"formatter" : p46FormateoDate,
						"width" : 60,
						"sortable" : true
			   		},{
			   			"name"  : "cantidad2",
			   			"index" : "cantidad2",
			   			"formatter" : p46FormateoCantidades,
			   			"editable": true,
			   			"edittype":"text",
			   			"editoptions":{
			   	            "size":"6",
			   	            "maxlength":"5",
			   	            "dataEvents": [
			                            {//control para el keydown
			                           	  type: 'keydown',
			                                 fn: controlNavegacionP46,
			                             },// cierra el control del keydown
			                             {//control para el click
			                            	 type: 'click',
			                            	 fn: controlClickP46
			                             }
			                           ]
			   	         	},
			   			"cellEdit" : true,
			   			"cellsubmit" : "clientArray",
			   			"sortable" : false,
			   			"fixed":true,
			   			"width" : 47
			   		},{
						"name"  : "fecha3",
						"index" : "fecha3",
						"formatter" : p46FormateoDate,
						"width" : 60,
						"sortable" : true
			   		},{
			   			"name"  : "cantidad3",
			   			"index" : "cantidad3",
			   			"formatter" : p46FormateoCantidades,
			   			"editable": true,
			   			"edittype":"text",
			   			"editoptions":{
			   	            "size":"6",
			   	            "maxlength":"5",
			   	            "dataEvents": [
			                            {//control para el keydown
			                           	  type: 'keydown',
			                                 fn: controlNavegacionP46,
			                             },// cierra el control del keydown
			                             {//control para el click
			                            	 type: 'click',
			                            	 fn: controlClickP46
			                             }
			                           ]
			   	         	},
			   			"cellEdit" : true,
			   			"cellsubmit" : "clientArray",
			   			"sortable" : false,
			   			"fixed":true,
			   			"width" : 47
			   		},{
						"name"  : "fecha4",
						"index" : "fecha4",
						"formatter" : p46FormateoDate,
						"width" : 60,
						"sortable" : true
			   		},{
			   			"name"  : "cantidad4",
			   			"index" : "cantidad4",
			   			"formatter" : p46FormateoCantidades,
			   			"editable": true,
			   			"edittype":"text",
			   			"editoptions":{
			   	            "size":"6",
			   	            "maxlength":"5",
			   	            "dataEvents": [
			                            {//control para el keydown
			                           	  type: 'keydown',
			                                 fn: controlNavegacionP46,
			                             },// cierra el control del keydown
			                             {//control para el click
			                            	 type: 'click',
			                            	 fn: controlClickP46
			                             }
			                           ]
			   	         	},
			   			"cellEdit" : true,
			   			"cellsubmit" : "clientArray",
			   			"sortable" : false,
			   			"fixed":true,
			   			"width" : 47
			   		},{
						"name"  : "fecha5",
						"index" : "fecha5",
						"formatter" : p46FormateoDate,
						"width" : 60,
						"sortable" : true
			   		},{
			   			"name"  : "cantidad5",
			   			"index" : "cantidad5",
			   			"formatter" : p46FormateoCantidades,
			   			"editable": true,
			   			"edittype":"text",
			   			"editoptions":{
			   	            "size":"6",
			   	            "maxlength":"5",
			   	            "dataEvents": [
			                            {//control para el keydown
			                           	  type: 'keydown',
			                                 fn: controlNavegacionP46,
			                             },// cierra el control del keydown
			                             {//control para el click
			                            	 type: 'click',
			                            	 fn: controlClickP46
			                             }
			                           ]
			   	         	},
			   			"cellEdit" : true,
			   			"cellsubmit" : "clientArray",
			   			"sortable" : false,
			   			"fixed":true,
			   			"width" : 47
			   		},{
							"name"  : "fechaFin",
							"index" : "fechaFin",
							"formatter" : p46FormateoDate,
							"width" : 60,
							"sortable" : true
				   		},{
						"name"  : "cantMin",
						"index" : "cantMin", 
						"formatter" : p46FormateoCantidades,
						"width" : 40,
						"sortable" : true
			   		},{
						"name"  : "cantMax",
						"index" : "cantMax", 
						"formatter" : p46FormateoCantidades,
						"width" : 40,
						"sortable" : true
			   		},{
						"name"  : "descOferta",
						"index" : "descOferta", 
						"width" : 50,
						"hidden" : true,
						"sortable" : true
					},{
						"name"  : "color",
						"index" : "color", 
						"width" : 50,
						"hidden" : true,
						"sortable" : true
					},{
						"name"  : "talla",
						"index" : "talla", 
						"width" : 50,
						"hidden" : true,
						"sortable" : true
					},{
						"name"  : "modeloProveedor",
						"index" : "modeloProveedor", 
						"width" : 50,
						"hidden" : true,
						"sortable" : true
					}
				];
		this.sortIndex = null;
		this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
		this.pagerName = "pagerP46VC"; 
		this.pagerNameJQuery = "#pagerP46VC";
		this.title = null;
		this.actualPage = null;
		this.localdata = null;
		this.emptyRecords = null;
		this.myColumnStateName = 'gridP46PedidoAdicionalVC.colState';
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
		
		this.saveObjectInLocalStorage = function(storageItemName, object) {
			if (typeof window.localStorage !== 'undefined') {
				window.localStorage.setItem(storageItemName, JSON.stringify(object));
			}
		}

		this.removeObjectFromLocalStorage = function(storageItemName) {
			if (typeof window.localStorage !== 'undefined') {
				window.localStorage.removeItem(storageItemName);
			}
		}

		this.getObjectFromLocalStorage = function(storageItemName) {
			if (typeof window.localStorage !== 'undefined') {
				return JSON.parse(window.localStorage.getItem(storageItemName));
			}
		}

		this.saveColumnState = function(perm) {
			var colModel = jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid(
					'getGridParam', 'colModel');
			// if (colModel == null)
			// colModel = grid.cm;
			var i;
			var l = colModel.length;
			var colItem;
			var cmName;
			var postData = jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid(
					'getGridParam', 'postData');
			var columnsState = {
				search : jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('getGridParam', 'search'),
				page : jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('getGridParam', 'page'),
				sortname : jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('getGridParam', 'sortname'),
				sortorder : jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('getGridParam', 'sortorder'),
				permutation : perm,
				colStates : {}
			};
			var colStates = columnsState.colStates;

			if ((typeof (postData) !== 'undefined')
					&& (typeof (postData.filters) !== 'undefined')) {
				columnsState.filters = postData.filters;
			}

			for (i = 0; i < l; i++) {
				colItem = colModel[i];
				cmName = colItem.name;
				if (cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {
					colStates[cmName] = {
						width : colItem.width,
						hidden : colItem.hidden
					};
				}
			}

			// this.saveObjectInLocalStorage(this.myColumnStateName, columnsState);
			if (typeof window.localStorage !== 'undefined') {
				window.localStorage.setItem(
						gridP46PedidoAdicionalVC.myColumnStateName, JSON
								.stringify(columnsState));
			}
		}

		this.restoreColumnState = function(colModel) {
			var colItem, i, l = colModel.length, colStates, cmName;
			var columnsState = this
					.getObjectFromLocalStorage(this.myColumnStateName);
			if (columnsState) {
				colStates = columnsState.colStates;
				for (i = 0; i < l; i++) {
					colItem = colModel[i];
					cmName = colItem.name;
					if (cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {
						colModel[i] = $.extend(true, {}, colModel[i],
								colStates[cmName]);
					}
				}
			}
			return columnsState;
		}
		
		this.clearGrid = function clearGrid() {
			$(this.nameJQuery).jqGrid('GridUnload');
		}
}

function p46FormateoDate(cellValue, opts, rowObject) {

	var fechaFormateada = '';
	if (cellValue != null)
	{
		
		var diaFecha = parseInt(cellValue.substring(0, 2), 10);
		var mesFecha = parseInt(cellValue.substring(2, 4), 10);
		var anyoFecha = parseInt(cellValue.substring(4), 10);
	
		fechaFormateada = $.datepicker.formatDate("D dd-M", new Date(anyoFecha,
				mesFecha - 1, diaFecha), {
			dayNamesShort : $.datepicker.regional["es"].dayNamesShort,
			dayNames : $.datepicker.regional["es"].dayNames,
			monthNamesShort : $.datepicker.regional["es"].monthNamesShort,
			monthNames : $.datepicker.regional["es"].monthNames
		});
	}

	return (fechaFormateada);

}

function p46FormateoCantidades(cantidad) {
	
	if (cantidad != '' && cantidad != null)
	{
		//Actualizamos el formateo de los campos a dos decimales
		return $.formatNumber(cantidad,{format:"0.##",locale:"es"});
	}
	else
	{
		return '';
	}
}

function p46FormateoUnidadesCaja(cantidad) {
	
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

function p46ImageFormatMessage(cellValue, opts, rData) {
	
	var imagen = "";
	var mostrarError = "none;";
	var descError = "";
	var mostrarModificado = "none;";
	var mostrarNoActiva = "none;";
	var p46EstadoNoActivaValor = "";

	
	//Controlamos los posibles errores que me lleguen para pintar el icono correspondiente.
	if (parseFloat(rData['codError']) == '1')
	{
		//Pintamos el icono de que ha ocurrido un error con la descripción de borrado
		mostrarError = "block;";
		descError = replaceSpecialCharacters(errorValidacion);
	}
	else if (parseFloat(rData['codError']) == '2')
	{
		//Pintamos el icono de que ha ocurrido un error con la descripción de modificación
		mostrarError = "block;";
		descError = replaceSpecialCharacters(errorValidacion);
	}
	else if (parseFloat(rData['codError']) == '9')
	{
		//Pintamos el icono de que se ha guardado, por que se ha realizado la modificación correctamente
		mostrarModificado = "block;";
	}
	else if (!isNaN(parseFloat(rData['codError'])))
	{
		//Pintamos el icono de que ha ocurrido un error con la descripción de modificación
		mostrarError = "block;";
		if (rData['descError'] != '')
		{
			descError = rData['descError'];
		}
		else
		{
			descError = replaceSpecialCharacters(errorModificacionValFecInicio);
		}
	}else{
		//Añadimos el literal de no activa en caso de que no se muestre ningún icono y esté bloquedo
		if (rData['estado'] != '' && rData['estado'] == "NOACT")
		{
			mostrarNoActiva = "block";
			p46EstadoNoActivaValor = p46EstadoNoActiva;
		}
	}

	imagen = "<div id='"+opts.rowId+"_divModificadoVC' align='center' style='display: "+ mostrarModificado + "'><img id='"+opts.rowId+"_imgGuardado' src='./misumi/images/modificado.png' title='"+iconoModificado+"'/></div>"; //Modificado
	imagen += "<div id='"+opts.rowId+"_divErrorVC' align='center' style='display: " + mostrarError + "'><img id='"+opts.rowId+"_imgErrorVC' src='./misumi/images/dialog-error-24.png' title='"+descError+"'/></div>"; //Error
	imagen += "<div id='"+opts.rowId+"_divNoActiva' align='center' style='display: " + mostrarNoActiva + "'><a href='#' id='"+opts.rowId+"_p46_estado' class='p46Bloqueada' onclick='javascript:p46ReloadMotivosBloqueo("+opts.rowId+")' alt='"+p46ConsultaMotivosBloqueo+"'>"+p46EstadoNoActivaValor+"</a></div>"; //No Activa
		
	//Añadimos los valores de la columna clasePedido ocultos para poder utilizarlos posteriormente.
	var datoClasePedido = "<input type='hidden' id='"+opts.rowId+"_clasePedidoVC' value='"+rData['clasePedido']+"'>";
	imagen +=  datoClasePedido;
	
	//Añadimos los valores de la columna perfil ocultos para poder utilizarlos posteriormente.
	var datoPerfil = "<input type='hidden' id='"+opts.rowId+"_perfil' value='"+rData['perfil']+"'>";
	imagen +=  datoPerfil;
	
	//Añadimos los valores de la columna identificador ocultos para poder utilizarlos posteriormente.
	var datoIdentificador = "<input type='hidden' id='"+opts.rowId+"_identificadorVC' value='"+rData['identificador']+"'>";
	imagen +=  datoIdentificador;
	
	//Añadimos los valores de la columna tipo de pedido ocultos para poder utilizarlos posteriormente.
	var datoTipoPedido = "<input type='hidden' id='"+opts.rowId+"_tipoPedidoVC' value='"+rData['tipoPedido']+"'>";
	imagen +=  datoTipoPedido;

	//Añadimos los valores de la columna fecha inicio con formato ocultos para poder utilizarlos posteriormente.
	var datoIdentificador = "<input type='hidden' id='"+opts.rowId+"_fechaInicioVC' value='"+(rData['fechaInicio']!=null?rData['fechaInicio']:'')+"'>";
	imagen +=  datoIdentificador;

	//Añadimos los valores de la columna fecha2 con formato ocultos para poder utilizarlos posteriormente.
	var datofecha2 = "<input type='hidden' id='"+opts.rowId+"_fecha2VC' value='"+(rData['fecha2']!=null?rData['fecha2']:'')+"'>";
	imagen +=  datofecha2;

	//Añadimos los valores de la columna fecha3 con formato ocultos para poder utilizarlos posteriormente.
	var datofecha3 = "<input type='hidden' id='"+opts.rowId+"_fecha3VC' value='"+(rData['fecha3']!=null?rData['fecha3']:'')+"'>";
	imagen +=  datofecha3;

	//Añadimos los valores de la columna fecha4 con formato ocultos para poder utilizarlos posteriormente.
	var datofecha4 = "<input type='hidden' id='"+opts.rowId+"_fecha4VC' value='"+(rData['fecha4']!=null?rData['fecha4']:'')+"'>";
	imagen +=  datofecha4;

	//Añadimos los valores de la columna fecha5 con formato ocultos para poder utilizarlos posteriormente.
	var datofecha5 = "<input type='hidden' id='"+opts.rowId+"_fecha5VC' value='"+(rData['fecha5']!=null?rData['fecha5']:'')+"'>";
	imagen +=  datofecha5;

	//Añadimos los valores de la columna fechaInPil con formato ocultos para poder utilizarlos posteriormente.
	var datofechaInPil = "<input type='hidden' id='"+opts.rowId+"_fechaInPilVC' value='"+(rData['fechaPilada']!=null?rData['fechaPilada']:'')+"'>";
	imagen +=  datofechaInPil;

	//Añadimos los valores de la columna fecha inicio con formato ocultos para poder utilizarlos posteriormente.
	var datoIdentificador = "<input type='hidden' id='"+opts.rowId+"_fechaFinVC' value='"+(rData['fechaFin']!=null?rData['fechaFin']:'')+"'>";
	imagen +=  datoIdentificador;
	
	var datoCantidad1Orig = "<input type='hidden' id='"+opts.rowId+"_cantidad1_orig' value='"+rData['cantidad1']+"'>";
	imagen +=  datoCantidad1Orig;
	
	var datoCantidad1 = "<input type='hidden' id='"+opts.rowId+"_cantidad1_tmp' value='"+rData['cantidad1']+"'>";
	imagen +=  datoCantidad1;

	var datoCantidad2Orig = "<input type='hidden' id='"+opts.rowId+"_cantidad2_orig' value='"+rData['cantidad2']+"'>";
	imagen +=  datoCantidad2Orig;
	
	var datoCantidad2 = "<input type='hidden' id='"+opts.rowId+"_cantidad2_tmp' value='"+rData['cantidad2']+"'>";
	imagen +=  datoCantidad2;
	
	var datoCantidad3Orig = "<input type='hidden' id='"+opts.rowId+"_cantidad3_orig' value='"+rData['cantidad3']+"'>";
	imagen +=  datoCantidad3Orig;
	
	var datoCantidad3 = "<input type='hidden' id='"+opts.rowId+"_cantidad3_tmp' value='"+rData['cantidad3']+"'>";
	imagen +=  datoCantidad3;
	
	var datoCantidad4Orig = "<input type='hidden' id='"+opts.rowId+"_cantidad4_orig' value='"+rData['cantidad4']+"'>";
	imagen +=  datoCantidad4Orig;
	
	var datoCantidad4 = "<input type='hidden' id='"+opts.rowId+"_cantidad4_tmp' value='"+rData['cantidad4']+"'>";
	imagen +=  datoCantidad4;
	
	var datoCantidad5Orig = "<input type='hidden' id='"+opts.rowId+"_cantidad5_orig' value='"+rData['cantidad5']+"'>";
	imagen +=  datoCantidad5Orig;
	
	var datoCantidad5 = "<input type='hidden' id='"+opts.rowId+"_cantidad5_tmp' value='"+rData['cantidad5']+"'>";
	imagen +=  datoCantidad5;
	
	var modificableIndivVC = "<input type='hidden' id='"+opts.rowId+"_modificableIndivVC' value='"+rData['modificableIndiv']+"'>";
	imagen +=  modificableIndivVC;
	
	//Añadimos también el valor del codError de cada registro.
	var datoError = "<input type='hidden' id='"+opts.rowId+"_codError_origVC' value='"+rData['codError']+"'>";
	imagen +=  datoError;
	
	//Añadimos también el valor del codError de cada registro.
	var datoError = "<input type='hidden' id='"+opts.rowId+"_descError_origVC' value='"+rData['descError']+"'>";
	imagen +=  datoError;
	
	//Añadimos la fecha hasta para controlar la visualización de cantidades por fila
	var datoFechaHasta = "<input type='hidden' id='"+opts.rowId+"_fechaHasta' value='"+rData['fechaHasta']+"'>";
	imagen +=  datoFechaHasta;

	//Añadimos la oferta
	var datoOferta = "<input type='hidden' id='"+opts.rowId+"_oferta' value='"+rData['oferta']+"'>";
	imagen +=  datoOferta;

	//Añadimos los valores de la columna referencia ocultos para poder utilizarlos posteriormente.
	var datoCodArticulo = "<input type='hidden' id='"+opts.rowId+"_codArticuloVC' value='"+rData['codArticulo']+"'>";
	imagen +=  datoCodArticulo;

	//Añadimos los valores para el bloqueo
	var estado = "<input type='hidden' id='"+opts.rowId+"_estadoVC' value='"+rData['estado']+"'>";
	imagen +=  estado;

	//Añadimos los valores para el bloqueo
	var noGestionaPbl = "<input type='hidden' id='"+opts.rowId+"_noGestionaPblVC' value='"+rData['noGestionaPbl']+"'>";
	imagen +=  noGestionaPbl;

	//Añadimos los valores de la columna identificador ocultos para poder utilizarlos posteriormente.
	var datoIdentificadorSIA = "<input type='hidden' id='"+opts.rowId+"_identificadorSIAVC' value='"+rData['identificadorSIA']+"'>";
	imagen +=  datoIdentificadorSIA;
	
	return imagen;
}

function setHeadersTitles(data){
	
   	var colModel = $(gridP46PedidoAdicionalVC.nameJQuery).jqGrid("getGridParam", "colModel");
    $.each(colModel, function(i) {
    	if (colModel[i].name!="rn"){
	    	$("#jqgh_gridP46VC_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
    	}
     });
}

function controlClickP46(e) {
	var idActual = e.target.id;
	$("#"+idActual).focus();
	$("#"+idActual).select();
}

function controlNavegacionP46(e) {
	var idActual = e.target.id;
	var idActualTmp = idActual+"_tmp";
	var campoActualTmpPunto;
	var idFoco;
	//Obtención de fila y columna actuales
	var fila = idActual.substring(0, idActual.indexOf("_"));
	var filaFoco;
	var nombreColumna = idActual.substring(idActual.indexOf("_")+1);
	var numeroColumna = nombreColumna.substr(nombreColumna.length - 1);
	var validacionNavegacion = 'S';
	var key = e.which; //para soporte de todos los navegadores
 	if (key == 13){//Tecla enter, buscar
 		//e.preventDefault();
 		disabledValidation = true;
		finder();

    }

 	//Se recupera el valor temporal para saber si ha cambiado y lanzar la validación
 	/*if (nombreColumna == "cantidad1" || nombreColumna == "cantidad2" || nombreColumna == "cantidad3"){
 			if (!P46EsCampoCantidadVacio(idActualTmp)){
 				campoActualTmpPunto = $("#"+idActualTmp).val().replace(',','.');
 			}else{
 				campoActualTmpPunto = "";
 			}
 	}*/
 	
    //Flechas de cursores para navegación
 	//Se controla el tipo de listado porque los campos a controlar son diferentes
	    if (key == 37){//Tecla izquierda
	    	e.preventDefault();
	    	if(nombreColumna == "cantidad2"){
	    		if(!$("#"+fila + "_cantidad1").is(":disabled")){
		        	idFoco = fila + "_cantidad1";
		        	validacionNavegacion = validacionCantidad(fila + "_cantidad2", '2');
	    		}
	    	}
	    	if(nombreColumna == "cantidad3"){
	    		if(!$("#"+fila + "_cantidad2").is(":disabled")){
		        	idFoco = fila + "_cantidad2";
		        	validacionNavegacion = validacionCantidad(fila + "_cantidad3", '3');
	    		}
	    	}
	    	if(nombreColumna == "cantidad4"){
	    		if(!$("#"+fila + "_cantidad3").is(":disabled")){
		        	idFoco = fila + "_cantidad3";
		        	validacionNavegacion = validacionCantidad(fila + "_cantidad4", '4');
	    		}
	    	}
	    	if(nombreColumna == "cantidad5"){
	    		if(!$("#"+fila + "_cantidad4").is(":disabled")){
		        	idFoco = fila + "_cantidad4";
		        	validacionNavegacion = validacionCantidad(fila + "_cantidad5", '5');
		    	}
	    	}
	    	if (validacionNavegacion=='S'){
	        	$("#"+idFoco).focus();
	        	$("#"+idFoco).select();
	    	}
	    }
	    if (key == 38){//Tecla arriba
	    	e.preventDefault();
	    	var trAnterior = $("#"+idActual).parent().parent().prevAll("tr[editable='1']").eq(0);
	    	idFoco = null;
	        while (trAnterior.length != 0 && null == idFoco){
	        	filaFoco = trAnterior.attr('id');
		    	if(nombreColumna == "cantidad1"){
		    		if (!$("#"+filaFoco + "_cantidad1").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad1";
		        	} else if($("#"+filaFoco + "_cantidad2").is(':visible') && !$("#"+filaFoco + "_cantidad2").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad2";
		    		} else if($("#"+filaFoco + "_cantidad3").is(':visible') && !$("#"+filaFoco + "_cantidad3").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad3";
		    		} else if($("#"+filaFoco + "_cantidad4").is(':visible') && !$("#"+filaFoco + "_cantidad4").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad4";
		    		} else if($("#"+filaFoco + "_cantidad5").is(':visible') && !$("#"+filaFoco + "_cantidad5").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad5";
		    		}
		    	}
		    	if(nombreColumna == "cantidad2"){
		    		if($("#"+filaFoco + "_cantidad2").is(':visible') && !$("#"+filaFoco + "_cantidad2").is(':disabled') ){
		    			idFoco = filaFoco + "_cantidad2";
		    		}else if (!$("#"+filaFoco + "_cantidad1").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad1";
		        	} else if($("#"+filaFoco + "_cantidad3").is(':visible') && !$("#"+filaFoco + "_cantidad3").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad3";
		    		} else if($("#"+filaFoco + "_cantidad4").is(':visible') && !$("#"+filaFoco + "_cantidad4").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad4";
		    		} else if($("#"+filaFoco + "_cantidad5").is(':visible') && !$("#"+filaFoco + "_cantidad5").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad5";
		    		}
		        	
		    	}
		    	if(nombreColumna == "cantidad3"){
		    		if($("#"+filaFoco + "_cantidad3").is(':visible') && !$("#"+filaFoco + "_cantidad3").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad3";
		    		}else if($("#"+filaFoco + "_cantidad2").is(':visible') && !$("#"+filaFoco + "_cantidad2").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad2";
		    		}else if (!$("#"+filaFoco + "_cantidad1").is(':disabled')) {
		    			idFoco = filaFoco + "_cantidad1";
		        	} else if($("#"+filaFoco + "_cantidad4").is(':visible') && !$("#"+filaFoco + "_cantidad4").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad4";
		    		} else if($("#"+filaFoco + "_cantidad5").is(':visible') && !$("#"+filaFoco + "_cantidad5").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad5";
		    		}
		    		
			        	//validacionNavegacion = validacionCantidad(fila + "_cantidad3",  '3');
		    	}
		    	if(nombreColumna == "cantidad4"){
		    		if($("#"+filaFoco + "_cantidad4").is(':visible') && !$("#"+filaFoco + "_cantidad4").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad4";
		    		}else if($("#"+filaFoco + "_cantidad3").is(':visible') && !$("#"+filaFoco + "_cantidad3").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad3";
		    		}else if($("#"+filaFoco + "_cantidad2").is(':visible') && !$("#"+filaFoco + "_cantidad2").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad2";
		    		}else if (!$("#"+filaFoco + "_cantidad1").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad1";
		        	} else if($("#"+filaFoco + "_cantidad5").is(':visible') && !$("#"+filaFoco + "_cantidad5").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad5";
		    		}
			        //validacionNavegacion = validacionCantidad(fila + "_cantidad4",  '4');
			    	
		    	}
		    	if(nombreColumna == "cantidad5"){
		    		if($("#"+filaFoco + "_cantidad5").is(':visible') && !$("#"+filaFoco + "_cantidad5").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad5";
		    		}else if($("#"+filaFoco + "_cantidad4").is(':visible') && !$("#"+filaFoco + "_cantidad4").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad4";
		    		}else if($("#"+filaFoco + "_cantidad3").is(':visible') && !$("#"+filaFoco + "_cantidad3").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad3";
		    		}else if($("#"+filaFoco + "_cantidad2").is(':visible') && !$("#"+filaFoco + "_cantidad2").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad2";
		    		}else if (!$("#"+filaFoco + "_cantidad1").is(':disabled')) {
		    			idFoco = filaFoco + "_cantidad1";
		        	}
		        	//validacionNavegacion = validacionCantidad(fila + "_cantidad5", '5');
		    	}

		    	trAnterior = $("#"+filaFoco+"_"+nombreColumna).parent().parent().prevAll("tr[editable='1']").eq(0);
	        }
	        if (null != idFoco){
	    		validacionNavegacion = validacionCantidad(fila + "_"+nombreColumna,  numeroColumna);
		    	if (validacionNavegacion == "S"){
		    		$("#"+idFoco).focus();
		    		$("#"+idFoco).select();
		    	}
		    	
	    	}
	    }
	    if (key == 39){//Tecla derecha
	    	e.preventDefault();
	    	if(nombreColumna == "cantidad1"){
	    		if($("#"+fila + "_cantidad2").is(':visible') ){
	    			idFoco = fila + "_cantidad2";
	        	}
	        	validacionNavegacion = validacionCantidad(fila + "_cantidad1",  '1');
	    	}
	    	if(nombreColumna == "cantidad2"){
	    		if($("#"+fila + "_cantidad3").is(':visible')){
	    			idFoco = fila + "_cantidad3";
	        	}
	        	validacionNavegacion = validacionCantidad(fila + "_cantidad2", '2');
	    	}
	    	if(nombreColumna == "cantidad3"){
	    		if($("#"+fila + "_cantidad4").is(':visible')){
	    			idFoco = fila + "_cantidad4";
	        	}
	        	validacionNavegacion = validacionCantidad(fila + "_cantidad3", '3');
	    	}
	    	if(nombreColumna == "cantidad4"){
	    		if($("#"+fila + "_cantidad5").is(':visible')){
	    			idFoco = fila + "_cantidad5";
	        	}
	        	validacionNavegacion = validacionCantidad(fila + "_cantidad4", '4');
	    	}
	    	if (validacionNavegacion=='S' && null != idFoco){
	        	$("#"+idFoco).focus();
	        	$("#"+idFoco).select();
	    	}
	    }
	    if (key == 40){//Tecla abajo
	    	e.preventDefault();
	    	var trSiguiente = $("#"+idActual).parent().parent().nextAll("tr[editable='1']").eq(0);
	    	idFoco = null;
	        while (trSiguiente.length != 0 && null == idFoco){
	        	filaFoco = trSiguiente.attr('id');
		    	
		    	if(nombreColumna == "cantidad1"){
		    		if (!$("#"+filaFoco + "_cantidad1").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad1";
		        	} else if($("#"+filaFoco + "_cantidad2").is(':visible') && !$("#"+filaFoco + "_cantidad2").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad2";
		    		} else if($("#"+filaFoco + "_cantidad3").is(':visible') && !$("#"+filaFoco + "_cantidad3").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad3";
		    		} else if($("#"+filaFoco + "_cantidad4").is(':visible') && !$("#"+filaFoco + "_cantidad4").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad4";
		    		} else if($("#"+filaFoco + "_cantidad5").is(':visible') && !$("#"+filaFoco + "_cantidad5").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad5";
		    		}
		        	//validacionNavegacion = validacionCantidad(fila + "_cantidad1", '1');
		    	}
		    	if(nombreColumna == "cantidad2"){
		    		if($("#"+filaFoco + "_cantidad2").is(':visible') && !$("#"+filaFoco + "_cantidad2").is(':disabled') ){
		    			idFoco = filaFoco + "_cantidad2";
		    		}else if (!$("#"+filaFoco + "_cantidad1").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad1";
		        	} else if($("#"+filaFoco + "_cantidad3").is(':visible') && !$("#"+filaFoco + "_cantidad3").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad3";
		    		} else if($("#"+filaFoco + "_cantidad4").is(':visible') && !$("#"+filaFoco + "_cantidad4").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad4";
		    		} else if($("#"+filaFoco + "_cantidad5").is(':visible') && !$("#"+filaFoco + "_cantidad5").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad5";
		    		}
		        	//validacionNavegacion = validacionCantidad(fila + "_cantidad2", '2');
		    	}
		    	if(nombreColumna == "cantidad3"){
		    		if($("#"+filaFoco + "_cantidad3").is(':visible') && !$("#"+filaFoco + "_cantidad3").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad3";
		    		}else if($("#"+filaFoco + "_cantidad2").is(':visible') && !$("#"+filaFoco + "_cantidad2").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad2";
		    		}else if (!$("#"+filaFoco + "_cantidad1").is(':disabled')) {
		    			idFoco = filaFoco + "_cantidad1";
		        	}  else if($("#"+filaFoco + "_cantidad4").is(':visible') && !$("#"+filaFoco + "_cantidad4").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad4";
		    		} else if($("#"+filaFoco + "_cantidad5").is(':visible') && !$("#"+filaFoco + "_cantidad5").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad5";
		    		}
		        	//validacionNavegacion = validacionCantidad(fila + "_cantidad3", '3');
		    	}
		    	if(nombreColumna == "cantidad4"){
		    		if($("#"+filaFoco + "_cantidad4").is(':visible') && !$("#"+filaFoco + "_cantidad4").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad4";
		    		}else if($("#"+filaFoco + "_cantidad3").is(':visible') && !$("#"+filaFoco + "_cantidad3").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad3";
		    		}else if($("#"+filaFoco + "_cantidad2").is(':visible') && !$("#"+filaFoco + "_cantidad2").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad2";
		    		}else if (!$("#"+filaFoco + "_cantidad1").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad1";
		        	} else if($("#"+filaFoco + "_cantidad5").is(':visible') && !$("#"+filaFoco + "_cantidad5").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad5";
		    		}
		        	//validacionNavegacion = validacionCantidad(fila + "_cantidad4", '4');
		    	}
		    	if(nombreColumna == "cantidad5"){
		    		if($("#"+filaFoco + "_cantidad5").is(':visible') && !$("#"+filaFoco + "_cantidad5").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad5";
		    		}else if($("#"+filaFoco + "_cantidad4").is(':visible') && !$("#"+filaFoco + "_cantidad4").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad4";
		    		}else if($("#"+filaFoco + "_cantidad3").is(':visible') && !$("#"+filaFoco + "_cantidad3").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad3";
		    		}else if($("#"+filaFoco + "_cantidad2").is(':visible') && !$("#"+filaFoco + "_cantidad2").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad2";
		    		}else if (!$("#"+filaFoco + "_cantidad1").is(':disabled')){
		    			idFoco = filaFoco + "_cantidad1";
		        	}
		        	//validacionNavegacion = validacionCantidad(fila + "_cantidad5", '5');
		    	}
		    	trSiguiente = $("#"+filaFoco+"_"+nombreColumna).parent().parent().nextAll("tr[editable='1']").eq(0);
	        }
	        if (null != idFoco){
	    		validacionNavegacion = validacionCantidad(fila + "_"+nombreColumna,  numeroColumna);
		    	if (validacionNavegacion == "S"){
		    		$("#"+idFoco).focus();
		    		$("#"+idFoco).select();
		    	}
		    	
	    	}
	
	}
}

function validacionCantidad(id, numCampo){
	if (!disabledValidation){
	var campoActual = $("#"+id).val();
	var campoActualPunto = $("#"+id).val().replace(',','.');
	var fila = id.substring(0, id.indexOf("_"));
	var error = 0;
	var errorFila = 0;
	var descError = '';
	var campoCantidadOrig = id +"_orig";
	var campoCantidadTmp = id + "_tmp";
	var campoDivModificado = fila +"_divModificadoVC";
	var campoDivError = fila +"_divErrorVC";
	var campoImgError = fila+"_imgErrorVC";
	var campoErrorOrig = fila +"_codError_origVC";	
	var campoDescErrorOrig = fila+"_descError_origVC";	
	var resultadoValidacion = 'S';
	var mensajeInvalid = null;
	var cantidadMinima = 0;
	var cantidadMaxima = 9999;
	var cantMax = jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('getCell',fila,'cantMax');
	var cantMin = jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('getCell',fila,'cantMin');
	if (cantMax != "" && cantMax != 0){
		cantidadMaxima = cantMax;
	}
	if (cantMin != "" && cantMin != 0){
		cantidadMinima = cantMin;
	}

		if (campoActual == '')
		{
			campoActualPunto = "0.00";
		}
		$("#"+id).val(campoActualPunto).formatNumber({format:"0.00",locale:"es"});
		if (isNaN(parseFloat(campoActualPunto)) || campoActual.split(",").length > 2 || campoActualPunto < parseFloat(cantidadMinima) || campoActualPunto > parseFloat(cantidadMaxima)){
			if (numCampo=='1'){
				mensajeInvalid = invalidCant1;
			}else if (numCampo=='2') {
				mensajeInvalid = invalidCant2;
			}else if (numCampo == '3'){
				mensajeInvalid = invalidCant3;
			}else if (numCampo == '4'){
				mensajeInvalid = invalidCant4;
			}else{
				mensajeInvalid = invalidCant5;
			}
			descError = msgAlertCantidades(mensajeInvalid, cantidadMinima, cantidadMaxima)
			createAlert(descError, "ERROR");
			
			//Pintamos de rojo el campo.
			$("#"+id).removeClass("editable").addClass("editableError");
			
			error = 1;
			resultadoValidacion = "N";
		}

		var campoCantidadOrigPunto = $("#"+campoCantidadOrig).val().replace(',','.');
		
		if (error != 1 && !validacionFila(fila)){
			errorFila = 1;
			descError = replaceSpecialCharacters(datosObligatoriosFilaP46);
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
			addSeleccionadosP46(fila);
			
			//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
			$("#p46_fld_ValidarCant_Selecc").val(id);
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
			addSeleccionadosP46(fila);
		}
		
		return resultadoValidacion;	
	}
}

function addSeleccionadosP46(fila){
	seleccionadosP46[fila] = fila;
}

function msgAlertCantidades(literal, cantidadMinima, cantidadMaxima){
	literal = literal.replace('{0}',cantidadMinima).replace('{1}',cantidadMaxima);
	return literal;
}

function validacionFila(fila){
	
	var validaLinea = true;
	
		
		if(p46EsCampoCantidadVacio(fila+"_cantidad1")){
			validaLinea = false;
		}

		if ($("#"+fila+"_cantidad2").is(":visible") && p46EsCampoCantidadVacio(fila+"_cantidad2")){
			validaLinea = false;
		}

		if ($("#"+fila+"_cantidad3").is(":visible") && p46EsCampoCantidadVacio(fila+"_cantidad3")){
			validaLinea = false;
		}

	return validaLinea;
}
		
function marcarValidacionFila(fila){
	

			
	if(p46EsCampoCantidadVacio(fila+"_cantidad1")){
				//Pintamos de rojo el campo.
			$("#"+fila+"_cantidad1").removeClass("editable").addClass("editableError");
	}else{
			$("#"+fila+"_cantidad1").removeClass("editableError").addClass("editable");
	}
	
	if ($("#"+fila+"_cantidad2").is(":visible")){
	if (p46EsCampoCantidadVacio(fila+"_cantidad2")){
				//Pintamos de rojo el campo.
			$("#"+fila+"_cantidad2").removeClass("editable").addClass("editableError");
	}else{
			$("#"+fila+"_cantidad2").removeClass("editableError").addClass("editable");
	}
	}
	if ($("#"+fila+"_cantidad3").is(":visible")){
	 if ( p46EsCampoCantidadVacio(fila+"_cantidad3")){
				//Pintamos de rojo el campo.
				$("#"+fila+"_cantidad3").removeClass("editable").addClass("editableError");
			}else{
				$("#"+fila+"_cantidad3").removeClass("editableError").addClass("editable");
			}
	
	}
}

function p46EsCampoCantidadVacio(id){
	var valorCampo = $("#"+id).val();
	var valorCampoPunto = null;
	var valorCampoVacio = $.formatNumber(0,{format:"0.00",locale:"es"});
	if (valorCampo == null || valorCampo == "null" || valorCampo == ''){
		return true
	}else{
		valorCampoPunto = $.formatNumber(valorCampo.replace(',','.'),{format:"0.00",locale:"es"});
		if (isNaN(parseFloat(valorCampoPunto))){
			return true;
		}else{
			return false;
		}
	}
}

function obtenerListadoModificadosP46(){
	
	//A partir del array de seleccionados obtenemos el listado de campos modificados a enviar al controlador.

	for (i = 0; i < seleccionadosP46.length; i++){
		if (seleccionadosP46[i] != null && seleccionadosP46[i] != '')
		{
			//Por cada elemento seleccionado, tenemos que crear el listado de modificados.
			registroListadoModificado = {};
				valorCantidad1 = $("#"+ seleccionadosP46[i] + "_cantidad1").val() ? $("#"+ seleccionadosP46[i] + "_cantidad1").val().replace(',','.') : "";
				valorCantidad2 = $("#"+ seleccionadosP46[i] + "_cantidad2").val() ? $("#"+ seleccionadosP46[i] + "_cantidad2").val().replace(',','.') : "";
				valorCantidad3 = $("#"+ seleccionadosP46[i] + "_cantidad3").val() ? $("#"+ seleccionadosP46[i] + "_cantidad3").val().replace(',','.') : "";
				valorCantidad4 = $("#"+ seleccionadosP46[i] + "_cantidad4").val() ? $("#"+ seleccionadosP46[i] + "_cantidad4").val().replace(',','.') : "";
				valorCantidad5 = $("#"+ seleccionadosP46[i] + "_cantidad5").val() ? $("#"+ seleccionadosP46[i] + "_cantidad5").val().replace(',','.') : "";
				valorNoGestionaPbl = $("#"+ seleccionadosP46[i] + "_noGestionaPblVC").val() ? $("#"+ seleccionadosP46[i] + "_noGestionaPblVC").val().replace(',','.') : "";

				codError =  $("#"+ seleccionadosP46[i] + "_codError_origVC").val();
				descError = $("#"+ seleccionadosP46[i] + "_descError_origVC").val();
				identificador = $("#"+ seleccionadosP46[i] + "_identificadorVC").val();
				identificadorSIA = $("#"+ seleccionadosP46[i] + "_identificadorSIAVC").val();
				
				registroListadoModificado.cantidad1 =  valorCantidad1;
				registroListadoModificado.cantidad2 =  valorCantidad2;
				registroListadoModificado.cantidad3 =  valorCantidad3;
				registroListadoModificado.cantidad4 =  valorCantidad4;
				registroListadoModificado.cantidad5 =  valorCantidad5;
				registroListadoModificado.noGestionaPbl =  valorNoGestionaPbl;
				if (null != identificador && "null" != identificador && "" != identificador){
					registroListadoModificado.identificador =  identificador;
				}
				if (null != identificadorSIA && "null" != identificadorSIA && "" != identificadorSIA){
				registroListadoModificado.identificadorSIA =  identificadorSIA;
				}
				if (null != codError && "null" != codError && "" != codError){
					registroListadoModificado.codError = codError;
				}
				if (null != descError && "null" != descError && "" != descError){
					registroListadoModificado.descError = descError;
				}
				registroListadoModificado.codCentro = $("#centerId").val();
				if (registroListadoModificado.codError != 99){
					listadoModificadosP46.push(registroListadoModificado);
				}
		}	
	}

	//Reseteamos el array de los seleccionados.
	seleccionadosP46 = new Array();
}

function resultadoSaveGrid(error){
	var message = null;
	var level = null;
	if (error == 0){
		message = saveResultOK;
		level = "INFO";
	} else {
		message = saveResultError;
		message = message.replace('{0}',error);
		level = "ERROR";
	}
	createAlert(message, level);
}
function cargarOrdenacionesColumnas(){
	  //Cuando pulse la columna para ordenar por icono tenemos que llamar manualmente al reload para que reordene,
	  //ya que al tener el jsp campos editables no funciona de la manera convencional.
	  $("#gridP46VC_tipoAprovisionamiento").unbind('click');
	  $("#gridP46VC_tipoAprovisionamiento").bind("click", function(e) {
			$('#gridP46VC').setGridParam({sortname:'tipoAprovisionamiento'});
			$('#gridP46VC').setGridParam({page:1});
			reloadDataP46PedidoAdicionalVC('S');
	   }); 
	  $("#gridP46VC_codArticulo").unbind('click');
	  $("#gridP46VC_codArticulo").bind("click", function(e) {
			$('#gridP46VC').setGridParam({sortname:'codArticulo'});
			$('#gridP46VC').setGridParam({page:1});
			reloadDataP46PedidoAdicionalVC('S');
	   }); 
	  $("#gridP46VC_descriptionArt").unbind('click');
	  $("#gridP46VC_descriptionArt").bind("click", function(e) {
			$('#gridP46VC').setGridParam({sortname:'descriptionArt'});
			$('#gridP46VC').setGridParam({page:1});
			reloadDataP46PedidoAdicionalVC('S');
	   }); 
	  $("#gridP46VC_uniCajaServ").unbind('click');
	  $("#gridP46VC_uniCajaServ").bind("click", function(e) {
			$('#gridP46VC').setGridParam({sortname:'uniCajaServ'});
			$('#gridP46VC').setGridParam({page:1});
			reloadDataP46PedidoAdicionalVC('S');
	   }); 
	  $("#gridP46VC_fechaInicio").unbind('click');
	  $("#gridP46VC_fechaInicio").bind("click", function(e) {
			$('#gridP46VC').setGridParam({sortname:'fechaInicio'});
			$('#gridP46VC').setGridParam({page:1});
			reloadDataP46PedidoAdicionalVC('S');
	   }); 
	  $("#gridP46VC_cantidad1").unbind('click');
	  $("#gridP46VC_cantidad1").bind("click", function(e) {
			$('#gridP46VC').setGridParam({sortname:'cantidad1'});
			$('#gridP46VC').setGridParam({page:1});
			reloadDataP46PedidoAdicionalVC('S');
	   }); 
	  $("#gridP46VC_fecha2").unbind('click');
	  $("#gridP46VC_fecha2").bind("click", function(e) {
			$('#gridP46VC').setGridParam({sortname:'fecha2'});
			$('#gridP46VC').setGridParam({page:1});
			reloadDataP46PedidoAdicionalVC('S');
	   }); 
	  $("#gridP46VC_cantidad2").unbind('click');
	  $("#gridP46VC_cantidad2").bind("click", function(e) {
			$('#gridP46VC').setGridParam({sortname:'cantidad2'});
			$('#gridP46VC').setGridParam({page:1});
			reloadDataP46PedidoAdicionalVC('S');
	   }); 
	  $("#gridP46VC_fecha3").unbind('click');
	  $("#gridP46VC_fecha3").bind("click", function(e) {
			$('#gridP46VC').setGridParam({sortname:'fecha3'});
			$('#gridP46VC').setGridParam({page:1});
			reloadDataP46PedidoAdicionalVC('S');
	   }); 
	  $("#gridP46VC_cantidad3").unbind('click');
	  $("#gridP46VC_cantidad3").bind("click", function(e) {
			$('#gridP46VC').setGridParam({sortname:'cantidad3'});
			$('#gridP46VC').setGridParam({page:1});
			reloadDataP46PedidoAdicionalVC('S');
	   }); 
	  $("#gridP46VC_fecha4").unbind('click');
	  $("#gridP46VC_fecha4").bind("click", function(e) {
			$('#gridP46VC').setGridParam({sortname:'fecha4'});
			$('#gridP46VC').setGridParam({page:1});
			reloadDataP46PedidoAdicionalVC('S');
	   }); 
	  $("#gridP46VC_cantidad4").unbind('click');
	  $("#gridP46VC_cantidad4").bind("click", function(e) {
			$('#gridP46VC').setGridParam({sortname:'cantidad4'});
			$('#gridP46VC').setGridParam({page:1});
			reloadDataP46PedidoAdicionalVC('S');
	   });
	  $("#gridP46VC_fecha5").unbind('click');
	  $("#gridP46VC_fecha5").bind("click", function(e) {
			$('#gridP46VC').setGridParam({sortname:'fecha5'});
			$('#gridP46VC').setGridParam({page:1});
			reloadDataP46PedidoAdicionalVC('S');
	   }); 
	  $("#gridP46VC_cantidad5").unbind('click');
	  $("#gridP46VC_cantidad5").bind("click", function(e) {
			$('#gridP46VC').setGridParam({sortname:'cantidad5'});
			$('#gridP46VC').setGridParam({page:1});
			reloadDataP46PedidoAdicionalVC('S');
	   });
	  $("#gridP46VC_fechaFin").unbind('click');
	  $("#gridP46VC_fechaFin").bind("click", function(e) {
			$('#gridP46VC').setGridParam({sortname:'fechaFin'});
			$('#gridP46VC').setGridParam({page:1});
			reloadDataP46PedidoAdicionalVC('S');
	   }); 
	  $("#gridP46VC_cantMax").unbind('click');
	  $("#gridP46VC_cantMax").bind("click", function(e) {
			$('#gridP46VC').setGridParam({sortname:'cantMax'});
			$('#gridP46VC').setGridParam({page:1});
			reloadDataP46PedidoAdicionalVC('S');
	   }); 
	  $("#gridP46VC_cantMin").unbind('click');
	  $("#gridP46VC_cantMin").bind("click", function(e) {
			$('#gridP46VC').setGridParam({sortname:'cantMin'});
			$('#gridP46VC').setGridParam({page:1});
			reloadDataP46PedidoAdicionalVC('S');
	   }); 
	  $("#gridP46VC_descOferta").unbind('click');
	  $("#gridP46VC_descOferta").bind("click", function(e) {
			$('#gridP46VC').setGridParam({sortname:'descOferta'});
			$('#gridP46VC').setGridParam({page:1});
			reloadDataP46PedidoAdicionalVC('S');
	   }); 
	  
	  $("#gridP46VC_mensaje").unbind('click');
	  $("#gridP46VC_mensaje").bind("click", function(e) {
			$('#gridP46VC').setGridParam({sortname:'codError'});
			$('#gridP46VC').setGridParam({page:1});
			reloadDataP46PedidoAdicionalVC('S');
	   }); 
}

function dibujarFechaHasta(fechaHastaDDMMYYYY){
	
	var fechaFormateada = "";
	if (null != fechaHastaDDMMYYYY && "" != fechaHastaDDMMYYYY){
		var diaFecha = parseInt(fechaHastaDDMMYYYY.substring(0,2),10);
		var mesFecha = parseInt(fechaHastaDDMMYYYY.substring(2,4),10);
		var anyoFecha = parseInt(fechaHastaDDMMYYYY.substring(4),10);
		var fechaCompleta = anyoFecha + "-" + mesFecha + "-" + diaFecha;
		
		fechaFormateada = $.datepicker.formatDate("dd-M-yy", devuelveDate(fechaCompleta),{
			dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
			dayNames: $.datepicker.regional[ "es" ].dayNames,
			monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
			monthNames: $.datepicker.regional[ "es" ].monthNames
			});

		var message = mensajeFechaHasta.replace('{0}',fechaFormateada);
		$("#p46_lbl_fechaHasta").html(message);
		$("#p46_fld_fechaHasta").val(fechaHastaDDMMYYYY);
	}else{
		$("#p46_lbl_fechaHasta").html("");
		$("#p46_fld_fechaHasta").val("");
	}
}

function controlModifFechaHasta(fila){
	//Se controla si las cantidades pueden ser modificadas mirando la fecha hasta indicada a través del combo
	//o en su defecto la proporcionada por la fila si existe
	var modificable = true;
	var fechaControl = "";
	var diaFecha = "";
	var mesFecha = "";
	var anyoFecha = "";
	var fechaYYYMMDD = "";
	var fechaHastaDDMMYYYY = $("#p46_fld_fechaHasta").val();
	if (fechaHastaDDMMYYYY == null || fechaHastaDDMMYYYY == "null" || fechaHastaDDMMYYYY==""){
		//Control de fecha por fila
		fechaHastaDDMMYYYY = $("#"+fila+"_fechaHasta").val();
	}
	if(fechaHastaDDMMYYYY != null && fechaHastaDDMMYYYY != "null" && fechaHastaDDMMYYYY!=""){
		diaFecha = parseInt(fechaHastaDDMMYYYY.substring(0,2),10);
		mesFecha = parseInt(fechaHastaDDMMYYYY.substring(2,4),10);
		anyoFecha = parseInt(fechaHastaDDMMYYYY.substring(4),10);
		fechaYYYMMDD = anyoFecha + "-" + mesFecha + "-" + diaFecha;
		fechaControl = devuelveDate(fechaYYYMMDD).getTime();

		var fechaHoy = new Date().getTime();
		var c = 24*60*60*1000;
	    var diffMs = (fechaControl - fechaHoy)/(c);
	    //Si la fecha de hoy es mayor a la fecha de control no se podrán modificar las cantidades
	    if(diffMs<0){
	    	var diffDays = Math.floor(Math.abs(diffMs));
	    	if(diffDays>0){
	    		modificable = false;
	    	}
	    }
	}
    
 	return modificable;
}

function loadPopUpAyudaP46(event){
	
	//Cargamos los parámetros necesarios para la ayuda e invocamos a su carga
	var $focused = $(event.target);
	var id = $focused.attr("id");
	var i = id.indexOf("_");
	var rowId = id.substring(0, i);
	defineCampoFoco($focused);
	var rowData = jQuery(gridP46PedidoAdicionalVC.nameJQuery).getRowData(rowId);
	//var codArt = rowData['codArticulo'];
	var codArt = $("#"+rowId+"_codArticuloVC").val();
	var oferta = ($("#"+rowId+"_oferta").val()=="null"?null:$("#"+rowId+"_oferta").val());
	$.ajax({
		type : 'POST',			
		url : './pedidoAdicionalValidarCant/cargaParametrosAyuda.do?oferta='+oferta+'&codArt='+codArt,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {	
			
			var referenciaUnitaria = data.codArt;
			var tipoOferta = data.tipoOferta;
			var $focused = $(event.target);
			var id = $focused.attr("id");
			var i = id.indexOf("_");
			var rowId = id.substring(0, i);
			defineCampoFoco($focused);
			var rowData = jQuery(gridP46PedidoAdicionalVC.nameJQuery).getRowData(rowId);
			v_Oferta = new VOferta($("#centerId").val(),referenciaUnitaria);
			vReferenciasCentro=new ReferenciasCentro(referenciaUnitaria, $("#centerId").val());
			v_Oferta.tipoOferta = tipoOferta; 
			if (oferta == null || oferta == "0"){
				$("#p56_fld_pestanaAyuda1").text(literalAyuda1SinOferta);		
			}else{
				$("#p56_fld_pestanaAyuda1").text(literalAyuda1ConOferta);
			}
			var ofertaType = $("#"+rowId+"_clasePedidoVC").val();
			load_cmbVentasUltimasOfertasP56(v_Oferta,ofertaType);
			$focused.focus();

		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});	
}

function controlMostradoAyuda(event){
	//Si la fila para la que se va a tratar la ayuda es diferente a la que se está tratando hasta ahora
	//se forzará para generar una nueva ayuda
	var $focused = $(event.target);
	var id = $focused.attr("id");
	var i = id.indexOf("_");
	var rowId = id.substring(0, i);
	
	var llamarAyuda = false;
	if($("#p46_fld_ValidarCant_Selecc").val() == null || $("#p46_fld_ValidarCant_Selecc").val()==''){
		if (filaAyuda == null || filaAyuda!=rowId){
			filaAyuda = rowId;
			llamarAyuda = true;
		}
	}
	return llamarAyuda;
}

function actualizarAvisosValidarCant(){
	
	$.ajax({
		type : 'POST',
		url : './welcome/getUserSession.do',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {				
		
				//el combo de centro se muestra con valor nulo para que el usuario seleccione un centro
				//control de limpiado del valor del centro cuando se pincha sobre el campo
				if (data!=null && data.centro != null){
					 if (data.centro.listaAvisosCentro != null && data.centro.listaAvisosCentro.length > 0){
						 $("#TablonAnuncios").attr("style", "display:inline;");
					 }else{
						 $("#TablonAnuncios").attr("style", "display:none;");
					 }
				}else{
					$("#TablonAnuncios").attr("style", "display:none;");
				}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
       }			
	});	
	
}

function p46ReloadMotivosBloqueo(rowid){
	var tipoPedido = $("#"+(rowid)+"_tipoPedidoVC").val();
	var codTpBloqueo = "";
	//Control de tipo de bloqueo a controlar
	if ("P" == tipoPedido){
		codTpBloqueo = "P";
	}else if ("E" == tipoPedido){
		if ($("#"+(rowid)+"_fechaInPilVC").val() != null && $("#"+(rowid)+"_fechaInPilVC").val()!=''){
			codTpBloqueo = "EP";
		}else{
			codTpBloqueo = "E";
		}
	}
	reloadPopupP74($("#"+(rowid)+"_codArticuloVC").val(), codTpBloqueo, $("#"+(rowid)+"_fechaInicioVC").val(), $("#"+(rowid)+"_fecha2VC").val(), $("#"+(rowid)+"_fecha3VC").val(), $("#"+(rowid)+"_fecha4VC").val(), $("#"+(rowid)+"_fecha5VC").val(), $("#"+(rowid)+"_fechaInPilVC").val(), $("#"+(rowid)+"_fechaFinVC").val(), "N", $("#"+(rowid)+"_clasePedidoVC").val());
	//reloadPopupP74_2($("#"+(rowid)+"_codArticuloVC").val(), codTpBloqueo, $("#"+(rowid)+"_fechaInicioVC").val(), $("#"+(rowid)+"_fecha2VC").val(), $("#"+(rowid)+"_fecha3VC").val(), $("#"+(rowid)+"_fecha4VC").val(), $("#"+(rowid)+"_fecha5VC").val(), $("#"+(rowid)+"_fechaInPilVC").val(), $("#"+(rowid)+"_fechaFinVC").val(), "N", $("#"+(rowid)+"_clasePedidoVC").val());
}

function hideShowColumnsVC(){
	
	codArea = null;
	codSeccion = null;
	if ($("#p40_cmb_seccion").combobox('getValue') != null&& $("#p40_cmb_seccion").combobox('getValue') != '') {
		valorSeccion = $("#p40_cmb_seccion").combobox('getValue').split('*');
		codArea = valorSeccion[0];
		codSeccion = valorSeccion[1];
	}
	
	if(codArea==3){
		//Si es textil, se cambia el nombre de las coumnas cantidad1, cantidad2, cantidad3, cantidad4 por C 1, C 2, C 3, C 4
		jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('setLabel', 'cantidad1', 'C 1');
		jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('setLabel', 'cantidad2', 'C 2');
		jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('setLabel', 'cantidad3', 'C 3');
		jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('setLabel', 'cantidad4', 'C 4');
		jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('setLabel', 'cantidad5', 'C 5');
		//Añadir columna nueva
		jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('showCol',["color","talla","modeloProveedor"]);
		jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('setGridWidth', $("#p46_AreaVC").width(), false);
		//COLOR, TALLA, MODELO, PROVEEDOR
		
	}else{
		
		//Se restablecen los nombre de las cantidades
		jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('setLabel', 'cantidad1', 'Cantid. 1');
		jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('setLabel', 'cantidad2', 'Cantid. 2');
		jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('setLabel', 'cantidad3', 'Cantid. 3');
		jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('setLabel', 'cantidad4', 'Cantid. 4');
		jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('setLabel', 'cantidad5', 'Cantid. 5');
		
		jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('hideCol',["color","talla","modeloProveedor"]);
		jQuery(gridP46PedidoAdicionalVC.nameJQuery).jqGrid('setGridWidth', $("#p46_AreaVC").width(), false);
		
	}
}
