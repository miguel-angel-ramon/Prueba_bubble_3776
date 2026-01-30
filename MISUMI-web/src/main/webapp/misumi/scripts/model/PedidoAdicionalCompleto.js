/*
 * JAVASCRIPT CLASS: PedidoAdicional
 */

	function PedidoAdicionalCompleto( tipoPedido, codCentro, codArt, descArt, frescoPuro, oferta
									, referenciaNueva, fechaIni, cantidad1, uniCajas, fecha2, cantidad2, fecha3
									, cantidad3, fechaFin, capacidadMaxima, implantacionMinima, estado, conCajas
									, cajas, fechaIniWS, fechaIniOferta, minFechaIni, identificador, fechaPilada
									, excluir, tipoAprovisionamiento, uuid, bloqueoPilada, bloqueoEncargo, tipoOferta
									, referenciaUnitaria, stock, fecha4, cantidad4, fecha5, cantidad5, stockPlataforma
									, esStock, referenciaFFPP, fechaNoDisponible, noGestionaPbl, area, seccion, esGenerica
									, esEncargoEspecial, datosPedidoUnidadesPedir, datosPedidoRadioPeso, datosPedidoPesoDesde
									, datosPedidoPesoHasta, datosPedidoDescripcion, primeraFechaEntrega, strPrimeraFechaEntrega
									, fechasVenta, bloqueoEncargoCliente, errorValidar, nombreCliente, apellidoCliente
									, telefonoCliente, contactoCentro, esRefErronea, listaOfertas, errorWS, fechaFinOferta
									, error, user, perfil, descError, sesion, strFechaIni, strFechaFin, strFechaFinMin, strFecha2
									, strFecha3, strFechaPilada, strFechaIniWS, strFechaIniOferta, strFechaFinOferta, strMinFechaIni
									, calcularFechaIniFin, catalogo, fechaBloqueoEncargo, fechaBloqueoEncargoPilada, mostrarLeyendaBloqueo
									, categoria, subcategoria, flgAvisoEncargoLote, flgEspec, identificadorSIA, flgGestionaSIA
									, codArtEroski, formatoArticulo
									, tratamientoVegalsa, identificadorVegalsa, tratamiento
									, diasMaximos, cantidadMaxima, msgUNIDADESCAJAS
									) {
		this.tipoPedido = tipoPedido;
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.descArt = descArt;
		this.frescoPuro = frescoPuro;
		this.oferta = oferta;
		this.referenciaNueva = referenciaNueva;
		this.fechaIni = fechaIni;
		this.cantidad1 = cantidad1;
		this.uniCajas = uniCajas;
		this.fecha2 = fecha2;
		this.cantidad2 = cantidad2;
		this.fecha3 = fecha3;
		this.cantidad3 = cantidad3;
		this.fechaFin = fechaFin;
		this.capacidadMaxima = capacidadMaxima;
		this.implantacionMinima = implantacionMinima;
		this.estado = estado;
		this.conCajas = conCajas;
		this.cajas = cajas;
		this.fechaIniWS = fechaIniWS;
		this.fechaIniOferta = fechaIniOferta;
		this.minFechaIni = minFechaIni;
		this.identificador = identificador;
		this.fechaPilada = fechaPilada;
		this.excluir = excluir;
		this.tipoAprovisionamiento = tipoAprovisionamiento;
		this.uuid = uuid;
		this.bloqueoPilada = bloqueoPilada;
		this.bloqueoEncargo = bloqueoEncargo;
		this.tipoOferta = tipoOferta;
		this.referenciaUnitaria = referenciaUnitaria;
		this.stock = stock;
		this.fecha4 = fecha4;
		this.cantidad4 = cantidad4;
		this.fecha5 = fecha5;
		this.cantidad5 = cantidad5;
		this.stockPlataforma = stockPlataforma;
		this.esStock = esStock;
		this.referenciaFFPP = referenciaFFPP;
		this.fechaNoDisponible = fechaNoDisponible;
		this.noGestionaPbl = noGestionaPbl;
		this.area = area;
		this.seccion = seccion;
		this.esGenerica = esGenerica;
		this.esEncargoEspecial = esEncargoEspecial;
		this.datosPedidoUnidadesPedir = datosPedidoUnidadesPedir;
		this.datosPedidoRadioPeso = datosPedidoRadioPeso;
		this.datosPedidoPesoDesde = datosPedidoPesoDesde;
		this.datosPedidoPesoHasta = datosPedidoPesoHasta;
		this.datosPedidoDescripcion = datosPedidoDescripcion;
		this.primeraFechaEntrega = primeraFechaEntrega;
		this.strPrimeraFechaEntrega = strPrimeraFechaEntrega;
		this.fechasVenta = fechasVenta;
		this.bloqueoEncargoCliente = bloqueoEncargoCliente;
		this.errorValidar = errorValidar;
		this.nombreCliente = nombreCliente;
		this.apellidoCliente = apellidoCliente;
		this.telefonoCliente = telefonoCliente;
		this.contactoCentro = contactoCentro;
		this.esRefErronea = esRefErronea;
		this.listaOfertas = listaOfertas;
		this.errorWS = errorWS;
		this.fechaFinOferta = fechaFinOferta;
		this.error = error;
		this.user = user;
		this.perfil = perfil;
		this.descError = descError;
		this.sesion = sesion;
		this.strFechaIni = strFechaIni;
		this.strFechaFin = strFechaFin;
		this.strFechaFinMin = strFechaFinMin;
		this.strFecha2 = strFecha2;
		this.strFecha3 = strFecha3;
		this.strFechaPilada = strFechaPilada;
		this.strFechaIniWS = strFechaIniWS;
		this.strFechaIniOferta = strFechaIniOferta;
		this.strFechaFinOferta = strFechaFinOferta;
		this.strMinFechaIni = strMinFechaIni;
		this.calcularFechaIniFin = calcularFechaIniFin;
		this.catalogo = catalogo;
		this.fechaBloqueoEncargo = fechaBloqueoEncargo;
		this.fechaBloqueoEncargoPilada = fechaBloqueoEncargoPilada;
		this.mostrarLeyendaBloqueo = mostrarLeyendaBloqueo;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
		this.flgAvisoEncargoLote = flgAvisoEncargoLote;
		this.flgEspec = flgEspec;
		this.identificadorSIA = identificadorSIA;
		this.flgGestionaSIA = flgGestionaSIA;
		this.codArtEroski = codArtEroski;
		this.formatoArticulo = formatoArticulo;
		this.tratamientoVegalsa = tratamientoVegalsa;
		this.identificadorVegalsa = identificadorVegalsa;
		this.tratamiento = tratamiento;
		this.diasMaximos = diasMaximos;
		this.cantidadMaxima = cantidadMaxima;
		this.msgUNIDADESCAJAS = msgUNIDADESCAJAS;

	//Metodos	
	this.preparePedidoAdicionalToJsonObject = function preparePedidoAdicionalToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
					"tipoPedido" : tipoPedido,
					"codCentro" : codCentro,
					"codArt" : codArt,
					"descArt" : descArt,
					"frescoPuro" : frescoPuro,
					"oferta" : oferta,
					"referenciaNueva" : referenciaNueva,
					"fechaIni" : fechaIni,
					"cantidad1" : cantidad1,
					"uniCajas" : uniCajas,
					"fecha2" : fecha2,
					"cantidad2" : cantidad2,
					"fecha3" : fecha3,
					"cantidad3" : cantidad3,
					"fechaFin" : fechaFin,
					"capacidadMaxima" : capacidadMaxima,
					"implantacionMinima" : implantacionMinima,
					"estado" : estado,
					"conCajas" : conCajas,
					"cajas" : cajas,
					"fechaIniWS" : fechaIniWS,
					"fechaIniOferta" : fechaIniOferta,
					"minFechaIni" : minFechaIni,
					"identificador" : identificador,
					"fechaPilada" : fechaPilada,
					"excluir" : excluir,
					"tipoAprovisionamiento" : tipoAprovisionamiento,
					"uuid" : uuid,
					"bloqueoEncargo" : bloqueoEncargo,
					"bloqueoPilada" : bloqueoPilada,
					"tipoOferta" : tipoOferta,
					"referenciaUnitaria" : referenciaUnitaria,
					"stock" : stock,
					"fecha4" : fecha4,
					"cantidad4" : cantidad4,
					"fecha5" : fecha5,
					"cantidad5" : cantidad5,
					"stockPlataforma" : stockPlataforma,
					"esStock" : esStock,
					"referenciaFFPP" : referenciaFFPP,
					"calcularFechaIniFin" : calcularFechaIniFin,
					"noGestionaPbl" : noGestionaPbl,
					"area" : area,
					"seccion" : seccion,
					"esGenerica" : esGenerica,
					"esEncargoEspecial" : esEncargoEspecial,
					"datosPedidoUnidadesPedir" : datosPedidoUnidadesPedir,
					"datosPedidoRadioPeso" : datosPedidoRadioPeso,
					"datosPedidoPesoDesde" : datosPedidoPesoDesde,
					"datosPedidoPesoHasta" : datosPedidoPesoHasta,
					"datosPedidoDescripcion" : datosPedidoDescripcion,
					"primeraFechaEntrega" : primeraFechaEntrega,
					"strPrimeraFechaEntrega" : strPrimeraFechaEntrega,
					"fechasVenta" : fechasVenta,
					"bloqueoEncargoCliente" : bloqueoEncargoCliente,
					"errorValidar" : errorValidar,
					"nombreCliente" : nombreCliente,
					"apellidoCliente" : apellidoCliente,
					"telefonoCliente" : telefonoCliente,
					"contactoCentro" : contactoCentro,
					"esRefErronea" : esRefErronea,
					"listaOfertas" : listaOfertas,
					"errorWS" : errorWS,
					"fechaFinOferta" : fechaFinOferta,
					"error" : error,
					"user" : user,
					"perfil" : perfil,
					"descError" : descError,
					"sesion" : sesion,
					"strFechaIni" : strFechaIni,
					"strFechaFin" : strFechaFin,
					"strFechaFinMin" : strFechaFinMin,
					"strFecha2" : strFecha2,
					"strFecha3" : strFecha3,
					"strFechaPilada" : strFechaPilada,
					"strFechaIniWS" : strFechaIniWS,
					"strFechaIniOferta" : strFechaIniOferta,
					"strFechaFinOferta" : strFechaFinOferta,
					"strMinFechaIni" : strMinFechaIni,
					"calcularFechaIniFin" : calcularFechaIniFin,
					"catalogo" : catalogo,
					"fechaBloqueoEncargo" : fechaBloqueoEncargo,
					"fechaBloqueoEncargoPilada" : fechaBloqueoEncargoPilada,
					"mostrarLeyendaBloqueo" : mostrarLeyendaBloqueo,
					"categoria" : categoria,
					"subcategoria" : subcategoria,
					"flgAvisoEncargoLote" : flgAvisoEncargoLote,
					"flgEspec" : flgEspec,
					"identificadorSIA" : identificadorSIA,
					"flgGestionaSIA" : flgGestionaSIA,
					"codArtEroski" : codArtEroski,
					"formatoArticulo" : formatoArticulo,
					"tratamientoVegalsa" : tratamientoVegalsa,
					"identificadorVegalsa" : identificadorVegalsa,
					"tratamiento" : tratamiento,
					"diasMaximos" : diasMaximos,
					"cantidadMaxima" : cantidadMaxima,
					"msgUNIDADESCAJAS" : msgUNIDADESCAJAS
			 }; 
		}
		
		return jsonObject;
	}
}

