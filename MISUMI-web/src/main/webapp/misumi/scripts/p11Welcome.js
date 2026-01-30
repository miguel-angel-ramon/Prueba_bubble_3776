var mensajePedidoAdicional=null;
var mensajeAvisoDevolucion=null;
var mensajeAvisoCalendario=null;
var mensajeAvisoCalendarioPlataforma=null;
var mensajeAvisoDevolucionUrgente=null;
var mensajeAvisoEntrada=null;
var mensajeAvisoPLU=null;
var mensajeAvisoMostrador=null;
var mensajeAvisoFacingCero=null;
var mensajeAvisoAjustePedidos = null;
var arrayDescPeriodo = new Array();

$(document).ready(function(){
	p11InitializeScreen();
});

function p11InitializeScreen(){
	p11Clear_center();
	
	$(document).on('CargadoCentro', function(e) {

		loadP11(locale);
		
	});
	
	//if ($("#userPerfil").val() == "2"){
		//$("#p01_txt_infoRef").focus();
	
//	}
	if ($("#centerId").val()!=null && $("#centerId").val()!=''){
	
		$("#p01_txt_infoRef").focus();
		
	}
	
}

function p11GetCentroUsuarioSession(){
	
	$.ajax({
		type : 'GET',
		url : './welcome/getAvisosCentro.do',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {	
			
				//Se habilitan los avisos correspondientes al centro
				var codigoAviso = null;
				var codigo = null;
				var aviso = null;
				var parrafoAvisoPedidoAdicional = "";
				var parrafoAvisoDevolucion = "";
				var parrafoAvisoDevolucionUrgente = "";
				var parrafoAvisoCalendario = "";
				var parrafoAvisoCalendarioPlataforma = "";
				var parrafoAvisoCalendarioKO = "";
				var parrafoAvisoEntradas = "";
				var parrafoAvisoPLU = "";
				var parrafoAvisoMostrador = "";
				var parrafoAvisoFacingCero = "";
				var parrafoAjustePedidos="";
				var mensajeAuxiliar = null;
				
				if (data!=null && data.centro != null){
					var codCentro = data.centro.codCentro;
					
					if (data.centro.listaAvisosCentro != null && data.centro.listaAvisosCentro.length > 0){
						 if ($('#p01_btn_lote_navidad').is(':visible')){
							 if ($('#InformeHuecos').is(':visible')){
								 $("#TablonAnuncios").addClass("ConLoteConInforme");
								 $("#TablonAnuncios").removeClass("ConLoteSinInforme");
								 $("#TablonAnuncios").removeClass("SinLoteConInforme");
								 $("#TablonAnuncios").removeClass("SinLoteSinInforme");
							 } else {
								 $("#TablonAnuncios").addClass("ConLoteSinInforme");
								 $("#TablonAnuncios").removeClass("ConLoteConInforme");
								 $("#TablonAnuncios").removeClass("SinLoteConInforme");
								 $("#TablonAnuncios").removeClass("SinLoteSinInforme");
							 }

						 } else {
							 
							 if ($('#InformeHuecos').is(':visible')){
								 $("#TablonAnuncios").addClass("SinLoteConInforme");
								 $("#TablonAnuncios").removeClass("ConLoteSinInforme");
								 $("#TablonAnuncios").removeClass("ConLoteConInforme");
								 $("#TablonAnuncios").removeClass("SinLoteSinInforme");
							 } else {
								 $("#TablonAnuncios").addClass("SinLoteSinInforme");
								 $("#TablonAnuncios").removeClass("ConLoteSinInforme");
								 $("#TablonAnuncios").removeClass("SinLoteConInforme");
								 $("#TablonAnuncios").removeClass("ConLoteConInforme");
							 }
							
						 }
						 $("#TablonAnuncios").attr("style", "display:inline;");
						 $("#p11_avisoValidarCantidadesExtraIcono").attr("style", "display:inline;");
						 $("#p11_mensajesAvisos").attr("style", "display:inline;");
						 $("#p11_cabeceraAvisos").attr("style", "display:inline;");
						 
						for (i = 0; i < data.centro.listaAvisosCentro.length; i++) {
							 codigoAviso = data.centro.listaAvisosCentro[i].split("**");
							 codigo = codigoAviso[0];
							 aviso = codigoAviso[1]; 

							 if(codigo != null && codigo == '1'){
								 $("#p11_avisoValidarCantidadesExtra").attr("style", "display:inline;");
								 p11RecalcularEnlaceValidarCantidadesExtra();
							 }
							 
							 if(codigo != null && codigo == '2'){
								 $("#p11_avisoPedidoAdicional").attr("style", "display:inline;");
								 
								 var tipoDeMontaje = codigoAviso[2]; 
								 mensajeAuxiliar = mensajePedidoAdicional;
								 mensajeAuxiliar = mensajeAuxiliar.replace('{0}',aviso).replace('{1}',i).replace('{2}',tipoDeMontaje);
								 parrafoAvisoPedidoAdicional = parrafoAvisoPedidoAdicional + "<div class='p11_avisosMensaje'><p class='p11_parrafoAvisoPedidoAdicional' id='p11_parrafoAvisoPedidoAdicional'>" + mensajeAuxiliar + "</p></div>";

								 arrayDescPeriodo[i] = aviso;
								 
							 }
							 
							 if(codigo != null && codigo == '3'){
								 $("#p11_avisoDevoluciones").attr("style", "display:inline;");							
								 
								 mensajeAuxiliar = mensajeAvisoDevolucion;
								 mensajeAuxiliar = mensajeAuxiliar.replace('{0}',aviso);
								 parrafoAvisoDevolucion = parrafoAvisoDevolucion + "<div class='p11_avisosMensaje'><p id='p11_parrafoDevoluciones'>" + mensajeAuxiliar + "</p></div>";
							 }
							 
							
							 if(codigo != null && codigo == '4'){
								 $("#p11_avisoCalendario").attr("style", "display:inline;");							
								 
								 mensajeAuxiliar =  mensajeAvisoCalendario ;
								 
								 //Obtenemos el mes y el día del calendario fecha limite.
								 var partesFechaP11 = createCalendarioLimiteP11(aviso).split("-");
								 var diaP11 = partesFechaP11[0];
								 var mesP11 = partesFechaP11[1];
								 
								 var elementosCalendario = "<div class='p11_avisosMensaje'>"
								 + 		"<div id='p11_Img'>"					
								 +  		"<div id='p11_mesCalendario'>"+mesP11+"</div>"
								 + 			"<div id='p11_diaCalendario'>"+diaP11+"</div>"
								 + 		"</div>"
								 + "</div>";
								 
								 mensajeAuxiliar = mensajeAuxiliar.replace('{0}',elementosCalendario);								
								 parrafoAvisoCalendario = parrafoAvisoCalendario + mensajeAuxiliar;
							 }	
							 
							 //Aviso Calendario plataforma
							 if(codigo != null && codigo == '5'){
								 $("#p11_avisoCalendarioPlataforma").attr("style", "display:inline;");							
								 
								 mensajeAuxiliar = "- " + aviso + ". ";
								 parrafoAvisoCalendarioPlataforma = parrafoAvisoCalendarioPlataforma + "<div class='p11_avisosMensaje'><p id='p11_parrafoCalendarioPlataforma'>" + mensajeAuxiliar + "<span id='p11_mensajeRecordatorioCalendarioPlataformaEnlace'>Pinchar aquí</span> </p>  </div> ";
							 }
							 
							 if(codigo != null && codigo == '6'){
								 $("#p11_avisoDevolucionesUrgente").attr("style", "display:inline;");							
								 
								 mensajeAuxiliar = mensajeAvisoDevolucionUrgente;
								 mensajeAuxiliar = mensajeAuxiliar.replace('{0}',aviso);
								 parrafoAvisoDevolucionUrgente = parrafoAvisoDevolucionUrgente + "<div class='p11_avisosMensaje'><p id='p11_parrafoDevolucionesUrgente'>" + mensajeAuxiliar + "</p></div>";
							 }
							 
							 if(codigo != null && codigo == '7'){
								 $("#p11_avisoCalendarioKO").attr("style", "display:inline;");							
								 
								 mensajeAuxiliar = "- " + aviso + ". ";
								 parrafoAvisoCalendarioKO = parrafoAvisoCalendarioKO + "<div class='p11_avisosMensaje'><p id='p11_parrafoCalendarioKO'>" + mensajeAuxiliar + "<span id='p11_mensajeRecordatorioCalendarioKOEnlace'>Pinchar aquí</span> </p>  </div> ";
							 }
							 
							 if(codigo != null && codigo == '8'){
								 $("#p11_avisoEntradas").attr("style", "display:inline;");							
								 
								 mensajeAuxiliar = mensajeAvisoEntrada;
								 mensajeAuxiliar = mensajeAuxiliar.replace('{0}',aviso);
								 parrafoAvisoEntradas= parrafoAvisoEntradas + "<div class='p11_avisosMensaje'><p id='p11_parrafoEntradas'>" + mensajeAuxiliar + "</p></div>";
							 }
							 
							 if(codigo != null && codigo == '9'){
								 
								 $("#p11_avisoPLU").attr("style", "display:inline;");							
								 
								 mensajeAuxiliar = mensajeAvisoPLU;
								 mensajeAuxiliar = mensajeAuxiliar.replace('{0}',aviso);
								 parrafoAvisoPLU= parrafoAvisoPLU + "<div class='p11_avisosMensaje'><p id='p11_parrafoAvisoPLU'>" + mensajeAuxiliar + "</p></div>";
							 }
							 
							 if(codigo != null && codigo == '10'){
								 $("#p11_avisoMostrador").attr("style", "display:inline;");							
								 
								 mensajeAuxiliar = mensajeAvisoMostrador;
								 mensajeAuxiliar = mensajeAuxiliar.replace('{0}',aviso);
								 parrafoAvisoMostrador= parrafoAvisoMostrador + "<div class='p11_avisosMensaje'><p id='p11_parrafoAvisoMostrador'>" + mensajeAuxiliar + "</p></div>";
							 }

							 if(codigo != null && codigo == '11'){
								 $("#p11_avisoFacingCero").attr("style", "display:inline;");							
								 
								 mensajeAuxiliar = mensajeAvisoFacingCero;
								 mensajeAuxiliar = mensajeAuxiliar.replace('{0}',aviso);
								 parrafoAvisoFacingCero = parrafoAvisoFacingCero + "<div class='p11_avisosMensaje'><p id='p11_parrafoAvisoFacingCero'>" + mensajeAuxiliar + "</p></div>";
							 }
							 
							 if(codigo != null && codigo == '12'){
								 $("#p11_avisoAjustePedidos").attr("style", "display:inline;");					
								 parrafoAjustePedidos = "<div class='p11_avisosMensaje p11_aviso'><p id='p11_parrafoAvisoAjustePedidos' class='p11_parrafoAviso'>" + mensajeAvisoAjustePedidos + "</p></div>";
							 }
						}

						$("#p11_avisoPedidoAdicional").html(parrafoAvisoPedidoAdicional);
						p11RecalcularEnlacePedidoAdicional(data.centro.listaAvisosCentro.length, arrayDescPeriodo);
						$("#p11_avisoDevoluciones").html(parrafoAvisoDevolucion);
						p11RecalcularEnlaceDevoluciones();
						$("#p11_avisoCalendario").html(parrafoAvisoCalendario);
						p11RecalcularEnlaceCalendario();
						$("#p11_avisoCalendarioPlataforma").html(parrafoAvisoCalendarioPlataforma);
						p11RecalcularEnlaceCalendarioPlataforma();
						$("#p11_avisoDevolucionesUrgente").html(parrafoAvisoDevolucionUrgente);
						p11RecalcularEnlaceDevolucionesUrgente();
						$("#p11_avisoCalendarioKO").html(parrafoAvisoCalendarioKO);
						p11RecalcularEnlaceCalendarioKO();
						$("#p11_avisoEntradas").html(parrafoAvisoEntradas);
						p11RecalcularEnlaceEntradas();
						$("#p11_avisoPLU").html(parrafoAvisoPLU);
						p11RecalcularEnlaceAvisosPLU();
						$("#p11_avisoMostrador").html(parrafoAvisoMostrador);
						p11RecalcularEnlaceAvisosMostrador();
						$("#p11_avisoFacingCero").html(parrafoAvisoFacingCero);
						p11RecalcularEnlaceAvisosFacingCero();
						
						$('#p11_avisoAjustePedidos').html(parrafoAjustePedidos);
						p11RecalcularEnlaceAjustePedidos();
						
					}else{
						 $("#TablonAnuncios").attr("style", "display:none;");
					}
					
				}else{
					$("#TablonAnuncios").attr("style", "display:none;");
					$("#p11_avisoValidarCantidadesExtraIcono").attr("style", "display:none;");
					$("#p11_mensajesAvisos").attr("style", "display:none;");
				}				
		},
		error : function (xhr, status, error){
					handleError(xhr, status, error, locale);				
				}			
	});	
	
}

function p11RecalcularEnlaceAvisosPLU(){
	var url = './alarmasPLU.do?center='+$("#centerId").val();

	$( "#p11_mensajeRecordatorioAlarmasPLUEnlace" ).unbind("click");
	$( "#p11_mensajeRecordatorioAlarmasPLUEnlace" ).click(function() {
    	window.location=url;
    });
}

function p11RecalcularEnlaceAvisosMostrador(){
	var url = './detalladoMostrador.do?center='+$("#centerId").val();

	$( "#p11_mensajeRecordatorioAvisoMostradorEnlace" ).unbind("click");
	$( "#p11_mensajeRecordatorioAvisoMostradorEnlace" ).click(function() {
    	window.location=url;
    });
}

function p11RecalcularEnlaceAvisosFacingCero(){
	var url = './facingCero.do?center='+$("#centerId").val();

	$( "#p11_mensajeRecordatorioFacingCeroEnlace" ).unbind("click");
	$( "#p11_mensajeRecordatorioFacingCeroEnlace" ).click(function() {
    	window.location=url;
    });
}

function p11RecalcularEnlaceValidarCantidadesExtra(){

	var url = './pedidoAdicional.do?flagCancelarNuevo=S&pestanaOrigen=AVISOS_VC&flgReferenciaCentro=N';

	$( "#p11_mensajeRecordatorioValidarCantidadesExtraEnlace" ).unbind("click");
	$( "#p11_mensajeRecordatorioValidarCantidadesExtraEnlace" ).click(function() {
    	window.location=url;
    });
}

function p11RecalcularEnlaceDevoluciones(){

	var url = './devoluciones.do?center='+$("#centerId").val()+'&origenPantalla=AVISOS'; 

	$( "#p11_mensajeRecordatorioDevolucionesEnlace" ).unbind("click");
	$( "#p11_mensajeRecordatorioDevolucionesEnlace" ).click(function() {
    	window.location=url;
    });
}

function p11RecalcularEnlaceDevolucionesUrgente(){

	var url = './devoluciones.do?center='+$("#centerId").val()+'&origenPantalla=AVISOS'; 

	$( "#p11_mensajeRecordatorioDevolucionesEnlaceUrgente" ).unbind("click");
	$( "#p11_mensajeRecordatorioDevolucionesEnlaceUrgente" ).click(function() {
    	window.location=url;
    });
}

function p11RecalcularEnlaceCalendario(){

	var url = './calendario.do?center='+$("#centerId").val()+'&origenPantalla=AVISOS'; 

	$( "#p11_mensajeRecordatorioCalendarioEnlace" ).unbind("click");
	$( "#p11_mensajeRecordatorioCalendarioEnlace" ).click(function() {
    	window.location=url;
    });
}

function p11RecalcularEnlaceCalendarioPlataforma(){

	var url = './calendario.do?center='+$("#centerId").val()+'&origenPantalla=AVISOSE'; 

	$( "#p11_mensajeRecordatorioCalendarioPlataformaEnlace" ).unbind("click");
	$( "#p11_mensajeRecordatorioCalendarioPlataformaEnlace" ).click(function() {
    	window.location=url;
    });
}

function p11RecalcularEnlaceCalendarioKO(){

	var url = './calendario.do?center='+$("#centerId").val()+'&origenPantalla=AVISOSE'; 

	$( "#p11_mensajeRecordatorioCalendarioKOEnlace" ).unbind("click");
	$( "#p11_mensajeRecordatorioCalendarioKOEnlace" ).click(function() {
    	window.location=url;
    });
}

function p11RecalcularEnlaceEntradas(){

	var url = './entradasDescentralizadas.do?center='+$("#centerId").val()+'&origenPantalla=AVISOS'; 

	$( "#p11_mensajeRecordatorioEntradasEnlace" ).unbind("click");
	$( "#p11_mensajeRecordatorioEntradasEnlace" ).click(function() {
    	window.location=url;
    });
}

function p11RecalcularEnlacePedidoAdicional(tamano){
	

	/*for (i = 0; i < tamano; i++) {
		
		$( "#p11_mensajeRecordatorioPedidoAdicionalEnlace" + i ).unbind("click");
		$( "#p11_mensajeRecordatorioPedidoAdicionalEnlace" + i  ).click(function(e) {
			var id = e.target.id;
	    	window.location= './pedidoAdicional.do?flagCancelarNuevo=S&pestanaOrigen=AVISOS_MO&flgReferenciaCentro=N&mac=S&descPeriodo=' + arrayDescPeriodo[id.substring(id.length-1)];
	    });
		
	}*/
	//Si es de tipo montaje adicional en oferta
	$( ".tipoDePedidoAdicional2").unbind("click");
	$( ".tipoDePedidoAdicional2").click(function(e) {
		var id = e.target.id;
    	window.location= './pedidoAdicional.do?flagCancelarNuevo=S&pestanaOrigen=AVISOS_M&flgReferenciaCentro=N&mac=S&descPeriodo=' + arrayDescPeriodo[id.substring(id.length-1)];
    });
	
	//Si es de tipo montaje adicional
	$( ".tipoDePedidoAdicional3").unbind("click");
	$( ".tipoDePedidoAdicional3").click(function(e) {
		var id = e.target.id;
    	window.location= './pedidoAdicional.do?flagCancelarNuevo=S&pestanaOrigen=AVISOS_MO&flgReferenciaCentro=N&mac=S&descPeriodo=' + arrayDescPeriodo[id.substring(id.length-1)];
    });
}


function p11RecalcularEnlaceAjustePedidos(){
	
	var url = './misPedidos.do?pestanaOrigen=Welcome';

	$( "#p11_mensajeAjustePedidosEnlace" ).unbind("click");
	$( "#p11_mensajeAjustePedidosEnlace" ).click(function() {
    	window.location=url;
    });
}

function p11Clear_center(){
	$( "#centerName" ).bind('focus', function() {
		$("#p11_avisoValidarCantidadesExtra").attr("style", "display:none;");
    });
}

function p11GetAviso(){
	var mensajes="";
	$.ajax({
		type : 'POST',
		url : './welcome/getAviso.do',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {				
						
				if (data[0]!=null && data[0].mensaje != null){
					 $("#p11_aviso").attr("style", "display:inline;");
					 for (i = 0; i < data.length; i++){
						 mensajes=mensajes+"<BR>"+data[i].mensaje;
					 }
					 $("#p11_avisoMens").html(mensajes);
				}else{
					 $("#p11_aviso").attr("style", "display:none;");
				}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
       }			
	});	
	
}

function loadP11(locale){
	
	this.i18nJSON = './misumi/resources/p11Welcome/p11Welcome_' + locale + '.json';
	
	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {
		
	})
	.success(function(data) {
		
		mensajePedidoAdicional= data.mensajePedidoAdicional;
		mensajeAvisoDevolucion= data.mensajeAvisoDevolucion;
		mensajeAvisoCalendario= data.mensajeAvisoCalendario;
		mensajeAvisoDevolucionUrgente = data.mensajeAvisoDevolucionUrgente;
		mensajeAvisoEntrada= data.mensajeAvisoEntrada;
		mensajeAvisoPLU= data.mensajeAvisoPLU;
		mensajeAvisoMostrador= data.mensajeAvisoMostrador;
		mensajeAvisoFacingCero= data.mensajeAvisoFacingCero;
		mensajeAvisoAjustePedidos = data.mensajeAvisoAjustePedidos;
		
		p11GetAviso();
		p11GetCentroUsuarioSession();
		

	}).error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
    });
}

function createCalendarioLimiteP11(fecha){
	//De la fecha limite obtenemos el dia por un lado y el mes por otro
	var fechaLimiteAuxP11  = fecha.split("-");
	var anioFechaLimiteP11  = fechaLimiteAuxP11[0];
	var mesFechaLimiteP11  = fechaLimiteAuxP11[1]; 
	var diaFechaLimiteP11  = fechaLimiteAuxP11[2]; 

	//Obtenemo literal del mes
	var literalMesFechaLimiteP11 = obtenerLiteralMesP11(diaFechaLimiteP11,mesFechaLimiteP11,anioFechaLimiteP11);

	return diaFechaLimiteP11 + "-" + literalMesFechaLimiteP11;
}

function obtenerLiteralMesP11(dia, mes, anio){
	// ENERO FEBRERO MARZO ABRIL MAYO JUNIO JULIO AGOSTO SEPTIEMBRE OCTUBRE NOVIEMBRE DICIEMBRE
	var nombreMes = $.datepicker.formatDate("MM", new Date(anio, mes - 1, dia),{
		monthNames: $.datepicker.regional[ "es" ].monthNames
	});

	//Pasar de ENERO a Enero, FEBRERO Febrero
	//nombreMes = nombreMes.replace(/\w\S*/g, nombreMes.charAt(0).toUpperCase() + nombreMes.substr(1).toLowerCase());
	return nombreMes;
}