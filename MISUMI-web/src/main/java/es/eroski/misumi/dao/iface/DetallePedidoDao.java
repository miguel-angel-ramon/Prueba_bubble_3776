package es.eroski.misumi.dao.iface;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.Cronometro;
import es.eroski.misumi.model.DetallePedido;
import es.eroski.misumi.model.DetallePedidoLista;
import es.eroski.misumi.model.DetallePedidoModificados;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.GestionEurosRefs;
import es.eroski.misumi.model.PropuestaDetalladoVegalsa;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.model.calendariovegalsa.MapaVegalsa;
import es.eroski.misumi.model.ui.Pagination;

public interface DetallePedidoDao  {


	List<DetallePedido> findDetallePedido(Centro centro, HttpSession session) throws Exception;
	List<DetallePedido> findDetallePedidoSIA(Centro centro) throws Exception;

	/**
	 * Carga de la tabla T_DETALLADO_PEDIDO.
	 * @param listToInsert
	 * @param sesionID
	 * @return
	 * @throws Exception
	 */
	Long insertListIntoTemp(List<DetallePedido> listToInsert, String sesionID) throws Exception;

	void DeleteTemp(String sesionID) throws Exception;

	List<DetallePedido> findSessionInfo(DetallePedido detallePedido, String sesionID, Pagination pagination, String filtrosTabla) throws Exception;
	
	
	Double sumarEurosIniciales(DetallePedido detallePedido,String sesionID) throws Exception;
	Double sumarEurosFinales(DetallePedido detallePedido,String sesionID) throws Exception;
	Double sumarCajasIniciales(DetallePedido detallePedido,String sesionID) throws Exception;
	Double sumarCajasFinales(DetallePedido detallePedido,String sesionID) throws Exception;
	
	
	List<DetallePedido> findSessionInfoUpdateable(DetallePedido detallePedido,
			String sesionID, Pagination pagination) throws Exception;

	Long countSessionInfo(DetallePedido detallePedido, String sesionID, String filtrosTabla) throws Exception;

	List<VAgruComerRef> findFilterMatchesSection(VAgruComerRef vAgruComerRef,
			String sessionId) throws Exception;

	List<VAgruComerRef> findFilterMatchesCategoria(VAgruComerRef vAgruComerRef,
			String sessionId) throws Exception;

	

	DetallePedido add(DetallePedido detalladoPedido, String sessionId)
			throws Exception;
	DetallePedido updateEmpuje(DetallePedido detalladoPedido, String sessionId)
			throws Exception;
	DetallePedido updateDetalladoNuevo(DetallePedido detalladoPedido, String sessionId)
			throws Exception;
	

	void updateGridState(List<DetallePedido> detalladoPedido, String sessionId) throws Exception;
	
	void updateOnlyGridState(List<DetallePedido> detalladoPedido, String sessionId, Long estadoGrid, Long nuevoEstadoGrid) throws Exception;

	void deleteNewRecords(String sessionId) throws Exception;

	void resetSessionData(String sessionId) throws Exception;

	List<DetallePedido> findModifies(String sesionID) throws Exception;
	
	void updateData(List<DetallePedidoModificados> listaDetalladoModif,
			String sessionId) throws Exception;

	Long sumBoxes(DetallePedido detallePedido, String sesionID)
			throws Exception;

	Long existsSType(DetallePedido detallePedido, String sesionID)
			throws Exception;
	
	
	List<DetallePedido> findDatosEspecificosTextil(Long codCentro, String listaArticulos) throws Exception;
	
	VSurtidoTienda findDatosTipoAprovisionamiento (Long codCentro, Long referencia) throws Exception;

	List<GenericExcelVO> findSessionInfoExcel(DetallePedido detallePedido,String[] columnModel, String sesionID, Pagination pagination) throws Exception;
	
	String comprobarReferenciaNueva(Long codCentro, Long referencia, Date fechaGen) throws Exception;
	
	Long countHijasLote(Long codArt) throws Exception;
	
    List<DetallePedido> findAllTextilN2ByLote(DetallePedido detallePedido) throws Exception;
	
	int updateAllCodigoCaprabo(Long codCentro, String sesionID) throws Exception;
	
	int updateAllDescripcionCaprabo(Long codCentro, String sesionID) throws Exception;
	
    List<DetallePedido> findAllCaprabo(Long codCentro, String sesionID) throws Exception;
    
	void deleteAllNoCaprabo(Long codCentro, String sesionID) throws Exception;

	DetallePedido findOne(Long referencia, String codCentro, String sesionID) throws Exception;
	
	public DetallePedidoLista referenciaNuevaSIA(Long referencia, Long codCentro) throws Exception;
	
	public Cronometro calculoCronometroYNumeroHorasLimite(DetallePedido detallePedido, String sesionID) throws Exception;
	
	public Long countFlgPropuesta(String sesionID, Long center, Long seccion, Long categoria) throws Exception;
	public DetallePedido actualizarPrecioCostoFinal(DetallePedido detalladoPedido, String sesionID) throws Exception;
	
	public List<GestionEurosRefs> findSessionInfoGestionEurosRefs(DetallePedido detallePedido,String sesionID, Pagination pagination) throws Exception;
	
	public void updateGridStateGestionEurosRefs(final List<DetallePedido> listaDetalladoModif, final String sessionId) throws Exception;
	
	public void updatePrevioCalcular(DetallePedido detallePedido,String sesionID) throws Exception;
	
	public void updatePropuestaInicial(DetallePedido detallePedido,String sesionID) throws Exception;
	
	public int countEmpuje(String sessionId, DetallePedido detallePedido) throws Exception;
	
	/**
	 * Devuelve el numero de registros encontrados con cod_soc=13 (Centro Vegalsa) para el cod_centro indicado.
	 * 0 = No centro Vegalsa
	 * >0 = Centro Vegalsa
	 * 
	 * @param codCentro
	 * @return
	 * @throws Exception
	 */
	public int countCentroVegalsa(Long codCentro) throws Exception; 
	
	/**
	 * Obtiene la lista de registros de T_MIS_DETALLADO_VEGALSA para el centro. 
	 * @param codCentro
	 * @param idSession
	 * @return TODO
	 * @throws Exception
	 */
	public List<DetallePedido> getDetalladoPedidoVegalsa(Long codCentro) throws Exception;
	/**
	 * Devuelve el numero de registros con origen de carga de SIA  en la tabla T_DETALLADO_PEDIDO 
	 * para el centro y session pasada como parametro.
	 *  
	 * @param codCentro
	 * @return
	 * @throws Exception
	 */
	public Long countDetallePedidoFromSIA(Long codCentro, String sesionID) throws Exception;
	
	/**
	 * Obtiene la lista de los diferentes mapas de los registros de T_DETALLADO_PEDIDO 
	 * @param codCentro
	 * @param sesionID
	 * @return
	 * @throws Exception
	 */
	public List<MapaVegalsa> getMapasVegalsaByCenterAndIdSessionId(Long codCentro, String sesionID) throws Exception;
	
	List<PropuestaDetalladoVegalsa> resumenPropuestaVegalsa(Long codCentro, Long codMapa);

	/**
	 * Comprueba si existe un registro en T_DETALLADO_PEDIDO con el FLG_SIA = 'N'.
	 * 
	 * @param detalle
	 * @param string 
	 * @return
	 * @throws Exception
	 */
	public DetallePedido findDatosVegalsaModif(DetallePedidoModificados detalle, String idSession) throws Exception;

	/**
	 * Alta de registros modificados de Vegalsa en el GRID.
	 * 
	 * @param detPedido
	 * @param session 
	 * @return
	 * @throws DataIntegrityViolationException
	 * @throws DataAccessException
	 */
	public void insertVegalsaModif(DetallePedido detPedido, HttpSession session) throws DataIntegrityViolationException, DataAccessException, Exception;

	/**
	 * Modificación de registros modificados de Vegalsa en el GRID.
	 * 
	 * @param detallePedido
	 * @param session
	 * @return
	 * @throws DataAccessException
	 */
	public void updateVegalsaModif(DetallePedido detallePedido, HttpSession session) throws DataAccessException, Exception;
	
	/**
	 * Recuperacion de referencias sin PLU asignado y sin grupo de balanzas para consultar en el WS e insertar en T_MIS_ALARMAS_PLU.
	 * @param codCentro
	 * @param idSesion
	 * @throws Exception
	 */
	public List<DetallePedido> referenciasSinPLUAAniadir(Long codCentro,  String idSesion) throws Exception;
	/**
	 * Recuperacion de referencias sin PLU asignado.
	 * @param codCentro
	 * @param idSesion
	 * @throws Exception
	 */
	public List<DetallePedido> referenciasSinPLUAMostrar(Long codCentro,  String idSesion, Long seccionId) throws Exception;
	
}
