package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.VOferta;

public interface VOfertaDao  {

	public List<VOferta> findAll(VOferta vOferta) throws Exception ;

}
