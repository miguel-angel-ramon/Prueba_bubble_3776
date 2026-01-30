var AREA_CONST="area";
var RESET_CONST="reset";
var SECTION_CONST="section";
var CATEGORY_CONST="category";
var SUBCATEGORY_CONST="subcategory"; 
var SEGMENT_CONST="segment";
var grid=null;
var centerRequired=null;
var camposRequired=null;
var camposRequiredFacing0=null;
var tableFilter=null;
var allOptions=null;
var emptyRecords=null;
var P110Inicializada = null;
var colReset=true;

var optionNull = "";
$(document).ready(function(){
	$(document).on('CargarScriptsPantallas', function(e) { 
		$(document).on('CargadoCentro', function(e) { 
			if(P110Inicializada == null){
				P110Inicializada = 'S';
				loadP110(locale);
			}
		});
	
		if($("#centerId").val() != null && $("#centerId").val() != "" && P110Inicializada == null){
			P110Inicializada = 'S';
			loadP110(locale);
		}
		
		events_p110_btn_reset();
		events_p110_btn_buscar();
		events_p110_btn_exportExcel();
	});
	initializeScreenComponents();
});

function initializeScreenComponents(){
	$("#p110_cmb_area").combobox(null);
	$("#p110_cmb_seccion").combobox(null);
	$("#p110_cmb_subcategoria").combobox(null);
	$("#p110_cmb_categoria").combobox(null);
	$("#p110_cmb_segmento").combobox(null);

	$("#p110_cmb_mmc").combobox(null);
	$("#p110_cmb_catalogo").combobox(null);
	$("#p110_cmb_tipoAprov").combobox(null);
	
	CargadaEstructuraPantalla(2);
}

function initializeScreen(){
	$("#p110_cmb_mmc").combobox(null);
	$("#p110_cmb_catalogo").combobox(null);
	$("#p110_cmb_tipoAprov").combobox(null);
	if ($("#centerId").val()!=null && $("#centerId").val()!=''){
		$("#p110_cmb_mmc").combobox('autocomplete',allOptions);
		$("#p110_cmb_catalogo").combobox('autocomplete',allOptions);
		$("#p110_cmb_tipoAprov").combobox('autocomplete',allOptions);
	}else{
		$("#p110_cmb_mmc").combobox('autocomplete',"");
		$("#p110_cmb_catalogo").combobox('autocomplete',"");
		$("#p110_cmb_tipoAprov").combobox('autocomplete',"");
	}
	$("#p110_cmb_mmc").combobox('comboautocomplete',"null");
	$("#p110_cmb_catalogo").combobox('comboautocomplete',"null");
	$("#p110_cmb_tipoAprov").combobox('comboautocomplete',"null");
	
	var result=cleanFilterSelection(RESET_CONST);
	controlCentro();
	
}

function load_cmbArea(){	
	var options = "";
	var optionNull = "";
	//$( "#p110_cmb_area" ).prepend("<option value=null selected='selected'>"+optionNull+"</option>");
	var vAgruComerRef=new VAgruComerRef("I1");
	var objJson = $.toJSON(vAgruComerRef);	
	 $.ajax({
		type : 'POST',
		url : './facingVegalsa/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {
		   for (i = 0; i < data.length; i++){
				options = options + "<option value='" + data[i].grupo1 + "'>" + formatDescripcionCombo(data[i].grupo1, data[i].descripcion) + "</option>"; 
		   }
		   $("select#p110_cmb_area").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
		$("#p110_cmb_area").combobox({
	        selected: function(event, ui) {
	        	if ( ui.item.value!="" && ui.item.value!="null" ) {
		           if ($("#p110_cmb_area").val()!=null){
//		        	   $("#p110_cmb_area").combobox('autocomplete',ui.item.label);
//		        		$("#p110_cmb_area").combobox('comboautocomplete',ui.item.value);
		        	   
		        	   load_cmbSeccion();
		        	   var result = cleanFilterSelection(AREA_CONST);
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
	
	$("#p110_cmb_area").combobox('autocomplete',optionNull);
	$("#p110_cmb_area").combobox('comboautocomplete',null);

}

function load_cmbSeccion(){	
	var options = "";
	var optionNull = "";
	$("#p110_cmb_seccion").combobox(null);
	//$( "#p110_cmb_seccion" ).prepend("<option value='null' selected='selected'>"+optionNull+"</option>");
	var vAgruComerRef=new VAgruComerRef("I2",$("#p110_cmb_area").val());
	var objJson = $.toJSON(vAgruComerRef);	
	 $.ajax({
		type : 'POST',
		url : './facingVegalsa/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			   for (i = 0; i < data.length; i++){
					options = options + "<option value='" + data[i].grupo2 + "'>" + formatDescripcionCombo(data[i].grupo2, data[i].descripcion) + "</option>"; 
			   }
			   $("select#p110_cmb_seccion").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
		$("#p110_cmb_seccion").combobox({
	        selected: function(event, ui) {
	        	
	         if ( ui.item.value!="" && ui.item.value!="null") {
		           if ($("#p110_cmb_seccion").val()!=null){
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
	
	$("#p110_cmb_seccion").combobox('autocomplete',optionNull);
	$("#p110_cmb_seccion").combobox('comboautocomplete',null);
}

function load_cmbCategory(){	
	var options = "";
	var optionNull = "";
	$("#p110_cmb_categoria").combobox(null);
		var vAgruComerRef=new VAgruComerRef("I3",$("#p110_cmb_area").val(),$("#p110_cmb_seccion").val());
		var objJson = $.toJSON(vAgruComerRef);	
	 $.ajax({
		type : 'POST',
		url : './facingVegalsa/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			   for (i = 0; i < data.length; i++){
					options = options + "<option value='" + data[i].grupo3 + "'>" + formatDescripcionCombo(data[i].grupo3, data[i].descripcion) + "</option>"; 
			   }
			   $("select#p110_cmb_categoria").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
		$("#p110_cmb_categoria").combobox({
	        selected: function(event, ui) {
	        	 if ( ui.item.value!="" && ui.item.value!="null") {
		           if ($("#p110_cmb_categoria").val()!=null){
		        	
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
		
	
	$("#p110_cmb_categoria").combobox('autocomplete',optionNull);
	$("#p110_cmb_categoria").combobox('comboautocomplete',null);
	
	
}
function load_cmbSubCategory(){	
	var options = "";
	var optionNull = "";
	$("#p110_cmb_subcategoria").combobox(null);
	var vAgruComerRef=new VAgruComerRef("I4",$("#p110_cmb_area").val(),$("#p110_cmb_seccion").val(),$("#p110_cmb_categoria").val());
	var objJson = $.toJSON(vAgruComerRef);	
	 $.ajax({
		type : 'POST',
		url : './facingVegalsa/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			   for (i = 0; i < data.length; i++){
					options = options + "<option value='" + data[i].grupo4 + "'>" + formatDescripcionCombo(data[i].grupo4, data[i].descripcion) + "</option>"; 
			   }
			   $("select#p110_cmb_subcategoria").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
		$("#p110_cmb_subcategoria").combobox({
	        selected: function(event, ui) {
	          if ( ui.item.value!="" && ui.item.value!="null") {
		           if ($("#p110_cmb_subcategoria").val()!=null){
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
	
	$("#p110_cmb_subcategoria").combobox('autocomplete',optionNull);
	$("#p110_cmb_subcategoria").combobox('comboautocomplete',null);
	
	
}
function load_cmbSegment(){	
	var options = "";
	var optionNull = "";
	$("#p110_cmb_segmento").combobox(null);
	var vAgruComerRef=new VAgruComerRef("I5",$("#p110_cmb_area").val(),$("#p110_cmb_seccion").val(),$("#p110_cmb_categoria").val(),$("#p110_cmb_subcategoria").val());
	var objJson = $.toJSON(vAgruComerRef);	
	 $.ajax({
		type : 'POST',
		url : './facingVegalsa/loadAreaData.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			   for (i = 0; i < data.length; i++){
					options = options + "<option value='" + data[i].grupo5 + "'>" + formatDescripcionCombo(data[i].grupo5, data[i].descripcion) + "</option>"; 
			   }
			   $("select#p110_cmb_segmento").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
		$("#p110_cmb_segmento").combobox({
	        selected: function(event, ui) {
	          if ( ui.item.value!="" && ui.item.value!="null") {
		           if ($("#p110_cmb_segmento").val()!=null){
		        	   $("#p110_cmb_segmento").combobox('comboautocomplete',ui.item.value);
		        	   $("#p110_cmb_segmento").combobox('autocomplete',ui.item.label);
		        	   var result=cleanFilterSelection(SEGMENT_CONST);
		        	   
		           } 
	          }   else{
	        	  var result= cleanFilterSelection(SUBCATEGORY_CONST);
	        	} 
	          
	        }  ,		       
	        changed: function(event, ui) { 
				   if (ui.item==null || ui.item.value!="" || ui.item.value!="null"){
					   if (cleanFilterSelection(SUBCATEGORY_CONST)){ 
						   load_cmbSegment();
					   }   
				   }	 
			   }
	          
	     });
	
	$("#p110_cmb_segmento").combobox('autocomplete',optionNull);
	$("#p110_cmb_segmento").combobox('comboautocomplete',null);
}

function events_p110_btn_reset(){
	$("#p110_btn_reset").click(function () {
		colReset=true;
		 var result=cleanFilterSelection(RESET_CONST);
		 
		$("#p110_cmb_mmc").combobox('autocomplete',allOptions);
		$("#p110_cmb_mmc").combobox('comboautocomplete',"null");
  
   		$("#p110_cmb_catalogo").combobox("enable");
   		$("#p110_cmb_catalogo").combobox('autocomplete',allOptions);
	 	$("#p110_cmb_catalogo").combobox('comboautocomplete',"null");

		$("#p110_cmb_tipoAprov").combobox('autocomplete',allOptions);
 		$("#p110_cmb_tipoAprov").combobox('comboautocomplete',"null");
 		
 		$("#p110_chk_facing0").prop('checked',false);
 		
 		$("#p110_chk_fondoAlimentado").prop('checked',false);
 		$("#p110_chk_conStock").prop('checked',false);
 		$("#p110_chk_gama").prop('checked',false);
 		
 		$('#p110_chk_fondoAlimentado').prop('disabled',true);
 		$('#p110_chk_conStock').prop('disabled',true);
 		$('#p110_chk_gama').prop('disabled',true);
 	
		$('#gridP110').jqGrid('clearGridData');
	   	jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
	});
}

function findValidation(){
	var messageVal=null;
	if ($("#centerId").val()==null || $("#centerId").val()==""){
		messageVal = centerRequired;	 
	} else{
		var facing0 = $("#p110_chk_facing0").is(":checked");
		if(facing0){
			var area = $("#p110_cmb_area").combobox('getValue');
			if(area==null){
				messageVal = camposRequiredFacing0;
			}
		}else{
			var seccion = $("#p110_cmb_seccion").combobox('getValue');
			if(seccion==null){
				messageVal = camposRequired;
			}
		}
	}
	return messageVal;
	
}
function events_p110_btn_buscar(){
	$("#p110_btn_buscar").click(function () {
		//flag para resetear as columnas. True - Resetea. False - No resetea
		colReset=false;
		finder();
	});	
}

function hideShowColumns(){
	var centro = $("#centerId").val();
	var area = $("#p110_cmb_area").combobox('getValue');
	var seccion = $("#p110_cmb_seccion").combobox('getValue');
	var categoria = $("#p110_cmb_categoria").combobox('getValue');
	var subcategoria = $("#p110_cmb_subcategoria").combobox('getValue');
	var segmento = $("#p110_cmb_segmento").combobox('getValue');
	var mmc = $("#p110_cmb_mmc").combobox('getValue');
	var catalogo = $("#p110_cmb_catalogo").combobox('getValue');
	var tipoAprov = $("#p110_cmb_tipoAprov").combobox('getValue');
	var facing0 = $("#p110_chk_facing0").is(":checked");
	
	if (area!=null){
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["area"]);
	}else{
		jQuery(grid.nameJQuery).jqGrid('showCol', ["area"]);
	}
	if (seccion!=null){
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["seccion"]);
	}else{
		jQuery(grid.nameJQuery).jqGrid('showCol', ["seccion"]);
	}
	if (categoria!=null){
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["categoria"]);
	}else{
		jQuery(grid.nameJQuery).jqGrid('showCol', ["categoria"]);
	}
	if (subcategoria!=null){
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["subcategoria"]);
	}else{
		jQuery(grid.nameJQuery).jqGrid('showCol', ["subcategoria"]);
	}
	if (segmento!=null){
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["segmento"]);
	}else{
		jQuery(grid.nameJQuery).jqGrid('showCol', ["segmento"]);
	}
	if (mmc!="null"){
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["mmc"]);
	}else{
		jQuery(grid.nameJQuery).jqGrid('showCol', ["mmc"]);
	}
	if (catalogo!="null"){
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["catalogo"]);
	}else{
		jQuery(grid.nameJQuery).jqGrid('showCol', ["catalogo"]);
	}
	if (tipoAprov!="null"){
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["tipoAprov"]);
	}else{
		jQuery(grid.nameJQuery).jqGrid('showCol', ["tipoAprov"]);
	}
	if (facing0){
		jQuery(grid.nameJQuery).jqGrid('hideCol', ["facing"]);
	}else{
		jQuery(grid.nameJQuery).jqGrid('showCol', ["facing"]);
	}
	
}

function finder(){
	var messageVal=findValidation();
	hideShowColumns();
	
	if (messageVal!=null){
		$('#gridP110').jqGrid('clearGridData');
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		$('#p110_btn_buscar').prop("disabled",true); // Desactivar el botón <Buscar> hasta que finalice la operación.
		$('#gridP110').setGridParam({page:1});
		reloadData('N');
	}
}

function events_p110_btn_exportExcel(){
	$("#p110_btn_exportExcel").click(function () {	
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
	    	$("#jqgh_gridP110_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
    	}
     });
}

function loadP110(locale){
	grid = new GridP110(locale);
	
	var jqxhr = $.getJSON(grid.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				grid.colNames = data.p110ColNames;
				index = '';
				sortOrder = 'asc';
				grid.title = data.p110GridTitle;
				grid.emptyRecords= data.emptyRecords;
				centerRequired=data.centerRequired;
				camposRequired=data.camposRequired;
				camposRequiredFacing0=data.camposRequiredFacing0;
				tableFilter= data.tableFilter;
				allOptions=data.allOptions;
				emptyRecords=data.emptyRecords;
				initializeScreen();
				initGridP110(grid);
				setHeadersTitles(data);
				
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
            });
	
}

function prepareObjectJSON(){
	var centro = $("#centerId").val();
	var area = $("#p110_cmb_area").combobox('getValue');
	var seccion = $("#p110_cmb_seccion").combobox('getValue');
	var categoria = $("#p110_cmb_categoria").combobox('getValue');
	var subcategoria = $("#p110_cmb_subcategoria").combobox('getValue');
	var segmento = $("#p110_cmb_segmento").combobox('getValue');
	var mmc = $("#p110_cmb_mmc").combobox('getValue');
	var catalogo = $("#p110_cmb_catalogo").combobox('getValue');
	var tipoAprov = $("#p110_cmb_tipoAprov").combobox('getValue');
	var facing0 = $("#p110_chk_facing0").is(":checked");
	var alimentado = $("#p110_chk_fondoAlimentado").is(":checked");
	var conStock = $("#p110_chk_conStock").is(":checked");
	var gama = $("#p110_chk_gama").is(":checked");
	
	var buscador = {
		"centro" : centro,
		"area" : area,
		"seccion" : seccion,
		"categoria" : categoria,
		"subcategoria" : subcategoria,
		"segmento" : segmento,
		"mmc" : mmc!="null"?mmc:null,
		"catalogo" : catalogo!="null"?catalogo:null,
		"tipoAprov" : tipoAprov!="null"?tipoAprov:null,
		"facing0" : facing0,
		"alimentado" : alimentado,
		"conStock" : conStock,
		"gama" : gama
	};
	return $.toJSON(buscador);
}

function reloadData(recarga) {
	var messageVal=findValidation();
		
	if (grid.firstLoad) {
		$('#gridP110').jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
		grid.firstLoad = false;
        if (grid.isColState) {
            $(this).jqGrid("remapColumns", grid.myColumnsState.permutation, true);
        }
    } else {
    	$('#gridP110').jqGrid('setGridWidth', $("#contenidoPagina").width(), false);
    }
	
	
	var messageVal=findValidation();
	if (messageVal!=null){
		$('#gridP110').jqGrid('clearGridData');
		
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		
		var objJson = prepareObjectJSON();
		
		var max = '';
		if (grid.getRowNumPerPage()!=null){
			max='&max='+grid.getRowNumPerPage();
		}
		var index = '';
		if (grid.getSortIndex()!=null){
			index = '&index='+grid.getSortIndex();
		}
		var url = './facingVegalsa/loadDataGrid.do?page='+grid.getActualPage()+max+index+'&sortorder='+grid.getSortOrder();	
		$.ajax({
			type : 'POST',
			url : url,
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {	
				$('#p110_btn_exportExcel').prop("disabled",false);
				if (data.records != 0){
					$("#gbox_gridP110").show();
					$("#AreaResultados").show();

					$(grid.nameJQuery)[0].addJSONData(data);
					grid.actualPage = data.page;
					grid.localData = data;  

					if (recarga == "N") {
						jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
					} else {
						jQuery(grid.nameJQuery).jqGrid('setGridWidth', $("#contenidoPagina").width(), false);
					}
				}

				$("#AreaResultados .loading").css("display", "none");
				$('#p110_btn_buscar').prop("disabled",false); // Activar el botón "Buscar".
				if (data.records == 0){
					createAlert(replaceSpecialCharacters(emptyRecords), "ERROR");
					$(grid.nameJQuery).jqGrid('clearGridData');
				} 
			},
			error : function (xhr, status, error){
				$("#AreaResultados .loading").css("display", "none");	
				$('#p110_btn_buscar').prop("disabled",false); // Activar el botón "Buscar".
				handleError(xhr, status, error, locale);
	        }
		});
	}
}

function initGridP110(grid){
	$(grid.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		datatype : 'local',
		contentType : 'application/json',
		mtype : 'POST',
//		headertitles: true ,
		colNames : grid.colNames,
		colModel : grid.cm,
		rowNum : 10,
		rowList : [ 10, 20, 50 ],
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
}

/*Clase de constantes para le GRID*/
function GridP110 (locale){
	
		// Atributos
		this.name = "gridP110"; 
		this.nameJQuery = "#gridP110"; 
		this.i18nJSON = './misumi/resources/p110FacingVegalsa/p110facingVegalsa_' + locale + '.json';
		this.colNames = null;
		this.cm = [	

		{
			"name" : "area",
			"index":"area", 
			"width" : 15
		},{
			"name" : "seccion",
			"index":"seccion", 
			"width" : 15
		},{
			"name" : "categoria",
			"index":"categoria", 
			"width" : 15
		},{
			"name" : "subcategoria",
			"index":"subcategoria", 
			"width" : 15
		},{
			"name" : "segmento",
			"index":"segmento", 
			"width" : 15
		},{
			"name" : "referencia",
			"index":"referencia", 
			"formatter":"integer",
			"align" : "center",
			"width" : 20
			     
		},{
			"name" : "denominacion",
			"index":"denominacion", 
			"width" : 70						
		},{
			"name" : "marca",
			"index":"marca", 
			"width" : 15
		},{
			"name" : "uc",
			"index" : "uc", 
			"formatter":"integer",
			"align" : "center",
			"width" : 15
		},{
			"name" : "stock",
			"index" : "stock",
			"align" : "center",
			"sortable" : false,
			"width" : 15
		},{
			"name" : "mmc",
			"index" : "mmc", 
			"align" : "center",
			"width" : 10
		},{
			"name" : "cc",
			"index" : "cc", 
			"sortable" : false,
			"width" : 6
		},{
			"name" : "reapro",
			"index" : "reapro",
			"align" : "center",
			"width" : 4
		},{
			"name" : "ufp",
			"index" : "ufp",
			"width" : 15
		},{
			"name" : "tipoAprov",
			"index":"tipoAprov", 
			"width" : 10
		},{
			"name" : "ffpp",
			"index" : "ffpp", 
			"width" : 15
		},{
			"name" : "numOferta",
			"index" : "numOferta", 
			"width" : 18
		},{
			"name" : "mapa",
			"index" : "mapa",
			"width" : 18
		},{
			"name" : "catalogo",
			"index" : "catalogo",
			"align" : "center",
			"width" : 5
		},{
			"name" : "capacidad",
			"index":"capacidad",
			"formatter":"integer",
			"align" : "center",
			"width" : 5
		},{
			"name" : "facing",
			"index":"facing",
			"formatter":"integer",
			"align" : "center",
			"width" : 15
		},{
			"name" : "fondo",
			"index":"fondo",
			"formatter":"integer",
			"align" : "center",
			"width" : 5
		}
	];
	
		
	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP110"; 
	this.pagerNameJQuery = "#pagerP110";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;
	this.myColumnStateName = 'gridP110.colState';
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
		if ($(this.nameJQuery).getGridParam('rowNum')){
			return $(this.nameJQuery).getGridParam('rowNum');
		}else{
			return null;
		}
		
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
	
function controlCentro(){
	$( "#centerName" ).bind('focus', function() {
		if ($("#centerName").val()==null || $("#centerName").val()==''){
			cleanFilterSelection(RESET_CONST);
			$("#p110_cmb_mmc").combobox('autocomplete',"");
	 		$("#p110_cmb_mmc").combobox('comboautocomplete',"null");
			$("#p110_cmb_catalogo").combobox('autocomplete',"");
	 		$("#p110_cmb_catalogo").combobox('comboautocomplete',"null");
			$("#p110_cmb_tipoAprov").combobox('autocomplete',"");
	 		$("#p110_cmb_tipoAprov").combobox('comboautocomplete',"null");
		}
    });
}

function cleanFilterSelection(componentName){
	
	if (componentName == RESET_CONST){
		$('#p110_btn_exportExcel').prop("disabled",true);
		$("#p110_cmb_area").combobox(null);
		$("select#p110_cmb_area").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p110_cmb_area").combobox('autocomplete',"");
		$("#p110_cmb_area").combobox('comboautocomplete',null);
		$("#p110_cmb_seccion").combobox(null);
		$("#p110_cmb_subcategoria").combobox(null);
		$("#p110_cmb_categoria").combobox(null);
		$("#p110_cmb_segmento").combobox(null);
		if ($("#centerId").val()!=null && $("#centerId").val()!=''){
			load_cmbArea();
		}
	}
	
	if (componentName == AREA_CONST || componentName == RESET_CONST){
		$("select#p110_cmb_seccion").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p110_cmb_seccion").combobox('autocomplete','');
		$("#p110_cmb_seccion").combobox('comboautocomplete',null);

	}
	if(componentName==SECTION_CONST || componentName==AREA_CONST || componentName == RESET_CONST){
		
		$("select#p110_cmb_categoria").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p110_cmb_categoria").combobox('autocomplete','');
		$("#p110_cmb_categoria").combobox('comboautocomplete',null);

		
	}
	if(componentName==CATEGORY_CONST || componentName==SECTION_CONST || componentName==AREA_CONST || componentName == RESET_CONST){
	
		$("select#p110_cmb_subcategoria").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p110_cmb_subcategoria").combobox('autocomplete',"");
		$("#p110_cmb_subcategoria").combobox('comboautocomplete',null);

		
	}
	if(componentName==SUBCATEGORY_CONST || componentName==CATEGORY_CONST || componentName==SECTION_CONST || componentName==AREA_CONST || componentName == RESET_CONST){
		
		$("select#p110_cmb_segmento").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p110_cmb_segmento").combobox('autocomplete',"");
		$("#p110_cmb_segmento").combobox('comboautocomplete',null);
	

	}
	disableFilterSelection(componentName);
	return true;
}
function disableFilterSelection(componentName){
	if (componentName == RESET_CONST){
		if ($("#centerId").val()!=null && $("#centerId").val()!=''){
			$("#p110_cmb_area").combobox("enable");
			$("#p110_cmb_mmc").combobox("enable");
			$("#p110_cmb_catalogo").combobox("enable");
			$("#p110_cmb_tipoAprov").combobox("enable");
		}else{
			$("#p110_cmb_area").combobox("disable");
			$("#p110_cmb_mmc").combobox("disable");
			$("#p110_cmb_catalogo").combobox("disable");
			$("#p110_cmb_tipoAprov").combobox("disable");
		}
		$("#p110_cmb_seccion").combobox("disable");
		$("#p110_cmb_categoria").combobox("disable");
		$("#p110_cmb_subcategoria").combobox("disable");
		$("#p110_cmb_segmento").combobox("disable");
		
		$("#p110_chk_fondoAlimentado").prop('checked',false);
 		$("#p110_chk_conStock").prop('checked',false);
 		$("#p110_chk_gama").prop('checked',false);
 		
 		$('#p110_chk_fondoAlimentado').prop('disabled',true);
 		$('#p110_chk_conStock').prop('disabled',true);
 		$('#p110_chk_gama').prop('disabled',true);
	}else if (componentName == AREA_CONST){
		$("#p110_cmb_seccion").combobox("enable");
		$("#p110_cmb_categoria").combobox("disable");
		$("#p110_cmb_subcategoria").combobox("disable");
		$("#p110_cmb_segmento").combobox("disable");
		
	} else	if(componentName==SECTION_CONST ){
		$("#p110_cmb_categoria").combobox("enable");
		$("#p110_cmb_subcategoria").combobox("disable");
		$("#p110_cmb_segmento").combobox("disable");
	}
	if(componentName==CATEGORY_CONST ){
		$("#p110_cmb_subcategoria").combobox("enable");
		$("#p110_cmb_segmento").combobox("disable");

	}
	if(componentName==SUBCATEGORY_CONST){
		$("#p110_cmb_segmento").combobox("enable");

	}
}



function p110FormaterNivelLote(cellValue, opts, rData) {

	//Añadimos los valores de la columna referencia ocultos para poder utilizarlos posteriormente.
	var datoNivelLote= "<input type='hidden' id='"+opts.rowId+"_nivelLote' value='"+rData['nivelLote']+"'>";
	
	return datoNivelLote;
}

function prepareObjectJSONExcel(){
	var centro = $("#centerId").val();
	var area = $("#p110_cmb_area").combobox('getValue');
	var seccion = $("#p110_cmb_seccion").combobox('getValue');
	var categoria = $("#p110_cmb_categoria").combobox('getValue');
	var subcategoria = $("#p110_cmb_subcategoria").combobox('getValue');
	var segmento = $("#p110_cmb_segmento").combobox('getValue');
	var mmc = $("#p110_cmb_mmc").combobox('getValue');
	var catalogo = $("#p110_cmb_catalogo").combobox('getValue');
	var tipoAprov = $("#p110_cmb_tipoAprov").combobox('getValue');
	var facing0 = $("#p110_chk_facing0").is(":checked");
	var alimentado = $("#p110_chk_fondoAlimentado").is(":checked");
	var conStock = $("#p110_chk_conStock").is(":checked");
	var gama = $("#p110_chk_gama").is(":checked");
	
	var centroDesc = $("#centerName").val();
	var areaDesc = $("#p110_cmb_area :selected").text();
	var seccionDesc = $("#p110_cmb_seccion :selected").text();
	var categoriaDesc = $("#p110_cmb_categoria :selected").text();
	var subcategoriaDesc = $("#p110_cmb_subcategoria :selected").text();
	var segmentoDesc = $("#p110_cmb_segmento :selected").text();
	var mmcDesc = $("#p110_cmb_mmc :selected").text();
	var catalogoDesc = $("#p110_cmb_catalogo :selected").text();
	var tipoAprovDesc = $("#p110_cmb_tipoAprov :selected").text();
	
	var buscador = {
		"centro" : centro,
		"area" : area,
		"seccion" : seccion,
		"categoria" : categoria,
		"subcategoria" : subcategoria,
		"segmento" : segmento,
		"mmc" : mmc!="null"?mmc:null,
		"catalogo" : catalogo!="null"?catalogo:null,
		"tipoAprov" : tipoAprov!="null"?tipoAprov:null,
		"facing0" : facing0,
		"centroDesc" : centroDesc,
		"areaDesc" : areaDesc,
		"seccionDesc" : seccionDesc,
		"categoriaDesc" : categoriaDesc,
		"subcategoriaDesc" : subcategoriaDesc,
		"segmentoDesc" : segmentoDesc,
		"mmcDesc" : mmcDesc,
		"catalogoDesc" : catalogoDesc,
		"tipoAprovDesc" : tipoAprovDesc,
		"alimentado" : alimentado,
		"conStock" : conStock,
		"gama" : gama
	};
	return $.toJSON(buscador);
}

function exportExcel(){
	var messageVal=findValidation();
	if (messageVal!=null){
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		$('#p110_btn_exportExcel').prop('disabled',true);
		var colModel = $(grid.nameJQuery).jqGrid("getGridParam", "colModel");
		
	    var headers = new Array();
	  
	    $.each(colModel, function(i) {
	    	if (colModel[i].name!="rn" && !colModel[i].hidden ){
		    	headers.push(colModel[i].name);
	    	}
	    });
	    
	    var url = 'facingVegalsa/exportGrid.do?headers='+headers;
	    var objJson = prepareObjectJSONExcel();
	    var form = $('<form/>', { action: url, method: 'GET', name: 'csvexportform'});
	    form.append( $('<input />', {type: 'hidden', name: 'headers', value: headers}) );
	    form.append( $('<input />', {type: 'hidden', name: 'facingVegalsaRequest', value: objJson}) );
	    form.appendTo('body').submit();
		$('#p110_btn_exportExcel').prop('disabled',true);
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
				$('#p110_btn_exportExcel').prop("disabled",false); // Vuelvo a activar el botón <Excel>.
//				console.log('EXCEL generado.');
			}
			
		},
		error : function (xhr, status, error){
			$("#AreaResultados .loading").css("display", "none");
			$('#p110_btn_exportExcel').prop("disabled",false); // Vuelvo a activar el botón <Excel>.
			handleError(xhr, status, error, locale);
        }			
	});
}

function formatDescripcionCombo(codigo, descripcion){
	return codigo + "-" + descripcion;
}

function habilitarChecksFiltro(){
	var facing0 = $("#p110_chk_facing0").is(":checked");
	$("#p110_chk_fondoAlimentado").prop('checked',false);
	$("#p110_chk_conStock").prop('checked',false);
	$("#p110_chk_gama").prop('checked',false);
	
	if(facing0){
 		$('#p110_chk_fondoAlimentado').prop('disabled',false);
 		$('#p110_chk_conStock').prop('disabled',false);
 		$('#p110_chk_gama').prop('disabled',false);
 		
 		$("#p110_cmb_mmc").combobox('autocomplete',"SI");
 		$("#p110_chk_fondoAlimentado").prop('checked',true);
 		$("#p110_chk_conStock").prop('checked',true);
 		$("#p110_chk_gama").prop('checked',true);
	}else{
 		$('#p110_chk_fondoAlimentado').prop('disabled',true);
 		$('#p110_chk_conStock').prop('disabled',true);
 		$('#p110_chk_gama').prop('disabled',true);
 		$("#p110_cmb_mmc").combobox('autocomplete',allOptions);
 	}
}

function comprobarComboMMC(){
	var gama = $("#p110_chk_gama").is(":checked");
	//Si gama=true entonces MMC = SI y bloqueado
	if(gama){
		$("#p110_cmb_mmc").combobox(null);
		if ($("#centerId").val()!=null && $("#centerId").val()!=''){
			$("#p110_cmb_mmc").combobox('autocomplete',"SI");
		}else{
			$("#p110_cmb_mmc").combobox('autocomplete',"");
		}
		$("#p110_cmb_mmc").combobox('comboautocomplete',"S");
		$("#p110_cmb_mmc").combobox("disable");
	//Si gama=false entonces MMC = TODOS y habilitado
	}else{
		$("#p110_cmb_mmc").combobox(null);
		if ($("#centerId").val()!=null && $("#centerId").val()!=''){
			$("#p110_cmb_mmc").combobox('autocomplete',allOptions);
		}else{
			$("#p110_cmb_mmc").combobox('autocomplete',"");
		}
		$("#p110_cmb_mmc").combobox('comboautocomplete',"null");
		$("#p110_cmb_mmc").combobox("enable");
	}
}




