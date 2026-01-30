function initializeScreen_p118() {
	events_p118_foto();
}
function events_p118_foto(){
	var foto =  document.getElementById('pda_p118_detalleReferencia_foto');

	if (foto != null && typeof(foto) != 'undefined') {
		foto.onclick = function () {
			var codArt  = this.getAttribute("data-codArtFoto");
			window.location.href = "./pdaP20FotoAmpliadaDatosReferencia.do?codArticulo="+codArt+"&tieneFoto=S&pestanaDatosRef=pdaP118DatosRefDetalleReferencia";
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	foto = null;
}