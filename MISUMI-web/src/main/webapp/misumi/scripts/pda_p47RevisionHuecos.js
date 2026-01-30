function initializeScreen_p47(){
	//Eventos de cabecera
	events_p47_volver();

}

function events_p47_volver(){
	var imagenVolver =  document.getElementById('pda_p10_volver');
	if (imagenVolver != null && typeof(imagenVolver) != 'undefined') {
		imagenVolver.onclick = function () {
			document.getElementById("actionVolver").value = "S";
			document.getElementById("pdaP47RevisionHuecos").submit();
		};
	}
}
