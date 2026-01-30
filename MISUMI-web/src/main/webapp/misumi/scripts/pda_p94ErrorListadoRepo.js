function initializeScreen_p94(){
	//Eventos de cabecera
	events_p94_volver();
}

function events_p94_volver(){
	var marcoPantalla =  document.getElementById('marco');
	if (marcoPantalla != null && typeof(marcoPantalla) != 'undefined') {
		marcoPantalla.onclick = function () {
			document.getElementById("pda_p94_errorListadoRepo_form").submit();
		}		
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	marcoPantalla = null;
}