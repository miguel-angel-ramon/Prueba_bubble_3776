var AREA_CONST="area";
var RESET_CONST="reset";
var grid=null;
var gridFacingCero=null;
var gridColNamesFacingCero = null;
var gridTitleFacingCero = null;
var gridEmptyRecords = null;
var gridEmptyRecordsFacingCero = null;
var gridEmptyRecordsEstructuraNoParam = null;
var noReferenceError = null;
var emptyListaModificados = null;
var emptyListaSeleccionados = null;
var centerRequired=null;
var areaRequired=null;
var centerRequided=null;
var tableFilter=null;
var optionNull = "";
var numRow=1;
var iconoGuardado=null;
var iconoModificado=null;
var FacingIncorrecto=null;
var errorActualizacionEstra=null;
var errorActualizacion=null;
var errorActualizacionOrig=null;
var seleccionados = new Array();
var listadoModificados = new Array();
var listadoSeleccionados = new Array();
var optionActiva=null;
var optionActivaTodos=null;
var mensajeAyudaActiva = null;
var mensajeAyudaSoloImagen = null;
var expositor = null;
var expositorTitle = null;
var pedible = null;
var tempColNumOrden = null;
var P111Inicializada = null;
var flgDespliegueSubgrid = true;
var subgridActual = null;
var idFocoSubgrid;
var flgActualizarFacing = "";

$(document).ready(function(){
	$(document).on('CargarScriptsPantallas', function(e) { 
		$(document).on('CargadoCentro', function(e) { 
			if(P111Inicializada == null){
				P111Inicializada = 'S';
				loadP111(locale);
			}
		});
	
		if($("#centerId").val() != null && $("#centerId").val() != "" && P111Inicializada == null){
			P111Inicializada = 'S';
			loadP111(locale);
		}
		
		events_p111_btn_reset();
		events_p111_btn_buscar();
		events_p111_btn_exportExcel();
		events_p111_btn_guardar();
		events_p111_btn_ayudaActiva();
		events_p111_btn_finRevision();
		events_p111_btn_ayudaSoloImagen();

		
	});
	initializeScreenComponentsP111();
});	

function initializeScreenComponentsP111(){
	$("#p111_cmb_area").combobox(null);

	CargadaEstructuraPantalla(2);
}

function initializeScreen(){
	//Nos posicionamos en el campo al que ha navegado el usuario y que hemos almacenado en el hidden.
	$("#p03_btn_aceptar").bind("click", function(e) {
			if ($("#p111_fld_FacingCero_Selecc").val() != '')
			{
				$("#"+$("#p111_fld_FacingCero_Selecc").val()).focus();
				$("#"+$("#p111_fld_FacingCero_Selecc").val()).select();
				e.stopPropagation();
				$("#p111_fld_FacingCero_Selecc").val('');
			}
		 });  
	
	var result=cleanFilterSelection(RESET_CONST);
	controlCentro();
	
	/**
	 *P52083
	 * Comento el filtro para que acepte letras
	 * @author BICUGUAL 
	 */
	//$('#p26_fld_referencia').filter_input({regex:'[0-9]'});
}
	 
function load_cmbArea(){	
	
	var options = "";
	var optionNull = "";
	var vAgruComerParamSfmcap=new VAgruComerParamSfmcap($("#centerId").val(), "I1");
	var objJson = $.toJSON(vAgruComerParamSfmcap);	
	 $.ajax({
		type : 'POST',
		url : './facingCero/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	

			let options = "<option value='null'>&nbsp;</option>";
			
			if ( data ) {
				data.forEach( item => options += "<option value='" + item.grupo1 + "'>" + formatDescripcionCombo(item.grupo1, item.descripcion) + "</option>");
			}

			$("select#p111_cmb_area").html(options);
				
			//Si solo se recibe un item
			if (data && data.length == 1){
				//seleccionar el elemento
				$("#p111_cmb_area").combobox('comboautocomplete', data[0].grupo1);
				$("#p111_cmb_area").combobox('autocomplete', data[0].descripcion);
					
				//invocar al evento de seleccion
				$("#p111_cmb_area").trigger("comboboxselected", { item: {value: data[0].grupo1 } } );
				
				// Si sólo hay un valor en el combo de Área se invoca directamente a la carga del Grid.
				finder();
			}
				
			$("#p111_cmb_area").combobox("enable");

		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});

	$("#p111_cmb_area").combobox({
		selected: function(event, ui) {
        	if ( ui.item.value!="" && ui.item.value!="null" ) {
        		if ($("#p111_cmb_area").val()!=null){
        			var result = cleanFilterSelection(AREA_CONST);
        		}
        	}else{
        		var result= cleanFilterSelection(RESET_CONST);
        	}
          
        },
       
        changed: function(event, ui) { 
        	if (!ui.item || !ui.item.value){
        		return result=cleanFilterSelection(RESET_CONST);
        	}	 
        }
	}); 
	
	$("#p111_cmb_area").combobox('autocomplete',optionNull);
	$("#p111_cmb_area").combobox('comboautocomplete',null);

}

function findValidation(){
	var messageVal=null;
	
	//Estructura comercial
	if ($("#centerId").val()==null || $("#centerId").val()==""){
		messageVal = centerRequided;
	} else if($("#p111_cmb_area").combobox('getValue')=="null" || $("#p111_cmb_area").combobox('getValue')==null){
		messageVal = areaRequired;
   	}
	return messageVal;
}

function findValidationSave(){
	var messageVal=null;
	obtenerListadoModificados();
	
	if (listadoModificados==null || listadoModificados.length == 0){
		messageVal = emptyListaModificados;
	}
	return messageVal;
	
}

function validationFinRevision(){
	var messageVal=null;
	obtenerListadoSeleccionados();
	
	if (listadoSeleccionados==null || listadoSeleccionados.length == 0){
		messageVal = emptyListaSeleccionados;
	}
	return messageVal;
	
}

function events_p111_btn_buscar(){
	$("#p111_btn_buscar").click(function () {
		finder();
	});	
}

function events_p111_btn_reset(){
	$("#p111_btn_reset").click(function () {
		 var result=cleanFilterSelection(RESET_CONST);
		 $("#p111AreaErrores").attr("style", "display:none");
		 $('#gridP111FacingCero').jqGrid('clearGridData');
		 $("#AreaResultados").hide();
	});
}

function events_p111_btn_exportExcel(){
	excel();
}

function events_p111_btn_guardar(){
	$("#p111_btn_save").click(function () {
		guardarDatos();
	});	
}

function events_p111_btn_ayudaActiva(){
	$("#p111_btn_ayudaActiva").click(function () {
   		createAlert(replaceSpecialCharacters(mensajeAyudaActiva), "HELP");
	});
}

function events_p111_btn_finRevision(){
	$("#p111_btn_finRevision").click(function () {
		preguntarSiGuardar();
	});	
}

function events_p111_btn_ayudaSoloImagen(){
	$("#p111_btn_ayudaSoloImagen").click(function () {
   		createAlert(replaceSpecialCharacters(mensajeAyudaSoloImagen), "HELP");
	});
}

function excel(){
	var messageVal=findValidation();
	if (messageVal!=null){
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	} else {
		$("#p111_btn_excel").click(function () {	
			exportExcel();
		});	
	}
}

function finder(){
	var messageVal=findValidation();

	if (messageVal!=null){
		$("#p111AreaErrores").attr("style", "display:none");
		$('#gridP111FacingCero').jqGrid('clearGridData');
		jQuery(gridFacingCero.nameJQuery).jqGrid('hideCol', ["descCodN1","descCodN2","descCodN3","descCodN4","descCodN5","marca"]);
		jQuery(gridFacingCero.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
		
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		$('#gridP111FacingCero').setGridParam({page:1});
//		hideShowColumns(gridFacingCero);
		reloadData('N');
	}
}

function guardarDatos(){
	
	//Inicializamos el número de fila para los campos ocultos que tenemos.
	numRow=1;
	var messageVal=findValidationSave();

	if (messageVal!=null){
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		var recordSfm = new VArtSfm( null, null, null, null, null, null, null, null, null
								   , null, null, null, null, null,null, null, null, null
								   , null, null, null,	null, null, null, null, listadoModificados
								   );
		
		//Reseteamos el array de modificados.
		listadoModificados = new Array();
		existenDatosSinGuardar = false;
		var objJson = $.toJSON(recordSfm.prepareVArtSfmToJsonObject());
		grid = gridFacingCero;
				
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
			url : './facingCero/saveDataGrid.do?page='+grid.getActualPage()+'&max='+grid.getRowNumPerPage()+'&index='+grid.getSortIndex()+'&sortorder='+grid.getSortOrder(),
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {	
					if (data != null && data != ''){
						if (data.estado == 0){
							$("#p111AreaErrores").attr("style", "display:none");
						}else{
							$("#p111AreaErrores").attr("style", "display:block");
						}
							
					}else{
						$("#p111AreaErrores").attr("style", "display:none");
					}
					
					$("#p111_AreaNotaPie").hide();
					$("#p111_Area_mensajes").hide();
					$("#p111_AreaEstados").hide();

					if (data.datos != null && data.datos.records != null && data.datos.records != 0){
						$(gridFacingCero.nameJQuery)[0].addJSONData(data.datos);
						gridFacingCero.actualPage = data.datos.page;
						gridFacingCero.localData = data.datos;
						grid = gridFacingCero;
						$("#p111_AreaNotaPie").show();
					}
					
					/*
					 * P-50987
					 * Si el sumatorio es menor que los huecos muestro mensaje de aviso
					 * @BICUGUAL 
					 */
//					if (huecosFacing!=null && data.sumatorio!=null){
//						if (data.sumatorio < huecosFacing){
//							var mensaje=numHuecosLibres;
//							mensaje = numHuecosLibres.replace('{0}',(huecosFacing - data.sumatorio));
//							createAlert(mensaje, "ERROR");	
//						}
//					}
						
					var ids = jQuery(grid.nameJQuery).jqGrid('getDataIDs'), i, l = ids.length;
					var guardadoConError=false;
					$("#AreaResultados").hide();
				    for (i = 0; i < l; i++) {
				    	if (data.datos.rows[i].tipoGama =='Estrategica'){
							jQuery(grid.nameJQuery).jqGrid('setCell',ids[i],'tipoGama',data.datos.rows[i].tipoGama,{'color':'#ff0000','text-align':'center'});
						}
						if ($("#"+ids[i]+"_expositor").val() == "S"){
							jQuery(gridFac.nameJQuery).jqGrid('setCell',ids[i],'facingCentro',expositor,{'font-weight':'bold','color':'#ff4aff','text-align':'center'},{ title: expositorTitle});
						}else{
					    		
							//El campo Facing será editable
							if (data.datos.rows[i].flgSfmFijo !='B'){
					    		
								jQuery(grid.nameJQuery).jqGrid('editRow', ids[i], false);
								
								$("#"+ids[i]+"_facingCentro").filter_input({regex:'[0-9]'});

					    		$("#"+ids[i]+"_facingCentro").formatNumber({format:"0",locale:"es", round:false});
					    		$("#"+ids[i]+"_facingCero_tmp").formatNumber({format:"0",locale:"es", round:false});
					    			
							    $("#"+ids[i]+"_facingCentro").focusout(function() {
							    	existenDatosSinGuardar = true;
							    	validacionFacing(this.id, 'S');
							    });
					    	
							    //Es solo imagen, el texto se pintara en naranja
							    if (data.datos.rows[i].flgSoloImagen =='S'){ 
					    			$("#"+ids[i]+"_facingCentro").addClass("p26_ReferenciaSoloImagen");
					    			
					    			$("#p111_Area_mensajes").show().css('display', 'inline-block');
					    			$("#p111_AreaSoloImagen").show().css('display', 'inline-block');
					    		}

							// El campo facing será no editable
							}else{

								$("#p111_Area_mensajes").show().css('display', 'inline-block');
//				    			$("#p111_AreaEstados").show().css('display', 'inline-block');

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
						if (data.datos.rows[i].codError!=null){
							guardadoConError=true;
						}	
				    }
				    
				    if (guardadoConError){
				    	createAlert(replaceSpecialCharacters(referenciasFacingCeroGuardadasConError), "ERROR");
				    }else{
				    	createAlert(replaceSpecialCharacters(referenciasFacingCeroGuardadasOk), "INFO");
				    }

				    cargarOrdenacionesColumnas();
				    $("#AreaResultados").show();

					$("#p111_btn_buscar").focus();

					$("#AreaResultados .loading").css("display", "none");	
				},
				error : function (xhr, status, error){
					$("#AreaResultados .loading").css("display", "none");	
					handleError(xhr, status, error, locale);				
		        },
//		        complete:function (){
//		        	var totalRegistrosFac=$(gridFacingCero.nameJQuery).getGridParam("reccount");
//		        	//Si existen registros cargados en la tabla, llamo a gestionar huecos
//		        	if (totalRegistrosFac!=null && totalRegistrosFac>0){
//		        		mostrarHuecos();
//		        	}
//		        }
			});
	}
}

function preguntarSiGuardar(){
	$('#p111_mensajeDialogConfirm').text();
	
	$(function() {
		 $( "#p111dialog-confirm" ).dialog({
			 resizable: false,
			 height:140,
			 modal: true,
			 buttons: {
				 "Si": function() {
					 finalizarRevision();
//					 var msg = $('#p15_mensajeDialogConfirm').text();
//
//						
//					 msg = msg.replace(facingOrig,'{0}');
//					 msg = msg.replace(facingNuevo,'{1}');
					$('#p111_mensajeDialogConfirm').text();
					$(this).dialog( "close" );
//
				 },
				 "No": function() {
					$('#p111_mensajeDialogConfirm').text();
					$(this).dialog( "close" );
				 }
			 }
		 });
	});

}

function finalizarRevision(){
	
	//Inicializamos el número de fila para los campos ocultos que tenemos.
	numRow=1;
	var messageVal=validationFinRevision();

	if (messageVal!=null){
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		var referenciasSeleccionadas = new Array();
		//Reseteamos el array de modificados.
		for(var i = 0; i<listadoSeleccionados.length; i++){
			referenciasSeleccionadas.push(listadoSeleccionados[i]);
		}
		listadoSeleccionados = new Array();
		var objJson = $.toJSON(referenciasSeleccionadas);
		grid = gridFacingCero;
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
			url : './facingCero/finRevisionGrid.do?page='+grid.getActualPage()+'&max='+grid.getRowNumPerPage()+'&index='+grid.getSortIndex()+'&sortorder='+grid.getSortOrder(),
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
					$('#gridP111FacingCero').setGridParam({page:1});
					reloadData('N', 'S');
					$("#AreaResultados .loading").css("display", "none");
				},
				error : function (xhr, status, error){
					$("#AreaResultados .loading").css("display", "none");	
					handleError(xhr, status, error, locale);	
		        }
			});
	}
}

function setFacingCeroHeadersTitles(data){
//   	var colModel = $(gridFacingCero.nameJQuery).jqGrid("getGridParam", "cm");
   	var colModel = $(gridFacingCero.nameJQuery).jqGrid("getGridParam", "colModel");
//   	var colModel = $("#gridP111FacingCero").jqGrid("getGridParam", "colModel");
   	$.each(colModel, function(i) {
    	if (colModel[i].name!="rn"){
	    	$("#jqgh_gridP111FacingCero_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
    	}
     });
}

function loadP111(locale){
	 
	gridFacingCero = new GridP111FacingCero(locale);
	
	var jqxhr = $.getJSON(gridFacingCero.i18nJSON,
			function(data) {
				//console.log( ">> Success load Grid Facing Cero." );
				})
			.success(function(data) {
				gridFacingCero.colNames = data.p111ColNamesFacing;
				gridFacingCero.title = data.p111GridTitleFacing;
				gridColNamesFacing = data.p111ColNamesFacing;
				gridTitleFacing = data.p111GridTitleFacing;
				index = '';
				sortOrder = 'asc';
				gridFacingCero.emptyRecords= data.emptyRecords;
				gridEmptyRecords= data.emptyRecords;
				noReferenceError = data.noReferenceError;
				gridEmptyRecordsFacingCero=data.emptyRecordsRefFacacingCero;
				gridEmptyRecordsEstructuraNoParam = data.emptyRecordsEstructuraNoParam;
				emptyListaModificados = data.emptyListaModificados;
				emptyListaSeleccionados = data.emptyListaSeleccionados;
				referenciasFacingCeroCorregidas = data.referenciasFacingCeroCorregidas;
				referenciasFacingCeroGuardadasConError = data.referenciasFacingCeroGuardadasConError;
				referenciasFacingCeroGuardadasOk = data.referenciasFacingCeroGuardadasOk;
				finalizadaRevision = data.finalizadaRevision;
				mensajeAyudaActiva = data.mensajeAyudaActiva;
				mensajeAyudaSoloImagen = data.mensajeAyudaSoloImagen;
				centerRequired=data.centerRequired;
				areaRequired=data.areaRequired;
				mayorNivelRequired=data.mayorNivelRequired;
				nivelesInsuficientes=data.nivelesInsuficientes;
				nivelesInsuficientesEstructura=data.nivelesInsuficientesEstructura;
				iconoGuardado=data.iconoGuardado;
				iconoModificado=data.iconoModificado;
				CapacidadIncorrecto=data.CapacidadIncorrecto;
				CapacidadMayorPermitida=data.CapacidadMayorPermitida;
				FacingIncorrecto=data.FacingIncorrecto;
				errorActualizacionEstra=data.errorActualizacionEstra;
				errorActualizacion=data.errorActualizacion;
				errorActualizacionOrig=data.errorActualizacionOrig;
				tableFilter= data.tableFilter;
				optionActiva=data.optionActiva;
				optionActivaTodos=data.optionActivaTodos;
				expositor = data.expositor;
				expositorTitle = data.expositorTitle;
				pedible = data.pedible;
				tempColNumOrden = data.tempColNumOrden;

				initializeScreen();
				
				loadP111FacingMock(gridFacingCero);
				setFacingCeroHeadersTitles(data);
				numHuecosLibres= data.msgHuecosLibres;
				huecosSegmento= data.huecosSegmento;
				huecosSubcategoria= data.huecosSubcategoria;
				avisoHuecos= data.avisoHuecos;
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
            });
}

function loadP111FacingMock(grid) {
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
			multiselect : true,
			index: grid.sortIndex,
			sortname: grid.sortIndex,
			sortorder: grid.sortOrder,
			emptyrecords : grid.emptyRecords,
			gridComplete : function() {
				grid.headerHeight("gridP111Header");  
			},
			loadComplete : function(data) {
				grid.actualPage = data.page;
				grid.localData = data;
				grid.sortIndex = null;
				grid.sortOrder = null;
				$("#AreaResultados .loading").css("display", "none");	
				if (grid.firstLoad)
					jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
				// Ocultamos la check de seleccionar todos.
				//$("#cb_" + grid.name).attr("style",	"display:none");
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

function transformRowFilter(recordAlta){
	return recordAlta;
}

function reloadData(recarga, finRevision) {
	
	//Inicializamos el número de fila para los campos ocultos que tenemos.
	numRow=1;
	var messageVal=findValidation();
	
	$("#p111AreaErrores").attr("style", "display:none");
	if (messageVal!=null){
		$('#gridP111FacingCero').jqGrid('clearGridData');
		jQuery(gridFacingCero.nameJQuery).jqGrid('hideCol', ["descCodN1","descCodN2","descCodN3","descCodN4","descCodN5","marca"]);
		jQuery(gridFacingCero.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		var valorArea = null;
		var codArea = null;
		
		if( $("#p111_cmb_area").combobox('getValue')!="null" && $("#p111_cmb_area").combobox('getValue')!=null){
			valorArea = $("#p111_cmb_area").combobox('getValue').split('*');
			codArea = valorArea[0];
		}
		
		if (recarga == "N"){
			listadoModificados = new Array();
			seleccionados = new Array();
		} else {
			obtenerListadoModificados();
		}
	
		var recordFacingCero = new VArtSfm($("#centerId").val(), null, null, null, codArea, null, null, null, null
										  , null, null, null, null, null,null,null, null, null, null, null, null,	null
										  , null, null, null, listadoModificados, null, null, null
										  );
		
		//Reseteamos el array de modificados.
		listadoModificados = new Array();
		
		var objJson = $.toJSON(recordFacingCero.prepareVArtSfmToJsonObject());
	
		//Mirar la selección del combo "Area"
		grid = gridFacingCero;

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
			//url : './misumi/resources/p26SfmCapacidad/mockFacing.json',
			url : './facingCero/loadDataGrid.do?page='+grid.getActualPage()+'&max='+grid.getRowNumPerPage()+'&index='+grid.getSortIndex()+'&sortorder='+grid.getSortOrder()+'&recarga='+recarga,
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				
				$(grid.nameJQuery).jqGrid('clearGridData');
				$("#p111_AreaNotaPie").hide();
				$("#p111_AreaEstados").hide();
//					$("#p111_AreaSoloImagen").hide();
				$("#p111_Area_mensajes").hide();

				grid = gridFacingCero;

				if (data.datos != null && data.datos.records != null && data.datos.records != 0){

					/* P-55928
					 * Oculto la columna pedir (Activa) para grid FAC CAP
					 * @BICUGUAL 
					 */
					jQuery(gridFacingCero.nameJQuery).jqGrid('hideCol', ["capacidad"]);
					jQuery(gridFacingCero.nameJQuery).jqGrid('showCol', ["facingPrevio"]);
								
					jQuery(gridFacingCero.nameJQuery).jqGrid('showCol', ["pedir"]);//<-P-55928
							
					if (recarga == "N"){
						jQuery(gridFacingCero.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
					}
				}
					
				$("#gbox_gridP111FacingCero").show();
				$("#p111_AreaNotaPie").show();
				$("#AreaResultados").show();
				
				$(gridFacingCero.nameJQuery)[0].addJSONData(data.datos);
				gridFacingCero.actualPage = data.datos.page;
				gridFacingCero.localData = data.datos;
					
				var ids = jQuery(grid.nameJQuery).jqGrid('getDataIDs'), i, l = ids.length;
					
				$("#AreaResultados").hide();

				for (i = 0; i < l; i++) {
					if (data.datos.rows[i].tipoGama =='Estrategica'){
						jQuery(grid.nameJQuery).jqGrid('setCell',ids[i],'tipoGama',data.datos.rows[i].tipoGama,{'color':'#ff0000','text-align':'center'});
					}	
					if ($("#"+ids[i]+"_expositor").val() == "S"){
						jQuery(grid.nameJQuery).jqGrid('setCell',ids[i],'facingCentro',expositor,{'font-weight':'bold','color':'#ff4aff','text-align':'center'},{ title: expositorTitle});
					}else{

						if (data.datos.rows[i].flgSfmFijo !='B'){ //el campo Facing sera editable
					    		
							jQuery(grid.nameJQuery).jqGrid('editRow', ids[i], false);
								
							$("#"+ids[i]+"_facingCentro").filter_input({regex:'[0-9]'});

							$("#"+ids[i]+"_facingCentro").formatNumber({format:"0",locale:"es", round:false});
							$("#"+ids[i]+"_facingCero_tmp").formatNumber({format:"0",locale:"es", round:false});
					    		
							if (data.datos.rows[i].flgSoloImagen =='S'){ //Es solo imagen, el texto se pintara en naranja
								$("#"+ids[i]+"_facingCentro").addClass("p26_ReferenciaSoloImagen");
					    			
								$("#p111_Area_mensajes").show().css('display', 'inline-block');
				    			$("#p111_AreaSoloImagen").show().css('display', 'inline-block');
							}
					    			
							$("#"+ids[i]+"_facingCentro").focusout(function() {
								existenDatosSinGuardar = true;
								validacionFacing(this.id, 'S');
							});
					    	
						}else{ //El campo facing sera no editable 
								
							$("#p111_Area_mensajes").show().css('display', 'inline-block');
//							$("#p111_AreaEstados").show().css('display', 'inline-block');
								
							if (data.datos.rows[i].flgSoloImagen =='S'){
								//El valor aparecera en naranja
								jQuery(grid.nameJQuery).jqGrid('setCell',ids[i],'facingCentro',data.datos.rows[i].facingCentro,{'font-weight':'bold','color':'#fd9852','text-align':'center'});
								$("#p111_Area_mensajes").show().css('display', 'inline-block');
//					    			$("#p111_AreaSoloImagen").show().css('display', 'inline-block');		    			
						    			
							}else{
//								El valor aparecera en rosa
								jQuery(grid.nameJQuery).jqGrid('setCell',ids[i],'facingCentro',data.datos.rows[i].facingCentro,{'font-weight':'bold','color':'#ff4aff','text-align':'center'});
							}
						}

					}
							
				}
				
				cargarOrdenacionesColumnas();
				
//				var totalRegistrosFac=$(grid.nameJQuery).getGridParam("reccount");
				
		       	//Si existen registros cargados en la tabla, llamo a gestionar huecos
//				if (totalRegistrosFac!=null && totalRegistrosFac>0){
//					mostrarHuecos();
//				}
				
				$("#AreaResultados").show();
				$("#p111_btn_buscar").focus();

				$("#AreaResultados .loading").css("display", "none");
				
				if (finRevision == "S"){
					createAlert(replaceSpecialCharacters(finalizadaRevision), "INFO");
				}else{
					if (data.datos.records == null || data.datos.records == 0){
						createAlert(replaceSpecialCharacters(referenciasFacingCeroCorregidas), "INFO");
					}
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
function GridP111FacingCero (locale){
	// Atributos
	this.name = "gridP111FacingCero"; 
	this.nameJQuery = "#gridP111FacingCero"; 
	this.i18nJSON = './misumi/resources/p111FacingCero/p111facingCero_' + locale + '.json';
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
		},{
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
		this.pagerName = "pagerP111FacingCero"; 
		this.pagerNameJQuery = "#pagerP111FacingCero";
		this.title = null;
		this.actualPage = null;
		this.localdata = null;
		this.emptyRecords = null;
		this.myColumnStateName = 'gridP111FacingCero.colState';
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
		
		this.getRowValue = function getCellValue(rowId){ 
			return $(this.nameJQuery).getRowData(rowId); 
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
	            window.localStorage.setItem(gridFacingCero.myColumnStateName, JSON.stringify(columnsState));
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

function controlCentro(){
	$( "#centerName" ).bind('focus', function() {
		if ($("#centerName").val()==null || $("#centerName").val()==''){
			cleanFilterSelection(RESET_CONST);
		}
    });
}

function cleanFilterSelection(componentName){
	
	if (componentName == RESET_CONST){
		$("#p111_cmb_area").combobox(null);
		$("select#p111_cmb_area").html("<option value='' selected='selected'></option>");
		$("#p111_cmb_area").combobox('autocomplete',"");
		$("#p111_cmb_area").combobox('comboautocomplete',null);
		
		if ($("#centerId").val()!=null && $("#centerId").val()!=''){
			load_cmbArea();
		}
	}
	
	disableFilterSelection(componentName);
	return true;
}

function disableFilterSelection(componentName){
	if (componentName == RESET_CONST){
		if ($("#centerId").val()!=null && $("#centerId").val()!=''){
			$("#p111_cmb_area").combobox("enable");
		}else{
			$("#p111_cmb_area").combobox("disable");
		}
	}
}

function mine(value, options, rData){ if (value==0){return '';}else{return value;}}

function formatDescripcionCombo(codigo, descripcion){
	return codigo + "-" + descripcion;
}

function limitesFormatNumber(cellValue, opts, rData) {
	
	//Actualizamos el formateo del campo ventaMedia
	return $.formatNumber(cellValue,{format:"0.000",locale:"es"});
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
	var datoFacingOrig = "<input type='hidden' id='"+opts.rowId+"_facingCero_orig' value='"+rData['facingCentro']+"'>";
	imagen +=  datoFacingOrig;
	
	var datoFacing = "<input type='hidden' id='"+opts.rowId+"_facingCero_tmp' value='"+rData['facingCentro']+"'>";
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
	        	$("#p111_fld_facingCentro_Selecc").val(idFoco);
        	}else{
        		$("#p111_btn_buscar").focus();	        	
        	}
    	}
    	
    	if(nombreColumna == "facingCentro"){
    		validacionNavegacion = validacionFacing(fila + "_facingCentro", 'N');
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
		    	    	$("#p111_fld_facingCentro_Selecc").val(idFoco);
	    				
	    			}
	    			
	    			
	    		} else { //origen igual a 'H', partimos de una hija
	    			
	    			
	    			var subgrid  = $(e.target).closest( ".ui-jqgrid-btable" ).attr('id');
	    			var valores = subgrid.split('_');
	    			var indiceLote = valores[1]; //obteniendo el indice del subgrid, sabemos en que lote estamos
	    			
	    			if (fila == 1) { //Esta en la primera hija, debe navegar a la madre/lote

    	    			idFoco = indiceLote + "_" + nombreColumna;
    	    			
    	    			$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_facingCentro'] #"+idFoco).focus();
		    	    	$("#gridP26FacTextil [aria-describedby='gridP26FacTextil_facingCentro'] #"+idFoco).select();
		    	    	$("#p111_fld_facingCentro_Selecc").val(idFoco);

	    				
	    			} else { //No estamos en la primera hija, debemos navegar a la hija superior
	    				
	    				idFocoSubgrid = (parseInt(fila,10)-1) + "_" + nombreColumna;

	    		    	$("#" + subgrid + " #"+idFocoSubgrid).focus();
	    		    	$("#" + subgrid + " #"+idFocoSubgrid).select();
	    				
	    			}
	    			
	    		}

    			
    		} else {
    			
    			$("#"+idFoco).focus();
    	    	$("#"+idFoco).select();
    	    	$("#p111_fld_facingCentro_Selecc").val(idFoco);
    		}

    	}else{
    		$("#p111_btn_buscar").focus();   	
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
	        	$("#p111_fld_facingCentro_Selecc").val(idFoco);
        	}else{
        		$("#p111_btn_buscar").focus();
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
    		
   			$("#"+idFoco).focus();
   	    	$("#"+idFoco).select();
   	    	$("#p111_fld_facingCentro_Selecc").val(idFoco);

    	}else{
    		$("#p111_btn_buscar").focus();
    	}
    }
}


function controlNavegacionSubgrid(e) {
	controlNavegacion(e,'H');
}

function controlNavegacionFacingLote(e) {
	controlNavegacion(e,'L');
}

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
	}else{ //Teclado
		if (key == 38){//Tecla arriba
			fila--;
			idActual = fila+"_facingCentro";
			if ($("#"+idActual).length){
				valueActual = $("#"+idActual).val();
				//Seleccionamos el texto para editar
				//$("#"+idActual).select();
			}
		}else if (key == 40){//Tecla abajo
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
	var campoActual = $("#gridP111FacingCero [aria-describedby='gridP111FacingCero_facingCentro'] #"+id).val();

	var campoActualPunto = $("#"+id).val();
	var fila = id.substring(0, id.indexOf("_"));
	var error = 0;
	var descError = '';
	var campoFacingOrig = fila + "_facingCero_orig";
	var campoFacingTmp = fila + "_facingCero_tmp";
	var valorCampoFacingTmp = $("#"+campoFacingTmp).val();
	var campoDivModificado = fila + "_fac_divModificado";
	var campoDivGuardado = fila + "_fac_divGuardado";
	var campoDivError = fila + "_fac_divError";
	var campoImgError = fila + "_fac_imgError";
	var campoErrorOrig = fila + "_fac_codError_orig";	
	var campoFlgEstrategica = fila + "_flgEstrategica";
	var resultadoValidacion = 'S';
	
	if (mostrarAlerta == 'S'){
		
		if (campoActual == ''){
			campoActualPunto = "0";
		}
		
		if (isNaN(parseInt(campoActualPunto))){
			descError = replaceSpecialCharacters(FacingIncorrecto);
			createAlert(descError, "ERROR");
			
			//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
			$("#p111_fld_facingCentro_Selecc").val(fila + "_facingCentro");
			
			//Pintamos de rojo el campo.
			$("#"+id).removeClass("editable").addClass("editableError");
			
			error = 1;
		}else if ( ($("#"+campoFlgEstrategica).val() == "S") && (campoActualPunto == 0) ) {
			descError = replaceSpecialCharacters(errorActualizacionEstra);
			createAlert(descError, "ERROR");
			
			//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente
			$("#p111_fld_facingCentro_Selecc").val(fila + "_facingCentro");
			
			//Pintamos de rojo el campo.
			$("#"+id).removeClass("editable").addClass("editableError");
			
			error = 1;
		}
		
		var campoFacingOrigPunto = $("#"+campoFacingOrig).val();
		
		if (error == 1){

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
			
		}else{
			
			//No hay error con lo que quitamos el posible icono de error.
			$("#"+campoDivError).hide();
			$("#"+id).removeClass("editableError").addClass("editable");
			
			//Además se establece si se ha modificado el campo
			if (($("#gridP111FacingCero [aria-describedby='gridP111FacingCero_mensaje'] #"+campoFacingTmp).val() != null) && ($("#gridP111FacingCero [aria-describedby='gridP111FacingCero_mensaje'] #"+campoFacingTmp).val() != campoActual)){
				//En este caso se ha modificado el campo y hay que establecer el icono de modificaci?n.
				$("#gridP111FacingCero [aria-describedby='gridP111FacingCero_mensaje'] #"+campoDivError).hide();
				$("#gridP111FacingCero [aria-describedby='gridP111FacingCero_mensaje'] #"+campoDivGuardado).hide();
				$("#gridP111FacingCero [aria-describedby='gridP111FacingCero_mensaje'] #"+campoDivModificado).show();
				$("#gridP111FacingCero [aria-describedby='gridP111FacingCero_mensaje'] #"+campoFacingTmp).val(campoActual);
				$("#gridP111FacingCero [aria-describedby='gridP111FacingCero_mensaje'] #"+campoErrorOrig).val('9');
					
				//En el caso de ser una consulta de Facing-capacidad
				//Al modificar el facing hay que calcular la capacidad.
				if ($("#p111_txt_flgFacing").val()=='S' && $("#p111_txt_flgFacingCapacidad").val()=='S'){
					recalcularCapacidad(id,campoActual);
				}

				//Añadimos la fila al array.
				addSeleccionados(fila);

			}
		}
		
	}else{
		
		if (campoActual == ''){ //En textil puede ser vacio.
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
	
	var recordSfm = new VArtSfm( $("#centerId").val(), codId, null, null, null, null, null, null, null
							   , null, null, null, null, null,null, null, null, null, null, null, null
							   , null, null, null, null, null, null, facingCentro
							   );
	
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
				 jQuery($('#gridP111FacingCentro')).jqGrid('setCell',fila,'capacidad',data,{},{ title: data});
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
			$("#p111_fld_facingCentro_Selecc").val(fila + "_capacidad");
			
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
			$("#p111_fld_facingCentro_Selecc").val(fila + "_coberturaSfm");
			
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
		
				if (parseFloat($("#"+campoVentaMediaActual).val()) == 0){
					$("#"+campoSfmActual).val($("#"+id).val());
					$("#"+campoSfmTmp).val($("#"+id).val());
				}else{
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

function obtenerListadoSeleccionados() {

	listadoSeleccionados = new Array();
	
	let gridFilasMarcadas = $("#gridP111FacingCero").getGridParam('selarrrow');
	
	// copia del array de los IDS seleccionados.
	let idsFilasMarcadas = $.makeArray(gridFilasMarcadas);
	let numeroElementos = gridFilasMarcadas.length;
	for (i = 0; i < numeroElementos; i++){
		var rowData = $("#gridP111FacingCero").getRowData(idsFilasMarcadas[i]);

		var codArticulo = rowData['codArticulo'];
		listadoSeleccionados.push(codArticulo);
	}
}

function getRowDataByRowId(id){
	return jQuery(grid.nameJQuery).getRowValue(id);
}

function obtenerListadoModificados(){
	
	//A partir del array de "seleccionados" obtenemos el listado de campos modificados a enviar al controlador.
	var registroListadoModificado = {};
	var valorCapacidad = "";
	var valorSfm = "";
	var valorCoberturaSfm = "";
	var valorCodError = "";
	for (i = 0; i < seleccionados.length; i++){
		
		if (seleccionados[i] != null && seleccionados[i] != ''){
			
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
			
			valorFacingCero = $("#"+ seleccionados[i] + "_facingCentro").val() ? $("#"+ seleccionados[i] + "_facingCentro").val().replace(',','.') : "";

			registroListadoModificado.indice =  $("#"+ seleccionados[i] + "_fac_indice").val();
			registroListadoModificado.codError = $("#"+ seleccionados[i] + "_fac_codError_orig").val() ? $("#"+ seleccionados[i] + "_fac_codError_orig").val() : "";
			
			registroListadoModificado.sfm =  valorSfm;
			registroListadoModificado.coberturaSfm = valorCoberturaSfm;
			registroListadoModificado.capacidad = valorCapacidad;
			registroListadoModificado.facingCentro = valorFacingCero;

			listadoModificados.push(registroListadoModificado);
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
	if( $("#p111_cmb_area").combobox('getValue')!="null" && $("#p111_cmb_area").combobox('getValue')!=null){
		valorArea = $("#p111_cmb_area").combobox('getValue').split('*');
		codArea = valorArea[0];
	}
	
	//Si hay centro y area de electro seleccionadas consulto para ver si tiene configurados los huecos 
	if (centerName!=null && centerName!='' && codArea!=null && (codArea=="5")){
		
//		if( $("#p26_cmb_seccion").combobox('getValue')!="null" && $("#p26_cmb_seccion").combobox('getValue')!=null){
//			valorSeccion = $("#p26_cmb_seccion").combobox('getValue').split('*');
//			codSeccion = valorSeccion[0];
//		}
//		if( $("#p26_cmb_categoria").combobox('getValue')!="null" && $("#p26_cmb_categoria").combobox('getValue')!=null){
//			valorCategoria = $("#p26_cmb_categoria").combobox('getValue').split('*');
//			codCategoria = valorCategoria[0];
//		}
//		if( $("#p26_cmb_subcategoria").combobox('getValue')!="null" && $("#p26_cmb_subcategoria").combobox('getValue')!=null){
//			valorSubcategoria = $("#p26_cmb_subcategoria").combobox('getValue').split('*');
//			codSubcategoria = valorSubcategoria[0];
//		}
//		if( $("#p26_cmb_segmento").combobox('getValue')!="null" && $("#p26_cmb_segmento").combobox('getValue')!=null){
//			valorSegmento = $("#p26_cmb_segmento").combobox('getValue').split('*');
//			codSegmento = valorSegmento[0];
//		}
		
//		var recordSfm = new VArtSfm( null, null, null, null, codArea, null, codSeccion, null, codCategoria
//								   , null, codSubcategoria, null, codSegmento, null, null
//								   , null, null, null, null, null, null, null, null, null, null, null, null
//								   );
//		
//		var objJson = $.toJSON(recordSfm.prepareVArtSfmToJsonObject());
//
//		$.ajax({
//			type : 'POST',
//			data : objJson,
//			url : './sfmCapacidad/getHuecos.do?',
//			contentType : "application/json; charset=utf-8",
//			dataType : "json",
//			cache : false,
//			success : function(data) {
//				if (data != null) {
//					mostrarNumHuecos(data);
//				}else{
//					ocultarHuecos();
//				}
//
//			},
//			error : function(xhr, status, error) {
//				handleError(xhr, status, error, locale);
//			}
//		});	
		
	}else{
		ocultarHuecos();
	}
	
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
	
	$("#p111_Area_mensajes").show().css('display', 'inline-block');
	$("#p111_mensajeHuecos").show().css('display', 'inline-block');
	
}

function ocultarHuecos(){
	$("#p111_mensajeHuecos").hide();
};


function funcionTest(rData) {
	var fechaSfm = devuelveDate(rData['fechaSfmDDMMYYYY'].substring(4) +"-"+ rData['fechaSfmDDMMYYYY'].substring(2,4)  +"-"+ rData['fechaSfmDDMMYYYY'].substring(0,2)).getTime();
	
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
	
	/* Inicio modificacion P-55928 */	
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
		grid = gridFacingCero;
	}
	
	var colModel = $(grid.nameJQuery).jqGrid("getGridParam", "colModel");      
	var myColumns = new Array();
	var myColumnsWidth = new Array();
	 
	var j=0;		  
	$.each(colModel, function(i) {
		  if (colModel[i].name!="rn" && colModel[i].name!== 'cb' && colModel[i].name!== 'subgrid' && !colModel[i].hidden ){
			    myColumns[j]=colModel[i].name;
			    myColumnsWidth[j]=colModel[i].width;
			    j++;		    	
		  }
	 });

	var form = "<form name='csvexportform' action='facingCero/exportGrid.do' accept-charset='ISO-8859-1' method='get'>";	
	form = form + "<input type='hidden' name='model' value='"+myColumns+"'>";
	form = form + "<input type='hidden' name='widths' value='"+myColumnsWidth+"'>";
	form = form + "<input type='hidden' name='centro' value='"+$("#centerId").val()+"'>";
	
	if ($("#p111_cmb_area").combobox('getValue')!="" && $("#p111_cmb_area").combobox('getValue')!=null  ){
		let area = $("#p111_cmb_area").combobox('getValue').split('*');
		form = form + "<input type='hidden' name='grupo1' value='"+area[0]+"'>";
		
		let selectedText = $("#p111_cmb_area option:selected").text().split('-');
		form = form + "<input type='hidden' name='descArea' value='"+selectedText[1]+"'>";
	}	
	
	form = form + "</form><script>document.csvexportform.submit();</script>";
	Show_Popup(form);	
}	

