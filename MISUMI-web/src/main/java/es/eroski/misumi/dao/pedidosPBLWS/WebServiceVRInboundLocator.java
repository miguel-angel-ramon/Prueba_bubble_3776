/**
 * WebServiceVRInboundLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.dao.pedidosPBLWS;

import es.eroski.misumi.util.Constantes;

public class WebServiceVRInboundLocator extends org.apache.axis.client.Service implements es.eroski.misumi.dao.pedidosPBLWS.WebServiceVRInbound {

    public WebServiceVRInboundLocator() {
    }


    public WebServiceVRInboundLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WebServiceVRInboundLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PedidosPBLSOAPInbound
    private java.lang.String PedidosPBLSOAPInbound_address = "http://gersoa2prepro.eroski.es/services/PedidosPBL201802Proxy";

    public java.lang.String getPedidosPBLSOAPInboundAddress() {
        return PedidosPBLSOAPInbound_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PedidosPBLSOAPInboundWSDDServiceName = "PedidosPBLSOAPInbound";

    public java.lang.String getPedidosPBLSOAPInboundWSDDServiceName() {
        return PedidosPBLSOAPInboundWSDDServiceName;
    }

    public void setPedidosPBLSOAPInboundWSDDServiceName(java.lang.String name) {
        PedidosPBLSOAPInboundWSDDServiceName = name;
    }

    public es.eroski.misumi.dao.pedidosPBLWS.PedidosPBLSOAP getPedidosPBLSOAPInbound() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PedidosPBLSOAPInbound_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPedidosPBLSOAPInbound(endpoint);
    }

    public es.eroski.misumi.dao.pedidosPBLWS.PedidosPBLSOAP getPedidosPBLSOAPInbound(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            es.eroski.misumi.dao.pedidosPBLWS.PedidosPBLSOAPStub _stub = new es.eroski.misumi.dao.pedidosPBLWS.PedidosPBLSOAPStub(portAddress, this);
            _stub.setPortName(getPedidosPBLSOAPInboundWSDDServiceName());
            _stub.setTimeout(Constantes.TIMEOUT_WS);
            return _stub;
           
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPedidosPBLSOAPInboundEndpointAddress(java.lang.String address) {
        PedidosPBLSOAPInbound_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (es.eroski.misumi.dao.pedidosPBLWS.PedidosPBLSOAP.class.isAssignableFrom(serviceEndpointInterface)) {
                es.eroski.misumi.dao.pedidosPBLWS.PedidosPBLSOAPStub _stub = new es.eroski.misumi.dao.pedidosPBLWS.PedidosPBLSOAPStub(new java.net.URL(PedidosPBLSOAPInbound_address), this);
                _stub.setPortName(getPedidosPBLSOAPInboundWSDDServiceName());
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
        if ("PedidosPBLSOAPInbound".equals(inputPortName)) {
            return getPedidosPBLSOAPInbound();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "WebServiceVRInbound");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "PedidosPBLSOAPInbound"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PedidosPBLSOAPInbound".equals(portName)) {
            setPedidosPBLSOAPInboundEndpointAddress(address);
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
