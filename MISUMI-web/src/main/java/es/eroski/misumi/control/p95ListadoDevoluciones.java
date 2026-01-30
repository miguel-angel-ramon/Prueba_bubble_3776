package es.eroski.misumi.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.eroski.misumi.model.BultoCantidad;
import es.eroski.misumi.model.Devolucion;
import es.eroski.misumi.model.DevolucionCatalogoEstado;
import es.eroski.misumi.model.DevolucionLinea;
import es.eroski.misumi.model.TDevolucionBulto;
import es.eroski.misumi.model.TDevolucionLinea;
import es.eroski.misumi.model.User;
import es.eroski.misumi.service.iface.DevolucionLineaBultoCantidadService;
import es.eroski.misumi.service.iface.DevolucionService;

@Controller
@RequestMapping("/p95ListadoDevoluciones")
public class p95ListadoDevoluciones {
	
	private static Logger logger = Logger.getLogger(p95ListadoDevoluciones.class);
	
	@Autowired
	private DevolucionService devolucionesService;
	
	@Autowired
	private DevolucionLineaBultoCantidadService devolucionLineaBultoCantidadService;

	@RequestMapping(value="/getPdfDevolucionFinCampana",method = RequestMethod.GET)
	public String getViewAsPdfFinCampana(@RequestParam(value = "localizador", required = false, defaultValue = "1") String localizador, @RequestParam(value = "flgHistorico", required = false, defaultValue = "1") String flgHistorico, String tipoImpresion, String filtroProveedor, ModelMap model,HttpServletResponse response,
			HttpSession session) throws Exception{

		
		
		Devolucion devolucion = new Devolucion();
		devolucion.setLocalizador(localizador);
		
		devolucion.setFlagHistorico(flgHistorico);
		
		User userSession = (User)session.getAttribute("user");
		devolucion.setCentro(userSession.getCentro().getCodCentro()); 
		devolucion.setTipoPaginacionPDF(tipoImpresion);
		devolucion.setFiltroProveedor(filtroProveedor);
		
		//Obtiene una devolución con sus líneas de devolución
		DevolucionCatalogoEstado devolucionCatalogo = devolucionesService.cargarAllDevoluciones(devolucion); 

		//Se mira que existe la lista de líneas de devolución
		if(devolucionCatalogo != null){
			if(devolucionCatalogo.getListDevolucionEstado() != null && devolucionCatalogo.getListDevolucionEstado().size() > 0){
				if(devolucionCatalogo.getListDevolucionEstado().get(0).getListDevolucion() != null && devolucionCatalogo.getListDevolucionEstado().get(0).getListDevolucion().size() > 0){
					devolucion = devolucionCatalogo.getListDevolucionEstado().get(0).getListDevolucion().get(0);
					
					if(devolucion.getDevLineas() != null && devolucion.getDevLineas().size() > 0){																							

						//Se obtiene la lista de líneas de devolución
						List<DevolucionLinea> listDevolucionLinea = (List<DevolucionLinea>) devolucion.getDevLineas();
						
						// Cuando hacemos la primera búsqueda eliminamos siempre los
						// registros guardados anteriormente. Se eliminarán todos los
						//registros de todas las sesiones que lleven más de un día en
						//la tabla.
						this.eliminarTablaSesionHistorico();

						// Insertar tabla temporal T_DEVOLUCIONES
						// A partir de la lista obtenida tenemos que insertar en la
						// tabla temporal los registros obtenidos,
						// borrando previamente los posibles registros almacenados.
						this.eliminarTablaSesion(session.getId());

						//Se inserta en una tabla temporal cada línea de devolución. Se devuelve una lista de TDevolucionLinea tratada
						//a partir de la lista obtenida en el PLSQL. Así evitamos tirar de la tabla temporal al ya tener los datos.
						this.insertarTablaSesionLineaDevolucion(listDevolucionLinea, session.getId(),devolucion.getDevolucion());																       		

					}
				}
			}
		}
		
		devolucion.setTipoPaginacionPDF(tipoImpresion);
		devolucion.setFiltroProveedor(filtroProveedor);
		if (devolucion != null){
			model.addAttribute("devolucion", devolucion);
		}

		if (localizador != null){
			model.addAttribute("idSession", localizador);
		}
		
		if (tipoImpresion != null){
			model.addAttribute("tipoImpresion", tipoImpresion);
		}
		
		if (session.getId() != null){
			model.addAttribute("idSession", session.getId());
		}

		/*
		if (tipoImpresion.equals(Constantes.PAGINACION_POR_REFERENCIA)) {
			return "pdfInformeDevolucionFinCampanaReferenciaView";
		} else {
			if (tipoImpresion.equals(Constantes.PAGINACION_POR_PROVEEDOR)) {
				return "pdfInformeDevolucionFinCampanaProveedorView";
			} else {
				return "pdfInformeDevolucionFinCampanaTodoView";
			}
		}
		*/
		return "pdfInformeDevolucionFinCampanaProveedorView";
	}

	@RequestMapping(value = "/getPdfDevolucionOrdenRetirada",method = RequestMethod.GET)
	public String getViewAsPdfOrdenRetirada(@RequestParam(value = "localizador", required = false, defaultValue = "1") String localizador, @RequestParam(value = "flgHistorico", required = false, defaultValue = "1") String flgHistorico, String tipoImpresion, String filtroProveedor, ModelMap model,HttpServletResponse response,
			HttpSession session) throws Exception{

		Devolucion devolucion = new Devolucion();
		devolucion.setLocalizador(localizador);
		
		devolucion.setFlagHistorico(flgHistorico);
		
		User userSession = (User)session.getAttribute("user");
		devolucion.setCentro(userSession.getCentro().getCodCentro());
		devolucion.setTipoPaginacionPDF(tipoImpresion);
		devolucion.setFiltroProveedor(filtroProveedor);
		
		//Obtiene una devolución con sus líneas de devolución
		DevolucionCatalogoEstado devolucionCatalogo = devolucionesService.cargarAllDevoluciones(devolucion); 

		//Se mira que existe la lista de líneas de devolución
		if(devolucionCatalogo != null){
			if(devolucionCatalogo.getListDevolucionEstado() != null && devolucionCatalogo.getListDevolucionEstado().size() > 0){
				if(devolucionCatalogo.getListDevolucionEstado().get(0).getListDevolucion() != null && devolucionCatalogo.getListDevolucionEstado().get(0).getListDevolucion().size() > 0){
					devolucion = devolucionCatalogo.getListDevolucionEstado().get(0).getListDevolucion().get(0);
					
					if(devolucion.getDevLineas() != null && devolucion.getDevLineas().size() > 0){																							

						//Se obtiene la lista de líneas de devolución
						List<DevolucionLinea> listDevolucionLinea = (List<DevolucionLinea>) devolucion.getDevLineas();
						
						// Cuando hacemos la primera búsqueda eliminamos siempre los
						// registros guardados anteriormente. Se eliminarán todos los
						//registros de todas las sesiones que lleven más de un día en
						//la tabla.
						this.eliminarTablaSesionHistorico();

						// Insertar tabla temporal T_DEVOLUCIONES
						// A partir de la lista obtenida tenemos que insertar en la
						// tabla temporal los registros obtenidos,
						// borrando previamente los posibles registros almacenados.
						this.eliminarTablaSesion(session.getId());

						//Se inserta en una tabla temporal cada línea de devolución. Se devuelve una lista de TDevolucionLinea tratada
						//a partir de la lista obtenida en el PLSQL. Así evitamos tirar de la tabla temporal al ya tener los datos.
						this.insertarTablaSesionLineaDevolucion(listDevolucionLinea, session.getId(),devolucion.getDevolucion());																       		

					}

					
				}
			}
		}
		
		devolucion.setTipoPaginacionPDF(tipoImpresion);
		devolucion.setFiltroProveedor(filtroProveedor);
		if (devolucion != null){
			model.addAttribute("devolucion", devolucion);
		}

		if (localizador != null){
			model.addAttribute("idSession", localizador);
		}
		
		if (tipoImpresion != null){
			model.addAttribute("tipoImpresion", tipoImpresion);
		}
		
		
		if (session.getId() != null){
			model.addAttribute("idSession", session.getId());
		}

		/*
		if (tipoImpresion.equals(Constantes.PAGINACION_POR_REFERENCIA)) {
			return "pdfInformeDevolucionOrdenRetiradaReferenciaView";
		} else {
			if (tipoImpresion.equals(Constantes.PAGINACION_POR_PROVEEDOR)) {
				return "pdfInformeDevolucionOrdenRetiradaProveedorView";
			} else {
				return "pdfInformeDevolucionOrdenRetiradaTodoView";
			}
		}
		*/
		return "pdfInformeDevolucionOrdenRetiradaProveedorView";
		
	}
	
	
	@RequestMapping(value="/getPdfDevolucionCreadaCentro",method = RequestMethod.GET)
	public String getViewAsPdfCreadaCentro(@RequestParam(value = "localizador", required = false, defaultValue = "1") String localizador, @RequestParam(value = "flgHistorico", required = false, defaultValue = "1") String flgHistorico, String tipoImpresion, String filtroProveedor, ModelMap model,HttpServletResponse response,
			HttpSession session) throws Exception{

		
		
		Devolucion devolucion = new Devolucion();
		devolucion.setLocalizador(localizador);
		
		devolucion.setFlagHistorico(flgHistorico);
		
		User userSession = (User)session.getAttribute("user");
		devolucion.setCentro(userSession.getCentro().getCodCentro()); 
		devolucion.setTipoPaginacionPDF(tipoImpresion);
		devolucion.setFiltroProveedor(filtroProveedor);
		
		//Obtiene una devolución con sus líneas de devolución
		DevolucionCatalogoEstado devolucionCatalogo = devolucionesService.cargarAllDevoluciones(devolucion); 

		//Se mira que existe la lista de líneas de devolución
		if(devolucionCatalogo != null){
			if(devolucionCatalogo.getListDevolucionEstado() != null && devolucionCatalogo.getListDevolucionEstado().size() > 0){
				if(devolucionCatalogo.getListDevolucionEstado().get(0).getListDevolucion() != null && devolucionCatalogo.getListDevolucionEstado().get(0).getListDevolucion().size() > 0){
					devolucion = devolucionCatalogo.getListDevolucionEstado().get(0).getListDevolucion().get(0);
					
					if(devolucion.getDevLineas() != null && devolucion.getDevLineas().size() > 0){																							

						//Se obtiene la lista de líneas de devolución
						List<DevolucionLinea> listDevolucionLinea = (List<DevolucionLinea>) devolucion.getDevLineas();
						
						// Cuando hacemos la primera búsqueda eliminamos siempre los
						// registros guardados anteriormente. Se eliminarán todos los
						//registros de todas las sesiones que lleven más de un día en
						//la tabla.
						this.eliminarTablaSesionHistorico();

						// Insertar tabla temporal T_DEVOLUCIONES
						// A partir de la lista obtenida tenemos que insertar en la
						// tabla temporal los registros obtenidos,
						// borrando previamente los posibles registros almacenados.
						this.eliminarTablaSesion(session.getId());

						//Se inserta en una tabla temporal cada línea de devolución. Se devuelve una lista de TDevolucionLinea tratada
						//a partir de la lista obtenida en el PLSQL. Así evitamos tirar de la tabla temporal al ya tener los datos.
						this.insertarTablaSesionLineaDevolucion(listDevolucionLinea, session.getId(),devolucion.getDevolucion());																       		

					}
				}
			}
		}
		
		devolucion.setTipoPaginacionPDF(tipoImpresion);
		devolucion.setFiltroProveedor(filtroProveedor);
		if (devolucion != null){
			model.addAttribute("devolucion", devolucion);
		}

		if (localizador != null){
			model.addAttribute("idSession", localizador);
		}
		
		if (tipoImpresion != null){
			model.addAttribute("tipoImpresion", tipoImpresion);
		}
		
		if (session.getId() != null){
			model.addAttribute("idSession", session.getId());
		}

		return "pdfInformeDevolucionCreadaCentroProveedorView";
	}

	@RequestMapping(value = "/getPdfDevolucionOrdenRecogida")//, method = RequestMethod.POST)
	public String getViewAsPdfOrdenRecogida(@RequestParam(value = "localizador", required = false, defaultValue = "1") String localizador
											, @RequestParam(value = "flgHistorico", required = false, defaultValue = "1") String flgHistorico
											, ModelMap model,HttpServletResponse response
											, HttpSession session) throws Exception{

		Devolucion devolucion = new Devolucion();
		devolucion.setLocalizador(localizador);
		
		devolucion.setFlagHistorico(flgHistorico);
		
		User userSession = (User)session.getAttribute("user");
		devolucion.setCentro(userSession.getCentro().getCodCentro());

		//Obtiene una devolución con sus líneas de devolución
		DevolucionCatalogoEstado devolucionCatalogo = devolucionesService.cargarAllDevoluciones(devolucion); 

		//Se mira que existe la lista de líneas de devolución
		if(devolucionCatalogo != null){
			if(devolucionCatalogo.getListDevolucionEstado() != null && devolucionCatalogo.getListDevolucionEstado().size() > 0){
				if(devolucionCatalogo.getListDevolucionEstado().get(0).getListDevolucion() != null && devolucionCatalogo.getListDevolucionEstado().get(0).getListDevolucion().size() > 0){
					devolucion = devolucionCatalogo.getListDevolucionEstado().get(0).getListDevolucion().get(0);
				
					if(devolucion.getDevLineas() != null && devolucion.getDevLineas().size() > 0){																							

						//Se obtiene la lista de líneas de devolución
						List<DevolucionLinea> listDevolucionLinea = (List<DevolucionLinea>) devolucion.getDevLineas();
						
						// Cuando hacemos la primera búsqueda eliminamos siempre los
						// registros guardados anteriormente. Se eliminarán todos los
						//registros de todas las sesiones que lleven más de un día en
						//la tabla.
						this.eliminarTablaSesionHistorico();

						// Insertar tabla temporal T_DEVOLUCIONES
						// A partir de la lista obtenida tenemos que insertar en la
						// tabla temporal los registros obtenidos,
						// borrando previamente los posibles registros almacenados.
						this.eliminarTablaSesion(session.getId());

						//Se inserta en una tabla temporal cada línea de devolución. Se devuelve una lista de TDevolucionLinea tratada
						//a partir de la lista obtenida en el PLSQL. Así evitamos tirar de la tabla temporal al ya tener los datos.
						this.insertarTablaSesionLineaDevolucion(listDevolucionLinea, session.getId(),devolucion.getDevolucion());																       		

					}

				}
			}
		}
		
		if (devolucion != null){
			model.addAttribute("devolucion", devolucion);
		}

		if (localizador != null){
			model.addAttribute("idSession", localizador);
		}
		
		if (session.getId() != null){
			model.addAttribute("idSession", session.getId());
		}

		return "pdfInformeDevOrdenRecogidaProveedorView";
	}
	
	
	//Método que sirve para guardar cada línea de devolución en un registro de una tabla temporal llamada T_DEVOLUCIONES
	//Esa tabla además de contener como columnas los atributos del objeto DevolucionLinea, contiene el id de la sesión
	//del usuario, la fecha de creación y un código de devolución. Con la columna del id de sesión es posible que cada
	//usuario tenga sus datos en la tabla temporal y así no ver los de los demás, además se utilizará para borrar los
	//datos de la tabla temporal de su sesión cuando sea necesario. El campo de la fecha de creación sirve para eliminar los
	//registros que lleven guardados X tiempo y sean considerados como basura en la tabla temporal. El campo de devolución
	//se utilizará para saber cada línea de devolución a que devolución pertenece.
	private void insertarTablaSesionLineaDevolucion(List<DevolucionLinea> listDevolucionLinea, String idSesion, Long devolucion){

		//Nos recorremos la lista para generar la lista que debemos enviar al servicio para que inserte en la tabla temporal.
		List<TDevolucionLinea> listTDevolucionLinea = new ArrayList<TDevolucionLinea>();

		//Definimos el puntero del registro a guardar en la bd
		TDevolucionLinea nuevoRegistro = null;

		for (DevolucionLinea devolLinea:listDevolucionLinea){
			List<TDevolucionBulto> listTDevolucionLineaBultoCantidad = new ArrayList<TDevolucionBulto>();
			//Se inicializa el objeto. Cada objeto de este tipo será un registro de la tabla temporal
			TDevolucionBulto nuevoRegistroBultoCantidad = null;
			for (BultoCantidad bultoCantidad:devolLinea.getListaBultoCantidad()){
				nuevoRegistroBultoCantidad = new TDevolucionBulto();
				nuevoRegistroBultoCantidad.setIdSesion(idSesion);
				nuevoRegistroBultoCantidad.setFechaGen(new Date());
				nuevoRegistroBultoCantidad.setDevolucion(devolucion);
				nuevoRegistroBultoCantidad.setBulto(bultoCantidad.getBulto());
				nuevoRegistroBultoCantidad.setCodArticulo(devolLinea.getCodArticulo());
				nuevoRegistroBultoCantidad.setBultoOri(bultoCantidad.getBulto());
				nuevoRegistroBultoCantidad.setStock(bultoCantidad.getStock());
				nuevoRegistroBultoCantidad.setStockOri(bultoCantidad.getStock());
				nuevoRegistroBultoCantidad.setEstadoCerrado(bultoCantidad.getEstadoCerrado());
				nuevoRegistroBultoCantidad.setCodError(bultoCantidad.getCodError());
				nuevoRegistroBultoCantidad.setDescError(bultoCantidad.getDescError());
				nuevoRegistroBultoCantidad.setCreationDate(new Date());
				listTDevolucionLineaBultoCantidad.add(nuevoRegistroBultoCantidad);
			}
			try {
				this.devolucionLineaBultoCantidadService.insertAll(listTDevolucionLineaBultoCantidad);
			} catch (Exception e) {
				logger.error("insertar Tabla Lista Bulto Cantidad= "+e.toString());
				e.printStackTrace();
			}
			//Se inicializa el objeto. Cada objeto de este tipo será un registro de la tabla temporal
			nuevoRegistro = new TDevolucionLinea();

			//Se rellena el objeto
			nuevoRegistro.setIdSesion(idSesion);
			nuevoRegistro.setCreationDate(new Date());
			nuevoRegistro.setDevolucion(devolucion);
			nuevoRegistro.setCodArticulo(devolLinea.getCodArticulo());
			nuevoRegistro.setDenominacion((devolLinea.getDenominacion() != null && !("".equals(devolLinea.getDenominacion())))?devolLinea.getDenominacion().toString():null);			
			nuevoRegistro.setMarca((devolLinea.getMarca() != null && !("".equals(devolLinea.getMarca())))?devolLinea.getMarca():null);
			nuevoRegistro.setSeccion((devolLinea.getSeccion() != null && !("".equals(devolLinea.getSeccion())))?devolLinea.getSeccion():null);
			nuevoRegistro.setProvrGen(devolLinea.getProvrGen());
			nuevoRegistro.setProvrTrabajo(devolLinea.getProvrTrabajo());
			nuevoRegistro.setDenomProveedor((devolLinea.getDenomProveedor() != null && !("".equals(devolLinea.getDenomProveedor())))?devolLinea.getDenomProveedor():null);			
			nuevoRegistro.setFamilia((devolLinea.getFamilia() != null && !("".equals(devolLinea.getFamilia())))?devolLinea.getFamilia():null);
			nuevoRegistro.setFormatoDevuelto((devolLinea.getFormatoDevuelto() != null && !("".equals(devolLinea.getFormatoDevuelto())))?devolLinea.getFormatoDevuelto():null);
			nuevoRegistro.setFormato(devolLinea.getFormato());
			nuevoRegistro.setTipoFormato((devolLinea.getTipoFormato() != null && !("".equals(devolLinea.getTipoFormato())))?devolLinea.getTipoFormato():null);
			nuevoRegistro.setPasillo((devolLinea.getPasillo() != null && !("".equals(devolLinea.getPasillo())))?devolLinea.getPasillo():null);
			nuevoRegistro.setEstructuraComercial((devolLinea.getEstructuraComercial() != null && !("".equals(devolLinea.getEstructuraComercial())))?devolLinea.getEstructuraComercial():null);
			nuevoRegistro.setUc(devolLinea.getUc());
			nuevoRegistro.setStockActual(devolLinea.getStockActual());
			nuevoRegistro.setStockTienda(devolLinea.getStockTienda());
			nuevoRegistro.setStockDevolver(devolLinea.getStockDevolver());
			nuevoRegistro.setStockDevuelto(devolLinea.getStockDevuelto());
			nuevoRegistro.setStockDevueltoOrig(devolLinea.getStockDevuelto());
			nuevoRegistro.setCantAbonada(devolLinea.getCantAbonada());
			nuevoRegistro.setFlgContinuidad((devolLinea.getFlgContinuidad() != null && !("".equals(devolLinea.getFlgContinuidad())))?devolLinea.getFlgContinuidad():null);
			nuevoRegistro.setLote((devolLinea.getLote() != null && !("".equals(devolLinea.getLote())))?devolLinea.getLote():null);
			nuevoRegistro.setnLote((devolLinea.getnLote() != null && !("".equals(devolLinea.getnLote())))?devolLinea.getnLote():null);
			nuevoRegistro.setCaducidad((devolLinea.getCaducidad() != null && !("".equals(devolLinea.getCaducidad())))?devolLinea.getCaducidad():null);
			nuevoRegistro.setnCaducidad((devolLinea.getnCaducidad() != null && !("".equals(devolLinea.getnCaducidad())))?devolLinea.getnCaducidad():null);
			nuevoRegistro.setDescAbonoError((devolLinea.getDescAbonoError() != null && !("".equals(devolLinea.getDescAbonoError())))?devolLinea.getDescAbonoError():null);
			nuevoRegistro.setBulto(devolLinea.getBulto());
			nuevoRegistro.setBultoOrig(devolLinea.getBulto());
			nuevoRegistro.setUbicacion((devolLinea.getUbicacion() != null && !("".equals(devolLinea.getUbicacion())))?devolLinea.getUbicacion():null);
			nuevoRegistro.setTipoReferencia((devolLinea.getTipoReferencia() != null && !("".equals(devolLinea.getTipoReferencia())))?devolLinea.getTipoReferencia():null);
			nuevoRegistro.setEstadoLin(devolLinea.getEstadoLin());
			nuevoRegistro.setCodError(devolLinea.getCodError());
			nuevoRegistro.setDescError((devolLinea.getDescError() != null && !("".equals(devolLinea.getDescError())))?devolLinea.getDescError():null);
			nuevoRegistro.setFlgBandejas((devolLinea.getFlgBandejas() != null && !("".equals(devolLinea.getFlgBandejas())))?devolLinea.getFlgBandejas():null);
			nuevoRegistro.setStockDevueltoBandejas(devolLinea.getStockDevueltoBandejas());
			nuevoRegistro.setDescrTalla(devolLinea.getDescrTalla());
			nuevoRegistro.setDescrColor(devolLinea.getDescrColor());
			nuevoRegistro.setModelo(devolLinea.getModelo());
			nuevoRegistro.setModeloProveedor(devolLinea.getModeloProveedor());
			nuevoRegistro.setArea(devolLinea.getArea());
			nuevoRegistro.setCosteUnitario(devolLinea.getCosteUnitario());
			nuevoRegistro.setCantidadMaximaLin(devolLinea.getCantidadMaximaLin());

			//El objeto se inseta en una lista
			listTDevolucionLinea.add(nuevoRegistro);
		}

		try {
			this.devolucionesService.insertAll(listTDevolucionLinea);
		} catch (Exception e) {
			logger.error("insertarTablaSesionM="+e.toString());
			e.printStackTrace();
		}	
	}

	//Elimina la tabla temporal histórica de todos los usuarios si ha pasado más de un día desde
	//la creación de los registros
	private void eliminarTablaSesionHistorico(){		
		try {
			this.devolucionesService.deleteHistorico();
			this.devolucionLineaBultoCantidadService.deleteHistorico();
		} catch (Exception e) {
			logger.error("eliminarTablaSesionHistorico="+e.toString());
			e.printStackTrace();
		}
	}

	//Elimina los datos de sesión de la tabla temporal de ese usuario 
	private void eliminarTablaSesion(String idSesion){		
		TDevolucionLinea registro = new TDevolucionLinea();		
		registro.setIdSesion(idSesion);

		try {
			this.devolucionesService.delete(registro);
			this.devolucionLineaBultoCantidadService.delete(registro);
		} catch (Exception e) {
			logger.error("eliminarTablaSesion="+e.toString());
			e.printStackTrace();
		}
	}

}