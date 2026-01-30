function initializeScreen_p93(){
	//Eventos de cabecera
	events_p93_volver();
}

function events_p93_volver(){
	var marcoPantalla =  document.getElementById('marco');
	if (marcoPantalla != null && typeof(marcoPantalla) != 'undefined') {
		marcoPantalla.onclick = function () {
			document.getElementById("pda_p93_fotoAmpliada_form").submit();
		}		
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	marcoPantalla = null;
}