package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VOfertaDao;
import es.eroski.misumi.model.VOferta;
import es.eroski.misumi.service.iface.VOfertaService;

@Service(value = "VOfertaService")
public class VOfertaServiceImpl implements VOfertaService {
	//private static Logger logger = LoggerFactory.getLogger(VOfertaServiceImpl.class);
	//private static Logger logger = Logger.getLogger(VOfertaServiceImpl.class);
    @Autowired
	private VOfertaDao vOfertaDao;
	
    @Override
	 public List<VOferta> findAll(VOferta vOferta) throws Exception {
		return this.vOfertaDao.findAll(vOferta);
	}
	
	@Override
	public VOferta findOne(VOferta vOferta) throws Exception {
		VOferta vOfertaRes = null;
		List<VOferta> listOferta = this.vOfertaDao.findAll(vOferta);
		if (!listOferta.isEmpty()){
			vOfertaRes = listOferta.get(0);
		}
		return vOfertaRes;

	}

}
