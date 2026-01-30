package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.VAgruComerParamSfmcap;
import es.eroski.misumi.model.ui.Pagination;


public interface VAgruComerParamSfmcapService {

	  public List<VAgruComerParamSfmcap> findAll(VAgruComerParamSfmcap vAgruComerSfmcap, Pagination pagination) throws Exception  ;

		/**
		 * Extraemos la estructura unicamente y nos ahorramos algunos mapeos y ordenacion.
		 * Si no hay datos devolvemos null.
		 */
	  public String findTipoEstructura(VAgruComerParamSfmcap vAgruComerSfmcap) throws Exception;
}
