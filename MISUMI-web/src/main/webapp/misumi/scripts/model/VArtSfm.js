/*
 * JAVASCRIPT CLASS: VArtSfm
 */
function VArtSfm (codLoc, codArticulo, nivel, denomInforme, codN1,descCodN1, codN2, descCodN2, codN3, descCodN3,
		codN4, descCodN4, codN5, descCodN5, marca, lmin, lsf, sfm,
		coberturaSfm, ventaMedia, ventaAnticipada, uc, stock, diasStock, capacidad, listadoModificados, pedir,facingCentro,loteSN,codArticuloLote,flgActualizarFacing){
	
	// Atributos
	this.codLoc=codLoc;
	this.codArticulo=codArticulo;
	this.nivel=nivel;
	this.denomInforme=denomInforme;
	if (codN1!="null"){
		this.codN1=codN1;
	}else{
		this.codN1=null;
	}
	this.descCodN1=descCodN1;
	if (codN2!="null"){
		this.codN2=codN2;
	}else{
		this.codN2=null;
	}
	this.descCodN2=descCodN2;
	if (codN3!="null"){
		this.codN3=codN3;
	}else{
		this.codN3=null;
	}
	this.descCodN3=descCodN3;
	if (codN4!="null"){
		this.codN4=codN4;
	}else{
		this.codN4=null;
	}
	this.descCodN4=descCodN4;
	if (codN5!="null"){
		this.codN5=codN5;
	}else{
		this.codN5=null;
	}
	this.descCodN5=descCodN5;
	this.marca=marca;
	this.lmin=lmin;
	this.lsf=lsf;
	this.sfm=sfm;
	this.coberturaSfm=coberturaSfm;
	this.ventaMedia=ventaMedia;
	this.ventaAnticipada=ventaAnticipada;
	this.uc=uc;
	this.stock=stock;	
	this.diasStock=diasStock;
	this.capacidad=capacidad;
	this.listadoModificados=listadoModificados;
	if (pedir!="null"){
		this.pedir=pedir;
	}else{
		this.pedir=null;
	}
	this.loteSN=loteSN;
	this.facingCentro=facingCentro;
	this.codArticuloLote=codArticuloLote;
	this.flgActualizarFacing = flgActualizarFacing;
	
	//Metodos	
	this.prepareVArtSfmToJsonObject = function prepareVArtSfmToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codLoc" : codLoc,
				 "codArticulo" : codArticulo,
				 "nivel" : nivel,
				 "denomInforme" : denomInforme,
				 "codN1" : codN1,
				 "descCodN1" : descCodN1,
				 "codN2" : codN2,
				 "descCodN2" : descCodN2,
				 "codN3" : codN3,
				 "descCodN3" : descCodN3,
				 "codN4" : codN4,
				 "descCodN4" : descCodN4,
				 "codN5" : codN5,
				 "descCodN5" : descCodN5,
				 "marca" : marca,
				 "lmin" : lmin,
				 "lsf" : lsf,
				 "sfm" : sfm,
				 "coberturaSfm" : coberturaSfm,
				 "ventaMedia" : ventaMedia,
				 "ventaAnticipada" : ventaAnticipada,
				 "uc" : uc,
				 "stock" : stock,	
				 "diasStock" : diasStock,
				 "capacidad" : capacidad,
				 "listadoModificados" : listadoModificados,
				 "facingCentro" : facingCentro,
				 "pedir" : pedir,
				 "loteSN" : loteSN,
				 "codArticuloLote" : codArticuloLote,
				 "flgActualizarFacing" : flgActualizarFacing
			 }; 
		}
		
		return jsonObject;
	}
}

