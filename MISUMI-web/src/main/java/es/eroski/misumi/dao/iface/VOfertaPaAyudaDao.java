package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.VOfertaPaAyuda;

public interface VOfertaPaAyudaDao  {

	public List<VOfertaPaAyuda> findCountNOVigentes(VOfertaPaAyuda vOfertaPaAyuda, int iRows) throws Exception;
	public List<VOfertaPaAyuda> findAllNoVigentesCentro(VOfertaPaAyuda vOfertaPaAyuda) throws Exception;
}
