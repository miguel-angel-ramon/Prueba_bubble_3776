package es.eroski.misumi.dao.prehuecos.iface;

import java.util.List;

import es.eroski.misumi.model.pda.prehuecos.AlmacenPrehuecos;
import es.eroski.misumi.model.pda.prehuecos.StockAlmacen;

/**
 * Contiene las interfaces relacionadas con el stock almacen de prehuecos.
 * @author MAPALAPA
 *
 */
public interface PrehuecosAlmacenDao {

	/**
	 * Obtiene la lista de referencias
	 * @return
	 */
	public List<StockAlmacen> getReferenciasPrehuecosStockAlmacen(Long centro, String mac, String codArt);
	
	public List<AlmacenPrehuecos> getReferenciasPrehuecosAlmacen(Long centro, String mac, String codArt);
	
	public List<AlmacenPrehuecos> getReferenciasPrehuecosAlmacenActualizada(Long centro, String mac, String codArt);
}
