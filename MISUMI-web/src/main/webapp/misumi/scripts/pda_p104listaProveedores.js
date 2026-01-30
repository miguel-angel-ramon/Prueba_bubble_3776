function initializeScreen_p104() {
	events_p104_titulo();
	events_p104_finalizar();
}

function events_p104_titulo(){
	var tituloLink = document.getElementById('pdaP104Titulo');
	var tipoDevolucion = document.getElementById('tipoDevolucion');
	var devolucionId = document.getElementById('devolucion').value;
	
	if (tituloLink != null && typeof(tituloLink) != 'undefined') {
		tituloLink.onclick = function () {
			
			if (tipoDevolucion.value=='OrdenRetirada'){
				realizarAccionSiValido("pdaP104ListaProveedores", "OrdenRetirada", devolucionId);
			}else{
				realizarAccionSiValido("pdaP104ListaProveedores", "FinCampana", devolucionId);
			}
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	tituloLink = null;
}

function events_p104_finalizar(){
	var btn_fin =  document.getElementById('pda_p104_btn_fin');
	var devolucionId = document.getElementById('devolucion').value;

	if (btn_fin != null && typeof(btn_fin) != 'undefined') {
		btn_fin.onclick = function () {
			document.getElementById("pda_p104_btn_fin").style.display = "none";
			realizarAccionSiValido("pdaP104ListaProveedores", "Finalizar", devolucionId);
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	btn_fin = null;
}

function events_p104_cargarPaginaBultosProv(indiceProveedor){
	var devolucionId = document.getElementById('devolucion').value;
	var descProveedor = document.getElementById('descProv'+indiceProveedor).value;

	realizarAccionSiValido("pdaP104ListaProveedores", "CargarBultos", devolucionId, descProveedor);
}

function realizarAccionSiValido(pagina, accion, devolucion, descProveedor){
	document.getElementById("accion").value = accion;
	document.getElementById("devolucion").value = devolucion;
	if (descProveedor != null && typeof(descProveedor) != 'undefined') {
		document.getElementById("descProveedor").value = descProveedor;
	}
	document.getElementById(pagina).submit();
}

