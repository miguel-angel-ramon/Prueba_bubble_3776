/**
 * ImprimirEtiquetaPort.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.dao.imprimirEtiquetaTiendaWS;

import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.ImprimirEtiquetaRequestType;
import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.ImprimirEtiquetaResponseType;

public interface ImprimirEtiquetaPort extends java.rmi.Remote {
    public ImprimirEtiquetaResponseType imprimirEtiqueta(ImprimirEtiquetaRequestType inputParameters) throws java.rmi.RemoteException;
}
