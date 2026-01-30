/*
 * JAVASCRIPT CLASS: Filters
 * Reglas para el filtrado (busqueda) mediante el search toolbar 
 * field - Nombre de la columna por la que filtrar
 * op - Tipo de filtrado ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
 * data - Valor del filtro
 */
function Filters (groupOp , rules){
	// Atributos
	this.groupOp=groupOp;
	this.rules=rules;
}