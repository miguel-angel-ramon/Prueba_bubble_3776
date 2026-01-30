package es.eroski.misumi.service.iface;

import es.eroski.misumi.model.VentaAnticipada;

public interface VentaAnticipadaService {

	public VentaAnticipada obtenerVentaAnticipada(VentaAnticipada ventaAnticipada) throws Exception;
	public void guardarVentaAnticipada(VentaAnticipada ventaAnticipada) throws Exception;
}
