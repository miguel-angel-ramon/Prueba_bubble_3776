var p35BotonAceptar = null;
var p35mensajeInforme=null;
var arrayDescPeriodo = new Array();

$(document).ready(function(){

	loadP35(locale);

});

function p35loadInformes(){
	//$( "#p35_popupInformes" ).dialog( "open" );
	
	$.ajax({
		type : 'POST',
		url : './welcome/getInformesCentro.do',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {				
				//Se habilitan los informes correspondientes al centro
				var informeListado = null;
				var idSubcategoria = null;
				var descSubcategoria = null;
				var descripcion = null;
				var parrafo = "";
				var mensajeAuxiliar = null;

				if (data!=null && data.centro != null){
					
					// Si el centro tiene informeListado dibujamos la lista en el popup
					if (data.centro.informeListado != null && data.centro.informeListado.existe){
						
						// Aquí ocultariamos el mensaje de que no hay informes disponibles
						// y mostrariamos los informes disponibles, por ahora sólo tenemos
						// el informe de pesca mostrador.
						
						$("div#p35_NoInformesDisponible").attr("style", "display:none");
						$("div#p35_informes").attr("style", "display:block");
						
						if (data.centro.informeListado.listaInformeListadoPesca != null && data.centro.informeListado.listaInformeListadoPesca.length > 0){
							// Aquí mostramos el informe de pesca mostrador.
							$("div#p35_informePescaMostrador").attr("style", "display:block");
						}
						
					}else{
						
						// Aquí falta mostrar el mensaje de que no hay informes disponibles.
						// y ocultariamos todos los informes
						
						$("div#p35_informes").attr("style", "display:none");
						$("div#p35_informePescaMostrador").attr("style", "display:none");
						
						$("div#p35_NoInformesDisponible").attr("style", "display:block");
					
						
					}
				}
				$( "#p35_popupInformes" ).dialog( "open" );
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
       }			
	});	
	
}

function p35RecalcularEnlaceValidarCantidadesExtra(){

	var url = './pedidoAdicional.do?flagCancelarNuevo=S&pestanaOrigen=AVISOS_VC&flgReferenciaCentro=N';
	$( "#p35_mensajeRecordatorioValidarCantidadesExtraEnlace" ).unbind("click");
	$( "#p35_mensajeRecordatorioValidarCantidadesExtraEnlace" ).click(function() {
    	window.location=url;
    });
}

function p35RecalcularEnlacePedidoAdicional(tamano){
	
	//var url = './pedidoAdicional.do?flagCancelarNuevo=S&pestanaOrigen=AVISOS_MO&flgReferenciaCentro=N&mac=S';

	for (i = 0; i < tamano; i++) {
		$( "#p35_mensajeRecordatorioPedidoAdicionalEnlace" + i ).unbind("click");
		$( "#p35_mensajeRecordatorioPedidoAdicionalEnlace" + i  ).click(function(e) {
			var id = e.target.id;
	    	window.location= './pedidoAdicional.do?flagCancelarNuevo=S&pestanaOrigen=AVISOS_MO&flgReferenciaCentro=N&mac=S&descPeriodo=' + arrayDescPeriodo[id.substring(id.length-1)];

	    });
		
	}
}

function initializeScreenInformesPopup(){
	$( "#p35_popupInformes" ).dialog({
        autoOpen: false,
        height: 'auto',
        width: 'auto',
        modal: true,
        resizable: false,
        buttons:[{
            text: p35BotonAceptar,
            click: function() {
                      $(this).dialog('close');
                   }
        }],
		open: function() {
			$('.ui-dialog-titlebar-close').on('mousedown', function(){
				$("#p35_popupInformes").dialog('close');
			});
		}
    });
	
	$(window).bind('resize', function() {
	    $("#p35_popupInformes").dialog("option", "position", "center");
	});
}


function loadP35(locale){
	this.i18nJSON = './misumi/resources/p35InformesPopup/p35informesPopup_' + locale + '.json';
	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				p35BotonAceptar = data.p35BotonAceptar;
				p35mensajeInforme= data.p35mensajeInforme;
				initializeScreenInformesPopup();
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
           });
}

function p35SetInformesListadoVisible(visible){
	//Si es visible muestro el ícono en la posición correcta
	//dependiendo de si los demás íconos están visibles o no.
	//Si no, lo oculto.
	if (visible){
		//Borro las clases que tuviera
		$("#InformesListado").removeClass("ConTablonConLoteConInforme");
		$("#InformesListado").removeClass("ConTablonConLoteSinInforme");
		$("#InformesListado").removeClass("ConTablonSinLoteConInforme");
		$("#InformesListado").removeClass("ConTablonSinLoteSinInforme");
		$("#InformesListado").removeClass("SinTablonConLoteConInforme");
		$("#InformesListado").removeClass("SinTablonConLoteSinInforme");
		$("#InformesListado").removeClass("SinTablonSinLoteConInforme");
		$("#InformesListado").removeClass("SinTablonSinLoteSinInforme");
		
		//Añado la clase que le corresponde para situar correctamente el ícono 
		if ($('#p01_btn_tablonAnuncios').is(':visible')){
			if ($('#p01_btn_lote_navidad').is(':visible')){
				if ($('#InformeHuecos').is(':visible')){
					$("#InformesListado").addClass("ConTablonConLoteConInforme");
				} else {
					$("#InformesListado").addClass("ConTablonConLoteSinInforme");
				}
			} else {
				if ($('#InformeHuecos').is(':visible')){
					$("#InformesListado").addClass("ConTablonSinLoteConInforme");
				} else {
					$("#InformesListado").addClass("ConTablonSinLoteSinInforme");
				}
			}
		} else {
			if ($('#p01_btn_lote_navidad').is(':visible')){
				if ($('#InformeHuecos').is(':visible')){
					$("#InformesListado").addClass("SinTablonConLoteConInforme");
				} else {
					$("#InformesListado").addClass("SinTablonConLoteSinInforme");
				}
			} else {
				if ($('#InformeHuecos').is(':visible')){
					$("#InformesListado").addClass("SinTablonSinLoteConInforme");
				} else {
					$("#InformesListado").addClass("SinTablonSinLoteSinInforme");
				}
			}
		}
		$("#InformesListado").attr("style", "display:inline;");
	}
	else{
		$("#InformesListado").attr("style", "display:none;");
	}
}