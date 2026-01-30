package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.VAgruComerParamSfmcap;
import es.eroski.misumi.model.ui.Pagination;

public interface VAgruComerParamSfmcapDao  {

	public List<VAgruComerParamSfmcap> findAll(VAgruComerParamSfmcap vAgruComerParamSfmcap, Pagination pagination) throws Exception ;

}
