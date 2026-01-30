package es.eroski.misumi.dao.iface;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.facingVegalsaWS.ModificarMedidasFacingVegalsaRequestType;
import es.eroski.misumi.model.facingVegalsaWS.ModificarMedidasFacingVegalsaResponseType;

public interface FacingAltoAnchoDao extends CentroParametrizadoDao {

	/**
	 * Modificar el alto x ancho del facing a través del WS.
	 * @param facingAltoAnchoRequest
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public ModificarMedidasFacingVegalsaResponseType modificarFacingAltoAncho(ModificarMedidasFacingVegalsaRequestType facingAltoAnchoRequest, HttpSession session) throws Exception;

}
