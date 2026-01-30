function DetalladoPedidoModif(codArticulo, cantidad, cantidadOriginal, cantidadUltValida, estadoGrid, fechaPedido
							 , grupo1, grupo2, grupo3, grupo4, grupo5, stock, enCurso1, enCurso2, unidadesCaja, tipoDetallado
							 , descriptionArt, resultadoWS, estadoGrid, flgSIA, codCentro, codMapa
					){
	// Atributos
	this.codArticulo=codArticulo;
	this.cantidad=cantidad;
	this.cantidadOriginal=cantidadOriginal;
	this.cantidadUltValida=cantidadUltValida;
	this.estadoGrid=estadoGrid;
	this.grupo1=grupo1;
	this.grupo2=grupo2;
	this.grupo3=grupo3;
	this.grupo4=grupo4;
	this.grupo5=grupo5;
	this.fechaPedido=fechaPedido;
	this.stock=stock;
	this.enCurso1=enCurso1;
	this.enCurso2=enCurso2;
	this.unidadesCaja=unidadesCaja;

	this.tipoDetallado=tipoDetallado;
	this.descriptionArt=descriptionArt;
	this.resultadoWS=resultadoWS;
	this.estadoGrid=estadoGrid;
	this.flgSIA=flgSIA;
	this.codCentro=codCentro;
	this.codMapa=codMapa;

	//Metodos	
	this.prepareToJsonObjectDetalladoPedidoModif = function prepareToJsonObjectDetalladoPedidoModif(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = { "codArticulo" : codArticulo
						 , "cantidad": cantidad
						 , "cantidadOriginal": cantidadOriginal
						 , "cantidadUltValida": cantidadUltValida
						 , "estadoGrid":estadoGrid
						 , "grupo1": grupo1
						 , "grupo2":grupo2
						 , "grupo3":grupo3
						 , "grupo4": grupo4
						 , "grupo5":grupo5
						 , "fechaPedido": fechaPedido
						 , "stock":stock
						 , "enCurso1":enCurso1
						 , "enCurso2":enCurso2
						 , "unidadesCaja":unidadesCaja
						 , "tipoDetallado":tipoDetallado
						 , "estadoGrid" : estadoGrid
						 , "flgSIA" : flgSIA
						 , "codCentro" : codCentro
						 , "codMapa" : codMapa
			 }; 
		}
		
		return jsonObject;
	}
}

