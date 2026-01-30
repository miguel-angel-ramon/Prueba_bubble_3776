package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.VarAprpv;


public interface VarAprpvService {

	  public List<VarAprpv> findAll(VarAprpv varAprpv) throws Exception  ;
		  
	  public VarAprpv findOne(VarAprpv varAprpv) throws Exception  ;
}
