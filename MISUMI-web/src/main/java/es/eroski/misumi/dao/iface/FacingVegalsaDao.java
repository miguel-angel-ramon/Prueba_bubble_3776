package es.eroski.misumi.dao.iface;

import java.util.List;
import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.FacingVegalsaRequest;
import es.eroski.misumi.model.FacingVegalsaResponse;
import es.eroski.misumi.model.ui.Pagination;

import es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaRequestType;
import es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaResponseType;
import es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaRequestType;
import es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaResponseType;
import es.eroski.misumi.model.referenciaCentroWS.ConsultaEtiquetaFacingRequest;
import es.eroski.misumi.model.referenciaCentroWS.ConsultaEtiquetaFacingResponse;

public interface FacingVegalsaDao  {
	/**
	 * 
	 * @param request
	 * @param pagination
	 * @return
	 * @throws Exception
	 */
	public List<FacingVegalsaResponse> findAll(FacingVegalsaRequest request, Pagination pagination) throws Exception ;
	/**
	 * 
	 * @param facingVegalsaRequest
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public ConsultaFacingVegalsaResponseType consultarFacingVegalsa(ConsultaFacingVegalsaRequestType facingVegalsaRequest, HttpSession session) throws Exception;
	/**
	 * 
	 * @param facingVegalsaRequest
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public ModificarFacingVegalsaResponseType modificarFacingVegalsa(ModificarFacingVegalsaRequestType facingVegalsaRequest, HttpSession session) throws Exception;
	/**
	 * 
	 * @param codCentro
	 * @return
	 */
	public boolean facingModificable(Long codCentro);
}
