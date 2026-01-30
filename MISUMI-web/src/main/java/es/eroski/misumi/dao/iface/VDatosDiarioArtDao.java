package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.ui.Pagination;

public interface VDatosDiarioArtDao  {

	public List<VDatosDiarioArt> findAll(VDatosDiarioArt vDatosDiarioArt) throws Exception ;
	
	public Long findVidaUtil(VDatosDiarioArt vDatosDiarioArt) throws Exception;
	
	public List<VDatosDiarioArt> findAllVentaRef(VDatosDiarioArt vDatosDiarioArt, Pagination pagination) throws Exception ;

	public Long findAllVentaRefCont(VDatosDiarioArt vDatosDiarioArt) throws Exception ;
	
	public List<Long> findRefMismaRefMadre(Long codCentro,Long codArt)throws Exception;
}
