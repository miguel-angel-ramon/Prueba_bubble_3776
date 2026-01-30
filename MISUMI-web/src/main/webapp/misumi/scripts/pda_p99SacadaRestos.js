function initializeScreen_p99(){

}

function events_p99_foto(){
	var linkFoto =  document.getElementById('pda_p99_fld_descripcionRef');
	if (linkFoto != null && typeof(linkFoto) != 'undefined') {
			var codArt = document.getElementById("pda_p99_codArt").value;
			var tieneFoto = document.getElementById("pda_p99_tieneFoto").value;
			window.location.href = "./pdaP20FotoAmpliadaDatosReferencia.do?codArticulo="+codArt+"&tieneFoto="+tieneFoto+"&pestanaDatosRef=pdaP99SacadaRestos";
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	linkFoto = null;
}