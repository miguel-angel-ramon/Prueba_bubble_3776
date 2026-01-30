/*
 * JAVASCRIPT CLASS: ArtGamaRapid
 */
function ArtGamaRapid (codCentro, codArticulo, grupo1, grupo2, grupo3,
		grupo4, grupo5){
	
	// Atributos
	this.codCentro=codCentro;
	this.codArticulo=codArticulo;
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
	
	
	//Metodos	
	this.prepareArtGamaRapidToJsonObject = function prepareArtGamaRapidToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codCentro" : codCentro,
				 "codArticulo" : codArticulo,
				 "grupo1" : grupo1,
				 "grupo2" : grupo2,
				 "grupo3" : grupo3,
				 "grupo4" : grupo4,
				 "grupo5" : grupo5		
			 }; 
		}
		
		return jsonObject;
	}
}

