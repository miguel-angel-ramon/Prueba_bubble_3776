package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.EncargoReserva;
import es.eroski.misumi.model.EncargosReservasLista;
import es.eroski.misumi.model.PedidoAdicionalCompleto;

public interface EncargosReservasService {

	public EncargosReservasLista obtenerEncReservas(EncargoReserva encargoReserva) throws Exception;
	public EncargosReservasLista insertarEncReservas(List<EncargoReserva> listaEncargosReservas) throws Exception;
	public EncargosReservasLista contarEncReservas(EncargoReserva encargoReserva) throws Exception;
	public EncargoReserva validarArticulo(EncargoReserva encargoReserva) throws Exception;
	public EncargosReservasLista borrarEncReservas(List<EncargoReserva> listaEncargosReservas) throws Exception;
	public EncargosReservasLista modifEncReservas(List<EncargoReserva> listaEncargosReservas) throws Exception;
	public EncargosReservasLista insertarPedido(List<PedidoAdicionalCompleto> list) throws Exception;
	public EncargosReservasLista modificarPedido(PedidoAdicionalCompleto pedidoAdicional) throws Exception;
}
