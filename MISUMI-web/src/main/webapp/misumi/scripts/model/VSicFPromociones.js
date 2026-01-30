/*
 * JAVASCRIPT CLASS: VSicFPromociones
 */
function VSicFPromociones (ejerPromocion, codPromocion, codLoc, nelCodN1, nelCodN2, nelCodN3, neaCodN1, neaCodN2, neaCodN3, cabGondola){
	// Atributos
	this.ejerPromocion=ejerPromocion;
	this.codPromocion=codPromocion;
	this.codLoc=codLoc;
	this.nelCodN1=nelCodN1;
	this.nelCodN2=nelCodN2;
	this.nelCodN3=nelCodN3;
	this.neaCodN1=neaCodN1;
	this.neaCodN2=neaCodN2;	
	this.neaCodN3=neaCodN3;
	this.cabGondola=cabGondola;
		
	//Metodos	
	this.prepareToJsonObject = function prepareToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				"ejerPromocion" : ejerPromocion,
				 "codPromocion" : codPromocion,	
				 "codLoc" : codLoc,
				 "nelCodN1" : nelCodN1,
				 "nelCodN2" : nelCodN2,
				 "nelCodN3" : nelCodN3,
				 "neaCodN1" : neaCodN1,
				 "neaCodN2" : neaCodN2,
				 "neaCodN3" : neaCodN3,
				 "cabGondola" : cabGondola
			 }; 
		}
		
		return jsonObject;
	}
}

