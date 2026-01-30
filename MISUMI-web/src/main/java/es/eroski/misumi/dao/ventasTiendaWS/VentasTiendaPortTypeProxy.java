package es.eroski.misumi.dao.ventasTiendaWS;

public class VentasTiendaPortTypeProxy implements es.eroski.misumi.dao.ventasTiendaWS.VentasTiendaPortType {
  private String _endpoint = null;
  private es.eroski.misumi.dao.ventasTiendaWS.VentasTiendaPortType ventasTiendaPortType = null;
  
  public VentasTiendaPortTypeProxy() {
    _initVentasTiendaPortTypeProxy();
  }
  
  public VentasTiendaPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initVentasTiendaPortTypeProxy();
  }
  
  private void _initVentasTiendaPortTypeProxy() {
    try {
      ventasTiendaPortType = (new es.eroski.misumi.dao.ventasTiendaWS.VentasTiendaServiceBusLocator()).getVentasTiendaPortBus();
      if (ventasTiendaPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)ventasTiendaPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)ventasTiendaPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (ventasTiendaPortType != null)
      ((javax.xml.rpc.Stub)ventasTiendaPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public es.eroski.misumi.dao.ventasTiendaWS.VentasTiendaPortType getVentasTiendaPortType() {
    if (ventasTiendaPortType == null)
      _initVentasTiendaPortTypeProxy();
    return ventasTiendaPortType;
  }
  
  public es.eroski.misumi.model.ventasTiendaWS.VentasTiendaResponseType ventasTiendaOperation(es.eroski.misumi.model.ventasTiendaWS.VentasTiendaRequestType part1) throws java.rmi.RemoteException{
    if (ventasTiendaPortType == null)
      _initVentasTiendaPortTypeProxy();
    return ventasTiendaPortType.ventasTiendaOperation(part1);
  }
  
  
}