package es.eroski.misumi.dao.iface;

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
import es.eroski.misumi.model.SeccionBean;


public interface DevolucionDaoSIA {

	// Devolucion Fin Campaña
	public List<SeccionBean> findAllDevolucionFinCampanaSecciones(DevolucionFinCampana devFinCampana) throws Exception;
	public List<DevolucionFinCampana> findAllDevolucionFinCampana(DevolucionFinCampana devFinCampana) throws Exception;
	public Long findAllDevolucionFinCampanaCount(DevolucionFinCampana devFinCampana) throws Exception;

	// Devolucion Orden de Retirada
	public List<SeccionBean> findAllDevolucionOrdenRetiradaSecciones(DevolucionOrdenRetirada devOrdenRetirada) throws Exception;
	public List<DevolucionOrdenRetirada> findAllDevolucionOrdenRetirada(DevolucionOrdenRetirada devOrdenRetirada) throws Exception;
	public Long findAllDevolucionOrdenRetiradaCount(DevolucionOrdenRetirada devOrdenRetirada) throws Exception;

	// Devolucion Orden de Recogida
	public List<SeccionBean> findAllDevolucionOrdenRecogidaSecciones(DevolucionOrdenRecogida devOrdenRecogida) throws Exception;
	public List<DevolucionOrdenRecogida> findAllDevolucionOrdenRecogida(DevolucionOrdenRecogida devOrdenRecogida) throws Exception;
	public Long findAllDevolucionOrdenRecogidaCount(DevolucionOrdenRecogida devOrdenRecogida) throws Exception;
	
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
	public String devolucionConStockVacio(final Devolucion devolucion) throws Exception;
	
	//Método PLSQL PK_APR_DEVOLUCIONES_MISUMI.p_apr_alta_devolucion, que crea una devolución creada por el centro.
	public DevolucionCatalogoEstado altaDevolucionCreadaPorCentro(final Devolucion devolucion) throws Exception;
	
	//Método PLSQL PK_APR_DEVOLUCIONES_MISUMI.p_apr_obtener_plataformas, obtiene las
	public DevolucionPlataforma obtenerPlataformasDevolucionCreadaPorCentro(final DevolucionPlataforma devolucionPlataforma) throws Exception;
	
	public DevolucionTipos obtenerDatosCombo(Centro paramCentro) throws Exception;

	public List<DevolucionLinea> obtenerCantidadADevolver(Devolucion paramDevolucion) throws Exception;

	/**************************************      PK_APR_DEVOLUCIONES_ACT_MISUMI         ***********************************/
	
	//Método PLSQL PK_APR_DEVOLUCIONES_ACT_MISUMI.P_APR_ACTUALIZAR_DEVOL, que actualiza las líneas de devolución
	public DevolucionCatalogoEstado actualizarDevolucion(final Devolucion devolucion) throws Exception;
	
	//Método PLSQL PK_APR_DEVOLUCIONES_ACT_MISUMI.P_APR_FINALIZAR_DEVOL, que pasa de estado a finalizadas las devoluciones.
	public DevolucionEmail finalizarDevolucion(final Devolucion devolucion,String flgRellenarHuecos, String dispositivo) throws Exception;

	//Método PLSQL PK_APR_DEVOLUCIONES_ACT_MISUMI.P_APR_DUPLICAR_DEVOLUCION, que duplica una devolución.
	public Devolucion duplicarDevolucion(final Devolucion devolucion) throws Exception;

	//Método PLSQL PK_APR_DEVOLUCIONES_ACT_MISUMI.P_APR_ELIMINAR_DEVOLUCION, que duplica una devolución.
	public Devolucion eliminarDevolucion(final Devolucion devolucion) throws Exception;
	
	/**
	 * Llama a la función de SIA "pk_apr_devoluciones_misumi.f_puede_finalizar_dev()" que permite conocer
	 * si la devolución se puede o no finalizar.
	 * @param centro
	 * @param localizador
	 * @return
	 */
	public String puedeFinalizar(final Long centro, String localizador) throws Exception;

}
