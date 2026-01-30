var gridP41Encargos=null;
var p41Seleccionados = new Array();
var p41SeleccionadosTotal = new Array();
var p41esTecnico =null;
var p41emptyListaSeleccionados = null;
var errorBorrado=null;
var tableFilter=null;
var tipoEncargo=null;
var textoFechaEncargo= null;
var p41ComboSI= null;
var p41ComboNO= null;
var p41BotonGuardar= null;
var p41BotonVolver= null;
var p41EstadoNoActiva = null;
var p41ConsultaMotivosBloqueo = null;

function initializeP41(){
		loadP41Encargos(locale);
}

function resetDatosP41(){
	$('#gridP41Encargos').jqGrid('clearGridData');
}

function reloadDatosEncargos() {
	reloadDataP41Encargos('N','','',null,'');
}

function p41SetHeadersTitles(data){
	
   	var colModel = $(gridP41Encargos.nameJQuery).jqGrid("getGridParam", "colModel");
    $.each(colModel, function(i) {
    	if (colModel[i].name!="rn"){
	    	$("#jqgh_gridP41Encargos_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
    	}
     });
}

function loadP41Encargos(locale){
	gridP41Encargos = new GridP41Encargos(locale);
	
	var jqxhr = $.getJSON(gridP41Encargos.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				gridP41Encargos.colNames = data.p41EncargosColNames;
				gridP41Encargos.title = data.p41EncargosGridTitle;
				gridP41Encargos.emptyRecords= data.emptyRecords;
				p41emptyListaSeleccionados = data.emptyListaSeleccionados;
				errorBorrado=data.errorBorrado;
				tableFilter= data.tableFilter;
				tipoEncargo= data.tipoEncargo;
				textoFechaEncargo= data.textoFechaEncargo;
				p41ComboSI= data.p41ComboSI;
				p41ComboNO= data.p41ComboNO;
				p41BotonGuardar= data.p41BotonGuardar;
				p41BotonVolver= data.p41BotonVolver;
				loadP41EncargosMock(gridP41Encargos);
				p41SetHeadersTitles(data);
				p41EstadoNoActiva = data.p41EstadoNoActiva;
				p41ConsultaMotivosBloqueo = data.p41ConsultaMotivosBloqueo;
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
         });
}

function loadP41EncargosMock(gridP41Encargos) {
	//para hacer cabeceras
	//jQuery("#ghwcs").jqGrid('setGroupHeaders', { useColSpanStyle: true, groupHeaders:[ {startColumnName: 'amount', numberOfColumns: 3, titleText: '<em>Price</em>'}, {startColumnName: 'closed', numberOfColumns: 2, titleText: 'Shiping'} ] });
		$(gridP41Encargos.nameJQuery).jqGrid({
			ajaxGridOptions : {
				contentType : 'application/json; charset=utf-8',
				cache : false
			},
			datatype : 'local',
			contentType : 'application/json',
			mtype : 'POST',
			colNames : gridP41Encargos.colNames,
			colModel : gridP41Encargos.cm,
			rowNum : 10,
			rowList : [ 10,20,30 ],
			height : "auto",
			autowidth : true,
			width : "auto",
			rownumbers : true,
			pager : gridP41Encargos.pagerNameJQuery,
			viewrecords : true,
			caption : gridP41Encargos.title,
			altclass: "ui-priority-secondary",
			altRows: true, //false, para que el grid no muestre cebrado
			hidegrid : false, //false, para ocultar el boton que colapsa el grid.
			sortable : true,
			multiselect: true,
			index: gridP41Encargos.sortIndex,
			sortname: gridP41Encargos.sortIndex,
			sortorder: gridP41Encargos.sortOrder,
			emptyrecords : gridP41Encargos.emptyRecords,
			gridComplete : function() {
				
				
			},
			loadComplete : function(data) {
				gridP41Encargos.actualPage = data.page;
				gridP41Encargos.localData = data;
				gridP41Encargos.sortIndex = null;
				gridP41Encargos.sortOrder = null;
				if (gridP41Encargos.firstLoad)
					jQuery(gridP41Encargos.nameJQuery).jqGrid('setGridWidth', $("#p41_AreaEncargos").width(), true);
				
				//Ocultamos la check de seleccionar todos.
				$("#cb_gridP41Encargos").attr("style", "display:none");
			},
			onPaging : function(postdata) {			
				alreadySorted = false;
				gridP41Encargos.sortIndex = null;
				gridP41Encargos.sortOrder = null;
				gridP41Encargos.saveColumnState.call($(this), this.p.remapColumns);

				reloadDataP41Encargos('S','','',null,'');
				return 'stop';
			},
			beforeSelectRow: function (rowid, e) {
			    var $myGrid = $(this),
			        i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),
			        cm = $myGrid.jqGrid('getGridParam', 'colModel');
			    
			    	if (!(cm[i].name === 'cb') && !(cm[i].name === 'mensaje' && $("#"+(rowid)+"_estadoE").val()=== 'NOACT'))
			    	{
			    		p41ModificarReferencia($("#"+(rowid)+"_codArticulo").val(),$("#"+(rowid)+"_identificador").val(),$("#"+(rowid)+"_identificadorSIA").val());
			    	}
			    	
			    	filaModificable = $("#"+(rowid)+"_modificable").val();
			    	perfil = $("#"+(rowid)+"_perfil").val();
			    	
			    return (cm[i].name === 'cb' && filaModificable != "N" && filaModificable != "B" && perfil == "3");
			},
			resizeStop: function () {
				gridP41Encargos.modificado = true;
				gridP41Encargos.saveColumnState.call($(this),gridP41Encargos.myColumnsState);
				jQuery(gridP41Encargos.nameJQuery).jqGrid('setGridWidth', $("#p41_AreaEncargos").width(), false);
            },
			onSortCol : function (index, columnIndex, sortOrder){
				gridP41Encargos.sortIndex = index;
				gridP41Encargos.sortOrder = sortOrder;
				gridP41Encargos.saveColumnState.call($(this), this.p.remapColumns);

				reloadDataP41Encargos('S','','',null,'');

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
		
		jQuery(gridP41Encargos.nameJQuery).jqGrid('navGrid',gridP41Encargos.pagerNameJQuery,{
			add:false,edit:false,del:false,search:false,refresh:false}
		); 
		
		jQuery(gridP41Encargos.nameJQuery).jqGrid('navButtonAdd',gridP41Encargos.pagerNameJQuery,{ 
			caption: tableFilter, title: "Reordenar Columnas", 
		onClickButton : function (){ 
			jQuery(gridP41Encargos.nameJQuery).jqGrid('columnChooser',
					{
	            		"done": function(perm) {
	            			if (perm) {
	            				var autowidth = true;
	            				if (gridP41Encargos.modificado == true){
	            					autowidth = false;
	            					gridP41Encargos.myColumnsState =  gridP41Encargos.restoreColumnState(gridP41Encargos.cm);
	                		    	gridP41Encargos.isColState = typeof (gridP41Encargos.myColumnsState) !== 'undefined' && gridP41Encargos.myColumnsState !== null;
	                		    	this.jqGrid("remapColumns", perm, true);
	                		    	 var colModel =jQuery(gridP41Encargos.nameJQuery).jqGrid('getGridParam', 'colModel'); 
	                		    	 var l = colModel.length;
	                		         var colItem; 
	                		         var cmName;
	                		         var colStates = gridP41Encargos.myColumnsState.colStates;
	                		         var cIndex = 2;
	                		         for (i = 0; i < l; i++) {
	                		             colItem = colModel[i];
	                		             cmName = colItem.name;
	                		             if (colItem.hidden !== true && cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {
	                		            	 
	                		            	 jQuery(gridP41Encargos.nameJQuery).jqGrid('setColProp',cmName,{width:colStates[cmName].width});
	                		            	 var cad =gridP41Encargos.nameJQuery+'_'+cmName;
	                		            	 var ancho = "'"+colStates[cmName].width+"px'";
	                		            	 var cell = jQuery('table'+gridP41Encargos.nameJQuery+' tr.jqgfirstrow td:nth-child(' + (i+1) + ')');
	                		            	 cell.css("width", colStates[cmName].width + "px");
	                		            	
	                		            	 jQuery(cad).css("width", colStates[cmName].width + "px");
	                		            	
	                		             }
	                		         }
	                		         
	            				} else {
	            					this.jqGrid("remapColumns", perm, true);
	            				}
	            				gridP41Encargos.saveColumnState.call(this, perm);
	            				jQuery(gridP41Encargos.nameJQuery).jqGrid('setGridWidth', $("#p41_AreaEncargos").width(), autowidth);
	            			}
	            		}
					}		
			); } });
}

function p41FormateoDate(cellValue, opts, rowObject) {
	
	var fechaFormateada = '';
	if (cellValue != null)
	{
		var diaFecha = parseInt(cellValue.substring(0,2),10);
		var mesFecha = parseInt(cellValue.substring(2,4),10);
		var anyoFecha = parseInt(cellValue.substring(4),10);
		
	
		fechaFormateada = $.datepicker.formatDate("D dd-M", new Date(anyoFecha, mesFecha - 1, diaFecha),{
			dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
			dayNames: $.datepicker.regional[ "es" ].dayNames,
			monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
			monthNames: $.datepicker.regional[ "es" ].monthNames
			});
	}
	
	return(fechaFormateada);
	
}

function p41TratarCajas(cellValue, opts, rowObject){
	
	
	if (null != cellValue){
		if (cellValue == true)
		{
			return "S";
		}
		else
		{
			return "N";
		}
	} else {
		return "";
	}
}

function p41TratarExcluir(cellValue, opts, rowObject){
	
	if (cellValue == true)
	{
		return "S";
	}
	else
	{
		return "N";
	}
}

function p41ImageFormatMessage(cellValue, opts, rData) {
	var imagen = "";
	var mostrarError = "none;";
	var descError = "";
	var mostrarModificado = "none;";
	var mostrarNoActiva = "none;";
	var p41EstadoNoActivaValor = "";
	
	//Controlamos los posibles errores que me lleguen para pintar el icono correspondiente.
	if (parseFloat(rData['codError']) == '1')
	{
		//Pintamos el icono de que ha ocurrido un error con la descripción de borrado
		mostrarError = "block;";
		if (rData['descError'] != '')
		{
			descError = rData['descError'];
		}
		else
		{
			descError = replaceSpecialCharacters(errorBorrado);
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
			p41EstadoNoActivaValor = p41EstadoNoActiva;
		}
	}
	
	imagen = "<div id='"+opts.rowId+"_divGuardado' align='center' style='display: "+ mostrarModificado + "'><img id='"+opts.rowId+"_imgGuardado' src='./misumi/images/floppy.png' title='"+iconoModificado+"'/></div>"; //Modificado
	imagen += "<div id='"+opts.rowId+"_divError' align='center' style='display: " + mostrarError + "'><img id='"+opts.rowId+"_imgError' src='./misumi/images/dialog-error-24.png' title='"+descError+"'/></div>"; //Error
	imagen += "<div id='"+opts.rowId+"_divNoActiva' align='center' style='display: " + mostrarNoActiva + "'><a href='#' id='"+opts.rowId+"_p41_estado' class='p41Bloqueada' onclick='javascript:p41ReloadMotivosBloqueo("+opts.rowId+")' title='"+p41ConsultaMotivosBloqueo+"'>"+p41EstadoNoActivaValor+"</a></div>"; //No Activa

	//Añadimos los valores de la columna referencia ocultos para poder utilizarlos posteriormente.
	var datoCodArticulo = "<input type='hidden' id='"+opts.rowId+"_codArticulo' value='"+rData['codArticulo']+"'>";
	imagen +=  datoCodArticulo;
	
	//Añadimos los valores de la columna si es modificable ocultos para poder utilizarlos posteriormente.
	var datoModificable = "<input type='hidden' id='"+opts.rowId+"_modificable' value='"+rData['modificable']+"'>";
	imagen +=  datoModificable;
	
	//Añadimos los valores de la columna perfil ocultos para poder utilizarlos posteriormente.
	var datoPerfil = "<input type='hidden' id='"+opts.rowId+"_perfil' value='"+rData['perfil']+"'>";
	imagen +=  datoPerfil;
	
	//Añadimos los valores de la columna identificador ocultos para poder utilizarlos posteriormente.
	var datoIdentificador = "";
	if(rData['identificador']!=null){
		datoIdentificador = "<input type='hidden' id='"+opts.rowId+"_identificador' value='"+(rData['identificador'])+"'>";		
	}else{
		datoIdentificador = "<input type='hidden' id='"+opts.rowId+"_identificador'>";		
	}
	imagen +=  datoIdentificador;
	
	//Añadimos los valores de la columna fecha entrefa con formato ocultos para poder utilizarlos posteriormente.
	var datofechaEntrega = "<input type='hidden' id='"+opts.rowId+"_fecEntregaE' value='"+rData['fecEntrega']+"'>";
	imagen +=  datofechaEntrega;
	
	//Añadimos los valores para el bloqueo
	var estado = "<input type='hidden' id='"+opts.rowId+"_estadoE' value='"+rData['estado']+"'>";
	imagen +=  estado;

	//Añadimos los valores de la columna identificador ocultos para poder utilizarlos posteriormente.
	var datoIdentificadorSIA = "";
	if(rData['identificadorSIA']!=null){
		datoIdentificadorSIA = "<input type='hidden' id='"+opts.rowId+"_identificadorSIA' value='"+(rData['identificadorSIA'])+"'>";		
	}else{
		datoIdentificadorSIA = "<input type='hidden' id='"+opts.rowId+"_identificadorSIA'>";		
	}
	imagen +=  datoIdentificadorSIA;
	
	return imagen;
}

function p41ControlesPantalla(){
	
	//Nos recorremos los registros existentes en pantalla.
	var rowsInPage = jQuery(gridP41Encargos.nameJQuery).jqGrid('getGridParam', 'rowNum');
	for (i = 0; i < rowsInPage; i++){
		
		//Tenemos que seleccionar las checks que nos llegan de sesión
		p41CheckearSeleccionados(i+1);
		
		//Controlamos las filas que vengan realizadas por Central o modificable a N del WS para pintarlas de Rojo.
		p41RegistrosCentral(i+1);
		
		//Controlamos bloquear el registro(no permitir checkear), cuando el registro no sea modificable
		//por el WS o cuando el registro ha sido realizado por Central.
		p41ControlBloqueos(i+1);
	}
	
	
}

function p41DesCheckearSeleccionados(){
	
	//Nos recorremos los registros existentes en pantalla.
	var rowsInPage = jQuery(gridP41Encargos.nameJQuery).jqGrid('getGridParam', 'rowNum');
	for (i = 0; i < rowsInPage; i++){
		
		//Tenemos que seleccionar las checks que nos llegan de sesión
		codArticulo = $("#"+(i+1)+"_codArticulo").val();
		identificador = $("#"+(i+1)+"_identificador").val();
		identificadorSIA = $("#"+(i+1)+"_identificadorSIA").val();
		
		//En pantalla los identificadores vacíos son '' pero desde el controlador se transforman a null por tratarse de campos numéricos
		if (identificador==''){
			identificador = null;
		}
		if (identificadorSIA==''){
			identificadorSIA = null;
		}

		//Nos recorremos la lista de seleccionados.
		for (j = 0; j < p41SeleccionadosTotal.length; j++){
			if ((p41SeleccionadosTotal[j].codArticulo == codArticulo)&&(p41SeleccionadosTotal[j].identificador == identificador)&&
				(p41SeleccionadosTotal[j].identificadorSIA == identificadorSIA)&&(p41SeleccionadosTotal[j].seleccionado == "S"))
			{
				jQuery(gridP41Encargos.nameJQuery).jqGrid('setSelection', i+1);
			}
		}
	}
}

function p41CheckearSeleccionados(fila){
	
	codArticulo = $("#"+(fila)+"_codArticulo").val();
	identificador = $("#"+(fila)+"_identificador").val();
	identificadorSIA = $("#"+(i+1)+"_identificadorSIA").val();

	//En pantalla los identificadores vacíos son '' pero desde el controlador se transforman a null por tratarse de campos numéricos
	if (identificador==''){
		identificador = null;
	}
	if (identificadorSIA==''){
		identificadorSIA = null;
	}

	//Nos recorremos la lista de seleccionados.
	for (j = 0; j < p41SeleccionadosTotal.length; j++){
		if ((p41SeleccionadosTotal[j].codArticulo == codArticulo)&&(p41SeleccionadosTotal[j].identificador == identificador)&&
				(p41SeleccionadosTotal[j].identificadorSIA == identificadorSIA)&&(p41SeleccionadosTotal[j].seleccionado == "S"))
		{
			jQuery(gridP41Encargos.nameJQuery).jqGrid('setSelection', fila);
		}
	}

}

function p41RegistrosCentral(fila){
	
	//filaModificable = $("#"+(fila)+"_modificable").val();
	perfil = $("#"+(fila)+"_perfil").val();

	//Si el perfil es distinto de 3 significa que es Central
	//if ((filaModificable == "N")||(perfil != "3"))
	if (perfil != "3")
	{	
		$(gridP41Encargos.nameJQuery).find("#"+(fila)).find("td").addClass("p41_columnaResaltada");
	}
}

function p41ControlBloqueos(fila){
	
	filaModificable = $("#"+(fila)+"_modificable").val();
	perfil = $("#"+(fila)+"_perfil").val();
	
	if ((filaModificable == "N") || (filaModificable == "B") ||(perfil != "3"))
	{
		//Tenemos que deshabilitar la check
		$("#jqg_"+gridP41Encargos.name+"_"+(fila)).attr("style", "display:none");
	}
}

function p41ModificarReferencia(codArticulo,identificador,identificadorSIA){

	//Antes de abrir la pantalla de modificación única por referencia, obtenemos la información necesaria para mostrarla posteriormente.
	var recordPedidoAdicionalE = new PedidoAdicionalE ($("#centerId").val() , null, null, null, codArticulo, null, 1,null, null, 
			null, null, null, null, identificador, null, null, null, [1],identificadorSIA);

	var objJson = $.toJSON(recordPedidoAdicionalE.preparePedidoAdicionalEToJsonObject());

		$("#AreaResultados .loading").css("display", "block");
		
		$.ajax({
			type : 'POST',
			url : './pedidoAdicional/openModifyDataGridE.do?page='+gridP41Encargos.getActualPage()+'&max='+gridP41Encargos.getRowNumPerPage()+'&index='+gridP41Encargos.getSortIndex()+'&sortorder='+gridP41Encargos.getSortOrder(),
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {		
				
				if (data != null && data != '' && data.codArticulo != null && data.codArticulo != '')
				{
					if (data.codError == 0){
						p41CargaDatosCalendarios(data);
						
						p60CrearCalendarios();
						
						p60DefinePedidoAdicionalE(data);
						//Primero cargamos los datos.
						p41CargaDatos(data);
						
						//Ahora establecemos los campos que son modificables
						p41ControlModificables(data);
						
						//Abrimos el popup
						$( "#p60_AreaModificacion" ).dialog( "open" );

						$("#p40AreaErrores").attr("style", "display:none");
						
						$("#AreaResultados .loading").css("display", "none");	
					}else if (data.codError == 2){
						$(function() {
							 $( "#p41dialog-confirm-noExiste" ).dialog({
								 resizable: false,
								 height:140,
								 modal: true,
								 buttons: {
								 Aceptar: function() {
								 p41NoExiste(data.codArticulo,data.identificador,data.clasePedido, data.identificadorSIA);
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

function p41NoExiste(codArticulo,identificador,clasePedido,identificadorSIA){
	reloadDataP41Encargos('S',codArticulo,identificador,clasePedido,identificadorSIA);
}

function p41FormateoCantidadesPopUp(cantidad) {
	
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

function p41FormateoUnidadesCaja(cantidad) {
	
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

function p41CargaDatosCalendarios(data){
	
	//Carga de datos para los calendarios
	$("#codCentroCalendario").val($("#centerId").val());
	$("#codArticuloCalendario").val(data.codArticulo);
	$("#identificadorCalendario").val(data.identificador);
	$("#identificadorSIACalendario").val(data.identificadorSIA);
	$("#clasePedidoCalendario").val("E");
	$("#recargarParametrosCalendario").val("S");
	$("#cargadoDSCalendario").val("N");
	
}

function p41CargaDatos(data){
	
	//Primero las etiquetas
	$("#p60_lbl_fechaInicio").text(textoFechaEncargo);
	
	//Ahora los datos
	$("#p60_fld_referencia").val(data.codArticulo);
	$("#p60_fld_denominacion").val(data.descriptionArt);
	$("#p60_fld_referenciaVisualizada").val(data.codArticuloGrid);
	$("#p60_fld_denominacionVisualizada").val(data.descriptionArtGrid);
	$("#p60_fld_tipoPedidoAdicional").val(tipoEncargo);
	$("#p60_fld_aprov").val(data.tipoAprovisionamiento);

	$("#p60_div_fechaFin").hide();
	$("#p60_div_numeroOferta").hide();
	
	
	
	
	//Establecemos la fecha mínima
	
	$("#p60_fechaInicioDatePicker").datepicker( "setDate", new Date(data.fecEntrega.substring(4), data.fecEntrega.substring(2,4) - 1, data.fecEntrega.substring(0,2)) );
	$("#p60_fechaInicioDatePicker").datepicker('refresh');
	
	$("#p60_div_cmb_excluir").show();	
	$("#p60_cmb_excluir").combobox(null);
	
	if (data.excluir == true)
	{
		$("#p60_cmb_excluir").combobox('autocomplete',p41ComboSI);
		$("#p60_cmb_excluir").combobox('comboautocomplete',null);
	}
	else
	{
		$("#p60_cmb_excluir").combobox('autocomplete',p41ComboNO);
		$("#p60_cmb_excluir").combobox('comboautocomplete',null);
	}
	if (data.tratamiento != null && data.tratamiento != "" )
	{
		$("#p60_tratamiento").show();
		options4 =          "<option value='A'>Añadir          - Añade cantidad a transmisión</option>";
		options4 = options4+"<option value='S'>Sustituir      - Machaca la cantidad transmitida por las del encargo</option>";
		options4 = options4+"<option value='F'>Fija/Mínima - Aplica la mayor cantidad entre lo transmitido y las unidades del encargo. Garantiza al menos la cantidad del encargo</option>";
		$("select#p60_cmb_tratamiento").html(options4);
		if (data.tratamiento=="A"){
			$("#p60_cmb_tratamiento").combobox('autocomplete',"Añadir");
			$("#p60_cmb_tratamiento").combobox('comboautocomplete',"Añadir         - Añade cantidad a transmisión");
		}else if(data.tratamiento == "S"){
			$("#p60_cmb_tratamiento").combobox('autocomplete',"Sustituir");
			$("#p60_cmb_tratamiento").combobox('comboautocomplete',"Sustituir      - Machaca la cantidad transmitida por las del encargo");
		}else{
			$("#p60_cmb_tratamiento").combobox('autocomplete',"Fija/Mínima");
			$("#p60_cmb_tratamiento").combobox('comboautocomplete',"Fija/Mínima - Aplica la mayor cantidad entre lo transmitido y las unidades del encargo. Garantiza al menos la cantidad del encargo");
		}
		$("#p60_cmb_tratamiento").val(data.tratamiento);
	}
	
	if (null != data.cajas){
		//Si es un centro Caprabo queda oculto el campo de cajas y excluir al ser horizonte.
		if (!esCentroCaprabo){
			if(data.showExcluirAndCajas){
				$("#p60_bloque3AreaDatosCampos1_1").show();
			}else{
				$("#p60_bloque3AreaDatosCampos1_1").hide();
				$("#p60_excluir").hide();
			}
			$(".p60_bloque3AreaDatosCampos1").css("width", "auto");
			//$("#p60_bloque3AreaDatosCampos2").removeClass("p60_bloque3AreaDatosCampos2").addClass("p60_bloque3AreaDatosCampos2Linea");
			
			$("#p60_cmb_cajas").combobox(null);
			if (data.cajas == true)
			{
				$("#p60_cmb_cajas").combobox('autocomplete',p41ComboSI);
				$("#p60_cmb_cajas").combobox('comboautocomplete',null);
			}
			else
			{
				$("#p60_cmb_cajas").combobox('autocomplete',p41ComboNO);
				$("#p60_cmb_cajas").combobox('comboautocomplete',null);
			}
		}else{
			$("#p60_bloque3AreaDatosCampos1_1").hide();
			$("#p60_excluir").hide();
		}
		$("#p60_bloque3AreaDatosCampos2Linea").css("clear", "none");
		$("#p60_bloque3AreaDatosCampos2Linea").css("padding-top", "0px");
	}
	if (data.stock == -9999){
		$("#p60_fld_stock").val("Error");
	} else {
		$("#p60_fld_stock").val(data.stock).formatNumber({format:"0.##"});
	}
	$("#p60_fld_uc").val(data.uniCajaServ).formatNumber({format:"0.##"});
	$("#p60_bloqueFrescos").hide();
	var fechaformateada = $.datepicker.formatDate("D dd", new Date(data.fecEntrega.substring(4), data.fecEntrega.substring(2,4) - 1, data.fecEntrega.substring(0,2)),{
		dayNamesShort: $.datepicker.regional[ "es" ].dayNamesMin,
		dayNames: $.datepicker.regional[ "es" ].dayNames,
		monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
		monthNames: $.datepicker.regional[ "es" ].monthNames
		});
	
	$("#p60_lbl_cantidad1").text(fechaformateada);
	$("#p60_fld_cantidad1").val(p41FormateoCantidadesPopUp(data.unidadesPedidas));
	$("#p60_lbl_cantidad2").text("");
	$("#p60_lbl_cantidad3").text("");
	$("#p60_lbl_cantidad4").text("");
	$("#p60_lbl_cantidad5").text("");
	
	$("#p60_fld_cantidad2").attr("style", "display:none");
	$("#p60_fld_cantidad3").attr("style", "display:none");
	$("#p60_fld_cantidad4").attr("style", "display:none");
	$("#p60_fld_cantidad5").attr("style", "display:none");
	
	
	//Borrar mensajes de articulos anteriores
	$("#p60_montajeMensajesFechas").hide();
	$("#p60_encargoMensajesFechas").hide();
	$("#p60_montajeFechasBloquedasEncargos").hide();
	$("#p60_montajeFechasBloquedasMontajes").hide();
	$("#p60_montajeFechasBloquedasMantenimiento").hide();
	
	if(data.mostrarLeyendaBloqueo != null && data.mostrarLeyendaBloqueo == "S"){
		//Hay que mostrar las leyendas de bloqueos, hay que realizar el control de que leyenda se muestra.
	
		//Carga de mensajes de calendarios
		$("#p60_montajeMensajesFechas").hide();
		$("#p60_encargoMensajesFechas").show();
		$("#p60_montajeFechasBloquedasEncargos").show();
		$("#p60_montajeFechasBloquedasMontajes").hide();
		$("#p60_montajeFechasBloquedasMantenimiento").hide();
	}
	
}

function p41ControlModificables(data){


	
	//Primero deshabilitamos todos los campos.
	$("#p60_fechaInicioDatePicker").datepicker('disable');
	$("#p60_cmb_excluir").combobox("disable");
	$("#p60_cmb_cajas").combobox("disable");
	$("#p60_fld_cantidad1").attr("disabled", "disabled");
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
	
	//Inicialmente con los dos botones.
	$( "#p60_AreaModificacion" ).dialog( "option", "buttons", [ { text: p41BotonGuardar, click: function() {p60GuardarPedido();} },
	                                                            { text: p41BotonVolver, click: function() { p60ControlesCierrePopUp($(this)); } } ] );
	
	//Borramos el htlm de fecha hasta para que al abrir distintos pedidos, si el pedido actual no tiene mensaje,
	//no aparezca el mensaje del pedido anterior.
	$("#p60_lbl_fechaHasta").html("");
	$("#p60_fld_fechaHasta").val("");
	
	var userPerfil = $("#userPerfil").val();
	if (userPerfil == "3"){
		//Si el perfil del usuario es igual a 3, hay que quitar el boton de guardar.
		$( "#p60_AreaModificacion" ).dialog( "option", "buttons", [ { text: p42BotonVolver, click: function() { p60ControlesCierrePopUp($(this)); } } ] );
	}
	
	$( "#p60_AreaModificacion" ).dialog( "option", "height", "485");
	$( "#p60_AreaModificacion" ).dialog( "option", "open" ,function() {$('.ui-dialog-titlebar-close').on('mousedown', function(e){e.stopPropagation();p60ControlesCierrePopUpAspa();});$(".p60_bloque3AreaDatosCampos").css("margin-top", "14px");$(".p60_bloque3AreaDatosCampos").css("width", "inherit");if($("#p60_fld_tipoPedidoAdicional").val()!=p60TipoEncargo){reloadAyuda1();}});
	//No debe ser central(Si el perfil es distinto de 3 significa que es Central) y debe ser modificable
	//Si tiene bloqueo de encargo no se permite la modificación
	if (p41ControlModifFechaHasta(data.fechaHasta) && (data.perfil != null && data.perfil == "3" && data.modificable == "S") && (data.fechaBloqueoEncargo == null || data.fechaBloqueoEncargo != "S"))
	{
		
		var fecMin = data.fechaMinima;
		$( "#p60_fechaInicioDatePicker" ).datepicker( "option", "minDate", new Date(fecMin.substring(4), fecMin.substring(2,4) - 1, fecMin.substring(0,2)) );
		//En este caso si es modificable el pedido
		$("#p60_fechaInicioDatePicker").datepicker('enable');
		$("#p60_cmb_excluir").combobox("enable");
		$("#p60_cmb_cajas").combobox("enable");
		$("#p60_fld_cantidad1").removeAttr("disabled");
		$("#p60_fld_cantidad1").focus(function(event) {
			$("#p60_fld_cantidad1").select();
		});
	}
	else
	{
		
	
		//No es modificable, debemos quitar el boton de guardar.
		$( "#p60_AreaModificacion" ).dialog( "option", "buttons", [ { text: p41BotonVolver, click: function() { p60ControlesCierrePopUp($(this)); } } ] );
		$( "#p60_AreaModificacion" ).dialog( "option", "height", "485");
	}
	
	p41EstablecerFiltrosConDec();
}

function p41EstablecerFiltrosConDec(){
	
	//Establecemos el formato para los campos de cantidades.
	$("#p60_fld_cantidad1").filter_input({regex:'[0-9,]'});
	$("#p60_fld_cantidad2").filter_input({regex:'[0-9]'});
	$("#p60_fld_cantidad3").filter_input({regex:'[0-9]'});
} 

function reloadDataP41Encargos(recarga,articulo,identificador,clasePedido,identificadorSIA) {
	var mca = "N";
	if($("#p40_chk_mca").is(':checked')) {
		mca = "S";
	}
	if (gridP41Encargos.firstLoad) {
		jQuery(gridP41Encargos.nameJQuery).jqGrid('setGridWidth', $("#p41_AreaEncargos").width(), true);
		gridP41Encargos.firstLoad = false;
        if (gridP41Encargos.isColState) {
            $(this).jqGrid("remapColumns", grid.myColumnsState.permutation, true);
        }
    } else {
    	gridP41Encargos.myColumnsState = gridP41Encargos.restoreColumnState(gridP41Encargos.cm);
    	gridP41Encargos.isColState = typeof (gridP41Encargos.myColumnsState) !== 'undefined' && gridP41Encargos.myColumnsState !== null;
    	jQuery(gridP41Encargos.nameJQuery).jqGrid('setGridWidth', $("#p41_AreaEncargos").width(), false);
    }
	
	//Obtenemos el array con los registros seleccionados
	p41ObtenerSeleccionados();

	var codArea = null;
	var codSeccion = null;
	if ($("#p40_cmb_seccion").combobox('getValue') != null && $("#p40_cmb_seccion").combobox('getValue') != '')
	{
		valorSeccion = $("#p40_cmb_seccion").combobox('getValue').split('*');
		codArea = valorSeccion[0];
		codSeccion = valorSeccion[1];
	}
	var recordPedidoAdicionalE = new PedidoAdicionalE ($("#centerId").val() , codArea, codSeccion, $("#p40_cmb_categoria").combobox('getValue'),
			$("#p40_fld_referencia").val(), null, null,null, null, null, null, null, p41SeleccionadosTotal, null, mca, null, null, [1], null);
	
	//Reseteamos el array de seleccionados.
	p41Seleccionados = new Array();
	p41SeleccionadosTotal = new Array();
	
	//DesCheckeamos lo seleccionado al recargar la lista.
	// MISUMI-364: Corrección Ticket#2022101110790783 - Encargos en Misumi
	if (recarga=='S'){
		jQuery(gridP41Encargos.nameJQuery).jqGrid('resetSelection');
	}
	p41DesCheckearSeleccionados();

	var objJson = $.toJSON(recordPedidoAdicionalE.preparePedidoAdicionalEToJsonObject());

		$("#AreaResultados .loading").css("display", "block");
		
		$.ajax({
			type : 'POST',
			url : './pedidoAdicional/loadDataGridE.do?page='+gridP41Encargos.getActualPage()+'&max='+gridP41Encargos.getRowNumPerPage()+'&index='+gridP41Encargos.getSortIndex()+'&recarga='+recarga +'&articulo='+articulo +'&identificador='+identificador +'&identificadorSIA='+identificadorSIA +'&sortorder='+gridP41Encargos.getSortOrder(),

			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {		
				$(gridP41Encargos.nameJQuery)[0].addJSONData(data.datos);
				gridP41Encargos.actualPage = data.datos.page;
				gridP41Encargos.localData = data.datos;
				
				if (data != null && data != '')
				{
					if (data.codError == 0){
						$("#p40AreaErrores").attr("style", "display:none");
						$("div#p40_AreaPestanas").attr("style", "display:block");
						$("#p40_pestanaEncargosCargada").val("S");
						
						p41SeleccionadosTotal = data.listadoSeleccionados;
						
						p41esTecnico = data.esTecnico;
						
						if (articulo != '')
						{
							//En este caso tenemos que actualizar el contador.
							$("#p40_fld_contadorEncargos").text(literalEncargos + " ("+data.contadorEncargos+")");
						}
						
		                
						$("#AreaResultados .loading").css("display", "none");	
						if (data.records == 0){
							createAlert(replaceSpecialCharacters(gridP41Encargos.emptyRecords), "ERROR");
						} 
						
						p41ControlesPantalla();
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
		// MISUMI-364: Corrección Ticket#2022101110790783 - Encargos en Misumi
		if (recarga=='S'){
			hideShowColumnsE();		
		}

}



function p41ObtenerSeleccionados(){
	
	p41Seleccionados = new Array();
	
	var selectedRowIDs = jQuery(gridP41Encargos.nameJQuery).jqGrid('getGridParam', 'selarrrow');
	var rowsInPage = jQuery(gridP41Encargos.nameJQuery).jqGrid('getGridParam', 'rowNum');

	// MISUMI-364: Corrección Ticket#2022101110790783 - Encargos en Misumi
	if (selectedRowIDs !== undefined){
		//Obtenemos las referencias seleccionadas
		for (i = 0; i < selectedRowIDs.length; i++){
			if (selectedRowIDs[i] != null && selectedRowIDs[i] != '')
			{
				p41Seleccionados.push($("#"+selectedRowIDs[i]+"_codArticulo").val()+"#"+$("#"+selectedRowIDs[i]+"_identificador").val()+"#"+$("#"+selectedRowIDs[i]+"_identificadorSIA").val());
			}
		}		
	}
	
	//Nos recorremos los registros existentes en pantalla.
	for (i = 0; i < rowsInPage; i++){
		
		codArticulo = $("#"+(i+1)+"_codArticulo").val();
		identificador = $("#"+(i+1)+"_identificador").val();
		identificadorSIA = $("#"+(i+1)+"_identificadorSIA").val();
		
		identificadorArray = identificador;
		identificadorSIAArray = identificadorSIA;

		//En pantalla los identificadores vacíos son '' pero desde el controlador se transforman a null por tratarse de campos numéricos
		if (identificador==''){
			identificador = null;
		}
		if (identificadorSIA==''){
			identificadorSIA = null;
		}

		//Nos recorremos la lista de seleccionados.
		for (j = 0; j < p41SeleccionadosTotal.length; j++){
			
			if ((p41SeleccionadosTotal[j].codArticulo == codArticulo)&&(p41SeleccionadosTotal[j].identificador == identificador)&&(p41SeleccionadosTotal[j].identificadorSIA == identificadorSIA))
			{
				//Si es una de las referencias de la página, actualizamos el valor de seleccionado.
				if ($.inArray(codArticulo+"#"+identificadorArray+"#"+identificadorSIAArray, p41Seleccionados) > -1)
				{
					p41SeleccionadosTotal[j].seleccionado = "S";
				}
				else
				{
					p41SeleccionadosTotal[j].seleccionado = "N";
				}
			}
		}
	}
}

function p41FindValidationRemove(){
	var messageVal = p41emptyListaSeleccionados;
	
	//Nos recorremos la lista de seleccionados.
	for (j = 0; j < p41SeleccionadosTotal.length; j++){
		if (p41SeleccionadosTotal[j].seleccionado == "S")
		{
			messageVal=null;
		}
	}
	return messageVal;
	
}

function p41BorrarDatos(){
	
	//Obtenemos el array con los registros seleccionados
	p41ObtenerSeleccionados();
	
	var messageVal=p41FindValidationRemove();
	
	if (messageVal!=null){
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{
		
		 $(function() {
			 $( "#p41dialog-confirm" ).dialog({
				 resizable: false,
				 height:140,
				 modal: true,
				 buttons: {
				 "Si": function() {
				 p41Remove();
				 $( this ).dialog( "close" );
				 },
				 "No": function() {
				 $( this ).dialog( "close" );
				 }
				 }
			 });
		});
	}
}		

function p41Remove(){
	
	var codArea = null;
	var codSeccion = null;
	var mca = "N";
	if ($("#p40_cmb_seccion").combobox('getValue') != null && $("#p40_cmb_seccion").combobox('getValue') != '')
	{
		valorSeccion = $("#p40_cmb_seccion").combobox('getValue').split('*');
		codArea = valorSeccion[0];
		codSeccion = valorSeccion[1];
	}
	if($("#p40_chk_mca").is(':checked')) {
		mca = "S";
	}
	var recordPedidoAdicionalE = new PedidoAdicionalE ($("#centerId").val() , codArea, codSeccion, $("#p40_cmb_categoria").combobox('getValue'),
			$("#p40_fld_referencia").val(), null, 1,null, null, null, null, null, p41SeleccionadosTotal, null, null, null, null, [1], null);

	recordPedidoAdicionalE.mca = mca;
	//DesCheckeamos lo seleccionado al llamar a borrar.
	p41DesCheckearSeleccionados();
	
	//Reseteamos el array de seleccionados.
	p41Seleccionados = new Array();
	p41SeleccionadosTotal = new Array();
	

	var objJson = $.toJSON(recordPedidoAdicionalE.preparePedidoAdicionalEToJsonObject());

		$("#AreaResultados .loading").css("display", "block");
		
		$.ajax({
			type : 'POST',
			url : './pedidoAdicional/removeDataGridE.do?max='+gridP41Encargos.getRowNumPerPage()+'&index='+gridP41Encargos.getSortIndex()+'&sortorder='+gridP41Encargos.getSortOrder(),
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {		
				$(gridP41Encargos.nameJQuery)[0].addJSONData(data.datos);
				gridP41Encargos.actualPage = data.datos.page;
				gridP41Encargos.localData = data.datos;
				
				if (data != null && data != '')
				{
					if (data.codError == 0){
						$("#p40AreaErrores").attr("style", "display:none");
						$("div#p40_AreaPestanas").attr("style", "display:block");
						$("#p40_pestanaEncargosCargada").val("S");
						
						p41SeleccionadosTotal = data.listadoSeleccionados;
						
						p41esTecnico = data.esTecnico;
						
						//Actualizamos el contador de encargos tras el borrado.
						$("#p40_fld_contadorEncargos").text(literalEncargos + " ("+data.contadorEncargos+")");
		                
						$("#AreaResultados .loading").css("display", "none");	
						if (data.records == 0){
							createAlert(replaceSpecialCharacters(gridP41Encargos.emptyRecords), "ERROR");
						} 
						
						p41ControlesPantalla();
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

function p51FormateoCantidades(cellValue, opts, rData) {
	
	if (cellValue != '')
	{
		//Actualizamos el formateo de los campos a dos decimales
		return $.formatNumber(cellValue,{format:"0.00",locale:"es"});
	}
	else
	{
		return '';
	}
}		


/*Clase de constantes para el GRID de encargos*/
function GridP41Encargos (locale){
	// Atributos
	this.name = "gridP41Encargos"; 
	this.nameJQuery = "#gridP41Encargos"; 
	this.i18nJSON = './misumi/resources/p41PedidoAdicionalE/p41pedidoAdicionalE_' + locale + '.json';
	this.colNames = null;
	this.cm = [{
					"name" : "mensaje",
					"index":"mensaje", 
					"formatter": p41ImageFormatMessage,
					"fixed":true,
					"width" : 75,
					"sortable" : true
				},{		
					"name"  : "codArticuloGrid",
					"index" : "codArticuloGrid",
					"width" : 60,
					"sortable" : true
				},{
					"name"  : "agrupacion",
					"index" : "agrupacion", 
					"width" : 70,
					"hidden" : true,
					"sortable" : true
				},{
					"name"  : "descriptionArtGrid",
					"index" : "descriptionArtGrid", 
					"width" : 230,
					"sortable" : true
					},{
					"name"  : "fecEntrega",
					"index" : "fecEntrega",
					"formatter": p41FormateoDate,
					"width" : 65,
					"sortable" : true
				},{
					"name"  : "unidadesPedidas",
					"index" : "unidadesPedidas", 
					"formatter" : p41FormateoCantidadesPopUp,
					"width" : 90,
					"sortable" : true
				},{
					"name"  : "cajasPedidas",
					"index" : "cajasPedidas", 
					"formatter" : p41FormateoCantidadesPopUp,
					"width" : 80,
					"sortable" : true
				},{
					"name"  : "uniCajaServ",
					"index" : "uniCajaServ",
					"formatter" : p41FormateoUnidadesCaja,
					"width" : 20,
					"sortable" : true
				},{
					"name"  : "cajas",
					"index" : "cajas", 
					"formatter": p41TratarCajas,
					"width" : 40,
					"sortable" : true
				},{
					"name"  : "excluir",
					"index" : "excluir", 
					"formatter": p41TratarExcluir,
					"width" : 40,
					"sortable" : true
				},{
					"name"  : "color",
					"index" : "color", 
					"width" : 30,
					"sortable" : true
				},{
					"name"  : "talla",
					"index" : "talla", 
					"width" : 30,
					"sortable" : true
				},{
					"name"  : "modeloProveedor",
					"index" : "modeloProveedor", 
					"width" : 40,
					"sortable" : true
				}
			];
	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP41Encargos"; 
	this.pagerNameJQuery = "#pagerP41Encargos";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;
	this.myColumnStateName = 'gridP41Encargos.colState';
    this.myColumnsState = null;
    this.isColState = null;
    this.firstLoad = true;
    this.modificado = false;
	this.titleVentaForzada = null;
	this.titleModifPorAjuste = null;
	this.titleModifPorRegularizacion = null;


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
		// MISUMI-364: Corrección Ticket#2022101110790783 - Encargos en Misumi
		var rowNumPerPage = $(this.nameJQuery).getGridParam('rowNum');
		if (rowNumPerPage!=null && rowNumPerPage!==undefined){
			return rowNumPerPage;
		}else{
			return 10;
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
        var colModel =jQuery(gridP41Encargos.nameJQuery).jqGrid('getGridParam', 'colModel'); 
        //if (colModel == null)
       //	 colModel = grid.cm;
        var i;
        var l = colModel.length;
        var colItem; 
        var cmName;
        var postData = jQuery(gridP41Encargos.nameJQuery).jqGrid('getGridParam', 'postData');
        var columnsState = {
                search: jQuery(gridP41Encargos.nameJQuery).jqGrid('getGridParam', 'search'),
                page: jQuery(gridP41Encargos.nameJQuery).jqGrid('getGridParam', 'page'),
                sortname: jQuery(gridP41Encargos.nameJQuery).jqGrid('getGridParam', 'sortname'),
                sortorder: jQuery(gridP41Encargos.nameJQuery).jqGrid('getGridParam', 'sortorder'),
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
            window.localStorage.setItem(gridP41Encargos.myColumnStateName, JSON.stringify(columnsState));
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
	
	this.clearGrid = function clearGrid() {
		$(this.nameJQuery).jqGrid('GridUnload');
	}
	
}

function p41ControlModifFechaHasta(fechaHastaDDMMYYYY){
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

function p41ReloadMotivosBloqueo(rowid){
	var buscarPedidos = "S";
	if("1"==$("#"+(rowid)+"_perfil").val()){
		buscarPedidos = "N";
	}
	reloadPopupP74($("#"+(rowid)+"_codArticulo").val(), "E", $("#"+(rowid)+"_fecEntregaE").val(), "", "", "", "", "", $("#"+(rowid)+"_fecEntregaE").val(), buscarPedidos, "1");
	//reloadPopupP74_2($("#"+(rowid)+"_codArticulo").val(), "E", $("#"+(rowid)+"_fecEntregaE").val(), "", "", "", "", "", $("#"+(rowid)+"_fecEntregaE").val(), buscarPedidos, "1");
}

function hideShowColumnsE(){
	
	codArea = null;
	codSeccion = null;
	if ($("#p40_cmb_seccion").combobox('getValue') != null&& $("#p40_cmb_seccion").combobox('getValue') != '') {
		valorSeccion = $("#p40_cmb_seccion").combobox('getValue').split('*');
		codArea = valorSeccion[0];
		codSeccion = valorSeccion[1];
	}
	
	if(codArea==3){
		//Si es textil, se cambia el nombre de las coumnas cantidad1, cantidad2, cantidad3, cantidad4 por C 1, C 2, C 3, C 4
		jQuery(gridP41Encargos.nameJQuery).jqGrid('setLabel', 'cantidad1', 'C 1');
		jQuery(gridP41Encargos.nameJQuery).jqGrid('setLabel', 'cantidad2', 'C 2');
		jQuery(gridP41Encargos.nameJQuery).jqGrid('setLabel', 'cantidad3', 'C 3');
		jQuery(gridP41Encargos.nameJQuery).jqGrid('setLabel', 'cantidad4', 'C 4');
		
		//Añadir columna nueva
		jQuery(gridP41Encargos.nameJQuery).jqGrid('showCol',["color", "talla","modeloProveedor"]);
		jQuery(gridP41Encargos.nameJQuery).jqGrid('setGridWidth', $("#p41_AreaEncargos").width(), true);
		//COLOR, TALLA, MODELO, PROVEEDOR
		
		
		
	}else{
		
		//Se restablecen los nombre de las cantidades
		jQuery(gridP41Encargos.nameJQuery).jqGrid('setLabel', 'cantidad1', 'Cantid. 1');
		jQuery(gridP41Encargos.nameJQuery).jqGrid('setLabel', 'cantidad2', 'Cantid. 2');
		jQuery(gridP41Encargos.nameJQuery).jqGrid('setLabel', 'cantidad3', 'Cantid. 3');
		jQuery(gridP41Encargos.nameJQuery).jqGrid('setLabel', 'cantidad4', 'Cantid. 4');
		jQuery(gridP41Encargos.nameJQuery).jqGrid('hideCol',["color", "talla","modeloProveedor"]);
		jQuery(gridP41Encargos.nameJQuery).jqGrid('setGridWidth', $("#p41_AreaEncargos").width(), true);
		
		
	}
}