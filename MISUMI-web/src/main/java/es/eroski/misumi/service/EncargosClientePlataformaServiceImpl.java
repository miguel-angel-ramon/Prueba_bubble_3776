package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import es.eroski.misumi.dao.iface.DiasServicioDao;
import es.eroski.misumi.dao.iface.EncargosClientePlataformaDao;
import es.eroski.misumi.dao.iface.GestionEncargosDao;
import es.eroski.misumi.dao.iface.ReferenciasAltaPlataformaDao;
import es.eroski.misumi.dao.iface.ValidarReferenciaEncargosDao;
import es.eroski.misumi.model.DiasServicio;
import es.eroski.misumi.model.EncargoCliente;
import es.eroski.misumi.model.EncargosClienteLista;
import es.eroski.misumi.model.EncargosClientePlataforma;
import es.eroski.misumi.model.EncargosClientePlataformaLista;
import es.eroski.misumi.model.PedidoAdicionalCompleto;
import es.eroski.misumi.model.ValidarReferenciaEncargo;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.EncargosClientePlataformaService;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;

@Service(value = "EncargosClientePlataformaService")
public class EncargosClientePlataformaServiceImpl implements EncargosClientePlataformaService {

	@Autowired
	private EncargosClientePlataformaDao encargosClientePlataformaDao;
	
	@Autowired
	private ReferenciasAltaPlataformaDao referenciasAltaPlataformaDao;
	
	@Autowired
	private ValidarReferenciaEncargosDao validarReferenciaEncargosDao;
	
	@Autowired
	private GestionEncargosDao gestionEncargosDao;
	
	@Autowired
	private DiasServicioDao diasServicioDao;
	
	@Autowired
	private UtilidadesCapraboService utilidadesCapraboService;
	
	
	@Override
	public EncargosClientePlataformaLista cargarReferencias(EncargosClientePlataforma encargosCliente) throws Exception{
		this.encargosClientePlataformaDao.deleteReferencias(encargosCliente);
		EncargosClientePlataformaLista listaReferencias = this.referenciasAltaPlataformaDao.consultaReferenciasAltaCatalogo(encargosCliente);
		if (!listaReferencias.getDatos().isEmpty()){
			this.encargosClientePlataformaDao.insertReferencias(listaReferencias.getDatos());
		}
		
		return listaReferencias;
	}

	@Override
	public List<String> getSecciones(
			EncargosClientePlataforma encargosCliente) throws Exception {

		return this.encargosClientePlataformaDao.getSecciones(encargosCliente);
	}

	@Override
	public List<String> getCategorias(
			EncargosClientePlataforma encargosCliente) throws Exception {
		return this.encargosClientePlataformaDao.getCategorias(encargosCliente);
	}

	@Override
	public List<EncargosClientePlataforma> getReferencias(
			EncargosClientePlataforma encargosCliente, Pagination pagination) throws Exception {
		
		return this.encargosClientePlataformaDao.getReferencias(encargosCliente, pagination);
	}
	
	@Override
	public Long getReferenciasCount(
			EncargosClientePlataforma encargosCliente) throws Exception {
		
		return this.encargosClientePlataformaDao.getReferenciasCount(encargosCliente);
	}
	
	@Override
	public void insertReferencias(final List<EncargosClientePlataforma> listaReferencias) throws Exception{
		this.encargosClientePlataformaDao.insertReferencias(listaReferencias);

	}
	
	@Override
	public void deleteReferencias(EncargosClientePlataforma encargosCliente) throws Exception {
		this.encargosClientePlataformaDao.deleteReferencias(encargosCliente);

	}
	
	@Override
	public ValidarReferenciaEncargo validarReferencia(ValidarReferenciaEncargo validarReferenciaEncargo, String codUser) throws Exception {
		String esGenerico = validarReferenciaEncargo.getGenerico();
		validarReferenciaEncargo.setGenerico(null);
		ValidarReferenciaEncargo encargo =  this.validarReferenciaEncargosDao.validarReferenciaEncargos(validarReferenciaEncargo);
		if(null == encargo){
			encargo = new ValidarReferenciaEncargo();
			encargo.setCodError(-1);
			encargo.setDescError("Error al validar la referencia de encargo cliente");
		}
		if (null != encargo.getFechasVenta() && !encargo.getFechasVenta().isEmpty()){
			DiasServicio diasServicio = new DiasServicio();
			diasServicio.setdEncargoCliente("S");
			
			if ((utilidadesCapraboService.esCentroCaprabo(validarReferenciaEncargo.getCodCentro(), codUser)) && !(esGenerico.equals("S"))) {//Es un centro Caprabo . si es una referencia generica no hay que traducirla.
				diasServicio.setCodArt(utilidadesCapraboService.obtenerCodigoEroski(validarReferenciaEncargo.getCodCentro(), validarReferenciaEncargo.getCodReferencia()));
			} else {
				diasServicio.setCodArt(validarReferenciaEncargo.getCodReferencia());
			}
				
			diasServicio.setCodCentro(validarReferenciaEncargo.getCodCentro());
			diasServicio.setIdsesion(RequestContextHolder.getRequestAttributes().getSessionId());
			this.diasServicioDao.updateDiasEncargoCliente(diasServicio, encargo.getFechasVenta());
		}
		return encargo;
	}
	
	@Override
	public EncargosClienteLista insertarReferencia(PedidoAdicionalCompleto pedidoAdicional, String codUser) throws Exception{
		List<EncargoCliente> listaActualizacion = new ArrayList<EncargoCliente>();
		EncargoCliente encargoCliente = new EncargoCliente();
		encargoCliente.setCodLoc(pedidoAdicional.getCodCentro());
		encargoCliente.setContactoCentro(pedidoAdicional.getContactoCentro());
		Long telefono = encargoCliente.getCodLoc() + 40000;
		encargoCliente.setTelefonoCentro(telefono.toString());
		encargoCliente.setNombreCliente(pedidoAdicional.getNombreCliente());
		encargoCliente.setApellidoCliente(pedidoAdicional.getApellidoCliente());
		encargoCliente.setTelefonoCliente(pedidoAdicional.getTelefonoCliente());
		encargoCliente.setFechaHoraEncargo(new Date());
		String tipoEncargo = "N";
		if (null != pedidoAdicional.getDatosPedidoPesoDesde() || !pedidoAdicional.getDatosPedidoDescripcion().isEmpty()){
			tipoEncargo = "E";
		}
		encargoCliente.setTipoEncargo(tipoEncargo);
		encargoCliente.setFechaVenta(pedidoAdicional.getFechaIni());
		if (pedidoAdicional.getFechaIni().before(pedidoAdicional.getPrimeraFechaEntrega())){
			encargoCliente.setFechaInferior(true);
		} else {
			encargoCliente.setFechaInferior(false);
		}
		
		encargoCliente.setCodArticulo(pedidoAdicional.getCodArt());
		
		if (utilidadesCapraboService.esCentroCaprabo(pedidoAdicional.getCodCentro(), codUser)) {//Es un centro Caprabo . 	
			encargoCliente.setCodArticulo(pedidoAdicional.getCodArtGrid());
		}
			
		
		if (!pedidoAdicional.getDatosPedidoDescripcion().isEmpty()){
			encargoCliente.setEspecificacion(pedidoAdicional.getDatosPedidoDescripcion());
		}
		if (null != pedidoAdicional.getDatosPedidoPesoDesde()) {
			encargoCliente.setPesoDesde(pedidoAdicional.getDatosPedidoPesoDesde());
			if (null != pedidoAdicional.getDatosPedidoPesoHasta()){
				encargoCliente.setPesoHasta(pedidoAdicional.getDatosPedidoPesoHasta());
			} else {
				encargoCliente.setPesoHasta(pedidoAdicional.getDatosPedidoPesoDesde());
			}
		}
		encargoCliente.setCantidadEncargo(pedidoAdicional.getDatosPedidoUnidadesPedir());
		if (tipoEncargo.equals("E")){
			encargoCliente.setConfirmarEspecificacion(true);
		} else {
			encargoCliente.setConfirmarEspecificacion(false);
		}
		if (pedidoAdicional.isEsGenerica()){
			encargoCliente.setFaltaReferencia(true);
		} else {
			encargoCliente.setFaltaReferencia(false);
		}
		
		encargoCliente.setCambioReferencia(false);
		encargoCliente.setConfirmarPrecio("N");
		encargoCliente.setFlgEspec(pedidoAdicional.getFlgEspec());
		listaActualizacion.add(encargoCliente);
		
		return this.gestionEncargosDao.gestionEncargos(listaActualizacion);
	}
	
	@Override
	public Boolean comprobarReferencia(EncargosClientePlataforma encargosCliente) throws Exception{
		Boolean generico = false;
		EncargosClientePlataformaLista listaReferencias = this.referenciasAltaPlataformaDao.consultaReferenciasAltaCatalogo(encargosCliente);
		for (EncargosClientePlataforma reg : listaReferencias.getDatos()){
			if (reg.getCodReferencia().equals(encargosCliente.getCodReferencia())){
				generico = true;
				break;
			}
		}
		return generico;
	}

}
