package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.ParamCentrosVp;

public interface ParamCentrosVpDao  {

	public List<ParamCentrosVp> findAll(ParamCentrosVp paramCentrosVp) throws Exception ;

	public Long findAllCont(ParamCentrosVp paramCentrosVp) throws Exception;

}
