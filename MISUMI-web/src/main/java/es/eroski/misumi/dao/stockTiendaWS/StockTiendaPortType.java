/**
 * StockTiendaPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.dao.stockTiendaWS;

public interface StockTiendaPortType extends java.rmi.Remote {
    public es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType consultarStock(es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType part1) throws java.rmi.RemoteException;
    public es.eroski.misumi.model.stockTiendaWS.ModificarStockResponseType modificarStock(es.eroski.misumi.model.stockTiendaWS.ModificarStockRequestType part3) throws java.rmi.RemoteException;
    public es.eroski.misumi.model.stockTiendaWS.DevolucionResponseType devolucion(es.eroski.misumi.model.stockTiendaWS.DevolucionRequestType part5) throws java.rmi.RemoteException;
}
