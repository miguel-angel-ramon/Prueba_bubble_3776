package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.VentaAnticipada;

public interface VentaAnticipadaDao {
	
	public VentaAnticipada find(VentaAnticipada ventaAnticipada) throws Exception;
	public void insert(VentaAnticipada ventaAnticipada) throws Exception;
	public void update(VentaAnticipada ventaAnticipada) throws Exception;
	public void delete(VentaAnticipada ventaAnticipada) throws Exception;
	

}
