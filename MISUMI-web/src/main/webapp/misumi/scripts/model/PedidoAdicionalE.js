/*
 * JAVASCRIPT CLASS: PedidoAdicionalE
 */
function PedidoAdicionalE (codCentro, grupo1, grupo2, grupo3, codArticulo, descriptionArt, clasePedido, fecEntrega, unidadesPedidas,
		uniCajaServ, cajas, excluir, listadoSeleccionados, identificador, mca, stock, consultaAlmacenada, listaFiltroClasePedido, identificadorSIA){
	
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
	this.fecEntrega=fecEntrega;
	this.unidadesPedidas=unidadesPedidas;
	this.uniCajaServ=uniCajaServ;
	this.cajas=cajas;
	this.excluir=excluir;
	this.listadoSeleccionados=listadoSeleccionados;
	this.identificador=identificador;
	this.mca=mca;
	this.stock=stock;
	this.consultaAlmacenada = consultaAlmacenada;
	this.listaFiltroClasePedido = listaFiltroClasePedido;
	this.identificadorSIA=identificadorSIA;
	
	
	//Metodos	
	this.preparePedidoAdicionalEToJsonObject = function preparePedidoAdicionalEToJsonObject(){
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
				 "fecEntrega" : fecEntrega,
				 "unidadesPedidas" : unidadesPedidas,
				 "uniCajaServ" : uniCajaServ,
				 "cajas" : cajas,
				 "excluir" : excluir,
				 "listadoSeleccionados" : listadoSeleccionados,
				 "identificador" : identificador,
				 "mca" : mca,
				 "stock" : stock,
				 "consultaAlmacenada" : consultaAlmacenada,
				 "listaFiltroClasePedido" : listaFiltroClasePedido,
				 "identificadorSIA" : identificadorSIA
			 }; 
		}
		
		return jsonObject;
	}
}

