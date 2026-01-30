/**
 * PlanogramasCentroWSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.dao.planogramasCentroWS;

import es.eroski.misumi.util.Constantes;

public class PlanogramasCentroWSServiceLocator extends org.apache.axis.client.Service implements es.eroski.misumi.dao.planogramasCentroWS.PlanogramasCentroWSService {

    public PlanogramasCentroWSServiceLocator() {
    }


    public PlanogramasCentroWSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PlanogramasCentroWSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PlanogramasCentroWSPort
    private java.lang.String PlanogramasCentroWSPort_address = "http://gersoa2prepro.eroski.es/services/PlanogramasCentro201509Proxy";

    public java.lang.String getPlanogramasCentroWSPortAddress() {
        return PlanogramasCentroWSPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PlanogramasCentroWSPortWSDDServiceName = "PlanogramasCentroWSPort";

    public java.lang.String getPlanogramasCentroWSPortWSDDServiceName() {
        return PlanogramasCentroWSPortWSDDServiceName;
    }

    public void setPlanogramasCentroWSPortWSDDServiceName(java.lang.String name) {
        PlanogramasCentroWSPortWSDDServiceName = name;
    }

    public es.eroski.misumi.dao.planogramasCentroWS.PlanogramasCentroWS getPlanogramasCentroWSPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PlanogramasCentroWSPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPlanogramasCentroWSPort(endpoint);
    }

    public es.eroski.misumi.dao.planogramasCentroWS.PlanogramasCentroWS getPlanogramasCentroWSPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            es.eroski.misumi.dao.planogramasCentroWS.PlanogramasCentroWSPortBindingStub _stub = new es.eroski.misumi.dao.planogramasCentroWS.PlanogramasCentroWSPortBindingStub(portAddress, this);
            _stub.setPortName(getPlanogramasCentroWSPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPlanogramasCentroWSPortEndpointAddress(java.lang.String address) {
        PlanogramasCentroWSPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (es.eroski.misumi.dao.planogramasCentroWS.PlanogramasCentroWS.class.isAssignableFrom(serviceEndpointInterface)) {
                es.eroski.misumi.dao.planogramasCentroWS.PlanogramasCentroWSPortBindingStub _stub = new es.eroski.misumi.dao.planogramasCentroWS.PlanogramasCentroWSPortBindingStub(new java.net.URL(PlanogramasCentroWSPort_address), this);
                _stub.setPortName(getPlanogramasCentroWSPortWSDDServiceName());
                _stub.setTimeout(Constantes.TIMEOUT_WS);
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
        if ("PlanogramasCentroWSPort".equals(inputPortName)) {
            return getPlanogramasCentroWSPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservice.gcnp.eroski.es/", "PlanogramasCentroWSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservice.gcnp.eroski.es/", "PlanogramasCentroWSPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PlanogramasCentroWSPort".equals(portName)) {
            setPlanogramasCentroWSPortEndpointAddress(address);
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
