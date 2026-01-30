package es.eroski.misumi.dao.pedidosPBLWS;

import es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloRequestValidarArticuloValidarArticulo;
import es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo;
import es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoModificarReponse;
import es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoObtenerRequest;
import es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoObtenerResponse;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesRequest;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesResponse;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerRequest;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerResponse;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse;

public class PedidosPBLSOAPProxy implements es.eroski.misumi.dao.pedidosPBLWS.PedidosPBLSOAP {
  private String _endpoint = null;
  private es.eroski.misumi.dao.pedidosPBLWS.PedidosPBLSOAP pedidosPBLSOAP = null;
  
  public PedidosPBLSOAPProxy() {
    _initPedidosPBLSOAPProxy();
  }
  
  public PedidosPBLSOAPProxy(String endpoint) {
    _endpoint = endpoint;
    _initPedidosPBLSOAPProxy();
  }
  
  private void _initPedidosPBLSOAPProxy() {
    try {
      pedidosPBLSOAP = (new es.eroski.misumi.dao.pedidosPBLWS.WebServiceVRInboundLocator()).getPedidosPBLSOAPInbound();
      if (pedidosPBLSOAP != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)pedidosPBLSOAP)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)pedidosPBLSOAP)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (pedidosPBLSOAP != null)
      ((javax.xml.rpc.Stub)pedidosPBLSOAP)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public es.eroski.misumi.dao.pedidosPBLWS.PedidosPBLSOAP getPedidosPBLSOAP() {
    if (pedidosPBLSOAP == null)
      _initPedidosPBLSOAPProxy();
    return pedidosPBLSOAP;
  }
  
  public es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoModificarReponse detalladoPedidoModificar(es.eroski.misumi.model.pedidosPBLWS.ArrayOfDetalladoPedidoDetalladoPedido[] detalladoPedidoModificarRequest) throws java.rmi.RemoteException{
    if (pedidosPBLSOAP == null)
      _initPedidosPBLSOAPProxy();
    return pedidosPBLSOAP.detalladoPedidoModificar(detalladoPedidoModificarRequest);
  }
  
  public es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoObtenerResponse detalladoPedidoObtener(es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoObtenerRequest detalladoPedidoObtenerRequest) throws java.rmi.RemoteException{
    if (pedidosPBLSOAP == null)
      _initPedidosPBLSOAPProxy();
    return pedidosPBLSOAP.detalladoPedidoObtener(detalladoPedidoObtenerRequest);
  }
  
  public es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesResponse pedidoAdicionalContarClases(es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesRequest pedidoAdicionalContarClasesRequest) throws java.rmi.RemoteException{
    if (pedidosPBLSOAP == null)
      _initPedidosPBLSOAPProxy();
    return pedidosPBLSOAP.pedidoAdicionalContarClases(pedidoAdicionalContarClasesRequest);
  }
  
  public es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse[] pedidoAdicionalEliminar(es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalPedidoAdicional[] pedidoAdicionalEliminarRequest) throws java.rmi.RemoteException{
    if (pedidosPBLSOAP == null)
      _initPedidosPBLSOAPProxy();
    return pedidosPBLSOAP.pedidoAdicionalEliminar(pedidoAdicionalEliminarRequest);
  }
  
  public es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse[] pedidoAdicionalInsertar(es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalPedidoAdicional[] pedidoAdicionalInsertarRequest) throws java.rmi.RemoteException{
    if (pedidosPBLSOAP == null)
      _initPedidosPBLSOAPProxy();
    return pedidosPBLSOAP.pedidoAdicionalInsertar(pedidoAdicionalInsertarRequest);
  }
  
  public es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse[] pedidoAdicionalModificar(es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalPedidoAdicional[] pedidoAdicionalModificarRequest) throws java.rmi.RemoteException{
    if (pedidosPBLSOAP == null)
      _initPedidosPBLSOAPProxy();
    return pedidosPBLSOAP.pedidoAdicionalModificar(pedidoAdicionalModificarRequest);
  }
  
  public es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerResponse pedidoAdicionalObtener(es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerRequest pedidoAdicionalObtenerRequest) throws java.rmi.RemoteException{
    if (pedidosPBLSOAP == null)
      _initPedidosPBLSOAPProxy();
    return pedidosPBLSOAP.pedidoAdicionalObtener(pedidoAdicionalObtenerRequest);
  }
  
  public es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo[] validarArticulo(es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloRequestValidarArticuloValidarArticulo[] validarArticuloRequest) throws java.rmi.RemoteException{
    if (pedidosPBLSOAP == null)
      _initPedidosPBLSOAPProxy();
    return pedidosPBLSOAP.validarArticulo(validarArticuloRequest);
  }
  
  public es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaObtenerResponseType exclusionVentaObtener(es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaObtenerRequestType obtenerExclusion) throws java.rmi.RemoteException{
    if (pedidosPBLSOAP == null)
      _initPedidosPBLSOAPProxy();
    return pedidosPBLSOAP.exclusionVentaObtener(obtenerExclusion);
  }
  
  public es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaInsertarResponseType exclusionVentaInsertar(es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaInsertarRequestType insertarExclusion) throws java.rmi.RemoteException{
    if (pedidosPBLSOAP == null)
      _initPedidosPBLSOAPProxy();
    return pedidosPBLSOAP.exclusionVentaInsertar(insertarExclusion);
  }
  
  public es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaBorrarResponseType exclusionVentaBorrar(es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaBorrarRequestType borrarExclusion) throws java.rmi.RemoteException{
    if (pedidosPBLSOAP == null)
      _initPedidosPBLSOAPProxy();
    return pedidosPBLSOAP.exclusionVentaBorrar(borrarExclusion);
  }
  
  
}