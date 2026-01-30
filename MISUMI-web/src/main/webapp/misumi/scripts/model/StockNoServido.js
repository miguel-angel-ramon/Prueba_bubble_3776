/*
 * JAVASCRIPT CLASS: StockNoServido
 */
function StockNoServido (codCentro, codArt, fechaNsr, uniNoServ, 
						 motivo, motivoDes, codPlat, codPlatDes){
	// Atributos
	this.codCentro = codCentro;
	this.codArt = codArt;
	this.fechaNsr = fechaNsr;
	this.uniNoServ = uniNoServ;
	this.motivo = motivo;
	this.motivoDes = motivoDes;
	this.codPlat = codPlat;
	this.codPlatDes = codPlatDes;
    
	//Metodos	
	this.prepareToJsonObject = function prepareToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codCentro" : codCentro,
				 "codArt" : codArt,
				 "fechaNsr": fechaNsr,
				 "uniNoServ" : uniNoServ,
				 "motivo" : motivo,
				 "motivoDes" : motivoDes,
				 "codPlat" : codPlat,
				 "codPlatDes" : codPlatDes
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

