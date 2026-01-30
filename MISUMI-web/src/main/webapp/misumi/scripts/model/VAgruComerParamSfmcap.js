/*
 * JAVASCRIPT CLASS: VAgruComerParamSfmcap
 */
function VAgruComerParamSfmcap (codCentro, nivel, grupo1, grupo2, grupo3, grupo4, grupo5, descripcion, flgStockFinal, flgCapacidad){
	// Atributos
	this.codCentro=codCentro;
	this.nivel=nivel;
	this.grupo1=grupo1;
	this.grupo2=grupo2;
	this.grupo3=grupo3;
	this.grupo4=grupo4;
	this.grupo5=grupo5;
	this.descripcion=descripcion;
	this.flgStockFinal=flgStockFinal;
	this.flgCapacidad=flgCapacidad;
		
	//Metodos	
	this.prepareToJsonObject = function prepareToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codCentro" : codCentro,	
				 "nivel" : nivel,
				 "grupo1" : grupo1,
				 "grupo2" : grupo2,
				 "grupo3" : grupo3,
				 "grupo4" : grupo4,
				 "grupo5" : grupo5,
				 "descripcion" : descripcion,
				 "flgStockFinal" : flgStockFinal,
				 "flgCapacidad" : flgCapacidad
			 }; 
		}
		
		return jsonObject;
	}
}

