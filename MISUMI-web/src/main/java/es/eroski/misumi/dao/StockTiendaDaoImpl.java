package es.eroski.misumi.dao;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.xml.namespace.QName;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.HandlerRegistry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.StockTiendaDao;
import es.eroski.misumi.dao.stockTiendaWS.StockTiendaPortType;
import es.eroski.misumi.dao.stockTiendaWS.StockTiendaServiceLocator;
import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ModificarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ModificarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaModType;
import es.eroski.misumi.service.iface.ParamCentrosOpcService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.LogHandler;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Repository
public class StockTiendaDaoImpl implements StockTiendaDao {
	
	@Autowired
	private ParamCentrosOpcService paramCentrosOpcService;	

	@Value( "${ws.stockTiendaNuevo}" )
	private String stockTienda;

	private JdbcTemplate jdbcTemplate;

	private static Logger logger = Logger.getLogger(StockTiendaDaoImpl.class);

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	@Cacheable(value = "cacheConsultaStockWS",key = "{#stockTiendaRequest}")
	//Para que sea cacheable ConsultarStockRequestType tiene que tener los métodos hashCode() y equals()
	public ConsultarStockResponseType consultaStock(ConsultarStockRequestType stockTiendaRequest, HttpSession session) throws Exception{

		StockTiendaServiceLocator locator = new StockTiendaServiceLocator();
		HandlerRegistry handlerRegistry = locator.getHandlerRegistry();

		QName qname = new QName("http://www.eroski.es/GCPV/wsdl/StockTienda2014", "StockTiendaPort");
		List chain = handlerRegistry.getHandlerChain(qname);
		HandlerInfo info = new HandlerInfo();
		info.setHandlerClass(LogHandler.class);        
		chain.add(info);

		ConsultarStockResponseType stockTiendaResponse = null;
		try {

			User user = (User) session.getAttribute("user");
			ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
			paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
			ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);

			if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
				logger.error("######################## stockTienda WS CONSULTA ############################");

				if (null != user){
					logger.error("Centro: " + user.getCentro().getCodCentro() );
					logger.error("Usuario: " + user.getCode() );
				}
				logger.error("Tipo de Mensaje: " + stockTiendaRequest.getTipoMensaje());
				if (null != stockTiendaRequest){
					logger.error("Referencia: " + Arrays.toString(stockTiendaRequest.getListaCodigosReferencia()));
				}

				logger.error("#####################################################");
			}

			URL address = new URL(stockTienda);
			StockTiendaPortType proxy = locator.getStockTiendaPort(address);
			//stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA);
			stockTiendaResponse = proxy.consultarStock(stockTiendaRequest);

		} catch (Exception e) {

			logger.error("######################## stockTienda WS CONSULTA ERROR ############################");
			User user = (User) session.getAttribute("user");
			if (null != user){
				logger.error("Centro: " + user.getCentro().getCodCentro() );
				logger.error("Usuario: " + user.getCode() );
			}
			logger.error("Tipo de Mensaje: " + stockTiendaRequest.getTipoMensaje());
			if (null != stockTiendaRequest){
				logger.error("Referencia: " + Arrays.toString(stockTiendaRequest.getListaCodigosReferencia()));
			}
			logger.error( StackTraceManager.getStackTrace(e));
			logger.error("#####################################################");
			
			//Indicamos que ha ocurrido un error.
			if (stockTiendaResponse!=null){
				stockTiendaResponse.setCodigoRespuesta("ERR");
			}

		}

		return stockTiendaResponse;
	}

	@Override
	@CacheEvict(value="cacheConsultaStockWS",allEntries=true)
	public ModificarStockResponseType modificarStock(ModificarStockRequestType modificarStockRequest, HttpSession session) throws Exception{

		StockTiendaServiceLocator locator = new StockTiendaServiceLocator();
		HandlerRegistry handlerRegistry = locator.getHandlerRegistry();

		QName qname = new QName("http://www.eroski.es/GCPV/wsdl/StockTienda2014", "StockTiendaPort");
		List chain = handlerRegistry.getHandlerChain(qname);
		HandlerInfo info = new HandlerInfo();
		info.setHandlerClass(LogHandler.class);        
		chain.add(info);

		ModificarStockResponseType modificarStockResponse = null;
		try {
			User user = (User) session.getAttribute("user");
			ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
			paramCentrosOpc.setCodLoc(user.getCentro().getCodCentro());
			ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
			
			if (paramCentroOpciones.getOpcHabil().toUpperCase().indexOf(Constantes.PERMISO_LOG) != -1) { //Si existe el parametro "99_LOG" pintamos el log
				logger.error("######################## stockTienda WS MODIFICACIÓN ############################");	
				if (null != user){
					logger.error("Centro: " + user.getCentro().getCodCentro());
					logger.error("Usuario: " + user.getCode());
					logger.error("Mac: " + user.getMac());
					logger.error("Fehca-Hora: " + Utilidades.formatearFechaHora(new Date()));
				}
				if (null != modificarStockRequest){
	
	
					for (ReferenciaModType referencia : modificarStockRequest.getListaReferencias()) {
						logger.error("Referencia: " + referencia.getCodigoReferencia());
						logger.error("Stock: " + referencia.getStock());
					}
				}
				logger.error("#####################################################");
			}
			
			URL address = new URL(stockTienda);
			StockTiendaPortType proxy = locator.getStockTiendaPort(address);
			//stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA);
			modificarStockResponse = proxy.modificarStock(modificarStockRequest);


		} catch (Exception e) {


			logger.error("######################## stockTienda WS ERROR MODIFICACIÓN ############################");
			User user = (User) session.getAttribute("user");
			if (null != user){
				logger.error("Centro: " + user.getCentro().getCodCentro());
				logger.error("Usuario: " + user.getCode());
				logger.error("Mac: " + user.getMac());
				logger.error("Fehca-Hora: " + Utilidades.formatearFechaHora(new Date()));
			}
			if (null != modificarStockRequest){


				for (ReferenciaModType referencia : modificarStockRequest.getListaReferencias()) {
					logger.error("Referencia: " + referencia.getCodigoReferencia());
					logger.error("Stock: " + referencia.getStock());
				}
			}
			logger.error( StackTraceManager.getStackTrace(e));
			logger.error("#####################################################");
		}

		return modificarStockResponse;
	}

	@Override
	public Double getStockInicial(Long codCentro, Long codArticulo) throws Exception {
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer("SELECT stock "
											+ "FROM stock_actual_centro "
											+ "WHERE cod_loc = ? "
											+ "AND cod_articulo = ?"
											);

		params.add(codCentro);
		params.add(codArticulo);

		Double stock = new Double(0); 
		try {
			stock = this.jdbcTemplate.queryForObject(query.toString(), params.toArray(), new StockMapper());
		}catch (EmptyResultDataAccessException e){
			
		}catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		return stock;
	}

	/**
	 * Mapper de la consulta de Stock Inicial
	 * @author BICAGAAN
	 *
	 */
	private class StockMapper implements RowMapper<Double> {

		public Double mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getDouble("stock");
		}
	}
	
}
