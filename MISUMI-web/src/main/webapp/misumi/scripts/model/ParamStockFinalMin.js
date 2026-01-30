/*
 * JAVASCRIPT CLASS: Centro
 */
function ParamStockFinalMin (codLoc, codN1,codN2,codN3,codN4,codN5){
	
	// Atributos
	this.codLoc = codLoc;
	this.codN1 = codN1;
	this.codN2 = codN2;
	this.codN3 = codN3;
	this.codN4 = codN4;
	this.codN5 = codN5;
	
		
	//Metodos	
	this.prepareToJsonObjectArticulo = function prepareToJsonObjectArticulo(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codLoc" : codLoc,
				 "codN1" : codN1,
				 "codN2" : codN2,
				 "codN3" : codN3,
				 "codN4" : codN4,
				 "codN5" : codN5
			 }; 
		}
		
		return jsonObject;
	}
}

