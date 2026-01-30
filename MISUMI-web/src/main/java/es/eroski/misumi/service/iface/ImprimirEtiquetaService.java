package es.eroski.misumi.service.iface;

import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.ImprimirEtiquetaRequestType;
import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.ImprimirEtiquetaResponseType;

public interface ImprimirEtiquetaService {

   
	 public ImprimirEtiquetaResponseType imprimirEtiquetaWS(ImprimirEtiquetaRequestType imprimirEtiquetaRequest) throws Exception;
	
	 public ImprimirEtiquetaRequestType createImprimirEtiquetaRequestType() throws Exception;
	 
	 public boolean isOkImprimirEtiquetaResponse(ImprimirEtiquetaResponseType imprimirEtiquetaResponse) throws Exception;
}
