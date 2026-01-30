/* JAVASCRIPT CLASS: CalendarioCambioEstacional
 */
function CalendarioCambioEstacional (listadoValidacionesTratadas){
	// Atributos
	this.listadoValidacionesTratadas = listadoValidacionesTratadas;
		
	//Metodos	
	this.prepareToJsonObjectArticulo = function prepareToJsonObjectArticulo(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "listadoValidacionesTratadas" : listadoValidacionesTratadas		
			 }; 
		}
		
		return jsonObject;
	}
}
