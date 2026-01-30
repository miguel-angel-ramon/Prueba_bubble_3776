package es.eroski.misumi.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.AlarmasPLUDao;
import es.eroski.misumi.model.AlarmaPLU;
import es.eroski.misumi.model.GenericExcelFieldsVO;
import es.eroski.misumi.model.PLU;
import es.eroski.misumi.model.alarmasPLUSWS.ConsultarPLUSRequestType;
import es.eroski.misumi.model.alarmasPLUSWS.ConsultarPLUSResponseType;
import es.eroski.misumi.model.alarmasPLUSWS.ModificarPLUSRequestType;
import es.eroski.misumi.model.alarmasPLUSWS.ModificarPLUSResponseType;
import es.eroski.misumi.model.alarmasPLUSWS.ReferenciaModType;
import es.eroski.misumi.model.alarmasPLUSWS.ReferenciaResModType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.AlarmasPLUService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Service(value = "AlarmasPLUService")
public class AlarmasPLUServiceImpl implements AlarmasPLUService {

	private static Logger logger = Logger.getLogger(AlarmasPLUServiceImpl.class);

	// Conjunto de listados de alarmas para cada centro
	Map<Long, List<AlarmaPLU>> alarmListOrig = new HashMap<Long, List<AlarmaPLU>>();
	Map<Long, List<AlarmaPLU>> alarmListModified = new HashMap<Long, List<AlarmaPLU>>();
	
	@Resource 
	private MessageSource messageSource;
	
	@Autowired
	private AlarmasPLUDao alarmasPLUDao;

	@Autowired
	private StockTiendaService stockTiendaService;
	
	private Map<Long, Boolean> cargandoPLUs = new HashMap<Long, Boolean>();

	@Override
	public Boolean getEstaCargando(Long codCentro){
		Boolean output = cargandoPLUs.get(codCentro);
		if (output==null){
			cargandoPLUs.put(codCentro,false);
			output = false;
		}
		return output; 
	}
	
	@Override
	public void setEstaCargando(Long codCentro, Boolean estaCargando){
		cargandoPLUs.put(codCentro,estaCargando);
	}
	
	@Override
	public List<AlarmaPLU> findAll(Pagination pagination, Long codCentro, Long codArea, Long codSeccion, Long agrupacion, HttpSession session) throws Exception {
		logger.info("findAll - codCentro = "+codCentro);
		List<AlarmaPLU> output = alarmasPLUDao.findAll(pagination, codCentro, codArea, codSeccion, agrupacion);
		output=this.setPendienteRecibir(output);
		output = filterList(output, codArea, codSeccion, agrupacion, null, true);
		output = setStock(output, codCentro, session);
		output = setStatusToList(output);		
		return orderList(output, pagination, true);
	}
	
	@Override
	public AlarmaPLU findOne(Long codCentro, Long codArt, Long agrupacion) throws Exception {
		return alarmasPLUDao.findOne( codCentro, codArt, agrupacion);
	}
	
	private List<AlarmaPLU> setStock(List<AlarmaPLU> alarmas, Long codCentro, HttpSession session) {
		logger.info("setStock - codCentro = "+codCentro);
		List<AlarmaPLU> output = new ArrayList<AlarmaPLU>();
		
		
		ConsultarStockRequestType requestType = new ConsultarStockRequestType();
		requestType.setCodigoCentro(BigInteger.valueOf(codCentro));
		requestType.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA);
		
		BigInteger[] listaRef = new BigInteger[alarmas.size()];
		
		for (int i=0; i<alarmas.size(); i++){
			listaRef[i] = BigInteger.valueOf(alarmas.get(i).getCodArticulo()); 
		}
		requestType.setListaCodigosReferencia(listaRef);
		
		ConsultarStockResponseType responseType = null;
		try {
			responseType = this.stockTiendaService.consultaStock(requestType,session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (responseType!=null){
			logger.info("setStock - codigoRespuesta = "+responseType.getCodigoRespuesta()+" - tipoMensaje = "+responseType.getTipoMensaje());
			if (responseType.getCodigoRespuesta().equals("OK")){
				
				for (AlarmaPLU alarma : alarmas){
					final Double stockBefore = alarma.getStock();
					alarma = getStockFromReferences(alarma,responseType.getListaReferencias());
					Double stockAfter = null;
					if(alarma!=null){
						stockAfter =alarma.getStock();
					}
					logger.info("setStock - stockBefore = "+stockBefore+" - stockAfter = "+stockAfter);
					if (stockAfter!=null && !stockBefore.equals(stockAfter)){
						alarma.setStockModificado(true);
					}else{
						alarma.setStockModificado(false);
					}
					output.add(alarma);
				}

			} else {
				logger.error("Error al consultar los STOCKs del centro "+codCentro+" ("+responseType.getCodigoRespuesta()+") : "+responseType.getDescripcionRespuesta());
				return alarmas;
			}
		}else{
			logger.error("Error al consultar los STOCKs del centro "+codCentro+" : ERROR AL INICIALIZAR LA PETICION DEL WS");
			return alarmas;
		}
		
		// Guardar el stock cargado del WS en las tablas de la BBDD
		updateStock(codCentro,output);
		return output;
	}

	private void updateStock(Long codCentro, List<AlarmaPLU> alarmas) {
		logger.info("updateStock - codCentro = "+codCentro);
		alarmasPLUDao.updateStock(codCentro, alarmas);
	}

	private AlarmaPLU getStockFromReferences(AlarmaPLU alarma, ReferenciaType[] listaReferencias) {
		AlarmaPLU output = null;
		Double stock = 0d;
		Long codArticulo = alarma.getCodArticulo();
		for (ReferenciaType referencia : listaReferencias){
			if (referencia.getCodigoError().equals(new BigInteger("0"))){
				if (referencia.getCodigoReferencia().equals(BigInteger.valueOf(codArticulo))){
					if (referencia.getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
						stock = referencia.getBandejas().doubleValue();
					} else {
						stock = referencia.getStock().doubleValue();
					}
					output = alarma;
					output.setStock(stock);

					break;
				}
			} 
		}
		if (output==null){
			logger.error("Error al consultar el STOCK del articulo "+codArticulo+" : NO SE HA ENCONTRADO");
		}
		logger.info("getStockFromReferences - codArticulo = "+codArticulo+" - stock = "+output);
		return output;
	}

	@Override
	public List<AlarmaPLU> findAllInMemory(Pagination pagination, Long codCentro, Long codArea, Long codSeccion, Long agrupacion, String filtroReferencia, Boolean useStatus, HttpSession session) throws Exception {		
		logger.info("findAllInMemory - codCentro = "+codCentro);
		List<AlarmaPLU> output = alarmListModified.get(codCentro);
		output = filterList(output, codArea, codSeccion, agrupacion, filtroReferencia, true);
		if (session!=null){
			output = setStock(output, codCentro, session);
		}
		output = setStatusToList(output);

		return orderList(output, pagination, useStatus);
	}
	
	@Override
	public List<AlarmaPLU> findAllAvisos(Pagination pagination, Long codCentro, Long codArea, Long codSeccion, Long agrupacion, String filtroReferencia, Boolean useStatus, HttpSession session) throws Exception {		
		logger.info("findAllAvisos - codCentro = "+codCentro);
		List<AlarmaPLU> output = alarmListOrig.get(codCentro);
		output = filterList(output, codArea, codSeccion, agrupacion, filtroReferencia, true);
		if (session!=null){
			output = setStock(output, codCentro, session);
		}
		//output = setStatusToList(output);

		return orderList(output, pagination, useStatus);
	}
	
	@Override
	public List<AlarmaPLU> findAllAvisosVacio(Pagination pagination, Long codCentro, Long codArea, Long codSeccion, Long agrupacion, String filtroReferencia, Boolean useStatus, HttpSession session) throws Exception {		
		logger.info("findAllAvisos - codCentro = "+codCentro);
		List<AlarmaPLU> output = alarmListOrig.get(codCentro);
		output=this.setPendienteRecibir(output);
		output = filterList(output, codArea, codSeccion, agrupacion, filtroReferencia, true);
		if (session!=null){
			output = setStock(output, codCentro, session);
		}
		return orderList(output, pagination, useStatus);
	}

	@Override
	// Misumi-312: Calcular si tiene cantidades pendientes de recibir
	public List<AlarmaPLU> setPendienteRecibir(List<AlarmaPLU> alarmas) {
		List<AlarmaPLU> output = new ArrayList<AlarmaPLU>();
		for (AlarmaPLU alarma : alarmas){
			final Long codCentro = alarma.getCodCentro();
			final Long codArticulo = alarma.getCodArticulo();
			
			final BigDecimal pendienteRecibir = alarmasPLUDao.calculaPendienteRecibir(codArticulo, codCentro);
			if (pendienteRecibir!=null && pendienteRecibir.intValue()>0){
				alarma.setCajasPendientesRecibir(pendienteRecibir.longValue());
			}
			output.add(alarma);
		}
		
		return output;
	}

	private List<AlarmaPLU> setStatusToList(final List<AlarmaPLU> currentList) throws Exception {
		List<AlarmaPLU> output = new ArrayList<AlarmaPLU>();
		logger.info("setStatusToList - currentList.size = "+currentList.size());

		for(AlarmaPLU alarma : currentList){
			Long plu = alarma.getPlu();
			Double stock = alarma.getStock();
			String albaranes = alarma.getAlbaranes();
			Date fechaUltimaVenta = alarma.getFechaUltimaVenta();
			String compraVenta = alarma.getCompraVenta();
			final Long codCentro = alarma.getCodCentro();
			final Long codArt = alarma.getCodArticulo();
			final Long grupoBalanza = alarma.getGrupoBalanza();
			final Long cajasPendientesRecibir = alarma.getCajasPendientesRecibir();
			Boolean existeEnOtraAgrupBal=alarmasPLUDao.existeReferenciaDiferenteGrupoBalanza(codCentro, codArt, grupoBalanza);
			if (plu!=null && stock!=null && plu.intValue()==0 && !existeEnOtraAgrupBal && (stock.doubleValue() != 0 || albaranes=="S" || cajasPendientesRecibir != null)){
				// ESTADO 1: PLU = 0 AND ( (STOCK > 0) OR ALBARANES = 'S') (ROJO)
				alarma.setEstadoGrid(1L);
			}else if (plu!=null && stock!=null && plu.intValue()!=0 && fechaVentaUltimos10Dias(fechaUltimaVenta) && compraVenta!="COMPRA"){
				// ESTADO 2: PLU <> 0 AND TRUNC(ULTIMA_VENTA) < TRUNC(SYSDATE-1) AND COMPRA_VENTA <> 'C' (AMARILLO)
				alarma.setEstadoGrid(2L);
			}else{
				// ESTADO 3: EL RESTO (VERDE)
				alarma.setEstadoGrid(3L);	
			}
			output.add(alarma);
		}
		logger.info("setStatusToList - output.size = "+output.size());
		return output;
	}
	
	// MISUMI-312: NO mostrar las referencias con venta en los 3 últimos días y esos tres días de ventas NO tenía plu
	private boolean analizarUltimos10Dias(AlarmaPLU alarma) {
		boolean output = false;
		final Date fechaUltimaVenta = alarma.getFechaUltimaVenta();
		boolean tieneVentasEnUltimos10Dias = false;
		if (fechaUltimaVenta!=null){
			tieneVentasEnUltimos10Dias = isSameDay(fechaUltimaVenta,today());
			for(int i=1;i<=10 && !tieneVentasEnUltimos10Dias;i++){
				tieneVentasEnUltimos10Dias = isSameDay(fechaUltimaVenta,pastDay(i));
			}
		}
		if (tieneVentasEnUltimos10Dias){
			Long plu = alarma.getPlu();
			Long plu_1 = alarma.getPlu_1();
			Long pluOriginal = alarma.getPluOriginal();
			Long pluOriginal_1 = alarma.getPluOriginal_1();
			
			boolean pluZero = (plu.equals(0L) && plu_1!=null && plu_1.equals(0L));
			boolean pluOriginalZero = (pluOriginal.equals(0L) && pluOriginal_1!=null && pluOriginal_1.equals(0L));
			
			if (pluZero && pluOriginalZero){
				output = true;
			}
		}
		return output;
	}

	/* Comprueba si se han producido ventas en los últimos 10 días*/
	private boolean fechaVentaUltimos10Dias(Date fechaUltimaVenta) {
		boolean tieneVentasEnUltimos10Dias = false;
		if (fechaUltimaVenta!=null){
			tieneVentasEnUltimos10Dias = isSameDay(fechaUltimaVenta,today());
			for(int i=1;i<=10 && !tieneVentasEnUltimos10Dias;i++){
				tieneVentasEnUltimos10Dias = isSameDay(fechaUltimaVenta,pastDay(i));
			}
			return !tieneVentasEnUltimos10Dias;
		}else{
			return true;
		}
		
	}
	
	public static boolean isSameDay(Date date1, Date date2) {
	    Calendar calendar1 = Calendar.getInstance();
	    calendar1.setTime(date1);
	    Calendar calendar2 = Calendar.getInstance();
	    calendar2.setTime(date2);
	    return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
	      && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
	      && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
	}
	
	private Date pastDay(int minusDay) {
	    final Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, (0-minusDay));
	    return cal.getTime();
	}
	
	private Date today() {
	    final Calendar cal = Calendar.getInstance();
	    return cal.getTime();
	}

	private List<AlarmaPLU> filterList(List<AlarmaPLU> currentList, Long codArea, Long codSeccion, Long agrupacion, String filtroReferencia, Boolean ocultarDatos) {
		logger.info("filterList");
		List<AlarmaPLU> output = new ArrayList<AlarmaPLU>();
		if (currentList!=null && currentList.size()>0){
			for (AlarmaPLU alarma : currentList){
				if (alarma!=null){
					final Long area = alarma.getGrupo1();
					final Long seccion = alarma.getGrupo2();
					final Long agru = alarma.getGrupoBalanza();
					final Long codArt = alarma.getCodArticulo();
					final Long plu = alarma.getPlu();
					final Double stock = alarma.getStock();
					final String albaranes = alarma.getAlbaranes();
					final Long pendientesRecibir = alarma.getCajasPendientesRecibir();
	
					if (ocultarDatos){
						// ELIMINAR DE LA LISTA: PLU = 0 AND (STOCK = 0 || CAJAS PENDIENTES RECIBIR > 0) AND ALBARANES <> 'S'
						//if (plu!=null && stock!=null && plu.intValue()==0 && stock.doubleValue() == 0 && albaranes!="S"){
						if (plu!=null && stock!=null && plu.intValue()==0 && stock.doubleValue() == 0 && pendientesRecibir == null && albaranes!="S"){
							continue;
						}
						// MISUMI-312: NO mostrar las referencias con venta en los 3 últimos días y esos tres días de ventas NO tenía plu
						if (analizarUltimos10Dias(alarma)){
							continue;
						} 
					}
					
					if (codArea!=null && !codArea.equals(area)){
						continue;
					}
		
					if (codSeccion!=null && !codSeccion.equals(seccion)){
						continue;
					}
		
					if (agrupacion!=null && !agrupacion.equals(agru)){
						continue;
					}
						
					if (filtroReferencia!=null && codArt!=null && !codArt.toString().contains(filtroReferencia)){
						continue;
					}
					output.add(alarma);
				}
			}
		}
		return output;
	}
	
	private List<AlarmaPLU> orderList(List<AlarmaPLU> currentList, Pagination pagination, Boolean useStatus) {
		List<AlarmaPLU> output = new ArrayList<AlarmaPLU>();
		
		if (pagination != null) {
			if (pagination.getSort() != null && !(pagination.getSort().equals(""))) {
				output = sortListBy(currentList, pagination.getSort(), pagination.getAscDsc());
				
			} else {
				output = sortListBy(currentList, "stock", "desc");
			}
		}else{
			output = sortListBy(currentList, "stock", "desc");
		}   
		if (useStatus){
			output = sortListBy(output, "status", "asc");
		}
		return output;
	}

	private int compareLong(Long value1, Long value2, String ascDsc){
		if (value1==null && value2==null){
			return 0;
		}else{
			if (ascDsc.equalsIgnoreCase("asc")){
				if (value1==null){
					return -1;
				}else if (value2==null){
					return 1;
				}else
					return value1.compareTo(value2);	
		    }else{
		    	if (value1==null){
					return 1;
				}else if (value2==null){
					return -1;
				}else
					return value2.compareTo(value1);	
		    }
		}
	}
	
	private int compareDouble(Double value1, Double value2, String ascDsc){
		if (value1==null && value2==null){
			return 0;
		}else{
			if (ascDsc.equalsIgnoreCase("asc")){
				if (value1==null){
					return -1;
				}else if (value2==null){
					return 1;
				}else
					return value1.compareTo(value2);	
		    }else{
		    	if (value1==null){
					return 1;
				}else if (value2==null){
					return -1;
				}else
					return value2.compareTo(value1);	
		    }
		}
	}
	
	private int compareString(String value1, String value2, String ascDsc){
		if (value1==null && value2==null){
			return 0;
		}else{
			if (ascDsc.equalsIgnoreCase("asc")){
				if (value1==null){
					return -1;
				}else if (value2==null){
					return 1;
				}else
					return value1.compareTo(value2);	
		    }else{
		    	if (value1==null){
					return 1;
				}else if (value2==null){
					return -1;
				}else
					return value2.compareTo(value1);	
		    }
		}
	}
	
	private int compareDate(Date value1, Date value2, String ascDsc){
		if (value1==null && value2==null){
			return 0;
		}else{
			if (ascDsc.equalsIgnoreCase("asc")){
				if (value1==null){
					return -1;
				}else if (value2==null){
					return 1;
				}else
					return value1.compareTo(value2);	
		    }else{
		    	if (value1==null){
					return 1;
				}else if (value2==null){
					return -1;
				}else
					return value2.compareTo(value1);	
		    }
		}
	}
	
	private List<AlarmaPLU> sortListBy(List<AlarmaPLU> list, String fieldName, final String ascDsc) {
		List<AlarmaPLU> output = list;
		if(fieldName.equals("codArticulo")){
			Collections.sort(output, new Comparator<AlarmaPLU>() {
			    @Override
			    public int compare(AlarmaPLU o1, AlarmaPLU o2) {
			        Long value1 = o1.getCodArticulo();
			        Long value2 = o2.getCodArticulo();
			        return compareLong(value1, value2, ascDsc);
			    }
			});
		}else if(fieldName.equals("denominacion")){
			Collections.sort(output, new Comparator<AlarmaPLU>() {
			    @Override
			    public int compare(AlarmaPLU o1, AlarmaPLU o2) {
			        String value1 = o1.getDenominacion();
			        String value2 = o2.getDenominacion();
			        return compareString(value1, value2, ascDsc);
			    }
			});
		}else if(fieldName.equals("agrupacion")){
			Collections.sort(output, new Comparator<AlarmaPLU>() {
			    @Override
			    public int compare(AlarmaPLU o1, AlarmaPLU o2) {
			        Long value1 = o1.getGrupo1();
			        Long value2 = o2.getGrupo1();
			        int res = compareLong(value1, value2, ascDsc);
			        if (res==0){
				        value1 = o1.getGrupo2();
				        value2 = o2.getGrupo2();
			        	res = compareLong(value1, value2, ascDsc);
				        if (res==0){
					        value1 = o1.getGrupo3();
					        value2 = o2.getGrupo3();
				        	res = compareLong(value1, value2, ascDsc);
					        if (res==0){
						        value1 = o1.getGrupo4();
						        value2 = o2.getGrupo4();
					        	res = compareLong(value1, value2, ascDsc);
						        if (res==0){
							        value1 = o1.getGrupo5();
							        value2 = o2.getGrupo5();
						        	res = compareLong(value1, value2, ascDsc);
						        }
					        }
				        }
			        }
			        
			        return res;
			    }
			});
			
		}else if(fieldName.equals("grupo3")){
			Collections.sort(output, new Comparator<AlarmaPLU>() {
			    @Override
			    public int compare(AlarmaPLU o1, AlarmaPLU o2) {
			        Long value1 = o1.getGrupo3();
			        Long value2 = o2.getGrupo3();
			        return compareLong(value1, value2, ascDsc);
			    }
			});
		}else if(fieldName.equals("grupo4")){
			Collections.sort(output, new Comparator<AlarmaPLU>() {
			    @Override
			    public int compare(AlarmaPLU o1, AlarmaPLU o2) {
			        Long value1 = o1.getGrupo4();
			        Long value2 = o2.getGrupo4();
			        return compareLong(value1, value2, ascDsc);
			    }
			});
		}else if(fieldName.equals("grupo5")){
			Collections.sort(output, new Comparator<AlarmaPLU>() {
			    @Override
			    public int compare(AlarmaPLU o1, AlarmaPLU o2) {
			        Long value1 = o1.getGrupo5();
			        Long value2 = o2.getGrupo5();
			        return compareLong(value1, value2, ascDsc);
			    }
			});
		}else if(fieldName.equals("denomSegmento")){
			Collections.sort(output, new Comparator<AlarmaPLU>() {
			    @Override
			    public int compare(AlarmaPLU o1, AlarmaPLU o2) {
			        String value1 = o1.getDenomSegmento();
			        String value2 = o2.getDenomSegmento();
			        return compareString(value1, value2, ascDsc);
			    }
			});
		}else if(fieldName.equals("marca")){
			Collections.sort(output, new Comparator<AlarmaPLU>() {
			    @Override
			    public int compare(AlarmaPLU o1, AlarmaPLU o2) {
			        String value1 = o1.getMarca();
			        String value2 = o2.getMarca();
			        return compareString(value1, value2, ascDsc);
			    }
			});
		}else if(fieldName.equals("grupoBalanza")){
			Collections.sort(output, new Comparator<AlarmaPLU>() {
			    @Override
			    public int compare(AlarmaPLU o1, AlarmaPLU o2) {
			        Long value1 = o1.getGrupoBalanza();
			        Long value2 = o2.getGrupoBalanza();
			        return compareLong(value1, value2, ascDsc);
			    }
			});
		}else if(fieldName.equals("plu")){
			Collections.sort(output, new Comparator<AlarmaPLU>() {
			    @Override
			    public int compare(AlarmaPLU o1, AlarmaPLU o2) {
			        Long value1 = o1.getPlu();
			        Long value2 = o2.getPlu();
			        return compareLong(value1, value2, ascDsc);
			    }
			});
		}else if(fieldName.equals("diasCaducidad")){
			Collections.sort(output, new Comparator<AlarmaPLU>() {
			    @Override
			    public int compare(AlarmaPLU o1, AlarmaPLU o2) {
			        Long value1 = o1.getDiasCaducidad();
			        Long value2 = o2.getDiasCaducidad();
			        return compareLong(value1, value2, ascDsc);
			    }
			});
		}else if(fieldName.equals("mmc")){
			Collections.sort(output, new Comparator<AlarmaPLU>() {
			    @Override
			    public int compare(AlarmaPLU o1, AlarmaPLU o2) {
			        String value1 = o1.getMmc();
			        String value2 = o2.getMmc();
			        return compareString(value1, value2, ascDsc);
			    }
			});
		}else if(fieldName.equals("eb")){
			Collections.sort(output, new Comparator<AlarmaPLU>() {
			    @Override
			    public int compare(AlarmaPLU o1, AlarmaPLU o2) {
			        String value1 = o1.getEb();
			        String value2 = o2.getEb();
			        return compareString(value1, value2, ascDsc);
			    }
			});		
		}else if(fieldName.equals("fechaUltimaVenta")){
			Collections.sort(output, new Comparator<AlarmaPLU>() {
			    @Override
			    public int compare(AlarmaPLU o1, AlarmaPLU o2) {
			        Date value1 = o1.getFechaUltimaVenta();
			        Date value2 = o2.getFechaUltimaVenta();
			        return compareDate(value1, value2, ascDsc);
			    }
			});	
		}else if(fieldName.equals("stock")){
			Collections.sort(output, new Comparator<AlarmaPLU>() {
			    @Override
			    public int compare(AlarmaPLU o1, AlarmaPLU o2) {
			        Double value1 = o1.getStock();
			        Double value2 = o2.getStock();
			        return compareDouble(value1, value2, ascDsc);
			    }
			});	
		}else if(fieldName.equals("compraVenta")){
			Collections.sort(output, new Comparator<AlarmaPLU>() {
			    @Override
			    public int compare(AlarmaPLU o1, AlarmaPLU o2) {
			        String value1 = o1.getCompraVenta();
			        String value2 = o2.getCompraVenta();
			        return compareString(value1, value2, ascDsc);
			    }
			});	
		}else if(fieldName.equals("status")){
			Collections.sort(output, new Comparator<AlarmaPLU>() {
			    @Override
			    public int compare(AlarmaPLU o1, AlarmaPLU o2) {
			        Long value1 = o1.getEstadoGrid();
			        Long value2 = o2.getEstadoGrid();
			        return compareLong(value1, value2, "asc");
			    }
			});	
		}else if(fieldName.equals("cajasPendientesRecibir")){
			Collections.sort(output, new Comparator<AlarmaPLU>() {
			    @Override
			    public int compare(AlarmaPLU o1, AlarmaPLU o2) {
			        Long value1 = o1.getCajasPendientesRecibir();
			        Long value2 = o2.getCajasPendientesRecibir();
			        return compareLong(value1, value2, ascDsc);
			    }
			});	
		}else{
			Collections.sort(output, new Comparator<AlarmaPLU>() {
			    @Override
			    public int compare(AlarmaPLU o1, AlarmaPLU o2) {
			        Double value1 = o1.getStock();
			        Double value2 = o2.getStock();
			        return compareDouble(value1, value2, ascDsc);
			    }
			});	
		}
		
		return output;
	}
	
	@Override
	public List<GenericExcelFieldsVO> findAllExcel(Pagination pagination, String[] headers, Long codCentro, Long codArea, Long codSeccion, Long agrupacion, String filtroReferencia) throws Exception {
		
		logger.info("alarmasPLU - findAllExcel");
		final List<AlarmaPLU> list = this.findAllInMemory(pagination, codCentro, codArea, codSeccion, agrupacion, filtroReferencia, true, null);
		
		final List<AlarmaPLU> listConPendienteRecibir = setPendienteRecibir(list);
		
		List<GenericExcelFieldsVO> output = new ArrayList<GenericExcelFieldsVO>();

	    try {
	    	for (int i=0; i<listConPendienteRecibir.size(); i++){
	    		AlarmaPLU item = listConPendienteRecibir.get(i);
	    		GenericExcelFieldsVO newItem = parseAlarmaPLUToGenericExcelVO(headers, item, i);
	    		output.add(newItem);
		    }
		} catch (Exception e){
			e.printStackTrace();
		}
	    return output;
	}
	
	private GenericExcelFieldsVO parseAlarmaPLUToGenericExcelVO(String[] headers, AlarmaPLU item, Integer index) {
		GenericExcelFieldsVO output = new GenericExcelFieldsVO();
		
		for (String header : headers){
			Object elem = getItemToShow(header, item, index);
			output.addField(elem);
		}
		
		return output;
	}
	
	private Object getItemToShow(String header, AlarmaPLU item, Integer index) {
		
		Object output = null;
		String headerFiltered = Utilidades.removeAccents(header);
		headerFiltered = headerFiltered.trim();
		
		if (headerFiltered.equals("REF")){
			output = item.getCodArticulo();
		}else if (headerFiltered.equals("DENOMINACION")){
			output = item.getDenominacion();
		}else if (headerFiltered.equals("AGRUPACION")){
			output = item.getAgrupacion();			
		}else if (headerFiltered.equals("CAT")){
			output = item.getGrupo3();
		}else if (headerFiltered.equals("SCAT")){
			output = item.getGrupo4();
		}else if (headerFiltered.equals("SEG")){
			output = item.getGrupo5();
		}else if (headerFiltered.contains("DENO") && (header.contains("SEG"))){
			output = item.getDenomSegmento();
		}else if (headerFiltered.equals("MARCA")){
			output = item.getMarca();
		}else if (headerFiltered.equals("COD")){
			output = item.getGrupoBalanza();
		}else if (headerFiltered.equals("PLUs")){
			output = item.getPlu();
		}else if (headerFiltered.equals("CAD")){
			output = item.getDiasCaducidad();			
		}else if (headerFiltered.equals("MMC")){
			output = item.getMmc();
		}else if (headerFiltered.equals("ULTIMA VENTA")){
			output = Utilidades.obtenerFechaFormateadaddMMyyyy(item.getFechaUltimaVenta());
		}else if (headerFiltered.equals("STOCK")){
			output = item.getStock();
		}else if (headerFiltered.equals("COMPRA-VENTA/SOLO-VENTA")){
			output = item.getCompraVenta();
		}else if (headerFiltered.equals("ELABORABLE")){
			output = item.getEb();
		}else if (headerFiltered.equals("HIDDEN_STATUS")){
			output = item.getEstadoGrid();
		}else if (headerFiltered.equals("PENDIENTE RECIBIR")){
			output = (item.getCajasPendientesRecibir()!=null && item.getCajasPendientesRecibir()>0)?"*":"";
		}else if (headerFiltered.equals("#")){
			output = index;
		}
		return output;
	}
	
	@Override
	public Integer[] counters(Long codCentro, Long codArea, Long codSeccion, Long agrupacion) throws Exception{
		//return alarmasPLUDao.counters(codCentro, codArea, codSeccion, agrupacion);
		
		Integer[] output = new Integer[3];
		final List<AlarmaPLU> alarmas = alarmListModified.get(codCentro);
		final List<AlarmaPLU> alarmasFiltered = filterList(alarmas, codArea, codSeccion, agrupacion, null, true);
		
		Integer counter1 = getCounter1(alarmasFiltered);
		Integer counter2 = getCounter2(alarmasFiltered);
		Integer counter3 = getCounter3(alarmasFiltered);
		
		output[0] = counter1;
		output[1] = counter2;
		output[2] = counter3;

		return output;
		
	}
	
	private Integer getCounter1(List<AlarmaPLU> alarmasFiltered) {
		
		Integer output = 0;
		
		for (AlarmaPLU alarma : alarmasFiltered){
			Long plu = alarma.getPlu();
			// PLU <> 0
			if (plu!=null && plu.longValue()!=0){
				output++;
			}
		}
		return output;
		
	}
	
	private Integer getCounter2(List<AlarmaPLU> alarmasFiltered) {
		
		Integer output = 0;
		
		for (AlarmaPLU alarma : alarmasFiltered){
			Double stock = alarma.getStock();
			String flgVariable = alarma.getFlgVariable();
			// STOCK <> 0 AND FLG_VARIABLE = 'S'
			if (stock!=null && flgVariable!=null && stock.doubleValue()!=0.0 && flgVariable.equals("S")){
				output++;
			}
		}
		return output;
		
	}
	
	private Integer getCounter3(List<AlarmaPLU> alarmasFiltered) {
		
		Integer output = 0;
		
		for (AlarmaPLU alarma : alarmasFiltered){
			Long plu = alarma.getPlu();
			String compraVenta = alarma.getCompraVenta();
			// PLU <> 0 AND COMPRA_VENTA='V'
			if (plu!=null && compraVenta!=null && plu.longValue()!=0 && compraVenta.equals("VENTA")){
				output++;
			}
		}
		return output;
		
	}
	

	@Override
	public Map<Long, String> getAreasPLUs(Long codCentro) throws Exception{
		
		Map<Long, String> output = new HashMap<Long, String>();
		
		final List<AlarmaPLU> alarmas = alarmListOrig.get(codCentro);
		for (AlarmaPLU alarma : alarmas){
			Long area = alarma.getGrupo1();
			if (!output.containsKey(area)){
				String descArea = alarma.getDescGrupo1();
				output.put(area, descArea);
			}
		}
		return output;
	}

	@Override
	public Map<Long, String> getSeccionesPLUs(Long codCentro, Long codArea) throws Exception{
		
		Map<Long, String> output = new HashMap<Long, String>();
		
		final List<AlarmaPLU> alarmas = alarmListOrig.get(codCentro);
		final List<AlarmaPLU> alarmasFiltered = filterList(alarmas, codArea, null, null, null, false);
		
		for (AlarmaPLU alarma : alarmasFiltered){
			Long seccion = alarma.getGrupo2();
			if (!output.containsKey(seccion)){
				String descSeccion = alarma.getDescGrupo2();
				output.put(seccion, descSeccion);
			}
		}
		return output;
	}
	
	@Override
	public Map<Long, String> getAgrupacionesPLUs(Long codCentro, Long codArea, Long codSeccion) throws Exception{
		
		Map<Long, String> output = new HashMap<Long, String>();
		
		final List<AlarmaPLU> alarmas = alarmListOrig.get(codCentro);
		final List<AlarmaPLU> alarmasFiltered = filterList(alarmas, codArea, codSeccion, null, null, false);
		logger.info("getAgrupacionesPLUs - INICIO - alarmasFiltered.size = "+alarmasFiltered.size());
		for (AlarmaPLU alarma : alarmasFiltered){
			Long agru = alarma.getGrupoBalanza();
			if (!output.containsKey(agru)){
				String descAgru = alarma.getDenominacionBalanza();
				output.put(agru, descAgru);
			}
		}
		return output;
	}
	
	@Override
	public List<Long> plusLibres(Long codCentro, Long codArea, Long codSeccion, Long agrupacion) throws Exception{
		//return alarmasPLUDao.plusLibres(codCentro, codArea, codSeccion, agrupacion);
		
		// Lista con todos los plus
		List<Long> output = new ArrayList<Long>();
		
		Integer numMaxPLU = alarmasPLUDao.getMaxPLUS(codCentro, agrupacion);
		
		for (long i=1; i<=Math.abs(numMaxPLU); i++){
			output.add(i);
		}
		
		// Lista de plus ocupados
		final List<AlarmaPLU> alarmas = alarmListOrig.get(codCentro);
		final List<AlarmaPLU> alarmasFiltered = filterList(alarmas, codArea, codSeccion, agrupacion, null, true);
		List<Long> plusOcupados = new ArrayList<Long>();

		for(AlarmaPLU alarma : alarmasFiltered){
			Long plu = alarma.getPlu();
			if (!plusOcupados.contains(plu)){
				plusOcupados.add(plu);
			}
		}
		
		Collections.sort(plusOcupados);
		for (Long plu : plusOcupados) {
			output.remove(plu);
		}
		output.add(0, numMaxPLU.longValue());
		Collections.sort(plusOcupados);
		
		Long last = 0L;
		if (plusOcupados.size()>0){
			last = plusOcupados.get(plusOcupados.size() - 1);	
		}
		
		output.add(1, last);
		return output;
		
	}
	
	@Override
	public String checkTeclasBalanza(Long codCentro, Long codArea, Long codSeccion, Long agrupacion) throws Exception{
		//return alarmasPLUDao.checkTeclasBalanza(codCentro, codArea, codSeccion);
		String output = null;
		
		final List<Long> plusLibres = plusLibres(codCentro, codArea, codSeccion, agrupacion);
		if (plusLibres.size()==0){
			output = agrupacion.toString();
		}
		return output;
	}
	
	@Override
	public void asignar(Long codCentro, Long plu, Long referencia, Long grupoBalanza) throws Exception{
		final List<AlarmaPLU> lista = alarmListModified.get(codCentro);
		boolean incluido=false;
		for (int i=0; i<lista.size(); i++){
			AlarmaPLU alarma = lista.get(i);
			final Long ref = alarma.getCodArticulo();
			final Long gBalanza = alarma.getGrupoBalanza();
			if (ref!=null && ref.equals(referencia) && gBalanza!=null && gBalanza.equals(grupoBalanza)){
				alarma.setPlu(plu);
				lista.set(i, alarma);
				incluido=true;
				break;
			}
		}
		if (incluido==false){
			List<AlarmaPLU> listaComp = new ArrayList<AlarmaPLU>();
			listaComp = alarmasPLUDao.findAll(null, codCentro, null, null, null);
			for (int i=0; i<listaComp.size(); i++){
				AlarmaPLU alarma = listaComp.get(i);
				final Long ref = alarma.getCodArticulo();
				final Long gBalanza = alarma.getGrupoBalanza();
				if (ref!=null && ref.equals(referencia) && gBalanza!=null && gBalanza.equals(grupoBalanza)){
					alarma.setPlu(plu);
					lista.add(alarma);
					break;
				}
			}
		}
		alarmListModified.put(codCentro, lista);
	}
	
	@Override
	public void aniadirAlarma(Long codCentro,AlarmaPLU alarma)throws Exception{
		final List<AlarmaPLU> lista = alarmListModified.get(codCentro);
		AlarmaPLU alarmaNueva=this.findOne(alarma.getCodCentro(), alarma.getCodArticulo(), alarma.getGrupoBalanza());
		lista.add(alarmaNueva);
		alarmListModified.put(codCentro, lista);
	}
	
	@Override
	public void eliminar(Long codCentro, Long[] referencias, Long  grupoBalanza) throws Exception{
		
		logger.info("eliminar - modifiedAlarmList = true - codCentro = "+codCentro);

		final List<AlarmaPLU> lista = alarmListModified.get(codCentro);
		for (Long referencia : referencias){
			for (int i=0; i<lista.size(); i++){
				AlarmaPLU alarma = lista.get(i);
				final Long ref = alarma.getCodArticulo();
				final Long gBalanza = alarma.getGrupoBalanza();
				if (ref!=null && ref.equals(referencia) && gBalanza!=null && gBalanza.equals(grupoBalanza)){
					alarma.setPlu(0L);
					lista.set(i, alarma);
					break;
				}
			}
		}
		alarmListModified.put(codCentro, lista);
	}
	
	@Override
	public List<String> hayAvisosPLU(Long codCentro) throws Exception{
		final List<String> output = new ArrayList<String>();

		// 1 - Cargamos las alarmas filtradas
		List<AlarmaPLU> alarmas = findAllAvisos(null,codCentro,null,null, null,null,false,null);
		if (alarmas.size()==0){
			initModifiedAlarmList(codCentro);
			alarmas = findAllAvisosVacio(null,codCentro,null,null, null,null,false,null);
		}
		// 2- recorremos el listado de alarmas filtradas
		for (AlarmaPLU alarma : alarmas){
			final Long plu = alarma.getPlu();
			final Double stock = alarma.getStock();
			final String grupo2 = alarma.getDescGrupo2();
			final Long cajasPendientesRecibir = alarma.getCajasPendientesRecibir();
			// Si tienen plu=0 y stock>0, guardamos su seccion en el listado de salida
			if (plu==0){
				// stock > 0 o stock=0 pero tiene cajas pendientes de recibir, guardamos su seccion en el listado de salida
				if (stock!=0 || cajasPendientesRecibir>0){
					if (!output.contains(grupo2)){
						final Long codArt = alarma.getCodArticulo();
						final Long grupoBalanza = alarma.getGrupoBalanza();
						Boolean existeEnOtraAgrupBal=alarmasPLUDao.existeReferenciaDiferenteGrupoBalanza(codCentro, codArt, grupoBalanza);
						if(!existeEnOtraAgrupBal){
							output.add(grupo2);
						}
					}
				}
			}
		}
		
		return output;
	}
	
	@Override
	public boolean hayDatosDeHoyEnTablaIntermedia(Long codCentro) throws Exception{
		return alarmasPLUDao.hayDatosDeHoyEnTablaIntermedia(codCentro);
	}
		
	@Override
	public List<AlarmaPLU> obtenerDatosGisae(Long codCentro, Long codArticulo){
		// Parametros de la llamada: 
		// -Codigo de centro
		// -Referencia = 99999999 (8 nueves para obtener todas las referencias)
		// -Tipo de mantenimiento = CP
		// -Grupo de Balanza = vacio
		// -PLU = vacio
		List<AlarmaPLU> output = null;
		Locale locale = LocaleContextHolder.getLocale();
		ConsultarPLUSRequestType request = new ConsultarPLUSRequestType();
		request.setCodigoCentro(BigInteger.valueOf(codCentro));
		if (codArticulo==null){
			codArticulo = 99999999L;
		}
		request.setCodigoReferencia(BigInteger.valueOf(codArticulo));

		request.setTipoMantenimiento("CP");
		ConsultarPLUSResponseType response = null;
		try {
			response = alarmasPLUDao.getPLUsGISAE(request);
		} catch (Exception e) {
			logger.error(messageSource.getMessage("p107_alarmasPLU.errorWS", null, locale) + " - ERROR EN LA CONSULTA DEL ARTICULO "+codArticulo+" PARA EL CENTRO "+codCentro+" : "+e.getMessage());
		}
		if (response !=null){
			// RESPUESTAS:
			// -OK: EL RESULTADO ES CORRECTO
			// -WN: EL RESULTADO ES CORRECTO, PERO HAY ALGUNAS REFERENCIAS QUE HAN DADO ERROR Y PUEDE QUE VENGAN MAL
			// -OK: EL RESULTADO NO ES CORRECTO
			if (response.getCodigoRespuesta().equals("OK") || response.getCodigoRespuesta().equals("WN")){
				if (response.getReferencias()!=null){
					output = alarmasPLUDao.fromGisaeResponseToAlarmasPLU(response.getReferencias(), codCentro);
				}else{
					logger.error(messageSource.getMessage("p107_alarmasPLU.errorWS", null, locale) + ": ERROR EN LA CONSULTA DEL ARTICULO "+codArticulo+" PARA EL CENTRO "+codCentro+" : NO SE HA DEVUELTO NINGUN REGISTRO");
				}
			}else{
				logger.error(messageSource.getMessage("p107_alarmasPLU.errorWS", null, locale) + ": ERROR EN LA CONSULTA DEL ARTICULO "+codArticulo+" PARA EL CENTRO "+codCentro+" : ("+response.getCodigoRespuesta()+") "+response.getDescripcionRespuesta());
			}
			
		}
		return output;
	}
	
	@Override
	public void cargarDatosEnTablaIntermedia(Long codCentro) throws Exception{
		
		// 1) Cargar datos de GISAE
		logger.info("cargarDatosEnTablaIntermedia - cargar datos de GISAE");
		List<AlarmaPLU> lista = obtenerDatosGisae(codCentro,null);		
		if (lista!=null){
			alarmasPLUDao.insertarDatosEnTablaIntermedia(lista);
		}
		logger.info("cargarDatosEnTablaIntermedia - cargar datos extra: STOCK");
		// 2) Cargar datos extra en la tabla intermedia: stock
		List<String> extra = alarmasPLUDao.cargaExtraStockAlarmasPLU(codCentro);
		for (String elemExtra : extra){
			lista = obtenerDatosGisae(codCentro,Long.valueOf(elemExtra));
			if (lista!=null){
				alarmasPLUDao.insertarDatosEnTablaIntermedia(lista);	
			}
		}
		logger.info("cargarDatosEnTablaIntermedia - cargar datos extra: ALBARANES");
		// 3) Cargar datos extra en la tabla intermedia: albaranes
		extra = alarmasPLUDao.cargaExtraAlbaranesAlarmasPLU(codCentro);
		for (String elemExtra : extra){
			lista = obtenerDatosGisae(codCentro,Long.valueOf(elemExtra));
			if (lista!=null){
				alarmasPLUDao.insertarDatosEnTablaIntermedia(lista);	
			}
		}

	}
	
	@Override
	public void initModifiedAlarmList(Long codCentro) throws Exception {
		logger.info("initModifiedAlarmList - codCentro = "+codCentro);
		List<AlarmaPLU> lista = new ArrayList<AlarmaPLU>();
		lista = alarmasPLUDao.findAll(null, codCentro, null, null, null);
		final List<AlarmaPLU> listConPendienteRecibir = setPendienteRecibir(lista);
		alarmListOrig.put(codCentro, listConPendienteRecibir);
		alarmListModified.put(codCentro, listConPendienteRecibir);

	}
	
	@Override
	public void initModifiedAlarmList(Long codCentro, List<AlarmaPLU> alarmas) throws Exception {
		logger.info("initModifiedAlarmList - codCentro = "+codCentro);
		alarmListModified.put(codCentro, alarmas);
	}
	
	@Override
	public Long altaReferenciaGisae(Long codCentro, Long plu,Long referencia, Long agrupacionBalanza) throws NoSuchMessageException, Exception {
		List<AlarmaPLU> alarmasConCambios = new ArrayList<AlarmaPLU>();
		AlarmaPLU alarma= new AlarmaPLU();
		alarma.setCodCentro(codCentro);
		alarma.setPlu(plu);
		alarma.setPluAnt(0L);
		alarma.setCodArticulo(referencia);
		alarma.setGrupoBalanza(agrupacionBalanza);
		alarmasConCambios.add(alarma);
		ModificarPLUSRequestType request = new ModificarPLUSRequestType();
		request.setCodigoCentro(BigInteger.valueOf(codCentro));
		request.setTipoMantenimiento("AG");
		ReferenciaModType[] refs = fromAlarmasToDatosGISAE(alarmasConCambios);
		request.setReferencias(refs);
		ModificarPLUSResponseType response = null;
		try {
			response = alarmasPLUDao.setPLUsGISAE(request);
		} catch (Exception e) {
			Locale locale = LocaleContextHolder.getLocale();
			e.printStackTrace();
			throw new Exception(messageSource.getMessage("p107_alarmasPLU.errorWS", null, locale) + ": "+e.getMessage());
		}
		logger.info("altaReferenciaGisae - response - codigoRespuesta = "+response.getCodigoRespuesta());
		if (response!=null && (response.getCodigoRespuesta().equalsIgnoreCase("OK"))){
			// Si todo ha ido bien, se comitan todos los cambios
			return alarmasConCambios.get(0).getCodArticulo();
		}else{
			return null;
		}
	}

	@Override
	public List<Long> modificarDatosGisae(Long codCentro, Long agrupacionBalanza, String user) throws NoSuchMessageException, Exception {
		logger.info("modificarDatosGisae - codCentro = "+codCentro+" - agrupacionBalanza = "+agrupacionBalanza);
		List<AlarmaPLU> alarmas = alarmListModified.get(codCentro);
		List<AlarmaPLU> alarmasConCambios = new ArrayList<AlarmaPLU>();
		List<Long> referenciasAImprimir = new ArrayList<Long>();
		
		for (AlarmaPLU alarma : alarmas){
			final Long plu = alarma.getPlu();
			final Long agruBalanza = alarma.getGrupoBalanza();
			final Long pluAnt = alarma.getPluAnt();
			if (plu!=null && agruBalanza!=null && !plu.equals(pluAnt) && agruBalanza.equals(agrupacionBalanza)){
				alarmasConCambios.add(alarma);
			}
		}
		logger.info("modificarDatosGisae - alarmas con cambios = "+alarmasConCambios.size());
		if (alarmasConCambios.size()>0){
																																																																								// Parametros de la llamada: 
			// -Codigo de centro
			// -Tipo de mantenimiento = MP
			// -REFERENCIA [1..N]
			// --CODIGO REFEREENCIA: 
			// --GRUPO BALANZA:
			// --PLU: 
			// --PLU ANTIGUO: 
			ModificarPLUSRequestType request = new ModificarPLUSRequestType();
			request.setCodigoCentro(BigInteger.valueOf(codCentro));
			request.setTipoMantenimiento("MP");
			
			ReferenciaModType[] refs = fromAlarmasToDatosGISAE(alarmasConCambios);
			request.setReferencias(refs);
			ModificarPLUSResponseType response = null;
			try {
				response = alarmasPLUDao.setPLUsGISAE(request);
			} catch (Exception e) {
				Locale locale = LocaleContextHolder.getLocale();
				e.printStackTrace();
				throw new Exception(messageSource.getMessage("p107_alarmasPLU.errorWS", null, locale) + ": "+e.getMessage());
			}
			logger.info("modificarDatosGisae - response - codigoRespuesta = "+response.getCodigoRespuesta());
			if (response!=null && (response.getCodigoRespuesta().equalsIgnoreCase("OK"))){
				// Si todo ha ido bien, se comitan todos los cambios
				final String msg = "OK#"+user;
				commitAll(codCentro, alarmasConCambios, msg);
				for (AlarmaPLU alarma : alarmasConCambios){
					Long plu = alarma.getPlu();
					if (plu!=null && !plu.equals(0L)){
						referenciasAImprimir.add(alarma.getCodArticulo());	
					}
				}
			}else{
				logger.info("modificarDatosGisae - response - descRespuesta = "+response.getDescripcionRespuesta());
				// Si vienen referencias en la respuesta, se comitan las que hayan salido bien y se echan para atras las que tengan error
				if (response.getReferencias()!=null && response.getReferencias().length>0){
					for (ReferenciaResModType ref : response.getReferencias()){
						Long codArticulo = ref.getCodigoReferencia()!=null?ref.getCodigoReferencia().longValue():null;
						Long agruBalanza = ref.getGrupoBalanza()!=null?ref.getGrupoBalanza().longValue():null;
						if (codArticulo!=null && agruBalanza!=null){
							if (ref.getCodigoError() != null && ref.getCodigoError().intValue() > 0){
								String parsedMsgError = parseMsgError(ref.getMensajeError());
								String currentMsgError = "ERROR al asignar el plu " +ref.getPLU()+ " a la referencia "+codArticulo+" en el grupo de balanza "+agruBalanza+". RAZON:"+parsedMsgError+"). ";
								rollback(codCentro, ref.getCodigoReferencia().longValue(),ref.getGrupoBalanza().longValue(), currentMsgError);
							}else{
								BigInteger plu = ref.getPLU();
								if (plu!=null && plu.longValue()!=0){
									referenciasAImprimir.add(codArticulo);	
								}
								final String msg = "OK#"+user;
								commit(codCentro, codArticulo, agruBalanza, msg, plu.longValue());
							}
						}
					}	
				}else{
					
					for (AlarmaPLU alarma : alarmasConCambios){
						Long codArticulo = alarma.getCodArticulo();
						Long agruBalanza = alarma.getGrupoBalanza();
						String msgError = "No se pudieron guardar los cambios: " +response.getCodigoRespuesta()+ " - " + response.getDescripcionRespuesta();
						rollback(codCentro, codArticulo,agruBalanza, msgError);
					}
				}
			}
			// Se recargan los datos de la base de datos en memoria
			initModifiedAlarmList(codCentro);
		}
		return referenciasAImprimir;
	}
	
	@Override
	public List<Long> modificarDatosGisaeTodos(Long codCentro, Long agrupacionBalanza, String user) throws NoSuchMessageException, Exception {
		logger.info("modificarDatosGisaeTodos - codCentro = "+codCentro+" - agrupacionBalanza = "+agrupacionBalanza);
		List<AlarmaPLU> alarmas = alarmListModified.get(codCentro);
		List<AlarmaPLU> alarmasConCambios = new ArrayList<AlarmaPLU>();
		List<AlarmaPLU> alarmasConCambiosTodasAgrupaciones = new ArrayList<AlarmaPLU>();
		List<Long> referenciasAImprimir = new ArrayList<Long>();
		
		for (AlarmaPLU alarma : alarmas){
			final Long plu = alarma.getPlu();
			final Long agruBalanza = alarma.getGrupoBalanza();
			final Long pluAnt = alarma.getPluAnt();
			if (plu!=null && agruBalanza!=null && !plu.equals(pluAnt) && agruBalanza.equals(agrupacionBalanza)){
				alarmasConCambios.add(alarma);
				alarmasConCambiosTodasAgrupaciones.add(alarma);
			}
		}
		logger.info("modificarDatosGisaeTodos - alarmas con cambios = "+alarmasConCambios.size());
		if (alarmasConCambios.size()>0){
			for (AlarmaPLU alarma : alarmasConCambios){
				if(alarma.getLstOtrasAgrupacionesBalanza() != null){
					String[] otrasAgrupacionesBalanza = alarma.getLstOtrasAgrupacionesBalanza().split(",");
					for(int i =0;i<otrasAgrupacionesBalanza.length;i++){
						AlarmaPLU aux=this.findOneInMemory(codCentro, alarma.getCodArticulo(), Long.parseLong(otrasAgrupacionesBalanza[i]));
						aux.setPluAnt(aux.getPlu());
						aux.setPlu(0L);
						alarmasConCambiosTodasAgrupaciones.add(aux);
					}
				}
				
			}
			// Parametros de la llamada: 
			// -Codigo de centro
			// -Tipo de mantenimiento = MP
			// -REFERENCIA [1..N]
			// --CODIGO REFEREENCIA: 
			// --GRUPO BALANZA:
			// --PLU: 
			// --PLU ANTIGUO: 
			ModificarPLUSRequestType request = new ModificarPLUSRequestType();
			request.setCodigoCentro(BigInteger.valueOf(codCentro));
			request.setTipoMantenimiento("MP");
			
			ReferenciaModType[] refs = fromAlarmasToDatosGISAE(alarmasConCambiosTodasAgrupaciones);
			request.setReferencias(refs);
			ModificarPLUSResponseType response = null;
			try {
				response = alarmasPLUDao.setPLUsGISAE(request);
			} catch (Exception e) {
				Locale locale = LocaleContextHolder.getLocale();
				e.printStackTrace();
				throw new Exception(messageSource.getMessage("p107_alarmasPLU.errorWS", null, locale) + ": "+e.getMessage());
			}
			logger.info("modificarDatosGisaeTodos - response - codigoRespuesta = "+response.getCodigoRespuesta());
			if (response!=null && (response.getCodigoRespuesta().equalsIgnoreCase("OK"))){
				// Si todo ha ido bien, se comitan todos los cambios
				final String msg = "OK#"+user;
				commitAll(codCentro, alarmasConCambiosTodasAgrupaciones, msg);
				for (AlarmaPLU alarma : alarmasConCambios){
					Long plu = alarma.getPlu();
					if (plu!=null && !plu.equals(0L)){
						referenciasAImprimir.add(alarma.getCodArticulo());	
					}
				}
			}else{
				logger.info("modificarDatosGisaeTodos - response - descRespuesta = "+response.getDescripcionRespuesta());
				// Si vienen referencias en la respuesta, se comitan las que hayan salido bien y se echan para atras las que tengan error
				if (response.getReferencias()!=null && response.getReferencias().length>0){
					for (ReferenciaResModType ref : response.getReferencias()){
						Long codArticulo = ref.getCodigoReferencia()!=null?ref.getCodigoReferencia().longValue():null;
						Long agruBalanza = ref.getGrupoBalanza()!=null?ref.getGrupoBalanza().longValue():null;
						if (codArticulo!=null && agruBalanza!=null){
							if (ref.getCodigoError() != null && ref.getCodigoError().intValue() > 0){
								String parsedMsgError = parseMsgError(ref.getMensajeError());
								String currentMsgError = "ERROR al asignar el plu " +ref.getPLU()+ " a la referencia "+codArticulo+" en el grupo de balanza "+agruBalanza+". RAZON:"+parsedMsgError+"). ";
								rollback(codCentro, ref.getCodigoReferencia().longValue(),ref.getGrupoBalanza().longValue(), currentMsgError);
							}else{
								Long plu = ref.getPLU()!=null?ref.getPLU().longValue():null;
								if (plu!=null && plu.longValue()!=0){
									referenciasAImprimir.add(codArticulo);	
								}
								final String msg = "OK#"+user;
								commit(codCentro, codArticulo, agruBalanza, msg,plu);
							}
						}
					}	
				}else{
					
					for (AlarmaPLU alarma : alarmasConCambiosTodasAgrupaciones){
						Long codArticulo = alarma.getCodArticulo();
						Long agruBalanza = alarma.getGrupoBalanza();
						String msgError = "No se pudieron guardar los cambios: " +response.getCodigoRespuesta()+ " - " + response.getDescripcionRespuesta();
						rollback(codCentro, codArticulo,agruBalanza, msgError);
					}
				}
			}
			// Se recargan los datos de la base de datos en memoria
			initModifiedAlarmList(codCentro);
		}
		return referenciasAImprimir;
	}

	private String parseMsgError(String mensajeError) {
		//Ejemplo: PLU->002 ASOC.A.00011353
		String output = mensajeError;
		String[] mensajeErrorSplit = mensajeError.split("->");
		if (mensajeErrorSplit!=null && mensajeErrorSplit.length>1){
			String[] mensajeErrorSplit2 = mensajeErrorSplit[1].split(" ASOC.A.");
			if (mensajeErrorSplit2!=null && mensajeErrorSplit2.length>1){
				String plu = mensajeErrorSplit2[0];
				plu = plu.replaceFirst ("^0*", "");
				String ref = mensajeErrorSplit2[1];
				ref = ref.replaceFirst ("^0*", "");
				output+="El plu " + plu+" ya esta asignado a la referencia " + ref;
			}
		}
		return output;
	}

	private void anotarMsgError(Long codCentro, Long codigoReferencia, Long grupoBalanza, String msg) {
		alarmasPLUDao.anotarMsgError(codCentro,codigoReferencia,grupoBalanza, msg);
	}

	private void commitAll(Long codCentro, List<AlarmaPLU> alarmasConCambios, String msg) {
		for(int i=0; i<alarmasConCambios.size(); i++){
			AlarmaPLU alarma = alarmasConCambios.get(i);
			Long ref = alarma.getCodArticulo();
			Long agruBalanza = alarma.getGrupoBalanza();
			Long plu = alarma.getPlu();
			Long pluAnt = alarma.getPluAnt();
			if (plu!=null && !plu.equals(pluAnt)){
				asignarPluAnt(codCentro, plu, alarma.getCodArticulo(), agruBalanza);
				anotarMsgError(codCentro,ref,agruBalanza,msg);
			}
		}

	}

	private void commit(Long codCentro, Long codArticulo, Long grupoBalanza, String msg, Long plu) throws Exception {
		logger.info("AlarmasPLUServiceImpl - commit - codCentro = "+codCentro+" - codArticulo = "+codArticulo+" - agrupacionBalanza = "+grupoBalanza);
			asignarPluAnt(codCentro, plu, codArticulo,  grupoBalanza);
			anotarMsgError(codCentro,codArticulo,grupoBalanza,msg);
	}

	private void asignarPluAnt(Long codCentro, Long plu, Long codArticulo, Long  grupoBalanza) {
		alarmasPLUDao.asignarPluAnt(codCentro, plu, codArticulo, grupoBalanza);
	}

	private void rollback(Long codCentro, Long codigoReferencia, Long grupoBalanza, String msgError) throws Exception {
		logger.info("AlarmasPLUServiceImpl - rollback - codCentro = "+codCentro+" - codigoReferencia = "+codigoReferencia+" - msgError = "+msgError);
		List<AlarmaPLU> alarmas = alarmListOrig.get(codCentro);
		for(int i=0; i<alarmas.size(); i++){
			AlarmaPLU alarma = alarmas.get(i);
			if (alarma.getCodArticulo().equals(codigoReferencia) && alarma.getGrupoBalanza().equals(grupoBalanza)){
				Long plu = alarma.getPlu();
				asignar(codCentro, plu, codigoReferencia, grupoBalanza);
				anotarMsgError(codCentro,codigoReferencia,grupoBalanza,msgError);
				break;
			}
		}
	}

	private ReferenciaModType[] fromAlarmasToDatosGISAE(List<AlarmaPLU> alarmas) {
		ReferenciaModType[] output = new ReferenciaModType[alarmas.size()];
		for (int i=0; i<alarmas.size(); i++){
			final AlarmaPLU alarma = alarmas.get(i);
			final Long codReferencia = alarma.getCodArticulo();
			final Long grupoBalanza = alarma.getGrupoBalanza();
			final Long plu = alarma.getPlu();
			final Long pluAnt = alarma.getPluAnt();
			ReferenciaModType ref = new ReferenciaModType();
			ref.setCodigoReferencia(BigInteger.valueOf(codReferencia));
			ref.setGrupoBalanza(grupoBalanza!=null ? BigInteger.valueOf(grupoBalanza) : null);
			ref.setPLU(plu!=null ? BigInteger.valueOf(plu) : null);
			ref.setPLU_ANTIGUO(pluAnt!=null?BigInteger.valueOf(pluAnt) : null);
			output[i] = ref;
		}
		return output;
	}

	@Override
	public AlarmaPLU findOneInMemory(Long codCentro, Long referencia, Long grupoBalanza) {
		final List<AlarmaPLU> currentList = alarmListOrig.get(codCentro);
		for (AlarmaPLU alarma: currentList){
			final Long codArt = alarma.getCodArticulo();
			final Long gb = alarma.getGrupoBalanza();
			if (referencia.equals(codArt) && grupoBalanza.equals(gb)){
				return alarma;
			}
		}
		return null;
	}
	
	@Override
	public AlarmaPLU findOneInModify(Long codCentro, Long referencia, Long grupoBalanza) {
		final List<AlarmaPLU> currentList = alarmListModified.get(codCentro);
		for (AlarmaPLU alarma: currentList){
			final Long codArt = alarma.getCodArticulo();
			final Long gb = alarma.getGrupoBalanza();
			if (referencia.equals(codArt) && grupoBalanza.equals(gb)){
				return alarma;
			}
		}
		return null;
	}

	public void updateMaxPLUS(PLU plu) throws Exception{
		alarmasPLUDao.updateMaxPLUS(plu);
	}

	@Override
	public void insertarDatosEnTablaIntermedia(List<AlarmaPLU> lista){
		try{
			alarmasPLUDao.insertarDatosEnTablaIntermedia(lista);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Boolean existeAlarma(Long codCentro, Long referencia, Long grupoBalanza) throws Exception {
		return alarmasPLUDao.existeAlarma(codCentro, referencia,grupoBalanza);
	}
	
	@Override
	public void updatePLU(List<AlarmaPLU> lista) throws Exception {
		try{
			alarmasPLUDao.updatePLU(lista);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int obtenerTipoPopupStock(Long codCentro, Long codArt, HttpSession session) {
		
		ConsultarStockRequestType requestType = new ConsultarStockRequestType();
		requestType.setCodigoCentro(BigInteger.valueOf(codCentro));
		requestType.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_CORRECION);
		
		BigInteger[] listaRef = new BigInteger[1];
		listaRef[0] = BigInteger.valueOf(codArt); 
		requestType.setListaCodigosReferencia(listaRef);
		
		ConsultarStockResponseType responseType = null;
		try {
			responseType = this.stockTiendaService.consultaStock(requestType,session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (responseType!=null){
			logger.info("setStock - codigoRespuesta = "+responseType.getCodigoRespuesta()+" - tipoMensaje = "+responseType.getTipoMensaje());
			if (responseType.getCodigoRespuesta().equals("OK")){
				return 0;
//				return getTipoPopupStock(responseType.getTipoMensaje());

			} else {
				logger.error("Error al consultar los STOCKs del centro "+codCentro+" ("+responseType.getCodigoRespuesta()+") : "+responseType.getDescripcionRespuesta());
				return 0;
			}
		}else{
			logger.error("Error al consultar los STOCKs del centro "+codCentro+" : ERROR AL INICIALIZAR LA PETICION DEL WS");
			return 0;
		}
	}

//	private int getTipoPopupStock(String tipoMensaje) {
//		// PORCION CONSUMIDOR
//		if (tipoMensaje.equals(Constantes.STOCK_TIENDA_PORCION_CONSUMIDOR)){
//			return 1;
//		}else if (tipoMensaje.equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE)){
//			return 3;
//		}else if (tipoMensaje.equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE)){
//			
//		}else return 0;
//	}
}
