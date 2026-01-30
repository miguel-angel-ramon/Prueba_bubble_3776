package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.Agrupacion;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.ui.Pagination;

public interface AgrupacionDao  {

	  public List<Agrupacion> findAll(Agrupacion agrupacion) throws Exception  ;
}
