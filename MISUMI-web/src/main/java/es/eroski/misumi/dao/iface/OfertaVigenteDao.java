package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.OfertaPVP;
import es.eroski.misumi.model.OfertaVigente;

public interface OfertaVigenteDao  {

	public List<OfertaVigente> findAll(OfertaVigente ofertaVigente) throws Exception ;
	public OfertaPVP recuperarAnnoOferta(OfertaPVP ofertaPVP) throws Exception ;

}
