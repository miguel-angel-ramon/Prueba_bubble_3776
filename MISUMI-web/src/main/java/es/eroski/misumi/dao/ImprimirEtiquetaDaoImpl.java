package es.eroski.misumi.dao;

import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.HandlerRegistry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.ImprimirEtiquetaDao;
import es.eroski.misumi.dao.imprimirEtiquetaTiendaWS.ImprimirEtiquetaPort;
import es.eroski.misumi.dao.imprimirEtiquetaTiendaWS.ImprimirEtiquetaTiendaServiceLocator;
import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.ImprimirEtiquetaRequestType;
import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.ImprimirEtiquetaResponseType;
import es.eroski.misumi.util.LogHandler;
import es.eroski.misumi.util.StackTraceManager;

@Repository
public class ImprimirEtiquetaDaoImpl implements ImprimirEtiquetaDao {

	@Value("${ws.imprimirEtiqueta}")
	private String imprimirEtiquetaWsdl;

	private static Logger logger = Logger.getLogger(ImprimirEtiquetaDaoImpl.class);

	@Override
	public ImprimirEtiquetaResponseType imprimirEtiquetaWS(ImprimirEtiquetaRequestType imprimirEtiquetaRequest)
			throws Exception {

		ImprimirEtiquetaTiendaServiceLocator locator = new ImprimirEtiquetaTiendaServiceLocator();
		HandlerRegistry handlerRegistry = locator.getHandlerRegistry();
		QName qname = new QName("http://www.eroski.es/gcpv/wsdl/imprimirEtiquetaTienda", "ImprimirEtiquetaTiendaSoap11Endpoint");
		List chain = handlerRegistry.getHandlerChain(qname);
		HandlerInfo info = new HandlerInfo();
		info.setHandlerClass(LogHandler.class);        
		chain.add(info);
		
		ImprimirEtiquetaPort port = null;
		try {
			URL address = new URL(imprimirEtiquetaWsdl);
			port = locator.getImprimirEtiquetaTiendaSoap11Endpoint(address);
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		return port.imprimirEtiqueta(imprimirEtiquetaRequest);
	}
}
