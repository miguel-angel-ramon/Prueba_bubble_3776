/**
 * VentasTiendaServiceBusLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.dao.ventasTiendaWS;

import es.eroski.misumi.util.Constantes;

public class VentasTiendaServiceBusLocator extends org.apache.axis.client.Service implements es.eroski.misumi.dao.ventasTiendaWS.VentasTiendaServiceBus {

    public VentasTiendaServiceBusLocator() {
    }


    public VentasTiendaServiceBusLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public VentasTiendaServiceBusLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for VentasTiendaPortBus
    private java.lang.String VentasTiendaPortBus_address = "http://gersoa2prepro.eroski.es/services/VentasTienda201605Proxy";

    public java.lang.String getVentasTiendaPortBusAddress() {
        return VentasTiendaPortBus_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String VentasTiendaPortBusWSDDServiceName = "VentasTiendaPortBus";

    public java.lang.String getVentasTiendaPortBusWSDDServiceName() {
        return VentasTiendaPortBusWSDDServiceName;
    }

    public void setVentasTiendaPortBusWSDDServiceName(java.lang.String name) {
        VentasTiendaPortBusWSDDServiceName = name;
    }

    public es.eroski.misumi.dao.ventasTiendaWS.VentasTiendaPortType getVentasTiendaPortBus() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(VentasTiendaPortBus_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getVentasTiendaPortBus(endpoint);
    }

    public es.eroski.misumi.dao.ventasTiendaWS.VentasTiendaPortType getVentasTiendaPortBus(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            es.eroski.misumi.dao.ventasTiendaWS.VentasTiendaBindingBusStub _stub = new es.eroski.misumi.dao.ventasTiendaWS.VentasTiendaBindingBusStub(portAddress, this);
            _stub.setPortName(getVentasTiendaPortBusWSDDServiceName());
            
            _stub.setTimeout(Constantes.TIMEOUT_VENTAS_TIENDA_WS);
            
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setVentasTiendaPortBusEndpointAddress(java.lang.String address) {
        VentasTiendaPortBus_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (es.eroski.misumi.dao.ventasTiendaWS.VentasTiendaPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                es.eroski.misumi.dao.ventasTiendaWS.VentasTiendaBindingBusStub _stub = new es.eroski.misumi.dao.ventasTiendaWS.VentasTiendaBindingBusStub(new java.net.URL(VentasTiendaPortBus_address), this);
                _stub.setPortName(getVentasTiendaPortBusWSDDServiceName());
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
        if ("VentasTiendaPortBus".equals(inputPortName)) {
            return getVentasTiendaPortBus();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.eroski.es/gcpv/wsdl/VentasTienda", "VentasTiendaServiceBus");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/wsdl/VentasTienda", "VentasTiendaPortBus"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("VentasTiendaPortBus".equals(portName)) {
            setVentasTiendaPortBusEndpointAddress(address);
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
