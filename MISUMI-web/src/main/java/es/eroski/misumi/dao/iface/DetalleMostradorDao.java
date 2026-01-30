package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.Cronometro;
import es.eroski.misumi.model.DetalladoMostradorInfo;
import es.eroski.misumi.model.DetalleMostrador;
import es.eroski.misumi.model.DetalleMostradorModificados;
import es.eroski.misumi.model.EstrucComercialMostrador;
import es.eroski.misumi.model.FiltrosDetalleMostrador;
import es.eroski.misumi.model.OfertaDetalleMostrador;
import es.eroski.misumi.model.ReferenciaNoGamaMostrador;
import es.eroski.misumi.model.VMisDetalladoMostrador;
import es.eroski.misumi.model.ui.Pagination;

public interface DetalleMostradorDao  {

	/**
	 * Borra la tabla temporal T_DETALLE_MOSTRADOR.
	 * @param sesionID
	 * @throws Exception
	 */
	public void deleteTemp(String sesionID) throws Exception;

	/**
	 * Carga la tabla temporal T_DETALLE_MOSTRADOR para mostrar los datos en el GRID.
	 * @param listToInsert
	 * @param sesionId
	 * @return
	 * @throws Exception
	 */
	public Long insertListIntoTemp(List<DetalleMostrador> listToInsert, String sesionId) throws Exception;
	
	/**
	 * Recupera los registros del nivel 2 si los hubiera relacionados con el Ident del nivel 1.
	 * 
	 * @param sesionID
	 * @param ident
	 * @return
	 * @throws Exception
	 */
	public List<VMisDetalladoMostrador> findDetalleNivel2Mostrador(String sessionId, Long ident) throws Exception;

	
	/**
	 * Obtiene de la tabla T_DETALLADO_MOSTRADOR los valores de Fechas de la cabecera.
	 * 
	 * @param codCentro
	 * @param sesionId
	 * @return
	 * @throws Exception
	 */
	public DetalladoMostradorInfo obtenerDatosCabecera(Long codCentro, String sesionId) throws Exception;
	
	/**
	 * Actualiza los datos de SIA en la tabla temporal "T_DETALLADO_MOSTRADOR".
	 * 
	 * @param listaDetalladoModif
	 * @param sessionId
	 * @throws Exception
	 */
	public void updateData(List<DetalleMostradorModificados> listaDetalladoModif, Long codCentro, String sessionId) throws Exception;
	
	/**
	 * Obtiene los codigos/denominacion correspondientes para los parámetros pasados.  
	 * @param codLoc
	 * @param codN2
	 * @param codN3
	 * @param codN4
	 * @return
	 */
	public EstrucComercialMostrador obtenerEstrCom(final Long codLoc, final Long codN2, final Long codN3, final Long codN4, final String tipoAprov);
	
	/**
	 * @author BICUGUAL
	 * @param detalleMostrador
	 * @return
	 * @throws Exception
	 */
	public List<DetalleMostrador> findDetalleMostradorSIA(FiltrosDetalleMostrador filtros) throws Exception;

	/**
	 * @author BICUGUAL
	 * @param codCentro
	 * @param sesionID
	 * @return
	 * @throws Exception
	 */
	public List<VMisDetalladoMostrador> findDetalleNivel1Mostrador(Long codCentro, String sesionID) throws Exception;

	/**
	 * @author BICUGUAL
	 * @param codCentro
	 * @param sesionID
	 * @return
	 * @throws Exception
	 */
	public Cronometro calculoCronometroYNumeroHorasLimite(Long codCentro, String sesionID) throws Exception;
	
	/**
	 * @author BICUGUAL
	 * @param codCentro
	 * @return
	 * @throws Exception
	 */
	public List<String> getFestivosByCentro(Long codCentro) throws Exception;
	
	/**
	 * Recupera la lista de ofertas de una referencia - centro para una session
	 * @author BICUGUAL
	 * @param codCentro
	 * @param referencia
	 * @param sesionId
	 * @return
	 * @throws Exception
	 */
	public List<OfertaDetalleMostrador> getOfertas (Long codCentro, Long referencia, String sesionId) throws Exception;

	/**
	 * Inserta en batch en la tabla temporal T_MIS_REF_NO_GAMA_MOSTRADOR
	 * @param lstReferencias
	 * @param sesionId
	 * @throws Exception
	 */
	public void insertarTempListadoReferenciasNoGama(final List<ReferenciaNoGamaMostrador> lstReferencias, String sesionId) throws Exception;
	
	/**
	 * Elimina los registros del idSession de la tabla temporal T_MIS_REF_NO_GAMA_MOSTRADOR
	 * @param sesionId
	 */
	public void deleteTempListadoReferenciasNoGama(String sesionId);
	
	/**
	 * Obtiene una pagina para cargar la lista de referencias no gama de mostrador
	 * @param sesionId
	 * @param codCentro
	 * @param pagination
	 * @return
	 */
	public List<ReferenciaNoGamaMostrador> getReferenciasNoGamaMostrador(String sesionId, Long cod_centro, Pagination pagination);
	
	/**
	 * Devuelve la count de registros totales para una session
	 * @param sesionId
	 * @return
	 */
	public Long getLstReferenciasNoGamaCount(String sesionId);

}
