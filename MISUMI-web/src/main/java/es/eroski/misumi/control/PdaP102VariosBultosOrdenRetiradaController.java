package es.eroski.misumi.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.eroski.misumi.model.BultoCantidad;
import es.eroski.misumi.model.Devolucion;
import es.eroski.misumi.model.DevolucionCatalogoEstado;
import es.eroski.misumi.model.DevolucionLinea;
import es.eroski.misumi.model.DevolucionLineaModificada;
import es.eroski.misumi.model.TDevolucionBulto;
import es.eroski.misumi.model.TDevolucionLinea;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.service.iface.DevolucionLineaBultoCantidadService;
import es.eroski.misumi.service.iface.DevolucionService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;

@Controller
public class PdaP102VariosBultosOrdenRetiradaController extends pdaConsultasController{

	private static Logger logger = Logger.getLogger(PdaP102VariosBultosOrdenRetiradaController.class);

	@Autowired
	private DevolucionLineaBultoCantidadService devolucionLineaBultoCantidadService;
	
	@Autowired
	private DevolucionService devolucionesService;
	
	@RequestMapping(value = "/pdaP102VariosBultosOrdenRetirada",method = RequestMethod.GET)
	public String showForm( ModelMap model
						  , @Valid final Long codArt, @Valid final Long devolucionPantalla
						  , @Valid final Double stockDevueltoPantalla, @Valid final Long bultoPantalla
						  , @Valid final Double stockDevueltoOrigPantalla, @Valid final Long bultoPantallaOrig
						  , @Valid final Boolean variosBultosPantalla, @Valid final String estadoCerradoPantalla
						  , @Valid final String origenPantalla, @Valid final String selectProv
						  , HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultado = "pda_p102_variosBultosOrdenRetirada";
		List<TDevolucionBulto> listTDevolucionLinea = new ArrayList<TDevolucionBulto>(5);
		List<TDevolucionLinea> list = null;
		TDevolucionLinea devolucionLinea= new TDevolucionLinea();
				
		// Llamada para conseguir las devoluciones. 
		Devolucion devolucion = new Devolucion();
		devolucion.setDevolucion(new Long(devolucionPantalla));

		//Obtención de la devolución de la lista de devoluciones
		@SuppressWarnings("unchecked")
		List<Devolucion> listaDevolucion = (List<Devolucion>)session.getAttribute("listaDevolucion");
		devolucion = listaDevolucion.get(listaDevolucion.indexOf(devolucion));
		if(!variosBultosPantalla){
			if((stockDevueltoPantalla!=null && bultoPantalla!=null) || (stockDevueltoOrigPantalla!=null  && bultoPantallaOrig!=null)){
					
				if((stockDevueltoOrigPantalla!= null && bultoPantallaOrig!=null) && (!stockDevueltoPantalla.equals(stockDevueltoOrigPantalla) && !bultoPantalla.equals(bultoPantallaOrig))){
					this.devolucionLineaBultoCantidadService.deleteLineasTablaBultoCantidad(session.getId(), devolucionPantalla, codArt);
				}
				if((stockDevueltoPantalla!= null && bultoPantalla!=null) && (!stockDevueltoPantalla.equals(stockDevueltoOrigPantalla) && !bultoPantalla.equals(bultoPantallaOrig))){
					this.insertarTablaSesionLineaDevolucion( session.getId(), devolucionPantalla, codArt, stockDevueltoPantalla, bultoPantalla);
					this.actualizarDatosTablaSesionLineaBulto(devolucionPantalla, codArt,session.getId());
					DevolucionCatalogoEstado devolucionCatalogoEstadoActualizada = null;
					////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//Una vez tenemos la tabla temporal actualizada, obtenemos los elementos modificados.
					//listTDevolucionesModificadas -> ESTA LISTA SOLO CONTIENE 1 REGISTRO QUE SERÁ LA DE LA LINEA A MODIFICAR(RECUPERAR DE BBDD O DE devolucion) 
					//CON LA LISTA listaBultoCantidad RELLENA CON LOS DATOS INTRODUCIDOS EN LA PANTALLA
					List<DevolucionLinea> listTDevolucionesModificadas = this.devolucionesService.findLineasDevolucionEditadas(session.getId(),new Long("9"));		
					listTDevolucionesModificadas=this.rellenarEstructuraLineaBulto(listTDevolucionesModificadas, stockDevueltoPantalla, bultoPantalla,estadoCerradoPantalla);
					//Si hay líneas de devolución editadas, actualizamos esas líneas mediante el PLSQL, actualizamos la temporal con los códigos de error correspondientes
					//y obtenemos las líneas del grid actualizadas de la tabla temporal actualizada. Si no hay modificaciones, obtenemos la lista tal cual se encuentra
					//en la tabla temporal.
					//Se insertan las líneas de devolución modificadas únicamente en la lista de líneas de devolución de la lista.
					devolucion.setDevLineas(listTDevolucionesModificadas);
	
					//Actualizamos la devolución y obtenemos el resultado con las líneas de devolucion y sus códigos de error
					devolucionCatalogoEstadoActualizada = this.devolucionesService.actualizarDevolucion(devolucion);
	
					if(devolucionCatalogoEstadoActualizada != null && ((new Long("0")).equals(devolucionCatalogoEstadoActualizada.getpCodError()) || devolucionCatalogoEstadoActualizada.getpCodError() == null)){
						if(devolucionCatalogoEstadoActualizada.getListDevolucionEstado() != null && devolucionCatalogoEstadoActualizada.getListDevolucionEstado().size() > 0){
							if(devolucionCatalogoEstadoActualizada.getListDevolucionEstado().get(0).getListDevolucion() != null && devolucionCatalogoEstadoActualizada.getListDevolucionEstado().get(0).getListDevolucion().size() > 0){
								//Obtenemos la devolucion actualizada
								Devolucion devolucionActualizada = devolucionCatalogoEstadoActualizada.getListDevolucionEstado().get(0).getListDevolucion().get(0);
	
								if(new Long("0").equals(devolucionActualizada.getCodError()) || devolucionActualizada.getCodError() == null){
									//Por cada línea de la devolución si el codError es 9, significa que se ha actualizado bien, al no haber cambio en el código de error insertado en el PLSQL. En ese caso insertamos a las líneas el código de error 8 
									//para indicar que han pasado de modificadas a guardadas. En caso contrario, se inserta el código de error obtenido. Estos cambios, habrá que guardarlos en la tabla temporal para así tener actualizados los estados de
									//cada línea y dibujar bien el grid, por lo cual utilizaremos el método updateTablaSesionLineaDevolucion. 
	
									DevolucionLineaModificada devLineaModificada = null;
									List<DevolucionLineaModificada> devLineaModificadaActualizada = new ArrayList<DevolucionLineaModificada>();
									Long codError = null;
									for(DevolucionLinea devLinea:devolucionActualizada.getDevLineas()){		
										//Si coinciden en código de artículo estamos hablando de la misma línea de devolución
										if((new Long("0")).equals(devLinea.getCodError())){
											codError = new Long("8");
										}else{
											codError = devLinea.getCodError();
										}
										devLineaModificada = new DevolucionLineaModificada(devLinea.getBulto(), devLinea.getStockDevuelto(), devLinea.getStockActual(), codError, devLinea.getCodArticulo(), devLinea.getStockDevueltoBandejas());
										devLineaModificadaActualizada.add(devLineaModificada);
									}
									devolucionActualizada.setDevLineasModificadas(devLineaModificadaActualizada);
	
									//Más tarde se limpian los estados. De esta forma se evita que al actualizar dos veces se mantengan los errores y disquetes de los estados Anteriores y solo se visualicen
									//los nuevos.		
									this.devolucionesService.resetDevolEstados(session.getId());			
	
									//Actualizamos los códigos de error de las devoluciones. Además también actualizamos los datos de BULTO_ORI y STOCKDEVUELTO_ORI de la tabla temporal
									this.devolucionesService.updateTablaSesionLineaDevolucion(session.getId(),devolucionActualizada,true);									
	
								}else{
									String err = "ERR_ACT";
									model.addAttribute("actionP67Volver","pdaP63VariosBultosLinkVuelta.do");

									model.addAttribute("codErrorActualizar",err);
									resultado = "pda_p67_errorDevolucionLinea";
								}
							}else{
								String err = "ERR_ACT";
								model.addAttribute("actionP67Volver","pdaP63VariosBultosLinkVuelta.do");

								model.addAttribute("codErrorActualizar",err);
								resultado = "pda_p67_errorDevolucionLinea";
							}
						}else{
							String err = "ERR_ACT";
							model.addAttribute("actionP67Volver","pdaP63VariosBultosLinkVuelta.do");

							model.addAttribute("codErrorActualizar",err);
							resultado = "pda_p67_errorDevolucionLinea";
						}
					}else{
						String err = "ERR_ACT";
						model.addAttribute("actionP67Volver","pdaP63VariosBultosLinkVuelta.do");

						model.addAttribute("codErrorActualizar",err);
						resultado = "pda_p67_errorDevolucionLinea";
					}
				}
			}
		}
		
		
		//Refrescamos la lista de devoluciones con las modificaciones correspondientes y con el nuevo filtro
		list = this.devolucionesService.findLineasDevolucion(session.getId(),devolucion,null,codArt,null,null);
		//Obtenemos la lista de cantidades y bultos asociados a la referencia para mostrar en la pantalla
		list = this.devolucionLineaBultoCantidadService.cargarBultoCantidadLinea(session.getId(),list);
		listTDevolucionLinea=list.get(0).getListTDevolucionLinea();
		if(list.get(0).getListTDevolucionLinea() ==null || list.get(0).getListTDevolucionLinea().size() < 5){
			for(int i=listTDevolucionLinea.size();i<5;i++){
				TDevolucionBulto vacio=new TDevolucionBulto();
				listTDevolucionLinea.add(vacio);
			}
		}
		
		String listaBultos=this.devolucionLineaBultoCantidadService.cargarListaBultos(session.getId(),list.get(0));
		
		devolucionLinea.setCodArticulo(list.get(0).getCodArticulo());
		devolucionLinea.setDenominacion(list.get(0).getDenominacion());
		devolucionLinea.setFlgPesoVariable(list.get(0).getFlgPesoVariable());
		devolucionLinea.setEstructuraComercial(list.get(0).getEstructuraComercial());
		devolucionLinea.setProvrGen(list.get(0).getProvrGen());
		devolucionLinea.setProvrTrabajo(list.get(0).getProvrTrabajo());
		devolucionLinea.setListTDevolucionLinea(listTDevolucionLinea);
		model.addAttribute("devolucionCabecera", devolucion);
		model.addAttribute("devolucionLinea", devolucionLinea);
		model.addAttribute("listaBultos", listaBultos);
		session.setAttribute("devolucion", devolucion);
		model.addAttribute("pdaDatosCab", new PdaDatosCabecera());
		
		model.addAttribute("origenPantalla", origenPantalla);
		model.addAttribute("selectProv", selectProv);
		
		return resultado;
	}

	private void insertarTablaSesionLineaDevolucion(String idSesion, Long devolucion, Long codArt, Double stockDevueltoPantalla, Long bultoPantalla){
		List<TDevolucionBulto> listTDevolucionLineaBultoCantidad = new ArrayList<TDevolucionBulto>();
		try {
			this.devolucionLineaBultoCantidadService.deleteLineasTablaBultoCantidad(idSesion, devolucion, codArt);
			if(stockDevueltoPantalla!=null && bultoPantalla!=null){
				TDevolucionBulto nuevoRegistroBultoCantidad = null;
				nuevoRegistroBultoCantidad = new TDevolucionBulto();
				nuevoRegistroBultoCantidad.setIdSesion(idSesion);
				nuevoRegistroBultoCantidad.setFechaGen(new Date());
				nuevoRegistroBultoCantidad.setDevolucion(devolucion);
				nuevoRegistroBultoCantidad.setBulto(bultoPantalla);
				nuevoRegistroBultoCantidad.setCodArticulo(codArt);
				nuevoRegistroBultoCantidad.setBultoOri(bultoPantalla);
				nuevoRegistroBultoCantidad.setStock(stockDevueltoPantalla);
				nuevoRegistroBultoCantidad.setStockOri(stockDevueltoPantalla);
				nuevoRegistroBultoCantidad.setEstadoCerrado("N");
				nuevoRegistroBultoCantidad.setCodError(0L);
				nuevoRegistroBultoCantidad.setDescError(null);
				nuevoRegistroBultoCantidad.setCreationDate(new Date());
				listTDevolucionLineaBultoCantidad.add(nuevoRegistroBultoCantidad);
				this.devolucionLineaBultoCantidadService.insertAll(listTDevolucionLineaBultoCantidad);
			}
		} catch (Exception e) {
			logger.error("insertar Tabla Lista Bulto Cantidad= "+e.toString());
			e.printStackTrace();
		}	
	}

	@RequestMapping(value = "/pdaP102VariosBultosOrdenRetirada",method = RequestMethod.POST)
	public String processForm(@Valid final PdaDatosCabecera pdaDatosCab, ModelMap model, RedirectAttributes redirectAttributes,
			HttpSession session, HttpServletRequest request, @Valid TDevolucionLinea devolucionLinea,
			HttpServletResponse response) {

		String resultado = "redirect:pdaP63VariosBultosLinkVuelta.do";
		redirectAttributes.addAttribute("codArt", devolucionLinea.getCodArticulo());
		String accion = request.getParameter("action");

		String origenPantalla = request.getParameter("origenPantalla");
		String selectProv = request.getParameter("selProv");

		DevolucionLinea nuevoRegistroDevLinea = new DevolucionLinea();
		int idxNuevaDevLinea = 0;

		DevolucionCatalogoEstado devol = (DevolucionCatalogoEstado) session.getAttribute("devCatalogoEstado");
		Devolucion devolLinea = devol.getListDevolucionEstado().get(0).getListDevolucion().get(0);

		//Si se ha pulsado el botón actualizar
		if (accion!=null && accion.equals("save")){
			try {
				//Obtenemos la lista de cantidades y bultos asociados a la referencia para mostrar en la pantalla
				//llamamos para guardarlos nuevos registros en el procedimiento, cuando vuelva del procedimineto borramos todos los registros existentes asociados a 
				//la sesion, devolucion y codArt y luego insertamos los nuevos valores

				// Actualizar tablas de UNIDIN (T_DEVOLUCIONES y T_MIS_DEVOLUCIONES_BULTO)
				this.devolucionLineaBultoCantidadService.deleteLineasTablaBultoCantidad(session.getId(), devolucionLinea.getDevolucion(), devolucionLinea.getCodArticulo());
				insertarDatosTablaSesionLineaBulto(devolucionLinea,session.getId());
				actualizarDatosTablaSesionLineaBulto(devolucionLinea.getDevolucion(), devolucionLinea.getCodArticulo(),session.getId());
				
				// Actualizar datos en SIA.
				for (int i=0;i<5;i++){
					TDevolucionBulto tDevolucionBulto=devolucionLinea.getListTDevolucionLinea().get(i);
					if (tDevolucionBulto.getEstadoCerrado().equals("S")){
						Long bultos=this.devolucionLineaBultoCantidadService.existenBultosPorbultoProveedor(session.getId(),devolucionLinea.getDevolucion(),devolucionLinea.getProvrGen(),devolucionLinea.getProvrTrabajo(),tDevolucionBulto.getBulto());
						if (bultos>0L){
							this.devolucionLineaBultoCantidadService.actualizarEstadoBultoPorProveedor(session.getId(),tDevolucionBulto.getEstadoCerrado(),devolucionLinea.getDevolucion(),devolucionLinea.getProvrGen(),null,tDevolucionBulto.getBulto());
							this.devolucionLineaBultoCantidadService.procedimientoActualizarEstadoBultoPorProveedor(Constantes.CERRAR_BULTO,devolucionLinea.getDevolucion(),devolucionLinea.getProvrGen(),null,tDevolucionBulto.getBulto());
						}
					}
				}
				
				Devolucion devolucion = new Devolucion();
				devolucion.setDevolucion(devolucionLinea.getDevolucion());

				//Obtención de la devolución de la lista de devoluciones
				@SuppressWarnings("unchecked")
				List<Devolucion> listaDevolucion = (List<Devolucion>)session.getAttribute("listaDevolucion");
				devolucion = listaDevolucion.get(listaDevolucion.indexOf(devolucion));
				devolucion.setFlagHistorico(Constantes.DEVOLUCIONES_NO_MOSTRAR_HISTORICO);
				
				DevolucionCatalogoEstado devolucionCatalogoEstadoActualizada = null;
				//Una vez tenemos la tabla temporal actualizada, obtenemos los elementos modificados.
				//CON LA LISTA listaBultoCantidad RELLENA CON LOS DATOS INTRODUCIDOS EN LA PANTALLA
				List<DevolucionLinea> listTDevolucionesModificadas = this.devolucionesService.findLineasDevolucionEditadas(session.getId(),new Long("9"));		
				listTDevolucionesModificadas=this.rellenarEstructuraLineaBulto(listTDevolucionesModificadas, devolucionLinea);

				// Actualizar la variable de Sesión con los cambios realizados a nivel de Bultos.
				nuevoRegistroDevLinea.setCodArticulo(devolucionLinea.getCodArticulo());
				// Actualizar los datos de la lista DevLinea
				idxNuevaDevLinea=devolLinea.getDevLineas().indexOf(nuevoRegistroDevLinea);
				devolLinea.getDevLineas().set(idxNuevaDevLinea, listTDevolucionesModificadas.get(0));

				devol.getListDevolucionEstado().get(0).getListDevolucion().set(0, devolLinea);
				session.setAttribute("devCatalogoEstado", devol);

				//Si hay líneas de devolución editadas, actualizamos esas líneas mediante el PLSQL, actualizamos la temporal con los códigos de error correspondientes
				//y obtenemos las líneas del grid actualizadas de la tabla temporal actualizada. Si no hay modificaciones, obtenemos la lista tal cual se encuentra
				//en la tabla temporal.
				//Se insertan las líneas de devolución modificadas únicamente en la lista de líneas de devolución de la lista.
				devolucion.setDevLineas(listTDevolucionesModificadas);

				//Actualizamos la devolución y obtenemos el resultado con las líneas de devolucion y sus códigos de error
				devolucionCatalogoEstadoActualizada = this.devolucionesService.actualizarDevolucion(devolucion);

				if (devolucionCatalogoEstadoActualizada != null && ((new Long("0")).equals(devolucionCatalogoEstadoActualizada.getpCodError()) || devolucionCatalogoEstadoActualizada.getpCodError() == null)){
					if (devolucionCatalogoEstadoActualizada.getListDevolucionEstado() != null && devolucionCatalogoEstadoActualizada.getListDevolucionEstado().size() > 0){
						if (devolucionCatalogoEstadoActualizada.getListDevolucionEstado().get(0).getListDevolucion() != null && devolucionCatalogoEstadoActualizada.getListDevolucionEstado().get(0).getListDevolucion().size() > 0){
							//Obtenemos la devolucion actualizada
							Devolucion devolucionActualizada = devolucionCatalogoEstadoActualizada.getListDevolucionEstado().get(0).getListDevolucion().get(0);

							if (new Long("0").equals(devolucionActualizada.getCodError()) || devolucionActualizada.getCodError() == null){
								//Por cada línea de la devolución si el codError es 9, significa que se ha actualizado bien, al no haber cambio en el código de error insertado en el PLSQL. En ese caso insertamos a las líneas el código de error 8 
								//para indicar que han pasado de modificadas a guardadas. En caso contrario, se inserta el código de error obtenido. Estos cambios, habrá que guardarlos en la tabla temporal para así tener actualizados los estados de
								//cada línea y dibujar bien el grid, por lo cual utilizaremos el método updateTablaSesionLineaDevolucion. 

								DevolucionLineaModificada devLineaModificada = null;
								List<DevolucionLineaModificada> devLineaModificadaActualizada = new ArrayList<DevolucionLineaModificada>();
								Long codError = null;
								for (DevolucionLinea devLinea:devolucionActualizada.getDevLineas()){		
									//Si coinciden en código de artículo estamos hablando de la misma línea de devolución
									if ((new Long("0")).equals(devLinea.getCodError())){
										codError = new Long("8");
									}else{
										codError = devLinea.getCodError();
									}
									devLineaModificada = new DevolucionLineaModificada(devLinea.getBulto(), devLinea.getStockDevuelto(), devLinea.getStockActual(), codError, devLinea.getCodArticulo(), devLinea.getStockDevueltoBandejas());
									devLineaModificadaActualizada.add(devLineaModificada);
								}
								devolucionActualizada.setDevLineasModificadas(devLineaModificadaActualizada);

								//Más tarde se limpian los estados. De esta forma se evita que al actualizar dos veces se mantengan los errores y disquetes de los estados Anteriores y solo se visualicen
								//los nuevos.		
								this.devolucionesService.resetDevolEstados(session.getId());			

								//Actualizamos los códigos de error de las devoluciones. Además también actualizamos los datos de BULTO_ORI y STOCKDEVUELTO_ORI de la tabla temporal
								this.devolucionesService.updateTablaSesionLineaDevolucion(session.getId(),devolucionActualizada,true);									

							}else{
								String err = "ERR_ACT";
								model.addAttribute("actionP67Volver","pdaP63VariosBultosLinkVuelta.do");

								model.addAttribute("codErrorActualizar",err);
								resultado = "pda_p67_errorDevolucionLinea";
							}
						}else{
							String err = "ERR_ACT";
							model.addAttribute("actionP67Volver","pdaP63VariosBultosLinkVuelta.do");

							model.addAttribute("codErrorActualizar",err);
							resultado = "pda_p67_errorDevolucionLinea";
						}
					}else{
						String err = "ERR_ACT";
						model.addAttribute("actionP67Volver","pdaP63VariosBultosLinkVuelta.do");

						model.addAttribute("codErrorActualizar",err);
						resultado = "pda_p67_errorDevolucionLinea";
					}
				}else{
					String err = "ERR_ACT";
					model.addAttribute("actionP67Volver","pdaP63VariosBultosLinkVuelta.do");

					model.addAttribute("codErrorActualizar",err);
					resultado = "pda_p67_errorDevolucionLinea";
				}
			} catch (Exception e) {
				logger.error(StackTraceManager.getStackTrace(e));
			}
			
		}
		
		redirectAttributes.addAttribute("origenPantalla", origenPantalla);
		redirectAttributes.addAttribute("selectProv", selectProv);
		
		return resultado;
	}
	
	private List<DevolucionLinea> rellenarEstructuraLineaBulto(List<DevolucionLinea> listTDevolucionesModificadas,TDevolucionLinea devolucionLinea){
		List<BultoCantidad> listBultoCantidad=new ArrayList<BultoCantidad>();
		for(TDevolucionBulto tDevolucionBulto:devolucionLinea.getListTDevolucionLinea()){
			if(tDevolucionBulto.getBulto() != null && tDevolucionBulto.getStock() != null){
				BultoCantidad bultoCantidad=new BultoCantidad();
				bultoCantidad.setBulto(tDevolucionBulto.getBulto());
				bultoCantidad.setStock(tDevolucionBulto.getStock());
				bultoCantidad.setEstadoCerrado(tDevolucionBulto.getEstadoCerrado());
				listBultoCantidad.add(bultoCantidad);
			}
		}
		listTDevolucionesModificadas.get(0).setListaBultoCantidad(listBultoCantidad);
		return listTDevolucionesModificadas;
	}

	private void insertarDatosTablaSesionLineaBulto(TDevolucionLinea devolucionLinea, String idSesion){

		List<TDevolucionBulto> listTDevolucionLineaBultoCantidad = new ArrayList<TDevolucionBulto>();
		//Se inicializa el objeto. Cada objeto de este tipo será un registro de la tabla temporal
		TDevolucionBulto nuevoRegistroBultoCantidad = null;
		for (TDevolucionBulto bultoCantidad:devolucionLinea.getListTDevolucionLinea()){
			if(bultoCantidad.getBulto() != null && bultoCantidad.getStock() != null){
				nuevoRegistroBultoCantidad = new TDevolucionBulto();
				nuevoRegistroBultoCantidad.setIdSesion(idSesion);
				nuevoRegistroBultoCantidad.setFechaGen(new Date());
				nuevoRegistroBultoCantidad.setDevolucion(devolucionLinea.getDevolucion());
				nuevoRegistroBultoCantidad.setBulto(bultoCantidad.getBulto());
				nuevoRegistroBultoCantidad.setCodArticulo(devolucionLinea.getCodArticulo());
				nuevoRegistroBultoCantidad.setBultoOri(bultoCantidad.getBulto());
				nuevoRegistroBultoCantidad.setStock(bultoCantidad.getStock());
				nuevoRegistroBultoCantidad.setStockOri(bultoCantidad.getStock());
				nuevoRegistroBultoCantidad.setEstadoCerrado(bultoCantidad.getEstadoCerrado());
				nuevoRegistroBultoCantidad.setCodError(bultoCantidad.getCodError());
				nuevoRegistroBultoCantidad.setDescError(bultoCantidad.getDescError());
				nuevoRegistroBultoCantidad.setCreationDate(new Date());
				listTDevolucionLineaBultoCantidad.add(nuevoRegistroBultoCantidad);
			}
		}
		try {
			this.devolucionLineaBultoCantidadService.insertAll(listTDevolucionLineaBultoCantidad);
		} catch (Exception e) {
			logger.error("insertar Tabla Lista Bulto Cantidad= "+e.toString());
			e.printStackTrace();
		}	
	}
	
	private void actualizarDatosTablaSesionLineaBulto(Long devolucion, Long codArt, String idSesion) throws Exception{
		DevolucionLineaModificada devolucionLineaActualizar = new DevolucionLineaModificada();
		//Creamos dos nuevos objetos, para actualizar la devolución linea
		Devolucion devolucionActualizar = new Devolucion();

		//Creamos una lista para insertar la línea de devolución modificada
		List<DevolucionLineaModificada> devLineaLstModificada = new ArrayList<DevolucionLineaModificada>();

		//Insertamos en la línea el artículo al que corresponde esa línea y el estado modificado como código 9
		devolucionLineaActualizar.setCodError(new Long(9));
		devolucionLineaActualizar.setCodArticulo(codArt);				

		//Insertamos la línea de devolución modificada
		devLineaLstModificada.add(devolucionLineaActualizar);

		devolucionActualizar.setDevolucion(devolucion);	
		devolucionActualizar.setDevLineasModificadas(devLineaLstModificada);

		this.devolucionesService.updateTablaSesionLineaDevolucion(idSesion,devolucionActualizar,false);
	}
	
	private List<DevolucionLinea> rellenarEstructuraLineaBulto(List<DevolucionLinea> listTDevolucionesModificadas, Double stockDevueltoPantalla, Long bultoPantalla,String estadoCerradoPantalla){
		List<BultoCantidad> listBultoCantidad=new ArrayList<BultoCantidad>();
			BultoCantidad bultoCantidad=new BultoCantidad();
			bultoCantidad.setBulto(bultoPantalla);
			bultoCantidad.setStock(stockDevueltoPantalla);
			bultoCantidad.setEstadoCerrado(estadoCerradoPantalla);
			listBultoCantidad.add(bultoCantidad);
		listTDevolucionesModificadas.get(0).setListaBultoCantidad(listBultoCantidad);
		return listTDevolucionesModificadas;
	}
}