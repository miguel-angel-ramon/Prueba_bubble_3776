package es.eroski.misumi.dao;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.HandlerRegistry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.PlanogramaCentroDao;
import es.eroski.misumi.dao.planogramasCentroWS.PlanogramasCentroWS;
import es.eroski.misumi.dao.planogramasCentroWS.PlanogramasCentroWSServiceLocator;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.planogramasCentroWS.ConsultaPlanogramaPorReferenciaRequestType;
import es.eroski.misumi.model.planogramasCentroWS.ConsultaPlanogramaPorReferenciaResponseType;
import es.eroski.misumi.util.LogHandler;
import es.eroski.misumi.util.StackTraceManager;
 	
@Repository
public class PlanogramaCentroDaoImpl implements PlanogramaCentroDao{
	 
	 @Value( "${ws.planogramasCentro}" )
	 private String planogramasCentroWsdl;
	 
	 private static Logger logger = Logger.getLogger(PlanogramaCentroDaoImpl.class);
	

	@Override
	@Cacheable(value = "cachePlanogramasCentroWS",key = "{#vReferenciasCentro.codCentro, #vReferenciasCentro.codArt}")
	public ConsultaPlanogramaPorReferenciaResponseType findPlanogramasCentroWS(
			ReferenciasCentro vReferenciasCentro, HttpSession session) throws Exception {
		
		PlanogramasCentroWSServiceLocator locator = new PlanogramasCentroWSServiceLocator();
		HandlerRegistry handlerRegistry = locator.getHandlerRegistry();
		
		QName qname = new QName("http://webservice.gcnp.eroski.es/", "PlanogramasCentroWSPort");
		List chain = handlerRegistry.getHandlerChain(qname);
		HandlerInfo info = new HandlerInfo();
		info.setHandlerClass(LogHandler.class);        
		chain.add(info);
	
	
		try {
			 URL address = new URL(planogramasCentroWsdl);
			 BigDecimal[] refArray= new BigDecimal[1];
				refArray[0]= new BigDecimal(vReferenciasCentro.getCodArt());
				
				ConsultaPlanogramaPorReferenciaRequestType argument=new ConsultaPlanogramaPorReferenciaRequestType(new BigDecimal(vReferenciasCentro.getCodCentro()),refArray);
				//PlanogramasCentroWSProxy planogramasCentroWSProxy = new PlanogramasCentroWSProxy(this.planogramasCentroWsdl);
				PlanogramasCentroWS planogramasCentroWSProxy = locator.getPlanogramasCentroWSPort(address);
				ConsultaPlanogramaPorReferenciaResponseType resultado = planogramasCentroWSProxy.consultaPlanogramaPorReferencia(argument);
				return resultado;
		} catch (Exception e) {
			
			logger.error("######################## planogramaCentro WS ############################");
			User user = (User) session.getAttribute("user");
			if (null != user){
				logger.error("Centro: " + user.getCentro().getCodCentro() );
				logger.error("Usuario: " + user.getCode() );
			}
			if (null != vReferenciasCentro){
				logger.error("Referencia: " + vReferenciasCentro.getCodArt().toString());
			}
			logger.error( StackTraceManager.getStackTrace(e));
			logger.error("#####################################################");

			
			ConsultaPlanogramaPorReferenciaResponseType resultado = new ConsultaPlanogramaPorReferenciaResponseType();
			resultado.setCodigoRespuesta("1");
			return resultado;
		}
		
	 }
   
}


