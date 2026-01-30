package es.eroski.misumi.dao.iface;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ModificarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ModificarStockResponseType;

public interface StockTiendaDao {

	public ConsultarStockResponseType consultaStock(ConsultarStockRequestType stockTiendaRequest, HttpSession session) throws Exception;
	
	public ModificarStockResponseType modificarStock(ModificarStockRequestType modificarStockRequest, HttpSession session) throws Exception;
	
	public Double getStockInicial(Long codCentro, Long codArticulo) throws Exception;

}