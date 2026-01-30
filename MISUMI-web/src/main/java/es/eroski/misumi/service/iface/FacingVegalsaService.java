package es.eroski.misumi.service.iface;

import java.util.List;
import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaRequestType;
import es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaResponseType;
import es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaRequestType;
import es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaResponseType;
import es.eroski.misumi.model.facingVegalsaWS.ModificarMedidasFacingVegalsaRequestType;
import es.eroski.misumi.model.facingVegalsaWS.ModificarMedidasFacingVegalsaResponseType;
import es.eroski.misumi.model.referenciaCentroWS.ConsultaEtiquetaFacingRequest;
import es.eroski.misumi.model.referenciaCentroWS.ConsultaEtiquetaFacingResponse;
import es.eroski.misumi.model.FacingVegalsaRequest;
import es.eroski.misumi.model.FacingVegalsaResponse;
import es.eroski.misumi.model.ui.Pagination;

/**
 * Interface de acceso al Facing para centros VEGALSA.
 * @author BICAGAAN
 *
 */
public interface FacingVegalsaService{

	/**
	 * 
	 * @param vDatosDiarioArt
	 * @param pagination
	 * @return
	 * @throws Exception
	 */
	public List<FacingVegalsaResponse> findAll(FacingVegalsaRequest vDatosDiarioArt, Pagination pagination) throws Exception  ;

	/**
	 * Invoca al WS de Consulta de Facing.
	 * @param facingVegalsaRequest
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public ConsultaFacingVegalsaResponseType consultarFacing(ConsultaFacingVegalsaRequestType facingVegalsaRequest,  HttpSession session) throws Exception;
	
	/**
	 * Invoca al WS de Modificación de Facing. 
	 * @param facingVegalsaRequest
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public ModificarFacingVegalsaResponseType modificarFacing(ModificarFacingVegalsaRequestType facingVegalsaRequest,  HttpSession session) throws Exception;

	/**
	 * Comprueba si el centro tiene permiso de modificar el facing.
	 * @param codCentro
	 * @return
	 * @throws Exception
	 */
    public boolean isFacingModificable(Long codCentro) throws Exception;
  
    /**
     * Invoca al WS de obtención del Tipo de Etiqueta de Facing.
     * @param tipoEtiquetaRequest
     * @param session
     * @return
     * @throws Exception
     */
	public ConsultaEtiquetaFacingResponse obtenerTipoEtiquetaFacing(ConsultaEtiquetaFacingRequest tipoEtiquetaRequest, HttpSession session) throws Exception;

}
