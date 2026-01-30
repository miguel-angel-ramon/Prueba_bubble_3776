var gridP42PedidoAdicionalM=null;
var p42Seleccionados = new Array();
var p42SeleccionadosTotal = new Array();
var p42esTecnico =null;
var p42emptyListaSeleccionados = null;
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
var sinOferta=null;
var textoFechaInicio=null;
var textoImplInicial= null;
var textoImplFinal=null;
var p42ComboSI= null;
var p42ComboNO= null;
var p42BotonGuardar= null;
var p42BotonVolver= null;
var p42EstadoNoActiva = null;
var p42ConsultaMotivosBloqueo = null;

var p42DescPeriodo = null;
var p42EspacioPromo = null;
var p42CmbOfertaTodos = null;
var p42CmbPromocionTodos = null;

function initializeP42(){
	$("#p42_cmb_oferta").combobox(null);
	$("#p42_cmb_oferta").combobox({
		selected: function(event, ui) {
			if (null != ui.item.value && ui.item.value != ""){
				var comboOferta = ui.item.value;
				p42DescPeriodo = comboOferta;
			} else {
				p42DescPeriodo = null;
			}

			reloadDataP42PedidoAdicionalM('N','','',null,'');  
		}  
	,	
	changed: function(event, ui) { 
		if (null != ui.item && null != ui.item.value && ui.item.value != ""){
			var comboOferta = ui.item.value;
			p42DescPeriodo = comboOferta;
		} else {
			p42DescPeriodo = null;
		}

		reloadDataP42PedidoAdicionalM('N','','',null,'');
	}
	});
	$("#p42_cmb_oferta").combobox('autocomplete',p42CmbOfertaTodos);

	$("#p42_cmb_promocion").combobox(null);
	$("#p42_cmb_promocion").combobox({
		selected: function(event, ui) {
			if (null != ui.item.value && ui.item.value != ""){
				var comboPromocion = ui.item.value;
				p42EspacioPromo = comboPromocion;
			} else {
				p42EspacioPromo = null;
			}

			reloadDataP42PedidoAdicionalM('N','','',null,'');  
		}  
	,	
	changed: function(event, ui) { 
		if (null != ui.item && null != ui.item.value && ui.item.value != ""){
			var comboPromocion = ui.item.value;
			p42EspacioPromo = comboPromocion;
		} else {
			p42EspacioPromo = null;
		}

		reloadDataP42PedidoAdicionalM('N','','',null,'');
	}
	});
	$("#p42_cmb_promocion").combobox('autocomplete',p42CmbPromocionTodos);

	loadP42PedidoAdicionalM(locale);
}

function resetDatosP42(){
	$('#gridP42MontajeAdicional').jqGrid('clearGridData');
}

function reloadMontaje() {
	$("#p40_pestanaMontajeCargada").val("N");
	reloadDataP42PedidoAdicionalM('N','','',null,'');

}

function p42SetHeadersTitles(data){

	var colModel = $(gridP42PedidoAdicionalM.nameJQuery).jqGrid("getGridParam", "colModel");
	$.each(colModel, function(i) {
		if (colModel[i].name!="rn"){
			$("#jqgh_gridP42MontajeAdicional_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
		}
	});
}

function loadP42PedidoAdicionalM(locale){
	gridP42PedidoAdicionalM = new GridP42PedidoAdicionalM(locale);

	var jqxhr = $.getJSON(gridP42PedidoAdicionalM.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		gridP42PedidoAdicionalM.colNames = data.p42PedidoAdicionalMColNames;
		gridP42PedidoAdicionalM.title = data.p42PedidoAdicionalMGridTitle;
		gridP42PedidoAdicionalM.emptyRecords= data.emptyRecords;
		p42emptyListaSeleccionados = data.emptyListaSeleccionados;
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
		sinOferta= data.sinOferta;
		textoFechaInicio= data.textoFechaInicio;
		textoImplInicial=data.textoImplInicial;
		textoImplFinal=data.textoImplFinal;
		p42ComboSI= data.p42ComboSI;
		p42ComboNO= data.p42ComboNO;
		p42BotonGuardar= data.p42BotonGuardar;
		p42BotonVolver= data.p42BotonVolver;
		p42EstadoNoActiva = data.p42EstadoNoActiva;
		p42ConsultaMotivosBloqueo = data.p42ConsultaMotivosBloqueo;
		p42CmbOfertaTodos: data.p42CmbOfertaTodos;
		p42CmbPromocionTodos: data.p42CmbPromocionTodos;

		loadP42PedidoAdicionalMMock(gridP42PedidoAdicionalM);
		p42SetHeadersTitles(data);
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}


function loadP42PedidoAdicionalMMock(gridP42PedidoAdicionalM) {
	$(gridP42PedidoAdicionalM.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		datatype : 'local',
		contentType : 'application/json',
		mtype : 'POST',
		colNames : gridP42PedidoAdicionalM.colNames,
		colModel : gridP42PedidoAdicionalM.cm,
		rowNum : 10,
		rowList : [ 10, 20, 30 ],
		height : "auto",
		autowidth : true,
		width : "auto",
		rownumbers : true,
		pager : gridP42PedidoAdicionalM.pagerNameJQuery,
		viewrecords : true,
		caption : gridP42PedidoAdicionalM.title,
		altclass: "ui-priority-secondary",
		altRows: true, //false, para que el grid no muestre cebrado
		hidegrid : false, //false, para ocultar el boton que colapsa el grid.
		sortable : true,
		multiselect: true,
		index: gridP42PedidoAdicionalM.sortIndex,
		sortname: gridP42PedidoAdicionalM.sortIndex,
		sortorder: gridP42PedidoAdicionalM.sortOrder,
		emptyrecords : gridP42PedidoAdicionalM.emptyRecords,
		gridComplete : function() {


		},
		loadComplete : function(data) {
			gridP42PedidoAdicionalM.actualPage = data.page;
			gridP42PedidoAdicionalM.localData = data;
			gridP42PedidoAdicionalM.sortIndex = null;
			gridP42PedidoAdicionalM.sortOrder = null;
			if (gridP42PedidoAdicionalM.firstLoad)
				jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setGridWidth', $("#p42_AreaMontajeAdicional").width(), true);

			//Ocultamos la check de seleccionar todos.
			$("#cb_"+gridP42PedidoAdicionalM.name).attr("style", "display:none");
		},
		onPaging : function(postdata) {			
			alreadySorted = false;
			gridP42PedidoAdicionalM.sortIndex = null;
			gridP42PedidoAdicionalM.sortOrder = null;
			gridP42PedidoAdicionalM.saveColumnState.call($(this), this.p.remapColumns);
			reloadDataP42PedidoAdicionalM('S','','',null,'');

			return 'stop';
		},
		beforeSelectRow: function (rowid, e) {
			var $myGrid = $(this),
			i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),
			cm = $myGrid.jqGrid('getGridParam', 'colModel');
			if (!(cm[i].name === 'cb') && !(cm[i].name === 'mensaje' && $("#"+(rowid)+"_estadoM").val()=== 'NOACT'))
			{
				var idVegalsa=$("#"+(rowid)+"_identificadorVegalsaM").val();
				p42ModificarReferencia($("#"+(rowid)+"_clasePedidoM").val(),$("#"+(rowid)+"_codArticuloM").val(),$("#"+(rowid)+"_esPlanogramaM").val(),$("#"+(rowid)+"_identificadorM").val(),$("#"+(rowid)+"_fechaInicioM").val(), $("#"+(rowid)+"_noGestionaPblM").val(), $("#"+(rowid)+"_identificadorSIAM").val(), idVegalsa);
			}

			filaBorrable = $("#"+(rowid)+"_borrableM").val();
			perfil = $("#"+(rowid)+"_perfilM").val();
			clasePedido = $("#"+(rowid)+"_clasePedidoM").val();
			var modificableIndiv = $("#"+(rowid)+"_modificableIndivM").val();
			var noGestionaPbl = $("#"+(rowid)+"_noGestionaPblM").val();

			//Se permite el check si es de tipo Q o T
			var ultimoCaracterModifIndiv = "";
			if (modificableIndiv != null && modificableIndiv != ""){
				ultimoCaracterModifIndiv = modificableIndiv.substring(modificableIndiv.length - 1);
			}

			return (cm[i].name === 'cb' && ((clasePedido=="7" && filaBorrable != "N") || (clasePedido!="7" && (ultimoCaracterModifIndiv == 'Q' || ultimoCaracterModifIndiv == 'T' || ((filaBorrable != "N") && (perfil == "3")) || (noGestionaPbl != null && noGestionaPbl =="S" && modificableIndiv.indexOf("S") >= 0)))));
		},
		resizeStop: function () {
			gridP42PedidoAdicionalM.modificado = true;
			gridP42PedidoAdicionalM.saveColumnState.call($(this),gridP42PedidoAdicionalM.myColumnsState);
			jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setGridWidth', $("#p42_AreaMontajeAdicional").width(), false);
		},
		onSortCol : function (index, columnIndex, sortOrder){
			gridP42PedidoAdicionalM.sortIndex = index;
			gridP42PedidoAdicionalM.sortOrder = sortOrder;
			gridP42PedidoAdicionalM.saveColumnState.call($(this), this.p.remapColumns);
			reloadDataP42PedidoAdicionalM('S','','',null,'');

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

	jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('navGrid',gridP42PedidoAdicionalM.pagerNameJQuery,{
		add:false,edit:false,del:false,search:false,refresh:false}
	); 

	jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('navButtonAdd',gridP42PedidoAdicionalM.pagerNameJQuery,{ 
		caption: tableFilter, title: "Reordenar Columnas", 
		onClickButton : function (){ 
			jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('columnChooser',
					{
				"done": function(perm) {
					if (perm) {
						var autowidth = true;
						if (gridP42PedidoAdicionalM.modificado == true){
							autowidth = false;
							gridP42PedidoAdicionalM.myColumnsState =  gridP42PedidoAdicionalM.restoreColumnState(gridP42PedidoAdicionalM.cm);
							gridP42PedidoAdicionalM.isColState = typeof (gridP42PedidoAdicionalM.myColumnsState) !== 'undefined' && gridP42PedidoAdicionalM.myColumnsState !== null;
							this.jqGrid("remapColumns", perm, true);
							var colModel =jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('getGridParam', 'colModel'); 
							var l = colModel.length;
							var colItem; 
							var cmName;
							var colStates = gridP42PedidoAdicionalM.myColumnsState.colStates;
							var cIndex = 2;
							for (i = 0; i < l; i++) {
								colItem = colModel[i];
								cmName = colItem.name;
								if (colItem.hidden !== true && cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {

									jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setColProp',cmName,{width:colStates[cmName].width});
									var cad =gridP42PedidoAdicionalM.nameJQuery+'_'+cmName;
									var ancho = "'"+colStates[cmName].width+"px'";
									var cell = jQuery('table'+gridP42PedidoAdicionalM.nameJQuery+' tr.jqgfirstrow td:nth-child(' + (i+1) + ')');
									cell.css("width", colStates[cmName].width + "px");

									jQuery(cad).css("width", colStates[cmName].width + "px");

								}
							}

						} else {
							this.jqGrid("remapColumns", perm, true);
						}
						gridP42PedidoAdicionalM.saveColumnState.call(this, perm);
						jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setGridWidth', $("#p42_AreaMontajeAdicional").width(), autowidth);
					}
				}
					}		
			); } });
}

function p42FormateoDate(cellValue, opts, rowObject) {

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

function p42TratarExcluir(cellValue, opts, rowObject){

	if (cellValue == true)
	{
		return "S";
	}
	else
	{
		return "N";
	}
}

function p42ImageFormatMessage(cellValue, opts, rData) {

	var imagen = "";
	var mostrarError = "none;";
	var descError = "";
	var mostrarModificado = "none;";
	var mostrarNoActiva = "none;";
	var p42EstadoNoActivaValor = "";

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
			p42EstadoNoActivaValor = p42EstadoNoActiva;
		}
	}

	imagen = "<div id='"+opts.rowId+"_divGuardadoM' align='center' style='display: "+ mostrarModificado + "'><img id='"+opts.rowId+"_imgGuardado' src='./misumi/images/floppy.png' title='"+iconoModificado+"'/></div>"; //Modificado
	imagen += "<div id='"+opts.rowId+"_divErrorM' align='center' style='display: " + mostrarError + "'><img id='"+opts.rowId+"_imgError' src='./misumi/images/dialog-error-24.png' title='"+descError+"'/></div>"; //Error
	imagen += "<div id='"+opts.rowId+"_divNoActiva' align='center' style='display: " + mostrarNoActiva + "'><a href='#' id='"+opts.rowId+"_p42_estado' class='p42Bloqueada' onclick='javascript:p42ReloadMotivosBloqueo("+opts.rowId+")' title='"+p42ConsultaMotivosBloqueo+"'>"+p42EstadoNoActivaValor+"</a></div>"; //No Activa

	//Añadimos los valores de la columna clasePedido ocultos para poder utilizarlos posteriormente.
	var datoClasePedido = "<input type='hidden' id='"+opts.rowId+"_clasePedidoM' value='"+rData['clasePedido']+"'>";
	imagen +=  datoClasePedido;

	//Añadimos los valores de la columna referencia ocultos para poder utilizarlos posteriormente.
	var datoCodArticulo = "<input type='hidden' id='"+opts.rowId+"_codArticuloM' value='"+rData['codArticulo']+"'>";
	imagen +=  datoCodArticulo;

	//Añadimos los valores de la columna si es modificable ocultos para poder utilizarlos posteriormente.
	var datoModificable = "<input type='hidden' id='"+opts.rowId+"_modificableM' value='"+rData['modificable']+"'>";
	imagen +=  datoModificable;

	//Añadimos los valores de la columna si es modificable individual ocultos para poder utilizarlos posteriormente.
	var datoModificableIndiv = "<input type='hidden' id='"+opts.rowId+"_modificableIndivM' value='"+rData['modificableIndiv']+"'>";
	imagen +=  datoModificableIndiv;

	//Añadimos los valores de la columna tipo de pedido ocultos para poder utilizarlos posteriormente.
	var datoTipoPedido = "<input type='hidden' id='"+opts.rowId+"_tipoPedidoM' value='"+rData['tipoPedido']+"'>";
	imagen +=  datoTipoPedido;

	//Añadimos los valores de la columna si es borrable ocultos para poder utilizarlos posteriormente.
	var datoBorrable = "<input type='hidden' id='"+opts.rowId+"_borrableM' value='"+rData['borrable']+"'>";
	imagen +=  datoBorrable;

	//Añadimos los valores de la columna perfil ocultos para poder utilizarlos posteriormente.
	var datoPerfil = "<input type='hidden' id='"+opts.rowId+"_perfilM' value='"+rData['perfil']+"'>";
	imagen +=  datoPerfil;

	//Añadimos los valores de la columna esPlanograma ocultos para poder utilizarlos posteriormente.
	var datoPlanograma = "<input type='hidden' id='"+opts.rowId+"_esPlanogramaM' value='"+rData['esPlanograma']+"'>";
	imagen +=  datoPlanograma;

	//Añadimos los valores de la columna identificador ocultos para poder utilizarlos posteriormente.
	var datoIdentificador = "";
	if(rData['identificador']!=null){
		datoIdentificador = "<input type='hidden' id='"+opts.rowId+"_identificadorM' value='"+(rData['identificador'])+"'>";		
	}else{
		datoIdentificador = "<input type='hidden' id='"+opts.rowId+"_identificadorM'>";		
	}

	imagen +=  datoIdentificador;

	//Añadimos los valores de la columna fecha inicio con formato ocultos para poder utilizarlos posteriormente.
	var datofechaInicio = "<input type='hidden' id='"+opts.rowId+"_fechaInicioM' value='"+(rData['fechaInicio']!=null?rData['fechaInicio']:'')+"'>";
	imagen +=  datofechaInicio;

	//Añadimos los valores de la columna fecha2 con formato ocultos para poder utilizarlos posteriormente.
	var datofecha2 = "<input type='hidden' id='"+opts.rowId+"_fecha2M' value='"+(rData['fecha2']!=null?rData['fecha2']:'')+"'>";
	imagen +=  datofecha2;

	//Añadimos los valores de la columna fecha3 con formato ocultos para poder utilizarlos posteriormente.
	var datofecha3 = "<input type='hidden' id='"+opts.rowId+"_fecha3M' value='"+(rData['fecha3']!=null?rData['fecha3']:'')+"'>";
	imagen +=  datofecha3;

	//Añadimos los valores de la columna fecha4 con formato ocultos para poder utilizarlos posteriormente.
	var datofecha4 = "<input type='hidden' id='"+opts.rowId+"_fecha4M' value='"+(rData['fecha4']!=null?rData['fecha4']:'')+"'>";
	imagen +=  datofecha4;

	//Añadimos los valores de la columna fecha5 con formato ocultos para poder utilizarlos posteriormente.
	var datofecha5 = "<input type='hidden' id='"+opts.rowId+"_fecha5M' value='"+(rData['fecha5']!=null?rData['fecha5']:'')+"'>";
	imagen +=  datofecha5;

	//Añadimos los valores de la columna fechaInPil con formato ocultos para poder utilizarlos posteriormente.
	var datofechaInPil = "<input type='hidden' id='"+opts.rowId+"_fechaInPilM' value='"+(rData['fechaPilada']!=null?rData['fechaPilada']:'')+"'>";
	imagen +=  datofechaInPil;

	//Añadimos los valores de la columna fecha fin con formato ocultos para poder utilizarlos posteriormente.
	var datofechaFin = "<input type='hidden' id='"+opts.rowId+"_fechaFinM' value='"+(rData['fechaFin']!=null?rData['fechaFin']:'')+"'>";
	imagen +=  datofechaFin;

	//Añadimos los valores para el bloqueo
	var estado = "<input type='hidden' id='"+opts.rowId+"_estadoM' value='"+rData['estado']+"'>";
	imagen +=  estado;

	//Añadimos los valores de la columna noGestionaPbl.
	var datoNoGestionaPbl = "<input type='hidden' id='"+opts.rowId+"_noGestionaPblM' value='"+rData['noGestionaPbl']+"'>";
	imagen +=  datoNoGestionaPbl;

	//Añadimos los valores de la columna identificadorSIA ocultos para poder utilizarlos posteriormente.
	var datoIdentificadorSIA = "";
	if(rData['identificadorSIA']!=null){
		datoIdentificadorSIA = "<input type='hidden' id='"+opts.rowId+"_identificadorSIAM' value='"+(rData['identificadorSIA'])+"'>";		
	}else{
		datoIdentificadorSIA = "<input type='hidden' id='"+opts.rowId+"_identificadorSIAM'>";		
	}

	imagen +=  datoIdentificadorSIA;

	//Añadimos los valores de la columna identificadorVegalsa ocultos para poder utilizarlos posteriormente.
	var datoIdentificadorVegalsa = "";
	if(rData['identificadorVegalsa']!=null){
		datoIdentificadorVegalsa = "<input type='hidden' id='"+opts.rowId+"_identificadorVegalsaM' value='"+(rData['identificadorVegalsa'])+"'>";		
	}else{
		datoIdentificadorVegalsa = "<input type='hidden' id='"+opts.rowId+"_identificadorVegalsaM'>";		
	}
	
	imagen +=  datoIdentificadorVegalsa;


	return imagen;
}

function p42ControlesPantalla(){

	//Nos recorremos los registros existentes en pantalla.
	var rowsInPage = jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('getGridParam', 'rowNum');
	for (i = 0; i < rowsInPage; i++){

		//Tenemos que seleccionar las checks que nos llegan de sesión
		p42CheckearSeleccionados(i+1);

		//Controlamos las filas que vengan realizadas por Central o modificable a N del WS para pintarlas de Rojo.
		p42RegistrosCentral(i+1);

		//Controlamos bloquear el registro(no permitir checkear), cuando el registro no sea modificable
		//por el WS o cuando el registro ha sido realizado por Central.
		p42ControlBloqueos(i+1);
	}


}

function p42DesCheckearSeleccionados(){

	//Nos recorremos los registros existentes en pantalla.
	var rowsInPage = jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('getGridParam', 'rowNum');
	for (i = 0; i < rowsInPage; i++){

		//Tenemos que seleccionar las checks que nos llegan de sesión
		var clasePedido = $("#"+(i+1)+"_clasePedidoM").val();
		var codArticulo = $("#"+(i+1)+"_codArticuloM").val();
		var identificador = $("#"+(i+1)+"_identificadorM").val();
		var identificadorSIA = $("#"+(i+1)+"_identificadorSIAM").val();
		var identificadorVegalsa = $("#"+(i+1)+"_identificadorVegalsaM").val();

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
		for (j = 0; j < p42SeleccionadosTotal.length; j++){
			if ((p42SeleccionadosTotal[j].identificadorVegalsa==null)&&(p42SeleccionadosTotal[j].clasePedido == clasePedido)&&(p42SeleccionadosTotal[j].codArticulo == codArticulo)&&(p42SeleccionadosTotal[j].identificador == identificador)&&
					(p42SeleccionadosTotal[j].identificadorSIA == identificadorSIA)&&(p42SeleccionadosTotal[j].seleccionado == "S"))
			{
				jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setSelection', i+1);
			}else if (p42SeleccionadosTotal[j].identificadorVegalsa!=null && p42SeleccionadosTotal[j].identificadorVegalsa==identificadorVegalsa && (p42SeleccionadosTotal[j].seleccionado == "S")){
				jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setSelection', i+1);
			}
		}
	}
}

function p42CheckearSeleccionados(fila){

	var clasePedido = $("#"+(fila)+"_clasePedidoM").val();
	var codArticulo = $("#"+(fila)+"_codArticuloM").val();
	var identificador = $("#"+(fila)+"_identificadorM").val();
	var identificadorSIA = $("#"+(i+1)+"_identificadorSIAM").val();
	var identificadorVegalsa = $("#"+(i+1)+"_identificadorVegalsaM").val();
	
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
	for (j = 0; j < p42SeleccionadosTotal.length; j++){
		if ((p42SeleccionadosTotal[j].identificadorVegalsa==null)&&(p42SeleccionadosTotal[j].clasePedido == clasePedido)&&(p42SeleccionadosTotal[j].codArticulo == codArticulo)&&(p42SeleccionadosTotal[j].identificador == identificador)&&
				(p42SeleccionadosTotal[j].identificadorSIA == identificadorSIA)&&(p42SeleccionadosTotal[j].seleccionado == "S"))
		{
			jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setSelection', fila);
		}else if (p42SeleccionadosTotal[j].identificadorVegalsa!=null && p42SeleccionadosTotal[j].identificadorVegalsa==identificadorVegalsa && (p42SeleccionadosTotal[j].seleccionado == "S")){
			jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setSelection', fila);
		}
	}

}

function p42RegistrosCentral(fila){

	//filaBorrable = $("#"+(fila)+"_borrableM").val();
	filaPlanogramada = $("#"+(fila)+"_esPlanogramaM").val();
	perfil = $("#"+(fila)+"_perfilM").val();

	//Si el perfil es distinto de 3 significa que es Central
	//Además si es planogramada.
	//if ((filaBorrable == "N")||(perfil != "3"))
	if ((filaPlanogramada == "S")||(perfil != "3"))
	{	
		$(gridP42PedidoAdicionalM.nameJQuery).find("#"+(fila)).find("td").addClass("p42_columnaResaltada");
	}
}

function p42ControlBloqueos(fila){

	var filaBorrable = $("#"+(fila)+"_borrableM").val();
	var perfil = $("#"+(fila)+"_perfilM").val();
	var clasePedido = $("#"+(fila)+"_clasePedidoM").val();
	var modificableIndiv = $("#"+(fila)+"_modificableIndivM").val();
	var noGestionaPbl = $("#"+(fila)+"_noGestionaPblM").val();

	//Se permite el check si es de tipo Q o T
	var ultimoCaracterModifIndiv = "";
	if (modificableIndiv != null && modificableIndiv != ""){
		ultimoCaracterModifIndiv = modificableIndiv.substring(modificableIndiv.length - 1);
	}

	if (noGestionaPbl != null && noGestionaPbl =="S"){
		if (modificableIndiv.indexOf("S") < 0){ //Si no está marcarda ninguna cantidad modificable no se visualiza el check
			$("#jqg_"+gridP42PedidoAdicionalM.name+"_"+(fila)).attr("style", "display:none");
		}
	}
	else if (clasePedido == "7")
	{
		// Todos los registros tipo 7, aunque seán de central son modificables si lo permite el WS
		if (filaBorrable == "N"){
			//Tenemos que deshabilitar la check
			$("#jqg_"+gridP42PedidoAdicionalM.name+"_"+(fila)).attr("style", "display:none");
		}
	}
	else if (ultimoCaracterModifIndiv != 'Q' && ultimoCaracterModifIndiv != 'T' && ((filaBorrable == "N")||(perfil != "3")))
	{
		//Tenemos que deshabilitar la check
		$("#jqg_"+gridP42PedidoAdicionalM.name+"_"+(fila)).attr("style", "display:none");
	}
}

function reloadDataP42PedidoAdicionalM(recarga,articulo,identificador,clasePedido,identificadorSIA) {
	if (gridP42PedidoAdicionalM.firstLoad) {
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setGridWidth', $("#p42_AreaMontajeAdicional").width(), true);
		gridP42PedidoAdicionalM.firstLoad = false;
		if (gridP42PedidoAdicionalM.isColState) {
			$(this).jqGrid("remapColumns", gridP42PedidoAdicionalM.myColumnsState.permutation, true);
		}
	} else {
		gridP42PedidoAdicionalM.myColumnsState = gridP42PedidoAdicionalM.restoreColumnState(gridP42PedidoAdicionalM.cm);
		gridP42PedidoAdicionalM.isColState = typeof (gridP42PedidoAdicionalM.myColumnsState) !== 'undefined' && gridP42PedidoAdicionalM.myColumnsState !== null;
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setGridWidth', $("#p42_AreaMontajeAdicional").width(), false);
	}

	//Obtenemos el array con los registros seleccionados
	p42ObtenerSeleccionados();

	

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
		$("#p42_AreaBeforeM").attr("style", "display:inline;");
	}else {
		$("#p42_AreaBeforeM").attr("style", "display:none;");
	}

	p40DescPeriodo = $("#p40_descPeriodo").val(); //Se recoge la descripcion de la oferta seleccionada desde los avisos, sino se viene de avisos ser null o string vacio
	if (p40DescPeriodo != null && p40DescPeriodo != '') {
		p42DescPeriodo = p40DescPeriodo;
	}

	var recordPedidoAdicionalM = new PedidoAdicionalM ($("#centerId").val() , codArea, codSeccion, $("#p40_cmb_categoria").combobox('getValue'),
			$("#p40_fld_referencia").val(), null, clasePedido,null, null, null, null, null, null, null, null, null, p42SeleccionadosTotal, null, null, mca, null, [2,7], null, null,p42DescPeriodo,p42EspacioPromo);

	//Reseteamos el array de seleccionados.
	p42Seleccionados = new Array();
	p42SeleccionadosTotal = new Array();

	//DesCheckeamos lo seleccionado al recargar la lista.
	if (recarga=='S'){
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('resetSelection');
	}
	p42DesCheckearSeleccionados();

	var objJson = $.toJSON(recordPedidoAdicionalM.preparePedidoAdicionalMToJsonObject());

	$("#AreaResultados .loading").css("display", "block");

	$.ajax({
		type : 'POST',
		url : './pedidoAdicional/loadDataGridM.do?page='+gridP42PedidoAdicionalM.getActualPage()+'&max='+gridP42PedidoAdicionalM.getRowNumPerPage()+'&index='+gridP42PedidoAdicionalM.getSortIndex()+'&recarga='+recarga +'&articulo='+articulo +'&identificador='+identificador +'&identificadorSIA='+identificadorSIA +'&sortorder='+gridP42PedidoAdicionalM.getSortOrder(),

		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		
			if (data != null && data != '')
			{
				$(gridP42PedidoAdicionalM.nameJQuery)[0].addJSONData(data.datos);
				gridP42PedidoAdicionalM.actualPage = data.datos.page;
				gridP42PedidoAdicionalM.localData = data.datos;
				if (data.codError == 0){
					$("#p40AreaErrores").attr("style", "display:none");
					$("div#p40_AreaPestanas").attr("style", "display:block");

					p42SeleccionadosTotal = data.listadoSeleccionados;

					p42esTecnico = data.esTecnico;

					if (articulo != '')
					{
						//En este caso tenemos que actualizar el contador.
						$("#p40_fld_contadorMontaje").text(literalMontajeAdic + " ("+data.contadorMontaje+")");
					}


					$("#AreaResultados .loading").css("display", "none");	
					if (data.records == 0){
						createAlert(replaceSpecialCharacters(gridP42PedidoAdicionalM.emptyRecords), "ERROR");
					} 

					p42ControlesPantalla();

					if(recarga == 'N'){
						var comboOferta = data.listadoComboOfertaPeriodo;
						if (null != comboOferta && comboOferta.length > 0){

							options = "<option value=null>&nbsp;</option>";

							for (i = 0; i < comboOferta.length; i++){
								var comboValor = comboOferta[i];
								options = options + "<option value='" + comboValor+ "'>" + comboValor + "</option>";
							}
							$("select#p42_cmb_oferta").html(options);

							p40DescPeriodo = $("#p40_descPeriodo").val();
							if (p40DescPeriodo != ''){

								$("#p42_cmb_oferta").combobox('autocomplete',p40DescPeriodo);
								$("#p42_cmb_oferta").val(p40DescPeriodo);
								$("#p40_descPeriodo").val("");
							} else {
								if (null != data.defaultDescPeriodo){
									defaultDescPeriodo = data.defaultDescPeriodo;
									$("#p42_cmb_oferta").combobox('autocomplete',defaultDescPeriodo);
									$("#p42_cmb_oferta").val(defaultDescPeriodo);
									defaultDescPeriodo = null;
								}
							}
						} else {
							$("select#p42_cmb_oferta").html("");
						}


						var comboEspacioPromo = data.listadoComboEspacioPromo;
						if (null != comboEspacioPromo && comboEspacioPromo.length > 0){

							options = "<option value=null>&nbsp;</option>";

							for (i = 0; i < comboEspacioPromo.length; i++){
								var comboValor = comboEspacioPromo[i];
								options = options + "<option value='" + comboValor+ "'>" + comboValor + "</option>";
							}
							$("select#p42_cmb_promocion").html(options);


							if (null != data.defaultEspacioPromo){
								defaultEspacioPromo = data.defaultEspacioPromo;
								$("#p42_cmb_promocion").combobox('autocomplete',defaultEspacioPromo);
								$("#p42_cmb_promocion").val(defaultEspacioPromo); 
								defaultEspacioPromo = null;
							}
						} else {
							$("select#p42_cmb_promocion").html("");
						}
						
					}
				}else{
					$("#AreaResultados .loading").css("display", "none");
					$("#p40_erroresTexto").text(data.descError);
					$("#p40AreaErrores").attr("style", "display:block");
					$("#p40_AreaPestanas").attr("style", "display:none");
				}
				hideShowColumnsM();
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

function p42ObtenerSeleccionados(){


	p42Seleccionados = new Array();

	var selectedRowIDs = jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('getGridParam', 'selarrrow');
	var rowsInPage = jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('getGridParam', 'rowNum');

	if(selectedRowIDs != undefined){
		//Obtenemos las referencias seleccionadas
		for (i = 0; i < selectedRowIDs.length; i++){
			if (selectedRowIDs[i] != null && selectedRowIDs[i] != '')
			{
				var idVegalsa = $("#"+selectedRowIDs[i]+"_identificadorVegalsaM").val();
				if (idVegalsa == '' || idVegalsa == null){
					p42Seleccionados.push($("#"+selectedRowIDs[i]+"_clasePedidoM").val()+"#"+$("#"+selectedRowIDs[i]+"_codArticuloM").val()+"#"+$("#"+selectedRowIDs[i]+"_identificadorM").val()+"#"+$("#"+selectedRowIDs[i]+"_identificadorSIAM").val());
				}else{
					p42Seleccionados.push($("#"+selectedRowIDs[i]+"_clasePedidoM").val()+"#"+$("#"+selectedRowIDs[i]+"_codArticuloM").val()+"#"+$("#"+selectedRowIDs[i]+"_identificadorM").val()+"#"+$("#"+selectedRowIDs[i]+"_identificadorSIAM").val()+"#"+idVegalsa);
				}
			}
		}
	}

	//Nos recorremos los registros existentes en pantalla.
	for (i = 0; i < rowsInPage; i++){

		var clasePedido = $("#"+(i+1)+"_clasePedidoM").val();
		var codArticulo = $("#"+(i+1)+"_codArticuloM").val();
		var identificador = $("#"+(i+1)+"_identificadorM").val();
		var identificadorSIA = $("#"+(i+1)+"_identificadorSIAM").val();
		var identificadorVegalsa = $("#"+(i+1)+"_identificadorVegalsaM").val();
		var identificadorArray = identificador;
		var identificadorSIAArray = identificadorSIA;
		var identificadorVegalsaArray = identificadorVegalsa;

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
		for (j = 0; j < p42SeleccionadosTotal.length; j++){
				
			if (p42SeleccionadosTotal[j].identificadorVegalsa == null){
				if ((p42SeleccionadosTotal[j].clasePedido == clasePedido)&&(p42SeleccionadosTotal[j].codArticulo == codArticulo)&&(p42SeleccionadosTotal[j].identificador == identificador)&&(p42SeleccionadosTotal[j].identificadorSIA == identificadorSIA))
				{
					//Si es una de las referencias de la página, actualizamos el valor de seleccionado.
					if (($.inArray(clasePedido+"#"+codArticulo+"#"+identificadorArray+"#"+identificadorSIAArray, p42Seleccionados) > -1) )
					{
						p42SeleccionadosTotal[j].seleccionado = "S";
					}
					else
					{
						p42SeleccionadosTotal[j].seleccionado = "N";
					}
				}
			}else{
				if (p42SeleccionadosTotal[j].identificadorVegalsa == identificadorVegalsa){
					if ($.inArray(clasePedido+"#"+codArticulo+"#"+identificadorArray+"#"+identificadorSIAArray+"#"+identificadorVegalsaArray, p42Seleccionados) > -1 )
					{
						p42SeleccionadosTotal[j].seleccionado = "S";
					}
					else
					{
						p42SeleccionadosTotal[j].seleccionado = "N";
					}
				}
			}
		}
	}
}

function p42FindValidationRemove(){
	var messageVal = p42emptyListaSeleccionados;

	//Nos recorremos la lista de seleccionados.
	for (j = 0; j < p42SeleccionadosTotal.length; j++){
		if (p42SeleccionadosTotal[j].seleccionado == "S")
		{
			messageVal=null;
		}
	}
	return messageVal;

}

function p42BorrarDatos(){

	//Obtenemos el array con los registros seleccionados
	p42ObtenerSeleccionados();

	var messageVal=p42FindValidationRemove();

	if (messageVal!=null){
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
	}else{

		$(function() {
			$( "#p42dialog-confirm" ).dialog({
				resizable: false,
				height:140,
				modal: true,
				buttons: {
					"Si": function() {
						p42Remove();
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

function p42Remove(){


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
	var recordPedidoAdicionalM = new PedidoAdicionalM ($("#centerId").val() , codArea, codSeccion, $("#p40_cmb_categoria").combobox('getValue'),
			$("#p40_fld_referencia").val(), null, null, null, null, null, null, null, null, null, null, null, p42SeleccionadosTotal,null,null, null, null, [2,7], null, null);

	recordPedidoAdicionalM.mca = mca;
	//DesCheckeamos lo seleccionado al llamar a borrar.
	p42DesCheckearSeleccionados();

	//Reseteamos el array de seleccionados.
	p42Seleccionados = new Array();
	p42SeleccionadosTotal = new Array();


	var objJson = $.toJSON(recordPedidoAdicionalM.preparePedidoAdicionalMToJsonObject());

	$("#AreaResultados .loading").css("display", "block");

	$.ajax({
		type : 'POST',
		url : './pedidoAdicional/removeDataGridM.do?max='+gridP42PedidoAdicionalM.getRowNumPerPage()+'&index='+gridP42PedidoAdicionalM.getSortIndex()+'&sortorder='+gridP42PedidoAdicionalM.getSortOrder(),
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		
			$(gridP42PedidoAdicionalM.nameJQuery)[0].addJSONData(data.datos);
			gridP42PedidoAdicionalM.actualPage = data.datos.page;
			gridP42PedidoAdicionalM.localData = data.datos;

			if (data != null && data != '')
			{
				if (data.codError == 0){
					$("#p40AreaErrores").attr("style", "display:none");
					$("div#p40_AreaPestanas").attr("style", "display:block");

					p42SeleccionadosTotal = data.listadoSeleccionados;

					p42esTecnico = data.esTecnico;

					//Actualizamos el contador de montaje tras el borrado.
					$("#p40_fld_contadorMontaje").text(literalMontajeAdic + " ("+data.contadorMontaje+")");


					$("#AreaResultados .loading").css("display", "none");	
					if (data.records == 0){
						createAlert(replaceSpecialCharacters(gridP42PedidoAdicionalM.emptyRecords), "ERROR");
					} 

					p42ControlesPantalla();
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

function p42ModificarReferencia(clasePedido,codArticulo,esPlanograma,identificador, fechaInicio, noGestionaPbl, identificadorSIA, identificadorVegalsa){

	//Antes de abrir la pantalla de modificación única por referencia, obtenemos la información necesaria para mostrarla posteriormente.
	var recordPedidoAdicionalM = new PedidoAdicionalM ($("#centerId").val() , null, null, null,	codArticulo, null, clasePedido, null, null,
			null, null, null, null, null, null, null, p42SeleccionadosTotal, esPlanograma, identificador, null, null, [2,7], noGestionaPbl, identificadorSIA, identificadorVegalsa);
	recordPedidoAdicionalM.fechaInicio = fechaInicio;
	recordPedidoAdicionalM.identificadorVegalsa=identificadorVegalsa;
	var objJson = $.toJSON(recordPedidoAdicionalM.preparePedidoAdicionalMToJsonObject());

	$("#AreaResultados .loading").css("display", "block");

	$.ajax({
		type : 'POST',
		url : './pedidoAdicional/openModifyDataGridM.do?page='+gridP42PedidoAdicionalM.getActualPage()+'&max='+gridP42PedidoAdicionalM.getRowNumPerPage()+'&index='+gridP42PedidoAdicionalM.getSortIndex()+'&sortorder='+gridP42PedidoAdicionalM.getSortOrder(),
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		
			if (data != null && data != '' && data.codArticulo != null && data.codArticulo != ''){
				
				if (data.codError == 0){
					// MISUMI-352
					//obtenerSiEsCentroReferenciaVegalsa(data.codArticulo);
					if (esCentroVegalsa()){
						esCentroReferenciaVegalsa=true;
					}else{
						esCentroReferenciaVegalsa=false;
					}
					p42CargaDatosCalendarios(data);

					p60CrearCalendarios();

					p60DefinePedidoAdicionalM(data);
					//Primero cargamos los datos.
					p42CargaDatos(data);

					//Ahora establecemos los campos que son modificables
					p42ControlModificables(data);

					//Abrimos el popup
					$( "#p60_AreaModificacion" ).dialog( "open" );

					$("#p40AreaErrores").attr("style", "display:none");

					$("#AreaResultados .loading").css("display", "none");	

				}else if (data.codError == 2){
					$(function() {
						$( "#p42dialog-confirm-noExiste" ).dialog({
							resizable: false,
							height:140,
							modal: true,
							buttons: {
								Aceptar: function() {
									p42NoExiste(data.codArticulo,data.identificador,data.identificadorSIA,data.clasePedido);
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

function p42NoExiste(codArticulo,identificador,identificadorSIA,clasePedido){

	reloadDataP42PedidoAdicionalM('S',codArticulo,identificador,clasePedido,identificadorSIA);
}

function p42CargaDatosCalendarios(data){

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

function p42CargaDatosCalendariosMasivo(){

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

function p42CargaDatos(data){

	//alert("Fechas---->"+data.fechaInicio+","+data.fecha2+","+data.fecha3+","+data.fecha4+","+data.fechaFin);

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
	if (data.clasePedido == "7"){
		$("#p60_fld_numeroOferta").val(data.descOferta);
		$("#p60_fld_numeroOferta").attr('title',data.descOferta);
	} else {
		$("#p60_fld_numeroOferta").val(sinOferta);
		$("#p60_fld_numeroOferta").removeAttr('title');
	}
	$("#p60_fld_sinOferta").val("S");
	$("#p60_fld_numeroOferta").attr("disabled", "disabled");
	$("#p60_fld_tipoOferta").val(1);
	$("#p60_bloque3AreaDatosCampos1_1").hide(); //$("#p60_div_cmb_cajas").hide();
	$("#p60_bloque3AreaDatosCampos2Linea").css("clear", "left");
	$("#p60_bloque3AreaDatosCampos2Linea").css("padding-top", "20px");
	$("#p60_div_cmb_excluir").show();
	$("#p60_div_fechaFin").show();
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

	$("#p60_cmb_excluir").combobox(null);
	if (data.excluir != null && data.excluir == true)
	{
		$("#p60_cmb_excluir").combobox('autocomplete',p42ComboSI);
		$("#p60_cmb_excluir").combobox('comboautocomplete',null);
	}
	else
	{
		$("#p60_cmb_excluir").combobox('autocomplete',p42ComboNO);
		$("#p60_cmb_excluir").combobox('comboautocomplete',null);
	}
	if (data.stock == -9999){
		$("#p60_fld_stock").val("Error");
	} else {
		$("#p60_fld_stock").val(data.stock).formatNumber({format:"0.##"});
	}

	$("#p60_fld_uc").val(data.uniCajaServ).formatNumber({format:"0.##"});
	if (data.clasePedido == 7){
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
		$("#p60_fld_cantidad1").val(p42FormateoImplantPopUp(data.capMax));
		$("#p60_lbl_cantidad2").text(textoImplFinal);
		$("#p60_fld_cantidad2").attr("style", "display:inherit");
		$("#p60_fld_cantidad2").val(p42FormateoImplantPopUp(data.capMin));
		$("#p60_fld_cantidad3").attr("style", "display:none");
		$(".p60_bloque3AreaDatosCampos1").css("width", "155px");
		$("#p60_bloque3AreaDatosCampos2_3").hide();


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
			if (data.clasePedido == 7){
				$("#p60_bloque3AreaDatosCampos2_4.p60_bloque3AreaDatosCampos1").css("width", "155px");
				$("#p60_bloqueFrescosCampoMax.p60_bloque3AreaDatosCampos1").css("width", "155px");
			}
			$("#p60_lbl_cantidad1").text(fechaformateada);

			$("#p60_fld_cantidad1").attr("style", "display:inherit");
			$("#p60_fld_cantidad1").val(p52FormateoCantidadesPopUp(data.cantidad1));
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
			$("#p60_fld_cantidad2").val(p52FormateoCantidadesPopUp(data.cantidad2));
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
			$("#p60_fld_cantidad3").val(p52FormateoCantidadesPopUp(data.cantidad3));
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

function p52FormateoCantidadesPopUp(cantidad) {

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

function p42FormateoImplantPopUp(cantidad) {

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

function p42EstablecerFiltrosConDec(){

	//Establecemos el formato para los campos de cantidades.
	$("#p60_fld_cantidad1").filter_input({regex:'[0-9,]'});
	$("#p60_fld_cantidad2").filter_input({regex:'[0-9,]'});
	$("#p60_fld_cantidad3").filter_input({regex:'[0-9,]'});
	$("#p60_fld_cantidad4").filter_input({regex:'[0-9,]'});
	$("#p60_fld_cantidad5").filter_input({regex:'[0-9,]'});
} 

function p42EstablecerFiltrosSinDec(){

	//Establecemos el formato para los campos de cantidades.
	$("#p60_fld_cantidad1").filter_input({regex:'[0-9]'});
	$("#p60_fld_cantidad2").filter_input({regex:'[0-9]'});
	$("#p60_fld_cantidad3").filter_input({regex:'[0-9]'});
	$("#p60_fld_cantidad4").filter_input({regex:'[0-9]'});
	$("#p60_fld_cantidad5").filter_input({regex:'[0-9]'});
}



function p42ControlModificables(data){

	//Primero deshabilitamos todos los campos.
	$("#p60_fechaInicioDatePicker").datepicker('disable');
	$("#p60_fechaFinDatePicker").datepicker('disable');
	$("#p60_cmb_excluir").combobox("disable");
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
	$( "#p60_AreaModificacion" ).dialog( "option", "buttons", [ { text: p42BotonGuardar, click: function() {p60GuardarPedido();} },
	                                                            { text: p42BotonVolver, click: function() { p60ControlesCierrePopUp($(this)); } } ] );
	//Borramos el htlm de fecha hasta para que al abrir distintos pedidos, si el pedido actual no tiene mensaje,
	//no aparezca el mensaje del pedido anterior.
	$("#p60_lbl_fechaHasta").html("");
	$("#p60_fld_fechaHasta").val("");

	var userPerfil = $("#userPerfil").val();
	if (userPerfil == "3"){
		//Si el perfil es igual a 3, hay que quitar el boton de guardar.
		$( "#p60_AreaModificacion" ).dialog( "option", "buttons", [ { text: p42BotonVolver, click: function() { p60ControlesCierrePopUp($(this)); } } ] );
	}

	$( "#p60_AreaModificacion" ).dialog( "option", "height", "600");
	$( "#p60_AreaModificacion" ).dialog( "option", "open" ,function() {$('.ui-dialog-titlebar-close').on('mousedown', function(e){e.stopPropagation();p60ControlesCierrePopUpAspa();});$(".p60_bloque3AreaDatosCampos").css("margin-top", "14px");$(".p60_bloque3AreaDatosCampos").css("width", "auto");if($("#p60_fld_tipoPedidoAdicional").val()!=p60TipoEncargo){reloadAyuda1();}});
	//Para poder modificar el pedido, en primer lugar no puede ser una planogramada.
	if (((data.esPlanograma == null) || (data.esPlanograma != null && data.esPlanograma != 'S')) && (null != data.fechaMinima ))
	{
		//En segundo lugar no debe ser central(Si el perfil es distinto de 3 significa que es Central)
		if ((data.perfil != null && data.perfil == "3") || (data.clasePedido == 7) || (data.noGestionaPbl != null && data.noGestionaPbl == 'S'))
		{
			//Ahora tenemos que determinar que se puede modificar.
			p42EstablecerModificables(data);
		}
	}

	if (((data.modificable == "N") && (data.borrable == "N")) || (null == data.fechaMinima )) {

		//No es modificable, debemos quitar el boton de guardar.
		$( "#p60_AreaModificacion" ).dialog( "option", "buttons", [ { text: p41BotonVolver, click: function() { p60ControlesCierrePopUp($(this)); } } ] );
		$( "#p60_AreaModificacion" ).dialog( "option", "height", "485");
	}

}

function p42ActivarGuardar(){

	//Inicialmente con los dos botones.
	$( "#p60_AreaModificacion" ).dialog( "option", "buttons", [ { text: p42BotonGuardar, click: function() {p60GuardarPedido();} },
	                                                            { text: p42BotonVolver, click: function() { p60ControlesCierrePopUp($(this)); } } ] );

	var userPerfil = $("#userPerfil").val();
	if (userPerfil == "3"){
		//Si el perfil del usuario es igual a 3, hay que quitar el boton de guardar.
		$( "#p60_AreaModificacion" ).dialog( "option", "buttons", [ { text: p42BotonVolver, click: function() { p60ControlesCierrePopUp($(this)); } } ] );
	}

	$( "#p60_AreaModificacion" ).dialog( "option", "height", "600");
}

function p42EstablecerModificables(data){

	if (data.modificableIndiv != null){
		p60ModificableIndiv =  data.modificableIndiv;
	}
	if (data.noGestionaPbl == null || data.noGestionaPbl != 'S' || (data.identificadorSIA != null && data.identificadorSIA != '')){

		if (data.tipoPedido != null && data.tipoPedido == 'P')
		{
			//Alimentación(Si es N no se permite modificar nada)
			//alert("Es alimentación con modificableIndiv--->"+data.modificableIndiv);




			if (p42ControlModifFechaHasta(data.fechaHasta) && data.modificableIndiv != null && (data.modificableIndiv == 'S' || data.modificableIndiv == 'T' || data.modificableIndiv == 'Q'))
			{
				//Se permite modificar todo salvo la fecha de inicio si es de tipo Q
				if (data.modificableIndiv != 'Q'){
					$("#p60_fechaInicioDatePicker").datepicker('enable');
				}

				$("#p60_fechaFinDatePicker").datepicker('enable');

				if (data.modificableIndiv != 'Q'){
					p42EstablecerFechaMin(data.fechaMinima,data.fechaMinima,"N");
				}else{
					p42EstablecerFechaMin(data.fechaInicio,data.fechaMinima,"S");
				}

				$("#p60_cmb_excluir").combobox("enable");
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
				

				p42ActivarGuardar();
			}
			else if (p42ControlModifFechaHasta(data.fechaHasta) && data.modificableIndiv != null && data.modificableIndiv == 'P')
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
				

				p42EstablecerFechaMin(data.fechaInicio,data.fechaMinima,"S");		
				p42ActivarGuardar();
			}

		}
		else if (data.tipoPedido != null && data.tipoPedido == 'E')
		{

			var modif = false;
			//Fresco Puro
			//alert("Es fresco puro con modificableIndiv--->"+data.modificableIndiv);
			//Si tiene bloqueo de encargo sólo se permite modificar la fecha de fin
			if (p42ControlModifFechaHasta(data.fechaHasta) && (data.fechaBloqueoEncargo == null || data.fechaBloqueoEncargo != 'S') && data.modificableIndiv != null && (data.modificableIndiv.charAt(0) == "S" || data.modificableIndiv.charAt(0) == "T" || data.modificableIndiv.charAt(0) == "Q"))
			{
				//Se permite modificar todo salvo la fecha de inicio si tiene bloqueo de pilada y es de tipo Q
				var ultimoCaracterModifIndiv = "";
				if (data.modificableIndiv != null && data.modificableIndiv != ""){
					ultimoCaracterModifIndiv = data.modificableIndiv.substring(data.modificableIndiv.length - 1);
				}

				if (ultimoCaracterModifIndiv != 'Q'){
					$("#p60_fechaInicioDatePicker").datepicker('enable');
					p42EstablecerFechaMin(data.fechaMinima,data.fechaMinima,"N");
				}else{
					p42EstablecerFechaMin(data.fechaInicio,data.fechaMinima,"S");
				}

				$("#p60_fld_cantidad1").removeAttr("disabled");
				$("#p60_fld_cantidad1").focus(function(event) {
					$("#p60_fld_cantidad1").select();
				});
				$("#p60_cmb_excluir").combobox("enable");

				p42ActivarGuardar();
				modif = true;
			}
			if (p42ControlModifFechaHasta(data.fechaHasta) && (data.fechaBloqueoEncargo == null || data.fechaBloqueoEncargo != 'S') && data.modificableIndiv != null && data.modificableIndiv.length > 1 && (data.modificableIndiv.charAt(1) == "S" || data.modificableIndiv.charAt(1) == "T" || data.modificableIndiv.charAt(1) == "Q"))
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
					p42EstablecerFechaMin(data.fechaInicio,data.fechaMinima,"S");
					p42ActivarGuardar();
					modif = true;
				}
			}
			if (p42ControlModifFechaHasta(data.fechaHasta) && (data.fechaBloqueoEncargo == null || data.fechaBloqueoEncargo != 'S') && data.modificableIndiv != null && data.modificableIndiv.length > 2 && (data.modificableIndiv.charAt(2) == "S" || data.modificableIndiv.charAt(2) == "T" || data.modificableIndiv.charAt(2) == "Q"))
			{
				//Sólo se permite modificar la cantidad3.
				$("#p60_fld_cantidad3").removeAttr("disabled");
				$("#p60_fld_cantidad3").focus(function(event) {
					$("#p60_fld_cantidad3").select();
				});

				if (!modif){
					p42EstablecerFechaMin(data.fechaInicio,data.fechaMinima,"S");
					p42ActivarGuardar();
					modif = true;
				}
			}

			if (data.clasePedido == "7"){
				if (p42ControlModifFechaHasta(data.fechaHasta) && (data.fechaBloqueoEncargo == null || data.fechaBloqueoEncargo != 'S') && data.modificableIndiv != null && data.modificableIndiv.length > 3 && (data.modificableIndiv.charAt(3) == "S" || data.modificableIndiv.charAt(3) == "T" || data.modificableIndiv.charAt(3) == "Q"))
				{
					//Sólo se permite modificar la cantidad3.
					$("#p60_fld_cantidad4").removeAttr("disabled");
					$("#p60_fld_cantidad4").focus(function(event) {
						$("#p60_fld_cantidad4").select();
					});

					if (!modif){
						p42EstablecerFechaMin(data.fechaInicio,data.fechaMinima,"S");
						p42ActivarGuardar();
						modif = true;
					}
				}
				if (p42ControlModifFechaHasta(data.fechaHasta) && (data.fechaBloqueoEncargo == null || data.fechaBloqueoEncargo != 'S') && data.modificableIndiv != null && data.modificableIndiv.length > 4 && (data.modificableIndiv.charAt(4) == "S" || data.modificableIndiv.charAt(4) == "T" || data.modificableIndiv.charAt(4) == "Q"))
				{
					//Sólo se permite modificar la cantidad3.
					$("#p60_fld_cantidad5").removeAttr("disabled");
					$("#p60_fld_cantidad5").focus(function(event) {
						$("#p60_fld_cantidad5").select();
					});

					if (!modif){
						p42EstablecerFechaMin(data.fechaInicio,data.fechaMinima,"S");
						p42ActivarGuardar();
						modif = true;
					}
				}

			} else {
				if (p42ControlModifFechaHasta(data.fechaHasta) && data.modificableIndiv != null && data.modificableIndiv.length > 3 && ( data.modificableIndiv.charAt(3) == "S" || data.modificableIndiv.charAt(3) == "P" || data.modificableIndiv.charAt(3) == "T" || data.modificableIndiv.charAt(3) == "Q"))
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
					if (!modif){
						p42ActivarGuardar();
						modif = true;
					}
				}else{
					var ultimoCaracterModifIndiv = "";
					if (data.modificableIndiv != null && data.modificableIndiv != ""){
						ultimoCaracterModifIndiv = data.modificableIndiv.substring(data.modificableIndiv.length - 1);
					}
					if (p42ControlModifFechaHasta(data.fechaHasta) && data.modificableIndiv != null && (ultimoCaracterModifIndiv == "S" || ultimoCaracterModifIndiv == "P" || ultimoCaracterModifIndiv == "T"  || ultimoCaracterModifIndiv == "Q") && (data.fechaBloqueoEncargo == null || data.fechaBloqueoEncargo != 'S'))
					{
						//Sólo se permite modificar la Fecha Fin
						$("#p60_fechaFinDatePicker").datepicker('enable');

						if (!modif){
							p42EstablecerFechaMin(data.fechaInicio,data.fechaMinima,"S");
							p42ActivarGuardar();
							modif = true;
						}
					}
				}
			}
			p42EstablecerFiltrosConDec();
		}
	}else{



		$("#p60_excluir").hide();
		$("#p60_fechaInicioDatePicker").datepicker('disable');
		$("#p60_fechaFinDatePicker").datepicker('disable');

		if (p42ControlModifFechaHasta(data.fechaHasta) && data.modificableIndiv != null && (data.modificableIndiv.charAt(0) == "S"))
		{
			$("#p60_fld_cantidad1").removeAttr("disabled");
			$("#p60_fld_cantidad1").focus(function(event) {
				$("#p60_fld_cantidad1").select();
			});

			p42ActivarGuardar();
			modif = true;
		}
		if (p42ControlModifFechaHasta(data.fechaHasta) && data.modificableIndiv.length > 1 && (data.modificableIndiv.charAt(1) == "S"))
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
				p42ActivarGuardar();
				modif = true;
			}
		}
		if (p42ControlModifFechaHasta(data.fechaHasta) && data.modificableIndiv != null && data.modificableIndiv.length > 2 && (data.modificableIndiv.charAt(2) == "S"))
		{
			//Sólo se permite modificar la cantidad3.
			$("#p60_fld_cantidad3").removeAttr("disabled");
			$("#p60_fld_cantidad3").focus(function(event) {
				$("#p60_fld_cantidad3").select();
			});

			if (!modif){
				p42ActivarGuardar();
				modif = true;
			}
		}

		if (p42ControlModifFechaHasta(data.fechaHasta) && (data.fechaBloqueoEncargo == null || data.fechaBloqueoEncargo != 'S') && data.modificableIndiv != null && data.modificableIndiv.length > 3 && (data.modificableIndiv.charAt(3) == "S"))
		{
			//Sólo se permite modificar la cantidad3.
			$("#p60_fld_cantidad4").removeAttr("disabled");
			$("#p60_fld_cantidad4").focus(function(event) {
				$("#p60_fld_cantidad4").select();
			});

			if (!modif){
				p42ActivarGuardar();
				modif = true;
			}
		}
		if (p42ControlModifFechaHasta(data.fechaHasta) && (data.fechaBloqueoEncargo == null || data.fechaBloqueoEncargo != 'S') && data.modificableIndiv != null && data.modificableIndiv.length > 4 && (data.modificableIndiv.charAt(4) == "S"))
		{
			//Sólo se permite modificar la cantidad3.
			$("#p60_fld_cantidad5").removeAttr("disabled");
			$("#p60_fld_cantidad5").focus(function(event) {
				$("#p60_fld_cantidad5").select();
			});

			if (!modif){
				p42ActivarGuardar();
				modif = true;
			}
		}
	}
	p42EstablecerFiltrosSinDec();

	if (data.referenciaNueva == true){
		$("#p60_excluir").hide();
		$("#p60_fechaFinDatePicker").datepicker('disable');
	}
	if (data.clasePedido == "7"){
		$("#p60_cmb_excluir").combobox("disable");
		$("#p60_fechaInicioDatePicker").datepicker('disable');
		$("#p60_fechaFinDatePicker").datepicker('disable');
	}
}

function p42EstablecerFechaMin(fecIni,fecFin,disabledIni){

	$("#p60_fechaInicioDatePicker" ).datepicker( "option", "minDate", new Date(fecIni.substring(4), fecIni.substring(2,4) - 1, fecIni.substring(0,2)) );

	if (disabledIni == "S")
	{
		$("#p60_fechaInicioDatePicker").datepicker('disable');
	}

	$("#p60_fechaFinDatePicker" ).datepicker( "option", "minDate", new Date(fecFin.substring(4), fecFin.substring(2,4) - 1, fecFin.substring(0,2)) );

}

function p42ModificarDatos(){

	p42ObtenerSeleccionados();

	// Nos recorremos la lista de seleccionados.
	var contSeleccionados = 0;
	var clasePedidoSel;
	var codArticuloSel;
	var esPlanogramaSel;
	var identificadorSel;
	var noGestionaPbl;
	var identificadorSIASel;
	var identificadorVegalsaSel;
	for (j = 0; j < p42SeleccionadosTotal.length; j++) {
		if (p42SeleccionadosTotal[j].seleccionado == "S") {
			contSeleccionados++;
			clasePedidoSel = p42SeleccionadosTotal[j].clasePedido;
			codArticuloSel = p42SeleccionadosTotal[j].codArticulo;
			esPlanogramaSel = p42SeleccionadosTotal[j].esPlanograma;
			identificadorSel = p42SeleccionadosTotal[j].identificador;
			noGestionaPblSel = p42SeleccionadosTotal[j].noGestionaPbl;
			identificadorSIASel = p42SeleccionadosTotal[j].identificadorSIA;
			identificadorVegalsaSel = p42SeleccionadosTotal[j].identificadorVegalsa;
		}
	}

	if (contSeleccionados == 0)
	{
		createAlert(replaceSpecialCharacters(p42emptyListaSeleccionados), "ERROR");
	}
	else if (contSeleccionados == 1)
	{
		//Si sólo ha seleccionado un registro llamaremos a la modicicación individual.
		p42ModificarReferencia(clasePedidoSel,codArticuloSel,esPlanogramaSel,identificadorSel,null, noGestionaPblSel, identificadorSIASel, identificadorVegalsaSel);
	}
	else if ( (contSeleccionados > 1) && ($("#p40_chk_mca").is(':checked') ))
	{
		createAlert(replaceSpecialCharacters(errorModificacionNoMasiva), "ERROR");
	}
	else
	{
		//Antes de abrir el calendario, tenemos que hacer las comprobaciones de que haya seleccionado
		//todos los registros con la misma fecha fin y obtener la fecha de inicio del calendario a mostrar al usuario.
		var recordPedidoAdicionalM = new PedidoAdicionalM ($("#centerId").val() , null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, p42SeleccionadosTotal, null, null, null, null, [2,7], null, null);

		//DesCheckeamos lo seleccionado al llamar a comprobar si es modificable la lista seleccionada.
		p42DesCheckearSeleccionados();

		//Reseteamos el array de seleccionados.
		p42Seleccionados = new Array();
		p42SeleccionadosTotal = new Array();


		var objJson = $.toJSON(recordPedidoAdicionalM.preparePedidoAdicionalMToJsonObject());

		$("#AreaResultados .loading").css("display", "block");

		$.ajax({
			type : 'POST',
			url : './pedidoAdicional/testModifyDataGridM.do?max='+gridP42PedidoAdicionalM.getRowNumPerPage()+'&index='+gridP42PedidoAdicionalM.getSortIndex()+'&sortorder='+gridP42PedidoAdicionalM.getSortOrder(),
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {		
				$(gridP42PedidoAdicionalM.nameJQuery)[0].addJSONData(data.datos);
				gridP42PedidoAdicionalM.actualPage = data.datos.page;
				gridP42PedidoAdicionalM.localData = data.datos;

				if (data != null && data != '')
				{
					if (data.codError == 0){
						$("#p40AreaErrores").attr("style", "display:none");
						$("div#p40_AreaPestanas").attr("style", "display:block");

						p42SeleccionadosTotal = data.listadoSeleccionados;

						p42esTecnico = data.esTecnico;

						$("#AreaResultados .loading").css("display", "none");	
						if (data.records == 0){
							createAlert(replaceSpecialCharacters(gridP42PedidoAdicionalM.emptyRecords), "ERROR");
						} 

						p42ControlesPantalla();

						if (data.esModificable == "S")
						{
							p42CargaDatosCalendariosMasivo();

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


function p42ModificarDatosDesdePopup(){

	var codArea = null;
	var codSeccion = null;
	if ($("#p40_cmb_seccion").combobox('getValue') != null && $("#p40_cmb_seccion").combobox('getValue') != '')
	{
		valorSeccion = $("#p40_cmb_seccion").combobox('getValue').split('*');
		codArea = valorSeccion[0];
		codSeccion = valorSeccion[1];
	}

	p42ObtenerSeleccionados();

	var recordPedidoAdicionalM = new PedidoAdicionalM ($("#centerId").val() , codArea, codSeccion, $("#p40_cmb_categoria").combobox('getValue'),
			$("#p40_fld_referencia").val(), null, null, null, $( "#p61_fechaFinDatePicker" ).val(),
			null, null, null, null, null, null, null, p42SeleccionadosTotal,null, null, null, null, [2,7], null, null);

	//DesCheckeamos lo seleccionado al llamar a modificar.
	p42DesCheckearSeleccionados();

	//Reseteamos el array de seleccionados.
	p42Seleccionados = new Array();
	p42SeleccionadosTotal = new Array();


	var objJson = $.toJSON(recordPedidoAdicionalM.preparePedidoAdicionalMToJsonObject());

	$("#AreaResultados .loading").css("display", "block");

	$.ajax({
		type : 'POST',
		url : './pedidoAdicional/modifyDataGridM.do?max='+gridP42PedidoAdicionalM.getRowNumPerPage()+'&index='+gridP42PedidoAdicionalM.getSortIndex()+'&sortorder='+gridP42PedidoAdicionalM.getSortOrder(),
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		
			$(gridP42PedidoAdicionalM.nameJQuery)[0].addJSONData(data.datos);
			gridP42PedidoAdicionalM.actualPage = data.datos.page;
			gridP42PedidoAdicionalM.localData = data.datos;

			if (data != null && data != '')
			{
				if (data.codError == 0){
					$("#p40AreaErrores").attr("style", "display:none");
					$("div#p40_AreaPestanas").attr("style", "display:block");

					p42SeleccionadosTotal = data.listadoSeleccionados;

					p42esTecnico = data.esTecnico;

					$("#AreaResultados .loading").css("display", "none");	
					if (data.records == 0){
						createAlert(replaceSpecialCharacters(gridP42PedidoAdicionalM.emptyRecords), "ERROR");
					} 

					p42ControlesPantalla();

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

function p52FormateoCantidades(cellValue, opts, rData) {

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
function p42FormateoImplant(cellValue, opts, rData) {

	if (cellValue != '')
	{
		//Actualizamos el formateo de los campos para que no tenga decimales
		return $.formatNumber(cellValue,{format:"0",locale:"es"});
	}
	else
	{
		return '';
	}
}

/*Clase de constantes para el GRID de Pedido adicional*/
function GridP42PedidoAdicionalM (locale){
	// Atributos
	this.name = "gridP42MontajeAdicional"; 
	this.nameJQuery = "#gridP42MontajeAdicional"; 
	this.i18nJSON = './misumi/resources/p42PedidoAdicionalM/p42pedidoAdicionalM_' + locale + '.json';
	this.colNames = null;
	this.cm = [ {
		"name" : "mensaje",
		"index":"mensaje", 
		"formatter": p42ImageFormatMessage,
		"fixed":true,
		"width" : 75,
		"sortable" : true
	},{
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
		"name"  : "codArticuloGrid",
		"index" : "codArticuloGrid",
		"width" : 78,
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
		"width" : 220,
		"sortable" : true
	},{
		"name"  : "fechaInicio",
		"index" : "fechaInicio",
		"formatter" : p42FormateoDate,
		"width" : 60,
		"sortable" : true
	},{
		"name"  : "fechaFin",
		"index" : "fechaFin",
		"formatter" : p42FormateoDate,
		"width" : 60,
		"sortable" : true
	},{
		"name"  : "capMax",
		"index" : "capMax", 
		"formatter":p42FormateoImplant,
		"width" : 60,
		"sortable" : true
	},{
		"name"  : "capMin",
		"index" : "capMin", 
		"formatter":p42FormateoImplant,
		"width" : 60,
		"sortable" : true
	},{
		"name"  : "cantidad1",
		"index" : "cantidad1", 
		"formatter":p52FormateoCantidades,
		"width" : 55,
		"sortable" : true
	},{
		"name"  : "cantidad2",
		"index" : "cantidad2", 
		"formatter":p52FormateoCantidades,
		"width" : 55,
		"sortable" : true
	},{
		"name"  : "cantidad3",
		"index" : "cantidad3", 
		"formatter":p52FormateoCantidades,
		"width" : 55,
		"sortable" : true
	},{
		"name"  : "cantidad4",
		"index" : "cantidad4", 
		"formatter" : p52FormateoCantidades,
		"width" : 55,
		"hidden": true,
		"sortable" : true
	},{
		"name"  : "cantidad5",
		"index" : "cantidad5", 
		"formatter" : p52FormateoCantidades,
		"width" : 55,
		"hidden": true,
		"sortable" : true
	},{
		"name"  : "uniCajaServ",
		"index" : "uniCajaServ", 
		"formatter" : p42FormateoUnidadesCaja,
		"width" : 50,
		"hidden" : true,
		"sortable" : true
	},{
		"name"  : "excluir",
		"index" : "excluir", 
		"formatter": p42TratarExcluir,
		"width" : 50,
		"hidden" : true,
		"sortable" : true
	},{
		"name"  : "cantMin",
		"index" : "cantMin", 
		"formatter" : p52FormateoCantidades,
		"width" : 60,
		"hidden" : true,
		"sortable" : true
	},{
		"name"  : "cantMax",
		"index" : "cantMax", 
		"formatter" : p52FormateoCantidades,
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
	}
	];
	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP42MontajeAdicional"; 
	this.pagerNameJQuery = "#pagerP42MontajeAdicional";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;
	this.myColumnStateName = 'gridP42MontajeAdicional.colState';
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
		var colModel =jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('getGridParam', 'colModel'); 
		//if (colModel == null)
		//	 colModel = grid.cm;
		var i;
		var l = colModel.length;
		var colItem; 
		var cmName;
		var postData = jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('getGridParam', 'postData');
		var columnsState = {
				search: jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('getGridParam', 'search'),
				page: jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('getGridParam', 'page'),
				sortname: jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('getGridParam', 'sortname'),
				sortorder: jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('getGridParam', 'sortorder'),
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
			window.localStorage.setItem(gridP42PedidoAdicionalM.myColumnStateName, JSON.stringify(columnsState));
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

function p42FormateoUnidadesCaja(cantidad) {

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

function p42ControlModifFechaHasta(fechaHastaDDMMYYYY){
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

function p42ReloadMotivosBloqueo(rowid){

	var tipoPedido = $("#"+(rowid)+"_tipoPedidoM").val();
	var codTpBloqueo = "";
	//Control de tipo de bloqueo a controlar
	if ("P" == tipoPedido){
		codTpBloqueo = "P";
	}else if ("E" == tipoPedido){
		if ($("#"+(rowid)+"_fechaInPilM").val() != null && $("#"+(rowid)+"_fechaInPilM").val()!=''){
			codTpBloqueo = "EP";
		}else{
			codTpBloqueo = "E";
		}
	}

	var buscarPedidos = "S";
	if("1"==$("#"+(rowid)+"_perfilM").val()){
		buscarPedidos = "N";
	}

	reloadPopupP74($("#"+(rowid)+"_codArticuloM").val(), codTpBloqueo, $("#"+(rowid)+"_fechaInicioM").val(), $("#"+(rowid)+"_fecha2M").val(), $("#"+(rowid)+"_fecha3M").val(), $("#"+(rowid)+"_fecha4M").val(), $("#"+(rowid)+"_fecha5M").val(), $("#"+(rowid)+"_fechaInPilM").val(), $("#"+(rowid)+"_fechaFinM").val(), buscarPedidos, $("#"+(rowid)+"_clasePedidoM").val());
	//reloadPopupP74_2($("#"+(rowid)+"_codArticuloM").val(), codTpBloqueo, $("#"+(rowid)+"_fechaInicioM").val(), $("#"+(rowid)+"_fecha2M").val(), $("#"+(rowid)+"_fecha3M").val(), $("#"+(rowid)+"_fecha4M").val(), $("#"+(rowid)+"_fecha5M").val(), $("#"+(rowid)+"_fechaInPilM").val(), $("#"+(rowid)+"_fechaFinM").val(), buscarPedidos, $("#"+(rowid)+"_clasePedidoM").val());

	//reloadPopupP75($("#"+(rowid)+"_codArticuloM").val(), codTpBloqueo, $("#"+(rowid)+"_fechaInicioM").val(), $("#"+(rowid)+"_fecha2M").val(), $("#"+(rowid)+"_fecha3M").val(), $("#"+(rowid)+"_fecha4M").val(), $("#"+(rowid)+"_fecha5M").val(), $("#"+(rowid)+"_fechaInPilM").val(), $("#"+(rowid)+"_fechaFinM").val(), buscarPedidos, $("#"+(rowid)+"_clasePedidoM").val());
	//alert("change");
}

function hideShowColumnsM(){

	codArea = null;
	codSeccion = null;
	if ($("#p40_cmb_seccion").combobox('getValue') != null&& $("#p40_cmb_seccion").combobox('getValue') != '') {
		valorSeccion = $("#p40_cmb_seccion").combobox('getValue').split('*');
		codArea = valorSeccion[0];
		codSeccion = valorSeccion[1];
	}

	if(codArea==3){
		//Si es textil, se cambia el nombre de las coumnas cantidad1, cantidad2, cantidad3, cantidad4 por C 1, C 2, C 3, C 4
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setLabel', 'cantidad1', 'C 1');
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setLabel', 'cantidad2', 'C 2');
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setLabel', 'cantidad3', 'C 3');
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setLabel', 'cantidad4', 'C 4');

		//Añadir columna nueva
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('showCol',["color", "talla","modeloProveedor"]);
		//COLOR, TALLA, MODELO, PROVEEDOR


	}else{

		//Se restablecen los nombre de las cantidades
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setLabel', 'cantidad1', 'Cantid. 1');
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setLabel', 'cantidad2', 'Cantid. 2');
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setLabel', 'cantidad3', 'Cantid. 3');
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setLabel', 'cantidad4', 'Cantid. 4');
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('hideCol',["color", "talla","modeloProveedor"]);
	}

	if($("#p40_chk_mca").is(':checked')) {
		//El checkBox de MAC está activado
		//Se restablecen los nombre de las cantidades y descOferta
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setLabel', 'cantidad1', 'Cant. 1');
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setLabel', 'cantidad2', 'Cant. 2');
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setLabel', 'cantidad3', 'Cant. 3');
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setLabel', 'cantidad4', 'Cant. 4');
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setLabel', 'descOferta', 'Descripción');
		//Se muestra la columna descOferta
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('showCol',["descOferta"]);
	}
	else{
		//El checkBox de MAC está desactivado
		//Se restablecen los nombre de las cantidades y descOferta
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setLabel', 'cantidad1', 'Cantid. 1');
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setLabel', 'cantidad2', 'Cantid. 2');
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setLabel', 'cantidad3', 'Cantid. 3');
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setLabel', 'cantidad4', 'Cantid. 4');
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setLabel', 'descOferta', 'Desc. Oferta');
		//Se oculta la columna descOferta
		jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('hideCol',["descOferta"]);
	}
	jQuery(gridP42PedidoAdicionalM.nameJQuery).jqGrid('setGridWidth', $("#p42_AreaMontajeAdicional").width(), true);
}
