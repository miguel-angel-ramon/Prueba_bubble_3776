/*
 * JAVASCRIPT CLASS: ImagenComercial
 */
function ImagenComercial( centro, referencia, facing, capacidad, facingAlto, facingAncho
						, tipoReferencia, tipoPlano, multiplicador, imc, avisoCambio
						, metodo, sfm, diasDeVenta
						, tratamientoVegalsa, facingModificable, flgErrorWSFacingVegalsa
						){
	// Atributos
	this.centro = centro;
	this.referencia = referencia;
	this.facing = facing;
	this.capacidad = capacidad;
	this.facingAlto = facingAlto;
	this.facingAncho = facingAncho;
	this.tipoReferencia = tipoReferencia;
	this.tipoPlano = tipoPlano;
	this.multiplicador = multiplicador;
	this.imc = imc;
	this.avisoCambio = avisoCambio;
	this.metodo = metodo;
	this.sfm = sfm;
	this.diasDeVenta = diasDeVenta;
	this.tratamientoVegalsa = tratamientoVegalsa;
	this.facingModificable = facingModificable;

	//Metodos	
	this.prepareImagenComercialToJsonObject = function prepareImagenComercialToJsonObject(){
		var jsonObject = null;		
		with (this) {
			jsonObject = {
					"centro" : centro,
					"referencia" : referencia,
					"facing" : facing,
					"capacidad" : capacidad,
					"facingAlto" : facingAlto,
					"facingAncho" : facingAncho,
					"tipoReferencia" : tipoReferencia,
					"tipoPlano" : tipoPlano,
					"multiplicador" : multiplicador,
					"imc" : imc,
					"avisoCambio" : avisoCambio,
					"metodo" : metodo,
					"sfm" : sfm,
					"diasDeVenta" : diasDeVenta,
					"tratamientoVegalsa" : tratamientoVegalsa,
					"facingModificable" : facingModificable
			 }; 
		}		
		return jsonObject;
	}
}