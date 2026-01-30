package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.CentroAutoservicioDao;
import es.eroski.misumi.dao.iface.ParamCentrosVpDao;
import es.eroski.misumi.model.CentroAutoservicio;
import es.eroski.misumi.model.ParamCentrosVp;
import es.eroski.misumi.service.iface.ParamCentrosVpService;
import es.eroski.misumi.util.Constantes;

@Service(value = "ParamCentrosVpService")
public class ParamCentrosVpServiceImpl implements ParamCentrosVpService {
	//private static Logger logger = LoggerFactory.getLogger(ParamCentrosVpServiceImpl.class);
	//private static Logger logger = Logger.getLogger(ParamCentrosVpServiceImpl.class);
    @Autowired
	private ParamCentrosVpDao paramCentrosVpDao;
    @Autowired
	private CentroAutoservicioDao centroAutoservicioDao;

	@Override
	public List<ParamCentrosVp> findAll(ParamCentrosVp paramCentrosVp)
			throws Exception {
		return this.paramCentrosVpDao.findAll(paramCentrosVp);
	}
	@Override
	public Long findAllCont(ParamCentrosVp paramCentrosVp) throws Exception {
		return this.paramCentrosVpDao.findAllCont(paramCentrosVp);
	}
	@Override
	public boolean esAutoservicio(ParamCentrosVp paramCentrosVp)
			throws Exception {
		
		boolean resultado = false;
		ParamCentrosVp paramCentrosVpBusq = new ParamCentrosVp();
		paramCentrosVpBusq.setCodLoc(paramCentrosVp.getCodLoc());
		//Para que sea autoservicio el flag FLG_CAPACIDAD = S y el porcentaje_capacidad del centro no debe ser nulo
		paramCentrosVpBusq.setFlgCapacidad(Constantes.FLG_CAPACIDAD_AUTOSERVICIO);
		if (this.paramCentrosVpDao.findAllCont(paramCentrosVpBusq) > 0){
			CentroAutoservicio centroAutoservicio = new CentroAutoservicio();
			centroAutoservicio.setCodCentro(paramCentrosVpBusq.getCodLoc());
			resultado = this.centroAutoservicioDao.esAutoservicio(centroAutoservicio);
		}
		return resultado;
	}
	
	@Override
	public List<CentroAutoservicio> findCentroAutoServicioAll(CentroAutoservicio centroAutoservicio)
			throws Exception {
		return this.centroAutoservicioDao.findAll(centroAutoservicio);
	}
	
	@Override
	public boolean esFacingCentro(ParamCentrosVp paramCentrosVp)
			throws Exception {
		
		boolean resultado = false;

		List <ParamCentrosVp> listaParamCentrosVp = this.findAll(paramCentrosVp);
		if (listaParamCentrosVp != null && listaParamCentrosVp.size() == 1 && listaParamCentrosVp.get(0) != null){
			resultado = Constantes.FLG_SI_FACING_CENTRO.equals(listaParamCentrosVp.get(0).getFlgFacingCentro());
		}
		return resultado;
	}
	
}
