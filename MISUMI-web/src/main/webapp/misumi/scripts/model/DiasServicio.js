/*
 * JAVASCRIPT CLASS: DiasServicio
 */
	function DiasServicio(codCentro, codArt,
			codN1, codN2, codN3, fechaPantalla,uuid,clasePedido) {
		
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.codN1 = codN1;
		this.codN2 = codN2;
		this.codN3 = codN3;
		this.fechaPantalla = fechaPantalla;
		this.uuid = uuid;
		this.clasePedido = clasePedido;
		
	//Metodos	
	this.prepareDiasServicioToJsonObject = function prepareDiasServicioToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
					"codCentro" : codCentro,
					"codArt" : codArt,
					"codN1" : codN1,
					"codN2" : codN2,
					"codN3" : codN3,
					"fechaPantalla" : fechaPantalla,
					"uuid" : uuid,
					"clasePedido" : clasePedido
					
			 }; 
		}
		
		return jsonObject;
	}
}

