package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VMapaAprovFestivoDao;
import es.eroski.misumi.model.VMapaAprovFestivo;
import es.eroski.misumi.service.iface.VMapaAprovFestivoService;

@Service(value = "VMapaAprovFestivoService")
public class VMapaAprovFestivoServiceImpl implements VMapaAprovFestivoService {
	//private static Logger logger = LoggerFactory.getLogger(VMapaAprovFestivoServiceImpl.class);
    @Autowired
	private VMapaAprovFestivoDao vMapaAprovFestivoDao;
	
    @Override
	 public List<VMapaAprovFestivo> findAll(VMapaAprovFestivo vMapaAprovFestivo) throws Exception {
		return this.vMapaAprovFestivoDao.findAll(vMapaAprovFestivo);
	}
	
    @Override
	 public Long count(VMapaAprovFestivo vMapaAprovFestivo) throws Exception {
		return this.vMapaAprovFestivoDao.count(vMapaAprovFestivo);
	}

    @Override
	public VMapaAprovFestivo findOne(VMapaAprovFestivo vMapaAprovFestivo) throws Exception {
		VMapaAprovFestivo vMapaAprovFestivoRes = null;
		List<VMapaAprovFestivo> listVMapaAprovFestivo = this.vMapaAprovFestivoDao.findAll(vMapaAprovFestivo);
		if (!listVMapaAprovFestivo.isEmpty()){
			vMapaAprovFestivoRes = listVMapaAprovFestivo.get(0);
		}
		return vMapaAprovFestivoRes;

	}

}
