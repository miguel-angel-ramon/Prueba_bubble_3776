package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.DetalladoMostradorRedondeo;
import es.eroski.misumi.model.DetalladoMostradorSIA;
import es.eroski.misumi.model.DetalladoMostradorSIALista;
import es.eroski.misumi.model.EstrucComercialMostrador;
import es.eroski.misumi.model.FiltrosDetalleMostrador;
import es.eroski.misumi.model.GestionEuros;
import es.eroski.misumi.model.GestionEurosSIA;
import es.eroski.misumi.model.ReferenciaNoGamaMostrador;
import es.eroski.misumi.model.VMisDetalladoMostrador;

public interface DetalladoMostradorSIADao  {
	
	/**
	 * Actualiza los datos de SIA invocando al procedimiento P_ACTUALIZAR().
	 * 
	 * @param lista
	 * @param codCentro
	 * @return
	 * @throws Exception
	 */
	public List<DetalladoMostradorSIA> actualizarDatosGridSIA(List<VMisDetalladoMostrador> lista, Long codCentro) throws Exception;
	
	/**
	 * Actualiza los datos del GRID en SIA a través del procedimiento "P_ACTUALIZAR()".
	 * 
	 * @param detalladoMostradorSIALista
	 * @return
	 * @throws Exception
	 */
	public DetalladoMostradorSIALista actualizarDetalladoSIA(List<DetalladoMostradorSIA> detalladoMostradorSIALista) throws Exception;
	
	/**
	 * Obtiene los codigos/denominacion correspondientes para los parámetros pasados.  
	 * @param codLoc
	 * @param codN2
	 * @param codN3
	 * @param codN4
	 * @param tipoAprov
	 * @return
	 */
	public EstrucComercialMostrador obtenerEstrCom(final Long codLoc, final Long codN2, final Long codN3, final Long codN4, final String tipoAprov);
	
	
	public DetalladoMostradorRedondeo redondeoDetallado(DetalladoMostradorSIA detalladoMostradorSIA) throws Exception;
	public DetalladoMostradorSIALista referenciaNueva(DetalladoMostradorSIA detalladoMostradorSIA) throws Exception;
	
	public GestionEurosSIA gestionEuros(GestionEuros gestionEuros) throws Exception;

	
	/**
	 * Obtiene los datos del GRID invocando al procedimiento de SIA "P_CONSULTA()".
	 * @author BICUGUAL
	 * 
	 * @param filtros
	 * @return
	 * @throws Exception
	 */
	public DetalladoMostradorSIALista consultaDetallado(FiltrosDetalleMostrador filtros) throws Exception;

	/**
	 * Obtiene la lista de referencias no gama invocando al PL/SQL P_REF_NO_GAMA
	 * @param codCentro
	 * @param descripcion
	 * @param seccion
	 * @param categoria
	 * @param subcategoria
	 * @param segmento
	 * @param gamaLocal
	 * @return
	 */
	public List<ReferenciaNoGamaMostrador> callProcedureRefNoGama(Long codCentro, String descripcion, String seccion, String categoria,
			String subcategoria, String segmento, String gamaLocal);
	
	/**
	 * Obtiene la lista de referencias no gama invocando al PL/SQL P_REF_NO_GAMA
	 * @param codCentro
	 * @param descripcion
	 * @param seccion
	 * @param categoria
	 * @param subcategoria
	 * @param segmento
	 * @param gamaLocal
	 * @return
	 */
	public List<ReferenciaNoGamaMostrador> callProcedureRefNoGamaNew(Long codCentro, String descripcion, String seccion,
			String categoria, String subcategoria, String segmento, String gamaLocal);

}
