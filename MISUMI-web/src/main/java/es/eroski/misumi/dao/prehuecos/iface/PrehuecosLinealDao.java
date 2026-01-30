package es.eroski.misumi.dao.prehuecos.iface;

import es.eroski.misumi.model.pda.prehuecos.PrehuecosLineal;

public interface PrehuecosLinealDao {

	/**
	 * Comprueba si existen Prehuecos en la tabla T_MIS_PREHUECOS_LINEAL.
	 * 
	 * @param prehuecosLineal
	 * @return
	 * @throws Exception
	 */
	public Long getPrehuecosSinValidar(PrehuecosLineal prehuecosLineal, Boolean esHoy);

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
	 * Borrado de los datos guardados en T_MIS_PREHUECOS_LINEAL.
	 * 
	 * @param prehuecosLineal
	 * @throws Exception
	 */
	public int deletePrehuecos(PrehuecosLineal prehuecosLineal);

	/**
	 * Alta de los datos del Stock Lineal en la tabla T_MIS_PREHUECOS_LINEAL.
	 * 
	 * @param prehuecosLineal
	 */
	public int insertPrehuecos(PrehuecosLineal prehuecosLineal);

	/**
	 * Modificar los datos del Stock Lineal en la tabla T_MIS_PREHUECOS_LINEAL.
	 * 
	 * @param prehuecosLineal
	 */
	public int updatePrehuecos(PrehuecosLineal prehuecosLineal);

}
