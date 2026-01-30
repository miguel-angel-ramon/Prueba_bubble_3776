function initializeScreen_p03InvLibShowMessage(){
	//Eventos de cabecera
	events_p42_nuevo();
	events_p42_seccion();
	events_p42_volver();
	
	posInicialCursorP42();
	
	fld_txt_infoRef = document.getElementById("pda_p01_txt_infoRef");
	fld_txt_infoRef.onkeydown = function(e) {
		e = e || window.event;
		var key = e.which || e.keyCode; //para soporte de todos los navegadores
	 	if (key == 13){//Tecla enter, guardado
	 		if(e.preventDefault) {
		        e.preventDefault();
		    } else {
		        e.returnValue = false;
		    }
	 		document.getElementById("pda_p42_fld_codArtCab").value = document.getElementById("pda_p01_txt_infoRef").value;
			document.getElementById("actionNuevo").value = "S";
			document.getElementById("pdaP42InventarioLibre").submit();
	    }
	}
}

function events_p42_volver(){
	var imagenVolver =  document.getElementById('pda_p10_volver');
	if (imagenVolver != null && typeof(imagenVolver) != 'undefined') {
		imagenVolver.onclick = function () {
			document.getElementById("actionVolver").value = "S";
			document.getElementById("pdaP42InventarioLibre").submit();
		}
	}
}

function events_p42_nuevo(){
	var btnNuevo =  document.getElementById('pda_p01_btn_aceptar');
	if (btnNuevo != null && typeof(btnNuevo) != 'undefined') {
		btnNuevo.onclick = function () {
			document.getElementById("pda_p42_fld_codArtCab").value = document.getElementById("pda_p01_txt_infoRef").value;
			document.getElementById("actionNuevo").value = "S";
			document.getElementById("pdaP42InventarioLibre").submit();
		}
	}
}

function events_p42_seccion(){
	var selectSeccion =  document.getElementById('pda_p01_txt_infoSeccion');
	if (selectSeccion != null && typeof(selectSeccion) != 'undefined') {
		selectSeccion.onchange = function () {
			document.getElementById("pda_p42_fld_seccion").value = selectSeccion.value;
			document.getElementById("actionSeccion").value = "S";
			document.getElementById("pdaP42InventarioLibre").submit();
		}
	}
}

function posInicialCursorP42(){
	idFoco = "pda_p01_txt_infoRef";
	document.getElementById(idFoco).focus();
	document.getElementById(idFoco).select();
}