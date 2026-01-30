package es.eroski.misumi.dao.iface;

import java.util.List;
import java.util.Map;

import es.eroski.misumi.model.DiaVentasUltimasOfertas;
import es.eroski.misumi.model.HistoricoUnidadesVenta;

public interface HistoricoUnidadesVentaDao {

	public List<HistoricoUnidadesVenta> findAll(HistoricoUnidadesVenta historicoUnidadesVenta) throws Exception ;
	public List<HistoricoUnidadesVenta> findTotalLastDays(HistoricoUnidadesVenta historicoUnidadesVenta, int lastDays) throws Exception ;
	public Double findDayMostSales(HistoricoUnidadesVenta historicoUnidadesVenta, int lastDays) throws Exception;
	public List<DiaVentasUltimasOfertas> findDateListMediaSales(String codCentro, String codArticulo, String fecIni, String fecFin,String sumVentaAnticipada) throws Exception;
	public List<Map<String,Object>> findDateListMediaSalesViejo(String codCentro, String codArticulo, String fecIni, String fecFin) throws Exception;
	public HistoricoUnidadesVenta findVentaUltimaOferta(HistoricoUnidadesVenta historicoUnidadesVenta,String sumVentaAnticipada) throws Exception;
}
