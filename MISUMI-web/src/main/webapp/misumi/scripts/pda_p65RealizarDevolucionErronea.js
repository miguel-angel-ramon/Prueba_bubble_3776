function initializeScreen_p65(){
	//Eventos de cabecera
	//events_p65_titulo();
	events_p65_volver();
	
}

function events_p65_volver(){
	var marcoPantalla =  document.getElementById('marco');
	if (marcoPantalla != null && typeof(marcoPantalla) != 'undefined') {
		marcoPantalla.onclick = function () {
			document.getElementById('pda_p65_devolucionErronea_form').submit();
		}		
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	 marcoPantalla = null;
}

function events_p65_titulo(){
	var tituloLink =  document.getElementById('pda_p65_titulo');

	if (tituloLink != null && typeof(tituloLink) != 'undefined') {
		tituloLink.onclick = function (e) {
			e.stopPropagation();
			document.getElementById("accion").value = "VerCabeceraErrOk";
			document.getElementById('pda_p65_devolucionErronea_form').submit();
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	tituloLink = null;
}