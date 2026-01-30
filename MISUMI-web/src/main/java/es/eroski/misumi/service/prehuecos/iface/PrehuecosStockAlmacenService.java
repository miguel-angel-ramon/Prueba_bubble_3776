package es.eroski.misumi.service.prehuecos.iface;

import java.sql.SQLException;
import java.util.List;

import es.eroski.misumi.model.pda.prehuecos.StockAlmacen;

/**
 * 
 * @author MAPALAPA
 *
 */
public interface PrehuecosStockAlmacenService {

	/**
	 * Obtiene la lista de referencias
	 * @return
	 * @throws SQLException 
	 */
	public List<StockAlmacen> getReferenciasPrehuecosStockAlmacen(Long centro, String mac,int pagActual) throws SQLException;
	
	public int updateEstadoPrehueco(Long codCentro, String mac, String codArt, int nuevoEstado) throws SQLException;

}
