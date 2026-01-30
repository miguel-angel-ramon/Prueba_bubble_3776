/**
 * ReferenciaCentroServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.dao.referenciaCentroWS;

public class ReferenciaCentroServiceLocator extends org.apache.axis.client.Service implements es.eroski.misumi.dao.referenciaCentroWS.ReferenciaCentroService {

    public ReferenciaCentroServiceLocator() {
    }


    public ReferenciaCentroServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ReferenciaCentroServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ReferenciaCentroPort
    private java.lang.String ReferenciaCentroPort_address = "http://gersoa2prepro.eroski.es/services/ReferenciaCentroKNProxy201601";

    public java.lang.String getReferenciaCentroPortAddress() {
        return ReferenciaCentroPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ReferenciaCentroPortWSDDServiceName = "ReferenciaCentroPort";

    public java.lang.String getReferenciaCentroPortWSDDServiceName() {
        return ReferenciaCentroPortWSDDServiceName;
    }

    public void setReferenciaCentroPortWSDDServiceName(java.lang.String name) {
        ReferenciaCentroPortWSDDServiceName = name;
    }

    public es.eroski.misumi.dao.referenciaCentroWS.ReferenciaCentroPortType getReferenciaCentroPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ReferenciaCentroPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getReferenciaCentroPort(endpoint);
    }

    public es.eroski.misumi.dao.referenciaCentroWS.ReferenciaCentroPortType getReferenciaCentroPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            es.eroski.misumi.dao.referenciaCentroWS.ReferenciaCentroBindingStub _stub = new es.eroski.misumi.dao.referenciaCentroWS.ReferenciaCentroBindingStub(portAddress, this);
            _stub.setPortName(getReferenciaCentroPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setReferenciaCentroPortEndpointAddress(java.lang.String address) {
        ReferenciaCentroPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (es.eroski.misumi.dao.referenciaCentroWS.ReferenciaCentroPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                es.eroski.misumi.dao.referenciaCentroWS.ReferenciaCentroBindingStub _stub = new es.eroski.misumi.dao.referenciaCentroWS.ReferenciaCentroBindingStub(new java.net.URL(ReferenciaCentroPort_address), this);
                _stub.setPortName(getReferenciaCentroPortWSDDServiceName());
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
        if ("ReferenciaCentroPort".equals(inputPortName)) {
            return getReferenciaCentroPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.eroski.es/KOSMOS/wsdl/ReferenciaCentroKN", "ReferenciaCentroService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.eroski.es/KOSMOS/wsdl/ReferenciaCentroKN", "ReferenciaCentroPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ReferenciaCentroPort".equals(portName)) {
            setReferenciaCentroPortEndpointAddress(address);
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
