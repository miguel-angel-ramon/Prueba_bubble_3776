function initializeScreen_p105() {
	events_p105_titulo();
	events_p105_borrarBulto();
	events_p105_abrirCaja();
	events_p105_finalizar();
}

function events_p105_titulo(){
	var tituloLink = document.getElementById('pdaP105Titulo');
	var tipoDevolucion = document.getElementById('tipoDevolucion');
	var devolucionId = document.getElementById('devolucion').value;
	
	if (tituloLink != null && typeof(tituloLink) != 'undefined') {
		tituloLink.onclick = function () {
			
			if (tipoDevolucion.value=='OrdenRetirada'){
				realizarAccionSiValido("pdaP105ListaBultosProv", "OrdenRetirada", devolucionId);
			}else{
				realizarAccionSiValido("pdaP105ListaBultosProv", "FinCampana", devolucionId);
			}
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	tituloLink = null;
}

function events_p105_abrirCaja(bulto){
	var abrirCajaLink = document.getElementById('abrirCaja'+bulto);
	var devolucionId = document.getElementById('devolucion').value;
	
	if (abrirCajaLink != null && typeof(abrirCajaLink) != 'undefined') {
		realizarAccionSiValido("pdaP105ListaBultosProv", "A", devolucionId, bulto);
	}
}

function events_p105_borrarBulto(bulto){
	var borrarBultoLink = document.getElementById('borrarBulto'+bulto);
	var devolucionId = document.getElementById('devolucion').value;

	if (borrarBultoLink != null && typeof(borrarBultoLink) != 'undefined') {
		realizarAccionSiValido("pdaP105ListaBultosProv", "B", devolucionId, bulto);
	}
}

function events_p105_finalizar(){
	var btn_fin =  document.getElementById('pda_p105_btn_fin');
	var devolucionId = document.getElementById('devolucion').value;

	if (btn_fin != null && typeof(btn_fin) != 'undefined') {
		btn_fin.onclick = function () {
			realizarAccionSiValido("pdaP105ListaBultosProv", "Finalizar", devolucionId);
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	btn_fin = null;
}

function realizarAccionSiValido(pagina, accion, devolucion, bulto){
	document.getElementById("accion").value = accion;
	document.getElementById("devolucion").value = devolucion;
	document.getElementById("bulto").value = bulto;
//	document.getElementById("proveedor").value = proveedor;
	document.getElementById(pagina).submit();
}

