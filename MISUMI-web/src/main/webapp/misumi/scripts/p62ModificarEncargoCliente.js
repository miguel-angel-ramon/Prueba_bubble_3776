
var pedidoAdicionalEC = null;
var p62SelectCantidad = false;
var p62SelectPesoDesde = false;
var p62SelectPesoHasta = false;
var fechaVentaPantalla = '';
var p62pedidoModificable = null;
var p62CantidadRequired;
var p62PesoDesdeRequired;
var p62PesoHastaRequired;
var p62DescripcionRequired;
var p62PesoDesdeIncorrect;
var p62PesoHastaIncorrect;
var p62PesoDesdeMayor;
var p62invalidTelefonoCliente;
var p62noClientName;
var p62noClientSurname;
var p62noTelephoneClient;
var p62noCenterContact; 
var p62esGenerica;
var p62Foco = null;
var p62primeraFechaEntrega = null;
var p62fechaVentaGuardada = null;
var p62AvisoPedidoEspecial=null;
//Flag utilizado para comprobar si a finalizado el recalculo de calendarios
var recalculoFinalizado=true;

function initializeP62(){

	initializeScreenP62();
	load62Literales(locale);
	$("#p62_AreaModificacion").keydown(function(event) {
		if(event.which == 13) {
			var userPerfil = $("#userPerfil").val();
			if (userPerfil == "3"){
				p62ControlesCierrePopUp($(this));
			}else{	
				var $focused = $(event.target);
				var id = $focused.attr("id");
				if ("p62_fld_especificacion" == id) {
					pedidoAdicionalEC.especificacion = $("#p62_fld_especificacion").val();
					controlRecalculoCalendario();
				}else if ("p62_fld_pesoDesde" == id) {
					pedidoAdicionalEC.pesoDesde = $("#p62_fld_pesoDesde").val().replace(',','.');
					controlRecalculoCalendario();
				}else if ("p62_fld_pesoHasta" == id) {
					pedidoAdicionalEC.pesoHasta = $("#p62_fld_pesoHasta").val().replace(',','.');
					controlRecalculoCalendario();
				}
		        if ( !p62_Valid() ) {
	  		 		if (event.preventDefault) { event.preventDefault(); } else { event.returnValue = false; }
	  		 		return false;
	    		}
	        	p62GuardarPedido();
			}
		}        
    });
	$( "#p62_fld_cantidad").filter_input({regex:'[0-9,]'});
	$( "#p62_fld_pesoDesde").filter_input({regex:'[0-9,]'});
	$( "#p62_fld_pesoHasta").filter_input({regex:'[0-9,]'});

	events_p62_cantidad();
	events_p62_pesoDesde();
	events_p62_pesoHasta();
}

function initializeScreenP62(){

	
	p62CrearCalendarios();
	
	//Inicializamos el popup de modificación.
	var userPerfil = $("#userPerfil").val();
	if (userPerfil == "3"){
		$( "#p62_AreaModificacion" ).dialog({
	        autoOpen: false,
	        height: 610,
	        width: 980,
	        modal: true,
	        resizable: false,
	        buttons:[{
	            text: "Volver",
	            click: function() {
	            	p62ControlesCierrePopUp($(this));
	            }
	        }
	        ],
			open: function() {
				$('.ui-dialog-titlebar-close').on('mousedown', function(){
					p62ControlesCierrePopUpAspa();
				});
			}
	    });
	}else{
		$( "#p62_AreaModificacion" ).dialog({
	        autoOpen: false,
	        height: 610,
	        width: 980,
	        modal: true,
	        resizable: false,
	        buttons:[{
	            id:"p62_btn_guardar",
	        	text: "Guardar",
	            click: function(e) {
	            	// Validar el pop-up de modificacion
	        		if ( !p62_Valid() ) {
	      		 		if (e.preventDefault) { e.preventDefault(); } else { e.returnValue = false; }
	      		 		return false;
	        		}
	        		p62GuardarPedido();
	            }
	        },{
	            text: "Volver",
	            click: function() {
	            	p62ControlesCierrePopUp($(this));
	            }
	        }
	        ],
			open: function() {
				$('.ui-dialog-titlebar-close').on('mousedown', function(){
					p62ControlesCierrePopUpAspa();
				});
			}
	    });
	}		
	$("#p62_fld_especificacion").blur(function(e) {
		pedidoAdicionalEC.especificacion = $("#p62_fld_especificacion").val();
		controlRecalculoCalendario();
	});
	
	$("#p62_fld_cantidad").blur(function(e) {
		pedidoAdicionalEC.cantEncargo = $("#p62_fld_cantidad").val().replace(',','.');
	});
	
	$("#p62_fld_pesoDesde").blur(function(e) {
		pedidoAdicionalEC.pesoDesde = $("#p62_fld_pesoDesde").val().replace(',','.');
		controlRecalculoCalendario();
	});

	$("#p62_fld_pesoHasta").blur(function(e) {
		pedidoAdicionalEC.pesoHasta = $("#p62_fld_pesoHasta").val().replace(',','.');
		controlRecalculoCalendario();
	});
}

function controlRecalculoCalendario(){
	var esEncargoEspecial = $("#encargoClienteEspecialCalendario").val();
	var recalcularCalendario = false;
	if ((pedidoAdicionalEC.pesoDesde != null && pedidoAdicionalEC.pesoDesde != "") || (pedidoAdicionalEC.pesoHasta != null && pedidoAdicionalEC.pesoHasta != "") || (pedidoAdicionalEC.especificacion != null && pedidoAdicionalEC.especificacion != "")) {
		if (esEncargoEspecial != "true"){
			recalcularCalendario = true;
		}
		$("#encargoClienteEspecialCalendario").val("true");
		pedidoAdicionalEC.tipoEncargo = 'E';
		$("#p62_fechaEntregaEncCliDatePicker").removeClass("encargosEspeciales");
		
		gestionAvisoPedidoEspecial(true);
	}
	else {
		if (esEncargoEspecial != "false"){
			recalcularCalendario = true;
		}
		$("#encargoClienteEspecialCalendario").val("false");
		pedidoAdicionalEC.tipoEncargo = 'N';
		
		gestionAvisoPedidoEspecial(false);
	}
	
	if (recalcularCalendario)
			recalculoFinalizado=false;
	
	setTimeout(function() {
		
		if (recalcularCalendario){
			//Carga de datos para los calendarios
			$("#codCentroCalendario").val($("#centerId").val());
			$("#codArticuloCalendario").val($("#p62_fld_referencia").val());
			$("#identificadorCalendario").val("");
			$("#identificadorSIACalendario").val("");
			$("#clasePedidoCalendario").val("EC");
			$("#recargarParametrosCalendario").val("S");
			$("#cargadoDSCalendario").val("S");
			$("#encargoClienteFechaVentaModificada").val("S");
			$("#encargoClienteUnidadesPedirCalendario").val($("#p62_fld_cantidad").val());
			
			if (pedidoAdicionalEC.tipoEncargo=="E"){
	   		 	$("#p62_fechaEntregaEncCliDatePicker").removeClass("encargosEspeciales");
	   		 	//Se pone la fecha de venta a la primera fecha de entrega como minimo por estar las anteriores deshabilitadas
	   		 	if (new Date(p62primeraFechaEntrega) > pedidoAdicionalEC.fechaVenta){
	   		 		$("#p62_fechaEntregaEncCliDatePicker").datepicker( "setDate", new Date(p62primeraFechaEntrega));
	   		 		$('#p62_lbl_cantidad').text(getFechaFormateada_D_d(p62primeraFechaEntrega, 'D d'));
	   		 	}
	   	 	}else{
	   	 		if (new Date(p62primeraFechaEntrega) > pedidoAdicionalEC.fechaVenta){
	 				$("#p62_fechaEntregaEncCliDatePicker").addClass("encargosEspeciales");
	 			} else {
	 				$("#p62_fechaEntregaEncCliDatePicker").removeClass("encargosEspeciales");
	 			} 
	   	 	}    
			$("#p62_fechaEntregaEncCliDatePicker").datepicker("refresh");
			pedidoAdicionalEC.fechaVenta = $("#p62_fechaEntregaEncCliDatePicker").datepicker("getDate");
			
			//Acabada la operacion de recalculo habilito de nuevo el boton de guardar
			recalculoFinalizado=true;
		}
		
	}, 200);
	
}
/**
 * P-51880
 * @author BICUGUAL
 * Gestion para mostrar u ocular aviso con fecha minima para los pedidos que tienen especificaciones.
 * @param visible
 */
function gestionAvisoPedidoEspecial(visible){
	
	var fechaAviso=getFechaFormateada_D_d(p62primeraFechaEntrega, 'D d M');
	
	if (visible) 
		$('#p62_bloque3EncargoClienteTexto').text(p62AvisoPedidoEspecial+" "+fechaAviso).show();
	else
		$('#p62_bloque3EncargoClienteTexto').hide();
	
}
/**
 * P-51880
 * @author BICUGUAL
 * Devuelve la fecha pasada en formato deseado
 * @param fecha
 * @param patron
 * @returns
 */
function getFechaFormateada_D_d(fecha, patron){
	var fechaFormateada= $.datepicker.formatDate(patron,new Date(fecha),{
		dayNamesShort: $.datepicker.regional[ "es" ].dayNamesMin,
		dayNames: $.datepicker.regional[ "es" ].dayNames,
		monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
		monthNames: $.datepicker.regional[ "es" ].monthNames
		});
	return fechaFormateada;
}

function p62ControlesCierrePopUp(elem){
	p62LimpiarCalendarios();
	elem.dialog('close');
}

function p62ControlesCierrePopUpAspa(){
	p62LimpiarCalendarios();
}

function p62LimpiarCalendarios(){
	$("#p62_fechaEntregaEncCliDatePicker").datepicker("destroy");
}

function p62CrearCalendarios(){
	
	//Se obtiene el valor de recargarParametrosCalendario para replicarlo para todos los calendarios
	//var recargarParametrosCalendario = $("#recargarParametrosCalendario").val();
	$.datepicker.setDefaults($.datepicker.regional['esDiasServicio']);
	//$("#recargarParametrosCalendario").val(recargarParametrosCalendario);
	$("#p62_fechaEntregaEncCliDatePicker").datepicker( "option", $.datepicker.regional[ 'esDiasServicio' ] );
	$( "#p62_fechaEntregaEncCliDatePicker" ).datepicker({
        onSelect: function(dateText, inst) {
        	 pedidoAdicionalEC.fechaVenta = $("#p62_fechaEntregaEncCliDatePicker").datepicker("getDate");
        	 var fechaVentaFormateada = $.datepicker.formatDate('D d', $("#p62_fechaEntregaEncCliDatePicker").datepicker("getDate"),{
					dayNamesShort: $.datepicker.regional[ "es" ].dayNamesMin,
					dayNames: $.datepicker.regional[ "es" ].dayNames,
					monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
					monthNames: $.datepicker.regional[ "es" ].monthNames
					});		 
        	
        	 if (pedidoAdicionalEC.tipoEncargo=="E"){
        		 $("#p62_fechaEntregaEncCliDatePicker").removeClass("encargosEspeciales");
        	 }else{
	        	 if (new Date(p62primeraFechaEntrega) > pedidoAdicionalEC.fechaVenta){
	  				$("#p62_fechaEntregaEncCliDatePicker").addClass("encargosEspeciales");
	  			 } else {
	  				$("#p62_fechaEntregaEncCliDatePicker").removeClass("encargosEspeciales");
	  			 } 
        	 }        	 
        	 $('#p62_lbl_cantidad').text(fechaVentaFormateada);
        }
	}).prop({
		   disableBeforeDay:'S',
		   dayControl: new Date()
    });
}

function p62DefinePedidoAdicionalEC(data){

	pedidoAdicionalEC = new PedidoAdicionalEC (data.localizador, data.codLoc, data.contactoCentro, data.nombreCliente, data.apellidoCliente, data.telefonoCliente, 
			data.tipoEncargo, data.fechaVenta, data.fechaVentaModificada, data.codArtFormlog, data.denomArticulo, data.especificacion, data.pesoDesde, data.pesoHasta, 
			data.confirmarPrecio, data.cantEncargo, data.estado, data.observacionesMisumi, data.codigoPedidoInterno, data.tipoAprov, new Date(p62primeraFechaEntrega), null);
	pedidoAdicionalEC.flgEspec = data.flgEspec;
	fechaVentaPantalla = '';
	p62pedidoModificable = data.flgModificable;
	
	$("#p62_fechaEntregaEncCliDatePicker").datepicker( "setDate", new Date(data.fechaVentaPantalla.substring(4), data.fechaVentaPantalla.substring(2,4) - 1, data.fechaVentaPantalla.substring(0,2)));
	$("#p62_fechaEntregaEncCliDatePicker").datepicker("refresh");
	pedidoAdicionalEC.fechaVenta = $("#p62_fechaEntregaEncCliDatePicker").datepicker("getDate");	
	p62fechaVentaGuardada = pedidoAdicionalEC.fechaVenta;
}

function p62GuardarPedido(){
	if (p62pedidoModificable != null && "S"==p62pedidoModificable){
		pedidoAdicionalEC.cantEncargo = $("#p62_fld_cantidad").val().replace(',','.');
		if($("#p62_fld_pesoDesde").is(':visible')){			
			pedidoAdicionalEC.pesoDesde = $("#p62_fld_pesoDesde").val().replace(',','.');
		}
		if($("#p62_fld_pesoHasta").is(':visible')){
			pedidoAdicionalEC.pesoHasta = $("#p62_fld_pesoHasta").val().replace(',','.');
		}
		pedidoAdicionalEC.especificacion = $("#p62_fld_especificacion").val();
	}
	pedidoAdicionalEC.nombreCliente = $("#p62_fld_clienteNombre").val();
	pedidoAdicionalEC.apellidoCliente = $("#p62_fld_clienteApellido").val();
	pedidoAdicionalEC.telefonoCliente = $("#p62_fld_clienteTelefono").val();
	pedidoAdicionalEC.contactoCentro = $("#p62_fld_centroContacto").val();
	
	/**
	 *  P-51880
	 * @author BICUGUAL
	 * Para evitar el error al guardar metiendo solo descripcion guardar por el settimeout del recalculo de calendario
	 * He creado una variable global a la que informo si la operacion a terminado.
	 * En caso negativo, le doy tiempo a que acabe antes de realizar la operacion de guardado.
	 */
	if (!recalculoFinalizado){
		setTimeout(function() {
			p62ModifyPedido();
		},300);
	}
	else		
		p62ModifyPedido();
	
}

function p62ModifyPedido(){
	
	var objJson = $.toJSON(pedidoAdicionalEC.preparePedidoAdicionalECToJsonObject());
	$.ajax({
		type : 'POST',
		url : './modificarEncargoCliente/modifyPedido.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		success : function(data) {	
			if (data != null && data != '' && data.codigoError != 8){
				//Si pasa por aquí se ha producido 
				//un error al modificar un encargo cliente.
				
				//Saco el mensaje de alerta
				var codError = data.codigoError;
				var descError = data.descripcionError;
				createAlert(replaceSpecialCharacters(descError), "ERROR");
				

				if (data.id != null && data.id != ''){
					//Refresco el popup con los datos ya modificados
					p62primeraFechaEntrega = p62getEncargoClientePrimeraFechaEntrega(data.primeraFechaEntrega, data.fechasVenta);
					
					if(data.fechaVenta != data.fechaVentaModificada){
						$("#encargoClienteFechaVentaModificadaCalendario").val(data.fechaVentaModificada);
					}else{
						$("#encargoClienteFechaVentaModificadaCalendario").val("");
					}
					
					p47CargaDatosCalendarios(data);
					
					p62CrearCalendarios();
					
					p62DefinePedidoAdicionalEC(data);
					
					//Primero cargamos los datos.
					p47CargaDatos(data);

				    if (pedidoAdicionalEC.tipoEncargo=="E"){
			        		 $("#p62_fechaEntregaEncCliDatePicker").removeClass("encargosEspeciales");
			        }else{
						if (new Date(p62primeraFechaEntrega) > new Date(data.fechaVentaPantalla.substring(4), data.fechaVentaPantalla.substring(2,4) - 1, data.fechaVentaPantalla.substring(0,2))){
			 				$("#p62_fechaEntregaEncCliDatePicker").addClass("encargosEspeciales");
			 			} else {
			 				$("#p62_fechaEntregaEncCliDatePicker").removeClass("encargosEspeciales");
			 			}
			        }						
					//Ahora establecemos los campos que son modificables
					p47ControlModificables(data);
				}
				else{
					//Si pasa por aquí el modificar encargo cliente ha dado error
					//pero no ha devuelto los datos para pintar la pantalla de modificación,
					//por eso se cierra la pantalla de modificación.
					p62LimpiarCalendarios();
					$("#p62_AreaModificacion").dialog( "close" );
				}
			} else{
				//Si pasa por aquí el encargo cliente
				//se ha modificado correctamente.
				p62LimpiarCalendarios();
				$("#p62_AreaModificacion").dialog( "close" );
			}
			reloadDataP47Modificacion();
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
        }			
	})
}

function load62Literales(locale){
	
	var jqxhr = $.getJSON('./misumi/resources/p62ModificarEncargoCliente/p62modificarEncargoCliente_' + locale + '.json',
			function(data) {
											
			})
			.success(function(data) {
				p62CantidadRequired = data.p62CantidadRequired;
				p62PesoDesdeRequired = data.p62PesoDesdeRequired;
				p62PesoHastaRequired = data.p62PesoHastaRequired;
				p62DescripcionRequired = data.p62DescripcionRequired;
				p62PesoDesdeIncorrect = data.p62PesoDesdeIncorrect;
				p62PesoHastaIncorrect = data.p62PesoHastaIncorrect;
				p62PesoDesdeMayor = data.p62PesoDesdeMayor;
				p62invalidTelefonoCliente = data.p62invalidTelefonoCliente;
				p62noClientName = data.p62noClientName;
				p62noClientSurname = data.p62noClientSurname;
				p62noTelephoneClient = data.p62noTelephoneClient;
				p62noCenterContact = data.p62noCenterContact;
				p62AvisoPedidoEspecial =data.p62AvisoPedidoEspecial;
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
         });
}

function events_p62_cantidad(){
	$("#p62_fld_cantidad").on("keydown", function(e) {
		var unidadesPedir = $(this).val();
		var esTeclaNumero = (e.which == 48 || e.which == 49 || e.which == 50 || e.which == 51 || e.which == 52 || e.which == 53 || e.which == 54 || e.which == 55 || e.which == 56 || e.which == 57);
		var esTeclaComa = (e.which == 188);
		if (esTeclaNumero || esTeclaComa){
			var lengthUnidadesPedir = unidadesPedir.length;
			var esVacioUnidadesPedir = (lengthUnidadesPedir == 0);
			if (esTeclaComa && esVacioUnidadesPedir){
				if (e.preventDefault) { e.preventDefault(); } else { e.returnValue = false; }
		 		return false;
			}
			var posicionComa = unidadesPedir.indexOf(',');
			var esDouble = (posicionComa != -1);
			if (esDouble){
				decimalesUnidadesPedir = unidadesPedir.substring(posicionComa+1, lengthUnidadesPedir);
				if (decimalesUnidadesPedir.length >= 2 || esTeclaComa){
					if (!p62SelectCantidad){
						if (e.preventDefault) { e.preventDefault(); } else { e.returnValue = false; }
				 		return false;
					}
					else{
						p62SelectCantidad = false;
					}
				}
			}
		}
		p62SelectCantidad = false;
	});
	$("#p62_fld_cantidad").on("select", function(e) {
		p62SelectCantidad = true;
	});
}

function events_p62_pesoDesde(){
	
	
	$("#p62_fld_pesoDesde").on("keydown", function(e) {
		var pesoDesde = $(this).val();
		var esTeclaNumero = (e.which == 48 || e.which == 49 || e.which == 50 || e.which == 51 || e.which == 52 || e.which == 53 || e.which == 54 || e.which == 55 || e.which == 56 || e.which == 57);
		var esTeclaComa = (e.which == 188);
		if (esTeclaNumero || esTeclaComa){
			var posicionComa = pesoDesde.indexOf(',');
			var esDouble = (posicionComa != -1);
			if (esDouble){
				decimalesPesoDesde = pesoDesde.substring(posicionComa+1, pesoDesde.length);
				if (decimalesPesoDesde.length >= 2 || esTeclaComa){
					if (!p62SelectPesoDesde){
						if (e.preventDefault) { e.preventDefault(); } else { e.returnValue = false; }
				 		return false;
					}
					else{
						p62SelectPesoDesde = false;
					}
				}
			}
		}
		p62SelectPesoDesde = false;
	});
	$("#p62_fld_pesoDesde").on("select", function(e) {
		p62SelectPesoDesde = true;
	});
}

function events_p62_pesoHasta(){
	$("#p62_fld_pesoHasta").focus(
		function(){
			if ($(this).val() == ""){
				$( "#p62_fld_pesoHasta" ).val( $("#p62_fld_pesoDesde").val() );
			}
	});
	$("#p62_fld_pesoHasta").on("keydown", function(e) {
		var pesoHasta = $(this).val();
		var esTeclaNumero = (e.which == 48 || e.which == 49 || e.which == 50 || e.which == 51 || e.which == 52 || e.which == 53 || e.which == 54 || e.which == 55 || e.which == 56 || e.which == 57);
		var esTeclaComa = (e.which == 188);
		if (esTeclaNumero || esTeclaComa){
			var posicionComa = pesoHasta.indexOf(',');
			var esDouble = (posicionComa != -1);
			if (esDouble){
				decimalesPesoHasta = pesoHasta.substring(posicionComa+1, pesoHasta.length);
				if (decimalesPesoHasta.length >= 2 || esTeclaComa){
					if (!p62SelectPesoHasta){
						if (e.preventDefault) { e.preventDefault(); } else { e.returnValue = false; }
				 		return false;
					}
					else{
						p62SelectPesoHasta = false;
					}
				}
			}
		}
		p62SelectPesoHasta = false;
	});
	$("#p62_fld_pesoHasta").on("select", function(e) {
		p62SelectPesoHasta = true;
	});
}

function p62getEncargoClientePrimeraFechaEntrega(pPrimeraFechaEntrega, pFechasVenta){
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

function p62_Valid(){
	var valid = true;
	var messageVal = p62_findValidation();
	if (messageVal != null){
		$("#p03_btn_aceptar").unbind("click");
		$("#p03_btn_aceptar").bind("click", function(e) {
				$(p62Foco).focus();
				$(p62Foco).select();
			 });
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
		valid = false;
		$("#p03_btn_aceptar").focus();
	}

	return valid;
}

function p62_findValidation(){
	var messageVal=null;
	if (p62pedidoModificable != null && "S"==p62pedidoModificable && ($( "#p62_fld_cantidad" ).val() == null || $( "#p62_fld_cantidad" ).val() == "")){
		p62Foco = "#p62_fld_cantidad";
		messageVal = p62CantidadRequired;
	}else if ( p62pedidoModificable != null && "S"==p62pedidoModificable && p62_esGenerica() && p62_getDescripcion() == ""){
		messageVal = p62DescripcionRequired;
		p62Foco = "#p62_fld_especificacion";
	}else if(!$("#p62_fld_clienteNombre").val() ){
		messageVal = p62noClientName;
		p62Foco = "#p62_fld_clienteNombre";
	}else if(!$("#p62_fld_clienteApellido").val() ){
		messageVal = p62noClientSurname;
		p62Foco = "#p62_fld_clienteApellido";
	}else if(!$("#p62_fld_clienteTelefono").val() ){
		messageVal = p62noTelephoneClient;
		p62Foco = "#p62_fld_clienteTelefono";
	}else if(!p62validarTelefono($("#p62_fld_clienteTelefono").val()) ){
		messageVal = p62invalidTelefonoCliente;
		p62Foco = "#p62_fld_clienteTelefono";
	}else if(!$("#p62_fld_centroContacto").val() ){
		messageVal = p62noCenterContact;
		p62Foco = "#p62_fld_centroContacto";
	}else if (p62pedidoModificable != null && "S"==p62pedidoModificable && $("#p62_fld_pesoDesde").is(':visible')){
		if ($( "#p62_fld_pesoDesde" ).val() == "" && $( "#p62_fld_pesoHasta" ).val() != ""){
			messageVal = p62PesoDesdeRequired;
			p62Foco = "#p62_fld_pesoDesde";
		}else if (!p62validarPesos($( "#p62_fld_pesoDesde" ).val())){
			messageVal = p62PesoDesdeIncorrect;
			p62Foco = "#p62_fld_pesoDesde";
		}else if ($( "#p62_fld_pesoHasta" ).val() == "" && $( "#p62_fld_pesoDesde" ).val() != ""){
			messageVal = p62PesoHastaRequired;
			p62Foco = "#p62_fld_pesoHasta";
		}else if (!p62validarPesos($( "#p62_fld_pesoHasta" ).val())){
			messageVal = p62PesoHastaIncorrect;
			p62Foco = "#p62_fld_pesoHasta";
		} else if ($( "#p62_fld_pesoDesde" ).val() != "" && $( "#p62_fld_pesoHasta" ).val() != "" && parseFloat($( "#p62_fld_pesoDesde" ).val().replace(',','.')) > parseFloat($( "#p62_fld_pesoHasta" ).val().replace(',','.'))){
			messageVal = p62PesoDesdeMayor;
			p62Foco = "#p62_fld_pesoDesde";
		}
	}
	return messageVal;
}

function p62_esGenerica() {
	if ($("#p62_fld_generica").val()=="S") {
		return true;
	}
	else {
		return false;
	}
}

function p62_getDescripcion(){
	return $( "#p62_fld_especificacion" ).val();
}

function p62validarTelefono(telefono){
	
	if (isNaN(telefono) || telefono.length < 9){
		return false;
	} else {
		return true;
	}
}

function p62validarPesos(cantidad){

	if (cantidad != ""){ 
		var cantidadPunto = cantidad.replace(',','.');
	
		if (isNaN(parseFloat(cantidadPunto)) || cantidad.split(",").length > 2){
			return false;
		} else {
			return true;
		}
	}else{
		return true;
	}
}