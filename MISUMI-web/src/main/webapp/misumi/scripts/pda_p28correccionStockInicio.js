function initializeScreen_p29(){
	events_29_volver();
}

function form_submit(){
   document.forms[0].action = "pdaP29CorreccionStockSeleccion.do?actionNext=yes";    
   document.forms[0].submit();
}

function events_29_volver(){
	var imagenVolver =  document.getElementById('pda_p10_volver');
	if (imagenVolver != null && typeof(imagenVolver) != 'undefined') {
		imagenVolver.onclick = function () {
			document.forms[0].action = "pdaP28CorreccionStockInicio.do?actionPrev=yes";
			document.forms[0].submit();

		};
	}
}
