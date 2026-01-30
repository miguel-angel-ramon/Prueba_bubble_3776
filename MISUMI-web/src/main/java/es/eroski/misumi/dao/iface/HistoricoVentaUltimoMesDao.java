package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.HistoricoVentaUltimoMes;

public interface HistoricoVentaUltimoMesDao  {

	public List<HistoricoVentaUltimoMes> findAll(HistoricoVentaUltimoMes historicoVentaUltimoMes) throws Exception ;
	
	public List<HistoricoVentaUltimoMes> findAllLastDays(HistoricoVentaUltimoMes historicoVentaUltimoMes, int lastDays)	throws Exception;
	
	public List<HistoricoVentaUltimoMes> findTotalLastDays(HistoricoVentaUltimoMes historicoVentaUltimoMes, int lastDays) throws Exception;
}
