package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.ParamStockFinalMinimo;
import es.eroski.misumi.model.ReferenciasCentroIC;
import es.eroski.misumi.model.StockFinalMinimo;


public interface StockFinalMinimoService {

	  public List<StockFinalMinimo> findAll(StockFinalMinimo stockFinalMinimo) throws Exception  ;
	  
	  public StockFinalMinimo findOne(StockFinalMinimo stockFinalMinimo) throws Exception  ;


	  public Long findFinalStockParam(ParamStockFinalMinimo paramStockFinalMinimo)
			throws Exception;

	  public StockFinalMinimo findOneSIA(StockFinalMinimo stockFinalMinimo) throws Exception  ;
	  
	  public Long findVidaUtil(StockFinalMinimo stockFinalMinimo) throws Exception;
	  
	  public StockFinalMinimo obtenerStockFinalMinimo(ReferenciasCentroIC referenciasCentroIC) throws Exception  ;
}
