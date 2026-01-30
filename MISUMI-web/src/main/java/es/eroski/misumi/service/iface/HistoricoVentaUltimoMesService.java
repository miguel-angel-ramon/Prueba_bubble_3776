package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.HistoricoVentaUltimoMes;


public interface HistoricoVentaUltimoMesService {

	  public List<HistoricoVentaUltimoMes> findAll(HistoricoVentaUltimoMes historicoVentaUltimoMes) throws Exception  ;
	  
	  public List<HistoricoVentaUltimoMes> findAllLastDays(HistoricoVentaUltimoMes historicoVentaUltimoMes) throws Exception  ;
	  
	  public HistoricoVentaUltimoMes findOne(HistoricoVentaUltimoMes historicoVentaUltimoMes) throws Exception  ;
	  
	  public HistoricoVentaUltimoMes findTotalLastDays(HistoricoVentaUltimoMes historicoVentaUltimoMes, int lastDays) throws Exception;
}
