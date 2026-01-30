function initializeScreen_p98(){
	//Link para foto
	events_p98_foto();
}

function form_submit(){
	var codArt = document.getElementById("pda_p98_codArt").value;
	var flgCapacidadIncorrecta = document.getElementById("pda_p98_checkbox_no_ok").checked ? "S" : "N";
	
	document.forms.flgCapacidadIncorrecta.method='POST';
	document.forms.flgCapacidadIncorrecta.action = "pdaP98CapturaRestos/capacidadIncorrecta.do?codArticulo="+codArt+"&flgCapacidadIncorrecta="+flgCapacidadIncorrecta;
	document.forms.flgCapacidadIncorrecta.submit();
}

function events_p98_foto(){
	var linkFoto =  document.getElementById('pda_p98_fld_descripcionRef');
	
	if (linkFoto != null && typeof(linkFoto) != 'undefined') {
		linkFoto.onclick = function () {
			var codArt = document.getElementById("pda_p98_codArt").value;
			var tieneFoto = document.getElementById("pda_p98_tieneFoto").value;
			//window.location.href = "./pdaP20FotoAmpliadaDatosReferencia.do?codArticulo="+codArt+"&tieneFoto="+tieneFoto+"&pestanaDatosRef=pdaP12DatosReferencia";
			window.location.href = "./pdaP20FotoAmpliadaDatosReferencia.do?codArticulo="+codArt+"&tieneFoto="+tieneFoto+"&pestanaDatosRef=pdaP98CapturaRestos";
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	linkFoto = null;
}