/*
 * JAVASCRIPT CLASS: ListadoRefSegundaReposicion
 */
function ListadoRefSegundaReposicion (codCentro, grupo1, grupo2, listadoSeleccionados, mes){
	
	// Atributos
	this.codCentro=codCentro;
	this.grupo1=grupo1;
	this.grupo2=grupo2;
	this.listadoSeleccionados=listadoSeleccionados;
	this.mes=mes;
		
	//Metodos	
	this.prepareListadoRefSegundaReposicionToJsonObject = function prepareListadoRefSegundaReposicionToJsonObject(){
		var jsonObject = null;
		
		with (this) {
			jsonObject = {
					"codCentro" : codCentro,
					"grupo1" : grupo1,
					"grupo2" : grupo2,
					"listadoSeleccionados" : listadoSeleccionados,
					"mes" : mes
			 }; 
		}
		
		return jsonObject;
	}
}

