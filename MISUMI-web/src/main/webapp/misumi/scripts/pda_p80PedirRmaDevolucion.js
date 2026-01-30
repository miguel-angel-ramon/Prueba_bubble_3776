'use strict';
function initializeScreen_p80(){
	events_p80_rma();
	
	posInicialCursorP80();
}

function events_p80_rma(){
	var objetoRma = document.getElementById("pda_p80_codRma");
	objetoRma.onkeydown = function (e) {
		e = e || window.event;
		var key = e.which || e.keyCode; //para soporte de todos los navegadores
		if (key == 13){//Tecla enter, enviar
			if(e.preventDefault) {
				e.preventDefault();
			} else {
				e.returnValue = false;
			}
			var objetoSi = document.getElementById("pda_p68_btn_finalizarSi");
			objetoSi.click();
			return false;
		}
	};
	// limitar a 50 caracteres
	objetoRma.onkeypress = function(e) {
		e = e || window.event;
		var key = e.which || e.keyCode; //para soporte de todos los navegadores
		// caracter imprimible y no mas de 15
		if(key > 47 && key < 91 && this.value.length >= 15){
			return false;
		}
		return true;
	};
}


function posInicialCursorP80(){
	var targetObject = document.getElementById("pda_p80_codRma");
	targetObject.focus();
}
