/**
 * 
 */
package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.CategoriaBean;
import es.eroski.misumi.model.NumLoteMpGrid;
import es.eroski.misumi.model.QueryReferenciasByDescr;
import es.eroski.misumi.model.ReferenciasByDescr;
import es.eroski.misumi.model.SeccionBean;
import es.eroski.misumi.model.ui.Pagination;

/**
 * Consulta de referencias por descripcion para seleccion de referencia desde pop-up.
 * P49634
 * @author BICUGUAL
 *
 */
public interface ReferenciasSelectDao {

	/**
	 * Me devuelve el numero de registros totales encontrados para una busqueda
	 * @param filtros
	 * @return
	 * @throws Exception
	 */
	public NumLoteMpGrid findAllCount(QueryReferenciasByDescr filtros) throws Exception;
	
	/**
	 * Me devuelve el numero de registros totales encontrados para una busqueda de nivel modelo proveedor
	 * @param filtros
	 * @return
	 * @throws Exception
	 */
	public Long findAllCountNivelModProv(QueryReferenciasByDescr filtros) throws Exception;
	
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
	 * Obtiene la lista de secciones (distinct) con su respectiva area, existentes en la query de referencias por descripcion
	 * @param filtros
	 * @return
	 * @throws Exception
	 */
	public List<SeccionBean> findLstSecciones(QueryReferenciasByDescr filtros) throws Exception;

	/**
	 * Obtiene la lista de categorias (distinct) con su respectiva area, existentes en la query de referencias por descripcion
	 * @param filtros
	 * @return
	 * @throws Exception
	 */
	public List<CategoriaBean> findLstCategoria(QueryReferenciasByDescr filtros)	throws Exception;
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
	public List<Long> findArea(QueryReferenciasByDescr referenciaByDescr) throws Exception;


	/**
	 * Obtiene la lista de ÇReferencias Numericas
	 * @param referenciaByDescr
	 * @return
	 * @throws Exception
	 */
	public List<ReferenciasByDescr> findAllRef(QueryReferenciasByDescr filtros, Pagination pagination) throws Exception;

	
	

	
}
