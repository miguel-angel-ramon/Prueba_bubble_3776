package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.Eans;

public interface EansDao  {

	public List<Eans> findAll(Eans eans) throws Exception ;

	public Long findAllCont(Eans eans) throws Exception;
	
	public Long obtenerReferenciaEan14(Long codigoEan) throws Exception ;
}
