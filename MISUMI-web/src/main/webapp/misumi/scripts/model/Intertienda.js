/*
 * JAVASCRIPT CLASS: Intertienda
 */
function Intertienda (codReferencia, codCentro, descCentro, codRegion, descRegion, codZona, descZona, codProvincia, descProvincia, codEnsena, codArea, stock, ventaMedia, existeArticulo, descError){
	// Atributos
	this.codReferencia = codReferencia;
	this.codCentro = codCentro;
	this.descCentro = descCentro;
	this.codRegion = codRegion;
	this.descRegion = descRegion;
	this.codZona = codZona;
	this.descZona = descZona;
	this.codProvincia = codProvincia;
	this.descProvincia = descProvincia;
	this.codEnsena = codEnsena;
	this.codArea = codArea;
	this.stock = stock;
	this.ventaMedia = ventaMedia;
	this.existeArticulo = existeArticulo;
	this.descError = descError;
		
	//Metodos	
	this.prepareToJsonObject = function prepareToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codReferencia" : codReferencia,
				 "codCentro" : codCentro,
				 "descCentro" : descCentro,
				 "codRegion" : codRegion,
				 "descRegion" : descRegion,
				 "codZona" : codZona,
				 "descZona" : descZona,
				 "codProvincia" : codProvincia,
				 "descProvincia" : descProvincia,
				 "codEnsena" : codEnsena,
				 "codArea" : codArea,
				 "stock" : stock,
				 "ventaMedia" : ventaMedia,
				 "existeArticulo" : existeArticulo,
				 "descError" : descError
			 }; 
		}
		
		return jsonObject;
	}
}

