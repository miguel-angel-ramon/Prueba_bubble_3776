var p19BotonAceptar = null;
var p19TituloVentanaTablonPopup = null;
var p19mensajePedidoAdicional=null;
var p19mensajeAvisoDevolucion=null;
var p19mensajeAvisoCalendario=null;
var p19mensajeAvisoEntrada=null;
var p19mensajeAvisoAlarmasPLU=null;
var p19mensajeAvisoMostrador=null;
var p19mensajeAvisoFacingCero=null;
var arrayDescPeriodo = new Array();

$(document).ready(function(){

	loadP19(locale);

});

function p19loadTablonAnuncios(){
	$.ajax({
		type : 'POST',
		url : './welcome/getUserSession.do',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {				
				//Se habilitan los avisos correspondientes al centro
				var codigoAviso = null;
				var codigo = null;
				var aviso = null;
				var parrafoAvisoPedidoAdicional = "";
				var parrafoAvisoDevolucion = "";
				var parrafoAvisoCalendario = "";
				var parrafoAvisoCalendarioPlataforma = "";
				var parrafoAvisoDevolucionUrgente="";
				var parrafoAvisoCalendarioKO = "";
				var parrafoAvisoEntradas = "";
				var parrafoAvisoAlarmasPLU = "";
				var parrafoAvisoMostrador = "";
				var parrafoAvisoFacingCero = "";
				var mensajeAuxiliar = null;
				if (data!=null && data.centro != null){

					 if (data.centro.listaAvisosCentro != null && data.centro.listaAvisosCentro.length > 0){
						 
						 $("#p19_avisoSinAvisosPendientes").attr("style", "display:none;");
						 $("#p19_avisoConAvisosPendientes").attr("style", "display:inline;");
						 $("#p19_mensajesAvisos").attr("style", "display:inline;");
						 $("#p19_cabeceraAvisos").attr("style", "display:inline;");
						 
						 for (i = 0; i < data.centro.listaAvisosCentro.length; i++) {
							 codigoAviso = data.centro.listaAvisosCentro[i].split("**");
							 codigo = codigoAviso[0];
							 aviso = codigoAviso[1];
							
							 if(codigo != null && codigo == '1'){
								 $("#p19_avisoValidarCantidadesExtra").attr("style", "display:inline;");
								
								 p19RecalcularEnlaceValidarCantidadesExtra();
							 }
							 
							 if(codigo != null && codigo == '2'){
								 $("#p19_avisoPedidoAdicional").attr("style", "display:inline;");
								
								 var tipoDeMontaje = codigoAviso[2]; 
								 
								 mensajeAuxiliar = p19mensajePedidoAdicional;		 
								 mensajeAuxiliar = mensajeAuxiliar.replace('{0}',aviso).replace('{1}',i).replace('{2}',tipoDeMontaje);
								 parrafoAvisoPedidoAdicional = parrafoAvisoPedidoAdicional + "<div class='p19_avisosMensaje'><p class='p19_parrafoAvisoPedidoAdicional' id='p19_parrafoAvisoPedidoAdicional'>" + mensajeAuxiliar + "</p></div>";
								 
								 arrayDescPeriodo[i] = aviso;
							
							 }
							 if(codigo != null && codigo == '3'){
								 $("#p19_avisoDevoluciones").attr("style", "display:inline;");							
								 
								 mensajeAuxiliar = p19mensajeAvisoDevolucion;
								 mensajeAuxiliar = mensajeAuxiliar.replace('{0}',aviso);
								 parrafoAvisoDevolucion = parrafoAvisoDevolucion + "<div class='p19_avisosMensaje'><p id='p19_parrafoDevoluciones'>" + mensajeAuxiliar + "</p></div>";
							 }
							 
							 if(codigo != null && codigo == '4'){
								 $("#p19_avisoCalendario").attr("style", "display:inline;");							
								 
								 mensajeAuxiliar =  p19mensajeAvisoCalendario ;
								 
								 //Obtenemos el mes y el día del calendario fecha limite.
								 var partesFechaP19 = createCalendarioLimiteP19(aviso).split("-");
								 var diaP19 = partesFechaP19[0];
								 var mesP19 = partesFechaP19[1];
								 
								 var elementosCalendario = "<div class='p19_avisosMensaje'>"
								 + 		"<div id='p19_Img'>"					
								 +  		"<div id='p19_mesCalendario'>"+mesP19+"</div>"
								 + 			"<div id='p19_diaCalendario'>"+diaP19+"</div>"
								 + 		"</div>"
								 + "</div>";
								 
								 mensajeAuxiliar = mensajeAuxiliar.replace('{0}',elementosCalendario);								
								 parrafoAvisoCalendario = parrafoAvisoCalendario + mensajeAuxiliar;								 						
							 }
							 
							//Aviso Calendario plataforma
							 if(codigo != null && codigo == '5'){
								 $("#p19_avisoCalendarioPlataforma").attr("style", "display:inline;");							
								 
								 mensajeAuxiliar = "- " + aviso + ". ";
								 parrafoAvisoCalendarioPlataforma = parrafoAvisoCalendarioPlataforma + "<div class='p19_avisosMensaje'><p id='p19_parrafoCalendarioPlataforma'>" + mensajeAuxiliar + "<span id='p19_mensajeRecordatorioCalendarioPlataformaEnlace'>Pinchar aquí</span> </p>  </div> ";
							 }
							 
							 if(codigo != null && codigo == '6'){
								 $("#p19_avisoDevolucionesUrgente").attr("style", "display:inline;");							
								 
								 mensajeAuxiliar = p19mensajeAvisoDevolucionUrgente;
								 mensajeAuxiliar = mensajeAuxiliar.replace('{0}',aviso);
								 parrafoAvisoDevolucionUrgente = parrafoAvisoDevolucionUrgente + "<div class='p19_avisosMensaje'><p id='p19_parrafoDevolucionesUrgente'>" + mensajeAuxiliar + "</p></div>";
							 }
							 if(codigo != null && codigo == '7'){
								 $("#p19_avisoCalendarioKO").attr("style", "display:inline;");							
								 
								 mensajeAuxiliar = "- " + aviso + ". ";;
								 parrafoAvisoCalendarioKO = parrafoAvisoCalendarioKO +  "<div class='p19_avisosMensaje'><p id='p19_parrafoCalendarioKO'>" + mensajeAuxiliar + "<span id='p19_mensajeRecordatorioCalendarioKOEnlace'>Pinchar aquí</span> </p>  </div> ";
							 }		
							 
							 if(codigo != null && codigo == '8'){
								 $("#p19_avisoEntradas").attr("style", "display:inline;");							
								 
								 mensajeAuxiliar = p19mensajeAvisoEntrada;
								 mensajeAuxiliar = mensajeAuxiliar.replace('{0}',aviso);
								 parrafoAvisoEntradas= parrafoAvisoEntradas + "<div class='p19_avisosMensaje'><p id='p19_parrafoEntradas'>" + mensajeAuxiliar + "</p></div>";
							 }
							 if(codigo != null && codigo == '9'){
								 $("#p19_avisoAlarmasPLU").attr("style", "display:inline;");							
								 
								 mensajeAuxiliar = p19mensajeAvisoAlarmasPLU;
								 mensajeAuxiliar = mensajeAuxiliar.replace('{0}',aviso);
								 parrafoAvisoAlarmasPLU= parrafoAvisoAlarmasPLU + "<div class='p19_avisosMensaje'><p id='p19_parrafoAlarmasPLU'>" + mensajeAuxiliar + "</p></div>";
							 }
							 if(codigo != null && codigo == '10'){
								 $("#p19_avisoMostrador").attr("style", "display:inline;");							
								 
								 mensajeAuxiliar = p19mensajeAvisoMostrador;
								 mensajeAuxiliar = mensajeAuxiliar.replace('{0}',aviso);
								 parrafoAvisoMostrador= parrafoAvisoMostrador + "<div class='p19_avisosMensaje'><p id='p19_parrafoMostrador'>" + mensajeAuxiliar + "</p></div>";
							 }

							 // Aviso para Facing Cero
							 if(codigo != null && codigo == '11'){
								 $("#p19_avisoFacingCero").attr("style", "display:inline;");							
								 
								 mensajeAuxiliar = p19mensajeAvisoFacingCero;
								 mensajeAuxiliar = mensajeAuxiliar.replace('{0}',aviso);
								 parrafoAvisoFacingCero= parrafoAvisoFacingCero + "<div class='p19_avisosMensaje'><p id='p19_parrafoFacingCero'>" + mensajeAuxiliar + "</p></div>";
							 }
						 }
						 
						 $("#p19_avisoPedidoAdicional").html(parrafoAvisoPedidoAdicional);
						 p19RecalcularEnlacePedidoAdicional(data.centro.listaAvisosCentro.length);
						 $("#p19_avisoDevoluciones").html(parrafoAvisoDevolucion);
						 p19RecalcularEnlaceDevoluciones();
						 $("#p19_avisoCalendario").html(parrafoAvisoCalendario);
						 p19RecalcularEnlaceCalendario();
						 $("#p19_avisoCalendarioPlataforma").html(parrafoAvisoCalendarioPlataforma);
						 p19RecalcularEnlaceCalendarioPlataforma();						 
						 $("#p19_avisoDevolucionesUrgente").html(parrafoAvisoDevolucionUrgente);
						 p19RecalcularEnlaceDevolucionesUrgente();
						 $("#p19_avisoCalendarioKO").html(parrafoAvisoCalendarioKO);
						 p19RecalcularEnlaceCalendarioKO();
						 $("#p19_avisoEntradas").html(parrafoAvisoEntradas);
						 p19RecalcularEnlaceEntradas();
						 $("#p19_avisoAlarmasPLU").html(parrafoAvisoAlarmasPLU);
						 p19RecalcularEnlaceAvisosPLU();
						 $("#p19_avisoMostrador").html(parrafoAvisoMostrador);
						 p19RecalcularEnlaceAvisosMostrador();
						 $("#p19_avisoFacingCero").html(parrafoAvisoFacingCero);
						 p19RecalcularEnlaceFacingCero();
					 }else{
						 $("#p19_avisoSinAvisosPendientes").attr("style", "display:inline;");
						 $("#p19_avisoValidarCantidadesExtra").attr("style", "display:none;");
					 }
				}else{
					
					 $("#p19_avisoSinAvisosPendientes").attr("style", "display:inline;");
					 $("#p19_avisoValidarCantidadesExtra").attr("style", "display:none;");
				}
				$( "#p19_tablonAnuncios" ).dialog( "open" );
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
       }			
	});	
	
}

function p19RecalcularEnlaceFacingCero(){
	var url = './facingCero.do?center='+$("#centerId").val();

	$( "#p19_mensajeRecordatorioFacingCeroEnlace" ).unbind("click");
	$( "#p19_mensajeRecordatorioFacingCeroEnlace" ).click(function() {
    	window.location=url;
    });
}

function p19RecalcularEnlaceAvisosMostrador(){
	var url = './detalladoMostrador.do?center='+$("#centerId").val();

	$( "#p19_mensajeRecordatorioAvisoMostradorEnlace" ).unbind("click");
	$( "#p19_mensajeRecordatorioAvisoMostradorEnlace" ).click(function() {
    	window.location=url;
    });
}

function p19RecalcularEnlaceAvisosPLU(){
	var url = './alarmasPLU.do?center='+$("#centerId").val();

	$( "#p19_mensajeRecordatorioAlarmasPLUEnlace" ).unbind("click");
	$( "#p19_mensajeRecordatorioAlarmasPLUEnlace" ).click(function() {
    	window.location=url;
    });
}

function p19RecalcularEnlaceAvisosMostrador(){
	var url = './detalladoMostrador.do?center='+$("#centerId").val();

	$( "#p19_mensajeRecordatorioAvisoMostradorEnlace" ).unbind("click");
	$( "#p19_mensajeRecordatorioAvisoMostradorEnlace" ).click(function() {
    	window.location=url;
    });
}

function p19RecalcularEnlaceValidarCantidadesExtra(){

	var url = './pedidoAdicional.do?flagCancelarNuevo=S&pestanaOrigen=AVISOS_VC&flgReferenciaCentro=N';
	$( "#p19_mensajeRecordatorioValidarCantidadesExtraEnlace" ).unbind("click");
	$( "#p19_mensajeRecordatorioValidarCantidadesExtraEnlace" ).click(function() {
    	window.location=url;
    });
}

function p19RecalcularEnlaceDevoluciones(){

	var url = './devoluciones.do?center='+$("#centerId").val()+'&origenPantalla=AVISOS'; 

	$( "#p19_mensajeRecordatorioDevolucionesEnlace" ).unbind("click");
	$( "#p19_mensajeRecordatorioDevolucionesEnlace" ).click(function() {
    	window.location=url;
    });
}

function p19RecalcularEnlaceDevolucionesUrgente(){

	var url = './devoluciones.do?center='+$("#centerId").val()+'&origenPantalla=AVISOS'; 

	$( "#p19_mensajeRecordatorioDevolucionesEnlaceUrgente" ).unbind("click");
	$( "#p19_mensajeRecordatorioDevolucionesEnlaceUrgente" ).click(function() {
    	window.location=url;
    });
}

function p19RecalcularEnlaceCalendario(){

	var url = './calendario.do?center='+$("#centerId").val()+'&origenPantalla=AVISOS'; 

	$( "#p19_mensajeRecordatorioCalendarioEnlace" ).unbind("click");
	$( "#p19_mensajeRecordatorioCalendarioEnlace" ).click(function() {
    	window.location=url;
    });
}

function p19RecalcularEnlaceCalendarioPlataforma(){

	var url = './calendario.do?center='+$("#centerId").val()+'&origenPantalla=AVISOSE'; 

	$( "#p19_mensajeRecordatorioCalendarioPlataformaEnlace" ).unbind("click");
	$( "#p19_mensajeRecordatorioCalendarioPlataformaEnlace" ).click(function() {
    	window.location=url;
    });
}

function p19RecalcularEnlaceCalendarioKO(){

	var url = './calendario.do?center='+$("#centerId").val() +'&origenPantalla=AVISOSE'; 

	$( "#p19_mensajeRecordatorioCalendarioKOEnlace" ).unbind("click");
	$( "#p19_mensajeRecordatorioCalendarioKOEnlace" ).click(function() {
    	window.location=url;
    });
}

function p19RecalcularEnlaceEntradas(){

	var url = './entradasDescentralizadas.do?center='+$("#centerId").val()+'&origenPantalla=AVISOS'; 

	$( "#p19_mensajeRecordatorioEntradasEnlace" ).unbind("click");
	$( "#p19_mensajeRecordatorioEntradasEnlace" ).click(function() {
    	window.location=url;
    });
}

function p19RecalcularEnlacePedidoAdicional(tamano){
	
	//var url = './pedidoAdicional.do?flagCancelarNuevo=S&pestanaOrigen=AVISOS_MO&flgReferenciaCentro=N&mac=S';

	/*for (i = 0; i < tamano; i++) {
		$( "#p19_mensajeRecordatorioPedidoAdicionalEnlace" + i ).unbind("click");
		$( "#p19_mensajeRecordatorioPedidoAdicionalEnlace" + i  ).click(function(e) {
			var id = e.target.id;
	    	window.location= './pedidoAdicional.do?flagCancelarNuevo=S&pestanaOrigen=AVISOS_MO&flgReferenciaCentro=N&mac=S&descPeriodo=' + arrayDescPeriodo[id.substring(id.length-1)];

	    });
		
	}*/
	//Si es de tipo montaje adicional en oferta
	$( ".p19tipoDePedidoAdicional2").unbind("click");
	$( ".p19tipoDePedidoAdicional2").click(function(e) {
		var id = e.target.id;
    	window.location= './pedidoAdicional.do?flagCancelarNuevo=S&pestanaOrigen=AVISOS_M&flgReferenciaCentro=N&mac=S&descPeriodo=' + arrayDescPeriodo[id.substring(id.length-1)];
    });
	
	//Si es de tipo montaje adicional
	$( ".p19tipoDePedidoAdicional3").unbind("click");
	$( ".p19tipoDePedidoAdicional3").click(function(e) {
		var id = e.target.id;
    	window.location= './pedidoAdicional.do?flagCancelarNuevo=S&pestanaOrigen=AVISOS_MO&flgReferenciaCentro=N&mac=S&descPeriodo=' + arrayDescPeriodo[id.substring(id.length-1)];
    });
}

function initializeScreenTablonPopup(){
	$( "#p19_tablonAnuncios" ).dialog({
        autoOpen: false,
        height: 'auto',
        width: 'auto',
        modal: true,
        title: p19TituloVentanaTablonPopup,
        resizable: false,
        buttons:[{
            text: p19BotonAceptar,
            click: function() {
                      $(this).dialog('close');
                   }
        }],
		open: function() {
			$('.ui-dialog-titlebar-close').on('mousedown', function(){
				$("#p19_tablonAnuncios").dialog('close');
			});
		}
    });
	
	$(window).bind('resize', function() {
	    $("#p19_tablonAnuncios").dialog("option", "position", "center");
	});
}


function loadP19(locale){
	this.i18nJSON = './misumi/resources/p19TablonAnunciosPopup/p19tablonAnunciosPopup_' + locale + '.json';
	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				p19BotonAceptar = data.p19BotonAceptar;
				p19TituloVentanaTablonPopup = data.p19TituloVentanaTablonPopup;
				p19mensajePedidoAdicional= data.p19mensajePedidoAdicional;
				p19mensajeAvisoDevolucion= data.p19mensajeAvisoDevolucion;
				p19mensajeAvisoCalendario= data.p19mensajeAvisoCalendario;
				p19mensajeAvisoDevolucionUrgente = data.p19mensajeAvisoDevolucionUrgente;
				p19mensajeAvisoEntrada= data.p19mensajeAvisoEntrada;
				p19mensajeAvisoAlarmasPLU = data.p19mensajeAvisoAlarmasPLU;
				p19mensajeAvisoMostrador = data.p19mensajeAvisoMostrador;
				p19mensajeAvisoFacingCero = data.p19mensajeAvisoFacingCero;
				initializeScreenTablonPopup();
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
           });
}

function createCalendarioLimiteP19(fecha){
	//De la fecha limite obtenemos el dia por un lado y el mes por otro
	var fechaLimiteAuxP19  = fecha.split("-");
	var anioFechaLimiteP19 = fechaLimiteAuxP19[0];
	var mesFechaLimiteP19  = fechaLimiteAuxP19[1]; 
	var diaFechaLimiteP19  = fechaLimiteAuxP19[2]; 

	//Obtenemo literal del mes
	var literalMesFechaLimiteP19 = obtenerLiteralMesP19(diaFechaLimiteP19,mesFechaLimiteP19,anioFechaLimiteP19);

	return diaFechaLimiteP19 + "-" + literalMesFechaLimiteP19;
}

function obtenerLiteralMesP19(dia, mes, anio){
	// ENERO FEBRERO MARZO ABRIL MAYO JUNIO JULIO AGOSTO SEPTIEMBRE OCTUBRE NOVIEMBRE DICIEMBRE
	var nombreMes = $.datepicker.formatDate("MM", new Date(anio, mes - 1, dia),{
		monthNames: $.datepicker.regional[ "es" ].monthNames
	});

	//Pasar de ENERO a Enero, FEBRERO Febrero
	//nombreMes = nombreMes.replace(/\w\S*/g, nombreMes.charAt(0).toUpperCase() + nombreMes.substr(1).toLowerCase());
	return nombreMes;
}