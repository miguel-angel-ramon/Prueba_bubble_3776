/**
 * VentasTiendaServiceBus.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.dao.ventasTiendaWS;

public interface VentasTiendaServiceBus extends javax.xml.rpc.Service {
    public java.lang.String getVentasTiendaPortBusAddress();

    public es.eroski.misumi.dao.ventasTiendaWS.VentasTiendaPortType getVentasTiendaPortBus() throws javax.xml.rpc.ServiceException;

    public es.eroski.misumi.dao.ventasTiendaWS.VentasTiendaPortType getVentasTiendaPortBus(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
