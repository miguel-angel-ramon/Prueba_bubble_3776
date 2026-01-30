/*
 * JAVASCRIPT CLASS: QueryReferenciasByDescr
 */
function QueryReferenciasByDescr(codCentro, codSeccion, descripcion, activo, altaCatalogo, codArticulo, modelo_proveedor,alfaNumerico,ean,talla,color,paginaConsulta,flgNivelModProv,codCategoria,codCentroRelacionado,numOrden){

	// Atributos
	this.codCentro = codCentro;
	this.codSeccion = codSeccion;
	this.descripcion = descripcion;
	this.activo = activo;
	this.altaCatalogo = altaCatalogo;
	this.codArticulo = codArticulo;
	this.modelo_proveedor = modelo_proveedor;
	this.alfaNumerico = alfaNumerico;
	this.ean = ean;
	this.talla = talla;
	this.color = color;
	this.paginaConsulta=paginaConsulta;
	this.flgNivelModProv = flgNivelModProv;
	this.codCategoria = codCategoria;
	this.codCentroRelacionado = codCentroRelacionado;
	this.numOrden = numOrden;
	
	//Metodos	
	this.prepareToJsonObject = function prepareToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codCentro" : codCentro,
				 "codSeccion" : codSeccion,
				 "descripcion" : descripcion,
				 "activo": activo,
				 "altaCatalogo" : altaCatalogo,
				 "codArticulo" : codArticulo,
				 "modelo_proveedor" : modelo_proveedor,
				 "alfaNumerico": alfaNumerico,
				 "ean": ean,
				 "talla": talla,
				 "color": color,
				 "paginaConsulta":paginaConsulta,
				 "flgNivelModProv":flgNivelModProv,
				 "codCategoria":codCategoria,
				 "codCentroRelacionado":codCentroRelacionado,
				 "numOrden":numOrden
			 }; 
		}
		return jsonObject;
	}

	

}

