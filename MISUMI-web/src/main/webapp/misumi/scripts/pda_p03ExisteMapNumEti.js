window.onload = function(){
	initializeShowMessage();
}
function initializeShowMessage(){
	events_p03_bot_action();
}
function events_p03_bot_action(){
	var botonNo=document.getElementById('pda_p03_btn_no');
	if (botonNo != null && typeof(botonNo) != 'undefined') {
		botonNo.onclick = function () {
			window.history.back();
			return false;
		};
	}
	var botonSi=document.getElementById('pda_p03_btn_si');
	var sufijoPrehueco=document.getElementById('pda_p115_sufijoPrehueco').value;
	if (botonSi != null && typeof(botonSi) != 'undefined') {
		botonSi.onclick = function () {
			window.location.href = "./pdaP03ResetNumeroEtiqueta.do?sufijoPrehueco="+sufijoPrehueco;
		};
	}
}
