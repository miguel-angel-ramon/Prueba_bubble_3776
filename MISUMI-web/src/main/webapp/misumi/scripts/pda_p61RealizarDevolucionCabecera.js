function initializeScreen_p61(){
	//Eventos de cabecera
	events_p61_volver();
}

function events_p61_volver(){
	var marcoPantalla =  document.getElementById('marco');
	if (marcoPantalla != null && typeof(marcoPantalla) != 'undefined') {
		marcoPantalla.onclick = function () {
			var origenPantallaPda = document.getElementById("origenPantalla").value;			
			if(origenPantallaPda == 'pdaP64'){
				window.location = './pdaP60RealizarDevolucion.do'; 
			}else if(origenPantallaPda == 'pdaP104' || origenPantallaPda == 'pdaP105'){
				var devolucionId = document.getElementById('devolucion').value;
				document.getElementById("devolucion").value = devolucionId;
				document.getElementById("pda_p61_realizarDevolucionCabecera_form").submit();
			}else{
				document.getElementById("pda_p61_realizarDevolucionCabecera_form").submit();
			}
			origenPantallaPda = null;
		}
	}
	marcoPantalla = null;
}