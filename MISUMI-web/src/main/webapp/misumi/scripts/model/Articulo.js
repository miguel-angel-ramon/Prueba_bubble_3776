/*
 * JAVASCRIPT CLASS: Centro
 */
function Articulo (centro, codArt){
	// Atributos
	this.centro = centro;
	this.codArt = codArt;
		
	//Metodos	
	this.prepareToJsonObjectArticulo = function prepareToJsonObjectArticulo(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "centro" : centro,
				 "codArt" : codArt
				
			 }; 
		}
		
		return jsonObject;
	}
}

