function initializeScreen_p67(){
	//Eventos de cabecera
	events_p67_volver();
}

function events_p67_volver(){
	var marcoPantalla =  document.getElementById('marco');
	if (marcoPantalla != null && typeof(marcoPantalla) != 'undefined') {
		marcoPantalla.onclick = function () {
			document.getElementById('pda_p67_errorDevolucionLinea_form').submit();
		}		
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	 marcoPantalla = null;
}