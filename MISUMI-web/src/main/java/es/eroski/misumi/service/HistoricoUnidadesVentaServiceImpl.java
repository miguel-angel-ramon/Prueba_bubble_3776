package es.eroski.misumi.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.HistoricoUnidadesVentaDao;
import es.eroski.misumi.dao.iface.RelacionArticuloDao;
import es.eroski.misumi.model.DiaVentasUltimasOfertas;
import es.eroski.misumi.model.HistoricoUnidadesVenta;
import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.service.iface.HistoricoUnidadesVentaService;
import es.eroski.misumi.util.Constantes;

@Service(value = "HistoricoUnidadesVentaService")
public class HistoricoUnidadesVentaServiceImpl implements HistoricoUnidadesVentaService {
	//private static Logger logger = LoggerFactory.getLogger(HistoricoUnidadesVentaServiceImpl.class);
    @Autowired
	private HistoricoUnidadesVentaDao historicoUnidadesVentaDao;
    
    @Autowired
	private RelacionArticuloDao relacionArticuloDao;
	
    @Override
	 public List<HistoricoUnidadesVenta> findAll(HistoricoUnidadesVenta historicoUnidadesVenta) throws Exception {
		return this.historicoUnidadesVentaDao.findAll(historicoUnidadesVenta);
	}
	
    @Override
	public HistoricoUnidadesVenta findOne(HistoricoUnidadesVenta historicoUnidadesVenta) throws Exception {
		HistoricoUnidadesVenta historicoUnidadesVentaRes = null;
		List<HistoricoUnidadesVenta> listHistoricoUnidadesVentas = this.historicoUnidadesVentaDao.findAll(historicoUnidadesVenta);
		if (!listHistoricoUnidadesVentas.isEmpty()){
			historicoUnidadesVentaRes = listHistoricoUnidadesVentas.get(0);
		}
		return historicoUnidadesVentaRes;

	}
    
    @Override
    public HistoricoUnidadesVenta findTotalLastDays(HistoricoUnidadesVenta historicoUnidadesVenta, int lastDays) throws Exception {
    	HistoricoUnidadesVenta historicoUnidadesVentaRes = null;
		List<HistoricoUnidadesVenta> listHistoricoUnidadesVentas = this.historicoUnidadesVentaDao.findTotalLastDays(historicoUnidadesVenta, lastDays);
		if (!listHistoricoUnidadesVentas.isEmpty()){
			historicoUnidadesVentaRes = listHistoricoUnidadesVentas.get(0);
		}
		return historicoUnidadesVentaRes;
	}
    
    @Override
    public Double findDayMostSales(HistoricoUnidadesVenta historicoUnidadesVenta) throws Exception {
    	RelacionArticulo relacionArticulo = new RelacionArticulo();
		relacionArticulo.setCodArt(historicoUnidadesVenta.getCodArticulo());
		relacionArticulo.setCodCentro(historicoUnidadesVenta.getCodLoc());
    	return this.historicoUnidadesVentaDao.findDayMostSales(historicoUnidadesVenta, Constantes.NUMERO_DIAS_MAYOR_VENTA);
    }

    @Override
	public List<DiaVentasUltimasOfertas> findDateListMediaSales(String codCentro, String codArticulo, String fecIni, String fecFin,String sumVentaAnticipada) throws Exception {
		return this.historicoUnidadesVentaDao.findDateListMediaSales(codCentro, codArticulo, fecIni, fecFin,sumVentaAnticipada);
	}
    
	@Override
	public List<Map<String, Object>> findDateListMediaSalesViejo(String codCentro, String codArticulo, String fecIni, String fecFin) throws Exception {
		return this.historicoUnidadesVentaDao.findDateListMediaSalesViejo(codCentro, codArticulo, fecIni, fecFin);
	}
	
	public HistoricoUnidadesVenta findVentaUltimaOferta(HistoricoUnidadesVenta historicoUnidadesVenta, String sumVentaAnticipada) throws Exception{
		return this.historicoUnidadesVentaDao.findVentaUltimaOferta(historicoUnidadesVenta,sumVentaAnticipada);
	}
}
