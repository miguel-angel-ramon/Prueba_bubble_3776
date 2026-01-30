/*
 * JAVASCRIPT CLASS: GestionEuros
 */
function GestionEuros (codLoc,respetarIMC, precioCostoFinal, precioCostoFinalFinal){	
	// Atributos
	this.codLoc = codLoc;
	this.respetarIMC = respetarIMC;
	this.precioCostoFinal = precioCostoFinal;
	this.precioCostoFinalFinal = precioCostoFinalFinal;

	//Metodos	
	this.prepareGestionEurosToJsonObject = function prepareGestionEurosToJsonObject(){
		var jsonObject = null;		
		with (this) {
			jsonObject = {
					"codLoc" : codLoc,
					"respetarIMC" : respetarIMC,
					"precioCostoFinal" : precioCostoFinal,
					"precioCostoFinalFinal" : precioCostoFinalFinal					
			 }; 
		}		
		return jsonObject;
	}
}