function initializeScreen_p22(){
	fld_capacidad = document.getElementById("pda_p22_fld_capacidad");
	if (fld_capacidad != null && typeof(fld_capacidad) != 'undefined') {
		fld_capacidad.onclick = function(e) {
			e = e || window.event;
			this.select();
			if(e.stopPropagation) {
			    e.stopPropagation();
			} else {
				e.cancelBubble = true;
			}
		}
	}
	events_22_volver();
	posInicialCursorP22();
}

function events_22_volver(){
	var imagenVolver =  document.getElementById('pda_p10_volver');
	if (imagenVolver != null && typeof(imagenVolver) != 'undefined') {
		imagenVolver.onclick = function () {
			var origenConsulta = document.getElementById('pda_p22_fld_cap_origen');
			if (origenConsulta.value == "1"){
				 document.forms[0].action = "pdaP21Sfm.do?actionPrev=yes";    
				 document.forms[0].submit();
			} else {
				window.location.href = "./pdawelcome.do";
			}
		};
	}
}

function posInicialCursorP22(){
	// pila de objetivos por preferencia de foco inversamente ordenado
	var targetStack = [];

	targetStack.push("pda_p01_txt_infoRef");
	targetStack.push('pda_p22_fld_facing_con_multiplicador');
	targetStack.push("pda_p22_fld_capacidad");
	
	// si esta guardado que se posicione en la referencia preferentemente
	var bloqueGuardado =  document.getElementById('pda_p22_capacidad_bloqueGuardado');
	if (bloqueGuardado != null && typeof(bloqueGuardado) != 'undefined') {
		targetStack.push("pda_p01_txt_infoRef");
   	}
	
	// intenta poner el foco y la seleccion en orden inverso de asignacion
	var target;
	while(target = targetStack.pop()){
		target = document.getElementById(target);
		if(target && !target.disabled){
			target.focus();
			target.select();
			break;
		}
	}
	target = null;
}


