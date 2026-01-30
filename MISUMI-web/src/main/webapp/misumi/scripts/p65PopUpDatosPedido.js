var p65BotonLimpiar = null;
var p65BotonAceptar = null;
var p65BotonVolver = null;
var p65UnidadesPedirRequired;
var p65OtroPesoRequired;
var p65PesoDesdeRequired;
var p65PesoHastaRequired;
var p65DescripcionRequired;
var p65OtroPesoIncorrect;
var p65PesoDesdeIncorrect;
var p65PesoHastaIncorrect;
var p65PesoDesdeMayor;
var p65Ayuda;
var p65PedidoAdicionalCompleto;
var p65SelectUnidadesPedir = false;
var p65SelectOtroPeso = false;
var p65SelectPesoDesde = false;
var p65SelectPesoHasta = false;
var p65Foco = null;

$(document).ready(function(){
	loadP65(locale);
});

function initializeScreenP65PopupDatosPedido(){
	// Campos numéricos con decimales
	$( "#p65_fld_unidadesPedir").filter_input({regex:'[0-9,]'});
	$( "#p65_fld_otroPeso").filter_input({regex:'[0-9,]'});
	$( "#p65_fld_pesoDesde").filter_input({regex:'[0-9,]'});
	$( "#p65_fld_pesoHasta").filter_input({regex:'[0-9,]'});
	// Inicializar eventos de los campos del pop-up
	events_p65_unidadesPedir();
	events_p65_radioPeso();
	events_p65_otroPeso();
	events_p65_pesoDesde();
	events_p65_pesoHasta();
	// Abrir el pop-up de datos pedido
	$( "#p65_popupDatosPedido" ).dialog({
        autoOpen: false,
        height: 410,
        width: 760,
        modal: true,
        resizable: false,
        buttons:[{
        	text: p65BotonLimpiar,
        	click: function(e) {
        		// Limpiar el pop-up de datos pedido
        		reset_p65();
    			$( "#p65_fld_unidadesPedir" ).focus();
        	}
        },{
        	text: p65BotonAceptar,
        	click: function(e) {
        		// Validar el pop-up de datos pedido
        		if ( !p65_Valid() ) {
      		 		if (e.preventDefault) { e.preventDefault(); } else { e.returnValue = false; }
      		 		return false;
        		}
        		
        		p65_getDatosPedido();
        		$(this).dialog('close');
        		// Copiar el campo unidades pedir del pop up sobre el campo de la pantalla principal y lanzar el evento change
        		$( "#p52_fld_cantidad_enc_cli" ).val( p65_getUnidadesPedir() ).change();
    			comprobarFechaEntregaDatosCliente();
    			$( "#p52_fld_clienteNombre" ).focus();
    			$( "#p52_fld_clienteNombre" ).select();
    			setLabelDescPedido();
        	}
        },{
        	text: p65BotonVolver,
        	click: function() {
        		reset_p65();
        		$(this).dialog('close');
        		// Devolver el foco sobre la referencia o sobre el Nombre del cliente
        		if ($( "#p52_fld_cantidad_enc_cli" ).val() == ""){
        			$( "#p52_fld_referencia" ).focus();
        			$( "#p52_fld_referencia" ).select();
        		}
        		else{
        			$( "#p52_fld_clienteNombre" ).focus();
        			$( "#p52_fld_clienteNombre" ).select();
        		}
        	}
        }],
		open: function() {
			//Deshabilitar el campo U/C
			$( "#p65_fld_uc" ).prop('disabled', true);
			//Cuando se clique sobre la X cerrar ventana
			$( '.ui-dialog-titlebar-close' ).on('mousedown', function(){
				$( "#p65_popupDatosPedido" ).dialog('close');
			});
			
			// MISUMI-323
			if (esPedidoEspecial(p65PedidoAdicionalCompleto.flgEspec)){
				$("#p65_AreaBloque1").insertAfter("#p65_AreaBloque2");
				checkDescripcionPedidoEspecial();
			}else{
				$("#p65_div_uc").show();
				$("#p65_AreaBloque2").insertAfter("#p65_AreaBloque1");
			}
			
			
			setLabelCantidadAPedir(p65PedidoAdicionalCompleto.formatoArticulo);
			
			$("#p65_fld_descripcion").keyup(function(){
				if (esPedidoEspecial(p65PedidoAdicionalCompleto.flgEspec)){
					checkDescripcionPedidoEspecial();
				}
			});
		}
    });
	
	$(window).bind('resize', function() {
	    $( "#p65_popupDatosPedido" ).dialog("option", "position",  { my: "left top", at: "left bottom", of: window });
	});
	 $(".controlReturnP65").on("keydown", function(e) {
	    	if (e.which == 13) {
	    		// Validar el pop-up de datos pedido
        		if ( !p65_Valid() ) {
      		 		if (e.preventDefault) { e.preventDefault(); } else { e.returnValue = false; }
      		 		return false;
        		}
        		p65_getDatosPedido();
        		$( "#p65_popupDatosPedido" ).dialog('close');
        		// Copiar el campo unidades pedir del pop up sobre el campo de la pantalla principal y lanzar el evento change
        		$( "#p52_fld_cantidad_enc_cli" ).val( p65_getUnidadesPedir() ).change();
    			comprobarFechaEntregaDatosCliente();
    			setLabelDescPedido();
	    	}
	  	    
	  	});
	
	$("#p65_fld_uc").attr("disabled", "disabled");
}

function checkDescripcionPedidoEspecial(){
	if ($("#p65_fld_descripcion").val().length>0){
		$("#p65_div_uc").hide();
		$("#p65_lbl_unidadesPedir").text("PIEZAS");
	}else{
		$("#p65_div_uc").show();
		setLabelCantidadAPedir(p65PedidoAdicionalCompleto.formatoArticulo);
	}
}

function setLabelDescPedido(){
	var cantidad = $("#p65_fld_unidadesPedir").val();
	var formato = $("#p65_lbl_unidadesPedir").text();
	var denominacion = $("#p52_fld_denominacionEroski").val();
	
	var descPedido = "Has pedido "+cantidad +" " + formato + " de " + denominacion;
	$( "#p52_lbl_descPedido").text(descPedido);
}

function setLabelCantidadAPedir(formatoArticulo){
	if (formatoArticulo == "K"){
		$("#p65_lbl_unidadesPedir").text("KILOS");
		$("#p65_lbl_uc").text("Kilos/Caja");
	}else if (formatoArticulo == "P"){
		$("#p65_lbl_unidadesPedir").text("BANDEJAS");
		$("#p65_lbl_uc").text("Bandejas/Caja");
	}else{
		$("#p65_lbl_unidadesPedir").text("UNIDADES");
		$("#p65_lbl_uc").text("Unidades/Caja");
	}
}

function reset_p65(){
	$( "#p65_fld_unidadesPedir" ).val('');
	$( "input:radio[name='p65_rad_peso']").prop('checked', false);
	$( "#p65_fld_otroPeso" ).val('');
	$( "#p65_fld_otroPeso").attr("disabled", "disabled");
	$( "#p65_fld_pesoDesde" ).val('');
	$( "#p65_fld_pesoDesde").attr("disabled", "disabled");
	$( "#p65_fld_pesoHasta" ).val('');
	$( "#p65_fld_pesoHasta").attr("disabled", "disabled");
	$( "#p65_fld_descripcion" ).val('');
}



function loadP65(locale){
	var jqxhr = $.getJSON('./misumi/resources/p65PopUpDatosPedido/p65PopUpDatosPedido_' + locale + '.json',
			function(data) {
											
			})
			.success(function(data) {
				p65BotonLimpiar = data.p65BotonLimpiar;
				p65BotonAceptar = data.p65BotonAceptar;
				p65BotonVolver = data.p65BotonVolver;
				p65UnidadesPedirRequired = data.p65UnidadesPedirRequired;
				p65OtroPesoRequired = data.p65OtroPesoRequired;
				p65PesoDesdeRequired = data.p65PesoDesdeRequired;
				p65PesoHastaRequired = data.p65PesoHastaRequired;
				p65DescripcionRequired = data.p65DescripcionRequired;
				p65OtroPesoIncorrect = data.p65OtroPesoIncorrect;
				p65PesoDesdeIncorrect = data.p65PesoDesdeIncorrect;
				p65PesoHastaIncorrect = data.p65PesoHastaIncorrect;
				p65PesoDesdeMayor = data.p65PesoDesdeMayor;
				p65Ayuda = data.p65Ayuda;
				initializeScreenP65PopupDatosPedido();
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
        });
	events_p65_btn_ayuda();
}


function events_p65_btn_ayuda(){
	$( "#p65_btn_ayudaUnidadesPedir" ).click(function () {
   		createAlert(replaceSpecialCharacters(p65Ayuda), "HELP", 600);
	});
}

function esPedidoEspecial(flgEspec){
	return (flgEspec == null || flgEspec == "" || flgEspec == "S");
}
	
function reloadDataP65(p65NuevoPedidoRef){
	console.log("reloadDataP65");
	console.log(p65NuevoPedidoRef);
	reset_p65();
	if (p65NuevoPedidoRef instanceof PedidoAdicionalCompleto) {
		p65PedidoAdicionalCompleto = p65NuevoPedidoRef;
		if (esPedidoEspecial(p65NuevoPedidoRef.flgEspec)){
			$( "#p65_AreaBloqueEspecificacion" ).show();
			$( "#p65_AreaBloqueNoEspecificacion" ).hide();
			if ( p65_esGenerica() || !p65_esCarnePescado() ) {
				// Se ocultan los campos de pesos
				$( "#p65_AreaBloque2_1Titulo" ).hide();
				$( "#p65_AreaBloque2_1Datos" ).hide();
			}
			else {
				// Se muestran los campos de pesos
				$( "#p65_AreaBloque2_1Titulo" ).show();
				$( "#p65_AreaBloque2_1Datos" ).show();
			}
			
			var peso = p65PedidoAdicionalCompleto.datosPedidoRadioPeso;
			if(peso){
				$( "input[name='p65_rad_peso'][value='" + peso + "']").prop('checked', true);
			}
			if (p65PedidoAdicionalCompleto.datosPedidoRadioPeso == "Otro"){
				$( "#p65_fld_otroPeso").attr("disabled", false);
				p65_setOtroPeso(p65PedidoAdicionalCompleto.datosPedidoPesoDesde);
			}
			else if (p65PedidoAdicionalCompleto.datosPedidoRadioPeso == "DesdeHasta"){
				$( "#p65_fld_pesoDesde").attr("disabled", false);
				$( "#p65_fld_pesoHasta").attr("disabled", false);
				p65_setPesoDesde(p65PedidoAdicionalCompleto.datosPedidoPesoDesde);
				p65_setPesoHasta(p65PedidoAdicionalCompleto.datosPedidoPesoHasta);
			}
			p65_setDescripcion(p65PedidoAdicionalCompleto.datosPedidoDescripcion);
		} else {
			$( "#p65_AreaBloqueEspecificacion" ).hide();
			$( "#p65_AreaBloqueNoEspecificacion" ).show();
		}
		$( "#p65_fld_uc" ).val($.formatNumber(p65PedidoAdicionalCompleto.uniCajas,{format:"0.##",locale:"es"}));
		p65_setUnidadesPedir(p65PedidoAdicionalCompleto.datosPedidoUnidadesPedir);
	}
	$( "#p65_popupDatosPedido" ).dialog( "open" );
}

function p65_getUnidadesPedir(){
	return $( "#p65_fld_unidadesPedir" ).val();
}

function p65_setUnidadesPedir(value){
	if (value){
		if (typeof value == 'number'){
			value = $.formatNumber(value,{format:"0.##",locale:"es"})
		}
		$( "#p65_fld_unidadesPedir" ).val(value);
	}
}

function p65_getRadioPeso(){
	var radioPeso = $( "input:radio[name='p65_rad_peso']:checked" ).val();
	if (typeof radioPeso == "undefined") radioPeso = "";
	return radioPeso;
}

function p65_setRadioPeso(value){
	$( "#"+value ).prop("checked", true);
}

function p65_setOtroPeso(value){
	if (value){
		if (typeof value == 'number'){
			value = $.formatNumber(value,{format:"0.##",locale:"es"})
		}
		$( "#p65_fld_otroPeso" ).val(value);
	}
}

function p65_getPesoDesde(){
	var peso = "";
	var radioPeso = p65_getRadioPeso();
	
	if (radioPeso == "Otro") {
		peso = $( "#p65_fld_otroPeso" ).val();
	}
	else if (radioPeso == "DesdeHasta") {
		peso = $( "#p65_fld_pesoDesde" ).val();
	}
	else {
		peso = radioPeso;
	}
	return peso;
}

function p65_setPesoDesde(value){
	if (value){
		if (typeof value == 'number'){
			value = $.formatNumber(value,{format:"0.##",locale:"es"})
		}
		$( "#p65_fld_pesoDesde" ).val(value);
	}
}

function p65_getPesoHasta(){
	var peso = "";
	var radioPeso = p65_getRadioPeso();
	
	if (radioPeso == "Otro") {
		peso = $( "#p65_fld_otroPeso" ).val();
	}
	else if (radioPeso == "DesdeHasta") {
		peso = $( "#p65_fld_pesoHasta" ).val();
	}
	else {
		peso = radioPeso;
	}
	return peso;
}

function p65_setPesoHasta(value ){
	if (value){
		if (typeof value == 'number'){
			value = $.formatNumber(value,{format:"0.##",locale:"es"})
		}
		$( "#p65_fld_pesoHasta" ).val(value);
	}
}

function p65_getDescripcion(){
	return $( "#p65_fld_descripcion" ).val();
}

function p65_setDescripcion(value){
	$( "#p65_fld_descripcion" ).val( value );
}

function p65_getDatosPedido(){
	if ( p65_Valid() ) {
		if (p65PedidoAdicionalCompleto instanceof PedidoAdicionalCompleto) {
			p65PedidoAdicionalCompleto.datosPedidoUnidadesPedir = p65_getUnidadesPedir();
			p65PedidoAdicionalCompleto.datosPedidoRadioPeso = p65_getRadioPeso();
			p65PedidoAdicionalCompleto.datosPedidoPesoDesde = p65_getPesoDesde();
			p65PedidoAdicionalCompleto.datosPedidoPesoHasta = p65_getPesoHasta();
			p65PedidoAdicionalCompleto.datosPedidoDescripcion = p65_getDescripcion();
			if (p65PedidoAdicionalCompleto.datosPedidoUnidadesPedir != "" && (p65PedidoAdicionalCompleto.datosPedidoPesoDesde != "" || p65PedidoAdicionalCompleto.datosPedidoDescripcion != "") ) {
				p65PedidoAdicionalCompleto.esEncargoEspecial = true;
			}
			else {
				p65PedidoAdicionalCompleto.esEncargoEspecial = false;
			}
		}
	}
	
	return p65PedidoAdicionalCompleto;
}

function events_p65_unidadesPedir(){
	$("#p65_fld_unidadesPedir").on("keydown", function(e) {
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
					if (!p65SelectUnidadesPedir){
						if (e.preventDefault) { e.preventDefault(); } else { e.returnValue = false; }
				 		return false;
					}
					else{
						p65SelectUnidadesPedir = false;
					}
				}
			}
		}
		p65SelectUnidadesPedir = false;
	});
	$("#p65_fld_unidadesPedir").on("select", function(e) {
		p65SelectUnidadesPedir = true;
	});
}


function events_p65_radioPeso(){
	$("input:radio[name='p65_rad_peso']").change(
		function(){
			if ($(this).is(':checked')){
				if ($(this).val() == 'Otro') {
					$( "#p65_fld_otroPeso").removeAttr('disabled');        //Habilitamos el input otroPeso
					$( "#p65_fld_otroPeso").focus();                       //Le damos el focus
				}
				else{
					$("#p65_fld_otroPeso").attr("disabled", "disabled");   //Deshabilitamos el input otroPeso
					$( "#p65_fld_otroPeso").val('');                       //Reseteamos el valor
				}
				if ($(this).val() == 'DesdeHasta') {
					$( "#p65_fld_pesoDesde").removeAttr('disabled');        //Habilitamos el input pesoDesde
					$( "#p65_fld_pesoHasta").removeAttr('disabled');        //Habilitamos el input pesoHasta
					$( "#p65_fld_pesoDesde").focus();                       //Le damos el focus
				}
				else{
					$("#p65_fld_pesoDesde").attr("disabled", "disabled");  //Deshabilitamos el input pesoDesde
					$("#p65_fld_pesoHasta").attr("disabled", "disabled");  //Deshabilitamos el input pesoHasta
					$( "#p65_fld_pesoDesde").val('');                       //Reseteamos el valor
					$( "#p65_fld_pesoHasta").val('');                       //Reseteamos el valor
				}
			}
	});
}

function events_p65_otroPeso(){
	$("#p65_fld_otroPeso").on("keydown", function(e) {
		var otroPeso = $(this).val();
		var esTeclaNumero = (e.which == 48 || e.which == 49 || e.which == 50 || e.which == 51 || e.which == 52 || e.which == 53 || e.which == 54 || e.which == 55 || e.which == 56 || e.which == 57);
		var esTeclaComa = (e.which == 188);
		if (esTeclaNumero || esTeclaComa){
			var posicionComa = otroPeso.indexOf(',');
			var esDouble = (posicionComa != -1);
			if (esDouble){
				decimalesOtroPeso = otroPeso.substring(posicionComa+1, otroPeso.length);
				if (decimalesOtroPeso.length >= 2 || esTeclaComa){
					if (!p65SelectOtroPeso){
						if (e.preventDefault) { e.preventDefault(); } else { e.returnValue = false; }
				 		return false;
					}
					else{
						p65SelectOtroPeso = false;
					}
				}
			}
		}
		p65SelectOtroPeso = false;
	});
	$("#p65_fld_otroPeso").on("select", function(e) {
		p65SelectOtroPeso = true;
	});
}

function events_p65_pesoDesde(){
	$("#p65_fld_pesoDesde").on("keydown", function(e) {
		var pesoDesde = $(this).val();
		var esTeclaNumero = (e.which == 48 || e.which == 49 || e.which == 50 || e.which == 51 || e.which == 52 || e.which == 53 || e.which == 54 || e.which == 55 || e.which == 56 || e.which == 57);
		var esTeclaComa = (e.which == 188);
		if (esTeclaNumero || esTeclaComa){
			var posicionComa = pesoDesde.indexOf(',');
			var esDouble = (posicionComa != -1);
			if (esDouble){
				decimalesPesoDesde = pesoDesde.substring(posicionComa+1, pesoDesde.length);
				if (decimalesPesoDesde.length >= 2 || esTeclaComa){
					if (!p65SelectPesoDesde){
						if (e.preventDefault) { e.preventDefault(); } else { e.returnValue = false; }
				 		return false;
					}
					else{
						p65SelectPesoDesde = false;
					}
				}
			}
		}
		p65SelectPesoDesde = false;
	});
	$("#p65_fld_pesoDesde").on("select", function(e) {
		p65SelectPesoDesde = true;
	});
}

function events_p65_pesoHasta(){
	$("#p65_fld_pesoHasta").focus(
		function(){
			if ($(this).val() == ""){
				$( "#p65_fld_pesoHasta" ).val( p65_getPesoDesde() );
			}
	});
	$("#p65_fld_pesoHasta").on("keydown", function(e) {
		var pesoHasta = $(this).val();
		var esTeclaNumero = (e.which == 48 || e.which == 49 || e.which == 50 || e.which == 51 || e.which == 52 || e.which == 53 || e.which == 54 || e.which == 55 || e.which == 56 || e.which == 57);
		var esTeclaComa = (e.which == 188);
		if (esTeclaNumero || esTeclaComa){
			var posicionComa = pesoHasta.indexOf(',');
			var esDouble = (posicionComa != -1);
			if (esDouble){
				decimalesPesoHasta = pesoHasta.substring(posicionComa+1, pesoHasta.length);
				if (decimalesPesoHasta.length >= 2 || esTeclaComa){
					if (!p65SelectPesoHasta){
						if (e.preventDefault) { e.preventDefault(); } else { e.returnValue = false; }
				 		return false;
					}
					else{
						p65SelectPesoHasta = false;
					}
				}
			}
		}
		p65SelectPesoHasta = false;
	});
	$("#p65_fld_pesoHasta").on("select", function(e) {
		p65SelectPesoHasta = true;
	});
}

function p65_Valid(){
	var valid = true;
	var messageVal = p65_findValidation();
	if (messageVal != null){
		$("#p03_btn_aceptar").unbind("click");
		$("#p03_btn_aceptar").bind("click", function(e) {
				$(p65Foco).focus();
				$(p65Foco).select();
			
			 });
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");
		valid = false;
		$("#p03_btn_aceptar").focus();
	}

	return valid;
}

function p65_findValidation(){
	var messageVal=null;
	if (p65_getUnidadesPedir() == null || p65_getUnidadesPedir() == ""){
		p65Foco = "#p65_fld_unidadesPedir";
		messageVal = p65UnidadesPedirRequired;
	} else if (p65_getRadioPeso()=="Otro" && $( "#p65_fld_otroPeso" ).val() == ""){
		messageVal = p65OtroPesoRequired;
		p65Foco = "#p65_fld_otroPeso";
	} else if ($( "#p65_fld_otroPeso" ).val() && !p65_validarPesos($( "#p65_fld_otroPeso" ).val())){
		messageVal = p65OtroPesoIncorrect;
		p65Foco = "#p65_fld_otroPeso";
	}else if (p65_getRadioPeso()=="DesdeHasta") {
	
		if ($( "#p65_fld_pesoDesde" ).val() == ""){
			messageVal = p65PesoDesdeRequired;
			p65Foco = "#p65_fld_pesoDesde";
		}else if (!p65_validarPesos($( "#p65_fld_pesoDesde" ).val())){
			messageVal = p65PesoDesdeIncorrect;
			p65Foco = "#p65_fld_pesoDesde";
		}
		else if ($( "#p65_fld_pesoHasta" ).val() == ""){
			messageVal = p65PesoHastaRequired;
			p65Foco = "#p65_fld_pesoHasta";
		}else if (!p65_validarPesos($( "#p65_fld_pesoHasta" ).val())){
			messageVal = p65PesoHastaIncorrect;
			p65Foco = "#p65_fld_pesoHasta";
		} else if (parseFloat($( "#p65_fld_pesoDesde" ).val().replace(',','.')) > parseFloat($( "#p65_fld_pesoHasta" ).val().replace(',','.'))){
			messageVal = p65PesoDesdeMayor;
			p65Foco = "#p65_fld_pesoDesde";
		}
	}
	else if ( p65_esGenerica() && p65_getDescripcion() == ""){
		messageVal = p65DescripcionRequired;
		p65Foco = "#p65_fld_descripcion";
	}

	return messageVal;
}

function p65_esGenerica() {
	if (p65PedidoAdicionalCompleto instanceof PedidoAdicionalCompleto) {
		return (p65PedidoAdicionalCompleto.esGenerica);
	}
	else {
		return false;
	}
}

function p65_esCarnePescado() {
	if (p65PedidoAdicionalCompleto instanceof PedidoAdicionalCompleto) {
		if ((p65PedidoAdicionalCompleto.area == "1" && p65PedidoAdicionalCompleto.seccion == "7" && p65PedidoAdicionalCompleto.categoria == "14") ||
			(p65PedidoAdicionalCompleto.area == "1" && p65PedidoAdicionalCompleto.seccion == "2" && p65PedidoAdicionalCompleto.categoria == "61" && p65PedidoAdicionalCompleto.subcategoria == "7") ||
			(p65PedidoAdicionalCompleto.area == "1" && p65PedidoAdicionalCompleto.seccion == "2" && p65PedidoAdicionalCompleto.categoria == "62" && p65PedidoAdicionalCompleto.subcategoria == "6") ||
			(p65PedidoAdicionalCompleto.area == "1" && p65PedidoAdicionalCompleto.seccion == "2" && p65PedidoAdicionalCompleto.categoria == "63" && p65PedidoAdicionalCompleto.subcategoria == "6") ||
			(p65PedidoAdicionalCompleto.area == "1" && p65PedidoAdicionalCompleto.seccion == "2" && p65PedidoAdicionalCompleto.categoria == "64" && p65PedidoAdicionalCompleto.subcategoria == "5") ||
			(p65PedidoAdicionalCompleto.area == "1" && p65PedidoAdicionalCompleto.seccion == "2" && p65PedidoAdicionalCompleto.categoria == "65" && p65PedidoAdicionalCompleto.subcategoria == "12")
				) {
			return true;
		} else {
			return false;
		}
	}
	else {
		return false;
	}
}

function p65_validarPesos(cantidad){

	var cantidadPunto = cantidad.replace(',','.');

	if (isNaN(parseFloat(cantidadPunto)) || cantidad.split(",").length > 2){
		return false;
	} else {
		return true;
	}
}