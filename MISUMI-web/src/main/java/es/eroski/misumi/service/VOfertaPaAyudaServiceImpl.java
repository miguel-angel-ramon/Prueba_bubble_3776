package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VOfertaPaAyudaDao;
import es.eroski.misumi.model.VOfertaPaAyuda;
import es.eroski.misumi.service.iface.VOfertaPaAyudaService;

@Service(value = "VOfertaPaAyudaService")
public class VOfertaPaAyudaServiceImpl implements VOfertaPaAyudaService {
	//private static Logger logger = LoggerFactory.getLogger(VOfertaPaAyudaServiceImpl.class);
	//private static Logger logger = Logger.getLogger(VOfertaPaAyudaServiceImpl.class);
    @Autowired
	private VOfertaPaAyudaDao vOfertaPaAyudaDao;
	
	@Override
	 public List<VOfertaPaAyuda> findCountNOVigentes(VOfertaPaAyuda vOfertaPaAyuda, int vRow) throws Exception  {
		 return this.vOfertaPaAyudaDao.findCountNOVigentes(vOfertaPaAyuda, vRow); 
	 }
	
	@Override
	public VOfertaPaAyuda findOfertaPasadaMasReciente(VOfertaPaAyuda vOfertaPaAyuda) throws Exception {
		VOfertaPaAyuda vOfertaPaAyudaRes = null;
		List<VOfertaPaAyuda> listOferta = this.vOfertaPaAyudaDao.findAllNoVigentesCentro(vOfertaPaAyuda);
		if (!listOferta.isEmpty()){
			vOfertaPaAyudaRes = listOferta.get(0);
		}
		return vOfertaPaAyudaRes;

	}
}
