package es.eroski.misumi.dao.imprimirEtiquetaTiendaWS;

import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.ImprimirEtiquetaRequestType;
import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.ImprimirEtiquetaResponseType;

public class ImprimirEtiquetaPortProxy implements ImprimirEtiquetaPort {
  private String _endpoint = null;
  private ImprimirEtiquetaPort imprimirEtiquetaPort = null;
  
  public ImprimirEtiquetaPortProxy() {
    _initImprimirEtiquetaPortProxy();
  }
  
  public ImprimirEtiquetaPortProxy(String endpoint) {
    _endpoint = endpoint;
    _initImprimirEtiquetaPortProxy();
  }
  
  private void _initImprimirEtiquetaPortProxy() {
    try {
      imprimirEtiquetaPort = (new ImprimirEtiquetaTiendaServiceLocator()).getImprimirEtiquetaTiendaSoap11Endpoint();
      if (imprimirEtiquetaPort != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)imprimirEtiquetaPort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)imprimirEtiquetaPort)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (imprimirEtiquetaPort != null)
      ((javax.xml.rpc.Stub)imprimirEtiquetaPort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ImprimirEtiquetaPort getImprimirEtiquetaPort() {
    if (imprimirEtiquetaPort == null)
      _initImprimirEtiquetaPortProxy();
    return imprimirEtiquetaPort;
  }
  
  public ImprimirEtiquetaResponseType imprimirEtiqueta(ImprimirEtiquetaRequestType inputParameters) throws java.rmi.RemoteException{
    if (imprimirEtiquetaPort == null)
      _initImprimirEtiquetaPortProxy();
    return imprimirEtiquetaPort.imprimirEtiqueta(inputParameters);
  }
  
  
}