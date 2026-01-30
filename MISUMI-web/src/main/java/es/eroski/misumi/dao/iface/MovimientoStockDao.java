package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.MovimientoStock;
import es.eroski.misumi.model.pda.PdaUltimosMovStocks;
import es.eroski.misumi.model.ui.Pagination;

public interface MovimientoStockDao  {

	public List<MovimientoStock> findAllLastDays(MovimientoStock mc, int lastDays) throws Exception ;
	public List<MovimientoStock> findAllDetailsLastDays(MovimientoStock mc, int lastDays, Pagination pagination) throws Exception ;
	public List<PdaUltimosMovStocks> findAllDetailsLastDaysPda(MovimientoStock mc) throws Exception;
	public List<PdaUltimosMovStocks> findAllDetailsLastDaysPdaPaginada(MovimientoStock mc, int inicioPaginacion, int finPaginacion) throws Exception;
}
