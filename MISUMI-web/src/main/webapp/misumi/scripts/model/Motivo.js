/*
 * JAVASCRIPT CLASS: Motivo
 */
function Motivo (mapaHoy, pedir, codArt, codCentro, origenPantalla,codArtSustituidaPorEroski){
	// Atributos
	this.mapaHoy = mapaHoy;
	this.pedir = pedir;
	this.codArt = codArt;
	this.codCentro = codCentro;
	this.origenPantalla = origenPantalla;
	this.codArtSustituidaPorEroski = codArtSustituidaPorEroski;
	
	//Metodos	
	this.prepareToJsonObject = function prepareToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "mapaHoy" : mapaHoy,
				 "pedir": pedir,
				 "codArt": codArt,
				 "codCentro": codCentro,
				 "origenPantalla": origenPantalla,
				 "codArtSustituidaPorEroski" : codArtSustituidaPorEroski
			 }; 
		}
		return jsonObject;
	}
}

