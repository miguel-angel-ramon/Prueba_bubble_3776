package es.eroski.misumi.service.iface;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ModificarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ModificarStockResponseType;

public interface StockTiendaService {
	
	/**
	 * Consulta el Stock de la tienda.
	 * @param stockTiendaRequest
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public abstract ConsultarStockResponseType consultaStock(ConsultarStockRequestType stockTiendaRequest,  HttpSession session) throws Exception;

	/**
	 * Modifica el Stock de la tienda.
	 * @param modificarStockRequest
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public abstract ModificarStockResponseType modificarStock(ModificarStockRequestType modificarStockRequest,  HttpSession session) throws Exception;
    
	/**
	 * Devuelve "S" si es Pc peso variable. Si falla se come la excepcion y devuelve null.
	 * @param codArticulo
	 * @return
	 */
    public String consultarPcPesoVariable(Long codArticulo);
    
    /**
     * Consulta el Stock inicial.
     * @param codCentro
     * @param codArticulo
     * @return
     */
    public Double consultarStockInicial(Long codCentro, Long codArticulo) throws Exception;

    /**
     * Devuelve el precio del articulo en un centro.
     * @param codCentro
     * @param codArticulo
     * @return
     */
	public Double consultaPVP(Long codCentro, Long codArticulo, HttpSession session) throws Exception;
    
}