package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.BorrUniAux;
import es.eroski.misumi.model.ui.Pagination;

public interface BorrUniAuxDao  {

	public List<BorrUniAux> findAll(BorrUniAux borrUniAux, Pagination pagination) throws Exception ;

}
