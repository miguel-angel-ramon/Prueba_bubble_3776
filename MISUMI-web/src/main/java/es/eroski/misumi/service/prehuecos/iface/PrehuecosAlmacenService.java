package es.eroski.misumi.service.prehuecos.iface;

import java.sql.SQLException;
import java.util.List;

import es.eroski.misumi.model.pda.prehuecos.AlmacenPrehuecos;
import es.eroski.misumi.model.pda.prehuecos.StockAlmacen;

/**
 * 
 * @author MAPALAPA
 *
 */
public interface PrehuecosAlmacenService {

	
	public List<StockAlmacen> getReferenciasPrehuecosStockAlmacen(Long centro, String mac, String codArt) throws SQLException;
	
	/**
	 * Obtiene la lista de referencias
	 * @return
	 * @throws SQLException 
	 */
	public List<AlmacenPrehuecos> getReferenciasPrehuecosAlmacen(Long centro, String mac, String codArt) throws SQLException;
	
	/**
	 * Obtiene la lista de referencias después de actualizar el estado
	 * @return
	 * @throws SQLException 
	 */
	public List<AlmacenPrehuecos> getReferenciasPrehuecosAlmacenActualizada(Long centro, String mac, String codArt) throws SQLException;

}
