package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.Pedido;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.pedidosCentroWS.ValidarReferenciasResponseType;

public interface PedidoCentroDao  {
	public ValidarReferenciasResponseType findPedidosCentroWS(ReferenciasCentro vReferenciasCentro) throws Exception;

	public Pedido findAllUltimosEnvios(Pedido pedido, List<Long> referencias) throws Exception;
}
