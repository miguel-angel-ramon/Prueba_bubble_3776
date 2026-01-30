package es.eroski.misumi.service.iface;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.Cronometro;
import es.eroski.misumi.model.DetalladoMostradorInfo;
import es.eroski.misumi.model.DetalleMostrador;
import es.eroski.misumi.model.EstrucComercialMostrador;
import es.eroski.misumi.model.OfertaDetalleMostrador;
import es.eroski.misumi.model.ReferenciaNoGamaMostrador;
import es.eroski.misumi.model.VMisDetalladoMostrador;
import es.eroski.misumi.model.VMisDetalladoMostradorWrapperList;
import es.eroski.misumi.model.ui.Page;

public interface DetalladoMostradorService {

	/**
	 * Borrar la tabla temporal "T_DETALLADO_MOSTRADOR" para la sesión dada.
	 * 
	 * @param sesionID
	 */
	public void borrarTablaTemp(String sesionId) throws Exception;

	/**
	 * 
	 * @param listToInsert
	 * @param sesionID
	 * @return
	 * @throws Exception
	 */
	public Long cargarTablaTemp(List<DetalleMostrador> listToInsert, String sesionId) throws Exception;

	/**
	 * Buscar los datos del detallado mostrador que se presentarán en el GRID del Nivel 2 para el Identificador dado.
	 * 
	 * @param sesionID
	 * @param ident
	 * @return
	 */
	public List<VMisDetalladoMostrador> findDetalleNivel2Mostrador(String sesionId, Long ident) throws Exception;

	/**
	 * Guardar en SIA los datos modificados en el GRID.
	 * 
	 * @param listToUpdate
	 * @param codCentro
	 * @param sesionId
	 * @return
	 * @throws Exception
	 */
	public void actualizarDatosGrid(VMisDetalladoMostradorWrapperList listToUpdate, Long codCentro, String sesionId) throws Exception;
	
	/**
	 * Obtener los datos de la cabecera que aparecen junto a los totales de EUROS y CAJAS. (Fecha Entrega, Fecha Sgte Pedido, ...)
	 * @param codCentro
	 * @param sesionId
	 * @return
	 * @throws Exception
	 */
	public DetalladoMostradorInfo obtenerDatosCabecera(Long codCentro, String sesionId) throws Exception;
	
	/**
	 * Obtener el stock de las referencias que se van a mostrar
	 * @param codCentro
	 * @param rows
	 * @param sesionId
	 * @return List<VMisDetalladoMostrador> rows
	 * @throws Exception
	 */
	public List<VMisDetalladoMostrador> setStock(Long codCentro, List<VMisDetalladoMostrador> rows, HttpSession session) throws Exception;

	/**
	 * Invoca al procedimiento de SIA para recuperar el codigo/denominacion para cargar los combos de
	 * Estructura Comercial y la Fecha Espejo de referencia para la consulta de referencias del GRID.  
	 * @param codCentro
	 * @param codN2
	 * @param codN3
	 * @param codN4
	 * @param tipoAprov
	 * @return
	 * @throws Exception
	 */
	public EstrucComercialMostrador findEstructuraComercial(Long codCentro, Long codN2, Long codN3, Long codN4, String tipoAprov) throws Exception;
	
	/**
	 * @author BICUGUAL
	 * @param centro
	 * @param codArticulo
	 * @param pdteRecibirVenta
	 * @param unidadesCaja
	 * @param propuestaPedido
	 * @return
	 * @throws Exception
	 */
	public VMisDetalladoMostrador redondeoDetallado(Long codCentro, Long codArticulo, Long pdteRecibirVenta,
			Double unidadesCaja, Long propuestaPedido) throws Exception;

	/**
	 * @author BICUGUAL
	 * @param codCentro
	 * @param seccion
	 * @param categoria
	 * @param subcategoria
	 * @param segmento
	 * @param tipoAprov
	 * @param soloVenta
	 * @param gamaLocal
	 * @param diaEspejo
	 * @param sesionId
	 * @return
	 * @throws Exception
	 * 
	 * Invoca al DAO para el BORRADO, CONSULTA(SIA [pk_apr_det_mostrador_misumi.p_consulta()]) y CARGA de los datos en la tabla T_DETALLADO_MOSTRADOR. 
	 */
	public Long cargarDetalladoMostrador(Long codCentro, Long seccion, Long categoria, Long subcategoria, Long segmento
										, String tipoAprov, String soloVenta, String gamaLocal, Date diaEspejo, String sesionId) throws Exception;

	/**
	 * @author BICUGUAL
	 * @param codCentro
	 * @param sesionID
	 * @return
	 * @throws Exception
	 */
	public Cronometro calculoCronometroYNumeroHorasLimite(Long codCentro, String sesionID) throws Exception;

	/**
	 * Obtiene referencias de la vista de detallado para nivel 1
	 * @author BICUGUAL
	 * @param codCentro
	 * @param sesionId
	 * @return
	 * @throws Exception
	 */
	public List<VMisDetalladoMostrador> findDetalleNivel1Mostrador(Long codCentro, String sesionId) throws Exception;
	
	/**
	 * Obtiene los dias festivos del centro
	 * @author BICUGUAL
	 * @param codCentro
	 * @return
	 * @throws Exception
	 */
	public List<String> getFestivos(Long codCentro) throws Exception;
	
	/**
	 * Obtiene la lista de ofertas
	 * @author BICUGUAL
	 * @param codCentro
	 * @param referencia
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public List<OfertaDetalleMostrador> getOfertasDetalleMostrador(Long codCentro, Long referencia, String sessionId) throws Exception;
	
	/**
	 * Exportacion a Excel de los registros que se estan filtrando en el grid
	 * @param cabecera
	 * @param rows
	 * @param response
	 * @throws Exception
	 */
	public void exportDetalleMostrador(String cabecera, String rows, HttpServletResponse response) throws Exception;
	
	
	/**
	 * Carga de la tabla temporal T_MIS_REF_NO_GAMA_MOSTRADOR para lanzar las consultas de referencias no gama
	 * y devuelve el numero de registros cargados
	 * @author BICUGUAL
	 * @param codCentro
	 * @param descripcion
	 * @param seccion
	 * @param categoria
	 * @param subcategoria
	 * @param segmento
	 * @param gamaLocal
	 * @param sesionId
	 * @return
	 * @throws Exception
	 */
	public Integer loadReferenciasNoGama(Long codCentro, String descripcion, String seccion, String categoria, String subcategoria, String segmento,
			String gamaLocal, String sesionId) throws Exception;

	/**
	 * Obtiene los registros para cargar una pagina de las Referencias No Gama Mostrador
	 * @param sesionId
	 * @param codCentro
	 * @param page
	 * @param rows
	 * @return
	 */
	public Page<ReferenciaNoGamaMostrador> getLstReferenciasNoGama(String sesionId,Long codCentro, Long page, Long rows);

}
