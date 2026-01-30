package es.eroski.misumi.dao.alarmasPLUSWS;

public class PLUSTiendaPortTypeProxy implements es.eroski.misumi.dao.alarmasPLUSWS.PLUSTiendaPortType {
  private String _endpoint = null;
  private es.eroski.misumi.dao.alarmasPLUSWS.PLUSTiendaPortType pLUSTiendaPortType = null;
  
  public PLUSTiendaPortTypeProxy() {
    _initPLUSTiendaPortTypeProxy();
  }
  
  public PLUSTiendaPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initPLUSTiendaPortTypeProxy();
  }
  
  private void _initPLUSTiendaPortTypeProxy() {
    try {
      pLUSTiendaPortType = (new es.eroski.misumi.dao.alarmasPLUSWS.PLUSTiendaServiceLocator()).getPLUSTiendaPort();
      if (pLUSTiendaPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)pLUSTiendaPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)pLUSTiendaPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (pLUSTiendaPortType != null)
      ((javax.xml.rpc.Stub)pLUSTiendaPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public es.eroski.misumi.dao.alarmasPLUSWS.PLUSTiendaPortType getPLUSTiendaPortType() {
    if (pLUSTiendaPortType == null)
      _initPLUSTiendaPortTypeProxy();
    return pLUSTiendaPortType;
  }
  
  public es.eroski.misumi.model.alarmasPLUSWS.ConsultarPLUSResponseType consultarPLUS(es.eroski.misumi.model.alarmasPLUSWS.ConsultarPLUSRequestType part1) throws java.rmi.RemoteException{
    if (pLUSTiendaPortType == null)
      _initPLUSTiendaPortTypeProxy();
    return pLUSTiendaPortType.consultarPLUS(part1);
  }
  
  public es.eroski.misumi.model.alarmasPLUSWS.ModificarPLUSResponseType modificarPLUS(es.eroski.misumi.model.alarmasPLUSWS.ModificarPLUSRequestType part3) throws java.rmi.RemoteException{
    if (pLUSTiendaPortType == null)
      _initPLUSTiendaPortTypeProxy();
    return pLUSTiendaPortType.modificarPLUS(part3);
  }
  
  
}