/**
 * FacingVegalsaServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.dao.facingVegalsaWS;

public class FacingVegalsaServiceLocator extends org.apache.axis.client.Service implements es.eroski.misumi.dao.facingVegalsaWS.FacingVegalsaService {

    public FacingVegalsaServiceLocator() {
    }


    public FacingVegalsaServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public FacingVegalsaServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for FacingVegalsaPort
    private java.lang.String FacingVegalsaPort_address = "http://gersoa2prepro.eroski.es/services/FacingProxy";

    public java.lang.String getFacingVegalsaPortAddress() {
        return FacingVegalsaPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String FacingVegalsaPortWSDDServiceName = "FacingVegalsaPort";

    public java.lang.String getFacingVegalsaPortWSDDServiceName() {
        return FacingVegalsaPortWSDDServiceName;
    }

    public void setFacingVegalsaPortWSDDServiceName(java.lang.String name) {
        FacingVegalsaPortWSDDServiceName = name;
    }

    public es.eroski.misumi.dao.facingVegalsaWS.FacingVegalsaPortType getFacingVegalsaPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(FacingVegalsaPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getFacingVegalsaPort(endpoint);
    }

    public es.eroski.misumi.dao.facingVegalsaWS.FacingVegalsaPortType getFacingVegalsaPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            es.eroski.misumi.dao.facingVegalsaWS.FacingVegalsaBindingStub _stub = new es.eroski.misumi.dao.facingVegalsaWS.FacingVegalsaBindingStub(portAddress, this);
            _stub.setPortName(getFacingVegalsaPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setFacingVegalsaPortEndpointAddress(java.lang.String address) {
        FacingVegalsaPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (es.eroski.misumi.dao.facingVegalsaWS.FacingVegalsaPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                es.eroski.misumi.dao.facingVegalsaWS.FacingVegalsaBindingStub _stub = new es.eroski.misumi.dao.facingVegalsaWS.FacingVegalsaBindingStub(new java.net.URL(FacingVegalsaPort_address), this);
                _stub.setPortName(getFacingVegalsaPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("FacingVegalsaPort".equals(inputPortName)) {
            return getFacingVegalsaPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.eroski.es/GCPV/wsdl/Facing", "FacingVegalsaService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/wsdl/Facing", "FacingVegalsaPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("FacingVegalsaPort".equals(portName)) {
            setFacingVegalsaPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
