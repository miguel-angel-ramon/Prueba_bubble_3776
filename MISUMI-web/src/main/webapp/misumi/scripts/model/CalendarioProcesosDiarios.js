/*
 * JAVASCRIPT CLASS: CalendarioProcesosDiarios
 */
function CalendarioProcesosDiarios (listadoServiciosCentroTemporal){
	// Atributos
	this.listadoServiciosCentroTemporal = listadoServiciosCentroTemporal;	
	
	//Metodos	
	this.prepareToJsonObjectArticulo = function prepareToJsonObjectArticulo(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
					"listadoServiciosCentroTemporal" : listadoServiciosCentroTemporal
			 }; 
		}
		
		return jsonObject;
	}
}
