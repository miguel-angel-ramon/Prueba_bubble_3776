/*
 * JAVASCRIPT CLASS: TCalendarioDia
 */
function TCalendarioDia (fechaCalendario, festivo, ponerDiaVerde, cerrado, servicioHabitual, cambioEstacional, cambioManual, suministro,flgServiciosBuscados){
	// Atributos
	this.fechaCalendario = fechaCalendario;
	this.festivo = festivo;
	this.ponerDiaVerde = ponerDiaVerde;
	this.cerrado = cerrado;
	this.servicioHabitual = servicioHabitual;
	this.cambioEstacional = cambioEstacional;
	this.cambioManual = cambioManual;
	this.suministro = suministro;
	this.flgServiciosBuscados = flgServiciosBuscados;
	//Metodos	
	this.prepareToJsonObjectArticulo = function prepareToJsonObjectArticulo(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "fechaCalendario" : fechaCalendario,
				 "festivo" : festivo,
				 "ponerDiaVerde" : ponerDiaVerde,
				 "cerrado" : cerrado,
				 "servicioHabitual" : servicioHabitual,
				 "cambioEstacional" : cambioEstacional,
				 "cambioManual" : cambioManual,
				 "suministro" : suministro,
				 "flgServiciosBuscados" : flgServiciosBuscados
			 }; 
		}
		
		return jsonObject;
	}
}
