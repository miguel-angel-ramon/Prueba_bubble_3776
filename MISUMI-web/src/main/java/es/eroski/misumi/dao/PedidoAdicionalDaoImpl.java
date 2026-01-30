package es.eroski.misumi.dao;

import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.HandlerRegistry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.PedidoAdicionalDao;
import es.eroski.misumi.dao.pedidosPBLWS.PedidosPBLSOAP;
import es.eroski.misumi.dao.pedidosPBLWS.WebServiceVRInboundLocator;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.pedidosPBLWS.ArrayOfDetalladoPedidoDetalladoPedido;
import es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalPedidoAdicional;
import es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloRequestValidarArticuloValidarArticulo;
import es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo;
import es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoModificarReponse;
import es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoObtenerRequest;
import es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoObtenerResponse;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesRequest;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesResponse;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerRequest;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerResponse;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse;
import es.eroski.misumi.util.LogHandler;
import es.eroski.misumi.util.StackTraceManager;



@Repository
public class PedidoAdicionalDaoImpl implements PedidoAdicionalDao {
	
	 @Value( "${ws.pedidoAdicional}" )
	
	 private String pedidosAdicionalWsdl;
	 
	 private PedidosPBLSOAP proxy;
	 
	 private static Logger logger = Logger.getLogger(PedidoAdicionalDaoImpl.class);
	
	 private void defineClient() throws Exception{
		 WebServiceVRInboundLocator locator = new WebServiceVRInboundLocator();
		 HandlerRegistry handlerRegistry = locator.getHandlerRegistry();
		
		 QName qname = new QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "PedidosPBLSOAPInbound");
		 List chain = handlerRegistry.getHandlerChain(qname);
		 HandlerInfo info = new HandlerInfo();
		 info.setHandlerClass(LogHandler.class);        
		 chain.add(info);
		 
		//Error
		 try {
			 URL address = new URL(pedidosAdicionalWsdl);
			 this.proxy = locator.getPedidosPBLSOAPInbound(address);
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
			 
		//this.proxy = new PedidosPBLSOAPProxy(this.pedidosAdicionalWsdl);
	 }

	 @Override
	 @CacheEvict(value = {"cachePedidosAdicionalesPBLWS", "cacheDetalladoPedidoPBLWS"}, allEntries = true)
	public ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo[] validarArticulo(ArrayOfValidarArticuloRequestValidarArticuloValidarArticulo[] validarRequest, HttpSession session ) throws Exception{
		 
		 this.defineClient();
		 
		 ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo [] validarArticuloResponse = null;
		 try {
			 validarArticuloResponse = proxy.validarArticulo(validarRequest);
		} catch (Exception e) {
			
			logger.error("######################## pedidosPBL WS ############################");
			User user = (User) session.getAttribute("user");
			if (null != user){
				logger.error("Centro: " + user.getCentro().getCodCentro() );
				logger.error("Usuario: " + user.getCode() );
			}
			logger.error( StackTraceManager.getStackTrace(e));
			logger.error("#####################################################");
		//	logger.error(StackTraceManager.getStackTrace(e));
			
		//	ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo[] arrayOfValidarArticuloResponseValidarArticuloValidarArticulo = {new ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo()};
		//	arrayOfValidarArticuloResponseValidarArticuloValidarArticulo[0].setCodigoRespuesta("1");
		//	arrayOfValidarArticuloResponseValidarArticuloValidarArticulo[0].setDescripcionRespuesta("");
		//	return arrayOfValidarArticuloResponseValidarArticuloValidarArticulo;
		}
		 
		 return validarArticuloResponse;
		

	 }
	 @Override
	 @Cacheable(value = "cacheDetalladoPedidoPBLWS",key = "{#detalladoPedidoObtenerRequest}")
	//Para que sea cacheable DetalladoPedidoObtenerRequest tiene que tener los métodos hashCode() y equals()
	 public es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoObtenerResponse detalladoPedidoObtener(DetalladoPedidoObtenerRequest detalladoPedidoObtenerRequest, HttpSession session) throws Exception{
		 this.defineClient();
		 try {
			 return proxy.detalladoPedidoObtener(detalladoPedidoObtenerRequest);
		} catch (Exception e) {
			
			logger.error("######################## pedidosPBL WS ############################");
			User user = (User) session.getAttribute("user");
			if (null != user){
				logger.error("Centro: " + user.getCentro().getCodCentro() );
				logger.error("Usuario: " + user.getCode() );
			}
			logger.error( StackTraceManager.getStackTrace(e));
			logger.error("#####################################################");
			
			DetalladoPedidoObtenerResponse detalladoPedidoObtener = new DetalladoPedidoObtenerResponse();
			detalladoPedidoObtener.setCodigoRespuesta("1");
			return detalladoPedidoObtener;
		}
		 

	 }
	 @Override
	 @CacheEvict(value = {"cachePedidosAdicionalesPBLWS", "cacheDetalladoPedidoPBLWS"}, allEntries = true)
	public PedidoAdicionalContarClasesResponse contarClases(PedidoAdicionalContarClasesRequest contarClasesRequest, HttpSession session) throws Exception{
		this.defineClient();
		try {
			return proxy.pedidoAdicionalContarClases(contarClasesRequest);
		} catch (Exception e) {
			
			logger.error("######################## pedidosPBL WS ############################");
			User user = (User) session.getAttribute("user");
			if (null != user){
				logger.error("Centro: " + user.getCentro().getCodCentro() );
				logger.error("Usuario: " + user.getCode() );
			}
			logger.error( StackTraceManager.getStackTrace(e));
			logger.error("#####################################################");
			
			PedidoAdicionalContarClasesResponse pedidoAdicionalContarClasesResponse = new PedidoAdicionalContarClasesResponse();
			pedidoAdicionalContarClasesResponse.setCodigoRespuesta("1");
			return pedidoAdicionalContarClasesResponse;
		}

	 } 
	 
	 @Override
	 @CacheEvict(value = {"cachePedidosAdicionalesPBLWS", "cacheDetalladoPedidoPBLWS"}, allEntries = true)
	public PedidoAdicionalResponse[] eliminarPedido(ArrayOfPedidoAdicionalPedidoAdicional[] eliminarRequest, HttpSession session) throws Exception{
		 
		 this.defineClient();
		 PedidoAdicionalResponse[] pedidoAdicionalResponse = null;
		try {
			pedidoAdicionalResponse = proxy.pedidoAdicionalEliminar(eliminarRequest);
		} catch (Exception e) {
			logger.error("######################## pedidosPBL WS ############################");
			User user = (User) session.getAttribute("user");
			if (null != user){
				logger.error("Centro: " + user.getCentro().getCodCentro() );
				logger.error("Usuario: " + user.getCode() );
			}
			logger.error( StackTraceManager.getStackTrace(e));
			logger.error("#####################################################");
		//	logger.error(StackTraceManager.getStackTrace(e));
		//	PedidoAdicionalResponse [] pedidoAdicionalResponse = {new PedidoAdicionalResponse()};
		//	pedidoAdicionalResponse[0].setCodigoRespuesta("1");
		//	return pedidoAdicionalResponse;
		}
		 return pedidoAdicionalResponse;
	 }
	 
	 @Override
	 @CacheEvict(value = {"cachePedidosAdicionalesPBLWS", "cacheDetalladoPedidoPBLWS"}, allEntries = true)
	public PedidoAdicionalResponse[] insertarPedido(ArrayOfPedidoAdicionalPedidoAdicional[] insertarRequest, HttpSession session) throws Exception{
		 
		 this.defineClient();
		 try {
			 return proxy.pedidoAdicionalInsertar(insertarRequest);
		} catch (Exception e) {
			
			logger.error("######################## pedidosPBL WS ############################");
			User user = (User) session.getAttribute("user");
			if (null != user){
				logger.error("Centro: " + user.getCentro().getCodCentro() );
				logger.error("Usuario: " + user.getCode() );
			}
			logger.error( StackTraceManager.getStackTrace(e));
			logger.error("#####################################################");
			
			PedidoAdicionalResponse [] pedidoAdicionalResponse = {new PedidoAdicionalResponse()};
			pedidoAdicionalResponse[0].setCodigoRespuesta("1");
			return pedidoAdicionalResponse;
		}
		 
	 }
	 
	 @Override
	 @CacheEvict(value = {"cachePedidosAdicionalesPBLWS", "cacheDetalladoPedidoPBLWS"}, allEntries = true)
	public PedidoAdicionalResponse[] modificarPedido(ArrayOfPedidoAdicionalPedidoAdicional[] modificarRequest, HttpSession session) throws Exception{
		 
		 this.defineClient();
		 PedidoAdicionalResponse[] pedidoAdicionalResponse = null;
		 try {
			 pedidoAdicionalResponse = proxy.pedidoAdicionalModificar(modificarRequest);
		} catch (Exception e) {
			
			logger.error("######################## pedidosPBL WS ############################");
			User user = (User) session.getAttribute("user");
			if (null != user){
				logger.error("Centro: " + user.getCentro().getCodCentro() );
				logger.error("Usuario: " + user.getCode() );
			}
			logger.error( StackTraceManager.getStackTrace(e));
			logger.error("#####################################################");
			
		//	PedidoAdicionalResponse [] pedidoAdicionalResponse = {new PedidoAdicionalResponse()};
		//	pedidoAdicionalResponse[0].setCodigoRespuesta("1");
		//	return pedidoAdicionalResponse;
		}
		 
		 return pedidoAdicionalResponse;
	 }
	 
	@Override
	@Cacheable(value = "cachePedidosAdicionalesPBLWS",key = "{#obtenerRequest}")
	//Para que sea cacheable PedidoAdicionalObtenerRequest tiene que tener los métodos hashCode() y equals()
	public PedidoAdicionalObtenerResponse obtenerPedido(PedidoAdicionalObtenerRequest obtenerRequest, HttpSession session) throws Exception{
		 
		 this.defineClient();
		 PedidoAdicionalObtenerResponse pedidoAdicionalObtenerResponse = null;
		 try {
			 pedidoAdicionalObtenerResponse =  proxy.pedidoAdicionalObtener(obtenerRequest);
		} catch (Exception e) {
			 logger.error("######################## pedidosPBL WS ############################");
				User user = (User) session.getAttribute("user");
				if (null != user){
					logger.error("Centro: " + user.getCentro().getCodCentro() );
					logger.error("Usuario: " + user.getCode() );
				}
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
				
			
			pedidoAdicionalObtenerResponse = new PedidoAdicionalObtenerResponse();
			pedidoAdicionalObtenerResponse.setCodigoRespuesta("1");
		//	return pedidoAdicionalObtenerResponse;
		}
		 return pedidoAdicionalObtenerResponse;
	 } 
	 
	 @Override
	 @CacheEvict(value = {"cachePedidosAdicionalesPBLWS", "cacheDetalladoPedidoPBLWS"}, allEntries = true)
	 public DetalladoPedidoModificarReponse detalladoPedidoModificar(ArrayOfDetalladoPedidoDetalladoPedido[] detalladoPedidoModificarRequest, HttpSession session) throws Exception{
		 this.defineClient();
		 try {
			return proxy.detalladoPedidoModificar(detalladoPedidoModificarRequest);
		} catch (Exception e) {
			
			logger.error("######################## pedidosPBL WS ############################");
			User user = (User) session.getAttribute("user");
			if (null != user){
				logger.error("Centro: " + user.getCentro().getCodCentro() );
				logger.error("Usuario: " + user.getCode() );
			}
			logger.error( StackTraceManager.getStackTrace(e));
			logger.error("#####################################################");
			
			
			DetalladoPedidoModificarReponse detalladoPedidoModificarReponse = new DetalladoPedidoModificarReponse();
			detalladoPedidoModificarReponse.setCodigoRespuesta("1");
			return detalladoPedidoModificarReponse;
		}
		 
	 }
	 
}
