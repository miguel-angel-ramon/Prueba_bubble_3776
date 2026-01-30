package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.CapHistEansDef;

public interface CapHistEansDefDao  {

	public List<CapHistEansDef> findAll(CapHistEansDef capHistEansDef) throws Exception ;

	public Long findAllCont(CapHistEansDef capHistEansDef) throws Exception;
}
