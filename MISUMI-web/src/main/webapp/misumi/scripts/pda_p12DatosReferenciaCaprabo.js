function initializeScreen_p12(){
	//Eventos de cabecera

	events_p12_volver();

}


function events_p12_volver(){
	var imagenVolver =  document.getElementById('pda_p10_volver');
	if (imagenVolver != null && typeof(imagenVolver) != 'undefined') {
		imagenVolver.onclick = function () {
			var existeMapNumeroEtiqueta = document.getElementById('existeMapNumeroEtiqueta');
			if (existeMapNumeroEtiqueta != null && typeof(existeMapNumeroEtiqueta) != 'undefined' && existeMapNumeroEtiqueta.value == "true") {
				window.location.href = "./pdaP03ExisteMapNumEti.do";
			} else {
				var origenGISAE = document.getElementById('pda_p12_origenGISAE');
				if (origenGISAE != null  && typeof(origenGISAE) != 'undefined' && origenGISAE.value == "SI"){
					window.location.href = "./pdaP42InventarioLibre.do?codArtAnterior=anterior";
				} else {
					window.location.href = "./pdawelcome.do";
				}
			}
		};
	}
}
