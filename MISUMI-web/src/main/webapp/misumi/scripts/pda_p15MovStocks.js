function initializeScreen_p15(){
	//Eventos de cabecera
	events_p15_volver();
	
	//Link para foto
	events_p15_foto();
}


function events_p15_volver(){
	var imagenVolver =  document.getElementById('pda_p10_volver');
	if (imagenVolver != null && typeof(imagenVolver) != 'undefined') {
		imagenVolver.onclick = function () {
			var existeMapNumeroEtiqueta = document.getElementById('existeMapNumeroEtiqueta');
			if (existeMapNumeroEtiqueta != null && typeof(existeMapNumeroEtiqueta) != 'undefined' && existeMapNumeroEtiqueta.value == "true") {
				window.location.href = "./pdaP03ExisteMapNumEti.do";
			} else {
				var origenGISAE = document.getElementById('pda_p15_origenGISAE');
				if (origenGISAE != null && typeof(origenGISAE) != 'undefined' && origenGISAE.value == "SI"){
					window.location.href = "./pdaP42InventarioLibre.do?codArtAnterior=anterior";
				} else {
					window.location.href = "./pdawelcome.do";
				}
			}
		};
	}
}

function events_p15_foto(){
	var linkFoto = document.getElementById('pda_p15_fld_descripcionRef');

	if (linkFoto != null && typeof(linkFoto) != 'undefined') {
		linkFoto.onclick = function () {
			var codArt = document.getElementById("pda_p15_codArt").value;
			var tieneFoto = document.getElementById("pda_p15_tieneFoto").value;
			var procede = document.getElementById("pda_p15_procede").value;
			var pestanaDatosRef = 'pdaP15MovStocks';
			
//			if (procede != null && typeof(procede) != 'undefined' && procede=='pdaP115PrehuecosLineal') {
//				pestanaDatosRef = 'pdaP15MovStocks';
//			}
			window.location.href = "./pdaP20FotoAmpliadaDatosReferencia.do?codArticulo="+codArt+"&tieneFoto="+tieneFoto+"&pestanaDatosRef="+pestanaDatosRef+"&procede="+procede;
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	linkFoto = null;
	codArt = null;
	tieneFoto = null;
	procede = null;
}
