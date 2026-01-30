window.onload = function(){
	var infoRef =  document.getElementById('pda_p01_txt_infoRef');
	if (infoRef != null && typeof(infoRef) != 'undefined') {
		infoRef.className = infoRef.className + " inputResaltado";
		infoRef.value = "";
		infoRef.focus();
		events_p01_referencia();
		events_p01_img_cerrar();
	}
	initializeScreen();
	var ventaAnticipada_unidades = document.getElementById("pda_p25_ventaAnticipada_unidades");
	var bnt_save = document.getElementById("pda_p25_btn_save");
	if (ventaAnticipada_unidades != null && typeof(ventaAnticipada_unidades) != 'undefined') {
		ventaAnticipada_unidades.onkeydown = function(e) {
			e = e || window.event;
			var key = e.which || e.keyCode;
			if(key == 13) {
				if(e.preventDefault) {
			        e.preventDefault();
			    } else {
			        e.returnValue = false;
			    }  
				if (bnt_save != null && typeof(bnt_save) != 'undefined') {
					bnt_save.click();
				}
			}
		}
	}
	if (bnt_save != null && typeof(bnt_save) != 'undefined') {
		bnt_save.onkeydown = function(e) {
			e = e || window.event;
			var key = e.which || e.keyCode;
			if(key == 13) {
				if(e.preventDefault) {
			        e.preventDefault();
			    } else {
			        e.returnValue = false;
			    }  
				bnt_save.click();
			}
		}
	}
}
function initializeScreen(){
	var bloqueGuardado =  document.getElementById('pda_p25_ventaAnticipada_bloqueGuardado');
	if (bloqueGuardado == null || typeof(bloqueGuardado) == 'undefined') {
		var unidades =  document.getElementById('pda_p25_ventaAnticipada_unidades');
		if (unidades != null && typeof(unidades) != 'undefined') {
			unidades.focus();
			if (unidades.value != ''){
				unidades.select();
			}
		}
	}
}
function events_p01_referencia(){
	var infoRef =  document.getElementById('pda_p01_txt_infoRef');
	if (infoRef != null && typeof(infoRef) != 'undefined') {
		infoRef.onclick = function () {
			limpiarCabecera();
		}
	}
}
function events_p01_img_cerrar(){
	var imagenCerrar =  document.getElementById('pda_p01_imagenCerrar');
	if (imagenCerrar != null && typeof(imagenCerrar) != 'undefined') {
		imagenCerrar.onclick = function () {
			Close_Window();
		}
	}
}
function limpiarCabecera() {
	var infoRef =  document.getElementById('pda_p01_txt_infoRef');
	if (infoRef != null && typeof(infoRef) != 'undefined') {
		infoRef.value = "";
		infoRef.className = infoRef.className + " inputResaltado";
	}
	var descRef =  document.getElementById('pda_p01_txt_descRef');
	if (descRef != null && typeof(descRef) != 'undefined') {
		descRef.value = "";
	}
}
function Close_Window() {
	self.close();
}
