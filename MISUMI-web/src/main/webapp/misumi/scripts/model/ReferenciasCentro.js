/*
 * JAVASCRIPT CLASS: ReferenciasCentro
 */
function ReferenciasCentro (codArt, codCentro, origenPantalla,flgCapacidad,flgFacing,flgFacingCapacidad,codArtCaprabo){
	// Atributos
	this.codArt = codArt;
	this.codCentro = codCentro;
	this.origenPantalla = origenPantalla;
	this.flgCapacidad = flgCapacidad;
	this.flgFacing = flgFacing;
	this.flgFacingCapacidad = flgFacingCapacidad;
	this.codArtCaprabo = codArtCaprabo;
	
	//Metodos	
	this.prepareToJsonObject = function prepareToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codArt" : codArt,
				 "codCentro": codCentro,
				 "origenPantalla": origenPantalla,
				 "codArtCaprabo" : codArtCaprabo
			 }; 
		}
		return jsonObject;
	}
}

