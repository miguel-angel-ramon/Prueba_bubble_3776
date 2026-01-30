package es.eroski.misumi.service.iface;

import java.util.List;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.MovimientoStock;
import es.eroski.misumi.model.StockNoServido;
import es.eroski.misumi.model.pda.PdaUltimosMovStocks;
import es.eroski.misumi.model.ui.Pagination;

public interface MovimientosStockService {

	public List<MovimientoStock> findAllLastDays(MovimientoStock mc, HttpSession session) throws Exception  ;
	public List<MovimientoStock> findAllDetailsLastDays(MovimientoStock mc, Pagination pagination) throws Exception  ;
	public List<StockNoServido> findAllLastDays(StockNoServido nsr, Pagination pagination) throws Exception ;
	public List<PdaUltimosMovStocks> findAllDetailsLastDaysPda(MovimientoStock mc) throws Exception;
	public List<PdaUltimosMovStocks> findAllDetailsLastDaysPdaPaginada(MovimientoStock mc, int inicioPaginacion, int finPaginacion) throws Exception;

}
