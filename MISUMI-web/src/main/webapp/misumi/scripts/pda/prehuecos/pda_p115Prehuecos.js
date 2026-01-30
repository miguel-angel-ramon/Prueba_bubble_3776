var ejecutarEventoBlur = true;

function initializeScreen_p115(){
	// Controla el guardado del valor de Stock Lineal.
	events_p115_validarStockLineal();

	//Miramos qué contenido enseñamos, si el de la referencia o el de mensaje de promo.
	controlPromo();

	//Eventos de cabecera
	events_p115_volver();
	
	//Link para foto
	events_p115_foto();
	
	//Cuando hago click en el mensaje, se muestra la pantalla principal.
	events_p115_msgPromo();
}

function controlPromo(){
	var codArtPromo = document.getElementById("pda_p115_codArtPromo").value;
	var contenidoPaginaPromo = document.getElementById('contenidoPaginaPromo');
	var contenidoPagina = document.getElementById('contenidoPagina');

	if (codArtPromo != ""){
		contenidoPagina.style.display = "none";
		contenidoPaginaPromo.style.display = "block";

		//Reemplazamos texto con codigo de articulo
		document.getElementById('pda_p115_msg_promo').innerText = document.getElementById('pda_p115_msg_promo').innerText.replace('${0}',codArtPromo);
	}else{
		contenidoPagina.style.display = "block";
		contenidoPaginaPromo.style.display = "none";
	}
}

function events_p115_volver(){
	var imagenVolver = document.getElementById('pda_p10_volver');
	if (imagenVolver != null && typeof(imagenVolver) != 'undefined') {
		imagenVolver.onclick = function () {
			var existeMapNumeroEtiqueta = document.getElementById('existeMapNumeroEtiqueta');
			if (existeMapNumeroEtiqueta != null && typeof(existeMapNumeroEtiqueta) != 'undefined' && existeMapNumeroEtiqueta.value == "true") {
				window.location.href = "./pdaP03ExisteMapNumEti.do?sufijoPrehueco=_PREHUECO";
			} else {
				var origenGISAE = document.getElementById('pda_p12_origenGISAE');
				if (origenGISAE != null  && typeof(origenGISAE) != 'undefined' && origenGISAE.value == "SI"){
					window.location.href = "./pdaP42InventarioLibre.do?codArtAnterior=anterior";
				} else {
					window.location.href = "./pdawelcome.do";
				}
			}
		};
	}
}

function events_p115_foto(){
	var linkFoto = document.getElementById('pda_p115_fld_descripcionRef');
	
	if (linkFoto != null && typeof(linkFoto) != 'undefined') {
		linkFoto.onclick = function () {
			var codArt = document.getElementById("pda_p115_codArt").value;
			var tieneFoto = document.getElementById("pda_p115_tieneFoto").value;
			var procede = document.getElementById("pda_p115_procede").value;
			window.location.href = "./pdaP20FotoAmpliadaDatosReferencia.do?codArticulo="+codArt+"&tieneFoto="+tieneFoto+"&pestanaDatosRef=pdaP115PrehuecosLineal&procede="+procede;;

			//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
			linkFoto = null;
			codArt = null;
			tieneFoto = null;
		}
	}
}

function events_p115_msgPromo(){
	var contenidoPaginaPromo =  document.getElementById('contenidoPaginaPromo');
	if (contenidoPaginaPromo != null && typeof(contenidoPaginaPromo) != 'undefined') {
		contenidoPaginaPromo.onclick = function () {
			var contenidoPagina = document.getElementById('contenidoPagina');
			var contenidoPaginaPromo = document.getElementById('contenidoPaginaPromo');

			contenidoPagina.style.display = "block";
			contenidoPaginaPromo.style.display = "none";
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	contenidoPaginaPromo = null;
}

function events_p115_validarStockLineal(){
	var pda_p115_fld_stockLineal = document.getElementById('pda_p115_fld_stockLineal');
	var prehuecoSeleccionado = document.getElementById('prehuecoSeleccionado').value;

	if (pda_p115_fld_stockLineal != null) {

		pda_p115_fld_stockLineal.onkeydown = function(e) {
			e = e || window.event;
			var key = e.which || e.keyCode; //para soporte de todos los navegadores
			if (key == 13){
				if (this.value !== "") { // Verifica que tenga valor
					if(e.preventDefault) {
						e.preventDefault();
					} else {
						e.returnValue = false;
					}
					
//					if (prehuecoSeleccionado !== "" && prehuecoSeleccionado == "S"){
						actualizarStockLineal();
//					}
				
					//Evitamos que se ejecute el blur
					ejecutarEventoBlur = false;
					document.getElementById("pda_p01_txt_infoRef").focus();
				}
			}
		}
		
		pda_p115_fld_stockLineal.onblur = function(e) {

		    var valorStockLineal = pda_p115_fld_stockLineal.value;
		    var valorStockLinealOri = document.getElementById('pda_p115_fld_stockLineal_ori').value;

		    if (ejecutarEventoBlur
				&& ((valorStockLineal !== "" && valorStockLinealOri !== ""
					 && valorStockLineal != valorStockLinealOri
					)
					||(valorStockLineal !== "")
					||(valorStockLineal == "" && valorStockLinealOri !== "")
					)){
//				if (prehuecoSeleccionado !== "" && prehuecoSeleccionado == "S"){
					actualizarStockLineal();
//				}
			}else{
				ejecutarEventoBlur = true;
			}
		}

		pda_p115_fld_stockLineal.onclick = function(e) {
			this.select();
		}
	}
}

function actualizarStockLineal(){
    var valorStockLineal = pda_p115_fld_stockLineal.value;
    var valorStockLinealOri = document.getElementById('pda_p115_fld_stockLineal_ori').value;
	var codArt = document.getElementById("pda_p115_codArt").value;
	
	/***
	 * @author BICUGUAL MISUMI-664
	 * A fin de sacar trazas en el log de los datos que maneja la pantalla incluyo las siguiente varibles
	 */
	var origenPantalla = document.getElementById("origenPantalla").value;
	var procede = document.getElementById("pda_p115_procede").value;
	
	var pathName = window.location.pathname; // Ruta después del dominio
	var searchParams = window.location.search; // Parámetros de la URL (ej. ?id=123)
	
	var pdaOrigen =  document.getElementById("pdaOrigen").value;
	
	var otherDataForLog="&origenPantalla="+origenPantalla+"&procede="+procede+"&pathName="+pathName+"&pdaOrigen="+pdaOrigen;
	
	var params = 'codArt='+codArt + otherDataForLog;
	
    // Si se deja vacío el campo "Stock Lineal" --> BORRAR el registro si existe, de la tabla T_MIS_PREHUECOS_LINEAL.
    if (valorStockLineal==""){
		var url = './pdaP115BorrarPrehuecos.do';
		var method = 'POST';
		postData(url, method, params, true, "application/x-www-form-urlencoded", deletePrehuecos, hayError);
		
    }else if (valorStockLinealOri != valorStockLineal){
    	var url = './pdaP115AltaPrehuecos.do';
		var method = 'POST';
		params = params + '&stockLineal='+valorStockLineal;
		postData(url, method, params, true, "application/x-www-form-urlencoded", AddEditPrehuecos, hayError);
    }

    //Limpiar la variable DOM que no se utiliza más para evitar memory leak
	valorStockLineal=null;
	valorStockLinealOri=null;
}

function AddEditPrehuecos(data) {
    // La respuesta es el número de prehuecos sin validar
    var numPrehuecosSinValidar = parseInt(data.numPrehuecos, 10);  // Este valor es el Long retornado por el controlador

    // Lógica para manejar el valor, por ejemplo:
    if (numPrehuecosSinValidar > 0) {
    	document.getElementById('pda_p115_fld_stockLineal_ori').value=document.getElementById('pda_p115_fld_stockLineal').value;
    	document.getElementById('inputNumeroPrehuecos').value=numPrehuecosSinValidar;
    	
		var estadoRef = parseInt(data.estadoRef, 10);
		gestionIcono(estadoRef);
    	
    } else {
        alert('No hay prehuecos sin validar');
    }
}

function gestionIcono(valorEstado){
    // Crear imagen dinámica compatible con IE6
    var imgSrc = "";
    if (valorEstado === 0) {
        imgSrc = "./misumi/images/icono_aspa.png?version=${misumiVersion}";
    } else if (valorEstado === 1) {
        imgSrc = "./misumi/images/icono_pendiente.png?version=${misumiVersion}";
    } else if (valorEstado > 1) {
        imgSrc = "./misumi/images/icono_tick.png?version=${misumiVersion}";
    }

    // Actualizar imagen
    var estadoDiv = document.getElementById("pda_p115_prehuecoLineal_estado");

    // Eliminar contenido anterior
    while (estadoDiv.firstChild) {
        estadoDiv.removeChild(estadoDiv.firstChild);
    }

    // Crear y agregar imagen
    if (imgSrc !== "") {
        var img = document.createElement("img");
        img.src = imgSrc;
        img.style.width = "15px";
        img.style.height = "15px";
        estadoDiv.appendChild(img);
    }
}

function deletePrehuecos(data){
	var numPrehuecosSinValidar = parseInt(data.numPrehuecos, 10);

	// Lógica para manejar el valor, por ejemplo:
    if (numPrehuecosSinValidar > 0) {
    	document.getElementById('pda_p115_fld_stockLineal_ori').value="0";
    	document.getElementById('inputNumeroPrehuecos').value=numPrehuecosSinValidar;
    } else {
    	window.location.href = "./pdaP115MostrarMensajeError.do";
    }

	var estadoRef = parseInt(data.estadoRef, 10);
	document.getElementById('pda_p115_fld_stockLineal_ori').value=document.getElementById('pda_p115_fld_stockLineal').value;
	gestionIcono(estadoRef);
}

function hayError(){
	alert("Ha ocurrido un error.");
}

function events_p116_cargarReferenciaStockAlmacen(codArt){
	if (!codArt) {
//	    console.error("El parámetro codArt es necesario.");
	    return;
	}
	
	// Redirigir a la URL con el parámetro codArt
	var url= "pdaP118PrehuecosAlmacen.do?codArtCab=" + codArt + "&tipoListado=1" + "&soyPaletInput=true" + "&actionRef=pdaP115PrehuecosLineal.do";
	window.location.href = url;
}
