package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VBloqueoEncargosPiladasDao;
import es.eroski.misumi.model.VBloqueoEncargosPiladas;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.VBloqueoEncargosPiladasService;
import es.eroski.misumi.util.Constantes;

@Service(value = "VBloqueoEncargosPiladasService")
public class VBloqueoEncargosPiladasServiceImpl implements VBloqueoEncargosPiladasService {
	//private static Logger logger = LoggerFactory.getLogger(VBloqueoEncargosPiladasServiceImpl.class);
    
	@Autowired
	private VBloqueoEncargosPiladasDao vBloqueoEncargosPiladasDao;
	
    @Override
	 public List<VBloqueoEncargosPiladas> findAll(VBloqueoEncargosPiladas vBloqueoEncargosPiladas) throws Exception {
    	vBloqueoEncargosPiladas.setModo(Constantes.BLOQUEOS_MODO_FECHA_ENTREGA);
		return this.vBloqueoEncargosPiladasDao.findAll(vBloqueoEncargosPiladas);
	}
    
    @Override
	 public List<VBloqueoEncargosPiladas> findAllPaginate(VBloqueoEncargosPiladas vBloqueoEncargosPiladas, Pagination pagination) throws Exception {
    	vBloqueoEncargosPiladas.setModo(Constantes.BLOQUEOS_MODO_FECHA_ENTREGA);
		return this.vBloqueoEncargosPiladasDao.findAllPaginate(vBloqueoEncargosPiladas, pagination);
	}
    
    @Override
    public List<VBloqueoEncargosPiladas> findMotivosRefBloqueada(VBloqueoEncargosPiladas vBloqueoEncargosPiladas, Pagination pagination) throws Exception {
    	vBloqueoEncargosPiladas.setModo(Constantes.BLOQUEOS_MODO_FECHA_ENTREGA);
    	return this.vBloqueoEncargosPiladasDao.findMotivosRefBloqueada(vBloqueoEncargosPiladas, pagination);
    }
    
    @Override
    public Long findMotivosRefBloqueadaCont(VBloqueoEncargosPiladas vBloqueoEncargosPiladas) throws Exception  {
    	vBloqueoEncargosPiladas.setModo(Constantes.BLOQUEOS_MODO_FECHA_ENTREGA);
    	return this.vBloqueoEncargosPiladasDao.findMotivosRefBloqueadaCont(vBloqueoEncargosPiladas);
    }
    
    @Override
    public Long registrosRefBloqueadaCont(VBloqueoEncargosPiladas vBloqueoEncargosPiladas) throws Exception  {
    	vBloqueoEncargosPiladas.setModo(Constantes.BLOQUEOS_MODO_FECHA_ENTREGA);
    	return this.vBloqueoEncargosPiladasDao.registrosRefBloqueadaCont(vBloqueoEncargosPiladas);
    }
    
    @Override
    public VBloqueoEncargosPiladas getBloqueoFecha(VBloqueoEncargosPiladas vBloqueoEncargosPiladas) throws Exception{
    	return this.vBloqueoEncargosPiladasDao.getBloqueoFecha(vBloqueoEncargosPiladas);
    }
}
