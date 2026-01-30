package es.eroski.misumi.service.prehuecos.iface;

import es.eroski.misumi.model.pda.prehuecos.PrehuecosLineal;

public interface PrehuecosLinealService {
	
	/**
	 * Comprueba si existen registros en la tabla T_MIS_PREHUECOS_LINEAL que no estén validados.
	 * 
	 * @param prehuecosLineal
	 * @return
	 * @throws Exception
	 */
	public Long getPrehuecosLineal(PrehuecosLineal prehuecosLineal) throws Exception;

	/**
	 * Obtengo el stock lineal y el estado de la referencia consultada.
	 * Podrá tener tres posibles valores,
	 * 
	 * 0 - Sin Revisar.
	 * 1 - Pendiente.
	 * 2 - Validada.
	 * 
	 * @param prehuecosLineal
	 * @return
	 * @throws Exception
	 */
	public PrehuecosLineal getStockLinealEstadoRef(PrehuecosLineal prehuecosLineal) throws Exception;

	/**
	 * Comprueba si hay prehuecos SIN VALIDAR o han sido VALIDADOS HOY.
	 * @param prehuecosLineal
	 * @return
	 * @throws Exception
	 */
	public Long getPrehuecosSinValidar(PrehuecosLineal prehuecosLineal) throws Exception;

	/**
	 * Borrado de los datos de los prehuecos de la tabla T_MIS_PREHUECOS_LINEAL.
	 * @param prehuecosLineal
	 * @throws Exception
	 */
	public int deletePrehuecos(PrehuecosLineal prehuecosLineal) throws Exception;

	/**
	 * Alta de los datos de prehuecos en la tabla T_MIS_PREHUECOS_LINEAL.
	 * @param prehuecosLineal
	 * @throws Exception
	 */
	public int insertPrehuecos(PrehuecosLineal prehuecosLineal) throws Exception;
	
	/**
	 * Modificar los datos del Stock Lineal en la tabla T_MIS_PREHUECOS_LINEAL.
	 * 
	 * @param prehuecosLineal
	 */
	public int updatePrehuecos(PrehuecosLineal prehuecosLineal);
	
}
