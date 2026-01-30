package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.VPlanPedidoAdicional;

public interface VPlanPedidoAdicionalDao  {

	public List<VPlanPedidoAdicional> findAll(VPlanPedidoAdicional vPlanPedidoAdicional) throws Exception ;
	public List<String> findUltimosPedidos(VPlanPedidoAdicional vPlanPedidoAdicional) throws Exception ;
	public Boolean isCentroVegalsa(Long codCentro) throws Exception; 
}
