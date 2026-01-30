/*
 * JAVASCRIPT CLASS: PedidoAdicionalMO
 */
function PedidoAdicionalMO (codCentro, grupo1, grupo2, grupo3, codArticulo, descriptionArt, clasePedido, fechaInicio, fechaFin, capMax,
		capMin, cantidad1, cantidad2, cantidad3, uniCajaServ, oferta, listadoSeleccionados, esPlanograma, identificador, mca, stock, listaFiltroClasePedido, noGestionaPbl,descPeriodo,espacioPromo, identificadorSIA){
	
	
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
	this.fechaFin=fechaFin;
	this.capMax=capMax;
	this.capMin=capMin;
	this.cantidad1=cantidad1;
	this.cantidad2=cantidad2;
	this.cantidad3=cantidad3;
	this.uniCajaServ=uniCajaServ;
	this.oferta=oferta;
	this.listadoSeleccionados=listadoSeleccionados;
	this.esPlanograma=esPlanograma;
	this.identificador=identificador;
	this.mca=mca;
	this.stock=stock;
	this.listaFiltroClasePedido = listaFiltroClasePedido;
	this.noGestionaPbl = noGestionaPbl;
	this.descPeriodo=descPeriodo;
	this.espacioPromo=espacioPromo;
	this.identificadorSIA = identificadorSIA;
	this.identificadorVegalsa = "";
	
	//Metodos	
	this.preparePedidoAdicionalMOToJsonObject = function preparePedidoAdicionalMOToJsonObject(){
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
				 "fechaFin" : fechaFin,
				 "capMax" : capMax,
				 "capMin" : capMin,
				 "cantidad1" : cantidad1,
				 "cantidad2" : cantidad2,
				 "cantidad3" : cantidad3,
				 "uniCajaServ" : uniCajaServ,
				 "oferta" : oferta,
				 "listadoSeleccionados" : listadoSeleccionados,
				 "esPlanograma" : esPlanograma,
				 "identificador" : identificador,
				 "mca" : mca,
				 "stock" : stock,
				 "listaFiltroClasePedido" : listaFiltroClasePedido,
				 "noGestionaPbl" : noGestionaPbl,
				 "descPeriodo" : descPeriodo,
				 "espacioPromo" : espacioPromo,
				 "identificadorSIA" : identificadorSIA,
				 "identificadorVegalsa" : identificadorVegalsa
			 }; 
		}
		
		return jsonObject;
	}
}

