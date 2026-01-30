/*
 * JAVASCRIPT CLASS: MotivoTengoMuchoPoco
 */
function MotivoTengoMuchoPoco (codCentro, tipo, codArticulo, stockBajo, stockAlto, descripcion, stock){
	// Atributos
	this.codCentro = codCentro;
	this.tipo = tipo;
	this.codArticulo = codArticulo;
	this.stockBajo = stockBajo;
	this.stockAlto = stockAlto;
	this.descripcion = descripcion;
	this.stock = stock;
	
	//Metodos	
	this.prepareToJsonObject = function prepareToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codCentro" : codCentro,
				 "tipo" : tipo,
				 "codArticulo" : codArticulo,
				 "stockBajo" : stockBajo,
				 "stockAlto" : stockAlto,
				 "descripcion" : descripcion,
				 "stock" : stock
			 }; 
		}
		return jsonObject;
	}
}