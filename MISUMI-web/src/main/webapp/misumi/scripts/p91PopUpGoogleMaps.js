$(document).ready(function(){
	loadP91(locale);
	//p91_clearCenter();
});

function initializeScreenGooglePopup(){
	$( "#p91_tablonAnuncios" ).dialog({
		autoOpen: false,
		height: 'auto',
		width: 'auto',
		modal: true,
		title: p91TituloVentanaTablonPopup,
		resizable: false,
		close: function( event, ui ) {
			$("#p91_tablonAnuncios").empty();
		}
	});
}

function loadP91PopupGoogle(){
	//Se obtiene la dirección optenida de la dirección de google por geolocalización inversa.
	var direccionGoogle = $("#direccionGoogleMaps").val();

	//Si no se ha encontrado la dirección google mediante la api de geolocalización de inversa de google,
	//al menos, se muestra el mapa con longitud y latitud.
	if(direccionGoogle == "" || direccionGoogle == null){		
		var latitudCentro = $("#latitudCentro").val().trim();
		var longitudCentro = $("#longitudCentro").val().trim();
		direccionGoogle = encodeURI(latitudCentro+","+longitudCentro);
	}

	var llamadaGoogle = "https://www.google.com/maps/embed/v1/place?key=AIzaSyD8deQ7W7TUDsvH_Ykry3hP4JBetWmqROg&q="+direccionGoogle;
	iframeGoogle = "<iframe id='a' width='600' height='450' frameborder='0' style='border:0' src="+llamadaGoogle+" allowfullscreen></iframe>";

	$("#p91_tablonAnuncios").append(iframeGoogle);
	$("#p91_tablonAnuncios").dialog("open");
}

function loadP91(locale){
	this.i18nJSON = './misumi/resources/p91PopUpGoogle/p91PopUpGoogle_' + locale + '.json';
	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		p91TituloVentanaTablonPopup = data.p91TituloVentanaTablonPopup;
		initializeScreenGooglePopup();

		//Miramos que no estén calculadas la latitud y longitud.
		//Eso significaría que no se ha encontrado dirección y que
		//hay que calcularla.
		var latitudGoogle = $("#latitudCentro").val();
		var longitudGoogle = $("#longitudCentro").val();
		var direccionGoogle = $("#direccionGoogleMaps").val();

		//Si no se ha calculado la dirección.
		if(longitudGoogle != "" && latitudGoogle != "" && direccionGoogle == ""){
			conseguirDireccionGoogleApi();
		}
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

function conseguirDireccionGoogleApi(){
	var latitudCentro = $("#latitudCentro").val().trim();
	var longitudCentro = $("#longitudCentro").val().trim();

	var noExisteCalle = true;

	//Reseteamos el valor de la dirección porque en Firefox a veces guarda la dirección del caso anterior.
	//No se ha encontrado explicación a ese comportamiento, por lo que con esta línea se soluciona y se evita
	//que pueda haber problemas.
	//$("#direccionGoogleMaps").val("");
	$.ajax({
		type : 'GET',
		url : 'https://maps.googleapis.com/maps/api/geocode/json?latlng='+latitudCentro+','+longitudCentro,
		cache : false,
		success : function(data) {	
			if(data != null){
				if(data.status == "OK"){
					if(data.results.length > 0){
						//Obtenemos la primera dirección de google.
						var direccion = data.results[0].formatted_address;

						//Miramos que la primera dirección es una calle.
						if(data.results[0].types.indexOf("street_address") >=0){
							noExisteCalle = false;
						}

						//Si la primera dirección no es una calle, buscamos entre las demás direcciones
						//para encontrar una que sí lo sea.
						if(noExisteCalle){
							var direccionCandidata;
							//Nos quedamos con la dirección que describa una calle.
							for(var i = 1;i<data.results.length;i++){
								var objDireccionCandidata = data.results[i];
								if(objDireccionCandidata.types.indexOf("street_address") >=0){
									direccion = objDireccionCandidata.formatted_address;
									noExisteCalle = false;
								}
							}
						}

						//Si no se ha encontrado calle todavía, nos quedamos con el campo más largo, pues será el que más
						//información tenga en principio de donde se encuentra el centro.
						if(noExisteCalle){
							var direccionCandidata;
							//Comparar las direcciones y quedarnos con la más larga que será la más completa.
							for(var i = 1;i<data.results.length;i++){
								direccionCandidata = data.results[i].formatted_address;
								if(direccionCandidata.length > direccion.length){
									direccion = direccionCandidata;
								}
							}
						}

						//Guardar la dirección en el input oculto. Este input guardará la dirección de google
						//para luego dibujarlo en el iframe.
						$("#direccionGoogleMaps").val(encodeURI(direccion.replace(/ /g,"+")));

						//Guardamos en la bd la direccion del centro, para no tener que volver a buscarla mediante la api 
						//de google.
						guardarDireccionGoogleCentro(direccion);
					}
				}
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});
}

function guardarDireccionGoogleCentro(direccion){
	var direccionGoogle = encodeURI(direccion.replace(/ /g,"+"));

	$.ajax({
		type : 'POST',
		url : './googleMaps/guardarDireccionGoogle.do',
		data: {"direccionGoogle":direccionGoogle},
		dataType : "json",
		cache : false,
		success : function(data) {},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});
}

function p91_clearCenter(){
	$("#centerName").click(function(){
		$("#latitudCentro").removeAttr('value');
		$("#longitudCentro").removeAttr('value');
		$("#direccionGoogleMaps").removeAttr('value');
	});
}