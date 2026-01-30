/*
 * JAVASCRIPT CLASS: Centro
 */
function Centro (codCentro, descripCentro, negocio, flgCapacidad, codEnsena, codArea, codRegion, codZona, descripZona, provincia){
	// Atributos
	this.codCentro = codCentro;
	this.descripCentro = descripCentro;
	this.negocio = negocio;
	this.flgCapacidad = flgCapacidad;
	this.codEnsena = codEnsena;
	this.codArea = codArea;
	this.codRegion = codRegion;
	this.codZona = codZona;
	this.descripZona = descripZona;
	this.provincia = provincia;
		
	//Metodos	
	this.prepareToJsonObject = function prepareToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codCentro" : codCentro,
				 "descripCentro" : descripCentro,
				 "negocio" : negocio,
				 "flgCapacidad" : flgCapacidad,
				 "codEnsena" : codEnsena,
				 "codArea" : codArea,
				 "codRegion" : codRegion,
				 "codZona" : codZona,
				 "descripZona" : descripZona,
				 "provincia" : provincia
			 }; 
		}
		
		return jsonObject;
	}
}

