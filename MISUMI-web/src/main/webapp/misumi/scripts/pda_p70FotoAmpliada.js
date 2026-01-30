function initializeScreen_p70(){
	//Eventos de cabecera
	events_p70_volver();
}

function events_p70_volver(){
	var marcoPantalla =  document.getElementById('marco');
	if (marcoPantalla != null && typeof(marcoPantalla) != 'undefined') {
		marcoPantalla.onclick = function () {
			document.getElementById("pda_p70_fotoAmpliada_form").submit();
		}		
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	marcoPantalla = null;
}