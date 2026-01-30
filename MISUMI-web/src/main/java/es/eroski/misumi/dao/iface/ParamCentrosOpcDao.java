package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.ParamCentrosOpc;

public interface ParamCentrosOpcDao{

	/**
	 * Recupera todas las parametrizaciones según los parámetros que se pasen.
	 * Opcional el centro y el código de parametrización.
	 * 
	 * @param paramCentrosOpc
	 * @return
	 * @throws Exception
	 */
	public List<ParamCentrosOpc> findAll(ParamCentrosOpc paramCentrosOpc) throws Exception;

	/**
	 * Recupera el número de registros parametrizados según los parámetros que se pasen.
	 * Opcional el centro y el código de parametrización.
	 * 
	 * @param paramCentrosOpc
	 * @return
	 * @throws Exception
	 */
	public Long findAllCont(ParamCentrosOpc paramCentrosOpc) throws Exception;
	
	/**
	 * Recupera todas las parametrizaciones concatenadas, según los parámetros que se pasen.
	 * Opcional el centro y el código de parametrización.
	 * 
	 * @param paramCentrosOpc
	 * @return
	 * @throws Exception
	 */
	public List<ParamCentrosOpc> findAllConcat(ParamCentrosOpc paramCentrosOpc) throws Exception;

}
