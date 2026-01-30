package es.eroski.misumi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VSurtidoTiendaDao;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.service.iface.VSurtidoTiendaService;

@Service(value = "VSurtidoTiendaService")
public class VSurtidoTiendaServiceImpl implements VSurtidoTiendaService {
	//private static Logger logger = LoggerFactory.getLogger(VSurtidoTiendaServiceImpl.class);
	//private static Logger logger = Logger.getLogger(VSurtidoTiendaServiceImpl.class);
	@Autowired
	private VSurtidoTiendaDao vSurtidoTiendaDao;

	@Override
	public List<VSurtidoTienda> findAll(VSurtidoTienda vSurtidoTienda) throws Exception {
		return this.vSurtidoTiendaDao.findAll(vSurtidoTienda);
	}

	@Override
	public VSurtidoTienda findOne(VSurtidoTienda vSurtidoTienda) throws Exception {
		VSurtidoTienda vSurtidoTiendaRes = null;
		List<VSurtidoTienda> listSurtidoTienda = this.vSurtidoTiendaDao.findAll(vSurtidoTienda);
		if (!listSurtidoTienda.isEmpty()){
			vSurtidoTiendaRes = listSurtidoTienda.get(0);
		}
		return vSurtidoTiendaRes;

	}

	@Override
	public VSurtidoTienda findOneGama(VSurtidoTienda vSurtidoTienda) throws Exception {
		VSurtidoTienda vSurtidoTiendaRes = null;
		List<VSurtidoTienda> listSurtidoTienda = this.vSurtidoTiendaDao.findAllGama(vSurtidoTienda);
		if (!listSurtidoTienda.isEmpty()){
			vSurtidoTiendaRes = listSurtidoTienda.get(0);
		}
		return vSurtidoTiendaRes;
	}
	
	@Override
	public VSurtidoTienda findOneVegalsa(VSurtidoTienda vSurtidoTienda) throws Exception {
		VSurtidoTienda vSurtidoTiendaRes = new VSurtidoTienda();
		List<VSurtidoTienda> listSurtidoTienda = this.vSurtidoTiendaDao.findAllVegalsa(vSurtidoTienda);
		if (!listSurtidoTienda.isEmpty()){
			vSurtidoTiendaRes = listSurtidoTienda.get(0);
		}
		return vSurtidoTiendaRes;
	}


	@Override
	public Date obtenerFechaGeneracionSurtidoTienda(VSurtidoTienda vSurtidoTienda) throws Exception {
		Date fechaGen = null;
		if(vSurtidoTienda != null){
			List<VSurtidoTienda> listSurtidoTienda = this.vSurtidoTiendaDao.obtenerFechaGeneracionSurtidoTienda(vSurtidoTienda);
			if (!listSurtidoTienda.isEmpty()){
				for (VSurtidoTienda vSurTi: listSurtidoTienda){
					if ("S".equals(vSurTi.getPedir())){
						fechaGen = vSurTi.getFechaGen();
					}
					if ("N".equals(vSurTi.getPedir())){
						break;
					}
				}
			}
		}
		return fechaGen;
	}

	@Override
	public Long comprobarStockMayorACero(VSurtidoTienda vSurtidoTienda) throws Exception {
		Long stock = 0L;
		if(vSurtidoTienda != null){
			stock = this.vSurtidoTiendaDao.comprobarStockMayorACero(vSurtidoTienda);
		}
		return stock;
	}

	@Override
	public Long comprobarStockMayorACeroCaprabo(VSurtidoTienda vSurtidoTienda) throws Exception {

		return this.vSurtidoTiendaDao.comprobarStockMayorACeroCaprabo(vSurtidoTienda);

	}
}
