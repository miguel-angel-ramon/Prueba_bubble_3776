/*
 * JAVASCRIPT CLASS: Centro
 */
function Devolucion (centro,flagHistorico,titulo1,codArticulo,estadoCab,localizador,devolucion,fechaDesde,fechaHasta,flgRecogida,abono,recogida,codPlataforma,descripcion,motivo,titulo2,fechaPrecio,codError,descError,devLineas,devLineasModificadas,proveedor,flagRefPermanentes,codRMA){
	
	// Atributos
	this.devolucion = devolucion;
	this.localizador = localizador;
	this.centro = centro;
	this.fechaDesde = fechaDesde;
	this.fechaHasta = fechaHasta;
	this.flgRecogida = flgRecogida;
	this.abono = abono;
	this.recogida = recogida;
	this.codPlataforma = codPlataforma;
	this.titulo1 = titulo1;
	this.descripcion = descripcion;
	this.motivo = motivo;
	this.titulo2 = titulo2;
	this.fechaPrecio = fechaPrecio;
	this.estadoCab = estadoCab;
	this.codError = codError;
	this.descError = descError;
	this.devLineas = devLineas;
	this.devLineasModificadas = devLineasModificadas;
	this.flagHistorico = flagHistorico;
	this.codArticulo = codArticulo;
	this.proveedor = proveedor;
	this.flagRefPermanentes = flagRefPermanentes;
	this.codRMA = codRMA;
		
	//Metodos	
	this.prepareDevolucionToJsonObject = function prepareDevolucionToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {				
					"devolucion" : devolucion,
					"localizador" : localizador,
					"centro" : centro,
					"fechaDesde" : fechaDesde,
					"fechaHasta" : fechaHasta,
					"flgRecogida" : flgRecogida,
					"abono" : abono,
					"recogida" : recogida,
					"codPlataforma" : codPlataforma,
					"titulo1" : titulo1,
					"descripcion" : descripcion,
					"motivo" : motivo,
					"titulo2" : titulo2,
					"fechaPrecio" : fechaPrecio,
					"estadoCab" : estadoCab,
					"codError" : codError,
					"descError" : descError,
					"devLineas" : devLineas,
					"devLineasModificadas" : devLineasModificadas,
					"flagHistorico" : flagHistorico,
					"codArticulo" : codArticulo,
					"proveedor" : proveedor,
					"flagRefPermanentes" : flagRefPermanentes,
					"codRMA" : codRMA
			 }; 
		}	
		return jsonObject;
	}
}