package es.eroski.misumi.dao;

import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.HandlerRegistry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.InventarioTiendaDao;
import es.eroski.misumi.dao.inventarioWS.InventarioTiendaPortType;
import es.eroski.misumi.dao.inventarioWS.InventarioTiendaServiceBusLocator;
import es.eroski.misumi.filter.SecurityFilter;
import es.eroski.misumi.model.inventarioWS.ComunicarNoServidoRequestType;
import es.eroski.misumi.model.inventarioWS.ComunicarNoServidoResponseType;
import es.eroski.misumi.util.LogHandler;
import es.eroski.misumi.util.StackTraceManager;




@Repository
public class InventarioTiendaDaoImpl implements InventarioTiendaDao {

	@Value("${ws.inventarioTienda}")
	private String inventarioTiendaWsdl;
	
	private static Logger logger = Logger.getLogger(InventarioTiendaDaoImpl.class);

	@Override
	public ComunicarNoServidoResponseType comunicarNoServido(
			ComunicarNoServidoRequestType comunicarRequest) throws Exception {
		
		InventarioTiendaServiceBusLocator locator = new InventarioTiendaServiceBusLocator();
		HandlerRegistry handlerRegistry = locator.getHandlerRegistry();
		QName qname = new QName("http://www.eroski.es/gcpv/wsdl/InventarioTienda", "InventarioTiendaPortBus");
		 List chain = handlerRegistry.getHandlerChain(qname);
		 HandlerInfo info = new HandlerInfo();
		 info.setHandlerClass(LogHandler.class);        
		 chain.add(info);
		 
		 InventarioTiendaPortType port = null;
		 try {
			 URL address = new URL(inventarioTiendaWsdl);
			 port = locator.getInventarioTiendaPortBus(address);
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		return port.comunicarNoServidoOperation(comunicarRequest);

	}

}
