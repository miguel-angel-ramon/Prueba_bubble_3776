package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.AreaFacingCero;
import es.eroski.misumi.model.VAgruComerParamSfmcap;
import es.eroski.misumi.model.ui.Pagination;


public interface VAgruComerParamFacingCeroService {

	/**
	 * Obtener los datos de la estructura comercial. Para nuestro caso, el área.
	 * Devuelve nulo en caso de fallo, o la lista si va bien. 
	 * 
	 * @param areasFacingCero
	 * @param pagination
	 * @return
	 * @throws Exception
	 */
	public List<VAgruComerParamSfmcap> findAllAreas(AreaFacingCero areasFacingCero, Pagination pagination) throws Exception;

	/**
	 * Extraemos la estructura unicamente y nos ahorramos algunos mapeos y ordenacion.
	 * Si no hay datos devolvemos null.
	 * 
	 * @param codCentro
	 * @param grupo1
	 * @return
	 * @throws Exception
	 */
	public String findTipoEstructura(Long codCentro, Long grupo1) throws Exception;
}
