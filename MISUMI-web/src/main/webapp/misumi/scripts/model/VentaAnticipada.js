/*
 * JAVASCRIPT CLASS: VentaAnticipada
 */
function VentaAnticipada(codArt,cantidad,existe,flgEnvioAC,fechaGen){
	// Atributos
	this.codArt=codArt;
	this.cantidad=cantidad;
	this.existe=existe;
	this.flgEnvioAC=flgEnvioAC;
	this.fechaGen=fechaGen;
	
		
	//Metodos	
	this.prepareToJsonObject = function prepareToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codArt" : codArt,
				 "cantidad" : cantidad,
				 "existe" : existe,
				 "flgEnvioAC" : flgEnvioAC,
				 "fechaGen" : fechaGen
			 }; 
		}
		
		return jsonObject;
	};
}

