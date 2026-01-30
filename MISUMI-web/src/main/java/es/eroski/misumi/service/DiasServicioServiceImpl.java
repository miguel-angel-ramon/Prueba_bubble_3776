package es.eroski.misumi.service;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.DiasServicioDao;
import es.eroski.misumi.dao.iface.VDatosDiarioArtDao;
import es.eroski.misumi.model.DiasServicio;
import es.eroski.misumi.model.FechaEntregaDiasServicio;
import es.eroski.misumi.model.TPedidoAdicional;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.service.iface.DiasServicioService;
import es.eroski.misumi.service.iface.TPedidoAdicionalService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Service(value = "DiasServicioService")
public class DiasServicioServiceImpl implements DiasServicioService {
	
	private static Logger logger = Logger.getLogger(DiasServicioServiceImpl.class);

	@Resource 
	private MessageSource messageSource;
	
	@Autowired
	private TPedidoAdicionalService tPedidoAdicionalService;

    @Autowired
	private DiasServicioDao diasServicioDao;
    
	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;
	
	@Autowired
	private VDatosDiarioArtDao vDatosDiarioArtDao;
	
    @Override
	 public List<DiasServicio> findAll(DiasServicio diasServicio) throws Exception {
		return this.diasServicioDao.findAll(diasServicio);
	}
    
    @Override
	 public DiasServicio recargaDiasServicio(DiasServicio diasServicio) throws Exception {
		return this.diasServicioDao.recargaDiasServicio(diasServicio);
	}
    
    @Override
	 public DiasServicio recargaDiasServicioOfer(DiasServicio diasServicio, String oferta) throws Exception {
		return this.diasServicioDao.recargaDiasServicioOfer(diasServicio, oferta);
	}

    @Override
	 public DiasServicio recargaDiasServicioGrupo(DiasServicio diasServicio) throws Exception {
		return this.diasServicioDao.recargaDiasServicioGrupo(diasServicio);
	}

    @Override
	public DiasServicio cargarDiasServicio(DiasServicio diasServicio, String idSession, Long codFpMadre, HttpSession session) throws Exception{

    	Locale locale = LocaleContextHolder.getLocale();
		DiasServicio diasServicioRes = new DiasServicio();	
		//Se cargan los datos del centro, artículo e idsesion
		DiasServicio diasServicioActual = new DiasServicio();
		diasServicioActual.setCodCentro(diasServicio.getCodCentro());
		diasServicioActual.setCodArt(diasServicio.getCodArt());
		//MISUMI-453 hay q crear la sesion con la 
		
		if (diasServicio.getCodArt()!= null){
			
			try {		
				//MISUMI-453
// Para PRUEBAS. Se comenta
				if (codFpMadre==null){
					VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
					vDatosDiarioArt.setCodArt(diasServicio.getCodArt());
					VDatosDiarioArt vDatosDiarioArtAux = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
					diasServicioActual.setIdsesion(idSession+"_"+vDatosDiarioArtAux.getCodFpMadre());
				}else{
					diasServicioActual.setIdsesion(idSession+"_"+codFpMadre);
				}
				
				//Planogramadas
				TPedidoAdicional tPedidoAdicional = new TPedidoAdicional();
				tPedidoAdicional.setIdSesion(diasServicioActual.getIdsesion());
				tPedidoAdicional.setCodCentro(diasServicioActual.getCodCentro());
				tPedidoAdicional.setCodArticulo(diasServicioActual.getCodArt());
				
				//MISUMI-453 Se añade borrado de pedido adicional porque cada vez que entraba insertaba registros sin límite
				this.tPedidoAdicionalService.deleteDatosSesionPedidoAdicional(diasServicioActual.getCodCentro(),diasServicioActual.getIdsesion());
					
				this.tPedidoAdicionalService.insertPlanogramadas(tPedidoAdicional);
				//Pedidos no gestionados por PBL
				this.tPedidoAdicionalService.insertPedidosHTNoPbl(tPedidoAdicional);
				//Pedidos no gestionados por PBL, gestionados por SIA	
				this.tPedidoAdicionalService.insertPedidosNoAliSIA(tPedidoAdicional);
					
				//MISUMI-453
				if(codFpMadre!=diasServicio.getCodArt()){
					
					//buscar referencias asociadas a la referencia introducida q tengan la misma referencia madre(buscar porque existe algo parecido o igual)
					List<Long> vRelacionArticuloLista = this.vDatosDiarioArtDao.findRefMismaRefMadre(diasServicioActual.getCodCentro(), diasServicioActual.getCodArt());
					for(int i=0;vRelacionArticuloLista.size()>i;i++){
						TPedidoAdicional tPedidoAdicionalRefMadre = new TPedidoAdicional();
						tPedidoAdicionalRefMadre.setIdSesion(diasServicioActual.getIdsesion());
						tPedidoAdicionalRefMadre.setCodCentro(diasServicioActual.getCodCentro());
						tPedidoAdicionalRefMadre.setCodArticulo(vRelacionArticuloLista.get(i));

						this.tPedidoAdicionalService.insertPlanogramadas(tPedidoAdicionalRefMadre);
						//Pedidos no gestionados por PBL
						this.tPedidoAdicionalService.insertPedidosHTNoPbl(tPedidoAdicionalRefMadre);
						//Pedidos no gestionados por PBL, gestionados por SIA
							
						this.tPedidoAdicionalService.insertPedidosNoAliSIA(tPedidoAdicionalRefMadre);
					}
				}

				if (null == diasServicioRes.getCodError() || new Long(0).equals(diasServicioRes.getCodError())){
					diasServicioRes = recargaDiasServicio(diasServicioActual);
				}	

			} catch (Exception e){
				diasServicioRes.setCodError(new Long(1));
				diasServicioRes.setDescError(this.messageSource.getMessage("p40_pedidoAdicional.errorWSBuscarPedidosE",null, locale));
			}

		}		
	    return diasServicioRes;
	}
	
    @Override
	public List<DiasServicio> obtenerDiasServicio(
			DiasServicio diasServicio, 
			String idSession
			) throws Exception{
		
			//Se cargan los datos del centro, artículo, idsesion y mes buscado
			DiasServicio  diasServicioActual = new DiasServicio();
			diasServicioActual.setCodCentro(diasServicio.getCodCentro());
			diasServicioActual.setCodArt(diasServicio.getCodArt());
			diasServicioActual.setFecha(Utilidades.convertirStringAFecha(diasServicio.getFechaPantalla()));
			//MISUMI-453
			VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
			vDatosDiarioArt.setCodArt(diasServicio.getCodArt());
			VDatosDiarioArt vDatosDiarioArtAux = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
			diasServicioActual.setIdsesion(idSession+"_"+vDatosDiarioArtAux.getCodFpMadre());
			//diasServicioActual.setIdsesion(idSession+"_"+diasServicio.getCodArt());
			diasServicioActual.setClasePedido(diasServicio.getClasePedido());

			//Se obtienen los datos desde la tabla DIAS_SERVICIO previamente cargada
			List<DiasServicio> listaFechas = findAll(diasServicioActual);
			
			return listaFechas;
	}

    @Override
	public String getPrimerDiaHabilitado(DiasServicio diasServicio, Long codFpMadre, String idSession)  throws Exception{
    	//MISUMI-453
    	if (codFpMadre==null){
    		VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
    		vDatosDiarioArt.setCodArt(diasServicio.getCodArt());
    		VDatosDiarioArt vDatosDiarioArtAux = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
    		return this.diasServicioDao.getPrimerDiaHabilitado(diasServicio, idSession+"_"+vDatosDiarioArtAux.getCodFpMadre());
    	}else{
    		return this.diasServicioDao.getPrimerDiaHabilitado(diasServicio, idSession+"_"+codFpMadre);
    	}
	}

	@Override
	public DiasServicio findOne(DiasServicio diasServicio) throws Exception {
		// TODO Auto-generated method stub
		DiasServicio diasServicioRes = null;
		List<DiasServicio> listDiasServicio = this.diasServicioDao.findAll(diasServicio);
		if (listDiasServicio!= null && !listDiasServicio.isEmpty()){
			diasServicioRes = listDiasServicio.get(0);
		}
		return diasServicioRes;
	}
	
	@Override
	public void actualizarDiasServicio(Long centro, Long referencia, Long codFpMadre, String idSession) throws Exception{
		FechaEntregaDiasServicio fechasServicio=this.diasServicioDao.cargarDiasServicioDServicio(centro,referencia);
		//MISUMI-453
		if (codFpMadre ==null){
			VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
			vDatosDiarioArt.setCodArt(referencia);
			VDatosDiarioArt vDatosDiarioArtAux = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
			this.diasServicioDao.actualizarDiasServicio(centro,referencia,fechasServicio, vDatosDiarioArtAux.getCodFpMadre(), idSession);
		}else{
			this.diasServicioDao.actualizarDiasServicio(centro, referencia, fechasServicio, codFpMadre, idSession);
		}
	}
}
