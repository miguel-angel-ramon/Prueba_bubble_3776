/*
 * JAVASCRIPT CLASS: DevolucionLinea
 */
function DevolucionLinea (codArticulo,denominacion,marca,seccion,provrGen,provrTrabajo,denomProveedor,familia,formatoDevuelto,formato,tipoFormato,pasillo,estructuraComercial,uc,stockActual,stockTienda,stockDevolver,stockDevuelto,cantAbonada,flgContinuidad,lote,nLote,caducidad,nCaducidad,descAbonoError,bulto,ubicacion,tipoReferencia,estadoLin,codError,descError,codTpCa,cantidadMaximaPermitida,stockDevueltoBandejas,flgBandejas){
	
	// Atributos
	this.codArticulo = codArticulo;
	this.denominacion = denominacion;
	this.marca = marca;
	this.seccion = seccion;
	this.provrGen = provrGen;
	this.provrTrabajo = provrTrabajo;
	this.denomProveedor = denomProveedor;
	this.familia = familia;
	this.formatoDevuelto = formatoDevuelto;
	this.formato = formato;
	this.tipoFormato = tipoFormato;
	this.pasillo = pasillo;
	this.estructuraComercial = estructuraComercial;
	this.uc = uc;
	this.stockActual = stockActual;
	this.stockTienda = stockTienda;
	this.stockDevolver = stockDevolver;
	this.stockDevuelto = stockDevuelto;
	this.cantAbonada = cantAbonada;
	this.flgContinuidad = flgContinuidad;
	this.lote = lote;
	this.nLote = nLote;
	this.caducidad = caducidad;	
	this.nCaducidad = nCaducidad;
	this.descAbonoError = descAbonoError;
	this.bulto = bulto;
	this.ubicacion = ubicacion;
	this.tipoReferencia = tipoReferencia;
	this.estadoLin = estadoLin;
	this.codError = codError;
	this.descError = descError;
	this.codTpCa = codTpCa;
	this.cantidadMaximaPermitida = cantidadMaximaPermitida;
	this.stockDevueltoBandejas = stockDevueltoBandejas;
	this.flgBandejas = flgBandejas;
		
	//Metodos	
	this.prepareDevolucionToJsonObject = function prepareDevolucionToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {				
					"codArticulo" : codArticulo,
					"denominacion" : denominacion,
					"marca" : marca,
					"seccion" : seccion,
					"provrGen" : provrGen,
					"provrTrabajo" : provrTrabajo,
					"denomProveedor" : denomProveedor,
					"familia" : familia,
					"formatoDevuelto" : formatoDevuelto,
					"formato" : formato,
					"tipoFormato" : tipoFormato,
					"pasillo" : pasillo,
					"estructuraComercial" : estructuraComercial,
					"uc" : uc,
					"stockActual" : stockActual,
					"stockTienda" : stockTienda,
					"stockDevolver" : stockDevolver,
					"stockDevuelto" : stockDevuelto,
					"stockDevueltoBandejas" : stockDevueltoBandejas,
					"flgBandejas" : flgBandejas,
					"cantAbonada" : cantAbonada,
					"flgContinuidad" : flgContinuidad,
					"lote" : lote,
					"nLote" : nLote,
					"caducidad" : caducidad,
					"nCaducidad" : nCaducidad,
					"descAbonoError" : descAbonoError,
					"bulto" : bulto,
					"ubicacion" : ubicacion,
					"tipoReferencia" : tipoReferencia,
					"estadoLin" : estadoLin,
					"codError" : codError,
					"descError" : descError,
					"codTpCa" : codTpCa,
					"cantidadMaximaPermitida" : cantidadMaximaPermitida
			 }; 
		}	
		return jsonObject;
	}
}