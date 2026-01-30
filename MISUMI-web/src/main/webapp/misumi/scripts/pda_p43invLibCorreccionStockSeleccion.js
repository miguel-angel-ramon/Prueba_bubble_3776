function initializeScreen_p43(){
	events_43_volver();
}

function form_submit(){
   document.forms[0].action = "pdaP43InvLibCorreccionStockSeleccion.do?actionNext=yes";    
   document.forms[0].submit();
}

function events_43_volver(){
	var imagenVolver =  document.getElementById('pda_p10_volver');
	if (imagenVolver != null && typeof(imagenVolver) != 'undefined') {
		imagenVolver.onclick = function () {
			document.forms[0].action = "pdaP43InvLibCorreccionStockSeleccion.do?actionBack=yes";    
			document.forms[0].submit();

		};
	}
}
