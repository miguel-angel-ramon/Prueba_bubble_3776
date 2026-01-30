package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VArtSfmDao;
import es.eroski.misumi.dao.iface.VUltimoPedidoNsrDao;
import es.eroski.misumi.model.MotivoTengoMuchoPoco;
import es.eroski.misumi.model.MotivoTengoMuchoPocoLista;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.SfmCapacidadFacing;
import es.eroski.misumi.model.SfmCapacidadFacingPagina;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VArtSfm;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.MotivoTengoMuchoPocoService;
import es.eroski.misumi.service.iface.UtilidadesVegalsaService;
import es.eroski.misumi.service.iface.VArtSfmService;
import es.eroski.misumi.service.iface.VRelacionArticuloService;
import es.eroski.misumi.service.iface.VSurtidoTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.Utilidades;
import es.eroski.misumi.util.VArtSfmComparator;
import es.eroski.misumi.util.iface.PaginationManager;

@Service(value = "VArtSfmService")
public class VArtSfmServiceImpl implements VArtSfmService {
	//private static Logger logger = Logger.getLogger(VArtSfmServiceImpl.class);
	private PaginationManager<VArtSfm> paginationManager = new PaginationManagerImpl<VArtSfm>();
    
    @Autowired
  	private VUltimoPedidoNsrDao vUltimoPedidoNsrDao;
    
	@Autowired
	private VRelacionArticuloService vRelacionArticuloService;
	
	@Autowired
	private MotivoTengoMuchoPocoService motivoTengoMuchoPocoService;

	@Autowired
	private VSurtidoTiendaService vSurtidoTiendaService;
	@Autowired
	private VArtSfmDao vArtSfmDao;
	
	@Autowired
	private UtilidadesVegalsaService utilidadesVegalsaService;

    @Autowired
	private HttpSession httpSession;
    
	@Override
	 public SfmCapacidadFacing consultaSfm(VArtSfm vArtSfm) throws Exception {
		SfmCapacidadFacing sfmCapacidad = prvObtenerSfmFacCap(vArtSfm);
		if (sfmCapacidad !=null) {
			sfmCapacidad.setFlgCapacidad("N");
			sfmCapacidad.setFlgFacing("N");
		}
		return sfmCapacidad;		
	}

	@Override
	 public SfmCapacidadFacing consultaCap(VArtSfm vArtSfm) throws Exception {
		SfmCapacidadFacing sfmCapacidad = prvObtenerSfmFacCap(vArtSfm);
		if (sfmCapacidad !=null) {
			sfmCapacidad.setFlgCapacidad("S");
			sfmCapacidad.setFlgFacing("N");
		}
		return sfmCapacidad;
	}
	
	@Override
	 public SfmCapacidadFacing consultaFac(VArtSfm vArtSfm) throws Exception {
		SfmCapacidadFacing sfmCapacidad = prvObtenerSfmFacCap(vArtSfm);
		if (sfmCapacidad !=null) {
			sfmCapacidad.setFlgCapacidad("N");
			sfmCapacidad.setFlgFacing("S");
		}
		return sfmCapacidad;
	}

	@Override
	public void aplicarCorreccionConsultaFacConLotes(VArtSfm vArtSfm, SfmCapacidadFacing sfmCapacidad) throws Exception{
		if (sfmCapacidad != null && sfmCapacidad.getDatos() != null && sfmCapacidad.getDatos().size()==1){
			VArtSfm dato = sfmCapacidad.getDatos().get(0);
			if (dato != null && dato.getNivelLote() != null && dato.getNivelLote() > 0){
				vArtSfm.setCodArticuloLote(vArtSfm.getCodArticulo());
				//vArtSfm.setCodArticulo(null);
				SfmCapacidadFacing sfmCapacidadLote = prvObtenerSfmFacCap(vArtSfm);
				// MISUMI-365: Corrección Ticket#2022101710793141 — MISUMI-JAVA SFM-FAC-CAP PISTOLA referencias textil tipo lote
				if (sfmCapacidadLote!=null && sfmCapacidadLote.getDatos()!=null){
					sfmCapacidad.getDatos().addAll(sfmCapacidadLote.getDatos());
				}
			}
		}
	}

	private SfmCapacidadFacing prvObtenerSfmFacCap(VArtSfm vArtSfm) {
		return this.vArtSfmDao.obtenerSfmFacCap(
				vArtSfm.getCodLoc(), 
				vArtSfm.getCodArticulo(),
				null, //vArtSfm.getNivel(),
				vArtSfm.getCodN1(),
				vArtSfm.getCodN2(),
				vArtSfm.getCodN3(),
				vArtSfm.getCodN4(),
				vArtSfm.getCodN5(),
				vArtSfm.getPedir(),
				vArtSfm.getLoteSN(),
				vArtSfm.getCodArticuloLote()
				);
	}
	
	@Override
	public SfmCapacidadFacing actualizacionSfm(List<VArtSfm> listaModificadosActualizacion, String usuario) throws Exception {
		return this.vArtSfmDao.actualizacionSfmFacCap(listaModificadosActualizacion, Constantes.SFMCAP_TIPO_LISTADO_SFM, usuario);
	}
	@Override
	public SfmCapacidadFacing actualizacionCap(List<VArtSfm> listaModificadosActualizacion, String usuario) throws Exception {
		return this.vArtSfmDao.actualizacionSfmFacCap(listaModificadosActualizacion, Constantes.SFMCAP_TIPO_LISTADO_CAP, usuario);
	}
	
	@Override
	public SfmCapacidadFacing actualizacionFac(List<VArtSfm> listaModificadosActualizacion, String usuario) throws Exception {
		return this.vArtSfmDao.actualizacionSfmFacCap(listaModificadosActualizacion, Constantes.SFMCAP_TIPO_LISTADO_FAC, usuario);
	}
	
	
	@Override
	 public Long recalcularCapacidad(VArtSfm vArtSfm) throws Exception {
		return this.vArtSfmDao.recalcularCapacidad(
				vArtSfm.getCodLoc(),
				vArtSfm.getCodArticulo(),
				vArtSfm.getFacingCentro()
				);		
	}

	@Override
	public boolean esReferenciaExpositor(VArtSfm vArtSfm) throws Exception {
		boolean esExpositor = false;
		if (vArtSfm != null && Constantes.REFERENCIA_EXPOSITOR.equals(vArtSfm.getTipoExpositor())){
			esExpositor = true;
		}
		return esExpositor;
	}
	
	@Override
	public SfmCapacidadFacing calcularCampoNSR(SfmCapacidadFacing sfmCapacidadFacing) throws Exception {
		
		if (sfmCapacidadFacing != null && sfmCapacidadFacing.getDatos() != null && sfmCapacidadFacing.getEstado() != null && sfmCapacidadFacing.getEstado().longValue() == 0){
			sfmCapacidadFacing.setDatos(this.vUltimoPedidoNsrDao.completarCampoNSR(sfmCapacidadFacing.getDatos()));
		}
		return sfmCapacidadFacing;
	}
	
	@Override
	public SfmCapacidadFacingPagina calcularCampoNSR(SfmCapacidadFacingPagina sfmCapacidadFacingPagina) throws Exception {
		
		if (sfmCapacidadFacingPagina != null && sfmCapacidadFacingPagina.getDatos() != null && sfmCapacidadFacingPagina.getDatos().getRows() != null && sfmCapacidadFacingPagina.getEstado() != null && sfmCapacidadFacingPagina.getEstado().longValue() == 0){
			sfmCapacidadFacingPagina.getDatos().setRows(this.vUltimoPedidoNsrDao.completarCampoNSR(sfmCapacidadFacingPagina.getDatos().getRows()));
		}
		return sfmCapacidadFacingPagina;
	}
	
	@Override
	public SfmCapacidadFacingPagina calcularCampoMuchoPoco(SfmCapacidadFacingPagina sfmCapacidadFacingPagina, Long codFpMadre, HttpSession session) throws Exception {
		//Peticion 51292. Si el FLG_MUCHO_POCO es igual a P, hay que poner un link en el campo Stok, pero realmente
		//solo se pintara el link si hay motivos que mostar. Para ello, sera necesario mirar para todos los articulo
		//que tengan FLG_MUCHO_POCO a P, llamando al procedimiento PK_MIS_PROCESOS.P_MOTIVO_TENGO_MUCHOPOCO, si tiene motivos.
		//En el caso de que exitan motivos, indicaremos en el campo flg_pintar_link una S.

		//Nos recorremos la lista.
		if (sfmCapacidadFacingPagina != null && sfmCapacidadFacingPagina.getDatos() != null && sfmCapacidadFacingPagina.getDatos().getRows() != null){
			VArtSfm campo = new VArtSfm();
			for (int i = 0, tam = sfmCapacidadFacingPagina.getDatos().getRows().size(); i < tam; i++)
			{
				campo = (VArtSfm)sfmCapacidadFacingPagina.getDatos().getRows().get(i);
				
				if ((campo.getFlgMuchoPoco() != null) && (campo.getFlgMuchoPoco().equals("P"))){
					//el FLG_MUCHO_POCO es igual a P, miramos si tiene motivos


					MotivoTengoMuchoPoco motivoTengoMuchoPoco = new MotivoTengoMuchoPoco();
					motivoTengoMuchoPoco.setCodCentro(campo.getCodLoc());
					motivoTengoMuchoPoco.setTipo("P");
					motivoTengoMuchoPoco.setCodArticulo(campo.getCodArticulo());

					// Para los parametros stockBajo y sobreStockInferior, debemos mirar cual es el valor mas grande (facing * 2) o ventaMedia y 
					//quedarnos con el valor mas alto

					Double stockBajo = null;
					Double stockAlto = null;

					Double multiplicador = new Double(1);

					//Comprobación de si una referencia es FFPP 
					ReferenciasCentro referenciasCentro = new ReferenciasCentro();
					referenciasCentro.setCodCentro(campo.getCodLoc());
					referenciasCentro.setCodArt(campo.getCodArticulo());
					
					User user = (User) session.getAttribute("user");
					boolean tratamientoVegalsaAux = utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), referenciasCentro.getCodArt());
					referenciasCentro = this.vRelacionArticuloService.obtenerFfppActivaOUnitaria(referenciasCentro, tratamientoVegalsaAux);

					//Si es un FFPP no tiene sentido el multiplicador
					if (!referenciasCentro.isTieneUnitaria()){
						
						//VFacingX vFacingXRes = new VFacingX();
						//VFacingX vFacingX = new VFacingX();

						//vFacingX.setCodArticulo(codFpMadre); 
						//vFacingX.setCodCentro(campo.getCodLoc());
						//vFacingXRes = this.vFacingXService.findOne(vFacingX);

						//if (vFacingXRes != null && vFacingXRes.getFacingX()!=null){
							multiplicador = campo.getMultiplicadorFacing();
						//} 
					}
					if ((campo.getFacingCentro() * multiplicador) > campo.getVentaMedia().longValue()) {

						stockBajo = new Double (campo.getFacingCentro() * multiplicador);
						stockAlto = new Double (campo.getFacingCentro() * multiplicador);
					} else {
						stockBajo = campo.getVentaMedia();
						stockAlto = campo.getVentaMedia();
					}


					motivoTengoMuchoPoco.setStockBajo(stockBajo);
					motivoTengoMuchoPoco.setStockAlto(stockAlto);
					motivoTengoMuchoPoco.setDescripcion("");
					motivoTengoMuchoPoco.setStock(campo.getStock());
					motivoTengoMuchoPoco.setIdSesion(session.getId()+ "_MOT_FFPP");

					if ((stockBajo != 0) && (stockAlto != 0)){
						MotivoTengoMuchoPocoLista motivoTengoMuchoPocoLista = null;
						motivoTengoMuchoPocoLista = this.motivoTengoMuchoPocoService.consultaMotivosTengoMuchoPoco(motivoTengoMuchoPoco);

						if ((motivoTengoMuchoPocoLista != null) && (motivoTengoMuchoPocoLista.getDatos() != null) && (motivoTengoMuchoPocoLista.getDatos().size() > 0)) {
							//Cuando el FLG_MUCHO_POCO es igual a P y hay motivos 
							//hay que poner el campo Stok de color rojo con link.
							sfmCapacidadFacingPagina.getDatos().getRows().get(i).setFlgPintarLinkTengoPoco("S");
							sfmCapacidadFacingPagina.getDatos().getRows().get(i).setStockBajo(stockBajo);
						}
						else{
							//Petición 53653: Cuando el FLG_MUCHO_POCO es igual a P y no hay motivos 
							//hay que poner el campo Stok de color rojo sin link.
							sfmCapacidadFacingPagina.getDatos().getRows().get(i).setFlgPintarTengoPoco("S");
						}
					}

				}
			}
		}
		return sfmCapacidadFacingPagina;
	}
	
	@Override
	public List<VArtSfm> formatearLsf(List<VArtSfm> listaSfm) throws Exception {

		for (int i = 0, tam = listaSfm.size(); i < tam; i++) {

			if(listaSfm.get(i).getFechaSfmDDMMYYYY().length()!=0){
				//Formateamos la fechaSFM a date
				String getfechaSfm = listaSfm.get(i).getFechaSfmDDMMYYYY().substring(0,2) +""+  listaSfm.get(i).getFechaSfmDDMMYYYY().substring(2,4)  +""+  listaSfm.get(i).getFechaSfmDDMMYYYY().substring(4);
				Calendar fechaSfm = Calendar.getInstance();
				fechaSfm .setTime(Utilidades.convertirStringAFecha(getfechaSfm));


				//Obtenemos la fecha actual
				Calendar fechaActual = Calendar.getInstance();


				//Obtenemos  la diferencia entre las dos fechas 
				long c = 24*60*60*1000;

				long diffDays = (long) Math.floor((fechaActual.getTimeInMillis() - fechaSfm.getTimeInMillis())/(c));

				if (diffDays <= 21) {
					//Si la diferencia entre fechas es menor o igual que 21, entonces el limite superior se pondra a ""
					listaSfm.get(i).setLsf(-100.0);
				}else {
					//sfmCapacidadFacingPagina.getDatos().getRows().get(i).setLsf(sfmCapacidadFacingPagina.getDatos().getRows().get(i).getLmin());
				}
			}
		}

		return listaSfm;
	}
	
	@Override
	public List<VArtSfm> ordenarLista(List<VArtSfm> listaSfm, String index, String sortOrder) throws Exception {
		
		if (listaSfm != null && index != null && !index.equals("")){
			Comparator<VArtSfm> comparator = VArtSfmComparator.getComparator(index);
			if (null != listaSfm && listaSfm.size() > 0){
				//Formateamos el campo Lsf segun fecha
				listaSfm = this.formatearLsf(listaSfm);
				Collections.sort(listaSfm, comparator);
				if ("desc".equals(sortOrder)) {//El 'sort' siempre ordena en ascendente, por lo tanto si se quiere 
					//ordenar en descendente le damos la vuelta
					Collections.reverse(listaSfm);
				}
			}
		}
		return listaSfm;
	}
	
	@Override
	public SfmCapacidadFacingPagina crearListaPaginada(SfmCapacidadFacing sfmCapacidadGuardada, List<VArtSfm> listaSfm, Long page, Long max, String index, String sortOrder) throws Exception {
		SfmCapacidadFacingPagina sfmCapacidadFacingPagina = new SfmCapacidadFacingPagina();
		List<VArtSfm> subListaSfm = new ArrayList<VArtSfm>();
		Page<VArtSfm> listaSfmPaginada = null;
		int records = 0;
		Pagination pagination= new Pagination(max,page);
		if (index!=null){
			pagination.setSort(index);
		}
		if (sortOrder!=null){
			pagination.setAscDsc(sortOrder);
		}
		Long elemInicio = (pagination.getPage()-1)*pagination.getRows();
		Long elemFinal = ((pagination.getPage())*pagination.getRows()) + 1;
		
		if (listaSfm != null){ 
			records = listaSfm.size();
			if (elemInicio <= records){
				if (elemFinal > records){
					elemFinal = new Long(records);
				}
				subListaSfm = (listaSfm).subList(Integer.parseInt(elemInicio.toString()), Integer.parseInt(elemFinal.toString()));
			}
		}
		if (subListaSfm != null && subListaSfm.size() > 0 && sfmCapacidadGuardada != null) {
			listaSfmPaginada = this.paginationManager.paginate(new Page<VArtSfm>(), subListaSfm,
					max.intValue(), records, page.intValue());	
			sfmCapacidadFacingPagina.setDatos(listaSfmPaginada);
			sfmCapacidadFacingPagina.setEstado(sfmCapacidadGuardada.getEstado());
			sfmCapacidadFacingPagina.setDescEstado(sfmCapacidadGuardada.getDescEstado());
			sfmCapacidadFacingPagina.setFlgCapacidad(sfmCapacidadGuardada.getFlgCapacidad());
			sfmCapacidadFacingPagina.setFlgFacing(sfmCapacidadGuardada.getFlgFacing());

		} else {
			sfmCapacidadFacingPagina.setDatos(new Page<VArtSfm>());
			sfmCapacidadFacingPagina.setEstado(new Long(0));
			sfmCapacidadFacingPagina.setDescEstado("");

		}
		return sfmCapacidadFacingPagina;
	}
	@Override
	public String calcularPedir(VArtSfm vArtSfm) throws Exception {
		String pedir = "";
		
		User user = (User) httpSession.getAttribute("user");
		
		if (vArtSfm.getCodArticulo() != null && !"".equals(vArtSfm.getCodArticulo()) && user != null){ 

			//Se esta filtrando por referencia, tenemos que consultar V_SURTIDO_TIENDA para saber si el 
			//campo PEDIR es S o N para esta referencia. 

			VSurtidoTienda vSurtidoTiendaRes;
			VSurtidoTienda vSurtidoTienda = new VSurtidoTienda();
			vSurtidoTienda.setCodArt(vArtSfm.getCodArticulo());
			vSurtidoTienda.setCodCentro(user.getCentro().getCodCentro());

			vSurtidoTiendaRes = this.vSurtidoTiendaService.findOne(vSurtidoTienda);

			if (vSurtidoTiendaRes != null) {
				pedir = vSurtidoTiendaRes.getPedir();
			}
		}
		return pedir;
	}
	
}
