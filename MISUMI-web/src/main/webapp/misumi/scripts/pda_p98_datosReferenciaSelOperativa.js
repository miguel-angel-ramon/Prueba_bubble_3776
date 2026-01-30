window.onload = function(){
	initializeShowMessage();
}
function initializeShowMessage(){
	events_p98_bot_action();
}
function events_p98_bot_action(){
	
	var botonAtras =  document.getElementById('pda_p98_btn_prev');
	if (botonAtras != null && typeof(botonAtras) != 'undefined') {
		botonAtras.onclick = function () {
			window.location.href = "./pdaP12BotonAtrasSelOperativa.do";
		};
	}
}
