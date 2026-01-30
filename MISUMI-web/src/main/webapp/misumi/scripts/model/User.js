/*
 * JAVASCRIPT CLASS: User
 */
function User (user, clave, perfil, centro){
	// Atributos
	this.userName = user;
	this.password = clave;
	this.perfil = perfil; //T = Tecnico (puede elegir centro); C = Centro (Tiene un centro asignado)
	this.centro = centro;
		
	//Metodos	
	this.prepareToJsonObject = function prepareToJsonObject(){
		var jsonObject = null;
		var temp = null;
		if (centro != null)
			temp = centro.prepareToJsonObject();
		
		with (this) {
			jsonObject = {
				 "userName" : userName,
				 "password" : password,
				 "perfil" : perfil,
				 "centro" : temp					 
			 }; 
		}
		
		return jsonObject;
	}
}

