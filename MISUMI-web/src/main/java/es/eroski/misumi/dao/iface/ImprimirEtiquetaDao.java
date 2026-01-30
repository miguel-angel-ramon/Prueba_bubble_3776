package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.*;

public interface ImprimirEtiquetaDao  {
	 public ImprimirEtiquetaResponseType imprimirEtiquetaWS(ImprimirEtiquetaRequestType imprimirEtiquetaRequest) throws Exception;
}
