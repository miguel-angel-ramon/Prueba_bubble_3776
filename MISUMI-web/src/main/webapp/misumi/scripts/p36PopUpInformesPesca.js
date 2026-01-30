var p36BotonAceptar = null;
var p36mensajeInforme=null;
var arrayDescPeriodo = new Array();

var p36seleccionarHabitualNoHabitual;

$(document).ready(function(){

	loadP36(locale);

});

function p36loadDatos(flgHabitual){
	p36fijarAnchoLista();
	p36loadInformesPesca(flgHabitual);
}
function p36loadInformesPesca(flgHabitual){
	//Miramos la url a utilizar, si flgHabitual tiene dato,
	//esto es, si hay un checkbox seleccionado, se le pasa parámetro,
	//si no, no.
	var urlAUtilizar = './welcome/getInformesCentro.do';
	if(flgHabitual != null){
		urlAUtilizar = './welcome/getInformesCentro.do?flgHabitual='+flgHabitual;
	}
	$.ajax({
		type : 'POST',
		url : urlAUtilizar,
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

					// Iniciamos la lista ul
					parrafo = parrafo + '<ul id="p36_listaOrdenable">';

					var listaPesca = data.centro.informeListado.listaInformeListadoPesca;
					for (i = 0; i < listaPesca.length; i++) {
						informeListado = listaPesca[i];
						idSubcategoria = informeListado.identificaSubcat;
						descSubcategoria = informeListado.descSubcategoria;
						descripcion = informeListado.descripcion;

						// Añadimos el elemento a la lista
						parrafo = parrafo + 
						'<li id="' + idSubcategoria + '" class="p36_itemListaOrdenable">' + 
						'<span class="ui-icon ui-icon-arrowthick-2-n-s" style="float: left;">' +
						'</span>' + 
						'<div style="margin-left: 20px;">' + 
						descripcion + 
						'</div>' + 
						'<input type="hidden" name="listaPescaMostrador" value="' + idSubcategoria + '">' + 
						'</li>';
					}

					// Cerramos la lista ul
					parrafo = parrafo + '<ul>';

					// Insertamos la lista en el popup
					$("#p36_divListaOrdenable").html(parrafo);

					// Hacemos la lista que sea drag && drop
					$( "#p36_listaOrdenable" ).sortable({ axis: "y", containment: "#p36_cuerpoInformesPesca", scroll: true, scrollSensitivity: 100, scrollSpeed: 100 });
					$( "#p36_listaOrdenable" ).disableSelection();
					
					$("#p36_divListaOrdenable").append('<input type="hidden" name="flgHabitual" value="' + flgHabitual + '">');
				}else{
					//Si no hay informes, vaciamos la tabla.
					$("#p36_divListaOrdenable").empty();

					parrafo = parrafo + '<div id="p36RefAMostrarMsg" class="p36_itemListaNoOrdenable">'+p36SeleccionarReferenciasAMostrar+'</div>';

					// Insertamos la lista en el popup
					$("#p36_divListaOrdenable").html(parrafo);
				}
			}

			//En el titulo del pop up, tiene que aparecer la fecha del dia en rojo
			fechaFormateadaTitulo = $.datepicker.formatDate("DD dd-MM", new Date(),{
				dayNamesShort: $.datepicker.regional[ "es" ].dayNames,
				dayNames: $.datepicker.regional[ "es" ].dayNames,
				monthNamesShort: $.datepicker.regional[ "es" ].monthNames,
				monthNames: $.datepicker.regional[ "es" ].monthNames
			});

			p36tituloPopupFechaSustituido = p36tituloPopupFecha.replace('{0}',fechaFormateadaTitulo);

			$("#p36_popupInformesPesca").dialog( "option", "title", p36tituloPopupFechaSustituido + " " + p36tituloPopup);

			$("#p36_popupInformesPesca").dialog( "open" );
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});	

}

function p36fijarAnchoLista(){
	$.ajax({
		type : 'POST',
		url : './welcome/countHabitualNoHabitual.do',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {				
			if(data != null){
				heightTotal = (26*data);
				$("#p36_divListaOrdenable").css("height", heightTotal);
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});
}

function p36RecalcularEnlaceValidarCantidadesExtra(){

	var url = './pedidoAdicional.do?flagCancelarNuevo=S&pestanaOrigen=AVISOS_VC&flgReferenciaCentro=N';
	$( "#p36_mensajeRecordatorioValidarCantidadesExtraEnlace" ).unbind("click");
	$( "#p36_mensajeRecordatorioValidarCantidadesExtraEnlace" ).click(function() {
		window.location=url;
	});
}

function p36RecalcularEnlacePedidoAdicional(tamano){

	//var url = './pedidoAdicional.do?flagCancelarNuevo=S&pestanaOrigen=AVISOS_MO&flgReferenciaCentro=N&mac=S';

	for (i = 0; i < tamano; i++) {
		$( "#p36_mensajeRecordatorioPedidoAdicionalEnlace" + i ).unbind("click");
		$( "#p36_mensajeRecordatorioPedidoAdicionalEnlace" + i  ).click(function(e) {
			var id = e.target.id;
			window.location= './pedidoAdicional.do?flagCancelarNuevo=S&pestanaOrigen=AVISOS_MO&flgReferenciaCentro=N&mac=S&descPeriodo=' + arrayDescPeriodo[id.substring(id.length-1)];

		});

	}
}

function event_onlyOnecheckBox(){
	$(".p36_check").click(function(){
		var noHabitualCheck = $("#p36_checkBoxNoHabitual").is(":checked");
		var habitualCheck = $("#p36_checkBoxHabitual").is(":checked");

		//Si solo hay un checkbox seleccionado
		var flgHabitual = null;
		if(habitualCheck){
			flgHabitual = $("#p36_checkBoxHabitual").val();
		}
		if(noHabitualCheck){
			if(flgHabitual != null){				
				flgHabitual = flgHabitual +','+ $("#p36_checkBoxNoHabitual").val();
			}else{
				flgHabitual = $("#p36_checkBoxNoHabitual").val();
			}
		}
		p36loadInformesPesca(flgHabitual);		
	});
}
function initializeScreenInformesPescaPopup(){
	$( "#p36_popupInformesPesca" ).dialog({
		autoOpen: false,
		height: 'auto',
		width: 'auto',
		modal: true,
		resizable: false,
		buttons:[{
			text: p36BotonAceptar,
			click: function() {
				var noHabitualCheck = $("#p36_checkBoxNoHabitual").is(":checked");
				var habitualCheck = $("#p36_checkBoxHabitual").is(":checked");

				if(noHabitualCheck || habitualCheck){
					// Aquí obtenemos la lista ordenada que hay que sacar
					// en el documento PDF
					var listaOrdenada = $( "#p36_listaOrdenable" ).sortable( "toArray" );
					//console.log(listaOrdenada);					

					// Aquí hay que llamar a generar el documento PDF

					/*
            	var strWindowFeatures = "";//"location=yes,height=570,width=520,scrollbars=yes,status=yes";
            	var URL = "./p36ListadoPescaMostrador/getPdf.do";//?lista=" + listaOrdenada;
            	var win = window.open(URL, "_blank", strWindowFeatures);
					 */
					$( "#p36_formListadoPescaMostrador" ).submit();

					// Cerramos el dialog.
					$(this).dialog('close');
				}else{
					createAlert(replaceSpecialCharacters(p36seleccionarHabitualNoHabitual),"ERROR");
				}
			}
		}],
		open: function() {
			//Al abrir el popup, seleccionamos el checkbox habitual y deseleccionamos el no habitual.
			$('#p36_checkBoxNoHabitual').prop("checked", false);
			$('#p36_checkBoxHabitual').prop("checked", true);

			$('.ui-dialog-titlebar-close').on('mousedown', function(){
				$("#p36_popupInformesPesca").dialog('close');
			});
		}
	});

	$(window).bind('resize', function() {
		$("#p36_popupInformesPesca").dialog("option", "position", "center");
	});
}


function loadP36(locale){
	this.i18nJSON = './misumi/resources/p36InformesPescaPopup/p36informesPescaPopup_' + locale + '.json';
	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		p36BotonAceptar = data.p36BotonAceptar;
		p36mensajeInforme= data.p36mensajeInforme;
		p36tituloPopupFecha= data.p36tituloPopupFecha;
		p36tituloPopup= data.p36tituloPopup;
		p36seleccionarHabitualNoHabitual = data.p36seleccionarHabitualNoHabitual;
		p36SeleccionarReferenciasAMostrar = data.p36SeleccionarReferenciasAMostrar;

		initializeScreenInformesPescaPopup();

		event_onlyOnecheckBox();
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}
