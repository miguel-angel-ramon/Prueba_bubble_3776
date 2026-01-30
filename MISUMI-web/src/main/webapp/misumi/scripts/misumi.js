var locale = "es";
var CONS_TECNIC_ROLE = 1;
var CONS_CENTER_ROLE =2;
var CONS_CONSULTA_ROLE =3;
var CONS_ADMIN_ROLE =4;


window.onload = function() {
	if(getCookie("sessionCookie") == "null"){		
		var webName = location.pathname.split('/');
		var redirectURL = window.location.protocol + "//" + window.location.host + "/" + webName[1] + "/login.do";
		window.location= redirectURL;
	}	
}

jQuery(document).ready(function() {
	$.ajaxSetup({ cache: false ,scriptCharset: "utf-8"}); 
	loadLogOut();
	scrollWindowControl();
});

/* Calculates scrollbar width in pixels */
function scrollbar_width() {
    if( jQuery('body').height() > jQuery(window).height()) {
        var calculation_content = jQuery('<div style="width:50px;height:50px;overflow:hidden;position:absolute;top:-200px;left:-200px;"><div style="height:50px;"></div>');
        jQuery('body').append( calculation_content );
       
        var width_one = jQuery('div', calculation_content).innerWidth();
        calculation_content.css('overflow-y', 'scroll');
        var width_two = jQuery('div', calculation_content).innerWidth();
        jQuery(calculation_content).remove();
        return ( width_one - width_two );
    }
    return 0;
}

function scrollWindowControl(){

	//Control de scroll para IE. El resto de navegadores no necesita este control
	if ($.browser.msie){
		setOverflowScrollbar();
   	 	$(window).bind('resize', function() {
   		 	setOverflowScrollbar();
 	    });
	}
}

function setOverflowScrollbar(){
	var windowWidth = parseInt($(window).width());
	var documentWidth = parseInt($(document).width());
    var windowHeight = parseInt($(window).height());
    var documentHeight = parseInt($(document).height());
    var windowWidthScroll = windowWidth;
    //Si existe scroll vertical hay que quitar al ancho de pantalla el tamaño del scroll
    if (windowHeight < documentHeight){
    	windowWidthScroll = windowWidthScroll - scrollbar_width();
    }
    
    if (windowWidthScroll < documentWidth){
    	if (windowWidth == documentWidth){
    		$('html').css('overflow-x', 'hidden');
    	}else{
    		$('html').css('overflow-x', 'auto');
    	}
    } else {
        $('html').css('overflow-x', 'hidden');
    }
}

function loadLogOut(){
	$("#logout").click(function() {
		window.location = "./logout.do";
	});
	
}
function readCookie(){ 
	var temp = $.cookie('language');
	if (temp == null){
		locale = "en";
		cookieExists = false;
	}else if (temp != "es" && temp != "en"){
		  	  locale = "es";
		  	  cookieExists = true;
		  }else{
			  locale = temp;
			  cookieExists = true;
		  }
}


function getCookie(CookieName) {
var CookieVal = null;
if (document.cookie) // only if exists{
	var arr = document.cookie.split((escape(CookieName) + '='));
	if (arr.length >= 2){
		var arr2 = arr[1].split(';');
		CookieVal = unescape(arr2[0]); // unescape() : Decodes the String
	}
	return CookieVal;
}
function replaceSpecialCharacters(feedBack){
	var scapedMessage = null;
	scapedMessage = feedBack.replace(/\\u00E1/g, "á");
	scapedMessage = scapedMessage.replace(/\\u00E9/g, "é");
	scapedMessage = scapedMessage.replace(/\\u00ED/g, "í");	
	scapedMessage = scapedMessage.replace(/\\u00F3/g, "ó");
	scapedMessage = scapedMessage.replace(/\\u00fa/g, "ú");
	scapedMessage = scapedMessage.replace(/\\u00C1/g, "Á");
	scapedMessage = scapedMessage.replace(/\\u00C9/g, "É");
	scapedMessage = scapedMessage.replace(/\\u00CD/g, "Í");
	scapedMessage = scapedMessage.replace(/\\u00D3/g, "Ó");
	scapedMessage = scapedMessage.replace(/\\u00DA/g, "Ú");
	scapedMessage = scapedMessage.replace(/\\u00F1/g, "ñ");
	scapedMessage = scapedMessage.replace(/\\u00D1/g, "Ñ");
	scapedMessage = scapedMessage.replace(/\\u00D2/g, "º");
	
	return scapedMessage;
	
}


function handleError(xhr, status, error, locale){
	if(status == "error"){
		if(xhr.responseText != ""){
			var mesg = "";
			
			if (xhr.responseText.indexOf('Error-1')==0){
				mesg = xhr.responseText.substring(xhr.responseText.indexOf('.')+2);
			}else{
				mesg = xhr.responseText+"&e&";
			}
			createAlert(replaceSpecialCharacters(mesg), "ERROR");
		}
    }
    else {
    	if (xhr.responseText.indexOf('html')>0){ //Devuelve una pantalla completa, no es el resultado de ajax
		    window.location.href = "./logout.do";
    	}else{
    		var message = ''; 
	    	if(locale=="en")
	    	  message = "Unexpected error&e&";
	    	else if(locale=="es")
	    	  message = "Error inesperado&e&";
	          createAlert(replaceSpecialCharacters(message), "ERROR");
    	}
    }
	$(".loading").css("display", "none");
	$("#cargando").hide();
}
function formaterGroupibng(cellvalue, options, rowObject){ return "3"; }

function controlDatosTimeout(data){
	if (data.indexOf("LogoLogin")>-1){
        window.location.href="./logout.do";
	}
}
/*
 * Función utilizada para que al usar el Datepicker se cree la 
 * fecha correctamente para que funcione en IE.
 * 
 */
function devuelveDate(cadenaDate)
{
	if (cadenaDate.toString().split("-").length > 2)
	{
		var fecha =  cadenaDate.toString().split("-");
		
		var diaFechaTitulo = parseInt(fecha[2],10);
		var mesFechaTitulo = parseInt(fecha[1],10);
		var anyoFechaTitulo = parseInt(fecha[0],10);
		
		return (new Date(anyoFechaTitulo, mesFechaTitulo - 1, diaFechaTitulo));
	}
	else
	{
		return (new Date(cadenaDate));
	}
	
}

function replaceAll(find, replace, str) 
{ 
	var strRep = '';
	var re = new RegExp(find, 'g'); 
	strRep = str.replace(re, replace);
	
	return strRep; 
}

function eliminarBlancos(str) 
{ 
	var strRep = '';
	if(str!=null){
		strRep = $.trim( str )
	}
	return strRep; 
}

function eliminarCaracteresNoPermitidos(str) {
	return str.replace(/[^\w]/g, function(match) {return match.charCodeAt(0);});
}

function isInt(n) { 
   return /^[0-9]+$/.test(n);   
}

//Función para carga de imagen erronea cuando no es posible cargar la imagen
function cargaFotoError(elementoImagen){
	elementoImagen.src = "./misumi/images/nofoto.png";
}

//Carga recursiva de funcion onerror para colocar una imagen predefinida para casos en 
//los que no es posible cargar la imagen
function cargarImgOnError(){
	$('img').attr("onerror",function() {cargaFotoError(this)});
}