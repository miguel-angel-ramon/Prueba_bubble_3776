package es.eroski.misumi.service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.AgrupacionDao;
import es.eroski.misumi.dao.iface.AlarmasPLUDao;
import es.eroski.misumi.dao.iface.DetalladoSIADao;
import es.eroski.misumi.dao.iface.DetallePedidoDao;
import es.eroski.misumi.dao.iface.VBloqueoEncargosPiladasDao;
import es.eroski.misumi.dao.iface.VFestivoCentroDao;
import es.eroski.misumi.dao.iface.VReferenciaActiva2Dao;
import es.eroski.misumi.model.Agrupacion;
import es.eroski.misumi.model.AlarmaPLU;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.Cronometro;
import es.eroski.misumi.model.DetalladoContadores;
import es.eroski.misumi.model.DetalladoRedondeo;
import es.eroski.misumi.model.DetalladoSIA;
import es.eroski.misumi.model.DetallePedido;
import es.eroski.misumi.model.DetallePedidoLista;
import es.eroski.misumi.model.DetallePedidoModificados;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.GestionEuros;
import es.eroski.misumi.model.GestionEurosRefs;
import es.eroski.misumi.model.GestionEurosSIA;
import es.eroski.misumi.model.OptionSelectBean;
import es.eroski.misumi.model.PropuestaDetalladoVegalsa;
import es.eroski.misumi.model.ReferenciasSinPLU;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.VBloqueoEncargosPiladas;
import es.eroski.misumi.model.VFestivoCentro;
import es.eroski.misumi.model.VReferenciaActiva2;
import es.eroski.misumi.model.alarmasPLUSWS.ModificarPLUSRequestType;
import es.eroski.misumi.model.alarmasPLUSWS.ModificarPLUSResponseType;
import es.eroski.misumi.model.alarmasPLUSWS.ReferenciaModType;
import es.eroski.misumi.model.alarmasPLUSWS.ReferenciaResModType;
import es.eroski.misumi.model.calendariovegalsa.MapaVegalsa;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.AlarmasPLUService;
import es.eroski.misumi.service.iface.DetalladoPedidoService;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;
import es.eroski.misumi.service.iface.VAgruComerRefService;
import es.eroski.misumi.service.iface.VReferenciaActiva2Service;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;


@Service(value = "DetalladoPedidoService")
public class DetalladoPedidoServiceImpl implements DetalladoPedidoService {

	@Autowired
	private VAgruComerRefService vAgruComerRefService;

	@Autowired
	private VReferenciaActiva2Service vReferenciaActiva2Service;
	
	@Autowired
	private AlarmasPLUService alarmasPLUService;

	@Autowired
	private AgrupacionDao agrupacionDao;

	@Autowired
	private DetallePedidoDao detallePedidoDao;

	@Autowired
	private DetalladoSIADao detalladoSIADao;

	@Autowired
	private VFestivoCentroDao vFestivoCentroDao;;

	@Autowired
	private VReferenciaActiva2Dao vReferenciaActiva2Dao;

	@Autowired
	private VBloqueoEncargosPiladasDao vBloqueoEncargosPiladasDao;
	
	@Autowired
	private AlarmasPLUDao alarmasPLUDao;

	@Autowired
	private UtilidadesCapraboService utilidadesCapraboService;
	
	@Resource 
	private MessageSource messageSource;

	private static Logger logger = Logger.getLogger(DetalladoPedidoServiceImpl.class);
	
	@Override
	public List<VAgruComerRef> findFilter(VAgruComerRef vAgruCommerRef,String sessionId) throws Exception  {

		List<VAgruComerRef> listaAuxiliar= this.vAgruComerRefService.findAll(vAgruCommerRef, null);
		if (vAgruCommerRef.getNivel().equals("I2")){
			//			//Seccion
			List<VAgruComerRef> listaTabla= detallePedidoDao.findFilterMatchesSection(vAgruCommerRef, sessionId);
			Map<Long,VAgruComerRef> detalle= transformListSeccion(listaTabla);
			return juntarListaSeccion(listaAuxiliar,detalle);
		}else if (vAgruCommerRef.getNivel().equals("I3")){
			List<VAgruComerRef> listaTabla= detallePedidoDao.findFilterMatchesCategoria(vAgruCommerRef, sessionId);
			Map<Long,VAgruComerRef> detalle= transformListCategoria(listaTabla);
			return juntarListaCategoria(listaAuxiliar,detalle);
		}
		return null;

	}
	
	private Map<Long,VAgruComerRef> transformListSeccion(List<VAgruComerRef>  listAgruTabla){
		Map<Long,VAgruComerRef> map = new HashMap<Long,VAgruComerRef>();
		for (VAgruComerRef i : listAgruTabla) map.put(i.getGrupo2(),i);
		return map;
	}
	
	private Map<Long,VAgruComerRef> transformListCategoria(List<VAgruComerRef> listDetallePedido){
		Map<Long,VAgruComerRef> map = new HashMap<Long,VAgruComerRef>();
		for (VAgruComerRef i : listDetallePedido) map.put(i.getGrupo3(),i);
		return map;
	}

	private List<VAgruComerRef> juntarListaCategoria(List<VAgruComerRef> listaAuxiliar,Map<Long,VAgruComerRef> detalle){
		List<VAgruComerRef> listaFinal= new ArrayList<VAgruComerRef>();
		for (VAgruComerRef i:listaAuxiliar){
			Long categoria=  i.getGrupo3();
			VAgruComerRef det =detalle.get(categoria);
			if (det!=null){
				listaFinal.add(i);
			}

		}
		return listaFinal;

	}
	
	private List<VAgruComerRef> juntarListaSeccion(List<VAgruComerRef> listaAuxiliar,Map<Long,VAgruComerRef> detalle){
		List<VAgruComerRef> listaFinal= new ArrayList<VAgruComerRef>();
		for (VAgruComerRef i:listaAuxiliar){
			Long seccion=  i.getGrupo2();
			VAgruComerRef det =detalle.get(seccion);
			if (det!=null){
				listaFinal.add(i);
			}

		}
		return listaFinal;

	}
	
	@Override
	public Agrupacion findAll(Agrupacion agrupacion) throws Exception {
		// TODO Auto-generated method stub
		List<Agrupacion> lstAgrup= agrupacionDao.findAll(agrupacion);
		if (lstAgrup!=null && lstAgrup.size()>0)
			return lstAgrup.get(0);
		else
			return null;
	}

	@Override
	public DetalladoContadores loadContadoresDetallado(DetallePedido detallePedido,  HttpSession session, HttpServletResponse response) throws Exception{

		DetalladoContadores resultado = new DetalladoContadores();
		Long contadorSinOferta = new Long(0);
		Long contadorOferta = new Long(0);

		//CALCULAR LOS CONTADORES

		//Contamos los pedidos SIN OFERTA
		detallePedido.setFlgOferta("N");
		contadorSinOferta = detallePedidoDao.countSessionInfo(detallePedido, session.getId(), null);

		//Contamos los pedidos CON OFERTA
		detallePedido.setFlgOferta("S");
		contadorOferta = detallePedidoDao.countSessionInfo(detallePedido, session.getId(), null);


		resultado.setContadorSinOferta(contadorSinOferta);
		resultado.setContadorOferta(contadorOferta);

		return resultado;

	}

	@Override
	public List<DetallePedido> findDetallePedido(Centro centro,  String sessionId, HttpSession session) throws Exception{

		List<DetallePedido> auxLista = detallePedidoDao.findDetallePedido(centro, session);
	
		if (auxLista!=null && auxLista.size()>0){
			
			cargaTablaTemp(auxLista,sessionId);
			
			User user = (User) session.getAttribute("user");
			if (utilidadesCapraboService.esCentroCaprabo(centro.getCodCentro(), user.getCode())){
				//Actualizamos los datos de la tabla temporal
				//con los códigos de caprabo
				detallePedidoDao.updateAllCodigoCaprabo(centro.getCodCentro(), sessionId);
				//Actualizamos los datos de la tabla temporal
				//con las descripciones de caprabo
				detallePedidoDao.updateAllDescripcionCaprabo(centro.getCodCentro(), sessionId);
				//Borramos de la tabla temporal los que no son de caprabo
				detallePedidoDao.deleteAllNoCaprabo(centro.getCodCentro(), sessionId);
				//Volvemos a recuperar los datos de la tabla temporal
				//con el código y la descripción de caprabo
				auxLista = detallePedidoDao.findAllCaprabo(centro.getCodCentro(), sessionId);
			}
		}

		return auxLista;

	}

	private Long cargaTablaTemp(List<DetallePedido> listaDetalle, String sessionId) throws Exception{
		detallePedidoDao.DeleteTemp(sessionId);
		Long resultado = detallePedidoDao.insertListIntoTemp(listaDetalle,sessionId);
		return resultado;
	}

	@Override
	public List<DetallePedido> findSessionInfo(DetallePedido detallePedido,String sesionID, Pagination pagination, String filtrosTabla) throws Exception{
		List<DetallePedido> listaSession = detallePedidoDao.findSessionInfo(detallePedido,sesionID,pagination, filtrosTabla);
		return listaSession;
	}	
	
	@Override
	public Double sumarEurosIniciales(DetallePedido detallePedido,String sesionID) throws Exception{
		Double suma = detallePedidoDao.sumarEurosIniciales(detallePedido,sesionID);
		return suma;
	}
	
	@Override
	public Double sumarEurosFinales(DetallePedido detallePedido,String sesionID) throws Exception{
		Double suma = detallePedidoDao.sumarEurosFinales(detallePedido,sesionID);
		return suma;
	}
	
	@Override
	public Double sumarCajasIniciales(DetallePedido detallePedido,String sesionID) throws Exception{
		Double suma = detallePedidoDao.sumarCajasIniciales(detallePedido,sesionID);
		return suma;
	}
	
	@Override
	public Double sumarCajasFinales(DetallePedido detallePedido,String sesionID) throws Exception{
		Double suma = detallePedidoDao.sumarCajasFinales(detallePedido,sesionID);
		return suma;
	}

	@Override
	public List<GenericExcelVO> findSessionInfoExcel(DetallePedido detallePedido,String[] columnModel, String sesionID, Pagination pagination) throws Exception{
		List<GenericExcelVO> listaSession = detallePedidoDao.findSessionInfoExcel(detallePedido,columnModel,sesionID, pagination);
		return listaSession;
	}
	@Override
	public Long countSessionInfo(DetallePedido detallePedido,String sesionID, String filtrosTabla) throws Exception{
		return  detallePedidoDao.countSessionInfo(detallePedido, sesionID, filtrosTabla);
	}

	@Override
    public DetallePedido add(DetallePedido detalladoPedido, String sessionId) throws Exception {
          List<DetallePedido> aux= detallePedidoDao.findSessionInfo(detalladoPedido,sessionId,null,null);
          
          //GESTION DE EUROS
          Double precioCostoArticulo = detalladoPedido.getPrecioCostoArticulo();

          String flgSIA = detalladoPedido.getFlgSIA();
          
          if ((aux!=null && aux.size()>0) && (aux.get(0).getEstadoPedido().equals("I"))){ //Si la referencia ya existe y esta en estado Integrado. No hacemos nada. Mostramos mensaje
                 return null;
          } else {

                 if ((aux!=null && aux.size()>0)  && !(aux.get(0).getEstadoPedido().equals("E"))){ //la referencia ya esta insertada en T_DETALLADO_PEDIDO Y ademas el estado es distinto de E (EMPUJE)
    
                        DetallePedido detallePedidoAux = aux.get(0);
                        
                        //si es SIA llamamos la redondeo
                        if (flgSIA != null && flgSIA.equals("S")){
                             //Redondeo de la cantidad
                             DetalladoSIA detalladoSIA = new DetalladoSIA();
                             detalladoSIA.setCodLoc(detalladoPedido.getCodCentro());
                             detalladoSIA.setCodArticulo(detalladoPedido.getCodArticuloEroski()); //Calculamos con el código de referencia de eroski
                             detalladoSIA.setUnidPropuestasFlModif(new Double(detalladoPedido.getCantidad() * detalladoPedido.getUnidadesCaja()));
         
                             DetalladoRedondeo detalladoRedondeo = (DetalladoRedondeo) detalladoSIADao.redondeoDetallado(detalladoSIA);
  
                             
                             if (detalladoRedondeo.getCodError() == 1) {
                                    //Existe redondeo en la cantidad
                                    detallePedidoAux.setCantidad(detalladoRedondeo.getUnidPropuestasFlModif().longValue()); //Actualizamos la cantidad con el redondeo   
                                    detallePedidoAux.setUnidadesCaja(new Double(detalladoRedondeo.getUnidCaja())); //Actualizamos las unidades caja con el redondeo    
                                    
                                    if ((detallePedidoAux.getEstadoGrid() != null) && (detallePedidoAux.getEstadoGrid().equals("2") || detallePedidoAux.getEstadoGrid().equals("5"))) {//Si la referencia ya esta en estado modificado, es decir ha sido previamente modificada. En la recarga del grid 
                                           //no nos interesa modificar el T_DETALLADO_PEDIDO con la cantidad de la lista de modificados, ya que ya lo estamos 
                                           //modificando aqui con el valor indicado en el POP UP de Nuevo
                                           detallePedidoAux.setEstadoGrid("8"); //A la hora de modificar miraremos si el estadoGrid es 8, si es asi no lo volveremos a modificar y volveremos a poner es estdo a "5" para que en el grid aparezca como modificado y con redondeo.
                                    } else {
                                           detallePedidoAux.setEstadoGrid("5");
                                    }
                                    
                                    detallePedidoAux.setCodErrorRedondeo(detalladoRedondeo.getCodError());
                                    detallePedidoAux.setDescErrorRedondeo(detalladoRedondeo.getDescError());
         
                             } else {
                                    detallePedidoAux.setCantidad(detalladoPedido.getCantidad()); //Actualizamos la cantidad con lo de pantalla     
                                    detallePedidoAux.setUnidadesCaja(detalladoPedido.getUnidadesCaja()); //Actualizamos la cantidad con lo de pantalla             
                                    if ((detallePedidoAux.getEstadoGrid() != null) && (detallePedidoAux.getEstadoGrid().equals("2") || detallePedidoAux.getEstadoGrid().equals("5"))) {//Si la referencia ya esta en estado modificado, es decir ha sido previamente modificada. En la recarga del grid 
                                           //no nos interesa modificar el T_DETALLADO_PEDIDO con la cantidad de la lista de modificados, ya que ya lo estamos 
                                           //modificando aqui con el valor indicado en el POP UP de Nuevo
                                           detallePedidoAux.setEstadoGrid("9"); //A la hora de modificar miraremos si el estadoGrid es 9, si es asi no lo volveremos a modificar y volveremos a poner es estdo a "2" para que en el grid aparezca como modificado.
                                    } else {
                                           detallePedidoAux.setEstadoGrid("2");
                                    }
                                    detallePedidoAux.setCodErrorRedondeo(detalladoRedondeo.getCodError());
                                    detallePedidoAux.setDescErrorRedondeo(detalladoRedondeo.getDescError());
                             }
                
                                     
						}  else {
							
							detallePedidoAux.setCantidad(detalladoPedido.getCantidad()); //Actualizamos la cantidad con lo de pantalla	
							detallePedidoAux.setUnidadesCaja(detalladoPedido.getUnidadesCaja()); //Actualizamos la cantidad con lo de pantalla	
							detallePedidoAux.setEstadoGrid("2");
						}
                        
                        
                        //GESTION DE EUROS
             			Double unidadesCaja = detallePedidoAux.getUnidadesCaja();
             			Long cantidad = detallePedidoAux.getCantidad().longValue();

             			Double precioCostoFinal = cantidad * (precioCostoArticulo!=null?precioCostoArticulo:new Double("0")) * unidadesCaja;
             			detallePedidoAux.setPrecioCostoFinal((double)Math.round(precioCostoFinal * 10d) / 10d); //Limitamos los decimales a 1
             			detallePedidoAux.setPrecioCostoArticulo(precioCostoArticulo);
             			detallePedidoAux.setPrecioCostoInicial(detalladoPedido.getPrecioCostoInicial());
             			detallePedidoAux.setRotacion(detalladoPedido.getRotacion());
             			detallePedidoAux.setDenomArea(detalladoPedido.getDenomArea());
             			detallePedidoAux.setDenomSeccion(detalladoPedido.getDenomSeccion());
             			detallePedidoAux.setDenomCategoria(detalladoPedido.getDenomCategoria());
             			detallePedidoAux.setDenomSegmento(detalladoPedido.getDenomSegmento());
             			detallePedidoAux.setDenomSubcategoria(detalladoPedido.getDenomSubcategoria());
                        
                        return detallePedidoDao.updateDetalladoNuevo(detallePedidoAux, sessionId);
                        
                        
                 }else{

                        if ((aux!=null && aux.size()>0) && (aux.get(0).getEstadoPedido().equals("E"))){ 
                               //La referencia ya esta insertada pero esta en estado E. Permitimos insertar, realimente sera un uppdate.
                               
                               DetallePedido detallePedidoAux = aux.get(0);
    
                               //si es SIA llamamos la redondeo
                               if (flgSIA != null && flgSIA.equals("S")){
                                     //Redondeo de la cantidad
                                     DetalladoSIA detalladoSIA = new DetalladoSIA();
                                     detalladoSIA.setCodLoc(detalladoPedido.getCodCentro());
                                      detalladoSIA.setCodArticulo(detalladoPedido.getCodArticuloEroski()); //Calculamos con el código de referencia de eroski
                                     detalladoSIA.setUnidPropuestasFlModif(new Double(detalladoPedido.getCantidad() * detalladoPedido.getUnidadesCaja()));
                 
                                     DetalladoRedondeo detalladoRedondeo = (DetalladoRedondeo) detalladoSIADao.redondeoDetallado(detalladoSIA);
                                     
                                     if (detalladoRedondeo.getCodError() == 0  || detalladoRedondeo.getCodError() == 1) {
    
                                            detallePedidoAux.setEstadoPedido("P");
                                            detallePedidoAux.setTipoDetallado("A");
                                            detallePedidoAux.setPropuesta(new Long(0));
                                            if ( detalladoRedondeo.getCodError() == 1) {
                                                   detallePedidoAux.setEstadoGrid("5");
                                            } else {
                                                   detallePedidoAux.setEstadoGrid("2");
                                            }
                                            detallePedidoAux.setCantidad(detalladoRedondeo.getUnidPropuestasFlModif().longValue()); //Actualizamos la cantidad con el redondeo   
                                            detallePedidoAux.setUnidadesCaja(new Double(detalladoRedondeo.getUnidCaja())); //Actualizamos las unidades caja con el redondeo
                                            detallePedidoAux.setCodErrorRedondeo(detalladoRedondeo.getCodError());
                                            detallePedidoAux.setDescErrorRedondeo(detalladoRedondeo.getDescError());

                                      } else {
          
                                            detallePedidoAux.setCodErrorRedondeo(detalladoRedondeo.getCodError());
                                            detallePedidoAux.setDescErrorRedondeo(detalladoRedondeo.getDescError()); //Lo vamos a mostrar en pantalla
          
                                            return detallePedidoAux;
                                     }
                                     
                               } else {
						
									detallePedidoAux.setCantidad(detalladoPedido.getCantidad()); //Actualizamos la cantidad con lo de pantalla	
									detallePedidoAux.setUnidadesCaja(detalladoPedido.getUnidadesCaja()); //Actualizamos la cantidad con lo de pantalla	
									detallePedidoAux.setEstadoGrid("2");
                               }
                               
                               
                               //GESTION DE EUROS
	                			Double unidadesCaja = detallePedidoAux.getUnidadesCaja();
	                			Long cantidad = detallePedidoAux.getCantidad().longValue();
	
	                			Double precioCostoFinal = cantidad * precioCostoArticulo * unidadesCaja;
	                			detallePedidoAux.setPrecioCostoFinal((double)Math.round(precioCostoFinal * 10d) / 10d); //Limitamos los decimales a tres
	                			detallePedidoAux.setPrecioCostoArticulo(precioCostoArticulo);
	                			detallePedidoAux.setPrecioCostoInicial(detalladoPedido.getPrecioCostoInicial());
	                			detallePedidoAux.setRotacion(detalladoPedido.getRotacion());
	                 			detallePedidoAux.setDenomArea(detalladoPedido.getDenomArea());
	                 			detallePedidoAux.setDenomSeccion(detalladoPedido.getDenomSeccion());
	                 			detallePedidoAux.setDenomCategoria(detalladoPedido.getDenomCategoria());
	                 			detallePedidoAux.setDenomSegmento(detalladoPedido.getDenomSegmento());
	                 			detallePedidoAux.setDenomSubcategoria(detalladoPedido.getDenomSubcategoria());
    
	                			return detallePedidoDao.updateEmpuje(detallePedidoAux, sessionId);
    
                        } else {
                               
                               //La referencia no esta insertada anteriormente
                 
                               if (detalladoPedido.getGrupo1() == 3){ //Es una referencia de textil, es necesario completar los datos complementarios de textil
    
                                     List<DetallePedido> datosTextil = detallePedidoDao.findDatosEspecificosTextil(detalladoPedido.getCodCentro(), detalladoPedido.getCodArticuloEroski()+"");  //Calculamos con el código de referencia de eroski
                                     //Completamos el objeto con los datos de textil
                                     if(datosTextil != null && datosTextil.size()>0) {
                                            detalladoPedido.setTemporada(datosTextil.get(0).getTemporada());
                                            detalladoPedido.setNumOrden(datosTextil.get(0).getNumOrden());
                                            detalladoPedido.setModeloProveedor(datosTextil.get(0).getModeloProveedor());
                                            detalladoPedido.setDescrTalla(datosTextil.get(0).getDescrTalla());
                                            detalladoPedido.setDescrColor(datosTextil.get(0).getDescrColor());
                                            detalladoPedido.setFacing(datosTextil.get(0).getFacing());
                                     }
                               }
    
                               //si es SIA llamamos la redondeo
                               if (flgSIA != null && flgSIA.equals("S")){
                                     
                                     //Redondeo de la cantidad
                                     DetalladoSIA detalladoSIA = new DetalladoSIA();
                                     detalladoSIA.setCodLoc(detalladoPedido.getCodCentro());
                                     detalladoSIA.setCodArticulo(detalladoPedido.getCodArticuloEroski()); //Calculamos con el código de referencia de eroski
                                     detalladoSIA.setUnidPropuestasFlModif(new Double(detalladoPedido.getCantidad() * detalladoPedido.getUnidadesCaja()));
                 
                                     DetalladoRedondeo detalladoRedondeo = (DetalladoRedondeo) detalladoSIADao.redondeoDetallado(detalladoSIA);

                                     if (detalladoRedondeo.getCodError() == 0  || detalladoRedondeo.getCodError() == 1) {
                                            //Existe redondeo en la cantidad
          
                                            detalladoPedido.setCantidad(detalladoRedondeo.getUnidPropuestasFlModif().longValue()); //Actualizamos la cantidad con el redondeo   
                                            detalladoPedido.setUnidadesCaja(new Double(detalladoRedondeo.getUnidCaja())); //Actualizamos las unidades caja con el redondeo    
                                            
                                            if ( detalladoRedondeo.getCodError() == 1) {
                                                   detalladoPedido.setEstadoGrid("6");
                                            } else {
                                                   detalladoPedido.setEstadoGrid("1");
                                            }
                                            
                                            detalladoPedido.setCodErrorRedondeo(detalladoRedondeo.getCodError());
                                            detalladoPedido.setDescErrorRedondeo(detalladoRedondeo.getDescError());

                                     } else {

                                            detalladoPedido.setCodErrorRedondeo(detalladoRedondeo.getCodError());
                                            detalladoPedido.setDescErrorRedondeo(detalladoRedondeo.getDescError()); //Lo vamos a mostrar en pantalla
          
                                            return detalladoPedido;
                                     }
                               } 
                               
                               //GESTION DE EUROS
                                Double unidadesCaja = detalladoPedido.getUnidadesCaja();
		                		Long cantidad = detalladoPedido.getCantidad().longValue();
		
		                		Double precioCostoFinal = cantidad * (precioCostoArticulo!=null?precioCostoArticulo:new Double("0")) * unidadesCaja;
		                		detalladoPedido.setPrecioCostoFinal((double)Math.round(precioCostoFinal * 10d) / 10d); //Limitamos los decimales a tres
		                		detalladoPedido.setPrecioCostoArticulo(precioCostoArticulo);
		                		detalladoPedido.setPrecioCostoInicial(detalladoPedido.getPrecioCostoInicial());
		                		detalladoPedido.setRotacion(detalladoPedido.getRotacion());
		                		detalladoPedido.setDenomArea(detalladoPedido.getDenomArea());
		                		detalladoPedido.setDenomSeccion(detalladoPedido.getDenomSeccion());
		                		detalladoPedido.setDenomCategoria(detalladoPedido.getDenomCategoria());
		                		detalladoPedido.setDenomSegmento(detalladoPedido.getDenomSegmento());
		                		detalladoPedido.setDenomSubcategoria(detalladoPedido.getDenomSubcategoria());
		                			
	                            return detallePedidoDao.add(detalladoPedido, sessionId);

                        }
                 }
          }

    }




	@Override
	public void resetSessionData(String sessionId) throws Exception {
		detallePedidoDao.deleteNewRecords(sessionId) ;
		detallePedidoDao.resetSessionData(sessionId);


	}

	@Override
	public void updateGridState(DetallePedido detalladoPedido, HttpSession session) throws Exception {
		
		
		User userSession = (User)session.getAttribute("user");
		String sessionId = session.getId();
		
		if (detalladoPedido!=null && detalladoPedido.getListaModificados() != null && detalladoPedido.getListaModificados().size()>0){
			List<DetallePedido> aux= new ArrayList<DetallePedido>();
			
			for (DetallePedidoModificados i : detalladoPedido.getListaModificados()){
				DetallePedido detalle = new DetallePedido();

				detalle.setCodArticulo(i.getCodArticulo());
				detalle.setEstadoGrid(i.getEstadoGrid());
				detalle.setCantidad(i.getCantidad());
				detalle.setCodCentro(userSession.getCentro().getCodCentro());
				aux.add(detalle);
			}
			
			detallePedidoDao.updateGridState(aux,sessionId);
		}
	}	
	
	
public void updateOnlyGridState(DetallePedido detalladoPedido, HttpSession session, Long estadoGrid, Long nuevoEstadoGrid) throws Exception {
		
		
		User userSession = (User)session.getAttribute("user");
		String sessionId = session.getId();
		
		if (detalladoPedido!=null && detalladoPedido.getListaModificados() != null && detalladoPedido.getListaModificados().size()>0){
			List<DetallePedido> aux= new ArrayList<DetallePedido>();
			
			for (DetallePedidoModificados i : detalladoPedido.getListaModificados()){
				DetallePedido detalle = new DetallePedido();

				detalle.setCodArticulo(i.getCodArticulo());
				detalle.setCodCentro(userSession.getCentro().getCodCentro());
				aux.add(detalle);
				
			}
			
			detallePedidoDao.updateOnlyGridState(aux, sessionId, estadoGrid, nuevoEstadoGrid); //Actualiza el solo el estadoGrid
		}
	}	


	@Override
	public List<DetallePedidoModificados> saveData(DetallePedido detalladoPedido, String sessionId, Centro centro, HttpSession session) throws Exception {
		List<DetallePedidoModificados> resultado = null;
		resultado = this.saveDataSIA(detalladoPedido,sessionId,centro);

		return resultado;
	}	

	private List<DetallePedidoModificados> saveDataSIA(DetallePedido detalladoPedido, String sessionId, Centro centro) throws Exception {

		List<DetallePedidoModificados>  respuesta = new ArrayList<DetallePedidoModificados>();
		//Al obtener la lista de los Pedidos a modificar de T_DETALLADO_PEDIDO, en este caso solo obtengo los de SIA (FLG_SIA = 'S')
		
		
		detalladoPedido.setFlgSIA(Constantes.FLG_SIA_SI);
		List<DetallePedido> aModificar = this.detallePedidoDao.findSessionInfoUpdateable(detalladoPedido, sessionId, null);
		detalladoPedido.setFlgSIA(null); //Lo volvemos a poner a null para que no interfiera mas tarde en el contar total (PBL + SIA)

		Integer total = 0;
		Integer error = 0;

		// Si FLG_SIA = 'S'. Centro EROSKI.
		if (aModificar!=null && aModificar.size()>0){
			respuesta = modifyProcedureSIAData(aModificar,centro);

			total = respuesta.size();

			//Si no se ha modificado nada, indicamos que hay error en el procedimiento.
			if(new Integer(0).equals(total)){
				//Si el total no es mayor que 0 ha habido un error en el procedimiento de modificación.
				detalladoPedido.setErrorModificarProc(-1);
			}

			for (DetallePedidoModificados dpm : respuesta){
				if (dpm.getEstadoGrid().equals("-1")){
					error++;

					int indice = -1;
					for(int i = 0; i < aModificar.size(); i++){
						DetallePedido detPedido = aModificar.get(i);
						if (detPedido.getCodArticuloEroski().equals(dpm.getCodArticulo())){
							indice = i;
							break;
						}
					}
					DetallePedido aux = aModificar.get(indice);
					//DetallePedido aux = aModificar.get(aModificar.indexOf(dp));
					if (null != aux.getEstadoGrid() && (aux.getEstadoGrid().equals("1") || aux.getEstadoGrid().equals("-3"))){
						dpm.setEstadoGrid("-3");
					}
				}
				
			}

		// Si FLG_SIA = 'N'. Centro VEGALSA.
		}else{
			respuesta = detalladoPedido.getListaModificados();
			// Comprobar si existe algún registro con error (triángulo)
			for (DetallePedidoModificados dpm : respuesta){
				if (dpm.getEstadoGrid().equals("-1")){
					error++;
				}
			}
			total = respuesta.size();
		}

		detallePedidoDao.updateData(respuesta, sessionId);
		
		if(detalladoPedido.getTotalGuardar() != null){
			int guardadoAcumulado = detalladoPedido.getTotalGuardar();
			detalladoPedido.setTotalGuardar(guardadoAcumulado + total);
		}else{
			detalladoPedido.setTotalGuardar(total);
		}

		if(detalladoPedido.getTotalError() != null){
			int errorAcumulado = detalladoPedido.getTotalError();
			detalladoPedido.setTotalError(errorAcumulado + error);
		}else{
			detalladoPedido.setTotalError(error);
		}

		return respuesta;

	}

	private	List<DetallePedidoModificados> modifyProcedureSIAData(List<DetallePedido> lista,
			Centro centro) throws Exception{

		return detalladoSIADao.modifyProcedureSIAData(lista, centro.getCodCentro());
	}

//	private List<DetallePedido> mergeLists(List<DetallePedido> aModificar, List<DetallePedidoModificados> modificaodsEnMem){
//		List<DetallePedido> nuevaLista = new ArrayList<DetallePedido>()  ;
//		Map<Long,DetallePedidoModificados> newData = transformListSeleccionados(modificaodsEnMem);
//		for (DetallePedido i : aModificar) {
//			DetallePedidoModificados det =newData.get(i.getCodArticulo()); 
//			if (det!=null){
//				i.setCantidad(det.getCantidad());
//				nuevaLista.add(i);
//			}else{
//				nuevaLista.add(i);
//			}
//		}
//		return nuevaLista;
//	}
	
//	private Map<Long,DetallePedidoModificados> transformListSeleccionados(List<DetallePedidoModificados>  listModif){
//		Map<Long,DetallePedidoModificados> map = new HashMap<Long,DetallePedidoModificados>();
//		for (DetallePedidoModificados i : listModif) map.put(i.getCodArticulo(),i);
//		return map;
//	}
	
	@Override
	public Long sumBoxes(DetallePedido detalladoPedido, String sessionId) throws Exception {
		if (detallePedidoDao.existsSType(detalladoPedido, sessionId)>0){
			return detallePedidoDao.sumBoxes(detalladoPedido, sessionId);
		}else{
			return null;
		}

	}

	@Override
	public String comprobarReferenciaNueva(Long codCentro, Long referencia, Date fechaGen) throws Exception {
		return detallePedidoDao.comprobarReferenciaNueva(codCentro, referencia, fechaGen);
	}


	@Override
	public List<DetallePedido> findAllTextilN2ByLote(DetallePedido detallePedido) throws Exception {
		List<DetallePedido> lista = this.detallePedidoDao.findAllTextilN2ByLote(detallePedido);
		for (DetallePedido detPedido : lista){
			detPedido.setNextDayPedido(this.vReferenciaActiva2Service.getNextFechaPedido(detPedido.getCodCentro(), detPedido.getCodArticulo()));
		}
		return lista;

	}

	@Override
	public VBloqueoEncargosPiladas getNextDayDetalladoPedido(Long codArt, Long centerId) throws Exception{

		VBloqueoEncargosPiladas vBloqueoEncargosPiladas = null;
		List<VReferenciaActiva2> listDate = this.vReferenciaActiva2Dao.getNextDiaPedido(centerId, codArt);
		if (!listDate.isEmpty()){
			VReferenciaActiva2 vRefAct = listDate.get(0);
			if (vRefAct.getActiva().equals("S")){
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat df = new SimpleDateFormat("E",Locale.ENGLISH);
				String dia = df.format(cal.getTime());
				PropertyDescriptor pd = new PropertyDescriptor(dia, VReferenciaActiva2.class);
				Method getter = pd.getReadMethod();
				Integer pedido = (Integer) getter.invoke(vRefAct);
				if (pedido > 0){
					Integer i=0;
					VFestivoCentro vFestivoCentro = new VFestivoCentro();
					vFestivoCentro.setCodCentro(centerId);
					vFestivoCentro.setFechaFestivo(cal.getTime());
					while (i<pedido){
						String fecha = this.vFestivoCentroDao.getNextDay(vFestivoCentro);
						if(fecha!=null) {
							vFestivoCentro.setFechaFestivo(Utilidades.convertirStringAFecha(fecha));
						}
						i++;
					}
					VBloqueoEncargosPiladas vBloqueo = new VBloqueoEncargosPiladas();
					vBloqueo.setCodCentro(centerId);
					vBloqueo.setCodArticulo(codArt);
					vBloqueo.setCodTpBloqueo(Constantes.COD_TP_BLOQUEO_DETALLADO);
					vBloqueo.setModo(Constantes.BLOQUEOS_MODO_FECHA_ENTREGA);
					vBloqueo.setFechaControl(vFestivoCentro.getFechaFestivo());
					vBloqueoEncargosPiladas = this.vBloqueoEncargosPiladasDao.getBloqueoFecha(vBloqueo);

				}
			}

		}
		return vBloqueoEncargosPiladas;
	}


	public DetalladoRedondeo redondeoDetallado(DetalladoSIA detalladoSIA) throws Exception{
		return detalladoSIADao.redondeoDetallado(detalladoSIA);
	}
	
	public DetallePedido actualizarPrecioCostoFinal(DetallePedido detallePedido, String sesionID) throws Exception{
		return detallePedidoDao.actualizarPrecioCostoFinal(detallePedido, sesionID);
	}
	
	@Override
	public DetallePedido findOne(Long referencia, String codCentro, String sesionID) throws Exception {
		return detallePedidoDao.findOne(referencia, codCentro, sesionID);
	}

	@Override
	public DetallePedidoLista referenciaNuevaSIA(Long referencia, Long codCentro) throws Exception {
		return detallePedidoDao.referenciaNuevaSIA(referencia, codCentro);
	}
	
	@Override
	public Cronometro calculoCronometroYNumeroHorasLimite(DetallePedido detallePedido, String sesionID) throws Exception {
		return detallePedidoDao.calculoCronometroYNumeroHorasLimite(detallePedido, sesionID);
	}
	
	@Override
	public Long countFlgPropuesta (String sessionId, Long center, Long seccion, Long categoria) throws Exception{
		return  detallePedidoDao.countFlgPropuesta(sessionId, center, seccion, categoria);
	}

	public GestionEurosSIA gestionEuros(final GestionEuros gestionEuros) throws Exception{
		return detalladoSIADao.gestionEuros(gestionEuros);
	}
	
	@Override
	public List<GestionEurosRefs> findSessionInfoGestionEurosRefs(DetallePedido detallePedido,String sesionID, Pagination pagination) throws Exception{
		List<GestionEurosRefs> listaSession = detallePedidoDao.findSessionInfoGestionEurosRefs(detallePedido,sesionID,pagination);
		return listaSession;
	}
	
	@Override
	public void updatePrevioCalcular(DetallePedido detallePedido,String sesionID) throws Exception{
		detallePedidoDao.updatePrevioCalcular(detallePedido,sesionID);
	}
	
	@Override
	public void updatePropuestaInicial(DetallePedido detallePedido,String sesionID) throws Exception {
		detallePedidoDao.updatePropuestaInicial(detallePedido,sesionID);
	}
	
	@Override
	public void updateGridStateGestionEurosRefs(GestionEuros gestionEuros, String sessionId) throws Exception {
		// TODO Auto-generated method stub
		if (gestionEuros!=null && gestionEuros.getGestionEurosRefsLst().size()>0){
			List<DetallePedido> aux= new ArrayList<DetallePedido>();
			
			for (GestionEurosRefs i : gestionEuros.getGestionEurosRefsLst()){
				DetallePedido detalle = new DetallePedido();

				detalle.setCodArticulo(i.getCodArticulo());
				//if ((i.getRefCumple() != null) && (i.getRefCumple().equals("N")) ) {
				if ((null != i.getRefCumple()) && ("N".equals(i.getRefCumple()))) {
					detalle.setEstadoGrid("4");
				} else {
					detalle.setEstadoGrid("2");
				}
				
				detalle.setCantidad(i.getUnidPropuestasFlModif());
				detalle.setCodCentro(gestionEuros.getCodLoc());
				detalle.setPrecioCostoFinal(i.getPrecioCostoArtLinealFinal());
				detalle.setDiferencia(i.getDiferencia());
				detalle.setRefCumple(i.getRefCumple());
				detalle.setAviso(i.getAvisos());
				
				aux.add(detalle);				
			}
			
			detallePedidoDao.updateGridStateGestionEurosRefs(aux,sessionId);
		}
	}
	
	@Override
	public String mostrarEmpuje(String sessionId, DetallePedido detallePedido) throws Exception{
		 return detallePedidoDao.countEmpuje(sessionId,detallePedido) > 0 ? "S" : "N";
	}
	
	@Override
	public int countModificados(String sesionID) throws Exception {
		// TODO Auto-generated method stub
		return detallePedidoDao.findModifies(sesionID).size();
	}
	
	/*
	 *  MISUMI-300
	 * @author BICUGUAL
	 */
	@Override
	public Boolean isCentroVegalsa(Long codCentro) throws Exception {
		return detallePedidoDao.countCentroVegalsa(codCentro) > 0 ? true : false;
	}
	
	@Override
	public Boolean hasAnyRecordFromSIA(Long codCentro, String sesionID) throws Exception {
		return detallePedidoDao.countDetallePedidoFromSIA(codCentro, sesionID) > 0 ? true : false;
	}
	
	@Override
	public int loadDetalladoPedido(Long idCentro, Boolean isCentroVegalsa, HttpSession session) throws Exception {
	
		Centro centro= new Centro(idCentro, null, null, null, null, null, null, null, null, null,null);
		String sessionId = session.getId();
		
		List<DetallePedido> auxLista = detallePedidoDao.findDetallePedido(centro, session);
		
		//Si es centro Vegalsa obtengo la lista de registros del detallado de Vegalsa 
		if (isCentroVegalsa){
			List<DetallePedido> lstDetallePedidoVegalsa = detallePedidoDao.getDetalladoPedidoVegalsa(centro.getCodCentro());

			if (null!= lstDetallePedidoVegalsa && !lstDetallePedidoVegalsa.isEmpty()){
				auxLista.addAll(lstDetallePedidoVegalsa);
			}
		}
		
		if (auxLista!=null && auxLista.size()>0){
			
			this.cargaTablaTemp(auxLista,sessionId);
			
			User user = (User) session.getAttribute("user");
			
			if (utilidadesCapraboService.esCentroCaprabo(centro.getCodCentro(), user.getCode())){
				//Actualizamos los datos de la tabla temporal
				//con los códigos de caprabo
				detallePedidoDao.updateAllCodigoCaprabo(centro.getCodCentro(), sessionId);
				//Actualizamos los datos de la tabla temporal
				//con las descripciones de caprabo
				detallePedidoDao.updateAllDescripcionCaprabo(centro.getCodCentro(), sessionId);
				//Borramos de la tabla temporal los que no son de caprabo
				detallePedidoDao.deleteAllNoCaprabo(centro.getCodCentro(), sessionId);
				//Volvemos a recuperar los datos de la tabla temporal
				//con el código y la descripción de caprabo
				auxLista = detallePedidoDao.findAllCaprabo(centro.getCodCentro(), sessionId);
			}
		}

		return auxLista.size();
	}

	@Override
	public List<OptionSelectBean> getLstMapasByCenterAndIdSession(Long codCenter, String sessionId) throws Exception{
		List<MapaVegalsa> lstMapas = detallePedidoDao.getMapasVegalsaByCenterAndIdSessionId(codCenter, sessionId);
		
		List<OptionSelectBean> lstOptions = new ArrayList<OptionSelectBean>();
		
		for (MapaVegalsa mapa: lstMapas){
			OptionSelectBean option = new OptionSelectBean(String.valueOf(mapa.getCodMapa()), mapa.getCodMapa() + "-" + mapa.getDescMapa());
			lstOptions.add(option);
		}
		return lstOptions;
	}
	
	@Override
	public List<PropuestaDetalladoVegalsa> resumenPropuestaVegalsa(Long codCentro, Long codMapa) {
		return detallePedidoDao.resumenPropuestaVegalsa(codCentro, codMapa);
	}

	@Override
	public List<DetallePedidoModificados> gestionDatosVegalsaModif(List<DetallePedidoModificados> lstDetallePedidoModif, HttpSession session) throws Exception{

		List<DetallePedidoModificados> lstDetallePedidoModifAux = lstDetallePedidoModif;
		
		if (lstDetallePedidoModif != null && lstDetallePedidoModif.size() > 0){
			
			int indiceModif = 0;
			
			for (DetallePedidoModificados detalle : lstDetallePedidoModif){
				DetallePedido detallePedido = new DetallePedido();
				detallePedido.setCodCentro(detalle.getCodCentro());
				detallePedido.setCodArticulo(detalle.getCodArticulo());
				detallePedido.setCantidad(detalle.getCantidad());
				detallePedido.setPropuesta(detalle.getPropuesta());
				detallePedido.setCantidadOriginal(detalle.getCantidadOriginal());
				detallePedido.setCantidadAnt(detalle.getCantidadOriginal());
				detallePedido.setUnidadesCaja(detalle.getUnidadesCaja());
				
				// Recuperar registros de T_DETALLADO_PEDIDO.
				DetallePedido detallePedidoAux = detallePedidoDao.findDatosVegalsaModif(detalle, session.getId());
				
				// Si la referencia del centro Vegalsa NO es de SIA --> Alta en T_MIS_DETALLA_VEGALSA_MODIF.
				if (detallePedidoAux != null){
					detallePedido.setFechaPedido(detallePedidoAux.getFechaPedido());
					detallePedido.setHoraLimite(detallePedidoAux.getHoraLimite());
					detallePedido.setCodMapa(detallePedidoAux.getCodMapa());
					detallePedido.setUfp(detallePedidoAux.getUfp());
					detallePedido.setFFPP(detallePedidoAux.getFFPP());
					
					// Alta en la tabla T_MIS_DETALLADO_VEGALSA_MODIF.
					try{
						detallePedidoDao.insertVegalsaModif(detallePedido, session);
						
						lstDetallePedidoModifAux.get(indiceModif).setEstadoGrid("0"); // Icono del disquete.
						lstDetallePedidoModifAux.get(indiceModif).setEstadoPedido("R"); // Realizado. Color verde para la cantidad modificada.
						lstDetallePedidoModifAux.get(indiceModif).setCantidadOriginal(lstDetallePedidoModifAux.get(indiceModif).getCantidad());
					}catch(DataIntegrityViolationException din){
						// Error - Ya existe el registro y se tendrán que modificar
						// valores de la tabla T_MIS_DETALLADO_VEGALSA_MODIF. (DataIntegrityViolationException)
						try{
							detallePedidoDao.updateVegalsaModif(detallePedido, session);

							lstDetallePedidoModifAux.get(indiceModif).setEstadoGrid("0"); // Icono del disquete.
							lstDetallePedidoModifAux.get(indiceModif).setEstadoPedido("R"); // Realizado. Color verde para la cantidad modificada.
							lstDetallePedidoModifAux.get(indiceModif).setCantidadOriginal(lstDetallePedidoModifAux.get(indiceModif).getCantidad());
						}catch(DataAccessException dae){
							lstDetallePedidoModifAux.get(indiceModif).setEstadoGrid("-1"); // Icono del aviso de error (Triángulo).
							lstDetallePedidoModifAux.get(indiceModif).setEstadoPedido("I"); // Realizado. Color rojo para la cantidad no modificada.
							lstDetallePedidoModifAux.get(indiceModif).setCantidad(lstDetallePedidoModifAux.get(indiceModif).getCantidadOriginal());
							lstDetallePedidoModifAux.get(indiceModif).setCodigoError("Hora limite ya pasada.");
						}catch(Exception e){
							logger.error("######################## Error SQL ############################");
							logger.error( StackTraceManager.getStackTrace(e));
							logger.error("###############################################################");
						}
					}catch(DataAccessException dae){
						// Error - Se ha superado la hora límite de modificación. (DataAccessException)
						lstDetallePedidoModifAux.get(indiceModif).setEstadoGrid("-1"); // Icono del aviso de error (Triángulo).
						lstDetallePedidoModifAux.get(indiceModif).setEstadoPedido("I"); // Realizado. Color rojo para la cantidad no modificada.
						lstDetallePedidoModifAux.get(indiceModif).setCodigoError("Hora limite ya pasada.");
						lstDetallePedidoModifAux.get(indiceModif).setCantidad(lstDetallePedidoModifAux.get(indiceModif).getCantidadOriginal());
					}catch(Exception e){
						logger.error("######################## Error SQL ############################");
						logger.error( StackTraceManager.getStackTrace(e));
						logger.error("###############################################################");
					}

					lstDetallePedidoModifAux.get(indiceModif).setCantidadAnt(lstDetallePedidoModifAux.get(indiceModif).getCantidadOriginal());

					List<DetallePedidoModificados> lstDetallePedidoOneFile = new ArrayList<DetallePedidoModificados>(); 
					lstDetallePedidoOneFile.add(lstDetallePedidoModif.get(indiceModif)); 
					
					// Actualizo los estados de los registros para visualizar los iconos en el GRID.
					this.updateDatosVegalsa(lstDetallePedidoModif, session.getId());
					
					indiceModif++;
				}else{
					lstDetallePedidoModifAux.get(indiceModif).setCantidadAnt(detalle.getCantidadOriginal());
				}
			}
			
		}
		
		return lstDetallePedidoModifAux;
		
	}

	@Override
	public void updateDatosVegalsa(List<DetallePedidoModificados> listaDetalladoModif, String sessionId) throws Exception {
		detallePedidoDao.updateData(listaDetalladoModif, sessionId);
	}
	
	@Override
	public List<ReferenciasSinPLU> loadReferenciasSinPLU(Long codCentro,  String idSesion, Long seccionId) throws Exception{
		ReferenciasSinPLU aux = new ReferenciasSinPLU();
		List<ReferenciasSinPLU> resultado = new ArrayList<ReferenciasSinPLU>();
		List<DetallePedido> referenciasSinPLU=detallePedidoDao.referenciasSinPLUAAniadir(codCentro, idSesion);
		for(DetallePedido referenciaSinPLU:referenciasSinPLU){
			obtenerAlarmaYInsertarAlarma(codCentro, referenciaSinPLU.getCodArticulo());
		}

		List<DetallePedido> referenciasSinPLUAMostrar=detallePedidoDao.referenciasSinPLUAMostrar(codCentro, idSesion, seccionId);
		for (DetallePedido referenciaSinPLUAMostrar : referenciasSinPLUAMostrar){
			aux = new ReferenciasSinPLU();
			aux.setCodCentro(referenciaSinPLUAMostrar.getCodCentro());
			aux.setCodArt(referenciaSinPLUAMostrar.getCodArticulo());
			aux.setDescArt(referenciaSinPLUAMostrar.getDescriptionArt());
			aux.setCodError(0L);
			aux.setDescError(" ");
			aux.setCodAgrupacionBalanza(referenciaSinPLUAMostrar.getCodGrupoBalanza());
			aux.setDescAgrupacionBalanza(referenciaSinPLUAMostrar.getDescGrupoBalanza());
			aux.setPlu(0L);
			resultado.add(aux);
		}
		alarmasPLUService.initModifiedAlarmList(codCentro);
		return resultado;

	}
	@Override
	public List<ReferenciasSinPLU> saveReferenciasSinPLU(List<ReferenciasSinPLU> listaReferenciasSinPlu, String nombreUsuario) throws Exception{
		List<ReferenciasSinPLU> resultado = new ArrayList<ReferenciasSinPLU>();
		List<ReferenciasSinPLU> salida = new ArrayList<ReferenciasSinPLU>();
		Long codCentro=listaReferenciasSinPlu.get(0).getCodCentro();
		List<ReferenciasSinPLU> listaEnviarWS = new ArrayList<ReferenciasSinPLU>();
		final String msg = "OK#"+nombreUsuario;
		//Filtramos las referencias que tienen el PLU relleno
		for(ReferenciasSinPLU referenciasSinPLU:listaReferenciasSinPlu){
			if(referenciasSinPLU.getPlu() > 0){
				listaEnviarWS.add(referenciasSinPLU);
			}else{
				resultado.add(referenciasSinPLU);
			}
		}
		//Si existe alguna referencia con PLU relleno entonces se realiza la llamada
		if(listaEnviarWS.size()>0){
			ModificarPLUSRequestType request = new ModificarPLUSRequestType();
			request.setCodigoCentro(BigInteger.valueOf(codCentro));
			request.setTipoMantenimiento("MP");
			
			ReferenciaModType[] refs = fromReferenciasToDatosGISAE(listaEnviarWS);
			request.setReferencias(refs);
			ModificarPLUSResponseType response = null;
			try {
				response = alarmasPLUDao.setPLUsGISAE(request);
			} catch (Exception e) {
				Locale locale = LocaleContextHolder.getLocale();
				e.printStackTrace();
				throw new Exception(messageSource.getMessage("p114_popupPluPendiente.errorWS", null, locale) + ": "+e.getMessage());
			}
			//Si es ok insertar en la tabla de alarmas
			if (response!=null && (response.getCodigoRespuesta().equalsIgnoreCase("OK"))){
				if (response.getReferencias()!=null && response.getReferencias().length>0){
					for (ReferenciaResModType ref : response.getReferencias()){
						obtenerAlarmaYInsertarAlarma(codCentro, ref.getCodigoReferencia().longValue());
						this.anotarMsgError(codCentro,ref.getCodigoReferencia().longValue(),ref.getGrupoBalanza().longValue(),msg);
					}
				}
			}else{
				if (response.getReferencias()!=null && response.getReferencias().length>0){
					for (ReferenciaResModType ref : response.getReferencias()){
						Long codArticulo = ref.getCodigoReferencia()!=null?ref.getCodigoReferencia().longValue():null;
						Long agruBalanza = ref.getGrupoBalanza()!=null?ref.getGrupoBalanza().longValue():null;
						if (codArticulo!=null && agruBalanza!=null){
							if (ref.getCodigoError() != null && ref.getCodigoError().intValue() > 0){
								for(ReferenciasSinPLU referenciasSinPLU:listaEnviarWS){
									if(referenciasSinPLU.getCodArt().equals(codArticulo) && referenciasSinPLU.getCodAgrupacionBalanza().equals(agruBalanza)){
										ReferenciasSinPLU aux = crearReferenciaSinPLUResponseWS(ref,referenciasSinPLU);
										salida.add(aux);
									}
								}
							}else{
								obtenerAlarmaYInsertarAlarma(codCentro, ref.getCodigoReferencia().longValue());
								this.anotarMsgError(codCentro,ref.getCodigoReferencia().longValue(),ref.getGrupoBalanza().longValue(),msg);
							}
						}
					}
				}
			}
			alarmasPLUService.initModifiedAlarmList(codCentro);
		}
		for(ReferenciasSinPLU referenciasSinPLU:resultado){
			salida.add(referenciasSinPLU);
		}
		return salida;
	}
	
	private void anotarMsgError(Long codCentro, Long codigoReferencia, Long grupoBalanza, String msg) {
		alarmasPLUDao.anotarMsgError(codCentro,codigoReferencia,grupoBalanza, msg);
	}
	
	private ReferenciaModType[] fromReferenciasToDatosGISAE(List<ReferenciasSinPLU> referencias) {
		ReferenciaModType[] output = new ReferenciaModType[referencias.size()];
		for (int i=0; i<referencias.size(); i++){
			final ReferenciasSinPLU referencia = referencias.get(i);
			final Long codReferencia = referencia.getCodArt();
			final Long grupoBalanza = referencia.getCodAgrupacionBalanza();
			final Long plu = referencia.getPlu();
			final Long pluAnt = 0L;
			ReferenciaModType ref = new ReferenciaModType();
			ref.setCodigoReferencia(BigInteger.valueOf(codReferencia));
			ref.setGrupoBalanza(grupoBalanza!=null ? BigInteger.valueOf(grupoBalanza) : null);
			ref.setPLU(plu!=null ? BigInteger.valueOf(plu) : null);
			ref.setPLU_ANTIGUO(pluAnt!=null?BigInteger.valueOf(pluAnt) : null);
			output[i] = ref;
		}
		return output;
	}
	
	private void obtenerAlarmaYInsertarAlarma(Long codCentro, Long codArt) throws Exception{
		List<AlarmaPLU> alarmas = alarmasPLUService.obtenerDatosGisae(codCentro, codArt);
		if (alarmas!=null && alarmas.size()>0){
			for (AlarmaPLU alarma : alarmas){
				List<AlarmaPLU> lista = new ArrayList<AlarmaPLU>();
				lista.add(alarma);
				//Si existe la alarma se modifica, sino existe se inserta
				if(this.existeAlarma(codCentro, codArt,alarma.getGrupoBalanza())){
					alarmasPLUService.updatePLU(lista);
				}else{
					alarmasPLUService.insertarDatosEnTablaIntermedia(lista);
				}
			}
		}
	}
	
	private boolean existeAlarma(Long codCentro, Long codArt, Long grupoBalanza) throws Exception{ 
		return alarmasPLUService.existeAlarma(codCentro, codArt,grupoBalanza);
	}
	
	private ReferenciasSinPLU crearReferenciaSinPLUResponseWS(ReferenciaResModType ref,ReferenciasSinPLU referenciasSinPLU) {
		ReferenciasSinPLU aux = new ReferenciasSinPLU();
		aux.setCodCentro(referenciasSinPLU.getCodCentro());
		aux.setCodArt(ref.getCodigoReferencia().longValue());
		aux.setDescArt(referenciasSinPLU.getDescArt());
		aux.setCodError(ref.getCodigoError().longValue());
		aux.setDescError(ref.getMensajeError());
		aux.setCodAgrupacionBalanza(ref.getGrupoBalanza().longValue());
		aux.setDescAgrupacionBalanza(referenciasSinPLU.getDescAgrupacionBalanza());
		aux.setPlu(ref.getPLU().longValue());
		return aux;
	}
}
