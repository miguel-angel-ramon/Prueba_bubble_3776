package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.ParamStockFinalMinimo;
import es.eroski.misumi.model.StockFinalMinimo;

public interface StockFinalMinimoDao  {

	public List<StockFinalMinimo> findAll(StockFinalMinimo stockFinalMinimo) throws Exception ;

	public Long findFinalStockParam(ParamStockFinalMinimo paramStockFinalMinimo)
			throws Exception;
	
	public Long findVidaUtil(StockFinalMinimo stockFinalMinimo) throws Exception;
}
