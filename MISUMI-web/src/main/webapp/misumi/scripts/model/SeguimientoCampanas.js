/*
 * JAVASCRIPT CLASS: SeguimientoCampanas
 */

function SeguimientoCampanas(codCentro, campana, oferta, tipoOC, fechaInicioDDMMYYYY, fechaInicioPantalla, fechaFinDDMMYYYY, fechaFinPantalla,
			codArea, descArea, codSeccion, descSeccion,
			codCategoria, descCategoria, codSubcategoria, descSubcategoria, codSegmento, descSegmento, codArt, descCodArt) {
	// Atributos
	this.codCentro = codCentro;
	this.campana = campana;
	this.oferta = oferta;
	this.tipoOC = tipoOC;
	this.fechaInicioDDMMYYYY = fechaInicioDDMMYYYY;
	this.fechaInicioPantalla = fechaInicioPantalla;
	this.fechaFinDDMMYYYY = fechaFinDDMMYYYY;
	this.fechaFinPantalla = fechaFinPantalla;
	this.codArea = codArea;
	this.descArea = descArea;
	this.codSeccion = codSeccion;
	this.descSeccion = descSeccion;
	this.codCategoria = codCategoria;
	this.descCategoria = descCategoria;
	this.codSubcategoria = codSubcategoria;
	this.descSubcategoria = descSubcategoria;
	this.codSegmento = codSegmento;
	this.descSegmento = descSegmento;
	this.codArt = codArt;
	this.descCodArt = descCodArt;
	
	//Metodos	
	this.prepareToJsonObject = function prepareToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codCentro" : codCentro,
				 "campana" : campana,
				 "oferta" : oferta,
				 "tipoOC" : tipoOC,
				 "fechaInicioDDMMYYYY" : fechaInicioDDMMYYYY,
				 "fechaInicioPantalla" : fechaInicioPantalla,
				 "fechaFinDDMMYYYY" : fechaFinDDMMYYYY,
				 "fechaFinPantalla" : fechaFinPantalla,
				 "codArea": codArea,
				 "descArea" : descArea,
				 "codSeccion" : codSeccion,
				 "descSeccion" : descSeccion,
				 "codCategoria" : codCategoria,
				 "descCategoria" : descCategoria,
				 "codSubcategoria" : codSubcategoria,
				 "descSubcategoria" : descSubcategoria,
				 "codSegmento" : codSegmento,
				 "descSegmento" : descSegmento,
				 "codArt" : codArt,
				 "descCodArt" : descCodArt
			}; 
		}
		return jsonObject;
	}
}

