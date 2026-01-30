/**
 * PLUSTiendaServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.dao.alarmasPLUSWS;

public class PLUSTiendaServiceLocator extends org.apache.axis.client.Service implements es.eroski.misumi.dao.alarmasPLUSWS.PLUSTiendaService {

    public PLUSTiendaServiceLocator() {
    }


    public PLUSTiendaServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PLUSTiendaServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PLUSTiendaPort
    private java.lang.String PLUSTiendaPort_address = "http://gersoa2prepro.eroski.es/services/PLUSTiendaProxy";

    public java.lang.String getPLUSTiendaPortAddress() {
        return PLUSTiendaPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PLUSTiendaPortWSDDServiceName = "PLUSTiendaPort";

    public java.lang.String getPLUSTiendaPortWSDDServiceName() {
        return PLUSTiendaPortWSDDServiceName;
    }

    public void setPLUSTiendaPortWSDDServiceName(java.lang.String name) {
        PLUSTiendaPortWSDDServiceName = name;
    }

    public es.eroski.misumi.dao.alarmasPLUSWS.PLUSTiendaPortType getPLUSTiendaPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PLUSTiendaPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPLUSTiendaPort(endpoint);
    }

    public es.eroski.misumi.dao.alarmasPLUSWS.PLUSTiendaPortType getPLUSTiendaPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            es.eroski.misumi.dao.alarmasPLUSWS.PLUSTiendaBindingStub _stub = new es.eroski.misumi.dao.alarmasPLUSWS.PLUSTiendaBindingStub(portAddress, this);
            _stub.setPortName(getPLUSTiendaPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPLUSTiendaPortEndpointAddress(java.lang.String address) {
        PLUSTiendaPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (es.eroski.misumi.dao.alarmasPLUSWS.PLUSTiendaPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                es.eroski.misumi.dao.alarmasPLUSWS.PLUSTiendaBindingStub _stub = new es.eroski.misumi.dao.alarmasPLUSWS.PLUSTiendaBindingStub(new java.net.URL(PLUSTiendaPort_address), this);
                _stub.setPortName(getPLUSTiendaPortWSDDServiceName());
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
        if ("PLUSTiendaPort".equals(inputPortName)) {
            return getPLUSTiendaPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.eroski.es/GCPV/wsdl/PLUSTienda", "PLUSTiendaService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/wsdl/PLUSTienda", "PLUSTiendaPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PLUSTiendaPort".equals(portName)) {
            setPLUSTiendaPortEndpointAddress(address);
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
