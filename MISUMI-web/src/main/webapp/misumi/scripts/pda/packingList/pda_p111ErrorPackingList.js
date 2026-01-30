function initializeScreen_p111(){
	//Eventos de cabecera
	events_p111_volver();
}

function events_p111_volver(){
	var marcoPantalla = document.getElementById('marco');
	if (marcoPantalla != null && typeof(marcoPantalla) != 'undefined') {
		marcoPantalla.onclick = function () {
			var actionP111Volver = document.getElementById('controlVolver').value;
			window.location = './'+actionP111Volver;
			actionP111Volver = null;
		}
	}
	marcoPantalla = null;
}