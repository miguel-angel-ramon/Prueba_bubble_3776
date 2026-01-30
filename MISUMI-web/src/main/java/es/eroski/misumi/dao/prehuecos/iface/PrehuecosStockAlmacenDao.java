package es.eroski.misumi.dao.prehuecos.iface;

import java.util.List;

import es.eroski.misumi.model.pda.prehuecos.StockAlmacen;

/**
 * Contiene las interfaces relacionadas con el stock almacen de prehuecos.
 * @author MAPALAPA
 *
 */
public interface PrehuecosStockAlmacenDao {

	/**
	 * Obtiene la lista de referencias
	 * @return
	 */
	public List<StockAlmacen> getReferenciasPrehuecosStockAlmacen(Long centro, String mac, int pagActual);
	
	public int updateEstadoPrehueco(String codCentro, String mac, String codArt, int nuevoEstado);
	
	
}
