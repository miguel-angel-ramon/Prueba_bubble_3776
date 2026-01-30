/*
 * JAVASCRIPT CLASS: TCalendarioDiaCambioServicio
 */
function TCalendarioDiaCambioServicio (codigoServicio, cambioManual){
	// Atributos
	this.codigoServicio = codigoServicio;
	this.cambioManual = cambioManual;
	
	//Metodos	
	this.prepareToJsonObjectArticulo = function prepareToJsonObjectArticulo(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codigoServicio" : codigoServicio,
				 "cambioManual" : cambioManual
			 }; 
		}		
		return jsonObject;
	}
}