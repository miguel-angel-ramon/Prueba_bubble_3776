package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.RefAsociadas;

public interface RefAsociadasService {

	public List<RefAsociadas> findAll(RefAsociadas refAsociadas) throws Exception;
	public Long findAllCont(RefAsociadas refAsociadas) throws Exception;
}
