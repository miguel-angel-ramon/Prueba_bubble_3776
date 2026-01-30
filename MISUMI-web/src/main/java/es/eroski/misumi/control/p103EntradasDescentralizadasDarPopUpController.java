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

import es.eroski.misumi.model.Entrada;
import es.eroski.misumi.model.EntradaActualizada;
import es.eroski.misumi.model.EntradaCatalogo;
import es.eroski.misumi.model.EntradaLinea;
import es.eroski.misumi.model.EntradaLineaModificada;
import es.eroski.misumi.model.EntradaPagina;
import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.model.TEntradaLinea;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.EntradasDescentralizadasService;
import es.eroski.misumi.service.iface.FotosReferenciaService;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.iface.PaginationManager;

@Controller
@RequestMapping("/entradasDescentralizadas/popup/dar")
public class p103EntradasDescentralizadasDarPopUpController {
	private static Logger logger = Logger.getLogger(p103EntradasDescentralizadasDarPopUpController.class);

	@Autowired
	private EntradasDescentralizadasService entradasDescentralizadasService;

	@Autowired
	private FotosReferenciaService fotosReferenciaService;

	@Resource
	private MessageSource messageSource;

	//Elimina la tabla de sesión la vuelve a rellenar con datos actualizados y devuelve la paginación. Se ejecuta la primera vez que se carga el grid
	//al abrir el popup.
	@RequestMapping(value="/loadDataGrid",method = RequestMethod.POST)
	public @ResponseBody EntradaPagina loadDataGrid(@RequestBody Entrada entrada,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			@RequestParam(value = "recarga", required = false, defaultValue = "N") String recarga,
			HttpSession session,HttpServletResponse response) throws Exception {

		//Paginación de entrada linea
		Page<TEntradaLinea> result = null;	        
		Entrada entradaProcedimiento = null;
		
		//Si no es una paginación o reordenación
		if(("N").equals(recarga)){
			//Obtiene la entrada con sus líneas.
			EntradaCatalogo entradaCatalogo = entradasDescentralizadasService.cargarAllLineasEntrada(entrada); 
			if(entradaCatalogo != null){
				List<Entrada> lstEntrada  = entradaCatalogo.getLstEntrada();

				//Se mira que existe la lista con entradas
				if(lstEntrada != null && lstEntrada.size() > 0){
					entradaProcedimiento = lstEntrada.get(0);

					//Se obtiene la lista de líneas de entrada
					List<EntradaLinea> lstEntradaLinea = entradaProcedimiento.getLstEntradaLinea();
					if(lstEntradaLinea != null && lstEntradaLinea.size() > 0){																							
						// Cuando hacemos la primera búsqueda eliminamos siempre los
						// registros guardados anteriormente. Se eliminarán todos los
						//registros de todas las sesiones que lleven más de un día en
						//la tabla.
						this.eliminarTablaSesionHistorico();

						// Insertar tabla temporal T_ENTRADA_LINEAS
						// A partir de la lista obtenida tenemos que insertar en la
						// tabla temporal los registros obtenidos,
						// borrando previamente los posibles registros almacenados.
						this.eliminarTablaSesion(session.getId());

						//Se inserta en una tabla temporal cada línea de entrada. Se devuelve una lista de TEntradaLinea tratada
						//a partir de la lista obtenida en el PLSQL. Así evitamos tirar de la tabla temporal al ya tener los datos.
						this.insertarTablaSesionLineasEntrada(lstEntradaLinea, session,entradaProcedimiento.getCodCabPedido());																       		

						//Obtenemos la lista ordenada de la tabla tmeporal para mostrar en el grid
						List<TEntradaLinea> listTEntradaLinea = this.entradasDescentralizadasService.findLineasEntrada(session.getId(),entrada,null);

						PaginationManager<TEntradaLinea> paginationManager = new PaginationManagerImpl<TEntradaLinea>();

						//El número de registros será el de la lista obtenida.
						int records = lstEntradaLinea.size();

						if(lstEntradaLinea.size() < max){
							result = paginationManager.paginate(new Page<TEntradaLinea>(), listTEntradaLinea, max.intValue(), records, page.intValue());
						}else{					
							List<TEntradaLinea> listaAMostrar = listTEntradaLinea.subList(0, max.intValue());
							result = paginationManager.paginate(new Page<TEntradaLinea>(), listaAMostrar, max.intValue(), records, page.intValue());
						}
					}
				}
			}
		}else{
			result = loadDataGridRecarga(entrada, page, max, index, sortOrder, response, session);
		}
		return new EntradaPagina(result,entradaProcedimiento);
	}		

	//Es la recarga del grid, al paginar o reordenar el grid
	@RequestMapping(value = "/loadDataGridRecarga", method = RequestMethod.POST)
	public  @ResponseBody Page<TEntradaLinea> loadDataGridRecarga(
			@RequestBody Entrada entrada,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		Pagination pagination= new Pagination(max,page);
		if (index!=null){
			pagination.setSort(index);
		}
		if (sortOrder!=null){
			pagination.setAscDsc(sortOrder);
		}
		List<TEntradaLinea> lstTEntradaLinea = null;

		try {
			//Si hay cambios actualizar tabla temporal
			if(entrada.getLstModificados() != null && entrada.getLstModificados().size() > 0){
				this.entradasDescentralizadasService.updateTablaSesionLineasEntrada(session.getId(),entrada,false);
			}
			lstTEntradaLinea = this.entradasDescentralizadasService.findLineasEntrada(session.getId(),entrada, pagination);
		} catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		Page<TEntradaLinea> result = null;

		PaginationManager<TEntradaLinea> paginationManager = new PaginationManagerImpl<TEntradaLinea>();

		if (lstTEntradaLinea != null && lstTEntradaLinea.size() > 0) {
			int records = lstTEntradaLinea.size();
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

			List<TEntradaLinea> listaAMostrar = lstTEntradaLinea.subList(desdeSubList,hastaSubList);
			result = paginationManager.paginate(new Page<TEntradaLinea>(), listaAMostrar,max.intValue(), records, page.intValue());	
		} else {
			return new Page<TEntradaLinea>();
		}
		return result;
	}

	//Sirve para guardar los valores de las líneas de devolución modificadas
	@RequestMapping(value = "/saveDataGrid", method = RequestMethod.POST)
	public  @ResponseBody EntradaActualizada loadComboProveedor(
			@RequestBody Entrada entrada,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
			@RequestParam(value = "max", required = false, defaultValue = "10") Long max,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "sortorder", required = false) String sortOrder,
			HttpServletResponse response,
			HttpSession session) throws Exception{
		Locale locale = LocaleContextHolder.getLocale();

		Pagination pagination= new Pagination(max,page);

		//Guarda la cantidad de líneas guardadas y con error.
		int countGuardados = 0;
		int countError = 0;

		//Guarda el error y la descripción a guardar.
		String descError;
		Long codError;

		if (index!=null){
			pagination.setSort(index);
		}
		if (sortOrder!=null){
			pagination.setAscDsc(sortOrder);
		}

		List<TEntradaLinea> list = null;
		EntradaCatalogo entradaCatalogoActualizada = null;


		//Si hay cambios, actualizar tabla temporal y poner los registros como modificados.Número 9.
		if(entrada.getLstModificados() != null && entrada.getLstModificados().size() > 0){
			this.entradasDescentralizadasService.updateTablaSesionLineasEntrada(session.getId(), entrada, false);
		}
		//Una vez tenemos la tabla temporal actualizada, obtenemos los elementos modificados. Como para actualizar
		//el PLSQL vamos a necesitar una lista de EntradaLinea y no de TEntradaLinea, devolvemos una lista de EntradLinea
		List<EntradaLinea> lstEntradaLineasModificadas = this.entradasDescentralizadasService.findLineasEntradaEditadas(session.getId(),new Long("9"));		

		//Si hay líneas de entradas editadas, actualizamos esas líneas mediante el PLSQL, actualizamos la temporal con los códigos de error correspondientes
		//y obtenemos las líneas del grid actualizadas de la tabla temporal actualizada. Si no hay, devolvemos error.
		//if ((lstEntradaLineasModificadas != null & lstEntradaLineasModificadas.size() > 0)){
		
		//Se insertan las líneas de entradas modificadas únicamente en la lista de líneas de entradas de la lista.
		entrada.setLstEntradaLinea(lstEntradaLineasModificadas);

		//Actualizamos la entrada y obtenemos el resultado con las líneas de entrada y sus códigos de error
		entradaCatalogoActualizada = this.entradasDescentralizadasService.actualizarEntrada(entrada);

		//Si se ha guardado todo bien actualizamos la temporal. Si no, devolvemos error.
		if(new Long("0").equals(entradaCatalogoActualizada.getCodError())){
			if(entradaCatalogoActualizada.getLstEntrada() != null && entradaCatalogoActualizada.getLstEntrada().size() > 0){
				//Obtenemos la entrada actualizada
				Entrada entradaActualizada = entradaCatalogoActualizada.getLstEntrada().get(0);

				//Por cada línea de la entrada si el codError es 9, significa que se ha actualizado bien, al no haber cambio en el código de error insertado en el PLSQL. En ese caso insertamos a las líneas el código de error 8 
				//para indicar que han pasado de modificadas a guardadas. En caso contrario, se inserta el código de error obtenido. Estos cambios, habrá que guardarlos en la tabla temporal para así tener actualizados los estados de
				//cada línea y dibujar bien el grid, por lo cual utilizaremos el método updateTablaSesionLineaEntrada

				EntradaLineaModificada entradaLineaModificada = null;
				List<EntradaLineaModificada> entradaLineaModificadaActualizada = new ArrayList<EntradaLineaModificada>();
				Long codErrorModif = null;
				for(EntradaLinea entradaLinea:entradaActualizada.getLstEntradaLinea()){		
					codErrorModif = new Long("8");
					countGuardados ++;
					/*//Si coinciden en código de artículo estamos hablando de la misma línea de entrada
					if((new Long("0")).equals(entradaLinea.getCodError())){
						codError = new Long("8");
						countGuardados ++;
					}else{
						codError = entradaLinea.getCodError();
						countError ++;
					}*/
					entradaLineaModificada = new EntradaLineaModificada(entradaLinea.getCodArticulo(),entradaLinea.getNumeroCajasRecepcionadas(),entradaLinea.getTotalBandejasRecepcionadas(),entradaLinea.getTotalUnidadesRecepcionadas(),codErrorModif,entradaLinea.getDescError());
					entradaLineaModificadaActualizada.add(entradaLineaModificada);
				}
				entradaActualizada.setLstModificados(entradaLineaModificadaActualizada);

				//Más tarde se limpian los estados. De esta forma se evita que al actualizar dos veces se mantengan los errores y disquetes de los estados Anteriores y solo se visualicen
				//los nuevos.		
				this.entradasDescentralizadasService.resetEntradaEstados(session.getId());			

				//Actualizamos los códigos de error de las devoluciones. Además también actualizamos los datos de BULTO_ORI y STOCKDEVUELTO_ORI de la tabla temporal
				this.entradasDescentralizadasService.updateTablaSesionLineasEntrada(session.getId(),entradaActualizada,true);

				//Devolvemos el código de error y la descripción del procedimiento.
				descError = entradaCatalogoActualizada.getDescError();
				codError = entradaCatalogoActualizada.getCodError();
			}else{
				//Devolvemos el error de que no se ha actualizado bien la entrada.;
				descError = messageSource.getMessage("p103_dar.entrada.actualizar.error", null,locale);
				codError = new Long("1");
			}
		}else{
			//Devolvemos todas las líneas como erroneas ya he ha habido error al actualizar. El error de actualizar y su descripción
			descError = entradaCatalogoActualizada.getDescError();
			codError = entradaCatalogoActualizada.getCodError();
			countError = lstEntradaLineasModificadas.size();
		}
		/*}else{
			//Devolvemos el error de que no hay elementos modificados. countError y countGuardados serán 0;
			descError = messageSource.getMessage("p103_dar.entrada.actualizar.sinModificacion", null,locale);
			codError = new Long("1");
		}*/

		list = this.entradasDescentralizadasService.findLineasEntrada(session.getId(), entrada, pagination);

		Page<TEntradaLinea> result = null;

		PaginationManager<TEntradaLinea> paginationManager = new PaginationManagerImpl<TEntradaLinea>();

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
			List<TEntradaLinea> listaAMostrar = list.subList(desdeSubList,hastaSubList);

			result = paginationManager.paginate(new Page<TEntradaLinea>(), listaAMostrar,max.intValue(), records, page.intValue());	
		} else {
			return new EntradaActualizada(new Page<TEntradaLinea>(),countGuardados,countError,descError,codError);
		}
		EntradaActualizada entradaActualizadaResp = new EntradaActualizada(result,countGuardados,countError,descError,codError);

		return entradaActualizadaResp;
	}
	/*********************************************************************************************************************************************/
	/*********************************************************************************************************************************************/
	/****************************************************************** MÉTODOS PRIVADOS ***************************************************************/
	/*********************************************************************************************************************************************/
	/*********************************************************************************************************************************************/

	//Elimina la tabla temporal histórica de todos los usuarios si ha pasado más de un día desde
	//la creación de los registros
	private void eliminarTablaSesionHistorico(){		
		try {
			this.entradasDescentralizadasService.deleteHistorico();
		} catch (Exception e) {
			logger.error("eliminarTablaSesionHistorico="+e.toString());
			e.printStackTrace();
		}
	}

	//Elimina los datos de sesión de la tabla temporal de ese usuario 
	private void eliminarTablaSesion(String idSesion){		
		TEntradaLinea registro = new TEntradaLinea();		
		registro.setIdSesion(idSesion);

		try {
			this.entradasDescentralizadasService.delete(registro);
		} catch (Exception e) {
			logger.error("eliminarTablaSesion="+e.toString());
			e.printStackTrace();
		}
	}

	//Método que sirve para guardar cada línea de entrada en un registro de una tabla temporal llamada T_ENTRADA_LINEAS
	//Esa tabla además de contener como columnas los atributos del objeto EntradaLinea, contiene el id de la sesión
	//del usuario, la fecha de creación. Con la columna del id de sesión es posible que cada
	//usuario tenga sus datos en la tabla temporal y así no ver los de los demás, además se utilizará para borrar los
	//datos de la tabla temporal de su sesión cuando sea necesario. El campo de la fecha de creación sirve para eliminar los
	//registros que lleven guardados X tiempo y sean considerados como basura en la tabla temporal. El campo de codCabPedido
	//se utilizará para saber cada línea de entrada a que entrada pertenece.
	private void insertarTablaSesionLineasEntrada(List<EntradaLinea> lstEntradaLinea, HttpSession session, Long codCabPedido){

		//Nos recorremos la lista para generar la lista que debemos enviar al servicio para que inserte en la tabla temporal.
		List<TEntradaLinea> lstTEntradaLinea = new ArrayList<TEntradaLinea>();

		//Definimos el puntero del registro a guardar en la bd
		TEntradaLinea nuevoRegistro = null;

		//Obtenemos el idSesion
		String idSesion = session.getId();
		
		//Obtenemos el codLoc
	    User user = (User) session.getAttribute("user");
	    Long codLoc = user.getCentro().getCodCentro();
	    
		for (EntradaLinea entradaLinea:lstEntradaLinea){
			//Se inicializa el objeto. Cada objeto de este tipo será un registro de la tabla temporal
			nuevoRegistro = new TEntradaLinea();

			//Datos de sesión de usuario y día de creación.
			nuevoRegistro.setIdSesion(idSesion);
			nuevoRegistro.setCreationDate(new Date());

			//Dato que relaciona la linea de entrada con la cabecera de la entrada.
			nuevoRegistro.setCodCabPedido(codCabPedido);

			//Datos de entradaLinea
			nuevoRegistro.setCodArticulo(entradaLinea.getCodArticulo());
			nuevoRegistro.setDenomCodArticulo(entradaLinea.getDenomCodArticulo() != null && !("".equals(entradaLinea.getDenomCodArticulo()))?entradaLinea.getDenomCodArticulo():null);
			nuevoRegistro.setNumeroCajasPedidas(entradaLinea.getNumeroCajasPedidas());
			nuevoRegistro.setNumeroCajasRecepcionadas(entradaLinea.getNumeroCajasRecepcionadas());
			nuevoRegistro.setUc(entradaLinea.getUc());
			nuevoRegistro.setTotalBandejasPedidas(entradaLinea.getTotalBandejasPedidas());
			nuevoRegistro.setTotalBandejasRecepcionadas(entradaLinea.getTotalBandejasRecepcionadas());
			nuevoRegistro.setTotalUnidadesPedidas(entradaLinea.getTotalUnidadesPedidas());
			nuevoRegistro.setTotalUnidadesRecepcionadas(entradaLinea.getTotalUnidadesRecepcionadas());

			//Dato de si tiene foto o no. Lo calculamos y lo insertamos en la temporal.
			nuevoRegistro.setFlgFoto(this.refTieneFoto(entradaLinea));

			//Insertamos el codLoc
			nuevoRegistro.setCodLoc(codLoc);
			
			//El objeto se inseta en una lista
			lstTEntradaLinea.add(nuevoRegistro);
		}
		try {
			this.entradasDescentralizadasService.insertAll(lstTEntradaLinea);
		} catch (Exception e) {
			logger.error("insertarTablaSesionM="+e.toString());
			e.printStackTrace();
		}	
	}

	private String refTieneFoto(EntradaLinea entradaLinea){
		try{		
			//Comprobar si tiene Foto
			FotosReferencia fotosReferencia = new FotosReferencia();
			fotosReferencia.setCodReferencia(entradaLinea.getCodArticulo());
			if (fotosReferenciaService.checkImage(fotosReferencia)){
				return "S";
			} else {
				return "N";
			}
		}catch(Exception e){
			logger.error("###############Error al obtener si la referencia de EntradaLinea tiene foto");
			logger.error(StackTraceManager.getStackTrace(e));

			return "N";
		}	
	}
}
