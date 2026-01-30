package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.VarAprpv;

public interface VarAprpvDao  {

	public List<VarAprpv> findAll(VarAprpv varAprpv) throws Exception ;

}
