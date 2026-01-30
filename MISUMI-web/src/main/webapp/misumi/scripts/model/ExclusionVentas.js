/*
 * JAVASCRIPT CLASS: ExclusionVentas
 */
function ExclusionVentas (codCentro, codArt, grupo1, grupo2, grupo3, grupo4, grupo5, identificador, listadoSeleccionados, fecha){
	
	// Atributos
	this.codCentro=codCentro;
	this.codArt=codArt;
	
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
	if (identificador!="null"){
		this.identificador=identificador;
	}else{
		this.identificador=null;
	}
	this.listadoSeleccionados=listadoSeleccionados;

	if (fecha!="null"){
		this.fecha=fecha;
	}else{
		this.fecha=null;
	}

	//Metodos	
	this.prepareExclusionVentasToJsonObject = function prepareExclusionVentasToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
					"codCentro" : codCentro,
					"codArt" : codArt,
					"grupo1" : grupo1,
					"grupo2" : grupo2,
					"grupo3" : grupo3,
					"grupo4" : grupo4,
					"grupo5" : grupo5,
					"identificador" : identificador,
					"listadoSeleccionados" : listadoSeleccionados,
					"fecha" : fecha
			 }; 
		}
		
		return jsonObject;
	}
}

