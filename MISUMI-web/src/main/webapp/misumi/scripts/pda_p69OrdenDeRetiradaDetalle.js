function initializeScreen_p69(){
	//Eventos de cabecera
	events_p69_volver();
}

function events_p69_volver(){
	var marcoPantalla =  document.getElementById('marco');
	if (marcoPantalla != null && typeof(marcoPantalla) != 'undefined') {
		marcoPantalla.onclick = function () {
			document.getElementById("pda_p69_ordenDeRetiradaDetalle_form").submit();
		}		
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	 marcoPantalla = null;
}