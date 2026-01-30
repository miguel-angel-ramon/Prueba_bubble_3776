package es.eroski.misumi.control;

import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.EncargoCliente;
import es.eroski.misumi.model.EncargosClienteLista;
import es.eroski.misumi.model.PedidoAdicionalEC;
import es.eroski.misumi.model.TEncargosClte;
import es.eroski.misumi.service.iface.GestionEncargosService;
import es.eroski.misumi.service.iface.PedidoAdicionalECService;
import es.eroski.misumi.service.iface.TEncargosClteService;
import es.eroski.misumi.util.Constantes;

@Controller
@RequestMapping("/modificarEncargoCliente")
public class P62ModificarEncargoClienteController {

	private static Logger logger = Logger.getLogger(P62ModificarEncargoClienteController.class);

	@Resource
	private MessageSource messageSource;
	
	@Autowired
	private TEncargosClteService tEncargosClteService;
	
	@Autowired
	private PedidoAdicionalECService pedidoAdicionalECService;
	
	@Autowired
	private GestionEncargosService gestionEncargosService;

	@RequestMapping(value = "/modifyPedido", method = RequestMethod.POST)
	public @ResponseBody PedidoAdicionalEC modifyPedido(@RequestBody PedidoAdicionalEC pedidoAdicionalEC,
			HttpServletResponse response, HttpSession session) throws Exception {

		Locale locale = LocaleContextHolder.getLocale();
		PedidoAdicionalEC resultado = new PedidoAdicionalEC();
		try{
	        //Reseteamos todos los posibles errores previos.
			this.resetearErroresCliente(session.getId(), pedidoAdicionalEC.getCodLoc());
	        
			if (pedidoAdicionalEC != null){
				TEncargosClte registroBuscado = new TEncargosClte();
				registroBuscado.setIddsesion(session.getId());
				registroBuscado.setLocalizador(pedidoAdicionalEC.getLocalizador());
				
				TEncargosClte encargosClte = this.tEncargosClteService.findOne(registroBuscado);
				//Actualizamos el registro obtenido de la tabla con los datos modificados en pantalla
				if ("S".equals(encargosClte.getFlgModificable())){
					encargosClte.setCantEncargo(pedidoAdicionalEC.getCantEncargo());
					encargosClte.setPesoDesde(pedidoAdicionalEC.getPesoDesde());
					encargosClte.setPesoHasta(pedidoAdicionalEC.getPesoHasta());
					encargosClte.setEspecificacion(pedidoAdicionalEC.getEspecificacion());
					encargosClte.setFechaVenta(pedidoAdicionalEC.getFechaVenta());
					//Si se modifica la fecha de venta se pone la fecha de venta modificada al mismo valor
					if (encargosClte.getFechaVenta() != pedidoAdicionalEC.getFechaVenta()){
						encargosClte.setFechaVentaModificada(pedidoAdicionalEC.getFechaVenta());
					}
					if (pedidoAdicionalEC.getFechaVenta().before(pedidoAdicionalEC.getPrimeraFechaEntrega())){
						encargosClte.setFechaInferior("S");
					} else {
						encargosClte.setFechaInferior("N");
					}
				}
				encargosClte.setNombreCliente(pedidoAdicionalEC.getNombreCliente());
				encargosClte.setApellidoCliente(pedidoAdicionalEC.getApellidoCliente());
				encargosClte.setTelefonoCliente(pedidoAdicionalEC.getTelefonoCliente());
				encargosClte.setContactoCentro(pedidoAdicionalEC.getContactoCentro());
				encargosClte.setTipoEncargo(pedidoAdicionalEC.getTipoEncargo());
				encargosClte.setConfirmarPrecio("N");
	
				EncargosClienteLista encargosLista = this.gestionEncargosService.modificarEncargo(encargosClte);
				if (encargosLista != null && encargosLista.getDatos() != null && encargosLista.getDatos().size()>0){
					//Se refrescan los datos de codigo de error y los posibles campos modificados 
					EncargoCliente encargoClienteModificado = encargosLista.getDatos().get(0);
					encargosClte.setCodigoError(encargoClienteModificado.getCodError());
					encargosClte.setDescripcionError(encargoClienteModificado.getDescError());
					
					encargosClte.setCantEncargo(encargoClienteModificado.getCantidadEncargo());
					encargosClte.setContactoCentro(encargoClienteModificado.getContactoCentro());
					encargosClte.setTelefonoCentro(encargoClienteModificado.getTelefonoCentro());
					encargosClte.setNombreCliente(encargoClienteModificado.getNombreCliente());
					encargosClte.setApellidoCliente(encargoClienteModificado.getApellidoCliente());
					encargosClte.setTelefonoCliente(encargoClienteModificado.getTelefonoCliente());
					encargosClte.setTipoEncargo(encargoClienteModificado.getTipoEncargo());
					encargosClte.setFechaVenta(encargoClienteModificado.getFechaVenta());
					encargosClte.setFechaVentaModificada(encargoClienteModificado.getFechaVentaModificada());
					encargosClte.setEspecificacion(encargoClienteModificado.getEspecificacion());
					encargosClte.setPesoDesde(encargoClienteModificado.getPesoDesde());
					encargosClte.setPesoHasta(encargoClienteModificado.getPesoHasta());

					if (!(new Long(0).equals(encargoClienteModificado.getCodError()))){
						encargosClte.setCodigoError(new Long(Constantes.MODIFICADO_ERRONEO_PANTALLA));
						if (encargoClienteModificado.getDescError() != null && !"".equals(encargoClienteModificado.getDescError().trim())){
							encargosClte.setDescripcionError(encargoClienteModificado.getDescError());
						}
						else{
							encargosClte.setDescripcionError(this.messageSource.getMessage("p62_modificarPedidoEncargoCliente.errorModificar",null, locale));
						}
					}else{
						encargosClte.setCodigoError(new Long(Constantes.MODIFICADO_CORRECTO_PANTALLA));
						encargosClte.setDescripcionError("");
					}
					this.tEncargosClteService.updateEncargo(encargosClte);	
					this.tEncargosClteService.updateErrorEncargo(encargosClte);	

					//Volvemos a obtener PedidoAdicionalEC para enviarlo como respuesta de la llamada
					resultado = this.pedidoAdicionalECService.obtenerTablaSesionECRegistro(session.getId(), pedidoAdicionalEC.getLocalizador());
				}else{
					resultado.setCodigoError(new Long(Constantes.MODIFICADO_ERRONEO_PANTALLA));
					resultado.setDescripcionError(this.messageSource.getMessage("p62_modificarPedidoEncargoCliente.errorModificar",null, locale));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			//Si ha fallado algo debemos enviar un error al JSP para que lo muestre por pantalla.
			resultado.setCodigoError(new Long(Constantes.MODIFICADO_ERRONEO_PANTALLA));
			resultado.setDescripcionError(this.messageSource.getMessage("p62_modificarPedidoEncargoCliente.errorModificar",null, locale));
		}
		return resultado;
	}
	
	private void resetearErroresCliente(String idSesion, Long codCentro){
		
		TEncargosClte tEncargosClte = new TEncargosClte();
		tEncargosClte.setIddsesion(idSesion);
		tEncargosClte.setCentro(codCentro);
		
		try {
			this.tEncargosClteService.updateErrores(tEncargosClte);
		} catch (Exception e) {
			logger.error("resetearErrores="+e.toString());
			e.printStackTrace();
		}
	}

}
