function initializeScreen_p13(){
	//Eventos de cabecera
	events_p13_volver();
	
	//Link para foto
	events_p13_foto();
}


function events_p13_volver(){
	var imagenVolver =  document.getElementById('pda_p10_volver');
	if (imagenVolver != null && typeof(imagenVolver) != 'undefined') {
		imagenVolver.onclick = function () {
			var existeMapNumeroEtiqueta = document.getElementById('existeMapNumeroEtiqueta');
			if (existeMapNumeroEtiqueta != null && typeof(existeMapNumeroEtiqueta) != 'undefined' && existeMapNumeroEtiqueta.value == "true") {
				window.location.href = "./pdaP03ExisteMapNumEti.do";
			} else {
				var origenGISAE = document.getElementById('pda_p13_origenGISAE');
				if (origenGISAE.value == "SI"){
					window.location.href = "./pdaP42InventarioLibre.do?codArtAnterior=anterior";
				} else {
					window.location.href = "./pdawelcome.do";
				}
			}
		};
	}
	
	imagenVolver = null;
}

function events_p13_foto(){
	var linkFoto = document.getElementById('pda_p13_fld_descripcionRef');

	if (linkFoto != null && typeof(linkFoto) != 'undefined') {
		linkFoto.onclick = function () {
			var codArt = document.getElementById("pda_p13_codArt").value;
			var tieneFoto = document.getElementById("pda_p13_tieneFoto").value;
			var procede = document.getElementById("pda_p13_procede").value;
			var pestanaDatosRef = 'pdaP13SegPedidos';
			
//			if (procede != null && typeof(procede) != 'undefined' && procede=='pdaP115PrehuecosLineal') {
//				pestanaDatosRef = 'pdaP13SegPedidos';
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
