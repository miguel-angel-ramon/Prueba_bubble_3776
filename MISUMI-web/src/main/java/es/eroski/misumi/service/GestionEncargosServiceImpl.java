package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.GestionEncargosDao;
import es.eroski.misumi.model.EncargoCliente;
import es.eroski.misumi.model.EncargosClienteLista;
import es.eroski.misumi.model.TEncargosClte;
import es.eroski.misumi.service.iface.GestionEncargosService;

@Service(value = "GestionEncargosService")
public class GestionEncargosServiceImpl implements GestionEncargosService{
	
	@Autowired
	private GestionEncargosDao gestionEncargosDao;
	
	public EncargosClienteLista borrarEncargos(List<TEncargosClte> encargosClte) throws Exception{
		
		List<EncargoCliente> listaActualizacion = new ArrayList<EncargoCliente>();
		
		if (encargosClte != null && encargosClte.size() > 0){
			for (int i=0; i<encargosClte.size(); i++){
				EncargoCliente encargoCliente = new EncargoCliente();
				encargoCliente.setLocalizador(encargosClte.get(i).getLocalizador());
				encargoCliente.setCodLoc(encargosClte.get(i).getCentro());
				encargoCliente.setCodArticulo(encargosClte.get(i).getReferencia());
				encargoCliente.setEstado("ANULADO");
				
				listaActualizacion.add(encargoCliente);
			}			
			return this.gestionEncargosDao.gestionEncargos(listaActualizacion);
		}else{
			return new EncargosClienteLista();
		}
	}

	public EncargosClienteLista modificarEncargo(TEncargosClte encargosClte) throws Exception{
		
		List<EncargoCliente> listaActualizacion = new ArrayList<EncargoCliente>();
		
		if (encargosClte != null){
			EncargoCliente encargoCliente = new EncargoCliente();
			encargoCliente.setLocalizador(encargosClte.getLocalizador());
			encargoCliente.setCodLoc(encargosClte.getCentro());
			encargoCliente.setCodArticulo(encargosClte.getReferencia());
			encargoCliente.setDescripcionArt(encargosClte.getDescripcion());
			encargoCliente.setFechaHoraEncargo(encargosClte.getFechaHoraEncargo());
			encargoCliente.setContactoCentro(encargosClte.getContactoCentro());
			Long telefono = encargoCliente.getCodLoc() + 40000;
			encargoCliente.setTelefonoCentro(telefono.toString());
			encargoCliente.setNombreCliente(encargosClte.getNombreCliente());
			encargoCliente.setApellidoCliente(encargosClte.getApellidoCliente());
			encargoCliente.setTelefonoCliente(encargosClte.getTelefonoCliente());
			encargoCliente.setTipoEncargo(encargosClte.getTipoEncargo());
			encargoCliente.setFechaVenta(encargosClte.getFechaVenta());
			encargoCliente.setFechaVentaModificada(encargosClte.getFechaVentaModificada());
			if ("S".equals(encargosClte.getFechaInferior())){
				encargoCliente.setFechaInferior(true);
			} else {
				encargoCliente.setFechaInferior(false);
			}
			encargoCliente.setEspecificacion(encargosClte.getEspecificacion());
			
			//Comprobación de modificación de pesos
			String area = (encargosClte.getArea() != null && !"".equals(encargosClte.getArea())? new Integer(encargosClte.getArea().substring(0, encargosClte.getArea().indexOf('-'))).toString():"");
			String seccion = (encargosClte.getSeccion() != null && !"".equals(encargosClte.getSeccion())? new Integer(encargosClte.getSeccion().substring(0, encargosClte.getSeccion().indexOf('-'))).toString():"");
			String categoria = (encargosClte.getCategoria() != null && !"".equals(encargosClte.getCategoria())? new Integer(encargosClte.getCategoria().substring(0, encargosClte.getCategoria().indexOf('-'))).toString():"");
			String subcategoria = (encargosClte.getSubcategoria() != null && !"".equals(encargosClte.getSubcategoria())? new Integer(encargosClte.getSubcategoria().substring(0, encargosClte.getSubcategoria().indexOf('-'))).toString():"");
			if ((area.equals("1") && seccion.equals("7") && categoria.equals("14")) ||
				(area.equals("1") && seccion.equals("2") && categoria.equals("61") && subcategoria.equals("7")) ||
				(area.equals("1") && seccion.equals("2") && categoria.equals("62") && subcategoria.equals("6")) ||
				(area.equals("1") && seccion.equals("2") && categoria.equals("63") && subcategoria.equals("6")) ||
				(area.equals("1") && seccion.equals("2") && categoria.equals("64") && subcategoria.equals("5")) ||
				(area.equals("1") && seccion.equals("2") && categoria.equals("65") && subcategoria.equals("12"))
				) {
					if (null != encargosClte.getPesoDesde()) {
						encargoCliente.setPesoDesde(encargosClte.getPesoDesde());
						if (null != encargosClte.getPesoHasta()){
							encargoCliente.setPesoHasta(encargosClte.getPesoHasta());
						} else {
							encargoCliente.setPesoHasta(encargosClte.getPesoDesde());
						}
					}

			} else {
				encargoCliente.setPesoDesde(null);
				encargoCliente.setPesoHasta(null);
			}

			if ("E".equals(encargosClte.getTipoEncargo())){
				encargoCliente.setConfirmarEspecificacion(true);
			} else {
				encargoCliente.setConfirmarEspecificacion(false);
			}
			if ("S".equals(encargosClte.getFaltaRef())){
				encargoCliente.setFaltaReferencia(true);
			} else {
				encargoCliente.setFaltaReferencia(false);
			}
			encargoCliente.setCambioReferencia("S".equals(encargosClte.getCambioRef()));
			encargoCliente.setConfirmarPrecio(encargosClte.getConfirmarPrecio());		
			encargoCliente.setCantidadEncargo(encargosClte.getCantEncargo());
			encargoCliente.setCantidadFinalCompra(encargosClte.getCantFinalCompra());
			encargoCliente.setCantidadServida(encargosClte.getCantServido());
			encargoCliente.setCantidadNoServida(encargosClte.getCantNoServido());
			encargoCliente.setUnidadesCaja(encargosClte.getUnidadescaja());
			encargoCliente.setEstado(encargosClte.getEstado());
			encargoCliente.setObservacionesRechazo(encargosClte.getObservacionesMisumi());
			encargoCliente.setCodigoPedidoInterno(encargosClte.getCodigoPedidoInterno()!=null?encargosClte.getCodigoPedidoInterno().toString():null);
				
			listaActualizacion.add(encargoCliente);
					
			return this.gestionEncargosDao.gestionEncargos(listaActualizacion);
		}else{
			return new EncargosClienteLista();
		}
	}

}
