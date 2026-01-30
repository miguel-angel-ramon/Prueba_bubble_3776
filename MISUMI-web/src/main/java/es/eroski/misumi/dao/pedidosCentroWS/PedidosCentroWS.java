/**
 * PedidosCentroWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.dao.pedidosCentroWS;

public interface PedidosCentroWS extends java.rmi.Remote {
    public es.eroski.misumi.model.pedidosCentroWS.ValidarReferenciasResponseType validarReferencias(es.eroski.misumi.model.pedidosCentroWS.ValidarReferenciasRequestType arg0) throws java.rmi.RemoteException, es.eroski.misumi.model.pedidosCentroWS.FaultType;
    public es.eroski.misumi.model.pedidosCentroWS.ConsultaEstadoPedidosResponseType consultaEstadoPedidos(es.eroski.misumi.model.pedidosCentroWS.ConsultaEstadoPedidosRequestType arg0) throws java.rmi.RemoteException, es.eroski.misumi.model.pedidosCentroWS.FaultType;
}
