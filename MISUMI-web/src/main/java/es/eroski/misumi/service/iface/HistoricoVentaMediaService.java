package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.HistoricoVentaMedia;


public interface HistoricoVentaMediaService {

	  //public List<HistoricoVentaMedia> findAll(HistoricoVentaMedia historicoVentaMedia) throws Exception  ;
	  
	  //public HistoricoVentaMedia findOne(HistoricoVentaMedia historicoVentaMedia) throws Exception  ;
	  
	  public List<HistoricoVentaMedia> findAllAcumuladoRef(HistoricoVentaMedia historicoVentaMedia) throws Exception  ;
	  
	  public HistoricoVentaMedia findOneAcumuladoRef(HistoricoVentaMedia historicoVentaMedia) throws Exception  ;
}
