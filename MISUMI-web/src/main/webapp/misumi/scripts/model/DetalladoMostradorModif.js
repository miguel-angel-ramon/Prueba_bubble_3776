function DetalladoPedidoModif( codArticulo
							 , propuestaPedido
							 , cantidadOriginal
							 , cantidadUltValida
							 , estadoGrid
							 , fechaPedido
							 , grupo1
							 , grupo2
							 , grupo3
							 , grupo4
							 , grupo5
							 , pendienteRecibirManana
							 , unidadesCaja
							 , descriptionArt
//							 , resultadoWS
							 , estadoGrid
					){
	// Atributos
	this.codArticulo=codArticulo;
	this.propuestaPedido=propuestaPedido;
	this.cantidadOriginal=cantidadOriginal;
	this.cantidadUltValida=cantidadUltValida;
	this.estadoGrid=estadoGrid;
	this.grupo1=grupo1;
	this.grupo2=grupo2;
	this.grupo3=grupo3;
	this.grupo4=grupo4;
	this.grupo5=grupo5;
	this.fechaPedido=fechaPedido;
	this.pendienteRecibirManana=pendienteRecibirManana;
	this.unidadesCaja=unidadesCaja;

	this.descriptionArt=descriptionArt;
//	this.resultadoWS=resultadoWS;
	this.estadoGrid=estadoGrid;
		
	//Metodos	
	this.prepareToJsonObjectDetalladoMostradorModif = function prepareToJsonObjectDetalladoMostradorModif(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {"codArticulo":codArticulo
						 ,"propuestaPedido":propuestaPedido
						 ,"cantidadOriginal":cantidadOriginal
						 ,"cantidadUltValida":cantidadUltValida
						 ,"estadoGrid":estadoGrid
						 ,"grupo1":grupo1
						 ,"grupo2":grupo2
						 ,"grupo3":grupo3
						 ,"grupo4":grupo4
						 ,"grupo5":grupo5
						 ,"fechaPedido":fechaPedido
						 ,"pendienteRecibirManana":pendienteRecibirManana
						 ,"unidadesCaja":unidadesCaja
						 ,"estadoGrid":estadoGrid
			 }; 
		}
		
		return jsonObject;
	}
}

