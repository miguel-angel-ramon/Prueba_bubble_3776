var gridP43PedidoAdicionalMO = null;
var p43Seleccionados = new Array();
var p43SeleccionadosTotal = new Array();
var p43esTecnico = null;
var p43emptyListaSeleccionados = null;
var iconoModificado=null;
var errorModificacion=null;
var errorModificacionFechas=null;
var errorModificacionNoModif=null;
var errorModificacionNoMasiva=null;
var errorModificacionValFecInicio=null;
var errorBorrado=null;
var errorBorradoNoBorrable=null;
var tableFilter=null;
var tipoMontaje=null;
var textoFechaInicio=null;
var textoImplInicial= null;
var textoImplFinal=null;
var p43BotonGuardar= null;
var p43BotonVolver= null;
var p43EstadoNoActiva = null;
var p43ConsultaMotivosBloqueo = null;
var p43CmbOfertaTodos = null;
var p43CmbPromocionTodos = null;
var p43DescPeriodo = null
var p43EspacioPromo = null

function initializeP43(){
	initializeScreenPedidoAdicionalMO();
	//loadP43PedidoAdicionalMO(locale);
}
function initializeScreenPedidoAdicionalMO(){
	
	
	$("#p43_cmb_oferta").combobox(null);
	$("#p43_cmb_oferta").combobox({
        selected: function(event, ui) {
        	if (null != ui.item.value && ui.item.value != ""){
				var comboOferta = ui.item.value;
				p43DescPeriodo = comboOferta;
        	} else {
        		p43DescPeriodo = null;
        	}
  
        	reloadDataP43PedidoAdicionalMO('N','','',null,'');  
        }  
        ,	
        changed: function(event, ui) { 
        	if (null != ui.item && null != ui.item.value && ui.item.value != ""){
        		var comboOferta = ui.item.value;
        		p43DescPeriodo = comboOferta;
        	} else {
        		p43DescPeriodo = null;
        	}
        	
        	reloadDataP43PedidoAdicionalMO('N','','',null,'');
		   }
     });
	$("#p43_cmb_oferta").combobox('autocomplete',p43CmbOfertaTodos);
	
	$("#p43_cmb_promocion").combobox(null);
	$("#p43_cmb_promocion").combobox({
        selected: function(event, ui) {
        	if (null != ui.item.value && ui.item.value != ""){
				var comboPromocion = ui.item.value;
				p43EspacioPromo = comboPromocion;
        	} else {
        		p43EspacioPromo = null;
        	}
        	
        	reloadDataP43PedidoAdicionalMO('N','','',null,'');  
        }  
        ,	
        changed: function(event, ui) { 
        	if (null != ui.item && null != ui.item.value && ui.item.value != ""){
        		var comboPromocion = ui.item.value;
        		p43EspacioPromo = comboPromocion;
        	} else {
        		p43EspacioPromo = null;
        	}
        	
        	reloadDataP43PedidoAdicionalMO('N','','',null,'');
		   }
     });
	$("#p43_cmb_promocion").combobox('autocomplete',p43CmbPromocionTodos);
	
	loadP43PedidoAdicionalMO(locale);
}



function resetDatosP43(){
	$('#gridP43MontajeAdicionalOferta').jqGrid('clearGridData');
}

function reloadMontajeOferta() {
	$("#p40_pestanaMontajeOfertaCargada").val("S");
	reloadDataP43PedidoAdicionalMO( 'N','','',null,'');
	
}

function p43SetHeadersTitles(data){
	
   	var colModel = $(gridP43PedidoAdicionalMO.nameJQuery).jqGrid("getGridParam", "colModel");
    $.each(colModel, function(i) {
    	if (colModel[i].name!="rn"){
	    	$("#jqgh_gridP43MontajeAdicionalOferta_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
    	}
     });
}

function loadP43PedidoAdicionalMO(locale) {
	gridP43PedidoAdicionalMO = new GridP43PedidoAdicionalMO(locale);
	
	var jqxhr = $.getJSON(gridP43PedidoAdicionalMO.i18nJSON, function(data) {

	}).success(function(data) {
		gridP43PedidoAdicionalMO.colNames = data.p43PedidoAdicionalMOColNames;
		gridP43PedidoAdicionalMO.title = data.p43PedidoAdicionalMOGridTitle;
		gridP43PedidoAdicionalMO.emptyRecords = data.emptyRecords;
		p43emptyListaSeleccionados = data.emptyListaSeleccionados;
		iconoModificado=data.iconoModificado;
		errorModificacion=data.errorModificacion;
		errorModificacionFechas=data.errorModificacionFechas;
		errorModificacionNoModif=data.errorModificacionNoModif;
		errorModificacionNoMasiva=data.errorModificacionNoMasiva;
		errorModificacionValFecInicio=data.errorModificacionValFecInicio;
		errorBorrado=data.errorBorrado;
		errorBorradoNoBorrable=data.errorBorradoNoBorrable;
		tableFilter= data.tableFilter;
		tipoMontaje= data.tipoMontaje;
		textoFechaInicio= data.textoFechaInicio;
		textoImplInicial=data.textoImplInicial;
		textoImplFinal=data.textoImplFinal;
		p43ComboSI= data.p43ComboSI;
		p43ComboNO= data.p43ComboNO;
		p43BotonGuardar= data.p43BotonGuardar;
		p43BotonVolver= data.p43BotonVolver;
		p43EstadoNoActiva = data.p43EstadoNoActiva;
		p43ConsultaMotivosBloqueo = data.p43ConsultaMotivosBloqueo;
		p43CmbOfertaTodos = data.p43CmbOfertaTodos;
		p43CmbPromocionTodos = data.p43CmbPromocionTodos;
		
		
		loadP43PedidoAdicionalMOMock(gridP43PedidoAdicionalMO);
		p43SetHeadersTitles(data);
		
	}).error(function(xhr, status, error) {
		handleError(xhr, status, error, locale);
	});
}



function loadP43PedidoAdicionalMOMock(gridP43PedidoAdicionalMO) {
	
	$(gridP43PedidoAdicionalMO.nameJQuery).jqGrid(
			{
				ajaxGridOptions : {
					contentType : 'application/json; charset=utf-8',
					cache : false
				},
				datatype : 'local',
				contentType : 'application/json',
				mtype : 'POST',
				colNames : gridP43PedidoAdicionalMO.colNames,
				colModel : gridP43PedidoAdicionalMO.cm,
				rowNum : 10,
				rowList : [ 10, 20, 30 ],
				height : "auto",
				autowidth : true,
				width : "auto",
				rownumbers : true,
				pager : gridP43PedidoAdicionalMO.pagerNameJQuery,
				viewrecords : true,
				caption : gridP43PedidoAdicionalMO.title,
				altclass : "ui-priority-secondary",
				altRows : true, // false, para que el grid no muestre cebrado
				hidegrid : false, // false, para ocultar el boton que colapsa
									// el grid.
				sortable : true,
				multiselect : true,
				index : gridP43PedidoAdicionalMO.sortIndex,
				sortname : gridP43PedidoAdicionalMO.sortIndex,
				sortorder : gridP43PedidoAdicionalMO.sortOrder,
				emptyrecords : gridP43PedidoAdicionalMO.emptyRecords,
				gridComplete : function() {

				},
				loadComplete : function(data) {
					
					gridP43PedidoAdicionalMO.actualPage = data.page;
					gridP43PedidoAdicionalMO.localData = data;
					gridP43PedidoAdicionalMO.sortIndex = null;
					gridP43PedidoAdicionalMO.sortOrder = null;
					if (gridP43PedidoAdicionalMO.firstLoad)
						jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setGridWidth',$("#p43_AreaMontajeAdicionalOferta").width(),true);	
					
					// Ocultamos la check de seleccionar todos.
					$("#cb_" + gridP43PedidoAdicionalMO.name).attr("style",
							"display:none");
				},
				onPaging : function(postdata) {
					alreadySorted = false;
					gridP43PedidoAdicionalMO.sortIndex = null;
					gridP43PedidoAdicionalMO.sortOrder = null;
					gridP43PedidoAdicionalMO.saveColumnState.call($(this),
							this.p.remapColumns);
					
					reloadDataP43PedidoAdicionalMO('S','','',null,'');

					return 'stop';
				},
				beforeSelectRow: function (rowid, e) {
					 var $myGrid = $(this),
				        i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),
				        cm = $myGrid.jqGrid('getGridParam', 'colModel');
					 	if (!(cm[i].name === 'cb') && !(cm[i].name === 'mensaje' && $("#"+(rowid)+"_estadoMO").val()=== 'NOACT'))
				    	{
					 		var idVegalsa=$("#"+(rowid)+"_identificadorVegalsaMO").val();
				    		p43ModificarReferencia($("#"+(rowid)+"_clasePedidoMO").val(),$("#"+(rowid)+"_codArticuloMO").val(),$("#"+(rowid)+"_esPlanogramaMO").val(),$("#"+(rowid)+"_identificadorMO").val(),$("#"+(rowid)+"_fechaInicioMO").val(), $("#"+(rowid)+"_noGestionaPblMO").val(), $("#"+(rowid)+"_identificadorSIAMO").val(), idVegalsa);
				    	}
					 	
						filaBorrable = $("#"+(rowid)+"_borrableMO").val();
						perfil = $("#"+(rowid)+"_perfilMO").val();
						clasePedido = $("#"+(rowid)+"_clasePedidoMO").val();
						var modificableIndiv = $("#"+(rowid)+"_modificableIndivMO").val();
						var noGestionaPbl = $("#"+(rowid)+"_noGestionaPblMO").val();
						
						//Se permite el check si es de tipo Q o T
						var ultimoCaracterModifIndiv = "";
						if (modificableIndiv != null && modificableIndiv != ""){
							ultimoCaracterModifIndiv = modificableIndiv.substring(modificableIndiv.length - 1);
						}

				    return (cm[i].name === 'cb' && ((clasePedido=="8" && filaBorrable != "N") || (clasePedido!="8" && (ultimoCaracterModifIndiv == 'Q' || ultimoCaracterModifIndiv == 'T' || ((filaBorrable != "N") && (perfil == "3")) || (noGestionaPbl != null && noGestionaPbl =="S" && modificableIndiv.indexOf("S") >= 0)))));
				},
				onSortCol : function(index, columnIndex, sortOrder) {
					gridP43PedidoAdicionalMO.sortIndex = index;
					gridP43PedidoAdicionalMO.sortOrder = sortOrder;
					gridP43PedidoAdicionalMO.saveColumnState.call($(this),
							this.p.remapColumns);
					reloadDataP43PedidoAdicionalMO('S','','',null);
					return 'stop';
				},
				jsonReader : {
					root : "rows",
					page : "page",
					total : "total",
					records : "records",
					repeatitems : false
				},
				loadError : function(xhr, status, error) {
					handleError(xhr, status, error, locale);
				}
			});
	
	jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('navGrid',gridP43PedidoAdicionalMO.pagerNameJQuery,{
		add:false,edit:false,del:false,search:false,refresh:false}
	); 
	
	
	
	jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('navButtonAdd',gridP43PedidoAdicionalMO.pagerNameJQuery,{ 
		caption: tableFilter, title: "Reordenar Columnas", 
	onClickButton : function (){ 
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('columnChooser',
				{
            		"done": function(perm) {
            			if (perm) {
            				var autowidth = true;
            				if (gridP43PedidoAdicionalMO.modificado == true){
            					autowidth = false;
            					gridP43PedidoAdicionalMO.myColumnsState =  gridP43PedidoAdicionalMO.restoreColumnState(gridP43PedidoAdicionalMO.cm);
                		    	gridP43PedidoAdicionalMO.isColState = typeof (gridP43PedidoAdicionalMO.myColumnsState) !== 'undefined' && gridP43PedidoAdicionalMO.myColumnsState !== null;
                		    	this.jqGrid("remapColumns", perm, true);
                		    	 var colModel =jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('getGridParam', 'colModel'); 
                		    	 var l = colModel.length;
                		         var colItem; 
                		         var cmName;
                		         var colStates = gridP43PedidoAdicionalMO.myColumnsState.colStates;
                		         var cIndex = 2;
                		         for (i = 0; i < l; i++) {
                		             colItem = colModel[i];
                		             cmName = colItem.name;
                		             if (colItem.hidden !== true && cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {
                		            	 
                		            	 jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setColProp',cmName,{width:colStates[cmName].width});
                		            	 var cad =gridP43PedidoAdicionalMO.nameJQuery+'_'+cmName;
                		            	 var ancho = "'"+colStates[cmName].width+"px'";
                		            	 var cell = jQuery('table'+gridP43PedidoAdicionalMO.nameJQuery+' tr.jqgfirstrow td:nth-child(' + (i+1) + ')');
                		            	 cell.css("width", colStates[cmName].width + "px");
                		            	
                		            	 jQuery(cad).css("width", colStates[cmName].width + "px");
                		            	
                		             }
                		         }
                		         
            				} else {
            					this.jqGrid("remapColumns", perm, true);
            				}
            				gridP43PedidoAdicionalMO.saveColumnState.call(this, perm);
            				jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setGridWidth', $("#p43_AreaMontajeAdicionalOferta").width(), autowidth);
            			}
            		}
				}		
		); } });
	
	
}

function p43FormateoDate(cellValue, opts, rowObject) {

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

function p43ImageFormatMessage(cellValue, opts, rData) {

	var imagen = "";
	var mostrarError = "none;";
	var descError = "";
	var mostrarModificado = "none;";
	var mostrarNoActiva = "none;";
	var p43EstadoNoActivaValor = "";
	
	//Controlamos los posibles errores que me lleguen para pintar el icono correspondiente.
	if (parseFloat(rData['codError']) == '1')
	{
		//Pintamos el icono de que ha ocurrido un error con la descripción de borrado
		mostrarError = "block;";
		if (rData['codError'] == '1B')
		{
			//Pintamos el icono de que ha ocurrido un error con la descripción de bloqueo de borrado
			mostrarError = "block;";
			descError = replaceSpecialCharacters(errorBorradoNoBorrable);
		}else{
			if (rData['descError'] != '')
			{
				descError = rData['descError'];
			}
			else
			{
				descError = replaceSpecialCharacters(errorBorrado);
			}
		}
	}

	else if (parseFloat(rData['codError']) == '2')
	{
		//Pintamos el icono de que ha ocurrido un error con la descripción de modificación
		mostrarError = "block;";
		if (rData['descError'] != '')
		{
			descError = rData['descError'];
		}
		else
		{
			descError = replaceSpecialCharacters(errorModificacion);
		}
	}
	else if (parseFloat(rData['codError']) == '3')
	{
		//Pintamos el icono de que ha ocurrido un error con la descripción de modificación
		mostrarError = "block;";
		descError = replaceSpecialCharacters(errorModificacionFechas);
	}
	else if (parseFloat(rData['codError']) == '4')
	{
		//Pintamos el icono de que ha ocurrido un error con la descripción de modificación
		mostrarError = "block;";
		descError = replaceSpecialCharacters(errorModificacionNoModif);
	}
	else if (parseFloat(rData['codError']) == '5')
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
	}
	else if (parseFloat(rData['codError']) == '8')
	{
		//Pintamos el icono de que se ha guardado, por que se ha realizado la modificación correctamente
		mostrarModificado = "block;";
	}else{
		//Añadimos el literal de no activa en caso de que no se muestre ningún icono y esté bloquedo
		if (rData['estado'] != '' && rData['estado'] == "NOACT")
		{
			mostrarNoActiva = "block";
			p43EstadoNoActivaValor = p43EstadoNoActiva;
		}
	}

	imagen = "<div id='"+opts.rowId+"_divGuardadoMO' align='center' style='display: "+ mostrarModificado + "'><img id='"+opts.rowId+"_imgGuardado' src='./misumi/images/floppy.png' title='"+iconoModificado+"'/></div>"; //Modificado
	imagen += "<div id='"+opts.rowId+"_divErrorMO' align='center' style='display: " + mostrarError + "'><img id='"+opts.rowId+"_imgError' src='./misumi/images/dialog-error-24.png' title='"+descError+"'/></div>"; //Error
	imagen += "<div id='"+opts.rowId+"_divNoActiva' align='center' style='display: " + mostrarNoActiva + "'><a href='#' id='"+opts.rowId+"_p43_estado' class='p43Bloqueada' onclick='javascript:p43ReloadMotivosBloqueo("+opts.rowId+")' title='"+p43ConsultaMotivosBloqueo+"'>"+p43EstadoNoActivaValor+"</a></div>"; //No Activa
	
	//Añadimos los valores de la columna clasePedido ocultos para poder utilizarlos posteriormente.
	var datoClasePedido = "<input type='hidden' id='"+opts.rowId+"_clasePedidoMO' value='"+rData['clasePedido']+"'>";
	imagen +=  datoClasePedido;
	
	// Añadimos los valores de la columna referencia ocultos para poder utilizarlos posteriormente.
	var datoCodArticulo = "<input type='hidden' id='" + opts.rowId+ "_codArticuloMO' value='" + rData['codArticulo'] + "'>";
	imagen += datoCodArticulo;

	// Añadimos los valores de la columna si es modificable ocultos para poder utilizarlos posteriormente.
	var datoModificable = "<input type='hidden' id='" + opts.rowId+ "_modificableMO' value='" + rData['modificable'] + "'>";
	imagen += datoModificable;
	
	//Añadimos los valores de la columna si es modificable individual ocultos para poder utilizarlos posteriormente.
	var datoModificableIndiv = "<input type='hidden' id='"+opts.rowId+"_modificableIndivMO' value='"+rData['modificableIndiv']+"'>";
	imagen +=  datoModificableIndiv;
	
	//Añadimos los valores de la columna tipo de pedido ocultos para poder utilizarlos posteriormente.
	var datoTipoPedido = "<input type='hidden' id='"+opts.rowId+"_tipoPedidoMO' value='"+rData['tipoPedido']+"'>";
	imagen +=  datoTipoPedido;
	
	//Añadimos los valores de la columna si es borrable ocultos para poder utilizarlos posteriormente.
	var datoBorrable = "<input type='hidden' id='"+opts.rowId+"_borrableMO' value='"+rData['borrable']+"'>";
	imagen +=  datoBorrable;

	//Añadimos los valores de la columna perfil ocultos para poder utilizarlos posteriormente.
	var datoPerfil = "<input type='hidden' id='"+opts.rowId+"_perfilMO' value='"+rData['perfil']+"'>";
	imagen +=  datoPerfil;
	
	//Añadimos los valores de la columna esPlanograma ocultos para poder utilizarlos posteriormente.
	var datoPlanograma = "<input type='hidden' id='"+opts.rowId+"_esPlanogramaMO' value='"+rData['esPlanograma']+"'>";
	imagen +=  datoPlanograma;
	
	//Añadimos los valores de la columna identificador ocultos para poder utilizarlos posteriormente.
	var datoIdentificador = "";
	if(rData['identificador']!=null){
		datoIdentificador = "<input type='hidden' id='"+opts.rowId+"_identificadorMO' value='"+(rData['identificador'])+"'>";		
	}else{
		datoIdentificador = "<input type='hidden' id='"+opts.rowId+"_identificadorMO'>";		
	}
	
	imagen +=  datoIdentificador;

	//Añadimos los valores de la columna fecha inicio con formato ocultos para poder utilizarlos posteriormente.
	var datofechaInicio = "<input type='hidden' id='"+opts.rowId+"_fechaInicioMO' value='"+(rData['fechaInicio']!=null?rData['fechaInicio']:'')+"'>";
	imagen +=  datofechaInicio;
	
	//Añadimos los valores de la columna fecha2 con formato ocultos para poder utilizarlos posteriormente.
	var datofecha2 = "<input type='hidden' id='"+opts.rowId+"_fecha2MO' value='"+(rData['fecha2']!=null?rData['fecha2']:'')+"'>";
	imagen +=  datofecha2;

	//Añadimos los valores de la columna fecha3 con formato ocultos para poder utilizarlos posteriormente.
	var datofecha3 = "<input type='hidden' id='"+opts.rowId+"_fecha3MO' value='"+(rData['fecha3']!=null?rData['fecha3']:'')+"'>";
	imagen +=  datofecha3;

	//Añadimos los valores de la columna fecha4 con formato ocultos para poder utilizarlos posteriormente.
	var datofecha4 = "<input type='hidden' id='"+opts.rowId+"_fecha4MO' value='"+(rData['fecha4']!=null?rData['fecha4']:'')+"'>";
	imagen +=  datofecha4;

	//Añadimos los valores de la columna fecha5 con formato ocultos para poder utilizarlos posteriormente.
	var datofecha5 = "<input type='hidden' id='"+opts.rowId+"_fecha5MO' value='"+(rData['fecha5']!=null?rData['fecha5']:'')+"'>";
	imagen +=  datofecha5;

	//Añadimos los valores de la columna fechaInPil con formato ocultos para poder utilizarlos posteriormente.
	var datofechaInPil = "<input type='hidden' id='"+opts.rowId+"_fechaInPilMO' value='"+(rData['fechaPilada']!=null?rData['fechaPilada']:'')+"'>";
	imagen +=  datofechaInPil;
	
	//Añadimos los valores de la columna fecha fin con formato ocultos para poder utilizarlos posteriormente.
	var datofechaFin = "<input type='hidden' id='"+opts.rowId+"_fechaFinMO' value='"+(rData['fechaFin']!=null?rData['fechaFin']:'')+"'>";
	imagen +=  datofechaFin;
	
	//Añadimos los valores para el bloqueo
	var estado = "<input type='hidden' id='"+opts.rowId+"_estadoMO' value='"+rData['estado']+"'>";
	imagen +=  estado;
	
	//Añadimos los valores de la columna noGestionaPbl.
	var datoNoGestionaPbl = "<input type='hidden' id='"+opts.rowId+"_noGestionaPblM0' value='"+rData['noGestionaPbl']+"'>";
	imagen +=  datoNoGestionaPbl;

	//Añadimos los valores de la columna identificadorSIA ocultos para poder utilizarlos posteriormente.
	var datoIdentificadorSIA = "";
	if(rData['identificadorSIA']!=null){
		datoIdentificadorSIA = "<input type='hidden' id='"+opts.rowId+"_identificadorSIAMO' value='"+(rData['identificadorSIA'])+"'>";		
	}else{
		datoIdentificadorSIA = "<input type='hidden' id='"+opts.rowId+"_identificadorSIAMO'>";		
	}

	imagen +=  datoIdentificadorSIA;
	
	//Añadimos los valores de la columna identificadorVegalsa ocultos para poder utilizarlos posteriormente.
	var datoIdentificadorVegalsa = "";
	if(rData['identificadorVegalsa']!=null){
		datoIdentificadorVegalsa = "<input type='hidden' id='"+opts.rowId+"_identificadorVegalsaMO' value='"+(rData['identificadorVegalsa'])+"'>";		
	}else{
		datoIdentificadorVegalsa = "<input type='hidden' id='"+opts.rowId+"_identificadorVegalsaMO'>";		
	}
	
	imagen +=  datoIdentificadorVegalsa;

	return imagen;
}

function p43ControlesPantalla() {

	// Nos recorremos los registros existentes en pantalla.
	var rowsInPage = jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid(
			'getGridParam', 'rowNum');
	for (i = 0; i < rowsInPage; i++) {

		// Tenemos que seleccionar las checks que nos llegan de sesión
		p43CheckearSeleccionados(i + 1);

		// Controlamos las filas que vengan realizadas por Central o modificable
		// a N del WS para pintarlas de Rojo.
		p43RegistrosCentral(i + 1);

		// Controlamos bloquear el registro(no permitir checkear), cuando el
		// registro no sea modificable
		// por el WS o cuando el registro ha sido realizado por Central.
		p43ControlBloqueos(i + 1);
	}

}

function p43DesCheckearSeleccionados() {

	// Nos recorremos los registros existentes en pantalla.
	var rowsInPage = jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid(
			'getGridParam', 'rowNum');
	for (i = 0; i < rowsInPage; i++) {

		// Tenemos que seleccionar las checks que nos llegan de sesión
		var clasePedido = $("#"+(i+1)+"_clasePedidoMO").val();
		var codArticulo = $("#" + (i + 1) + "_codArticuloMO").val();
		var identificador = $("#"+(i+1)+"_identificadorMO").val();
		var identificadorSIA = $("#"+(i+1)+"_identificadorSIAMO").val();
		var identificadorVegalsa = $("#"+(i+1)+"_identificadorVegalsaMO").val();
		
		//En pantalla los identificadores vacíos son '' pero desde el controlador se transforman a null por tratarse de campos numéricos
		if (identificador==''){
			identificador = null;
		}
		if (identificadorSIA==''){
			identificadorSIA = null;
		}
		if (identificadorVegalsa==''){
			identificadorVegalsa = null;
		}
		
		// Nos recorremos la lista de seleccionados.
		for (j = 0; j < p43SeleccionadosTotal.length; j++){
			if ((p43SeleccionadosTotal[j].identificadorVegalsa==null)&&(p43SeleccionadosTotal[j].clasePedido == clasePedido)&&(p43SeleccionadosTotal[j].codArticulo == codArticulo)&&(p43SeleccionadosTotal[j].identificador == identificador)&&
					(p43SeleccionadosTotal[j].identificadorSIA == identificadorSIA)&&(p43SeleccionadosTotal[j].seleccionado == "S"))
			{
				jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setSelection', i+1);
			}else if (p43SeleccionadosTotal[j].identificadorVegalsa!=null && p43SeleccionadosTotal[j].identificadorVegalsa==identificadorVegalsa && (p43SeleccionadosTotal[j].seleccionado == "S")){
				jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setSelection', i+1);
			}
		}
	}
}

function p43CheckearSeleccionados(fila) {

	var clasePedido = $("#"+(fila)+"_clasePedidoMO").val();
	var codArticulo = $("#" + (fila) + "_codArticuloMO").val();
	var identificador = $("#"+(fila)+"_identificadorMO").val();
	var identificadorSIA = $("#"+(fila)+"_identificadorSIAMO").val();
	var identificadorVegalsa = $("#"+(i+1)+"_identificadorVegalsaMO").val();
	
	//En pantalla los identificadores vacíos son '' pero desde el controlador se transforman a null por tratarse de campos numéricos
	if (identificador==''){
		identificador = null;
	}
	if (identificadorSIA==''){
		identificadorSIA = null;
	}
	if (identificadorVegalsa==''){
		identificadorVegalsa = null;
	}

	// Nos recorremos la lista de seleccionados.
	for (j = 0; j < p43SeleccionadosTotal.length; j++) {
		if ((p43SeleccionadosTotal[j].identificadorVegalsa==null)&&(p43SeleccionadosTotal[j].clasePedido == clasePedido)&&(p43SeleccionadosTotal[j].codArticulo == codArticulo)&&(p43SeleccionadosTotal[j].identificador == identificador)&&
				(p43SeleccionadosTotal[j].identificadorSIA == identificadorSIA)&&(p43SeleccionadosTotal[j].seleccionado == "S"))
		{
			jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setSelection', fila);
		}else if (p43SeleccionadosTotal[j].identificadorVegalsa!=null && p43SeleccionadosTotal[j].identificadorVegalsa==identificadorVegalsa && (p43SeleccionadosTotal[j].seleccionado == "S")){
			jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setSelection', fila);
		}
	}

}

function p43RegistrosCentral(fila) {

	//filaBorrable = $("#"+(fila)+"_borrableMO").val();
	filaPlanogramada = $("#"+(fila)+"_esPlanogramaMO").val();
	perfil = $("#"+(fila)+"_perfilMO").val();

	//Si el perfil es distinto de 3 significa que es Central
	//if ((filaBorrable == "N")||(perfil != "3"))
	if ((filaPlanogramada == "S")||(perfil != "3"))
	{
		$(gridP43PedidoAdicionalMO.nameJQuery).find("#" + (fila)).find("td")
				.addClass("p43_columnaResaltada");
	}
}

function p43ControlBloqueos(fila) {

	var filaBorrable = $("#"+(fila)+"_borrableMO").val();
	var perfil = $("#"+(fila)+"_perfilMO").val();
	var clasePedido = $("#"+(fila)+"_clasePedidoMO").val();
	var modificableIndiv = $("#"+(fila)+"_modificableIndivMO").val();
	var noGestionaPbl = $("#"+(fila)+"_noGestionaPblMO").val();
	
	//Se permite el check si es de tipo Q o T
	var ultimoCaracterModifIndiv = "";
	if (modificableIndiv != null && modificableIndiv != ""){
		ultimoCaracterModifIndiv = modificableIndiv.substring(modificableIndiv.length - 1);
	}

	if (noGestionaPbl != null && noGestionaPbl =="S"){
		if (modificableIndiv.indexOf("S") < 0){ //Si no está marcarda ninguna cantidad modificable no se visualiza el check
			$("#jqg_"+gridP43PedidoAdicionalMO.name+"_"+(fila)).attr("style", "display:none");
		}
	}
	else if (clasePedido == "8")
	{
		// Todos los registros tipo 8, aunque seán de central son modificables si lo permite el WS
		if (filaBorrable == "N"){
			//Tenemos que deshabilitar la check
			$("#jqg_"+gridP43PedidoAdicionalMO.name+"_"+(fila)).attr("style", "display:none");
		}
	}
	else if (ultimoCaracterModifIndiv != 'Q' && ultimoCaracterModifIndiv != 'T' && ((filaBorrable == "N")||(perfil != "3")))
	{
		// Tenemos que deshabilitar la check
		$("#jqg_" + gridP43PedidoAdicionalMO.name + "_" + (fila)).attr("style",
				"display:none");
	}
}

function reloadDataP43PedidoAdicionalMO(recarga, articulo, identificador, clasePedido, identificadorSIA) {

	
	if (gridP43PedidoAdicionalMO.firstLoad) {
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setGridWidth', $("#p43_AreaMontajeAdicionalOferta").width(), true);
		gridP43PedidoAdicionalMO.firstLoad = false;
		if (gridP43PedidoAdicionalMO.isColState) {
			$(this).jqGrid("remapColumns", gridP43PedidoAdicionalMO.myColumnsState.permutation,true);
		}
	} else {
		gridP43PedidoAdicionalMO.myColumnsState = gridP43PedidoAdicionalMO.restoreColumnState(gridP43PedidoAdicionalMO.cm);
		gridP43PedidoAdicionalMO.isColState = typeof (gridP43PedidoAdicionalMO.myColumnsState) !== 'undefined'
				&& gridP43PedidoAdicionalMO.myColumnsState !== null;
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setGridWidth', $("#p43_AreaMontajeAdicionalOferta").width(), false);
	}

	// Obtenemos el array con los registros seleccionados
	p43ObtenerSeleccionados();
	
	

	var codArea = null;
	var codSeccion = null;
	var mca = "N";
	if ($("#p40_cmb_seccion").combobox('getValue') != null&& $("#p40_cmb_seccion").combobox('getValue') != '') {
		valorSeccion = $("#p40_cmb_seccion").combobox('getValue').split('*');
		codArea = valorSeccion[0];
		codSeccion = valorSeccion[1];
	}

	if($("#p40_chk_mca").is(':checked')) {
		mca = "S";
		$("#p43_AreaBeforeMO").attr("style", "display:inline;");
	} else {
		$("#p43_AreaBeforeMO").attr("style", "display:none;");
	}
	
	//var descPeriodo = null;
	//if ($("#p43_cmb_oferta").combobox('getValue') != null && $("#p43_cmb_oferta").combobox('getValue') != '') {
		//descPeriodo = $("#p43_cmb_oferta").combobox('getValue');
	//}
	
	//var espacioPromo = null;
	//if ($("#p43_cmb_promocion").combobox('getValue') != null && $("#p43_cmb_promocion").combobox('getValue') != '') {
		//espacioPromo = $("#p43_cmb_promocion").combobox('getValue');
	//}
	
	p40DescPeriodo = $("#p40_descPeriodo").val(); //Se recoge la descripcion de la oferta seleccionada desde los avisos, sino se viene de avisos ser null o string vacio
	if (p40DescPeriodo != null && p40DescPeriodo != '') {
		p43DescPeriodo = p40DescPeriodo;
	}
	
	var recordPedidoAdicionalMO = new PedidoAdicionalMO($("#centerId").val(),codArea, codSeccion, $("#p40_cmb_categoria").combobox('getValue'),
			$("#p40_fld_referencia").val(), null, clasePedido, null, null, null, null,null, null, null, null, null, p43SeleccionadosTotal, null, null, mca, null, [3,8], null, p43DescPeriodo, p43EspacioPromo, null);

	// Reseteamos el array de seleccionados.
	p43Seleccionados = new Array();
	p43SeleccionadosTotal = new Array();
	
	//DesCheckeamos lo seleccionado al recargar la lista.
	if (recarga=='S'){
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('resetSelection');
	}
	
	p43DesCheckearSeleccionados();

	var objJson = $.toJSON(recordPedidoAdicionalMO
			.preparePedidoAdicionalMOToJsonObject());

	$("#AreaResultados .loading").css("display", "block");
	

	$.ajax({
		type : 'POST',
		url : './pedidoAdicional/loadDataGridMO.do?page='+ gridP43PedidoAdicionalMO.getActualPage() + '&max=' + gridP43PedidoAdicionalMO.getRowNumPerPage()+ '&index=' + gridP43PedidoAdicionalMO.getSortIndex() + '&recarga=' + recarga +'&articulo='+articulo +'&identificador='+ identificador +'&identificadorSIA='+ identificadorSIA + '&sortorder=' + gridP43PedidoAdicionalMO.getSortOrder(),
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
			
			$(gridP43PedidoAdicionalMO.nameJQuery)[0].addJSONData(data.datos);
			gridP43PedidoAdicionalMO.actualPage = data.datos.page;
			gridP43PedidoAdicionalMO.localData = data.datos;
			
			if (recarga == 'N') {
				// Sólo recargamos los combos de Oferta y Promocion cuando
				// no es una recarga.
				combo = data.listadoComboOfertaPeriodo;
				
				if (null != combo && combo.length > 0){
					
					options = "<option value=null>&nbsp;</option>";
				
				   for (i = 0; i < combo.length; i++){
						var comboValor = combo[i];
						options = options + "<option value='" + comboValor+ "'>" + comboValor + "</option>";
				   }
				   $("select#p43_cmb_oferta").html(options);
				  // $("#p43_cmb_oferta").combobox('autocomplete',p43CmbOfertaTodos);
				   
				   p40DescPeriodo = $("#p40_descPeriodo").val();
				   if (p40DescPeriodo != ''){
				   
					   $("#p43_cmb_oferta").combobox('autocomplete',p40DescPeriodo);
					   $("#p43_cmb_oferta").val(p40DescPeriodo);
					   $("#p40_descPeriodo").val("");
				   } else {
					   if (null != data.defaultDescPeriodo){
						   defaultDescPeriodo = data.defaultDescPeriodo;
						   $("#p43_cmb_oferta").combobox('autocomplete',defaultDescPeriodo);
						   $("#p43_cmb_oferta").val(defaultDescPeriodo);
							 defaultDescPeriodo = null;
					   }
					  
				   }

				} else {
					$("select#p43_cmb_oferta").html("");
				}
				
				
				combo = data.listadoComboEspacioPromo;
		
				if (null != combo && combo.length > 0){
					
					options = "<option value=null>&nbsp;</option>";
				
					   for (i = 0; i < combo.length; i++){
							var comboValor = combo[i];
							options = options + "<option value='" + comboValor+ "'>" + comboValor + "</option>";
					   }
					   $("select#p43_cmb_promocion").html(options);
					  // $("#p43_cmb_promocion").combobox('autocomplete',p43CmbPromocionTodos);
					   
					 
					   if (null != data.defaultEspacioPromo){
						   defaultEspacioPromo = data.defaultEspacioPromo;
						   $("#p43_cmb_promocion").combobox('autocomplete',defaultEspacioPromo);
						   $("#p43_cmb_promocion").val(defaultEspacioPromo); 
						   defaultEspacioPromo = null;
					   }
				} else {
					$("select#p43_cmb_promocion").html("");
				}
			}
			
			
			
			if (data != null && data != '')
			{
				if (data.codError == 0){
					$("#p40AreaErrores").attr("style", "display:none");
					$("div#p40_AreaPestanas").attr("style", "display:block");
					
					p43SeleccionadosTotal = data.listadoSeleccionados;

					p43esTecnico = data.esTecnico;
					
					if (articulo != '')
					{
						//En este caso tenemos que actualizar el contador.
						$("#p40_fld_contadorMontajeOferta").text(literalMontajeAdicEnOf + " ("+data.contadorMontajeOferta+")");
					}

					$("#AreaResultados .loading").css("display", "none");
					if (data.records == 0) {
						createAlert(replaceSpecialCharacters(gridP43PedidoAdicionalMO.emptyRecords),
								"ERROR");
					}

					p43ControlesPantalla();
					
				}else{
					$("#AreaResultados .loading").css("display", "none");
					$("#p40_erroresTexto").text(data.descError);
					$("#p40AreaErrores").attr("style", "display:block");
					$("#p40_AreaPestanas").attr("style", "display:none");
				}
				hideShowColumnsMO();
			}else{
				$("#p40AreaErrores").attr("style", "display:none");
			}
		},
		error : function(xhr, status, error) {
			$("#AreaResultados .loading").css("display", "none");
			handleError(xhr, status, error, locale);
		}
	});
}

function p43ObtenerSeleccionados() {

	p43Seleccionados = new Array();

	var selectedRowIDs = jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid(
			'getGridParam', 'selarrrow');
	var rowsInPage = jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid(
			'getGridParam', 'rowNum');
	if(selectedRowIDs != undefined){
	// Obtenemos las referencias seleccionadas
		for (i = 0; i < selectedRowIDs.length; i++) {
			if (selectedRowIDs[i] != null && selectedRowIDs[i] != '') {
				var idVegalsa = $("#"+selectedRowIDs[i]+"_identificadorVegalsaMO").val();
				if (idVegalsa == '' || idVegalsa == null){
					p43Seleccionados.push($("#"+selectedRowIDs[i]+"_clasePedidoMO").val()+"#"+$("#"+selectedRowIDs[i]+"_codArticuloMO").val()+"#"+$("#"+selectedRowIDs[i]+"_identificadorMO").val()+"#"+$("#"+selectedRowIDs[i]+"_identificadorSIAMO").val());
				}else{
					p43Seleccionados.push($("#"+selectedRowIDs[i]+"_clasePedidoMO").val()+"#"+$("#"+selectedRowIDs[i]+"_codArticuloMO").val()+"#"+$("#"+selectedRowIDs[i]+"_identificadorMO").val()+"#"+$("#"+selectedRowIDs[i]+"_identificadorSIAMO").val()+"#"+idVegalsa);
				}
			}
		}
	}
	// Nos recorremos los registros existentes en pantalla.
	for (i = 0; i < rowsInPage; i++) {

		var clasePedido = $("#"+(i+1)+"_clasePedidoMO").val();
		var codArticulo = $("#" + (i + 1) + "_codArticuloMO").val();
		var identificador = $("#"+(i+1)+"_identificadorMO").val();
		var identificadorSIA = $("#"+(i+1)+"_identificadorSIAMO").val();
		var identificadorVegalsa = $("#"+(i+1)+"_identificadorVegalsaMO").val();
		
		identificadorArray = identificador;
		identificadorSIAArray = identificadorSIA;
		identificadorVegalsaArray = identificadorVegalsa;
		
		//En pantalla los identificadores vacíos son '' pero desde el controlador se transforman a null por tratarse de campos numéricos
		if (identificador==''){
			identificador = null;
		}
		if (identificadorSIA==''){
			identificadorSIA = null;
		}
		if (identificadorVegalsa==''){
			identificadorVegalsa = null;
		}
		
		//Nos recorremos la lista de seleccionados.
		for (j = 0; j < p43SeleccionadosTotal.length; j++){
				
			if (p43SeleccionadosTotal[j].identificadorVegalsa == null){
				if ((p43SeleccionadosTotal[j].clasePedido == clasePedido)&&(p43SeleccionadosTotal[j].codArticulo == codArticulo)&&(p43SeleccionadosTotal[j].identificador == identificador)&&(p43SeleccionadosTotal[j].identificadorSIA == identificadorSIA))
				{
					//Si es una de las referencias de la página, actualizamos el valor de seleccionado.
					if (($.inArray(clasePedido+"#"+codArticulo+"#"+identificadorArray+"#"+identificadorSIAArray, p43Seleccionados) > -1) )
					{
						p43SeleccionadosTotal[j].seleccionado = "S";
					}
					else
					{
						p43SeleccionadosTotal[j].seleccionado = "N";
					}
				}
			}else{
				if (p43SeleccionadosTotal[j].identificadorVegalsa == identificadorVegalsa){
					if ($.inArray(clasePedido+"#"+codArticulo+"#"+identificadorArray+"#"+identificadorSIAArray+"#"+identificadorVegalsaArray, p43Seleccionados) > -1 )
					{
						p43SeleccionadosTotal[j].seleccionado = "S";
					}
					else
					{
						p43SeleccionadosTotal[j].seleccionado = "N";
					}
				}
			}
		}
	}
}

function p43FindValidationRemove() {
	var messageVal = p43emptyListaSeleccionados;

	// Nos recorremos la lista de seleccionados.
	for (j = 0; j < p43SeleccionadosTotal.length; j++) {
		if (p43SeleccionadosTotal[j].seleccionado == "S") {
			messageVal = null;
		}
	}
	return messageVal;

}

function p43BorrarDatos() {

	// Obtenemos el array con los registros seleccionados
	p43ObtenerSeleccionados();

	var messageVal = p43FindValidationRemove();

	if (messageVal != null) {
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	} else {

		$(function() {
			$("#p43dialog-confirm").dialog({
				resizable : false,
				height : 140,
				modal : true,
				buttons : {
					"Si" : function() {
						p43Remove();
						$(this).dialog("close");
					},
					"No" : function() {
						$(this).dialog("close");
					}
				}
			});
		});
	}
}

function p43Remove() {

	var codArea = null;
	var codSeccion = null;
	var mca = "N";
	if ($("#p40_cmb_seccion").combobox('getValue') != null
			&& $("#p40_cmb_seccion").combobox('getValue') != '') {
		valorSeccion = $("#p40_cmb_seccion").combobox('getValue').split('*');
		codArea = valorSeccion[0];
		codSeccion = valorSeccion[1];
	}
	if($("#p40_chk_mca").is(':checked')) {
		mca = "S";
	}
	var recordPedidoAdicionalMO = new PedidoAdicionalMO($("#centerId").val(),codArea, codSeccion, $("#p40_cmb_categoria").combobox('getValue'),
			$("#p40_fld_referencia").val(), null, null, null, null, null, null,null, null, null, null, null, p43SeleccionadosTotal,null, null, null, null, [3,8],null,null,null,null);

	recordPedidoAdicionalMO.mca = mca;
	// DesCheckeamos lo seleccionado al llamar a borrar.
	p43DesCheckearSeleccionados();

	// Reseteamos el array de seleccionados.
	p43Seleccionados = new Array();
	p43SeleccionadosTotal = new Array();

	var objJson = $.toJSON(recordPedidoAdicionalMO.preparePedidoAdicionalMOToJsonObject());

	$("#AreaResultados .loading").css("display", "block");

	$.ajax({
				type : 'POST',
				url : './pedidoAdicional/removeDataGridMO.do?max='+ gridP43PedidoAdicionalMO.getRowNumPerPage()+ '&index=' + gridP43PedidoAdicionalMO.getSortIndex()+ '&sortorder='	+ gridP43PedidoAdicionalMO.getSortOrder(),
				data : objJson,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {
					$(gridP43PedidoAdicionalMO.nameJQuery)[0]
							.addJSONData(data.datos);
					gridP43PedidoAdicionalMO.actualPage = data.datos.page;
					gridP43PedidoAdicionalMO.localData = data.datos;
					
					if (data != null && data != '')
					{
						if (data.codError == 0){
							$("#p40AreaErrores").attr("style", "display:none");
							$("div#p40_AreaPestanas").attr("style", "display:block");
							
							p43SeleccionadosTotal = data.listadoSeleccionados;

							p43esTecnico = data.esTecnico;

							//Actualizamos el contador de montaje tras el borrado.
							$("#p40_fld_contadorMontajeOferta").text(literalMontajeAdicEnOf + " ("+data.contadorMontajeOferta+")");
							
							$("#AreaResultados .loading").css("display", "none");
							if (data.records == 0) {
								createAlert(replaceSpecialCharacters(gridP43PedidoAdicionalMO.emptyRecords),
										"ERROR");
							}

							p43ControlesPantalla();
							
						}else{
							$("#AreaResultados .loading").css("display", "none");
							$("#p40_erroresTexto").text(data.descError);
							$("#p40AreaErrores").attr("style", "display:block");
							$("#p40_AreaPestanas").attr("style", "display:none");
						}
							
					}else{
						$("#p40AreaErrores").attr("style", "display:none");
					}

				},
				error : function(xhr, status, error) {
					$("#AreaResultados .loading").css("display", "none");
					handleError(xhr, status, error, locale);
				}
			});
}

function p43ModificarReferencia(clasePedido,codArticulo,esPlanograma,identificador, fechaInicio, noGestionaPbl, identificadorSIA, identificadorVegalsa){

	//Antes de abrir la pantalla de modificación única por referencia, obtenemos la información necesaria para mostrarla posteriormente.
	var recordPedidoAdicionalMO = new PedidoAdicionalMO($("#centerId").val(),null, null, null,codArticulo, null, clasePedido, null, null,
		    null, null,null, null, null, null, null, p43SeleccionadosTotal, esPlanograma, identificador, null, null, [3,8], noGestionaPbl, null, null, identificadorSIA);
    recordPedidoAdicionalMO.fechaInicio = fechaInicio;
    recordPedidoAdicionalMO.identificadorVegalsa = identificadorVegalsa;
	var objJson = $.toJSON(recordPedidoAdicionalMO.preparePedidoAdicionalMOToJsonObject());

		$("#AreaResultados .loading").css("display", "block");
		
		$.ajax({
			type : 'POST',
			url : './pedidoAdicional/openModifyDataGridMO.do?page='+gridP43PedidoAdicionalMO.getActualPage()+'&max='+gridP43PedidoAdicionalMO.getRowNumPerPage()+'&index='+gridP43PedidoAdicionalMO.getSortIndex()+'&sortorder='+gridP43PedidoAdicionalMO.getSortOrder(),
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {		
				
				if (data != null && data != '' && data.codArticulo != null && data.codArticulo != '')
				{
					if (data.codError == 0){
						// MISUMI-352
						//obtenerSiEsCentroReferenciaVegalsa(data.codArticulo);
						if (esCentroVegalsa()){
							esCentroReferenciaVegalsa=true;
						}else{
							esCentroReferenciaVegalsa=false;
						}
						p43CargaDatosCalendarios(data);
						
						p60CrearCalendarios();
						
						p60DefinePedidoAdicionalMO(data);
						//Primero cargamos los datos.
						p43CargaDatos(data);
						
						//Ahora establecemos los campos que son modificables
						p43ControlModificables(data);
						
						//Abrimos el popup
						$( "#p60_AreaModificacion" ).dialog( "open" );

						$("#p40AreaErrores").attr("style", "display:none");
						
						$("#AreaResultados .loading").css("display", "none");	
					}else if (data.codError == 2){
						$(function() {
							 $( "#p43dialog-confirm-noExiste" ).dialog({
								 resizable: false,
								 height:140,
								 modal: true,
								 buttons: {
								 Aceptar: function() {
								 p43NoExiste(data.codArticulo,data.identificador,data.clasePedido, data.identificadorSIA);
								 $( this ).dialog( "close" );
								 }
								 }
							 });
						});
					}else{
						$("#AreaResultados .loading").css("display", "none");
						$("#p40_erroresTexto").text(data.descError);
						$("#p40AreaErrores").attr("style", "display:block");
						$("#p40_AreaPestanas").attr("style", "display:none");
					}
						
				}else{
					$("#AreaResultados .loading").css("display", "none");
					$("#p40_erroresTexto").text(data.descError);
					$("#p40AreaErrores").attr("style", "display:block");
					$("#p40_AreaPestanas").attr("style", "display:none");
				}
				
			},
			error : function (xhr, status, error){
				$("#AreaResultados .loading").css("display", "none");	
				handleError(xhr, status, error, locale);				
	        }			
		});
}

function p43NoExiste(codArticulo,identificador,clasePedido,identificadorSIA){
	reloadDataP43PedidoAdicionalMO('S',codArticulo,identificador,clasePedido,identificadorSIA);
	
}

function p43CargaDatosCalendarios(data){
	
	//Carga de datos para los calendarios
	$("#codCentroCalendario").val($("#centerId").val());
	$("#codArticuloCalendario").val(data.codArticulo);
	$("#identificadorCalendario").val(data.identificador);
	$("#identificadorSIACalendario").val(data.identificadorSIA);
	$("#identificadorVegalsaCalendario").val(data.identificadorVegalsa);
	$("#clasePedidoCalendario").val("M");
	$("#recargarParametrosCalendario").val("S");
	$("#cargadoDSCalendario").val("N");
	if (data.tipoPedido != null && data.tipoPedido == 'E'){
		$("#esFresco").val("S");
	}else{
		$("#esFresco").val("N");
	}

}

function p43CargaDatosCalendariosMasivo(){
	
	//Carga de datos para los calendarios
	$("#codCentroCalendario").val($("#centerId").val());
	$("#codArticuloCalendario").val('');
	$("#identificadorCalendario").val('');
	$("#identificadorSIACalendario").val('');
	$("#identificadorVegalsaCalendario").val('');
	$("#clasePedidoCalendario").val("M");
	$("#recargarParametrosCalendario").val("S");
	$("#cargadoDSCalendario").val("N");
}

function p43CargaDatos(data){
	
	//Primero las etiquetas
	$("#p60_lbl_fechaInicio").text(textoFechaInicio);
	
	//Ahora los datos
	$("#p60_fld_referencia").val(data.codArticulo);
	$("#p60_fld_denominacion").val(data.descriptionArt);
	$("#p60_fld_referenciaVisualizada").val(data.codArticuloGrid);
	$("#p60_fld_denominacionVisualizada").val(data.descriptionArtGrid);
	$("#p60_fld_tipoPedidoAdicional").val(tipoMontaje);
	$("#p60_fld_aprov").val(data.tipoAprovisionamiento);
	
	$("#p60_div_numeroOferta").show();
	$("#p60_fld_numeroOferta").val(data.oferta);
	$("#p60_fld_numeroOferta").removeAttr('title');
	$("#p60_fld_sinOferta").val("N");
	$("#p60_fld_numeroOferta").attr("disabled", "disabled");
	$("#p60_fld_tipoOferta").val(data.tipoOferta);
	$("#p60_div_fechaFin").show();
	$("#p60_div_cmb_excluir").hide();
	$("#p60_bloque3AreaDatosCampos1_1").hide(); //$("#p60_div_cmb_cajas").hide();
	$("#p60_bloque3AreaDatosCampos2Linea").css("clear", "left");
	$("#p60_bloque3AreaDatosCampos2Linea").css("padding-top", "20px");
	$("#p60_tratamiento").hide();
	if (data.fechaInicio != null && data.fechaInicio != '')
	{
		$("#p60_fechaInicioDatePicker").datepicker( "setDate", new Date(data.fechaInicio.substring(4), data.fechaInicio.substring(2,4) - 1, data.fechaInicio.substring(0,2)) );
		$("#p60_fechaInicioDatePicker").datepicker('refresh');
	}
	
	$("#p60_fechaFinDatePicker").attr("style", "display:inherit");
	
	
	if (data.fechaFin != null && data.fechaFin != '')
	{
		$("#p60_fechaFinDatePicker").datepicker( "setDate", new Date(data.fechaFin.substring(4), data.fechaFin.substring(2,4) - 1, data.fechaFin.substring(0,2)) );
		$("#p60_fechaFinDatePicker").datepicker('refresh');
	}
	
	if (data.stock == -9999){
		$("#p60_fld_stock").val("Error");
	} else {
		$("#p60_fld_stock").val(data.stock).formatNumber({format:"0.##"});
	}
	$("#p60_fld_uc").val(data.uniCajaServ).formatNumber({format:"0.##"});
	if (data.clasePedido == 8){
		$("#p60_bloqueFrescos").show();
		if (data.cantMax != null && data.cantMax != "" && data.cantMax != "0"){
			$("#p60_fld_max").val(data.cantMax).formatNumber({format:"0.##"});
		}else{
			$("#p60_fld_max").val("");
		}
		if (data.cantMin != null && data.cantMin != "" && data.cantMin != "0"){
			$("#p60_fld_min").val(data.cantMin).formatNumber({format:"0.##"});
		}else{
			$("#p60_fld_min").val("");
		}
	} else {
		$("#p60_bloqueFrescos").hide();
	}
	
	//Inicializamos antes de nada.
	$("#p60_lbl_cantidad1").text("");
	$("#p60_lbl_cantidad2").text("");
	$("#p60_lbl_cantidad3").text("");
	$("#p60_lbl_cantidad4").text("");
	$("#p60_lbl_cantidad5").text("");
	
	$("#p60_fld_cantidad1").val("");
	$("#p60_fld_cantidad2").val("");
	$("#p60_fld_cantidad3").val("");
	$("#p60_fld_cantidad4").val("");
	$("#p60_fld_cantidad5").val("");
	
	$("#p60_fld_cantidad1").attr("style", "display:none");
	$("#p60_fld_cantidad2").attr("style", "display:none");
	$("#p60_fld_cantidad3").attr("style", "display:none");
	$("#p60_fld_cantidad4").attr("style", "display:none");
	$("#p60_fld_cantidad5").attr("style", "display:none");
	
	if ((data.tipoPedido != null && data.tipoPedido == 'P')||(data.esPlanograma != null && data.esPlanograma == 'S'))
	{
		//Alimentación
		$("#p60_lbl_cantidad1").text(textoImplInicial);
		$("#p60_fld_cantidad1").attr("style", "display:inherit");
		$("#p60_fld_cantidad1").val(p43FormateoImplantPopUp(data.capMax));
		$("#p60_lbl_cantidad2").text(textoImplFinal);
		$("#p60_fld_cantidad2").attr("style", "display:inherit");
		$("#p60_fld_cantidad2").val(p43FormateoImplantPopUp(data.capMin));
		$("#p60_fld_cantidad3").attr("style", "display:none");
		$(".p60_bloque3AreaDatosCampos1").css("width", "155px");
		$("#p60_bloque3AreaDatosCampos2_3").hide();
		
		
		$("#p60_montajeMensajesFechas").hide();
		$("#p60_encargoMensajesFechas").hide();
		$("#p60_montajeFechasBloquedasEncargos").hide();
		$("#p60_montajeFechasBloquedasMontajes").hide();
		$("#p60_montajeFechasBloquedasMantenimiento").hide();
		
		if(data.mostrarLeyendaBloqueo != null && data.mostrarLeyendaBloqueo == "S"){
			$("#p60_montajeMensajesFechas").show();
			$("#p60_encargoMensajesFechas").hide();
			$("#p60_montajeFechasBloquedasEncargos").hide();
			$("#p60_montajeFechasBloquedasMontajes").show();
			$("#p60_montajeFechasBloquedasMantenimiento").hide();
		}

	}
	else if (data.tipoPedido != null && data.tipoPedido == 'E')
	{
		//Fresco Puro
		if (data.fechaInicio != null && data.fechaInicio != '')
		{
			var fechaformateada = $.datepicker.formatDate("D dd", new Date(data.fechaInicio.substring(4), data.fechaInicio.substring(2,4) - 1, data.fechaInicio.substring(0,2)),{
				dayNamesShort: $.datepicker.regional[ "es" ].dayNamesMin,
				dayNames: $.datepicker.regional[ "es" ].dayNames,
				monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
				monthNames: $.datepicker.regional[ "es" ].monthNames
				});
			
			$(".p60_bloque3AreaDatosCampos1").css("width", "110px");
			$("#p60_bloque3AreaDatosCampos1_2.p60_bloque3AreaDatosCampos1").css("width", "155px");
			$("#p60_bloque3AreaDatosCampos2_1.p60_bloque3AreaDatosCampos1").css("width", "155px");
			if (data.clasePedido == 8){
				$("#p60_bloque3AreaDatosCampos2_4.p60_bloque3AreaDatosCampos1").css("width", "155px");
				$("#p60_bloqueFrescosCampoMax.p60_bloque3AreaDatosCampos1").css("width", "155px");
			}
			$("#p60_lbl_cantidad1").text(fechaformateada);
			
			$("#p60_fld_cantidad1").attr("style", "display:inherit");
			$("#p60_fld_cantidad1").val(p53FormateoCantidadesPopUp(data.cantidad1));
		}
		if (data.fecha2 != null && data.fecha2 != '')
		{
		
			var fechaformateada2 = $.datepicker.formatDate("D dd", new Date(data.fecha2.substring(4), data.fecha2.substring(2,4) - 1, data.fecha2.substring(0,2)),{
				dayNamesShort: $.datepicker.regional[ "es" ].dayNamesMin,
				dayNames: $.datepicker.regional[ "es" ].dayNames,
				monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
				monthNames: $.datepicker.regional[ "es" ].monthNames
				});

			//$(".p60_bloque3AreaDatosCampos1").css("width", "110px");
			$("#p60_bloque3AreaDatosCampos2_2").show();  //$("#p60_div_cantidad2").show();
			$("#p60_lbl_cantidad2").text(fechaformateada2);
			
			$("#p60_fld_cantidad2").attr("style", "display:inherit");
			$("#p60_fld_cantidad2").val(p53FormateoCantidadesPopUp(data.cantidad2));
		} else {
			$("#p60_bloque3AreaDatosCampos2_2").hide();  //$("#p60_div_cantidad2").hide();
		}
		if (data.fecha3 != null && data.fecha3 != '')
		{
		
			var fechaformateada3 = $.datepicker.formatDate("D dd", new Date(data.fecha3.substring(4), data.fecha3.substring(2,4) - 1, data.fecha3.substring(0,2)),{
				dayNamesShort: $.datepicker.regional[ "es" ].dayNamesMin,
				dayNames: $.datepicker.regional[ "es" ].dayNames,
				monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
				monthNames: $.datepicker.regional[ "es" ].monthNames
				});
			//$(".p60_bloque3AreaDatosCampos1").css("width", "110px");
			$("#p60_bloque3AreaDatosCampos2_3").show(); //$("#p60_div_cantidad3").show();
			$("#p60_lbl_cantidad3").text(fechaformateada3);
			
			$("#p60_fld_cantidad3").attr("style", "display:inherit");
			$("#p60_fld_cantidad3").val(p53FormateoCantidadesPopUp(data.cantidad3));
		} else {
			$("#p60_bloque3AreaDatosCampos2_3").hide(); //$("#p60_div_cantidad3").hide();
		}
		if (data.fecha4 != null && data.fecha4 != '')
		{
		
			var fechaformateada4 = $.datepicker.formatDate("D dd", new Date(data.fecha4.substring(4), data.fecha4.substring(2,4) - 1, data.fecha4.substring(0,2)),{
				dayNamesShort: $.datepicker.regional[ "es" ].dayNamesMin,
				dayNames: $.datepicker.regional[ "es" ].dayNames,
				monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
				monthNames: $.datepicker.regional[ "es" ].monthNames
				});
			
			//$(".p60_bloque3AreaDatosCampos1").css("width", "110px");
			$("#p60_bloque3AreaDatosCampos2_4.p60_bloque3AreaDatosCampos1").css("width", "155px");
			$("#p60_bloque3AreaDatosCampos2_4").show(); //$("#p60_div_cantidad3").show();
			$("#p60_lbl_cantidad4").text(fechaformateada4);
			
			$("#p60_fld_cantidad4").attr("style", "display:inherit");
			$("#p60_fld_cantidad4").val(p52FormateoCantidadesPopUp(data.cantidad4));
		} else {
			$("#p60_bloque3AreaDatosCampos2_4").hide(); //$("#p60_div_cantidad3").hide();
		}
		if (data.fecha5 != null && data.fecha5 != '')
		{
		
			var fechaformateada5 = $.datepicker.formatDate("D dd", new Date(data.fecha5.substring(4), data.fecha5.substring(2,4) - 1, data.fecha5.substring(0,2)),{
				dayNamesShort: $.datepicker.regional[ "es" ].dayNamesMin,
				dayNames: $.datepicker.regional[ "es" ].dayNames,
				monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
				monthNames: $.datepicker.regional[ "es" ].monthNames
				});
			
			//$(".p60_bloque3AreaDatosCampos1").css("width", "110px");
			$("#p60_bloque3AreaDatosCampos2_5").show(); //$("#p60_div_cantidad3").show();
			$("#p60_lbl_cantidad5").text(fechaformateada5);
			
			$("#p60_fld_cantidad5").attr("style", "display:inherit");
			$("#p60_fld_cantidad5").val(p52FormateoCantidadesPopUp(data.cantidad5));
		} else {
			$("#p60_bloque3AreaDatosCampos2_5").hide(); //$("#p60_div_cantidad3").hide();
		}
		
		$("#p60_montajeMensajesFechas").hide();
		$("#p60_encargoMensajesFechas").hide();
		$("#p60_montajeFechasBloquedasEncargos").hide();
		$("#p60_montajeFechasBloquedasMontajes").hide();
		$("#p60_montajeFechasBloquedasMantenimiento").hide();
		if(data.mostrarLeyendaBloqueo != null && data.mostrarLeyendaBloqueo == "S"){
			//Hay que mostrar las leyendas de bloqueos, hay que realizar el control de que leyenda se muestra.
			//Carga de mensajes de calendarios
			$("#p60_montajeMensajesFechas").show();
			$("#p60_encargoMensajesFechas").hide();
			$("#p60_montajeFechasBloquedasEncargos").show();
			$("#p60_montajeFechasBloquedasMontajes").hide();
			$("#p60_montajeFechasBloquedasMantenimiento").show();
		}
	}
}

function p53FormateoCantidadesPopUp(cantidad) {
	
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

function p43FormateoImplantPopUp(cantidad) {
	
	if (cantidad != '')
	{
		//Actualizamos el formateo de los campos para que no tenga decimales
		return $.formatNumber(cantidad,{format:"0",locale:"es"});
	}
	else
	{
		return '';
	}
}

function p43EstablecerFiltrosConDec(){
	
	//Establecemos el formato para los campos de cantidades.
	$("#p60_fld_cantidad1").filter_input({regex:'[0-9,]'});
	$("#p60_fld_cantidad2").filter_input({regex:'[0-9,]'});
	$("#p60_fld_cantidad3").filter_input({regex:'[0-9,]'});
	$("#p60_fld_cantidad4").filter_input({regex:'[0-9,]'});
	$("#p60_fld_cantidad5").filter_input({regex:'[0-9,]'});
} 

function p43EstablecerFiltrosSinDec(){
	
	//Establecemos el formato para los campos de cantidades.
	$("#p60_fld_cantidad1").filter_input({regex:'[0-9]'});
	$("#p60_fld_cantidad2").filter_input({regex:'[0-9]'});
	$("#p60_fld_cantidad3").filter_input({regex:'[0-9]'});
	$("#p60_fld_cantidad4").filter_input({regex:'[0-9]'});
	$("#p60_fld_cantidad5").filter_input({regex:'[0-9]'});
}

function p43ControlModificables(data){
	
	//Primero deshabilitamos todos los campos.
	$("#p60_fechaInicioDatePicker").datepicker('disable');
	$("#p60_fechaFinDatePicker").datepicker('disable');
	if (data.stock == -9999){
		$("#p60_fld_stock").addClass("p60_stock_error");
		 $("#p60_fld_stock").prop("readonly", true);
			$("#p60_fld_stock").bind("keydown", function (e) {
				 if (e.keyCode == 8){
					 e.preventDefault();
					 e.stopPropagation();
				 }
			});
	} else {
		$("#p60_fld_stock").removeClass("p60_stock_error");
		$("#p60_fld_stock").attr("disabled", "disabled");
	}
	$("#p60_fld_uc").attr("disabled", "disabled");
	$("#p60_fld_max").attr("disabled", "disabled");
	$("#p60_fld_min").attr("disabled", "disabled");
	$("#p60_fld_cantidad1").attr("disabled", "disabled");
	$("#p60_fld_cantidad2").attr("disabled", "disabled");
	$("#p60_fld_cantidad3").attr("disabled", "disabled");
	$("#p60_fld_cantidad4").attr("disabled", "disabled");
	$("#p60_fld_cantidad5").attr("disabled", "disabled");
	
	//Inicialmente activamos el botón volver y el boton Guardar
	$( "#p60_AreaModificacion" ).dialog( "option", "buttons", [ { text: p43BotonGuardar, click: function() {p60GuardarPedido();} },
	                                                            { text: p43BotonVolver, click: function() { p60ControlesCierrePopUp($(this)); } } ] );
	
	//Borramos el htlm de fecha hasta para que al abrir distintos pedidos, si el pedido actual no tiene mensaje,
	//no aparezca el mensaje del pedido anterior.
	$("#p60_lbl_fechaHasta").html("");
	$("#p60_fld_fechaHasta").val("");
	
	var userPerfil = $("#userPerfil").val();
	if (userPerfil == "3"){
		//Si el perfil es igual a 3, hay que quitar el boton de guardar.
		$( "#p60_AreaModificacion" ).dialog( "option", "buttons", [ { text: p43BotonVolver, click: function() { p60ControlesCierrePopUp($(this)); } } ] );
	}
	
	$( "#p60_AreaModificacion" ).dialog( "option", "height", "600");
	$( "#p60_AreaModificacion" ).dialog( "option", "open" ,function() {$('.ui-dialog-titlebar-close').on('mousedown', function(e){e.stopPropagation();p60ControlesCierrePopUpAspa();});$(".p60_bloque3AreaDatosCampos").css("margin-top", "14px");$(".p60_bloque3AreaDatosCampos").css("width", "auto");if($("#p60_fld_tipoPedidoAdicional").val()!=p60TipoEncargo){reloadAyuda1();}});
	//Para poder modificar el pedido, en primer lugar no puede ser una planogramada.
	if ((data.esPlanograma == null) || (data.esPlanograma != null && data.esPlanograma != 'S'))
	{
		//En segundo lugar no debe ser central(Si el perfil es distinto de 3 significa que es Central)
		if ((data.perfil != null && data.perfil == "3")  || (data.clasePedido == 8) || (data.noGestionaPbl != null && data.noGestionaPbl == 'S'))
		{
			//Ahora tenemos que determinar que se puede modificar.
			p43EstablecerModificables(data);
		}
	}
	
	if ((data.modificable == "N") && (data.borrable == "N")) {

		//No es modificable, debemos quitar el boton de guardar.
		$( "#p60_AreaModificacion" ).dialog( "option", "buttons", [ { text: p41BotonVolver, click: function() { p60ControlesCierrePopUp($(this)); } } ] );
		$( "#p60_AreaModificacion" ).dialog( "option", "height", "485");
	}
}

function p43ActivarGuardar(){
	
	$( "#p60_AreaModificacion" ).dialog( "option", "buttons", [ { text: p43BotonGuardar, click: function() {p60GuardarPedido();} },
	                                                            { text: p43BotonVolver, click: function() { p60ControlesCierrePopUp($(this)); } } ] );
	var userPerfil = $("#userPerfil").val();
	if (userPerfil == "3"){
		//Si el perfil del usuario es igual a 3, hay que quitar el boton de guardar.
		$( "#p60_AreaModificacion" ).dialog( "option", "buttons", [ { text: p43BotonVolver, click: function() { p60ControlesCierrePopUp($(this)); } } ] );
	}
	$( "#p60_AreaModificacion" ).dialog( "option", "height", "600");
}

function p43EstablecerModificables(data){
	if (data.modificableIndiv != null){
		p60ModificableIndiv =  data.modificableIndiv;
	}

	if (data.noGestionaPbl == null || data.noGestionaPbl != 'S' || (data.identificadorSIA != null && data.identificadorSIA != '')){
		
		if (data.tipoPedido != null && data.tipoPedido == 'P')
		{
			//Alimentación(Si es N no se permite modificar nada)
			//alert("Es alimentación con modificableIndiv--->"+data.modificableIndiv);
			if (p43ControlModifFechaHasta(data.fechaHasta) && data.modificableIndiv != null && (data.modificableIndiv == 'S' || data.modificableIndiv == 'T' || data.modificableIndiv == 'Q'))
			{
				//Se permite modificar todo salvo la fecha de inicio si es de tipo Q
				if (data.modificableIndiv != 'Q'){
					$("#p60_fechaInicioDatePicker").datepicker('enable');
				}
				
				$("#p60_fechaFinDatePicker").datepicker('enable');
	
				if (data.modificableIndiv != 'Q'){
					p43EstablecerFechaMin(data.fechaMinima,data.fechaMinima,"N");
				}else{
					p43EstablecerFechaMin(data.fechaInicio,data.fechaMinima,"S");
				}
	
				// MISUMI-352
				if (daysDiff() > 7 && !esCentroReferenciaVegalsa){
					$("#p60_fld_cantidad2").removeAttr("disabled");
					$("#p60_fld_cantidad2").focus(function(event) {
						$("#p60_fld_cantidad2").select();
					});
				}else{
					$("#p60_fld_cantidad1").keyup(function(event) {
						$("#p60_fld_cantidad2").val(this.value);
					});
				}
				$("#p60_fld_cantidad1").focus(function(event) {
					$("#p60_fld_cantidad1").select();
				});
	
				p43ActivarGuardar();
			}
			else if (p43ControlModifFechaHasta(data.fechaHasta) && data.modificableIndiv != null && data.modificableIndiv == 'P')
			{
				//Se permiten modificar las cantidades y la fecha fin
				$("#p60_fechaFinDatePicker").datepicker('enable');
				$("#p60_fld_cantidad1").removeAttr("disabled");
				// MISUMI-352				
				if (daysDiff() > 7 && !esCentroReferenciaVegalsa){
					$("#p60_fld_cantidad2").removeAttr("disabled");
					$("#p60_fld_cantidad2").focus(function(event) {
						$("#p60_fld_cantidad2").select();
					});
				}else{
					$("#p60_fld_cantidad1").keyup(function(event) {
						$("#p60_fld_cantidad2").val(this.value);
					});
				}
				$("#p60_fld_cantidad1").focus(function(event) {
					$("#p60_fld_cantidad1").select();
				});
				
				
				p43EstablecerFechaMin(data.fechaInicio,data.fechaMinima,"S");		
				p43ActivarGuardar();
			}
			
			p43EstablecerFiltrosSinDec();
			
		}
		else if (data.tipoPedido != null && data.tipoPedido == 'E')
		{
			//Fresco Puro
			var modif43 = false;
			//Fresco Puro
			//alert("Es fresco puro con modificableIndiv--->"+data.modificableIndiv);
			//Si tiene bloqueo de encargo sólo se permite modificar la fecha de fin
			if (p43ControlModifFechaHasta(data.fechaHasta) && (data.fechaBloqueoEncargo == null || data.fechaBloqueoEncargo != 'S') && data.modificableIndiv != null && (data.modificableIndiv.charAt(0) == "S" || data.modificableIndiv.charAt(0) == "T" || data.modificableIndiv.charAt(0) == "Q"))
			{
				//Se permite modificar todo salvo la fecha de inicio si tiene bloqueo de pilada y es de tipo Q
				var ultimoCaracterModifIndiv = "";
				if (data.modificableIndiv != null && data.modificableIndiv != ""){
					ultimoCaracterModifIndiv = data.modificableIndiv.substring(data.modificableIndiv.length - 1);
				}
				if (ultimoCaracterModifIndiv != 'Q'){
					$("#p60_fechaInicioDatePicker").datepicker('enable');
					p43EstablecerFechaMin(data.fechaMinima,data.fechaMinima,"N");
				}else{
					p43EstablecerFechaMin(data.fechaInicio,data.fechaMinima,"S");
				}
				
				$("#p60_fld_cantidad1").removeAttr("disabled");
				$("#p60_fld_cantidad1").focus(function(event) {
					$("#p60_fld_cantidad1").select();
				});
				$("#p60_cmb_excluir").combobox("enable");
				
				
				p43ActivarGuardar();
				modif43 = true;
			}
			if (p43ControlModifFechaHasta(data.fechaHasta) && (data.fechaBloqueoEncargo == null || data.fechaBloqueoEncargo != 'S') && data.modificableIndiv != null && data.modificableIndiv.length > 1 && (data.modificableIndiv.charAt(1) == "S" || data.modificableIndiv.charAt(1) == "T" || data.modificableIndiv.charAt(1) == "Q"))
			{
				// MISUMI-352
				if (!esCentroReferenciaVegalsa){
					$("#p60_fld_cantidad2").removeAttr("disabled");
					$("#p60_fld_cantidad2").focus(function(event) {
						$("#p60_fld_cantidad2").select();
					});
				}else{
					$("#p60_fld_cantidad1").keyup(function(event) {
						$("#p60_fld_cantidad2").val(this.value);
					});
				}
				
				if (!modif43){
				p43EstablecerFechaMin(data.fechaInicio,data.fechaMinima,"S");
				p43ActivarGuardar();
				modif43 = true;
				}
			}
			if (p43ControlModifFechaHasta(data.fechaHasta) && (data.fechaBloqueoEncargo == null || data.fechaBloqueoEncargo != 'S') && data.modificableIndiv != null && data.modificableIndiv.length > 2 && (data.modificableIndiv.charAt(2) == "S" || data.modificableIndiv.charAt(2) == "T" || data.modificableIndiv.charAt(2) == "Q"))
			{
				//Sólo se permite modificar la cantidad3.
				$("#p60_fld_cantidad3").removeAttr("disabled");
				$("#p60_fld_cantidad3").focus(function(event) {
					$("#p60_fld_cantidad3").select();
				});
				
				if (!modif43){
					p43EstablecerFechaMin(data.fechaInicio,data.fechaMinima,"S");
					p43ActivarGuardar();
					modif43 = true;
					}
			}
			
			if (data.clasePedido == "8"){
				if (p43ControlModifFechaHasta(data.fechaHasta) && (data.fechaBloqueoEncargo == null || data.fechaBloqueoEncargo != 'S') && data.modificableIndiv != null && data.modificableIndiv.length > 3 && (data.modificableIndiv.charAt(3) == "S" || data.modificableIndiv.charAt(3) == "T" || data.modificableIndiv.charAt(3) == "Q"))
				{
					//Sólo se permite modificar la cantidad3.
					$("#p60_fld_cantidad4").removeAttr("disabled");
					$("#p60_fld_cantidad4").focus(function(event) {
						$("#p60_fld_cantidad4").select();
					});
					
					if (!modif43){
						p43EstablecerFechaMin(data.fechaInicio,data.fechaMinima,"S");
						p43ActivarGuardar();
						modif43 = true;
					}
				}
				if (p43ControlModifFechaHasta(data.fechaHasta) && (data.fechaBloqueoEncargo == null || data.fechaBloqueoEncargo != 'S') && data.modificableIndiv != null && data.modificableIndiv.length > 4 && (data.modificableIndiv.charAt(4) == "S" || data.modificableIndiv.charAt(4) == "T" || data.modificableIndiv.charAt(4) == "Q"))
				{
					//Sólo se permite modificar la cantidad3.
					$("#p60_fld_cantidad5").removeAttr("disabled");
					$("#p60_fld_cantidad5").focus(function(event) {
						$("#p60_fld_cantidad5").select();
					});
					
					if (!modif43){
						p43EstablecerFechaMin(data.fechaInicio,data.fechaMinima,"S");
						p43ActivarGuardar();
						modif43 = true;
						}
				}
				
			} else {
				
				
				if (p43ControlModifFechaHasta(data.fechaHasta) && data.modificableIndiv != null && data.modificableIndiv.length > 3 && ( data.modificableIndiv.charAt(3) == "S" || data.modificableIndiv.charAt(3) == "P" || data.modificableIndiv.charAt(3) == "T" || data.modificableIndiv.charAt(3) == "Q"))
				{
					//Sólo se permite modificar la Fecha Fin
					//Si tiene pilada sólo se permite modificar la fecha fin desde la fecha de pilada
					//Si tiene bloqueo de encargo no puede ser menor que la fecha de pilada
					if (p60ModificableIndiv != null && (p60ModificableIndiv == 'S' || p60ModificableIndiv == 'SS' || p60ModificableIndiv == 'SSS' || p60ModificableIndiv == 'SSSS') && (data.fechaBloqueoEncargo == null || data.fechaBloqueoEncargo != 'S')){
						$("#p60_fechaFinDatePicker").datepicker( 'option', 'minDate', new Date(data.fechaMinima.substring(4), data.fechaMinima.substring(2,4) - 1, data.fechaMinima.substring(0,2)));
					}else{
						$("#p60_fechaFinDatePicker").datepicker( 'option', 'minDate', new Date(data.fechaPilada.substring(4), data.fechaPilada.substring(2,4) - 1, data.fechaPilada.substring(0,2)));
					}
					$("#p60_fechaFinDatePicker").datepicker('enable');
					
					if (!modif43){
						p43ActivarGuardar();
						modif43 = true;
					}
	
				}else{
					var ultimoCaracterModifIndiv = "";
					if (data.modificableIndiv != null && data.modificableIndiv != ""){
						ultimoCaracterModifIndiv = data.modificableIndiv.substring(data.modificableIndiv.length - 1);
					}
					if (p43ControlModifFechaHasta(data.fechaHasta) && data.modificableIndiv != null && (ultimoCaracterModifIndiv == "S" || ultimoCaracterModifIndiv == "P" || ultimoCaracterModifIndiv == "T"  || ultimoCaracterModifIndiv == "Q") && (data.fechaBloqueoEncargo == null || data.fechaBloqueoEncargo != 'S'))
					{
						//Sólo se permite modificar la Fecha Fin
						$("#p60_fechaFinDatePicker").datepicker('enable');
						
						if (!modif43){
							p43EstablecerFechaMin(data.fechaInicio,data.fechaMinima,"S");
							p43ActivarGuardar();
							modif43 = true;
						}
					}
				}
			}
		}
	}else{
		$("#p60_excluir").hide();
		$("#p60_fechaInicioDatePicker").datepicker('disable');
		$("#p60_fechaFinDatePicker").datepicker('disable');
		
		if (p43ControlModifFechaHasta(data.fechaHasta) && data.modificableIndiv != null && (data.modificableIndiv.charAt(0) == "S"))
		{
			$("#p60_fld_cantidad1").removeAttr("disabled");
			$("#p60_fld_cantidad1").focus(function(event) {
				$("#p60_fld_cantidad1").select();
			});
			
			p43ActivarGuardar();
			modif = true;
		}
		if (p43ControlModifFechaHasta(data.fechaHasta) && data.modificableIndiv.length > 1 && (data.modificableIndiv.charAt(1) == "S"))
		{
			//Sólo se permite modificar la cantidad2.
			// MISUMI-352			
			if (!esCentroReferenciaVegalsa){
				$("#p60_fld_cantidad2").removeAttr("disabled");
				$("#p60_fld_cantidad2").focus(function(event) {
					$("#p60_fld_cantidad2").select();
				});
				
			}else{
				$("#p60_fld_cantidad1").keyup(function(event) {
					$("#p60_fld_cantidad2").val(this.value);
				});
			}
			
			if (!modif){
				p43ActivarGuardar();
				modif = true;
			}
		}
		if (p43ControlModifFechaHasta(data.fechaHasta) && data.modificableIndiv != null && data.modificableIndiv.length > 2 && (data.modificableIndiv.charAt(2) == "S"))
		{
			//Sólo se permite modificar la cantidad3.
			$("#p60_fld_cantidad3").removeAttr("disabled");
			$("#p60_fld_cantidad3").focus(function(event) {
				$("#p60_fld_cantidad3").select();
			});
			
			if (!modif){
				p43ActivarGuardar();
				modif = true;
			}
		}

		if (p43ControlModifFechaHasta(data.fechaHasta) && (data.fechaBloqueoEncargo == null || data.fechaBloqueoEncargo != 'S') && data.modificableIndiv != null && data.modificableIndiv.length > 3 && (data.modificableIndiv.charAt(3) == "S"))
		{
			//Sólo se permite modificar la cantidad3.
			$("#p60_fld_cantidad4").removeAttr("disabled");
			$("#p60_fld_cantidad4").focus(function(event) {
				$("#p60_fld_cantidad4").select();
			});
			
			if (!modif){
				p43ActivarGuardar();
				modif = true;
			}
		}
		if (p43ControlModifFechaHasta(data.fechaHasta) && (data.fechaBloqueoEncargo == null || data.fechaBloqueoEncargo != 'S') && data.modificableIndiv != null && data.modificableIndiv.length > 4 && (data.modificableIndiv.charAt(4) == "S"))
		{
			//Sólo se permite modificar la cantidad3.
			$("#p60_fld_cantidad5").removeAttr("disabled");
			$("#p60_fld_cantidad5").focus(function(event) {
				$("#p60_fld_cantidad5").select();
			});
			
			if (!modif){
				p43ActivarGuardar();
				modif = true;
			}
		}
	}
	p43EstablecerFiltrosConDec();
	
	if (data.clasePedido == "8"){
		$("#p60_fechaInicioDatePicker").datepicker('disable');
		$("#p60_fechaFinDatePicker").datepicker('disable');
	}
}

function p43EstablecerFechaMin(data){
	var fecIni = data.fechaInicio;
	$("#p60_fechaInicioDatePicker" ).datepicker( "option", "minDate", new Date(fecIni.substring(4), fecIni.substring(2,4) - 1, fecIni.substring(0,2)) );
	$("#p60_fechaInicioDatePicker").datepicker('disable');
}

function p43EstablecerFechaMin(fecIni,fecFin,disabledIni){

	$("#p60_fechaInicioDatePicker" ).datepicker( "option", "minDate", new Date(fecIni.substring(4), fecIni.substring(2,4) - 1, fecIni.substring(0,2)) );
	
	if (disabledIni == "S")
	{
		$("#p60_fechaInicioDatePicker").datepicker('disable');
	}
	
	$("#p60_fechaFinDatePicker" ).datepicker( "option", "minDate", new Date(fecFin.substring(4), fecFin.substring(2,4) - 1, fecFin.substring(0,2)) );
}

function p43ModificarDatos(){
	
	p43ObtenerSeleccionados();
	
	// Nos recorremos la lista de seleccionados.
	var contSeleccionados = 0;
	var clasePedidoSel;
	var codArticuloSel;
	var esPlanogramaSel;
	var identificadorSel;
	var fechaInicioSel;
	var noGestionaPbl;
	var identificadorSIASel;
	var identificadorVegalsaSel;
	for (j = 0; j < p43SeleccionadosTotal.length; j++) {
		if (p43SeleccionadosTotal[j].seleccionado == "S") {
			contSeleccionados++;
			clasePedidoSel = p43SeleccionadosTotal[j].clasePedido;
			codArticuloSel = p43SeleccionadosTotal[j].codArticulo;
			esPlanogramaSel = p43SeleccionadosTotal[j].esPlanograma;
			identificadorSel = p43SeleccionadosTotal[j].identificador;
			noGestionaPblSel = p43SeleccionadosTotal[j].noGestionaPbl;
			identificadorSIASel = p43SeleccionadosTotal[j].identificadorSIA;
			identificadorVegalsaSel = p43SeleccionadosTotal[j].identificadorVegalsa;			
		}
	}
	
	if (contSeleccionados == 0)
	{
		createAlert(replaceSpecialCharacters(p43emptyListaSeleccionados), "ERROR");
	}
	else if (contSeleccionados == 1)
	{
		//Si sólo ha seleccionado un registro llamaremos a la modicicación individual.
		p43ModificarReferencia(clasePedidoSel,codArticuloSel,esPlanogramaSel,identificadorSel,null, noGestionaPblSel, identificadorSIASel, identificadorVegalsaSel);
	}
	else if ( (contSeleccionados > 1) && ($("#p40_chk_mca").is(':checked') ))
	{
		createAlert(replaceSpecialCharacters(errorModificacionNoMasiva), "ERROR");
	}
	else
	{
		//Antes de abrir el calendario, tenemos que hacer las comprobaciones de que haya seleccionado
		//todos los registros con la misma fecha fin y obtener la fecha de inicio del calendario a mostrar al usuario.
		var recordPedidoAdicionalMO = new PedidoAdicionalMO ($("#centerId").val() , null, null, null,
				null, null, null,null, null, null, null, null, null, null, null, null, p43SeleccionadosTotal, null, null, null, null, [3,8],null,null,null,null);

		//DesCheckeamos lo seleccionado al llamar a comprobar si es modificable la lista seleccionada.
		p43DesCheckearSeleccionados();
		
		//Reseteamos el array de seleccionados.
		p43Seleccionados = new Array();
		p43SeleccionadosTotal = new Array();

		var objJson = $.toJSON(recordPedidoAdicionalMO.preparePedidoAdicionalMOToJsonObject());

			$("#AreaResultados .loading").css("display", "block");
			
			$.ajax({
				type : 'POST',
				url : './pedidoAdicional/testModifyDataGridMO.do?max='+gridP43PedidoAdicionalMO.getRowNumPerPage()+'&index='+gridP43PedidoAdicionalMO.getSortIndex()+'&sortorder='+gridP43PedidoAdicionalMO.getSortOrder(),
				data : objJson,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {		
					$(gridP43PedidoAdicionalMO.nameJQuery)[0].addJSONData(data.datos);
					gridP43PedidoAdicionalMO.actualPage = data.datos.page;
					gridP43PedidoAdicionalMO.localData = data.datos;
					
					if (data != null && data != '')
					{
						if (data.codError == 0){
							$("#p40AreaErrores").attr("style", "display:none");
							$("div#p40_AreaPestanas").attr("style", "display:block");
							
							p43SeleccionadosTotal = data.listadoSeleccionados;

							p43esTecnico = data.esTecnico;

							$("#AreaResultados .loading").css("display", "none");
							if (data.records == 0) {
								createAlert(replaceSpecialCharacters(gridP43PedidoAdicionalMO.emptyRecords),
										"ERROR");
							}

							p43ControlesPantalla();
							
							if (data.esModificable == "S")
							{
								p43CargaDatosCalendariosMasivo();
								
								//Sólo en el caso de que me devuelva el controlador que es modificable, abrimos para que seleccione la fecha.
								//Tenemos que obtener la fecha mínima.
								var fecMin = data.fechaMinima;
								var fechaFin = data.fechaFin;
								$( "#p61_fechaFinDatePicker" ).datepicker( "option", "minDate", new Date(fecMin.substring(4), fecMin.substring(2,4) - 1, fecMin.substring(0,2)) );
								$( "#p61_fechaFinDatePicker" ).datepicker( "setDate", new Date(fechaFin.substring(4), fechaFin.substring(2,4) - 1, fechaFin.substring(0,2)) );
								$("#p61_AreaModificacion").dialog("open");
							}
							
						}else{
							$("#AreaResultados .loading").css("display", "none");
							$("#p40_erroresTexto").text(data.descError);
							$("#p40AreaErrores").attr("style", "display:block");
							$("#p40_AreaPestanas").attr("style", "display:none");
						}
					}else{
						$("#p40AreaErrores").attr("style", "display:none");
					}
				},
				error : function (xhr, status, error){
					$("#AreaResultados .loading").css("display", "none");	
					handleError(xhr, status, error, locale);				
		        }			
			});
	}	
}

function p43ModificarDatosDesdePopup(){

	var codArea = null;
	var codSeccion = null;
	if ($("#p40_cmb_seccion").combobox('getValue') != null && $("#p40_cmb_seccion").combobox('getValue') != '')
	{
		valorSeccion = $("#p40_cmb_seccion").combobox('getValue').split('*');
		codArea = valorSeccion[0];
		codSeccion = valorSeccion[1];
	}
	
	p43ObtenerSeleccionados();
	
	var recordPedidoAdicionalMO = new PedidoAdicionalMO ($("#centerId").val() , codArea, codSeccion, $("#p40_cmb_categoria").combobox('getValue'),
		    $("#p40_fld_referencia").val(), null, null,null, $( "#p61_fechaFinDatePicker" ).val(), 
		    null, null, null, null, null, null, null, p43SeleccionadosTotal, null, null, null, null, [3,8],null,null,null,null);

	//DesCheckeamos lo seleccionado al llamar a modificar.
	p43DesCheckearSeleccionados();
	
	//Reseteamos el array de seleccionados.
	p43Seleccionados = new Array();
	p43SeleccionadosTotal = new Array();
	

	var objJson = $.toJSON(recordPedidoAdicionalMO.preparePedidoAdicionalMOToJsonObject());

		$("#AreaResultados .loading").css("display", "block");
		
		$.ajax({
			type : 'POST',
			url : './pedidoAdicional/modifyDataGridMO.do?max='+ gridP43PedidoAdicionalMO.getRowNumPerPage()+ '&index=' + gridP43PedidoAdicionalMO.getSortIndex()+ '&sortorder='	+ gridP43PedidoAdicionalMO.getSortOrder(),
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {		
				$(gridP43PedidoAdicionalMO.nameJQuery)[0].addJSONData(data.datos);
				gridP43PedidoAdicionalMO.actualPage = data.datos.page;
				gridP43PedidoAdicionalMO.localData = data.datos;
				
				if (data != null && data != '')
				{
					if (data.codError == 0){
						$("#p40AreaErrores").attr("style", "display:none");
						$("div#p40_AreaPestanas").attr("style", "display:block");
						
						p43SeleccionadosTotal = data.listadoSeleccionados;

						p43esTecnico = data.esTecnico;

						$("#AreaResultados .loading").css("display", "none");
						if (data.records == 0) {
							createAlert(replaceSpecialCharacters(gridP43PedidoAdicionalMO.emptyRecords),
									"ERROR");
						}

						p43ControlesPantalla();
						
					}else{
						$("#AreaResultados .loading").css("display", "none");
						$("#p40_erroresTexto").text(data.descError);
						$("#p40AreaErrores").attr("style", "display:block");
						$("#p40_AreaPestanas").attr("style", "display:none");
					}
				}else{
					$("#p40AreaErrores").attr("style", "display:none");
				}
			},
			error : function (xhr, status, error){
				$("#AreaResultados .loading").css("display", "none");	
				handleError(xhr, status, error, locale);				
	        }			
		});
}

function p53FormateoCantidades(cellValue, opts, rData) {
	
	if ((cellValue != null) && (cellValue != ''))
	{
		//Actualizamos el formateo de los campos a dos decimales
		return $.formatNumber(cellValue,{format:"0.##",locale:"es"});
	}
	else
	{
		return '';
	}
}

function p43FormateoImplant(cellValue, opts, rData) {
	
	if (cellValue != '')
	{
		//Actualizamos el formateo de los campos a dos decimales
		return $.formatNumber(cellValue,{format:"0",locale:"es"});
	}
	else
	{
		return '';
	}
}

/* Clase de constantes para el GRID de Pedido adicional oferta */
function GridP43PedidoAdicionalMO(locale) {
	// Atributos
	this.name = "gridP43MontajeAdicionalOferta";
	this.nameJQuery = "#gridP43MontajeAdicionalOferta";
	this.i18nJSON = './misumi/resources/p43PedidoAdicionalMO/p43pedidoAdicionalMO_'
			+ locale + '.json';
	this.colNames = null;
	this.cm = [ {
		"name" : "mensaje",
		"index" : "mensaje",
		"formatter" : p43ImageFormatMessage,
		"fixed" : true,
		"width" : 75,
		"sortable" : true
	}, {
		"name"  : "descOferta",
		"index" : "descOferta", 
		"width" : 170,
		"hidden" : true,
		"sortable" : true
	},{
		"name"  : "identificadorVegalsa",
		"index" : "identificadorVegalsa", 
		"width" : 25,
		"hidden" : true,
		"hidedlg" : true
	},{		
		"name" : "codArticuloGrid",
		"index" : "codArticuloGrid",
		"width" : 78,
		"sortable" : true
	},{
		"name"  : "agrupacion",
		"index" : "agrupacion", 
		"width" : 70,
		"hidden" : true,
		"sortable" : true
	}, {
		"name" : "descriptionArtGrid",
		"index" : "descriptionArtGrid",
		"width" : 220,
		"sortable" : true
	}, {
		"name" : "fechaInicio",
		"index" : "fechaInicio",
		"formatter" : p43FormateoDate,
		"width" : 60,
		"sortable" : true
	}, {
		"name" : "fechaFin",
		"index" : "fechaFin",
		"formatter" : p43FormateoDate,
		"width" : 60,
		"sortable" : true
	}, {
		"name" : "capMax",
		"index" : "capMax",
		"formatter" : p43FormateoImplant,
		"width" : 60,
		"sortable" : true
	}, {
		"name" : "capMin",
		"index" : "capMin",
		"formatter" : p43FormateoImplant,
		"width" : 60,
		"sortable" : true
	}, {
		"name" : "cantidad1",
		"index" : "cantidad1",
		"formatter" : p53FormateoCantidades,
		"width" : 55,
		"sortable" : true
	}, {
		"name" : "cantidad2",
		"index" : "cantidad2",
		"formatter" : p53FormateoCantidades,
		"width" : 55,
		"sortable" : true
	}, {
		"name" : "cantidad3",
		"index" : "cantidad3",
		"formatter" : p53FormateoCantidades,
		"width" : 55,
		"sortable" : true
	},{
		"name"  : "cantidad4",
		"index" : "cantidad4", 
		"formatter" : p53FormateoCantidades,
		"width" : 55,
		"hidden": true,
		"sortable" : true
	},{
		"name"  : "cantidad5",
		"index" : "cantidad5", 
		"formatter" : p53FormateoCantidades,
		"width" : 55,
		"hidden": true,
		"sortable" : true
	},{
		"name" : "uniCajaServ",
		"index" : "uniCajaServ",
		"formatter" : p43FormateoUnidadesCaja,
		"hidden" : true,
		"width" : 50,
		"sortable" : true
	}, {
		"name" : "oferta",
		"index" : "oferta",
		"hidden" : true,
		"width" : 50,
		"sortable" : true
	}, {
		"name"  : "cantMin",
		"index" : "cantMin", 
		"formatter" : p53FormateoCantidades,
		"width" : 60,
		"hidden" : true,
		"sortable" : true
	}, {
		"name"  : "cantMax",
		"index" : "cantMax", 
		"formatter" : p53FormateoCantidades,
		"width" : 60,
		"hidden" : true,
		"sortable" : true
	},{		
		"name"  : "color",
		"index" : "color",
		"width" : 50,
		"sortable" : true
	},{		
		"name"  : "talla",
		"index" : "talla",
		"width" : 50,
		"sortable" : true
	},{		
		"name"  : "modeloProveedor",
		"index" : "modeloProveedor",
		"width" : 100,
		"sortable" : true
	} ];
	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP43MontajeAdicionalOferta";
	this.pagerNameJQuery = "#pagerP43MontajeAdicionalOferta";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;
	this.myColumnStateName = 'gridP43PedidoAdicionalMO.colState';
	this.myColumnsState = null;
	this.isColState = null;
	this.firstLoad = true;
	this.modificado = false;
	this.titleVentaForzada = null;
	this.titleModifPorAjuste = null;
	this.titleModifPorRegularizacion = null;

	// Metodos
	this.getActualPage = function getActualPage() {
		if (this.actualPage == null)
			this.actualPage = 1;
		else
			this.actualPage = $(this.nameJQuery).getGridParam('page');

		return this.actualPage;
	}

	this.getSelectedRow = function getSelectedRow() {
		return $(this.nameJQuery).getGridParam('selrow');
	}

	this.getCellValue = function getCellValue(rowId, colName) {
		return $(this.nameJQuery).getRowData(rowId)[colName];
	}

	this.getRowNumPerPage = function getRowNumPerPage() {
		var rowNumPerPage = $(this.nameJQuery).getGridParam('rowNum');
		if (rowNumPerPage!=null && rowNumPerPage!==undefined){
			return rowNumPerPage;
		}else{
			return 10;
		}
	}

	this.getSortIndex = function getSortIndex() {
		if ($(this.nameJQuery).getGridParam('sortname') != null) {
			return $(this.nameJQuery).getGridParam('sortname');
		} else {
			return null;
		}

	}

	this.getSortOrder = function getSortOrder() {

		if ($(this.nameJQuery).getGridParam('sortorder') != null) {
			return $(this.nameJQuery).getGridParam('sortorder');
		} else {
			return "asc";
		}
	}

	this.saveObjectInLocalStorage = function(storageItemName, object) {
		if (typeof window.localStorage !== 'undefined') {
			window.localStorage
					.setItem(storageItemName, JSON.stringify(object));
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
		var colModel = jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid(
				'getGridParam', 'colModel');
		// if (colModel == null)
		// colModel = grid.cm;
		var i;
		var l = colModel.length;
		var colItem;
		var cmName;
		var postData = jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid(
				'getGridParam', 'postData');
		var columnsState = {
			search : jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid(
					'getGridParam', 'search'),
			page : jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid(
					'getGridParam', 'page'),
			sortname : jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid(
					'getGridParam', 'sortname'),
			sortorder : jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid(
					'getGridParam', 'sortorder'),
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
					gridP43PedidoAdicionalMO.myColumnStateName, JSON
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

function p43FormateoUnidadesCaja(cantidad) {
	
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

function p43ControlModifFechaHasta(fechaHastaDDMMYYYY){
	//Se controla si las cantidades pueden ser modificadas mirando la fecha hasta indicada a través del combo
	//o en su defecto la proporcionada por la fila si existe
	var modificable = true;
	var fechaControl = "";
	var diaFecha = "";
	var mesFecha = "";
	var anyoFecha = "";
	var fechaYYYMMDD = "";
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
	
	p60DibujarFechaHasta(fechaHastaDDMMYYYY);
	
 	return modificable;
}

function p43ReloadMotivosBloqueo(rowid){
	var tipoPedido = $("#"+(rowid)+"_tipoPedidoMO").val();
	var codTpBloqueo = "";
	//Control de tipo de bloqueo a controlar
	if ("P" == tipoPedido){
		codTpBloqueo = "P";
	}else if ("E" == tipoPedido){
		if ($("#"+(rowid)+"_fechaInPilMO").val() != null && $("#"+(rowid)+"_fechaInPilMO").val()!=''){
			codTpBloqueo = "EP";
		}else{
			codTpBloqueo = "E";
		}
	}
	
	var buscarPedidos = "S";
	if("1"==$("#"+(rowid)+"_perfilMO").val()){
		buscarPedidos = "N";
	}

	reloadPopupP74($("#"+(rowid)+"_codArticuloMO").val(), codTpBloqueo, $("#"+(rowid)+"_fechaInicioMO").val(), $("#"+(rowid)+"_fecha2MO").val(), $("#"+(rowid)+"_fecha3MO").val(), $("#"+(rowid)+"_fecha4MO").val(), $("#"+(rowid)+"_fecha5MO").val(), $("#"+(rowid)+"_fechaInPilMO").val(), $("#"+(rowid)+"_fechaFinMO").val(), buscarPedidos, $("#"+(rowid)+"_clasePedidoMO").val());
	//reloadPopupP74_2($("#"+(rowid)+"_codArticuloMO").val(), codTpBloqueo, $("#"+(rowid)+"_fechaInicioMO").val(), $("#"+(rowid)+"_fecha2MO").val(), $("#"+(rowid)+"_fecha3MO").val(), $("#"+(rowid)+"_fecha4MO").val(), $("#"+(rowid)+"_fecha5MO").val(), $("#"+(rowid)+"_fechaInPilMO").val(), $("#"+(rowid)+"_fechaFinMO").val(), buscarPedidos, $("#"+(rowid)+"_clasePedidoMO").val());
}

function p43CargarParametrosExcel(){
	var formInputs = "";
	var p43CmbOferta = $("#p43_cmb_oferta");
	if (p43CmbOferta.length && p43CmbOferta.combobox('getValue')!="null" && p43CmbOferta.combobox('getValue')!=null  ){
		formInputs = formInputs + "<input type='hidden' name='descPeriodo' value='"+p43CmbOferta.val()+"'>";
	}
	var p43CmbPromocion = $("#p43_cmb_promocion");
	if (p43CmbPromocion.length && p43CmbPromocion.combobox('getValue')!="null" && p43CmbPromocion.combobox('getValue')!=null  ){
		formInputs = formInputs + "<input type='hidden' name='espacioPromo' value='"+p43CmbPromocion.val()+"'>";
	}
	return formInputs;
}

function hideShowColumnsMO(){
	
	codArea = null;
	codSeccion = null;
	if ($("#p40_cmb_seccion").combobox('getValue') != null&& $("#p40_cmb_seccion").combobox('getValue') != '') {
		valorSeccion = $("#p40_cmb_seccion").combobox('getValue').split('*');
		codArea = valorSeccion[0];
		codSeccion = valorSeccion[1];
	}
	
	if(codArea==3){
		//Si es textil, se cambia el nombre de las coumnas cantidad1, cantidad2, cantidad3, cantidad4 por C 1, C 2, C 3, C 4
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setLabel', 'cantidad1', 'C1');
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setLabel', 'cantidad2', 'C2');
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setLabel', 'cantidad3', 'C3');
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setLabel', 'cantidad4', 'C4');
		
		//Añadir columna nueva
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('showCol',["color", "talla","modeloProveedor"]);
	}else{
		//Se restablecen los nombre de las cantidades
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setLabel', 'cantidad1', 'Cantid. 1');
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setLabel', 'cantidad2', 'Cantid. 2');
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setLabel', 'cantidad3', 'Cantid. 3');
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setLabel', 'cantidad4', 'Cantid. 4');
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('hideCol',["color", "talla","modeloProveedor"]);
	}
	
	if($("#p40_chk_mca").is(':checked')) {
		//El checkBox de MAC está activado
		//Se restablecen los nombre de las cantidades y descOferta
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setLabel', 'cantidad1', 'Cant. 1');
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setLabel', 'cantidad2', 'Cant. 2');
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setLabel', 'cantidad3', 'Cant. 3');
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setLabel', 'cantidad4', 'Cant. 4');
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setLabel', 'descOferta', 'Descripción');
		//Se muestra la columna descOferta
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('showCol',["descOferta"]);
	}
	else{
		//El checkBox de MAC está desactivado
		//Se restablecen los nombre de las cantidades y descOferta
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setLabel', 'cantidad1', 'Cantid. 1');
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setLabel', 'cantidad2', 'Cantid. 2');
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setLabel', 'cantidad3', 'Cantid. 3');
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setLabel', 'cantidad4', 'Cantid. 4');
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setLabel', 'descOferta', 'Desc. Oferta');
		//Se oculta la columna descOferta
		jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('hideCol',["descOferta"]);
		
	}
	jQuery(gridP43PedidoAdicionalMO.nameJQuery).jqGrid('setGridWidth', $("#p43_AreaMontajeAdicionalOferta").width(), true);
}