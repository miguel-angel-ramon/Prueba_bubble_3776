package es.eroski.misumi.dao;

import java.math.BigInteger;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.HandlerRegistry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.IncluirExcluirRefGamaDao;
import es.eroski.misumi.dao.referenciaCentroWS.ReferenciaCentroPortType;
import es.eroski.misumi.dao.referenciaCentroWS.ReferenciaCentroServiceLocator;
import es.eroski.misumi.model.CambioGama;
import es.eroski.misumi.model.IncluirExcluirRefGama;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.referenciaCentroWS.IncluirExcluirGamaTiendaRequest;
import es.eroski.misumi.model.referenciaCentroWS.IncluirExcluirGamaTiendaResponse;
import es.eroski.misumi.model.referenciaCentroWS.IncluirExcluirGamaTiendaResponseReferenciasCentrosReferenciaCentro;
import es.eroski.misumi.service.iface.CambioGamaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.LogHandler;
import es.eroski.misumi.util.StackTraceManager;

@Repository
public class IncluirExcluirRefGamaDaoImpl implements IncluirExcluirRefGamaDao{
	@Value( "${ws.referenciasCentro}" )
	private String referenciasCentro;
	
	private static Logger logger = Logger.getLogger(IncluirExcluirRefGamaDaoImpl.class);

	@Autowired
	private CambioGamaService cambioGamaService;
	
	@Override
	public IncluirExcluirRefGama incluirExcluirRefGama(ReferenciasCentro vReferenciasCentro,String incluirExluir, String usuario) throws Exception {
		IncluirExcluirRefGama incluirExcluirRefGama=new IncluirExcluirRefGama();
		ReferenciaCentroServiceLocator locator = new ReferenciaCentroServiceLocator();
		HandlerRegistry handlerRegistry = locator.getHandlerRegistry();
		QName qname = new QName("http://www.eroski.es/KOSMOS/wsdl/ReferenciaCentroKN", "ReferenciaCentroPort");
		List chain = handlerRegistry.getHandlerChain(qname);
		HandlerInfo info = new HandlerInfo();
		info.setHandlerClass(LogHandler.class);        
		chain.add(info);
		IncluirExcluirGamaTiendaRequest incluirExcluirGamaTiendaRequest=setearValoresWS(vReferenciasCentro,incluirExluir, usuario);
		IncluirExcluirGamaTiendaResponse incluirExcluirGamaTiendaResponse =null;
		try {
			URL address = new URL(referenciasCentro);
			ReferenciaCentroPortType proxy = locator.getReferenciaCentroPort(address);
			incluirExcluirGamaTiendaResponse = proxy.incluirExcluirGamaTienda(incluirExcluirGamaTiendaRequest);
			if(incluirExcluirGamaTiendaResponse!=null && incluirExcluirGamaTiendaResponse.getCodigoRespuesta().equals(BigInteger.ZERO)){
				if(incluirExcluirGamaTiendaResponse.getReferenciasCentros().length>0){
					final IncluirExcluirGamaTiendaResponseReferenciasCentrosReferenciaCentro[] listaRefResponse = incluirExcluirGamaTiendaResponse.getReferenciasCentros();
					incluirExcluirRefGama.setCodError(listaRefResponse[0].getCodigoError().longValue());	
					incluirExcluirRefGama.setDescError(listaRefResponse[0].getDescripcionError());
					// MISUMI-518
					if(incluirExcluirRefGama.getCodError()==0){
						Long codCentro=Long.valueOf(listaRefResponse[0].getCodigoCentro());
						Long codArt=listaRefResponse[0].getCodigoReferencia().longValue();
						String accion=listaRefResponse[0].getAccionRealizada();
						incluirExcluirRefGama.setAccion(incluirExluir);
						/* La devolución de existeGama devuelve un objeto CambioGama para:
						 * - Si existe poder actualizar los datos
						 * - Si no existe entonces insertamos un registro nuevo con los datos devueltos del ws*/
						CambioGama cambioGama=cambioGamaService.existeGama(codCentro, codArt);
						if(cambioGama!=null){
							cambioGamaService.updateCambioGama(cambioGama,accion, usuario);
						}else{
							cambioGamaService.insertarCambioGama(codCentro, codArt, accion,usuario);
						}
					}
					 
				}
				
			}else{
				incluirExcluirRefGama.setCodError(1L);	
				incluirExcluirRefGama.setDescError(Constantes.ERROR_WS_INCLUIR_EXCLUIR);
			}
			return incluirExcluirRefGama;

		} catch (Exception e) {
			logger.error("######################## incluirExcluirRefGama WS CONSULTA ERROR ############################");
			logger.error(" incluirExcluirRefGama COD CENTRO:"+vReferenciasCentro.getCodCentro());
			logger.error(" incluirExcluirRefGama COD ART:"+vReferenciasCentro.getCodArt());
			logger.error( StackTraceManager.getStackTrace(e));
			logger.error("#####################################################");
			incluirExcluirRefGama.setCodError(1L);	
			incluirExcluirRefGama.setDescError(Constantes.ERROR_WS_INCLUIR_EXCLUIR);
			return incluirExcluirRefGama;
		}
	}
	
	private IncluirExcluirGamaTiendaRequest setearValoresWS(ReferenciasCentro vReferenciasCentro,String incluirExluir, String usuario) throws Exception {
		final BigInteger[] referencias = new BigInteger[1];
		referencias[0] = BigInteger.valueOf(vReferenciasCentro.getCodArt());
		final BigInteger[] centros = new BigInteger[1];
		centros[0] = BigInteger.valueOf(vReferenciasCentro.getCodCentro());
		IncluirExcluirGamaTiendaRequest incluirExcluirGamaTiendaRequest=new IncluirExcluirGamaTiendaRequest();
		incluirExcluirGamaTiendaRequest.setReferencias(referencias);
		incluirExcluirGamaTiendaRequest.setCentros(centros);
		if(incluirExluir.equals("1") || incluirExluir.equals("4")){
			incluirExcluirGamaTiendaRequest.setAccion(Constantes.INCLUIR);
		}else{
			incluirExcluirGamaTiendaRequest.setAccion(Constantes.EXCLUIR);
		}
		incluirExcluirGamaTiendaRequest.setUsuario(usuario);
		
		return incluirExcluirGamaTiendaRequest;
	}

}
