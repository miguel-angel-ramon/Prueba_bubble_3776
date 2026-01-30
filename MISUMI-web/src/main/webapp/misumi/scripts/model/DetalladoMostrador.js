function DetalladoMostrador(seccion,categoria,subcategoria,segmento,codCentro,flgIncluirPropPed,listaModificados){
	// Atributos
	this.seccion=seccion;
	this.categoria=categoria;
	this.subcategoria=subcategoria;
	this.segmento=segmento;
	this.codCentro=codCentro;
	this.flgIncluirPropPed=flgIncluirPropPed;
	this.listaModificados=listaModificados;
		
	//Metodos	
	this.prepareToJsonObjectDetalladoMostrador = function prepareToJsonObjectDetalladoMostrador(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				"seccion" : seccion,
				"categoria" : categoria,
				"subcategoria" : subcategoria,
				"segmento" : segmento,
				"codCentro" : codCentro,
				"flgIncluirPropPed" : flgIncluirPropPed,
				"listaModificados" : listaModificados
			 }; 
		}
		
		return jsonObject;
	}
}

