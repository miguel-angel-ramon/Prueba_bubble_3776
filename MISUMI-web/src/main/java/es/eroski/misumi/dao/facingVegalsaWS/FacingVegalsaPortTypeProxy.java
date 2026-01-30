package es.eroski.misumi.dao.facingVegalsaWS;

public class FacingVegalsaPortTypeProxy implements es.eroski.misumi.dao.facingVegalsaWS.FacingVegalsaPortType {

	  private String _endpoint = null;
	  private es.eroski.misumi.dao.facingVegalsaWS.FacingVegalsaPortType facingVegalsaPortType = null;
	  
	  public FacingVegalsaPortTypeProxy() {
	    _initFacingVegalsaPortTypeProxy();
	  }
	  
	  public FacingVegalsaPortTypeProxy(String endpoint) {
	    _endpoint = endpoint;
	    _initFacingVegalsaPortTypeProxy();
	  }
	  
	  private void _initFacingVegalsaPortTypeProxy() {
	    try {
	      facingVegalsaPortType = (new es.eroski.misumi.dao.facingVegalsaWS.FacingVegalsaServiceLocator()).getFacingVegalsaPort();
	      if (facingVegalsaPortType != null) {
	        if (_endpoint != null)
	          ((javax.xml.rpc.Stub)facingVegalsaPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
	        else
	          _endpoint = (String)((javax.xml.rpc.Stub)facingVegalsaPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
	      }
	      
	    }
	    catch (javax.xml.rpc.ServiceException serviceException) {}
	  }
	  
	  public String getEndpoint() {
	    return _endpoint;
	  }
	  
	  public void setEndpoint(String endpoint) {
	    _endpoint = endpoint;
	    if (facingVegalsaPortType != null)
	      ((javax.xml.rpc.Stub)facingVegalsaPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
	    
	  }
	  
	  public es.eroski.misumi.dao.facingVegalsaWS.FacingVegalsaPortType getfacingVegalsaPortType() {
	    if (facingVegalsaPortType == null)
	      _initFacingVegalsaPortTypeProxy();
	    return facingVegalsaPortType;
	  }
	
	public es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaResponseType consultaFacing(es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaRequestType part1) throws java.rmi.RemoteException {
	    if (facingVegalsaPortType == null)
	        _initFacingVegalsaPortTypeProxy();
	    return facingVegalsaPortType.consultaFacing(part1);
	}
	
	public es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaResponseType modificarFacing(es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaRequestType part3) throws java.rmi.RemoteException {
	    if (facingVegalsaPortType == null)
	        _initFacingVegalsaPortTypeProxy();
	    return facingVegalsaPortType.modificarFacing(part3);
	}

	public es.eroski.misumi.model.facingVegalsaWS.ModificarMedidasFacingVegalsaResponseType modificarMedidasFacing(es.eroski.misumi.model.facingVegalsaWS.ModificarMedidasFacingVegalsaRequestType part5) throws java.rmi.RemoteException {
	    if (facingVegalsaPortType == null)
	        _initFacingVegalsaPortTypeProxy();
	    return facingVegalsaPortType.modificarMedidasFacing(part5);
	}
	
}
