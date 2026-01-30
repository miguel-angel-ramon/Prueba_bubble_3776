package es.eroski.misumi.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.DetalladoRedondeo;
import es.eroski.misumi.model.DetalladoSIA;
import es.eroski.misumi.model.Devolucion;
import es.eroski.misumi.model.DevolucionActualizada;
import es.eroski.misumi.model.DevolucionCatalogoEstado;
import es.eroski.misumi.model.DevolucionLinea;
import es.eroski.misumi.model.DevolucionLineaModificada;
import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.model.OptionSelectBean;
import es.eroski.misumi.model.TDevolucionBulto;
import es.eroski.misumi.model.TDevolucionLinea;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.DevolucionLineaBultoCantidadService;
import es.eroski.misumi.service.iface.DevolucionService;
import es.eroski.misumi.service.iface.FotosReferenciaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.iface.PaginationManager;

@Controller
@RequestMapping("/devoluciones/popup/creadaCentro")
public class p87DevolucionesCreadaCentroPopupController {

	private static Logger logger = Logger.getLogger(p87DevolucionesCreadaCentroPopupController.class);

	@Autowired
	private FotosReferenciaService fotosReferenciaService;
	
	@Autowired
	private DevolucionService devolucionesService;

	@Autowired
	private DevolucionLineaBultoCantidadService devolucionLineaBultoCantidadService;

	@Resource 
	private MessageSource messageSource;

	//Elimina la tabla de sesión la vuelve a rellenar con datos actualizados y devuelve la paginación. Se ejecuta la primera vez que se carga el grid
	//al abrir el popup.
	@RequestMapping(value="/loadDataGrid",method = RequestMethod.POST)
	public @ResponseBody Page<TDevolucionLinea> loadDataGrid(@RequestBody Devolucion devolucion,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			@RequestParam(value = "recarga", required = false, defaultValue = "N") String recarga,
			@RequestParam(value = "filtroReferencia", required = false, defaultValue = "") String filtroReferencia,
			HttpSession session,HttpServletResponse response) throws Exception {

		//Paginación de devolución linea
		Page<TDevolucionLinea> result = null;	        

		//Si no es una paginación o reordenación
		if(("N").equals(recarga)){
			//Obtiene una devolución con sus líneas de devolución
			DevolucionCatalogoEstado devol = devolucionesService.cargarAllDevoluciones(devolucion); 

			//Se mira que existe la lista de líneas de devolución
			if(devol != null){
				if(devol.getListDevolucionEstado() != null && devol.getListDevolucionEstado().size() > 0){
					if(devol.getListDevolucionEstado().get(0).getListDevolucion() != null && devol.getListDevolucionEstado().get(0).getListDevolucion().size() > 0){
						Devolucion devolucionSeleccion = devol.getListDevolucionEstado().get(0).getListDevolucion().get(0);
						if(devolucionSeleccion.getDevLineas() != null && devolucionSeleccion.getDevLineas().size() > 0){																							

							//Se obtiene la lista de líneas de devolución
							List<DevolucionLinea> listDevolucionLinea = (List<DevolucionLinea>) devolucionSeleccion.getDevLineas();

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
							this.insertarTablaSesionLineaDevolucion(listDevolucionLinea, session.getId(),devolucionSeleccion.getDevolucion());																       		

							//Control del provvedor para ver si se elige REFERENCIAS PERMANENTES
							if (Constantes.PROVEEDOR_REF_PERMANENTES.equals(devolucion.getProveedor())){
								devolucion.setProveedor(null);
								devolucion.setFlagRefPermanentes("S");
							}

							//Obtenemos la lista ordenada de la tabla temporal para mostrar en el grid
							List<TDevolucionLinea> listTDevolucionLinea = this.devolucionesService.findLineasDevolucion(session.getId(),devolucion,null,null,devolucion.getProveedor(),devolucion.getFlagRefPermanentes());							
							
							PaginationManager<TDevolucionLinea> paginationManager = new PaginationManagerImpl<TDevolucionLinea>();

							//El número de registros será el de la lista obtenida.
							int records = listDevolucionLinea.size();

							//Al clicar el input del primer elemento del grid, se rellenará automáticamente con un 1.
							listTDevolucionLinea.get(0).setPrimerElementoBulto(new Long(1));
							//Al clicar el input del primer elemento del grid, se rellenará automáticamente con un 1.00.
							listTDevolucionLinea.get(0).setPrimerElementoStockDevuelto(new Double(1.00));

							if(listDevolucionLinea.size() < max){
								refTieneFoto(listTDevolucionLinea);
								result = paginationManager.paginate(new Page<TDevolucionLinea>(), listTDevolucionLinea, max.intValue(), records, page.intValue());
							}else{
								List<TDevolucionLinea> listaAMostrar = listTDevolucionLinea.subList(0, max.intValue());
								refTieneFoto(listaAMostrar);
								result = paginationManager.paginate(new Page<TDevolucionLinea>(), listaAMostrar, max.intValue(), records, page.intValue());
							}
						}
					}
				}
			}
		}else{
			result = loadDataGridRecarga(devolucion, page, max, index, sortOrder, filtroReferencia, response, session);
		}
		return result;
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
			nuevoRegistro.setStockDevueltoBandejasOrig(devolLinea.getStockDevueltoBandejas());
			nuevoRegistro.setCantidadMaximaPermitida(devolLinea.getCantidadMaximaPermitida());
			//Pet. MISUMI - 114
			nuevoRegistro.setDescrTalla(devolLinea.getDescrTalla());
			nuevoRegistro.setDescrColor(devolLinea.getDescrColor());
			nuevoRegistro.setModelo(devolLinea.getModelo());
			nuevoRegistro.setModeloProveedor(devolLinea.getModeloProveedor());
			nuevoRegistro.setCosteUnitario(devolLinea.getCosteUnitario());
			
			if (devolLinea.getCosteUnitario()!= null && devolLinea.getStockDevuelto()!= null) {
				
				
				Double costeUnitario= devolLinea.getCosteUnitario();
				Double stockDevuelto= devolLinea.getStockDevuelto();
						
				Double costeFinal = new Double(costeUnitario * stockDevuelto);
				nuevoRegistro.setCosteFinal(costeFinal);
			} else {
				nuevoRegistro.setCosteFinal(new Double(0));
			}
			
			nuevoRegistro.setArea(devolLinea.getArea());
					    
					    
			listTDevolucionLinea.add(nuevoRegistro);
		}

		try {
			this.devolucionesService.insertAll(listTDevolucionLinea);
		} catch (Exception e) {
			logger.error("insertarTablaSesionM="+e.toString());
			e.printStackTrace();
		}	
	}
	
	private void refTieneFoto(List<TDevolucionLinea> listTDevolucionLinea) throws Exception{
		for(TDevolucionLinea devLin:listTDevolucionLinea){
			 //Comprobar si tiene Foto
			FotosReferencia fotosReferencia = new FotosReferencia();
			fotosReferencia.setCodReferencia(devLin.getCodArticulo());
			if (fotosReferenciaService.checkImage(fotosReferencia)){
				devLin.setFlgFoto("S");
			} else {
				devLin.setFlgFoto("N");
			}
		}
	}

	//Elimina la tabla temporal histórica de todos los usuarios si ha pasado más de un día desde
	//la creación de los registros
	private void eliminarTablaSesionHistorico(){		
		try {
			this.devolucionesService.deleteHistorico();
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
		} catch (Exception e) {
			logger.error("eliminarTablaSesion="+e.toString());
			e.printStackTrace();
		}
	}

	//Es la recarga del grid, al paginar o reordenar el grid
	@RequestMapping(value = "/loadDataGridRecarga", method = RequestMethod.POST)
	public  @ResponseBody Page<TDevolucionLinea> loadDataGridRecarga(
			@RequestBody Devolucion devolucion,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			@RequestParam(value = "filtroReferencia", required = false) String filtroReferencia,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		List<TDevolucionBulto> listTDevolucionLinea = new ArrayList<TDevolucionBulto>(5);
		String listaBultos = "";

		Pagination pagination= new Pagination(max,page);

		if (index!=null){
			pagination.setSort(index);
		}
		if (sortOrder!=null){
			pagination.setAscDsc(sortOrder);
		}

		List<TDevolucionLinea> list = null;

		try {
			//Si hay cambios actualizar tabla temporal
			if(devolucion.getDevLineasModificadas() != null && devolucion.getDevLineasModificadas().size() > 0){
				this.devolucionesService.updateTablaSesionLineaDevolucion(session.getId(),devolucion,false);
			}
			
			//Control del provvedor para ver si se elige REFERENCIAS PERMANENTES
			if (Constantes.PROVEEDOR_REF_PERMANENTES.equals(devolucion.getProveedor())){
				devolucion.setProveedor(null);
				devolucion.setFlagRefPermanentes("S");
			}

			list = this.devolucionesService.findLineasDevolucion(session.getId(),devolucion, pagination,null,devolucion.getProveedor(),devolucion.getFlagRefPermanentes(),filtroReferencia);
			list = this.devolucionLineaBultoCantidadService.cargarBultoCantidadLinea(session.getId(),list);

			if (list != null && list.size() > 0) {

				//Obtenemos la lista de cantidades y bultos asociados a la referencia para mostrar en la pantalla
				listTDevolucionLinea=list.get(0).getListTDevolucionLinea();
				if (list.get(0).getListTDevolucionLinea() == null || list.get(0).getListTDevolucionLinea().size() < 5){
					for (int i=listTDevolucionLinea.size();i<5;i++){
						TDevolucionBulto vacio=new TDevolucionBulto();
						listTDevolucionLinea.add(vacio);
					}
				}

				listaBultos = this.devolucionLineaBultoCantidadService.cargarListaBultos(session.getId(),list.get(0));

	//			devolucionLinea.setDevolucion(devolucion.getDevolucion());
	//			devolucionLinea.setCodArticulo(devolucion.getCodArticulo());
	//			devolucionLinea.setFlgPesoVariable(list.get(0).getFlgPesoVariable());
	//			devolucionLinea.setCantidadMaximaLin(list.get(0).getCantidadMaximaLin());
	//			devolucionLinea.setEstadoCerrado(list.get(0).getEstadoCerrado());
	//			devolucionLinea.setListTDevolucionLinea(listTDevolucionLinea);
	//			devolucionLinea.setVariosBultos(list.get(0).getVariosBultos());
	//			devolucionLinea.setListaBultos(listaBultos);
	//			devolucionLinea.setBultoEstadoCerrado(list.get(0).getBultoEstadoCerrado());

				list.get(0).setListaBultos(listaBultos);
				list.get(0).setListTDevolucionLinea(listTDevolucionLinea);
			}

		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		Page<TDevolucionLinea> result = null;

		PaginationManager<TDevolucionLinea> paginationManager = new PaginationManagerImpl<TDevolucionLinea>();

		if (list != null && list.size() > 0) {
			int records = list.size();
			int desdeSubList = ((page.intValue()-1) * max.intValue());
			int hastaSubList = ((page.intValue())*max.intValue());

			//Hay que ver desde que elemento hasta que elemento hay que hacer la sublista. Para ello utilizamos
			//el elemento desdeSubList y hastaSubList. Puede darse la situación de que hastaSubList sea mayor a los
			//elementos de la lista, por ejemplo existen 6 paginaciones y se enseñan 10 elementos por paginación,
			//en la paginación 6 el elemento desdeSubList será 50 y hastaSubList 60, pero si la sublista tiene
			//53 elementos da error! Por lo cual hay que hacer un caso en el que hastaSubList sea igual al número
			//de elementos de la lista!
			if(hastaSubList > records){
				hastaSubList = records;				
			}

			//Si vamos a la 1 página, el primer elemento guardará un 1 en caso de ser vacío y al clicarlo. En caso
			//contrario, guardará el bulto actual del elemento de la página anterior.
			if(page.intValue() == 1){
				list.get(desdeSubList).setPrimerElementoBulto(new Long(1));
				list.get(desdeSubList).setPrimerElementoStockDevuelto(new Double(1.00));
			}else{
				list.get(desdeSubList).setPrimerElementoBulto(list.get(desdeSubList-1).getBulto());
				list.get(desdeSubList).setPrimerElementoStockDevuelto(list.get(desdeSubList-1).getStockDevuelto());
			}

			List<TDevolucionLinea> listaAMostrar = list.subList(desdeSubList,hastaSubList);
			refTieneFoto(listaAMostrar);
			
			result = paginationManager.paginate(new Page<TDevolucionLinea>(), listaAMostrar,max.intValue(), records, page.intValue());	
		} else {
			return new Page<TDevolucionLinea>();
		}
		return result;
	}

	//Sirve para cargar los valores del combobox ordenados
	@RequestMapping(value = "/loadComboProveedor", method = RequestMethod.POST)
	public  @ResponseBody List<OptionSelectBean> loadComboProveedor(
			@RequestBody Devolucion devolucion,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		List<OptionSelectBean> listaProveedores = null;
		try {
			Locale locale = LocaleContextHolder.getLocale();
			listaProveedores = new  ArrayList<OptionSelectBean>();

			OptionSelectBean opcionRefPermanentes = new OptionSelectBean();
			opcionRefPermanentes.setCodigo(Constantes.PROVEEDOR_REF_PERMANENTES);
			opcionRefPermanentes.setDescripcion(this.messageSource.getMessage("p84_popUpDevolOrdenRetirada.proveedor.ref.permanentes", null, locale));
			listaProveedores.add(opcionRefPermanentes);
			
			listaProveedores.addAll(this.devolucionesService.obtenerProveedoresLineasDevolucion(session.getId(),devolucion));
			
		} catch (Exception e) {

			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		return listaProveedores;

	}

	//Sirve para guardar los valores de las líneas de devolución modificadas
	@RequestMapping(value = "/saveDataGrid", method = RequestMethod.POST)
	public  @ResponseBody DevolucionActualizada loadComboProveedor(
			@RequestBody Devolucion devolucion,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			@RequestParam(value = "codRMA", required = false) String codRMA,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		Pagination pagination= new Pagination(max,page);
		int countGuardados = 0;
		int countError = 0;

		if (index!=null){
			pagination.setSort(index);
		}
		if (sortOrder!=null){
			pagination.setAscDsc(sortOrder);
		}

		List<TDevolucionLinea> list = null;
		DevolucionCatalogoEstado devolucionCatalogoEstadoActualizada = null;
		
		//Añadimos el valor de RMA a la devolución. Ha podido ser modificado por el usuario
		devolucion.setCodRMA(codRMA);

		//Si hay cambios, actualizar tabla temporal y poner los registros como modificados.Número 9.
		if(devolucion.getDevLineasModificadas() != null && devolucion.getDevLineasModificadas().size() > 0){
			this.devolucionesService.updateTablaSesionLineaDevolucion(session.getId(),devolucion,false);
		}
		//Una vez tenemos la tabla temporal actualizada, obtenemos los elementos modificados.
		List<DevolucionLinea> listTDevolucionesModificadas = this.devolucionesService.findLineasDevolucionEditadas(session.getId(),new Long("9"));

		//Si hay líneas de devolución editadas, actualizamos esas líneas mediante el PLSQL, actualizamos la temporal con los códigos de error correspondientes
		//y obtenemos las líneas del grid actualizadas de la tabla temporal actualizada. Si no hay modificaciones, obtenemos la lista tal cual se encuentra
		//en la tabla temporal.
		if ((listTDevolucionesModificadas != null & listTDevolucionesModificadas.size() > 0) || (codRMA != null)){
			//Se insertan las líneas de devolución modificadas únicamente en la lista de líneas de devolución de la lista.
			devolucion.setDevLineas(listTDevolucionesModificadas);

			//Actualizamos la devolución y obtenemos el resultado con las líneas de devolucion y sus códigos de error
			devolucionCatalogoEstadoActualizada = this.devolucionesService.actualizarDevolucion(devolucion);

			//Si no actualiza bien
			if(devolucionCatalogoEstadoActualizada.getListDevolucionEstado() != null && devolucionCatalogoEstadoActualizada.getListDevolucionEstado().size() > 0){
				//Obtenemos la devolucion actualizada
				Devolucion devolucionActualizada = devolucionCatalogoEstadoActualizada.getListDevolucionEstado().get(0).getListDevolucion().get(0);

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
						countGuardados ++;
					}else{
						codError = devLinea.getCodError();
						countError ++;
					}
					devLineaModificada = new DevolucionLineaModificada(devLinea.getBulto(), devLinea.getStockDevuelto(), devLinea.getStockDevolver(), devLinea.getStockActual(), codError, devLinea.getCodArticulo(), devLinea.getStockDevueltoBandejas());
					devLineaModificadaActualizada.add(devLineaModificada);
				}
				devolucionActualizada.setDevLineasModificadas(devLineaModificadaActualizada);

				//Más tarde se limpian los estados. De esta forma se evita que al actualizar dos veces se mantengan los errores y disquetes de los estados enteriores y solo se visualicen
				//los nuevos.		
				this.devolucionesService.resetDevolEstados(session.getId());			
				
				//Actualizamos los códigos de error de las devoluciones. Además también actualizamos los datos de BULTO_ORI y STOCKDEVUELTO_ORI de la tabla temporal
				this.devolucionesService.updateTablaSesionLineaDevolucion(session.getId(),devolucionActualizada,true);
			}
		}
		
		//Control del provvedor para ver si se elige REFERENCIAS PERMANENTES
		if (Constantes.PROVEEDOR_REF_PERMANENTES.equals(devolucion.getProveedor())){
			devolucion.setProveedor(null);
			devolucion.setFlagRefPermanentes("S");
		}
		
		list = this.devolucionesService.findLineasDevolucion(session.getId(),devolucion, pagination,null,devolucion.getProveedor(),devolucion.getFlagRefPermanentes());

		Page<TDevolucionLinea> result = null;

		PaginationManager<TDevolucionLinea> paginationManager = new PaginationManagerImpl<TDevolucionLinea>();

		if (list != null && list.size() > 0) {
			int records = list.size();
			int desdeSubList = ((page.intValue()-1) * max.intValue());
			int hastaSubList = ((page.intValue())*max.intValue());

			//Hay que ver desde que elemento hasta que elemento hay que hacer la sublista. Para ello utilizamos
			//el elemento desdeSubList y hastaSubList. Puede darse la situación de que hastaSubList sea mayor a los
			//elementos de la lista, por ejemplo existen 6 paginaciones y se enseñan 10 elementos por paginación,
			//en la paginación 6 el elemento desdeSubList será 50 y hastaSubList 60, pero si la sublista tiene
			//53 elementos da error! Por lo cual hay que hacer un caso en el que hastaSubList sea igual al número
			//de elementos de la lista!
			if(hastaSubList > records){
				hastaSubList = records;				
			}
			List<TDevolucionLinea> listaAMostrar = list.subList(desdeSubList,hastaSubList);
			
			//Obtenemos el flag de si la referencia tiene foto o no.
			refTieneFoto(listaAMostrar);
			result = paginationManager.paginate(new Page<TDevolucionLinea>(), listaAMostrar,max.intValue(), records, page.intValue());	
		} else {
			return new DevolucionActualizada(new Page<TDevolucionLinea>(),countGuardados,countError,devolucionCatalogoEstadoActualizada.getpDescError(),devolucionCatalogoEstadoActualizada.getpCodError());
		}
		DevolucionActualizada devActualizada = new DevolucionActualizada(result,countGuardados,countError,devolucionCatalogoEstadoActualizada.getpDescError(),devolucionCatalogoEstadoActualizada.getpCodError());
		return devActualizada;
	}
	
	//Devuelve una lista de referencias que contiene el patrón introducido.
	@RequestMapping(value="/loadRefPattern", method = RequestMethod.POST)
	public @ResponseBody List<Long> loadRefPattern(@RequestParam String term,
			@RequestBody Devolucion devolucion,
			HttpSession session, HttpServletResponse response) throws Exception {

		List<Long> referencias = null;
		try {	
			referencias = this.devolucionesService.getRefByPattern(term,session.getId(),devolucion);
			
		} catch (Exception e) {		  
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
		return referencias;
	}


	/** Sirve para buscar las plataformas de una referencia. */
	@RequestMapping(value = "/obtenerCantidadADevolver", method = RequestMethod.POST)
	public  @ResponseBody List<DevolucionLinea> obtenerCantidadADevolver(
			@RequestBody  Devolucion devolucion,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		return devolucionesService.obtenerCantidadADevolver(devolucion); 
	}
	
	@RequestMapping(value = "/getSumaCosteFinal", method = RequestMethod.POST)
	public  @ResponseBody Double getSumaCosteFinal(
			@RequestBody Devolucion devolucion,
			HttpServletResponse response,
			HttpSession session
				) throws Exception{
	    		
		
			Double suma = this.devolucionesService.getSumaCosteFinal(devolucion,session.getId());		
			
			return suma;
	
	}
	
	
	
}
