function initializeScreen_p95(){
	//Eventos de cabecera
	events_p95_volver();
}

function events_p95_volver(){
	var marcoPantalla =  document.getElementById('marco');
	if (marcoPantalla != null && typeof(marcoPantalla) != 'undefined') {
		marcoPantalla.onclick = function () {
			document.getElementById("pda_p95_descripcionDetalle_form").submit();
		}		
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	marcoPantalla = null;
}