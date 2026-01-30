package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.ParamCentrosOpc;


public interface ParamCentrosOpcService {

	public List<ParamCentrosOpc> findAll(ParamCentrosOpc paramCentrosOpc) throws Exception;
	public ParamCentrosOpc findOne(ParamCentrosOpc paramCentrosOpc) throws Exception;
	public Long findAllCont(ParamCentrosOpc paramCentrosOpc) throws Exception;
}
