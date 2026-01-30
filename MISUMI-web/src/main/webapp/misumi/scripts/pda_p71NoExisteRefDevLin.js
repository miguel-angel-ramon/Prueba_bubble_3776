function initializeScreen_p71(){
	//Eventos de cabecera
	events_p71_volver();
}

function events_p71_volver(){
	var marcoPantalla =  document.getElementById('marco');
	if (marcoPantalla != null && typeof(marcoPantalla) != 'undefined') {
		marcoPantalla.onclick = function () {
			document.getElementById("pda_p71_noExisteRefDevLin_form").submit();
		}		
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	 marcoPantalla = null;
}