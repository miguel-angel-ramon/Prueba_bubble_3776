package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.VRotacionRefEuros;


public interface VRotacionRefEurosService {

	public List<VRotacionRefEuros> findAll(VRotacionRefEuros vRotacionRefEuros) throws Exception;
	public Long findAllCont(VRotacionRefEuros vRotacionRefEuros) throws Exception;
	public VRotacionRefEuros findOne(VRotacionRefEuros vRotacionRefEuros) throws Exception;
}
