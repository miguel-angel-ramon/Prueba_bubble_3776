/*
 * JAVASCRIPT CLASS: ReposicionGuardar
 */
function ReposicionGuardar (codLoc,codMac,tipoListado,modeloProveedor,codArt,
		descrColor,cantRepo,flgRevisada,flgSustituida,codError,descError){
	// Atributos
	this.codLoc = codLoc;
	this.codMac = codMac;
	this.tipoListado = tipoListado;
	this.modeloProveedor = modeloProveedor;
	this.codArt = codArt;
	this.descrColor = descrColor;
	this.cantRepo = cantRepo;
	this.flgRevisada = flgRevisada;
	this.flgSustituida = flgSustituida;
	this.codError = codError;
	this.descError = descError;
	
	//Metodos	
	this.prepareToJsonObject = function prepareToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				"codLoc" : codLoc,
				"codMac" : codMac,
				"tipoListado" : tipoListado,
				"modeloProveedor" : modeloProveedor,
				"codArt" : codArt,
				"descrColor" : descrColor,
				"cantRepo" : cantRepo,
				"flgRevisada" : flgRevisada,
				"flgSustituida" : flgSustituida,
				"codError" : codError,
				"descError" : descError
			 }; 
		}
		
		return jsonObject;
	}
}
