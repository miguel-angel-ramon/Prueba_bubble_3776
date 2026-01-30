package es.eroski.misumi.dao.pedidosCentroWS;

public class PedidosCentroWSProxy implements es.eroski.misumi.dao.pedidosCentroWS.PedidosCentroWS {
  private String _endpoint = null;
  private es.eroski.misumi.dao.pedidosCentroWS.PedidosCentroWS pedidosCentroWS = null;
  
  public PedidosCentroWSProxy() {
    _initPedidosCentroWSProxy();
  }
  
  public PedidosCentroWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initPedidosCentroWSProxy();
  }
  
  private void _initPedidosCentroWSProxy() {
    try {
      pedidosCentroWS = (new es.eroski.misumi.dao.pedidosCentroWS.PedidosCentroWSServiceLocator()).getPedidosCentroWSPort();
      if (pedidosCentroWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)pedidosCentroWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)pedidosCentroWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (pedidosCentroWS != null)
      ((javax.xml.rpc.Stub)pedidosCentroWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public es.eroski.misumi.dao.pedidosCentroWS.PedidosCentroWS getPedidosCentroWS() {
    if (pedidosCentroWS == null)
      _initPedidosCentroWSProxy();
    return pedidosCentroWS;
  }
  
  public es.eroski.misumi.model.pedidosCentroWS.ValidarReferenciasResponseType validarReferencias(es.eroski.misumi.model.pedidosCentroWS.ValidarReferenciasRequestType arg0) throws java.rmi.RemoteException, es.eroski.misumi.model.pedidosCentroWS.FaultType{
    if (pedidosCentroWS == null)
      _initPedidosCentroWSProxy();
    return pedidosCentroWS.validarReferencias(arg0);
  }
  
  public es.eroski.misumi.model.pedidosCentroWS.ConsultaEstadoPedidosResponseType consultaEstadoPedidos(es.eroski.misumi.model.pedidosCentroWS.ConsultaEstadoPedidosRequestType arg0) throws java.rmi.RemoteException, es.eroski.misumi.model.pedidosCentroWS.FaultType{
    if (pedidosCentroWS == null)
      _initPedidosCentroWSProxy();
    return pedidosCentroWS.consultaEstadoPedidos(arg0);
  }
  
  
}