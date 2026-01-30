window.onload = function(){
	initializeShowMessage();
	p01FocoReferencia();
}
function initializeShowMessage(){
	events_p03_bot_volver();
}
function events_p03_bot_volver(){
	var imagenVolver =  document.getElementById('pda_p10_volver');
	if (imagenVolver != null && typeof(imagenVolver) != 'undefined') {
		imagenVolver.onclick = function () {
			window.location.href = "./pdawelcome.do";

		};
	}
}
function p01FocoReferencia(){
	var p01Ref =  document.getElementById('pda_p01_txt_infoRef');
	if(p01Ref != null && typeof(p01Ref) != 'undefined') {
		p01Ref.focus();
	}
}