package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.StockFinalMinimoDao;
import es.eroski.misumi.dao.iface.StockFinalMinimoSIADao;
import es.eroski.misumi.model.ParamStockFinalMinimo;
import es.eroski.misumi.model.ReferenciasCentroIC;
import es.eroski.misumi.model.StockFinalMinimo;
import es.eroski.misumi.service.iface.StockFinalMinimoService;

@Service(value = "StockFinalMinimoService")
public class StockFinalMinimoServiceImpl implements StockFinalMinimoService {
	//private static Logger logger = LoggerFactory.getLogger(StockFinalMinimoServiceImpl.class);
    @Autowired
	private StockFinalMinimoDao stockFinalMinimoDao;

    @Autowired
	private StockFinalMinimoSIADao stockFinalMinimoSIADao;

    @Override
	 public List<StockFinalMinimo> findAll(StockFinalMinimo stockFinalMinimo) throws Exception {
		return this.stockFinalMinimoDao.findAll(stockFinalMinimo);
	}
	
    @Override
	public StockFinalMinimo findOne(StockFinalMinimo stockFinalMinimo) throws Exception {
		StockFinalMinimo vMapaSotockFinalMinimoRes = null;
		List<StockFinalMinimo> listStockFinalMinimo = this.stockFinalMinimoDao.findAll(stockFinalMinimo);
		if (!listStockFinalMinimo.isEmpty()){
			vMapaSotockFinalMinimoRes = listStockFinalMinimo.get(0);
		}
		return vMapaSotockFinalMinimoRes;

	}

    @Override
    public Long findFinalStockParam(ParamStockFinalMinimo paramStockFinalMinimo)
			throws Exception{
    	return this.stockFinalMinimoDao.findFinalStockParam(paramStockFinalMinimo);
    }
    
    @Override
	public StockFinalMinimo findOneSIA(StockFinalMinimo stockFinalMinimo) throws Exception {
		StockFinalMinimo vMapaSotockFinalMinimoRes = null;
		List<StockFinalMinimo> listStockFinalMinimo = this.stockFinalMinimoSIADao.findAll(stockFinalMinimo);
		if (null!=listStockFinalMinimo && !listStockFinalMinimo.isEmpty()){
			vMapaSotockFinalMinimoRes = listStockFinalMinimo.get(0);
		}
		return vMapaSotockFinalMinimoRes;

	}
    
    @Override
    public Long findVidaUtil(StockFinalMinimo stockFinalMinimo) throws Exception{
    	return this.stockFinalMinimoDao.findVidaUtil(stockFinalMinimo);
    }

    @Override
	public StockFinalMinimo obtenerStockFinalMinimo(
			ReferenciasCentroIC referenciasCentroIC) throws Exception {

		StockFinalMinimo stockFinalMinimoRes;
		StockFinalMinimo stockFinalMinimoSIARes;
		StockFinalMinimo stockFinalMinimo = new StockFinalMinimo();
		stockFinalMinimo.setCodArticulo(referenciasCentroIC.getCodArt());
		stockFinalMinimo.setCodLoc(referenciasCentroIC.getCodCentro());

		stockFinalMinimoRes = this.findOne(stockFinalMinimo);
		stockFinalMinimoSIARes = this.findOneSIA(stockFinalMinimo);
		
		if (stockFinalMinimoRes != null){
			
			if (stockFinalMinimoRes.getVentaMedia()==null){
				stockFinalMinimoRes.setVentaMedia(new Float("0.0"));
			}
		}else{
			stockFinalMinimoRes = new StockFinalMinimo();
			stockFinalMinimoRes.setVentaMedia(new Float("0.0"));
			stockFinalMinimoRes.setStockFinMinS(new Float("0.0"));
		}
		//Recalcular dias de venta
		stockFinalMinimoRes.recalcularDiasVenta();

		//Guardado de la cantidad manual de SIA para comprobacón en stock de pantalla
		if (stockFinalMinimoSIARes!=null && stockFinalMinimoSIARes.getStockFinMinS() != null){
			stockFinalMinimoRes.setCantidadManualSIA(stockFinalMinimoSIARes.getStockFinMinS());
		}else{
			stockFinalMinimoRes.setCantidadManualSIA(null);
		}
		
		//Guardado del facing centro de SIA para comprobacón en facing
		if (stockFinalMinimoSIARes!=null && stockFinalMinimoSIARes.getFacingCentro() != null){
			stockFinalMinimoRes.setFacingCentroSIA(stockFinalMinimoSIARes.getFacingCentro());
		}else{
			stockFinalMinimoRes.setFacingCentroSIA(null);
		}
		
		//Guardado del facing centro de SIA para comprobacón en facing
		if (stockFinalMinimoSIARes!=null && stockFinalMinimoSIARes.getCapacidad() != null){
			stockFinalMinimoRes.setCapacidadSIA(stockFinalMinimoSIARes.getCapacidad());
		}else{
			stockFinalMinimoRes.setCapacidadSIA(null);
		}


		return stockFinalMinimoRes;
	}

}
