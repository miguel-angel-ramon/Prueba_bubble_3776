package es.eroski.misumi.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.PedidosAdCentralSPDao;
import es.eroski.misumi.dao.iface.VDatosDiarioArtDao;
import es.eroski.misumi.model.PedidoAdicionalContadores;
import es.eroski.misumi.model.PedidoAdicionalE;
import es.eroski.misumi.model.PedidoAdicionalEC;
import es.eroski.misumi.model.PedidoAdicionalEM;
import es.eroski.misumi.model.PedidoAdicionalM;
import es.eroski.misumi.model.PedidoAdicionalMO;
import es.eroski.misumi.model.PedidoAdicionalVC;
import es.eroski.misumi.model.PedidoHTNoPbl;
import es.eroski.misumi.model.PedidoHTNoPblLista;
import es.eroski.misumi.model.PedidosAdCentral;
import es.eroski.misumi.model.TEncargosClte;
import es.eroski.misumi.model.TPedidoAdicional;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VPlanPedidoAdicional;
import es.eroski.misumi.service.iface.PedidoAdicionalECService;
import es.eroski.misumi.service.iface.PedidoAdicionalEService;
import es.eroski.misumi.service.iface.PedidoAdicionalMOService;
import es.eroski.misumi.service.iface.PedidoAdicionalMService;
import es.eroski.misumi.service.iface.PedidoAdicionalService;
import es.eroski.misumi.service.iface.PedidoHTNoPblService;
import es.eroski.misumi.service.iface.PedidosAdCentralService;
import es.eroski.misumi.service.iface.TEncargosClteService;
import es.eroski.misumi.service.iface.TPedidoAdicionalService;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;
import es.eroski.misumi.service.iface.VPlanPedidoAdicionalService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;



@Service(value = "PedidoAdicionalService")
public class PedidoAdicionalServiceImpl implements PedidoAdicionalService {
	
	private static Logger logger = Logger.getLogger(PedidoAdicionalServiceImpl.class);
	
	@Autowired
	private TPedidoAdicionalService tPedidoAdicionalService;
	
    @Autowired
    private PedidosAdCentralSPDao pedidosAdCentralSPDao;

	@Autowired
	private PedidosAdCentralService pedidosAdCentralService;
	
	@Autowired
	private PedidoAdicionalEService pedidoAdicionalEService;
	
	@Autowired
	private PedidoAdicionalMService pedidoAdicionalMService;
	
	@Autowired
	private PedidoAdicionalMOService pedidoAdicionalMOService;
	

	@Autowired
	private PedidoAdicionalECService pedidoAdicionalECService;
	
	@Autowired
	private TEncargosClteService tEncargosClteService;
	
	@Autowired
	private PedidoHTNoPblService pedidoHTNoPblService;

	@Autowired
	private VPlanPedidoAdicionalService vPlanPedidoAdicionalService;
	
	@Autowired
	private UtilidadesCapraboService utilidadesCapraboService;

	@Autowired
	private VDatosDiarioArtDao vDatosDiarioArtDao;
	
	public PedidoAdicionalE obtenerArticuloTablaSesionE(String idSesion, Long clasePedido, Long codCentro, Long codArticulo, Long identificador, Long identificadorSIA){

		TPedidoAdicional registro = new TPedidoAdicional();
		
		registro.setIdSesion(idSesion);
		registro.setClasePedido(clasePedido);
		registro.setListaFiltroClasePedido(Arrays.asList(new Long(Constantes.CLASE_PEDIDO_ENCARGO)));
		registro.setCodCentro(codCentro);
		registro.setCodArticulo(codArticulo);
		registro.setIdentificador(identificador);
		registro.setIdentificadorSIA(identificadorSIA);
		registro.setPantalla(Constantes.PANTALLA_LISTADOS);
		
		PedidoAdicionalE articulo = new PedidoAdicionalE();
		
		try {
			List<TPedidoAdicional> listaTPedidoAdicional = this.tPedidoAdicionalService.findAll(registro);
			
			if (listaTPedidoAdicional != null && listaTPedidoAdicional.size()>0)
			{
				registro = (TPedidoAdicional)listaTPedidoAdicional.get(0);
				
				articulo.setCodCentro((registro.getCodCentro() != null && !("".equals(registro.getCodCentro().toString())))?new Long(registro.getCodCentro().toString()):null);
				articulo.setIdentificador((registro.getIdentificador() != null && !("".equals(registro.getIdentificador().toString())))?new Long(registro.getIdentificador().toString()):null);
				articulo.setCodArticulo((registro.getCodArticulo() != null && !("".equals(registro.getCodArticulo().toString())))?new Long(registro.getCodArticulo().toString()):null);
				articulo.setDescriptionArt((registro.getDescriptionArt() != null && !("".equals(registro.getDescriptionArt())))?registro.getDescriptionArt():null);
				articulo.setUniCajaServ((registro.getUniCajaServ() != null && !("".equals(registro.getUniCajaServ().toString())))?new Double(registro.getUniCajaServ().toString()):null);
				articulo.setUsuario((registro.getUsuario() != null && !("".equals(registro.getUsuario())))?registro.getUsuario():null);
				articulo.setPerfil((registro.getPerfil() != null && !("".equals(registro.getPerfil())))?registro.getPerfil():null);
				articulo.setAgrupacion((registro.getAgrupacion() != null && !("".equals(registro.getAgrupacion())))?registro.getAgrupacion():null);
				articulo.setTipoAprovisionamiento((registro.getTipoAprovisionamiento() != null && !("".equals(registro.getTipoAprovisionamiento())))?registro.getTipoAprovisionamiento():null);
				articulo.setUnidadesPedidas((registro.getCajasPedidas() != null && !("".equals(registro.getCajasPedidas().toString())))?new Double(registro.getCajasPedidas().toString()):null);
				articulo.setFecEntrega((registro.getFecEntrega() != null && !("".equals(registro.getFecEntrega())))?registro.getFecEntrega():null);
				articulo.setCajas((registro.getCajas() != null && !("".equals(registro.getCajas().toString())))?(("N".equals(registro.getCajas().toString())?false:true)):null);
				articulo.setExcluir((registro.getExcluir() != null && !("".equals(registro.getExcluir().toString())))?(("N".equals(registro.getExcluir().toString())?false:true)):null);
				articulo.setModificable((registro.getModificable() != null && !("".equals(registro.getModificable())))?registro.getModificable():null);
				articulo.setClasePedido((registro.getClasePedido() != null && !("".equals(registro.getClasePedido())))?registro.getClasePedido():null);
				articulo.setCodError((registro.getCodError() != null && !("".equals(registro.getCodError())))?registro.getCodError():null);
				articulo.setDescError((registro.getDescError() != null && !("".equals(registro.getDescError())))?registro.getDescError():null);
				articulo.setTratamiento(registro.getTratamiento());
				articulo.setEstado((registro.getEstado() != null && !("".equals(registro.getEstado())))?registro.getEstado():null);
				articulo.setIdentificadorSIA((registro.getIdentificadorSIA() != null && !("".equals(registro.getIdentificadorSIA().toString())))?new Long(registro.getIdentificadorSIA().toString()):null);
			}
		} catch (Exception e) {
			logger.error("obtenerArticuloTablaSesionE="+e.toString());
			e.printStackTrace();
		}
			
		return articulo;
	}
	
	public PedidoAdicionalM obtenerArticuloTablaSesionM(String idSesion, Long clasePedido, Long codCentro, Long codArticulo, Long identificador, Long identificadorSIA, Long identificadorVegalsa){

		TPedidoAdicional registro = new TPedidoAdicional();
		
		registro.setIdSesion(idSesion);
		//registro.setClasePedido(clasePedido);
		registro.setListaFiltroClasePedido(Arrays.asList(new Long(Constantes.CLASE_PEDIDO_MONTAJE), new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL)));
		registro.setCodCentro(codCentro);
		registro.setCodArticulo(codArticulo);
		registro.setIdentificador(identificador);
		registro.setIdentificadorSIA(identificadorSIA);
		registro.setIdentificadorVegalsa(identificadorVegalsa);
		registro.setPantalla(Constantes.PANTALLA_LISTADOS);
		if (clasePedido.equals(new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL))){
			registro.setMAC("S");
		}
		
		PedidoAdicionalM articulo = new PedidoAdicionalM();
		
		try {
			List<TPedidoAdicional> listaTPedidoAdicional = this.tPedidoAdicionalService.findAll(registro);
			
			if (listaTPedidoAdicional != null && listaTPedidoAdicional.size()>0)
			{
				registro = (TPedidoAdicional)listaTPedidoAdicional.get(0);
				
				articulo.setCodCentro((registro.getCodCentro() != null && !("".equals(registro.getCodCentro().toString())))?new Long(registro.getCodCentro().toString()):null);
				articulo.setIdentificador((registro.getIdentificador() != null && !("".equals(registro.getIdentificador().toString())))?new Long(registro.getIdentificador().toString()):null);
				articulo.setCodArticulo((registro.getCodArticulo() != null && !("".equals(registro.getCodArticulo().toString())))?new Long(registro.getCodArticulo().toString()):null);
				articulo.setDescriptionArt((registro.getDescriptionArt() != null && !("".equals(registro.getDescriptionArt())))?registro.getDescriptionArt():null);
				articulo.setUniCajaServ((registro.getUniCajaServ() != null && !("".equals(registro.getUniCajaServ().toString())))?new Double(registro.getUniCajaServ().toString()):null);
				articulo.setUsuario((registro.getUsuario() != null && !("".equals(registro.getUsuario())))?registro.getUsuario():null);
				articulo.setPerfil((registro.getPerfil() != null && !("".equals(registro.getPerfil())))?registro.getPerfil():null);
				articulo.setAgrupacion((registro.getAgrupacion() != null && !("".equals(registro.getAgrupacion())))?registro.getAgrupacion():null);
				articulo.setTipoAprovisionamiento((registro.getTipoAprovisionamiento() != null && !("".equals(registro.getTipoAprovisionamiento())))?registro.getTipoAprovisionamiento():null);
				articulo.setBorrable((registro.getBorrable() != null && !("".equals(registro.getBorrable())))?registro.getBorrable():null);
				articulo.setModificable((registro.getModificable() != null && !("".equals(registro.getModificable())))?registro.getModificable():null);
				articulo.setModificableIndiv((registro.getModificableIndiv() != null && !("".equals(registro.getModificableIndiv())))?registro.getModificableIndiv():null);
				articulo.setFechaInicio((registro.getFechaInicio() != null && !("".equals(registro.getFechaInicio())))?registro.getFechaInicio():null);
				articulo.setFechaFin((registro.getFechaFin() != null && !("".equals(registro.getFechaFin())))?registro.getFechaFin():null);
				articulo.setClasePedido((registro.getClasePedido() != null && !("".equals(registro.getClasePedido())))?registro.getClasePedido():null);
				articulo.setFecha2((registro.getFecha2() != null && !("".equals(registro.getFecha2())))?registro.getFecha2():null);
				articulo.setFecha3((registro.getFecha3() != null && !("".equals(registro.getFecha3())))?registro.getFecha3():null);
				articulo.setFecha4((registro.getFecha4() != null && !("".equals(registro.getFecha4())))?registro.getFecha4():null);
				articulo.setCapMax((registro.getCapMax() != null && !("".equals(registro.getCapMax().toString())))?new Double(registro.getCapMax().toString()):null);
				articulo.setCapMin((registro.getCapMin() != null && !("".equals(registro.getCapMin().toString())))?new Double(registro.getCapMin().toString()):null);
				articulo.setCantidad1((registro.getCantidad1() != null && !("".equals(registro.getCantidad1().toString())))?new Double(registro.getCantidad1().toString()):null);
				articulo.setCantidad2((registro.getCantidad2() != null && !("".equals(registro.getCantidad2().toString())))?new Double(registro.getCantidad2().toString()):null);
				articulo.setCantidad3((registro.getCantidad3() != null && !("".equals(registro.getCantidad3().toString())))?new Double(registro.getCantidad3().toString()):null);
				articulo.setTipoPedido((registro.getTipoPedido() != null && !("".equals(registro.getTipoPedido())))?registro.getTipoPedido():null);
				articulo.setExcluir((registro.getExcluir() != null && !("".equals(registro.getExcluir().toString())))?(("N".equals(registro.getExcluir().toString())?false:true)):null);
				articulo.setCodError((registro.getCodError() != null && !("".equals(registro.getCodError())))?registro.getCodError():null);
				articulo.setDescError((registro.getDescError() != null && !("".equals(registro.getDescError())))?registro.getDescError():null);
				articulo.setEsPlanograma((registro.getEsPlanograma() != null && !("".equals(registro.getEsPlanograma())))?registro.getEsPlanograma():null);
				articulo.setFecha5((registro.getFecha5() != null && !("".equals(registro.getFecha5())))?registro.getFecha5():null);
				articulo.setFechaPilada((registro.getFechaPilada() != null && !("".equals(registro.getFechaPilada())))?registro.getFechaPilada():null);
				articulo.setCantidad4((registro.getCantidad4() != null && !("".equals(registro.getCantidad4().toString())))?new Double(registro.getCantidad4().toString()):null);
				articulo.setCantidad5((registro.getCantidad5() != null && !("".equals(registro.getCantidad5().toString())))?new Double(registro.getCantidad5().toString()):null);
				articulo.setTratamiento(registro.getTratamiento());
				articulo.setEstado((registro.getEstado() != null && !("".equals(registro.getEstado())))?registro.getEstado():null);
				articulo.setNoGestionaPbl((registro.getNoGestionaPbl() != null && !("".equals(registro.getNoGestionaPbl())))?registro.getNoGestionaPbl():null);
				articulo.setIdentificadorSIA((registro.getIdentificadorSIA() != null && !("".equals(registro.getIdentificadorSIA().toString())))?new Long(registro.getIdentificadorSIA().toString()):null);
				articulo.setIdentificadorVegalsa((registro.getIdentificadorVegalsa() != null && !("".equals(registro.getIdentificadorVegalsa().toString())))?new Long(registro.getIdentificadorVegalsa().toString()):null);

			}
			
		} catch (Exception e) {
			logger.error("obtenerArticuloTablaSesionM="+e.toString());
			e.printStackTrace();
		}
			
		return articulo;
	}
	
	public PedidoAdicionalMO obtenerArticuloTablaSesionMO(String idSesion, Long clasePedido, Long codCentro, Long codArticulo, Long identificador, Long identificadorSIA, Long identificadorVegalsa){

		TPedidoAdicional registro = new TPedidoAdicional();
		
		registro.setIdSesion(idSesion);
		//registro.setClasePedido(clasePedido);
		registro.setListaFiltroClasePedido(Arrays.asList(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL), new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA)));
		registro.setCodCentro(codCentro);
		registro.setCodArticulo(codArticulo);
		registro.setIdentificador(identificador);
		registro.setIdentificadorSIA(identificadorSIA);
		registro.setIdentificadorVegalsa(identificadorVegalsa);
		registro.setPantalla(Constantes.PANTALLA_LISTADOS);
		if (clasePedido.equals(new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA))){
			registro.setMAC("S");
		}
		
		PedidoAdicionalMO articulo = new PedidoAdicionalMO();
		
		try {
			List<TPedidoAdicional> listaTPedidoAdicional = this.tPedidoAdicionalService.findAll(registro);
			
			if (listaTPedidoAdicional != null && listaTPedidoAdicional.size()>0)
			{
				registro = (TPedidoAdicional)listaTPedidoAdicional.get(0);
				
				articulo.setCodCentro((registro.getCodCentro() != null && !("".equals(registro.getCodCentro().toString())))?new Long(registro.getCodCentro().toString()):null);
				articulo.setIdentificador((registro.getIdentificador() != null && !("".equals(registro.getIdentificador().toString())))?new Long(registro.getIdentificador().toString()):null);
				articulo.setCodArticulo((registro.getCodArticulo() != null && !("".equals(registro.getCodArticulo().toString())))?new Long(registro.getCodArticulo().toString()):null);
				articulo.setDescriptionArt((registro.getDescriptionArt() != null && !("".equals(registro.getDescriptionArt())))?registro.getDescriptionArt():null);
				articulo.setUniCajaServ((registro.getUniCajaServ() != null && !("".equals(registro.getUniCajaServ().toString())))?new Double(registro.getUniCajaServ().toString()):null);
				articulo.setUsuario((registro.getUsuario() != null && !("".equals(registro.getUsuario())))?registro.getUsuario():null);
				articulo.setPerfil((registro.getPerfil() != null && !("".equals(registro.getPerfil())))?registro.getPerfil():null);
				articulo.setAgrupacion((registro.getAgrupacion() != null && !("".equals(registro.getAgrupacion())))?registro.getAgrupacion():null);
				articulo.setOferta((registro.getOferta() != null && !("".equals(registro.getOferta())))?registro.getOferta():null);
				articulo.setTipoAprovisionamiento((registro.getTipoAprovisionamiento() != null && !("".equals(registro.getTipoAprovisionamiento())))?registro.getTipoAprovisionamiento():null);
				articulo.setBorrable((registro.getBorrable() != null && !("".equals(registro.getBorrable())))?registro.getBorrable():null);
				articulo.setModificable((registro.getModificable() != null && !("".equals(registro.getModificable())))?registro.getModificable():null);
				articulo.setModificableIndiv((registro.getModificableIndiv() != null && !("".equals(registro.getModificableIndiv())))?registro.getModificableIndiv():null);
				articulo.setFechaInicio((registro.getFechaInicio() != null && !("".equals(registro.getFechaInicio())))?registro.getFechaInicio():null);
				articulo.setFechaFin((registro.getFechaFin() != null && !("".equals(registro.getFechaFin())))?registro.getFechaFin():null);
				articulo.setClasePedido((registro.getClasePedido() != null && !("".equals(registro.getClasePedido())))?registro.getClasePedido():null);
				articulo.setFecha2((registro.getFecha2() != null && !("".equals(registro.getFecha2())))?registro.getFecha2():null);
				articulo.setFecha3((registro.getFecha3() != null && !("".equals(registro.getFecha3())))?registro.getFecha3():null);
				articulo.setFecha4((registro.getFecha4() != null && !("".equals(registro.getFecha4())))?registro.getFecha4():null);
				articulo.setCapMax((registro.getCapMax() != null && !("".equals(registro.getCapMax().toString())))?new Double(registro.getCapMax().toString()):null);
				articulo.setCapMin((registro.getCapMin() != null && !("".equals(registro.getCapMin().toString())))?new Double(registro.getCapMin().toString()):null);
				articulo.setCantidad1((registro.getCantidad1() != null && !("".equals(registro.getCantidad1().toString())))?new Double(registro.getCantidad1().toString()):null);
				articulo.setCantidad2((registro.getCantidad2() != null && !("".equals(registro.getCantidad2().toString())))?new Double(registro.getCantidad2().toString()):null);
				articulo.setCantidad3((registro.getCantidad3() != null && !("".equals(registro.getCantidad3().toString())))?new Double(registro.getCantidad3().toString()):null);
				articulo.setTipoPedido((registro.getTipoPedido() != null && !("".equals(registro.getTipoPedido())))?registro.getTipoPedido():null);
				articulo.setCodError((registro.getCodError() != null && !("".equals(registro.getCodError())))?registro.getCodError():null);
				articulo.setDescError((registro.getDescError() != null && !("".equals(registro.getDescError())))?registro.getDescError():null);
				articulo.setEsPlanograma((registro.getEsPlanograma() != null && !("".equals(registro.getEsPlanograma())))?registro.getEsPlanograma():null);
				articulo.setExcluir(registro.getExcluir().equals("S")?true:false);
				articulo.setFecha5((registro.getFecha5() != null && !("".equals(registro.getFecha5())))?registro.getFecha5():null);
				articulo.setFechaPilada((registro.getFechaPilada() != null && !("".equals(registro.getFechaPilada())))?registro.getFechaPilada():null);
				articulo.setCantidad4((registro.getCantidad4() != null && !("".equals(registro.getCantidad4().toString())))?new Double(registro.getCantidad4().toString()):null);
				articulo.setCantidad5((registro.getCantidad5() != null && !("".equals(registro.getCantidad5().toString())))?new Double(registro.getCantidad5().toString()):null);
				articulo.setTratamiento(registro.getTratamiento());
				articulo.setEstado((registro.getEstado() != null && !("".equals(registro.getEstado())))?registro.getEstado():null);
				articulo.setNoGestionaPbl((registro.getNoGestionaPbl() != null && !("".equals(registro.getNoGestionaPbl())))?registro.getNoGestionaPbl():null);
				articulo.setIdentificadorSIA((registro.getIdentificadorSIA() != null && !("".equals(registro.getIdentificadorSIA().toString())))?new Long(registro.getIdentificadorSIA().toString()):null);
				articulo.setIdentificadorVegalsa((registro.getIdentificadorVegalsa() != null && !("".equals(registro.getIdentificadorVegalsa().toString())))?new Long(registro.getIdentificadorVegalsa().toString()):null);
			}
		} catch (Exception e) {
			logger.error("obtenerArticuloTablaSesionMO="+e.toString());
			e.printStackTrace();
		}
			
		return articulo;
	}
	
	@Override
	public PedidoAdicionalContadores loadContadoresVegalsa(PedidoAdicionalE pedidoAdicionalE) throws Exception {
		PedidoAdicionalContadores output = new PedidoAdicionalContadores();
		// BUSCAR EN LA TABLA T_MIS_MONTAJES_VEGALSA
		final Long montajesAdicionales = this.tPedidoAdicionalService.findMontajesAdicionalesVegalsa(pedidoAdicionalE, false);
		final Long montajesAdicionalesOferta = this.tPedidoAdicionalService.findMontajesAdicionalesVegalsa(pedidoAdicionalE, true);
		
		output.setContadorMontaje(montajesAdicionales);
		output.setContadorMontajeOferta(montajesAdicionalesOferta);

		output.setContadorEncargos(0L);
		output.setContadorValidarCantExtra(0L);
		output.setContadorEncargosCliente(0L);
		
		return output;
	}
	
	@Override
	public PedidoAdicionalContadores loadContadoresVegalsaReferencia(TPedidoAdicional pedidoAdicional) throws Exception {
		PedidoAdicionalE pedidoAdicionalE = new PedidoAdicionalE();
		pedidoAdicionalE.setCodCentro(pedidoAdicional.getCodCentro());
		pedidoAdicionalE.setCodArticulo(pedidoAdicional.getCodArticulo());
		return this.loadContadoresVegalsa(pedidoAdicionalE);
	}
	public PedidoAdicionalContadores loadContadores(PedidoAdicionalE pedidoAdicionalE, HttpSession session, HttpServletResponse response){
				
		PedidoAdicionalContadores resultado = new PedidoAdicionalContadores();
		
		try {
			User user = (User) session.getAttribute("user");
			boolean esCaprabo = utilidadesCapraboService.esCentroCaprabo(pedidoAdicionalE.getCodCentro(), user.getCode());
			
			if (null == pedidoAdicionalE.getConsultaAlmacenada() || !pedidoAdicionalE.getConsultaAlmacenada()) {
				List<Long> vRelacionArticuloLista = new ArrayList<Long>();
				if(pedidoAdicionalE.getCodArticulo() != null && !("".equals(pedidoAdicionalE.getCodArticulo().toString()))){
					// Buscamos la referencias que tengan la misma referencia madre
					vRelacionArticuloLista = this.vDatosDiarioArtDao.findRefMismaRefMadre(pedidoAdicionalE.getCodCentro(), pedidoAdicionalE.getCodArticulo());
				}
				PedidoAdicionalE pedidoAdicionalEAux=new PedidoAdicionalE();
				pedidoAdicionalEAux.setCodCentro(pedidoAdicionalE.getCodCentro());
				pedidoAdicionalEAux.setGrupo1(pedidoAdicionalE.getGrupo1());
				pedidoAdicionalEAux.setGrupo2(pedidoAdicionalE.getGrupo2());
				pedidoAdicionalEAux.setGrupo3(pedidoAdicionalE.getGrupo3());
				pedidoAdicionalEAux.setClasePedido(new Long(Constantes.CLASE_PEDIDO_ENCARGO));
				pedidoAdicionalEAux.setEsCaprabo(esCaprabo);
				//--------------------------
				// Pestaña Encargos
				// Begin
				// Sólo hay que buscar los pedidos clase 1
				//--------------------------
				
				List<PedidoAdicionalE> listE = new ArrayList<PedidoAdicionalE>();
	
				// Clase pedido 1 del WS y de encargos reservas
				pedidoAdicionalE.setClasePedido(new Long(Constantes.CLASE_PEDIDO_ENCARGO));
				pedidoAdicionalE.setEsCaprabo(esCaprabo);
				listE = this.pedidoAdicionalEService.findAll(pedidoAdicionalE, session);
				
				
				// Cuando hacemos la primera búsqueda eliminamos siempre los
				// registros guardados anteriormente.
				this.eliminarTablaSesionHistorico();

				// Insertar tabla temporal T_PEDIDO_ADICIONAL
				// A partir de la lista obtenida tenemos que insertar en la
				// tabla temporal los registros obtenidos,
				// borrando previamente los posibles registros almacenados.
				this.eliminarTablaSesion(session.getId(), new Long(Constantes.CLASE_PEDIDO_ENCARGO), pedidoAdicionalE.getCodCentro());
				for(int i=0;vRelacionArticuloLista.size()>i;i++){
					
					pedidoAdicionalEAux.setCodArticulo(vRelacionArticuloLista.get(i));
					// Buscamos por cada una de las referencias los montajes adicionales 
					List<PedidoAdicionalE> listMontajesAsociados = this.pedidoAdicionalEService.findAll(pedidoAdicionalEAux, session);
					listE.addAll(listMontajesAsociados);
				}
				if (listE != null && listE.size() > 0) {
					this.insertarTablaSesionE(listE, session.getId(),esCaprabo);
				}
				
				//--------------------------
				// End
				// Pestaña Encargos
				//--------------------------

				//--------------------------
				// Pestaña Montaje Adicional
				// Begin
				// Hay que buscar los pedidos clase 2, clase 7 (sólo cuando es MAC) 
				// y las planogramadas y unirlas en una lista
				//--------------------------
				List<PedidoAdicionalM> listM = new ArrayList<PedidoAdicionalM>();

				// Clase pedido 2
				PedidoAdicionalM pedidoAdicionalM = new PedidoAdicionalM();
				pedidoAdicionalM.setClasePedido(new Long(Constantes.CLASE_PEDIDO_MONTAJE));
				pedidoAdicionalM.setCodCentro(pedidoAdicionalE.getCodCentro());
				pedidoAdicionalM.setGrupo1(pedidoAdicionalE.getGrupo1());
				pedidoAdicionalM.setGrupo2(pedidoAdicionalE.getGrupo2());
				pedidoAdicionalM.setGrupo3(pedidoAdicionalE.getGrupo3());
				pedidoAdicionalM.setCodArticulo(pedidoAdicionalE.getCodArticulo());
				pedidoAdicionalM.setEsCaprabo(esCaprabo);
				
				// Obtenemos la lista de clase pedido 2 del WS y de encargos reservas.
				List<PedidoAdicionalM> listM2 = this.pedidoAdicionalMService.findAll(pedidoAdicionalM, session);
				// Añadimos la lista del WS y de encargos reservas de clase pedido 2 a la lista Montaje Adicional
				listM.addAll(listM2);
				// Si la búsqueda es por referencia tenemos que buscar todas las referencias que tengan la misma referencia madre
				// y realizar la consulta por cada una de ellas para que recupere los montajes si existieran
				
				for(int i=0;vRelacionArticuloLista.size()>i;i++){
					pedidoAdicionalM.setCodArticulo(vRelacionArticuloLista.get(i));
					// Buscamos por cada una de las referencias los montajes adicionales 
					List<PedidoAdicionalM> listMontajesAsociados = this.pedidoAdicionalMService.findAll(pedidoAdicionalM, session);
					listM.addAll(listMontajesAsociados);
				}
				
				
				// Clase pedido 7 
				pedidoAdicionalM.setClasePedido(new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL));
				pedidoAdicionalM.setCodCentro(pedidoAdicionalE.getCodCentro());
				pedidoAdicionalM.setGrupo1(pedidoAdicionalE.getGrupo1());
				pedidoAdicionalM.setGrupo2(pedidoAdicionalE.getGrupo2());
				pedidoAdicionalM.setGrupo3(pedidoAdicionalE.getGrupo3());
				pedidoAdicionalM.setCodArticulo(pedidoAdicionalE.getCodArticulo());
				// Obtenemos la lista de clase pedido 7 del WS.
				List<PedidoAdicionalM> listM7 = this.pedidoAdicionalMService.findAll(pedidoAdicionalM, session);
				
				// Añadimos la lista del WS de clase pedido 7 a la lista Montaje Adicional
				listM.addAll(listM7);
				
				for(int i=0;vRelacionArticuloLista.size()>i;i++){
					pedidoAdicionalM.setCodArticulo(vRelacionArticuloLista.get(i));
					// Buscamos por cada una de las referencias los montajes adicionales 
					List<PedidoAdicionalM> listMontajesAsociados = this.pedidoAdicionalMService.findAll(pedidoAdicionalM, session);
					listM.addAll(listMontajesAsociados);
				}
				
				// No gestionadas por PBL en montaje adicional
				PedidoHTNoPbl pedidoHTNoPblMontAd = new PedidoHTNoPbl();
				pedidoHTNoPblMontAd.setCodCentro(pedidoAdicionalE.getCodCentro());
				pedidoHTNoPblMontAd.setClasePedido(new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL));
				pedidoHTNoPblMontAd.setValidados(Constantes.NO_GESTIONADO_PBL_VALIDADOS_SI);
				pedidoHTNoPblMontAd.setCodArticulo(pedidoAdicionalE.getCodArticulo());
				pedidoHTNoPblMontAd.setGrupo1(pedidoAdicionalE.getGrupo1());
				pedidoHTNoPblMontAd.setGrupo2(pedidoAdicionalE.getGrupo2());
				pedidoHTNoPblMontAd.setGrupo3(pedidoAdicionalE.getGrupo3());
				PedidoHTNoPblLista listaNoGestionadosPblMontAd = this.pedidoHTNoPblService.consultaPedidosHTNoPbl(pedidoHTNoPblMontAd);
				listM = this.addPedidosHTNoPblM(listaNoGestionadosPblMontAd, listM);
				for(int i=0;vRelacionArticuloLista.size()>i;i++){
					pedidoHTNoPblMontAd.setCodArticulo(vRelacionArticuloLista.get(i));
					// Buscamos por cada una de las referencias los montajes adicionales 
					PedidoHTNoPblLista listaNoGestionadosPblMontAdAsociados = this.pedidoHTNoPblService.consultaPedidosHTNoPbl(pedidoHTNoPblMontAd);
					listM = this.addPedidosHTNoPblM(listaNoGestionadosPblMontAdAsociados, listM);
				}

				// Planogramadas
				VPlanPedidoAdicional vPlanPedidoAdicional = new VPlanPedidoAdicional();
				vPlanPedidoAdicional.setCodCentro(pedidoAdicionalE.getCodCentro());
				vPlanPedidoAdicional.setCodArt(pedidoAdicionalE.getCodArticulo());
				vPlanPedidoAdicional.setGrupo1(pedidoAdicionalE.getGrupo1());
				vPlanPedidoAdicional.setGrupo2(pedidoAdicionalE.getGrupo2());
				vPlanPedidoAdicional.setGrupo3(pedidoAdicionalE.getGrupo3());
				vPlanPedidoAdicional.setCodArt(pedidoAdicionalE.getCodArticulo());
				vPlanPedidoAdicional.setEsOferta(Constantes.NO_OFERTA);
				// Obtenemos la lista de planogramadas de Base de Datos.
				List<VPlanPedidoAdicional> listPlanogramas = this.vPlanPedidoAdicionalService.findAll(vPlanPedidoAdicional);
				
				// hacer nueva 
				
				// Añadimos la lista de Base de Datos de planogramadas a la lista Montaje Adicional
				listM = this.addPlanogramas(listPlanogramas, listM);
				for(int i=0;vRelacionArticuloLista.size()>i;i++){
					vPlanPedidoAdicional.setCodArt(vRelacionArticuloLista.get(i));
					// Buscamos por cada una de las referencias los montajes adicionales 
					List<VPlanPedidoAdicional> listPlanogramasAsociados = this.vPlanPedidoAdicionalService.findAll(vPlanPedidoAdicional);
					listM = this.addPlanogramas(listPlanogramasAsociados, listM);
				}
				// Insertar tabla temporal T_PEDIDO_ADICIONAL
				// A partir de la lista obtenida tenemos que insertar en la
				// tabla temporal los registros obtenidos,
				// borrando previamente los posibles registros almacenados.
				this.eliminarTablaSesion(session.getId(), new Long(Constantes.CLASE_PEDIDO_MONTAJE), pedidoAdicionalE.getCodCentro());
				this.eliminarTablaSesion(session.getId(), new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL), pedidoAdicionalE.getCodCentro());
				if (listM != null && listM.size() > 0) {
					this.insertarTablaSesionM(listM, session.getId(),esCaprabo);
				}
				//--------------------------
				// End
				// Pestaña Montaje Adicional
				//--------------------------

				//--------------------------
				// Pestaña Montaje Oferta
				// Begin
				// Hay que buscar los pedidos clase 3, clase 8 (sólo cuando es MAC) 
				// y las planogramadas y unirlas en una lista
				//--------------------------
				List<PedidoAdicionalMO> listMO = new ArrayList<PedidoAdicionalMO>();

				// Clase pedido 3
				PedidoAdicionalMO pedidoAdicionalMO = new PedidoAdicionalMO();
				pedidoAdicionalMO.setClasePedido(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL));
				pedidoAdicionalMO.setCodCentro(pedidoAdicionalE.getCodCentro());
				pedidoAdicionalMO.setGrupo1(pedidoAdicionalE.getGrupo1());
				pedidoAdicionalMO.setGrupo2(pedidoAdicionalE.getGrupo2());
				pedidoAdicionalMO.setGrupo3(pedidoAdicionalE.getGrupo3());
				pedidoAdicionalMO.setCodArticulo(pedidoAdicionalE.getCodArticulo());
				// Obtenemos la lista de clase pedido 3 del WS y de encargos reservas.
				List<PedidoAdicionalMO> listMO3 = this.pedidoAdicionalMOService.findAll(pedidoAdicionalMO, session);
				
				// Añadimos la lista del WS y de encargos reservas de clase pedido 3 a la lista Montaje Oferta
				listMO.addAll(listMO3);
				
				for(int i=0;vRelacionArticuloLista.size()>i;i++){
					pedidoAdicionalMO.setCodArticulo(vRelacionArticuloLista.get(i));
					// Buscamos por cada una de las referencias los montajes adicionales 
					List<PedidoAdicionalMO> listMO3Asociados = this.pedidoAdicionalMOService.findAll(pedidoAdicionalMO, session);
					listMO.addAll(listMO3Asociados);
				}
				
				// Clase pedido 8 
				pedidoAdicionalMO.setClasePedido(new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA));
				pedidoAdicionalMO.setCodCentro(pedidoAdicionalE.getCodCentro());
				pedidoAdicionalMO.setGrupo1(pedidoAdicionalE.getGrupo1());
				pedidoAdicionalMO.setGrupo2(pedidoAdicionalE.getGrupo2());
				pedidoAdicionalMO.setGrupo3(pedidoAdicionalE.getGrupo3());
				pedidoAdicionalMO.setCodArticulo(pedidoAdicionalE.getCodArticulo());
				pedidoAdicionalMO.setEsCaprabo(esCaprabo);
				
			
				// Obtenemos la lista de clase pedido 8 del WS y de encargos reservas.
				List<PedidoAdicionalMO> listMO8 = this.pedidoAdicionalMOService.findAll(pedidoAdicionalMO,session);
				
				
				// Añadimos la lista del WS y de encargos reservas de clase pedido 8 a la lista Montaje Adicional
				listMO.addAll(listMO8);
				for(int i=0;vRelacionArticuloLista.size()>i;i++){
					pedidoAdicionalMO.setCodArticulo(vRelacionArticuloLista.get(i));
					// Buscamos por cada una de las referencias los montajes adicionales 
					List<PedidoAdicionalMO> listMO8Asociados = this.pedidoAdicionalMOService.findAll(pedidoAdicionalMO, session);
					listMO.addAll(listMO8Asociados);
				}
				// No gestionadas por PBL en montaje adicional en oferta
				PedidoHTNoPbl pedidoHTNoPblMontAdOf = new PedidoHTNoPbl();
				pedidoHTNoPblMontAdOf.setCodCentro(pedidoAdicionalE.getCodCentro());
				pedidoHTNoPblMontAdOf.setClasePedido(new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA));
				pedidoHTNoPblMontAdOf.setValidados(Constantes.NO_GESTIONADO_PBL_VALIDADOS_SI);
				pedidoHTNoPblMontAdOf.setCodArticulo(pedidoAdicionalE.getCodArticulo());
				pedidoHTNoPblMontAdOf.setGrupo1(pedidoAdicionalE.getGrupo1());
				pedidoHTNoPblMontAdOf.setGrupo2(pedidoAdicionalE.getGrupo2());
				pedidoHTNoPblMontAdOf.setGrupo3(pedidoAdicionalE.getGrupo3());
				PedidoHTNoPblLista listaNoGestionadosPblMontAdOf = this.pedidoHTNoPblService.consultaPedidosHTNoPbl(pedidoHTNoPblMontAdOf);
				listMO = this.addPedidosHTNoPblMO(listaNoGestionadosPblMontAdOf, listMO);
				for(int i=0;vRelacionArticuloLista.size()>i;i++){
					pedidoHTNoPblMontAdOf.setCodArticulo(vRelacionArticuloLista.get(i));
					// Buscamos por cada una de las referencias los montajes adicionales 
					PedidoHTNoPblLista listaNoGestionadosPblMontAdOfAsociado = this.pedidoHTNoPblService.consultaPedidosHTNoPbl(pedidoHTNoPblMontAdOf);
					listMO = this.addPedidosHTNoPblMO(listaNoGestionadosPblMontAdOfAsociado, listMO);
				}
				// Planogramadas
				vPlanPedidoAdicional.setEsOferta(Constantes.SI_OFERTA);
				vPlanPedidoAdicional.setCodArt(pedidoAdicionalE.getCodArticulo());
				// Obtenemos la lista de planogramadas de Base de Datos.
				listPlanogramas = this.vPlanPedidoAdicionalService.findAll(vPlanPedidoAdicional);
				// Añadimos la lista de Base de Datos de planogramadas a la lista Montaje Oferta
				listMO = this.addPlanogramasOferta(listPlanogramas, listMO);
				for(int i=0;vRelacionArticuloLista.size()>i;i++){
					vPlanPedidoAdicional.setCodArt(vRelacionArticuloLista.get(i));
					// Buscamos por cada una de las referencias los montajes adicionales 
					List<VPlanPedidoAdicional> listPlanogramasAsociados = this.vPlanPedidoAdicionalService.findAll(vPlanPedidoAdicional);
					listMO = this.addPlanogramasOferta(listPlanogramasAsociados, listMO);
				}

				// Insertar tabla temporal T_PEDIDO_ADICIONAL
				// A partir de la lista obtenida tenemos que insertar en la
				// tabla temporal los registros obtenidos,
				// borrando previamente los posibles registros almacenados.
				this.eliminarTablaSesion(session.getId(), new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL), pedidoAdicionalE.getCodCentro());
				this.eliminarTablaSesion(session.getId(), new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA), pedidoAdicionalE.getCodCentro());
				if (listMO != null && listMO.size() > 0) {
					this.insertarTablaSesionMO(listMO, session.getId(),esCaprabo);
				}
				//--------------------------
				// End
				// Pestaña Montaje Oferta
				//--------------------------
				
				// No gestionadas por PBL empujes
				PedidoHTNoPbl pedidoHTNoPblEmp = new PedidoHTNoPbl();
				pedidoHTNoPblEmp.setCodCentro(pedidoAdicionalE.getCodCentro());
				pedidoHTNoPblEmp.setClasePedido(new Long(Constantes.CLASE_PEDIDO_EMPUJE));
				pedidoHTNoPblEmp.setValidados(Constantes.NO_GESTIONADO_PBL_VALIDADOS_TODOS);
				pedidoHTNoPblEmp.setCodArticulo(pedidoAdicionalE.getCodArticulo());
				pedidoHTNoPblEmp.setGrupo1(pedidoAdicionalE.getGrupo1());
				pedidoHTNoPblEmp.setGrupo2(pedidoAdicionalE.getGrupo2());
				pedidoHTNoPblEmp.setGrupo3(pedidoAdicionalE.getGrupo3());
				
				PedidoHTNoPblLista listaNoGestionadosPblEmp = this.pedidoHTNoPblService.consultaPedidosHTNoPbl(pedidoHTNoPblEmp);
				
				List<PedidoAdicionalEM> listEmpuje = new ArrayList<PedidoAdicionalEM>();
				listEmpuje = this.addPedidosHTNoPblEM(listaNoGestionadosPblEmp, listEmpuje);
				for(int i=0;vRelacionArticuloLista.size()>i;i++){
					pedidoHTNoPblEmp.setCodArticulo(vRelacionArticuloLista.get(i));
					// Buscamos por cada una de las referencias los montajes adicionales 
					PedidoHTNoPblLista listaNoGestionadosPblEmpAsociado = this.pedidoHTNoPblService.consultaPedidosHTNoPbl(pedidoHTNoPblEmp);
					listEmpuje = this.addPedidosHTNoPblEM(listaNoGestionadosPblEmpAsociado, listEmpuje);
				}
				
				//A partir de la lista obtenida tenemos que insertar en la tabla temporal los registros obtenidos,
				//borrando previamente los posibles registros almacenados.
				this.eliminarTablaSesion(session.getId(),new Long(Constantes.CLASE_PEDIDO_EMPUJE),pedidoAdicionalE.getCodCentro());
				if (listEmpuje != null && listEmpuje.size()>0) {
					this.insertarTablaSesionEM(listEmpuje,session.getId(),esCaprabo);
				}
				
				
				//--------------------------
				// Pestaña Validar Cantidades Extra
				// Begin
				// Hay que buscar los pedidos clase 4 y 5 (sólo cuando es MAC) 
				// y unirlas en una lista
				//--------------------------
				List<PedidoAdicionalVC> listVC = new ArrayList<PedidoAdicionalVC>();
	
				// No gestionadas por PBL validar cantidades extra
				//Como puede haber de 2 tipos (4 o 5) optamos que para este caso se tratarán como tipo 4
				
				//Incidencia 442633-17: Como pueden venir pedidos con clase pedido distinto a 4 y 5
				//añadimos en la llamada al procedimiento consultaPedidosHTNoPbl los tipos de pedido 4 y 5.
				
				// Clase pedido 4
				PedidoHTNoPbl pedidoHTNoPblVC = new PedidoHTNoPbl();
				pedidoHTNoPblVC.setClasePedido(new Long(Constantes.CLASE_PEDIDO_VALIDAR_CANT_EXTRA_4));
				pedidoHTNoPblVC.setCodCentro(pedidoAdicionalE.getCodCentro());
				pedidoHTNoPblVC.setValidados(Constantes.NO_GESTIONADO_PBL_VALIDADOS_NO);
				pedidoHTNoPblVC.setCodArticulo(pedidoAdicionalE.getCodArticulo());
				pedidoHTNoPblVC.setGrupo1(pedidoAdicionalE.getGrupo1());
				pedidoHTNoPblVC.setGrupo2(pedidoAdicionalE.getGrupo2());
				pedidoHTNoPblVC.setGrupo3(pedidoAdicionalE.getGrupo3());
				PedidoHTNoPblLista listaNoGestionadosPblVC4 = this.pedidoHTNoPblService.consultaPedidosHTNoPbl(pedidoHTNoPblVC);
				listVC = this.addPedidosHTNoPblVC(listaNoGestionadosPblVC4, listVC);
				for(int i=0;vRelacionArticuloLista.size()>i;i++){
					pedidoHTNoPblVC.setCodArticulo(vRelacionArticuloLista.get(i));
					// Buscamos por cada una de las referencias los montajes adicionales 
					PedidoHTNoPblLista listaNoGestionadosPblVC4Asociados = this.pedidoHTNoPblService.consultaPedidosHTNoPbl(pedidoHTNoPblVC);
					listVC = this.addPedidosHTNoPblVC(listaNoGestionadosPblVC4Asociados, listVC);
				}
				// Clase pedido 5
				pedidoHTNoPblVC.setClasePedido(new Long(Constantes.CLASE_PEDIDO_VALIDAR_CANT_EXTRA_5));
				PedidoHTNoPblLista listaNoGestionadosPblVC5 = this.pedidoHTNoPblService.consultaPedidosHTNoPbl(pedidoHTNoPblVC);
				listVC = this.addPedidosHTNoPblVC(listaNoGestionadosPblVC5, listVC);
				for(int i=0;vRelacionArticuloLista.size()>i;i++){
					pedidoHTNoPblVC.setCodArticulo(vRelacionArticuloLista.get(i));
					// Buscamos por cada una de las referencias los montajes adicionales 
					PedidoHTNoPblLista listaNoGestionadosPblVC5Asociados = this.pedidoHTNoPblService.consultaPedidosHTNoPbl(pedidoHTNoPblVC);
					listVC = this.addPedidosHTNoPblVC(listaNoGestionadosPblVC5Asociados, listVC);
				}
				
				// Insertar tabla temporal T_PEDIDO_ADICIONAL
				// A partir de la lista obtenida tenemos que insertar en la 
				// tabla temporal los registros obtenidos,
				// borrando previamente los posibles registros almacenados.
				this.eliminarTablaSesion(session.getId(),new Long(Constantes.CLASE_PEDIDO_VALIDAR_CANT_EXTRA_4),pedidoAdicionalE.getCodCentro());
				this.eliminarTablaSesion(session.getId(),new Long(Constantes.CLASE_PEDIDO_VALIDAR_CANT_EXTRA_5),pedidoAdicionalE.getCodCentro());
				if (listVC != null && listVC.size()>0) {
					this.insertarTablaSesionVC(listVC,session.getId(),esCaprabo);
				}

				//--------------------------
				// End
				// Pestaña Validar Cantidades Extra
				//--------------------------
				
	
				//--------------------------
				// Pestaña Encargo Cliente
				// Begin
				//--------------------------
					List<PedidoAdicionalEC> listEC = new ArrayList<PedidoAdicionalEC>();
	
					PedidoAdicionalEC pedidoAdicionalEC = new PedidoAdicionalEC();
					pedidoAdicionalEC.setCodLoc(pedidoAdicionalE.getCodCentro());
					
	
					if (pedidoAdicionalE.getGrupo1()!=null){
						Formatter fmt = new Formatter();
						fmt.format("%04d",pedidoAdicionalE.getGrupo1());
						pedidoAdicionalEC.setArea(fmt.toString());
					}else{
						pedidoAdicionalEC.setArea(null);
					}
					if (pedidoAdicionalE.getGrupo2()!=null){
						Formatter fmt = new Formatter();
						fmt.format("%04d",pedidoAdicionalE.getGrupo2());
						pedidoAdicionalEC.setSeccion(fmt.toString());
					}else{
						pedidoAdicionalEC.setSeccion(null);
					}
					if (pedidoAdicionalE.getGrupo3()!=null){
						Formatter fmt = new Formatter();
						fmt.format("%04d",pedidoAdicionalE.getGrupo3());
						pedidoAdicionalEC.setCategoria(fmt.toString());
					}else{
						pedidoAdicionalEC.setCategoria(null);
					}
					//Puede que aquí lleguemos desde la pantalla de pedidos de los grid o desde un link de consulta datos referencia. En el caso de llegar de consulta
					//datos referencia, hay que obtener el código artículo desde el campo codArtículo y no codArticuloGrid, porque si no, llega a null y busca mal.
					pedidoAdicionalEC.setCodArtFormlog(pedidoAdicionalE.getCodArticuloGrid() != null ? pedidoAdicionalE.getCodArticuloGrid() : pedidoAdicionalE.getCodArticulo());
	
					
					// Obtenemos la lista de encargos de cliente del WS.
					listEC = this.pedidoAdicionalECService.findAll(pedidoAdicionalEC);
					for(int i=0;vRelacionArticuloLista.size()>i;i++){
						pedidoAdicionalEC.setCodArtFormlog(vRelacionArticuloLista.get(i));
						// Buscamos por cada una de las referencias los montajes adicionales 
						List<PedidoAdicionalEC> listMO8Asociados = this.pedidoAdicionalECService.findAll(pedidoAdicionalEC);
						listEC.addAll(listMO8Asociados);
					}
					
					// Insertar tabla temporal T_ENCARGOS_CLTE
					// A partir de la lista obtenida tenemos que insertar en la
					// tabla temporal los registros obtenidos,
					// borrando previamente los posibles registros almacenados.
					this.eliminarTablaSesionEC(session.getId(), pedidoAdicionalE.getCodCentro());
					if (listEC != null && listEC.size() > 0) {
						this.insertarTablaSesionEC(listEC, session.getId(),esCaprabo);
					}
				//--------------------------
				// End
				// Pestaña Encargo Cliente
				//--------------------------

			
			}
			
			//--------------------------
			// Pestaña Encargos
			// Begin
			// Contadores: clase pedido 1
			//--------------------------
			Long contadorE = new Long(0);
			
			// Clase pedido 1
			TPedidoAdicional tPedidoAdicional = new TPedidoAdicional();
			tPedidoAdicional.setIdSesion(session.getId());
			tPedidoAdicional.setListaFiltroClasePedido(Arrays.asList(new Long(Constantes.CLASE_PEDIDO_ENCARGO)));
			tPedidoAdicional.setCodCentro(pedidoAdicionalE.getCodCentro());
			tPedidoAdicional.setPantalla(Constantes.PANTALLA_LISTADOS);
			tPedidoAdicional.setMAC(pedidoAdicionalE.getMca());
			contadorE = this.tPedidoAdicionalService.findAllCount(tPedidoAdicional);

			resultado.setContadorEncargos(contadorE);
			//--------------------------
			// End
			// Pestaña Encargos
			//--------------------------
			
			//--------------------------
			// Pestaña Montaje Adicional
			// Begin
			// Contadores: suma de clase pedido 2 y clase pedido 7 (sólo cuando es MAC)
			//--------------------------
			Long contadorM = new Long(0);
			
			// Clase pedido 2 y 7
			tPedidoAdicional.setListaFiltroClasePedido(Arrays.asList(new Long(Constantes.CLASE_PEDIDO_MONTAJE), new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL)));
			Long contadorM2 = this.tPedidoAdicionalService.findAllCount(tPedidoAdicional);
			contadorM = contadorM + contadorM2;

			resultado.setContadorMontaje(contadorM);
			//--------------------------
			// End
			// Pestaña Montaje Adicional
			//--------------------------

			//--------------------------
			// Pestaña Montaje Oferta
			// Begin
			// Contadores: suma de clase pedido 3 y clase pedido 8 (sólo cuando es MAC)
			//--------------------------
			Long contadorMO = new Long(0);
			
			// Clase pedido 3 y 8
			tPedidoAdicional.setListaFiltroClasePedido(Arrays.asList(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL), new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA)));
			Long contadorMO3 = this.tPedidoAdicionalService.findAllCount(tPedidoAdicional);
			contadorMO = contadorMO + contadorMO3;

			resultado.setContadorMontajeOferta(contadorMO);
			//--------------------------
			// End
			// Pestaña Montaje Oferta
			//--------------------------
			
			//--------------------------
			// Pestaña Empujes
			// Begin
			// Contadores: suma de clase pedido 6 
			//--------------------------
			
			//Esta sección se comenta hasta activar la pestaña de empujes para no penalizar el resto de búsquedas
			
			tPedidoAdicional.setListaFiltroClasePedido(Arrays.asList(new Long(Constantes.CLASE_PEDIDO_EMPUJE)));
			tPedidoAdicional.setMAC(null);
			resultado.setContadorEmpuje(this.tPedidoAdicionalService.findAllCount(tPedidoAdicional));
			
			//--------------------------
			// End
			// Pestaña Empujes
			//--------------------------
			
			//--------------------------
			// Pestaña Validar Cantidades Extra
			// Begin
			// Contadores: suma de clase pedido 4 y 5 (sólo cuando es MAC)
			//--------------------------
			//Esta sección se comenta hasta activar la pestaña de Validar Cantidades Extra para no penalizar el resto de búsquedas
			Long contadorVC = new Long(0);
			
			// Clase pedido 4 y 5
			tPedidoAdicional.setListaFiltroClasePedido(Arrays.asList(new Long(Constantes.CLASE_PEDIDO_VALIDAR_CANT_EXTRA_4), new Long(Constantes.CLASE_PEDIDO_VALIDAR_CANT_EXTRA_5)));
			Long contadorVC45 = this.tPedidoAdicionalService.findAllCount(tPedidoAdicional);
			contadorVC = contadorVC + contadorVC45;

			resultado.setContadorValidarCantExtra(contadorVC);
			//--------------------------
			// End
			// Pestaña Validar Cantidades Extra
			//--------------------------

			//--------------------------
			// Pestaña Encargos de cliente
			// Begin
			//--------------------------
			Long contadorEC = new Long(0);
			
			TEncargosClte tEncargosClte = new TEncargosClte();
			tEncargosClte.setIddsesion(session.getId());
			tEncargosClte.setCentro(pedidoAdicionalE.getCodCentro());
			contadorEC = this.tEncargosClteService.findAllCount(tEncargosClte);

			resultado.setContadorEncargosCliente(contadorEC);
			//--------------------------
			// End
			// Pestaña Encargos de cliente
			//--------------------------

		
		} catch (Exception e) {
			logger.error("loadContadores="+e.toString());
			e.printStackTrace();
		}
			
		return resultado;
	}

	
	private void eliminarTablaSesionHistorico(){
		
		try {
			this.tPedidoAdicionalService.deleteHistorico();
			this.tEncargosClteService.deleteHistorico();
		} catch (Exception e) {
			logger.error("eliminarTablaSesionHistorico="+e.toString());
			e.printStackTrace();
		}
	}
	
	private void eliminarTablaSesion(String idSesion, Long clasePedido, Long codCentro){
		
		TPedidoAdicional registro = new TPedidoAdicional();
		
		registro.setIdSesion(idSesion);
		registro.setClasePedido(clasePedido);
		registro.setCodCentro(codCentro);
		registro.setPantalla(Constantes.PANTALLA_LISTADOS);
		
		try {
			this.tPedidoAdicionalService.delete(registro);
		} catch (Exception e) {
			logger.error("eliminarTablaSesion="+e.toString());
			e.printStackTrace();
		}
	}
	
	private void eliminarTablaSesionEC(String idSesion, Long codCentro){
		
		TEncargosClte registro = new TEncargosClte();
		
		registro.setIddsesion(idSesion);
		registro.setCentro(codCentro);
		
		try {
			this.tEncargosClteService.delete(registro);
		} catch (Exception e) {
			logger.error("eliminarTablaSesionEC="+e.toString());
			e.printStackTrace();
		}
	}
	
	
	private void insertarTablaSesionE(List<PedidoAdicionalE> list, String idSesion, boolean esCaprabo){

		//Nos recorremos la lista para generar la lista que debemos enviar al servicio para que inserte en la tabla temporal.
		List<TPedidoAdicional> listaTPedidoAdicional = new ArrayList<TPedidoAdicional>();
		
		//Nos recorremos la lista.
		PedidoAdicionalE registro = new PedidoAdicionalE();
		TPedidoAdicional nuevoRegistro = new TPedidoAdicional();
		
		try {
			for (int i =0;i<list.size();i++)
			{
				nuevoRegistro = new TPedidoAdicional();
				
				registro = (PedidoAdicionalE)list.get(i);
				
				nuevoRegistro.setIdSesion(idSesion);
				nuevoRegistro.setCodCentro((registro.getCodCentro() != null && !("".equals(registro.getCodCentro().toString())))?new Long(registro.getCodCentro().toString()):null);
				nuevoRegistro.setIdentificador((registro.getIdentificador() != null && !("".equals(registro.getIdentificador().toString())))?new Long(registro.getIdentificador().toString()):null);
				
				nuevoRegistro.setCodArticulo((registro.getCodArticulo() != null && !("".equals(registro.getCodArticulo().toString())))?new Long(registro.getCodArticulo().toString()):null);
				nuevoRegistro.setDescriptionArt((registro.getDescriptionArt() != null && !("".equals(registro.getDescriptionArt())))?registro.getDescriptionArt():null);
				if (esCaprabo) {
					nuevoRegistro.setCodArticuloGrid(utilidadesCapraboService.obtenerCodigoCaprabo(registro.getCodCentro(), registro.getCodArticulo()));
					nuevoRegistro.setDescriptionArtGrid(utilidadesCapraboService.obtenerDescArtCaprabo(nuevoRegistro.getCodArticuloGrid()));
				} else {
					nuevoRegistro.setCodArticuloGrid((registro.getCodArticulo() != null && !("".equals(registro.getCodArticulo().toString())))?new Long(registro.getCodArticulo().toString()):null);
					nuevoRegistro.setDescriptionArtGrid((registro.getDescriptionArt() != null && !("".equals(registro.getDescriptionArt())))?registro.getDescriptionArt():null);
				}
				nuevoRegistro.setUniCajaServ((registro.getUniCajaServ() != null && !("".equals(registro.getUniCajaServ().toString())))?new Double(registro.getUniCajaServ().toString()):null);
				nuevoRegistro.setUsuario((registro.getUsuario() != null && !("".equals(registro.getUsuario())))?registro.getUsuario():null);
				nuevoRegistro.setPerfil((registro.getPerfil() != null && !("".equals(registro.getPerfil())))?registro.getPerfil():null);
				nuevoRegistro.setAgrupacion((registro.getAgrupacion() != null && !("".equals(registro.getAgrupacion())))?registro.getAgrupacion():null);
				nuevoRegistro.setTipoAprovisionamiento((registro.getTipoAprovisionamiento() != null && !("".equals(registro.getTipoAprovisionamiento())))?registro.getTipoAprovisionamiento():null);
				nuevoRegistro.setCajasPedidas((registro.getUnidadesPedidas() != null && !("".equals(registro.getUnidadesPedidas().toString())))?new Double(registro.getUnidadesPedidas().toString()):null);
				nuevoRegistro.setFecEntrega((registro.getFecEntrega() != null && !("".equals(registro.getFecEntrega())))?registro.getFecEntrega():null);
				nuevoRegistro.setCajas((registro.isCajas()?"S":"N"));
				nuevoRegistro.setExcluir((registro.isExcluir()?"S":"N"));
				nuevoRegistro.setModificable((registro.getModificable() != null && !("".equals(registro.getModificable())))?registro.getModificable():null);
				nuevoRegistro.setClasePedido((registro.getClasePedido() != null && !("".equals(registro.getClasePedido())))?registro.getClasePedido():null);
				nuevoRegistro.setTratamiento(registro.getTratamiento());
				nuevoRegistro.setFechaHasta((registro.getFechaHasta() != null && !("".equals(registro.getFechaHasta())))?registro.getFechaHasta():null);
				nuevoRegistro.setPantalla(Constantes.PANTALLA_LISTADOS);
				nuevoRegistro.setEstado(registro.getEstado());
				nuevoRegistro.setIdentificadorSIA((registro.getIdentificadorSIA() != null && !("".equals(registro.getIdentificadorSIA().toString())))?new Long(registro.getIdentificadorSIA().toString()):null);
				listaTPedidoAdicional.add(nuevoRegistro);
			}
		
	
			this.tPedidoAdicionalService.insertAll(listaTPedidoAdicional);
		} catch (Exception e) {
			logger.error("insertarTablaSesionE="+e.toString());
			e.printStackTrace();
		}
			
	}

	private void insertarTablaSesionM(List<PedidoAdicionalM> list, String idSesion, boolean esCaprabo){

		//Nos recorremos la lista para generar la lista que debemos enviar al servicio para que inserte en la tabla temporal.
		List<TPedidoAdicional> listaTPedidoAdicional = new ArrayList<TPedidoAdicional>();
		
		//Nos recorremos la lista.
		PedidoAdicionalM registro = new PedidoAdicionalM();
		TPedidoAdicional nuevoRegistro = new TPedidoAdicional();
		try {
			for (int i =0;i<list.size();i++)
			{
				nuevoRegistro = new TPedidoAdicional();
				
				registro = (PedidoAdicionalM)list.get(i);
				
				nuevoRegistro.setIdSesion(idSesion);
				nuevoRegistro.setCodCentro((registro.getCodCentro() != null && !("".equals(registro.getCodCentro().toString())))?new Long(registro.getCodCentro().toString()):null);
				nuevoRegistro.setIdentificador((registro.getIdentificador() != null && !("".equals(registro.getIdentificador().toString())))?new Long(registro.getIdentificador().toString()):null);
				
				nuevoRegistro.setCodArticulo((registro.getCodArticulo() != null && !("".equals(registro.getCodArticulo().toString())))?new Long(registro.getCodArticulo().toString()):null);
				nuevoRegistro.setDescriptionArt((registro.getDescriptionArt() != null && !("".equals(registro.getDescriptionArt())))?registro.getDescriptionArt():null);
				if (esCaprabo) {
					nuevoRegistro.setCodArticuloGrid(utilidadesCapraboService.obtenerCodigoCaprabo(registro.getCodCentro(), registro.getCodArticulo()));
					nuevoRegistro.setDescriptionArtGrid(utilidadesCapraboService.obtenerDescArtCaprabo(nuevoRegistro.getCodArticuloGrid()));
				} else {
					nuevoRegistro.setCodArticuloGrid((registro.getCodArticulo() != null && !("".equals(registro.getCodArticulo().toString())))?new Long(registro.getCodArticulo().toString()):null);
					nuevoRegistro.setDescriptionArtGrid((registro.getDescriptionArt() != null && !("".equals(registro.getDescriptionArt())))?registro.getDescriptionArt():null);
				}
				
				
				nuevoRegistro.setUniCajaServ((registro.getUniCajaServ() != null && !("".equals(registro.getUniCajaServ().toString())))?new Double(registro.getUniCajaServ().toString()):null);
				nuevoRegistro.setUsuario((registro.getUsuario() != null && !("".equals(registro.getUsuario())))?registro.getUsuario():null);
				nuevoRegistro.setPerfil((registro.getPerfil() != null && !("".equals(registro.getPerfil())))?registro.getPerfil():null);
				nuevoRegistro.setAgrupacion((registro.getAgrupacion() != null && !("".equals(registro.getAgrupacion())))?registro.getAgrupacion():null);
				nuevoRegistro.setTipoAprovisionamiento((registro.getTipoAprovisionamiento() != null && !("".equals(registro.getTipoAprovisionamiento())))?registro.getTipoAprovisionamiento():null);
				nuevoRegistro.setBorrable((registro.getBorrable() != null && !("".equals(registro.getBorrable())))?registro.getBorrable():null);
				nuevoRegistro.setModificable((registro.getModificable() != null && !("".equals(registro.getModificable())))?registro.getModificable():null);
				nuevoRegistro.setModificableIndiv((registro.getModificableIndiv() != null && !("".equals(registro.getModificableIndiv())))?registro.getModificableIndiv():null);
				nuevoRegistro.setFechaInicio((registro.getFechaInicio() != null && !("".equals(registro.getFechaInicio())))?registro.getFechaInicio():null);
				nuevoRegistro.setFechaFin((registro.getFechaFin() != null && !("".equals(registro.getFechaFin())))?registro.getFechaFin():null);
				nuevoRegistro.setClasePedido((registro.getClasePedido() != null && !("".equals(registro.getClasePedido())))?registro.getClasePedido():null);
				nuevoRegistro.setFecha2((registro.getFecha2() != null && !("".equals(registro.getFecha2())))?registro.getFecha2():null);
				nuevoRegistro.setFecha3((registro.getFecha3() != null && !("".equals(registro.getFecha3())))?registro.getFecha3():null);
				nuevoRegistro.setFecha4((registro.getFecha4() != null && !("".equals(registro.getFecha4())))?registro.getFecha4():null);
				nuevoRegistro.setCapMax((registro.getCapMax() != null && !("".equals(registro.getCapMax().toString())))?new Double(registro.getCapMax().toString()):null);
				nuevoRegistro.setCapMin((registro.getCapMin() != null && !("".equals(registro.getCapMin().toString())))?new Double(registro.getCapMin().toString()):null);
				nuevoRegistro.setCantidad1((registro.getCantidad1() != null && !("".equals(registro.getCantidad1().toString())))?new Double(registro.getCantidad1().toString()):null);
				nuevoRegistro.setCantidad2((registro.getCantidad2() != null && !("".equals(registro.getCantidad2().toString())))?new Double(registro.getCantidad2().toString()):null);
				nuevoRegistro.setCantidad3((registro.getCantidad3() != null && !("".equals(registro.getCantidad3().toString())))?new Double(registro.getCantidad3().toString()):null);
				nuevoRegistro.setTipoPedido((registro.getTipoPedido() != null && !("".equals(registro.getTipoPedido())))?registro.getTipoPedido():null);
				nuevoRegistro.setExcluir((registro.isExcluir()?"S":"N"));
				nuevoRegistro.setPantalla(Constantes.PANTALLA_LISTADOS);
				nuevoRegistro.setEsPlanograma((registro.getEsPlanograma() != null && !("".equals(registro.getEsPlanograma())))?registro.getEsPlanograma():null);
				nuevoRegistro.setNoGestionaPbl((registro.getNoGestionaPbl() != null && !("".equals(registro.getNoGestionaPbl())))?registro.getNoGestionaPbl():null);
				nuevoRegistro.setCantMax(registro.getCantMax());
				nuevoRegistro.setCantMin(registro.getCantMin());
				nuevoRegistro.setDescOferta(registro.getDescOferta());
				nuevoRegistro.setFecha5((registro.getFecha5() != null && !("".equals(registro.getFecha5())))?registro.getFecha5():null);
				nuevoRegistro.setFechaPilada((registro.getFechaPilada() != null && !("".equals(registro.getFechaPilada())))?registro.getFechaPilada():null);
				nuevoRegistro.setCantidad4((registro.getCantidad4() != null && !("".equals(registro.getCantidad4().toString())))?new Double(registro.getCantidad4().toString()):null);
				nuevoRegistro.setCantidad5((registro.getCantidad5() != null && !("".equals(registro.getCantidad5().toString())))?new Double(registro.getCantidad5().toString()):null);
				nuevoRegistro.setTratamiento(registro.getTratamiento());
				nuevoRegistro.setFechaHasta((registro.getFechaHasta() != null && !("".equals(registro.getFechaHasta())))?registro.getFechaHasta():null);
				nuevoRegistro.setEstado(registro.getEstado());
				nuevoRegistro.setDescPeriodo((registro.getDescPeriodo() != null && !("".equals(registro.getDescPeriodo())))?registro.getDescPeriodo():null);
				nuevoRegistro.setEspacioPromo((registro.getEspacioPromo() != null && !("".equals(registro.getEspacioPromo())))?registro.getEspacioPromo():null);
				nuevoRegistro.setIdentificadorSIA((registro.getIdentificadorSIA() != null && !("".equals(registro.getIdentificadorSIA().toString())))?new Long(registro.getIdentificadorSIA().toString()):null);
				
				listaTPedidoAdicional.add(nuevoRegistro);
			}
		
	
			this.tPedidoAdicionalService.insertAll(listaTPedidoAdicional);
		} catch (Exception e) {
			logger.error("insertarTablaSesionM="+e.toString());
			e.printStackTrace();
		}
			
	}

	private void insertarTablaSesionMO(List<PedidoAdicionalMO> list, String idSesion, boolean esCaprabo){

		//Nos recorremos la lista para generar la lista que debemos enviar al servicio para que inserte en la tabla temporal.
		List<TPedidoAdicional> listaTPedidoAdicional = new ArrayList<TPedidoAdicional>();
		
		//Nos recorremos la lista.
		PedidoAdicionalMO registro = new PedidoAdicionalMO();
		TPedidoAdicional nuevoRegistro = new TPedidoAdicional();
		try {
			for (int i =0;i<list.size();i++)
			{
				nuevoRegistro = new TPedidoAdicional();
				
				registro = (PedidoAdicionalMO)list.get(i);
				
				nuevoRegistro.setIdSesion(idSesion);
				nuevoRegistro.setCodCentro((registro.getCodCentro() != null && !("".equals(registro.getCodCentro().toString())))?new Long(registro.getCodCentro().toString()):null);
				nuevoRegistro.setIdentificador((registro.getIdentificador() != null && !("".equals(registro.getIdentificador().toString())))?new Long(registro.getIdentificador().toString()):null);
				
				nuevoRegistro.setCodArticulo((registro.getCodArticulo() != null && !("".equals(registro.getCodArticulo().toString())))?new Long(registro.getCodArticulo().toString()):null);
				nuevoRegistro.setDescriptionArt((registro.getDescriptionArt() != null && !("".equals(registro.getDescriptionArt())))?registro.getDescriptionArt():null);
				if (esCaprabo) {
					nuevoRegistro.setCodArticuloGrid(utilidadesCapraboService.obtenerCodigoCaprabo(registro.getCodCentro(), registro.getCodArticulo()));
					nuevoRegistro.setDescriptionArtGrid(utilidadesCapraboService.obtenerDescArtCaprabo(nuevoRegistro.getCodArticuloGrid()));
				} else {
					nuevoRegistro.setCodArticuloGrid((registro.getCodArticulo() != null && !("".equals(registro.getCodArticulo().toString())))?new Long(registro.getCodArticulo().toString()):null);
					nuevoRegistro.setDescriptionArtGrid((registro.getDescriptionArt() != null && !("".equals(registro.getDescriptionArt())))?registro.getDescriptionArt():null);
				}
				
				nuevoRegistro.setUniCajaServ((registro.getUniCajaServ() != null && !("".equals(registro.getUniCajaServ().toString())))?new Double(registro.getUniCajaServ().toString()):null);
				nuevoRegistro.setUsuario((registro.getUsuario() != null && !("".equals(registro.getUsuario())))?registro.getUsuario():null);
				nuevoRegistro.setPerfil((registro.getPerfil() != null && !("".equals(registro.getPerfil())))?registro.getPerfil():null);
				nuevoRegistro.setAgrupacion((registro.getAgrupacion() != null && !("".equals(registro.getAgrupacion())))?registro.getAgrupacion():null);
				nuevoRegistro.setOferta((registro.getOferta() != null && !("".equals(registro.getOferta())))?registro.getOferta():null);
				nuevoRegistro.setTipoAprovisionamiento((registro.getTipoAprovisionamiento() != null && !("".equals(registro.getTipoAprovisionamiento())))?registro.getTipoAprovisionamiento():null);
				nuevoRegistro.setBorrable((registro.getBorrable() != null && !("".equals(registro.getBorrable())))?registro.getBorrable():null);
				nuevoRegistro.setModificable((registro.getModificable() != null && !("".equals(registro.getModificable())))?registro.getModificable():null);
				nuevoRegistro.setModificableIndiv((registro.getModificableIndiv() != null && !("".equals(registro.getModificableIndiv())))?registro.getModificableIndiv():null);
				nuevoRegistro.setFechaInicio((registro.getFechaInicio() != null && !("".equals(registro.getFechaInicio())))?registro.getFechaInicio():null);
				nuevoRegistro.setFechaFin((registro.getFechaFin() != null && !("".equals(registro.getFechaFin())))?registro.getFechaFin():null);
				nuevoRegistro.setClasePedido((registro.getClasePedido() != null && !("".equals(registro.getClasePedido())))?registro.getClasePedido():null);
				nuevoRegistro.setFecha2((registro.getFecha2() != null && !("".equals(registro.getFecha2())))?registro.getFecha2():null);
				nuevoRegistro.setFecha3((registro.getFecha3() != null && !("".equals(registro.getFecha3())))?registro.getFecha3():null);
				nuevoRegistro.setFecha4((registro.getFecha4() != null && !("".equals(registro.getFecha4())))?registro.getFecha4():null);
				nuevoRegistro.setCapMax((registro.getCapMax() != null && !("".equals(registro.getCapMax().toString())))?new Double(registro.getCapMax().toString()):null);
				nuevoRegistro.setCapMin((registro.getCapMin() != null && !("".equals(registro.getCapMin().toString())))?new Double(registro.getCapMin().toString()):null);
				nuevoRegistro.setCantidad1((registro.getCantidad1() != null && !("".equals(registro.getCantidad1().toString())))?new Double(registro.getCantidad1().toString()):null);
				nuevoRegistro.setCantidad2((registro.getCantidad2() != null && !("".equals(registro.getCantidad2().toString())))?new Double(registro.getCantidad2().toString()):null);
				nuevoRegistro.setCantidad3((registro.getCantidad3() != null && !("".equals(registro.getCantidad3().toString())))?new Double(registro.getCantidad3().toString()):null);
				nuevoRegistro.setTipoPedido((registro.getTipoPedido() != null && !("".equals(registro.getTipoPedido())))?registro.getTipoPedido():null);
				nuevoRegistro.setPantalla(Constantes.PANTALLA_LISTADOS);
				nuevoRegistro.setEsPlanograma((registro.getEsPlanograma() != null && !("".equals(registro.getEsPlanograma())))?registro.getEsPlanograma():null);
				nuevoRegistro.setNoGestionaPbl((registro.getNoGestionaPbl() != null && !("".equals(registro.getNoGestionaPbl())))?registro.getNoGestionaPbl():null);
				nuevoRegistro.setExcluir(registro.isExcluir()?"S":"N");
				nuevoRegistro.setCantMax(registro.getCantMax());
				nuevoRegistro.setCantMin(registro.getCantMin());
				nuevoRegistro.setDescOferta(registro.getDescOferta());
				nuevoRegistro.setFecha5((registro.getFecha5() != null && !("".equals(registro.getFecha5())))?registro.getFecha5():null);
				nuevoRegistro.setFechaPilada((registro.getFechaPilada() != null && !("".equals(registro.getFechaPilada())))?registro.getFechaPilada():null);
				nuevoRegistro.setCantidad4((registro.getCantidad4() != null && !("".equals(registro.getCantidad4().toString())))?new Double(registro.getCantidad4().toString()):null);
				nuevoRegistro.setCantidad5((registro.getCantidad5() != null && !("".equals(registro.getCantidad5().toString())))?new Double(registro.getCantidad5().toString()):null);
				nuevoRegistro.setTratamiento(registro.getTratamiento());
				nuevoRegistro.setFechaHasta((registro.getFechaHasta() != null && !("".equals(registro.getFechaHasta())))?registro.getFechaHasta():null);
				nuevoRegistro.setEstado(registro.getEstado());
				nuevoRegistro.setDescPeriodo((registro.getDescPeriodo() != null && !("".equals(registro.getDescPeriodo())))?registro.getDescPeriodo():null);
				nuevoRegistro.setEspacioPromo((registro.getEspacioPromo() != null && !("".equals(registro.getEspacioPromo())))?registro.getEspacioPromo():null);
				nuevoRegistro.setIdentificadorSIA((registro.getIdentificadorSIA() != null && !("".equals(registro.getIdentificadorSIA().toString())))?new Long(registro.getIdentificadorSIA().toString()):null);		
				listaTPedidoAdicional.add(nuevoRegistro);
			}
		
		
			this.tPedidoAdicionalService.insertAll(listaTPedidoAdicional);
		} catch (Exception e) {
			logger.error("insertarTablaSesionMO="+e.toString());
			e.printStackTrace();
		}
			
	}

	private void insertarTablaSesionEM(List<PedidoAdicionalEM> list, String idSesion, boolean esCaprabo){

		//Nos recorremos la lista para generar la lista que debemos enviar al servicio para que inserte en la tabla temporal.
		List<TPedidoAdicional> listaTPedidoAdicional = new ArrayList<TPedidoAdicional>();
		
		//Nos recorremos la lista.
		PedidoAdicionalEM registro = new PedidoAdicionalEM();
		
		try {
			for (int i =0;i<list.size();i++)
			{
				TPedidoAdicional nuevoRegistro = new TPedidoAdicional();
				
				registro = (PedidoAdicionalEM)list.get(i);
				
				nuevoRegistro.setIdSesion(idSesion);
				nuevoRegistro.setCodCentro((registro.getCodCentro() != null && !("".equals(registro.getCodCentro().toString())))?new Long(registro.getCodCentro().toString()):null);
				nuevoRegistro.setIdentificador((registro.getIdentificador() != null && !("".equals(registro.getIdentificador().toString())))?new Long(registro.getIdentificador().toString()):null);
				
				nuevoRegistro.setCodArticulo((registro.getCodArticulo() != null && !("".equals(registro.getCodArticulo().toString())))?new Long(registro.getCodArticulo().toString()):null);
				nuevoRegistro.setDescriptionArt((registro.getDescriptionArt() != null && !("".equals(registro.getDescriptionArt())))?registro.getDescriptionArt():null);
				if (esCaprabo) {
					nuevoRegistro.setCodArticuloGrid(utilidadesCapraboService.obtenerCodigoCaprabo(registro.getCodCentro(), registro.getCodArticulo()));
					nuevoRegistro.setDescriptionArtGrid(utilidadesCapraboService.obtenerDescArtCaprabo(nuevoRegistro.getCodArticuloGrid()));
				} else {
					nuevoRegistro.setCodArticuloGrid((registro.getCodArticulo() != null && !("".equals(registro.getCodArticulo().toString())))?new Long(registro.getCodArticulo().toString()):null);
					nuevoRegistro.setDescriptionArtGrid((registro.getDescriptionArt() != null && !("".equals(registro.getDescriptionArt())))?registro.getDescriptionArt():null);
				}
				
				nuevoRegistro.setUniCajaServ((registro.getUniCajaServ() != null && !("".equals(registro.getUniCajaServ().toString())))?new Double(registro.getUniCajaServ().toString()):null);
				nuevoRegistro.setUsuario((registro.getUsuario() != null && !("".equals(registro.getUsuario())))?registro.getUsuario():null);
				nuevoRegistro.setPerfil((registro.getPerfil() != null && !("".equals(registro.getPerfil())))?registro.getPerfil():null);
				nuevoRegistro.setAgrupacion((registro.getAgrupacion() != null && !("".equals(registro.getAgrupacion())))?registro.getAgrupacion():null);
				nuevoRegistro.setOferta((registro.getOferta() != null && !("".equals(registro.getOferta())))?registro.getOferta():null);
				nuevoRegistro.setTipoAprovisionamiento((registro.getTipoAprovisionamiento() != null && !("".equals(registro.getTipoAprovisionamiento())))?registro.getTipoAprovisionamiento():null);
				nuevoRegistro.setBorrable((registro.getBorrable() != null && !("".equals(registro.getBorrable())))?registro.getBorrable():null);
				nuevoRegistro.setModificable((registro.getModificable() != null && !("".equals(registro.getModificable())))?registro.getModificable():null);
				nuevoRegistro.setModificableIndiv((registro.getModificableIndiv() != null && !("".equals(registro.getModificableIndiv())))?registro.getModificableIndiv():null);
				nuevoRegistro.setFechaInicio((registro.getFechaInicio() != null && !("".equals(registro.getFechaInicio())))?registro.getFechaInicio():null);
				nuevoRegistro.setFechaFin((registro.getFechaFin() != null && !("".equals(registro.getFechaFin())))?registro.getFechaFin():null);
				nuevoRegistro.setClasePedido((registro.getClasePedido() != null && !("".equals(registro.getClasePedido())))?registro.getClasePedido():null);
				nuevoRegistro.setFecha2((registro.getFecha2() != null && !("".equals(registro.getFecha2())))?registro.getFecha2():null);
				nuevoRegistro.setFecha3((registro.getFecha3() != null && !("".equals(registro.getFecha3())))?registro.getFecha3():null);
				nuevoRegistro.setFecha4((registro.getFecha4() != null && !("".equals(registro.getFecha4())))?registro.getFecha4():null);
				nuevoRegistro.setCapMax((registro.getCapMax() != null && !("".equals(registro.getCapMax().toString())))?new Double(registro.getCapMax().toString()):null);
				nuevoRegistro.setCapMin((registro.getCapMin() != null && !("".equals(registro.getCapMin().toString())))?new Double(registro.getCapMin().toString()):null);
				nuevoRegistro.setCantidad1((registro.getCantidad1() != null && !("".equals(registro.getCantidad1().toString())))?new Double(registro.getCantidad1().toString()):null);
				nuevoRegistro.setCantidad2((registro.getCantidad2() != null && !("".equals(registro.getCantidad2().toString())))?new Double(registro.getCantidad2().toString()):null);
				nuevoRegistro.setCantidad3((registro.getCantidad3() != null && !("".equals(registro.getCantidad3().toString())))?new Double(registro.getCantidad3().toString()):null);
				nuevoRegistro.setTipoPedido((registro.getTipoPedido() != null && !("".equals(registro.getTipoPedido())))?registro.getTipoPedido():null);
				nuevoRegistro.setPantalla(Constantes.PANTALLA_LISTADOS);
				nuevoRegistro.setEsPlanograma((registro.getEsPlanograma() != null && !("".equals(registro.getEsPlanograma())))?registro.getEsPlanograma():null);
				nuevoRegistro.setNoGestionaPbl((registro.getNoGestionaPbl() != null && !("".equals(registro.getNoGestionaPbl())))?registro.getNoGestionaPbl():null);
				nuevoRegistro.setExcluir(registro.isExcluir()?"S":"N");
				nuevoRegistro.setCantMax(registro.getCantMax());
				nuevoRegistro.setCantMin(registro.getCantMin());
				nuevoRegistro.setDescOferta(registro.getDescOferta());
				nuevoRegistro.setFecha5((registro.getFecha5() != null && !("".equals(registro.getFecha5())))?registro.getFecha5():null);
				nuevoRegistro.setFechaPilada((registro.getFechaPilada() != null && !("".equals(registro.getFechaPilada())))?registro.getFechaPilada():null);
				nuevoRegistro.setCantidad4((registro.getCantidad4() != null && !("".equals(registro.getCantidad4().toString())))?new Double(registro.getCantidad4().toString()):null);
				nuevoRegistro.setCantidad5((registro.getCantidad5() != null && !("".equals(registro.getCantidad5().toString())))?new Double(registro.getCantidad5().toString()):null);
				nuevoRegistro.setTratamiento(registro.getTratamiento());
				nuevoRegistro.setFechaHasta((registro.getFechaHasta() != null && !("".equals(registro.getFechaHasta())))?registro.getFechaHasta():null);
				nuevoRegistro.setEstado(registro.getEstado());
				nuevoRegistro.setIdentificadorSIA((registro.getIdentificadorSIA() != null && !("".equals(registro.getIdentificadorSIA().toString())))?new Long(registro.getIdentificadorSIA().toString()):null);
				listaTPedidoAdicional.add(nuevoRegistro);
			}
			
		
			this.tPedidoAdicionalService.insertAll(listaTPedidoAdicional);
		} catch (Exception e) {
			logger.error("insertarTablaSesionEM="+e.toString());
			e.printStackTrace();
		}
			
	}

	private void insertarTablaSesionVC(List<PedidoAdicionalVC> list, String idSesion, boolean esCaprabo){

		//Nos recorremos la lista para generar la lista que debemos enviar al servicio para que inserte en la tabla temporal.
		List<TPedidoAdicional> listaTPedidoAdicional = new ArrayList<TPedidoAdicional>();
		
		//Nos recorremos la lista.
		PedidoAdicionalVC registro = new PedidoAdicionalVC();
		TPedidoAdicional nuevoRegistro = new TPedidoAdicional();
		try {
			for (int i =0;i<list.size();i++)
			{
				nuevoRegistro = new TPedidoAdicional();
				
				registro = (PedidoAdicionalVC)list.get(i);
				
				nuevoRegistro.setIdSesion(idSesion);
				nuevoRegistro.setCodCentro((registro.getCodCentro() != null && !("".equals(registro.getCodCentro().toString())))?new Long(registro.getCodCentro().toString()):null);
				nuevoRegistro.setIdentificador((registro.getIdentificador() != null && !("".equals(registro.getIdentificador().toString())))?new Long(registro.getIdentificador().toString()):null);
				
				nuevoRegistro.setCodArticulo((registro.getCodArticulo() != null && !("".equals(registro.getCodArticulo().toString())))?new Long(registro.getCodArticulo().toString()):null);
				nuevoRegistro.setDescriptionArt((registro.getDescriptionArt() != null && !("".equals(registro.getDescriptionArt())))?registro.getDescriptionArt():null);
				if (esCaprabo) {
					nuevoRegistro.setCodArticuloGrid(utilidadesCapraboService.obtenerCodigoCaprabo(registro.getCodCentro(), registro.getCodArticulo()));
					nuevoRegistro.setDescriptionArtGrid(utilidadesCapraboService.obtenerDescArtCaprabo(nuevoRegistro.getCodArticuloGrid()));
				} else {
					nuevoRegistro.setCodArticuloGrid((registro.getCodArticulo() != null && !("".equals(registro.getCodArticulo().toString())))?new Long(registro.getCodArticulo().toString()):null);
					nuevoRegistro.setDescriptionArtGrid((registro.getDescriptionArt() != null && !("".equals(registro.getDescriptionArt())))?registro.getDescriptionArt():null);
				}
				
				nuevoRegistro.setUniCajaServ((registro.getUniCajaServ() != null && !("".equals(registro.getUniCajaServ().toString())))?new Double(registro.getUniCajaServ().toString()):null);
				nuevoRegistro.setUsuario((registro.getUsuario() != null && !("".equals(registro.getUsuario())))?registro.getUsuario():null);
				nuevoRegistro.setPerfil((registro.getPerfil() != null && !("".equals(registro.getPerfil())))?registro.getPerfil():null);
				nuevoRegistro.setAgrupacion((registro.getAgrupacion() != null && !("".equals(registro.getAgrupacion())))?registro.getAgrupacion():null);
				nuevoRegistro.setOferta((registro.getOferta() != null && !("".equals(registro.getOferta())))?registro.getOferta():null);
				nuevoRegistro.setTipoAprovisionamiento((registro.getTipoAprovisionamiento() != null && !("".equals(registro.getTipoAprovisionamiento())))?registro.getTipoAprovisionamiento():null);
				nuevoRegistro.setBorrable((registro.getBorrable() != null && !("".equals(registro.getBorrable())))?registro.getBorrable():null);
				nuevoRegistro.setModificable((registro.getModificable() != null && !("".equals(registro.getModificable())))?registro.getModificable():null);
				nuevoRegistro.setModificableIndiv((registro.getModificableIndiv() != null && !("".equals(registro.getModificableIndiv())))?registro.getModificableIndiv():null);
				nuevoRegistro.setFechaInicio((registro.getFechaInicio() != null && !("".equals(registro.getFechaInicio())))?registro.getFechaInicio():null);
				nuevoRegistro.setFechaFin((registro.getFechaFin() != null && !("".equals(registro.getFechaFin())))?registro.getFechaFin():null);
				nuevoRegistro.setClasePedido((registro.getClasePedido() != null && !("".equals(registro.getClasePedido())))?registro.getClasePedido():null);
				nuevoRegistro.setFecha2((registro.getFecha2() != null && !("".equals(registro.getFecha2())))?registro.getFecha2():null);
				nuevoRegistro.setFecha3((registro.getFecha3() != null && !("".equals(registro.getFecha3())))?registro.getFecha3():null);
				nuevoRegistro.setFecha4((registro.getFecha4() != null && !("".equals(registro.getFecha4())))?registro.getFecha4():null);
				nuevoRegistro.setCapMax((registro.getCapMax() != null && !("".equals(registro.getCapMax().toString())))?new Double(registro.getCapMax().toString()):null);
				nuevoRegistro.setCapMin((registro.getCapMin() != null && !("".equals(registro.getCapMin().toString())))?new Double(registro.getCapMin().toString()):null);
				nuevoRegistro.setCantidad1((registro.getCantidad1() != null && !("".equals(registro.getCantidad1().toString())))?new Double(registro.getCantidad1().toString()):null);
				nuevoRegistro.setCantidad2((registro.getCantidad2() != null && !("".equals(registro.getCantidad2().toString())))?new Double(registro.getCantidad2().toString()):null);
				nuevoRegistro.setCantidad3((registro.getCantidad3() != null && !("".equals(registro.getCantidad3().toString())))?new Double(registro.getCantidad3().toString()):null);
				nuevoRegistro.setTipoPedido((registro.getTipoPedido() != null && !("".equals(registro.getTipoPedido())))?registro.getTipoPedido():null);
				nuevoRegistro.setPantalla(Constantes.PANTALLA_LISTADOS);
				nuevoRegistro.setEsPlanograma((registro.getEsPlanograma() != null && !("".equals(registro.getEsPlanograma())))?registro.getEsPlanograma():null);
				nuevoRegistro.setNoGestionaPbl((registro.getNoGestionaPbl() != null && !("".equals(registro.getNoGestionaPbl())))?registro.getNoGestionaPbl():null);
				nuevoRegistro.setExcluir(registro.isExcluir()?"S":"N");
				nuevoRegistro.setCajas(registro.isCajas()?"S":"N");
				nuevoRegistro.setCantMin((registro.getCantMin() != null && !("".equals(registro.getCantMin().toString())))?new Double(registro.getCantMin().toString()):null);
				nuevoRegistro.setCantMax((registro.getCantMax() != null && !("".equals(registro.getCantMax().toString())))?new Double(registro.getCantMax().toString()):null);
				nuevoRegistro.setDescOferta(registro.getDescOferta());
				nuevoRegistro.setFecha5((registro.getFecha5() != null && !("".equals(registro.getFecha5())))?registro.getFecha5():null);
				nuevoRegistro.setFechaPilada((registro.getFechaPilada() != null && !("".equals(registro.getFechaPilada())))?registro.getFechaPilada():null);
				nuevoRegistro.setCantidad4((registro.getCantidad4() != null && !("".equals(registro.getCantidad4().toString())))?new Double(registro.getCantidad4().toString()):null);
				nuevoRegistro.setCantidad5((registro.getCantidad5() != null && !("".equals(registro.getCantidad5().toString())))?new Double(registro.getCantidad5().toString()):null);
				nuevoRegistro.setTratamiento(registro.getTratamiento());
				nuevoRegistro.setFechaHasta((registro.getFechaHasta() != null && !("".equals(registro.getFechaHasta())))?registro.getFechaHasta():null);
				nuevoRegistro.setEstado(registro.getEstado());
				nuevoRegistro.setIdentificadorSIA((registro.getIdentificadorSIA() != null && !("".equals(registro.getIdentificadorSIA().toString())))?new Long(registro.getIdentificadorSIA().toString()):null);
				
				listaTPedidoAdicional.add(nuevoRegistro);
			}
		
		
			this.tPedidoAdicionalService.insertAll(listaTPedidoAdicional);
		} catch (Exception e) {
			logger.error("insertarTablaSesionVC="+e.toString());
			e.printStackTrace();
		}
			
	}

	private void insertarTablaSesionEC(List<PedidoAdicionalEC> list, String idSesion, boolean esCaprabo){

		//Nos recorremos la lista para generar la lista que debemos enviar al servicio para que inserte en la tabla temporal.
		List<TEncargosClte> listaTEncargosClte = new ArrayList<TEncargosClte>();
		
		//Nos recorremos la lista.
		PedidoAdicionalEC registro = new PedidoAdicionalEC();
		TEncargosClte nuevoRegistro = new TEncargosClte();
		try {
			for (int i =0;i<list.size();i++)
			{
				nuevoRegistro = new TEncargosClte();
				
				registro = (PedidoAdicionalEC)list.get(i);
				
				nuevoRegistro.setLocalizador(registro.getLocalizador() != null && !("".equals(registro.getLocalizador().toString()))?new Long(registro.getLocalizador().toString()):null);
				nuevoRegistro.setCentro((registro.getCodLoc() != null && !("".equals(registro.getCodLoc().toString())))?new Long(registro.getCodLoc().toString()):null);
				nuevoRegistro.setArea(registro.getArea());		
				nuevoRegistro.setSeccion(registro.getSeccion());		
				nuevoRegistro.setCategoria(registro.getCategoria());		
				nuevoRegistro.setSubcategoria(registro.getSubcategoria());		
				nuevoRegistro.setSegmento(registro.getSegmento());		
				
				nuevoRegistro.setReferencia((registro.getCodArtFormlog() != null && !("".equals(registro.getCodArtFormlog().toString())))?new Long(registro.getCodArtFormlog().toString()):null);		
				nuevoRegistro.setReferenciaMadre((registro.getCodArtFormlog() != null && !("".equals(registro.getCodArtFormlog().toString())))?new Long(registro.getCodArtFormlog().toString()):null);
				nuevoRegistro.setDescripcion(registro.getDenomArticulo());		
				
				if (esCaprabo) {
					nuevoRegistro.setCodArticuloGrid(registro.getCodArtFormlog());
					nuevoRegistro.setDescriptionArtGrid(utilidadesCapraboService.obtenerDescArtCaprabo(nuevoRegistro.getCodArticuloGrid()));
				} else {
					nuevoRegistro.setCodArticuloGrid((registro.getCodArtFormlog() != null && !("".equals(registro.getCodArtFormlog().toString())))?new Long(registro.getCodArtFormlog().toString()):null);
					nuevoRegistro.setDescriptionArtGrid((registro.getDenomArticulo() != null && !("".equals(registro.getDenomArticulo())))?registro.getDenomArticulo():null);
				}
				
				nuevoRegistro.setUnidadescaja(registro.getUnidServ() != null && !("".equals(registro.getUnidServ().toString()))?new Double(registro.getUnidServ().toString()):null);		
				nuevoRegistro.setIddsesion(idSesion);		
				nuevoRegistro.setContactoCentro(registro.getContactoCentro());		
				nuevoRegistro.setTelefonoCentro(registro.getTelefonoCentro());		
				nuevoRegistro.setNombreCliente(registro.getNombreCliente());		
				nuevoRegistro.setApellidoCliente(registro.getApellidoCliente());		
				nuevoRegistro.setTelefonoCliente(registro.getTelefonoCliente());		
				nuevoRegistro.setFechaHoraEncargo(registro.getFechaHoraEncargo());		
				nuevoRegistro.setTipoEncargo(registro.getTipoEncargo());		
				nuevoRegistro.setFechaVenta(registro.getFechaVenta());		
				nuevoRegistro.setFechaVentaModificada(registro.getFechaVentaModificada());		
				nuevoRegistro.setFechaInferior(registro.getFechaInferior());		
				nuevoRegistro.setEspecificacion(registro.getEspecificacion());		
				nuevoRegistro.setPesoDesde((registro.getPesoDesde() != null && !("".equals(registro.getPesoDesde().toString())))?new Double(registro.getPesoDesde().toString()):null);		
				nuevoRegistro.setPesoHasta((registro.getPesoHasta() != null && !("".equals(registro.getPesoHasta().toString())))?new Double(registro.getPesoHasta().toString()):null);		
				nuevoRegistro.setConfirmarEspecificaciones(registro.getConfirmarEspecificaciones());		
				nuevoRegistro.setFaltaRef(registro.getFaltaRef());		
				nuevoRegistro.setCambioRef(registro.getCambioRef());		
				nuevoRegistro.setConfirmarPrecio(registro.getConfirmarPrecio());
				nuevoRegistro.setCantEncargo((registro.getCantEncargo() != null && !("".equals(registro.getCantEncargo().toString())))?new Double(registro.getCantEncargo().toString()):null);
				nuevoRegistro.setCantFinalCompra((registro.getCantFinalCompra() != null && !("".equals(registro.getCantFinalCompra().toString())))?new Double(registro.getCantFinalCompra().toString()):null);
				nuevoRegistro.setCantServido((registro.getCantServido() != null && !("".equals(registro.getCantServido().toString())))?new Double(registro.getCantServido().toString()):null);
				nuevoRegistro.setCantNoServido((registro.getCantNoServido() != null && !("".equals(registro.getCantNoServido().toString())))?new Double(registro.getCantNoServido().toString()):null);
				nuevoRegistro.setEstado(registro.getEstado());
				nuevoRegistro.setObservacionesMisumi(registro.getObservacionesMisumi());
				nuevoRegistro.setCodigoPedidoInterno((registro.getCodigoPedidoInterno() != null && !("".equals(registro.getCodigoPedidoInterno().toString())))?new BigDecimal(registro.getCodigoPedidoInterno().toString()):null);
				nuevoRegistro.setFlgModificable(registro.getFlgModificable());
				nuevoRegistro.setCodTpAprov(registro.getTipoAprov());
				nuevoRegistro.setVitrina(registro.getVitrina());
				nuevoRegistro.setRelCompraVenta(registro.getRelCompraVenta());
				nuevoRegistro.setCodigoError((registro.getCodigoError() != null && !("".equals(registro.getCodigoError().toString())))?new Long(registro.getCodigoError().toString()):null);
				nuevoRegistro.setDescripcionError(registro.getDescripcionError());
				nuevoRegistro.setFechacreacion(new Date());
				nuevoRegistro.setDescripcionGestadic(registro.getDescripcionGestadic());
				nuevoRegistro.setEstadoGestadic(registro.getEstadoGestadic());
				nuevoRegistro.setTxtDetalleGestadic(registro.getTxtDetalleGestadic());
				nuevoRegistro.setTxtSituacionGestadic(registro.getTxtSituacionGestadic());
				nuevoRegistro.setFlgEspec(registro.getFlgEspec());
				
				listaTEncargosClte.add(nuevoRegistro);
			}
		
		
			this.tEncargosClteService.insertAll(listaTEncargosClte);
		} catch (Exception e) {
			logger.error("insertarTablaSesionEC="+e.toString());
			e.printStackTrace();
		}
			
	}
	
	
private List<PedidoAdicionalM> addPedidosHTNoPblM(PedidoHTNoPblLista listPedidosHTNoPbl, List<PedidoAdicionalM> list)  throws Exception{
		
		PedidoAdicionalM pedidoAdicionalM = new PedidoAdicionalM();
		
		//Obtenemos el índice.
		int indice = list.size();
		
		List<PedidoHTNoPbl> datosPedidosHTNoPbl = null;
		if(listPedidosHTNoPbl != null && listPedidosHTNoPbl.getDatos()!=null){
			datosPedidosHTNoPbl = listPedidosHTNoPbl.getDatos();
		}
		if (datosPedidosHTNoPbl!=null){
			for (PedidoHTNoPbl pedidoHTNoPbl : datosPedidosHTNoPbl)
			{
				pedidoAdicionalM = new PedidoAdicionalM();
				
				pedidoAdicionalM.setCodCentro(pedidoHTNoPbl.getCodCentro());
				pedidoAdicionalM.setClasePedido(new Long(Constantes.CLASE_PEDIDO_MONTAJE));
				pedidoAdicionalM.setCodArticulo(pedidoHTNoPbl.getCodArticulo());
				pedidoAdicionalM.setDescriptionArt(pedidoHTNoPbl.getDescriptionArt());
				pedidoAdicionalM.setGrupo1(pedidoHTNoPbl.getGrupo1());
				pedidoAdicionalM.setGrupo2(pedidoHTNoPbl.getGrupo2());
				pedidoAdicionalM.setGrupo3(pedidoHTNoPbl.getGrupo3());
				pedidoAdicionalM.setAgrupacion(pedidoHTNoPbl.getAgrupacion());
				pedidoAdicionalM.setUniCajaServ((pedidoHTNoPbl.getUniCajaServ() != null && !("".equals(pedidoHTNoPbl.getUniCajaServ().toString())))?pedidoHTNoPbl.getUniCajaServ():null);
				pedidoAdicionalM.setFechaInicio((pedidoHTNoPbl.getFechaInicio() != null)?pedidoHTNoPbl.getFechaInicio():null);
				pedidoAdicionalM.setFechaFin((pedidoHTNoPbl.getFechaFin() != null)?pedidoHTNoPbl.getFechaFin():null);
				pedidoAdicionalM.setCantMax((pedidoHTNoPbl.getCantMax() != null && !("".equals(pedidoHTNoPbl.getCantMax().toString())))?pedidoHTNoPbl.getCantMax():null);
				pedidoAdicionalM.setCantMin((pedidoHTNoPbl.getCantMin() != null && !("".equals(pedidoHTNoPbl.getCantMin().toString())))?pedidoHTNoPbl.getCantMin():null);
				pedidoAdicionalM.setPerfil(new Long(1));//Pet. 58538
				pedidoAdicionalM.setExcluir(true);//En la pantalla no aparecerá pero tendrá "S" por defecto
				pedidoAdicionalM.setEsPlanograma("N");
				pedidoAdicionalM.setNoGestionaPbl("S");
				pedidoAdicionalM.setIndice(indice);
				pedidoAdicionalM.setIdentificador(pedidoHTNoPbl.getIdentificador());
				pedidoAdicionalM.setTipoAprovisionamiento(pedidoHTNoPbl.getTipoAprovisionamiento());
				if (pedidoHTNoPbl.getAnoOferta()!=null && !"".equals(pedidoHTNoPbl.getAnoOferta()) && pedidoHTNoPbl.getNumOferta()!=null && !"".equals(pedidoHTNoPbl.getNumOferta())){
					pedidoAdicionalM.setOferta(pedidoHTNoPbl.getAnoOferta()+"-"+pedidoHTNoPbl.getNumOferta());
				}
				pedidoAdicionalM.setTipoPedido((pedidoHTNoPbl.getTipoPedido() != null && !("".equals(pedidoHTNoPbl.getTipoPedido())))?pedidoHTNoPbl.getTipoPedido():null);
				pedidoAdicionalM.setFechaHasta((pedidoHTNoPbl.getFechaHasta() != null && !("".equals(pedidoHTNoPbl.getFechaHasta())))?pedidoHTNoPbl.getFechaHasta():null);
				pedidoAdicionalM.setDescOferta((pedidoHTNoPbl.getDescOferta() != null && !("".equals(pedidoHTNoPbl.getDescOferta())))?pedidoHTNoPbl.getDescOferta():null);
				pedidoAdicionalM.setFecha2((pedidoHTNoPbl.getFecha2() != null && !("".equals(pedidoHTNoPbl.getFecha2())))?pedidoHTNoPbl.getFecha2():null);
				pedidoAdicionalM.setFecha3((pedidoHTNoPbl.getFecha3() != null && !("".equals(pedidoHTNoPbl.getFecha3())))?pedidoHTNoPbl.getFecha3():null);
				pedidoAdicionalM.setFecha4((pedidoHTNoPbl.getFecha4() != null && !("".equals(pedidoHTNoPbl.getFecha4())))?pedidoHTNoPbl.getFecha4():null);
				pedidoAdicionalM.setFecha5((pedidoHTNoPbl.getFecha5() != null && !("".equals(pedidoHTNoPbl.getFecha5())))?pedidoHTNoPbl.getFecha5():null);
				pedidoAdicionalM.setCantidad1((pedidoHTNoPbl.getCantidad1() != null )? pedidoHTNoPbl.getCantidad1():null);
				pedidoAdicionalM.setCantidad2((pedidoHTNoPbl.getCantidad2() != null )? pedidoHTNoPbl.getCantidad2():null);
				pedidoAdicionalM.setCantidad3((pedidoHTNoPbl.getCantidad3() != null )? pedidoHTNoPbl.getCantidad3():null);
				pedidoAdicionalM.setCantidad4((pedidoHTNoPbl.getCantidad4() != null )? pedidoHTNoPbl.getCantidad4():null);
				pedidoAdicionalM.setCantidad5((pedidoHTNoPbl.getCantidad5() != null )? pedidoHTNoPbl.getCantidad5():null);
				
				//Las planogramadas no son ni modificables ni borrables.
				pedidoAdicionalM.setBorrable(Constantes.PEDIDO_NO_BORRABLE);
				pedidoAdicionalM.setModificable(Constantes.PEDIDO_MODIFICABLE_SI);
				
				//Cálculo de cantidades modificables. La fecha de la cantidad debe ser >= hoy + 1 día
				StringBuffer modificableIndivBuff = new StringBuffer();
				
				Calendar calendarDiaControl = Calendar.getInstance();
				calendarDiaControl.setTime(new Date()); 
				calendarDiaControl.set(Calendar.HOUR_OF_DAY, 0);
				calendarDiaControl.set(Calendar.MINUTE, 0);
				calendarDiaControl.set(Calendar.SECOND, 0);
				calendarDiaControl.set(Calendar.MILLISECOND, 0);
				Date diaControl = calendarDiaControl.getTime(); 
				
				if (pedidoHTNoPbl.getFechaInicio() != null && !"".equals(pedidoHTNoPbl.getFechaInicio())){
					if (Utilidades.convertirStringAFecha(pedidoHTNoPbl.getFechaInicio()).after(diaControl)){
						modificableIndivBuff.append("S");
					}else{
						modificableIndivBuff.append("N");
					}
					if (pedidoHTNoPbl.getFecha2() != null && !"".equals(pedidoHTNoPbl.getFecha2())){
						if (Utilidades.convertirStringAFecha(pedidoHTNoPbl.getFecha2()).after(diaControl)){
							modificableIndivBuff.append("S");
						}else{
							modificableIndivBuff.append("N");
						}
						if (pedidoHTNoPbl.getFecha3() != null && !"".equals(pedidoHTNoPbl.getFecha3())){
							if (Utilidades.convertirStringAFecha(pedidoHTNoPbl.getFecha3()).after(diaControl)){
								modificableIndivBuff.append("S");
							}else{
								modificableIndivBuff.append("N");
							}
							if (pedidoHTNoPbl.getFecha4() != null && !"".equals(pedidoHTNoPbl.getFecha4())){
								if (Utilidades.convertirStringAFecha(pedidoHTNoPbl.getFecha4()).after(diaControl)){
									modificableIndivBuff.append("S");
								}else{
									modificableIndivBuff.append("N");
								}
								if (pedidoHTNoPbl.getFecha5() != null && !"".equals(pedidoHTNoPbl.getFecha5())){
									if (Utilidades.convertirStringAFecha(pedidoHTNoPbl.getFecha5()).after(diaControl)){
										modificableIndivBuff.append("S");
									}else{
										modificableIndivBuff.append("N");
									}
								}
							}
						}
					}
				}
				pedidoAdicionalM.setModificableIndiv(modificableIndivBuff.toString());

				list.add(pedidoAdicionalM);
				
				indice++;
			}
		}
		
		return list;
	}
	
	private List<PedidoAdicionalMO> addPedidosHTNoPblMO(PedidoHTNoPblLista listPedidosHTNoPbl, List<PedidoAdicionalMO> list)  throws Exception{
		
		PedidoAdicionalMO pedidoAdicionalMO = new PedidoAdicionalMO();
		
		//Obtenemos el índice.
		int indice = list.size();
		
		List<PedidoHTNoPbl> datosPedidosHTNoPbl = null;
		if(listPedidosHTNoPbl != null && listPedidosHTNoPbl.getDatos()!=null){
			datosPedidosHTNoPbl = listPedidosHTNoPbl.getDatos();
		}
		if (datosPedidosHTNoPbl!=null){
			for (PedidoHTNoPbl pedidoHTNoPbl : datosPedidosHTNoPbl)
			{
				pedidoAdicionalMO = new PedidoAdicionalMO();
				
				pedidoAdicionalMO.setCodCentro(pedidoHTNoPbl.getCodCentro());
				pedidoAdicionalMO.setClasePedido(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL));
				pedidoAdicionalMO.setCodArticulo(pedidoHTNoPbl.getCodArticulo());
				pedidoAdicionalMO.setDescriptionArt(pedidoHTNoPbl.getDescriptionArt());
				pedidoAdicionalMO.setGrupo1(pedidoHTNoPbl.getGrupo1());
				pedidoAdicionalMO.setGrupo2(pedidoHTNoPbl.getGrupo2());
				pedidoAdicionalMO.setGrupo3(pedidoHTNoPbl.getGrupo3());
				pedidoAdicionalMO.setAgrupacion(pedidoHTNoPbl.getAgrupacion());
				pedidoAdicionalMO.setUniCajaServ((pedidoHTNoPbl.getUniCajaServ() != null && !("".equals(pedidoHTNoPbl.getUniCajaServ().toString())))?pedidoHTNoPbl.getUniCajaServ():null);
				pedidoAdicionalMO.setFechaInicio((pedidoHTNoPbl.getFechaInicio() != null)?pedidoHTNoPbl.getFechaInicio():null);
				pedidoAdicionalMO.setFechaFin((pedidoHTNoPbl.getFechaFin() != null)?pedidoHTNoPbl.getFechaFin():null);
				pedidoAdicionalMO.setCantMax((pedidoHTNoPbl.getCantMax() != null && !("".equals(pedidoHTNoPbl.getCantMax().toString())))?pedidoHTNoPbl.getCantMax():null);
				pedidoAdicionalMO.setCantMin((pedidoHTNoPbl.getCantMin() != null && !("".equals(pedidoHTNoPbl.getCantMin().toString())))?pedidoHTNoPbl.getCantMin():null);
				pedidoAdicionalMO.setPerfil(new Long(1));//Pet. 58538
				pedidoAdicionalMO.setExcluir(true);//En la pantalla no aparecerá pero tendrá "S" por defecto
				pedidoAdicionalMO.setEsPlanograma("N");
				pedidoAdicionalMO.setNoGestionaPbl("S");
				pedidoAdicionalMO.setIndice(indice);
				pedidoAdicionalMO.setIdentificador(pedidoHTNoPbl.getIdentificador());
				pedidoAdicionalMO.setTipoAprovisionamiento(pedidoHTNoPbl.getTipoAprovisionamiento());
				if (pedidoHTNoPbl.getAnoOferta()!=null && !"".equals(pedidoHTNoPbl.getAnoOferta()) && pedidoHTNoPbl.getNumOferta()!=null && !"".equals(pedidoHTNoPbl.getNumOferta())){
					pedidoAdicionalMO.setOferta(pedidoHTNoPbl.getAnoOferta()+"-"+pedidoHTNoPbl.getNumOferta());
				}
				pedidoAdicionalMO.setTipoPedido((pedidoHTNoPbl.getTipoPedido() != null && !("".equals(pedidoHTNoPbl.getTipoPedido())))?pedidoHTNoPbl.getTipoPedido():null);
				pedidoAdicionalMO.setFechaHasta((pedidoHTNoPbl.getFechaHasta() != null && !("".equals(pedidoHTNoPbl.getFechaHasta())))?pedidoHTNoPbl.getFechaHasta():null);
				pedidoAdicionalMO.setDescOferta((pedidoHTNoPbl.getDescOferta() != null && !("".equals(pedidoHTNoPbl.getDescOferta())))?pedidoHTNoPbl.getDescOferta():null);
				pedidoAdicionalMO.setFecha2((pedidoHTNoPbl.getFecha2() != null && !("".equals(pedidoHTNoPbl.getFecha2())))?pedidoHTNoPbl.getFecha2():null);
				pedidoAdicionalMO.setFecha3((pedidoHTNoPbl.getFecha3() != null && !("".equals(pedidoHTNoPbl.getFecha3())))?pedidoHTNoPbl.getFecha3():null);
				pedidoAdicionalMO.setFecha4((pedidoHTNoPbl.getFecha4() != null && !("".equals(pedidoHTNoPbl.getFecha4())))?pedidoHTNoPbl.getFecha4():null);
				pedidoAdicionalMO.setFecha5((pedidoHTNoPbl.getFecha5() != null && !("".equals(pedidoHTNoPbl.getFecha5())))?pedidoHTNoPbl.getFecha5():null);
				pedidoAdicionalMO.setCantidad1((pedidoHTNoPbl.getCantidad1() != null )? pedidoHTNoPbl.getCantidad1():null);
				pedidoAdicionalMO.setCantidad2((pedidoHTNoPbl.getCantidad2() != null )? pedidoHTNoPbl.getCantidad2():null);
				pedidoAdicionalMO.setCantidad3((pedidoHTNoPbl.getCantidad3() != null )? pedidoHTNoPbl.getCantidad3():null);
				pedidoAdicionalMO.setCantidad4((pedidoHTNoPbl.getCantidad4() != null )? pedidoHTNoPbl.getCantidad4():null);
				pedidoAdicionalMO.setCantidad5((pedidoHTNoPbl.getCantidad5() != null )? pedidoHTNoPbl.getCantidad5():null);
				
				//Las planogramadas no son ni modificables ni borrables.
				pedidoAdicionalMO.setBorrable(Constantes.PEDIDO_NO_BORRABLE);
				pedidoAdicionalMO.setModificable(Constantes.PEDIDO_MODIFICABLE_SI);
				
				//Cálculo de cantidades modificables. La fecha de la cantidad debe ser >= hoy + 1 día
				StringBuffer modificableIndivBuff = new StringBuffer();
				
				Calendar calendarDiaControl = Calendar.getInstance();
				calendarDiaControl.setTime(new Date()); 
				calendarDiaControl.set(Calendar.HOUR_OF_DAY, 0);
				calendarDiaControl.set(Calendar.MINUTE, 0);
				calendarDiaControl.set(Calendar.SECOND, 0);
				calendarDiaControl.set(Calendar.MILLISECOND, 0);
				Date diaControl = calendarDiaControl.getTime(); 
				
				if (pedidoHTNoPbl.getFechaInicio() != null && !"".equals(pedidoHTNoPbl.getFechaInicio())){
					if (Utilidades.convertirStringAFecha(pedidoHTNoPbl.getFechaInicio()).after(diaControl)){
						modificableIndivBuff.append("S");
					}else{
						modificableIndivBuff.append("N");
					}
					if (pedidoHTNoPbl.getFecha2() != null && !"".equals(pedidoHTNoPbl.getFecha2())){
						if (Utilidades.convertirStringAFecha(pedidoHTNoPbl.getFecha2()).after(diaControl)){
							modificableIndivBuff.append("S");
						}else{
							modificableIndivBuff.append("N");
						}
						if (pedidoHTNoPbl.getFecha3() != null && !"".equals(pedidoHTNoPbl.getFecha3())){
							if (Utilidades.convertirStringAFecha(pedidoHTNoPbl.getFecha3()).after(diaControl)){
								modificableIndivBuff.append("S");
							}else{
								modificableIndivBuff.append("N");
							}
							if (pedidoHTNoPbl.getFecha4() != null && !"".equals(pedidoHTNoPbl.getFecha4())){
								if (Utilidades.convertirStringAFecha(pedidoHTNoPbl.getFecha4()).after(diaControl)){
									modificableIndivBuff.append("S");
								}else{
									modificableIndivBuff.append("N");
								}
								if (pedidoHTNoPbl.getFecha5() != null && !"".equals(pedidoHTNoPbl.getFecha5())){
									if (Utilidades.convertirStringAFecha(pedidoHTNoPbl.getFecha5()).after(diaControl)){
										modificableIndivBuff.append("S");
									}else{
										modificableIndivBuff.append("N");
									}
								}
							}
						}
					}
				}
				pedidoAdicionalMO.setModificableIndiv(modificableIndivBuff.toString());

				list.add(pedidoAdicionalMO);
				
				indice++;
			}
		}
		
		return list;
	}

	private List<PedidoAdicionalEM> addPedidosHTNoPblEM(PedidoHTNoPblLista listPedidosHTNoPbl, List<PedidoAdicionalEM> list)  throws Exception{
		
		PedidoAdicionalEM pedidoAdicionalEM = new PedidoAdicionalEM();
		
		//Obtenemos el índice.
		int indice = list.size();
		
		List<PedidoHTNoPbl> datosPedidosHTNoPbl = null;
		if(listPedidosHTNoPbl != null && listPedidosHTNoPbl.getDatos()!=null){
			datosPedidosHTNoPbl = listPedidosHTNoPbl.getDatos();
		}
		if (datosPedidosHTNoPbl!=null){
			for (PedidoHTNoPbl pedidoHTNoPbl : datosPedidosHTNoPbl)
			{
				pedidoAdicionalEM = new PedidoAdicionalEM();
				
				pedidoAdicionalEM.setCodCentro(pedidoHTNoPbl.getCodCentro());
				pedidoAdicionalEM.setClasePedido(new Long(Constantes.CLASE_PEDIDO_EMPUJE));
				pedidoAdicionalEM.setCodArticulo(pedidoHTNoPbl.getCodArticulo());
				pedidoAdicionalEM.setDescriptionArt(pedidoHTNoPbl.getDescriptionArt());
				pedidoAdicionalEM.setGrupo1(pedidoHTNoPbl.getGrupo1());
				pedidoAdicionalEM.setGrupo2(pedidoHTNoPbl.getGrupo2());
				pedidoAdicionalEM.setGrupo3(pedidoHTNoPbl.getGrupo3());
				pedidoAdicionalEM.setAgrupacion(pedidoHTNoPbl.getAgrupacion());
				pedidoAdicionalEM.setUniCajaServ((pedidoHTNoPbl.getUniCajaServ() != null && !("".equals(pedidoHTNoPbl.getUniCajaServ().toString())))?pedidoHTNoPbl.getUniCajaServ():null);
				pedidoAdicionalEM.setFechaInicio((pedidoHTNoPbl.getFechaInicio() != null)?pedidoHTNoPbl.getFechaInicio():null);
				pedidoAdicionalEM.setFechaFin((pedidoHTNoPbl.getFechaFin() != null)?pedidoHTNoPbl.getFechaFin():null);
				pedidoAdicionalEM.setCantMax((pedidoHTNoPbl.getCantMax() != null && !("".equals(pedidoHTNoPbl.getCantMax().toString())))?pedidoHTNoPbl.getCantMax():null);
				pedidoAdicionalEM.setCantMin((pedidoHTNoPbl.getCantMin() != null && !("".equals(pedidoHTNoPbl.getCantMin().toString())))?pedidoHTNoPbl.getCantMin():null);
				pedidoAdicionalEM.setPerfil(new Long(1));//Pet. 58538
				pedidoAdicionalEM.setExcluir(true);//En la pantalla no aparecerá pero tendrá "S" por defecto
				pedidoAdicionalEM.setEsPlanograma("N");
				pedidoAdicionalEM.setNoGestionaPbl("S");
				pedidoAdicionalEM.setIndice(indice);
				pedidoAdicionalEM.setIdentificador(pedidoHTNoPbl.getIdentificador());
				pedidoAdicionalEM.setTipoAprovisionamiento(pedidoHTNoPbl.getTipoAprovisionamiento());
				if (pedidoHTNoPbl.getAnoOferta()!=null && !"".equals(pedidoHTNoPbl.getAnoOferta()) && pedidoHTNoPbl.getNumOferta()!=null && !"".equals(pedidoHTNoPbl.getNumOferta())){
					pedidoAdicionalEM.setOferta(pedidoHTNoPbl.getAnoOferta()+"-"+pedidoHTNoPbl.getNumOferta());
				}
				pedidoAdicionalEM.setTipoPedido((pedidoHTNoPbl.getTipoPedido() != null && !("".equals(pedidoHTNoPbl.getTipoPedido())))?pedidoHTNoPbl.getTipoPedido():null);
				pedidoAdicionalEM.setFechaHasta((pedidoHTNoPbl.getFechaHasta() != null && !("".equals(pedidoHTNoPbl.getFechaHasta())))?pedidoHTNoPbl.getFechaHasta():null);
				pedidoAdicionalEM.setDescOferta((pedidoHTNoPbl.getDescOferta() != null && !("".equals(pedidoHTNoPbl.getDescOferta())))?pedidoHTNoPbl.getDescOferta():null);
				pedidoAdicionalEM.setFecha2((pedidoHTNoPbl.getFecha2() != null && !("".equals(pedidoHTNoPbl.getFecha2())))?pedidoHTNoPbl.getFecha2():null);
				pedidoAdicionalEM.setFecha3((pedidoHTNoPbl.getFecha3() != null && !("".equals(pedidoHTNoPbl.getFecha3())))?pedidoHTNoPbl.getFecha3():null);
				pedidoAdicionalEM.setFecha4((pedidoHTNoPbl.getFecha4() != null && !("".equals(pedidoHTNoPbl.getFecha4())))?pedidoHTNoPbl.getFecha4():null);
				pedidoAdicionalEM.setFecha5((pedidoHTNoPbl.getFecha5() != null && !("".equals(pedidoHTNoPbl.getFecha5())))?pedidoHTNoPbl.getFecha5():null);
				pedidoAdicionalEM.setCantidad1((pedidoHTNoPbl.getCantidad1() != null )? pedidoHTNoPbl.getCantidad1():null);
				pedidoAdicionalEM.setCantidad2((pedidoHTNoPbl.getCantidad2() != null )? pedidoHTNoPbl.getCantidad2():null);
				pedidoAdicionalEM.setCantidad3((pedidoHTNoPbl.getCantidad3() != null )? pedidoHTNoPbl.getCantidad3():null);
				pedidoAdicionalEM.setCantidad4((pedidoHTNoPbl.getCantidad4() != null )? pedidoHTNoPbl.getCantidad4():null);
				pedidoAdicionalEM.setCantidad5((pedidoHTNoPbl.getCantidad5() != null )? pedidoHTNoPbl.getCantidad5():null);
				
				//Las planogramadas no son ni modificables ni borrables.
				pedidoAdicionalEM.setBorrable(Constantes.PEDIDO_NO_BORRABLE);
				pedidoAdicionalEM.setModificable(Constantes.PEDIDO_MODIFICABLE_NO);
				
				list.add(pedidoAdicionalEM);
				
				indice++;
			}
		}
		
		return list;
	}
	
	private List<PedidoAdicionalVC> addPedidosHTNoPblVC(PedidoHTNoPblLista listPedidosHTNoPbl, List<PedidoAdicionalVC> list)  throws Exception{
		
		PedidoAdicionalVC pedidoAdicionalVC = new PedidoAdicionalVC();
		
		//Obtenemos el índice.
		int indice = list.size();
		
		List<PedidoHTNoPbl> datosPedidosHTNoPbl = null;
		if(listPedidosHTNoPbl != null && listPedidosHTNoPbl.getDatos()!=null){
			datosPedidosHTNoPbl = listPedidosHTNoPbl.getDatos();
		}
		if (datosPedidosHTNoPbl!=null){
			for (PedidoHTNoPbl pedidoHTNoPbl : datosPedidosHTNoPbl)
			{
				pedidoAdicionalVC = new PedidoAdicionalVC();
				
				pedidoAdicionalVC.setCodCentro(pedidoHTNoPbl.getCodCentro());
				pedidoAdicionalVC.setClasePedido(new Long(Constantes.CLASE_PEDIDO_VALIDAR_CANT_EXTRA_4));
				pedidoAdicionalVC.setCodArticulo(pedidoHTNoPbl.getCodArticulo());
				pedidoAdicionalVC.setDescriptionArt(pedidoHTNoPbl.getDescriptionArt());
				pedidoAdicionalVC.setGrupo1(pedidoHTNoPbl.getGrupo1());
				pedidoAdicionalVC.setGrupo2(pedidoHTNoPbl.getGrupo2());
				pedidoAdicionalVC.setGrupo3(pedidoHTNoPbl.getGrupo3());
				pedidoAdicionalVC.setAgrupacion(pedidoHTNoPbl.getAgrupacion());
				pedidoAdicionalVC.setUniCajaServ((pedidoHTNoPbl.getUniCajaServ() != null && !("".equals(pedidoHTNoPbl.getUniCajaServ().toString())))?pedidoHTNoPbl.getUniCajaServ():null);
				pedidoAdicionalVC.setFechaInicio((pedidoHTNoPbl.getFechaInicio() != null)?pedidoHTNoPbl.getFechaInicio():null);
				pedidoAdicionalVC.setFechaFin((pedidoHTNoPbl.getFechaFin() != null)?pedidoHTNoPbl.getFechaFin():null);
				pedidoAdicionalVC.setCantMax((pedidoHTNoPbl.getCantMax() != null && !("".equals(pedidoHTNoPbl.getCantMax().toString())))?pedidoHTNoPbl.getCantMax():null);
				pedidoAdicionalVC.setCantMin((pedidoHTNoPbl.getCantMin() != null && !("".equals(pedidoHTNoPbl.getCantMin().toString())))?pedidoHTNoPbl.getCantMin():null);
				pedidoAdicionalVC.setPerfil(new Long(1));//Pet. 58538
				pedidoAdicionalVC.setExcluir(true);//En la pantalla no aparecerá pero tendrá "S" por defecto
				pedidoAdicionalVC.setEsPlanograma("N");
				pedidoAdicionalVC.setNoGestionaPbl("S");
				pedidoAdicionalVC.setIndice(indice);
				pedidoAdicionalVC.setIdentificador(pedidoHTNoPbl.getIdentificador());
				pedidoAdicionalVC.setTipoAprovisionamiento(pedidoHTNoPbl.getTipoAprovisionamiento());
				if (pedidoHTNoPbl.getAnoOferta()!=null && !"".equals(pedidoHTNoPbl.getAnoOferta()) && pedidoHTNoPbl.getNumOferta()!=null && !"".equals(pedidoHTNoPbl.getNumOferta())){
					pedidoAdicionalVC.setOferta(pedidoHTNoPbl.getAnoOferta()+"-"+pedidoHTNoPbl.getNumOferta());
				}
				pedidoAdicionalVC.setTipoPedido((pedidoHTNoPbl.getTipoPedido() != null && !("".equals(pedidoHTNoPbl.getTipoPedido())))?pedidoHTNoPbl.getTipoPedido():null);
				pedidoAdicionalVC.setFechaHasta((pedidoHTNoPbl.getFechaHasta() != null && !("".equals(pedidoHTNoPbl.getFechaHasta())))?pedidoHTNoPbl.getFechaHasta():null);
				pedidoAdicionalVC.setDescOferta((pedidoHTNoPbl.getDescOferta() != null && !("".equals(pedidoHTNoPbl.getDescOferta())))?pedidoHTNoPbl.getDescOferta():null);
				pedidoAdicionalVC.setFecha2((pedidoHTNoPbl.getFecha2() != null && !("".equals(pedidoHTNoPbl.getFecha2())))?pedidoHTNoPbl.getFecha2():null);
				pedidoAdicionalVC.setFecha3((pedidoHTNoPbl.getFecha3() != null && !("".equals(pedidoHTNoPbl.getFecha3())))?pedidoHTNoPbl.getFecha3():null);
				pedidoAdicionalVC.setFecha4((pedidoHTNoPbl.getFecha4() != null && !("".equals(pedidoHTNoPbl.getFecha4())))?pedidoHTNoPbl.getFecha4():null);
				pedidoAdicionalVC.setFecha5((pedidoHTNoPbl.getFecha5() != null && !("".equals(pedidoHTNoPbl.getFecha5())))?pedidoHTNoPbl.getFecha5():null);
				pedidoAdicionalVC.setCantidad1((pedidoHTNoPbl.getCantidad1() != null )? pedidoHTNoPbl.getCantidad1():null);
				pedidoAdicionalVC.setCantidad2((pedidoHTNoPbl.getCantidad2() != null )? pedidoHTNoPbl.getCantidad2():null);
				pedidoAdicionalVC.setCantidad3((pedidoHTNoPbl.getCantidad3() != null )? pedidoHTNoPbl.getCantidad3():null);
				pedidoAdicionalVC.setCantidad4((pedidoHTNoPbl.getCantidad4() != null )? pedidoHTNoPbl.getCantidad4():null);
				pedidoAdicionalVC.setCantidad5((pedidoHTNoPbl.getCantidad5() != null )? pedidoHTNoPbl.getCantidad5():null);
				
				//Las planogramadas no son ni modificables ni borrables.
				pedidoAdicionalVC.setBorrable(Constantes.PEDIDO_NO_BORRABLE);
				pedidoAdicionalVC.setModificable(Constantes.PEDIDO_MODIFICABLE_SI);
				
				//Cálculo de cantidades modificables. La fecha de la cantidad debe ser >= hoy + 1 día
				StringBuffer modificableIndivBuff = new StringBuffer();
				
				Calendar calendarDiaControl = Calendar.getInstance();
				calendarDiaControl.setTime(new Date()); 
				calendarDiaControl.set(Calendar.HOUR_OF_DAY, 0);
				calendarDiaControl.set(Calendar.MINUTE, 0);
				calendarDiaControl.set(Calendar.SECOND, 0);
				calendarDiaControl.set(Calendar.MILLISECOND, 0);
				Date diaControl = calendarDiaControl.getTime(); 
				
				if (pedidoHTNoPbl.getFechaInicio() != null && !"".equals(pedidoHTNoPbl.getFechaInicio())){
					if (Utilidades.convertirStringAFecha(pedidoHTNoPbl.getFechaInicio()).after(diaControl)){
						modificableIndivBuff.append("S");
					}else{
						modificableIndivBuff.append("N");
					}
					if (pedidoHTNoPbl.getFecha2() != null && !"".equals(pedidoHTNoPbl.getFecha2())){
						if (Utilidades.convertirStringAFecha(pedidoHTNoPbl.getFecha2()).after(diaControl)){
							modificableIndivBuff.append("S");
						}else{
							modificableIndivBuff.append("N");
						}
						if (pedidoHTNoPbl.getFecha3() != null && !"".equals(pedidoHTNoPbl.getFecha3())){
							if (Utilidades.convertirStringAFecha(pedidoHTNoPbl.getFecha3()).after(diaControl)){
								modificableIndivBuff.append("S");
							}else{
								modificableIndivBuff.append("N");
							}
							if (pedidoHTNoPbl.getFecha4() != null && !"".equals(pedidoHTNoPbl.getFecha4())){
								if (Utilidades.convertirStringAFecha(pedidoHTNoPbl.getFecha4()).after(diaControl)){
									modificableIndivBuff.append("S");
								}else{
									modificableIndivBuff.append("N");
								}
								if (pedidoHTNoPbl.getFecha5() != null && !"".equals(pedidoHTNoPbl.getFecha5())){
									if (Utilidades.convertirStringAFecha(pedidoHTNoPbl.getFecha5()).after(diaControl)){
										modificableIndivBuff.append("S");
									}else{
										modificableIndivBuff.append("N");
									}
								}
							}
						}
					}
				}
				pedidoAdicionalVC.setModificableIndiv(modificableIndivBuff.toString());
				
				list.add(pedidoAdicionalVC);
				
				indice++;
			}
		}
		
		return list;
	}
	
private List<PedidoAdicionalM> addPlanogramas(List<VPlanPedidoAdicional> listPlanogramas, List<PedidoAdicionalM> list)  throws Exception{
		
		PedidoAdicionalM pedidoAdicionalM = new PedidoAdicionalM();
		
		//Obtenemos el índice.
		int indice = list.size();
		
		if (listPlanogramas != null && listPlanogramas.size()>0)
		{
			for (int i =0;i<listPlanogramas.size();i++)
			{
				pedidoAdicionalM = new PedidoAdicionalM();
				
				pedidoAdicionalM.setCodCentro(listPlanogramas.get(i).getCodCentro());
				pedidoAdicionalM.setClasePedido(new Long(Constantes.CLASE_PEDIDO_MONTAJE));
				pedidoAdicionalM.setCodArticulo(listPlanogramas.get(i).getCodArt());
				pedidoAdicionalM.setDescriptionArt(listPlanogramas.get(i).getDescripArt());
				pedidoAdicionalM.setGrupo1(listPlanogramas.get(i).getGrupo1());
				pedidoAdicionalM.setGrupo2(listPlanogramas.get(i).getGrupo2());
				pedidoAdicionalM.setGrupo3(listPlanogramas.get(i).getGrupo3());
				pedidoAdicionalM.setAgrupacion(listPlanogramas.get(i).getAgrupacion());
				pedidoAdicionalM.setUniCajaServ((listPlanogramas.get(i).getUniCajaServ() != null && !("".equals(listPlanogramas.get(i).getUniCajaServ().toString())))?listPlanogramas.get(i).getUniCajaServ():null);
				pedidoAdicionalM.setFechaInicio((listPlanogramas.get(i).getFechaInicio() != null)?Utilidades.formatearFecha(listPlanogramas.get(i).getFechaInicio()):null);
				pedidoAdicionalM.setFechaFin((listPlanogramas.get(i).getFechaFin() != null)?Utilidades.formatearFecha(listPlanogramas.get(i).getFechaFin()):null);
				pedidoAdicionalM.setCapMax((listPlanogramas.get(i).getImpInicial() != null && !("".equals(listPlanogramas.get(i).getImpInicial().toString())))?listPlanogramas.get(i).getImpInicial():null);
				pedidoAdicionalM.setCapMin((listPlanogramas.get(i).getImpFinal() != null && !("".equals(listPlanogramas.get(i).getImpFinal().toString())))?listPlanogramas.get(i).getImpFinal():null);
				pedidoAdicionalM.setPerfil(listPlanogramas.get(i).getPerfil());
				pedidoAdicionalM.setExcluir((listPlanogramas.get(i).getExcluir() != null && !("".equals(listPlanogramas.get(i).getExcluir().toString())))?(("N".equals(listPlanogramas.get(i).getExcluir().toString())?false:true)):null);
				pedidoAdicionalM.setEsPlanograma("S");
				pedidoAdicionalM.setNoGestionaPbl("N");
				pedidoAdicionalM.setIndice(indice);
				pedidoAdicionalM.setTipoAprovisionamiento(listPlanogramas.get(i).getTipoAprovisionamiento());
				pedidoAdicionalM.setDescPeriodo(listPlanogramas.get(i).getDescPeriodo());
				pedidoAdicionalM.setEspacioPromo(listPlanogramas.get(i).getEspacioPromo());
				
				//Las planogramadas no son ni modificables ni borrables.
				pedidoAdicionalM.setBorrable(Constantes.PEDIDO_NO_BORRABLE);
				pedidoAdicionalM.setModificable(Constantes.PEDIDO_MODIFICABLE_NO);
				
				list.add(pedidoAdicionalM);
				
				indice++;
			}
		}
		
		return list;
		
	}

private List<PedidoAdicionalMO> addPlanogramasOferta(List<VPlanPedidoAdicional> listPlanogramas, List<PedidoAdicionalMO> list)  throws Exception{
	
	PedidoAdicionalMO pedidoAdicionalMO = new PedidoAdicionalMO();
	
	//Obtenemos el índice.
	int indice = list.size();
	
	if (listPlanogramas != null && listPlanogramas.size()>0)
	{
		for (int i =0;i<listPlanogramas.size();i++)
		{
			pedidoAdicionalMO = new PedidoAdicionalMO();
			
			pedidoAdicionalMO.setCodCentro(listPlanogramas.get(i).getCodCentro());
			pedidoAdicionalMO.setClasePedido(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL));
			pedidoAdicionalMO.setCodArticulo(listPlanogramas.get(i).getCodArt());
			pedidoAdicionalMO.setDescriptionArt(listPlanogramas.get(i).getDescripArt());
			pedidoAdicionalMO.setGrupo1(listPlanogramas.get(i).getGrupo1());
			pedidoAdicionalMO.setGrupo2(listPlanogramas.get(i).getGrupo2());
			pedidoAdicionalMO.setGrupo3(listPlanogramas.get(i).getGrupo3());
			pedidoAdicionalMO.setAgrupacion(listPlanogramas.get(i).getAgrupacion());
			pedidoAdicionalMO.setOferta(listPlanogramas.get(i).getAnoOferta()+"-"+((listPlanogramas.get(i).getCodOferta())));
			pedidoAdicionalMO.setUniCajaServ((listPlanogramas.get(i).getUniCajaServ() != null && !("".equals(listPlanogramas.get(i).getUniCajaServ().toString())))?listPlanogramas.get(i).getUniCajaServ():null);
			pedidoAdicionalMO.setFechaInicio((listPlanogramas.get(i).getFechaInicio() != null)?Utilidades.formatearFecha(listPlanogramas.get(i).getFechaInicio()):null);
			pedidoAdicionalMO.setFechaFin((listPlanogramas.get(i).getFechaFin() != null)?Utilidades.formatearFecha(listPlanogramas.get(i).getFechaFin()):null);
			pedidoAdicionalMO.setCapMax((listPlanogramas.get(i).getImpInicial() != null && !("".equals(listPlanogramas.get(i).getImpInicial().toString())))?listPlanogramas.get(i).getImpInicial():null);
			pedidoAdicionalMO.setCapMin((listPlanogramas.get(i).getImpFinal() != null && !("".equals(listPlanogramas.get(i).getImpFinal().toString())))?listPlanogramas.get(i).getImpFinal():null);
			pedidoAdicionalMO.setPerfil(listPlanogramas.get(i).getPerfil());
			pedidoAdicionalMO.setEsPlanograma("S");
			pedidoAdicionalMO.setNoGestionaPbl("N");
			pedidoAdicionalMO.setIndice(indice);
			pedidoAdicionalMO.setTipoAprovisionamiento(listPlanogramas.get(i).getTipoAprovisionamiento());
			pedidoAdicionalMO.setDescPeriodo(listPlanogramas.get(i).getDescPeriodo());
			pedidoAdicionalMO.setEspacioPromo(listPlanogramas.get(i).getEspacioPromo());
			
			//Las planogramadas no son ni modificables ni borrables.
			pedidoAdicionalMO.setBorrable(Constantes.PEDIDO_NO_BORRABLE);
			pedidoAdicionalMO.setModificable(Constantes.PEDIDO_MODIFICABLE_NO);
			
			list.add(pedidoAdicionalMO);
			
			indice++;
		}
	}
	
	return list;
	
}
	
	@Override
	public void modifyPedidoAdCentral(TPedidoAdicional tPedidoAdicional, HttpSession session) throws Exception {

		if (tPedidoAdicional.getIdentificador() != null){
			PedidosAdCentral pedidosAdCentral = new PedidosAdCentral();
			pedidosAdCentral.setIdentificador(tPedidoAdicional.getIdentificador());
			List<PedidosAdCentral> listaPedidosAdCentral = this.pedidosAdCentralService.findAll(pedidosAdCentral);
			
			if (listaPedidosAdCentral != null && listaPedidosAdCentral.size()>0)
			{
				PedidosAdCentral registro = (PedidosAdCentral)listaPedidosAdCentral.get(0);
				tPedidoAdicional.setFlgValidado(registro.getFlgValidado());
				this.pedidosAdCentralSPDao.actualizacionPedidosAd(tPedidoAdicional, session);
			}
		}
	}

	@Override
	public void insertarPedidosVegalsa(PedidoAdicionalE pedidoAdicionalE, HttpSession session) throws Exception {
		this.tPedidoAdicionalService.insertarPedidosVegalsa(pedidoAdicionalE, session);
	}

}
