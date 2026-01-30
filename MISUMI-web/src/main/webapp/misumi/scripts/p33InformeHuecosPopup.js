var p33BotonAceptar = null;
var p33TituloVentanaInformePopup = null;
var p33mensajeInformeHuecos=null;
var arrayDescPeriodo = new Array();

$(document).ready(function(){

	loadP33(locale);

});

function p33loadInformeHuecos(){
	$.ajax({
		type : 'POST',
		url : './welcome/getUserSession.do',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {				
				
			//Se habilitan los avisos correspondientes al centro
				//data.centro.informeHuecos = true;
				
				if (data!=null && data.centro != null && data.centro.informeListado != null && data.centro.informeListado.existe == true){
					//Existe algun tipo de informe, o huecos, o pesca.
					
						//if (data.centro.informeHuecos) { //Existen informes de hueco
						if (data.centro.informeListado.informeHuecosCount != null && data.centro.informeListado.informeHuecosCount > 0){
						 	var fechaEntrega = $.datepicker.formatDate('DD dd-MM',new Date(),{
								dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
								dayNames: $.datepicker.regional[ "es" ].dayNames,
								monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
								monthNames: $.datepicker.regional[ "es" ].monthNames
								});
							 $("#p33_avisosFecha").html("(" + fechaEntrega + ")");
							p33RecalcularEnlaceInformeHuecos();
							
							$("div#p33_avisoInformeHuecos").attr("style", "display:block");
						} else {
							$("div#p33_avisoInformeHuecos").attr("style", "display:none");
						}
						
						
						if (data.centro.informeListado.listaInformeListadoPesca != null && data.centro.informeListado.listaInformeListadoPesca.length > 0){
							// Aquí mostramos el informe de pesca mostrador.
							//En el titulo del pop up, tiene que aparecer la fecha del dia en rojo
							var fechaFormateadaTitulo = $.datepicker.formatDate("DD dd-MM", new Date(),{
								dayNamesShort: $.datepicker.regional[ "es" ].dayNames,
								dayNames: $.datepicker.regional[ "es" ].dayNames,
								monthNamesShort: $.datepicker.regional[ "es" ].monthNames,
								monthNames: $.datepicker.regional[ "es" ].monthNames
								});
							$('#p35_informesPescaFecha').html("(" + fechaFormateadaTitulo + ")");

							$("div#p35_cuerpoInformes").attr("style", "display:block");
						} else {
							$("div#p35_cuerpoInformes").attr("style", "display:none");
						}
							
				}		
				$( "#p33_informeHuecos" ).dialog( "open" );
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
       }			
	});	
	
}

function p33RecalcularEnlaceInformeHuecos(){

	$( "#p33_mensajeInformeHuecosEnlace" ).unbind("click");
	$( "#p33_mensajeInformeHuecosEnlace" ).click(function() {
		var form = "<form name='informeHuecosForm' action='welcome/exportGrid.do'  accept-charset='ISO-8859-1' method='get'>";
		form = form + "</form><script>document.informeHuecosForm.submit();</script>";
		Show_PopupInforme(form);	
    });
}


function initializeScreenHuecosPopup(){
	$( "#p33_informeHuecos" ).dialog({
        autoOpen: false,
        height: 'auto',
        width: 'auto',
        modal: true,
        title: p33TituloVentanaInformePopup,
        resizable: false,
        buttons:[{
            text: p33BotonAceptar,
            click: function() {
                      $(this).dialog('close');
                   }
        }],
		open: function() {
			$('.ui-dialog-titlebar-close').on('mousedown', function(){
				$("#p33_informeHuecos").dialog('close');
			});
		}
    });
	
	$(window).bind('resize', function() {
	    $("#p33_informeHuecos").dialog("option", "position", "center");
	});
}


function loadP33(locale){
	this.i18nJSON = './misumi/resources/p33InformeHuecosPopup/p33informeHuecosPopup_' + locale + '.json';
	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				p33BotonAceptar = data.p33BotonAceptar;
				p33TituloVentanaInformePopup = data.p33TituloVentanaInformePopup;
				p33mensajeInformeHuecos= data.p33mensajeInformeHuecos;
				initializeScreenHuecosPopup();
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
           });
}

function Show_PopupInforme(form) {
	$('#informePopup').html(form);
	$('#informePopup').fadeIn('fast');
	$('#informeWindow').fadeIn('fast');
	Close_PopupInforme();
}

function Close_PopupInforme() {
	$('#informePopup').fadeOut('fast');
	$('#informeWindow').fadeOut('fast');
}