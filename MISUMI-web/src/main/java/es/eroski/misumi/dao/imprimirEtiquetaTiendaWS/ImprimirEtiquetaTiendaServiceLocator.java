/**
 * ImprimirEtiquetaTiendaServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.dao.imprimirEtiquetaTiendaWS;

import es.eroski.misumi.util.Constantes;

public class ImprimirEtiquetaTiendaServiceLocator extends org.apache.axis.client.Service implements ImprimirEtiquetaTiendaService {

    public ImprimirEtiquetaTiendaServiceLocator() {
    }


    public ImprimirEtiquetaTiendaServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ImprimirEtiquetaTiendaServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ImprimirEtiquetaTiendaSoap11Endpoint
    private java.lang.String ImprimirEtiquetaTiendaSoap11Endpoint_address = "http://gersoa2prepro.eroski.es/services/ImprimirEtiquetaTiendaProxy";

    public java.lang.String getImprimirEtiquetaTiendaSoap11EndpointAddress() {
        return ImprimirEtiquetaTiendaSoap11Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ImprimirEtiquetaTiendaSoap11EndpointWSDDServiceName = "ImprimirEtiquetaTiendaSoap11Endpoint";

    public java.lang.String getImprimirEtiquetaTiendaSoap11EndpointWSDDServiceName() {
        return ImprimirEtiquetaTiendaSoap11EndpointWSDDServiceName;
    }

    public void setImprimirEtiquetaTiendaSoap11EndpointWSDDServiceName(java.lang.String name) {
        ImprimirEtiquetaTiendaSoap11EndpointWSDDServiceName = name;
    }

    public ImprimirEtiquetaPort getImprimirEtiquetaTiendaSoap11Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ImprimirEtiquetaTiendaSoap11Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getImprimirEtiquetaTiendaSoap11Endpoint(endpoint);
    }

    public ImprimirEtiquetaPort getImprimirEtiquetaTiendaSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ImprimirEtiquetaTienda11BindingStub _stub = new ImprimirEtiquetaTienda11BindingStub(portAddress, this);
            _stub.setPortName(getImprimirEtiquetaTiendaSoap11EndpointWSDDServiceName());
            _stub.setTimeout(Constantes.TIMEOUT_WS);
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setImprimirEtiquetaTiendaSoap11EndpointEndpointAddress(java.lang.String address) {
        ImprimirEtiquetaTiendaSoap11Endpoint_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ImprimirEtiquetaPort.class.isAssignableFrom(serviceEndpointInterface)) {
                ImprimirEtiquetaTienda11BindingStub _stub = new ImprimirEtiquetaTienda11BindingStub(new java.net.URL(ImprimirEtiquetaTiendaSoap11Endpoint_address), this);
                _stub.setPortName(getImprimirEtiquetaTiendaSoap11EndpointWSDDServiceName());
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
        if ("ImprimirEtiquetaTiendaSoap11Endpoint".equals(inputPortName)) {
            return getImprimirEtiquetaTiendaSoap11Endpoint();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.eroski.es/gcpv/wsdl/imprimirEtiquetaTienda", "ImprimirEtiquetaTiendaService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/wsdl/imprimirEtiquetaTienda", "ImprimirEtiquetaTiendaSoap11Endpoint"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ImprimirEtiquetaTiendaSoap11Endpoint".equals(portName)) {
            setImprimirEtiquetaTiendaSoap11EndpointEndpointAddress(address);
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
