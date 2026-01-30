package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.OptionSelectBean;
import es.eroski.misumi.model.calendariovegalsa.DiaCalendarioVegalsa;
import es.eroski.misumi.model.calendariovegalsa.DiaDetalleCalendarioVegalsa;
import es.eroski.misumi.model.ui.GridFilterBean;
import es.eroski.misumi.model.ui.Page;

public interface CalendarioVegalsaService {

	/**
	 * Devuelve la lista de calendarios vegalsa con objeto clave-valor para utilizarlo en un combobox
	 * @return
	 */
	public List<OptionSelectBean> getLstMapasVegalsa();
	
	/**
	 * Devuelve la lista de dias de un mes para el calendario Vegalsa
	 * @param codCentro
	 * @param codMapa
	 * @param mes
	 * @return
	 */
	public List<DiaCalendarioVegalsa> getLstDiasCalendarioVegalsa(Long codCentro, Integer codMapa, String mes);

	/**
	 * Devuelve la lista de dias de detalle para la carga de detalle de pedido Vegalsa en el jqGrid
	 * @param codCentro
	 * @param codMapa
	 * @param filtros del jqGrid
	 * @return
	 */
	public Page<DiaDetalleCalendarioVegalsa> getLstDiasDetalleCalendarioVegalsaGrid(Long codCentro, Integer codMapa, GridFilterBean filtros);

	/**
	 * Devuelve la lista de dias de detalle para la carga del Excel
	 * 
	 * @param codCentro
	 * @param codMapa
	 * @param filtrosJson
	 * @return
	 * @throws Exception
	 */
	public List<DiaDetalleCalendarioVegalsa> getLstDiasDetalleCalendarioVegalsaExport(Long codCentro, Integer codMapa,
			String filtrosJson) throws Exception;
}
