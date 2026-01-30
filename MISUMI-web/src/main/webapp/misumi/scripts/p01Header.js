var centerRequired= null;
var referenceRequired=null;
var referenceMinCaracters=null;
var emptyRecordReference = null;
var centerRequiredForWS= null;
var cargadaPantallaCabecera = null;
var cargadaPantallaCuerpo = null;
//Se considera cargado siempre el pie porque no tiene funcionalidad ni campos a mostrar
var cargadaPantallaPie = "S";
var cargadoDetallado = false;
var cargadoDetalladoPedido = false;
var esCentroCaprabo = false;
var esCentroReferenciaVegalsa = false;

var calendarioEnProcesoDeCarga = "N";
var calendarioEnProcesoDeCargaVegalsa = "N";

//Función para indicar y controlar si la estructura de las páginas se ha cargado
//Se da prioridad al dibujado antes de cargar los scripts de funcionalidad de cada subpantalla

//Guardamos el perfil de misumi en una variable global para usar en todas las pantallas de la aplicación.
var userPerfilMisumi; 

function esCentroVegalsa(){
	var centerSoc = $('#centerSoc').val();
	return centerSoc == 13;
}

function obtenerSiEsCentroReferenciaVegalsa(codArticulo){
	$.ajax({
		url: "./welcome/esCentroReferenciaVegalsa.do?codArticulo="+ codArticulo,
		cache : false,
		success: function(data) {
			console.log("esCentroReferenciaVegalsa: "+data);
			esCentroReferenciaVegalsa = data;
		},
		error: function(){
			esCentroReferenciaVegalsa = false;
		}
	});
}

function CargadaEstructuraPantalla(numSubpantalla){
	//El número de la subpantalla viene definido por el orden en el que se dibujan
	//1.Cabecera, 2.Cuerpo, 3.Pie
	if (numSubpantalla == 1){
		cargadaPantallaCabecera = "S";
	}else if (numSubpantalla == 2){
		cargadaPantallaCuerpo = "S";
		//Comprobamos si el modulo seleccionado debe tener habilitada la opcion de multicentro
		comprobarMultiCentro();
	}else{
		cargadaPantallaPie = "S";
	}
	if (cargadaPantallaCabecera != null && cargadaPantallaCuerpo != null){
		$(document).trigger('CargarScriptsPantallas');
	}
}

function EstaCargadaEstructuraPantalla(){
	return (cargadaPantallaCabecera != null && cargadaPantallaCuerpo != null);
}

jQuery(document).ready(function() {
	CargadaEstructuraPantalla(1);
	//$(document).on('CargarScriptsPantallas', function(e) { 
	//$("#cabecera").show();
	//$("#migaDePan").show();
	//$("#contenidoPagina").show();
	//$("#piePagina").show();
	initializeHeader();
	//});
});

function comprobarMultiCentro(){

	var size;

	size = $("#breadCrumb ul li").size();

	if(size != 1){
		disableMultiCentro();
	}

}

/*
55929-P . 
Los centros Caprabo no deben visualizar ni el buscador ni la botonera superior.
Para evitar el efecto de carga y posterior ocultación, se valida el codigo a nivel de jsp 
en vez de a nivel de JS (${!user.centro.esCentroCaprabo})
BICUGUAL

function opcionesCentro(){

	if ($("#centerSoc").val()==125){//Caprabo		
		$("#infoRef").attr("style", "display:none");
		$("#accesoDirectoMenu").attr("style", "display:none");
		$("#menu2").attr("style", "display:block");
		$("#altasCatalogo").attr("style", "display:none");
		$("#referenciasCentro").attr("style", "display:block");
		$("#intertienda").attr("style", "display:none");
		$("#menu").attr("style", "display:none")	
	} else {
		$("#infoRef").attr("style", "display:block");
		$("#accesoDirectoMenu").attr("style", "display:block");
		$("#menu2").attr("style", "display:block");
		$("#altasCatalogo").attr("style", "display:block");
		$("#referenciasCentro").attr("style", "display:block");
		$("#intertienda").attr("style", "display:block");
		$("#menu").attr("style", "display:block");
	}
}
 */

function initializeHeader(){
	//Guardamos el perfil de misumi.
	userPerfilMisumi = $("#userPerfil").val();
	
	//Para mostrar un gif animado mientras se carga la pagina ...
	var contadorAjax = 0;

	$('body').append('<div id="cargando"><div id="marcoCargando"><label>Cargando...</label><br/><img src="./misumi/images/ajax-loader.gif" /></div></div><div id="cargandoIE"></div>');
	$("#cargando").bind("ajaxSend", function(event, jqxhr, settings) {

		if(settings.url.indexOf("getAvisosCentro.do") <= 0){  
			contadorAjax = contadorAjax + 1;
			$(this).show();
		}
	}).bind("ajaxComplete", function(event, jqxhr, settings) {

		if(settings.url.indexOf("getAvisosCentro.do") <= 0){
			contadorAjax = contadorAjax - 1;
			if (contadorAjax == 0) {  	  			
				$(this).hide();
			}
		}

	}).bind("ajaxError", function(event, jqxhr, settings) {

		$(this).hide();
	});
	// ... fin gif animado


	//Para cargar e inicializar el menú 
	$("ul.sf-menu").superfish();  

	//load_cmbCentros();     
	loadGeneralMessages("es");
	centerAutoComplete();    
	getCentroUsuarioSession();
	events_p01_btn_manual();
	events_p01_txt_infoRef();
	events_p01_btn_infoRef();
	events_p01_btn_sfmCapacidad();
	events_p01_btn_misPedidos();
	events_p01_btn_pedidoAdicional();
	events_p01_btn_sfm();
	events_p01_btn_detallado();
	events_p01_btn_intertienda();
	events_p01_btn_tablonAnuncios();
	events_p01_btn_informeHuecos();
	events_p01_btn_informesListado();
	events_enlaceVentaAnticipada();
	events_enlaceAlarmasPLU();
	events_p01_btn_googleMaps();
	/********* Petición *********/ 
	events_enlaceDevoluciones();
	/*****************************/
	//MISUMI-69
	events_enlaceCalendario();
	
	/**** Peticion MISUMI-301 **********
	 * Calendario Vegalsa
	 * @author BICUGUAL
	 */
	events_enlaceCalendarioVegalsa();
	
	/********* Petición MISUMI-203*********/ 
	events_enlaceEntradas();
	

	/** MISUMI-383. Enlace provisional para la ENTREGA-1. **/
	events_enlaceDetalladoPedido();
	
	
	/********* Petición MISUMI-266*********/ 
	events_parametrizacionCestas();
	
	/********* Petición MISUMI-621*********/ 
	events_avisosSiec();
	
	//evento que enlaza a los lotes de navidad activos en cada centro
	events_p01_btn_lote_navidad();

	//MultiCentros - Evento para el label Centro.
	events_p01_label_Centro();

	$(document).on('CargadoCentro', function(e) {
		//En caso de estar en la pantalla de welcome, la búsqueda de avisos no se llamará desde la cabecera sino desde la p11Welcome.js 
		if (!$('#welcome').length) {
			getAvisosCentro();
		}

	});

	//Si se pulsa intro, volvemos a lanzar el buscar.
	$(".controlReturn").keydown(function(e) {
		if(e.which == 13) {
			e.preventDefault();
			finder();
		}
	});

	$("#enlaceExclusionVentas").click(function () {
		if ($("#centerId").val()==null || $("#centerId").val()==""){
			createAlert(replaceSpecialCharacters(centerRequiredForWS), "ERROR");

		}else{
			if($("#centerMultiCentro").attr("value")==''){
				window.location = './exclusionVentas.do?center='+$("#centerId").val(); 
			}

		}

	});
}

$(window).load(function(){ 
	/*  $('#loading').fadeOut(6000, function(){
	        $("#wrap").fadeIn(1000);
	        $("#footer").fadeIn(1000);
	    });*/
});

function events_p01_txt_infoRef(){
	//Si se pulsa intro, volvemos a lanzar el buscar.
	$("#p01_txt_infoRef").keydown(function(e) {
		if(e.which == 13) {
			//Se ha pulsado intro
			e.preventDefault();
			findReferencia();
		}
	});
}

function events_p01_btn_infoRef(){
	$("#p01_btn_infoRef").click(function () {		
		findReferencia();
	});
}

/**
 * P-52401
 * MULTICENTROS 
 * Muestra el pop Up de multicentro
 * @author BIBAESGA		
 */	
function   events_p01_label_Centro(){

	//obtencion de userId para activar el multicentro
	var userId = $("#userId").attr("value").toLowerCase();

	if(userId=="i0306" || userId=="i1251" || userId=="s305060"){

		$("#p01_lbl_centerHeader").css({cursor: "default",  color:"blue",  'text-decoration': "underline" });
		$("#p01_lbl_centerHeader").mouseover(function() {
			$("#p01_lbl_centerHeader").css({cursor:"pointer", cursor: "hand", color:"black", 'text-decoration': "underline"});
		});

		$("#p01_lbl_centerHeader").mouseout(function() {
			$("#p01_lbl_centerHeader").css({cursor: "default",  color:"blue",  'text-decoration': "underline" });
		});

		$("#p01_lbl_centerHeader").click(function(event) {
			//alert(event.target.id);

			$( "#p90_multicentrosPopupSeleccion" ).dialog("open");
			//$( "#p32_popupVentaAnticipada" ).dialog("open");
			p90_getCentros();

		});
	}

}


/**
 * P-49364
 * Busqueda de referencia pasando por validador. 
 * Este metodo se utiliza tanto para la busqueda desde el boton como para la busqueda a traves del return
 * @author BICUGUAL		
 */		
function findReferencia(){			
	$("#infoRef").hide();
	var txtBusqueda=$("#p01_txt_infoRef").val();
	var messageVal=findReferenciaValidation(txtBusqueda);
	var alfaNumerico=null;
	var ean=null;

	if (messageVal==null)
	{		
		//Si se trata de una busqueda alfanumerica o alfabetica
		if (!isInt(txtBusqueda)){
			//se buscará como MODELO PROVEEDOR
			//En el caso que no eista MODELO PROVEEDOR si no hay registros como DENOMINACION
			alfaNumerico= true;
			ean = false;
			reloadDataP34(txtBusqueda, "informacionBasica","", alfaNumerico, ean);
		}

		else
		{
			//Si la REFERENCIA mayor de 8 digitos se busca como EAN, 
			//Si no hay registros como EAN se buscará como MODELO PROVEEDOR
			//En el caso que no eista MODELO PROVEEDOR si no hay registros como DENOMINACION
			alfaNumerico = false;
			if(txtBusqueda.length > 8)
			{
				ean= true;
				reloadDataP34(txtBusqueda, "informacionBasica","",false,ean);
			}
			else
			{
				ean = false;
				reloadDataP34(txtBusqueda, "informacionBasica","",false,ean);
			}
		}
	}
	else
	{		
		$("#p03_btn_aceptar").bind("click", function(e) {
			$("#p01_txt_infoRef").focus();
			$("#p01_txt_infoRef").select();
			e.stopPropagation();
			$("#infoRef").show();
		}); 
		createAlert(replaceSpecialCharacters(messageVal), "ERROR");	
	}

}


/**
 * P-49364
 * Validacion para la busqueda de referencias. 
 * Centro cargado, input de busqueda cargado. Que el input de busqueda tenga mas de 4 caracteres si se ha introducido una secuencia alfanumerica.
 * MISUMI-376
 * Eliminar espacios en blanco por detras y por delante del texto de busqueda para validar que tenga mas de 4 caracteres.
 * 
 * @author BICUGUAL
 * @param txtBusqueda
 * @returns mensaje de error
 */	
function findReferenciaValidation(txtBusqueda){		
	var messageVal=null;		

	if ($("#centerId").val()==null || $("#centerId").val()==""){		
		messageVal=centerRequired;		
	}			
	else if (txtBusqueda==null || txtBusqueda==""){		
		messageVal=referenceRequired;   		
	}		
	else if (!isInt(txtBusqueda) && txtBusqueda.trim().length<4){		
		messageVal=referenceMinCaracters;		
	}		

	return messageVal;		
}		


function events_enlaceVentaAnticipada(){
	$("#enlaceVentaAnticipada").click(function () {

		if ($("#centerId").val()!=null && $("#centerId").val()!=""){
			if($("#centerMultiCentro").attr("value")==''){
				cleanP32();
				$( "#p32_popupVentaAnticipada" ).dialog("open");
			}
		}else{
			createAlert(replaceSpecialCharacters(centerRequired), "ERROR");

		}


	});
}

function events_enlaceAlarmasPLU(){
	$("#enlaceAlarmasPLU").click(function () {

		if ($("#centerId").val()!=null && $("#centerId").val()!=""){
			if($("#centerMultiCentro").attr("value")==''){
				window.location = './alarmasPLU.do?center='+$("#centerId").val(); 
			}
		}else{
			createAlert(replaceSpecialCharacters(centerRequired), "ERROR");

		}


	});
}

function events_enlaceDevoluciones(){
	$("#enlaceDevoluciones").click(function () {
		if ($("#centerId").val()!=null && $("#centerId").val()!=""){
			if($("#centerMultiCentro").attr("value")==''){
				window.location = './devoluciones.do?center='+$("#centerId").val(); 
			}
		}else{
			createAlert(replaceSpecialCharacters(centerRequired), "ERROR");

		}				
	});
}

function events_enlaceCalendario(){
	$("#enlaceCalendario").click(function () {
		if ($("#centerId").val()!=null && $("#centerId").val()!=""){
			if($("#centerMultiCentro").attr("value")==''){
				//Para evitar que el usuario pulse el botón del calendario dos veces seguidas mientras se carga la temporal y que devuelva errores.
				//Si es N es que se puede pulsar el botón.
				if(calendarioEnProcesoDeCarga == 'N'){
					window.location = './calendario.do?center='+$("#centerId").val();
				}
			}
		}else{
			createAlert(replaceSpecialCharacters(centerRequired), "ERROR");

		}				
	});
}

function events_enlaceDetalladoPedido(){
	$("#p70_selOpcionPedidos").click(function () {

		if($("#centerMultiCentro").attr("value")==''){

			if ($("#centerId").val()==null || $("#centerId").val()==""){
				createAlert(replaceSpecialCharacters(centerRequiredForWS), "ERROR");
			}else{
				if (!cargadoDetalladoPedido){
					cargadoDetalladoPedido=true;
					window.location = './detalladoPedido.do?center='+$("#centerId").val();
				}
			}
		}

	});
	
}

function events_enlaceCalendarioVegalsa(){
	$("#enlaceCalendarioVegalsa").click(function () {
		if ($("#centerId").val()!=null && $("#centerId").val()!=""){
			if($("#centerMultiCentro").attr("value")==''){
				//Para evitar que el usuario pulse el botón del calendario dos veces seguidas mientras se carga la temporal y que devuelva errores.
				//Si es N es que se puede pulsar el botón.
				if(calendarioEnProcesoDeCargaVegalsa == 'N'){
					window.location = './calendarioVegalsa.do?center='+$("#centerId").val();
				}
			}
		}else{
			createAlert(replaceSpecialCharacters(centerRequired), "ERROR");

		}				
	});
}

function events_enlaceEntradas(){
	$("#enlaceEntradas").click(function () {
		if ($("#centerId").val()!=null && $("#centerId").val()!=""){
			if($("#centerMultiCentro").attr("value")==''){
				window.location = './entradasDescentralizadas.do?center='+$("#centerId").val(); 
			}
		}else{
			createAlert(replaceSpecialCharacters(centerRequired), "ERROR");
		}				
	});
}

function events_parametrizacionCestas(){
	$("#enlaceParametrizacionCestas").click(function () {
		window.location = './parametrizacionCestas.do?center='+$("#centerId").val(); 			
	});
}

function events_avisosSiec(){
	$("#enlaceAvisosSiec").click(function () {
		window.location = './gestionAvisosSiec.do?center='+$("#centerId").val(); 			
	});
}

function events_p01_btn_manual(){
	$("#p01_btn_manual").click(function () {

		var formManual = "<form name='manualform' action='welcome/downloadManual.do'  accept-charset='ISO-8859-1' method='get'>";
		formManual = formManual + "</form><script>document.manualform.submit();</script>";
		Show_PopupManual(formManual);
	});
}

function events_p01_btn_sfmCapacidad(){


	$("#p01_btn_sfmCapacidad").click(function () {
		if($("#centerMultiCentro").attr("value")==''){
			window.location = './sfmCapacidad.do'
		}
	});

}

function events_p01_btn_misPedidos(){

	$("#p01_btn_misPedidos").click(function () {
		if($("#centerMultiCentro").attr("value")==''){
			window.location = './selPedidosCampanas.do'
		}
	});

}

function events_p01_btn_pedidoAdicional(){

	$("#p01_btn_pedidoAdicional").click(function () {
		if($("#centerMultiCentro").attr("value")==''){
			window.location = './pedidoAdicional.do'
		}
	});


}

function events_p01_btn_sfm(){

	$("#p01_btn_sfm").click(function () {
		if($("#centerMultiCentro").attr("value")==''){
			window.location = './sfmCapacidad.do'
		}
	});	

}

function events_p01_btn_detallado(){
	$("#botonDetallePedido").click(function () {

		if($("#centerMultiCentro").attr("value")==''){

			if ($("#centerId").val()==null || $("#centerId").val()==""){
				createAlert(replaceSpecialCharacters(centerRequiredForWS), "ERROR");

			}else{
				if (!cargadoDetallado){
					cargadoDetallado = true;
					if ($("#isMostrador").val()=="true"){
						window.location = './selPedidosMostrador.do';
					} else {
						window.location = './detalladoPedido.do?center='+$("#centerId").val();
					}
				}
			}
		}
	});
}

function events_p01_btn_intertienda(){
	//Si se pulsa intro, volvemos a lanzar el buscar.
	$("#intertienda").click(function() {
		if ($("#centerId").val()!=null && $("#centerId").val()!=""){
			window.location.href = './intertienda.do';
		}else{
			createAlert(replaceSpecialCharacters(centerRequired), "ERROR");
		}
	});
}

function events_p01_btn_tablonAnuncios(){
	//Si se pulsa intro, volvemos a lanzar el buscar.
	$("#p01_btn_tablonAnuncios").click(function() {
		if ($("#centerId").val()!=null && $("#centerId").val()!=""){
			p19loadTablonAnuncios();
		}else{
			createAlert(replaceSpecialCharacters(centerRequired), "ERROR");
		}
	});
}

function events_p01_btn_informeHuecos(){
	//Si se pulsa intro, volvemos a lanzar el buscar.
	$("#p01_btn_informeHuecos").click(function() {
		if ($("#centerId").val()!=null && $("#centerId").val()!=""){
			p33loadInformeHuecos();
		}else{
			createAlert(replaceSpecialCharacters(centerRequired), "ERROR");
		}
	});
}

function events_p01_btn_informesListado(){
	//Si se pulsa intro, volvemos a lanzar el buscar.
	$("#p01_btn_informesListado").click(function() {
		if ($("#centerId").val()!=null && $("#centerId").val()!=""){
			p35loadInformes();
		}else{
			createAlert(replaceSpecialCharacters(centerRequired), "ERROR");
		}
	});
}

/**
 * Funcion que abre dialog con las cestas de navidad del centro
 */
function events_p01_btn_lote_navidad(){
	$("#p01_btn_lote_navidad").on('click',function() {


		if ($("#centerId").val()!=null && $("#centerId").val()!=""){
			//Abro el popup
			$("#p80_popupLotesNavidad").dialog("open");
			//Cargo el popUp con las imagenes
			loadP80Popup();
		}else{
			createAlert(replaceSpecialCharacters(centerRequired), "ERROR");

		}
	});

}

function events_p01_btn_googleMaps(){
	$("#p01_div_googleMaps").on('click',function(){
		if ($("#centerId").val()!=null && $("#centerId").val()!=""){
			loadP91PopupGoogle();
		}else{
			createAlert(replaceSpecialCharacters(centerRequired), "ERROR");
		}
	});
}

function concatCodDesCentro(cod, des){
	return cod + "-" + des;
}

function codDesCentro(codDes){
	return codDes.split("-");
}


function setCentroUsuarioSession(centroNuevo, multiCentro){
	var mCentro = new Array();

	if(!multiCentro){

		mCentro.push(centroNuevo.item.id);


		var objJson = $.toJSON(mCentro);
	}else{

		for(var i = 0; i<centroNuevo.length; i++){

			mCentro.push(centroNuevo[i].item.id);

		}


	}
	var objJson = $.toJSON(mCentro);

	$.ajax({
		type : 'POST',
		url : './welcome/setUserCentro.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {				
			if (data.centro!=null){
//				$("#centerId").val(data.centro.codCentro);
//				$("#centerName").val(concatCodDesCentro(data.centro.codCentro, data.centro.descripCentro));
//				$("#centerNegocio").val(data.centro.codNegocio);
//				$("#centerRegion").val(data.centro.codRegion);
//				$("#centerZona").val(data.centro.codZona);
//				$("#centerEnsena").val(data.centro.codEnsena);
//				$("#centerArea").val(data.centro.codArea);
				if (data.centro.flgCapacidad != null){
					//Control de autoservicio para sfm y capacidad
					$("#p01_txt_flgCapacidad").val(data.centro.flgCapacidad);
				}else{
					$("#p01_txt_flgCapacidad").val("N");
				}	
				if (data.centro.flgFacing != null){
					//Control de facing
					$("#p01_txt_flgFacing").val(data.centro.flgFacing);
				}else{
					$("#p01_txt_flgFacing").val("N");
				}	

				if(multiCentro){
					var centroConcat='';
					for(var i = 0; i<data.listaCentros.length; i++){
						centroConcat = centroConcat+" "+data.listaCentros[i].codCentro+ " /";
					}

					$("#centerName").val(centroConcat);
					$("#centerName").attr( "title", "Centros: "+centroConcat );
					//$("#centerMultiCentro").attr("value","S");


					disableBtn();

				}
				else{

					$("#centerName").val(concatCodDesCentro(data.centro.codCentro, data.centro.descripCentro));
					$("#centerName").attr( "title", "Centros: "+ concatCodDesCentro(data.centro.codCentro, data.centro.descripCentro) );
					// $("#centerMultiCentro").attr("value","");

					//set_listaFinal($("#centerName").val());

					enableBtn();
				}

				//se borran los metadatos de centro anteriores
				deleteCenterMetaData()
				// se obtienen los metadatos de los centros
				addCenterMetaData(data);


				//elimino la lista de centros
				clearListaFinal()

				//actualizamos la lista de centros
				for( var i=0; i<$("#multicentroSize").val();i++){
					set_listaFinal($("#centerDescription_"+i+"").val()); 
				}


				//Caprabo
				if (data.centro.codZona == 125){
					window.location.href = "./welcome.do";  	
				} else {
					location.reload();
				}


			}else{
				$("#p01_txt_flgCapacidad").val("N");
				$("#p01_txt_flgFacing").val("N");
			}


			//location.reload();

		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});	

	if(!multiCentro){
		$("#p01_txt_infoRef").focus();
	}
//	else{
//	$("#p01_lbl_centerHeader").focus();
//	}
}
function loadGeneralMessages(locale){

	var jqxhr = $.getJSON('./misumi/resources/misumi_' + locale + '.json',
			function(data) {

	})
	.success(function(data) {
		centerRequired= data.centerRequired;
		referenceRequired=data.referenceRequired;
		referenceMinCaracters=data.referenceMinCaracters;
		emptyRecordReference=data.emptyRecordReference;
		centerRequiredForWS= data.centerRequiredForWS;

	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}
function resetCentroUsuarioSession(){

	$.ajax({
		type : 'POST',
		url : './welcome/resetUserCentro.do',
		contentType : "application/json; charset=utf-8",
		dataType : "json",

		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});	

}

function getCentroUsuarioSession(){

	$.ajax({
		type : 'POST',
		url : './welcome/getUserSession.do',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {				

			//el combo de centro se muestra con valor nulo para que el usuario seleccione un centro
			//control de limpiado del valor del centro cuando se pincha sobre el campo
			Clear_center();
			if (data!=null && data.centro != null){
				$("#centerId").val(data.centro.codCentro);
				//Gestion de las opciones de Centro dependiendo del codZona
				$("#centerZona").val(data.centro.codZona);
				$("#centerSoc").val(data.centro.codSoc);
				esCentroCaprabo = data.centro.esCentroCaprabo;
				esCentroCapraboEspecial = data.centro.esCentroCapraboEspecial;
				esCentroCapraboNuevo = data.centro.esCentroCapraboNuevo;
			//	if (esCentroCaprabo || esCentroCapraboEspecial || esCentroCapraboNuevo){
			//		$("#p01_txt_infoRef").filter_input({regex:'[0-9]'});
			//	}

				//opcionesCentro(); 	
				if(data.multicentro){
					var centroConcat='';
					for(var i = 0; i<data.listaCentros.length; i++){
						centroConcat = centroConcat+" "+data.listaCentros[i].codCentro+ " /";
					}

					$("#centerName").val(centroConcat);
					$("#centerName").attr( "title", "Centros: "+centroConcat );
					//$("#centerMultiCentro").attr("value","S");

					disableBtn();

				}
				else{

					$("#centerName").val(concatCodDesCentro(data.centro.codCentro, data.centro.descripCentro));
					//$("#centerMultiCentro").attr("value","");
					enableBtn();
				}

				//se borran los metadatos de centro anteriores
				deleteCenterMetaData()
				addCenterMetaData(data);

				//elimino la lista de centros
				clearListaFinal()

				//actualizamos la lista de centros
				for( var i=0; i<$("#multicentroSize").val();i++){
					set_listaFinal($("#centerDescription_"+i+"").val()); 
				}

//				//$( "#centerName" ).val(concatCodDesCentro(data.centro.codCentro, data.centro.descripCentro));
//				$("#centerNegocio").val(data.centro.codNegocio);
//				$("#centerRegion").val(data.centro.codRegion);
//				$("#centerZona").val(data.centro.codZona);
//				$("#centerEnsena").val(data.centro.codEnsena);
//				$("#centerArea").val(data.centro.codArea);
				if (data.centro.flgCapacidad != null){
					//Control de autoservicio para sfm y capacidad
					$("#p01_txt_flgCapacidad").val(data.centro.flgCapacidad);
				}else{
					$("#p01_txt_flgCapacidad").val("N");
				}	
				if (data.centro.flgFacing != null){
					//Control de facing
					$("#p01_txt_flgFacing").val(data.centro.flgFacing);
				}else{
					$("#p01_txt_flgFacing").val("N");
				}	
			}else{
				$("#p01_txt_flgCapacidad").val("N");
				$("#p01_txt_flgFacing").val("N");
			}

			//Gestion de boton de cestas de navidad
			/** P50055  
			 * Modificacion para que aparezca el texto de cestas en la miga 
			 * @author BICUGUAL  */
			if (data.centro!= null && data.centro.lotesNavidadActivos==true){
				$("#p01_btn_lote_navidad").show();
			}
			else{
				$("#p01_btn_lote_navidad").hide();
			}

			// Si el centro tiene informes mostramos el ícono
			if (data.centro!= null && data.centro.informeListado != null && data.centro.informeListado.existe){
				if ($('#p01_btn_lote_navidad').is(':visible')){
					$("#InformeHuecos").removeClass("SinLote");
					$("#InformeHuecos").addClass("ConLote");
				} else {
					$("#InformeHuecos").removeClass("ConLote");
					$("#InformeHuecos").addClass("SinLote");
				}
				$("#InformeHuecos").show();
			}else{
				$("#InformeHuecos").hide();
			}
//			if(!data.multicentro){
//			$("#p01_txt_infoRef").focus();	
//			}
//			else{
//			$("#p01_lbl_centerHeader").focus();
//			}

			$(document).trigger('CargadoCentro');

			//Ponemos el foco un elemento en funcion del tipo de usuario.
			if(!data.multicentro){
				setFocus();
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});	

}

function getAvisosCentro(){

	$.ajax({
		type : 'GET',
		url : './welcome/getAvisosCentro.do',
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {				

//			console.log("getAvisosCentro - SUCCESS");

			if (data!=null && data.centro != null){
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
				}else{
					$("#TablonAnuncios").attr("style", "display:none;");
				}
			}else{
				$("#TablonAnuncios").attr("style", "display:none;");
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});	

}


function CargarScriptsPantallas(){

}

function centerAutoComplete(){
	var cache = {};
	$( "#centerName" ).autocomplete({
		minLength: 1,
		mustMatch:true,
		source: function( request, response ) {
			var term = request.term;

			$.ajax({
				url: "./welcome/loadCentrosPattern.do?term="+ request.term,
				dataType: "json",
				cache : false,

				success: function(data) {
					cache[ term ] = data;
					response($.map(data, function(item) {
						return {
							label: item.codCentro  +"-" +item.descripCentro  ,
							id: item.codCentro ,
							abbrev: item.codCentro
						};
					}));

				}
			});
		},

		select: function(event, ui) {
			oneEventFired = true;

			$("#centerId").val(ui.item.id);
			setCentroUsuarioSession(ui);

			this.close;
		}
	})
	;


}

function Show_PopupManual(form) {
	$('#manualPopup').html(form);
	$('#manualPopup').fadeIn('fast');
	$('#manualWindow').fadeIn('fast');
	Close_Popup();
}

function Close_PopupManual() {
	$('#manualPopup').fadeOut('fast');
	$('#manualWindow').fadeOut('fast');
}

function Show_Popup(form) {
	$('#excellPopup').html(form);
	$('#excellPopup').fadeIn('fast');
	$('#excellWindow').fadeIn('fast');
	Close_Popup();
}

function Close_Popup() {
	$('#excellPopup').fadeOut('fast');
	$('#excellWindow').fadeOut('fast');
}

function Clear_center(){
	$( "#centerName" ).bind('focus', function() {
		$("#centerId").val('');
		$("#centerName").val('');
		$("#TablonAnuncios").attr("style", "display:none;");
		$("#p01_btn_lote_navidad").attr("style", "display:none;");
		$("#InformesListado").attr("style", "display:none;");
		$("#InformeHuecos").attr("style", "display:none;");

		$("#p01_div_googleMaps").attr("style", "display:none;");


		clearListaFinal();

		$("#centerId").val('');
		$("#centerNegocio").val('');
		$("#centerRegion").val('');
		$("#centerZona").val('');
		$("#centerSoc").val('');
		$("#centerEnsena").val('');
		$("#centerArea").val('');
		resetCentroUsuarioSession();
	});
}

function disableBtn(){

	$("#p01_btn_misPedidos").css("background-color", "rgb(248, 248, 248)");
	$("#p01_btn_sfmCapacidad").css("background-color", "rgb(248, 248, 248)");
	$("#botonDetallePedido").css("background-color", "rgb(248, 248, 248)");
	$("#p01_btn_pedidoAdicional").css("background-color", "rgb(248, 248, 248)");
	//$("#p01_btn_pedidoAdicional").removeClass("accesoDirecto").addClass("enlaceMulticentro");

	$("#p01_txt_infoRef").prop( "disabled", true );
	//$("#menu ul li ul li a").bind('click', function(e){e.preventDefault(); alert("click menu");});
	$("#menu2 ul li ul li a").bind('click', function(e){e.preventDefault();});

}

function enableBtn(){

	$("#p01_btn_misPedidos").css("background-color", "#FFFFFF");
	$("#p01_btn_sfmCapacidad").css("background-color", "#FFFFFF");
	$("#botonDetallePedido").css("background-color", "#FFFFFF");
	$("#p01_btn_pedidoAdicional").css("background-color", "#FFFFFF");
	//$("#p01_btn_pedidoAdicional").removeClass("enlaceMulticentro").addClass("accesoDirecto");
	$("#p01_txt_infoRef").prop( "disabled", false );
	$("#menu2 ul li ul li a").unbind( "click");
	$("#p01_txt_infoRef").focus();

}

//Funcion que desabilita el multicentro para modulo determinado
function disableMultiCentro(){

	$("#p01_lbl_centerHeader").css({cursor: "default",  color:"#807878",  'text-decoration': "none" });
	$("#p01_lbl_centerHeader").unbind('mouseover');
	$("#p01_lbl_centerHeader").unbind('mouseout');
	$("#p01_lbl_centerHeader").unbind('click');


}

function deleteCenterMetaData(){

	$("[id*=centerId]").remove();
	$("[id*=centerNegocio]").remove();
	$("[id*=centerTipoNegocio]").remove();
	$("[id*=centerRegion]").remove();
	$("[id*=centerZona]").remove();
	$("[id*=centerSoc]").remove();
	$("[id*=centerEnsena]").remove();
	$("[id*=centerArea]").remove();
	$("[id*=centerDescription_]").remove();

}

function addCenterMetaData(data){

	if(data.multicentro){

		for(var i = 0 ; i < data.listaCentros.length; i++){
			$("#DatosUsuario").append('<input id="centerId_'+i+'" type="hidden" value="'+data.listaCentros[i].codCentro+'"/>');
			$("#DatosUsuario").append('<input id="centerNegocio_'+i+'" type="hidden" value="'+data.listaCentros[i].codNegocio+'"/>');
			$("#DatosUsuario").append('<input id="centerTipoNegocio_'+i+'" type="hidden" value="'+data.listaCentros[i].negocio+'"/>');
			$("#DatosUsuario").append('<input id="centerRegion_'+i+'"  type="hidden" value="'+data.listaCentros[i].codRegion+'"/>');
			$("#DatosUsuario").append('<input id="centerZona_'+i+'"  type="hidden" value="'+data.listaCentros[i].codZona+'"/>');
			$("#DatosUsuario").append('<input id="centerSoc_'+i+'"  type="hidden" value="'+data.listaCentros[i].codSoc+'"/>');
			$("#DatosUsuario").append('<input id="centerEnsena_'+i+'"  type="hidden" value="'+data.listaCentros[i].codEnsena+'"/>');
			$("#DatosUsuario").append('<input id="centerArea_'+i+'"  type="hidden" value="'+data.listaCentros[i].codArea+'"/>');
			$("#DatosUsuario").append('<input id="centerDescription_'+i+'"  type="hidden" value="'+concatCodDesCentro(data.listaCentros[i].codCentro, data.listaCentros[i].descripCentro)+'"/>');


		}

		$("#DatosUsuario").append('<input id="multicentroSize"  type="hidden" value="'+data.listaCentros.length+'"/>');
		$("#centerMultiCentro").attr("value","S");
	}else{

		$("#DatosUsuario").append('<input id="centerId" type="hidden" value="'+data.centro.codCentro+'"/>');
		$("#DatosUsuario").append('<input id="centerNegocio" type="hidden" value="'+data.centro.codNegocio+'"/>');
		$("#DatosUsuario").append('<input id="centerTipoNegocio" type="hidden" value="'+data.centro.negocio+'"/>');
		$("#DatosUsuario").append('<input id="centerRegion"  type="hidden" value="'+data.centro.codRegion+'"/>');
		$("#DatosUsuario").append('<input id="centerZona"  type="hidden" value="'+data.centro.codZona+'"/>');
		$("#DatosUsuario").append('<input id="centerSoc"  type="hidden" value="'+data.centro.codSoc+'"/>');
		$("#DatosUsuario").append('<input id="centerEnsena"  type="hidden" value="'+data.centro.codEnsena+'"/>');
		$("#DatosUsuario").append('<input id="centerArea"  type="hidden" value="'+data.centro.codArea+'"/>');
		$("#centerMultiCentro").attr("value","");
	}


}

function setFocus(){

	if($("#userPerfil").val()=="1"){
		// Si el usuario tiene perfil técnico
		if ($("#centerName").val() == ''){
			// Foco en el campo "Centro" (centerName)
			$("#centerName").focus();
		}
		else if ($("#p13_fld_referencia").length){
			// Estamos en el menú "Consulta código referencia" y 
			// entonces ponemos el foco en el campo "p13_fld_referencia"
			$("#p13_fld_referencia").focus();
		}
		else if ($("#p72_fld_referencia").length){
			// Estamos en el menú "Intertienda" y 
			// entonces ponemos el foco en el campo "p72_fld_referencia"
			$("#p72_fld_referencia").focus();
		}
		else{
			$("#p01_txt_infoRef").focus();
		}
	}else{
		// Si el usuario tiene perfil centro o consulta
		if ($("#p13_fld_referencia").length){
			// Estamos en el menú "Consulta código referencia" y 
			// entonces ponemos el foco en el campo "p13_fld_referencia"
			$("#p13_fld_referencia").focus();
		}
		else if ($("#p72_fld_referencia").length){
			// Estamos en el menú "Intertienda" y 
			// entonces ponemos el foco en el campo "p72_fld_referencia"
			$("#p72_fld_referencia").focus();
		}
		else{
			$("#p01_txt_infoRef").focus();
		}
	}
}
