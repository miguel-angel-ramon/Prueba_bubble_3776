/*
 * JAVASCRIPT CLASS: VAgruComerRef
 */
function VAgruComerRef (nivel, grupo1, grupo2, grupo3, grupo4, grupo5, descripcion){
	// Atributos
	this.nivel=nivel;
	this.grupo1=grupo1;
	this.grupo2=grupo2;
	this.grupo3=grupo3;
	this.grupo4=grupo4;
	this.grupo5=grupo5;
	this.descripcion=descripcion;	
		
	//Metodos	
	this.prepareToJsonObject = function prepareToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "nivel" : nivel,
				 "grupo1" : grupo1,
				 "grupo2" : grupo2,
				 "grupo3" : grupo3,
				 "grupo4" : grupo4,
				 "grupo5" : grupo5,
				 "descripcion" : descripcion
				 
			 }; 
		}
		
		return jsonObject;
	}
}

