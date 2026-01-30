/*
 * JAVASCRIPT CLASS: MotivoBloqueo
 */
function MotivoBloqueo (codArticulo, codTpBloqueo, fecIniDDMMYYYY, fecha2DDMMYYYY, fecha3DDMMYYYY, fecha4DDMMYYYY, fecha5DDMMYYYY, fechaInPilDDMMYYYY, fecFinDDMMYYYY, clasePedido){
	// Atributos
	this.codArticulo = codArticulo;
	this.codTpBloqueo = codTpBloqueo;
	this.fecIniDDMMYYYY = fecIniDDMMYYYY;
	this.fecha2DDMMYYYY = fecha2DDMMYYYY;
	this.fecha3DDMMYYYY = fecha3DDMMYYYY;
	this.fecha4DDMMYYYY = fecha4DDMMYYYY;
	this.fecha5DDMMYYYY = fecha5DDMMYYYY;
	this.fechaInPilDDMMYYYY = fechaInPilDDMMYYYY;
	this.fecFinDDMMYYYY = fecFinDDMMYYYY;
	this.clasePedido = clasePedido;
	
	//Metodos	
	this.prepareToJsonObject = function prepareToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codArticulo" : codArticulo,
				 "codTpBloqueo" : codTpBloqueo,
				 "fecIniDDMMYYYY" : fecIniDDMMYYYY,
				 "fecha2DDMMYYYY" : fecha2DDMMYYYY,
				 "fecha3DDMMYYYY" : fecha3DDMMYYYY,
				 "fecha4DDMMYYYY" : fecha4DDMMYYYY,
				 "fecha5DDMMYYYY" : fecha5DDMMYYYY,
				 "fechaInPilDDMMYYYY" : fechaInPilDDMMYYYY,
				 "fecFinDDMMYYYY" : fecFinDDMMYYYY,
				 "clasePedido" : clasePedido
			 }; 
		}
		return jsonObject;
	}
}