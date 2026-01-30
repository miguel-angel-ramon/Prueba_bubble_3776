package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.PedidoAdicionalEC;

public interface PedidoAdicionalECService {

	public List<PedidoAdicionalEC> findAll(PedidoAdicionalEC pedidoAdicionalM) throws Exception;
	
	public List<PedidoAdicionalEC> removeAll(List<PedidoAdicionalEC> listaPedidoAdicionalEC) throws Exception;
	
	public List<PedidoAdicionalEC> modifyAll(List<PedidoAdicionalEC> listaPedidoAdicionalEC) throws Exception;	

	public PedidoAdicionalEC obtenerTablaSesionECRegistro(String idSesion, Long localizador) throws Exception;	
}
