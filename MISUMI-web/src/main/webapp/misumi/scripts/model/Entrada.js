/*
 * JAVASCRIPT CLASS: Entrada
 */
function Entrada (codLoc,codCabPedido,codProvGen,codProvTrab,codAlbProv,tipoRecepcion,lstModificados,fechaEntrada,numIncidencia){
	
	// Atributos
	this.codLoc = codLoc;
	this.codCabPedido = codCabPedido;
	this.codProvGen = codProvGen;
	this.codProvTrab = codProvTrab;
	this.codAlbProv = codAlbProv;
	this.tipoRecepcion = tipoRecepcion;
	this.fechaEntrada = fechaEntrada;
	this.numIncidencia = numIncidencia;
	
	//Líneas Modificadas
	this.lstModificados = lstModificados;
	
		
	//Metodos	
	this.prepareDevolucionToJsonObject = function prepareEntradaToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {	
					"codLoc" : codLoc,
					"codCabPedido" : codCabPedido,
					"codProvGen" : codProvGen,
					"codProvTrab" : codProvTrab,
					"codAlbProv" : codAlbProv,
					"tipoRecepcion" : tipoRecepcion,
					"lstModificados" : lstModificados,
					"fechaEntrada" : fechaEntrada,
					"numIncidencia" : numIncidencia
			 }; 
		}	
		return jsonObject;
	}
}