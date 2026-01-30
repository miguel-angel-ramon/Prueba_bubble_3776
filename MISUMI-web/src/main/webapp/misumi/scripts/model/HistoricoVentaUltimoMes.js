/*
 * JAVASCRIPT CLASS: HistoricoVentaUltimoMes
 */
function HistoricoVentaUltimoMes (codArticulo, codLoc, fechaVentaDDMMYYYY){
	// Atributos
	this.codArticulo = codArticulo;
	this.codLoc = codLoc;
	this.fechaVentaDDMMYYYY = fechaVentaDDMMYYYY;
	
	//Metodos	
	this.prepareToJsonObject = function prepareToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codArticulo" : codArticulo,
				 "codLoc": codLoc,
				 "fechaVentaDDMMYYYY" : fechaVentaDDMMYYYY
			 }; 
		}
		return jsonObject;
	}
}

