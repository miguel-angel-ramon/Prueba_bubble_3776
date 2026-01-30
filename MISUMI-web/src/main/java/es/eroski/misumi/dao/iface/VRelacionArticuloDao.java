package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.VRelacionArticulo;

public interface VRelacionArticuloDao  {

	public List<VRelacionArticulo> findAll(VRelacionArticulo vRelacionArticulo) throws Exception ;

}
