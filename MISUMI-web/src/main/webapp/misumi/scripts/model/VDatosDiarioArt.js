/*
 * JAVASCRIPT CLASS: VDatosDiarioArt
 */
function VDatosDiarioArt (codArt, descripArt, grupo1, grupo2, grupo3, grupo4, grupo5, codifiComerSup, codifiComerHip){
	// Atributos
	this.codArt=codArt;
	this.descripArt=descripArt;
	this.grupo1=grupo1;
	this.grupo2=grupo2;
	this.grupo3=grupo3;
	this.grupo4=grupo4;
	this.grupo5=grupo5;
	this.codifiComerSup=codifiComerSup;	
	this.codifiComerHip=codifiComerHip;
		
	//Metodos	
	this.prepareToJsonObject = function prepareToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
				 "codArt" : codArt,
				 "descripArt" : descripArt,
				 "grupo1" : grupo1,
				 "grupo2" : grupo2,
				 "grupo3" : grupo3,
				 "grupo4" : grupo4,
				 "grupo5" : grupo5,
				 "codifiComerSup" : codifiComerSup,
				 "codifiComerHip" : codifiComerHip
			 }; 
		}
		
		return jsonObject;
	}
}

