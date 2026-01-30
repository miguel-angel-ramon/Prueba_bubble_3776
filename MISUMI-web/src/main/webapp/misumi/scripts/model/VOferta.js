/*
 * JAVASCRIPT CLASS: VOferta
 */
function VOferta (codCentro, codArt, anoOferta,numOferta, tipoOferta, fechaIni, fechaFin, doftip){
	// Atributos
	
	this.codCentro=codCentro;
	this.codArt=codArt;
	this.anoOferta=anoOferta;
	this.numOferta=numOferta;
	this.tipoOferta=tipoOferta;
	this.fechaIni=fechaIni;
	this.fechaFin=fechaFin;
	this.doftip=doftip;	
		
	//Metodos	
	this.prepareToJsonObject = function prepareToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codCentro" : codCentro,
				 "codArt" : codArt,
				 "anoOferta" : anoOferta,
				 "numOferta" : numOferta,
				 "tipoOferta" : tipoOferta,
				 "fechaIni" : fechaIni,
				 "fechaFin" : fechaFin,
				 "doftip" : doftip
			 }; 
		}
		
		return jsonObject;
	}
}

