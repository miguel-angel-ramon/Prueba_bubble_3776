/*
 * JAVASCRIPT CLASS: MovimientoStock
 */
function MovimientoStock (codCentro, codArt, stock, stockKgVal, stockPenReci, 
		   					entradas, salidas, salidasPromo, 
		   					salidasForz, ajusteConteo, regular, fechaGen, rotura){
	// Atributos
	this.codCentro = codCentro;
	this.codArt = codArt;
	this.stock = stock;
	this.stockKgVal = stockKgVal;
	this.stockPenReci = stockPenReci;
	this.entradas = entradas;
	this.salidas = salidas;
	this.salidasPromo = salidasPromo;
	this.salidasForz = salidasForz;
	this.ajusteConteo = ajusteConteo;
	this.regular = regular;
	this.fechaGen = fechaGen;
	this.rotura = rotura;
	
	//Metodos	
	this.prepareToJsonObject = function prepareToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codCentro" : codCentro,
				 "codArt" : codArt,
				 "stock": stock,
				 "stockKgVal" : stockKgVal,
				 "stockPenReci" : stockPenReci,
				 "entradas" : entradas,
				 "salidas" : salidas,
				 "salidasPromo" : salidasPromo,
				 "salidasForz" : salidasForz,
				 "ajusteConteo" : ajusteConteo,
				 "regular" : regular,
				 "fechaGen" : fechaGen,
				 "rotura" : rotura
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

