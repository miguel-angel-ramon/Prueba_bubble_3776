function DetalladoPedido(grupo2, grupo3, codArticulo, listaModificados, id, codCentro, flgIncluirPropPed, flgOferta, respetarIMC, precioCostoFinal, precioCostoFinalFinal, rotacion, grupo1, grupo4, grupo5, codMapa, motivoPedido, filtroMapa){
	// Atributos
	
	this.grupo2=grupo2;
	this.grupo3=grupo3;
	this.codArticulo=codArticulo;
	this.listaModificados=listaModificados;
	this.id=id;  //id para enlazar con las referencias de un lote.
	this.codCentro=codCentro;
	this.flgIncluirPropPed = flgIncluirPropPed;
	this.flgOferta = flgOferta;
	this.respetarIMC = respetarIMC;
	this.precioCostoFinal = precioCostoFinal;
	this.precioCostoFinalFinal = precioCostoFinalFinal;
	this.rotacion = rotacion;
	this.grupo1 = grupo1;
	this.grupo4=grupo4;
	this.grupo5=grupo5;
	this.codMapa = codMapa;
	this.motivoPedido = motivoPedido;
	this.filtroMapa = filtroMapa;
		
	//Metodos	
	this.prepareToJsonObjectDetalladoPedido = function prepareToJsonObjectDetalladoPedido(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "grupo2" : grupo2,
				 "grupo3" : grupo3,
				 "codArticulo" : codArticulo,
				 "listaModificados":listaModificados,
				 "id" : id,
				 "codCentro" : codCentro,
				 "flgIncluirPropPed" : flgIncluirPropPed,
				 "flgOferta" : flgOferta,
				 "respetarIMC" : respetarIMC,
				 "precioCostoFinal" : precioCostoFinal,
				 "precioCostoFinalFinal" : precioCostoFinalFinal,
				 "rotacion" : rotacion,
				 "grupo1" : grupo1,
				 "grupo4" : grupo4,
				 "grupo5" : grupo5,
				 "codMapa": codMapa,
				 "motivoPedido": motivoPedido,
				 "filtroMapa": filtroMapa
			 }; 
		}
		
		return jsonObject;
	}
}

