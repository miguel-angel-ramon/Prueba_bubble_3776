/*
 * JAVASCRIPT CLASS: NuevoPedidoOferta
 */
	function NuevoPedidoOferta(tipoAprov, codCentro,
			codArticulo, descriptionArt, fechaInicio,
			fecha2, fecha3, fechaPilada, fechaFin,
			fechaMinima, fechaMaxima, implInicial,
			implFinal, cantidad1, cantidad2,
			cantidad3, uniCajaServ, flgTipoListado, bloqueado,
			tipoPedido, codError, descError, indice,
			usuario, anoOferta, numOferta, grupo1,
			grupo2, grupo3,
			listadoModificados,
			cabGondola, nelCodN1, nelCodN2, nelCodN3) {
		
		this.tipoAprov = tipoAprov;
		this.codCentro = codCentro;
		this.codArticulo = codArticulo;
		this.descriptionArt = descriptionArt;
		this.fechaInicio = fechaInicio;
		this.fecha2 = fecha2;
		this.fecha3 = fecha3;
		this.fechaPilada = fechaPilada;
		this.fechaFin = fechaFin;
		this.fechaMinima = fechaMinima;
		this.fechaMaxima = fechaMaxima;
		this.implInicial = implInicial;
		this.implFinal = implFinal;
		this.cantidad1 = cantidad1;
		this.cantidad2 = cantidad2;
		this.cantidad3 = cantidad3;
		this.uniCajaServ = uniCajaServ;
		this.flgTipoListado = flgTipoListado;
		this.bloqueado = bloqueado;
		this.tipoPedido = tipoPedido;
		this.codError = codError;
		this.descError = descError;
		this.indice = indice;
		this.usuario = usuario;
		this.anoOferta = anoOferta;
		this.numOferta = numOferta;
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
		this.listadoModificados = listadoModificados;
		
		this.cabGondola = cabGondola;
		this.nelCodN1 = nelCodN1;
		this.nelCodN2 = nelCodN2;
		this.nelCodN3 = nelCodN3;
	
	//Metodos	
	this.prepareNuevoPedidoOfertaToJsonObject = function prepareNuevoPedidoOfertaToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
					"tipoAprov" : tipoAprov,
					"codCentro" : codCentro,
					"codArticulo" : codArticulo,
					"descriptionArt" : descriptionArt,
					"fechaInicio" : fechaInicio,
					"fecha2" : fecha2,
					"fecha3" : fecha3,
					"fechaPilada" : fechaPilada,
					"fechaFin" : fechaFin,
					"fechaMinima" : fechaMinima,
					"fechaMaxima" : fechaMaxima,
					"implInicial" : implInicial,
					"implFinal" : implFinal,
					"cantidad1" : cantidad1,
					"cantidad2" : cantidad2,
					"cantidad3" : cantidad3,
					"uniCajaServ" : uniCajaServ,
					"flgTipoListado" : flgTipoListado,
					"bloqueado" : bloqueado,
					"tipoPedido" : tipoPedido,
					"codError" : codError,
					"descError" : descError,
					"indice" : indice,
					"usuario" : usuario,
					"anoOferta" : anoOferta,
					"numOferta" : numOferta,
					"grupo1" : grupo1,
					"grupo2" : grupo2,
					"grupo3" : grupo3,
					"listadoModificados" : listadoModificados,
					"cabGondola" : cabGondola,
					"nelCodN1" : nelCodN1,
					"nelCodN2" : nelCodN2,
					"nelCodN3" : nelCodN3

			 }; 
		}
		
		return jsonObject;
	}
}

