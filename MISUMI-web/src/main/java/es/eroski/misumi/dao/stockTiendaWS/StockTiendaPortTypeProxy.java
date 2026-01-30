package es.eroski.misumi.dao.stockTiendaWS;

public class StockTiendaPortTypeProxy implements es.eroski.misumi.dao.stockTiendaWS.StockTiendaPortType {
  private String _endpoint = null;
  private es.eroski.misumi.dao.stockTiendaWS.StockTiendaPortType stockTiendaPortType = null;
  
  public StockTiendaPortTypeProxy() {
    _initStockTiendaPortTypeProxy();
  }
  
  public StockTiendaPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initStockTiendaPortTypeProxy();
  }
  
  private void _initStockTiendaPortTypeProxy() {
    try {
      stockTiendaPortType = (new es.eroski.misumi.dao.stockTiendaWS.StockTiendaServiceLocator()).getStockTiendaPort();
      if (stockTiendaPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)stockTiendaPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)stockTiendaPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (stockTiendaPortType != null)
      ((javax.xml.rpc.Stub)stockTiendaPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public es.eroski.misumi.dao.stockTiendaWS.StockTiendaPortType getStockTiendaPortType() {
    if (stockTiendaPortType == null)
      _initStockTiendaPortTypeProxy();
    return stockTiendaPortType;
  }
  
  public es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType consultarStock(es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType part1) throws java.rmi.RemoteException{
    if (stockTiendaPortType == null)
      _initStockTiendaPortTypeProxy();
    return stockTiendaPortType.consultarStock(part1);
  }
  
  public es.eroski.misumi.model.stockTiendaWS.ModificarStockResponseType modificarStock(es.eroski.misumi.model.stockTiendaWS.ModificarStockRequestType part3) throws java.rmi.RemoteException{
    if (stockTiendaPortType == null)
      _initStockTiendaPortTypeProxy();
    return stockTiendaPortType.modificarStock(part3);
  }
  
  public es.eroski.misumi.model.stockTiendaWS.DevolucionResponseType devolucion(es.eroski.misumi.model.stockTiendaWS.DevolucionRequestType part5) throws java.rmi.RemoteException{
    if (stockTiendaPortType == null)
      _initStockTiendaPortTypeProxy();
    return stockTiendaPortType.devolucion(part5);
  }
  
  
}