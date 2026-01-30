package es.eroski.misumi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.FechaProximasEntregasDao;
import es.eroski.misumi.model.FechaProximaEntregaRef;
import es.eroski.misumi.service.iface.FechaProximasEntregasService;

@Service(value = "FechaProximasEntregasService")
public class FechaProximasEntregasServiceImpl implements FechaProximasEntregasService{
	@Autowired
	private FechaProximasEntregasDao fechaProximasEntregasDao;
	
	public FechaProximaEntregaRef getFechaProximasEntregas(final Long codLoc, final Long codArt) {
		return fechaProximasEntregasDao.getFechaProximasEntregas(codLoc, codArt);
	}
}
