/*
 * JAVASCRIPT CLASS: DevolucionPlataforma
 */
function DevolucionPlataforma (codArticulo, centro, codAbono, codRecogida){
	// Atributos
	this.codArticulo = codArticulo;
	this.centro = centro;
	this.codAbono = codAbono;
	this.codRecogida = codRecogida;
	
	//Metodos	
	this.prepareToJsonObject = function prepareToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codArticulo" : codArticulo,
				 "centro": centro,
				 "codAbono" : codAbono,
				 "codRecogida" : codRecogida
			 }; 
		}
		return jsonObject;
	}
}