/*
 * JAVASCRIPT CLASS: VArtCentroAlta
 */
function VArtCentroAlta (centro, codArticulo, descriptionArt, nivel, grupo1, grupo2, grupo3,
						grupo4, grupo5, descripcion, pedir, marcaMaestroCentro, catalogo,
						tpGama,	stock, tipoAprov, uniCajaServ, capacidadMax, stockMinComer, stockFinMinS, 
						codArtRela,	descripRela, multipli, tipoOferta, numeroOferta, cc, pedible, id,
						loteSN, facingCero){
	
	// Atributos
	this.centro=centro;
	this.codArticulo=codArticulo;
	this.descriptionArt=descriptionArt;
	this.nivel=nivel;
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
	if (grupo4!="null"){
		this.grupo4=grupo4;
	}else{
		this.grupo4=null;
	}
	if (grupo5!="null"){
		this.grupo5=grupo5;
	}else{
		this.grupo5=null;
	}
	this.descripcion=descripcion;
	if (pedir!="null"){
		this.pedir=pedir;
	}else{
		this.pedir=null;
	}
	
	if (marcaMaestroCentro!="null"){
		this.marcaMaestroCentro=marcaMaestroCentro;
	}else{
		this.marcaMaestroCentro=null;
	}

	if (catalogo!="null"){
		this.catalogo=catalogo;
	}else{
		this.catalogo=null;
	}

	this.tpGama=tpGama;
	this.stock=stock;	
	this.tipoAprov=tipoAprov;
	this.uniCajaServ=uniCajaServ;
	this.capacidadMax=capacidadMax;
	this.stockMinComer=stockMinComer;
	this.stockFinMinS=stockFinMinS;
	this.codArtRela=codArtRela;
	this.descripRela=descripRela;
	this.multipli=multipli;
	this.numeroOferta=numeroOferta;	
	this.cc=cc;
	if (pedible!="null"){
		this.pedible=pedible;
	}else{
		this.pedible=null;
	}
	
	this.id=id;  //id para enlazar con las referencias de un lote.
	
	if (loteSN!="null"){
		this.loteSN=loteSN;
	}else{
		this.loteSN=null;
	}
	
	this.facingCero = facingCero;
		
	//Metodos	
	this.prepareVArtCentroAltaToJsonObject = function prepareVArtCentroAltaToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "centro" : {
					 "codCentro" : centro.codCentro
				 },
				 "codArticulo" : codArticulo,
				 "descriptionArt" : descriptionArt,
				 "nivel" : nivel,
				 "grupo1" : grupo1,
				 "grupo2" : grupo2,
				 "grupo3" : grupo3,
				 "grupo4" : grupo4,
				 "grupo5" : grupo5,
				 "descripcion" : descripcion,
				 "pedir" : pedir,
				 "marcaMaestroCentro" : marcaMaestroCentro,
				 "catalogo" : catalogo,
				 "tpGama" : tpGama,
				 "stock" : stock,
				 "tipoAprov" : tipoAprov,
				 "uniCajaServ" : uniCajaServ,
				 "capacidadMax" : capacidadMax,
				 "stockMinComer" : stockMinComer,
				 "stockFinMinS" : stockFinMinS,
				 "codArtRela" : codArtRela,
				 "descripRela" : descripRela,
				 "multipli" : multipli,
				 "numeroOferta" : numeroOferta,
				 "cc" : cc,
				 "pedible" : pedible,
				 "id" : id,
				 "loteSN" : loteSN, 
				 "facingCero" : facingCero
			 }; 
		}
		
		return jsonObject;
	}
}

