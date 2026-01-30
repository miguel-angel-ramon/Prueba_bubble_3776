var formatoFechaTituloPopup = null;
var ventasTituloPopup = null;
var p17BotonAceptar = null;

$(document).ready(function(){

	loadP17(locale);

});

function initializeScreenPopupVentas(){
	
	$("#p17_fld_tarifa").attr("disabled", "disabled");
	$("#p17_fld_competencia").attr("disabled", "disabled");
	$("#p17_fld_oferta").attr("disabled", "disabled");
	$("#p17_fld_anticipada").attr("disabled", "disabled");
	$("#p17_fld_total").attr("disabled", "disabled");
	
	$( "#p17_popupVentas" ).dialog({
        autoOpen: false,
        height: 250,
        width: 300,
        modal: true,
        resizable: false,
        dialogClass: "p17_popupResaltado",
        buttons:[{
            text: p17BotonAceptar,
            click: function() {
                      $(this).dialog('close');
                   }
        }],
		open: function() {
			$('.ui-dialog-titlebar-close').on('mousedown', function(){
				$("#p17_popupVentas").dialog('close');
			});
		}
    });
	
	$(window).bind('resize', function() {
	    $("#p17_popupVentas").dialog("option", "position", "center");
	});
}

function loadP17(locale){
	
	this.i18nJSON = './misumi/resources/p17ReferenciasCentroPopupVentas/p17referenciasCentroPopupVentas_' + locale + '.json';
	
	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				formatoFechaTituloPopup = data.formatoFechaTituloPopup;
				ventasTituloPopup = data.ventasTituloPopup;
				p17BotonAceptar = data.p17BotonAceptar;
				initializeScreenPopupVentas();
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
           });
}

function recalcularEnlacesPopupVentas(){

	$( ".p15_ventasUltimoMesTablaTd" )
    .click(function() {
    	recalcularPopupCalendario(this);
    });
	
	$( ".p15_ventasUltimoMesAnticipadaTablaTd" )
    .click(function() {
    	recalcularPopupCalendario(this);
    });

	$( ".p15_ventasUltimoMesSinEnlaceTablaTd" )
    .click(function() {
    });

}

function recalcularPopupCalendario(diaCalendario){

	//Calcular el d�a de venta
	var p17idCampoFecha = diaCalendario.id.replace("Mes", "MesFecha");
	var fechaVentaDDMMYYYY = $("#"+p17idCampoFecha).val();
	var diaFechaTitulo = parseInt(fechaVentaDDMMYYYY.substring(0,2),10);
	var mesFechaTitulo = parseInt(fechaVentaDDMMYYYY.substring(2,4),10);
	var anyoFechaTitulo = parseInt(fechaVentaDDMMYYYY.substring(4),10);
	
	//Formateo de fecha
	var fechaTituloFormateada = $.datepicker.formatDate(formatoFechaTituloPopup, new Date(anyoFechaTitulo, mesFechaTitulo - 1, diaFechaTitulo));
	$( "#p17_popupVentas" ).dialog( "option", "title", ventasTituloPopup + " " + fechaTituloFormateada);
	
	resetResultadosPopupVentas();
	loadPopupVentas(fechaVentaDDMMYYYY);
}

function loadPopupVentas(fechaVentaDDMMYYYY){
	var vHistoricoVentaUltimoMes=new HistoricoVentaUltimoMes($("#p13_fld_referenciaEroski").val(), $("#centerId").val(), fechaVentaDDMMYYYY);
	var objJson = $.toJSON(vHistoricoVentaUltimoMes);	
	 $.ajax({
		type : 'POST',
		url : './referenciasCentro/loadDatosPopupVentas.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
				
			//Actualizaci�n de los datos de "Popup Ventas"
			updateResultadosPopupVentas(data);
			$( "#p17_popupVentas" ).dialog( "open" );
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});	
}

function loadPopupVentasUltimasOfertas(fechaVentaDDMMYYYY){
	var vHistoricoUnidadesVenta=new HistoricoUnidadesVenta(refArticulo, $("#centerId").val(), fechaVentaDDMMYYYY);
	var objJson = $.toJSON(vHistoricoUnidadesVenta);	
	 $.ajax({
		type : 'POST',
		url : './cargarPopup/loadDatosPopupVentasUltimasOfertas.do?sumVentaAnticipada='+sumVentaAnticipada,
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	

			//Actualizaci?n de los datos de "Popup Ventas"
			updateResultadosPopupVentas(data);
			$( "#p17_popupVentas" ).dialog( "open" );
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});	
}

function updateResultadosPopupVentas (data){
	
	if (data != null){
		
		$("#p17_fld_tarifa").val(data.unidVentaTarifa).formatNumber();
		$("#p17_fld_competencia").val(data.unidVentaCompetencia).formatNumber();
		$("#p17_fld_oferta").val(data.unidVentaOferta).formatNumber();
		$("#p17_fld_anticipada").val(data.unidVentaAnticipada).formatNumber();
		$("#p17_fld_total").val(data.totalVentas).formatNumber();
	}
}

function resetResultadosPopupVentas (){
	$("#p17_fld_tarifa").val("");
	$("#p17_fld_competencia").val("");
	$("#p17_fld_oferta").val("");
	$("#p17_fld_anticipada").val("");
	$("#p17_fld_total").val("");
}
