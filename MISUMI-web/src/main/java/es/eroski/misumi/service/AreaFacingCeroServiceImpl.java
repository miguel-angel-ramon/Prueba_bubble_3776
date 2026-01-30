package es.eroski.misumi.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.AreaFacingCeroSIADao;
import es.eroski.misumi.model.AreaFacingCero;
import es.eroski.misumi.model.AreaFacingCeroSIALista;
import es.eroski.misumi.model.ECAreaFacingCero;
import es.eroski.misumi.service.iface.AreaFacingCeroService;

@Service(value = "AreaFacingCeroService")
public class AreaFacingCeroServiceImpl implements AreaFacingCeroService{
	
	@Autowired
	private AreaFacingCeroSIADao areaFacingCeroDao;

	@Override
	public AreaFacingCero getAreasFacingCero(Long codCentro) throws Exception {
		
		AreaFacingCeroSIALista areas = areaFacingCeroDao.consultaAreas(codCentro);
		
		AreaFacingCero area = new AreaFacingCero();
		
		area.setLstAreaReferenciasFacingCero(null != areas ? areas.getAreas() : new ArrayList<ECAreaFacingCero>());
		area.setCodError(areas.getCodError());
		area.setMensajeError(areas.getMensajeError());
		
		return area;
	}
	
}
