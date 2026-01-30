package es.eroski.misumi.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.model.facingVegalsaWS.ModificarMedidasFacingVegalsaRequestType;
import es.eroski.misumi.model.facingVegalsaWS.ModificarMedidasFacingVegalsaResponseType;
import es.eroski.misumi.dao.CentroParametrizadoDaoImpl;
import es.eroski.misumi.dao.iface.FacingAltoAnchoDao;
import es.eroski.misumi.service.iface.FacingAltoAnchoService;

@Service(value = "FacingAltoAnchoService")
public class FacingAltoAnchoServiceImpl extends CentroParametrizadoDaoImpl implements FacingAltoAnchoService {
	
    @Autowired
	private FacingAltoAnchoDao facingAltoAnchoDao;
    
	@Override
    public ModificarMedidasFacingVegalsaResponseType modificarMedidasFacing(ModificarMedidasFacingVegalsaRequestType facingMedidasVegalsaRequest , HttpSession session) throws Exception{
    	return facingAltoAnchoDao.modificarFacingAltoAncho(facingMedidasVegalsaRequest, session);
    }

	@Override
	public boolean isCentroParametrizado(Long codCentro, String tipoPermiso) throws Exception {
		return facingAltoAnchoDao.centroParametrizado(codCentro, tipoPermiso);
	}
	
}
