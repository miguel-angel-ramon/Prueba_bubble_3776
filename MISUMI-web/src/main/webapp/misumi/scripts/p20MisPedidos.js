var centerRequired=null;
var literalPendConf = null;
var literalAjusteCapPlat= null;
var AREA_CONST="area";
var RESET_CONST="reset";
var SECTION_CONST="section";
var codigoArea = "null";
var codigoSeccion = "null";

var contenidoCeldaSuperiorIzquierda1 = "<br><br>Fecha<br/>Area / Sección / Categoría<br/><br/><input type='button' id='gridP20ExpandAll' class='boton  botonHover' style='display: none' value='Expandir todo'/>";
var contenidoCeldaSuperiorIzquierda2 = "<br><br>Fecha<br/>Mapa / Area / Sección<br/><br/><input type='button' id='gridP20ExpandAll' class='boton  botonHover' style='display: none' value='Expandir todo'/>";

var vengoDeOtraRef = 1;
$(document).ready(function(){
	CargadaEstructuraPantalla(2)
	events_p20_btn_reset();
	events_p20_btn_buscar();
	events_p20_rad_tipoFiltro();
	
	$(document).on('CargadoCentro', function(e) { 
		initializeScreen();
		cambioDeRadioButton();
		load_gridP20Tree(locale);
		$("#p20_fld_referencia").focus();
		if (esCentroVegalsa()){
			initVegalsa();
		}else{
			$('input:radio[name=p20_rad_tipoFiltro]')[0].checked = true;
			$('input:radio[name=p20_rad_tipoFiltro]')[1].checked = false;
		}
		
	});	
  });

function esCentroVegalsa(){
	var centerSoc = $('#centerSoc').val();
	return centerSoc == 13;
}

function initVegalsa(){
	$($('input:radio[name=p20_rad_tipoFiltro]')[2]).show();
	$('#p40_lbl_radioMapa').show();
	showVegalsa();
	
	mapasVegalsaCmb.init();
}

function showVegalsa(){
	$("div#p20_filtroMapa").attr("style", "display:inline-block");
	$("div#p20_filtroEstructura").attr("style", "display:none");
	$("div#p20_filtroReferencia").attr("style", "display:none");
	$('input:radio[name=p20_rad_tipoFiltro]')[0].checked = false;
	$('input:radio[name=p20_rad_tipoFiltro]')[1].checked = false;
	$('input:radio[name=p20_rad_tipoFiltro]')[2].checked = true;

}

function hideVegalsa(){
	$('#p20_filtroMapa').hide();
}

function cambioDeRadioButton(){
	if($("#p30_flgMasInfPedidos").val() == 'S'){
		// Referencias
		$('input:radio[name=p20_rad_tipoFiltro]')[1].checked = false;
		$('input:radio[name=p20_rad_tipoFiltro]')[2].checked = true;
		$("div#p20_filtroEstructura").attr("style", "display:none");
		$("div#p20_filtroReferencia").attr("style", "display:inline");
	}
}

function initializeScreen(){
	
	$("#p20_fld_referencia").filter_input({regex:'[0-9]'});
	$("#p20_fld_descripcionRef").attr("disabled", "disabled");
	var result=cleanFilterSelection(RESET_CONST);
	controlCentro();
	checkPestanaOrigen();
}

function events_p20_btn_reset(){
	$("#p20_btn_reset").click(function () {
		if (esCentroVegalsa()){
			mapasVegalsaCmb.reset();
		}
		$("#p20_fld_referencia").val("");
		$("#p20_fld_referencia").focus();
		$("#p20_fld_referencia_tmp").val("");
	   	$("#p20_cmd_seccion").empty();
	   	$("#p20_cmd_categoria").empty();
	   	codigoArea="null";
	   	codigoseccion="null;"
	   	initializeScreen();
		resetResultados();
	});
}

function events_p20_btn_buscar(){
	$("#p20_btn_buscar").click(function () {
		$("#p20_btn_buscar").attr("disabled", "disabled");
		finder();
	});	
}

function events_p20_rad_tipoFiltro(){
    $("input[name='p20_rad_tipoFiltro']").change(function () {
    	var radioValue = $("input[name='p20_rad_tipoFiltro']:checked").val();
    	if ( radioValue == "1"){
    		// Estructura comercial
    		 $("div#p20_filtroEstructura").attr("style", "display: inline-block");
    		 $("div#p20_filtroReferencia").attr("style", "display:none");
    		 $("p20_btn_reset").attr("style", "display:none");
    		 $("#p20_fld_referencia").val("");
    		
    		 // load_cmbArea();
    		 load_cmbSeccion();
    		 resetResultados();
    		 hideVegalsa();
    	} else if (radioValue == 2){ 
    		// Referencias
    		$("div#p20_filtroEstructura").attr("style", "display:none");
   		 	$("div#p20_filtroReferencia").attr("style", "display: inline-block");
   		 	$("p20_btn_reset").attr("style", "display: inline-block");
   		 	$("#p20_cmd_area").empty();
   		 	$("#p20_cmd_seccion").empty();
   		 	$("#p20_cmd_categoria").empty();
   		     // var result= cleanFilterSelection(RESET_CONST);
   		     resetResultados();
   		     $("#p20_fld_referencia").focus();
   		     hideVegalsa();
    	} else {
    		$("#p20_cmd_area").empty();
   		 	$("#p20_cmd_seccion").empty();
   		 	$("#p20_cmd_categoria").empty();
    		$("#p20_fld_referencia").val("");
    		resetResultados();
    		showVegalsa();
    	}
    });
}

function finder(){
	var messageVal=null;
	resetResultados();
	// Centro
	if ($("#centerId").val()==null || $("#centerId").val()==""){
		messageVal = centerRequired;
	}		

	if (messageVal!=null){
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
		$("#p20_btn_buscar").attr("disabled", false);
	}else{
		// Recargamos los datos de la tienda para posteriormente hacer la
		// consulta
		loadData_WsAlbaran();
	}
}


function resetResultados (){
	$("#p20_fld_descripcionRef").val("");
	$("#p20_lbl_descripcionRef").attr("style", "display:none");
	$("#p20_fld_descripcionRef").attr("style", "display:none");
	$('#gridP20').jqGrid('clearGridData');
	$("#gridP20").jqGrid('GridUnload');
	$("#p20AreaErrores").attr("style", "display:none");
	$("#p20AreaResultados").attr("style", "display:none");
}

function load_gridP20Tree(locale){
	grid = new GridP20(locale);
	
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
				grid.marcaDiaHoy = data.marcaDiaHoy;
				grid.formatoFecha = data.formatoFecha;
				literalPendConf = data.literalPendConf;
				literalAjusteCapPlat = data.literalAjusteCapPlat;
				if  ($("#p20_fld_referencia").val()!=""){
					finder();
				}
				
				
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
        });
}

function controlCentro(){
	$( "#centerName" ).bind('focus', function() {
		$("#centerName").val('');
		if ($("#centerName").val()==null || $("#centerName").val()==''){
			// Tenemos que resetear lo que hubiese buscado.
			resetResultados();
			$("#p20_fld_referencia").val("");
			$("#p20_fld_referencia_tmp").val("");
		}
    });
}

function loadData_WsAlbaran(){
	var center = $("#centerId").val();
	var ref = $("#p20_fld_referencia").val();
	$.ajax({
		type : 'POST',
		url : './misPedidos/loadWsAlbaran.do?centerId='+center+'&referencia='+ref,
		datatype : 'json',
		contentType : "application/json; charset=utf-8",
		cache : false,
		success : function(data) {
			// Comprobación de carga de datos de la tienda. Si hay error se
			// mostrará un mensaje indicando que no se han podido
			// recargar los datos de la tienda. Aún así se realizarán las
			// consultas con lo que ya está recargado con anterioridad en BD.
			if (data != null && data != '')
			{
				if (data.codError == 0){
					$("#p20AreaErrores").attr("style", "display:none");
				}else{
					$("#p20AreaErrores").attr("style", "display:block");
				}
					
			}else{
				$("#p20AreaErrores").attr("style", "display:none");
			}
			
			// Actualizamos el valor de la referencia introducida por el usuario
			// con la que tenemos en el hidden del jsp.
			$("#p20_fld_referencia_tmp").val($("#p20_fld_referencia").val());
			
			// Se rellenan los dos campos hidden con la referencia eroski y
			// caprabo.
			$("#p20_RefEroski").val(data.codArt);
			$("#p20_RefCaprabo").val(data.codArtATransformar);
			
			loadData_gridP20(grid);
			loadData_DescReferencia();
			$("#p20AreaResultados").attr("style", "display:block");
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});
		
}

function loadData_DescReferencia(grid){
	
	var vdatosdiarioart=new VDatosDiarioArt($("#p20_fld_referencia").val(), null, null, null, null, null, null, null, null);
	var objJson = $.toJSON(vdatosdiarioart);	
	 $.ajax({
			type : 'POST',
			url : './misPedidos/loadDescReferencia.do',
			data : objJson,
			contentType : "application/json; charset=utf-8",
			cache : false,
			success : function(data) {
					if (data != null && data != '')
					{
						controlDatosTimeout(data);
						$("#p20_lbl_descripcionRef").attr("style", "display:inline-block");
						$("#p20_fld_descripcionRef").attr("style", "display:inline-block");
						$("#p20_fld_descripcionRef").val(data);
					}
			},
			error : function (xhr, status, error){
				handleError(xhr, status, error, locale);
	        }			
		});
}

function loadData_gridP20(grid){
	var radioVal = $("input[name='p20_rad_tipoFiltro']:checked").val();
	var centro = $("#centerId").val();
	var referencia = $("#p20_RefEroski").val();
	var referenciaATransformar = $("#p20_RefCaprabo").val();
		
	if (radioVal == "1"){
		var categoria = $("#p20_cmb_categoria").combobox('getValue');
		var url = './misPedidos/loadAllData.do?centerId='+centro+'&referencia='+referencia+'&referenciaATransformar='+referenciaATransformar+'&area='+codigoArea+'&seccion='+codigoSeccion+'&categoria='+categoria;
		loadGrid(url);
	}else if (radioVal == 2){
		var url = './misPedidos/loadAllData.do?centerId='+centro+'&referencia='+referencia+'&referenciaATransformar='+referenciaATransformar;
		loadGrid(url);
	}else{
		var mapa = mapasVegalsaCmb.getValue();
		var url = './misPedidos/loadAllData.do?centerId='+centro+'&referencia='+referencia+'&referenciaATransformar='+referenciaATransformar+'&mapa='+mapa;
		loadGrid(url);
	}
	
}

function loadGrid(url){
	$(grid.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		// url: './misumi/resources/p20MisPedidos/p20mockMisPedidos.json',
		url : url,
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
					     "n_level": postData.n_level
						}; 
	           return JSON.stringify(jsonObject);
		},
		mtype : 'POST',
		 
	    treeGrid: true,
	    treeGridModel: 'adjacency', // nested o adjacency
	    ExpandColClick : true, // para permitir que se pueda expandir/colapsar
								// pulsando en la columna
	    ExpandColumn : 'fecha', 
	    // See http://jquery.keicode.com/ui/icons.php
	    // treeIcons:
		// {plus:'ui-icon-triangle-1-e',minus:'ui-icon-triangle-1-s',leaf:'ui-icon-document'},
	    treeIcons: {plus:'ui-icon-circle-plus',minus:'ui-icon-minusthick',leaf:'ui-icon-document'},	    
	    colNames : grid.colNames, 
	    colModel :[ 
	         {name:'descripcionNivel', index:'descripcionNivel', width:'205', resizable: false, sortable:false, formatter: descripcionFormatter}, 
	         {name:'hoy', index:'hoy', width:'190', resizable: false, sortable:false, formatter: cantPedidasFormatter},
	         {name:'nsr', index:'nsr', width:'190', resizable: false, sortable:false, formatter: nsrFormatter},
	         {name:'confirm', index:'confirm', width:'190', resizable: false, sortable:false, formatter: confirmFormatter},
	         {name:'gisae', index:'gisae', width:'190', resizable: false, sortable:false, formatter: gisaeFormatter}
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
		altRows: true, // false, para que el grid no muestre cebrado
		hidegrid : false, // false, para ocultar el boton que colapsa el grid.
        width: 'auto',
        height: 'auto',
        gridComplete : function() {			

        	grid.headerHeight("p20_gridMisPedidosHeader");     	
			
			$("td[aria-describedby=gridP20_fecha]").css("border-bottom-width", "0px"); // Para
																						// borrar
																						// el
																						// borde
																						// inferior
																						// de
																						// las
																						// celdas
																						// de
																						// la
																						// primera
																						// columna
			$("td[aria-describedby=gridP20_fecha]").css("border-left-width", "0px"); // Para
																						// borrar
																						// el
																						// borde
																						// izquierdo
																						// de
																						// las
																						// celdas
																						// de
																						// la
																						// primera
																						// columna
			
			$("div[class='tree-wrap tree-wrap-ltr']").css("padding-top", "10px"); // Para
																					// centrar
																					// los
																					// iconos
																					// de +
																					// y -
																					// de
																					// las
																					// celdas
																					// de
																					// la
																					// primera
																					// columna
			$("div[id=gbox_gridP20]").css("border", "0 solid #AAAAAA"); // Para
																		// borrar
																		// el
																		// borde
																		// exterior
																		// de
																		// toda
																		// la
																		// tabla
			
			$("#gridP20ExpandAll").click(function() {				
				grid.expandirContraerTodo("gridP20ExpandAll");				
			});
			$("#p20AreaResultados").attr("style", "display:block");
			var radioValue = $("input[name='p20_rad_tipoFiltro']:checked").val();
			if (radioValue==3){
				$('#jqgh_gridP20_descripcionNivel').html(contenidoCeldaSuperiorIzquierda2);
			}else{
				$('#jqgh_gridP20_descripcionNivel').html(contenidoCeldaSuperiorIzquierda1);
			}
			
		},
		loadComplete: function (rowid) {
			var ids = jQuery("#gridP20").jqGrid('getDataIDs');
			$("#p20_btn_buscar").attr("disabled", false);
			if (ids == '')
			{
				createAlert(replaceSpecialCharacters(grid.emptyRecordsSelecc), "ERROR");
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
        		var mapa = null;
        		var descrMapa = null;
        		if (esCentroVegalsa()){
        			mapa = $("#p20_gridCodMapa" + rowid).val();
        			descrMapa = $("#p20_gridDescMapa" + rowid).val();
        		}
        		
	        	var seguimientoMiPedido = new SeguimientoMiPedido($("#centerId").val() , $("#p20_gridFechaPrevisEnt" + rowid).val(), $("#p20_gridFechaFormateadaPrevisEnt" + rowid).val(), $("#p20_gridCodArea" + rowid).val(), $("#p20_gridDescArea" + rowid).val(), $("#p20_gridCodSeccion" + rowid).val(), $("#p20_gridDescSeccion" + rowid).val(), $("#p20_gridCodCategoria" + rowid).val(), $("#p20_gridDescCategoria" + rowid).val(), "",$("#p20_RefEroski").val(),mapa,descrMapa);
	        	if (iCol == 1){
	        		reloadDataP21(seguimientoMiPedido);
	        	}
	        	if (iCol == 2){
	        		reloadDataP22(seguimientoMiPedido);
	        	}
	        	if (iCol == 3){
	        		reloadDataP23(seguimientoMiPedido);
	        	}
	        	if (iCol == 4){
	        		reloadDataP24(seguimientoMiPedido);
	        	}
        	}
        }
		
    });
}

function descripcionFormatter(cellvalue, options, rowObject) {
	var cellData = cellvalue.split("**");
    var formattedCell = "";

    var fechaPrevistaEntDDMMYYYY = rowObject.fechaPrevisEntDDMMYYYY;
	var diaFechaSt = fechaPrevistaEntDDMMYYYY.substring(0,2);
	var mesFechaSt = fechaPrevistaEntDDMMYYYY.substring(2,4);
	var anyoFechaSt = fechaPrevistaEntDDMMYYYY.substring(4);
 
	var diaFecha = parseInt(diaFechaSt,10);
	var mesFecha = parseInt(mesFechaSt,10);
	var anyoFecha = parseInt(anyoFechaSt,10);
    var fechaPrevistaEntFormateada = $.datepicker.formatDate(grid.formatoFecha, new Date(anyoFecha, mesFecha - 1, diaFecha));
    
    var codArea = "";
    var descArea = "";
    var codSeccion = "";
    var descSeccion = "";
    var codCategoria = "";
    var descCategoria = "";

    var codMapa = "";
    var descMapa = "";
    
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
    	 var descNivel = rowObject.descripcionNivel.split("**")[0];
    	 var radioValue = $("input[name='p20_rad_tipoFiltro']:checked").val();
    	 var cmbMapa = mapasVegalsaCmb.getValue();
    	 
    	 /*******************************************************************
			 * MISUMI-366
			 * 
			 * @author BICUGUAL
			 */
    	 if (( (rowObject.level > 0) || (rowObject.level == 0 && cmbMapa!="0")) && radioValue==3){
    		 codMapa = rowObject.mapa;
    		 if (rowObject.descMapa!=null){
    			 descMapa = rowObject.descMapa
    		 }
    	 }
     } 
     
	 if (rowObject.level == 0||rowObject.level == 9){
		if (cellData[1]!=null && cellData[1]=="1"){
			// formattedCell = "<span
			// class=\"p20_gridMisPedidosColum0Texto1Resaltado\">" +
			// fechaPrevistaEntFormateada + " " + grid.marcaDiaHoy + "</span>";
			formattedCell = "<p class=\"p20_gridMisPedidosColum0Texto1Hoy\">" + fechaPrevistaEntFormateada + "<span class=\"p20_gridMisPedidosColum0Texto1Resaltado\">" + grid.marcaDiaHoy + "</span></p>";
		}else{
			formattedCell = "<span class=\"p20_gridMisPedidosColum0Texto1\">" + fechaPrevistaEntFormateada + "</span>";
	 	}
	 }else if (rowObject.level == 1){
	 	formattedCell = "<span class=\"p20_gridMisPedidosColum0Texto2\">" + cellData[0] + "</span>";
	 }else if (rowObject.level == 2){
	 	formattedCell = "<span class=\"p20_gridMisPedidosColum0Texto3\">" + cellData[0] + "</span>";
	 }else if (rowObject.level == 3){
	 	formattedCell = "<span class=\"p20_gridMisPedidosColum0Texto4\">" + cellData[0] + "</span>";
	 }
	 formattedCell = formattedCell + "<input type=\"hidden\" id=\"p20_gridFechaPrevisEnt" + rowObject.id + "\" value=\"" + fechaPrevistaEntDDMMYYYY + "\"/>";
	 formattedCell = formattedCell + "<input type=\"hidden\" id=\"p20_gridFechaFormateadaPrevisEnt" + rowObject.id + "\" value=\"" + fechaPrevistaEntFormateada + "\"/>";
	 formattedCell = formattedCell + "<input type=\"hidden\" id=\"p20_gridCodArea" + rowObject.id + "\" value=\"" + codArea + "\"/>";
	 formattedCell = formattedCell + "<input type=\"hidden\" id=\"p20_gridDescArea" + rowObject.id + "\" value=\"" + descArea + "\"/>";
	 formattedCell = formattedCell + "<input type=\"hidden\" id=\"p20_gridCodSeccion" + rowObject.id + "\" value=\"" + codSeccion + "\"/>";
	 formattedCell = formattedCell + "<input type=\"hidden\" id=\"p20_gridDescSeccion" + rowObject.id + "\" value=\"" + descSeccion + "\"/>";
	 formattedCell = formattedCell + "<input type=\"hidden\" id=\"p20_gridCodCategoria" + rowObject.id + "\" value=\"" + codCategoria + "\"/>";
	 formattedCell = formattedCell + "<input type=\"hidden\" id=\"p20_gridDescCategoria" + rowObject.id + "\" value=\"" + descCategoria + "\"/>";
	 // MISUMI-299
	 formattedCell = formattedCell + "<input type=\"hidden\" id=\"p20_gridCodMapa" + rowObject.id + "\" value=\"" + codMapa + "\"/>";
	 formattedCell = formattedCell + "<input type=\"hidden\" id=\"p20_gridDescMapa" + rowObject.id + "\" value=\"" + descMapa + "\"/>";

    return formattedCell;
}


function cantPedidasFormatter(cellvalue, options, rowObject) {
    var cellData = cellvalue.split("**");
	
	var formattedCell = "";
	var permisoAjustePedidos = $("#p20_permiso_ajuste_pedidos").val();
	
	formattedCell += "<span class=\"p20_gridMisPedidosColum1Texto1\">" + cellData[0] + " / " + cellData[1] + "</span>";	
	if (rowObject.cajasCortadas != 0 && permisoAjustePedidos === 'true' ){
		formattedCell += "<span class=\"p20_gridMisPedidosColum1Texto3\">" + literalAjusteCapPlat + "</span>";
	}
	        
    return formattedCell;
}

function nsrFormatter(cellvalue, options, rowObject) {
    var cellData = cellvalue.split("**");
	
	var formattedCell = "";
	
	formattedCell += "<span class=\"p20_gridMisPedidosColum2Texto1\">" + cellData[0] + " / " + cellData[1] + "</span>";	
	        
    return formattedCell;
}

function confirmFormatter(cellvalue, options, rowObject) {
    var cellData = cellvalue.split("**");
	
	var formattedCell = "";
	
	formattedCell += "<span class=\"p20_gridMisPedidosColum1Texto1\">" + cellData[0] + " / " + cellData[1] + "</span>";	
	        
    return formattedCell;
}

function gisaeFormatter(cellvalue, options, rowObject) {
	
    var cellData = cellvalue.split("**");
	
	var formattedCell = "";
	
	formattedCell += "<span class=\"p20_gridMisPedidosColum1Texto1\">" + cellData[0] + " / " + cellData[1] + "</span>";
	if ((cellData[0] - cellData[1] > 0)){ // Hay albaranes sin confirmar
		var fechaEnt = rowObject.fechaPrevisEntDDMMYYYY;
		var fechaEntDate = new Date(fechaEnt.substring(4), fechaEnt.substring(2,4) - 1, fechaEnt.substring(0,2));
		var fechaHoy = new Date();
		fechaHoy.setHours(0,0,0,0);
		if(fechaHoy.getTime() > fechaEntDate.getTime()){
			formattedCell += "<span class=\"p20_gridMisPedidosColum4Texto2\">"+literalPendConf+"</span>";
		}
	}   
    return formattedCell;
}



/* Clase de constantes para el TREE-GRID */
function GridP20 (locale){
	// Atributos
	this.name = "gridP20"; 
	this.nameJQuery = "#gridP20"; 
	this.i18nJSON = './misumi/resources/p20MisPedidos/p20MisPedidos_' + locale + '.json';
	this.colNames = null; // Esta en el json
	
	this.locale = locale;
	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = null; // "pagerP16Movimientos";
	this.pagerNameJQuery = null; // "#pagerP16Movimientos";
	this.title = null; // Esta en el json
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null; // Esta en el json
	this.rownumbers = false;
	this.expandirTodo = "";
	this.contraerTodo = "";
	this.marcaDiaHoy = "";
	this.formatoFecha = "";
	
	// Metodos
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
	} ;
	
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
			$("#gridP20").setLabel(colModel[i].name, colNames[i], cssClass);
		}
		
	}
	
	this.expandirContraerTodo = function expandirContraerTodo(btnName){
		var jqBtnName = "#" + btnName; 
		if ($(jqBtnName).val() == this.expandirTodo){
			$(".treeclick","table#gridP20").each(function() {
				if($(this).hasClass("tree-plus")) {
					$(this).trigger("click");
				}
			});	
			$(jqBtnName).val(this.contraerTodo);
		} else {
			$(".treeclick","table#gridP20").each(function() {
				if($(this).hasClass("tree-minus")) {
					$(this).trigger("click");
				}
			});
			$(jqBtnName).val(this.expandirTodo);
		}				
	}
		
}


function load_cmbArea(){	
	
	var options = "";
	var optionNull = "";
	// $( "#p20_cmb_area" ).prepend("<option value=null
	// selected='selected'>"+optionNull+"</option>");
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
			// options = "<option value='null'
			// selected='selected'>"+optionNull+"</option>";
			   for (i = 0; i < data.length; i++){
					options = options + "<option value='" + data[i].grupo1 + "'>" + formatDescripcionCombo(data[i].grupo1, data[i].descripcion) + "</option>"; 
			   }
			   $("select#p20_cmb_area").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
		$("#p20_cmb_area").combobox({
	        selected: function(event, ui) {
	        	if ( ui.item.value!="" && ui.item.value!="null" ) {
		           if ($("#p20_cmb_area").val()!=null){
// $("#p20_cmb_area").combobox('autocomplete',ui.item.label);
// $("#p20_cmb_area").combobox('comboautocomplete',ui.item.value);
		        	   
		        	   load_cmbSeccion();
		        	   var result = cleanFilterSelection(AREA_CONST);

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
	
	$("#p20_cmb_area").combobox('autocomplete',optionNull);
	$("#p20_cmb_area").combobox('comboautocomplete',null);

}

function load_cmbSeccion(){	
	var options = "";
	var optionNull = "";
	$("#p20_cmb_seccion").combobox(null);
	// $( "#p20_cmb_seccion" ).prepend("<option value='null'
	// selected='selected'>"+optionNull+"</option>");
	var vAgruComerRef=new VAgruComerRef("I2"/* ,$("#p20_cmb_area").val() */);
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
					options = options + "<option value='"+ data[i].grupo1 +"*"+ data[i].grupo2 + "'>" + formatDescripcionCombo(data[i].grupo2, data[i].descripcion) + "</option>"; 
			   }
			   $("select#p20_cmb_seccion").html(options);
			   
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
		$("#p20_cmb_seccion").combobox({
	        selected: function(event, ui) {
	        	
	         if ( ui.item.value!="" && ui.item.value!="null") {
		           if ($("#p20_cmb_seccion").val()!=null){
		        	   load_cmbCategory();
		        	   var result=cleanFilterSelection(SECTION_CONST);
		        	
		           }
	         }  else{
	        	   // var result=cleanFilterSelection(AREA_CONST);
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
	
	$("#p20_cmb_seccion").combobox('autocomplete',optionNull);
	$("#p20_cmb_seccion").combobox('comboautocomplete',null);
}

function load_cmbCategory(){		
	var options = "";
	var optionNull = "";
	var valorSeccion = $("#p20_cmb_seccion").val().split('*');
	codigoArea = valorSeccion[0];
	codigoSeccion = valorSeccion[1];
	$("#p20_cmb_categoria").combobox(null);
		var vAgruComerRef=new VAgruComerRef("I3",codigoArea,codigoSeccion);
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
			   $("select#p20_cmb_categoria").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
		
	$("#p20_cmb_categoria").combobox('autocomplete',optionNull);
	$("#p20_cmb_categoria").combobox('comboautocomplete',null);
	
	
}

var mapasVegalsaCmb = {
		idHTML : "p20_cmb_mapa",
		idJquery : "#p20_cmb_mapa",
		populate : function() {
			$.ajax({
				type : 'GET',
				url : './calendarioVegalsa/loadMapasCombo.do',
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				cache : false,
				success : function(data) {
					if (data != null) {
						options = "<option value='0'>SIN SELECCIÓN</option>";

						data.forEach(function(item, indice, array) {
							options = options + "<option value='" + item.codigo
									+ "'>" + item.descripcion + "</option>";
						});

						// Ponemos la opción TODOS por defecto.
						$(mapasVegalsaCmb.idJquery).combobox('autocomplete',
								"SIN SELECCIÓN");
						$(mapasVegalsaCmb.idJquery).combobox('comboautocomplete',
								"null");
						$("select" + mapasVegalsaCmb.idJquery).html(options);
					}
				},
				error : function(xhr, status, error) {
					handleError(xhr, status, error, locale);
				}
			});

		},
		reset: function(){
			$(mapasVegalsaCmb.idJquery).combobox('autocomplete', "SIN SELECCIÓN");
			$(mapasVegalsaCmb.idJquery).combobox('comboautocomplete',"0");
		},
		getValue: function(){
			return $(mapasVegalsaCmb.idJquery).val();
		},
		getText: function(){
			return $(mapasVegalsaCmb.idJquery + ' option:selected').text();
		},
		onSelect: function(){
			// Evento de seleccion del combo
			$(this.idJquery).combobox({
				selected : function(event, ui) {
					if (ui.item.value != "0" && ui.item.value != "null") {

						// TODO - LO QUE HACE EL COMBO
					}
				}
			});
		},
		init : function() {
			this.populate();
			this.onSelect();
		}
	}

function cleanFilterSelection(componentName){
	
	
	if (componentName == RESET_CONST){
		$("#p20_cmb_area").combobox(null);
		$("select#p20_cmb_area").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p20_cmb_area").combobox('autocomplete',"");
		$("#p20_cmb_area").combobox('comboautocomplete',null);
		$("#p20_cmb_seccion").combobox(null);
		$("#p20_cmb_categoria").combobox(null);
		if ($("#centerId").val()!=null && $("#centerId").val()!=''){
			// load_cmbArea();
			load_cmbSeccion();
		}
	}
	if (componentName == AREA_CONST || componentName == RESET_CONST){
// $("#p20_cmb_seccion").combobox(null);
		$("select#p20_cmb_seccion").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p20_cmb_seccion").combobox('autocomplete','');
		$("#p20_cmb_seccion").combobox('comboautocomplete',null);

		
	}
	if(componentName==SECTION_CONST || componentName==AREA_CONST || componentName == RESET_CONST){
		
	// $("#p20_cmb_categoria").combobox(null);
		$("select#p20_cmb_categoria").html("<option value=null selected='selected'>"+''+"</option>");
		$("#p20_cmb_categoria").combobox('autocomplete','');
		$("#p20_cmb_categoria").combobox('comboautocomplete',null);

		
	}
	
	disableFilterSelection(componentName);
	return true;
}

function disableFilterSelection(componentName){
	if (componentName == RESET_CONST){
		if ($("#centerId").val()!=null && $("#centerId").val()!=''){
			$("#p20_cmb_seccion").combobox("enable");

		}else{
			$("#p20_cmb_seccion").combobox("disable");
		}
		$("#p20_cmb_categoria").combobox("disable");
		
	}else if (componentName == AREA_CONST){
		$("#p20_cmb_seccion").combobox("enable");
		$("#p20_cmb_categoria").combobox("disable");
	
		
	} else	if(componentName==SECTION_CONST ){
		$("#p20_cmb_categoria").combobox("enable");
	
	}
	
}


/**
 * MISUMI-674
 * Comprueba que el parametro PestanaOrigen exista en la url y tenga el valor Welcome.
 * Si es asi, lanza buscar sin parametros seleccionados.
 */
function checkPestanaOrigen (){
	// Obtenemos los parámetros de la URL actual
	const params = new URLSearchParams(window.location.search);
	
	// Comprobamos si existe con el valor concreto
	if (params.get("pestanaOrigen") === "Welcome") {
	  $("#p20_btn_buscar").click();
	}	
}
