/*
 * JAVASCRIPT CLASS: SeguimientoMiPedido
 */
function SeguimientoMiPedido(codCentro, fechaPedidoDDMMYYYY, fechaPedidoPantalla,
			codArea, descArea, codSeccion, descSeccion,
			codCategoria, descCategoria, nivel, codArt, mapa, descrMapa) {
	
	// Atributos
	this.codCentro = codCentro;
	this.fechaPedidoDDMMYYYY = fechaPedidoDDMMYYYY;
	this.fechaPedidoPantalla = fechaPedidoPantalla;
	this.codArea = codArea;
	this.descArea = descArea;
	this.codSeccion = codSeccion;
	this.descSeccion = descSeccion;
	this.codCategoria = codCategoria;
	this.descCategoria = descCategoria;
	this.nivel = nivel;
	this.codArt = codArt;
	this.mapa = (typeof mapa !== 'undefined') ?  mapa : null;
	this.descrMapa = (typeof descrMapa !== 'undefined') ?  descrMapa : null;
	//Metodos	
	this.prepareToJsonObject = function prepareToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codCentro" : codCentro,
				 "fechaPedidoDDMMYYYY" : fechaPedidoDDMMYYYY,
				 "fechaPedidoPantalla" : fechaPedidoPantalla,
				 "codArea": codArea,
				 "descArea" : descArea,
				 "codSeccion" : codSeccion,
				 "descSeccion" : descSeccion,
				 "codCategoria" : codCategoria,
				 "descCategoria" : descCategoria,
				 "nivel" : nivel,
				 "codArt" : codArt,
				 "mapa" : (typeof mapa !== 'undefined') ?  mapa : null,
				 "descrMapa" : (typeof descrMapa !== 'undefined') ?  descrMapa : null
			 }; 
		}
		return jsonObject;
	}
}


