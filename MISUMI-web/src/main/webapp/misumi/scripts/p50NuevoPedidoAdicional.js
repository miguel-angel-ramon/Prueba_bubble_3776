var referenceRequired=null;
var centerRequired=null;
var emptyRecords=null;
var v_Oferta = null;
var P50Inicializada = null;

$(document).ready(function(){
	$(document).on('CargarScriptsPantallas', function(e) { 
		//checkP51Visibility();
		loadP50(locale);
		initializeScreenP50();
		unloadP52Grid();
		//Esperamos a que se cargue el centerName en el metodo getCentroUsuarioSession()
		$(document).on('CargadoCentro', function(e) { 
			if(P50Inicializada == null){
				P50Inicializada = 'S';
				$("#p50_pestanaReferenciaCargada").val("N");
				$("#p50_pestanaOfertaCargada").val("N");
				//Eliminado pestana p51 temporal -- initializeScreenP51();
				initializeScreenP52();
				initializeP56();
			}
		});

		if($("#centerId").val() != null && $("#centerId").val() != "" && P50Inicializada == null){
			P50Inicializada = 'S';
			$("#p50_pestanaReferenciaCargada").val("N");
			$("#p50_pestanaOfertaCargada").val("N");
			//Eliminado pestana p51 temporal -- initializeScreenP51();
			initializeScreenP52();
			initializeP56();
		}

	});
	initializeScreenComponentsP50();
});

function initializeScreenComponentsP50(){
	//Eliminado pestana p51 temporal -- initializeScreenComponentsP51();
	initializeScreenComponentsP52();

	CargadaEstructuraPantalla(2);
}

$(document).mousedown(function (e)
		{

	if($("#p56_AreaAyuda").is(':visible')){
		var container = $(".p56_popupResaltado");
		//Si el popup 17 est� abierto, si se clica el popup o alg�no de sus hijos no cerrar popup p56
		var container2 = $(".p17_popupResaltado");

		var $el = $(e.target);

		var idCal = $el.closest(".p52_datepicker").attr("id");
		var containerClassName = e.target.className;
		var hide = false;
		if (!container.is(e.target) // if the target of the click isn't the container...
				&& container.has(e.target).length === 0 
				&& !container2.is(e.target) // if the target of the click isn't the container...
				&& container2.has(e.target).length === 0 
				&& !$("#p17_popupVentas").is(':visible')
				&& (null == containerClassName || containerClassName.indexOf("conAyuda") == -1) 
				&& idCal == null) // ... nor a descendant of the container
		{
			$("#p56_AreaAyuda").dialog('close');
		}
	}
	//Si se clica fuera del popup p17 cerrar el popup p17
	if($("#p17_popupVentas").is(':visible')){
		var container2 = $(".p17_popupResaltado");

		if (!container2.is(e.target)
				&& container2.has(e.target).length === 0 ){
			$("#p17_popupVentas").dialog('close');
		}
	}

	if($("#p59_AreaStock").is(':visible')){
		var container = $(".p59_popupResaltado");
		var container2 = $(".ui-widget-content #colchooser_gridP59Stock");
		var containerClassName = e.target.className;
		if ((!container.is(e.target) && container.has(e.target).length === 0) && 
				(!container2.is(e.target) && container2.has(e.target).length === 0)

				&& (null == containerClassName || containerClassName.indexOf("conStock") == -1)) // ... nor a descendant of the container
		{
			$("#p59_AreaStock").dialog('close');
		}
	}

		});

$(document).focus(function (e)
		{

	if($("#p56_AreaAyuda").is(':visible')){
		var container = $(".p56_popupResaltado");
		var containerClassName = e.target.className;
		var $el = $(e.target);
		var idCal = $el.closest(".p52_datepicker").attr("id");
		var hide = false;
		if (!container.is(e.target) // if the target of the click isn't the container...
				&& container.has(e.target).length === 0
				&& (null == containerClassName || containerClassName.indexOf("conAyuda") == -1) && idCal == null) // ... nor a descendant of the container
		{
			$("#p56_AreaAyuda").dialog('close');
		}
	}

	if($("#p59_AreaStock").is(':visible')){
		var container = $(".p59_popupResaltado");
		var container2 = $(".ui-widget-content #colchooser_gridP59Stock");
		var containerClassName = e.target.className;
		if ((!container.is(e.target) && container.has(e.target).length === 0) && 
				(!container2.is(e.target) && container2.has(e.target).length === 0)
				&& (null == containerClassName || containerClassName.indexOf("conStock") == -1)) // ... nor a descendant of the container
		{
			$("#p59_AreaStock").dialog('close');
		}
	}
		});

function initializeScreenP50(){

	$("#p50_pestanas").tabs({ 
		select:
			function (event, ui) {

			$("#p56_AreaAyuda").dialog('close');

			var nombrePestanaActivada = ui.panel.id;
			var valorPestanaCargada = $("#" + nombrePestanaActivada + "Cargada").val();

			//Sólo se ejecuta la carga de datos si no se ha cargado antes
			if (valorPestanaCargada == null || !(valorPestanaCargada == "S")){
				if (nombrePestanaActivada == "p50_pestanaOferta"){
					//Carga de la pestaña de oferta
					reloadDatosOferta();
				}else{
					//Carga de la pestaña de referencia
					reloadDatosReferencia();  
				}
			}
			/*Eliminado pestana p51 temporal -- if (nombrePestanaActivada == "p50_pestanaOferta"){
	    			//Hago unbind/bind del botón aceptar del alert cada vez que entro en esta pestaña
	    			//para que no haya conflictos con el unbind/bind del botón aceptar del alert de la otra pestaña.
	    			p51AlertBotonAceptarClick();
	    		}*/

			//Se actualiza el control de carga de datos en pestaña
			$(nombrePestanaActivada + "Cargada").val("S");

			return true;
		}
	});
}

function loadP50(locale){

	this.i18nJSON = './misumi/resources/p50NuevoPedidoAdicional/p50nuevoPedidoAdicional_' + locale + '.json';

	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		emptyRecords= data.emptyRecords;
		centerRequired=data.centerRequired;
		referenceRequired = data.referenceRequired;

	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

function unloadP52Grid() {

	$.ajax({
		type : 'POST',
		url : './nuevoPedidoAdicionalReferencia/cleanGrid.do',
		contentType : "application/json; charset=utf-8",
		success : function(data) {	
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});	

};

/*function checkP51Visibility(){

	var user = $("#userId").val();
	var center = $("#centerId").val();
	if (user != null && (user.toUpperCase() == "I1251" || user.toUpperCase() == "S305060")){
		$("#p50_pestOferta").show();
	} else {
		if (center == null || center.toUpperCase() == '202' || center.toUpperCase() == '673' || center.toUpperCase() == '357'){
			$("#p50_pestOferta").show();
		}else{
			$("#p50_pestOferta").hide();
		}
	}
}*/