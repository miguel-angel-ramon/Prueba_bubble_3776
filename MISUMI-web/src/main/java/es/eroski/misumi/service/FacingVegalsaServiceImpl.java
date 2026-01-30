package es.eroski.misumi.service;

import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaRequestType;
import es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaResponseType;
import es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaRequestType;
import es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaResponseType;
import es.eroski.misumi.model.referenciaCentroWS.ConsultaEtiquetaFacingRequest;
import es.eroski.misumi.model.referenciaCentroWS.ConsultaEtiquetaFacingResponse;
import es.eroski.misumi.dao.iface.FacingVegalsaDao;
import es.eroski.misumi.dao.iface.ReferenciaCentroDao;
import es.eroski.misumi.model.FacingVegalsaRequest;
import es.eroski.misumi.model.FacingVegalsaResponse;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.FacingVegalsaService;

@Service(value = "FacingVegalsaService")
public class FacingVegalsaServiceImpl implements FacingVegalsaService {
	
    @Autowired
	private FacingVegalsaDao facingVegalsaDao;
    
    @Autowired
	private ReferenciaCentroDao referenciaCentroDao;
    
	@Override
	  public List<FacingVegalsaResponse> findAll(FacingVegalsaRequest request, Pagination pagination) throws Exception {
		return this.facingVegalsaDao.findAll(request, pagination);
	}
	
	@Override
	public ConsultaFacingVegalsaResponseType consultarFacing(ConsultaFacingVegalsaRequestType facingVegalsaRequest,  HttpSession session) throws Exception{
    	return facingVegalsaDao.consultarFacingVegalsa(facingVegalsaRequest, session);
    }
    
	@Override
    public ModificarFacingVegalsaResponseType modificarFacing(ModificarFacingVegalsaRequestType facingVegalsaRequest , HttpSession session) throws Exception{
    	return facingVegalsaDao.modificarFacingVegalsa(facingVegalsaRequest, session);
    }

	@Override
	public boolean isFacingModificable(Long codCentro) throws Exception {
		return facingVegalsaDao.facingModificable(codCentro);
	}

	@Override
	public ConsultaEtiquetaFacingResponse obtenerTipoEtiquetaFacing(ConsultaEtiquetaFacingRequest tipoEtiquetaRequest,
			HttpSession session) throws Exception {
		return referenciaCentroDao.consultaTipoEtiquetaFacing(tipoEtiquetaRequest, session);
	}

}
