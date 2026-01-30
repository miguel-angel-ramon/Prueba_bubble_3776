var validandoReferencia = false;
var validReferencia = false;
var validCargaDatosMaestro = false;
var validAgrupacion = false;
var p71Cajas = null;
var p71Unidades = null;
var esReferenciaSIA = null;
var p71CodError = null;
var p71DescError = null;

$(document).ready(function(){

	initializeScreenP71();
	reloadDatosReferencia();
});


function initializeScreenP71(){

	$("#p71_fld_cajas").filter_input({regex:'[0-9,]'});
	$("#p71_fld_cajas").attr("disabled", "disabled");
	$("#p71_fld_referencia").filter_input({regex:'[0-9]'});

	$("#p71_fld_referencia").keydown(function(e) {
		if (e.which == 13){//Tecla intro,
			validandoReferencia = true;
			incluirP71();
		}else{
			validReferencia = false;
			validCargaDatosMaestro = false;
			validAgrupacion = false;
		}
	});

	$("#p71_fld_cajas").keydown(function(e) {
		if (e.which == 13){//Tecla intro,
			//Se quita el evento change para que no salte dos veces checkCajas cuando se introduce una cantidad en cajas y pulsas enter.
			//Se evita que se ejecute el evento change en este caso.
			$("#p71_fld_cajas").unbind("change", checkCajas);
			if (checkCajas()){
				if (insertReference("S")!="1"){
				}
			}
			//Volvemos a activar el evento change una vez ejecutado checkCajas
			$("#p71_fld_cajas").bind("change", checkCajas);
		}
	});
	$("#p71_fld_cajas").bind("change", checkCajas);


	//Nos posicionamos en el campo al que ha navegado el usuario y que hemos almacenado en el hidden.
	$("#p03_btn_aceptar").bind("click", function(e) {
		$("#p71_fld_referencia").focus();
		$("#p71_fld_referencia").select();
		e.stopPropagation();
	});  

	//Inicializamos el popup de nuevo detallado.
	$( "#p71_AreaModificacion" ).dialog({
		autoOpen: false,
		height: 270,
		width: 650,
		modal: true,
		resizable: false,
		buttons:[{
			text: "Incluir",
			id: "p71_btn_incluir",
			click: function() {
				incluirP71();
			}
		},{
			text: "Volver",
			click: function() {
				clear71ScreenPopup();
				//reloadGrid('S');
				loadContadoresDetallado('S');
				$(this).dialog('close');
			}
		}

		],
		open: function() {
			$('.ui-dialog-titlebar-close').on('mousedown', function(){
				clear71ScreenPopup();
				$("#p71_AreaModificacion").dialog('close');
			});
		},
		close: function() {
			//Enseñamos el botón.
			$("#p71_btn_incluir").show();
		}
	});
	loadP71(locale);
}

function incluirP71(){
	if (getP71FldReferencia()==null || getP71FldReferencia()==""){
		createAlert(replaceSpecialCharacters(referenceEmpty), "ERROR");
	}else{
		if (validReferencia){
			if (validCargaDatosMaestro){
			}else{
				if(esReferenciaSIA){
					loadP70DatosMaestroSIA(locale);
				}else{
					loadP70DatosMaestro(locale);
				}	
			}
			if (!validCajas){
				if (checkCajas()){
					if (insertReference("S")!="1"){

					}	            	
				}
			}else{
				if (insertReference("S")!="1"){

				}	            	
			}
		}else{
			controlesReferencia();
		}
	}
	validandoReferencia = false;

}

function checkCajas(){
	//Si es una referencia de SIA puede dar un error el procedimiento que sacamos por pantalla
//	if(esReferenciaSIA && (p71CodError!=0 && p71CodError != null)){
//	createAlert(replaceSpecialCharacters(p71DescError), "ERROR");
//	validCajas = false;
//	return false;
//	}else{
	if ($("#p71_fld_cajas").val()==null || $("#p71_fld_cajas").val()=="" || $("#p71_fld_cajas").val()<1 ||$("#p71_fld_cajas").val()>999){
		createAlert(replaceSpecialCharacters(boxesIncorrect), "ERROR");
		validCajas = false;
		return false;
	}else if ($("#p71_txtgrupo1").val()==null ||  $("#p71_txtgrupo1").val()=="" ||
			$("#p71_txtgrupo2").val()==null ||  $("#p71_txtgrupo2").val()=="" ||
			$("#p71_txtgrupo3").val()==null ||  $("#p71_txtgrupo3").val()=="" ||
			$("#p71_txtgrupo4").val()==null ||  $("#p71_txtgrupo4").val()=="" ||
			$("#p71_txtgrupo5").val()==null ||  $("#p71_txtgrupo5").val()=="" )
	{
		createAlert(replaceSpecialCharacters(selectReference), "ERROR");
		validCajas = false;
		return false;
	}else{

		if ($("#p71_tipoUfp").val() == "U") {				
			var cantidad = Number( $("#p71_fld_cajas").val());
			var mod = cantidad%$("#p71_dexenx").val();
			if( mod != 0){
				var suma = $("#p71_dexenx").val() - mod;
				cantidad = cantidad + suma;
				$("#p71_fld_cajas").val(cantidad);
			} 			
		} 


		validCajas = true
		return true;
	}
//	}
}

function insertReference(origen){

	//Control de la estructura buscada con la estructura de la referencia
	var seccionBuscada = $("#p70_cmb_seccion_b").val();
	var categoriaBuscada = $("#p70_cmb_categoria_b").val();
	var seccionReferencia = $("#p71_txtgrupo2").val();
	var categoriaReferencia = $("#p71_txtgrupo3").val();
	if ((seccionBuscada != null && seccionBuscada != '' && seccionBuscada != seccionReferencia) || 
			(categoriaBuscada != null && categoriaBuscada != '' && categoriaBuscada != categoriaReferencia)){//Control de sección y categoría del listado
		createAlert(agrupacionNoPermitida, "ERROR");
	}else{
		/*$.ajax({
			type : 'GET',
			url : './detalladoPedido/validateAgrupacion.do?grupo1='+$("#p71_txtgrupo1").val()+'&grupo2='+$("#p71_txtgrupo2").val()+'&grupo3='+$("#p71_txtgrupo3").val()+'&grupo4='+$("#p71_txtgrupo4").val()+'&grupo5='+$("#p71_txtgrupo5").val(),
			dataType : "json",
			success : function(data){
				if(data!=null && data!=""){
					createAlert(data, "ERROR");
				}else{*/
		var mydata = 
		{codCentro:$("#centerId").val(),grupo1:$("#p71_txtgrupo1").val(),grupo2:$("#p71_txtgrupo2").val(),
				grupo3:$("#p71_txtgrupo3").val(),grupo4:$("#p71_txtgrupo4").val(),grupo5:$("#p71_txtgrupo5").val(),
				codArticulo:getP71FldReferencia(),descriptionArt:getP71FldDescripcionRef(),
				codArticuloEroski:getP71FldReferenciaEroski(),descriptionArtEroski:getP71FldDescripcionRefEroski(),stock:trasformToString($("#p71_fld_stock").val()),
				nextDayPedido:trasformToString($("#p71_fld_nextDay_BD").val()),enCurso1:trasformToString($("#p71_fld_cajasPendH").val()),enCurso2:trasformToString($("#p71_fld_cajasPendM").val()),unidadesCaja:trasformToString($("#p71_fld_uc").val()),cajasPedidas:"0",
				propuesta:"0",cantidad:$("#p71_fld_cajas").val(),tipoDetallado:"A",estadoGrid:"1",flgSIA:esReferenciaSIA,flgOferta:$("#p71_flgOferta").val()

		}
		;

		var objJson = $.toJSON(mydata);	
		$.ajax({
			type : 'POST',
			url : './detalladoPedido/insertNewRecord.do',
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			cache : false,
			success : function(data) {				
				if (data!="1" && data!="2"){

					clear71ScreenPopup();
					if (origen == "S")
					{
						//En este caso hemos guardado tras pulsar intro.
						$("#p71_insertado").html(replaceSpecialCharacters(referenceIncluded));
						$("#p71_insertado").show();
						$("#p71_fld_referencia").focus();

					}
					else
					{
						//En este caso hemos guardado a través del botón incluir
						//reloadGrid('S');
						loadContadoresDetallado('S');
						$("#p71_AreaModificacion" ).dialog('close');
					}
					//Añadimos el modificado.
					ponerModificado();
				}else{
					if (data =="1") {
						$("#p71_error").html(replaceSpecialCharacters(referencePreviouslyInserted));
						$("#p71_error").show();
						$("#p71_fld_referencia").focus();
						$("#p71_fld_referencia").select();
					}

					if (data =="2") {
						$("#p71_error").html(replaceSpecialCharacters(errorRedondeoCantidadInsert));
						$("#p71_error").show();
						$("#p71_fld_referencia").focus();
						$("#p71_fld_referencia").select();
					}


				}
			},
			error : function (xhr, status, error){
				handleError(xhr, status, error, locale);
				return null;
			}			
		});		
		/*}
			},
			error : function (xhr, status, error){
				 createAlert(xhr.responseText, "ERROR");
			}			
		});*/
	}

}

function controlesReferencia(){
	limpiarMensajesP71();
	$("#p71_fld_cajas").val("");
	if (getP71FldReferencia()!=null && getP71FldReferencia()!=""){

		validCajas = false;
		validCargaDatosMaestro = false;
		cargaDatosMaestro();

	}else{

	}
}

function reloadDatosReferencia() {
	$("#p71_fld_referencia").change(function(e) {
		if(!validandoReferencia){
			controlesReferencia();
		}
	});
}


function checkValidReference(){
	$.ajax({
		type : 'GET',
		url : './detalladoPedido/validateReference.do?codArt='+getP71FldReferencia()+'&centerId='+$("#centerId").val(),
		async: false,
		success : function(data){
			if(data!=null && data.valido!=""){

				if (data.valido == 'S'){
					//Es una referencia valida de SIA.
					esReferenciaSIA = "S";
					validReferencia = true;
				} else {
					createAlert(data.valido, "ERROR");
					validReferencia = false;
				}

			}else{
				validReferencia = true;
				esReferenciaSIA = null;
			}
			if(data!=null){
				setP71FldReferenciaEroski(data.codArtEroski)
				setP71FldEsCaprabo(data.esCaprabo)
			}
		},
		error : function (xhr, status, error){
			createAlert(xhr.responseText, "ERROR");
			validReferencia = false;
		}			
	});
}


function cargaDatosMaestro(){
	checkValidReference();

	if (validReferencia){
		if(esReferenciaSIA){
			loadP70DatosMaestroSIA(locale);
		}else{
			loadP70DatosMaestro(locale);
		}
	}
}

function checkValidAgrupacion(){
	//Control de la estructura buscada con la estructura de la referencia
	var seccionBuscada = $("#p70_cmb_seccion_b").val();
	var categoriaBuscada = $("#p70_cmb_categoria_b").val();
	var seccionReferencia = $("#p71_txtgrupo2").val();
	var categoriaReferencia = $("#p71_txtgrupo3").val();
	if ((seccionBuscada != null && seccionBuscada != '' && seccionBuscada != seccionReferencia) || 
			(categoriaBuscada != null && categoriaBuscada != '' && categoriaBuscada != categoriaReferencia)){//Control de sección y categoría del listado
		createAlert(agrupacionNoPermitida, "ERROR");
		validAgrupacion = false;
	}else{
		/*$.ajax({
			type : 'GET',
			url : './detalladoPedido/validateAgrupacion.do?grupo1='+$("#p71_txtgrupo1").val()+'&grupo2='+$("#p71_txtgrupo2").val()+'&grupo3='+$("#p71_txtgrupo3").val()+'&grupo4='+$("#p71_txtgrupo4").val()+'&grupo5='+$("#p71_txtgrupo5").val(),
			dataType : "json",
			async: false,
			success : function(data){
				if(data!=null && data!=""){
					createAlert(data, "ERROR");
					validAgrupacion = false;
				}
				validAgrupacion = true;
			},
			error : function (xhr, status, error){
				 createAlert(xhr.responseText, "ERROR");
				 validAgrupacion = false;
			}			
		});*/
		validAgrupacion = true;
	}
}

function loadP70DatosMaestro(locale){

	var vReferenciasCentro=new ReferenciasCentro(getP71FldReferenciaEroski(), $("#centerId").val(), "ORIGEN_DETALLADO");
	var objJson = $.toJSON(vReferenciasCentro);	
	$.ajax({
		type : 'POST',
		url : './referenciasCentro/loadDatosMaestrosFijoDet.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		async :false,
		success : function(data) {	
			p71CodError = data.codError;
			p71DescError = data.descError;



			if ( data.length == 0 || 
					data.diarioArt == null || data.surtidoTienda == null || 
					data.diarioArt.length ==0 || data.surtidoTienda.length == 0 ){
				createAlert(replaceSpecialCharacters(emptyRecordReference), "ERROR");
				validCargaDatosMaestro = false;
			}else{
//				$("#p30_fld_mapaHoy").val(data.surtidoTienda.mapaHoy);
//				$("#p30_fld_refActiva").val(data.surtidoTienda.pedir);
//				p30ActivateFielsetPedidoAuto(data);
				if (getP71FldEsCaprabo() == "true"){
					setP71FldDescripcionRef(data.descArtCaprabo);
				}
				else{
					setP71FldDescripcionRef(data.diarioArt.descripArt);
				}
				setP71FldDescripcionRefEroski(data.diarioArt.descripArt);
				$("#p71_ufp").val(data.surtidoTienda.ufp);
				$("#p71_tipoUfp").val(data.tipoUFP);
				$("#p71_dexenx").val(data.dexenx);

				//$("#p71_dexenxEntero").val(data.dexenxEntero);

				$(".p71Cajas").text(p71Cajas);

				$("#p71_fld_nextDay_BD").val(data.nextDayPedido);
				$("#p71_fld_nextDay").val(formateoNextDay(data.nextDayPedido));
				$(".p71Upper").text($(".p71Upper").text().toString().toUpperCase());

				$("#p71_fld_uc").val(data.surtidoTienda.uniCajaServ).formatNumber({format:"0.##"});

				if (data.tipoUFP == "U") {
					if (data.dexenx > 1) {
						$("#p71_lbl_dexenx").html("De" + data.dexenx + "en" + data.dexenx);
						$("#p71_lbl_dexenx").show();
					}
				} else {
					$("#p71_lbl_dexenx").html("");
					$("#p71_lbl_dexenx").hide();
				}

				//getDescriptionInfoPopup70(data);
				//	loadPedidoBasicInfo();
				loadp70StockWS();
				loadp70PendientesRecibir(locale);
				loadp70Agrupacion();
				validCargaDatosMaestro = true;


			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
			validCargaDatosMaestro = false;
		}			
	});		

}


function loadP70DatosMaestroSIA(locale){

	var vReferenciasCentro=new ReferenciasCentro(getP71FldReferenciaEroski(), $("#centerId").val());
	var objJson = $.toJSON(vReferenciasCentro);	
	$.ajax({
		type : 'POST',
		url : './referenciasCentro/loadDatosMaestrosFijoDetSIA.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		async :false,
		success : function(data) {	
			p71CodError = data.codError;
			p71DescError = data.descError;

			//Si es una referencia de SIA puede dar un error el procedimiento que sacamos por pantalla
			if(p71CodError!=0 && p71CodError != null){
				createAlert(replaceSpecialCharacters(p71DescError), "ERROR");

				return false;

			}else{	

				if ( data.length == 0 || 
						data.diarioArt == null || data.surtidoTienda == null || 
						data.diarioArt.length ==0 || data.surtidoTienda.length == 0 ){
					createAlert(replaceSpecialCharacters(emptyRecordReference), "ERROR");
					validCargaDatosMaestro = false;
				}else{
					if (getP71FldEsCaprabo() == "true"){
						setP71FldDescripcionRef(data.descArtCaprabo);
					}
					else{
						setP71FldDescripcionRef(data.diarioArt.descripArt);
					}
					setP71FldDescripcionRefEroski(data.diarioArt.descripArt);


					$("#p71_ufp").val(data.surtidoTienda.ufp);
					$("#p71_tipoUfp").val(data.tipoUFP);
					$("#p71_dexenx").val(data.dexenx);
					//$("#p71_dexenxEntero").val(data.dexenxEntero);
					$("#p71_flgOferta").val(data.flgOferta);

					$(".p71Cajas").text(p71Cajas);

					$("#p71_fld_nextDay_BD").val(data.nextDayPedido);
					$("#p71_fld_nextDay").val(formateoNextDay(data.nextDayPedido));
					$(".p71Upper").text($(".p71Upper").text().toString().toUpperCase());

					$("#p71_fld_uc").val(data.surtidoTienda.uniCajaServ).formatNumber({format:"0.##"});

					if (data.tipoUFP == "U") {
						if (data.dexenx > 1) {
							$("#p71_lbl_dexenx").html("De" + data.dexenx + "en" + data.dexenx);
							$("#p71_lbl_dexenx").show();
						}
					} else {
						$("#p71_lbl_dexenx").html("");
						$("#p71_lbl_dexenx").hide();
					}


					//$("#p71_fld_stock").val($.formatNumber(data.stockTienda,{format:"0.00",locale:"es"}));
					$("#p71_fld_cajasPendH").val(data.pendienteTienda);
					$("#p71_fld_cajasPendM").val(data.pendienteTiendaManana);
					$("#p71_txtgrupo1").val(data.grupo1);
					$("#p71_txtgrupo2").val(data.grupo2);
					$("#p71_txtgrupo3").val(data.grupo3);
					$("#p71_txtgrupo4").val(data.grupo4);
					$("#p71_txtgrupo5").val(data.grupo5);
					$("#p71_fld_cajas").attr("disabled", false);
					$("#p71_fld_cajas").focus();
					checkValidAgrupacion();

					//		loadp70StockWS();
					$("#p71_fld_stock").val($.formatNumber(data.stockTienda,{format:"0.00",locale:"es"}));	

					validCargaDatosMaestro = true;
				}

				//Si el estado es de tipo E, ocultamos el botón.
				if(data.estadoPedido == "E"){
					$("#p71_btn_incluir").hide();
					createAlert(replaceSpecialCharacters(referenceEmpuje), "ERROR");
				}else{
					$("#p71_btn_incluir").show();
				}

			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
			validCargaDatosMaestro = false;
		}			
	});		

}

function loadp70StockWS() {
	var vArticulo = new Articulo(new Centro($("#centerId").val(),null,null), getP71FldReferenciaEroski());
	var objJson = $.toJSON(vArticulo.prepareToJsonObjectArticulo());	
	$.ajax({
		type : 'POST',
		url : './welcome/loadStockCentroArticulo.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		cache:false,
		success : function(data){
			if (data!=null && data!="" && data!="Error"){
				$("#p71_fld_stock").val($.formatNumber(data,{format:"0.00",locale:"es"}));	
			}


		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		
}
function loadp70PendientesRecibir(locale){

	var pendientesRecibir = new PendientesRecibir ( 
			$("#centerId").val() , 
			getP71FldReferenciaEroski() ,
			null ,
			null);

	var objJson = $.toJSON(pendientesRecibir.prepareToJsonObject_2());

	$.ajax({
		type : 'POST',			
		url : './referenciasCentro/pendientesRecibir.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {				

			$("#p71_fld_cajasPendH").val(data.cantHoy);
			$("#p71_fld_cajasPendM").val(data.cantFutura);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});		
}

function clear71ScreenPopup() {
	validandoReferencia = false;
	validReferencia = false;
	validCargaDatosMaestro = false;
	validAgrupacion = false;

	setP71FldReferencia("");
	setP71FldDescripcionRef("");
	setP71FldReferenciaEroski("");
	setP71FldDescripcionRefEroski("");
	$("#p71_fld_stock").val("");
	$("#p71_fld_cajasPendH").val("");
	$("#p71_fld_cajasPendM").val("");
	$("#p71_fld_uc").val("");
	$("#p71_fld_nextDay").val("");
	$("#p71_fld_nextDay_BD").val("");
	$("#p71_fld_cajas").val("");
	$("#p71_txtgrupo1").val("");
	$("#p71_txtgrupo2").val("");
	$("#p71_txtgrupo3").val("");
	$("#p71_txtgrupo4").val("");
	$("#p71_txtgrupo5").val("");
	$("#p71_fld_cajas").attr("disabled", "disabled");
	//$("#p71_filtroBloque4").hide();

}
function loadp70Agrupacion(){
	$.ajax({
		type : 'GET',
		url : './detalladoPedido/loadAgrupacion.do?codArticulo='+ getP71FldReferenciaEroski(),
		contentType : "application/json; charset=utf-8",
		cache : false,
		async : false,
		success : function(data) {		
			$("#p71_txtgrupo1").val(data.grupo1);
			$("#p71_txtgrupo2").val(data.grupo2);
			$("#p71_txtgrupo3").val(data.grupo3);
			$("#p71_txtgrupo4").val(data.grupo4);
			$("#p71_txtgrupo5").val(data.grupo5);
			$("#p71_fld_cajas").attr("disabled", false);
			$("#p71_fld_cajas").focus();
			checkValidAgrupacion();
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});	
}

function limpiarMensajesP71(){
	$("#p71_insertado").hide();
	$("#p71_insertado").html("");
	$("#p71_lbl_dexenx").hide();
	$("#p71_lbl_dexenx").hide();
	$("#p71_error").html("");
	$("#p71_error").hide();
}



function formateoNextDay(fecha) {

	var fechaFormateada = '';
	if (fecha != null)
	{
		var diaFecha = parseInt(fecha.substring(0,2),10);
		var mesFecha = parseInt(fecha.substring(2,4),10);
		var anyoFecha = parseInt(fecha.substring(4),10);


		fechaFormateada = $.datepicker.formatDate("D dd-M", new Date(anyoFecha, mesFecha - 1, diaFecha),{
			dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
			dayNames: $.datepicker.regional[ "es" ].dayNames,
			monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
			monthNames: $.datepicker.regional[ "es" ].monthNames
		});
	}

	return(fechaFormateada);

}


function loadP71(locale){

	this.i18nJSON = './misumi/resources/p71DetalladoPedidoNuevo/p71DetalladoPedidoNuevo_' + locale + '.json';

	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {

	})
	.success(function(data) {

		p71Unidades = data.unidades;
		p71Cajas = data.cajas;


	}).error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

function getP71FldReferencia(){
	return $("#p71_fld_referencia").val();
}

function setP71FldReferencia(value){
	return $("#p71_fld_referencia").val(value);
}

function getP71FldDescripcionRef(){
	return $("#p71_fld_descripcionRef").val();
}

function setP71FldDescripcionRef(value){
	return $("#p71_fld_descripcionRef").val(value);
}

function getP71FldReferenciaEroski(){
	return $("#p71_fld_referenciaEroski").val();
}

function setP71FldReferenciaEroski(value){
	return $("#p71_fld_referenciaEroski").val(value);
}

function getP71FldDescripcionRefEroski(){
	return $("#p71_fld_descripcionRefEroski").val();
}

function setP71FldDescripcionRefEroski(value){
	return $("#p71_fld_descripcionRefEroski").val(value);
}

function getP71FldEsCaprabo(){
	return $("#p71_fld_esCaprabo").val();
}

function setP71FldEsCaprabo(value){
	return $("#p71_fld_esCaprabo").val(value);
}

function getP71FldReferenciaCaprabo(){
	if (getP71FldEsCaprabo() == "true"){
		return $("#p71_fld_referencia").val();
	}
	else{
		return "";
	}
}

function getP71FldDescripcionRefCaprabo(){
	if (getP71FldEsCaprabo() == "true"){
		return $("#p71_fld_descripcionRef").val();
	}
	else{
		return "";
	}
}
