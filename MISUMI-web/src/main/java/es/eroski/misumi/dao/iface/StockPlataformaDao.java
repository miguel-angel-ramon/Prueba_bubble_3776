package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.StockPlataforma;

public interface StockPlataformaDao {

	public abstract StockPlataforma find(StockPlataforma sp) throws Exception;

}