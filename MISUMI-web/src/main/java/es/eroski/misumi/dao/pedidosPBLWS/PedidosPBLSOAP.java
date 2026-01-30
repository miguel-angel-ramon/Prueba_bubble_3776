/**
 * PedidosPBLSOAP.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.dao.pedidosPBLWS;

import es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloRequestValidarArticuloValidarArticulo;
import es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo;
import es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoModificarReponse;
import es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoObtenerRequest;
import es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoObtenerResponse;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesRequest;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesResponse;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerRequest;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerResponse;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse;

public interface PedidosPBLSOAP extends java.rmi.Remote {
    public es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoModificarReponse detalladoPedidoModificar(es.eroski.misumi.model.pedidosPBLWS.ArrayOfDetalladoPedidoDetalladoPedido[] detalladoPedidoModificarRequest) throws java.rmi.RemoteException;
    public es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoObtenerResponse detalladoPedidoObtener(es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoObtenerRequest detalladoPedidoObtenerRequest) throws java.rmi.RemoteException;
    public es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesResponse pedidoAdicionalContarClases(es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesRequest pedidoAdicionalContarClasesRequest) throws java.rmi.RemoteException;
    public es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse[] pedidoAdicionalEliminar(es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalPedidoAdicional[] pedidoAdicionalEliminarRequest) throws java.rmi.RemoteException;
    public es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse[] pedidoAdicionalInsertar(es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalPedidoAdicional[] pedidoAdicionalInsertarRequest) throws java.rmi.RemoteException;
    public es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse[] pedidoAdicionalModificar(es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalPedidoAdicional[] pedidoAdicionalModificarRequest) throws java.rmi.RemoteException;
    public es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerResponse pedidoAdicionalObtener(es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerRequest pedidoAdicionalObtenerRequest) throws java.rmi.RemoteException;
    public es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo[] validarArticulo(es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloRequestValidarArticuloValidarArticulo[] validarArticuloRequest) throws java.rmi.RemoteException;
    public es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaObtenerResponseType exclusionVentaObtener(es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaObtenerRequestType obtenerExclusion) throws java.rmi.RemoteException;
    public es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaInsertarResponseType exclusionVentaInsertar(es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaInsertarRequestType insertarExclusion) throws java.rmi.RemoteException;
    public es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaBorrarResponseType exclusionVentaBorrar(es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaBorrarRequestType borrarExclusion) throws java.rmi.RemoteException;
}
