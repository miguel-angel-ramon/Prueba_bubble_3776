function DetallePedidoLista (datos){
	// Atributos
	this.datos=datos;	
		
	//Metodos	
	this.prepareToJsonObjectDetallePedidoLista = function prepareToJsonObjectDetallePedidoLista(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "datos":datos
			 }; 
		}		
		return jsonObject;
	}
}

