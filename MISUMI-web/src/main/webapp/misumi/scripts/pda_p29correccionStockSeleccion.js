function initializeScreen_p29() {
	events_29_volver();
}

function form_submit() {
	document.forms[0].action = "pdaP29CorreccionStockSeleccion.do?actionNext=yes";
	document.forms[0].submit();
}

function events_29_volver() {
	var imagenVolver = document.getElementById('pda_p10_volver');
	if (imagenVolver != null && typeof (imagenVolver) != 'undefined') {
		imagenVolver.onclick = function() {
			
			try {
				var url_string = (window.location.href).toLowerCase();
				var url = new URL(url_string);
				var actionsave = url.searchParams.get("actionsave");
				//Si se ha modificado el stock agrego parametro actionSave=yes para informar de que se ha guardado
				if (actionsave!=null){
					document.forms[0].action = "pdaP29CorreccionStockSeleccion.do?actionBack=yes&actionSave=yes";
				}else {
					document.forms[0].action = "pdaP29CorreccionStockSeleccion.do?actionBack=yes";
				}
			} catch (err) {
				document.forms[0].action = "pdaP29CorreccionStockSeleccion.do?actionBack=yes";
			}

			document.forms[0].submit();

		};
	}
}