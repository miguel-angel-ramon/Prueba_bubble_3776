function initializeScreen_p111(){
	//Eventos de cabecera
	events_p111_volver();
}


function events_p111_volver(){
    var marcoPantalla = document.getElementById('marco');
    if (marcoPantalla != null && typeof(marcoPantalla) != 'undefined') {
        marcoPantalla.onclick = function () {

            window.history.back(); 
        }
    }
    marcoPantalla = null;
}
