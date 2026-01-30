package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.QueHacerRefDao;
import es.eroski.misumi.model.QueHacerRef;
import es.eroski.misumi.service.iface.QueHacerRefService;

@Service(value = "QueHacerRefService")
public class QueHacerRefServiceImpl implements QueHacerRefService {
	
	@Autowired
	private QueHacerRefDao queHacerRefDao;
	
	@Override
	public QueHacerRef obtenerAccionRef(QueHacerRef pdaQueHacerRef) throws Exception {
		return this.queHacerRefDao.obtenerAccionRef(pdaQueHacerRef);
	}
	
	/*@Override
	public List<QueHacerRef> obtenerRefTextil(List<QueHacerRef> listPdaQueHacerRef) throws Exception {
		return this.queHacerRefDao.obtenerRefTextil(listPdaQueHacerRef);
	}*/
}
