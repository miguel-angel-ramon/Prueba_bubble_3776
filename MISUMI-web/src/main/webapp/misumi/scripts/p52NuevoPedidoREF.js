var gridP52PedidosAdicionales=null;
var nuevoPedidoRef = null;
var tableFilter=null;
var withoutOffer = null;
var clientOrder = null;
var orders = null;
var additionalAssembly = null;
var ordersGrid = null;
var additionalAssemblyGrid = null;
var clientOrderGrid = null;
var v_Oferta = null;
var vReferenciasCentro = null;
var origen;
var endDateGrtrStartDate = null;
var noOferta = null;
var noStartDate = null;
var noEndDate = null;
var noReference = null;
var invalidCant1 = null;
var invalidCant2 = null;
var invalidCant3 = null;
var invalidCapMax = null;
var invalidImpMin = null;
var noClientName = null;
var noClientSurname = null;
var noTelephoneClient = null;
var noCenterContact = null;
var invalidCantidadEncargo = null;
var invalidTelefonoCliente = null;
var invalidDenominacion = null;
var noCajas = null;
var fechaInicio = null;
var fechaEncargo = null;
var noPedidoSave = null;
var noPedidoDelete = null;
var saveResultOK = null;
var saveResultError = null;
var implInicialError = null;
var implFinalError = null;
var titBloque3_Cajas = null;
var titBloque3_Unid = null;
var hrefBloque3 = null;
var referenceLocked = null;
var referenceNoMaintenance = null;
var referenceNotManaged = null;
var cambioTipoPedido = false;
var literalAyuda1ConOferta = null;
var literalAyuda1SinOferta = null;
var tipoPedidoRequired = null;
var centerRequired = null;
var noBloqueo = true;
var impFinalModif = false;
var mensajeAyudaActiva = null;
var loadReferenciaActiva = false;
var bajaCatalogoError = null;
var allowed = true;
var eventNodeId = "";
var fechaSelBloqueosEncargosMontajes = null;
var fechaSelBloqueosEncargos = null;
var fechaSelBloqueosMontajes = null;
var fechasEntregaBloqueadas = null;
var p52selectedRow = false;
var p52Generico = false;
var p64CodReferencia = null;
var p64DescReferencia = null;
var p52FocoAnterior = null;
var p52Uuid = null;
var p52Estado = null;
var legendAreaBloque2= null;
var legendAreaBloque2Encargos= null;
var activoBotonGuardar = true;

var mostrarCajasYExcluir = false;

var dialogoCargado = false;
function initializeScreenComponentsP52(){
	$("#p52_cmb_tipoPedidoAdicional").combobox(null);
	$("#p52_cmb_numeroOferta").combobox(null);
	$("#p52_leyendaReferenciaNueva").hide();
	$("#p52_rellenoExcluir").hide();
	$("#p52_cmb_excluir").combobox(null);
	$("#p52_cmb_cajas").combobox(null);
	$("#p52_cmb_tratamiento").combobox(null);

	$("#p52_ReferenciaLote").css("display", "none");
	$("#p52_ReferenciaHijaDeLote").css("display", "none");

	$.datepicker.setDefaults($.datepicker.regional['es']);
	//$( "#p52_fechaInicioDatePicker" ).datepicker( $.datepicker.regional[ 'esDiasServicio' ] );
	//cargarParametrosCalendarioCabP52();
	$( "#p52_fechaInicioDatePicker" ).datepicker({
		onSelect: function(dateText, inst) {
			if (null != nuevoPedidoRef){
				nuevoPedidoRef.fechaIni = $("#p52_fechaInicioDatePicker").datepicker("getDate");
				if (nuevoPedidoRef.tipoPedido == "1"){
					nuevoPedidoRef.fechaFin = $("#p52_fechaInicioDatePicker").datepicker("getDate");
				}
				if (nuevoPedidoRef.tipoPedido == "1" || nuevoPedidoRef.referenciaNueva){
					loadDates();
				} else {
					if (nuevoPedidoRef.fechaFin >= nuevoPedidoRef.fechaIni ){
						loadDates();
					} else {
						$("#p03_btn_aceptar").unbind("click");
						$("#p03_btn_aceptar").bind("click", function(e) {
							if (!$("#p52_div_cantidad1").is(':hidden')){
								$("#p52_fld_cantidad1").focus();
								$("#p52_fld_cantidad1").select();
							} else {
								$("#p52_fld_capMax").focus();
								$("#p52_fld_capMax").select();
							}
						});
						createAlert(endDateGrtrStartDate, "ERROR");
						$("#p03_btn_aceptar").focus();
					}
				}
			}
		}
	});

	//$( "#p52_fechaFinDatePicker" ).datepicker( $.datepicker.regional[ 'esDiasServicio' ] );
	//cargarParametrosCalendarioCabP52();
	$( "#p52_fechaFinDatePicker" ).datepicker({
		onSelect: function(dateText, inst) {
			if (null != nuevoPedidoRef){
				nuevoPedidoRef.fechaFin = $("#p52_fechaFinDatePicker").datepicker("getDate");
				if (nuevoPedidoRef.fechaFin >= nuevoPedidoRef.fechaIni){
					loadDates();
				} else {
					$("#p03_btn_aceptar").unbind("click");
					$("#p03_btn_aceptar").bind("click", function(e) {
						if (!$("#p52_div_cantidad1").is(':hidden')){
							$("#p52_fld_cantidad1").focus();
							$("#p52_fld_cantidad1").select();
						} else {
							$("#p52_fld_capMax").focus();
							$("#p52_fld_capMax").select();
						}
					});
					createAlert(endDateGrtrStartDate, "ERROR");
					$("#p03_btn_aceptar").focus();
				}
			}
		}

	});

	$( "#p52_fechaEntregaEncCliDatePicker" ).datepicker({
		onSelect: function(dateText, inst) {
			if (null != nuevoPedidoRef){
				nuevoPedidoRef.fechaIni = $("#p52_fechaEntregaEncCliDatePicker").datepicker("getDate");
				var fechaEntrega = $.datepicker.formatDate('D d', $("#p52_fechaEntregaEncCliDatePicker").datepicker("getDate"),{
					dayNamesShort: $.datepicker.regional[ "es" ].dayNamesMin,
					dayNames: $.datepicker.regional[ "es" ].dayNames,
					monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
					monthNames: $.datepicker.regional[ "es" ].monthNames
				});		 

				if (nuevoPedidoRef.esEncargoEspecial){
					$("#p52_fechaEntregaEncCliDatePicker").removeClass("encargosEspeciales");
				}else{
					if (new Date(nuevoPedidoRef.primeraFechaEntrega) > nuevoPedidoRef.fechaIni){
						// inst.dpDiv.find('.ui-datepicker-active').css('border', '1px solid #D8000C');
						$("#p52_fechaEntregaEncCliDatePicker").addClass("encargosEspeciales");
					} else {
						$("#p52_fechaEntregaEncCliDatePicker").removeClass("encargosEspeciales");
					} 
				}

				$('#p52_lbl_cantidad_enc_cli').text(fechaEntrega);
				$("#"+p52FocoAnterior).focus();
				$("#"+p52FocoAnterior).select();
			}
		}
	}).prop({
		disableBeforeDay:'S',
		dayControl: new Date()
	});

	$("#p52_fld_uc").attr("disabled", "disabled");
	$("#p52_fld_stock").attr("disabled", "disabled");
	$("#p52_fld_stock_plat").attr("disabled", "disabled");
	$("#p52_fld_denominacion").attr("disabled", "disabled");
	$("#p52_fld_aprov").attr("disabled", "disabled");
	$("#p52_fld_stock_enc_cli").attr("disabled", "disabled");

	$( "input[id^='p52_fld']" ).focusout(function(e) {
		p52FocoAnterior = e.target.id;
	});
}

function initializeScreenP52(){
	$("#p52_cmb_tipoPedidoAdicional").combobox(null);
	$("#p52_cmb_tipoPedidoAdicional").combobox({
		selected: function(event, ui) {
			if ( ui.item.value!="" && ui.item.value!="null"  ) {
				/**
				 * P-51880
				 * @author BICUGUAL
				 * El texto del fieldset de p52_AreaBloque2 debe cambiar en funcion de la seleccion del combo
				 * Si se selecciona ENCARGOS(1) debe aparecer 2)Introducimos FECHA ENTREGA y OPCIONAL: EXCLUIR
				 */
				if (ui.item.value==1){
					$('#p52_AreaBloque2Legend').hide();
					$('#p52_AreaBloque2LegendEncargo').show();
				}
				else{
					$('#p52_AreaBloque2Legend').show();
					$('#p52_AreaBloque2LegendEncargo').hide();
				}

				if (null != nuevoPedidoRef){
					if (nuevoPedidoRef.esGenerica){
						limpiarReferenciaP52();
						$("#p52_fld_referencia").focus();
						$("#p52_fld_referencia").select();
					} else {
						cambioTipoPedido = true;
						nuevoPedidoRef.tipoPedido = ui.item.value;
						if ($("#p52_cmb_tipoPedidoAdicional").combobox("getValue") != null && $("#p52_cmb_tipoPedidoAdicional").combobox("getValue") != ""){
							if ($("#p52_fld_referencia").val() != ""){
								p52getReferencia();

							} else {
								$("#p52_fld_referencia").focus();
								$("#p52_fld_referencia").select();
							}
						}
					}
				} else {
					$("#p52_fld_referencia").focus();
					$("#p52_fld_referencia").select();
				}	       

				if (esCentroCaprabo && $("#p52_cmb_tipoPedidoAdicional").val() !="3") {//Encargos de cliente
					$("#p52_fld_referencia").filter_input({regex:'[0-9]'});
				} else {
					$("#p52_fld_referencia").unbind('keypress')
				}

			}

		} ,		     
		changed: function(event, ui) { 
			if (ui.item!=null && ui.item.value!="" && ui.item.value!="null"){

				if (null != nuevoPedidoRef){
					if (nuevoPedidoRef.esGenerica){
						limpiarReferenciaP52();
						$("#p52_fld_referencia").focus();
						$("#p52_fld_referencia").select();
					} else {
						cambioTipoPedido = true;
						nuevoPedidoRef.tipoPedido = ui.item.value;
						if ($("#p52_cmb_tipoPedidoAdicional").combobox("getValue") != null && $("#p52_cmb_tipoPedidoAdicional").combobox("getValue") != ""){
							if ($("#p52_fld_referencia").val() != ""){
								p52getReferencia();

							} else {
								$("#p52_fld_referencia").focus();
								$("#p52_fld_referencia").select();
							}
						}
					}
				} else {
					$("#p52_fld_referencia").focus();
					$("#p52_fld_referencia").select();
				}	        		   
			}	 
		}
	}); 
	$("#p52_cmb_numeroOferta").combobox(null);
	$("#p52_leyendaReferenciaNueva").hide();
	$("#p52_rellenoExcluir").hide();
	$("#p52_cmb_numeroOferta").combobox({
		selected: function(event, ui) {
			if ( ui.item.value!="" && ui.item.value!="null") {
				if (null != nuevoPedidoRef){
					nuevoPedidoRef.oferta = ui.item.value;
					loadOferta();

					if (nuevoPedidoRef.oferta != "0")
					{
						//En este caso selecciona oferta tenemos que ocultar el Excluir.
						$("#p52_cmb_excluir").combobox('autocomplete','N');
						$("#p52_cmb_excluir").val("N");
						$("#p52_excluir").hide();

					}
					else
					{
						//En este caso selecciona SIN OFERTA tenemos que mostrar el Excluir.
						$("#p52_cmb_excluir").combobox('autocomplete','S');
						$("#p52_cmb_excluir").val("S");
						if(mostrarCajasYExcluir){
							$("#p52_excluir").show();
						}else{
							$("#p52_excluir").hide();
							$("#p52_cajas").hide();
						}
					}
				} 
			}
		},		       
		change: function(event, ui) { 
			if (ui.item!=null && ui.item.value!="" && ui.item.value!="null"){
				if (null != nuevoPedidoRef){
					nuevoPedidoRef.oferta = ui.item.value;
					loadOferta();

				}
			}	
		}
	});
	$("#p52_cmb_numeroOferta").bind("focus", function () {
		$(this).autocomplete("search", '');
	});
	$("#p52_cmb_excluir").combobox({
		selected: function(event, ui) {
			if ( ui.item.value!="" && ui.item.value!="null"  ) {
				if (null != nuevoPedidoRef){
					nuevoPedidoRef.excluir = ui.item.value;
				}
			}

		} ,		       
		changed: function(event, ui) { 
			if (ui.item!=null && ui.item.value!="" && ui.item.value!="null"){
				if (null != nuevoPedidoRef){
					nuevoPedidoRef.excluir = ui.item.value;
				}
			}	 
		}
	});
	$("#p52_cmb_cajas").combobox({
		selected: function(event, ui) {
			if ( ui.item.value!="" && ui.item.value!="null"  ) {
				if (null != nuevoPedidoRef){
					nuevoPedidoRef.cajas = ui.item.value;
					if (nuevoPedidoRef.esStock && nuevoPedidoRef.tipoPedido =="1"){
						getStockPlataformaP52();
					}
				}
			}

		} ,		       
		changed: function(event, ui) { 
			if (ui.item!=null && ui.item.value!="" && ui.item.value!="null"){
				if (null != nuevoPedidoRef){
					nuevoPedidoRef.cajas = ui.item.value;
					if (nuevoPedidoRef.esStock && nuevoPedidoRef.tipoPedido =="1"){
						getStockPlataformaP52();
					}
				}	   
			}	 
		}
	});
	$("#p52_cmb_tratamiento").combobox({
		selected: function(event, ui) {
			if ( ui.item.value!="" && ui.item.value!="null"  ) {
				if (null != nuevoPedidoRef){	
					nuevoPedidoRef.tratamiento = ui.item.value;
					if (nuevoPedidoRef.tratamiento=="A"){
						$("#p52_cmb_tratamiento").combobox('autocomplete',"Añadir");
						$("#p52_cmb_tratamiento").combobox('comboautocomplete',"Añadir         - Añade cantidad a transmisión");
					}else if(nuevoPedidoRef.tratamiento == "S"){
						$("#p52_cmb_tratamiento").combobox('autocomplete',"Sustituir");
						$("#p52_cmb_tratamiento").combobox('comboautocomplete',"Sustituir      - Machaca la cantidad transmitida por las del encargo");
					}else{
						$("#p52_cmb_tratamiento").combobox('autocomplete',"Fija/Mínima");
						$("#p52_cmb_tratamiento").combobox('comboautocomplete',"Fija/Mínima - Aplica la mayor cantidad entre lo transmitido y las unidades del encargo. Garantiza al menos la cantidad del encargo");
					}
					$("#p52_cmb_tratamiento").val(nuevoPedidoRef.tratamiento);
				}else{
					$("#p52_cmb_tratamiento").combobox('autocomplete',"Añadir");
					$("#p52_cmb_tratamiento").combobox('comboautocomplete',"Añadir         - Añade cantidad a transmisión");
					$("#p52_cmb_tratamiento").val("A");
				}
			}

		} ,		       
		changed: function(event, ui) { 
			if (ui.item!=null && ui.item.value!="" && ui.item.value!="null"){
				if (null != nuevoPedidoRef){
					nuevoPedidoRef.tratamiento = ui.item.value;
					$("#p52_cmb_tratamiento").combobox('autocomplete',nuevoPedidoRef.tratamiento);
				}	   
			}	 
		}
	});
	$.datepicker.setDefaults($.datepicker.regional['es']);
	//$( "#p52_fechaInicioDatePicker" ).datepicker( $.datepicker.regional[ 'esDiasServicio' ] );
	cargarParametrosCalendarioCabP52();
	$( "#p52_fechaInicioDatePicker" ).datepicker({
		onSelect: function(dateText, inst) {
			if (null != nuevoPedidoRef){
				nuevoPedidoRef.fechaIni = $("#p52_fechaInicioDatePicker").datepicker("getDate");
				if (nuevoPedidoRef.tipoPedido == "1"){
					nuevoPedidoRef.fechaFin = $("#p52_fechaInicioDatePicker").datepicker("getDate");
				}
				if (nuevoPedidoRef.tipoPedido == "1" || nuevoPedidoRef.referenciaNueva){
					loadDates();
				} else {
					if (nuevoPedidoRef.fechaFin >= nuevoPedidoRef.fechaIni ){
						loadDates();
					} else {
						$("#p03_btn_aceptar").unbind("click");
						$("#p03_btn_aceptar").bind("click", function(e) {
							if (!$("#p52_div_cantidad1").is(':hidden')){
								$("#p52_fld_cantidad1").focus();
								$("#p52_fld_cantidad1").select();
							} else {
								$("#p52_fld_capMax").focus();
								$("#p52_fld_capMax").select();
							}
						});
						createAlert(endDateGrtrStartDate, "ERROR");
						$("#p03_btn_aceptar").focus();
					}
				}
			}
		}
	});

	//$( "#p52_fechaFinDatePicker" ).datepicker( $.datepicker.regional[ 'esDiasServicio' ] );
	cargarParametrosCalendarioCabP52();
	$( "#p52_fechaFinDatePicker" ).datepicker({
		onSelect: function(dateText, inst) {
			if (null != nuevoPedidoRef){
				nuevoPedidoRef.fechaFin = $("#p52_fechaFinDatePicker").datepicker("getDate");
				if (nuevoPedidoRef.fechaFin >= nuevoPedidoRef.fechaIni){
					loadDates();
				} else {
					$("#p03_btn_aceptar").unbind("click");
					$("#p03_btn_aceptar").bind("click", function(e) {
						if (!$("#p52_div_cantidad1").is(':hidden')){
							$("#p52_fld_cantidad1").focus();
							$("#p52_fld_cantidad1").select();
						} else {
							$("#p52_fld_capMax").focus();
							$("#p52_fld_capMax").select();
						}
					});
					createAlert(endDateGrtrStartDate, "ERROR");
					$("#p03_btn_aceptar").focus();
				}
			}
		}

	});


	$("#p52_fld_uc").attr("disabled", "disabled");
	$("#p52_fld_stock").attr("disabled", "disabled");
	$("#p52_fld_stock_plat").attr("disabled", "disabled");
	$("#p52_fld_denominacion").attr("disabled", "disabled");
	$("#p52_fld_aprov").attr("disabled", "disabled");

	//$("#p52_cmb_numeroOferta").combobox('autocomplete',"");
	//$("#p52_cmb_numeroOferta").combobox('comboautocomplete',null);
	$("#p52_cmb_excluir").combobox('autocomplete',"");
	$("#p52_cmb_excluir").combobox('comboautocomplete',null);

	$("#p52_cmb_cajas").combobox('autocomplete',"");
	$("#p52_cmb_cajas").combobox('comboautocomplete',null);

	//Ocultado de ayuda en campos no vinculados a la misma
	$("#p51_fld_numoferta").focus(function() {
	});

	$("#p52_fld_cantidad_enc_cli").click(function() {
		if($("#p52_cmb_tipoPedidoAdicional").combobox("getValue") =="3"){//Encargos de cliente
			if (null != nuevoPedidoRef){
				reloadDataP65(nuevoPedidoRef);
			}
		}
	});

	$("#p52_fld_referencia").click(function() {
		limpiarReferenciaP52();

	});
	$("#p52_fld_referencia").focus(function() {
		$("#p56_AreaAyuda").dialog('close');

	});
	//Hay que controlar el tabulador en referencia porque produce errorees en la recuperación
	//de los datos de la oferta al posicionarse sobre el combo y redefinirlo
	$("#p52_fld_referencia").on("keydown", function(e) {
		var key = e.which; //para soporte de todos los navegadores
		if (key == 9){//Tecla tabulación
			if (e.target.id == eventNodeId) {
				eventNodeId = "";
				if (e.preventDefault) { e.preventDefault(); } else { e.returnValue = false; }
				return false;
			}
			if (e.preventDefault) { e.preventDefault(); } else { e.returnValue = false; }
			eventNodeId = e.target.id;
			e.preventDefault();
			p52getReferencia();
		}
	});
	$("#p52_fld_referencia").keyup(function(e) { 
		allowed = true;
	});
	$("#p52_fld_referencia").focus(function(e) { 
		allowed = true;
	});
	$(".controlReturnP52").on("keydown", function(e) {
		if (e.target.id == "p52_fld_referencia" && e.which == 13) {
			// Para que no se lanzen el evento $("#p52_fld_referencia").on("keydown" y el
			// evento $(".controlReturnP52").on("keydown" a la vez 
			// sobre el input "p52_fld_referencia".
			if (!allowed) {
				return false;
			}
			allowed = false;
			if (e.target.id == eventNodeId) {
				eventNodeId = "";
				if (e.preventDefault) { e.preventDefault(); } else { e.returnValue = false; }
				return false;
			}
			eventNodeId = e.target.id;
		}
		if(e.which == 13) {
			e.preventDefault();
			if($("#p52_fld_referencia").is(":focus")){
				p52getReferencia();
			} else if ($("#p52_fld_cantidad1").is(":focus") && null != nuevoPedidoRef){
				if(validarCantidad($("#p52_fld_cantidad1").val()) == false){
					$("#p03_btn_aceptar").unbind("click");
					$("#p03_btn_aceptar").bind("click", function(e) {
						$("#p52_fld_cantidad1").focus();
						$("#p52_fld_cantidad1").select();
					});
					createAlert(invalidCant1, "ERROR");
					$("#p03_btn_aceptar").focus();
				}else if (!$("#p52_div_cantidad2").is(':hidden')){
					$("#p52_fld_cantidad2").focus();
					$("#p52_fld_cantidad2").select();
				} else {
					finder();
				}
			} else if ($("#p52_fld_cantidad2").is(":focus") && null != nuevoPedidoRef){
				if(validarCantidad($("#p52_fld_cantidad2").val()) == false){
					$("#p03_btn_aceptar").unbind("click");
					$("#p03_btn_aceptar").bind("click", function(e) {
						$("#p52_fld_cantidad2").focus();
						$("#p52_fld_cantidad2").select();
					});
					createAlert(invalidCant2, "ERROR");
					$("#p03_btn_aceptar").focus();
				}else if (!$("#p52_div_cantidad3").is(':hidden')){
					$("#p52_fld_cantidad3").focus();
					$("#p52_fld_cantidad3").select();
				} else {
					finder();
				}

			} else if ($("#p52_fld_cantidad3").is(":focus") && null != nuevoPedidoRef){
				if(validarCantidad($("#p52_fld_cantidad3").val()) == false){
					$("#p03_btn_aceptar").unbind("click");
					$("#p03_btn_aceptar").bind("click", function(e) {
						$("#p52_fld_cantidad3").focus();
						$("#p52_fld_cantidad3").select();
					});
					createAlert(invalidCant3, "ERROR");
					$("#p03_btn_aceptar").focus();
				}else {
					finder();
				}

			} else if ($("#p52_fld_capMax").is(":focus") && null != nuevoPedidoRef){
				if(validarCantidadAlim($("#p52_fld_capMax").val()) == false || $("#p52_fld_capMax").val() <= 0 || $("#p52_fld_capMax").val() > 9999 ){
					$("#p03_btn_aceptar").unbind("click");
					$("#p03_btn_aceptar").bind("click", function(e) {
						$("#p52_fld_capMax").focus();
						$("#p52_fld_capMax").select();
					});
					createAlert(invalidCapMax, "ERROR");
					$("#p03_btn_aceptar").focus();
					valido = false;
				} else {
					p52ImplantacionInicial();
					if ($("#p52_fld_impMin").is(":enabled")){
						$("#p52_fld_impMin").focus();
						$("#p52_fld_impMin").select();
					} else {
						finder();
					}

				}

			} else if ($("#p52_fld_impMin").is(":focus") && null != nuevoPedidoRef){
				if (validarCantidadAlim($("#p52_fld_impMin").val()) == false || $("#p52_fld_impMin").val() <= 0 || $("#p52_fld_impMin").val() > 9999){
					$("#p03_btn_aceptar").unbind("click");
					$("#p03_btn_aceptar").bind("click", function(e) {
						$("#p52_fld_impMin").focus();
						$("#p52_fld_impMin").select();
					});
					createAlert(invalidImpMin, "ERROR");
					$("#p03_btn_aceptar").focus();
				} else {
					finder();
				}
			} else if ($("#p52_fld_clienteNombre").is(":focus") && null != nuevoPedidoRef){
				$("#p52_fld_clientePrimerApellido").focus();
				$("#p52_fld_clientePrimerApellido").select();
				e.stopPropagation();
			} else if ($("#p52_fld_clientePrimerApellido").is(":focus") && null != nuevoPedidoRef){
				$("#p52_fld_clienteTelefono").focus();
				$("#p52_fld_clienteTelefono").select();
				e.stopPropagation();
			} else if ($("#p52_fld_clienteTelefono").is(":focus") && null != nuevoPedidoRef){
				$("#p52_fld_centroContacto").focus();
				$("#p52_fld_centroContacto").select();
				e.stopPropagation();
			} else {
				finder();
				e.stopPropagation();
			}
		}
	});
	$("#p52_fld_referencia").on("focusout", function(e) {
		if (e.target.id == eventNodeId) {
			eventNodeId = "";
			if (e.preventDefault) { e.preventDefault(); } else { e.returnValue = false; }
			return false;
		}
		eventNodeId = "";
		if ($("#p52_cmb_tipoPedidoAdicional").combobox("getValue") == "3" && !$("#p52_fld_referencia").val()){
			return false;
		}
		p52getReferencia();
	});
	$("#p52_fld_denominacion").focus(function() {
	});

	//Mostrado de ayuda en campos vinculados
	$("#p52_fld_cantidad1").filter_input({regex:'[0-9,]'});
	$("#p52_fld_cantidad2").filter_input({regex:'[0-9,]'});
	$("#p52_fld_cantidad3").filter_input({regex:'[0-9,]'});
	$("#p52_fld_capMax").filter_input({regex:'[0-9]'});
	$("#p52_fld_impMin").filter_input({regex:'[0-9]'});

	$("#p52_fld_cantidad1").click(function(event) {
		$("#p52_fld_cantidad1").focus();
		$("#p52_fld_cantidad1").select();
	});

	/*$("#p52_fld_cantidad2").focus(function(event) {
		if(!$("#p56_AreaAyuda").is(':visible')){
			loadPopUpAyudaP52(event);
		}
	});*/

	$("#p52_fld_cantidad2").click(function(event) {
		$("#p52_fld_cantidad2").focus();
		$("#p52_fld_cantidad2").select();
	});

	/*$("#p52_fld_cantidad3").focus(function(event) {
		if(!$("#p56_AreaAyuda").is(':visible')){
			loadPopUpAyudaP52(event);
		}
	});*/

	$("#p52_fld_cantidad3").click(function(event) {
		$("#p52_fld_cantidad3").focus();
		$("#p52_fld_cantidad3").select();
	});

	/*$("#p52_fld_capMax").focus(function(event) {
		if(!$("#p56_AreaAyuda").is(':visible')){
			loadPopUpAyudaP52(event);

		}
	});*/

	$("#p52_fld_capMax").click(function(event) {
		$("#p52_fld_capMax").focus();
		$("#p52_fld_capMax").select();
	});

	$("#p52_fld_capMax").change(function(event) {
		// Inicio MISUMI-409.
		if ($("#p52_fld_capMax").val() != null && nuevoPedidoRef.cantidadMaxima != null	&& nuevoPedidoRef.cantidadMaxima > 0
				&& $("#p52_fld_capMax").val() > nuevoPedidoRef.cantidadMaxima){
			if (!$("#popupShowMessage").is(':visible')){
				let cantidadMaximaVegalsaNuevo = cantidadMaximaVegalsa+nuevoPedidoRef.cantidadMaxima;
				createAlert(cantidadMaximaVegalsaNuevo, "ERROR");
			}
		}else{
		// FIN MISUMI-409.
			p52ImplantacionInicial();
		}
	});

	$("#p52_fld_cantidad_enc_cli").change(function(event) {
		nuevoPedidoRef.datosPedidoUnidadesPedir = $(this).val();

		//Begin calendario encargos cliente
		//Cuando se cambia las cantidades a pedir recalculamos el calendario

		p52CargaDatosCalendarioEncCli();
		$("#encargoClienteUnidadesPedirCalendario").val(nuevoPedidoRef.datosPedidoUnidadesPedir);
		$("#encargoClienteEspecialCalendario").val(nuevoPedidoRef.esEncargoEspecial);
		var fechaEntrega = new Date( nuevoPedidoRef.primeraFechaEntrega );

		if (nuevoPedidoRef.esEncargoEspecial){
			$("#p52_fechaEntregaEncCliDatePicker").removeClass("encargosEspeciales");
		}else{
			if (new Date(nuevoPedidoRef.primeraFechaEntrega) > fechaEntrega){
				// inst.dpDiv.find('.ui-datepicker-active').css('border', '1px solid #D8000C');
				$("#p52_fechaEntregaEncCliDatePicker").addClass("encargosEspeciales");
			} else {
				$("#p52_fechaEntregaEncCliDatePicker").removeClass("encargosEspeciales");
			} 
		}

		$("#p52_fechaEntregaEncCliDatePicker").datepicker("setDate", fechaEntrega);
		$("#p52_fechaEntregaEncCliDatePicker").datepicker("refresh");
		//End calendario encargos cliente

		comprobarFechaEntregaDatosCliente();
	});

	/*$("#p52_fld_impMin").focus(function(event) {
		if(!$("#p56_AreaAyuda").is(':visible')){
			loadPopUpAyudaP52(event);
		}
	});*/

	$("#p52_fld_impMin").change(function(event) {
		nuevoPedidoRef.implantacionMinima = $(this).val();
	});

	$("#p52_fld_impMin").click(function(event) {
		$("#p52_fld_impMin").focus();
		$("#p52_fld_impMin").select();
	});

	$("#p52_btn_anadir").click(function() {
		finder();
	});

	$("#p52_btn_borrar").click(function() {
		deleteGrid();
	});

	$("#p52_btn_guardar").click(function() {
		if (activoBotonGuardar){
			activoBotonGuardar = false;
			saveGrid();
		}
	});

	/*	$("#p52_fld_capMax").keydown(function(e) {
		if (e.which == 9){//Tecla tabulador,
			e.preventDefault();
			if (!$("#p52_div_impMin").is(':hidden')){
				$("#p52_fld_impMin").focus();
			} else {
				$("#p52_fld_referencia").focus();
			}
	    }
	});*/

	/*	$("#p52_fld_referencia").keydown(function(e) {
		if ((e.which == 9)&&(modificadoRef != null)&&(modificadoRef != 'null')){//Tecla tabulador,
			//e.preventDefault();
			alert("modificadoRef--->"+modificadoRef);
			if (!$("#p52_div_cantidad1").is(':hidden')){
				$("#p52_fld_cantidad1").focus();
			} else {
				$("#p52_fld_capMax").focus();
			}
			e.preventDefault();
	    }
		else
		{
			modificadoRef = "S";
		}

	});*/

	/*	$("#p52_fld_referencia").focusout(function(e) {
		if (modificadoRef == null){
			e.stopPropagation();
	    }
	});*/

	$("#p52_fld_cantidad1").keydown(function(e) {
		if (e.which == 9){//Tecla tabulador,
			e.preventDefault();
			if (!$("#p52_div_cantidad2").is(':hidden')){
				$("#p52_fld_cantidad2").focus();
				$("#p52_fld_cantidad2").select();
			} else {
				$("#p52_fld_referencia").focus();
				$("#p52_fld_referencia").select();
			}
		}

	});

	$("#p52_fld_cantidad2").keydown(function(e) {
		if (e.which == 9){//Tecla tabulador,
			e.preventDefault();
			if (!$("#p52_div_cantidad3").is(':hidden')){
				$("#p52_fld_cantidad3").focus();
				$("#p52_fld_cantidad3").select();
			} else {
				$("#p52_fld_referencia").focus();
				$("#p52_fld_referencia").select();
			}
		}

	});

	$("#p52_fld_cantidad3").keydown(function(e) {

		if (e.which == 9){//Tecla tabulador,
			e.preventDefault();
			$("#p52_fld_referencia").focus();
			$("#p52_fld_referencia").select();
		}

	});


	$("#p52_fld_impMin").keydown(function(e) {

		if (e.which == 9){//Tecla tabulador,
			e.preventDefault();
			$("#p52_fld_referencia").focus();
			$("#p52_fld_referencia").select();
		}

	});

	$("#p52_fld_capMax").keydown(function(e) {
		if (e.which == 9){//Tecla tabulador,
			e.preventDefault();
			if ($("#p52_fld_impMin").attr("disabled") != "disabled"){
				$("#p52_fld_impMin").focus();
				$("#p52_fld_impMin").select();
			} else {
				// Inicio MISUMI-409.	
				if (!$("#popupShowMessage").is(':visible')){

					if ($("#p52_fld_capMax").val() != null && nuevoPedidoRef.cantidadMaxima != null && nuevoPedidoRef.cantidadMaxima > 0
							&& $("#p52_fld_capMax").val() > nuevoPedidoRef.cantidadMaxima){
						if (!$("#popupShowMessage").is(':visible')){
							let cantidadMaximaVegalsaNuevo = cantidadMaximaVegalsa+nuevoPedidoRef.cantidadMaxima;
							createAlert(cantidadMaximaVegalsaNuevo, "ERROR");
						}
					}else{
				// FIN MISUMI-409
						$("#p52_fld_referencia").focus();
						$("#p52_fld_referencia").select();
					}
				}
			}
		}
	});

	/*	$("#p52_fld_referencia").keydown(function(e) {
		if ((e.which == 9)&&(modificadoRef != null)&&(modificadoRef != 'null')){//Tecla tabulador,
			//e.preventDefault();
			alert("modificadoRef--->"+modificadoRef);
			if (!$("#p52_div_cantidad1").is(':hidden')){
				$("#p52_fld_cantidad1").focus();
			} else {
				$("#p52_fld_capMax").focus();
			}
			e.preventDefault();
	    }
		else
		{
			modificadoRef = "S";
		}

	});*/

	/*	$("#p52_fld_referencia").focusout(function(e) {
		if (modificadoRef == null){
			e.stopPropagation();
	    }
	});*/

	$("#p52_fld_cantidad1").keydown(function(e) {
		if (e.which == 9){//Tecla tabulador,
			e.preventDefault();
			if (!$("#p52_div_cantidad2").is(':hidden')){
				$("#p52_fld_cantidad2").focus();
				$("#p52_fld_cantidad2").select();
			} else {
				$("#p52_fld_referencia").focus();
				$("#p52_fld_referencia").select();
			}
		}

	});

	$("#p52_fld_cantidad2").keydown(function(e) {
		if (e.which == 9){//Tecla tabulador,
			e.preventDefault();
			if (!$("#p52_div_cantidad3").is(':hidden')){
				$("#p52_fld_cantidad3").focus();
				$("#p52_fld_cantidad3").select();
			} else {
				$("#p52_fld_referencia").focus();
				$("#p52_fld_referencia").select();
			}
		}

	});

	$("#p52_fld_cantidad3").keydown(function(e) {

		if (e.which == 9){//Tecla tabulador,
			e.preventDefault();
			$("#p52_fld_referencia").focus();
			$("#p52_fld_referencia").select();
		}

	});

	$("#p52_fld_impMin").keydown(function(e) {

		if (e.which == 9){//Tecla tabulador,
			e.preventDefault();
			$("#p52_fld_referencia").focus();
			$("#p52_fld_referencia").select();
		}

	});

	$("#p52_fld_impMin").keydown(function(e) {

		if (e.which == 9){//Tecla tabulador,
			e.preventDefault();
			$("#p52_fld_referencia").focus();
			$("#p52_fld_referencia").select();
		}

	});

	$("#p52_btn_cancelar").click(function() {

		//Primero antes de volver tenemos que comprobar que no haya nada pendiente de guardar.
		var gridCount = $(gridP52PedidosAdicionales.nameJQuery).jqGrid('getGridParam', 'reccount');
		if (gridCount > 0) {
			//En este caso debemos mostrar un aviso de que hay cosas pendientes de guardar y que se pueden perder.
			$(function() {
				$( "#p52dialogCancel-confirm" ).dialog({
					resizable: false,
					height:160,
					modal: true,
					buttons: {
						"Si": function() {
							p52RetornoPedidoAdicional();
							$( this ).dialog( "close" );
						},
						"No": function() {
							$( this ).dialog( "close" );
						}
					}
				});
			});
		}
		else
		{
			p52RetornoPedidoAdicional();
		}
	});

	initializeP59();
	reloadDatosReferencia();
	$("#p52_btn_ayudaActiva").click(function () {
		createAlert(replaceSpecialCharacters(mensajeAyudaActiva), "HELP");
	});
	$("#p52_btn_ayudaActivaEncCli").click(function () {
		createAlert(replaceSpecialCharacters(mensajeAyudaActiva), "HELP");
	});
}

function cargarParametrosCalendarioCabP52(){
	$("#codCentroCalendario").val($("#centerId").val());
	$("#codArticuloCalendario").val("");
	$("#identificadorCalendario").val("");
	$("#identificadorSIACalendario").val("");
	$("#clasePedidoCalendario").val("");
	$("#recargarParametrosCalendario").val("S");
	$("#cargadoDSCalendario").val("N");
	if (nuevoPedidoRef != null && nuevoPedidoRef.frescoPuro != null){
		if (nuevoPedidoRef.frescoPuro){
			$("#esFresco").val("S");
		}else{
			$("#esFresco").val("N");
		}
	}
}

function p52ControlTitulo(tipoPedido){

	if (tipoPedido == "1"){
		//Se trata de un ENCARGO, ponemos el título correspondiente.
		$("#p52_div_legend3").html(replaceSpecialCharacters(titBloque3_Cajas)+hrefBloque3);
	} else {
		//Se trata de un MONTAJE, ponemos el título correspondiente.
		$("#p52_div_legend3").html(replaceSpecialCharacters(titBloque3_Unid)+hrefBloque3);
	}
	event_p52_ayuda();
}


function p52RetornoPedidoAdicional(){

	window.location='./pedidoAdicional.do?flagCancelarNuevo=S&pestanaOrigen='+$("#p50_pestanaOrigen").val()+'&codArea='+$("#p50_codArea").val()+
	'&codSeccion='+$("#p50_codSeccion").val()+'&codCategoria='+$("#p50_codCategoria").val()+'&referencia='+$("#p50_referencia").val();
}

function load_combosP52(){

	console.log("montaje adicional = "+$('#p01_txt_tipoPedidoAdicional_opc_montajeAdicional').val());
	console.log("encargos = "+$('#p01_txt_tipoPedidoAdicional_opc_encargos').val());
	console.log("encargos cliente = "+$('#p01_txt_tipoPedidoAdicional_opc_encargoCliente').val());
	var options1 = "";
	//MISUMI-352: PARAMETRIZACION DE LAS OPCIONES DE TIPO_PEDIDO_ADICIONAL
	// MONTAJE ADICIOAL
	if($('#p01_txt_tipoPedidoAdicional_opc_montajeAdicional').length && $('#p01_txt_tipoPedidoAdicional_opc_montajeAdicional').val()){
		options1 += "<option value='2'>"+additionalAssembly+"</option>";
	}
	// ENCARGOS
	if($('#p01_txt_tipoPedidoAdicional_opc_encargos').length && $('#p01_txt_tipoPedidoAdicional_opc_encargos').val()){
		options1 += "<option value='1'>"+orders+"</option>";
	}
	// ENCARGOS CLIENTE
	if($('#p01_txt_tipoPedidoAdicional_opc_encargoCliente').length && $('#p01_txt_tipoPedidoAdicional_opc_encargoCliente').val()){
		options1 += "<option value='3'>"+clientOrder+"</option>";		
	}

	$("select#p52_cmb_tipoPedidoAdicional").html(options1);
	$("#p52_cmb_tipoPedidoAdicional").combobox(null);
	$("#p52_cmb_tipoPedidoAdicional").combobox('autocomplete',"");
	$("#p52_cmb_tipoPedidoAdicional").combobox('comboautocomplete',null);
	//var text = $("#p52_cmb_tipoPedidoAdicional option[value='1']").text();
	//options2 = "<option value='123456'>123456</option><option value='654321'>654321</option><option value='0'>SIN OFERTA</option>";
	//$("select#p52_cmb_numeroOferta").html(options2);
	options3 = "<option value='S'>S</option><option value='N'>N</option>";
	$("select#p52_cmb_excluir").html(options3);
	$("select#p52_cmb_cajas").html(options3);
	//MISUMI-437
	options4 =          "<option value='A'>Añadir          - Añade cantidad a transmisión</option>";
	options4 = options4+"<option value='S'>Sustituir      - Machaca la cantidad transmitida por las del encargo</option>";
	options4 = options4+"<option value='F'>Fija/Mínima - Aplica la mayor cantidad entre lo transmitido y las unidades del encargo. Garantiza al menos la cantidad del encargo</option>";
	$("select#p52_cmb_tratamiento").html(options4);
	$("#p52_cmb_tipoPedidoAdicional").focus();


}

function resetResultadosNuevoPedidoREF(){

	if (gridP52PedidosAdicionales != null) {
		gridP52PedidosAdicionales.clearGrid();
	}

}

function reloadDatosReferencia() {

	$("#p50_pestanaReferenciaCargada").val("S");

	loadP52PedidosAdicionales(locale);
}

function loadP52PedidosAdicionales(locale){
	gridP52PedidosAdicionales = new GridP52PedidosAdicionales(locale);

	var jqxhr = $.getJSON(gridP52PedidosAdicionales.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		gridP52PedidosAdicionales.colNames = data.p52PedidosAdicionalesColNames;
		gridP52PedidosAdicionales.title = data.p52PedidosAdicionalesGridTitle;
		gridP52PedidosAdicionales.emptyRecords= data.emptyRecords;
		endDateGrtrStartDate = data.endDateGrtrStartDate;
		noOferta = data.noOferta;
		noStartDate = data.noStartDate;
		noEndDate = data.noEndDate;
		noReference = data.noReference;
		invalidCant1 = data.invalidCant1;
		invalidCant2 = data.invalidCant2;
		invalidCant3 = data.invalidCant3;
		invalidCapMax = data.invalidCapMax;
		invalidImpMin = data.invalidImpMin;
		noClientName = data.noClientName;
		noClientSurname = data.noClientSurname;
		noTelephoneClient = data.noTelephoneClient;
		noCenterContact = data.noCenterContact;
		invalidCantidadEncargo = data.invalidCantidadEncargo;
		invalidTelefonoCliente = data.invalidTelefonoCliente;
		invalidDenominacion = data.invalidDenominacion;
		noCajas = data.noCajas;
		fechaInicio = data.fechaInicio;
		fechaEncargo = data.fechaEncargo;
		tableFilter = data.tableFilter;
		withoutOffer = data.withoutOffer;
		clientOrder = data.clientOrder;
		orders = data.orders;
		additionalAssembly = data.additionalAssembly;
		noPedidoSave = data.noPedidoSave;
		noPedidoDelete = data.noPedidoDelete;
		saveResultOK = data.saveResultOK;
		saveResultError = data.saveResultError;
		implInicialError = data.implInicialError;
		implFinalError = data.implFinalError;
		titBloque3_Cajas = data.titBloque3_Cajas;
		titBloque3_Unid = data.titBloque3_Unid;
		hrefBloque3 = data.hrefBloque3;
		ordersGrid = data.ordersGrid;
		additionalAssemblyGrid = data.additionalAssemblyGrid;
		clientOrderGrid = data.clientOrderGrid;
		referenceLocked = data.referenceLocked;
		referenceNoMaintenance = data.referenceNoMaintenance;
		referenceNotManaged = data.referenceNotManaged;
		literalAyuda1ConOferta = data.literalAyuda1ConOferta;
		literalAyuda1SinOferta = data.literalAyuda1SinOferta;
		tipoPedidoRequired = data.tipoPedidoRequired;
		centerRequired = data.centerRequired;
		mensajeAyudaActiva = data.mensajeAyudaActiva;
		bajaCatalogoError = data.bajaCatalogoError;
		fechaSelBloqueosEncargosMontajes = data.fechaSelBloqueosEncargosMontajes;
		fechaSelBloqueosEncargos = data.fechaSelBloqueosEncargos;
		fechaSelBloqueosMontajes = data.fechaSelBloqueosMontajes;
		fechasEntregaBloqueadas = data.fechasEntregaBloqueadas;
		cantidadMaximaVegalsa = data.cantidadMaximaVegalsa;
		load_combosP52();
		loadP52PedidosAdicionalesMock(gridP52PedidosAdicionales);
		p52SetHeadersTitles(data);
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

function loadP52PedidosAdicionalesMock(gridP52PedidosAdicionales) {
	$(gridP52PedidosAdicionales.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		url : './misumi/resources/mock.json',
		datatype : 'json',
		contentType : 'application/json',
		mtype : 'POST',
		colNames : gridP52PedidosAdicionales.colNames,
		colModel : gridP52PedidosAdicionales.cm,
		rowNum : 10,
		rowList : [ 10, 20, 30 ],
		height : "100%",
		autowidth : true,
		width : "auto",
		rownumbers : true,
		pager : gridP52PedidosAdicionales.pagerNameJQuery,
		viewrecords : true,
		caption : gridP52PedidosAdicionales.title,
		altclass: "ui-priority-secondary",
		altRows: true, //false, para que el grid no muestre cebrado
		hidegrid : false, //false, para ocultar el boton que colapsa el grid.
		sortable : true,
		multiselect: true,
		index: gridP52PedidosAdicionales.sortIndex,
		sortname: gridP52PedidosAdicionales.sortIndex,
		sortorder: gridP52PedidosAdicionales.sortOrder,
		emptyrecords : gridP52PedidosAdicionales.emptyRecords,
		loadComplete : function(data) {
			gridP52PedidosAdicionales.actualPage = data.page;
			gridP52PedidosAdicionales.localData = data;
			gridP52PedidosAdicionales.sortIndex = null;
			gridP52PedidosAdicionales.sortOrder = null;
			//reloadDataP52PedidosAdicionales(gridP52PedidosAdicionales);
			$("#cb_" + gridP52PedidosAdicionales.name).attr("style",
			"display:none");
		},
		onPaging : function(postdata) {			
			alreadySorted = false;
			gridP52PedidosAdicionales.sortIndex = null;
			gridP52PedidosAdicionales.sortOrder = null;
			reloadDataP52NuevoPedidoReferencia();
			return 'stop';
		},
		beforeSelectRow: function (rowid, e) {
			if(p52selectedRow){
				return false;
			}else{
				var $myGrid = $(this),
				i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),
				cm = $myGrid.jqGrid('getGridParam', 'colModel');
				if (cm[i].name === 'cb'){
					return true;
				} else {
					p52selectedRow = true;
					var rowData = jQuery(gridP52PedidosAdicionales.nameJQuery).getRowData(rowid);
					var codCentro = rowData['codCentro'];
					var codArt = $("#"+rowid+"_codArt").val();
					nuevoPedidoRef = new PedidoAdicionalCompleto(null, codCentro, codArt);
					nuevoPedidoRef.calcularFechaIniFin = '';
					var uuid = $("#"+rowid+"_uuid").val();
					nuevoPedidoRef.uuid = uuid;
					obtainReferencia(nuevoPedidoRef);
					if(rowData['tipoPedido'] != clientOrderGrid){
						$("#p52_fld_referencia").focus();
						$("#p52_fld_referencia").select();
					}
					return false;
				}
			}
		},
		onSortCol : function (index, columnIndex, sortOrder){
			gridP52PedidosAdicionales.sortIndex = index;
			gridP52PedidosAdicionales.sortOrder = sortOrder;
			//reloadDataP52PedidosAdicionales(gridP52PedidosAdicionales);
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

	jQuery(gridP52PedidosAdicionales.nameJQuery).jqGrid('navGrid',gridP52PedidosAdicionales.pagerNameJQuery,{
		add:false,edit:false,del:false,search:false,refresh:false}
	); 

	jQuery(gridP52PedidosAdicionales.nameJQuery).jqGrid('navButtonAdd',gridP52PedidosAdicionales.pagerNameJQuery,{ 
		caption: tableFilter, title: "Reordenar Columnas", 
		onClickButton : function (){ 
			jQuery(gridP52PedidosAdicionales.nameJQuery).jqGrid('columnChooser',
					{
				"done": function(perm) {
					if (perm) {
						var autowidth = true;
						if (gridP52PedidosAdicionales.modificado == true){
							autowidth = false;
							gridP52PedidosAdicionales.myColumnsState =  gridP52PedidosAdicionales.restoreColumnState(gridP43PedidoAdicionalMO.cm);
							gridP52PedidosAdicionales.isColState = typeof (gridP52PedidosAdicionales.myColumnsState) !== 'undefined' && gridP43PedidoAdicionalMO.myColumnsState !== null;
							this.jqGrid("remapColumns", perm, true);
							var colModel =jQuery(gridP52PedidosAdicionales.nameJQuery).jqGrid('getGridParam', 'colModel'); 
							var l = colModel.length;
							var colItem; 
							var cmName;
							var colStates = gridP52PedidosAdicionales.myColumnsState.colStates;
							var cIndex = 2;
							for (i = 0; i < l; i++) {
								colItem = colModel[i];
								cmName = colItem.name;
								if (colItem.hidden !== true && cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {

									jQuery(gridP52PedidosAdicionales.nameJQuery).jqGrid('setColProp',cmName,{width:colStates[cmName].width});
									var cad =gridP52PedidosAdicionales.nameJQuery+'_'+cmName;
									var ancho = "'"+colStates[cmName].width+"px'";
									var cell = jQuery('table'+gridP52PedidosAdicionales.nameJQuery+' tr.jqgfirstrow td:nth-child(' + (i+1) + ')');
									cell.css("width", colStates[cmName].width + "px");

									jQuery(cad).css("width", colStates[cmName].width + "px");

								}
							}

						} else {
							this.jqGrid("remapColumns", perm, true);
						}
						gridP52PedidosAdicionales.saveColumnState.call(this, perm);
						jQuery(gridP52PedidosAdicionales.nameJQuery).jqGrid('setGridWidth', $("#p52_bloque3AreaDatosPedidosAdicionales").width(), autowidth);
					}
				}
					}		
			); } });
}

function p52imageFormatEstado(cellValue, opts, rowObject) {
	var imagen = "";
	if (cellValue == "0")	    
		imagen = "<div align='center'><img src='./misumi/images/floppy.png'/></div>";
	else if (cellValue == "1")		    
		imagen = "<div align='center'><img src='./misumi/images/modificado.png'/></div>";
	else if (cellValue == "2"){
		descError = rowObject['descError'];
		imagen = "<div align='center'><img src='./misumi/images/dialog-error-24.png'  title='"+descError+"'/></div>";
	}
	imagen +="<input type='hidden' id='"+opts.rowId+"_uuid' value='"+rowObject['uuid']+"'>";
	imagen +="<input type='hidden' id='"+opts.rowId+"_codArt' value='"+rowObject['codArt']+"'>";
	imagen +="<input type='hidden' id='"+opts.rowId+"_descArt' value='"+rowObject['descArt']+"'>";
	return imagen;
}

function p52FormateoDate(cellValue, opts, rowObject) {

	var fechaFormateada = "";

	if (null != cellValue){
		fechaFormateada = $.datepicker.formatDate("D dd-M", new Date(cellValue),{
			dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
			dayNames: $.datepicker.regional[ "es" ].dayNames,
			monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
			monthNames: $.datepicker.regional[ "es" ].monthNames
		});
	}
	return(fechaFormateada);

}

function p52FormateoCantidadEncargo(cellValue, opts, rowObject) {

	var tipoPedido = rowObject['tipoPedido'];
	var cantidad = null;
	if (tipoPedido == "3"){
		cantidad = rowObject['datosPedidoUnidadesPedir'];
	} else {
		cantidad = rowObject['cantidad1'];
	}


	if (null != cantidad && cantidad != '' )
	{
		//Actualizamos el formateo de los campos para que no tenga decimales
		return $.formatNumber(cantidad,{format:"0.00",locale:"es"});
	}
	else
	{
		return '';
	}

}

function p52FormateoTipoPedido(cellValue, opts, rowObject) {

	var tipoPedido;

	if (cellValue == "1"){
		tipoPedido = ordersGrid;

	} else if(cellValue == "2"){
		tipoPedido = additionalAssemblyGrid;
	} else {
		tipoPedido = clientOrderGrid;
	}
	return(tipoPedido);

}

function p52FormateoCodRef(cellValue, opts, rData) {

	var codReferencia = cellValue;
	if(rData['esGenerica'] && rData["tipoPedido"] == "3"){
		codReferencia = "--------";
	}
	return codReferencia;
}

function p52FormateoImplant(cellValue, opts, rData) {

	if (null != cellValue && cellValue != '' )
	{
		//Actualizamos el formateo de los campos para que no tenga decimales
		return $.formatNumber(cellValue,{format:"0",locale:"es"});
	}
	else
	{
		return '';
	}
}

function p52FormateoCant(cellValue, opts, rData) {


	if (null != cellValue && cellValue != '' )
	{
		//Actualizamos el formateo de los campos para que no tenga decimales
		return $.formatNumber(cellValue,{format:"0.00",locale:"es"});
	}
	else
	{
		return '';
	}
}

/*Clase de constantes para el GRID de Pedido adicional*/
function GridP52PedidosAdicionales (locale){
	// Atributos
	this.name = "gridP52PedidosAdicionales"; 
	this.nameJQuery = "#gridP52PedidosAdicionales"; 
	this.i18nJSON = './misumi/resources/p52NuevoPedidoREF/p52pedidosAdicionales_' + locale + '.json';
	this.colNames = null;
	this.cm = [
	           {
	        	   "name"  : "codCentro",
	        	   "index" : "codCentro",
	        	   "hidden" : true
	           },{
	        	   "name"  : "tratamiento",
	        	   "index" : "tratamiento",
	        	   "hidden" : true
	           },{
	        	   "name"  : "codArtGrid",
	        	   "index" : "codArtGrid",
	        	   "formatter" : p52FormateoCodRef,
	        	   "width" : 100
	           },{
	        	   "name"  : "descArtGrid",
	        	   "index" : "descArtGrid",
	        	   "width" : 240	
	           },{
	        	   "name"  : "fechaIni",
	        	   "index" : "fechaIni", 
	        	   "formatter" : p52FormateoDate,
	        	   "width" : 75
	           },{
	        	   "name"  : "fechaFin",
	        	   "index" : "fechaFin", 
	        	   "formatter" : p52FormateoDate,
	        	   "width" : 75
	           },{
	        	   "name"  : "capacidadMaxima",
	        	   "index" : "capacidadMaxima", 
	        	   "formatter":p52FormateoImplant,
	        	   "width" : 90
	           },{
	        	   "name"  : "cantidadMaxima",
	        	   "index" : "cantidadMaxima", 
	        	   "hidden" : true
	           },{
	        	   "name"  : "implantacionMinima",
	        	   "index" : "implantacionMinima", 
	        	   "formatter":p52FormateoImplant,
	        	   "width" : 90
	           },{
	        	   "name"  : "cantidad1",
	        	   "index" : "cantidad1", 
	        	   "formatter":p52FormateoCantidadEncargo,
	        	   "width" : 80
	           },{
	        	   "name"  : "cantidad2",
	        	   "index" : "cantidad2", 
	        	   "formatter":p52FormateoCant,
	        	   "width" : 80
	           },{
	        	   "name"  : "cantidad3",
	        	   "index" : "cantidad3", 
	        	   "formatter":p52FormateoCant,
	        	   "width" : 80
	           },{
	        	   "name"  : "uniCajas",
	        	   "index" : "uniCajas", 
	        	   "formatter":p52FormateoUnidadesCaja,
	        	   "width" : 40
	           },{
	        	   "name"  : "tipoPedido",
	        	   "index" : "tipoPedido", 
	        	   "formatter" : p52FormateoTipoPedido,
	        	   "width" : 70
	           },{
	        	   "name"  : "estado",
	        	   "index" : "estado", 
	        	   "formatter" : p52imageFormatEstado,
	        	   "width" : 70
	           },{
	        	   "name"  : "msgUNIDADESCAJAS",
	        	   "index" : "msgUNIDADESCAJAS",
	        	   "hidden" : true
	           }
	           ];
	this.sortIndex = "referencia";
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP52PedidosAdicionales"; 
	this.pagerNameJQuery = "#pagerP52PedidosAdicionales";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;
	this.myColumnStateName = 'gridP52PedidosAdicionales.colState';
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
		return $(this.nameJQuery).getGridParam('rowNum');
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
		var colModel = jQuery(gridP52PedidosAdicionales.nameJQuery).jqGrid(
				'getGridParam', 'colModel');
		// if (colModel == null)
		// colModel = grid.cm;
		var i;
		var l = colModel.length;
		var colItem;
		var cmName;
		var postData = jQuery(gridP52PedidosAdicionales.nameJQuery).jqGrid(
				'getGridParam', 'postData');
		var columnsState = {
				search : jQuery(gridP52PedidosAdicionales.nameJQuery).jqGrid(
						'getGridParam', 'search'),
						page : jQuery(gridP52PedidosAdicionales.nameJQuery).jqGrid(
								'getGridParam', 'page'),
								sortname : jQuery(gridP52PedidosAdicionales.nameJQuery).jqGrid(
										'getGridParam', 'sortname'),
										sortorder : jQuery(gridP52PedidosAdicionales.nameJQuery).jqGrid(
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
					gridP52PedidosAdicionales.myColumnStateName, JSON
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

function p52SetHeadersTitles(data){

	var colModel = $(gridP52PedidosAdicionales.nameJQuery).jqGrid("getGridParam", "colModel");
	$.each(colModel, function(i) {
		if (colModel[i].name!="rn"){
			$("#jqgh_gridP52PedidosAdicionales_"+colModel[i].name).attr("title", eval("data."+colModel[i].name+"Title"));
		}
	});
}


function getReferencia(){

	var nuevoPedido = new PedidoAdicionalCompleto($("#p52_cmb_tipoPedidoAdicional").combobox("getValue"));
	//nuevoPedido.esGenerica
	if (nuevoPedidoRef && nuevoPedidoRef.uuid){

		p52Uuid = nuevoPedidoRef.uuid;
		p52Estado = nuevoPedidoRef.estado;
		nuevoPedido.uuid = p52Uuid;
	} else {
		p52Uuid = null;
		p52Estado = null;
	}
	nuevoPedido.esGenerica = p52Generico;
	p52Generico = false;
	nuevoPedido.calcularFechaIniFin = '';
	if (p64CodReferencia) {
		nuevoPedido.codArt = p64CodReferencia;
		nuevoPedido.descArt = p64DescReferencia;
		p64CodReferencia = null;
	} else {
		nuevoPedido.codArt = $("#p52_fld_referencia").val();
	}
	nuevoPedido.codCentro = $("#centerId").val();
	nuevoPedido.fechaIni = $("#p52_fechaInicioDatePicker").datepicker("getDate");
	nuevoPedido.fechaFin = $("#p52_fechaFinDatePicker").datepicker("getDate");


	var objJson = $.toJSON(nuevoPedido.preparePedidoAdicionalToJsonObject());
	$.ajax({
		type : 'POST',
		url : './nuevoPedidoAdicionalReferencia/loadReferencia.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		//async: false,
		success : function(data) {
			//console.log("getReferencia()");
			//console.log(data);
			$.datepicker.setDefaults($.datepicker.regional['esDiasServicio']);
			//el combo de centro se muestra con valor nulo para que el usuario seleccione un centro
			//control de limpiado del valor del centro cuando se pincha sobre el campo
			if (data != null){

				$("#p52_cmb_numeroOferta").combobox(null);
				//$("select#p52_cmb_numeroOferta").html("<option value=null selected='selected'>"+''+"</option>");
				$("#p52_cmb_numeroOferta").combobox('autocomplete',"");
				$("#p52_cmb_numeroOferta").combobox('comboautocomplete',null);
				if (data.error != null){
					$("#p03_btn_aceptar").unbind("click");
					$("#p03_btn_aceptar").bind("click", function(e) {
						//$("#p52_fld_referencia").focus();
						//$("#p52_fld_referencia").select();
					});				
					$("#p52_linkAyuda").hide();
					createAlert(data.error, "ERROR");
					$("#p03_btn_aceptar").focus();
				} else if (data.errorPedirSIA != null){
					//Si la tabla V_REFERENCIA_PEDIR_SIA devuelve nulo, mostrar error y no dejar insertar.
					//Ponemos la denominación
					$("#p52_fld_denominacion").val(data.descArt);
					
					$("#p03_btn_aceptar").unbind("click");
					$("#p03_btn_aceptar").bind("click", function(e) {
						$("#p52_fld_referencia").focus();
						$("#p52_fld_referencia").select();
						
						//Al pulsar aceptar se resetea la descripción y la denominación.
						$("#p52_fld_referencia").val("");
						$("#p52_fld_denominacion").val("");
					});				
					createAlert(data.errorPedirSIA, "ERROR");
					$("#p03_btn_aceptar").focus();
				}else {	
					//Quitamos posibles eventos del aceptar de búsquedas anteriores.
					$("#p03_btn_aceptar").unbind("click");
					
					mostrarCajasYExcluir = data.showExcluirAndCajas;
					nuevoPedidoRef = new PedidoAdicionalCompleto($("#p52_cmb_tipoPedidoAdicional").combobox("getValue"));
					nuevoPedidoRef.uuid = p52Uuid;
					nuevoPedidoRef.estado = p52Estado;
					nuevoPedidoRef.fechaBloqueoEncargo = data.fechaBloqueoEncargo;
					nuevoPedidoRef.fechaBloqueoEncargoPilada = data.fechaBloqueoEncargoPilada;
					nuevoPedidoRef.errorValidar = data.errorValidar;
					nuevoPedidoRef.errorWS = data.errorWS;
					nuevoPedidoRef.flgAvisoEncargoLote = data.flgAvisoEncargoLote; //Pet. 49333. Flag para aviso de lotes.
					nuevoPedidoRef.flgEspec = data.flgEspec;
					//console.log("getReferencia - formatoArticulo = "+data.formatoArticulo);
					nuevoPedidoRef.formatoArticulo = data.formatoArticulo;
					nuevoPedidoRef.flgGestionaSIA = data.flgGestionaSIA;
					nuevoPedidoRef.tratamientoVegalsa = data.tratamientoVegalsa; // MISUMI-352.
					
					// Inicio MISUMI-409.
					nuevoPedidoRef.diasMaximos = data.diasMaximos;
					nuevoPedidoRef.cantidadMaxima = data.cantidadMaxima;
					// FIN MISUMI-409.
					
					//misumi-270
					nuevoPedidoRef.ofertaErr = data.ofertaErr;
					
					if (data.mantieneReferenciaTextil == false){
						$("#p52_AreaTextilLote").show();
					}
					// MISUMI-437.
					nuevoPedidoRef.tratamiento = data.tratamiento; 
					if (nuevoPedidoRef.tipoPedido == "1"){
						$("#p52_tratamiento").show();
						if(data.area==1){
							$("#p52_cmb_tratamiento").combobox('autocomplete',"Sustituir");
							$("#p52_cmb_tratamiento").combobox('comboautocomplete',"Sustituir      - Machaca la cantidad transmitida por las del encargo");
							$("#p52_cmb_tratamiento").val("S");
						}else{
							$("#p52_cmb_tratamiento").combobox('autocomplete',"Añadir");
							$("#p52_cmb_tratamiento").combobox('comboautocomplete',"Añadir         - Añade cantidad a transmisión");
							$("#p52_cmb_tratamiento").val("A");
						}
						
					}else{
						$("#p52_tratamiento").hide();
						$("#p52_cmb_tratamiento").combobox('autocomplete',null);
						$("#p52_cmb_tratamiento").combobox('comboautocomplete',null);
						$("#p52_cmb_tratamiento").val(null);
					}
					if((('S' == data.fechaBloqueoEncargo || 'S' == data.fechaBloqueoEncargoPilada || data.errorWS) && nuevoPedidoRef.tipoPedido == "2") ||
							(data.errorValidar && nuevoPedidoRef.tipoPedido == "3") || (data.errorWS && nuevoPedidoRef.tipoPedido == "1")	){
						$("#p03_btn_aceptar").unbind("click");
						$("#p03_btn_aceptar").bind("click", function(e) {
							nuevoPedidoRef.calcularFechaIniFin = '';
							nuevoPedidoRef.codArt = data.codArt;
							nuevoPedidoRef.codArtEroski = data.codArtEroski;
							nuevoPedidoRef.descArt = data.descArt;
							nuevoPedidoRef.descArtEroski = data.descArtEroski;
							nuevoPedidoRef.area = data.area;
							nuevoPedidoRef.seccion = data.seccion;
							nuevoPedidoRef.categoria = data.categoria;
							nuevoPedidoRef.subcategoria = data.subcategoria;
							nuevoPedidoRef.codCentro = data.codCentro;
							options = "";
							nuevoPedidoRef.frescoPuro = data.frescoPuro;
							nuevoPedidoRef.bloqueoEncargoCliente = data.bloqueoEncargoCliente;
							nuevoPedidoRef.fechasVenta = data.fechasVenta;
							nuevoPedidoRef.nombreCliente = data.nombreCliente;
							nuevoPedidoRef.apellidoCliente = data.apellidoCliente;
							nuevoPedidoRef.telefonoCliente = data.telefonoCliente;
							nuevoPedidoRef.contactoCentro = data.contactoCentro;
							nuevoPedidoRef.primeraFechaEntrega = getEncargoClientePrimeraFechaEntrega(data.primeraFechaEntrega, data.fechasVenta);
							if (data.listaOfertas != null ){
								for (i = 0; i < data.listaOfertas.length; i++){
									options = options + "<option value='" + data.listaOfertas[i] + "' >" + data.listaOfertas[i] + "</option>";
								}
							}
							options = options + "<option value='0'>"+withoutOffer+"</option>";
							$("select#p52_cmb_numeroOferta").html(options);
							var of = $("select#p52_cmb_numeroOferta").val();
							nuevoPedidoRef.oferta = data.oferta;
							nuevoPedidoRef.tipoOferta = data.tipoOferta;
							nuevoPedidoRef.referenciaNueva = data.referenciaNueva;
							if (data.strFechaIniWS){
								nuevoPedidoRef.fechaIniWS = $.datepicker.parseDate( "ddmmyy", data.strFechaIniWS );
							}
							nuevoPedidoRef.conCajas = data.conCajas;
							nuevoPedidoRef.uniCajas = data.uniCajas; 
							if (data.strFechaIni){
								nuevoPedidoRef.fechaIni = $.datepicker.parseDate( "ddmmyy", data.strFechaIni );
							}
							if (data.strFechaFin){
								nuevoPedidoRef.fechaFin = $.datepicker.parseDate( "ddmmyy", data.strFechaFin );
							}
							volcadoFechas(data);

							nuevoPedidoRef.tipoAprovisionamiento = data.tipoAprovisionamiento;
							//nuevoPedidoRef.capacidadMaxima = data.capacidadMaxima;
							//nuevoPedidoRef.implantacionMinima = data.implantacionMinima;
							nuevoPedidoRef.bloqueoEncargo = data.bloqueoEncargo;
							nuevoPedidoRef.bloqueoPilada = data.bloqueoPilada;
							nuevoPedidoRef.referenciaUnitaria = data.referenciaUnitaria;
							nuevoPedidoRef.stock = data.stock;
							nuevoPedidoRef.esStock = data.esStock;
							nuevoPedidoRef.stockPlataforma = data.stockPlataforma;
							nuevoPedidoRef.referenciaFFPP = data.referenciaFFPP;
							nuevoPedidoRef.fechaNoDisponible = data.fechaNoDisponible;
							nuevoPedidoRef.esGenerica = data.esGenerica;
							origen = "ref";

							nuevoPedidoRef.catalogo = data.catalogo;
							
							// Inicio MISUMI-409.
							nuevoPedidoRef.diasMaximos = data.diasMaximos;
							nuevoPedidoRef.cantidadMaxima = data.cantidadMaxima;
							// FIN MISUMI-409.
							
							nuevoPedidoRef.msgUNIDADESCAJAS = data.msgUNIDADESCAJAS;

							//Se actualizan los valores de los calendarios
							if (data.tipoPedido =="3"){//Encargos de cliente
								//if (nuevoPedidoRef.primeraFechaEntrega){
								//	p52CargarDiasServicioCalendarios();
								//	$("#p52_fechaEntregaEncCliDatePicker").datepicker("setDate", nuevoPedidoRef.primeraFechaEntrega);
								//	$("#p52_fechaEntregaEncCliDatePicker").datepicker("refresh");
								//}
								$("#p52_fld_cantidad_enc_cli").focus();
								$("#p52_fld_cantidad_enc_cli").select();

							}else{
								if (nuevoPedidoRef.fechaIni){
									p52CargarDiasServicioCalendarios();
									if (('S' != nuevoPedidoRef.fechaBloqueoEncargo && 'S' != nuevoPedidoRef.fechaBloqueoEncargoPilada && data.tipoPedido == "2" ) ||
											data.tipoPedido == "1"){
										$("#p52_fechaInicioDatePicker").datepicker("setDate", nuevoPedidoRef.fechaIni);
										$("#p52_fechaInicioDatePicker").datepicker("refresh");
									} else {
										$("#p52_fechaInicioDatePicker").datepicker("setDate", null);
										$('#p52_fechaInicioDatePicker .ui-datepicker-current-day').removeClass('ui-datepicker-current-day');
										nuevoPedidoRef.fechaIni = new Date();
									}
								}

								if (data.tipoPedido =="2" && !data.frescoPuro && ((data.catalogo == "B") || data.bloqueoPilada)){
									//Si es Montaje Adicional de alimentacion y (es baja de catalogo o si tiene un bloqueo de piladas)
									$("#p03_btn_aceptar").unbind("click");
									$("#p03_btn_aceptar").bind("click", function(e) {
										if (noBloqueo){
											if (!$("#p52_div_cantidad1").is(':hidden')){
												$("#p52_fld_cantidad1").focus();
												$("#p52_fld_cantidad1").select();
												//Guardamos en una variable oculta, el campo que tiene el foco para poder posicionarnos una 
												//vez volvamos del PopUp de ayuda de textil
												$("#p52_campo_foco_vueltaPopUpTextil").val("p52_fld_cantidad1");
											} else if (!$("#p52_fld_capMax").is(':hidden')) {
												$("#p52_fld_capMax").focus();
												$("#p52_fld_capMax").select();
												//Guardamos en una variable oculta, el campo que tiene el foco para poder posicionarnos una 
												//vez volvamos del PopUp de ayuda de textil
												$("#p52_campo_foco_vueltaPopUpTextil").val("p52_fld_capMax");
											} else {
												$("#p52_fld_cantidad_enc_cli").focus();
												$("#p52_fld_cantidad_enc_cli").select();
											}
										}
									});
									createAlert(replaceSpecialCharacters(referenceLocked), "ERROR");
									$("#p03_btn_aceptar").focus();

								} else {
									
									if (data.catalogo == "B"){ //(Es un encargo o montaje adicional para frescos ) y es baja de catalogo
										$("#p03_btn_aceptar").unbind("click");
										$("#p03_btn_aceptar").bind("click", function(e) {
											if (noBloqueo){
												if (!$("#p52_div_cantidad1").is(':hidden')){
													$("#p52_fld_cantidad1").focus();
													$("#p52_fld_cantidad1").select();
													//Guardamos en una variable oculta, el campo que tiene el foco para poder posicionarnos una 
													//vez volvamos del PopUp de ayuda de textil
													$("#p52_campo_foco_vueltaPopUpTextil").val("p52_fld_cantidad1");
												} else if (!$("#p52_fld_capMax").is(':hidden')) {
													$("#p52_fld_capMax").focus();
													$("#p52_fld_capMax").select();
													//Guardamos en una variable oculta, el campo que tiene el foco para poder posicionarnos una 
													//vez volvamos del PopUp de ayuda de textil
													$("#p52_campo_foco_vueltaPopUpTextil").val("p52_fld_capMax");
												} else {
													$("#p52_fld_cantidad_enc_cli").focus();
													$("#p52_fld_cantidad_enc_cli").select();
												}
											}
										});
										createAlert(replaceSpecialCharacters(bajaCatalogoError), "ERROR");
										$("#p03_btn_aceptar").focus();
									} else {
										if (noBloqueo){
											if (!$("#p52_div_cantidad1").is(':hidden')){
												$("#p52_fld_cantidad1").focus();
												$("#p52_fld_cantidad1").select();
												//Guardamos en una variable oculta, el campo que tiene el foco para poder posicionarnos una 
												//vez volvamos del PopUp de ayuda de textil
												$("#p52_campo_foco_vueltaPopUpTextil").val("p52_fld_cantidad1");
											} else if (!$("#p52_fld_capMax").is(':hidden')) {
												$("#p52_fld_capMax").focus();
												$("#p52_fld_capMax").select();
												//Guardamos en una variable oculta, el campo que tiene el foco para poder posicionarnos una 
												//vez volvamos del PopUp de ayuda de textil
												$("#p52_campo_foco_vueltaPopUpTextil").val("p52_fld_capMax");
											} else {
												$("#p52_fld_cantidad_enc_cli").focus();
												$("#p52_fld_cantidad_enc_cli").select();
											}
										}
									}
								}
							}
							populatePedidoAdicional();
						});

						// MISUMI-324: MISUMI-JAVA Encargos Referencias DESCENTRALIZADAS bloquear
						if (nuevoPedidoRef.tipoAprovisionamiento == 'D' && data.tipoPedido == 1 
							&& !mostrarCajasYExcluir){
							createAlert(replaceSpecialCharacters(referenceNotManaged), "ERROR");
							return;
						}
						if (nuevoPedidoRef.tipoPedido != "3"){
							if('S' == data.fechaBloqueoEncargo && 'S' == data.fechaBloqueoEncargoPilada){
								createAlert(fechaSelBloqueosEncargosMontajes, "ERROR");
							}else if('S' == data.fechaBloqueoEncargo ){
								createAlert(fechaSelBloqueosEncargos, "ERROR");	
							}else if('S' == data.fechaBloqueoEncargoPilada){
								createAlert(fechaSelBloqueosMontajes, "ERROR");	
							}else {
								createAlert(data.errorWS, "ERROR");
							}
						} else {
							createAlert(nuevoPedidoRef.errorValidar, "ERROR");
						}


						$("#p03_btn_aceptar").focus();

					}else{
						nuevoPedidoRef = new PedidoAdicionalCompleto($("#p52_cmb_tipoPedidoAdicional").combobox("getValue"));
						nuevoPedidoRef.uuid = p52Uuid
						nuevoPedidoRef.estado = p52Estado;
						nuevoPedidoRef.fechaBloqueoEncargo = data.fechaBloqueoEncargo;
						nuevoPedidoRef.fechaBloqueoEncargoPilada = data.fechaBloqueoEncargoPilada;
						nuevoPedidoRef.errorValidar = data.errorValidar;
						nuevoPedidoRef.errorWS = data.errorWS;
						nuevoPedidoRef.flgAvisoEncargoLote = data.flgAvisoEncargoLote; //Pet. 49333. Flag para aviso de lotes.
						nuevoPedidoRef.calcularFechaIniFin = '';
						nuevoPedidoRef.codArt = data.codArt;
						nuevoPedidoRef.codArtEroski = data.codArtEroski;
						nuevoPedidoRef.descArt = data.descArt;
						nuevoPedidoRef.descArtEroski = data.descArtEroski;
						nuevoPedidoRef.area = data.area;
						nuevoPedidoRef.seccion = data.seccion;
						nuevoPedidoRef.categoria = data.categoria;
						nuevoPedidoRef.subcategoria = data.subcategoria;
						nuevoPedidoRef.codCentro = data.codCentro;
						options = "";
						nuevoPedidoRef.frescoPuro = data.frescoPuro;
						nuevoPedidoRef.bloqueoEncargoCliente = data.bloqueoEncargoCliente;
						nuevoPedidoRef.flgEspec = data.flgEspec;
						nuevoPedidoRef.flgGestionaSIA = data.flgGestionaSIA;
						nuevoPedidoRef.tratamientoVegalsa = data.tratamientoVegalsa; // MISUMI-352.
						nuevoPedidoRef.primeraFechaEntrega = getEncargoClientePrimeraFechaEntrega(data.primeraFechaEntrega, data.fechasVenta);
						nuevoPedidoRef.nombreCliente = data.nombreCliente;
						nuevoPedidoRef.apellidoCliente = data.apellidoCliente;
						nuevoPedidoRef.telefonoCliente = data.telefonoCliente;
						nuevoPedidoRef.contactoCentro = data.contactoCentro;
						nuevoPedidoRef.errorValidar = data.errorValidar;
						
						//misumi-270
						nuevoPedidoRef.ofertaErr = data.ofertaErr
						
						if (data.listaOfertas != null ){
							for (i = 0; i < data.listaOfertas.length; i++){
								options = options + "<option value='" + data.listaOfertas[i] + "' >" + data.listaOfertas[i] + "</option>";
							}
						}
						options = options + "<option value='0'>"+withoutOffer+"</option>";
						$("select#p52_cmb_numeroOferta").html(options);
						var of = $("select#p52_cmb_numeroOferta").val();
						nuevoPedidoRef.oferta = data.oferta;
						nuevoPedidoRef.tipoOferta = data.tipoOferta;
						nuevoPedidoRef.referenciaNueva = data.referenciaNueva;
						if (data.strFechaIniWS){
							nuevoPedidoRef.fechaIniWS = $.datepicker.parseDate( "ddmmyy", data.strFechaIniWS );
						}
						nuevoPedidoRef.conCajas = data.conCajas;
						nuevoPedidoRef.uniCajas = data.uniCajas; 
						if (data.strFechaIni){
							nuevoPedidoRef.fechaIni = $.datepicker.parseDate( "ddmmyy", data.strFechaIni );
						}
						if (data.strFechaFin){
							nuevoPedidoRef.fechaFin = $.datepicker.parseDate( "ddmmyy", data.strFechaFin );
						}
						volcadoFechas(data);

						nuevoPedidoRef.tipoAprovisionamiento = data.tipoAprovisionamiento;
						
						//nuevoPedidoRef.capacidadMaxima = data.capacidadMaxima;
						//nuevoPedidoRef.implantacionMinima = data.implantacionMinima;
						nuevoPedidoRef.bloqueoEncargo = data.bloqueoEncargo;
						nuevoPedidoRef.bloqueoPilada = data.bloqueoPilada;
						nuevoPedidoRef.referenciaUnitaria = data.referenciaUnitaria;
						nuevoPedidoRef.stock = data.stock;
						nuevoPedidoRef.esStock = data.esStock;
						nuevoPedidoRef.stockPlataforma = data.stockPlataforma;
						nuevoPedidoRef.referenciaFFPP = data.referenciaFFPP;
						nuevoPedidoRef.fechaNoDisponible = data.fechaNoDisponible;
						nuevoPedidoRef.esGenerica = data.esGenerica;
						origen = "ref";

						nuevoPedidoRef.catalogo = data.catalogo;

						nuevoPedidoRef.flgAvisoEncargoLote = data.flgAvisoEncargoLote; //Pet. 49333. Flag para aviso de lotes.


						nuevoPedidoRef.formatoArticulo = data.formatoArticulo;
						
						//Se actualizan los valores de los calendarios


						//Campo para el control de mostrar las leyendas de bloqueos
						nuevoPedidoRef.mostrarLeyendaBloqueo = data.mostrarLeyendaBloqueo;

						// Inicio MISUMI-409.
						nuevoPedidoRef.diasMaximos = data.diasMaximos;
						nuevoPedidoRef.cantidadMaxima = data.cantidadMaxima;
						// FIN MISUMI-409.
						
						nuevoPedidoRef.msgUNIDADESCAJAS = data.msgUNIDADESCAJAS;
						
						populatePedidoAdicional(data.msgUNIDADESCAJAS);

						// MISUMI-324: MISUMI-JAVA Encargos Referencias DESCENTRALIZADAS bloquear
						if (nuevoPedidoRef.tipoAprovisionamiento == 'D' && data.tipoPedido == 1 
							&& !mostrarCajasYExcluir){
							createAlert(replaceSpecialCharacters(referenceNotManaged), "ERROR");
							return;
						}
						if (data.tipoPedido =="3"){//Encargos de cliente
							p52CargarDiasServicioCalendarios();
							$("#p52_fld_cantidad_enc_cli").focus();
							$("#p52_fld_cantidad_enc_cli").select();
							reloadDataP65(nuevoPedidoRef);
						}else{
							if (nuevoPedidoRef.fechaIni){
								if (('S' != nuevoPedidoRef.fechaBloqueoEncargo && 'S' != nuevoPedidoRef.fechaBloqueoEncargoPilada && data.tipoPedido == "2" ) ||
										data.tipoPedido == "1"){
									$("#p52_fechaInicioDatePicker").datepicker("setDate", nuevoPedidoRef.fechaIni);

									// Inicio MISUMI-409.
									if (nuevoPedidoRef.diasMaximos!=null && nuevoPedidoRef.diasMaximos>=0){
										$("#p52_fechaInicioDatePicker").datepicker("option", "maxDate", nuevoPedidoRef.diasMaximos);
									}
									// FIN MISUMI-409.
									$("#p52_fechaInicioDatePicker").datepicker("refresh");
								} else {
									$("#p52_fechaInicioDatePicker").datepicker("setDate", null);
									$('#p52_fechaInicioDatePicker .ui-datepicker-current-day').removeClass('ui-datepicker-current-day');
									nuevoPedidoRef.fechaIni = new Date();
								}

							} else {
								$("#p52_fechaInicioDatePicker").datepicker("setDate", null);
								$('#p52_fechaInicioDatePicker .ui-datepicker-current-day').removeClass('ui-datepicker-current-day');
								nuevoPedidoRef.fechaIni = new Date();
							}
							p52CargarDiasServicioCalendarios();
							if (data.tipoPedido =="2" && !data.frescoPuro && ((data.catalogo == "B") || data.bloqueoPilada)){
								//Si es Montaje Adicional de alimentacion y (es baja de catalogo o si tiene un bloqueo de piladas)
								$("#p03_btn_aceptar").unbind("click");
								$("#p03_btn_aceptar").bind("click", function(e) {
									if (noBloqueo){
										if (!$("#p52_div_cantidad1").is(':hidden')){
											$("#p52_fld_cantidad1").focus();
											$("#p52_fld_cantidad1").select();
											//Guardamos en una variable oculta, el campo que tiene el foco para poder posicionarnos una 
											//vez volvamos del PopUp de ayuda de textil
											$("#p52_campo_foco_vueltaPopUpTextil").val("p52_fld_cantidad1");
										} else if (!$("#p52_fld_capMax").is(':hidden')) {
											$("#p52_fld_capMax").focus();
											$("#p52_fld_capMax").select();
											//Guardamos en una variable oculta, el campo que tiene el foco para poder posicionarnos una 
											//vez volvamos del PopUp de ayuda de textil
											$("#p52_campo_foco_vueltaPopUpTextil").val("p52_fld_capMax");
										} else {
											$("#p52_fld_cantidad_enc_cli").focus();
											$("#p52_fld_cantidad_enc_cli").select();
										}
									}
								});
								createAlert(replaceSpecialCharacters(referenceLocked), "ERROR");
								$("#p03_btn_aceptar").focus();

							}else {
								if (data.catalogo == "B" && noBloqueo){
									$("#p03_btn_aceptar").unbind("click");
									$("#p03_btn_aceptar").bind("click", function(e) {
										if (noBloqueo){
											if (!$("#p52_div_cantidad1").is(':hidden')){
												$("#p52_fld_cantidad1").focus();
												$("#p52_fld_cantidad1").select();
											} else if (!$("#p52_fld_capMax").is(':hidden')) {
												$("#p52_fld_capMax").focus();
												$("#p52_fld_capMax").select();
											} else {
												$("#p52_fld_cantidad_enc_cli").focus();
												$("#p52_fld_cantidad_enc_cli").select();
											}
										}
									});
									createAlert(replaceSpecialCharacters(bajaCatalogoError), "ERROR");
									$("#p03_btn_aceptar").focus();
								}
								else {
									if (noBloqueo){
										if (!$("#p52_div_cantidad1").is(':hidden')){
											if (data.tipoPedido =="1" && data.grupo1 == "3" && data.showPopUpTextil == true) { 
												reloadDataP66(data.codArt);	
											} else {
												$("#p52_fld_cantidad1").focus();
												$("#p52_fld_cantidad1").select();
											}
											//Guardamos en una variable oculta, el campo que tiene el foco para poder posicionarnos una 
											//vez volvamos del PopUp de ayuda de textil

											$("#p52_campo_foco_vueltaPopUpTextil").val("p52_fld_cantidad1");

										} else if (!$("#p52_fld_capMax").is(':hidden')) {
											$("#p52_fld_capMax").focus();
											$("#p52_fld_capMax").select();
											//Guardamos en una variable oculta, el campo que tiene el foco para poder posicionarnos una 
											//vez volvamos del PopUp de ayuda de textil
											$("#p52_campo_foco_vueltaPopUpTextil").val("p52_fld_capMax");
										} else {
											$("#p52_fld_cantidad_enc_cli").focus();
											$("#p52_fld_cantidad_enc_cli").select();
										}
										if (cambioTipoPedido == true){
											$("#p52_campo_foco_vueltaPopUpTextil").val("p52_fld_cantidad1");
										}
									}
								}
							}
							if(nuevoPedidoRef.ofertaErr != null){
								//MISUMI-270 OfertaErr será distinto de null cuando se haya buscado un encargo o encargo cliente. En el controlador se controla que lo calcule solo
								//si es encargo cliente o encargo y además que solo tenga dato si es oferta 4000 con anno 9999 o 2010 y MMC = N
								createAlert(nuevoPedidoRef.ofertaErr, "ERROR");	
							}
						}
						cambioTipoPedido = false;
					}
				}

				//Si es un ENCARGO y es una referencia de TEXTIL, sacar el PopUp de ayuda para textil (lista de referencias con mismo modelo proveedor)

				//Se indica que el dialogo de ayuda no se ha cargado al haber cambiado de referencia. Así obligamos
				//a que busque en el controlador la información de esa referencia.
				dialogoCargado = false;
			}
			
			loadReferenciaActiva = false;
			tabPressed = false;

			//Mostrar u ocultar la foto
			$("#p52_fld_tieneFoto").val(data.tieneFoto);
			loadP52Foto(data.codArtEroski);
			$("#infoRef").show();
		},
		error : function (xhr, status, error){
			loadReferenciaActiva = false;
			handleError(xhr, status, error, locale);				
		}			
	});	

}

function obtainReferencia(nuevoPedidoRefFila){
	loadReferenciaActiva = true;
	var objJson = $.toJSON(nuevoPedidoRef.preparePedidoAdicionalToJsonObject());
	$.ajax({
		type : 'POST',
		url : './nuevoPedidoAdicionalReferencia/obtainReferencia.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		//async: false,
		success : function(data) {	
			nuevoPedidoRef = nuevoPedidoRefFila;
			nuevoPedidoRef.msgUNIDADESCAJAS = data.msgUNIDADESCAJAS;
			nuevoPedidoRef.bloqueoPilada = data.bloqueoPilada;
			nuevoPedidoRef.tipoPedido = data.tipoPedido;
			nuevoPedidoRef.codArt = data.codArtGrid;
			nuevoPedidoRef.codArtEroski = data.codArtEroski;
			nuevoPedidoRef.descArt = data.descArtGrid;
			nuevoPedidoRef.descArtEroski = data.descArtEroski;
			nuevoPedidoRef.codCentro = data.codCentro;
			nuevoPedidoRef.uuid = data.uuid;
			options = "";
			nuevoPedidoRef.frescoPuro = data.frescoPuro;
			nuevoPedidoRef.oferta = data.oferta;
			nuevoPedidoRef.tipoOferta = data.tipoOferta;
			for (i = 0; i < data.listaOfertas.length; i++){
				options = options + "<option value='" + data.listaOfertas[i] + "' >" + data.listaOfertas[i] + "</option>";
			}
			options = options + "<option value='0'>"+withoutOffer+"</option>";
			$("select#p52_cmb_numeroOferta").html(options);
			nuevoPedidoRef.referenciaNueva = data.referenciaNueva;
			if (data.strFechaIniWS){
				nuevoPedidoRef.fechaIniWS = $.datepicker.parseDate( "ddmmyy", data.strFechaIniWS );
			}
			nuevoPedidoRef.conCajas = data.conCajas;
			nuevoPedidoRef.cajas = data.cajas;
			nuevoPedidoRef.excluir = data.excluir;
			nuevoPedidoRef.uniCajas = data.uniCajas; 
			volcadoFechas(data);
			nuevoPedidoRef.cantidad1 = data.cantidad1;
			nuevoPedidoRef.cantidad2 = data.cantidad2;
			nuevoPedidoRef.cantidad3 = data.cantidad3;
			nuevoPedidoRef.capacidadMaxima = data.capacidadMaxima;
			nuevoPedidoRef.implantacionMinima = data.implantacionMinima;
			nuevoPedidoRef.cantidadMaxima = data.cantidadMaxima;
			nuevoPedidoRef.estado = "1";
			nuevoPedidoRef.tipoAprovisionamiento = data.tipoAprovisionamiento;
			nuevoPedidoRef.referenciaUnitaria = data.referenciaUnitaria;
			nuevoPedidoRef.stock = data.stock;
			nuevoPedidoRef.esStock = data.esStock;
			nuevoPedidoRef.stockPlataforma = data.stockPlataforma;
			nuevoPedidoRef.referenciaFFPP = data.referenciaFFPP;

			nuevoPedidoRef.area = data.area;
			nuevoPedidoRef.seccion = data.seccion;
			nuevoPedidoRef.categoria = data.categoria;
			nuevoPedidoRef.subcategoria = data.subcategoria;
			nuevoPedidoRef.bloqueoEncargoCliente = data.bloqueoEncargoCliente;
			nuevoPedidoRef.primeraFechaEntrega = getEncargoClientePrimeraFechaEntrega(data.primeraFechaEntrega, data.fechasVenta);
			nuevoPedidoRef.errorValidar = data.errorValidar;
			nuevoPedidoRef.esGenerica = data.esGenerica;
			nuevoPedidoRef.esEncargoEspecial = data.esEncargoEspecial;
			nuevoPedidoRef.flgEspec = data.flgEspec;
			nuevoPedidoRef.flgGestionaSIA = data.flgGestionaSIA;
			if (data.datosPedidoUnidadesPedir){
				nuevoPedidoRef.datosPedidoUnidadesPedir = $.formatNumber(data.datosPedidoUnidadesPedir,{format:"0.##",locale:"es"});
			}
			nuevoPedidoRef.datosPedidoRadioPeso = data.datosPedidoRadioPeso;
			if (data.datosPedidoPesoDesde){
				nuevoPedidoRef.datosPedidoPesoDesde = $.formatNumber(data.datosPedidoPesoDesde,{format:"0.00",locale:"es"});
			}
			if (data.datosPedidoPesoHasta){
				nuevoPedidoRef.datosPedidoPesoHasta = $.formatNumber(data.datosPedidoPesoHasta,{format:"0.00",locale:"es"});
			}

			nuevoPedidoRef.datosPedidoDescripcion = data.datosPedidoDescripcion;
			nuevoPedidoRef.nombreCliente = data.nombreCliente;
			nuevoPedidoRef.apellidoCliente = data.apellidoCliente;
			nuevoPedidoRef.telefonoCliente = data.telefonoCliente;
			nuevoPedidoRef.contactoCentro = data.contactoCentro;
			if (data.tipoPedido =="1"){
				nuevoPedidoRef.tratamiento = data.tratamiento;
			}
			populatePedidoAdicional(data.msgUNIDADESCAJAS);
			
			if (data.tipoPedido =="3"){//Encargos de cliente


				$("#p52_fld_cantidad_enc_cli").focus();
				$("#p52_fld_cantidad_enc_cli").select();

			}
			loadReferenciaActiva = false;
			//Begin calendario encargos cliente
			//Cuando se recarga un nuevoPedidoRef recalculamos el calendario
			p52CargarDiasServicioCalendariosObtenerRef();
			//End calendario encargos cliente

			p52selectedRow = false;
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});	

}



function p52FormateoCantidades(cantidad) {

	if (cantidad != '')
	{
		//Actualizamos el formateo de los campos a dos decimales
		return $.formatNumber(cantidad,{format:"0.00",locale:"es"});
	}
	else
	{
		return '';
	}
}

function p52FormateoUnidadesCaja(cantidad) {

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

function p52FormateoImplantaciones(cantidad) {

	if (cantidad != '' && cantidad != undefined){
		//Actualizamos el formateo de los campos a dos decimales
		return $.formatNumber(cantidad,{format:"0",locale:"es"});
	}
	else{
		return '';
	}
}

function populatePedidoAdicional(textoUNIDADESCAJAS){
	text = $("#p52_cmb_tipoPedidoAdicional option[value='"+nuevoPedidoRef.tipoPedido+"']").text();
	$("#p52_cmb_tipoPedidoAdicional").combobox('autocomplete',text);
	$("#p52_cmb_tipoPedidoAdicional").combobox('comboautocomplete',null);
	$("#p52_cmb_tipoPedidoAdicional").val(nuevoPedidoRef.tipoPedido);
	$("#p52_ReferenciaLote").css("display", "none");
	$("#p52_ReferenciaHijaDeLote").css("display", "none");
	$("#p52_cmb_cajas").combobox("enable");

	//TODO cargar valor combo tipoPedido
	if (nuevoPedidoRef.esGenerica && nuevoPedidoRef.tipoPedido == "3"){
		$("#p52_fld_referencia").val("--------");
	} else {
		$("#p52_fld_referencia").val(nuevoPedidoRef.codArt);
		$("#p52_fld_referenciaEroski").val(nuevoPedidoRef.codArtEroski);
		$("#p52_fld_denominacionEroski").val(nuevoPedidoRef.descArtEroski);
	}

	$("#p52_fld_aprov").prop("disabled", false);
	$("#p52_fld_aprov").val(nuevoPedidoRef.tipoAprovisionamiento);
	$("#p52_fld_aprov").prop("disabled", true);
	$("#p52_fld_denominacion").prop("disabled", false);
	$("#p52_fld_denominacion").val(nuevoPedidoRef.descArt);
	$("#p52_fld_denominacion").prop("disabled", true);
	noBloqueo = controlBloqueoP52();
	if (null != nuevoPedidoRef.oferta){
		text = $("#p52_cmb_numeroOferta option[value='"+nuevoPedidoRef.oferta+"']").text();
		$("#p52_cmb_numeroOferta").val(nuevoPedidoRef.oferta);
		$("#p52_cmb_numeroOferta").combobox('autocomplete',text);
	}
	if (null != nuevoPedidoRef.uuid){
		$("#uuidCalendario").val(nuevoPedidoRef.uuid);
	}

	//Carga de mensajes de calendarios
	if(nuevoPedidoRef.tipoPedido != null && nuevoPedidoRef.tipoPedido != ""){

		if(nuevoPedidoRef.mostrarLeyendaBloqueo != null && nuevoPedidoRef.mostrarLeyendaBloqueo == "S"){
			//Hay que mostrar las leyendas de bloqueos, hay que realizar el control de que leyenda se muestra.

			if(nuevoPedidoRef.tipoPedido != null && nuevoPedidoRef.tipoPedido == "1"){
				$("#p52_montajeMensajesFechas").hide();
				$("#p52_encargoMensajesFechas").show();
				$("#p52_fechasBloquedasEncargos").hide();
				$("#p52_fechasBloquedasMontajes").hide();
			}else{
				$("#p52_encargoMensajesFechas").hide();
				$("#p52_montajeMensajesFechas").show();
				if (nuevoPedidoRef.frescoPuro){
					$("#p52_montajeFechasBloquedasEncargos").show();
					$("#p52_montajeFechasBloquedasMontajes").show();
				}else{
					$("#p52_montajeFechasBloquedasEncargos").hide();
					$("#p52_montajeFechasBloquedasMontajes").show();
				}
			}

		} else {
			$("#p52_rellenoMensajesFechas").hide();
			$("#p52_montajeMensajesFechas").hide();
			$("#p52_encargoMensajesFechas").hide();
			$("#p52_montajeFechasBloquedasEncargos").hide();
			$("#p52_montajeFechasBloquedasMontajes").hide();
		}

	}else{
		$("#p52_rellenoMensajesFechas").hide();
		$("#p52_montajeMensajesFechas").hide();
		$("#p52_encargoMensajesFechas").hide();
		$("#p52_montajeFechasBloquedasEncargos").hide();
		$("#p52_montajeFechasBloquedasMontajes").hide();
	}

	/****Incidencia 96, al cambiar de oferta se tienen que mostrar siempre las fechas propias de la oferta y no las
	fechas mayores de las ofertas, se cambia tras hablarlo con María.****/

	if (nuevoPedidoRef.tipoPedido == "3"){//Encargo de cliente
		$("#p52_cajas").hide();
		$("#p52_div_stock").hide();
		$("#p52_div_uc").hide();
		$("#p52_div_frescoPuro").hide();
		$("#p52_div_alimentacion").hide();
		$("#p52_AreaBloque2").hide();
		$("#p52_div_legend3").text("");


		//Refresco del calendario para maquetación
		//p52CargaDatosCalendarioEncCli();
		//$("#p52_fechaEntregaEncCliDatePicker").datepicker('refresh');

		$("#p52_AreaBloque2EncargoCliente").css("display", "block");
		$("#p52_AreaBloque3EncargoCliente").css("display", "block");

		if (nuevoPedidoRef.stock == -9999) {
			$("#p52_fld_stock_enc_cli").val("Error");
			$("#p52_fld_stock_enc_cli").addClass("p52_stock_error");
		} else {
			$("#p52_fld_stock_enc_cli").removeClass("p52_stock_error");
			$("#p52_fld_stock_enc_cli").val($.formatNumber(nuevoPedidoRef.stock,{format:"0.##",locale:"es"}));
		}
		var dateFechaEntrega;
		if (nuevoPedidoRef.primeraFechaEntrega){
			dateFechaEntrega = new Date( nuevoPedidoRef.primeraFechaEntrega );
			if(nuevoPedidoRef.estado == "1"){
				dateFechaEntrega = nuevoPedidoRef.fechaIni;
			}
			nuevoPedidoRef.fechaIni = dateFechaEntrega;

			if (nuevoPedidoRef.esEncargoEspecial){
				$("#p52_fechaEntregaEncCliDatePicker").removeClass("encargosEspeciales");
			}else{
				if (new Date(nuevoPedidoRef.primeraFechaEntrega) > nuevoPedidoRef.fechaIni){
					// inst.dpDiv.find('.ui-datepicker-active').css('border', '1px solid #D8000C');
					$("#p52_fechaEntregaEncCliDatePicker").addClass("encargosEspeciales");
				} else {
					$("#p52_fechaEntregaEncCliDatePicker").removeClass("encargosEspeciales");
				} 
			}
			p52CargaDatosCalendarioEncCli();

			var fechaEntrega = $.datepicker.formatDate('D d', dateFechaEntrega,{
				dayNamesShort: $.datepicker.regional[ "es" ].dayNamesMin,
				dayNames: $.datepicker.regional[ "es" ].dayNames,
				monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
				monthNames: $.datepicker.regional[ "es" ].monthNames
			});		 
			$('#p52_lbl_cantidad_enc_cli').text(fechaEntrega);
		} else {
			$('#p52_lbl_cantidad_enc_cli').text("");
		}
		
		if (nuevoPedidoRef.estado == "1"){
			if (nuevoPedidoRef.datosPedidoUnidadesPedir){
				var unidadesPedir = "" + nuevoPedidoRef.datosPedidoUnidadesPedir;
				$("#p52_fld_cantidad_enc_cli").val(unidadesPedir.replace('.',','));
			}
			$("#encargoClienteUnidadesPedirCalendario").val(nuevoPedidoRef.datosPedidoUnidadesPedir);
			$("#encargoClienteEspecialCalendario").val(nuevoPedidoRef.esEncargoEspecial);

		} else{
			$("#encargoClienteUnidadesPedirCalendario").val("");
			$("#encargoClienteEspecialCalendario").val(nuevoPedidoRef.esEncargoEspecial);
		}
		$("#p52_fld_clienteNombre").val(nuevoPedidoRef.nombreCliente);
		$("#p52_fld_clientePrimerApellido").val(nuevoPedidoRef.apellidoCliente);
		$("#p52_fld_clienteTelefono").val(nuevoPedidoRef.telefonoCliente);
		$("#p52_fld_centroContacto").val(nuevoPedidoRef.contactoCentro);

		if (nuevoPedidoRef.esEncargoEspecial){
			$("#p52_fechaEntregaEncCliDatePicker").removeClass("encargosEspeciales");
		}else{
			if (new Date(nuevoPedidoRef.primeraFechaEntrega) > dateFechaEntrega){
				// inst.dpDiv.find('.ui-datepicker-active').css('border', '1px solid #D8000C');
				$("#p52_fechaEntregaEncCliDatePicker").addClass("encargosEspeciales");
			} else {
				$("#p52_fechaEntregaEncCliDatePicker").removeClass("encargosEspeciales");
			} 
		}
		
		$("#p52_fechaEntregaEncCliDatePicker").datepicker("setDate", dateFechaEntrega);
		$("#p52_fechaEntregaEncCliDatePicker").datepicker("refresh");

		comprobarFechaEntregaDatosCliente();
		$("#p52_fld_cantidad_enc_cli").focus();
		$("#p52_fld_cantidad_enc_cli").select();
		
	}else{

		/****Incidencia 96, al cambiar de oferta se tienen que mostrar siempre las fechas propias de la oferta y no las
	    	fechas mayores de las ofertas, se cambia tras hablarlo con María.****/
		//var fechaIni =  $("#p52_fechaInicioDatePicker").datepicker("getDate");
		//var fechaFin =  $("#p52_fechaFinDatePicker").datepicker("getDate");
		p52CargaDatosCalendarios();
		//alert("Fecha Inicio: " + nuevoPedidoRef.fechaIni);
		if (nuevoPedidoRef.fechaIni){

			$("#p52_fechaInicioDatePicker").datepicker( 'option', 'minDate', nuevoPedidoRef.minFechaIni);
			//if (nuevoPedidoRef.estado == "1" || nuevoPedidoRef.fechaIni > fechaIni){

			if ((nuevoPedidoRef.tipoPedido == "1") || ('S' != nuevoPedidoRef.fechaBloqueoEncargo && 'S' != nuevoPedidoRef.fechaBloqueoEncargoPilada && nuevoPedidoRef.tipoPedido == "2")){
				$("#p52_fechaInicioDatePicker").datepicker("setDate", nuevoPedidoRef.fechaIni);
			} else {
				$("#p52_fechaInicioDatePicker").datepicker("setDate", null);
				$('#p52_fechaInicioDatePicker .ui-datepicker-current-day').removeClass('ui-datepicker-current-day');
				nuevoPedidoRef.fechaIni = new Date();
			}

			$("#p52_fechaInicioDatePicker").datepicker("refresh");
			//alert("Calendario FechaIni: " + $("#p52_fechaInicioDatePicker").datepicker("getDate"));
			//} else if (fechaIni != null) {
			//	 $("#p52_fechaInicioDatePicker").datepicker("setDate", fechaIni);
			//}
			$("#p52_fechaFinDatePicker").datepicker( 'option', 'minDate', nuevoPedidoRef.minFechaIni);

			if (nuevoPedidoRef.fechaFin != null){
				// alert("Fecha Fin: " + nuevoPedidoRef.fechaFin);
				if ('S' != nuevoPedidoRef.fechaBloqueoEncargo && 'S' != nuevoPedidoRef.fechaBloqueoEncargoPilada){
					$("#p52_fechaFinDatePicker").datepicker("setDate", nuevoPedidoRef.fechaFin);
					// Inicio MISUMI-409.
					if (nuevoPedidoRef.diasMaximos!=null && nuevoPedidoRef.diasMaximos>=0){
						$("#p52_fechaFinDatePicker").datepicker("option", "maxDate", nuevoPedidoRef.diasMaximos);
					}
					// FIN MISUMI-409.
				} else {
					$("#p52_fechaFinDatePicker").datepicker("setDate", null);
					$('#p52_fechaFinDatePicker .ui-datepicker-current-day').removeClass('ui-datepicker-current-day');
					nuevoPedidoRef.fechaFin = new Date();
				}

				$("#p52_fechaFinDatePicker").datepicker("refresh");
				//alert("Calendario FechaFin: " + $("#p52_fechaFinDatePicker").datepicker("getDate"));
			} else {
				$("#p52_fechaFinDatePicker").datepicker("setDate", null);
				$('#p52_fechaFinDatePicker .ui-datepicker-current-day').removeClass('ui-datepicker-current-day');
				nuevoPedidoRef.fechaFin = new Date();
			}
		} else {
			$("#p52_fechaInicioDatePicker").datepicker("setDate", null);
			$('#p52_fechaInicioDatePicker .ui-datepicker-current-day').removeClass('ui-datepicker-current-day');
			$("#p52_fechaFinDatePicker").datepicker("setDate", null);
			$('#p52_fechaFinDatePicker .ui-datepicker-current-day').removeClass('ui-datepicker-current-day');
			nuevoPedidoRef.fechaIni = new Date();
			nuevoPedidoRef.fechaFin = new Date();
		}
		p52CargarDiasServicioCalendarios();
		$("#p52_div_uc").show();
		$("#p52_div_stock").show();
		$("#p52_div_fresco_puro").show();
		$("#p52_AreaBloque2").show();

		$("#p52_div_legend3").html(titBloque3_Unid + hrefBloque3);
		if (textoUNIDADESCAJAS && textoUNIDADESCAJAS.trim()) {
		    if (textoUNIDADESCAJAS.trim() === 'CAJAS') {
		    	$("#p52_div_legend3").html(titBloque3_Cajas + hrefBloque3);
			} 
		}

		event_p52_ayuda();
		$("#p52_AreaBloque2EncargoCliente").css("display", "none");
		$("#p52_AreaBloque3EncargoCliente").css("display", "none");

		if (nuevoPedidoRef.tipoPedido == "1"){

			$("#p52_lbl_fechaInicio").html(fechaEncargo);
			$("#p52_leyendaReferenciaNueva").hide();
			$("#p52_rellenoExcluir").hide();
			$("#p52_div_numeroOferta").hide();

			if (nuevoPedidoRef.conCajas == true){

				if ((null != nuevoPedidoRef.flgAvisoEncargoLote) && (nuevoPedidoRef.flgAvisoEncargoLote != "")) { //Pet. 49333. Aviso encargo lotes
					if (nuevoPedidoRef.flgAvisoEncargoLote == "L"){ // Es una referencia lote
						$("#p52_cmb_cajas").combobox('autocomplete','S');
						$("#p52_cmb_cajas").val("S");
						$("#p52_cmb_cajas").combobox("disable");
						$("#p52_ReferenciaLote").css("display", "inline-block");

					}
					if (nuevoPedidoRef.flgAvisoEncargoLote == "H"){ // Es una referencia hija de un lote
						$("#p52_cmb_cajas").combobox('autocomplete','N');
						$("#p52_cmb_cajas").val("N");
						$("#p52_cmb_cajas").combobox("disable");
						$("#p52_ReferenciaHijaDeLote").css("display", "inline-block");
					}

				} else {
					if (null != nuevoPedidoRef.cajas){
						$("#p52_cmb_cajas").combobox('autocomplete',nuevoPedidoRef.cajas);
						$("#p52_cmb_cajas").combobox('comboautocomplete',null);
						$("#p52_cmb_cajas").val(nuevoPedidoRef.cajas);
					} else{
						$("#p52_cmb_cajas").combobox('autocomplete','N');
						$("#p52_cmb_cajas").val("N");
					}
				}
				//Si es un centro Caprabo queda oculto el campo de cajas y excluir al ser horizonte.
				if (!esCentroCaprabo){
					if(mostrarCajasYExcluir){
						$("#p52_cajas").show();
					}else{
						$("#p52_excluir").hide();
						$("#p52_cajas").hide();
					}
				}
			} else {
				$("#p52_cajas").hide();
			}
			if (nuevoPedidoRef.tratamiento != "" && nuevoPedidoRef.tratamiento != null){
				//$("#p52_cmb_tratamiento").combobox('autocomplete',nuevoPedidoRef.tratamiento);
				if (nuevoPedidoRef.tratamiento=="A"){
					$("#p52_cmb_tratamiento").combobox('autocomplete',"Añadir");
					$("#p52_cmb_tratamiento").combobox('comboautocomplete',"Añadir         - Añade cantidad a transmisión");
				}else if(nuevoPedidoRef.tratamiento == "S"){
					$("#p52_cmb_tratamiento").combobox('autocomplete',"Sustituir");
					$("#p52_cmb_tratamiento").combobox('comboautocomplete',"Sustituir      - Machaca la cantidad transmitida por las del encargo");
				}else{
					$("#p52_cmb_tratamiento").combobox('autocomplete',"Fija/Mínima");
					$("#p52_cmb_tratamiento").combobox('comboautocomplete',"Fija/Mínima - Aplica la mayor cantidad entre lo transmitido y las unidades del encargo. Garantiza al menos la cantidad del encargo");
				}
				
				$("#p52_cmb_tratamiento").val(nuevoPedidoRef.tratamiento);

			}
			if (nuevoPedidoRef.esStock){
				$("#p52_div_uc .textBoxMin").css("margin-right","0px");
				$("#p52_div_stock_plat").show();
			} else {
				$("#p52_div_uc .textBoxMin").css("margin-right","20px");
				$("#p52_div_stock_plat").hide();
			}
			//$("#p52_fld_cantidad1").unbind('focus');
			$("#p52_div_fechaFin").hide();
			if (null != nuevoPedidoRef.excluir){
				$("#p52_cmb_excluir").combobox('autocomplete',nuevoPedidoRef.excluir);
				$("#p52_cmb_excluir").combobox('comboautocomplete',null);
				$("#p52_cmb_excluir").val(nuevoPedidoRef.excluir);
			} else{
				$("#p52_cmb_excluir").combobox('autocomplete','N');
				$("#p52_cmb_excluir").val("N");
				//$("#p52_cmb_excluir").attr("disabled", true); 
				if(mostrarCajasYExcluir){
					$("#p52_excluir").show();
				}else{
					$("#p52_excluir").hide();
					$("#p52_cajas").hide();
				}
			}
			$("#p52_div_frescoPuro").show();
			$("#p52_div_alimentacion").hide();
			var fecha1 = $.datepicker.formatDate('D d', nuevoPedidoRef.fechaIni,{
				dayNamesShort: $.datepicker.regional[ "es" ].dayNamesMin,
				dayNames: $.datepicker.regional[ "es" ].dayNames,
				monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
				monthNames: $.datepicker.regional[ "es" ].monthNames
			});		 
			$('#p52_lbl_cantidad1').text(fecha1);
			$('#p52_div_cantidad1').show();	
			//$("#p52_fld_cantidad1").unbind("focus");
			$("#p52_fld_cantidad1").bind("focus");
			/*$("#p52_fld_cantidad1").focus(function(event) {
				if(!$("#p56_AreaAyuda").is(':visible')){
					loadPopUpAyudaP52(event);
				} 
			});*/
			if (nuevoPedidoRef.estado == "1"){
				$('#p52_fld_cantidad1').val(nuevoPedidoRef.cantidad1);
			}
			$('#p52_div_cantidad2').hide();
			$('#p52_div_cantidad3').hide();
		} else {

			$("#p52_lbl_fechaInicio").html(fechaInicio);
			$("#p52_div_numeroOferta").show();
			var oferta = $("#p52_cmb_numeroOferta").val();
			if (nuevoPedidoRef.estado == "1"){
				text = $("#p52_cmb_numeroOferta option[value='"+nuevoPedidoRef.oferta+"']").text();
				$("#p52_cmb_numeroOferta").combobox('autocomplete',text);
			} else if (oferta != null) {
				text = $("#p52_cmb_numeroOferta option[value='"+oferta+"']").text();
				$("#p52_cmb_numeroOferta").combobox('autocomplete',text);
				nuevoPedidoRef.oferta = oferta;
			}

			if (nuevoPedidoRef.estado == "1"){
				$("#p52_cmb_numeroOferta").val(nuevoPedidoRef.oferta);
			}
			$("#p52_div_fechaFin").show();
			$("#p52_cajas").hide();
			$("#p52_div_uc .textBoxMin").css("margin-right","20px");
			$("#p52_div_stock_plat").hide();
			$("#p52_fld_cantidad1").bind("focus");
			/*$("#p52_fld_cantidad1").focus(function(event) {
				if(!$("#p56_AreaAyuda").is(':visible')){
					loadPopUpAyudaP52(event);
				} 
			});*/
			if (nuevoPedidoRef.referenciaNueva == true && (null == nuevoPedidoRef.oferta || nuevoPedidoRef.oferta == "0")){
				$("#p52_leyendaReferenciaNueva").show();
				$("#p52_rellenoExcluir").show();
				if (nuevoPedidoRef.bloqueoPilada){
					$("#p52_mensajeLeyendaReferenciaNueva").text($("#p52_ReferenciaNuevaBloqueo").text());
				} else {
					$("#p52_mensajeLeyendaReferenciaNueva").text($("#p52_ReferenciaNueva").text());
				}
				if (null != nuevoPedidoRef.excluir){
					$("#p52_cmb_excluir").combobox('autocomplete',nuevoPedidoRef.excluir);
					$("#p52_cmb_excluir").combobox('comboautocomplete',null);
					$("#p52_cmb_excluir").val(nuevoPedidoRef.excluir);
				} else{
					$("#p52_cmb_excluir").combobox('autocomplete','N');
					$("#p52_cmb_excluir").val("N");
				}
				$("#p52_excluir").hide();
				$("#p52_fechaFinDatePicker").datepicker("disable");
				$("#p52_leyendaReferenciaNueva").show();
				$("#p52_rellenoExcluir").show();
			} else {
				$("#p52_leyendaReferenciaNueva").hide();
				$("#p52_rellenoExcluir").hide();
				$("#p52_cmb_excluir").combobox('autocomplete','N');
				$("#p52_cmb_excluir").val("N");
				$("#p52_fechaFinDatePicker").datepicker("enable");
				if (nuevoPedidoRef.oferta != "0")
				{
					//En este caso selecciona oferta tenemos que ocultar el Excluir.
					$("#p52_excluir").hide();

				}else{	 
					if (null != nuevoPedidoRef.excluir){
						$("#p52_cmb_excluir").combobox('autocomplete',nuevoPedidoRef.excluir);
						$("#p52_cmb_excluir").combobox('comboautocomplete',null);
						$("#p52_cmb_excluir").val(nuevoPedidoRef.excluir);
					} else{
						$("#p52_cmb_excluir").combobox('autocomplete','S');
						$("#p52_cmb_excluir").val("S");
					}
					if(mostrarCajasYExcluir){
						$("#p52_excluir").show();
					}else{
						$("#p52_excluir").hide();
						$("#p52_cajas").hide();
					}
					
				}
				$("#p52_div_fechaFin").show();
				$("#p52_leyendaReferenciaNueva").hide();
				$("#p52_rellenoExcluir").hide();
				if (nuevoPedidoRef.frescoPuro == true && !nuevoPedidoRef.tratamientoVegalsa){
					$("#p52_div_cantidad1").show();
					$("#p52_div_cantidad2").show();
					$("#p52_div_cantidad3").show();
				} else {

					$("#p52_div_capMax").show();
					$("#p52_div_impMin").show();
				}
			}
			if (nuevoPedidoRef.frescoPuro == true){
				$("#p52_div_frescoPuro").show();
				$("#p52_div_alimentacion").hide();
				if (nuevoPedidoRef.fecha2 != null && nuevoPedidoRef.tipoPedido == 2){
					var fecha2 = $.datepicker.formatDate('D d', nuevoPedidoRef.fecha2,{
						dayNamesShort: $.datepicker.regional[ "es" ].dayNamesMin,
						dayNames: $.datepicker.regional[ "es" ].dayNames,
						monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
						monthNames: $.datepicker.regional[ "es" ].monthNames
					});		 
					$('#p52_lbl_cantidad2').text(fecha2);
					$('#p52_div_cantidad2').show();
					if (nuevoPedidoRef.estado == "1"){
						$('#p52_fld_cantidad2').val(nuevoPedidoRef.cantidad2);
					}
				} else {
					$('#p52_div_cantidad2').hide();
					//$('#p52_fld_cantidad2').val('');
				}
				if (nuevoPedidoRef.fecha3 != null && nuevoPedidoRef.tipoPedido == 2){
					var fecha3 = $.datepicker.formatDate('D d', nuevoPedidoRef.fecha3,{
						dayNamesShort: $.datepicker.regional[ "es" ].dayNamesMin,
						dayNames: $.datepicker.regional[ "es" ].dayNames,
						monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
						monthNames: $.datepicker.regional[ "es" ].monthNames
					});		 
					$('#p52_lbl_cantidad3').text(fecha3);
					$('#p52_div_cantidad3').show();
					if (nuevoPedidoRef.estado == "1"){
						$('#p52_fld_cantidad3').val(nuevoPedidoRef.cantidad3);
					}
				} else {
					$('#p52_div_cantidad3').hide();
					//$('#p52_fld_cantidad3').val('');
				}
				var fecha1 = $.datepicker.formatDate('D d', nuevoPedidoRef.fechaIni,{
					dayNamesShort: $.datepicker.regional[ "es" ].dayNamesMin,
					dayNames: $.datepicker.regional[ "es" ].dayNames,
					monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
					monthNames: $.datepicker.regional[ "es" ].monthNames
				});		 
				$('#p52_lbl_cantidad1').text(fecha1);
				$('#p52_div_cantidad1').show();	
				if (nuevoPedidoRef.estado == "1"){
					$('#p52_fld_cantidad1').val(nuevoPedidoRef.cantidad1);
				}

			} else {
				$("#p52_div_frescoPuro").hide();
				$("#p52_div_alimentacion").show();
				if (null != nuevoPedidoRef.capacidadMaxima){
					$("#p52_fld_capMax").val(p52FormateoImplantaciones(nuevoPedidoRef.capacidadMaxima));

				}
				if ((null != nuevoPedidoRef.fechaIni) && (null != nuevoPedidoRef.fechaFin) && daysDiff() > 7 && !nuevoPedidoRef.tratamientoVegalsa){
					if (null != nuevoPedidoRef.implantacionMinima){
						$("#p52_fld_impMin").val(p52FormateoImplantaciones(nuevoPedidoRef.implantacionMinima));
					}
					$("#p52_fld_impMin").removeAttr("disabled");
				} else {
					//$("#p52_fld_impMin").val(p52FormateoImplantaciones(nuevoPedidoRef.capacidadMaxima));
					$("#p52_fld_impMin").val($("#p52_fld_capMax").val());
					$("#p52_fld_impMin").attr("disabled", "disabled");
				}
			}
		}

		/*if (nuevoPedidoRef.oferta != null){
			$("#p52_fechaInicioDatePicker").datepicker("setDate", new Date(nuevoPedidoRef.fechaIni));
			$("#p52_fechaFinDatePicker").datepicker("setDate", new Date(nuevoPedidoRef.fechaFin));
		} else {
			$("#p52_fechaInicioDatePicker").datepicker("setDate", new Date(nuevoPedidoRef.fechaIni));
		}*/

		if (nuevoPedidoRef.fechaNoDisponible){
			$("#p52_fechaNoDisponible").show();
			if (nuevoPedidoRef.referenciaNueva){
				$("#p52_rellenoExcluir").hide();
			}
		} else {
			$("#p52_fechaNoDisponible").hide();
			if (nuevoPedidoRef.referenciaNueva){
				$("#p52_rellenoExcluir").show();
			}
		}
		if($("#p52_excluir").is(':visible') && !$("#p52_fechaNoDisponible").is(':visible')){
			$("#p52_rellenoMensajesFechas").show();
		}else{
			$("#p52_rellenoMensajesFechas").hide();
		}
		origen = null;
		$("#p52_fld_stock").prop("disabled", false);
		if ((nuevoPedidoRef.tipoPedido == "1" && nuevoPedidoRef.esStock) || nuevoPedidoRef.stock == -9999){
			if (nuevoPedidoRef.stock == -9999) {
				$("#p52_fld_stock").val("Error");
				$("#p52_fld_stock").addClass("p52_stock_error");
			} else {
				$("#p52_fld_stock").removeClass("p52_stock_error");
				$("#p52_fld_stock").val(nuevoPedidoRef.stock);
			}
			if (nuevoPedidoRef.esStock){
				$("#p52_fld_stock").focus(function(event) {
					if(!$("#p59_AreaStock").is(':visible')){
						loadStockPlataformaP52();
					}

				});
				$("#p52_lbl_stock").addClass("p52_stockPlataforma");
				$("#p52_lbl_stock").css( 'cursor', 'pointer' );
				$("#p52_lbl_stock").click(function(){
					if(!$("#p59_AreaStock").is(':visible')){
						loadStockPlataformaP52();
					}
				});
			} else {
				$("#p52_fld_stock").unbind("focus");
				$("#p52_lbl_stock").unbind("click");
				$("#p52_lbl_stock").css('cursor', 'default');
				$("#p52_lbl_stock").removeClass("p52_stockPlataforma");
			}
			$("#p52_fld_stock").prop("readonly", true);
			$("#p52_fld_stock").bind("keydown", function (e) {
				if (e.keyCode == 8){
					e.preventDefault();
					e.stopPropagation();
				}
			});
		} else {
			$("#p52_lbl_stock").removeClass("p52_stockPlataforma");
			$("#p52_lbl_stock").unbind("click");
			$("#p52_lbl_stock").css("cursor","default");
			$("#p52_fld_stock").removeClass("p52_stock_error");
			$("#p52_fld_stock").val(nuevoPedidoRef.stock);
			$("#p52_fld_stock").formatNumber({format:"0.##",locale:"es"});
			$("#p52_fld_stock").prop("disabled", true);
		}
		$("#p52_fld_stock_plat").prop("disabled", false);
		$("#p52_fld_stock_plat").val(nuevoPedidoRef.stockPlataforma);
		$("#p52_fld_stock_plat").prop("disabled", true);
		$("#p52_fld_uc").prop("disabled", false);
		$("#p52_fld_uc").val(nuevoPedidoRef.uniCajas);
		$("#p52_fld_uc").formatNumber({format:"0.##",locale:"es"});
		$("#p52_fld_uc").prop("disabled", true);
	}

//	$("#p52_div_legend3").text(nuevoPedidoRef.msgUNIDADESCAJAS);
	
	if (null != p52Uuid){
		nuevoPedidoRef.uuid = p52Uuid
		nuevoPedidoRef.estado = p52Estado;
	}
}

function addGridRow(){
	var objJson = $.toJSON(nuevoPedidoRef.preparePedidoAdicionalToJsonObject());


	$.ajax({
		type : 'POST',
		url : './nuevoPedidoAdicionalReferencia/addDataGrid.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		success : function(data) {	
			if (null == data){
				reloadDataP52NuevoPedidoReferencia();
				if ($("#p52_cmb_tipoPedidoAdicional").combobox("getValue") != "3"){
					$("#p52_fld_referencia").focus();
					$("#p52_fld_referencia").select();
				}
				limpiarReferenciaP52();

			} else {
				if (data == "1")
				{
					$("#p03_btn_aceptar").unbind("click");
					$("#p03_btn_aceptar").bind("click", function(e) {
						if ($("#p52_cmb_tipoPedidoAdicional").combobox("getValue") != "3"){
							$("#p52_fld_referencia").focus();
							$("#p52_fld_referencia").select();
						}
					});
					createAlert(replaceSpecialCharacters(implInicialError), "ERROR");
					reloadDataP52NuevoPedidoReferencia();
					$("#p03_btn_aceptar").focus();
				}
				else if (data == "2")
				{
					$("#p03_btn_aceptar").unbind("click");
					$("#p03_btn_aceptar").bind("click", function(e) {
						if ($("#p52_cmb_tipoPedidoAdicional").combobox("getValue") != "3"){
							$("#p52_fld_referencia").focus();
							$("#p52_fld_referencia").select();
						}
					});
					createAlert(replaceSpecialCharacters(implFinalError), "ERROR");
					reloadDataP52NuevoPedidoReferencia();
					$("#p03_btn_aceptar").focus();

				}
				else if (data == "3")
				{
					$("#p03_btn_aceptar").unbind("click");
					$("#p03_btn_aceptar").bind("click", function(e) {
						if ($("#p52_cmb_tipoPedidoAdicional").combobox("getValue") != "3"){
							$("#p52_fld_referencia").focus();
							$("#p52_fld_referencia").select();
						}
					});
					createAlert(replaceSpecialCharacters(referenceNotManaged), "ERROR");
					reloadDataP52NuevoPedidoReferencia();
					$("#p03_btn_aceptar").focus();
				}
				else
				{
					$("#p03_btn_aceptar").unbind("click");
					$("#p03_btn_aceptar").bind("click", function(e) {
						$("#p52_fld_referencia").focus();
						$("#p52_fld_referencia").select();
					});
					createAlert(replaceSpecialCharacters(data), "ERROR");
					$("#p03_btn_aceptar").focus();
				}

			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});	
}

function loadOferta(){
	var objJson = $.toJSON(nuevoPedidoRef.preparePedidoAdicionalToJsonObject());
	$.ajax({
		type : 'POST',
		url : './nuevoPedidoAdicionalReferencia/loadOferta.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {	
			nuevoPedidoRef.fechaBloqueoEncargo = data.fechaBloqueoEncargo;
			nuevoPedidoRef.fechaBloqueoEncargoPilada = data.fechaBloqueoEncargoPilada;
			if('S' == data.fechaBloqueoEncargo || 'S' == data.fechaBloqueoEncargoPilada){
				$("#p03_btn_aceptar").unbind("click");
				$("#p03_btn_aceptar").bind("click", function(e) {
					nuevoPedidoRef.tipoOferta = data.tipoOferta;
					nuevoPedidoRef.fechaNoDisponible = data.fechaNoDisponible;
					volcadoFechas(data);
					if($("#p52_fld_impMin").attr("disabled") == undefined){
						impFinalModif = true;
					} 

					//Campo para el control de mostrar las leyendas de bloqueos
					nuevoPedidoRef.mostrarLeyendaBloqueo = data.mostrarLeyendaBloqueo

					populatePedidoAdicional();
					if (!$("#p52_div_cantidad1").is(':hidden')){
						$("#p52_fld_cantidad1").focus();
						$("#p52_fld_cantidad1").select();
					} else {
						$("#p52_fld_capMax").focus();
						$("#p52_fld_capMax").select();
					}
				});
				if('S' == data.fechaBloqueoEncargo && 'S' == data.fechaBloqueoEncargoPilada){
					createAlert(fechaSelBloqueosEncargosMontajes, "ERROR");
				}else if('S' == data.fechaBloqueoEncargo ){
					createAlert(fechaSelBloqueosEncargos, "ERROR");	
				}else{//'S' == data.fechaBloqueoEncargoPilada
					createAlert(fechaSelBloqueosMontajes, "ERROR");	
				}

				$("#p03_btn_aceptar").focus();
			}else{				
				nuevoPedidoRef.tipoOferta = data.tipoOferta;
				//nuevoPedidoRef.capacidadMaxima = data.capacidadMaxima;
				//nuevoPedidoRef.implantacionMinima = data.implantacionMinima;
				nuevoPedidoRef.fechaNoDisponible = data.fechaNoDisponible;
				volcadoFechas(data);
				if($("#p52_fld_impMin").attr("disabled") == undefined){
					impFinalModif = true;
				} 

				//Campo para el control de mostrar las leyendas de bloqueos
				nuevoPedidoRef.mostrarLeyendaBloqueo = data.mostrarLeyendaBloqueo

				populatePedidoAdicional();
				if (!$("#p52_div_cantidad1").is(':hidden')){
					$("#p52_fld_cantidad1").focus();
					$("#p52_fld_cantidad1").select();
				} else {
					p52ImplantacionInicial();
					$("#p52_fld_capMax").focus();
					$("#p52_fld_capMax").select();
				}
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});	
}

function deleteOferta(pedidoRef){
	var objJson = $.toJSON(pedidoRef.preparePedidoAdicionalToJsonObject());
	$.ajax({
		type : 'POST',
		url : './nuevoPedidoAdicionalReferencia/deleteDataGrid.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {	
			if(data!=null && data==1){
				createAlert("Error en el procedimiento. Hablar con el SIEC", "ERROR");
			}
			$(gridP52PedidosAdicionales.nameJQuery).setGridParam({page:1});
			reloadDataP52NuevoPedidoReferencia();
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});	
}

function loadDates(){
	var objJson = $.toJSON(nuevoPedidoRef.preparePedidoAdicionalToJsonObject());
	$.ajax({
		type : 'POST',
		url : './nuevoPedidoAdicionalReferencia/loadDates.do',
		data : objJson,
		//async: false,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
			nuevoPedidoRef.fechaBloqueoEncargo = data.fechaBloqueoEncargo;
			nuevoPedidoRef.fechaBloqueoEncargoPilada = data.fechaBloqueoEncargoPilada;
			if('S' == data.fechaBloqueoEncargo || 'S' == data.fechaBloqueoEncargoPilada){
				$("#p03_btn_aceptar").unbind("click");
				$("#p03_btn_aceptar").bind("click", function(e) {
					if($("#p52_fld_impMin").attr("disabled") == undefined){
						impFinalModif = true;
					} 
					volcadoFechasDates(data);

					//Campo para el control de mostrar las leyendas de bloqueos
					nuevoPedidoRef.mostrarLeyendaBloqueo = data.mostrarLeyendaBloqueo

					populatePedidoAdicional();

					if (!nuevoPedidoRef.frescoPuro){
						p52ImplantacionInicial();
					}
					if (!$("#p52_div_cantidad1").is(':hidden')){
						$("#p52_fld_cantidad1").focus();
						$("#p52_fld_cantidad1").select();
					} else {
						$("#p52_fld_capMax").focus();
						$("#p52_fld_capMax").select();
					}
				});
				if('S' == data.fechaBloqueoEncargo && 'S' == data.fechaBloqueoEncargoPilada){
					createAlert(fechaSelBloqueosEncargosMontajes, "ERROR");
				}else if('S' == data.fechaBloqueoEncargo &&  nuevoPedidoRef.tipoPedido == "2"){
					createAlert(fechaSelBloqueosEncargos, "ERROR");	
				}else if('S' == data.fechaBloqueoEncargo &&  nuevoPedidoRef.tipoPedido == "1"){
					createAlert(fechasEntregaBloqueadas, "ERROR");	
				}else{//'S' == data.fechaBloqueoEncargoPilada
					createAlert(fechaSelBloqueosMontajes, "ERROR");	
				}

				$("#p03_btn_aceptar").focus();

			}else{				

				//nuevoPedidoRef.capacidadMaxima = data.capacidadMaxima;
				//nuevoPedidoRef.implantacionMinima = data.implantacionMinima;
				if($("#p52_fld_impMin").attr("disabled") == undefined){
					impFinalModif = true;
				} 
				volcadoFechasDates(data);

				//Campo para el control de mostrar las leyendas de bloqueos
				nuevoPedidoRef.mostrarLeyendaBloqueo = data.mostrarLeyendaBloqueo

				populatePedidoAdicional();

				if (!nuevoPedidoRef.frescoPuro){
					p52ImplantacionInicial();
				}

				if (!$("#p52_div_cantidad1").is(':hidden')){
					$("#p52_fld_cantidad1").focus();
					$("#p52_fld_cantidad1").select();
				} else {
					$("#p52_fld_capMax").focus();
					$("#p52_fld_capMax").select();
				}
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});	
}

function recalcularDiasHabilitados(){
	var objJson = $.toJSON(nuevoPedidoRef.preparePedidoAdicionalToJsonObject());
	$.ajax({
		type : 'POST',
		url : './nuevoPedidoAdicionalReferencia/recalcularDiasHabilitados.do',
		data : objJson,
		//async: false,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {	
			nuevoPedidoRef.capacidadMaxima = data.capacidadMaxima;
			nuevoPedidoRef.implantacionMinima = data.implantacionMinima;
			volcadoFechasDates(data);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});	
}

function volcadoFechasDates(data)
{
	if (null != data.strFechaIni){
		var fecIni = $.datepicker.parseDate( "ddmmyy", data.strFechaIni );
		nuevoPedidoRef.fechaIni = fecIni;
	}
	if (null != data.strFechaFin){
		var fecFin = $.datepicker.parseDate( "ddmmyy", data.strFechaFin );
		nuevoPedidoRef.fechaFin = fecFin;
	}
	if (null != data.strFecha2){
		var fecha2 = $.datepicker.parseDate( "ddmmyy", data.strFecha2 );	
		nuevoPedidoRef.fecha2 = fecha2;
	}
	else
	{
		nuevoPedidoRef.fecha2 = null;
	}
	if (null != data.strFecha3){
		var fecha3 = $.datepicker.parseDate( "ddmmyy", data.strFecha3 );
		nuevoPedidoRef.fecha3 = fecha3;
	}
	else
	{
		nuevoPedidoRef.fecha3 = null;
	}
	if (null != data.strFechaPilada){
		var fechaPilada = $.datepicker.parseDate( "ddmmyy", data.strFechaPilada );
		nuevoPedidoRef.fechaPilada = fechaPilada;
	}
	else
	{
		nuevoPedidoRef.fechaPilada = null;
	}
	if (null != data.strMinFechaIni){
		var minFechaIni = $.datepicker.parseDate( "ddmmyy", data.strMinFechaIni );
		nuevoPedidoRef.minFechaIni = minFechaIni;
	}

	if (null != data.strFechaIniOferta){
		var fechaIniOferta = $.datepicker.parseDate( "ddmmyy", data.strFechaIniOferta );
		nuevoPedidoRef.fechaIniOferta = fechaIniOferta;
	}

	if (null != data.strFechaFinOferta){
		var fechaFinOferta = $.datepicker.parseDate( "ddmmyy", data.strFechaFinOferta );
		nuevoPedidoRef.fechaFinOferta = fechaFinOferta;
	}

}

function volcadoFechas(data)
{
	if (null != data.strFechaIni){
		var fecIni = $.datepicker.parseDate( "ddmmyy", data.strFechaIni );
		nuevoPedidoRef.fechaIni = fecIni;
	} else {
		nuevoPedidoRef.fechaIni = null;
	}
	if (null != data.strFechaFin){
		var fecFin = $.datepicker.parseDate( "ddmmyy", data.strFechaFin );
		nuevoPedidoRef.fechaFin = fecFin;
	} else {
		nuevoPedidoRef.fechaFin = null;
	}
	if (null != data.strFecha2){
		var fecha2 = $.datepicker.parseDate( "ddmmyy", data.strFecha2 );	
		nuevoPedidoRef.fecha2 = fecha2;
	} else {
		nuevoPedidoRef.fecha2 = null;
	}

	if (null != data.strFecha3){
		var fecha3 = $.datepicker.parseDate( "ddmmyy", data.strFecha3 );
		nuevoPedidoRef.fecha3 = fecha3;
	} else {
		nuevoPedidoRef.fecha3 = null;
	}
	if (null != data.strFechaPilada){
		var fechaPilada = $.datepicker.parseDate( "ddmmyy", data.strFechaPilada );
		nuevoPedidoRef.fechaPilada = fechaPilada;
	} else {
		nuevoPedidoRef.fechaPilada = null;
	}
	if (null != data.strMinFechaIni){
		var minFechaIni = $.datepicker.parseDate( "ddmmyy", data.strMinFechaIni );
		nuevoPedidoRef.minFechaIni = minFechaIni;
	} else {
		nuevoPedidoRef.minFechaIni = null;
	}
	if (null != data.strFechaIniOferta){
		var fechaIniOferta = $.datepicker.parseDate( "ddmmyy", data.strFechaIniOferta );
		nuevoPedidoRef.fechaIniOferta = fechaIniOferta;
	} else {
		nuevoPedidoRef.fechaIniOferta = null;
	}
	if (null != data.strFechaFinOferta){
		var fechaFinOferta = $.datepicker.parseDate( "ddmmyy", data.strFechaFinOferta );
		nuevoPedidoRef.fechaFinOferta = fechaFinOferta;
	} else {
		nuevoPedidoRef.fechaFinOferta = null;
	}
}

function reloadDataP52NuevoPedidoReferencia() {

	/*if (gridP52PedidosAdicionales.firstLoad) {
			gridP52PedidosAdicionales.firstLoad = false;
	        if (gridP52PedidosAdicionales.isColState) {
	            $(this).jqGrid("remapColumns", gridP52PedidosAdicionales.myColumnsState.permutation, true);
	        }
	    } else {
	    	gridP52PedidosAdicionales.myColumnsState = gridP52PedidosAdicionales.restoreColumnState(gridP52PedidosAdicionales.cm);
	    	gridP52PedidosAdicionales.isColState = typeof (gridP52PedidosAdicionales.myColumnsState) !== 'undefined' && gridP52PedidosAdicionales.myColumnsState !== null;
	    }*/
	//$("#p16_AreaMovimientos .loading").css("display", "block");
	//var objJson = $.toJSON(nuevoPedidoRef.preparePedidoAdicionalToJsonObject());
	$.ajax({
		type : 'POST',			
		url : './nuevoPedidoAdicionalReferencia/loadDataGrid.do?page='+gridP52PedidosAdicionales.getActualPage()+'&max='+gridP52PedidosAdicionales.getRowNumPerPage()+'&index='+gridP52PedidosAdicionales.getSortIndex()+'&sortorder='+gridP52PedidosAdicionales.getSortOrder(),
		//data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {	
			$(gridP52PedidosAdicionales.nameJQuery)[0].addJSONData(data);
			gridP52PedidosAdicionales.actualPage = data.page;
			gridP52PedidosAdicionales.localData = data;
			activoBotonGuardar = true;
		},
		error : function (xhr, status, error){	
			activoBotonGuardar = true;
			handleError(xhr, status, error, locale);				
		}			
	});		

}

function daysDiff(){
	var a = nuevoPedidoRef.fechaFin.getTime(),
	b = nuevoPedidoRef.fechaIni.getTime(),
	c = 24*60*60*1000,
	diffDays = Math.round(Math.abs((a - b)/(c)));
	return diffDays;
}

function loadPopUpAyudaP52(event){
	if (nuevoPedidoRef != null && nuevoPedidoRef.tipoPedido != "3"){

		defineCampoFoco(event.target);
		v_Oferta = new VOferta($("#centerId").val(),nuevoPedidoRef.referenciaUnitaria);
		vReferenciasCentro=new ReferenciasCentro(nuevoPedidoRef.referenciaUnitaria, $("#centerId").val());
		v_Oferta.tipoOferta = nuevoPedidoRef.tipoOferta;
		if (nuevoPedidoRef.oferta == null || nuevoPedidoRef.oferta == "0"){
			$("#p56_fld_pestanaAyuda1").text(literalAyuda1SinOferta);		
		}else{
			$("#p56_fld_pestanaAyuda1").text(literalAyuda1ConOferta);
		}
		load_cmbVentasUltimasOfertasP56(v_Oferta,nuevoPedidoRef.oferta,nuevoPedidoRef.tipoPedido);
		//$( "#p56_AreaAyuda" ).dialog( "open" );
		//alert(event.target.id);
		//$focused.focus();
		//$focused.select();
	}
}

function p52Remove(){
	let gridFilasMarcadas = $("#gridP52PedidosAdicionales").getGridParam('selarrrow');
	
	// copia del array de los IDS seleccionados.
	let idsFilasMarcadas = $.makeArray(gridFilasMarcadas);
	let numeroElementos = gridFilasMarcadas.length;
	for (i = 0; i < numeroElementos; i++){
		var rowData = $("#gridP52PedidosAdicionales").getRowData(idsFilasMarcadas[i]);

		var codCentro = rowData['codCentro'];
		var codArt = $("#"+idsFilasMarcadas[i]+"_codArt").val();
		var uuid = $("#"+idsFilasMarcadas[i]+"_uuid").val();

		pedidoRef = new PedidoAdicionalCompleto(null, codCentro, codArt);
		pedidoRef.calcularFechaIniFin = '';
		pedidoRef.uuid = uuid;
		deleteOferta(pedidoRef);

		// eliminar la fila borrada del Grid mostrado en pantalla. 
		$("#gridP52PedidosAdicionales").delRowData(idsFilasMarcadas[i]);
	}
}


function validarCantidad(cantidad){

	var cantidadPunto = cantidad.replace(',','.');

	if (isNaN(parseFloat(cantidadPunto)) || cantidad.split(",").length > 2){
		return false;
	} else {
		if (cantidadPunto > 0 && cantidadPunto <= 9999){
			return true;
		} else {
			return false;
		}
	}
}

function validarTelefono(telefono){

	if (isNaN(telefono) || telefono.length < 9){
		return false;
	} else {
		return true;
	}
}

function validarCantidadAlim(cantidad){

	var cantidadPunto = cantidad.replace(',','.');

	if (isNaN(parseFloat(cantidadPunto)) || cantidad.split(",").length > 2){
		return false;
	} else {
		return true;
	}
}

function finder(){
	if (null != nuevoPedidoRef){
		
		// MISUMI-324: MISUMI-JAVA Encargos Referencias DESCENTRALIZADAS bloquear
		if (nuevoPedidoRef.tipoAprovisionamiento == 'D' && nuevoPedidoRef.tipoPedido=="1"
			// MISUMI-358
			&& !mostrarCajasYExcluir){
			$("#p03_btn_aceptar").unbind("click");
			$("#p03_btn_aceptar").bind("click", function(e) {
				$("#p52_fld_referencia").focus();
				$("#p52_fld_referencia").select();
			});
			createAlert(replaceSpecialCharacters(referenceNotManaged), "ERROR");
			$("#p03_btn_aceptar").focus();
			return;
		}
		
		if (nuevoPedidoRef.tipoPedido =="2" && !nuevoPedidoRef.frescoPuro && ((nuevoPedidoRef.catalogo == "B") || nuevoPedidoRef.bloqueoPilada)){
			//Si es Montaje Adicional de alimentacion y (es baja de catalogo o si tiene un bloqueo de piladas)
			//$("#p52_fld_referencia").val("");
			$("#p03_btn_aceptar").unbind("click");
			$("#p03_btn_aceptar").bind("click", function(e) {
				$("#p52_fld_referencia").focus();
				$("#p52_fld_referencia").select();
			});
			createAlert(referenceLocked, "ERROR");
			$("#p03_btn_aceptar").focus();

		}else {
			if (controlBloqueoP52()){
				if((('S' == nuevoPedidoRef.fechaBloqueoEncargo || 'S' == nuevoPedidoRef.fechaBloqueoEncargoPilada || nuevoPedidoRef.errorWS) && nuevoPedidoRef.tipoPedido == "2") 
						|| (('S' == nuevoPedidoRef.fechaBloqueoEncargo || nuevoPedidoRef.errorWS) && nuevoPedidoRef.tipoPedido == "1")){
					if('S' == nuevoPedidoRef.fechaBloqueoEncargo && 'S' == nuevoPedidoRef.fechaBloqueoEncargoPilada){
						createAlert(fechaSelBloqueosEncargosMontajes, "ERROR");
					}else if('S' == nuevoPedidoRef.fechaBloqueoEncargo &&  nuevoPedidoRef.tipoPedido == "2" ){
						createAlert(fechaSelBloqueosEncargos, "ERROR");	
					}else if('S' == nuevoPedidoRef.fechaBloqueoEncargo &&  nuevoPedidoRef.tipoPedido == "1" ){
						createAlert(fechasEntregaBloqueadas, "ERROR");	
					}else if ('S' == nuevoPedidoRef.fechaBloqueoEncargoPilada){//
						createAlert(fechaSelBloqueosMontajes, "ERROR");	
					} else {
						createAlert(nuevoPedidoRef.errorWS, "ERROR");	
					}
				}else if(nuevoPedidoRef.ofertaErr != null){
					//MISUMI-270 OfertaErr será distinto de null cuando se haya buscado un encargo o encargo cliente. En el controlador se controla que lo calcule solo
					//si es encargo cliente o encargo y además que solo tenga dato si es oferta 4000 con anno 9999 o 2010 y MMC = N
					createAlert(nuevoPedidoRef.ofertaErr, "ERROR");	
				}else if (null == nuevoPedidoRef.oferta && nuevoPedidoRef.tipoPedido =="2"){
					$("#p03_btn_aceptar").unbind("click");
					$("#p03_btn_aceptar").bind("click", function(e) {
						$("#p52_cmb_numeroOferta").focus();
						$("#p52_cmb_numeroOferta").select();
					});
					createAlert(noOferta, "ERROR");
					$("#p03_btn_aceptar").focus();
				} else if (null == nuevoPedidoRef.fechaIni){
					createAlert(noStartDate, "ERROR");
				}else if (null == nuevoPedidoRef.fechaFin && nuevoPedidoRef.tipoPedido =="2"){
					createAlert(noEndDate, "ERROR");
				} else if (nuevoPedidoRef.fechaIni > nuevoPedidoRef.fechaFin && nuevoPedidoRef.tipoPedido =="2"){
					createAlert(endDateGrtrStartDate, "ERROR");
				} else if (nuevoPedidoRef.conCajas == true && nuevoPedidoRef.tipoPedido =="1" && null == $("#p52_cmb_cajas").val()){
					createAlert(noCajas, "ERROR");
				}else{
					var valido = true;
					if (nuevoPedidoRef.tipoPedido =="2"){
						if (nuevoPedidoRef.frescoPuro == true){
							if(validarCantidad($("#p52_fld_cantidad1").val()) == false){
								$("#p03_btn_aceptar").unbind("click");
								$("#p03_btn_aceptar").bind("click", function(e) {
									$("#p52_fld_cantidad1").focus();
									$("#p52_fld_cantidad1").select();
								});
								createAlert(invalidCant1, "ERROR");
								$("#p03_btn_aceptar").focus();
								valido = false;
							} else if (null != nuevoPedidoRef.fecha2 && validarCantidad($("#p52_fld_cantidad2").val()) == false){
								$("#p03_btn_aceptar").unbind("click");
								$("#p03_btn_aceptar").bind("click", function(e) {
									$("#p52_fld_cantidad2").focus();
									$("#p52_fld_cantidad2").select();
								});
								createAlert(invalidCant2, "ERROR");
								$("#p03_btn_aceptar").focus();
								valido = false;
							} else if (null != nuevoPedidoRef.fecha3 && validarCantidad($("#p52_fld_cantidad3").val()) == false){
								$("#p03_btn_aceptar").unbind("click");
								$("#p03_btn_aceptar").bind("click", function(e) {
									$("#p52_fld_cantidad3").focus();
									$("#p52_fld_cantidad3").select();
								});
								createAlert(invalidCant3, "ERROR");
								$("#p03_btn_aceptar").focus();
								valido = false;
							}
						} else {
							if(validarCantidadAlim($("#p52_fld_capMax").val()) == false || $("#p52_fld_capMax").val() <= 0 || $("#p52_fld_capMax").val() > 9999 ){
								$("#p03_btn_aceptar").unbind("click");
								$("#p03_btn_aceptar").bind("click", function(e) {
									$("#p52_fld_capMax").focus();
									$("#p52_fld_capMax").select();
								});
								createAlert(invalidCapMax, "ERROR");
								$("#p03_btn_aceptar").focus();
								valido = false;
							} else if ((null != nuevoPedidoRef.fechaIni) && (null != nuevoPedidoRef.fechaFin) && (daysDiff() > 7) && 
									(validarCantidadAlim($("#p52_fld_impMin").val()) == false || $("#p52_fld_impMin").val() <= 0 || $("#p52_fld_impMin").val() > 9999)){
								$("#p03_btn_aceptar").unbind("click");
								$("#p03_btn_aceptar").bind("click", function(e) {
									$("#p52_fld_impMin").focus();
									$("#p52_fld_impMin").select();
								});
								createAlert(invalidImpMin, "ERROR");
								$("#p03_btn_aceptar").focus();
								valido = false;
							} 
						}
					} else if (nuevoPedidoRef.tipoPedido == "1"){
						if(validarCantidad($("#p52_fld_cantidad1").val()) == false){
							$("#p03_btn_aceptar").unbind("click");
							$("#p03_btn_aceptar").bind("click", function(e) {
								$("#p52_fld_cantidad1").focus();
								$("#p52_fld_cantidad1").select();
							});
							createAlert(invalidCant1, "ERROR");
							valido = false;
						}
					} else {
						if(validarCantidad($("#p52_fld_cantidad_enc_cli").val()) == false){
							$("#p03_btn_aceptar").unbind("click");
							$("#p03_btn_aceptar").bind("click", function(e) {
								$("#p52_fld_cantidad1").focus();
								$("#p52_fld_cantidad1").select();
							});
							createAlert(invalidCantidadEncargo, "ERROR");
							valido = false;
						}else if(!$("#p52_fld_clienteNombre").val() ){
							$("#p03_btn_aceptar").unbind("click");
							$("#p03_btn_aceptar").bind("click", function(e) {
								$("#p52_fld_clienteNombre").focus();
								$("#p52_fld_clienteNombre").select();
							});
							createAlert(noClientName, "ERROR");
							valido = false;
						}else if(!$("#p52_fld_clientePrimerApellido").val() ){
							$("#p03_btn_aceptar").unbind("click");
							$("#p03_btn_aceptar").bind("click", function(e) {
								$("#p52_fld_clientePrimerApellido").focus();
								$("#p52_fld_clientePrimerApellido").select();
							});
							createAlert(noClientSurname, "ERROR");
							valido = false;
						}else if(!$("#p52_fld_clienteTelefono").val() ){
							$("#p03_btn_aceptar").unbind("click");
							$("#p03_btn_aceptar").bind("click", function(e) {
								$("#p52_fld_clienteTelefono").focus();
								$("#p52_fld_clienteTelefono").select();
							});
							createAlert(noTelephoneClient, "ERROR");
							valido = false;
						}else if(!validarTelefono($("#p52_fld_clienteTelefono").val()) ){
							$("#p03_btn_aceptar").unbind("click");
							$("#p03_btn_aceptar").bind("click", function(e) {
								$("#p52_fld_clienteTelefono").focus();
								$("#p52_fld_clienteTelefono").select();
							});
							createAlert(invalidTelefonoCliente, "ERROR");
							valido = false;
						}else if(!$("#p52_fld_centroContacto").val() ){
							$("#p03_btn_aceptar").unbind("click");
							$("#p03_btn_aceptar").bind("click", function(e) {
								$("#p52_fld_centroContacto").focus();
								$("#p52_fld_centroContacto").select();
							});
							createAlert(noCenterContact, "ERROR");
							valido = false;
						}
					}
					if (valido){

//						nuevoPedidoRef.msgUNIDADESCAJAS = $("#p52_div_legend3").text();

						if (nuevoPedidoRef.conCajas == true && nuevoPedidoRef.tipoPedido =="1"){
							nuevoPedidoRef.cajas = $("#p52_cmb_cajas").combobox("getValue");
						}
						if (nuevoPedidoRef.tipoPedido =="1"){
							nuevoPedidoRef.cantidad1 = $("#p52_fld_cantidad1").val().replace(',','.');
							nuevoPedidoRef.cantidad2 = null;
							nuevoPedidoRef.cantidad3 = null;
							nuevoPedidoRef.fechaFin = null;
							nuevoPedidoRef.capacidadMaxima = null;
							nuevoPedidoRef.implantacionMinima = null;
							nuevoPedidoRef.excluir = $("#p52_cmb_excluir").combobox("getValue");
							nuevoPedidoRef.tratamiento = $("#p52_cmb_tratamiento").combobox("getValue");
						} else if (nuevoPedidoRef.tipoPedido =="2") {

							if (nuevoPedidoRef.frescoPuro == true){
								nuevoPedidoRef.cantidad1 = $("#p52_fld_cantidad1").val().replace(',','.');
								if (null != nuevoPedidoRef.fecha2){
									nuevoPedidoRef.cantidad2 = $("#p52_fld_cantidad2").val().replace(',','.');
								} 
								if (null != nuevoPedidoRef.fecha3){
									nuevoPedidoRef.cantidad3 = $("#p52_fld_cantidad3").val().replace(',','.');
								}
							} else {
								nuevoPedidoRef.capacidadMaxima = $("#p52_fld_capMax").val().replace(',','.');
								if ((null != nuevoPedidoRef.fechaIni) && (null != nuevoPedidoRef.fechaFin) && (daysDiff() > 7)){
									nuevoPedidoRef.implantacionMinima = $("#p52_fld_impMin").val().replace(',','.');
								} 
							}
						} else {
							nuevoPedidoRef.cantidad1 = null;
							nuevoPedidoRef.cantidad2 = null;
							nuevoPedidoRef.cantidad3 = null;
							nuevoPedidoRef.fechaFin = null;
							nuevoPedidoRef.capacidadMaxima = null;
							nuevoPedidoRef.implantacionMinima = null;
							if (nuevoPedidoRef.datosPedidoPesoDesde){
								nuevoPedidoRef.datosPedidoPesoDesde = nuevoPedidoRef.datosPedidoPesoDesde.replace(',','.');
							}
							if (nuevoPedidoRef.datosPedidoPesoHasta){
								nuevoPedidoRef.datosPedidoPesoHasta = nuevoPedidoRef.datosPedidoPesoHasta.replace(',','.');
							}

							nuevoPedidoRef.datosPedidoRadioPeso = nuevoPedidoRef.datosPedidoRadioPeso;
							if (nuevoPedidoRef.datosPedidoUnidadesPedir){
								nuevoPedidoRef.datosPedidoUnidadesPedir = nuevoPedidoRef.datosPedidoUnidadesPedir.replace(',','.');
							}
							nuevoPedidoRef.nombreCliente =$("#p52_fld_clienteNombre").val()
							nuevoPedidoRef.apellidoCliente = $("#p52_fld_clientePrimerApellido").val();
							nuevoPedidoRef.telefonoCliente = $("#p52_fld_clienteTelefono").val();
							nuevoPedidoRef.contactoCentro = $("#p52_fld_centroContacto").val();
						}

						// Inicio MISUMI-409.
						if (nuevoPedidoRef.capacidadMaxima != null && nuevoPedidoRef.cantidadMaxima != null	&& nuevoPedidoRef.cantidadMaxima > 0
							&& nuevoPedidoRef.capacidadMaxima > nuevoPedidoRef.cantidadMaxima){
							if (!$("#popupShowMessage").is(':visible')){
								let cantidadMaximaVegalsaNuevo = cantidadMaximaVegalsa+nuevoPedidoRef.cantidadMaxima;
								createAlert(cantidadMaximaVegalsaNuevo, "ERROR");
							}
						}else{
						// FIN MISUMI-409.
							addGridRow();
						}
					}

				}
			}
		}


	} else {
		$("#p52_fld_referencia").val("");
		$("#p03_btn_aceptar").unbind("click");
		$("#p03_btn_aceptar").bind("click", function(e) {
			$("#p52_fld_referencia").focus();
			$("#p52_fld_referencia").select();
		});
		createAlert(noReference, "ERROR");
		$("#p03_btn_aceptar").focus();
	}
}

function deleteGrid(){
	var s;
	s = $(gridP52PedidosAdicionales.nameJQuery).jqGrid('getGridParam','selarrrow');
	if (s.length > 0) {
		$(function() {
			$( "#p52dialog-confirm" ).dialog({
				resizable: false,
				height:140,
				modal: true,
				buttons: {
					"Si": function() {
						p52Remove();
						$( this ).dialog( "close" );
					},
					"No": function() {
						$( this ).dialog( "close" );
					}
				}
			});
		});
	} else {
		createAlert(noPedidoDelete, "ERROR");
	}
}

function saveGrid(){
	var gridCount = $(gridP52PedidosAdicionales.nameJQuery).jqGrid('getGridParam', 'reccount');
	if (gridCount > 0) {
		loadReferenciaActiva = true;
		$.ajax({
			type : 'POST',
			url : './nuevoPedidoAdicionalReferencia/saveDataGrid.do',
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			beforeSend : function(){
				// Desactivar los botones para evitar errores de pulsaciones erráticas.
				$('#p52_btn_anadir').attr('disabled','disabled');
				$('#p52_btn_borrar').attr('disabled','disabled');
				$('#p52_btn_cancelar').attr('disabled','disabled');
			},
			success : function(data) {
				resultadoSaveGrid(data.error, data.total);

				// Activar nuevamente los botones desactivados una vez completada la operación de guardar.
				$('#p52_btn_anadir').removeAttr('disabled');
				$('#p52_btn_borrar').removeAttr('disabled');
				$('#p52_btn_cancelar').removeAttr('disabled');

				$(gridP52PedidosAdicionales.nameJQuery).setGridParam({page:1});
				reloadDataP52NuevoPedidoReferencia();
				loadReferenciaActiva = false;
			},
			error : function (xhr, status, error){
				activoBotonGuardar = true;

				// Activar nuevamente los botones desactivados una vez completada la operación de guardar.
				$('#p52_btn_anadir').removeAttr('disabled');
				$('#p52_btn_borrar').removeAttr('disabled');
				$('#p52_btn_cancelar').removeAttr('disabled');

				handleError(xhr, status, error, locale);				
			}			
		});	
	} else {
		$("#p03_btn_aceptar").unbind("click");
		$("#p03_btn_aceptar").bind("click", function(e) {
			if ($("#p52_cmb_tipoPedidoAdicional").combobox("getValue") == "3"){
				$("#p52_cmb_tipoPedidoAdicional").focus();
			} else {
				$("#p52_fld_referencia").focus();
				$("#p52_fld_referencia").select();
			}
		});
		activoBotonGuardar = true;
		createAlert(noPedidoSave, "ERROR");
		$("#p03_btn_aceptar").focus();
	}
}

function resultadoSaveGrid(error, total){
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
	$("#p03_btn_aceptar").unbind("click");
	$("#p03_btn_aceptar").bind("click", function(e) {
		if ($("#p52_cmb_tipoPedidoAdicional").combobox("getValue") == "3"){
			$("#p52_cmb_tipoPedidoAdicional").focus();
		} else {
			$("#p52_fld_referencia").focus();
			$("#p52_fld_referencia").select();
		}
	});
	createAlert(message, level);
	$("#p03_btn_aceptar").focus();

}

function limpiarReferenciaP52(){
	nuevoPedidoRef = null;
	// MISUMI-323
	$("#p52_lbl_descPedido").text("");
	//
	$("#p52_fld_referencia").val("");
	$("#p52_fld_aprov").val("");
	$("#p52_fld_denominacion").val("");

	$('#p52_fld_cantidad1').val("");
	$('#p52_fld_cantidad2').val("");
	$('#p52_fld_cantidad3').val("");
	$("#p52_fld_uc").val("");
	$("#p52_fld_stock").val("");
	$("#p52_fld_stock").prop("disabled", true);
	$("#p52_div_uc .textBoxMin").css("margin-right","20px");
	$("#p52_fld_stock_plat").val("");
	$("#p52_div_stock_plat").hide();
	$("#p52_fld_stock").val("");
	$("#p52_fld_capMax").val("");
	$("#p52_fld_impMin").val("");

	$("#p52_fld_stock_enc_cli").val("");
	$("#p52_fld_cantidad_enc_cli").val("");
	$("#p52_fld_clienteNombre").val("");
	$("#p52_fld_clientePrimerApellido").val("");
	$("#p52_fld_clienteTelefono").val("");
	$("#p52_fld_centroContacto").val("");

	$("#p52_fld_referencia").focus();
	$("#p52_fld_referencia").select();
	$("#p52_AreaTextilLote").hide();

}

function getImplantacionInicial(){
	var objJson = $.toJSON(nuevoPedidoRef.preparePedidoAdicionalToJsonObject());
	$.ajax({
		type : 'POST',
		url : './nuevoPedidoAdicionalReferencia/getImplantacionInicial.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {	
			nuevoPedidoRef.capacidadMaxima = data.capacidadMaxima;
			nuevoPedidoRef.implantacionMinima = data.implantacionMinima;
			populatePedidoAdicional();	

		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});	

}

function controlBloqueoP52(){
	// MISUMI-369: no se hara un control de bloqueo para referencias vegalsa
	if (nuevoPedidoRef.tratamientoVegalsa){
	    return true;
	}
	if ((nuevoPedidoRef.bloqueoEncargo && nuevoPedidoRef.tipoPedido == "1") || 
			(nuevoPedidoRef.bloqueoEncargo && nuevoPedidoRef.tipoPedido == "2" && nuevoPedidoRef.frescoPuro) ||
			(nuevoPedidoRef.bloqueoPilada && nuevoPedidoRef.tipoPedido == "2" && !nuevoPedidoRef.frescoPuro) ||
			(nuevoPedidoRef.tipoPedido == "2" && nuevoPedidoRef.catalogo === 'B')){
		$("#p03_btn_aceptar").unbind("click");
		$("#p03_btn_aceptar").bind("click", function(e) {
			$("#p52_fld_referencia").focus();
			$("#p52_fld_referencia").select();
		});
		createAlert(referenceLocked,"ERROR");
		$("#p03_btn_aceptar").focus();
		return false;
	} else if (nuevoPedidoRef.bloqueoPilada && nuevoPedidoRef.tipoPedido == "2" && nuevoPedidoRef.frescoPuro && null != nuevoPedidoRef.fechaPilada){
		$("#p03_btn_aceptar").unbind("click");
		$("#p03_btn_aceptar").bind("click", function(e) {
			if (!$("#p52_div_cantidad1").is(':hidden')){
				$("#p52_fld_cantidad1").focus();
				$("#p52_fld_cantidad1").select();
			} else {
				$("#p52_fld_capMax").focus();
				$("#p52_fld_capMax").select();
			}
		});
		createAlert(referenceNoMaintenance,"ERROR");
		$("#p03_btn_aceptar").focus();
		return false;
	} else if (nuevoPedidoRef.tipoPedido == "3" && nuevoPedidoRef.errorValidar) {
		$("#p03_btn_aceptar").unbind("click");
		$("#p03_btn_aceptar").bind("click", function(e) {
			$("#p52_fld_referencia").focus();
			$("#p52_fld_referencia").select();
		});
		createAlert(nuevoPedidoRef.errorValidar,"ERROR");
		$("#p03_btn_aceptar").focus();
		return false;
	} else {
		return true;
	}
}

function p52CargaDatosCalendarios(){

	//Carga de datos para los calendarios
	$("#codCentroCalendario").val($("#centerId").val());
	$("#codArticuloCalendario").val(nuevoPedidoRef.codArtEroski);
	$("#identificadorCalendario").val("");
	$("#identificadorSIACalendario").val("");
	$("#encargoClienteUnidadesPedirCalendario").val("");
	$("#encargoClienteEspecialCalendario").val("");
	if ($("#p52_cmb_tipoPedidoAdicional").combobox("getValue") == "1"){
		$("#clasePedidoCalendario").val("E");
	}else{
		$("#clasePedidoCalendario").val("M");
	}
	if (nuevoPedidoRef != null && nuevoPedidoRef.frescoPuro != null){
		if (nuevoPedidoRef.frescoPuro){
			$("#esFresco").val("S");
		}else{
			$("#esFresco").val("N");
		}
	}
	$("#recargarParametrosCalendario").val("S");
	$("#cargadoDSCalendario").val("S");

}

function p52CargaDatosCalendarioEncCli(){

	//Carga de datos para los calendarios
	$("#codCentroCalendario").val($("#centerId").val());
	//$("#codArticuloCalendario").val(nuevoPedidoRef.codArt);
	$("#codArticuloCalendario").val(nuevoPedidoRef.codArtEroski);
	$("#identificadorCalendario").val("");
	$("#identificadorSIACalendario").val("");
	$("#clasePedidoCalendario").val("EC");


	if (nuevoPedidoRef != null && nuevoPedidoRef.frescoPuro != null){
		if (nuevoPedidoRef.frescoPuro){
			$("#esFresco").val("S");
		}else{
			$("#esFresco").val("N");
		}
	}
	$("#recargarParametrosCalendario").val("S");
	$("#cargadoDSCalendario").val("S");
}

function p52CargarDiasServicioCalendarios(){
	if ($("#centerId").val() != ''){
		if ($("#p52_cmb_tipoPedidoAdicional").combobox("getValue") != null && $("#p52_cmb_tipoPedidoAdicional").combobox("getValue") != ""){
			if ($("#p52_fld_referencia").val() != ""){
				//Se actualizan los valores de los calendarios
				p52CargaDatosCalendarios();
				$("#p52_fechaInicioDatePicker").datepicker('refresh');
				p52CargaDatosCalendarios();
				$("#p52_fechaFinDatePicker").datepicker('refresh');
				//p52CargaDatosCalendarioEncCli();
				//$("#p52_fechaEntregaEncCliDatePicker").datepicker('refresh');
			}
		}
	}
}

function p52CargarDiasServicioCalendariosObtenerRef(){
	if ($("#centerId").val() != ''){
		if ($("#p52_cmb_tipoPedidoAdicional").combobox("getValue") != null && $("#p52_cmb_tipoPedidoAdicional").combobox("getValue") != ""){
			if ($("#p52_fld_referencia").val() != ""){
				//Se actualizan los valores de los calendarios
				p52CargaDatosCalendariosObtenerRef();
				$("#p52_fechaInicioDatePicker").datepicker('refresh');
				p52CargaDatosCalendariosObtenerRef();
				$("#p52_fechaFinDatePicker").datepicker('refresh');
			}
		}
	}
}

function p52CargaDatosCalendariosObtenerRef(){

	//Carga de datos para los calendarios
	$("#codCentroCalendario").val($("#centerId").val());
	//$("#codArticuloCalendario").val(nuevoPedidoRef.codArt);
	$("#codArticuloCalendario").val(nuevoPedidoRef.codArtEroski);
	$("#identificadorCalendario").val("");
	$("#identificadorSIACalendario").val("");
	$("#encargoClienteUnidadesPedirCalendario").val("");
	$("#encargoClienteEspecialCalendario").val("");
	if ($("#p52_cmb_tipoPedidoAdicional").combobox("getValue") == "1"){
		$("#clasePedidoCalendario").val("E");
	}else{
		$("#clasePedidoCalendario").val("M");
	}
	if (nuevoPedidoRef != null && nuevoPedidoRef.frescoPuro != null){
		if (nuevoPedidoRef.frescoPuro){
			$("#esFresco").val("S");
		}else{
			$("#esFresco").val("N");
		}
	}
	$("#recargarParametrosCalendario").val("S");
	$("#cargadoDSCalendario").val("N");
}


/**
 * P49634
 * Modificacion para permitir la busqueda de referencias por denominacion en todas las opciones de combo.
 * MISUMI-376
 * Eliminar espacios en blanco por detras y por delante del texto de busqueda para validar que tenga mas de 4 caracteres.
 * @author BICUGUAL
 */
function p52getReferencia(){
	if (loadReferenciaActiva) {
		return;
	}
	loadReferenciaActiva = true;
	if (nuevoPedidoRef == null || nuevoPedidoRef.codArt != $("#p52_fld_referencia").val()){

		nuevoPedidoRef = null;
		if($("#p52_cmb_tipoPedidoAdicional").combobox("getValue") =="3"){//Encargos de cliente y vacía la referencia
			if ($("#p52_fld_referencia").val() != ""){
				if($.isNumeric($("#p52_fld_referencia").val())){
					getReferencia();
				} else {
					if (!$.isNumeric( $('#p52_fld_referencia').val() ) && $("#p52_fld_referencia").val().trim().length >= 4){
						reloadDataP64();
					} else {
						$("#p03_btn_aceptar").unbind("click");
						$("#p03_btn_aceptar").bind("click", function(e) {
							$("#p52_fld_referencia").focus();
							$("#p52_fld_referencia").select();
						});
						createAlert(invalidDenominacion, "ERROR");
						$("#p03_btn_aceptar").focus();
						loadReferenciaActiva = false;
					}
				}
			} else {
				reloadDataP64();
			}
		} 
		//PARA MONTAJE ADICIONAL Y ENCARGOS
		else {
			if ($("#centerId").val() != ''){
				if ($("#p52_cmb_tipoPedidoAdicional").combobox("getValue") != null && $("#p52_cmb_tipoPedidoAdicional").combobox("getValue") != ""){
					if ($("#p52_fld_referencia").val() != "" && isInt($("#p52_fld_referencia").val())){
						//getReferencia();
						//REFERENCIA ES NUMERICA
						var txtBusqueda=$("#p52_fld_referencia").val();
						//Si el texto de busqueda introducido es mayor de 5 realizo la busqueda

						var ean = false;
						if(txtBusqueda.length > 8){
							ean = true;
						}
						var pantallaDeBusqueda="nuevoPedidoAdicional";
						var tipoPedido = null;
						//Busqueda para Montajes
						if($("#p52_cmb_tipoPedidoAdicional").combobox("getValue") =="2"){
							pantallaDeBusqueda=pantallaDeBusqueda+"-montaje";
							tipoPedido="M";
							//Busqueda para Encargos
						}else if ($("#p52_cmb_tipoPedidoAdicional").combobox("getValue") =="1"){
							pantallaDeBusqueda=pantallaDeBusqueda+"-encargos";
							tipoPedido = "E";
						}
						reloadDataP34(txtBusqueda, pantallaDeBusqueda,tipoPedido,false, ean);
						loadReferenciaActiva = false;
						tabPressed = false;


//						//Si el texto de busqueda introducido es menor de 5 muestro mensaje
//						else {
//						$("#p03_btn_aceptar").unbind("click");
//						$("#p03_btn_aceptar").bind("click", function(e) {
//						$("#p52_fld_referencia").focus();
//						$("#p52_fld_referencia").select();
//						});
//						createAlert(invalidDenominacion, "ERROR");
//						$("#p03_btn_aceptar").focus();
//						loadReferenciaActiva = false;
//						}		

					} else if ($("#p52_fld_referencia").val() == ""){
						loadReferenciaActiva = false;
						allowed = true;
					} else {
						//REFERENCIA NO NUMERICA
						var txtBusqueda=$("#p52_fld_referencia").val();
						//Si el texto de busqueda introducido es mayor de 5 realizo la busqueda
						if (txtBusqueda.trim().length >= 4){

							var pantallaDeBusqueda="nuevoPedidoAdicional";
							var tipoPedido = null;
							//Busqueda para Montajes
							if($("#p52_cmb_tipoPedidoAdicional").combobox("getValue") =="2"){
								pantallaDeBusqueda=pantallaDeBusqueda+"-montaje";
								tipoPedido="M";
								//Busqueda para Encargos
							}else if ($("#p52_cmb_tipoPedidoAdicional").combobox("getValue") =="1"){
								pantallaDeBusqueda=pantallaDeBusqueda+"-encargos";
								tipoPedido = "E";
							}
							reloadDataP34(txtBusqueda, pantallaDeBusqueda,tipoPedido,true, false);
							loadReferenciaActiva = false;
							tabPressed = false;
						}

						//Si el texto de busqueda introducido es menor de 5 muestro mensaje
						else {
							$("#p03_btn_aceptar").unbind("click");
							$("#p03_btn_aceptar").bind("click", function(e) {
								$("#p52_fld_referencia").focus();
								$("#p52_fld_referencia").select();
							});
							createAlert(invalidDenominacion, "ERROR");
							$("#p03_btn_aceptar").focus();
							loadReferenciaActiva = false;
						}							
					}

				} else {
					$("#p03_btn_aceptar").unbind("click");
					$("#p03_btn_aceptar").bind("click", function(e) {
						$("#p52_cmb_tipoPedidoAdicional").focus();
						$("#p52_cmb_tipoPedidoAdicional").select();
					});
					createAlert(tipoPedidoRequired, "ERROR");
					$("#p52_fld_referencia").val("");
					$("#p03_btn_aceptar").focus();
					loadReferenciaActiva = false;

				}

			} else {
				//$("#p52_fld_referencia").val("");
				$("#p03_btn_aceptar").unbind("click");
				$("#p03_btn_aceptar").bind("click", function(e) {
					$("#centerName").focus();
				});
				createAlert(centerRequired, "ERROR");
				$("#p03_btn_aceptar").focus();
				loadReferenciaActiva = false;
			}
		} 
	}else if (cambioTipoPedido == true){
		getReferencia();
	} else {
		if (!$("#p52_div_cantidad1").is(':hidden')){
			$("#p52_fld_cantidad1").focus();
			$("#p52_fld_cantidad1").select();
		} else {
			$("#p52_fld_capMax").focus();
			$("#p52_fld_capMax").select();
		}
		loadReferenciaActiva = false;
	}
}


function p52ImplantacionInicial(){
	
	if ($("#p52_fld_capMax").val() == ""){
		nuevoPedidoRef.capacidadMaxima = null;
	} else {
		nuevoPedidoRef.capacidadMaxima = $("#p52_fld_capMax").val();
	}

	// Inicio MISUMI-409
	if (nuevoPedidoRef.capacidadMaxima != null && nuevoPedidoRef.cantidadMaxima != null	&& nuevoPedidoRef.cantidadMaxima > 0
		&& nuevoPedidoRef.capacidadMaxima > nuevoPedidoRef.cantidadMaxima){
		
		if (!$("#popupShowMessage").is(':visible')){
			let cantidadMaximaVegalsaNuevo = cantidadMaximaVegalsa+nuevoPedidoRef.cantidadMaxima;
			createAlert(cantidadMaximaVegalsaNuevo, "ERROR");
		}
	}else{
	// FIN MISUMI-409
		if (null != nuevoPedidoRef.capacidadMaxima){
			if ((null != nuevoPedidoRef.fechaIni) && (null != nuevoPedidoRef.fechaFin) && daysDiff() > 7 && !nuevoPedidoRef.tratamientoVegalsa){
				if (!impFinalModif){
					nuevoPedidoRef.implantacionMinima = Math.ceil(nuevoPedidoRef.capacidadMaxima * 0.2);
				}

			} else {
				nuevoPedidoRef.implantacionMinima = nuevoPedidoRef.capacidadMaxima;
			}
		} else {
			nuevoPedidoRef.implantacionMinima = nuevoPedidoRef.capacidadMaxima;
		}
		$("#p52_fld_impMin").val(nuevoPedidoRef.implantacionMinima);
		impFinalModif = false;
	}
	
}

function loadStockPlataformaP52(){
	var codReferencia;
	var mostrarTitle;
	codReferencia = nuevoPedidoRef.referenciaUnitaria;
	if (nuevoPedidoRef.codArt == nuevoPedidoRef.referenciaUnitaria){
		mostrarTitle = false;
	} else {
		mostrarTitle = true;
	}
	defineP59(codReferencia, mostrarTitle);
	$("#p59_AreaStock").dialog('open');
}

function getStockPlataformaP52(){
	var objJson = $.toJSON(nuevoPedidoRef.preparePedidoAdicionalToJsonObject());
	$.ajax({
		type : 'POST',
		url : './nuevoPedidoAdicionalReferencia/obtainStockPlataforma.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {	
			nuevoPedidoRef.stockPlataforma = data;
			populatePedidoAdicional();	

		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});	

}

function comprobarFechaEntregaDatosCliente(){
	if(nuevoPedidoRef.datosPedidoUnidadesPedir ) {
		$("#p52_fechaEntregaEncCliDatePicker").datepicker('enable');
		$("#p52_fld_clienteNombre").prop('disabled', false);
		$("#p52_fld_clientePrimerApellido").prop('disabled', false);
		$("#p52_fld_clienteTelefono").prop('disabled', false);
		$("#p52_fld_centroContacto").prop('disabled', false);
		$("#p52_fld_centroTelefono").prop('disabled', false);
		$("#p52_fld_clienteNombre").focus();
		$("#p52_fld_clienteNombre").select();
	} else {
		//Begin calendario encargos cliente
		// Cuando se deshabilita el calendario de encargos cliente hay que resetear el calendario al día de hoy
		p52CargaDatosCalendarioEncCli();

		$("#encargoClienteUnidadesPedirCalendario").val(nuevoPedidoRef.datosPedidoUnidadesPedir);
		$("#encargoClienteEspecialCalendario").val(nuevoPedidoRef.esEncargoEspecial);
		var fechaEntrega = new Date();
		if (nuevoPedidoRef.fechaIni){
			fechaEntrega = nuevoPedidoRef.fechaIni;
		}

		if (nuevoPedidoRef.esEncargoEspecial){
			$("#p52_fechaEntregaEncCliDatePicker").removeClass("encargosEspeciales");
		}
		$("#p52_fechaEntregaEncCliDatePicker").datepicker("setDate", fechaEntrega);
		$("#p52_fechaEntregaEncCliDatePicker").datepicker("refresh");
		//End calendario encargos cliente

		$("#p52_fechaEntregaEncCliDatePicker").datepicker('disable');
		$("#p52_fld_clienteNombre").prop('disabled', true);
		$("#p52_fld_clientePrimerApellido").prop('disabled', true);
		$("#p52_fld_clienteTelefono").prop('disabled', true);
		$("#p52_fld_centroContacto").prop('disabled', true);
		$("#p52_fld_centroTelefono").prop('disabled', true);
	}

}

function getEncargoClientePrimeraFechaEntrega(pPrimeraFechaEntrega, pFechasVenta){
	var resultado;
	if (pPrimeraFechaEntrega) {
		// Existe primera fecha de entrega
		resultado = pPrimeraFechaEntrega;
	}
	else{
		// Si no existe primera fecha de entrega
		if ( pFechasVenta && $.isArray(pFechasVenta) && pFechasVenta.length > 0 && $.isNumeric(pFechasVenta[0]) ){
			// Cogemos la primera fecha de venta a partir de hoy como primera fecha de entrega
			var fechaHoy = new Date();
			jQuery.each( pFechasVenta, function( i, val ) {
				var fechaVenta = new Date(val);
				if (fechaVenta > fechaHoy){
					resultado = val;
					return false;
				}
			});
		}
		else {
			resultado = null;
		}
	}
	if (resultado){
		//Guardamos el primeraFechaEntrega en un input hidden
		$("#encargoClientePrimFechaEntregaCalendario").val(resultado);
	}
	return resultado;
}

/**
 * P49634
 * Sobre escribe el metodo que existe para las busquedas por descripcion
 * existente en los js de busquedas llamando al metodo que se encarga de 
 * buscar referencias.
 * @author BICUGUAL
 */
function findReferenciaP52(){
	p52getReferencia();
}

function redirigirLogin() {
	window.location='./login.do';
}	

function loadP52Foto(codArtEroski){
	if ($("#p52_fld_tieneFoto").val() == "S"){
		var url = "./welcome/getImage.do?codArticulo="+codArtEroski;
		$('#p52_img_referencia').attr('src',url);
		$('#p52_img_referencia').attr("onerror", "redirigirLogin()");
		$("#p52_AreaFotos").show();
	} else {
		//Quitamos el onerror para que al hacer (src,'') no
		//nos tire del aplicativo.
		$('#p52_img_referencia').attr("onerror","");
		$('#p52_img_referencia').attr('src','');
		$("#p52_AreaFotos").hide();
	}			
}

function event_p52_ayuda(){
	$("#p52_linkAyuda").click(function(event){
		//Si ya se ha cargado la información de la referencia, se abre el dialogo sin más.
		//Si no se ha cargado, se busca y se pone el flag a true para que las siguientes veces
		//no haga falta buscar en esas referencias.
		if(dialogoCargado){
			$("#p56_AreaAyuda").dialog("open");
		}else{
			loadPopUpAyudaP52(event);
			dialogoCargado = true;
		}
	});
}