/**
 * InventarioTiendaServiceBusLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.dao.inventarioWS;

import es.eroski.misumi.util.Constantes;

public class InventarioTiendaServiceBusLocator extends org.apache.axis.client.Service implements es.eroski.misumi.dao.inventarioWS.InventarioTiendaServiceBus {

    public InventarioTiendaServiceBusLocator() {
    }


    public InventarioTiendaServiceBusLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public InventarioTiendaServiceBusLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for InventarioTiendaPortBus
    private java.lang.String InventarioTiendaPortBus_address = "http://gersoa2prepro.eroski.es/services/InventarioTiendaProxy";

    private int timeout = Constantes.TIMEOUT_WS; // en milisegundos

    public java.lang.String getInventarioTiendaPortBusAddress() {
        return InventarioTiendaPortBus_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String InventarioTiendaPortBusWSDDServiceName = "InventarioTiendaPortBus";

    public java.lang.String getInventarioTiendaPortBusWSDDServiceName() {
        return InventarioTiendaPortBusWSDDServiceName;
    }

    public void setInventarioTiendaPortBusWSDDServiceName(java.lang.String name) {
        InventarioTiendaPortBusWSDDServiceName = name;
    }

    public es.eroski.misumi.dao.inventarioWS.InventarioTiendaPortType getInventarioTiendaPortBus() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(InventarioTiendaPortBus_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getInventarioTiendaPortBus(endpoint);
    }

    public es.eroski.misumi.dao.inventarioWS.InventarioTiendaPortType getInventarioTiendaPortBus(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            es.eroski.misumi.dao.inventarioWS.InventarioTiendaBindingBusStub _stub = new es.eroski.misumi.dao.inventarioWS.InventarioTiendaBindingBusStub(portAddress, this);
            _stub.setPortName(getInventarioTiendaPortBusWSDDServiceName());
            _stub.setTimeout(this.timeout);
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setInventarioTiendaPortBusEndpointAddress(java.lang.String address) {
        InventarioTiendaPortBus_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (es.eroski.misumi.dao.inventarioWS.InventarioTiendaPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                es.eroski.misumi.dao.inventarioWS.InventarioTiendaBindingBusStub _stub = new es.eroski.misumi.dao.inventarioWS.InventarioTiendaBindingBusStub(new java.net.URL(InventarioTiendaPortBus_address), this);
                _stub.setPortName(getInventarioTiendaPortBusWSDDServiceName());
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
        if ("InventarioTiendaPortBus".equals(inputPortName)) {
            return getInventarioTiendaPortBus();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.eroski.es/gcpv/wsdl/InventarioTienda", "InventarioTiendaServiceBus");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/wsdl/InventarioTienda", "InventarioTiendaPortBus"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("InventarioTiendaPortBus".equals(portName)) {
            setInventarioTiendaPortBusEndpointAddress(address);
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
