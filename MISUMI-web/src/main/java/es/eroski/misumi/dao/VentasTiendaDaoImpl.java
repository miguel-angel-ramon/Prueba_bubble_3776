package es.eroski.misumi.dao;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.HandlerRegistry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.VentasTiendaDao;
import es.eroski.misumi.dao.ventasTiendaWS.VentasTiendaPortType;
import es.eroski.misumi.dao.ventasTiendaWS.VentasTiendaServiceBusLocator;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.ventasTiendaWS.VentasTiendaRequestType;
import es.eroski.misumi.model.ventasTiendaWS.VentasTiendaResponseType;
import es.eroski.misumi.util.LogHandler;
import es.eroski.misumi.util.StackTraceManager;

@Repository
public class VentasTiendaDaoImpl extends CentroParametrizadoDaoImpl implements VentasTiendaDao {

	@Value("${ws.ventasTiendaNuevo}")
	private String ventasTienda;

	private static Logger logger = Logger.getLogger(StockTiendaDaoImpl.class);

	@Override
	@Cacheable(value = "cacheVentasTiendaWS",key = "{#ventasTiendaRequest}")
	//Para que sea cacheable VentasTiendaRequestType tiene que tener los métodos hashCode() y equals()
	public VentasTiendaResponseType consultaVentas(
			VentasTiendaRequestType ventasTiendaRequest, HttpSession session) throws Exception {

		VentasTiendaServiceBusLocator locator = new VentasTiendaServiceBusLocator();
		HandlerRegistry handlerRegistry = locator.getHandlerRegistry();
		QName qname = new QName("http://www.eroski.es/gcpv/wsdl/VentasTienda",
				"VentasTiendaPortBus");
		List chain = handlerRegistry.getHandlerChain(qname);
		HandlerInfo info = new HandlerInfo();
		info.setHandlerClass(LogHandler.class);
		chain.add(info);

		VentasTiendaResponseType ventasTiendaResponse = null;
		try {
			URL address = new URL(ventasTienda);
			VentasTiendaPortType proxy = locator
					.getVentasTiendaPortBus(address);
			ventasTiendaResponse = proxy
					.ventasTiendaOperation(ventasTiendaRequest);
			return ventasTiendaResponse;

		} catch (Exception e) {
			logger.error("######################## ventasTienda WS CONSULTA ERROR ############################");
			User user = (User) session.getAttribute("user");
			if (null != user){
				logger.error("Centro: " + user.getCentro().getCodCentro() );
				logger.error("Usuario: " + user.getCode() );
			}
			if(ventasTiendaRequest != null){
				logger.error("Fecha desde: " + ventasTiendaRequest.getFechaDesde());
				logger.error("Fecha hasta: " + ventasTiendaRequest.getFechaHasta());
				if(ventasTiendaRequest.getListaReferencias() != null && ventasTiendaRequest.getListaReferencias().length > 0){
					logger.error("Referencias: " + Arrays.toString(ventasTiendaRequest.getListaReferencias()));
				}
			}
			logger.error(StackTraceManager.getStackTrace(e));
			logger.error("#####################################################");
			
			e.printStackTrace();
			ventasTiendaResponse.setCodigoRespuesta("1");
			return ventasTiendaResponse;
		}
	}
}
