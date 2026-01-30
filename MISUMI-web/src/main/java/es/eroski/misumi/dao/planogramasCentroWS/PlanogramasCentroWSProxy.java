package es.eroski.misumi.dao.planogramasCentroWS;

public class PlanogramasCentroWSProxy implements es.eroski.misumi.dao.planogramasCentroWS.PlanogramasCentroWS {
  private String _endpoint = null;
  private es.eroski.misumi.dao.planogramasCentroWS.PlanogramasCentroWS planogramasCentroWS = null;
  
  public PlanogramasCentroWSProxy() {
    _initPlanogramasCentroWSProxy();
  }
  
  public PlanogramasCentroWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initPlanogramasCentroWSProxy();
  }
  
  private void _initPlanogramasCentroWSProxy() {
    try {
      planogramasCentroWS = (new es.eroski.misumi.dao.planogramasCentroWS.PlanogramasCentroWSServiceLocator()).getPlanogramasCentroWSPort();
      if (planogramasCentroWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)planogramasCentroWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)planogramasCentroWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (planogramasCentroWS != null)
      ((javax.xml.rpc.Stub)planogramasCentroWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public es.eroski.misumi.dao.planogramasCentroWS.PlanogramasCentroWS getPlanogramasCentroWS() {
    if (planogramasCentroWS == null)
      _initPlanogramasCentroWSProxy();
    return planogramasCentroWS;
  }
  
  public es.eroski.misumi.model.planogramasCentroWS.ConsultaPlanogramaPorReferenciaResponseType consultaPlanogramaPorReferencia(es.eroski.misumi.model.planogramasCentroWS.ConsultaPlanogramaPorReferenciaRequestType arg0) throws java.rmi.RemoteException, es.eroski.misumi.model.planogramasCentroWS.FaultType{
    if (planogramasCentroWS == null)
      _initPlanogramasCentroWSProxy();
    return planogramasCentroWS.consultaPlanogramaPorReferencia(arg0);
  }
  
  
}