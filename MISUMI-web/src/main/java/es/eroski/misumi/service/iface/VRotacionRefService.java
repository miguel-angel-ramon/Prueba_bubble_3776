package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.VRotacionRef;


public interface VRotacionRefService {

	public List<VRotacionRef> findAll(VRotacionRef vRotacionRef) throws Exception;
	public Long findAllCont(VRotacionRef vRotacionRef) throws Exception;
	public VRotacionRef findOne(VRotacionRef vRotacionRef) throws Exception;
}
