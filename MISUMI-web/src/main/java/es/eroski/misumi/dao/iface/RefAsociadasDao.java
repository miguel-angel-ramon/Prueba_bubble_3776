package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.RefAsociadas;

public interface RefAsociadasDao  {

	public List<RefAsociadas> findAll(RefAsociadas refAsociadas) throws Exception ;
	public Long findAllCont(RefAsociadas refAsociadas) throws Exception;
}
