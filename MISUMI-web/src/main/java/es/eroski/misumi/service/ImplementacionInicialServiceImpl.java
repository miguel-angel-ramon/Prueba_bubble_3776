package es.eroski.misumi.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.HistoricoVentaMediaDao;
import es.eroski.misumi.dao.iface.VFestivoCentroDao;
import es.eroski.misumi.model.HistoricoVentaMedia;
import es.eroski.misumi.model.ImplantacionInicial;
import es.eroski.misumi.model.VFestivoCentro;
import es.eroski.misumi.service.iface.ImplantacionInicialService;

@Service(value = "ImplantacionInicialService")
public class ImplementacionInicialServiceImpl implements ImplantacionInicialService {

    @Autowired
	private HistoricoVentaMediaDao historicoVentaMediaDao;
    
    @Autowired
	private VFestivoCentroDao vFestivoCentroDao;
    
	@Override
	public ImplantacionInicial getImplantacionInicial(ImplantacionInicial implantacionInicial) throws Exception {
		Double ventaMedia = new Double(1);
		HistoricoVentaMedia historicoVentaMedia = new HistoricoVentaMedia();
		historicoVentaMedia.setCodLoc(implantacionInicial.getCodLoc());
		historicoVentaMedia.setCodArticulo(implantacionInicial.getCodArticulo());
		HistoricoVentaMedia historicoVentaMediaRes = null;
		List<HistoricoVentaMedia> listHistoricoVentaMedia = this.historicoVentaMediaDao.findAllAcumuladoRef(historicoVentaMedia);
		if (!listHistoricoVentaMedia.isEmpty()){
			historicoVentaMediaRes = listHistoricoVentaMedia.get(0);
		}
		if (null != historicoVentaMediaRes){
			historicoVentaMediaRes.recalcularVentasMedia();
			if (!historicoVentaMediaRes.getMedia().equals(new Float(0.0))){
				ventaMedia = historicoVentaMediaRes.getMedia().doubleValue();
			}
		} 
		VFestivoCentro vFestivoCentro = new VFestivoCentro();
		vFestivoCentro.setCodCentro(implantacionInicial.getCodLoc());
		vFestivoCentro.setFechaInicio(implantacionInicial.getFechaInicio());
		vFestivoCentro.setFechaFin(implantacionInicial.getFechaFin());
		Integer numDias = this.vFestivoCentroDao.getNumDias(vFestivoCentro);
		if (numDias > 15){
			numDias = 15;
		}
		BigDecimal vm = new BigDecimal(ventaMedia);
		BigDecimal nd = new BigDecimal(numDias);
		BigDecimal resultado = vm.multiply(nd);
		BigDecimal res = resultado.setScale(0, RoundingMode.UP);
		//Long ventasMedias = new Double(ventaMedia * numDias).longValue();
		implantacionInicial.setVentaMedia(res.doubleValue()); 
		return implantacionInicial;
	}

}
