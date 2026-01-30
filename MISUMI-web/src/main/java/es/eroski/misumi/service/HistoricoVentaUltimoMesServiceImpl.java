package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.HistoricoVentaUltimoMesDao;
import es.eroski.misumi.model.HistoricoVentaUltimoMes;
import es.eroski.misumi.service.iface.HistoricoVentaUltimoMesService;
import es.eroski.misumi.util.Constantes;

@Service(value = "HistoricoVentaUltimoMesService")
public class HistoricoVentaUltimoMesServiceImpl implements HistoricoVentaUltimoMesService{
	//private static Logger logger = LoggerFactory.getLogger(HistoricoVentaUltimoMesServiceImpl.class);
    @Autowired
	private HistoricoVentaUltimoMesDao historicoVentaUltimoMesDao;
	
    @Override
	 public List<HistoricoVentaUltimoMes> findAll(HistoricoVentaUltimoMes historicoVentaUltimoMes) throws Exception {
		return this.historicoVentaUltimoMesDao.findAll(historicoVentaUltimoMes);
	}
	
    @Override
	 public List<HistoricoVentaUltimoMes> findAllLastDays(HistoricoVentaUltimoMes historicoVentaUltimoMes) throws Exception {
		return this.historicoVentaUltimoMesDao.findAllLastDays(historicoVentaUltimoMes, Constantes.NUMERO_DIAS_HISTORICO_VENTA_MES);
	}

    @Override
	public HistoricoVentaUltimoMes findOne(HistoricoVentaUltimoMes historicoVentaUltimoMes) throws Exception {
		HistoricoVentaUltimoMes historicoVentaUltimoMesRes = null;
		List<HistoricoVentaUltimoMes> listHistoricoVentaUltimoMes = this.historicoVentaUltimoMesDao.findAll(historicoVentaUltimoMes);
		if (!listHistoricoVentaUltimoMes.isEmpty()){
			historicoVentaUltimoMesRes = listHistoricoVentaUltimoMes.get(0);
		}
		return historicoVentaUltimoMesRes;

	}
    
    @Override
    public HistoricoVentaUltimoMes findTotalLastDays(HistoricoVentaUltimoMes historicoVentaUltimoMes, int lastDays) throws Exception{
    	HistoricoVentaUltimoMes historicoVentaUltimoMesRes = null;
		List<HistoricoVentaUltimoMes> listHistoricoVentaUltimoMes = this.historicoVentaUltimoMesDao.findTotalLastDays(historicoVentaUltimoMes, lastDays);
		if (null != listHistoricoVentaUltimoMes && !listHistoricoVentaUltimoMes.isEmpty()){
			historicoVentaUltimoMesRes = listHistoricoVentaUltimoMes.get(0);
			historicoVentaUltimoMesRes.recalcularTotalVentas();
		}
		return historicoVentaUltimoMesRes;
    }

}
