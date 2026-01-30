//Función de calculo de campos
function events_p33_calculate(){
	event_p33_facing();
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
				var inputFacing = document.getElementById('pda_p33_fld_facing');
				var inputFondo = document.getElementById('pda_p33_fld_multiplicador');
				var inputCapacidad = document.getElementById('pda_p33_fld_capacidad');

				var fondo = inputFondo.value;
				var facing = inputFacing.value;
				var capacidad = inputCapacidad.value;

				if (fondo != ''){
					facing = facing != "" ? parseInt(facing) : 0;
					capacidad = facing * fondo;
				}else{
					capacidad = 0;
				}
				
				//Ponemos la nueva capacidad
				inputCapacidad.value = capacidad;
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

function isNumberKey(evt){
	var charCode = (evt.which) ? evt.which : event.keyCode;
	var numeroIsOk = ((charCode >= 48 && charCode <=57) || (charCode >= 96 && charCode <=105));
	return numeroIsOk;
}