function initializeScreen_p109() {
	
	
	events_p109_foto();
	


}

function events_p109_foto() {
    var linkFotos = document.getElementById('pda_p108_articuloDescripcion');

    
    if (linkFotos != null && typeof(linkFotos) != 'undefined') {
        linkFotos.onclick = function() {
            var codArtFromDesc = document.getElementById('pda_p12_codArt').value;
            var tieneFoto = document.getElementById("pda_p12_tieneFoto").value;
            var fechaDesde = document.getElementById('pda_p12_fechaDesde').value;
            var fechaHasta = document.getElementById('pda_p12_fechaHasta').value;

            window.location.href = "./pdaP109FotoAmpliadaDatosReferencia.do?codArticulo="
                + codArtFromDesc
                + "&tieneFoto="
                + tieneFoto
                + "&fechaDesde="
                + fechaDesde
                + "&fechaHasta="
                + fechaHasta
                + "&pestanaDatosRef=pdaP109ConsultasReferencia";
        }
    }

    linkFotos = null;
}
