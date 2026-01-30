/*
 * JAVASCRIPT CLASS: VSurtidoTienda
 */
function VSurtidoTienda (codCentro, codArt, uniCajaServ, tipoAprov, marcaMaestroCentro, catalogo, pedir, tpGama){
	// Atributos
	
	this.codCentro=codCentro;
	this.codArt=codArt;
	this.uniCajaServ=uniCajaServ;
	this.tipoAprov=tipoAprov;
	this.marcaMaestroCentro=marcaMaestroCentro;
	this.catalogo=catalogo;
	this.pedir=pedir;
	this.tpGama=tpGama;	
		
	//Metodos	
	this.prepareToJsonObject = function prepareToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codCentro" : codCentro,
				 "codArt" : codArt,
				 "uniCajaServ" : uniCajaServ,
				 "tipoAprov" : tipoAprov,
				 "marcaMaestroCentro" : marcaMaestroCentro,
				 "catalogo" : catalogo,
				 "pedir" : pedir,
				 "tpGama" : tpGama
			 }; 
		}
		
		return jsonObject;
	}
}

