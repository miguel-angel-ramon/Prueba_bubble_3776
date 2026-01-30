//Función de calculo de campos
function events_p33_calculate(){
	p33Init();
	event_p33_alto();
	event_p33_ancho();
	event_p33_facing();
	event_p33_capacidad();
}

function p33Init(){	
	document.getElementById('pda_p33_fld_facAncho').select();
}

function event_p33_ancho(){
	//Conseguir el input ancho
	var inputAncho =  document.getElementById('pda_p33_fld_facAncho');

	//Mirar que los elementos no sean vacíos y les añadimos el evento
	if (inputAncho != null && typeof(inputAncho) != 'undefined' && inputAncho != null && typeof(inputAncho) != 'undefined') {		
		//Recalcula campos y numéricos si lo introducido es un número.
		inputAncho.onkeyup = function(e) {
			e = e || window.event;
			var charCode = e.keyCode;
			var numeroIsOk = ((charCode >= 48 && charCode <=57) || (charCode >= 96 && charCode <=105));
			if(numeroIsOk || charCode == 8 || charCode == 46){
				var inputAlto =  document.getElementById('pda_p33_fld_facAlto');
				var inputAncho =  document.getElementById('pda_p33_fld_facAncho');
				var inputFacing =  document.getElementById('pda_p33_fld_facing');
				var inputCapacidad =  document.getElementById('pda_p33_fld_capacidad');
				
				var inputAltoOrig =  document.getElementById('pda_p33_fld_facAltoOrig');
				var inputAnchoOrig =  document.getElementById('pda_p33_fld_facAnchoOrig');
				var inputFacingOrig =  document.getElementById('pda_p33_fld_facingOrig');
				var inputCapacidadOrig =  document.getElementById('pda_p33_fld_capacidadOrig');
				
				//Obtenemos los originales
				var facingOrig = inputFacingOrig.value != "" ? parseInt(inputFacingOrig.value) : 0;
				var anchoOrig = inputAnchoOrig.value != "" ? parseInt(inputAnchoOrig.value) : 0;
				var altoOrig = inputAltoOrig.value != "" ? parseInt(inputAltoOrig.value) : 0;
				var capacidadOrig = inputCapacidadOrig.value != "" ? parseInt(inputCapacidadOrig.value) : 0;
				
				//Calculamos el nuevo facing
				var ancho = inputAncho.value != "" ? parseInt(inputAncho.value) : 1;
				var alto = inputAlto.value != "" ? parseInt(inputAlto.value) : 1;

				var facing = (facingOrig/(anchoOrig*altoOrig))*(alto*ancho);

				//Ponemos el nuevo facing
				inputFacing.value = facing;

				var calculoCapacidad = capacidadOrig/facingOrig;
				var capacidad = Math.floor(calculoCapacidad * facing);
				
				//Ponemos la nueva capacidad
				inputCapacidad.value = capacidad;
				if (facing % 1 === 0) {
					//Simulamos
					simularImc(this);
				}else{
					hayErrorSimular();
				}
				
			}
		}
		//Evitamos que pueda escribir letras o campos no numéricos.
		inputAncho.onkeypress = function(e) {
			e = e || window.event;
			return isNumberKey(e);
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	inputAncho = null;
}

function event_p33_facing(){
	//Conseguir el input facing
	var inputFacing =  document.getElementById('pda_p33_fld_facing');

	//Mirar que los elementos no sean vacíos y les añadimos el evento
	if (inputFacing != null && typeof(inputFacing) != 'undefined' && inputFacing != null && typeof(inputFacing) != 'undefined') {		
		//Recalcula campos y numéricos si lo introducido es un número.
		inputFacing.onkeyup = function(e) {
			e = e || window.event;
			var charCode = e.keyCode;
			var numeroIsOk = ((charCode >= 48 && charCode <=57) || (charCode >= 96 && charCode <=105));
			if(numeroIsOk || charCode == 8 || charCode == 46){
				var inputAlto =  document.getElementById('pda_p33_fld_facAlto');
				var inputAncho =  document.getElementById('pda_p33_fld_facAncho');
				var inputFacing =  document.getElementById('pda_p33_fld_facing');
				var inputCapacidad =  document.getElementById('pda_p33_fld_capacidad');
				
				var inputAltoOrig =  document.getElementById('pda_p33_fld_facAltoOrig');
				var inputAnchoOrig =  document.getElementById('pda_p33_fld_facAnchoOrig');
				var inputFacingOrig =  document.getElementById('pda_p33_fld_facingOrig');
				var inputCapacidadOrig =  document.getElementById('pda_p33_fld_capacidadOrig');
				
				//Obtenemos el facing
				var facing = inputFacing.value != "" ? parseInt(inputFacing.value) : 1;

				//Obtenemos los originales
				var facingOrig = inputFacingOrig.value != "" ? parseInt(inputFacingOrig.value) : 0;
				var anchoOrig = inputAnchoOrig.value != "" ? parseInt(inputAnchoOrig.value) : 0;
				var altoOrig = inputAltoOrig.value != "" ? parseInt(inputAltoOrig.value) : 0;
				var capacidadOrig = inputCapacidadOrig.value != "" ? parseInt(inputCapacidadOrig.value) : 0;
				
				//Ponemos el nuevo alto y ancho
				inputAlto.value = 1;
				inputAncho.value = Math.floor(facing/(facingOrig/(anchoOrig*altoOrig)));

				var calculoCapacidad = capacidadOrig/facingOrig;
				var capacidad = Math.floor(calculoCapacidad * facing);
				
				//Ponemos la nueva capacidad
				inputCapacidad.value = capacidad;
				if (facing % 1 === 0) {
					//Simulamos
					simularImc(this);
				}else{
					hayErrorSimular();
				}
			}
		}
		//Cuando se hace un focusout, se recalcula el facing
		inputFacing.onblur = function(e) {
			var inputAlto =  document.getElementById('pda_p33_fld_facAlto');
			var inputAncho =  document.getElementById('pda_p33_fld_facAncho');
			var inputFacing =  document.getElementById('pda_p33_fld_facing');
			var inputCapacidad =  document.getElementById('pda_p33_fld_capacidad');
			
			var inputAltoOrig =  document.getElementById('pda_p33_fld_facAltoOrig');
			var inputAnchoOrig =  document.getElementById('pda_p33_fld_facAnchoOrig');
			var inputFacingOrig =  document.getElementById('pda_p33_fld_facingOrig');
			var inputCapacidadOrig =  document.getElementById('pda_p33_fld_capacidadOrig');
			
			//Obtenemos los originales
			var facingOrig = inputFacingOrig.value != "" ? parseInt(inputFacingOrig.value) : 0;
			var anchoOrig = inputAnchoOrig.value != "" ? parseInt(inputAnchoOrig.value) : 0;
			var altoOrig = inputAltoOrig.value != "" ? parseInt(inputAltoOrig.value) : 0;
			var capacidadOrig = inputCapacidadOrig.value != "" ? parseInt(inputCapacidadOrig.value) : 0;
			
			//Calculamos el nuevo facing
			var ancho = inputAncho.value != "" ? parseInt(inputAncho.value) : 1;
			var alto = inputAlto.value != "" ? parseInt(inputAlto.value) : 1;

			var facing = (facingOrig/(anchoOrig*altoOrig))*(alto*ancho);

			//Ponemos el nuevo facing
			inputFacing.value = facing;

			var calculoCapacidad = capacidadOrig/facingOrig;
			var capacidad = Math.floor(calculoCapacidad * facing);
			
			//Ponemos la nueva capacidad
			inputCapacidad.value = capacidad;
			
			if (facing % 1 === 0) {
				//Simulamos
				simularImc(this);
			}else{
				hayErrorSimular()
			}
		}
		//Evitamos que pueda escribir letras o campos no numéricos.
		inputFacing.onkeypress = function(e) {
			e = e || window.event;
			return isNumberKey(e);
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	inputFacing = null;
}

function event_p33_alto(){
	//Conseguir el input alto
	var inputAlto =  document.getElementById('pda_p33_fld_facAlto');


	//Mirar que los elementos no sean vacíos y les añadimos el evento
	if (inputAlto != null && typeof(inputAlto) != 'undefined' && inputAlto != null && typeof(inputAlto) != 'undefined') {		
		//Recalcula campos y numéricos si lo introducido es un número.
		inputAlto.onkeyup = function(e) {
			e = e || window.event;
			var charCode = e.keyCode;
			var numeroIsOk = ((charCode >= 48 && charCode <=57) || (charCode >= 96 && charCode <=105));
			if(numeroIsOk || charCode == 8 || charCode == 46){
				var inputAlto =  document.getElementById('pda_p33_fld_facAlto');
				var inputAncho =  document.getElementById('pda_p33_fld_facAncho');
				var inputFacing =  document.getElementById('pda_p33_fld_facing');
				var inputCapacidad =  document.getElementById('pda_p33_fld_capacidad');
				
				var inputAltoOrig =  document.getElementById('pda_p33_fld_facAltoOrig');
				var inputAnchoOrig =  document.getElementById('pda_p33_fld_facAnchoOrig');
				var inputFacingOrig =  document.getElementById('pda_p33_fld_facingOrig');
				var inputCapacidadOrig =  document.getElementById('pda_p33_fld_capacidadOrig');
				
				//Obtenemos los originales
				var facingOrig = inputFacingOrig.value != "" ? parseInt(inputFacingOrig.value) : 0;
				var anchoOrig = inputAnchoOrig.value != "" ? parseInt(inputAnchoOrig.value) : 0;
				var altoOrig = inputAltoOrig.value != "" ? parseInt(inputAltoOrig.value) : 0;
				var capacidadOrig = inputCapacidadOrig.value != "" ? parseInt(inputCapacidadOrig.value) : 0;
				
				//Calculamos el nuevo facing
				var ancho = inputAncho.value != "" ? parseInt(inputAncho.value) : 1;
				var alto = inputAlto.value != "" ? parseInt(inputAlto.value) : 1;

				var facing = (facingOrig/(anchoOrig*altoOrig))*(alto*ancho);

				//Ponemos el nuevo facing
				inputFacing.value = facing;

				var calculoCapacidad = capacidadOrig/facingOrig;
				var capacidad = Math.floor(calculoCapacidad * facing);
				
				//Ponemos la nueva capacidad
				inputCapacidad.value = capacidad;
				if (facing % 1 === 0) {
					//Simulamos
					simularImc(this);
				}else{
					hayErrorSimular();
				}
			}
		}
		//Evitamos que pueda escribir letras o campos no numéricos.
		inputAlto.onkeypress = function(e) {
			e = e || window.event;
			return isNumberKey(e);
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	inputAlto = null;
}

function event_p33_capacidad(){
	//Conseguir el input capacidad
	var inputCapacidad =  document.getElementById('pda_p33_fld_capacidad');

	//Mirar que los elementos no sean vacíos y les añadimos el evento
	if (inputCapacidad != null && typeof(inputCapacidad) != 'undefined' && inputCapacidad != null && typeof(inputCapacidad) != 'undefined') {		
		//Recalcula campos y numéricos si lo introducido es un número.
		inputCapacidad.onkeyup = function(e) {
			e = e || window.event;
			var charCode = e.keyCode;
			var numeroIsOk = ((charCode >= 48 && charCode <=57) || (charCode >= 96 && charCode <=105));
			if(numeroIsOk || charCode == 8 || charCode == 46){
				var inputCapacidad =  document.getElementById('pda_p33_fld_capacidad');
				var capacidad = inputCapacidad.value != "" ? parseInt(inputCapacidad.value) : 0;

				//Si capacidad es vacío, quitamos el guardar y pintamos de rojo
				if(capacidad == "" || capacidad == 0){
					//Ocultamos el guardar
					ocultarBotonGuardar();

					//Quitamos el rojo del campo.
					inputCapacidad.className = inputCapacidad.className.replace(/pda_p33_redInput/g,'');

					//Pintamos de rojo si está mal
					inputCapacidad.className = inputCapacidad.className + " pda_p33_redInput";
				}else{
					//Mostramos el guardar
					mostrarBotonGuardar();

					//Quitamos el rojo del campo.
					inputCapacidad.className = inputCapacidad.className.replace(/pda_p33_redInput/g,'');
				}				
			}
		}
		inputCapacidad.onchange = function(e) {
			var inputCapacidad =  document.getElementById('pda_p33_fld_capacidad');
			var inputImc = document.getElementById('pda_p33_fld_imagenMinimaComercial');

			var capacidad = inputCapacidad.value != "" ? parseInt(inputCapacidad.value) : 0;
			var imc = inputImc.value != "" ? parseInt(inputImc.value) : 0;				

			//Si capacidad es menor que IMC al salir del campo de capacidad, se corrige con el de IMC.
			if(capacidad < imc){
				inputCapacidad.value = imc;
			}

			//Volvemos a obtener la capacidad			
			var capacidad = inputCapacidad.value != "" ? parseInt(inputCapacidad.value) : 0;

			//Si capacidad es vacío, quitamos el guardar y pintamos de rojo
			if(capacidad == "" || capacidad == 0){
				//Ocultamos el guardar
				ocultarBotonGuardar();

				//Quitamos el rojo del campo.
				inputCapacidad.className = inputCapacidad.className.replace(/pda_p33_redInput/g,'');

				//Pintamos de rojo si está mal
				inputCapacidad.className = inputCapacidad.className + " pda_p33_redInput";
			}else{
				//Mostramos el guardar
				mostrarBotonGuardar();

				//Quitamos el rojo del campo.
				inputCapacidad.className = inputCapacidad.className.replace(/pda_p33_redInput/g,'');
			}
		}
		//Evitamos que pueda escribir letras o campos no numéricos.
		inputCapacidad.onkeypress = function(e) {
			e = e || window.event;
			return isNumberKey(e);
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	inputCapacidad = null;
}


function isNumberKey(evt){
	var charCode = (evt.which) ? evt.which : event.keyCode;
	var numeroIsOk = ((charCode >= 48 && charCode <=57) || (charCode >= 96 && charCode <=105));
	return numeroIsOk;
}    