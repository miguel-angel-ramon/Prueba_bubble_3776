package es.eroski.misumi.service.iface;

import es.eroski.misumi.model.Pedido;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.VRelacionArticulo;
import es.eroski.misumi.model.pedidosCentroWS.ValidarReferenciasResponseType;



public interface PedidosCentroService {

   
   public ValidarReferenciasResponseType findPedidosCentroWS(ReferenciasCentro vReferenciasCentro) throws Exception;
   public Pedido findAllBasicInfo(Pedido pedido) throws Exception;
   public Pedido findAllSegPedidosPda(Pedido pedido, VRelacionArticulo vRelacionArticulo) throws Exception;
}
