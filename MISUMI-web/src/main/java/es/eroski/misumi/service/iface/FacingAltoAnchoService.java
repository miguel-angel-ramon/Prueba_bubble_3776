package es.eroski.misumi.service.iface;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.facingVegalsaWS.ModificarMedidasFacingVegalsaRequestType;
import es.eroski.misumi.model.facingVegalsaWS.ModificarMedidasFacingVegalsaResponseType;

/**
 * Interface de acceso al Facing para centros VEGALSA.
 * @author BICAGAAN
 *
 */
public interface FacingAltoAnchoService{
	/**
	 * Invoca al WS de Modificación de Facing. 
	 * @param facingAltoAnchoRequest
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public ModificarMedidasFacingVegalsaResponseType modificarMedidasFacing(ModificarMedidasFacingVegalsaRequestType facingAltoAnchoRequest, HttpSession session) throws Exception;

	/**
	 * Indica si el centro está parametrizado.
	 * @param codCentro
	 * @param tipoPermiso
	 * @return
	 * @throws Exception
	 */
	public boolean isCentroParametrizado(Long codCentro, String tipoPermiso) throws Exception;	
}
