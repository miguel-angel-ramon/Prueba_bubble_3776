/*
 * JAVASCRIPT CLASS: Rules
 * Reglas para el filtrado (busqueda) mediante el search toolbar 
 * field - Nombre de la columna por la que filtrar
 * op - Tipo de filtrado ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
 * data - Valor del filtro
 */
function Rules (field,op,data){
	
	// Atributos
	this.field=field;
	this.op=op;
	this.data=data;
	
}
