/*
 * JAVASCRIPT CLASS: PedidoAdicionalVC
 */
function PedidoAdicionalVC (codCentro, grupo1, grupo2, grupo3, codArticulo, descriptionArt, clasePedido, fechaInicio, fecha2, fecha3, fecha4, capMax,
		capMin, cantidad1, cantidad2, cantidad3, cantMin, cantMax, uniCajaServ, oferta, listadoSeleccionados, identificador, mca, listaFiltroClasePedido, descOferta){
	
	// Atributos
	this.codCentro=codCentro;
	
	if (grupo1!="null"){
		this.grupo1=grupo1;
	}else{
		this.grupo1=null;
	}
	if (grupo2!="null"){
		this.grupo2=grupo2;
	}else{
		this.grupo2=null;
	}
	if (grupo3!="null"){
		this.grupo3=grupo3;
	}else{
		this.grupo3=null;
	}
	this.codArticulo=codArticulo;
	this.descriptionArt=descriptionArt;
	this.clasePedido=clasePedido;
	this.fechaInicio=fechaInicio;
	this.fecha2=fecha2;
	this.fecha3=fecha3;
	this.fecha4=fecha4;
	this.capMax=capMax;
	this.capMin=capMin;
	this.cantidad1=cantidad1;
	this.cantidad2=cantidad2;
	this.cantidad3=cantidad3;
	this.cantMin=cantMin;
	this.cantMax=cantMax;
	this.uniCajaServ=uniCajaServ;
	this.oferta=oferta;
	this.listadoSeleccionados=listadoSeleccionados;
	this.identificador=identificador;
	this.mca=mca;
	this.listaFiltroClasePedido = listaFiltroClasePedido;
	this.descOferta = descOferta;
	
	
	//Metodos	
	this.preparePedidoAdicionalVCToJsonObject = function preparePedidoAdicionalVCToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codCentro" : codCentro,
				 "grupo1": grupo1,
				 "grupo2" : grupo2,
				 "grupo3" : grupo3,
				 "codArticulo" : codArticulo,
				 "descriptionArt" : descriptionArt,
				 "clasePedido" : clasePedido,
				 "fechaInicio" : fechaInicio,
				 "fecha2" : fecha2,
				 "fecha3" : fecha3,
				 "fecha4" : fecha4,
				 "capMax" : capMax,
				 "capMin" : capMin,
				 "cantidad1" : cantidad1,
				 "cantidad2" : cantidad2,
				 "cantidad3" : cantidad3,
				 "cantMin" : cantMin,
				 "cantMax" : cantMax,
				 "uniCajaServ" : uniCajaServ,
				 "oferta" : oferta,
				 "listadoSeleccionados" : listadoSeleccionados,
				 "identificador" : identificador,
				 "mca" : mca,
				 "listaFiltroClasePedido" : listaFiltroClasePedido,
				 "descOferta" : descOferta
			 }; 
		}
		
		return jsonObject;
	}
}

