package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.StockFinalMinimo;

public interface StockFinalMinimoSIADao  {

	public List<StockFinalMinimo> findAll(StockFinalMinimo stockFinalMinimo) throws Exception ;
}
