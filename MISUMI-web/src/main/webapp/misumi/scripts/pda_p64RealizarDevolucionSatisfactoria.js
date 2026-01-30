function initializeScreen_p64(){
	//Eventos de cabecera
	//events_p64_titulo();
	events_p64_volver();
}

function events_p64_titulo(){
	var tituloLink =  document.getElementById('pda_p64_titulo');

	if (tituloLink != null && typeof(tituloLink) != 'undefined') {
		tituloLink.onclick = function (e) {
			e.stopPropagation();
			document.getElementById("accion").value = "VerCabeceraErrOk";
			document.getElementById('pda_p64_devolucionSatisfactoria_form').submit();
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	tituloLink = null;
}

function events_p64_volver(){
	var marcoPantalla =  document.getElementById('marco');
	if (marcoPantalla != null && typeof(marcoPantalla) != 'undefined') {
		marcoPantalla.onclick = function () {
			 window.location = './pdaP60RealizarDevolucion.do'; 
		}		
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	 marcoPantalla = null;
}

