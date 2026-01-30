window.onload = function(){
	initializeSesionCaducada();
}
function initializeSesionCaducada(){
	events_p03_bot_aceptar();
}
function events_p03_bot_aceptar(){
	var botCerrar =  document.getElementById('pda_p03_botAceptar');
	if (botCerrar != null && typeof(botCerrar) != 'undefined') {
		botCerrar.onclick = function () {
			self.close();
		}
	}
}