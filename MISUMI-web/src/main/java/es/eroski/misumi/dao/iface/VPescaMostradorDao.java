package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.InformeListadoPesca;
import es.eroski.misumi.model.VPescaMostrador;

public interface VPescaMostradorDao  {

	public Long countAllInformeListadoPesca(Long codCentro) throws Exception;
	public List<VPescaMostrador> findAll(VPescaMostrador vPescaMostrador) throws Exception ;
	public List<InformeListadoPesca> findAllInformeListadoPesca(VPescaMostrador vPescaMostrador) throws Exception ;
	public Long getInformeListadoPescaCount(VPescaMostrador vPescaMostrador) throws Exception;
	public List<VPescaMostrador> findAll(Long codCentro, List<String> listaSubcategoria) throws Exception ;
	public List<String> listaCodigosHabituales(Long codCentro, List<String> listaSubcategoria) throws Exception ;
}
