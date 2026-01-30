package es.eroski.misumi.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.eroski.misumi.dao.iface.DetalladoMostradorSIADao;
import es.eroski.misumi.dao.iface.DetalladoSIADao;
import es.eroski.misumi.dao.iface.DetalleMostradorDao;
import es.eroski.misumi.model.AlarmaPLU;
import es.eroski.misumi.model.Cronometro;
import es.eroski.misumi.model.DetalladoMostradorExcel;
import es.eroski.misumi.model.DetalladoMostradorInfo;
import es.eroski.misumi.model.DetalladoRedondeo;
import es.eroski.misumi.model.DetalladoSIA;
import es.eroski.misumi.model.DetalleMostrador;
import es.eroski.misumi.model.DetalleMostradorModificados;
import es.eroski.misumi.model.DetallePedido;
import es.eroski.misumi.model.DetallePedidoModificados;
import es.eroski.misumi.model.EstrucComercialMostrador;
import es.eroski.misumi.model.FiltrosDetalleMostrador;
import es.eroski.misumi.model.GenericExcelReport;
import es.eroski.misumi.model.OfertaDetalleMostrador;
import es.eroski.misumi.model.ReferenciaNoGamaMostrador;
import es.eroski.misumi.model.VMisDetalladoMostrador;
import es.eroski.misumi.model.VMisDetalladoMostradorWrapperList;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.DetalladoMostradorService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.ExcelManagerImpl;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.iface.PaginationManager;


@Service(value = "DetalladoMostradorService")
public class DetalladoMostradorServiceImpl implements DetalladoMostradorService {

	private static Logger logger = Logger.getLogger(DetalladoMostradorServiceImpl.class);
	
	@Autowired
	private DetalleMostradorDao detalleMostradorDao;

	@Autowired
	private DetalladoSIADao detalladoSIADao;
	
	@Autowired
	private DetalladoMostradorSIADao detalladoMostradorSIADao;

	@Autowired
	private ExcelManagerImpl excel;
	
	@Autowired
	private StockTiendaService stockTiendaService;

	@Override
	public void borrarTablaTemp(String sesionId) throws Exception{
		// Borrado de la tabla temporal.
		detalleMostradorDao.deleteTemp(sesionId);
	}

	@Override
	public Long cargarTablaTemp(List<DetalleMostrador> listaDetalle, String sesionId) throws Exception{

		// Carga de la tabla temporal.
		Long resultado = detalleMostradorDao.insertListIntoTemp(listaDetalle,sesionId);
		
		return resultado;
	}

	@Override
	public void actualizarDatosGrid(VMisDetalladoMostradorWrapperList listToUpdate, Long codCentro, String sesionId) throws Exception{
		
		//Obtención de parámetros de actualización. Datos que se enviarán al procedimiento
		final List<VMisDetalladoMostrador> lstModificados = new ArrayList<VMisDetalladoMostrador>();
		
		//Carga de lista de modificados
		for (int i=0; i<listToUpdate.size(); i++){
			VMisDetalladoMostrador vMisdetalladoMostrador = listToUpdate.get(i);
			//Se guardan los modificados para pasarlos al procedimiento.
			lstModificados.add(vMisdetalladoMostrador);
		}
		
		// 1º Llamada a P_ACTUALIZAR() de SIA.
//		List<DetalleMostradorModificados> detalleMostradorModificados = detalladoMostradorSIADao.actualizarDatosGridSIA(lstModificados, codCentro);
		
		List<DetalleMostradorModificados> lstDetalleMostradorModificados = new ArrayList<DetalleMostradorModificados>();
		DetalleMostradorModificados detalladoMostradorModificados;
		DetallePedido registro;

		List<DetallePedido> lstDetallePedido = new ArrayList<DetallePedido>();
		
		for (int i=0;i<listToUpdate.size();i++){
			String tipoDetallado = "A";		
			registro = new DetallePedido();
			registro.setCodCentro((codCentro != null && !("".equals(codCentro.toString())))?codCentro:null);
			registro.setCodArticulo((lstModificados.get(i).getReferencia() != null && !("".equals(lstModificados.get(i).getReferencia().toString())))?lstModificados.get(i).getReferencia():null);	
			registro.setCodArticuloEroski((lstModificados.get(i).getReferencia() != null && !("".equals(lstModificados.get(i).getReferencia().toString())))?lstModificados.get(i).getReferencia():null);
			registro.setUnidadesCaja((lstModificados.get(i).getUnidadesCaja() != null && !("".equals(lstModificados.get(i).getUnidadesCaja().toString())))?lstModificados.get(i).getUnidadesCaja():null);
			registro.setCantidadOriginal(0L);
//			registro.setCantidad((lstModificados.get(i).getPropuestaPedido() != null && !("".equals(lstModificados.get(i).getPropuestaPedido().toString())))?Math.round(Math.floor(lstModificados.get(i).getPropuestaPedido() * registro.getUnidadesCaja())):null);
//			registro.setCantidadAnt((lstModificados.get(i).getPropuestaPedidoAnt() != null && !("".equals(lstModificados.get(i).getPropuestaPedidoAnt().toString())))?Math.round(Math.floor(lstModificados.get(i).getPropuestaPedidoAnt() * registro.getUnidadesCaja())):null);
			registro.setCantidad((lstModificados.get(i).getPropuestaPedido() != null && !("".equals(lstModificados.get(i).getPropuestaPedido().toString())))?lstModificados.get(i).getPropuestaPedido():null);
			registro.setCantidadAnt((lstModificados.get(i).getPropuestaPedidoAnt() != null && !("".equals(lstModificados.get(i).getPropuestaPedidoAnt().toString())))?lstModificados.get(i).getPropuestaPedidoAnt():null);
			
			// Si se modifica una referencia (D)ESCENTRALIZADA tenga o no necesidad asignada --> TIPO_DETALLADO = 'D'.
			// Resto de casos se trata como hasta ahora. Si es (C)ENTRALIZADA y no tiene necesidad --> TIPO_DETALLADO = 'A'
			if (lstModificados.get(i).getTipoAprov()!= null && Constantes.TIPO_APROVISIONAMIENTO_DESCENTRALIZADO.equalsIgnoreCase(lstModificados.get(i).getTipoAprov())){
				tipoDetallado = Constantes.TIPO_APROVISIONAMIENTO_DESCENTRALIZADO;
				registro.setTipoDetallado(tipoDetallado);
			// Si CENTRALIZADA.
			}else{
				//Si codigo de necesidad es 0 o null, tipoDetallado = "A", resto de casos tipoDetallado=null
				registro.setTipoDetallado(null != lstModificados.get(i).getCodNecesidad() && Long.valueOf(0).compareTo(lstModificados.get(i).getCodNecesidad()) < 0 ? null : tipoDetallado);
			}
//			registro.setTipoDetallado((lstModificados.get(i).getCodNecesidad() != null && !("".equals(lstModificados.get(i).getCodNecesidad().toString())))?null:tipoDetallado);
			registro.setEstadoPedido((lstModificados.get(i).getEstado() != null && !("".equals(lstModificados.get(i).getEstado().toString())))?lstModificados.get(i).getEstado() :null);
			registro.setCodNecesidad((lstModificados.get(i).getCodNecesidad() != null && !("".equals(lstModificados.get(i).getCodNecesidad().toString())))?new Long(lstModificados.get(i).getCodNecesidad().toString()):null);
			registro.setFechaEntrega(lstModificados.get(i).getFechaVenta());
			
			lstDetallePedido.add(registro);
		}
		
		// 1º Llamada a P_ACTUALIZAR() de SIA.
		List<DetallePedidoModificados> lstDetallePedidoModificados = detalladoSIADao.modifyProcedureSIAData(lstDetallePedido, codCentro);

		// 2º ACTUALIZAR la tabla Temporal con lo devuelto de SIA.
		for (int i=0;i<lstDetallePedidoModificados.size();i++){
			detalladoMostradorModificados = new DetalleMostradorModificados();
			detalladoMostradorModificados.setCodArticulo(lstDetallePedidoModificados.get(i).getCodArticulo());
			detalladoMostradorModificados.setCodNecesidad(lstDetallePedidoModificados.get(i).getCodNecesidad());
			detalladoMostradorModificados.setEstadoPedido(lstDetallePedidoModificados.get(i).getEstadoPedido());
			detalladoMostradorModificados.setEstadoGrid(lstDetallePedidoModificados.get(i).getEstadoGrid());
			detalladoMostradorModificados.setDescError(lstDetallePedidoModificados.get(i).getResultadoWS());
//			detalladoMostradorModificados.setPropuestaPedido(Math.round(Math.ceil(new Double(lstDetallePedidoModificados.get(i).getCantidad().toString())/new Double(lstDetallePedidoModificados.get(i).getUnidadesCaja().toString()))));
//			detalladoMostradorModificados.setPropuestaPedidoAnt(Math.round(Math.ceil(new Double(lstDetallePedidoModificados.get(i).getCantidadAnt().toString())/new Double(lstDetallePedidoModificados.get(i).getUnidadesCaja().toString()))));
			detalladoMostradorModificados.setPropuestaPedido(lstDetallePedidoModificados.get(i).getCantidad());
			detalladoMostradorModificados.setPropuestaPedidoAnt(lstDetallePedidoModificados.get(i).getCantidadAnt());

			lstDetalleMostradorModificados.add(detalladoMostradorModificados);			
		}
		
		detalleMostradorDao.updateData(lstDetalleMostradorModificados, codCentro, sesionId);
		
	}
	

	@Override
	public List<VMisDetalladoMostrador> findDetalleNivel1Mostrador(Long codCentro, String sesionId) throws Exception{
		List<VMisDetalladoMostrador> lstNivel1Mostrador = new ArrayList<VMisDetalladoMostrador>();
		
		// Recupera registros de nivel 1 de la vista V_MIS_DETALLADO_MOSTRADOR.
		lstNivel1Mostrador = detalleMostradorDao.findDetalleNivel1Mostrador(codCentro, sesionId);
			
		return lstNivel1Mostrador;
	}
	
	@Override
	public List<VMisDetalladoMostrador> findDetalleNivel2Mostrador(String sessionId, Long ident) throws Exception{
		List<VMisDetalladoMostrador> listaNivel2Mostrador = new ArrayList<VMisDetalladoMostrador>();
		
		// Recupera registros de nivel 2 de la vista V_MIS_DETALLADO_MOSTRADOR.
		listaNivel2Mostrador = detalleMostradorDao.findDetalleNivel2Mostrador(sessionId, ident);
			
		return listaNivel2Mostrador;
	}

	@Override
	public DetalladoMostradorInfo obtenerDatosCabecera(Long codCentro, String sesionId) throws Exception{
		DetalladoMostradorInfo userData = detalleMostradorDao.obtenerDatosCabecera(codCentro, sesionId);
		
		return userData;
	}
	
	@Override
	public List<VMisDetalladoMostrador> setStock(Long codCentro, List<VMisDetalladoMostrador> rows, HttpSession session) throws Exception{
		logger.info("setStock - codCentro = "+codCentro);
		List<VMisDetalladoMostrador> output = new ArrayList<VMisDetalladoMostrador>();
		
		
		ConsultarStockRequestType requestType = new ConsultarStockRequestType();
		requestType.setCodigoCentro(BigInteger.valueOf(codCentro));
		requestType.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA);
		
		BigInteger[] listaRef = new BigInteger[rows.size()];
		
		for (int i=0; i<rows.size(); i++){
			listaRef[i] = BigInteger.valueOf(rows.get(i).getReferencia()); 
		}
		requestType.setListaCodigosReferencia(listaRef);
		
		ConsultarStockResponseType responseType = null;
		try {
			responseType = this.stockTiendaService.consultaStock(requestType,session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (responseType!=null){
			logger.info("setStock - codigoRespuesta = "+responseType.getCodigoRespuesta()+" - tipoMensaje = "+responseType.getTipoMensaje());
			if (responseType.getCodigoRespuesta().equals("OK")){
				
				for (VMisDetalladoMostrador row : rows){
					row = getStockFromReferences(row,responseType.getListaReferencias());
					output.add(row);
				}

			} else {
				logger.error("Error al consultar los STOCKs del centro "+codCentro+" ("+responseType.getCodigoRespuesta()+") : "+responseType.getDescripcionRespuesta());
				return rows;
			}
		}else{
			logger.error("Error al consultar los STOCKs del centro "+codCentro+" : ERROR AL INICIALIZAR LA PETICION DEL WS");
			return rows;
		}
		
		return output;
	}
	
	private VMisDetalladoMostrador getStockFromReferences(VMisDetalladoMostrador row, ReferenciaType[] listaReferencias) {
		VMisDetalladoMostrador output = null;
		Double stock = 0d;
		Long codArticulo = row.getReferencia();
		for (ReferenciaType referencia : listaReferencias){
			if (referencia.getCodigoError().equals(new BigInteger("0"))){
				if (referencia.getCodigoReferencia().equals(BigInteger.valueOf(codArticulo))){
					if (referencia.getStockPrincipal().equals(Constantes.STOCK_PRINCIPAL_BANDEJAS)){
						stock = referencia.getBandejas().doubleValue();
					} else {
						stock = referencia.getStock().doubleValue();
					}
					output = row;
					output.setStock(stock);

					break;
				}
			} 
		}
		if (output==null){
			logger.error("Error al consultar el STOCK del articulo "+codArticulo+" : NO SE HA ENCONTRADO");
		}
		logger.info("getStockFromReferences - codArticulo = "+codArticulo+" - stock = "+output);
		return output;
	}

	@Override
	public EstrucComercialMostrador findEstructuraComercial(Long codCentro, Long codN2, Long codN3, Long codN4, String tipoAprov) throws Exception {
		return detalleMostradorDao.obtenerEstrCom(codCentro, codN2, codN3, codN4, tipoAprov);
	}

	/**
	 * @author BICUGUAL
	 */
	@Override
	public Cronometro calculoCronometroYNumeroHorasLimite(Long codCentro, String sesionID) throws Exception {
		return detalleMostradorDao.calculoCronometroYNumeroHorasLimite(codCentro, sesionID);
	}

	@Override
	public VMisDetalladoMostrador redondeoDetallado(Long codCentro, Long codArticulo, Long pdteRecibirVenta, Double unidadesCaja, Long propuestaPedido ) throws Exception{
		
		VMisDetalladoMostrador result = null;
		
		DetalladoSIA ent = new DetalladoSIA();
		ent.setCodArticulo(codArticulo);
		ent.setCodLoc(codCentro);
		
//		ent.setUnidPropuestasFlModif(Math.round(Math.floor(propuestaPedido * unidadesCaja)));
		ent.setUnidPropuestasFlModif(Math.round(propuestaPedido * unidadesCaja*100.0)/100.0);
		
		DetalladoRedondeo sal = detalladoSIADao.redondeoDetallado(ent);
		String pdteRecibirVentaGrid="";
		
		if (sal != null) {
			result = new VMisDetalladoMostrador();
			
			if ( sal.getCodError() == 1 && (Long.valueOf(0).compareTo(pdteRecibirVenta) < 0)) {
				pdteRecibirVentaGrid = "R ".concat(pdteRecibirVenta.toString()).concat("E");
			} else if (sal.getCodError() == 1 && (Long.valueOf(0).compareTo(pdteRecibirVenta) == 0)) {
				pdteRecibirVentaGrid = "R";
			} else if (sal.getCodError() == 0 && (Long.valueOf(0).compareTo(pdteRecibirVenta) < 0)) {
				pdteRecibirVentaGrid = pdteRecibirVenta.toString().concat("E");
			} else if (sal.getCodError() == 0 && (Long.valueOf(0).compareTo(pdteRecibirVenta) == 0)) {
				pdteRecibirVentaGrid=null;
			}
			
			result.setPropuestaPedido(sal.getUnidPropuestasFlModif().longValue());
			result.setPdteRecibirVentaGrid(pdteRecibirVentaGrid);
			result.setDescripcionError(null!=sal.getCodError() ? sal.getCodError()+"-"+sal.getDescError() : sal.getDescError());
		}
		
		return result;
		
	}

	@Override
	public Long cargarDetalladoMostrador(Long codCentro, Long seccion, Long categoria, Long subcategoria,
			Long segmento, String tipoAprov, String soloVenta, String gamaLocal, Date diaEspejo, String sesionId) throws Exception {

		FiltrosDetalleMostrador filtros = new FiltrosDetalleMostrador(codCentro, seccion, categoria, subcategoria, segmento, tipoAprov
																	, soloVenta, gamaLocal, diaEspejo, sesionId);
		
		// 1º.- BORRAR la tabla temporal "T_DETALLADO_MOSTRADOR".
		this.borrarTablaTemp(sesionId);

		// 2º.- RECUPERAR los datos de
		// SIA[pk_apr_det_mostrador_misumi.p_consulta()] del Detallado Mostrador
		// para el filtro dado (centro, EC, ...).
		List<DetalleMostrador> lstDetalleMostrador = detalleMostradorDao.findDetalleMostradorSIA(filtros);

		// 3º.- CARGA de la tabla temporal "T_DETALLADO_MOSTRADOR".
		Long count = this.cargarTablaTemp(lstDetalleMostrador, sesionId);

		return count;
	}

	@Override
	public List<String> getFestivos(Long codCentro) throws Exception {
		return detalleMostradorDao.getFestivosByCentro(codCentro);
	}

	@Override
	public List<OfertaDetalleMostrador> getOfertasDetalleMostrador(Long codCentro, Long referencia, String sessionId)
			throws Exception {
		
		return detalleMostradorDao.getOfertas(codCentro, referencia, sessionId);
	}

	@Override
	public void exportDetalleMostrador(String cabecera, String rows, HttpServletResponse response) throws Exception{

    	try {
			ObjectMapper mapper = new ObjectMapper();
			DetalladoMostradorExcel datosCabecera = new DetalladoMostradorExcel();
			GenericExcelReport datosGrid = new GenericExcelReport();
			
			// Realizo el marshing de JSON to Object
			datosCabecera= mapper.readValue(cabecera, DetalladoMostradorExcel.class);
			datosGrid= mapper.readValue(rows, GenericExcelReport.class);
			
			excel.exportDetalladoMostrador(datosCabecera, datosGrid, response);
			
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
		
	}

	@Override
	@Transactional
	public Integer loadReferenciasNoGama(Long codCentro, String descripcion, String seccion, String categoria, String subcategoria, String segmento,
			String gamaLocal, String sesionId) throws Exception {

		detalleMostradorDao.deleteTempListadoReferenciasNoGama(sesionId);
		
		List<ReferenciaNoGamaMostrador> lstReferencias = detalladoMostradorSIADao.callProcedureRefNoGamaNew(codCentro, descripcion, seccion, categoria, subcategoria, segmento, gamaLocal);
		detalleMostradorDao.insertarTempListadoReferenciasNoGama(lstReferencias, sesionId);
		
		return null!=lstReferencias ? lstReferencias.size() : 0;
		
	}
	
	@Override
	public Page<ReferenciaNoGamaMostrador> getLstReferenciasNoGama(String sesionId, Long codCentro, Long page, Long max){
		Page<ReferenciaNoGamaMostrador> result = new Page<ReferenciaNoGamaMostrador>();
		
		List<ReferenciaNoGamaMostrador> lstReferencias = detalleMostradorDao.getReferenciasNoGamaMostrador(sesionId, codCentro, new Pagination(max, page));
		
		if (null!=lstReferencias && !lstReferencias.isEmpty()) {
			PaginationManager<ReferenciaNoGamaMostrador> paginationManager = new PaginationManagerImpl<ReferenciaNoGamaMostrador>();
			Long totalReg = detalleMostradorDao.getLstReferenciasNoGamaCount(sesionId);
			result = paginationManager.paginate(new Page<ReferenciaNoGamaMostrador>(), lstReferencias, max.intValue(),
			totalReg.intValue(), page.intValue());
		}
		
		return result;
	}
	

}
