package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.Devolucion;
import es.eroski.misumi.model.DevolucionAvisos;
import es.eroski.misumi.model.DevolucionCatalogoDescripcion;
import es.eroski.misumi.model.DevolucionCatalogoEstado;
import es.eroski.misumi.model.DevolucionEmail;
import es.eroski.misumi.model.DevolucionFinCampana;
import es.eroski.misumi.model.DevolucionLinea;
import es.eroski.misumi.model.DevolucionOrdenRecogida;
import es.eroski.misumi.model.DevolucionOrdenRetirada;
import es.eroski.misumi.model.DevolucionPlataforma;
import es.eroski.misumi.model.DevolucionTipos;
import es.eroski.misumi.model.OptionSelectBean;
import es.eroski.misumi.model.SeccionBean;
import es.eroski.misumi.model.TDevolucionLinea;
import es.eroski.misumi.model.ui.Pagination;

public interface DevolucionService {
	// Devolucion Fin Campaña
	public List<SeccionBean> findAllDevolucionFinCampanaSecciones(Long codCentro) throws Exception;
	public List<DevolucionFinCampana> findAllDevolucionFinCampana(Long codCentro, Long codSeccion) throws Exception;
	public Long findAllDevolucionFinCampanaCount(Long codCentro) throws Exception;
	public List<Long> getRefByPattern(String term, String session,Devolucion devolucion) throws Exception;
	
	// Devolucion Orden de Retirada
	public List<SeccionBean> findAllDevolucionOrdenRetiradaSecciones(Long codCentro) throws Exception;
	public List<DevolucionOrdenRetirada> findAllDevolucionOrdenRetirada(Long codCentro, Long codSeccion) throws Exception;
	public Long findAllDevolucionOrdenRetiradaCount(Long codCentro) throws Exception;

	// Devolucion Orden de Recogida
	public List<SeccionBean> findAllDevolucionOrdenRecogidaSecciones(Long codCentro) throws Exception;
	public List<DevolucionOrdenRecogida> findAllDevolucionOrdenRecogida(Long codCentro, Long codSeccion) throws Exception;
	public Long findAllDevolucionOrdenRecogidaCount(Long codCentro) throws Exception;

	
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	/**************************************CONSULTAS PROCEDIMIENTOS PLSQL DEVOLUCIONES***********************************/
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	
	/***********************************          PK_APR_DEVOLUCIONES_MISUMI          ***********************************/
	
	//Método PLSQL PK_APR_DEVOLUCIONES_MISUMI.P_APR_OBT_CAB_DEVOL, que devuelve las cabeceras de las devoluciones
	public DevolucionCatalogoEstado cargarCabeceraDevoluciones(final Devolucion devolucion) throws Exception;

	//Método PLSQL PK_APR_DEVOLUCIONES_MISUMI.P_APR_OBT_ESTADO_DEVOL, que devuelve las devoluciones por estado
	public DevolucionCatalogoEstado cargarEstadoDevoluciones(final Devolucion devolucion) throws Exception;

	//Método PLSQL PK_APR_DEVOLUCIONES_MISUMI.P_APR_OBT_DEVOL_TOTAL, que devuelve todas las devoluciones
	public DevolucionCatalogoEstado cargarAllDevoluciones(final Devolucion devolucion) throws Exception;

	//Método PLSQL PK_APR_DEVOLUCIONES_MISUMI.P_APR_OBT_DENOM_DEVOL, que devuelve las denominaciones de devoluciones por centro
	public DevolucionCatalogoDescripcion cargarDenominacionesDevoluciones(final Devolucion devolucion) throws Exception;
	
	//Método PLSQL PK_APR_DEVOLUCIONES_MISUMI.P_AVISOS_PREP_MERCANCIA, que devuelve si existen devoluciones en estado PREPARAR MERCANCIA
    //(ESTADO = 1) y si son devoluciones de FRESCOS (AREA = 1), ALI (AREA = 2) o NO ALI (AREA > 2).
	public DevolucionAvisos cargarAvisosDevoluciones(final Long codCentro) throws Exception;
	
	//Método PLSQL PK_APR_DEVOLUCIONES_MISUMI.f_cant_stock_dev_vacia, que devuelve si una devolución tiene o no algún stock vacío.
	public String devolucionConStockVacio(Devolucion devolucion) throws Exception;
	
	//Método PLSQL PK_APR_DEVOLUCIONES_MISUMI.P_OBTENER_DATOS_COMBO
	public DevolucionTipos obtenerDatosCombo(Centro centro) throws Exception;

	//Método PLSQL PK_APR_DEVOLUCIONES_MISUMI.p_obtener_plataformas
	public DevolucionPlataforma obtenerPlataformasDevolucionCreadaPorCentro(DevolucionPlataforma devolucionPlataforma) throws Exception;

	//Método PLSQL PK_APR_DEVOLUCIONES_MISUMI.P_OBT_CANT_DEVOLVER
	public List<DevolucionLinea> obtenerCantidadADevolver(Devolucion paramDevolucion) throws Exception;
	
	/**************************************      PK_APR_DEVOLUCIONES_ACT_MISUMI         ***********************************/
	
	//Método PLSQL PK_APR_DEVOLUCIONES_ACT_MISUMI.P_APR_ACTUALIZAR_DEVOL, que actualiza las líneas de devolución
	public DevolucionCatalogoEstado actualizarDevolucion(final Devolucion devolucion) throws Exception;
	
	//Método PLSQL PK_APR_DEVOLUCIONES_ACT_MISUMI.P_APR_FINALIZAR_DEVOL, que pasa de estado a finalizadas las devoluciones.
	public DevolucionEmail finalizarDevolucion(final Devolucion devolucion, String flgRellenarHuecos, String dispositivo) throws Exception;

	public Devolucion duplicarDevolucion(final Devolucion devolucion) throws Exception;
	
	public Devolucion eliminarDevolucion(final Devolucion devolucion) throws Exception;
	
	//Método PLSQL PK_APR_DEVOLUCIONES_ACT_MISUMI.p_apr_alta_devolucion
	public DevolucionCatalogoEstado altaDevolucionCreadaPorCentro(Devolucion devolucion) throws Exception;
	
	
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	/******************************************** CONSULTAS TABLA TEMPORAL************************************************/
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	
	//Método para rellenar la tabla temporal con las líneas de devolución
	public void insertAll(List<TDevolucionLinea> listaTDevolucionLinea) throws Exception;
	
	//Método para eliminar los registros de la tabla temporal antiguos
	public void deleteHistorico() throws Exception;
	
	//Método para eliminar los registros 
	public void delete(TDevolucionLinea tDevolLinea) throws Exception;
	
	//Método que devuelve las líneas de devolución de una devolución ordenadas
	public List<TDevolucionLinea> findLineasDevolucion(String session,Devolucion devolucion, Pagination pagination, Long codArticulo, String proveedor,String flagRefPermanentes) throws Exception;
	
	//Método que devuelve las líneas de devolución de una devolución ordenadas filtrando por parte de la referencia
	public List<TDevolucionLinea> findLineasDevolucion(String session,Devolucion devolucion, Pagination pagination, Long codArticulo, String proveedor,String flagRefPermanentes,String filtroReferencia) throws Exception;
	
	//Método que actualiza con ceros las líneas de la devolución que tienen la cantidad vacía y el bulto vacío
	public void updateConCeroTablaSesionLineaDevolucion(String session, Devolucion devolucion) throws Exception;
	
	//Método que sirve para detectar si existen líneas con stock_devuelto en nulo.
	public String existeStockDevueltoConCeroTablaSesionLineaDevolucion(String session, Devolucion devolucion) throws Exception;

	/**
	 * Saber si el proveedor ha finalizado la revisión de bultos y referencias. (Ningún bulto abierto)
	 * Devuelve NO, el proveedor está SIN FINALIZAR (bultos pendientes de cerrar. COUNT > 0)
	 * Devuelve SI, el proveedor está FINALIZADO (bultos cerrados. COUNT=0)
	 * @param session
	 * @param devolucion
	 * @param proveedor
	 * @param origen PC, PDA
	 * @return
	 * @throws Exception
	 */
	public String esProveedorSinFinalizar(String session, Long devolucion, String proveedor, String origen) throws Exception;
	
	public List<TDevolucionLinea> findLineasDevolucionPDF(String session, Devolucion devolucion) throws Exception;
	public List<TDevolucionLinea> findLineasDevolucionAgrupadasPDF(String session, Devolucion devolucion) throws Exception;
	public List<TDevolucionLinea> findContadoresPorProveedorPDF(String session, Devolucion devolucion) throws Exception;
	public List<TDevolucionLinea> findContadoresPorProveedorPDF(String session, Devolucion devolucion, boolean contarReferenciasSinRetirada) throws Exception;
	public List<TDevolucionLinea> findContadoresBultoPorProveedorPDF(String session, Devolucion devolucion) throws Exception;
	public List<TDevolucionLinea> findContadoresBultoPorProveedorPDF(String session, Devolucion devolucion, boolean contarReferenciasSinRetirada) throws Exception;
	public List<TDevolucionLinea> findContadoresPorProveedorBultoPDF(String session, Devolucion devolucion) throws Exception;
	public Long findContadoresReferenciasSinRetiradaPDF(String session, Devolucion devolucion) throws Exception;
	public List<TDevolucionLinea> findContadoresPorBultoPDF(String session, Devolucion devolucion) throws Exception;
	
	//Método que devuelve una lista que rellenará el combobox de proveedores.
	public List<OptionSelectBean> obtenerProveedoresLineasDevolucion(String session,Devolucion devolucion) throws Exception;
	
	//Actualiza las líneas de devolución modificadas
	public void updateTablaSesionLineaDevolucion(String session,Devolucion devolucion, boolean isSaveData) throws Exception;
	
	//Resetea los valores de los estados de las devoluciones
	public void resetDevolEstados(String session) throws Exception;
	
	//Método para obtener las líneas de devolución modificadas.
	public List<DevolucionLinea> findLineasDevolucionEditadas(String session, Long codError) throws Exception;

	//Método para obtener el indice de la primera línea de devolución con stockDevuelto null o bulto null de una lista de líneas de devolución.
	public int firstLineaDevolucionStockOrBultoNull(Integer posicionInicial, List<TDevolucionLinea> listaTDevolucionLinea) throws Exception;

	//Método para obtener las líneas de devolución completadas.
	public int findLineasDevolucionCompletadas(List<TDevolucionLinea> listaTDevolucionLinea) throws Exception;
	
	//Método para comprobación de la existencia de ref permanentes para una devolución
	public boolean existenRefPermanentes(String session, Devolucion devolucion) throws Exception;
	
	//Mira si una linea de devolución existe para una devolución en la tabla temporal.
	public String existeReferenciaEnDevolucion(String session, DevolucionLinea devLin) throws Exception;

	//Elimina las filas seleccionadas en el grid.
	public void deleteLineasDevolucion(String session, Devolucion devolucionAEliminar) throws Exception;
	
	//Método para obtener la suma total de costes finales
	public Double getSumaCosteFinal(final Devolucion devolucion, String sessionId) throws Exception;
	
	/**
	 * comprueba si el centro tiene todas sus referencias revisadas.
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public String hayRefsPdtes(String session);
	
	/**
	 * Para los centros parametrizados con 27_PC_DEVOLUCIONES_PROCEDIMIENTO,
	 * comprobar si se puede finalizar la devolución.
	 * Para ello se termina llamando a la función de SIA "pk_apr_devoluciones_misumi.f_puede_finalizar_dev()".
	 * @param centro
	 * @param localizador
	 * @return
	 */
	public String puedeFinalizar(Long centro, String localizador) throws Exception;

}
