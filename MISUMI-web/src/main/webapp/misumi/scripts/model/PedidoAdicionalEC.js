/*
 * JAVASCRIPT CLASS: PedidoAdicionalEC
 */
function PedidoAdicionalEC (localizador, codLoc, contactoCentro, nombreCliente, apellidoCliente, telefonoCliente, 
		tipoEncargo, fechaVenta, fechaVentaModificada, codArtFormlog, denomArticulo, especificacion, pesoDesde, pesoHasta, 
		confirmarPrecio, cantEncargo, estado, observacionesMisumi, codigoPedidoInterno, tipoAprov, primeraFechaEntrega, listadoSeleccionados, flgEspec){
	
	// Atributos
	this.localizador=localizador;
	this.codLoc=codLoc;
	this.contactoCentro=contactoCentro;
	this.nombreCliente=nombreCliente;
	this.apellidoCliente=apellidoCliente;
	this.telefonoCliente=telefonoCliente;
	this.tipoEncargo = tipoEncargo;
	this.fechaVenta=fechaVenta;
	this.fechaVentaModificada=fechaVentaModificada;
	this.codArtFormlog=codArtFormlog;
	this.denomArticulo=denomArticulo;
	this.especificacion=especificacion;
	this.pesoDesde=pesoDesde;
	this.pesoHasta=pesoHasta;
	this.confirmarPrecio=confirmarPrecio;
	this.cantEncargo=cantEncargo;
	this.estado=estado;
	this.observacionesMisumi=observacionesMisumi;
	this.codigoPedidoInterno=codigoPedidoInterno;
	this.tipoAprov=tipoAprov;
	this.primeraFechaEntrega = primeraFechaEntrega;
	this.listadoSeleccionados=listadoSeleccionados;
	this.flgEspec = flgEspec;
	
	//Metodos	
	this.preparePedidoAdicionalECToJsonObject = function preparePedidoAdicionalECToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "localizador" : localizador,
				 "codLoc": codLoc,
				 "contactoCentro" : contactoCentro,
				 "nombreCliente" : nombreCliente,
				 "apellidoCliente" : apellidoCliente,
				 "telefonoCliente" : telefonoCliente,
				 "tipoEncargo" : tipoEncargo,
				 "fechaVenta" : fechaVenta,
				 "fechaVentaModificada" : fechaVentaModificada,
				 "codArtFormlog" : codArtFormlog,
				 "denomArticulo" : denomArticulo,
				 "especificacion" : especificacion,
				 "pesoDesde" : pesoDesde,
				 "pesoHasta" : pesoHasta,
				 "confirmarPrecio" : confirmarPrecio,
				 "cantEncargo" : cantEncargo,
				 "estado" : estado,
				 "observacionesMisumi" : observacionesMisumi,
				 "codigoPedidoInterno" : codigoPedidoInterno,
				 "tipoAprov" : tipoAprov,
				 "primeraFechaEntrega" : primeraFechaEntrega,
				 "listadoSeleccionados" : listadoSeleccionados,
				 "flgEspec" : flgEspec
			 }; 
		}
		
		return jsonObject;
	}
}

