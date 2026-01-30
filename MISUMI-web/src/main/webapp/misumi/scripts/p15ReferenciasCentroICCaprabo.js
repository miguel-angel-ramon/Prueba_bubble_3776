

$(document).ready(function(){
	loadP15(locale);
	initializeScreenIC()
});

function initializeScreenIC(){	
}

function loadP15(locale){	
}


function loadImagenComercial(data){

	$("#p15_caprabo_divImplantacionVerde").attr("style", "display:none");
	$("#p15_caprabo_divImplantacionRojo").attr("style", "display:none");
	
	if (data.implantacion != null && data.mostrarImplantacion){ 
		if(data.flgColorImplantacion == "VERDE") {
			campo = $("#p15_caprabo_ParrafoImplantacionVerde");
			campo.text(data.implantacion);
			$("#p15_caprabo_divImplantacionVerde").attr("style", "display:inline-block");
		
		} else {
			if(data.flgColorImplantacion == "ROJO") {
				campo = $("#p15_caprabo_ParrafoImplantacionRojo");
				campo.text(data.implantacion);
				$("#p15_caprabo_divImplantacionRojo").attr("style", "display:inline-block");
			} 
		} 
	 }
	
}


function reloadImagenComercial(data) {

	loadImagenComercial(data);
}


