/**
 * WebServiceVRInbound.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.dao.pedidosPBLWS;

public interface WebServiceVRInbound extends javax.xml.rpc.Service {
    public java.lang.String getPedidosPBLSOAPInboundAddress();

    public es.eroski.misumi.dao.pedidosPBLWS.PedidosPBLSOAP getPedidosPBLSOAPInbound() throws javax.xml.rpc.ServiceException;

    public es.eroski.misumi.dao.pedidosPBLWS.PedidosPBLSOAP getPedidosPBLSOAPInbound(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
