package es.eroski.misumi.dao.iface;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.referenciaCentroWS.ConsultaEtiquetaFacingRequest;
import es.eroski.misumi.model.referenciaCentroWS.ConsultaEtiquetaFacingResponse;

public interface ReferenciaCentroDao  {
	/**
	 * 
	 * @param tipoEtiquetaRequest
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public ConsultaEtiquetaFacingResponse consultaTipoEtiquetaFacing(ConsultaEtiquetaFacingRequest tipoEtiquetaRequest, HttpSession session) throws Exception;
}
