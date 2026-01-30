/**
 * 
 */
package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.calendariovegalsa.DiaCalendarioVegalsa;
import es.eroski.misumi.model.calendariovegalsa.DiaDetalleCalendarioVegalsa;
import es.eroski.misumi.model.calendariovegalsa.MapaVegalsa;
import es.eroski.misumi.model.ui.FilterBean;
import es.eroski.misumi.model.ui.Pagination;

/**
 * MISUMI-301
 * 
 * Capa de persistencia para la pantalla de Calendario de Vegalsa
 * 
 * @author BICUGUAL
 *
 */
public interface CalendarioVegalsaDao {

	/**
	 * Devuelve la lista completa de mapas Vegalsa
	 * @return
	 */
	public List<MapaVegalsa> getLstMapasVegalsa();
	
	/**
	 * Obtiene la lista de dias del calendario correspondientes al mes facilitado, para el centro y mapa dados. 
	 * @param codCentro
	 * @param codMapa
	 * @param mes. Formato yyyy-mm
	 * @return
	 */
	public List<DiaCalendarioVegalsa> getLstDiasCalendarioVegalsa(Long codCentro, Integer codMapa, String mes);
	
	
	/**
	 * Obtiene la lista de dias del detalle de pedido de Vegalsa
	 * @param codCentro
	 * @param codMapa
	 * @param filtros
	 * @param pagination
	 * @return
	 */
	public List<DiaDetalleCalendarioVegalsa> getLstDiasDetalleCalendarioVegalsaForGrid(Long codCentro, Integer codMapa, FilterBean filtros, Pagination pagination);

	
	/**
	 * Devuelve el numero de elementos que se obtienen en la consulta en funcion de los filtros
	 * @param codCentro
	 * @param codMapa
	 * @param filtros
	 * @return
	 */
	public Long getCountDiasDetalleCalendarioVegalsaForGrid(Long codCentro, Integer codMapa, FilterBean filtros);
}
