package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.HistoricoVentaMedia;

public interface HistoricoVentaMediaDao  {

	//public List<HistoricoVentaMedia> findAll(HistoricoVentaMedia historicoVentaMedia) throws Exception ;
	public List<HistoricoVentaMedia> findAllAcumuladoRef(HistoricoVentaMedia historicoVentaMedia) throws Exception ;
}
