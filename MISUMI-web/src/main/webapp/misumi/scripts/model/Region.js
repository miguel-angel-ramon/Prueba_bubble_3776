/*
 * JAVASCRIPT CLASS: Region
 */
function Region (codRegion, codArea, codEnsena, codNegocio, descripcion){
	// Atributos
	this.codRegion = codRegion;
	this.codArea = codArea;
	this.codEnsena = codEnsena;
	this.codNegocio = codNegocio;
	this.descripcion = descripcion;
		
	//Metodos	
	this.prepareToJsonObjectRegion = function prepareToJsonObjectRegion(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codRegion" : codRegion,
				 "codArea" : codArea,
				 "codEnsena" : codEnsena,
				 "codNegocio" : codNegocio,
				 "descripcion" : descripcion
				
			 }; 
		}
		
		return jsonObject;
	}
}

