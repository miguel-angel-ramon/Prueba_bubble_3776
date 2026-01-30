package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.VFacingX;

public interface VFacingXDao  {
	
	public List<VFacingX> findAll(VFacingX vFacingX) throws Exception;
	public Long findAllCont(VFacingX vFacingX) throws Exception;
	public VFacingX findOne(VFacingX vFacingX) throws Exception;
}
