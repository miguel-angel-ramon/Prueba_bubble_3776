/**
 * StockTiendaServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.dao.stockTiendaWS;

import es.eroski.misumi.util.Constantes;

public class StockTiendaServiceLocator extends org.apache.axis.client.Service implements es.eroski.misumi.dao.stockTiendaWS.StockTiendaService {

    public StockTiendaServiceLocator() {
    }


    public StockTiendaServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public StockTiendaServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for StockTiendaPort
    private java.lang.String StockTiendaPort_address = "http://gersoa2prepro.eroski.es/services/StockTienda201907Proxy";

    public java.lang.String getStockTiendaPortAddress() {
        return StockTiendaPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String StockTiendaPortWSDDServiceName = "StockTiendaPort";

    public java.lang.String getStockTiendaPortWSDDServiceName() {
        return StockTiendaPortWSDDServiceName;
    }

    public void setStockTiendaPortWSDDServiceName(java.lang.String name) {
        StockTiendaPortWSDDServiceName = name;
    }

    public es.eroski.misumi.dao.stockTiendaWS.StockTiendaPortType getStockTiendaPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(StockTiendaPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getStockTiendaPort(endpoint);
    }

    public es.eroski.misumi.dao.stockTiendaWS.StockTiendaPortType getStockTiendaPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            es.eroski.misumi.dao.stockTiendaWS.StockTiendaBindingStub _stub = new es.eroski.misumi.dao.stockTiendaWS.StockTiendaBindingStub(portAddress, this);
            _stub.setPortName(getStockTiendaPortWSDDServiceName());
            _stub.setTimeout(Constantes.TIMEOUT_STOCK_TIENDA_WS);
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setStockTiendaPortEndpointAddress(java.lang.String address) {
        StockTiendaPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (es.eroski.misumi.dao.stockTiendaWS.StockTiendaPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                es.eroski.misumi.dao.stockTiendaWS.StockTiendaBindingStub _stub = new es.eroski.misumi.dao.stockTiendaWS.StockTiendaBindingStub(new java.net.URL(StockTiendaPort_address), this);
                _stub.setPortName(getStockTiendaPortWSDDServiceName());
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
        if ("StockTiendaPort".equals(inputPortName)) {
            return getStockTiendaPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.eroski.es/GCPV/wsdl/StockTienda201907", "StockTiendaService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/wsdl/StockTienda201907", "StockTiendaPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("StockTiendaPort".equals(portName)) {
            setStockTiendaPortEndpointAddress(address);
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
