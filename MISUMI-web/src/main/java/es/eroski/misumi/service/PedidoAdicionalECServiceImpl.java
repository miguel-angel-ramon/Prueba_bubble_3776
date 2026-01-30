package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.PedidoAdicionalECDao;
import es.eroski.misumi.model.PedidoAdicionalEC;
import es.eroski.misumi.model.TEncargosClte;
import es.eroski.misumi.service.iface.PedidoAdicionalECService;
import es.eroski.misumi.service.iface.TEncargosClteService;
import es.eroski.misumi.util.Utilidades;


@Service(value = "PedidoAdicionalECService")
public class PedidoAdicionalECServiceImpl implements PedidoAdicionalECService {
    @Autowired
	private PedidoAdicionalECDao pedidoAdicionalDao;
	@Autowired
	private TEncargosClteService tEncargosClteService;
	@Override
	 public List<PedidoAdicionalEC> findAll(PedidoAdicionalEC pedidoAdicionalEC) throws Exception {
		return this.pedidoAdicionalDao.findAll(pedidoAdicionalEC);
	}
	@Override
	public List<PedidoAdicionalEC> removeAll(List<PedidoAdicionalEC> listaPedidoAdicionalEC) throws Exception {
		return this.pedidoAdicionalDao.removeAll(listaPedidoAdicionalEC);
	}
	@Override
	public List<PedidoAdicionalEC> modifyAll(List<PedidoAdicionalEC> listaPedidoAdicionalEC) throws Exception {
		return this.pedidoAdicionalDao.modifyAll(listaPedidoAdicionalEC);
	}
	@Override
	public PedidoAdicionalEC obtenerTablaSesionECRegistro(String idSesion, Long localizador) throws Exception {
		TEncargosClte registroBuscado = new TEncargosClte();
		registroBuscado.setIddsesion(idSesion);
		registroBuscado.setLocalizador(localizador);
		
		PedidoAdicionalEC pedidoAdicionalEC = null;
		
		TEncargosClte registroTEncargosCliente = this.tEncargosClteService.findOne(registroBuscado);
		
		if (registroTEncargosCliente != null )
		{
			pedidoAdicionalEC = new PedidoAdicionalEC();
			
			pedidoAdicionalEC.setId((registroTEncargosCliente.getCodigoPedidoInterno() != null && !("".equals(registroTEncargosCliente.getCodigoPedidoInterno().toString())))?"n2_"+registroTEncargosCliente.getLocalizador().toString():null);
			pedidoAdicionalEC.setLocalizador(registroTEncargosCliente.getLocalizador() != null && !("".equals(registroTEncargosCliente.getLocalizador().toString()))?new Long(registroTEncargosCliente.getLocalizador().toString()):null);
			pedidoAdicionalEC.setCodLoc((registroTEncargosCliente.getCentro() != null && !("".equals(registroTEncargosCliente.getCentro().toString())))?new Long(registroTEncargosCliente.getCentro().toString()):null);
			pedidoAdicionalEC.setArea(registroTEncargosCliente.getArea());		
			pedidoAdicionalEC.setSeccion(registroTEncargosCliente.getSeccion());		
			pedidoAdicionalEC.setCategoria(registroTEncargosCliente.getCategoria());		
			pedidoAdicionalEC.setSubcategoria(registroTEncargosCliente.getSubcategoria());		
			pedidoAdicionalEC.setSegmento(registroTEncargosCliente.getSegmento());		
			pedidoAdicionalEC.setCodArtFormlog((registroTEncargosCliente.getReferencia() != null && !("".equals(registroTEncargosCliente.getReferencia().toString())))?new Long(registroTEncargosCliente.getReferencia().toString()):null);		
			pedidoAdicionalEC.setCodArtFormlogMisumi((registroTEncargosCliente.getReferencia() != null && !("".equals(registroTEncargosCliente.getReferencia().toString())))?new Long(registroTEncargosCliente.getReferencia().toString()):null);
			pedidoAdicionalEC.setDenomArticulo(registroTEncargosCliente.getDescripcion());		
			pedidoAdicionalEC.setUnidServ(registroTEncargosCliente.getUnidadescaja() != null && !("".equals(registroTEncargosCliente.getUnidadescaja().toString()))?new Double(registroTEncargosCliente.getUnidadescaja().toString()):null);		
			pedidoAdicionalEC.setContactoCentro(registroTEncargosCliente.getContactoCentro());		
			pedidoAdicionalEC.setTelefonoCentro(registroTEncargosCliente.getTelefonoCentro());		
			pedidoAdicionalEC.setNombreCliente(registroTEncargosCliente.getNombreCliente());		
			pedidoAdicionalEC.setApellidoCliente(registroTEncargosCliente.getApellidoCliente());		
			pedidoAdicionalEC.setTelefonoCliente(registroTEncargosCliente.getTelefonoCliente());		
			pedidoAdicionalEC.setFechaHoraEncargo(registroTEncargosCliente.getFechaHoraEncargo());		
			pedidoAdicionalEC.setTipoEncargo(registroTEncargosCliente.getTipoEncargo());		
			pedidoAdicionalEC.setFechaVenta(registroTEncargosCliente.getFechaVenta());		
			pedidoAdicionalEC.setFechaVentaModificada(registroTEncargosCliente.getFechaVentaModificada());		
			pedidoAdicionalEC.setFechaInferior(registroTEncargosCliente.getFechaInferior());		
			pedidoAdicionalEC.setEspecificacion(registroTEncargosCliente.getEspecificacion());		
			pedidoAdicionalEC.setPesoDesde((registroTEncargosCliente.getPesoDesde() != null && !("".equals(registroTEncargosCliente.getPesoDesde().toString())))?new Double(registroTEncargosCliente.getPesoDesde().toString()):null);		
			pedidoAdicionalEC.setPesoHasta((registroTEncargosCliente.getPesoHasta() != null && !("".equals(registroTEncargosCliente.getPesoHasta().toString())))?new Double(registroTEncargosCliente.getPesoHasta().toString()):null);		
			pedidoAdicionalEC.setConfirmarEspecificaciones(registroTEncargosCliente.getConfirmarEspecificaciones());		
			pedidoAdicionalEC.setFaltaRef(registroTEncargosCliente.getFaltaRef());		
			pedidoAdicionalEC.setCambioRef(registroTEncargosCliente.getCambioRef());		
			pedidoAdicionalEC.setConfirmarPrecio(registroTEncargosCliente.getConfirmarPrecio());
			pedidoAdicionalEC.setCantEncargo((registroTEncargosCliente.getCantEncargo() != null && !("".equals(registroTEncargosCliente.getCantEncargo().toString())))?new Double(registroTEncargosCliente.getCantEncargo().toString()):null);
			pedidoAdicionalEC.setCantFinalCompra((registroTEncargosCliente.getCantFinalCompra() != null && !("".equals(registroTEncargosCliente.getCantFinalCompra().toString())))?new Double(registroTEncargosCliente.getCantFinalCompra().toString()):null);
			pedidoAdicionalEC.setCantServido((registroTEncargosCliente.getCantServido() != null && !("".equals(registroTEncargosCliente.getCantServido().toString())))?new Double(registroTEncargosCliente.getCantServido().toString()):null);
			pedidoAdicionalEC.setCantNoServido((registroTEncargosCliente.getCantNoServido() != null && !("".equals(registroTEncargosCliente.getCantNoServido().toString())))?new Double(registroTEncargosCliente.getCantNoServido().toString()):null);
			pedidoAdicionalEC.setEstado(registroTEncargosCliente.getEstado());
			pedidoAdicionalEC.setObservacionesMisumi(registroTEncargosCliente.getObservacionesMisumi());
			pedidoAdicionalEC.setCodigoPedidoInterno((registroTEncargosCliente.getCodigoPedidoInterno() != null && !("".equals(registroTEncargosCliente.getCodigoPedidoInterno().toString())))?registroTEncargosCliente.getCodigoPedidoInterno():null);
			pedidoAdicionalEC.setFlgModificable(registroTEncargosCliente.getFlgModificable());
			pedidoAdicionalEC.setTipoAprov(registroTEncargosCliente.getCodTpAprov());
			pedidoAdicionalEC.setVitrina(registroTEncargosCliente.getVitrina());
			pedidoAdicionalEC.setRelCompraVenta(registroTEncargosCliente.getRelCompraVenta());
			pedidoAdicionalEC.setCodigoError((registroTEncargosCliente.getCodigoError() != null && !("".equals(registroTEncargosCliente.getCodigoError().toString())))?new Long(registroTEncargosCliente.getCodigoError().toString()):null);
			pedidoAdicionalEC.setDescripcionError(registroTEncargosCliente.getDescripcionError());
			pedidoAdicionalEC.setNivel(registroTEncargosCliente.getNivel());
			pedidoAdicionalEC.setDescripcionGestadic(registroTEncargosCliente.getDescripcionGestadic());
			pedidoAdicionalEC.setEstadoGestadic(registroTEncargosCliente.getEstadoGestadic());
			pedidoAdicionalEC.setTxtDetalleGestadic(registroTEncargosCliente.getTxtDetalleGestadic());
			pedidoAdicionalEC.setTxtSituacionGestadic(registroTEncargosCliente.getTxtSituacionGestadic());
			
			//Formateo de fechas de pantalla
			if (registroTEncargosCliente.getFechaVenta()!=null){
				pedidoAdicionalEC.setFechaVentaPantalla(Utilidades.formatearFecha(registroTEncargosCliente.getFechaVenta()));
			}else{
				pedidoAdicionalEC.setFechaVentaPantalla("");
			}
			if (registroTEncargosCliente.getFechaVentaModificada()!=null){
				pedidoAdicionalEC.setFechaVentaModificadaPantalla(Utilidades.formatearFecha(registroTEncargosCliente.getFechaVentaModificada()));
			}else{
				pedidoAdicionalEC.setFechaVentaModificadaPantalla("");
			}
		}
		return pedidoAdicionalEC;
	}
}
