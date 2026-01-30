package es.eroski.misumi.service.iface;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.Agrupacion;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.Cronometro;
import es.eroski.misumi.model.DetalladoContadores;
import es.eroski.misumi.model.DetalladoRedondeo;
import es.eroski.misumi.model.DetalladoSIA;
import es.eroski.misumi.model.DetallePedido;
import es.eroski.misumi.model.DetallePedidoLista;
import es.eroski.misumi.model.DetallePedidoModificados;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.GestionEuros;
import es.eroski.misumi.model.GestionEurosRefs;
import es.eroski.misumi.model.GestionEurosSIA;
import es.eroski.misumi.model.OptionSelectBean;
import es.eroski.misumi.model.PropuestaDetalladoVegalsa;
import es.eroski.misumi.model.ReferenciasSinPLU;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.VBloqueoEncargosPiladas;
import es.eroski.misumi.model.ui.Pagination;

public interface DetalladoPedidoService {

	Agrupacion findAll(Agrupacion agrupacion) throws Exception;
	
	public DetalladoContadores loadContadoresDetallado(DetallePedido detallePedido,  HttpSession session, HttpServletResponse response) throws Exception;

	List<DetallePedido> findDetallePedido(Centro centro,String sessionId, HttpSession session) throws Exception;

	List<DetallePedido> findSessionInfo(DetallePedido detallePedido, String sesionID, Pagination pagination, String filtrosTabla) throws Exception;

	Double sumarEurosIniciales(DetallePedido detallePedido,String sesionID) throws Exception;
	Double sumarEurosFinales(DetallePedido detallePedido,String sesionID) throws Exception;
	Double sumarCajasIniciales(DetallePedido detallePedido,String sesionID) throws Exception;
	Double sumarCajasFinales(DetallePedido detallePedido,String sesionID) throws Exception;
	
	Long countSessionInfo(DetallePedido detallePedido, String sesionID, String filtrosTabla) throws Exception;

	List<VAgruComerRef> findFilter(VAgruComerRef vAgruCommerRef, String sessionId) throws Exception;

	DetallePedido add(DetallePedido detalladoPedido, String sessionId) throws Exception;

	void resetSessionData(String sessionId) throws Exception;

	void updateGridState(DetallePedido detalladoPedido, HttpSession session) throws Exception;
	
	public void updateGridStateGestionEurosRefs(GestionEuros gestionEuros, String sessionId) throws Exception;
	
	void updateOnlyGridState(DetallePedido detalladoPedido, HttpSession session , Long estadoGrid, Long nuevoEstadoGrid) throws Exception;

	List<DetallePedidoModificados> saveData(DetallePedido detalladoPedido,
			String sessionId, Centro centro, HttpSession session) throws Exception;

	Long sumBoxes(DetallePedido detalladoPedido, String sessionId) throws Exception;
	
	List<GenericExcelVO> findSessionInfoExcel(DetallePedido detallePedido,String[] columnModel, String sesionID, Pagination pagination) throws Exception;

	String comprobarReferenciaNueva(Long codCentro, Long referencia, Date fechaGen) throws Exception;
	
	List<DetallePedido> findAllTextilN2ByLote(DetallePedido detallePedido) throws Exception;
	
	VBloqueoEncargosPiladas getNextDayDetalladoPedido(Long codArt, Long centerId) throws Exception;
	
	DetalladoRedondeo redondeoDetallado(DetalladoSIA detalladoSIA) throws Exception;
	
	DetallePedido actualizarPrecioCostoFinal(DetallePedido detallePedido, String sessionId) throws Exception;

	DetallePedido findOne(Long referencia, String codCentro, String sesionID) throws Exception;
	
	DetallePedidoLista referenciaNuevaSIA(Long referencia, Long codCentro) throws Exception;
	
	public Cronometro calculoCronometroYNumeroHorasLimite(DetallePedido detallePedido, String sesionID) throws Exception;
	
	public Long countFlgPropuesta (String sessionId, Long center, Long seccion, Long categoria) throws Exception;
	
	public GestionEurosSIA gestionEuros(final GestionEuros gestionEuros) throws Exception;
	
	public List<GestionEurosRefs> findSessionInfoGestionEurosRefs(DetallePedido detallePedido,String sesionID, Pagination pagination) throws Exception;
	
	public void updatePrevioCalcular(DetallePedido detallePedido,String sesionID) throws Exception;
	
	public void updatePropuestaInicial(DetallePedido detallePedido,String sesionID) throws Exception;
	
	public String mostrarEmpuje(String sessionId, DetallePedido detallePedido) throws Exception;

	public int countModificados(String sesionID) throws Exception;
	
	/**
	 * Determina si el centro pasado como parametro es un centro Vegalsa (true) o no (false) 
	 * @param codCentro
	 * @return
	 * @throws Exception
	 */
	public Boolean isCentroVegalsa(Long codCentro) throws Exception;
	
	
	/**
	 * Elimina los registros de T_DETALLADO_PEDIDO para el centro y la session pasada como parametros y carga los registros
	 * de nuevo con los valores obtenidos del detallado de SIA y del detallado de Vegalsa si procede (isCentroVegalsa=true)
	 * El metodo devuelve el numero de registros insertados en T_DETALLADO_PEDIDO.
	 * @param idCentro
	 * @param isCentroVegalsa
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public int loadDetalladoPedido (Long idCentro, Boolean isCentroVegalsa, HttpSession session) throws Exception;

	/**
	 * Determina si existen registros con Origen de carga SIA en el detallado del centro para la sesion (true) o no (false) 
	 * @param codCentro
	 * @param sesionID
	 * @return
	 * @throws Exception
	 */
	public Boolean hasAnyRecordFromSIA(Long codCentro, String sesionID) throws Exception;

	/**
	 * Obtiene la lista de Mapas para cargar el selector de mapas de detallado.
	 * @param codCenter
	 * @param sessionId
	 * @return
	 * @throws Exception 
	 */
	public List<OptionSelectBean> getLstMapasByCenterAndIdSession(Long codCenter, String sessionId) throws Exception;

	/**
	 * Obtiene la lista para cargar la tabla del Popup de Detallado para centros Vegalsa.
	 * @param codCenter
	 * @param sessionId
	 * @return
	 * @throws Exception 
	 */
	List<PropuestaDetalladoVegalsa> resumenPropuestaVegalsa(Long codCentro, Long codMapa);

	/**
	 * Gestion del alta/modificacion de datos en la tabla T_MIS_DETALLADO_VEGALSA_MODIF.
	 * Determina tambien el icono que se mostrara en el grid de pantalla despues de guardar.
	 * 
	 * @param detallePedidoModif
	 * @param session 
	 * @throws Exception
	 */
	public List<DetallePedidoModificados> gestionDatosVegalsaModif(List<DetallePedidoModificados> detallePedidoModif, HttpSession session) throws Exception;
	
	/**
	 * Actualización del estado, cantidades, ... de los registros modificados.
	 * @param listaDetalladoModif
	 * @param sessionId
	 * @throws Exception
	 */
	public void updateDatosVegalsa(final List<DetallePedidoModificados> listaDetalladoModif, final String sessionId) throws Exception;	
	
	/**
	 * Recuperacion de referencias sin PLU asignado.
	 * @param codCentro
	 * @param idSesion
	 * @param seccionId
	 * @throws Exception
	 */
	public List<ReferenciasSinPLU> loadReferenciasSinPLU(Long codCentro,  String idSesion, Long seccionId) throws Exception;
	
	/**
	 * Guardar de referencias sin PLU asignado.
	 * @param listaReferenciasSinPlu
	 * @throws Exception
	 */
	public List<ReferenciasSinPLU> saveReferenciasSinPLU(List<ReferenciasSinPLU> listaReferenciasSinPlu, String nombreUsuario) throws Exception;
}
