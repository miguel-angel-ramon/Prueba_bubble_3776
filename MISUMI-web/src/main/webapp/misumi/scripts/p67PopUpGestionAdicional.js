var p67TituloVentana = null;

$(document).ready(function(){
	loadP67(locale);
});

function initializeScreenP67PopupGestionAdicional(){
	// Abrir el pop-up de datos pedido
	$( "#p67_popupGestionAdicional" ).dialog({
        autoOpen: false,
        height: "auto",
        width: 800,
        modal: true,
        resizable: false,
		open: function() {
			$("#p67_fld_Preguntas").prop("readonly", true);
			$("#p67_fld_Respuestas").prop("readonly", true);
			//Cuando se clique sobre la X cerrar ventana
			$( '.ui-dialog-titlebar-close' ).on('mousedown', function(){
				$( "#p67_popupGestionAdicional" ).dialog('close');
			});
		}
    });
	
	this.i18nJSON = './misumi/resources/p67GestionAdicional/p67GestionAdicional_' + locale + '.json';
	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				p67TituloVentana = data.p67TituloVentana;
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
			});
	
	$(window).bind('resize', function() {
	    $( "#p67_popupGestionAdicional" ).dialog("option", "position",  { my: "left top", at: "left bottom", of: window });
	});
}

function reset_p67(){
	$( "#p67_fld_Preguntas" ).val('');
	$( "#p67_fld_Respuestas" ).val('');
	$( "#p67AreaErrores").attr("style", "display:none");
	$( "#p67_popupGestionAdicional" ).dialog( "option", "title", p67TituloVentana);
}

function loadP67(locale){
	initializeScreenP67PopupGestionAdicional();
}

function reloadDataP67(localizador){
	reset_p67();
	
	//Antes de abrir la pantalla de gestión adicional, obtenemos la información necesaria para mostrarla posteriormente.
	var recordPedidoAdicionalEC = new PedidoAdicionalEC (localizador, $("#centerId").val(), null, null, null, null, 
			null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null);
	
	var objJson = $.toJSON(recordPedidoAdicionalEC.preparePedidoAdicionalECToJsonObject());
	
	$.ajax({
		type : 'POST',
		url : './gestionAdicionalEC/loadGestionAdicionalEC.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	

			if (data.codigoError == 0){
				$("#p67AreaErrores").attr("style", "display:none");
				//Actualización de los datos de "Gestión adicional"
				updateGestionAdicional(data);
			}else{
				$("#p67AreaErrores").attr("style", "display:block");
			}
			$( "#p67_popupGestionAdicional" ).dialog( "open" );
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});	
}

function updateGestionAdicional (data){
	
	if (data != null){
		if (data.descripcionGestadic != null && data.descripcionGestadic!=''){
			$("#p67_popupGestionAdicional" ).dialog( "option", "title", p67TituloVentana + " - " + data.descripcionGestadic);
		}
		$("#p67_fld_Preguntas").val(data.txtDetalleGestadic);
		$("#p67_fld_Respuestas").val(data.txtSituacionGestadic);
	}
}