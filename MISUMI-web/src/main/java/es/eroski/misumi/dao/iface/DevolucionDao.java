package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.Devolucion;
import es.eroski.misumi.model.DevolucionLinea;
import es.eroski.misumi.model.OptionSelectBean;
import es.eroski.misumi.model.TDevolucionLinea;
import es.eroski.misumi.model.ui.Pagination;

public interface DevolucionDao {

	//Método para rellenar la tabla temporal con las líneas de devolución
	public void insertAll(List<TDevolucionLinea> listaTDevolucionLinea) throws Exception;
	
	//Método para eliminar los registros de la tabla temporal antiguos
	public void deleteHistorico() throws Exception;
		
	//Método para eliminar los registros 
	public void delete(TDevolucionLinea tDevolLinea) throws Exception;
	
	//Método para obtener los registros del combo de proveedor
	public List<OptionSelectBean> obtenerProveedoresLineasDevolucion(String session, Devolucion devolucion) throws Exception;
	
	//Método para obtener los elementos según una ordenación o paginación
	public List<TDevolucionLinea> findLineasDevolucion(String session, Devolucion devolucion, Pagination pagination, Long codArticulo, String proveedor, String flagRefPermanentes, String filtroReferencia) throws Exception;

	//Método para obtener los elementos según una ordenación o paginación para los casos en los que se ha seleccionado un bulto concreto
	public List<TDevolucionLinea> findLineasDevBulto(String session, Devolucion devolucion, Pagination pagination, Long codArticulo, String proveedor, String flagRefPermanentes, String filtroReferencia) throws Exception;

	//Método que sirve para actualizar la tabla temporal con los datos modificados.
	public void updateTablaSesionLineaDevolucion(String session, Devolucion devolucion, boolean isSaveData) throws Exception;
	
	//Método que actualiza con ceros las líneas de la devolución que tienen la cantidad vacía y el bulto vacío
	public void updateConCeroTablaSesionLineaDevolucion(String session, Devolucion devolucion) throws Exception;
	
	//Método que sirve para detectar si existen líneas con stock_devuelto en nulo.
	public boolean existeStockDevueltoConCeroTablaSesionLineaDevolucion(String session, Devolucion devolucion) throws Exception;

	/**
	 * Saber si el proveedor ha finalizado la revisión de bultos y referencias. (Ningún bulto abierto)
	 * Devuelve NO, el proveedor está SIN FINALIZAR (bultos pendientes de cerrar. COUNT > 0)
	 * Devuelve SI, el proveedor está FINALIZADO (bultos cerrados. COUNT=0)
	 * @param session
	 * @param devolucion
	 * @param proveedor
	 * @return
	 * @throws Exception
	 */
	public boolean esProveedorSinFinalizar(String session, Long devolucion, String proveedor, String origen) throws Exception;

	//Resetea los valores de los estados de las devoluciones
	public void resetDevolEstados(String session) throws Exception;
	
	//Método para obtener las líneas de devolución modificadas.
	public List<DevolucionLinea> findLineasDevolucionEditadas(String session, Long codError) throws Exception;
	
	//Método para obtener los elementos para los pdf
	public List<TDevolucionLinea> findLineasDevolucionPDF(String session, Devolucion devolucion) throws Exception;
	public List<TDevolucionLinea> findLineasDevolucionAgrupadasPDF(String session, Devolucion devolucion) throws Exception;
	public List<TDevolucionLinea> findContadoresPorProveedorPDF(String session, Devolucion devolucion) throws Exception;
	public List<TDevolucionLinea> findContadoresPorProveedorPDF(String session, Devolucion devolucion, boolean contarReferenciasSinRetirada) throws Exception;
	public List<TDevolucionLinea> findContadoresBultoPorProveedorPDF(String session, Devolucion devolucion) throws Exception;
	public List<TDevolucionLinea> findContadoresBultoPorProveedorPDF(String session, Devolucion devolucion, boolean contarReferenciasSinRetirada) throws Exception;
	public List<TDevolucionLinea> findContadoresPorProveedorBultoPDF(String session, Devolucion devolucion) throws Exception;
	public Long findContadoresReferenciasSinRetiradaPDF(String session, Devolucion devolucion) throws Exception;
	public List<TDevolucionLinea> findContadoresPorBultoPDF(String session, Devolucion devolucion) throws Exception;
	
	//Método para comprobación de la existencia de ref permanentes para una devolución
	public boolean existenRefPermanentes(String session, Devolucion devolucion) throws Exception;
	
	//Mira si una linea de devolución existe para una devolución en la tabla temporal.
	public boolean existeReferenciaEnDevolucion(String session, DevolucionLinea devLin) throws Exception;

	//Elimina las filas seleccionadas en el grid.
	public void deleteLineasDevolucion(String session, Devolucion devolucionAEliminar) throws Exception;
	
	//Busca las referencias que encajan con el patrón introducido.
	public List<Long> getRefByPattern(String term, String session, Devolucion devolucion) throws Exception;
	
	//Método para obtener la suma total de costes finales
	public Double getSumaCosteFinal(final  Devolucion devolucion, String sessionId) throws Exception;

	/**
	 * Determina si el centro tiene todas las referencias revisadas.
	 * @param session
	 * @param devolucion
	 * @param proveedor
	 * @return
	 * @throws Exception
	 */
	public boolean hayRefsPdtes(String session) throws Exception;
}
