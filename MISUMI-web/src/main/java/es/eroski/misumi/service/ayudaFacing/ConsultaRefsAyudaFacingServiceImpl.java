package es.eroski.misumi.service.ayudaFacing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.ayudaFacing.iface.ConsultaRefsAyudaFacingSIADao;
import es.eroski.misumi.model.pda.ayudaFacing.RefAyudaFacingLista;
import es.eroski.misumi.service.ayudaFacing.iface.ConsultaRefsAyudaFacingService;

@Service
public class ConsultaRefsAyudaFacingServiceImpl implements ConsultaRefsAyudaFacingService{

	@Autowired
	private ConsultaRefsAyudaFacingSIADao consultaRefsAyudaFacingDao;
	
	public RefAyudaFacingLista obtenerListaRefsAyudaFacing(Long centro, Long referencia){
		return consultaRefsAyudaFacingDao.getRefsAyudaFacing(centro, referencia);
	}
	
}
