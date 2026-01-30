/*
 * JAVASCRIPT CLASS: Planograma vigente
 */
function PlanogramaVigente (codCentro,codArt,capacidadMaxLineal,stockMinComerLineal,capacidadMaxLinealOrig,stockMinComerLinealOrig){		
	// Atributos
	this.codCentro = codCentro;
	this.codArt = codArt;
	this.capacidadMaxLineal = capacidadMaxLineal;
	this.stockMinComerLineal = stockMinComerLineal;
	this.capacidadMaxLinealOrig = capacidadMaxLinealOrig;
	this.stockMinComerLinealOrig = stockMinComerLinealOrig;
	
	//Metodos	
	this.prepareDevolucionToJsonObject = function prepareDevolucionToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {				
					"codCentro" : codCentro,
					"codArt" : codArt,
					"capacidadMaxLineal" : capacidadMaxLineal,
					"stockMinComerLineal" : stockMinComerLineal,
					"capacidadMaxLinealOrig" : capacidadMaxLinealOrig,
					"stockMinComerLinealOrig" : stockMinComerLinealOrig
			 }; 
		}	
		return jsonObject;
	}
}