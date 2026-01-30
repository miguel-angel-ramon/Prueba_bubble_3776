package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.HistoricoVentaMediaDao;
import es.eroski.misumi.model.HistoricoVentaMedia;
import es.eroski.misumi.service.iface.HistoricoVentaMediaService;

@Service(value = "HistoricoVentaMediaService")
public class HistoricoVentaMediaServiceImpl implements HistoricoVentaMediaService {
	//private static Logger logger = LoggerFactory.getLogger(HistoricoVentaMediaServiceImpl.class);
    @Autowired
	private HistoricoVentaMediaDao historicoVentaMediaDao;
	
    /*@Override
	 public List<HistoricoVentaMedia> findAll(HistoricoVentaMedia historicoVentaMedia) throws Exception {
		return this.historicoVentaMediaDao.findAll(historicoVentaMedia);
	}
	
    @Override
	public HistoricoVentaMedia findOne(HistoricoVentaMedia historicoVentaMedia) throws Exception {
		HistoricoVentaMedia historicoVentaMediaRes = null;
		List<HistoricoVentaMedia> listHistoricoVentaMedia = this.historicoVentaMediaDao.findAll(historicoVentaMedia);
		if (!listHistoricoVentaMedia.isEmpty()){
			historicoVentaMediaRes = listHistoricoVentaMedia.get(0);
		}
		return historicoVentaMediaRes;

	}*/

    @Override
	 public List<HistoricoVentaMedia> findAllAcumuladoRef(HistoricoVentaMedia historicoVentaMedia) throws Exception {
		return this.historicoVentaMediaDao.findAllAcumuladoRef(historicoVentaMedia);
	}
	
    @Override
	public HistoricoVentaMedia findOneAcumuladoRef(HistoricoVentaMedia historicoVentaMedia) throws Exception {
		HistoricoVentaMedia historicoVentaMediaRes = null;
		List<HistoricoVentaMedia> listHistoricoVentaMedia = this.historicoVentaMediaDao.findAllAcumuladoRef(historicoVentaMedia);
		if (!listHistoricoVentaMedia.isEmpty()){
			historicoVentaMediaRes = listHistoricoVentaMedia.get(0);
		}
		return historicoVentaMediaRes;

	}
}
