function initializeScreen_p20(){
	//Eventos de cabecera
	events_p20_volver();
}

function events_p20_volver(){
	var marcoPantalla =  document.getElementById('marco');
	if (marcoPantalla != null && typeof(marcoPantalla) != 'undefined') {
		marcoPantalla.onclick = function () {
			document.getElementById("pda_p20_fotoAmpliadaDatosReferencia_form").submit();
		}		
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	marcoPantalla = null;
}