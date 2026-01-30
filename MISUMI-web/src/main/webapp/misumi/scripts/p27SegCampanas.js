var centerRequired=null;
var p27textoColumn1=null;
var P27Inicializada = null;

var p27SeguimientoCampanaEnProcesoDeCarga = "N";
$(document).ready(function(){
	$(document).on('CargarScriptsPantallas', function(e) { 
		$(document).on('CargadoCentro', function(e) { 
			if(P27Inicializada == null){
				P27Inicializada = 'S';
				p27initializeScreen();
				load_gridP27Tree(locale);
			}
		});
	
		if($("#centerId").val() != null && $("#centerId").val() != "" && P27Inicializada == null){
			P27Inicializada = 'S';
			p27initializeScreen();
			load_gridP27Tree(locale);
		}
		
		events_p27_rad_tipoFiltro();
		events_p27_btn_reset();
		events_p27_btn_buscar();
	});
	initializeScreenComponentsP27();
});

function initializeScreenComponentsP27(){	
	$("#p27_cmb_campana").combobox(null);
	$("#p27_cmb_oferta").combobox(null);
	CargadaEstructuraPantalla(2);
};

function p27initializeScreen(){
	
	$("select#p27_cmb_campana").html("<option value=null selected='selected'>"+''+"</option>");
	$("#p27_cmb_campana").combobox('autocomplete',"");
	$("#p27_cmb_campana").combobox('comboautocomplete',null);

	$("select#p27_cmb_oferta").html("<option value=null selected='selected'>"+''+"</option>");
	$("#p27_cmb_oferta").combobox('autocomplete',"");
	$("#p27_cmb_oferta").combobox('comboautocomplete',null);

	$("#p27_fld_referencia").filter_input({regex:'[0-9]'});
	$("#p27_rad_tipoFiltro").filter('[value=1]').attr('checked', true);
	p27cleanFilterSelection();
	p27controlCentro();
}

function events_p27_rad_tipoFiltro(){
    $("input[name='p27_rad_tipoFiltro']").change(function () {
 
    	if ($("input[name='p27_rad_tipoFiltro']:checked").val() == "1"){
    		//Campaña
    		 $("div#p27_filtroCampana").attr("style", "display:inline");
    		 $("div#p27_filtroOferta").attr("style", "display:none");
    		 $("#p27_cmb_oferta_tmp").val("");
    		 $("#p12_cmb_oferta").combobox('autocomplete',"");
    	 	 $("#p12_cmb_oferta").combobox('comboautocomplete',null);
    		 $('#gridP27').jqGrid('clearGridData');
    		 if ($("#centerId").val()!=null && $("#centerId").val()!=''){
    			 p27load_cmbCampana();
    		 }
    		 
    	} else { //Oferta
    		$("div#p27_filtroCampana").attr("style", "display:none");
   		 	$("div#p27_filtroOferta").attr("style", "display:inline");
   		 	$("#p27_cmb_campana_tmp").val("");
   		 	$("#p12_cmb_campana").combobox('autocomplete',"");
	 		$("#p12_cmb_campana").combobox('comboautocomplete',null);
	 		$('#gridP27').jqGrid('clearGridData');
	 		if ($("#centerId").val()!=null && $("#centerId").val()!=''){
	 			p27load_cmbOferta();
	 		}
    	}   
    });
}

function events_p27_btn_reset(){
	$("#p27_btn_reset").click(function () {
		p27resetPantalla();
	});
}

function events_p27_btn_buscar(){
	$("#p27_btn_buscar").click(function () {
		if(p27SeguimientoCampanaEnProcesoDeCarga == "N"){
			p27SeguimientoCampanaEnProcesoDeCarga = "S";
			finder();
		}
	});	
}

function finder(){
	var messageVal=null;
	p27resetResultados();
	//Centro
	if ($("#centerId").val()==null || $("#centerId").val()==""){
		messageVal = centerRequired;
	}		

	if (messageVal!=null){
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		if($("div#p27_filtroCampana").is(':visible')){
   		    $("#p27_rad_tipoFiltro_tmp").val("C");
			$("#p27_cmb_campana_tmp").val($("#p27_cmb_campana").combobox('getValue'));
		}else{
   		    $("#p27_rad_tipoFiltro_tmp").val("O");
			$("#p27_cmb_oferta_tmp").val($("#p27_cmb_oferta").combobox('getValue'));
		}
		$("#p27_fld_referencia_tmp").val($("#p27_fld_referencia").val());
		p27recargaDatosCampanaOfer(grid);
	}
}

function p27resetResultados (){
	$('#gridP27').jqGrid('clearGridData');
	$("#gridP27").jqGrid('GridUnload');
}

function load_gridP27Tree(locale){
	grid = new GridP27(locale);
	
	var jqxhr = $.getJSON(grid.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				grid.colNames = data.ColNames;
				grid.title = data.GridTitle;
				grid.emptyRecords= data.emptyRecords;
				grid.emptyRecordsSelecc= data.emptyRecordsSelecc;
				grid.expandirTodo = data.expandirTodo;
				grid.contraerTodo = data.contraerTodo;
				grid.formatoFecha = data.formatoFecha;
				p27textoColumn1 = data.p27textoColumn1;
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
        });
}

function p27controlCentro(){
	$( "#centerName" ).bind('focus', function() {
		$("#centerName").val('');
		if ($("#centerName").val()==null || $("#centerName").val()==''){
			p27resetPantalla();
		}
    });
}

function p27resetPantalla(){
	$("#p27_fld_referencia").val("");
	$("#p27_fld_referencia_tmp").val("");
	$("#p27_cmb_campana").combobox('autocomplete',"");
	$("#p27_cmb_campana").combobox('comboautocomplete',null);
	$("#p27_cmb_campana_tmp").val("");
	$("#p27_cmb_oferta").combobox('autocomplete',"");
	$("#p27_cmb_oferta").combobox('comboautocomplete',null);
	$("#p27_cmb_oferta_tmp").val("");
	$("#p27AreaErrores").attr("style", "display:none");
	p27resetResultados();
	p27disableFilterSelection();
}

function p27recargaDatosCampanaOfer(grid){
	
	var mydata = 
 	{	codCentro:$("#centerId").val(),
		codArt:$("#p27_fld_referencia_tmp").val(),
		tipoOC:$("#p27_rad_tipoFiltro_tmp").val(),
		identificador:$("#p27_cmb_campana_tmp").val(),
		oferta:$("#p27_cmb_oferta_tmp").val()
    };
	
	var objJson = $.toJSON(mydata);	

	$.ajax({
			type : 'POST',
			url : './segCampanas/recargaDatosCampanaOfer.do',
			datatype : 'json',
			data : objJson,
			contentType : "application/json; charset=utf-8",
			cache : false,
			success : function(data) {
				//Para permitir pulsar el botón de buscar.
				p27SeguimientoCampanaEnProcesoDeCarga = "N";
				
				//Comprobación de carga de datos de campana oferta. Si hay error se mostrará un mensaje indicando que no se han podido
				//recargar los datos.
				if (data != null && data != '')
				{
					if (data.codError == 0){
						$("#p27AreaErrores").attr("style", "display:none");
						loadData_gridP27(grid);
					}else{
						if (typeof(data.codError) != "undefined"){
							$("#p27AreaErrores").attr("style", "display:block");
						}else{
							//Si devuelve un html se fuerza al inicio para controlar caducidad de sesion
							window.location='./login.do';
						}
					}
				
				}else{
					$("#p27AreaErrores").attr("style", "display:none");
				}
				
			},
			error : function (xhr, status, error){
				handleError(xhr, status, error, locale);
	        }			
		});
}

function loadData_gridP27(grid){

	var mydata = 
 	{	codCentro:$("#centerId").val(),
		codArt:$("#p27_fld_referencia_tmp").val(),
		tipoOC:$("#p27_rad_tipoFiltro_tmp").val(),
		identificador:$("#p27_cmb_campana_tmp").val(),
		oferta:$("#p27_cmb_oferta_tmp").val()
    };
	
	var objJson = $.toJSON(mydata);	
	
	$(grid.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		url : './segCampanas/loadAllData.do',
		datatype : 'json',
		contentType : 'application/json',
		jsonReader : {
			root : "rows",
			page : "page",
			total : "total",
			records : "records",
			repeatitems : false
		},
		treeReader : {
				   level_field: "level",
				   parent_id_field: "parent", 
				   leaf_field: "leaf",
				   expanded_field: "expanded"
		},
		postData : {
			   nodeid : "",
			   parentid : "",
			   n_level : 0 
			  
		},
		serializeGridData: function (postData) {
				var jsonObject = null;
				jsonObject = {"nodeid": postData.nodeid,
					     "parentid": postData.parentid,
					     "n_level": postData.n_level,
					     "datosCampana": mydata
						}; 
	           return JSON.stringify(jsonObject);
		},
		mtype : 'POST',
		 
	    treeGrid: true,
	    treeGridModel: 'adjacency', //nested o adjacency
	    ExpandColClick : true, //para permitir que se pueda expandir/colapsar pulsando en la columna
	    ExpandColumn : 'campanaOferta', 
	    // See http://jquery.keicode.com/ui/icons.php
	    //treeIcons: {plus:'ui-icon-triangle-1-e',minus:'ui-icon-triangle-1-s',leaf:'ui-icon-document'},
	    treeIcons: {plus:'ui-icon-circle-plus',minus:'ui-icon-minusthick',leaf:'ui-icon-document'},	    
	    colNames : grid.colNames, 
	    colModel :[ 
	         {name:'descripcionNivel', index:'descripcionNivel', width:'425', resizable: false, sortable:false, formatter: p27descripcionFormatter}, 
	         {name:'servidoPendiente', index:'servidoPendiente', width:'280', resizable: false, sortable:false, formatter: p27servidoPendienteFormatter},
	         {name:'noServido', index:'noServido', width:'135', resizable: false, sortable:false, formatter: p27noServidoFormatter},
	         {name:'ventasPrevision', index:'ventasPrevision', width:'135', resizable: false, sortable:false, formatter: p27ventasPrevisionFormatter}
	    ],
	    rownumbers : grid.rownumbers,
		pager : grid.pagerNameJQuery,
        rowNum: 200,
        rowList:[],
        sortable : false,
        sortname: grid.sortIndex,
        sortorder: grid.sortOrder,
        viewrecords: true,
        gridview: true,
        caption: grid.title,
        altclass: "ui-priority-secondary",
		altRows: true, //false, para que el grid no muestre cebrado
		hidegrid : false, //false, para ocultar el boton que colapsa el grid.
        width: 'auto',
        height: 'auto',
        gridComplete : function() {			

        	grid.headerHeight("p27_gridSegCampanasHeader");     	
			
			$("td[aria-describedby=gridP27_campanaOferta]").css("border-bottom-width", "0px"); //Para borrar el borde inferior de las celdas de la primera columna
			$("td[aria-describedby=gridP27_campanaOferta]").css("border-left-width", "0px"); //Para borrar el borde izquierdo de las celdas de la primera columna
			
			$("div[class='tree-wrap tree-wrap-ltr']").css("padding-top", "10px"); //Para centrar los iconos de + y - de las celdas de la primera columna
			$("div[id=gbox_gridP27]").css("border", "0 solid #AAAAAA"); //Para borrar el borde exterior de toda la tabla
			
			$("#gridP27ExpandAll").click(function() {				
				grid.expandirContraerTodo("gridP27ExpandAll");				
			});
			$("#p27AreaResultados").attr("style", "display:block");
			
			
		},
		loadComplete: function (rowid) {
			var ids = jQuery("#gridP27").jqGrid('getDataIDs'), i, l = ids.length;
			
			if (ids == '')
			{
				createAlert(replaceSpecialCharacters(grid.emptyRecordsSelecc), "ERROR");
			}
			
		    for (i = 0; i < l; i++) {
		    	var name = eliminarCaracteresNoPermitidos(ids[i]);
		    	//Servido/pendiente
		    	var progressbar = $("#p27_progressbar_1_" + name);
		    	var progressLabel = $("#p27_progress-label1_" + name);
		    	var progressbarValue1 = parseFloat($("#p27_progressbar_value_1_1_" + name).val());
		    	var progressbarValue2 = parseFloat($("#p27_progressbar_value_1_2_" + name).val());
		    	progressbar.progressbar({value: false});
				progressbar.progressbar( "option", {
					value: ((progressbarValue2 != 0)?((progressbarValue1 > progressbarValue2)?100:Math.floor( (progressbarValue1*100)/ progressbarValue2)):(progressbarValue1 > 0?100:0))
				});
				progressLabel.text( $.formatNumber(progressbarValue1,{format:"#,##0",locale:"es"})+"/"+$.formatNumber(progressbarValue2,{format:"#,##0",locale:"es"}));

		    	//No servidos unidades
		    	progressbar = $("#p27_progressbar_2_" + name);
		    	progressLabel = $("#p27_progress-label2_1_" + name);
		    	progressbarValue1 = parseFloat($("#p27_progressbar_value_2_1_" + name).val());
		    	progressbarValue2 = parseFloat($("#p27_progressbar_value_2_2_" + name).val());
		    	var progressLabel2 = $("#p27_progress-label2_2_" + name);
		    	progressbar.progressbar({value: false});
				progressbar.progressbar( "option", {
					value: ((progressbarValue1 > 50)?100:Math.floor( (progressbarValue1* 100 )/50))
				});
				progressLabel.text( $.formatNumber(progressbarValue1,{format:"#,##0.#",locale:"es"}));
				progressLabel2.text( $.formatNumber(progressbarValue2,{format:"#,##0",locale:"es"}));

				//Ventas actuales previsión
		    	progressbar = $("#p27_progressbar_3_" + name);
		    	progressLabel = $("#p27_progress-label3_" + name);
		    	progressbarValue1 = parseFloat($("#p27_progressbar_value_3_1_" + name).val());
		    	progressbarValue2 = parseFloat($("#p27_progressbar_value_3_2_" + name).val());
		    	progressbar.progressbar({value: false});
				progressbar.progressbar( "option", {
					value: ((progressbarValue2 != 0)?((progressbarValue1 > progressbarValue2)?100:Math.floor( (progressbarValue1*100)/ progressbarValue2)):(progressbarValue1 > 0?100:0))
				});
				progressLabel.text( $.formatNumber(progressbarValue1,{format:"#,##0",locale:"es"})+"/"+$.formatNumber(progressbarValue2,{format:"#,##0",locale:"es"}));

		    }
			
        },
        onCellSelect: function (rowid, iCol, cellcontent, e){
        	
        	var campo = cellcontent.substring(cellcontent.indexOf(">")+1 , cellcontent.lastIndexOf("<"));
        	var referencia = campo.substring(0 , campo.lastIndexOf("/"));

        	if (referencia == 0)
        	{
        		var messageVal=null;
				messageVal = grid.emptyRecords;
				if (messageVal!=null){
					createAlert(replaceSpecialCharacters(messageVal), "INFO");
				}
        	}
        	else
        	{
        		var fila = eliminarCaracteresNoPermitidos(rowid);
        		var seguimientoCampanas = new SeguimientoCampanas($("#centerId").val(), $("#p27_gridCampana" + fila).val(), $("#p27_gridOferta" + fila).val(), $("#p27_rad_tipoFiltro_tmp").val(), $("#p27_gridFechaInicio" + fila).val(), null, $("#p27_gridFechaFin" + fila).val(), null,
        				$("#p27_gridCodArea" + fila).val(), $("#p27_gridDescArea" + fila).val(), $("#p27_gridCodSeccion" + fila).val(), $("#p27_gridDescSeccion" + fila).val(),
        				$("#p27_gridCodCategoria" + fila).val(), $("#p27_gridDescCategoria" + fila).val(), $("#p27_gridCodSubcategoria" + fila).val(), $("#p27_gridDescSubcategoria" + fila).val(), $("#p27_gridCodSegmento" + fila).val(), $("#p27_gridDescSegmento" + fila).val(), $("#p27_fld_referencia_tmp").val(), '');
        		reloadDataP28(seguimientoCampanas);
        	}
        }
		
    });
}

function p27descripcionFormatter(cellvalue, options, rowObject) {
     var formattedCell = "";

     var descripcionNivel = rowObject.descripcionNivel;
    
	 var fechaInicioDDMMYYYY = rowObject.fechaInicioDDMMYYYY;
	 var fechaFechaInicioFormateada = "";

	 if (fechaInicioDDMMYYYY != null && fechaInicioDDMMYYYY != ""){
		 var diaFechaInicioSt = fechaInicioDDMMYYYY.substring(0,2);
		 var mesFechaInicioSt = fechaInicioDDMMYYYY.substring(2,4);
		 var anyoFechaInicioSt = fechaInicioDDMMYYYY.substring(4);
		 var diaFechaInicio = parseInt(diaFechaInicioSt,10);
		 var mesFechaInicio = parseInt(mesFechaInicioSt,10);
		 var anyoFechaInicio = parseInt(anyoFechaInicioSt,10);
		 var fechaInicioCompleta = anyoFechaInicio + "-" + mesFechaInicio + "-" + diaFechaInicio
		 fechaFechaInicioFormateada = $.datepicker.formatDate("D dd/mm/yy", devuelveDate(fechaInicioCompleta),{
			dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
			dayNames: $.datepicker.regional[ "es" ].dayNames,
			monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
			monthNames: $.datepicker.regional[ "es" ].monthNames
				});

	 }else{
		 fechaInicioDDMMYYYY = "";
	 }	 

	 var fechaFinDDMMYYYY = rowObject.fechaFinDDMMYYYY;
	 var fechaFechaFinFormateada = "";

	 if (fechaFinDDMMYYYY != null && fechaFinDDMMYYYY != ""){
		 var diaFechaFinSt = fechaFinDDMMYYYY.substring(0,2);
		 var mesFechaFinSt = fechaFinDDMMYYYY.substring(2,4);
		 var anyoFechaFinSt = fechaFinDDMMYYYY.substring(4);
		 var diaFechaFin = parseInt(diaFechaFinSt,10);
		 var mesFechaFin = parseInt(mesFechaFinSt,10);
		 var anyoFechaFin = parseInt(anyoFechaFinSt,10);
		 var fechaFinCompleta = anyoFechaFin + "-" + mesFechaFin + "-" + diaFechaFin
		 fechaFechaFinFormateada = $.datepicker.formatDate("D dd/mm/yy", devuelveDate(fechaFinCompleta),{
			dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
			dayNames: $.datepicker.regional[ "es" ].dayNames,
			monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
			monthNames: $.datepicker.regional[ "es" ].monthNames
				});
	 }else{
		 fechaFinDDMMYYYY = "";
	 }

	 var codArea = "";
	 var descArea = "";
	 var codSeccion = "";
	 var descSeccion = "";
	 var codCategoria = "";
	 var descCategoria = "";
	 var codSubcategoria = "";
	 var descSubcategoria = "";
	 var codSegmento =  "";
	 var descSegmento = "";

	 if (rowObject != null){
    	 if (rowObject.codArea!=null && rowObject.codArea!=0){
    		 codArea = rowObject.codArea;
    		 if (rowObject.descArea!=null){
    			 descArea = rowObject.descArea
    		 }
    	 }
    	 if (rowObject.codSeccion!=null && rowObject.codSeccion!=0){
    		 codSeccion = rowObject.codSeccion;
    		 if (rowObject.descSeccion!=null){
    			 descSeccion = rowObject.descSeccion
    		 }
    	 }
    	 if (rowObject.codCategoria!=null && rowObject.codCategoria!=0){
    		 codCategoria = rowObject.codCategoria;
    		 if (rowObject.descCategoria!=null){
    			 descCategoria = rowObject.descCategoria
    		 }
    	 }
    	 if (rowObject.codSubcategoria!=null && rowObject.codSubcategoria!=0){
    		 codSubcategoria = rowObject.codSubcategoria;
    		 if (rowObject.descSubcategoria!=null){
    			 descSubcategoria = rowObject.descSubcategoria
    		 }
    	 }
    	 if (rowObject.codSegmento!=null && rowObject.codSegmento!=0){
    		 codSegmento = rowObject.codSegmento;
    		 if (rowObject.codSegmento!=null){
    			 codSegmento = rowObject.codSegmento
    		 }
    	 }
	 } 

	 var campana = "";
	 var oferta = "";
	 if($("div#p27_filtroCampana").is(':visible')){
		 campana = rowObject.campana;
	 }else{
		 oferta = rowObject.oferta;
	 }
	 if (rowObject.level == 0){
		if($("div#p27_filtroCampana").is(':visible')){
			descripcionNivel = rowObject.campana;
		}else{
			descripcionNivel = rowObject.oferta;
		}
		if(descripcionNivel.length > 30){
			formattedCell = "<span class=\"p27_gridSegCampanasColum0Texto1\">" + descripcionNivel + "</span>";
			formattedCell = formattedCell + "<span class=\"p27_gridSegCampanasColum0Texto1\">(" + fechaFechaInicioFormateada + " - " + fechaFechaFinFormateada +")</span>";
		}else{
			formattedCell =   "<span class=\"p27_gridSegCampanasColum0Texto1\">" + descripcionNivel + " (" + fechaFechaInicioFormateada + " - " + fechaFechaFinFormateada + ")</span>";
		}
	 }else if (rowObject.level == 1){
	 	formattedCell = "<span class=\"p27_gridSegCampanasColum0Texto2\">" + descripcionNivel + "</span>";
	 }else if (rowObject.level == 2){
	 	formattedCell = "<span class=\"p27_gridSegCampanasColum0Texto3\">" + descripcionNivel + "</span>";
	 }else if (rowObject.level == 3){
	 	formattedCell = "<span class=\"p27_gridSegCampanasColum0Texto4\">" + descripcionNivel + "</span>";
	 }
	 
	 var name = eliminarCaracteresNoPermitidos(rowObject.id);
	 formattedCell = formattedCell + "<input type=\"hidden\" id=\"p27_gridCampana" + name + "\" value=\"" + campana + "\"/>";
	 formattedCell = formattedCell + "<input type=\"hidden\" id=\"p27_gridOferta" + name + "\" value=\"" + oferta + "\"/>";
	 formattedCell = formattedCell + "<input type=\"hidden\" id=\"p27_gridFechaInicio" + name + "\" value=\"" + fechaInicioDDMMYYYY + "\"/>";
	 formattedCell = formattedCell + "<input type=\"hidden\" id=\"p27_gridFechaFin" + name + "\" value=\"" + fechaFinDDMMYYYY + "\"/>";
	 formattedCell = formattedCell + "<input type=\"hidden\" id=\"p27_gridCodArea" + name + "\" value=\"" + codArea + "\"/>";
	 formattedCell = formattedCell + "<input type=\"hidden\" id=\"p27_gridDescArea" + name + "\" value=\"" + descArea + "\"/>";
	 formattedCell = formattedCell + "<input type=\"hidden\" id=\"p27_gridCodSeccion" + name + "\" value=\"" + codSeccion + "\"/>";
	 formattedCell = formattedCell + "<input type=\"hidden\" id=\"p27_gridDescSeccion" + name + "\" value=\"" + descSeccion + "\"/>";
	 formattedCell = formattedCell + "<input type=\"hidden\" id=\"p27_gridCodCategoria" + name + "\" value=\"" + codCategoria + "\"/>";
	 formattedCell = formattedCell + "<input type=\"hidden\" id=\"p27_gridDescCategoria" + name + "\" value=\"" + descCategoria + "\"/>";
	 formattedCell = formattedCell + "<input type=\"hidden\" id=\"p27_gridCodSubcategoria" + name + "\" value=\"" + codSubcategoria + "\"/>";
	 formattedCell = formattedCell + "<input type=\"hidden\" id=\"p27_gridDescSubcategoria" + name + "\" value=\"" + descSubcategoria + "\"/>";
	 formattedCell = formattedCell + "<input type=\"hidden\" id=\"p27_gridCodSegmento" + name + "\" value=\"" + codSegmento + "\"/>";
	 formattedCell = formattedCell + "<input type=\"hidden\" id=\"p27_gridDescSegmento" + name + "\" value=\"" + descSegmento + "\"/>";
	 
    return formattedCell;
}


function p27servidoPendienteFormatter(cellvalue, options, rowObject) {
    var cellData = cellvalue.split("**");
	
	var formattedCell = "";
	var name = eliminarCaracteresNoPermitidos(rowObject.id);
	
	formattedCell += "<div class=\"p27_gridSegCampanasColum1\">";
	formattedCell += "<div class=\"p27_progressbar_1_label\"><span class=\"p27_gridSegCampanasColum1Texto1\">" + p27textoColumn1 + $.formatNumber( cellData[0],{format:"#,##0",locale:"es"}) + "</span></div>";	
	formattedCell += "<div id=\"p27_progressbar_1_" + name + "\" class=\"p27_progressbar_1_img\"><div id=\"p27_progress-label1_" + name + "\" class=\"p27_progress-label1\"></div></div>";
	formattedCell += "<input id=\"p27_progressbar_value_1_1_" + name + "\" type=\"hidden\" value=\"" + cellData[1] + "\"></input>";
	formattedCell += "<input id=\"p27_progressbar_value_1_2_" + name + "\" type=\"hidden\" value=\"" + cellData[2] + "\"></input>";
	formattedCell += "</div>";
	        
    return formattedCell;
}

function p27noServidoFormatter(cellvalue, options, rowObject) {
    var cellData = cellvalue.split("**");
    
	var formattedCell = "";
	var name = eliminarCaracteresNoPermitidos(rowObject.id);
	
	formattedCell += "<div class=\"p27_gridSegCampanasColum2\">";
	formattedCell += "<div id=\"p27_progressbar_2_" + name + "\" class=\"p27_progressbar_2_img\"><div id=\"p27_progress-label2_1_" + name + "\" class=\"p27_progress-label2_1\"></div><div id=\"p27_progress-label2_2_" + name + "\" class=\"p27_progress-label2_2\"></div></div>";
	formattedCell += "<input id=\"p27_progressbar_value_2_1_" + name + "\" type=\"hidden\" value=\"" + cellData[0] + "\"></input>";
	formattedCell += "<input id=\"p27_progressbar_value_2_2_" + name + "\" type=\"hidden\" value=\"" + cellData[1] + "\"></input>";
	formattedCell += "</div>";
	        
    return formattedCell;
}

function p27ventasPrevisionFormatter(cellvalue, options, rowObject) {
    var cellData = cellvalue.split("**");
    
	var formattedCell = "";
	var name = eliminarCaracteresNoPermitidos(rowObject.id);
	
	formattedCell += "<div class=\"p27_gridSegCampanasColum3\">";
	formattedCell += "<div id=\"p27_progressbar_3_" + name + "\" class=\"p27_progressbar_3_img\"><div  id=\"p27_progress-label3_" + name + "\" class=\"p27_progress-label3\"></div></div>";
	formattedCell += "<input id=\"p27_progressbar_value_3_1_" + name + "\" type=\"hidden\" value=\"" + cellData[0] + "\"></input>";
	formattedCell += "<input id=\"p27_progressbar_value_3_2_" + name + "\" type=\"hidden\" value=\"" + cellData[1] + "\"></input>";
	formattedCell += "</div>";
	        
    return formattedCell;
}

/*Clase de constantes para el TREE-GRID */
function GridP27 (locale){
	// Atributos
	this.name = "gridP27"; 
	this.nameJQuery = "#gridP27"; 
	this.i18nJSON = './misumi/resources/p27SegCampanas/p27segCampanas_' + locale + '.json';
	this.colNames = null; //Esta en el json
	
	this.locale = locale;
	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = null;  
	this.pagerNameJQuery = null;
	this.title = null; //Esta en el json
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null; //Esta en el json
	this.rownumbers = false;
	this.expandirTodo = "";
	this.contraerTodo = "";
	this.marcaDiaHoy = "";
	this.formatoFecha = "";
	
	//Metodos		
	this.getActualPage = function getActualPage(){ 
		if (this.actualPage == null)
			this.actualPage = 1;
		else
			this.actualPage = $(this.nameJQuery).getGridParam('page');
		
		return this.actualPage;
	};
	
	this.getSelectedRow = function getSelectedRow(){ 
		return $(this.nameJQuery).getGridParam('selrow'); 
	};
	
	this.getCellValue = function getCellValue(rowId, colName){ 
		return $(this.nameJQuery).getRowData(rowId)[colName]; 
	};
	
	this.getRowNumPerPage = function getRowNumPerPage () {
		return $(this.nameJQuery).getGridParam('rowNum');
	};
	
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
	
	this.clearGrid = function clearGrid() {
		$(this.nameJQuery).jqGrid('GridUnload');
	}
	
	this.headerHeight = function headerHeight(cssClass) {
		var colModel = $(this.nameJQuery).jqGrid('getGridParam','colModel');
		var colNames = $(this.nameJQuery).jqGrid('getGridParam','colNames');
		for (i = 0; i < colNames.length; i++){
			$("#gridP27").setLabel(colModel[i].name, colNames[i], cssClass);
		}
	}
	
	this.expandirContraerTodo = function expandirContraerTodo(btnName){
		var jqBtnName = "#" + btnName; 
		if ($(jqBtnName).val() == this.expandirTodo){
			$(".treeclick","table#gridP27").each(function() {
				if($(this).hasClass("tree-plus")) {
					$(this).trigger("click");
				}
			});	
			$(jqBtnName).val(this.contraerTodo);
		} else {
			$(".treeclick","table#gridP27").each(function() {
				if($(this).hasClass("tree-minus")) {
					$(this).trigger("click");
				}
			});
			$(jqBtnName).val(this.expandirTodo);
		}				
	}
		
}

function controlCentro(){
	$( "#centerName" ).bind('focus', function() {
		if ($("#centerName").val()==null || $("#centerName").val()==''){
			p27cleanFilterSelection();
			$("#p27_cmb_campana").combobox('autocomplete',"");
			$("#p27_cmb_campana").combobox('comboautocomplete',null);
			$("#p27_cmb_oferta").combobox('autocomplete',"");
			$("#p27_cmb_oferta").combobox('comboautocomplete',null);
		}
    });
}

function p27disableFilterSelection(){
	if ($("#centerId").val()!=null && $("#centerId").val()!=''){
		$("#p27_cmb_campana").combobox("enable");
		$("#p27_cmb_oferta").combobox("enable");
		$("#p27_fld_referencia").removeAttr("disabled");
		$("#p27_rad_tipoFiltro").removeAttr("disabled");
		$("input[name='p27_rad_tipoFiltro']").each(function(i) {
			$(this).removeAttr("disabled");
		});
	}else{
		$("#p27_cmb_campana").combobox("disable");
		$("#p27_cmb_oferta").combobox("disable");
		$("#p27_fld_referencia").attr("disabled", "disabled");
		$("input[name='p27_rad_tipoFiltro']").each(function(i) {
			$(this).attr('disabled', 'disabled');
		});
	}
}

function p27cleanFilterSelection(){
	
	$("#p27_cmb_campana").combobox(null);
	$("select#p27_cmb_campana").html("<option value=null selected='selected'>"+''+"</option>");
	$("#p27_cmb_campana").combobox('autocomplete',"");
	$("#p27_cmb_campana").combobox('comboautocomplete',null);

	$("#p27_cmb_oferta").combobox(null);
	$("select#p27_cmb_oferta").html("<option value=null selected='selected'>"+''+"</option>");
	$("#p27_cmb_oferta").combobox('autocomplete',"");
	$("#p27_cmb_oferta").combobox('comboautocomplete',null);

	if ($("#centerId").val()!=null && $("#centerId").val()!=''){
		if($("div#p27_filtroCampana").is(':visible')){
			p27load_cmbCampana();
		}else{
			p27load_cmbOferta();
		}
	}
	p27disableFilterSelection();
	
	return true;
}

function p27load_cmbCampana(){	
	
	var options = "";
	var optionNull = "";
	$.ajax({
		type : 'POST',
		url : './segCampanas/loadCampanas.do',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			//options = "<option value='null' selected='selected'>"+optionNull+"</option>";
			   for (i = 0; i < data.length; i++){
					options = options + "<option value='" + data[i].identificador + "'>" + data[i].identificador + "</option>"; 
			   }
			   $("select#p27_cmb_campana").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
	$("#p27_cmb_campana").combobox({
        selected: function(event, ui) {
        }  ,
	    changed: function(event, ui) { 
	   }
    }); 
	
	$("#p27_cmb_campana").combobox('autocomplete',optionNull);
	$("#p27_cmb_campana").combobox('comboautocomplete',null);

}

function p27load_cmbOferta(){	
	
	var options = "";
	var optionNull = "";
	$.ajax({
		type : 'POST',
		url : './segCampanas/loadOfertas.do',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			   for (i = 0; i < data.length; i++){
					options = options + "<option value='" + data[i].anoOferta + "-" + data[i].numOferta + "'>" + data[i].anoOferta + "-" + data[i].numOferta + "</option>"; 
			   }
			   $("select#p27_cmb_oferta").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
	$("#p27_cmb_oferta").combobox({
        selected: function(event, ui) {
        }  ,
	    changed: function(event, ui) { 
	   }
    }); 
	
	$("#p27_cmb_oferta").combobox('autocomplete',optionNull);
	$("#p27_cmb_oferta").combobox('comboautocomplete',null);

}