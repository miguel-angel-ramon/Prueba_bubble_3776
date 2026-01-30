package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.VFacingX;


public interface VFacingXService {

	public List<VFacingX> findAll(VFacingX vFacingX) throws Exception;
	public Long findAllCont(VFacingX vFacingX) throws Exception;
	public VFacingX findOne(VFacingX vFacingX) throws Exception;
}
