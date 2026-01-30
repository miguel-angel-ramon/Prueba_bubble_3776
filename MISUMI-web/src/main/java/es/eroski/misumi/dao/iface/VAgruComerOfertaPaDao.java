package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.VAgruComerOfertaPa;

public interface VAgruComerOfertaPaDao  {

	public List<VAgruComerOfertaPa> findAll(VAgruComerOfertaPa vAgruComerOfertaPa) throws Exception ;

}
