package es.eroski.misumi.dao;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.HandlerRegistry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.facingVegalsaWS.FacingVegalsaPortType;
import es.eroski.misumi.dao.facingVegalsaWS.FacingVegalsaServiceLocator;
import es.eroski.misumi.dao.iface.FacingAltoAnchoDao;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.facingVegalsaWS.ModificarMedidasFacingVegalsaRequestType;
import es.eroski.misumi.model.facingVegalsaWS.ModificarMedidasFacingVegalsaResponseType;
import es.eroski.misumi.model.facingVegalsaWS.ReferenciaMedidaType;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.LogHandler;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Repository
public class FacingAltoAnchoDaoImpl extends CentroParametrizadoDaoImpl implements FacingAltoAnchoDao {
	
	@Value( "${ws.facingVegalsa}" )
	private String facingAltoAncho;

	private static Logger logger = Logger.getLogger(FacingAltoAnchoDaoImpl.class);

	@Override
	public ModificarMedidasFacingVegalsaResponseType modificarFacingAltoAncho(ModificarMedidasFacingVegalsaRequestType facingAltoAnchoRequest, HttpSession session) throws Exception{

		FacingVegalsaServiceLocator locator = new FacingVegalsaServiceLocator();
		HandlerRegistry handlerRegistry = locator.getHandlerRegistry();

		QName qname = new QName("http://www.eroski.es/GCPV/wsdl/Facing", "FacingAltoAnchoPort");
		List chain = handlerRegistry.getHandlerChain(qname);
		HandlerInfo info = new HandlerInfo();
		info.setHandlerClass(LogHandler.class);        
		chain.add(info);

		ModificarMedidasFacingVegalsaResponseType facingAltoAnchoResponse = null;
		try {
			URL address = new URL(facingAltoAncho);
			FacingVegalsaPortType proxy = locator.getFacingVegalsaPort(address);
			facingAltoAnchoResponse = proxy.modificarMedidasFacing(facingAltoAnchoRequest);

		} catch (Exception e) {
			logger.error("######################## facingVegalsa WS ERROR MODIFICACION MEDIDAS ############################");
			User user = (User)session.getAttribute("user");
			if (null != user){
				logger.error("Centro: " + user.getCentro().getCodCentro());
				logger.error("Usuario: " + user.getCode());
				logger.error("Mac: " + user.getMac());
				logger.error("Fecha-Hora: " + Utilidades.formatearFechaHora(new Date()));
			}
			if (null != facingAltoAnchoRequest){
				
				ReferenciaMedidaType arrayReferencias[] = facingAltoAnchoRequest.getReferencias();
				
			    for(ReferenciaMedidaType referencia : arrayReferencias) {
			    	
			    	if(referencia != null && referencia.getCodigoReferencia() != null) {
			    		logger.error("Tipo modificacion facing: "+facingAltoAnchoRequest.getTipo());
			    		logger.error("Referencia: "+referencia.getCodigoReferencia());
						logger.error("Facing Alto: "+referencia.getFacingAlto());
						logger.error("Facing Ancho: "+referencia.getFacingAncho());
						logger.error("Tipo Etiqueta: "+referencia.getTipoEtiqueta());
			       }
			    }
				
			}
			logger.error( StackTraceManager.getStackTrace(e));
			logger.error("#####################################################");
		}

		return facingAltoAnchoResponse;
	}

}
