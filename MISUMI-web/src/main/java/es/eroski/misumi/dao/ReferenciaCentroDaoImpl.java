package es.eroski.misumi.dao;

import java.net.URL;
import java.util.Arrays;
import java.util.Date;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.facingVegalsaWS.FacingVegalsaPortType;
import es.eroski.misumi.dao.facingVegalsaWS.FacingVegalsaServiceLocator;
import es.eroski.misumi.dao.iface.FacingVegalsaDao;
import es.eroski.misumi.dao.iface.ReferenciaCentroDao;
import es.eroski.misumi.dao.referenciaCentroWS.ReferenciaCentroPortType;
import es.eroski.misumi.dao.referenciaCentroWS.ReferenciaCentroServiceLocator;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaRequestType;
import es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaResponseType;
import es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaRequestType;
import es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaResponseType;
import es.eroski.misumi.model.facingVegalsaWS.ReferenciaModType;
import es.eroski.misumi.model.referenciaCentroWS.ConsultaEtiquetaFacingRequest;
import es.eroski.misumi.model.referenciaCentroWS.ConsultaEtiquetaFacingResponse;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.LogHandler;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.model.FacingVegalsaRequest;
import es.eroski.misumi.model.FacingVegalsaResponse;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Utilidades;

@Repository
public class ReferenciaCentroDaoImpl implements ReferenciaCentroDao{
	@Value( "${ws.referenciasCentro}" )
	private String referenciasCentro;
	
	private static Logger logger = Logger.getLogger(ReferenciaCentroDaoImpl.class);

	@Cacheable(value = "cacheConsultaReferenciaCentroWS",key = "{#tipoEtiquetaRequest}")
	//Para que sea cacheable ConsultaEtiquetaFacingRequest tiene que tener los métodos hashCode() y equals()
	@Override
	public ConsultaEtiquetaFacingResponse consultaTipoEtiquetaFacing(ConsultaEtiquetaFacingRequest tipoEtiquetaRequest,
			HttpSession session) throws Exception {
		
		ReferenciaCentroServiceLocator locator = new ReferenciaCentroServiceLocator();
		HandlerRegistry handlerRegistry = locator.getHandlerRegistry();

		QName qname = new QName("http://www.eroski.es/KOSMOS/wsdl/ReferenciaCentroKN", "ReferenciaCentroPort");
		List chain = handlerRegistry.getHandlerChain(qname);
		HandlerInfo info = new HandlerInfo();
		info.setHandlerClass(LogHandler.class);        
		chain.add(info);

		ConsultaEtiquetaFacingResponse etiquetaFacingResponse =null;
		try {
			URL address = new URL(referenciasCentro);
			ReferenciaCentroPortType proxy = locator.getReferenciaCentroPort(address);
			etiquetaFacingResponse = proxy.consultaEtiquetaFacing(tipoEtiquetaRequest);

		} catch (Exception e) {
			logger.error("######################## referenciaCentro WS CONSULTA ERROR ############################");
			User user = (User) session.getAttribute("user");
			if (null != user){
				logger.error("Centro: " + user.getCentro().getCodCentro() );
			}
			if (null != tipoEtiquetaRequest){
				logger.error("Referencia: " + tipoEtiquetaRequest.getCodigoReferencia());
				logger.error("Facing: " + tipoEtiquetaRequest.getFacing());
			}
			logger.error( StackTraceManager.getStackTrace(e));
			logger.error("#####################################################");
		}

		return etiquetaFacingResponse;
	}

}
