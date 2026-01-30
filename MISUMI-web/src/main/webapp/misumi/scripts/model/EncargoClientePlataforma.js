/*
 * JAVASCRIPT CLASS: EncargoClientePlataforma
 */
function EncargoClientePlataforma (centro, seccion, categoria){
	// Atributos
	this.centro=centro;
	this.seccion=seccion;
	this.categoria = categoria;

		
	//Metodos	
	this.prepareToJsonObject = function prepareToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codCentro" : centro,
				 "categoria" : categoria,
				 "seccion" : seccion
				 
				 
			 }; 
		}
		
		return jsonObject;
	}
}

