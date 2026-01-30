function initializeP61(){

	initializeScreenP61();
	$("#p61_AreaModificacion").keydown(function(event) {
		if(event.which == 13) {
			event.preventDefault();
	        var buttons = $( "#p61_AreaModificacion" ).dialog( "option", "buttons" );
	        var numBotones = buttons.length;
	        if (numBotones == 2){
	        	//Botones de guardar y volver
            	if ($("#p40_pestanaMontaje").is (":visible")) 
        		{
            		p42ModificarDatosDesdePopup();
        		}
        		else if ($("#p40_pestanaMontajeOferta").is (":visible")) 
        		{
        			p43ModificarDatosDesdePopup();
        		}
            	$("#p61_AreaModificacion").dialog('close');
	        }else{
	        	//Botón de volver
	        	$("#p61_AreaModificacion").dialog('close');
	        }
		}        
    });
}


function initializeScreenP61(){

	$( "#p61_fechaFinDatePicker" ).datepicker($.datepicker.regional['esDiasServicio']);
	
	
	//Inicializamos el popup de ayuda.
	$( "#p61_AreaModificacion" ).dialog({
        autoOpen: false,
        height: 270,
        width: 350,
        modal: true,
        resizable: false,
        buttons:[{
	            text: "Guardar",
	            click: function() {
	            	if ($("#p40_pestanaMontaje").is (":visible")) 
	        		{
	            		p42ModificarDatosDesdePopup();
	        		}
	        		else if ($("#p40_pestanaMontajeOferta").is (":visible")) 
	        		{
	        			p43ModificarDatosDesdePopup();
	        		}
	            	$(this).dialog('close');
                   }
        	},{
	            text: "Volver",
	            click: function() {
	                  $(this).dialog('close');
                   }
        	}
        
        ],
		open: function() {
			$('.ui-dialog-titlebar-close').on('mousedown', function(){
				$("#p61_AreaModificacion").dialog('close');
			});
		}
    });
}

function reloadDatosReferencia() {

}

function p61CargaDatosCalendariosMod(){
	
	//Carga de datos para los calendarios
	$("#codCentroCalendario").val($("#centerId").val());
	$("#codArticuloCalendario").val("");
	$("#identificadorCalendario").val("");
	$("#identificadorSIACalendario").val("");
	$("#clasePedidoCalendario").val("M");
	$("#recargarParametrosCalendario").val("S");
	$("#cargadoDSCalendario").val("N");
}
