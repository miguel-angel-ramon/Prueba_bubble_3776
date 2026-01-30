package es.eroski.misumi.service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.MapaAprovFestivoDao;
import es.eroski.misumi.dao.iface.VReferenciaActiva2Dao;
import es.eroski.misumi.model.MapaAprovFestivo;
import es.eroski.misumi.model.VReferenciaActiva2;
import es.eroski.misumi.service.iface.VReferenciaActiva2Service;
import es.eroski.misumi.util.Utilidades;

@Service(value = "VReferenciaActiva2Service")
public class VReferenciaActiva2ServiceImpl implements VReferenciaActiva2Service {
	//private static Logger logger = LoggerFactory.getLogger(VRelacionArticuloServiceImpl.class);
	//private static Logger logger = LoggerFactory.getLogger(VRelacionArticuloServiceImpl.class);
    @Autowired
	private VReferenciaActiva2Dao vReferenciaActiva2Dao;
    
    @Autowired
    private MapaAprovFestivoDao mapaAprovFestivoDao;
    
	@Resource
	private MessageSource messageSource;
	

	 /* (non-Javadoc)
	 * @see es.eroski.misumi.service.VReferenciaActiva2Service#getNextDiaPedido(java.lang.Long, java.lang.Long)
	 */
	@Override
	public String getNextDiaPedido(Long codCentro, Long codArt) throws Exception {
		String nextDayStr = null;
		List<VReferenciaActiva2> listDate = this.vReferenciaActiva2Dao.getNextDiaPedido(codCentro, codArt);
		
		MapaAprovFestivo mapaAprov = new MapaAprovFestivo();
		mapaAprov.setCodArt(codArt);
		mapaAprov.setCodCentro(codCentro);
		mapaAprov.setEstado("A");
		List<MapaAprovFestivo> listFechas = this.mapaAprovFestivoDao.getFechasPedido(mapaAprov);
		
		Calendar cal = Calendar.getInstance();
		cal.clear(Calendar.HOUR_OF_DAY);
		cal.clear(Calendar.HOUR);
		cal.clear(Calendar.AM_PM);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		cal.add(Calendar.DATE, 1);
		boolean existsDay = false;
		MapaAprovFestivo mapaAprovFestivo = new MapaAprovFestivo();
		mapaAprovFestivo.setFechaCambio(cal.getTime());
		SimpleDateFormat df = new SimpleDateFormat("E",Locale.ENGLISH);
		VReferenciaActiva2 vRefAct = null;
		if (!listDate.isEmpty()){
			vRefAct = listDate.get(0);
			if (!vRefAct.getActiva().equals("S")){
				vRefAct = null;
			}
		}
		if (!listFechas.isEmpty() || !listDate.isEmpty()){
			Integer i = 0;
			while(i < 6 && !existsDay){
				if (listFechas.contains(mapaAprovFestivo)){
					MapaAprovFestivo mapaAux = listFechas.get(listFechas.indexOf(mapaAprovFestivo));
					if (mapaAux.getPlazo() != 0){
						existsDay = true;
						int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
						if (dayOfWeek == 0)
						    dayOfWeek = 7;
						//MessageSource messageSource = (MessageSource) ContextLoader.getCurrentWebApplicationContext().getBean("messageSource");
						String messageKey = "calendario."+String.valueOf(dayOfWeek);
						nextDayStr=messageSource.getMessage(messageKey, null,LocaleContextHolder.getLocale());
					} else {
						cal.add(Calendar.DATE, 1);
						i++;
					}
				} else if (null != vRefAct){
					String dia = df.format(cal.getTime());
					PropertyDescriptor pd = new PropertyDescriptor(dia, VReferenciaActiva2.class);
					Method getter = pd.getReadMethod();
					Integer pedido = (Integer) getter.invoke(vRefAct);
					if (pedido > 0){
						existsDay = true;
						int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
						if (dayOfWeek == 0)
						    dayOfWeek = 7;
						//MessageSource messageSource = (MessageSource) ContextLoader.getCurrentWebApplicationContext().getBean("messageSource");
						String messageKey = "calendario."+String.valueOf(dayOfWeek);
						nextDayStr=messageSource.getMessage(messageKey, null,LocaleContextHolder.getLocale());
					} else {
						cal.add(Calendar.DATE, 1);
						i++;
					}
				} else {
					cal.add(Calendar.DATE, 1);
					i++;
				}
			}
		}
		
		return nextDayStr;
		
	}
	
	@Override
	public String getNextFechaPedido(Long codCentro, Long codArt) throws Exception {
		String nextFechaStr = null;
		StringBuffer nextFechaBuffer = new StringBuffer();
		
		List<VReferenciaActiva2> listDate = this.vReferenciaActiva2Dao.getNextDiaPedido(codCentro, codArt);
		
		MapaAprovFestivo mapaAprov = new MapaAprovFestivo();
		mapaAprov.setCodArt(codArt);
		mapaAprov.setCodCentro(codCentro);
		mapaAprov.setEstado("A");
		List<MapaAprovFestivo> listFechas = this.mapaAprovFestivoDao.getFechasPedido(mapaAprov);
		
		Calendar cal = Calendar.getInstance();
		cal.clear(Calendar.HOUR_OF_DAY);
		cal.clear(Calendar.HOUR);
		cal.clear(Calendar.AM_PM);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		cal.add(Calendar.DATE, 1);
		boolean existsDay = false;
		MapaAprovFestivo mapaAprovFestivo = new MapaAprovFestivo();
		mapaAprovFestivo.setFechaCambio(cal.getTime());
		SimpleDateFormat df = new SimpleDateFormat("E",Locale.ENGLISH);
		VReferenciaActiva2 vRefAct = null;
		if (!listDate.isEmpty()){
			vRefAct = listDate.get(0);
			if (!vRefAct.getActiva().equals("S")){
				vRefAct = null;
			}
		}
		if (!listFechas.isEmpty() || !listDate.isEmpty()){
			Integer i = 0;
			while(i < 7 && !existsDay){
				if (listFechas.contains(mapaAprovFestivo)){
					MapaAprovFestivo mapaAux = listFechas.get(listFechas.indexOf(mapaAprovFestivo));
					if (mapaAux.getPlazo() != 0){
						existsDay = true;
						
						nextFechaBuffer.append(Utilidades.rellenarIzquierda(cal.get(Calendar.DAY_OF_MONTH) + "", '0', 2));
						nextFechaBuffer.append(Utilidades.rellenarIzquierda(cal.get(Calendar.MONTH) + 1 + "", '0', 2));
						nextFechaBuffer.append(cal.get(Calendar.YEAR) + "");
						
						nextFechaStr = nextFechaBuffer.toString();
						
					} else {
						cal.add(Calendar.DATE, 1);
						i++;
					}
				} else if (null != vRefAct){
					String dia = df.format(cal.getTime());
					PropertyDescriptor pd = new PropertyDescriptor(dia, VReferenciaActiva2.class);
					Method getter = pd.getReadMethod();
					Integer pedido = (Integer) getter.invoke(vRefAct);
					if (pedido > 0){
						existsDay = true;
						
						nextFechaBuffer.append(Utilidades.rellenarIzquierda(cal.get(Calendar.DAY_OF_MONTH) + "", '0', 2));
						nextFechaBuffer.append(Utilidades.rellenarIzquierda(cal.get(Calendar.MONTH) + 1 + "", '0', 2));
						nextFechaBuffer.append(cal.get(Calendar.YEAR) + "");
						
						nextFechaStr = nextFechaBuffer.toString();

					} else {
						cal.add(Calendar.DATE, 1);
						i++;
					}
				} else {
					cal.add(Calendar.DATE, 1);
					i++;
				}
			}
		}
		
		return nextFechaStr;
		
	}


}
