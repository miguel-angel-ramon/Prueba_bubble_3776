package es.eroski.misumi.service;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.ConfirmacionNoServidoDao;
import es.eroski.misumi.dao.iface.MovimientoStockDao;
import es.eroski.misumi.dao.iface.StockTiendaDao;
import es.eroski.misumi.model.MovimientoStock;
import es.eroski.misumi.model.StockNoServido;
import es.eroski.misumi.model.pda.PdaUltimosMovStocks;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.MovimientosStockService;
import es.eroski.misumi.util.Constantes;

@Service(value = "MovimientosStockService")
public class MovimientosStockServiceImpl implements MovimientosStockService {
	//private static Logger logger = LoggerFactory.getLogger(MovimientosStockServiceImpl.class);
    @Autowired
	private MovimientoStockDao movimientosStockDao;
	
    @Autowired
	private ConfirmacionNoServidoDao confirmacionNoServidoDao;
    
    @Autowired
    private StockTiendaDao stockTiendaDao;
	
	 @Resource
	 private MessageSource messageSource;
    
	@Override
	public List<MovimientoStock> findAllLastDays(MovimientoStock mc,  HttpSession session) throws Exception {
		List<MovimientoStock> MovimientoStockLista = this.movimientosStockDao.findAllLastDays(mc, Constantes.DIAS_MOVIMIENTO_STOCK); //�ltimos 15 d�as 
		boolean found=false;
		
		/*Correlacion de stock... */
		/*Antes de enviar el stock, se comprueba los días que no tienen stock y se asigna el stock del ultimo día anterior con stock*/
		Locale locale = LocaleContextHolder.getLocale();
		for (int i = MovimientoStockLista.size() - 1; i >= 0; i--){
			if (!MovimientoStockLista.get(i).getStockFinal().equals(Constantes.STOCK_NO_ASIGNADO)){
				if (MovimientoStockLista.get(i).getFechaGen() == null){
					Float stock = null;
					for (int k = i + 1; k < MovimientoStockLista.size(); k++){
						if (MovimientoStockLista.get(k).getFechaGen() != null){
							stock = MovimientoStockLista.get(k).getStockFinal();
							break;
						}
					}
					Calendar cal = Calendar.getInstance();
					String fechaHoy = String.format("%02d", cal.get(Calendar.DAY_OF_MONTH));
					if (fechaHoy.equals(MovimientoStockLista.get(i).getFecha())){
						found=true;
					}
					if (found){
						stock=null;
					}
					MovimientoStockLista.get(i).setStockFinal(stock);
				}
			}
			if (MovimientoStockLista.get(i).getEsHoy() != null && MovimientoStockLista.get(i).getEsHoy().equals("S"))
			{
				//En el caso de ser la fecha de hoy, obtenemos el stock de un WEB-SERVICE.
				
				try {
					ConsultarStockRequestType requestType = new ConsultarStockRequestType();
					requestType.setCodigoCentro(BigInteger.valueOf(mc.getCodCentro()));
					requestType.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA);
					BigInteger[] listaRef = {BigInteger.valueOf(mc.getCodArt())}; 
					requestType.setListaCodigosReferencia(listaRef);
					ConsultarStockResponseType responseType = this.stockTiendaDao.consultaStock(requestType,session);
					if (responseType.getCodigoRespuesta().equals("OK")){
						for (ReferenciaType referencia : responseType.getListaReferencias()){
							if (referencia.getCodigoReferencia().equals(BigInteger.valueOf(mc.getCodArt()))){
								if (referencia.getCodigoError().equals(new BigInteger("0"))){
									if (referencia.getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
										MovimientoStockLista.get(i).setStockFinal(referencia.getBandejas().floatValue());
										MovimientoStockLista.get(i).setFlgErrorWSStockTienda(0);
										MovimientoStockLista.get(i).setMensajeErrorWSStockTienda("");
									} else {
										MovimientoStockLista.get(i).setStockFinal(referencia.getStock().floatValue());
										MovimientoStockLista.get(i).setFlgErrorWSStockTienda(0);
										MovimientoStockLista.get(i).setMensajeErrorWSStockTienda("");
									}
								} else {
									MovimientoStockLista.get(i).setFlgErrorWSStockTienda(1);
									MovimientoStockLista.get(i).setMensajeErrorWSStockTienda(this.messageSource.getMessage("p16_referenciasCentroM.errorWSStockTiendaCelda", null, locale));
								}
							}
						}
					} else {
						MovimientoStockLista.get(i).setFlgErrorWSStockTienda(1);
						MovimientoStockLista.get(i).setMensajeErrorWSStockTienda(this.messageSource.getMessage("p16_referenciasCentroM.errorWSStockTiendaCelda", null, locale));
					}
				} catch (Exception e) {
					MovimientoStockLista.get(i).setFlgErrorWSStockTienda(1);
					MovimientoStockLista.get(i).setMensajeErrorWSStockTienda(this.messageSource.getMessage("p16_referenciasCentroM.errorWSStockTiendaCelda", null, locale));
				}
				MovimientoStockLista.get(i).setFecha(MovimientoStockLista.get(i).getFecha() + " " + this.messageSource.getMessage("p16_referenciasCentroM.hoy", null, locale));
			}
				
				
		}
		if (MovimientoStockLista.get(MovimientoStockLista.size()-1).getFechaGen() == null){
			MovimientoStockLista.get(MovimientoStockLista.size()-1).setStockFinal(null);
		}
		return MovimientoStockLista; 
	}

	@Override
	public List<MovimientoStock> findAllDetailsLastDays(MovimientoStock mc, Pagination pagination) throws Exception {
		
		return this.movimientosStockDao.findAllDetailsLastDays(mc, Constantes.DIAS_MOVIMIENTO_STOCK_DETALLE, pagination); //Últimos 30 días
	}
    
	@Override
	public List<StockNoServido> findAllLastDays(StockNoServido nsr, Pagination pagination) throws Exception {
		
		return this.confirmacionNoServidoDao.findAllLastDays(nsr, Constantes.DIAS_STOCK_NO_SERVIDO, pagination); //Últimos 15 días
	}
    
	@Override
	public List<PdaUltimosMovStocks> findAllDetailsLastDaysPda(MovimientoStock mc) throws Exception {
		
		return this.movimientosStockDao.findAllDetailsLastDaysPda(mc); 
	}
	
	@Override
	public List<PdaUltimosMovStocks> findAllDetailsLastDaysPdaPaginada(MovimientoStock mc, int inicioPaginacion, int finPaginacion) throws Exception {
		
		return this.movimientosStockDao.findAllDetailsLastDaysPdaPaginada(mc, inicioPaginacion, finPaginacion); 
	}
}