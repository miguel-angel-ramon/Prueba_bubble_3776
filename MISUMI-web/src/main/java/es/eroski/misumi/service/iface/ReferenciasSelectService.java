package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.CategoriaBean;
import es.eroski.misumi.model.NumLoteMpGrid;
import es.eroski.misumi.model.PopUp34ViewerManagerBean;
import es.eroski.misumi.model.QueryReferenciasByDescr;
import es.eroski.misumi.model.ReferenciasByDescr;
import es.eroski.misumi.model.ui.Pagination;

/**
 *  Interface para la seleccion de referencias en busquedas por descripcion. 
 *  P49634
 * @author BICUGUAL
 */
public interface ReferenciasSelectService {

	/**
	 * Me devuelve el numero de registros totales encontrados para una busqueda
	 * @param filtros
	 * @return
	 * @throws Exception
	 */
	public NumLoteMpGrid findAllCount(QueryReferenciasByDescr filtros) throws Exception;
	
	/**
	 * Devuelve de todas las referencias.
	 * para la descripcion, codSeccion y codCentro seleccionados
	 * 
	 * @param filtros
	 * @param pagination
	 * @return
	 * @throws Exception
	 */
	public List<ReferenciasByDescr> findAll (QueryReferenciasByDescr filtros, Pagination pagination) throws Exception ;
	
	
	/**
	 * Obtiene la lista de secciones (distinct) existentes en la query de referencias por descripcion
	 * @param filtros
	 * @return
	 * @throws Exception
	 */
	public PopUp34ViewerManagerBean findLstOptions(QueryReferenciasByDescr filtros)throws Exception ;
	

	/**
	 * Obtiene la lista de hijas de la referencia para el subgrid
	 * @param referenciaByDescr
	 * @return
	 * @throws Exception
	 */
	public List<ReferenciasByDescr> findAllTextilN2ByLote(QueryReferenciasByDescr referenciaByDescr) throws Exception;
	
	/**
	 * Obtiene Cod_Art mediante EAN
	 * @param referenciaByDescr
	 * @return long
	 * @throws Exception
	 */
	public List<ReferenciasByDescr> findEAN(QueryReferenciasByDescr referenciaByDescr) throws Exception;
	
	/**
	 * Obtiene Grupo1 (AREA) mediante en grupo2 (seccion)
	 * @param referenciaByDescr
	 * @return long
	 * @throws Exception
	 */
	public List<Long> findArea(QueryReferenciasByDescr filtros) throws Exception;
	
	/**
	 * P-52083
	 * Refactorizacion de codigo para reutilizar la query principal de busqueda de referencias.
	 * Diferentes categorias exitentes en la busqueda de referencias. Utilizada para cargar el combo de categorias 
	 * para filtrar las referencias por seccion. 
	 * @BICUGUAL
	 */
	public List<CategoriaBean> findLstCategoria(QueryReferenciasByDescr filtros)	throws Exception;

}
