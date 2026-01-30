package es.eroski.misumi.dao.inventarioWS;

public class InventarioTiendaPortTypeProxy implements es.eroski.misumi.dao.inventarioWS.InventarioTiendaPortType {
  private String _endpoint = null;
  private es.eroski.misumi.dao.inventarioWS.InventarioTiendaPortType inventarioTiendaPortType = null;
  
  public InventarioTiendaPortTypeProxy() {
    _initInventarioTiendaPortTypeProxy();
  }
  
  public InventarioTiendaPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initInventarioTiendaPortTypeProxy();
  }
  
  private void _initInventarioTiendaPortTypeProxy() {
    try {
      inventarioTiendaPortType = (new es.eroski.misumi.dao.inventarioWS.InventarioTiendaServiceBusLocator()).getInventarioTiendaPortBus();
      if (inventarioTiendaPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)inventarioTiendaPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)inventarioTiendaPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (inventarioTiendaPortType != null)
      ((javax.xml.rpc.Stub)inventarioTiendaPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public es.eroski.misumi.dao.inventarioWS.InventarioTiendaPortType getInventarioTiendaPortType() {
    if (inventarioTiendaPortType == null)
      _initInventarioTiendaPortTypeProxy();
    return inventarioTiendaPortType;
  }
  
  public es.eroski.misumi.model.inventarioWS.ComunicarNoServidoResponseType comunicarNoServidoOperation(es.eroski.misumi.model.inventarioWS.ComunicarNoServidoRequestType part1) throws java.rmi.RemoteException{
    if (inventarioTiendaPortType == null)
      _initInventarioTiendaPortTypeProxy();
    return inventarioTiendaPortType.comunicarNoServidoOperation(part1);
  }
  
  
}