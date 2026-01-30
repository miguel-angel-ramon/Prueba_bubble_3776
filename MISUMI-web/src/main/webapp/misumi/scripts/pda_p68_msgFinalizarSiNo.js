'use strict';
function initializeScreen_p68(){
	
}

function events_p68(){
	var objetoSi = document.getElementById("pda_p68_btn_finalizarSi");
	if (objetoSi != null && typeof(objetoSi) != 'undefined') {
		objetoSi.style.display = "none";
		document.getElementById("pda_p68_btn_finalizarNo").style.display = "none";
		document.getElementById("pda_p68_msgFinalizarSiNo_form").submit();
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	objetoSi = null;
}
