package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.VDatosDiarioCap;

public interface VDatosDiarioCapDao  {

	public List<VDatosDiarioCap> findAll(VDatosDiarioCap vDatosDiarioCap) throws Exception ;
	
}
