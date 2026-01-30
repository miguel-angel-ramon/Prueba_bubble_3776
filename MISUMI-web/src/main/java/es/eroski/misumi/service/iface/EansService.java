package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.Eans;


public interface EansService {

	public List<Eans> findAll(Eans eans) throws Exception;
	public Long findAllCont(Eans eans) throws Exception;
	public Eans findOne(Eans eans) throws Exception;
	public Long obtenerReferenciaEan(Long codigoEan) throws Exception;
	public Long obtenerReferenciaEan14(Long codigoEan) throws Exception;
}
