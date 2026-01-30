function initializeScreen_p12(){
	//Miramos qué contenido enseñamos, si el de la referencia o el de mensaje de promo.
	controlPromo();

	//Eventos de cabecera
	events_p12_volver();
	
	//Link para foto
	events_p12_foto();
	
	//Cuando hago click en el mensaje, se muestra la pantalla principal.
	events_p12_msgPromo();
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

function events_p12_foto(){
	var linkFoto = document.getElementById('pda_p12_fld_descripcionRef');
	
	if (linkFoto != null && typeof(linkFoto) != 'undefined') {
		linkFoto.onclick = function () {
			var codArt = document.getElementById("pda_p12_codArt").value;
			var tieneFoto = document.getElementById("pda_p12_tieneFoto").value;
			window.location.href = "./pdaP20FotoAmpliadaDatosReferencia.do?codArticulo="+codArt+"&tieneFoto="+tieneFoto+"&pestanaDatosRef=pdaP12DatosReferencia";
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	linkFoto = null;
	codArt = null;
	tieneFoto = null;
}

function controlPromo(){
	var codArtPromo = document.getElementById("pda_p12_codArtPromo").value;
	var contenidoPaginaPromo =  document.getElementById('contenidoPaginaPromo');
	var contenidoPagina =  document.getElementById('contenidoPagina');
	
	if(codArtPromo != ""){
		contenidoPagina.style.display = "none";
		contenidoPaginaPromo.style.display = "block";
		
		//Reemplazamos texto con codigo de articulo
		document.getElementById('pda_p12_msg_promo').innerText = document.getElementById('pda_p12_msg_promo').innerText.replace('${0}',codArtPromo);
	}else{
		contenidoPagina.style.display = "block";
		contenidoPaginaPromo.style.display = "none";
	}
}

function events_p12_msgPromo(){
	var contenidoPaginaPromo =  document.getElementById('contenidoPaginaPromo');
	if (contenidoPaginaPromo != null && typeof(contenidoPaginaPromo) != 'undefined') {
		contenidoPaginaPromo.onclick = function () {
			var contenidoPagina =  document.getElementById('contenidoPagina');
			var contenidoPaginaPromo =  document.getElementById('contenidoPaginaPromo');
			
			contenidoPagina.style.display = "block";
			contenidoPaginaPromo.style.display = "none";
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	contenidoPaginaPromo = null;
}
