/*
 * JAVASCRIPT CLASS: PendientesRecibir
 */
function PendientesRecibir (codCentro, codArt, cantHoy, cantFutura){

	// Atributos
	this.codCentro = codCentro;
	this.codArt = codArt;
	this.cantHoy = cantHoy;
	this.cantFutura = cantFutura;
	
	//Metodos	
	this.prepareToJsonObject = function prepareToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codCentro" : codCentro,
				 "codArt" : codArt,
				 "cantHoy": cantHoy,
				 "cantFutura" : cantFutura
			 }; 
		}
		return jsonObject;
	}

	this.prepareToJsonObject_2 = function prepareToJsonObject_2(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codCentro" : codCentro,
				 "codArt" : codArt
			 }; 
		}
		return jsonObject;
	}

}

