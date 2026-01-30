package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.ui.Pagination;

public interface VAgruComerRefDao  {

	public List<VAgruComerRef> findAll(VAgruComerRef vAgruComerRef, Pagination pagination) throws Exception ;
	
	public String getDescripcionSeccion(Integer codArea, Integer codSeccion) throws Exception;
	
	/**
	 * Obtiene la descripción del área.
	 * @param codArea
	 * @return
	 * @throws Exception
	 */
	public String getDescripcionArea(Integer codArea) throws Exception;

}
